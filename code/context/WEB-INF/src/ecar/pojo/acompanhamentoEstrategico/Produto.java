/***********************************************************************
 * Module:  Produto.java
 * Author:  leonardo.marques
 * Purpose: Defines the Class Produto
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

/** @pdOid 4af4b399-f740-42aa-9783-f81d7f611e80 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Produto implements Comparable<Produto> {

	private Long codigo;

	private String nome;

	private Date dataInicio;

	private Date dataFim;

	@XmlTransient
	private long codResultado;

	public List<PeriodoAcompanhamento> periodoAcompanhamento = new ArrayList<PeriodoAcompanhamento>();

	public Responsavel responsavel;

	public List<Responsavel> coResponsavel;

	public List<Acao> acoes;

	private String siglaProduto;

	private Long codAri;
	private String usu;
	private Long codCor;
	private String nomeCor;
	private String mes;
	private String ano;
	private String parecer;
	private Long codResponsavel;
	private Long codAref;

	private Long codAcao;
	private String acao;
	private String siglaAcao;
	private Date dataInicioAcao;
	private Date dataFimAcao;
	private Long codAriA;
	private Long codRespAcao;
	private String responsavelAcao;
	private Long codCorA;
	private Long codArefA;
	private String nomeCorA;
	private String mesA;
	private String anoA;
	private String parecerA;
	
	private Long codAtividade;
	private String atividade;
	private String siglaAtividade;
	private Date dataInicioAtividade;
	private Date dataFimAtividade;
	private Long codAriAt;
	private Long codRespAtividade;
	private String responsavelAtividade;
	private Long codCorAt;
	private Long codArefAt;
	private String nomeCorAt;
	private String mesAt;
	private String anoAt;
	private String parecerAt;
	private String ativoAcao;
	private String ativoAtiv;
	private String orgao;
	private String situacao;
	
	public Produto() {
//		coResponsavel = new ArrayList<Responsavel>();
//		coResponsavel.add(new Responsavel());

	}

	public Produto(Long codigo) {
		this.codigo = codigo;
	}

	public Produto(Long codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public Produto(Long codigo, String nome, long codResultado, String sigla) {
		this.codigo = codigo;
		this.nome = nome;
		this.codResultado = codResultado;
		this.siglaProduto = sigla;
	}

	public Produto(Long codigo, String nome, Long codResultado, String sigla,
			String responsavel, Long codResponsavel, Long codAri,
			String parecer, String mes, String ano, Long codCor,
			String nomeCor, Date dataInicio, Date dataFim, String orgao) {
		this.codigo = codigo;
		this.nome = nome;
		this.codResultado = codResultado;
		this.siglaProduto = sigla;

		PeriodoAcompanhamento pa = new PeriodoAcompanhamento();
//		pa.setMes(mes);
//		pa.setAno(Integer.parseInt(ano));
		
		if (parecer != null) {
			pa = new PeriodoAcompanhamento(codAri,
					parecer, mes, ano, codCor, nomeCor, 0L);			
		}
		
		this.periodoAcompanhamento = new ArrayList<PeriodoAcompanhamento>();
		this.periodoAcompanhamento.add(pa);

		Responsavel r = new Responsavel();
		if (codResponsavel != null) {
			r.setCodigo(codResponsavel);
			r.setNome(responsavel);
			r.orgao.setNome(orgao);
			
			this.responsavel = r;

			// TODO Esta utilizando o Co Responsável FAKE, substituir depois
			// pelo correto.
			// Responsavel cr = new Responsavel();
			// r.setCodigo(0);
			// r.setNome("SAS");
			// this.coResponsavel = cr;
		}

		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}

	/** @pdOid e881c1b3-b584-49a2-9af2-5cd2474b23be */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param newCodigo
	 * @pdOid 4daac741-f042-4dcb-808a-24b5d618f895
	 */
	public void setCodigo(Long newCodigo) {
		codigo = newCodigo;
	}

	/** @pdOid fd6dc6f0-8d2a-46e9-8453-9116a46651bb */
	public String getNome() {
		return nome;
	}

	/**
	 * @param newNome
	 * @pdOid 2a5c78fd-03f9-441b-acd5-c52caa84d98a
	 */
	public void setNome(String newNome) {
		nome = newNome;
	}

	/** @pdOid 5b4d5db4-cef2-4edd-8ae0-7b6d581fe155 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param newDataInicio
	 * @pdOid fca4d8e5-08c1-4036-8bc7-fc2bcee0770a
	 */
	public void setDataInicio(Date newDataInicio) {
		dataInicio = newDataInicio;
	}

	/** @pdOid 96f199f8-41b5-4b35-8f85-c49d9c847247 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * @param newDataFim
	 * @pdOid 6a1cda7a-1c50-4f8d-b96b-374c5b3c2fc9
	 */
	public void setDataFim(Date newDataFim) {
		dataFim = newDataFim;
	}

	/** @pdGenerated default getter */
	public java.util.List<PeriodoAcompanhamento> getPeriodoAcompanhamento() {
		if (periodoAcompanhamento == null)
			periodoAcompanhamento = new java.util.ArrayList<PeriodoAcompanhamento>();
		return periodoAcompanhamento;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator getIteratorPeriodoAcompanhamento() {
		if (periodoAcompanhamento == null)
			periodoAcompanhamento = new java.util.ArrayList<PeriodoAcompanhamento>();
		return periodoAcompanhamento.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newPeriodosAcompanhamento
	 */
	public void setPeriodosAcompanhamento(
			java.util.List<PeriodoAcompanhamento> newPeriodosAcompanhamento) {
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

	/** @pdGenerated default getter */
	public java.util.List<Acao> getAcoes() {
		if (acoes == null)
			acoes = new java.util.ArrayList<Acao>();
		return acoes;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator getIteratorAcoes() {
		if (acoes == null)
			acoes = new java.util.ArrayList<Acao>();
		return acoes.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newAcoes
	 */
	public void setAcoes(java.util.List<Acao> newAcoes) {
		removeAllAcoes();
		for (java.util.Iterator iter = newAcoes.iterator(); iter.hasNext();)
			addAcoes((Acao) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newAcao
	 */
	public void addAcoes(Acao newAcao) {
		if (newAcao == null)
			return;
		if (this.acoes == null)
			this.acoes = new java.util.ArrayList<Acao>();
		if (!this.acoes.contains(newAcao))
			this.acoes.add(newAcao);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldAcao
	 */
	public void removeAcoes(Acao oldAcao) {
		if (oldAcao == null)
			return;
		if (this.acoes != null)
			if (this.acoes.contains(oldAcao))
				this.acoes.remove(oldAcao);
	}

	/** @pdGenerated default removeAll */
	public void removeAllAcoes() {
		if (acoes != null)
			acoes.clear();
	}

	public long getCodResultado() {
		return codResultado;
	}

	public void setCodResultado(long codResultado) {
		this.codResultado = codResultado;
	}

	public String getSiglaProduto() {
		return siglaProduto;
	}

	public void setSiglaProduto(String siglaProduto) {
		this.siglaProduto = siglaProduto;
	}

	public Long getCodAri() {
		return codAri;
	}

	public void setCodAri(Long codAri) {
		this.codAri = codAri;
	}

	public String getUsu() {
		return usu;
	}

	public void setUsu(String usu) {
		this.usu = "";
		if (usu != null) {
			this.usu = usu;
		}
	}

	public Long getCodCor() {
		return codCor;
	}

	public void setCodCor(Long codCor) {
		this.codCor = 0L;
		if (codCor != null) {
			this.codCor = codCor;
		}
	}

	public String getNomeCor() {
		return nomeCor;
	}

	public void setNomeCor(String nomeCor) {
		this.nomeCor = "";
		if (nomeCor != null) {
			this.nomeCor = nomeCor;
		}
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getParecer() {
		return parecer;
	}

	public void setParecer(String parecer) {
		this.parecer = parecer;
	}

	public Long getCodResponsavel() {
		return codResponsavel;
	}

	public void setCodResponsavel(Long codResponsavel) {
		this.codResponsavel = 0L;
		if (codResponsavel != null) {
			this.codResponsavel = codResponsavel;
		}
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public List<Responsavel> getCoResponsavel() {
		if (coResponsavel == null) {
			coResponsavel = new ArrayList<Responsavel>();
		}
		return coResponsavel;
	}

	public void setCoResponsavel(List<Responsavel> coResponsavel) {
		this.coResponsavel = coResponsavel;
	}

	public void setPeriodoAcompanhamento(
			List<PeriodoAcompanhamento> periodoAcompanhamento) {
		this.periodoAcompanhamento = periodoAcompanhamento;
	}

	public List<Integer> getListaMeses() {
		List<Integer> listaMeses = new ArrayList<Integer>();
		if (dataInicio != null && dataFim != null) {
			listaMeses = GanttUtil.formataGantt(dataInicio, dataFim);
		}

		return listaMeses;
	}

	public String getCaminhoImagemStatus() {
		String caminho = null;
		Long codCor = null;
		try {
			codCor = periodoAcompanhamento.get(0).statusPeriodoAcompanhamento
					.get(0).getCodigo();
		} catch (Exception e) {
		}
		if (codCor != null) {
			switch (codCor.intValue()) {
			case 1:
				caminho = Produto.class
						.getResource("/images/" + "/sVerde9.png").getPath();
				break;
			case 2:
				caminho = Produto.class.getResource(
						"/images/" + "/sAmarelo9.png").getPath();
				break;
			case 3:
				caminho = Produto.class.getResource(
						"/images/" + "/sVermelho9.png").getPath();
				break;
			case 10:
				caminho = Produto.class.getResource("/images/" + "/sAzul9.png")
						.getPath();
				break;
			case 11:
				caminho = Produto.class
						.getResource("/images/" + "/sCinza9.png").getPath();
				break;
			default:
				caminho = Produto.class.getResource(
						"/images/" + "/sBranco9.png").getPath();
				break;
			}
		} else {
			caminho = Produto.class.getResource("/images/" + "/sBranco9.png")
					.getPath();
		}

		return caminho;
	}

	public Long getCodAref() {
		return codAref;
	}

	public void setCodAref(Long codAref) {
		this.codAref = codAref;
	}

	public Long getCodAcao() {
		return codAcao;
	}

	public void setCodAcao(Long codAcao) {
		this.codAcao = codAcao;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public String getSiglaAcao() {
		return siglaAcao;
	}

	public void setSiglaAcao(String siglaAcao) {
		this.siglaAcao = siglaAcao;
	}

	public Date getDataInicioAcao() {
		return dataInicioAcao;
	}

	public void setDataInicioAcao(Date dataInicioAcao) {
		this.dataInicioAcao = dataInicioAcao;
	}

	public Date getDataFimAcao() {
		return dataFimAcao;
	}

	public void setDataFimAcao(Date dataFimAcao) {
		this.dataFimAcao = dataFimAcao;
	}

	public Long getCodAriA() {
		return codAriA;
	}

	public void setCodAriA(Long codAriA) {
		this.codAriA = codAriA;
	}

	public Long getCodRespAcao() {
		return codRespAcao;
	}

	public void setCodRespAcao(Long codRespAcao) {
		this.codRespAcao = codRespAcao;
	}

	public String getResponsavelAcao() {
		return responsavelAcao;
	}

	public void setResponsavelAcao(String responsavelAcao) {
		this.responsavelAcao = responsavelAcao;
	}

	public Long getCodCorA() {
		return codCorA;
	}

	public void setCodCorA(Long codCorA) {
		this.codCorA = codCorA;
	}

	public Long getCodArefA() {
		return codArefA;
	}

	public void setCodArefA(Long codArefA) {
		this.codArefA = codArefA;
	}

	public String getNomeCorA() {
		return nomeCorA;
	}

	public void setNomeCorA(String nomeCorA) {
		this.nomeCorA = nomeCorA;
	}

	public String getMesA() {
		return mesA;
	}

	public void setMesA(String mesA) {
		this.mesA = mesA;
	}

	public String getAnoA() {
		return anoA;
	}

	public void setAnoA(String anoA) {
		this.anoA = anoA;
	}

	public String getParecerA() {
		return parecerA;
	}

	public void setParecerA(String parecerA) {
		this.parecerA = parecerA;
	}

	public Long getCodAtividade() {
		return codAtividade;
	}

	public void setCodAtividade(Long codAtividade) {
		this.codAtividade = codAtividade;
	}

	public String getAtividade() {
		return atividade;
	}

	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}

	public String getSiglaAtividade() {
		return siglaAtividade;
	}

	public void setSiglaAtividade(String siglaAtividade) {
		this.siglaAtividade = siglaAtividade;
	}

	public Date getDataInicioAtividade() {
		return dataInicioAtividade;
	}

	public void setDataInicioAtividade(Date dataInicioAtividade) {
		this.dataInicioAtividade = dataInicioAtividade;
	}

	public Date getDataFimAtividade() {
		return dataFimAtividade;
	}

	public void setDataFimAtividade(Date dataFimAtividade) {
		this.dataFimAtividade = dataFimAtividade;
	}

	public Long getCodAriAt() {
		return codAriAt;
	}

	public void setCodAriAt(Long codAriAt) {
		this.codAriAt = codAriAt;
	}

	public Long getCodRespAtividade() {
		return codRespAtividade;
	}

	public void setCodRespAtividade(Long codRespAtividade) {
		this.codRespAtividade = codRespAtividade;
	}

	public String getResponsavelAtividade() {
		return responsavelAtividade;
	}

	public void setResponsavelAtividade(String responsavelAtividade) {
		this.responsavelAtividade = responsavelAtividade;
	}

	public Long getCodCorAt() {
		return codCorAt;
	}

	public void setCodCorAt(Long codCorAt) {
		this.codCorAt = codCorAt;
	}

	public Long getCodArefAt() {
		return codArefAt;
	}

	public void setCodArefAt(Long codArefAt) {
		this.codArefAt = codArefAt;
	}

	public String getNomeCorAt() {
		return nomeCorAt;
	}

	public void setNomeCorAt(String nomeCorAt) {
		this.nomeCorAt = nomeCorAt;
	}

	public String getMesAt() {
		return mesAt;
	}

	public void setMesAt(String mesAt) {
		this.mesAt = mesAt;
	}

	public String getAnoAt() {
		return anoAt;
	}

	public void setAnoAt(String anoAt) {
		this.anoAt = anoAt;
	}

	public String getParecerAt() {
		return parecerAt;
	}

	public void setParecerAt(String parecerAt) {
		this.parecerAt = parecerAt;
	}

	
	public String getAtivoAcao() {
		return ativoAcao;
	}

	public void setAtivoAcao(String ativoAcao) {
		this.ativoAcao = ativoAcao;
	}

	public String getAtivoAtiv() {
		return ativoAtiv;
	}

	public void setAtivoAtiv(String ativoAtiv) {
		this.ativoAtiv = ativoAtiv;
	}

	
	public String getOrgao() {
		return orgao;
	}

	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}

	public String getParecerCicloAtual() {
		String parecer = "PARECER NAO INFORMADO";
		try {
			parecer = periodoAcompanhamento.get(0).getParecer();
		} catch (Exception e) {}
		return parecer;
	}
	
	public String getMesAnoCicloAtual() {
		String mesAno = "";
		try {
			mesAno = periodoAcompanhamento.get(0).getMes() + "/" + periodoAcompanhamento.get(0).getAno();
		} catch (Exception e) {}
		return mesAno;
	}
	
	public String getCaminhoImagemStatusPenultimo(){
		String caminho = null;
		//Long codCor = periodoAcompanhamento.get(0).statusPeriodoAcompanhamento.get(0).getCodigo();
		Long codCor = null;
		if (periodoAcompanhamento != null && periodoAcompanhamento.size() >= 2  && periodoAcompanhamento.get(1).statusPeriodoAcompanhamento != null) {
			codCor = periodoAcompanhamento.get(1).statusPeriodoAcompanhamento.get(0).getCodigo();
		}
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
		
		return caminho;
	}
	
	public String getMesAnoCicloPenultimo() {
		String mesAno = "";
		try {
			if (periodoAcompanhamento != null && periodoAcompanhamento.size() >= 2 ) {
				mesAno = periodoAcompanhamento.get(1).getMes() + "/" + periodoAcompanhamento.get(1).getAno();
			}
		} catch (Exception e) {
			e.printStackTrace();
			}
		return mesAno;
	}
		
	public String getCaminhoImagemStatusAntePenultimo(){
		String caminho = null;
		//Long codCor = periodoAcompanhamento.get(0).statusPeriodoAcompanhamento.get(0).getCodigo();
		Long codCor = null;
		if (periodoAcompanhamento != null && periodoAcompanhamento.size() >= 3  && periodoAcompanhamento.get(2).statusPeriodoAcompanhamento != null) {
			codCor = periodoAcompanhamento.get(2).statusPeriodoAcompanhamento.get(0).getCodigo();
		}
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
		
		return caminho;
	}
	
	public String getMesAnoCicloAntePenultimo() {
		String mesAno = "";
		try {
			if (periodoAcompanhamento != null && periodoAcompanhamento.size() >= 3 ) {
				mesAno = periodoAcompanhamento.get(2).getMes() + "/" + periodoAcompanhamento.get(2).getAno();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mesAno;
	}
	
	public int compareTo(Produto p) {
		if (this.siglaProduto.compareTo(p.getSiglaProduto()) < 0) {
            return -1;
        }
        if (this.siglaProduto.compareTo(p.getSiglaProduto()) > 0) {
            return 1;
        }
		return 0;
	}

	public void addPeriodoAcompanhamento(
			PeriodoAcompanhamento periodoAcompanhamento2) {
		// TODO Auto-generated method stub
		
	}
	
	

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	@Override
	public String toString() {
		return "Produto [codigo=" + codigo + ", nome=" + nome
				+ ", periodoAcompanhamento=" + periodoAcompanhamento + "]";
	}


	
	
}