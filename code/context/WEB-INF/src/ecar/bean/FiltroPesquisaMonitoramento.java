package ecar.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ecar.enumerator.TipoRelatorioAcompanhamento;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.OrgaoOrg;
import ecar.servico.RelatorioAcompanhamentoService;

public class FiltroPesquisaMonitoramento implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Integer> codigosObjetivosEstrategicos;
	private List<String> etiquetasSelecionadas;
	private List<Integer> statusSelecionados;
	private List<Long> secretariasSelecionadas;
	private boolean prioritario;
	private TipoRelatorioAcompanhamento tipoRelatorioAcompanhamento;
	private boolean ordenarPorStatus;
	private List<Long> codigosUsuariosPermissao;
	private Long codigoUsuario;
	private List<String> etiquetasSemFonetizacao;
	private Date dataInicial;
	private Date dataFinal;
	private boolean comIndicadores;
	private Long exercicio;
	private boolean somenteREM;
	private Long acompReferencia;

	
	
	public Long getAcompReferencia() {
		return acompReferencia;
	}


	public void setAcompReferencia(Long acompReferencia) {
		this.acompReferencia = acompReferencia;
	}


	public Long getExercicio() {
		return exercicio;
	}
	

	public void setExercicio(Long exercicio) {
		this.exercicio = exercicio;
	}

	public List<Integer> getCodigosObjetivosEstrategicos() {
		if(codigosObjetivosEstrategicos == null){
			codigosObjetivosEstrategicos = new ArrayList<Integer>();
		}
		
		return codigosObjetivosEstrategicos;
	}

	public void setCodigosObjetivosEstrategicos(List<Integer> codigosObjetivosEstrategicos) {
		this.codigosObjetivosEstrategicos = codigosObjetivosEstrategicos;
	}
	
	public List<String> getEtiquetasSelecionadas() {
		if(etiquetasSelecionadas == null){
			etiquetasSelecionadas = new ArrayList<String>();
		}
		
		return etiquetasSelecionadas;
	}
	
	public void setEtiquetasSelecionadas(List<String> etiquetasSelecionadas) {
		this.etiquetasSelecionadas = etiquetasSelecionadas;
	}
	
	public List<Integer> getStatusSelecionados() {
		if(statusSelecionados == null){
			statusSelecionados = new ArrayList<Integer>();
		}
		return statusSelecionados;
	}
	
	public void setStatusSelecionados(List<Integer> statusSelecionados) {
		this.statusSelecionados = statusSelecionados;
	}
	
	public List<Long> getSecretariasSelecionadas() {
		if(secretariasSelecionadas == null){
			secretariasSelecionadas = new ArrayList<Long>();
		}
		return secretariasSelecionadas;
	}
	
	public void setSecretariasSelecionadas(List<Long> secretariasSelecionadas) {
		this.secretariasSelecionadas = secretariasSelecionadas;
	}

	public boolean isPrioritario() {
		return prioritario;
	}

	public void setPrioritario(boolean prioritario) {
		this.prioritario = prioritario;
	}

	public TipoRelatorioAcompanhamento getTipoRelatorioAcompanhamento() {
		return tipoRelatorioAcompanhamento;
	}

	public void setTipoRelatorioAcompanhamento(TipoRelatorioAcompanhamento tipoRelatorioAcompanhamento) {
		this.tipoRelatorioAcompanhamento = tipoRelatorioAcompanhamento;
	}

	public boolean isOrdenarPorStatus() {
		return ordenarPorStatus;
	}

	public void setOrdenarPorStatus(boolean ordenarPorStatus) {
		this.ordenarPorStatus = ordenarPorStatus;
	}
	
	public boolean isComIndicadores() {
		return comIndicadores;
	}

	public void setComIndicadores(boolean comIndicadores) {
		this.comIndicadores = comIndicadores;
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder descricaoFiltro = new StringBuilder();
		descricaoFiltro.append(preencherDescricaoObjetivosEstrategicos());
		descricaoFiltro.append(preencherDescricaoEtiquetas());
		descricaoFiltro.append(preencherDescricaoStatus());
		descricaoFiltro.append(preencherDescricaoResultadoPrioritario());
		descricaoFiltro.append(preencherDescricaoOrdenarPorStatus());
		descricaoFiltro.append(preencherDescricaoSecretarias());
		descricaoFiltro.append(somenteREM?"+ Somente Indicadores REM":"");
		
		return descricaoFiltro.toString();
	}
	
	private String preencherDescricaoSecretarias(){
		StringBuilder descricaoSecretarias = new StringBuilder();
		for(int i = 0; i < secretariasSelecionadas.size(); i++){
			RelatorioAcompanhamentoService relatorioAcompanhamentoService = new RelatorioAcompanhamentoService();
			Long codigoSecretaria = secretariasSelecionadas.get(i);
			
			OrgaoOrg secretaria = relatorioAcompanhamentoService.recuperarOrgaoPorCodigo(codigoSecretaria);

			descricaoSecretarias.append(" " + secretaria.getSiglaOrg());
			if(secretariasSelecionadas.size() > 1 && i < secretariasSelecionadas.size() - 1){
				descricaoSecretarias.append("+ ");
			}
			
		}
		
		return descricaoSecretarias.toString();
	}
	
	private String preencherDescricaoOrdenarPorStatus(){
		if(ordenarPorStatus){
			return " + ORDENAR POR STATUS ";
		}else{
			return "";
		}
	}
	
	private String preencherDescricaoResultadoPrioritario(){
		if(prioritario){
			return " + RESULTADOS PRIORITÁRIOS";
		}else{
			return "";
		}
	}
	
	private String preencherDescricaoStatus(){
		StringBuilder descricaoStatus = new StringBuilder();
		for(Integer status : statusSelecionados){
			switch (status.intValue()) {
			case 1:
				descricaoStatus.append(" + ");
				descricaoStatus.append("SATISFATÓRIOS ");
				break;
			case 2:
				descricaoStatus.append(" + ");
				descricaoStatus.append("ALERTAS ");
				break;
			case 3:
				descricaoStatus.append(" + ");
				descricaoStatus.append("CRÍTICOS ");
				break;
			case 10:
				descricaoStatus.append(" + ");
				descricaoStatus.append("CONCLUÍDOS ");
				break;
			case 11:
				descricaoStatus.append(" + ");
				descricaoStatus.append("CANCELADOS ");
				break;
			default:
				descricaoStatus.append(" + ");
				descricaoStatus.append("NÃO MONITORADO ");
				break;
			}
		}
			
		return descricaoStatus.toString();
	}
	
	private String preencherDescricaoEtiquetas(){
		StringBuilder descricaoEtiquetas = new StringBuilder();
		if(!etiquetasSemFonetizacao.isEmpty()){
			for(String etiquetaSelecionada : etiquetasSemFonetizacao){
				String [] etiquetas = etiquetaSelecionada.split(",");
				for(String etiqueta : etiquetas){
					if(StringUtils.isNotBlank(etiqueta)){
						descricaoEtiquetas.append(" + ");
						descricaoEtiquetas.append(etiqueta.toUpperCase());
					}
				}
			}
		}
			
		return descricaoEtiquetas.toString();
	}
	
	private String preencherDescricaoObjetivosEstrategicos(){
		StringBuilder descricaoObjetivos = new StringBuilder();
		for(int i = 0; i < codigosObjetivosEstrategicos.size(); i++){
			RelatorioAcompanhamentoService relatorioAcompanhamentoService = new RelatorioAcompanhamentoService();
			Integer codigoIett = codigosObjetivosEstrategicos.get(i);
			ItemEstruturaIett itemIett = relatorioAcompanhamentoService.recuperarSiglaIettPorCodigo(new Long(codigoIett));
			descricaoObjetivos.append(itemIett.getSiglaIett());
			if(codigosObjetivosEstrategicos.size() > 1 && i < codigosObjetivosEstrategicos.size() - 1){
				descricaoObjetivos.append(" + ");
			}
			
		}
		
		return descricaoObjetivos.toString();
	}

	public List<Long> getCodigosUsuariosPermissao() {
		return codigosUsuariosPermissao;
	}

	public void setCodigosUsuariosPermissao(List<Long> codigosUsuariosPermissao) {
		this.codigosUsuariosPermissao = codigosUsuariosPermissao;
	}
	
	public Long getCodigoUsuario() {
		return codigoUsuario;
	}
	
	public void setCodigoUsuario(Long codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public List<String> getEtiquetasSemFonetizacao() {
		if(etiquetasSemFonetizacao == null){
			etiquetasSemFonetizacao = new ArrayList<String>();
		}
		return etiquetasSemFonetizacao;
	}

	public void setEtiquetasSemFonetizacao(List<String> etiquetasSemFonetizacao) {
		this.etiquetasSemFonetizacao = etiquetasSemFonetizacao;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}


	public boolean isSomenteREM() {
		return somenteREM;
	}


	public void setSomenteREM(boolean somenteREM) {
		this.somenteREM = somenteREM;
	}
	
	
	
}
