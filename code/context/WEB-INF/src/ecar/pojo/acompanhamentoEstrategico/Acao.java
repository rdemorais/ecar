/***********************************************************************
 * Module:  Acao.java
 * Author:  leonardo.marques
 * Purpose: Defines the Class Acao
 ***********************************************************************/

package ecar.pojo.acompanhamentoEstrategico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import ecar.util.GanttUtil;

/** @pdOid 39d5cf27-749a-4e75-9325-542540ea5722 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Acao implements Comparable<Acao> {

	private long codigo;

	private String nome;

	private Date dataInicio;

	private Date dataFim;
	
	@XmlTransient
	private long codProd;

	public List<PeriodoAcompanhamento> periodoAcompanhamento;

	private String siglaAcao;
	
	public Responsavel responsavel;
	
	private List<Atividade> atividades;
	
	private String situacao;
	
	public Acao() {
		
	}
	
	public Acao(long codigo) {		
		this.codigo = codigo;
	}
	
	public Acao(long codigo, String nome) {		
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public Acao(long codigo, String nome, long codProd, String sigla) {		
		this.codigo = codigo;
		this.nome = nome;
		this.codProd = codProd;
		this.siglaAcao = sigla;
	}
	
	public Acao(Long codigo, String nome, String sigla, 
			String responsavel, Long codResponsavel, Long codAri,
			String parecer, String mes, String ano, Long codCor, String nomeCor,
			Date dataInicio, Date dataFim, Long codProd) {		
		this.codigo = codigo;
		this.nome = nome;		
		this.siglaAcao = sigla;
		this.codProd = codProd;
		
		PeriodoAcompanhamento pa = new PeriodoAcompanhamento();
		pa.setMes(mes);
		pa.setAno(Integer.parseInt(ano));
		
		if(parecer != null){
			pa = new PeriodoAcompanhamento(codAri, parecer,
					mes, ano, codCor, nomeCor, 0L);			
		}		
		
		this.periodoAcompanhamento = new ArrayList<PeriodoAcompanhamento>();
		this.periodoAcompanhamento.add(pa);	
		
		Responsavel r = new Responsavel();
		if(codResponsavel != null) {
			r.setCodigo(codResponsavel);
			r.setNome(responsavel);
			this.responsavel = r;
			
			//TODO Esta utilizando o Co Responsável FAKE, substituir depois pelo correto.
			Responsavel cr = new Responsavel();
			r.setCodigo(0);
			r.setNome("SAS");
//			this.coResponsavel = cr;
		}
		
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public long getCodigo() {
		return codigo;
	}

	/**
	 * @param newCodigo
	 * @pdOid 3f62042f-efc6-4ae5-b226-5fda146f5db0
	 */
	public void setCodigo(long newCodigo) {
		codigo = newCodigo;
	}

	/** @pdOid 77ebdc80-8c0a-4129-8f3c-2ee173c94efe */
	public String getNome() {
		return nome;
	}

	/**
	 * @param newNome
	 * @pdOid d03d0179-72df-4a3c-99e6-029aa0fa7ea9
	 */
	public void setNome(String newNome) {
		nome = newNome;
	}

	/** @pdOid 59cd90e1-2d9a-4304-b22e-548a539a7efb */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param newDataInicio
	 * @pdOid f74a7b08-f3ae-40ac-ac93-b05f920f0922
	 */
	public void setDataInicio(Date newDataInicio) {
		dataInicio = newDataInicio;
	}

	/** @pdOid ac2235ff-7d08-4eda-8ac9-4718f9187e59 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * @param newDataFim
	 * @pdOid 21d70521-4102-4348-a07f-f995c30610bb
	 */
	public void setDataFim(Date newDataFim) {
		dataFim = newDataFim;
	}

	/** @pdGenerated default getter */
	public List<PeriodoAcompanhamento> getPeriodoAcompanhamento() {
		if (periodoAcompanhamento == null)
			periodoAcompanhamento = new java.util.ArrayList<PeriodoAcompanhamento>();
		return periodoAcompanhamento;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator getIteratorPeriodosAcompanhamento() {
		if (periodoAcompanhamento == null)
			periodoAcompanhamento = new java.util.ArrayList<PeriodoAcompanhamento>();
		return periodoAcompanhamento.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newPeriodosAcompanhamento
	 */
	public void setPeriodoAcompanhamento(
			List<PeriodoAcompanhamento> newPeriodosAcompanhamento) {
		removeAllPeriodosAcompanhamento();
		for (java.util.Iterator iter = newPeriodosAcompanhamento.iterator(); iter
				.hasNext();)
			addPeriodosAcompanhamento((PeriodoAcompanhamento) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newPeriodoAcompanhamento
	 */
	public void addPeriodosAcompanhamento(
			PeriodoAcompanhamento newPeriodoAcompanhamento) {
		if (newPeriodoAcompanhamento == null)
			return;
		if (this.periodoAcompanhamento == null)
			this.periodoAcompanhamento = new java.util.ArrayList<PeriodoAcompanhamento>();
		if (!this.periodoAcompanhamento.contains(newPeriodoAcompanhamento))
			this.periodoAcompanhamento.add(newPeriodoAcompanhamento);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldPeriodoAcompanhamento
	 */
	public void removePeriodosAcompanhamento(
			PeriodoAcompanhamento oldPeriodoAcompanhamento) {
		if (oldPeriodoAcompanhamento == null)
			return;
		if (this.periodoAcompanhamento != null)
			if (this.periodoAcompanhamento.contains(oldPeriodoAcompanhamento))
				this.periodoAcompanhamento.remove(oldPeriodoAcompanhamento);
	}

	/** @pdGenerated default removeAll */
	public void removeAllPeriodosAcompanhamento() {
		if (periodoAcompanhamento != null)
			periodoAcompanhamento.clear();
	}

	public long getCodProd() {
		return codProd;
	}

	public void setCodProd(long codProd) {
		this.codProd = codProd;
	}

	public String getSiglaAcao() {
		return siglaAcao;
	}

	public void setSiglaAcao(String siglaAcao) {
		this.siglaAcao = siglaAcao;
	}

	public List<Atividade> getAtividades() {
		if(atividades == null){
			atividades = new ArrayList<Atividade>();
		}
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}
	
	public String getCaminhoImagemStatus(){
		String caminho = null;
		if(periodoAcompanhamento != null && periodoAcompanhamento.size() > 0) {
			Long codCor = periodoAcompanhamento.get(0).statusPeriodoAcompanhamento.get(0).getCodigo();
			if(codCor != null){
				switch (codCor.intValue()) {
				case 1:
					caminho = Resultado.class.getResource("/images/" + "/sVerde9.png").getPath();
					break;
				case 2:
					caminho = Resultado.class.getResource("/images/" + "/sAmarelo9.png").getPath();
					break;
				case 3:
					caminho = Resultado.class.getResource("/images/" + "/sVermelho9.png").getPath();
					break;
				case 10:
					caminho = Resultado.class.getResource("/images/" + "/sAzul9.png").getPath();
					break;
				case 11:
					caminho = Resultado.class.getResource("/images/" + "/sCinza9.png").getPath();
					break;
				default:
					caminho = Resultado.class.getResource("/images/" + "/sBranco9.png").getPath();
					break;
				}
			}else{
				caminho = Resultado.class.getResource("/images/" + "/sBranco9.png").getPath();
			}
		}else {
			caminho = Resultado.class.getResource("/images/" + "/sBranco9.png").getPath();
		}
		return caminho;	
	}
	
	public List<Integer> getListaMeses(){
		List<Integer> listaMeses = new ArrayList<Integer>();
		if(dataInicio != null && dataFim != null){
			listaMeses = GanttUtil.formataGantt(dataInicio, dataFim);
		}
		
		return listaMeses;
	}
	
	public int compareTo(Acao a) {
		if (this.siglaAcao.compareTo(a.getSiglaAcao()) < 0) {
            return -1;
        }
        if (this.siglaAcao.compareTo(a.getSiglaAcao()) > 0) {
            return 1;
        }
		return 0;
	}
	
	public String getMesAnoCicloAtual() {
		String mesAno = "";
		try {
			mesAno = periodoAcompanhamento.get(0).getMes() + "/" + periodoAcompanhamento.get(0).getAno();
		} catch (Exception e) {}
		return mesAno;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	
	
}