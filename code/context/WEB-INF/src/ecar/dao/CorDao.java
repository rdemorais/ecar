/*
 * Created on 13/09/2004
 * 
 */
package ecar.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.hibernate.Query;

import comum.database.Dao;
import comum.util.FileUpload;
import comum.util.Pagina;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.pojo.Cor;
import ecar.pojo.CorTipoFuncAcompCtfa;
import ecar.pojo.HistoricoCorPtcH;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.servlet.grafico.bean.PosicaoBean;
import ecar.util.Dominios;

/**
 * @author garten
 *
 */
public class CorDao extends Dao {
   
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public CorDao(HttpServletRequest request) {
		super();
		this.request = request;
	}

	/**
	 * Grava Cor e suas respectivas CorTipo
	 * @author ..., rogerio
	 * @since N/C
	 * @version 0.2, 28/02/2007
	 * @param cor
	 * @throws ECARException
	 */
	public void salvar(Cor cor) throws ECARException {
		if (pesquisarDuplos(cor, new String[] {"nomeCor"}, "codCor").size() > 0)
		    throw new ECARException("cor.validacao.registroDuplicado");
		super.salvar(cor, cor.getCorTipoFuncAcompCtfas());
	} // fim salvar()
	
	/**
	 * Altera Cor e suas respectivas CorTipoFuncAcomp.
	 * @author ..., rogerio
	 * @since N/C
	 * @version 0.2, 02/03/2007
	 * @param cor
	 * @throws ECARException
	 */
	public void alterar(Cor cor) throws ECARException {
		if (pesquisarDuplos(cor, new String[] {"nomeCor"}, "codCor").size() > 0)
		    throw new ECARException("cor.validacao.registroDuplicado");
		
		/* --
		 * Controla a atualização dos registros de CorTipoFuncAcomp no Banco de Dados.
		 * Verifica a existência de um registro que está sendo alterado, se existir apaga e insere,
		 * caso contrário, apenas insere no banco.
		 *  -- */
		List<CorTipoFuncAcompCtfa> listCtfa = new ArrayList(cor.getCorTipoFuncAcompCtfas());	
		CorTipoFuncAcompCtfa  aux = null; // aux para pesquisa
		
		for( CorTipoFuncAcompCtfa ctfa : listCtfa ) {
			/* -- 
			 * Consulta por hql pois o método "buscar" não leva em conta o 
			 * -- */
   	    	Query query = session.createQuery(
       	       		" from CorTipoFuncAcompCtfa " +
       	       		" where comp_id.codCor = :codCor " + 
      				"   and comp_id.codTpfa = :tipoFunc " +
       				"   and comp_id.posicaoCtfa = :posicao ");
        	    	
       	    query.setLong("codCor", ctfa.getComp_id().getCodCor());
       	    query.setLong("tipoFunc", ctfa.getComp_id().getCodTpfa());
       	    query.setString("posicao", ctfa.getComp_id().getPosicaoCtfa());
				
       	    aux = (CorTipoFuncAcompCtfa) query.uniqueResult();
        	    
       	    if( aux != null )
				super.alterar(ctfa);
       	    else
				super.salvar(ctfa);
		}
		
		super.alterar(cor);
	} // fim alterar()
	
    /**
     * Retorna o noma da imagem representando uma cor para uma função de acompanhamento
     * @param cor
     * @param funcaoAcomp
     * @return
     */
    public String getImagemSinal(Cor cor, TipoFuncAcompTpfa funcaoAcomp){
    	return "s" + cor.getNomeCor() + funcaoAcomp.getTamanhoSinalTpfa() + ".png";
    }
    
    /**
     * Retorna o noma da imagem representando uma cor para uma função de acompanhamento
     * @param cor
     * @param funcaoAcomp
     * @return
     */
    public String getImagemSinalRelPosicoes(Cor cor, TipoFuncAcompTpfa funcaoAcomp){
        //return "sinal" + cor.getCodCor() + funcaoAcomp.getTamanhoSinalTpfa() + ".png";
    	if(cor != null)
    		return "r" + cor.getNomeCor() + funcaoAcomp.getTamanhoSinalTpfa() + ".png";
    	else
    		return "rBranco" + funcaoAcomp.getTamanhoSinalTpfa() + ".png";
    }

    /**
     * Retorna o noma da imagem representando uma cor para uma função de acompanhamento
     * @param cor
     * @param funcaoAcomp
     * @return
     */
    public String getImagemRelatorio(Cor cor, TipoFuncAcompTpfa funcaoAcomp){    
        if(cor != null)
            return cor.getNomeCor().toLowerCase() + funcaoAcomp.getTamanhoSinalTpfa() + ".png";
        else
            return "branco" + funcaoAcomp.getTamanhoSinalTpfa() + ".png";
    }
    
