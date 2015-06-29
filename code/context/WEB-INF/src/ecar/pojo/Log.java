package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Log implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8797835277941245494L;

	/** identifier field */
    private Long codLog;

    /** nullable persistent field */
    private Date dataLog;

    /** nullable persistent field */
    private String stringSqlLog;

    /** nullable persistent field */
    private String nomeTabelaLog;

    /** nullable persistent field */
    private String operacaoLog;

    /** nullable persistent field */
    private String ipLog;

    /** nullable persistent field */
    private Long codSessaoLog;

    /** nullable persistent field */
    private Long codGrupoLog;

    /** persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsu;

    /** full constructor
     * @param dataLog
     * @param stringSqlLog
     * @param nomeTabelaLog
     * @param operacaoLog
     * @param codGrupoLog
     * @param ipLog
     * @param codSessaoLog
     * @param usuarioUsu
     */
    public Log(Date dataLog, String stringSqlLog, String nomeTabelaLog, String operacaoLog, String ipLog, Long codSessaoLog, Long codGrupoLog, ecar.pojo.UsuarioUsu usuarioUsu) {
        this.dataLog = dataLog;
        this.stringSqlLog = stringSqlLog;
        this.nomeTabelaLog = nomeTabelaLog;
        this.operacaoLog = operacaoLog;
        this.ipLog = ipLog;
        this.codSessaoLog = codSessaoLog;
        this.codGrupoLog = codGrupoLog;
        this.usuarioUsu = usuarioUsu;
    }

    /** default constructor */
    public Log() {
    }

    /** minimal constructor
     * @param usuarioUsu
     */
    public Log(ecar.pojo.UsuarioUsu usuarioUsu) {
        this.usuarioUsu = usuarioUsu;
    }

    /**
     *
     * @return
     */
    public Long getCodLog() {
        return this.codLog;
    }

    /**
     *
     * @param codLog
     */
    public void setCodLog(Long codLog) {
        this.codLog = codLog;
    }

    /**
     *
     * @return
     */
    public Date getDataLog() {
        return this.dataLog;
    }

    /**
     *
     * @param dataLog
     */
    public void setDataLog(Date dataLog) {
        this.dataLog = dataLog;
    }

    /**
     *
     * @return
     */
    public String getStringSqlLog() {
        return this.stringSqlLog;
    }

    /**
     *
     * @param stringSqlLog
     */
    public void setStringSqlLog(String stringSqlLog) {
        this.stringSqlLog = stringSqlLog;
    }

    /**
     *
     * @return
     */
    public String getNomeTabelaLog() {
        return this.nomeTabelaLog;
    }

    /**
     *
     * @param nomeTabelaLog
     */
    public void setNomeTabelaLog(String nomeTabelaLog) {
        this.nomeTabelaLog = nomeTabelaLog;
    }

    /**
     *
     * @return
     */
    public String getOperacaoLog() {
        return this.operacaoLog;
    }

    /**
     *
     * @param operacaoLog
     */
    public void setOperacaoLog(String operacaoLog) {
        this.operacaoLog = operacaoLog;
    }

    /**
     *
     * @return
     */
    public String getIpLog() {
        return this.ipLog;
    }

    /**
     *
     * @param ipLog
     */
    public void setIpLog(String ipLog) {
        this.ipLog = ipLog;
    }

    /**
     *
     * @return
     */
    public Long getCodSessaoLog() {
        return this.codSessaoLog;
    }

    /**
     *
     * @param codSessaoLog
     */
    public void setCodSessaoLog(Long codSessaoLog) {
        this.codSessaoLog = codSessaoLog;
    }

    /**
     *
     * @return
     */
    public Long getCodGrupoLog() {
        return this.codGrupoLog;
    }

    /**
     *
     * @param codGrupoLog
     */
    public void setCodGrupoLog(Long codGrupoLog) {
        this.codGrupoLog = codGrupoLog;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.UsuarioUsu getUsuarioUsu() {
        return this.usuarioUsu;
    }

    /**
     *
     * @param usuarioUsu
     */
    public void setUsuarioUsu(ecar.pojo.UsuarioUsu usuarioUsu) {
        this.usuarioUsu = usuarioUsu;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codLog", getCodLog())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof Log) ) return false;
        Log castOther = (Log) other;
        return new EqualsBuilder()
            .append(this.getCodLog(), castOther.getCodLog())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodLog())
            .toHashCode();
    }

}
