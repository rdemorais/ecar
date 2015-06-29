/*
 * Criado em 19/01/2005
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

import comum.database.Dao;
import comum.util.ConstantesECAR;
import comum.util.Data;
import comum.util.FileUpload;

import ecar.exception.ECARException;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ItemEstrUplCategIettuc;
import ecar.pojo.ItemEstrutUploadIettup;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.UploadTipoCategoriaUtc;
import ecar.util.Dominios;

/**
 * @author felipev
 *
 */
public class ItemEstruturaUploadCategoriaDao extends Dao{
    
    
    /**
     *
     * @param request
     */
    public ItemEstruturaUploadCategoriaDao(HttpServletRequest request) {
		super();
		this.request = request;

	}
    
    /**
     * Retorna as Categorias de Anexo ativas de um Item
     * @param itemEstrutura
     * @return Collection de Objetos ItemEstrUplCategIettuc
     * @throws ECARException
     */
    public Collection getAtivos(ItemEstruturaIett itemEstrutura) throws ECARException{
        try {
            Query query = this.getSession().createQuery(
            		"select iettuc from ItemEstrUplCategIettuc iettuc" +
            		" where iettuc.itemEstruturaIett.codIett = :codIett" +
            		" and iettuc.itemEstruturaIett.indAtivoIett = 'S'" +
            		" and iettuc.indAtivoIettuc = 'S'");
            query.setLong("codIett", itemEstrutura.getCodIett().longValue());
            
            return query.list();
        } catch(HibernateException e){
        	this.logger.error(e);
            throw new ECARException(e);
        }        
    }
    
    /**
     * Retorna os ItemEstrUplCategIettuc ativos de um UploadTipoCategoria e de um ItemEstrutura
     * @param uploadTipoCat 
     * @param itemEstrutura
     * @return Collection de Objetos ItemEstrUplCategIettuc
     * @throws ECARException
     */
    public List getItemEstrUplCategIettucs(UploadTipoCategoriaUtc uploadTipoCat, ItemEstruturaIett itemEstrutura) throws ECARException{
        try{
        	StringBuilder select = new StringBuilder("select itemEstrutUpCat from ItemEstrUplCategIettuc as itemEstrutUpCat")
        					.append(" where itemEstrutUpCat.uploadTipoCategoriaUtc.codUtc = :codUtc")
        					.append(" and itemEstrutUpCat.itemEstruturaIett.codIett = :codIett")
        					.append(" and itemEstrutUpCat.itemEstruturaIett.indAtivoIett = 'S'")
        					.append(" and itemEstrutUpCat.indAtivoIettuc = :indAtivo")
        					.append(" order by itemEstrutUpCat.nomeIettuc");
        	
        	Query query = this.getSession().createQuery(select.toString());
        	query.setLong("codUtc", uploadTipoCat.getCodUtc().longValue());
        	query.setLong("codIett", itemEstrutura.getCodIett().longValue());
        	query.setString("indAtivo", "S");
        	
        	List listaItemEstUC = query.list();
        	excluiInativos(listaItemEstUC);    		
    		return listaItemEstUC;
        	
        }catch(HibernateException e){
            this.logger.error(e);
            throw new ECARException(e);
        }        
    }
    
    
    /**
     * Retorna os ItemEstrUplCategIettuc ativas de um UploadTipoCategoria e de um ItemEstrutura
     * 		com parâmetros para paginação.
     * @param uploadTipoCat
     * @param itemEstrutura
     * @param pagina
     * @param resultados Pagina
     * @return Collection de Objetos ItemEstrUplCategIettuc
     * @throws ECARException
     */
    public List getItemEstrUplCategIettucsPaginacao(UploadTipoCategoriaUtc uploadTipoCat, 
    					ItemEstruturaIett itemEstrutura,
						int pagina,
						int resultados) throws ECARException{
        try{
        	StringBuilder select = new StringBuilder("select itemEstrutUpCat from ItemEstrUplCategIettuc as itemEstrutUpCat")
        						.append(" where itemEstrutUpCat.uploadTipoCategoriaUtc.codUtc = :codUtc")
        						.append(" and itemEstrutUpCat.itemEstruturaIett.codIett = :codIett")
        						.append(" and itemEstrutUpCat.indAtivoIettuc = :indAtivo")
        						.append(" order by itemEstrutUpCat.nomeIettuc");
        	
        	Query query = this.getSession().createQuery(select.toString());
        	query.setLong("codUtc", uploadTipoCat.getCodUtc().longValue());
        	query.setLong("codIett", itemEstrutura.getCodIett().longValue());
        	query.setString("indAtivo", "S");
        	
        	query.setFirstResult(pagina); /* inicia no 0 */
        	query.setMaxResults(resultados);
        	
        	List listaItemEstUC = query.list();
    		excluiInativos(listaItemEstUC);
    		return listaItemEstUC;
        	
        }catch(HibernateException e){
            this.logger.error(e);
            throw new ECARException(e);
        }        
    }
    
