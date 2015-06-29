/*
 * Criado em 15/02/2005
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.ItemEstUsutpfuacIettutfaPK;
import ecar.pojo.ItemEstrutUsuarioIettus;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.TipoFuncAcompTpfaPermiteAlterar;
import ecar.pojo.UsuarioAtributoUsua;
import ecar.pojo.UsuarioUsu;

/**
 * @author evandro
 *
 */
public class ItemEstUsutpfuacDao extends Dao{
	
    /**
     *
     */
    public ItemEstUsutpfuacDao() {
		super();
	}
	
    /**
     *
     * @param request
     */
    public ItemEstUsutpfuacDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
    /**
     * Pesquisa ItemEstUsutpfuac referente a ItemEstrutura e funcao
     * 
     * @param itemEstrutura
     * @param funcao
     * @return ItemEstUsutpfuac  - sen�o encontrar retorna NULL
     * @throws ECARException
     */
    public ItemEstUsutpfuacIettutfa getUsuarioAcompanhamento (ItemEstruturaIett itemEstrutura, TipoFuncAcompTpfa funcao) throws ECARException{
    	
    	ItemEstUsutpfuacIettutfa itemEstUsu = new ItemEstUsutpfuacIettutfa();
    	
    	itemEstUsu.setItemEstruturaIett(itemEstrutura);
		itemEstUsu.setTipoFuncAcompTpfa(funcao);
		
		/* buscar em tpfuac o usu�rio a partir de codFuncao e codItem */
		List lista = pesquisar(itemEstUsu, new String[] {"tipoFuncAcompTpfa.codTpfa","asc"});
		Iterator it = lista.iterator();
		
		/* se n�o encontrar nome do usu�rio, devolver vazio */
		if (it.hasNext()){
			return (ItemEstUsutpfuacIettutfa) it.next();
		}
		
		return null;
    }

    /**
     * Obter os ItemEstUsutpfuacIettutfa de uma lista de itens
     * 
     * @param listItemEstrutura
     * @return List
     * @throws ECARException
     */
    public List getItemEstUsutpfuacIettutfa(List listItemEstrutura) throws ECARException {
    	List retorno = new ArrayList();
    	Iterator it = listItemEstrutura.iterator();
    	while(it.hasNext()) {
    		ItemEstruturaIett iett = (ItemEstruturaIett)it.next();
    		if(iett.getItemEstUsutpfuacIettutfas() != null) {
    			retorno.addAll(iett.getItemEstUsutpfuacIettutfas());
    		}
    	}
		return retorno;
    }
    
    /**
     * Retorna uma lista com todos os usu�rios que emitem parecer no acompanhamento de um item.
     * @param itemEstrutura
     * @return
     * @throws ECARException
     */
    public List getUsuariosAcompanhamento (ItemEstruturaIett itemEstrutura) throws ECARException{
    	
        List retorno = new ArrayList();
        
    	ItemEstUsutpfuacIettutfa itemEstUsu = new ItemEstUsutpfuacIettutfa();
    	
    	itemEstUsu.setItemEstruturaIett(itemEstrutura);

    	List lista = pesquisar(itemEstUsu, null);
		Iterator it = lista.iterator();
		
		/* se n�o encontrar nome do usu�rio, devolver vazio */
		while (it.hasNext()){
		    ItemEstUsutpfuacIettutfa item = (ItemEstUsutpfuacIettutfa) it.next();
		    if (item.getUsuarioUsu() != null){
		    	retorno.add(item.getUsuarioUsu());
		    } else if (item.getSisAtributoSatb() != null){
		    	if (item.getSisAtributoSatb().getUsuarioAtributoUsuas() != null){
		    		Iterator itUsuariosAtributos = item.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
		    		while (itUsuariosAtributos.hasNext()){
		    			retorno.add(((UsuarioAtributoUsua) itUsuariosAtributos.next()).getUsuarioUsu());
		    		}
		    	}
		    }
		}		
		return retorno;
    }
    
    /**
     * Retorna um objeto ItemEstUsutpfuacIettutfa a partir dos c�digos
     * 
     * @param codIett
     * @param codTpfa
     * @return
     * @throws ECARException
     */
    public ItemEstUsutpfuacIettutfa buscar(Long codIett, Long codTpfa) throws ECARException {
    	ItemEstUsutpfuacIettutfaPK chave = new ItemEstUsutpfuacIettutfaPK();
    	ItemEstUsutpfuacIettutfa itemEstUsutpfuacIettutfa = null;
    	    	
    	try{
    		chave.setCodIett(codIett);
    		chave.setCodTpfa(codTpfa);
	 
    		return (ItemEstUsutpfuacIettutfa) super.buscar(ItemEstUsutpfuacIettutfa.class, chave);
    
    	} catch (ECARException e) {
    		
    		return itemEstUsutpfuacIettutfa;
    	} 
    }
    
