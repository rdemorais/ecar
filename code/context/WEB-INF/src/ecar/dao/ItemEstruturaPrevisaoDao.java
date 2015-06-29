/*
 * Criado em 21/12/2004
 *
 */
package ecar.dao;

import java.math.BigDecimal;
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
import ecar.pojo.EfIettFonteTotEfieft;
import ecar.pojo.EfItemEstContaEfiec;
import ecar.pojo.EfItemEstPrevisaoEfiep;
import ecar.pojo.EfItemEstPrevisaoEfiepPK;
import ecar.pojo.ExercicioExe;
import ecar.pojo.FonteRecursoFonr;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturaIettPPA;
import ecar.pojo.ItemEstruturaSisAtributoIettSatb;
import ecar.pojo.RecursoRec;
import ecar.util.Dominios;

/**
 * @author felipev
 *  
 */
public class ItemEstruturaPrevisaoDao extends Dao {

    /**
     *
     * @param request
     */
    public ItemEstruturaPrevisaoDao(HttpServletRequest request) {
		super();
		this.request = request;
    }

    /**
     * Retorna um objeto EfItemEstPrevisaoEfiep a partir do código do Item da
     * Estrutura, do código do exercício e do código do Recurso e do Código da
     * Fonte de Recurso
     * 
     * @param codItemEstrutura
     * @param codFonteRecurso
     * @param codRecurso
     * @param codExercicio
     * @return
     * @throws ECARException
     */
    public EfItemEstPrevisaoEfiep buscar(Long codItemEstrutura,
            Long codFonteRecurso, Long codRecurso, Long codExercicio)
            throws ECARException {
    	
    	/*
    	EfItemEstPrevisaoEfiep efItemEstPrevisaoEfiep = null;
        EfItemEstPrevisaoEfiepPK comp_id = new EfItemEstPrevisaoEfiepPK();
        comp_id.setCodFonr(codFonteRecurso);
        comp_id.setCodExe(codExercicio);
        comp_id.setCodIett(codItemEstrutura);
        comp_id.setCodRec(codRecurso);
        
        try
        {
        
        	efItemEstPrevisaoEfiep = (EfItemEstPrevisaoEfiep)super.buscar(EfItemEstPrevisaoEfiep.class, comp_id);
        
        }catch (org.hibernate.ObjectNotFoundException e) {
        	efItemEstPrevisaoEfiep = new EfItemEstPrevisaoEfiep();
        }
        */
    	
    	try{
    		StringBuilder select = new StringBuilder();
    		select.append("select efiep from EfItemEstPrevisaoEfiep efiep");
    		select.append(" where efiep.itemEstruturaIett.codIett = :item and efiep.itemEstruturaIett.indAtivoIett = 'S'");
    		select.append(" and efiep.exercicioExe.codExe = :exe");
    		select.append(" and efiep.fonteRecursoFonr.codFonr = :fonr");
    		select.append(" and efiep.recursoRec.codRec = :rec");
    		
    		Query q = this.session.createQuery(select.toString());
    		
    		q.setLong("item", codItemEstrutura.longValue());
    		q.setLong("exe", codExercicio.longValue());
    		q.setLong("fonr", codFonteRecurso.longValue());
    		q.setLong("rec", codRecurso.longValue());
    		
    		q.setMaxResults(1);
    		
    		Object o = q.uniqueResult();
    		
    		return (o != null) ? (EfItemEstPrevisaoEfiep) o : new EfItemEstPrevisaoEfiep();
    	}
    	catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
    }
    
