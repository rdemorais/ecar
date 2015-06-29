package ecar.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.AtributoDemandaAtbdem;
import ecar.pojo.VisaoAtributoDemanda;
import ecar.pojo.VisaoDemandasVisDem;


/**
 * Classe de acesso ao Banco para a entidade Visao.
 *
 */
public class VisaoAtributoDemandaDao extends Dao {
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public VisaoAtributoDemandaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Visão 
         * @param visaoAtributoDemanda
         * @throws ECARException
	 */
	public void salvar(VisaoAtributoDemanda visaoAtributoDemanda) throws ECARException {
    	
		if(pesquisarDuplos(visaoAtributoDemanda))
		    throw new ECARException("atributo.validacao.registroDuplicado");
		
		if (visaoAtributoDemanda.getIndRestritivo().equals("S") && 
				(visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().getSisGrupoAtributoSga() == null || 
				!visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().getSisGrupoAtributoSga().getIndCadUsuSga().equals("S"))){
			throw new ECARException("atributo.validacao.indRestritivoInvalido");
		}
		
		if (visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().getSisGrupoAtributoSga() == null){
			validarNomeAtributoVisao(visaoAtributoDemanda);
		}
		
		
		super.salvar(visaoAtributoDemanda);
	}
	
	/**
	 * Visão 
         * @param visaoAtributoDemanda
         * @throws ECARException
	 */
	public void alterar(VisaoAtributoDemanda visaoAtributoDemanda) throws ECARException {
    				
		if (visaoAtributoDemanda.getIndRestritivo().equals("S") && 
				(visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().getSisGrupoAtributoSga() == null || 
				!visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().getSisGrupoAtributoSga().getIndCadUsuSga().equals("S"))){
			throw new ECARException("atributo.validacao.indRestritivoInvalido");
		}
				
		super.alterar(visaoAtributoDemanda);
	}
	
	

	/**
	 * Excluir visão
         * @param visaoAtributoDemanda
         * @throws ECARException
	 */
	public void excluir(VisaoAtributoDemanda visaoAtributoDemanda) throws ECARException {
		try {
			boolean excluir = true;
	       
			if(excluir)
				super.excluir(visaoAtributoDemanda);	
		}catch(ECARException e){
			this.logger.error(e);
			throw e;
		}    
	}
	
