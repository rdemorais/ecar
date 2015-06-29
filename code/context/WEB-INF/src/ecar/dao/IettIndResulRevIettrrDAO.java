package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.pojo.ExercicioExe;
import ecar.pojo.IettIndResulRevIettrr;
import ecar.pojo.ItemEstFisicoRevIettfr;
import ecar.pojo.ItemEstFisicoRevIettfrPK;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstruturarevisaoIettrev;


/**
 *
 * @author 70744416353
 */
public class IettIndResulRevIettrrDAO extends Dao{
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public IettIndResulRevIettrrDAO(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	
	/**
     * Cria um objeto itemEstrtIndResul a partir de parâmetros passados
     * no objeto request
     * 
     * @param request
     * @param itemEstrtIndResul
     * @throws ECARException
     */
    public void setItemEstrtIndResul(HttpServletRequest request, IettIndResulRevIettrr itemEstrtIndResul) throws ECARException {
    	itemEstrtIndResul.setItemEstruturarevisaoIettrev( (ItemEstruturarevisaoIettrev) this.buscar(ItemEstruturarevisaoIettrev.class, Integer.valueOf(Pagina.getParamStr(request, "codIettrev"))) );
    }
    
    
    /**
     * Recebe um array contendo códigos de IndResultado e exclui todos 
     * os registros
     * 
     * @param codigosParaExcluir
     * @throws ECARException
     */
    public void excluir(String[] codigosParaExcluir) throws ECARException {
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        for (int i = 0; i < codigosParaExcluir.length; i++) {
	        	IettIndResulRevIettrr itemEstrtIndResul = (IettIndResulRevIettrr) buscar(IettIndResulRevIettrr.class, Integer.valueOf(codigosParaExcluir[i]));
	            session.delete(itemEstrtIndResul);
				objetos.add(itemEstrtIndResul);
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
    
    
    /**
     * Soma o valor de todos as quantidades de um Indicador de Resultado
     * onde IndAtivo = 'S'
     * 
     * @param itemEstrtIndResul
     * @return
     * @throws ECARException
     */
    public double getSomaQuantidades(IettIndResulRevIettrr itemEstrtIndResul) throws ECARException {
        double total = 0;
        if (itemEstrtIndResul.getItemEstFisicoRevIettfrs() != null) {
            Iterator it = itemEstrtIndResul.getItemEstFisicoRevIettfrs().iterator();
            while (it.hasNext()) {
            	ItemEstFisicoRevIettfr itemEstrutFisico = (ItemEstFisicoRevIettfr) it.next();
            	if("S".equalsIgnoreCase(itemEstrutFisico.getIndAtivoIettfr()))
            		total += itemEstrutFisico.getQtdPrevistaIettfr().doubleValue();
            }
        }
        return total;
    }
    
    /**
     * Se Indicador de Resultado é Acumulável soma o valor de todos as quantidades 
     * onde IndAtivo = 'S', o retorno é em string;
     * Senão retorna "Não se aplica"
     * 
     * @param itemEstrtIndResul
     * @return
     * @throws ECARException
     */
    public String getSomaQuantidadePrevista(IettIndResulRevIettrr itemEstrtIndResul) throws ECARException {
        String retorno = "";
        double total = 0;
   
    	ItemEstrtIndResulIettr indicador = itemEstrtIndResul.getItemEstrtIndResulIettr();
    	
    	if(indicador != null){
            if ("S".equals(indicador.getIndAcumulavelIettr())){
            	List exercicios = new ArrayList(itemEstrtIndResul.getItemEstFisicoRevIettfrs());
            	
                Iterator it = exercicios.iterator();                
                while(it.hasNext()){
                    ItemEstFisicoRevIettfr exercicio = (ItemEstFisicoRevIettfr) it.next();
                    if("S".equals(exercicio.getIndAtivoIettfr()))
                    	total += exercicio.getQtdPrevistaIettfr().doubleValue();
                }
                
                retorno = Util.formataNumeroSemDecimal(total);

            }else{
            	
            	/*
            	 * Anotação ref. Mantis 5016:
            	 * - Maior: obter o maior valor de ItemEstrutFisicoIettf
            	 * - Último: obter valor do último exercício informado de ItemEstrutFisicoIettf
            	 * - Não se aplica: soma total ItemEstrutFisicoIettf
            	 */
            	
            	if("M".equals(indicador.getIndValorFinalIettr())){ //Maior

                    List exercicios = new ArrayList(itemEstrtIndResul.getItemEstFisicoRevIettfrs());
                	
                    Iterator it = exercicios.iterator();                
                    double maior = 0;
                    while(it.hasNext()){
                        ItemEstFisicoRevIettfr exercicio = (ItemEstFisicoRevIettfr) it.next();
                        if("S".equals(exercicio.getIndAtivoIettfr())){
                        	if(exercicio.getQtdPrevistaIettfr().doubleValue() > maior){
                        		maior = exercicio.getQtdPrevistaIettfr().doubleValue();
                        	}
                        	total = maior;
                        }
                    }
                    
                    retorno = Util.formataNumeroSemDecimal(total);
            	}
            	else if("U".equals(indicador.getIndValorFinalIettr())){ //Ultimo
    	            double ultimo = 0;
            		ExercicioExe ultimoExe = getMaiorExercicioIndicador(itemEstrtIndResul);

                    List exercicios = new ArrayList(itemEstrtIndResul.getItemEstFisicoRevIettfrs());
                	
                    Iterator it = exercicios.iterator();                
                    while(it.hasNext()){
                        ItemEstFisicoRevIettfr exercicio = (ItemEstFisicoRevIettfr) it.next();
                        if("S".equals(exercicio.getIndAtivoIettfr())){
                        	if(exercicio.getExercicioExe().getCodExe().equals(ultimoExe.getCodExe())){
                        		ultimo = exercicio.getQtdPrevistaIettfr().doubleValue();
                        		break;
                        	}
                        }
                    }
        	        retorno = Util.formataNumeroSemDecimal(ultimo);
            	}
            	else if("N".equals(indicador.getIndValorFinalIettr())){ //Não se aplica
            		retorno = "";
            	}
            }
    		
    	}
        
        return retorno;
    }
    
    
    /**
     * Método que retorna a Quantidade Prevista de um Indicador de Resultado
     * em um Exercício.
     * 
     * @param indResul
     * @param exercicio
     * @return
     * @throws NumberFormatException
     */
    public double getQtdPrevistoExercicio(IettIndResulRevIettrr indResul, ExercicioExe exercicio){
    	double quant = 0;
    	
    	try{
	      	ItemEstFisicoRevIettfr qtdPrevista = new ItemEstFisicoRevIettfrDAO(request).buscar(Long.valueOf(indResul.getCodIettirr().longValue()), exercicio.getCodExe());
	      	if ("S".equalsIgnoreCase(qtdPrevista.getIndAtivoIettfr()))
	      		quant = qtdPrevista.getQtdPrevistaIettfr().doubleValue();
    	} catch (ECARException e) {
    		this.logger.error(e);
    		/* não realiza nada e devolve quant = 0 */
    	}
    	
    	return quant;
    }
	
	/**
	 * Devolve o maior exercício em que foi cadastrada uma quantidade prevista para um indicador de resultado
	 * @param indResul
	 * @return
	 * @throws ECARException
	 */
	public ExercicioExe getMaiorExercicioIndicador(IettIndResulRevIettrr indResul) throws ECARException{
		try{
			Query q = this.getSession().createQuery(
					"select ieFisico.exercicioExe from ItemEstFisicoRevIettfr ieFisico" +
					" where ieFisico.iettIndResulRevIettrr.codIettirr = ? " +
					" order by ieFisico.exercicioExe.dataFinalExe desc");
			q.setLong(0, indResul.getCodIettirr().longValue());
			q.setMaxResults(1);
			return (ExercicioExe) q.uniqueResult();
		} catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}		
	}
    
	/**
	 * 
	 * @author n/c
	 * @param indicativoResultado
	 * @return List
	 * @throws ECARException
	 */
    public List getExerciosCadastroPermitidos(IettIndResulRevIettrr indicativoResultado) throws ECARException{                
        List exercicios = new ExercicioDao(request).listar(ExercicioExe.class, new String[] {"descricaoExe", Dao.ORDEM_ASC});
        Set quantidades = indicativoResultado.getItemEstFisicoRevIettfrs();
        Iterator it = quantidades.iterator();
        while(it.hasNext()){
        	ItemEstFisicoRevIettfr ieFisico = (ItemEstFisicoRevIettfr) it.next();
            ExercicioExe exercicioCadastrado = ieFisico.getExercicioExe();
            exercicios.remove(exercicioCadastrado);
        }
        return exercicios;
    }
    
	/**
	 * retorna lista variável de quantidade prevista preenchidas de acordo com os
	 * exercícios
	 * 
	 * @author n/c
	 * @param request
	 * @return List
	 * @throws ECARException
	 */
    public List getListaQuantidadePrevista (HttpServletRequest request) throws ECARException{
    	ExercicioDao exercicioDao = new ExercicioDao(request);
    	List listaExercicio = exercicioDao.getExerciciosValidos(Long.valueOf(Pagina.getParamStr(request, "codIett")));
		Iterator itExercicio = listaExercicio.iterator();
		
		List listaQtd = new ArrayList();
		
		while (itExercicio.hasNext()) {
			ExercicioExe exercicio = (ExercicioExe) itExercicio.next();
			
			if (!"".equals(Pagina.getParamStr(request, "qtdPrevistaIettf" + exercicio.getCodExe()))) {
				ItemEstFisicoRevIettfr itemEstrutFisico = new ItemEstFisicoRevIettfr();
				ItemEstFisicoRevIettfrPK chave = new ItemEstFisicoRevIettfrPK();
				
				chave.setCodExe(Integer.valueOf(exercicio.getCodExe().intValue()));
				itemEstrutFisico.setComp_id(chave);
				itemEstrutFisico.setQtdPrevistaIettfr(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, "qtdPrevistaIettf" + exercicio.getCodExe()))));
				
				itemEstrutFisico.setIndAtivoIettfr("S");
				itemEstrutFisico.setDataInclusaoIettfr(Data.getDataAtual());
				
				listaQtd.add(itemEstrutFisico);
			}
		}
		
