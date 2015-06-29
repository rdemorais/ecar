package ecar.portal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ecar.dao.SegmentoCategoriaDao;
import ecar.exception.ECARException;
import ecar.pojo.UsuarioUsu;


public class CapaGlossario extends Portal{
	
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
	public CapaGlossario(UsuarioUsu usuario, HttpServletRequest request) throws ECARException {
		super(request);

		this.segmentoCategoriaDao= new SegmentoCategoriaDao(null);
		
		this.carregarListaSegmentoGlossario(usuario);
		
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
     * Método para selecionar categorias de glossário.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param UsuarioUsu usuario
     * @throws ECARException
     */
	private void carregarListaSegmentoGlossario(UsuarioUsu usuario) throws ECARException {
		
		this.setListSegmentoCategorias(this.getSegmentoCategoriaDao().getSegmentoCategoriasGlossarioVinculadasAoUsuario(usuario));
		
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
	 * Atribui valor especificado para String segmentoCategoriaSelecionada.<brt>
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
