package ecar.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.permissao.ControlePermissao;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstrutUsuarioIettus;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.SituacaoSit;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/**
 * DAO para carga de dados em ItemEstruturaIett e seus relacionamentos
 * Solução temporária para atender apuração especial
 * 
 * 
 */
public class CargaItemEstruturaIettDao extends Dao {
    /**
     *
     * @param request
     */
	
	final String DESCRICAO_GRUPO_ACESSO_PADRAO = "Grupo PACInter";
	final String DESCRICAO_SITUACAO_PADRAO = "Importado do PACinter";
	final String DESCRICAO_ORGAO_PADRAO_SEM_CORRESPONDENTE = "Sem Correspondente no PACinter";
	final String DESCRICAO_ORGAO_PADRAO_NAO_INFORMADO = "Órgão não informado pelo PACInter";
	final String NOME_ESTRUTURA_PADRAO = "PLANEJAMENTO ESTRATÉGICO";
	final String NOME_ITEM_PAI = "Infra-Estrutura";
	final String NOME_USUARIO_PADRAO = "Usuário PACInter";
	final String STRING_VAZIO = "";
	final String MASCARA_DATA = "dd/MM/yyyy";
	final int QUANTIDADE_CAMPOS_LINHA = 11;
	
    public CargaItemEstruturaIettDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
    
    /**
     *
     * @throws ECARException
     */
    public void efetuarCargaItens() throws ECARException{
        Transaction tx = null;
        ConfiguracaoCfg configuracaoCfg = new ConfiguracaoDao(request).getConfiguracao();
        try{
        	
		    super.inicializarLogBean();

            tx = session.beginTransaction();

            executarCargaItemEstruturaIett(configuracaoCfg);
            
			tx.commit();
		} catch (ECARException e) {
			e.printStackTrace();
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
	        this.logger.error(e);
			throw new ECARException(e.getMessageKey()); 
		}	    			
    }
    
