package ecar.dao;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import comum.database.Dao;
import comum.util.ConstantesECAR;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.pojo.AtributoDemandaAtbdem;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SitDemandaSitd;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.VisaoAtributoDemanda;
import ecar.pojo.VisaoDemandasGrpAcesso;
import ecar.pojo.VisaoDemandasVisDem;
import ecar.pojo.VisaoSituacaoDemanda;


/**
 * Classe de acesso ao Banco para a entidade Visao.
 *
 */
public class VisaoDao extends Dao {
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public VisaoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Visão 
	 * @param visao
	 * @throws ECARException
	 */
	public void salvar(VisaoDemandasVisDem visao) throws ECARException {
	    	Transaction tx = null;
		if (pesquisarDuplos(visao, false))
		     throw new ECARException("visao.validacao.registroDuplicado");
		
		
		if (visao.getDescricaoVisao().length() > 500) {
			throw new ECARException("registroDemanda.inclusao.erro.descricao");
		}
		
		try{
        		tx = session.beginTransaction();
        		session.saveOrUpdate(visao);
        		
        		for (VisaoSituacaoDemanda vsd : visao.getVisaoSituacaoDemandas()){
        		    vsd.getId().setCodVisao(visao.getCodVisao());
        		    session.saveOrUpdate(vsd);
        		}
        		
        		///session.save(visao.getVisaoSituacaoDemandas());
        		tx.commit();
		}catch (HibernateException e) {
		    throw new ECARException("erro.hibernateException"); 
		}
	}

	/**
	 * Excluir visão
	 * @param visao
	 * @throws ECARException
	 */
	public void excluir(VisaoDemandasVisDem visao) throws ECARException {
		try {
			boolean excluir = true;
	       
			if(excluir){
				List objs = new ArrayList();
				
				if (visao.getVisaoSituacaoDemandas() != null) {
					Iterator itVisoesSituacaoDemandas = visao.getVisaoSituacaoDemandas().iterator();
					while (itVisoesSituacaoDemandas.hasNext()) {
						VisaoSituacaoDemanda visaoSituacaoDemanda = (VisaoSituacaoDemanda) itVisoesSituacaoDemandas.next();
						objs.add(visaoSituacaoDemanda);
					}
				}
				visao.setVisaoSituacaoDemandas(null);
				
				if (visao.getVisoesGrpAcesso() != null) {
					Iterator itVisoesDemandas = visao.getVisoesGrpAcesso().iterator();
					while (itVisoesDemandas.hasNext()) {
						VisaoDemandasGrpAcesso visaoDemandas = (VisaoDemandasGrpAcesso) itVisoesDemandas.next();
						objs.add(visaoDemandas);
					}
				}
				visao.setVisoesGrpAcesso(null);
												
				// remover atributos demandas da visao
				String hql = MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_ATRIBUTOS_DEMANDA_VISAO, request.getSession().getServletContext()), visao.getCodVisao().toString());
				
				Query q = this.getSession().createQuery(hql);
										
				List atributosDemandas = q.list();
				
				ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
				if(configuracaoDao.getConfiguracao().getVisaoDemandasVisDem() != null && 
						configuracaoDao.getConfiguracao().getVisaoDemandasVisDem().getCodVisao() == visao.getCodVisao()){
	                excluir = false;
	                throw new ECARException( "visao.exclusao.erro.associacaoDemanda" ); //Possui acompanhamentos ligados a essa funcao de acompanhamento codAri)
				}
				
				Iterator itAtributosDemandas = atributosDemandas.iterator();
				while (itAtributosDemandas.hasNext()) {
					VisaoAtributoDemanda atributosVisao = (VisaoAtributoDemanda) itAtributosDemandas.next();
					objs.add(atributosVisao);
				}
				
				objs.add(visao);
				super.excluir(objs);
				
				
			}	
		} catch(IOException e){
			this.logger.error(e);
	        throw new ECARException(e);	
        } catch(ECARException e){
			this.logger.error(e);
			throw e;
		}    
	}
