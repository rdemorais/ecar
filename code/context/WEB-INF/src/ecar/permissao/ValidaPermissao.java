/*
 * Created on 14/06/2005
 *
 */
package ecar.permissao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.util.Pagina;

import ecar.api.facade.Funcao;
import ecar.api.facade.ItemEstrutura;
import ecar.dao.EstruturaAcessoDao;
import ecar.dao.EstruturaDao;
import ecar.dao.FuncaoDao;
import ecar.dao.ItemEstrutUsuarioDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.OrgaoDao;
import ecar.dao.TipoAcompGrpAcessoDao;
import ecar.dao.TipoAcompTipofuncacompSisatributoTaTpfaSatbDao;
import ecar.dao.TipoAcompanhamentoDao;
import ecar.dao.TipoFuncAcompDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.EstruturaAcessoEtta;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.ItemEstrutUsuarioIettus;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.UsuarioUsu;

/**
 * @author garten
 */
public class ValidaPermissao {
    
	private Logger logger = null;

	public static final String SIM = "S";
    public static final String NAO = "N";
	
	public static final String EM_MONITORAMENTO = "M";
	public static final String PROPRIA_SECRETARIA = "P";    
	public static final String OUTRAS_SECRETARIAS = "O";
	
	public static final String LEITURA_ACOMP_MONITORADO_SUA_SECRETARIA = "LEITURA_ACOMP_MONITORADO_SUA_SECRETARIA";
	public static final String LEITURA_ACOMP_MONITORADO_OUTRA_SECRETARIA = "LEITURA_ACOMP_MONITORADO_OUTRA_SECRETARIA";
	public static final String LEITURA_ACOMP_SUA_SECRETARIA = "LEITURA_ACOMP_SUA_SECRETARIA";
	public static final String LEITURA_ACOMP_OUTRA_SECRETARIA = "LEITURA_ACOMP_OUTRA_SECRETARIA";
	
	/* VARI�VEIS UTILIZADAS PARA VERIFICAR PERMISS�ES - UTILIZADAS COMO VALORES BIN�RIOS */
	public static final short PERMISSAO_EXCLUIR = 1;
	public static final short PERMISSAO_CONSULTAR = 2;  // se continuar, utilizar, 4, 8, 16, 32, 64, ...
	public static final short PERMISSAO_CONSULTAR_PARECER = 4;

	/* VARI�VEIS UTILIZADAS PARA VERIFICAR PERMISS�ES DE INCLUIR REGISTRO DE ACOMPANHAMENTO  */
	public static final int PERMISSAO_TODOS_ORGAOS = 1;
	public static final int PERMISSAO_SEUS_ORGAOS = 2;  
	public static final int PERMISSAO_SUA_RESPONSABILIDADE = 3;
		
	private short PERMISSOES = 0;
	
	private boolean ignorarPermissoes = false;

	/**
	 * Construtor Valida Permissao.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 */
	public ValidaPermissao() {
		setIgnorarPermissoes(false);

		logger = Logger.getLogger(this.getClass());
		
		try {
			Object ignorarPermissoesContext = String.valueOf("");//Connector.getInstance().getResource("ignorarPermissoes");
			
			if(ignorarPermissoesContext != null) {
				if("true".equals(ignorarPermissoesContext.toString())) {
					setIgnorarPermissoes(true);
				}
			}
		}
		catch(Exception e) {
			// n�o precisa logar exce��o
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		}
	}
	
