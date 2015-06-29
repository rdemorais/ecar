package ecar.pojo.intercambioDados.tecnologia;

import ecar.pojo.AvaliaMetodo;

public class DadosTecnologiaPerfilWsDtpw extends DadosTecnologiaPerfilDtp {

	public DadosTecnologiaPerfilWsDtpw() {
		super();
		this.setTipoTecnologia(null);
		//this.setTipoTecnologia(TipoTecnologiaEnum.WEBSERVICE_PACINTER);
	}

	/**
     * 
     */
	private static final long serialVersionUID = 885675745513100559L;

	private String protocoloServidorPrimarioDtpw;
	/**
     * 
     */
	private String tipoHostServidorPrimarioDtpw;
	/**
     * 
     */
	private String hostServidorPrimarioDtpw;
	/**
     * 
     */
	private String portaServidorPrimarioDtpw;
	/**
     * 
     */
	private String caminhoServicoServidorPrimarioDtpw;
	/**
     * 
     */
	private String loginServidorPrimarioDtpw;
	/**
     * 
     */
	private String senhaServidorPrimarioDtpw;
	/**
     * 
     */
	private String confirmacaoSenhaServidorPrimarioDtpw;
	/**
     * 
     */
	private String protocoloServidorSecundarioDtpw;
	/**
     * 
     */
	private String tipoHostServidorSecundarioDtpw;
	/**
     * 
     */
	private String hostServidorSecundarioDtpw;
	/**
     * 
     */
	private String portaServidorSecundarioDtpw;
	/**
     * 
     */
	private String caminhoServicoServidorSecundario;
	/**
     * 
     */
	private String loginServidorSecundarioDtpw;
	/**
     * 
     */
	private String senhaServidorSecundarioDtpw;
	/**
     * 
     */
	private String confirmacaoSenhaServidorSecundarioDtpw;

	/**
	 * Intervalo de Seleção dos elementos
	 */
	private String indModoIntervaloSelecaoDtpw;


	@Override
	public TipoTecnologiaEnum getTipoTecnologia() {
		//return TipoTecnologiaEnum.WEBSERVICE_PACINTER;
		return null;
	}

	/**
	 * @return the protocoloServidorPrimarioDtpw
	 */
	public String getProtocoloServidorPrimarioDtpw() {
		return protocoloServidorPrimarioDtpw;
	}

	/**
	 * @param protocoloServidorPrimarioDtpw
	 *            the protocoloServidorPrimarioDtpw to set
	 */
	public void setProtocoloServidorPrimarioDtpw(
			String protocoloServidorPrimarioDtpw) {
		this.protocoloServidorPrimarioDtpw = protocoloServidorPrimarioDtpw;
	}

	/**
	 * @return the ipServidorPrimarioDtpw
	 */
	public String getTipoHostServidorPrimarioDtpw() {
		return tipoHostServidorPrimarioDtpw;
	}

	/**
	 * @param ipServidorPrimarioDtpw
	 *            the ipServidorPrimarioDtpw to set
	 */
	public void setTipoHostServidorPrimarioDtpw(String tipoHostServidorPrimarioDtpw) {
		this.tipoHostServidorPrimarioDtpw = tipoHostServidorPrimarioDtpw;
	}

	/**
	 * @return the portaServidorPrimarioDtpw
	 */
	public String getPortaServidorPrimarioDtpw() {
		return portaServidorPrimarioDtpw;
	}

	/**
	 * @param portaServidorPrimarioDtpw
	 *            the portaServidorPrimarioDtpw to set
	 */
	public void setPortaServidorPrimarioDtpw(String portaServidorPrimarioDtpw) {
		this.portaServidorPrimarioDtpw = portaServidorPrimarioDtpw;
	}

	/**
	 * @return the caminhoServicoServidorPrimarioDtpw
	 */
	public String getCaminhoServicoServidorPrimarioDtpw() {
		return caminhoServicoServidorPrimarioDtpw;
	}

	/**
	 * @param caminhoServicoServidorPrimarioDtpw
	 *            the caminhoServicoServidorPrimarioDtpw to set
	 */
	public void setCaminhoServicoServidorPrimarioDtpw(
			String caminhoServicoServidorPrimarioDtpw) {
		this.caminhoServicoServidorPrimarioDtpw = caminhoServicoServidorPrimarioDtpw;
	}

	/**
	 * @return the protocoloServidorSecundarioDtpw
	 */
	public String getProtocoloServidorSecundarioDtpw() {
		return protocoloServidorSecundarioDtpw;
	}

	/**
	 * @param protocoloServidorSecundarioDtpw
	 *            the protocoloServidorSecundarioDtpw to set
	 */
	public void setProtocoloServidorSecundarioDtpw(
			String protocoloServidorSecundarioDtpw) {
		this.protocoloServidorSecundarioDtpw = protocoloServidorSecundarioDtpw;
	}

	/**
	 * @return the ipServidorSecundarioDtpw
	 */
	public String getTipoHostServidorSecundarioDtpw() {
		return tipoHostServidorSecundarioDtpw;
	}

