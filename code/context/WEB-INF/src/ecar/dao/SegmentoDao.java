/*
 * Criado em 29/04/2005
 *
 */
package ecar.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

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
import ecar.pojo.SegmentoAreaSgta;
import ecar.pojo.SegmentoLeiauteSgtl;
import ecar.pojo.SegmentoSgt;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;

/**
 * @author evandro
 * @author felipe
 *
 */
public class SegmentoDao extends Dao{
    /**
     * Construtor. Chama o Session factory do Hibernate
     * @param request
     */
    public SegmentoDao(HttpServletRequest request) {
		super();
		this.request = request;
    }
    
    /**
     * Salva um registro de segmento a partir de par�metros recebidos por request por um formul�rio de Upload.
     * Salva a cole��o de tipos de acesso do segmento.
     * Salva a imagem selecionada em disco.
     * @param segmento
     * @param campos
     * @param pathRaiz
     * @param pathRelativo
     * @throws ECARException
     */
    public void salvar(SegmentoSgt segmento, List campos, String pathRaiz, String pathRelativo) throws ECARException{        
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        setSegmentoUpload(segmento, campos);
	        criarCollectionTipoAcesso(segmento, campos,null,true);
	        session.save(segmento);
			objetos.add(segmento);
	        
			/* grava a imagem e atualiza o segmento para vincular o nome da imagem */
	        uploadImagem(segmento, campos, pathRaiz , pathRelativo);
	        session.update(segmento);
			objetos.add(segmento);
	
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
     * Exclui um registro de segmento. Exclui a imagem vinculada caso exista.
     * @param segmento
     * @param pathRaiz
     * @throws ECARException
     */
    public void excluir(SegmentoSgt segmento, String pathRaiz) throws ECARException{                   
        boolean excluir = true;
        if(contar(segmento.getSegmentoCategoriaSgtcs()) > 0){ 
            excluir = false;
            throw new ECARException("segmento.exclusao.erro.segmentoCategoriaSgtcs");
        }                     
        if(contar(segmento.getSegmentoItemSgtis()) > 0){ 
            excluir = false;
            throw new ECARException("segmento.exclusao.erro.segmentoItemSgtis");
        }                     
        
        if(excluir){
            if(segmento.getImagemSgt()!=null){
                try{
                    if (FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segmento.getImagemSgt(), ""))){
                        super.excluir(segmento);                                
                    } else {
                        throw new ECARException("erro.excluirArquivo");        
                    }                        
                } catch(Exception e){
                	this.logger.error(e);
                    throw new ECARException("erro.exception");
                }
            } else 
                super.excluir(segmento);                
        }
    }
    
    
    /**
     * Altera um registro de segmento a partir de par�metros recebidos por request por um formul�rio de Upload.
     * Altera a cole��o de tipos de acesso do segmento.
     * Salva a imagem selecionada em disco e apaga a uma imagem existente anteriormente.
     * @param segmento
     * @param campos
     * @param pathRaiz
     * @param pathRelativo
     * @throws ECARException
     */
    public void alterar(SegmentoSgt segmento, List campos, String pathRaiz, String pathRelativo) throws ECARException{        
        
    	String flagImagem = "";
    	
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();
    		
    		if (("".equals(FileUpload.verificaValorCampo(campos, "imagemSgt"))) && 
    				(!"".equals(FileUpload.verificaValorCampo(campos, "imagem")))){
    				if(segmento.getImagemSgt()!=null){
    				
    					if (!FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segmento.getImagemSgt(), ""))){
    						throw new ECARException("erro.excluirArquivo");
    						
    					}   					
    				}    		
    				
    				flagImagem = "excluidaImagem";
    				
    		}
	    		
	    	    	
	    	setSegmentoUpload(segmento, campos);
	    	
	    	if ("excluidaImagem".equalsIgnoreCase(flagImagem)){
	    		segmento.setImagemSgt("");
				session.save(segmento);
				objetos.add(segmento);
	    	}
	    	
	   		if ("".equals(FileUpload.verificaValorCampo(campos, "legendaImagemSgt"))){
	   			segmento.setImagemSgt("");
	   			session.save(segmento);
				objetos.add(segmento);
	   		}
	    	
	        criarCollectionTipoAcesso(segmento, campos,null,true);
	        uploadImagem(segmento, campos, pathRaiz , pathRelativo);
	        session.update(segmento);
			objetos.add(segmento);
	    	
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
     * Realiza o upload da imagem do Segmento. 
     * Seta no objeto segmento refer�ncia para tal imagem.
     * Apaga uma imagem existente anteriormente em caso de altera��o.
     * @param segmento
     * @param campos
     * @param pathRaiz
     * @param pathRelativo
     * @throws ECARException
     */
    public void uploadImagem(SegmentoSgt segmento, List campos, String pathRaiz, String pathRelativo) throws ECARException{
        try{
            Iterator it = campos.iterator();  
            while(it.hasNext()){
                FileItem fileItem = (FileItem) it.next();             
                if(!fileItem.isFormField() && !"".equals(fileItem.getName())){    
                    
                    if(segmento.getImagemSgt() != null)
                        FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segmento.getImagemSgt(), ""));                        
                    
                    File arquivoGravado = FileUpload.salvarNoDisco(fileItem, 
                                                                   FileUpload.getPathFisico(pathRaiz, 
                                                                                            pathRelativo,  
                                                                                            segmento.getCodSgt() + FileUpload.getNomeArquivo(fileItem)));
                    segmento.setImagemSgt(FileUpload.getPathFisico("", 
                                                                  pathRelativo,  
                                                                  segmento.getCodSgt() + FileUpload.getNomeArquivo(fileItem)));                
                }
            }            
        } catch (Exception e){
        	this.logger.error(e);
            throw new ECARException(e);
        }

    }
    
    /**
     * A partir de dados passados por request popula um objeto SegmentoSgt
     * @param segmento
     * @param campos
     * @param recuperarParametrosComoString indica se ir� recuperar dados nulos como String vazia
     * @throws ECARException
     */
    public void setSegmento(SegmentoSgt segmento, 
                        HttpServletRequest campos,                         
                        boolean recuperarParametrosComoString
                        ) throws ECARException{
        
        try{                     
            if(!"".equals(Pagina.getParamStr(campos, "segmentoArea"))){
                segmento.setSegmentoAreaSgta((SegmentoAreaSgta) this.buscar(
                            SegmentoAreaSgta.class,
                            Long.valueOf(Pagina.getParamStr(campos, "segmentoArea"))));
            }
            if(!"".equals(Pagina.getParamStr(campos, "segmentoLeiauteSgtl"))){
                segmento.setSegmentoLeiauteSgtl((SegmentoLeiauteSgtl) this.buscar(
                            SegmentoLeiauteSgtl.class,
                            Long.valueOf(Pagina.getParamStr(campos, "segmentoLeiauteSgtl"))));
            }
            if(!"".equals(Pagina.getParamStr(campos, "sisGrupoAtributoEditorias"))){
                segmento.setSisGrupoAtributoSga((SisGrupoAtributoSga) this.buscar(
                            SisGrupoAtributoSga.class,
                            Long.valueOf(Pagina.getParamStr(campos, "sisGrupoAtributoEditorias"))));
            }
            if(recuperarParametrosComoString){
                segmento.setTituloSgt(Pagina.getParamStr(campos, "tituloSgt"));
                segmento.setDescricaoSgt(Pagina.getParamStr(campos, "descricaoSgt"));
                segmento.setLegendaImagemSgt(Pagina.getParamStr(campos, "legendaImagemSgt"));
                segmento.setLinkPesquisaSgt(Pagina.getParamStr(campos, "linkPesquisaSgt"));
                segmento.setIndMenuSgt(Pagina.getParamStr(campos, "indMenuSgt"));
                segmento.setIndAtivoSgt(Pagina.getParamStr(campos, "indAtivoSgt"));
                segmento.setIndUtilizTpAcessoSgt(Pagina.getParamStr(campos, "indUtilizTpAcessoSgt"));
            } else {
                segmento.setTituloSgt(Pagina.getParam(campos, "tituloSgt"));
                segmento.setDescricaoSgt(Pagina.getParam(campos, "descricaoSgt"));
                segmento.setLegendaImagemSgt(Pagina.getParam(campos, "legendaImagemSgt"));
                segmento.setLinkPesquisaSgt(Pagina.getParam(campos, "linkPesquisaSgt"));
                segmento.setIndMenuSgt(Pagina.getParam(campos, "indMenuSgt"));
                segmento.setIndAtivoSgt(Pagina.getParam(campos, "indAtivoSgt"));
                segmento.setIndUtilizTpAcessoSgt(Pagina.getParam(campos, "indUtilizTpAcessoSgt"));                
            }
            criarCollectionTipoAcesso(segmento, null, campos,false);
        } catch(Exception e){
            this.logger.error(e);
            throw new ECARException(e);
        }
        
    }

    /**
     * A partir de um List de campos obtidos na requsi��o de um formul�rio de upload 
     * popula um objeto SegmentoSgt
     * @param segmento
     * @param campos
     * @throws ECARException
     */
    public void setSegmentoUpload(SegmentoSgt segmento, List campos) throws ECARException{
        
        try{                     
            if(!"".equals(FileUpload.verificaValorCampo(campos, "segmentoArea"))){
                segmento.setSegmentoAreaSgta((SegmentoAreaSgta) this.buscar(
                        SegmentoAreaSgta.class,
                        Long.valueOf(FileUpload.verificaValorCampo(campos, "segmentoArea"))));
            }
            if(!"".equals(FileUpload.verificaValorCampo(campos, "segmentoLeiauteSgtl"))){
                segmento.setSegmentoLeiauteSgtl((SegmentoLeiauteSgtl) this.buscar(
                        SegmentoLeiauteSgtl.class,
                        Long.valueOf(FileUpload.verificaValorCampo(campos, "segmentoLeiauteSgtl"))));
            }
            if(!"".equals(FileUpload.verificaValorCampo(campos, "sisGrupoAtributoEditorias"))){
                segmento.setSisGrupoAtributoSga((SisGrupoAtributoSga) this.buscar(
                        SisGrupoAtributoSga.class,
                        Long.valueOf(FileUpload.verificaValorCampo(campos, "sisGrupoAtributoEditorias"))));
            }else{
            	segmento.setSisGrupoAtributoSga(null);
            }
            segmento.setTituloSgt(FileUpload.verificaValorCampo(campos, "tituloSgt"));
            segmento.setDescricaoSgt(FileUpload.verificaValorCampo(campos, "descricaoSgt"));
            segmento.setLegendaImagemSgt(FileUpload.verificaValorCampo(campos, "legendaImagemSgt"));
            segmento.setLinkPesquisaSgt(FileUpload.verificaValorCampo(campos, "linkPesquisaSgt"));
            segmento.setIndMenuSgt(FileUpload.verificaValorCampo(campos, "indMenuSgt"));
            segmento.setIndAtivoSgt(FileUpload.verificaValorCampo(campos, "indAtivoSgt"));
            segmento.setIndUtilizTpAcessoSgt(FileUpload.verificaValorCampo(campos, "indUtilizTpAcessoSgt"));

        } catch(Exception e){
            this.logger.error(e);
            throw new ECARException(e);
        }
        
    }
    
    
    /**
     * Cria a cole��o de atributos de Tipo de Acesso de Um Segmento
     * @param segmento
     * @param request
     */
    private void criarCollectionTipoAcesso(SegmentoSgt segmento, List campos, HttpServletRequest request, boolean usarFileUpload) throws ECARException{
        try{
            Object[] codigosTpAcesso;
            if(usarFileUpload)
            	codigosTpAcesso = FileUpload.verificaValorCampoArray(campos, "sisAtributoSatb");
            else
            	codigosTpAcesso = request.getParameterValues("sisAtributoSatb");
            
            if(codigosTpAcesso != null){
                segmento.setSegmentoTpAcessoSgttas(new HashSet());
                for(int i = 0; i<codigosTpAcesso.length;i++){
                    SisAtributoSatb atributo = (SisAtributoSatb) super.buscar(SisAtributoSatb.class, Long.valueOf(codigosTpAcesso[i].toString()));
                    if(segmento.getSegmentoTpAcessoSgttas() != null && !segmento.getSegmentoTpAcessoSgttas().contains(atributo))
                    	segmento.getSegmentoTpAcessoSgttas().add(atributo);                                                   
                }	
            }
                        
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException(e);
        }
    }
    

    /**
     * Retorna uma List com todos as identifica��es dos Atributos que representam os Tipos de Acesso de um 
     * Segmento
     * @param segmento
     * @return
     */
    public List getTipoAcessoSegmentoById(SegmentoSgt segmento){
        List retorno = new ArrayList();
        if(segmento.getSegmentoTpAcessoSgttas() != null && segmento.getSegmentoTpAcessoSgttas().size() > 0){
            Iterator it = segmento.getSegmentoTpAcessoSgttas().iterator();
            while(it.hasNext()){
                SisAtributoSatb segmentoTpAcesso = (SisAtributoSatb) it.next();
                retorno.add(segmentoTpAcesso.getCodSatb());
            }
        }
        return retorno;
    }
    
    /**
     * Invoca o m�todo pesquisar do Dao, retirando do resultado os Segmentos n�o fixos.
     *  e itera sobre o resultado para inicializar as Collections de Tipo de Acesso de Cada
     * objeto retornado.
     * Devolve um lista de segmentos livres de acordo com os par�metros de o
     * @param segmento
     * @param application
     * @return
     * @throws ECARException
     */
    public List pesquisar(SegmentoSgt segmento, ServletContext application) throws ECARException{
       
       List pesquisa = super.pesquisar(segmento, new String[] {"descricaoSgt","asc"});
	   
	   pesquisa.retainAll(this.getSegmentosLivres(application));
       
       if(segmento.getSegmentoTpAcessoSgttas() != null){
       	/*
       	 * Percorre o resultado e retira dele todas os segmentos onde
       	 * dentro da cole��o de SegmentoTpAcessoSgttas n�o existam todos os
       	 * atributos de TpAcessoSgttas passados no objeto de pesquisa
       	 */
       	List atributosPesquisa = new ArrayList();
       	atributosPesquisa.addAll(segmento.getSegmentoTpAcessoSgttas());
       	
       	Iterator it = pesquisa.iterator();
       	while (it.hasNext()) {
       		List atributosResultado = new ArrayList();
       		Iterator itAtribResultado = ((SegmentoSgt) it.next()).getSegmentoTpAcessoSgttas().iterator();
       		
       		while (itAtribResultado.hasNext())
       			atributosResultado.add((SisAtributoSatb) itAtribResultado.next());
       		
       		if (!atributosResultado.containsAll(atributosPesquisa))
       			it.remove();
       	}
       }
             
       if(pesquisa.size() > 0){
           Iterator it = pesquisa.iterator();
           while(it.hasNext()){
                SegmentoSgt sgt = (SegmentoSgt) it.next();
                sgt.getSegmentoTpAcessoSgttas().size();
           }           
       }
       return pesquisa;
    }
	
    /**
     * Pesquisa
     * @param segmento
     * @return List (pesquisa)
     * @throws ECARException
     */
    public List pesquisar(SegmentoSgt segmento) throws ECARException{
	       
	       List pesquisa = super.pesquisar(segmento, new String[] {"descricaoSgt","asc"});
		   	       
	       if(segmento.getSegmentoTpAcessoSgttas() != null){
	       	/*
	       	 * Percorre o resultado e retira dele todas os segmentos onde
	       	 * dentro da cole��o de SegmentoTpAcessoSgttas n�o existam todos os
	       	 * atributos de TpAcessoSgttas passados no objeto de pesquisa
	       	 */
	       	List atributosPesquisa = new ArrayList();
	       	atributosPesquisa.addAll(segmento.getSegmentoTpAcessoSgttas());
	       	
	       	Iterator it = pesquisa.iterator();
	       	while (it.hasNext()) {
	       		List atributosResultado = new ArrayList();
	       		Iterator itAtribResultado = ((SegmentoSgt) it.next()).getSegmentoTpAcessoSgttas().iterator();
	       		
	       		while (itAtribResultado.hasNext())
	       			atributosResultado.add((SisAtributoSatb) itAtribResultado.next());
	       		
	       		if (!atributosResultado.containsAll(atributosPesquisa))
	       			it.remove();
	       	}
	       }
	             
	       if(pesquisa.size() > 0){
	           Iterator it = pesquisa.iterator();
	           while(it.hasNext()){
	                SegmentoSgt sgt = (SegmentoSgt) it.next();
	                sgt.getSegmentoTpAcessoSgttas().size();
	           }           
	       }
	       return pesquisa;
	    }
    
    /**
     * Retorna uma lista com os segmentos livres 
     * 	(diferente de itens fixos Perguntas Frequentes, Glossario, Artigo e imagens da m�dia)
     * Utilizando arquivo de properties (admPortal.itensFixos)
     * @param application
     * @return
     * @throws ECARException
     */
    public List getSegmentosLivres(ServletContext application) throws ECARException{
        try{            
        	Mensagem properties = new Mensagem(application);
        	String select = "select segmento from SegmentoSgt segmento where not segmento.codSgt in (:msg)";
            Query query = this.getSession().createQuery(select);   
            String[] msg = properties.getMensagem("admPortal.itensFixos").split(",");
            List itens = new ArrayList();
            for(int i = 0; i < msg.length; i++){
            	if(!"".equals(msg[i])){
            		itens.add(Long.valueOf(msg[i]));
            	}
            }
            query.setParameterList("msg", itens);
            
            return  query.list();            
        } catch(HibernateException e){
            this.logger.error(e);
            throw new ECARException(e);              
        }
    }
    
    /**
     * Retorna uma lista com os segmentos livres que possuam um grupo de atributos para editorias cadastrado
     * ( c�digo maior que 4 - diferente de Perguntas Frequentes, Glossario, Artigo e
     * imagens da m�dia)
     * @param application
     * @return
     * @throws ECARException
     */
    public List getSegmentosLivresComGrupoDeAtributoParaEditorias(ServletContext application) throws ECARException{
        try{
        	Mensagem properties = new Mensagem(application);
        	StringBuilder select = new StringBuilder("select segmento from SegmentoSgt segmento")
        							.append(" where not segmento.codSgt in (:msg)")
        							.append(" and segmento.sisGrupoAtributoSga is not null");
            
        	Query query = this.getSession().createQuery(select.toString());  
            query.setString("msg", properties.getMensagem("admPortal.itensFixos"));
            return  query.list();            
        } catch(HibernateException e){
            this.logger.error(e);
            throw new ECARException(e);              
        }
    }
    
    /**
     * 
     * @return List
     * @throws ECARException
     */
    public List getAtivos() throws ECARException{
    	SegmentoSgt segmento = new SegmentoSgt();
    	segmento.setIndAtivoSgt("S");
    	return this.pesquisar(segmento);
    }
}
