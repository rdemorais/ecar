/*
 * Criado em 22/12/2004
 *
 */
package ecar.popup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;
import comum.util.Pagina;
import comum.util.Util;

import ecar.dao.ExercicioDao;
import ecar.dao.FonteRecursoDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.ItemEstruturaPrevisaoDao;
import ecar.dao.RecursoDao;
import ecar.exception.ECARException;
import ecar.pojo.ExercicioExe;
import ecar.pojo.FonteRecursoFonr;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.RecursoRec;

/**
 * @author felipev
 *
 */
public class PopUpRecurso implements PopUpPesquisa{
    

    // array de nomes de campos em que pode pesquisar
    private String[] pesquisarEm;
    
    // declare o seu pojo e o Dao aqui
    private RecursoRec pojo;
    private RecursoDao dao;
    
    private ExercicioExe exercicio;
    private ItemEstruturaIett item;
    private FonteRecursoFonr fonte;

    /**
     *
     */
    public PopUpRecurso(){
        pojo = new RecursoRec();
        dao = new RecursoDao(null);
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
     * Retorna String "Recursos".<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getTitulo(){
        return "Recursos";
    }

    /**
     * Recebe o argumento de pesquisa do template jsp e seta nos respectivos campos.<br>
     * String arg 			- a string do argumento de pesquisa.<br>
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
    	if(!"".equals(Pagina.getParamStr(request, "codExercicio"))){
    	    exercicio = (ExercicioExe) new ExercicioDao(null).buscar(ExercicioExe.class, Long.valueOf(Pagina.getParamStr(request, "codExercicio")));
    	}
    	if(!"".equals(Pagina.getParamStr(request, "codIett"))){
    	    item = (ItemEstruturaIett) new ItemEstruturaDao(null).buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
    	}
    	if(!"".equals(Pagina.getParamStr(request, "codFonr"))){
    	    fonte = (FonteRecursoFonr) new FonteRecursoDao(null).buscar(FonteRecursoFonr.class, Long.valueOf(Pagina.getParamStr(request, "codFonr")));
    	}        
    	String[] pesquisarEm = request.getParameterValues("hidPesquisarEm");
        
        if (pesquisarEm != null)
	        for (int i = 0; i < pesquisarEm.length; i++) {
	            if ("0".equals(pesquisarEm[i]))
	                pojo.setNomeRec(arg);
	        }
    }

    /**
     * Retorna recursos dentro do periodo definido no exercicio.<br>
     * Metodo que realiza pesquisa.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return List
     * @throws ECARException
     */
    public List pesquisar() throws ECARException {        
        try{
            Query query = new RecursoDao(null).getSession().createQuery(
            		"from RecursoRec r where not (:dataInicial < r.dataIniValidadeRec or :dataFinal > r.dataFimValidadeRec) and r.indAtivoRec='S'");
            query.setDate("dataInicial", exercicio.getDataInicialExe());
            query.setDate("dataFinal", exercicio.getDataFinalExe());
            List recursos = query.list(); 
            List recursosCadastrados = new ItemEstruturaPrevisaoDao(null).getRecursosByFonteRecursoExercicio(item, exercicio, fonte);
            Collection resultado = Util.diferenca(recursos, recursosCadastrados);
            List retorno = new ArrayList();
            retorno.addAll(resultado);
            return retorno;
        } catch(HibernateException e){
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
            throw new ECARException(e);
        }
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
        return pojo.getCodRec().toString();
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
        return pojo.getNomeRec();
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
        pojo = (RecursoRec) o;
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
    

}