    /**
     * Verificar, para um item, se o usuario tem permissao para exclui-lo.<br>
     * Pode excluir se usuario x grupo x item x iettus tem ind_exclusao = 'S'.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param ItemEstruturaIett item
     * @param UsuarioUsu usuario
     * @param Set gruposUsuario
     * @return boolean
     */
    public boolean permissaoExcluirItem(ItemEstruturaIett item, UsuarioUsu usuario, Set gruposUsuario) {
    	try {
	    	if(getIgnorarPermissoes()) {
	    		return true;
	    	}
	    	
			Set itemUsuarios = item.getItemEstrutUsuarioIettusesByCodIett();
			Iterator it = itemUsuarios.iterator();
			while(it.hasNext()){
				ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) it.next();
				if(ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_GRUPO) && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())){
					if(SIM.equals(ieUsuario.getIndExcluirIettus()))
						return true;
				}
				if((ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_USUARIO) 
					|| ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_FUNCAO_ACOMPANHAMENTO))
					&& 
					((ieUsuario.getUsuarioUsu() != null && ieUsuario.getUsuarioUsu().equals(usuario)) || (ieUsuario.getSisAtributoSatb() != null && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())))){
					if(SIM.equals(ieUsuario.getIndExcluirIettus()))
						return true;				
				}
			}
			
	        return false;
		}
		catch(Exception e) {
			this.logger.error(e);
	        return false;
		}
    }

    /**
     * Verificar, para uma estrutura, se o usuario pode adicionar um item.<br>
     * Pode adicionar se grupo x etta tem ind_incl_item = 'S'.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param EstruturaEtt estrutura
     * @param Set gruposUsuario
     * @return boolean
     */
    public boolean permissaoAdicionarItem(EstruturaEtt estrutura, Set gruposUsuario) {
    	try {
	    	if( ignorarPermissoes ) {
	    		return true;
	    	}
	    	
	    	for (Iterator it = estrutura.getEstruturaAcessoEttas().iterator(); it.hasNext();) {
				EstruturaAcessoEtta estruturaAcesso = (EstruturaAcessoEtta) it.next();
				if(gruposUsuario.contains(estruturaAcesso.getSisAtributoSatb())){
					 if(estruturaAcesso.getIndIncItemEtta() != null && SIM.equals(estruturaAcesso.getIndIncItemEtta())){
						  return true;				
					 }
				}
			 }
			 return false;		
		}
		catch(Exception e) {
			this.logger.error(e);
	        return false;
		}
    }
    
    /**
     * Verificar, para uma estrutura, se o usuario pode imprimir uma listagem.<br>
     * Pode imprimir se grupo x etta tem getIndExibirImprimirEtta = 'S'.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param EstruturaEtt estrutura
     * @param Set gruposUsuario
     * @return boolean
     */
    public boolean permissaoImprimirListagem(EstruturaEtt estrutura, Set gruposUsuario) {
    	try {
	    	if( ignorarPermissoes ) {
	    		return true;
	    	}
	    	
	    	for (Iterator it = estrutura.getEstruturaAcessoEttas().iterator(); it.hasNext();) {
				EstruturaAcessoEtta estruturaAcesso = (EstruturaAcessoEtta) it.next();
				if(gruposUsuario.contains(estruturaAcesso.getSisAtributoSatb())){
					 if(estruturaAcesso.getIndExibirImprimirEtta() != null && SIM.equals(estruturaAcesso.getIndExibirImprimirEtta())){
						  return true;				
					 }
				}
			 }
			 return false;		
		}
		catch(Exception e) {
			this.logger.error(e);
	        return false;
		}
    }
    
    /**
     * Verificar, para uma estrutura, se o usuario pode gerar arquivos.<br>
     * Pode gerar arquivos se grupo x etta tem getIndExibirGerarArquivosEtta = 'S'.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param EstruturaEtt estrutura
     * @param Set gruposUsuario
     * @return boolean
     */
    public boolean permissaoGerarArquivos(EstruturaEtt estrutura, Set gruposUsuario) {
    	try {
	    	if( ignorarPermissoes ) {
	    		return true;
	    	}
	    	
	    	for (Iterator it = estrutura.getEstruturaAcessoEttas().iterator(); it.hasNext();) {
				EstruturaAcessoEtta estruturaAcesso = (EstruturaAcessoEtta) it.next();
				if(gruposUsuario.contains(estruturaAcesso.getSisAtributoSatb())){
					 if(estruturaAcesso.getIndExibirGerarArquivosEtta() != null && SIM.equals(estruturaAcesso.getIndExibirGerarArquivosEtta())){
						  return true;				
					 }
				}
			 }
			 return false;		
		}
		catch(Exception e) {
			this.logger.error(e);
	        return false;
		}
    }
    

    /**
     * Verificar, para um item, se o usuario pode altera-lo.<br>
     * Pode alterar se usuario x grupo x item x iettus tem ind_edicao = 'S'.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param ItemEstruturaIett item
     * @param UsuarioUsu usuario
     * @param Set gruposUsuario
     * @return boolean
     */
    public Boolean permissaoAlterarItem(ItemEstruturaIett item, UsuarioUsu usuario, Set gruposUsuario, EstruturaFuncaoEttf estruturaFuncaoEttf) {
    	try {
    		if(getIgnorarPermissoes()) {
    			return true;
    		}
    		
    		Set itemUsuarios = item.getItemEstrutUsuarioIettusesByCodIett();
    		Iterator it = itemUsuarios.iterator();

    		while(it.hasNext()){
    			ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) it.next();
    			
    			/* Procurar saber que informa��o � essa para documentar*/
    			if(ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_GRUPO) && 
    					gruposUsuario.contains(ieUsuario.getSisAtributoSatb())) {
    				
    				/* Se o usu�rio tem permiss�o para editar o item */
    				if(SIM.equals(ieUsuario.getIndEdicaoIettus())) {
    					
    					/*Verfica se o planejamento do item est� bloqueado */
    					if (item.getIndBloqPlanejamentoIett() == null || item.getIndBloqPlanejamentoIett().equals(NAO)) {
    						return true;
    					} else {
    						//Para o item bloqueado:
    						//incluindo teste para pontos criticos (mantis 0011072)
    						//se item estiver bloqueado e a fun��o pode ser bloqueada, ent�o
    						//n�o � permitido alterar
    						Funcao funcao = new Funcao(estruturaFuncaoEttf);
    						if (estruturaFuncaoEttf == null || estruturaFuncaoEttf.getIndPodeBloquearEttf().equals(NAO) ||
    								(funcao.getNome().equals(FuncaoDao.NOME_FUNCAO_PONTOS_CRITICOS) && funcao.podeSerBloqueada() == false)){
    							return true;
    						}
    					}
    				}
    			} 
    			
    			if((ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_USUARIO) 
    					|| ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_FUNCAO_ACOMPANHAMENTO)) 
    					&& ( (ieUsuario.getUsuarioUsu() != null && ieUsuario.getUsuarioUsu().equals(usuario)) 
    						|| (ieUsuario.getSisAtributoSatb() != null && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())))){
    				
    				/*Verifica se o usu�rio pode editar o item*/
    				if(SIM.equals(ieUsuario.getIndEdicaoIettus())) {
    					if (item.getIndBloqPlanejamentoIett() == null || 
    							NAO.equals(item.getIndBloqPlanejamentoIett())) {
    						return true;
    					} else {
    						Funcao funcao = new Funcao(estruturaFuncaoEttf);
    						if (estruturaFuncaoEttf == null || estruturaFuncaoEttf.getIndPodeBloquearEttf() == null || 
    								estruturaFuncaoEttf.getIndPodeBloquearEttf().equals(NAO)||
    								(funcao.getNome().equals(FuncaoDao.NOME_FUNCAO_PONTOS_CRITICOS) && funcao.podeSerBloqueada() == false)){
    							return true;
    						}
    					}
    				}
    			}
    		}
    		
    		/*Corre��o homologacao v7.0 */
    		/* Caso o item esteja bloqueado verificar se o usu�rio (ou os grupos) � fun��o de acompanhamento com permiss�o de alterar */
    		if (estruturaFuncaoEttf!=null &&  ( item.getIndBloqPlanejamentoIett() != null || SIM.equals(item.getIndBloqPlanejamentoIett()  ) ) ) {
    			
    			// Lista com as fun��es de acompanhamento para as quais o item n�o est� bloqueado  
				Iterator itLiberadoParaTpfa =  estruturaFuncaoEttf.getLibTipoFuncAcompTpfas().iterator();
			
				// Lista com as funcoes de acompanhamento do usu�rio 
				List listTipoFuncaoUsuario = (new TipoFuncAcompDao(null)).getFuncoesAcompNaEstruturaDoUsuario(item, usuario, gruposUsuario);
		
				while (itLiberadoParaTpfa.hasNext()){
					TipoFuncAcompTpfa liberadoParaTpfa = (TipoFuncAcompTpfa) itLiberadoParaTpfa.next();

					if (listTipoFuncaoUsuario!= null &&  listTipoFuncaoUsuario.contains(liberadoParaTpfa)){
						return true;
					}
				}
    		}
    		
    		return false;
    	}
    	catch(Exception e) {
    		this.logger.error(e);
    		return false;
    	}
    }
    
    /**
     * Verificar, para um item, se o usuario pode ler seus dados e funcoes.<br>
     * Pode consultar se usuario x grupo x item x iettus tem<br> 
     * ind_leitura = 'S'.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param ItemEstruturaIett item
     * @param UsuarioUsu usuario
     * @param Set gruposUsuario
     * @return boolean
     */
    public boolean permissaoConsultarItem(ItemEstruturaIett item, UsuarioUsu usuario, Set gruposUsuario) {
    	try {
	    	if(getIgnorarPermissoes()) {
	    		return true;
	    	}
	    	
			Set itemUsuarios = item.getItemEstrutUsuarioIettusesByCodIett();
	    	
	    	if(itemUsuarios == null) {
	    		return true;
	    	}
	
	    	Iterator it = itemUsuarios.iterator();
			while(it.hasNext()){
				ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) it.next();
				if(ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_GRUPO) && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())){
					if(ieUsuario.getIndLeituraIettus().equals(SIM))
						return true;
				}
				if((ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_USUARIO) 
						|| ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_FUNCAO_ACOMPANHAMENTO))				
						&& 
						((ieUsuario.getUsuarioUsu() != null && ieUsuario.getUsuarioUsu().equals(usuario)) || (ieUsuario.getSisAtributoSatb() != null && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())))){
					if(ieUsuario.getIndLeituraIettus().equals(SIM))
						return true;				
				}
			}
	        return false;
    	}
    	catch(Exception e) {
			this.logger.error(e);
	        return false;
    	}
    }
    
    /**
     * Verificar, para um item, se o usuario pode ativar o monitoramento.<br>
     * Pode ativar o monitoramento se<br>
     * usuario x grupo x item x iettus tem ind_ativ_monit = 'S' e<br>
     * item.ind_monitoramento = 'N'.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param ItemEstruturaIett item
     * @param UsuarioUsu usuario
     * @param Set gruposUsuario
     * @return boolean
     */
    public boolean permissaoAtivarMonitoramentoItem(ItemEstruturaIett item, UsuarioUsu usuario, Set gruposUsuario) {
    	try {
	    	if(getIgnorarPermissoes()) {
	    		return true;
	    	}
	    	
			Set itemUsuarios = item.getItemEstrutUsuarioIettusesByCodIett();
			if(itemUsuarios != null){
				Iterator it = itemUsuarios.iterator();
				while(it.hasNext()){
					ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) it.next();
					if(ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_GRUPO) && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())){
						if(ieUsuario.getIndAtivMonitIettus().equals(SIM) && (item.getIndMonitoramentoIett() == null || item.getIndMonitoramentoIett().equals(NAO)))
							return true;
					}
					if((ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_USUARIO) 
							|| ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_FUNCAO_ACOMPANHAMENTO))
							&& 
							((ieUsuario.getUsuarioUsu() != null && ieUsuario.getUsuarioUsu().equals(usuario)) || (ieUsuario.getSisAtributoSatb() != null && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())))){
						if(ieUsuario.getIndAtivMonitIettus().equals(SIM) && (item.getIndMonitoramentoIett() == null || item.getIndMonitoramentoIett().equals(NAO)))
							return true;				
					}
				}			
			}
	        return false;       				        
    	}
    	catch(Exception e) {
			this.logger.error(e);
	        return false;
    	}
    }
    

    /**
     * Verificar, para um item, se o usuario pode desativar o monitoramento.<br>
     * Pode desativar o monitoramento se<br>
     * usuario x grupo x item x iettus tem ind_desat_monit = 'S' e<br>
     * item.ind_monitoramento = 'S'<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param ItemEstruturaIett item
     * @param UsuarioUsu usuario
     * @param Set gruposUsuario
     * @return boolean
     */
    public boolean permissaoDesativarMonitoramentoItem(ItemEstruturaIett item, UsuarioUsu usuario, Set gruposUsuario) {
    	try {
	    	if(getIgnorarPermissoes()) {
	    		return true;
	    	}
	    	
			Set itemUsuarios = item.getItemEstrutUsuarioIettusesByCodIett();
			Iterator it = itemUsuarios.iterator();
			while(it.hasNext()){
				ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) it.next();
				if(ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_GRUPO) && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())){
					if(ieUsuario.getIndDesatMonitIettus().equals(SIM) && (item.getIndMonitoramentoIett() != null && item.getIndMonitoramentoIett().equals(SIM)))
						return true;
				}
				if((ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_USUARIO) 
						|| ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_FUNCAO_ACOMPANHAMENTO))
						&& 
						((ieUsuario.getUsuarioUsu() != null && ieUsuario.getUsuarioUsu().equals(usuario)) || (ieUsuario.getSisAtributoSatb() != null && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())))){
					if(ieUsuario.getIndDesatMonitIettus().equals(SIM) && (item.getIndMonitoramentoIett() != null && item.getIndMonitoramentoIett().equals(SIM)))
						return true;				
				}
			}       		
	        return false;
    	}
    	catch(Exception e) {
			this.logger.error(e);
	        return false;
    	}
    }


    /**
     * Verificar, para um item, se o usuario pode bloquear planejamento.<br>
     * Pode bloquear o planejamento se<br>
     * usuario x grupo x item x iettus tem ind_bloq_plan = 'S' e<br>
     * item.ind_bloq_planejamento = 'N'<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param ItemEstruturaIett item
     * @param UsuarioUsu usuario
     * @param Set gruposUsuario
     * @return boolean
     */
    public boolean permissaoBloquearPlanejamentoItem(ItemEstruturaIett item, UsuarioUsu usuario, Set gruposUsuario) {
    	try {
	    	if(getIgnorarPermissoes()) {
	    		return true;
	    	}
	    	
			Set itemUsuarios = item.getItemEstrutUsuarioIettusesByCodIett();
			if(itemUsuarios != null){
				Iterator it = itemUsuarios.iterator();
				while(it.hasNext()){
					ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) it.next();
					if(ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_GRUPO) && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())){
						if(ieUsuario.getIndBloqPlanIettus().equals(SIM) && (item.getIndBloqPlanejamentoIett() == null || item.getIndBloqPlanejamentoIett().equals(NAO)))
							return true;
					}
					if((ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_USUARIO) 
							|| ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_FUNCAO_ACOMPANHAMENTO))
							&& 
							((ieUsuario.getUsuarioUsu() != null && ieUsuario.getUsuarioUsu().equals(usuario)) || (ieUsuario.getSisAtributoSatb() != null && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())))){
						if(ieUsuario.getIndBloqPlanIettus().equals(SIM) && (item.getIndBloqPlanejamentoIett() == null || item.getIndBloqPlanejamentoIett().equals(NAO)))
							return true;				
					}
				}	
			}		       		        				
	        return false;
    	}
    	catch(Exception e) {
			this.logger.error(e);
	        return false;
    	}
    }

    /**
     * Verificar, para um item, se o usuario pode liberar o planejamento.<br>
     * Pode liberar o planejamento se<br>
     * usuario x grupo x item x iettus tem ind_desbl_plan = 'S' e<br>
     * item.ind_bloq_plan = 'S'<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param ItemEstruturaIett item
     * @param UsuarioUsu usuario
     * @param Set gruposUsuario
     * @return boolean
     */
    public boolean permissaoLiberarPlanejamentoItem(ItemEstruturaIett item, UsuarioUsu usuario, Set gruposUsuario) {
    	try {
	    	if(getIgnorarPermissoes()) {
	    		return true;
	    	}
	    	
			Set itemUsuarios = item.getItemEstrutUsuarioIettusesByCodIett();
			Iterator it = itemUsuarios.iterator();
			while(it.hasNext()){
				ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) it.next();
				if(ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_GRUPO) && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())){
					if(ieUsuario.getIndDesblPlanIettus().equals(SIM) && (item.getIndBloqPlanejamentoIett() != null && item.getIndBloqPlanejamentoIett().equals(SIM)))
						return true;
				}
				if((ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_USUARIO) 
						|| ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_FUNCAO_ACOMPANHAMENTO))
						&& 
						((ieUsuario.getUsuarioUsu() != null && ieUsuario.getUsuarioUsu().equals(usuario)) || (ieUsuario.getSisAtributoSatb() != null && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())))){
					if(ieUsuario.getIndDesblPlanIettus().equals(SIM) && (item.getIndBloqPlanejamentoIett() != null && item.getIndBloqPlanejamentoIett().equals(SIM)))
						return true;				
				}
			}       		        						
	        return false;
    	}
    	catch(Exception e) {
			this.logger.error(e);
	        return false;
    	}
    }
    
       
    /**
     * Devolve true quando o usuario faz parte de algum grupo em estrutura_acesso que tem permissao de incluir 
     * referencia em monitoramento.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param Set gruposUsuario
     * @return boolean
     * @throws ECARException
     */
