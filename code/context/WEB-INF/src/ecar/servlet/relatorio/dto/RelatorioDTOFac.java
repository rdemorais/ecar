package ecar.servlet.relatorio.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import comum.util.Data;
import comum.util.Ordena;
import comum.util.Util;

import ecar.api.facade.AcompanhamentoItemEstrutura;
import ecar.api.facade.EcarData;
import ecar.api.facade.Exercicio;
import ecar.api.facade.IndicadorResultado;
import ecar.api.facade.ItemEstrutura;
import ecar.api.facade.Previsto;
import ecar.api.facade.Realizado;
import ecar.dao.AcompRealFisicoDao;
import ecar.dao.AcompReferenciaDao;
import ecar.dao.ApontamentoDao;
import ecar.dao.CorDao;
import ecar.dao.ExercicioDao;
import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.ApontamentoApt;
import ecar.pojo.Cor;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrutEntidadeIette;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.PeriodicidadePrdc;
import ecar.pojo.SituacaoSit;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.UsuarioUsu;
import ecar.servlet.relatorio.RelatorioPE;
import ecar.servlet.relatorio.dto.marcas.MarcasCicloDTO;
import ecar.servlet.relatorio.dto.marcas.RedeCicloDTO;
import ecar.servlet.relatorio.dto.marcas.RedeCicloDTOCompleto;
import ecar.servlet.relatorio.dto.marcas.RedeCicloIndicadorDTO;
import ecar.servlet.relatorio.dto.marcas.RedePlanoAcaoDTO;
import ecar.sinalizacao.CalculoSinalizacao;
import ecar.util.Dominios;
import ecar.util.HtmlSanitizer;

/**
 * 
 * @author Rafael de Morais
 *
 */
public class RelatorioDTOFac {
	
	private CorDao corDao;
	private AcompRealFisicoDao acompRealFisicoDao;
	private AcompReferenciaDao acompReferenciaDao;
	private ItemEstruturaDao itemEstruturaDao;
	private ExercicioDao exercicioDao;
	
