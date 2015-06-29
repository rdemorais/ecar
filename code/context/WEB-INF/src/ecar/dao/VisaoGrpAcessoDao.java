package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.VisaoDemandasGrpAcesso;
import ecar.pojo.VisaoDemandasGrpAcessoPK;
import ecar.pojo.VisaoDemandasVisDem;

/**
 *
 * @author 70744416353
 */
public class VisaoGrpAcessoDao extends Dao{
	
    /**
     *
     */
    public VisaoGrpAcessoDao() {
		super();
	}
	
        /**
         *
         * @param codSatb
         * @return
         * @throws ECARException
         */
        public List getVisoesVisaoDemandasGrpAcesso(Long codSatb) throws ECARException{
		try{
			StringBuilder query = new StringBuilder("select distinct visao from VisaoDemandasGrpAcesso as visaoDemandas, VisaoDemandasVisDem visao ")
				.append(" where visaoDemandas.visaoDemandasGrpAcessoPk.sisAtributo.codSatb = " + codSatb + " and visaoDemandas.visaoDemandasGrpAcessoPk.visao.codVisao = visao.codVisao");

			Query q = this.getSession().createQuery(query.toString());
		
			return q.list();
			
		} catch(Exception e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}
	
	/**
	 * Inseri Visoes de Grupo de Acesso dado o grupo de acesso e as visoes
	 * @param visoes
         * @param codSatb
         * @throws ECARException
	 */
	public void inserir(String[] visoes, Long codSatb) throws ECARException{
		try{
			
			Criteria c = session.createCriteria(VisaoDemandasGrpAcesso.class);
			
			c.add(Restrictions.eq("visaoDemandasGrpAcessoPk.sisAtributo.codSatb", codSatb));
			
			List<VisaoDemandasGrpAcesso> visoesGrpAcesso = c.list();
			
			List<VisaoDemandasGrpAcesso> visoesGrpAcessoInsercao = new ArrayList<VisaoDemandasGrpAcesso>();
			List<VisaoDemandasGrpAcesso> visoesGrpAcessoRemocao = new ArrayList<VisaoDemandasGrpAcesso>();
			
			// definindo os para inclusao
			Iterator<VisaoDemandasGrpAcesso> iterator = null;
			boolean existe = false;
			VisaoDemandasGrpAcesso visaoGrpAcesso  = null;
			if (visoes != null){
				for(int i=0;i<visoes.length;i++) {
					iterator = visoesGrpAcesso.iterator();
					while(iterator.hasNext()) {
						visaoGrpAcesso  = iterator.next();
						if (new Long(visoes[i]).equals(visaoGrpAcesso.getVisaoDemandasGrpAcessoPk().getVisao().getCodVisao())) {
							existe = true;	
							break;
						}
					}
					if(!existe) {
						visaoGrpAcesso = new VisaoDemandasGrpAcesso();
						VisaoDemandasGrpAcessoPK visaoGrpAcessoPk = new VisaoDemandasGrpAcessoPK();
						SisAtributoSatb sis = new SisAtributoSatb();
						sis.setCodSatb(codSatb);
						visaoGrpAcessoPk.setSisAtributo(sis);
						VisaoDemandasVisDem visao = new VisaoDemandasVisDem();
						visao.setCodVisao(new Long(visoes[i]));
						visaoGrpAcessoPk.setVisao(visao);
						visaoGrpAcesso.setVisaoDemandasGrpAcessoPk(visaoGrpAcessoPk);
						visoesGrpAcessoInsercao.add(visaoGrpAcesso);
					} else {
						existe = false;
					}

				}
			}

			// definindo os para exclusao
			iterator = visoesGrpAcesso.iterator();
			boolean naoExiste = true;
			while(iterator.hasNext()) {
				visaoGrpAcesso  = iterator.next();
				if (visoes != null){
					for(int i=0;i<visoes.length;i++) {
						if (new Long(visoes[i]).equals(visaoGrpAcesso.getVisaoDemandasGrpAcessoPk().getVisao().getCodVisao())) {
							naoExiste = false;	
							break;
						}
					}
				}
					
				if(naoExiste) {
					visoesGrpAcessoRemocao.add(visaoGrpAcesso);
				} else {
					naoExiste = true;
				}
			}
			
			iterator  = visoesGrpAcessoInsercao.iterator();
			while(iterator.hasNext()) {
				visaoGrpAcesso  = iterator.next();
				this.salvar(visaoGrpAcesso);
			}
			
			iterator  = visoesGrpAcessoRemocao.iterator();
			while(iterator.hasNext()) {
				visaoGrpAcesso  = iterator.next();
				this.excluir(visaoGrpAcesso);
			}
			
		} catch(Exception e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}

}