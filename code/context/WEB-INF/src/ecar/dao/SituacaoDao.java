/*
 * Criado em 28/10/2004
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.SitDemandaSitd;
import ecar.pojo.SituacaoSit;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;

/**
 * @author felipev
 *
 */
public class SituacaoDao extends Dao{
    
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public SituacaoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Utiliza o método excluir da classe Dao realizando antes validações de relacionamento da 
	 * situação com registros em outras tabelas.
	 * @param situacao
	 * @throws ECARException
	 */
	public void excluir(SituacaoSit situacao) throws ECARException {	    
	   try{
	       	boolean excluir = true;
		    if(contar(situacao.getAcompRealFisicoArfs()) > 0){
		        excluir = false;
		        AcompRealFisicoArf arf = (AcompRealFisicoArf) new ArrayList(situacao.getAcompRealFisicoArfs()).get(0);
			    throw new ECARException("situacao.exclusao.erro.acompRealFisicoArfs", null, null);
		    }      			       
		    if(contar(situacao.getAcompRelatorioArels()) > 0){
		        excluir = false;
		        AcompRelatorioArel arel = (AcompRelatorioArel) new ArrayList(situacao.getAcompRelatorioArels()).get(0);
		        throw new ECARException("situacao.exclusao.erro.acompRelatorioArels", null, null);			       
			}
//		    if(contar(situacao.getEstruturaSituacaoEtts()) > 0){
//		    	excluir = false;
//		    	EstruturaEtt ett = (EstruturaEtt) new ArrayList(situacao.getEstruturaSituacaoEtts()).get(0);
//		    	throw new ECARException("situacao.exclusao.erro.estruturaSituacaoEtts", null, new String[] {ett.getNomeEtt()});
//		    }
		    if(contar(situacao.getItemEstruturaIetts()) > 0){
		        excluir = false;
		        ItemEstruturaIett iett = (ItemEstruturaIett) new ArrayList(situacao.getItemEstruturaIetts()).get(0);
		        throw new ECARException("situacao.exclusao.erro.itemEstruturaIetts", null, new String[] {iett.getNomeIett()});			       
		    }
		    if(contar(situacao.getPerfilIntercambioDadosPflidsNaoInformado()) > 0){
		        excluir = false;
		        PerfilIntercambioDadosPflid plfid = (PerfilIntercambioDadosPflid) new ArrayList(situacao.getPerfilIntercambioDadosPflidsNaoInformado()).get(0);
		        throw new ECARException("situacao.exclusao.erro.perfilIntercambioDadosPflids", null, new String[] {plfid.getNomePflid()});			       
		    }
		    if(contar(situacao.getPerfilIntercambioDadosPflidsSemCorrespondente()) > 0){
		        excluir = false;
		        PerfilIntercambioDadosPflid plfid = (PerfilIntercambioDadosPflid) new ArrayList(situacao.getPerfilIntercambioDadosPflidsSemCorrespondente()).get(0);
		        throw new ECARException("situacao.exclusao.erro.perfilIntercambioDadosPflids", null, new String[] {plfid.getNomePflid()});			       
		    }
//		    if(contar(situacao.getSituacaoTpFuncAcmpSitfas()) > 0){
//		    	excluir = false;
//		    	TipoFuncAcompTpfa tpfa = (TipoFuncAcompTpfa) new ArrayList(situacao.getSituacaoTpFuncAcmpSitfas()).get(0);
//		    	throw new ECARException("situacao.exclusao.erro.situacaoTpFuncAcmpSitfas", null, new String [] {tpfa.getDescricaoTpfa()});
//		    }
		    if(excluir)
		        super.excluir(situacao);
	   }catch(ECARException e){	    
		   this.logger.error(e);
	       throw e;
	   }    
	}
	
	/**
	 * Invoca o método pesquisar da classe Dao e filtra o resultado obtido para retornar somente 
	 * os registros que tenham as estruturas e funções de acompanhamento informadas na pesquisa
	 * @param situacao
	 * @param ordem
	 * @return
	 * @throws ECARException
	 */
	public List pesquisar(SituacaoSit situacao, String[] ordem) throws ECARException {

	    List results = super.pesquisar(situacao, ordem);	    
	    if(situacao.getEstruturaSituacaoEtts() != null && situacao.getEstruturaSituacaoEtts().size() > 0) {
	        Iterator it = results.iterator();
	        while(it.hasNext()){
	            SituacaoSit situacaoBanco = (SituacaoSit) it.next();
	            if(!situacaoBanco.getEstruturaSituacaoEtts().containsAll(situacao.getEstruturaSituacaoEtts())){
	                it.remove();
	            } 
	        }
	    }
	    if(situacao.getSituacaoTpFuncAcmpSitfas() != null && situacao.getSituacaoTpFuncAcmpSitfas().size() > 0){
	        Iterator it = results.iterator();
	        while(it.hasNext()){
	            SituacaoSit situacaoBanco = (SituacaoSit) it.next();
	            if(!situacaoBanco.getSituacaoTpFuncAcmpSitfas().containsAll(situacao.getSituacaoTpFuncAcmpSitfas())){
	                it.remove();
	            } 
	        }
	    }
	    
	    if (results.size() > 0) {
	        Iterator it = results.iterator();
	        SituacaoSit sit;
	        while (it.hasNext()) {
	            sit = (SituacaoSit) it.next();
	            
	            if(sit.getEstruturaSituacaoEtts() != null)
	            	sit.getEstruturaSituacaoEtts().size();
	            if(sit.getSituacaoTpFuncAcmpSitfas() != null)
	            	sit.getSituacaoTpFuncAcmpSitfas().size();
	        }
	    }
	    return results;
	    
	}

