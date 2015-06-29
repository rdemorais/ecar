package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.TipoAcompAbasSisatributoTaAbaSatb;
import ecar.pojo.TipoAcompAbasSisatributoTaAbaSatbId;
import ecar.util.Dominios;

/**
 *
 * @author 70744416353
 */
public class TipoAcompAbasSisatributoTaAbaSatbDao extends Dao {

    /**
     *
     */
    public TipoAcompAbasSisatributoTaAbaSatbDao() {
		// TODO Auto-generated constructor stub
	}
	
    /**
     *
     * @param codTa
     * @param codAba
     * @param codSatb
     * @return
     * @throws ECARException
     */
    public TipoAcompAbasSisatributoTaAbaSatb buscar(Long codTa, Long codAba, Long codSatb) throws ECARException{
		try {
			return  (TipoAcompAbasSisatributoTaAbaSatb)
					this.getSession()
					.get(TipoAcompAbasSisatributoTaAbaSatb.class,
							new TipoAcompAbasSisatributoTaAbaSatbId(codTa, codAba, codSatb));
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}
	
    /**
     *
     * @param taabasatb
     * @param request
     */
    public void setTipoAcompAbasSisatributoTaAbaSatb(TipoAcompAbasSisatributoTaAbaSatb taabasatb, HttpServletRequest request){
	
		taabasatb.setIndVisualizaAba(
			Pagina.getParamOrDefault(request,
								"tpaba" + 
								taabasatb.getId().getCodTa()  + "-" + 
								taabasatb.getId().getCodAba() + "-" +
								taabasatb.getId().getCodSatb(), 
							Dominios.SEM_ACESSO_LEITURA));
	}

}
