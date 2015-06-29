package ecar.sinalizacao;

import java.util.ArrayList;
import java.util.List;

import ecar.api.facade.IndicadorResultado;
import ecar.api.facade.Previsto;
import ecar.api.facade.Realizado;
import ecar.dao.CorDao;
import ecar.exception.ECARException;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.Cor;
import ecar.pojo.acompanhamentoEstrategico.PeriodoAcompanhamento;

/**
 * 
 * @author Rafael de Morais
 *
 */
public class CalculoSinalizacao {
	
	public static Faixa NA;
	public static Faixa NA_REALIZADO;	

	static {
		try {
			Cor na = (Cor) new CorDao(null).buscar(Cor.class, new Long(5));
			Cor na_realizado = (Cor) new CorDao(null).buscar(Cor.class, new Long(6));
			NA           = new Faixa(na, 0, 0);
			NA_REALIZADO = new Faixa(na_realizado, 0, 0);
		} catch (ECARException e) {
			e.printStackTrace();
		}
	}
	
	private AcompReferenciaItemAri ari;
	private AcompRealFisicoArf ind;
	private Long calculo;
	private Double realizado = null;
	private Double previsto = null;
	private PeriodoAcompanhamento periodoAcompanhamento;
	
	private Faixa faixaSelecionada = new Faixa();
	private Faixa faixaLinhaDeBase = new Faixa();

	public CalculoSinalizacao(){		
	}
	
	public CalculoSinalizacao(AcompReferenciaItemAri ari, AcompRealFisicoArf ind) {
		this.ari = ari;
		this.ind = ind;
	}
	
	public CalculoSinalizacao(AcompReferenciaItemAri ari, AcompRealFisicoArf ind, Double realizado, Double previsto) {
		this.ari = ari;
		this.ind = ind;
		this.realizado = realizado;
		this.previsto = previsto;
	}
	
	public CalculoSinalizacao(PeriodoAcompanhamento periodoAcompanhamento, AcompRealFisicoArf acompRealFisicoArf, double realizado, double previsto) {
		this.periodoAcompanhamento = periodoAcompanhamento;
		this.ind = acompRealFisicoArf;
		this.realizado = realizado;
		this.previsto = previsto;
	}
	
	public void execute() throws NumberFormatException, ECARException {
		
		IndicadorResultado indicadorResultado = null;
		try{
			indicadorResultado = new IndicadorResultado(ind.getItemEstrtIndResulIettr());		
		} catch (Exception e){
			System.out.println("codigo arf: " + ind.getCodArf());
			e.printStackTrace();
		}
		Double c = 0.0;
		Double prev;
		List<PrevistoFaixa> previstos = new ArrayList<PrevistoFaixa>();
		
		Sinalizacao sinalizacao = ind.getItemEstrtIndResulIettr().getSinalizacao();
		
		if(sinalizacao == null) {
			faixaSelecionada = NA;
			faixaLinhaDeBase = NA;
			return;
		}
		
		Previsto previsto = null;
		Realizado realizado = null; 
				
		if(periodoAcompanhamento != null){
			previsto = indicadorResultado.getPrevistoMensal(Integer.valueOf(periodoAcompanhamento.getMes()),Integer.valueOf(periodoAcompanhamento.getAno()));
			realizado = indicadorResultado.getRealizadoMensal(Long.valueOf(periodoAcompanhamento.getMes()),Long.valueOf(periodoAcompanhamento.getAno()));
		} else {
			previsto = indicadorResultado.getPrevistoMensal(Integer.valueOf(ari.getAcompReferenciaAref().getMesAref()),Integer.valueOf(ari.getAcompReferenciaAref().getAnoAref()));
			realizado = indicadorResultado.getRealizadoMensal(Long.valueOf(ari.getAcompReferenciaAref().getMesAref()),Long.valueOf(ari.getAcompReferenciaAref().getAnoAref()));
		}
		
		if(previsto != null) {
			prev = previsto.getValorPrevisto();
		}else {
			prev = null;
		}
		previstos.add(new PrevistoFaixa(prev, faixaSelecionada));
		
		if(ind.getItemEstrtIndResulIettr().getConsiderarLinhaDeBase() != null) {
			previstos.add(new PrevistoFaixa(ind.getItemEstrtIndResulIettr().getLinhaBase(), faixaLinhaDeBase));
		}else {
			faixaLinhaDeBase = NA;
		}
		
		for (PrevistoFaixa previstoFaixa : previstos) {
			if(!isNA(previstoFaixa.getPrevisto(), realizado, previstoFaixa.getFaixa())) {
				if(previstoFaixa.getPrevisto() != 0) {
					if(sinalizacao != null && sinalizacao.getPolaridade() != null && sinalizacao.getPolaridade() == true) {
						c = (1 + ((realizado.getRealizado() - previstoFaixa.getPrevisto()) / previstoFaixa.getPrevisto())) * 100;
					}else {
						c = (1 - ((realizado.getRealizado() - previstoFaixa.getPrevisto()) / previstoFaixa.getPrevisto())) * 100;
					}
				}else {
					c = null;
				}
				if (c != null && c < 0.0) {
					c = 0.0;
				}
				//calculo = Math.round(c);
				if(c != null) {
					for (Faixa faixa : sinalizacao.getFaixas()) {
						if(estaNaFaixa(faixa, c)) {
							previstoFaixa.setFaixa(faixa);
							break;
						}
					}					
				}
			}
		}
	}
	
