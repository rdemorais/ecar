package ecar.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.Aba;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.FuncaoFun;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.util.Dominios;

/**
 * @author evandro
 * @since 12/05/2006
 */
public class AbaDao extends Dao {
	
    /**
     *
     * @param request
     */
    public AbaDao(HttpServletRequest request) {
		super();
		this.request = request; 
	}
	
    /**
     *
     * @param request
     * @param objetos
     * @throws ECARException
     */
    @SuppressWarnings("unchecked")
	public void alterar (HttpServletRequest request, ArrayList objetos) throws ECARException {
		Transaction tx = null;
		try {
			//Altera��o para colocar as altera��es da tela de abas em uma �nica transa��o
			//ArrayList<Aba> objetos = new ArrayList<Aba>();
			//super.inicializarLogBean();
			//tx = session.beginTransaction();
			
			/* Passar por todas as abas e atualizar */
			ArrayList<Aba> lista = (ArrayList<Aba>)this.listar(Aba.class, new String[]{"codAba", "asc"});

			String[] ehPadraoRegistroArray = Pagina.getParamLista(request, "ehPadraoRegistro");
			String[] ehPadraoVisualizacaoArray = Pagina.getParamLista(request, "ehPadraoVisualizacao");
			int posicao = 0;
						
			List<String> ehPadraoRegistroLista = new ArrayList<String>();
			List<String> ehPadraoVisualizacaoLista = new ArrayList<String>();
			ehPadraoRegistroLista = Arrays.asList(ehPadraoRegistroArray);
			ehPadraoVisualizacaoLista = Arrays.asList(ehPadraoVisualizacaoArray);
					
			for(Aba aba : lista){
							
				if (Dominios.SIM.equals(Pagina.getParamStr(request, "exibePosicaoAba" + aba.getCodAba().toString()))) {
					aba.setExibePosicaoAba(Dominios.SIM);
				} else {
					aba.setExibePosicaoAba(Dominios.NAO);
				}
				
				aba.setOrdemAba(Integer.valueOf(Pagina.getParamInt(request, "ordemAba" + aba.getCodAba().toString())));
				aba.setLabelAba(Pagina.getParamStr(request, "labelAba" + aba.getCodAba().toString()));				
				
				if("".equals(Pagina.getParamStr(request, "funAba" + aba.getCodAba().toString())))
						aba.setFuncaoFun(null);
				else {
					FuncaoDao funcaoDao = new FuncaoDao(request);
					FuncaoFun funcao = (FuncaoFun)funcaoDao.buscar(FuncaoFun.class,Long.valueOf(Pagina.getParamStr(request, "funAba" + aba.getCodAba().toString()))); 
					aba.setFuncaoFun(funcao);
				}
				
				if (ehPadraoRegistroLista.contains(aba.getCodAba().toString())) {
					aba.setEhPadraoRegistro(Dominios.SIM);
				} else {
					aba.setEhPadraoRegistro(Dominios.NAO);
				}
				
				if (ehPadraoVisualizacaoLista.contains(aba.getCodAba().toString())) {
					aba.setEhPadraoVisualizacao(Dominios.SIM);
				} else {
					aba.setEhPadraoVisualizacao(Dominios.NAO);
				}
				
				session.update(aba);
				objetos.add(aba);
			}
			
		} catch (HibernateException e) {
			if (tx != null)
				try {
				//	tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		} catch (ECARException e) {
			if (tx != null)
				try {
					//tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			this.logger.error(e);
			throw e;
		}
	}
	
        /**
         *
         * @param request
         * @throws ECARException
         */
        @SuppressWarnings("unchecked")
	public void alterarAbaLinkHistorico (HttpServletRequest request) throws ECARException {
		Transaction tx = null;
		HistoricoDao historicoDao = new HistoricoDao(request);
		LinkDao linkDao = new LinkDao(request);
		try {
			ArrayList objetos = new ArrayList();
			super.inicializarLogBean();
			tx = session.beginTransaction();
			
			this.alterar(request, objetos);
			historicoDao.alterar(request, objetos);
			linkDao.alterar(request, objetos);
			
			tx.commit();
			
			/* log */
			if (super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("ALT");

				for (Iterator itObj = objetos.iterator(); itObj.hasNext();) {
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
		}
	}
        
    /**
     * M�todo que altera a configuraçãoo das abas e �cones.
     * @param request
     * @throws ECARException
     */
   @SuppressWarnings("unchecked")
	public void alterarAbaLinkHistorico(List aba, List historicoConfig, List linksConfig) throws ECARException {
		Transaction tx = null;
//		HistoricoDao historicoDao = new HistoricoDao(request);
//		LinkDao linkDao = new LinkDao(request);
		try {
			super.inicializarLogBean();
			tx = session.beginTransaction();
			
			//this.alterar(aba);
			Iterator abaIt = aba.iterator();
			while(abaIt.hasNext()) {
			    session.update(abaIt.next());
			}
			
			//historicoDao.alterar(historicoConfig);
			Iterator histIt = historicoConfig.iterator();
			while(histIt.hasNext()) {
			    session.update(histIt.next());
			}
			
			//linkDao.alterar(linksConfig);
			Iterator linkIt = linksConfig.iterator();
			while(linkIt.hasNext()) {
			    session.update(linkIt.next());
			}
			
			tx.commit();
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
	 * Verifica se a op��o Nivel Planejamento est� selecionada para exibi��o
	 * em Gera��o de Per�odo de Refer�ncia ou Relat�rio
	 * 
	 * @param opcao - String ("E" para Elabora��o Gera��o ou "P" para Relat�rio Posi��o)
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public boolean verificaNivelPlanejamento(String opcao){
		boolean retorno = false;
		
		try {
	    	Aba abaAux = new Aba();
	    	abaAux.setNomeAba("NIVEL_PLANEJAMENTO");
	    	
	        ArrayList<Aba> lista = (ArrayList<Aba>)this.pesquisar(abaAux, new String[]{"ordemAba", "asc"});
			Iterator<Aba> it = lista.iterator();
			
			if (it.hasNext()) {
				Aba aba = (Aba) it.next();				
				if ("P".equals(opcao)) {
					if ("S".equals(aba.getExibePosicaoAba())) {
						retorno = true;
					}
				}
			}
		} catch (Exception e) {
			this.logger.error(e);
			retorno = false;
		}
		
		return retorno;
	}
	
        /**
         *
         * @return
         */
        public List getListaAbas(){
		return this.getSession().createCriteria(Aba.class)
				.add(Restrictions.eq("exibePosicaoAba",Dominios.SIM))
				.addOrder(Order.asc("ordemAba"))
				.list();
		
	}
	
	

	

        /**
         *
         * @param ta
         * @param gruposUsuario
         * @return
         */
        public List getListaAbasComAcesso(TipoAcompanhamentoTa ta, Set gruposUsuario){
		return this.getSession().createQuery(
			"select distinct aba " +
			"from Aba aba " +
			"inner join aba.tipoacompAbasSisatributoTaabasatbs acesso " +
			"where acesso.tipoAcompanhamentoTa = :ta " +
			"and aba.abaSuperior = :abaSuperior " +
			"and aba.exibePosicaoAba = :exibe " +
			"and acesso.indVisualizaAba = :le " +
			"and acesso.sisAtributoSatb in (:grupo) " +
			"order by aba.ordemAba ")
		.setParameter("ta", ta)
		.setParameter("abaSuperior", Dominios.NAO)
		.setString("exibe", Dominios.SIM)
		.setString("le", Dominios.COM_ACESSO_LEITURA)
		.setParameterList("grupo", gruposUsuario)
		.list();
	}
        /***
         * Retorna a Lista de Abas com acesso cujas funcoes de acompanhamento estao configuradas na estrutura.
         * @param ett
         * @param ta
         * @param gruposUsuario
         * @return
         */
        public List getListaAbasComAcessoConfiguradasNaEstrutura(EstruturaEtt ett, TipoAcompanhamentoTa ta, Set gruposUsuario){
        	
        	AbaDao abaDao = new AbaDao(null);
        	List<Aba> listAbas = null;
        	listAbas = abaDao.getListaAbasComAcesso(ta, gruposUsuario);
        	
        	List<Aba> listAbasConfiguradas = new ArrayList<Aba>();
        	
        	for  (Iterator it = listAbas.iterator();it.hasNext();) {
				Aba aba = (Aba) it.next();
				if(aba.getFuncaoFun()!= null){        	
    	        	EstruturaDao estruturaDao = new EstruturaDao(request);
    	        	Set listaFuncoes = ett.getEstruturaFuncaoEttfs();
    	        	Iterator itListaFuncoes = listaFuncoes.iterator();
    	        	while(itListaFuncoes.hasNext()){
    	        		EstruturaFuncaoEttf funcao = (EstruturaFuncaoEttf) itListaFuncoes.next();
    	        		if(aba.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
    	        			listAbasConfiguradas.add(aba);
    	        			break;
    	        		}
    	        	}
    	        } else{
    	        	listAbasConfiguradas.add(aba);
    	        }				
				
				
        	}
        	return listAbasConfiguradas;
        }
        
        /**
        *
        * @param ta
        * @param gruposUsuario
        * @return
        */
       public List getListaAbasComAcesso(TipoAcompanhamentoTa ta, Set gruposUsuario, int tipoPadraoExibicaoAba){
    	   
    	   String tipoPadrapExibicaoAbaString = "";
    	   
    	   if(tipoPadraoExibicaoAba==1)
    		   tipoPadrapExibicaoAbaString = "and aba.ehPadraoRegistro = :epr ";
    	   else if(tipoPadraoExibicaoAba==2)
    		   tipoPadrapExibicaoAbaString = "and aba.ehPadraoVisualizacao = :epr ";
    	   
    	   // busca aba
    	   List lista = this.getSession().createQuery(
    				"select distinct aba " +
    				"from Aba aba " +
    				"join fetch aba.tipoacompAbasSisatributoTaabasatbs acesso " +
    				"where acesso.tipoAcompanhamentoTa = :ta " +
    				"and aba.abaSuperior = :abaSuperior " +
    				"and aba.exibePosicaoAba = :exibe " +
    				"and acesso.indVisualizaAba = :le " +
    				"and acesso.sisAtributoSatb in (:grupo) " +
    				tipoPadrapExibicaoAbaString +
    				"order by aba.ordemAba ")
    			.setParameter("ta", ta)
    			.setParameter("abaSuperior", Dominios.NAO)
    			.setString("exibe", Dominios.SIM)
    			.setString("le", Dominios.COM_ACESSO_LEITURA)
    			.setParameterList("grupo", gruposUsuario)
    			.setString("epr", Dominios.SIM)
    			.list();
    	   
    	   if (lista==null || lista.size()==0) {
			    	   
					lista = this.getSession().createQuery(
						"select distinct aba " +
						"from Aba aba " +
						"join fetch aba.tipoacompAbasSisatributoTaabasatbs acesso " +
						"where acesso.tipoAcompanhamentoTa = :ta " +
						"and aba.abaSuperior = :abaSuperior " +
						"and aba.exibePosicaoAba = :exibe " +
						"and acesso.indVisualizaAba = :le " +
						"and acesso.sisAtributoSatb in (:grupo) " +
						"order by aba.ordemAba ")
					.setParameter("ta", ta)
					.setParameter("abaSuperior", Dominios.NAO)
					.setString("exibe", Dominios.SIM)
					.setString("le", Dominios.COM_ACESSO_LEITURA)
					.setParameterList("grupo", gruposUsuario)
					.list();
    	   }
    	   
		return lista;
	}
       
        
        
//	
//        /**
//        *
//        * @param ta
//        * @param gruposUsuario
//        * @return
//        */
//       public List getListaAbasComAcesso(TipoAcompanhamentoTa ta, Set gruposUsuario, boolean ehPadraoRegistro, boolean ehPadraoVisualizacao){
//		return this.getSession().createQuery(
//			"select distinct aba " +
//			"from Aba aba " +
//			"inner join aba.tipoacompAbasSisatributoTaabasatbs acesso " +
//			"where acesso.tipoAcompanhamentoTa = :ta " +
//			"and aba.abaSuperior = :abaSuperior " +
//			"and aba.exibePosicaoAba = :exibe " +
//			"and acesso.indVisualizaAba = :le " +
//			"and acesso.sisAtributoSatb in (:grupo) " +
//			"and aba.ehPadraoRegistro = :epr " +
//			"and aba.ehPadraoVisualizacao = :epv " +
//			"order by aba.ordemAba ")
//		.setParameter("ta", ta)
//		.setParameter("abaSuperior", Dominios.NAO)
//		.setString("exibe", Dominios.SIM)
//		.setString("le", Dominios.COM_ACESSO_LEITURA)
//		.setString("epr", ehPadraoRegistro?"S":"N")
//		.setString("epv", ehPadraoVisualizacao?"S":"N")
//		.setParameterList("grupo", gruposUsuario)
//		.list();
//	}
    

	
        /**
         *
         * @param ta
         * @param gruposUsuario
         * @return
         */
        public List getListaAbasSuperiorComAcesso(TipoAcompanhamentoTa ta, Set gruposUsuario) {
		
		return this.getSession().createQuery(
				"select distinct aba " +
				"from Aba aba " +
				"where aba.abaSuperior = :abaSuperior " +
				"and aba.exibePosicaoAba = :exibe " +
				"order by aba.ordemAba ")
			.setParameter("abaSuperior", Dominios.SIM)
			.setString("exibe", Dominios.SIM)
			.list();
	}

        /**
         *
         * @param nomeAba
         * @return
         */
        public Aba buscarAba(String nomeAba) {
		List <Aba> listAbas =  this.getSession().createQuery(
				"select distinct aba " +
				"from Aba aba " +
				"where nomeAba = :nomeAba " )
				.setParameter("nomeAba", nomeAba).list();
		
		if (listAbas!=null && (!listAbas.isEmpty()) ){
			return (Aba)listAbas.iterator().next();
		} else {
			return null;
		}
	}
	
        /**
         *
         * @return
         */
        public Collection<Aba>  listarSuperior(){
		
		return this.getSession().createCriteria(Aba.class)
		.add(Restrictions.eq("abaSuperior",Dominios.SIM))
		// ordem alfab�tica pelo label
		.addOrder(Order.asc("nomeAba"))
		// Este c�digo determina que a ordena��o seja feita pela ordem definida na tela pelo usuario
		//.addOrder(Order.asc("ordemAba"))
		.list();
	}
	
        /**
         *
         * @return
         */
        public Collection<Aba>  listarAbasMonitoramento(){
		
		return this.getSession().createCriteria(Aba.class)
		.add(Restrictions.eq("abaSuperior",Dominios.NAO))
		// ordem alfab�tica pelo label
		.addOrder(Order.asc("nomeAba"))
		// Este c�digo determina que a ordena��o seja feita pela ordem definida na tela pelo usuario
		//.addOrder(Order.asc("ordemAba"))
		.list();
	}
	
        /**
         *
         * @param aba
         * @param estruturaEtt
         * @return
         */
        public String getLabelAbaEstrutura(Aba aba, EstruturaEtt estruturaEtt){
		
		String labelAba = "";
		//Caso exista alguma fun��o associada a aba (na tela de "Exibi��o de Abas"), o sistema utilizar� o label definido para a fun��o na estrutura em quest�o.
        if(aba.getFuncaoFun()!= null){        	
        	EstruturaDao estruturaDao = new EstruturaDao(request);
        	Set listaFuncoes = estruturaEtt.getEstruturaFuncaoEttfs();
        	Iterator itListaFuncoes = listaFuncoes.iterator();
        	while(itListaFuncoes.hasNext()){
        		EstruturaFuncaoEttf funcao = (EstruturaFuncaoEttf) itListaFuncoes.next();
        		if(aba.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
        			labelAba =  funcao.getLabelEttf();
        			break;
        		}
        	}
        }

        //Quando n�o existir fun��o para a aba cadastrada na tela de "Exibi��o de Abas", o sistema utilizar� o label cadastrado na tela de exibi��o de abas.
        if (labelAba.equals(Dominios.STRING_VAZIA)){
        	labelAba = aba.getLabelAba();
        }
        return labelAba;
		
	}
	
	/**
	 * O sistema verifica a ordem configurada para a exibi��o das abas 
	 * e direciona o usu�rio para a aba de menor ordem E que o usu�rio tenha permiss�o de acesso. 
	 * Caso nenhuma aba esteja configurada ou nao tenha permissao, o sistema direciona para a tela Resumo, que hoje � uma tela fixa. 
	 * 
         * @param ta
         * @param gruposUsuario
         * @param tipoPadraoExibicaoAba tipo registro ou tipo visualiza��o
         * @return String pagina
	 */
	public String pesquisaEnderecoAbaRegistro(TipoAcompanhamentoTa ta, Set gruposUsuario, int tipoPadraoExibicaoAba, String nomeAbaClicada) {
		
		String pagina = "";
	    
        List listAbasComAcesso = null;
        int i=0;
        String nomeAba = null;
        //vai buscar as abas com acesso
        // listAbasComAcesso = this.getListaAbasComAcesso(ta, gruposUsuario);
        
        nomeAba = nomeAbaClicada;
        
        //Caso nenhuma aba esteja configurada ou nao tenha permissao, o sistema direciona para a tela Resumo, que hoje � uma tela fixa.
    	pagina = "acompanhamento/resumo/detalharItem.jsp";
        
               
        if(nomeAbaClicada==null && listAbasComAcesso == null) {
            
        	// valor tipo 1 => registro 2=> visualiza��o
            listAbasComAcesso = this.getListaAbasComAcesso(ta, gruposUsuario, tipoPadraoExibicaoAba);
            
            if (listAbasComAcesso!=null && !listAbasComAcesso.isEmpty()) {
            	Aba abaDirecionaRegistro = (Aba) listAbasComAcesso.get(0);
            	nomeAba = abaDirecionaRegistro.getNomeAba();	
            }
        }
        
			// se existir aba com acesso
			if(nomeAba != null && !nomeAba.equals("")) {	
        		
				/***************ABA DADOS GERAIS*************************/
				if ("DADOS_GERAIS".equals(nomeAba)) {
					pagina = "acompanhamento/dadosGerais/frm_con.jsp";
					
					if (tipoPadraoExibicaoAba==2)
						pagina = "acompanhamento/relAcompanhamento/dadosGerais/dadosGerais.jsp";
				
				/***************ABA EVENTOS*****************************/
				} else if ("EVENTOS".equals(nomeAba)) {
					pagina = "acompanhamento/realizacoes/eventos.jsp";	
					
					if (tipoPadraoExibicaoAba==2)
						pagina = "acompanhamento/relAcompanhamento/eventos/eventos.jsp";
				
				/***************ABA PONTOS CRITICOS*********************/	
				} else if ("PONTOS_CRITICOS".equals(nomeAba)) {
					pagina = "acompanhamento/restricoes/pontosCriticos.jsp";
					
					if (tipoPadraoExibicaoAba==2)
						pagina = "acompanhamento/restricoes/pontosCriticos.jsp?tela=V";
				
				/***************ABA SITUACAO PONTOS CRITICOS***********/	
				} else if(nomeAba.equals("SITUACAO_PONTOS_CRITICOS")) {
					pagina =  "acompanhamento/situacaoDatas/situacaoDatas.jsp";
					
					if (tipoPadraoExibicaoAba==2)
						pagina = "acompanhamento/relAcompanhamento/situacaoDatas/situacaoDatas.jsp";
				
				/***************ABA GALERIA***************************/	
				} else if ("GALERIA".equals(nomeAba)) {
					pagina =  "acompanhamento/galeria/galeria.jsp";
					
					if (tipoPadraoExibicaoAba==2)
						pagina = "acompanhamento/relAcompanhamento/galeria/galeria.jsp";
					
					
				/***************ABA FINANCEIRO************************/
				} else if ("FINANCEIRO".equals(nomeAba)) {
					pagina =  "acompanhamento/financeiro/financeiro.jsp";
					
					if (tipoPadraoExibicaoAba==2)
						pagina = "acompanhamento/relAcompanhamento/financeiro/financeiro.jsp";
					
				
				/***************ABA RESUMO***************************/
				} else if("RELACAO".equals(nomeAba)){
					pagina =  "acompanhamento/resumo/detalharItem.jsp";
	            	
					if (tipoPadraoExibicaoAba==2)
						pagina = "acompanhamento/relAcompanhamento/resumo/detalharItem.jsp";
					
	            /***************ABA METAS E INDICADORES***************/	
	            } else if ("REL_FISICO_IND_RESULTADO".equals(nomeAba)) {
	            	pagina = "acompanhamento/resultado/indicadoresResultado.jsp";	
	            
	            	if (tipoPadraoExibicaoAba==2)
						pagina = "acompanhamento/resultado/indicadoresResultado.jsp?tela=V";
	            	
				/***************ABA SITUACAO E INDICADORES*************/	
	            } else if (nomeAba.equals("SITUACAO_INDICADORES")) {
	            	pagina = "acompanhamento/situacaoIndicadores/situacaoIndicadores.jsp";
	            	
	            	if (tipoPadraoExibicaoAba==2)
						pagina = "acompanhamento/relAcompanhamento/situacaoIndicadores/situacaoIndicadores.jsp";
				
	            /***************ABA ETAPA*****************************/		
	            }	else if ("ETAPA".equals(nomeAba)) {
	            	pagina = "acompanhamento/etapa/etapas.jsp";
	            	
	            	if (tipoPadraoExibicaoAba==2)
						pagina = "acompanhamento/relAcompanhamento/etapas/etapas.jsp";
					
				/***************ABA DATAS LIMITES*********************/	
				} else if ("DATAS_LIMITES".equals(nomeAba)) {
					pagina = "acompanhamento/datasLimites/datasLimites.jsp";
					
					if (tipoPadraoExibicaoAba==2)
						pagina = "acompanhamento/relAcompanhamento/datasLimites/datasLimites.jsp";
				
				/***************ABA SITUACAO***************************/
				} else if("SITUACAO".equals(nomeAba)){
					pagina = "acompanhamento/situacao/relatorios.jsp";
					
					if (tipoPadraoExibicaoAba==2)
						pagina = "acompanhamento/situacao/relatorios.jsp?tela=V";
				
				/***************ABA GRAFICO DE GANTT*********************/
				} else if("GRAFICO_DE_GANTT".equals(nomeAba)){
					pagina = "acompanhamento/graficoGantt/graficoGantt.jsp";
					
					if (tipoPadraoExibicaoAba==2)
						pagina = "acompanhamento/relAcompanhamento/graficoGantt/graficoGantt.jsp";
				
				/***************ABA RELATORIO ***************************/
				}	else if("RELATORIO".equals(nomeAba)) {
					pagina= "acompanhamento/relatorios/relatorioImpresso.jsp?tela=R";
					
					if (tipoPadraoExibicaoAba==2)
						pagina = "acompanhamento/relatorios/relatorioImpresso.jsp?tela=V";
					
        		}else {
                	
                	//Caso nenhuma aba esteja configurada ou nao tenha permissao, o sistema direciona para a tela Resumo, que hoje � uma tela fixa.
                	pagina = "acompanhamento/resumo/detalharItem.jsp";
                	
                	if (tipoPadraoExibicaoAba==2)
						pagina = "acompanhamento/relAcompanhamento/resumo/detalharItem.jsp";
                }
			}	
        
			// adiciona tipo padrao de exibicao
			if (pagina!=null) {
				pagina += (pagina.indexOf("&")>0 || pagina.indexOf("?")>0?"&tipoPadraoExibicaoAba=" + tipoPadraoExibicaoAba:"?tipoPadraoExibicaoAba=" + tipoPadraoExibicaoAba); 
			}
			
        return pagina;
        
	} 
	
	/**
	 * Pesquisa se o nome da aba passada como parametro tem acesso para o grupo de usuario
	 * 
         * @param ta
         * @param nomeAba
         * @param gruposUsuario
         * @return boolean
	 */
	public Aba buscarAbaItemComAcesso(TipoAcompanhamentoTa ta, Set gruposUsuario, String nomeAba) {
		
		List <Aba> listAbas = this.getSession().createQuery(
								"select distinct aba " +
								"from Aba aba " +
								"inner join aba.tipoacompAbasSisatributoTaabasatbs acesso " +
								"where acesso.tipoAcompanhamentoTa = :ta " +
								"and aba.abaSuperior = :abaSuperior " +
								"and aba.nomeAba = :nomeAba " +
								"and aba.exibePosicaoAba = :exibe " +
								"and acesso.indVisualizaAba = :le " +
								"and acesso.sisAtributoSatb in (:grupo) ")
							.setParameter("ta", ta)
							.setParameter("abaSuperior", Dominios.NAO)
							.setParameter("nomeAba", nomeAba)
							.setString("exibe", Dominios.SIM)
							.setString("le", Dominios.COM_ACESSO_LEITURA)
							.setParameterList("grupo", gruposUsuario)
							.list();
						
		
		
		if (listAbas!=null && (!listAbas.isEmpty()) ){
			Aba aba = listAbas.get(0);
			return aba;
		} else {
			return null;
		}
	}
	
	
	/**
	 * Pesquisa a aba que seja padrão de registro ou visualizaçao
     * @param tipo
     * @return aba
	 */
	public Aba buscarAbaPadrao(int tipo) {
		
		String query = "select distinct aba from Aba aba ";
		if (tipo==1)
			query += "where ehPadraoRegistro = 'S' ";
		else if (tipo==2)
			query += "where ehPadraoVisualizacao = 'S' ";
		
		List <Aba> listAbas = this.getSession().createQuery(query)
							.list();

		if (listAbas!=null && (!listAbas.isEmpty()) ){
			Aba aba = listAbas.get(0);
			return aba;
		} else {
			return null;
		}
	}
	
	
	
}
