package ecar.pojo.historico;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.HistoricoIettfH;
import ecar.pojo.IettIndResulRevIettrr;
import ecar.pojo.ItemEstrtIndResulCorIettrcor;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrtIndResulLocalIettirl;
import ecar.pojo.ItemEstrutFisicoIettf;
import ecar.pojo.ServicoSer;
import ecar.pojo.UsuarioUsu;


/**
 *
 * @author 70744416353
 */
public class HistoricoItemEstrtIndResulIettr implements Serializable, IHistorico {

	private static final long serialVersionUID = 1535244703426341533L;
	
	private Set<AcompRealFisicoArf> acompRealFisicoArfs;
	
	private Long idObjetoSerializado;
	
	private Long idHistorico;
	
	private Long codIettir;
	
	private ecar.pojo.SisAtributoSatb codUnidMedidaIettr;
	
	private Date dataApuracaoIettr;
	
	private Date dataHistorico;
	
	private Date dataUltManutencao;
	
	private String descricaoIettir;
	
	private String fonteIettr;
	
	private String formulaIettr;
	
	private String historico;
	
	private Set<HistoricoIettfH> historicoIettfHs;
	
	private Set<IettIndResulRevIettrr> iettIndResulRevIettrrs;
	
	private String indAcumulavelIettr;
	
	private String indAtivoIettr;
	
	private Boolean indExclusaoPosHistorico;
	
	private Double indiceMaisRecenteIettr;
	
	private String indPrevPorLocal;
	
	private String indProjecaoIettr;
	
	private String indPublicoIettr;
	
	private String indRealPorLocal;
	
	private String indSinalizacaoIettr;
	
	private String indTipoAtualizacaoPrevisto;
	
	private String indTipoAtualizacaoRealizado;
	
	private String indTipoQtde;
	
	private String indValorFinalIettr;
	
	private Set<ItemEstrtIndResulCorIettrcor> itemEstrtIndResulCorIettrcores;
	
	private Set<ItemEstrtIndResulLocalIettirl> itemEstrtIndResulLocalIettirls;
	
	private Set<ItemEstrutFisicoIettf> itemEstrutFisicoIettfs;
	
	private ecar.pojo.ItemEstruturaIett itemEstruturaIett;
	
	private ecar.pojo.ItemEstruturaIettPPA itemEstruturaIettPPA;
	
	private String labelGraficoGrupoIettir;
	
	private String nomeIettir;
	
	private ecar.pojo.PeriodicidadePrdc periodicidadePrdc;
	
	private ServicoSer previstoServicoSer;
	
	private ServicoSer realizadoServicoSer;
	
	private ecar.pojo.SisAtributoSatb sisAtributoSatb;
	
	private Long tipoHistorico;
	
	private String unidMedidaIettr;
	
	private UsuarioUsu usuarioUsu;
	
	private UsuarioUsu usuarioUsuManutencao;

        /**
         *
         */
        public HistoricoItemEstrtIndResulIettr() {
	}

        /**
         *
         * @param objPai
         */
        public void carregar(Object objPai) {

		ItemEstrtIndResulIettr obj = (ItemEstrtIndResulIettr) objPai;
		
		//ecar.historico.Historico.inicializaHibernate(obj);
		
		this.codIettir = obj.getCodIettir();
		this.idObjetoSerializado = obj.getCodIettir();
		this.unidMedidaIettr = obj.getUnidMedidaIettr();
		this.codUnidMedidaIettr = obj.getCodUnidMedidaIettr();
		this.descricaoIettir = obj.getDescricaoIettir();
		this.nomeIettir = obj.getNomeIettir();
		this.indProjecaoIettr = obj.getIndProjecaoIettr();
		this.indAcumulavelIettr = obj.getIndAcumulavelIettr();
		this.itemEstruturaIett = obj.getItemEstruturaIett();
		this.acompRealFisicoArfs = obj.getAcompRealFisicoArfs();
		this.itemEstrutFisicoIettfs = obj.getItemEstrutFisicoIettfs();
		this.indTipoQtde = obj.getIndTipoQtde();
		this.indValorFinalIettr = obj.getIndValorFinalIettr();
		this.indRealPorLocal = obj.getIndRealPorLocal();
		this.iettIndResulRevIettrrs = obj.getIettIndResulRevIettrrs();
		this.sisAtributoSatb = obj.getSisAtributoSatb();
		this.periodicidadePrdc = obj.getPeriodicidadePrdc();
		this.fonteIettr = obj.getFonteIettr();
		this.historicoIettfHs = obj.getHistoricoIettfHs();
		this.indExclusaoPosHistorico = obj.getIndExclusaoPosHistorico();
		this.formulaIettr = obj.getFormulaIettr();
		this.indiceMaisRecenteIettr = obj.getIndiceMaisRecenteIettr();
		this.dataApuracaoIettr = obj.getDataApuracaoIettr();
		this.dataUltManutencao = obj.getDataUltManutencao();
		this.usuarioUsuManutencao = obj.getUsuarioUsuManutencao();
		this.indAtivoIettr = obj.getIndAtivoIettr();
		this.indPublicoIettr = obj.getIndPublicoIettr();
		this.labelGraficoGrupoIettir = obj.getLabelGraficoGrupoIettir();
		this.indSinalizacaoIettr = obj.getIndSinalizacaoIettr();
		this.indPrevPorLocal = obj.getIndPrevPorLocal();
		this.itemEstruturaIettPPA = obj.getItemEstruturaIettPPA();
		this.itemEstrtIndResulCorIettrcores = obj.getItemEstrtIndResulCorIettrcores();
		this.indTipoAtualizacaoPrevisto = obj.getIndTipoAtualizacaoPrevisto();
		this.indTipoAtualizacaoRealizado = obj.getIndTipoAtualizacaoRealizado();
		this.realizadoServicoSer = obj.getRealizadoServicoSer();
		this.previstoServicoSer = obj.getPrevistoServicoSer();

	}