		return listaQtd;
    }
    
    /**
     * 
     *  @author n/c
     * @param itemEstrtIndResul
     * @param listaQtd
     * @throws ECARException
     */
    public void salvar (IettIndResulRevIettrr itemEstrtIndResul, List listaQtd) throws ECARException {
    	inicializarLogBean();
		Transaction tx = null;
		try {
			ArrayList objetosInseridos = new ArrayList();
			tx = session.beginTransaction();
			
			//salva o pai
			session.save(itemEstrtIndResul);
			objetosInseridos.add(itemEstrtIndResul);
			
			Iterator itQtd = listaQtd.iterator();
			while (itQtd.hasNext()) {
				ItemEstFisicoRevIettfr itemEstrutFisico = (ItemEstFisicoRevIettfr) itQtd.next();
				itemEstrutFisico.getComp_id().setCodIettirr(itemEstrtIndResul.getCodIettirr());
				
				session.save(itemEstrutFisico);
				objetosInseridos.add(itemEstrutFisico);
			}
			
			tx.commit();
			
			if(logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("INC");
				Iterator it2 = objetosInseridos.iterator();

				while(it2.hasNext()) {
					logBean.setObj(it2.next());
					loggerAuditoria.info(logBean.toString());
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
     * 
     *  @author n/c
     * @param itemEstrtIndResul
     * @param listaQtd
     * @throws ECARException
     */
    public void alterar (IettIndResulRevIettrr itemEstrtIndResul, List listaQtd) throws ECARException {
    	inicializarLogBean();
		Transaction tx = null;
		try {
			ArrayList objetosInseridos = new ArrayList();
			ArrayList objetosExcluidos = new ArrayList();
			tx = session.beginTransaction();
			
			//excluir todas as quantidades previstas
			List listaAux = new ArrayList(); 
			listaAux.addAll(itemEstrtIndResul.getItemEstFisicoRevIettfrs());

			Iterator itAux = listaAux.iterator();
			while (itAux.hasNext()) {
				ItemEstFisicoRevIettfr itemEstrutFisico = (ItemEstFisicoRevIettfr) itAux.next();
				objetosExcluidos.add(itemEstrutFisico);
	            session.delete(itemEstrutFisico);
	        }
			
			if(logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("EXC");
				Iterator it = objetosExcluidos.iterator();

				while(it.hasNext()) {
					logBean.setObj(it.next());
					loggerAuditoria.info(logBean.toString());
				}
			}
			
			//salva o pai
			session.update(itemEstrtIndResul);
			
			if(logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setObj(itemEstrtIndResul);
				logBean.setOperacao("ALT");
				loggerAuditoria.info(logBean.toString());
			}
			
			Iterator itQtd = listaQtd.iterator();
			while (itQtd.hasNext()) {
				ItemEstFisicoRevIettfr itemEstrutFisico = (ItemEstFisicoRevIettfr) itQtd.next();
				itemEstrutFisico.getComp_id().setCodIettirr(itemEstrtIndResul.getCodIettirr());
				
				session.save(itemEstrutFisico);
				objetosInseridos.add(itemEstrutFisico);
			}
			
			tx.commit();
			
			if(logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("INC");
				Iterator it2 = objetosInseridos.iterator();

				while(it2.hasNext()) {
					logBean.setObj(it2.next());
					loggerAuditoria.info(logBean.toString());
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
	
}