    /**
     *
     * @return
     * @throws ECARException
     */
    public List getOrdemCores() throws ECARException{
    	return this.listar(Cor.class, new String[] {"ordemCor", ""});
    }
    
    /**
     * Realiza o upload das imagens de Cores na inclusão. Sobrescreve o arquivo existente anteriormente. <br>
     * @author joao carlos, rogerio
     * @since N/C
     * @version 0.2, 28/02/2007
     * @param campos
     * @throws ECARException
     */
   
    public Map<String, String> uploadImagem(List campos) throws ECARException {
    	String pathRaiz = new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getRaizUpload(); //_msg.getPathUploadRaiz("path .upload.raiz");
    	String pathRelativo = Dominios.PATH_REMOTE_IMAGES;
    	
    	Map<String, String> arquivos = new HashMap<String, String>();
    	
        try{ 
        	Iterator it = campos.iterator();  
        	while( it.hasNext() ){
                FileItem fileItem = (FileItem) it.next();             
                
                //FileUpload.verificaValorCampo(campos, "hid" + fileItem.getFieldName()).trim();
                String nomeArquivo = FileUpload.verificaValorCampo(campos, "hidNomeArquivo" + fileItem.getFieldName()).trim();
                
                if(!fileItem.isFormField() && !"".equals(fileItem.getName())){
                	
                	String fileName = FileUpload.getNomeArquivo(fileItem);
                	
                	File realFile = new File(pathRaiz + pathRelativo + nomeArquivo);
                	
                	if( realFile.exists() ) FileUpload.apagarArquivo(pathRaiz + pathRelativo + nomeArquivo);   
                	        			                	
        			if (!"".equals(fileName)){
        				File arquivoGravado = FileUpload.salvarNoDisco(fileItem, FileUpload.getPathFisico(pathRaiz, pathRelativo, fileName));
        				arquivos.put(fileItem.getFieldName(), arquivoGravado.getName());
        			}
                }
                else if (!fileItem.isFormField() && "".equals(fileItem.getName()) && "_excluir".equals(FileUpload.verificaValorCampo(campos, "hid" + fileItem.getFieldName()))){
                	                	
 //               	String nomeArquivo = FileUpload.verificaValorCampo(campos, "hidNomeArquivo" + fileItem.getFieldName()).trim();
                	
                	if (nomeArquivo != null && !"".equals(nomeArquivo)){           	
	                	File realFile = new File(pathRaiz + pathRelativo + nomeArquivo);	                	                	                	
	                	if( realFile.exists() ){
	                		FileUpload.apagarArquivo(pathRaiz + pathRelativo + nomeArquivo);
	                	}
                	}
                }
            }            
        } catch (Exception e){
            this.logger.error(e);
            throw new ECARException(e);
        }

        return arquivos;
    }
    
    /**
     * Retorna o caminho da imagem personalizada pelo usuário para a função de acompanhamento. <br>
     * Na falta da imagem personalizada, retorna NULL. <br>
     * @author rogerio
     * @since 0.1, 27/02/2007
     * @version 0.1, 27/02/2007
     * @param cor
     * @param funcaoAcomp
     * @param posicao
     * @return String
     * @throws ECARException
     */
    public String getImagemPersonalizada(Cor cor, TipoFuncAcompTpfa funcaoAcomp, String posicao) throws ECARException {
    	String path = null;
    	ConfiguracaoDao cdao = new ConfiguracaoDao(null);
    	    	
    	if( cor != null && cor.getCodCor() != null && funcaoAcomp != null && posicao != null ) {
        	CorTipoFuncAcompCtfa ctfa = null;
        	
        	try {
    	    	Query query = session.createQuery(
    	       		" from CorTipoFuncAcompCtfa " +
    	       		" where comp_id.codCor = :codCor " + 
    				"   and comp_id.codTpfa = :tipoFunc " +
    				"   and comp_id.posicaoCtfa = :posicao ");
    	    	
    	    	query.setLong("codCor", cor.getCodCor());
    	    	query.setLong("tipoFunc", funcaoAcomp.getCodTpfa());
    	    	query.setString("posicao", posicao);
    	
    	    	ctfa = (CorTipoFuncAcompCtfa) query.uniqueResult();
        	} catch( Exception e ) {
        		this.logger.error(e);
        		throw new ECARException("erro.exception");
        	}
        	
        	/* --
        	 * Se o usuario personalizou a imagem para a função de acompanhamento, retorna o caminho para 
        	 * exibição da imagem, caso contrário retorna NULL e o controle deverá ser feito na página.
        	 *  -- */
        	if( ctfa != null && ( ctfa.getCaminhoImagemCtfa() != null || !"".equals(ctfa.getCaminhoImagemCtfa()) ) ) {
        		path = cdao.getConfiguracao().getRaizUpload() + Dominios.PATH_REMOTE_IMAGES + ctfa.getCaminhoImagemCtfa();
        		File file = new File(path);
        		if( !file.exists() ) path = null;
        	}         		
    	} else if( cor != null && cor.getCodCor() != null && funcaoAcomp == null ) {   		
    		path = cdao.getConfiguracao().getRaizUpload() + Dominios.PATH_REMOTE_IMAGES + cor.getCaminhoImagemPontoCriticoCor(); 
    		File file = new File(path);
    		if( !file.exists() ) path = null;
    	}    	
    	
    	return path;
    } // fim getImagemPersonalizada()
    
