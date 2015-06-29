/*
 * Created on 29/10/2004
 * 
 */
package ecar.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Util;

import ecar.enumerator.TipoOcorrencia;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.ConfigExecFinanCef;
import ecar.pojo.ConfigSisExecFinanCsef;
import ecar.pojo.ConfigSisExecFinanCsefv;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EfIettFonteTotEfieft;
import ecar.pojo.EfIettFonteTotEfieftPK;
import ecar.pojo.EfImportOcorrenciasEfio;
import ecar.pojo.EfItemEstContaEfiec;
import ecar.pojo.EfItemEstPrevisaoEfiep;
import ecar.pojo.EfItemEstPrevisaoEfiepPK;
import ecar.pojo.EfItemEstRealizadoEfier;
import ecar.pojo.EspecieEsp;
import ecar.pojo.ExercicioExe;
import ecar.pojo.FonteRecursoFonr;
import ecar.pojo.ImportacaoImp;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.RecursoRec;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/**
 * @author evandro, aleixo
 *
 */
public class ConfigSisExecFinanDao extends Dao{
	
	private static final int TAMANHO_CONTA_CONTABIL = 255;
	private static final int TAMANHO_SIGLA_SISTEMA = 6;
	private static final int TAMANHO_VALORES = 14;
	private static final int TAMANHO_QTDE_REGISTROS = 6;
	private static final String TR_HEADER = "00";
	private static final String TR_REGISTRO = "01";
	private static final String TR_REGISTRO_NAO_REVISTO = "02";
	private static final String TR_TRAILER = "99";
	private static final String FORMATO_DATAHORA = "ddMMyyyyHHmmss";
	private static final int TAMANHO_DATAHORA = 14;
	
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public ConfigSisExecFinanDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Retorna uma lista dos sistemas cadastrados em ConfigSisExecFinanCsef que são ativos e possuem versão.
	 * @return List
	 * @throws ECARException
	 */
	public List getSistemasAtivos() throws ECARException{
		ConfigSisExecFinanCsefvDao versaoDao = new ConfigSisExecFinanCsefvDao(null);
		
		List retorno = new ArrayList();
		
		List versoes = versaoDao.getCsefvOrderBySistema();
		
		if(versoes != null && !versoes.isEmpty()){
			Iterator itVersoes = versoes.iterator();
			while(itVersoes.hasNext()){
				ConfigSisExecFinanCsefv versao = (ConfigSisExecFinanCsefv) itVersoes.next();
				
				if(!retorno.contains(versao.getConfigSisExecFinanCsef())){
					retorno.add(versao.getConfigSisExecFinanCsef());
				}
			}
		}
		
		return retorno;
	}

