/*
 * Criado em 28/10/2004
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import comum.database.CacheManagerImpl;
import comum.database.Dao;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.FuncaoFun;
import ecar.util.Dominios;

/**
 * @author felipev
 *
 */
public class FuncaoDao extends Dao{
    
    /**
     *
     */
    public static final String NOME_FUNCAO_DADOS_GERAIS = "Dados_Gerais";
        /**
         *
         */
        public static final String NOME_FUNCAO_PONTOS_CRITICOS = "Pontos_Criticos";
        /**
         *
         */
        public static final String NOME_FUNCAO_AGENDA = "Agenda";
        /**
         *
         */
        public static final String NOME_FUNCAO_APONTAMENTOS = "Apontamentos";
        /**
         *
         */
        public static final String NOME_FUNCAO_EDITORES_LEITORES= "Editores_Leitores";//controle de Permiss�o
        /**
         *
         */
        public static final String NOME_FUNCAO_ASSOCIACAO_DEMANDAS= "AssociacaoDemandas";
        /**
         *
         */
        public static final String NOME_FUNCAO_BENEFICIARIO= "Beneficiario";
        /**
         *
         */
        public static final String NOME_FUNCAO_CATEGORIAS= "Categorias";
        /**
         *
         */
        public static final String NOME_FUNCAO_CONTAS_ORCAMENTO= "Contas_do_Orcamento";
        /**
         *
         */
        public static final String NOME_FUNCAO_CRITERIOS= "Criterios";
        /**
         *
         */
        public static final String NOME_FUNCAO_ENTIDADES= "Entidades";
        /**
         *
         */
        public static final String NOME_FUNCAO_EVENTOS= "Evento"; //tamb�m conhecida como A��O  e Di�rio de bordo
        /**
         *
         */
        public static final String NOME_FUNCAO_EXECUCAO_FINANCEIRA= "Execucao_Financeira";
        /**
         *
         */
        public static final String NOME_FUNCAO_FONTES_RECURSOS = "Fontes_Recursos";
        /**
         *
         */
        public static final String NOME_FUNCAO_LOCALIZACAO = "Localizacao";
        /**
         *
         */
        public static final String NOME_FUNCAO_METAS_INDICADORES = "Indicadores_Resultado"; //Tamb�m conhecido como indicadores de resultado
        /**
         *
         */
        public static final String NOME_FUNCAO_INFORMACOES_RELACIONADAS = "Informacoes_Relacionadas";
        /**
         *
         */
        public static final String NOME_FUNCAO_ITENS_ANEXO = "Itens_de_Anexo";
        /**
         *
         */
        public static final String NOME_FUNCAO_QUANTIDADES_PREVISTAS = "Quantidades_Previstas";
        /**
         *
         */
        public static final String NOME_FUNCAO_RECURSOS = "Recursos";
        /**
         *
         */
        public static final String NOME_FUNCAO_RELATORIOS_IMPRESSOS = "Relatorios_Impressos";
        /**
         *
         */
        public static final String NOME_FUNCAO_REVISAO = "Revisao";
	
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public FuncaoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}

	/**
	 * Utiliza o m�todo excluir da classe Dao realizando antes valida��es de relacionamento da 
	 * fun��o com registros em outras tabelas.
	 * 
         * @param funcao
         * @author n/c
	 * @throws ECARException
	 */
	public void excluir(FuncaoFun funcao) throws ECARException {	    
	   try{
	       	boolean excluir = true;
		    if(contar(funcao.getEstruturaFuncaoEttfs()) > 0){
		        excluir = false;
			    throw new ECARException("funcao.exclusao.erro.estruturaFuncaoEtts");
		    }      			       
		    if(excluir)
		        super.excluir(funcao);
	   }catch(ECARException e){	   
		   this.logger.error(e);
	       throw e;
	   }    
	}
	
	/**
	 * Recebe um array com os C�digos dos Tipos de Fun��o e retorna um Set com objetos TipoFuncAcompTpfa correspondentes
	 * a estes c�digos
	 * @author n/c
	 * @param funcoes array com os C�digos dos Tipos de Fun��o
         * @return Set objetos TipoFuncAcompTpfa
         * @throws ECARException
	 */
	public Set getSetFuncoes(String funcoes[]) throws ECARException{
	    Set retorno = new HashSet();
		if(funcoes != null){
			for(int i = 0; i < funcoes.length; i++){				
				try{
					FuncaoFun funcao = (FuncaoFun) this.buscar(FuncaoFun.class, Long.valueOf(funcoes[i]));
					retorno.add(funcao);		
				} catch (ECARException e) {
					this.logger.error(e);
					throw e;
				}
			}		
		}
		return retorno;
	}
	
    /**
     * Devolve um conjunto com todos as fun��es ativas nao opcionais, ou seja,
     * que s�o obrigatorios em todas as estruturas
     * 
	 * @author n/c
     * @return List
     * @throws ECARException 
     */
    @SuppressWarnings("unchecked")
	public List getFuncoesNaoOpcionais() throws ECARException {
        List retorno = new ArrayList();

        FuncaoFun funcao = new FuncaoFun();

        funcao.setIndAtivoFun("S");
        funcao.setIndOpcionalFun("N");

        retorno = this.pesquisar(funcao, new String[]{"nomeFun",FuncaoDao.ORDEM_ASC});

        return retorno;
    }
    
    /**
     * Devolve um conjunto com todos as fun��es ativas opcionais
     * 
	 * @author n/c
     * @return List
     * @throws ECARException 
     */
    @SuppressWarnings("unchecked")
	public List getFuncoesOpcionais() throws ECARException {
        List retorno = new ArrayList();

        FuncaoFun funcao = new FuncaoFun();

        funcao.setIndAtivoFun("S");
        funcao.setIndOpcionalFun("S");

        retorno = this.pesquisar(funcao, new String[]{"nomeFun",FuncaoDao.ORDEM_ASC});

        return retorno;

    }
	
    /**
     * Devolve uma funcao atrav�s do ID
     * 
     * @param id
	 * @author n/c
     * @return FuncaoFun
     * @throws ECARException
     */
    public FuncaoFun getFuncao(Long id) throws ECARException {
        return (FuncaoFun) this.buscar(FuncaoFun.class, id);
    }    
    

	 /**
	  * 
	  * @author n/c
	  * @param funcao
	  * @throws ECARException
	  */
    public void salvar(FuncaoFun funcao) throws ECARException {
		if (pesquisarDuplos(funcao, new String[] {"nomeFun","labelPadraoFun"}, "codFun").size() > 0)
		    throw new ECARException("funcao.validacao.registroDuplicado");
		super.salvar(funcao);
	}
    
    /**
     * 
	 * @author n/c
     * @param funcao
     * @throws ECARException
     */
    public void alterar(FuncaoFun funcao) throws ECARException {
		if (pesquisarDuplos(funcao, new String[] {"nomeFun","labelPadraoFun"}, "codFun").size() > 0)
		    throw new ECARException("funcao.validacao.registroDuplicado");
		super.alterar(funcao);
	}
    
    /**
     *
     * @param request
     * @param funcao
     * @param usarGetparamStr
     */
    public void setFuncaoFun(HttpServletRequest request, FuncaoFun funcao, boolean usarGetparamStr){
		if(Pagina.getParam(request, "codigo") != null)
			funcao.setCodFun(Long.valueOf (Pagina.getParam(request, "codigo")));
		else
			funcao.setCodFun(null);
    	
    	if(usarGetparamStr){
			funcao.setNomeFun(Pagina.getParamStr(request, "nomeFun").trim());
			funcao.setLabelPadraoFun(Pagina.getParamStr(request, "labelPadraoFun").trim());
			funcao.setLinkFuncaoFun(Pagina.getParamStr(request, "linkFuncaoFun").trim());
			funcao.setIndAtivoFun(Pagina.getParamOrDefault(request, "indAtivoFun",Pagina.NAO).trim());
			funcao.setIndExclusivoEstruturaFun(Pagina.getParamStr(request, "indExclusivoEstruturaFun").trim());
			funcao.setIndOpcionalFun(Pagina.getParamOrDefault(request, "indOpcionalFun", Pagina.NAO).trim());
			funcao.setDocumentacaoFun(Pagina.getParamStr(request, "documentacaoFun").trim());
    	}
    	else{
    		funcao.setNomeFun(Pagina.getParam(request, "nomeFun"));
    		funcao.setLabelPadraoFun(Pagina.getParam(request, "labelPadraoFun"));
    		funcao.setLinkFuncaoFun(Pagina.getParam(request, "linkFuncaoFun"));
    		funcao.setIndAtivoFun(Pagina.getParam(request, "indAtivoFun"));
    		funcao.setIndExclusivoEstruturaFun(Pagina.getParam(request, "indExclusivoEstruturaFun"));
    		funcao.setIndOpcionalFun(Pagina.getParam(request, "indOpcionalFun"));
			funcao.setDocumentacaoFun(Pagina.getParam(request, "documentacaoFun"));
    	}
    	
    	if (funcao.getIndPossuiAtributos() == null){
    		funcao.setIndPossuiAtributos("N");
    	}
    }
    
    /**
     * Recupera o codFun da fun��o de dados gerais
     * @return Long
     * @throws ECARException 
     */
    public long getCodFuncaoDadosGerais() throws ECARException{
    	long retorno = 0;
    	FuncaoFun funcaoFun = this.getFuncaoDadosGerais();
    	if (funcaoFun != null){
    		retorno = funcaoFun.getCodFun().longValue();
    	} 
    	
    	return retorno;
    }
    
    /**
     * 
     * @return
     * @throws ECARException
     */
    @SuppressWarnings("static-access")
    public FuncaoFun getFuncaoDadosGerais() throws ECARException{
    	FuncaoFun funcaoFun = new FuncaoFun();
    	funcaoFun.setNomeFun(FuncaoDao.NOME_FUNCAO_DADOS_GERAIS);
//    	List listFuncao = this.pesquisar(funcaoFun, new String[]{"codFun",FuncaoDao.ORDEM_ASC});
    	
    	List listFuncao;
		String cacheId = FuncaoDao.NOME_FUNCAO_DADOS_GERAIS;
		listFuncao = (List) CacheManagerImpl.get(cacheId);
		if (listFuncao == null) {
			listFuncao = this.pesquisar(funcaoFun, new String[]{"codFun",FuncaoDao.ORDEM_ASC});
			CacheManagerImpl.add(cacheId, listFuncao);
		}
		
    	if (!listFuncao.isEmpty()){
    		return ((FuncaoFun) listFuncao.get(0));
    	} 
    	return null;
    }
    
    /**
     * 
     * @return
     * @throws ECARException
     */
    @SuppressWarnings("static-access")
    public FuncaoFun getFuncaoEntidades() throws ECARException{
    	FuncaoFun funcaoFun = new FuncaoFun();
    	funcaoFun.setNomeFun(FuncaoDao.NOME_FUNCAO_ENTIDADES);
    	List listFuncao = this.pesquisar(funcaoFun, new String[]{"codFun",FuncaoDao.ORDEM_ASC});
    	if (!listFuncao.isEmpty()){
    		return ((FuncaoFun) listFuncao.get(0));
    	} 
    	return null;
    }
    
    /**
     * 
     * @param nomeFuncao
     * @return
     * @throws ECARException
     */
    public FuncaoFun getFuncaoPorNome(String nomeFuncao) throws ECARException{
    	FuncaoFun funcaoFun = new FuncaoFun();
    	funcaoFun.setNomeFun(nomeFuncao);
    	List listFuncao = this.pesquisar(funcaoFun, new String[]{"codFun",FuncaoDao.ORDEM_ASC});
    	if (!listFuncao.isEmpty()){
    		return ((FuncaoFun) listFuncao.get(0));
    	} 
    	return null;
    }
    
    /**
     * Recupera o codFun da fun��o de pontos cr�ticos
     * @return Long
     * @throws ECARException 
     */
    public long getCodFuncaoPontosCriticos() throws ECARException{
    	long retorno = 0;
    	FuncaoFun funcaoFun = this.getFuncaoPontosCriticos();
    	if (funcaoFun != null){
    		retorno = funcaoFun.getCodFun().longValue();
    	} 
    	
    	return retorno;
    }
    
    /**
     * 
     * @return
     * @throws ECARException
     */
    @SuppressWarnings("static-access")
    public FuncaoFun getFuncaoPontosCriticos() throws ECARException{
    	FuncaoFun funcaoFun = new FuncaoFun();
    	funcaoFun.setNomeFun(FuncaoDao.NOME_FUNCAO_PONTOS_CRITICOS);
    	List listFuncao = this.pesquisar(funcaoFun, new String[]{"codFun",FuncaoDao.ORDEM_ASC});
    	if (!listFuncao.isEmpty()){
    		return ((FuncaoFun) listFuncao.get(0));
    	} 
    	return null;
    }
    
    /**
     *
     * @param listFuncoes
     */
    public void filtraFuncoesParaCopiar(List listFuncoes){
    
    	Iterator<FuncaoFun> itFuncoes = listFuncoes.iterator();
    	while (itFuncoes.hasNext()) {
			FuncaoFun funcao = (FuncaoFun) itFuncoes.next();
			if (funcao.getIndCopiaFun() != null && !funcao.getIndCopiaFun().equals(Dominios.SIM)){
				itFuncoes.remove();
			}
			
		}
	
	
    }
}
