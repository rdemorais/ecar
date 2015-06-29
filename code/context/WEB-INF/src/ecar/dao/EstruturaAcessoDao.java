/*
 * Created on 13/09/2004
 * 
 */
package ecar.dao;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.EstruturaAcessoEtta;
import ecar.pojo.EstruturaAcessoEttaPK;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.TipoAcompGrpAcesso;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/**
 * @author evandro
 *
 */
public class EstruturaAcessoDao extends Dao{
	
    /**
     *
     */
    public static final short PERMISSAO_ACESSO_SECRETARIA = 1;
    /**
     *
     */
    public static final short PERMISSAO_ACESSO_OUTRA_SECRETARIA = 2;
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public EstruturaAcessoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}

        /**
         *
         */
        public EstruturaAcessoDao() {
		super();
	}
    
	
	/**
	 * Verifica se um grupo de usuario tem permissao de incluir registro de monitoramento em qualquer estrutura.
	 * @param estrutura
	 * @param gruposUsuario
	 * @return
	 * @throws ECARException
	 */
	//@@ALTERAR@@
//    public boolean temPermissoesAcessoAcompMonitorado(Set gruposUsuario) throws ECARException {
//        boolean resultado = false;
//        		
//        try {
//            Query q = this.getSession().createQuery("select permissoes.estruturaEtt.codEtt " +
//                    "from EstruturaAcessoEtta permissoes " +                    
//                    "where permissoes.indIncAcompMonitoradoEtta = :incluir " +
//                    "and permissoes.sisAtributoSatb.codSatb in (:lista) ");
//            
//            q.setMaxResults(1);
//
//            q.setString("incluir", ValidaPermissao.SIM);
//			
//			List codigos = new ArrayList();
//			Iterator it = gruposUsuario.iterator();
//			
//			while(it.hasNext()){
//				SisAtributoSatb atributo = (SisAtributoSatb) it.next();
//				codigos.add(atributo.getCodSatb());
//			}
//            q.setParameterList("lista", codigos);			
//			            
//            List retorno = q.list();
//            if(!retorno.isEmpty()) {
//            	resultado = true;
//            }
//			
//        } catch (HibernateException e) {
//            this.logger.error(e);
//            throw new ECARException(e);
//        }
//        return resultado;
//        
//    }
	
	/**
	 * Verifica se um grupo de usuario tem permissao de incluir registro de monitoramento em qualquer estrutura
	 * para registros de sua propria secretaria.
	 * @param estrutura
	 * @param gruposUsuario
	 * @return
	 * @throws ECARException
	 */
    //@@ALTERAR@@
//    public boolean temPermissoesAcessoSecretaria(Set gruposUsuario) throws ECARException {
//        boolean resultado = false;
//        		
//        try {
//            Query q = this.getSession().createQuery("select permissoes.estruturaEtt.codEtt " +
//                    "from EstruturaAcessoEtta permissoes " +                    
//                    "where permissoes.indLeiAcompSecpropEtta = :incluir " +
//                    "and permissoes.sisAtributoSatb.codSatb in (:lista) ");
//            
//            q.setMaxResults(1);
//
//            q.setString("incluir", ValidaPermissao.SIM);
//			
//			List codigos = new ArrayList();
//			Iterator it = gruposUsuario.iterator();
//			
//			while(it.hasNext()){
//				SisAtributoSatb atributo = (SisAtributoSatb) it.next();
//				codigos.add(atributo.getCodSatb());
//			}
//            q.setParameterList("lista", codigos);
//			            
//            List retorno = q.list();
//            if(!retorno.isEmpty()) {
//            	resultado = true;
//            }
//			
//        } catch (HibernateException e) {
//            this.logger.error(e);
//            throw new ECARException(e);
//        }
//        return resultado;
//        
//    }
	
	/**
	 * Verifica se um grupo de usuario tem permissao de incluir registro de monitoramento em qualquer estrutura
	 * para registros de qualquer secretaria que não a sua própria.
	 * @param estrutura
	 * @param gruposUsuario
	 * @return
	 * @throws ECARException
	 */
    //@@ALTERAR@@
