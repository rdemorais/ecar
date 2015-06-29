/*
 * Created on 27/10/2004
 * 
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import comum.database.Dao;
import comum.util.Data;

import ecar.exception.ECARException;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstrutFisicoIettf;
import ecar.pojo.ItemEstruturaIett;

/**
 * @author evandro
 *
 */
public class ExercicioDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public ExercicioDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	
	/**
	 * Verifica e exclui
	 *  
	 * @author n/c
	 * @param exercicio
	 * @throws ECARException
	 */
	public void excluir(ExercicioExe exercicio) throws ECARException {	    
       	boolean excluir = true;
	    if(contar(exercicio.getEfItemEstContaEfiecs()) > 0){ 
	        excluir = false;
		    throw new ECARException("exercicio.exclusao.erro.efItemEstContaEfiecs");
	    }   
	    if(contar(exercicio.getEfItemEstPrevisaoEfieps()) > 0){ 
	        excluir = false;
		    throw new ECARException("exercicio.exclusao.erro.efItemEstPrevisaoEfieps");
	    }
	    if(contar(exercicio.getItemEstrutFisicoIettfs()) > 0){ 
	        excluir = false;
		    throw new ECARException("exercicio.exclusao.erro.itemEstrutFisicoIettfs");
	    }
/*
 * FIXME: Reaplicar valida��o para hist�rico.
	    if(contar(exercicio.getEfItemEstPrevhistEfiephs()) > 0){ 
	        excluir = false;
		    throw new ECARException("exercicio.exclusao.erro.efItemEstPrevhistEfiephs");
	    }
*/
	    if(contar(exercicio.getAcompReferenciaArefs()) > 0){ 
	        excluir = false;
		    throw new ECARException("exercicio.exclusao.erro.acompReferenciaArefs");
	    }
	    if(excluir)
	        super.excluir(exercicio);	
	}
	
	/**
	 * Retorna uma lista de exercicios que tem data inicial ou final que se sobrepoem ao exercicio informado
	 * como par�metro.
	 * N�o deve haver sobreposicao de datas de nenhum exercicio cadastrado, ou seja, quando termina um come�a
	 * o outro. Isto porque em acomp_relatorio, precisamos determinar um e somente um exercicio para um dado
	 * m�s e ano.
	 * A logica � a seguinte:
	 * 
	 * dataInicialT = data inicial do exercicio que queremos cadastrar<br>
	 * dataFinalT = data final do exercicio que queremos cadastrar<br>
	 * dataInicialX = data inicial de um exercicio qualquer j� cadastrado<br>
	 * dataFinalX = data final de um exercicio j� cadastrado<br>
	 * <pre> 
	 * exercicioX								o-------------o					
	 * exercicioT                o------------o                 o-------------o      
	 * 
	 * se dataFinalT < dataInicialX ou dataInicialT > dataFinalX ent�o<br> 
	 *    o exercicio T nao se sobrepoe ao exercicio X<br>
	 * </pre>
	 * 
	 * @author garten
	 * @param exercicio
	 * @return
	 * @throws ECARException
	 */
	public List getExerciciosComSobreposicaoData(ExercicioExe exercicio) throws ECARException {
	    List resultado = new ArrayList();
	    
	    try {
            String select = "from ExercicioExe e " +
            		" where not (:dataFinal < e.dataInicialExe or :dataInicial > e.dataFinalExe)";
            
            if(exercicio.getCodExe() != null)
            	select += " and e.codExe <> :codExe";
            
            Query q = this.session.createQuery(select);
            
            q.setDate("dataFinal", exercicio.getDataFinalExe());
            q.setDate("dataInicial", exercicio.getDataInicialExe());
            
            if(exercicio.getCodExe() != null)
            	q.setLong("codExe", exercicio.getCodExe().longValue());
            
            resultado = q.list();
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
	    
	    return (resultado);
	}
	

	/** 
	 * Salva um exercicio somente se n�o sobrepuser nenhum exercicio j� cadastrado
	 * @author garten
	 * @param exercicio
	 * @throws ECARException
	 */
	public void salvar(ExercicioExe exercicio) throws ECARException {
		if (pesquisarDuplos(exercicio, new String[] {"descricaoExe"}, "codExe").size() > 0)
		    throw new ECARException("exercicio.validacao.registroDuplicado");
		
	    List lExercicios = getExerciciosComSobreposicaoData(exercicio);
	    if (lExercicios != null && lExercicios.size() > 0) {
	        throw new ECARException("exercicio.validacao.erroSobreposicao");
	    } else {
	        super.salvar(exercicio);
	    }
	}
	
	/**
	 * Altera um exercicio somente se as datas n�o sobrepoem nenhum outro exercicio
	 * @author garten
	 * @param exercicio
	 * @throws ECARException
	 */
	public void alterar(ExercicioExe exercicio) throws ECARException {
		if (pesquisarDuplos(exercicio, new String[] {"descricaoExe"}, "codExe").size() > 0)
		    throw new ECARException("exercicio.validacao.registroDuplicado");
		
	    List lExercicios = getExerciciosComSobreposicaoData(exercicio);
	    if (lExercicios != null && lExercicios.size() > 0) {
	        throw new ECARException("exercicio.validacao.erroSobreposicao");
	    } else {
	        super.alterar(exercicio);
	    }
	}
	
	/**
	 * Retorna uma lista de exercicios anteriores a um exerc�cio
	 * @param exercicio
	 * @return
	 * @throws ECARException
	 */
	public List getExerciciosAnteriores(ExercicioExe exercicio) throws ECARException{
	    List resultado = new ArrayList();
	    
	    try {
            String select = "from ExercicioExe e where e.dataFinalExe < :dataFinal order by e.dataInicialExe";
            
            Query q = this.session.createQuery(select);
            
            q.setDate("dataFinal", exercicio.getDataInicialExe());
            
            resultado = q.list();
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
	    
	    return (resultado);		
	}
	
	/**
	 * Retorna uma lista de todos os exercicios para um determinado item.
	 *
	 * 1- Se tiver data inicial e final informadas no cadastro do item, ent�o retorna os exerc�cios com base nessas datas 
	 * e no cadastro de exerc�cios, mesmo que n�o tenham indicadores cadastrados para alguma dessas datas, o qual, 
	 * se acontecer, ser� apresentado com valor zero (0).
	 * 
	 * 2- Se s� tiver a data inicial informada no cadastro do item, ent�o retorna os exerc�cios que come�am a partir 
	 * desta data inicial com base no cadastro de exerc�cio at� o �ltimo exerc�cio informado nos indicadores de resultado daquele item.
	 * 
	 * 3- Se s� tiver a data final informada no cadastro do item, ent�o retorna os exerc�cios que come�am a partir do 
	 * primeiro exerc�cio com base no cadastro de exerc�cio e nos indicadores de resultado at� a data final informada.
	 * 
	 * 4- Se n�o tiver nenhuma das datas no cadastro do item (nem inicial, nem final) ent�o retorna os exerc�cios informados 
	 * nos indicadores de resultado daquele item.	 
	 *  
         * @param codIett
	 * @return List
	 * @throws ECARException
	 * @throws HibernateException 
	 */
	public List getExerciciosProjecao(Long codIett, AcompReferenciaItemAri ari, String tipoSelecao) throws ECARException {
	    AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(null);
    	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(null);
    	ItemEstruturaDao estruturaDao = new ItemEstruturaDao(null);
	    
	    List resultado = new ArrayList();
	    
    	try { 
    	    String queryExeIndicadores = "select distinct ieFisico.exercicioExe from ItemEstrutFisicoIettf ieFisico" +
    	    		" where ieFisico.itemEstrtIndResulIettr.itemEstruturaIett.codIett = :codIett" +
    	    		" and ieFisico.itemEstrtIndResulIettr.itemEstruturaIett.indAtivoIett = 'S'" +
    	    		" order by ieFisico.exercicioExe.dataFinalExe";

			Query q = this.getSession().createQuery(queryExeIndicadores);
			
			q.setLong("codIett", codIett.longValue());
			
	    	ItemEstruturaIett item = (ItemEstruturaIett) estruturaDao.buscar(ItemEstruturaIett.class, codIett);

	    	if(item != null){
		    	
				// Nova regra (Mantis 6282) - inicio
				long anoMesArfMaisRecente = 0;
				long anoMesArfMaisAntigo = 0;

		    	AcompRealFisicoArf arfMaisRecente = acompRealFisicoDao.getArfMaisRecenteItem(item);
		    	if(arfMaisRecente != null) {
		    		String mesAux = arfMaisRecente.getMesArf().toString();
		    		if(arfMaisRecente.getMesArf().longValue() < 10) {
		    			mesAux = "0" + mesAux;
		    		}
		    		anoMesArfMaisRecente = Long.parseLong(arfMaisRecente.getAnoArf().toString() + mesAux);
		    	}
		    	AcompRealFisicoArf arfMaisAntigo = acompRealFisicoDao.getArfMaisAntigoItem(item);
		    	if(arfMaisAntigo != null) {
		    		String mesAux = arfMaisAntigo.getMesArf().toString();
		    		if(arfMaisAntigo.getMesArf().longValue() < 10) {
		    			mesAux = "0" + mesAux;
		    		}
		    		anoMesArfMaisAntigo = Long.parseLong(arfMaisAntigo.getAnoArf().toString() + mesAux);
		    	}
				// Nova regra (Mantis 6282) - fim
		    	
		    	/* Se n�o tiver nenhuma das datas */
	    		if (item.getDataInicioIett() == null && item.getDataTerminoIett() == null){
	    			resultado = q.list();
	    		}
	    		else {
					int mes = 0;
					int ano = 0;
					int mesFinal = 0;
					int anoFinal = 0;

		    		/* Se tiver as 2 datas */
					if(item.getDataInicioIett() != null && item.getDataTerminoIett() != null){
						mes = Data.getMes(item.getDataInicioIett()) + 1;
						ano = Data.getAno(item.getDataInicioIett());
						mesFinal = Data.getMes(item.getDataTerminoIett()) + 1;
						anoFinal = Data.getAno(item.getDataTerminoIett());
		    		}
					/* se s� tiver a data inicial */
		    		else if (item.getDataInicioIett() != null && item.getDataTerminoIett() == null){
		    			
						mes = Data.getMes(item.getDataInicioIett()) + 1;
						ano = Data.getAno(item.getDataInicioIett());
						mesFinal = 0;
						anoFinal = 0;

						List exerciciosTemp = q.list();
	
						if(exerciciosTemp != null && exerciciosTemp.size() > 0){
							ExercicioExe exercicioFinal = (ExercicioExe) exerciciosTemp.get(exerciciosTemp.size()-1);
							mesFinal = Data.getMes(exercicioFinal.getDataFinalExe());
							anoFinal = Data.getAno(exercicioFinal.getDataFinalExe());
						}
		    		}
					/* se s� tiver a data final */
		    		else {
						mes = Data.getMes(item.getDataTerminoIett()) + 2; //Acrescento +1 ao mes e ao ano da data final para ter certeza de que n�o 
						ano = Data.getAno(item.getDataTerminoIett()) + 1; //entre no while se n�o achar nenhum exercicio na query abaixo
						
						mesFinal = Data.getMes(item.getDataTerminoIett()) + 1;
						anoFinal = Data.getAno(item.getDataTerminoIett());
						
						List exerciciosTemp = q.list();
	
						if(exerciciosTemp != null && exerciciosTemp.size() > 0){
							ExercicioExe exercicioInicial = (ExercicioExe) exerciciosTemp.get(0);
							mes = Data.getMes(exercicioInicial.getDataInicialExe());
							ano = Data.getAno(exercicioInicial.getDataInicialExe());
						}
		    		}

					
					// Nova regra (Mantis 6282) - inicio
		    		String mesAux = String.valueOf(mes);
		    		if(mes < 10) {
		    			mesAux = "0" + mesAux;
		    		}
					long anoMesItemInicio = Long.parseLong(String.valueOf(ano) + mesAux);

					mesAux = String.valueOf(mesFinal);
		    		if(mesFinal < 10) {
		    			mesAux = "0" + mesAux;
		    		}
					long anoMesItemFim = Long.parseLong(String.valueOf(anoFinal) + mesAux);
					
					if(anoMesArfMaisAntigo > 0 && anoMesArfMaisAntigo > anoMesItemInicio) {
						mes = arfMaisAntigo.getMesArf().intValue();
						ano = arfMaisAntigo.getAnoArf().intValue();
					}
					/*if(anoMesArfMaisRecente > 0 && anoMesArfMaisRecente > anoMesItemFim) {
						mesFinal = arfMaisRecente.getMesArf().intValue();
						anoFinal = arfMaisRecente.getAnoArf().intValue();
					}*/
					// Nova regra (Mantis 6282) - fim

					int anoExeIni = 0;
					double totalRealizado = 0;
					double totalPrevisto = 0;
					while(ano < anoFinal || (ano == anoFinal && mes <= mesFinal)){
						ExercicioExe exercicio = acompReferenciaDao.getExercicio(String.valueOf(ano), String.valueOf(mes));
						if(exercicio != null && !resultado.contains(exercicio)){
							anoExeIni = Data.getAno(exercicio.getDataInicialExe());
							if(anoExeIni <= anoFinal && anoExeIni >= ano) {
								resultado.add(exercicio);
								
								if (ari!=null) {
									
	 								List indResultados;
									indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari, tipoSelecao, false);
								
									Iterator itIndResult = indResultados.iterator();
	
									while(itIndResult.hasNext()) {
										AcompRealFisicoArf arf = (AcompRealFisicoArf) itIndResult.next();
										// verificar se valor realizado
										double realizadoNoExercicio = 0;
										double previstoNoExercicio = 0;						
										if ("S".equals(arf.getItemEstrtIndResulIettr().getIndAcumulavelIettr())) {
											realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicio(exercicio, arf.getItemEstrtIndResulIettr(),ari.getAcompReferenciaAref());
										} else {
											realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicioNaoAcumulavel(exercicio,arf.getItemEstrtIndResulIettr(),ari.getAcompReferenciaAref());
										}
										previstoNoExercicio = new ItemEstrtIndResulDao(request).getQtdPrevistoExercicio(arf.getItemEstrtIndResulIettr(), exercicio);
	
										if (arf.getItemEstrtIndResulIettr() != null) {
											if ("S".equals(arf.getItemEstrtIndResulIettr().getIndAcumulavelIettr())) {
												totalRealizado += realizadoNoExercicio;
												totalPrevisto += previstoNoExercicio;
											} else {
												totalRealizado = realizadoNoExercicio;
												totalPrevisto = previstoNoExercicio;
											}
										}
	
									}
									
	
									
									if ((totalPrevisto==0 && totalRealizado==0))
										resultado.remove(exercicio);
								}

							}
						}
						
						mes++;
						if(mes > 12){
							mes = 1;
							ano++;
						}
					}
	    		}
	    	}
	    	/* item == null, portanto n�o tenho nenhuma das datas */
	    	else{
		    	resultado = q.list();
	    	}
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        } catch(Exception e) {
        	this.logger.error(e);
        	throw new ECARException("erro.hibernateException");
        }
	    
	    return (resultado);		
	}

	/**
	 * Retorna uma lista de todos os exercicios para um determinado item.
	 *
	 * A regra de exibi��o dos exerc�cios na tela de resultados, ir� variar de acordo com a data de in�cio e data de t�rmino definidas na aba dados b�sicos;
	 * 1. Se a data de in�cio e a data de t�rmino foram informadas, o sistema traz todos os exerc�cios entre as datas;
	 * 2. Se a data de in�cio e a data de t�rmino n�o foram informadas, o sistema exibe todos os exerc�cios que possuam valores registrados em previsto ou realizado; 
	 * 3. Se a data de in�cio n�o for informada, mas a data de t�rmino sim, o sistema exibe todos os exerc�cios at� a data t�rmino que possuam valores registrados em previsto ou realizado. 
	 * 4. Se a data de in�cio for informada, mas a data de t�rmino n�o, o sistema exibe todos os exerc�cios a partir da data de in�cio que possuam valores registrados em previsto ou realizado.	 
	 *  
     * @param codIett
	 * @return List
	 * @throws ECARException
	 * @throws HibernateException 
	 */
	public List getExerciciosProjecaoResultados(Long codIett) throws ECARException {
    	ItemEstruturaDao estruturaDao = new ItemEstruturaDao(null);
    	List<ExercicioExe> resultado = new ArrayList<ExercicioExe>();
    	Set<ExercicioExe> exercicios = new TreeSet<ExercicioExe>();
    	try {
    		ItemEstruturaIett iett = (ItemEstruturaIett) estruturaDao.buscar(ItemEstruturaIett.class, codIett);
    		if (iett != null){
    			if (iett.getDataInicioIett() != null && iett.getDataTerminoIett() != null){
					//SE a data de inicio do item e a data de termino do item estiver preenchida,
					//recupera todos os exerc�cios entre estas datas, independentes se tem previsto ou realizado
					resultado.addAll(getExercicios(iett.getDataInicioIett(), iett.getDataTerminoIett())); 
    			} else {
    				exercicios.addAll(getExerciciosPrevistos(iett));
        			exercicios.addAll(getExerciciosRealizados(iett));
        			if (iett.getDataInicioIett() == null && iett.getDataTerminoIett() == null){
            			//Se o item n�o tem data inicio e n�o tem data de termino, exibe todos os exercicios que tenha 
            			//previsto ou realizado
        				resultado.addAll(exercicios);
        			} else if (iett.getDataInicioIett() != null && iett.getDataTerminoIett() == null){
            			//Se o item tem a data de inicio informado e n�o tem a data de termino,
            			//o sistema recupera todos os exercicios que tenham previsto ou realizado
            			//a partir da data de inicio
	    				for (ExercicioExe exercicioExe : exercicios) {
		    				if ( (Data.compareAnoMesDia(exercicioExe.getDataInicialExe(), iett.getDataInicioIett()) >= 0 || 
		   						 Data.compareAnoMesDia(exercicioExe.getDataFinalExe(), iett.getDataInicioIett()) >= 0)){
		    					resultado.add(exercicioExe);
							}
	    				}
	    			} else if (iett.getDataInicioIett() == null && iett.getDataTerminoIett() != null){
	    				//Se o item tem a data de termino informada e n�o tem a data de inicio,
            			//o sistema recupera todos os exercicios que tenham previsto ou realizado
            			//at� a data de termino
	    				for (ExercicioExe exercicioExe : exercicios) {
		    				if ( (Data.compareAnoMesDia(exercicioExe.getDataInicialExe(), iett.getDataTerminoIett()) <= 0 || 
		   						 Data.compareAnoMesDia(exercicioExe.getDataFinalExe(), iett.getDataTerminoIett()) <= 0) ){
		    					resultado.add(exercicioExe);
							}
	    				}
	    			}
    			}
    		}
      	} catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
	    ordenaListInvert(resultado, "descricaoExe");
	    return resultado;	

//	    AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(null);
//    	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(null);
//	    List resultado = new ArrayList();
//		int mes = 0;
//		int ano = 0;
//		int mesFinal = 0;
//		int anoFinal = 0;
//		boolean verifica = true;
//	    
//    	try { 
//    	    String queryExeIndicadores = "select distinct ieFisico.exercicioExe from ItemEstrutFisicoIettf ieFisico" +
//    	    		" where ieFisico.itemEstrtIndResulIettr.itemEstruturaIett.codIett = :codIett" +
//    	    		" and ieFisico.itemEstrtIndResulIettr.itemEstruturaIett.indAtivoIett = 'S'" +
//    	    		" order by ieFisico.exercicioExe.dataFinalExe";
//
//			Query q = this.getSession().createQuery(queryExeIndicadores);
//			
//			q.setLong("codIett", codIett.longValue());
//			
//	    	ItemEstruturaIett item = (ItemEstruturaIett) estruturaDao.buscar(ItemEstruturaIett.class, codIett);
//
//	    	if(item != null){
//		    	
//				// Nova regra (Mantis 6282) - inicio
//				long anoMesArfMaisRecente = 0;
//				long anoMesArfMaisAntigo = 0;
//
//		    	AcompRealFisicoArf arfMaisRecente = acompRealFisicoDao.getArfMaisRecenteItem(item);
//		    	if(arfMaisRecente != null) {
//		    		String mesAux = arfMaisRecente.getMesArf().toString();
//		    		if(arfMaisRecente.getMesArf().longValue() < 10) {
//		    			mesAux = "0" + mesAux;
//		    		}
//		    		anoMesArfMaisRecente = Long.parseLong(arfMaisRecente.getAnoArf().toString() + mesAux);
//		    	}
//		    	AcompRealFisicoArf arfMaisAntigo = acompRealFisicoDao.getArfMaisAntigoItem(item);
//		    	if(arfMaisAntigo != null) {
//		    		String mesAux = arfMaisAntigo.getMesArf().toString();
//		    		if(arfMaisAntigo.getMesArf().longValue() < 10) {
//		    			mesAux = "0" + mesAux;
//		    		}
//		    		anoMesArfMaisAntigo = Long.parseLong(arfMaisAntigo.getAnoArf().toString() + mesAux);
//		    	}
//				// Nova regra (Mantis 6282) - fim
//		    	
//		    	/* Se n�o tiver nenhuma das datas, exibe todos os exerc�cios que possuam valores 
//		    	 * registrados em previsto ou realizado */
//	    		if (item.getDataInicioIett() == null && item.getDataTerminoIett() == null){
//	    			resultado = q.list();
//	    			if (resultado.size() == 0){
//	    				return resultado;
//	    			}
//	    			ano = Integer.parseInt(arfMaisAntigo.getAnoArf().toString());
//	    			mes = Integer.parseInt(arfMaisAntigo.getMesArf().toString());
//	    		}
//	    		else {
//
//
//		    		/* Se tiver as 2 datas, pega todos os exerc�cios entre as datas */
//					if(item.getDataInicioIett() != null && item.getDataTerminoIett() != null){
//						mes = Data.getMes(item.getDataInicioIett()) + 1;
//						ano = Data.getAno(item.getDataInicioIett());
//						mesFinal = Data.getMes(item.getDataTerminoIett()) + 1;
//						anoFinal = Data.getAno(item.getDataTerminoIett());
//						verifica = false;
//		    		}
//					/* se s� tiver a data inicial, todos os exerc�cios a partir da data de in�cio 
//					 * que possuam valores registrados em previsto ou realizado */
//		    		else if (item.getDataInicioIett() != null && item.getDataTerminoIett() == null){
//		    			
//						mes = Data.getMes(item.getDataInicioIett()) + 1;
//						ano = Data.getAno(item.getDataInicioIett());
//						mesFinal = 0;
//						anoFinal = 0;
//						verifica = false;
//						List exerciciosTemp = q.list();
//	
//						if(exerciciosTemp != null && exerciciosTemp.size() > 0){
//							ExercicioExe exercicioFinal = (ExercicioExe) exerciciosTemp.get(exerciciosTemp.size()-1);
//							mesFinal = Data.getMes(exercicioFinal.getDataFinalExe());
//							anoFinal = Data.getAno(exercicioFinal.getDataFinalExe());
//						}
//		    		}
//					/* se s� tiver a data final, exibe todos os exerc�cios at� a data t�rmino 
//					 * que possuam valores registrados em previsto ou realizado */
//		    		else {
//						mes = Data.getMes(item.getDataTerminoIett()) + 2; //Acrescento +1 ao mes e ao ano da data final para ter certeza de que n�o 
//						ano = Data.getAno(item.getDataTerminoIett()) + 1; //entre no while se n�o achar nenhum exercicio na query abaixo
//						
//						mesFinal = Data.getMes(item.getDataTerminoIett()) + 1;
//						anoFinal = Data.getAno(item.getDataTerminoIett());
//						
//						List exerciciosTemp = q.list();
//	
//						if(exerciciosTemp != null && exerciciosTemp.size() > 0){
//							ExercicioExe exercicioInicial = (ExercicioExe) exerciciosTemp.get(0);
//							mes = Data.getMes(exercicioInicial.getDataInicialExe());
//							ano = Data.getAno(exercicioInicial.getDataInicialExe());
//							ExercicioExe exercicioFinal = (ExercicioExe) exerciciosTemp.get(exerciciosTemp.size()-1);
//							mesFinal = Data.getMes(exercicioFinal.getDataFinalExe());
//							anoFinal = Data.getAno(exercicioFinal.getDataFinalExe());
//						}
//						else{
//							return exerciciosTemp;
//						}
//		    		}
//	    		}
//					
//					// Nova regra (Mantis 6282) - inicio
//		    		String mesAux = String.valueOf(mes);
//		    		if(mes < 10) {
//		    			mesAux = "0" + mesAux;
//		    		}
//					long anoMesItemInicio = Long.parseLong(String.valueOf(ano) + mesAux);
//
//					mesAux = String.valueOf(mesFinal);
//		    		if(mesFinal < 10) {
//		    			mesAux = "0" + mesAux;
//		    		}
//					long anoMesItemFim = Long.parseLong(String.valueOf(anoFinal) + mesAux);
//					
//					if(anoMesArfMaisAntigo > 0 && anoMesArfMaisAntigo < anoMesItemInicio && verifica) {
//						mes = arfMaisAntigo.getMesArf().intValue();
//						ano = arfMaisAntigo.getAnoArf().intValue();
//					}
//					if(anoMesArfMaisRecente > 0 && anoMesArfMaisRecente > anoMesItemFim && verifica) {
//						mesFinal = arfMaisRecente.getMesArf().intValue();
//						anoFinal = arfMaisRecente.getAnoArf().intValue();
//					}
//					// Nova regra (Mantis 6282) - fim
//					int anoExeIni = 0;
//					while(ano < anoFinal || (ano == anoFinal && mes <= mesFinal)){
//						ExercicioExe exercicio = acompReferenciaDao.getExercicio(String.valueOf(ano), String.valueOf(mes));
//						if(exercicio != null && !resultado.contains(exercicio)){
//							anoExeIni = Data.getAno(exercicio.getDataInicialExe());
//							if(anoExeIni <= anoFinal && anoExeIni >= ano)
//							resultado.add(exercicio);
//						}
//						
//						mes++;
//						if(mes > 12){
//							mes = 1;
//							ano++;
//						}
//					}
//	    		}
//	    	//}
//	    	/* item == null, portanto n�o tenho nenhuma das datas */
//	    	else{
//		    	resultado = q.list();
//	    	}
//        } catch (HibernateException e) {
//            this.logger.error(e);
//            throw new ECARException("erro.hibernateException");
//        }
//	    ordenaListInvert(resultado, "descricaoExe");
//	    return (resultado);		
	}
	
	/**
	 * Retorna uma lista de exercicios posteriores a um exerc�cio
	 * @param exercicio
	 * @return
	 * @throws ECARException
	 */
	public List getExerciciosPosteriores(ExercicioExe exercicio) throws ECARException{
	    List resultado = new ArrayList();
	    
	    try {
            String select = "from ExercicioExe e where e.dataFinalExe > :dataInicial order by e.dataInicialExe";
            
            Query q = this.getSession().createQuery(select);
            
            q.setDate("dataInicial", exercicio.getDataInicialExe());
            
            resultado = q.list();
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
	    
	    return (resultado);		
	}
	
	
	/**
	 * Retorna uma lista com meses dentro de um exerc�cio no formato mes-ano (01-2004)
	 * @param exercicio
	 * @return
	 */
	public List getMesesDentroDoExercicio(ExercicioExe exercicio){
		
		List retorno = new ArrayList();
		Calendar dataInicialExe = Data.getCalendar(exercicio.getDataInicialExe());
		Calendar dataFinalExe = Data.getCalendar(exercicio.getDataFinalExe());
		
		int anoInicial = dataInicialExe.get(Calendar.YEAR);
		int anoFinal = dataFinalExe.get(Calendar.YEAR);
		int mesInicial = dataInicialExe.get(Calendar.MONTH) + 1;
		int mesFinal = dataFinalExe.get(Calendar.MONTH) + 1;
		
		while((anoInicial != anoFinal) || (mesInicial != mesFinal && anoInicial == anoFinal)){
			retorno.add(mesInicial + "-" + anoInicial);
			mesInicial++;
			if(mesInicial == 13){
				mesInicial = 1;
				anoInicial++;
			}			
		}
		retorno.add(mesInicial + "-" + anoInicial);
		
		return retorno;
	}
	
	/**
	 * Retorna uma lista com meses dentro de um exerc�cio no formato mes-ano (01-2004) at� um determinado m�s/ano
	 * @param exercicio
         * @param mes
         * @param ano
         * @return
	 */
	public List<String> getMesesDentroDoExercicio(ExercicioExe exercicio, int mes, int ano){
		
		List<String> retorno = new ArrayList<String>();
		Calendar dataInicialExe = Data.getCalendar(exercicio.getDataInicialExe());
		//Calendar dataFinalExe = Data.getCalendar(exercicio.getDataFinalExe());
		
		int anoInicial = dataInicialExe.get(Calendar.YEAR);
		int anoFinal = ano;
		int mesInicial = dataInicialExe.get(Calendar.MONTH) + 1;
		int mesFinal = mes;
		
		while((anoInicial != anoFinal) || (mesInicial != mesFinal && anoInicial == anoFinal)){
			retorno.add(String.valueOf(mesInicial) + "-" + String.valueOf(anoInicial));
			mesInicial++;
			if(mesInicial == 13){
				mesInicial = 1;
				anoInicial++;
			}			
		}
		retorno.add(mesInicial + "-" + anoInicial);
		
		return retorno;
	}
	
	/**
	 * Retorna uma lista de todos os exercicios v�lidos para um determinado item.
	 *
	 * 1- Se tiver data inicial e final informadas no cadastro do item, ent�o retorna os exerc�cios com base nessas datas 
	 * e no cadastro de exerc�cios, mesmo que n�o tenham indicadores cadastrados para alguma dessas datas, o qual, 
	 * se acontecer, ser� apresentado com valor zero (0).
	 * 
	 * 2- Se s� tiver a data inicial informada no cadastro do item, ent�o retorna os exerc�cios que come�am 
	 * a partir desta data inicial com base no cadastro de exerc�cio at� o �ltimo exerc�cio informado na tabela de exercicios (ExercicioExe).
	 * 
	 * 3- Se s� tiver a data final informada no cadastro do item, ent�o retorna os exerc�cios que come�am a partir do primeiro 
	 * exerc�cio com base no cadastro de exerc�cio (ExercicioExe) at� a data final do item informada.
	 * 
	 * 4- Se n�o tiver nenhuma das datas no cadastro do item (nem inicial, nem final) ent�o retorna os exerc�cios do cadastro 
	 * de exerc�cios (ExercicioExe).
	 *  
         * @param codIett
	 * @return List
	 * @throws ECARException
	 */
	public List getExerciciosValidos(Long codIett) throws ECARException{
    	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(null);
    	ItemEstruturaDao estruturaDao = new ItemEstruturaDao(null);
	    
	    List resultado = new ArrayList();

	    String queryExercicios = "from ExercicioExe exercicio" +
		" order by exercicio.dataFinalExe";

    	try { 


	    	ItemEstruturaIett item = (ItemEstruturaIett) estruturaDao.buscar(ItemEstruturaIett.class, codIett);

	    	if(item != null){
	    		if (item.getDataInicioIett() == null && item.getDataTerminoIett() == null){
		    		/* Se n�o tiver nenhuma das datas 
					 * 4- Se n�o tiver nenhuma das datas no cadastro do item (nem inicial, nem final) ent�o retorna os exerc�cios do cadastro 
					 * de exerc�cios (ExercicioExe).
			    	 */
	    			resultado = session.createQuery(queryExercicios).list();
	    		}
	    		else {
					int mes = 0;
					int ano = 0;
					int mesFinal = 0;
					int anoFinal = 0;

		    		/* Se tiver as 2 datas 
					 * 1- Se tiver data inicial e final informadas no cadastro do item, ent�o retorna os exerc�cios com base nessas datas 
					 * e no cadastro de exerc�cios, mesmo que n�o tenham indicadores cadastrados para alguma dessas datas, o qual, 
					 * se acontecer, ser� apresentado com valor zero (0).					
					 */
					if(item.getDataInicioIett() != null && item.getDataTerminoIett() != null){
		    			
						mes = Data.getMes(item.getDataInicioIett()) + 1;
						ano = Data.getAno(item.getDataInicioIett());
						mesFinal = Data.getMes(item.getDataTerminoIett()) + 1;
						anoFinal = Data.getAno(item.getDataTerminoIett());
						
		    		}
					/* se s� tiver a data inicial 
					 * 2- Se s� tiver a data inicial informada no cadastro do item, ent�o retorna os exerc�cios que come�am 
					 * a partir desta data inicial com base no cadastro de exerc�cio at� o �ltimo exerc�cio informado na 
					 * tabela de exercicios (ExercicioExe).
					 */
		    		else if (item.getDataInicioIett() != null && item.getDataTerminoIett() == null){
		    			
						mes = Data.getMes(item.getDataInicioIett()) + 1;
						ano = Data.getAno(item.getDataInicioIett());
						mesFinal = 0;
						anoFinal = 0;
						
						List exerciciosTemp = session.createQuery(queryExercicios).list();
	
						if(exerciciosTemp != null && exerciciosTemp.size() > 0){
							ExercicioExe exercicioFinal = (ExercicioExe) exerciciosTemp.get(exerciciosTemp.size()-1);
							mesFinal = Data.getMes(exercicioFinal.getDataFinalExe());
							anoFinal = Data.getAno(exercicioFinal.getDataFinalExe());
						}
						
		    		}
					/* se s� tiver a data final
					 * 3- Se s� tiver a data final informada no cadastro do item, ent�o retorna os exerc�cios que come�am a partir do primeiro 
					 * exerc�cio com base no cadastro de exerc�cio (ExercicioExe) at� a data final do item informada.
					 */
		    		else {
						mes = Data.getMes(item.getDataTerminoIett()) + 2; //Acrescento +1 ao mes e ao ano da data final para ter certeza de que n�o 
						ano = Data.getAno(item.getDataTerminoIett()) + 1; //entre no while se n�o achar nenhum exercicio na query abaixo
						
						mesFinal = Data.getMes(item.getDataTerminoIett()) + 1;
						anoFinal = Data.getAno(item.getDataTerminoIett());
						
						List exerciciosTemp = session.createQuery(queryExercicios).list();
	
						if(exerciciosTemp != null && exerciciosTemp.size() > 0){
							ExercicioExe exercicioInicial = (ExercicioExe) exerciciosTemp.get(0);
							mes = Data.getMes(exercicioInicial.getDataInicialExe());
							ano = Data.getAno(exercicioInicial.getDataInicialExe());
						}
		    		}

		    		while(ano < anoFinal || (ano == anoFinal && mes <= mesFinal)){
						ExercicioExe exercicio = acompReferenciaDao.getExercicio(String.valueOf(ano), String.valueOf(mes));
						if(exercicio != null && !resultado.contains(exercicio)){
							resultado.add(exercicio);
						}
						
						mes++;
						if(mes > 12){
							mes = 1;
							ano++;
						}
					}
	    		}
	    	}
	    	else{
		    	/* item == null, portanto n�o tenho nenhuma das datas
				 * 4- Se n�o tiver nenhuma das datas no cadastro do item (nem inicial, nem final) ent�o retorna os exerc�cios do cadastro 
				 * de exerc�cios (ExercicioExe).
		    	 */
		    	resultado = session.createQuery(queryExercicios).list();
	    	}
	    	
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
	    
	    return (resultado);		
	}
	
        /**
         *
         * @param exeReferencia
         * @return
         * @throws ECARException
         */
        public List getExeByPerExe(ExercicioExe exeReferencia) throws ECARException{
		
		List retorno = new ArrayList();
		
		try{
			StringBuilder select = new StringBuilder();
			select.append("select exe from ExercicioExe exe");
			
			if(exeReferencia != null && exeReferencia.getPeriodoExercicioPerExe() != null){
				select.append(" where exe.periodoExercicioPerExe.codPerExe = :codPerExe");
			}

			select.append(" order by exe.dataInicialExe desc");
			
			Query q = this.session.createQuery(select.toString());
			
			if(exeReferencia != null && exeReferencia.getPeriodoExercicioPerExe() != null){
				q.setLong("codPerExe", exeReferencia.getPeriodoExercicioPerExe().getCodPerExe().longValue());
			}
	
			retorno = q.list();
			
			if(retorno == null || (retorno != null && retorno.isEmpty()))
				retorno = new ArrayList();

		}
		catch (HibernateException he) {
			this.logger.error(he);
			throw new ECARException("erro.hibernateException");
		}
		
		return retorno;
	}
	
    /**
     * Carrega listagem de Exercicios de acordo com a periodicidade
     * @param codPerExe Identificador <PeriodoExercicioPerExe>
     * @return List<ExercicioExe>
     * @throws ECARException
     */
    public List getExercicioByPeriodicidade( Long codPerExe ) throws ECARException{
    	
    	try{
    		StringBuilder qry = new StringBuilder("from ExercicioExe as ex")
								.append(" where ex.periodoExercicioPerExe.codPerExe  = :codigo")
    							.append(" order by ex.dataFinalExe");
    		
    		Query q = this.session.createQuery(qry.toString());
    		q.setLong("codigo", codPerExe.longValue());
    		
    		return q.list();
    		
    	}
    	catch (HibernateException e){
    		this.logger.error(e);
    		throw new ECARException("erro.hibernateException");
    	}
    	    	
    }
    
    /**
	 * Retorna uma lista de todos os exercicios para um determinado item.
	 *
	 * 1- Se tiver data inicial e final informadas no cadastro do item, ent�o retorna os exerc�cios com base nessas datas 
	 * e no cadastro de exerc�cios, mesmo que n�o tenham indicadores cadastrados para alguma dessas datas, o qual, 
	 * se acontecer, ser� apresentado com valor zero (0).
	 * 
	 * 2- Se s� tiver a data inicial informada no cadastro do item, ent�o retorna os exerc�cios que come�am a partir 
	 * desta data inicial com base no cadastro de exerc�cio at� o �ltimo exerc�cio informado nos indicadores de resultado daquele item.
	 * 
	 * 3- Se s� tiver a data final informada no cadastro do item, ent�o retorna os exerc�cios que come�am a partir do 
	 * primeiro exerc�cio com base no cadastro de exerc�cio e nos indicadores de resultado at� a data final informada.
	 * 
	 * 4- Se n�o tiver nenhuma das datas no cadastro do item (nem inicial, nem final) ent�o retorna os exerc�cios informados 
	 * nos indicadores de resultado daquele item.	 
	 *  
     * @param codIett
     * @param codExe
     * @return List
	 * @throws ECARException
	 * @throws HibernateException 
	 */
	public List getExerciciosProjecaoByExercicio(Long codIett, Long codExe) throws ECARException {
	    AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(null);
    	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(null);
    	ItemEstruturaDao estruturaDao = new ItemEstruturaDao(null);
	    
	    List resultado = new ArrayList();
	    
    	try { 
    	    String queryExeIndicadores = "select distinct ieFisico.exercicioExe from ItemEstrutFisicoIettf ieFisico" +
    	    		" where ieFisico.itemEstrtIndResulIettr.itemEstruturaIett.codIett = :codIett" +
    	    		" and ieFisico.itemEstrtIndResulIettr.itemEstruturaIett.indAtivoIett = 'S'" +
    	    		" and ieFisico.exercicioExe.codExe = :codExe" +
    	    		" order by ieFisico.exercicioExe.dataFinalExe";

			Query q = this.getSession().createQuery(queryExeIndicadores);
			
			q.setLong("codIett", codIett.longValue());
			q.setLong("codExe",codExe);
			
	    	ItemEstruturaIett item = (ItemEstruturaIett) estruturaDao.buscar(ItemEstruturaIett.class, codIett);

	    	if(item != null){
		    	
				long anoMesArfMaisRecente = 0;
				long anoMesArfMaisAntigo = 0;

		    	AcompRealFisicoArf arfMaisRecente = acompRealFisicoDao.getArfMaisRecenteItem(item);
		    	if(arfMaisRecente != null) {
		    		String mesAux = arfMaisRecente.getMesArf().toString();
		    		if(arfMaisRecente.getMesArf().longValue() < 10) {
		    			mesAux = "0" + mesAux;
		    		}
		    		anoMesArfMaisRecente = Long.parseLong(arfMaisRecente.getAnoArf().toString() + mesAux);
		    	}
		    	AcompRealFisicoArf arfMaisAntigo = acompRealFisicoDao.getArfMaisAntigoItem(item);
		    	if(arfMaisAntigo != null) {
		    		String mesAux = arfMaisAntigo.getMesArf().toString();
		    		if(arfMaisAntigo.getMesArf().longValue() < 10) {
		    			mesAux = "0" + mesAux;
		    		}
		    		anoMesArfMaisAntigo = Long.parseLong(arfMaisAntigo.getAnoArf().toString() + mesAux);
		    	}
				
		    	/* Se n�o tiver nenhuma das datas */
	    		if (item.getDataInicioIett() == null && item.getDataTerminoIett() == null){
	    			resultado = q.list();
	    		}
	    		else {
					int mes = 0;
					int ano = 0;
					int mesFinal = 0;
					int anoFinal = 0;

		    		/* Se tiver as 2 datas */
					if(item.getDataInicioIett() != null && item.getDataTerminoIett() != null){
						mes = Data.getMes(item.getDataInicioIett()) + 1;
						ano = Data.getAno(item.getDataInicioIett());
						mesFinal = Data.getMes(item.getDataTerminoIett()) + 1;
						anoFinal = Data.getAno(item.getDataTerminoIett());
		    		}
					/* se s� tiver a data inicial */
		    		else if (item.getDataInicioIett() != null && item.getDataTerminoIett() == null){
		    			
						mes = Data.getMes(item.getDataInicioIett()) + 1;
						ano = Data.getAno(item.getDataInicioIett());
						mesFinal = 0;
						anoFinal = 0;

						List exerciciosTemp = q.list();
	
						if(exerciciosTemp != null && exerciciosTemp.size() > 0){
							ExercicioExe exercicioFinal = (ExercicioExe) exerciciosTemp.get(exerciciosTemp.size()-1);
							mesFinal = Data.getMes(exercicioFinal.getDataFinalExe());
							anoFinal = Data.getAno(exercicioFinal.getDataFinalExe());
						}
		    		}
					/* se s� tiver a data final */
		    		else {
						mes = Data.getMes(item.getDataTerminoIett()) + 2; //Acrescento +1 ao mes e ao ano da data final para ter certeza de que n�o 
						ano = Data.getAno(item.getDataTerminoIett()) + 1; //entre no while se n�o achar nenhum exercicio na query abaixo
						
						mesFinal = Data.getMes(item.getDataTerminoIett()) + 1;
						anoFinal = Data.getAno(item.getDataTerminoIett());
						
						List exerciciosTemp = q.list();
	
						if(exerciciosTemp != null && exerciciosTemp.size() > 0){
							ExercicioExe exercicioInicial = (ExercicioExe) exerciciosTemp.get(0);
							mes = Data.getMes(exercicioInicial.getDataInicialExe());
							ano = Data.getAno(exercicioInicial.getDataInicialExe());
						}
		    		}

					String mesAux = String.valueOf(mes);
		    		if(mes < 10) {
		    			mesAux = "0" + mesAux;
		    		}
					long anoMesItemInicio = Long.parseLong(String.valueOf(ano) + mesAux);

					mesAux = String.valueOf(mesFinal);
		    		if(mesFinal < 10) {
		    			mesAux = "0" + mesAux;
		    		}
					long anoMesItemFim = Long.parseLong(String.valueOf(anoFinal) + mesAux);
					
					if(anoMesArfMaisAntigo > 0 && anoMesArfMaisAntigo < anoMesItemInicio) {
						mes = arfMaisAntigo.getMesArf().intValue();
						ano = arfMaisAntigo.getAnoArf().intValue();
					}
					if(anoMesArfMaisRecente > 0 && anoMesArfMaisRecente > anoMesItemFim) {
						mesFinal = arfMaisRecente.getMesArf().intValue();
						anoFinal = arfMaisRecente.getAnoArf().intValue();
					}
				
					while(ano < anoFinal || (ano == anoFinal && mes <= mesFinal)){
						ExercicioExe exercicio = acompReferenciaDao.getExercicio(String.valueOf(ano), String.valueOf(mes));
						if(exercicio != null && !resultado.contains(exercicio)){
							resultado.add(exercicio);
						}
						
						mes++;
						if(mes > 12){
							mes = 1;
							ano++;
						}
					}
	    		}
	    	}
	    	/* item == null, portanto n�o tenho nenhuma das datas */
	    	else{
		    	resultado = q.list();
	    	}
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
	    
	    return (resultado);		
	}
	
	/*
	 * Hist�rico
	 */
	
//	/**
//	 * Retorna uma lista de todos os exercicios v�lidos para um determinado item.
//	 *
//	 * 1- Se tiver data inicial e final informadas no cadastro do item, ent�o retorna os exerc�cios com base nessas datas 
//	 * e no cadastro de exerc�cios, mesmo que n�o tenham indicadores cadastrados para alguma dessas datas, o qual, 
//	 * se acontecer, ser� apresentado com valor zero (0).
//	 * 
//	 * 2- Se s� tiver a data inicial informada no cadastro do item, ent�o retorna os exerc�cios que come�am 
//	 * a partir desta data inicial com base no cadastro de exerc�cio at� o �ltimo exerc�cio informado na tabela de exercicios (ExercicioExe).
//	 * 
//	 * 3- Se s� tiver a data final informada no cadastro do item, ent�o retorna os exerc�cios que come�am a partir do primeiro 
//	 * exerc�cio com base no cadastro de exerc�cio (ExercicioExe) at� a data final do item informada.
//	 * 
//	 * 4- Se n�o tiver nenhuma das datas no cadastro do item (nem inicial, nem final) ent�o retorna os exerc�cios do cadastro 
//	 * de exerc�cios (ExercicioExe).
//	 *  
//	 * @param Long codIett
//	 * @return List
//	 * @throws ECARException
//	 */
//	public List getExerciciosValidos(ItemEstruturaIett item) throws ECARException{
//    	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(null);
//    	ItemEstruturaDao estruturaDao = new ItemEstruturaDao(null);
//	    
//	    List resultado = new ArrayList();
//
//	    String queryExercicios = "from ExercicioExe exercicio" +
//		" order by exercicio.dataFinalExe";
//
//    	try { 
//
//
//	    	ItemEstruturaIett item = (ItemEstruturaIett) estruturaDao.buscar(ItemEstruturaIett.class, codIett);
//
//	    	if(item != null){
//	    		if (item.getDataInicioIett() == null && item.getDataTerminoIett() == null){
//		    		/* Se n�o tiver nenhuma das datas 
//					 * 4- Se n�o tiver nenhuma das datas no cadastro do item (nem inicial, nem final) ent�o retorna os exerc�cios do cadastro 
//					 * de exerc�cios (ExercicioExe).
//			    	 */
//	    			resultado = session.createQuery(queryExercicios).list();
//	    		}
//	    		else {
//					int mes = 0;
//					int ano = 0;
//					int mesFinal = 0;
//					int anoFinal = 0;
//
//		    		/* Se tiver as 2 datas 
//					 * 1- Se tiver data inicial e final informadas no cadastro do item, ent�o retorna os exerc�cios com base nessas datas 
//					 * e no cadastro de exerc�cios, mesmo que n�o tenham indicadores cadastrados para alguma dessas datas, o qual, 
//					 * se acontecer, ser� apresentado com valor zero (0).					
//					 */
//					if(item.getDataInicioIett() != null && item.getDataTerminoIett() != null){
//		    			
//						mes = Data.getMes(item.getDataInicioIett()) + 1;
//						ano = Data.getAno(item.getDataInicioIett());
//						mesFinal = Data.getMes(item.getDataTerminoIett()) + 1;
//						anoFinal = Data.getAno(item.getDataTerminoIett());
//						
//		    		}
//					/* se s� tiver a data inicial 
//					 * 2- Se s� tiver a data inicial informada no cadastro do item, ent�o retorna os exerc�cios que come�am 
//					 * a partir desta data inicial com base no cadastro de exerc�cio at� o �ltimo exerc�cio informado na 
//					 * tabela de exercicios (ExercicioExe).
//					 */
//		    		else if (item.getDataInicioIett() != null && item.getDataTerminoIett() == null){
//		    			
//						mes = Data.getMes(item.getDataInicioIett()) + 1;
//						ano = Data.getAno(item.getDataInicioIett());
//						mesFinal = 0;
//						anoFinal = 0;
//						
//						List exerciciosTemp = session.createQuery(queryExercicios).list();
//	
//						if(exerciciosTemp != null && exerciciosTemp.size() > 0){
//							ExercicioExe exercicioFinal = (ExercicioExe) exerciciosTemp.get(exerciciosTemp.size()-1);
//							mesFinal = Data.getMes(exercicioFinal.getDataFinalExe());
//							anoFinal = Data.getAno(exercicioFinal.getDataFinalExe());
//						}
//						
//		    		}
//					/* se s� tiver a data final
//					 * 3- Se s� tiver a data final informada no cadastro do item, ent�o retorna os exerc�cios que come�am a partir do primeiro 
//					 * exerc�cio com base no cadastro de exerc�cio (ExercicioExe) at� a data final do item informada.
//					 */
//		    		else {
//						mes = Data.getMes(item.getDataTerminoIett()) + 2; //Acrescento +1 ao mes e ao ano da data final para ter certeza de que n�o 
//						ano = Data.getAno(item.getDataTerminoIett()) + 1; //entre no while se n�o achar nenhum exercicio na query abaixo
//						
//						mesFinal = Data.getMes(item.getDataTerminoIett()) + 1;
//						anoFinal = Data.getAno(item.getDataTerminoIett());
//						
//						List exerciciosTemp = session.createQuery(queryExercicios).list();
//	
//						if(exerciciosTemp != null && exerciciosTemp.size() > 0){
//							ExercicioExe exercicioInicial = (ExercicioExe) exerciciosTemp.get(0);
//							mes = Data.getMes(exercicioInicial.getDataInicialExe());
//							ano = Data.getAno(exercicioInicial.getDataInicialExe());
//						}
//		    		}
//
//		    		while(ano < anoFinal || (ano == anoFinal && mes <= mesFinal)){
//						ExercicioExe exercicio = acompReferenciaDao.getExercicio(String.valueOf(ano), String.valueOf(mes));
//						if(exercicio != null && !resultado.contains(exercicio)){
//							resultado.add(exercicio);
//						}
//						
//						mes++;
//						if(mes > 12){
//							mes = 1;
//							ano++;
//						}
//					}
//	    		}
//	    	}
//	    	else{
//		    	/* item == null, portanto n�o tenho nenhuma das datas
//				 * 4- Se n�o tiver nenhuma das datas no cadastro do item (nem inicial, nem final) ent�o retorna os exerc�cios do cadastro 
//				 * de exerc�cios (ExercicioExe).
//		    	 */
//		    	resultado = session.createQuery(queryExercicios).list();
//	    	}
//	    	
//        } catch (HibernateException e) {
//            this.logger.error(e);
//            throw new ECARException("erro.hibernateException");
//        }
//	    
//	    return (resultado);		
//	}
//	
//	/*
//	 * Fim do Hist�rico
//	 */

	/**
	 * Retorna os exerc�cio ordenado pela data final
	 */
	public List<ExercicioExe> getExercicios() throws ECARException{
	    List<ExercicioExe> resultado = new ArrayList();
	    String queryExercicios = "from ExercicioExe exercicio" +
		" order by exercicio.dataFinalExe";

    	try { 
   			resultado = session.createQuery(queryExercicios).list();	    	
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
	    
	    return (resultado);		
	}
	
	
	/**
	 * Retorna todos os exerc�cio que tenha algum valor previsto informado para o item
	 * @param iett
	 * @return
	 * @throws ECARException
	 */
	public List<ExercicioExe> getExerciciosPrevistos(ItemEstruturaIett iett) throws ECARException{
		List<ExercicioExe> exerciciosPrevistos = new ArrayList<ExercicioExe>();
		try{
			StringBuilder query = new StringBuilder("select ieFisico from ItemEstrutFisicoIettf ieFisico")
			.append(" where ieFisico.itemEstrtIndResulIettr.itemEstruturaIett.codIett = :codIett")
			.append(" and ieFisico.qtdPrevistaIettf is not null")
			.append(" and ieFisico.itemEstrtIndResulIettr.itemEstruturaIett.indAtivoIett = 'S'")
			.append(" order by ieFisico.anoIettf desc, ieFisico.mesIettf desc");

			Query q = this.getSession().createQuery(query.toString());
			q.setLong("codIett", iett.getCodIett().longValue());
			List<ItemEstrutFisicoIettf> iettfs = q.list();
			
			for (ItemEstrutFisicoIettf iefft : iettfs) {
				ExercicioExe exercicioExe = getExercicio(iefft.getMesIettf().toString(), iefft.getAnoIettf().toString());
				if (exercicioExe != null && !exerciciosPrevistos.contains(exercicioExe)){
					exerciciosPrevistos.add(exercicioExe);
				}
			}
			return exerciciosPrevistos;
		} catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}

/*		
		List<ExercicioExe> exerciciosPrevistos = new ArrayList<ExercicioExe>();
		try{
			StringBuilder query = new StringBuilder("select distinct exercicioExe from ExercicioExe exercicio ")
									.append("where (")
									.append(" select count(*) from ItemEstrutFisicoIettf ieFisico ")
									.append(" where ieFisico.itemEstrtIndResulIettr.itemEstruturaIett.codIett = :codIett ")
									.append(" and ieFisico.itemEstrtIndResulIettr.itemEstruturaIett.indAtivoIett = 'S' ")
									.append("and ieFisico.anoIettf ")
									
									.append(") > 0 ")
									
									
									
									.append("order by exercicioExe.dataFinalExe");
				
			Query q = this.getSession().createQuery(query.toString());
			q.setLong("codIett", iett.getCodIett().longValue());
			exerciciosPrevistos.addAll(q.list());
			return exerciciosPrevistos;
		} catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}
*/		
	}
	
	/**
	 * Retorna todos os exerc�cio que tenha algum valor realizado informado para o item
	 * @param iett
	 * @return
	 * @throws ECARException
	 */
	public List<ExercicioExe> getExerciciosRealizados(ItemEstruturaIett iett) throws ECARException{
		List<ExercicioExe> exerciciosRealizados = new ArrayList<ExercicioExe>();
		try{
			StringBuilder query = new StringBuilder("select ARF from AcompRealFisicoArf as ARF")
			.append(" where ARF.itemEstruturaIett.codIett = :codIett")
			.append(" and ARF.qtdRealizadaArf is not null")
			.append(" and ARF.itemEstruturaIett.indAtivoIett = 'S'")
			.append(" order by ARF.anoArf desc, ARF.mesArf desc");

			Query q = this.getSession().createQuery(query.toString());
			q.setLong("codIett", iett.getCodIett().longValue());
			List<AcompRealFisicoArf> arfs = q.list();
			
			for (AcompRealFisicoArf acompRealFisicoArf : arfs) {
				ExercicioExe exercicioExe = getExercicio(acompRealFisicoArf.getMesArf().toString(), acompRealFisicoArf.getAnoArf().toString());
				if (exercicioExe != null && !exerciciosRealizados.contains(exercicioExe)){
					exerciciosRealizados.add(exercicioExe);
				}
			}
			return exerciciosRealizados;
		} catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}
	}
	
	
	/**
     * Retorna o Exerc�cio de mes e ano 
     * passados como parametro.
     * 
     * @param ano
     * @param mes
     * @return
     * @throws ECARException
     */
    public ExercicioExe getExercicio(String mes, String ano) throws ECARException{
    	ExercicioExe exercicio = new ExercicioExe();
    	
    	/* Padr�o iniciar o Exerc�cio � o dia 01 */
    	/* Padr�o de data no HQL, � como no Banco de Dados */
    	StringBuilder select = new StringBuilder("select distinct exercicio from ExercicioExe as exercicio")
    							.append(" where exercicio.dataInicialExe <= :data")
    							.append(" and exercicio.dataFinalExe >= :data");
    	
		Date data = Data.parseDate("01/" + mes + "/" + ano);
		try {
			Query q = this.getSession().createQuery(select.toString());
			q.setDate("data", data);
			q.setMaxResults(1);
			return (ExercicioExe) q.uniqueResult();
		} catch (HibernateException e){
			this.logger.error(e);
			throw new ECARException (e);
		}
    }
    
    /**
     * Retorna todos os exercicios entre as datas passadas como par�metro
     * @param dataInicio
     * @param dataFim
     * @return
     */
    public List<ExercicioExe> getExercicios(Date dataInicial, Date dataFinal) throws ECARException{
    	try {
            String select = "select e from ExercicioExe e where e.dataFinalExe >= :dataInicial and e.dataInicialExe <= :dataFinal order by e.dataInicialExe";
            Query q = this.getSession().createQuery(select);     
            q.setDate("dataInicial", dataInicial);
            q.setDate("dataFinal", dataFinal);
            return q.list();
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }	    
    }
    
    /**
     * Retorna todos os exercicios 
     * @param dataInicio
     * @param dataFim
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<ExercicioExe> listarExercios() {
		String sql = "select new ecar.pojo.ExercicioExe(" +
				"codExe, dataFinalExe, dataInicialExe, descricaoExe) " +
				"from ExercicioExe " +
				"order by descricaoExe";
		

		Query query = this.getSession().createQuery(sql.toString());
		
		List<ExercicioExe> lista = new ArrayList<ExercicioExe>();

		for(ExercicioExe exercicio: (List<ExercicioExe>)query.list()){
			if(exercicio.getDataInicialExe().before(new Date())&&exercicio.getDataFinalExe().after(new Date())){
				exercicio.setCicloCorrenteExe(true);
			}
			else{
				exercicio.setCicloCorrenteExe(false);
			}
			if(!exercicio.getDataInicialExe().after(new Date())&&!exercicio.getDescricaoExe().equals("2010")&&!exercicio.getDescricaoExe().equals("2011"))
				lista.add(exercicio);
		}
		
		return lista;
	}	

    /**
     * Retorna a data inicial do primeiro exerc�cio.
     * @return
     * @throws ECARException
     */
    public Date getDataInicialExePrimeiroExercicio() throws ECARException{
    	try {
            String select = "select min(e.dataInicialExe) from ExercicioExe e";
            Query q = this.getSession().createQuery(select);
            q.setMaxResults(1);
			return (Date) q.uniqueResult();
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }	    
    }

    /**
     * Retorna a data final do �ltimo exerc�cio.
     * @return
     * @throws ECARException
     */
    public Date getDataFinalExeUltimoExercicio() throws ECARException{
    	try {
            String select = "select max(e.dataFinalExe) from ExercicioExe e";
            Query q = this.getSession().createQuery(select);
            q.setMaxResults(1);
			return (Date) q.uniqueResult();
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }	    
    }
    
    public Long buscaCodigoExercicioCorrente(){
    	String sql = "SELECT cod_exe from tb_exercicio_exe WHERE NOW() BETWEEN data_inicial_exe AND data_final_exe";
    	SQLQuery q = this.getSession().createSQLQuery(sql.toString());
    	return Long.parseLong(q.uniqueResult().toString());
    }
}