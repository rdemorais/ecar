package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 *
 * @author 70744416353
 */
public class EfItemEstRealizadoEfier implements Serializable {


	private static final long serialVersionUID = -1220306816865075840L;

	private Long codEfier;

	private Date dataInclusaoEfier;

	private String indManualEfier;

	private Double valor1Efier;

	private Double valor2Efier;

	private Double valor3Efier;

	private Double valor4Efier;

	private Double valor5Efier;

	private Double valor6Efier;

	private String contaSistemaOrcEfier;

	private Long anoReferenciaEfier;

	private Long mesReferenciaEfier;

	private Date dataHoraInfoEfier;

	private String indContabilidadeEfier;

	private ecar.pojo.UsuarioUsu usuarioUsu;
	
	/** COD_CSEFV - Foreign Key - Tabela <tb_config_sis_exec_finan_csefv> - NOT NULL */
	private ConfigSisExecFinanCsefv configSisExecFinanCsefv;

	/** COD_IMP - Foreign Key - <TB_Importacao_Imp> */
	private ImportacaoImp importacaoImp;
	
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
	
        /**
         *
         */
        public EfItemEstRealizadoEfier() {
		
	}

        /**
         *
         * @param codEfier
         * @param dataInclusaoEfier
         * @param indManualEfier
         * @param valor1Efier
         * @param valor2Efier
         * @param valor3Efier
         * @param valor4Efier
         * @param valor5Efier
         * @param valor6Efier
         * @param contaSistemaOrcEfier
         * @param anoReferenciaEfier
         * @param mesReferenciaEfier
         * @param dataHoraInfoEfier
         * @param indContabilidadeEfier
         * @param usuarioUsu
         * @param importacaoImp
         */
        public EfItemEstRealizadoEfier(Long codEfier, Date dataInclusaoEfier,
			String indManualEfier, Double valor1Efier, Double valor2Efier,
			Double valor3Efier, Double valor4Efier, Double valor5Efier,
			Double valor6Efier, String contaSistemaOrcEfier,
			Long anoReferenciaEfier, Long mesReferenciaEfier,
			Date dataHoraInfoEfier, String indContabilidadeEfier,
			UsuarioUsu usuarioUsu, ImportacaoImp importacaoImp) {
		super();
		this.codEfier = codEfier;
		this.dataInclusaoEfier = dataInclusaoEfier;
		this.indManualEfier = indManualEfier;
		this.valor1Efier = valor1Efier;
		this.valor2Efier = valor2Efier;
		this.valor3Efier = valor3Efier;
		this.valor4Efier = valor4Efier;
		this.valor5Efier = valor5Efier;
		this.valor6Efier = valor6Efier;
		this.contaSistemaOrcEfier = contaSistemaOrcEfier;
		this.anoReferenciaEfier = anoReferenciaEfier;
		this.mesReferenciaEfier = mesReferenciaEfier;
		this.dataHoraInfoEfier = dataHoraInfoEfier;
		this.indContabilidadeEfier = indContabilidadeEfier;
		this.usuarioUsu = usuarioUsu;
		this.importacaoImp = importacaoImp;
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
         * @param usuarioUsu
         */
        public EfItemEstRealizadoEfier(ecar.pojo.UsuarioUsu usuarioUsu) {
		this.usuarioUsu = usuarioUsu;
	}

        /**
         *
         * @return
         */
        public Long getCodEfier() {
		return this.codEfier;
	}

        /**
         *
         * @param codEfier
         */
        public void setCodEfier(Long codEfier) {
		this.codEfier = codEfier;
	}

        /**
         *
         * @return
         */
        public Date getDataInclusaoEfier() {
		return this.dataInclusaoEfier;
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
        public String getIndManualEfier() {
		return this.indManualEfier;
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
        public String getContaSistemaOrcEfier() {
		return this.contaSistemaOrcEfier;
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
        public Long getAnoReferenciaEfier() {
		return this.anoReferenciaEfier;
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
        public Long getMesReferenciaEfier() {
		return this.mesReferenciaEfier;
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

        /**
         *
         * @return
         */
        public Date getDataHoraInfoEfier() {
		return this.dataHoraInfoEfier;
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
        public String getIndContabilidadeEfier() {
		return this.indContabilidadeEfier;
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
        public ConfigSisExecFinanCsefv getConfigSisExecFinanCsefv() {
		return configSisExecFinanCsefv;
	}

        /**
         *
         * @param configSisExecFinanCsefv
         */
        public void setConfigSisExecFinanCsefv(
			ConfigSisExecFinanCsefv configSisExecFinanCsefv) {
		this.configSisExecFinanCsefv = configSisExecFinanCsefv;
	}	

    @Override
	public String toString() {
		return new ToStringBuilder(this).append("codEfier", getCodEfier())
				.toString();
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((codEfier == null) ? 0 : codEfier.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final EfItemEstRealizadoEfier other = (EfItemEstRealizadoEfier) obj;
		if (codEfier == null) {
			if (other.codEfier != null)
				return false;
		} else if (!codEfier.equals(other.codEfier))
			return false;
		return true;
	}
}
