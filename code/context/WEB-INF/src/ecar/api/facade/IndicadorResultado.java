package ecar.api.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ecar.dao.AcompRealFisicoDao;
import ecar.dao.ExercicioDao;
import ecar.dao.ItemEstrtIndResulDao;
import ecar.exception.ECARException;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompRealFisicoLocalArfl;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrtIndResulLocalIettirl;
import ecar.pojo.ItemEstrutFisicoIettf;
import ecar.servlet.grafico.GeradorGrafico;

/**
 * Classe wrapper para facilitar a manipula��o de indicadores desonerando a necessidade de usar m�todos com 
 * nomes complicados como getAcompRealFisicoArfs() que passa a ser acessado por getRealizados().
 * 
 * Al�m disso evita a replica��o de c�digo em jsp, como por exemplo, para o caso de pegar o valor realizado levando
 * em conta se o indicador � por local ou n�o. Na implementa��o original toda vez que esse valor  � necess�rio, os 
 * testes s�o repetidos para verificar se o indicador � por localidade, se acumul�vel, etc. 
 * Tamb�m facilita a corre��o de bugs por que o c�digo est� em um �nico lugar e fora do jsp.  
 *  
 * 
 * @author 82035644020
 */
public class IndicadorResultado implements EcarWrapperInterface<ItemEstrtIndResulIettr>{

	
	private ItemEstrtIndResulIettr indicador = null;
	private ItemEstrtIndResulDao dao = null;
	private Set<Local> locais = null;
	private EcarData dataConclusao = null;
	private Realizado valorConclusao = null;
	
	public IndicadorResultado(long codIettir) throws ECARException{
		dao = new ItemEstrtIndResulDao(null);
		indicador = (ItemEstrtIndResulIettr) dao.buscar(ItemEstrtIndResulIettr.class, codIettir);

		if(indicador == null){
			throw new ECARException();
		}
	}
	
	
	public IndicadorResultado(ItemEstrtIndResulIettr indicador){
		dao = new ItemEstrtIndResulDao(null);
		this.indicador = indicador;
	}
	
	public long getId(){
		return indicador.getCodIettir();
	}
	
	public String getNome(){
		return indicador.getNomeIettir();
	}

	public boolean isAcumulavel(){
		return indicador.getIndAcumulavelIettr().equals("S");
	}
	
	public long getItemEstruturaID(){
		return indicador.getItemEstruturaIett().getCodIett();
	}
	
	
	public ItemEstrutura getItemEstrutura(){
		return new ItemEstrutura(indicador.getItemEstruturaIett());
	}
	
	
	public boolean isIndicadorPorLocal(){
		return "S".equals(indicador.getIndPrevPorLocal());
	}
	
	public String getUnidadeMedida() {
		return indicador.getUnidMedidaIettr();
	}
	
	/**
	 * Return true se o indicador tem um label para
	 * agrupamento de indicadores. Usado no gr�fico de
	 * previsto vs realizado.
	 * 
	 * @see GeradorGrafico
	 * @return
	 */
	public boolean hasLabelGrupo(){
		return indicador.getLabelGraficoGrupoIettir() != null;
	}
	
	/**
	 * Se o indicador � agrupado com outros indicadores ele possui
	 * um nome para o grupo, sen�o retorna null
	 * @return
	 */
	public String getLabelGrupo(){
		return indicador.getLabelGraficoGrupoIettir();
	}
	
	
	
	public boolean isAtivo(){
		return indicador.getIndAtivoIettr().equals("S");
	}
	
	
	
	/**
	 * Retorna o objeto original utilizado no ecar 
	 * 
	 * @return
	 */
	public ItemEstrtIndResulIettr getRealObject(){
		return indicador;
	}
	

	/**
	 * Retorna quais indicadores est�o agrupados
	 * com esse indicador. Utilizado na constru��o
	 * do gr�fico.
	 * 
	 * 
	 * @return
	 * @throws ECARException 
	 */
	public List<IndicadorResultado> getIndicadoresDoGrupo() throws ECARException{
		List<IndicadorResultado> indicadores = new ArrayList<IndicadorResultado>();
		List listaIndicadoresGrupo = dao.retornaIndicadoresGraficoGrupo(indicador);
		
		if(listaIndicadoresGrupo !=null && listaIndicadoresGrupo.size() > 0){
			Iterator i = listaIndicadoresGrupo.iterator();
			while(i.hasNext()){
				ItemEstrtIndResulIettr ind = (ItemEstrtIndResulIettr) i.next();
				if(ind != null){
					indicadores.add(new IndicadorResultado(ind));
				}
			}
		}
		
		return indicadores;
	}
	
