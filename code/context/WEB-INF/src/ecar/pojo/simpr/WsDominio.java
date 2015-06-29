package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsDominio")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsDominio {
	
	@XmlElement(required = true, nillable = false)
	private Integer codigo;
	
	@XmlElement(required = true, nillable = false)
	private String descricao;

	public static WsDominio newWsDominio(Long codigo, String descricao) {
		return new WsDominio(codigo, descricao);
	}
	
	public WsDominio() {
	}

	public WsDominio(Long codigo, String descricao) {
		this.codigo = Integer.parseInt(codigo.toString());
		this.descricao = descricao;
	}
	
	public WsDominio(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public WsDominio(Integer codEixo, Integer codigoEixo, String descEixo) {
		// TODO Auto-generated constructor stub
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
		return "WsDominio [codigo=" + codigo + ", descricao=" + descricao + "]";
	}
	
}
