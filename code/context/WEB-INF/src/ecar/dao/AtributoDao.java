/*
 * Criado em 16/11/2004
 *
 */
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
import org.hibernate.criterion.Restrictions;

import comum.database.Dao;
import comum.util.ConstantesECAR;
import comum.util.Pagina;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.pojo.AtributosAtb;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.FuncaoFun;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;

/**
 * @author felipev
 *  
 */
public class AtributoDao extends Dao {

    /**
     * Construtor. Chama o Session factory do Hibernate
     * @param request
     */
    public AtributoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}

    /**
     * Devolve um conjunto com todos os atributos ativos nao opcionais, ou seja,
     * que são obrigatorios em todas as estruturas
     * 
     * @return
     * @throws ECARException
     */
    public List getAtributosNaoOpcionais() throws ECARException {
        List retorno = new ArrayList();

        AtributosAtb atributo = new AtributosAtb();

        atributo.setIndAtivoAtb("S");
        atributo.setIndOpcionalAtb("N");

        retorno = this.pesquisar(atributo, null);

        return retorno;
    }
    
    /**
     * Devolve um conjunto com todos os atributos ativos nao opcionais, ou seja,
     * que são obrigatorios em todas as estruturas
     * 
     * @param funcao 
     * @return
     * @throws ECARException
     */
    public List<AtributosAtb> getAtributosNaoOpcionais(FuncaoFun funcao) throws ECARException {
    	

    	try {	
    			String hql = MessageFormat.format(Util.getHql(
    					ConstantesECAR.PESQUISA_ATRIBUTOS_NAO_OPCIONAIS_ATIVOS_FUNCAO, 
    					request.getSession().getServletContext()), "\'N\'", "\'S\'", funcao.getCodFun().toString());
    			
    			StringBuilder query = new StringBuilder(hql);

    			Query q = this.getSession().createQuery(query.toString());

    			return q.list();
    			
    	} catch(IOException e){
    			this.logger.error(e);
    	        throw new ECARException(e);
    	}
    		
		
    }
    
    /**
     * Devolve um conjunto com todos os atributos ativos nao opcionais, ou seja,
     * que são obrigatorios em todas as estruturas
     * 
     * @param estruturasFuncoes 
     * @return
     * @throws ECARException
     */
    public List getAtributosNaoOpcionais(Set<EstruturaFuncaoEttf> estruturasFuncoes) throws ECARException {
    	
    	List<AtributosAtb> atributos = null;
    	
    	atributos = new ArrayList<AtributosAtb>();
	    if (estruturasFuncoes!=null) {
	    	
	    	for (Iterator<EstruturaFuncaoEttf> iterator = estruturasFuncoes.iterator(); iterator.hasNext();) {
				EstruturaFuncaoEttf element = (EstruturaFuncaoEttf) iterator.next();
				atributos.addAll(getAtributosNaoOpcionais(element.getFuncaoFun()));
			}
    	}
    		
    	return atributos;
		
    }

    /**
     * Devolve um conjunto com todos os atributos ativos opcionais
     * 
     * @param funcaoFun 
     * @return
     * @throws ECARException
     */
    public List getAtributosOpcionais(FuncaoFun funcaoFun) throws ECARException {
        List retorno = new ArrayList();

        AtributosAtb atributo = new AtributosAtb();

        atributo.setIndAtivoAtb("S");
        atributo.setIndOpcionalAtb("S");
        atributo.setFuncaoFun(funcaoFun);

        retorno = this.pesquisar(atributo, null);

        return retorno;
    }

    /**
     * Exclui um atributo, verificando antes se ele possui relação com outras
     * tabelas. Neste caso, não permite exclusão
     * 
     * @param atributo
     * @throws ECARException
     */
    public void excluir(AtributosAtb atributo) throws ECARException {
        try {
            boolean excluir = true;

            if (contar(atributo.getEstruturaAtributoEttats()) > 0) {
                excluir = false;
                throw new ECARException(
                        "atributo.exclusao.erro.estruturaAtributoEttats");
            }
            if (excluir)
                super.excluir(atributo);
        } catch (ECARException e) {
        	this.logger.error(e);
            throw e;
        }
    }

    /**
     *
     * @param atributo
     * @throws ECARException
     */
    public void salvar(AtributosAtb atributo) throws ECARException {

    	if (atributo.getSisGrupoAtributoSga().getSisAtributoSatbs() == null || atributo.getSisGrupoAtributoSga().getSisAtributoSatbs().isEmpty()){
    		throw new ECARException("atributo.validacao.grupoAtributoSemAtributo");
    	}
    	
    	SisAtributoSatb atributoLivre = (SisAtributoSatb)atributo.getSisGrupoAtributoSga().getSisAtributoSatbs().iterator().next();
    	
    	//O campo de 'Atributo opcional' deve ser marcado caso seja atributo do tipo ID ou Mascara
    	if (atributoLivre.isAtributoAutoIcremental() || atributoLivre.isAtributoContemMascara()){
    		if (atributo.getIndOpcionalAtb() == null || atributo.getIndOpcionalAtb().equals(Pagina.NAO)){
    			throw new ECARException("atributo.validacao.atributoIDOpcional");
    		}
    	}
    	
    	// Só deve fazer as validações de atributo duplicado se for diferente de atributo livre do tipo ID.
		if(pesquisarDuplos(atributo, new String[] {"nomeAtb","labelPadraoAtb"}, "codAtb").size() > 0)
		    throw new ECARException("atributo.validacao.registroDuplicado");
		
		if(atributo.getIndAtivoAtb() != null && "S".equals(atributo.getIndAtivoAtb())){
			if (!pesquisarDuplosAtributosAtb(atributo).isEmpty())
			    throw new ECARException("atributo.validacao.registroDuplicadoAtivo");
		}
		
		super.salvar(atributo);
	}
    
    /**
     *
     * @param atributo
     * @throws ECARException
     */
    public void alterar(AtributosAtb atributo) throws ECARException {

    	if (atributo.getSisGrupoAtributoSga() != null && (atributo.getSisGrupoAtributoSga().getSisAtributoSatbs() == null || atributo.getSisGrupoAtributoSga().getSisAtributoSatbs().isEmpty())){
    		throw new ECARException("atributo.validacao.grupoAtributoSemAtributo");
    	} else if (atributo.getSisGrupoAtributoSga() != null && atributo.getSisGrupoAtributoSga().getSisAtributoSatbs() != null && !atributo.getSisGrupoAtributoSga().getSisAtributoSatbs().isEmpty()) {
    	
	    	SisAtributoSatb atributoLivre = (SisAtributoSatb)atributo.getSisGrupoAtributoSga().getSisAtributoSatbs().iterator().next();
	    	
	    	//O campo de 'Atributo opcional' deve ser marcado caso seja atributo do tipo ID ou Mascara
	    	if (atributoLivre.isAtributoAutoIcremental() || atributoLivre.isAtributoContemMascara()){
	    		if (atributo.getIndOpcionalAtb() == null || atributo.getIndOpcionalAtb().equals(Pagina.NAO)){
	    			throw new ECARException("atributo.validacao.atributoIDOpcional");
	    		}
	    	}
	    	
	    	// Só deve fazer as validações de atributo duplicado se for diferente de atributo livre do tipo ID.
			if(pesquisarDuplos(atributo, new String[] {"nomeAtb","labelPadraoAtb"}, "codAtb").size() > 0)
			    throw new ECARException("atributo.validacao.registroDuplicado");
			
			if(atributo.getIndAtivoAtb() != null && "S".equals(atributo.getIndAtivoAtb())){
				if (!pesquisarDuplosAtributosAtb(atributo).isEmpty())
				    throw new ECARException("atributo.validacao.registroDuplicadoAtivo");
			}
    	}
		
		super.alterar(atributo);
	}
    
    /**
     *
     * @param atributo
     * @return
     * @throws ECARException
     */
    public List<AtributosAtb> pesquisarDuplosAtributosAtb(AtributosAtb atributo/*, SisGrupoAtributoSga sisGrupo, String indAtivoAtb*/) throws ECARException {
    	
    	//boolean retorno = false;
    	SisGrupoAtributoSga sisGrupo = atributo.getSisGrupoAtributoSga();
    	SisAtributoSatb sisAtributo = null;
    	Long codSga = 0L;
    	String indAtivoAtb = atributo.getIndAtivoAtb();
    	Long codAtb = 0L;
    	
    	
		//Para atributos do tipo ID um grupo de atributo livre deve ter um e somente um atributo livre, portanto podemos utilizar o next do Iterator.
		//Esse atributo será utilizado para verificar se o mesmo é do tipo ID.
		if (atributo.getCodAtb() != null){
			codAtb = atributo.getCodAtb();
		}
		
		if (sisGrupo != null) {
			codSga = sisGrupo.getCodSga();
			sisAtributo = (SisAtributoSatb)sisGrupo.getSisAtributoSatbs().iterator().next();
		}

    	
    	try {
    	
	    	Criteria crits = session.createCriteria(AtributosAtb.class);
 
	    	crits.add(Restrictions.eq("sisGrupoAtributoSga.codSga",codSga));
	    	crits.add(Restrictions.eq("indAtivoAtb", indAtivoAtb));
	    	crits.add(Restrictions.ne("codAtb", codAtb)); // != codAtb
	    	if (sisAtributo != null && (sisAtributo.isAtributoAutoIcremental() || sisAtributo.isAtributoContemMascara())){
	    		crits.add(Restrictions.eq("funcaoFun", atributo.getFuncaoFun())); 
	    	}
	    	
	    	
	    	List<AtributosAtb> lista = (List<AtributosAtb>)crits.list();
	    	
//	    	if (lista.size() > 0) {
//	    		retorno = true;
//	    	}
	    	
	    	return lista;
    	
		}catch (Exception e) {
	        this.logger.error(e);
	    	throw new ECARException("erro.hibernateException");
		}
    }
    
    /**
     *
     * @param request
     * @param atributo
     * @param usarGetParamStr
     * @throws ECARException
     */
    public void setAtributosAtb(HttpServletRequest request, AtributosAtb atributo, boolean usarGetParamStr) throws ECARException{
    	
    	if(Pagina.getParam(request, "codigo") != null)
			atributo.setCodAtb(Long.valueOf (Pagina.getParam(request, "codigo")));
    	else
			atributo.setCodAtb(null);
    	
    	if(usarGetParamStr){
			atributo.setNomeAtb(Pagina.getParamStr(request, "nomeAtb").trim());
			atributo.setLabelPadraoAtb(Pagina.getParamStr(request, "labelPadraoAtb").trim());
			atributo.setCodFkAtb(Pagina.getParamStr(request, "codFkAtb").trim());
			atributo.setNomeFkAtb(Pagina.getParamStr(request, "nomeFkAtb").trim());
			atributo.setIndAtivoAtb(Pagina.getParamOrDefault(request, "indAtivoAtb", Pagina.NAO));
			atributo.setIndExclusivoEstruturaAtb(Pagina.getParamOrDefault(request, "indExclusivoEstruturaAtb", Pagina.NAO));
			atributo.setIndOpcionalAtb(Pagina.getParamOrDefault(request, "indOpcionalAtb", Pagina.NAO));
			atributo.setDocumentacaoAtb(Pagina.getParamStr(request,"documentacaoAtb").trim());
			
			if(!"".equals(Pagina.getParamStr(request, "funcaoFun"))){
				atributo.setFuncaoFun((FuncaoFun) new FuncaoDao(request).buscar(FuncaoFun.class, Long.valueOf(Pagina.getParamStr(request, "funcaoFun"))));
			}
			else {
				atributo.setFuncaoFun(null);
			}
    	}
    	else{
			atributo.setNomeAtb(Pagina.getParam(request, "nomeAtb"));
			atributo.setLabelPadraoAtb(Pagina.getParam(request, "labelPadraoAtb"));
			atributo.setCodFkAtb(Pagina.getParam(request, "codFkAtb"));
			atributo.setNomeFkAtb(Pagina.getParam(request, "nomeFkAtb"));
			atributo.setIndAtivoAtb(Pagina.getParam(request, "indAtivoAtb"));
			atributo.setIndExclusivoEstruturaAtb(Pagina.getParam(request, "indExclusivoEstruturaAtb"));
			atributo.setIndOpcionalAtb(Pagina.getParam(request, "indOpcionalAtb"));
			atributo.setDocumentacaoAtb(Pagina.getParam(request,"documentacaoAtb"));
			if(!"".equals(Pagina.getParamStr(request, "funcaoFun"))){
				atributo.setFuncaoFun((FuncaoFun) new FuncaoDao(request).buscar(FuncaoFun.class, Long.valueOf(Pagina.getParamStr(request, "funcaoFun"))));
			}
			else {
				atributo.setFuncaoFun(null);
			}
    	}
    }
    
    /**
     * Retorna o atributoAtb pelo nomeAtb
     * @param nomeAtb
     * @return
     */
    public AtributosAtb getAtributosAtbByNomeAtb(String nomeAtb) throws ECARException{
    	AtributosAtb atributosAtb = null;
       	try {
    		String hql = "select atributosAtb from AtributosAtb atributosAtb where atributosAtb.nomeAtb = :nomeAtb";
			Query q = this.session.createQuery(hql);
			q.setString("nomeAtb", nomeAtb);
			q.setMaxResults(1);
			atributosAtb = (AtributosAtb) q.uniqueResult();
    	} catch(HibernateException e) {
            this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
    	}
    	return atributosAtb;
    }
}