	/**
	 * 
	 * Retorna a lista de acompanhamentos informados para o
	 * indicado. Lista de objetos @see{AcompRealFisicoArf}.
	 * 
	 * @return
	 */
	public List<Realizado> getAcompanhamentosRealizado(){
		List<Realizado> l = new ArrayList<Realizado>();
		Set set = indicador.getAcompRealFisicoArfs();
		if(set != null && set.size() > 0){
			Iterator it = set.iterator();
			while(it.hasNext()){
				AcompRealFisicoArf arf = (AcompRealFisicoArf)it.next();
				l.add(new Realizado(arf));
			}
		}
		
		return l;
	}
	
	/**
	 * Para um acompanhamento f�sico retorna um realizado 
	 * por local.
	 * 
	 * 
	 * @param realizado
	 * @return
	 */
	public List<RealizadoLocal> getAcompanhamentoFisicoLocal(Realizado realizado){
		List<RealizadoLocal> l = new ArrayList<RealizadoLocal>();
		Set rlocal = realizado.getRealObject().getAcompRealFisicoLocalArfls();
		if(rlocal != null && rlocal.size() > 0){
			for(Object o: rlocal){
			    	l.add(new RealizadoLocal((AcompRealFisicoLocalArfl)o));
			}
		}
		
		return l;
	}
	
	
	
