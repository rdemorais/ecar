package ecar.intercambioDados.exportacao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import comum.util.ConstantesECAR;
import comum.util.FileUpload;
import comum.util.Pagina;

import ecar.dao.CorDao;
import ecar.dao.SisTipoExibicGrupoDao;
import ecar.dao.SituacaoDao;
import ecar.exception.ECARException;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.intercambioDados.recurso.txt.RegistroTXT;
import ecar.login.SegurancaECAR;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.Cor;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.FuncaoSisAtributo;
import ecar.pojo.IConfiguracaoAtributoLivre;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.PontoCriticoPtc;
import ecar.pojo.PontocriticoCorPtccor;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.SituacaoSit;
import ecar.util.Dominios;

public abstract class ExportaFuncaoConfiguravelAtributoLivre extends ExportaFuncao {

	
	private static final String ESTADO = "Estado";
	private static final String ANTECEDENCIA = "Antecedência";
	private static final String FREQUENCIA_ENVIO = "Frequência de Envio";
	private static final String ATIVO = "Ativo";
	
	@Override
	protected List<IRegistro> montarConteudo(List<ItemEstruturaIett> listaItensEstruturaExportacao, List<AcompReferenciaItemAri> listaArisEstruturaExportacao, ConfiguracaoCfg configuracao,List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref, SegurancaECAR segurancaECAR) throws ECARException {
		
		List<IConfiguracaoAtributoLivre> listaObjetosNegocio = identificarObjeto (listaItensEstruturaExportacao);
		
		List<IRegistro> registros = montarConteudoRegistro (listaObjetosNegocio,configuracao,colunas,null,Dominios.NAO);
		
		return registros;
	}