    /**
     * Retorna um objeto EfItemEstPrevisaoEfiep a partir do código do Item da
     * Estrutura, do código do exercício e do Código da Fonte de Recurso
     * 
     * @param codItemEstrutura
     * @param codFonteRecurso
     * @param codExercicio
     * @return
     * @throws ECARException
     * @throws HibernateException 
     */
    public EfItemEstPrevisaoEfiep buscar(Long codItemEstrutura,
            	Long codFonteRecurso, Long codExercicio)
            	throws ECARException, HibernateException {
    	Query query = session.createQuery("from EfItemEstPrevisaoEfiep efiep " +
        		"where efiep.exercicioExe.codExe = :codExe " +
        		"and efiep.itemEstruturaIett.codIett = :codIett " +
        		"and efiep.itemEstruturaIett.indAtivoIett = 'S' " +
        		"and efiep.fonteRecursoFonr.codFonr = :codFonr " +
        		"order by efiep.fonteRecursoFonr.nomeFonr, efiep.recursoRec.nomeRec");

    	query.setLong("codExe", codExercicio.longValue());
    	query.setLong("codIett", codItemEstrutura.longValue());
    	query.setLong("codFonr", codFonteRecurso.longValue());

    	List lista = query.list();
    	if (lista != null && lista.size() > 0) {
    		return (EfItemEstPrevisaoEfiep) lista.iterator().next();
    	} else {
    		return null;
    	}
    }

    
    /**
     * Retorna soma de valor aprovado do item 
     * - com regra de negocio - integralização de capital e unidade de investimento
     * @param codItem
     * @param codExercicio
     * @return
     * @throws ECARException
     * @throws HibernateException
     */
    public BigDecimal previsaoItemAcao(Long codItem, Long codExercicio )
        	throws ECARException, HibernateException {
    	
    	final long INTEGRALIZACAO_CAPITAL_SIM = 53L;
    	
    	ArrayList<Long> integralizacaoCapital = new ArrayList<Long>();
    	
    	ItemEstruturaIettPPA itemAcao = (ItemEstruturaIettPPA)this.buscar( ItemEstruturaIettPPA.class , codItem );

		Set sisAtributoList = itemAcao.getItemEstruturaSisAtributoIettSatbs();
		for (Iterator iterator = sisAtributoList.iterator(); iterator
				.hasNext();) {
			ItemEstruturaSisAtributoIettSatb sis = (ItemEstruturaSisAtributoIettSatb) iterator.next();
			if (sis.getSisAtributoSatb().getCodSatb().longValue() == INTEGRALIZACAO_CAPITAL_SIM ){
				integralizacaoCapital.add( itemAcao.getCodIett() );
			}
		}
			
    	StringBuilder queryStr = new StringBuilder();
    	
    	queryStr.append("select sum(efiep.valorAprovadoEfiep) from EfItemEstPrevisaoEfiep efiep " +
        		"where efiep.exercicioExe.codExe = :codExe " +
        		"and efiep.itemEstruturaIett.codIett = :codIett " +
        		"and efiep.itemEstruturaIett.indAtivoIett = 'S' " );

    	
		if ( !integralizacaoCapital.isEmpty() ){
			//queryStr.append("and efiep.itemEstruturaIett.unidadeOrcamentariaUO.indTipoOrcamentoUo != :unidadeOrcTipoOrcamento ");			
			queryStr.append("and efiep.itemEstruturaIett.codIett not in (:listaIntegralizacao) " );	
		}    	
    	
    	Query query = session.createQuery(queryStr.toString());

    	query.setLong("codExe", codExercicio.longValue());
    	query.setLong("codIett", codItem.longValue());

		if ( !integralizacaoCapital.isEmpty() ){
			query.setParameterList( "listaIntegralizacao"  , integralizacaoCapital );
	    	//query.setString("unidadeOrcTipoOrcamento" , Dominios.TIPO_ORC_INVESTIMENTO );   			
		}    	

    	query.setMaxResults(1);
    	
    	return (BigDecimal)query.uniqueResult();
	
    }    
    
    
    /**
     * Retorna soma de valor aprovado do item 
     * - com regra de negocio - integralização de capital e unidade de investimento
     * @param codItem
     * @param codExercicio
     * @return
     * @throws ECARException
     * @throws HibernateException
     */
    public BigDecimal previsaoItem(Long codItem, Long codExercicio )
        	throws ECARException, HibernateException {

    	final long INTEGRALIZACAO_CAPITAL_SIM = 53L;
    	
    	ArrayList<Long> integralizacaoCapital = new ArrayList<Long>();
    	
    	ItemEstruturaIettPPA itemPrograma = (ItemEstruturaIettPPA)this.buscar( ItemEstruturaIettPPA.class , codItem );
    	Set acoes = itemPrograma.getItemEstruturaIetts();
    	for (Iterator iter = acoes.iterator(); iter.hasNext();) {
			ItemEstruturaIettPPA acao = (ItemEstruturaIettPPA) iter.next();
			
			Set sisAtributoList = acao.getItemEstruturaSisAtributoIettSatbs();
			for (Iterator iterator = sisAtributoList.iterator(); iterator
					.hasNext();) {
				ItemEstruturaSisAtributoIettSatb sis = (ItemEstruturaSisAtributoIettSatb) iterator.next();
				if (sis.getSisAtributoSatb().getCodSatb().longValue() == INTEGRALIZACAO_CAPITAL_SIM ){
					integralizacaoCapital.add( acao.getCodIett() );
				}
			}
			
		}
    	
    	StringBuilder queryStr = new StringBuilder();
    	
    	queryStr.append("select sum(efiep.valorAprovadoEfiep) from EfItemEstPrevisaoEfiep efiep " +
        		"where efiep.exercicioExe.codExe = :codExe " +
        		"and efiep.itemEstruturaIett.itemEstruturaIett.codIett = :codIett " +
        		"and efiep.itemEstruturaIett.indAtivoIett = 'S' " );

    
		if ( !integralizacaoCapital.isEmpty() ){
			queryStr.append("and efiep.itemEstruturaIett.codIett not in (:listaIntegralizacao) " );
			//queryStr.append("and efiep.itemEstruturaIett.unidadeOrcamentariaUO.indTipoOrcamentoUo != :unidadeOrcTipoOrcamento ");			
		}    	
    	
    	Query query = session.createQuery(queryStr.toString());

    	query.setLong("codExe", codExercicio.longValue());
    	query.setLong("codIett", codItem.longValue());
    	

		if ( !integralizacaoCapital.isEmpty() ){
			query.setParameterList( "listaIntegralizacao"  , integralizacaoCapital );
	    	//query.setString("unidadeOrcTipoOrcamento" , Dominios.TIPO_ORC_INVESTIMENTO );			
		}    	

    	query.setMaxResults(1);
    	
    	return (BigDecimal)query.uniqueResult(); 	
	
    }
    
