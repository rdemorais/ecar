/*
 * Criado em 20/04/2005
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.Cor;
import ecar.pojo.ItemEstrutMarcadorIettm;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.UsuarioUsu;

/**
 * @author felipev
 *
 */
public class ItemEstrutMarcadorDao extends Dao{

    /**
     *
     * @param request
     */
    public ItemEstrutMarcadorDao(HttpServletRequest request) {
		super();
		this.request = request;
    }
    
    /**
     * Cria um objeto itemEstrutMarcador a partir de par�metros passados
     * no objeto request
     * 
     * @param request
     * @param itemEstruturaMarcador
     * @throws ECARException
     */
    public void setItemEstrutMarcador(HttpServletRequest request,
            ItemEstrutMarcadorIettm itemEstruturaMarcador) throws ECARException {
        ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) this.buscar(
                ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(
                        request, "codIett")));
        itemEstruturaMarcador.setItemEstruturaIett(itemEstrutura);
        if(!"".equals(Pagina.getParamStr(request, "codCor")))
            itemEstruturaMarcador.setCor( (Cor) this.buscar(Cor.class, Long.valueOf(Pagina.getParamStr(request, "codCor"))));
        else
            itemEstruturaMarcador.setCor( null );
        itemEstruturaMarcador.setDescricaoIettm(Pagina.getParamStr(request, "descricao"));
        itemEstruturaMarcador.setUsuarioUsu((UsuarioUsu) this.buscar(UsuarioUsu.class, Long.valueOf(Pagina.getParamStr(request, "codUsu"))));
        
    }
    
    /**
     * 
     * @param itemEstruturaMarcador
     * @throws ECARException
     */
    public void salvar(ItemEstrutMarcadorIettm itemEstruturaMarcador) throws ECARException{
        itemEstruturaMarcador.setDataInclusaoIettm(Data.getDataAtual());
        super.salvar(itemEstruturaMarcador);
    }
    
    /**
     * Retorna uma lista com todos os marcadores de um item feitos por um usu�rio
     * @param item
     * @param usuario
     * @return
     * @throws ECARException
     */
    public Collection getMarcadoresByUsuario(ItemEstruturaIett item, UsuarioUsu usuario) throws ECARException{
    	List retorno = new ArrayList();
        try {
        	
        	Iterator itIettms = item.getItemEstrutMarcadorIettms().iterator();
        	
        	while (itIettms.hasNext()){
        		ItemEstrutMarcadorIettm itemEstrutMarcadorIettm = (ItemEstrutMarcadorIettm) itIettms.next();
        		if (itemEstrutMarcadorIettm.getUsuarioUsu().equals(usuario)){
        			retorno.add(itemEstrutMarcadorIettm);
        		}
        		
        		
        	}
        	
        	return retorno;
        	
            //return this.getSession().
            //        createFilter(item.getItemEstrutMarcadorIettms(), 
            //                "where this.usuarioUsu = " + usuario.getCodUsu() + " order by this.dataInclusaoIettm desc").list();
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException(e);
        }    
    }
    
    
    /**
     * Retorna o ultimo marcador de um item para um usu�rio, se n�o existirem marcadores, retorna null 
     * @param item
     * @param usuario 
     * @return ItemEstrutMarcadorIettm
     * @throws ECARException
     */
    public ItemEstrutMarcadorIettm getUltimoMarcador(ItemEstruturaIett item, UsuarioUsu usuario) throws ECARException{
        if(item.getItemEstrutMarcadorIettms().size() == 0)
            return null;
        else {
            Collection marcadores = this.getMarcadoresByUsuario(item, usuario);
            if(marcadores!= null && !marcadores.isEmpty()){
                return (ItemEstrutMarcadorIettm) marcadores.iterator().next();
            }                    
            else
                return null;
        }                         
    }
    
    //Método criado pelo DATASUS - 08/03/2012 para otimizar listagem de itens
    public ItemEstrutMarcadorIettm getUltimoMarcador(ItemEstruturaIett item, List<Long> listaIettms,UsuarioUsu usuario) throws ECARException{
       
    	if(!listaIettms.contains(item.getCodIett())){
    		return null;
    	}else {
            Collection marcadores = this.getMarcadoresByUsuario(item, usuario);
            if(marcadores!= null && !marcadores.isEmpty()){
                return (ItemEstrutMarcadorIettm) marcadores.iterator().next();
            }                    
            else
                return null;
        }                         
    }
    
    //Método criado pelo DATASUS - 08/03/2012 para otimizar listagem de itens
    public List<ItemEstrutMarcadorIettm> getListaItemEstrutMarcadorIettm(List<Long> listaCodigoitem, UsuarioUsu usuario) throws ECARException{
    	List<ItemEstrutMarcadorIettm> lista = null;
    	
    	try{
    	    	StringBuilder query = new StringBuilder("select iettm from ItemEstrutMarcadorIettm iettm")
    	    							.append(" join fetch iettm.itemEstruturaIett iett ")
    	    							.append(" join fetch iettm.usuarioUsu usuario ")
    	    							.append(" where iett.codIett in (:listCodIett)")
    	    							.append(" and usuario.codUsu = :codUsu");

    	    	
    	    	Query q = this.getSession().createQuery(query.toString());
    			q.setParameterList("listCodIett", listaCodigoitem);
    			q.setLong("codUsu",usuario.getCodUsu());

    			q.setCacheable(true);
    			q.setCacheMode(CacheMode.NORMAL);

    			lista = q.list();
    			
    			if(lista == null || lista.isEmpty()){
                    return new ArrayList<ItemEstrutMarcadorIettm>();
                } else { 
                	return lista;
                }
        } catch(HibernateException e){
			this.logger.error(e);
            throw new ECARException(e);
        }
    }
    
    /**
     * Recebe um array de c�digos de ItemEstrutMArcador e exclui os registros
     * referenciados por estes c�digos
     * 
     * @param codigosParaExcluir
     * @throws ECARException
     */
    public void excluir(String[] codigosParaExcluir) throws ECARException {
        List excluir = new ArrayList();
        for (int i = 0; i < codigosParaExcluir.length; i++) {
            ItemEstrutMarcadorIettm itemEstrutMarcador = (ItemEstrutMarcadorIettm) this
                    .buscar(ItemEstrutMarcadorIettm.class, Long
                            .valueOf(codigosParaExcluir[i]));
            excluir.add(itemEstrutMarcador);            
        }
        super.excluir(excluir);
    }
}
