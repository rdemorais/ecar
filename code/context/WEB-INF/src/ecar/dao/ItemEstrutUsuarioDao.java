/*
 * Criado em 20/12/2004
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.historico.HistoricoIettus;
import ecar.login.SegurancaECAR;
import ecar.permissao.ControlePermissao;
import ecar.pojo.ItemEstrutUsuarioIettus;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/**
 * @author evandro
 *
 */
public class ItemEstrutUsuarioDao extends Dao{
	
    /**
     *
     */
    public static final short PERMISSAO_CONSULTA_ITEM = 1;
        /**
         *
         */
        public static final short PERMISSAO_ALTERA_ITEM = 2;
        /**
         *
         */
        public static final short PERMISSAO_EXCLUI_ITEM = 3;
        /**
         *
         */
        public static final short PERMISSAO_ADICIONA_ITEM = 4;
        /**
         *
         */
        public static final short PERMISSAO_LIBERAR_PLANEJAMENTO_ITEM = 5;
        /**
         *
         */
        public static final short PERMISSAO_BLOQUEAR_PLANEJAMENTO_ITEM = 6;
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public ItemEstrutUsuarioDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
    
    
	/**
	 * Metodo utilizado para setar ItemEstrutura e também um Usuário (UsuarioUsu) ou
	 * 		Grupo (SisAtributoSatb), são setados somente na inclusão, depois não são
	 * 		mais alterados.
	 * @param request
	 * @param itemEstrutUsuario
	 * @throws ECARException
	 */
	public void setItemUsuarioGrupo(HttpServletRequest request, ItemEstrutUsuarioIettus itemEstrutUsuario) throws ECARException{
		try {
			itemEstrutUsuario.setItemEstruturaIett( (ItemEstruturaIett) buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett"))));
			itemEstrutUsuario.setItemEstruturaIettOrigem( (ItemEstruturaIett) buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett"))));
			
			/* o código é passado iniciando com a letra "U" indicando usuário */
			if(Pagina.getParamStr(request, "codUsu").startsWith(ControlePermissao.PERMISSAO_USUARIO)){
				String cod = Pagina.getParamStr(request, "codUsu").substring(1);
				UsuarioUsu usuario = (UsuarioUsu) buscar(UsuarioUsu.class, Long.valueOf(cod));
				itemEstrutUsuario.setUsuarioUsu(usuario);
				itemEstrutUsuario.setCodTpPermIettus(ControlePermissao.PERMISSAO_USUARIO);
			}
			/* o código é passado iniciando com a letra "G" indicando grupo */
			if(Pagina.getParamStr(request, "codUsu").startsWith(ControlePermissao.PERMISSAO_GRUPO)){
				String cod = Pagina.getParamStr(request, "codUsu").substring(1);
				SisAtributoSatb sisAtributo = (SisAtributoSatb) buscar(SisAtributoSatb.class, Long.valueOf(cod));
				itemEstrutUsuario.setSisAtributoSatb(sisAtributo);
				itemEstrutUsuario.setCodTpPermIettus(ControlePermissao.PERMISSAO_GRUPO);
			}
		}catch(NumberFormatException e){
            this.logger.error(e);
			throw new ECARException(e);
		}
	}
	
	
	/**
	 * Metodo para alterar os indicativos de Leitura, Edição, Exclusão e Manter
	 * 			para próximo nível. Utilizado na inclusão e alteração.
	 * @param request
	 * @param itemEstrutUsuario
	 */
	public void setItemEstrutUsuario(HttpServletRequest request, ItemEstrutUsuarioIettus itemEstrutUsuario){
		itemEstrutUsuario.setIndLeituraIettus(ControlePermissao.SIM.equals(Pagina.getParamStr(request, "indLeituraIettus")) ? ControlePermissao.SIM : ControlePermissao.NAO);
		itemEstrutUsuario.setIndEdicaoIettus(ControlePermissao.SIM.equals(Pagina.getParamStr(request, "indEdicaoIettus")) ? ControlePermissao.SIM : ControlePermissao.NAO);
		itemEstrutUsuario.setIndExcluirIettus(ControlePermissao.SIM.equals(Pagina.getParamStr(request, "indExcluirIettus")) ? ControlePermissao.SIM : ControlePermissao.NAO);
		itemEstrutUsuario.setIndProxNivelIettus(ControlePermissao.SIM.equals(Pagina.getParamStr(request, "indProxNivelIettus")) ? ControlePermissao.SIM : ControlePermissao.NAO);
		itemEstrutUsuario.setIndLeituraParecerIettus(ControlePermissao.SIM.equals(Pagina.getParamStr(request, "indLeituraParecerIettus"))? ControlePermissao.SIM : ControlePermissao.NAO);
	}
	
    
    /**
     * Ordena uma lista de objetos ItemEstrutUsuarioIettus,
     * 		dependendo se tem Usuario ou Grupo cadastrado.
     * @param lista
     * @return
     * @throws ECARException
     */
    public List ordenaLista (List lista) throws ECARException{
    	try{
	    	Collections.sort(lista,
		            new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        			if ( ( (ItemEstrutUsuarioIettus) o1).getUsuarioUsu() != null && ( (ItemEstrutUsuarioIettus) o2).getUsuarioUsu() != null)
		        				return ( (ItemEstrutUsuarioIettus) o1 ).getUsuarioUsu().getNomeUsuSent().compareToIgnoreCase( ( (ItemEstrutUsuarioIettus) o2 ).getUsuarioUsu().getNomeUsuSent() );
		        			else if ( ( (ItemEstrutUsuarioIettus) o1).getSisAtributoSatb() != null && ( (ItemEstrutUsuarioIettus) o2).getSisAtributoSatb() != null)
		        				return ( (ItemEstrutUsuarioIettus) o1 ).getSisAtributoSatb().getDescricaoSatb().compareToIgnoreCase( ( (ItemEstrutUsuarioIettus) o2 ).getSisAtributoSatb().getDescricaoSatb() );
		        			else if (( (ItemEstrutUsuarioIettus) o1).getUsuarioUsu() != null && ( (ItemEstrutUsuarioIettus) o2).getSisAtributoSatb() != null)
		        				return ( (ItemEstrutUsuarioIettus) o1 ).getUsuarioUsu().getNomeUsuSent().compareToIgnoreCase( ( (ItemEstrutUsuarioIettus) o2 ).getSisAtributoSatb().getDescricaoSatb() );
		        			else{
		        				if(((ItemEstrutUsuarioIettus) o1 ).getSisAtributoSatb() != null && ((ItemEstrutUsuarioIettus) o2 ).getUsuarioUsu() != null)
		        					return ( (ItemEstrutUsuarioIettus) o1 ).getSisAtributoSatb().getDescricaoSatb().compareToIgnoreCase( ( (ItemEstrutUsuarioIettus) o2 ).getUsuarioUsu().getNomeUsuSent() );
		        				else
		        					return 0;
		        			}
		        		}
		    		} );
	    	
	    	return lista;
    	}
    	catch (Exception e) {
			logger.error(e);
			throw new ECARException(e);
		}
    }
    