    /**
     * 
     * @param configuracaoDao
     * @throws ECARException
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void executarCargaItemEstruturaIett(ConfiguracaoCfg configuracaoCfg) throws ECARException{
    	try {
    		//Carrega o usuário padrão "Usuário PACInter" para setar nos objetos
    		UsuarioDao usuarioDao = new UsuarioDao(request);
    		UsuarioUsu usuarioUsuPadrao = usuarioDao.getUsuarioUsuByNome(NOME_USUARIO_PADRAO);
    		if (usuarioUsuPadrao == null){
    			throw new ECARException("Usuário \""+ NOME_USUARIO_PADRAO + "\" não cadastrado.");
    		}
    		
    		//Carrega a situação "Importado do PACInter" para setar nos objetos de item de estrutura importados
    		SituacaoDao situacaoDao = new SituacaoDao(request);
    		SituacaoSit situacaoSitPadrao = situacaoDao.getSituacaoSitByDescricao(DESCRICAO_SITUACAO_PADRAO);
    		if (situacaoSitPadrao == null){
    			throw new ECARException("Situação \""+ DESCRICAO_SITUACAO_PADRAO + "\" não cadastrada.");
    		}
    		//Carrega o orgão "Sem correspondente no PACInter" para usar quando o órgão do PACInter 
    		//não esteja cadastrado no e-car
    		OrgaoDao orgaoDao = new OrgaoDao(request);
    		OrgaoOrg orgaoOrgPadraoSemCorrespondente = orgaoDao.getOrgaoOrgByDescricao(DESCRICAO_ORGAO_PADRAO_SEM_CORRESPONDENTE);
    		if (orgaoOrgPadraoSemCorrespondente == null){
    			throw new ECARException("Órgão \"" +DESCRICAO_ORGAO_PADRAO_SEM_CORRESPONDENTE + "\" não cadastrado.");
    		}
    		
    		OrgaoOrg orgaoOrgPadraoNaoInformado = orgaoDao.getOrgaoOrgByDescricao(DESCRICAO_ORGAO_PADRAO_NAO_INFORMADO);
    		if (orgaoOrgPadraoNaoInformado == null){
    			throw new ECARException("Órgão \"" +DESCRICAO_ORGAO_PADRAO_NAO_INFORMADO + "\" não cadastrado.");
    		}
    		
    		SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
    		SisGrupoAtributoSga grupoAcessoUsuarioLogado = configuracaoCfg.getSisGrupoAtributoSgaByCodSgaGrAtrClAcesso();
    		
    		SisAtributoSatb sisAtributoSatbGrupoAcessoPadrao = sisAtributoDao.getSisAtributoSatb(grupoAcessoUsuarioLogado, DESCRICAO_GRUPO_ACESSO_PADRAO);
    		
    		if (sisAtributoSatbGrupoAcessoPadrao == null){
    			throw new ECARException("Grupo de acesso \"" + DESCRICAO_GRUPO_ACESSO_PADRAO + "\" não cadastrado.");
    		}
    		//Estatistica
    		//Número de registros do arquivo
    		//Número de registros importados
    		//Número de registros por cidade arquivo
    		//Número de registros por cidade importado
    		//Número de registros com orgão = ""
    		//Número de registros com orgão = "Sem correspondente"
    		//Se abortar exibir linha completa do registro
    		//Carrega a estrutura padrão para importação dos itens
    		EstruturaDao estruturaDao = new EstruturaDao(request);
    		EstruturaEtt estruturaEttPadrao = estruturaDao.getEstruturaEttByNome(NOME_ESTRUTURA_PADRAO);
    		
    		if (estruturaEttPadrao == null){
    			throw new ECARException("Estrutura \"" + NOME_ESTRUTURA_PADRAO + "\" não cadastrada.");
    		}
    			
    		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
    		
    		HashMap hashMapItensPais = new HashMap();
        	
            String caminhoArquivoEntrada = configuracaoCfg.getRaizUpload() + "/importacaoDadosPacInter/empreendimentos_copa2014_UF_IBGE.csv";
            
            String caminhoArquivoSaida = configuracaoCfg.getRaizUpload() + "/importacaoDadosPacInter/Rejeitados-PACInter-empreendimentos.csv";
            
            FileOutputStream arquivoSaida = new FileOutputStream(caminhoArquivoSaida);
            FileInputStream file = new FileInputStream(caminhoArquivoEntrada);
            InputStreamReader inputStream = new InputStreamReader(file, Dominios.ENCODING_DEFAULT);       
        	BufferedReader in = new BufferedReader (inputStream);
        	
        	int countRegistrosArquivo = 0;
        	int countRegistrosImportados = 0;
        	int countRegistrosRejeitadosPorUF = 0;
        	HashMap hashMapRegistrosPorCidadeArquivo = new HashMap();
        	HashMap hashMapRegistrosPorCidadeImportados = new HashMap();
        	       	
    		String linha="";
    		String cabecalho = "";
    		if ((cabecalho = in.readLine()) != null){
    			validarSequenciaCamposCabecalho(cabecalho);
    			arquivoSaida.write((cabecalho + ";\"motivo_rejeicao\"\n").getBytes(Dominios.ENCODING_DEFAULT));
                arquivoSaida.flush();
    			
    			while ((linha=in.readLine()) != null) {
    				if (linha == null || linha.trim().equals(STRING_VAZIO)){
    					continue;
    				}
    				countRegistrosArquivo++;
	    			String[] campos = linha.split(";");
	    			if (campos.length < QUANTIDADE_CAMPOS_LINHA){
	    				arquivoSaida.write((linha + ";Quantidade de campos da linha diferente da esperada " +  QUANTIDADE_CAMPOS_LINHA + "\n").getBytes(Dominios.ENCODING_DEFAULT));
	    				arquivoSaida.flush();
	    				continue;
	    			}
	    			/*
	    			Mapeamento dos campos do arquivo empreendimentos.csv
	    			Código - idn_empreendimento - siglaIett 
					Nome - dsc_titulo - nomeIett
					Descrição - dsc_descricao - descricaoR2
					Data de Início - dat_inicio_empreendimento - dataInicioIett	
					Data de Término - dat_conclusao_original - dataTerminoIett
					Orgão Responsável - dsc_executor_controle - orgaoOrgByCodOrgaoResponsavel1Iett	
					Valor Previsto - custo - valPrevistoFuturoIett
					Situação - dsc_estagio (será considerado valor fixo 'Importado do PACInter') - situacaoSit
					Tipo - dsc_tipo_empreendimento - descricaoR4
					Sub-Tipo - dsc_subtipo - descricaoR5
					Código da UF IBGE - (cod_uf_ibge) - descricao Reserva 1 em Cidade da Copa
	    	    	*/
	    			String codItemExterno = formatarStringSemAspas(campos[0].trim());
	    			String nome = formatarStringSemAspas(campos[1].trim());
	    			String descricao = formatarStringSemAspas(campos[2].trim());
	    			String dataInicio = formatarStringSemAspas(campos[3].trim());
	    			String dataTermino = formatarStringSemAspas(campos[4].trim());
	    			String orgaoResponsavel = formatarStringSemAspas(campos[5].trim());
	    			String valorPrevisto = formatarStringSemAspas(campos[6].trim());
	    			//A princípio a situação não será importada
	    			//será usada a situação padrão "Importado do PACInter"
	    			String situacao = formatarStringSemAspas(campos[7].trim());
	    			String tipoEmpreendimento = formatarStringSemAspas(campos[8].trim()); //descricaoR4
	    			String subTipoEmpreendimento = formatarStringSemAspas(campos[9].trim()); //descricaoR5
	    			String codUFIBGE = formatarStringSemAspas(campos[10].trim());
	    			if (hashMapRegistrosPorCidadeArquivo.containsKey(codUFIBGE)){
	    				Integer qtdArquivos = (Integer) hashMapRegistrosPorCidadeArquivo.get(codUFIBGE);
	    				qtdArquivos = qtdArquivos + 1;
	    				hashMapRegistrosPorCidadeArquivo.put(codUFIBGE, qtdArquivos);
	    			} else {
	    				hashMapRegistrosPorCidadeArquivo.put(codUFIBGE, new Integer(1));
	    			}
	    			System.out.println("Lendo linha: " + countRegistrosArquivo + ".\n ID: " + codItemExterno + "\n");
	    			//Validações
	    			//valida se o código do PACInter foi informado no arquivo
	    			if (codItemExterno == null || codItemExterno.equals(STRING_VAZIO)){
	    				arquivoSaida.write((linha + ";Código do Item no PACInter (idn_empreendimento) não informado\n").getBytes(Dominios.ENCODING_DEFAULT));
	    				arquivoSaida.flush();
	    				continue;
	    			}
	    			
