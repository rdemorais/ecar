package ecar.util.jasper;
/**
 * 
 * @author Rafael Freitas em 03/05/2010
 *
 */
public enum Report {
	SITUACAO_PRODUTO("SituacaoProd3M.jrxml"),
	SITUACAO_PRODUTO_JASPER("SituacaoProd3M.jasper"),
	SITUACAO_PRODUTO_SR_INDICADOR("SituacaoProd_ind3M.jrxml"),
	SITUACAO_PRODUTO_SR_INDICADOR_JASPER("SituacaoProd_ind3M.jasper"),
	SITUACAO_PRODUTO_SR_CORESP("SituacaoProd_CoRes.jrxml"),
	SITUACAO_PRODUTO_SR_CORESP_JASPER("SituacaoProd_CoRes.jasper"),
	CADERNO_PE("CadernoPE.jrxml"),
	CADERNO_PE_JASPER("CadernoPE.jasper"),
	CADERNO_PE_SR_ESTRATEGIA("CadernoPE_PG2_Est.jrxml"),
	CADERNO_PE_SR_ESTRATEGIA_JASPER("CadernoPE_PG2_Est.jasper"),
	CADERNO_PE_SR_PRODUTO("CadernoPE_PG3_Prod.jrxml"),
	CADERNO_PE_SR_PRODUTO_JASPER("CadernoPE_PG3_Prod.jasper"),
	CADERNO_PE_SR_INDICADOR("CadernoPE_PG4_Ind.jrxml"),
	CADERNO_PE_SR_INDICADOR_JASPER("CadernoPE_PG4_Ind.jasper"),
	CADERNO_PE_SR_INDICADOR_GRAF("CadernoPE_sr_Graf.jrxml"),
	CADERNO_PE_SR_INDICADOR_GRAF_JASPER("CadernoPE_sr_Graf.jasper"),
	SIT_PROD_PARECER("SituacaoProdParecer.jrxml"),
	SIT_PROD_PARECER_JASPER("SituacaoProdParecer.jasper"),
	MONITORAMENTO_CICLO("MonitoramentoCiclo.jrxml"),
	MONITORAMENTO_CICLO_SR_ESTRATEGIA("MonitoramentoCiclo_Sub_Est.jrxml"),
	MONITORAMENTO_CICLO_SR_PRODUTO("MonitoramentoCiclo_Sub_Prod.jrxml"),
	MONITORAMENTO_CICLO_JASPER("MonitoramentoCiclo.jasper"),
	MONITORAMENTO_CICLO_SR_ESTRATEGIA_JASPER("MonitoramentoCiclo_Sub_Est.jasper"),
	MONITORAMENTO_CICLO_SR_PRODUTO_JASPER("MonitoramentoCiclo_Sub_Prod.jasper"),
	REDE_CICLO("IndicadoresRedes.jrxml"),
	REDE_CICLO_JASPER("IndicadoresRedes.jasper"),
	REDE_CICLO_IND_JASPER("IndicadoresRedes_Ind.jasper"),
	REDE_CICLO_PT_JASPER("PlanoAcao_SR.jasper"),
	REDE_PLANO_ACAO_JASPER("PlanoTrabalho.jasper"),
	REDE_PLANO_ACAO_PE_JASPER("PlanoTrabalhoPE.jasper"),
	REDE_CICLO_SIMPLES_JASPER("IndicadoresRedesSIMPLES.jasper"),
	REDE_CICLO_SIMPLES_IND_JASPER("IndicadoresRedes_IndSIMPLES.jasper")
	;
	
	private String reportName;
	
	private Report(String reportName) {
		this.reportName = reportName;
	}
	
	public String getReportName() {
		return reportName;
	}
	
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
}