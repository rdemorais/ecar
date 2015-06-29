/*
 * Criado em 28/10/2004
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

import ecar.bean.AtributoEstruturaListagemItens;
import ecar.exception.ECARException;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.OrgaoPeriodoExercicioOrgPerExe;
import ecar.pojo.UsuarioUsu;
import ecar.servlet.relatorio.dto.CMSecretariaDTO;
import ecar.util.Dominios;

/**
 * @author felipev
 *
 */
public class OrgaoDao extends Dao{

	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public OrgaoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Utiliza o m�todo excluir da classe Dao realizando antes valida��es de relacionamento do
	 * �rg�o com registros em outras tabelas.
         * @param orgao
	 * @throws ECARException
	 */
	public void excluir(OrgaoOrg orgao) throws ECARException {	    
	   try{
	       	boolean excluir = true;
		    if(contar(orgao.getAcompReferenciaArefs()) > 0){
		        excluir = false;
			    throw new ECARException("orgao.exclusao.erro.acompReferenciaArefs");
		    }      			       
		    if(contar(orgao.getItemEstruturaIettsByCodOrgaoResponsavel1Iett()) > 0){
		        excluir = false;
		        throw new ECARException("orgao.exclusao.erro.itemEstruturaIettsByCodOrgaoResponsavel1Iett");			       
			}
		    if(contar(orgao.getItemEstruturaIettsByCodOrgaoResponsavel2Iett()) > 0){
		        excluir = false;
		        throw new ECARException("orgao.exclusao.erro.itemEstruturaIettsByCodOrgaoResponsavel2Iett");			       
			}
		    if(contar(orgao.getUsuarioUsus()) > 0){
		        excluir = false;
		        throw new ECARException("orgao.exclusao.erro.usuarioUsus");			       
			}
		    if(contar(orgao.getRegDemandaRegds()) > 0){
		        excluir = false;
		        throw new ECARException("orgao.exclusao.erro.regDemandaRegds");			       
			}
		    
		    if(excluir)
		        super.excluir(orgao);
	   }catch(ECARException e){
		   this.logger.error(e);
	       throw e;
	   }    
	}

	/**
	 * verifica duplica��o depois salva 
	 * @param orgao
	 * @throws ECARException
	 */
	public void salvar(OrgaoOrg orgao) throws ECARException {
		if (pesquisarDuplos(orgao, new String[] {"descricaoOrg","siglaOrg"}, "codOrg").size() > 0)
		    throw new ECARException("orgao.validacao.registroDuplicado");
		if(orgao.getCodigoIdentOrg() != null)
			if (pesquisarDuplos(orgao, new String[] {"codigoIdentOrg"}, "codOrg").size() > 0)
			    throw new ECARException("orgao.validacao.registroDuplicado.codigoIdentOrg");
		super.salvar(orgao);
	}
	
	/**
	 * verifica duplica��o depois altera
	 * @param orgao
	 * @throws ECARException
	 */
	public void alterar(OrgaoOrg orgao) throws ECARException {
		if (pesquisarDuplos(orgao, new String[] {"descricaoOrg","siglaOrg"}, "codOrg").size() > 0)
		    throw new ECARException("orgao.validacao.registroDuplicado");
		if(orgao.getCodigoIdentOrg() != null)
			if (pesquisarDuplos(orgao, new String[] {"codigoIdentOrg"}, "codOrg").size() > 0)
				throw new ECARException("orgao.validacao.registroDuplicado.codigoIdentOrg");
		super.alterar(orgao);
	}
	
	
	/**
	 *  Retorna lista de org�o respons�veis de itemEstrutura 
	 *  
	 * @param listItemEstruturaIett
	 * @return List
	 * @throws ECARException
	 */
    public List getOrgaosRespItemEstrutura(List listItemEstruturaIett) throws ECARException{
        try{    
        	List codigosOrgaos = new ArrayList();
        	List orgaos = new ArrayList();
        	Iterator it = listItemEstruturaIett.iterator();
        	
        	while(it.hasNext()) {
        		ItemEstruturaIett item = (ItemEstruturaIett) it.next();
        		
        		OrgaoOrg org = null;

				if(item.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
					org = item.getOrgaoOrgByCodOrgaoResponsavel1Iett();	
				} else {
					/* Se n�o possuir orgao procura orgao do pai */
					ItemEstruturaIett itemAux = item;
					while(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett() == null && itemAux.getItemEstruturaIett() != null){
						itemAux = itemAux.getItemEstruturaIett();
					}
					if(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
						org = itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett();											
					}
				}
				
				if(org != null && !codigosOrgaos.contains(org.getCodOrg().toString())) {
					codigosOrgaos.add(org.getCodOrg().toString());
					orgaos.add(org);
				}
        	}
        	
        	return orgaos;
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException(e);            
        }
    }
    /*
    M�todo anterior - com problema
    
    public List getOrgaosRespItemEstrutura(Long codAref) throws ECARException{
        try{        
        	String query = "select distinct orgao from OrgaoOrg orgao " +
                        "join orgao.itemEstruturaIettsByCodOrgaoResponsavel1Iett item " +
						"join item.acompReferenciaItemAris ari " +
						"join ari.acompReferenciaAref aref ";
			String where = "where orgao.indAtivoOrg = 'S' ";
			String order = "order by orgao.siglaOrg";
			
			if(codAref != null)
				where += "and aref.codAref = :codAref  ";
			
            Query q = this.getSession().createQuery(query + where + order);
            
            if(codAref != null)
            	q.setLong("codAref", codAref.longValue());
                        
            return q.list();

        } catch(HibernateException e){
        	this.logger.error(e);
            throw new ECARException(e);            
        }
    }
    */
    