	/**
	 * Retorna os exerc�cios de um acompanhamento realizado
	 * do indicador. Obt�m o AcompRealFisicoArf e retorna
	 * seus exerc�cios.
	 * 
	 * Retorna null caso n�o haja exerc�cios.
	 * 
	 * 
	 * @param Realizado - wrapper para a classe @see{AcompRealFisicoArf}
	 * 
	 * @return
	 * @throws ECARException 
	 */
	public List<Exercicio> getExerciciosDoAcompanhamento(Realizado realizado) throws ECARException{
		List<Exercicio> exe = null;
		AcompRealFisicoArf indicadorAcompRealFisico = realizado.getRealObject();
		
		ExercicioDao exeDao = new ExercicioDao(null);
		List<ExercicioExe> exerciciosAnterioresIndicador = exeDao.getExerciciosProjecaoResultados(indicadorAcompRealFisico.getItemEstruturaIett().getCodIett());

		if(exerciciosAnterioresIndicador!=null && exerciciosAnterioresIndicador.size()>0){
			exe = new ArrayList<Exercicio>();
			for(ExercicioExe exercicio: exerciciosAnterioresIndicador){
				exe.add(new Exercicio(exercicio));
			}
		}
		
		return exe;
		
	}
	
	
	
	
	/**
	 * Retorna o valor realizado do indicador no exerc�cio dado at� o acompanhamento dado.
	 * 
	 * 
	 * @param exe
	 * @param indicador
	 * @return
	 * @throws ECARException
	 */
	public double getRealizadoNoExercicio(Exercicio exe, AcompanhamentoItemEstrutura acompanhamento) throws ECARException{
	
		AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(null);

		double value = 0;
		if(this.isAcumulavel()){
			value = acompRealFisicoDao.getQtdRealizadaExercicio(exe.getRealObject(), 
					this.getRealObject(), 
					acompanhamento.getRealObject().getAcompReferenciaAref());
		}else{
			value = acompRealFisicoDao.getQtdRealizadaExercicioNaoAcumulavel(exe.getRealObject(), 
					this.getRealObject(), acompanhamento.getRealObject().getAcompReferenciaAref());
		}

		return value;
	}
	

	
	/**
	 * Retorna a quantidade realizada de um indicador em um determinado exerc�cio
	 * @param exe
	 * @return
	 * @throws NumberFormatException
	 * @throws ECARException
	 */
	public double getRealizadoNoExercio(Exercicio exe) throws NumberFormatException, ECARException{
		AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(null);

		String mesRef = Integer.toString(exe.getDataFinal().getMonth()+1);
		String anoRef = Integer.toString(exe.getDataFinal().getYear()+1900);

		double valor = 0.0;
		
		if(this.isAcumulavel()){
			valor = acompRealFisicoDao.getQtdIndicadorGrupoRealizadaExercicio(indicador, 
					exe.getRealObject(), 
					Long.valueOf(anoRef), 
					Long.valueOf(mesRef));
		}else{
			valor = acompRealFisicoDao.getQtdIndicadorGrupoRealizadaExercicioNaoAcumulavel(indicador,
					exe.getRealObject(), Long.valueOf(anoRef), Long.valueOf(mesRef));
		}

		
		return valor;
	}

	
	
	
	
	
	/**
	 * Retorna um mapa contendo os valor de cada indicador do grupo no seguinte
	 * formato:
	 * 
	 *  Map<ano, List<Map<Indicador, valor>>
	 * 
	 * Exemplo de uso veja em @see{@link GeradorGrafico#gerarGraficoPorExercicio(javax.servlet.http.HttpServletRequest)}
	 * 
	 * @param codIettir
	 * @return
	 * @throws ECARException
	 */
	public Map<Exercicio, List<Map<IndicadorResultado, Double>>> getRealizadoGrupoIndicador() throws ECARException{
		ItemEstrutura itemEstrutura = new ItemEstrutura(getItemEstrutura().getId());
		IndicadorResultado indicador = itemEstrutura.getIndicadorById(getId());
		
		Map<Exercicio, List<Map<IndicadorResultado, Double>>> map = new TreeMap<Exercicio, List<Map<IndicadorResultado, Double>>>();
		
		for(Exercicio exe: getItemEstrutura().getExercicios()){
			List<Map<IndicadorResultado, Double>> listMapsIndicadorValor = new ArrayList<Map<IndicadorResultado, Double>>();
			for(IndicadorResultado ind: indicador.getIndicadoresDoGrupo()){
				
				String mesRef = Integer.toString(exe.getDataFinal().getMonth()+1);
				String anoRef = Integer.toString(exe.getDataFinal().getYear()+1900);
				
				//List<Double> val = ind.getValoresRealizados(exe, getMesReferencia(), getAnoReferencia());
				List<Double> val = ind.getValoresRealizados(exe, mesRef, anoRef);
				
				if(val != null && val.size() > 0){
					Map<IndicadorResultado, Double> indicadorValor = new HashMap<IndicadorResultado, Double>(); 
					for(Double valor: val){
						indicadorValor.put(ind, valor);
						listMapsIndicadorValor.add(indicadorValor);
					}
					map.put(exe, listMapsIndicadorValor);
				}
			}
		}

		return map;
		
	}
	
	
	
	
	
	
	/**
	 * Retorna um hash de valores realizados dos acompanhamentos 
	 * para o exerc�cios passado como argumento. O m�todo itera
	 * pelos acompanhamentos f�sicos (@see{AcompRealFisicoArf}) e
	 * se o acompanhamento possui valor para o exerc�cio dado. 
	 * 
	 * O hash tem o seguinte formato Map<ANO-EXERCICIO, VALOR>
	 * 
	 * Retorna null se n�o existerem valores para o exerc�cio dado.
	 * 
	 * @param exe
	 * @return
	 * @throws ECARException 
	 */
	public List<Double> getValoresRealizados(Exercicio exe, String mesRef, String anoRef) throws ECARException{
		List<Double> valores = null;

		//obtem a lista de acompanhamentos f�sicos AcompRealFisicoArf
		List<Realizado> realizados = getAcompanhamentosRealizado();
		if(realizados.size() > 0 && realizados != null){
			//para cada acompanhamento pega o exerc�cios
			outer:
			for(Realizado real: realizados){
				List<Exercicio> exercicios = real.getExercicios(anoRef);
				for(Exercicio e: exercicios){
					if(e.equals(exe)){
						if(valores == null){valores = new ArrayList<Double>();}
						//se � acumul�vel...
						double valor = 0;
						AcompRealFisicoArf acompRealFisico = real.getRealObject();
						AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(null);

						if(this.isAcumulavel()){
							valor = acompRealFisicoDao.getQtdIndicadorGrupoRealizadaExercicio(indicador, 
									exe.getRealObject(), 
									Long.valueOf(anoRef), 
									Long.valueOf(mesRef));
						}else{
							valor = acompRealFisicoDao.getQtdIndicadorGrupoRealizadaExercicioNaoAcumulavel(indicador,
									exe.getRealObject(), Long.valueOf(anoRef), Long.valueOf(mesRef));
						}
						
						valores.add(valor);
						break outer;//passa para o pr�ximo acompanhamento e nao para o pr�ximo exerc�cio
					}
				}
			}
		}
		
		return valores;
	}

