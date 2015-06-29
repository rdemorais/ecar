package ecar.dao;

import org.hibernate.Query;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.DemandasGrpAcesso;
import ecar.pojo.SisAtributoSatb;

/**
 *
 * @author 70744416353
 */
public class DemandasGrpAcessoDao extends Dao{
	
    /**
     *
     */
    public DemandasGrpAcessoDao() {
		super();
	}
	
        /**
         *
         * @param satb
         * @return
         * @throws ECARException
         */
        public DemandasGrpAcesso getDemandasGrpAcesso(SisAtributoSatb satb) throws ECARException{
		try{
			StringBuilder query = new StringBuilder("select demandas from DemandasGrpAcesso as demandas ")
				.append(" where demandas.codSatb = " + satb.getCodSatb());

			Query q = this.getSession().createQuery(query.toString());
			
			q.setMaxResults(1);
			
			Object demanda = q.uniqueResult();
			
			if(demanda != null) {
				return (DemandasGrpAcesso)demanda;
			} else {
				return null;
			}
			
		} catch(Exception e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}

}