    /**
     *
     * @param listaItemEstUC
     * @return
     */
    public int contaAtivos(List listaItemEstUC){
    	int contadorAtivos = 0;
    	Iterator itItemEstUC = listaItemEstUC.iterator();
		while(itItemEstUC.hasNext()){ 
			ItemEstrUplCategIettuc itemEstUC = (ItemEstrUplCategIettuc) itItemEstUC.next();
			if(itemEstUC.getItemEstrutUploadIettups() != null){
				HashSet setItemEstrutUploadIettups = new HashSet();
				Iterator itItemEstrutUploadIettups = itemEstUC.getItemEstrutUploadIettups().iterator();
				while(itItemEstrutUploadIettups.hasNext()){
					ItemEstrutUploadIettup itemEstrutUploadIettup = (ItemEstrutUploadIettup) itItemEstrutUploadIettups.next();
					if(Dominios.ATIVO.equalsIgnoreCase(itemEstrutUploadIettup.getIndAtivoIettup()) ){
						setItemEstrutUploadIettups.add(itemEstrutUploadIettup);
						contadorAtivos++;
					}					
				}
			}
		}
		return contadorAtivos;
    }
    
    /**
     *
     * @param listaItemEstUC
     * @return
     */
    public List excluiInativos(List listaItemEstUC){
    	Iterator itItemEstUC = listaItemEstUC.iterator();
		while(itItemEstUC.hasNext()){
			ItemEstrUplCategIettuc itemEstUC = (ItemEstrUplCategIettuc) itItemEstUC.next();
			if(itemEstUC.getItemEstrutUploadIettups() != null){
				HashSet setItemEstrutUploadIettups = new HashSet();
				Iterator itItemEstrutUploadIettups = itemEstUC.getItemEstrutUploadIettups().iterator();
				while(itItemEstrutUploadIettups.hasNext()){
					ItemEstrutUploadIettup itemEstrutUploadIettup = (ItemEstrutUploadIettup) itItemEstrutUploadIettups.next();
					if(Dominios.ATIVO.equalsIgnoreCase(itemEstrutUploadIettup.getIndAtivoIettup()) ){
						setItemEstrutUploadIettups.add(itemEstrutUploadIettup);
					}					
				}
				itemEstUC.setItemEstrutUploadIettups(setItemEstrutUploadIettups);
			}
		}
		return listaItemEstUC;
    }
    