	    			//validaçao se o item já foi registrado no e-car (validar pelo codItemExterno).
	    			//se já foi registrado, passa para o próximo item
	    			if (itemEstruturaDao.getItemEstruturaBySiglaDescricaoR1Avo(codItemExterno, codUFIBGE) != null){
	    				continue;
    				}
	    			 
	    			Date dataInicioDate = null;
	    			//valida se data de início passada está no formato esperado
	    			if (dataInicio != null && !dataInicio.equals(STRING_VAZIO)){
	    				dataInicioDate = Data.parseDate(dataInicio, MASCARA_DATA); 
	    				if (dataInicioDate == null){
	    					arquivoSaida.write((linha + ";Data de Início (dat_inicio_empreendimento) inválida\n").getBytes(Dominios.ENCODING_DEFAULT));
		    				arquivoSaida.flush();
		    				continue;
	    				}
	    			}
	    			
	    			Date dataTerminoDate = null;
	    			//valida se data termino passada está no formato esperado
	    			if (dataTermino != null && !dataTermino.equals(STRING_VAZIO)){
	    				dataTerminoDate = Data.parseDate(dataTermino, MASCARA_DATA);
	    				if (dataTerminoDate == null){
	    					arquivoSaida.write((linha + ";Data de Término (dat_conclusao_original) inválida\n").getBytes(Dominios.ENCODING_DEFAULT));
		    				arquivoSaida.flush();
		    				continue;
	    				} else {
	    					//valida se a data de ínicio é posterior a data de termino
	    					if (dataInicioDate != null && dataInicioDate.after(dataTerminoDate)){
	    						arquivoSaida.write((linha + ";Data de Término (dat_conclusao_original) inferior a Data de Início (dat_inicio_empreendimento)\n").getBytes(Dominios.ENCODING_DEFAULT));
			    				arquivoSaida.flush();
			    				continue;
	    					}
	    				}
	    			}
	    			
