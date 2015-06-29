/*
 * Criado em 03/05/2005
 *
 */
package ecar.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.FileUpload;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.SegmentoCategoriaSgtc;
import ecar.pojo.SegmentoSgt;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.UsuarioUsu;

/**
 * @author felipev
 *
 */
public class SegmentoCategoriaDao extends Dao{
        
    /**
     * Construtor. Chama o Session factory do Hibernate
     * @param request
     */
    public SegmentoCategoriaDao(HttpServletRequest request) {
		super();
		this.request = request;
    }
    
    /**
     * Salva um registro de categoria de segmento a partir de parâmetros recebidos por request por um formulário de Upload.
     * Salva a coleção de tipos de acesso da categoria de  segmento.
     * Salva a imagem selecionada em disco.
     * @param segmentoCategoria
     * @param campos
     * @param pathRaiz
     * @param pathRelativo
     * @throws ECARException
     */
    public void salvar(SegmentoCategoriaSgtc segmentoCategoria, List campos, String pathRaiz, String pathRelativo) throws ECARException{        
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

            setSegmentoCategoriaUpload(segmentoCategoria, campos);
        
            criarCollectionTipoAcesso(segmentoCategoria, campos, null, true);
        
            session.save(segmentoCategoria);
			objetos.add(segmentoCategoria);
        
            /* grava a imagem e atualiza o segmento para vincular o nome da imagem */
        
            uploadImagem(segmentoCategoria, campos, pathRaiz , pathRelativo);
            session.update(segmentoCategoria);
			objetos.add(segmentoCategoria);

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
     * Exclui um registro de kategoria de segmento. Exclui a imagem vinculada caso exista.
     * @param segmentoCategoria
     * @param pathRaiz
     * @throws ECARException
     */
    public void excluir(SegmentoCategoriaSgtc segmentoCategoria, String pathRaiz) throws ECARException{
        try{          
            boolean excluir = true;
            if(contar(segmentoCategoria.getSegmentoItemSgtis()) > 0){ 
                excluir = false;
                throw new ECARException("segmentoCategoria.exclusao.erro.segmentoItem");
            }                      
            if(excluir){
                if(segmentoCategoria.getImagemSgtc()!=null && !segmentoCategoria.getImagemSgtc().equals("")){
                    if (FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segmentoCategoria.getImagemSgtc(), ""))){
                        super.excluir(segmentoCategoria);                                
                    } else {
                        throw new ECARException("erro.excluirArquivo");        
                    }
                } else 
                    super.excluir(segmentoCategoria);                
            }
        }
        catch(ECARException e){
        	this.logger.error(e);
            throw e;
        }
        catch (Exception e){
        	this.logger.error(e);
            throw new ECARException(e);
        }
    }
        
    /**
     * Altera um registro de categoria de segmento a partir de parâmetros recebidos por request por um formulário de Upload.
     * Altera a coleção de tipos de acesso da categoria de segmento.
     * Salva a imagem selecionada em disco e apaga a uma imagem existente anteriormente.
     * @param segmentoCategoria
     * @param campos
     * @param pathRaiz
     * @param pathRelativo
     * @throws ECARException
     */
   
    
    public void alterar(SegmentoCategoriaSgtc segmentoCategoria, List campos, String pathRaiz, String pathRelativo) throws ECARException{        
        
    	String flagImagem = "";
    	
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();
    		
    		if (("".equals(FileUpload.verificaValorCampo(campos, "imagemSgtc"))) && 
    				(!"".equals(FileUpload.verificaValorCampo(campos, "imagem")))){
    				if(segmentoCategoria.getImagemSgtc()!=null){
    				
    					if (!FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segmentoCategoria.getImagemSgtc(), ""))){
    						throw new ECARException("erro.excluirArquivo");
    						
    					}   					
    				}    		
    				
    				flagImagem = "excluidaImagem";
    				
    		}
    	
	    	setSegmentoCategoriaUpload(segmentoCategoria, campos);
	    	
	    	if ("excluidaImagem".equalsIgnoreCase(flagImagem)){
	    		segmentoCategoria.setImagemSgtc("");
				session.save(segmentoCategoria);
				objetos.add(segmentoCategoria);
	    	}

	    	if ("".equals(FileUpload.verificaValorCampo(campos, "legImagemSgtc"))){
    			segmentoCategoria.setLegImagemSgtc("");
    			session.save(segmentoCategoria);
    			objetos.add(segmentoCategoria);
    		}
	    	
	        criarCollectionTipoAcesso(segmentoCategoria, campos, null, true);
	        uploadImagem(segmentoCategoria, campos, pathRaiz , pathRelativo);

	        session.update(segmentoCategoria);
			objetos.add(segmentoCategoria);

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
     * Realiza o upload da imagem da Categoria de Segmento. 
     * Seta no objeto segmento referência para tal imagem.
     * Apaga uma imagem existente anteriormente em caso de alteração.
     * @param segmentoCategoria
     * @param campos
     * @param pathRaiz
     * @param pathRelativo
     * @throws ECARException
     */
    public void uploadImagem(SegmentoCategoriaSgtc segmentoCategoria, List campos, String pathRaiz, String pathRelativo) throws ECARException{
        try{
            Iterator it = campos.iterator();  
            while(it.hasNext()){
                FileItem fileItem = (FileItem) it.next();             
                if(!fileItem.isFormField() && !"".equals(fileItem.getName())){    
                    
                    if(segmentoCategoria.getImagemSgtc() != null)
                        FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, segmentoCategoria.getImagemSgtc(), ""));                        
                    
                    File arquivoGravado = FileUpload.salvarNoDisco(fileItem, 
                                                                   FileUpload.getPathFisico(pathRaiz, 
                                                                                            pathRelativo,  
                                                                                            "segCat" + segmentoCategoria.getCodSgtc() + FileUpload.getNomeArquivo(fileItem)));
                    segmentoCategoria.setImagemSgtc(FileUpload.getPathFisico("", 
                                                                  pathRelativo,  
                                                                  "segCat" + segmentoCategoria.getCodSgtc() + FileUpload.getNomeArquivo(fileItem)));                
                }
            }            
        } catch (Exception e){
        	this.logger.error(e);
            throw new ECARException(e);
        }

    }
    
    /**
     * A partir de dados passados por request popula um objeto SegmentoCategoriaSgtc
     * @param segmentoCategoria
     * @param campos
     * @param recuperarParametrosComoString indica se irá recuperar dados nulos como String vazia
     * @throws ECARException
     */
    public void setSegmentoCategoria(SegmentoCategoriaSgtc segmentoCategoria, 
                        HttpServletRequest campos,                         
                        boolean recuperarParametrosComoString
                        ) throws ECARException{
        
        try{
            
            if(!"".equals(Pagina.getParamStr(campos, "segmentoSgt"))){
                SegmentoSgt segmento = (SegmentoSgt) super.buscar(SegmentoSgt.class, Long.valueOf(Pagina.getParamStr(campos, "segmentoSgt")));
                segmentoCategoria.setSegmentoSgt(segmento);
            } else {
                segmentoCategoria.setSegmentoSgt(null);
            }
            
            if(recuperarParametrosComoString){
                segmentoCategoria.setTituloSgtc(Pagina.getParamStr(campos, "tituloSgtc"));
                segmentoCategoria.setDescricaoSgtc(Pagina.getParamStr(campos, "descricaoSgtc"));
                segmentoCategoria.setLegImagemSgtc(Pagina.getParamStr(campos, "legImagemSgtc"));                                
                segmentoCategoria.setIndAtivoSgtc(Pagina.getParamStr(campos, "indAtivoSgtc"));
                segmentoCategoria.setIndUtilizTpAcessoSgtc(Pagina.getParamStr(campos, "indUtilizTpAcessoSgtc"));
            } else {
                segmentoCategoria.setTituloSgtc(Pagina.getParam(campos, "tituloSgtc"));
                segmentoCategoria.setDescricaoSgtc(Pagina.getParam(campos, "descricaoSgtc"));
                segmentoCategoria.setLegImagemSgtc(Pagina.getParam(campos, "legImagemSgtc"));                                
                segmentoCategoria.setIndAtivoSgtc(Pagina.getParam(campos, "indAtivoSgtc"));
                segmentoCategoria.setIndUtilizTpAcessoSgtc(Pagina.getParam(campos, "indUtilizTpAcessoSgtc"));
            }
            criarCollectionTipoAcesso(segmentoCategoria, null, campos, false);
        } catch(Exception e){
            this.logger.error(e);
            throw new ECARException(e);
        }
        
    }

    /**
     * A partir de um List de campos obtidos na requsição de um formulário de upload 
     * popula um objeto SegmentoCategoriaSgtc
     * @param segmentoCategoria
     * @param campos
     * @throws ECARException
     */
    public void setSegmentoCategoriaUpload(SegmentoCategoriaSgtc segmentoCategoria, List campos) throws ECARException{
        
        try{
            
            if(!"".equals(FileUpload.verificaValorCampo(campos, "segmentoSgt"))){
                SegmentoSgt segmento = (SegmentoSgt) super.buscar(SegmentoSgt.class, Long.valueOf(FileUpload.verificaValorCampo(campos, "segmentoSgt")));
                segmentoCategoria.setSegmentoSgt(segmento);
            }
            
            segmentoCategoria.setTituloSgtc(FileUpload.verificaValorCampo(campos, "tituloSgtc"));
            segmentoCategoria.setDescricaoSgtc(FileUpload.verificaValorCampo(campos, "descricaoSgtc"));
            segmentoCategoria.setLegImagemSgtc(FileUpload.verificaValorCampo(campos, "legImagemSgtc"));
            segmentoCategoria.setIndAtivoSgtc(FileUpload.verificaValorCampo(campos, "indAtivoSgtc"));
            segmentoCategoria.setIndUtilizTpAcessoSgtc(FileUpload.verificaValorCampo(campos, "indUtilizTpAcessoSgtc"));

        } catch(Exception e){
            this.logger.error(e);
            throw new ECARException(e);
        }
        
    }
    
    
    /**
     * Cria a coleção de atributos de Tipo de Acesso de Uma Categoria de Segmento
     * @param segmentoCategoria
     * @param request
     */
    private void criarCollectionTipoAcesso(SegmentoCategoriaSgtc segmentoCategoria, List campos, HttpServletRequest request, boolean usarFileUpload) throws ECARException{
        try{
            Object[] codigosTpAcesso;
            if(usarFileUpload)
            	codigosTpAcesso = FileUpload.verificaValorCampoArray(campos, "sisAtributoSatb");
            else
            	codigosTpAcesso = request.getParameterValues("sisAtributoSatb");
            
            if(codigosTpAcesso != null){

                for(int i = 0; i<codigosTpAcesso.length;i++){
                    SisAtributoSatb atributo = (SisAtributoSatb) super.buscar(SisAtributoSatb.class, Long.valueOf(codigosTpAcesso[i].toString()));
                    if(segmentoCategoria.getSegmentoCategTpAcessSgts() != null && !segmentoCategoria.getSegmentoCategTpAcessSgts().contains(atributo))
                        segmentoCategoria.getSegmentoCategTpAcessSgts().add(atributo);
                    else{
                        segmentoCategoria.setSegmentoCategTpAcessSgts(new HashSet());
                        if(!segmentoCategoria.getSegmentoCategTpAcessSgts().contains(atributo)){
                            segmentoCategoria.getSegmentoCategTpAcessSgts().add(atributo);    
                        }
                        
                    }                
                }                        	
            }
            
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException(e);
        }
    }
    

    /**
     * Retorna uma List com todos as identificações dos Atributos que representam os Tipos de Acesso de uma 
     * Categoria de Segmento
     * @param segmentoCategoria
     * @return
     */
    public List getTipoAcessoSegmentoCategoriaById(SegmentoCategoriaSgtc segmentoCategoria){
        List retorno = new ArrayList();
        if(segmentoCategoria.getSegmentoCategTpAcessSgts() != null && segmentoCategoria.getSegmentoCategTpAcessSgts().size() > 0){
            Iterator it = segmentoCategoria.getSegmentoCategTpAcessSgts().iterator();
            while(it.hasNext()){
                SisAtributoSatb segmentoTpAcesso = (SisAtributoSatb) it.next();
                retorno.add(segmentoTpAcesso.getCodSatb());
            }
        }
        return retorno;
    }
    
    /**
     * 
     * @param segmento
     * @return Collection
     * @throws ECARException
     */
    public Collection getAtivosBySegmento(SegmentoSgt segmento) throws ECARException{
    	try{
        	Query query = this.getSession().createQuery(
        			"select sgtc from SegmentoCategoriaSgtc sgtc" +
        			" where sgtc.segmentoSgt.codSgt = :codSgt" +
        			" and sgtc.indAtivoSgtc = 'S'");  
        	query.setLong("codSgt", segmento.getCodSgt().longValue());
    		return query.list();
    	}catch(HibernateException e){
            this.logger.error(e);
    		throw new ECARException(e);
    	}
    }
    
    /**
     * Invoca o método pesquisar do Dao e itera sobre o resultado para inicializar as Collections de Tipo de Acesso de Cada
     * objeto retornado. 
     * @param segmentoCategoria
     * @return List
     * @throws ECARException
     */
    public List pesquisar(SegmentoCategoriaSgtc segmentoCategoria)
			throws ECARException {

		List pesquisa = super.pesquisar(segmentoCategoria, new String[] {
				"tituloSgtc", "asc" });

		if (segmentoCategoria.getSegmentoCategTpAcessSgts() != null) {
			/*
			 * Percorre o resultado e retira dele todas os SegmentoCategoriaSgtc onde
			 * dentro da coleção de getSegmentoCategTpAcessSgts não existam todos os
			 * atributos de SegmentoCategoriaSgtc passados no objeto de pesquisa
			 */
			List atributosPesquisa = new ArrayList();
			atributosPesquisa.addAll(segmentoCategoria
					.getSegmentoCategTpAcessSgts());

			Iterator it = pesquisa.iterator();
			while (it.hasNext()) {
				List atributosResultado = new ArrayList();
				Iterator itAtribResultado = ((SegmentoCategoriaSgtc) it.next())
						.getSegmentoCategTpAcessSgts().iterator();

				while (itAtribResultado.hasNext())
					atributosResultado.add((SisAtributoSatb) itAtribResultado
							.next());

				if (!atributosResultado.containsAll(atributosPesquisa))
					it.remove();
			}
		}

		if (pesquisa.size() > 0) {
			Iterator it = pesquisa.iterator();
			while (it.hasNext()) {
				SegmentoCategoriaSgtc sgtc = (SegmentoCategoriaSgtc) it.next();
				sgtc.getSegmentoCategTpAcessSgts().size();
			}
		}
		return pesquisa;
	}
    
    /**
	 * Invoca o método pesquisar do Dao e itera sobre o resultado para
	 * inicializar as Collections de Tipo de Acesso de Cada objeto retornado e
	 * retirar dele SegmentosCategoria que não pertencam a Segmentos de itens
	 * Livres.
	 * 
     * @param segmentoCategoria
	 * @return
	 * @throws ECARException
	 */
    public List pesquisarSegmentosCategoriaItensLivres(SegmentoCategoriaSgtc segmentoCategoria) throws ECARException{
       
       List pesquisa = super.pesquisar(segmentoCategoria, new String[] {"tituloSgtc","asc"});
       if(pesquisa.size() > 0){
           Iterator it = pesquisa.iterator();
           while(it.hasNext()){
                SegmentoCategoriaSgtc sgtc = (SegmentoCategoriaSgtc) it.next();
                if(sgtc.getSegmentoSgt() != null && sgtc.getSegmentoSgt().getCodSgt().longValue() > 4 )
                    sgtc.getSegmentoCategTpAcessSgts().size();
                else
                    it.remove();
           }           
       }
       return pesquisa;
    }
    
      
    /**
     * Devolve uma lista com os segmentos de categoria de dúvidas ativos visíveis ao usuário
     * 
     * @param usuario 
     * @return List
     * @throws ECARException
     */    
    public List getSegmentoCategoriasVinculadasAoUsuario(UsuarioUsu usuario) throws ECARException {
        List lista = null;
        List lista2 = null;
        Query query2 = null;
        
        try {
            
        	StringBuilder select = new StringBuilder("select i from SegmentoCategoriaSgtc i where i.indAtivoSgtc = 'S' ")
        						.append("and i.segmentoSgt.codSgt = 3 ")
        						.append("and ((i.indUtilizTpAcessoSgtc = 'N') or ")
        						.append("(i.indUtilizTpAcessoSgtc = 'S' and i.codSgtc in (" )
        						.append("select b.comp_id.codSgtc from SegmentoCategoriaTpAcesSgt b ")
        						.append("where b.comp_id.codSatb in (")
        						.append("select a.comp_id.codSatb from UsuarioAtributoUsua a ")
        						.append("where a.comp_id.codUsu = :codUsu))))")
        						.append(" order by i.dataInclusaoSgtc asc");
            		
            Query query = this.getSession().createQuery(select.toString());
            query.setLong("codUsu",usuario.getCodUsu().longValue());
            lista = query.list();
            
            Iterator it = lista.iterator();
            
			while (it.hasNext()) {
				
				StringBuilder select2 = new StringBuilder("select i from SegmentoItemSgti i where i.indAtivoSgti = 'S' ")
								.append("and i.segmentoCategoriaSgtc.codSgtc = :codSgtc")
								.append(" and i.segmentoSgt.codSgt = 3 ")
								.append("and ((i.indUtilizTpAcessoSgti = 'N') or ")
								.append("(i.indUtilizTpAcessoSgti = 'S' and i.codSgti in (")
								.append("select b.comp_id.codSgti from SegmentoItemTpAcesSgtITA b ")
								.append("where b.comp_id.codSatb in (")
								.append("select a.comp_id.codSatb from UsuarioAtributoUsua a ")
								.append("where a.comp_id.codUsu = :codUsu))))")
								.append(" order by i.dataItemSgti asc");
				
				query2 = this.getSession().createQuery(select2.toString());
				query2.setLong("codUsu",usuario.getCodUsu().longValue());
				query2.setLong("codSgtc",((SegmentoCategoriaSgtc) it.next()).getCodSgtc().longValue());
	            lista2 = query2.list();
				
				if (lista2.size() < 1)
					it.remove();
			}

            		
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        
        return lista;
    }
            
    /**
     * Devolve uma lista com os segmentos de categoria de dúvidas ativos para acesso público
     * 
     * @return List
     * @throws ECARException
     */    
    public List getSegmentoCategoriasAcessoPublico() throws ECARException {
        List lista = null;
        List lista2 = null;
        Query query2 = null;
        
        try {
            
        	StringBuilder select = new StringBuilder("select i from SegmentoCategoriaSgtc i where i.indAtivoSgtc = 'S' ")
        						.append("and i.segmentoSgt.codSgt = 3 ")
        						.append("and ((i.indUtilizTpAcessoSgtc = 'N') or ")
        						.append("(i.indUtilizTpAcessoSgtc = 'S' and i.codSgtc in (")
        						.append("select b.comp_id.codSgtc from SegmentoCategoriaTpAcesSgt b ")
        						.append("where b.sisAtributoSatb.codSatb in (")
        						.append("select cfg.sisAtributoSatbByCodSaAcesso.codSatb from ConfiguracaoCfg cfg ")
        						.append("))))")
        						.append(" order by i.dataInclusaoSgtc asc");
            		
            Query query = this.getSession().createQuery(select.toString());
            lista = query.list();
            
            Iterator it = lista.iterator();
            
			while (it.hasNext()) {
				
				StringBuilder select2 = new StringBuilder("select i from SegmentoItemSgti i where i.indAtivoSgti = 'S' ")
									.append("and i.segmentoCategoriaSgtc.codSgtc = :codSgtc")
									.append(" and i.segmentoSgt.codSgt = 3 ")
									.append("and ((i.indUtilizTpAcessoSgti = 'N') or ")
									.append("(i.indUtilizTpAcessoSgti = 'S' and i.codSgti in (")
									.append("select b.comp_id.codSgti from SegmentoItemTpAcesSgtITA b ")
									.append("where b.sisAtributoSatb.codSatb in (")
									.append("select cfg.sisAtributoSatbByCodSaAcesso.codSatb from ConfiguracaoCfg cfg ")
									.append("))))")
									.append(" order by i.dataItemSgti asc");
				
				query2 = this.getSession().createQuery(select2.toString());
				query2.setLong("codSgtc",((SegmentoCategoriaSgtc) it.next()).getCodSgtc().longValue());
	            lista2 = query2.list();
				
				if (lista2.size() < 1)
					it.remove();
			}

            		
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        
        return lista;
    }
    
    /**
     * Devolve uma lista com os segmentos de categoria de glossário ativos visíveis ao usuário
     * 
     * @param usuario 
     * @return List
     * @throws ECARException
     */    
    public List getSegmentoCategoriasGlossarioVinculadasAoUsuario(UsuarioUsu usuario) throws ECARException {
        List lista = null;
               
        try {
            
        	StringBuilder select = new StringBuilder("select i from SegmentoCategoriaSgtc i where i.indAtivoSgtc = 'S' ")
        						.append("and i.segmentoSgt.codSgt = 4 ");
        						
            if(usuario != null && usuario.getCodUsu() != null) {
            	select.append("and ((i.indUtilizTpAcessoSgtc = 'N') or ")
        				.append("(i.indUtilizTpAcessoSgtc = 'S' and i.codSgtc in (")
        				.append("select b.comp_id.codSgtc from SegmentoCategoriaTpAcesSgt b ")
        				.append("where b.comp_id.codSatb in (")
        				.append("select a.comp_id.codSatb from UsuarioAtributoUsua a ")
        				.append("where a.comp_id.codUsu = :codUsu))))");
            } else {
            	select.append("and i.indUtilizTpAcessoSgtc = 'N'");
            }
			select.append(" order by i.dataInclusaoSgtc asc");
            		
            Query query = this.getSession().createQuery(select.toString());
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
    
    
    
}
