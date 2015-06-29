package ecar.api.facade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;

import comum.database.HibernateUtil;
import comum.util.Data;

import ecar.dao.AcompRealFisicoDao;
import ecar.dao.ExercicioDao;
import ecar.dao.ItemEstrutLocalDao;
import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrutLocalIettl;
import ecar.pojo.ItemEstrutUploadIettup;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.SisAtributoSatb;
import ecar.util.Dominios;

public class ItemEstrutura implements EcarWrapperInterface<ItemEstruturaIett>{

	
	private ItemEstruturaDao dao = new ItemEstruturaDao(null);
	private ItemEstruturaIett item = null;
	
	public ItemEstrutura(){
		
	}
	
	
	public ItemEstrutura(long id) throws ECARException{
		item = (ItemEstruturaIett) dao.buscar(ItemEstruturaIett.class, id);
		
		if(item == null){
			throw new ECARException();
		}
		
	}
	
	
	public ItemEstrutura(ItemEstruturaIett item){
		this.item = item;
	}
	
	
	public long getId(){
		return item.getCodIett(); 
	}
	
	
	public String getNome(){
		return item.getNomeIett();
	}
	
	
	/**
	 * Retorna o n�vel hier�rquico do item. Quanto mais
	 * alto o n�mero mais baixo ele est� na hierarquia. 
	 * 
	 * @return
	 */
	public int getNivel(){
		return item.getNivelIett();
	}
	
	public Date getDataInicial() throws ECARException{
		return dao.ObtemDataInicialItemEstrutura(item);
	}
	
	public Date getDataFinal() throws ECARException{
		return dao.ObtemDataTerminoItemEstrutura(item);
	}
	
	
	public List<AcompanhamentoItemEstrutura> getListaDeAcompanhamentos(){
		List<AcompanhamentoItemEstrutura> l = new ArrayList<AcompanhamentoItemEstrutura>();
		Set set = this.item.getAcompReferenciaItemAris();
	
		Iterator i = set.iterator();
		
		while(i.hasNext()){
			AcompReferenciaItemAri a = (AcompReferenciaItemAri) i.next();
			l.add(new AcompanhamentoItemEstrutura(a));
		}
		
		return l;
	}
	
	/**
	 * Retorna uma lista de valores realizados do item de acordo com a situa��o
	 * que pode ser:<br/>
	 * 
	 * Dominios.TODOS<br/>
	 * Dominios.CONCLUIDOS<br/>
	 * Dominios.NAO_CONCLUIDOS<br/>
	 * 
	 * @see Dominios
	 * @param situacao - Valores poss�veis descritos na classe {@link ecar.util.Dominios}  
	 * @return
	 * @throws Exception 
	 */
	public List<Realizado> getRealizados(AcompanhamentoItemEstrutura ari, String situacao) throws Exception{
		List<Realizado> list = null;
		AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(null);
		List indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari.getRealObject(), situacao, false);		

		if(indResultados != null && indResultados.size() > 0){
			list = new ArrayList<Realizado>();
			for(Object obj: indResultados){
				AcompRealFisicoArf arf = (AcompRealFisicoArf) obj;
				if(arf != null){
					list.add(new Realizado(arf));
				}
			}
		}
		