    /**
     * Filtra um set de ItemEstrutUsuarioIettus onde indProxNivelIettus='S'
     * @param setIettus
     * @return Set
     * @throws ECARException
     * @throws HibernateException
     */
    public Set getHerdaPermissao(Set setIettus) throws ECARException{
    	try{
	    	Set heranca = new HashSet();
	    	heranca.addAll(session.createFilter(setIettus, " where this.indProxNivelIettus = '" + ControlePermissao.SIM +"'").list());
	    	return heranca;
    	}catch(HibernateException e){
            this.logger.error(e);
			throw new ECARException(e);
		}
    }
    
    
	
    /**
     * Inclui um registro de ItemEstrutUsuarioIettus e propaga a permissão para os próximos níveis
     * 
     * @param itemEstrutUsuarioIettus
     * @param usuario
     * @throws ECARException
     */
    public void salvar(ItemEstrutUsuarioIettus itemEstrutUsuarioIettus, UsuarioUsu usuario) throws ECARException {
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();
            
            ControlePermissao controlePermissao = new ControlePermissao();
            
            if(!controlePermissao.verificarInclusaoUsuarioGrupo(itemEstrutUsuarioIettus.getItemEstruturaIett(), itemEstrutUsuarioIettus)) {
    			throw new ECARException("itemEstrutura.usuario.validacao.inclusaoUsuarioGrupo"); 
            }

            session.save(itemEstrutUsuarioIettus);
			objetos.add(itemEstrutUsuarioIettus);
			
	        //
	        // controlar as permissoes
	        //
			controlePermissao.propagarPermissoesItensFilhos(itemEstrutUsuarioIettus, session);

			tx.commit();

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC");
				Iterator itObj = objetos.iterator();

				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
		} catch (ECARException e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
            this.logger.error(e);
			throw e;
		} catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
            this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
    }
	
    /**
     * Altera um registro de ItemEstrutUsuarioIettus e propaga a permissão para os próximos níveis
     * 
     * @param novo
     * @param old
     * @param indProxNivelIettusAnterior
     * @throws ECARException
     */
    public void alterar(ItemEstrutUsuarioIettus novo, ItemEstrutUsuarioIettus old, String indProxNivelIettusAnterior) throws ECARException {
        
    	Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();
			
            ControlePermissao controlePermissao = new ControlePermissao();

            if(!controlePermissao.verificarInclusaoUsuarioGrupo(novo.getItemEstruturaIett(), novo)) {
    			throw new ECARException("itemEstrutura.usuario.validacao.inclusaoUsuarioGrupo"); 
            }

            //
	        // controlar as permissoes
	        //
            
            /******** Historico *********/
            
            HistoricoIettus historico = new HistoricoIettus(old, 
															HistoricoIettus.alterarPermissoes, 
            												session,
            												new ConfiguracaoDao(request),
            												request);
            historico.gerarHistorico();
            
        	/******** Historico *********/
            
            controlePermissao.atualizarPermissoesItensFilhos(novo, indProxNivelIettusAnterior, session, request, historico);
        	
			session.update(novo);
			objetos.add(novo);

            //Fazer a mesma verificação da inclusão para alteracao... 
			
			tx.commit();

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("ALT");
				Iterator itObj = objetos.iterator();

				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
		} catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
            this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
    }
	
	/**
     * Recebe um Array com Códigos de ItemEstrutUsuarioIettus para exclusão. 
     * @param codigosParaExcluir
         * @param usuarioLogado
         * @throws ECARException
     */
    public void excluir(String[] codigosParaExcluir, UsuarioUsu usuarioLogado) throws ECARException {
    	
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();
			
            for (int i = 0; i < codigosParaExcluir.length; i++) {  
            	
            	ItemEstrutUsuarioIettus old = (ItemEstrutUsuarioIettus) this.buscar(ItemEstrutUsuarioIettus.class, Long.valueOf(codigosParaExcluir[i]));
           	
            	// Mantis #2156         
            	old.setUsuManutencao(usuarioLogado);
            	
            	//
		        // controlar as permissoes
		        //
	            new ControlePermissao().removerPermissoesItensFilhos(old, session, request);
	            
	            
	            /******** Historico *********/	            
	            HistoricoIettus historico = new HistoricoIettus(old,
	            												HistoricoIettus.excluirPermissoes,
	            												session,
	            												new ConfiguracaoDao(request),
	            												request);
	            historico.gerarHistorico();
	        	/******** Historico *********/

	            session.delete(old);	            
				objetos.add(old);
            }

			tx.commit();

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("EXC");
				Iterator itObj = objetos.iterator();

				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
		} catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
            this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
    }
    
    /**
     * Obter os acompanhamentos (AcompReferenciaAref) que sejam de um tipo de acompanhamento
     * @param codIett
     * @return List de ItemEstrutUsuarioIettus
     * @throws HibernateException
     * @throws ECARException
     */
    public List getItemEstrutUsuarioItemOrigemAndEmitePosicaoAndInfAndamento(Long codIett) throws ECARException {
    	try{
	        StringBuilder str = new StringBuilder(); 
	        str.append("select iettus from ItemEstrutUsuarioIettus iettus");
	        str.append(" where (iettus.indEmitePosIettus = :indEmitePosIettus");
	        str.append(" or iettus.indInfAndamentoIettus = :indInfAndamentoIettus)");
	        str.append(" and iettus.itemEstruturaIett.codIett = :codIett");
	        str.append(" and iettus.itemEstruturaIett.indAtivoIett = :indAtivo");
	        
	        Query query = this.getSession().createQuery(str.toString());
	        query.setLong("codIett", codIett.longValue());
	        query.setString("indEmitePosIettus", "S");
	        query.setString("indInfAndamentoIettus", "S");
	        query.setString("indAtivo", "S");
	    	
	    	return query.list();

    	} catch(HibernateException e){
    		this.logger.error(e);
            throw new ECARException(e);
        }
    }
    
    /**
     * @author Robson
     * @param iett
     * @param seguranca
     * @param acao
     * @return boolean
     * @throws ECARException
     * @since 18/12/2007
     * TODO: nao terminado, falta incluir as acoes
     * verifica o acesso a um usuario a realizar uma acao em um itemEstrutura
     */
    public boolean PermissaoAcessoUsuarioItemEstrutura(ItemEstruturaIett iett, SegurancaECAR seguranca, short acao) throws ECARException{
    	try {
			StringBuilder str = new StringBuilder("from ItemEstrutUsuarioIettus iettus " +
												"where iettus.itemEstruturaIett = :iett " +
												"and iettus.indEmitePosIettus = :emitepos " +
												"and iettus.itemEstruturaIett.indAtivoIett = :ativo " +
												"and (iettus.usuarioUsu.codUsu = :codusu ");
			
			Set gruposUsuario = seguranca.getGruposAcesso();
			if (gruposUsuario != null && gruposUsuario.size() > 0)
				str.append( " or iettus.sisAtributoSatb in (:grupos)" ) ;
			str.append(" ) ");
			
			if(acao == PERMISSAO_CONSULTA_ITEM)
				str.append("and iettus.indLeituraIettus = :leitura ");
			
			else if(acao == PERMISSAO_ALTERA_ITEM){
				str.append("and iettus.indEdicaoIettus = :edita ");
				str.append("and iettus.itemEstruturaIett.indBloqPlanejamentoIett = :bloqueia ");
			}
			else if(acao == PERMISSAO_EXCLUI_ITEM)
				str.append("and iettus.indExcluirIettus = :exclui ");
			
			else if(acao == PERMISSAO_LIBERAR_PLANEJAMENTO_ITEM){
				str.append("and (iettus.indDesblPlanIettus = :desbloqueia " +
						"and iettus.itemEstruturaIett.indBloqPlanejamentoIett = :bloqueiaitem) ");
			}
			else if(acao == PERMISSAO_BLOQUEAR_PLANEJAMENTO_ITEM){
				str.append("and iettus.indBloqPlanIettus = :bloqueia " +
						"and(iettus.itemEstruturaIett.indBloqPlanejamentoIett is null " +
						"or iettus.itemEstruturaIett.indBloqPlanejamentoIett = :bloqueiaitem) ");
			}
			Query q = this.getSession().createQuery(str.toString());
			
			q.setMaxResults(1);
			q.setParameter("iett", iett);
			q.setString("emitepos", Dominios.SIM);
			q.setString("ativo",    Dominios.ATIVO);
			q.setLong("codusu",     seguranca.getCodUsu());
			if (gruposUsuario != null && gruposUsuario.size() > 0)
				q.setParameterList("grupos", gruposUsuario);
			
			if(acao == PERMISSAO_CONSULTA_ITEM)
				q.setString("leitura",  Dominios.SIM);
			
			else if(acao == PERMISSAO_ALTERA_ITEM){
				q.setString("edita",    Dominios.SIM);
				q.setString("bloqueia", Dominios.NAO);
			}
			else if(acao == PERMISSAO_EXCLUI_ITEM)
				q.setString("exclui",   Dominios.SIM);
			
			else if(acao == PERMISSAO_LIBERAR_PLANEJAMENTO_ITEM){
				q.setString("desbloqueia", Dominios.SIM);
				q.setString("bloqueiaitem", Dominios.NAO);
			}
			else if(acao == PERMISSAO_BLOQUEAR_PLANEJAMENTO_ITEM){
				q.setString("bloqueia", Dominios.SIM);
				q.setString("bloqueiaitem", Dominios.NAO);
			}
			
			return !q.list().isEmpty();
			
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
    }
}
