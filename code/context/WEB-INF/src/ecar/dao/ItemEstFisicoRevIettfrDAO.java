package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
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


/**
 * Classe de manipulação de objetos da classe ItemEstFisicoRevIettfr.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Fri Apr 28 17:12:23 BRT 2006
 *
 */
public class ItemEstFisicoRevIettfrDAO extends Dao{
	
    /**
     *
     * @param request
     */
    public ItemEstFisicoRevIettfrDAO(HttpServletRequest request) {
		super();
		this.request = request;
	}

	/**
     * Cria um objeto itemEstrutFisico a partir de parâmetros passados
     * no objeto request
     * 
     * @param request
     * @param itemEstrutFisico
     * @throws ECARException
     */
    public void setItemEstFisicoRev(HttpServletRequest request, ItemEstFisicoRevIettfr itemEstrutFisico) throws ECARException {
    	itemEstrutFisico.setIettIndResulRevIettrr((IettIndResulRevIettrr) this.buscar(IettIndResulRevIettrr.class, Long.valueOf(Pagina.getParamStr(request, "codIettirr"))) );
    	itemEstrutFisico.setExercicioExe( (ExercicioExe) this.buscar(ExercicioExe.class, Long.valueOf(Pagina.getParamStr(request, "codExe"))) );
    	itemEstrutFisico.setQtdPrevistaIettfr(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, "qtdPrevistaIettfr"))));
    	itemEstrutFisico.setIndAtivoIettfr(Pagina.getParamStr(request, "indAtivoIettfr"));

    }
        
    /**
     * Recebe o código de itemIndResultado e um array contendo códigos de
     * exercicio e exclui todos os registros que relacionam
     * 
     * @param codigosParaExcluir
     * @param codItemIndResultado
     * @throws ECARException
     */
    public void excluir(String[] codigosParaExcluir, Long codItemIndResultado) throws ECARException {
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        for (int i = 0; i < codigosParaExcluir.length; i++) {
	        	ItemEstFisicoRevIettfr itemEstrutFisico = (ItemEstFisicoRevIettfr) buscar(codItemIndResultado , Long.valueOf(codigosParaExcluir[i]));
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
	    
    /**
     * Método utilizado para setar os valores da PK da classe
     * 
     * @param itemEstrutFisico
     */
    public void setPK(ItemEstFisicoRevIettfr itemEstrutFisico) {
    	ItemEstFisicoRevIettfrPK chave = new ItemEstFisicoRevIettfrPK();
        
        chave.setCodIettirr(itemEstrutFisico.getIettIndResulRevIettrr().getCodIettirr());
        chave.setCodExe(Integer.valueOf(itemEstrutFisico.getExercicioExe().getCodExe().intValue()));
        
        itemEstrutFisico.setComp_id(chave);
    }
    
    

    /**
     * Grava uma relação entre itemIndResultado e Exercicio
     * 
     * @param itemEstrutFisico
     * @throws ECARException
     */
    public void salvar(ItemEstFisicoRevIettfr itemEstrutFisico) throws ECARException {
        setPK(itemEstrutFisico);
        try {
            if (buscar(ItemEstFisicoRevIettfr.class, itemEstrutFisico.getComp_id()) != null)
            	throw new ECARException("itemEstrutura.quantPrevista.inclusao.jaExiste");
        } catch (ECARException e) {
        	this.logger.error(e);
            if (e.getMessageKey().equalsIgnoreCase("erro.objectNotFound")) {
                super.salvar(itemEstrutFisico);
            } else
                /* joga para frente a inclusao.jaExiste */
                throw e;
        }
    }
        
    /**
     * Retorna um objeto ItemEstrutFisico a partir dos códigos
     * 
     * @param codIettirr 
     * @param codExe
     * @return
     * @throws ECARException
     */
    public ItemEstFisicoRevIettfr buscar(Long codIettirr, Long codExe) throws ECARException {
    	ItemEstFisicoRevIettfrPK chave = new ItemEstFisicoRevIettfrPK();
    
        chave.setCodIettirr(Integer.valueOf(codIettirr.intValue()));
        chave.setCodExe(Integer.valueOf(codExe.intValue()));
        
        return (ItemEstFisicoRevIettfr) super.buscar(ItemEstFisicoRevIettfr.class, chave);
    }
    
}
