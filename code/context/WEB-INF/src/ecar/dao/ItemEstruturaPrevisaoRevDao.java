/*
 * Criado em 21/12/2004
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.pojo.EfIettFonTotRevEfieftr;
import ecar.pojo.EfIettPrevisaoRevEfiepr;
import ecar.pojo.EfIettPrevisaoRevEfieprPK;
import ecar.pojo.ExercicioExe;
import ecar.pojo.FonteRecursoFonr;
import ecar.pojo.ItemEstruturarevisaoIettrev;
import ecar.pojo.RecursoRec;

/**
 * @author felipev
 *  
 */
public class ItemEstruturaPrevisaoRevDao extends Dao {

    /**
     *
     * @param request
     */
    public ItemEstruturaPrevisaoRevDao(HttpServletRequest request) {
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
    public EfIettPrevisaoRevEfiepr buscar(Integer codItemEstrutura,
            Integer codFonteRecurso, Integer codRecurso, Integer codExercicio)
            throws ECARException {
        EfIettPrevisaoRevEfieprPK comp_id = new EfIettPrevisaoRevEfieprPK();
        comp_id.setCodFonr(codFonteRecurso);
        comp_id.setCodExe(codExercicio);
        comp_id.setCodIettrev(codItemEstrutura);
        comp_id.setCodRec(codRecurso);
        return (EfIettPrevisaoRevEfiepr) super.buscar(
        		EfIettPrevisaoRevEfiepr.class, comp_id);
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
    public EfIettPrevisaoRevEfiepr buscar(Integer codItemEstrutura,
    			Long codFonteRecurso, Long codExercicio)
            	throws ECARException, HibernateException {
    	Query query = session.createQuery("from EfIettPrevisaoRevEfiepr efiepr " +
        		"where efiepr.exercicioExe.codExe = :codExe " +
        		"and efiepr.itemEstruturarevisaoIettrev.codIettrev = :codIettRev " +
        		"and efiepr.fonteRecursoFonr.codFonr = :codFonr " +
        		"order by efiepr.fonteRecursoFonr.nomeFonr, efiepr.recursoRec.nomeRec");

    	query.setLong("codExe", codExercicio.longValue());
    	query.setInteger("codIettRev", codItemEstrutura.intValue());
    	query.setLong("codFonr", codFonteRecurso.longValue());

    	List lista = query.list();
    	if (lista != null && lista.size() > 0) {
    		return (EfIettPrevisaoRevEfiepr) lista.iterator().next();
    	} else {
    		return null;
    	}
    }

    /**
     * Método utilizado para setar os valores da PK da classe
     * EfItemEstPrevisaoEfiep
     * 
     * @param itemEstruturaRecurso
     */
    public void setPK(EfIettPrevisaoRevEfiepr itemEstruturaRecurso) {
        EfIettPrevisaoRevEfieprPK comp_id = new EfIettPrevisaoRevEfieprPK();
        comp_id.setCodExe(Integer.valueOf(itemEstruturaRecurso.getExercicioExe().getCodExe().intValue()));
        comp_id.setCodFonr(Integer.valueOf(itemEstruturaRecurso.getFonteRecursoFonr()
                .getCodFonr().intValue()));
        comp_id.setCodIettrev(itemEstruturaRecurso.getItemEstruturarevisaoIettrev()
                .getCodIettrev());
        comp_id.setCodRec(Integer.valueOf(itemEstruturaRecurso.getRecursoRec().getCodRec().intValue()));
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
            EfIettPrevisaoRevEfiepr itemEstruturaRecurso) throws ECARException {
        ItemEstruturarevisaoIettrev itemEstrutura = (ItemEstruturarevisaoIettrev) this.buscar(
                ItemEstruturarevisaoIettrev.class, Integer.valueOf(Pagina.getParamStr(
                        request, "codIettrev")));
        //Campo retirado
        //itemEstruturaRecurso.setDataValorEfiep(Pagina.getParamDataBanco(
        //       request, "dataValorEfiep"));
        itemEstruturaRecurso.setExercicioExe((ExercicioExe) super.buscar(
                ExercicioExe.class, Long.valueOf(Pagina.getParamStr(request,
                        "codExe"))));
        itemEstruturaRecurso.setFonteRecursoFonr((FonteRecursoFonr) super
                .buscar(FonteRecursoFonr.class, Long.valueOf(Pagina
                        .getParamStr(request, "codFonr"))));
        itemEstruturaRecurso.setIndAtivoEfiepr("S");
        itemEstruturaRecurso.setItemEstruturarevisaoIettrev(itemEstrutura);
        itemEstruturaRecurso.setRecursoRec((RecursoRec) super.buscar(
                RecursoRec.class, Long.valueOf(Pagina.getParamStr(request,
                        "codRec"))));
        itemEstruturaRecurso.setValorAprovadoEfiepr(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, "valorAprovadoEfiepr"))));
        itemEstruturaRecurso.setValorRevisadoEfiepr(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, "valorRevisadoEfiepr"))));
    }

    /**
     * Grava uma relação entre itemEstrutura e Recurso
     * 
     * @param itemEstruturaRecurso
     * @throws ECARException
     */
    public void salvar(EfIettPrevisaoRevEfiepr itemEstruturaRecurso)
            throws ECARException {
        setPK(itemEstruturaRecurso);
        itemEstruturaRecurso.setDataInclusaoEfiepr(Data.getDataAtual());
        try {
            if (buscar(
                    itemEstruturaRecurso.getItemEstruturarevisaoIettrev().getCodIettrev(),
                    Integer.valueOf(itemEstruturaRecurso.getFonteRecursoFonr().getCodFonr().intValue()),
                    Integer.valueOf(itemEstruturaRecurso.getRecursoRec().getCodRec().intValue()),
                    Integer.valueOf(itemEstruturaRecurso.getExercicioExe().getCodExe().intValue())) != null)
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
    public void excluirRecursos(EfIettFonTotRevEfieftr fonteRecurso) throws ECARException{
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        List recursos = getRecursosByFonteRecurso(fonteRecurso.getFonteRecursoFonr().getCodFonr(), fonteRecurso.getItemEstruturarevisaoIettrev().getCodIettrev());
	        if(recursos != null){
	            Iterator it = recursos.iterator();
	            while(it.hasNext()){
	                EfIettPrevisaoRevEfiepr recurso = (EfIettPrevisaoRevEfiepr) it.next();
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
    public void excluir(String[] codigosParaExcluir, Integer codItemEstrutura)
            throws ECARException {
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        for (int i = 0; i < codigosParaExcluir.length; i++) {
	            String[] codigos = codigosParaExcluir[i].split(",");
	            EfIettPrevisaoRevEfiepr itemEstruturaRecurso = buscar(
	                    codItemEstrutura, Integer.valueOf(codigos[0]), Integer
	                            .valueOf(codigos[1]), Integer.valueOf(codigos[2]));
	            
	            session.delete(itemEstruturaRecurso);
				objetos.add(itemEstruturaRecurso);
	                       
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
     * Devolve objetos EfItemEstPrevisaoEfiep ( recurso de um item Estrutura ) a
     * partir do Código da Fonte de Recurso e código do Exercício
     * 
     * @param codFonteRecurso
     * @param codItemEstrutura
     * @return
     * @throws ECARException
     */
    public List getRecursosByFonteRecurso(Long codFonteRecurso, Integer codItemEstrutura) throws ECARException {
        EfIettPrevisaoRevEfiepr itemEstruturaRecurso = new EfIettPrevisaoRevEfiepr(); 
        itemEstruturaRecurso.setFonteRecursoFonr((FonteRecursoFonr) super
                .buscar(FonteRecursoFonr.class, codFonteRecurso));
        itemEstruturaRecurso.setItemEstruturarevisaoIettrev((ItemEstruturarevisaoIettrev) super.buscar(
                ItemEstruturarevisaoIettrev.class, codItemEstrutura));
        itemEstruturaRecurso.setIndAtivoEfiepr("S");
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
    			Integer codItemEstrutura) throws ECARException {
    	try {
	    	StringBuilder sql = new StringBuilder();
			
			sql.append("select distinct recurso from RecursoRec as recurso ");
			sql.append("join recurso.efIettPrevisaoRevEfieprs as ieRecurso ");
			sql.append("where ieRecurso.itemEstruturarevisaoIettrev.codIettrev = :item ");
			sql.append("and ieRecurso.fonteRecursoFonr.codFonr = :fonte ");
			
			Query query = this.getSession().createQuery(sql.toString());
			
			query.setLong("item", codItemEstrutura.intValue());
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
    public List getRecursosByFonteRecursoExercicio(ItemEstruturarevisaoIettrev itemEstrutura, ExercicioExe exercicio, FonteRecursoFonr fonte) throws ECARException{
        List retorno = new ArrayList();
        EfIettPrevisaoRevEfiepr recurso = new EfIettPrevisaoRevEfiepr();
        recurso.setItemEstruturarevisaoIettrev(itemEstrutura);
        recurso.setExercicioExe(exercicio);
        recurso.setFonteRecursoFonr(fonte);
        recurso.setIndAtivoEfiepr("S");        
        List ieRec =  super.pesquisar(recurso, null);
        if(ieRec != null ){
            Iterator it = ieRec.iterator();
            while(it.hasNext()){
                retorno.add ( ((EfIettPrevisaoRevEfiepr) it.next() ).getRecursoRec() );
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
    public List getRecursosByFonteRecursoExercicio(Integer itemEstrutura, Long exercicio, Long fonte) throws ECARException{
        ItemEstruturarevisaoIettrev item = (ItemEstruturarevisaoIettrev) this.buscar(ItemEstruturarevisaoIettrev.class, itemEstrutura);
        ExercicioExe exercicioExe = (ExercicioExe) this.buscar(ExercicioExe.class, exercicio);
        FonteRecursoFonr fonteRec = (FonteRecursoFonr) this.buscar(FonteRecursoFonr.class, fonte);
        return this.getRecursosByFonteRecursoExercicio(item, exercicioExe, fonteRec);
    }
    
    /**
     * Devolve uma lista de ItemEstruturaPrevisaoEfiep para um dado exercicio e item.
     * @param item 
     * @param exercicio
     * @return List ItemEstruturaPrevisaoEfiep
     * @throws ECARException
     */
    public List getListaItemEstruturaPrevisao(ItemEstruturarevisaoIettrev item, ExercicioExe exercicio) throws ECARException {
        List lista = new ArrayList();
        
        try {
        	
            Query query = session.createQuery("from EfIettPrevisaoRevEfiepr efiepr " +
            		"where efiepr.exercicioExe.codExe = :codExe " +
            		"and efiepr.itemEstruturarevisaoIettrev.codIettrev = :codIettrev " +
            		"order by efiepr.fonteRecursoFonr.nomeFonr, efiep.recursoRec.nomeRec");
            
            query.setLong("codExe", exercicio.getCodExe().longValue());
            query.setInteger("codIettrev", item.getCodIettrev().intValue());
            lista = query.list(); 
        } catch (HibernateException e) {
        	this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
        }
        return lista;
    }


}