//	
		/**
         *
         * @param visao
         * @throws ECARException
         */
	public void alterar(VisaoDemandasVisDem visao) throws ECARException {
		Transaction tx = null;
		if (pesquisarDuplos(visao, true))
			throw new ECARException("visao.validacao.registroDuplicado");
		try{
        	tx = session.beginTransaction();
        	session.saveOrUpdate(visao);
        	for (VisaoSituacaoDemanda vsd : visao.getVisaoSituacaoDemandas()){
        	    vsd.getId().setCodVisao(visao.getCodVisao());
        	    session.saveOrUpdate(vsd);
        	}
        	tx.commit();
		}catch (HibernateException e) {
		    throw new ECARException("erro.hibernateException"); 
		}
	}

		
	/**
	 * Seleciona as visões de acordo com um conjunto de ids de visões 
	 * @param idsVisoes
	 * @return
	 * @throws ECARException
	 */
	public List getVisoes(String[] idsVisoes) throws ECARException{
		
		try
    	{
    	
			List<VisaoDemandasVisDem> lista = null;
			
			if (idsVisoes!=null && idsVisoes.length>0 && idsVisoes[0].length()>0) {
		    	Criteria crits = session.createCriteria(VisaoDemandasVisDem.class);
	 
		    	Long[] idVisoesLong = new Long[idsVisoes.length];
		    	for(int i=0;i<idsVisoes.length;i++) {
		    		idVisoesLong[i] = Long.valueOf(idsVisoes[i]);
		    	}
		    	
		    	crits.add(Restrictions.in("codVisao",idVisoesLong));
		    		    	
		    	lista = (List<VisaoDemandasVisDem>)crits.list();
		    	
			}
	    	return lista;
    	
		}catch (Exception e) {
	        this.logger.error(e);
	    	throw new ECARException("erro.hibernateException");
		}
		
	}
	
	/**
	 * Seleciona as visões de acordo com um conjunto de ids de visões 
	 * @param idsVisoes
	 * @return
	 * @throws ECARException
	 */
	public List getVisoesAtributoDemanda(String[] idsVisoes) throws ECARException{
		
		try
    	{
    	
			List<VisaoDemandasVisDem> lista = null;
			
			if (idsVisoes!=null && idsVisoes.length>0 && idsVisoes[0].length()>0) {
		    	Criteria crits = session.createCriteria(VisaoDemandasVisDem.class);
	 
		    	Long[] idVisoesLong = new Long[idsVisoes.length];
		    	for(int i=0;i<idsVisoes.length;i++) {
		    		idVisoesLong[i] = Long.valueOf(idsVisoes[i]);
		    	}
		    	
		    	crits.add(Restrictions.in("codVisao",idVisoesLong));
		    		    	
		    	lista = (List<VisaoDemandasVisDem>)crits.list();
		    	
			}
	    	return lista;
    	
		}catch (Exception e) {
	        this.logger.error(e);
	    	throw new ECARException("erro.hibernateException");
		}
		
	}
	
	/**
	 * Recupera todas as visões
	 * @return
	 * @throws ECARException
	 */
	public List getVisoes() throws ECARException{
		
		return super.listar(VisaoDemandasVisDem.class, null);
	}

	/**
	 * Recupera as visões acessados pelo usuário
	 * @param usuario
	 * @param indRetornarVisaoConfiguracaoGeral indica se a consulta deve retornar a visão configurada em configuracaoGeral para associação de demandas
	 * @return
	 * @throws ECARException
	 */
	public List getVisoesGrupoAcesso(UsuarioUsu usuario, boolean indRetornarVisaoConfiguracaoGeral, HttpServletRequest request) throws ECARException{
		
		Set<SisAtributoSatb> gruposAcesso = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getGruposAcesso();
		
		String codStabString = "";
		
		Iterator<SisAtributoSatb> it = gruposAcesso.iterator();
		
		int counter = 0;
		while(it.hasNext()) {
			if (counter==0)
				codStabString += it.next().getCodSatb().toString();
			else
		 		codStabString += "," + it.next().getCodSatb().toString();
			counter++;
		}
		
		if (codStabString.length()>0) {
			codStabString = "(" + codStabString + ")";
		try {	
			
			String hql = "";
			
			if (indRetornarVisaoConfiguracaoGeral){
				hql = MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_VISOES_DADO_GRUPOS_ACESSOS_USUARIO, request.getSession().getServletContext()), codStabString); 
			} else {
				hql = MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_VISOES_DADO_GRUPOS_ACESSOS_USUARIO_SEM_VISAO_CONFIG_GERAL, request.getSession().getServletContext()), codStabString);
			}
				
			StringBuilder query = new StringBuilder(hql);

			Query q = this.getSession().createQuery(query.toString());

			return q.list();
			
		} catch(IOException e){
			this.logger.error(e);
	        throw new ECARException(e);
	    }
			
		} else {
			return null;
		}
							

	}
	
	/**
     *
     * @param obj
     * @param alteracao
     * @return
     * @throws ECARException
     */
	public boolean pesquisarDuplos(VisaoDemandasVisDem obj, boolean alteracao) throws ECARException {
    	
    	boolean retorno = false;
    	
    	try
    	{
    	
	    	Criteria crits = session.createCriteria(VisaoDemandasVisDem.class);
 
	    	if (!alteracao) {
	    		crits.add(Restrictions.eq("descricaoVisao", obj.getDescricaoVisao()));
	    	
	    	} else {
	    		crits.add(Restrictions.eq("descricaoVisao", obj.getDescricaoVisao()));
	    		crits.add(Restrictions.ne("codVisao", obj.getCodVisao()));
	    	}
	    		
	    	List<AtributoDemandaAtbdem> lista = (List<AtributoDemandaAtbdem>)crits.list();
	    	
	    	if (lista.size() > 0) {
	    		retorno = true;
	    	}
	    	
	    	return retorno;
    	
		}catch (Exception e) {
	        this.logger.error(e);
	    	throw new ECARException("erro.hibernateException");
		}
    }
	
	/**
	 * 
	 * @param request
	 * @param visao
	 * @param situacoesFiltro
	 * @param situacoesAlteracao
	 * @param ordem
	 * @return
	 * @throws ECARException
	 */
	public List<VisaoDemandasVisDem> pesquisar(HttpServletRequest request, VisaoDemandasVisDem visao, List<SitDemandaSitd> situacoesFiltro, List<SitDemandaSitd> situacoesAlteracao, String[] ordem) throws ECARException{
		SituacaoDao situacaoDao = new SituacaoDao(request);
		VisaoDao visaoDao = new VisaoDao(request);
		VisaoSituacaoDemandaDao visaoSituacaoDemandaDao = new VisaoSituacaoDemandaDao(request);
	    List<VisaoDemandasVisDem> listaVisaoGeral = new ArrayList<VisaoDemandasVisDem>();
	    boolean removerVisao = false;
//	    if (((situacoesAlteracao != null) && (situacoesAlteracao.size() > 0)) 
//		    || ((situacoesFiltro != null) && (situacoesFiltro.size() > 0)) 
//		    || ((visao.getDescricaoVisao() != null) && (visao.getDescricaoVisao().length() > 0)))
	    listaVisaoGeral.addAll(pesquisar(visao, ordem));   
	    //Esta consulta será refeita na alteração da pesquisa de visão para colocar no padrão do e-car
	    //(transformar checkbox em radiobutton na consulta)
	    if ((situacoesFiltro!= null && situacoesFiltro.size() > 0) || (situacoesAlteracao != null && situacoesAlteracao.size() > 0)){
	    	Iterator itVisao = listaVisaoGeral.iterator();
	    	while (itVisao.hasNext()){
	    		removerVisao = false;
	    		VisaoDemandasVisDem vis = (VisaoDemandasVisDem) itVisao.next();
	    		List situacoesFiltroVisao = visaoSituacaoDemandaDao.getSituacoesVisao(vis, VisaoSituacaoDemandaDao.SITUACAO_VISAO_FILTRO);
	    		for (SitDemandaSitd sitFiltro : situacoesFiltro){
	    			if (!situacoesFiltroVisao.contains(sitFiltro)){
	    				removerVisao = true;
	    			}
	    		}
	    		List situacoesAlteracaoVisao = visaoSituacaoDemandaDao.getSituacoesVisao(vis, VisaoSituacaoDemandaDao.SITUACAO_VISAO_EDICAO);
	    		for (SitDemandaSitd sitAlteracao : situacoesAlteracao){
	    			if (!situacoesAlteracaoVisao.contains(sitAlteracao)){
	    				removerVisao = true;
	    			}
	    		}
	    		if (removerVisao){
	    			itVisao.remove();
	    		}
	    	}
	    }

	   	return listaVisaoGeral;
	}
		
}
