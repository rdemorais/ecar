package ecar.pojo.intercambioDados;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import comum.util.ConstantesECAR;

public class EntidadeLogIntercambioDadosEtlogid {
	
	/**
	 * 
	 */
	private Long codEtlogid;
	
	/**
	 * Nome
	 */
	private String nomeEtlogid;
	
	/**
	 * 
	 */
	private MotivoRejeicaoMtr motivoRejeicaoEtlogid;
	
	/**
	 * 
	 */
	private String valorLigacaoEtlogid;
	
	/**
	 * 
	 */
	private String registroEtlogid;
	
	/**
	 * 
	 */
	private Long numeroRegistroEtlogid;
	
	/**
	 * 
	 */
	private String situcaoEtlogid;
	
	
	/**
     * 
     */
    private String indOperacaoRealizadaEtlogid;
    
    
    /**
     * 
     */
    private LogIntercambioDadosLid logIntercambioDados;
    private Map<String, String> mapaValorLig = new HashMap<String, String>();
    
    
    public static final String VALOR_LIG_COD_ITEM_EST = "codigoItem";
    public static final String VALOR_LIG_TIPO_EMP = "valorTipoEmp";
    public static final String VALOR_LIGACAO = "valorLigacao";
    
    
    public EntidadeLogIntercambioDadosEtlogid() {
    	
    }

	public EntidadeLogIntercambioDadosEtlogid(Long codEtlogid,
			String nomeEtlogid, MotivoRejeicaoMtr motivoRejeicaoEtlogid,
			String valorLigacaoEtlogid, String registroEtlogid,
			Long numeroRegistroEtlogid, String situcaoEtlogid,
			String indOperacaoRealizadaEtlogid,
			LogIntercambioDadosLid logIntercambioDados) {
		super();
		this.codEtlogid = codEtlogid;
		this.nomeEtlogid = nomeEtlogid;
		this.motivoRejeicaoEtlogid = motivoRejeicaoEtlogid;
		this.valorLigacaoEtlogid = valorLigacaoEtlogid;
		this.registroEtlogid = registroEtlogid;
		this.numeroRegistroEtlogid = numeroRegistroEtlogid;
		this.situcaoEtlogid = situcaoEtlogid;
		this.indOperacaoRealizadaEtlogid = indOperacaoRealizadaEtlogid;
		this.logIntercambioDados = logIntercambioDados;
	}


	public Long getCodEtlogid() {
		return codEtlogid;
	}


	public void setCodEtlogid(Long codEtlogid) {
		this.codEtlogid = codEtlogid;
	}


	public String getNomeEtlogid() {
		return nomeEtlogid;
	}


	public void setNomeEtlogid(String nomeEtlogid) {
		this.nomeEtlogid = nomeEtlogid;
	}


	public void setValorLigacaoEtlogid(String valorLigacaoEtlogid) {
		this.valorLigacaoEtlogid = valorLigacaoEtlogid;
	}


	public String getRegistroEtlogid() {
		return registroEtlogid;
	}


	public void setRegistroEtlogid(String registroEtlogid) {
		this.registroEtlogid = registroEtlogid;
	}


	public Long getNumeroRegistroEtlogid() {
		return numeroRegistroEtlogid;
	}


	public void setNumeroRegistroEtlogid(Long numeroRegistroEtlogid) {
		this.numeroRegistroEtlogid = numeroRegistroEtlogid;
	}


	public String getSitucaoEtlogid() {
		return situcaoEtlogid;
	}


	public void setSitucaoEtlogid(String situcaoEtlogid) {
		this.situcaoEtlogid = situcaoEtlogid;
	}


	public String getIndOperacaoRealizadaEtlogid() {
		return indOperacaoRealizadaEtlogid;
	}


	public void setIndOperacaoRealizadaEtlogid(String indOperacaoRealizadaEtlogid) {
		this.indOperacaoRealizadaEtlogid = indOperacaoRealizadaEtlogid;
	}


	public MotivoRejeicaoMtr getMotivoRejeicaoEtlogid() {
		return motivoRejeicaoEtlogid;
	}


	public void setMotivoRejeicaoEtlogid(MotivoRejeicaoMtr motivoRejeicaoEtlogid) {
		this.motivoRejeicaoEtlogid = motivoRejeicaoEtlogid;
	}


	public LogIntercambioDadosLid getLogIntercambioDados() {
		return logIntercambioDados;
	}


	public void setLogIntercambioDados(LogIntercambioDadosLid logIntercambioDados) {
		this.logIntercambioDados = logIntercambioDados;
	}
	
    /**
     * @return Retorna a descrição do tipo de operação realizada.
     */
    public String getDescOperacaoRealizadaEtlogid() {
	
	if (indOperacaoRealizadaEtlogid!=null && indOperacaoRealizadaEtlogid.equalsIgnoreCase(ConstantesECAR.TIPO_OPERACAO_INCLUSAO))
	    return "Inclusão";
	
	if (indOperacaoRealizadaEtlogid!=null && indOperacaoRealizadaEtlogid.equalsIgnoreCase(ConstantesECAR.TIPO_OPERACAO_ALTERACAO))
	    return "Alteração";
	
	if (indOperacaoRealizadaEtlogid!=null && indOperacaoRealizadaEtlogid.equalsIgnoreCase(ConstantesECAR.TIPO_OPERACAO_EXCLUSAO))
	    return "Exclusão";
	
	return "?";
    }
    
    public Map<String, String> parserValorLigacao() {
		    	
		if (mapaValorLig.size()==0) {
			mapaValorLig = new HashMap<String, String>();
			StringTokenizer strToekn = new StringTokenizer(valorLigacaoEtlogid, LogIntercambioDadosLid.LOG_SEPARADOR);
			
			while(strToekn.hasMoreTokens()) {
				
				String par = strToekn.nextToken();
				// CHAVE, VALOR
				mapaValorLig.put(par.substring(0,par.indexOf("=")), par.substring(par.indexOf("=")+1, par.length()));

			}

		} 
		return mapaValorLig;
	}
    
    public String getCodItemSistemaOrigemEtlogid() {
    	return parserValorLigacao().get(VALOR_LIG_COD_ITEM_EST);
    }
    
    public String getValorTipoEmpreendimentoEtlogid() {
    	return parserValorLigacao().get(VALOR_LIG_TIPO_EMP);
    }
    
	public String getValorLigacaoParsed() {
		return parserValorLigacao().get(VALOR_LIGACAO);
	}

	public String getValorLigacaoEtlogid() {
		return valorLigacaoEtlogid;
	}


}
