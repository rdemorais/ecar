package ecar.pojo.intercambioDados;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import comum.util.ConstantesECAR;
import comum.util.Data;

import ecar.pojo.UsuarioUsu;


/** @author Hibernate CodeGenerator */
public class LogIntercambioDadosLid implements Serializable {
	
	
	private static final long serialVersionUID = 5919535588095055569L;
	/**
	 * Código do log gerado pelo banco
	 * */
    private Long codLid;
    
    /**
     * Data/hora do processamento
     */
    private Date dataHoraProcessamentoLid;
    
    /**
     * 
     */
    private String indSituacaoProcessamentoLid;
    
	/**
     * Usuário responsável pelo processamento do log
     */
    private UsuarioUsu usuarioUsu;
    
    /**
     * Quantidade de registros processados
     */
    private Long quantidadeRegistrosProcessadosLid;
    
    /**
     * Conteúdo que gerou log
     */
    private String conteudoLid;
    
    
    /**
     * Descrição de Erro de Comunicação
     */
    private String descricaoErroComunicacaoLid;
    
    
    /**
     * Log de Perfil
     */
    private PerfilIntercambioDadosLogPflogid perfilLog;
    
    /**
     * Situacao do Log
     */
    private String situacaoLogLid;
    
    
    /**
     * 
     */
    private Set<EntidadeLogIntercambioDadosEtlogid> entidadesLog;
    
    /**
     * 
     */
    private DadosTecnlogiaLogIntercambioDadosLogdtid dadosTecnologia;
    
    
    public static final String LOG_SEPARADOR = ",";

    
    private int itensIncluidos;
    private int itensAlterados;
    private int itensExcluidos;
    private int itensImportados;
    private int itensRejeitados;
    private int itensProcessados;
    
	
	public Long getCodLid() {
		return codLid;
	}

	public void setCodLid(Long codLid) {
		this.codLid = codLid;
	}

	public Date getDataHoraProcessamentoLid() {
		return dataHoraProcessamentoLid;
	}
	
	public String getDataHoraProcessamentoString() {
		return Data.parseDateHourMinuteSecond(dataHoraProcessamentoLid);
	}

	public void setDataHoraProcessamentoLid(Date dataHoraProcessamentoLid) {
		this.dataHoraProcessamentoLid = dataHoraProcessamentoLid;
	}

	public UsuarioUsu getUsuarioUsu() {
		return usuarioUsu;
	}

	public void setUsuarioUsu(UsuarioUsu usuarioUsu) {
		this.usuarioUsu = usuarioUsu;
	}

	public Long getQuantidadeRegistrosProcessadosLid() {
		return quantidadeRegistrosProcessadosLid;
	}

	public void setQuantidadeRegistrosProcessadosLid(
			Long quantidadeRegistrosProcessadosLid) {
		this.quantidadeRegistrosProcessadosLid = quantidadeRegistrosProcessadosLid;
	}

	public String getIndSituacaoProcessamentoLid() {
		return indSituacaoProcessamentoLid;
	}

	public void setIndSituacaoProcessamentoLid(String indSituacaoProcessamentoLid) {
		this.indSituacaoProcessamentoLid = indSituacaoProcessamentoLid;
	}

	public String getConteudoLid() {
		return conteudoLid;
	}

	public void setConteudoLid(String conteudoLid) {
		this.conteudoLid = conteudoLid;
	}

	public String getDescricaoErroComunicacaoLid() {
		return descricaoErroComunicacaoLid;
	}

	public void setDescricaoErroComunicacaoLid(String descricaoErroComunicacaoLid) {
		this.descricaoErroComunicacaoLid = descricaoErroComunicacaoLid;
	}

	public PerfilIntercambioDadosLogPflogid getPerfilLog() {
		return perfilLog;
	}

	public void setPerfilLog(PerfilIntercambioDadosLogPflogid perfilLog) {
		this.perfilLog = perfilLog;
	}

	public SituacaoLogEnum getSituacaoEnum() {
		return SituacaoLogEnum.valueOf(Integer.parseInt(situacaoLogLid));
	}

	public Set<EntidadeLogIntercambioDadosEtlogid> getEntidadesLog() {
		return entidadesLog;
	}

	public void setEntidadesLog(Set<EntidadeLogIntercambioDadosEtlogid> entidadesLog) {
		this.entidadesLog = entidadesLog;
	}

	public DadosTecnlogiaLogIntercambioDadosLogdtid getDadosTecnologia() {
		return dadosTecnologia;
	}

	public void setDadosTecnologia(DadosTecnlogiaLogIntercambioDadosLogdtid dadosTecnologia) {
		this.dadosTecnologia = dadosTecnologia;
	}
	
	
	
    
	/** default constructor */
    public LogIntercambioDadosLid() {
    	
    }
    
    

