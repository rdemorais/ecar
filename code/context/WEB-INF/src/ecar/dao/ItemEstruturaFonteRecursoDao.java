/*
 * Criado em 21/12/2004
 *
 */
package ecar.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
import ecar.pojo.EfIettFonteTotEfieft;
import ecar.pojo.EfIettFonteTotEfieftPK;
import ecar.pojo.EfItemEstContaEfiec;
import ecar.pojo.EfItemEstPrevisaoEfiep;
import ecar.pojo.ExercicioExe;
import ecar.pojo.FonteRecursoFonr;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.RecursoRec;
import ecar.util.Dominios;

/**
 * @author felipev
 *  
 */
public class ItemEstruturaFonteRecursoDao extends Dao {
    /**
     *
     * @param request
     */
    public ItemEstruturaFonteRecursoDao(HttpServletRequest request) {
		super();
		this.request = request;
    }

    /**
     * Retorna as Fontes de Recursos ativas de um itemEstrutura
     * 
     * @param itemEstrutura
     * @return
     * @throws ECARException
     */    
    public List getAtivos(ItemEstruturaIett itemEstrutura) throws ECARException {
        try {
            //Query q = getSession() 
            //        .createQuery(
            //                " select fonteRecurso from ecar.pojo.EfIettFonteTotEfieft fonteRecurso " +
            //                "where fonteRecurso.itemEstruturaIett.codIett = ? and fonteRecurso.indAtivoEfieft = 'S' " +
            //                "order by fonteRecurso.fonteRecursoFonr.nomeFonr, fonteRecurso.exercicioExe.descricaoExe asc");
            Query q = getSession() 
            .createQuery(
                    " select fonteRecurso from ecar.pojo.EfIettFonteTotEfieft fonteRecurso " +
                    "where fonteRecurso.itemEstruturaIett.codIett = ? and fonteRecurso.itemEstruturaIett.indAtivoIett = 'S' and fonteRecurso.indAtivoEfieft = 'S' " +
                    "order by fonteRecurso.fonteRecursoFonr.sequenciaFonr asc ,fonteRecurso.fonteRecursoFonr.nomeFonr asc");
            q.setLong(0, itemEstrutura.getCodIett().longValue());
            return q.list();
        } catch (HibernateException e) {
            this.logger.error(e);
        	throw new ECARException("erro.hibernateException");            
        } 
    }

    /**
     * Verifica a possibilidade de ser incluída uma fonte de recurso para um
     * ItemEstrutura. Todos os itens que possuem fontes de recursos devem estar no mesmo 
     * nivel da estrutura, podendo ser de ramificações diferentes.
     * 
     * @param itemEstrutura
     * @return
     * @throws ECARException
     */
    public boolean verificaPossibilidadeInclusao(ItemEstruturaIett itemEstrutura) throws ECARException {
		/*EstruturaEtt estrutura = itemEstrutura.getEstruturaEtt();
		Query queryEstrutura = this.getSession().createQuery("select distinct fonte.itemEstruturaIett.estruturaEtt " +
				" from EfIettFonteTotEfieft fonte");
		EstruturaEtt estruturaNiveis = (EstruturaEtt) queryEstrutura.uniqueResult();
		if(estruturaNiveis == null)
			return true;
		else{
			if(estruturaNiveis.equals(estrutura))
				return true;
		}*/
    	
    	/* ** Encontrando fonte de recurso no item já permite inclusão ** */
		if(itemEstrutura.getEfIettFonteTotEfiefts().size() > 0)
			return true;
			
		ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
		
		List lista = new ArrayList();
		lista.addAll(itemDao.getAscendentes(itemEstrutura));
		lista.addAll(itemDao.getDescendentes(itemEstrutura, true));
		
		Iterator it = lista.iterator();
		
		while(it.hasNext()){
			ItemEstruturaIett itemLista = (ItemEstruturaIett) it.next();
			
			if(itemLista.getEfIettFonteTotEfiefts() != null &&
					itemLista.getEfIettFonteTotEfiefts().size() > 0)
				return false;
		}
		
		return true;	
    }

