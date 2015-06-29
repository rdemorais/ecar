/*
 * Created on 14/12/2004
 * 
 * Classe que implementa a interface PopUpPesquisa que � utilizada pelo template jsp
 * popup_pesquisa.jsp
 * 
 * O template instancia essa classe utilizando os m�todos definidos na interface.
 * Assim o template pode ser usado para pesquisar e navegar em v�rias classes sem 
 * precisar escrever um jsp espec�fico para cada um.
 * Basta criar uma classe que implementa a interface PopUpPesquisa e pass�-la como
 * par�matro no momento de chamar a tela de pesquisa
 * 
 * A tela de pesquisa sempre retorna um c�digo e uma descri��o do que foi selecionado.
 *
 */
package ecar.popup;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.dao.CriterioDao;
import ecar.exception.ECARException;
import ecar.pojo.CriterioCri;

/**
 * @author garten
 */
public class PopUpCriterio implements PopUpPesquisa {
    

    // array de nomes de campos em que pode pesquisar
    private String[] pesquisarEm;
    
    // declare o seu pojo e o Dao aqui
    private CriterioCri pojo;
    private CriterioDao dao;

    /**
     *
     */
    public PopUpCriterio(){
        pojo = new CriterioCri();
        dao = new CriterioDao(null);
        this.setPesquisarEm(new String[] {"Nome"});
    }
    
    /**
     * Devolve para o template jsp o Dao. <br>
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
     * Retorna String "Crit&eacute;rios".<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getTitulo(){
        return "Crit&eacute;rios";
    }

    /**
     * Recebe o argumento de pesquisa do template jsp e seta nos respectivos campos.<br>
     * String arg - a string do argumento d pesquisa<br>
     * String[] pesquisarEm - um array de string com os nomes dos campos que devem ser pesquisados<br>
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
        
        if (pesquisarEm != null)
	        for (int i = 0; i < pesquisarEm.length; i++) {
	            if ("0".equals(pesquisarEm[i]))
	                pojo.setDescricaoCri(arg);
	        }
    }

    /**
     * Retorna List de pojos pesquisados.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return List
     * @throws ECARException
     */
    public List pesquisar() throws ECARException {
        pojo.setIndAtivoCri("S");
        return dao.pesquisar(pojo, new String[] {"descricaoCri", "asc"});
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
        return pojo.getCodCri().toString();
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
        return pojo.getDescricaoCri();
    }
    
    /**
     * Recebe um objeto do template.<br>
     * @author N/C
     * @since N/C
     * @version N/C
     * @param o
     */
    public void setPojo(Object o) {
        pojo = (CriterioCri) o;
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