	/**
	 * @param ipServidorSecundarioDtpw
	 *            the ipServidorSecundarioDtpw to set
	 */
	public void setTipoHostServidorSecundarioDtpw(String tipoHostServidorSecundarioDtpw) {
		this.tipoHostServidorSecundarioDtpw = tipoHostServidorSecundarioDtpw;
	}

	/**
	 * @return the portaServidorSecundarioDtpw
	 */
	public String getPortaServidorSecundarioDtpw() {
		return portaServidorSecundarioDtpw;
	}

	/**
	 * @param portaServidorSecundarioDtpw
	 *            the portaServidorSecundarioDtpw to set
	 */
	public void setPortaServidorSecundarioDtpw(
			String portaServidorSecundarioDtpw) {
		this.portaServidorSecundarioDtpw = portaServidorSecundarioDtpw;
	}

	/**
	 * @return the caminhoServicoServidorSecundario
	 */
	public String getCaminhoServicoServidorSecundario() {
		return caminhoServicoServidorSecundario;
	}

	/**
	 * @param caminhoServicoServidorSecundario
	 *            the caminhoServicoServidorSecundario to set
	 */
	public void setCaminhoServicoServidorSecundario(
			String caminhoServicoServidorSecundario) {
		this.caminhoServicoServidorSecundario = caminhoServicoServidorSecundario;
	}
	
	/**
	 * 
	 * @return the indModoIntervaloSelecaoDtpw
	 */
	public String getIndModoIntervaloSelecaoDtpw() {
		return indModoIntervaloSelecaoDtpw;
	}

	/**
	 * 
	 * @param indModoIntervaloSelecaoDtpw
	 */
	public void setIndModoIntervaloSelecaoDtpw(String indModoIntervaloSelecaoDtpw) {
		this.indModoIntervaloSelecaoDtpw = indModoIntervaloSelecaoDtpw;
	}

	/**
	 * 
	 * @return
	 */
	public String getHostServidorPrimarioDtpw() {
		return hostServidorPrimarioDtpw;
	}

	/**
	 * 
	 * @param hostServidorPrimarioDtpw
	 */
	public void setHostServidorPrimarioDtpw(String hostServidorPrimarioDtpw) {
		this.hostServidorPrimarioDtpw = hostServidorPrimarioDtpw;
	}

	/**
	 * 
	 * @return
	 */
	public String getHostServidorSecundarioDtpw() {
		return hostServidorSecundarioDtpw;
	}

	public String getLoginServidorPrimarioDtpw() {
		return loginServidorPrimarioDtpw;
	}

	public void setLoginServidorPrimarioDtpw(String loginServidorPrimarioDtpw) {
		this.loginServidorPrimarioDtpw = loginServidorPrimarioDtpw;
	}

	public String getSenhaServidorPrimarioDtpw() {
		return senhaServidorPrimarioDtpw;
	}

	public void setSenhaServidorPrimarioDtpw(String senhaServidorPrimarioDtpw) {
		this.senhaServidorPrimarioDtpw = senhaServidorPrimarioDtpw;
	}

	@AvaliaMetodo (valida=false)
	public String getConfirmacaoSenhaServidorPrimarioDtpw() {
		return confirmacaoSenhaServidorPrimarioDtpw;
	}

	public void setConfirmacaoSenhaServidorPrimarioDtpw(
			String confirmacaoSenhaServidorPrimarioDtpw) {
		this.confirmacaoSenhaServidorPrimarioDtpw = confirmacaoSenhaServidorPrimarioDtpw;
	}

	
	public String getLoginServidorSecundarioDtpw() {
		return loginServidorSecundarioDtpw;
	}

	public void setLoginServidorSecundarioDtpw(String loginServidorSecundarioDtpw) {
		this.loginServidorSecundarioDtpw = loginServidorSecundarioDtpw;
	}

	public String getSenhaServidorSecundarioDtpw() {
		return senhaServidorSecundarioDtpw;
	}

	public void setSenhaServidorSecundarioDtpw(String senhaServidorSecundarioDtpw) {
		this.senhaServidorSecundarioDtpw = senhaServidorSecundarioDtpw;
	}

	@AvaliaMetodo (valida=false)
	public String getConfirmacaoSenhaServidorSecundarioDtpw() {
		return confirmacaoSenhaServidorSecundarioDtpw;
	}

	public void setConfirmacaoSenhaServidorSecundarioDtpw(
			String confirmacaoSenhaServidorSecundarioDtpw) {
		this.confirmacaoSenhaServidorSecundarioDtpw = confirmacaoSenhaServidorSecundarioDtpw;
	}

