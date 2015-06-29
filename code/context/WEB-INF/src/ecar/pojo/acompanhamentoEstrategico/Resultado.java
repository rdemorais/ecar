/***********************************************************************
 * Module:  Resultado.java
 * Author:  leonardo.marques
 * Purpose: Defines the Class Resultado
 ***********************************************************************/

package ecar.pojo.acompanhamentoEstrategico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import ecar.sinalizacao.Sinalizacao;
import ecar.util.GanttUtil;

/** @pdOid 6ce35f00-0a36-409c-98a7-d1aeb076e098 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resultado implements Comparable<Resultado>, Serializable {

	private static final long serialVersionUID = 1L;

	private long codigo;

	private String nome;

	@XmlTransient
	private long codEstrat;
	
	private String prioritario;
	
	private boolean atencao;
	

	public List<PeriodoAcompanhamento> periodoAcompanhamento = new ArrayList<PeriodoAcompanhamento>();
	public Responsavel responsavel;
	public List<Responsavel> coResponsavel;
	
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
	@XmlTransient
	public String nomeEOrgaoResponsavel = "";	
	
	public List<Responsavel> articulacao;
	public List<Produto> produtos;
	public List<Indicador> indicador = new ArrayList<Indicador>();

	// propriedades para montar Resultado a paritr da conulta listarResultado.
	private Long codAri;
	private String usu;
	private String usuP;
	private Long codCor;
	private String nomeCor;
	private Long codCorP;
	private String nomeCorP;
	private Long codAref;
	private Long codArefP;
	private String mes;
	private String ano;
	private String mesP;
	private String anoP;
	private String parecer = "";
	private String parecerP;
	private Date dataI;
	private Date dataT;
	private Long codResul;
	private String nomeResul;
	private Long codProd;
	private String produto;
	private String siglaResultado;
	private Long codResponsavel;
	private String siglaProduto;
	private Long codRespProduto;
	private Long codAriP;
	private Date dataInicio;
	private Date dataFim;
	
	private Long codAcao;
	private String acao;
	private String siglaAcao;
	private Date dataInicioAcao;
	private Date dataFimAcao;
	private Long codAriA;
	private Long codRespAcao;
	private String responsavelAcao;
	private Long codCorA;
	private String nomeCorA;
	private Long codArefA;	
	private String mesA;
	private String anoA;		
	private String parecerA;
	private String ativoProd;
	private String ativoAcao;
	private String orgao;
	private String situacao;
	private String conceituacao;
	private String interpretacao;
	private String metodoCalculo;
	private Sinalizacao sinalizacao;

	public Resultado() {
		
//		coResponsavel = new ArrayList<Responsavel>();
//		coResponsavel.add(new Responsavel());
//		
//		articulacao = new ArrayList<Responsavel>();
//		articulacao.add(new Responsavel());
		setSinalizacao(new Sinalizacao());

	}	

	public Resultado(long codigo) {
		this.codigo = codigo;
	}

	public Resultado(long codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public Resultado(Long codigo, String nome, Long codEstrat, Long codAri,
			String parecer, String mes, String ano, Long codCor, String nomeCor,
			String prioritario, String sigla, String responsavel, Long codResponsavel, 
			Date dataInicio, Date dataFim, Boolean atencao, String orgao) {
		this.codigo = codigo;
		this.nome = nome;
		this.codEstrat = codEstrat;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.atencao = atencao;
		
		PeriodoAcompanhamento pa = new PeriodoAcompanhamento();
		if(codAri != null){
			pa = new PeriodoAcompanhamento(codAri, parecer,
					mes, ano, codCor, nomeCor, 0L);			
		}
		
		this.periodoAcompanhamento = new ArrayList<PeriodoAcompanhamento>();
		this.periodoAcompanhamento.add(pa);
		
		this.prioritario = "N";
		if(prioritario != null) {
			this.prioritario = "S";
		}
		
		this.siglaResultado = sigla;
		
		Responsavel r = new Responsavel();
//		Responsavel cr = new Responsavel();
		
		this.responsavel = r;
//		coResponsavel = new ArrayList<Responsavel>();
//		this.coResponsavel.add(cr);
		
		if(codResponsavel != null) {		
			r.setCodigo(codResponsavel);
			r.setNome(responsavel);		
			r.orgao.setNome(orgao);
			this.responsavel = r;
			
			//TODO Esta utilizando o Co Responsável FAKE, substituir depois pelo correto.			
//			cr.setCodigo(1);
//			cr.setNome("SAS");			
//			this.coResponsavel.get(0).setCodigo(1);
//			this.coResponsavel.get(0).setNome("SAS");
		}
	}

	public Resultado(Long codAri, String usu, String usuP, Long codCor, String nomeCor,
			Long codCorP, String nomeCorP, Long codAref, Long codArefP,
			String mes, String ano, String mesP, String anoP, String parecer,
			String parecerP, Date dataI, Date dataT, Long codResul,
			String nomeResul, Long codProd, String produto) {
		this.codAri = codAri;
		this.usu = usu;
		this.usuP = usuP;
		this.codCor = codCor;
		this.nomeCor = nomeCor;
		this.codCorP = codCorP;
		this.nomeCorP = nomeCorP;
		this.codAref = codAref;
		this.codArefP = codArefP;
		this.mes = mes;
		this.ano = ano;
		this.mesP = mesP;
		this.anoP = anoP;
		if(parecer != null && !parecer.equals("")) {
			this.parecer = parecer;			
		}
		this.parecerP = parecerP;
		this.dataI = dataI;
		this.dataT = dataT;
		this.codResul = codResul;
		this.nomeResul = nomeResul;
		this.codProd = codProd;
		this.produto = produto;
	}

	public boolean isAtencao() {
		return atencao;
	}

	public void setAtencao(Boolean atencao) {
		if(atencao == null) {
			this.atencao = false;
		}else{
			this.atencao = atencao;	
		}	
	}

	/** @pdOid e144d989-f091-4b05-a2e2-6b6d2c7f8f5e */
	public long getCodigo() {
		return codigo;
	}

	/**
	 * @param newCodigo
	 * @pdOid da9be4d6-caf1-4c76-8fc7-57f6d66d88ee
	 */
	public void setCodigo(long newCodigo) {
		codigo = newCodigo;
	}

	/** @pdOid b8731c11-f65f-480a-ab30-7370bfa171a8 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param newNome
	 * @pdOid 1bd51faf-93a2-4435-b5ee-6b9da8865507
	 */
	public void setNome(String newNome) {
		nome = newNome;
	}

	/** @pdGenerated default getter */
	public List<Responsavel> getArticulacao() {
		if (articulacao == null)
			articulacao = new java.util.ArrayList<Responsavel>();
		return articulacao;
	}

	/** @pdGenerated default iterator getter */
	@SuppressWarnings("rawtypes")
	public Iterator getIteratorArticulacao() {
		if (articulacao == null)
			articulacao = new java.util.ArrayList<Responsavel>();
		return articulacao.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newArticulacao
	 */
	@SuppressWarnings("rawtypes")
	public void setArticulacao(List<Responsavel> newArticulacao) {
		removeAllArticulacao();
		for (Iterator iter = newArticulacao.iterator(); iter.hasNext();)
			addArticulacao((Responsavel) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newResponsavel
	 */
	public void addArticulacao(Responsavel newResponsavel) {
		if (newResponsavel == null)
			return;
		if (this.articulacao == null)
			this.articulacao = new java.util.ArrayList<Responsavel>();
		if (!this.articulacao.contains(newResponsavel))
			this.articulacao.add(newResponsavel);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldResponsavel
	 */
	public void removeArticulacao(Responsavel oldResponsavel) {
		if (oldResponsavel == null)
			return;
		if (this.articulacao != null)
			if (this.articulacao.contains(oldResponsavel))
				this.articulacao.remove(oldResponsavel);
	}

	/** @pdGenerated default removeAll */
	public void removeAllArticulacao() {
		if (articulacao != null)
			articulacao.clear();
	}

	/** @pdGenerated default getter */
	public List<Produto> getProdutos() {
		if (produtos == null)
			produtos = new java.util.ArrayList<Produto>();
		return produtos;
	}

	/** @pdGenerated default iterator getter */
	@SuppressWarnings("rawtypes")
	public Iterator getIteratorProdutos() {
		if (produtos == null)
			produtos = new java.util.ArrayList<Produto>();
		return produtos.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newProdutos
	 */
	@SuppressWarnings("rawtypes")
	public void setProdutos(List<Produto> newProdutos) {
		removeAllProdutos();
		for (Iterator iter = newProdutos.iterator(); iter.hasNext();)
			addProdutos((Produto) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newProduto
	 */
	public void addProdutos(Produto newProduto) {
		if (newProduto == null)
			return;
		if (this.produtos == null)
			this.produtos = new java.util.ArrayList<Produto>();
		if (!this.produtos.contains(newProduto))
			this.produtos.add(newProduto);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldProduto
	 */
	public void removeProdutos(Produto oldProduto) {
		if (oldProduto == null)
			return;
		if (this.produtos != null)
			if (this.produtos.contains(oldProduto))
				this.produtos.remove(oldProduto);
	}

	/** @pdGenerated default removeAll */
	public void removeAllProdutos() {
		if (produtos != null)
			produtos.clear();
	}

	public long getCodEstrat() {
		return codEstrat;
	}

	public void setCodEstrat(long codEstrat) {
		this.codEstrat = codEstrat;
	}

	public List<PeriodoAcompanhamento> getPeriodoAcompanhamento() {
		if (periodoAcompanhamento == null)
			periodoAcompanhamento = new ArrayList<PeriodoAcompanhamento>();
		return periodoAcompanhamento;
	}

	public void setPeriodoAcompanhamento(
			List<PeriodoAcompanhamento> periodoAcompanhamento) {
		this.periodoAcompanhamento = periodoAcompanhamento;
	}
	
	public void addPeriodoAcompanhamento(PeriodoAcompanhamento pa){
		
		this.periodoAcompanhamento.add(pa);
	}

	public List<Indicador> getIndicador() {
		if (indicador == null)
			indicador = new ArrayList<Indicador>();
		return indicador;
	}

	public void setIndicador(List<Indicador> indicador) {
		this.indicador = indicador;
	}

	public String getUsu() {
		return usu;
	}

	public void setUsu(String usu) {
		this.usu = "";
		if(usu != null){
			this.usu = usu;	
		}		
	}

	public String getUsuP() {
		return usuP;
	}

	public void setUsuP(String usuP) {
		this.usuP = usuP;
	}

	public Long getCodCor() {
		return codCor;
	}

	public void setCodCor(Long codCor) {
		this.codCor = 0L;
		if(codCor != null) {
			this.codCor = codCor;
		}
	}

	public String getNomeCor() {
		return nomeCor;
	}

	public void setNomeCor(String nomeCor) {
		this.nomeCor = "";
		if(nomeCor != null) {
			this.nomeCor = nomeCor;
		}		
	}

	public Long getCodCorP() {
		return codCorP;
	}

	public void setCodCorP(Long codCorP) {
		this.codCorP = codCorP;
	}

	public String getNomeCorP() {
		return nomeCorP;
	}

	public void setNomeCorP(String nomeCorP) {
		this.nomeCorP = nomeCorP;
	}

	public Long getCodAref() {
		return codAref;
	}

	public void setCodAref(Long codAref) {
		this.codAref = codAref;
	}

	public Long getCodArefP() {
		return codArefP;
	}

	public void setCodArefP(Long codArefP) {
		this.codArefP = codArefP;
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

	public String getMesP() {
		return mesP;
	}

	public void setMesP(String mesP) {
		this.mesP = mesP;
	}

	public String getAnoP() {
		return anoP;
	}

	public void setAnoP(String anoP) {
		this.anoP = anoP;
	}

	public String getParecer() {
		return parecer;
	}

	public void setParecer(String parecer) {
		this.parecer = parecer;
	}

	public String getParecerP() {
		return parecerP;
	}

	public void setParecerP(String parecerP) {
		this.parecerP = parecerP;
	}

	public Date getDataI() {
		return dataI;
	}

	public void setDataI(Date dataI) {
		this.dataI = dataI;
	}

	public Date getDataT() {
		return dataT;
	}

	public void setDataT(Date dataT) {
		this.dataT = dataT;
	}

	public Long getCodResul() {
		return codResul;
	}

	public void setCodResul(Long codResul) {
		this.codResul = codResul;
	}

	public String getNomeResul() {
		return nomeResul;
	}

	public void setNomeResul(String nomeResul) {
		this.nomeResul = nomeResul;
	}

	public Long getCodProd() {
		return codProd;
	}

	public void setCodProd(Long codProd) {
		this.codProd = codProd;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public Long getCodAri() {
		return codAri;
	}

	public void setCodAri(Long codAri) {
		this.codAri = codAri;
	}

	public String getNomeEOrgaoResponsavel() {
		if (this.responsavel == null){
			return "Objeto Resultado.responsavel � nulo.";
		}else if (this.responsavel.getNomeEOrgao() == null){
			return "String Resultado.responsavel.getNomeEOrgao() � nulo.";
		}else {
			return this.responsavel.getNomeEOrgao();
		}
	}

	public void setNomeEOrgaoResponsavel(String nomeEOrgaoResponsavel) {
		this.responsavel.setNomeEOrgao(nomeEOrgaoResponsavel);
	}

	public String getPrioritario() {
		return prioritario;
	}

	public void setPrioritario(String prioritario) {
		this.prioritario = prioritario;
	}

	public String getSiglaResultado() {
		return siglaResultado;
	}

	public void setSiglaResultado(String siglaResultado) {
		this.siglaResultado = siglaResultado;
	}

	public Long getCodResponsavel() {
		return codResponsavel;
	}

	public void setCodResponsavel(Long codResponsavel) {
		this.codResponsavel = 0L;
		if(codResponsavel != null){
			this.codResponsavel = codResponsavel;	
		}		
	}

	public String getSiglaProduto() {
		return siglaProduto;
	}

	public void setSiglaProduto(String siglaProduto) {
		this.siglaProduto = siglaProduto;
	}

	public Long getCodRespProduto() {
		return codRespProduto;
	}

	public void setCodRespProduto(Long codRespProduto) {
		this.codRespProduto = codRespProduto;
	}

	public Long getCodAriP() {
		return codAriP;
	}

	public void setCodAriP(Long codAriP) {
		this.codAriP = codAriP;
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

	public List<Responsavel> getCoResponsavel() {
		if(coResponsavel == null){
			coResponsavel = new ArrayList<Responsavel>();
		}
		return coResponsavel;
	}

	public void setCoResponsavel(List<Responsavel> coResponsavel) {
		this.coResponsavel = coResponsavel;
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

	public String getNomeCorA() {
		return nomeCorA;
	}

	public void setNomeCorA(String nomeCorA) {
		this.nomeCorA = nomeCorA;
	}

	public Long getCodArefA() {
		return codArefA;
	}

	public void setCodArefA(Long codArefA) {
		this.codArefA = codArefA;
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

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}
	
	public String getResponsavelNome() {
		return this.responsavel.getNome();
	}
	
	public String getResponsavelOrgao() {
		return this.responsavel.getOrgao().getNome();
	}
	
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	
	public String getAtivoProd() {
		return ativoProd;
	}

	public void setAtivoProd(String ativoProd) {
		this.ativoProd = ativoProd;
	}

	public String getAtivoAcao() {
		return ativoAcao;
	}

	public void setAtivoAcao(String ativoAcao) {
		this.ativoAcao = ativoAcao;
	}

	
	public String getOrgao() {
		return orgao;
	}

	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}
	
	public String getConceituacao() {
		return conceituacao;
	}

	public void setConceituacao(String conceituacao) {
		this.conceituacao = conceituacao;
	}

	public String getInterpretacao() {
		return interpretacao;
	}

	public void setInterpretacao(String interpretacao) {
		this.interpretacao = interpretacao;
	}

	public String getMetodoCalculo() {
		return metodoCalculo;
	}

	public void setMetodoCalculo(String metodoCalculo) {
		this.metodoCalculo = metodoCalculo;
	}

	public Sinalizacao getSinalizacao() {
		return sinalizacao;
	}

	public void setSinalizacao(Sinalizacao sinalizacao) {
		this.sinalizacao = sinalizacao;
	}

	public List<Integer> getListaMeses(){
		List<Integer> listaMeses = new ArrayList<Integer>();
		if(dataInicio != null && dataFim != null){
			listaMeses = GanttUtil.formataGantt(dataInicio, dataFim);
		}
		
		return listaMeses;
	}
				
	public String getCaminhoImagemStatus(){
		String caminho = null;
		Long codCor = null;
		if (periodoAcompanhamento != null && periodoAcompanhamento.size() >= 1  && periodoAcompanhamento.get(0).statusPeriodoAcompanhamento != null) {
			codCor = periodoAcompanhamento.get(0).statusPeriodoAcompanhamento.get(0).getCodigo();
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
	
	public String getParecerCicloAtual() {
		String parecer = "PARECER NAO INFORMADO";
		try {
			parecer = periodoAcompanhamento.get(0).getParecer();
		} catch (Exception e) {
			e.printStackTrace();
			}
		return parecer;
	}
	
	public String getMesAnoCicloAtual() {
		String mesAno = "";
		try {
			if (periodoAcompanhamento != null && periodoAcompanhamento.size() >= 1 && periodoAcompanhamento.get(0).getMes() != null && periodoAcompanhamento.get(0).getAno() > 0) {
				mesAno = periodoAcompanhamento.get(0).getMes() + "/" + periodoAcompanhamento.get(0).getAno();
			}
		} catch (Exception e) {
			e.printStackTrace();
			}
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
	
	public int compareTo(Resultado r) {
		if (this.siglaResultado.compareTo(r.getSiglaResultado()) < 0) {
            return -1;
        }
        if (this.siglaResultado.compareTo(r.getSiglaResultado()) > 0) {
            return 1;
        }
		return 0;
	}
	
	

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	@Override
	public String toString() {
		return "Resultado [codigo=" + codigo + ", nome=" + nome + ", produtos="
				+ produtos + "]";
	}
	
//	@Override
//	public String toString() {
//		return "RESULTADO ["+nome+", "+prioritario+"]";
//	}
	
	
	
	
}