//    public boolean permissaoAcessoReferenciaMonitoramento(Set gruposUsuario) throws ECARException {
//    	if(getIgnorarPermissoes()) {
//    		return true;
//    	}
//    	
//        return new EstruturaAcessoDao(null).temPermissoesAcessoAcompMonitorado(gruposUsuario);
//    }

    /**
     * Devolve true quando o usuario faz parte de algum grupo em estrutura_acesso que tem permissao de incluir 
     * referencia para sua pr�pria secretaria.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param Set gruposUsuario
     * @return boolean
     * @throws ECARException
     */
//	public boolean permissaoAcessoReferenciaSecretaria (Set gruposUsuario) throws ECARException {
//    	if(getIgnorarPermissoes()) {
//    		return true;
//    	}
//    	
//        return new EstruturaAcessoDao(null).temPermissoesAcessoSecretaria(gruposUsuario);		
//    }

	/**
     * Devolve true quando o usuario faz parte de algum grupo em estrutura_acesso que tem permissao de incluir 
     * referencia para qualquer secretaria que n�o seja sua propria.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param Set gruposUsuario
     * @return boolean
     * @throws ECARException
     */
//	public boolean permissaoAcessoReferenciaOutraSecretaria (Set gruposUsuario) throws ECARException {
//    	if(getIgnorarPermissoes()) {
//    		return true;
//    	}
//    	
//        return new EstruturaAcessoDao(null).temPermissoesAcessoOutraSecretaria(gruposUsuario);
//    }

    /**
     * Devolve true quando o usuario faz parte de algum grupo em estrutura_acesso que tem permissao de incluir 
     * referencia em monitoramento para itens de uma estrutura.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param Set gruposUsuario
     * @param EstruturaEtt estrutura
     * @return boolean
     * @throws ECARException
     */
//	public boolean permissaoAcessoReferenciaMonitoramento (Set gruposUsuario, EstruturaEtt estrutura) throws ECARException {
//    	if(getIgnorarPermissoes()) {
//    		return true;
//    	}
//    	
//  return new EstruturaAcessoDao(null).temPermissoesAcessoAcompMonitorado(gruposUsuario, estrutura);
//    }

    /**
     * Devolve true quando o usuario faz parte de algum grupo em estrutura_acesso que tem permissao de incluir 
     * referencia para a secretaria do usu�rio para itens de uma estrutura.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param Set gruposUsuario
     * @param EstruturaEtt estrutura
     * @return boolean
     * @throws ECARException
     */
//	public boolean permissaoAcessoReferenciaSecretaria (Set gruposUsuario, EstruturaEtt estrutura) throws ECARException {
//    	if(getIgnorarPermissoes()) {
//    		return true;
//    	}
//    	
//        return new EstruturaAcessoDao(null).temPermissoesAcessoSecretaria(gruposUsuario, estrutura);		
//    }
	
	/**
     * Devolve true quando o usuario faz parte de algum grupo em <br>
     * estrutura_acesso que tem permissao de incluir  referencia <br>
     * para a qualquer secreataria que n�o seja a prorpria secretaria<br>
     * do usu�rio para itens de uma estrutura.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param Set gruposUsuario
     * @param EstruturaEtt estrutura
     * @return boolean
     * @throws ECARException
     */
//	public boolean permissaoAcessoReferenciaOutraSecretaria (Set gruposUsuario, EstruturaEtt estrutura) throws ECARException {
//    	if(getIgnorarPermissoes()) {
//    		return true;
//    	}
//    	
//        return new EstruturaAcessoDao(null).temPermissoesAcessoOutraSecretaria(gruposUsuario, estrutura);
//    }
	
	/**
     * Verifica se um usu�rio tem permiss�o para acesso � elabora��o de acompanhamento de um item.<br>
     * As condi��es para isso s�o:<br>
     * (TODO Cade as condicoes?)
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param ItemEstruturaIett item
     * @param UsuarioUsu usuario
     * @param Set gruposUsuario
     * @return boolean
     * @throws ECARException
     */
