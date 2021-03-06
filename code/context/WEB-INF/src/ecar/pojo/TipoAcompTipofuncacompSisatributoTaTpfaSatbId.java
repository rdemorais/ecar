package ecar.pojo;

import java.io.Serializable;

/**
 * TipoAcompTipofuncacompSisatributoTaTpfaSatbId generated by MyEclipse
 * Persistence Tools
 */

public class TipoAcompTipofuncacompSisatributoTaTpfaSatbId implements Serializable {

	// Fields

	private Long codTa;
	private Long codTpfa;
	private Long codSatb;

	// Constructors

	/** default constructor */
	public TipoAcompTipofuncacompSisatributoTaTpfaSatbId() {
	}

        /** full constructor
         * @param codTa
         * @param codTpfa
         * @param codSatb
         */
	public TipoAcompTipofuncacompSisatributoTaTpfaSatbId(Long codTa,
			Long codTpfa, Long codSatb) {
		this.codTa = codTa;
		this.codTpfa = codTpfa;
		this.codSatb = codSatb;
	}

	// Property accessors

        /**
         *
         * @return
         */
        public Long getCodTa() {
		return this.codTa;
	}

        /**
         *
         * @param codTa
         */
        public void setCodTa(Long codTa) {
		this.codTa = codTa;
	}

        /**
         *
         * @return
         */
        public Long getCodTpfa() {
		return this.codTpfa;
	}

        /**
         *
         * @param codTpfa
         */
        public void setCodTpfa(Long codTpfa) {
		this.codTpfa = codTpfa;
	}

        /**
         *
         * @return
         */
        public Long getCodSatb() {
		return this.codSatb;
	}

        /**
         *
         * @param codSatb
         */
        public void setCodSatb(Long codSatb) {
		this.codSatb = codSatb;
	}

    @Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TipoAcompTipofuncacompSisatributoTaTpfaSatbId))
			return false;
		TipoAcompTipofuncacompSisatributoTaTpfaSatbId castOther = (TipoAcompTipofuncacompSisatributoTaTpfaSatbId) other;

		return ((this.getCodTa() == castOther.getCodTa()) || (this.getCodTa() != null
				&& castOther.getCodTa() != null && this.getCodTa().equals(
				castOther.getCodTa())))
				&& ((this.getCodTpfa() == castOther.getCodTpfa()) || (this
						.getCodTpfa() != null
						&& castOther.getCodTpfa() != null && this.getCodTpfa()
						.equals(castOther.getCodTpfa())))
				&& ((this.getCodSatb() == castOther.getCodSatb()) || (this
						.getCodSatb() != null
						&& castOther.getCodSatb() != null && this.getCodSatb()
						.equals(castOther.getCodSatb())));
	}

    @Override
	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCodTa() == null ? 0 : this.getCodTa().hashCode());
		result = 37 * result
				+ (getCodTpfa() == null ? 0 : this.getCodTpfa().hashCode());
		result = 37 * result
				+ (getCodSatb() == null ? 0 : this.getCodSatb().hashCode());
		return result;
	}

}