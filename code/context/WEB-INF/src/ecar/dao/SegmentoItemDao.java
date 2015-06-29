/*
 * Criado em 02/05/2005
 *
 */
package ecar.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.FileUpload;
import comum.util.Mensagem;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.DestaqueItemRelDtqir;
import ecar.pojo.DestaqueSubAreaDtqsa;
import ecar.pojo.SegmentoCategoriaSgtc;
import ecar.pojo.SegmentoItemFonteSgtif;
import ecar.pojo.SegmentoItemLeiauteSgtil;
import ecar.pojo.SegmentoItemSgti;
import ecar.pojo.SegmentoSgt;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.UsuarioUsu;

/**
 * @author evandro
 *
 */
public class SegmentoItemDao extends Dao{
    /**
     * Construtor. Chama o Session factory do Hibernate
     * @param request
     */
    public SegmentoItemDao(HttpServletRequest request) {
		super();
		this.request = request;
    }
    
    /**
     * Salva um registro de segmentoItem a partir de parâmetros recebidos por request por um formulário de Upload.
     * Salva a coleção de aditorias do segmentoItem.
     * Salva a coleção de tipos de acesso do segmentoItem.
     * Salva a imagem selecionada em disco.
     * @param segItem
     * @param campos
     * @param pathRaiz
     * @param pathRelativo
     * @param application
     * @throws ECARException
     */
    public void salvar(SegmentoItemSgti segItem, List campos, String pathRaiz, String pathRelativo, ServletContext application) throws ECARException{        
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();
    		
	        setSegmentoItemUpload(segItem, campos, application, true);
	        segItem.setDataInclusaoSgti(Data.getDataAtual());
	                        
	        if ( ("2".equalsIgnoreCase(segItem.getSegmentoSgt().getCodSgt().toString()))
	         	   || ("3".equalsIgnoreCase(segItem.getSegmentoSgt().getCodSgt().toString())) 
	         	   || ("4".equalsIgnoreCase(segItem.getSegmentoSgt().getCodSgt().toString()))){
	         		
	             	SegmentoItemLeiauteSgtil leiaute = new SegmentoItemLeiauteSgtil();
	             	leiaute.setCodSgtil(segItem.getSegmentoSgt().getCodSgt());
	             	segItem.setSegmentoItemLeiauteSgtil(leiaute);             	
	        }
	        
	        /* segItem.setUsuarioUsu(); */
	        session.save(segItem);
			objetos.add(segItem);

			/* grava a(s) imagem(ns) e atualiza o segmentoItem para vincular o nome da(s) imagem(ns) */
	        uploadImagem(segItem, campos, pathRaiz , pathRelativo);
	        session.update(segItem);
			objetos.add(segItem);
			
			tx.commit();
	
			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC_ALT");
				Iterator itObj = objetos.iterator();
	
				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
		} catch (HibernateException e) {
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
     * Altera um registro de segmentoItem a partir de parâmetros recebidos por request por um formulário de Upload.
     * Altera a coleção de editorias do segmento.
     * Altera a coleção de tipos de acesso do segmento.
     * Salva a imagem selecionada em disco e apaga a uma imagem existente anteriormente.
     * @param segItem
     * @param campos
     * @param pathRaiz
     * @param pathRelativo
     * @param application
     * @throws ECARException
     */
  
    public void alterar(SegmentoItemSgti segItem, List campos, String pathRaiz, String pathRelativo, ServletContext application) throws ECARException{        
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();
    		
	    	
	    	String flagImagem1 = "";
	    	String flagCapa = "";
	    	String flagImagem2 = "";
	    	String flagImagem3 = "";
	    	String flagAnexo = "";
    	
    		if (("".equals(FileUpload.verificaValorCampo(campos, "imagem1Sgti"))) && 
    				(!"".equals(FileUpload.verificaValorCampo(campos, "imagem1")))){
    				if(segItem.getImagem1Stgi()!=null){
    				
    					if (!FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segItem.getImagem1Stgi(), ""))){
    						throw new ECARException("erro.excluirArquivo");
    						
    					}   					
    				}    		
    				
    				flagImagem1 = "excluidaImagem1";
    				
    		} 
    		
