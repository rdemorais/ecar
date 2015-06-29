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
import ecar.pojo.TipoAcompTipofuncacompSisatributoTaTpfaSatb;
import ecar.pojo.TipoAcompTipofuncacompSisatributoTaTpfaSatbId;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.util.Dominios;

/**
 *
 * @author 70744416353
 */
public class TipoAcompTipofuncacompSisatributoTaTpfaSatbDao extends Dao {

    /**
     *
     */
    public TipoAcompTipofuncacompSisatributoTaTpfaSatbDao() {
		// TODO Auto-generated constructor stub
	}
	
    /**
     *
     * @param codTa
     * @param codTpfa
     * @param codSatb
     * @return
     * @throws ECARException
     */
    public TipoAcompTipofuncacompSisatributoTaTpfaSatb buscar(Long codTa, Long codTpfa, Long codSatb) throws ECARException{
		try {
			return (TipoAcompTipofuncacompSisatributoTaTpfaSatb)
					this.getSession()
					.get(TipoAcompTipofuncacompSisatributoTaTpfaSatb.class,
							new TipoAcompTipofuncacompSisatributoTaTpfaSatbId(codTa,codTpfa,codSatb));
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}
	
        /**
         *
         * @param tatpfasatb
         * @param request
         */
        public void setTipoAcompTipofuncacompSisatributoTaTpfaSatb(TipoAcompTipofuncacompSisatributoTaTpfaSatb tatpfasatb, HttpServletRequest request){
		tatpfasatb.setIndLeituraParecer(
    			Pagina.getParamOrDefault(request, 
    										"Tpfa" + 
					    					tatpfasatb.getId().getCodTa() + "-" + 
					    					tatpfasatb.getId().getCodTpfa() + "-" +
					    					tatpfasatb.getId().getCodSatb(),
					    				Dominios.SEM_ACESSO_LEITURA));
	}
	
	/**
     * 
     * @param tipoAcompanhamentoTa
         * @param gruposAcessoSatb
         * @return List
         * @throws ECARException
     */
    
    public List pesquisarPermissaoVisualizarParecerTipoFuncaoAcompTpfa(TipoAcompanhamentoTa tipoAcompanhamentoTa, Set gruposAcessoSatb) throws ECARException{
    	
    	List listaTipoFuncaoAcompanhamentoTpfa = new ArrayList();
    	
        try {
            Iterator gruposAcessoSatbIt = gruposAcessoSatb.iterator();
        	while(gruposAcessoSatbIt.hasNext()){
            	TipoAcompTipofuncacompSisatributoTaTpfaSatb tipoAcompTipofuncacompSisatributoTaTpfaSatb = new TipoAcompTipofuncacompSisatributoTaTpfaSatb();
            	SisAtributoSatb grupoPermissaoAcessoSatb = (SisAtributoSatb) gruposAcessoSatbIt.next();
            	tipoAcompTipofuncacompSisatributoTaTpfaSatb.setSisAtributoSatb(grupoPermissaoAcessoSatb);
                tipoAcompTipofuncacompSisatributoTaTpfaSatb.setTipoAcompanhamentoTa(tipoAcompanhamentoTa);
                tipoAcompTipofuncacompSisatributoTaTpfaSatb.setIndLeituraParecer("S");
                
                List listaTipoAcompTipofuncacompSisatributoTaTpfaSatb = super.pesquisar(tipoAcompTipofuncacompSisatributoTaTpfaSatb, new String[]{"", ""});
                           
                Iterator listaTipoAcompTipofuncacompSisatributoTaTpfaSatbIt = listaTipoAcompTipofuncacompSisatributoTaTpfaSatb.iterator(); 
                while(listaTipoAcompTipofuncacompSisatributoTaTpfaSatbIt.hasNext()){
                	tipoAcompTipofuncacompSisatributoTaTpfaSatb = (TipoAcompTipofuncacompSisatributoTaTpfaSatb) listaTipoAcompTipofuncacompSisatributoTaTpfaSatbIt.next();
                	//Apenas entram na lista as funções que emitem posição
                	if (!listaTipoFuncaoAcompanhamentoTpfa.contains(tipoAcompTipofuncacompSisatributoTaTpfaSatb.getTipoFuncAcompTpfa()) && 
                			tipoAcompTipofuncacompSisatributoTaTpfaSatb.getTipoFuncAcompTpfa() != null && 
                			tipoAcompTipofuncacompSisatributoTaTpfaSatb.getTipoFuncAcompTpfa().getIndEmitePosicaoTpfa().equals("S")){
                		listaTipoFuncaoAcompanhamentoTpfa.add(tipoAcompTipofuncacompSisatributoTaTpfaSatb.getTipoFuncAcompTpfa());   
                	}
                }            	
            }            
        } catch (ECARException e) {
            this.logger.error(e);
            throw new ECARException(e);
        }
        
        return listaTipoFuncaoAcompanhamentoTpfa;
    }
    
}
	