    /**
     * Retorna soma de valor aprovado do item 
     * @param codItem
     * @param codFonteRecurso
     * @param codExercicio
     * @param codRecurso
     * @return
     * @throws ECARException
     * @throws HibernateException
     */
    public BigDecimal previsaoItemSemIntegralizacao(Long codItem,
        	Long codFonteRecurso, Long codExercicio, Long codRecurso)
        	throws ECARException, HibernateException {
	Query query = session.createQuery("select sum(efiep.valorAprovadoEfiep) from EfItemEstPrevisaoEfiep efiep " +
    		"where efiep.exercicioExe.codExe = :codExe " +
    		"and efiep.itemEstruturaIett.codIett = :codIett " +
    		"and efiep.fonteRecursoFonr.codFonr = :codFonr " +
    		"and efiep.recursoRec.codRec = :codRec " +
			"and efiep.itemEstruturaIett.indAtivoIett = 'S'");

	query.setLong("codExe", codExercicio.longValue());
	query.setLong("codIett", codItem.longValue());
	query.setLong("codFonr", codFonteRecurso.longValue());
	query.setLong("codRec" , codRecurso.longValue() );
	query.setMaxResults(1);
	
	return (BigDecimal)query.uniqueResult();
	
    }     
    
    /**
     * Retorna soma de valor aprovado do item 
     * @param codItem
     * @param codFonteRecurso
     * @param codExercicio
     * @param codRecurso
     * @return
     * @throws ECARException
     * @throws HibernateException
     */
    public BigDecimal previsaoItem(Long codItem,
        	Long codFonteRecurso, Long codExercicio, Long codRecurso)
        	throws ECARException, HibernateException {

    	final long INTEGRALIZACAO_CAPITAL_SIM = 53L;
    	
    	ArrayList<Long> integralizacaoCapital = new ArrayList<Long>();
    	
    	ItemEstruturaIettPPA itemAcao = (ItemEstruturaIettPPA)this.buscar( ItemEstruturaIettPPA.class , codItem );
			
		Set sisAtributoList = itemAcao.getItemEstruturaSisAtributoIettSatbs();
		for (Iterator iterator = sisAtributoList.iterator(); iterator
				.hasNext();) {
			ItemEstruturaSisAtributoIettSatb sis = (ItemEstruturaSisAtributoIettSatb) iterator.next();
			if (sis.getSisAtributoSatb().getCodSatb().longValue() == INTEGRALIZACAO_CAPITAL_SIM ){
				integralizacaoCapital.add( itemAcao.getCodIett() );
			}
		}
    	
    	StringBuilder queryStr = new StringBuilder();
    	
    	queryStr.append("select sum(efiep.valorAprovadoEfiep) from EfItemEstPrevisaoEfiep efiep " +
        		"where efiep.exercicioExe.codExe = :codExe " +
        		"and efiep.itemEstruturaIett.codIett = :codIett " +
        		"and efiep.itemEstruturaIett.indAtivoIett = 'S' " +
        		"and efiep.fonteRecursoFonr.codFonr = :codFonr " +
        		"and efiep.recursoRec.codRec = :codRec " );
    	
		if ( !integralizacaoCapital.isEmpty() ){
			queryStr.append("and efiep.itemEstruturaIett.codIett not in (:listaIntegralizacao) " );
			//queryStr.append("and efiep.itemEstruturaIett.unidadeOrcamentariaUO.indTipoOrcamentoUo != :unidadeOrcTipoOrcamento ");			
		}    	
    	
    	Query query = session.createQuery(queryStr.toString());

    	query.setLong("codExe", codExercicio.longValue());
    	query.setLong("codIett", codItem.longValue());
    	query.setLong("codFonr", codFonteRecurso.longValue());
    	query.setLong("codRec" , codRecurso.longValue() );

		if ( !integralizacaoCapital.isEmpty() ){
			query.setParameterList( "listaIntegralizacao"  , integralizacaoCapital );
	    	//query.setString("unidadeOrcTipoOrcamento" , Dominios.TIPO_ORC_INVESTIMENTO );			
		}    	

    	query.setMaxResults(1);
    	
    	return (BigDecimal)query.uniqueResult();    	
    	
	
    }        
    
