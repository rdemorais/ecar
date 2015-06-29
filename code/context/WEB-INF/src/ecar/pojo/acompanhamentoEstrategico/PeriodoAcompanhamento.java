/***********************************************************************
 * Module:  PeriodoAcompanhamento.java
 * Author:  leonardo.marques
 * Purpose: Defines the Class PeriodoAcompanhamento
 ***********************************************************************/

package ecar.pojo.acompanhamentoEstrategico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import ecar.servlet.indicador.IndicadorMonitoramentoDto;

/** @pdOid 335b7e4c-0d59-4e37-86b9-f44549dd74d9 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PeriodoAcompanhamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private long codigo;
	private String parecer; // descricao_arel
	private String mes; // mes_aref
	private int ano;// ano_aref
	private Long codCor;
	private String nomeCor;
	private int ciclo;
	private long codArel;
	public List<IndicadorMonitoramentoDto> indMonitoramento = new ArrayList<IndicadorMonitoramentoDto>();
	public List<StatusPeriodoAcompanhamento> statusPeriodoAcompanhamento;

	public PeriodoAcompanhamento() {
		this.codigo = 0L;
		this.parecer = "";
		this.mes = "";
		this.ano = 0;
		this.codCor = 0L;
		this.nomeCor = "N&atilde;o Monitorado";
		this.ciclo = 0;
		statusPeriodoAcompanhamento = new ArrayList<StatusPeriodoAcompanhamento>();
		StatusPeriodoAcompanhamento status = new StatusPeriodoAcompanhamento(codCor, nomeCor);
		statusPeriodoAcompanhamento.add(status);
		indMonitoramento = new ArrayList<IndicadorMonitoramentoDto>();
	}

	public PeriodoAcompanhamento(long codigo, String mes, String ano) {
		this.codigo = codigo;
		this.parecer = "";
		this.mes = mes;
		this.ano = Integer.parseInt(ano);
		this.codCor = 0L;
		this.nomeCor = "N&atilde;o Monitorado";
		this.ciclo = 0;
		statusPeriodoAcompanhamento = new ArrayList<StatusPeriodoAcompanhamento>();
		StatusPeriodoAcompanhamento status = new StatusPeriodoAcompanhamento(codCor, nomeCor);
		statusPeriodoAcompanhamento.add(status);
		indMonitoramento = new ArrayList<IndicadorMonitoramentoDto>();
	}

	public PeriodoAcompanhamento(long codigo, String parecer, String mes, String ano, long codCor, String nomeCor, Long codArel) {

		this.codigo = codigo;
		this.parecer = parecer;
		this.mes = mes;
		this.ano = ano != null ? Integer.parseInt(ano) : 0;
		this.codCor = codCor;
		this.nomeCor = nomeCor;
		statusPeriodoAcompanhamento = new ArrayList<StatusPeriodoAcompanhamento>();
		StatusPeriodoAcompanhamento status = new StatusPeriodoAcompanhamento();
		if (this.codCor != null) {
			status = new StatusPeriodoAcompanhamento(codCor, nomeCor);
		}
		statusPeriodoAcompanhamento.add(status);
		indMonitoramento = new ArrayList<IndicadorMonitoramentoDto>();
		this.codArel = codArel;

	}

	/** @pdOid 4d9541e4-415b-4b5d-bc1c-9845e7358160 */
	public String getNome() {
		return this.mes + "/" + this.ano;
	}

	/** @pdOid 0c2796c3-0950-4269-a11e-65d1d0506100 */
	public long getCodigo() {
		return codigo;
	}

	/**
	 * @param newCodigo
	 * @pdOid e255e774-5e31-4f0b-b32d-af638c835a2e
	 */
	public void setCodigo(long newCodigo) {
		codigo = newCodigo;
	}

	/** @pdOid 81ee1375-3732-46b3-9c3c-b1adbfadc5d2 */
	public String getParecer() {
		return parecer;
	}

	/**
	 * @param newParecer
	 * @pdOid 30827c0a-12eb-476b-8628-db2c8eb854e4
	 */
	public void setParecer(String newParecer) {
		parecer = newParecer;
	}

	/** @pdOid 1e8efff3-1d36-4f35-9981-9f91a8c09c1d */
	public String getMes() {
		return mes;
	}

	/**
	 * @param newMes
	 * @pdOid 92a54f9c-cc29-4795-9182-fd07bea5a5e2
	 */
	public void setMes(String newMes) {
		mes = newMes;
	}

	/** @pdOid 5af4e361-670b-47ef-b9f4-3cd54980ce9a */
	public int getAno() {
		return ano;
	}

	/**
	 * @param newAno
	 * @pdOid 4536e720-0213-4ec3-8554-4d37878aeb96
	 */
	public void setAno(int newAno) {
		ano = newAno;
	}

	/** @pdGenerated default getter */
	public List<StatusPeriodoAcompanhamento> getStatusPeriodoAcompanhamento() {
		if (statusPeriodoAcompanhamento == null)
			statusPeriodoAcompanhamento = new java.util.ArrayList<StatusPeriodoAcompanhamento>();
		return statusPeriodoAcompanhamento;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator getIteratorStatusPeriodoAcompanhamento() {
		if (statusPeriodoAcompanhamento == null)
			statusPeriodoAcompanhamento = new java.util.ArrayList<StatusPeriodoAcompanhamento>();
		return statusPeriodoAcompanhamento.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newStatusPeriodoAcompanhamento
	 */
	public void setStatusPeriodoAcompanhamento(List<StatusPeriodoAcompanhamento> newStatusPeriodoAcompanhamento) {
		removeAllStatusPeriodoAcompanhamento();
		for (java.util.Iterator iter = newStatusPeriodoAcompanhamento.iterator(); iter.hasNext();)
			addStatusPeriodoAcompanhamento((StatusPeriodoAcompanhamento) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newStatusPeriodoAcompanhamento
	 */
	public void addStatusPeriodoAcompanhamento(StatusPeriodoAcompanhamento newStatusPeriodoAcompanhamento) {
		if (newStatusPeriodoAcompanhamento == null)
			return;
		if (this.statusPeriodoAcompanhamento == null)
			this.statusPeriodoAcompanhamento = new java.util.ArrayList<StatusPeriodoAcompanhamento>();
		if (!this.statusPeriodoAcompanhamento.contains(newStatusPeriodoAcompanhamento))
			this.statusPeriodoAcompanhamento.add(newStatusPeriodoAcompanhamento);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldStatusPeriodoAcompanhamento
	 */
	public void removeStatusPeriodoAcompanhamento(StatusPeriodoAcompanhamento oldStatusPeriodoAcompanhamento) {
		if (oldStatusPeriodoAcompanhamento == null)
			return;
		if (this.statusPeriodoAcompanhamento != null)
			if (this.statusPeriodoAcompanhamento.contains(oldStatusPeriodoAcompanhamento))
				this.statusPeriodoAcompanhamento.remove(oldStatusPeriodoAcompanhamento);
	}

	/** @pdGenerated default removeAll */
	public void removeAllStatusPeriodoAcompanhamento() {
		if (statusPeriodoAcompanhamento != null)
			statusPeriodoAcompanhamento.clear();
	}

	public Long getCodCor() {
		return codCor;
	}

	public void setCodCor(Long codCor) {
		this.codCor = codCor;
	}

	public String getNomeCor() {
		return nomeCor;
	}

	public void setNomeCor(String nomeCor) {
		this.nomeCor = nomeCor;
	}

	public int getCiclo() {
		return ciclo;
	}

	public void setCiclo(int ciclo) {
		this.ciclo = ciclo;
	}

	public List<IndicadorMonitoramentoDto> getIndMonitoramento() {
		if (indMonitoramento == null)
			indMonitoramento = new ArrayList<IndicadorMonitoramentoDto>();
		return indMonitoramento;
	}

	public void setIndMonitoramento(List<IndicadorMonitoramentoDto> indMonitoramento) {
		this.indMonitoramento = indMonitoramento;
	}

	public Long getCodArel() {
		return codArel;
	}

	public void setCodArel(Long codArel) {
		this.codArel = codArel;
	}

	@Override
	public String toString() {
		return "PeriodoAcompanhamento [codigo=" + codigo + ", parecer=" + parecer + ", mes=" + mes + ", ano=" + ano + ", codCor=" + codCor + ", nomeCor=" + nomeCor + ", ciclo=" + ciclo
				+ ", statusPeriodoAcompanhamento=" + statusPeriodoAcompanhamento + "]";
	}

}