/*
 * Created on 17/12/2004
 * 
 * Classe que implementa a interface PopUpPesquisa que é utilizada pelo template jsp
 * popup_pesquisa.jsp
 * 
 * O template instancia essa classe utilizando os métodos definidos na interface.
 * Assim o template pode ser usado para pesquisar e navegar em várias classes sem 
 * precisar escrever um jsp específico para cada um.
 * Basta criar uma classe que implementa a interface PopUpPesquisa e passá-la como
 * parâmatro no momento de chamar a tela de pesquisa
 * 
 * A tela de pesquisa sempre retorna um código e uma descrição do que foi selecionado.
 *
 */
package ecar.popup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.dao.LocalItemDao;
import ecar.exception.ECARException;
import ecar.pojo.ItemEstrutLocalIettl;
import ecar.pojo.LocalGrupoLgp;
import ecar.pojo.LocalItemLit;

/**
 * @author evandro
 *
 */
public class PopUpLocalItem implements PopUpPesquisa {
    

    private String[] pesquisarEm;
    private List locais;
    
    
    // declare o seu pojo e o Dao aqui
    private LocalItemLit localItem;
    private LocalItemDao localItemDao;

    /**
     *
     */
    public PopUpLocalItem(){
        localItem = new LocalItemLit();
        localItemDao = new LocalItemDao(null);
        this.setPesquisarEm(new String[] {"Nome"});
    }
    
    /**
     * Devolve para o template jsp o Dao.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return Dao
     * @see ecar.popup.PopUpPesquisa#getDao()
     */
    public Dao getDao() {
        return localItemDao;
    }

    /**
     * Recebe o argumento de pesquisa do template jsp e seta nos respectivos campos.<br>
     * String arg - a string do argumento d pesquisa.<br>
     * String[] pesquisarEm - um array de string com os nomes dos campos que devem ser pesquisados.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param request
     * @throws ECARException
     */ 
    public void setArgPesquisa(HttpServletRequest request) throws ECARException{
        String arg = Pagina.getParam(request, "hidArg");
        String[] pesquisarEm = request.getParameterValues("hidPesquisarEm");
        String codLgp = Pagina.getParamStr(request, "codLgp");
        
        localItem.setLocalGrupoLgp((LocalGrupoLgp) new Dao().buscar(LocalGrupoLgp.class, Long.valueOf(codLgp)));
    	if (pesquisarEm != null)
	        for (int i = 0; i < pesquisarEm.length; i++) {
	            if ("0".equals(pesquisarEm[i]))
	                localItem.setIdentificacaoLit(arg);
	            
	        }
    	
    	if(request.getSession().getAttribute("listaLocaisItem") != null)
    		locais = new ArrayList((Set)request.getSession().getAttribute("listaLocaisItem"));
    }
    
    /**
     * Retorna List com locais do item pesquisado.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return List
     * @throws ECARException
     */
    public List pesquisar() throws ECARException {
        localItem.setIndAtivoLit("S");
        List retorno = localItemDao.pesquisar(localItem, new String[]{"identificacaoLit", "asc"});
        //return localItemDao.pesquisar(localItem, new String[] {"identificacaoLit", "asc"});
        if(locais != null && locais.size() > 0){
        	Iterator itLocais = locais.iterator();
        	while(itLocais.hasNext()){
        		ItemEstrutLocalIettl itemLocal = (ItemEstrutLocalIettl) itLocais.next();
        		
        		Iterator itLocaisPesquisados = retorno.iterator();
        		while(itLocaisPesquisados.hasNext()){
        			LocalItemLit local = (LocalItemLit) itLocaisPesquisados.next();
        			if(local.equals(itemLocal.getLocalItemLit())){
        				itLocaisPesquisados.remove();
        			}
        		}
        	}
        }
        return retorno;
    }
    
    /**
     * Devolve para o template o codigo do radio button.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getCodigo() {
        return localItem.getCodLit().toString();
    }
    
    /**
     * Devolve para o template a descricao.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getDescricao() {
        return localItem.getIdentificacaoLit();
    }
    
    /**
     * Recebe um objeto do template.<br>
     * 
     * @param o
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public void setPojo(Object o) {
        localItem = (LocalItemLit) o;
    }

    /**
     * Retorna para o template um array com os nomes para montar os checkbox.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String[] - (Returns the pesquisarEm)
     */
    public String[] getPesquisarEm() {
        return pesquisarEm;
    }

    /**
     * Atribui internamente um array com os campos possiveis para a pesquisa.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param String[] pesquisarEm - (The pesquisarEm to set)
     */
    private void setPesquisarEm(String[] pesquisarEm) {
        this.pesquisarEm = pesquisarEm;
    }

    /**
     * Retorna String "Localização".<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getTitulo() {
        return "Localização";
    }
}
