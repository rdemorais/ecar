/*
 * Criado em 28/10/2004
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import comum.database.CacheManagerImpl;
import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.bean.EstruturaWebServiceBean;
import ecar.exception.ECARException;
import ecar.pojo.AtributosAtb;
import ecar.pojo.EstAtribTipoAcompEata;
import ecar.pojo.EstrutTpFuncAcmpEtttfa;
import ecar.pojo.EstruturaAtributoEttat;
import ecar.pojo.EstruturaAtributoEttatPK;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.FuncaoFun;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturaSisAtributoIettSatb;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.PaiFilho;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.SituacaoSit;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.intercambioDados.funcionalidade.PerfilIntercambioDadosCadastroPidc;
import ecar.util.Dominios;

/**
 * @author felipev
 * @author garten
 *  
 */
public class EstruturaDao extends Dao {       


	/**
     * Construtor. Chama o Session factory do Hibernate
         * @param request
         */
    public EstruturaDao(HttpServletRequest request) {
		super();
		this.request = request;
    }
    
    /**
     * Construtor. Chama o Session factory do Hibernate, utiliza o Hibernate Util do Marcas.
         * @param request
         */
    public EstruturaDao(HttpServletRequest request, String cfg) {
		super(cfg);		
    }
       
    public Long numeroItensEstruturaIett(EstruturaEtt ett) {
    	String hql = "select count(iett.codIett) from ItemEstruturaIett iett where iett.estruturaEtt = :ett";
    	
    	Query q = this.getSession().createQuery(hql);
    	q.setEntity("ett", ett);
    	
    	return (Long) q.uniqueResult();
    }
    
    
    /**
     * Altera o registro de estrutura recuperado a partir de vari�veis do request passadas pelo formulario de altera��o 
     * de estrutura (configuracao/estrutura/frm_alt.jsp)
     * @param request
     * @throws ECARException
     */
    public void alterar(HttpServletRequest request) throws ECARException{
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();
		    
			super.inicializarLogBean();

            EstruturaEtt estrutura = (EstruturaEtt) this.buscar(EstruturaEtt.class, 
					Long.valueOf(Pagina.getParamLong(request, "codigo")));
			
            //valida��o de estruturas duplicadas com base no nome e sigla 
            this.validarEstruturaDuplicada(Pagina.getParamLong(request, "codigo"),Pagina.getParam(request, "nomeEtt"),Pagina.getParam(request, "siglaEtt"));
			this.verificarAtributoUtilizadoRestringirVisualizacao(estrutura, request.getParameterValues("atributosAtb"));
            
			tx = session.beginTransaction();
			
            estrutura.setNomeEtt(Pagina.getParam(request, "nomeEtt"));
            estrutura.setSiglaEtt(Pagina.getParam(request, "siglaEtt"));
            estrutura.setSeqApresentacaoEtt(Integer.valueOf(Pagina.getParam(request, "seqApresentacaoEtt")));
            
			Set atributosEstruturaAnteriores = estrutura.getEstruturaAtributoEttats();			
			Set funcoesEstruturaAnteriores = estrutura.getEstruturaFuncaoEttfs();			
			Set funcoesAcompanhamentoAnteriores = estrutura.getEstrutTpFuncAcmpEtttfas();			

			/* Cria a nova Collection de Atributos da Estrutura, baseado no request e nos dados j� existentes. Deve fazer isso antes de 
			apagar a Collection */	
			String[] atributosSelecionados = request.getParameterValues("atributosAtb");
			
			Set funcoesEstrutura = this.getSetFuncoesEstrutura(estrutura, request.getParameterValues("funcaoFun"), true);	
			Set<EstruturaAtributoEttat> atributosEstrutura = this.getSetAtributosEstrutura(estrutura, atributosSelecionados, true);
			Set funcoesAcompanhamentoEstrutura = this.getSetFuncoesAcompEstrutura(estrutura, request.getParameterValues("tipoFuncAcompTpfa"));
			
						
        	//Verifica se pode adicionar um atributo 
			if (!permiteIncluirAtributoLivreID(request, estrutura, atributosSelecionados, atributosEstruturaAnteriores)) {
				throw new ECARException("estruturaAtributo.validacao.estrutura.possuiItens.campoID");
			}

			
            Iterator itAtbAnt = atributosEstruturaAnteriores.iterator();
            while (itAtbAnt.hasNext()) {
                EstruturaAtributoEttat estruturaAtributoAnt = (EstruturaAtributoEttat) itAtbAnt.next();

                boolean achou = false;
                Iterator itAtb = atributosEstrutura.iterator();
                while (itAtb.hasNext()) {
                    EstruturaAtributoEttat estruturaAtributo = (EstruturaAtributoEttat) itAtb.next();
                    if(estruturaAtributo.getAtributosAtb().equals(estruturaAtributoAnt.getAtributosAtb())) {
                    	achou = true;
                    	break;                    	
                    }
                                                            	                    
                }
                if(!achou) {
                	session.delete(estruturaAtributoAnt);
                }
            }
			
            Iterator itFunAnt = funcoesEstruturaAnteriores.iterator();
            while (itFunAnt.hasNext()) {
            	EstruturaFuncaoEttf estruturaFuncaoAnt = (EstruturaFuncaoEttf) itFunAnt.next();

                boolean achou = false;
                Iterator itFun = funcoesEstrutura.iterator();
                while (itFun.hasNext()) {
                	EstruturaFuncaoEttf estruturaFuncao = (EstruturaFuncaoEttf) itFun.next();
                    if(estruturaFuncao.getFuncaoFun().equals(estruturaFuncaoAnt.getFuncaoFun())) {
                    	achou = true;
                    	break;                    	
                    }
                }
                if(!achou) {
                	session.delete(estruturaFuncaoAnt);
                }
            }
			
            Iterator itTpFunAnt = funcoesAcompanhamentoAnteriores.iterator();
            while (itTpFunAnt.hasNext()) {
            	EstrutTpFuncAcmpEtttfa estruturaTpFuncaoAnt = (EstrutTpFuncAcmpEtttfa) itTpFunAnt.next();

                boolean achou = false;
                Iterator itTpFun = funcoesAcompanhamentoEstrutura.iterator();
                while (itTpFun.hasNext()) {
                	EstrutTpFuncAcmpEtttfa estruturaTpFuncao = (EstrutTpFuncAcmpEtttfa) itTpFun.next();
                    if(estruturaTpFuncao.getTipoFuncAcompTpfa().equals(estruturaTpFuncaoAnt.getTipoFuncAcompTpfa())) {
                    	achou = true;
                    	break;                    	
                    }
                }
                if(!achou) {
                	session.delete(estruturaTpFuncaoAnt);
                }
            }
            
			/* recupera os novos dados da Estrutura */
			this.setEstrutura(request, estrutura, true, true, false);
			
			
			Set<SisAtributoSatb> listaSisAtributoSatb = new HashSet<SisAtributoSatb>();
		
			
			//adiciona todos os editaveis
			if( request.getParameterValues("sisAtributoSatbEttSuperiorEditaveis") != null) {
				String[] atributoEttSuperiorSelecionadosEditaveis = request.getParameterValues("sisAtributoSatbEttSuperiorEditaveis");
				listaSisAtributoSatb = this.getSetSisAtributoSatbEttSuperior(estrutura, atributoEttSuperiorSelecionadosEditaveis);
			} 
			
			//verifica agora os que nao sao editaveis e ja pertenciam a lista
			if (estrutura.getSisAtributoSatbEttSuperior() != null) {
				Iterator itListaSisAtributoSatb = estrutura.getSisAtributoSatbEttSuperior().iterator();
				while (itListaSisAtributoSatb.hasNext()){
					SisAtributoSatb sisAtributoSatbSelecionado = (SisAtributoSatb) itListaSisAtributoSatb.next();
					if(this.existeSisAtributoSatbUsadoEstruturaSuperior(sisAtributoSatbSelecionado,estrutura)) {
						//vai adicionar todos os atributos nao editaveis
						listaSisAtributoSatb.add(sisAtributoSatbSelecionado);
					} 
				}
			}
			
			//quando alterar os atributos editaveis, tb vai ser setado os campos
			if (Pagina.getParamStr(request, "indAlterarCamposRestritivos").equals(Dominios.SIM)){
				if(Pagina.getParam(request, "indExibirEstruturaEtt") != null) {
					estrutura.setIndExibirEstruturaEtt(Pagina.getParam(request, "indExibirEstruturaEtt"));
				} else {
					estrutura.setIndExibirEstruturaEtt(Dominios.NAO);
				}
	    		estrutura.setAtributoAtbExibirEstruturaEtt(this.getAtributoAtbExibirEstruturaEtt((Pagina.getParam(request, "codAtbExibirEstruturaEtt"))));
	    		estrutura.setSisAtributoSatbEttSuperior(listaSisAtributoSatb);
	    		
			} else if(!listaSisAtributoSatb.isEmpty()) {
				estrutura.setSisAtributoSatbEttSuperior(listaSisAtributoSatb);
			}
			
			
			estrutura.setEstruturaAtributoEttats(atributosEstrutura);
			estrutura.setEstrutTpFuncAcmpEtttfas(funcoesAcompanhamentoEstrutura);
			estrutura.setEstruturaFuncaoEttfs(funcoesEstrutura);
	    	
	        estrutura.setDataUltManutEtt(Data.getDataAtual());
	        
	        session.update(estrutura);
			objetos.add(estrutura);

			List filhos = new ArrayList();
			filhos.addAll(atributosEstrutura);			
			filhos.addAll(funcoesEstrutura);			
			filhos.addAll(funcoesAcompanhamentoEstrutura);
			


			Iterator it = filhos.iterator();
	        
	        while(it.hasNext()){
				PaiFilho object = (PaiFilho) it.next();
				object.atribuirPKPai();
				session.saveOrUpdate(object);
				objetos.add(object);
	        }

            tx.commit();

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC_ALT_EXC");
				Iterator itObj = objetos.iterator();

				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
        } catch(HibernateException e){
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
        catch(ECARException e){
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
     * Recebe uma estrutura e uma lista de codAtb e lan�a mensagem de valida��o de 
     * utiliza��o dos atributos na restri��o de visualiza��o das estruturas filhas da estrutura
     * 
     * @param estrutura
     * @return
     * @throws ECARException
     */
    private void verificarAtributoUtilizadoRestringirVisualizacao(EstruturaEtt estrutura, String[] atributos) throws ECARException {
        
        boolean achouAtributoRestritivo = false;
    	Iterator<EstruturaEtt> estruturasFilhasIt = new ArrayList(estrutura.getEstruturaEtts()).iterator();
    	AtributoDao atributoDao = new AtributoDao(null);
    	Set<AtributosAtb> atributoNovos = new HashSet<AtributosAtb>(); 
    	Set<EstruturaAtributoEttat> atributosAnteriores = null;
    	Set<AtributosAtb> atributosRestritivos = new HashSet<AtributosAtb>(); 
    	String nomeAtributos = "";
       	StringBuffer args = null;
    	
    	//monta a lista de atributos novos
    	if(atributos != null && atributos.length > 0) {
	    	for (int i = 0; i < atributos.length; i++) {
	    		AtributosAtb atributo =  (AtributosAtb)atributoDao.buscar(AtributosAtb.class, Long.valueOf(atributos[i]));
	    		atributoNovos.add(atributo);
	    	}	
    	}
    	
    	//monta a lista de atributos anteriores
    	if(estrutura.getEstruturaAtributoEttats() != null) {
    		atributosAnteriores = estrutura.getEstruturaAtributoEttats();
    	}

        while(estruturasFilhasIt.hasNext() && atributosAnteriores != null) {
        	EstruturaEtt estruturaFilha = (EstruturaEtt) estruturasFilhasIt.next();
        	if(estruturaFilha.getIndExibirEstruturaEtt().equals(Dominios.SIM)) {
        		//percorre a lista de atributos para verificar se estao sendo utilizados pela estrutura filha
        		Iterator itAtributoAnteriores = atributosAnteriores.iterator();
        		while(itAtributoAnteriores.hasNext()) {
    	    		EstruturaAtributoEttat estruturaAtributo = (EstruturaAtributoEttat) itAtributoAnteriores.next();
        			AtributosAtb atributoAnterior =  estruturaAtributo.getAtributosAtb();
        			//se o atributo foi desmarcado e ele n�o � obrigat�rio e est� sendo usado por alguma estrutura filha
        			//e se o atributo j� nao est� na lista (pesquisa todos os atributos que est�o sendo utilizados)
        			if(!atributoNovos.contains(atributoAnterior) && atributoAnterior.getIndOpcionalAtb().equals(Pagina.SIM) &&
        				!atributosRestritivos.contains(atributoAnterior) &&
        				estruturaFilha.getAtributoAtbExibirEstruturaEtt() != null && 
	        			estruturaFilha.getAtributoAtbExibirEstruturaEtt().getCodAtb().equals(atributoAnterior.getCodAtb())) {
        				
        		  		if (args == null){
                 			args = new StringBuffer();
                 			args.append(this.getLabelAtributoEstrutra(estrutura,atributoAnterior.getSisGrupoAtributoSga()));
                 		
        		  		} else {
                 			args.append(", ").append(this.getLabelAtributoEstrutra(estrutura,atributoAnterior.getSisGrupoAtributoSga()));
                 		}
        		  		
        		  		atributosRestritivos.add(atributoAnterior);
        			}
				}
			}
        }
        
        
        
        if(args != null) {
        	throw new ECARException("estrutura.validacao.alteracao.estruturaAtributo", null, new String[]{args.toString()});
        }
        
        
        
        
        
    }
    
	private void validarEstruturaDuplicada(Long codigo,String nomeEtt, String siglaEtt) 
		throws ECARException {
        
		EstruturaEtt estrutura = new EstruturaEtt();
		
		/* setar nome e sigla para usar na valida��o de duplos */
        if (codigo != null){
        	estrutura.setCodEtt(codigo);
        }
		estrutura.setNomeEtt(nomeEtt);
        estrutura.setSiglaEtt(siglaEtt);
        
        List<EstruturaEtt> listaEstruturas = super.pesquisarDuplosExatos(estrutura, new String[]{"nomeEtt","siglaEtt"},"codEtt");
        
        if (!listaEstruturas.isEmpty()) {	
        	throw new ECARException("estrutura.validacao.registroDuplicado");
        }
        
	}

    /**
     * Recebe um grupo de atributos e retorna se ele est� sendo utilizado na restri��o de visualiza��o de estruturas
     * 
     * @param sisGrupoAtributo
     * @return
     * @throws ECARException
     */
    public boolean verificaSisGrupoAtributoRestringirVisualizacao(SisGrupoAtributoSga sisGrupoAtributo) throws ECARException {
        
        boolean achouSisGrupoAtributoRestritivo = false;
		Iterator<EstruturaEtt> estruturasIt = this.pesquisar(new EstruturaEtt(), new String[] {"codEtt","asc"}).iterator();
        
        while(estruturasIt.hasNext()) {
        	EstruturaEtt estrutura = (EstruturaEtt) estruturasIt.next();
        
        	if(estrutura.getIndExibirEstruturaEtt().equals(Dominios.SIM)) {
        		AtributosAtb atributo = estrutura.getAtributoAtbExibirEstruturaEtt();
        		if(atributo.getSisGrupoAtributoSga().getCodSga().toString().equals(sisGrupoAtributo.getCodSga().toString())) {
        			achouSisGrupoAtributoRestritivo = true;
					break;
				}
    		}
        }
        return achouSisGrupoAtributoRestritivo;
    }
	
    /**
     * Recebe um grupo de atributos e retorna se ele est� sendo utilizado na restri��o de visualiza��o de estruturas
     * 
     * @param sisGrupoAtributo
     * @return
     * @throws ECARException
     */
    public boolean verificaSisAtributoRestringirVisualizacao(SisAtributoSatb sisAtributoSatb) throws ECARException {
        
        boolean achouSisGrupoAtributoRestritivo = false;
		Iterator<EstruturaEtt> estruturasIt = this.pesquisar(new EstruturaEtt(), new String[] {"codEtt","asc"}).iterator();
        
        while(estruturasIt.hasNext()) {
        	EstruturaEtt estrutura = (EstruturaEtt) estruturasIt.next();
        
        	if(estrutura.getIndExibirEstruturaEtt().equals(Dominios.SIM)) {
        		AtributosAtb atributo = estrutura.getAtributoAtbExibirEstruturaEtt();
        		if( atributo.getSisGrupoAtributoSga().getCodSga().toString().equals(sisAtributoSatb.getSisGrupoAtributoSga().getCodSga().toString())) {
        			Iterator<SisAtributoSatb> sisAtributosIt = estrutura.getSisAtributoSatbEttSuperior().iterator();
        			while(sisAtributosIt.hasNext()) {
        				SisAtributoSatb sisAtributo = sisAtributosIt.next();
	        			if(sisAtributo.getCodSatb().toString().equals(sisAtributoSatb.getCodSatb().toString())) {
	        				achouSisGrupoAtributoRestritivo = true;
							break;
	        			}
        			}
        			if(achouSisGrupoAtributoRestritivo)
        				break;
				}
    		}
        }
        return achouSisGrupoAtributoRestritivo;
    }
    
	/**
	 * Valida se pode incluir um novo atributo livre na estrutura. A valida��o verifica se j� existe itens na estrutura. 
	 * @param request
	 * @param estrutura
	 * @param atributosSelecionados
	 * @param atributosEstrutura
	 * @throws ECARException
	 */
	@SuppressWarnings("unchecked")
	private boolean permiteIncluirAtributoLivreID(HttpServletRequest request, EstruturaEtt estrutura, String[] atributosSelecionados,Set<EstruturaAtributoEttat> atributosEstrutura) 
		throws ECARException {
		
		boolean permiteIncluir = true;
		
		for (String idAtributo : atributosSelecionados) {
			boolean idAtributoAntigo= false; 
			
			for (EstruturaAtributoEttat atributoEstrutura : atributosEstrutura) {

				if (new Long(idAtributo).equals(atributoEstrutura.getAtributosAtb().getCodAtb())){
					idAtributoAntigo = true;
					break;
				}
				
			}
			
			//Se n�o for atributoAntigo, ou seja um atributo novo, entrar� na valida��o verificando se existem itens cadastrados na estrutura.
			if (!idAtributoAntigo){
				AtributosAtb atributo = (AtributosAtb) (this.buscar(AtributosAtb.class, Long.valueOf(idAtributo)));
				
				//Verifica se o atributo possui grupo de atributo livre associado e se o grupo de atributo livre possui atributos livres associados. 
				if (atributo.getSisGrupoAtributoSga() != null && !atributo.getSisGrupoAtributoSga().getSisAtributoSatbs().isEmpty()){

					//Obtem apenas o primeiro atributo livre associado ao grupo. Caso o grupo possua mais de um atributo livre associado est� errado. Foi incluido no cadastro de atributos livres uma restri��o para n�o permitir associar mais de um atributo livre ao grupo de atributo livre do tipo valida��o. 
		        	SisAtributoSatb sisAtributo = (SisAtributoSatb) atributo.getSisGrupoAtributoSga().getSisAtributoSatbs().toArray()[0];
					
		        	//Verifica se o atributo livre � do tipo ID
		        	if (sisAtributo.isAtributoAutoIcremental() || sisAtributo.isAtributoContemMascara()){
		            	
		        		//Pesquisa itens da estrutura
		    			List itensEstruturaAtual = new ItemEstruturaDao(request).getItensByEstrutura(estrutura.getCodEtt());
		    			
		    			//Caso a estrutura tenha itens incluidos. A inclus�o de um novo atributo livre do tipo ID n�o ser� permitida. 
		            	if (itensEstruturaAtual != null && !itensEstruturaAtual.isEmpty()){
		            		
		            		permiteIncluir = false;
		            		break;
		            	}
		            }
				}
			}
		}
		
		return permiteIncluir;
	}
  
    /**
     * Recebe um array com os C�digos das Estruturas e retorna um Set com
     * objetos EstruturaEtt correspondentes a estes c�digos
     * 
     * @param estruturas
     * @return Set objetos EstruturaEtt
     * @throws ECARException
     */
    public Set getSetEstruturas(String estruturas[]) throws ECARException {
        Set retorno = new HashSet();
        if (estruturas != null) {
            for (int i = 0; i < estruturas.length; i++) {
                try {
                    EstruturaEtt estrutura = (EstruturaEtt) this.buscar(
                            EstruturaEtt.class, Long.valueOf(estruturas[i]));
                    retorno.add(estrutura);
                } catch (ECARException e) {
                	this.logger.error(e);
                	throw e;
                }
            }
        }
        return retorno;

    }
    
   

    /**
     * Atribui os valores dos atributos passados por request a um objeto  Estrutura
     * 
     * Usado porque nao existe valor defaul para os campos na Pesquisa 
     * 
     * @param request
     *            Request
     * @param estrutura
     *            Objeto Estrutura no qual ser�o atribuidos os valores
     * @param usarParamStr
     *            <b>True </b> se os par�metros ser�o recuperados por
     *            Pagina.getParamStr <b>False </b> se os par�metros ser�o
     *            recuperados por Pagina.getParam
     * @param incluirObrigatorios 
     * @param criarCollections
     * @throws ECARException
     */
    public void setEstrutura(HttpServletRequest request,
            EstruturaEtt estrutura, boolean usarParamStr,
            boolean incluirObrigatorios, boolean criarCollections) throws ECARException {

        if (usarParamStr) {
        	
            estrutura.setNomeEtt(Pagina.getParamStr(request, "nomeEtt").trim());
            estrutura.setSiglaEtt(Pagina.getParamStr(request, "siglaEtt").trim());
            estrutura.setLabelEtt(Pagina.getParamStr(request, "labelEtt").trim());
            estrutura.setIndAtivoEtt(Pagina.getParamStr(request, "indAtivoEtt").trim());
            estrutura.setIndPrevFinanceiraEtt(Pagina.getParamStr(request,"indPrevFinanceiraEtt").trim());
            if(!"".equals(Pagina.getParamStr(request, "seqApresentacaoEtt").trim())){
            	estrutura.setSeqApresentacaoEtt(Integer.valueOf(Pagina.getParamStr(request,"seqApresentacaoEtt").trim()));
            }
            else {
            	estrutura.setSeqApresentacaoEtt(null);
            }
            estrutura.setCodCor1Ett(Pagina.getParamStr(request,"codCor1Ett").trim());
            estrutura.setCodCor2Ett(Pagina.getParamStr(request,"codCor2Ett").trim());
            estrutura.setCodCor3Ett(Pagina.getParamStr(request,"codCor3Ett").trim());
		    estrutura.setCodCor4Ett(Pagina.getParamStr(request,"codCor4Ett").trim());
		    
		    estrutura.setIndExibirImprimirListagem(Pagina.getParamOrDefault(request, "indExibirImprimirListagem", Pagina.NAO));
		    estrutura.setIndExibirOpcaoModelo(Pagina.getParamOrDefault(request, "indExibirOpcaoModelo", Pagina.NAO));
		    estrutura.setIndExibirGerarArquivos(Pagina.getParamOrDefault(request, "indExibirGerarArquivos", Pagina.NAO));
		    estrutura.setIndEtapaNivelSuperiorEtt(Pagina.getParamOrDefault(request, "indEtapaNivelSuperiorEtt", Pagina.NAO));
		    
		    if(!"".equals(Pagina.getParamStr(request, "tamanhoListagemVerticalEtt"))) {
		    	estrutura.setTamanhoListagemVerticalEtt(Long.valueOf(Pagina.getParamStr(request, "tamanhoListagemVerticalEtt")));
		    } else {
		    	estrutura.setTamanhoListagemVerticalEtt(null);
		    }
		    
		    estrutura.setVirtual(new Boolean (Pagina.getParam(request, "virtual")));
		    
        } else {
            estrutura.setNomeEtt(Pagina.getParam(request,"nomeEtt"));
            estrutura.setSiglaEtt(Pagina.getParam(request,"siglaEtt"));
            estrutura.setLabelEtt(Pagina.getParam(request,"labelEtt"));
            estrutura.setIndAtivoEtt(Pagina.getParam(request,"indAtivoEtt"));
            estrutura.setIndPrevFinanceiraEtt(Pagina.getParam(request,"indPrevFinanceiraEtt"));
            
            if(Pagina.getParam(request, "seqApresentacaoEtt") != null){
            	estrutura.setSeqApresentacaoEtt(Integer.valueOf(Pagina.getParam(request,"seqApresentacaoEtt")));
            }
            else {
            	estrutura.setSeqApresentacaoEtt(null);
            }
            estrutura.setCodCor1Ett(Pagina.getParam(request,"codCor1Ett"));
		    estrutura.setCodCor2Ett(Pagina.getParam(request,"codCor2Ett"));
		    estrutura.setCodCor3Ett(Pagina.getParam(request,"codCor3Ett"));
		    estrutura.setCodCor4Ett(Pagina.getParam(request,"codCor4Ett"));
		    estrutura.setIndExibirImprimirListagem(Pagina.getParam(request, "indExibirImprimirListagem"));
		    estrutura.setIndExibirOpcaoModelo(Pagina.getParam(request, "indExibirOpcaoModelo"));
		    estrutura.setIndEtapaNivelSuperiorEtt(Pagina.getParam(request, "indEtapaNivelSuperiorEtt"));
		    
		    if(Pagina.getParam(request, "tamanhoListagemVerticalEtt") != null)
		    	estrutura.setTamanhoListagemVerticalEtt(Long.valueOf(Pagina.getParam(request, "tamanhoListagemVerticalEtt")));
		    else
		    	estrutura.setTamanhoListagemVerticalEtt(null);
        }

        if (Pagina.getParam(request, "estruturaEttPai") != null){
            estrutura.setEstruturaEtt((EstruturaEtt) this.buscar(EstruturaEtt.class, Long.valueOf(Pagina.getParam(request,"estruturaEttPai"))));
        }
        else if (Pagina.getParam(request, "estruturaEttPaiHidden") != null){
        	estrutura.setEstruturaEtt((EstruturaEtt) this.buscar(EstruturaEtt.class, Long.valueOf(Pagina.getParam(request,"estruturaEttPaiHidden"))));
        } else {
        	estrutura.setEstruturaEtt(null);
        }
        
        if (Pagina.getParamStr(request, "indAlterarCamposRestritivos").equals(Dominios.SIM)){
        	if(Pagina.getParam(request, "indExibirEstruturaEtt") != null) {
    			estrutura.setIndExibirEstruturaEtt(Pagina.getParam(request, "indExibirEstruturaEtt"));
        	
        		estrutura.setAtributoAtbExibirEstruturaEtt(this.getAtributoAtbExibirEstruturaEtt((Pagina.getParam(request, "codAtbExibirEstruturaEtt"))));
        		String[] atributos = request.getParameterValues("sisAtributoSatbEttSuperior");
        	
        		Set<SisAtributoSatb> listaSisAtributoSatb = new HashSet<SisAtributoSatb>();
        		
        		if (request.getParameterValues("sisAtributoSatbEttSuperior") != null) {
                    atributos = request.getParameterValues("sisAtributoSatbEttSuperior");
                    listaSisAtributoSatb.addAll(this.getSetSisAtributoSatbEttSuperior(estrutura, atributos));
                } 
        		
        		
        		if (request.getParameterValues("sisAtributoSatbEttSuperiorEditaveis") != null) {
                    atributos = request.getParameterValues("sisAtributoSatbEttSuperiorEditaveis");
                    listaSisAtributoSatb.addAll(this.getSetSisAtributoSatbEttSuperior(estrutura, atributos));
                } 
        		
        		if(!listaSisAtributoSatb.isEmpty()) {
        			estrutura.setSisAtributoSatbEttSuperior(listaSisAtributoSatb);
        		} else {
        			estrutura.setSisAtributoSatbEttSuperior(null);
        		}
        		
        		
    		} else {
    			estrutura.setIndExibirEstruturaEtt(Dominios.NAO);
       			estrutura.setAtributoAtbExibirEstruturaEtt(null);
               	estrutura.setSisAtributoSatbEttSuperior(null);
    		}
       }
		
        if(criarCollections){
        
        	 if (request.getParameterValues("funcaoFun") != null) {
                 String[] funcoes = request.getParameterValues("funcaoFun");

                 if (funcoes.length > 0)
                     estrutura.setEstruturaFuncaoEttfs(this.getSetFuncoesEstrutura(estrutura, funcoes, incluirObrigatorios));
             } else {
                 if(incluirObrigatorios)
                     estrutura.setEstruturaFuncaoEttfs(this.getSetFuncoesEstruturaObrigatorios(estrutura));
                 else
                     estrutura.setEstruturaFuncaoEttfs(null);
             }

        	
        	if (request.getParameterValues("atributosAtb") != null) {
                String[] atributos = request.getParameterValues("atributosAtb");
                estrutura.setEstruturaAtributoEttats(this.getSetAtributosEstrutura(estrutura, atributos, incluirObrigatorios));
            } else {                    
                if (incluirObrigatorios)
                    estrutura.setEstruturaAtributoEttats(this.getSetAtributosEstruturaObrigatorios(estrutura));
                else
                    estrutura.setEstruturaAtributoEttats(null);
            }

           
            if (request.getParameterValues("tipoFuncAcompTpfa") != null) {
                String[] funcoesAcomp = request.getParameterValues("tipoFuncAcompTpfa");

                if (funcoesAcomp.length > 0)
                    estrutura.setEstrutTpFuncAcmpEtttfas(this.getSetFuncoesAcompEstrutura(estrutura, funcoesAcomp));
            } else {
                estrutura.setEstrutTpFuncAcmpEtttfas(null);
            }
            
        }
        
        if(Pagina.getParam(request, "virtual") != null){
        	estrutura.setVirtual(new Boolean (Pagina.getParam(request, "virtual")));
        }
     	estrutura.setIndExibirGerarArquivos(Pagina.getParam(request, "indExibirGerarArquivos"));

    }
    
    
   
    
    /**
     * Atribui os valores dos atributos passados por request a um objeto
     * Estrutura para pesquisa (n�o existe valor default) -> c�digo acima bastante confuso
     * 
     * @param request
     *            Request
     * @param estrutura
     *            Objeto Estrutura no qual ser�o atribuidos os valores
     * @param usarParamStr
     *            <b>True </b> se os par�metros ser�o recuperados por
     *            Pagina.getParamStr <b>False </b> se os par�metros ser�o
     *            recuperados por Pagina.getParam
     * @param incluirObrigatorios 
     * @param criarCollections
     * @throws ECARException
     */
    public void setEstruturaPesquisa(HttpServletRequest request,
            EstruturaEtt estrutura, boolean usarParamStr,
            boolean incluirObrigatorios, boolean criarCollections) throws ECARException {

       
            estrutura.setNomeEtt(Pagina.getParamStr(request, "nomeEtt").trim());
            estrutura.setSiglaEtt(Pagina.getParamStr(request, "siglaEtt").trim());
            estrutura.setLabelEtt(Pagina.getParamStr(request, "labelEtt").trim());
            estrutura.setIndAtivoEtt(Pagina.getParamStr(request, "indAtivoEtt").trim());
            estrutura.setIndPrevFinanceiraEtt(Pagina.getParamStr(request,"indPrevFinanceiraEtt").trim());
            if(!"".equals(Pagina.getParamStr(request, "seqApresentacaoEtt").trim())){
            	estrutura.setSeqApresentacaoEtt(Integer.valueOf(Pagina.getParamStr(request,"seqApresentacaoEtt").trim()));
            }
            else {
            	estrutura.setSeqApresentacaoEtt(null);
            }
            estrutura.setCodCor1Ett(Pagina.getParamStr(request,"codCor1Ett").trim());
            estrutura.setCodCor2Ett(Pagina.getParamStr(request,"codCor2Ett").trim());
            estrutura.setCodCor3Ett(Pagina.getParamStr(request,"codCor3Ett").trim());
		    estrutura.setCodCor4Ett(Pagina.getParamStr(request,"codCor4Ett").trim());
		    
		    estrutura.setIndExibirImprimirListagem(Pagina.getParamStr(request, "indExibirImprimirListagem"));
		    estrutura.setIndExibirOpcaoModelo(Pagina.getParamStr(request, "indExibirOpcaoModelo"));
		    estrutura.setIndExibirGerarArquivos(Pagina.getParamStr(request, "indExibirGerarArquivos"));
		    estrutura.setIndEtapaNivelSuperiorEtt(Pagina.getParamStr(request, "indEtapaNivelSuperiorEtt"));
		
		    
		    if(!"".equals(Pagina.getParamStr(request, "tamanhoListagemVerticalEtt"))) {
		    	estrutura.setTamanhoListagemVerticalEtt(Long.valueOf(Pagina.getParamStr(request, "tamanhoListagemVerticalEtt")));
		    } else {
		    	estrutura.setTamanhoListagemVerticalEtt(null);
		    }
		    
		    
		    
		    String virtual = Pagina.getParamStr(request, "virtual");
	        if(!virtual.equals("")){
	        	estrutura.setVirtual(new Boolean (Pagina.getParam(request, "virtual")));
	        }

        if (Pagina.getParam(request, "estruturaEttPai") != null){
            estrutura.setEstruturaEtt((EstruturaEtt) this.buscar(EstruturaEtt.class, Long.valueOf(Pagina.getParam(request,"estruturaEttPai"))));
        }
        else if (Pagina.getParam(request, "estruturaEttPaiHidden") != null){
        	estrutura.setEstruturaEtt((EstruturaEtt) this.buscar(EstruturaEtt.class, Long.valueOf(Pagina.getParam(request,"estruturaEttPaiHidden"))));
        } else {
        	estrutura.setEstruturaEtt(null);
        }
        
        if(Pagina.getParam(request, "indAlterouNivelSuperior") == null && (Pagina.getParam(request, "indExibirEstruturaEtt") != null)) {
	    		estrutura.setIndExibirEstruturaEtt(Pagina.getParam(request, "indExibirEstruturaEtt"));
	        	estrutura.setAtributoAtbExibirEstruturaEtt(this.getAtributoAtbExibirEstruturaEtt(Pagina.getParam(request, "codAtbExibirEstruturaEtt")));
	            if (request.getParameterValues("sisAtributoSatbEttSuperior") != null) {
	                 String[] atributos = request.getParameterValues("sisAtributoSatbEttSuperior");
	                 estrutura.setSisAtributoSatbEttSuperior(this.getSetSisAtributoSatbEttSuperior(estrutura, atributos));
	             } else {                    
	               	estrutura.setSisAtributoSatbEttSuperior(null);
	              }
	   	} else {
	   		estrutura.setIndExibirEstruturaEtt(null);
	   		estrutura.setAtributoAtbExibirEstruturaEtt(null);
	   		estrutura.setSisAtributoSatbEttSuperior(null);
	   	}
           
		
        if(criarCollections){
        
        	 if (request.getParameterValues("funcaoFun") != null) {
                 String[] funcoes = request.getParameterValues("funcaoFun");

                 if (funcoes.length > 0)
                     estrutura.setEstruturaFuncaoEttfs(this.getSetFuncoesEstrutura(estrutura, funcoes, incluirObrigatorios));
             } else {
                 if(incluirObrigatorios)
                     estrutura.setEstruturaFuncaoEttfs(this.getSetFuncoesEstruturaObrigatorios(estrutura));
                 else
                     estrutura.setEstruturaFuncaoEttfs(null);
             }

        	
        	if (request.getParameterValues("atributosAtb") != null) {
                String[] atributos = request.getParameterValues("atributosAtb");
                estrutura.setEstruturaAtributoEttats(this.getSetAtributosEstrutura(estrutura, atributos, incluirObrigatorios));
            } else {                    
                if (incluirObrigatorios)
                    estrutura.setEstruturaAtributoEttats(this.getSetAtributosEstruturaObrigatorios(estrutura));
                else
                    estrutura.setEstruturaAtributoEttats(null);
            }

           
            if (request.getParameterValues("tipoFuncAcompTpfa") != null) {
                String[] funcoesAcomp = request.getParameterValues("tipoFuncAcompTpfa");

                if (funcoesAcomp.length > 0)
                    estrutura.setEstrutTpFuncAcmpEtttfas(this.getSetFuncoesAcompEstrutura(estrutura, funcoesAcomp));
            } else {
                estrutura.setEstrutTpFuncAcmpEtttfas(null);
            }

       
        }
        
       
       

    }

	/**
     * Recebe um array com os C�digos dos Atributos e retorna um Set com objetos
     * EstruturaSisAtributo
     * 
     * @param estrutura
     * @param atributos
     * @return Set objetos EstruturaSisAtributo
     * @throws ECARException
     */
    public Set<SisAtributoSatb> getSetSisAtributoSatbEttSuperior(EstruturaEtt estrutura, String atributos[]) throws ECARException {
        Set<SisAtributoSatb> retorno = new HashSet<SisAtributoSatb>();
        
        if (atributos != null) {
            for (int i = 0; i < atributos.length; i++) {
            	SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) this.buscar(SisAtributoSatb.class, Long.valueOf(Long.valueOf(atributos[i])));
                retorno.add(sisAtributoSatb);
            }
        }
        
        return retorno;
    }
    
    

	/**
     * Recebe o codigo do atributo e retorna o objeto
     * AtributosAtb
     * 
     * @param codAtributoAtbExibirEstruturaEtt
     * @return AtributosAtb retorno
     * @throws ECARException
     */
    public AtributosAtb getAtributoAtbExibirEstruturaEtt(String codAtributoAtbExibirEstruturaEtt) throws ECARException {
        AtributosAtb retorno = null;
        
        if (codAtributoAtbExibirEstruturaEtt != null && !codAtributoAtbExibirEstruturaEtt.equals("")) {
            retorno = (AtributosAtb) this.buscar(AtributosAtb.class, Long.valueOf(codAtributoAtbExibirEstruturaEtt));
        }
        
        return retorno;
    }
    
    /**
     * Concatena todos as cole��es existentes no objeto estrutura em uma �nica
     * lista e chama o m�todo salvar do Dao
     * 
     * @param estrutura
     * @throws ECARException
     */
    public void salvar(EstruturaEtt estrutura) throws ECARException {
    	
    	//valida��o de estruturas duplicadas com base no nome e sigla 
    	this.validarEstruturaDuplicada(null,Pagina.getParam(request, "nomeEtt"), Pagina.getParam(request, "siglaEtt"));
    	this.validarConfiguracaoVisualizacao(estrutura);
   	    	
        estrutura.setDataInclusaoEtt(Data.getDataAtual());
        List filhos = new ArrayList();
        if (estrutura.getEstruturaAtributoEttats() != null)
            filhos.addAll(estrutura.getEstruturaAtributoEttats());
        if (estrutura.getEstruturaFuncaoEttfs() != null)
            filhos.addAll(estrutura.getEstruturaFuncaoEttfs());
        if (estrutura.getEstrutTpFuncAcmpEtttfas() != null)
            filhos.addAll(estrutura.getEstrutTpFuncAcmpEtttfas());
        try{
        	super.salvar(estrutura, filhos);
        }
        catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    private void validarConfiguracaoVisualizacao(EstruturaEtt estrutura) throws ECARException {
    
	    if( estrutura.getSisAtributoSatbEttSuperior() != null && !estrutura.getSisAtributoSatbEttSuperior().isEmpty() &&
	    	(estrutura.getIndExibirEstruturaEtt() == null || estrutura.getIndExibirEstruturaEtt().equals(Dominios.NAO)) ) {
	    	throw new ECARException("estrutura.validacao.visualizacaoPelaEstruturaSuperior");
	    }
	    if( estrutura.getAtributoAtbExibirEstruturaEtt() != null && 
		    (estrutura.getIndExibirEstruturaEtt() == null || estrutura.getIndExibirEstruturaEtt().equals(Dominios.NAO)) ) {
		    	throw new ECARException("estrutura.validacao.visualizacaoPelaEstruturaSuperior");
		}
	    if( estrutura.getIndExibirEstruturaEtt() != null && estrutura.getIndExibirEstruturaEtt().equals(Dominios.SIM) && 
	    	(estrutura.getAtributoAtbExibirEstruturaEtt() == null) ) {
	    	throw new ECARException("estrutura.validacao.visualizacaoPelaEstruturaSuperiorGprAtributo");
		}
		if( estrutura.getIndExibirEstruturaEtt() != null && estrutura.getIndExibirEstruturaEtt().equals(Dominios.SIM) && 
			estrutura.getAtributoAtbExibirEstruturaEtt() != null &&
		    (estrutura.getSisAtributoSatbEttSuperior() == null || estrutura.getSisAtributoSatbEttSuperior().isEmpty()) ) {
			throw new ECARException("estrutura.validacao.visualizacaoPelaEstruturaSuperiorAtributos");
		}
	    
    }

    /**
     * Exclui um registro de Estrutura e seus registros filhos 
     * 
	 * @author n/c
     * @param estrutura
     * @throws ECARException
     */
    public void excluir(EstruturaEtt estrutura) throws ECARException {
        List objetosParaExcluir = new ArrayList();
        
        try {
            boolean excluir = true;

            if (contar(estrutura.getTipoAcompanhamentoTas()) > 0) {
            	List list = new ArrayList(estrutura.getTipoAcompanhamentoTas());
            	TipoAcompanhamentoTa ocorrencia = (TipoAcompanhamentoTa) list.get(0);
            	
                excluir = false;
                throw new ECARException(
                        "estrutura.exclusao.erro.tipoAcompanhamentoTas", null, new String[] {ocorrencia.getDescricaoTa()});
            }
            if (contar(estrutura.getItemEstruturaIetts()) > 0) {
            	List list = new ArrayList(estrutura.getItemEstruturaIetts());
            	ItemEstruturaIett ocorrencia = (ItemEstruturaIett)list.get(0); 
            	
                excluir = false;
                
                if(ocorrencia.getNomeIett() != null) {
                	throw new ECARException(
                        "estrutura.exclusao.erro.itemEstruturaIetts", null, new String[] {ocorrencia.getNomeIett()});
                } else if (ocorrencia.getDescricaoIett() != null) {
                	throw new ECARException(
                            "estrutura.exclusao.erro.itemEstruturaIetts", null, new String[] {ocorrencia.getDescricaoIett()});
                } else {
                	throw new ECARException(
                            "estrutura.exclusao.erro.itemEstruturaIetts", null, new String[] {""});
                }
            }
            if (contar(estrutura.getEstruturaSituacaoEtts()) > 0){
            	List list = new ArrayList(estrutura.getEstruturaSituacaoEtts());
            	SituacaoSit ocorrencia = (SituacaoSit) list.get(0);
            	
            	excluir = false;
            	throw new ECARException(
            			"estrutura.exclusao.erro.estruturaSituacaoEtts", null, new String[] {ocorrencia.getDescricaoSit()});
            }
 
            if (contar(estrutura.getEstruturaEtts()) > 0){
            	excluir = false;
            	throw new ECARException("estrutura.exclusao.erro.estruturaEtts");
            }            
                       
            if(contar(estrutura.getPerfilIntercambioDadosCadastroPidcsBaseImp()) > 0){
		        excluir = false;
		        PerfilIntercambioDadosCadastroPidc pidc = (PerfilIntercambioDadosCadastroPidc) new ArrayList(estrutura.getPerfilIntercambioDadosCadastroPidcsBaseImp()).get(0);
		        throw new ECARException("estrutura.exclusao.erro.perfilIntercambioDadosPflids", null, new String[] {pidc.getNomePflid()});			       
		    }
            
		    if(contar(estrutura.getPerfilIntercambioDadosCadastroPidcsCriacaoItemImp()) > 0){
		        excluir = false;
		        PerfilIntercambioDadosCadastroPidc pidc = (PerfilIntercambioDadosCadastroPidc) new ArrayList(estrutura.getPerfilIntercambioDadosCadastroPidcsCriacaoItemImp()).get(0);
		        throw new ECARException("estrutura.exclusao.erro.perfilIntercambioDadosPflids", null, new String[] {pidc.getNomePflid()});			       
		    }
            
		    if(contar(estrutura.getPerfilIntercambioDadosCadastroPidcsItemNivelSuperiorImp()) > 0){
		        excluir = false;
		        PerfilIntercambioDadosCadastroPidc pidc = (PerfilIntercambioDadosCadastroPidc) new ArrayList(estrutura.getPerfilIntercambioDadosCadastroPidcsItemNivelSuperiorImp()).get(0);
		        throw new ECARException("estrutura.exclusao.erro.perfilIntercambioDadosPflids", null, new String[] {pidc.getNomePflid()});			       
		    }
		    
            if (excluir) {
            	objetosParaExcluir.addAll(estrutura.getEstruturaAtributoEttats());
                if(estrutura.getEstruturaAtributoEttats() != null){
                	Iterator itEttat = estrutura.getEstruturaAtributoEttats().iterator();
                	while(itEttat.hasNext()){
                		EstruturaAtributoEttat atributo = (EstruturaAtributoEttat) itEttat.next();
                		if(atributo.getEstAtribTipoAcompEatas() != null && atributo.getEstAtribTipoAcompEatas().size() > 0)
                			objetosParaExcluir.addAll(atributo.getEstAtribTipoAcompEatas());
                	}
                }
                objetosParaExcluir.addAll(estrutura.getEstruturaFuncaoEttfs());
                objetosParaExcluir.addAll(estrutura.getEstrutTpFuncAcmpEtttfas());
                objetosParaExcluir.addAll(estrutura.getEstruturaSituacaoEtts());
                
                estrutura.setSisAtributoSatbEttSuperior(new HashSet<SisAtributoSatb>());
                
                objetosParaExcluir.add(estrutura);
                super.excluir(objetosParaExcluir);
            }
        } catch (ECARException e) {
            throw e;
        }
    }

    /**
     * Invoca o m�todo pesquisar da classe Dao e filtra o resultado obtido para
     * retornar somente os registros que tenham as Atributos e Fun��es
     * informadas na pesquisa
     * 
     * @param estrutura
     * @param ordem
     * @return
     * @throws ECARException
     */
    @SuppressWarnings("unchecked")
	public List pesquisar(EstruturaEtt estrutura, String[] ordem)
            throws ECARException {

        List results = super.pesquisar(estrutura, ordem);
        /* se a estrutura possui atributos */
        if (estrutura.getEstruturaAtributoEttats() != null) {
            // Cria uma lista com os objetos atributo a serem pesquisados
            List atributosPesquisa = new ArrayList();
            Iterator it = estrutura.getEstruturaAtributoEttats().iterator();
            while (it.hasNext()) {
                atributosPesquisa.add(((EstruturaAtributoEttat) it.next())
                        .getAtributosAtb());
            }
            /*
             * Percorre o resultado e retira dele todas as Estruturas onde
             * dentro da cole��o de Estrutura Atributo n�o existam todos os
             * atributos de atributosPesquisa
             */
            Iterator itResults = results.iterator();
            while (itResults.hasNext()) {
                List atributosResultado = new ArrayList();
                Iterator itAtributosResultado = ((EstruturaEtt) itResults
                        .next()).getEstruturaAtributoEttats().iterator();
                while (itAtributosResultado.hasNext())
                    atributosResultado
                            .add(((EstruturaAtributoEttat) itAtributosResultado
                                    .next()).getAtributosAtb());
                if (!atributosResultado.containsAll(atributosPesquisa))
                    itResults.remove();
            }
        }

        /* se a estrutura possui fun��es */
        if (estrutura.getEstruturaFuncaoEttfs() != null) {
            // Cria uma lista com os objetos fun��o a serem pesquisados
            List funcoesPesquisa = new ArrayList();
            Iterator it = estrutura.getEstruturaFuncaoEttfs().iterator();
            while (it.hasNext()) {
                funcoesPesquisa.add(((EstruturaFuncaoEttf) it.next())
                        .getFuncaoFun());
            }
            /*
             * Percorre o resultado e retira dele todas as Estruturas onde
             * dentro da cole��o de Estrutura Fun��o n�o existam todas as
             * fun��es de funcoesPesquisa
             */
            Iterator itResults = results.iterator();
            while (itResults.hasNext()) {
                List funcoesResultado = new ArrayList();
                Iterator itFuncoesResultado = ((EstruturaEtt) itResults.next())
                        .getEstruturaFuncaoEttfs().iterator();
                while (itFuncoesResultado.hasNext())
                    funcoesResultado
                            .add(((EstruturaFuncaoEttf) itFuncoesResultado
                                    .next()).getFuncaoFun());
                if (!funcoesResultado.containsAll(funcoesPesquisa))
                    itResults.remove();
            }
        }

        /* se a estrutura possui fun��es de Acompanhamento */
        if (estrutura.getEstrutTpFuncAcmpEtttfas() != null) {
            // Cria uma lista com os objetos fun��o a serem pesquisados
            List funcoesAcompPesquisa = new ArrayList();
            Iterator it = estrutura.getEstrutTpFuncAcmpEtttfas().iterator();
            while (it.hasNext()) {
                funcoesAcompPesquisa.add(((EstrutTpFuncAcmpEtttfa) it.next())
                        .getTipoFuncAcompTpfa());
            }
            /*
             * Percorre o resultado e retira dele todas as Estruturas onde
             * dentro da cole��o de Estrutura-Tipo Fun��oAcompanhamento n�o
             * existam todas as fun��es de funcoesAcompPesquisa
             */
            Iterator itResults = results.iterator();
            while (itResults.hasNext()) {
                List funcoesResultado = new ArrayList();
                Iterator itFuncoesResultado = ((EstruturaEtt) itResults.next())
                        .getEstrutTpFuncAcmpEtttfas().iterator();
                while (itFuncoesResultado.hasNext())
                    funcoesResultado
                            .add(((EstrutTpFuncAcmpEtttfa) itFuncoesResultado
                                    .next()).getTipoFuncAcompTpfa());
                if (!funcoesResultado.containsAll(funcoesAcompPesquisa))
                    itResults.remove();
            }
        }
        
        /* se a estrutura possui dependencia de exibi��o com estrutura superior */
        if (estrutura.getSisAtributoSatbEttSuperior() != null) {
            // Cria uma lista com os objetos atributo a serem pesquisados
            List atributosPesquisa = new ArrayList();
            Iterator it = estrutura.getSisAtributoSatbEttSuperior().iterator();
            while (it.hasNext()) {
                atributosPesquisa.add(((SisAtributoSatb) it.next()));
            }
            /*
             * Percorre o resultado e retira dele todas as Estruturas onde
             * dentro da cole��o n�o existam todos os atributos de atributosPesquisa
             */
            Iterator itResults = results.iterator();
            while (itResults.hasNext()) {
                List atributosResultado = new ArrayList();
                Iterator itAtributosResultado = ((EstruturaEtt) itResults.next()).getSisAtributoSatbEttSuperior().iterator();
                while (itAtributosResultado.hasNext())
                    atributosResultado.add(((SisAtributoSatb) itAtributosResultado.next()));
                if (!atributosResultado.containsAll(atributosPesquisa))
                    itResults.remove();
            }
        }
        
		// inicializa as cole��es do resultado
		Iterator itResults = results.iterator();
		while(itResults.hasNext()){
			EstruturaEtt est = (EstruturaEtt) itResults.next();
			est.getEstruturaFuncaoEttfs().size();
			est.getEstruturaAtributoEttats().size();
			est.getEstrutTpFuncAcmpEtttfas().size();
		}
        return results;
    }


	/**
     * Recebe um array com os C�digos dos Atributos e retorna um Set com objetos
     * EstruturaAtributoEttat com os Atributos correspondentes a estes c�digos +
     * os atributos obrigat�rios
     * 
         * @param estrutura
         * @param atributos
         * @param incluirObrigatorios
         * @return Set objetos EstruturaEtt
         * @throws ECARException
     */
    public Set getSetAtributosEstrutura(EstruturaEtt estrutura,String atributos[], boolean incluirObrigatorios) throws ECARException {
        Set retorno = new HashSet();
        
        AtributoDao atributoDao = new AtributoDao(request);
        EstruturaAtributoDao estruturaAtributoDao = new EstruturaAtributoDao(request);
        
        List atributosDaEstrutura = new EstruturaAtributoDao(request).getAtributos(estrutura);
        
        if (atributos != null) {
            for (int i = 0; i < atributos.length; i++) {
                EstruturaAtributoEttat estruturaAtributo = new EstruturaAtributoEttat();                
                
                //Efetua a valida��o do
                
                
                //carrega o objeto atributo que representa o atributo que foi marcado na tela
                AtributosAtb atributo = (AtributosAtb) (atributoDao.buscar(AtributosAtb.class, Long.valueOf(atributos[i])));
                
                estruturaAtributo.setAtributosAtb(atributo);
                estruturaAtributo.setEstruturaEtt(estrutura); 
                
                if ( atributosDaEstrutura.contains(atributo)){                  
                    estruturaAtributo = (EstruturaAtributoEttat) estruturaAtributoDao.pesquisar(estruturaAtributo, null).iterator().next();
                } else {
                    estruturaAtributo.setLabelEstruturaEttat(atributo.getLabelPadraoAtb());
                    
                    // Robson - incluir valores do campo documentacao do atributo no estruturaAtributo
                    estruturaAtributo.setDocumentacaoEttat(atributo.getDocumentacaoAtb());
                }

                /* adiciona numa cole��o de EstruturaAtributo */
                retorno.add(estruturaAtributo);
            }
        }
        
        
        
        if (incluirObrigatorios)
            retorno.addAll(this.getSetAtributosEstruturaObrigatorios(estrutura));
        
        
        return retorno;
    }

    /**
     * Retorna um set com de objetos EstruturaAtributo com os atributos obrigat�rios
     * 
     * @param estrutura 
     * @return Set objetos EstruturaAtributoEtta
     * @throws ECARException
     */
    public Set getSetAtributosEstruturaObrigatorios(EstruturaEtt estrutura)
            throws ECARException {
    	
        Set retorno = new HashSet();
        
        Iterator it = new AtributoDao(request).getAtributosNaoOpcionais(estrutura.getEstruturaFuncaoEttfsEscolhidas()).iterator();
        
//      Iterator it = new AtributoDao(request).getAtributosNaoOpcionais().iterator();
                

        List atributosDaEstrutura = new EstruturaAtributoDao(request).getAtributos(estrutura);
        
        while (it.hasNext()) {
            EstruturaAtributoEttat estruturaAtributo = new EstruturaAtributoEttat();
            /* carrega o atributo */
            AtributosAtb atributo = (AtributosAtb) it.next();
            estruturaAtributo.setAtributosAtb(atributo);
            estruturaAtributo.setEstruturaEtt(estrutura);
            
            if ( atributosDaEstrutura.contains(atributo)){                  
                estruturaAtributo = (EstruturaAtributoEttat) new EstruturaAtributoDao(request).pesquisar(estruturaAtributo, null).iterator().next();
            } else {
                estruturaAtributo.setLabelEstruturaEttat(atributo.getLabelPadraoAtb());
                
                // Robson - incluir valores do campo documentacao do atributo no estruturaAtributo
                estruturaAtributo.setDocumentacaoEttat(atributo.getDocumentacaoAtb());
            }
            
            retorno.add(estruturaAtributo);
        }
        return retorno;

    }
    
    /**
     * Retorna um conjunto de estrutura_funcao com as fun��es que s�o obrigat�rias, isto �, ind_opcional="N"
     * @param estrutura
     * @return Set
     * @throws ECARException
     */
    public Set getSetFuncoesEstruturaObrigatorios(EstruturaEtt estrutura)
            throws ECARException {
        Set retorno = new HashSet();
        Iterator it = new FuncaoDao(request).getFuncoesNaoOpcionais().iterator();

        List funcoesDaEstrutura = new EstruturaFuncaoDao(request).getFuncoes(estrutura);
        
        while (it.hasNext()) {
            EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
            FuncaoFun funcao = (FuncaoFun) it.next();
            estruturaFuncao.setFuncaoFun(funcao);
            estruturaFuncao.setEstruturaEtt(estrutura);
            
            if ( funcoesDaEstrutura.contains(funcao)){                  
                estruturaFuncao = (EstruturaFuncaoEttf) new EstruturaFuncaoDao(request).pesquisar(estruturaFuncao, null).iterator().next();
            } else {
                estruturaFuncao.setLabelEttf(funcao.getLabelPadraoFun());
                // Robson - incluir valores do campo documentacao da fun��o no estruturaFuncao
                estruturaFuncao.setDocumentacaoEttf(funcao.getDocumentacaoFun());
                estruturaFuncao.setIndExibirHistoricoEttf("S");
            }
            if (estruturaFuncao.getIndExibirHistoricoEttf() == null){
            	estruturaFuncao.setIndExibirHistoricoEttf("N");
            }
            
            retorno.add(estruturaFuncao);
        }
        return retorno;

    }

    
    /**
     * Recebe um array com os C�digos das Funcoes e retorna um Set com objetos
     * EstrutTpFuncAcmpEtttfa com as Fun��es de acompamnhamento correspondentes
     * a estes c�digos + fun��es obirgat�rioas
     * 
     * 
	 * @author n/c
     * @param estrutura
     * @param funcoes
     *            array com os C�digos das Fun��es
     * @param incluirObrigatorios 
     * @return Set objetos EstruturaEtt
     * @throws ECARException
     */
    public Set getSetFuncoesEstrutura(EstruturaEtt estrutura, String funcoes[], boolean incluirObrigatorios)
            throws ECARException {
        Set retorno = new HashSet();
        Set escolhidas = new HashSet();
        
        List funcoesDaEstrutura = new EstruturaFuncaoDao(request).getFuncoes(estrutura);
        
        if (funcoes != null) {
            for (int i = 0; i < funcoes.length; i++) {
                EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
                /* carrega a fun��o */
                FuncaoFun funcao = (FuncaoFun) (new FuncaoDao(request).buscar(
                        FuncaoFun.class, Long.valueOf(funcoes[i])));

                estruturaFuncao.setFuncaoFun(funcao);
                estruturaFuncao.setEstruturaEtt(estrutura);
                	
                if(funcoesDaEstrutura.contains(funcao)) {
                    estruturaFuncao = (EstruturaFuncaoEttf) new EstruturaFuncaoDao(request).pesquisar(estruturaFuncao, null).iterator().next();
                }
                else {
                	estruturaFuncao.setLabelEttf(funcao.getLabelPadraoFun());
//                    // Robson - incluir valores do campo documentacao da fun��o no estruturaFuncao
                	estruturaFuncao.setDocumentacaoEttf(funcao.getDocumentacaoFun());                 
//                    // seta alguns valores default para atributos.
                	estruturaFuncao.setIndPodeBloquearEttf("N");
                	estruturaFuncao.setIndListagemImpressaResEttf("N");
                	estruturaFuncao.setIndListagemImpressCompEttf("S");
                	estruturaFuncao.setIndRevisaoEttf("S");
                	estruturaFuncao.setIndExibirHistoricoEttf("S");
                }
                
                /* adiciona numa cole��o de EstruturaFuncao */
                retorno.add(estruturaFuncao);
                escolhidas.add(estruturaFuncao);
            }
        }
        
        estrutura.setEstruturaFuncaoEttfsEscolhidas(escolhidas);
        
        if(incluirObrigatorios)
            retorno.addAll(getSetFuncoesEstruturaObrigatorios(estrutura));
        
        return retorno;

    }

    /**
     * Recebe um array com os C�digos das Funcoes de Acompanhamento e retorna um
     * Set com objetos EstrutTpFuncAcmpEtttfa com as Fun��es correspondentes a
     * estes c�digos
     * 
     * @param estrutura
     * @param funcoesAcompanhamento
     * @return Set objetos EstruturaEtt
     * @throws ECARException
     */
    public Set getSetFuncoesAcompEstrutura(EstruturaEtt estrutura,
            String funcoesAcompanhamento[]) throws ECARException {
        Set retorno = new HashSet();
        
        List funcoesAcompanhamentoDaEstrutura = new EstruturaTipoFuncAcompDao(request).getTipoFuncAcomp(estrutura);
        
        if (funcoesAcompanhamento != null) {
            for (int i = 0; i < funcoesAcompanhamento.length; i++) {
                EstrutTpFuncAcmpEtttfa estruturaFuncaoAcomp = new EstrutTpFuncAcmpEtttfa();
                /* carrega a fun��o */
                TipoFuncAcompTpfa funcaoAcomp = (TipoFuncAcompTpfa) (new TipoFuncAcompDao(request)
                        .buscar(TipoFuncAcompTpfa.class, Long
                                .valueOf(funcoesAcompanhamento[i])));                
                estruturaFuncaoAcomp.setTipoFuncAcompTpfa(funcaoAcomp);
                estruturaFuncaoAcomp.setEstruturaEtt(estrutura);
                
                if(funcoesAcompanhamentoDaEstrutura.contains(funcaoAcomp))
                    estruturaFuncaoAcomp = (EstrutTpFuncAcmpEtttfa) new EstruturaTipoFuncAcompDao(request).pesquisar(estruturaFuncaoAcomp, null).iterator().next();                
                /* adiciona numa cole��o de EstruturaFuncao */
                retorno.add(estruturaFuncaoAcomp);

            }
        }
        return retorno;

    }

    /**
     * Recebe uma estrutura e retorna em qual n�vel ela est�
     * 
     * @param estrutura
     * @return int N�vel em que se encontra a estrutura
     * @throws ECARException
     */
    public int getNivel(EstruturaEtt estrutura) throws ECARException {
        int nivel = 1;

        while (estrutura.getEstruturaEtt() != null) {
            estrutura = estrutura.getEstruturaEtt();
            nivel++;

        }

        return nivel;
    }

    /**
     * Recebe uma estrutura e devolve todos os atributos permitidos que podem
     * ser selecionados no checklist de atributos
     * 
     * @param estruturaPai
     * @param funcaoFun
     * @return Conjunto de atributos ativos, <br>
     *         opcionais, <br>
     *         nao exclusivos, <br>
     * @throws ECARException
     *  
     */
    public List getAtributosPermitidos(EstruturaEtt estruturaPai, FuncaoFun funcaoFun)
            throws ECARException {
        List retorno = new ArrayList();
        List lAtributos;
        int nivel;

        AtributosAtb atributo = new AtributosAtb();

        atributo.setIndAtivoAtb("S");
        atributo.setIndOpcionalAtb("S");
        atributo.setFuncaoFun(funcaoFun);

        lAtributos = new AtributoDao(request).pesquisar(atributo, new String[] { "nomeAtb",
                AtributoDao.ORDEM_ASC });

        Iterator it = lAtributos.iterator();
        while (it.hasNext()) {
            atributo = (AtributosAtb) it.next();
            if (atributo.getIndExclusivoEstruturaAtb() != null
                    && "N".equals(atributo.getIndExclusivoEstruturaAtb())) {
                retorno.add(atributo);
            } else {
                //Se o Atributo n�o foi utilzado em nenhuma estrutura, � um
                // atributo permitido
                if (atributo.getEstruturaAtributoEttats() != null) {
                    if (atributo.getEstruturaAtributoEttats().isEmpty())
                        retorno.add(atributo);
                    // Sen�o precisa verificar em qual n�vel este atributo foi
                    // usado
                    else {
                        EstruturaAtributoEttat estruturaAtributo = (EstruturaAtributoEttat) (atributo
                                .getEstruturaAtributoEttats().iterator().next());
                        /*
                         * Se o n�vel da estrutura na qual foi usada o atributo
                         * for igual ao n�vel do Pai + 1 (n�vel da nova
                         * estrutura) ent�o � um atributo permitido
                         */
                        nivel = (estruturaPai == null) ? 1 : this
                                .getNivel(estruturaPai) + 1;

                        if (this.getNivel(estruturaAtributo.getEstruturaEtt()) == nivel)
                            retorno.add(atributo);
                    }
                } else {

                    retorno.add(atributo);

                }
            }
        }

        return retorno;
    }

    /**
     * Recebe uma estrutura e devolve todos as Fun��es
     * ser selecionados no checklist de afun��es
     * 
     * @param estruturaPai
     * @return Conjunto de fun��es ativas, <br>
     *         opcionais, <br>
     *         nao exclusivos, <br>
     * @throws ECARException
     *  
     */
    public List getFuncoesPermitidas(EstruturaEtt estruturaPai)
            throws ECARException {
        List retorno = new ArrayList();
        List lFuncoes;
        int nivel;

        FuncaoFun funcao = new FuncaoFun();

        funcao.setIndAtivoFun("S");
        funcao.setIndOpcionalFun("S");

        lFuncoes = new FuncaoDao(request).pesquisar(funcao, new String[] { "nomeFun",
                AtributoDao.ORDEM_ASC });

        Iterator it = lFuncoes.iterator();
        while (it.hasNext()) {
            funcao = (FuncaoFun) it.next();
            if (funcao.getIndExclusivoEstruturaFun() != null
                    && !"S".equals(funcao.getIndExclusivoEstruturaFun())) {
                retorno.add(funcao);
            } else {
                //Se a Fun��o n�o foi utilzado em nenhuma estrutura, � uma
                // func��o permitida
                if (funcao.getEstruturaFuncaoEttfs() != null) {
                    if (funcao.getEstruturaFuncaoEttfs().isEmpty())
                        retorno.add(funcao);
                    // Sen�o precisa verificar em qual n�vel esta Fun��o foi
                    // usado
                    else {
                        EstruturaFuncaoEttf estruturaFuncao = (EstruturaFuncaoEttf) (funcao
                                .getEstruturaFuncaoEttfs().iterator().next());
                        /*
                         * Se o n�vel da estrutura na qual foi usada a fun��o
                         * for igual ao n�vel do Pai + 1 (n�vel da nova
                         * estrutura) ent�o � um atributo permitido
                         */
                        nivel = (estruturaPai == null) ? 1 : this
                                .getNivel(estruturaPai) + 1;

                        if (this.getNivel(estruturaFuncao.getEstruturaEtt()) == nivel)
                            retorno.add(funcao);
                    }
                } else {

                    retorno.add(funcao);

                }
            }
        }

        return retorno;
    }

    
    /**
     * Retorna um list com identifica��es de todas as Fun��es de uma Estrutura
     * 
     * @param estrutura
     * @return List de Long
     */
    public List getFuncoesById(EstruturaEtt estrutura) {
        List lFuncoes = new ArrayList();
        if (estrutura.getEstruturaFuncaoEttfs() != null) {
            estrutura.getEstruturaFuncaoEttfs().size(); // Faz isso para
            Iterator it = estrutura.getEstruturaFuncaoEttfs().iterator();
            while (it.hasNext()) {
                EstruturaFuncaoEttf estruturaFuncao = (EstruturaFuncaoEttf) it
                        .next();
                lFuncoes.add(estruturaFuncao.getFuncaoFun().getCodFun());
            }
        }
        return lFuncoes;
    }

    /**
     * Retorna um list com identifica��es de todos as Atributos de uma Estrutura
     * 
     * @param estrutura
     * @param funcaoFun
     * @return List de Long
     */
    public List getAtributosById(EstruturaEtt estrutura, FuncaoFun funcaoFun) {
        List lAtributos = new ArrayList();
        if (estrutura.getEstruturaAtributoEttats() != null) {
            estrutura.getEstruturaAtributoEttats().size(); // Faz isso para
            Iterator it = estrutura.getEstruturaAtributoEttats().iterator();
            while (it.hasNext()) {
                EstruturaAtributoEttat estruturaAtributo = (EstruturaAtributoEttat) it.next();
                if (estruturaAtributo.getAtributosAtb().getFuncaoFun().equals(funcaoFun)){
                	lAtributos.add(estruturaAtributo.getAtributosAtb().getCodAtb());
                }
            }
        }
        return lAtributos;
    }

    /**
     * Retorna um list com identifica��es de todos os Tipos de Fun��o de
     * Acompanhamento de uma Estrutura
     * 
     * @param estrutura
     * @return List de Long
     */
    public List getFuncoesAcompanhamentoById(EstruturaEtt estrutura) {
        List lFuncoesAcompanhamento = new ArrayList();
        if (estrutura.getEstrutTpFuncAcmpEtttfas() != null) {
            estrutura.getEstrutTpFuncAcmpEtttfas().size();
            Iterator it = estrutura.getEstrutTpFuncAcmpEtttfas().iterator();
            while (it.hasNext()) {
                EstrutTpFuncAcmpEtttfa estruturaTipoFuncAcomp = (EstrutTpFuncAcmpEtttfa) it
                        .next();
                lFuncoesAcompanhamento.add(estruturaTipoFuncAcomp
                        .getTipoFuncAcompTpfa().getCodTpfa());
            }
        }
        return lFuncoesAcompanhamento;
    }
    
        /**
     * Exclui todos os Registros de Atributos e Fun��e relacionados a uma
     * estrutura
     * 
     * @param estrutura
     * @throws ECARException
     */
    public void deleteFilhos(EstruturaEtt estrutura) throws ECARException {
        if (estrutura.getEstruturaAtributoEttats() != null) {
            Iterator itAtb = estrutura.getEstruturaAtributoEttats().iterator();
            while (itAtb.hasNext()) {
                EstruturaAtributoEttat estruturaAtributo = (EstruturaAtributoEttat) itAtb
                        .next();
                this.excluir(estruturaAtributo);
            }
        }
        if (estrutura.getEstruturaFuncaoEttfs() != null) {
            Iterator itFun = estrutura.getEstruturaFuncaoEttfs().iterator();
            while (itFun.hasNext()) {
                EstruturaFuncaoEttf estruturaFuncao = (EstruturaFuncaoEttf) itFun
                        .next();
                this.excluir(estruturaFuncao);
            }
        }
        if (estrutura.getEstrutTpFuncAcmpEtttfas() != null) {
            Iterator itFun = estrutura.getEstrutTpFuncAcmpEtttfas().iterator();
            while (itFun.hasNext()) {
                EstrutTpFuncAcmpEtttfa estruturaFuncaoAcomp = (EstrutTpFuncAcmpEtttfa) itFun
                        .next();
                this.excluir(estruturaFuncaoAcomp);
            }
        }
    }


    /**
     * A partir de uma estrutura, devolve uma lista com a pr�pria estrutura e
     * mais seus ascendentes diretos ( pai, pai do pai, etc.)
     * 
     * @param estrutura
     * @return Lista de EstruturaEtt
     */
    public List getAscendentes(EstruturaEtt estrutura) {
        List retorno = new ArrayList();
        retorno.add(estrutura);
        while (estrutura.getEstruturaEtt() != null) {
            estrutura = estrutura.getEstruturaEtt();
            retorno.add(estrutura);
        }
        Collections.reverse(retorno);
        return retorno;
    }

    /**
     * A partir de uma estrutura, devolve uma lista com os seus descendentes
     * diretos ( filho, neto, etc.)
     * 
     * @param estrutura
     * @return Lista de EstruturaEtt
     */
    public List getDescendentes(EstruturaEtt estrutura) {
        List retorno = new ArrayList();
        if (estrutura.getEstruturaEtts() != null && estrutura.getEstruturaEtts().size() > 0) {
            retorno.addAll(estrutura.getEstruturaEtts());
            Iterator it = estrutura.getEstruturaEtts().iterator();
            
            while (it.hasNext()) {
                EstruturaEtt estruturaFilha = (EstruturaEtt) it.next();
                retorno.addAll(this.getDescendentes(estruturaFilha));
            }
        }
        return retorno;
    }

    
    /**
     * Retorna um conjunto com as estruturas principais, isto �, estruturas de nivel 1 (sem pai)
     * Teoricamente pode retornar uma lista com v�rias estruturas, mas na pratica deve retornar apenas uma estrutura
     * @return List
     * @throws ECARException
     */
    public List getEstruturaPrincipal() throws ECARException {
        try {
        	// Query alterada devido ao BUG 894 e 2531
//            List list = session.createQuery("select e from EstruturaEtt as e where e.indAtivoEtt='S' and e.estruturaEtt is null order by e.seqApresentacaoEtt, e.nomeEtt").list();
            
        	List list;
			String cacheId = "estruturaPrincipal";
			list = (List) CacheManagerImpl.get(cacheId);
			if (list == null) {
				list = session.createQuery("select e from EstruturaEtt as e where e.indAtivoEtt='S' and e.estruturaEtt is null order by e.seqApresentacaoEtt, e.nomeEtt").list();
				CacheManagerImpl.add(cacheId, list);
			}

            return list;    
        } catch (HibernateException e) {
            this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
        }
    }

    /**
     * Retorna uma lista de atributos que ser�o utilizados para montar as
     * colunas na tela 6.b.1 - Rela��o para acesso na estrutura
     * Pseudo-codigo: Retorna os atributos de estrutura_atributo e funcao_acomp com
     * ind_listagem_tela=S ordenados por seq_apres_listagem_tela_ettat
     * 
     * @param estrutura 
     * @return List ObjetoEstrutura
     * @throws ECARException
     */
    public List getAtributosAcessoEstrutura(EstruturaEtt estrutura) throws ECARException {
        List retorno = new ArrayList();
        FuncaoDao funcaoDao = new FuncaoDao(request);
        try {
        	
        	if (!session.contains(estrutura)) {
        		 session.load(estrutura, new Long(estrutura.getCodEtt()));	
        	}
        	
        	
            retorno.addAll(session.createFilter(estrutura.getEstruturaAtributoEttats(),
                    "where this.indListagemTelaEttat = 'S' and this.atributosAtb.funcaoFun.codFun = " + funcaoDao.getCodFuncaoDadosGerais()).list());
            retorno.addAll(session.createFilter(estrutura.getEstrutTpFuncAcmpEtttfas(),
                    "where this.indListagemTelaEtttfa = 'S'").list());

            Collections.sort(retorno,
    	            new Comparator() {
    	        		public int compare(Object o1, Object o2) {
    	        		    return ((ObjetoEstrutura)o1).iGetSequenciaColunaEmListagem().intValue() - ((ObjetoEstrutura)o2).iGetSequenciaColunaEmListagem().intValue();
    	        		}
    	    		} );
            
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        return retorno;
    }
    
    /**
     * Retorna uma lista de atributos que ser�o utilizados para montar as �rvores
     * 
     * @param estrutura 
     * @return List ObjetoEstrutura
     * @throws ECARException
     */
    public List getAtributosAcessoEstruturaArvore(EstruturaEtt estrutura) throws ECARException {
        List retorno = new ArrayList();
        FuncaoDao funcaoDao = new FuncaoDao(request);
        try {
        	
        	if (!session.contains(estrutura)) {
        		 session.load(estrutura, new Long(estrutura.getCodEtt()));	
        	}
        	
            retorno.addAll(session.createFilter(estrutura.getEstruturaAtributoEttats(),
                    "where this.indListagemArvoreEttat = 'S' and this.atributosAtb.funcaoFun.codFun = " + funcaoDao.getCodFuncaoDadosGerais()).list());

            Collections.sort(retorno,
    	            new Comparator() {
    	        		public int compare(Object o1, Object o2) {
    	        		    return ((ObjetoEstrutura)o1).iGetSequenciaColunaEmListagem().intValue() - ((ObjetoEstrutura)o2).iGetSequenciaColunaEmListagem().intValue();
    	        		}
    	    		} );
            
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        return retorno;
    }
    
    /**
     * Retorna uma lista de atributos que ser�o utilizados para montar as
     * colunas - Rela��o para acesso na estrutura
     * Pseudo-codigo: Retorna os atributos de estrutura_atributo
     * ind_listagem_tela=S ordenados por seq_apres_listagem_tela_ettat
     * 
     * @param estrutura 
     * @param funcaoFun 
     * @return List ObjetoEstrutura
     * @throws ECARException
     */
    public List getAtributosAcessoEstrutura(EstruturaEtt estrutura, FuncaoFun funcaoFun) throws ECARException {
        List retorno = new ArrayList();
        try {
        	
        	if (!session.contains(estrutura)) {
        		 session.load(estrutura, new Long(estrutura.getCodEtt()));	
        	}
        	
            retorno.addAll(session.createFilter(estrutura.getEstruturaAtributoEttats(),
                    "where this.indListagemTelaEttat = 'S' and this.atributosAtb.funcaoFun.codFun = " + funcaoFun.getCodFun().toString()).list());

            Collections.sort(retorno,
    	            new Comparator() {
    	        		public int compare(Object o1, Object o2) {
    	        		    return ((ObjetoEstrutura)o1).iGetSequenciaColunaEmListagem().intValue() - ((ObjetoEstrutura)o2).iGetSequenciaColunaEmListagem().intValue();
    	        		}
    	    		} );
            
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        return retorno;
    }
 
    /**
     * Devolve uma lista de Atributos e Funcoes de Acompanhamento da Estrutura.
     * Essa lista cont�m objetos estruturaAtributo e estrutTipoFuncAcmp ordenados pela ordem do campo na tela.
     * O acesso aos objetos dessa lista se d� pela interface ObjetoEstrutura que normaliza os m�todos de acesso
     * @param estrutura
     * @return lista de objetos estruturaAtributo e estrutTipoFuncAcmp ordenados pela ordem do campo na tela.
     * @throws ECARException
     */
    public List getAtributosEstruturaDadosGerais(EstruturaEtt estrutura) throws ECARException {
        List retorno = new ArrayList();
        List<EstrutTpFuncAcmpEtttfa> listaFuncaoAcompanhamentoEstrutura = new ArrayList<EstrutTpFuncAcmpEtttfa>();
        
        try {
        	
			FuncaoDao funDao = new FuncaoDao(null);
			FuncaoFun funcao = funDao.getFuncaoDadosGerais();

			//Obtem as estruturaAtributosEtt ordenados 
            retorno.addAll(this.getAtributosEstrutura(estrutura, funcao));
            
            
            listaFuncaoAcompanhamentoEstrutura.addAll(estrutura.getEstrutTpFuncAcmpEtttfas());
           
            retorno.addAll(listaFuncaoAcompanhamentoEstrutura);
            
            //Ordena os ObjetoEstrutura (atributos na estrutura e fun��es de acompanhamentos na estrutura)
            Collections.sort(retorno,
    	            new Comparator() {

						public int compare(Object o1,
								Object o2) {
							return (((ObjetoEstrutura)o1).iGetSequenciaCampoEmTela().intValue() - ((ObjetoEstrutura)o2).iGetSequenciaCampoEmTela().intValue());
						}
    	    		} );
            
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        return retorno;
    }
        
    
    /**
     * Devolve uma lista de Atributos e Funcoes de Acompanhamento da Estrutura.
     * Essa lista cont�m objetos estruturaAtributo e estrutTipoFuncAcmp ordenados pela ordem do campo na tela.
     * O acesso aos objetos dessa lista se d� pela interface ObjetoEstrutura que normaliza os m�todos de acesso
     * @param estrutura
     * @param tipoAcompanhamentoTa
     * @return lista de objetos estruturaAtributo e estrutTipoFuncAcmp ordenados pela ordem do campo na tela.
     * @throws ECARException
     */
    public List getAtributosEstruturaDadosGeraisEhFiltro(EstruturaEtt estrutura, TipoAcompanhamentoTa tipoAcompanhamentoTa) throws ECARException {
        List retorno = new ArrayList();
        
        try {
        	Set set = estrutura.getEstruturaAtributoEttats();
        	Iterator itEstrutura = estrutura.getEstruturaAtributoEttats().iterator();
        	while (itEstrutura.hasNext()){
        		EstruturaAtributoEttat estruturaAtributoEttat = (EstruturaAtributoEttat) itEstrutura.next();
        		//Com a altera��o de EstruturaAtributo para guardar campos para qualquer fun��o,
            	//ent�o � necess�rio filtrar para recuperar somente os campos que est�o configurados
            	//para a fun��o de dados gerais
        		if (estruturaAtributoEttat.getAtributosAtb().getFuncaoFun().getNomeFun().equals(FuncaoDao.NOME_FUNCAO_DADOS_GERAIS)){
        			EstAtribTipoAcompEata estAtribTipoAcompEata = new EstAtribTipoAcompEata();
            		estAtribTipoAcompEata.setEstruturaAtributoEttat(estruturaAtributoEttat);
            		estAtribTipoAcompEata.setTipoAcompanhamentoTa(tipoAcompanhamentoTa);
            		estAtribTipoAcompEata.setFiltroEata("S");
            		

            		//Altera��o na forma de verificar se existe EstAtribTipoAcompEata - DATASUS:05/03/2012
            		if (this.existeEstAtribTipoAcompEata(estAtribTipoAcompEata)) {
            			retorno.add(estruturaAtributoEttat);
            		}
            		/*if (this.pesquisar(estAtribTipoAcompEata, null).size() > 0) {
            			retorno.add(estruturaAtributoEttat);
            		}*/
        		}	
        	}
            //retorno.addAll(estrutura.getEstruturaAtributoEttats());
            //retorno.addAll(estrutura.getEstrutTpFuncAcmpEtttfas());

            Collections.sort(retorno,
    	            new Comparator() {
    	        		public int compare(Object o1, Object o2) {
    	        		    return ((ObjetoEstrutura)o1).iGetSequenciaCampoEmTela().intValue() - ((ObjetoEstrutura)o2).iGetSequenciaCampoEmTela().intValue();
    	        		}
    	    		} );
            
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        return retorno;
    }
    
    /***
     * M�todo para verificar a existencia de EstAtribTipoAcompEata
     * @param estAtribTipoAcompEata
     * @param object
     * @return
     * @throws ECARException 
     */
    private Boolean existeEstAtribTipoAcompEata(EstAtribTipoAcompEata estAtribTipoAcompEata) throws ECARException {
    	
    	try {
    		
	    	String select = "select e from EstAtribTipoAcompEata as e " +
	    			" where e.filtroEata='S' and" +
	        		" e.estruturaAtributoEttat.comp_id.codEtt = :codEtt and " +
	        		" e.estruturaAtributoEttat.comp_id.codAtb = :codAtb and " + 
	    			" e.tipoAcompanhamentoTa.codTa = :codTa";
	    			
	    	Query q = this.session.createQuery(select);
	    	
	    	q.setLong("codEtt", estAtribTipoAcompEata.getEstruturaAtributoEttat().getComp_id().getCodEtt());
	    	q.setLong("codAtb", estAtribTipoAcompEata.getEstruturaAtributoEttat().getComp_id().getCodAtb());
	    	q.setLong("codTa", estAtribTipoAcompEata.getTipoAcompanhamentoTa().getCodTa());
	    	
	    	
	    	if(q.list() != null){
	    		return !q.list().isEmpty();
	    	}
    	}
    	catch(HibernateException e) {
            this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
    	}
    	
		return false;
	}

	/**
     *
     * @param estrutura
     * @param tipoAcompanhamentoTa
     * @return
     * @throws ECARException
     */
    public List getAtributosLivresEstruturaDadosGeraisEhFiltro(EstruturaEtt estrutura, TipoAcompanhamentoTa tipoAcompanhamentoTa) throws ECARException {
        List retorno = new ArrayList();
        
        try {
        	
        	Iterator itEstrutura = estrutura.getEstruturaAtributoEttats().iterator();
        	
        	while (itEstrutura.hasNext()){
        		
        		EstruturaAtributoEttat estruturaAtributoEttat = (EstruturaAtributoEttat) itEstrutura.next();
        		//Com a altera��o de EstruturaAtributo para guardar campos para qualquer fun��o,
            	//ent�o � necess�rio filtrar para recuperar somente os campos que est�o configurados
            	//para a fun��o de dados gerais
        		if (estruturaAtributoEttat.getAtributosAtb().getFuncaoFun().getNomeFun().equals(FuncaoDao.NOME_FUNCAO_DADOS_GERAIS)){
        			if (estruturaAtributoEttat.iGetGrupoAtributosLivres() != null){
            			EstAtribTipoAcompEata estAtribTipoAcompEata = new EstAtribTipoAcompEata();
                		estAtribTipoAcompEata.setEstruturaAtributoEttat(estruturaAtributoEttat);
                		estAtribTipoAcompEata.setTipoAcompanhamentoTa(tipoAcompanhamentoTa);
                		estAtribTipoAcompEata.setFiltroEata("S");
                		
                		if (this.pesquisar(estAtribTipoAcompEata, null).size() > 0) {
                			retorno.add(estruturaAtributoEttat);
                		}
            		}
        		}
        	}

            Collections.sort(retorno,
    	            new Comparator() {
    	        		public int compare(Object o1, Object o2) {
    	        		    return ((ObjetoEstrutura)o1).iGetSequenciaCampoEmTela().intValue() - ((ObjetoEstrutura)o2).iGetSequenciaCampoEmTela().intValue();
    	        		}
    	    		} );
            
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        return retorno;
    }

    

    
    /**
     *
     * @param estrutura
     * @return
     * @throws ECARException
     */
    public List getAtributosEstruturaRevisao(EstruturaEtt estrutura) throws ECARException {
        List retorno = new ArrayList();
      
        try {
        	retorno.addAll(session.createFilter(estrutura.getEstruturaAtributoEttats(),
        			"where this.indRevisaoEttat = 'S'").list());
            retorno.addAll(session.createFilter(estrutura.getEstrutTpFuncAcmpEtttfas(), 
            		"where this.indRevisao = 'S'").list());
        	Collections.sort(retorno, new Comparator() {
        		public int compare(Object o1, Object o2) {
        			return ((ObjetoEstrutura)o1).iGetSequenciaCampoEmTela().intValue() - ((ObjetoEstrutura)o2).iGetSequenciaCampoEmTela().intValue();
        			}
        		} );   
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        return retorno;
    }
    
    /**
     * Devolve uma lista ordenada de ObjetoEstrutura, utilizando como base o tipo do relat�rio (C, R ou L).
     * � utilizada a mesma ordena��o dos campos na tela.
     * @param estrutura
     * @param tipoRelatorio C - Completo R - Resumido L - listagem/rela��o
     * @return List ObjetoEstrutura ordenados por igetSequenciaCampoEmTela
     * @throws ECARException
     */
    public List getAtributosEstruturaRelatorio(EstruturaEtt estrutura, String tipoRelatorio) throws ECARException {
        List retorno = new ArrayList();

        try {
            if("C".equals(tipoRelatorio)){ //COMPLETO
                retorno.addAll(session.createFilter(estrutura.getEstruturaAtributoEttats(),
                        "where this.indListagemImpressCompEtta = 'S'").list());
                retorno.addAll(session.createFilter(estrutura.getEstrutTpFuncAcmpEtttfas(),
                        "where this.indListagemImprCompEtttfa = 'S'").list());
            } else if ("R".equals(tipoRelatorio)) { //RESUMO
                retorno.addAll(session.createFilter(estrutura.getEstruturaAtributoEttats(),
                        "where this.indListagemImpressaResEtta = 'S'").list());
                retorno.addAll(session.createFilter(estrutura.getEstrutTpFuncAcmpEtttfas(),
                        "where this.indListagemImprResEtttfa = 'S'").list());    
            } else if ("L".equals(tipoRelatorio)){ // RELA��O
            	retorno.addAll(session.createFilter(estrutura.getEstruturaAtributoEttats(),
            			"where this.indRelacaoImpressaEttat = 'S'").list());
            }
            Collections.sort(retorno,
    	            new Comparator() {
    	        		public int compare(Object o1, Object o2) {
    	        		    return ((ObjetoEstrutura)o1).iGetSequenciaCampoEmTela().intValue() - ((ObjetoEstrutura)o2).iGetSequenciaCampoEmTela().intValue();
    	        		}
    	    		} );
            
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        return retorno;
    }
    
    /**
     * Retorna as estruturas filhas da estrutura de um item
     * @param item 
     * @return List de estruturas
     * @throws ECARException
     */
    public List getSetEstruturasItem(ItemEstruturaIett item) throws ECARException {
    	try {
	    	// M�todo alterado devido ao BUG 894 e 2531
    		
	    	String select = "select e from EstruturaEtt as e " +
	    			" where e.indAtivoEtt='S' and" +
	        		" e.estruturaEtt.codEtt = :codEtt" + 
	        		" order by e.seqApresentacaoEtt, e.nomeEtt";
	    	Query q = this.session.createQuery(select);
	    	
	    	q.setLong("codEtt", item.getEstruturaEtt().getCodEtt().longValue());
	    	
			return q.list(); 
    	}
    	catch(HibernateException e) {
            this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
    	}
    }
    
    
    /**
     * Retorna as estruturas filhas da estrutura passada como par�metro
     * @param estruturaEtt 
     * @return List de estruturas
     * @throws ECARException
     */
    public List getEstruturas(EstruturaEtt estruturaEtt) throws ECARException {
    	try {
   		
	    	String select = "select e from EstruturaEtt as e " +
	    			" where e.indAtivoEtt='S' and" +
	        		" e.estruturaEtt.codEtt = :codEtt" + 
	        		" order by e.seqApresentacaoEtt, e.nomeEtt";
	    	Query q = this.session.createQuery(select);
	    	
	    	q.setLong("codEtt", estruturaEtt.getCodEtt().longValue());
	    	
			return q.list(); 
    	}
    	catch(HibernateException e) {
            this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
    	}
    }
    
    /**
     * Retorna as estruturas filhas da estrutura passada como par�metro
     * @param estruturaEtt 
     * @return List de estruturas
     * @throws ECARException
     */
    public List getEstruturasReais(EstruturaEtt estruturaEtt) throws ECARException {
    	try {
   		
	    	String select = "select e from EstruturaEtt as e " +
	    			" where e.indAtivoEtt='S' and" +
	        		" e.estruturaEtt.codEtt = :codEtt and" +
	        		" e.virtual = :virtual" +
	        		" order by e.seqApresentacaoEtt, e.nomeEtt";
	    	Query q = this.session.createQuery(select);
	    	
	    	if (estruturaEtt!=null && estruturaEtt.getCodEtt()!=null) {
	    		q.setLong("codEtt", estruturaEtt.getCodEtt().longValue());
	    		q.setBoolean("virtual", Boolean.FALSE);
	    		return q.list();
	    	} else {
	    		return new ArrayList<EstruturaEtt>();
	    	}
			 
    	}
    	catch(HibernateException e) {
            this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
    	}
    }
    
    /**
     * M�todo retorna o label utilizado pelo atributo �rg�o Respons�vel 1 na estrutura de maior n�vel hier�qrquivo dentre
     * as que possuam alguma fun��o de acompanhamento, Caso essa fun��o n�o possua label definido para tal atributo retorna
     * um label padr�o (�rg�o Respons�vel)
     * @return
     * @throws ECARException
     */
    public String getLabelPadraoOrgaoResponsavel() throws ECARException{
        try{
            List estruturas = this.listar(EstruturaEtt.class, null);
            if(estruturas == null || estruturas.size() == 0)
            	return "�rg�o Respons�vel";
            	
            int nivel = 0;
            EstruturaEtt maiorEstruturaComFuncao = null;
            Iterator it = estruturas.iterator();
            while(it.hasNext()){
                EstruturaEtt estrutura = (EstruturaEtt) it.next();
                if(estrutura.getEstrutTpFuncAcmpEtttfas().size() > 0){
                    int nivelEstrut = this.getNivel(estrutura); 
                    if(nivel == 0 || nivelEstrut < nivel){
                        nivel = nivelEstrut;
                        maiorEstruturaComFuncao = estrutura;
                    }
                }
            }
            Collection atributo = this.getSession().createFilter(maiorEstruturaComFuncao.getEstruturaAtributoEttats(),
                " where this.atributosAtb.nomeAtb = 'orgaoOrgByCodOrgaoResponsavel1Iett'").list();
            if(atributo == null || atributo.size() == 0)
                return "�rg�o Respons�vel";
            else
                return ( (EstruturaAtributoEttat) atributo.iterator().next()).getLabelEstruturaEttat();
            
        } catch(HibernateException e){
            this.logger.error(e);
            throw new ECARException(e);
        }
        
    }
    
    
    /**
	 * Monta lista de estruturas a partir das principais, 
	 * 		incluindo tamb�m os	descendentes em ordem de n�veis
	 * @return
	 * @throws ECARException
	 */
//	public List getEstruturas() throws ECARException {
//		List lista = new ArrayList();
//		
//		List estruturasPrincipais = this.getEstruturaPrincipal();
//		Iterator it = estruturasPrincipais.iterator();
//		
//		while(it.hasNext()){
//			EstruturaEtt principal = (EstruturaEtt) it.next();
//			
//			/* seleciona estruturas descendentes */
//			lista.add(principal);
//			lista.addAll(this.getDescendentes(principal));
//			//Iterator itLista = lista.iterator();
//		}
//		
//		return lista;
//	}
	
    /**
     * Verificar se um determinado atributo existe em um n�vel da estrutura atrav�s do nome do atributo (atributo.nomeAtb)
     * @param estrutura 
     * @param nomeAtb
     * @return true existe, false n�o existe
     * @throws ECARException
     */
    public boolean verificaExistenciaAtributoNaEstruturaByNomeAtb(EstruturaEtt estrutura, String nomeAtb) throws ECARException {
	    AtributosAtb atributo = new AtributosAtb();
	    atributo.setNomeAtb(nomeAtb);
	    
	    List atb = super.pesquisar(atributo, null);
	    if(atb.size() > 0){
	        atributo = (AtributosAtb) atb.get(0);
	        EstruturaAtributoEttat estruturaAtributo = new EstruturaAtributoEttat();
	        estruturaAtributo.setAtributosAtb(atributo);
	        estruturaAtributo.setEstruturaEtt(estrutura);
	        List estAtb = super.pesquisar(estruturaAtributo, null);
	        if(estAtb.size() > 0) {
	            return true;
	        }
	    }
        return false;
    }
    
    
    /**
     * Retorna uma lista de estruturas que s�o etapas da estrutura pai.
     * @param estruturaPai
     * @author aleixo
     * @version 0.1, 22/03/2007
     * @return List
     * @throws ECARException
     */
    public List getEstruturasEtapas(EstruturaEtt estruturaPai) throws ECARException{
    	try {
	    	String select = "select e from EstruturaEtt as e " +
	    			" where e.indAtivoEtt='S' and" +
	    			" e.indEtapaNivelSuperiorEtt='S' and" +
	        		" e.estruturaEtt.codEtt = :codEtt" + 
	        		" order by e.seqApresentacaoEtt, e.nomeEtt";
	    	Query q = this.session.createQuery(select);
	    	
	    	q.setLong("codEtt", estruturaPai.getCodEtt().longValue());
	    	
	    	List retorno = q.list();
	    	
	    	if(retorno != null)
	    		return retorno;
	    	
			return new ArrayList(); 
    	}
    	catch(HibernateException e) {
            this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
    	}
    }
    
    
    /**
     * Retorna uma lista de {@link EstruturaWebServiceBean} para o Web Service.
     * 
     * @author aleixo
     * @version 0.1 - 10/04/2007
     * @return List
     */
    public List getListaEstruturaWebService(){
    	List retorno = new ArrayList();
    	
    	StringBuilder select = new StringBuilder();
    	
    	select.append("select e.cod_ett, e.nome_ett from tb_estrutura_ett e order by e.nome_ett");
    	SQLQuery q = this.session.createSQLQuery(select.toString());
    	
    	List objects = q.list();
    	
    	if(objects != null && !objects.isEmpty()){
    		Iterator itO = objects.iterator();
    		while(itO.hasNext()){
    			Object[] o = (Object[]) itO.next();
    			
    			EstruturaWebServiceBean ewsBean = new EstruturaWebServiceBean();
    			
    			ewsBean.setCodigo((o[0] != null) ? Long.valueOf(o[0].toString()) : null);
    			ewsBean.setNome((o[1] != null) ? o[1].toString() : null);
    			
    	    	retorno.add(ewsBean);    			
    		}
    	}

    	return retorno;
    }
    
    
    /**
     * @param estruturaEtt
     * @author Robson
     * @since  19/11/2007
     * @return List<EstruturaEtt>
     * Retorna uma lista das Estruturas em Arvore a partir de uma estrutura pai
     */
    public List getListaEstruturas(EstruturaEtt estruturaEtt){
    	List lista = new ArrayList();
    	
    	Criteria select = session.createCriteria(EstruturaEtt.class);
    	
    	if (estruturaEtt == null || estruturaEtt.getCodEtt() == null)
    		select.add(Restrictions.isNull("estruturaEtt"));
    	else
    		select.add(Restrictions.eq("estruturaEtt", estruturaEtt));
    	
    	List pais = select
    				.add(Restrictions.eq("indAtivoEtt",Dominios.ATIVO))
    				.addOrder(Order.asc("nomeEtt")).list();
    	Iterator it = pais.iterator();
    	
    	while (it.hasNext()){
    		estruturaEtt = (EstruturaEtt) it.next();
    		lista.add(estruturaEtt);
    		lista.addAll(this.getListaEstruturas(estruturaEtt));
    	}
    	return lista;
    }
    
    /**
     * @param estruturaEtt
     * @author Robson
     * @since  19/11/2007
     * @return List<EstruturaEtt>
     * Retorna uma lista das Estruturas em Arvore a partir de uma estrutura pai
     */
    public List getListaEstruturasAtivasInativas(EstruturaEtt estruturaEtt){
    	List lista = new ArrayList();
    	
    	Criteria select = session.createCriteria(EstruturaEtt.class);
    	
    	if (estruturaEtt == null || estruturaEtt.getCodEtt() == null)
    		select.add(Restrictions.isNull("estruturaEtt"));
    	else
    		select.add(Restrictions.eq("estruturaEtt", estruturaEtt));
    	
    	List pais = select
    				.addOrder(Order.asc("nomeEtt")).list();
    	Iterator it = pais.iterator();
    	
    	while (it.hasNext()){
    		estruturaEtt = (EstruturaEtt) it.next();
    		lista.add(estruturaEtt);
    		lista.addAll(this.getListaEstruturasAtivasInativas(estruturaEtt));
    	}
    	return lista;
    }
    
    /**
     * @author Robson
     * @since  19/11/2007
     * @return List<EstruturaEtt>
     * n� raiz
     */
    
    public List getListaEstruturas(){
    	return this.getListaEstruturas(null);
    }
 
    /**
     * Associa itens a estrutura virtual
     * 
     * @param estrutura
     * @param itens
     * @throws ECARException 
     */
    public void associarItensEstruturaVirtual (EstruturaEtt estrutura,String[] itens) throws ECARException{
    	
    		Transaction tx = null;

	        try{
	
				tx = session.beginTransaction();
	
	    	
	    	if (!getSession().contains(estrutura)) {
	    		getSession().load(estrutura, estrutura.getCodEtt());
	    	}
	    	
	    	for (int i = 0; i < itens.length; i++) {
				
	    		ItemEstruturaIett item = (ItemEstruturaIett) getSession().load(ItemEstruturaIett.class, new Long(itens[i]));
	    		
	    		estrutura.getItensEstruturaVirtual().add(item);
	    		
			} 
	    	
	    	getSession().update(estrutura);
	    	
	    	tx.commit();
    	
        } catch(HibernateException e){
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
     *
     * @param estrutura
     * @param itens
     * @throws ECARException
     */
    public void desassociarItensEstruturaVirtual(EstruturaEtt estrutura,String[] itens) throws ECARException{
 
		Transaction tx = null;
    	
        try{

			tx = session.beginTransaction();

	    	for (int i = 0; i < itens.length; i++) {
				
	    		//Cria um objeto item desacoplado da Sessao do hibernate
	    		ItemEstruturaIett item = new ItemEstruturaIett();
	    		item.setCodIett(new Long(itens[i]));
	    		
	    		estrutura.getItensEstruturaVirtual().remove(item);
	    		
			}
	    	
	    	getSession().update(estrutura);

	    	tx.commit();
	    	
        	} catch(HibernateException e){
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
     * Devolve uma lista de Atributos de acordo com a estrutura e fun��o de acompanhamento passados como par�metro
     * Essa lista cont�m objetos estruturaAtributo ordenados pela ordem do campo na tela.
     * @param estruturaEtt
     * @param funcaoFun
     * @return lista de objetos estruturaAtributo ordenados pela ordem do campo na tela.
     * @throws ECARException
     */
     public List getAtributosEstrutura(EstruturaEtt estruturaEtt, FuncaoFun funcaoFun) throws ECARException {
    	 //retorna lista de atributos de uma estrutura de acordo com a fun��o passada
    	 //ex: atributos da func�o dados gerais...
    	 
    	 Query query = null;
    	 List rs = new ArrayList();
    	 try {
    		 StringBuilder sb = new StringBuilder("select estruturaAtributo from EstruturaAtributoEttat as estruturaAtributo ");
    		 sb.append(" where estruturaAtributo.comp_id.codAtb in (select atributo from AtributosAtb as atributo where atributo.funcaoFun.codFun = :codFun) ");
    		 sb.append(" and estruturaAtributo.estruturaEtt.codEtt = :codEtt order by estruturaAtributo.seqApresentTelaCampoEttat asc");	
    		 query = this.getSession().createQuery(sb.toString());
    		 query.setLong("codFun", funcaoFun.getCodFun());
    		 query.setLong("codEtt", estruturaEtt.getCodEtt());
    		 rs = query.list();
    	 } catch (Exception e) {
    		 throw new ECARException("Erro ao executar método getAtributosEstrutura. Exception capturada: " + e.getMessage());
    	 }
    	 return rs;
    	
    }
    
    
    
    /**
     * Retorna uma lista de atributos que ser�o utilizados para montar  
     * o nome de um iett na �rvore de cadastro, localizado na listagem
     * do cadastro.
     *  
     * @param estrutura 
     * @return List ObjetoEstrutura
     * @throws ECARException
     */
    public List getAtributosArvoreEstrutura(EstruturaEtt estrutura) throws ECARException {
        List retorno = new ArrayList();

        try {
            retorno.addAll(session.createFilter(estrutura.getEstruturaAtributoEttats(),
                    "where this.indListagemArvoreEttat = 'S'").list());

            Collections.sort(retorno,
    	            new Comparator() {
    	        		public int compare(Object o1, Object o2) {
    	        		    return ((ObjetoEstrutura)o1).iGetSequenciaColunaEmListagem().intValue() - ((ObjetoEstrutura)o2).iGetSequenciaColunaEmListagem().intValue();
    	        		}
    	    		} );
            
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        return retorno;
    }
 
    /**
     * Recebe uma estrutura e retorna um boolean informando se a estrutura tem algum item ativo
     * 
     * @param estrutura
     * @return
     * @throws ECARException
     */
    public boolean verificaSeTemItemAtivo(EstruturaEtt estrutura) throws ECARException {
        
        Iterator<ItemEstruturaIett> itensIt = new ArrayList(estrutura.getItemEstruturaIetts()).iterator();
        
        while(itensIt.hasNext()) {
        	ItemEstruturaIett item = (ItemEstruturaIett)itensIt.next();
        	if(Dominios.ATIVO.equals(item.getIndAtivoIett())) {
        		return false;
        	}
        }
        
	    return true;
    }
    
    /**
     * Recebe uma estrutura e o item selecionado e retorna um boolean informando se a estrutura pode ser exibida
     * 
     * @param estrutura
     * @param itemEstruturaSelecionado
     * @return
     * @throws ECARException
     */
    public boolean verificarExibeEstrutura(EstruturaEtt estrutura, ItemEstruturaIett itemEstruturaSelecionado) throws ECARException {
    	boolean exibirEstrutura = false;
    	
    	if (estrutura.getIndExibirEstruturaEtt() == null || estrutura.getIndExibirEstruturaEtt().equals(Dominios.NAO) 
    			|| itemEstruturaSelecionado == null ||  itemEstruturaSelecionado.getCodIett() == null 
    			|| itemEstruturaSelecionado.getItemEstruturaSisAtributoIettSatbs() == null) {
    		exibirEstrutura = true;
    	} else if(estrutura.getIndExibirEstruturaEtt().equals(Dominios.SIM)) {
    		Iterator<SisAtributoSatb> itSisAtributos = estrutura.getSisAtributoSatbEttSuperior().iterator();
			Iterator<ItemEstruturaSisAtributoIettSatb> itIettSatbs =  itemEstruturaSelecionado.getItemEstruturaSisAtributoIettSatbs().iterator();
			while(itIettSatbs.hasNext()) {
				ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributoIettSatb = (ItemEstruturaSisAtributoIettSatb) itIettSatbs.next();
				if(itemEstruturaSisAtributoIettSatb.getSisAtributoSatb() != null) {
					SisAtributoSatb sisAtributoItem = itemEstruturaSisAtributoIettSatb.getSisAtributoSatb();
					while(itSisAtributos.hasNext()) {
						SisAtributoSatb sisAtributoItemConfigurado = (SisAtributoSatb)itSisAtributos.next();
						if(sisAtributoItemConfigurado.getCodSatb().equals(sisAtributoItem.getCodSatb())) {
							exibirEstrutura = true;
							break;
						}
					}
					if(exibirEstrutura) {
						break;
					}
					itSisAtributos = estrutura.getSisAtributoSatbEttSuperior().iterator();
				}
			}
		}
    	return exibirEstrutura;
    }
    
    /**
     * Recebe uma estrutura e retorna um boolean informando se a estrutura est� configurada para usar model
     * 
     * @param estrutura
     * @return
     * @throws ECARException
     */
    public boolean verificaSeEstruturaUsaModelo(EstruturaEtt estrutura) throws ECARException {
        EstruturaAtributoEttatPK comp_id = new EstruturaAtributoEttatPK();
        comp_id.setCodEtt(estrutura.getCodEtt());
        
        //Pesquisa o atributo corresponente ao indicativo de que o item � modelo
        AtributosAtb atributo = new AtributosAtb();
        atributo.setNomeAtb("indModeloIett");
        
        List atributos = new AtributoDao(request).pesquisar(atributo, null);
        comp_id.setCodAtb(((AtributosAtb)atributos.get(0)).getCodAtb());
    	
    	EstruturaAtributoEttat ettat = new EstruturaAtributoEttat();
    	EstruturaAtributoDao ettatDao = new EstruturaAtributoDao(request);

    	try {
    	ettat = (EstruturaAtributoEttat)ettatDao.buscar(EstruturaAtributoEttat.class, comp_id);
    	} catch (ECARException e) {
			return false;
		}

    	if (ettat!= null && ettat.getAtributosAtb().getIndAtivoAtb().equals(Dominios.SIM)){
    		return true;
    	}
	    
	    return false;
    }
    
    /**
     * @param virtual
     * @param estruturaEtt
     * @author Robson
     * @since  19/11/2007
     * @return List<EstruturaEtt>
     * Retorna uma lista das Estruturas em Arvore a partir de uma estrutura pai
     * @throws ECARException
     */
    public List getEstruturasReaisOuVirtuais(boolean virtual, EstruturaEtt estruturaEtt) throws ECARException{
    	
    	String select = "select e from EstruturaEtt as e " +
		" where e.indAtivoEtt='S'";
		
		if (estruturaEtt != null && estruturaEtt.getCodEtt() != null){
			
			select +=  " and e.estruturaEtt.codEtt = :codEtt";
		}
		else{
			select +=  " and e.estruturaEtt is null";
		}
    	
    	select += " and e.virtual =:virtual";
		
    	select += " order by e.nomeEtt";
		Query q = this.session.createQuery(select);
		
		if (estruturaEtt != null && estruturaEtt.getCodEtt() != null){
			q.setLong("codEtt", estruturaEtt.getCodEtt().longValue());
		}
		
		q.setBoolean("virtual", virtual);
		
		List pais = q.list(); 
    	
    	List lista = new ArrayList();
    	
//    	Criteria select = session.createCriteria(EstruturaEtt.class);
//    	
//    	if (estruturaEtt == null || estruturaEtt.getCodEtt() == null)
//    		select.add(Restrictions.isNull("estruturaEtt"));
//    	else
//    		select.add(Restrictions.eq("estruturaEtt", estruturaEtt));
//    	
//    	select.add(Restrictions.ne("virtual", true));
//    	
//    	List pais = select    				
//    				.add(Restrictions.eq("indAtivoEtt",Dominios.ATIVO))
//    				.addOrder(Order.asc("nomeEtt")).list();
    	
    	Iterator it = pais.iterator();
    	
    	while (it.hasNext()){
    		estruturaEtt = (EstruturaEtt) it.next();
    		lista.add(estruturaEtt);
    		lista.addAll(this.getEstruturasReaisOuVirtuais(virtual, estruturaEtt));
    	}
    	return lista;
    }
    
    /**
     * Retorna as que podem ser pai (n�vel superior) da estrutura real sendo alterada
     * @param estruturaSelecionada 
     * @return List de estruturas
     * @throws ECARException
     */
    public List getEstruturasReaisPaisPossiveis(EstruturaEtt estruturaSelecionada) throws ECARException {
    	try {
   		
	    	String select = "select e from EstruturaEtt as e " +
	    			" where e.indAtivoEtt='S' and" +
	    			" e.virtual <> true ";
	    	if (estruturaSelecionada != null && estruturaSelecionada.getCodEtt() != null){
	    		select = select + " and e.codEtt <> :codEtt"; 
	    	}
	    	select = select + " order by e.nomeEtt";
	    	
	    	Query q = this.session.createQuery(select);
	    	if (estruturaSelecionada != null && estruturaSelecionada.getCodEtt() != null){
	    		q.setLong("codEtt", estruturaSelecionada.getCodEtt().longValue());
	    	}
	    	
			return q.list(); 
    	}
    	catch(HibernateException e) {
            this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
    	}
    }
    
    /**
     * 
     * @param nomeEtt
     * @return
     */
    public EstruturaEtt getEstruturaEttByNome(String nomeEtt) throws ECARException{
    	EstruturaEtt estruturaEtt = null;
    	try {
    		String hql = "select estruturaEtt from EstruturaEtt estruturaEtt where estruturaEtt.nomeEtt = :nomeEtt";
			Query q = this.session.createQuery(hql);
			q.setString("nomeEtt", nomeEtt);
			q.setMaxResults(1);
			estruturaEtt = (EstruturaEtt) q.uniqueResult();
    	} catch(HibernateException e) {
            this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
    	}
    	return estruturaEtt;
    }
    
    public List<EstruturaEtt> getEstruturasSisAtributo(SisAtributoSatb sisAtributo){
    	
    	List<EstruturaEtt> estruturas = new ArrayList<EstruturaEtt>();
    	
    	String hql = "Select ett from EstruturaEtt ett join ett.sisAtributoSatbEttSuperior sisAtb where sisAtb.codSatb = :codSatb" ;
    	
    	Query q = this.session.createQuery(hql);
		q.setLong("codSatb", sisAtributo.getCodSatb());
		
    	estruturas = q.list();
		
    	return estruturas;
    }
    
    
    
    /**
     * Recebe um atributo, uma estrutura, o item selecionado e retorna um boolean informando se o atributo est� sendo usado em algum item da estrutura superior
     * @param sisAtributoSatb 
     * @param estrutura
     * @param itemEstruturaSelecionado
     * @return
     * @throws ECARException
     */
    public boolean existeSisAtributoSatbUsadoEstruturaSuperior(SisAtributoSatb sisAtributoSatb, EstruturaEtt estrutura) throws ECARException {
    	boolean existeAtributoSatbUsado = false;
    	
    	try{ 
    		
    		ItemEstruturaDao  itemEstruturaDao = new ItemEstruturaDao(null);
    		List itensAtivos = null;
    		List listaItens = new ArrayList();
    		
  
    		if(estrutura.getCodEtt() != null) {
    			itensAtivos = itemEstruturaDao.getItensAtivosByEstrutura(estrutura.getCodEtt());
    		}	
    		
    	
	    	if(estrutura.getSisAtributoSatbEttSuperior() != null && estrutura.getEstruturaEtt() != null && itensAtivos != null && !itensAtivos.isEmpty()) {
	    		
	    		//pesquisa se algum item da estrutura pai possui o valor do sisAtributoSatb passado como parametro
	    		StringBuffer hql = new StringBuffer();
	    		
	    	
	    		hql.append("select iett from ItemEstruturaIett iett join iett.itemEstruturaSisAtributoIettSatbs satb ");
	    		hql.append("where satb.comp_id.codSatb = :codSatbProcurado ");
	    		hql.append("and iett.estruturaEtt = :estruturaEtt and ");
	    		hql.append("iett.indAtivoIett = 'S'");
	    		Query q = this.session.createQuery(hql.toString());
	   			q = this.session.createQuery(hql.toString());
	    	
	   			q.setParameter("estruturaEtt", estrutura.getEstruturaEtt());
	   			q.setParameter("codSatbProcurado", sisAtributoSatb.getCodSatb());
	   		    listaItens = q.list();
	   			    
	   		    if(listaItens != null && !listaItens.isEmpty()) {
	   		    	existeAtributoSatbUsado = true;
	   		    }
	       			
	    	}
		
	    	return existeAtributoSatbUsado;
    	
    	}catch(HibernateException e) {
            this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
    	}
    }
 
    /**
     * Retorna o label do atributo na estrutura de acordo com os parametros
     * @param ett
     * @param sga
     * @return
     * @throws ECARException
     */
    public String getLabelAtributoEstrutra(EstruturaEtt ett, SisGrupoAtributoSga sga)throws ECARException {
    	EstruturaAtributoEttat ettat = null;
    	String label = "";
    	try {
    		StringBuffer hql = new StringBuffer();
    		hql.append("select ettat from EstruturaAtributoEttat ettat ");
    		hql.append("where ettat.estruturaEtt = :ett ");
    		hql.append("and ettat.atributosAtb.sisGrupoAtributoSga = :sga ");
    		
			Query q = this.session.createQuery(hql.toString());
			q.setEntity("ett", ett);
			q.setEntity("sga", sga);
			q.setMaxResults(1);
			ettat = (EstruturaAtributoEttat) q.uniqueResult();
			if (ettat != null){
				if (ettat.getLabelEstruturaEttat() != null && !ettat.getLabelEstruturaEttat().equals("")){
					label = ettat.getLabelEstruturaEttat();
				} else {
					label = ettat.getAtributosAtb().getLabelPadraoAtb();
				}
			}
    	} catch(HibernateException e) {
            this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
    	}
    	return label;
    }
    
    
}