	    			BigDecimal valorPrevistoFormatado = null;
	    			if (valorPrevisto != null && !valorPrevisto.equals(STRING_VAZIO)) {
	    				if (!Util.ehValor(valorPrevisto)){
	    					arquivoSaida.write((linha + ";O Valor previsto (custo) não está no formato 0.00\n").getBytes(Dominios.ENCODING_DEFAULT));
		    				arquivoSaida.flush();
		    				continue;
	    				} else {
	    					valorPrevistoFormatado = new BigDecimal(Double.valueOf(valorPrevisto).doubleValue());
	    					if (valorPrevistoFormatado.doubleValue() < 0){
	    						arquivoSaida.write((linha + ";O Valor previsto (custo) não pode ser menor que zero\n").getBytes(Dominios.ENCODING_DEFAULT));
			    				arquivoSaida.flush();
			    				continue;
	    					}
	    					
	    				}
    				}
	    			
	    			if (codUFIBGE == null || codUFIBGE.equals(STRING_VAZIO)){
	    				arquivoSaida.write((linha + ";Código da cidade da Copa (idn_municipio) não informado\n").getBytes(Dominios.ENCODING_DEFAULT));
	    				arquivoSaida.flush();
	    				continue;
	    			}
	    			
	    			//Cria o item e seta os valores
	    			ItemEstruturaIett iett = new ItemEstruturaIett();
	    			iett.setEstruturaEtt(estruturaEttPadrao);
	    			
	    			ItemEstruturaIett itemEstruturaIettPai = null;
	    			if (hashMapItensPais.containsKey(codUFIBGE)){
	    				itemEstruturaIettPai = (ItemEstruturaIett) hashMapItensPais.get(codUFIBGE);
	    			} else {
	    				itemEstruturaIettPai = itemEstruturaDao.getItemEstruturaIett(codUFIBGE, estruturaEttPadrao.getEstruturaEtt().getCodEtt(), NOME_ITEM_PAI);
	    				hashMapItensPais.put(codUFIBGE, itemEstruturaIettPai);
	    			}
	    			
	    			if (itemEstruturaIettPai == null){
	    				arquivoSaida.write((linha + ";Não foi possível identificar o item " + NOME_ITEM_PAI + " para a UF da Cidade da Copa (cod_uf_ibge)\n").getBytes(Dominios.ENCODING_DEFAULT));
	    				arquivoSaida.flush();
	    				countRegistrosRejeitadosPorUF++;
	    				continue;
	    			}
	    			
	    			iett.setItemEstruturaIett(itemEstruturaIettPai);
	    			//Valores default do item   			
	    			iett.setIndAtivoIett(Pagina.SIM);
	    			iett.setIndBloqPlanejamentoIett(Pagina.NAO);
	    			iett.setIndCriticaIett(Pagina.NAO);
	    			iett.setIndMonitoramentoIett(Pagina.NAO);
	    			iett.setNivelIett(itemEstruturaIettPai.getNivelIett() + 1);
	    			iett.setDataInclusaoIett(Data.getDataAtual());
	    			iett.setUsuarioUsuByCodUsuIncIett(usuarioUsuPadrao);
	                iett.setDataInclusaoIett(Data.getDataAtual());
	    			
	    			//Campos do arquivo empreendimentos.csv
	                if (codItemExterno != null && !codItemExterno.equals(STRING_VAZIO)){
	                	iett.setSiglaIett(codItemExterno);
	                }
	                
	    			if (nome != null && !nome.equals(STRING_VAZIO)){
	    				iett.setNomeIett(nome);
	    			}
	    			
	    			if (descricao != null && !descricao.equals(STRING_VAZIO)){
	    				iett.setDescricaoR2(descricao);
	    			}
	    			
	    			iett.setDataInicioIett(dataInicioDate);
	    			
	    			iett.setDataTerminoIett(dataTerminoDate);
	    			
	    			if (orgaoResponsavel != null && !orgaoResponsavel.equals(STRING_VAZIO)){
	    				//orgao informado no arquivo de importacao
	    				OrgaoOrg orgaoOrg = null;
	    				orgaoOrg = orgaoDao.getOrgaoOrgByDescricao(orgaoResponsavel);
	    				if (orgaoOrg != null){
	    					//se o orgao != null então o orgao passado no arquivo ja esta cadastrado no e-car
		    				iett.setOrgaoOrgByCodOrgaoResponsavel1Iett(orgaoOrg);
		    			} else {
		    				//se o orgao == null então o órgão passado não está cadastrado no e-car
		    				//seta o orgao para o padrão sem correspondente
		    				iett.setOrgaoOrgByCodOrgaoResponsavel1Iett(orgaoOrgPadraoSemCorrespondente);
		    			}
	    			} else {
	    				//se nao foi informado o orgao no arquivo de importacao
	    				//seta o orgao para o padrao nao selecionado
	    				iett.setOrgaoOrgByCodOrgaoResponsavel1Iett(orgaoOrgPadraoNaoInformado);
	    			}
	    			
