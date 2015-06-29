/*
 * Criado em 21/12/2004
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrutFisicoIettf;

/**
 * @author evandro
 *
 */
public class ItemEstrutFisicoDao extends Dao{
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public ItemEstrutFisicoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	
	/**
     * Cria um objeto itemEstrutFisico a partir de par�metros passados
     * no objeto request
     * 
     * @author n/c
     * @param request
     * @param itemEstrutFisico
     * @throws ECARException
     */
    public void setItemEstrutFisico(HttpServletRequest request, ItemEstrutFisicoIettf itemEstrutFisico) throws ECARException {
    	itemEstrutFisico.setItemEstrtIndResulIettr( (ItemEstrtIndResulIettr) this.buscar(ItemEstrtIndResulIettr.class, Long.valueOf(Pagina.getParamStr(request, "codIettir"))) );
    	//Mantis 0010128 - Qtd prevista n�o � mais informado por exerc�cio    	
    	//itemEstrutFisico.setExercicioExe( (ExercicioExe) this.buscar(ExercicioExe.class, Long.valueOf(Pagina.getParamStr(request, "codExe"))) );
    	//itemEstrutFisico.setMesIettf(Integer.valueOf(Pagina.getParamStr(request, "mesIettf")));
    	//itemEstrutFisico.setAnoIettf(Integer.valueOf(Pagina.getParamStr(request, "anoIettf")));
    	itemEstrutFisico.setQtdPrevistaIettf(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, "qtdPrevistaIettf"))));
    	itemEstrutFisico.setIndAtivoIettf(Pagina.getParamStr(request, "indAtivoIettf"));
    }

	/**
	 * Retorna a soma do previsto de um indicador (ARF), entre um exerc�cio incial e final.
	 * 
	 * @author 
	 * @version 0.1 - 08/07/2010
         * @param exercicioExeInicial
         * @param exercicioExeFinal
         * @param iettr
         * @param aref
	 * @return double
	 */
	public double getPrevistoExercicioByIettrAndExe (ExercicioExe exercicioExeInicial, ExercicioExe exercicioExeFinal, ItemEstrtIndResulIettr iettr, AcompReferenciaAref aref){

		StringBuilder select = new StringBuilder();
		
		select.append("select sum(qtdPrevistaIettf) from ItemEstrutFisicoIettf as IETTF");
		select.append(" where IETTF.itemEstrtIndResulIettr.codIettir = :iettir");
		select.append(" and (IETTF.anoIettf > :anoInicial or (IETTF.anoIettf = :anoInicial and IETTF.mesIettf >= :mesInicial))");
		select.append(" and (IETTF.anoIettf < :anoFinal or (IETTF.anoIettf = :anoFinal and IETTF.mesIettf <= :mesFinal))");
		
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
	 * Retorna a quantidade prevista de um indicador (IETTF), em um exerc�cio, considerando o acompanhamento (AREF).
	 * 
	 * @author 
	 * @version 0.1 - 08/07/2010
         * @param exercicio
         * @param indResul
         * @param aref
	 * @return double
	 * @throws ECARException 
	 */
	public double getQtdPrevistaExercicio (ExercicioExe exercicio, ItemEstrtIndResulIettr indResul, AcompReferenciaAref aref) throws ECARException{
        //Passo o mesmo exerc�cio, pois quero o previsto de um exerc�cio. Ent�o o exer�cio inicial � o mesmo que o final.
		
        double total = 0;
        double maior = 0;        
        
        String indAcumulavel = indResul.getIndAcumulavelIettr();
        String indValorFinal = indResul.getIndValorFinalIettr();
        
        if("S".equals(indAcumulavel)){
        	//para indicadores acumul�veis faz a soma no hql.
        	return this.getPrevistoExercicioByIettrAndExe(exercicio, exercicio, indResul, aref);
        }
        else{
        	List valoresPrevExerc = this.ObtemListaPrevistoExercicioByIettrAndExe(exercicio,indResul,aref);
			return this.getSomaValoresIettfs(indResul, valoresPrevExerc);       	
        	
        }
	}
	
	/**
	 * Retorna uma lista de previsto de um indicador (ARF) entre um exerc�cio inicial e final.
	 * 
	 * @author 
	 * @version 0.1 - 08/07/2010 
         * @param exercicioExeInicial
         * @param exercicioExeFinal 
         * @param iettr
         * @param aref
         * @return List
	 */
	public List ObtemListaPrevistoExercicioByIettrAndExe (ExercicioExe exercicio, ItemEstrtIndResulIettr indResul, AcompReferenciaAref aref) throws ECARException{
		try{
			List valores = this.getListaPrevistoExercicioByIettrAndExe(exercicio, exercicio, indResul, aref);
			return valores;
		} catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}
		
	}
	
	/**
	 * Retorna uma lista de previsto de um indicador (ARF) entre um exerc�cio inicial e final.
	 * 
	 * @author aleixo
	 * @version 0.1 - 08/07/2010 
         * @param exercicioExeInicial
         * @param exercicioExeFinal 
         * @param iettr
         * @param aref
         * @return List
	 */
	public List getListaPrevistoExercicioByIettrAndExe (ExercicioExe exercicioExeInicial, ExercicioExe exercicioExeFinal, ItemEstrtIndResulIettr iettr, AcompReferenciaAref aref){

		StringBuilder select = new StringBuilder();
		
		select.append("select IETTF.qtdPrevistaIettf from ItemEstrutFisicoIettf as IETTF");
		select.append(" where IETTF.itemEstrtIndResulIettr.codIettir = :iettir");
		select.append(" and (IETTF.anoIettf > :anoInicial or (IETTF.anoIettf = :anoInicial and IETTF.mesIettf >= :mesInicial))");
		select.append(" and (IETTF.anoIettf < :anoFinal or (IETTF.anoIettf = :anoFinal and IETTF.mesIettf <= :mesFinal))");
		select.append(" order by IETTF.anoIettf desc, IETTF.mesIettf desc");
		
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
	 * Retorna a um valor de uma lista de valores, dependendo do indicador.<br>
	 * Se o indicador � acumul�vel, retorna a soma dos valores da lista.<br>
	 * Se o indicador n�o � acumul�vel, e seu valor final est� informado como "Maior",
	 * retorna o maior valor da lista de valores. <br>
	 * Se o indicador n�o � acumul�vel, e seu valor final est� informado como "�ltimo",
	 * retorna o PRIMEIRO* valor da lista de valores. <br>
	 * <br>
	 * *PRIMEIRO: os valores devem estar ordenados do Iettf mais recente para o Iettf menos recente 
	 * (do �ltimo informado para o primeiro).
	 * 
	 * @author 
	 * @version 0.1 - 08/07/2010	 
     * @param itemEstrtIndResul
     * @param valores
	 * @return Double
	 */
	public Double getSomaValoresIettfs(ItemEstrtIndResulIettr itemEstrtIndResul, List<Double> valores){
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
	        		 * A lista de valores j� est� vindo ordenada pelo �ltimo Iettf, ent�o, � 
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
	
  
  	/* Mantis 0010128 - Qtd prevista n�o � mais informado por exerc�cio
    /*
     * Recebe o c�digo de itemIndResultado e um array contendo c�digos de
     * exercicio e exclui todos os registros que relacionam
     * 
     * @author n/c
     * @param codigosParaExcluir
     * @param codItemIndResultado
     * @throws ECARException
     */
    /*
    public void excluir(String[] codigosParaExcluir, Long codItemIndResultado) throws ECARException {
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        for (int i = 0; i < codigosParaExcluir.length; i++) {
	        	ItemEstrutFisicoIettf itemEstrutFisico = (ItemEstrutFisicoIettf) buscar(codItemIndResultado , Long.valueOf(codigosParaExcluir[i]));
	            session.delete(itemEstrutFisico);
				objetos.add(itemEstrutFisico);
	        }
			
			tx.commit();
	
			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("EXC");
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
    */
    
	/* Mantis 0010128 - Qtd prevista n�o � mais informado por exerc�cio
	 * Mudou a pk. n�o usa mais chave composta
	 * */
    
    /*
     * M�todo utilizado para setar os valores da PK da classe
     * 
     * @author n/c
     * @param itemEstrutFisico
     */
    /*
    public void setPK(ItemEstrutFisicoIettf itemEstrutFisico) {
    	ItemEstrutFisicoIettfPK chave = new ItemEstrutFisicoIettfPK();
        
        chave.setCodIettir(itemEstrutFisico.getItemEstrtIndResulIettr().getCodIettir());
        chave.setCodExe(itemEstrutFisico.getExercicioExe().getCodExe());
        
        itemEstrutFisico.setComp_id(chave);
    }
    */
  
  	/* Mantis 0010128 - Qtd prevista n�o � mais informado por exerc�cio
	 * Mudou a pk. n�o usa mais chave composta
	 * */
    /*
     * Grava uma rela��o entre itemIndResultado e Exercicio
     * 
     * @author n/c
     * @param itemEstrutFisico
     * @throws ECARException
     */
    /*
    public void salvar(ItemEstrutFisicoIettf itemEstrutFisico) throws ECARException {
        setPK(itemEstrutFisico);
        try {
            if (buscar(ItemEstrutFisicoIettf.class, itemEstrutFisico.getComp_id()) != null)
            	throw new ECARException("itemEstrutura.quantPrevista.inclusao.jaExiste");
        } catch (ECARException e) {
            if (e.getMessageKey().equalsIgnoreCase("erro.objectNotFound")) {
                super.salvar(itemEstrutFisico);
            } else
            	this.logger.error(e);
                // joga para frente a inclusao.jaExiste 
                throw e;
        }
    }
    */    
    /*
     * Retorna um objeto ItemEstrutFisico a partir dos c�digos
     * 
     * @author n/c
     * @param codIettir
     * @param codExe
     * @return
     * @throws ECARException
     */
    /*
    public ItemEstrutFisicoIettf buscar(Long codIettir, Long codExe) throws ECARException {
    	ItemEstrutFisicoIettfPK chave = new ItemEstrutFisicoIettfPK();
    
        chave.setCodIettir(codIettir);
        chave.setCodExe(codExe);
        
        return (ItemEstrutFisicoIettf) super.buscar(ItemEstrutFisicoIettf.class, chave);
    }
    */
	
    /**
    *
    * @param codIettir
    * @return
    */
   public int deleteItemEstrutFisicoDeItem( Long codIettir, Long ano )
   {
       try
       {
           StringBuilder sb = new StringBuilder(  );
           sb.append( "delete from ItemEstrutFisicoIettf ie " );
           sb.append( " where " );
           sb.append( " ie.itemEstrtIndResulIettr.codIettir = :codIettir " );
           sb.append( " and ano_iettf = :ano" );           
                           
           Query query = session.createQuery( sb.toString(  ) );

           query.setLong( "codIettir",
                          codIettir.longValue(  ) );
           
           query.setLong( "ano",
                   ano.longValue(  ) );
           

           int rowcount = query.executeUpdate(  );

           return rowcount;
       } catch ( HibernateException e )
       {
           e.printStackTrace( System.out );
           this.logger.error( e );

           return 0;
       }
   }
	
   /**
    * Devolve a quantidade prevista total em ItemEstrutFisico para um indResultado
    * 
    * @param acompReferenciaItem
    * @param itemEstrtIndResul
    * @param radAcumulados
    * @return
    * @throws ECARException
    * @throws HibernateException
    */

   public double getQuantidadePrevista(AcompReferenciaItemAri acompReferenciaItem,
   					ItemEstrtIndResulIettr itemEstrtIndResul, String radAcumulados) throws ECARException, HibernateException {
       StringBuilder query = new StringBuilder("select sum(ie.qtdPrevistaIettf) from ItemEstrutFisicoIettf ie");
		query.append(" where ie.itemEstrtIndResulIettr.codIettir = :codIettir");
		
		if("P".equalsIgnoreCase(radAcumulados)){
			query.append(" and (ie.anoIettf < :anoIettf")
				 .append(" or (ie.mesIettf <= :mesIettf")
				 .append(" and ie.anoIettf = :anoIettf))");
		}

		Query q = session.createQuery(query.toString());
		
		q.setLong("codIettir", itemEstrtIndResul.getCodIettir().longValue());

		if("P".equalsIgnoreCase(radAcumulados)){
			q.setLong("anoIettf", Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getAnoAref()).longValue());
			q.setLong("mesIettf", Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getMesAref()).longValue());
		}
		q.setMaxResults(1);
		
		Double total = (Double) q.uniqueResult();
		if(total != null)
			return total.doubleValue();
		else
			return 0;
   }

   /**
    * Devolve a quantidade prevista total em ItemEstrutFisico para um indResultado n�o acumul�vel
    * 
    * @param acompReferenciaItem
    * @param itemEstrtIndResul
    * @param radAcumulados
    * @return
    * @throws ECARException
    * @throws HibernateException
    */

   public double getQuantidadePrevistaNaoAcumulavel(AcompReferenciaItemAri acompReferenciaItem,
   					ItemEstrtIndResulIettr itemEstrtIndResul, String radAcumulados) throws ECARException, HibernateException {
	   //N�o se aplica
	   if("N".equals(itemEstrtIndResul.getIndValorFinalIettr())) { 
   		return 0;
   		}
	   
	   StringBuilder query = new StringBuilder("select");
	   //Maior
	   if("M".equals(itemEstrtIndResul.getIndValorFinalIettr())) { 
	       query.append(" max(ie.qtdPrevistaIettf)");   		
	   }else{
		   query.append(" ie.qtdPrevistaIettf");
	   } 
	   
       query.append(" from ItemEstrutFisicoIettf ie where ie.itemEstrtIndResulIettr.codIettir = :codIettir");
		
		if("P".equalsIgnoreCase(radAcumulados)){
			query.append(" and (ie.anoIettf < :anoIettf")
				 .append(" or (ie.mesIettf <= :mesIettf")
				 .append(" and ie.anoIettf = :anoIettf))");
		}
	   
		if("M".equals(itemEstrtIndResul.getIndValorFinalIettr())) { 
			query.append(" GROUP BY ie.itemEstrtIndResulIettr.codIettir");   		
		}else{
			query.append(" ORDER BY ie.anoIettf DESC, ie.mesIettf DESC");
		}
		
		
		Query q = session.createQuery(query.toString());
		
		q.setLong("codIettir", itemEstrtIndResul.getCodIettir().longValue());

		if("P".equalsIgnoreCase(radAcumulados)){
			q.setLong("anoIettf", Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getAnoAref()).longValue());
			q.setLong("mesIettf", Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getMesAref()).longValue());
		}
		q.setMaxResults(1);
		
		Double total = (Double) q.uniqueResult();
		if(total != null)
			return total.doubleValue();
		else
			return 0;
   }
   
	/**
	 * Verifica exist�ncia de Estrutura Fisica para determinado m�s e ano
	 * @param itemEstrutura
	 * @param mes
	 * @param ano 
	 * @return
	 * @throws ECARException
	 */
	public ItemEstrutFisicoIettf getEstruturaFisicaPorMesAno(Long codIettir, Long mes, Long ano) throws ECARException {
		try {
			Query q = this.getSession().createQuery(
					"from ItemEstrutFisicoIettf ieFisico where ieFisico.itemEstrtIndResulIettr.codIettir = ? "
							+ " and ieFisico.mesIettf = ?" 
			                + " and ieFisico.anoIettf = ?");
			q.setLong(0, codIettir);
			q.setLong(1, mes);
			q.setLong(2, ano);
			q.setMaxResults(1);
			return (ItemEstrutFisicoIettf) q.uniqueResult();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}
	
	/**
	 * Retorna a lista com os valores previstos de um indicador.
	 * Considera a data de inicio e t�rmino do item.
	 * @param iettr	 
	 * @return
	 * @throws ECARException 
	 * @throws ECARException 
	 */
	public List<ItemEstrutFisicoIettf> getListaPrevistosIettr(ItemEstrtIndResulIettr iettr) throws ECARException {

		StringBuilder select = new StringBuilder();
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(this.request); 
		
		select.append("select IETTF from ItemEstrutFisicoIettf as IETTF");
		select.append(" where IETTF.itemEstrtIndResulIettr.codIettir = :iettir");
		select.append(" and (IETTF.anoIettf > :anoInicial or (IETTF.anoIettf = :anoInicial and IETTF.mesIettf >= :mesInicial))");
		select.append(" and (IETTF.anoIettf < :anoFinal or (IETTF.anoIettf = :anoFinal and IETTF.mesIettf <= :mesFinal))");
		select.append(" order by IETTF.anoIettf asc, IETTF.mesIettf asc");
		
		Date dataInicial = null; 
		dataInicial = itemEstruturaDao.ObtemDataInicialItemEstrutura(iettr.getItemEstruturaIett());
		
		Date dataTermino = null;		
		dataTermino = itemEstruturaDao.ObtemDataTerminoItemEstrutura(iettr.getItemEstruturaIett());
				
		long anoInicial = Data.getAno(dataInicial);
		long anoFinal = Data.getAno(dataTermino);
		long mesInicial = Data.getMes(dataInicial) + 1;
		long mesFinal = Data.getMes(dataTermino) + 1;
		try {
			Query q = this.session.createQuery(select.toString());
		
			q.setLong("iettir", iettr.getCodIettir().longValue());
			q.setLong("anoInicial", anoInicial);
			q.setLong("mesInicial", mesInicial);
			q.setLong("anoFinal", anoFinal);
			q.setLong("mesFinal", mesFinal);
		
		    return new ArrayList<ItemEstrutFisicoIettf>(q.list());
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
		    
	} 
	
	/**
	 * Retorna a quantidade prevista parcial de um indicador (IETTF), em um exercício, considerando o acompanhamento (AREF).
	 * 
     * @param exercicio
     * @param indResul
     * @param mesRef
     * @param aref
	 * @return double
	 * @throws ECARException 
	 */
	public double getQtdPrevistaParcialExercicio (ExercicioExe exercicio, long mesRef, ItemEstrtIndResulIettr indResul, AcompReferenciaAref aref) throws ECARException{
        double total = 0;
        double maior = 0;        
        
        String indAcumulavel = indResul.getIndAcumulavelIettr();
        String indValorFinal = indResul.getIndValorFinalIettr();
        
        if("S".equals(indAcumulavel)){
        	//para indicadores acumuláveis faz a soma no hql.
        	return this.getPrevistoExercicioByIettrAndExe(exercicio, mesRef, indResul, aref);
        }
        else{
        	List valoresPrevExerc = this.ObtemListaPrevistoExercicioByIettrAndExe(exercicio, mesRef,indResul,aref);
			return this.getSomaValoresIettfs(indResul, valoresPrevExerc);       	
        	
        }
	}
	
	/**
	 * Retorna a soma do previsto de um indicador (ARF), para um exercício até o mês de referência.
	 * 
	 * @author 
	 * @version 0.1 - 08/07/2010
         * @param exercicioExeInicial
         * @param exercicioExeFinal
         * @param iettr
         * @param aref
	 * @return double
	 */
	public double getPrevistoExercicioByIettrAndExe (ExercicioExe exercicio, long mesRef, ItemEstrtIndResulIettr iettr, AcompReferenciaAref aref){

		StringBuilder select = new StringBuilder();
		
		select.append("select sum(qtdPrevistaIettf) from ItemEstrutFisicoIettf as IETTF");
		select.append(" where IETTF.itemEstrtIndResulIettr.codIettir = :iettir");
		select.append(" and (IETTF.anoIettf > :anoInicial or (IETTF.anoIettf = :anoInicial and IETTF.mesIettf >= :mesInicial))");
		select.append(" and (IETTF.anoIettf < :anoFinal or (IETTF.anoIettf = :anoFinal and IETTF.mesIettf <= :mesFinal))");
		
		long anoInicial = Data.getAno(exercicio.getDataInicialExe());
		long anoFinal = Data.getAno(exercicio.getDataFinalExe());
		long mesInicial = Data.getMes(exercicio.getDataInicialExe()) + 1;
		long mesFinal = mesRef;

		/*
		 * Se aref != null:
		 * 	Se exercicio = exercicio do aref, somar até mes/ano do aref.
		 * 	Se exercicio > exercicio do aref, não somar.
		 * 	Se exercicio < exercicio do aref, continua como está.
		 */
		if(aref != null){
			if(exercicio.equals(aref.getExercicioExe())){
				//Mesmo exercício
				mesInicial = Data.getMes(aref.getExercicioExe().getDataInicialExe()) + 1;
				anoInicial = Data.getAno(aref.getExercicioExe().getDataInicialExe());
				mesFinal = Long.parseLong(aref.getMesAref());
				anoFinal = Long.parseLong(aref.getAnoAref());
			}
			else if(exercicio.getDataInicialExe().after(aref.getExercicioExe().getDataFinalExe())){
				//exercicio começa depois do exercício do aref
				return 0; //FIXME: Verificar se o retorno é zero ou vazio.
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
	 * Retorna uma lista de previsto de um indicador (ARF) entre um exercício inicial e final.
	 * 
	 * @author 
	 * @version 0.1 - 08/07/2010 
         * @param exercicioExeInicial
         * @param exercicioExeFinal 
         * @param iettr
         * @param aref
         * @return List
	 */
	public List ObtemListaPrevistoExercicioByIettrAndExe (ExercicioExe exercicio, long mesRef, ItemEstrtIndResulIettr indResul, AcompReferenciaAref aref) throws ECARException{
		try{
			List valores = this.getListaPrevistoExercicioByIettrAndExe(exercicio, mesRef, indResul, aref);
			return valores;
		} catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}
		
	}
	
	/**
	 * Retorna uma lista de previsto de um indicador (ARF) entre um exercício inicial e final.
	 * 
	 * @author aleixo
	 * @version 0.1 - 08/07/2010 
         * @param exercicioExeInicial
         * @param exercicioExeFinal 
         * @param iettr
         * @param aref
         * @return List
	 */
	public List getListaPrevistoExercicioByIettrAndExe (ExercicioExe exercicio, long mesRef, ItemEstrtIndResulIettr iettr, AcompReferenciaAref aref){

		StringBuilder select = new StringBuilder();
		
		select.append("select IETTF.qtdPrevistaIettf from ItemEstrutFisicoIettf as IETTF");
		select.append(" where IETTF.itemEstrtIndResulIettr.codIettir = :iettir");
		select.append(" and (IETTF.anoIettf > :anoInicial or (IETTF.anoIettf = :anoInicial and IETTF.mesIettf >= :mesInicial))");
		select.append(" and (IETTF.anoIettf < :anoFinal or (IETTF.anoIettf = :anoFinal and IETTF.mesIettf <= :mesFinal))");
		select.append(" order by IETTF.anoIettf desc, IETTF.mesIettf desc");
		
		long anoInicial = Data.getAno(exercicio.getDataInicialExe());
		long anoFinal = Data.getAno(exercicio.getDataFinalExe());
		long mesInicial = Data.getMes(exercicio.getDataInicialExe()) + 1;
		long mesFinal = mesRef;

		
		/*
		 * Se aref != null:
		 * 	Se exercicio = exercicio do aref, somar até mes/ano do aref.
		 * 	Se exercicio > exercicio do aref, não somar.
		 * 	Se exercicio < exercicio do aref, continua como está.
		 */
		if(aref != null){
			if(exercicio.equals(aref.getExercicioExe())){
				//Mesmo exercício
				mesInicial = Data.getMes(aref.getExercicioExe().getDataInicialExe()) + 1;
				anoInicial = Data.getAno(aref.getExercicioExe().getDataInicialExe());
				mesFinal = Long.parseLong(aref.getMesAref());
				anoFinal = Long.parseLong(aref.getAnoAref());
			}
			else if(exercicio.getDataInicialExe().after(aref.getExercicioExe().getDataFinalExe())){
				//exercicio começa depois do exercício do aref
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
}