//	public boolean permissaoAcessoItemElaboracaoAcompEmMonitoramento(ItemEstruturaIett item, UsuarioUsu usuario, Set gruposUsuario) throws ECARException{
//    	if(getIgnorarPermissoes()) {
//    		return true;
//    	}
//    	
//		return permissaoAcessoItemElaboracaoAcomp(item, usuario, gruposUsuario, EM_MONITORAMENTO);
//	}

//	/**
// 	 * Verifica se usuario tem permissao de acesso ao item da propria da secretaria.<br>
//	 * 
//	 * @author N/C
//     * @since N/C
//     * @version N/C
//	 * @param ItemEstruturaIett item
//	 * @param UsuarioUsu usuario
//	 * @param Set gruposUsuario
//	 * @return boolean
//	 * @throws ECARException
//	 */
//	public boolean permissaoAcessoItemElaboracaoAcompPropriaSecretaria(ItemEstruturaIett item, UsuarioUsu usuario, Set gruposUsuario) throws ECARException{
//    	if(getIgnorarPermissoes()) {
//    		return true;
//    	}
//    	
//		return permissaoAcessoItemElaboracaoAcomp(item, usuario, gruposUsuario, PROPRIA_SECRETARIA);
//	}

	/**
	 * Verifica se usuario tem permissao de acesso ao item.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param ItemEstruturaIett item
	 * @param UsuarioUsu usuario
	 * @param Set gruposUsuario
	 * @return boolean
	 * @throws ECARException
	 */
//	public boolean permissaoAcessoItemElaboracaoAcompOutraSecretaria(ItemEstruturaIett item, UsuarioUsu usuario, Set gruposUsuario) throws ECARException{
//    	if(getIgnorarPermissoes()) {
//    		return true;
//    	}
//    	
//		return permissaoAcessoItemElaboracaoAcomp(item, usuario, gruposUsuario, OUTRAS_SECRETARIAS);
//	}

	/**
	 * Verifica se usuario tem permissao de acesso ao item.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param ItemEstruturaIett item
	 * @param UsuarioUsu usuario
	 * @param Set gruposUsuario
	 * @param String tipoAcesso
	 * @return boolean
	 * @throws ECARException
	 */
	public boolean permissaoAcessoItemElaboracaoAcomp(ItemEstruturaIett item, UsuarioUsu usuario, Set gruposUsuario, String tipoAcesso, TipoAcompanhamentoTa tipoAcompanhamentoTa) throws ECARException{
    	if(getIgnorarPermissoes()) {
    		return true;
    	}
    	
    	List itensUsuario = new ItemEstrutUsuarioDao(null).getItemEstrutUsuarioItemOrigemAndEmitePosicaoAndInfAndamento(item.getCodIett());
		Iterator it = itensUsuario.iterator();
		while(it.hasNext()){
			ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) it.next();
			if(ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_GRUPO) && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())){
				return true;
			}
			if(((ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_USUARIO)
					|| ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_FUNCAO_ACOMPANHAMENTO))
					&& 
					(ieUsuario.getUsuarioUsu() != null && ieUsuario.getUsuarioUsu().equals(usuario)) || (ieUsuario.getSisAtributoSatb() != null && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())))){
				return true;				
			}
		}
		
		if (permissaoAcessoReferencia(tipoAcompanhamentoTa, gruposUsuario)) {
			return true;
		}
		