//    public boolean temPermissoesAcessoOutraSecretaria(Set gruposUsuario) throws ECARException {
//        boolean resultado = false;
//        		
//        try {
//            Query q = this.getSession().createQuery("select permissoes.estruturaEtt.codEtt " +
//                    "from EstruturaAcessoEtta permissoes " +                    
//                    "where permissoes.indIncAcompSecoutrEtta = :incluir " +
//                    "and permissoes.sisAtributoSatb.codSatb in (:lista) ");
//            
//            q.setMaxResults(1);
//
//            q.setString("incluir", ValidaPermissao.SIM);
//            
//			List codigos = new ArrayList();
//			Iterator it = gruposUsuario.iterator();
//			
//			while(it.hasNext()){
//				SisAtributoSatb atributo = (SisAtributoSatb) it.next();
//				codigos.add(atributo.getCodSatb());
//			}
//            q.setParameterList("lista", codigos);
//            
//            List retorno = q.list();
//            if(!retorno.isEmpty()) {
//            	resultado = true;
//            }
//			
//        } catch (HibernateException e) {
//            this.logger.error(e);
//            throw new ECARException(e);
//        }
//        return resultado;
//        
//    }
 
	/**
	 * Verifica se um grupo de usuario tem permissao de incluir registro de monitoramento em uma estrutura.
	 * @param estrutura
	 * @param gruposUsuario
	 * @return
	 * @throws ECARException
	 */
    //@@ALTERAR@@
//  public boolean temPermissoesAcessoAcompMonitorado(Set gruposUsuario, EstruturaEtt estrutura) throws ECARException {
//        boolean resultado = false;
//        		
//        try {
//            Query q = this.getSession().createQuery("select permissoes.estruturaEtt.codEtt " +
//                    "from EstruturaAcessoEtta permissoes " +                    
//                    "where permissoes.indIncAcompMonitoradoEtta = :incluir " +
//                    "and permissoes.sisAtributoSatb.codSatb in (:lista) " +
//                    "and permissoes.estruturaEtt.codEtt = :codEtt");
//            
//            q.setMaxResults(1);
//
//            q.setString("incluir", ValidaPermissao.SIM);
//            
//			List codigos = new ArrayList();
//			Iterator it = gruposUsuario.iterator();
//			
//			while(it.hasNext()){
//				SisAtributoSatb atributo = (SisAtributoSatb) it.next();
//				codigos.add(atributo.getCodSatb());
//			}
//            q.setParameterList("lista", codigos);
//			
//			q.setLong("codEtt", estrutura.getCodEtt().longValue());
//            
//            List retorno = q.list();
//            if(!retorno.isEmpty()) {
//            	resultado = true;
//            }
//			
//        } catch (HibernateException e) {
//            this.logger.error(e);
//            throw new ECARException(e);
//        }
//        return resultado;
//        
//    }
	
	/**
	 * Verifica se um grupo de usuario tem permissao de incluir registro de monitoramento em uma estrutura
	 * para registros de sua propria secretaria.
	 * @param estrutura
	 * @param gruposUsuario
	 * @return
	 * @throws ECARException
	 */
    //@@ALTERAR@@