    /**
     * Retorna soma de todos os valores aprovados dos filhos do item 
     * @param codItemEstruturaPai 
     * @param codExercicio
     * @param codFonteRecurso 
     * @param codRecurso 
     * @return
     * @throws ECARException
     * @throws HibernateException
     */
    public BigDecimal previsaoSomaItensFilhosSemIntegralizacao(Long codItemEstruturaPai,
            	Long codFonteRecurso, Long codExercicio, Long codRecurso)
            	throws ECARException, HibernateException {
    	Query query = session.createQuery("select sum(efiep.valorAprovadoEfiep) from EfItemEstPrevisaoEfiep efiep " +
        		"where efiep.exercicioExe.codExe = :codExe " +
        		"and efiep.itemEstruturaIett.itemEstruturaIett.codIett = :codIett " +
        		"and efiep.itemEstruturaIett.indAtivoIett = 'S' " +
        		"and efiep.fonteRecursoFonr.codFonr = :codFonr " +
        		"and efiep.recursoRec.codRec = :codRec ");

    	query.setLong("codExe", codExercicio.longValue());
    	query.setLong("codIett", codItemEstruturaPai.longValue());
    	query.setLong("codFonr", codFonteRecurso.longValue());
    	query.setLong("codRec" , codRecurso.longValue() );
    	query.setMaxResults(1);
    	
    	
    	return (BigDecimal)query.uniqueResult();
    }    

    /**
     * Retorna soma de todos os valores aprovados dos filhos do item 
     * - com regra de negocio - integralização de capital e unidade de investimento
     * @param codItemEstruturaPai 
     * @param codRecurso
     * @param codFonteRecurso 
     * @param codExercicio
     * @return 
     * @throws ECARException
     * @throws HibernateException
     */
    public BigDecimal previsaoSomaItensFilhos(Long codItemEstruturaPai,
            	Long codFonteRecurso, Long codExercicio, Long codRecurso)
            	throws ECARException, HibernateException {
    	
    	final long INTEGRALIZACAO_CAPITAL_SIM = 53L;
    	
    	ArrayList<Long> integralizacaoCapital = new ArrayList<Long>();
    	
    	ItemEstruturaIettPPA itemPrograma = (ItemEstruturaIettPPA)this.buscar( ItemEstruturaIettPPA.class , codItemEstruturaPai );
    	Set acoes = itemPrograma.getItemEstruturaIetts();
    	for (Iterator iter = acoes.iterator(); iter.hasNext();) {
			ItemEstruturaIettPPA acao = (ItemEstruturaIettPPA) iter.next();
			
			Set sisAtributoList = acao.getItemEstruturaSisAtributoIettSatbs();
			for (Iterator iterator = sisAtributoList.iterator(); iterator
					.hasNext();) {
				ItemEstruturaSisAtributoIettSatb sis = (ItemEstruturaSisAtributoIettSatb) iterator.next();
				if (sis.getSisAtributoSatb().getCodSatb().longValue() == INTEGRALIZACAO_CAPITAL_SIM ){
					integralizacaoCapital.add( acao.getCodIett() );
				}
			}
			
		}
    	
    	StringBuilder queryStr = new StringBuilder();
    	
    	queryStr.append("select sum(efiep.valorAprovadoEfiep) from EfItemEstPrevisaoEfiep efiep " +
        		"where efiep.exercicioExe.codExe = :codExe " +
        		"and efiep.itemEstruturaIett.itemEstruturaIett.codIett = :codIett " +
        		"and efiep.itemEstruturaIett.indAtivoIett = 'S' " +
        		"and efiep.fonteRecursoFonr.codFonr = :codFonr " +
        		"and efiep.recursoRec.codRec = :codRec " );
    

    	
		if ( !integralizacaoCapital.isEmpty() ){
			queryStr.append("and efiep.itemEstruturaIett.codIett not in (:listaIntegralizacao) " );
			//queryStr.append("and efiep.itemEstruturaIett.unidadeOrcamentariaUO.indTipoOrcamentoUo != :unidadeOrcTipoOrcamento " );		
		}    	
    	
    	Query query = session.createQuery(queryStr.toString());

    	query.setLong("codExe", codExercicio.longValue());
    	query.setLong("codIett", codItemEstruturaPai.longValue());
    	query.setLong("codFonr", codFonteRecurso.longValue());
    	query.setLong("codRec" , codRecurso.longValue() );
    	    	
		if ( !integralizacaoCapital.isEmpty() ){
			query.setParameterList( "listaIntegralizacao"  , integralizacaoCapital );
			//query.setString("unidadeOrcTipoOrcamento" , Dominios.TIPO_ORC_INVESTIMENTO );			
		}    	

    	query.setMaxResults(1);
    	
    	return (BigDecimal)query.uniqueResult();
    }    
    
    
    /**
     * Método utilizado para setar os valores da PK da classe
     * EfItemEstPrevisaoEfiep
     * 
     * @param itemEstruturaRecurso
     */
    public void setPK(EfItemEstPrevisaoEfiep itemEstruturaRecurso) {
        EfItemEstPrevisaoEfiepPK comp_id = new EfItemEstPrevisaoEfiepPK();
        comp_id.setCodExe(itemEstruturaRecurso.getExercicioExe().getCodExe());
        comp_id.setCodFonr(itemEstruturaRecurso.getFonteRecursoFonr()
                .getCodFonr());
        comp_id.setCodIett(itemEstruturaRecurso.getItemEstruturaIett()
                .getCodIett());
        comp_id.setCodRec(itemEstruturaRecurso.getRecursoRec().getCodRec());
        
        // Retirado da chave primaria
        itemEstruturaRecurso.setEspecieEsp(itemEstruturaRecurso.getEspecieEsp());
        itemEstruturaRecurso.setFonteFon(itemEstruturaRecurso.getFonteFon());
        
        itemEstruturaRecurso.setComp_id(comp_id);
    }

