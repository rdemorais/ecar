package ecar.pojo;
// Generated 03/08/2006 10:18:55 by J-Querena using Hibernate Tools 3.1.0.beta5


import java.util.HashSet;
import java.util.Set;

/**
 * ModeloRelatorioMrel generated by hbm2java
 */
public class ModeloRelatorioMrel  implements java.io.Serializable {

    // Fields    

     private int codMrel;
     private String descricaoMrel;
     private String classifMrel;
     private String codAlfaMrel;
     private Set configRelatorioCfgrels = new HashSet(0);

     // Constructors

    /** default constructor */
    public ModeloRelatorioMrel() {
    }

    /** minimal constructor
     * @param codMrel
     */
    public ModeloRelatorioMrel(int codMrel) {
        this.codMrel = codMrel;
    }
    /** full constructor
     * @param codMrel
     * @param descricaoMrel
     * @param classifMrel
     * @param codAlfaMrel
     * @param configRelatorioCfgrels
     */
    public ModeloRelatorioMrel(int codMrel, String descricaoMrel, String classifMrel, String codAlfaMrel, Set configRelatorioCfgrels) {
       this.codMrel = codMrel;
       this.descricaoMrel = descricaoMrel;
       this.classifMrel = classifMrel;
       this.codAlfaMrel = codAlfaMrel;
       this.configRelatorioCfgrels = configRelatorioCfgrels;
    }
    
   
    // Property accessors
    /**
     *
     * @return
     */
    public int getCodMrel() {
        return this.codMrel;
    }
    
    /**
     *
     * @param codMrel
     */
    public void setCodMrel(int codMrel) {
        this.codMrel = codMrel;
    }
    /**
     *
     * @return
     */
    public String getDescricaoMrel() {
        return this.descricaoMrel;
    }
    
    /**
     *
     * @param descricaoMrel
     */
    public void setDescricaoMrel(String descricaoMrel) {
        this.descricaoMrel = descricaoMrel;
    }
    /**
     *
     * @return
     */
    public String getClassifMrel() {
        return this.classifMrel;
    }
    
    /**
     *
     * @param classifMrel
     */
    public void setClassifMrel(String classifMrel) {
        this.classifMrel = classifMrel;
    }
    /**
     *
     * @return
     */
    public String getCodAlfaMrel() {
        return this.codAlfaMrel;
    }
    
    /**
     *
     * @param codAlfaMrel
     */
    public void setCodAlfaMrel(String codAlfaMrel) {
        this.codAlfaMrel = codAlfaMrel;
    }
    /**
     *
     * @return
     */
    public Set getConfigRelatorioCfgrels() {
        return this.configRelatorioCfgrels;
    }
    
    /**
     *
     * @param configRelatorioCfgrels
     */
    public void setConfigRelatorioCfgrels(Set configRelatorioCfgrels) {
        this.configRelatorioCfgrels = configRelatorioCfgrels;
    }




}


