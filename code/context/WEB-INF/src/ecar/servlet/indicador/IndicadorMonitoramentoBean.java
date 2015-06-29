package ecar.servlet.indicador;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import comum.util.Util;

import ecar.api.facade.Exercicio;
import ecar.api.facade.IndicadorResultado;
import ecar.api.facade.Previsto;
import ecar.api.facade.Realizado;
import ecar.dao.AcompRealFisicoDao;
import ecar.dao.AcompReferenciaDao;
import ecar.dao.ExercicioDao;
import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.acompanhamentoEstrategico.Indicador;
import ecar.pojo.acompanhamentoEstrategico.PeriodoAcompanhamento;
import ecar.sinalizacao.CalculoSinalizacao;
import ecar.util.Dominios;


public class IndicadorMonitoramentoBean {
	
	AcompRealFisicoDao acompRealFisicoDao;
	AcompReferenciaDao acompReferenciaDao;
	ItemEstruturaDao itemEstruturaDao;
	ExercicioDao exercicioDao;
	CalculoSinalizacao sinalizacao;
	Logger logger = Logger.getLogger(IndicadorMonitoramentoBean.class);
	
	public IndicadorMonitoramentoBean() {
		acompRealFisicoDao = new AcompRealFisicoDao(null);
		exercicioDao = new ExercicioDao(null);
	}
	
