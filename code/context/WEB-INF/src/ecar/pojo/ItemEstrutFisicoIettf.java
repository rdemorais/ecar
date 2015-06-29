package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * Esta classe representa os valore previstos para o indicador.
 * 
 * @author Hibernate CodeGenerator 
 * */
public class ItemEstrutFisicoIettf implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7211363418503332830L;

	/** identifier field */
    //private ecar.pojo.ItemEstrutFisicoIettfPK comp_id;
	private Long codIettf;
	
    /** nullable persistent field */
    private String indAtivoIettf;

    /** nullable persistent field */
    private Date dataInclusaoIettf;

    /** nullable persistent field */
    private Double qtdPrevistaIettf;

    /** nullable persistent field */
    private ecar.pojo.ItemEstrtIndResulIettr itemEstrtIndResulIettr;
    
    
    /** persistent field */                    
    private Set<ItemEstrtIndResulLocalIettirl> itemEstrtIndResulLocalIettirls;

    /** nullable persistent field */
    //private ecar.pojo.ExercicioExe exercicioExe;
    

    public Set<ItemEstrtIndResulLocalIettirl> getItemEstrtIndResulLocalIettirls() {
		return itemEstrtIndResulLocalIettirls;
	}

	public void setItemEstrtIndResulLocalIettirls(
			Set<ItemEstrtIndResulLocalIettirl> itemEstrtIndResulLocalIettirls) {
		this.itemEstrtIndResulLocalIettirls = itemEstrtIndResulLocalIettirls;
	}

	/** persistent field */
//    private Set itemEstrtFisHistIettfhs;
    
    
    private Date dataUltManutencao; 
    private UsuarioUsu usuarioUsuManutencao;
    private Boolean indExclusaoPosHistorico;
    private Integer mesIettf;
    private Integer anoIettf;
        

    /**
	 * @param codIettf the codIettf to set
	 */
	public void setCodIettf(Long codIettf) {
		this.codIettf = codIettf;
	}

	/**
	 * @return the codIettf
	 */
	public Long getCodIettf() {
		return codIettf;
	}

	/**
     *
     * @return
     */
    public Date getDataUltManutencao() {
		return dataUltManutencao;
	}

    /**
     *
     * @param dataUltManutencao
     */
    public void setDataUltManutencao(Date dataUltManutencao) {
		this.dataUltManutencao = dataUltManutencao;
	}

        /**
         *
         * @return
         */
        public UsuarioUsu getUsuarioUsuManutencao() {
		return usuarioUsuManutencao;
	}

        /**
         *
         * @param usuarioUsuManutencao
         */
        public void setUsuarioUsuManutencao(UsuarioUsu usuarioUsuManutencao) {
		this.usuarioUsuManutencao = usuarioUsuManutencao;
	}

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
        
    /* Mantis 0010128 - Qtd prevista não é mais informado por exercício
        /** full constructor
         * @param comp_id
         * @param indAtivoIettf
         * @param dataInclusaoIettf
         * @param exercicioExe
         * @param qtdPrevistaIettf
         * @param itemEstrtIndResulIettr
         */
/*        
    public ItemEstrutFisicoIettf(ecar.pojo.ItemEstrutFisicoIettfPK comp_id, String indAtivoIettf, Date dataInclusaoIettf, Double qtdPrevistaIettf, ecar.pojo.ItemEstrtIndResulIettr itemEstrtIndResulIettr, ecar.pojo.ExercicioExe exercicioExe) {
        this.comp_id = comp_id;
        this.indAtivoIettf = indAtivoIettf;
        this.dataInclusaoIettf = dataInclusaoIettf;
        this.qtdPrevistaIettf = qtdPrevistaIettf;
        this.itemEstrtIndResulIettr = itemEstrtIndResulIettr;
        this.exercicioExe = exercicioExe;
//        this.itemEstrtFisHistIettfhs = itemEstrtFisHistIettfhs;
    }
*/
        public ItemEstrutFisicoIettf(String indAtivoIettf, Date dataInclusaoIettf, Double qtdPrevistaIettf, ecar.pojo.ItemEstrtIndResulIettr itemEstrtIndResulIettr, Integer mesIettf, Integer anoIettf) {            
            this.indAtivoIettf = indAtivoIettf;
            this.dataInclusaoIettf = dataInclusaoIettf;
            this.qtdPrevistaIettf = qtdPrevistaIettf;
            this.itemEstrtIndResulIettr = itemEstrtIndResulIettr;
            this.mesIettf = mesIettf;
            this.anoIettf = anoIettf;
            //this.exercicioExe = exercicioExe;
//            this.itemEstrtFisHistIettfhs = itemEstrtFisHistIettfhs;
        }
        
    /** default constructor */
    public ItemEstrutFisicoIettf() {
    }

    
    /* Mantis 0010128 - Qtd prevista não é mais informado por exercício
    /** minimal constructor
     * @param comp_id
     */
