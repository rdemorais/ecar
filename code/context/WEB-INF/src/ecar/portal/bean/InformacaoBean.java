/*
 * Created on 11/07/2005
 *
 */
package ecar.portal.bean;


/**
 * Bean contendo as informações de uma notícia que será apresentada em cada seção da tela.<br>
 * 
 * @author cristiano
 */

public class InformacaoBean {

	private String titulo = null;
	private String descricao = null;
	private String dataHora = null;
	private String link = null;
	private String linkMais = null;
	private String imagem = null;
	private Long codSgti = null;
	private String fonte = null;
    
	/**
	 * Retorna String dataHora.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
    public String getDataHora() {
		return dataHora;
	}

    /**
     * Atribui valor especificado para String dataHora.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param String dataHora
     */
	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	/**
	 * Retorna String descricao.<br> 
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Atribui valor especificado para String descricao.<br>
	 * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param String descricao
     */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Retorna String imagem.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public String getImagem() {
		return imagem;
	}

	/**
	 * Atribui valor especificado para String imagem.<br>
	 * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param String imagem
     */
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	/**
	 * Retorna String link.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Atribui valor especificado para String link.<br>
	 * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param String link
     */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Retorna String linkMais.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public String getLinkMais() {
		return linkMais;
	}

	/**
	 * Atribui valor especificado para String linkMais.<br>
	 * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param String linkMais
     */
	public void setLinkMais(String linkMais) {
		this.linkMais = linkMais;
	}

	/**
	 * Retorna String titulo.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Atribui valor especificado para String titulo.<br>
	 * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param String titulo
     */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	/**
	 * Retorna Long codSgti.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return Long
	 */
	public Long getCodSgti() {
		return codSgti;
	}

	/**
	 * Atribui valor especificado para Long codSgti.<br>
	 * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param Long codSgti
     */
	public void setCodSgti(Long codSgti) {
		this.codSgti = codSgti;
	}
	
	/**
	 * Retorna Strig fonte.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public String getFonte() {
		return fonte;
	}

	/**
	 * Atribui valor especificado para String fonte.<br>
	 * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param String fonte
     */
	public void setFonte(String fonte) {
		this.fonte = fonte;
	}

	/**
     * Construtor.<br>
     */
	public InformacaoBean() {
		
	}

	
}