    /**
     * Retorna o caminho da imagem personalizada pelo usuário do indicador/resultado. <br>
     * Na falta da imagem personalizada, retorna NULL. <br>
     * @author 
     * @since 
     * @version 
     * @param cor
     * @return String
     * @throws ECARException
     */
    public String getImagemPersonalizadaIndResul(Cor cor) throws ECARException {
    	String path = null;
    	ConfiguracaoDao cdao = new ConfiguracaoDao(null);
    	    	
    	if( cor != null && cor.getCodCor() != null) {   		
    		path = cdao.getConfiguracao().getRaizUpload() + Dominios.PATH_REMOTE_IMAGES + cor.getCaminhoImagemIndResulCor(); 
    		File file = new File(path);
    		if( !file.exists() ) path = null;
    	}    	
    	
    	return path;
    } // fim getImagemPersonalizadaIndResul()
    
    public String getImagemIndResul(Cor cor){    
        if(cor != null)
            return cor.getCaminhoImagemIndResulCor();
        else
            return "rBranco9.png";
    }
    
    /**
     * Realiza a exclusão de cor. Antes de excluir efetivamente, obtem os nomes das imagens personalizadas,
     * caso existam e tenta realizar a exclusão dos registros do banco. Caso a exclusão seja bem sucedida, 
     * apaga as imagens do servidor. Caso contrário as mantém para evitar perda de arquivo. <br>
     * @author rogerio
     * @since 0.1, 01/03/2007
     * @version 0.1, 01/03/2007
     * @param cor
     * @throws ECARException
     */
    public void excluirCor(Cor cor) throws ECARException {
    	List<String> listImages = new ArrayList<String>();
    	List<CorTipoFuncAcompCtfa> listCtfa = new ArrayList<CorTipoFuncAcompCtfa>(); 
    	
   		/* -- 
   		 * Obtem o nome das imagens personalizadas do registro da cor e 
   		 * suas funções de acompanhamento 
   		 * -- */   	
    	try {
    		    	
    		//Solução temporária para erro de constraint na exclusão de cor quando esta possui registros no histórico de pontos críticos
    		//Deve-se implementar exclusão lógica de cor para retirar esta validação.
		    if(getHistoricoCorPontosCriticos(cor).size() > 0){
		        		    			    	
		    	String labelPontosCriticos = new FuncaoDao(request).getFuncaoPontosCriticos().getLabelPadraoFun();
		    	
		    	String[] args = new String[1];
				args[0] = labelPontosCriticos;
		    	
			    throw new ECARException("cor.validacao.registroComHistorico", null, args);
		    }
    		
    		// Imagem Ponto Crítico
    		listImages.add(cor.getCaminhoImagemPontoCriticoCor()); 
    		
    		// Imagens de Cor Tipo Função Acompanhamento
	    	Query query = session.createQuery(
	       		" from CorTipoFuncAcompCtfa " +
	       		" where comp_id.codCor = :codCor ");
	    	
	    	query.setLong("codCor", cor.getCodCor());
	    	listCtfa = query.list();
	    	
	    	for( CorTipoFuncAcompCtfa ctfa : listCtfa ) 
	    		listImages.add(ctfa.getCaminhoImagemCtfa());
	    	
    	}catch(ECARException e){
 		   this.logger.error(e);
 	       throw e; 	   
    	} 
//    	catch( Exception e ) {
//    		this.logger.error(e);
//    		throw new ECARException("erro.exception");
//    	}
    	
    	/* -- 
    	 * Apaga os registros do banco de dados.
    	 * -- */
    	try {
    		this.excluir(cor);
    	} catch( Exception e ) {
    		this.logger.error(e);
    		throw new ECARException("erro.exception");
    	}
    	
    	/* -- 
    	 * Se a exclusão dos registros foi bem sucedida, realiza a exclusão 
    	 * das imagens registradas para estas cores no servidor.
    	 * -- */ 
    	try {
	    	ConfiguracaoDao cDao = new ConfiguracaoDao(request);
	    	String pathRoot = cDao.getConfiguracao().getRaizUpload();
	    	String fullFile = "";
	    	
	    	for( int i=0; i<listImages.size(); i++ ) {
	    		if( listImages.get(i) != null ) {
		    		fullFile = pathRoot + Dominios.PATH_REMOTE_IMAGES + listImages.get(i);
		    		
		    		File f = new File(fullFile);
		    		if( f.exists() ) 
		    			FileUpload.apagarArquivo(fullFile);
	    		}
	    	}
    	} catch( Exception e ) {
    		this.logger.error(e);
    		throw new ECARException("erro.exception");
    	}   	
    } // fim excluir()
    
    
	/**
	 *
	 * Consulta as cores das posições presentes no gráfico e inicializa o contador com 0 (Zero); 
	 * 
	 *   @return Map com chave = codCor listadas no relatório (arel) com seus valores zerados
         *   @throws ECARException
         * @author 05110500460
	 */
	
