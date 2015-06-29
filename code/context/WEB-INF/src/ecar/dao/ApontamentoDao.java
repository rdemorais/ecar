package ecar.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.ApontamentoApt;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.PontoCriticoPtc;
import ecar.pojo.UsuarioUsu;

/*
 * Criado em 19/01/2005
 *
 */

/**
 * @author felipev
 * 
 */
public class ApontamentoDao extends Dao {
	/**
	 * 
	 * @param request
	 */
	public ApontamentoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	public ApontamentoDao() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public List<ApontamentoApt> loadApontamentos(ItemEstruturaIett iett) {
		String hql = "FROM ApontamentoApt ap WHERE ap.itemEstruturaIett = :iett " +
				"ORDER BY ap.dataInclusaoApt";
		Query q = this.getSession().createQuery(hql);
		q.setParameter("iett", iett);
		return q.list();
	}

	/**
	 * Constr�i um objeto Apontamento a partir de atributos passados no
	 * request
	 * 
	 * @param request
	 * @param apontamento
	 * @throws ECARException
	 */
	public void setApontamento(HttpServletRequest request,
			ApontamentoApt apontamento) throws ECARException {
		Pagina.getParamLong(request, "codIett");

		if (!Pagina.getParamStr(request, "codIett").equals("")) {
			ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) this.buscar(
					ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(
							request, "codIett")));
			apontamento.setItemEstruturaIett(itemEstrutura);
		}

		if (!"".equals(Pagina.getParamStr(request, "cod"))) {
			apontamento.setCodApt(Long.valueOf(Pagina.getParamStr(request,
					"cod")));
		} else if (!"".equals(Pagina.getParamStr(request, "codApt"))) {
			apontamento.setCodApt(Long.valueOf(Pagina.getParamStr(request,
					"codApt")));
		}

		PontoCriticoPtc pontoCritico = (PontoCriticoPtc) this.buscar(
				PontoCriticoPtc.class, Long.valueOf(Pagina.getParamStr(request,
						"codPtc")));
		apontamento.setPontoCriticoPtc(pontoCritico);
		apontamento.setTextoApt(Pagina.getParamStr(request, "textoApt"));

		UsuarioUsu usuario = (UsuarioUsu) this.buscar(UsuarioUsu.class, Long
				.valueOf(Pagina.getParamStr(request, "codUsu")));
		apontamento.setUsuarioUsu(usuario);
	}

	/**
	 * Recebe um Array com C�digos de Apontamento. Exclui cada um dos
	 * apontamentos
	 * 
	 * @param codigosParaExcluir
	 * @throws ECARException
	 */
	public void excluir(String[] codigosParaExcluir) throws ECARException {
		for (int i = 0; i < codigosParaExcluir.length; i++) {
			ApontamentoApt apontamento = (ApontamentoApt) this.buscar(
					ApontamentoApt.class, Long.valueOf(codigosParaExcluir[i]));
			this.excluir(apontamento);
		}
	}

	/**
	 * Salva um registro de Apontamento
	 * 
	 * @param apontamento
	 * @throws ECARException
	 */
	public void salvar(ApontamentoApt apontamento) throws ECARException {
		apontamento.setDataInclusaoApt(Data.getDataAtual());
		super.salvar(apontamento);
	}
	
}
