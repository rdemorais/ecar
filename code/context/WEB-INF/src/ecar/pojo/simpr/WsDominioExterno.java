package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsDominioExterno")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsDominioExterno {
	
	@XmlElement(required = true, nillable = false)
	private Integer codigoExterno;
	
	@XmlElement(required = true, nillable = false)
	private Integer codigo;
	
	@XmlElement(required = true, nillable = false)
	private String descricao;

	public WsDominioExterno() {
	}

	public WsDominioExterno(Integer codigoExterno, Integer codigo,
			String descricao) {
		this.codigoExterno = codigoExterno;
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigoExterno() {
		return codigoExterno;
	}

	public void setCodigoExterno(Integer codigoExterno) {
		this.codigoExterno = codigoExterno;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "WsDominioExterno [codigoExterno=" + codigoExterno + ", codigo="
				+ codigo + ", descricao=" + descricao + "]";
	}
}