	public RelatorioDTOFac(HttpServletRequest req) {
		corDao = new CorDao(req);
		acompRealFisicoDao = new AcompRealFisicoDao(req);
		acompReferenciaDao = new AcompReferenciaDao(req);
		itemEstruturaDao = new ItemEstruturaDao(req);
		exercicioDao = new ExercicioDao(req);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<RedePlanoAcaoDTO> dtoRedePlanoAcao(Set gruposAcesso, UsuarioUsu usuario) throws ECARException {
		List<RedePlanoAcaoDTO> planos = new ArrayList<RedePlanoAcaoDTO>();
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yy");
		//20 = Estrutura Atividade em Marcas
		//7  = Atividade - Marcas
		//18 = local
		List<ItemEstruturaIett> ietts = itemEstruturaDao.getItensByEstrutura(gruposAcesso, usuario, 20L, 7L);
		//List<ItemEstruturaIett> ietts = itemEstruturaDao.getItensByEstrutura(gruposAcesso, usuario, 18L);
		RedePlanoAcaoDTO dto;
		ItemEstruturaIett acao = null;
		ItemEstruturaIett eixo = null;
		for (ItemEstruturaIett iett : ietts) {
			List<ItemEstruturaIett> ascendentes = itemEstruturaDao.getAscendentes(iett);
			dto = new RedePlanoAcaoDTO();
			eixo = ascendentes.get(1);
			acao = ascendentes.get(2);
			dto.setRede(ascendentes.get(0).getNomeIett());
			dto.setObjetivo(ascendentes.get(0).getObjetivoGeralIett());
			String sigla = "";
			if(eixo.getSiglaIett() != null || eixo.getSiglaIett() != "") {
				sigla = eixo.getSiglaIett() + " - ";
			}
			dto.setEixo(sigla + eixo.getNomeIett());
			//19L - Local
			//5L  - Prod
			if(ascendentes.get(2).getEstruturaEtt().getCodEtt().equals(5L)) {
				eixo = ascendentes.get(2);
				acao = ascendentes.get(3);
				dto.setEixo(dto.getEixo() + " / " + eixo.getNomeIett());
			}
			sigla = "";
			if(acao.getSiglaIett() != null || acao.getSiglaIett() != "") {
				sigla = acao.getSiglaIett() + " - ";
			}
			dto.setAcao(sigla + acao.getNomeIett());
			dto.setRespAcao(responsavel(acao).getNomeUsu());
			if(acao.getDataInicioIett() != null) {
				dto.setInicioAcao(sd.format(acao.getDataInicioIett()));
			}
			if(acao.getDataTerminoIett() != null) {
				dto.setTerminoAcao(sd.format(acao.getDataTerminoIett()));
			}
			sigla = "";
			if(iett.getSiglaIett() != null || iett.getSiglaIett() != "") {
				sigla = iett.getSiglaIett() + " - ";
			}
			dto.setAtividade(sigla + iett.getNomeIett());
			dto.setRespAtividade(responsavel(iett).getNomeUsu());
			if(iett.getDataInicioIett() != null) {
				dto.setInicioAtividade(sd.format(iett.getDataInicioIett()));
			}
			if(iett.getDataTerminoIett() != null) {
				dto.setTerminoAtividade(sd.format(iett.getDataTerminoIett()));
			}
			if(iett.getDataInicioIett() != null && iett.getDataTerminoIett() != null) {
				formataGantt(dto, iett);
			}
			planos.add(dto);
		}
		System.out.println("Conclui a contrucao dos planos. Qtd: "+planos.size());
		ordenarPlanoAcao(planos);
		System.out.println("Conclui a ordenacao dos planos. Qtd: "+planos.size());
		return planos;
	}
	
	public void ordenarPlanoAcao(List<RedePlanoAcaoDTO> planos) {
		Ordena<RedePlanoAcaoDTO> ordenacao = new Ordena<RedePlanoAcaoDTO>();
		try {
			ordenacao.ordenarLista(planos, "getAtividade");
			ordenacao.ordenarLista(planos, "getAcao");
			ordenacao.ordenarLista(planos, "getEixo");
			ordenacao.ordenarLista(planos, "getRede");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	private UsuarioUsu responsavel(ItemEstruturaIett iett) {
		Iterator it = iett.getItemEstUsutpfuacIettutfas().iterator();
		while(it.hasNext()) {
			return ((ItemEstUsutpfuacIettutfa) iett.getItemEstUsutpfuacIettutfas().iterator().next()).getUsuarioUsu();
		}
		return new UsuarioUsu();
	}
	
	private void formataGantt(RedePlanoAcaoDTO dto, ItemEstruturaIett iett) {
		Calendar dtInicio = Calendar.getInstance();
		Calendar dtFinal = Calendar.getInstance();
		
		dtInicio.setTime(iett.getDataInicioIett());
		dtFinal.setTime(iett.getDataTerminoIett());
		
		List<Integer> anos = itensDoPeriodo(dtInicio, dtFinal, Calendar.YEAR);
		
		for (int i = 0; i < anos.size(); i++) {
			if(anos.size() >= 2 && (i < (anos.size() -1))) {
				dtFinal.set(anos.get(i), Calendar.DECEMBER, 31);
			}
			if(i > 0) {
				dtInicio.set(anos.get(i), Calendar.JANUARY, 1);
			}
			if(i == (anos.size() -1)) {
				dtFinal.setTime(iett.getDataTerminoIett());
			}
			List<Integer> meses = itensDoPeriodo(dtInicio, dtFinal, Calendar.MONTH);
			for (Integer mes : meses) {
				int anoIndice = anos.get(i) - 2011;
				dto.addMes(mes + (12*anoIndice));
			}
		}
	}
	
	/**
	 *
	 * @param ini
	 * @param fim
	 * @param tipo
	 * @return os dias, meses, ou anos
	 */
	private List<Integer> itensDoPeriodo(Calendar ini, Calendar fim, int tipo) {
		List<Integer> itens = new ArrayList<Integer>();
		int iniNumero = ini.get(tipo);
		int fimNumero = fim.get(tipo);
		for(;iniNumero < fimNumero; iniNumero++) {
			itens.add(iniNumero);
		}
		itens.add(fimNumero);
		return itens;
	}
	
	@SuppressWarnings("unchecked")
	public List<RedeCicloDTO> dtoRedesCicloSimples(List<AcompReferenciaItemAri> itens, 
			TipoFuncAcompTpfa tipoFAcomp,
			List<String> listCiclos) throws Exception {
		RedeCicloDTO dto;
		List<RedeCicloDTO> dtoList = new ArrayList<RedeCicloDTO>();
		for (AcompReferenciaItemAri ari : itens) {
			dto = new RedeCicloDTO();
			
			List<ItemEstruturaIett> ascendentes = itemEstruturaDao.getAscendentes(ari.getItemEstruturaIett());
			dto.setRede(ascendentes.get(0).getNomeIett());
			
			String eixo = ascendentes.get(1).getNomeIett();
			
			if(eixo.equals("B - SOS Emergências") || eixo.equals("C - Atenção Domiciliar / Melhor em Casa") 
					|| eixo.equals("D - Unidades de Pronto Atendimento")) {
				dto.setRede(eixo + " - " + dto.getRede());
			}
			
			dto.setEixo(eixo);
			
			dto.setAcao(ari.getItemEstruturaIett().getNomeIett());
			//Indicadores
			List<AcompRealFisicoArf> indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari, Dominios.TODOS, false);
			RedeCicloIndicadorDTO indicador = null;
			for(AcompRealFisicoArf ind : indResultados) {
				
				indicador = new RedeCicloIndicadorDTO();
				indicador.setRede(dto.getRede());
				indicador.setEixo(dto.getEixo());
				indicador.setAcao(dto.getAcao());
				indicador.setCodInd(String.valueOf(ind.getItemEstrtIndResulIettr().getCodIettir()));
				indicador.setNomeIndicador(ind.getItemEstrtIndResulIettr().getNomeIettir());
				indicador.setTipoIndicador(ind.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb());
				//indicador.setUnidade(ind.getItemEstrtIndResulIettr().getCodUnidMedidaIettr().getDescricaoSatb());
				indicador.setConceituacao(ind.getItemEstrtIndResulIettr().getConceituacao());
				indicador.setFonte(ind.getItemEstrtIndResulIettr().getFonteIettr());
				indicador.setAcumula(ind.getItemEstrtIndResulIettr().getIndAcumulavelIettr());
				if(ind.getItemEstrtIndResulIettr().getPeriodicidadePrdc() != null) {
					indicador.setPeriodicidade(ind.getItemEstrtIndResulIettr().getPeriodicidadePrdc().getDescricaoPrdc());
				}
				if(ind.getItemEstrtIndResulIettr().getOrdem() != null) {
					indicador.setOrdem(ind.getItemEstrtIndResulIettr().getOrdem());
				}
				
				ecar.servlet.relatorio.dto.util.Util.metaRealIndicador(ind.getItemEstrtIndResulIettr(), 
						indicador, listCiclos, acompReferenciaDao, exercicioDao);
				//Mostras apenas os da Casa Civil
				if(indicador.getOrdem() != -1) {
					dto.addIndicador(indicador);
				}
			}
			
			if(!dtoList.contains(dto)) {
				dtoList.add(dto);
			}
			RedeCicloDTO dtoFromList = dtoList.get(dtoList.indexOf(dto));
			List<RedeCicloIndicadorDTO> lInd = dtoFromList.getListIndicadores();
			List<RedeCicloIndicadorDTO> lDtoInd = dto.getListIndicadores();
			for (RedeCicloIndicadorDTO redeCicloIndicadorDTO : lDtoInd) {
				if(!lInd.contains(redeCicloIndicadorDTO)) {
					lInd.add(redeCicloIndicadorDTO);
				}
			}
			//-dtoFromList.getListIndicadores().addAll(dto.getListIndicadores());
		}
		for (RedeCicloDTO redeCicloDTO : dtoList) {
			redeCicloDTO.ordenaIndicadoresAcao();
		}
		return dtoList;
	}
	
	@SuppressWarnings("unchecked")
	public List<RedeCicloDTOCompleto> dtoRedesCiclo(List<AcompReferenciaItemAri> itens, 
			TipoFuncAcompTpfa tipoFAcomp,
			List<String> listCiclos) throws Exception {
		RedeCicloDTOCompleto dto;
		List<RedeCicloDTOCompleto> dtoList = new ArrayList<RedeCicloDTOCompleto>();
		
		for (AcompReferenciaItemAri ari : itens) {
			dto = new RedeCicloDTOCompleto();
			List<ItemEstruturaIett> ascendentes = itemEstruturaDao.getAscendentes(ari.getItemEstruturaIett());
			dto.setRede(ascendentes.get(0).getNomeIett());
			dto.setEixo(ascendentes.get(1).getNomeIett());
			dto.setAcao(ari.getItemEstruturaIett().getNomeIett());
			if(ari.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett() != null) {
				dto.setDepResp(ari.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg());
			}else {
				dto.setDepResp("N/I");
			}
			
			
			Set<AcompRelatorioArel> arels = ari.getAcompRelatorioArels();
			AcompRelatorioArel arel = null;
			for (AcompRelatorioArel acompRelatorioArel : arels) {
				if(acompRelatorioArel.getTipoFuncAcompTpfa().equals(tipoFAcomp)) {
					arel = acompRelatorioArel;
				}
			}
			
			dto.setResponsavel("");
			
			if(arel != null) {
				if(arel.getTipoFuncAcompTpfaUsuario() != null) {
					if(arel.getTipoFuncAcompTpfaUsuario().equals(tipoFAcomp)) {
						if(arel.getUsuarioUsuUltimaManutencao() != null) {
							dto.setResponsavel(arel.getUsuarioUsuUltimaManutencao().getNomeUsu());
						}
					}				
				}					
			}
			try {
				dto.setSituacao(imagePath(arel));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			dto.setParecer(arel.getDescricaoArel());
			
			//Indicadores
			List<AcompRealFisicoArf> indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari, Dominios.TODOS, false);
			RedeCicloIndicadorDTO indicador = null;
			//CalculoSinalizacao calculoSinalizacao;
			for(AcompRealFisicoArf ind : indResultados) {
				//calculoSinalizacao = new CalculoSinalizacao(ari, ind);
				//calculoSinalizacao.execute();
				
				indicador = new RedeCicloIndicadorDTO();
				indicador.setNomeIndicador(ind.getItemEstrtIndResulIettr().getNomeIettir());
				indicador.setTipoIndicador(ind.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb());
				//indicador.setUnidade(ind.getItemEstrtIndResulIettr().getCodUnidMedidaIettr().getDescricaoSatb());
				indicador.setUnidade("Un");
				//indicador.setSitInd(imagePath(calculoSinalizacao.getFaixa().getCor()));
				//if(calculoSinalizacao.getFaixaLinhaDeBase() != null) {
					//indicador.setLinhaBase(imagePath(calculoSinalizacao.getFaixaLinhaDeBase().getCor()));	
				//}
				
				//prevRealIndicadorRedes(ind.getItemEstrtIndResulIettr(), indicador, listCiclos);
				prevRealIndicadorRedesPorExercicio(ind.getItemEstrtIndResulIettr(), indicador, listCiclos);
				
				if(indicador.getOrdem() != -1) {
					dto.addIndicador(indicador);	
				}
				
			}
			
			//Plano de Ação
			List<ItemEstruturaIett> descendentes = itemEstruturaDao.getDescendentes(ari.getItemEstruturaIett(), 1);
			SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yy");
			RedePlanoAcaoDTO plano;
			for (ItemEstruturaIett iett : descendentes) {
				plano = new RedePlanoAcaoDTO();
				plano.setAtividade(iett.getNomeIett());
				if(iett.getDataInicioIett() != null) {
					plano.setInicioAtividade(sd.format(iett.getDataInicioIett()));
				}
				if(iett.getDataTerminoIett() != null) {
					plano.setTerminoAtividade(sd.format(iett.getDataTerminoIett()));
				}
				if(iett.getDataInicioIett() != null && iett.getDataTerminoIett() != null) {
					formataGantt(plano, iett);
				}
				dto.addPlanoAcao(plano);
			}
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	private void prevRealIndicadorRedes(ItemEstrtIndResulIettr ind, RedeCicloIndicadorDTO dto, List<String> listCiclos) 
		throws ECARException {
		
		IndicadorResultado wrapperIndicador = new IndicadorResultado(ind);
		EcarData ecarData;
		
		for (int i = 0; i < listCiclos.size(); i++) {
			
			Object[] mesAno = acompReferenciaDao.getMesAnoByNomeAref(listCiclos.get(i));
			
			ecarData = new EcarData(Integer.parseInt(mesAno[0] + ""), Integer.parseInt(mesAno[1] + ""));
			Previsto prev = wrapperIndicador.getPrevistoMensal(ecarData);
			Realizado real = wrapperIndicador.getRealizadoMensal(ecarData);
			
			//esta parte está ridícula!
			if(i==0) {
				if(prev != null) {
					dto.setInd_prev_1(prev.getPrevistoFormatadoPT_BR());	
				}
				if(real != null) {
					dto.setInd_real_1(real.getRealizadoFormatado());					
				}
			}else if(i==1) {
				if(prev != null) {
					dto.setInd_prev_2(prev.getPrevistoFormatadoPT_BR());	
				}
				if(real != null) {
					dto.setInd_real_2(real.getRealizadoFormatado());					
				}
			}else if(i==2) {
				if(prev != null) {
					dto.setInd_prev_3(prev.getPrevistoFormatadoPT_BR());	
				}
				if(real != null) {
					dto.setInd_real_3(real.getRealizadoFormatado());					
				}
			}
			
		}
	}
	
	private void prevRealIndicadorRedesPorExercicio(ItemEstrtIndResulIettr ind, RedeCicloIndicadorDTO dto, List<String> listCiclos) 
			throws ECARException {
			
			IndicadorResultado wrapperIndicador = new IndicadorResultado(ind);
			
			for (int i = 0; i < listCiclos.size(); i++) {
				
				Object[] mesAno = acompReferenciaDao.getMesAnoByNomeAref(listCiclos.get(i));
				ExercicioExe ex = exercicioDao.getExercicio(mesAno[0]+"", mesAno[1]+"");
				Exercicio exe = new Exercicio(ex);
				//esta parte está ridícula!
				if(i==0) {
					if(wrapperIndicador.getPrevistoNoExercicio(exe) != 0) {
						dto.setInd_prev_1(Util.formataDecimalPT_BR(wrapperIndicador.getPrevistoNoExercicio(exe)));
					}else {
						dto.setInd_prev_1("-");
					}
						
					if(wrapperIndicador.getRealizadoNoExercio(exe) != 0) {
						dto.setInd_real_1(Util.formataDecimalPT_BR(wrapperIndicador.getRealizadoNoExercio(exe)));
					}else {
						dto.setInd_real_1("-");
					}
				}else if(i==1) {
					if(wrapperIndicador.getPrevistoNoExercicio(exe) != 0) {
						dto.setInd_prev_2(Util.formataDecimalPT_BR(wrapperIndicador.getPrevistoNoExercicio(exe)));
					}else {
						dto.setInd_prev_2("-");
					}
						
					if(wrapperIndicador.getRealizadoNoExercio(exe) != 0) {
						dto.setInd_real_2(Util.formataDecimalPT_BR(wrapperIndicador.getRealizadoNoExercio(exe)));
					}else {
						dto.setInd_real_2("-");
					}
				}else if(i==2) {
					if(wrapperIndicador.getPrevistoNoExercicio(exe) != 0) {
						dto.setInd_prev_3(Util.formataDecimalPT_BR(wrapperIndicador.getPrevistoNoExercicio(exe)));
					}else {
						dto.setInd_prev_3("-");
					}
						
					if(wrapperIndicador.getRealizadoNoExercio(exe) != 0) {
						dto.setInd_real_3(Util.formataDecimalPT_BR(wrapperIndicador.getRealizadoNoExercio(exe)));
					}else {
						dto.setInd_real_3("-");
					}
				}
				
			}
		}
	
	@SuppressWarnings("unchecked")
	public List<MarcasCicloDTO> dtoMarcasCiclo(List<AcompReferenciaItemAri> itens, TipoFuncAcompTpfa tipoFAcomp) {
		MarcasCicloDTO dto;
		List<MarcasCicloDTO> dtoList = new ArrayList<MarcasCicloDTO>();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		ApontamentoDao apontamentoDao = new ApontamentoDao();
		
		for (AcompReferenciaItemAri ari : itens) {
			dto = new MarcasCicloDTO();
			
			List<ItemEstruturaIett> ascendentes = itemEstruturaDao.getAscendentes(ari.getItemEstruturaIett());
			dto.setMarca(ascendentes.get(0).getNomeIett());
			dto.setEixo("Eixo " + ascendentes.get(1).getSiglaIett() + ": " + ascendentes.get(1).getNomeIett());
			
			dto.setCodAcao(ari.getItemEstruturaIett().getSiglaIett());
			dto.setAcao(ari.getItemEstruturaIett().getNomeIett());
			
			Set<AcompRelatorioArel> arels = ari.getAcompRelatorioArels();
			AcompRelatorioArel arel = null;
			for (AcompRelatorioArel acompRelatorioArel : arels) {
				if(acompRelatorioArel.getTipoFuncAcompTpfa().equals(tipoFAcomp)) {
					arel = acompRelatorioArel;
				}
			}
			
			if(arel.getDescricaoArel() != null && arel.getDescricaoArel() != "") {
				dto.setParecer(arel.getDescricaoArel());	
			}
			if(arel.getUsuarioUsuUltimaManutencao() != null) {
				dto.setResponsavel(arel.getUsuarioUsuUltimaManutencao().getNomeUsu());	
			}
			
			if(ari.getItemEstruturaIett().getDataTerminoIett() != null) {
				dto.setPrazo(format.format(ari.getItemEstruturaIett().getDataTerminoIett()));				
			}
			
			dto.setEncaminhamentos(prepararApontamentos(apontamentoDao.loadApontamentos(ari.getItemEstruturaIett())));
			
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	private String prepararApontamentos(List<ApontamentoApt> apontamentos) {
		StringBuffer str = new StringBuffer();
		SimpleDateFormat dtF = new SimpleDateFormat("dd/MM/yyyy");
		for (ApontamentoApt apontamentoApt : apontamentos) {
			str.append("<b>");
			str.append(dtF.format(apontamentoApt.getDataInclusaoApt()));
			str.append(" - ");
			str.append(apontamentoApt.getUsuarioUsu().getNomeUsu());
			str.append("</b>");
			str.append("<p></p>");
			str.append(apontamentoApt.getTextoApt());
			str.append("<p></p>");
		}
		return str.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<CadernoPEDTO> dtoCadernoPE(List<AcompReferenciaItemAri> itens, TipoFuncAcompTpfa tipoFAcomp) throws Exception {
		CadernoPEDTO peDto = null;
		CadernoPEDTOEstrategia estrategia = null;
		CadernoPEDTOProduto produto = null;
		int qtdEstrategia = 0;
		int qtdProduto = 0;
		
		boolean novoObj = false;
		
		List<CadernoPEDTO> listCaderno = new ArrayList<CadernoPEDTO>();
		for(AcompReferenciaItemAri ari : itens) {
			
			List<ItemEstruturaIett> ascendentes = itemEstruturaDao.getAscendentes(ari.getItemEstruturaIett());
			
			ItemEstruturaIett asc = ascendentes.get(0);
			
			if((peDto = getCadernoPEFromList(listCaderno, String.valueOf(asc.getSiglaIett()))) == null) {
				novoObj = true;
				peDto = new CadernoPEDTO();
				qtdEstrategia = 0;
				qtdProduto = 0;
				
				peDto.setObjSigla(asc.getSiglaIett());
				peDto.setObjNome(asc.getNomeIett());
				
				Iterator itTpfas = asc.getItemEstUsutpfuacIettutfas().iterator();
				int i = 0;
				while(itTpfas.hasNext()) {
					ItemEstUsutpfuacIettutfa utfa = (ItemEstUsutpfuacIettutfa) itTpfas.next();
					if(i == 0) {
						peDto.setLabelFA1(utfa.getTipoFuncAcompTpfa().getLabelTpfa());
						peDto.setResponsavelFA1(utfa.getUsuarioUsu().getNomeUsu());
					}else if (i == 1) {
						peDto.setLabelFA2(utfa.getTipoFuncAcompTpfa().getLabelTpfa());
						peDto.setResponsavelFA2(utfa.getUsuarioUsu().getNomeUsu());
					}
					i++;
				}
			}else {
				novoObj = false;
			}
			
			//Estrategia
			if(ari.getItemEstruturaIett().getNivelIett() == 2) {
				estrategia = new CadernoPEDTOEstrategia(peDto);
				estrategia.setEstSigla(ari.getItemEstruturaIett().getSiglaIett());
				estrategia.setEstNome(ari.getItemEstruturaIett().getNomeIett());
				
				ItemEstUsutpfuacIettutfa utfa = (ItemEstUsutpfuacIettutfa) ari.getItemEstruturaIett().
					getItemEstUsutpfuacIettutfas().iterator().next();

				estrategia.setResponsavel(utfa.getUsuarioUsu().getNomeUsu());
				
				estrategia.setCor(imagePath((AcompRelatorioArel)ari.getAcompRelatorioArels().iterator().next()));
				
				SituacaoSit sit = ((AcompRelatorioArel)ari.getAcompRelatorioArels().iterator().next())
				.getSituacaoSit();
				
				if(sit != null) {
					estrategia.setSituacao(sit.getDescricaoSit());	
				}else {
					estrategia.setSituacao("N/I");
				}
				
				
				String parecer = ((AcompRelatorioArel)ari.getAcompRelatorioArels().iterator().next())
				.getDescricaoArel();
				
				if(parecer != null) {
					parecer = HtmlSanitizer.getText(parecer.toString());
				}
				
				estrategia.setParecer(parecer);
				
				qtdEstrategia++;
				
			}
			
			//Produto
			if(ari.getItemEstruturaIett().getNivelIett() == 3) {
				
				if(getEstrategiaFromCaderno(peDto, ascendentes.get(1).getSiglaIett()) == null) {
					//Carrega e insere a estrategia
				}else {
					produto = new CadernoPEDTOProduto(estrategia);
					produto.setProdSigla(ari.getItemEstruturaIett().getSiglaIett());
					produto.setProdNome(ari.getItemEstruturaIett().getNomeIett());
					
					qtdProduto++;
					
					Iterator it = ari.getItemEstruturaIett().getItemEstUsutpfuacIettutfas().iterator();
					UsuarioUsu usu = null;
					while(it.hasNext()) {
						ItemEstUsutpfuacIettutfa sds = (ItemEstUsutpfuacIettutfa) it.next();
						if(sds.getTipoFuncAcompTpfa().equals(tipoFAcomp)) {
							if(sds.getUsuarioUsu() != null) {
								usu = sds.getUsuarioUsu();
							}							
						}
					}

					
					if(usu != null) {
						produto.setResponsavel(usu.getNomeUsu());
					}else {
						produto.setResponsavel("Não Informado");
					}
					
					AcompRelatorioArel arel = null;
					AcompRelatorioArel arelAux = null;
					
					Iterator arelsIt = ari.getAcompRelatorioArels().iterator();
					
					while(arelsIt.hasNext()) {
						arelAux = (AcompRelatorioArel) arelsIt.next();
						if(arelAux.getTipoFuncAcompTpfa().equals(tipoFAcomp)) {
							arel = arelAux;
						}
					}
					
					produto.setCor(imagePath(arel));
					
					SituacaoSit sit = arel.getSituacaoSit();
					
					if(sit != null) {
						produto.setSituacao(sit.getDescricaoSit());
					}else {
						produto.setSituacao("N/I");
					}
					
					String parecer = arel.getDescricaoArel();
					
					if(parecer != null) {
						parecer = HtmlSanitizer.getText(parecer.toString());
					}
					
					produto.setParecer(parecer);
					
					//Indicadores
					List<AcompRealFisicoArf> indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari, Dominios.TODOS, false);
					CadernoPEDTOIndicador indicador = null;
					for(AcompRealFisicoArf ind : indResultados) {
						indicador = new CadernoPEDTOIndicador(produto);
						indicador.setNomeInd(ind.getItemEstrtIndResulIettr().getNomeIettir());
						indicador.setConceituacao(ind.getItemEstrtIndResulIettr().getConceituacao());
						indicador.setInterpretacao(ind.getItemEstrtIndResulIettr().getInterpretacao());
						indicador.setMetodoCalculo(ind.getItemEstrtIndResulIettr().getMetodoCalculo());
						geraSerieGraf(ari, ind, indicador);
					}
				}
			}
			peDto.setQtdEstrategia(qtdEstrategia);
			peDto.setQtdProduto(qtdProduto);
			
			if(novoObj) {
				listCaderno.add(peDto);
			}
		}
		return listCaderno;
	}
	

	@SuppressWarnings("unchecked")
	private void geraSerieGraf(AcompReferenciaItemAri ari,
			AcompRealFisicoArf ind, CadernoPEDTOIndicador indicador) throws ECARException {
		ExercicioExe exercicio = ari.getAcompReferenciaAref().getExercicioExe();
		List<String> meses = new ArrayList<String>();
		int mesRef = Integer.valueOf(ari.getAcompReferenciaAref().getMesAref()).intValue();
		int anoRef = Integer.valueOf(ari.getAcompReferenciaAref().getAnoAref()).intValue();	
		meses = exercicioDao.getMesesDentroDoExercicio(exercicio,mesRef,anoRef);
		
		//String tipoQtde = ind.getItemEstrtIndResulIettr().getIndTipoQtde();
		
		CadernoPEDTOIndGraf serie;
		List<CadernoPEDTOIndGraf> listSerie = new ArrayList<CadernoPEDTOIndGraf>();
		
		AcompanhamentoItemEstrutura acompanhamento = new AcompanhamentoItemEstrutura(ari);
		
		ItemEstrutura item = new ItemEstrutura(acompanhamento.getItemEstrutura().getRealObject());

		Exercicio exe = new Exercicio(exercicio);
		List<EcarData> mesesExercicio = exe.getMeses();
		
		EcarData dataInicioItem = new EcarData(item.getDataInicial());
		EcarData dataFimItem = new EcarData(item.getDataFinal());
		
		meses.clear();
		//pega os meses do item somente
		for(EcarData data: mesesExercicio){
			if(EcarData.pertenceAoIntervalo(data, dataInicioItem, dataFimItem)){
				meses.add(data.getMes() + "-" + data.getAno());
			}
		}
		
		Iterator it = meses.iterator();
		SortedMap<String, Integer> map = new TreeMap<String, Integer>();	
		
		//List<String> months  = new ArrayList<String>();
		while(it.hasNext()){
			String mesAno = it.next().toString();
			String ano  = mesAno.split("-")[1];
			if(map.get(ano) != null){
				int x = map.get(ano).intValue();
				map.put(ano, x+1);
			}else{
				map.put(ano, new Integer(1));
			}
		}
		
		Iterator ite = meses.iterator();
		String mesFormat;
		
		while(ite.hasNext()){
			String mes = ite.next().toString().split("-")[0];
			mesFormat = Data.getAbreviaturaMes(Integer.valueOf(mes));
			
			listSerie.add(new CadernoPEDTOIndGraf(mesFormat));
			
		}
		
		Map mapMeses = acompRealFisicoDao.getQtdRealizadaExercicioPorMes(exercicio, ind.getItemEstrtIndResulIettr());
		List valores = new ArrayList();
		
		int i = 0;
		for(String datas: meses){
			String mes = datas.split("-")[0];
			String ano = datas.split("-")[1];
			EcarData ecarData = new EcarData(mes, ano);
			
			IndicadorResultado wrapperIndicador = new IndicadorResultado(ind.getItemEstrtIndResulIettr());
			Previsto prev = wrapperIndicador.getPrevistoMensal(ecarData);
			
			serie = listSerie.get(i);
			
			if(prev != null){
				serie.setQtdPrev(prev.getValorPrevisto());
			}else {
				serie.setQtdPrev(0.0);
			}
			
			i++;
		}
		
		Iterator itMeses = meses.iterator();
		i = 0;
		while(itMeses.hasNext()){
			boolean possuiValor = true;
			String mesAnoMap = itMeses.next().toString();
			Object m = mapMeses.get(mesAnoMap);
			if(m == null) {
				possuiValor = false;
			}
			
			serie = listSerie.get(i);	
			if(possuiValor){
				Double valor = new Double(m.toString());
				valores.add(valor);
				/*
				String strValor = "";
				if("Q".equals(tipoQtde)) {
					strValor = Pagina.trocaNullComDecimal(valor);
				} else {
					strValor = Pagina.trocaNullMoeda(valor);
				}
				*/
				serie.setQtdRea(valor);
				
			} else {
				serie.setQtdRea(0.0);
			}
			i++;
		}
		
		indicador.setSerieGraf(listSerie);
	}

	@SuppressWarnings("unchecked")
	public List<SituacaoProdutoDTO> dtoSituacaoProdutos(List<AcompRelatorioArel> itens, 
			TipoFuncAcompTpfa tipoFAcomp,
			String modelo, List<String> listCiclos) throws Exception {
		
		List<SituacaoProdutoDTO> listDTO = new ArrayList<SituacaoProdutoDTO>();
		SituacaoProdutoDTO dto = null;
		ItemEstruturaIett itemIett = null;
		SituacaoSit sit = null;
		List<Object> list;
		Map<ItemEstruturaIett, List<Object>> map = new LinkedHashMap<ItemEstruturaIett, List<Object>>();
		
		for(AcompRelatorioArel arel : itens) {
			
			if(arel.getTipoFuncAcompTpfa().equals(tipoFAcomp)) {
				if(!map.containsKey(arel.getAcompReferenciaItemAri().getItemEstruturaIett())) {
					itemIett = arel.getAcompReferenciaItemAri().getItemEstruturaIett();
					
					sit = arel.getSituacaoSit();
					list = new ArrayList<Object>();
					list.add(arel.getAcompReferenciaItemAri());
					list.add(sit);
					
					List<Cor> cores = new ArrayList<Cor>();
					
					cores.add(arel.getCor());
					
					list.add(cores);
					
					list.add(arel.getDescricaoArel());
					map.put(itemIett, list);
				}else {
					List<Object> l = map.get(arel.getAcompReferenciaItemAri().getItemEstruturaIett());
					List<Cor> cores = (List<Cor>) l.get(2);
					cores.add(arel.getCor());
				}				
			}
		}

		Iterator<ItemEstruturaIett> it = map.keySet().iterator();
		List<MonitoramentoCicloDTO> monitoramentoDTOList = new ArrayList<MonitoramentoCicloDTO>();
		
		while (it.hasNext()) {
			ItemEstruturaIett iett = it.next();
			List l = (List) map.get(iett);
			
			if(modelo.equals("ECAR-001A")) {
				dto = dtoSituacaoProdutos((AcompReferenciaItemAri)l.get(0),
						(SituacaoSit)l.get(1),
						(List<Cor>) l.get(2),
						tipoFAcomp);
			}else if (modelo.equals("ECAR-001D")) {
				dto = dtoSituacaoProdParecer((AcompReferenciaItemAri)l.get(0),
						(SituacaoSit)l.get(1),
						(List<Cor>) l.get(2),
						(String) l.get(3),
						tipoFAcomp,
						listCiclos);
			}else if (modelo.equals("ECAR-001E")) {
				
			}
			
			if(dto != null) {
				listDTO.add(dto);
			}
		}
		return listDTO;
	}
	
	@SuppressWarnings("unchecked")
	public SituacaoProdutoDTO dtoSituacaoProdParecer(AcompReferenciaItemAri ari, 
			SituacaoSit sit, 
			List<Cor> cores,
			String parecer,
			TipoFuncAcompTpfa tipoFAcomp,
			List<String> listCiclos) throws Exception {
		
		SituacaoProdutoDTO dto = new SituacaoProdutoDTO();
		List<ItemEstruturaIett> ascendentes = itemEstruturaDao.getAscendentes(ari.getItemEstruturaIett());
		dto.setObjSigla(ascendentes.get(0).getSiglaIett());
		dto.setEstSigla(ascendentes.get(1).getSiglaIett());
		
		dto.setProdSigla(ari.getItemEstruturaIett().getSiglaIett());
		dto.setProdNome(ari.getItemEstruturaIett().getNomeIett());
		
		if(cores.size() > 0) {
			dto.setCor1(imagePath(cores.get(0), tipoFAcomp));	
		}else {
			dto.setCor1(imagePath(null, tipoFAcomp));
		}
		
		if(cores.size() > 1) {
			dto.setCor2(imagePath(cores.get(1), tipoFAcomp));	
		}else {
			dto.setCor2(imagePath(null, tipoFAcomp));
		}
		
		if(cores.size() > 2) {
			dto.setCor3(imagePath(cores.get(2), tipoFAcomp));	
		}else {
			dto.setCor3(imagePath(null, tipoFAcomp));
		}
		
		dto.setNap(null);
		
		if(sit != null) {
			dto.setSituacao(sit.getDescricaoSit());
		}else {
			dto.setSituacao("N/I");
		}
		
		PeriodicidadePrdc period = ari.getItemEstruturaIett().getPeriodicidadePrdc();
		if(period != null) {
			dto.setPeriodicidade(period.getDescricaoPrdc());
		}else {
			dto.setPeriodicidade("N/I");
		}	
		
		if(ari.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett() != null) {
			dto.setOrgSigla(ari.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg());
		}else {
			dto.setOrgSigla("N/I");
		}

		if(parecer == null || parecer.trim().equals("")) {
			parecer = "N/I";
		}
		//dto.setParecer(HtmlSanitizer.getText(parecer));
		dto.setParecer(parecer);
		
		Set<AcompRelatorioArel> arels = ari.getAcompRelatorioArels();
		
		for (AcompRelatorioArel arel : arels) {
			if(arel.getTipoFuncAcompTpfaUsuario() != null) {
				if(arel.getTipoFuncAcompTpfaUsuario().equals(tipoFAcomp)) {
					dto.setResponsavel(arel.getUsuarioUsuUltimaManutencao().getNomeUsu());
				}				
			}
		}
		
		//Co-Responsavel
		Set<ItemEstrutEntidadeIette> listEntidades =  ari.getItemEstruturaIett().getItemEstrutEntidadeIettes();
		StringBuffer ents = new StringBuffer();
		for(ItemEstrutEntidadeIette entidade : listEntidades) {
			ents.append(entidade.getEntidadeEnt().getNomeEnt() + ", ");
		}
		if(ents.length() == 0) {
			dto.setListCoResponsavel("Não Possui");
		}else {
			dto.setListCoResponsavel(ents.toString());
		}
		
		//Indicadores
		List<AcompRealFisicoArf> indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari, Dominios.TODOS, false);
		RedeCicloIndicadorDTO indicador = null;
		CalculoSinalizacao calculoSinalizacao;
		for(AcompRealFisicoArf ind : indResultados) {
			if(!ind.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb().equals("Marcador")) {
				calculoSinalizacao = new CalculoSinalizacao(ari, ind);
				calculoSinalizacao.execute();
				
				indicador = new RedeCicloIndicadorDTO();
				indicador.setNomeIndicador(ind.getItemEstrtIndResulIettr().getNomeIettir());
				
				indicador.setTipoIndicador(ind.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb());
				//indicador.setUnidade(ind.getItemEstrtIndResulIettr().getCodUnidMedidaIettr().getDescricaoSatb());
				//indicador.setUnidade("Un");
				if(calculoSinalizacao.getFaixa() != null) {
					indicador.setSitInd(imagePath(calculoSinalizacao.getFaixa().getCor()));
				}
				if(calculoSinalizacao.getFaixaLinhaDeBase() != null) {
					indicador.setLinhaBase(imagePath(calculoSinalizacao.getFaixaLinhaDeBase().getCor()));	
				}
				
				prevRealIndicadorRedes(ind.getItemEstrtIndResulIettr(), indicador, listCiclos);
				
				dto.addIndicadorPainel(indicador);				
			}
		}
		
		
		return dto;
	}
	
	@SuppressWarnings("unchecked")
	public SituacaoProdutoDTO dtoSituacaoProdutos(AcompReferenciaItemAri ari, 
			SituacaoSit sit, 
			List<Cor> cores,
			TipoFuncAcompTpfa tipoFAcomp) throws Exception {
		
		SituacaoProdutoDTO dto = new SituacaoProdutoDTO();
		List<ItemEstruturaIett> ascendentes = itemEstruturaDao.getAscendentes(ari.getItemEstruturaIett());
		dto.setObjSigla(ascendentes.get(0).getSiglaIett());
		dto.setEstSigla(ascendentes.get(1).getSiglaIett());
		
		dto.setProdSigla(ari.getItemEstruturaIett().getSiglaIett());
		dto.setProdNome(ari.getItemEstruturaIett().getNomeIett());
		
		if(cores.size() > 0) {
			dto.setCor1(imagePath(cores.get(0), tipoFAcomp));	
		}else {
			dto.setCor1(imagePath(null, tipoFAcomp));
		}
		
		if(cores.size() > 1) {
			dto.setCor2(imagePath(cores.get(1), tipoFAcomp));	
		}else {
			dto.setCor2(imagePath(null, tipoFAcomp));
		}
		
		if(cores.size() > 2) {
			dto.setCor3(imagePath(cores.get(2), tipoFAcomp));	
		}else {
			dto.setCor3(imagePath(null, tipoFAcomp));
		}
		
		dto.setNap(null);
		
		OrgaoOrg org = ari.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett();
		if(org != null) {
			dto.setOrgSigla(org.getSiglaOrg());
		}else {
			dto.setOrgSigla("N/I");
		}
		
		if(sit != null) {
			dto.setSituacao(sit.getDescricaoSit());
		}else {
			dto.setSituacao("N/I");
		}
		
		PeriodicidadePrdc period = ari.getItemEstruturaIett().getPeriodicidadePrdc();
		if(period != null) {
			dto.setPeriodicidade(period.getDescricaoPrdc());
		}else {
			dto.setPeriodicidade("N/I");
		}

		//Indicadores
		
		List<AcompRealFisicoArf> indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari, Dominios.TODOS, false);
		
		SituacaoProdutoIndicadorDTO sitProd;
		CalculoSinalizacao calculoSinalizacao;
		
		for(AcompRealFisicoArf arf : indResultados) {
			calculoSinalizacao = new CalculoSinalizacao(ari, arf);
			//calculoSinalizacao.execute();
			
			sitProd = new SituacaoProdutoIndicadorDTO();
			
			sitProd.setNomeIndicador(arf.getItemEstrtIndResulIettr().getNomeIettir());
			//sitProd.setPercent(String.valueOf(calculoSinalizacao.getCalculo()));
			//sitProd.setSinalizacao(imagePath(calculoSinalizacao.getFaixa().getCor()));
			
			dto.addIndicador(sitProd);
		}
		
		if(dto.getListIndicadores().size() == 0) {
			dto.addIndicador(new SituacaoProdutoIndicadorDTO("Não Possui"));
		}
		
		//Co-Responsavel
		Set<ItemEstrutEntidadeIette> listEntidades =  ari.getItemEstruturaIett().getItemEstrutEntidadeIettes();
		StringBuffer ents = new StringBuffer();
		for(ItemEstrutEntidadeIette entidade : listEntidades) {
			ents.append(entidade.getEntidadeEnt().getNomeEnt() + ", ");
		}
		if(ents.length() == 0) {
			dto.setListCoResponsavel("Não Possui");
		}else {
			dto.setListCoResponsavel(ents.toString());
		}
		
		return dto;
	}
	
	public void dtoItensMonitorados() {
		
	}
	
	private String imagePath(AcompRelatorioArel arel) {
		return RelatorioPE.class.getResource(
				"/images/relAcomp/" + 
				corDao.getImagemRelatorio(arel.getCor(), arel.getTipoFuncAcompTpfa())).getPath();
	}
	
	private String imagePath(Cor cor, TipoFuncAcompTpfa tipoFAcomp) {
		return RelatorioPE.class.getResource(
				"/images/relAcomp/" + 
				corDao.getImagemRelatorio(cor, tipoFAcomp)).getPath();
	}
	
	private String imagePath(Cor cor) throws ECARException {
		return RelatorioPE.class.getResource(
				"/images/relAcomp/" + 
				corDao.getImagemIndResul(cor)).getPath();
	}
	
	private CadernoPEDTO getCadernoPEFromList(List<CadernoPEDTO> list, String siglaObj) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getObjSigla().equalsIgnoreCase(siglaObj)) {
				return list.get(i);
			}
		}
		return null;
	}
	
	private CadernoPEDTOEstrategia getEstrategiaFromCaderno(CadernoPEDTO caderno, String estSigla) {
		for(int i = 0; i < caderno.getListEstrategias().size(); i++) {
			if(caderno.getListEstrategias().get(i).getEstSigla().equalsIgnoreCase(estSigla)) {
				return caderno.getListEstrategias().get(i);
			}
		}
		return null;
	}
}