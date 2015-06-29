package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author rogerio
 * @since 0.1, 14/05/2007
 * @version 0.1, 14/05/2007
 */
public class HistoricoIettrH implements Serializable {

	private static final long serialVersionUID = 1196157942420950441L;

	/** cod_efiech - Chave Primaria (PK) */
	private Long codIettrH;

    /** COD_IETT - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
    private ItemEstruturaIett itemEstruturaIett;

    /** nullable persistent field */
    private String unidMedidaIettr;

    /** nullable persistent field */
    // Unidade de medida usado caso seja um combo. Neste caso guarda o código
    private ecar.pojo.SisAtributoSatb codUnidMedidaIettr;

    /** nullable persistent field */
    private String descricaoIettir;

    /** nullable persistent field */
    private String nomeIettir;

    /** nullable persistent field */
    private String indProjecaoIettr;

    /** nullable persistent field */
    private String indAcumulavelIettr;

    /** persistent field */
    private Set acompRealFisicoArfs;

    /** persistent field */
    private Set itemEstrutFisicoIettfs;

    /** nullable persistent field */
    private String indTipoQtde;

    /** nullable persistent field */
    private String indValorFinalIettr;
    
    private String indRealPorLocal;
    
    // Mantis #0011576
    private String indPrevPorLocal;
    
    private Set iettIndResulRevIettrrs;
    
    /** persistent field */
    private ecar.pojo.SisAtributoSatb sisAtributoSatb;
    
    private ecar.pojo.PeriodicidadePrdc periodicidadePrdc;
    
    private String fonteIettr;
    
    /* Mantis #2156 */
    private Set historicoIettfHs;

    private String formulaIettr;

    private Double indiceMaisRecenteIettr;
    
    private Date dataApuracaoIettr;
    
    private Date dataUltManutencao;
    
    private UsuarioUsu usuarioUsuManutencao;
    
    private Set itemEstrtIndResulLocalIettirls;

    /** nullable persistent field */
    private String indPublicoIettr;
    
    
    /** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;	
	
	
    /**
     *
     */
    public HistoricoIettrH() {
	}

    /**
     *
     * @return
     */
    public Set getAcompRealFisicoArfs() {
		return acompRealFisicoArfs;
	}


    /**
     *
     * @param acompRealFisicoArfs
     */
    public void setAcompRealFisicoArfs(Set acompRealFisicoArfs) {
		this.acompRealFisicoArfs = acompRealFisicoArfs;
	}


    /**
     *
     * @return
     */
    public Long getCodIettrH() {
		return codIettrH;
	}


        /**
         *
         * @param codIettrH
         */
        public void setCodIettrH(Long codIettrH) {
		this.codIettrH = codIettrH;
	}


        /**
         *
         * @return
         */
        public Date getDataApuracaoIettr() {
		return dataApuracaoIettr;
	}


