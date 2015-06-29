package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.SitDemandaSitd;
import ecar.pojo.VisaoDemandasVisDem;
import ecar.pojo.VisaoSituacaoDemanda;
import ecar.pojo.VisaoSituacaoDemandaPK;

/**
 * Classe de acesso ao Banco para a entidade Visao.
 * 
 */
public class VisaoSituacaoDemandaDao extends Dao {
	
	public static final String SITUACAO_VISAO_FILTRO = "F";
	public static final String SITUACAO_VISAO_EDICAO = "E";
	public static final String SITUACAO_VISAO_TODOS = "T";
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
	 * 
	 * @param request
	 */
	public VisaoSituacaoDemandaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}

	/**
	 * Visão
	 * 
	 * @param visao
	 * @throws ECARException
	 */
	public void salvar(VisaoSituacaoDemanda visao) throws ECARException {

		super.salvar(visao);
	}

	/**
	 * Excluir visão
	 * 
	 * @param visao
	 * @throws ECARException
	 */
	public void excluir(VisaoSituacaoDemanda visaoSituacao)
			throws ECARException {

		super.excluir(visaoSituacao);

	}

	//	
	/**
	 * 
	 * @param visao
	 * @throws ECARException
	 */
	public void alterar(VisaoDemandasVisDem visao) throws ECARException {


		super.alterar(visao);
	}


	/**
	 * Recupera todas as visões
	 * 
	 * @return
	 * @throws ECARException
	 */
	public List getVisaoSituacao() throws ECARException {

		return super.listar(VisaoSituacaoDemanda.class, null);
	}

	public void mantemVisaoSituacaoDemanda(HttpServletRequest request, VisaoDemandasVisDem visao) throws ECARException{
		SituacaoDao situacaoDao = new SituacaoDao(request);
		List<SitDemandaSitd> todasSituacoes = situacaoDao.listar(SitDemandaSitd.class, null);
		for (SitDemandaSitd sit : todasSituacoes){
			VisaoSituacaoDemandaDao vsdDao = new VisaoSituacaoDemandaDao(request);
			VisaoSituacaoDemandaPK vsdpk = new VisaoSituacaoDemandaPK(visao.getCodVisao(), sit.getCodSitd());
			VisaoSituacaoDemanda vsd = (VisaoSituacaoDemanda) vsdDao.localizar(VisaoSituacaoDemanda.class, vsdpk);				
			if ((vsd == null))
				vsd = new VisaoSituacaoDemanda(vsdpk); 
					
			if (Pagina.getParamStr(request, "f" + sit.getCodSitd()).equals(Pagina.SIM)){
				vsd.setIndConsultar(true);
			} else {
				vsd.setIndConsultar(false);
			}					

			if (Pagina.getParamStr(request, "a" + sit.getCodSitd()).equals(Pagina.SIM)){
				vsd.setIndAlterar(true);
			} else {
				vsd.setIndAlterar(false);
			}
			visao.getVisaoSituacaoDemandas().add(vsd);
		}
	}
	
	
	/**
	 * Recupera as situações de uma determinada visão de acordo com o filtro passado como parâmetro
	 * @param visaoDemandasVisDem
	 * @param filtro
	 * @return
	 * @throws ECARException 
	 */
	public List<SitDemandaSitd> getSituacoesVisao(VisaoDemandasVisDem visaoDemandasVisDem, String filtro) throws ECARException{
		List<SitDemandaSitd> retorno = new ArrayList<SitDemandaSitd>();
		if (visaoDemandasVisDem != null && visaoDemandasVisDem.getVisaoSituacaoDemandas() != null){
			Iterator<VisaoSituacaoDemanda> itVisaoSituacaoDemandas = visaoDemandasVisDem.getVisaoSituacaoDemandas().iterator();
			while (itVisaoSituacaoDemandas.hasNext()){
				VisaoSituacaoDemanda visaoSituacaoDemanda = (VisaoSituacaoDemanda) itVisaoSituacaoDemandas.next();
				if (filtro == null || filtro.equals("") || filtro.equals(SITUACAO_VISAO_TODOS)){
					retorno.add(visaoSituacaoDemanda.getSitDemandaSitd());
				} else if (filtro != null){
					if ((filtro.equals(SITUACAO_VISAO_EDICAO) && visaoSituacaoDemanda.getIndAlterar()) 
							|| 
						 (filtro.equals(SITUACAO_VISAO_FILTRO) && visaoSituacaoDemanda.getIndConsultar())){
						retorno.add(visaoSituacaoDemanda.getSitDemandaSitd());
					} 
				}
			}
		}
		super.ordenaListInvert(retorno, "descricaoSitd");
		return retorno;
	}
	
	
	/**
    *
    * @return
	 * @throws ECARException 
    */
   public String getSituacoesVisaoIdsString(VisaoDemandasVisDem visaoDemandasVisDem, String filtro) throws ECARException {
		String ids = "";
		Iterator<SitDemandaSitd> it = null;
		SitDemandaSitd situacao = null;
		List<SitDemandaSitd> situacoesDemandas = this.getSituacoesVisao(visaoDemandasVisDem, filtro);
		int i = 0;
		if (situacoesDemandas!=null) {
			it = situacoesDemandas.iterator();
			while(it.hasNext()) {
				situacao = it.next();
				if (i==0)
					ids = situacao.getCodSitd().toString();
				else
					ids += "," + situacao.getCodSitd().toString();
				i++;
			}
		}		
		return ids;
	}
   
   /**
    * 
    * @param request
    * @param situacoesFiltro
    * @param situacoesAlteracao
    * @throws ECARException
    */
   	public void mapearSituacoesSelecionadas(HttpServletRequest request, List<SitDemandaSitd> situacoesFiltro, List<SitDemandaSitd> situacoesAlteracao) throws ECARException{
   		SituacaoDao situacaoDao = new SituacaoDao(request);
   		List<SitDemandaSitd> todasSituacoes = situacaoDao.listar(SitDemandaSitd.class, null);
		for (SitDemandaSitd sit : todasSituacoes){
			if (Pagina.getParamStr(request, "f" + sit.getCodSitd()).equals(Pagina.SIM)){
				situacoesFiltro.add(sit);
			}					
			if (Pagina.getParamStr(request, "a" + sit.getCodSitd()).equals(Pagina.SIM)){
				situacoesAlteracao.add(sit);
			}
		}
   }
	
}