    public ItemEstUsutpfuacIettutfa buscarOtimizada(Long codIett, Long codTpfa) throws ECARException {
    	ItemEstUsutpfuacIettutfaPK chave = new ItemEstUsutpfuacIettutfaPK();
    	ItemEstUsutpfuacIettutfa itemEstUsutpfuacIettutfa = null;
    	    
    	
    	
    	try{
    		StringBuilder query = new StringBuilder("select iettutfa from ItemEstUsutpfuacIettutfa iettutfa")
    								.append(" where iettutfa.comp_id.codIett = :codIett ")
    								.append(" and iettutfa.comp_id.codTpfa = :codTpfa ");
    		
    		Query q = session.createQuery(query.toString());

    		q.setLong("codIett",codIett);
    		q.setLong("codTpfa",codTpfa);
    		
    		List<ItemEstUsutpfuacIettutfa> lista = q.list(); 
    		if(lista != null && !lista.isEmpty()){
    			return lista.get(0); 
    		}else{
    			return itemEstUsutpfuacIettutfa;	
    		}
    	} catch(HibernateException e){
    		return itemEstUsutpfuacIettutfa;
    	}
    }
    
    /**
    *
    * @param codIett
    * @param codTpfa
    * @return
    * @throws ECARException
    */
   public TipoFuncAcompTpfa buscarMaiorHierarquia(Long codIett, Long codTpfa) throws ECARException {
	   TipoFuncAcompTpfa tpfaSuperior = null;
	   List listaEstruturas = new ArrayList();
	   
	   
		Set setTPFAPA = new HashSet();
        try{
        	String select = "from TipoFuncAcompTpfaPermiteAlterar as tipo " +
    		" where tipo.comp_id.cod_inferior_tpfapa.codTpfa= :cod " + 
    		" and tipo.permiteAlterarParecer= :permite";
    		Query q = this.session.createQuery(select);
    		
    		q.setLong("cod", codTpfa);
    		q.setString("permite", "S");
    		
    		setTPFAPA.addAll(q.list());
    		
            } catch(Exception e){
            	e.printStackTrace();
        }
            
        for (Object object : setTPFAPA) {
			
	    	TipoFuncAcompTpfaPermiteAlterar tipo = (TipoFuncAcompTpfaPermiteAlterar) object;
	    	listaEstruturas.add(tipo.getComp_id().getCod_superior_tpfapa());		 
	    
       	}
	   
	   if(listaEstruturas.size() == 1){
		   tpfaSuperior = ((TipoFuncAcompTpfa)listaEstruturas.get(0)).getTipoFuncAcompTpfa();
	   } else if(listaEstruturas.size() > 1){
		   for (Object object : listaEstruturas) {
			   TipoFuncAcompTpfa tpfa = (TipoFuncAcompTpfa) object;
			   if(tpfa.getTipoFuncAcompTpfa()==null){
				   tpfaSuperior = tpfa;
				   break;
			   }
		   }
		   
	   }
	   
	   return tpfaSuperior;	   
   }
    