    		if (("".equals(FileUpload.verificaValorCampo(campos, "imagemCapaSgti"))) && 
    				(!"".equals(FileUpload.verificaValorCampo(campos, "imagemCapa")))){
				if(segItem.getImagemCapaSgti()!=null){
				
					if (!FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segItem.getImagemCapaSgti(), ""))){
						throw new ECARException("erro.excluirArquivo");
						
					}   					
				}    		
				
				flagCapa = "excluidaCapa";
				
    		} 
    		
    		if (("".equals(FileUpload.verificaValorCampo(campos, "imagem2Sgti"))) && 
    				(!"".equals(FileUpload.verificaValorCampo(campos, "imagem2")))){
				if(segItem.getImagem2Sgti()!=null){
				
					if (!FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segItem.getImagem2Sgti(), ""))){
						throw new ECARException("erro.excluirArquivo");
						
					}   					
				}    		
				
				flagImagem2 = "excluidaImagem2";
				
    		} 
    		
    		if (("".equals(FileUpload.verificaValorCampo(campos, "imagem3Sgti"))) && 
    				(!"".equals(FileUpload.verificaValorCampo(campos, "imagem3")))){
				if(segItem.getImagem3Sgti()!=null){
				
					if (!FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segItem.getImagem3Sgti(), ""))){
						throw new ECARException("erro.excluirArquivo");
						
					}   					
				}    		
				
				flagImagem3 = "excluidaImagem3";
    		} 
    		
    		if (("".equals(FileUpload.verificaValorCampo(campos, "anexoEnderecoSgti"))) && 
    				(!"".equals(FileUpload.verificaValorCampo(campos, "anexo")))){
				if(segItem.getAnexoEnderecoSgti()!=null){
				
					if (!FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segItem.getAnexoEnderecoSgti(), ""))){
						throw new ECARException("erro.excluirArquivo");
						
					}   					
				}    		
				
				flagAnexo = "excluidoAnexo";
    		} 
    	    	
	    	setSegmentoItemUpload(segItem, campos, application, true);
	    	
	    	
	    	if ("excluidaImagem1".equalsIgnoreCase(flagImagem1)){
	    		segItem.setImagem1Stgi("");
				session.save(segItem);
				objetos.add(segItem);
	    	}
	    	
	    	if ("excluidaCapa".equalsIgnoreCase(flagCapa)){
	    		segItem.setImagemCapaSgti("");
				session.save(segItem);
				objetos.add(segItem);
	    	}
	    	
	    	if ("excluidaImagem2".equalsIgnoreCase(flagImagem2)){
	    		segItem.setImagem2Sgti("");
				session.save(segItem);
				objetos.add(segItem);
	    	}
	    	
	    	if ("excluidaImagem3".equalsIgnoreCase(flagImagem3)){
	    		segItem.setImagem3Sgti("");
				session.save(segItem);
				objetos.add(segItem);
	    	}
	    	
	    	if ("excluidoAnexo".equalsIgnoreCase(flagAnexo)){
	    		segItem.setAnexoEnderecoSgti("");
				session.save(segItem);
				objetos.add(segItem);
	    	}
	    	
    		if ("".equals(FileUpload.verificaValorCampo(campos, "legendaImag1Sgti"))){
    			segItem.setLegendaImag1Sgti("");
				session.save(segItem);
				objetos.add(segItem);
    		}
	    		
    		if ("".equals(FileUpload.verificaValorCampo(campos, "legendaImagCapaSgti"))){
    			segItem.setLegendaImagCapaSgti("");
				session.save(segItem);
				objetos.add(segItem);
    		}
	    		
    		if ("".equals(FileUpload.verificaValorCampo(campos, "legendaImag2Sgti"))){
    			segItem.setLegendaImag2Sgti("");
				session.save(segItem);
				objetos.add(segItem);
    		}
    		
    		if ("".equals(FileUpload.verificaValorCampo(campos, "legendaImag3Sgti"))){
    			segItem.setLegendaImag3Sgti("");
				session.save(segItem);
				objetos.add(segItem);
    		}
	    		
    		if ("".equals(FileUpload.verificaValorCampo(campos, "anexoLegendaSgti"))){
    			segItem.setAnexoLegendaSgti("");
				session.save(segItem);
				objetos.add(segItem);
    		}
	    	
	    	uploadImagem(segItem, campos, pathRaiz , pathRelativo);
	    	
	    	if ( ("2".equalsIgnoreCase(segItem.getSegmentoSgt().getCodSgt().toString()))
	    	   || ("3".equalsIgnoreCase(segItem.getSegmentoSgt().getCodSgt().toString())) 
	    	   || ("4".equalsIgnoreCase(segItem.getSegmentoSgt().getCodSgt().toString()))){
	    		
	        	SegmentoItemLeiauteSgtil leiaute = new SegmentoItemLeiauteSgtil();
	        	leiaute.setCodSgtil(segItem.getSegmentoSgt().getCodSgt());
	        	segItem.setSegmentoItemLeiauteSgtil(leiaute);
	        	
	        }
	    	
	    	
	        session.update(segItem);
			objetos.add(segItem);
			
			tx.commit();
	
			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC_ALT");
				Iterator itObj = objetos.iterator();
	
				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
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
     * Exclui um registro de segmentoItem. 
     * Exclui a(s) imagem(ns) e anexo caso existam.
     * @param segItem
     * @param pathRaiz
     * @throws ECARException
     */
    public void excluir(SegmentoItemSgti segItem, String pathRaiz) throws ECARException{                   
    	try{
    		if(segItem.getImagemCapaSgti()!=null && !segItem.getImagemCapaSgti().equals("")){
                if (!FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segItem.getImagemCapaSgti(), ""))){
                    throw new ECARException("erro.excluirArquivo");        
                }                        
            }
    		if(segItem.getImagem1Stgi()!=null && !segItem.getImagem1Stgi().equals("")){
                if (!FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segItem.getImagem1Stgi(), ""))){
                    throw new ECARException("erro.excluirArquivo");        
                }                        
            }
    		if(segItem.getImagem2Sgti()!=null && !segItem.getImagem2Sgti().equals("")){
                if (!FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segItem.getImagem2Sgti(), ""))){
                    throw new ECARException("erro.excluirArquivo");        
                }                        
            }
    		if(segItem.getImagem3Sgti()!=null && !segItem.getImagem3Sgti().equals("")){
                if (!FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segItem.getImagem3Sgti(), ""))){
                    throw new ECARException("erro.excluirArquivo");        
                }                        
            }
    		if(segItem.getAnexoEnderecoSgti()!=null && !segItem.getAnexoEnderecoSgti().equals("")){
                if (!FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segItem.getAnexoEnderecoSgti(), ""))){
                    throw new ECARException("erro.excluirArquivo");        
                }                        
            }
            super.excluir(segItem);                
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("erro.exception");
        }
    }
        
    
    /**
     * A partir de um List de campos obtidos na requsição de um formulário de upload 
     * popula um objeto SegmentoSgt
     * @param segItem
     * @param campos
     * @param application 
     * @param criarCollections
     * @throws ECARException
     */
    public void setSegmentoItemUpload(SegmentoItemSgti segItem, List campos,
    		 	ServletContext application,
				boolean criarCollections) throws ECARException{
        
        try{
        	Mensagem properties = new Mensagem(application);
        	
        	/* codSegmento nunca deve estar vazio */
        	Long codSegmento = Long.valueOf(FileUpload.verificaValorCampo(campos, "codSegmento"));
            
            /* itensFixos são códigos definidos nos links do menu e no arquivo de properties*/
            if (codSegmento.intValue() == (Long.valueOf (properties.getMensagem("admPortal.materias"))).intValue() ||
            			codSegmento.intValue() == (Long.valueOf (properties.getMensagem("admPortal.taxacoes"))).intValue() ||
						codSegmento.intValue() == (Long.valueOf (properties.getMensagem("admPortal.pergFreq"))).intValue() ||
						codSegmento.intValue() == (Long.valueOf (properties.getMensagem("admPortal.glossario"))).intValue())
            	segItem.setSegmentoSgt((SegmentoSgt) buscar(SegmentoSgt.class, codSegmento));
            else{
            	/* combo de segmentos em itens livres */
                if (!"".equals(FileUpload.verificaValorCampo(campos, "segmentoSgt")))
                	segItem.setSegmentoSgt((SegmentoSgt) buscar(SegmentoSgt.class, Long.valueOf(FileUpload.verificaValorCampo(campos, "segmentoSgt"))));
            }
            
            /* objetos que independente de estar ou não na tela devem ser testados */
            if(!"".equals(FileUpload.verificaValorCampo(campos, "segmentoCategoriaSgtc"))){
                segItem.setSegmentoCategoriaSgtc((SegmentoCategoriaSgtc) this.buscar(
                		SegmentoCategoriaSgtc.class,
                        Long.valueOf(FileUpload.verificaValorCampo(campos, "segmentoCategoriaSgtc"))));
            }
            if(!"".equals(FileUpload.verificaValorCampo(campos, "segmentoItemFonteSgtif"))){
                segItem.setSegmentoItemFonteSgtif((SegmentoItemFonteSgtif) this.buscar(
                		SegmentoItemFonteSgtif.class,
                        Long.valueOf(FileUpload.verificaValorCampo(campos, "segmentoItemFonteSgtif"))));
            }
            if(!"".equals(FileUpload.verificaValorCampo(campos, "segmentoItemLeiauteSgtil"))){
                segItem.setSegmentoItemLeiauteSgtil((SegmentoItemLeiauteSgtil) this.buscar(
                		SegmentoItemLeiauteSgtil.class,
                        Long.valueOf(FileUpload.verificaValorCampo(campos, "segmentoItemLeiauteSgtil"))));
            }
            
                       
            /* campos que estão em todas as telas */
            segItem.setTituloSgti(FileUpload.verificaValorCampo(campos, "tituloSgti"));
            segItem.setDataItemSgti(Data.parseDate(FileUpload.verificaValorCampo(campos, "dataItemSgti")));
            segItem.setPalavrasChavesSgti(FileUpload.verificaValorCampo(campos, "palavrasChavesSgti"));
            segItem.setIndUtilizTpAcessoSgti(FileUpload.verificaValorCampo(campos, "indUtilizTpAcessoSgti"));
            segItem.setIndAtivoSgti(FileUpload.verificaValorCampo(campos, "indAtivoSgti"));

            /* campos não estão em todas as telas */
            if(!"".equals(FileUpload.verificaValorCampo(campos, "integraSgti")))
            	segItem.setIntegraSgti(FileUpload.verificaValorCampo(campos, "integraSgti"));
            if(!"".equals(FileUpload.verificaValorCampo(campos, "linhaApoioSgti")))
            	segItem.setLinhaApoioSgti(FileUpload.verificaValorCampo(campos, "linhaApoioSgti"));
            if(!"".equals(FileUpload.verificaValorCampo(campos, "legendaImag1Sgti")))
            	segItem.setLegendaImag1Sgti(FileUpload.verificaValorCampo(campos, "legendaImag1Sgti"));
            if(!"".equals(FileUpload.verificaValorCampo(campos, "legendaImag2Sgti")))
            	segItem.setLegendaImag2Sgti(FileUpload.verificaValorCampo(campos, "legendaImag2Sgti"));
            if(!"".equals(FileUpload.verificaValorCampo(campos, "legendaImag3Sgti")))
            	segItem.setLegendaImag3Sgti(FileUpload.verificaValorCampo(campos, "legendaImag3Sgti"));
            if(!"".equals(FileUpload.verificaValorCampo(campos, "legendaImagCapaSgti")))
            	segItem.setLegendaImagCapaSgti(FileUpload.verificaValorCampo(campos, "legendaImagCapaSgti"));
            if(!"".equals(FileUpload.verificaValorCampo(campos, "anexoLegendaSgti")))
            	segItem.setAnexoLegendaSgti(FileUpload.verificaValorCampo(campos, "anexoLegendaSgti"));
            if(!"".equals(FileUpload.verificaValorCampo(campos, "urlLinkSgti")))
            	segItem.setUrlLinkSgti(FileUpload.verificaValorCampo(campos, "urlLinkSgti"));
            /* datas em branco são setadas null pelo parseDate*/
           	segItem.setDataIniValidadeSgti(Data.parseDate(FileUpload.verificaValorCampo(campos, "dataIniValidadeSgti")));
           	segItem.setDataFimValidadeSgti(Data.parseDate(FileUpload.verificaValorCampo(campos, "dataFimValidadeSgti")));
           	if(!"".equals(FileUpload.verificaValorCampo(campos, "indSuperDestaqueSgti")))
           		segItem.setIndSuperDestaqueSgti(FileUpload.verificaValorCampo(campos, "indSuperDestaqueSgti"));
           	if(!"".equals(FileUpload.verificaValorCampo(campos, "indDestaqueSgti")))
           		segItem.setIndDestaqueSgti(FileUpload.verificaValorCampo(campos, "indDestaqueSgti"));
           	
           	
           	if(criarCollections){
            	criarCollectionEditorias(segItem, campos);
            	criarCollectionTipoAcesso(segItem, campos);
            }
        } catch(Exception e){
            this.logger.error(e);
            throw new ECARException(e);
        }
        
    }
    
    
    /**
     * A partir de dados passados por request popula um objeto SegmentoItemSgti
     * @param segItem
     * @param campos
     * @param recuperarParametrosComoString indica se irá recuperar dados nulos como String vazia
     * @param criarCollections
     * @param application
     * @throws ECARException
     */
    public void setSegmentoItem(SegmentoItemSgti segItem, 
                        HttpServletRequest campos,                         
                        boolean recuperarParametrosComoString,
						ServletContext application,
						boolean criarCollections) throws ECARException{
        
        try{
        	Mensagem properties = new Mensagem(application);
        	
            /* codSegmento nunca deve estar vazio */
       		Long codSegmento = Long.valueOf(Pagina.getParamStr(campos, "codSegmento"));
       		
       		/* itensFixos são códigos definidos nos links do menu e no arquivo de properties*/
       		if (codSegmento.intValue() == (Long.valueOf (properties.getMensagem("admPortal.materias"))).intValue() ||
        			codSegmento.intValue() == (Long.valueOf (properties.getMensagem("admPortal.taxacoes"))).intValue() ||
					codSegmento.intValue() == (Long.valueOf (properties.getMensagem("admPortal.pergFreq"))).intValue() ||
					codSegmento.intValue() == (Long.valueOf (properties.getMensagem("admPortal.glossario"))).intValue()){
       			
       			SegmentoSgt segmento = (SegmentoSgt) buscar(SegmentoSgt.class, codSegmento);       		
       			
       			/* Fazemos um size na coleção de atributos para que o objeto seja carregado com a coleção
       			 * pois na pesquisa, se não encontrar resultado, o objeto é incluído na sessão e sem 
       			 * esse procedimento, ao carregar a lista, ele se perde*/
       			if(segmento.getSisGrupoAtributoSga() != null)
       				if(segmento.getSisGrupoAtributoSga().getSisAtributoSatbs() != null)
       					segmento.getSisGrupoAtributoSga().getSisAtributoSatbs().size();
       			
   				if(segmento.getSegmentoCategoriaSgtcs() != null)
           			segmento.getSegmentoCategoriaSgtcs().size();
       			
        		segItem.setSegmentoSgt(segmento);
       		}else{
        		/* combo de segmentos em itens livres */
                if (!"".equals(Pagina.getParamStr(campos, "segmentoSgt"))){
                	SegmentoSgt segmento = (SegmentoSgt) buscar(SegmentoSgt.class, Long.valueOf(Pagina.getParamStr(campos, "segmentoSgt")));       		
           			
           			/* Fazemos um size na coleção de atributos para que o objeto seja carregado com a coleção
           			 * pois na pesquisa, se não encontrar resultado, o objeto é incluído na sessão e sem 
           			 * esse procedimento, ao carregar a lista, ele se perde*/
                	if(segmento.getSisGrupoAtributoSga() != null)
                		if(segmento.getSisGrupoAtributoSga().getSisAtributoSatbs() != null)
                			segmento.getSisGrupoAtributoSga().getSisAtributoSatbs().size();
                	
                	if(segmento.getSegmentoCategoriaSgtcs() != null)
                   		segmento.getSegmentoCategoriaSgtcs().size();
           			
                  	segItem.setSegmentoSgt(segmento);
                }
        	}
            
       		/* objetos que independente de estar ou não na tela devem ser testados */
            if(!"".equals(Pagina.getParamStr(campos, "segmentoCategoriaSgtc"))){
                segItem.setSegmentoCategoriaSgtc((SegmentoCategoriaSgtc) this.buscar(
                		SegmentoCategoriaSgtc.class,
                        Long.valueOf(Pagina.getParamStr(campos, "segmentoCategoriaSgtc"))));
            }
            if(!"".equals(Pagina.getParamStr(campos, "segmentoItemFonteSgtif"))){
                segItem.setSegmentoItemFonteSgtif((SegmentoItemFonteSgtif) this.buscar(
                		SegmentoItemFonteSgtif.class,
                        Long.valueOf(Pagina.getParamStr(campos, "segmentoItemFonteSgtif"))));
            }
            if(!"".equals(Pagina.getParamStr(campos, "segmentoItemLeiauteSgtil"))){
                segItem.setSegmentoItemLeiauteSgtil((SegmentoItemLeiauteSgtil) this.buscar(
                		SegmentoItemLeiauteSgtil.class,
                        Long.valueOf(Pagina.getParamStr(campos, "segmentoItemLeiauteSgtil"))));
            }

            if(recuperarParametrosComoString){
            	/* campos que estão em todas as telas */
            	segItem.setTituloSgti(Pagina.getParamStr(campos, "tituloSgti"));
            	segItem.setDataItemSgti(Pagina.getParamDataBanco(campos, "dataItemSgti"));
            	segItem.setPalavrasChavesSgti(Pagina.getParamStr(campos, "palavrasChavesSgti"));
            	segItem.setIndUtilizTpAcessoSgti(Pagina.getParamStr(campos, "indUtilizTpAcessoSgti"));
            	segItem.setIndAtivoSgti(Pagina.getParamStr(campos, "indAtivoSgti"));
            	
                /* campos não estão em todas as telas */
               	segItem.setLinhaApoioSgti(Pagina.getParamStr(campos, "linhaApoioSgti"));
               	segItem.setIntegraSgti(Pagina.getParamStr(campos, "integraSgti"));
                segItem.setLegendaImag1Sgti(Pagina.getParamStr(campos, "legendaImag1Sgti"));
               	segItem.setLegendaImag2Sgti(Pagina.getParamStr(campos, "legendaImag2Sgti"));
               	segItem.setLegendaImag3Sgti(Pagina.getParamStr(campos, "legendaImag3Sgti"));
               	segItem.setLegendaImagCapaSgti(Pagina.getParamStr(campos, "legendaImagCapaSgti"));
               	segItem.setAnexoLegendaSgti(Pagina.getParamStr(campos, "anexoLegendaSgti"));
               	segItem.setUrlLinkSgti(Pagina.getParamStr(campos, "urlLinkSgti"));
                /* datas em branco são setadas null*/
                segItem.setDataIniValidadeSgti(Pagina.getParamDataBanco(campos, "dataIniValidadeSgti"));
                segItem.setDataFimValidadeSgti(Pagina.getParamDataBanco(campos, "dataFimValidadeSgti"));
            	segItem.setIndSuperDestaqueSgti(Pagina.getParamStr(campos, "indSuperDestaqueSgti"));
            	segItem.setIndDestaqueSgti(Pagina.getParamStr(campos, "indDestaqueSgti"));
            } else {
				/* campos que estão em todas as telas */
				segItem.setTituloSgti(Pagina.getParam(campos, "tituloSgti"));
            	segItem.setDataItemSgti(Pagina.getParamDataBanco(campos, "dataItemSgti"));
            	segItem.setPalavrasChavesSgti(Pagina.getParam(campos, "palavrasChavesSgti"));
            	segItem.setIndUtilizTpAcessoSgti(Pagina.getParam(campos, "indUtilizTpAcessoSgti"));
            	segItem.setIndAtivoSgti(Pagina.getParam(campos, "indAtivoSgti"));
            	
                /* campos não estão em todas as telas */
            	segItem.setLinhaApoioSgti(Pagina.getParam(campos, "linhaApoioSgti"));
               	segItem.setIntegraSgti(Pagina.getParam(campos, "integraSgti"));
               	segItem.setLegendaImag1Sgti(Pagina.getParam(campos, "legendaImag1Sgti"));
               	segItem.setLegendaImag2Sgti(Pagina.getParam(campos, "legendaImag2Sgti"));
               	segItem.setLegendaImag3Sgti(Pagina.getParam(campos, "legendaImag3Sgti"));
               	segItem.setLegendaImagCapaSgti(Pagina.getParam(campos, "legendaImagCapaSgti"));
               	segItem.setAnexoLegendaSgti(Pagina.getParam(campos, "anexoLegendaSgti"));
               	segItem.setUrlLinkSgti(Pagina.getParam(campos, "urlLinkSgti"));
                /* datas em branco são setadas null*/
                segItem.setDataIniValidadeSgti(Pagina.getParamDataBanco(campos, "dataIniValidadeSgti"));
                segItem.setDataFimValidadeSgti(Pagina.getParamDataBanco(campos, "dataFimValidadeSgti"));
                segItem.setIndSuperDestaqueSgti(Pagina.getParam(campos, "indSuperDestaqueSgti"));
                segItem.setIndDestaqueSgti(Pagina.getParam(campos, "indDestaqueSgti"));
            }
            
            if(criarCollections){
            	if (campos.getParameterValues("editoriasSisAtributoSatb") != null)
            		criarCollectionEditoriasPesquisa(segItem, campos);
            	if (campos.getParameterValues("tpAcessoSisAtributoSatb") != null)
            		criarCollectionTipoAcessoPesquisa(segItem, campos);
            }
        } catch(Exception e){
            this.logger.error(e);
            throw new ECARException(e);
        }
        
    }
    
    
    /**
     * Cria a coleção de atributos de Editorias de um SegmentoItem (Inclusão e alteração)
     * @param segmentoItem
     * @param request
     */
    private void criarCollectionEditorias(SegmentoItemSgti segItem, List campos) throws ECARException{
        try{
            Object[] codigosEditorias = FileUpload.verificaValorCampoArray(campos, "editoriasSisAtributoSatb");
            segItem.setSegmentoSisAtribSgtsas(new HashSet());
            for(int i = 0; i < codigosEditorias.length; i++){
                SisAtributoSatb atributo = (SisAtributoSatb) super.buscar(SisAtributoSatb.class, Long.valueOf(codigosEditorias[i].toString()));
               	segItem.getSegmentoSisAtribSgtsas().add(atributo);
            }            
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException(e);
        }
    }
    
    
    /**
     * Cria a coleção de atributos de Tipo de Acesso de um SegmentoItem (inclusão e alteração)
     * @param segmentoItem
     * @param request
     */
    private void criarCollectionTipoAcesso(SegmentoItemSgti segItem, List campos) throws ECARException{
        try{
            Object[] codigosTpAcesso = FileUpload.verificaValorCampoArray(campos, "tpAcessoSisAtributoSatb");
            segItem.setSegmentoItemTpacesSgtitas(new HashSet());
            for(int i = 0; i < codigosTpAcesso.length; i++){
                SisAtributoSatb atributo = (SisAtributoSatb) super.buscar(SisAtributoSatb.class, Long.valueOf(codigosTpAcesso[i].toString()));
               	segItem.getSegmentoItemTpacesSgtitas().add(atributo);
            }            
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException(e);
        }
    }
    
    
    /**
     * Cria a coleção de atributos de Editorias de um SegmentoItem para Pesquisa
     * @param segmentoItem
     * @param request
     */
    private void criarCollectionEditoriasPesquisa(SegmentoItemSgti segItem, HttpServletRequest campos) throws ECARException{
        try{
            Object[] codigosEditorias = campos.getParameterValues("editoriasSisAtributoSatb");
            segItem.setSegmentoSisAtribSgtsas(new HashSet());
            for(int i = 0; i < codigosEditorias.length; i++){
                SisAtributoSatb atributo = (SisAtributoSatb) super.buscar(SisAtributoSatb.class, Long.valueOf(codigosEditorias[i].toString()));
               	segItem.getSegmentoSisAtribSgtsas().add(atributo);
            }            
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException(e);
        }
    }
    
    
    /**
     * Cria a coleção de atributos de Tipo de Acesso de um SegmentoItem para Pesquisa
     * @param segmentoItem
     * @param request
     */
    private void criarCollectionTipoAcessoPesquisa(SegmentoItemSgti segItem, HttpServletRequest campos) throws ECARException{
        try{
            Object[] codigosTpAcesso = campos.getParameterValues("tpAcessoSisAtributoSatb");
            segItem.setSegmentoItemTpacesSgtitas(new HashSet());
            for(int i = 0; i < codigosTpAcesso.length; i++){
                SisAtributoSatb atributo = (SisAtributoSatb) super.buscar(SisAtributoSatb.class, Long.valueOf(codigosTpAcesso[i].toString()));
               	segItem.getSegmentoItemTpacesSgtitas().add(atributo);
            }            
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException(e);
        }
    }
    
    
    /**
     * Realiza o upload dos arquivos do Segmento Item. 
     * Seta no objeto segmentoItem referência para tal.
     * Apaga o arquivo existente anteriormente em caso de alteração.
     * @param segItem
     * @param campos
     * @param pathRaiz
     * @param pathRelativo
     * @throws ECARException
     */
    public void uploadImagem(SegmentoItemSgti segItem, List campos, String pathRaiz, String pathRelativo) throws ECARException{
        try{ 
            Iterator it = campos.iterator();  
            while(it.hasNext()){
                FileItem fileItem = (FileItem) it.next();             
                
                if(!fileItem.isFormField() && !"".equals(fileItem.getName())){
                	if("imagemCapaSgti".equals(fileItem.getFieldName())){
	                    if(segItem.getImagemCapaSgti() != null)
	                        FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segItem.getImagemCapaSgti(), ""));                        
	                    
	                    File arquivoGravado = FileUpload.salvarNoDisco(fileItem, 
	                    		FileUpload.getPathFisico(pathRaiz, 
	                    		pathRelativo, FileUpload.getNomeArquivo(fileItem)));
	                    
	                    segItem.setImagemCapaSgti(FileUpload.getPathFisico("", 
	                            pathRelativo, FileUpload.getNomeArquivo(fileItem)));
	            
                	}
	            
                	if("imagem1Sgti".equals(fileItem.getFieldName())){
	                    if(segItem.getImagem1Stgi() != null)
	                        FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segItem.getImagem1Stgi(), ""));                        
	                    
	                    File arquivoGravado = FileUpload.salvarNoDisco(fileItem, 
	                    		FileUpload.getPathFisico(pathRaiz, 
	                    		pathRelativo, FileUpload.getNomeArquivo(fileItem)));
	                    
	                    segItem.setImagem1Stgi(FileUpload.getPathFisico("", 
	                            pathRelativo, FileUpload.getNomeArquivo(fileItem)));
	            
                	}
                	
                	if("imagem2Sgti".equals(fileItem.getFieldName())){
	                    if(segItem.getImagem2Sgti() != null)
	                        FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segItem.getImagem2Sgti(), ""));                        
	                    
	                    File arquivoGravado = FileUpload.salvarNoDisco(fileItem, 
	                    		FileUpload.getPathFisico(pathRaiz, 
	                    		pathRelativo, FileUpload.getNomeArquivo(fileItem)));
	                    
	                    segItem.setImagem2Sgti(FileUpload.getPathFisico("", 
	                            pathRelativo, FileUpload.getNomeArquivo(fileItem)));
	            
                	}
                	
                	if("imagem3Sgti".equals(fileItem.getFieldName())){
	                    if(segItem.getImagem3Sgti() != null)
	                        FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segItem.getImagem3Sgti(), ""));                        
	                    
	                    File arquivoGravado = FileUpload.salvarNoDisco(fileItem, 
	                    		FileUpload.getPathFisico(pathRaiz, 
	                    		pathRelativo, FileUpload.getNomeArquivo(fileItem)));
	                    
	                    segItem.setImagem3Sgti(FileUpload.getPathFisico("", 
	                            pathRelativo, FileUpload.getNomeArquivo(fileItem)));
	            
                	}
                	
                	if("anexoEnderecoSgti".equals(fileItem.getFieldName())){
	                    if(segItem.getAnexoEnderecoSgti() != null)
	                        FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segItem.getAnexoEnderecoSgti(), ""));                        
	                    
	                    File arquivoGravado = FileUpload.salvarNoDisco(fileItem, 
	                    		FileUpload.getPathFisico(pathRaiz, 
	                    		pathRelativo, FileUpload.getNomeArquivo(fileItem)));
	                    
	                    segItem.setAnexoEnderecoSgti(FileUpload.getPathFisico("", 
	                            pathRelativo, FileUpload.getNomeArquivo(fileItem)));
	            
                	}
                }
            }            
        } catch (Exception e){
            this.logger.error(e);
            throw new ECARException(e);
        }

    }
    
    
    /**
     * Invoca o método pesquisar do Dao e itera sobre o resultado para inicializar as 
     * 		Collections de Tipo de Acesso e de Editorias de Cada objeto retornado.
     * Para a pesquisa de itens livres, quando não informado o segmento não deve 
     * 		selecionar segmentoItem dos segmentos fixos.
     * @param segItem
     * @param application
     * @return
     * @throws ECARException
     */
    public List pesquisar(SegmentoItemSgti segItem, ServletContext application) throws ECARException{
    	try{
	    	List pesquisa = new ArrayList();
	    	
	    	pesquisa = super.pesquisar(segItem, new String[] {"tituloSgti","asc"});
	    	
	    	if(segItem.getSegmentoSgt() == null){
	    		List itensFixos = new ArrayList();
	    		Mensagem properties = new Mensagem(application);
	        	StringBuilder select = new StringBuilder("select segItem from SegmentoItemSgti segItem")
	        						.append(" where segItem.segmentoSgt.codSgt in (")
	        						.append(properties.getMensagem("admPortal.itensFixos"))
	        						.append(")");
	            
	        	Query query = this.getSession().createQuery(select.toString());
	            itensFixos = query.list();
	            pesquisa.removeAll(itensFixos);
	    	}
	    	
	    	if(segItem.getSegmentoSisAtribSgtsas() != null){
	    		/*
	             * Percorre o resultado e retira dele todas os segmentosItem onde
	             * dentro da coleção de SegmentoSisAtrib não existam todos os
	             * atributos de SegmentoSisAtrib passados no objeto de pesquisa
	             */
	    		List atributosPesquisa = new ArrayList();
	    		Iterator itAtbPesquisa = segItem.getSegmentoSisAtribSgtsas().iterator();
	            while (itAtbPesquisa.hasNext())
	                atributosPesquisa.add(((SisAtributoSatb) itAtbPesquisa.next()));
	    		
	    		Iterator it = pesquisa.iterator();
	            while (it.hasNext()) {
	                List atributosResultado = new ArrayList();
	                Iterator itAtribResultado = ((SegmentoItemSgti) it.next()).getSegmentoSisAtribSgtsas().iterator();
	                
	                while (itAtribResultado.hasNext())
	                	atributosResultado.add((SisAtributoSatb) itAtribResultado.next());
	                
	                if (!atributosResultado.containsAll(atributosPesquisa))
	                    it.remove();
	            }
	    	}
	    	
	    	if(segItem.getSegmentoItemTpacesSgtitas() != null){
	    		/*
	             * Percorre o resultado e retira dele todas os segmentosItem onde
	             * dentro da coleção de SegmentoItemTpaces não existam todos os
	             * atributos de SegmentoItemTpaces passados no objeto de pesquisa
	             */
	    		List atributosPesquisa = new ArrayList();
	    		Iterator itAtbPesquisa = segItem.getSegmentoItemTpacesSgtitas().iterator();
	            while (itAtbPesquisa.hasNext())
	                atributosPesquisa.add(((SisAtributoSatb) itAtbPesquisa.next()));
	    		
	    		Iterator it = pesquisa.iterator();
	            while (it.hasNext()) {
	                List atributosResultado = new ArrayList();
	                Iterator itAtribResultado = ((SegmentoItemSgti) it.next()).getSegmentoItemTpacesSgtitas().iterator();
	                
	                while (itAtribResultado.hasNext())
	                	atributosResultado.add((SisAtributoSatb) itAtribResultado.next());
	                
	                if (!atributosResultado.containsAll(atributosPesquisa))
	                    it.remove();
	            }
	    	}
	    	
				
	    	if(pesquisa.size() > 0){
	           Iterator it = pesquisa.iterator();
	           while(it.hasNext()){
	                SegmentoItemSgti sgti = (SegmentoItemSgti) it.next();
	                sgti.getSegmentoItemTpacesSgtitas().size();
	                sgti.getSegmentoSisAtribSgtsas().size();
                	sgti.getSegmentoSgt().getSegmentoCategoriaSgtcs().size();
	                
                	if(sgti.getSegmentoSgt().getSisGrupoAtributoSga() != null)
                		sgti.getSegmentoSgt().getSisGrupoAtributoSga().getSisAtributoSatbs().size();
	           }           
	    	}
	    	return pesquisa;
    	}catch(Exception e){
            this.logger.error(e);
            throw new ECARException(e);
    	}
    }
    
    /**
     * Retorna os código(IDs) dos atributos cadastrados em Tipo de Acesso
     * 		 para o segmentoItem
     * @param segItem
     * @return lista
     * @throws ECARException
     */
    public List getIdsTpAcessoSelecionados(SegmentoItemSgti segItem) throws ECARException{
    	List lista = new ArrayList();
    	List atributos = new ArrayList();
    	
    	if (segItem.getSegmentoItemTpacesSgtitas() != null && segItem.getSegmentoItemTpacesSgtitas().size() > 0)
			atributos.addAll(segItem.getSegmentoItemTpacesSgtitas());
    	
    	Iterator it = atributos.iterator();
    	
    	while(it.hasNext()){
    		SisAtributoSatb atb = (SisAtributoSatb) it.next();
    		lista.add(atb.getCodSatb());
    	}
    	
    	return lista;
    }
    
    
    /**
     * Retorna os código(IDs) dos atributos cadastrados em Editorias
     * 		 para o segmentoItem
     * @param segItem
     * @return lista
     * @throws ECARException
     */
    public List getIdsEditSelecionados(SegmentoItemSgti segItem) throws ECARException{
    	List lista = new ArrayList();
    	List atributos = new ArrayList();
    	
    	if (segItem.getSegmentoSisAtribSgtsas() != null && segItem.getSegmentoSisAtribSgtsas().size() > 0)
    		atributos.addAll(segItem.getSegmentoSisAtribSgtsas());
    	
    	Iterator it = atributos.iterator();
    	
    	while(it.hasNext()){
    		SisAtributoSatb atb = (SisAtributoSatb) it.next();
    		lista.add(atb.getCodSatb());
    	}
    	
    	return lista;
    }
    
    /**
     * Método que cria a lista de editorias de um segmento
     * @param segItem
     * @return lista
     */
    public List getEditoriasBySegItem(SegmentoItemSgti segItem){
    	List lista = new ArrayList();
    	
    	if(segItem.getSegmentoSgt() != null)
			if(segItem.getSegmentoSgt().getSisGrupoAtributoSga() != null)
				if(segItem.getSegmentoSgt().getSisGrupoAtributoSga().getSisAtributoSatbs() != null && segItem.getSegmentoSgt().getSisGrupoAtributoSga().getSisAtributoSatbs().size() > 0)
					lista.addAll(segItem.getSegmentoSgt().getSisGrupoAtributoSga().getSisAtributoSatbs());

		return lista;
    }
    
    /** 
     * Retorna lista com todos os itens de segmentos não vinculados a uma sub-área de destaque
     * @param categoria
     * @param destaqueSubArea
     * @return
     * @throws ECARException
     */
    public Collection getSegmentosItemNaoVinculadosASubArea(SegmentoCategoriaSgtc categoria, DestaqueSubAreaDtqsa destaqueSubArea) throws ECARException{
    	try{
        	Query query = this.getSession().createQuery(
        			"select sgti from SegmentoItemSgti sgti " +
        			"where sgti.indAtivoSgti = 'S' " +
        			"and sgti.segmentoCategoriaSgtc.codSgtc = :codSgtc " +
        			"order by sgti.dataItemSgti asc");
        	query.setLong("codSgtc", categoria.getCodSgtc().longValue());
        	
        	Collection itensDaCategoriaAtivos = query.list();
        	List itensDaSubArea = new ArrayList();
        	Iterator it = destaqueSubArea.getDestaqueItemRelDtqirs().iterator();
        	while(it.hasNext()){
        		DestaqueItemRelDtqir destaque = (DestaqueItemRelDtqir) it.next();
        		if(destaque.getSegmentoItemSgti() != null)
        			itensDaSubArea.add(destaque.getSegmentoItemSgti());
        	}
        	itensDaCategoriaAtivos.removeAll(itensDaSubArea);
        	return itensDaCategoriaAtivos;    		
    	} catch(HibernateException e){
            this.logger.error(e);
    		throw new ECARException(e);
    	}
    }
    
    
    /**
     * Seleciona o SegmentoItem Matéria/Artigo para Destaque da capa do portal,
     * 		seja o mais recente, e que esteja dentro da validade
     * @param application
     * @return
     */
    public SegmentoItemSgti getSegmentoItemSuperDestaque(ServletContext application){
    	try{
    		Mensagem properties = new Mensagem(application);
    		
	    	Date dataAtual = Data.getDataAtual();

	    	StringBuilder select = new StringBuilder("select segItem from SegmentoItemSgti segItem")
	    						.append(" where segItem.segmentoSgt.codSgt = :codSgt")
	    						.append(" and segItem.indSuperDestaqueSgti = 'S'")
	    						.append(" and ( segItem.dataIniValidadeSgti <= :dataAtual")
	    						.append(" and segItem.dataFimValidadeSgti >= :dataAtual )")
	    						.append(" order by segItem.dataItemSgti desc");
	    	
        	Query query = this.getSession().createQuery(select.toString());
        	query.setLong("codSgt", Long.valueOf(properties.getMensagem("admPortal.materias")).longValue());
        	query.setDate("dataAtual", dataAtual);
        	query.setMaxResults(1);
        	
        	//Date dataMaisRecenteSegItem = getDataMaisRecenteSegmentoItemSuperDestaque();
	        //if(dataMaisRecenteSegItem != null){	
	        	//String select = "select segItem from SegmentoItemSgti segItem" + 
				//		" where segItem.indSuperDestaqueSgti = 'S'" +" + usuario.getCodUsu() + " 
				//		" and segItem.dataItemSgti <= :dataMaisRecente" +
				//		" and ( segItem.dataIniValidade <= :dataAtual" +
				//		" and segItem.dataFimValidade >= :dataAtual )";
	        	//Query query = this.getSession().createQuery(select);
	        	//query.setDate("dataMaisRecente", dataMaisRecenteSegItem);
	        	//query.setDate("dataAtual", dataAtual);
	        	
	        	List lista = query.list();
	        	Iterator it = lista.iterator();
	        	
	        	if(it.hasNext()){
	        		SegmentoItemSgti segItem = (SegmentoItemSgti) it.next();
	        		return segItem;
	        	}
	    	//}
	    	
	    	return null;
    	}catch(HibernateException e){
            this.logger.error(e);
    		return null;
    	}
    }
    
    /**
     * 
     * @return Date
     */
    
    public Date getDataMaisRecenteSegmentoItemSuperDestaque(){
       	try{
       		StringBuilder select = new StringBuilder("select max(segItem.dataItemSgti) from SegmentoItemSgti segItem")
       								.append(" where segItem.indSuperDestaqueSgti = 'S'");
       		
       		Query query = this.getSession().createQuery(select.toString());
			query.setMaxResults(1);

        	return (Date) query.uniqueResult();
        	
       	}catch (HibernateException e){
       		this.logger.error(e);
       		return null;
       	}
    }
    
    
    /**
     * Seleciona 3 (ou mais, se passado parametro "qtd") outras Matérias/Artigos mais recentes em SegmentoItem
     * 		diferente da Capa Destaque e que esteja na validade 
     * @param application
     * @param usuario
     * @return
     */
    public List getSegmentoItemMaisArtigos(ServletContext application,UsuarioUsu usuario){
    	List lista = new ArrayList();
    	try{
    		Mensagem properties = new Mensagem(application);
    		
	    	Date dataAtual = Data.getDataAtual();
	    	SegmentoItemSgti segItemSuper = getSegmentoItemSuperDestaque(application);

	    	String todos = null;
	    	try {	
	    		todos = Pagina.getParamStr(request, "todos");	
	    	}catch(NullPointerException ne){
	    		//não é necessário logar exceção aqui
	    	}
	    	
	    	StringBuilder select = new StringBuilder("select i from SegmentoItemSgti i where i.indAtivoSgti = 'S' ")
	    						.append(" and ( i.dataIniValidadeSgti <= :dataAtual")
	    						.append(" and i.dataFimValidadeSgti >= :dataAtual )")
	    						.append(" and i.segmentoSgt.codSgt = :codSgt ")
	    						.append("and ((i.indUtilizTpAcessoSgti = 'N') or ")
	    						.append("(i.indUtilizTpAcessoSgti = 'S' and i.codSgti in (")
	    						.append("select b.comp_id.codSgti from SegmentoItemTpAcesSgtITA b ")
	    						.append("where b.comp_id.codSatb in (")
	    						.append("select a.comp_id.codSatb from UsuarioAtributoUsua a ")
	    						.append("where a.comp_id.codUsu = :codUsu))))");
	    	
	    	
			if ((segItemSuper != null)&&(todos == null)){
					select.append(" and i.codSgti != :codSgti");
					select.append(" and i.indSuperDestaqueSgti = 'S' ");
			}
	    				
			if (todos == ""){
				select.append(" and ((i.indDestaqueSgti = 'S') or (i.indDestaqueSgti = 'N' and i.indSuperDestaqueSgti = 'S')) ");
			}
			
			String order = " order by i.dataItemSgti desc";
        				
	    	Query query = this.getSession().createQuery(select + order);
			query.setLong("codSgt", Long.valueOf(properties.getMensagem("admPortal.materias")).longValue());
        	query.setDate("dataAtual", dataAtual);
        	query.setLong("codUsu", usuario.getCodUsu().longValue());
        	        	
        	if ((segItemSuper != null)&&(todos == null))
				query.setLong("codSgti", segItemSuper.getCodSgti().longValue());        	   	
        	
        	String qtd = null, qtdPag = null, numPag = null;
        	
        	try {
        		qtd = Pagina.getParamStr(request, "qtd");
        		qtdPag = Pagina.getParamStr(request, "qtdPag");
        		numPag = Pagina.getParamStr(request, "numPag");
        	}catch(NullPointerException ne){
	    		//não é necessário logar exceção aqui
        	}
        	
        	if ((qtd != null)&&(!"".equals(qtd)))	 {
        		if (!"00".equals(qtd))
        			query.setMaxResults(Integer.parseInt(qtd));
        	}
        	//else	query.setMaxResults(3);
        	/*
        	if (((qtdPag != null)&&(!"".equals(qtdPag))) && (numPag != null)&&(!"".equals(numPag))) {
				query.setMaxResults(Integer.parseInt(qtdPag));
				query.setFirstResult( ((Integer.parseInt(numPag)-1) * Integer.parseInt(qtdPag)));
			}
        	*/
        	lista = query.list(); 
        	
        	return lista; 
    	}catch(HibernateException e){
    		this.logger.error(e);
    		return lista;
    	}
    }
    
    /**
     * Seleciona 3 outras imagens da mídia/Clips mais recentes em SegmentoItem
     * 		diferente da Capa Destaque e que esteja na validade 
     * @param application
     * @return
     */
    public List getTaxacaoClip(ServletContext application){
    	List lista = new ArrayList();
    	try{
    		
	    	Date dataAtual = Data.getDataAtual();
	    	SegmentoItemSgti segItemSuper = getSegmentoItemSuperDestaque(application);

	    	StringBuilder select = new StringBuilder("select segItem from SegmentoItemSgti segItem")
	    						.append(" where segItem.segmentoSgt.codSgt = :codSgt")
	    						.append(" and ( segItem.dataIniValidadeSgti <= :dataAtual")
	    						.append(" and segItem.dataFimValidadeSgti >= :dataAtual )");
			if (segItemSuper != null) {
				select.append(" and segItem.codSgti != :codSgti");
			}
			
			select.append(" order by segItem.dataItemSgti desc");
        	
			Query query = this.getSession().createQuery(select.toString());
			query.setLong("codSgt", 2);
        	query.setDate("dataAtual", dataAtual);
        	if (segItemSuper != null)
				query.setLong("codSgti", segItemSuper.getCodSgti().longValue());
        	query.setMaxResults(3);
        	
        	lista = query.list(); 
	        
        	return lista; 
    	}catch(HibernateException e){
    		this.logger.error(e);
    		return lista;
    	}
    }
    
    /**
     * Devolve uma lista de ítens de dúvidas frequentes visíveis ao usuário 
     * @param categoria 
     * @param usuario 
     * @return List
     * @throws ECARException
     */
    public List getListSegmentoItensVinculadosAoUsuario(SegmentoCategoriaSgtc categoria, UsuarioUsu usuario) throws ECARException {
        List lista = null;
        
        try {
            
        	StringBuilder select = new StringBuilder("select i from SegmentoItemSgti i where i.indAtivoSgti = 'S' ")
        						.append("and i.segmentoCategoriaSgtc.codSgtc = :codSgtc ")
        						.append(" and i.segmentoSgt.codSgt = 3 ")
        						.append("and ((i.indUtilizTpAcessoSgti = 'N') or ")
        						.append("(i.indUtilizTpAcessoSgti = 'S' and i.codSgti in (")
        						.append("select b.comp_id.codSgti from SegmentoItemTpAcesSgtITA b ")
        						.append("where b.comp_id.codSatb in (")
        						.append("select a.comp_id.codSatb from UsuarioAtributoUsua a ")
        						.append("where a.comp_id.codUsu = :codUsu))))")
        						.append(" order by i.dataItemSgti asc");
            		
        	
            Query query = this.getSession().createQuery(select.toString());
            query.setLong("codUsu", usuario.getCodUsu().longValue());
            query.setLong("codSgtc", categoria.getCodSgtc().longValue());
            lista = query.list();	
            		
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        
        return lista;
    }
    
    /**
     * Devolve uma lista de ítens de dúvidas frequentes para acesso publico 
     * @param categoria 
     * @return List
     * @throws ECARException
     */
    public List getListSegmentoItensAcessoPublico(SegmentoCategoriaSgtc categoria) throws ECARException {
        List lista = null;
        
        try {
        	StringBuilder select = new StringBuilder("select i from SegmentoItemSgti i where i.indAtivoSgti = 'S' ")
        						.append("and i.segmentoCategoriaSgtc.codSgtc = :codSgtc ")
        						.append(" and i.segmentoSgt.codSgt = 3 ")
        						.append("and ((i.indUtilizTpAcessoSgti = 'N') or ")
        						.append("(i.indUtilizTpAcessoSgti = 'S' and i.codSgti in (")
        						.append("select b.comp_id.codSgti from SegmentoItemTpAcesSgtITA b ")
        						.append("where b.comp_id.codSatb in (select cfg.sisAtributoSatbByCodSaAcesso.codSatb from ConfiguracaoCfg cfg ")
        						.append("))))")
        						.append(" order by i.dataItemSgti asc");
	            		
        	Query query = this.getSession().createQuery(select.toString());
        	query.setLong("codSgtc", categoria.getCodSgtc().longValue());
            lista = query.list();
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        
        return lista;
    }

    /**
     * Devolve uma lista de ítens do glossário visíveis ao usuário
     * @param categoria
     * @param usuario
     * @return List
     * @throws ECARException
     */
        
    public List getListSegmentoItensGlossarioVinculadosAoUsuario(SegmentoCategoriaSgtc categoria, UsuarioUsu usuario) throws ECARException {
        List lista = null;
        
        try {
            
        	StringBuilder select = new StringBuilder("select i from SegmentoItemSgti i where i.indAtivoSgti = 'S' ")
        						.append("and i.segmentoCategoriaSgtc.codSgtc = :codSgtc")
        						.append(" and i.segmentoSgt.codSgt = 4 ");
        	
            if(usuario != null && usuario.getCodUsu() != null) {
            	select.append("and ((i.indUtilizTpAcessoSgti = 'N') or ")
            			.append("(i.indUtilizTpAcessoSgti = 'S' and i.codSgti in (")
						.append("select b.comp_id.codSgti from SegmentoItemTpAcesSgtITA b ")
						.append("where b.comp_id.codSatb in (")
						.append("select a.comp_id.codSatb from UsuarioAtributoUsua a ")
						.append("where a.comp_id.codUsu = :codUsu))))");
            } else {
				select.append("and i.indUtilizTpAcessoSgti = 'N'");
            }
        	select.append(" order by i.tituloSgti asc");
            		
            Query query = this.getSession().createQuery(select.toString());
            query.setLong("codSgtc", categoria.getCodSgtc().longValue());
            if(usuario != null && usuario.getCodUsu() != null) {
            	query.setLong("codUsu", usuario.getCodUsu().longValue());
            }
            lista = query.list();	
            		
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        
        return lista;
    }
    
    
    /**
     * Devolve uma lista de ítens do glossário para acesso público
     * @param categoria 
     * @return List
     * @throws ECARException
     */
        
    public List getListSegmentoItensGlossarioAcessoPublico(SegmentoCategoriaSgtc categoria) throws ECARException {
        List lista = null;
        
        try {
            
        	StringBuilder select = new StringBuilder("select i from SegmentoItemSgti i where i.indAtivoSgti = 'S' ")
        						.append("and i.segmentoCategoriaSgtc.codSgtc = :codSgtc")
        						.append(" and i.segmentoSgt.codSgt = 4 ")
        						.append("and ((i.indUtilizTpAcessoSgti = 'N') or ")
        						.append("(i.indUtilizTpAcessoSgti = 'S' and i.codSgti in (")
        						.append("select b.comp_id.codSgti from SegmentoItemTpAcesSgtITA b ")
        						.append("where b.comp_id.codSatb in (")
        						.append("select cfg.sisAtributoSatbByCodSaAcesso.codSatb from ConfiguracaoCfg cfg ")
        						.append("))))")
        						.append(" order by i.tituloSgti asc");
            		
            Query query = this.getSession().createQuery(select.toString());
            query.setLong("codSgtc", categoria.getCodSgtc().longValue());
            lista = query.list();	
            		
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        
        return lista;
    }    
    
    /**
     * Retorna o numero de Matérias/Artigos mais recentes em SegmentoItem
     * 		diferente da Capa Destaque e que esteja na validade
     * @param application
     * @param usuario
     * @return List
     */
    public long getQtdSegmentoItemMaisArtigos(ServletContext application,UsuarioUsu usuario){
    	long ret = 0;
    	List lista = new ArrayList();
    	try{
    		Mensagem properties = new Mensagem(application);
    		
	    	Date dataAtual = Data.getDataAtual();
	    	SegmentoItemSgti segItemSuper = getSegmentoItemSuperDestaque(application);
	    	String todos = null;
	    	todos = Pagina.getParamStr(request, "todos");
	    	
	    	StringBuilder select = new StringBuilder("select i from SegmentoItemSgti i where i.indAtivoSgti = 'S' ")
	    					.append(" and ( i.dataIniValidadeSgti <= :dataAtual")
	    					.append(" and i.dataFimValidadeSgti >= :dataAtual )")
	    					.append(" and i.segmentoSgt.codSgt = :codSgt ")
	    					.append("and ((i.indUtilizTpAcessoSgti = 'N') or ")
	    					.append("(i.indUtilizTpAcessoSgti = 'S' and i.codSgti in (")
	    					.append("select b.comp_id.codSgti from SegmentoItemTpAcesSgtITA b ")
	    					.append("where b.comp_id.codSatb in (")
	    					.append("select a.comp_id.codSatb from UsuarioAtributoUsua a ")
	    					.append("where a.comp_id.codUsu = :codUsu))))");
	    	
	    	if ((segItemSuper != null)&&(todos == null)){
				//select = select + " and i.codSgti != :codSgti";
				select.append(" and i.indSuperDestaqueSgti = 'S' ");
	    	}
	    		    			
			if (todos == ""){
				select.append(" and ((i.indDestaqueSgti = 'S') or (i.indDestaqueSgti = 'N' and i.indSuperDestaqueSgti = 'S')) ");
			}
				    	
	    	select.append(" order by i.dataItemSgti desc");
        	
			Query query = this.getSession().createQuery(select.toString());
			query.setLong("codSgt", Long.valueOf(properties.getMensagem("admPortal.materias")).longValue());
        	query.setDate("dataAtual", dataAtual);
        	query.setLong("codUsu", usuario.getCodUsu().longValue());
        	//if (segItemSuper != null)
			//	query.setInteger("codSgti", segItemSuper.getCodSgti().intValue());        	   	
                
        	lista = query.list(); 
        	ret = lista.size();
        	return ret; 
    	}catch(HibernateException e){
    		this.logger.error(e);
    		return ret;
    	}
    }
    
    /**
     * Devolve uma lista de ítens de taxacoes 
     * @param usuario 
     * @return List
     * @throws ECARException
     */
    public List getListSegmentoItemTaxacoes(UsuarioUsu usuario) throws ECARException {
        List lista = null;
        
        try {        	
        	StringBuilder select = new StringBuilder("select i from SegmentoItemSgti i where i.indAtivoSgti = 'S' ")
        					.append(" and i.segmentoSgt.codSgt = 2 ")
        					.append(" and ( i.dataIniValidadeSgti <= :dataAtual")
        					.append(" and i.dataFimValidadeSgti >= :dataAtual ) ");
        	
        	String dt = "";
        	try {    		
        		dt = Pagina.getParamStr(request, "data");
        	}catch(NullPointerException ne){
        		this.logger.error(ne);
        	}	
        	
        	if (!"00".equals(dt))	select.append(" and i.dataItemSgti=:dataAtual ");
            		
        	select.append(" and ((i.indUtilizTpAcessoSgti = 'N') or ")
        		  .append(" (i.indUtilizTpAcessoSgti = 'S' and i.codSgti in (")
        		  .append("	select b.comp_id.codSgti from SegmentoItemTpAcesSgtITA b ")
        		  .append("	where b.comp_id.codSatb in (")
        		  .append("		select a.comp_id.codSatb from UsuarioAtributoUsua a ")
        		  .append("		where a.comp_id.codUsu = :codUsu))))")
        		  .append(" order by i.dataItemSgti desc, i.dataInclusaoSgti desc "); 
        	
            Query query = this.getSession().createQuery(select.toString());
            query.setLong("codUsu", usuario.getCodUsu().longValue());
            query.setDate("dataAtual", Data.getDataAtual()); 		
            
            String qtd = null;
        	try {    		
        		qtd = Pagina.getParamStr(request, "qtd");
        	}catch(NullPointerException ne){
        		this.logger.error(ne);
        	}	
            
        	if ((qtd != null)&&(!"".equals(qtd)))	 {
        		if (!"00".equals(qtd))
        			query.setMaxResults(Integer.parseInt(qtd));
        	}
        	else	query.setMaxResults(3);
        	
            lista = query.list();	
            		
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        
        return lista;
    }
    
    /**
     * Devolve a quantidade de ítens de taxacoes 
     * @param usuario 
     * @return List
     * @throws ECARException
     */
    public long getQtdSegmentoItemTaxacoes(UsuarioUsu usuario) throws ECARException {
    	long ret = 0;
        
        try {        	
        	StringBuilder select = new StringBuilder("select i from SegmentoItemSgti i where i.indAtivoSgti = 'S' ")
        						.append(" and i.segmentoSgt.codSgt = 2 ")
        						.append(" and ( i.dataIniValidadeSgti <= :dataAtual")
        						.append(" and i.dataFimValidadeSgti >= :dataAtual )")
        						.append(" and i.dataItemSgti=:dataAtual")
        						.append(" and ((i.indUtilizTpAcessoSgti = 'N') or ")
        						.append(" (i.indUtilizTpAcessoSgti = 'S' and i.codSgti in (")
        						.append("	select b.comp_id.codSgti from SegmentoItemTpAcesSgtITA b ")
        						.append("	where b.comp_id.codSatb in (")
        						.append("		select a.comp_id.codSatb from UsuarioAtributoUsua a ")
        						.append("		where a.comp_id.codUsu = :codUsu))))")
        						.append(" order by i.dataInclusaoSgti desc"); 
        	
            Query query = this.getSession().createQuery(select.toString());
            query.setDate("dataAtual", Data.getDataAtual()); 		
            query.setLong("codUsu", usuario.getCodUsu().longValue());
            ret = (query.list()).size();	
            		
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        
        return ret;
    }
    
    /**
     * Devolve uma lista de ítens de taxacoes de acordo com os parâmetros
     * @param usuario 
     * @return List
     * @throws ECARException
     */
    public List pesquisaTaxacoes(UsuarioUsu usuario) throws ECARException {
        List lista = null;
        List ret = new ArrayList();
        
        String palavra = Pagina.getParamStr(request, "palavra");
        String editoria = Pagina.getParamStr(request, "editorias");        
        String fonte = null;
        String dtInicial = null;
        String dtFinal = null;
        
        try {    		
        	fonte = Pagina.getParamStr(request, "fonte");        	
    	}catch(NullPointerException ne){
    		this.logger.error(ne);
    	}	
    	try {    		
    		dtInicial = Pagina.getParamStr(request, "dataInicial");
    	}catch(NullPointerException ne){
    		this.logger.error(ne);
    	}	
    	try {    		
    		dtFinal = Pagina.getParamStr(request, "dataFinal");
    	}catch(NullPointerException ne){
    		this.logger.error(ne);
    	}	
    	
    	
        try {         	
        	
        	String[] palavras = palavra.split("\\s");
        	String[] fontes = fonte.split("\\s");
        	
        	StringBuilder select = new StringBuilder("select distinct i from SegmentoItemSgti i where i.indAtivoSgti = 'S' ")
        					.append(" and i.segmentoSgt.codSgt = 2 ")
        					.append(" and ( i.dataIniValidadeSgti <= :dataAtual")
        					.append(" and i.dataFimValidadeSgti >= :dataAtual )" );
        	
        	select.append(" and ( ");
        	for (int i=0; i < palavras.length; i++) {        	
        		select.append(" ( upper( i.tituloSgti ) like :palavra").append(i)
        			  .append(" or upper( i.linhaApoioSgti ) like :palavra").append(i)
        			  .append(" or upper( i.legendaImagCapaSgti ) like :palavra").append(i).append(" )");
        		if ((i+1) < palavras.length)	select.append(" or ");
        	}
        	select.append(" ) ");
					
        	if (fontes.length > 0)	{
        		select.append(" and ( ");
            	for (int i=0; i < fontes.length; i++) {        	
            		select.append(" ( upper( i.segmentoItemFonteSgtif.descricaoSgtif ) like :fonte").append(i).append(" )");
            		if ((i+1) < fontes.length)	select.append(" or ");
            	}
            	select.append(" ) ");
        	}
        	if ((dtInicial != null)&&(!"".equals(dtInicial)))	select.append(" and (i.dataItemSgti >= :dtInicial) ");
        	if ((dtFinal != null)&&(!"".equals(dtFinal)))		select.append(" and (i.dataItemSgti <= :dtFinal) ");
        	
        	select.append(" and ((i.indUtilizTpAcessoSgti = 'N') or ")
        		  .append(" (i.indUtilizTpAcessoSgti = 'S' and i.codSgti in (")
        		  .append("	select b.comp_id.codSgti from SegmentoItemTpAcesSgtITA b ")
        		  .append("	   where b.comp_id.codSatb in (")
        		  .append("		select a.comp_id.codSatb from UsuarioAtributoUsua a ")
        		  .append("		where a.comp_id.codUsu = ")
        		  .append(usuario.getCodUsu()).append("))))")
        		  .append(" order by i.dataInclusaoSgti desc"); 
        	        	
            Query query = this.getSession().createQuery(select.toString());
            
	    	for (int i=0; i < palavras.length; i++) { 
	    		query.setString("palavra"+i, "%" + palavras[i].toUpperCase() + "%");
	    	}	    	
	    	for (int i=0; i < fontes.length; i++) { 
	    		query.setString("fonte"+i, "%" + fontes[i].toUpperCase() + "%");
	    	}
            if ((dtInicial != null)&&(!"".equals(dtInicial)))	query.setDate("dtInicial", Data.parseDate(dtInicial) ) ;
        	if ((dtFinal != null)&&(!"".equals(dtFinal)))		query.setDate("dtFinal", Data.parseDate(dtFinal) );
            
            query.setDate("dataAtual", Data.getDataAtual());
            lista = query.list();	
            
            if (lista.size() > 0)	{
            	Iterator it = lista.iterator(); 	           
 	           	while(it.hasNext()){
 	        	   SegmentoItemSgti sgti = (SegmentoItemSgti) it.next();
 	                
 	               Set atb = sgti.getSegmentoSisAtribSgtsas();
 	                
 	               if ((atb != null)&& !(atb.isEmpty())) {
 	            	   Iterator itAtb = atb.iterator(); 	           
 	            	   while(itAtb.hasNext()){
 	            		   SisAtributoSatb atributo = (SisAtributoSatb) itAtb.next();
 	                		
 	            		   String[] codSatb = editoria.split(",");
 	                	   for (int i=0; i < codSatb.length; i++)	{
 	                		   if (atributo.getCodSatb().equals(Long.valueOf(codSatb[i]))) {
 	                	    		ret.add(sgti);
 	                		   }
 	                	   }
 	            	   	}
 	               	}
 	           	}           
 	    	}            		
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        
        return ret;
    }
    
    /**
     * Seleciona o SegmentoItem pelo codSgti
     * @param codSgti
     * @return List
     */
    public SegmentoItemSgti getSegmentoItemCodSgti(long codSgti){
    	try{

	    	StringBuilder select = new StringBuilder("select segItem from SegmentoItemSgti segItem")
	    				        	.append(" where segItem.codSgti = :codSgti");
        	Query query = this.getSession().createQuery(select.toString());
        	query.setMaxResults(1); 
        	query.setLong("codSgti", codSgti); 
        	
        	List lista = query.list();
        	Iterator it = lista.iterator();
        	
        	if(it.hasNext()){
        		SegmentoItemSgti segItem = (SegmentoItemSgti) it.next();
        		return segItem;
        	}	    	
	    	return null;
    	}catch(HibernateException e){
            this.logger.error(e);
    		return null;
    	}
    }
    
    /**
     * Seleciona 3 outras imagens da mídia mais recentes em SegmentoItem
     * 		diferente da Capa Destaque e que esteja na validade 
     * @return List
     */
    public List getLeiautesNovos(){
    	List lista = null;
    	try{
    		
	    	StringBuilder select = new StringBuilder("select segLeiaute from SegmentoItemLeiauteSgtil segLeiaute")
	    				.append(" where segLeiaute.codSgtil >= 11")
	    				.append(" order by segLeiaute.descricaoSgtil asc");
        	
			Query query = this.getSession().createQuery(select.toString());
			        	
        	lista = query.list(); 
	        
        	return lista; 
    	}catch(HibernateException e){
    		this.logger.error(e);
    		return lista;
    	}
    }  
    
    /**
     * Método que cria a lista de editorias através dos codSatb
     * @param ids
     * @return lista
     */
    public List getEditoriasByIds(String ids){
    	List lista = null;
    	try{
			Query query = this.getSession().createQuery("select editoria from SisAtributoSatb editoria "+
					"where editoria.codSatb IN ("+ids+")");
			        	
        	lista = query.list(); 
	        
        	return lista; 
    	}catch(HibernateException e){
    		this.logger.error(e);
    		return lista;
    	}
    }
    
    /**
     * Devolve uma lista de SegmentoItem de acordo com os parâmetros passados por request (palavra, dataInicial, dataFinal, segmentos) 
     * e o UsuarioUsu por parâmetro do método
     * @param usuario 
     * @return List
     * @throws ECARException
     */
    public List pesquisar(UsuarioUsu usuario) throws ECARException {
        List lista = new ArrayList();
                
        String[] palavras = Pagina.getParamStr(request, "palavra").split("\\s");
        String dtInicial = null;
        String dtFinal = null;
             
    	try {    		
    		dtInicial = Pagina.getParamStr(request, "dataInicial");
    	}catch(NullPointerException ne){
    		this.logger.error(ne);
    	}	
    	try {    		
    		dtFinal = Pagina.getParamStr(request, "dataFinal");
    	}catch(NullPointerException ne){
    		this.logger.error(ne);
    	}	

        try {
        	StringBuilder select = new StringBuilder("select distinct i from SegmentoItemSgti i where i.indAtivoSgti = 'S' ")
        				.append(" and ( i.dataIniValidadeSgti <= :dataAtual")
        				.append(" and i.dataFimValidadeSgti >= :dataAtual )")
        				.append(" and i.segmentoSgt.codSgt IN (").append(Pagina.getParamStr(request, "codSgtis")).append(")");
        	
        	select.append(" and ( ");
        	for (int i=0; i < palavras.length; i++) {        	
        		select.append(" ( upper( i.tituloSgti ) like :palavra").append(i)
        			  .append(" or upper( i.linhaApoioSgti ) like :palavra").append(i)
        			  .append(" or upper( i.integraSgti ) like :palavra").append(i)
        			  .append(" or upper( i.palavrasChavesSgti ) like :palavra").append(i)
        			  .append(" or upper( i.anexoLegendaSgti ) like :palavra").append(i)
        			  .append(" or upper( i.legendaImagCapaSgti ) like :palavra").append(i)
        			  .append(" or upper( i.legendaImag1Sgti ) like :palavra").append(i)
        			  .append(" or upper( i.legendaImag2Sgti ) like :palavra").append(i)
        			  .append(" or upper( i.legendaImag3Sgti ) like :palavra").append(i)
        			  .append(" ) or ");
        		
        		select.append(" ( upper( i.segmentoCategoriaSgtc.tituloSgtc ) like :palavra").append(i).append(" ")
        			  .append(" or upper( i.segmentoCategoriaSgtc.descricaoSgtc ) like :palavra").append(i).append(" )");
        					
        		if ((i+1) < palavras.length)	select.append(" and ");
        	}
        	select.append(" ) ");
			
        	if ((dtInicial != null)&&(!"".equals(dtInicial)))	select.append(" and (i.dataItemSgti >= :dtInicial) " );
        	if ((dtFinal != null)&&(!"".equals(dtFinal)))		select.append(" and (i.dataItemSgti <= :dtFinal) ");
        	
        	select.append(" and ((i.indUtilizTpAcessoSgti = 'N') or ")
        		  .append(" (i.indUtilizTpAcessoSgti = 'S' and i.codSgti in (")
        		  .append("	select b.comp_id.codSgti from SegmentoItemTpAcesSgtITA b ")
        		  .append(" where b.comp_id.codSatb in (")
        		  .append("		select a.comp_id.codSatb from UsuarioAtributoUsua a ")
        		  .append("		where a.comp_id.codUsu = :codUsu))))")
        		  .append(" order by i.dataInclusaoSgti desc"); 
        	
            Query query = this.getSession().createQuery(select.toString());
            query.setLong("codUsu", usuario.getCodUsu().longValue());
            
	    	for (int i=0; i < palavras.length; i++) { 
	    		query.setString("palavra"+i, "%" + palavras[i].toUpperCase() + "%");
	    	}	
            if ((dtInicial != null)&&(!"".equals(dtInicial)))	query.setDate("dtInicial", Data.parseDate(dtInicial) ) ;
        	if ((dtFinal != null)&&(!"".equals(dtFinal)))		query.setDate("dtFinal", Data.parseDate(dtFinal) );
            
            query.setDate("dataAtual", Data.getDataAtual());
            lista = query.list();	 
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        
        return lista;
    }
    
    /**
     * Seleciona 3 (ou mais, se passado parametro "qtd") outras Matérias/Artigos mais recentes em SegmentoItem
     * 		diferente da Capa Destaque e que esteja na validade 
     * @param application
     * @param usuario
     * @return List
     */
    public List getSegmentoItemTodosArtigos(ServletContext application,UsuarioUsu usuario){
    	List lista = new ArrayList();
    	try{
    		Mensagem properties = new Mensagem(application);
    		
	    	Date dataAtual = Data.getDataAtual();
	    	
	    	String todos = null;
	    	try {	todos = Pagina.getParamStr(request, "todos");	}	catch(NullPointerException ne){this.logger.error(ne);}
	    	
	    	StringBuilder select = new StringBuilder("select i from SegmentoItemSgti i where i.indAtivoSgti = 'S' ")
	    				 .append(" and i.dataFimValidadeSgti >= :dataAtual ")
	    				 .append(" and i.segmentoSgt.codSgt = :codSgt ")
	    				 .append("and ((i.indUtilizTpAcessoSgti = 'N') or ")
	    				 .append("(i.indUtilizTpAcessoSgti = 'S' and i.codSgti in (")
	    				 .append("select b.comp_id.codSgti from SegmentoItemTpAcesSgtITA b ")
	    				 .append("where b.comp_id.codSatb in (").append("select a.comp_id.codSatb from UsuarioAtributoUsua a ")
	    				 .append("where a.comp_id.codUsu = :codUsu))))");
	    	
						
			StringBuilder order = new StringBuilder(" order by i.dataItemSgti desc");
        				
	    	Query query = this.getSession().createQuery(select.toString() + order);
			query.setLong("codSgt", Long.valueOf(properties.getMensagem("admPortal.materias")).longValue());
        	query.setDate("dataAtual", dataAtual);
        	query.setLong("codUsu", usuario.getCodUsu().longValue());
        	        	
        	String qtd = null, qtdPag = null, numPag = null;
        	
        	try {
        		qtd = Pagina.getParamStr(request, "qtd");
        		qtdPag = Pagina.getParamStr(request, "qtdPag");
        		numPag = Pagina.getParamStr(request, "numPag");
        	}catch(NullPointerException ne){
        		this.logger.error(ne);
        	}
        	
        	if ((qtd != null)&&(!"".equals(qtd)))	 {
        		if (!"00".equals(qtd))
        			query.setMaxResults(Integer.parseInt(qtd));
        	}
        	//else	query.setMaxResults(3);
        	/*
        	if (((qtdPag != null)&&(!"".equals(qtdPag))) && (numPag != null)&&(!"".equals(numPag))) {
				query.setMaxResults(Integer.parseInt(qtdPag));
				query.setFirstResult( ((Integer.parseInt(numPag)-1) * Integer.parseInt(qtdPag)));
			}
        	*/
        	lista = query.list(); 
        	
        	return lista; 
    	}catch(HibernateException e){
    		this.logger.error(e);
    		return lista;
    	}
    }
    
    /**
     * Método que cria a lista de editorias através dos codSatb
     * @param ids
     * @return lista
     */
    public List getEditorias(String ids){
    	List lista = null;
    	try{
			Query query = this.getSession().createQuery("select editoria from SisAtributoSatb editoria");
			        	
        	lista = query.list(); 
	        
        	return lista; 
    	}catch(HibernateException e){
    		this.logger.error(e);
    		return lista;
    	}
    }
    
    /**
     * Seleciona 3 (ou mais, se passado parametro "qtd") outras Matérias/Artigos mais recentes em SegmentoItem
     * 		diferente da Capa Destaque e que esteja na validade 
     * @param application
     * @param usuario
     * @param codSatb
     * @return List
     */
    public List getSegmentoItemArtigosPorEditoria(ServletContext application,UsuarioUsu usuario,String codSatb){
    	List lista = new ArrayList();
    	try{
    		Mensagem properties = new Mensagem(application);
    		
	    	Date dataAtual = Data.getDataAtual();
	    	SegmentoItemSgti segItemSuper = getSegmentoItemSuperDestaque(application);
	    		    	
	    	List editorias = new ArrayList();
	    	List atributos = new ArrayList();
	    	String aspas = "";
	    	
	    	if (segItemSuper.getSegmentoSisAtribSgtsas() != null && segItemSuper.getSegmentoSisAtribSgtsas().size() > 0)
	    		atributos.addAll(segItemSuper.getSegmentoSisAtribSgtsas());
	    	
	    	Iterator it = atributos.iterator();
	    	
	    	while(it.hasNext()){
	    		SisAtributoSatb atb = (SisAtributoSatb) it.next();
	    		aspas = "'" + atb.getCodSatb() + "'";
	    		editorias.add(aspas);
	    	}
	    	
	    	String todos = null;
	    	String teste = null;
	    	try {	
	    		todos = Pagina.getParamStr(request, "todos");	
	    		teste = Pagina.getParamStr(request, "codSatb");	    			
	    	}	
	    	catch(NullPointerException ne){
	    		this.logger.error(ne);
	    	}
	    	
	    	StringBuilder select = new StringBuilder("select i from SegmentoItemSgti i where i.indAtivoSgti = 'S' ")
	    					.append(" and ( i.dataIniValidadeSgti <= :dataAtual")
	    					.append(" and i.dataFimValidadeSgti >= :dataAtual )")
	    					.append(" and i.segmentoSgt.codSgt = :codSgt ")
	    					.append(" and i.codSgti in (").append("select s.comp_id.codSgti from SegmentoSisAtribSgtsa s ")
	    					.append("where s.comp_id.codSatb = :codSatb)")
	    					.append(" and ((i.indUtilizTpAcessoSgti = 'N') or ")
	    					.append("(i.indUtilizTpAcessoSgti = 'S' and i.codSgti in (")
	    					.append("select b.comp_id.codSgti from SegmentoItemTpAcesSgtITA b ")
	    					.append("where b.comp_id.codSatb in (").append("select a.comp_id.codSatb from UsuarioAtributoUsua a ")
	    					.append("where a.comp_id.codUsu = :codUsu))))");    		
	    	
			//if ((segItemSuper != null)&&(todos == null)){
			//		select = select + " and i.codSgti != :codSgti";
			//		select = select + " and i.indSuperDestaqueSgti = 'S' ";
			//}
	    				
			//if (todos == ""){
			//	select = select + " and ((i.indDestaqueSgti = 'S') or (i.indDestaqueSgti = 'N' and i.indSuperDestaqueSgti = 'S')) ";
			//}
			
			StringBuilder order = new StringBuilder(" order by i.dataItemSgti desc");
        				
	    	Query query = this.getSession().createQuery(select.toString() + order);
			query.setLong("codSgt", Long.valueOf(properties.getMensagem("admPortal.materias")).longValue());
        	query.setDate("dataAtual", dataAtual);
        	query.setLong("codUsu", usuario.getCodUsu().longValue());
        	query.setLong("codSatb",Long.parseLong(codSatb));
        	
        	
        	//if (segItemSuper != null)
			//	query.setInteger("codSgti", segItemSuper.getCodSgti().intValue());        	   	
        	
        	String qtd = null, qtdPag = null, numPag = null;
        	
        	try {
        		qtd = Pagina.getParamStr(request, "qtd");
        		qtdPag = Pagina.getParamStr(request, "qtdPag");
        		numPag = Pagina.getParamStr(request, "numPag");
        	}catch(NullPointerException ne){
        		this.logger.error(ne);
        	}
        	
        	if ((qtd != null)&&(!"".equals(qtd)))	 {
        		if (!"00".equals(qtd))
        			query.setMaxResults(Integer.parseInt(qtd));
        	}
        	
        	lista = query.list(); 
        	
        	return lista; 
    	}catch(HibernateException e){
    		this.logger.error(e);
    		return lista;
    	}
    }
    
}	
