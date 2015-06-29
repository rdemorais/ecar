/*
 * Created on 17/02/2005
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;

import ecar.bean.IettArfBean;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SituacaoSit;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.util.Dominios;

/**
 * @author evandro
 *
 */
public class AcompRealFisicoDao extends Dao {
    /**
     * 
     * @param request
     */
    public AcompRealFisicoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
    
    /**
     * Retorna um objeto AcompRealFisicoArf a partir do seu c�digo
     * 
     * @param codArf Long
     * 
     * @return AcompRealFisicoArf
     * @throws ECARException
     */
    public AcompRealFisicoArf buscar(Long codArf) throws ECARException {
        return (AcompRealFisicoArf) super.buscar(AcompRealFisicoArf.class, codArf);
    }
    
    
    /**
     * Retorna um objeto AcompRealFisicoArf a partir do IETT, IETTIR, M�s e Ano
     * 
     * @param mesArf
     * @param anoArf
     * @param codIettir Long
     * 
     * @return AcompRealFisicoArf
     * @throws ECARException
     */
    public AcompRealFisicoArf buscarPorIettir(Long mesArf, Long anoArf, Long codIettir) throws ECARException {
    	try {
			StringBuilder query = new StringBuilder("select ARF from AcompRealFisicoArf as ARF")
									.append(" where ARF.itemEstrtIndResulIettr.codIettir = :codIettir ")
									.append(" and ARF.mesArf = :mesArf ")
									.append(" and ARF.anoArf = :anoArf");
			
			Query q = this.getSession().createQuery(query.toString());
			
			q.setLong("codIettir", codIettir.longValue());
			q.setLong("mesArf", mesArf.longValue());
			q.setLong("anoArf", anoArf.longValue());
			q.setMaxResults(1);
			
			Object arf = q.uniqueResult();
			
			if(arf != null) {
				return (AcompRealFisicoArf)arf;
			} else {
				return null;
			}
    	}
    	catch(Exception e) {
    		this.logger.error(e);
			throw new ECARException(e);
		}
    }
    
    
    /**
     * Retorna um objeto AcompRealFisicoArf a partir do IETT, M�s e Ano
     * 
     * @param codIett Long
     * @param mesArf
     * @param anoArf
     * @return AcompRealFisicoArf
     * @throws ECARException
     */
    public List buscarPorIett(Long codIett, Long mesArf, Long anoArf) throws ECARException {
    	try {
			StringBuilder query = new StringBuilder("select ARF from AcompRealFisicoArf as ARF")
								.append(" where ARF.itemEstruturaIett.codIett = :codIett")
								.append(" and ARF.itemEstruturaIett.indAtivoIett = :ativo")
								.append(" and ARF.mesArf = :mesArf")
								.append(" and ARF.anoArf = :anoArf");
			
			Query q = this.getSession().createQuery(query.toString());
			
			q.setLong("codIett", codIett.longValue());
			q.setLong("mesArf", mesArf.longValue());
			q.setLong("anoArf", anoArf.longValue());
			q.setString("ativo", "S");
			
			return q.list();
    	}
    	catch(Exception e) {
    		this.logger.error(e);
			throw new ECARException(e);
		}
    }
    
    /**
     * Retorna um objeto AcompRealFisicoArf a partir do IETT, Dia, M�s e Ano
     * 
     * @param codIett Long
     * @param diaArf
     * @param mesArf
     * @param anoArf
     * @return AcompRealFisicoArf
     * @throws ECARException
     */
    public List buscarPorIett(Long codIett, Long diaArf, Long mesArf, Long anoArf) throws ECARException {
    	try {
			StringBuilder query = new StringBuilder("select ARF from AcompRealFisicoArf as ARF")
								.append(" where ARF.itemEstruturaIett.codIett = :codIett")
								.append(" and ARF.itemEstruturaIett.indAtivoIett = :ativo")
								.append(" and ARF.diaArf = :diaArf")
								.append(" and ARF.mesArf = :mesArf")
								.append(" and ARF.anoArf = :anoArf");
			
			Query q = this.getSession().createQuery(query.toString());
			
			q.setLong("codIett", codIett.longValue());
			q.setLong("diaArf", diaArf.longValue());
			q.setLong("mesArf", mesArf.longValue());
			q.setLong("anoArf", anoArf.longValue());
			q.setString("ativo", "S");
			
			return q.list();
    	}
    	catch(Exception e) {
    		this.logger.error(e);
			throw new ECARException(e);
		}
    }
    
    /**
     * Devolve a quantidade realizada total em AcompRealFisico para um indResultado
     * 
     * @param acompReferenciaItem
     * @param itemEstrtIndResul
     * @param radAcumulados
     * @return
     * @throws ECARException
     * @throws HibernateException
     */
    
    public double getQuantidadeRealizada(AcompReferenciaItemAri acompReferenciaItem,
    					ItemEstrtIndResulIettr itemEstrtIndResul, String radAcumulados) throws ECARException, HibernateException {
        StringBuilder query = new StringBuilder("select sum(acompRealFisico.qtdRealizadaArf) from AcompRealFisicoArf as acompRealFisico");
		query.append(" where acompRealFisico.itemEstrtIndResulIettr.codIettir = :codIettir");
		
		if("P".equalsIgnoreCase(radAcumulados)){
			query.append(" and (acompRealFisico.anoArf < :anoArf")
				 .append(" or (acompRealFisico.mesArf <= :mesArf")
				 .append(" and acompRealFisico.anoArf = :anoArf))");
		}

		Query q = this.getSession().createQuery(query.toString());
		
		q.setLong("codIettir", itemEstrtIndResul.getCodIettir().longValue());

		if("P".equalsIgnoreCase(radAcumulados)){
			q.setLong("anoArf", Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getAnoAref()).longValue());
			q.setLong("mesArf", Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getMesAref()).longValue());
		}
		q.setMaxResults(1);
		
		Double total = (Double) q.uniqueResult();
		if(total != null)
			return total.doubleValue();
		else
			return 0;
    }
    
    
    /**
     * Devolve a quantidade realizada, para um indResultado n�o acumul�vel
     *    A partir do Aref passado pelo Ari, � selecionado o �ltimo valor inclu�do
     * 	  no mesmo Exerc�cio anterior ou igual ao M�s e Ano do Aref. 
     * 
     * @param acompReferenciaItem
     * @param itemEstrtIndResul
     * @param radAcumulados
     * @return
     * @throws ECARException
     */
    
    // M�TODO OBSOLETO E N�O FUNCIONA CORRETAMENTE - CRISTIANO 02/02/2007