		return list;
	}
	
	/**
	 * Retorna todos os indicadores de um item de todos os tipos de 
	 * acompanhamento
	 * 
	 * @return Lista de indicadores
	 */
	public List<IndicadorResultado> getIndicadoresResultado(){
		List<IndicadorResultado> l = new ArrayList<IndicadorResultado>();
		Set set = this.item.getItemEstrtIndResulIettrs();
		
		if(set != null && set.size() > 0){
			Iterator i = set.iterator();
			while(i.hasNext()){
				ItemEstrtIndResulIettr indicador = (ItemEstrtIndResulIettr)i.next();
				IndicadorResultado ind = new IndicadorResultado(indicador);
				l.add(ind);
			}
		}
		
		sortList(l);
		
		return l;
	}
	
	
	/**
	 * Retorna os indicadores do item de estrutura de acordo com o tipo de acompanhamento
	 * 
	 * @param ari
	 * @param configuracao
	 * @return Lista de indicadores ou NULL se n�o existirem indicadores
	 */
	public List<IndicadorResultado> getIndicadoresResultado(AcompanhamentoItemEstrutura ari, ConfiguracaoCfg configuracao){
		//obt�m todos os indicadores do item
		List<IndicadorResultado> indicadores = getIndicadoresResultado();
		//filtra pelo tipo de acompanhamento
		List<IndicadorResultado> res = matchIndicadoresAcompanhamento(indicadores, ari, configuracao);
		
		return res;
	}
	
	
	
	public IndicadorResultado getIndicadorById(long id){
		Set set = this.item.getItemEstrtIndResulIettrs();
		
		IndicadorResultado res = null;
		if(set != null && set.size() > 0){
			Iterator i = set.iterator();
			while(i.hasNext()){
				ItemEstrtIndResulIettr indicador = (ItemEstrtIndResulIettr)i.next();
				if(indicador.getCodIettir() == id){
					res = new IndicadorResultado(indicador);
				}
				
			}
		}
		
		return res;
	}
	
	/**
	 * Retorna os locais cadastrados para esse item de acordo com a abrang�ncia
	 * do item
	 * 
	 * @return
	 * @throws ECARException 
	 */
	public List<Local> getLocais() throws ECARException{
		List<Local> locais = new ArrayList<Local>();
		
		List<ItemEstrutLocalIettl> locaisItem = new ArrayList<ItemEstrutLocalIettl>(item.getItemEstrutLocalIettls());
		if(locaisItem !=null && locaisItem.size() > 0){
			for(ItemEstrutLocalIettl local: locaisItem ){
				if(local.getLocalItemLit().getCodLgp() == getAbrangencia()){
					locais.add(new Local(local.getLocalItemLit()));
				}
			}
		}
		
		return locais;
	}


	/**
	 * Desce recursicamente em um hierarquia de locais para pegar os
	 * previstos para uma determinada hierarquia de locais.
	 * 
	 * @param abrangencia
	 * @return
	 * @throws ECARException
	 */
	public List<Local> getLocais(int abrangencia) throws ECARException{
		List<Local> locais = new ArrayList<Local>();
		List<Local> l = getLocais();
		
		if(l != null && l.size() > 0){
			for(Local itemLocal: l){
				List<Local> buffer = itemLocal.getFilhos(abrangencia);
				if(buffer != null && buffer.size() > 0){
					locais.addAll(buffer);
				}
			}
		}

		return locais;
	}
	
	
	/**
	 * Retorna os meses de um item
	 * @return
	 * @throws ECARException 
	 */
	public List<EcarData> getMeses() throws ECARException{
		List<EcarData> months = null;
		
		List<GregorianCalendar> meses = dao.GetMesesReferencia(item);
		
		if(meses != null && meses.size() > 0){
			months = new ArrayList<EcarData>();
			for(GregorianCalendar calendar: meses){
				 months.add(new EcarData(calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR) ));
				}
			}

		if(months != null){
			Collections.sort(months);
		}
		
		return months;
	}
	
	
	
	/**
	 * Retorna os meses associados ao item de estrutura filtrado pelo ano. Se n�o
	 * houver nenhum mes a lista ser� null
	 * 
	 * @param ano
	 * @return
	 * @throws ECARException 
	 */
	public List<EcarData> getMeses(int ano) throws ECARException{
		List<EcarData> months = null;
		
		List<GregorianCalendar> meses = dao.GetMesesReferenciaFiltraAno(item, (long)ano);
		
		if(meses != null && meses.size() > 0){
			months = new ArrayList<EcarData>();
			for(GregorianCalendar calendar: meses){
				if(ano == calendar.get(Calendar.YEAR)){
				 months.add(new EcarData(calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR) ));
				}
			}
		}
		
		Collections.sort(months);
		
		return months;
	}

	/**
	 * Retorna lista de exercicios cadastrados para o item
	 * @return
	 * @throws ECARException
	 */
	public List<Exercicio> getExercicios() throws ECARException{
		List<Exercicio> exercicios = new ArrayList<Exercicio>();
		ExercicioDao exercicioDao = new ExercicioDao(null);
		List<ExercicioExe> listaExeAnt = exercicioDao.getExerciciosProjecaoResultados(getRealObject().getCodIett());
		
		if(listaExeAnt != null){
			for(ExercicioExe ecarExe: listaExeAnt){
				exercicios.add(new Exercicio(ecarExe.getCodExe()));
			}
		}
		
		return exercicios;

	}
	
	
	
	/**
	 * Retorna o c�digo da abrang�ncia
	 * 
	 * 
	 * @return
	 * @throws ECARException
	 */
	public long getAbrangencia() throws ECARException{
		ItemEstrutLocalDao itemEstrutLocalDao = new ItemEstrutLocalDao(null);
		long i = itemEstrutLocalDao.getAbrangenciaAsInteger(item.getCodIett());
		return i;
	}
	
	
	
	/**
	 * Retorna a string correspondente a abrang�ncia
	 * 
	 * 
	 * @return
	 * @throws ECARException
	 */
	public String getAbrangenciaAsString() throws ECARException{
		//ItemEstrutLocalDao itemEstrutLocalDao = new ItemEstrutLocalDao(null);
		//String a = itemEstrutLocalDao.getAbrangencia(item.getCodIett());
		return GrupoLocal.getInstance().getNomeGrupo((int)getAbrangencia());
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * @see ecar.api.facade.EcarWrapperInterface#getRealObject()
	 */
	public ItemEstruturaIett getRealObject(){
		return item;
	}
	
	
	/**
	 * Retorna verdadeiro se a nova data de inicio ou fim n�o impactar 
	 * em valores j� informados de previsto ou se existe acompanhamentos
	 * criados para um indicador que fiquem de fora do intervalo das novas
	 * datas 
	 * 
	 * @param novaDataInicio
	 * @param novaDataFim
	 * @return
	 * @throws ECARException 
	 */
	public boolean podeAlterarData(String novaDataInicio, String novaDataFim) throws ECARException{
		
		if(novaDataInicio == null || novaDataInicio.equals("") || novaDataFim == null || novaDataFim.equals("") || this.item.getDataInicioIett() == null || this.item.getDataTerminoIett() == null){
			return true; //rever estar regra
		}
		
		if(this.item.getDataInicioIett().compareTo((Data.parseDate(novaDataInicio))) == 0 
				&&(this.item.getDataTerminoIett()!=null && this.item.getDataTerminoIett().compareTo(Data.parseDate(novaDataFim)) == 0)) {
			return true;	
		}
		
		GregorianCalendar start = new GregorianCalendar(Integer.parseInt(novaDataInicio.split("/")[2]), 
				Integer.parseInt(novaDataInicio.split("/")[1])-1, 
				Integer.parseInt(novaDataInicio.split("/")[0]));


		GregorianCalendar end = new GregorianCalendar(Integer.parseInt(novaDataFim.split("/")[2]), 
				Integer.parseInt(novaDataFim.split("/")[1])-1, 
				Integer.parseInt(novaDataFim.split("/")[0]));



		if(start.getTime().equals(getDataInicial()) && end.getTime().equals(getDataFinal())){
			return true;
		}

		//usa-se a classe EcarData para poder comparar se uma data pertence a um intervalo
		EcarData novoInicio = new EcarData(start);
		EcarData novoFim = new EcarData(end);

		//obtem a lista de indicadores
		List<IndicadorResultado> indicadores = getIndicadoresResultado();

		//verifica se algum indicador tem um valor previsto fora 
		//do novo intervalo
		if(indicadores != null && indicadores.size() > 0){
			for(IndicadorResultado indicador: indicadores){

				//verifica se tem acompanhamentos fora do novo per�odo
				for(Realizado realizado: indicador.getAcompanhamentosRealizado()){
					if(EcarData.pertenceAoIntervalo(realizado.getData(), novoInicio, novoFim) == false){
						
						String datamotivo = "";
						if(novoInicio.compareTo(realizado.getData()) == EcarData.DEPOIS){
							//data inicial n�o pode ser modificada...
							datamotivo = this.getEstrutura().getLabelAtributo("dataInicioIett");
						}else{
							datamotivo = this.getEstrutura().getLabelAtributo("dataTerminoIett");
						}
												
						//key-property#motivo#data#nome do indicador
						String strKey = "itemEstrutura.dataslimites.validacao.acompanhamento.erro#"+datamotivo+"#"+realizado.getData().getDataFormatadaMesExtenso()+
									  "#"+indicador.getNome();
						
						setErrorMessageKey(strKey);	
						return false;
					}
				}

				//verifica se possui valores previstos fora do novo intervalo
				if(indicador.isIndicadorPorLocal()){
					List<PrevistoLocal> previstos = indicador.getPrevistosPorLocal();
					if(previstos != null && previstos.size() > 0){
						for(PrevistoLocal previsto: previstos){
							if(EcarData.pertenceAoIntervalo(previsto.getData(), novoInicio, novoFim) == false){
								//n�o pertence ao intervalo
								String motivo = "";
								if(novoInicio.compareTo(previsto.getData()) == EcarData.DEPOIS){
									//data inicial n�o pode ser modificada...
									motivo = this.getEstrutura().getLabelAtributo("dataInicioIett");
								}else{
									motivo = this.getEstrutura().getLabelAtributo("dataTerminoIett");
								}

								String strKey = "itemEstrutura.dataslimites.validacao.previstolocal.erro#"+motivo+"#"+previsto.getLocal()+
											 "#"+previsto.getData().getDataFormatadaMesExtenso()+"#"+indicador.getNome();
								
								setErrorMessageKey(strKey);	
								return false;
							}
						}
					}else{
						continue;
					}
				}else{//indicador n�o � por local
					List<Previsto> previstos = indicador.getValoresPrevistosPorMes();
					if(previstos != null && previstos.size() > 0){
						for(Previsto previsto: previstos){
							if(EcarData.pertenceAoIntervalo(previsto.getData(), novoInicio, novoFim) == false){
								//n�o pertence ao intervalo

								String motivo = "";
								if(novoInicio.compareTo(previsto.getData()) == EcarData.DEPOIS){
									//data inicial n�o pode ser modificada...
									motivo = this.getEstrutura().getLabelAtributo("dataInicioIett");
								}else{
									motivo = this.getEstrutura().getLabelAtributo("dataTerminoIett");
								}
								

								String strKey ="itemEstrutura.dataslimites.validacao.previsto.erro#"+motivo+"#"+previsto.getData().getDataFormatadaMesExtenso()+
												"#"+indicador.getNome();
								
								setErrorMessageKey(strKey);	
								return false;
							}
						}
					}else{
						continue;
					}
				}
			}
		}
		return true;
	}

	
	private String errorMessageKey = null;
	
	
	private void setErrorMessageKey(String key){
		this.errorMessageKey = key;
	}
	
	public String getErrorKey(){
		return this.errorMessageKey;
	}
	
	
	/**
	 * Retorna a estrutura a qual o item pertence.
	 * @return
	 * @throws ECARException 
	 */
	public Estrutura getEstrutura() throws ECARException{
		return new Estrutura(getRealObject().getEstruturaEtt());
	}
	
	/**
	 * @return true se o item est� bloqueado.
	 */
	public boolean isBloqueado(){
		return (getRealObject().getIndBloqPlanejamentoIett() != null && getRealObject().getIndBloqPlanejamentoIett().equals(ValidaPermissao.SIM)); 
	}
	
	/**
	 * Retorna uma string contendo informa��es dos arquivos anexos
	 * associados ao item. Essas informa��es s�o usadas para exporta��o
	 * de dados.
	 * 
	 * @return
	 */
	public List<EcarAnexo> getAnexos(){
		Set attach = getRealObject().getItemEstrutUploadIettups();
		 
		List<EcarAnexo> anexos = new ArrayList<EcarAnexo>();
		
		if(attach != null && attach.size() > 0){
		   for(Object a: attach){
			ItemEstrutUploadIettup up = (ItemEstrutUploadIettup) a;
			anexos.add(new EcarAnexo(up));
		   }
		}
		
		return anexos;
	}
	
	
//	/**
//	 * Retorna a lista de indicadores conclu�dos do item
//	 * @return
//	 */
//	public List<IndicadorResultado> getIndicadoresConcluidos(){
//		List<IndicadorResultado> concluidos = null;
//		
//		if(this.getIndicadoresResultado() != null){
//			concluidos = new ArrayList<IndicadorResultado>();
//			
//			for(IndicadorResultado i: this.getIndicadoresResultado()){
//				if(i.isConcluido()){
//					concluidos.add(i);
//				}
//			}
//		}
//		return concluidos;
//	}
	
	/**
	 * Retorna os indicadores conclu�dos de um determinado item de estrutura ordenados
	 * pelo tipo do indicador. Esse m�todo foi refatorado devido a problemas de performance.
	 * 
	 * @return Lista de indicadores conclu�dos
	 */
	public  List<IndicadorResultado> getIndicadoresConcluidos(){
		
    	StringBuilder query = new StringBuilder();

    	//selecionar os indicadores que possuem algum arf 
    	//com situacao concluida
    	query.append("select arf.itemEstrtIndResulIettr from AcompRealFisicoArf as arf ");
    	query.append("left join arf.situacaoSit as situacao ");
    	query.append("where situacao.indConcluidoSit= 'S' and arf.itemEstruturaIett.codIett =:codIett");
    	query.append(" and arf.itemEstruturaIett.indAtivoIett = 'S' and arf.itemEstrtIndResulIettr.indAtivoIettr = 'S' ");
    	query.append(" order by arf.itemEstrtIndResulIettr.sisAtributoSatb.descricaoSatb, arf.itemEstrtIndResulIettr.nomeIettir");
    	
    	Query q = HibernateUtil.currentSession().createQuery(query.toString());
    	q.setLong("codIett", this.getId());
    	
    	List res = q.list();
    	List<IndicadorResultado> concluidos = null;
    	
    	if(res != null && res.size() > 0){
    		concluidos = new ArrayList<IndicadorResultado>();
    		for(Object ind: res){
    			concluidos.add(new IndicadorResultado((ItemEstrtIndResulIettr)ind));
    		}
    	}

		return concluidos;
	}
	
	/**
	 * Retorna os indicadores concluidos de acordo com o acompanhamento
	 * 
	 * @param ari
	 * @param configuracao
	 * @return
	 */
	public  List<IndicadorResultado> getIndicadoresConcluidos(AcompanhamentoItemEstrutura ari, ConfiguracaoCfg configuracao){
		//pega todos os indicadores conclu�dos do item
		List<IndicadorResultado> indicadores = getIndicadoresConcluidos();
		//filtra pelo tipo de acompanhamento
		List<IndicadorResultado> res = matchIndicadoresAcompanhamento(indicadores, ari, configuracao);
		
		return res;
	}
	
	
	
	/**
	 * Retorna uma lista de indicadores conclu�dos at� uma determinada data, incluindo
	 * a mesma se o argumento incluiData � true.
	 *
	 * @param data
	 * @return List<IndicadorResultado>
	 */
	public List<IndicadorResultado> getIndicadoresConcluidos(EcarData data, boolean incluiData){
		List<IndicadorResultado> ind =  getIndicadoresConcluidos();
		List<IndicadorResultado> ret = null;
		
		if(ind != null){
			for(IndicadorResultado i: ind){
				if(ret == null) ret = new ArrayList<IndicadorResultado>();
				
				if(i.getDataConclusao().compareTo(data) == EcarData.ANTES){
					ret.add(i);
				}else if(i.getDataConclusao().compareTo(data) == EcarData.IGUAL && incluiData == true ){
					ret.add(i);
				}
			}
		}
		return ret;
	}
	
	/**
	 * Retorna uma lista de indicadores conclu�dos de acordo com o acompanhamento at� uma determinada data, incluindo
	 * a mesma se o argumento incluiData � true.
	 *  
	 * @param data
	 * @param incluiData
	 * @param ari
	 * @return
	 */
	public List<IndicadorResultado> getIndicadoresConcluidos(EcarData data, boolean incluiData, AcompanhamentoItemEstrutura ari,
			ConfiguracaoCfg configuracao){
		//obt�m a lista de indicadores concluidos at� uma determinada data
		List<IndicadorResultado> indicadores = getIndicadoresConcluidos(data, incluiData);
		//filtra de acordo com o acompanhamento
		List<IndicadorResultado> res = matchIndicadoresAcompanhamento(indicadores, ari, configuracao);
		
		return res;
	}
	
	
	/**
	 * Retorna os indicadores n�o conclu�dos independendente da data. 
	 * 
	 * @return
	 */
	public List<IndicadorResultado> getIndicadoresNaoConcluidos(){
		List<IndicadorResultado> todos =  getIndicadoresResultado();
		List<IndicadorResultado> concluidos = getIndicadoresConcluidos();

		if(todos != null && concluidos != null) {
			todos.removeAll(concluidos);
		}

		sortList(todos);
		return todos;
	}
	
	/**
	 * Retorna os indicadores n�o conclu�dos e os conclu�dos na data 
	 * especificada pois ele ainda podem ser modificados no per�do selecionado,
	 * ap�s esse per�do ele n�o poder� ser alterado. 
	 *  
	 * @param data
	 * @param incluiData
	 * @return
	 */
	public List<IndicadorResultado> getIndicadoresNaoConcluidos(EcarData data){
		
		List<IndicadorResultado> todos =  getIndicadoresResultado();
		List<IndicadorResultado> concluidos = getIndicadoresConcluidos();

		//remove os conclu�dos 
		if(todos != null && concluidos != null){
			todos.removeAll(concluidos);
			//inclui os indicadores conclu�dos na data corrente
			//ser� considerado como n�o conclu�do conforme as regras
			//especificados no mantis 0011551
			//Os indicadores conclu�dos no m�s corrente ser�o exibidos
			//no monitoramento com "N�o Conlu�dos", no m�s posterior
			//esses mesmos indicadores somente ser�o apresentadosem Todos
			//e Conclu�dos e ter�o seus campos bloqueados.
			for(IndicadorResultado indicador: concluidos){
				if(indicador.isConcluido() && indicador.getDataConclusao().equals(data)){
					todos.add(indicador);
				}
			}
		}
		
		
		//ordena a lista uma vez que a view realizadoFisico.jsp
		//espera que 
		sortList(todos);
		return todos;
		
	}
	
	/**
	 * Retorna os indicadores n�o conclu�dos e os conclu�dos na data 
	 * especificada pois ele ainda podem ser modificados no per�do selecionado,
	 * ap�s esse per�do ele n�o poder� ser alterado. Os indicadores devolvidos
	 * pelo m�todo ser�o aquele pertencentes ao acompanhamento(ari) 
	 * 
	 * @param data
	 * @param ari
	 * @param configuracao
	 * @return
	 */
	public List<IndicadorResultado> getIndicadoresNaoConcluidos(EcarData data, AcompanhamentoItemEstrutura ari, ConfiguracaoCfg configuracao){
		List<IndicadorResultado> indicadores = getIndicadoresNaoConcluidos(data);
		return matchIndicadoresAcompanhamento(indicadores, ari, configuracao);
	}
	
	/**
	 * 
	 * Retorna os valores realizados do indicadores de acordo com a situa��o. Para o caso de "Todos" o
	 * argumento data pode ser null, caso contr�rio n�o poder� ser nulo.
	 * 
	 * Esse m�todo � um helper method usado em realizadoFisico.jsp para popular a lista de realizados.
	 * 
	 * @param ari
	 * @param configuracao
	 * @param data
	 * @return Os valores realizados dos indicadores encontrados
	 * @throws ECARException 
	 */
	public List getRealizadosIndicadoresPorSituacao(String situacao, AcompanhamentoItemEstrutura ari, ConfiguracaoCfg configuracao,
			EcarData data, boolean incluiData) throws ECARException{
		
		List lAcompRF = new ArrayList();
		
		if(situacao.equals(Dominios.TODOS)){
			List<IndicadorResultado> indicadores = getIndicadoresResultado(ari, configuracao);
			
			if(indicadores != null){
				for(IndicadorResultado indicador: indicadores){
					//n�o de deve incluir indicadores conclu�dos depois do per�odo selecionado
					if(indicador.isConcluido() && 
							(indicador.getDataConclusao().compareTo(data) == EcarData.ANTES ||
							 indicador.getDataConclusao().compareTo(data) == EcarData.IGUAL)){
						
						lAcompRF.add(indicador.getValorRealizadoConclusao().getRealObject());
					}else{
						Realizado real = indicador.getRealizadoMensal(data);
						//se o indicador � novo e ainda n�o tem um realizado para
						//esse per�odo.
						if(real != null){
							lAcompRF.add(real.getRealObject());
						}
					}
				}
			}
		}
		
		if(situacao.equals(Dominios.NAO_CONCLUIDOS)){
			List<IndicadorResultado> indicadores = 
				getIndicadoresNaoConcluidos(data, ari, configuracao);

			if(indicadores != null){			
				for(IndicadorResultado indicador: indicadores){
					//para o caso de um indicador novo o realizado ser� null
					//n�o adicionamos na lista abaixo
					Realizado r = indicador.getRealizadoMensal(data);
					if(r != null){
						lAcompRF.add(indicador.getRealizadoMensal(data).getRealObject());
					}
				}				
			}
		}
		
		if(situacao.equals(Dominios.CONCLUIDOS)){
			//pega os indicadores conclu�do antes da data do per�odo incluiData = false
			List<IndicadorResultado> indicadores = getIndicadoresConcluidos(data, false, ari, configuracao);
			if(indicadores != null){
				for(IndicadorResultado indicador: indicadores){
					lAcompRF.add(indicador.getValorRealizadoConclusao().getRealObject());
				}				
			}
		}
		
		
		return lAcompRF;
		
		
// snippet retirado de realizadoFisico.jsp para obter indicadores de acordo com a situacao		
//		if(tipoSelecao.equals("T")){//todos
//			List<IndicadorResultado> indicadores = itemSelectionado.getIndicadoresResultado(_ariWrapper, _configuracao);
//			if(indicadores != null){
//		
//				for(IndicadorResultado indicador: indicadores){
//					if(indicador.isConcluido()){
//						lAcompRF.add(indicador.getValorRealizadoConclusao().getRealObject());
//					}else{
//						Realizado real = indicador.getRealizadoMensal(periodoSelectionado);
//						//se o indicador � novo e ainda n�o tem um realizado para
//						//esse per�odo.
//						if(real != null){
//							lAcompRF.add(real.getRealObject());
//						}
//					}
//				}
//			}
//			
//		}else if(tipoSelecao.equals("C")) {//conclu�dos
//			List<IndicadorResultado> indicadores = 
//				itemSelectionado.getIndicadoresConcluidos(periodoSelectionado, true, _ariWrapper, _configuracao);
//			
//			if(indicadores != null){
//				for(IndicadorResultado indicador: indicadores){
//					lAcompRF.add(indicador.getValorRealizadoConclusao().getRealObject());
//				}				
//			}
//			
//		}else{//n�o conclu�dos
//			/* selecionar os indicadores dependendo da situacao selecionada*/
//			List<IndicadorResultado> indicadores = 
//				itemSelectionado.getIndicadoresNaoConcluidos(periodoSelectionado, _ariWrapper, _configuracao);
//
//			if(indicadores != null){			
//				for(IndicadorResultado indicador: indicadores){
//					//para o caso de um indicador novo o realizado ser� null
//					//n�o adicionamos na lista abaixo
//					Realizado r = indicador.getRealizadoMensal(periodoSelectionado);
//					if(r != null){
//						lAcompRF.add(indicador.getRealizadoMensal(periodoSelectionado).getRealObject());
//					}
//				}				
//			}
//		}
	}
	

	
	/**
	 * Retorna os indicadores do item de acordo com sua situa��o e o acompanhamento
	 * 
	 * @param situacao
	 * @param ari
	 * @param configuracao
	 * @param data
	 * @param incluiData
	 * @return
	 * @throws ECARException
	 */
	public List<IndicadorResultado> getIndicadoresPorSituacao(String situacao, AcompanhamentoItemEstrutura ari, ConfiguracaoCfg configuracao,
			EcarData data, boolean incluiData) throws ECARException{
		
		
		List<IndicadorResultado> indicadores = new ArrayList<IndicadorResultado>();
		
		if(situacao.equals(Dominios.TODOS)){
			indicadores = getIndicadoresResultado(ari, configuracao);
		}
		
		if(situacao.equals(Dominios.NAO_CONCLUIDOS)){
			indicadores = getIndicadoresNaoConcluidos(data, ari, configuracao);
		}
		
		if(situacao.equals(Dominios.CONCLUIDOS)){
			indicadores = getIndicadoresConcluidos(data, false, ari, configuracao);
		}
		
		return indicadores;
	}
	
	
	/**
	 * Ordena os indicadores de acordo com o tipo dele {Indicador Resultado,
	 * Meta F�sica, etc..}. Isso � necess�rio para a renderiza��o das informa��es
	 * dos indicadores no jsp realizadoFisico.jsp
	 * 
	 * @param list
	 */
	private void sortList(List<IndicadorResultado> list){
		//ordenando conforme o tipo do indicador
		if(list != null && list.size() > 0){
			Collections.sort(list, new Comparator(){
				public int compare(Object indicador1, Object indicador2) {
					
					String tipoIndicador1 = ((IndicadorResultado)indicador1).getDescricaoTipoIndicador();
					String tipoIndicador2 = ((IndicadorResultado)indicador2).getDescricaoTipoIndicador(); 
					
					if(tipoIndicador1 == null && tipoIndicador2 == null){
						return 0;
					}
					else if(tipoIndicador1 != null && tipoIndicador2 == null){
						return 1;
					}
					else if(tipoIndicador1 == null && tipoIndicador2 != null){
						return -1;
					}
					
					return tipoIndicador1.compareTo(tipoIndicador2);
				}
			});
		}
	}

	
	/**
	 * Seleciona os indicadores de uma lista de acordo com os tipos
	 * de indicadores que pertencem ao acompanhamento
	 * 
	 * @param indicadores
	 * @param ari
	 * @param configuracao
	 * @return Lista de indicadores ou null caso n�o seja encontrado nenhum indicador
	 */
	private List<IndicadorResultado> matchIndicadoresAcompanhamento(List<IndicadorResultado> indicadores,
			AcompanhamentoItemEstrutura ari,
			ConfiguracaoCfg configuracao){
		
		
		List<IndicadorResultado> _indicadores_ = null;
		
		
		if(indicadores == null || indicadores.size() == 0){
			return null;
		}
		
		if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null){
			AcompReferenciaItemAri _ari = ari.getRealObject();
			Set sisAtributos = _ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getSisAtributoSatbs();
			List listaAtributos = new ArrayList();
			
			if(sisAtributos != null){
				//se nenhum tipo de indicador foi configurado ent�o nao tem 
				//nenhum 
				if(sisAtributos.isEmpty() == true){
					return _indicadores_;
				}
				
				for(Object atributo: sisAtributos){
					SisAtributoSatb _atb = (SisAtributoSatb)atributo;
					if(_atb.getSisGrupoAtributoSga().equals(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas())){
						listaAtributos.add(_atb);
					}
				}
			}
			
			
			//retirar indicadores que n�o pertencem ao atributos
			//filtrados acima
			if(listaAtributos.isEmpty() == false ){
				for(IndicadorResultado indicador: indicadores){
					if(indicador.getRealObject().getSisAtributoSatb() != null &&
							listaAtributos.contains(indicador.getRealObject().getSisAtributoSatb())){
						
						if(_indicadores_ == null){
							_indicadores_ = new ArrayList<IndicadorResultado>();
						}
						
						_indicadores_.add(indicador);
					}
				}
			}
		}
		
		return _indicadores_;
		
	}
	
	
}
