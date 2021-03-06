package ecar.pojo;

import java.io.Serializable;

/**
 * TipoAcompTipofuncacompSisatributoTaTpfaSatb generated by MyEclipse
 * Persistence Tools
 */

public class TipoAcompTipofuncacompSisatributoTaTpfaSatb implements	Serializable {

	// Fields

	private TipoAcompTipofuncacompSisatributoTaTpfaSatbId id;
	private SisAtributoSatb sisAtributoSatb;
	private TipoFuncAcompTpfa tipoFuncAcompTpfa;
	private TipoAcompanhamentoTa tipoAcompanhamentoTa;
	private String indLeituraParecer;

	// Constructors

	/** default constructor */
	public TipoAcompTipofuncacompSisatributoTaTpfaSatb() {
	}

        /** minimal constructor
         * @param ta
         * @param tpfa
         * @param satb
         */
	public TipoAcompTipofuncacompSisatributoTaTpfaSatb(
			TipoAcompanhamentoTa ta,
			TipoFuncAcompTpfa tpfa,			
			SisAtributoSatb satb) {
		this.id = new TipoAcompTipofuncacompSisatributoTaTpfaSatbId(ta.getCodTa(),tpfa.getCodTpfa(),satb.getCodSatb());
		this.sisAtributoSatb = satb;
		this.tipoFuncAcompTpfa = tpfa;
		this.tipoAcompanhamentoTa = ta;
	}

        /** full constructor
         * @param ta
         * @param tpfa
         * @param satb
         * @param indLeituraParecer
         */
	public TipoAcompTipofuncacompSisatributoTaTpfaSatb(
			TipoAcompanhamentoTa ta,
			TipoFuncAcompTpfa tpfa,			
			SisAtributoSatb satb,
			String indLeituraParecer) {
		this.id = new TipoAcompTipofuncacompSisatributoTaTpfaSatbId(ta.getCodTa(),tpfa.getCodTpfa(),satb.getCodSatb());
		this.sisAtributoSatb = satb;
		this.tipoFuncAcompTpfa = tpfa;
		this.tipoAcompanhamentoTa = ta;
		this.indLeituraParecer = indLeituraParecer;
	}

	// Property accessors

        /**
         *
         * @return
         */
        public TipoAcompTipofuncacompSisatributoTaTpfaSatbId getId() {
		return this.id;
	}

        /**
         *
         * @param id
         */
        public void setId(TipoAcompTipofuncacompSisatributoTaTpfaSatbId id) {
		this.id = id;
	}

        /**
         *
         * @return
         */
        public SisAtributoSatb getSisAtributoSatb() {
		return this.sisAtributoSatb;
	}

        /**
         *
         * @param sisAtributoSatb
         */
        public void setSisAtributoSatb(SisAtributoSatb sisAtributoSatb) {
		this.sisAtributoSatb = sisAtributoSatb;
	}

        /**
         *
         * @return
         */
        public TipoFuncAcompTpfa getTipoFuncAcompTpfa() {
		return this.tipoFuncAcompTpfa;
	}

        /**
         *
         * @param tipoFuncAcompTpfa
         */
        public void setTipoFuncAcompTpfa(TipoFuncAcompTpfa tipoFuncAcompTpfa) {
		this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
	}

        /**
         *
         * @return
         */
        public TipoAcompanhamentoTa getTipoAcompanhamentoTa() {
		return this.tipoAcompanhamentoTa;
	}

        /**
         *
         * @param tipoAcompanhamentoTa
         */
        public void setTipoAcompanhamentoTa(
			TipoAcompanhamentoTa tipoAcompanhamentoTa) {
		this.tipoAcompanhamentoTa = tipoAcompanhamentoTa;
	}

        /**
         *
         * @return
         */
        public String getIndLeituraParecer() {
		return this.indLeituraParecer;
	}

        /**
         *
         * @param indLeituraParecer
         */
        public void setIndLeituraParecer(String indLeituraParecer) {
		this.indLeituraParecer = indLeituraParecer;
	}

}