        /**
         *
         * @param dataApuracaoIettr
         */
        public void setDataApuracaoIettr(Date dataApuracaoIettr) {
		this.dataApuracaoIettr = dataApuracaoIettr;
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
        public String getDescricaoIettir() {
		return descricaoIettir;
	}


        /**
         *
         * @param descricaoIettir
         */
        public void setDescricaoIettir(String descricaoIettir) {
		this.descricaoIettir = descricaoIettir;
	}


        /**
         *
         * @return
         */
        public String getFonteIettr() {
		return fonteIettr;
	}


        /**
         *
         * @param fonteIettr
         */
        public void setFonteIettr(String fonteIettr) {
		this.fonteIettr = fonteIettr;
	}


        /**
         *
         * @return
         */
        public String getFormulaIettr() {
		return formulaIettr;
	}


        /**
         *
         * @param formulaIettr
         */
        public void setFormulaIettr(String formulaIettr) {
		this.formulaIettr = formulaIettr;
	}


        /**
         *
         * @return
         */
        public Set getHistoricoIettfHs() {
		return historicoIettfHs;
	}


        /**
         *
         * @param historicoIettfHs
         */
        public void setHistoricoIettfHs(Set historicoIettfHs) {
		this.historicoIettfHs = historicoIettfHs;
	}


        /**
         *
         * @return
         */
        public Set getIettIndResulRevIettrrs() {
		return iettIndResulRevIettrrs;
	}


        /**
         *
         * @param iettIndResulRevIettrrs
         */
        public void setIettIndResulRevIettrrs(Set iettIndResulRevIettrrs) {
		this.iettIndResulRevIettrrs = iettIndResulRevIettrrs;
	}


        /**
         *
         * @return
         */
        public String getIndAcumulavelIettr() {
		return indAcumulavelIettr;
	}


        /**
         *
         * @param indPublicoIettr
         */
        public void setIndPublicoIettr(String indPublicoIettr) {
		this.indPublicoIettr = indPublicoIettr;
	}

        /**
         *
         * @return
         */
        public String getIndPublicoIettr() {
		return indPublicoIettr;
	}


        /**
         *
         * @param indAcumulavelIettr
         */
        public void setIndAcumulavelIettr(String indAcumulavelIettr) {
		this.indAcumulavelIettr = indAcumulavelIettr;
	}

        /**
         *
         * @return
         */
        public Double getIndiceMaisRecenteIettr() {
		return indiceMaisRecenteIettr;
	}


        /**
         *
         * @param indiceMaisRecenteIettr
         */
        public void setIndiceMaisRecenteIettr(Double indiceMaisRecenteIettr) {
		this.indiceMaisRecenteIettr = indiceMaisRecenteIettr;
	}


        /**
         *
         * @return
         */
        public String getIndProjecaoIettr() {
		return indProjecaoIettr;
	}


        /**
         *
         * @param indProjecaoIettr
         */
        public void setIndProjecaoIettr(String indProjecaoIettr) {
		this.indProjecaoIettr = indProjecaoIettr;
	}


        /**
         *
         * @return
         */
        public String getIndRealPorLocal() {
		return indRealPorLocal;
	}


        /**
         *
         * @param indRealPorLocal
         */
        public void setIndRealPorLocal(String indRealPorLocal) {
		this.indRealPorLocal = indRealPorLocal;
	}


        /**
         *
         * @return
         */
        public String getIndTipoQtde() {
		return indTipoQtde;
	}


        /**
         *
         * @param indTipoQtde
         */
        public void setIndTipoQtde(String indTipoQtde) {
		this.indTipoQtde = indTipoQtde;
	}


        /**
         *
         * @return
         */
        public String getIndValorFinalIettr() {
		return indValorFinalIettr;
	}


        /**
         *
         * @param indValorFinalIettr
         */
        public void setIndValorFinalIettr(String indValorFinalIettr) {
		this.indValorFinalIettr = indValorFinalIettr;
	}


        /**
         *
         * @return
         */
        public Set getItemEstrtIndResulLocalIettirls() {
		return itemEstrtIndResulLocalIettirls;
	}


        /**
         *
         * @param itemEstrtIndResulLocalIettirls
         */
        public void setItemEstrtIndResulLocalIettirls(Set itemEstrtIndResulLocalIettirls) {
		this.itemEstrtIndResulLocalIettirls = itemEstrtIndResulLocalIettirls;
	}


        /**
         *
         * @return
         */
        public Set getItemEstrutFisicoIettfs() {
		return itemEstrutFisicoIettfs;
	}


        /**
         *
         * @param itemEstrutFisicoIettfs
         */
        public void setItemEstrutFisicoIettfs(Set itemEstrutFisicoIettfs) {
		this.itemEstrutFisicoIettfs = itemEstrutFisicoIettfs;
	}


        /**
         *
         * @return
         */
        public ItemEstruturaIett getItemEstruturaIett() {
		return itemEstruturaIett;
	}


        /**
         *
         * @param itemEstruturaIett
         */
        public void setItemEstruturaIett(ItemEstruturaIett itemEstruturaIett) {
		this.itemEstruturaIett = itemEstruturaIett;
	}


        /**
         *
         * @return
         */
        public String getNomeIettir() {
		return nomeIettir;
	}


        /**
         *
         * @param nomeIettir
         */
        public void setNomeIettir(String nomeIettir) {
		this.nomeIettir = nomeIettir;
	}


        /**
         *
         * @return
         */
        public ecar.pojo.PeriodicidadePrdc getPeriodicidadePrdc() {
		return periodicidadePrdc;
	}


        /**
         *
         * @param periodicidadePrdc
         */
        public void setPeriodicidadePrdc(ecar.pojo.PeriodicidadePrdc periodicidadePrdc) {
		this.periodicidadePrdc = periodicidadePrdc;
	}


        /**
         *
         * @return
         */
        public ecar.pojo.SisAtributoSatb getSisAtributoSatb() {
		return sisAtributoSatb;
	}


        /**
         *
         * @param sisAtributoSatb
         */
        public void setSisAtributoSatb(ecar.pojo.SisAtributoSatb sisAtributoSatb) {
		this.sisAtributoSatb = sisAtributoSatb;
	}


        /**
         *
         * @return
         */
        public String getUnidMedidaIettr() {
		return unidMedidaIettr;
	}


        /**
         *
         * @param unidMedidaIettr
         */
        public void setUnidMedidaIettr(String unidMedidaIettr) {
		this.unidMedidaIettr = unidMedidaIettr;
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
        public HistoricoMaster getHistoricoMaster() {
		return historicoMaster;
	}

        /**
         *
         * @param historicoMaster
         */
        public void setHistoricoMaster(HistoricoMaster historicoMaster) {
		this.historicoMaster = historicoMaster;
	}

        /**
         *
         * @return
         */
        public String getIndPrevPorLocal() {
		return indPrevPorLocal;
	}

        /**
         *
         * @param indPrevPorLocal
         */
        public void setIndPrevPorLocal(String indPrevPorLocal) {
		this.indPrevPorLocal = indPrevPorLocal;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.SisAtributoSatb getCodUnidMedidaIettr() {
		return codUnidMedidaIettr;
	}

        /**
         *
         * @param codUnidMedidaIettr
         */
        public void setCodUnidMedidaIettr(ecar.pojo.SisAtributoSatb codUnidMedidaIettr) {
		this.codUnidMedidaIettr = codUnidMedidaIettr;
	}

}