/*    
    public ItemEstrutFisicoIettf(ecar.pojo.ItemEstrutFisicoIettfPK comp_id) {
        this.comp_id = comp_id;
  //      this.itemEstrtFisHistIettfhs = itemEstrtFisHistIettfhs;
    }
*/
    public ItemEstrutFisicoIettf(ecar.pojo.ItemEstrtIndResulIettr itemEstrtIndResulIettr ) {
    	this.itemEstrtIndResulIettr = itemEstrtIndResulIettr;
  //      this.itemEstrtFisHistIettfhs = itemEstrtFisHistIettfhs;
    }

 /* Mantis 0010128 - Qtd prevista não é mais informado por exercício    
    /**
     *
     * @return
     */
/*    
    public ecar.pojo.ItemEstrutFisicoIettfPK getComp_id() {
        return this.comp_id;
    }
*/
    
    /* Mantis 0010128 - Qtd prevista não é mais informado por exercício    
    /**
     *
     * @param comp_id
     */
/*    
    public void setComp_id(ecar.pojo.ItemEstrutFisicoIettfPK comp_id) {
        this.comp_id = comp_id;
    }
*/
    /**
     *
     * @return
     */
    public String getIndAtivoIettf() {
        return this.indAtivoIettf;
    }

    /**
     *
     * @param indAtivoIettf
     */
    public void setIndAtivoIettf(String indAtivoIettf) {
        this.indAtivoIettf = indAtivoIettf;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoIettf() {
        return this.dataInclusaoIettf;
    }

    /**
     *
     * @param dataInclusaoIettf
     */
    public void setDataInclusaoIettf(Date dataInclusaoIettf) {
        this.dataInclusaoIettf = dataInclusaoIettf;
    }

    /**
     *
     * @return
     */
    public Double getQtdPrevistaIettf() {
        return this.qtdPrevistaIettf;
    }

    /**
     *
     * @param qtdPrevistaIettf
     */
    public void setQtdPrevistaIettf(Double qtdPrevistaIettf) {
        this.qtdPrevistaIettf = qtdPrevistaIettf;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstrtIndResulIettr getItemEstrtIndResulIettr() {
        return this.itemEstrtIndResulIettr;
    }

    /**
     *
     * @param itemEstrtIndResulIettr
     */
    public void setItemEstrtIndResulIettr(ecar.pojo.ItemEstrtIndResulIettr itemEstrtIndResulIettr) {
        this.itemEstrtIndResulIettr = itemEstrtIndResulIettr;
    }

    /**
	 * @param mesIettf the mesIettf to set
	 */
	public void setMesIettf(Integer mesIettf) {
		this.mesIettf = mesIettf;
	}

	/**
	 * @return the mesIettf
	 */
	public Integer getMesIettf() {
		return mesIettf;
	}

	/**
	 * @param anoIettf the anoIettf to set
	 */
	public void setAnoIettf(Integer anoIettf) {
		this.anoIettf = anoIettf;
	}

	/**
	 * @return the anoIettf
	 */
	public Integer getAnoIettf() {
		return anoIettf;
	}

	
	/* Mantis 0010128 - Qtd prevista não é mais informado por exercício	
	/**
     *
     * @return
     */
	/*
    public ecar.pojo.ExercicioExe getExercicioExe() {
        return this.exercicioExe;
    }
    */
	
	/* Mantis 0010128 - Qtd prevista não é mais informado por exercício
    /**
     *
     * @param exercicioExe
     */
	/*
    public void setExercicioExe(ecar.pojo.ExercicioExe exercicioExe) {
        this.exercicioExe = exercicioExe;
    }
    */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getCodIettf())
            .toString();
    }
    
    /* Mantis 0010128 - Qtd prevista não é mais informado por exercício
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstrutFisicoIettf) ) return false;
        ItemEstrutFisicoIettf castOther = (ItemEstrutFisicoIettf) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }
    */
    
    @Override  
    public boolean equals(Object other) {        
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstrutFisicoIettf) ) return false;
        ItemEstrutFisicoIettf castOther = (ItemEstrutFisicoIettf) other;
        return new EqualsBuilder()
            .append(this.getCodIettf(), castOther.getCodIettf())
            .isEquals();        
        
    }    

    /* Mantis 0010128 - Qtd prevista não é mais informado por exercício          
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }
	*/
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIettf())
            .toHashCode();
    }
    
    /**
     *
     */
    /* Mantis 0010128 - Qtd prevista não é mais informado por exercício
    public void atribuirPK() {
        comp_id = new ItemEstrutFisicoIettfPK();        
        comp_id.setCodExe(this.getExercicioExe().getCodExe());
        comp_id.setCodIettir(this.getItemEstrtIndResulIettr().getCodIettir());
		
	}
	*/

}
