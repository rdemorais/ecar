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

import ecar.dao.UfDao;
import ecar.exception.ECARException;
import ecar.pojo.Uf;

/**
 * @author garten
 *
 */
public class PopUpUf implements PopUpPesquisa {
    

    // array de nomes de campos em que pode pesquisar
    private String[] pesquisarEm;
    
    // declare o seu pojo e o Dao aqui
    private Uf uf;
    private UfDao ufDao;

    /**
     *
     */
    public PopUpUf(){
        uf = new Uf();
        ufDao = new UfDao(null);
        this.setPesquisarEm(new String[] {"Descri&ccedil;&atilde;o", "C&oacute;digo da Uf"});
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
        return ufDao;
    }
    
    /**
     * Retorna String "Uf".<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getTitulo(){
        return "Uf";
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
     * @throws ECARException
     */
    public void setArgPesquisa(HttpServletRequest request) throws ECARException{
    	String arg = Pagina.getParam(request, "hidArg");
        String[] pesquisarEm = request.getParameterValues("hidPesquisarEm");
        
        if (pesquisarEm != null)
	        for (int i = 0; i < pesquisarEm.length; i++) {
	            if ("0".equals(pesquisarEm[i]))
	                uf.setDescricaoUf(arg);
	            
	            if ("1".equals(pesquisarEm[i]))
	                uf.setCodUf(arg);
	        }
    }

    /**
     * Efetua pesquisa de uf.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return List
     * @throws ECARException
     */
    public List pesquisar() throws ECARException {
        return ufDao.pesquisar(uf, new String[] {"codUf", "asc"});
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
        return uf.getCodUf();
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
        return uf.getDescricaoUf();
    }
    
    /**
     * Recebe um objeto do template.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param o
     */
    public void setPojo(Object o) {
        uf = (Uf) o;
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
     * @param String[] pesquisarEm - The pesquisarEm to set.
     */
    private void setPesquisarEm(String[] pesquisarEm) {
        this.pesquisarEm = pesquisarEm;
    }
}
