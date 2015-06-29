package ecar.pojo.intercambioDados.tecnologia;

public class DadosTecnologiaPerfilTxtDtpt extends DadosTecnologiaPerfilDtp {

	public DadosTecnologiaPerfilTxtDtpt() {
		super();
		this.setTipoTecnologia(TipoTecnologiaEnum.ARQUIVOTEXTO_PACINTER);
	}

	/**
     * 
     */
	private static final long serialVersionUID = 885675745513100559L;
	/**
     * 
     */
	private String indRejeitarNomenclaturaDiferenteDtpt;
	/**
     * 
     */
	private String separadorCamposDtpt;

	
	/**
	 * @return the indRejeitarNomenclaturaDiferenteDtpt
	 */
	public String getIndRejeitarNomenclaturaDiferenteDtpt() {
		return indRejeitarNomenclaturaDiferenteDtpt;
	}

	/**
	 * @param indRejeitarNomenclaturaDiferenteDtpt
	 *            the indRejeitarNomenclaturaDiferenteDtpt to set
	 */
	public void setIndRejeitarNomenclaturaDiferenteDtpt(
			String indRejeitarNomenclaturaDiferenteDtpt) {
		this.indRejeitarNomenclaturaDiferenteDtpt = indRejeitarNomenclaturaDiferenteDtpt;
	}

	/**
	 * @return the separadorCamposDtpt
	 */
	public String getSeparadorCamposDtpt() {
		return separadorCamposDtpt;
	}

	/**
	 * @param separadorCamposDtpt
	 *            the separadorCamposDtpt to set
	 */
	public void setSeparadorCamposDtpt(String separadorCamposDtpt) {
		this.separadorCamposDtpt = separadorCamposDtpt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int resultSuper = super.hashCode();	
		
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((indRejeitarNomenclaturaDiferenteDtpt == null) ? 0
						: indRejeitarNomenclaturaDiferenteDtpt.hashCode());
		result = prime
				* result
				+ ((separadorCamposDtpt == null) ? 0 : separadorCamposDtpt
						.hashCode());
		
		return resultSuper+result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		//Caso, a classe pai não seja igual ao obj passado como parametro então retorna false e não continua. 
		if (!super.equals(obj)) {
			return false;
		}

		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof DadosTecnologiaPerfilTxtDtpt)) {
			return false;
		}
		DadosTecnologiaPerfilTxtDtpt other = (DadosTecnologiaPerfilTxtDtpt) obj;
		if (indRejeitarNomenclaturaDiferenteDtpt == null) {
			if (other.indRejeitarNomenclaturaDiferenteDtpt != null) {
				return false;
			}
		} else if (!indRejeitarNomenclaturaDiferenteDtpt
				.equals(other.indRejeitarNomenclaturaDiferenteDtpt)) {
			return false;
		}
		if (separadorCamposDtpt == null) {
			if (other.separadorCamposDtpt != null) {
				return false;
			}
		} else if (!separadorCamposDtpt.equals(other.separadorCamposDtpt)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString()
				+ "DadosTecnologiaPerfilTxtDtpt [indRejeitarNomenclaturaDiferenteDtpt="
				+ indRejeitarNomenclaturaDiferenteDtpt
				+ ", separadorCamposDtpt=" + separadorCamposDtpt + "]";
	}

	@Override
	protected void setTipoTecnologia(TipoTecnologiaEnum tipoTecnologia) {
		this.tipoTecnologia = TipoTecnologiaEnum.ARQUIVOTEXTO_PACINTER;
		
	}
}
