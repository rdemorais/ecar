package ecar.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import comum.database.Dao;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.pojo.ApontamentoAnexo;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.DemAtributoDema;
import ecar.pojo.ObjetoDemanda;
import ecar.pojo.RegApontamentoRegda;
import ecar.pojo.RegDemandaRegd;
import ecar.pojo.SisAtributoSatb;
import ecar.taglib.util.Input;
import ecar.util.Dominios;

/**
 *
 * @author 70744416353
 */
public class ExportacaoRelatorioDemandasDao extends Dao{

	private String separadorCampos;
	private String separadorMultivalor;

	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public ExportacaoRelatorioDemandasDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Gera o arquivo de exportação
	 * @param nomeArquivo
         * @param listaDemandas
         * @param codVisao
         * @param configuracao
	 * @return String[]: posicao 0 --> caminho do arquivo gravado no servidor
	 * 					 posicao 1 --> nome do arquivo gerado
	 * @throws ECARException
	 */
	public String[] gerarArquivoExportacaoTxt(String nomeArquivo, 
			List<RegDemandaRegd> listaDemandas, Long codVisao, ConfiguracaoCfg configuracao) 
			throws ECARException {

		String[] retorno = new String[2];
		this.separadorCampos = configuracao.getSeparadorArqTXT();
		this.separadorMultivalor = configuracao.getSeparadorCampoMultivalor();
		
		RegDemandaDao regDemandaDao = new RegDemandaDao(null);
		
		//configuração de upload
		String caminho = configuracao.getRaizUpload() + configuracao.getUploadExportacaoDemandas();
		
		File dir1 = new File (".");

		String caminhoCompleto = "";
		
		int numeroCamposApontamento = 4;//Em apontamentos só tem campos fixos
		
		try {
        
			if(!caminho.endsWith("/"))
				caminho = dir1.getCanonicalPath() + caminho + "/";
			
            nomeArquivo +=  "_export.txt";
			caminhoCompleto = caminho + nomeArquivo;
 			
			File diretorio = new File(caminho);
		
			if(!diretorio.isDirectory()) 
				diretorio.mkdirs();
			
			
			File arq = new File(caminhoCompleto);
			
			arq.createNewFile();
			
			FileOutputStream arquivo = new FileOutputStream(arq);
			
			String linha = "";
			
			//Consulta qual a sequência dos atributos da demanda
			AtributoDemandaDao atributoDemandaDao = new AtributoDemandaDao(request);
			List<ObjetoDemanda> atributosOrdenados = atributoDemandaDao.getAtributosDemandaVisaoAtivosOrdenadosPorSequenciaTelaCampo(codVisao);
			Iterator<ObjetoDemanda> atributosOrdenadosIt = null;
			
			if (atributosOrdenados != null && atributosOrdenados.isEmpty()){
				throw new ECARException("atributoDemanda.validacao.vazia");
			}
			
			if( listaDemandas != null && !listaDemandas.isEmpty()) {

				//Para cada demanda criar a linhas da demanda e dos apontamentos
				Iterator<RegDemandaRegd> listaDemandasIt = listaDemandas.iterator();
				
				linha = montarHeader(atributosOrdenados.iterator(), numeroCamposApontamento);
				//escreve linha no arquivo .txt
				linha = Util.normalizaCaracterMarcador(linha);
				arquivo.write(linha.getBytes(Dominios.ENCODING_DEFAULT));
				arquivo.flush();
				
				while(listaDemandasIt.hasNext()) {
					
					//Demanda
					RegDemandaRegd regDemanda = (RegDemandaRegd) listaDemandasIt.next();
					//Consulta a regDemanda pois quando vai pegar a coleção de apontamentos e 
					//de atributos livres dá erro de hibernate (lazy) 
					regDemanda = (RegDemandaRegd) regDemandaDao.buscar(RegDemandaRegd.class, regDemanda.getCodRegd());

					atributosOrdenadosIt = atributosOrdenados.iterator();
					
					
					/*
					Para cada demanda escreve uma linha: 
					<campo1>[separador]<campo2>[separador]<campo3>...[separador][separador][separador][quebra de linha]
					
					Campo deve estar na mesma ordem que está sendo exibida 
					na tela de cadastro --> atributosOrdenados 
					*/
					
					linha = montaLinhaDemandaComCamposOrdenados(atributosOrdenadosIt, regDemanda, numeroCamposApontamento);
					//escreve linha no arquivo .txt
					linha = Util.normalizaCaracterMarcador(linha);
					arquivo.write(linha.getBytes(Dominios.ENCODING_DEFAULT));
					arquivo.flush();
					
					// Para cada demanda, verifica sem tem apontamentos,
					// e escreve as linhas com os campos dos apontamentos
					if( regDemanda.getRegApontamentoRegdas() != null &&
						regDemanda.getRegApontamentoRegdas().size() > 0) {
					
						/*
						Para cada demanda, consulta os apontamentos e
						para cada apontamento escreve uma linha: 
						[separador][separador][separador]<campo1>[separador]<campo2>...[quebra de linha]
						
						Obs.: O início da linha de cada apontamento inicia com 'n' separadores, sendo 'n'
						o número de campos que uma demanda tem.
						*/
						//Pega os Apontamentos da Demanda
						List listaApontamentos = new ArrayList();
						listaApontamentos.addAll(regDemanda.getRegApontamentoRegdas());

						//Se existir apontamentos na Demanda
						if (listaApontamentos != null && listaApontamentos.size() > 0) {
							
							Iterator listaApontamentosIt = listaApontamentos.iterator();

							//Para cada apontamento criar a linha
							while(listaApontamentosIt.hasNext()) {
								RegApontamentoRegda regApontamento = (RegApontamentoRegda) listaApontamentosIt.next();
								linha = montaLinhasApontamentosComCamposOrdenados(regDemanda, regApontamento, atributosOrdenados.size());
								//escreve linha no arquivo .txt
								linha = Util.normalizaCaracterMarcador(linha);
								arquivo.write(linha.getBytes(Dominios.ENCODING_DEFAULT));
								arquivo.flush();
							}
						}
					}
					
				}
			}
									
			//Fecha arquivo
			arquivo.close();
			
			retorno[0] = caminhoCompleto;
			retorno[1] = nomeArquivo;
			
    	} catch (ECARException ecarex) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(ecarex);
        	throw ecarex;
		} catch (Exception e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
    	}
    	