	public LogIntercambioDadosLid(Long codLid, Date dataHoraProcessamentoLid,
			String indSituacaoProcessamentoLid, UsuarioUsu usuarioUsu,
			Long quantidadeRegistrosProcessadosLid, String conteudoLid,
			String descricaoErroComunicacaoLid,
			PerfilIntercambioDadosLogPflogid perfilLog, String situacaoLogLid,
			Set<EntidadeLogIntercambioDadosEtlogid> entidadesLog,
			DadosTecnlogiaLogIntercambioDadosLogdtid dadosTecnologia) {
		super();
		this.codLid = codLid;
		this.dataHoraProcessamentoLid = dataHoraProcessamentoLid;
		this.indSituacaoProcessamentoLid = indSituacaoProcessamentoLid;
		this.usuarioUsu = usuarioUsu;
		this.quantidadeRegistrosProcessadosLid = quantidadeRegistrosProcessadosLid;
		this.conteudoLid = conteudoLid;
		this.descricaoErroComunicacaoLid = descricaoErroComunicacaoLid;
		this.perfilLog = perfilLog;
		this.situacaoLogLid = situacaoLogLid;
		this.entidadesLog = entidadesLog;
		this.dadosTecnologia = dadosTecnologia;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
    @Override
	public String toString() {
        return new ToStringBuilder(this)
            .append("codLid", getCodLid())
            .toString();
    }

	/**
	 * @author N/C
	 * @since N/C 
         * @param other
	 * @return boolean
	 */
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof LogIntercambioDadosLid) ) return false;
        LogIntercambioDadosLid castOther = (LogIntercambioDadosLid) other;
        return new EqualsBuilder()
            .append(this.getCodLid(), castOther.getCodLid())
            .isEquals();
    }

    /**
     * @author N/C
	 * @since N/C 
	 * @return int
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodLid())
            .toHashCode();
    }
    
    
 
    public String getDescSituacaoProcessamentoLid() {
		
		if (indSituacaoProcessamentoLid.equalsIgnoreCase(ConstantesECAR.PROCESSADO))
		    return "Processado";
		
		if (indSituacaoProcessamentoLid.equalsIgnoreCase(ConstantesECAR.REJEICAO))
		    return "Rejeitado";
	
		return "?";
    }

	public String getSituacaoLogLid() {
		return situacaoLogLid;
	}

	public void setSituacaoLogLid(String situacaoLogLid) {
		this.situacaoLogLid = situacaoLogLid;
	}
    
    public void gerarResumo(){
    	
    	itensProcessados = entidadesLog.size();
    	
    		for (EntidadeLogIntercambioDadosEtlogid entidade : entidadesLog){
    		
    			if (entidade.getIndOperacaoRealizadaEtlogid()!=null && 
        		    	entidade.getSitucaoEtlogid().equals(ConstantesECAR.REJEICAO)) {
        		    itensRejeitados++;
        		} else if (entidade.getIndOperacaoRealizadaEtlogid()!=null && 
    		    	entidade.getIndOperacaoRealizadaEtlogid().equalsIgnoreCase(ConstantesECAR.TIPO_OPERACAO_INCLUSAO)){
        			itensIncluidos++;
        		} else if (entidade.getIndOperacaoRealizadaEtlogid()!=null && 
    		    	entidade.getIndOperacaoRealizadaEtlogid().equalsIgnoreCase(ConstantesECAR.TIPO_OPERACAO_ALTERACAO)){
        			itensAlterados++;
        		} else if (entidade.getIndOperacaoRealizadaEtlogid()!=null && 
    		    	entidade.getIndOperacaoRealizadaEtlogid().equalsIgnoreCase(ConstantesECAR.TIPO_OPERACAO_EXCLUSAO)){
    		    	itensExcluidos++;
        		}
    		}
    		
    		itensImportados = itensProcessados - itensRejeitados;
        }
	
    public List<EntidadeLogIntercambioDadosEtlogid> getEntidadeLogIntercambioDadosEtlogidRejeitadas() {
    	List<EntidadeLogIntercambioDadosEtlogid> entidadesRejeitadas = new ArrayList<EntidadeLogIntercambioDadosEtlogid>();
    	
    	for (EntidadeLogIntercambioDadosEtlogid entidade : entidadesLog){
    		if (entidade.getSitucaoEtlogid().equals(ConstantesECAR.REJEICAO)) {
    			entidadesRejeitadas.add(entidade);
    		}
    	}
    	
    	return entidadesRejeitadas;
	}
    
    public List<EntidadeLogIntercambioDadosEtlogid> getEntidadeLogIntercambioDadosEtlogidAceitas() {
    	List<EntidadeLogIntercambioDadosEtlogid> entidadesRejeitadas = new ArrayList<EntidadeLogIntercambioDadosEtlogid>();
    	
    	for (EntidadeLogIntercambioDadosEtlogid entidade : entidadesLog){
    		if (entidade.getSitucaoEtlogid().equals(ConstantesECAR.PROCESSADO)) {
    			entidadesRejeitadas.add(entidade);
    		}
    	}
    	
    	return entidadesRejeitadas;
	}

	public int getItensIncluidos() {
		return itensIncluidos;
	}

	public void setItensIncluidos(int itensIncluidos) {
		this.itensIncluidos = itensIncluidos;
	}

	public int getItensAlterados() {
		return itensAlterados;
	}

	public void setItensAlterados(int itensAlterados) {
		this.itensAlterados = itensAlterados;
	}

	public int getItensExcluidos() {
		return itensExcluidos;
	}

	public void setItensExcluidos(int itensExcluidos) {
		this.itensExcluidos = itensExcluidos;
	}

	public int getItensImportados() {
		return itensImportados;
	}

	public void setItensImportados(int itensImportados) {
		this.itensImportados = itensImportados;
	}

	public int getItensRejeitados() {
		return itensRejeitados;
	}

	public void setItensRejeitados(int itensRejeitados) {
		this.itensRejeitados = itensRejeitados;
	}

	public int getItensProcessados() {
		return itensProcessados;
	}

	public void setItensProcessados(int itensProcessados) {
		this.itensProcessados = itensProcessados;
	}	
    
    
	
}



