/*
 * Created on 14/12/2004
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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.dao.EstruturaFuncaoDao;
import ecar.dao.FonteRecursoDao;
import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.FonteRecursoFonr;
import ecar.pojo.ItemEstruturaIett;


/**
 * @author garten
 */
public class PopUpFonteRecurso implements PopUpPesquisa {
    

    // array de nomes de campos em que pode pesquisar
    private String[] pesquisarEm;
    
    // declare o seu pojo e o Dao aqui
    private FonteRecursoFonr pojo;
    private FonteRecursoDao dao;

    /**
     *
     */
    public PopUpFonteRecurso(){
        pojo = new FonteRecursoFonr();
        dao = new FonteRecursoDao(null);
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
        return dao;
    }
    
    /**
     * Retorna String "Categoria Econômica".<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return	String
     */
    public String getTitulo(){
    	return "Categoria Econômica";
    }
    
    /**
     * Retorna String do label de Fonte de Recurso.<br>
     * 
     * @param request
     * @author Milton Pereira e Thaise Dantas
     * @since N/C
     * @version N/C
     * @return	String
     * @throws ECARException
     */
    public String getTitulo(HttpServletRequest request) throws ECARException {
    	String titulo = "";
    	
    	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
    	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParam(request,"codIett")));
    	
    	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
    	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
    	
   		estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(Pagina.getParam(request,"codAba")));
    		
    	titulo = estruturaFuncaoDao.getLabelFuncaoFonteRecurso(estruturaFuncao.getEstruturaEtt()).toString();
    	
    	return titulo;
    }

    /**
     * Recebe o argumento de pesquisa do template jsp e seta nos respectivos campos.<br>
     * String arg - a string do argumento d pesquisa.<br>
     * String[] pesquisarEm - um array de string com os nomes dos campos que devem ser pesquisados.<br>
     * 
     * @param request
     * @author N/C
     * @since N/C
     * @version N/C
     * @throwsECARException
     */
    public void setArgPesquisa(HttpServletRequest request) throws ECARException{
    	String arg = Pagina.getParam(request, "hidArg");
        String[] pesquisarEm = request.getParameterValues("hidPesquisarEm");
        
        if (pesquisarEm != null)
	        for (int i = 0; i < pesquisarEm.length; i++) {
	            if ("0".equals(pesquisarEm[i]))
	                pojo.setNomeFonr(arg);
	        }
    }

    /**
     * Retorna List com os pojos procurados.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return List
     * @throws ECARException
     */
    public List pesquisar() throws ECARException {
        pojo.setIndAtivoFonr("S");
        return dao.pesquisar(pojo, new String[] {"codFonr", "asc"});
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
        return pojo.getCodFonr().toString();
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
        return pojo.getNomeFonr();
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
        pojo = (FonteRecursoFonr) o;
    }

    /**
     * Retorna para o template um array com os nomes para montar os checkbox.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String[] - (Returns the pesquisarEm.)
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
}