   /**
    * Recupera a maior hierarquia do tipo de fun��o de acompanhamento associada ao ari e que tenha permiss�o para alterar o parecer
    * @param ari
    * @param tpfa
    * @return
    * @throws ECARException
    */
   public TipoFuncAcompTpfa buscarMaiorHierarquia(ItemEstruturaIett itemEstruturaIett, TipoFuncAcompTpfa tpfa, UsuarioUsu usuarioLogado) throws ECARException {
	   TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
	   UsuarioDao usuarioDao = new UsuarioDao(request);
	   TipoFuncAcompTpfa tpfaSuperior = null;
	   ItemEstUsutpfuacIettutfa iettutfa = null;
	   List<TipoFuncAcompTpfa> funcoesSuperiores = tipoFuncAcompDao.getListaTpfaSuperioresOrderByHierarquia(tpfa);	   
	   for (TipoFuncAcompTpfa tipoFuncAcompTpfa : funcoesSuperiores) {
		   iettutfa = getUsuarioAcompanhamento(itemEstruturaIett, tipoFuncAcompTpfa);
		   //Se existir usu�rio ou grupo associado a fun��o
		   //o sistema verifica se � o usu�rio logado ou algum grupo que ele perten�a
		   if (iettutfa != null){
			   if (iettutfa.getUsuarioUsu() != null && iettutfa.getUsuarioUsu().equals(usuarioLogado)){
				   //Verifica se o usu�rio logado est� associado a fun��o
				   tpfaSuperior = tipoFuncAcompTpfa;
				   break;
			   } else if (iettutfa.getSisAtributoSatb() != null){
				   Set gruposAcessoUsuarioLogado = usuarioDao.getClassesAcessoUsuario(usuarioLogado);
				   //Verifica se o usu�rio logado pertence ao grupo de usu�rio associado a fun��o do item
				   if (gruposAcessoUsuarioLogado != null && gruposAcessoUsuarioLogado.contains(iettutfa.getSisAtributoSatb())){
					   tpfaSuperior = tipoFuncAcompTpfa;
					   break;
				   }
			   }
		   }		   
	   }	   
	   return tpfaSuperior;	   
   }
   
   
   