	/**
	 * Gera o arquivo de exportação para integração financeira
	 * @param nomeArquivo
	 * @param codSistema
	 * @param mesAnoIni
	 * @param mesAnoFim
	 * @param configuracao
	 * @return String[]: posicao 0 --> caminho do arquivo gravado no servidor
	 * 					 posicao 1 --> nome do arquivo gerado
	 * @throws ECARException
	 */
	public String[] gerarArquivoExportacaoTxt(String nomeArquivo, String codSistema, String mesAnoIni, String mesAnoFim, ConfiguracaoCfg configuracao) throws ECARException{
		ItemEstruturaContaOrcamentoDao itemEstContaDao = new ItemEstruturaContaOrcamentoDao(null);
		//nomeArquivo = Util.trocarEspacoPorCaracter(nomeArquivo, "_");
		String caminho = configuracao.getRaizUpload() + configuracao.getUploadIntegracao();
		
		if(!caminho.endsWith("/"))
			caminho = caminho + "/";
		
		String caminhoCompleto = "";
		try{
        	String formato = "ddMMyyyyHHmmssSSS";
            SimpleDateFormat formatter = new SimpleDateFormat(formato);
            ConfigSisExecFinanCsef sistema = (ConfigSisExecFinanCsef) this.buscar(ConfigSisExecFinanCsef.class, Long.valueOf(codSistema));
            
            nomeArquivo += "_" + sistema.getSiglaCsef() + "_" + formatter.format(new Date()) + "_export.txt";
			caminhoCompleto = caminho + nomeArquivo;
 						
			File diretorio = new File(caminho);
			
			if(!diretorio.isDirectory())
				diretorio.mkdirs();

			FileOutputStream arquivo = new FileOutputStream(caminhoCompleto);
			
			String siglaSistema = sistema.getSiglaCsef();
			int tam = siglaSistema.length();
			for(int i = tam; i < TAMANHO_SIGLA_SISTEMA; i++){
				siglaSistema += " ";
			}
			String maIni[] = mesAnoIni.split("/");
			String maFim[] = mesAnoFim.split("/");
			String dataHoraGeracao = (new SimpleDateFormat(FORMATO_DATAHORA)).format(new Date());
			
			/*Gravar o HEADER - TR00 - no arquivo*/
			String header = TR_HEADER + siglaSistema + maIni[0] + maIni[1] + maFim[0] + maFim[1] + dataHoraGeracao + "\n";
			arquivo.write(header.getBytes(Dominios.ENCODING_DEFAULT));
			arquivo.flush();
			
			double[] totais = new double[6];
			
			for(int i = 0; i < 6; i++){
				totais[i] = 0;
			}
			
			long qtdeReg = 2; //TR00 e TR99 já contam!!!
			/*Verificar se existem contas em EfItemEstRealizadoEfier*/
			List contasEfiec = itemEstContaDao.getContasParaExportacao(maIni[0], maIni[1], maFim[0], maFim[1]);
			
			if(!contasEfiec.isEmpty()){
				Iterator itContas = contasEfiec.iterator();
				while(itContas.hasNext()){
					EfItemEstContaEfiec conta = (EfItemEstContaEfiec) itContas.next();
					String contaContabil = conta.getContaSistemaOrcEfiec();
					
					if(contaContabil != null && !"".equals(contaContabil)){
						tam = contaContabil.length();
						for(int i = tam; i < TAMANHO_CONTA_CONTABIL; i++){
							contaContabil += " ";
						}
						
						/* Registrar no arquivo um TR da conta para cada mes/ano do intervalo selecionado.
						 * Ou seja, se foi selecionado de 01/2006 a 05/2006, para cada conta deve-se escrever 
						 * no arquivo um:
						 * 
						 * 01Conta1 ... 012006
						 * 01Conta1 ... 022006
						 * ...
						 * 01Conta1 ... 052006
						 * 01Conta2 ... 012006
						 * 01Conta2 ... 022006
						 * ...
						 * 01Conta2 ... 052006
						 * .
						 * .
						 * .
						 * 01ContaN ... 012006
						 * 01ContaN ... 022006
						 * ...
						 * 01ContaN ... 052006
						 */
						
						int mes = Integer.parseInt(maIni[0]);
						int ano = Integer.parseInt(maIni[1]);
						int mesFim = Integer.parseInt(maFim[0]);
						int anoFim = Integer.parseInt(maFim[1]);
	
						/*Existe uma validação na tela que não permite gerar este arquivo com 
						 * uma data final menor que a data inicial, portanto basta testar
						 * considerando que a data inicial é menor ou igual a data final. */
						
						while(ano <= anoFim){
							if(ano < anoFim){
								while (mes <= 12){
									qtdeReg++;
									String registro = TR_REGISTRO + contaContabil + ((mes < 10) ? "0"+String.valueOf(mes) : String.valueOf(mes)) + ano + "\n";
									arquivo.write(registro.getBytes());
									arquivo.flush();
									mes++;
								}
								mes = 1;
								ano++;
							}
							else if (ano == anoFim) {
								while(mes <= mesFim){
									qtdeReg++;
									String registro = TR_REGISTRO + contaContabil + ((mes < 10) ? "0"+String.valueOf(mes) : String.valueOf(mes)) + ano + "\n";
									arquivo.write(registro.getBytes(Dominios.ENCODING_DEFAULT));
									arquivo.flush();
									mes++;
								}
								break; //Como ano == anoFim, quando terminar o loop dos meses, deve sair do loop mais externo.
							}
						}
					}
				}
			}
			
			/* Gravar TRAILER - TR99 - para totalização */
			String qtdeRegistros = Util.completarZerosEsquerda(Long.valueOf(qtdeReg), TAMANHO_QTDE_REGISTROS);
			String totVal1 = Util.formataNumeroDecimalParaExportacao(totais[0], TAMANHO_VALORES);
			String totVal2 = Util.formataNumeroDecimalParaExportacao(totais[1], TAMANHO_VALORES);
			String totVal3 = Util.formataNumeroDecimalParaExportacao(totais[2], TAMANHO_VALORES);
			String totVal4 = Util.formataNumeroDecimalParaExportacao(totais[3], TAMANHO_VALORES);
			String totVal5 = Util.formataNumeroDecimalParaExportacao(totais[4], TAMANHO_VALORES);
			String totVal6 = Util.formataNumeroDecimalParaExportacao(totais[5], TAMANHO_VALORES);
			
			String total = TR_TRAILER + qtdeRegistros + totVal1 + totVal2 + totVal3 + totVal4 + totVal5 + totVal6 + "\n";
			arquivo.write(total.getBytes(Dominios.ENCODING_DEFAULT));
			arquivo.flush();

			arquivo.close();
    	} catch (Exception e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
    	}
		
    	String[] retorno = {caminhoCompleto, nomeArquivo};
    	
		return retorno;
	}
	
	
	/**
	 * Verifica, segundo Anexo 1 do pacote de Integração Financeira, o arquivo conforme
	 * regras estabelecidas neste anexo.
	 * @param arquivoGravado
         * @param configuracao
         * @param usuarioLogado
         * @param request
         * @return List[]: Posição 0 --> form-beans dos registros válidos.
         * 				   Posição 1 --> ocorrências (EfImportOcorrenciasEfio) encontrados na crítica.
         * @throws ECARException
         * @throws Exception
	 */
	public List[] criticaArquivoImportado(File arquivoGravado, 
			ConfiguracaoCfg configuracao, 
			UsuarioUsu usuarioLogado, 
			HttpServletRequest request) throws ECARException, Exception {
		
		
		ItemEstruturaContaOrcamentoDao itemEstContaDao = new ItemEstruturaContaOrcamentoDao(null);
		ConfigSisExecFinanCsefvDao versaoDao = new ConfigSisExecFinanCsefvDao(null);
				
		//Dados Importacao
		ImportacaoImp dadosImportacao = new ImportacaoImp();
		dadosImportacao.setDataHoraImp(new Date());
		dadosImportacao.setNomeArquivoImp(arquivoGravado.getName());

		Date inicioPeriodo = null;
    	Date fimPeriodo = null;
		
		List registrosValidos = new ArrayList();
		List<EfImportOcorrenciasEfio> ocorrencias = new ArrayList<EfImportOcorrenciasEfio>();
		Long totalRegistros01 = 0L; 
		
		List sistemasExistentes = getSistemasAtivos();
		List contasOrcamentarias = itemEstContaDao.listar(EfItemEstContaEfiec.class, new String[] {"contaSistemaOrcEfiec", "asc"});
		
		ConfigSisExecFinanCsef sistemaValido = null;
		boolean existeSistema = false;
		
		//Inicialização do Total
		BigDecimal[] total = new BigDecimal[6];
		for(int i = 0; i < 6; i++)
			total[i] = new BigDecimal("0");
		
		if(contasOrcamentarias == null)
			contasOrcamentarias = new ArrayList();
		
		try{
			BufferedReader in = new BufferedReader (new FileReader(arquivoGravado));
			String linha="";
			
			//Dados para TR00
			String sistema = "";
			String mesIni = "";
			String anoIni = "";
			String mesFim = "";
			String anoFim = "";
			String dataHoraGeracao = "";
			String dataHoraCarga = "";
			
			long registrosLidos = 0;
			while ((linha=in.readLine()) != null) {
				String tr = "";
				try{
					tr = linha.substring(0, 2);
				}catch (StringIndexOutOfBoundsException siobe) {
					//Não conseguiu executar substring de 2 posições  na linha
					//ou seja, a linha tem zero ou só um caracter.
					relatarOcorrencia("TR deve ser válido - " + linha + " linha:" + registrosLidos, 
											ocorrencias,
											new TipoOcorrencia(TipoOcorrencia.TR_INVALIDO),
											dadosImportacao);
					//this.logger.error(siobe); //Não é necessário logar essa exceção
					continue;
				}
				
				if(TR_HEADER.equals(tr)){ //00
					
					registrosLidos++;
					
					sistema = linha.substring(2,8);
					mesIni = linha.substring(8,10);
					anoIni = linha.substring(10,14);
					mesFim = linha.substring(14,16);
					anoFim = linha.substring(16,20);
					dataHoraGeracao = linha.substring(20,34);
					
					inicioPeriodo = new SimpleDateFormat("MMyyyy").parse(mesIni + anoIni);
			    	fimPeriodo = new SimpleDateFormat("MMyyyy").parse(mesFim + anoFim);
					
					
					try{
						dataHoraCarga = linha.substring(34,48);
					}catch (StringIndexOutOfBoundsException siobe) {
						//DataHora Carga inválida: O Arquivo não foi gerado para importação!!!
						relatarOcorrencia("TR" + TR_HEADER + " linha:" + registrosLidos + " - Data/Hora da carga não informada.", 
											ocorrencias, 
											new TipoOcorrencia(TipoOcorrencia.DATA_HORA_CARGA_INEXISTENTE),
											dadosImportacao);
						//this.logger.error(siobe); //Não é necessário logar essa exceção
						continue;
					}
					//Verificar se o Sistema existe
					sistema = sistema.trim();
					Iterator itSis = sistemasExistentes.iterator();
					while(itSis.hasNext()){
						ConfigSisExecFinanCsef sis = (ConfigSisExecFinanCsef) itSis.next();
						if(sistema.equals(sis.getSiglaCsef())){
							sistemaValido = sis;
							existeSistema = true;
							break;
						}
					}
					if(!existeSistema){
						relatarOcorrencia("TR" + TR_HEADER + " linha:" + registrosLidos + " - Sistema inválido - " + sistema, 
											ocorrencias,
											new TipoOcorrencia(TipoOcorrencia.SISTEMA_INVALIDO),
											dadosImportacao);
					}
					
					validarMes(mesIni, ocorrencias, "TR" + TR_HEADER + " linha:" + registrosLidos + " - Mês inicial da solicitação", dadosImportacao);
					validarAno(anoIni, ocorrencias, "TR" + TR_HEADER + " linha:" + registrosLidos + " - Ano inicial da solicitação", dadosImportacao);
					validarMes(mesFim, ocorrencias, "TR" + TR_HEADER + " linha:" + registrosLidos + " - Mês final da solicitação", dadosImportacao);
					validarAno(anoFim, ocorrencias, "TR" + TR_HEADER + " linha:" + registrosLidos + " - Ano final da solicitação", dadosImportacao);
					validarDataHora(dataHoraGeracao, ocorrencias, "TR" + TR_HEADER + " linha:" + registrosLidos + " - Data/hora da Geração deve ser válida - " + dataHoraGeracao, dadosImportacao);
					validarDataHora(dataHoraCarga, ocorrencias, "TR" + TR_HEADER + " linha:" + registrosLidos + " - Data/hora da Carga deve ser válida - " + dataHoraCarga, dadosImportacao);
					
					dadosImportacao.setDataHoraGeracaoSisOrigem(new SimpleDateFormat("ddMMyyyyHHmmss").parse(dataHoraGeracao));
				}
				else if(TR_REGISTRO.equals(tr)){ //01
					
					totalRegistros01++;
					registrosLidos++;
					boolean houveOcorrencia = false;
					//Dados para TR01
					String conta = linha.substring(2,257).toUpperCase();
					String mesTrRegistro = linha.substring(257, 259);
					String anoTrRegistro = linha.substring(259,263);
					
					int pos = 263;
					int posFim = pos + TAMANHO_VALORES;
					int numeroValores = 6;
					String[] valores = new String[numeroValores];
					int cont = 0;
					while(cont < numeroValores){
						valores[cont] = linha.substring(pos, posFim);
						pos = posFim;
						posFim = posFim + TAMANHO_VALORES;
						cont++;
					}
					String indContabilidade = linha.substring(pos, pos + 1);

					String valor1Registro = valores[0].trim();
					String valor2Registro = valores[1].trim();
					String valor3Registro = valores[2].trim();
					String valor4Registro = valores[3].trim();
					String valor5Registro = valores[4].trim();
					String valor6Registro = valores[5].trim();

					//Verifica se existe a acao descrita na descrição da conta.
					final int ACAO = 3;
					boolean existeConta = false;
					String[] decompoeConta = conta.trim().split(" ");
					String projAtividade = decompoeConta[0];
					String recursoCta = decompoeConta[2];
					String fonteCta = decompoeConta[3];
					
					Query q = this.session.createQuery("from ItemEstruturaIett iett where iett.estruturaEtt.codEtt = :codEtt and trim( iett.siglaIett ) = :siglaIett and iett.indAtivoIett = 'S'");
					q.setLong("codEtt", ACAO);
					q.setString("siglaIett", projAtividade);
					
					//Se == 0, então a sigla da acao (projeto atividade) não existe.
					if(q.list().size() == 0) {
						
						relatarOcorrencia("TR" + TR_REGISTRO + " linha:" + registrosLidos + " - Item '" + projAtividade  + "' não encontrado - conta:" + conta, 
								ocorrencias,
								new TipoOcorrencia(TipoOcorrencia.ACAO_INEXISTENTE),
								dadosImportacao,
								conta);						
						houveOcorrencia = true;
						
					}
					
					//Verifica a existencia do recurso
					q = this.session.createQuery("from FonteRecursoFonr where trim( siglaFonr ) = :siglaFonr");
					q.setString("siglaFonr", recursoCta);
					
					if(q.list().size() == 0) {
						
						relatarOcorrencia("TR" + TR_REGISTRO + " linha:" + registrosLidos + " - Item '" + recursoCta  + "' não encontrado - conta:" + conta, 
								ocorrencias,
								new TipoOcorrencia(TipoOcorrencia.FONTE_INEXISTENTE),
								dadosImportacao,
								conta);						
						houveOcorrencia = true;	
					}
					
					//Verifica a existencia da fonte
					q = this.session.createQuery("from RecursoRec where trim( siglaRec ) = :siglaRec");
					q.setString("siglaRec", fonteCta);
					
					if(q.list().size() == 0) {
						
						relatarOcorrencia("TR" + TR_REGISTRO + " linha:" + registrosLidos + " - Item '" + fonteCta  + "' não encontrado - conta:" + conta, 
								ocorrencias,
								new TipoOcorrencia(TipoOcorrencia.RECURSO_INEXISTENTE),
								dadosImportacao,
								conta);						
						houveOcorrencia = true;
					}					
					
					//Se não houve nenhuma ocorrência nos passos anteriores, analisa a existência da conta.
					if(!houveOcorrencia) {
						//Verifica se a conta existe no Sistema.
						conta = conta.trim();
						existeConta = false;
						Iterator itConta = contasOrcamentarias.iterator();
						while(itConta.hasNext()){
							EfItemEstContaEfiec con = (EfItemEstContaEfiec) itConta.next();
							
							if(conta.trim().equals(con.getContaSistemaOrcEfiec().trim())){
								existeConta = true;
								break;
							}
						}
					
						if(!existeConta){
							relatarOcorrencia("TR" + TR_REGISTRO + " linha:" + registrosLidos + " - Conta não econtrada - conta:" + conta, 
												ocorrencias,
												new TipoOcorrencia(TipoOcorrencia.CONTA_INEXISTENTE),
												dadosImportacao,
												conta);
							houveOcorrencia = true;
						}
					}
										
					houveOcorrencia = houveOcorrencia || !validarMes(mesTrRegistro, ocorrencias, "TR" + TR_REGISTRO + " linha:" + registrosLidos + " - Mês", dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarAno(anoTrRegistro, ocorrencias, "TR" + TR_REGISTRO + " linha:" + registrosLidos + " - Ano", dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarSistemaMesAno(conta, sistemaValido, mesTrRegistro, anoTrRegistro, TR_REGISTRO + " linha:" + registrosLidos, ocorrencias, dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarValor(valor1Registro, ocorrencias, TR_REGISTRO + " linha:" + registrosLidos, configuracao.getFinanceiroDescValor1Cfg(), dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarValor(valor2Registro, ocorrencias, TR_REGISTRO + " linha:" + registrosLidos, configuracao.getFinanceiroDescValor2Cfg(), dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarValor(valor3Registro, ocorrencias, TR_REGISTRO + " linha:" + registrosLidos, configuracao.getFinanceiroDescValor3Cfg(), dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarValor(valor4Registro, ocorrencias, TR_REGISTRO + " linha:" + registrosLidos, configuracao.getFinanceiroDescValor4Cfg(), dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarValor(valor5Registro, ocorrencias, TR_REGISTRO + " linha:" + registrosLidos, configuracao.getFinanceiroDescValor5Cfg(), dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarValor(valor6Registro, ocorrencias, TR_REGISTRO + " linha:" + registrosLidos, configuracao.getFinanceiroDescValor6Cfg(), dadosImportacao);
					
					if(!"A".equals(indContabilidade) && !"F".equals(indContabilidade)){
						relatarOcorrencia("TR" + TR_REGISTRO  + " linha:" + registrosLidos + " - Flag indicador de contabilidade encerrada inválido - " + indContabilidade, 
											ocorrencias,
											new TipoOcorrencia(TipoOcorrencia.FLAG_CONTABILIDADE_INVALIDO),
											dadosImportacao);
						houveOcorrencia = true;
					}
					
					
					if(!houveOcorrencia && !"".equals(dataHoraCarga)){
						//Incluir dados na lista de registros válidos.
						//Dados que entram neste IF estão todos válidos!!!
						EfItemEstRealizadoEfier registroValido = new EfItemEstRealizadoEfier();
						registroValido.setDataInclusaoEfier(Data.getDataAtual());
						registroValido.setUsuarioUsu(usuarioLogado);
						registroValido.setContaSistemaOrcEfier(conta);
						registroValido.setMesReferenciaEfier(Long.valueOf(mesTrRegistro));
						registroValido.setAnoReferenciaEfier(Long.valueOf(anoTrRegistro));
						registroValido.setDataHoraInfoEfier(Data.parseDate(dataHoraCarga, FORMATO_DATAHORA));
						registroValido.setIndManualEfier("N");
						registroValido.setImportacaoImp(dadosImportacao);					
						registroValido.setConfigSisExecFinanCsefv(versaoDao.getConfigSisExecFinanCsefv(Long.valueOf(anoTrRegistro), Long.valueOf(mesTrRegistro), sistemaValido));
						registroValido.setValor1Efier(toBigDecimal(valor1Registro).doubleValue());
						registroValido.setValor2Efier(toBigDecimal(valor2Registro).doubleValue());
						registroValido.setValor3Efier(toBigDecimal(valor3Registro).doubleValue());
						registroValido.setValor4Efier(toBigDecimal(valor4Registro).doubleValue());
						registroValido.setValor5Efier(toBigDecimal(valor5Registro).doubleValue());
						registroValido.setValor6Efier(toBigDecimal(valor6Registro).doubleValue());
						
						registroValido.setIndContabilidadeEfier(indContabilidade);
						
						registrosValidos.add(registroValido);
						
					}
					total[0] = total[0].add(toBigDecimal(valor1Registro));
					total[1] = total[1].add(toBigDecimal(valor2Registro));
					total[2] = total[2].add(toBigDecimal(valor3Registro));
					total[3] = total[3].add(toBigDecimal(valor4Registro));
					total[4] = total[4].add(toBigDecimal(valor5Registro));
					total[5] = total[5].add(toBigDecimal(valor6Registro));
					
				}
				else if(TR_REGISTRO_NAO_REVISTO.equals(tr)){ //02
					registrosLidos++;
					String mesTrNaoPrevisto = linha.substring(2,4);
					String anoTrNaoPrevisto = linha.substring(4,8);

					int pos = 8;
					int posFim = pos + TAMANHO_VALORES;
					int numeroValores = 6;
					String[] valores = new String[numeroValores];
					int cont = 0;
					while(cont < numeroValores){
						valores[cont] = linha.substring(pos, posFim);
						pos = posFim;
						posFim = posFim + TAMANHO_VALORES;
						cont++;
					}
					
					String valor1NaoPrevisto = valores[0].trim();
					String valor2NaoPrevisto = valores[1].trim();
					String valor3NaoPrevisto = valores[2].trim();
					String valor4NaoPrevisto = valores[3].trim();
					String valor5NaoPrevisto = valores[4].trim();
					String valor6NaoPrevisto = valores[5].trim();
					
					boolean houveOcorrencia = false;
					
					houveOcorrencia = houveOcorrencia || !validarMes(mesTrNaoPrevisto, ocorrencias, "TR" + TR_REGISTRO_NAO_REVISTO + " linha:" + registrosLidos + " - Mês", dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarAno(anoTrNaoPrevisto, ocorrencias, "TR" + TR_REGISTRO_NAO_REVISTO + " linha:" + registrosLidos + " - Ano", dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarSistemaMesAno("", sistemaValido, mesTrNaoPrevisto, anoTrNaoPrevisto, TR_REGISTRO_NAO_REVISTO, ocorrencias, dadosImportacao);					
					houveOcorrencia = houveOcorrencia || !validarValor(valor1NaoPrevisto, ocorrencias, TR_REGISTRO_NAO_REVISTO + " linha:" + registrosLidos, configuracao.getFinanceiroDescValor1Cfg(), dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarValor(valor2NaoPrevisto, ocorrencias, TR_REGISTRO_NAO_REVISTO + " linha:" + registrosLidos, configuracao.getFinanceiroDescValor2Cfg(), dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarValor(valor3NaoPrevisto, ocorrencias, TR_REGISTRO_NAO_REVISTO + " linha:" + registrosLidos, configuracao.getFinanceiroDescValor3Cfg(), dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarValor(valor4NaoPrevisto, ocorrencias, TR_REGISTRO_NAO_REVISTO + " linha:" + registrosLidos, configuracao.getFinanceiroDescValor4Cfg(), dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarValor(valor5NaoPrevisto, ocorrencias, TR_REGISTRO_NAO_REVISTO + " linha:" + registrosLidos, configuracao.getFinanceiroDescValor5Cfg(), dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarValor(valor6NaoPrevisto, ocorrencias, TR_REGISTRO_NAO_REVISTO + " linha:" + registrosLidos, configuracao.getFinanceiroDescValor6Cfg(), dadosImportacao);
					
					if(!houveOcorrencia && !"".equals(dataHoraCarga)){
						//Incluir dados na lista de registros válidos.
						//Dados que entram neste IF estão todos válidos!!!
						EfItemEstRealizadoEfier registroValido = new EfItemEstRealizadoEfier();
						registroValido.setDataInclusaoEfier(Data.getDataAtual());
						registroValido.setUsuarioUsu(usuarioLogado);
						registroValido.setMesReferenciaEfier(Long.valueOf(mesTrNaoPrevisto));
						registroValido.setAnoReferenciaEfier(Long.valueOf(anoTrNaoPrevisto));
						registroValido.setDataHoraInfoEfier(Data.parseDate(dataHoraCarga, FORMATO_DATAHORA));
						registroValido.setIndManualEfier("N");	
						registroValido.setImportacaoImp(dadosImportacao);
						registroValido.setConfigSisExecFinanCsefv(versaoDao.getConfigSisExecFinanCsefv(Long.valueOf(anoTrNaoPrevisto), Long.valueOf(mesTrNaoPrevisto), sistemaValido));						
						registroValido.setValor1Efier(toBigDecimal(valor1NaoPrevisto).doubleValue());
						registroValido.setValor2Efier(toBigDecimal(valor2NaoPrevisto).doubleValue());
						registroValido.setValor3Efier(toBigDecimal(valor3NaoPrevisto).doubleValue());
						registroValido.setValor4Efier(toBigDecimal(valor4NaoPrevisto).doubleValue());
						registroValido.setValor5Efier(toBigDecimal(valor5NaoPrevisto).doubleValue());
						registroValido.setValor6Efier(toBigDecimal(valor6NaoPrevisto).doubleValue());

						registrosValidos.add(registroValido);
					}
					
					total[0] = total[0].add(toBigDecimal(valor1NaoPrevisto));
					total[1] = total[1].add(toBigDecimal(valor2NaoPrevisto));
					total[2] = total[2].add(toBigDecimal(valor3NaoPrevisto));
					total[3] = total[3].add(toBigDecimal(valor4NaoPrevisto));
					total[4] = total[4].add(toBigDecimal(valor5NaoPrevisto));
					total[5] = total[5].add(toBigDecimal(valor6NaoPrevisto));
					
				}
				else if(TR_TRAILER.equals(tr)){ //99
					registrosLidos++;
					String qtdeRegistros = linha.substring(2,8);
					
					int pos = 8;
					int posFim = pos + TAMANHO_VALORES;
					int numeroValores = 6;
					String[] valores = new String[numeroValores];
					int cont = 0;
					while(cont < numeroValores){
						valores[cont] = linha.substring(pos, posFim);
						pos = posFim;
						posFim = posFim + TAMANHO_VALORES;
						cont++;
					}
					
					String totalValor1 = valores[0].trim();
					String totalValor2 = valores[1].trim();
					String totalValor3 = valores[2].trim();
					String totalValor4 = valores[3].trim();
					String totalValor5 = valores[4].trim();
					String totalValor6 = valores[5].trim();

					boolean houveOcorrencia = false;
					
					long qtde = StrToLong(qtdeRegistros);
					if(qtde == -1){
						relatarOcorrencia("TR" + TR_TRAILER  + " linha:" + registrosLidos + " - Quantidade de registros deve ser numérico - " + qtdeRegistros, 
											ocorrencias,
											new TipoOcorrencia(TipoOcorrencia.QTDE_REGISTROS_INVALIDO),
											dadosImportacao);
						houveOcorrencia = true;
					}

					//Validar os registros lidos com os dados do trailer...(qtos registros foram lidos, totais de valores....)
					//Caso ocorra diferença em algum dado, relatar ocorrencia.
					
					houveOcorrencia = houveOcorrencia || !validarQtdeRegistrosLidos(qtde, registrosLidos, TR_TRAILER, ocorrencias, dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarValor(totalValor1, ocorrencias, TR_TRAILER + " linha:" + registrosLidos, "Total do " + configuracao.getFinanceiroDescValor1Cfg(), dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarValor(totalValor2, ocorrencias, TR_TRAILER + " linha:" + registrosLidos, "Total do " + configuracao.getFinanceiroDescValor2Cfg(), dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarValor(totalValor3, ocorrencias, TR_TRAILER + " linha:" + registrosLidos, "Total do " + configuracao.getFinanceiroDescValor3Cfg(), dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarValor(totalValor4, ocorrencias, TR_TRAILER + " linha:" + registrosLidos, "Total do " + configuracao.getFinanceiroDescValor4Cfg(), dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarValor(totalValor5, ocorrencias, TR_TRAILER + " linha:" + registrosLidos, "Total do " + configuracao.getFinanceiroDescValor5Cfg(), dadosImportacao);
					houveOcorrencia = houveOcorrencia || !validarValor(totalValor6, ocorrencias, TR_TRAILER + " linha:" + registrosLidos, "Total do " + configuracao.getFinanceiroDescValor6Cfg(), dadosImportacao);

					if(!houveOcorrencia && !"".equals(dataHoraCarga)){
						BigDecimal[] totalTrailer = new BigDecimal[6];
						for(int i = 0; i < 6; i++)
							totalTrailer[i] = new BigDecimal("0");
						
						totalTrailer[0] = toBigDecimal(totalValor1);
						totalTrailer[1] = toBigDecimal(totalValor2);
						totalTrailer[2] = toBigDecimal(totalValor3);
						totalTrailer[3] = toBigDecimal(totalValor4);
						totalTrailer[4] = toBigDecimal(totalValor5);
						totalTrailer[5] = toBigDecimal(totalValor6);
						
						for(int i = 0; i < 6; i++){
							//houveOcorrencia = houveOcorrencia || validarTotaisLidos(totalTrailer[i], total[i], TR_TRAILER + " linha:" + registrosLidos, ocorrencias, dadosImportacao);
							houveOcorrencia = validarTotaisLidos(totalTrailer[i], total[i], TR_TRAILER + " linha:" + registrosLidos, ocorrencias, dadosImportacao);
						}
					}
					
					break; //Este break é porque o TR99 é o último registro lido. Se chegar ao TR99, acabou o número de registros válidos.
				}
				else{
					try{
						int numero = Integer.valueOf(tr).intValue();
						/* A variável acima só é utilizada para efeito de conversão...
						 * Se caiu neste else e conseguiu converter, então a msg é "TR deve ser válido,"
						 * se der exceção, não é número então a msg é "TR deve ser numerico"*/
						relatarOcorrencia("TR deve ser válido - " + tr + " linha:" + registrosLidos, 
											ocorrencias,
											new TipoOcorrencia(TipoOcorrencia.TR_INVALIDO),
											dadosImportacao);
					}catch (NumberFormatException nfe) {
						//Não conseguiu converter de string para numérico: Não é um número.
						relatarOcorrencia("TR deve ser numérico - " + tr + " linha:" + registrosLidos, 
											ocorrencias,
											new TipoOcorrencia(TipoOcorrencia.TR_INVALIDO),
											dadosImportacao);
						//this.logger.error(nfe); Não é necessário logar esta exceção
					}
				}
			}
    	} catch (Exception e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        	throw e;
    	}

    	List<ConfigSisExecFinanCsef> sistema = new ArrayList<ConfigSisExecFinanCsef>();
    	sistema.add(sistemaValido);
    	
    	List<Date> periodo = new ArrayList<Date>();
    	periodo.add(inicioPeriodo);
    	periodo.add(fimPeriodo);

    	//dadosImportacao não precisará ser passado para a próxima camada pois ele tem referência dentro do objeto 'ocorrências'
    	dadosImportacao.setNumRegistrosValidosImp(registrosValidos.size());
    	dadosImportacao.setNumRegistrosInvalidosImp(totalRegistros01.intValue() - registrosValidos.size());
    	dadosImportacao.setUsuarioUsu(((SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
    	
    	//Agrupa as ocorrencias do tipo CONTA_INEXISTENTE
    	ocorrencias = agrupaOcorrenciasdoTipoContaInexistente(ocorrencias);

    	List[] retorno = {registrosValidos, ocorrencias, periodo, sistema};
    	
    	request.getSession().setAttribute("dadosImportacao", dadosImportacao);
    	
		return retorno;
	}
	
	/**
	 * Agrupa as ocorrencias do tipo Contas Inexistentes em apenas uma ocorrência por conta
         * @param ocorrencias
         * @return
         * @throws Exception
	 */
	public List<EfImportOcorrenciasEfio> agrupaOcorrenciasdoTipoContaInexistente(List<EfImportOcorrenciasEfio> ocorrencias) throws Exception {
		
    	//Agrupa as contas inexistentes em apenas uma ocorrencia
    	List<EfImportOcorrenciasEfio> contasAgrupadas = new ArrayList<EfImportOcorrenciasEfio>();
    	List<EfImportOcorrenciasEfio> listaRetorno = new ArrayList<EfImportOcorrenciasEfio>();
    	
    	TipoOcorrencia tipoOcorrencia = new TipoOcorrencia(TipoOcorrencia.CONTA_INEXISTENTE);

    	for(EfImportOcorrenciasEfio ocorrencia : ocorrencias) {
    		boolean existe = false;	
    		if(ocorrencia.getTipoOcorrencia().equals(tipoOcorrencia)) {
    			for(EfImportOcorrenciasEfio obj : contasAgrupadas) {
    				if(obj.getConta().equals(ocorrencia.getConta())) {
    					existe = true;
    					break;
    				}
    			}
    			if(!existe) {
    				contasAgrupadas.add(ocorrencia);
    			}
    		} else {
    			listaRetorno.add(ocorrencia);
    		}
    	}
    	
    	listaRetorno.addAll(contasAgrupadas);
    	
    	return listaRetorno;
	}
	
	/**
	 * Insere uma ocorrência com a data atual na lista de ocorrencias.
         * @param mensagem
         * @param ocorrencias
         * @param tipoOcorrencia
         * @param dadosImportacao
	 */
	public void relatarOcorrencia(String mensagem, 
										List<EfImportOcorrenciasEfio> ocorrencias,
										TipoOcorrencia tipoOcorrencia,
										ImportacaoImp dadosImportacao){
		
		if(ocorrencias == null)
			ocorrencias = new ArrayList<EfImportOcorrenciasEfio>();

		EfImportOcorrenciasEfio ocorrencia = new EfImportOcorrenciasEfio();
				
		ocorrencia.setDescricaoEfio(mensagem);
		ocorrencia.setTipoOcorrencia(tipoOcorrencia);		
		ocorrencia.setImportacaoImp(dadosImportacao);
		ocorrencias.add(ocorrencia);
	}
	
	/**
	 * Insere uma ocorrência com a data atual na lista de ocorrencias.
         * @param mensagem
         * @param ocorrencias
         * @param tipoOcorrencia
         * @param dadosImportacao
         * @param conta
         * @throws Exception
	 */
	public void relatarOcorrencia(String mensagem, 
										List<EfImportOcorrenciasEfio> ocorrencias,
										TipoOcorrencia tipoOcorrencia,
										ImportacaoImp dadosImportacao,
										String conta) throws Exception {
		
		if(ocorrencias == null)
			ocorrencias = new ArrayList<EfImportOcorrenciasEfio>();

		EfImportOcorrenciasEfio ocorrencia = new EfImportOcorrenciasEfio();
				
		ocorrencia.setDescricaoEfio(mensagem);
		ocorrencia.setTipoOcorrencia(tipoOcorrencia);		
		ocorrencia.setImportacaoImp(dadosImportacao);
		ocorrencia.setConta(conta);
		ocorrencias.add(ocorrencia);
	}

	/**
	 * Transforma um valor em string para um long para importação. 
	 * @param numero
	 * @return numero ou -1 se não conseguir tranformar.
	 */
	private long StrToLong(String numero) throws Exception {
		long retorno = -1;
		try{
			retorno = Long.valueOf(numero.trim()).longValue();
		}
		catch (Exception e) {
			throw e;
		}
		return retorno;
	}
	
	/**
	 * Valida mês válido para importação
	 * @param strMes
	 * @param ocorrencias
	 * @param tokenMensagemOcorrencia
	 * @return
	 */
	private boolean validarMes(String strMes, List ocorrencias, String tokenMensagemOcorrencia, 
								ImportacaoImp dadosImportacao){

		long mes = 0;
		
		try
		{
			mes = StrToLong(strMes);
		}catch(Exception e) {
			relatarOcorrencia(tokenMensagemOcorrencia + " deve ser numérico - " + strMes, 
								ocorrencias,							
								new TipoOcorrencia(TipoOcorrencia.VALOR_INVALIDO),
								dadosImportacao);
			return false;
		}
		
		if(mes < 1 || mes > 12){
			if(mes == -1){
				relatarOcorrencia(tokenMensagemOcorrencia + " deve ser numérico - " + strMes, 
									ocorrencias,
									new TipoOcorrencia(TipoOcorrencia.VALOR_INVALIDO),
									dadosImportacao);
				return false;
			}
			else{
				relatarOcorrencia(tokenMensagemOcorrencia + " deve ser válido - " + strMes, 
									ocorrencias,
									new TipoOcorrencia(TipoOcorrencia.VALOR_INVALIDO),
									dadosImportacao);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Valida um ano válido para importação
	 * @param strAno
	 * @param ocorrencias
	 * @param tokenMensagemOcorrencia
	 * @return
	 */
	private boolean validarAno(String strAno, List ocorrencias, String tokenMensagemOcorrencia, ImportacaoImp dadosImportados){
		
		long ano = 0;
		
		try
		{
			ano = StrToLong(strAno);
		}catch(Exception e) {
			return false;
		}
		
		if(ano == -1){
			relatarOcorrencia(tokenMensagemOcorrencia + " deve ser numérico - " + strAno, 
								ocorrencias,
								new TipoOcorrencia(TipoOcorrencia.VALOR_INVALIDO),
								dadosImportados);
			return false;
		}
		return true;
	}
	

	/**
	 * Valida campo de Datahora de importação
	 * @param strDataHora
	 * @param ocorrencias
	 * @param mensagemOcorrencia
	 */
	private void validarDataHora(String strDataHora, 
									List ocorrencias, 
									String mensagemOcorrencia, 
									ImportacaoImp dadosImportados){
		//Verificar a data de carga: Se o método retornar NULL, é porque não conseguiu converter para uma data válida.
		if(Data.parseDate(strDataHora, FORMATO_DATAHORA) == null){
			relatarOcorrencia(mensagemOcorrencia, 
								ocorrencias,
								new TipoOcorrencia(TipoOcorrencia.FORMATO_DATA_HORA_INVALIDO),
								dadosImportados);
		}
		else {
			if(!verificaDataHoraImportacao(strDataHora))
				relatarOcorrencia(mensagemOcorrencia, 
									ocorrencias,
									new TipoOcorrencia(TipoOcorrencia.FORMATO_DATA_HORA_INVALIDO),
									dadosImportados);
		}
	}
	
	/**
	 * Valida um valor de importação.
	 * <br> 
	 * Obs.: Se o valor estiver com um sinal de menos (-) na primeira posição do número, o valor é validado como número negativo.
	 * Ex.: Para a string -0000362418200, o valor será transformado para o número -3.624.182,00, ou seja, um número válido --> o retorno para este caso é <b>true</b>.
	 * 
	 * @param strValor
	 * @param ocorrencias
	 * @param tr
	 * @param nomeValor
	 * @return
	 */
	private boolean validarValor(String strValor, List ocorrencias, String tr, String nomeValor, ImportacaoImp dadosImportados){
		if(!"".equals(nomeValor.trim())){ //nomeValor vem da configuracao, se estiver vazio é pq não é para importar.
			
			long valor = 0;
			
			try
			{
				valor = StrToLong(strValor);
			}catch(Exception e) {
				relatarOcorrencia("TR" + tr + " - " + nomeValor + " deve ser numérico - " + strValor, 
									ocorrencias,
									new TipoOcorrencia(TipoOcorrencia.VALOR_INVALIDO),
									dadosImportados);
				return false;
			}
			
			/*
			if(valor == -1){
				relatarOcorrencia("TR" + tr + " - " + nomeValor + " deve ser numérico - " + strValor, ocorrencias, nomeArquivoImportado);
				return false;
			}
			*/
		}
		return true;
	}
	
	/**
	 * Valida a quantidade de registros lidos.
	 * @param qtde
	 * @param qtdeRegistrosLidos
	 * @param tr
	 * @param ocorrencias
	 * @return
	 */
	private boolean validarQtdeRegistrosLidos(long qtde, 
												long qtdeRegistrosLidos, 
												String tr, 
												List ocorrencias,
												ImportacaoImp dadosImportados){
		
		if(qtde != qtdeRegistrosLidos){
			relatarOcorrencia("TR" + tr + " - Quantidade de registros informados (" + String.valueOf(qtde) + ") é diferente da Quantidade de registros lidos (" + String.valueOf(qtdeRegistrosLidos) + ")", 
								ocorrencias,
								new TipoOcorrencia(TipoOcorrencia.INCONSISTENCIA_QTDE_REGISTROS_INFORMADOS_x_LIDOS),
								dadosImportados);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Valida os totais lidos.
	 * @param totalTrailer
	 * @param totalRegistro
	 * @param tr
	 * @param ocorrencias
	 * @return
	 */
	private boolean validarTotaisLidos(BigDecimal totalTrailer, 
											BigDecimal totalRegistro, 
											String tr, 
											List ocorrencias, 
											ImportacaoImp dadosImportados){
		
		if(totalTrailer.doubleValue() != totalRegistro.doubleValue()){
			
			DecimalFormat nf = new DecimalFormat("#,###,###,###,###,###.00");
			
			relatarOcorrencia("TR" + tr + " - Inconsistência nos valores de totalização -"
									+ " calc: " +  nf.format(totalRegistro) 
									+ " inf: " + nf.format(totalTrailer)
									+ " dif: " + nf.format((totalRegistro.subtract(totalTrailer)))
									, ocorrencias,
									new TipoOcorrencia(TipoOcorrencia.INCONSISTENCIA_TOTALIZACAO),
									dadosImportados);
			return false;
		}
		return true;
	}
	
	/**
	 * Função que valida se o sistema possui versão no Mês/Ano informado.
	 * @param sistema
	 * @param mes
	 * @param ano
	 * @param tr
	 * @param ocorrencias
	 * @return
	 */
	private boolean validarSistemaMesAno(String conta, 
											ConfigSisExecFinanCsef sistema, 
											String mes, 
											String ano, 
											String tr, 
											List ocorrencias, 
											ImportacaoImp dadosImportados) throws ECARException{
		ConfigSisExecFinanCsefvDao versaoDao = new ConfigSisExecFinanCsefvDao(null);
		Long mesLong = Long.valueOf(mes);
		Long anoLong = Long.valueOf(ano);
		
		ConfigSisExecFinanCsefv versao = versaoDao.getConfigSisExecFinanCsefv(anoLong, mesLong, sistema);
		
		if(versao == null ||(versao != null && !"S".equals(versao.getIndAtivoCsefv()))){
			relatarOcorrencia("TR" + tr + " - O Sistema não possui versão no Mês/Ano informado - " + sistema.getNomeCsef() + " (" + mes + "/" + ano + ")", 
								ocorrencias, 
								new TipoOcorrencia(TipoOcorrencia.VERSAO_INEXISTENTE_MES_ANO),
								dadosImportados);
			return false;
		}
		
		/* Validação da conta para registros válidos */
		if(tr.equals(TR_REGISTRO)){
			List estruturasContabil = new ConfigExecFinanDao(request).getConfigExecFinanByVersao(versao);
	        int tamanhoConta = 0;
	        if (estruturasContabil != null) {
	            
	            Iterator it = estruturasContabil.iterator();
	            while(it.hasNext()){
	                ConfigExecFinanCef estruturaContabil = (ConfigExecFinanCef) it.next();
	                /* Para cada campo da estrutura, na conta é adicionado um espaço em branco.
	                 * Portanto, deve-se somar ao tamanho, o numero de caracteres+1 */
	                tamanhoConta += estruturaContabil.getNumCaracteresCef().intValue() + 1;
	            }
	            tamanhoConta--; //Decremento o tamanho conta pq o último espaço em branco (final) é retirado com trim().
	        }		
			
	        if(conta.length() != tamanhoConta){
	        	relatarOcorrencia("TR" + tr + " - A conta informada pode estar em leiaute de versão diferente." + conta, 
	        						ocorrencias,
	        						new TipoOcorrencia(TipoOcorrencia.LAYOUT_INVALIDO),
	        						dadosImportados);
	        	return false;
	        }
		}
		
		return true;
	}
	
	/**
	 * Função que transforma valores da importação de String para Double 
	 * @param valor
	 * @return
	 */
	private Double valoresStringToDouble(String valor){
		valor = valor.trim();
		double inteiro = 0;
		double decimal = 0;
		if(!"".equals(valor)){
			String parteInteira = valor.substring(0,valor.length() - 2);
			String parteDecimal = valor.substring(valor.length() - 2, valor.length());
			
			inteiro = new Double(parteInteira).doubleValue();
			decimal = new Double("0." + parteDecimal).doubleValue();
			inteiro = inteiro + decimal;
		}
		Double retorno = new Double(inteiro);
		return retorno;
	}
	
	/**
	 * Verifica se uma data hora do arquivo de importação é válida.
	 * Para isso, a data hora deve estar no formato "ddMMyyyyHHmmss".
	 *  
	 * @param dataHora
	 * @return
	 */
	private boolean verificaDataHoraImportacao(String dataHora){
		if(dataHora.length() != TAMANHO_DATAHORA){
			return false;
		}
		else {
			try{
				int dia = Integer.valueOf(dataHora.substring(0,2)).intValue();
				int mes = Integer.valueOf(dataHora.substring(2,4)).intValue();
				int ano = Integer.valueOf(dataHora.substring(4,8)).intValue();
				int hora = Integer.valueOf(dataHora.substring(8,10)).intValue();
				int minuto = Integer.valueOf(dataHora.substring(10,12)).intValue();
				int segundo = Integer.valueOf(dataHora.substring(12,14)).intValue();
				
				if(mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12){
					if(dia < 1 || dia > 31){
						return false;
					}
				}
				else if(mes == 4 || mes == 6 || mes == 9|| mes == 11){
					if(dia < 1 || dia > 30){
						return false;
					}
				}
				else if(mes == 2){
					if((ano % 4) == 0 && (ano % 100) != 0){ //ano bissexto.
						if(dia < 1 || dia > 29){
							return false;
						}
					}
					else{
						if(dia < 1 || dia > 28){
							return false;
						}
					}
				}
				else{
					return false;
				}
				
				if(hora < 0 || hora > 23)
					return false;
				if(minuto < 0 || minuto > 59)
					return false;
				if(segundo < 0 || segundo > 59)
					return false;
			}
			catch (NumberFormatException nfe) {
				//this.logger.error(nfe); //Não é necessário logar essa exceção
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Importa os dados do arquivo para a tabela EfItemEstRealizadoEfier.
	 * Grava as ocorrencias na tabela EfImportOcorrenciasEfio
	 * @param arquivoImportado
	 * @param configuracao
         * @param usuarioLogado
         * @param request
         * @throws ECARException
         * @throws HibernateException
         * @throws Exception
	 */
	public void importarDadosArquivo(File arquivoImportado, ConfiguracaoCfg configuracao, UsuarioUsu usuarioLogado, HttpServletRequest request) throws ECARException, HibernateException, Exception {
		
		ItemEstruturaRealizadoDao itemEstRealizadoDao = new ItemEstruturaRealizadoDao(null);
		List[] critica = this.criticaArquivoImportado(arquivoImportado, configuracao, usuarioLogado, request);
		List registrosValidos = critica[0];
		List ocorrencias = critica[1];
		List periodo = critica[2];
		List sistema = critica[3];
		
		Date inicio = (Date)periodo.get(0); 
		Date fim = (Date)periodo.get(1);
		
		Transaction tx = null;
		try{
			
			tx = session.beginTransaction();
			
			//Deleta ocorrências antes de tudo
			List listaExclusao = itemEstRealizadoDao.getItemEstRealizadoEfierToImportacao(inicio, fim, (ConfigSisExecFinanCsef)sistema.get(0));
			if (listaExclusao != null) {
				for(Iterator it = listaExclusao.iterator(); it.hasNext();) {
					EfItemEstRealizadoEfier obj = (EfItemEstRealizadoEfier) it.next();
					session.delete(obj);
				}
			}

			if(!registrosValidos.isEmpty()){
				Iterator itReg = registrosValidos.iterator();
				while(itReg.hasNext()){
					EfItemEstRealizadoEfier realizado = (EfItemEstRealizadoEfier) itReg.next();
					session.save(realizado);					
				}
				
			}
			
			if(!ocorrencias.isEmpty()){

				Iterator itOc = ocorrencias.iterator();
			
				while(itOc.hasNext()){
					
					EfImportOcorrenciasEfio oc = (EfImportOcorrenciasEfio) itOc.next();				
					session.save(oc);
				}
			}
			
			tx.commit();
		}
		catch (HibernateException he) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(he);
        	
        	if(tx != null){
        		tx.rollback();
        	}
        	
        	throw new ECARException("integracaoFinanceira.importarArquivo.importacao.erro");
		}
		catch (Exception e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        	   	if(tx != null){
        		tx.rollback();
        	}
        	
        	throw new ECARException("integracaoFinanceira.importarArquivo.importacao.erro");
		}

	}
	
	/**
	 * Recebe um array de contas a serem criadas no Sistema.
         * @param contasInexistentes
         * @author carlos
	 * @since 07/06/2007
	 * @throws ECARException
	 */
	public void criarContas(Object[] contasInexistentes) throws ECARException {

		List<EfItemEstContaEfiec> todasContas = new ArrayList<EfItemEstContaEfiec>();
		List<EfIettFonteTotEfieft> fontesTotEfieft = new ArrayList<EfIettFonteTotEfieft>();
				
		Transaction tx = null;
		
		try
		{
			//Abre transação
			tx = session.beginTransaction();
			
			Query q = null;				
			
			q = this.session.createQuery("from FonteRecursoFonr");
			List<FonteRecursoFonr> fontesRecurso = (List<FonteRecursoFonr>)q.list();
			
			q = this.session.createQuery("from ExercicioExe exe order by exe.dataInicialExe asc");
			List<ExercicioExe> exercicios = (List<ExercicioExe>)q.list();
			
			q = this.session.createQuery("from RecursoRec");
			List<RecursoRec> recursos = (List<RecursoRec>)q.list();		
			
			final int ACAO = 3;
			
			
			for(int indice=0; indice<contasInexistentes.length; indice++) {
		
				String[] decompoeConta = ((String) contasInexistentes[indice]).trim().toUpperCase().split(" ");
				String projAtividade = decompoeConta[0];
				String obra = decompoeConta[1];
				
				q = this.session.createQuery("from ItemEstruturaIett iett where iett.estruturaEtt.codEtt = :codEtt and trim( iett.siglaIett ) = :siglaIett and iett.indAtivoIett = 'S'");
				q.setLong("codEtt", ACAO);
				q.setString("siglaIett", projAtividade);
				
				ItemEstruturaIett iett = (ItemEstruturaIett) q.list().get(0);
				
				/************************************************/
				//Verifica se já existe objetos EfIettFonteTotEfieft para esse iett, caso contrário inclui o mesmo no banco.
				ItemEstruturaFonteRecursoDao dao = new ItemEstruturaFonteRecursoDao(request);
				//O ExercicioExe é apenas exigido no método, mas não considerado na consulta, a linha referente
				//ao ExercicioExe está comentado no método.
				List fontesRecursos = dao.getFontesRecursosByExercicio(iett, new ExercicioExe());
				
				if(fontesRecursos.size() == 0) {
				
					//Tá invertido, o Recurso responde pela Fonte do recurso e vice-versa
					for(Iterator<FonteRecursoFonr> itFontes = fontesRecurso.iterator(); itFontes.hasNext();) {
						
						FonteRecursoFonr fonte = itFontes.next();
						
						EfIettFonteTotEfieft efTotEfieft = new EfIettFonteTotEfieft();
						
						EfIettFonteTotEfieftPK efTotEfieftPK = new EfIettFonteTotEfieftPK();
						efTotEfieftPK.setCodFonr(fonte.getCodFonr());
						efTotEfieftPK.setCodIett(iett.getCodIett());
						
						efTotEfieft.setComp_id(efTotEfieftPK);
						efTotEfieft.setDataInclusaoEfieft(new Date());
						efTotEfieft.setIndAtivoEfieft("S");
						efTotEfieft.setItemEstruturaIett(iett);
						efTotEfieft.setFonteRecursoFonr(fonte);
						
						//Acrescenta o objeto na lista que será salva posteriormente no banco.
						fontesTotEfieft.add(efTotEfieft);
					}
					
				}
						
				int anoInicio = Integer.parseInt(new SimpleDateFormat("yyyy").format(iett.getDataInicioIett())); 
				int anoFim = anoInicio;
				
				if(iett.getDataTerminoIett() != null) {
					anoFim = Integer.parseInt(new SimpleDateFormat("yyyy").format((iett.getDataTerminoIett())));						
				}
				
				
				//loop dos anos a serem gerados ocorrências (contas)
				for(int inicio=anoInicio; inicio<=anoFim; inicio++) {
					
					List<EfItemEstContaEfiec> contas = new ArrayList<EfItemEstContaEfiec>();
					
					int contador = (fontesRecurso.size() * recursos.size());

					for(int i=1; i<=contador; i++) {
						
						EfItemEstContaEfiec conta = new EfItemEstContaEfiec();
						
						for(ExercicioExe exercicio : exercicios) {	
							
							int ano = Integer.parseInt(new SimpleDateFormat("yyyy").format(exercicio.getDataInicialExe()));
							
							if(inicio == ano) {
								conta.setExercicioExe(exercicio);
								break;
							}
						}
												
						conta.setIndAtivoEfiec("S");
						conta.setIndAcumuladoEfiec("N");
						conta.setFormaInclusao(Dominios.CONTA_FORMA_INCLUSAO_VIA_IMPORTACAO);
						conta.setContaSistemaOrcEfiec(iett.getSiglaIett().trim() + " " + obra);
						conta.setItemEstruturaIett(iett);
												
						contas.add(conta);
					}
					
					

					
																
					for(Iterator<EfItemEstContaEfiec> itContas = contas.iterator(); itContas.hasNext();) {
												
						EfItemEstContaEfiec conta = itContas.next();
						
						for(Iterator<FonteRecursoFonr> itFontes = fontesRecurso.iterator(); itFontes.hasNext();) {
							
							FonteRecursoFonr fonte = itFontes.next();
							conta.setContaSistemaOrcEfiec(conta.getContaSistemaOrcEfiec() + " " + fonte.getSiglaFonr().trim());
							conta.setFonteRecursoFonr(fonte);
							//Tá invertido, o Recurso responde pela Fonte do recurso e vice-versa
							for(Iterator<RecursoRec> itRecursos = recursos.iterator(); itRecursos.hasNext();) {
								
								RecursoRec recurso = itRecursos.next();
								conta.setContaSistemaOrcEfiec(conta.getContaSistemaOrcEfiec().trim() + " " + recurso.getSiglaRec().trim());
								conta.setRecursoRec(recurso);
								
								if(itContas.hasNext() && itRecursos.hasNext()) {
									conta = itContas.next();
									conta.setFonteRecursoFonr(fonte);
									conta.setContaSistemaOrcEfiec(conta.getContaSistemaOrcEfiec().trim() + " " + fonte.getSiglaFonr().trim());
								} else if(itContas.hasNext() && itFontes.hasNext()) {
									conta = itContas.next();
								}
							}
						}
						
					}
					
					todasContas.addAll(contas);
				}
		
				
				ItemEstruturaPrevisaoDao iePrevisaoDao = new ItemEstruturaPrevisaoDao(request);
				List<EfItemEstContaEfiec> listaPersistenciaContas = new ArrayList<EfItemEstContaEfiec>();
				
				
				//Verifica se as contas a serem inseridas já não existem no Sistema.
				for(EfItemEstContaEfiec objConta : todasContas) {
								
					q = this.session.createQuery("from EfItemEstContaEfiec where contaSistemaOrcEfiec = :conta and " +
																					"fonteRecursoFonr = :fonteRecursoFonr and " +
																					"exercicioExe = :exercicioExe and " +
																					"recursoRec = :recursoRec");
					q.setString("conta", objConta.getContaSistemaOrcEfiec());
					q.setLong("fonteRecursoFonr", objConta.getFonteRecursoFonr().getCodFonr());
					q.setLong("exercicioExe", objConta.getExercicioExe().getCodExe());
					q.setLong("recursoRec", objConta.getRecursoRec().getCodRec());
					
					if(q.list().size() == 0) {
						listaPersistenciaContas.add(objConta);
					}
				}
				

								
				//Salva os objetos no banco
				for(EfItemEstContaEfiec obj : listaPersistenciaContas) {
				
					EfItemEstPrevisaoEfiep objEfiep = null;
					try
					{
						objEfiep = iePrevisaoDao.buscar(obj.getItemEstruturaIett().getCodIett(),
				            obj.getFonteRecursoFonr().getCodFonr(), obj.getRecursoRec().getCodRec(), obj.getExercicioExe().getCodExe());
		
					}catch (Exception e) {
						//Significa que não achou registro e poderá ser inserido um novo objeto no banco.
						objEfiep = new EfItemEstPrevisaoEfiep();  
						
						EfItemEstPrevisaoEfiepPK pk = new EfItemEstPrevisaoEfiepPK();
						pk.setCodExe(obj.getExercicioExe().getCodExe());
						pk.setCodFonr(obj.getFonteRecursoFonr().getCodFonr());
						pk.setCodIett(obj.getItemEstruturaIett().getCodIett());
						pk.setCodRec(obj.getRecursoRec().getCodRec());
						
						objEfiep.setComp_id(pk);
						objEfiep.setDataInclusaoEfiep(new Date());
						objEfiep.setExercicioExe(obj.getExercicioExe());
						objEfiep.setFonteRecursoFonr(obj.getFonteRecursoFonr());
						objEfiep.setIndAtivoEfiep("S");
						objEfiep.setItemEstruturaIett(obj.getItemEstruturaIett());
						objEfiep.setRecursoRec(obj.getRecursoRec());
						objEfiep.setValorAprovadoEfiep(new BigDecimal(0));
						objEfiep.setValorRevisadoEfiep(new BigDecimal(0));
						
						
				    	/*
				         * FIXME: Verificar esta regra
				         * Está fixo, pois falta fazer na tela para informar a espécie e a fonte
				         * rec 3 = fonte 49
				         * rec 4 = fonte 50
				         * rec 5 = fonte 51
				         */ 
				    	if(objEfiep.getEspecieEsp() == null){
				    		objEfiep.setEspecieEsp((EspecieEsp) buscar(EspecieEsp.class, Long.valueOf(0)));
				    		objEfiep.setEspecieEsp(objEfiep.getEspecieEsp());
				    	}
				    	
//				    	if(objEfiep.getFonteFon() == null){
//				    		if(objEfiep.getRecursoRec().getCodRec().longValue() == 3){
//				    			objEfiep.setFonteFon((FonteFon) buscar(FonteFon.class, Long.valueOf(49)));
//				    		}
//				    		if(objEfiep.getRecursoRec().getCodRec().longValue() == 4){
//				    			objEfiep.setFonteFon((FonteFon) buscar(FonteFon.class, Long.valueOf(50)));
//				    		}
//				    		if(objEfiep.getRecursoRec().getCodRec().longValue() == 5){
//				    			objEfiep.setFonteFon((FonteFon) buscar(FonteFon.class, Long.valueOf(51)));
//				    		}
//				    		objEfiep.getComp_id().setCodFon(objEfiep.getFonteFon().getCodFon());
//				    	}
						
						
						session.save(objEfiep);
					}
					
					session.save(obj);
				}
				
				
				//Salva os objetos no banco
				for(EfIettFonteTotEfieft obj : fontesTotEfieft) {
					session.save(obj);
				}			
				
			}
			
			
			tx.commit();

		} catch (ECARException e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
			this.logger.error(e);
			throw e;
		} catch (Exception e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
		
	}
	
	/**
	 * Exclui um objeto passando a classe e a chave como parâmetro. Primeiro carrega o objeto<br> 
         *
         * @param obj
         * @throws ECARException
         */
	public void excluir (ConfigSisExecFinanCsef obj) throws ECARException {
		
        try {
         
        	boolean excluir = true;

            if ((contar(obj.getFonteRecursoFonrs()) > 0) || (contar(obj.getConfigSisExecFinanCsefvs()) > 0)) {
                excluir = false;
                throw new ECARException("integracaoFinanceira.sistema.exclusao.erro");
            }
            if (excluir)
                super.excluir(obj);
        } catch (ECARException e) {
        	this.logger.error(e);
            throw e;
        }

	}
	
        /**
         *
         * @param valor
         * @return
         */
        public static BigDecimal toBigDecimal(String valor) {
		
		String inteiros = valor.substring(0, valor.length() - 2);
		String decimais = valor.substring(valor.length() - 2);
		
		String valorFormatado = inteiros + "." + decimais;
				
		BigDecimal retorno = new BigDecimal(valorFormatado);		
		return retorno;
	}
	
}