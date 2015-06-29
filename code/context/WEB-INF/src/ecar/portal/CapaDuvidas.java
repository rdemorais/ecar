package ecar.portal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ecar.dao.SegmentoCategoriaDao;
import ecar.exception.ECARException;
import ecar.pojo.UsuarioUsu;


public class CapaDuvidas extends Portal{
	
//	 DAOs acessados pela classe
	private SegmentoCategoriaDao segmentoCategoriaDao = null;
	
	private List listSegmentoCategorias;
	
	private String segmentoCategoriaSelecionada = "";
	
	/**
     * Construtor.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param UsuarioUsu usuario
     * @param HttpServletRequest request
     * @throws ECARException
     */
	public CapaDuvidas(UsuarioUsu usuario, HttpServletRequest request) throws ECARException {
		super(request);

		this.segmentoCategoriaDao= new SegmentoCategoriaDao(null);
		
		this.carregarComboSegmentoCategoria(usuario);
		
	}    
    
	/**
     * Construtor para acesso público das categorias.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param HttpServletRequest request
     * @throws ECARException
     */
	public CapaDuvidas(HttpServletRequest request) throws ECARException {
		super(request);

		this.segmentoCategoriaDao= new SegmentoCategoriaDao(null);
		
		this.carregarComboSegmentoCategoriaAcessoPublico();
		
	}    

	/**
     * Método para retornar o DAO da tabela SegmentoCategoriaDao.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return SegmentoCategoriaDao
     */
	private SegmentoCategoriaDao getSegmentoCategoriaDao() {
		return this.segmentoCategoriaDao;
	}

    /**
     * Método para popular combo categorias.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param UsuarioUsu usuario
     * @throws ECARException
     */
	private void carregarComboSegmentoCategoria(UsuarioUsu usuario) throws ECARException {
		
		//this.setListSegmentoCategorias(this.getSegmentoCategoriaDao().getSegmentoCategoriasDuvidaAtivo());
		this.setListSegmentoCategorias(this.getSegmentoCategoriaDao().getSegmentoCategoriasVinculadasAoUsuario(usuario));
		
	}    

    /**
     * Método para popular combo categorias para acesso público.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
     */
	private void carregarComboSegmentoCategoriaAcessoPublico() throws ECARException {
		
		//this.setListSegmentoCategorias(this.getSegmentoCategoriaDao().getSegmentoCategoriasDuvidaAtivo());
		this.setListSegmentoCategorias(this.getSegmentoCategoriaDao().getSegmentoCategoriasAcessoPublico());
		
	}    

	/**
	 * Retorna List listSegmentoCategorias.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return List
	 */
	public List getListSegmentoCategorias() {
		return listSegmentoCategorias;
	}

	/**
	 * Atribui valor especificado para List listSegmentoCategorias.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param List listSegmentoCategorias
	 */
	private void setListSegmentoCategorias(List listSegmentoCategorias) {
		this.listSegmentoCategorias = listSegmentoCategorias;
	}

	/**
	 * Retorna String segmentoCategoriaSelecionada.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public String getSegmentoCategoriaSelecionada() {
		return segmentoCategoriaSelecionada;
	}

	/**
	 * Atribui valor especificado para String segmentoCategoriaSelecionada.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param String segmentoCategoriaSelecionada
	 */
	public void setSegmentoCategoriaSelecionada(String segmentoCategoriaSelecionada) {
		this.segmentoCategoriaSelecionada = segmentoCategoriaSelecionada;
	}
	
		

}