//    public boolean temPermissoesAcessoSecretaria(Set gruposUsuario, EstruturaEtt estrutura) throws ECARException {
//        boolean resultado = false;
//        		
//        try {
//            Query q = this.getSession().createQuery("select permissoes.estruturaEtt.codEtt " +
//                    "from EstruturaAcessoEtta permissoes " +                    
//                    "where permissoes.estruturaEtt.codEtt = :codEtt and " +
//                    "permissoes.indLeiAcompSecpropEtta = :incluir " +
//                    "and permissoes.sisAtributoSatb.codSatb in (:lista) ");
//            
//            q.setMaxResults(1);
//
//            q.setLong("codEtt", estrutura.getCodEtt().longValue());
//            q.setString("incluir", ValidaPermissao.SIM);
//            
//			List codigos = new ArrayList();
//			Iterator it = gruposUsuario.iterator();
//			
//			while(it.hasNext()){
//				SisAtributoSatb atributo = (SisAtributoSatb) it.next();
//				codigos.add(atributo.getCodSatb());
//			}
//            q.setParameterList("lista", codigos);
//            
//            List retorno = q.list();
//            if(!retorno.isEmpty()) {
//            	resultado = true;
//            }
//			
//        } catch (HibernateException e) {
//            this.logger.error(e);
//            throw new ECARException(e);
//        }
//        return resultado;
//        
//    }
	
	/**
	 * Verifica se um grupo de usuario tem permissao de incluir registro de monitoramento em qualquer estrutura
	 * para registros de qualquer secretaria que não a sua própria.
	 * @param estrutura
	 * @param gruposUsuario
	 * @return
	 * @throws ECARException
	 */
    //@@ALTERAR@@
//    public boolean temPermissoesAcessoOutraSecretaria(Set gruposUsuario, EstruturaEtt estrutura) throws ECARException {
//        boolean resultado = false;
//        		
//        try {
//            Query q = this.getSession().createQuery("select permissoes.estruturaEtt.codEtt " +
//                    "from EstruturaAcessoEtta permissoes " +                    
//                    "where permissoes.estruturaEtt.codEtt = :codEtt and " +
//                    "permissoes.indIncAcompSecoutrEtta = :incluir " +
//                    "and permissoes.sisAtributoSatb.codSatb in (:lista) ");
//            
//            q.setMaxResults(1);
//
//            q.setLong("codEtt", estrutura.getCodEtt().longValue());
//            q.setString("incluir", ValidaPermissao.SIM);
//			
//			List codigos = new ArrayList();
//			Iterator it = gruposUsuario.iterator();
//			
//			while(it.hasNext()){
//				SisAtributoSatb atributo = (SisAtributoSatb) it.next();
//				codigos.add(atributo.getCodSatb());
//			}
//            q.setParameterList("lista", codigos);
//			            
//            List retorno = q.list();
//            if(!retorno.isEmpty()) {
//            	resultado = true;
//            }
//			
//        } catch (HibernateException e) {
//            this.logger.error(e);
//            throw new ECARException(e);
//        }
//        return resultado;
//        
//    }
 
	
	/**
	 * Verifica um conjunto de grupos de acesso tem permissao de leitura de acompanhamento para registros monitorados de sua responsabilidade
	 * @param estrutura
	 * @param gruposUsuario
	 * @return
	 * @throws ECARException
	 */
    //@@ALTERAR@@
//    public boolean temPermissoesLeituraAcompSuaResponsabilidade(Set gruposUsuario, EstruturaEtt estrutura) throws ECARException {
//        boolean resultado = false;
//        		
//        try {
//            Query q = this.getSession().createQuery("select permissoes.estruturaEtt.codEtt " +
//                    "from EstruturaAcessoEtta permissoes " +                    
//                    "where permissoes.estruturaEtt.codEtt = :codEtt and " +
//                    "permissoes.indLeiAcompSuaResponsEtta = :leitura " +
//                    "and permissoes.sisAtributoSatb.codSatb in (:lista) ");
//            
//            q.setMaxResults(1);
//            
//			q.setLong("codEtt", estrutura.getCodEtt().longValue());
//            q.setString("leitura", ValidaPermissao.SIM);
//			
//			List codigos = new ArrayList();
//			Iterator it = gruposUsuario.iterator();
//			
//			while(it.hasNext()){
//				SisAtributoSatb atributo = (SisAtributoSatb) it.next();
//				codigos.add(atributo.getCodSatb());
//			}
//            q.setParameterList("lista", codigos);
//			            
//            List retorno = q.list();
//            if(!retorno.isEmpty()) {
//            	resultado = true;
//            }
//			
//        } catch (HibernateException e) {
//        	this.logger.error(e);
//            throw new ECARException(e);
//        }
//        return resultado;
//        
//    }
	
	/**
	 * Verifica se um grupo de grupos de acesso tem permissao de leitura de acompanhamento para registros monitorados de sua secretaria/órgão
	 * @param estrutura
	 * @param gruposUsuario
	 * @return
	 * @throws ECARException
	 */
    //@@ALTERAR@@