	/**
	 * Retorna o valor realizado do indicador dado o m�s e o ano.
	 * 
	 * m�todo alternativo para acessar o realizado por mes
	 * AcompRealFisicoDao dao = new AcompRealFisicoDao(null);
	 * dao.getQtdRealizadaMesAno(indResul, mes, ano)
	 * 
	 * 
	 * @param mesRef
	 * @param anoRef
	 * @return
	 * @throws ECARException
	 */
	public Realizado getRealizadoMensal(long mesRef, long anoRef) throws ECARException{
		AcompRealFisicoDao dao = new AcompRealFisicoDao(null);

		if(isIndicadorPorLocal()){
			Realizado real = getRealizadoTotalLocal((int)mesRef, (int)anoRef);
			if(real != null){
				return real;	
			}
		}else{
			AcompRealFisicoArf realizado = dao.buscarPorIettir(mesRef, anoRef, getRealObject().getCodIettir());

			if(realizado != null){
				Realizado real = new Realizado(realizado);
				if(real != null){
					return real;
				}
			}
		}
		return null;
	}
	

	/**
	 * Retorna o valor do realizado mensal de acordo com a data 
	 * passada. @see EcarData
	 * 
	 * @param data
	 * @return
	 * @throws ECARException
	 */
	public Realizado getRealizadoMensal(EcarData data) throws ECARException{
		return getRealizadoMensal(data.getMes(), data.getAno());
	}
	
	
	/**
	 * 
	 * Retorna uma lista de valores realizados para o indicador dado uma data inicial e final
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return
	 * @throws ECARException
	 */
	public List<Realizado> getRealizadoMensal(Date dataInicial, Date dataFinal) throws ECARException{
		List<Realizado> valores = new ArrayList<Realizado>();
		
		for(int ano = dataInicial.getYear(); ano <= dataFinal.getYear(); ano++ ){
			for(int mes = dataInicial.getMonth()+1 ; mes <= dataFinal.getMonth()+1; mes++){
					valores.add(getRealizadoMensal(mes, ano));
			}
		}
		
		return valores;
	}
	
	
	
	/**
	 * 
	 * Retorna o realizado mensal dado um exerc�cio. Retorna o valor de cada mes dentro do exerc�cio.
	 * 
	 * @param exe
	 * @return
	 * @throws ECARException
	 */
	public Map<EcarData, Double> getRealizadoMensal(Exercicio exe) throws ECARException{
		Map<EcarData, Double> realizados = new TreeMap<EcarData, Double>(); 
		AcompRealFisicoDao dao = new AcompRealFisicoDao(null);
		Map map = dao.getQtdRealizadaExercicioPorMes(exe.getRealObject(), this.getRealObject());
		
		if(map != null && map.size() > 0){
			for(Object data: map.keySet()){
				EcarData d = new EcarData(data.toString());
				Double valor = (Double) map.get(data);
				
				if(valor == null) valor = 0.0;
				
				realizados.put(d, valor);
			}
		}
		
		return realizados;
	}
	
	
	
	/**
	 * Retorna o valor previsto para o indicador dado o m�s e o ano.
	 * Os valores est�o ordenados na lista. 
	 * 
	 * @param mesRef
	 * @param anoRef
	 * @return
	 */
	public List<Previsto> getValoresPrevistosPorMes(){
		List<Previsto> previstos = new ArrayList<Previsto>();
		//todos os previstos dentro 
		Set<ItemEstrutFisicoIettf> prev = this.indicador.getItemEstrutFisicoIettfs();
		if(prev != null && prev.size() > 0){
			for(ItemEstrutFisicoIettf pr: prev){
				Previsto obj = new Previsto(pr);
					previstos.add(obj);
			}
		}
				
		return previstos;
	}

	
	/**
	 * Retorna o realizado do indicador totalizando os valores
	 * realizados em cada local
	 * 
	 * 
	 * @param mes
	 * @param ano
	 * @return
	 * @throws ECARException
	 */
	public Realizado getRealizadoTotalLocal(int mes, int ano) throws ECARException{
		AcompRealFisicoDao  acompRealFisicoDao = new AcompRealFisicoDao(null);
		
		AcompRealFisicoArf arfMesAno = acompRealFisicoDao.buscarPorIettir(
				(long)mes,
				(long)ano,
				indicador.getCodIettir());
	
		Realizado real = null;
		if(arfMesAno != null){
		   real = new Realizado(arfMesAno);
		}
	
		return real;
	}
	
	
	/**
	 * Retorna o valor previsto dado um m�s e um ano
	 * 
	 * @param mes
	 * @param ano
	 * @return
	 */
	public Previsto getPrevistoMensal(int mes, int ano){
		List<Previsto>  previstos = getValoresPrevistosPorMes();
		
		if(previstos!= null && previstos.size() > 0){
			for(Previsto previsto:  previstos){
				if( (previsto.getData().getAno() == ano) && (previsto.getData().getMes() == mes) ){
					return previsto;
				}
			}
		}
		return null;
	}

