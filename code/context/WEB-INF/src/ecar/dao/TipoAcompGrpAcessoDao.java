package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.TipoAcompGrpAcesso;
import ecar.pojo.TipoAcompGrpAcessoId;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.util.Dominios;

/**
 * @author Robson
 * @since 22/11/2007
 */
public class TipoAcompGrpAcessoDao extends Dao{
	
    /**
     *
     * @param satb
     * @param ta
     * @return
     * @throws ECARException
     */
    public TipoAcompGrpAcesso getTipoAcompGrpAcesso(SisAtributoSatb satb, TipoAcompanhamentoTa ta) throws ECARException{
		try{
	//		return (TipoAcompGrpAcesso) 
	//				this.getSession()
	//					.createCriteria(TipoAcompGrpAcesso.class)
	//					.add(Restrictions.eq("id",id))
	//					.uniqueResult();
			return (TipoAcompGrpAcesso) this.getSession().get(TipoAcompGrpAcesso.class, new TipoAcompGrpAcessoId(ta.getCodTa(),satb.getCodSatb()));
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}
	
    /**
     *
     * @param taa
     * @param request
     */
    public void setTipoAcompGrpAcesso(TipoAcompGrpAcesso taa, HttpServletRequest request){
		
		taa.setAcessoInclusao(Pagina.getParamOrDefault(request, "GeraAcomp" + taa.getId().getCodTa(), Dominios.SEM_ACESSO_INCLUSAO));
		
		taa.setIndGerarArquivo(Pagina.getParamOrDefault(request, "GerarArquivo"+taa.getId().getCodTa(), Dominios.NAO));
		
		try{
			if(Dominios.COM_ACESSO_INCLUSAO.equals(taa.getAcessoInclusao()))
				taa.setSepararPorOrgao(Integer.valueOf(Pagina.getParamStr(request, "SepararPorOrgao" + taa.getId().getCodTa())));
			else
				taa.setSepararPorOrgao(null);
		}
		catch (NumberFormatException e) {
			taa.setSepararPorOrgao(null);
		}
	}
	
    /**
     *
     * @param gruposUsuario
     * @param TipoAcesso
     * @return
     */
    public boolean permissaoAcessoSecretariasUsuario(Set gruposUsuario, int TipoAcesso) {
		
		List listaCods = new ArrayList();
		for(Iterator it = gruposUsuario.iterator(); it.hasNext();){
			SisAtributoSatb satb = (SisAtributoSatb) it.next();
			listaCods.add(satb.getCodSatb());
		}
		
		return !this.getSession().createQuery(
				
				"select acesso " +
				"from TipoAcompGrpAcesso acesso " +
				"where acessoInclusao = :incluir " +
				"and acesso.separarPorOrgao = :tipo " +
				"and acesso.sisAtributoSatb.codSatb in (:lista) ")
									
			.setMaxResults(1)
			.setString("incluir", Dominios.SIM)
			.setInteger("tipo", TipoAcesso)
			.setParameterList("lista", listaCods)
			.list()
			.isEmpty();
	}
}