    /**
     * Retorna um objeto EfIettFonteTotEfieft a partir do código do Item da
     * Estrutura, do código do exercício e do código da Fonte de Recurso
     * 
     * @param codItemEstrutura
     * @param codFonteRecurso
     * @param codExercicio
     * @return
     * @throws ECARException
     */
    public EfIettFonteTotEfieft buscarOLD(Long codItemEstrutura,
            Long codFonteRecurso, Long codExercicio) throws ECARException {
        EfIettFonteTotEfieftPK comp_id = new EfIettFonteTotEfieftPK();
        comp_id.setCodFonr(codFonteRecurso);
        //comp_id.setCodExe(codExercicio);
        comp_id.setCodIett(codItemEstrutura);
        return (EfIettFonteTotEfieft) super.buscar(EfIettFonteTotEfieft.class,
                comp_id);
    }
    
    /**
     * Retorna um objeto EfIettFonteTotEfieft a partir do código do Item da
     * Estrutura, do código do exercício e do código da Fonte de Recurso
     * 
     * @param codItemEstrutura
     * @param codFonteRecurso
     * @return
     * @throws ECARException
     */
    public EfIettFonteTotEfieft buscar(Long codItemEstrutura,
            Long codFonteRecurso) throws ECARException {
        EfIettFonteTotEfieftPK comp_id = new EfIettFonteTotEfieftPK();
        comp_id.setCodFonr(codFonteRecurso);
        comp_id.setCodIett(codItemEstrutura);
        return (EfIettFonteTotEfieft) super.buscar(EfIettFonteTotEfieft.class,
                comp_id);
    }
    
    /**
     * Retorna uma lista com as fontes de recurso cadastradas para um item em um Exercício
     * @param itemEstrutura
     * @param exercicio
     * @return
     * @throws ECARException
     */
    public List getFontesRecursosByExercicio(ItemEstruturaIett itemEstrutura, ExercicioExe exercicio) throws ECARException{
        List retorno = new ArrayList();
        EfIettFonteTotEfieft fonte = new EfIettFonteTotEfieft();
        fonte.setItemEstruturaIett(itemEstrutura);
        //fonte.setExercicioExe(exercicio);
        fonte.setIndAtivoEfieft("S");
        List ieFontes =  super.pesquisar(fonte, null);
        if(ieFontes != null ){
            Iterator it = ieFontes.iterator();
            while(it.hasNext()){
                retorno.add ( ((EfIettFonteTotEfieft) it.next() ).getFonteRecursoFonr() );
            }
        }
        return retorno;
    }    
        
    /**
     *
     * @param itemEstrutura
     * @param exercicio
     * @return
     * @throws ECARException
     */
    public List getFontesRecursosByExercicio(Long itemEstrutura, Long exercicio) throws ECARException{
        ItemEstruturaIett item = (ItemEstruturaIett) this.buscar(ItemEstruturaIett.class, itemEstrutura);
        ExercicioExe exercicioExe = (ExercicioExe) this.buscar(ExercicioExe.class, exercicio);
        return this.getFontesRecursosByExercicio( item, exercicioExe);
    }    
    
   
    /**
     * Cria um objeto EfIettFonteTotEfieft a partir de parâmetros passados no
     * objeto request
     * 
     * @param request
     * @param itemEstruturaFonteRecurso
     * @throws ECARException
     */
    public void setItemEstruturaFonteRecursoOLD(HttpServletRequest request,
            EfIettFonteTotEfieft itemEstruturaFonteRecurso) throws ECARException {
        /*ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) this.buscar(
                ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(
                        request, "codIett")));*/
        itemEstruturaFonteRecurso.setDataValorEfieft(Pagina.getParamDataBanco(
                request, "dataValorEfieft"));
        //itemEstruturaFonteRecurso.setExercicioExe((ExercicioExe) super.buscar(
        //        ExercicioExe.class, Long.valueOf(Pagina.getParamStr(request,
        //                "codExe"))));
        itemEstruturaFonteRecurso.setFonteRecursoFonr((FonteRecursoFonr) super
                .buscar(FonteRecursoFonr.class, Long.valueOf(Pagina
                        .getParamStr(request, "codFonr"))));
        itemEstruturaFonteRecurso.setIndAtivoEfieft("S");
        itemEstruturaFonteRecurso.setValorEfieft(new BigDecimal(Double.valueOf(
                Util.formataNumero(Pagina.getParamStr(request, "valorEfieft")))
                .doubleValue()));
        itemEstruturaFonteRecurso
                .setItemEstruturaIett((ItemEstruturaIett) super.buscar(
                        ItemEstruturaIett.class, Long.valueOf(Pagina
                                .getParamStr(request, "codIett"))));
    }
    