    /**
     * Contar os getItemEstrUplCategIettucs
     * @param uploadTipoCat 
     * @param itemEstrutura
     * @return Collection de Objetos ItemEstrUplCategIettuc
     * @throws ECARException
     */
    public int contarItemEstrUplCategIettucs(UploadTipoCategoriaUtc uploadTipoCat, ItemEstruturaIett itemEstrutura) throws ECARException{
        try{
        	int resultado = 0;
        	
        	StringBuilder select = new StringBuilder("select itemEstrutUpCat from ItemEstrUplCategIettuc itemEstrutUpCat")
        						.append(" where itemEstrutUpCat.uploadTipoCategoriaUtc.codUtc = :codUtc")
        						.append(" and itemEstrutUpCat.itemEstruturaIett.codIett = :codIett")
        						.append(" and itemEstrutUpCat.indAtivoIettuc = :indAtivo");
        	
            Query query = this.getSession().createQuery(select.toString());
            query.setLong("codUtc", uploadTipoCat.getCodUtc().longValue());
        	query.setLong("codIett", itemEstrutura.getCodIett().longValue());
        	query.setString("indAtivo", "S");
            
            List retorno = query.list();
            if(!retorno.isEmpty()) {
            	resultado = contaAtivos(retorno);
            	//resultado = retorno.size();
            }
        	return resultado;
        }catch(HibernateException e){
            this.logger.error(e);
            throw new ECARException(e);
        }        
    }
    
    
    /**
     * Constrói um objeto ItemEstrUplCategIettuc a partir de atributos passados no request
     * @param campos
     * @param categoria
     * @param pathRaiz 
     * @param pathRelativo 
     * @param salvarImagem
     * @throws ECARException
     */
    public void setItemEstruturaUploadCategoria(List campos, ItemEstrUplCategIettuc categoria, String pathRaiz, String pathRelativo, boolean salvarImagem) throws ECARException{
        
        try{
            Iterator it = campos.iterator();  
            while(it.hasNext()){
                FileItem fileItem = (FileItem) it.next();             
                if(!fileItem.isFormField() && !"".equals(fileItem.getName()) && salvarImagem){    
                    /* se o campo é ser tratado é um campo file, e a categoria já possui um arquivo gravado, apaga este 
                     * arquivo
                     */
                    if(categoria.getImagemIettuc() != null)
                        FileUpload.apagarArquivo(pathRaiz + categoria.getImagemIettuc());
                    
                    File arquivoGravado = FileUpload.salvarNoDisco(fileItem, FileUpload.getPathFisico(pathRaiz, pathRelativo, FileUpload.getNomeArquivo(fileItem)));
                    categoria.setImagemIettuc(FileUpload.getPathFisico("", pathRelativo, FileUpload.getNomeArquivo(fileItem)));              
                } else {
                    if("codIett".equals(fileItem.getFieldName())){
                        ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) this.buscar(
                                ItemEstruturaIett.class, Long.valueOf(fileItem.getString()));
                        categoria.setItemEstruturaIett(itemEstrutura);            
                    }
                    if("cod".equals(fileItem.getFieldName()) && !"".equals(fileItem.getString()))
                        categoria.setCodIettuc(Long.valueOf(fileItem.getString()));            
                    /**
                     * SM: 193663 (SS 70171), Mantis: 0021300
                     * Retirada do caractere enter da descrição da categoria 
                     */    
                    if("descricaoIettuc".equals(fileItem.getFieldName()))
                    	categoria.setDescricaoIettuc(fileItem.getString().replaceAll("\r\n", ""));            
                    if("nomeIettuc".equals(fileItem.getFieldName()))
                        categoria.setNomeIettuc(fileItem.getString());            
                    if("uploadTipoCategoriaUtc".equals(fileItem.getFieldName()) && !"".equals(fileItem.getString()))
                        categoria.setUploadTipoCategoriaUtc( (UploadTipoCategoriaUtc) this.buscar(
                        		UploadTipoCategoriaUtc.class, Long.valueOf(fileItem.getString())));
                    if("codAri".equals(fileItem.getFieldName()) && !"".equals(fileItem.getString()))
                    	categoria.setAcompReferenciaItemAri( (AcompReferenciaItemAri) this.buscar(
                    			AcompReferenciaItemAri.class, Long.valueOf(fileItem.getString())));
                   	
                }                           
            }         
                    
            categoria.setIndAtivoIettuc("S");
            
        } catch (Exception e){
            this.logger.error(e);
            throw new ECARException("erro.exception");
        }
        
                
    }
    
    
    /**
     * Constrói um objeto ItemEstrUplCategIettuc a partir de atributos passados no request
     * @param campos
     * @param categoria
     * @param pathRaiz 
     * @param pathRelativo
     * @throws ECARException
     */
    public void setItemEstruturaUploadCategoria(List campos, ItemEstrUplCategIettuc categoria, String pathRaiz, String pathRelativo) throws ECARException{
    	this.setItemEstruturaUploadCategoria(campos, categoria, pathRaiz, pathRelativo, true);
    }
    
    
    /**s
     * Recebe um Array com Códigos de Categorias. Para cada uma das Categorias verifica a existência de 
     * Anexos. Se não existirem exclui a Categoria. Se existirem, dispara uma Exception
     * @param codigosParaExcluir
     * @param pathRaiz
     * @throws ECARException
     */
    public void excluir(String[] codigosParaExcluir, String pathRaiz) throws ECARException {
    	
        for (int i = 0; i < codigosParaExcluir.length; i++) {            
            ItemEstrUplCategIettuc categoria = (ItemEstrUplCategIettuc) this.buscar(ItemEstrUplCategIettuc.class, Long.valueOf(codigosParaExcluir[i]));
            List<ItemEstrUplCategIettuc> listaItemEstruturaUploadCategoria = new ArrayList<ItemEstrUplCategIettuc>();
            listaItemEstruturaUploadCategoria.add(categoria);
            if(contaAtivos(listaItemEstruturaUploadCategoria) > Integer.parseInt(ConstantesECAR.ZERO)){
                throw new ECARException("itemEstrutura.categoriaAnexo.exclusao.possuiUpload");
            //Mudança de requisito. Caso Mantis: 0010956.
            // Deve efetuar exclusão da pasta gerada no acompanhamento pelo cadastro.
            //} else if(categoria.getAcompReferenciaItemAri()!=null){
            //	throw new ECARException("itemEstrutura.categoriaAnexo.exclusao.possuiAri");
            } else {
                this.excluir(categoria, pathRaiz); 
            }
        }
    }
    
    /**
     * Exclui uma categoria de upload e apaga o arquivo de Imagem vinculado a ela
     * @param categoria
     * @param pathRaiz
     * @throws ECARException
     */ 
   public void excluir(ItemEstrUplCategIettuc categoria, String pathRaiz) throws ECARException{
        try{
        	        	
            categoria.setIndAtivoIettuc("N");
        	
            if(categoria.getImagemIettuc()!=null){
                if (FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, categoria.getImagemIettuc(), ""))){
//                    super.excluir(categoria);
                	super.alterar(categoria);
                } else {
                    throw new ECARException("erro.excluirArquivo");        
                }
            } else 
                super.alterar(categoria);
                       
        }
        catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("erro.exception");
        }
        
    }
    
    /**
     * Salva um registro de Categoria de Upload
     * @param categoria
     * @throws ECARException
     */
    public void salvar(ItemEstrUplCategIettuc categoria) throws ECARException{
       	categoria.setDataInclusaoIettuc(Data.getDataAtual());
       	super.salvar(categoria);
    }
    
    /**
     *
     * @param obj
     * @param excluiImagem
     * @param pathRaiz
     * @throws ECARException
     */
    public void alterar(Object obj, String excluiImagem, String pathRaiz) throws ECARException {
    	try{
	    	ItemEstrUplCategIettuc categoria = (ItemEstrUplCategIettuc)obj;
	    	
	    	if ("on".equals(excluiImagem)){
	    		if (FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, categoria.getImagemIettuc(), ""))){
	    			alterar(obj);                                
	            } else {
	                throw new ECARException("erro.excluirArquivo");        
	            }
	    	} else {
	    		alterar(obj);
	    	}
    	}
        catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("erro.exception");
        }
    }
    /**
     * A partir de um ItemEstrUplCategIettuc retornar uma collection de 
     * 	ItemEstrutUploadIettups ordenada por nomeOriginalIettup  
     * @param itemEstUC
     * @return
     * @throws ECARException
     */
    public Collection getItemEstrutUploadIettups(ItemEstrUplCategIettuc itemEstUC) throws ECARException{
    	try{
            Query query = this.getSession().createQuery(
            		"select iettup from ItemEstrutUploadIettup iettup" +
            		" where iettup.itemEstrUplCategIettuc.codIettuc = :codIettuc" +
            		" and iettup.indAtivoIettup = :indAtivo" +
            		" order by iettup.nomeOriginalIettup");
            query.setLong("codIettuc", itemEstUC.getCodIettuc().longValue());
            query.setString("indAtivo", "S");
            
            return query.list();
        } catch(HibernateException e){
            this.logger.error(e);
            throw new ECARException(e);
        } 
    }
}