	/**
	 * Retorna um list com identificações de todas as Funções de Acompanhamento de uma situação
	 * @param situacao
	 * @return List de Long
	 */
	public List getFuncoesAcompanhamentoById(SituacaoSit situacao){
		List lFuncoes = new ArrayList();
		if(situacao.getSituacaoTpFuncAcmpSitfas()!= null){
			situacao.getSituacaoTpFuncAcmpSitfas().size(); // Faz isso para inicializar a collection fazendo o select do banco
			Iterator it = situacao.getSituacaoTpFuncAcmpSitfas().iterator();
			while(it.hasNext()){
				TipoFuncAcompTpfa funcao = (TipoFuncAcompTpfa) it.next();
				lFuncoes.add(funcao.getCodTpfa());
			}
		}	 
		return lFuncoes;
	}

	/**
	 * Retorna um list com identificações de todas as Estruturas de uma situação
	 * @param situacao
	 * @return List de Long
	 */
	public List getEstruturasById(SituacaoSit situacao){
	    List lEstrutura = new ArrayList();
		if(situacao.getEstruturaSituacaoEtts()!= null){
			Iterator it = situacao.getEstruturaSituacaoEtts().iterator();
			while(it.hasNext()){
				EstruturaEtt estrutura = (EstruturaEtt) it.next();
				lEstrutura.add(estrutura.getCodEtt());
			}
		}	    
		return lEstrutura;
	}

	/**
	 * Imprimi o script de validacao de acordo com as Situações que exigem comentario,
	 *     quando indComentario = 'S'.
	 * 
	 * @param lista  - List, lista de situacaoSit
	 * @param campo	 - String, campo que será obrigatório de acordo com a Situação;
	 * @param cont   - String, para ser utilizada com função eval() do JavaScript,
	 * 					concatenando com o nome do campo, permitindo utilizad em vários 
	 * 					campos. (ver em \regAcompanhamento\elabAcompanhamento\realizadoFisico.jsp)
	 * 					Para ser utilizada como vazia, passar o valor "\"\"", que devolve
	 * 					duas aspas ("") para ser utilizada no javascript;
	 * @param msg	 - String, mensagem a ser exibida;
	 * 
	 * @return
	 * @throws ECARException
	 */
	public String getScriptValidacao(List lista, String campo, String cont, String msg) throws ECARException{
		Iterator itSit = lista.iterator();
		StringBuilder strIf = new StringBuilder("");
		boolean sitIndComentario = false;
		
		if(itSit.hasNext()){
			while(itSit.hasNext()){
				SituacaoSit situacao = (SituacaoSit) itSit.next();
				if("S".equalsIgnoreCase(situacao.getIndComentarioSit())){
					sitIndComentario = true;
					if ("".equals(strIf))
						strIf = new StringBuilder("\t\tif(Trim(eval(\"form.").append(campo)
										.append("\" + ").append(cont).append(" + \".value\")) == \"\" && (");
					else
						strIf.append(" || ");
					strIf.append("eval(\"form.situacaoSit\" + ").append(cont).append(" + \".value\") == ")
						 .append(situacao.getCodSit());
				}
			}
		}
		
		if(sitIndComentario){
			strIf.append(")){\n");
			strIf.append("\t\t\talert(\"").append(msg).append("\");\n");
			strIf.append("\t\t\teval(\"form.").append(campo).append("\" + ").append(cont).append(" + \".focus()\");\n");
			strIf.append("\t\t\treturn(false);\n"); 
			strIf.append("\t\t}\n");
		}
		
		return strIf.toString();
	}
	