    /**
     * Cria um objeto EfIettFonteTotEfieft a partir de parâmetros passados no
     * objeto request
     * 
     * @param request
     * @param itemEstruturaFonteRecurso
     * @throws ECARException
     */
    public void setItemEstruturaFonteRecurso(HttpServletRequest request,
    			EfIettFonteTotEfieft itemEstruturaFonteRecurso) 
    			throws ECARException {
        itemEstruturaFonteRecurso.setDataValorEfieft(Pagina.getParamDataBanco(request, "dataValorEfieft"));
        itemEstruturaFonteRecurso.setFonteRecursoFonr((FonteRecursoFonr) super.buscar(FonteRecursoFonr.class, Long.valueOf(Pagina.getParamStr(request, "codFonr"))));
        itemEstruturaFonteRecurso.setItemEstruturaIett((ItemEstruturaIett) super.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett"))));
    }
    
    /**
     * 
     * @param request
     * @param itemEstruturaFonteRecurso
     * @return
     * @throws ECARException
     * @deprecated
     */
    public List setListaRecursoOLD(HttpServletRequest request,
    			EfIettFonteTotEfieft itemEstruturaFonteRecurso) 
    			throws ECARException {
    	List lista = new ArrayList();
    	
    	ExercicioDao exercicioDao = new ExercicioDao(request);
		Collection listaExercicios = exercicioDao.getExerciciosValidos(itemEstruturaFonteRecurso.getItemEstruturaIett().getCodIett());
		Iterator itExerc = listaExercicios.iterator();
		
		while (itExerc.hasNext()) {
			ExercicioExe exercicio = (ExercicioExe) itExerc.next();
			
			if (!"".equals(Pagina.getParamStr(request, "recursoRec" + exercicio.getCodExe().toString()))) {
				EfItemEstPrevisaoEfiep recurso = new EfItemEstPrevisaoEfiep();
				
				recurso.setExercicioExe(exercicio);
				recurso.setFonteRecursoFonr(itemEstruturaFonteRecurso.getFonteRecursoFonr());
				recurso.setItemEstruturaIett(itemEstruturaFonteRecurso.getItemEstruturaIett());
				recurso.setRecursoRec((RecursoRec) super.buscar(RecursoRec.class, Long.valueOf(Pagina.getParamStr(request, "recursoRec" + exercicio.getCodExe().toString()))));
				recurso.setValorAprovadoEfiep(new BigDecimal(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, "valorAprovadoEfiep" + exercicio.getCodExe().toString()))).doubleValue()));
				recurso.setValorRevisadoEfiep(new BigDecimal(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, "valorRevisadoEfiep" + exercicio.getCodExe().toString()))).doubleValue()));
				
		        recurso.setIndAtivoEfiep("S");
		    	recurso.setDataInclusaoEfiep(Data.getDataAtual());

		    	/*
		         * FIXME: Verificar esta regra
		         * Está fixo, pois falta fazer na tela para informar a espécie e a fonte
		         * rec 3 = fonte 49
		         * rec 4 = fonte 50
		         * rec 5 = fonte 51
		         */ 
