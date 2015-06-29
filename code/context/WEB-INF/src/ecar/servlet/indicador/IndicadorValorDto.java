package ecar.servlet.indicador;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IndicadorValorDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String previsto = "-";
	private String realizado = "-";
	private String cor = "";
	private String atingimento = "-";
	private String justificativa;
	private boolean exigeJustificativa = false;
	private boolean edicaoJustificativa = false;
	
	public String getPrevisto() {
		return previsto;
	}
	public void setPrevisto(String previsto) {
		this.previsto = previsto;
	}
	public String getRealizado() {
		return realizado;
	}
	public void setRealizado(String realizado) {
		this.realizado = realizado;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public String getAtingimento() {
		return atingimento;
	}
	public void setAtingimento(String atingimento) {
		this.atingimento = atingimento;
	}
	public boolean isExigeJustificativa() {
		return exigeJustificativa;
	}
	public void setExigeJustificativa(boolean exigeJustificativa) {
		this.exigeJustificativa = exigeJustificativa;
	}
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	public Boolean getEdicaoJustificativa() {
		return edicaoJustificativa;
	}
	public void setEdicaoJustificativa(Boolean edicaoJustificativa) {
		this.edicaoJustificativa = edicaoJustificativa;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((atingimento == null) ? 0 : atingimento.hashCode());
		result = prime * result + ((cor == null) ? 0 : cor.hashCode());
		result = prime * result + (edicaoJustificativa ? 1231 : 1237);
		result = prime * result + (exigeJustificativa ? 1231 : 1237);
		result = prime * result
				+ ((justificativa == null) ? 0 : justificativa.hashCode());
		result = prime * result
				+ ((previsto == null) ? 0 : previsto.hashCode());
		result = prime * result
				+ ((realizado == null) ? 0 : realizado.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof IndicadorValorDto)) {
			return false;
		}
		IndicadorValorDto other = (IndicadorValorDto) obj;
		if (atingimento == null) {
			if (other.atingimento != null) {
				return false;
			}
		} else if (!atingimento.equals(other.atingimento)) {
			return false;
		}
		if (cor == null) {
			if (other.cor != null) {
				return false;
			}
		} else if (!cor.equals(other.cor)) {
			return false;
		}
		if (edicaoJustificativa != other.edicaoJustificativa) {
			return false;
		}
		if (exigeJustificativa != other.exigeJustificativa) {
			return false;
		}
		if (justificativa == null) {
			if (other.justificativa != null) {
				return false;
			}
		} else if (!justificativa.equals(other.justificativa)) {
			return false;
		}
		if (previsto == null) {
			if (other.previsto != null) {
				return false;
			}
		} else if (!previsto.equals(other.previsto)) {
			return false;
		}
		if (realizado == null) {
			if (other.realizado != null) {
				return false;
			}
		} else if (!realizado.equals(other.realizado)) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return "IndicadorValorDto [previsto=" + previsto + ", realizado="
				+ realizado + ", cor=" + cor + ", atingimento=" + atingimento
				+ ", justificativa=" + justificativa + ", exigeJustificativa="
				+ exigeJustificativa + ", edicaoJustificativa="
				+ edicaoJustificativa + "]";
	}
	
}