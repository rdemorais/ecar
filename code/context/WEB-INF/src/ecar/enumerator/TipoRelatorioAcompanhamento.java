package ecar.enumerator;

public enum TipoRelatorioAcompanhamento {
	EXECUTIVO("1", "Relat\u00F3rio Executivo", "RELAT\u00d3RIO EXECUTIVO"),
	GERENCIAL("2", "Relat\u00F3rio Gerencial", "RELAT\u00d3RIO GERENCIAL"),
	INDICADORES("3", "Relat\u00F3rio Indicadores", "RELAT\u00d3RIO INDICADORES"),
	OPERACIONAL_PRODUTOS("4", "Relat\u00F3rio Operacional Produtos", "Resultado + Produtos"),
	OPERACIONAL_PRODUTOS_ACOES("5", "Relat\u00F3rio Operacional Produtos + A\u00e7\u00f5es", "Produto + A\u00e7\u00f5es"),
	OPERACIONAL_PRODUTOS_ACOES_ATIVIDADES("6", "Relat\u00F3rio Operacional Produtos + A\u00e7\u00f5es + Atividades", "A\u00e7\u00f5es + Atividades"),
	ENCAMINHAMENTOS("7", "Relat\u00F3rio de Encaminhamentos", "RELAT\u00d3RIO DE ENCAMINHAMENTOS"),
	INDICADORES_DETALHAMENTO("8", "Relat\u00F3rio de Indicadores Detalhamento", "RELAT\u00d3RIO DE DETALHAMENTO DOS INDICADORES"),
	GERENCIAL_MONITORAMENTOS("9", "Relat\u00F3rio Gerencial", "RELAT\u00d3RIO GERENCIAL COM OS TRÊS ÚLTIMOS MONITORAMENTOS");

	
	private TipoRelatorioAcompanhamento(String codigo, String descricao, String descricaoReport){
		this.codigo = codigo;
		this.descricao = descricao;
		this.descricaoReport = descricaoReport;
	}
	
	private String codigo;
	private String descricao;
	private String descricaoReport;
	
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public String getDescricaoReport() {
		return descricaoReport;
	}
}