        /**
         *
         * @param visao
         * @throws ECARException
         */
        public void alterarSemLazy(VisaoAtributoDemanda visao) throws ECARException {
	
		super.alterar(visao);
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
	 * Recupera todas os atributos visões demanda
         * @param visaoAtributoDemanda
         * @return
	 * @throws ECARException
	 */
	public List getVisoesAtributoDemanda(VisaoAtributoDemanda visaoAtributoDemanda) throws ECARException{
		
		if (visaoAtributoDemanda!=null) {
			Criteria select;
			select = session.createCriteria(visaoAtributoDemanda.getClass());
			
			if (visaoAtributoDemanda.getVisaoAtributoDemandaPk()!=null && visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao()!=null)
				select.add(Restrictions.eq("visaoAtributoDemandaPk.visao.codVisao", visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao().getCodVisao()));
			
			if (visaoAtributoDemanda.getVisaoAtributoDemandaPk()!=null && visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda()!=null)
				select.add(Restrictions.eq("visaoAtributoDemandaPk.atributoDemanda.codAtbdem", visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().getCodAtbdem()));

			if (visaoAtributoDemanda.getIndListagemItensAtbvis()!=null)
				select.add(Restrictions.eq("indListagemItensAtbvis", visaoAtributoDemanda.getIndListagemItensAtbvis()));

			if (visaoAtributoDemanda.getIndFiltroAtbvis()!=null)
				select.add(Restrictions.eq("indFiltroAtbvis", visaoAtributoDemanda.getIndFiltroAtbvis()));

			if (visaoAtributoDemanda.getSeqApresListagemTelaAtbvis()!=null)
				select.add(Restrictions.eq("seqApresListagemTelaAtbvis", visaoAtributoDemanda.getSeqApresListagemTelaAtbvis()));

			if (visaoAtributoDemanda.getLarguraListagemTelaAtbvis()!=null)
				select.add(Restrictions.eq("larguraListagemTelaAtbvis", visaoAtributoDemanda.getLarguraListagemTelaAtbvis()));

			if (visaoAtributoDemanda.getIndExibivelConsultaAtbvis()!=null)
				select.add(Restrictions.eq("indExibivelConsultaAtbvis", visaoAtributoDemanda.getIndExibivelConsultaAtbvis()));

			if (visaoAtributoDemanda.getIndExibivelAtbvis()!=null)
				select.add(Restrictions.eq("indExibivelAtbvis", visaoAtributoDemanda.getIndExibivelAtbvis()));

			if (visaoAtributoDemanda.getIndEditavelAtbvis()!=null)
				select.add(Restrictions.eq("indEditavelAtbvis", visaoAtributoDemanda.getIndEditavelAtbvis()));

			if (visaoAtributoDemanda.getIndObrigatorioAtbvis()!=null)
				select.add(Restrictions.eq("indObrigatorioAtbvis", visaoAtributoDemanda.getIndObrigatorioAtbvis()));
			
			if (visaoAtributoDemanda.getIndRestritivo()!=null)
				select.add(Restrictions.eq("indRestritivo", visaoAtributoDemanda.getIndRestritivo()));

			if (visaoAtributoDemanda.getSeqApresTelaCampoAtbvis()!=null)
				select.add(Restrictions.eq("seqApresTelaCampoAtbvis", visaoAtributoDemanda.getSeqApresTelaCampoAtbvis()));

			if (visaoAtributoDemanda.getDicaAtbvis()!=null && visaoAtributoDemanda.getDicaAtbvis().length()>0)
				select.add(Restrictions.eq("dicaAtbvis", visaoAtributoDemanda.getDicaAtbvis()));
			
			return select.list();
		
		} else {
			return super.listar(VisaoAtributoDemanda.class, null);
		}
	}

	
        /**
         *
         * @param obj
         * @return
         * @throws ECARException
         */
        public boolean pesquisarDuplos(VisaoAtributoDemanda obj) throws ECARException {
    	
    	boolean retorno = false;
    	
    	try
    	{
    	
	    	Criteria crits = session.createCriteria(VisaoAtributoDemanda.class);
 
	    	crits.add(Restrictions.eq("visaoAtributoDemandaPk.atributoDemanda.codAtbdem", obj.getVisaoAtributoDemandaPk().getAtributoDemanda().getCodAtbdem()));
	    	
	    	crits.add(Restrictions.eq("visaoAtributoDemandaPk.visao.codVisao", obj.getVisaoAtributoDemandaPk().getVisao().getCodVisao()));
	    	
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
	 * Valida se o nome informado para o atributo na demanda 
	 * é igual a algum dos atributos de regDemanda
	 * 
         * @param visaoAtributoDemanda
         * @throws ECARException
	 */
	public void validarNomeAtributoVisao(VisaoAtributoDemanda visaoAtributoDemanda) throws ECARException{
		if (!visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("codRegd") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("dataLimiteRegd") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("descricaoRegd") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("observacaoRegd") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("numeroDocOrigemRegd") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("dataSolicitacaoRegd") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("dataInclusaoRegd") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("indAtivoRegd") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("nomeSolicitanteRegd") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("dataSituacaoRegd") && 
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("prioridadePrior") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("sitDemandaSitd") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("usuarioUsuByCodUsuInclusaoRegd") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("entidadeOrgaoDemandaEntorgds") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("localDemandaLdems") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("regApontamentoRegdas") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("itemRegdemandaIregds") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("demAtributoDemas") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("regDemandaRegds") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("regDemandaRegd") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("entidadeDemandaEntds") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("indRestritivo") && 
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("dataAlteracaoRegd") &&
		   !visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetNome().equals("usuarioUsuByCodUsuAlteracaoRegd") ){
			
			throw new ECARException("atributoDemanda.validacao.nomeAtributoDemandaInvalido");
			
		}
	}
		
}