//    public boolean temPermissoesLeituraAcompMonitoradoSuaSecretaria(Set gruposUsuario, EstruturaEtt estrutura) throws ECARException {
//        boolean resultado = false;
//        		
//        try {
//            Query q = this.getSession().createQuery("select permissoes.estruturaEtt.codEtt " +
//                    "from EstruturaAcessoEtta permissoes " +                    
//                    "where permissoes.estruturaEtt.codEtt = :codEtt and " +
//                    "permissoes.indLeiAcompMoniSecProEtta = :leitura " +
//                    "and permissoes.sisAtributoSatb.codSatb in (:lista) ");
//            
//            q.setMaxResults(1);
//
//            q.setLong("codEtt", estrutura.getCodEtt().longValue());
//            q.setString("leitura", ValidaPermissao.SIM);
//			
//			List codigos = new ArrayList();
//			Iterator it = gruposUsuario.iterator();
//			
//			while(it.hasNext()){
//				SisAtributoSatb atributo = (SisAtributoSatb) it.next();
//				codigos.add(atributo.getCodSatb());
//			}
//            q.setParameterList("lista", codigos);
//			            
//            List retorno = q.list();
//            if(!retorno.isEmpty()) {
//            	resultado = true;
//            }
//			
//        } catch (HibernateException e) {
//            this.logger.error(e);
//            throw new ECARException(e);
//        }
//        return resultado;
//        
//    }
	
	/**
	 * Verifica se um grupo de grupos de acesso tem permissao de leitura de acompanhamento para registros monitorados de outra secretaria/órgão
	 * @param estrutura
	 * @param gruposUsuario
	 * @return
	 * @throws ECARException
	 */
    //@@ALTERAR@@
//    public boolean temPermissoesLeituraAcompMonitoradoOutraSecretaria(Set gruposUsuario, EstruturaEtt estrutura) throws ECARException {
//        boolean resultado = false;
//        		
//        try {
//            Query q = this.getSession().createQuery("select permissoes.estruturaEtt.codEtt " +
//                    "from EstruturaAcessoEtta permissoes " +                    
//                    "where permissoes.estruturaEtt.codEtt = :codEtt and " +
//                    "permissoes.indLeiAcompMoniSecOutEtta = :leitura " +
//                    "and permissoes.sisAtributoSatb.codSatb in (:lista) ");
//            
//            q.setMaxResults(1);
//
//            q.setLong("codEtt", estrutura.getCodEtt().longValue());
//            q.setString("leitura", ValidaPermissao.SIM);
//			
//			List codigos = new ArrayList();
//			Iterator it = gruposUsuario.iterator();
//			
//			while(it.hasNext()){
//				SisAtributoSatb atributo = (SisAtributoSatb) it.next();
//				codigos.add(atributo.getCodSatb());
//			}
//            q.setParameterList("lista", codigos);
//			            
//            List retorno = q.list();
//            if(!retorno.isEmpty()) {
//            	resultado = true;
//            }
//			
//        } catch (HibernateException e) {
//            this.logger.error(e);
//            throw new ECARException(e);
//        }
//        return resultado;
//        
//    }
	
	/**
	 * Verifica se um grupo de grupos de acesso tem permissao de leitura de acompanhamento para registros monitorados próprios
	 * @param estrutura
	 * @param gruposUsuario
	 * @return
	 * @throws ECARException
	 */
    //@@ALTERAR@@
