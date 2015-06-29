package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * Esta classe detém o parecer do responsável pelo item em monitoramento.
 * 
 * É importante esclarecer que cada item pode ter mais de um ator, ou seja, pode ser que em um item
 * haja o responsável pelo item e o gerente do setor. Por este motivo esta classe aparece em um relacionamento de 1*N
 * considerando {@link AcompReferenciaItemAri} -> {@link AcompRelatorioArel}.
 * 
 * Aqui a {@link SituacaoSit} é informada, assim como o parecer textual representado aqui por descricaoArel.
 * 
 * @author Hibernate CodeGenerator 
 * */
public class AcompRelatorioArel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7982215844479210136L;

	/** identifier field */
    private Long codArel;

    /** nullable persistent field */
    private Date dataUltManutArel;

    /** nullable persistent field */
    private Date dataInclusaoArel;

    /** nullable persistent field */
    private String indLiberadoArel;

    /** 
     * Parecer do ator
     * 
     * nullable persistent field */
    private String descricaoArel;
    
    /** 
     * A função de acompanhamento do ator
     * persistent field */
    private TipoFuncAcompTpfa tipoFuncAcompTpfaUsuario;
    
    /** persistent field */
    private TipoFuncAcompTpfa tipoFuncAcompTpfa;
    
    private ecar.pojo.UsuarioUsu usuarioUsu;
    
    /** persistent field */
    private ecar.pojo.AcompReferenciaItemAri acompReferenciaItemAri;

    /** persistent field */
    private ecar.pojo.SituacaoSit situacaoSit;

    /** persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsuUltimaManutencao;

    /** persistent field */
    private ecar.pojo.Cor cor;
    
    private String complementoArel;
    
    /** persistent field */
    private Set itemEstrutUploadIettup;
    
    /** persistent field */
    private Set pontoCriticoPtc;

    /** 
     * full constructor.<br>
     * 
     * @author N/C
     * @since N/C
     * @param dataUltManutArel
     * @param dataInclusaoArel
     * @param indLiberadoArel
     * @param descricaoArel 
     * @param acompReferenciaItemAri
     * @param usuarioUsu 
     * @param situacaoSit
     * @param cor
     * @param complementoArel
     * @param usuarioUsuUltimaManuntencao
     * @param tipoFuncaoAcompTpfa
     * @param itemEstrutUploadIettup
     * @param pontoCriticoPtc
     */
    public AcompRelatorioArel(Date dataUltManutArel, Date dataInclusaoArel, String indLiberadoArel, 
    		String descricaoArel, ecar.pojo.AcompReferenciaItemAri acompReferenciaItemAri, 
    		ecar.pojo.SituacaoSit situacaoSit, ecar.pojo.UsuarioUsu usuarioUsu, ecar.pojo.Cor cor, 
    		UsuarioUsu usuarioUsuUltimaManuntencao, TipoFuncAcompTpfa tipoFuncaoAcompTpfa, 
    		String complementoArel, Set itemEstrutUploadIettup, Set pontoCriticoPtc,
    		TipoFuncAcompTpfa tipoFuncaoAcompTpfaUsuario) {
        this.dataUltManutArel = dataUltManutArel;
        this.dataInclusaoArel = dataInclusaoArel;
        this.indLiberadoArel = indLiberadoArel;
        this.descricaoArel = descricaoArel;
        this.acompReferenciaItemAri = acompReferenciaItemAri;
        this.situacaoSit = situacaoSit;
        this.usuarioUsu = usuarioUsu;
        this.usuarioUsuUltimaManutencao = usuarioUsuUltimaManuntencao;
        this.tipoFuncAcompTpfa = tipoFuncaoAcompTpfa;
        this.cor = cor;
        this.complementoArel = complementoArel;
        this.itemEstrutUploadIettup = itemEstrutUploadIettup;
        this.pontoCriticoPtc = pontoCriticoPtc;
        this.tipoFuncAcompTpfaUsuario = tipoFuncaoAcompTpfaUsuario;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getComplementoArel() {
		return complementoArel;
	}

    /**
     * @author N/C
     * @since N/C
     * @param complementoArel
     */
	public void setComplementoArel(String complementoArel) {
		this.complementoArel = complementoArel;
	}

	/** default constructor */
    public AcompRelatorioArel() {
    }

    /** minimal constructor
     * 
     * @author N/C
     * @since N/C
     * 
     * @param acompReferenciaItemAri 
     * @param situacaoSit
     * @param usuarioUsu
     * @param tipoFuncAcompTpfa
     * @param cor
     */
    public AcompRelatorioArel(ecar.pojo.AcompReferenciaItemAri acompReferenciaItemAri, 
    		ecar.pojo.SituacaoSit situacaoSit, ecar.pojo.UsuarioUsu usuarioUsu, ecar.pojo.Cor cor, 
    		TipoFuncAcompTpfa tipoFuncAcompTpfa) {
        this.acompReferenciaItemAri = acompReferenciaItemAri;
        this.situacaoSit = situacaoSit;
        this.usuarioUsu = usuarioUsu;
        this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
        this.cor = cor;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Long
     */
    public Long getCodArel() {
        return this.codArel;
    }

    /**
     * @param codArel
     * @author N/C
     * @since N/C
     */
    public void setCodArel(Long codArel) {
        this.codArel = codArel;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public Date getDataUltManutArel() {
        return this.dataUltManutArel;
    }

    /**
     * @author N/C
     * @since N/C
     * @param dataUltManutArel
     */
    public void setDataUltManutArel(Date dataUltManutArel) {
        this.dataUltManutArel = dataUltManutArel;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public Date getDataInclusaoArel() {
        return this.dataInclusaoArel;
    }

    /**
     * @author N/C
     * @since N/C
     * @param dataInclusaoArel
     */
    public void setDataInclusaoArel(Date dataInclusaoArel) {
        this.dataInclusaoArel = dataInclusaoArel;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getIndLiberadoArel() {
        return this.indLiberadoArel;
    }

    /**
     * @param indLiberadoArel
     * @author N/C
     * @since N/C
     */
    public void setIndLiberadoArel(String indLiberadoArel) {
        this.indLiberadoArel = indLiberadoArel;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getDescricaoArel() {
    	//Incluir modelo de relatório aqui
        return this.descricaoArel;
    }

    /**
     * @param descricaoArel
     * @author N/C
     * @since N/C
     */
    public void setDescricaoArel(String descricaoArel) {
        this.descricaoArel = descricaoArel;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.AcompReferenciaItemAri
     */
    public ecar.pojo.AcompReferenciaItemAri getAcompReferenciaItemAri() {
        return this.acompReferenciaItemAri;
    }

    /**
     * @param acompReferenciaItemAri
     * @author N/C
     * @since N/C
     */
    public void setAcompReferenciaItemAri(ecar.pojo.AcompReferenciaItemAri acompReferenciaItemAri) {
        this.acompReferenciaItemAri = acompReferenciaItemAri;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.SituacaoSit
     */
    public ecar.pojo.SituacaoSit getSituacaoSit() {
        return this.situacaoSit;
    }

    /**
     * @param situacaoSit
     * @author N/C
     * @since N/C
     */
    public void setSituacaoSit(ecar.pojo.SituacaoSit situacaoSit) {
        this.situacaoSit = situacaoSit;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.UsuarioUsu
     */
    public ecar.pojo.UsuarioUsu getUsuarioUsu() {
        return this.usuarioUsu;
    }

    /**
     * @author N/C
     * @since N/C
     * @param usuarioUsu
     */
    public void setUsuarioUsu(ecar.pojo.UsuarioUsu usuarioUsu) {
        this.usuarioUsu = usuarioUsu;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.Cor
     */
    public ecar.pojo.Cor getCor() {
        return this.cor;
    }

    /**
     * @param cor
     * @author N/C
     * @since N/C
     */
    public void setCor(ecar.pojo.Cor cor) {
        this.cor = cor;
    }

    /**
     * @author N/C
     * @since N/C
     * @return TipoFuncAcompTpfa
     */
    public TipoFuncAcompTpfa getTipoFuncAcompTpfa() {
        return tipoFuncAcompTpfa;
    }

    /**
     * @author N/C
     * @since N/C
     * @param tipoFuncAcompTpfaUsuario
     */
    public void setTipoFuncAcompTpfaUsuario(TipoFuncAcompTpfa tipoFuncAcompTpfaUsuario) {
        this.tipoFuncAcompTpfaUsuario = tipoFuncAcompTpfaUsuario;
    }
    
    /**
     * @author N/C
     * @since N/C
     * @return tipoFuncAcompTpfaUsuario
     */
    public TipoFuncAcompTpfa getTipoFuncAcompTpfaUsuario() {
        return tipoFuncAcompTpfaUsuario;
    }

    /**
     * @author N/C
     * @since N/C
     * @param tipoFuncAcompTpfa
     */
    public void setTipoFuncAcompTpfa(TipoFuncAcompTpfa tipoFuncAcompTpfa) {
        this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
    }


    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.UsuarioUsu
     */
    public ecar.pojo.UsuarioUsu getUsuarioUsuUltimaManutencao() {
        return usuarioUsuUltimaManutencao;
    }

    /**
     * @author N/C
     * @since N/C
     * @param usuarioUsuUltimaManutencao
     */
    public void setUsuarioUsuUltimaManutencao(
            ecar.pojo.UsuarioUsu usuarioUsuUltimaManutencao) {
        this.usuarioUsuUltimaManutencao = usuarioUsuUltimaManutencao;
    }
    
    /**
     * @author N/C
     * @since N/C
     * @return Set
     */
	public Set getItemEstrutUploadIettup() {
		return itemEstrutUploadIettup;
	}

    /**
     * @param itemEstrutUploadIettup
     * @author N/C
     * @since N/C
     */
	public void setItemEstrutUploadIettup(Set itemEstrutUploadIettup) {
		this.itemEstrutUploadIettup = itemEstrutUploadIettup;
	}
    
    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codArel", getCodArel())
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
        if ( !(other instanceof AcompRelatorioArel) ) return false;
        AcompRelatorioArel castOther = (AcompRelatorioArel) other;
        return new EqualsBuilder()
        	.append(this.getCodArel(), castOther.getCodArel())
        	.append(this.getTipoFuncAcompTpfa(), castOther.getTipoFuncAcompTpfa())        	
        	.append(this.getAcompReferenciaItemAri(), castOther.getAcompReferenciaItemAri())
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
            .append(getCodArel())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public Set getPontoCriticoPtc() {
		return pontoCriticoPtc;
	}

    /**
     *
     * @param pontoCriticoPtc
     */
    public void setPontoCriticoPtc(Set pontoCriticoPtc) {
		this.pontoCriticoPtc = pontoCriticoPtc;
	}
   
}