    /**
     *
     * @param codIett
     * @param codTpfa
     * @return
     * @throws ECARException
     */
    public List buscarSuperiores(Long codIett, Long codTpfa) throws ECARException {
    	
		Set setTPFAPA = new HashSet();
		List listaEstruturas = new ArrayList();
        try{
        	String select = "from TipoFuncAcompTpfaPermiteAlterar as tipo " +
    		" where tipo.comp_id.cod_inferior_tpfapa.codTpfa= :cod " + 
    		" and tipo.permiteAlterarParecer= :permite";
    		Query q = this.session.createQuery(select);
    		
    		q.setLong("cod", codTpfa);
    		q.setString("permite", "S");
    		
    		setTPFAPA.addAll(q.list());
    		
            } catch(Exception e){
            	e.printStackTrace();
            }
    	
    	for (Object object : setTPFAPA) {
			
	    	ItemEstUsutpfuacIettutfaPK chave = new ItemEstUsutpfuacIettutfaPK();
	    	ItemEstUsutpfuacIettutfa itemEstUsutpfuacIettutfa = null;
	    	
	    	TipoFuncAcompTpfaPermiteAlterar tipo = (TipoFuncAcompTpfaPermiteAlterar) object;
	    	
	    	try{
	        
	    		chave.setCodIett(codIett);
	    		chave.setCodTpfa(tipo.getComp_id().getCod_superior_tpfapa().getCodTpfa());
		 
	    		listaEstruturas.add((ItemEstUsutpfuacIettutfa) super.buscar(ItemEstUsutpfuacIettutfa.class, chave));
	    		//return (ItemEstUsutpfuacIettutfa) super.buscar(ItemEstUsutpfuacIettutfa.class, chave);
	    
	    	} catch (ECARException e) {
	    		continue;
	    	}
    	}
    	return listaEstruturas;
    }
    
    
    /**
     * Retorna true se o Usuario possuir alguma fun��o que informa andamento
     * 		para o item passado como par�metro.
     * 
     * @param usuario
     * @param item
     * @return
     * @throws ECARException
     */
    public boolean getFuncaoAcompInfAndamento (UsuarioUsu usuario, ItemEstruturaIett item) throws ECARException{
    	boolean infAndamento = false;
    	
    	ItemEstrutUsuarioIettus itemEstUsu = new ItemEstrutUsuarioIettus();
    	
    	itemEstUsu.setItemEstruturaIett(item);
		itemEstUsu.setUsuarioUsu(usuario);
		itemEstUsu.setCodTpPermIettus("F");
		
		List lista = pesquisar(itemEstUsu, new String[] {"codIettus","asc"});
		Iterator it = lista.iterator();
		
		while(it.hasNext()){
			ItemEstrutUsuarioIettus itemEstUsuLista = (ItemEstrutUsuarioIettus) it.next();
			
			if("S".equalsIgnoreCase(itemEstUsuLista.getIndInfAndamentoIettus()))
				infAndamento = true;
		}
		
	
		Iterator itUsuarios = item.getItemEstUsutpfuacIettutfas().iterator();
		
		while (itUsuarios.hasNext() && !infAndamento){
			
			ItemEstUsutpfuacIettutfa itemEstUsutpfuacIettutfa = (ItemEstUsutpfuacIettutfa) itUsuarios.next();
			
			if (itemEstUsutpfuacIettutfa.getSisAtributoSatb() != null) {
				
				itemEstUsu = new ItemEstrutUsuarioIettus();
		    	
		    	itemEstUsu.setItemEstruturaIett(item);
				itemEstUsu.setSisAtributoSatb(itemEstUsutpfuacIettutfa.getSisAtributoSatb());
				itemEstUsu.setCodTpPermIettus("F");
				
				List listaItemEstUsu = pesquisar(itemEstUsu, new String[] {"codIettus","asc"});
				
				Iterator itItemEstUsu = listaItemEstUsu.iterator();
				
				while (itItemEstUsu.hasNext()){
					
					ItemEstrutUsuarioIettus itemEstUsuLista = (ItemEstrutUsuarioIettus) itItemEstUsu.next();
					
					if("S".equalsIgnoreCase(itemEstUsuLista.getIndInfAndamentoIettus())){
						if (new UsuarioDao().getUsuariosBySisAtributoSatb(itemEstUsutpfuacIettutfa.getSisAtributoSatb()).contains(usuario)){
							infAndamento = true;
							break;
						}
					}
					
				}
				
			}
		}
		
		return infAndamento;
    }

    
    /**
     * Devolve uma lista de ItemEstUsutpfuac de acordo com a ordem correta de filho para pai.
     * A lista cont�m somente FUAC que tenham funcoes que emitem posicao
     * @param item
     * @return
     * @throws ECARException
     */
    public List getFuacEmitePosicaoOrderByFuncAcomp(ItemEstruturaIett item) 
	throws ECARException {
    
	    List lResultado = new ArrayList();
	
	    TipoFuncAcompDao tpfaDao = new TipoFuncAcompDao(request);
	    // lista mestre com a ordem correta 
	    List lFuncAcomp = tpfaDao.ordenarTpfaBySequencia(item.getEstruturaEtt());
	    
	    // lista dos fuac a serem ordenados 
	    Set lItemFuac = item.getItemEstUsutpfuacIettutfas();
	    
	    TipoFuncAcompTpfa funcao; 
	    ItemEstUsutpfuacIettutfa itemFuac;
	    
	    if (item != null && lItemFuac != null) {
	        // loop nas funcoes em ordem de filho para pai 
	        Iterator itFunc = lFuncAcomp.iterator();
	        while (itFunc.hasNext()) {
	            funcao = (TipoFuncAcompTpfa) itFunc.next();
	            Iterator itFuac = lItemFuac.iterator();
	            // busca no fuac a funcao que corresponde � hierarquia 
	            while (itFuac.hasNext()) {
	                itemFuac = (ItemEstUsutpfuacIettutfa) itFuac.next();
	                if (itemFuac.getTipoFuncAcompTpfa().getCodTpfa() == funcao.getCodTpfa()) {
	                    lResultado.add(itemFuac);
	                    break;
	                }
	            }
	        }
	    }
	    return lResultado;
	}

    
    /**
     * Devolve uma lista de ItemEstUsutpfuac de acordo com a ordem correta de filho para pai.
     * @param item
     * @return
     * @throws ECARException
     */
    public List getFuacOrderByFuncAcomp(ItemEstruturaIett item) throws ECARException {
    
	    List lResultado = new ArrayList();
	
	    List lFuncAcomp = new TipoFuncAcompDao(request).getTipoFuncAcompOrdemFilhoAoPai();
	    
	    // lista dos fuac a serem ordenados 
	    Set lItemFuac = item.getItemEstUsutpfuacIettutfas();
	    
	    TipoFuncAcompTpfa funcao; 
	    ItemEstUsutpfuacIettutfa itemFuac;
	    
	    if (item != null && lItemFuac != null) {
	        // loop nas funcoes em ordem de filho para pai 
	        Iterator itFunc = lFuncAcomp.iterator();
	        while (itFunc.hasNext()) {
	            funcao = (TipoFuncAcompTpfa) itFunc.next();
	            Iterator itFuac = lItemFuac.iterator();
	            // busca no fuac a funcao que corresponde � hierarquia 
	            while (itFuac.hasNext()) {
	                itemFuac = (ItemEstUsutpfuacIettutfa) itFuac.next();
	                if (itemFuac.getTipoFuncAcompTpfa().getCodTpfa() == funcao.getCodTpfa()) {
	                    lResultado.add(itemFuac);
	                    break;
	                }
	            }
	        }
	    }
	    return lResultado;
	}
    
}