	/**
	 * Monta o conteúdo do arquivo.
	 *  
	 * @param estruturaEtt
	 * @param listaObjetosNegocio
	 * @param configuracao
	 * @param colunas
	 * @param acompReferenciaAref
	 * @param relatorioTipoMonitoramento
	 * @return
	 * @throws ECARException
	 */
	protected List<IRegistro> montarConteudoRegistro(List<IConfiguracaoAtributoLivre> listaObjetosNegocio, ConfiguracaoCfg configuracao,List<ObjetoEstrutura> colunas,AcompReferenciaAref acompReferenciaAref,String relatorioTipoMonitoramento) throws ECARException{

		
		IRegistro registro;
		List<IRegistro> listaRegistros = new ArrayList<IRegistro>();
		String valorAtributoStr;
		String separadorMultivalor = configuracao.getSeparadorCampoMultivalor();
		String separadorCampos = configuracao.getSeparadorArqTXT();
		
		List<Cor> listaCoresTotal = new CorDao(null).listar(Cor.class, new String[]{"ordemCor","asc"});

		for (IConfiguracaoAtributoLivre objetoNegocio : listaObjetosNegocio) {

			StringBuffer objetoNegocioStr = new StringBuffer();
			
			ItemEstruturaIett itemEstruturaProprietario = objetoNegocio.itemProprietario();
			
			//Inicio Primeiro campo
			if (itemEstruturaProprietario.getItemEstruturaIett()!= null){
				objetoNegocioStr.append(itemEstruturaProprietario.getItemEstruturaIett().getCodIett());
				objetoNegocioStr.append(separadorCampos);
			} else {
				objetoNegocioStr.append(separadorCampos);
			}
			//Fim Primeiro campo
			
			//Inicio Segundo campo
			objetoNegocioStr.append(itemEstruturaProprietario.getCodIett());
			//Fim Segundo campo

			imprimirCampoCodigoObjetoNegocio (objetoNegocioStr,objetoNegocio,configuracao);
			
			for (ObjetoEstrutura atributo : colunas) {
			
				 SisGrupoAtributoSga grupoAtributoLivre = atributo.iGetGrupoAtributosLivres();
				 
				// Se for atributo livre pega valor pelo ItemEstruturaSisAtributoIettSatbs
				if(grupoAtributoLivre != null && (grupoAtributoLivre.getIndAtivoSga()!=null &&  grupoAtributoLivre.getIndAtivoSga().equals("S"))) {
					
					if(objetoNegocio.getListaAtributosLivres() != null) {
						objetoNegocioStr.append(separadorCampos);
		    	    	String separadorParaCamposMulti = "";
		    	    	String separadorParaCamposCheck = "";
						//Iterador com os atributos livres do ítem em questao
		    	    	Iterator<FuncaoSisAtributo> itAtribLivres = objetoNegocio.getListaAtributosLivres().iterator();
		    	    	
		    	    	//Para cada atributo livre do ítem, verifica-se se este corresponde 
		    	    	//ao atributo que esta sendo tratado
		    	    	while(itAtribLivres.hasNext()){
		    	    		
		    	    		FuncaoSisAtributo atributoLivreObjetoNegocio = (FuncaoSisAtributo) itAtribLivres.next();
		    	    		//Iterator com os atributos livres associados ao atributo em questão
		    	    		Iterator<SisAtributoSatb> sisAtributosSatbIt = atributo.iGetGrupoAtributosLivres().getSisAtributoSatbs().iterator();
		    	    		
		    	    		String tipoCampo = grupoAtributoLivre.getSisTipoExibicGrupoSteg().getCodSteg().toString();
		    	    		
		    	    		while (sisAtributosSatbIt.hasNext()) {
		    	    			SisAtributoSatb sisAtributo = (SisAtributoSatb) sisAtributosSatbIt.next();
		    	    			
			    	    		if( (sisAtributo).equals(atributoLivreObjetoNegocio.getSisAtributoSatb()) ) {
			    	    			//Caso seja um atributo tipo texto
			    	    			if(atributoLivreObjetoNegocio.getInformacao() != null) {
			    	    				if (relatorioTipoMonitoramento.equals(Dominios.NAO)){
			    	    					
			    	    					//Verifica se o grupo é do tipo imagem, se for obtem apenas o nome do arquivo.
			    	    					if (tipoCampo.equals(SisTipoExibicGrupoDao.IMAGEM)){
			    	    						
			    	    						String arquivo = atributoLivreObjetoNegocio.getInformacao();
			    	    						String nomeArquivo = "";
			    	    						
			    	    						if(arquivo.lastIndexOf("\\") != -1) {
			    	    		           			nomeArquivo = arquivo.substring(arquivo.lastIndexOf("\\") + 1); 
			    	    		           		} else if (arquivo.lastIndexOf("/") != -1){     
			    	    		           	       	nomeArquivo = arquivo.substring(arquivo.lastIndexOf("/") + 1);
			    	    		           		}
			    	    						
			    	    						valorAtributoStr = retirarCaracteresControle(FileUpload.getNomeArquivoOriginal(nomeArquivo));
			    	    					} else {
			    	    						valorAtributoStr = retirarCaracteresControle(atributoLivreObjetoNegocio.getInformacao());
			    	    					}
			    	    					objetoNegocioStr.append(separadorParaCamposMulti);
			    	    					objetoNegocioStr.append(valorAtributoStr);
			    	    					separadorParaCamposMulti = separadorMultivalor;
			    	    				} 
			    	    			//Caso seja atributo tipo check, radio ou select
			    	    			} else if(atributoLivreObjetoNegocio.getSisAtributoSatb().getDescricaoSatb() != null) {
			    	    				if (relatorioTipoMonitoramento.equals(Dominios.NAO)){
			    	    					valorAtributoStr = retirarCaracteresControle(atributoLivreObjetoNegocio.getSisAtributoSatb().getDescricaoSatb());
			    	    					objetoNegocioStr.append(separadorParaCamposCheck);
			    	    					objetoNegocioStr.append(valorAtributoStr);
			    	    					separadorParaCamposCheck = separadorMultivalor;
			    	    				} 
			    	    				
			    	    			//Caso não tenha valor associado ao atributo
			    	    			} else {
			    	    				objetoNegocioStr.append(Dominios.STRING_VAZIA);
			    	    			}
				    	    			
			    	    			//interrompe o laço pois já encontrou o sisAtributo procurado.
			    	    			break;
			    	    		}
		    	    		}
		    	    	}
		    	    	separadorParaCamposMulti = "";
		    	    	separadorParaCamposCheck = "";
		        	}
					
				} else if (atributo.iGetNome().equals("situacaoSit") && objetoNegocio instanceof ItemEstruturaIett) {
						
						objetoNegocioStr.append(separadorCampos);
						ItemEstruturaIett itemEstruturaIett = (ItemEstruturaIett)objetoNegocio;
					
						if (relatorioTipoMonitoramento.equals(Dominios.NAO)){
							String nomeAtributo = "";
							List situacoes = new SituacaoDao(null).getSituacaoByEstrutura(itemEstruturaIett.getEstruturaEtt(), new String[] {"descricaoSit","asc"});
				        	Iterator it = situacoes.iterator();
				        	while(it.hasNext() && nomeAtributo.equals("")){
				        		SituacaoSit situacao = (SituacaoSit) it.next();
				        		if(situacao!= null && itemEstruturaIett.getSituacaoSit()!= null && situacao.getDescricaoSit().equals(itemEstruturaIett.getSituacaoSit().getDescricaoSit())) {
				        			nomeAtributo = situacao.getDescricaoSit(); 
				        		}
				        	}
				        	
				        	if(!nomeAtributo.equals(Dominios.STRING_VAZIA)) {
				        		valorAtributoStr = retirarCaracteresControle(nomeAtributo);
				        		objetoNegocioStr.append(valorAtributoStr);
				        	}
						}
						
						
				
				} else if(atributo.iGetNome().equals("nivelPlanejamento") && objetoNegocio instanceof ItemEstruturaIett) {
					
						objetoNegocioStr.append(separadorCampos);
						ItemEstruturaIett itemEstruturaIett = (ItemEstruturaIett)objetoNegocio;
					
						if (relatorioTipoMonitoramento.equals(Pagina.NAO)){
							if(itemEstruturaIett.getItemEstruturaNivelIettns() != null && !itemEstruturaIett.getItemEstruturaNivelIettns().isEmpty()){
						    	Iterator<SisAtributoSatb> itNiveis = itemEstruturaIett.getItemEstruturaNivelIettns().iterator();
						    	while(itNiveis.hasNext()){
						    		SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
						    		valorAtributoStr = retirarCaracteresControle(nivel.getDescricaoSatb());
						    		objetoNegocioStr.append(valorAtributoStr);
						    	}
					    	}
						}
						
						
				} else if (atributo.iGetNome().equals("pontoCriticoCorPtccores")  && objetoNegocio instanceof PontoCriticoPtc) {
					
					PontoCriticoPtc ptc = (PontoCriticoPtc)objetoNegocio;
					
					Set<PontocriticoCorPtccor> listaPtcCor = ptc.getPontoCriticoCorPtccores();
					
					for (Cor cor : listaCoresTotal) {
						PontocriticoCorPtccor ptcCor = identificarCorListaCorPontoCritico(cor,listaPtcCor);
						
						if (ptcCor != null && ptcCor.getIndAtivoEnvioEmailPtccor().equals(Dominios.SIM)){
							objetoNegocioStr.append (configuracao.getSeparadorArqTXT());
							objetoNegocioStr.append(ptcCor.getCor().getNomeCor());
							objetoNegocioStr.append (configuracao.getSeparadorArqTXT());
							objetoNegocioStr.append (ptcCor.getAntecedenciaPrimEmailPtccor());
							objetoNegocioStr.append (configuracao.getSeparadorArqTXT());
							objetoNegocioStr.append (ptcCor.getFrequenciaEnvioEmailPtccor());
							objetoNegocioStr.append (configuracao.getSeparadorArqTXT());
							objetoNegocioStr.append (ptcCor.getIndAtivoEnvioEmailPtccor());
						} else {
							objetoNegocioStr.append (configuracao.getSeparadorArqTXT());
							objetoNegocioStr.append(ConstantesECAR.HIFEN);
							objetoNegocioStr.append (configuracao.getSeparadorArqTXT());
							objetoNegocioStr.append (ConstantesECAR.HIFEN);
							objetoNegocioStr.append (configuracao.getSeparadorArqTXT());
							objetoNegocioStr.append (ConstantesECAR.HIFEN);
							objetoNegocioStr.append (configuracao.getSeparadorArqTXT());
							objetoNegocioStr.append (ConstantesECAR.HIFEN);
						}
					}
					
				} else {
		        	if(atributo.iGetIndOpcional() == null || atributo.iGetIndOpcional().booleanValue() == false){
		    			// Mantis 6514: para atributos nï¿½o opcionais verificar pelo campo "sequencia de apresentacao em telas de informaï¿½ï¿½o"
		        		if(!atributo.iGetNome().equals("indAtivoIett") && !atributo.iGetNome().equals("dataInicioMonitoramentoIett") && atributo.iGetSequenciaCampoEmTela() != null && atributo.iGetSequenciaCampoEmTela().intValue() != 0){
		        			// Se for campo fixo pega valor direto do atributo
		        			objetoNegocioStr.append(separadorCampos);
		        			if (relatorioTipoMonitoramento.equals(Pagina.NAO)){
		        				
		        				valorAtributoStr = Pagina.trocaNull(retirarCaracteresControle(atributo.iGetValor(objetoNegocio)));
					    		
		        				objetoNegocioStr.append(valorAtributoStr);
							}
		        			
		        		}
		        	} else if(!atributo.iGetNome().equals("indAtivoIett") && !atributo.iGetNome().equals("dataInicioMonitoramentoIett")){ //&& atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true){	
		        		// Se for campo fixo pega valor direto do atributo
		        		objetoNegocioStr.append(separadorCampos);
		        		if (relatorioTipoMonitoramento.equals(Pagina.NAO)){
		        			valorAtributoStr =  Pagina.trocaNull(retirarCaracteresControle(atributo.iGetValor(objetoNegocio)));
		        			objetoNegocioStr.append(valorAtributoStr);
		        		}
		        		
		        	}
			   } 	
			}

			registro = new RegistroTXT(objetoNegocioStr.toString());
			
			listaRegistros.add(registro);
		
		}
		
		return listaRegistros;
	}


