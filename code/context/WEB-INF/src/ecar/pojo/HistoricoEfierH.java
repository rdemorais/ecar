package ecar.pojo;

import java.io.Serializable;
import java.util.Date;


/** 
 * Classe responsavel para armazenar dados da tabela tb_historico_efierh
 * - Modificado para comportar historico
 * 
 * @author rogerio, gabriel
 * @since 0.1, 15/05/2007
 * @version 0.2, 24/05/2007
 */
public class HistoricoEfierH implements Serializable {


	private static final long serialVersionUID = -42349881302362716L;
	
	/** cod_efierh - Chave Primaria (PK) */
	private Long codEfierH;

	/** data_inclusao_efier */
	private Date dataInclusaoEfier;
	
	/** ind_manual_efier */
	private String indManualEfier;
	
	/** conta_sistema_orc_efier */
	private String contaSistemaOrcEfier;	
	
	/** ano_referencia_efier */
	private Long anoReferenciaEfier;
	
	/** mes_referencia_efier */
	private Long mesReferenciaEfier;	
	
	/** data_hora_info_efier */
    private Date dataHoraInfoEfier;

    /** ind_contabilidade_efier */
    private String indContabilidadeEfier;
    
    /** valor_1_efier */
	private Double valor1Efier;
	
	/** valor_2_efier */
	private Double valor2Efier;
	
	/** valor_3_efier */
	private Double valor3Efier;
	
	/** valor_4_efier */
	private Double valor4Efier;
	
	/** valor_5_efier */	
	private Double valor5Efier;
	
	/** valor_6_efier */	
	private Double valor6Efier;

    /** COD_IMP - Chave estrangeira (FK) - Tabela <TB_IMPORTACAO_IMP> */    
    private ImportacaoImp importacaoImp;	
	
    /** cod_efier  */
	private Long codEfier;
	
    /** COD_USU - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
    private UsuarioUsu usuarioUsu;	
	
    /** COD_USU_MANUTENCAO - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
    private UsuarioUsu usuManutencao;    
    
    /** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;	
	
    /**
     *
     */
    public HistoricoEfierH() {}

    /**
     *
     * @return
     */
    public Long getAnoReferenciaEfier() {
		return anoReferenciaEfier;
	}

        /**
         *
         * @param anoReferenciaEfier
         */
        public void setAnoReferenciaEfier(Long anoReferenciaEfier) {
		this.anoReferenciaEfier = anoReferenciaEfier;
	}

        /**
         *
         * @return
         */
        public Long getCodEfierH() {
		return codEfierH;
	}

        /**
         *
         * @param codEfierH
         */
        public void setCodEfierH(Long codEfierH) {
		this.codEfierH = codEfierH;
	}

        /**
         *
         * @return
         */
        public ImportacaoImp getImportacaoImp() {
		return importacaoImp;
	}

        /**
         *
         * @param importacaoImp
         */
        public void setImportacaoImp(ImportacaoImp importacaoImp) {
		this.importacaoImp = importacaoImp;
	}

        /**
         *
         * @return
         */
        public String getContaSistemaOrcEfier() {
		return contaSistemaOrcEfier;
	}

        /**
         *
         * @param contaSistemaOrcEfier
         */
        public void setContaSistemaOrcEfier(String contaSistemaOrcEfier) {
		this.contaSistemaOrcEfier = contaSistemaOrcEfier;
	}

        /**
         *
         * @return
         */
        public Date getDataHoraInfoEfier() {
		return dataHoraInfoEfier;
	}

        /**
         *
         * @param dataHoraInfoEfier
         */
        public void setDataHoraInfoEfier(Date dataHoraInfoEfier) {
		this.dataHoraInfoEfier = dataHoraInfoEfier;
	}

        /**
         *
         * @return
         */
        public Date getDataInclusaoEfier() {
		return dataInclusaoEfier;
	}

        /**
         *
         * @param dataInclusaoEfier
         */
        public void setDataInclusaoEfier(Date dataInclusaoEfier) {
		this.dataInclusaoEfier = dataInclusaoEfier;
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
        public String getIndContabilidadeEfier() {
		return indContabilidadeEfier;
	}

        /**
         *
         * @param indContabilidadeEfier
         */
        public void setIndContabilidadeEfier(String indContabilidadeEfier) {
		this.indContabilidadeEfier = indContabilidadeEfier;
	}

        /**
         *
         * @return
         */
        public String getIndManualEfier() {
		return indManualEfier;
	}

        /**
         *
         * @param indManualEfier
         */
        public void setIndManualEfier(String indManualEfier) {
		this.indManualEfier = indManualEfier;
	}

        /**
         *
         * @return
         */
        public Long getMesReferenciaEfier() {
		return mesReferenciaEfier;
	}

        /**
         *
         * @param mesReferenciaEfier
         */
        public void setMesReferenciaEfier(Long mesReferenciaEfier) {
		this.mesReferenciaEfier = mesReferenciaEfier;
	}

        /**
         *
         * @return
         */
        public UsuarioUsu getUsuarioUsu() {
		return usuarioUsu;
	}

        /**
         *
         * @param usuarioUsu
         */
        public void setUsuarioUsu(UsuarioUsu usuarioUsu) {
		this.usuarioUsu = usuarioUsu;
	}

        /**
         *
         * @return
         */
        public UsuarioUsu getUsuManutencao() {
		return usuManutencao;
	}

        /**
         *
         * @param usuManutencao
         */
        public void setUsuManutencao(UsuarioUsu usuManutencao) {
		this.usuManutencao = usuManutencao;
	}

        /**
         *
         * @return
         */
        public Double getValor1Efier() {
		return valor1Efier;
	}

        /**
         *
         * @param valor1Efier
         */
        public void setValor1Efier(Double valor1Efier) {
		this.valor1Efier = valor1Efier;
	}

        /**
         *
         * @return
         */
        public Double getValor2Efier() {
		return valor2Efier;
	}

        /**
         *
         * @param valor2Efier
         */
        public void setValor2Efier(Double valor2Efier) {
		this.valor2Efier = valor2Efier;
	}

        /**
         *
         * @return
         */
        public Double getValor3Efier() {
		return valor3Efier;
	}

        /**
         *
         * @param valor3Efier
         */
        public void setValor3Efier(Double valor3Efier) {
		this.valor3Efier = valor3Efier;
	}

        /**
         *
         * @return
         */
        public Double getValor4Efier() {
		return valor4Efier;
	}

        /**
         *
         * @param valor4Efier
         */
        public void setValor4Efier(Double valor4Efier) {
		this.valor4Efier = valor4Efier;
	}

        /**
         *
         * @return
         */
        public Double getValor5Efier() {
		return valor5Efier;
	}

        /**
         *
         * @param valor5Efier
         */
        public void setValor5Efier(Double valor5Efier) {
		this.valor5Efier = valor5Efier;
	}

        /**
         *
         * @return
         */
        public Double getValor6Efier() {
		return valor6Efier;
	}

        /**
         *
         * @param valor6Efier
         */
        public void setValor6Efier(Double valor6Efier) {
		this.valor6Efier = valor6Efier;
	}

        /**
         *
         * @return
         */
        public Long getCodEfier() {
		return codEfier;
	}

        /**
         *
         * @param codEfier
         */
        public void setCodEfier(Long codEfier) {
		this.codEfier = codEfier;
	}
    
    
    
}