		return retorno;
	}
	
	/**
	 * Método que monta a linha com os campos de demanda ordenados. A linha deve seguir o padrão: 
	 * <campo1>[separador]<campo2>[separador]<campo3>...[separador][separador][separador][quebra de linha]
	 * 
	 * Ao final da linha deve haver tantos separadores quanto for o número de campos no cadastro de apontamento.
	 * 
	 * @param pAtributosOrdenadosIt
	 * @param pRegDemanda
	 * @return linha
	 */
	private String montaLinhaDemandaComCamposOrdenados(Iterator<ObjetoDemanda> pAtributosOrdenadosIt, 
			RegDemandaRegd pRegDemanda, int pNumeroCamposDemanda) {
		
		String linhaRetorno = "";
		
		linhaRetorno += pRegDemanda.getCodRegd().toString() +  separadorCampos +  separadorCampos;
		
		if(pAtributosOrdenadosIt != null) {
			while(pAtributosOrdenadosIt.hasNext()) {
				ObjetoDemanda atributo = (ObjetoDemanda) pAtributosOrdenadosIt.next();
				
				try {
					// Se for atributo livre pega valor pelo DemAtributoDemas
					if( atributo.iGetGrupoAtributosLivres() != null 
			    		&& (atributo.iGetGrupoAtributosLivres().getIndAtivoSga()!=null 
			    	    &&  atributo.iGetGrupoAtributosLivres().getIndAtivoSga().equals("S"))) {
						
						if(pRegDemanda.getDemAtributoDemas() != null) {
			    	    	String separadorParaCamposMulti = "";
			    	    	String separadorParaCamposCheck = "";
							//Iterador com os atributos livres da demanda em questao
			    	    	Iterator<DemAtributoDema> itAtribLivres = pRegDemanda.getDemAtributoDemas().iterator();
			    	    	
			    	    	//Para cada atributo livre da demanda, verifica-se se este corresponde 
			    	    	//ao atributo que esta sendo tratado
			    	    	while(itAtribLivres.hasNext()){
			    	    		DemAtributoDema atributoDema = (DemAtributoDema) itAtribLivres.next();
			    	    		//Iterador com os atributos livres associados ao atributo em questão
			    	    		Iterator<SisAtributoSatb> sisAtributosSatbIt = atributo.iGetGrupoAtributosLivres().getSisAtributoSatbs().iterator();
			    	    		
			    	    		while (sisAtributosSatbIt.hasNext()) {
			    	    			SisAtributoSatb sisAtributo = (SisAtributoSatb) sisAtributosSatbIt.next();
				    	    		if( (sisAtributo).equals(atributoDema.getSisAtributoSatb()) ) {
				    	    			//Caso seja um atributo tipo texto
				    	    			if(atributoDema.getInformacao() != null) {
				    	    				
				    	    				String conteudoAtributoDemanda = atributoDema.getInformacao().replaceAll("\n", " ").replaceAll("\r"," ");
				    	    				
				    	    				if (sisAtributo.getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg().intValue() == Input.IMAGEM){
				    	    					if (conteudoAtributoDemanda.indexOf("/") != -1){
				    	    						conteudoAtributoDemanda = conteudoAtributoDemanda.substring(conteudoAtributoDemanda.lastIndexOf("/")+1);
				    	    						if (conteudoAtributoDemanda.indexOf("--") != -1){
				    	    							conteudoAtributoDemanda = conteudoAtributoDemanda.substring(conteudoAtributoDemanda.lastIndexOf("--")+3);
				    	    						}
				    	    					}
				    	    				}
				    	    				
				    	    				linhaRetorno += separadorParaCamposMulti + conteudoAtributoDemanda;
				    	    				separadorParaCamposMulti = this.separadorMultivalor;
				    	    				

				    	    				
				    	    			//Caso seja atributo tipo check, radio ou select
				    	    			} else if(atributoDema.getSisAtributoSatb().getDescricaoSatb() != null) {
				    	    				linhaRetorno += separadorParaCamposCheck + (atributoDema.getSisAtributoSatb().getDescricaoSatb()).replaceAll("\n", " ").replaceAll("\r"," ");
				    	    				separadorParaCamposCheck = this.separadorMultivalor;
				    	    			//Caso não tenha valor associado ao atributo
				    	    			} else {
				    	    				linhaRetorno += "";
				    	    			}
				    	    		}
			    	    		}
			    	    	}
			    	    	separadorParaCamposMulti = "";
			    	    	separadorParaCamposCheck = "";
			        	}
					} else {
					// Se for campo fixo pega valor direto do atributo
						linhaRetorno +=	atributo.iGetValor(pRegDemanda).replaceAll("\n", " ").replaceAll("\r"," ");
					}
					// escreve o separador entre cada campo
					linhaRetorno += this.separadorCampos;
				} catch(ECARException e) {
				}		
			}

			// escreve o fim da linha com separadores e quebra de linha
			if(!linhaRetorno.equals("")) {
				//Numero de separadores é dado pelo numero de campos que existe no cadastro de apontamento
				for(int i = 1; i <= pNumeroCamposDemanda - 1; i++)
					linhaRetorno += this.separadorCampos;
				  
				linhaRetorno = linhaRetorno.replaceAll("\n"," ").replaceAll("\r"," ");
								
				//Quebra de Linha
				linhaRetorno += "\n";
			}
		}
		
		return linhaRetorno;		
	}
	
	
	private String montarHeader(Iterator<ObjetoDemanda> pAtributosOrdenadosIt, int pNumeroCamposDemanda) {
		String linhaRetorno = "";
	
		linhaRetorno += "ID_DEMANDA" + separadorCampos;
		linhaRetorno += "ID_ENCAMINHAMENTO" + separadorCampos;
		
		if(pAtributosOrdenadosIt != null) {
			while(pAtributosOrdenadosIt.hasNext()) {
				ObjetoDemanda atributo = (ObjetoDemanda) pAtributosOrdenadosIt.next();
				
					// Se for campo fixo pega valor direto do atributo
						linhaRetorno +=	atributo.iGetLabel().replaceAll("\n", " ").replaceAll("\r"," ");
						// escreve o separador entre cada campo
						linhaRetorno += this.separadorCampos;
					}
			}

		// Parecer da função
		linhaRetorno += "Data Encaminhamento" + separadorCampos;
		linhaRetorno += "Usuário" + separadorCampos;
		linhaRetorno += "Comentário" + separadorCampos;
		linhaRetorno += "Arquivos Anexos" + separadorCampos;
	
		
		//Quebra de Linha
		linhaRetorno += "\n";
		
		return linhaRetorno;	
	}
	
	/**
	 * Método que monta as linhas com os apontamentos. Cada linha contem os campos do apontamento ordenados. 
	 * A linha deve seguir o padrão: 
	 * [separador][separador][separador]<campo1>[separador]<campo2>[separador]<campo3>...[quebra de linha]
	 * 
	 * No início de cada linha de apontamento deverá iniciar com tantos separadores quanto forem a 
	 * quantidade de campos no cadastro de demandas.
	 * 
	 * @param pRegDemanda
	 * @param pNumeroCamposDemanda
	 * @return
	 */
	private String montaLinhasApontamentosComCamposOrdenados(RegDemandaRegd pRegDemanda, 
			RegApontamentoRegda pRegApontamento, int pNumeroCamposDemanda) {
		
		String linhaRetorno = "";
					
		linhaRetorno += pRegDemanda.getCodRegd().toString() +  separadorCampos + 
		pRegApontamento.getCodRegda() + separadorCampos;
				
		//Para cada apontamento monta o inicio da linha com os separadores
		for(int i = 1; i <= pNumeroCamposDemanda; i++)
			linhaRetorno += this.separadorCampos;
		
		//Monta os campos - em apontamento só existe campos fixos
		linhaRetorno += pRegApontamento.getDataRegda() + this.separadorCampos;
		linhaRetorno += pRegApontamento.getUsuarioUsu().getNomeUsu().replaceAll("\n"," ").replaceAll("\r"," ") + this.separadorCampos;
		linhaRetorno += pRegApontamento.getInfoRegda().replaceAll("\n"," ").replaceAll("\r"," ") + this.separadorCampos;
		
		//Pega os anexos do apontamento
		try {
			String separadorAnexos = "";
			ApontamentoAnexoDao anexoDao = new ApontamentoAnexoDao(request);
			List<ApontamentoAnexo> anexosApontamento = anexoDao.getAnexosApontamentoRegda(pRegApontamento);
			if(anexosApontamento != null && !anexosApontamento.isEmpty()) {
				Iterator<ApontamentoAnexo> anexosApontamentosIt = anexosApontamento.iterator();
				while(anexosApontamentosIt.hasNext()) {
					ApontamentoAnexo anexoApto = (ApontamentoAnexo)anexosApontamentosIt.next();
					linhaRetorno += separadorAnexos + anexoApto.getSrcAnexo();
					separadorAnexos = this.separadorMultivalor;
				}
			}
		} catch(ECARException e) {
		}
		
		linhaRetorno = linhaRetorno.replaceAll("\n"," ").replaceAll("\r"," ");
		
		//Quebra de linha
		linhaRetorno += "\n";
		
		return linhaRetorno;
	}
	
	
}