//		    	if(recurso.getEspecieEsp() == null){
//		    		recurso.setEspecieEsp((EspecieEsp) buscar(EspecieEsp.class, Long.valueOf(0)));
//		    	}
//		    	
//		    	if(recurso.getFonteFon() == null){
//		    		if(recurso.getRecursoRec().getCodRec().longValue() == 3){
//		    			recurso.setFonteFon((FonteFon) buscar(FonteFon.class, Long.valueOf(49)));
//		    		}
//		    		if(recurso.getRecursoRec().getCodRec().longValue() == 4){
//		    			recurso.setFonteFon((FonteFon) buscar(FonteFon.class, Long.valueOf(50)));
//		    		}
//		    		if(recurso.getRecursoRec().getCodRec().longValue() == 5){
//		    			recurso.setFonteFon((FonteFon) buscar(FonteFon.class, Long.valueOf(51)));
//		    		}
//		    	}

		    	lista.add(recurso);
			}
		}
    	
    	return lista;
    }
    
    /**
     * @param request
     * @param itemEstruturaFonteRecurso
     * @param numRecursos
     * @return
     * @throws ECARException
     */
    public List setListaRecurso(HttpServletRequest request,
				EfIettFonteTotEfieft itemEstruturaFonteRecurso,
				int numRecursos) throws ECARException {
		List lista = new ArrayList();
		
		ExercicioDao exercicioDao = new ExercicioDao(request);
		//Collection listaExercicios = exercicioDao.getExerciciosValidos(itemEstruturaFonteRecurso.getItemEstruturaIett().getCodIett());
		List listaExercicios = exercicioDao.getExerciciosValidos(itemEstruturaFonteRecurso.getItemEstruturaIett().getCodIett());
		
		String nomeCombo = "";
		String nomeCampoVlAprov = "";
		String nomeCampoVlRev = "";
		String nomeCampoAtivo = "";
		
		for (int i = 0; i < numRecursos; i++) {
			nomeCombo = "recursoRec" + i;
			//System.out.println(nomeCombo +" numeroRecursos: "+ numRecursos);
			Iterator itExerc = listaExercicios.iterator();
			
			while (itExerc.hasNext()) {
				ExercicioExe exercicio = (ExercicioExe) itExerc.next();
				
				nomeCampoVlRev = "valorRevisadoEfiep" + i + "e" + exercicio.getCodExe().toString();
				nomeCampoVlAprov = "valorAprovadoEfiep" + i + "e" + exercicio.getCodExe().toString();
				nomeCampoAtivo = "ativo" + i + "e" + exercicio.getCodExe().toString();
				
				if (!"".equals(Pagina.getParamStr(request, nomeCampoVlRev)) ||
							!"".equals(Pagina.getParamStr(request, nomeCampoVlAprov))) {
					EfItemEstPrevisaoEfiep recurso = new EfItemEstPrevisaoEfiep();

			    	recurso.setExercicioExe(exercicio);
					recurso.setFonteRecursoFonr(itemEstruturaFonteRecurso.getFonteRecursoFonr());
					recurso.setItemEstruturaIett(itemEstruturaFonteRecurso.getItemEstruturaIett());
					recurso.setRecursoRec((RecursoRec) super.buscar(RecursoRec.class, Long.valueOf(Pagina.getParamStr(request, nomeCombo))));
					
					if(!"".equals(Pagina.getParamStr(request, nomeCampoVlAprov)))
						recurso.setValorAprovadoEfiep(new BigDecimal(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, nomeCampoVlAprov))).doubleValue()));
					else
						recurso.setValorAprovadoEfiep(null);
					
					if(!"".equals(Pagina.getParamStr(request, nomeCampoVlRev)))
						recurso.setValorRevisadoEfiep(new BigDecimal(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, nomeCampoVlRev))).doubleValue()));
					else
						recurso.setValorRevisadoEfiep(null);
					
			        //recurso.setIndAtivoEfiep("S");
					String valorAtivo = Pagina.getParamStr(request, nomeCampoAtivo);
					recurso.setIndAtivoEfiep("S".equals(valorAtivo) ? "S" : "N");
					
					recurso.setDataInclusaoEfiep(Data.getDataAtual());

			    	lista.add(recurso);
				}
			}
		}
		//System.out.println("terminou com sucesso");
		return lista;
	}

    /**
     * Método utilizado para setar os valores da PK da classe
     * EfIettFonteTotEfieft
     * 
     * @param itemEstruturaFonteRecurso
     */
    public void setPK(EfIettFonteTotEfieft itemEstruturaFonteRecurso) {
        EfIettFonteTotEfieftPK comp_id = new EfIettFonteTotEfieftPK();
        comp_id.setCodFonr(itemEstruturaFonteRecurso.getFonteRecursoFonr().getCodFonr());
        comp_id.setCodIett(itemEstruturaFonteRecurso.getItemEstruturaIett().getCodIett());
        itemEstruturaFonteRecurso.setComp_id(comp_id);
    }
    
    /**
     * Grava uma relação entre itemEstrutura e Fonte de Recurso
     * e todos os recursos preenchidos
     * 
     * @param itemEstruturaFonteRecurso
     * @param listaRecursos
     * @throws ECARException
     * @throws HibernateException 
     */
    public void salvar(EfIettFonteTotEfieft itemEstruturaFonteRecurso, 
    			List listaRecursos) throws ECARException {
    	Transaction tx = null;
        try {
        	ArrayList objetos = new ArrayList();
			super.inicializarLogBean();
        	
			tx = session.beginTransaction();
			
        	setPK(itemEstruturaFonteRecurso);
            itemEstruturaFonteRecurso.setDataInclusaoEfieft(Data.getDataAtual());
            itemEstruturaFonteRecurso.setIndAtivoEfieft("S");
            
            EfIettFonteTotEfieft itemEstFRAux = null;
            try {
            	itemEstFRAux = buscar(itemEstruturaFonteRecurso.getItemEstruturaIett().getCodIett(),
            			itemEstruturaFonteRecurso.getFonteRecursoFonr().getCodFonr());
            } catch (Exception e) {
            	this.logger.error(e);
            	//quando não é encontrado nenhum registro gera exceção
            	itemEstFRAux = null;
            }
            
            if (itemEstFRAux != null) {
            	throw new ECARException("itemEstrutura.fonteRecurso.inclusao.jaExiste");
            }
            
            session.save(itemEstruturaFonteRecurso);
            objetos.add(itemEstruturaFonteRecurso);
            
            ItemEstruturaPrevisaoDao itemEstPrevisaoDao = new ItemEstruturaPrevisaoDao(request);
            
            Iterator itRecursos = listaRecursos.iterator();
            while (itRecursos.hasNext()) {
            	EfItemEstPrevisaoEfiep recurso = (EfItemEstPrevisaoEfiep) itRecursos.next();
            	//System.out.println(recurso.getFonteFon() + "  " + recurso.getRecursoRec());
            	
            	itemEstPrevisaoDao.setPK(recurso);
            	session.save(recurso);
                objetos.add(recurso);
            }
            
            tx.commit();
            
            if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC");
				Iterator itObj = objetos.iterator();

				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
        } catch (HibernateException he) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
			this.logger.error(he);
			throw new ECARException("erro.hibernateException"); 
		}
    }

    /**
     * Altera os atributos de uma relação entre ItemEstrutura e Fonte de Recurso
     * e todos os recursos preenchidos
     * 
     * @param itemEstruturaFonteRecurso
     * @param listaRecursos
     * @throws ECARException
     * @throws HibernateException 
     */
    public void alterar(EfIettFonteTotEfieft itemEstruturaFonteRecurso, 
    			List listaRecursos) throws ECARException {
    	Transaction tx = null;
        try {
        	ArrayList objetos = new ArrayList();
			super.inicializarLogBean();
        	
			tx = session.beginTransaction();
			
            session.update(itemEstruturaFonteRecurso);
            objetos.add(itemEstruturaFonteRecurso);
            
            ItemEstruturaPrevisaoDao itemEstPrevisaoDao = new ItemEstruturaPrevisaoDao(request);
            
            /* selecionar todos os recursos gravados para a fonte */
            List recursosDoBanco = itemEstPrevisaoDao.getRecursosByFonteRecurso(
            			itemEstruturaFonteRecurso.getFonteRecursoFonr().getCodFonr(),
            			itemEstruturaFonteRecurso.getItemEstruturaIett().getCodIett(), "");	
            
            /*List recursosDoBanco = itemEstPrevisaoDao.getRecursosDistintosComValoresByFonteRecurso(
        			itemEstruturaFonteRecurso.getFonteRecursoFonr().getCodFonr(),
        			itemEstruturaFonteRecurso.getItemEstruturaIett().getCodIett());
            */
            ExercicioDao exercicioDao = new ExercicioDao(request);
            Collection listaExercicios = exercicioDao.getExerciciosValidos(itemEstruturaFonteRecurso.getItemEstruturaIett().getCodIett());
			
            /* excluir todos os recursos que possam ter sido alterados na tela, a partir dos exercício validos */
            Iterator itRecBanco = recursosDoBanco.iterator();
            while (itRecBanco.hasNext()) {
            	EfItemEstPrevisaoEfiep recurso = (EfItemEstPrevisaoEfiep) itRecBanco.next();
            	if (listaExercicios.contains(recurso.getExercicioExe())) {
            		session.delete(recurso);
                    objetos.add(recurso);
            	}
            }
            
            Iterator itRecursos = listaRecursos.iterator();
            while (itRecursos.hasNext()) {
            	EfItemEstPrevisaoEfiep recurso = (EfItemEstPrevisaoEfiep) itRecursos.next();
            	
            	itemEstPrevisaoDao.setPK(recurso);
            	session.save(recurso);
                objetos.add(recurso);
            }
            
            tx.commit();
            
            if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC_ALT_EXC");
				Iterator itObj = objetos.iterator();

				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
        } catch (HibernateException he) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
			this.logger.error(he);
			throw new ECARException("erro.hibernateException"); 
		}
    }

    
    /**
     * Recebe um código de item estrutura e um array contendo códigos de
     * fontes de recurso, recuperar objetos itemEstruturaFonteRecurso e realiza,
     * para cada um deles o seguinte processo de exclusão:
     * 
     * (1) Excluir todas as contas (EfItemEstContaEfieg) para o Item e IEFonteRec;
     * (2) Excluir todos os recursos ();
     * (3) Excluir a fonte de recurso ();
     * 
     * @param codigosParaExcluir
     * @param codItemEstrutura
     * @throws ECARException
     */
    public void excluir(String[] codigosParaExcluir, Long codItemEstrutura)
            	throws ECARException {
    	Transaction tx = null;
    	
    	try {
	    	ArrayList objetos = new ArrayList();
			super.inicializarLogBean();
	    	
			tx = session.beginTransaction();
    	
	        for (int i = 0; i < codigosParaExcluir.length; i++) {
	            String[] codigos = codigosParaExcluir[i].split(",");
	            EfIettFonteTotEfieft itemEstruturaFonR = buscar(codItemEstrutura,
	                    Long.valueOf(codigos[0]));
	
	            EfItemEstContaEfiec objBuscaConta = new EfItemEstContaEfiec();
	            objBuscaConta.setItemEstruturaIett(itemEstruturaFonR.getItemEstruturaIett());
	            objBuscaConta.setFonteRecursoFonr(itemEstruturaFonR.getFonteRecursoFonr());
	
	            List resultBuscaConta = this.pesquisar(objBuscaConta, null);
	
	            if(resultBuscaConta.size() > 0){
	                Iterator it = resultBuscaConta.iterator();
	                while(it.hasNext()){
	                    EfItemEstContaEfiec contaEncontrada = (EfItemEstContaEfiec) it.next();
	                    session.delete(contaEncontrada);
	                    objetos.add(contaEncontrada);
	                }
	            }
	            
	            EfItemEstPrevisaoEfiep objBuscaRecurso = new EfItemEstPrevisaoEfiep();
	            objBuscaRecurso.setFonteRecursoFonr(itemEstruturaFonR.getFonteRecursoFonr());
	            objBuscaRecurso.setItemEstruturaIett(itemEstruturaFonR.getItemEstruturaIett());
	            
	            List resultBuscaRecurso = this.pesquisar(objBuscaRecurso, null);
	            
	            if(resultBuscaRecurso.size() > 0){
	                Iterator it = resultBuscaRecurso.iterator();
	                while(it.hasNext()){
	                	EfItemEstPrevisaoEfiep recursoEncontrado = (EfItemEstPrevisaoEfiep) it.next();
	                    session.delete(recursoEncontrado);
	                    objetos.add(recursoEncontrado);
	                }
	            }
	            
	            session.delete(itemEstruturaFonR);
                objetos.add(itemEstruturaFonR);
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
        } catch (HibernateException he) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
			this.logger.error(he);
			throw new ECARException("erro.hibernateException"); 
		} catch (Exception e) {
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
     * Desativa uma fonte de recurso e seus recursos
     * 
     * @param itemEstruturaFonteRecurso
     * @throws ECARException
     */
    public void desativaRecursos(EfIettFonteTotEfieft itemEstruturaFonteRecurso)
            throws ECARException {
        itemEstruturaFonteRecurso.setIndAtivoEfieft("N");
        this.alterar(itemEstruturaFonteRecurso);
        //List recursos = new ItemEstruturaPrevisaoDao(request)
        //        .getRecursosByFonteRecurso(itemEstruturaFonteRecurso
        //                .getFonteRecursoFonr().getCodFonr(),
        //                itemEstruturaFonteRecurso.getExercicioExe().getCodExe(),
        //                itemEstruturaFonteRecurso.getItemEstruturaIett().getCodIett());
        List recursos = new ItemEstruturaPrevisaoDao(request)
                .getRecursosByFonteRecurso(itemEstruturaFonteRecurso
                .getFonteRecursoFonr().getCodFonr(),
                itemEstruturaFonteRecurso.getItemEstruturaIett().getCodIett(), Dominios.SIM);
        if (recursos != null) {
            Iterator it = recursos.iterator();
            while (it.hasNext()) {
                EfItemEstPrevisaoEfiep recurso = (EfItemEstPrevisaoEfiep) it
                        .next();
                recurso.setIndAtivoEfiep("N");
                new ItemEstruturaPrevisaoDao(request).alterar(recurso);
            }
        }
    }

    /**
     * Soma o valor dos recursos de uma Fonte
     * 
     * @param itemEstruturaFonteRecurso
     * @return
     * @param tipo (Aprovado, Revisado ou Todos)
     * @throws ECARException
     */
    public double getSomaRecursosFonteRecurso(EfIettFonteTotEfieft itemEstruturaFonteRecurso, String tipo) throws ECARException {
        double total = 0;
        List recursos = new ItemEstruturaPrevisaoDao(request)
        		.getRecursosByFonteRecurso(itemEstruturaFonteRecurso
                .getFonteRecursoFonr().getCodFonr(),
                itemEstruturaFonteRecurso.getItemEstruturaIett().getCodIett(), Dominios.SIM);
        if (recursos != null) {
            Iterator it = recursos.iterator();
            while (it.hasNext()) {
                EfItemEstPrevisaoEfiep recurso = (EfItemEstPrevisaoEfiep) it
                        .next();
                if ("Aprovado".equalsIgnoreCase(tipo) && recurso.getValorAprovadoEfiep() != null)
                	total += recurso.getValorAprovadoEfiep().doubleValue();
                if ("Revisado".equalsIgnoreCase(tipo) && recurso.getValorRevisadoEfiep() != null)
                	total += recurso.getValorRevisadoEfiep().doubleValue();  
                if ("Todos".equalsIgnoreCase(tipo))
                {
                	if(recurso.getValorAprovadoEfiep() != null)
                		total += recurso.getValorAprovadoEfiep().doubleValue();
                	if(recurso.getValorRevisadoEfiep() != null)
                		total += recurso.getValorRevisadoEfiep().doubleValue(); 
                }
            }
        }       
        return total;
    }
    
    /**
     * Função que retorna o código e o nome do próximo recurso a ser cadastrado no cadastro de itens.
     * Verifica se existe algum recurso cadastrado no item e retorna 2 Strings, sendo:
     * String 1: codigo do próximo recurso
     * String 2: nome do próximo recurso
     *  
     * Se todos os recursos já estiverem cadastrados no item, retorna NULL.
     * 
     * @param item
     * @return String[2]
     * @throws ECARException
     */
    public String[] verificaExistenciaRecursoIett(ItemEstruturaIett item) throws ECARException{
		List fontesAtivas = this.getAtivos(item);
		List todasFontes = new FonteRecursoDao(request).listar(FonteRecursoFonr.class, new String[]{"sequenciaFonr","asc"});
		String[] retorno = new String[2];
		
		if(todasFontes != null && todasFontes != null && fontesAtivas.size() == todasFontes.size()){
			retorno = null;
		}
		else {//Ainda existem fontes que não foram cadastradas... verificar qual é a próxima fonte...
			Iterator itTodasFontes = todasFontes.iterator();
			while(itTodasFontes.hasNext()){
				FonteRecursoFonr fonte = (FonteRecursoFonr) itTodasFontes.next();
				Iterator itFontesEmTela = fontesAtivas.iterator();
				boolean existeFonteCadastrada = false;
				while(itFontesEmTela.hasNext()){
					EfIettFonteTotEfieft fonteCadastrada = (EfIettFonteTotEfieft) itFontesEmTela.next();
					if(fonteCadastrada.getFonteRecursoFonr().equals(fonte)){
						existeFonteCadastrada = true;
						break;
					}
				}
				
				if(!existeFonteCadastrada){
					retorno = new String[]{fonte.getCodFonr().toString(),fonte.getNomeFonr()};
					break;
				}
			}
		}
		return retorno;
    }
}