//    public boolean temPermissoesLeituraAcompMonitoradoProprio(Set gruposUsuario, EstruturaEtt estrutura) throws ECARException {
//        boolean resultado = false;
//        		
//        try {
//            Query q = this.getSession().createQuery("select permissoes.estruturaEtt.codEtt " +
//                    "from EstruturaAcessoEtta permissoes " +                    
//                    "where permissoes.estruturaEtt.codEtt = :codEtt and " +
//                    "permissoes.indLeiAcompMoniProprEtta = :leitura " +
//                    "and permissoes.sisAtributoSatb.codSatb in (:lista) ");
//            
//            q.setMaxResults(1);
//
//            q.setLong("codEtt", estrutura.getCodEtt().longValue());
//            q.setString("leitura", ValidaPermissao.SIM);
//			
//			List codigos = new ArrayList();
//			Iterator it = gruposUsuario.iterator();
//			
//			while(it.hasNext()){
//				SisAtributoSatb atributo = (SisAtributoSatb) it.next();
//				codigos.add(atributo.getCodSatb());
//			}
//            q.setParameterList("lista", codigos);
//			            
//            List retorno = q.list();
//            if(!retorno.isEmpty()) {
//            	resultado = true;
//            }
//			
//        } catch (HibernateException e) {
//            this.logger.error(e);
//            throw new ECARException(e);
//        }
//        return resultado;
//        
//    }
    
    /**
     * @author Robson
     * @param atributo
     * @param estruturaEtt
     * @return EstruturaAcessoEtta
     * @throws ECARException 
     */
    public EstruturaAcessoEtta getEstruturaAcessoEtta(SisAtributoSatb atributo, EstruturaEtt estruturaEtt) throws ECARException{
    	// XXX: Novas funções de acesso
    	try{
    		EstruturaAcessoEttaPK id = new EstruturaAcessoEttaPK(estruturaEtt.getCodEtt(),atributo.getCodSatb());
//	    	return (EstruturaAcessoEtta) this.getSession().createCriteria(EstruturaAcessoEtta.class)
//	    				.add(Restrictions.eq("estruturaEtt", estruturaEtt))
//	    				.add(Restrictions.eq("sisAtributoSatb", atributo))
//	    				.uniqueResult();
    		return (EstruturaAcessoEtta) this.getSession().get(EstruturaAcessoEtta.class, id);
    	}
    	catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
    }
    
    /**
     * @author Robson
     * @param etta
     * @param request
     */
    public void setEstruturaAcessoEtta(EstruturaAcessoEtta etta, HttpServletRequest request){
    	// XXX: Novas funções de acesso 
		etta.setIndIncItemEtta(
				Pagina.getParamOrDefault(
						request, 
						"indIncItemEtta" + etta.getComp_id().getCodEtt(), 
						EstruturaAcessoEtta.SEM_ACESSO_INCLUSAO));
		etta.setIndExibirHistoricoEtta(
				Pagina.getParamOrDefault(
						request, 
						"indExibirHistoricoEtta" + etta.getComp_id().getCodEtt(), 
						EstruturaAcessoEtta.SEM_ACESSO_INCLUSAO));
		etta.setIndExibirImprimirEtta(Pagina.getParamOrDefault(
						request, 
						"indExibirImprimirEtta" + etta.getComp_id().getCodEtt(), 
						EstruturaAcessoEtta.SEM_ACESSO_INCLUSAO));
		etta.setIndExibirGerarArquivosEtta(
				Pagina.getParamOrDefault(
						request, 
						"indExibirGerarArquivosEtta" + etta.getComp_id().getCodEtt(), 
						EstruturaAcessoEtta.SEM_ACESSO_INCLUSAO));
		
    }
    
    
	
	/**
	 * Verifica se um grupo de usuario tem permissao de acessar um acompanhamento de um tipo de acompanhamento
     * @param tipoAcompanhamentoTa
	 * @param gruposUsuario
	 * @return
	 * @throws ECARException
	 */
    public boolean temPermissoesAcessoAcomp(TipoAcompanhamentoTa tipoAcompanhamentoTa, Set gruposUsuario) throws ECARException {
        boolean resultado = false;
        
        try {
        	
        	
            Query q = this.getSession().createQuery("select tipoAcompGrpAcesso " +
                    "from TipoAcompGrpAcesso tipoAcompGrpAcesso " +                    
                    "where tipoAcompGrpAcesso.tipoAcompanhamentoTa = :tipoAcompanhamentoTa " +
                    "and tipoAcompGrpAcesso.sisAtributoSatb in (:gruposUsuarios) " +
                    "and tipoAcompGrpAcesso.acessoInclusao = :acessoInclusao");
            
            q.setMaxResults(1);

            q.setString("acessoInclusao", ValidaPermissao.SIM);
            q.setParameter("tipoAcompanhamentoTa", tipoAcompanhamentoTa);
			q.setParameterList("gruposUsuarios", gruposUsuario);			
			            
            List retorno = q.list();
            if(!retorno.isEmpty()) {
            	resultado = true;
            }
			
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException(e);
        }
        
        return resultado;
        
    }
    
    
    
	/**
	 * Verifica se um grupo de usuario tem permissao de acessar um acompanhamento de um tipo de acompanhamento separado por orgao
     * @param tipoAcompanhamentoTa
	 * @param gruposUsuario
	 * @return
	 * @throws ECARException
	 */
    public boolean temPermissoesAcessoAcompPorOrgao(TipoAcompanhamentoTa tipoAcompanhamentoTa, Set gruposUsuario, OrgaoOrg orgao, UsuarioUsu usuario) throws ECARException {
        boolean resultado = false;
        
        try {
        	
        	
        	TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(null); 
        	OrgaoDao orgDao = new OrgaoDao(null);
        			
        	//se for separado por orgao 
        	if(tipoAcompanhamentoTa != null && tipoAcompanhamentoTa.getIndSepararOrgaoTa() != null && tipoAcompanhamentoTa.getIndSepararOrgaoTa().equals("S")) {
        		//se estiver configurado para somente seus orgaos
        		if(this.temPermissoesAcessoAcompSeusOrgaos(tipoAcompanhamentoTa, gruposUsuario)) {
        			//se o orgao passado como parametro faz parte dos seus orgaos
        			List secretariasSeusOrgaos = orgDao.getListaOrgaosUsuario(usuario, false); 
        			if(secretariasSeusOrgaos != null && orgao != null && secretariasSeusOrgaos.contains(orgao)) 
        				resultado = true;
	        	} else {
	        		//se estiver configurado para todos os orgaos
	        		resultado = true;
	        	}
        			
        	} else {
        		//se nao for separado por orgao
        		resultado = true;
        	}	
        					
      	
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException(e);
        }
        
        return resultado;
        
    }
    
    
  															
										

    /**
	 * Verifica se um grupo de usuario tem permissao de acessar algum acompanhamento
	 * @param gruposUsuario
	 * @return
	 * @throws ECARException
	 */
    public boolean temPermissoesAcessoAcomp(Set gruposUsuario) throws ECARException {
        boolean resultado = false;
        
        try {
            Query q = this.getSession().createQuery("select tipoAcompGrpAcesso " +
                    "from TipoAcompGrpAcesso tipoAcompGrpAcesso " +                    
                    "where tipoAcompGrpAcesso.sisAtributoSatb in (:gruposUsuarios) " +
                    "and tipoAcompGrpAcesso.acessoInclusao = :acessoInclusao");
            
            q.setMaxResults(1);

            q.setString("acessoInclusao", ValidaPermissao.SIM);
			q.setParameterList("gruposUsuarios", gruposUsuario);			
			            
            List retorno = q.list();
            if(!retorno.isEmpty()) {
            	resultado = true;
            }
			
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException(e);
        }
        
        return resultado;
        
    }    
    
    /**
     *
     * @param tipoAcompanhamentoTa
     * @param gruposUsuario
     * @return
     * @throws ECARException
     */
    public boolean temPermissoesAcessoAcompTodosOrgaos(TipoAcompanhamentoTa tipoAcompanhamentoTa, Set gruposUsuario) throws ECARException {
        boolean resultado = false;
        
        try {
        	TipoAcompGrpAcesso tipoAcompGrpAcesso = new TipoAcompGrpAcesso();
        	tipoAcompGrpAcesso.getSepararPorOrgao();
            Query q = this.getSession().createQuery("select tipoAcompGrpAcesso " +
                    "from TipoAcompGrpAcesso tipoAcompGrpAcesso " +                    
                    "where tipoAcompGrpAcesso.tipoAcompanhamentoTa = :tipoAcompanhamentoTa " +
                    "and tipoAcompGrpAcesso.sisAtributoSatb in (:gruposUsuarios) " +
                    "and tipoAcompGrpAcesso.acessoInclusao = :acessoInclusao "+
        			"and (tipoAcompGrpAcesso.separarPorOrgao = :separarPorOrgao or tipoAcompGrpAcesso.separarPorOrgao is null)");
            
            q.setMaxResults(1);

            q.setString("acessoInclusao", ValidaPermissao.SIM);
            q.setParameter("tipoAcompanhamentoTa", tipoAcompanhamentoTa);
            q.setInteger("separarPorOrgao", ValidaPermissao.PERMISSAO_TODOS_ORGAOS);		
            q.setParameterList("gruposUsuarios", gruposUsuario);			
			            
            List retorno = q.list();
            if(!retorno.isEmpty()) {
            	resultado = true;
            }
			
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException(e);
        }
        
        return resultado;
        
    }
    
    
    /**
     *
     * @param tipoAcompanhamentoTa
     * @param gruposUsuario
     * @return
     * @throws ECARException
     */
    public boolean temPermissoesAcessoAcompSeusOrgaos(TipoAcompanhamentoTa tipoAcompanhamentoTa, Set gruposUsuario) throws ECARException {
        boolean resultado = false;
        
        try {
        	TipoAcompGrpAcesso tipoAcompGrpAcesso = new TipoAcompGrpAcesso();
        	tipoAcompGrpAcesso.getSepararPorOrgao();
            Query q = this.getSession().createQuery("select tipoAcompGrpAcesso " +
                    "from TipoAcompGrpAcesso tipoAcompGrpAcesso " +                    
                    "where tipoAcompGrpAcesso.tipoAcompanhamentoTa = :tipoAcompanhamentoTa " +
                    "and tipoAcompGrpAcesso.sisAtributoSatb in (:gruposUsuarios) " +
                    "and tipoAcompGrpAcesso.acessoInclusao = :acessoInclusao "+
        			"and tipoAcompGrpAcesso.separarPorOrgao = :separarPorOrgao");
            
            q.setMaxResults(1);

            q.setString("acessoInclusao", ValidaPermissao.SIM);
            q.setParameter("tipoAcompanhamentoTa", tipoAcompanhamentoTa);
            q.setInteger("separarPorOrgao", ValidaPermissao.PERMISSAO_SEUS_ORGAOS);
            q.setParameterList("gruposUsuarios", gruposUsuario);			
			            
            List retorno = q.list();
            if(!retorno.isEmpty()) {
            	resultado = true;
            }
			
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException(e);
        }
        
        return resultado;
        
    }  
    
    /**
     *
     * @param tipoAcompanhamentoTa
     * @param gruposUsuario
     * @return
     * @throws ECARException
     */
    public boolean temPermissoesAcessoAcompOrgaosSuaResponsabilidade(TipoAcompanhamentoTa tipoAcompanhamentoTa, Set gruposUsuario) throws ECARException {
        boolean resultado = false;
        
        try {
        	TipoAcompGrpAcesso tipoAcompGrpAcesso = new TipoAcompGrpAcesso();
        	tipoAcompGrpAcesso.getSepararPorOrgao();
            Query q = this.getSession().createQuery("select tipoAcompGrpAcesso " +
                    "from TipoAcompGrpAcesso tipoAcompGrpAcesso " +                    
                    "where tipoAcompGrpAcesso.tipoAcompanhamentoTa = :tipoAcompanhamentoTa " +
                    "and tipoAcompGrpAcesso.sisAtributoSatb in (:gruposUsuarios) " +
                    "and tipoAcompGrpAcesso.acessoInclusao = :acessoInclusao "+
        			"and tipoAcompGrpAcesso.separarPorOrgao = :separarPorOrgao");
            
            q.setMaxResults(1);

            q.setString("acessoInclusao", ValidaPermissao.SIM);
            q.setParameter("tipoAcompanhamentoTa", tipoAcompanhamentoTa);
            q.setInteger("separarPorOrgao", ValidaPermissao.PERMISSAO_SUA_RESPONSABILIDADE);		
            q.setParameterList("gruposUsuarios", gruposUsuario);			
			            
            List retorno = q.list();
            if(!retorno.isEmpty()) {
            	resultado = true;
            }
			
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException(e);
        }
        
        return resultado;
        
    } 
    
	//  public boolean temPermissaoAcessoEstrutura(SegurancaECAR seguranca, EstruturaEtt ett){
	//	Set gruposUsuario = seguranca.getGruposAcesso();
	//	Query q = this.getSession().createQuery("select ett " +
	//											"from EstruturaEtt Ett " +
	//											"inner join Ett.tipoAcompanhamentoTa ta " +
	//											"inner join ta.tipoacompTipofuncacompSisatributoTatpfasatbs parecer " +
	//											"inner join ta.tipoacompAbasSisatributoTaabasatbs aba " +
	//											"where Ett = :ett " +
	//											"and((parecer.sisAtributoSatb in (:atributosparecer) and parecer.indLeituraParecer = :leitura) " +
	//											"or  (aba.sisAtributoSatb in (:atributosaba) and aba.indVisualizaAba = :visualiza)) ")
	//								.setParameter("ett", ett)
	//								.setParameterList("atributosparecer", gruposUsuario)
	//								.setString("leitura", Dominios.COM_ACESSO_LEITURA)
	//								.setParameterList("atributosaba", gruposUsuario)
	//								.setString("visualiza", Dominios.COM_ACESSO_LEITURA);
	//	return !q.list().isEmpty();
	//}

	/**
	 * @author Robson
         * @param gruposUsuario
         * @param ett
         * @param acao
         * @return booelan
	 * @throws ECARException
	 * TODO: nao terminado, falta incluir acoes
	 * Funcao que verifica se usuario pode executar determinada acao na Estrutura
	 */
    public boolean PermissaoAcessoGrupoEstrutura(Set gruposUsuario, EstruturaEtt ett, short acao) throws ECARException{
	//XXX: Novas funções de acesso
	try {
		StringBuilder str = new StringBuilder("from EstruturaAcessoEtta etta " +
												"where etta.estruturaEtt = :ett " +
												"and etta.sisAtributoSatb in (:lista) ");
		if(acao == PERMISSAO_ACESSO_SECRETARIA)
			str.append("and etta.indLeiAcompSecpropEtta = :sim ");
		
		else if(acao == PERMISSAO_ACESSO_OUTRA_SECRETARIA)
			str.append("and etta.indIncAcompSecoutrEtta = :sim ");
		
		Query q = this.getSession().createQuery(str.toString());
		
		q.setMaxResults(1);
		q.setParameter("ett", ett);
		q.setParameterList("lista", gruposUsuario);
		q.setString("sim", Dominios.SIM);
		
		return !q.list().isEmpty();
		
	} catch (HibernateException e) {
		this.logger.error(e);
		throw new ECARException(e);
	}
}
    
}