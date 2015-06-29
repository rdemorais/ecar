package ecar.pojo.acompanhamentoEstrategico;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author Rafael de Morais
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Comentario {
	private long codigo;
	private Date dataInclusao;
	private String texto;
	private long codResultado;
	
	@XmlTransient
	private long codResponsavel;
	
	private Responsavel responsavel;
	private Date prazo;
	
	public Comentario() {
	}
	
	public Comentario(Long codigo, Date dataInclusao, String texto, Date prazo, long codResponsavel, String nome) {
		super();
		this.codigo = codigo;
		this.dataInclusao = dataInclusao;
		this.texto = texto;
		this.prazo = prazo;
		this.responsavel = new Responsavel();
		this.responsavel.setCodigo(codResponsavel);
		this.responsavel.setNome(nome);
	}

	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public long getCodResultado() {
		return codResultado;
	}
	public void setCodResultado(long codResultado) {
		this.codResultado = codResultado;
	}
	public long getCodResponsavel() {
		return codResponsavel;
	}

	public void setCodResponsavel(long codResponsavel) {
		this.codResponsavel = codResponsavel;
	}

	
	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public Date getPrazo() {
		return prazo;
	}

	public void setPrazo(Date prazo) {
		this.prazo = prazo;
	}

	@Override
	public String toString() {
		return "Comentario [codigo=" + codigo + ", dataInclusao="
				+ dataInclusao + ", prazo=" + prazo + ", texto=" + texto + ", codResultado="
				+ codResultado + ", responsável=" + responsavel.getNome() + "]";
	}
	
}