        /**
         *
         * @return
         */
        public Object descarregar() {

		ItemEstrtIndResulIettr iett = new ItemEstrtIndResulIettr();

		iett.setCodIettir(this.getCodIettir());
		iett.setUnidMedidaIettr(this.getUnidMedidaIettr());
		iett.setCodUnidMedidaIettr(this.getCodUnidMedidaIettr());
		iett.setDescricaoIettir(this.getDescricaoIettir());
		iett.setNomeIettir(this.getNomeIettir());
		iett.setIndProjecaoIettr(this.getIndProjecaoIettr());
		iett.setIndAcumulavelIettr(this.getIndAcumulavelIettr());
		iett.setItemEstruturaIett(this.getItemEstruturaIett());
		iett.setAcompRealFisicoArfs(this.getAcompRealFisicoArfs());
		iett.setItemEstrutFisicoIettfs(this.getItemEstrutFisicoIettfs());
		iett.setIndTipoQtde(this.getIndTipoQtde());
		iett.setIndValorFinalIettr(this.getIndValorFinalIettr());
		iett.setIndRealPorLocal(this.getIndRealPorLocal());
		iett.setIettIndResulRevIettrrs(this.getIettIndResulRevIettrrs());
		iett.setSisAtributoSatb(this.getSisAtributoSatb());
		iett.setPeriodicidadePrdc(this.getPeriodicidadePrdc());
		iett.setFonteIettr(this.getFonteIettr());
		iett.setHistoricoIettfHs(this.getHistoricoIettfHs());
		iett.setIndExclusaoPosHistorico(this.getIndExclusaoPosHistorico());
		iett.setFormulaIettr(this.getFormulaIettr());
		iett.setIndiceMaisRecenteIettr(this.getIndiceMaisRecenteIettr());
		iett.setDataApuracaoIettr(this.getDataApuracaoIettr());
		iett.setDataUltManutencao(this.getDataUltManutencao());
		iett.setUsuarioUsuManutencao(this.getUsuarioUsuManutencao());
		iett.setIndAtivoIettr(this.getIndAtivoIettr());
		iett.setIndPublicoIettr(this.getIndPublicoIettr());
		iett.setLabelGraficoGrupoIettir(this.getLabelGraficoGrupoIettir());
		iett.setIndSinalizacaoIettr(this.getIndSinalizacaoIettr());
		iett.setIndPrevPorLocal(this.getIndPrevPorLocal());
		iett.setItemEstruturaIettPPA(this.getItemEstruturaIettPPA());
		iett.setItemEstrtIndResulCorIettrcores(this.getItemEstrtIndResulCorIettrcores());
		iett.setIndTipoAtualizacaoPrevisto(this.getIndTipoAtualizacaoPrevisto());
		iett.setIndTipoAtualizacaoRealizado(this.getIndTipoAtualizacaoRealizado());
		iett.setRealizadoServicoSer(this.getRealizadoServicoSer());
		iett.setPrevistoServicoSer(this.getPrevistoServicoSer());

		return iett;
	}



	
        /**
         *
         * @param obj
         * @return
         */
        public boolean equalsOrigem(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof HistoricoItemEstrtIndResulIettr))
			return false;
		HistoricoItemEstrtIndResulIettr other = (HistoricoItemEstrtIndResulIettr) obj;
		if (acompRealFisicoArfs == null) {
			if (other.acompRealFisicoArfs != null)
				return false;
		} else if (!acompRealFisicoArfs.equals(other.acompRealFisicoArfs))
			return false;
		if (codIettir == null) {
			if (other.codIettir != null)
				return false;
		} else if (!codIettir.equals(other.codIettir))
			return false;
		if (codUnidMedidaIettr == null) {
			if (other.codUnidMedidaIettr != null)
				return false;
		} else if (!codUnidMedidaIettr.equals(other.codUnidMedidaIettr))
			return false;
		if (dataApuracaoIettr == null) {
			if (other.dataApuracaoIettr != null)
				return false;
		} else if (!dataApuracaoIettr.equals(other.dataApuracaoIettr))
			return false;
		if (dataHistorico == null) {
			if (other.dataHistorico != null)
				return false;
		} else if (!dataHistorico.equals(other.dataHistorico))
			return false;
		if (dataUltManutencao == null) {
			if (other.dataUltManutencao != null)
				return false;
		} else if (!dataUltManutencao.equals(other.dataUltManutencao))
			return false;
		if (descricaoIettir == null) {
			if (other.descricaoIettir != null)
				return false;
		} else if (!descricaoIettir.equals(other.descricaoIettir))
			return false;
		if (fonteIettr == null) {
			if (other.fonteIettr != null)
				return false;
		} else if (!fonteIettr.equals(other.fonteIettr))
			return false;
		if (formulaIettr == null) {
			if (other.formulaIettr != null)
				return false;
		} else if (!formulaIettr.equals(other.formulaIettr))
			return false;
		if (historico == null) {
			if (other.historico != null)
				return false;
		} else if (!historico.equals(other.historico))
			return false;
		if (historicoIettfHs == null) {
			if (other.historicoIettfHs != null)
				return false;
		} else if (!historicoIettfHs.equals(other.historicoIettfHs))
			return false;
		if (iettIndResulRevIettrrs == null) {
			if (other.iettIndResulRevIettrrs != null)
				return false;
		} else if (!iettIndResulRevIettrrs.equals(other.iettIndResulRevIettrrs))
			return false;
		if (indAcumulavelIettr == null) {
			if (other.indAcumulavelIettr != null)
				return false;
		} else if (!indAcumulavelIettr.equals(other.indAcumulavelIettr))
			return false;
		if (indAtivoIettr == null) {
			if (other.indAtivoIettr != null)
				return false;
		} else if (!indAtivoIettr.equals(other.indAtivoIettr))
			return false;
		if (indExclusaoPosHistorico == null) {
			if (other.indExclusaoPosHistorico != null)
				return false;
		} else if (!indExclusaoPosHistorico.equals(other.indExclusaoPosHistorico))
			return false;
		if (indPrevPorLocal == null) {
			if (other.indPrevPorLocal != null)
				return false;
		} else if (!indPrevPorLocal.equals(other.indPrevPorLocal))
			return false;
		if (indProjecaoIettr == null) {
			if (other.indProjecaoIettr != null)
				return false;
		} else if (!indProjecaoIettr.equals(other.indProjecaoIettr))
			return false;
		if (indPublicoIettr == null) {
			if (other.indPublicoIettr != null)
				return false;
		} else if (!indPublicoIettr.equals(other.indPublicoIettr))
			return false;
		if (indRealPorLocal == null) {
			if (other.indRealPorLocal != null)
				return false;
		} else if (!indRealPorLocal.equals(other.indRealPorLocal))
			return false;
		if (indSinalizacaoIettr == null) {
			if (other.indSinalizacaoIettr != null)
				return false;
		} else if (!indSinalizacaoIettr.equals(other.indSinalizacaoIettr))
			return false;
		if (indTipoAtualizacaoPrevisto == null) {
			if (other.indTipoAtualizacaoPrevisto != null)
				return false;
		} else if (!indTipoAtualizacaoPrevisto.equals(other.indTipoAtualizacaoPrevisto))
			return false;
		if (indTipoAtualizacaoRealizado == null) {
			if (other.indTipoAtualizacaoRealizado != null)
				return false;
		} else if (!indTipoAtualizacaoRealizado.equals(other.indTipoAtualizacaoRealizado))
			return false;
		if (indTipoQtde == null) {
			if (other.indTipoQtde != null)
				return false;
		} else if (!indTipoQtde.equals(other.indTipoQtde))
			return false;
		if (indValorFinalIettr == null) {
			if (other.indValorFinalIettr != null)
				return false;
		} else if (!indValorFinalIettr.equals(other.indValorFinalIettr))
			return false;
		if (indiceMaisRecenteIettr == null) {
			if (other.indiceMaisRecenteIettr != null)
				return false;
		} else if (!indiceMaisRecenteIettr.equals(other.indiceMaisRecenteIettr))
			return false;
		if (itemEstrtIndResulCorIettrcores == null) {
			if (other.itemEstrtIndResulCorIettrcores != null)
				return false;
		} else if (!itemEstrtIndResulCorIettrcores.equals(other.itemEstrtIndResulCorIettrcores))
			return false;
		if (itemEstrtIndResulLocalIettirls == null) {
			if (other.itemEstrtIndResulLocalIettirls != null)
				return false;
		} else if (!itemEstrtIndResulLocalIettirls.equals(other.itemEstrtIndResulLocalIettirls))
			return false;
		if (itemEstrutFisicoIettfs == null) {
			if (other.itemEstrutFisicoIettfs != null)
				return false;
		} else if (!itemEstrutFisicoIettfs.equals(other.itemEstrutFisicoIettfs))
			return false;
		if (itemEstruturaIett == null) {
			if (other.itemEstruturaIett != null)
				return false;
		} else if (!itemEstruturaIett.equals(other.itemEstruturaIett))
			return false;
		if (itemEstruturaIettPPA == null) {
			if (other.itemEstruturaIettPPA != null)
				return false;
		} else if (!itemEstruturaIettPPA.equals(other.itemEstruturaIettPPA))
			return false;
		if (labelGraficoGrupoIettir == null) {
			if (other.labelGraficoGrupoIettir != null)
				return false;
		} else if (!labelGraficoGrupoIettir.equals(other.labelGraficoGrupoIettir))
			return false;
		if (nomeIettir == null) {
			if (other.nomeIettir != null)
				return false;
		} else if (!nomeIettir.equals(other.nomeIettir))
			return false;
		if (periodicidadePrdc == null) {
			if (other.periodicidadePrdc != null)
				return false;
		} else if (!periodicidadePrdc.equals(other.periodicidadePrdc))
			return false;
		if (previstoServicoSer == null) {
			if (other.previstoServicoSer != null)
				return false;
		} else if (!previstoServicoSer.equals(other.previstoServicoSer))
			return false;
		if (realizadoServicoSer == null) {
			if (other.realizadoServicoSer != null)
				return false;
		} else if (!realizadoServicoSer.equals(other.realizadoServicoSer))
			return false;
		if (sisAtributoSatb == null) {
			if (other.sisAtributoSatb != null)
				return false;
		} else if (!sisAtributoSatb.equals(other.sisAtributoSatb))
			return false;
		if (tipoHistorico == null) {
			if (other.tipoHistorico != null)
				return false;
		} else if (!tipoHistorico.equals(other.tipoHistorico))
			return false;
		if (unidMedidaIettr == null) {
			if (other.unidMedidaIettr != null)
				return false;
		} else if (!unidMedidaIettr.equals(other.unidMedidaIettr))
			return false;
		if (usuarioUsu == null) {
			if (other.usuarioUsu != null)
				return false;
		} else if (!usuarioUsu.equals(other.usuarioUsu))
			return false;
		if (usuarioUsuManutencao == null) {
			if (other.usuarioUsuManutencao != null)
				return false;
		} else if (!usuarioUsuManutencao.equals(other.usuarioUsuManutencao))
			return false;
		return true;
	}

        /**
         *
         * @return
         */
        public Set getAcompRealFisicoArfs() {
		return this.acompRealFisicoArfs;
	}

        /**
         *
         * @return
         */
        public Long getCodIettir() {
		return this.codIettir;
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
         * @return
         */
        public Date getDataApuracaoIettr() {
		return dataApuracaoIettr;
	}

        /**
         *
         * @return
         */
        public Date getDataHistorico() {
		return dataHistorico;
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
         * @return
         */
        public String getDescricaoIettir() {
		return this.descricaoIettir;
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
         * @return
         */
        public String getFormulaIettr() {
		return formulaIettr;
	}

        /**
         *
         * @return
         */
        public String getHistorico() {
		return historico;
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
         * @return
         */
        public Set getIettIndResulRevIettrrs() {
		return iettIndResulRevIettrrs;
	}

        /**
         *
         * @return
         */
        public String getIndAcumulavelIettr() {
		return this.indAcumulavelIettr;
	}

        /**
         *
         * @return
         */
        public String getIndAtivoIettr() {
		return indAtivoIettr;
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
         * @return
         */
        public Double getIndiceMaisRecenteIettr() {
		return indiceMaisRecenteIettr;
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
         * @return
         */
        public String getIndProjecaoIettr() {
		return this.indProjecaoIettr;
	}

        /**
         *
         * @return
         */
        public String getIndPublicoIettr() {
		return this.indPublicoIettr;
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
         * @return
         */
        public String getIndSinalizacaoIettr() {
		return indSinalizacaoIettr;
	}

        /**
         *
         * @return
         */
        public String getIndTipoAtualizacaoPrevisto() {
		return indTipoAtualizacaoPrevisto;
	}

        /**
         *
         * @return
         */
        public String getIndTipoAtualizacaoRealizado() {
		return indTipoAtualizacaoRealizado;
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
         * @return
         */
        public String getIndValorFinalIettr() {
		return indValorFinalIettr;
	}

        /**
         *
         * @return
         */
        public Set getItemEstrtIndResulCorIettrcores() {
		return itemEstrtIndResulCorIettrcores;
	}

	/**
	 * @return the itemEstrtIndResulLocalIettirls
	 */
	public Set getItemEstrtIndResulLocalIettirls() {
		return itemEstrtIndResulLocalIettirls;
	}

        /**
         *
         * @return
         */
        public Set getItemEstrutFisicoIettfs() {
		return this.itemEstrutFisicoIettfs;
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
         * @return
         */
        public ecar.pojo.ItemEstruturaIettPPA getItemEstruturaIettPPA() {
		return itemEstruturaIettPPA;
	}

        /**
         *
         * @return
         */
        public String getLabelGraficoGrupoIettir() {
		return labelGraficoGrupoIettir;
	}

        /**
         *
         * @return
         */
        public String getNomeIettir() {
		return this.nomeIettir;
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
         * @return
         */
        public ServicoSer getPrevistoServicoSer() {
		return previstoServicoSer;
	}

        /**
         *
         * @return
         */
        public ServicoSer getRealizadoServicoSer() {
		return realizadoServicoSer;
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
         * @return
         */
        public Long getTipoHistorico() {
		return tipoHistorico;
	}

        /**
         *
         * @return
         */
        public String getUnidMedidaIettr() {
		return this.unidMedidaIettr;
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
         * @return
         */
        public UsuarioUsu getUsuarioUsuManutencao() {
		return usuarioUsuManutencao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acompRealFisicoArfs == null) ? 0 : acompRealFisicoArfs.hashCode());
		result = prime * result + ((codIettir == null) ? 0 : codIettir.hashCode());
		result = prime * result + ((codUnidMedidaIettr == null) ? 0 : codUnidMedidaIettr.hashCode());
		result = prime * result + ((dataApuracaoIettr == null) ? 0 : dataApuracaoIettr.hashCode());
		result = prime * result + ((dataHistorico == null) ? 0 : dataHistorico.hashCode());
		result = prime * result + ((dataUltManutencao == null) ? 0 : dataUltManutencao.hashCode());
		result = prime * result + ((descricaoIettir == null) ? 0 : descricaoIettir.hashCode());
		result = prime * result + ((fonteIettr == null) ? 0 : fonteIettr.hashCode());
		result = prime * result + ((formulaIettr == null) ? 0 : formulaIettr.hashCode());
		result = prime * result + ((historico == null) ? 0 : historico.hashCode());
		result = prime * result + ((historicoIettfHs == null) ? 0 : historicoIettfHs.hashCode());
		result = prime * result + ((iettIndResulRevIettrrs == null) ? 0 : iettIndResulRevIettrrs.hashCode());
		result = prime * result + ((indAcumulavelIettr == null) ? 0 : indAcumulavelIettr.hashCode());
		result = prime * result + ((indAtivoIettr == null) ? 0 : indAtivoIettr.hashCode());
		result = prime * result + ((indExclusaoPosHistorico == null) ? 0 : indExclusaoPosHistorico.hashCode());
		result = prime * result + ((indPrevPorLocal == null) ? 0 : indPrevPorLocal.hashCode());
		result = prime * result + ((indProjecaoIettr == null) ? 0 : indProjecaoIettr.hashCode());
		result = prime * result + ((indPublicoIettr == null) ? 0 : indPublicoIettr.hashCode());
		result = prime * result + ((indRealPorLocal == null) ? 0 : indRealPorLocal.hashCode());
		result = prime * result + ((indSinalizacaoIettr == null) ? 0 : indSinalizacaoIettr.hashCode());
		result = prime * result + ((indTipoAtualizacaoPrevisto == null) ? 0 : indTipoAtualizacaoPrevisto.hashCode());
		result = prime * result + ((indTipoAtualizacaoRealizado == null) ? 0 : indTipoAtualizacaoRealizado.hashCode());
		result = prime * result + ((indTipoQtde == null) ? 0 : indTipoQtde.hashCode());
		result = prime * result + ((indValorFinalIettr == null) ? 0 : indValorFinalIettr.hashCode());
		result = prime * result + ((indiceMaisRecenteIettr == null) ? 0 : indiceMaisRecenteIettr.hashCode());
		result = prime * result + ((itemEstrtIndResulCorIettrcores == null) ? 0 : itemEstrtIndResulCorIettrcores.hashCode());
		result = prime * result + ((itemEstrtIndResulLocalIettirls == null) ? 0 : itemEstrtIndResulLocalIettirls.hashCode());
		result = prime * result + ((itemEstrutFisicoIettfs == null) ? 0 : itemEstrutFisicoIettfs.hashCode());
		result = prime * result + ((itemEstruturaIett == null) ? 0 : itemEstruturaIett.hashCode());
		result = prime * result + ((itemEstruturaIettPPA == null) ? 0 : itemEstruturaIettPPA.hashCode());
		result = prime * result + ((labelGraficoGrupoIettir == null) ? 0 : labelGraficoGrupoIettir.hashCode());
		result = prime * result + ((nomeIettir == null) ? 0 : nomeIettir.hashCode());
		result = prime * result + ((periodicidadePrdc == null) ? 0 : periodicidadePrdc.hashCode());
		result = prime * result + ((previstoServicoSer == null) ? 0 : previstoServicoSer.hashCode());
		result = prime * result + ((realizadoServicoSer == null) ? 0 : realizadoServicoSer.hashCode());
		result = prime * result + ((sisAtributoSatb == null) ? 0 : sisAtributoSatb.hashCode());
		result = prime * result + ((tipoHistorico == null) ? 0 : tipoHistorico.hashCode());
		result = prime * result + ((unidMedidaIettr == null) ? 0 : unidMedidaIettr.hashCode());
		result = prime * result + ((usuarioUsu == null) ? 0 : usuarioUsu.hashCode());
		result = prime * result + ((usuarioUsuManutencao == null) ? 0 : usuarioUsuManutencao.hashCode());
		return result;
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
         * @param codIettir
         */
        public void setCodIettir(Long codIettir) {
		this.codIettir = codIettir;
	}

        /**
         *
         * @param codUnidMedidaIettr
         */
        public void setCodUnidMedidaIettr(ecar.pojo.SisAtributoSatb codUnidMedidaIettr) {
		this.codUnidMedidaIettr = codUnidMedidaIettr;
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
         * @param dataHistorico
         */
        public void setDataHistorico(Date dataHistorico) {
		this.dataHistorico = dataHistorico;
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
         * @param descricaoIettir
         */
        public void setDescricaoIettir(String descricaoIettir) {
		this.descricaoIettir = descricaoIettir;
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
         * @param formulaIettr
         */
        public void setFormulaIettr(String formulaIettr) {
		this.formulaIettr = formulaIettr;
	}

        /**
         *
         * @param historico
         */
        public void setHistorico(String historico) {
		this.historico = historico;
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
         * @param iettIndResulRevIettrrs
         */
        public void setIettIndResulRevIettrrs(Set iettIndResulRevIettrrs) {
		this.iettIndResulRevIettrrs = iettIndResulRevIettrrs;
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
         * @param indAtivoIettr
         */
        public void setIndAtivoIettr(String indAtivoIettr) {
		this.indAtivoIettr = indAtivoIettr;
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
         * @param indiceMaisRecenteIettr
         */
        public void setIndiceMaisRecenteIettr(Double indiceMaisRecenteIettr) {
		this.indiceMaisRecenteIettr = indiceMaisRecenteIettr;
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
         * @param indProjecaoIettr
         */
        public void setIndProjecaoIettr(String indProjecaoIettr) {
		this.indProjecaoIettr = indProjecaoIettr;
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
         * @param indRealPorLocal
         */
        public void setIndRealPorLocal(String indRealPorLocal) {
		this.indRealPorLocal = indRealPorLocal;
	}

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HistoricoItemEstrtIndResulIettr other = (HistoricoItemEstrtIndResulIettr) obj;
        if (this.acompRealFisicoArfs != other.acompRealFisicoArfs && (this.acompRealFisicoArfs == null || !this.acompRealFisicoArfs.equals(other.acompRealFisicoArfs))) {
            return false;
        }
        if (this.codIettir != other.codIettir && (this.codIettir == null || !this.codIettir.equals(other.codIettir))) {
            return false;
        }
        if (this.codUnidMedidaIettr != other.codUnidMedidaIettr && (this.codUnidMedidaIettr == null || !this.codUnidMedidaIettr.equals(other.codUnidMedidaIettr))) {
            return false;
        }
        if (this.dataApuracaoIettr != other.dataApuracaoIettr && (this.dataApuracaoIettr == null || !this.dataApuracaoIettr.equals(other.dataApuracaoIettr))) {
            return false;
        }
        if (this.dataHistorico != other.dataHistorico && (this.dataHistorico == null || !this.dataHistorico.equals(other.dataHistorico))) {
            return false;
        }
        if (this.dataUltManutencao != other.dataUltManutencao && (this.dataUltManutencao == null || !this.dataUltManutencao.equals(other.dataUltManutencao))) {
            return false;
        }
        if ((this.descricaoIettir == null) ? (other.descricaoIettir != null) : !this.descricaoIettir.equals(other.descricaoIettir)) {
            return false;
        }
        if ((this.fonteIettr == null) ? (other.fonteIettr != null) : !this.fonteIettr.equals(other.fonteIettr)) {
            return false;
        }
        if ((this.formulaIettr == null) ? (other.formulaIettr != null) : !this.formulaIettr.equals(other.formulaIettr)) {
            return false;
        }
        if ((this.historico == null) ? (other.historico != null) : !this.historico.equals(other.historico)) {
            return false;
        }
        if (this.historicoIettfHs != other.historicoIettfHs && (this.historicoIettfHs == null || !this.historicoIettfHs.equals(other.historicoIettfHs))) {
            return false;
        }
        if (this.iettIndResulRevIettrrs != other.iettIndResulRevIettrrs && (this.iettIndResulRevIettrrs == null || !this.iettIndResulRevIettrrs.equals(other.iettIndResulRevIettrrs))) {
            return false;
        }
        if ((this.indAcumulavelIettr == null) ? (other.indAcumulavelIettr != null) : !this.indAcumulavelIettr.equals(other.indAcumulavelIettr)) {
            return false;
        }
        if ((this.indAtivoIettr == null) ? (other.indAtivoIettr != null) : !this.indAtivoIettr.equals(other.indAtivoIettr)) {
            return false;
        }
        if (this.indExclusaoPosHistorico != other.indExclusaoPosHistorico && (this.indExclusaoPosHistorico == null || !this.indExclusaoPosHistorico.equals(other.indExclusaoPosHistorico))) {
            return false;
        }
        if (this.indiceMaisRecenteIettr != other.indiceMaisRecenteIettr && (this.indiceMaisRecenteIettr == null || !this.indiceMaisRecenteIettr.equals(other.indiceMaisRecenteIettr))) {
            return false;
        }
        if ((this.indPrevPorLocal == null) ? (other.indPrevPorLocal != null) : !this.indPrevPorLocal.equals(other.indPrevPorLocal)) {
            return false;
        }
        if ((this.indProjecaoIettr == null) ? (other.indProjecaoIettr != null) : !this.indProjecaoIettr.equals(other.indProjecaoIettr)) {
            return false;
        }
        if ((this.indPublicoIettr == null) ? (other.indPublicoIettr != null) : !this.indPublicoIettr.equals(other.indPublicoIettr)) {
            return false;
        }
        if ((this.indRealPorLocal == null) ? (other.indRealPorLocal != null) : !this.indRealPorLocal.equals(other.indRealPorLocal)) {
            return false;
        }
        if ((this.indSinalizacaoIettr == null) ? (other.indSinalizacaoIettr != null) : !this.indSinalizacaoIettr.equals(other.indSinalizacaoIettr)) {
            return false;
        }
        if ((this.indTipoAtualizacaoPrevisto == null) ? (other.indTipoAtualizacaoPrevisto != null) : !this.indTipoAtualizacaoPrevisto.equals(other.indTipoAtualizacaoPrevisto)) {
            return false;
        }
        if ((this.indTipoAtualizacaoRealizado == null) ? (other.indTipoAtualizacaoRealizado != null) : !this.indTipoAtualizacaoRealizado.equals(other.indTipoAtualizacaoRealizado)) {
            return false;
        }
        if ((this.indTipoQtde == null) ? (other.indTipoQtde != null) : !this.indTipoQtde.equals(other.indTipoQtde)) {
            return false;
        }
        if ((this.indValorFinalIettr == null) ? (other.indValorFinalIettr != null) : !this.indValorFinalIettr.equals(other.indValorFinalIettr)) {
            return false;
        }
        if (this.itemEstrtIndResulCorIettrcores != other.itemEstrtIndResulCorIettrcores && (this.itemEstrtIndResulCorIettrcores == null || !this.itemEstrtIndResulCorIettrcores.equals(other.itemEstrtIndResulCorIettrcores))) {
            return false;
        }
        if (this.itemEstrtIndResulLocalIettirls != other.itemEstrtIndResulLocalIettirls && (this.itemEstrtIndResulLocalIettirls == null || !this.itemEstrtIndResulLocalIettirls.equals(other.itemEstrtIndResulLocalIettirls))) {
            return false;
        }
        if (this.itemEstrutFisicoIettfs != other.itemEstrutFisicoIettfs && (this.itemEstrutFisicoIettfs == null || !this.itemEstrutFisicoIettfs.equals(other.itemEstrutFisicoIettfs))) {
            return false;
        }
        if (this.itemEstruturaIett != other.itemEstruturaIett && (this.itemEstruturaIett == null || !this.itemEstruturaIett.equals(other.itemEstruturaIett))) {
            return false;
        }
        if (this.itemEstruturaIettPPA != other.itemEstruturaIettPPA && (this.itemEstruturaIettPPA == null || !this.itemEstruturaIettPPA.equals(other.itemEstruturaIettPPA))) {
            return false;
        }
        if ((this.labelGraficoGrupoIettir == null) ? (other.labelGraficoGrupoIettir != null) : !this.labelGraficoGrupoIettir.equals(other.labelGraficoGrupoIettir)) {
            return false;
        }
        if ((this.nomeIettir == null) ? (other.nomeIettir != null) : !this.nomeIettir.equals(other.nomeIettir)) {
            return false;
        }
        if (this.periodicidadePrdc != other.periodicidadePrdc && (this.periodicidadePrdc == null || !this.periodicidadePrdc.equals(other.periodicidadePrdc))) {
            return false;
        }
        if (this.previstoServicoSer != other.previstoServicoSer && (this.previstoServicoSer == null || !this.previstoServicoSer.equals(other.previstoServicoSer))) {
            return false;
        }
        if (this.realizadoServicoSer != other.realizadoServicoSer && (this.realizadoServicoSer == null || !this.realizadoServicoSer.equals(other.realizadoServicoSer))) {
            return false;
        }
        if (this.sisAtributoSatb != other.sisAtributoSatb && (this.sisAtributoSatb == null || !this.sisAtributoSatb.equals(other.sisAtributoSatb))) {
            return false;
        }
        if (this.tipoHistorico != other.tipoHistorico && (this.tipoHistorico == null || !this.tipoHistorico.equals(other.tipoHistorico))) {
            return false;
        }
        if ((this.unidMedidaIettr == null) ? (other.unidMedidaIettr != null) : !this.unidMedidaIettr.equals(other.unidMedidaIettr)) {
            return false;
        }
        if (this.usuarioUsu != other.usuarioUsu && (this.usuarioUsu == null || !this.usuarioUsu.equals(other.usuarioUsu))) {
            return false;
        }
        if (this.usuarioUsuManutencao != other.usuarioUsuManutencao && (this.usuarioUsuManutencao == null || !this.usuarioUsuManutencao.equals(other.usuarioUsuManutencao))) {
            return false;
        }
        return true;
    }

        /**
         * 
         * @param indSinalizacaoIettr
         */
        public void setIndSinalizacaoIettr(String indSinalizacaoIettr) {
		this.indSinalizacaoIettr = indSinalizacaoIettr;
	}

        /**
         *
         * @param indTipoAtualizacaoPrevisto
         */
        public void setIndTipoAtualizacaoPrevisto(String indTipoAtualizacaoPrevisto) {
		this.indTipoAtualizacaoPrevisto = indTipoAtualizacaoPrevisto;
	}

        /**
         *
         * @param indTipoAtualizacaoRealizado
         */
        public void setIndTipoAtualizacaoRealizado(String indTipoAtualizacaoRealizado) {
		this.indTipoAtualizacaoRealizado = indTipoAtualizacaoRealizado;
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
         * @param indValorFinalIettr
         */
        public void setIndValorFinalIettr(String indValorFinalIettr) {
		this.indValorFinalIettr = indValorFinalIettr;
	}

        /**
         *
         * @param itemEstrtIndResulCorIettrcores
         */
        public void setItemEstrtIndResulCorIettrcores(Set itemEstrtIndResulCorIettrcores) {
		this.itemEstrtIndResulCorIettrcores = itemEstrtIndResulCorIettrcores;
	}

	/**
	 * @param itemEstrtIndResulLocalIettirls
	 *            the itemEstrtIndResulLocalIettirls to set
	 */
	public void setItemEstrtIndResulLocalIettirls(Set itemEstrtIndResulLocalIettirls) {
		this.itemEstrtIndResulLocalIettirls = itemEstrtIndResulLocalIettirls;
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
         * @param itemEstruturaIett
         */
        public void setItemEstruturaIett(ecar.pojo.ItemEstruturaIett itemEstruturaIett) {
		this.itemEstruturaIett = itemEstruturaIett;
	}

        /**
         *
         * @param itemEstruturaIettPPA
         */
        public void setItemEstruturaIettPPA(ecar.pojo.ItemEstruturaIettPPA itemEstruturaIettPPA) {
		this.itemEstruturaIettPPA = itemEstruturaIettPPA;
	}

        /**
         *
         * @param labelGraficoGrupoIettir
         */
        public void setLabelGraficoGrupoIettir(String labelGraficoGrupoIettir) {
		this.labelGraficoGrupoIettir = labelGraficoGrupoIettir;
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
         * @param periodicidadePrdc
         */
        public void setPeriodicidadePrdc(ecar.pojo.PeriodicidadePrdc periodicidadePrdc) {
		this.periodicidadePrdc = periodicidadePrdc;
	}

        /**
         *
         * @param previstoServicoSer
         */
        public void setPrevistoServicoSer(ServicoSer previstoServicoSer) {
		this.previstoServicoSer = previstoServicoSer;
	}

        /**
         *
         * @param realizadoServicoSer
         */
        public void setRealizadoServicoSer(ServicoSer realizadoServicoSer) {
		this.realizadoServicoSer = realizadoServicoSer;
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
         * @param tipoHistorico
         */
        public void setTipoHistorico(Long tipoHistorico) {
		this.tipoHistorico = tipoHistorico;
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
         * @param usuarioUsu
         */
        public void setUsuarioUsu(UsuarioUsu usuarioUsu) {
		this.usuarioUsu = usuarioUsu;
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
        public Long getIdObjetoSerializado() {
		return idObjetoSerializado;
	}

        /**
         *
         * @param idObjetoSerializado
         */
        public void setIdObjetoSerializado(Long idObjetoSerializado) {
		this.idObjetoSerializado = idObjetoSerializado;
	}

    @Override
	public String toString() {
		return new ToStringBuilder(this).append("codIettir", getCodIettir()).toString();
	}

    /**
     *
     * @return
     */
    public Long getIdHistorico() {
		return idHistorico;
	}

    /**
     *
     * @param idHistorico
     */
    public void setIdHistorico(Long idHistorico) {
		this.idHistorico = idHistorico;
	}

        /**
         *
         * @return
         */
        public UsuarioUsu getUsuarioUsuOperacao() {
		// TODO Auto-generated method stub
		return null;
	}

        /**
         *
         * @param usuarioUsuOperacao
         */
        public void setUsuarioUsuOperacao(UsuarioUsu usuarioUsuOperacao) {
		// TODO Auto-generated method stub
		
	}

}