	@SuppressWarnings("unchecked")
	public String jsonListaIndicadores(AcompReferenciaItemAri ari, Boolean edicaoJustificativa) {
		try {
			List<AcompRealFisicoArf> indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari, Dominios.TODOS, false);
			IndicadorMonitoramentoDto indMonitoramento;
			List<IndicadorMonitoramentoDto> indicadores = new ArrayList<IndicadorMonitoramentoDto>();
			IndicadorResultado indicadorFachada;
			for (AcompRealFisicoArf indEcar : indResultados) {
				indMonitoramento = new IndicadorMonitoramentoDto();
				indicadorFachada = new IndicadorResultado(indEcar.getItemEstrtIndResulIettr());
				indMonitoramento.setId(indicadorFachada.getId());
				indMonitoramento.setCodAref(ari.getAcompReferenciaAref().getCodAref());
				indMonitoramento.setTipo(indicadorFachada.getDescricaoTipoIndicador());
				indMonitoramento.setNomeIndicador(indicadorFachada.getNome());
				indMonitoramento.setUnidadeMedida(indicadorFachada.getUnidadeMedida());
				indMonitoramento.setValores(configuraValores(ari, indicadorFachada, indEcar, edicaoJustificativa));
				
				indicadores.add(indMonitoramento);
			}
			Gson gson = new Gson();
			return gson.toJsonTree(indicadores).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public List<IndicadorMonitoramentoDto> ListaIndicadores(AcompReferenciaItemAri ari, Boolean edicaoJustificativa) {
		List<IndicadorMonitoramentoDto> indicadores = new ArrayList<IndicadorMonitoramentoDto>();
		try {
			List<AcompRealFisicoArf> indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari, Dominios.TODOS, false);
			IndicadorMonitoramentoDto indMonitoramento;
			
			IndicadorResultado indicadorFachada;
			for (AcompRealFisicoArf indEcar : indResultados) {
				indMonitoramento = new IndicadorMonitoramentoDto();
				indicadorFachada = new IndicadorResultado(indEcar.getItemEstrtIndResulIettr());
				indMonitoramento.setId(indicadorFachada.getId());
				indMonitoramento.setCodAref(ari.getAcompReferenciaAref().getCodAref());
				indMonitoramento.setCodArf(indEcar.getCodArf());
				indMonitoramento.setTipo(indicadorFachada.getDescricaoTipoIndicador());
				indMonitoramento.setNomeIndicador(indicadorFachada.getNome());
				indMonitoramento.setUnidadeMedida(indicadorFachada.getUnidadeMedida());
				indMonitoramento.setValores(configuraValores(ari, indicadorFachada, indEcar, edicaoJustificativa));
				
				indicadores.add(indMonitoramento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indicadores;
	}

	public List<IndicadorMonitoramentoDto> ListaIndicadores(PeriodoAcompanhamento periodoAcompanhamento, List<Indicador> listaIndicadores) {
		List<IndicadorMonitoramentoDto> indicadores = new ArrayList<IndicadorMonitoramentoDto>();
		try {
			IndicadorMonitoramentoDto indMonitoramento;
			
			for (Indicador indEcar : listaIndicadores) {
				indMonitoramento = new IndicadorMonitoramentoDto();
				indMonitoramento.setId(new Long(indEcar.getCodigo()));
				indMonitoramento.setNomeIndicador(indEcar.getNome());
				indMonitoramento.setUnidadeMedida(indEcar.getUnidadeMedida());
							
				List<IndicadorValorDto> valores = new ArrayList<IndicadorValorDto>(); 
							
				//Meta2015 e Realizado2015 na verdade se refere a 2011
//				valores.add(configuraValores(ari, indEcar, indEcar.getMeta2015(),indEcar.getValorRealizado2015()));
//				valores.add(configuraValores(ari, indEcar, indEcar.getMeta2012(),indEcar.getValorRealizado2012()));
//				valores.add(configuraValores(ari, indEcar, indEcar.getMeta2013(),indEcar.getValorRealizado2013()));
//				valores.add(configuraValores(ari, indEcar, indEcar.getMeta2014(),indEcar.getValorRealizado2014()));				
				valores.add(configuraValores(periodoAcompanhamento, indEcar, indEcar.getMeta2015(),indEcar.getValorRealizado2015()));
				valores.add(configuraValores(periodoAcompanhamento, indEcar, indEcar.getMeta2012(),indEcar.getValorRealizado2012()));
				valores.add(configuraValores(periodoAcompanhamento, indEcar, indEcar.getMeta2013(),indEcar.getValorRealizado2013()));
				valores.add(configuraValores(periodoAcompanhamento, indEcar, indEcar.getMeta2014(),indEcar.getValorRealizado2014()));				
								
				indMonitoramento.setValores(valores);
				
				indicadores.add(indMonitoramento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indicadores;
	}
	
	
	public List<IndicadorMonitoramentoDto> ListaIndicadores(AcompReferenciaItemAri ari, List<Indicador> listaIndicadores) {
		List<IndicadorMonitoramentoDto> indicadores = new ArrayList<IndicadorMonitoramentoDto>();
		try {
//			List<AcompRealFisicoArf> indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari, Dominios.TODOS, false);
			IndicadorMonitoramentoDto indMonitoramento;
			
//			IndicadorResultado indicadorFachada;
			for (Indicador indEcar : listaIndicadores) {
				indMonitoramento = new IndicadorMonitoramentoDto();
//				indicadorFachada = new IndicadorResultado(indEcar.getItemEstrtIndResulIettr());
				indMonitoramento.setId(new Long(indEcar.getCodigo()));
//				indMonitoramento.setCodAref(indEcar.getCodAref());
//				indMonitoramento.setCodArf(indEcar.getCodArf());
//				indMonitoramento.setTipo(indicadorFachada.getDescricaoTipoIndicador());
				indMonitoramento.setNomeIndicador(indEcar.getNome());
				indMonitoramento.setUnidadeMedida(indEcar.getUnidadeMedida());
							
				List<IndicadorValorDto> valores = new ArrayList<IndicadorValorDto>();
							
				//Meta2015 e Realizado2015 na verdade se refere a 2011
				valores.add(configuraValores(ari, indEcar, indEcar.getMeta2015(),indEcar.getValorRealizado2015()));
				valores.add(configuraValores(ari, indEcar, indEcar.getMeta2012(),indEcar.getValorRealizado2012()));
				valores.add(configuraValores(ari, indEcar, indEcar.getMeta2013(),indEcar.getValorRealizado2013()));
				valores.add(configuraValores(ari, indEcar, indEcar.getMeta2014(),indEcar.getValorRealizado2014()));				
								
				indMonitoramento.setValores(valores);
				
				indicadores.add(indMonitoramento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indicadores;
	}

	private double converteDouble(String valor) {
		BigDecimal d = null;
		if ((valor == null) || (valor.equals(""))) {
			d = new BigDecimal("0.00");
		} else {
			if (valor.contains(",")) {
				valor = valor.replaceAll("\\.", "").replace(",", ".");
				d = new BigDecimal(valor);
			} else if (!valor.contains(".")) {
				valor = valor + ".00";
				d = new BigDecimal(valor);
			} else {
				try {
					d = new BigDecimal(valor);
				} catch (Exception e) {
					logger.error("Erro ao tentar capturar o valor: " + valor + "\n", e);
				}

			}
		}
		return d.doubleValue();
	}
	
	//PeriodoAcompanhamento periodoAcompanhamento
	private IndicadorValorDto configuraValores(PeriodoAcompanhamento periodoAcompanhamento, Indicador indEcar, String previsto, String realizado) throws ECARException {
		IndicadorValorDto valorDto = new IndicadorValorDto();
		previsto = String.valueOf(previsto);
		valorDto.setPrevisto(previsto);
		valorDto.setRealizado(realizado);
//				valorDto.setCor("branco.gif");
		double valorRealizado = converteDouble(realizado);
		double valorPrevisto = converteDouble(previsto);
		
		AcompRealFisicoArf ind = new AcompRealFisicoArf();
		ind.setItemEstrtIndResulIettr(new IndicadorResultado(indEcar.getCodigo()).getRealObject());
		sinalizacao = new CalculoSinalizacao(periodoAcompanhamento, ind, valorRealizado, valorPrevisto);
		sinalizacao.execute();
		if (sinalizacao.getCalculo()!= null)
			valorDto.setAtingimento(Util.formataDecimalPT_BR(sinalizacao.getCalculo()));
		if(sinalizacao.getFaixa().getCor() != null) {
			valorDto.setCor(sinalizacao.getFaixa().getCor().getCaminhoImagemIndResulCor());			
		}
		return valorDto;
	}
	
	
	private IndicadorValorDto configuraValores(AcompReferenciaItemAri ari, Indicador indEcar, String previsto, String realizado) throws ECARException {
		
		IndicadorValorDto valorDto = new IndicadorValorDto();
		
		valorDto.setPrevisto(previsto);
		valorDto.setRealizado(realizado);
//				valorDto.setCor("branco.gif");
		double valorRealizado = new Double(realizado);
		double valorPrevisto = new Double(previsto);
		
		AcompRealFisicoArf ind = new AcompRealFisicoArf();
		ind.setItemEstrtIndResulIettr(new IndicadorResultado(indEcar.getCodigo()).getRealObject());
		sinalizacao = new CalculoSinalizacao(ari, ind, valorRealizado, valorPrevisto);
		sinalizacao.execute();
		if (sinalizacao.getCalculo()!= null)
			valorDto.setAtingimento(Util.formataDecimalPT_BR(sinalizacao.getCalculo()));
		if(sinalizacao.getFaixa().getCor() != null) {
			valorDto.setCor(sinalizacao.getFaixa().getCor().getCaminhoImagemIndResulCor());			
		}
		return valorDto;
	}
	
	@SuppressWarnings("unchecked")
	private List<IndicadorValorDto> configuraValores(AcompReferenciaItemAri ari, IndicadorResultado indF, AcompRealFisicoArf indArf, Boolean edicaoJustificativa) 
			throws ECARException {
		List<IndicadorValorDto> valores = new ArrayList<IndicadorValorDto>();
		IndicadorValorDto valorDto;
		ExercicioExe ex = exercicioDao.getExercicio(String.valueOf(indArf.getMesArf()), String.valueOf(indArf.getAnoArf()));
		Exercicio exercicio = new Exercicio(ex);
		Realizado realizado;
		Previsto previsto;
		//Mes atual
		valorDto = new IndicadorValorDto();
		realizado = indF.getRealizadoMensal(indArf.getMesArf(), indArf.getAnoArf());
		if(realizado != null && realizado.getRealizado() != null) {
			valorDto.setRealizado(realizado.getRealizadoFormatado());			
		}
		previsto = indF.getPrevistoMensal(indArf.getMesArf().intValue(), indArf.getAnoArf().intValue());
		if(previsto != null && previsto.getValorPrevisto() != null) {
			valorDto.setPrevisto(previsto.getPrevistoFormatado());			
		}
		sinalizacao = new CalculoSinalizacao(ari, indArf);
		sinalizacao.execute();
		if (sinalizacao.getCalculo()!= null)
			valorDto.setAtingimento(Util.formataDecimalPT_BR(sinalizacao.getCalculo()));
		if(sinalizacao.getFaixa().getCor() != null) {
			valorDto.setCor(sinalizacao.getFaixa().getCor().getCaminhoImagemIndResulCor());			
		}
		
		if(sinalizacao.getFaixa().isExigeJustificativa() !=null && sinalizacao.getFaixa().isExigeJustificativa()) {
			valorDto.setExigeJustificativa(true);
			valorDto.setEdicaoJustificativa(edicaoJustificativa);
		}
		valorDto.setJustificativa(indArf.getJustificativaArf());		
		valores.add(valorDto);
		
		//Parcial do ano atual
		valorDto = new IndicadorValorDto();
		double valorRealizado = indF.getRealizadoParcialNoExercio(exercicio,indArf.getMesArf());
		double valorPrevisto = indF.getPrevistoParcialNoExercicio(exercicio,indArf.getMesArf());
		if(indF.getIndTipoQtde().equals("V")){
			//formato de moeda
			valorDto.setRealizado(Util.formataMoeda(valorRealizado));
			valorDto.setPrevisto(Util.formataMoeda(valorPrevisto));
		}else{
			valorDto.setRealizado(Util.formataNumeroDecimal(valorRealizado));
			valorDto.setPrevisto(Util.formataNumeroDecimal(valorPrevisto));
		}
		sinalizacao = new CalculoSinalizacao(ari, indArf, valorRealizado, valorPrevisto);
		sinalizacao.execute();
		if (sinalizacao.getCalculo()!= null)
			valorDto.setAtingimento(Util.formataDecimalPT_BR(sinalizacao.getCalculo()));
		if(sinalizacao.getFaixa().getCor() != null) {
			valorDto.setCor(sinalizacao.getFaixa().getCor().getCaminhoImagemIndResulCor());			
		}
		
		valores.add(valorDto);
		//Historico
		List<ExercicioExe> exercicios = exercicioDao.getExerciciosAnteriores(ex);
		for (ExercicioExe eExe : exercicios) {
			if(eExe.getDescricaoExe().equals("2012")){
			exercicio = new Exercicio(eExe);
			Double rea = indF.getRealizadoNoExercio(exercicio);
			if(rea != null) {
				valorDto = new IndicadorValorDto();
				double prev = indF.getPrevistoNoExercicio(exercicio);
				if(indF.getIndTipoQtde().equals("V")){
					//formato de moeda
					valorDto.setRealizado(Util.formataMoeda(rea));
					valorDto.setPrevisto(Util.formataMoeda(prev));
				}else{
					valorDto.setRealizado(Util.formataNumeroDecimal(rea));
					valorDto.setPrevisto(Util.formataNumeroDecimal(prev));
				}
				sinalizacao = new CalculoSinalizacao(ari, indArf, rea, prev);
				sinalizacao.execute();
				if (sinalizacao.getCalculo()!= null)
					valorDto.setAtingimento(Util.formataDecimalPT_BR(sinalizacao.getCalculo()));
				if(sinalizacao.getFaixa().getCor() != null) {
					valorDto.setCor(sinalizacao.getFaixa().getCor().getCaminhoImagemIndResulCor());			
				}
				valores.add(valorDto);
			}
		}
		}
		return valores;
	}
	
	
	
	public String jsonListaIndicadores(Long codAref) throws ECARException {
		acompReferenciaDao = new AcompReferenciaDao(null);
		itemEstruturaDao = new ItemEstruturaDao(null);
		AcompReferenciaAref aref = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, codAref);
		List<AcompRealFisicoArf> arfs = acompRealFisicoDao.obtemIndicadoresByAref(aref);
		IndicadorResultado indicadorFachada;
		IndicadorMonitoramentoDto dto;
		IndicadorValorDto valorDto;
		Previsto previsto;
		Realizado realizado;
		List<IndicadorMonitoramentoDto> indicadores = new ArrayList<IndicadorMonitoramentoDto>();
		for (AcompRealFisicoArf acompRealFisicoArf : arfs) {
			indicadorFachada = new IndicadorResultado(acompRealFisicoArf.getItemEstrtIndResulIettr());
			dto = new IndicadorMonitoramentoDto();
			dto.setId(indicadorFachada.getId());
			dto.setCodAref(codAref);
			dto.setNomeIndicador(configNomeIndicador(indicadorFachada));
			dto.setTipo(indicadorFachada.getDescricaoTipoIndicador());
			valorDto = new IndicadorValorDto();
			previsto = indicadorFachada.getPrevistoMensal(Integer.parseInt(aref.getMesAref()),	Integer.parseInt(aref.getAnoAref()));
			realizado = indicadorFachada.getRealizadoMensal(Integer.parseInt(aref.getMesAref()),	Integer.parseInt(aref.getAnoAref()));
			if(previsto != null) {
				valorDto.setPrevisto(previsto.getPrevistoFormatado());				
			}else {
				valorDto.setPrevisto("?");
			}
			if(realizado != null) {
				valorDto.setRealizado(realizado.getRealizadoFormatado());
			}else {
				valorDto.setRealizado("");
			}
			dto.addValor(valorDto);
			indicadores.add(dto);
		}
		Gson gson = new Gson();
		return gson.toJsonTree(indicadores).toString();
	}
	
	@SuppressWarnings("unchecked")
	private String configNomeIndicador(IndicadorResultado indR) {
		List<ItemEstruturaIett> ietts = itemEstruturaDao.getAscendentes(indR.getItemEstrutura().getRealObject());
		StringBuffer nome = new StringBuffer();
		int i = ietts.size() -1;
		do {
			nome.append(ietts.get(i).getSiglaIett());
			i--;
		}while(i == 0);
		nome.append(" - ");
		nome.append(indR.getItemEstrutura().getRealObject().getSiglaIett() + " - " + indR.getNome());
		return nome.toString();
		
	}
}