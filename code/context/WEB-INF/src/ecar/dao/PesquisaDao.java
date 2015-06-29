/*
 * Created on 11/05/2005
 * 
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Arrays;
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
import comum.util.ConstantesECAR;
import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;

import ecar.bean.AtributoEstruturaListagemItens;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.PaiFilho;
import ecar.pojo.Pesquisa;
import ecar.pojo.PesquisaIett;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.UsuarioUsu;


/**
 * @author evandro
 *
 */
public class PesquisaDao extends Dao{
	
	/**
	 * Tipo de pesquisa do Usuário
	 */
	public static final String PESQ_USUARIO = "U";
	/**
	 * Tipo de Pesquisa do Sistema
	 */
	public static final String PESQ_SISTEMA = "S";
	
	/**
	 * Exibe o ultimo periodo de referencia gerado
	 */
	public static final String ULTIMO_PERIODO = "U";
	/**
	 * Exibe o periodo de referencia correspondente a data atual
	 */
	public static final String PERIODO_ATUAL= "A";
	/**
	 * Exibe o periodo de referencia salvo pelo usuário
	 */
	public static final String PERIODO_EXIBIDO = "E";
	

	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public PesquisaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Metodo para setar os valores no objeto Pesquisa
	 * @param campos
         * @param pesquisa
	 * @throws ECARException 
	 */
	public void setPesquisa (HttpServletRequest campos,	Pesquisa pesquisa)throws ECARException{
		
		if (request.getSession().getAttribute("seguranca")!=null)
			pesquisa.setUsuarioUsu(((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
	
		if(request.getParameter("periodo")!= null ){
			pesquisa.setPeriodo(Integer.parseInt(request.getParameter("periodo")));
		}	

		if(!Pagina.getParamStr(campos, "nomePesquisa").equals("")) {
			pesquisa.setNomePesquisa(request.getParameter("nomePesquisa"));
		}
		
	 	//caso de checkbox
	 	if(!Pagina.getParamStr(campos, "indTipoPesquisa").equals("")) {
	 		pesquisa.setIndTipoPesquisa(Pagina.getParamStr(campos, "indTipoPesquisa"));
	 	} else {
	 		pesquisa.setIndTipoPesquisa(PESQ_USUARIO);
	 	}

		if(!Pagina.getParamStr(campos, "indTipoReferencia").equals("")) {
			pesquisa.setIndTipoReferencia(request.getParameter("indTipoReferencia"));
		}
	 	
		if(!Pagina.getParamStr(campos, "tipoAcompanhamentoTa").equals("")) {
			Long codTa = Long.valueOf(Pagina.getParamStr(campos, "tipoAcompanhamentoTa"));   
			pesquisa.setTipoAcompanhamentoTa((TipoAcompanhamentoTa)this.buscar(TipoAcompanhamentoTa.class, codTa) );
		}

		if (request.getSession().getAttribute("mapItensReferencia")!=null){
			pesquisa.setPesquisaIetts(setItensPesquisa(campos, pesquisa));
		}
	}
	
	
	
	/**
	 * Recupera os itens retornados do filtro guardados na sessão
	 * 
	 * @return  Conjunto com a lista com os itens da pesquisa da sessão
	 */
	private Set setItensPesquisa(HttpServletRequest campos,	Pesquisa pesquisa){
		Set pesquisaItens = new HashSet();
		if((request.getSession().getAttribute("mapItensReferencia")!=null)){
			HashMap<AcompReferenciaAref, List<AtributoEstruturaListagemItens>> itensAref = (HashMap<AcompReferenciaAref, List<AtributoEstruturaListagemItens>>) request.getSession().getAttribute("mapItensReferencia");
			for (AcompReferenciaAref acompReferenciaAref : itensAref.keySet()) {
				List<AtributoEstruturaListagemItens> itensEstrutura = itensAref.get(acompReferenciaAref);
				for (AtributoEstruturaListagemItens atributoEstruturaListagemItens : itensEstrutura) {
					PesquisaIett pesquisaIett = new PesquisaIett();
					pesquisaIett.setAcompReferenciaAref(acompReferenciaAref);
					pesquisaIett.setItemEstruturaIett(atributoEstruturaListagemItens.getItem());
					pesquisaIett.setPesquisa(pesquisa);
					pesquisaItens.add(pesquisaIett);
				}
			}
		}//fim if
		return pesquisaItens;
	}

    /**
     *
     * @return
     * @throws ECARException
     */
    public List listarPesquisaSistema() throws ECARException{
		List list = new ArrayList<Pesquisa>();
		try{
			StringBuilder query = new StringBuilder("select pesquisas from Pesquisa as pesquisas ")
				.append(" where indTipoPesquisa='").append(PesquisaDao.PESQ_SISTEMA).append("'")
				.append(" order by nomePesquisa "); 
			Query q = this.getSession().createQuery(query.toString());
			
			list = q.list();
			
		} catch(Exception e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
		return list;
	}
	
	
    /**
     *
     * @param request
     * @return
     */
    public Set setPesquisaSistemaGrupoAcesso(HttpServletRequest request){
		
		Set<Pesquisa> set = new HashSet<Pesquisa>();
		
		if(request.getParameterValues("pesquisa")!=null){
			String strPesquisa[] = request.getParameterValues("pesquisa");
			Long codPesquisa=null;//[] = new Long[strEntidades.length];
			
			try {
			for (int i = 0; i < strPesquisa.length; i++) {
				codPesquisa = Long.parseLong(strPesquisa[i]);
				Pesquisa pesquisa = (Pesquisa) this.buscar(Pesquisa.class, codPesquisa);
				
				set.add(pesquisa);
			}//fim for
			} catch (ECARException e) {
//				e.printStackTrace();
			}

		}//fim if
		
		return set;
		
	}
	
	
	/**
	 * 
	 * @param gruposAcesso
	 * @return
	 * @throws ECARException
	 */
	public List listarPesquisaSistemaDeGruposAcesso (Set gruposAcesso) throws ECARException{
		List list = new ArrayList<Pesquisa>();
		
		try{
			StringBuilder query = new StringBuilder("select distinct p from Pesquisa p left join p.sisAtributoSatbs atb ").
						append(" where ").
						append(" p.indTipoPesquisa = 'S' and atb.codSatb in (:lCodSatb) ").
						append(" and exists (select pesquisaIett from PesquisaIett pesquisaIett where pesquisaIett.pesquisa.codPesquisa = p.codPesquisa) ").
						append(" order by p.nomePesquisa asc "); 
			
			Query q = this.getSession().createQuery(query.toString());
		    
			Set<Long> lCodSatb = new HashSet<Long>();
            Iterator itGrupos = gruposAcesso.iterator();
            while(itGrupos.hasNext()){
                SisAtributoSatb grupoAcesso = (SisAtributoSatb) itGrupos.next();
                lCodSatb.add(grupoAcesso.getCodSatb());
            }
            
			if(!lCodSatb.isEmpty()) {
				q.setParameterList("lCodSatb", lCodSatb);
		    }
			
			list= q.list();
			
			
		} catch(Exception e) {
			this.logger.error(e);
			e.printStackTrace();
			throw new ECARException(e);
		}
		return list;
	}

	/**
	 * 
     * @param usuario
	 * @return
	 * @throws ECARException
	 */
	public List listarPesquisaUsuario (UsuarioUsu usuario) throws ECARException{
		List list = new ArrayList<Pesquisa>();
		
		try{
			StringBuilder query = new StringBuilder("select p from Pesquisa p  ").
						append(" where ").
						append(" (p.indTipoPesquisa = 'U' and p.usuarioUsu.codUsu = :usuario ) ").
						append(" and exists (select pesquisaIett from PesquisaIett pesquisaIett where pesquisaIett.pesquisa.codPesquisa = p.codPesquisa) ").
						append(" order by p.nomePesquisa asc "); 

			Query q = this.getSession().createQuery(query.toString());
			
        	if(usuario != null){
	    		q.setLong("usuario", usuario.getCodUsu().longValue());
	    	}
			
			list= q.list();
			
		} catch(Exception e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
		return list;
	}
	
	
    /**
     *
     * @param pesquisas
     * @return
     * @throws ECARException
     */
    public List ordenaPesquisa(List<Pesquisa> pesquisas ) throws ECARException{
		List pesquisaOrd = new ArrayList<Pesquisa>();
		
		return pesquisaOrd;
	}
	
	
	
    /**
     *
     * @param mapTipoAcompComPesquisa
     * @param pesquisa
     * @return
     */
    public String getNomeFiltroReferenciaHtml(Map mapTipoAcompComPesquisa, Pesquisa pesquisa) throws ECARException{
		PesquisaDao pesquisaDao=new PesquisaDao(null);
		AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
		AcompReferenciaAref aref = null;
		String strPeriodo = "";
		List arefs = (List)mapTipoAcompComPesquisa.get(pesquisa.getTipoAcompanhamentoTa());
		String nomeReferencia = "";
		String tipoReferencia = "";
		boolean tipoAcompSeparadoOrgao = false;
		if (pesquisa.getTipoAcompanhamentoTa().getIndSepararOrgaoTa() != null && pesquisa.getTipoAcompanhamentoTa().getIndSepararOrgaoTa().equals(Pagina.SIM)){
			tipoAcompSeparadoOrgao = true;
		}
		if (pesquisa.getIndTipoReferencia()==null || pesquisa.getIndTipoReferencia().equals(PesquisaDao.PERIODO_ATUAL) ){
			aref = (AcompReferenciaAref)arefs.get(0);
			tipoReferencia = " - Refer&ecirc;ncia Atual";
		} else if (pesquisa.getIndTipoReferencia().equals(PesquisaDao.ULTIMO_PERIODO) ){
			aref = (AcompReferenciaAref)arefs.get(1);
			tipoReferencia = " - Última Refer&ecirc;ncia ";
		} else {
			//Existe uma validação na inclusão 
			//da pesquisa para não deixar salvar uma pesquisa sem referência
			Iterator itPesquisaIetts = pesquisa.getPesquisaIetts().iterator();
			if (itPesquisaIetts.hasNext()){
				PesquisaIett pesquisaIett = (PesquisaIett) itPesquisaIetts.next();
				aref = pesquisaIett.getAcompReferenciaAref();
			}
		}
		nomeReferencia = aref.getNomeAref();
		if (tipoAcompSeparadoOrgao && acompReferenciaDao.getListaMesmaReferenciaDiaMesAno(aref).size() > 1) {
			nomeReferencia = ConstantesECAR.LABEL_ORGAO_CONSOLIDADO;
		} 
		strPeriodo = nomeReferencia + " - " + aref.getDiaAref() + "/" + aref.getMesAref() + "/" + aref.getAnoAref() + tipoReferencia;
		
		return strPeriodo;
	}

	/**
	 * Habita o Map sendo a chave os Tipos de Acompanhamento e o 
	 * campo uma lista com duas Referencias a referencia com maior data e 
	 * a referencia da data atual. 
	 * 
         * @param tipoAcompComPesquisa
	 * @param pesquisa
	 * @throws ECARException 
	 * @throws HibernateException 
	 */
	public void adicionaTipoAcompReferencias(Map<TipoAcompanhamentoTa, List> tipoAcompComPesquisa, Pesquisa pesquisa) throws HibernateException, ECARException{
		AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(null);
		List<AcompReferenciaAref> arefAtualIUltimo =  new ArrayList<AcompReferenciaAref>();
		
		List<AcompReferenciaAref> arefsTa = acompReferenciaDao.getListAcompReferenciaByTipoAcompanhamento(pesquisa.getTipoAcompanhamentoTa().getCodTa());
		
		if (arefsTa!= null && arefsTa.size()>0  ){ 
			arefAtualIUltimo.add(0,acompReferenciaDao.getAcompSelecionado(arefsTa));
			arefAtualIUltimo.add(1, arefsTa.get(0));
		}
		
		tipoAcompComPesquisa.put(pesquisa.getTipoAcompanhamentoTa(), arefAtualIUltimo);

	}

	/**
	 * Salva a pesquisa
	 * @param pesquisa
	 * @throws ECARException
	 */
	public void salvarPesquisa(Pesquisa pesquisa) throws ECARException {
		Transaction tx = null;
        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        List filhos = new ArrayList();
	        if ((pesquisa.getPesquisaIetts() != null) && (pesquisa.getPesquisaIetts().size() > 0)){
	        	filhos.addAll(pesquisa.getPesquisaIetts());
	        } 
	            
            session.save(pesquisa);
			objetos.add(pesquisa);

			Iterator it = filhos.iterator();
			while(it.hasNext()) {
			    PaiFilho object = (PaiFilho) it.next();
			    object.atribuirPKPai();
			    //salva os filhos
			    session.save(object);
				objetos.add(object);
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
     * @param aref
	 * @return
	 * @throws ECARException
	 */
	public List<Pesquisa> listarPesquisasByAref (String[] codigosAref) throws ECARException{
		List list = new ArrayList<Pesquisa>();
		Long[] codAref = Util.arrayStringToArrayLong(codigosAref);
		try{
			StringBuilder query = new StringBuilder("select distinct p.pesquisa from PesquisaIett p  ").
						append(" where p.acompReferenciaAref.codAref in (:codigosAref)"); 

			Query q = this.getSession().createQuery(query.toString());
			
    		q.setParameterList("codigosAref", Arrays.asList((codAref)));
		
			list= q.list();
			
		} catch(Exception e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
		return list;
	}
	
}