//		if(tipoAcesso.equals(EM_MONITORAMENTO)){
//			if(permissaoAcessoReferenciaMonitoramento(gruposUsuario, item.getEstruturaEtt()))
//				return true;			
//		}		
//		if(tipoAcesso.equals(PROPRIA_SECRETARIA)){
//			if(permissaoAcessoReferenciaSecretaria(gruposUsuario, item.getEstruturaEtt()))
//				return true;			
//		}
//		if(tipoAcesso.equals(OUTRAS_SECRETARIAS)){
//			if(permissaoAcessoReferenciaOutraSecretaria(gruposUsuario, item.getEstruturaEtt()))
//				return true;			
//		}
		
		return false;
	}
	
	
	
	/**
	 * Remove da lista os itens em que o usu�rio(ou seus grupos) n�o tenha permissao 
	 * de acesso na Estrutura do item.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param List lista
	 * @param Set gruposUsuario
	 * @param String tipoAcesso
	 * @return List
	 * @throws ECARException
	 */
	/*public List removeItensSemAcessoElaboracaoAcomp(List lista, Set gruposUsuario, String tipoAcesso, TipoAcompanhamentoTa tipoAcompanhamentoTa) throws ECARException{
		Iterator it = lista.iterator();
		
		while(it.hasNext()){
			ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) it.next();
			
			

			
			if (!permissaoAcessoReferencia(tipoAcompanhamentoTa, gruposUsuario)
			
			if(tipoAcesso.equals(EM_MONITORAMENTO)) {
				if(!permissaoAcessoReferenciaMonitoramento(gruposUsuario, itemEstrutura.getEstruturaEtt())) {
					it.remove();
				}
			} else if(tipoAcesso.equals(PROPRIA_SECRETARIA)) {
				if(!permissaoAcessoReferenciaSecretaria(gruposUsuario, itemEstrutura.getEstruturaEtt())) {
					it.remove();
				}
			} else if(tipoAcesso.equals(OUTRAS_SECRETARIAS)) {
				if(!permissaoAcessoReferenciaOutraSecretaria(gruposUsuario, itemEstrutura.getEstruturaEtt())) {
					it.remove();
				}
			} 
		}
		
		return lista;
	}*/

    /**
     * Descobre se o usuario tem acesso a algum item filho, neto, ..., do item passado como param.<br>
     * <p>
     * Obs.: Pode ter performance melhorada, por exemplo instanciar uma lista na classe,
     * 		executando o select somente uma vez por tela. Depois por item, em m�todo 
     * 		separado, realizar a verifica��o se filhos, netos do item est� na lista.
     *  
     * @author N/C
     * @since N/C
     * @version N/C
     * @param ItemEstruturaIett item
     * @param UsuarioUsu usuario
     * @param Set gruposUsuario
     * @return boolean
     * @throws ECARException
     */
    public boolean permissaoAcessoItensFilhos(ItemEstruturaIett item, UsuarioUsu usuario, Set gruposUsuario) throws ECARException {
    	if( ignorarPermissoes ) {
    		return true;
    	}
    	
    	try{
    		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
    		EstruturaDao estruturaDao = new EstruturaDao(null);

//    		List estruturas = estruturaDao.getDescendentes(item.getEstruturaEtt());
    		List estruturas = estruturaDao.getListaEstruturas(item.getEstruturaEtt());
	        if(estruturas != null && estruturas.size() > 0){
	        	ArrayList<Long> codEtt = new ArrayList<Long>();
	        	
	        	for (Iterator itEtt = estruturas.iterator(); itEtt.hasNext();) {
					EstruturaEtt ett = (EstruturaEtt) itEtt.next();
					codEtt.add(ett.getCodEtt());
				}
	        	
	        	StringBuilder sql = new StringBuilder(
	        			 "select item " +
			                "from ItemEstruturaIett item " +
			                "join item.estruturaEtt as estrutura " + 
							"join item.itemEstrutUsuarioIettusesByCodIett as iettus " +
			                "where estrutura.codEtt in (:estruturas) " +
			                "and ( iettus.usuarioUsu.codUsu = :usuario"	        	
	        	);
					
	        	ArrayList<Long> codSatb = new ArrayList<Long>();
				if(gruposUsuario != null && gruposUsuario.size() > 0) {
					sql.append( " or iettus.sisAtributoSatb.codSatb in (:grupos)" );

					for (Iterator itSatb = gruposUsuario.iterator(); itSatb.hasNext();) {
						SisAtributoSatb satb = (SisAtributoSatb) itSatb.next();
						codSatb.add(satb.getCodSatb());
					}
				}
				
				sql.append( " ) " );
	
	            Query q = estruturaDao.getSession().createQuery(sql.toString());
	            
	            q.setParameterList("estruturas", codEtt);
	            q.setLong("usuario", usuario.getCodUsu().longValue());
	
	            if(gruposUsuario != null && gruposUsuario.size() > 0){
	            	q.setParameterList("grupos", codSatb);
				}
	    		
	            List itensFilhos = q.list();
	            
	            for (Iterator it = itensFilhos.iterator(); it.hasNext();) {
	            	ItemEstruturaIett itemFilho = (ItemEstruturaIett) it.next();
	            	
	            	/* Verifica se item � filho, neto, bisneto do item passado como par�metro */
	            	if((itemEstruturaDao.getAscendentes(itemFilho)).contains(item)) {
	            		return true;
	            	}
	    		}
			}
    	}catch(HibernateException e){
			this.logger.error(e);
    		throw new ECARException("");
    	}
	        
        return false;
    }
	
	/**
	 * Retorna verdadeiro se um usu�rio tem permiss�o para 
	 * Informa��o de realizado f�sico Pode informar realizado f�sico  se
     * usuario x grupo x item x iettus tem ind_inf_andamento = 'S' e.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
	 * @param ItemEstruturaIett item
	 * @param UsuarioUsu usuario
	 * @param Set gruposUsuario
	 * @return boolean
	 * @throws ECARException
	 */
	public boolean permissaoInformarRealizadoFisico(ItemEstruturaIett item, UsuarioUsu usuario, Set gruposUsuario) throws ECARException{		
    	if(getIgnorarPermissoes()) {
    		return true;
    	}
    	
		Set itensUsuario = item.getItemEstrutUsuarioIettusesByCodIett();
		if(itensUsuario != null){
			Iterator it = itensUsuario.iterator();
			while(it.hasNext()){
				ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) it.next();
				if(ieUsuario.getItemEstruturaIett().equals(item) && ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_GRUPO) && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())){
					if(ieUsuario.getIndInfAndamentoIettus().equals(SIM) || ieUsuario.getIndInfAndamentoIettus().equals(SIM))
						return true;
				}
				if(ieUsuario.getItemEstruturaIett().equals(item) 
						&& ((ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_USUARIO) 
								|| ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_FUNCAO_ACOMPANHAMENTO))
								&& 
								((ieUsuario.getUsuarioUsu() != null && ieUsuario.getUsuarioUsu().equals(usuario)) || (ieUsuario.getSisAtributoSatb() != null && gruposUsuario.contains(ieUsuario.getSisAtributoSatb()))))){
					if(ieUsuario.getIndInfAndamentoIettus().equals(SIM) || ieUsuario.getIndInfAndamentoIettus().equals(SIM))
						return true;				
				}
			}					
		}
		return false;
	}
	
	/**
	 * Verifica se usuario tem permissao para Informar parecer.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param ItemEstruturaIett item
	 * @param UsuarioUsu usuario
	 * @param Set gruposUsuario
	 * @return boolean
	 * @throws ECARException
	 */
	public boolean permissaoInformarParecer(ItemEstruturaIett item, UsuarioUsu usuario, Set gruposUsuario) throws ECARException{		
    	if(getIgnorarPermissoes()) {
    		return true;
    	}
    	
		Set itensUsuario = item.getItemEstrutUsuarioIettusesByCodIett();
		if(itensUsuario != null){
			Iterator it = itensUsuario.iterator();
			while(it.hasNext()){
				ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) it.next();
				if(ieUsuario.getItemEstruturaIett().equals(item) && ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_GRUPO) && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())){
					if(ieUsuario.getIndEmitePosIettus().equals(SIM) || ieUsuario.getIndEmitePosIettus().equals(SIM))
						return true;
				}
				if(ieUsuario.getItemEstruturaIett().equals(item) 
						&& ((ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_USUARIO) 
								|| ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_FUNCAO_ACOMPANHAMENTO))
								&& 
								((ieUsuario.getUsuarioUsu() != null && ieUsuario.getUsuarioUsu().equals(usuario)) || (ieUsuario.getSisAtributoSatb() != null && gruposUsuario.contains(ieUsuario.getSisAtributoSatb()))))){
					if(ieUsuario.getIndEmitePosIettus().equals(SIM) || ieUsuario.getIndEmitePosIettus().equals(SIM))
						return true;				
				}
			}					
		}
		return false;
	}
	
	/**
	 * M�todo alternativo aos m�todos permissaoConsultarItem e permissaoExcluirItem
	 * Agrupa numa variavel binaria os valores das permissoes, utilizando a 
	 *    opera��o binaria OU ("|")<br>
	 * ex.:<br>
	 * * 0000 ****** 1010<br>
	 * | 1000 ******| 0011<br>
	 * **-------- ***** --------<br>
	 * * 1000 ****** 1011<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param ItemEstruturaIett item
	 * @param UsuarioUsu usuario
	 * @param Set gruposUsuario
	 * @throws ECARException
	 */
	public void permissoesItem(ItemEstruturaIett item, UsuarioUsu usuario, Set gruposUsuario) throws ECARException {
		try{
			PERMISSOES = 0;
			
			StringBuilder query = new StringBuilder(
					"select distinct itemUsuario from ItemEstrutUsuarioIettus itemUsuario" +                
					" where itemUsuario.itemEstruturaIett.codIett = :codIett" +
					" and ( itemUsuario.usuarioUsu.codUsu = :codUsu");
			
			if(gruposUsuario != null && gruposUsuario.size() > 0) 
				query.append( " or itemUsuario.sisAtributoSatb.codSatb in (:grupos)" ) ;
			
			query.append(" )" );
			
			Query q = new ItemEstrutUsuarioDao(null).getSession().createQuery(query.toString());   
	    	q.setLong("codIett", item.getCodIett().longValue());
	        q.setLong("codUsu", usuario.getCodUsu().longValue());
	        
	        ArrayList<Long> codSatb = new ArrayList<Long>();
	        if(gruposUsuario != null && gruposUsuario.size() > 0){
	        	
	        	for (Iterator itSatb = gruposUsuario.iterator(); itSatb.hasNext();) {
					codSatb.add( ((SisAtributoSatb)itSatb.next()).getCodSatb() );
				}
	        	q.setParameterList("grupos", codSatb);
	        }
			
			List lista = q.list();
			
			for (Iterator it = lista.iterator(); it.hasNext();) {
				ItemEstrutUsuarioIettus itemUsuario = (ItemEstrutUsuarioIettus) it.next();
				if(SIM.equals(itemUsuario.getIndExcluirIettus())){
					PERMISSOES |= PERMISSAO_EXCLUIR;
				}
				if(SIM.equals(itemUsuario.getIndLeituraIettus())){
					PERMISSOES |= PERMISSAO_CONSULTAR;
				}
				if(SIM.equals(itemUsuario.getIndLeituraParecerIettus())){
					PERMISSOES |= PERMISSAO_CONSULTAR_PARECER;
				}
			}
		}catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}
    }
	
	/**
	 * Verifica se tem permissao para consultar - valor na vari�vel binaria<br>
	 *      utilizando a opera��o E ("&")<br>
	 * obs.:  ver m�todo permissoesItem, necessita da sua utiliza��o.<br>
	 * ex.:<br>
	 * ******** 1100<br>
	 * ******& 0110<br>
	 * ********______<br>
	 * ******** 0100<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return boolean
	 */
	public boolean permissaoConsultarItem() {
    	if( ignorarPermissoes ) {
    		return true;
    	}
    	
		if((PERMISSOES & PERMISSAO_CONSULTAR) == PERMISSAO_CONSULTAR)
			return true;
		
        return false;
    }
	
	public boolean permissaoConsultarParecerItem() {
    	if( ignorarPermissoes ) {
    		return true;
    	}
    	
		if((PERMISSOES & PERMISSAO_CONSULTAR_PARECER) == PERMISSAO_CONSULTAR_PARECER)
			return true;
		
        return false;
    }
	
	
	/**
	 * Verifica se tem permissao para excluir - valor na vari�vel binaria
	 * 	    utilizando a opera��o E ("&")<br>
	 * obs.:  ver m�todo permissoesItem, necessita da sua utiliza��o.<br>
	 * 
	 * ex.:<br>
	 * ** 1100<br>
	 *& 0110<br>
	 * ** --------<br>
	 * ** 0100<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return boolean
	 */
	public boolean permissaoExcluirItem() {
    	if( ignorarPermissoes ) {
    		return true;
    	}
    	
		if((PERMISSOES & PERMISSAO_EXCLUIR) == PERMISSAO_EXCLUIR)
			return true;
		
        return false;
    }

	
    /**
     * Verificar se o usuario tem permissao de leitura de acompanhamento para um item.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param AcompReferenciaItemAri ari
     * @param UsuarioUsu usuarioUsu
     * @param Set gruposUsuario
     * @return boolean
     * @throws ECARException
     */
	@Deprecated
	public boolean permissaoLeituraAcompanhamento(AcompReferenciaItemAri ari, UsuarioUsu usuarioUsu, Set gruposUsuario) throws ECARException{
		
		
		try {
	    	/*
			if(getIgnorarPermissoes()) {
	    		return true;
	    	}
	    	*/
			
			if(Boolean.TRUE) { 
				//FOI feito porque demora muito o carregamento da página()
				return true;
			}
			
			String indItemMonitoradosAri = ari.getIndItemMonitoradosAri();
			
			// Mantis: 10511
			//if(!"S".equals(indItemMonitoradosAri)
			//		&& (permissaoAcessoReferenciaOutraSecretaria(gruposUsuario, ari.getItemEstruturaIett().getEstruturaEtt())
			//				|| permissaoAcessoReferenciaSecretaria(gruposUsuario, ari.getItemEstruturaIett().getEstruturaEtt()))) {
				
			//	return true;
			//}

			//OrgaoOrg orgaoAri = ari.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett();
			
			// monitorados de sua responsabilidade:
			// Listar registros de acompanhamento dos itens com indicador de monitorado e em que o usu�rio esta relacionado
			// com alguma fun��o de acompanhamento para o item (tipo "F" em tb_item_estrutura_usuario_iettus)
			if("S".equals(indItemMonitoradosAri) 
					//&& permissaoLeituraAcompSuaResponsabilidade(gruposUsuario, ari.getItemEstruturaIett().getEstruturaEtt())) {
					){
				Set itensUsuario = ari.getItemEstruturaIett().getItemEstrutUsuarioIettusesByCodIett();
				Iterator it = itensUsuario.iterator();
				while(it.hasNext()){
					ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) it.next();
					
					if(ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_FUNCAO_ACOMPANHAMENTO)
							&& 
							((ieUsuario.getUsuarioUsu() != null && ieUsuario.getUsuarioUsu().equals(usuarioUsu)) || (ieUsuario.getSisAtributoSatb() != null && gruposUsuario.contains(ieUsuario.getSisAtributoSatb())))){
							
						return true;
					}
					
					//Merge feito por Patricia da parte de Davi no Ecar TUR
					// Verifica se o usu�rio ou o grupo do usu�rio tem permiss�o de ler parecer do acompanhamento
					if ((usuarioUsu.equals(ieUsuario.getUsuarioUsu()) || gruposUsuario.contains(ieUsuario.getSisAtributoSatb())) 
							&& "S".equals(ieUsuario.getIndLeituraParecerIettus())){
						
						return true;
					}
				}
			}
			
			
			TipoAcompanhamentoTa tipoAcomp = ari.getAcompReferenciaAref().getTipoAcompanhamentoTa();
		   	TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(null);    
		
			List orgaosUsuario = null;
			OrgaoDao orgDao = new OrgaoDao(null);
			
			//permiss�o para verificar se o grupo do usu�rio tem permiss�o de visualiza��o
			if (permissaoAcessoReferencia(ari.getAcompReferenciaAref().getTipoAcompanhamentoTa(), gruposUsuario)){
				
				//se for separado por orgao
				if(tipoAcomp != null && tipoAcomp.getIndSepararOrgaoTa() != null && tipoAcomp.getIndSepararOrgaoTa().equals("S")) {
					// se � apenas para os seus �rg�os
					if(permissaoAcessoReferenciaSeusOrgaos(ari.getAcompReferenciaAref().getTipoAcompanhamentoTa(), gruposUsuario)) {
						orgaosUsuario = orgDao.getListaOrgaosUsuario(usuarioUsu, false);
						 if(orgaosUsuario != null && ari.getAcompReferenciaAref().getOrgaoOrg() != null 
								 && orgaosUsuario.contains(ari.getAcompReferenciaAref().getOrgaoOrg()))
							 return true;
					} else {
						// se for para gerar para todos os �rg�os
						return true;
					}
				} else {
					//se nao for separado por orgao
					return true;
				}	
			}

			// monitorados de sua secretaria/�rg�o:
			// Listar registros de acompanhamento dos itens com indicador de monitorado e em que o c�digo do �rg�o respons�vel 1 
			// seja o mesmo c�digo do �rg�o do usu�rio autenticado (tb_usuario e tb_item_estrutura)
			//if("S".equals(indItemMonitoradosAri) 
			//		&& permissaoLeituraAcompMonitoradoSuaSecretaria(gruposUsuario, ari.getItemEstruturaIett().getEstruturaEtt())
			//		&& (orgaoAri != null && usuarioUsu.getOrgaoOrgs().contains(orgaoAri))) {
			//	
			//	return true;
			//}
			 
			// para sua secretaria/�rg�o:
			// Listar registros de acompanhamento dos itens sem indicador de monitorado e em que o c�digo do �rg�o respons�vel 1 
			// seja o mesmo c�digo do �rg�o do usu�rio autenticado (tb_usuario e tb_item_estrutura)
			// Mantis: 10511: N�o � mais necess�rio, j� testa no primeiro IF