	    			iett.setValPrevistoFuturoIett(valorPrevistoFormatado);
	    			
	    			iett.setSituacaoSit(situacaoSitPadrao);
	    			
	    			if (tipoEmpreendimento != null && !tipoEmpreendimento.equals(STRING_VAZIO)){
	    				//Tipo - dsc_tipo_empreendimento - descricaoR4
						iett.setDescricaoR4(tipoEmpreendimento);
	    			}
	    			
	    			if (subTipoEmpreendimento != null && !subTipoEmpreendimento.equals(STRING_VAZIO)){
	    				//Sub-Tipo - dsc_subtipo - descricaoR5
	    				iett.setDescricaoR5(subTipoEmpreendimento);
	    			}
	    			//Salva o item
	    	        session.save(iett);
	   			
	    			//gravar permissão para o grupo do PACInter 
	    			ItemEstrutUsuarioIettus itemEstrutUsuario = new ItemEstrutUsuarioIettus();
	    	
	    			itemEstrutUsuario.setItemEstruturaIett(iett);
	    			itemEstrutUsuario.setItemEstruturaIettOrigem(iett);
	    			itemEstrutUsuario.setCodTpPermIettus(ControlePermissao.PERMISSAO_GRUPO);
	    			
	    			itemEstrutUsuario.setSisAtributoSatb(sisAtributoSatbGrupoAcessoPadrao);
	    	
	    			//gerar nova permissão de acesso com as permissões de leitura, alteração, exclusão e ler parecer.
	    			itemEstrutUsuario.setIndLeituraIettus(Pagina.SIM);
	    			itemEstrutUsuario.setIndEdicaoIettus(Pagina.SIM);
	    			itemEstrutUsuario.setIndExcluirIettus(Pagina.SIM);
	    			itemEstrutUsuario.setIndLeituraParecerIettus(Pagina.SIM);
	    				    			
	    			itemEstrutUsuario.setIndAtivMonitIettus(Pagina.NAO);
	    			itemEstrutUsuario.setIndDesatMonitIettus(Pagina.NAO);
	    			itemEstrutUsuario.setIndBloqPlanIettus(Pagina.NAO);
	    			itemEstrutUsuario.setIndDesblPlanIettus(Pagina.NAO);
	    			itemEstrutUsuario.setIndInfAndamentoIettus(Pagina.NAO);
	    			itemEstrutUsuario.setIndEmitePosIettus(Pagina.NAO);
	    			itemEstrutUsuario.setIndProxNivelIettus(Pagina.NAO);
	    			itemEstrutUsuario.setDataInclusaoIettus(Data.getDataAtual());
	    			//Salva a permissão padrão para o grupo do PACInter
	    			session.save(itemEstrutUsuario);
	    			countRegistrosImportados++;
	    			
