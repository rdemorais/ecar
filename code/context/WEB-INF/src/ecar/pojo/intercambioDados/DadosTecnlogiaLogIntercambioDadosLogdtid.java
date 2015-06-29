package ecar.pojo.intercambioDados;

import java.util.Date;

public class DadosTecnlogiaLogIntercambioDadosLogdtid {

	
	private static final long serialVersionUID = 5919535588095055569L;
	
	/**
	 * Código do log gerado pelo banco
	 * */
    private Long codDtlid;
    
    /**
	 * Código do log gerado pelo banco
	 * */
    private String tipoDtlid;
    
    /**
     * Data/hora de geração de arquivo
     */
    private Date dataGeracaoArquivoDtlid;
    
    /**
     * Nome de arquivo
     */
    private String nomeArquivoDtlid;
    
    
    /**
     * Data/hora de inicio de intervalo de importacao
     */
    private Date dataInicioIntervaloImportacaoDtlid;
    
    
    /**
     * Data/hora de fim de intervalo de importacao
     */
    private Date dataFimIntervaloImportacaoDtlid;

    public DadosTecnlogiaLogIntercambioDadosLogdtid() {
    	
    }
    
    
    public DadosTecnlogiaLogIntercambioDadosLogdtid(Long codDtlid,
			Date dataGeracaoArquivoDtlid, String nomeArquivoDtlid,
			Date dataInicioIntervaloImportacaoDtlid,
			Date dataFimIntervaloImportacaoDtlid,
			LogIntercambioDadosLid logIntercambioDados) {
		super();
		this.codDtlid = codDtlid;
		this.dataGeracaoArquivoDtlid = dataGeracaoArquivoDtlid;
		this.nomeArquivoDtlid = nomeArquivoDtlid;
		this.dataInicioIntervaloImportacaoDtlid = dataInicioIntervaloImportacaoDtlid;
		this.dataFimIntervaloImportacaoDtlid = dataFimIntervaloImportacaoDtlid;
		this.logIntercambioDados = logIntercambioDados;
	}


	/**
     * 
     */
    private LogIntercambioDadosLid logIntercambioDados;
    

	public Long getCodDtlid() {
		return codDtlid;
	}


	public void setCodDtlid(Long codDtlid) {
		this.codDtlid = codDtlid;
	}


	public Date getDataGeracaoArquivoDtlid() {
		return dataGeracaoArquivoDtlid;
	}


	public void setDataGeracaoArquivoDtlid(Date dataGeracaoArquivoDtlid) {
		this.dataGeracaoArquivoDtlid = dataGeracaoArquivoDtlid;
	}


	public String getNomeArquivoDtlid() {
		return nomeArquivoDtlid;
	}


	public void setNomeArquivoDtlid(String nomeArquivoDtlid) {
		this.nomeArquivoDtlid = nomeArquivoDtlid;
	}


	public Date getDataInicioIntervaloImportacaoDtlid() {
		return dataInicioIntervaloImportacaoDtlid;
	}


	public void setDataInicioIntervaloImportacaoDtlid(
			Date dataInicioIntervaloImportacaoDtlid) {
		this.dataInicioIntervaloImportacaoDtlid = dataInicioIntervaloImportacaoDtlid;
	}


	public Date getDataFimIntervaloImportacaoDtlid() {
		return dataFimIntervaloImportacaoDtlid;
	}


	public void setDataFimIntervaloImportacaoDtlid(
			Date dataFimIntervaloImportacaoDtlid) {
		this.dataFimIntervaloImportacaoDtlid = dataFimIntervaloImportacaoDtlid;
	}


	public LogIntercambioDadosLid getLogIntercambioDados() {
		return logIntercambioDados;
	}


	public void setLogIntercambioDados(LogIntercambioDadosLid logIntercambioDados) {
		this.logIntercambioDados = logIntercambioDados;
	}


	public String getTipoDtlid() {
		return tipoDtlid;
	}


	public void setTipoDtlid(String tipoDtlid) {
		this.tipoDtlid = tipoDtlid;
	}




}