//			if(!"S".equals(indItemMonitoradosAri)
//					&& permissaoAcessoReferenciaSecretaria(gruposUsuario, ari.getItemEstruturaIett().getEstruturaEtt())
//					&& (orgaoAri != null && usuarioUsu.getOrgaoOrgs().contains(orgaoAri))) {
//				
//				return true;
//			}
			
			// monitorados de outras secretarias/org�os:
			// Listar registros de acompanhamento dos itens com indicador de monitorado e em que o c�digo do �rg�o respons�vel 1 
			// seja diferente do c�digo do �rg�o do usu�rio autenticado (tb_usuario e tb_item_estrutura)
			//if("S".equals(indItemMonitoradosAri)
			//		&& permissaoLeituraAcompMonitoradoOutraSecretaria(gruposUsuario, ari.getItemEstruturaIett().getEstruturaEtt())
			//		&& (orgaoAri == null || !usuarioUsu.getOrgaoOrgs().contains(orgaoAri))) {
			//	
			//	return true;
			//}
			
			// para outras secretaria/�rg�o
			// Listar registros de acompanhamento dos itens sem indicador de monitorado e em que o c�digo do �rg�o respons�vel 1 
			// seja diferente do c�digo do �rg�o do usu�rio autenticado (tb_usuario e tb_item_estrutura)
			// Mantis: 10511: N�o � mais necess�rio, j� testa no primeiro IF
//			if(!"S".equals(indItemMonitoradosAri)
//					&& permissaoAcessoReferenciaOutraSecretaria(gruposUsuario, ari.getItemEstruturaIett().getEstruturaEtt())
//					&& (orgaoAri == null || !usuarioUsu.getOrgaoOrgs().contains(orgaoAri))) {
//				
//				return true;
//			}
			
		}
		catch(Exception e) {
			this.logger.error(e);
			throw new ECARException(e);
		}

		return false;
	}

    /**
     * Verifica se os grupos de acesso possuem permiss�o de leitura de
     * acompanhamento para itens monitorados pr�prios.<br> 
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param Set gruposUsuario
     * @param EstruturaEtt estrutura
     * @return boolean
     * @throws ECARException
     */
    //public boolean permissaoLeituraAcompMonitoradoProprio (Set gruposUsuario, EstruturaEtt estrutura) throws ECARException {
    //	if(getIgnorarPermissoes()) {
    //		return true;
    //	}
    //	
    //    return new EstruturaAcessoDao(null).temPermissoesLeituraAcompMonitoradoProprio(gruposUsuario, estrutura);
    //}

    /**
     * Verifica se os grupos de acesso possuem permiss�o de leitura de 
     * acompanhamento para itens monitorados de outras secretarias.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param Set gruposUsuario
     * @param EstruturaEtt estrutura
     * @return boolean
     * @throws ECARException
     */
//    public boolean permissaoLeituraAcompMonitoradoOutraSecretaria (Set gruposUsuario, EstruturaEtt estrutura) throws ECARException {
//    	if(getIgnorarPermissoes()) {
//    		return true;
//    	}
//    	
//        return new EstruturaAcessoDao(null).temPermissoesLeituraAcompMonitoradoOutraSecretaria(gruposUsuario, estrutura);
//    }

    /**
     * Verifica se os grupos de acesso possuem permiss�o de leitura 
     * de acompanhamento para itens monitorados de sua secretaria.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param Set gruposUsuario
     * @param EstruturaEtt estrutura
     * @return boolean
     * @throws throws ECARException
     */