	/**
	 * 
	 * Valida a regra para o NA - N�o se Aplica
	 * 
	 * Regra:
	 * 
	 * 1. Quando houver 0 (zero) em previsto, o sinalizador fica branco.
	 * 
	 * 2. Caso haja algum realizado, continua branco, mas com uma exclama��o no centro.
	 * 
	 * As regras acima ser�o expressas na forma do objeto {@link Cor}
	 * 
	 * @param previsto
	 * @param realizado
	 * @return true caso os parametros estejam enquadrados na regra de NA - N�o se Aplica
	 */
	private boolean isNA(Double previsto, Realizado realizado, Faixa faixa) {
		if(previsto == null) {
			if(realizado != null && realizado.getRealizado() > 0.0) {
				copyFaixa(NA_REALIZADO, faixa);
			}else {
				copyFaixa(NA, faixa);
			}
			return true;
		}
		return false;
	}
	
	private boolean estaNaFaixa(Faixa faixa, Double calculo) {
		if(calculo >= faixa.getMin() && calculo < faixa.getMax()) {
			return true;
		}
		return false;
	}
	
	private void copyFaixa(Faixa in, Faixa out) {
		out.setCodFaixa(in.getCodFaixa());
		out.setCor(in.getCor());
		out.setMax(in.getMax());
		out.setMin(in.getMin());
		out.setSinalizacao(in.getSinalizacao());
	}
	
	public Faixa getFaixa() {
		return faixaSelecionada;
	}
	
	public Long getCalculo() {
		return calculo;
	}
	public Faixa getFaixaLinhaDeBase() {
		return faixaLinhaDeBase;
	}
	
	class PrevistoFaixa {
		private Double previsto;
		private Faixa faixa;
		
		public PrevistoFaixa(Double previsto, Faixa faixa) {
			super();
			this.previsto = previsto;
			this.faixa = faixa;
		}
		
		public Double getPrevisto() {
			return previsto;
		}
		public Faixa getFaixa() {
			return faixa;
		}
		public void setFaixa(Faixa faixa) {
			this.faixa.setCodFaixa(faixa.getCodFaixa());
			this.faixa.setCor(faixa.getCor());
			this.faixa.setMax(faixa.getMax());
			this.faixa.setMin(faixa.getMin());
			this.faixa.setSinalizacao(faixa.getSinalizacao());
		}
	}
	
}