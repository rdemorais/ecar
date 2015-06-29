/*
 * Criado em 15/07/2005
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.UnidadeOrcamentariaPeriodoExercicioUoPerExe;
import ecar.pojo.UnidadeOrcamentariaUO;

/**
 * @author evandro
 *
 */
public class UnidadeOrcamentariaDao extends Dao{

	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public UnidadeOrcamentariaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Utiliza o método excluir da classe Dao realizando antes validações de relacionamento do
	 * órgão com registros em outras tabelas.
         * @param unidade
         * @throws ECARException
	 */
	public void excluir(UnidadeOrcamentariaUO unidade) throws ECARException {	    
	   try{
	       	boolean excluir = true;
		    if(contar(unidade.getItemEstruturaIettsByCodUo()) > 0){
		        excluir = false;
			    throw new ECARException("unidadeOrcamentaria.exclusao.erro.ItemEstruturaIetts");
		    }      			       
		    if(excluir)
		        super.excluir(unidade);
	   }catch(ECARException e){
		   this.logger.error(e);
	       throw e;
	   }    
	}

	/**
	 * Verifica duplicação depois salva
	 * @param unidade
	 * @throws ECARException
	 */
	public void salvar(UnidadeOrcamentariaUO unidade) throws ECARException {
		if (pesquisarDuplos(unidade, new String[] {"descricaoUo","siglaUo"}, "codUo").size() > 0)
		    throw new ECARException("unidadeOrcamentaria.validacao.registroDuplicado");
		if(unidade.getCodigoIdentUo() != null)
			if (pesquisarDuplos(unidade, new String[] {"codigoIdentUo"}, "codUo").size() > 0)
				throw new ECARException("unidadeOrcamentaria.validacao.registroDuplicado.codigoIdentUo");
		super.salvar(unidade);
	}
	
	/**
	 * Verifica duplicação depois altera
	 * @param unidade
	 * @throws ECARException
	 */
	public void alterar(UnidadeOrcamentariaUO unidade) throws ECARException {
		if (pesquisarDuplos(unidade, new String[] {"descricaoUo","siglaUo"}, "codUo").size() > 0)
		    throw new ECARException("unidadeOrcamentaria.validacao.registroDuplicado");
		if(unidade.getCodigoIdentUo() != null)
			if (pesquisarDuplos(unidade, new String[] {"codigoIdentUo"}, "codUo").size() > 0)
				throw new ECARException("unidadeOrcamentaria.validacao.registroDuplicado.codigoIdentUo");
		super.alterar(unidade);
	}
	
	/**
	 * Retorna uma lista de unidades orçamentárias de um órgão específico.
	 * 
	 * @author aleixo
	 * @since 03/07/2007
	 * @param orgao
         * @param campoOrdem
         * @return List
         * @throws ECARException
	 */
	public List getUnidadeOrcamentariaByOrgao(OrgaoOrg orgao, String campoOrdem) throws ECARException{
		StringBuilder select = new StringBuilder();
		
		select.append("select unidade from UnidadeOrcamentariaUO unidade");
		select.append(" where unidade.indAtivoUo = :indAtivo");
		
		if(orgao != null){
			select.append(" and unidade.orgaoOrg.codOrg = :codOrgao");
		}
		
		select.append(" order by unidade.");
		if(campoOrdem != null && !"".equals(campoOrdem)){
			select.append(campoOrdem);
		}
		else {
			select.append("descricaoUo");
		}
		select.append(" asc ");
		
		List retorno = new ArrayList();
		
		try{
		Query q = this.session.createQuery(select.toString());
		
		q.setString("indAtivo", "S");
		
		if(orgao != null){
			q.setLong("codOrgao", orgao.getCodOrg().longValue());
		}
		
		retorno = q.list();
		
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
		
		return (retorno != null) ? retorno : new ArrayList();
	}
	
    /**
     * Carrega listagem de Unidades Orcamentarias de acordo com o periodo
     * @param codPerExe Identificador <PeriodoExercicioPerExe>
     * @param orgId Identificador do Orgao
     * @param indAtivoPodPerExe Identificador de status ativo / inativo
     * @return ArrayList<UnidadeOrcamentariaUO>
     * @throws ECARException
     */
    public ArrayList<UnidadeOrcamentariaUO> getUnidadesByPeriodicidade( Long codPerExe, Long orgId,  Character indAtivoPodPerExe ) throws ECARException{
    	
    	try{
    		StringBuilder qry = new StringBuilder("from UnidadeOrcamentariaPeriodoExercicioUoPerExe as unidade")
									.append(" where unidade.indAtivoUoPerExe  = :status")
									.append(" and unidade.periodoExercicioPerExe.codPerExe  = :periodo")
									.append(" and unidade.unidadeOrcamentariaUO.orgaoOrg.codOrg  = :orgao")    							
									.append(" order by unidade.unidadeOrcamentariaUO.descricaoUo");
    		
    		Query q = this.session.createQuery(qry.toString());
    		q.setLong("periodo", codPerExe.longValue());
    		q.setLong("orgao", orgId.longValue());
    		q.setCharacter("status", indAtivoPodPerExe );   
    		
    		List unidadePerexe = q.list();
    		ArrayList<UnidadeOrcamentariaUO> listUnidade = new ArrayList<UnidadeOrcamentariaUO>();
    		
    		for (Iterator iter = unidadePerexe.iterator(); iter.hasNext();) {
    			UnidadeOrcamentariaPeriodoExercicioUoPerExe unidadeTmp = (UnidadeOrcamentariaPeriodoExercicioUoPerExe) iter.next();
    			listUnidade.add( unidadeTmp.getUnidadeOrcamentariaUO() );
			}
    		
    		return listUnidade;
    		
    	}
    	catch (HibernateException e){
    		this.logger.error(e);
    		throw new ECARException("erro.hibernateException");
    	}
    	    	
    }
	
    /**
     * Carrega listagem de Unidades Orcamentarias de acordo com o codigoIdentUo
     * @param codigoIdentUo Codigo e-COP
     * @return UnidadeOrcamentariaUO
     * @throws ECARException
     */
    public UnidadeOrcamentariaUO getUnidadeByCodigoIdentUo( Long codigoIdentUo  ) throws ECARException{
    	
    	try{
    		
    		StringBuilder qry = new StringBuilder("from UnidadeOrcamentariaUO as unidade")
									.append(" where unidade.codigoIdentUo  = :codigo");
    		Query q = this.session.createQuery(qry.toString());
    		q.setLong("codigo", codigoIdentUo.longValue());   
    		q.setMaxResults(1);
    		
    		UnidadeOrcamentariaUO unidade = (UnidadeOrcamentariaUO)q.uniqueResult();
    		
    		return unidade;
    		
    	}
    	catch (HibernateException e){
    		this.logger.error(e);
    		throw new ECARException("erro.hibernateException");
    	}
    	    	
    }	
    
	
}