//    public boolean permissaoLeituraAcompMonitoradoSuaSecretaria (Set gruposUsuario, EstruturaEtt estrutura) throws ECARException {
//    	if(getIgnorarPermissoes()) {
//    		return true;
//    	}
//    	
//        return new EstruturaAcessoDao(null).temPermissoesLeituraAcompMonitoradoSuaSecretaria(gruposUsuario, estrutura);
//    }

    /**
     * Verifica se os grupos de acesso possuem permiss�o de leitura 
     * de acompanhamento para itens monitorados de sua responsabilidade.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param Set gruposUsuario
     * @param EstruturaEtt estrutura
     * @return boolean
     * @throws ECARException
     */
//    public boolean permissaoLeituraAcompSuaResponsabilidade (Set gruposUsuario, EstruturaEtt estrutura) throws ECARException {
//    	if(getIgnorarPermissoes()) {
//    		return true;
//    	}
//    	
//        return new EstruturaAcessoDao(null).temPermissoesLeituraAcompSuaResponsabilidade(gruposUsuario, estrutura);
//    }

    /**
     * Retorna boolean ignorarPermissoes.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return boolean
     */
	public boolean getIgnorarPermissoes() {
		return ignorarPermissoes;
	}

	/**
	 * Atribui valor especificado para boolean ignorarPermissoes.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param boolean ignorarPermissoes
	 */
	private void setIgnorarPermissoes(boolean ignorarPermissoes) {
		this.ignorarPermissoes = ignorarPermissoes;
	}
	

	/**
	 * Verificar se o item tem permissao.<br>
	 * @param codIett - Codigo do item
	 * @param codUsu - codigo do usuario
	 * @param gruposUsuario - grupos do usuario
	 * @return <true> se tiver permissao 
	 */
	public boolean permissaoConsultaIETT(Long codIett, SegurancaECAR seguranca) throws ECARException {
		
		try{
			StringBuilder query = new StringBuilder(
					"select distinct itemUsuario from ItemEstrutUsuarioIettus itemUsuario" +                
					" where itemUsuario.itemEstruturaIett.codIett = :codIett" +
					" and ( itemUsuario.usuarioUsu.codUsu = :codUsu"
													);
			
			if( seguranca.getGruposAcesso() != null && seguranca.getGruposAcesso().size() > 0) 
				query.append( " or itemUsuario.sisAtributoSatb.codSatb in (:grupos)" ) ;
			
			query.append(" )" );
			
			Query q = new ItemEstrutUsuarioDao(null).getSession().createQuery(query.toString());   
	    	q.setLong("codIett", codIett);
	        q.setLong("codUsu", seguranca.getCodUsu());
	        
	        ArrayList<Long> codSatb = new ArrayList<Long>();
	        if(seguranca.getGruposAcesso() != null && seguranca.getGruposAcesso().size() > 0) {
	        	
	        	for (Iterator itSatb = seguranca.getGruposAcesso().iterator(); itSatb.hasNext();) {
					codSatb.add( ((SisAtributoSatb)itSatb.next()).getCodSatb() );
				}
	        	q.setParameterList("grupos", codSatb);
	        }
			
			if(q.list().iterator().hasNext())
				return true;

			return false;
			
		}catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}		
	}
	public boolean permissaoConsultaParecerIETT(Long codIett, SegurancaECAR seguranca) throws ECARException {
		
		try{
			StringBuilder query = new StringBuilder(
					"select distinct itemUsuario from ItemEstrutUsuarioIettus itemUsuario" +                
					" where itemUsuario.itemEstruturaIett.codIett = :codIett" +
					" and itemUsuario.indLeituraParecerIettus = :indLeituraParecerIettus" +
					" and ( itemUsuario.usuarioUsu.codUsu = :codUsu"
					);
			
			if( seguranca.getGruposAcesso() != null && seguranca.getGruposAcesso().size() > 0) 
				query.append( " or itemUsuario.sisAtributoSatb.codSatb in (:grupos)" ) ;
			
			query.append(" )" );
			
			Query q = new ItemEstrutUsuarioDao(null).getSession().createQuery(query.toString());   
	    	q.setLong("codIett", codIett);
	        q.setLong("codUsu", seguranca.getCodUsu());
	        q.setString("indLeituraParecerIettus", Pagina.SIM);
	        
	        ArrayList<Long> codSatb = new ArrayList<Long>();
	        if(seguranca.getGruposAcesso() != null && seguranca.getGruposAcesso().size() > 0) {
	        	
	        	for (Iterator itSatb = seguranca.getGruposAcesso().iterator(); itSatb.hasNext();) {
					codSatb.add( ((SisAtributoSatb)itSatb.next()).getCodSatb() );
				}
	        	q.setParameterList("grupos", codSatb);
	        }
			
			return q.list().iterator().hasNext();
			
		}catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}		
	}	
	
	public boolean permissaoConsultaParecerIETTGrupos(Long codIett, Long codTpfa, Long codTa, SegurancaECAR seguranca) throws ECARException {
		boolean permissao = false;
		try{
			StringBuilder query = new StringBuilder(
					"select distinct itemUsuario from ItemEstrutUsuarioIettus itemUsuario" +                
					" where itemUsuario.itemEstruturaIett.codIett = :codIett" +
					" and itemUsuario.indLeituraParecerIettus = :indLeituraParecerIettus" +
					" and ( itemUsuario.usuarioUsu.codUsu = :codUsu"
					);
			
			if( seguranca.getGruposAcesso() != null && seguranca.getGruposAcesso().size() > 0) 
				query.append( " or itemUsuario.sisAtributoSatb.codSatb in (:grupos)" ) ;
			
			query.append(" )" );
			
			Query q = new ItemEstrutUsuarioDao(null).getSession().createQuery(query.toString());   
	    	q.setLong("codIett", codIett);
	        q.setLong("codUsu", seguranca.getCodUsu());
	        q.setString("indLeituraParecerIettus", Pagina.SIM);
	        
	        ArrayList<Long> codSatb = new ArrayList<Long>();
	        if(seguranca.getGruposAcesso() != null && seguranca.getGruposAcesso().size() > 0) {
	        	
	        	for (Iterator itSatb = seguranca.getGruposAcesso().iterator(); itSatb.hasNext();) {
					codSatb.add( ((SisAtributoSatb)itSatb.next()).getCodSatb() );
				}
	        	q.setParameterList("grupos", codSatb);
	        }
			
			permissao = q.list().iterator().hasNext();
			
			
			StringBuilder queryGrupos = new StringBuilder(
					"select distinct taTpfaSatb from TipoAcompTipofuncacompSisatributoTaTpfaSatb taTpfaSatb" +                
					" where taTpfaSatb.tipoFuncAcompTpfa.codTpfa = :codTpfa" +
					" and taTpfaSatb.tipoAcompanhamentoTa.codTa = :codTa" +
					" and taTpfaSatb.indLeituraParecer = :indLeituraParecer"
					);
			
			if( seguranca.getGruposAcesso() != null && seguranca.getGruposAcesso().size() > 0) 
				queryGrupos.append(" and taTpfaSatb.sisAtributoSatb.codSatb in (:grupos)" ) ;
			
			Query qGrupo = new ItemEstrutUsuarioDao(null).getSession().createQuery(queryGrupos.toString());   
	    	qGrupo.setLong("codTpfa", codTpfa);
	    	
	    	if( seguranca.getGruposAcesso() != null && seguranca.getGruposAcesso().size() > 0)
	    		qGrupo.setLong("codTa", codTa);
	    	
	    	qGrupo.setString("indLeituraParecer", Pagina.SIM);
	    	
	        qGrupo.setParameterList("grupos", codSatb);
	        
			
			return permissao || qGrupo.list().iterator().hasNext();			
			
		}catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}		
	}	
	