    /**
     * Retorna lista de org�o respons�veis de itemEstrutura para a rela��o de itens da tela do Relat�rio 
     * @param listItemEstruturaIett
     * @return
	 * @throws ECARException
     */
    public List getOrgaosRespItemEstruturaRelatorio(List listItemEstruturaIett) throws ECARException{
        try{    
        	List codigosOrgaos = new ArrayList();
        	List orgaos = new ArrayList();
        	Iterator it = listItemEstruturaIett.iterator();
        	
        	while(it.hasNext()) {
        		
        		AtributoEstruturaListagemItens aeIett = (AtributoEstruturaListagemItens) it.next();
        		//ItemEstruturaIett item = (ItemEstruturaIett) it.next();
        		ItemEstruturaIett item = aeIett.getItem();
        		
        		OrgaoOrg org = null;

				if(item.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
					org = item.getOrgaoOrgByCodOrgaoResponsavel1Iett();	
				} else {
					/* Se n�o possuir orgao procura orgao do pai */
					ItemEstruturaIett itemAux = item;
					while(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett() == null && itemAux.getItemEstruturaIett() != null){
						itemAux = itemAux.getItemEstruturaIett();
					}
					if(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
						org = itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett();											
					}
				}
				
				if(org != null && !codigosOrgaos.contains(org.getCodOrg().toString())) {
					codigosOrgaos.add(org.getCodOrg().toString());
					orgaos.add(org);
				}
        	}
        	
        	return orgaos;
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException(e);            
        }
    }
    
	/**
     * Retorna lista de org�o respons�vel 2 de itemEstrutura 
         * @param listItemEstruturaIett
         * @return
	 * @throws ECARException
	 */
    public List getOrgaosResp2ItemEstrutura(List listItemEstruturaIett) throws ECARException{
        try{    
        	List codigosOrgaos = new ArrayList();
        	List orgaos = new ArrayList();
        	Iterator it = listItemEstruturaIett.iterator();
        	
        	while(it.hasNext()) {
        		ItemEstruturaIett item = (ItemEstruturaIett) it.next();
        		
        		OrgaoOrg org = null;

				if(item.getOrgaoOrgByCodOrgaoResponsavel2Iett() != null){
					org = item.getOrgaoOrgByCodOrgaoResponsavel2Iett();	
				} else {
					/* Se n�o possuir orgao procura orgao do pai */
					ItemEstruturaIett itemAux = item;
					while(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel2Iett() == null && itemAux.getItemEstruturaIett() != null){
						itemAux = itemAux.getItemEstruturaIett();
					}
					if(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel2Iett() != null){
						org = itemAux.getOrgaoOrgByCodOrgaoResponsavel2Iett();											
					}
				}
				
				if(org != null && !codigosOrgaos.contains(org.getCodOrg().toString())) {
					codigosOrgaos.add(org.getCodOrg().toString());
					orgaos.add(org);
				}
        	}
        	
        	return orgaos;
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException(e);            
        }
    }
    