	    			if (hashMapRegistrosPorCidadeImportados.containsKey(codUFIBGE)){
	    				Integer qtdArquivos = (Integer) hashMapRegistrosPorCidadeImportados.get(codUFIBGE);
	    				qtdArquivos = qtdArquivos + 1;
	    				hashMapRegistrosPorCidadeImportados.put(codUFIBGE, qtdArquivos);
	    			} else {
	    				hashMapRegistrosPorCidadeImportados.put(codUFIBGE, new Integer(1));
	    			}
	    			
	    		}
    		}
    		
    		arquivoSaida.write(("Registros lidos do arquivo: " + countRegistrosArquivo).getBytes(Dominios.ENCODING_DEFAULT));
    		arquivoSaida.write(("\nRegistros importados: " + countRegistrosImportados).getBytes(Dominios.ENCODING_DEFAULT));
    		
    		arquivoSaida.write(("\nRegistros por UF do arquivo: " + hashMapRegistrosPorCidadeArquivo.toString()).getBytes(Dominios.ENCODING_DEFAULT));
    		arquivoSaida.write(("\nRegistros por UF importados: " + hashMapRegistrosPorCidadeImportados.toString()).getBytes(Dominios.ENCODING_DEFAULT));
    		
    		arquivoSaida.write(("\nRegistros rejeitados UF: " + countRegistrosRejeitadosPorUF).getBytes(Dominios.ENCODING_DEFAULT));
    		
            arquivoSaida.flush();
    		
    	} catch (FileNotFoundException e) {
    		this.logger.error(e);
 			throw new ECARException("Arquivo " + configuracaoCfg.getRaizUpload() + "/importacaoDadosPacInter/empreendimentos_copa2014_UF_IBGE.csv não encontrado."); 
		} catch (IOException e) {
    		this.logger.error(e);
    		throw new ECARException("Erro ao ler o arquivo " + configuracaoCfg.getRaizUpload() + "/importacaoDadosPacInter/empreendimentos_copa2014_UF_IBGE.csv.");
		}
    }
    
    /**
     * 
     * @param stringComAspas
     * @return
     */
    private String formatarStringSemAspas(String s){
    	try {
    		if (s != null && !s.equals("")){
        		if (s.length() >= 2){
        			if (s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"'){
        				s = s.substring(1, s.length() - 1);
        			}
        		}
        	}
        	return s;
    	} catch (ArrayIndexOutOfBoundsException e) {
    		System.out.println(s);
    		System.out.println(e);
    		return s;
			// TODO: handle exception
		}
    	
    }
    
    /**
     * Valida a sequencia dos campos do cabecalho
     * @param cabecalho
     */
    private void validarSequenciaCamposCabecalho(String cabecalho) throws ECARException{
    	String[] campos = cabecalho.split(";");
    	/*
		Mapeamento dos campos do arquivo empreendimentos.csv
		Código - idn_empreendimento
		Nome - dsc_titulo
		Descrição - dsc_descricao
		Data de Início - dat_inicio_empreendimento	
		Data de Término - dat_conclusao_original
		Orgão Responsável - dsc_executor_controle	
		Valor Previsto - custo
		Situação - dsc_estagio (será considerado valor fixo 'Importado do PACInter')
		Tipo - dsc_tipo_empreendimento - descricaoR4
		Sub-Tipo - dsc_subtipo - descricaoR5
		Código da Cidade - (ver nome do campo no PacInter)
    	*/
		if (campos[0] == null || !campos[0].trim().equals("\"idn_empreendimento\"")){
			throw new ECARException("Arquivo não pode ser processado pois a ordem dos campos difere da esperada.");
		}
		if (campos[1] == null || !campos[1].trim().equals("\"dsc_titulo\"")){
			throw new ECARException("Arquivo não pode ser processado pois a ordem dos campos difere da esperada.");
		}
		if (campos[2] == null || !campos[2].trim().equals("\"dsc_descricao\"")){
			throw new ECARException("Arquivo não pode ser processado pois a ordem dos campos difere da esperada.");
		}
		if (campos[3] == null || !campos[3].trim().equals("\"dat_inicio_empreendimento\"")){
			throw new ECARException("Arquivo não pode ser processado pois a ordem dos campos difere da esperada.");
		}
		if (campos[4] == null || !campos[4].trim().equals("\"dat_conclusao_original\"")){
			throw new ECARException("Arquivo não pode ser processado pois a ordem dos campos difere da esperada.");
		}
		if (campos[5] == null || !campos[5].trim().equals("\"dsc_executor_controle\"")){
			throw new ECARException("Arquivo não pode ser processado pois a ordem dos campos difere da esperada.");
		}
		if (campos[6] == null || !campos[6].trim().equals("\"custo\"")){
			throw new ECARException("Arquivo não pode ser processado pois a ordem dos campos difere da esperada.");
		}
		if (campos[7] == null || !campos[7].trim().equals("\"dsc_estagio\"")){
			throw new ECARException("Arquivo não pode ser processado pois a ordem dos campos difere da esperada.");
		}
		if (campos[8] == null || !campos[8].trim().equals("\"dsc_tipo_empreendimento\"")){
			throw new ECARException("Arquivo não pode ser processado pois a ordem dos campos difere da esperada.");
		}
		if (campos[9] == null || !campos[9].trim().equals("\"dsc_subtipo\"")){
			throw new ECARException("Arquivo não pode ser processado pois a ordem dos campos difere da esperada.");
		}
		if (campos[10] == null || !campos[10].trim().equals("\"cod_uf_ibge\"")){
			throw new ECARException("Arquivo não pode ser processado pois a ordem dos campos difere da esperada.");
		}
    }
       
}