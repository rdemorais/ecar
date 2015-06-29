package ecar.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * @author Hibernate CodeGenerator, rogerio
 * @since n/c
 * @version 0.2, 06/06/2007 */
public class EfIettFonteTotEfieft implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4618206056177065245L;

	/** identifier field */
    private ecar.pojo.EfIettFonteTotEfieftPK comp_id;

    /** nullable persistent field */
    private BigDecimal valorEfieft;

    /** nullable persistent field */
    private Date dataValorEfieft;

    /** nullable persistent field */
    private String indAtivoEfieft;

    /** nullable persistent field */
    private Date dataInclusaoEfieft;

    /** nullable persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    /** nullable persistent field */
    private ecar.pojo.FonteRecursoFonr fonteRecursoFonr;
    
    /* -- Mantis #2156 -- */
    private Boolean indExclusaoPosHistorico;


    /**
     *
     * @return
     */
    public Boolean getIndExclusaoPosHistorico() {
		return indExclusaoPosHistorico;
	}

    /**
     *
     * @param indExclusaoPosHistorico
     */
    public void setIndExclusaoPosHistorico(Boolean indExclusaoPosHistorico) {
		this.indExclusaoPosHistorico = indExclusaoPosHistorico;
	}

        /** full constructor
         * @param comp_id
         * @param valorEfieft
         * @param dataInclusaoEfieft
         * @param dataValorEfieft
         * @param indAtivoEfieft
         * @param itemEstruturaIett
         * @param fonteRecursoFonr
         */
    public EfIettFonteTotEfieft(ecar.pojo.EfIettFonteTotEfieftPK comp_id, BigDecimal valorEfieft, Date dataValorEfieft, String indAtivoEfieft, Date dataInclusaoEfieft, ecar.pojo.ItemEstruturaIett itemEstruturaIett, ecar.pojo.FonteRecursoFonr fonteRecursoFonr) {
        this.comp_id = comp_id;
        this.valorEfieft = valorEfieft;
        this.dataValorEfieft = dataValorEfieft;
        this.indAtivoEfieft = indAtivoEfieft;
        this.dataInclusaoEfieft = dataInclusaoEfieft;
        this.itemEstruturaIett = itemEstruturaIett;
        this.fonteRecursoFonr = fonteRecursoFonr;
    }

    /** default constructor */
    public EfIettFonteTotEfieft() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public EfIettFonteTotEfieft(ecar.pojo.EfIettFonteTotEfieftPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.EfIettFonteTotEfieftPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.EfIettFonteTotEfieftPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public BigDecimal getValorEfieft() {
        return this.valorEfieft;
    }

    /**
     *
     * @param valorEfieft
     */
    public void setValorEfieft(BigDecimal valorEfieft) {
        this.valorEfieft = valorEfieft;
    }

    /**
     *
     * @return
     */
    public Date getDataValorEfieft() {
        return this.dataValorEfieft;
    }

    /**
     *
     * @param dataValorEfieft
     */
    public void setDataValorEfieft(Date dataValorEfieft) {
        this.dataValorEfieft = dataValorEfieft;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoEfieft() {
        return this.indAtivoEfieft;
    }

    /**
     *
     * @param indAtivoEfieft
     */
    public void setIndAtivoEfieft(String indAtivoEfieft) {
        this.indAtivoEfieft = indAtivoEfieft;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoEfieft() {
        return this.dataInclusaoEfieft;
    }

    /**
     *
     * @param dataInclusaoEfieft
     */
    public void setDataInclusaoEfieft(Date dataInclusaoEfieft) {
        this.dataInclusaoEfieft = dataInclusaoEfieft;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstruturaIett getItemEstruturaIett() {
        return this.itemEstruturaIett;
    }

    /**
     *
     * @param itemEstruturaIett
     */
    public void setItemEstruturaIett(ecar.pojo.ItemEstruturaIett itemEstruturaIett) {
        this.itemEstruturaIett = itemEstruturaIett;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.FonteRecursoFonr getFonteRecursoFonr() {
        return this.fonteRecursoFonr;
    }

    /**
     *
     * @param fonteRecursoFonr
     */
    public void setFonteRecursoFonr(ecar.pojo.FonteRecursoFonr fonteRecursoFonr) {
        this.fonteRecursoFonr = fonteRecursoFonr;
    }
    
    /**
     *
     */
    public void atribuirPKPai(){
		comp_id = new EfIettFonteTotEfieftPK();
		comp_id.setCodIett(this.getItemEstruturaIett().getCodIett());
		comp_id.setCodFonr(this.getFonteRecursoFonr().getCodFonr());
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof EfIettFonteTotEfieft) ) return false;
        EfIettFonteTotEfieft castOther = (EfIettFonteTotEfieft) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

}