	/**
	 * 
	 * @param hostServidorSecundarioDtpw
	 */
	public void setHostServidorSecundarioDtpw(String hostServidorSecundarioDtpw) {
		this.hostServidorSecundarioDtpw = hostServidorSecundarioDtpw;
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
				+ ((caminhoServicoServidorPrimarioDtpw == null) ? 0
						: caminhoServicoServidorPrimarioDtpw.hashCode());
		result = prime
				* result
				+ ((caminhoServicoServidorSecundario == null) ? 0
						: caminhoServicoServidorSecundario.hashCode());
		result = prime
				* result
				+ ((tipoHostServidorPrimarioDtpw == null) ? 0
						: tipoHostServidorPrimarioDtpw.hashCode());
		result = prime
				* result
				+ ((tipoHostServidorSecundarioDtpw == null) ? 0
						: tipoHostServidorSecundarioDtpw.hashCode());
		result = prime
				* result
				+ ((portaServidorPrimarioDtpw == null) ? 0
						: portaServidorPrimarioDtpw.hashCode());
		result = prime
				* result
				+ ((portaServidorSecundarioDtpw == null) ? 0
						: portaServidorSecundarioDtpw.hashCode());
		result = prime
				* result
				+ ((protocoloServidorPrimarioDtpw == null) ? 0
						: protocoloServidorPrimarioDtpw.hashCode());
		result = prime
				* result
				+ ((protocoloServidorSecundarioDtpw == null) ? 0
						: protocoloServidorSecundarioDtpw.hashCode());
		
		result = prime
				* result
				+ ((indModoIntervaloSelecaoDtpw == null) ? 0
						: indModoIntervaloSelecaoDtpw.hashCode());
		
		return resultSuper+result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		// Caso, a classe pai não seja igual ao obj passado como parametro então
		// retorna false e não continua.
		if (!super.equals(obj)) {
			return false;
		}

		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof DadosTecnologiaPerfilWsDtpw)) {
			return false;
		}
		DadosTecnologiaPerfilWsDtpw other = (DadosTecnologiaPerfilWsDtpw) obj;
		if (caminhoServicoServidorPrimarioDtpw == null) {
			if (other.caminhoServicoServidorPrimarioDtpw != null) {
				return false;
			}
		} else if (!caminhoServicoServidorPrimarioDtpw
				.equals(other.caminhoServicoServidorPrimarioDtpw)) {
			return false;
		}
		if (caminhoServicoServidorSecundario == null) {
			if (other.caminhoServicoServidorSecundario != null) {
				return false;
			}
		} else if (!caminhoServicoServidorSecundario
				.equals(other.caminhoServicoServidorSecundario)) {
			return false;
		}
		if (tipoHostServidorPrimarioDtpw == null) {
			if (other.tipoHostServidorPrimarioDtpw != null) {
				return false;
			}
		} else if (!tipoHostServidorPrimarioDtpw.equals(other.tipoHostServidorPrimarioDtpw)) {
			return false;
		}
		if (tipoHostServidorSecundarioDtpw == null) {
			if (other.tipoHostServidorSecundarioDtpw != null) {
				return false;
			}
		} else if (!tipoHostServidorSecundarioDtpw
				.equals(other.tipoHostServidorSecundarioDtpw)) {
			return false;
		}
		if (portaServidorPrimarioDtpw == null) {
			if (other.portaServidorPrimarioDtpw != null) {
				return false;
			}
		} else if (!portaServidorPrimarioDtpw
				.equals(other.portaServidorPrimarioDtpw)) {
			return false;
		}
		if (portaServidorSecundarioDtpw == null) {
			if (other.portaServidorSecundarioDtpw != null) {
				return false;
			}
		} else if (!portaServidorSecundarioDtpw
				.equals(other.portaServidorSecundarioDtpw)) {
			return false;
		}
		if (protocoloServidorPrimarioDtpw == null) {
			if (other.protocoloServidorPrimarioDtpw != null) {
				return false;
			}
		} else if (!protocoloServidorPrimarioDtpw
				.equals(other.protocoloServidorPrimarioDtpw)) {
			return false;
		}
		if (protocoloServidorSecundarioDtpw == null) {
			if (other.protocoloServidorSecundarioDtpw != null) {
				return false;
			}
		} else if (!protocoloServidorSecundarioDtpw
				.equals(other.protocoloServidorSecundarioDtpw)) {
			return false;
		} else if (!indModoIntervaloSelecaoDtpw
				.equals(other.indModoIntervaloSelecaoDtpw)){
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
				+ "DadosTecnologiaPerfilWsDtpw [caminhoServicoServidorPrimarioDtpw="
				+ caminhoServicoServidorPrimarioDtpw
				+ ", caminhoServicoServidorSecundario="	+ caminhoServicoServidorSecundario
				+ ", ipServidorPrimarioDtpw=" + tipoHostServidorPrimarioDtpw
				+ ", ipServidorSecundarioDtpw=" + tipoHostServidorSecundarioDtpw
				+ ", portaServidorPrimarioDtpw=" + portaServidorPrimarioDtpw
				+ ", portaServidorSecundarioDtpw=" + portaServidorSecundarioDtpw
				+ ", protocoloServidorPrimarioDtpw=" + protocoloServidorPrimarioDtpw
				+ ", protocoloServidorSecundarioDtpw=" + protocoloServidorSecundarioDtpw 
				+ ", indModoIntervaloSelecaoDtpw=" + indModoIntervaloSelecaoDtpw + "]";
	}

	@Override
	protected void setTipoTecnologia(TipoTecnologiaEnum tipoTecnologia) {
		this.tipoTecnologia = tipoTecnologia;
		
	}	
}