    /**
     * Carrega listagem de Orgaos de acordo com o periodo, status e Poder
     * @param codPerExe Identificador <PeriodoExercicioPerExe>
     * @param poderId Identificador do Poder
     * @param indAtivoPodPerExe Identificador de status Ativo / Inativo
     * @return ArrayList<OrgaoOrg>
     * @throws ECARException
     */
    public ArrayList<OrgaoOrg> getOrgaoByPeriodicidade( Long codPerExe, Long poderId,  Character indAtivoPodPerExe ) throws ECARException{
    	
    	try{
    		StringBuilder qry = new StringBuilder("from OrgaoPeriodoExercicioOrgPerExe as orgao")
								.append(" where orgao.indAtivoOrgPerExe  = :status")
    							.append(" and orgao.periodoExercicioPerExe.codPerExe  = :periodo")
    							.append(" and orgao.orgaoOrg.poderPod.codPod  = :poder")    							
    							.append(" order by orgao.orgaoOrg.descricaoOrg");
    		
    		Query q = this.session.createQuery(qry.toString());
    		q.setLong("periodo", codPerExe.longValue());
    		q.setLong("poder", poderId.longValue());
    		q.setCharacter("status", indAtivoPodPerExe );  
    		
    		List orgaoPerexe = q.list();
    		ArrayList<OrgaoOrg> listOrgao = new ArrayList<OrgaoOrg>();
    		
    		for (Iterator iter = orgaoPerexe.iterator(); iter.hasNext();) {
    			OrgaoPeriodoExercicioOrgPerExe orgTmp = (OrgaoPeriodoExercicioOrgPerExe) iter.next();
    			listOrgao.add( orgTmp.getOrgaoOrg() );
			}
    		
    		return listOrgao;
    		
    	}
    	catch (HibernateException e){
    		this.logger.error(e);
    		throw new ECARException("erro.hibernateException");
    	}
    	    	
    }
    
    /**
     * Carrega listagem de Orgaos de acordo com o periodo e status
     * @param codPerExe Identificador <PeriodoExercicioPerExe>
     * @param indAtivoPodPerExe Identificador de status Ativo / Inativo
     * @return ArrayList<OrgaoOrg>
     * @throws ECARException
     */
    public ArrayList<OrgaoOrg> getOrgaoByPeriodicidade( Long codPerExe,  Character indAtivoPodPerExe ) throws ECARException{
    	
    	try{
    		StringBuilder qry = new StringBuilder("from OrgaoPeriodoExercicioOrgPerExe as orgao")
								.append(" where orgao.indAtivoOrgPerExe  = :status")
    							.append(" and orgao.periodoExercicioPerExe.codPerExe  = :periodo")						
    							.append(" order by orgao.orgaoOrg.descricaoOrg");
    		
    		Query q = this.session.createQuery(qry.toString());
    		q.setLong("periodo", codPerExe.longValue());
    		q.setCharacter("status", indAtivoPodPerExe );  
    		
    		List orgaoPerexe = q.list();
    		ArrayList<OrgaoOrg> listOrgao = new ArrayList<OrgaoOrg>();
    		
    		for (Iterator iter = orgaoPerexe.iterator(); iter.hasNext();) {
    			OrgaoPeriodoExercicioOrgPerExe orgTmp = (OrgaoPeriodoExercicioOrgPerExe) iter.next();
    			listOrgao.add( orgTmp.getOrgaoOrg() );
			}
    		
    		return listOrgao;
    		
    	}
    	catch (HibernateException e){
    		this.logger.error(e);
    		throw new ECARException("erro.hibernateException");
    	}
    	    	
    }
    