	public Map<String, Integer>  criarMapCodCorPosicoesGeraisGrafico() throws ECARException {
		CorDao corDao = new CorDao(null);

		// Map para as cores configuradas, chave:codigo da cor; valor:quantidade
		// de vez em que apareceu
		Map<String, Integer> mapCoresConfiguradas = new HashMap<String, Integer>();
		
		/* Cores fixas no sistema */
		mapCoresConfiguradas.put(Cor.BRANCO, 0);
		mapCoresConfiguradas.put(Cor.NAO_LIBERADO, 0);
		mapCoresConfiguradas.put(Cor.NAO_ACOMPANHADO, 0);

		Iterator<Cor> itCor = corDao.listar(Cor.class, new String[] { "ordemCor", "asc" }).iterator();
		
		while (itCor.hasNext()) {
			Cor cor = itCor.next();
			
			if ( cor.getIndPosicoesGeraisCor().equals(Pagina.SIM) ) {
				mapCoresConfiguradas.put(cor.getCodCor()+"", 0);
			}
		}
	
		return mapCoresConfiguradas;
	}// fim criarMapCodCorPosicoesGeraisGrafico()
	
	/**
	 * Conta quantas vezes cada cor apareceu nas posições (acompanhamentos)
	 * 
         * @param posicaoBean
         * @return Map com: <br>
	 * 			<b> chave </b>: codigo da cor
         * 			<b> valor </b>: quantas vezes essa cor apareceu
         * @throws ECARException
	 */
	public Map<String, Integer> contadorDePosicoesPorCores (PosicaoBean posicaoBean) throws ECARException{
		int totalNaoAcompanhado=0, totalNaoLiberado=0, totalWhite=0;
		Map<String, Integer> mapCoresConfiguradas = this.criarMapCodCorPosicoesGeraisGrafico();
		Iterator itCoresPosicoes = posicaoBean.getCor().iterator();

		while (itCoresPosicoes.hasNext()) {
			String cor = itCoresPosicoes.next().toString();
			mapCoresConfiguradas.put(cor, mapCoresConfiguradas.get(cor) + 1);
		}
		
		return mapCoresConfiguradas;
		
	} // fim contadorDePosicoesPorCores (PosicaoBean posicaoBean) 
	
	
        /**
         *
         * @param setCores
         * @return
         */
        public List<String> ordenarCores(Set<String>setCores){
		return ordenarCores(new ArrayList<String>(setCores));
	}
	
        /**
         *
         * @param listCores
         * @return
         */
        public List <String> ordenarCores(List<String> listCores){
		
		/*Ordena o map na ordem do código das cores */
		Collections.sort(listCores, 
				 new Comparator() {
				
					public int compare(Object arg0, Object arg1) {
				
						String cor1 = (String) arg0;
						String cor2 = (String) arg1;
						
						boolean codCor1EhValor = Util.ehValor(cor1);
						boolean codCor2EhValor = Util.ehValor(cor2);

						if (codCor1EhValor && codCor2EhValor){
							Long longCor1 =  Long.parseLong(cor1);
							Long longCor2 =  Long.parseLong(cor2);
							return longCor1.compareTo(longCor2);
						} else if (codCor1EhValor ) {
							return -1;
						} else if ( codCor2EhValor){
							return 1; 
						} else { 
							return cor1.compareTo(cor2);
						}
					} //fim compare
				} // fim Comparator
		);// fim sort
		
		return listCores;
	}//fim ordenarCores

    public List<HistoricoCorPtcH> getHistoricoCorPontosCriticos(Cor cor){
    	
    	List<HistoricoCorPtcH> historicoCorPontosCriticos = new ArrayList<HistoricoCorPtcH>();
    	
    	String hql = "Select hcor from HistoricoCorPtcH hcor where hcor.cor.codCor = :codCor" ;
    	
    	Query q = this.session.createQuery(hql);
		q.setLong("codCor", cor.getCodCor());
		
		historicoCorPontosCriticos = q.list();
		
    	return historicoCorPontosCriticos;
    }
       
} // fim class