	/**
	 * Verifica se uma cor está na lista de cores do Ponto Critico.
	 * @param cor
	 * @param listaPtcCor
	 * @return
	 */
	private PontocriticoCorPtccor identificarCorListaCorPontoCritico(Cor cor, Set<PontocriticoCorPtccor> listaPtcCor) {
		
		PontocriticoCorPtccor corPtc = null;
		
		for (PontocriticoCorPtccor pontocriticoCorPtccor : listaPtcCor) {
			if (cor.equals(pontocriticoCorPtccor.getCor())){
				corPtc = pontocriticoCorPtccor;
				break;
			}
		}
		
		return corPtc;
	}


	@Override
	protected IRegistro montarSegundoHeader(EstruturaEtt estruturaEtt,List<ItemEstruturaIett> listaItensEstruturaExportacao, String labelFuncaoAba, ConfiguracaoCfg configuracao,List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref) throws ECARException {
		
		StringBuffer headerStr = new StringBuffer(); 
		IRegistro header; 
		
		//Inicio Primeiro campo
		headerStr.append(ConstantesECAR.ID_PAI);
		
		if (estruturaEtt.getEstruturaEtt()!= null){
			headerStr.append(ConstantesECAR.UNDERLINE);
			headerStr.append(estruturaEtt.getEstruturaEtt().getNomeEtt());
		}
		
		headerStr.append(configuracao.getSeparadorArqTXT());
		//Fim Primeiro campo

		//Inicio Segundo campo
		headerStr.append(ConstantesECAR.ID);
		headerStr.append(ConstantesECAR.UNDERLINE);
		headerStr.append(estruturaEtt.getNomeEtt());
		//Fim Segundo campo

		imprimirCampoHeaderObjetoNegocio(headerStr, labelFuncaoAba, configuracao);
		
		//Inicio dos campos(ObjetoEstrutura) configurados para serem exibidos na estrutura. 
		for (ObjetoEstrutura atributo : colunas) {
			
			String valorAtributoStr;
			//Se opcional é falso é porque o atributo é obrigatório.
        	if(atributo.iGetIndOpcional() == null || atributo.iGetIndOpcional().booleanValue() == false){
    			// Mantis 6514: para atributos não opcionais verificar pelo campo "sequencia de apresentacao em telas de informaï¿½ï¿½o"
        		if(!atributo.iGetNome().equals("indAtivoIett") && !atributo.iGetNome().equals("dataInicioMonitoramentoIett") && atributo.iGetSequenciaCampoEmTela() != null && atributo.iGetSequenciaCampoEmTela().intValue() != 0){
        			headerStr.append (configuracao.getSeparadorArqTXT());
        			if (atributo.iGetLabel() != null && !"".equals(atributo.iGetLabel())){
        				valorAtributoStr = retirarCaracteresControle(atributo.iGetLabel());
        				headerStr.append (valorAtributoStr);
        			}
        			else{
        				valorAtributoStr = retirarCaracteresControle(atributo.iGetNome());
        				headerStr.append (valorAtributoStr);
        			}
        		}
        	} else if(atributo.iGetNome().equals("nivelPlanejamento")){
        		SisGrupoAtributoSga grupoAtributo = configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan();
        		valorAtributoStr = retirarCaracteresControle(atributo.iGetLabel()); //grupoAtributo.getDescricaoSga());
        		headerStr.append (configuracao.getSeparadorArqTXT());
        		headerStr.append (valorAtributoStr);
        	} else if (atributo.iGetNome().equals("pontoCriticoCorPtccores"))  {
        		List<Cor> listaCores = new CorDao(null).listar(Cor.class, new String[]{"ordemCor","asc"});
        		
        		for (int i = 1;i <=listaCores.size();i++) {
        			headerStr.append (configuracao.getSeparadorArqTXT());
        			headerStr.append (ExportaFuncaoConfiguravelAtributoLivre.ESTADO+" "+i);
        			headerStr.append (configuracao.getSeparadorArqTXT());
        			headerStr.append (ExportaFuncaoConfiguravelAtributoLivre.ANTECEDENCIA+" "+i);
        			headerStr.append (configuracao.getSeparadorArqTXT());
        			headerStr.append (ExportaFuncaoConfiguravelAtributoLivre.FREQUENCIA_ENVIO+" "+i);
        			headerStr.append (configuracao.getSeparadorArqTXT());
        			headerStr.append (ExportaFuncaoConfiguravelAtributoLivre.ATIVO+" "+i);
				}
			} else if(!atributo.iGetNome().equals("indAtivoIett") && !atributo.iGetNome().equals("dataInicioMonitoramentoIett")){ //&& atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true){
				// escreve o separador entre cada campo
        		headerStr.append (configuracao.getSeparadorArqTXT());
        		if (atributo.iGetLabel() != null && !atributo.iGetLabel().equals(Dominios.STRING_VAZIA)){
        			valorAtributoStr = retirarCaracteresControle(atributo.iGetLabel());
        			headerStr.append (valorAtributoStr);
    			} else {
    				valorAtributoStr = retirarCaracteresControle(atributo.iGetNome());
    				headerStr.append (valorAtributoStr);
    			}
        	}
		}
		//Fim dos campos(ObjetoEstrutura) configurados para serem exibidos na estrutura.
		
		header = new RegistroTXT(headerStr.toString());
		
		return header;

	}

	protected abstract List<IConfiguracaoAtributoLivre> identificarObjeto(List<ItemEstruturaIett> listaItensEstruturaExportacao);

	protected abstract void imprimirCampoCodigoObjetoNegocio(StringBuffer objetoNegocioStr, IConfiguracaoAtributoLivre objetoNegocio,ConfiguracaoCfg configuracao);
	
	protected abstract void imprimirCampoHeaderObjetoNegocio(StringBuffer headerStr, String labelFuncaoAba, ConfiguracaoCfg configuracao);

}
