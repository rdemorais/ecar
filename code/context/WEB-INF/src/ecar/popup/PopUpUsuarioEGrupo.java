/*
 * Created on 06/06/2005
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
 * Esta classe tem mais implementações que outras classes PopUp.
 * Esta classe foi desenvolvida para contemplar o novo Caso de Uso de Editores/Leitores
 * onde pode ser incluído um Grupo (SisAtributoSatb) ou Usuario e dar as devidas
 * permissões.
 * Por este detalhe de pode incluir ou um ou outro, a classe sofreu implementações 
 * diferentes das demais. 
 */
package ecar.popup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.dao.ConfiguracaoDao;
import ecar.dao.UsuarioDao;
import ecar.exception.ECARException;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/**
 * @author garten
 *
 */
public class PopUpUsuarioEGrupo implements PopUpPesquisa {
    

    // array de nomes de campos em que pode pesquisar
    private String[] pesquisarEm;
    
    // declaramos Object e Dao para declaração genérica
    private Object pojo;
    private Dao dao;
    private UsuarioUsu usuario;
    private SisAtributoSatb grupo;
    private UsuarioDao usuarioDao = new UsuarioDao(null);

    /**
     *
     */
    public PopUpUsuarioEGrupo(){
        pojo = new Object();
        dao = new Dao();
        this.setPesquisarEm(new String[] {"Nome","E-mail"});
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
     * Retorna "Usu&aacute;rios e Grupos de Acesso".<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getTitulo(){
        return "Usu&aacute;rios e Grupos de Acesso";
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
    	usuario = new UsuarioUsu();
    	grupo = new SisAtributoSatb();
    	
    	String arg = Pagina.getParam(request, "hidArg");
        String[] pesquisarEm = request.getParameterValues("hidPesquisarEm");
        
        usuario.setIndAtivoUsu(Dominios.SIM);
        
        if (pesquisarEm != null)
	        for (int i = 0; i < pesquisarEm.length; i++) {
	            if("0".equals(pesquisarEm[i])){
	                usuario.setNomeUsu(arg);
	                grupo.setDescricaoSatb(arg);
	            }
	            // o grupo nao possui e-mail, por isso só pesquisa em descrição
	            if("1".equals(pesquisarEm[i]))
	                usuario.setEmail1Usu(arg);
	        }
    }
    
    /**
     * Executamos duas pesquisas e adicionamos os resultados em uma lista
     * que é ordenada de acordo com usuário ou grupo.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return List
     * @throws ECARException
     */ 
    public List pesquisar() throws ECARException {
    	ConfiguracaoCfg configuracao = new ConfiguracaoCfg();
    	ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(null);
    	
    	List lista = new ArrayList();
    	List listaGrupos = new ArrayList();
    	List listaUsuarios = new ArrayList();
    	
    	String nomeUsu = usuario.getNomeUsuSent();
    	String emailUsu = usuario.getEmail1UsuSent();
    	
    	// retirar o nome e email do usuario do bean para não pesquisa-lo no banco de dados ECAR
    	usuario.setNomeUsu(null);
    	usuario.setEmail1Usu(null);
    	
        listaUsuarios.addAll( usuarioDao.pesquisar(usuario, nomeUsu, "", "", emailUsu));
        
        /* Filtrar somente Classe de Acesso - Grupo de Acesso (Cadastro em ConfiguracaoCgf) */
        if(configuracaoDao.getConfiguracao() != null){
        	configuracao = configuracaoDao.getConfiguracao();
        	
        	if( configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrClAcesso() != null ) {
        		grupo.setSisGrupoAtributoSga(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrClAcesso());
        		listaGrupos.addAll( dao.pesquisar(grupo, new String[] {"descricaoSatb", "asc"}) );
        	}
        }
        
        Collections.sort(listaGrupos,
	            new Comparator() {
	        		public int compare(Object o1, Object o2) {
        				return ( (SisAtributoSatb)o1 ).getDescricaoSatb().compareToIgnoreCase( ( (SisAtributoSatb)o2 ).getDescricaoSatb() );
	        		}
	    		} );
        
        Collections.sort(listaUsuarios,
	            new Comparator() {
	        		public int compare(Object o1, Object o2) {
        				return ( (UsuarioUsu)o1 ).getNomeUsuSent().compareToIgnoreCase( ( (UsuarioUsu)o2 ).getNomeUsuSent() );
	        		}
	    		} );
        
        lista.addAll(listaGrupos);
        lista.addAll(listaUsuarios);

        return lista;
    }
    
    /**
     * Para diferenciar o grupo do usuário antes do valor do cógigo é passado a letra
     * "U" para Usuário e "G" para grupo.<br>
     * Esse código é tratado no método setItemUsuarioGrupo do ItemEstrutUsuarioDao,
     * que é executado somente quando o objeto é salvo.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getCodigo() {
    	if( pojo.getClass() == UsuarioUsu.class )
    		return "U"+((UsuarioUsu) pojo).getCodUsu().toString();
    	else
    		return "G"+((SisAtributoSatb) pojo).getCodSatb().toString();
    }
    
    /**
     * Devolve para o template a descricao de acordo com o objeto.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getDescricao() {
    	if( pojo.getClass() == UsuarioUsu.class )
    		return ((UsuarioUsu) pojo).getNomeUsuSent().toString();
    	else
    		return ((SisAtributoSatb) pojo).getDescricaoSatb().toString();
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
    	if( o.getClass() == UsuarioUsu.class)
    		pojo = (UsuarioUsu) o;
    	else
    		pojo = (SisAtributoSatb) o;
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
