package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Email implements Serializable {
	
    /**
     *
     */
    public static final String LIDO = "S";
    /**
     *
     */
    public static final String NLIDO = "N";
        /**
         *
         */
        public static final String MARCA_LIDO = "1";
        /**
         *
         */
        public static final String MARCA_NLIDO = "0";

    /** identifier field */
    private Long codEmail;

    /** nullable persistent field */
    private String sistema;

    /** nullable persistent field */
    private Date dataHoraEnvio;

    /** nullable persistent field */
    private String assunto;

    /** nullable persistent field */
    private String destinatario;

    /** nullable persistent field */
    private String destinatariocc;

    /** nullable persistent field */
    private String destinatariobcc;

    /** nullable persistent field */
    private String conteudo;

    /** nullable persistent field */
    private String lido;

    /** persistent field */
    private UsuarioUsu usuarioUsu;

    /** full constructor
     * @param sistema
     * @param dataHoraEnvio
     * @param assunto
     * @param conteudo
     * @param destinatariocc
     * @param destinatario
     * @param destinatariobcc
     * @param lido
     * @param usuarioUsu
     */
    public Email(String sistema, Date dataHoraEnvio, String assunto, String destinatario, String destinatariocc, String destinatariobcc, String conteudo, String lido, UsuarioUsu usuarioUsu) {
        //this.codEmail = codEmail;
        this.sistema = sistema;
        this.dataHoraEnvio = dataHoraEnvio;
        this.assunto = assunto;
        this.destinatario = destinatario;
        this.destinatariocc = destinatariocc;
        this.destinatariobcc = destinatariobcc;
        this.conteudo = conteudo;
        this.lido = lido;
        this.usuarioUsu = usuarioUsu;
    }

    /** default constructor */
    public Email() {
    }

    /** minimal constructor
     * @param usuarioUsu
     */
    public Email(UsuarioUsu usuarioUsu) {
        //this.codEmail = codEmail;
        this.usuarioUsu = usuarioUsu;
    }

    /**
     *
     * @return
     */
    public Long getCodEmail() {
        return this.codEmail;
    }

    /**
     *
     * @param codEmail
     */
    public void setCodEmail(Long codEmail) {
        this.codEmail = codEmail;
    }

    /**
     *
     * @return
     */
    public String getSistema() {
        return this.sistema;
    }

    /**
     *
     * @param sistema
     */
    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    /**
     *
     * @return
     */
    public Date getDataHoraEnvio() {
        return this.dataHoraEnvio;
    }

    /**
     *
     * @param dataHoraEnvio
     */
    public void setDataHoraEnvio(Date dataHoraEnvio) {
        this.dataHoraEnvio = dataHoraEnvio;
    }

    /**
     *
     * @return
     */
    public String getAssunto() {
        return this.assunto;
    }

    /**
     *
     * @param assunto
     */
    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    /**
     *
     * @return
     */
    public String getDestinatario() {
        return this.destinatario;
    }

    /**
     *
     * @param destinatario
     */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    /**
     *
     * @return
     */
    public String getDestinatariocc() {
        return this.destinatariocc;
    }

    /**
     *
     * @param destinatariocc
     */
    public void setDestinatariocc(String destinatariocc) {
        this.destinatariocc = destinatariocc;
    }

    /**
     *
     * @return
     */
    public String getDestinatariobcc() {
        return this.destinatariobcc;
    }

    /**
     *
     * @param destinatariobcc
     */
    public void setDestinatariobcc(String destinatariobcc) {
        this.destinatariobcc = destinatariobcc;
    }

    /**
     *
     * @return
     */
    public String getConteudo() {
        return this.conteudo;
    }

    /**
     *
     * @param conteudo
     */
    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    /**
     *
     * @return
     */
    public String getLido() {
        return this.lido;
    }

    /**
     * 
     * @param lido
     */
    public void setLido(String lido) {
        this.lido = lido;
    }

    /**
     *
     * @return
     */
    public UsuarioUsu getUsuarioUsu() {
        return this.usuarioUsu;
    }

    /**
     *
     * @param usuarioUsu
     */
    public void setUsuarioUsu(UsuarioUsu usuarioUsu) {
        this.usuarioUsu = usuarioUsu;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codEmail", getCodEmail())
            .toString();
    }
}