	/**
	 * Retorna o valor previsto dado um m� e ano encapsulados em um objeto EcarData
	 * @param data
	 * @return
	 */
	public Previsto getPrevistoMensal(EcarData data){
		return getPrevistoMensal(data.getMes(), data.getAno());
	}
	
	
	/**
	 * Retorna o previsto para o indicador para o exerc�cio dado.
	 * 
	 * 
	 * @param exe
	 * @param indicador
	 * @return
	 */
	
	/*N�o funciona mais como deveria devido a modifica��o */
	//Mantis 0010128 - Qtd prevista n�o � mais informado por exerc�cio e sim por m�s
	public double getPrevistoNoExercicio(Exercicio exe){
		ItemEstrtIndResulDao dao = new ItemEstrtIndResulDao(null);
		Double previsto = dao.getQtdPrevistoExercicio(indicador, exe.getRealObject());
		return previsto;
	}


	/**
	 * Retorna o valor previsto para um local dado de acordo com o 
	 * exercicio.
	 * O valor null denota que n�o tem valor previsto para o local na
	 * data informada.
	 * 
	 * @param exe
	 * @param local
	 * @return
	 */
	public PrevistoLocal getPrevistoPorLocal(EcarData data, Local local){
		
		List<PrevistoLocal> previstos = getPrevistosPorLocal();
		
		if(previstos!=null && previstos.size() > 0){
			for(PrevistoLocal prevLocal: previstos){
				
				EcarData prevData = prevLocal.getData();
				Local localPrev = prevLocal.getLocal();
				
				if(prevLocal.getData().equals(data) && prevLocal.getLocal().equals(local)){
					return prevLocal;
				}
			}
		}
		
		return null;
	}

	/**
	 * Retorna os locais que tenham algum valor previsto
	 * @return
	 */
	public Set<Local> getLocaisPrevistos(){
		
		if(locais != null){
			return locais;
		}else{
			List<PrevistoLocal> previstos = getPrevistosPorLocal();
			locais = new HashSet<Local>();
			if(previstos != null && previstos.size() >0){
				for(PrevistoLocal prev: previstos){
					Local l = prev.getLocal();
					locais.add(l);
				}
			}
			return locais;
		}
	}
	
	
	/**
	 * Retorna a lista de previstos por local do indicador se 
	 * existirem.
	 * 
	 * 
	 * @return
	 */
	public List<PrevistoLocal> getPrevistosPorLocal(){
		
		List<PrevistoLocal> previstosPorLocal = null;

		List<ItemEstrutFisicoIettf> pl =  new ArrayList<ItemEstrutFisicoIettf>(getRealObject().getItemEstrutFisicoIettfs());
  		
		if(pl != null && pl.size() > 0){
			previstosPorLocal = new ArrayList<PrevistoLocal>();
			for(ItemEstrutFisicoIettf p: pl){
				if(p.getItemEstrtIndResulLocalIettirls()!=null && p.getItemEstrtIndResulLocalIettirls().size() > 0){
					for(ItemEstrtIndResulLocalIettirl plocal: p.getItemEstrtIndResulLocalIettirls()){
							previstosPorLocal.add(new PrevistoLocal(plocal));
					}
				}
			}
		}
		
		return previstosPorLocal;
	}
	