// M�todo criado por Robson. Otimiza��o do m�todo acima. Falta testar.	
//	public boolean permissaoConsultaParecerIETTGrupos(Long codIett, Long codTpfa, Long codTa, SegurancaECAR seguranca) throws ECARException {
//		try{
//			StringBuilder query = new StringBuilder(
//					"select distinct itemUsuario from ItemEstrutUsuarioIettus itemUsuario" +                
//					" where itemUsuario.itemEstruturaIett.codIett = :codIett" +
//					" and itemUsuario.indLeituraParecerIettus = :indLeituraParecerIettus" +
//					" and ( itemUsuario.usuarioUsu.codUsu = :codUsu");
//			
//			boolean possuiGrupo = (seguranca.getGruposAcesso() != null && seguranca.getGruposAcesso().size() > 0);
//			
//			if(possuiGrupo) query.append( " or itemUsuario.sisAtributoSatb in (:grupos)");
//			
//			query.append(" )" );
//			
//			Query q = new ItemEstrutUsuarioDao(null).getSession().createQuery(query.toString())   
//							.setMaxResults(1)
//					    	.setLong("codIett", codIett)
//					        .setLong("codUsu", seguranca.getCodUsu())
//					        .setString("indLeituraParecerIettus", Pagina.SIM);
//			
//			if( seguranca.getGruposAcesso() != null && seguranca.getGruposAcesso().size() > 0) 
//	        	q.setParameterList("grupos", seguranca.getGruposAcesso());
//			
//			StringBuilder queryGrupos = new StringBuilder(
//					"select distinct taTpfaSatb from TipoAcompTipofuncacompSisatributoTaTpfaSatb taTpfaSatb" +                
//					" where taTpfaSatb.tipoFuncAcompTpfa.codTpfa = :codTpfa" +
//					" and taTpfaSatb.tipoAcompanhamentoTa.codTa = :codTa" +
//					" and taTpfaSatb.indLeituraParecer = :indLeituraParecer");
//			
//			if(possuiGrupo) queryGrupos.append(" and taTpfaSatb.sisAtributoSatb in (:grupos)" ) ;
//			
//			Query qGrupo = new ItemEstrutUsuarioDao(null).getSession().createQuery(queryGrupos.toString())
//								.setMaxResults(1)
//								.setLong("codTpfa", codTpfa)
//								.setLong("codTa", codTa)
//								.setString("indLeituraParecer", Pagina.SIM);
//	    	
//	    	if(possuiGrupo) qGrupo.setParameterList("grupos", seguranca.getGruposAcesso());
//			
//			return !q.list().isEmpty() || !qGrupo.list().isEmpty();			
//			
//		}catch(HibernateException e){
//			this.logger.error(e);
//			throw new ECARException(e);
//		}		
//	}	
	
	
	/**
	 * @author Robson
	 * @param gruposUsuario
	 * @param TipoAcesso
	 * @return boolean
	 */
	public boolean permissaoAcessoSecretariasUsuario(Set gruposUsuario, int TipoAcesso){
		// XXX: novas funcoes de acesso
		if(this.getIgnorarPermissoes())
			return true;
		else
			return new TipoAcompGrpAcessoDao().permissaoAcessoSecretariasUsuario(gruposUsuario, TipoAcesso); 
	}
	
	/**
	 * 
	 * @param tipoAcompanhamentoTa
	 * @param gruposUsuario
	 * @return boolean
	 * @throws ECARException
	 */
	public boolean permissaoAcessoReferencia(TipoAcompanhamentoTa tipoAcompanhamentoTa, Set gruposUsuario) throws ECARException{
    	if(getIgnorarPermissoes()) {
    		return true;
    	}
    	
        return new EstruturaAcessoDao(null).temPermissoesAcessoAcomp(tipoAcompanhamentoTa, gruposUsuario);
    }
	
	/**
	 * 
	 * @param gruposUsuario
	 * @return boolean
	 * @throws ECARException
	 */
	public boolean permissaoAcessoReferencia(Set gruposUsuario) throws ECARException{
    	if(getIgnorarPermissoes()) {
    		return true;
    	}
    	
        return new EstruturaAcessoDao(null).temPermissoesAcessoAcomp(gruposUsuario);
    }
	
	//
	public boolean permissaoAcessoReferenciaTodosOrgaos(TipoAcompanhamentoTa tipoAcompanhamentoTa, Set gruposUsuario)throws ECARException{
    	if(getIgnorarPermissoes()) {
    		return true;
    	}
    	
        return new EstruturaAcessoDao(null).temPermissoesAcessoAcompTodosOrgaos(tipoAcompanhamentoTa, gruposUsuario);
	}
	
	//
	public boolean permissaoAcessoReferenciaSeusOrgaos(TipoAcompanhamentoTa tipoAcompanhamentoTa, Set gruposUsuario)throws ECARException{
    	if(getIgnorarPermissoes()) {
    		return true;
    	}
    	
        return new EstruturaAcessoDao(null).temPermissoesAcessoAcompSeusOrgaos(tipoAcompanhamentoTa, gruposUsuario);
	}
	
	//
	public boolean permissaoAcessoReferenciaOrgaosSuaResponsabilidade(TipoAcompanhamentoTa tipoAcompanhamentoTa, Set gruposUsuario)throws ECARException{
    	if(getIgnorarPermissoes()) {
    		return true;
    	}
    	
    	return new EstruturaAcessoDao(null).temPermissoesAcessoAcompOrgaosSuaResponsabilidade(tipoAcompanhamentoTa, gruposUsuario);
	}
	
	/**
     * Retorna uma lista de TipoFuncAcompTpfa que representa os pareceres que um determinado Grupo pode visualizar em determinado Tipo de acompanhamento
     * @param tipoAcompanhamentoTa
     * @param grupoPermissaoAcessoSatb
     * @return
     */
    public List permissaoVisualizarPareceres(TipoAcompanhamentoTa tipoAcompanhamentoTa, Set gruposAcessoSatb){
		List retorno = new ArrayList();
	    
		try {
			TipoAcompTipofuncacompSisatributoTaTpfaSatbDao taTpfaSatbDao = new TipoAcompTipofuncacompSisatributoTaTpfaSatbDao();
			retorno = taTpfaSatbDao.pesquisarPermissaoVisualizarParecerTipoFuncaoAcompTpfa(tipoAcompanhamentoTa, gruposAcessoSatb);
		} catch (ECARException e) {
			//N�o precisa lan�ar exce��o aqui. Neste caso a lista passada ser� vazia. 
		}
		
		return retorno;
  }	
    
  /**
   * Verifica se os grupos de acesso passados como par�metro podem visualizar o hist�rico
   * @param estruturaEtt
   * @param gruposAcessoSatb
   * @return Boolean
   */
  public boolean permissaoExibirHistorico(EstruturaEtt estruturaEtt, Set gruposAcessoSatb){
	  
	  for (Iterator it = estruturaEtt.getEstruturaAcessoEttas().iterator(); it.hasNext();) {
		  EstruturaAcessoEtta estruturaAcesso = (EstruturaAcessoEtta) it.next();
		  if(gruposAcessoSatb.contains(estruturaAcesso.getSisAtributoSatb())){
			  if(estruturaAcesso.getIndExibirHistoricoEtta() != null && SIM.equals(estruturaAcesso.getIndExibirHistoricoEtta())){
				  return true;				
			  }
		  }
	  }
	  return false;
  }

  
  /**
   * Verifica se permite excluir um ponto cr�tico no cadastro ou no monitoramento. As regras
   * s�o as seguintes. De acordo com o retorno desse m�todo ser� renderizado o bot�o excluir
   * no cadastro e no monitoramento na aba Restri��es ou pontos cr�ticos
   * 
   * Se o item estiver bloqueado e algum campo do formul�rio de restri��es est� bloqueado ou 
   * a fun��o est� bloqueada ent�o o bot�o excluir n�o ser� exibido. 
   *
   * @see Funcao
   * @see 
   * 
   * @param itemEstrutura
   * @param estruturaFuncao
   * @param seguranca
   * @return
   */
  public boolean permiteExcluirPontoCritico(ItemEstruturaIett itemEstrutura, EstruturaFuncaoEttf estruturaFuncao, SegurancaECAR seguranca){
		//verifica se o usu�rio tem permiss�o para excluir, se o item est� bloqueado e o a fun�ao estiver bloqueada OU
		//algum atributo da fun��o est� bloqueado ent�o n�o ser� exibido o bot�o excluir
		ItemEstrutura witem = new ItemEstrutura(itemEstrutura); //wrapper para facilitar a vida
		Funcao pontosCriticos = new Funcao(estruturaFuncao);
		
		boolean permiteExcluir = false;
		
		//verfica se o usu�rio pode excluir
		if(permissaoExcluirItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso()) == true){
			if(witem.isBloqueado() == false){
				permiteExcluir = true;
			}else{//item est� bloqueado
				
				//se a fun��o pontos cr�tcos tamb�m conhecida com restri��es tem um campo bloqueado 
				//ou a funcao pode ser bloqueada e, o item est� bloqueado ent�o, o bot�o excluir 
				//n�o ser� mostrado.
				if(pontosCriticos.hasAtributosBloqueados() == false && pontosCriticos.podeSerBloqueada() == false){
					permiteExcluir = true;
				}
			}
		}			
		return permiteExcluir;
  }
  

}