//    public double getQuantidadeRealizadaNaoAcumulados(AcompReferenciaItemAri acompReferenciaItem,
//    					ItemEstrtIndResulIettr itemEstrtIndResul, String radAcumulados) throws ECARException{
//        double realizado = 0;
//        
//        List meses = new ExercicioDao(request)
//					.getMesesDentroDoExercicio(acompReferenciaItem.getAcompReferenciaAref().getExercicioExe(),
//							Integer.valueOf ( acompReferenciaItem.getAcompReferenciaAref().getMesAref() ).intValue(),
//							Integer.valueOf ( acompReferenciaItem.getAcompReferenciaAref().getAnoAref() ).intValue());
//        
//        Collections.reverse(meses);
//        
//        Iterator it = meses.iterator();
//        while(it.hasNext()){
//        	String mesAno = (String) it.next();
//        	
//        	String mes = mesAno.split("-")[0];
//        	String ano = mesAno.split("-")[1];
//        	
//        	if(Integer.valueOf(mes).intValue() < 10)
//        		mes = "0" + mes;
//
//        	System.out.println("mesAno: " + mesAno);
//        	
//        	AcompReferenciaAref acompReferencia = new AcompReferenciaAref();
//        	
//        	acompReferencia.setExercicioExe(acompReferenciaItem.getAcompReferenciaAref().getExercicioExe());
//        	acompReferencia.setMesAref(mes);
//        	acompReferencia.setAnoAref(ano);
//        	
//        	acompReferencia.setTipoAcompanhamentoTa(acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa());
//        	
//        	//FIXME: �rg�o � necess�rio?
//        	//acompReferencia.setOrgaoOrg(acompReferenciaItem.getAcompReferenciaAref().getOrgaoOrg());
//        	
//        	/* Verifica se tem o Aref para o mes e ano */
//        	List listaAcompRef = this.pesquisar(acompReferencia, null);
//        	
//        	System.out.println("listaAcompRef" + listaAcompRef.size());
//        	
//        	Iterator itAcompRef = listaAcompRef.iterator();
//        	
//        	if(itAcompRef.hasNext()){
//        		AcompReferenciaAref acompRefAnt = (AcompReferenciaAref) itAcompRef.next();
//        		
//        		AcompReferenciaItemAri acompRefItemPes = new AcompReferenciaItemAri();
//        		
//        		acompRefItemPes.setAcompReferenciaAref(acompRefAnt);
//        		acompRefItemPes.setItemEstruturaIett(acompReferenciaItem.getItemEstruturaIett());
//        		
//        		/* pesquisar, verificar se possui um ARI */
//        		List listaAcompRefItem = this.pesquisar(acompRefItemPes, null);
//            	//Iterator itAcompRefItem = listaAcompRefItem.iterator();
//            	
//            	//if(itAcompRefItem.hasNext()){
//            		//AcompReferenciaItemAri acompRefItemAnt = (AcompReferenciaItemAri) itAcompRefItem.next();
//        		if(listaAcompRefItem != null && !listaAcompRefItem.isEmpty()) {
//            		
//            		AcompRealFisicoArf acompRealFisicoPes = new AcompRealFisicoArf();
//            		
//            		//acompRealFisicoPes.setAcompReferenciaItemAri(acompRefItemAnt);
//            		acompRealFisicoPes.setItemEstrtIndResulIettr(itemEstrtIndResul);
//            		//acompRealFisicoPes.setItemEstruturaIett(acompReferenciaItem.getItemEstruturaIett());
//            		
//            		/* Verificar se tem o indicador com a quantidade preenchida */
//            		List listaAcompRealFis = this.pesquisar(acompRealFisicoPes, null);
//                	Iterator itAcompRealFis = listaAcompRealFis.iterator();
//                	
//                	if(itAcompRealFis.hasNext()){
//                		AcompRealFisicoArf acompRealFisicoAnt = (AcompRealFisicoArf) itAcompRealFis.next();
//                		
//                		if(acompRealFisicoAnt.getQtdRealizadaArf() != null && 
//                				acompRealFisicoAnt.getQtdRealizadaArf().doubleValue() > 0){
//                			return acompRealFisicoAnt.getQtdRealizadaArf().doubleValue();
//                		}
//                	}
//            	}
//        	}
//        }
//        
//        return realizado;
//    }
    
    /**
     * Calcula a quantidade realizada por exerc�cio ou total de um AcompReferenciaItemAri,
     * para um indicador de resultado (Iettr) n�o acumul�vel, considerando se � a quantidade Maior, �ltima ou N�o se aplica
     * 
     * @author cristianoprecoma
     * 
     * @param ari
     * @param iettr
     * @param acumulados
     * @param porExercicioOuTotal
     * @return double
     * @throws ECARException
     */
    public double getQuantidadeRealizadaNaoAcumulados(
    		AcompReferenciaItemAri ari,
    		ItemEstrtIndResulIettr iettr, 
    		String acumulados,
    		String porExercicioOuTotal) throws ECARException {
		try {
	        
	    	if("N".equals(iettr.getIndValorFinalIettr())) { //N�o se aplica
	    		return 0;
	    	}
	    	
	        // Realizados f�sicos do indicador
	        Set setRealizadosFisicos = iettr.getAcompRealFisicoArfs();
	        
	        if(setRealizadosFisicos != null) {
	            Iterator it = setRealizadosFisicos.iterator();
	            
	            // In�cio das verifica��es das quantidades
	            
	            // Realizado TOTAL
	            if("T".equals(porExercicioOuTotal)) {
	            	
	            	// Se o acumulados for at� o per�odo mudar conjunto de ARFs para verifica��o
		            if("P".equals(acumulados)) {
		                // Ano + M�s do acompanhamento (OBS.: no AREF o ano e m�s s�o String e o m�s j� est� com zero para os meses inferiores a 10)
		                long anoMesAcompanhamento = Long.parseLong(ari.getAcompReferenciaAref().getAnoAref() + ari.getAcompReferenciaAref().getMesAref()); 
		                // Obter os ARFs AT� O PER�ODO do indicador
		                Set setRealizadosFisicosTotalAtePeriodo = new HashSet();
		                it = setRealizadosFisicos.iterator();
		                while (it.hasNext()) {
		                	AcompRealFisicoArf arf = (AcompRealFisicoArf) it.next();
		                	String anoMes = String.valueOf(arf.getAnoArf());
		                	
		                	if(arf.getMesArf() < 10) {
		                		anoMes += "0";
		                	}
		            		anoMes += String.valueOf(arf.getMesArf());
	
		            		if(Long.parseLong(anoMes) <= anoMesAcompanhamento) {
		            			setRealizadosFisicosTotalAtePeriodo.add(arf);
		            		}
		                }
		            	it = setRealizadosFisicosTotalAtePeriodo.iterator();
		            }
				// Realizado EXERC�CIO
	            } else if("E".equals(porExercicioOuTotal)) {
	            	// obter os meses/anos do exerc�cio do acompanhamento
	            	List mesesAnos = null;
	            	
	            	// Se o acumulados for at� o per�odo considerar os meses/anos at� o m�s/ano do acompanhamento
		            if("P".equals(acumulados)) {
		            	mesesAnos = new ExercicioDao(request).getMesesDentroDoExercicio(
		            			ari.getAcompReferenciaAref().getExercicioExe(),
		            			Integer.valueOf(ari.getAcompReferenciaAref().getMesAref()).intValue(),
		            			Integer.valueOf(ari.getAcompReferenciaAref().getAnoAref()).intValue());
		            } else {
		            	mesesAnos = new ExercicioDao(request).getMesesDentroDoExercicio(ari.getAcompReferenciaAref().getExercicioExe());
		            }
		            
		            if(mesesAnos != null && !mesesAnos.isEmpty()) {
		            	List anoMesExercicio = new ArrayList();
		            	// alterar a lista para ano + m�s
		    			Iterator itMeses = mesesAnos.iterator();
		    			while(itMeses.hasNext()) {
		    				String mesAno = (String) itMeses.next();
		    				
		    				String mes = mesAno.split("-")[0];
		    				String ano = mesAno.split("-")[1];
		    				
		    				if(Integer.valueOf(mes).intValue() < 10) {
		    					mes = "0" + mes;
		    				}
		    				anoMesExercicio.add(ano + mes);
		    			}
		    			// ordenar a lista por ano + m�s
		    		   	Collections.sort(anoMesExercicio,
		    		            new Comparator() {
		    		        		public int compare(Object o1, Object o2) {		        		    	        		 
		    		        			Integer num1 = Integer.valueOf(o1.toString());
		    		        			Integer num2 = Integer.valueOf(o2.toString());
		    		        			
		    		        			return num1.compareTo(num2);	
		    						}
		    		   			}
		    			);
		    		   	
		    		   	long periodoInicial = Long.parseLong(anoMesExercicio.get(0).toString());
		    		   	long periodoFinal = Long.parseLong(anoMesExercicio.get((anoMesExercicio.size() - 1)).toString());
	
		    		   	// Considerar os ARFs que dentro do per�odo inicial/final
		                Set setRealizadosFisicosPorExercicio = new HashSet();
		                it = setRealizadosFisicos.iterator();
		                while (it.hasNext()) {
		                	AcompRealFisicoArf arf = (AcompRealFisicoArf) it.next();
		                	String anoMes = String.valueOf(arf.getAnoArf());
		                	
		                	if(arf.getMesArf() < 10) {
		                		anoMes += "0";
		                	}
		            		anoMes += String.valueOf(arf.getMesArf());
	
		            		if(Long.parseLong(anoMes) >= periodoInicial && Long.parseLong(anoMes) <= periodoFinal) {
		            			setRealizadosFisicosPorExercicio.add(arf);
		            		}
		                }
		            	it = setRealizadosFisicosPorExercicio.iterator();
		            } else {
		            	throw new ECARException("N�o existe exerc�cio cadastrado para o per�odo do acompanhamento");
		            }
	            	
	            }
	            
	            // verifificar a quantidade no conjunto de ARFs selecionado anteriormente
				if("M".equals(iettr.getIndValorFinalIettr())) { //Maior
		            double maior = 0;
		            while (it.hasNext()) {
		            	AcompRealFisicoArf arf = (AcompRealFisicoArf) it.next();
		            		if(arf.getQtdRealizadaArf() != null && arf.getQtdRealizadaArf().doubleValue() > maior){
	            				maior = arf.getQtdRealizadaArf().doubleValue();
		            		}
		            }
		            return maior;
	        	} else if("U".equals(iettr.getIndValorFinalIettr())) { //Ultimo
	        		long maiorAnoMes = 0;
	        		double ultimo = 0;
	        		
		            while (it.hasNext()) {
		            	AcompRealFisicoArf arf = (AcompRealFisicoArf) it.next();
		            	String anoMes = String.valueOf(arf.getAnoArf());
		            	
		            	if(arf.getMesArf() < 10) {
		            		anoMes += "0";
		            	}
	            		anoMes += String.valueOf(arf.getMesArf());
	
	            		if(Long.parseLong(anoMes) > maiorAnoMes) {
	            			maiorAnoMes = Long.parseLong(anoMes);
		            			if(arf.getQtdRealizadaArf() != null) {
		            				ultimo = arf.getQtdRealizadaArf().doubleValue();
		            			} else {
		            				ultimo = 0;
		            			}
	            		}
		            }
		            return ultimo;
	        	}
	            // Fim das verifica��es das quantidades
	        }
	        
	        return 0;
		} catch(Exception e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}		
    
    /**
	 * Seta um objeto AcompRealFisico de acordo com os par�metros do request
	 * 
     * @param request
     * @param acompRealFisicoArf
     * @throws ECARException
	 */
	public void setAcompRealFisico(HttpServletRequest request, AcompRealFisicoArf acompRealFisicoArf) throws ECARException {
				
		int countSelecionado = Pagina.getParamInt(request, "hidContSelecionado"); //numero de linhas
		
		if (countSelecionado > 0){
		
			if (acompRealFisicoArf.getItemEstrtIndResulIettr()!=null &&
					(acompRealFisicoArf.getItemEstrtIndResulIettr().getIndTipoAtualizacaoRealizado() == null) ||
					(acompRealFisicoArf.getItemEstrtIndResulIettr().getIndTipoAtualizacaoRealizado()!= null &&
					 !acompRealFisicoArf.getItemEstrtIndResulIettr().getIndTipoAtualizacaoRealizado().equals("A"))) {
				// se o campo de quantidade do ARF estiver habilitado
				if(!"".equals(Pagina.getParamStr(request, "qtdRealizadaArf" + countSelecionado))) {
					acompRealFisicoArf.setQtdRealizadaArf(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, "qtdRealizadaArf" + countSelecionado))));
				} else {
					acompRealFisicoArf.setQtdRealizadaArf(null);
				}
			}
			
			if (!"".equals(Pagina.getParamStr(request, "situacaoSit" + countSelecionado))) {
				acompRealFisicoArf.setSituacaoSit( (SituacaoSit) buscar(SituacaoSit.class, Long.valueOf(Pagina.getParamStr(request, "situacaoSit" + countSelecionado))));
			} else {
				acompRealFisicoArf.setSituacaoSit(null);
			}
			
			
			if (!"".equals(Pagina.getParamStr(request, "dataReferencia" + countSelecionado))){
				String[] dataReferencia = Pagina.getParamStr(request, "dataReferencia" + countSelecionado).toString().split("-");
				
				acompRealFisicoArf.setMesReferenciaArf(Long.parseLong(dataReferencia[0]));
				acompRealFisicoArf.setAnoReferenciaArf(Long.parseLong(dataReferencia[1]));
				
			}
			
			if (!"".equals(Pagina.getParamStr(request, "hdnEmApuracao"))){
				acompRealFisicoArf.setIndEmApuracaoArf(Pagina.getParamStr(request, "hdnEmApuracao").toString());
			}
				
			
			
			
			acompRealFisicoArf.setUsuarioUltManut(((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
			acompRealFisicoArf.setDataUltManut(Data.getDataAtual());
			
		}
			
	}
    
	
	/**
	 * Altera o objeto AcompRealFisicoArf passado como par�metro.
	 * Realiza um buscar com os parametros passados e faz o update;
	 * 
         * @param acompRealFisicoArf
         * @throws ECARException - executa o rollback da transa��o
	 */
	public void alterar(AcompRealFisicoArf acompRealFisicoArf) throws ECARException {
		Transaction tx = null;

		try {
		    ArrayList objetos = new ArrayList();
			super.inicializarLogBean();

			tx = session.beginTransaction();

			session.update(acompRealFisicoArf);
			objetos.add(acompRealFisicoArf);
				
			//Se a situa��o representa conclus�o, apago do banco todos os ARFs posteriores do indicador em quest�o.
			if(acompRealFisicoArf.getSituacaoSit() != null && "S".equals(acompRealFisicoArf.getSituacaoSit().getIndConcluidoSit())){
				List arfsPosteriores = this.getArfsPosteriores(acompRealFisicoArf);
				
				if(arfsPosteriores != null && !arfsPosteriores.isEmpty()){
					for(Iterator it = arfsPosteriores.iterator(); it.hasNext();){
						AcompRealFisicoArf arfExc = (AcompRealFisicoArf) it.next();
						session.delete(arfExc);
					}
				}
			}
				
			tx.commit();

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("ALT");
				Iterator itObj = objetos.iterator();

				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
		} catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
	}
	
    
    /**
	 * Atualiza a grade de AcompRealFisico de acordo com o numero de linhas passadas
	 * em hidCont.
	 * Realiza um buscar com os parametros passados e faz o update;
     * @param request
     * @deprecated desde 03/09/2008
	 * @throws ECARException - executa o rollback da transa��o
	 */
	public void atualizar (HttpServletRequest request) throws ECARException {
		Transaction tx = null;

		try {
		    ArrayList objetos = new ArrayList();
			super.inicializarLogBean();

			tx = session.beginTransaction();
			int cont = 1;
			int numReg = Pagina.getParamInt(request, "hidCont"); //numero de linhas
			while (cont <= numReg){
				AcompRealFisicoArf acompRealFisico = new AcompRealFisicoArf();
				
				// nova verifica��o da existencia do ARF pelo COD_ARF (Mantis 5518)
				acompRealFisico = this.buscar(Long.valueOf(Pagina.getParamStr(request, "codArf" + cont)));
				
				// se o campo de quantidade do ARF estiver habilitado
				if(!"".equals(Pagina.getParamStr(request, "qtdRealizadaArf" + cont))) {
					acompRealFisico.setQtdRealizadaArf(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, "qtdRealizadaArf" + cont))));
				} else {
					//acompRealFisico.setQtdRealizadaArf(null);
				}
				
				if (!"".equals(Pagina.getParamStr(request, "situacaoSit" + cont))) {
					acompRealFisico.setSituacaoSit( (SituacaoSit) buscar(SituacaoSit.class, Long.valueOf(Pagina.getParamStr(request, "situacaoSit" + cont))));
				} else {
					//acompRealFisico.setSituacaoSit(null);
				}
				
				acompRealFisico.setUsuarioUltManut(((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
				acompRealFisico.setDataUltManut(Data.getDataAtual());

				session.update(acompRealFisico);
				objetos.add(acompRealFisico);
				
				//Se a situa��o representa conclus�o, apago do banco todos os ARFs posteriores do indicador em quest�o.
				if(acompRealFisico.getSituacaoSit() != null && "S".equals(acompRealFisico.getSituacaoSit().getIndConcluidoSit())){
					List arfsPosteriores = this.getArfsPosteriores(acompRealFisico);
					
					if(arfsPosteriores != null && !arfsPosteriores.isEmpty()){
						for(Iterator it = arfsPosteriores.iterator(); it.hasNext();){
							AcompRealFisicoArf arfExc = (AcompRealFisicoArf) it.next();
							session.delete(arfExc);
						}
					}
				}
				
			    cont++;
			}
			tx.commit();

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("ALT");
				Iterator itObj = objetos.iterator();

				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
		} catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
	}

	/**
	 * Seta um objeto AcompRealFisico Filho de acordo com os par�metros do request
	 * 
         * @param request
         * @param acompRealFisicoArf
         * @throws ECARException
	 */
	public void setAcompRealFisicoFilho(HttpServletRequest request, AcompRealFisicoArf acompRealFisicoArf) throws ECARException {
		
		int countSelecionado = Pagina.getParamInt(request, "hidContSelecionado"); //numero de linhas
		int countFilhoSelecionado = Pagina.getParamInt(request, "hidContFilhoSelecionado");
		
		if (countSelecionado > 0){
			
			// Se o campo de quantidade do ARF estiver habilitado
			
			if (acompRealFisicoArf.getItemEstrtIndResulIettr()!=null && acompRealFisicoArf.getItemEstrtIndResulIettr().getIndTipoAtualizacaoRealizado()!= null 
					&& (!acompRealFisicoArf.getItemEstrtIndResulIettr().getIndTipoAtualizacaoRealizado().equals("A")) ) {
				if(!"".equals(Pagina.getParamStr(request, "qtdRealizadaArf" + countSelecionado + "-" + countFilhoSelecionado))) {
					acompRealFisicoArf.setQtdRealizadaArf(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, "qtdRealizadaArf" + countSelecionado + "-" + countFilhoSelecionado))));
				} else {
					acompRealFisicoArf.setQtdRealizadaArf(null);
				}
			}
			
			// Seo campo de situa��o do ARF estiver informado.
			if (!"".equals(Pagina.getParamStr(request, "situacaoSit" + countSelecionado + "-" + countFilhoSelecionado))) {
				acompRealFisicoArf.setSituacaoSit( (SituacaoSit) buscar(SituacaoSit.class, Long.valueOf(Pagina.getParamStr(request, "situacaoSit" + countSelecionado + "-" + countFilhoSelecionado))));
			} else {
				acompRealFisicoArf.setSituacaoSit(null);
			}

			acompRealFisicoArf.setUsuarioUltManut(((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
			acompRealFisicoArf.setDataUltManut(Data.getDataAtual());
			
		}
	}
	
	
    /**
	 * Atualiza a grade de AcompRealFisico de acordo com o numero de linhas passadas em hidCont.
	 * Realiza um buscar com os parametros passados e faz o update;
	 * 
     * @param request
     * @author n/c, rogerio
	 * @since 0.1, n/c
	 * @version 0.2, 03/04/2007
	 * @deprecated desde 03/09/2008
	 * @throws ECARException
	 */
	public void atualizarFilho (HttpServletRequest request) throws ECARException {
		Transaction tx = null;

		try {
		    ArrayList objetos = new ArrayList();
			super.inicializarLogBean();
			tx = session.beginTransaction();
			
			int numero = Pagina.getParamInt(request, "numFilho"); // numero de filhos na tela			
			int numReg;
						
			/* --
			 * Para o filho em que o usu�rio clicou em gravar, obtem os dados.
			 * Por Rog�rio (03/04/2007). Mantis #7832. 
			 * -- */
			// obtem o nro de linhas para aquele quadro filho.
			numReg = Pagina.getParamInt(request, "hidContFilhos"+String.valueOf(numero));
			
			for( int j = 1; j <= numReg; j++ ) {
				AcompRealFisicoArf acompRealFisico = new AcompRealFisicoArf();
				
				// Nova verifica��o da existencia do ARF pelo COD_ARF (Mantis 5518)
				acompRealFisico = this.buscar(Long.valueOf(Pagina.getParamStr(request, "codArf" + j + "-" + numero)));
			
				// Se o campo de quantidade do ARF estiver habilitado
				if(!"".equals(Pagina.getParamStr(request, "qtdRealizadaArf" + j + "-" + numero))) {
					acompRealFisico.setQtdRealizadaArf(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, "qtdRealizadaArf" + j + "-" + numero))));
				} else {
					acompRealFisico.setQtdRealizadaArf(null);
				}
				
				// Seo campo de situa��o do ARF estiver informado.
				if (!"".equals(Pagina.getParamStr(request, "situacaoSit" + j + "-" + numero))) {
					acompRealFisico.setSituacaoSit( (SituacaoSit) buscar(SituacaoSit.class, Long.valueOf(Pagina.getParamStr(request, "situacaoSit" + j + "-" + numero))));
				} else {
					acompRealFisico.setSituacaoSit(null);
				}

				acompRealFisico.setUsuarioUltManut(((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
				acompRealFisico.setDataUltManut(Data.getDataAtual());

				session.update(acompRealFisico);
				objetos.add(acompRealFisico);
				
				//Se a situa��o representa conclus�o, apago do banco todos os ARFs posteriores do indicador em quest�o.
				if(acompRealFisico.getSituacaoSit() != null && "S".equals(acompRealFisico.getSituacaoSit().getIndConcluidoSit())){
					List arfsPosteriores = this.getArfsPosteriores(acompRealFisico);
					
					if(arfsPosteriores != null && !arfsPosteriores.isEmpty()){
						for(Iterator it = arfsPosteriores.iterator(); it.hasNext();){
							AcompRealFisicoArf arfExc = (AcompRealFisicoArf) it.next();
							session.delete(arfExc);
						}
					}
				}
				
			}
			
			tx.commit();

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("ALT");
				Iterator itObj = objetos.iterator();

				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
		} catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
	}

	
	/**
	 * Retorna a quantidade Realizada de um indicador de resultado em um exerc�cio. 
	 * Utilizado para recuperar a quantidade realizada de um indicador n�o acumul�vel em um exerc�cio. 
	 * Vai retornar o valor realizado no �ltimo per�odo que tenha sido cadastrado para este exerc�cio.
	 * @param exercicio
	 * @param indResul
	 * @param aref
	 * @return
	 * @throws ECARException
	 */
	public double getQtdRealizadaExercicioNaoAcumulavel (ExercicioExe exercicio, ItemEstrtIndResulIettr indResul, AcompReferenciaAref aref) throws ECARException{
		try{
			// obter os ARIs dos AREFs do exerc�cio desejado
//			StringBuilder query = new StringBuilder("select ARI from AcompReferenciaItemAri as ARI")
//								.append(" where ARI.acompReferenciaAref.exercicioExe.codExe = :codExe")
//								.append(" and ARI.itemEstruturaIett.codIett = :codIett");
//			
//			Query q = this.getSession().createQuery(query.toString());
//			q.setLong("codExe", exercicio.getCodExe().longValue());
//			q.setLong("codIett", indResul.getItemEstruturaIett().getCodIett().longValue());
//			List listaARI = q.list();
//								
//			if(listaARI != null && !listaARI.isEmpty()) {
//				List listaMesAref = new ArrayList();
//				List listaAnoAref = new ArrayList();
//
//				Iterator it = listaARI.iterator();
//				while(it.hasNext()) {
//					AcompReferenciaItemAri ari = (AcompReferenciaItemAri)it.next();
//					
//					if(!listaMesAref.contains(Long.valueOf(ari.getAcompReferenciaAref().getMesAref()))) {
//						listaMesAref.add(Long.valueOf(ari.getAcompReferenciaAref().getMesAref()));
//					}
//					if(!listaAnoAref.contains(Long.valueOf(ari.getAcompReferenciaAref().getAnoAref()))) {
//						listaAnoAref.add(Long.valueOf(ari.getAcompReferenciaAref().getAnoAref()));
//					}
//				}
//				
//				/*
//				 * Selecionar todos os ARFs...
//				 */
//				query = new StringBuilder("select ARF.qtdRealizadaArf from AcompRealFisicoArf as ARF")
//								.append(" where ARF.mesArf in (:mes)")
//								.append(" and ARF.anoArf in (:ano)")
//								.append(" and ARF.itemEstrtIndResulIettr.codIettir=:iettir")
//								.append(" order by ARF.anoArf desc, ARF.mesArf desc");
//
//				q = this.getSession().createQuery(query.toString());
//				q.setParameterList("mes", listaMesAref);
//				q.setParameterList("ano", listaAnoAref);
//				q.setParameter("iettir", indResul.getCodIettir());

				List valores = this.getListaRealizadoExercicioByIettrAndExe(exercicio, exercicio, indResul, aref);
				
				/*
				 * Utilizando m�todo que valida se � para retornar o maior valor, �ltimo valor, etc...
				 */
				Double valor = this.getSomaValoresArfs(indResul, valores); 
				
				if(valor != null)
					return valor.doubleValue();
				else
					return 0;
//			} else {
//				return 0; 
//			}
		} catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}
		
	}
	
	/**
	 * Retorna a um valor de uma lista de valores, dependendo do indicador.<br>
	 * Se o indicador � acumul�vel, retorna a soma dos valores da lista.<br>
	 * Se o indicador n�o � acumul�vel, e seu valor final est� informado como "Maior",
	 * retorna o maior valor da lista de valores. <br>
	 * Se o indicador n�o � acumul�vel, e seu valor final est� informado como "�ltimo",
	 * retorna o PRIMEIRO* valor da lista de valores. <br>
	 * <br>
	 * *PRIMEIRO: os valores devem estar ordenados do Arf mais recente para o Arf menos recente 
	 * (do �ltimo informado para o primeiro).
	 * 
	 * @author aleixo
	 * @since 0.1 15/03/2007
	 * @version 0.3 16/03/2007, 0.2 16/03/2007
         * @param itemEstrtIndResul
         * @param valores
	 * @return Double
	 */
	public Double getSomaValoresArfs(ItemEstrtIndResulIettr itemEstrtIndResul, List<Double> valores){
        Double retorno = new Double(0);
        double total = 0;
        double maior = 0;
        
        String indAcumulavel = itemEstrtIndResul.getIndAcumulavelIettr();
        String indValorFinal = itemEstrtIndResul.getIndValorFinalIettr();
        
        for(Double valor : valores){
        	if("S".equals(indAcumulavel) && valor != null){
        		total += valor.doubleValue();
        	}
        	else {
	        	if("M".equals(indValorFinal)){ //Maior
            		if(valor != null && valor.doubleValue() > maior){
            			maior = valor.doubleValue();
   	            		total = maior;
            		}
	        	}
	        	else if("U".equals(indValorFinal) && valor != null){ //Ultimo
	        		/*
	        		 * A lista de valores j� est� vindo ordenada pelo �ltimo ARF, ent�o, � 
	        		 * s� atribuir o primeiro valor como retorno.
	        		 */
		            total = valor.doubleValue(); 
		            break;
	        	}
        	}
        }
        retorno = Double.valueOf(total);
        
        return retorno;
    }
	
	/**
	 * Retorna a Quantidadade Realizada de um Indicador de Resultado
	 * em um Exercicio.
	 *  
         * @param acompReferenciaItem
         * @param indResul
         * @param radAcumulados
         * @return double - quant
	 * @throws HibernateException
	 */
	public double getQtdRealizadaExercicio (AcompReferenciaItemAri acompReferenciaItem, 
						ItemEstrtIndResulIettr indResul, String radAcumulados) throws HibernateException{
		double total = 0;

		/*
		 * 
		// obter os ARIs dos AREFs do exerc�cio desejado
		StringBuilder query = new StringBuilder("select ARI from AcompReferenciaItemAri as ARI")
								.append(" where ARI.acompReferenciaAref.exercicioExe.codExe = :codExe")
								.append(" and ARI.itemEstruturaIett.codIett = :codIett");
		
		Query q = this.getSession().createQuery(query.toString());
		q.setLong("codExe", acompReferenciaItem.getAcompReferenciaAref().getExercicioExe().getCodExe().longValue());
		q.setLong("codIett", indResul.getItemEstruturaIett().getCodIett().longValue());
		List listaARI = q.list();
							
		if(listaARI != null && !listaARI.isEmpty()) {
			List listaMesAref = new ArrayList();
			List listaAnoAref = new ArrayList();

			Iterator it = listaARI.iterator();
			while(it.hasNext()) {
				AcompReferenciaItemAri ari = (AcompReferenciaItemAri)it.next();
				
				if(!listaMesAref.contains(Long.valueOf(ari.getAcompReferenciaAref().getMesAref()))) {
					listaMesAref.add(Long.valueOf(ari.getAcompReferenciaAref().getMesAref()));
				}
				if(!listaAnoAref.contains(Long.valueOf(ari.getAcompReferenciaAref().getAnoAref()))) {
					listaAnoAref.add(Long.valueOf(ari.getAcompReferenciaAref().getAnoAref()));
				}
			}
			query = new StringBuilder("select sum(ARF.qtdRealizadaArf) from AcompRealFisicoArf as ARF")
							.append(" where ARF.itemEstrtIndResulIettr.codIettir = :iettir");

			if("P".equalsIgnoreCase(radAcumulados)){
				query.append(" and (ARF.mesArf <= :mes and ARF.anoArf = :ano)");
			} else {
				query.append(" and ARF.mesArf in (:mes)")
				     .append(" and ARF.anoArf in (:ano)");
			}

			q = this.getSession().createQuery(query.toString());
			if(!"P".equalsIgnoreCase(radAcumulados)){
				q.setParameterList("mes", listaMesAref);
				q.setParameterList("ano", listaAnoAref);
			} else {
				q.setLong("mes", Long.parseLong(acompReferenciaItem.getAcompReferenciaAref().getMesAref()));
				q.setLong("ano", Long.parseLong(acompReferenciaItem.getAcompReferenciaAref().getAnoAref()));
			}
			q.setLong("iettir", indResul.getCodIettir().longValue());
			q.setMaxResults(1);

			Double valor = (Double) q.uniqueResult();
			if(valor != null) {
				total = valor.doubleValue();
			}
		}*/
		
		if("P".equalsIgnoreCase(radAcumulados)){ //Considera o AREF
			total = this.getQtdRealizadaExercicio(acompReferenciaItem.getAcompReferenciaAref().getExercicioExe(), indResul, acompReferenciaItem.getAcompReferenciaAref());
		} else { //Total do exerc�cio
			total = this.getQtdRealizadaExercicio(acompReferenciaItem.getAcompReferenciaAref().getExercicioExe(), indResul, null);
		}
			
		return total;
	}
	
	/**
	 * Retorna a quantidade Realizada de um indicador de resultado em um exerc�cio
	 * @param exercicio
	 * @param indResul
	 * @return
	 * @throws ECARException
	 */
	/*
	 * 
	 * Comentado por Aleixo
	 * 
	 * M�todo reescrito abaixo em : getRealizadoExercicioByIettrAndExe().
	 * 
	public double getQtdRealizadaExercicio (ExercicioExe exercicio, ItemEstrtIndResulIettr indResul) throws ECARException{
		try{
			double total = 0;

			// obter os ARIs dos AREFs do exerc�cio desejado
			StringBuilder query = new StringBuilder("select ARI from AcompReferenciaItemAri as ARI")
									.append(" where ARI.acompReferenciaAref.exercicioExe.codExe = :codExe")
									.append(" and ARI.itemEstruturaIett.codIett = :codIett");
			
			Query q = this.getSession().createQuery(query.toString());
			q.setLong("codExe", exercicio.getCodExe().longValue());
			q.setLong("codIett", indResul.getItemEstruturaIett().getCodIett().longValue());
			List listaARI = q.list();
								
			if(listaARI != null && !listaARI.isEmpty()) {
				List listaMesAref = new ArrayList();
				List listaAnoAref = new ArrayList();

				Iterator it = listaARI.iterator();
				while(it.hasNext()) {
					AcompReferenciaItemAri ari = (AcompReferenciaItemAri)it.next();
					
					if(!listaMesAref.contains(Long.valueOf(ari.getAcompReferenciaAref().getMesAref()))) {
						listaMesAref.add(Long.valueOf(ari.getAcompReferenciaAref().getMesAref()));
					}
					if(!listaAnoAref.contains(Long.valueOf(ari.getAcompReferenciaAref().getAnoAref()))) {
						listaAnoAref.add(Long.valueOf(ari.getAcompReferenciaAref().getAnoAref()));
					}
				}
				query = new StringBuilder("select sum(ARF.qtdRealizadaArf) from AcompRealFisicoArf as ARF")
							.append(" where ARF.mesArf in (:mes)")
							.append(" and ARF.anoArf in (:ano)")
							.append(" and ARF.itemEstrtIndResulIettr.codIettir = :iettir");

				q = this.getSession().createQuery(query.toString());
				
				q.setParameterList("mes", listaMesAref);
				q.setParameterList("ano", listaAnoAref);
				q.setLong("iettir", indResul.getCodIettir().longValue());
				q.setMaxResults(1);

				Double valor = (Double)q.uniqueResult();
				if(valor != null) {
					total = valor.doubleValue();
				}
			}
				
			return total;
		} catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}
		
	}*/

	/**
	 * Retorna a soma do realizado de um indicador (ARF), de um exerc�cio, considerando o acompanhamento (AREF).
	 * 
	 * @author aleixo
	 * @version 0.1 - 18/04/2007
         * @param exercicio
         * @param indResul
         * @param aref
	 * @return double
	 */
	public double getQtdRealizadaExercicio (ExercicioExe exercicio, ItemEstrtIndResulIettr indResul, AcompReferenciaAref aref){
		
		/*
		 * Passo o mesmo exerc�cio, pois quero o realizado de um exerc�cio. Ent�o o exer�cio inicial � o mesmo que o final.
		 */
		return this.getRealizadoExercicioByIettrAndExe(exercicio, exercicio, indResul, aref);
	}
	
	/**
	 * Retorna a soma do realizado de um indicador (ARF), entre um exerc�cio incial e final.
	 * 
	 * @author aleixo, cristiano
	 * @version 0.1 - 17/04/2007
         * @param exercicioExeInicial
         * @param exercicioExeFinal
         * @param iettr
         * @param aref
	 * @return double
	 */
	public double getRealizadoExercicioByIettrAndExe (ExercicioExe exercicioExeInicial, ExercicioExe exercicioExeFinal, ItemEstrtIndResulIettr iettr, AcompReferenciaAref aref){

		StringBuilder select = new StringBuilder();
		
		select.append("select sum(ARF.qtdRealizadaArf) from AcompRealFisicoArf as ARF");
		select.append(" where ARF.itemEstrtIndResulIettr.codIettir = :iettir");
		select.append(" and (ARF.anoArf > :anoInicial or (ARF.anoArf = :anoInicial and ARF.mesArf >= :mesInicial))");
		select.append(" and (ARF.anoArf < :anoFinal or (ARF.anoArf = :anoFinal and ARF.mesArf <= :mesFinal))");
		
		long anoInicial = Data.getAno(exercicioExeInicial.getDataInicialExe());
		long anoFinal = Data.getAno(exercicioExeFinal.getDataFinalExe());
		long mesInicial = Data.getMes(exercicioExeInicial.getDataInicialExe()) + 1;
		long mesFinal = Data.getMes(exercicioExeFinal.getDataFinalExe()) + 1;

		/*
		 * Se aref != null:
		 * 	Se exercicio = exercicio do aref, somar at� mes/ano do aref.
		 * 	Se exercicio > exercicio do aref, n�o somar.
		 * 	Se exercicio < exercicio do aref, continua como est�.
		 */
		if(aref != null){
			if(exercicioExeInicial.equals(aref.getExercicioExe())){
				//Mesmo exerc�cio
				mesInicial = Data.getMes(aref.getExercicioExe().getDataInicialExe()) + 1;
				anoInicial = Data.getAno(aref.getExercicioExe().getDataInicialExe());
				mesFinal = Long.parseLong(aref.getMesAref());
				anoFinal = Long.parseLong(aref.getAnoAref());
			}
			else if(exercicioExeInicial.getDataInicialExe().after(aref.getExercicioExe().getDataFinalExe())){
				//exercicio come�a depois do exerc�cio do aref
				return 0; //FIXME: Verificar se o retorno � zero ou vazio.
			}
		}
		
		
		Query q = this.session.createQuery(select.toString());
		
		q.setLong("iettir", iettr.getCodIettir().longValue());
		q.setLong("anoInicial", anoInicial);
		q.setLong("mesInicial", mesInicial);
		q.setLong("anoFinal", anoFinal);
		q.setLong("mesFinal", mesFinal);
		
		q.setMaxResults(1);
		
		Double retorno = (Double) q.uniqueResult();
		
		if(retorno != null)
			return retorno.doubleValue();

		return 0;
	}
	
	/**
	 * Retorna uma lista de realizado de um indicador (ARF) entre um exerc�cio inicial e final.
	 * 
	 * @author aleixo
	 * @version 0.1 - 17/04/2007 
         * @param exercicioExeInicial
         * @param exercicioExeFinal 
         * @param iettr
         * @param aref
         * @return List
	 */
	public List getListaRealizadoExercicioByIettrAndExe (ExercicioExe exercicioExeInicial, ExercicioExe exercicioExeFinal, ItemEstrtIndResulIettr iettr, AcompReferenciaAref aref){

		StringBuilder select = new StringBuilder();
		
		select.append("select ARF.qtdRealizadaArf from AcompRealFisicoArf as ARF");
		select.append(" where ARF.itemEstrtIndResulIettr.codIettir = :iettir");
		select.append(" and (ARF.anoArf > :anoInicial or (ARF.anoArf = :anoInicial and ARF.mesArf >= :mesInicial))");
		select.append(" and (ARF.anoArf < :anoFinal or (ARF.anoArf = :anoFinal and ARF.mesArf <= :mesFinal))");
		select.append(" order by ARF.anoArf desc, ARF.mesArf desc");
		
		long anoInicial = Data.getAno(exercicioExeInicial.getDataInicialExe());
		long anoFinal = Data.getAno(exercicioExeFinal.getDataFinalExe());
		long mesInicial = Data.getMes(exercicioExeInicial.getDataInicialExe()) + 1;
		long mesFinal = Data.getMes(exercicioExeFinal.getDataFinalExe()) + 1;

		
		/*
		 * Se aref != null:
		 * 	Se exercicio = exercicio do aref, somar at� mes/ano do aref.
		 * 	Se exercicio > exercicio do aref, n�o somar.
		 * 	Se exercicio < exercicio do aref, continua como est�.
		 */
		if(aref != null){
			if(exercicioExeInicial.equals(aref.getExercicioExe())){
				//Mesmo exerc�cio
				mesInicial = Data.getMes(aref.getExercicioExe().getDataInicialExe()) + 1;
				anoInicial = Data.getAno(aref.getExercicioExe().getDataInicialExe());
				mesFinal = Long.parseLong(aref.getMesAref());
				anoFinal = Long.parseLong(aref.getAnoAref());
			}
			else if(exercicioExeInicial.getDataInicialExe().after(aref.getExercicioExe().getDataFinalExe())){
				//exercicio come�a depois do exerc�cio do aref
				return new ArrayList();
			}
		}		
		
		Query q = this.session.createQuery(select.toString());
		
		q.setLong("iettir", iettr.getCodIettir().longValue());
		q.setLong("anoInicial", anoInicial);
		q.setLong("mesInicial", mesInicial);
		q.setLong("anoFinal", anoFinal);
		q.setLong("mesFinal", mesFinal);
		
		return q.list();
	}

	/**
	 * Retorna a quantidade realizada de um indicador de resultado em um exerc�cio at� um determinado m�s/ano
	 * @param exercicio
	 * @param indResul
	 * @param mes
	 * @param ano
	 * @return
	 * @throws ECARException
	 */
	/*
	 * Comentado por Claudismar: M�todo n�o utilizado mais. (19/04/2007)
	 *  
	public double getQtdRealizadaExercicio (ExercicioExe exercicio, ItemEstrtIndResulIettr indResul, int mes, int ano) throws ECARException{
		try{
			double total = 0;
			List meses = new ExercicioDao(request).getMesesDentroDoExercicio(exercicio, mes, ano);
			
			StringBuilder query = new StringBuilder("select sum(ARF.qtdRealizadaArf) from AcompRealFisicoArf as ARF")
							.append(" where ARF.itemEstrtIndResulIettr.codIettir = :iettir")
							.append(" and ARF.mesArf = :mes")
							.append(" and ARF.anoArf = :ano");
				
			Query q = this.getSession().createQuery(query.toString());
			q.setLong("iettir", indResul.getCodIettir().longValue());
							
			Iterator itMeses = meses.iterator();
			while(itMeses.hasNext()){
				String chave = itMeses.next().toString();
				String strMes = chave.split("-")[0];
				if(strMes.length() == 1) {
					strMes = "0" + strMes;
				}
				String strAno = chave.split("-")[1];
				q.setLong("mes", Long.parseLong(strMes));
				q.setLong("ano", Long.parseLong(strAno));
				q.setMaxResults(1);

				Double resultado = (Double)q.uniqueResult();
				if(resultado != null) {
					total += resultado.doubleValue();
				}
			}
			
			/*
			 * FIXME: VERIFICAR ISSO!
			StringBuilder query = new StringBuilder("select sum(ARF.qtdRealizadaArf) from AcompRealFisicoArf as ARF")
			.append(" where ARF.itemEstrtIndResulIettr.codIettir = :iettir")
			.append(" and (ARF.anoArf < :ano or (ARF.anoArf = :ano and ARF.mesArf <= :mes))");

			Query q = this.getSession().createQuery(query.toString());
			q.setLong("iettir", indResul.getCodIettir().longValue());
			
			q.setLong("mes", mes);
			q.setLong("ano", ano);
			
			q.setMaxResults(1);
			total = ((Double)q.uniqueResult()).doubleValue();
			*/
		/*	return total;
		} catch(HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
		
	}
	*/
	
	/**
	 * Retorna a quantidade realizada de um indicador de resultado em um exerc�cio at� um determinado m�s/ano
	 * @param indResul
	 * @param mes
	 * @param ano
	 * @return
	 * @throws ECARException
	 */
	public Double getQtdRealizadaMesAno (ItemEstrtIndResulIettr indResul, Long mes, Long ano) throws ECARException{
		try{
			
			Double valor = null;
			
			StringBuilder queryVerificaDataReferenciaPreenchida = new StringBuilder("select count(*) from AcompRealFisicoArf as arf")
																	.append(" where arf.itemEstrtIndResulIettr.codIettir = ?")
																	.append(" and arf.anoReferenciaArf=? and arf.mesReferenciaArf=?");

			Query queryDataReferencia = session.createQuery(queryVerificaDataReferenciaPreenchida.toString());
			queryDataReferencia.setLong(0, indResul.getCodIettir().longValue());
			queryDataReferencia.setLong(1, ano.longValue());
			queryDataReferencia.setLong(2, mes.longValue());
			
			Long quantidadeReferencia = (Long) queryDataReferencia.uniqueResult();

			if(quantidadeReferencia != null && quantidadeReferencia > 0L){
				
				StringBuilder sbPegaQtdDataReferenciaPreenchida = new StringBuilder("select arf.qtdRealizadaArf from AcompRealFisicoArf as arf")
				.append(" where arf.itemEstrtIndResulIettr.codIettir = ?")
				.append(" and arf.anoReferenciaArf=? and arf.mesReferenciaArf=?")
				.append(" order by arf.dataUltManut desc");

				Query queryPegaQtdDataReferenciaPreenchida = session.createQuery(sbPegaQtdDataReferenciaPreenchida.toString());
				queryPegaQtdDataReferenciaPreenchida.setLong(0, indResul.getCodIettir().longValue());
				queryPegaQtdDataReferenciaPreenchida.setLong(1, ano.longValue());
				queryPegaQtdDataReferenciaPreenchida.setLong(2, mes.longValue());
				queryPegaQtdDataReferenciaPreenchida.setMaxResults(1);
				
				
				valor = (Double) queryPegaQtdDataReferenciaPreenchida.uniqueResult();
				
				if (valor != null)
					return valor;
				else
					return null;
				
			} else {

				StringBuilder baseQuery = new StringBuilder(
						"select sum(arf.qtdRealizadaArf) from AcompRealFisicoArf as arf")
						.append(" where arf.itemEstrtIndResulIettr.codIettir = ?")
						.append(" and arf.anoArf=? and arf.mesArf=?")
						.append(" and arf.anoReferenciaArf=null and arf.mesReferenciaArf=null");

				Query q = session.createQuery(baseQuery.toString());
				q.setLong(0, indResul.getCodIettir().longValue());
				q.setLong(1, ano.longValue());
				q.setLong(2, mes.longValue());
				q.setMaxResults(1);

				valor = ((Double) q.uniqueResult());

				if (valor != null)
					return valor;
				else
					return null;
			}
			
		} catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}
		
	}
	
	/**
	 * Retorna um Map com a quantidade realizada de um indicador de resultado em cada m�s dentro de um exerc�cio
	 * As chaves do Map s�o no formatdo "mes-ano" ex: 01/2005 12/2004
	 * @param exercicio
	 * @param indResul
	 * @return
	 * @throws ECARException
	 */
	public Map getQtdRealizadaExercicioPorMes (ExercicioExe exercicio, ItemEstrtIndResulIettr indResul) throws ECARException{
		try{
			
			//FIXME: Rever este caso
			
			Map mapaMeses = new HashMap();
			
			List meses = new ExercicioDao(request).getMesesDentroDoExercicio(exercicio);

			Iterator itMeses = meses.iterator();
			while(itMeses.hasNext()){
				String chave = itMeses.next().toString();
				String mes = chave.split("-")[0];
				if(mes.length() == 1)
					mes = "0" + mes;				
				String ano = chave.split("-")[1];
				
				mapaMeses.put(chave, this.getQtdRealizadaMesAno(indResul, Long.valueOf(mes), Long.valueOf(ano)));
				
			}
				
			return mapaMeses;
		} catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}
		
	}
		
        /**
         *
         * @param acompReferenciaItem
         * @param itemMonitorado
         * @param indicador
         * @return
         * @throws ECARException
         */
        public AcompRealFisicoArf criaNovoAcompRealFisico(AcompReferenciaItemAri acompReferenciaItem,
			ItemEstruturaIett itemMonitorado, ItemEstrtIndResulIettr indicador) throws ECARException {
		
		// verificar a exist�ncia do ARF
        AcompRealFisicoArf acompRealFisico = this.buscarPorIettir( 
        		Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getMesAref()),
        		Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getAnoAref()),
        		indicador.getCodIettir());
        
        if(acompRealFisico == null) {
        	acompRealFisico = new AcompRealFisicoArf();

        	acompRealFisico.setItemEstrtIndResulIettr(indicador);
            acompRealFisico.setDataInclusaoArf(Data.getDataAtual());
            acompRealFisico.setItemEstruturaIett(itemMonitorado);
            acompRealFisico.setMesArf(Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getMesAref()));
            acompRealFisico.setAnoArf(Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getAnoAref()));
        }
        
        return acompRealFisico;
    
	}
	
	/**
     * Devolve uma lista de todos os AcompRealFisico de um item referente 
     * � um AcompanhamentoReferenciaItem e � situa��o.
     *
     * Situa��o:
     * 	-> N�o Conclu�dos (Alterar para DEFAULT, atualmente � o Todos):
     * 		- Mostrar ARFs com situa��o que N�O represente conclus�o
     * 		- Mostrar ARFs com situa��o que represente conclus�o e M�s/Ano ARF maior igual M�s/Ano do acompanhamento (AREF)
     * 	-> Todos:
     * 		- Mostrar todos os ARFs, considerando:
     * 		- ARFs com situa��o que representa conclus�o e M�s/Ano ARF menor que o M�s/Ano do acompanhamento (AREF)
     * 		- Demais ARFs
     * 	-> Conclu�dos:
     * 		- Mostrar ARFs com situa��o que representa conclus�o e M�s/Ano do ARF menor e igual ao M�s/Ano do acompanhamento (AREF)
     * 
     * AcompRealFisico � formada pela chave contendo IndResultado, AcompReferenciaItem e Item
     *  
     * @param acompRefItem
     * @param indSituacao (Dominios.TODOS, Dominios.CONLUIDOS, Dominios.NAO_CONCLUIDOS)
     * @param noMes (se true, s� considera o mes do acompRefItem)
     * @return lista (AcompRealFisico)
         * @throws Exception
         * @throws HibernateException
     */
    public List getIndResulByAcompRefItemBySituacao (AcompReferenciaItemAri acompRefItem, String indSituacao, boolean noMes) throws Exception{
    	List lista = new ArrayList();
    	
    	StringBuilder query = new StringBuilder();
    	
    	query.append("select acompRF from AcompRealFisicoArf as acompRF");
    	query.append(" where acompRF.itemEstruturaIett.codIett = :codIett");
    	query.append(" and acompRF.itemEstruturaIett.indAtivoIett = 'S'");
    	//Mantis-POA: caso 0010021 
		//Adicionado para n�o listar no acompanhamento os indicadores exclu�dos no cadastro
    	query.append(" and acompRF.itemEstrtIndResulIettr.indAtivoIettr = 'S'");

    	
    	if(Dominios.CONCLUIDOS.equals(indSituacao)){
    		
    		/* Conclu�dos:
    		 * Mostrar ARF's com situa��o que representa conclus�o.
    		 */
    		
    		query.append(" and (acompRF.situacaoSit.indConcluidoSit = :indConcluido) ");
    	}

    	/*
    	 *  Selecionar sempre itens com m�s/ano do ARF menor e igual ao m�s/ano do AREF (Acompanhamento)
    	 */
		query.append(" and (acompRF.anoArf < :ano or (acompRF.anoArf = :ano and ");
		if(noMes)
			query.append(" acompRF.mesArf = :mes))");
		else
			query.append(" acompRF.mesArf <= :mes))");
    	
    	query.append(" order by acompRF.itemEstrtIndResulIettr.nomeIettir asc, acompRF.anoArf desc, acompRF.mesArf desc ");

    	Query q = this.session.createQuery(query.toString());
    	
    	q.setLong("codIett", acompRefItem.getItemEstruturaIett().getCodIett().longValue());
    	
    	if(Dominios.CONCLUIDOS.equals(indSituacao)){
    		q.setString("indConcluido", "S");
    	}
    	
		q.setLong("ano", Long.valueOf(acompRefItem.getAcompReferenciaAref().getAnoAref()).longValue());
		q.setLong("mes", Long.valueOf(acompRefItem.getAcompReferenciaAref().getMesAref()).longValue());
    	
    	lista = q.list();
    	
    	if(lista == null){
    		return new ArrayList();
    	}
    	
		/*Tirando os repetidos... */
		/*
		 * Como os ARF's v�m ordenados do �ltimo informado menor ou igual ao mes/ano do AREF,
		 * s� deixo o primeiro ARF de cada indicador.
		 */
		String codIettr = "";
		for(Iterator it = lista.iterator(); it.hasNext();){
			AcompRealFisicoArf arf = (AcompRealFisicoArf) it.next();
			
			if(codIettr.equals(arf.getItemEstrtIndResulIettr().getCodIettir().toString())){
				it.remove();
			}
			else{
				codIettr = arf.getItemEstrtIndResulIettr().getCodIettir().toString();
			}
		}
		
    	//Remover os ARFs quando for N�o Conclu�do (Devido ao problema do "situacao_sit is null" n�o funcionar no Hibernate3 ) 
    	if(Dominios.NAO_CONCLUIDOS.equalsIgnoreCase(indSituacao)){
        	/*
    		Aplicar nova regra, conforme mantis 7795:
			1) N�o Conclu�dos (Alterar para DEFAULT, atualmente � o Todos) :
			
				Apresentar indicadores que n�o representem conclus�o at� mes/ano do AREF.
			
        	 	*** Apresentar todos os dados habilitados para altera��o no m�s/ano ***
        	 */
   			for(Iterator it = lista.iterator(); it.hasNext();){
   				AcompRealFisicoArf arf = (AcompRealFisicoArf)it.next();

   				//Se representa conclus�o
   				if(arf.getSituacaoSit() != null && "S".equals(arf.getSituacaoSit().getIndConcluidoSit())){ 
					int mesArf = arf.getMesArf().intValue();
					int anoArf = arf.getAnoArf().intValue();
					int mesAref = Integer.valueOf(acompRefItem.getAcompReferenciaAref().getMesAref()).intValue();
					int anoAref = Integer.valueOf(acompRefItem.getAcompReferenciaAref().getAnoAref()).intValue();
    				
					//Se n�o � o mesmo mes/ano do acompanhamento
					if(!(mesAref == mesArf && anoAref == anoArf)){
						//Remove da lista
						it.remove();
					}
   				}
   				
   			}
   			
   			
    	}

    	
    	ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
    	
    	
    	if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null){
    		//Obter os sisAtributos de grupos de metas f�sicas configurados 
			Set sisAtributosSatbs = acompRefItem.getAcompReferenciaAref().getTipoAcompanhamentoTa().getSisAtributoSatbs();
			List listAtributosConfig = new ArrayList();
			if(sisAtributosSatbs != null){
				Iterator itSatbs = sisAtributosSatbs.iterator();
				while(itSatbs.hasNext()) {
					SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) itSatbs.next();
					if(sisAtributoSatb.getSisGrupoAtributoSga().equals(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas())){
						listAtributosConfig.add(sisAtributoSatb);
					}
				}
			}
			
			//Se n�o h� "tipos de indicadores" configurados, limpa a lista de retorno 
			if (sisAtributosSatbs.isEmpty()){
				lista.clear();
				return lista;
			}
			
			//remover os indicadores que n�o perten�am � cole��o de atributos identificadas acima  
			if(!listAtributosConfig.isEmpty()){
				
				Iterator it = lista.iterator();
	   			while(it.hasNext()) {
	   				AcompRealFisicoArf arf = (AcompRealFisicoArf)it.next();
	    				
	   				if(arf.getItemEstrtIndResulIettr().getSisAtributoSatb() != null && !listAtributosConfig.contains(arf.getItemEstrtIndResulIettr().getSisAtributoSatb())) {
	   					it.remove();
	   				} 
	   			}
			}

			//Ordenando pelo atributo da configura��o
			//Para trazer agrupado na tela.
			Collections.sort(lista, new Comparator(){
				public int compare(Object arg0, Object arg1) {
					
					AcompRealFisicoArf arf1 = (AcompRealFisicoArf) arg0;
					AcompRealFisicoArf arf2 = (AcompRealFisicoArf) arg1;
					
					if(arf1.getItemEstrtIndResulIettr().getSisAtributoSatb() == null && arf2.getItemEstrtIndResulIettr().getSisAtributoSatb() == null){
						return 0;
					}
					else if(arf1.getItemEstrtIndResulIettr().getSisAtributoSatb() != null && arf2.getItemEstrtIndResulIettr().getSisAtributoSatb() == null){
						return 1;
					}
					else if(arf1.getItemEstrtIndResulIettr().getSisAtributoSatb() == null && arf2.getItemEstrtIndResulIettr().getSisAtributoSatb() != null){
						return -1;
					}
					
					return arf1.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb().compareTo(arf2.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb());
				}
			});
			
    	}
    	
    	return lista;
    }

    /**
     * Verifica se � para habilitar ou n�o a edi��o de ARFs na elabora��o de acompanhamento.
     * 
     * @author aleixo
     * @since 07/06/2007
     * @param arf
     * @param mesReferencia
     * @param indSituacao
     * @return
     */
    public boolean habilitarEdicaoArf(AcompRealFisicoArf arf, AcompReferenciaAref mesReferencia, String indSituacao){
    	/*
		Aplicar nova regra, conforme mantis 7795:
		*/
    	if(Dominios.NAO_CONCLUIDOS.equals(indSituacao)){
    		/*
			1) N�o Conclu�dos ):
				- Apresentar todos os dados habilitados para altera��o no m�s/ano ***
    		 */
			int mesArf = arf.getMesArf().intValue();
			int anoArf = arf.getAnoArf().intValue();
			int mesAref = Integer.valueOf(mesReferencia.getMesAref()).intValue();
			int anoAref = Integer.valueOf(mesReferencia.getAnoAref()).intValue();
			
			return (mesArf == mesAref && anoArf == anoAref);
    	}
    	else if (Dominios.CONCLUIDOS.equals(indSituacao)){
    		/*
			1) Conclu�dos ):
				- Apresentar o dado habilitado para altera��o no m�s/ano ***
    		 */
			int mesArf = arf.getMesArf().intValue();
			int anoArf = arf.getAnoArf().intValue();
			int mesAref = Integer.valueOf(mesReferencia.getMesAref()).intValue();
			int anoAref = Integer.valueOf(mesReferencia.getAnoAref()).intValue();
			
			return (mesArf == mesAref && anoArf == anoAref);
    	}
    	else{
    		/*
			2) Todos:
			- ARFs com situa��o que representa conclus�o e M�s/Ano ARF menor que o M�s/Ano do acompanhamento (AREF)
			*** Apresentar todos os dados desabilitados para altera��o ***
			- Demais ARFs
			*** Apresentar todos os dados habilitados para altera��o ***
    		 */
			int mesArf = arf.getMesArf().intValue();
			int anoArf = arf.getAnoArf().intValue();
			int mesAref = Integer.valueOf(mesReferencia.getMesAref()).intValue();
			int anoAref = Integer.valueOf(mesReferencia.getAnoAref()).intValue();
			
    		if(arf.getSituacaoSit() != null && "S".equals(arf.getSituacaoSit().getIndConcluidoSit()) 
    			&& (anoArf < anoAref || (anoArf == anoAref && mesArf < mesAref))){
				return false;
    		}
    		else {
    			return true;
    		}
    	}
    }
    
    /**
     *
     * @param request
     * @throws ECARException
     */
    public void incAcompRealFisicoArf(HttpServletRequest request) throws ECARException {
		Transaction tx = null;

		try {
			super.inicializarLogBean();
			tx = session.beginTransaction();
			
			long codAri;
			
			//Ajuste para setar o codAri com o valor correto
			//Estava pegando o ari do pai ao inserir uma m�trica nova no filho
			if (Pagina.getParamStr(request, "codAriFilho") == "")
			   codAri = Long.valueOf(Pagina.getParamStr(request, "codAri"));
		   else
				codAri = Long.valueOf(Pagina.getParamStr(request, "codAriFilho"));   
			
			AcompReferenciaItemAri acompRefItem = (AcompReferenciaItemAri) buscar(AcompReferenciaItemAri.class, codAri);
			ItemEstrtIndResulIettr indResul = (ItemEstrtIndResulIettr) buscar(ItemEstrtIndResulIettr.class, Long.valueOf(Pagina.getParamStr(request, "codNovoIndicador")));
			
			AcompRealFisicoArf acompRealFisico = this.criaNovoAcompRealFisico(acompRefItem, acompRefItem.getItemEstruturaIett(), indResul);

			session.save(acompRealFisico);
			
			tx.commit();

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setObj(acompRealFisico);
				super.logBean.setOperacao("INC");
				super.loggerAuditoria.info(logBean.toString());
			}
		} catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}

    }

    /**
	 * Retorna a quantidade de registros de indicadores de resultado em um per�odo
	 * @param iettr
	 * @param iett
	 * @param mes
	 * @param ano
	 * @return int
	 * @throws ECARException
	 */
	public int getQtdRegistrosRealizadoPeriodo (ItemEstrtIndResulIettr iettr, ItemEstruturaIett iett, Long mes, Long ano) throws ECARException{
		try{
			int resultado = 0;
			
			StringBuilder baseQuery = new StringBuilder("select arf.qtdRealizadaArf from AcompRealFisicoArf as arf")
									.append(" where arf.itemEstrtIndResulIettr.codIettir = ?")
									.append(" and arf.itemEstruturaIett.codIett = ?")
									.append(" and arf.itemEstruturaIett.indAtivoIett = 'S'")
									.append(" and arf.qtdRealizadaArf > 0 ")
									.append(" and (arf.anoArf < ?")
									.append(" 		or (arf.anoArf = ?")
									.append("			and arf.mesArf <= ?)")
									.append("		)");
			
			Query q = session.createQuery(baseQuery.toString());
			q.setLong(0, iettr.getCodIettir().longValue());
			q.setLong(1, iett.getCodIett().longValue());
			q.setLong(2, ano.longValue());
			q.setLong(3, ano.longValue());
			q.setLong(4, mes.longValue());

            List retorno = q.list();
            if(!retorno.isEmpty()) {
            	resultado = retorno.size();
            }

            return resultado;
			
		} catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}
		
	}
    
    
	@SuppressWarnings("unchecked")
	public Double getRealizadoPeriodo (ItemEstrtIndResulIettr iettr, ItemEstruturaIett iett, Long mes, Long ano) throws ECARException{
		try{
			
			StringBuilder baseQuery = new StringBuilder("select arf.qtdRealizadaArf from AcompRealFisicoArf as arf")
									.append(" where arf.itemEstrtIndResulIettr.codIettir = ?")
									.append(" and arf.itemEstruturaIett.codIett = ?")
									.append(" and arf.itemEstruturaIett.indAtivoIett = 'S'")
									.append(" and arf.qtdRealizadaArf > 0 ")
									.append(" and (arf.anoArf < ?")
									.append(" 		or (arf.anoArf = ?")
									.append("			and arf.mesArf <= ?)")
									.append("		)");
			
			Query q = session.createQuery(baseQuery.toString());
			q.setLong(0, iettr.getCodIettir().longValue());
			q.setLong(1, iett.getCodIett().longValue());
			q.setLong(2, ano.longValue());
			q.setLong(3, ano.longValue());
			q.setLong(4, mes.longValue());

			List result = q.list();
			if(result.size() > 0) {
				return (Double) q.list().get(0);
			}else {
				return 0.0; //Nao pode retornar 0
			}

		} catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}
		
	}	
	
    /**
     * Verificar se � permitido excluir um ARF, verificando se n�o est� em uso por algum ARI, desconsiderando o ARI fornecido.
     * 
     * @param arf
     * @param ari AcompReferenciaItemAri
     * 
     * @return boolean (True = Exclus�o permitida - False = N�o permitida)
     * @throws ECARException
     */
    public boolean verificarPermissaoExclusao(AcompRealFisicoArf arf, AcompReferenciaItemAri ari) throws ECARException {
    	try {
			StringBuilder query = new StringBuilder("select ARI from AcompReferenciaItemAri as ARI")
								.append(" where ARI.itemEstruturaIett.codIett = :codIett")
								.append(" and ARI.itemEstruturaIett.indAtivoIett = 'S'")
								.append(" and ARI.acompReferenciaAref.mesAref = :mesAref")
								.append(" and ARI.acompReferenciaAref.anoAref = :anoAref")
								.append(" and ARI.codAri <> :codAri");
			
			Query q = this.getSession().createQuery(query.toString());
			
			q.setLong("codIett", arf.getItemEstruturaIett().getCodIett().longValue());
			q.setString("mesAref", arf.getMesArf().toString());
			q.setString("anoAref", arf.getAnoArf().toString());
			q.setLong("codAri", ari.getCodAri().longValue());
			
			List listaARI = q.list();
			
			if(listaARI != null) {
				if(listaARI.size() > 0) {
					// ARF utilizado para mais de um ARI
					return false;
				}
				else {
					return true;
				}
			}
			return true;
    	}
    	catch(Exception e) {
    		this.logger.error(e);
			throw new ECARException(e);
    	}
    }
	
	
	/**
	 * Obtem o mes/ano mais recente que foi registrado acompanhamento para um item  
         * @param iett
	 * @return AcompRealFisicoArf
	 * @throws ECARException
	 */
	public AcompRealFisicoArf getArfMaisRecenteItem(ItemEstruturaIett iett) throws ECARException{
		try{
			StringBuilder query = new StringBuilder("select ARF from AcompRealFisicoArf as ARF")
									.append(" where ARF.itemEstruturaIett.codIett = ?")
									.append(" and ARF.itemEstruturaIett.indAtivoIett = 'S'")
									.append(" order by ARF.anoArf desc, ARF.mesArf desc");

			Query q = this.getSession().createQuery(query.toString());
			q.setLong(0, iett.getCodIett().longValue());
			q.setMaxResults(1);
			
			return (AcompRealFisicoArf) q.uniqueResult();
		} catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}
	}
	
	
	/**
	 * Obtem o mes/ano mais antigo que foi registrado acompanhamento para um item  
         * @param iett
	 * @return AcompRealFisicoArf
	 * @throws ECARException
	 */
	public AcompRealFisicoArf getArfMaisAntigoItem(ItemEstruturaIett iett) throws ECARException{
		try{
			StringBuilder query = new StringBuilder("select ARF from AcompRealFisicoArf as ARF")
								.append(" where ARF.itemEstruturaIett.codIett = ?")
								.append(" and ARF.itemEstruturaIett.indAtivoIett = 'S'")
								.append(" order by ARF.anoArf, ARF.mesArf");

			Query q = this.getSession().createQuery(query.toString());
			q.setLong(0, iett.getCodIett().longValue());
			q.setMaxResults(1);
			
			return (AcompRealFisicoArf) q.uniqueResult();
		} catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}
	}
	
	/**
	 * Busca uma lista de Realizado F�sico a partir de um item pai com filtros definidos em um Tipo de Acompanhamento.<br>
	 * Retorna uma lista de bean que contem o item e uma lista de arfs do item (inclusive o item pai).
	 * 
	 * @author aleixo
	 * @since 0.2 - 07/05/2007; 0.1 - 04/05/2007
         * @param item
         * @param tipoAcomp
         * @param filtroNiveis
         * @param mesArf 
         * @param anoArf 
         * @param configuracao
	 * @return List - Lista de Realizado F�sico (ARF's).
	 * @throws ECARException 
	 */
	public List getArfsByIettAndTipoAcomp(ItemEstruturaIett item, TipoAcompanhamentoTa tipoAcomp, ConfiguracaoCfg configuracao, List filtroNiveis, Long mesArf, Long anoArf) throws ECARException{
		
		List retorno = new ArrayList();
		if(item != null && tipoAcomp != null){
			ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(this.request);
			/*
				- Inclui Monitorado
				- Inclui N�o Monitorado
			 */
			String indMonitoramento = "";
		    
		    if("S".equals(tipoAcomp.getIndMonitoramentoTa()) && "N".equals(tipoAcomp.getIndNaoMonitoramentoTa())) {
		    	indMonitoramento = "S";
		    }
		    else if("N".equals(tipoAcomp.getIndMonitoramentoTa()) && "S".equals(tipoAcomp.getIndNaoMonitoramentoTa())) {
			    indMonitoramento = "N";
		    }			
			
		    
		    List niveis = new ArrayList();
		    List grupoMetaFisica = new ArrayList();
		    
		    if(tipoAcomp.getSisAtributoSatbs() != null && !tipoAcomp.getSisAtributoSatbs().isEmpty()){
			    Iterator itSatb = tipoAcomp.getSisAtributoSatbs().iterator();
			    while(itSatb.hasNext()){
			    	SisAtributoSatb satb = (SisAtributoSatb) itSatb.next();
				    /*
				    - N�vel do Planejamento
				    */
				    if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan() != null && configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan().equals(satb.getSisGrupoAtributoSga())){
				    	niveis.add(satb.getCodSatb());
				    }
			    	
				    /*
					- Grupo de Meta F�sica
				    */
				    if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null && configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas().equals(satb.getSisGrupoAtributoSga())){
				    	grupoMetaFisica.add(satb.getCodSatb());
				    }
			    }
			    
			    
		    }
		    
		    //List itensFilhos = itemEstruturaDao.getDescendentes(item, true);
		    List itens = new ArrayList();
		    itens.add(item);
		    
		    if(filtroNiveis != null && !filtroNiveis.isEmpty())
		    	itens.addAll(itemEstruturaDao.getDescendentesPorNivelPlanejamento(item, filtroNiveis));
		    else
		    	itens.addAll(itemEstruturaDao.getDescendentes(item, true));
		    
		    if(itens != null && !itens.isEmpty()){
			    
		    	Iterator it = itens.iterator();
		    	while(it.hasNext()){
		    		
		    		ItemEstruturaIett iett = (ItemEstruturaIett) it.next();
		    	
				    StringBuilder select = new StringBuilder();
				    select.append("select arf from AcompRealFisicoArf arf");
				    select.append(" where arf.itemEstruturaIett.codIett = :codIett");
				    select.append(" and arf.itemEstruturaIett.indAtivoIett = 'S'");
				    
				    if(mesArf != null){
				    	select.append(" and arf.mesArf = :mesArf");
				    }
				    if(anoArf != null){
				    	select.append(" and arf.anoArf = :anoArf");
				    }
				    if(!"".equals(indMonitoramento)){
				    	select.append(" and arf.itemEstruturaIett.indMonitoramentoIett = :indMonitoramento");
				    }
				    if(!niveis.isEmpty()){
				    	select.append(" and arf.itemEstruturaIett.itemEstruturaNivelIettns.codSatb in (:niveis)");
				    }
				    if(!grupoMetaFisica.isEmpty()){
				    	select.append(" and arf.itemEstrtIndResulIettr.sisAtributoSatb.codSatb in (:grupoMetaFisica)");
				    }

				    Query q = this.session.createQuery(select.toString());
				    
				    q.setLong("codIett", iett.getCodIett().longValue());
				    
				    if(mesArf != null){
				    	q.setLong("mesArf", mesArf.longValue());
				    }
				    if(anoArf != null){
				    	q.setLong("anoArf", anoArf.longValue());
				    }
				    if(!"".equals(indMonitoramento)){
				    	q.setString("indMonitoramento", indMonitoramento);
				    }
				    if(!niveis.isEmpty()){
				    	q.setParameterList("niveis", niveis);
				    }
				    
				    if(!grupoMetaFisica.isEmpty()){
				    	q.setParameterList("grupoMetaFisica", grupoMetaFisica);
				    }
				     
				    List arfs = q.list();
				    if((item.equals(iett)) || (arfs != null && !arfs.isEmpty())){
					    IettArfBean iaBean = new IettArfBean();
					    iaBean.setItem(iett);
					    iaBean.setArfs(arfs);
					    
					    retorno.add(iaBean);
				    }
		    	}
		    }
		}
		return retorno;
	}
	

	/**
	 * Retorna uma lista de realizados f�sicos posteriores ao realizado f�sico passado como par�metro.<br>
	 * Caso n�o exista, retorna uma lista vazia.
	 * 
	 * @author aleixo
	 * @since 23/07/2007
	 * @param arf
	 * @return List
	 * @throws ECARException
	 */
	public List getArfsPosteriores(AcompRealFisicoArf arf) throws ECARException{
		List retorno = new ArrayList();
		try{
			StringBuilder s = new StringBuilder();
			
			s.append("select arf from AcompRealFisicoArf arf");
			s.append(" where arf.itemEstruturaIett.codIett = :item");
			s.append(" and arf.itemEstruturaIett.indAtivoIett = 'S'");
			s.append("   and arf.itemEstrtIndResulIettr.codIettir = :indicador");
			s.append("   and (arf.anoArf > :ano or (arf.anoArf = :ano and arf.mesArf > :mes))");
			
			Query q = this.session.createQuery(s.toString());
			
			q.setLong("item", arf.getItemEstruturaIett().getCodIett().longValue());
			q.setLong("indicador", arf.getItemEstrtIndResulIettr().getCodIettir().longValue());
			q.setLong("ano", arf.getAnoArf().longValue());
			q.setLong("mes", arf.getMesArf().longValue());
			
			List arfs = q.list();
			
			retorno = (arfs != null && !arfs.isEmpty()) ? arfs : new ArrayList();
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
		
		return retorno;
	}
	
	/**
	 * Retorna o Realizado F�sico do indicador informado, anterior ao m�s/ano informado.
	 * 
	 * @author aleixo
	 * @since 23/07/2007
	 * @param indicador
	 * @param mes
	 * @param ano
	 * @return
	 * @throws ECARException
	 */
	public AcompRealFisicoArf getArfAnterior(ItemEstrtIndResulIettr indicador, long mes, long ano) throws ECARException{
		try{
			StringBuilder s = new StringBuilder();
			
			s.append("select arf from AcompRealFisicoArf arf");
			s.append(" where arf.itemEstruturaIett.codIett = :item");
			s.append(" and arf.itemEstruturaIett.indAtivoIett = 'S'");
			s.append("   and arf.itemEstrtIndResulIettr.codIettir = :indicador");
			s.append("   and (arf.anoArf < :ano or (arf.anoArf = :ano and arf.mesArf < :mes))");
			s.append(" order by arf.anoArf desc, arf.mesArf desc");
			
			Query q = this.session.createQuery(s.toString());
			
			q.setLong("item", indicador.getItemEstruturaIett().getCodIett().longValue());
			q.setLong("indicador", indicador.getCodIettir().longValue());
			q.setLong("ano", ano);
			q.setLong("mes", mes);
			
			q.setMaxResults(1);
			
			Object o = q.uniqueResult();
			
			return (o != null) ? (AcompRealFisicoArf) o : null;
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}
	
	/**
	 *  Retorna a soma do realizado de um indicador (ARF) de um exerc�cio at� o m�s de refer�ncia passado como par�metro.
	 *  
	 * @param indicador
	 * @param exercicio
	 * @param anoRef
	 * @param mesRef
	 * @return A soma dos valores realizados para um indicador
	 * @throws ECARException
	 */
	public double getQtdIndicadorGrupoRealizadaExercicio(ItemEstrtIndResulIettr indicador, ExercicioExe exercicio, long anoRef, long mesRef) throws ECARException{
		try{
			StringBuilder s = new StringBuilder();
			
			s.append("select sum(ARF.qtdRealizadaArf) from AcompRealFisicoArf as ARF");			
			s.append("  where ARF.itemEstruturaIett.codIett = :item");
			s.append("    and ARF.itemEstruturaIett.indAtivoIett = 'S'");
			s.append("    and ARF.itemEstrtIndResulIettr.codIettir = :indicador");
			s.append("    and ARF.anoArf = :anoArf");
			
			//Limita a consulta at� o m�s de refer�ncia informado.
			if(Long.valueOf(exercicio.getDescricaoExe()).equals(anoRef)){
				s.append("    and ARF.mesArf <= :mesFinal");
			}
			
			Query q = this.session.createQuery(s.toString());
			
			//q.setString("anoArf",exercicio.getDescricaoExe());
			q.setInteger("anoArf",Integer.parseInt(exercicio.getDescricaoExe()));
			q.setLong("item", indicador.getItemEstruturaIett().getCodIett().longValue());
			q.setLong("indicador", indicador.getCodIettir().longValue());
			
			if(Long.valueOf(exercicio.getDescricaoExe()).equals(anoRef)){
				q.setLong("mesFinal", mesRef);
			}
			
			q.setMaxResults(1);
			
			Double retorno = (Double) q.uniqueResult();
			
			if(retorno != null)
				return retorno.doubleValue();

			return 0;
		}
		catch (HibernateException e) {
			this.logger.error(e);
			e.printStackTrace();
			throw new ECARException(e);
		}
	}
	
	/**
	 * Retorna a quantidade Realizada de um indicador de resultado em um exerc�cio (at� o m�s de refer�ncia passado). 
	 * Utilizado para recuperar a quantidade realizada de um indicador n�o acumul�vel em um exerc�cio, podendo ser o "�ltimo Valor" ou o "Maior Valor",
	 * de acordo com o que foi setado na configura��o da Meta/Indicador.
	 * 
	 * @param indicador
	 * @param exercicio
	 * @param anoRef
	 * @param mesRef
	 * @return A quantidade realizada do indicador
	 * @throws ECARException
	 */
	public double getQtdIndicadorGrupoRealizadaExercicioNaoAcumulavel (ItemEstrtIndResulIettr indicador, ExercicioExe exercicio, long anoRef, long mesRef) throws ECARException{
		try{
			
			StringBuilder s = new StringBuilder();
			
			s.append("select ARF.qtdRealizadaArf from AcompRealFisicoArf as ARF");
			s.append("  where ARF.itemEstruturaIett.codIett = :item");
			s.append("    and ARF.itemEstruturaIett.indAtivoIett = 'S'");
			s.append("    and ARF.itemEstrtIndResulIettr.codIettir = :indicador");
			s.append("    and ARF.anoArf = :anoArf");
			
			//Limita a consulta at� o m�s de refer�ncia informado.  
			if(Long.valueOf(exercicio.getDescricaoExe()).equals(anoRef)){
				s.append("    and ARF.mesArf <= :mesFinal");
			}
			
			s.append(" order by ARF.anoArf desc, ARF.mesArf desc");
			
			Query q = this.session.createQuery(s.toString());
			
			q.setLong("item", indicador.getItemEstruturaIett().getCodIett().longValue());
			q.setLong("indicador", indicador.getCodIettir().longValue());
			//q.setString("anoArf",exercicio.getDescricaoExe());
			q.setInteger("anoArf",Integer.parseInt(exercicio.getDescricaoExe()));
			if(Long.valueOf(exercicio.getDescricaoExe()).equals(anoRef)){
				q.setLong("mesFinal", mesRef);
			}
			
			List listaIndicadores = q.list();
			
			/*
			 * Utilizando m�todo que valida se � para retornar o maior valor, �ltimo valor, etc...
			 */
			Double valor = this.getSomaValoresArfs(indicador, listaIndicadores); 
				
			if(valor != null)
				return valor.doubleValue();
			else
				return 0;
		} catch(HibernateException e){
			this.logger.error(e);
			e.printStackTrace();
			throw new ECARException(e);
		}
		
	}
	
	/**
	 * Retorna se tem algum valor realizado informado para o exercicio
	 */
	public double getQtdeRealizadoExercicioByIettAndExe (ExercicioExe exercicio, ItemEstruturaIett iett, AcompReferenciaAref aref, String grupoIndicador){

		StringBuilder select = new StringBuilder();
		
		select.append("select sum(ARF.qtdRealizadaArf) from AcompRealFisicoArf as ARF ");
		select.append(" where ARF.itemEstrtIndResulIettr.itemEstruturaIett.codIett = :iett ");
		select.append(" and ARF.itemEstrtIndResulIettr.indAtivoIettr = 'S' ");
		if(!grupoIndicador.equals(""))
			select.append(" and ARF.itemEstrtIndResulIettr.sisAtributoSatb.descricaoSatb = :grupoIndicador ");
		select.append(" and (ARF.anoArf > :anoInicial or (ARF.anoArf = :anoInicial and ARF.mesArf >= :mesInicial)) ");
		select.append(" and (ARF.anoArf < :anoFinal or (ARF.anoArf = :anoFinal and ARF.mesArf <= :mesFinal)) ");
		
		long anoInicial = Data.getAno(exercicio.getDataInicialExe());
		long anoFinal = Data.getAno(exercicio.getDataFinalExe());
		long mesInicial = Data.getMes(exercicio.getDataInicialExe()) + 1;
		long mesFinal = Data.getMes(exercicio.getDataFinalExe()) + 1;

		/*
		 * Se aref != null:
		 * 	Se exercicio = exercicio do aref, somar at� mes/ano do aref.
		 * 	Se exercicio > exercicio do aref, n�o somar.
		 * 	Se exercicio < exercicio do aref, continua como est�.
		 */
		if(aref != null){
			if(exercicio.equals(aref.getExercicioExe())){
				//Mesmo exerc�cio
				mesInicial = Data.getMes(aref.getExercicioExe().getDataInicialExe()) + 1;
				anoInicial = Data.getAno(aref.getExercicioExe().getDataInicialExe());
				mesFinal = Long.parseLong(aref.getMesAref());
				anoFinal = Long.parseLong(aref.getAnoAref());
			}
			else if(exercicio.getDataInicialExe().after(aref.getExercicioExe().getDataFinalExe())){
				//exercicio come�a depois do exerc�cio do aref
				return 0; //FIXME: Verificar se o retorno � zero ou vazio.
			}
		}
		
		
		Query q = this.session.createQuery(select.toString());
		
		q.setLong("iett", iett.getCodIett().longValue());
		if(!grupoIndicador.equals(""))
			q.setString("grupoIndicador", grupoIndicador);
		q.setLong("anoInicial", anoInicial);
		q.setLong("mesInicial", mesInicial);
		q.setLong("anoFinal", anoFinal);
		q.setLong("mesFinal", mesFinal);
		
		q.setMaxResults(1);
		
		Double retorno = (Double) q.uniqueResult();
		
		if(retorno != null)
			return retorno.doubleValue();

		return 0;
	}
	
	/**
	 * Retorna uma lista de realizado de um indicador (ARF) entre um exerc�cio inicial e final.
	 * 
	 * @author aleixo
	 * @version 0.1 - 15/06/2010 
         * @param exercicioExeInicial
         * @param exercicioExeFinal 
         * @param iettr
         * @param aref
         * @return List
	 */
	public List ObtemListaRealizadoExercicioByIettrAndExe (ExercicioExe exercicio, ItemEstrtIndResulIettr indResul, AcompReferenciaAref aref) throws ECARException{
		try{
			List valores = this.getListaRealizadoExercicioByIettrAndExe(exercicio, exercicio, indResul, aref);
			return valores;
		} catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}
		
	}
	
	/**
	 * Retorna a lista de todos os indicadores ordenados por Tipo e Nome
	 * 
	 * @author Rafael de Morais 22/11/2012
	 * @param aref
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AcompRealFisicoArf> obtemIndicadoresByAref(AcompReferenciaAref aref) {
		String hql = "FROM AcompRealFisicoArf as arf " +
				"WHERE arf.mesArf = :mes " +
				"AND arf.anoArf = :ano " +
				"ORDER BY arf.itemEstrtIndResulIettr.sisAtributoSatb.descricaoSatb, arf.itemEstrtIndResulIettr.nomeIettir";
	
		Query q = this.session.createQuery(hql.toString());
		q.setLong("mes", Long.parseLong(aref.getMesAref()));
		q.setLong("ano", Long.parseLong(aref.getAnoAref()));
		return q.list();
	}
	
}