	/**
	 * Retorna todos os previstos para um determinado local
	 * 
	 * @param local
	 * @return
	 */
	public List<PrevistoLocal> getPrevistosPorLocal(Local local){
		List<PrevistoLocal> previstos = new ArrayList<PrevistoLocal>();
		
		List<PrevistoLocal> plocais = getPrevistosPorLocal();
		if(plocais != null && plocais.size() > 0){
			for(PrevistoLocal prev: plocais){
				if(prev.getLocal().equals(local)){
					previstos.add(prev);
				}
			}
		}
		
		return previstos;
	}

	
	public boolean isMoeda(){
		return indicador.getIndTipoQtde().equals("V");
	}
	
	
	/**
	 * Retorna o tipo do indicador: Indicador Resultado, Meta F�sica, etc.. 
	 * 
	 * @return
	 */
	public String getDescricaoTipoIndicador(){
		return indicador.getSisAtributoSatb().getDescricaoSatb();
	}
	
	
	public String toString(){
		return "Indicador id: " + this.getId() + " Nome: " + this.getNome() + " Tipo: " + this.getDescricaoTipoIndicador();
	}
	
	
	/**
	 * Retorna os valores realizados do indicador
	 * @return
	 */
	public List<Realizado> getRealizados(){
	
		List<Realizado> realizados = null;
		
		if(getRealObject().getAcompRealFisicoArfs() != null && getRealObject().getAcompRealFisicoArfs().size() > 0){
			realizados = new ArrayList<Realizado>();	
			for(Object real: getRealObject().getAcompRealFisicoArfs()){
				realizados.add(new Realizado((AcompRealFisicoArf) real));
			}
		}
		
		return realizados;
	}
	
	/**
	 * @return TRUE se o indicador est� uma situa��o conclu�da.
	 */
	public boolean isConcluido(){
		
		if(this.dataConclusao != null && this.valorConclusao != null){
			return true;
		}
		
		if(getRealizados() == null){
			return false;
		}else{
			for(Realizado real: getRealizados()){
				if(real.isConcluido()){
					dataConclusao = real.getData();
					this.valorConclusao = real;
					return true;
				}
			}
		}
		
		return false;
	}	
	
	/**
	 * Retorna a data de conclus�o do indicador ou null caso n�o esteja conclu�do.
	 * @return
	 */
	public EcarData getDataConclusao(){
		if(isConcluido() == true){
			return dataConclusao;
		}
		return null;
	}
	
	/**
	 * Retorna o valor realizado no momento da conclus�o. Nulo
	 * caso n�o esteja conclu�do
	 * @return
	 */
	public Realizado getValorRealizadoConclusao(){
		if(isConcluido() == true){
			return this.valorConclusao;
		}
		
		return null;
	}
	
	/**
	 * Retorna a quantidade parcial realizada de um indicador em um determinado exercício.
	 * Exemplo: Se o parâmetro mesRef for igual a 10, o sistema somente irá considerar os valores realizados do exercício onde mês <= 10
	 *  
	 * @param exe
	 * @param mesRef
	 * @return
	 * @throws NumberFormatException
	 * @throws ECARException
	 */
	public double getRealizadoParcialNoExercio(Exercicio exe, long mesRef) throws NumberFormatException, ECARException{
		AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(null);
		
		String anoRef = Integer.toString(exe.getDataFinal().getYear()+1900);

		double valor = 0.0;

		if(this.isAcumulavel()){
			valor = acompRealFisicoDao.getQtdIndicadorGrupoRealizadaExercicio(indicador, 
					exe.getRealObject(), 
					Long.valueOf(anoRef), 
					Long.valueOf(mesRef));
		}else{
			valor = acompRealFisicoDao.getQtdIndicadorGrupoRealizadaExercicioNaoAcumulavel(indicador,
					exe.getRealObject(), Long.valueOf(anoRef), Long.valueOf(mesRef));
		}

		
		return valor;
	}
	
	/**
	 * Retorna a quantidade parcial prevista de um indicador em um determinado exercício.
	 * Exemplo: Se o parâmetro mesRef for igual a 10, o sistema somente irá considerar os valores previstos do exercício onde mês <= 10
	 * 
	 * @param exe
	 * @param indicador
	 * @return
	 */
	public double getPrevistoParcialNoExercicio(Exercicio exe, long mesRef){
		ItemEstrtIndResulDao dao = new ItemEstrtIndResulDao(null);
		Double previsto = dao.getQtdPrevistoParcialExercicio(indicador, exe.getRealObject(), mesRef);
		return previsto;
	}
	
	public String getIndTipoQtde(){
		return indicador.getIndTipoQtde();
	}
	
	public boolean equals(Object obj){
		if(obj instanceof IndicadorResultado){
			if(this.getId() == ((IndicadorResultado)obj).getId()){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
}
	
	

