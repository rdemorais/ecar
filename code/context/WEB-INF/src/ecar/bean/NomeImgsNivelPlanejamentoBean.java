package ecar.bean;

import javax.servlet.http.HttpServletRequest;


/**
 * Bean contendo o caminho da imagem e seu respectivo title.
 *  
 * @author aleixo
 */
public class NomeImgsNivelPlanejamentoBean {

	private String nome;
	private String title;


	/**
	 * Construtor 
	 * @author aleixo
	 * @since 21/05/2007
	 * @version 0.1
         * @param nome
         * @param title
	 */
	public NomeImgsNivelPlanejamentoBean(String nome, String title){
		this.nome = nome;
		this.title = title;
	}
	
	/**
	 * Retorna String nome.<br>
	 * 
	 * @author aleixo
	 * @since 21/05/2007
	 * @version 0.1
	 * @return String
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Atribui valor especificado para String nome.<br>
	 * 
	 * @author aleixo
	 * @since 21/05/2007
	 * @version 0.1
         * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Retorna String title.<br>
	 * 
	 * @author aleixo
	 * @since 21/05/2007
	 * @version 0.1
	 * @return String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Atribui valor especificado para String title.<br>
	 * 
	 * @author aleixo
	 * @since 21/05/2007
	 * @version 0.1
         * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

        /**
         *
         * @param request
         * @return
         */
        public String geraHtmlImg(HttpServletRequest request){
		String path = request.getContextPath() + "/images/relAcomp/";
		
		if(getNome() != null && !"".equals(getNome()))
			return "<img src=\"" + path + getNome() + "\" title=\"" + getTitle() + "\">&nbsp;";
		else
			return "&nbsp";
	}

}
