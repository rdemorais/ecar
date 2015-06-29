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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.dao.AtributoDemandaDao;
import ecar.dao.RegDemandaDao;
import ecar.dao.VisaoDao;
import ecar.exception.ECARException;
import ecar.pojo.EntidadeEnt;
import ecar.pojo.RegDemandaRegd;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.VisaoDemandasVisDem;

/**
 * @author garten
 *
 */
public class PopUpRegDemanda implements PopUpPesquisa {
    

    // array de nomes de campos em que pode pesquisar
    private String[] pesquisarEm;
    
    // declare o seu pojo e o Dao aqui
    private RegDemandaRegd pojo;
    private RegDemandaDao dao;
    
    private List objetosIgnorados;
    
    private Set gruposAcesso = null;
    
    private VisaoDemandasVisDem visaoDemandasVisDem = null;
    
    private List visoesAcesso = null;
    
    private UsuarioUsu usuario = null;
    
    private final String descricaoRegd = "descricaoRegd";
    
    private final String entidade = "entidadeDemandaEntds";
    
    private final String nomeSolicitanteRegd= "nomeSolicitanteRegd";
    
    private AtributoDemandaDao atributoDemandaDao = null;
    
    
	/**
     *
     */
    public PopUpRegDemanda() throws ECARException{
        pojo = new RegDemandaRegd();
        dao = new RegDemandaDao(null);
        atributoDemandaDao = new AtributoDemandaDao(null);
        this.setPesquisarEm(new String[] {atributoDemandaDao.getLabelAtributoDemandaFixo(descricaoRegd), 
        								  "Sigla " + atributoDemandaDao.getLabelAtributoDemandaFixo(entidade), 
        								  "Nome " + atributoDemandaDao.getLabelAtributoDemandaFixo(entidade), 
        								  atributoDemandaDao.getLabelAtributoDemandaFixo(nomeSolicitanteRegd)});
    }
    
    /**
     *
     * @param request
     */
    public PopUpRegDemanda(HttpServletRequest request) throws ECARException{
        pojo = new RegDemandaRegd();
        dao = new RegDemandaDao(request);
        atributoDemandaDao = new AtributoDemandaDao(null);
        this.setPesquisarEm(new String[] {atributoDemandaDao.getLabelAtributoDemandaFixo(descricaoRegd), 
        								  "Sigla " + atributoDemandaDao.getLabelAtributoDemandaFixo(entidade), 
        								  "Nome " + atributoDemandaDao.getLabelAtributoDemandaFixo(entidade), 
        								  atributoDemandaDao.getLabelAtributoDemandaFixo(nomeSolicitanteRegd)});
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
     * Retorna String "Demanda".<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getTitulo(){
        return "Demanda";
    }

    /**
     * Recebe o argumento de pesquisa do template jsp e seta nos respectivos campos.<br>
     * String arg - a string do argumento d pesquisa.<br>
     * String[] pesquisarEm um array de string com os nomes dos campos que devem ser pesquisados.<br>
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
        
        if(request.getSession().getAttribute("objetosIgnorados") != null)
        	objetosIgnorados = new ArrayList((List)request.getSession().getAttribute("objetosIgnorados"));
        
        if (request.getSession().getAttribute("gruposAcesso") != null){
        	
        	gruposAcesso = (Set) request.getSession().getAttribute("gruposAcesso");
        }
        
        if (request.getSession().getAttribute("usuario") != null){
        	
        	usuario = (UsuarioUsu) request.getSession().getAttribute("usuario");
        }
              
        VisaoDao visaoDao = new VisaoDao(request);
        
        if (pesquisarEm != null) {
        	EntidadeEnt entidade = null;
	        for (int i = 0; i < pesquisarEm.length; i++) {
	            if ("0".equals(pesquisarEm[i])) {
	                pojo.setDescricaoRegd(arg);
	            } else if ("1".equals(pesquisarEm[i])) {
	            	entidade = new EntidadeEnt();
	            	entidade.setSiglaEnt(arg);
	            } else if ("2".equals(pesquisarEm[i])) {
	            	if (entidade == null) {
	            		entidade = new EntidadeEnt();
	            	}
	            	entidade.setNomeEnt(arg);
	            } else if ("3".equals(pesquisarEm[i])){
	            	pojo.setNomeSolicitanteRegd(arg);
	            }
	        }
	        if (entidade != null) {
	        	pojo.setEntidadeDemandaEntds(new HashSet());
	        	pojo.getEntidadeDemandaEntds().add(entidade);
	        }
	        	        
        }
        visoesAcesso = new ArrayList();
        if (request.getSession().getAttribute("visoesAcesso") != null){
        	
        	visoesAcesso = (List) request.getSession().getAttribute("visoesAcesso");
        } else if (visaoDemandasVisDem == null && request.getSession().getAttribute("visaoDemandasVisDem") != null){
        	visaoDemandasVisDem = (VisaoDemandasVisDem) request.getSession().getAttribute("visaoDemandasVisDem");
        	visoesAcesso.add(visaoDemandasVisDem);
        }
    }

    /**
     * Metodo que efetua pesquisa de demandas.<br>
     *  
     * @author N/C
     * @since N/C
     * @version N/C
     * @return List
     * @throws ECARException
     */
    public List pesquisar() throws ECARException {
        List<RegDemandaRegd> demandas = dao.pesquisarPopUpRegDemanda(pojo, gruposAcesso, usuario, visoesAcesso);
        // NÃO ESTÁ SENDO LEVADO EM CONSIDERAÇÃO SE A DEMANDA ESTÁ INATIVA OU NÃO
        if(demandas != null){
        	Iterator<RegDemandaRegd> it = demandas.iterator();
        	while(it.hasNext()){
        		RegDemandaRegd d = (RegDemandaRegd) it.next();
        		if((objetosIgnorados != null && objetosIgnorados.contains(d)) || "N".equals(d.getIndAtivoRegd()))
        			it.remove();
        	}
        }
        return demandas;
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
        return pojo.getCodRegd().toString();
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
        return pojo.getDescricaoRegd();
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
        pojo = (RegDemandaRegd) o;
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
     * @param String[] pesquisarEm (The pesquisarEm to set)
     */
    private void setPesquisarEm(String[] pesquisarEm) {
        this.pesquisarEm = pesquisarEm;
    }
}
