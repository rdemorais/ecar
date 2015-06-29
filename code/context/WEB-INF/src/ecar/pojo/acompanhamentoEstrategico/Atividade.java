package ecar.pojo.acompanhamentoEstrategico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

public class Atividade{
	private long codigo;

	private String nome;

	private Date dataInicio;

	private Date dataFim;
	
	@XmlTransient
	private long codProd;

	private List<PeriodoAcompanhamento> periodoAcompanhamento;

	private String siglaAtividade;
	
	private String situacao;
	
	public Atividade(){}
	
	public Atividade(String nome){
		this.nome = nome;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public long getCodProd() {
		return codProd;
	}

	public void setCodProd(long codProd) {
		this.codProd = codProd;
	}

	public List<PeriodoAcompanhamento> getPeriodoAcompanhamento() {
		if(periodoAcompanhamento == null){
			periodoAcompanhamento = new ArrayList<PeriodoAcompanhamento>();
		}
		return periodoAcompanhamento;
	}

	public void setPeriodoAcompanhamento(
			List<PeriodoAcompanhamento> periodosAcompanhamento) {
		this.periodoAcompanhamento = periodosAcompanhamento;
	}

	public String getSiglaAtividade() {
		return siglaAtividade;
	}

	public void setSiglaAtividade(String siglaAtividade) {
		this.siglaAtividade = siglaAtividade;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	
	
}