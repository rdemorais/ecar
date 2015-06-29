package ecar.pojo;

import java.io.Serializable;

public class VisaoSituacaoDemandaPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7070829349908153187L;
	private Long codVisao;
	private Long codSitd;

	public VisaoSituacaoDemandaPK() {
	}

	public VisaoSituacaoDemandaPK(Long codVisao, Long codSitd) {
		this.codVisao = codVisao;
		this.codSitd = codSitd;
	}

	public Long getCodVisao() {
		return this.codVisao;
	}

	public void setCodVisao(Long codVisao) {
		this.codVisao = codVisao;
	}

	public Long getCodSitd() {
		return this.codSitd;
	}

	public void setCodSitd(Long codSitd) {
		this.codSitd = codSitd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codSitd == null) ? 0 : codSitd.hashCode());
		result = prime * result
				+ ((codVisao == null) ? 0 : codVisao.hashCode());
		return result;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VisaoSituacaoDemandaPK))
			return false;
		VisaoSituacaoDemandaPK castOther = (VisaoSituacaoDemandaPK) other;

		return (this.getCodVisao() == castOther.getCodVisao())
				&& (this.getCodSitd() == castOther.getCodSitd());
	}

	

	

}