	/**
	 * Retorna lista de funções que sejam vinculadas a uma função de acompanhamento e a uma estrutura
	 * @param funcao
	 * @param estrutura
	 * @return
	 */
	public List getSituacaoByTipoFuncaoAcompanhamentoEstrutura(TipoFuncAcompTpfa funcao, EstruturaEtt estrutura){
	    List retorno = new ArrayList();
	     
	    Iterator it = funcao.getSituacaoTpFuncAcmpSitfas().iterator();
	    
	    while(it.hasNext()){
	        SituacaoSit situacao = (SituacaoSit) it.next();
	        if(estrutura.getEstruturaSituacaoEtts() != null && estrutura.getEstruturaSituacaoEtts().contains(situacao)){
	            retorno.add(situacao);
	        }
	    } 
	    
        return retorno;
	}
	
	/**
	 * Retorna lista de situacoes que sejam vinculadas a uma estrutura
	 * @param estrutura
	 * @param ordem
	 * @return List
	 */
	public List getSituacaoByEstrutura(EstruturaEtt estrutura, String[] ordem){
	    List retorno = new ArrayList();
	    try{
	    	List situacoes = this.listar(SituacaoSit.class, ordem);
		    Iterator it = situacoes.iterator();
		    while(it.hasNext()){
		        SituacaoSit situacao = (SituacaoSit) it.next();
		        if(estrutura.getEstruturaSituacaoEtts() != null && estrutura.getEstruturaSituacaoEtts().contains(situacao)){
		            retorno.add(situacao);
		        }
		    }
	    }
	    catch (Exception e){
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
	    }
        return retorno;
	}
	
	/**
	 * Retorna lista de situações que estão vinculadas qualquer estrutura.<br>
	 * 
	 * @param ordem
	 * @return List
	 */
	public List getSituacaoEmUsoPorEstrutura(String[] ordem){
	    List retorno = new ArrayList();
	    try{
	    	List situacoes = this.listar(SituacaoSit.class, ordem);
		    Iterator it = situacoes.iterator();
		    
		    while(it.hasNext()){
		        SituacaoSit situacao = (SituacaoSit) it.next();
		        
		        if(situacao.getEstruturaSituacaoEtts() != null && situacao.getEstruturaSituacaoEtts().size() > 0){
		            retorno.add(situacao);
		        }
		    }
	    }
	    catch (Exception e){
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
	    }
        return retorno;
	}

	/**
	 * verifica duplicação de registro depois salva
	 * @param situacao
	 * @throws ECARException
	 */
	public void salvar(SituacaoSit situacao) throws ECARException {
		if (pesquisarDuplos(situacao, new String[] {"descricaoSit"}, "codSit").size() > 0)
		    throw new ECARException("situacao.validacao.registroDuplicado");
		super.salvar(situacao);
	}
	
	/**
	 * 
	 * verifica duplicação de registro depois Altera
	 * @param situacao
	 * @throws ECARException
	 */
	public void alterar(SituacaoSit situacao) throws ECARException {
		if (pesquisarDuplos(situacao, new String[] {"descricaoSit"}, "codSit").size() > 0)
		    throw new ECARException("situacao.validacao.registroDuplicado");
		super.alterar(situacao);
	}
	
	/**
	 * Seleciona as situações de acordo com um conjunto de ids de situações 
         * @param idsSituacoes
         * @return
	 * @throws ECARException
	 */
	public List getSituacoes(String[] idsSituacoes) throws ECARException{
		
		try
    	{
			List<SitDemandaSitd> lista = null;
			
			if (idsSituacoes!=null && idsSituacoes.length>0 && idsSituacoes[0].length()>0) {
		    	Criteria crits = session.createCriteria(SitDemandaSitd.class);
	 
		    	Long[] idVisoesLong = new Long[idsSituacoes.length];
		    	for(int i=0;i<idsSituacoes.length;i++) {
		    		idVisoesLong[i] = Long.valueOf(idsSituacoes[i]);
		    	}
		    	
		    	crits.add(Restrictions.in("codSitd",idVisoesLong));
		    		    	
		    	lista = (List<SitDemandaSitd>)crits.list();
		    	
			}
	    	return lista;
    	
		}catch (Exception e) {
	        this.logger.error(e);
	    	throw new ECARException("erro.hibernateException");
		}
		
	}
	/**
	 * 
	 * @param descricaoSit
	 * @return
	 */
	public SituacaoSit getSituacaoSitByDescricao(String descricaoSit) throws ECARException{
		SituacaoSit situacaoSit = null;
		try {
			String hql = "select situacaoSit from SituacaoSit situacaoSit where situacaoSit.descricaoSit = :descricaoSit";
			Query q = this.session.createQuery(hql);
			q.setString("descricaoSit", descricaoSit);
			q.setMaxResults(1);
			situacaoSit = (SituacaoSit) q.uniqueResult();
		}catch(HibernateException e) {
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
		return situacaoSit;
	}
}