    /**
	 * 
	 * @param descricaoOrgao
	 * @return
	 */
	public OrgaoOrg getOrgaoOrgByDescricao(String descricaoOrgao) throws ECARException{
		OrgaoOrg orgaoOrg = null;
		try {
			String hql = "select orgaoOrg from OrgaoOrg orgaoOrg where orgaoOrg.descricaoOrg = :descricaoOrg";
			Query q = this.session.createQuery(hql);
			q.setString("descricaoOrg", descricaoOrgao);
			q.setMaxResults(1);
			orgaoOrg = (OrgaoOrg) q.uniqueResult();
		} catch(HibernateException e) {
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
		return orgaoOrg;
	}
	
    /**
	 * 
	 * @param descricaoOrgao
	 * @return
	 */
	public List getListaOrgaos(boolean apenasOrgaosAtivos) throws ECARException{
		List orgaos = new ArrayList();
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("select orgaoOrg from OrgaoOrg orgaoOrg ");
			if (apenasOrgaosAtivos){
				hql.append("where indAtivoOrg = :indAtivoOrg ");
			}
			hql.append("order by descricaoOrg");
			
			Query q = this.session.createQuery(hql.toString());
			if (apenasOrgaosAtivos){
				q.setString("indAtivoOrg", "S");
			} 
    		orgaos = q.list();
		} catch(HibernateException e) {
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
		return orgaos;
	}
    
	
	/**
	 * Retorna os org�os ativos mais os org�os ativos ou inativos que estejam associados a um usu�rio, caso o usu�rio seja informado.
	 * @param usuario Opcional
	 * @return
	 * @throws ECARException 
	 */
	public List<OrgaoOrg> consultarOrgaosAtivosOuAssociadoUsuario (UsuarioUsu usuario) throws ECARException{
		List<OrgaoOrg> orgaos = new ArrayList<OrgaoOrg>();
		try {
			StringBuffer hql = new StringBuffer();
			
			hql.append("from OrgaoOrg orgao where indAtivoOrg = 'S' ");
			
			if (usuario != null){
				hql.append(" or orgao in (select org from OrgaoOrg as org right outer join org.usuarioUsus usu where usu.codUsu = :usuario )");
			}
			
			hql.append(" order by orgao.descricaoOrg ");
			 
			Query q = this.session.createQuery(hql.toString());
			
			
			if (usuario != null){
				q.setEntity("usuario", usuario);
			}
			
    		orgaos = q.list();
		} catch(HibernateException e) {
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
		return orgaos;
		
	}
	
	/**
	 * Retorna a lista de todos os org�os que est�o associados a algum item da estrutura
	 * @return
	 */
	public List<OrgaoOrg> consultarOrgaosDosItens() {
		String hql = "SELECT new ecar.servlet.relatorio.dto.CMSecretariaDTO(" +
				"item.orgaoOrgByCodOrgaoResponsavel1Iett.siglaOrg, " +
				"count(item.codIett), " +
				"count(item.codIett)) " +
				"FROM ItemEstruturaIett item " +
				"WHERE item.indAtivoIett = 'S' " +
				"AND item.estruturaEtt.codEtt = 15 " +
				"AND item.acompReferenciaItemAris.acompRelatorioArels.cor IS NOT NULL " +
				"GROUP BY item.orgaoOrgByCodOrgaoResponsavel1Iett.siglaOrg";
		
		Query q = this.session.createQuery(hql);
		List<CMSecretariaDTO> secretarias =  q.list();
		for(CMSecretariaDTO dto : secretarias) {
			System.out.println(dto.getNomeSecretaria() + " :: " + dto.getTotal() + " :: " + dto.getTotalMon());
		}
		return null;
	}
	
	/**
	 * Retorna os org�os ativos mais o org�o ativo ou inativo que estejam associado ao item, caso o item seja informado.
	 * @param itemEstrutura Opcional
	 * @return
	 * @throws ECARException 
	 */
	public List<OrgaoOrg> consultarOrgaosAtivosOuAssociadoItem (ItemEstruturaIett itemEstrutura) throws ECARException{
		List<OrgaoOrg> orgaos = new ArrayList<OrgaoOrg>();
		try {
			StringBuffer hql = new StringBuffer();
			
			hql.append("from OrgaoOrg orgao where indAtivoOrg = 'S' ");
			
			if (itemEstrutura != null){
				hql.append(" or orgao = (select item.orgaoOrgByCodOrgaoResponsavel1Iett from ItemEstruturaIett as item where item.codIett = :itemEstrutura ) ");
			}
			
			hql.append(" order by orgao.descricaoOrg ");
			 
			Query q = this.session.createQuery(hql.toString());
			
			
			if (itemEstrutura != null){
				q.setEntity("itemEstrutura", itemEstrutura);
			}
			
    		orgaos = q.list();
		} catch(HibernateException e) {
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
		return orgaos;
		
	}
	
	/**
	 * Retorna os �rg�os do usu�rio
	 * @param usuarioUsu
	 * @param apenasOrgaosAtivos
	 * @return
	 */
	public List<OrgaoOrg> getListaOrgaosUsuario(UsuarioUsu usuarioUsu, boolean apenasOrgaosAtivos){
		List<OrgaoOrg> orgaos = new ArrayList<OrgaoOrg>();
		if (apenasOrgaosAtivos){
			for (Object object : usuarioUsu.getOrgaoOrgs()) {
				OrgaoOrg orgao = (OrgaoOrg) object;
				if (orgao.getIndAtivoOrg().equals(Dominios.ATIVO)){
					orgaos.add(orgao);
				}
			}
		} else {
			orgaos.addAll(usuarioUsu.getOrgaoOrgs());
		}
		return orgaos;
	}
	
	
	/**
	 * Retorna os ids das sub secretarias por secretaria.
	 * @param String Opcional
	 * @return
	 * @throws ECARException 
	 */
	@SuppressWarnings("unchecked")
	public List<Long> listarIdsSecretarias (String secretaria) {
		List<Long> ids = new ArrayList<Long>();		
		StringBuffer hql = new StringBuffer();
		
		hql.append("select orgao.codOrg from OrgaoOrg orgao where indAtivoOrg = 'S' ");						
		hql.append("and orgao.siglaOrg = :secretaria ");
		 
		Query q = this.session.createQuery(hql.toString());						
		q.setString("secretaria", secretaria);
		
		ids = q.list();		
		
		return ids;	
	}
}