    /**
     * Cria um objeto EfItemEstPrevisaoEfiep a partir de parâmetros passados no
     * objeto request
     * 
     * @param request
     * @param itemEstruturaRecurso
     * @throws ECARException
     */
    public void setItemEstruturaPrevisao(HttpServletRequest request,
            EfItemEstPrevisaoEfiep itemEstruturaRecurso) throws ECARException {
        ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) this.buscar(
                ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(
                        request, "codIett")));
        //TODO: Campo retirado
        //itemEstruturaRecurso.setDataValorEfiep(Pagina.getParamDataBanco(
        //       request, "dataValorEfiep"));
        itemEstruturaRecurso.setExercicioExe((ExercicioExe) super.buscar(
                ExercicioExe.class, Long.valueOf(Pagina.getParamStr(request,
                        "codExe"))));
        itemEstruturaRecurso.setFonteRecursoFonr((FonteRecursoFonr) super
                .buscar(FonteRecursoFonr.class, Long.valueOf(Pagina
                        .getParamStr(request, "codFonr"))));
        itemEstruturaRecurso.setIndAtivoEfiep("S");
        itemEstruturaRecurso.setItemEstruturaIett(itemEstrutura);
        itemEstruturaRecurso.setRecursoRec((RecursoRec) super.buscar(
                RecursoRec.class, Long.valueOf(Pagina.getParamStr(request,
                        "codRec"))));
        itemEstruturaRecurso.setValorAprovadoEfiep(new BigDecimal(Double
                .valueOf(Util.formataNumero(Pagina.getParamStr(request, "valorAprovadoEfiep")))
                .doubleValue()));
        itemEstruturaRecurso.setValorRevisadoEfiep(new BigDecimal(Double
                .valueOf(Util.formataNumero(Pagina.getParamStr(request, "valorRevisadoEfiep")))
                .doubleValue()));
    }

    /**
     * Grava uma relação entre itemEstrutura e Recurso
     * 
     * @param itemEstruturaRecurso
     * @throws ECARException
     */
    public void salvar(EfItemEstPrevisaoEfiep itemEstruturaRecurso)
            throws ECARException {
    	
    	/*
         * FIXME: Verificar esta regra
         * Está fixo, pois falta fazer na tela para informar a espécie e a fonte
         * rec 3 = fonte 49
         * rec 4 = fonte 50
         * rec 5 = fonte 51
         */ 
//    	if(itemEstruturaRecurso.getEspecieEsp() == null){
//    		itemEstruturaRecurso.setEspecieEsp((EspecieEsp) buscar(EspecieEsp.class, Long.valueOf(0)));
//    	}
    	
//    	if(itemEstruturaRecurso.getFonteFon() == null){
//    		if(itemEstruturaRecurso.getRecursoRec().getCodRec().longValue() == 3){
//    			itemEstruturaRecurso.setFonteFon((FonteFon) buscar(FonteFon.class, Long.valueOf(49)));
//    		}
//    		if(itemEstruturaRecurso.getRecursoRec().getCodRec().longValue() == 4){
//    			itemEstruturaRecurso.setFonteFon((FonteFon) buscar(FonteFon.class, Long.valueOf(50)));
//    		}
//    		if(itemEstruturaRecurso.getRecursoRec().getCodRec().longValue() == 5){
//    			itemEstruturaRecurso.setFonteFon((FonteFon) buscar(FonteFon.class, Long.valueOf(51)));
//    		}
//    	}
    	
    	
        setPK(itemEstruturaRecurso);
        itemEstruturaRecurso.setDataInclusaoEfiep(Data.getDataAtual());
        try {
            if (buscar(
                    itemEstruturaRecurso.getItemEstruturaIett().getCodIett(),
                    itemEstruturaRecurso.getFonteRecursoFonr().getCodFonr(),
                    itemEstruturaRecurso.getRecursoRec().getCodRec(),
                    itemEstruturaRecurso.getExercicioExe().getCodExe()) != null)
                throw new ECARException(
                        "itemEstrutura.recurso.inclusao.jaExiste");
        } catch (ECARException e) {
        	this.logger.error(e);
            if (e.getMessageKey().equalsIgnoreCase("erro.objectNotFound")) {
                super.salvar(itemEstruturaRecurso);
            } else
                /* joga para frente a inclusao.jaExiste */
                throw e;
        }
    }

    /**
     * Exclui todos os recursos de uma fonteRecurso
     * @param fonteRecurso
     * @throws ECARException
     */
    public void excluirRecursos(EfIettFonteTotEfieft fonteRecurso) throws ECARException{
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        List recursos = getRecursosByFonteRecurso(fonteRecurso.getFonteRecursoFonr().getCodFonr(), fonteRecurso.getItemEstruturaIett().getCodIett(), "");
	        if(recursos != null){
	            Iterator it = recursos.iterator();
	            while(it.hasNext()){
	                EfItemEstPrevisaoEfiep recurso = (EfItemEstPrevisaoEfiep) it.next();
	                session.delete(recurso);
	    			objetos.add(recurso);
	            }
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
     * Recebe um código de item estrutura e um array contendo códigos de
     * exercícios, fontes de recurso e recurso. Cada conjunto desses códigos representa um registro de Recurso de um Item.
     * O processo de Exclusão é o seguinte:
     * Para cada Recurso do Item encontrado verifica se existe conta de orçamento cadastrada para este recurso. (1)
     * Se encontrar conta, o recurso não poderá ser excluído fisicamente mas ele e as contas dependentes encontradas serão
     * desativadas (2)
     * Se não encontrar conta, exclui fisicamente o recurso (3) 
     * @param codigosParaExcluir
     * @param codItemEstrutura
     * @throws ECARException
     */
    public void excluir(String[] codigosParaExcluir, Long codItemEstrutura)
            throws ECARException {
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        for (int i = 0; i < codigosParaExcluir.length; i++) {
	            String[] codigos = codigosParaExcluir[i].split(",");
	            EfItemEstPrevisaoEfiep itemEstruturaRecurso = buscar(
	                    codItemEstrutura, Long.valueOf(codigos[0]), Long
	                            .valueOf(codigos[1]), Long.valueOf(codigos[2]));
	            
	            /* (1) */
	            EfItemEstContaEfiec conta = new EfItemEstContaEfiec();
	            conta.setItemEstruturaIett(itemEstruturaRecurso.getItemEstruturaIett());
	            conta.setExercicioExe(itemEstruturaRecurso.getExercicioExe());
	            conta.setRecursoRec(itemEstruturaRecurso.getRecursoRec());
	            List dependentes = this.pesquisar(conta, null);
	            /* (1) */
	            
	            
	            if( dependentes.size() > 0){
	                /* (2) */
	                /* desativa o recurso no item */
	                itemEstruturaRecurso.setIndAtivoEfiep("N");
	                session.update(itemEstruturaRecurso);
	    			objetos.add(itemEstruturaRecurso);
	                /* desativa as contas */
	                Iterator it = dependentes.iterator();
	                while(it.hasNext()){
	                    EfItemEstContaEfiec contaDependente = (EfItemEstContaEfiec) it.next();
	                    contaDependente.setIndAtivoEfiec("N");
	                    session.update(contaDependente);
		    			objetos.add(contaDependente);
	                    throw new ECARException("itemEstrutura.recurso.exclusao.possuiConta");                    
	                }
	                /* (2) */
	            } else {
	                /* (3) */
	                session.delete(itemEstruturaRecurso);
	    			objetos.add(itemEstruturaRecurso);
	                /* (3) */
	            }
	                       
	        }
			
			tx.commit();
	
			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("ALT_EXC");
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
     * Devolve objetos EfItemEstPrevisaoEfiep ( recurso de um item Estrutura ) a
     * partir do Código da Fonte de Recurso e código do Exercício
     * 
     * @param codFonteRecurso
     * @param codItemEstrutura 
     * @param ativo
     * @return
     * @throws ECARException
     */
    public List getRecursosByFonteRecurso(Long codFonteRecurso, Long codItemEstrutura, String ativo) throws ECARException {
        EfItemEstPrevisaoEfiep itemEstruturaRecurso = new EfItemEstPrevisaoEfiep(); 
        itemEstruturaRecurso.setFonteRecursoFonr((FonteRecursoFonr) super
                .buscar(FonteRecursoFonr.class, codFonteRecurso));
        itemEstruturaRecurso.setItemEstruturaIett((ItemEstruturaIett) super.buscar(
                ItemEstruturaIett.class, codItemEstrutura));
        if(!"".equals(ativo))
        	itemEstruturaRecurso.setIndAtivoEfiep(ativo);
        return super.pesquisar(itemEstruturaRecurso, null);        
    }
    
    /**
     *
     * @param codFonteRecurso
     * @param codItemEstrutura
     * @return
     * @throws ECARException
     */
    public List getRecursosDistintosByFonteRecurso(Long codFonteRecurso,
    			Long codItemEstrutura) throws ECARException {
    	try {
	    	StringBuilder sql = new StringBuilder();
			
			sql.append("select distinct recurso from RecursoRec as recurso ");
			sql.append("join recurso.efItemEstPrevisaoEfieps as ieRecurso ");
			sql.append("where ieRecurso.itemEstruturaIett.codIett = :item ");
			sql.append("and ieRecurso.itemEstruturaIett.indAtivoIett = 'S'");
			sql.append("and ieRecurso.fonteRecursoFonr.codFonr = :fonte ");
			sql.append("order by recurso.sequenciaRec asc");			
			
			Query query = this.getSession().createQuery(sql.toString());
			
			query.setLong("item", codItemEstrutura.longValue());
			query.setLong("fonte", codFonteRecurso.longValue());
			
			return query.list();
    	} catch (HibernateException e) {
    		this.logger.error(e);
			throw new ECARException(e);
        }
    }
    
    /**
     * Retorna os Recursos que possuem valores (Aprovado/Revisado).
     * @param codFonteRecurso
     * @param codItemEstrutura
     * @return List de RecursoRec
     * @throws ECARException
     */
    public List getRecursosDistintosComValoresByFonteRecurso(Long codFonteRecurso,
			Long codItemEstrutura) throws ECARException {
		try {
	    	StringBuilder sql = new StringBuilder();
			
			sql.append("select distinct recurso from RecursoRec as recurso ");
			sql.append("join recurso.efItemEstPrevisaoEfieps as ieRecurso ");
			sql.append("where ieRecurso.itemEstruturaIett.codIett = :item ");
			sql.append("and ieRecurso.itemEstruturaIett.indAtivoIett = 'S' ");
			sql.append("and ieRecurso.fonteRecursoFonr.codFonr = :fonte ");
			sql.append("and (ieRecurso.valorAprovadoEfiep > 0 or ieRecurso.valorRevisadoEfiep > 0)");
			sql.append("order by recurso.sequenciaRec asc");
			
			Query query = this.getSession().createQuery(sql.toString());
			
			query.setLong("item", codItemEstrutura.longValue());
			query.setLong("fonte", codFonteRecurso.longValue());
			
			return query.list();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
	    }
	}

    /**
     * Retorna uma lista com os recursos cadastradas para um item em uma Fonte de Recurso e em um Exercício 
     * @param itemEstrutura
     * @param exercicio
     * @param fonte
     * @return
     * @throws ECARException
     */
    public List getRecursosByFonteRecursoExercicio(ItemEstruturaIett itemEstrutura, ExercicioExe exercicio, FonteRecursoFonr fonte) throws ECARException{
        List retorno = new ArrayList();
        EfItemEstPrevisaoEfiep recurso = new EfItemEstPrevisaoEfiep();
        recurso.setItemEstruturaIett(itemEstrutura);
        recurso.setExercicioExe(exercicio);
        recurso.setFonteRecursoFonr(fonte);
        recurso.setIndAtivoEfiep("S");        
        List ieRec =  super.pesquisar(recurso, null);
        if(ieRec != null ){
            Iterator it = ieRec.iterator();
            while(it.hasNext()){
                retorno.add ( ((EfItemEstPrevisaoEfiep) it.next() ).getRecursoRec() );
            }
        }
        return retorno;

    }

    /**
     * Devolve uma lista com todos os Recursos que possuem o item, o exercício e a fonte de recursos infomados
     * como parâmetro
     * @param itemEstrutura
     * @param exercicio
     * @param fonte
     * @return
     * @throws ECARException
     */
    public List getRecursosByFonteRecursoExercicio(Long itemEstrutura, Long exercicio, Long fonte) throws ECARException{
        ItemEstruturaIett item = (ItemEstruturaIett) this.buscar(ItemEstruturaIett.class, itemEstrutura);
        ExercicioExe exercicioExe = (ExercicioExe) this.buscar(ExercicioExe.class, exercicio);
        FonteRecursoFonr fonteRec = (FonteRecursoFonr) this.buscar(FonteRecursoFonr.class, fonte);
        return this.getRecursosByFonteRecursoExercicio(item, exercicioExe, fonteRec);
    }
    
    /**
     * Devolve uma lista de ItemEstruturaPrevisaoEfiep para um dado exercicio e item,
     * ordenados pela sequência de apresentação.
     * @param item
     * @param exercicio
     * @return List ItemEstruturaPrevisaoEfiep
     * @throws ECARException
     */
    public List getListaItemEstruturaPrevisao(ItemEstruturaIett item, ExercicioExe exercicio) throws ECARException {
        List lista = new ArrayList();
        
        try {
        	
        	StringBuilder sb = new StringBuilder();
        	sb.append("from EfItemEstPrevisaoEfiep efiep ");
        	sb.append(" where ");
        	
        	if(exercicio != null){
        		sb.append("efiep.exercicioExe.codExe = :codExe and ");
        	}
        	
        	sb.append(" efiep.itemEstruturaIett.codIett = :codIett ");
        	sb.append(" and efiep.itemEstruturaIett.indAtivoIett = 'S' ");
        	sb.append("order by efiep.fonteRecursoFonr.sequenciaFonr, efiep.recursoRec.sequenciaRec");
        	
            Query query = session.createQuery(sb.toString());
            
            if(exercicio != null)
            	query.setLong("codExe", exercicio.getCodExe().longValue());
            
            query.setLong("codIett", item.getCodIett().longValue());
            
            lista = query.list();
        } catch (HibernateException e) {
        	this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
        }
        return lista;
    }
    
    /**
     * 
     * @param item
     * @return
     * @throws ECARException
     */
    public List getListaExerciciosItemEstruturaPrevisao(ItemEstruturaIett item) throws ECARException {
        List lista = new ArrayList();
        
        try {
        	
        	StringBuilder sb = new StringBuilder();
        	sb.append("select distinct efiep.exercicioExe from EfItemEstPrevisaoEfiep efiep ");
        	sb.append(" where ");
        	sb.append(" efiep.itemEstruturaIett.codIett = :codIett and ");
        	sb.append(" efiep.itemEstruturaIett.indAtivoIett = 'S' ");
        	sb.append("order by efiep.exercicioExe.dataInicialExe");
        	
            Query query = session.createQuery(sb.toString());
            
            query.setLong("codIett", item.getCodIett().longValue());
            
            lista = query.list();
        } catch (HibernateException e) {
        	this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
        }
        return lista;
    }
    
    /**
     * 
     * @param codItem
     * @param codFonteRecurso
     * @param codExercicio
     * @param codRecurso
     * @param tipo
     * @return
     * @throws ECARException
     * @throws HibernateException
     */
    public BigDecimal previsaoSomaValores(Long codItem,
        	Long codFonteRecurso, Long codExercicio, Long codRecurso, String tipo)
        	throws ECARException, HibernateException {
    	
    	StringBuilder select = new StringBuilder();
    	
    	if(Dominios.EFIEP_VALOR_APROVADO.equals(tipo)){
    		select.append("select sum(efiep.valorAprovadoEfiep) from EfItemEstPrevisaoEfiep efiep ");
    	}
    	if(Dominios.EFIEP_VALOR_REVISADO.equals(tipo)){
    		select.append("select sum(efiep.valorRevisadoEfiep) from EfItemEstPrevisaoEfiep efiep ");
    	}
    	
    	select.append("where efiep.exercicioExe.codExe = :codExe ");
    	select.append(" and efiep.itemEstruturaIett.codIett = :codIett ");
    	select.append(" and efiep.itemEstruturaIett.indAtivoIett = 'S' ");
    	select.append(" and efiep.fonteRecursoFonr.codFonr = :codFonr ");
    	select.append("and efiep.recursoRec.codRec = :codRec ");
    	
		Query query = session.createQuery(select.toString());
	
		query.setLong("codExe", codExercicio.longValue());
		query.setLong("codIett", codItem.longValue());
		query.setLong("codFonr", codFonteRecurso.longValue());
		query.setLong("codRec" , codRecurso.longValue() );
		query.setMaxResults(1);
		
		return (BigDecimal)query.uniqueResult();
    }    
}