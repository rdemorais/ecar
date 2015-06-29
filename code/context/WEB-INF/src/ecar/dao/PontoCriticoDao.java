/*
 * Criado em 18/01/2005
 */

package ecar.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.ConstantesECAR;
import comum.util.Data;
import comum.util.FileUpload;
import comum.util.Pagina;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.historico.Historico;
import ecar.historico.HistoricoPtc;
import ecar.login.SegurancaECAR;
import ecar.pojo.AtributosAtb;
import ecar.pojo.Cor;
import ecar.pojo.EstruturaAtributoEttat;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.FuncaoFun;
import ecar.pojo.HistoricoPtcH;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.PaiFilho;
import ecar.pojo.PontoCriticoPtc;
import ecar.pojo.PontoCriticoSisAtributoPtcSatb;
import ecar.pojo.PontocriticoCorPtccor;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.TipoValor;
import ecar.pojo.TipoValorEnum;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.historico.HistoricoPontoCriticoPtc;
import ecar.pojo.historico.HistoricoXml;
import ecar.taglib.util.Input;
import ecar.util.Dominios;

/**
 * @author felipev
 */
public class PontoCriticoDao extends Dao {
	
	private Historico<HistoricoPontoCriticoPtc, PontoCriticoPtc> historico = null;
	
	
        /**
         *
         * @param request
         */
        public PontoCriticoDao(HttpServletRequest request) {
		super();
		this.request = request;
		historico = new Historico<HistoricoPontoCriticoPtc, PontoCriticoPtc>() {};
	}
	
	/**
	 * Retorna os Pontos Críticos ativos de um Item
	 * 
	 * @param itemEstrutura
	 * @return Collection de Objetos PontoCriticoPtc
	 * @throws ECARException
	 */
	public Collection getAtivos(ItemEstruturaIett itemEstrutura) throws ECARException {
		try {
			Query query = this.getSession().createQuery(
			        "select ptc from PontoCriticoPtc ptc" + " where ptc.itemEstruturaIett.codIett = :codIett"
			                + " and (ptc.indExcluidoPtc <> 'S' or  ptc.indExcluidoPtc is null) " + " and ptc.indAtivoPtc = 'S'");
			query.setLong("codIett", itemEstrutura.getCodIett().longValue());
			return query.list();
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}
	
	/**
	 * Retorna os Pontos Críticos ativos de um Item, ordenados de forma decrescente pela data limite
	 * e filtrados pelo tipo caso informado.
	 * 
	 * @param itemEstrutura
	 * @param codSgaPontoCritico
	 * @return Collection de Objetos PontoCriticoPtc
	 * @throws ECARException
	 */
	public Collection getPontosCriticosAtivosOrdenadosDataLimite(ItemEstruturaIett itemEstrutura, String codSgaPontoCritico) throws ECARException {
		try {
			Query query = null;
			
			Collection pontosCriticos;
			
			//Pesquisa primeiro apenas pontos críticos com data limite
			if (codSgaPontoCritico == null || "".equals(codSgaPontoCritico)) {
				query = this.getSession().createQuery(
				        "select ptc from PontoCriticoPtc ptc" + " where ptc.itemEstruturaIett.codIett = :codIett"
				                + " and (ptc.indExcluidoPtc <> 'S' or  ptc.indExcluidoPtc is null) " + " and ptc.indAtivoPtc = 'S'"
				                + " and dataLimitePtc <> null order by dataLimitePtc desc ");
				query.setLong("codIett", itemEstrutura.getCodIett().longValue());
			}
			else {
				query = this.getSession().createQuery(
				        "select ptc from PontoCriticoPtc ptc" + " where ptc.itemEstruturaIett.codIett = :codIett"
				                + " and (ptc.indExcluidoPtc <> 'S' or  ptc.indExcluidoPtc is null) "
				                + " and ptc.indAtivoPtc = 'S' and ptc.sisAtributoTipo.codSatb = :codSatb"
				                + " and dataLimitePtc <> null order by dataLimitePtc desc ");
				query.setLong("codIett", itemEstrutura.getCodIett().longValue());
				query.setLong("codSatb", Long.valueOf(codSgaPontoCritico));
			}
			
			pontosCriticos = query.list();
			
			//Pesquisa pontos críticos sem data limite para que estes fiquem no fim da lista ordenada
			if (codSgaPontoCritico == null || "".equals(codSgaPontoCritico)) {
				query = this.getSession().createQuery(
				        "select ptc from PontoCriticoPtc ptc" + " where ptc.itemEstruturaIett.codIett = :codIett"
				                + " and (ptc.indExcluidoPtc <> 'S' or  ptc.indExcluidoPtc is null) " + " and ptc.indAtivoPtc = 'S'"
				                + " and dataLimitePtc = null ");
				query.setLong("codIett", itemEstrutura.getCodIett().longValue());
			}
			else {
				query = this.getSession().createQuery(
				        "select ptc from PontoCriticoPtc ptc" + " where ptc.itemEstruturaIett.codIett = :codIett"
				                + " and (ptc.indExcluidoPtc <> 'S' or  ptc.indExcluidoPtc is null) "
				                + " and ptc.indAtivoPtc = 'S' and ptc.sisAtributoTipo.codSatb = :codSatb"
				                + " and dataLimitePtc = null ");
				
				query.setLong("codIett", itemEstrutura.getCodIett().longValue());
				query.setLong("codSatb", Long.valueOf(codSgaPontoCritico));
			}
			
			pontosCriticos.addAll(query.list());
			
			return pontosCriticos;
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}
	/**
	 * Retorna os Pontos Críticos ativos de um Item, ordenados de forma decrescente pela data limite.
	 * 
	 * @param itemEstrutura
	 * @return Collection de Objetos PontoCriticoPtc
	 * @throws ECARException
	 */
	public Collection getPontosCriticosAtivosOrdenadosDataLimite(ItemEstruturaIett itemEstrutura, boolean recuperarPontosCriticosSemDataLimiteInformada) throws ECARException {
		try {
			Query query = null;			
			Collection pontosCriticos;
	
			//Pesquisa primeiro apenas pontos críticos com data limite
			query = this.getSession().createQuery(
			        "select ptc from PontoCriticoPtc ptc" + " where ptc.itemEstruturaIett.codIett = :codIett"
			                + " and (ptc.indExcluidoPtc <> 'S' or  ptc.indExcluidoPtc is null) " + " and ptc.indAtivoPtc = 'S'"
			                + " and dataLimitePtc <> null order by dataLimitePtc desc");
			query.setLong("codIett", itemEstrutura.getCodIett().longValue());
			
			pontosCriticos = query.list();
			
			if (recuperarPontosCriticosSemDataLimiteInformada) {
				//Pesquisa pontos críticos sem data limite para que estes fiquem no fim da lista ordenada
				query = this.getSession().createQuery(
				        "select ptc from PontoCriticoPtc ptc" + " where ptc.itemEstruturaIett.codIett = :codIett"
				                + " and (ptc.indExcluidoPtc <> 'S' or  ptc.indExcluidoPtc is null) " + " and ptc.indAtivoPtc = 'S'"
				                + " and dataLimitePtc = null");
				query.setLong("codIett", itemEstrutura.getCodIett().longValue());
				
				pontosCriticos.addAll(query.list());
			}
			return pontosCriticos;
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}
	
	/**
	 * Retorna os Pontos Críticos ativos de um Item
	 * 
	 * @param itemEstrutura
	 * @return Collection de Objetos PontoCriticoPtc
	 * @throws ECARException
	 */
	public Collection getPontosCriticosOrdenadoDataLimite(ItemEstruturaIett itemEstrutura, String ordem) throws ECARException {
		try {
			
			String ordenacao;
			
			if(ordem != null && ordem.equals("pontoCriticoCorPtccores")){
				//se o atributo escolhido com primeiro na lista são as cores
				//ordena pela descrição evitando um erro de consulta no hibernate
				//que faz que com o ecar seja derrubado.
				ordenacao = " order by ptc.descricaoPtc asc, ptc.indAtivoPtc desc, dataLimitePtc desc";
			}else if (ordem != null){
				ordenacao = " order by ptc."+ordem+" asc, ptc.indAtivoPtc desc, dataLimitePtc desc";
			}else {
				ordenacao = " order by ptc.indAtivoPtc desc, dataLimitePtc desc"; 
			}
			
			Query query = this.getSession().createQuery(
			        "select ptc from PontoCriticoPtc ptc" + " where ptc.itemEstruturaIett.codIett = :codIett"
			                + " and (ptc.indExcluidoPtc <> 'S' or  ptc.indExcluidoPtc is null) "
			                + ordenacao);
			query.setLong("codIett", itemEstrutura.getCodIett().longValue());
			return query.list();
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}

	public Collection getPontosCriticosOrdenadoDataLimite(ItemEstruturaIett itemEstrutura) throws ECARException {
		  return getPontosCriticosOrdenadoDataLimite(itemEstrutura, null);
	}
	
	/**
	 * Retorna os Pontos Críticos ativos de um Item
	 * 
	 * @param itemEstrutura
	 * @return Collection de Objetos PontoCriticoPtc
	 * @throws ECARException
	 */
	public Collection getPontosCriticosComExcluidosOrdenadoDataLimite(ItemEstruturaIett itemEstrutura) throws ECARException {
		try {
			Query query = this.getSession().createQuery(
			        "select ptc from PontoCriticoPtc ptc" + " where ptc.itemEstruturaIett.codIett = :codIett"			                
			                + " order by ptc.indAtivoPtc desc, dataLimitePtc desc ");
			query.setLong("codIett", itemEstrutura.getCodIett().longValue());
			return query.list();
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}
	
	/**
	 * Retorna os Pontos Críticos ativos de um Item
	 * 
	 * @param itemEstrutura
         * @param codSgaPontoCritico
         * @return Collection de Objetos PontoCriticoPtc
	 * @throws ECARException
	 */
	public Collection getAtivos(ItemEstruturaIett itemEstrutura, String codSgaPontoCritico) throws ECARException {
		try {
			Query query = null;
			if (codSgaPontoCritico == null || "".equals(codSgaPontoCritico)) {
				query = this.getSession().createQuery(
				        "select ptc from PontoCriticoPtc ptc" + " where ptc.itemEstruturaIett.codIett = :codIett"
				                + " and (ptc.indExcluidoPtc <> 'S' or  ptc.indExcluidoPtc is null) " + " and ptc.indAtivoPtc = 'S'");
				query.setLong("codIett", itemEstrutura.getCodIett().longValue());
			}
			else {
				query = this.getSession().createQuery(
				        "select ptc from PontoCriticoPtc ptc" + " where ptc.itemEstruturaIett.codIett = :codIett"
				                + " and (ptc.indExcluidoPtc <> 'S' or  ptc.indExcluidoPtc is null) "
				                + " and ptc.indAtivoPtc = 'S' and ptc.sisAtributoTipo.codSatb = :codSatb");
				query.setLong("codIett", itemEstrutura.getCodIett().longValue());
				query.setLong("codSatb", Long.valueOf(codSgaPontoCritico));
			}
			return query.list();
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}
	
	/**
	 * Retorna os Pontos Críticos inativos de um Item
	 * 
	 * @param itemEstrutura
	 * @return Collection de Objetos PontoCriticoPtc
	 * @throws ECARException
	 */
	public Collection getInativos(ItemEstruturaIett itemEstrutura) throws ECARException {
		try {
			Query query = this.getSession().createQuery(
			        "select ptc from PontoCriticoPtc ptc" + " where ptc.itemEstruturaIett.codIett = :codIett"
			                + " and (ptc.indExcluidoPtc <> 'S' or  ptc.indExcluidoPtc is null) " + " and ptc.indAtivoPtc != 'S'");
			query.setLong("codIett", itemEstrutura.getCodIett().longValue());
			return query.list();
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}
	
	/**
	 * Constrói um objeto PontoCritico a partir de atributos passados no request
	 * 
	 * @param request
	 * @param pontoCritico
	 * @throws ECARException
	 */
	public void setPontoCritico(HttpServletRequest request, PontoCriticoPtc pontoCritico) throws ECARException {
		ItemEstruturaIett itemEstrutura = null;
		/*
		 * Por padrão o ponto crítico é não excluído. Se não colocar assim salva
		 * como nulo
		 */
		pontoCritico.setIndExcluidoPtc(Pagina.NAO);
		if (!Pagina.getParamStr(request, "codIett").trim().equals("")) {
			itemEstrutura = (ItemEstruturaIett) this.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
			pontoCritico.setItemEstruturaIett(itemEstrutura);
		}
//		if (!"".equals(Pagina.getParamStr(request, "codArel"))) {
//			AcompRelatorioArel acompRelatorioArel = (AcompRelatorioArel) this.buscar(AcompRelatorioArel.class, Long.valueOf(Pagina.getParamStr(request,
//			        "codArel")));
//			pontoCritico.setAcompRelatorioArel(acompRelatorioArel);
//			if (itemEstrutura == null) {
//				itemEstrutura = acompRelatorioArel.getAcompReferenciaItemAri().getItemEstruturaIett();
//			}
//		}

    	//30/01/2010 - areal setado sempre nulo para não ocorrer erro na geração de períodos. Esta ligação não deveria existir
    	pontoCritico.setAcompRelatorioArel(null);

		if (!"".equals(Pagina.getParamStr(request, "dataIdentificacaoPtc")))
			pontoCritico.setDataIdentificacaoPtc(Pagina.getParamDataBanco(request, "dataIdentificacaoPtc"));
		// if(!"".equals(Pagina.getParamStr(request, "cod")))
		// pontoCritico.setCodPtc(Long.valueOf(Pagina.getParamStr(request,
		// "cod")));
		pontoCritico.setDescricaoPtc(Pagina.getParamStr(request, "descricaoPtc"));
//		if (!"".equals(Pagina.getParamStr(request, "dataLimitePtc")))
			pontoCritico.setDataLimitePtc(Pagina.getParamDataBanco(request, "dataLimitePtc"));
		pontoCritico.setDataSolucaoPtc(Pagina.getParamDataBanco(request, "dataSolucaoPtc"));
		pontoCritico.setDescricaoSolucaoPtc(Pagina.getParamStr(request, "descricaoSolucaoPtc"));
		pontoCritico.setIndAmbitoInternoGovPtc(Pagina.getParamStr(request, "indAmbitoInternoGovPtc"));

		//Colocar o valor da request para indAtivoPtc caso o atributo esteja configurado na estrutura
		//caso constrário, colocar o valor default "S" para que o usuário não inclua pontos críticos já
		//não ativos e estes não serem visualizados no gráfico de gantt
		String indAtivoPtc = Pagina.getParamStr(request, "indAtivoPtc");
				
		AtributosAtb atributo = new AtributosAtb();
		atributo.setNomeAtb("indAtivoPtc");		
		atributo = (AtributosAtb) new AtributoDao(request).pesquisar(atributo, new String[]{"codAtb", "Asc"}).get(0);		
		
		EstruturaAtributoEttat estruturaAtributo = new EstruturaAtributoEttat();
		estruturaAtributo.setAtributosAtb(atributo);		
		estruturaAtributo.setEstruturaEtt(pontoCritico.getItemEstruturaIett().getEstruturaEtt());		
		List estruturaAtributos = new EstruturaAtributoDao(request).pesquisar(estruturaAtributo, new String[]{"", ""});
		
		if (estruturaAtributos != null && estruturaAtributos.size() > 0){
			pontoCritico.setIndAtivoPtc(indAtivoPtc);
		}		
		else{
			pontoCritico.setIndAtivoPtc("S");
		}
		
		if (!"".equals(Pagina.getParamStr(request, "codSgaPontoCritico"))){
			pontoCritico.setSisAtributoTipo((SisAtributoSatb) this.buscar(SisAtributoSatb.class, Long.valueOf(Pagina.getParam(request, "codSgaPontoCritico"))));
		} else {
			pontoCritico.setSisAtributoTipo(null);
		}
		if ((!"".equals(Pagina.getParamStr(request, "usuarioUsu")))) {
			UsuarioUsu usuario = (UsuarioUsu) this.buscar(UsuarioUsu.class, Long.valueOf(Pagina.getParamStr(request, "usuarioUsu")));
			pontoCritico.setUsuarioUsu(usuario);
		}
		else {
			pontoCritico.setUsuarioUsu(null);
		}
		this.setPontoCriticoCor(request, pontoCritico);
		// chama o método para setar os atributos livres do ponto crítico
		this.setAtributosLivresPontoCritico(request, pontoCritico);
	}
	
	/**
	 * Constrói um objeto PontoCritico a partir de atributos passados na lista
	 * 
	 * @param request
	 * @param pontoCritico
	 * @throws ECARException
	 */
	// public void setPontoCritico(HttpServletRequest request, List campos,
	// PontoCriticoPtc pontoCritico) throws ECARException{
	// try {
	// ItemEstruturaIett itemEstrutura = null;
	//	        
	// itemEstrutura = (ItemEstruturaIett) this.buscar(
	// ItemEstruturaIett.class,
	// Long.valueOf(FileUpload.verificaValorCampo(campos,"codIett")));
	//	       
	// pontoCritico.setItemEstruturaIett(itemEstrutura);
	//	        
	// if (!"".equals(FileUpload.verificaValorCampo(campos, "codArel"))) {
	// AcompRelatorioArel acompRelatorioArel = (AcompRelatorioArel) this.buscar(
	// AcompRelatorioArel.class,
	// Long.valueOf(FileUpload.verificaValorCampo(campos,"codArel")));
	// pontoCritico.setAcompRelatorioArel(acompRelatorioArel);
	// }
	//	        
	// if(!"".equals(FileUpload.verificaValorCampo(campos, "cod")))
	// pontoCritico.setCodPtc(Long.valueOf(FileUpload.verificaValorCampo(campos,
	// "cod")));
	//	        
	// if(!"".equals(FileUpload.verificaValorCampo(campos,
	// "dataIdentificacaoPtc")))
	// pontoCritico.setDataIdentificacaoPtc(FileUpload.verificaValorCampoDataBanco(campos,
	// "dataIdentificacaoPtc"));
	//	      
	// pontoCritico.setDescricaoPtc(FileUpload.verificaValorCampo(campos,
	// "descricaoPtc"));
	//	        
	// if(!"".equals(FileUpload.verificaValorCampo(campos, "dataLimitePtc")))
	// pontoCritico.setDataLimitePtc(FileUpload.verificaValorCampoDataBanco(campos,
	// "dataLimitePtc"));
	//	        
	// pontoCritico.setDataSolucaoPtc(FileUpload.verificaValorCampoDataBanco(campos,
	// "dataSolucaoPtc"));
	//	        
	// pontoCritico.setDescricaoSolucaoPtc(FileUpload.verificaValorCampo(campos,
	// "descricaoSolucaoPtc"));
	//	        
	// pontoCritico.setIndAmbitoInternoGovPtc(FileUpload.verificaValorCampo(campos,
	// "indAmbitoInternoGovPtc"));
	//	        
	// pontoCritico.setIndAtivoPtc(FileUpload.verificaValorCampo(campos,
	// "indAtivoPtc"));
	//	        
	// if (!"".equals(FileUpload.verificaValorCampo(campos,
	// "codSgaPontoCritico")))
	// pontoCritico
	// .setSisAtributoTipo((SisAtributoSatb) this
	// .buscar(SisAtributoSatb.class, Long
	// .valueOf(FileUpload.verificaValorCampo(campos,
	// "codSgaPontoCritico"))));
	//	
	//	        
	// if((!"".equals(FileUpload.verificaValorCampo(campos, "codUsu"))) &&
	// (!"".equals(FileUpload.verificaValorCampo(campos, "dataSolucaoPtc")))){
	// UsuarioUsu usuario = (UsuarioUsu) this.buscar(UsuarioUsu.class,
	// Long.valueOf(FileUpload.verificaValorCampo(campos, "codUsu")));
	// pontoCritico.setUsuarioUsu(usuario);
	// } else {
	// pontoCritico.setUsuarioUsu(null);
	// }
	//	        
	// this.setPontoCriticoCor(request, campos, pontoCritico);
	//	        
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	// }
	/**
	 * Altera um objeto pontoCritico e salva os registros filhos passados na
	 * List
	 * 
         * @param pontoCritico
         * @param usuario
         * @param request
         * @param historico
	 * @throws Exception 
	 */
	public void alterar(PontoCriticoPtc pontoCritico, HttpServletRequest request, UsuarioUsu usuario, HistoricoPtc historico) throws Exception {
		Transaction tx = null;
		HistoricoPontoCriticoPtc pojoHistorico = new HistoricoPontoCriticoPtc();
		PontoCriticoPtc ptcNegocio = (PontoCriticoPtc) buscar(PontoCriticoPtc.class, pontoCritico.getCodPtc());
		try {
			tx = session.beginTransaction();
			ArrayList objetos = new ArrayList();
			super.inicializarLogBean();
			PontoCriticoPtc pontoCriticoOld = (PontoCriticoPtc) buscar(PontoCriticoPtc.class, Long.valueOf(pontoCritico.getCodPtc()));
			/*** Historico ***/
			historico.gerarHistorico(pontoCriticoOld);
			/*** Historico ***/
			/* apaga os filhos para serem gravados novamente */
			if (pontoCritico.getPontoCriticoCorPtccores() != null) {
				Iterator itAtb = pontoCritico.getPontoCriticoCorPtccores().iterator();
				while (itAtb.hasNext()) {
					PontocriticoCorPtccor ptcCor = (PontocriticoCorPtccor) itAtb.next();
					session.delete(ptcCor);
					objetos.add(ptcCor);
				}
			}
			else
				pontoCritico.setPontoCriticoCorPtccores(new HashSet<PontocriticoCorPtccor>());
			pontoCritico.setPontoCriticoCorPtccores(null);
			/* apaga os filhos para serem gravados novamente */
			if (pontoCritico.getPontoCriticoSisAtributoPtcSatbs() != null && !pontoCritico.getPontoCriticoSisAtributoPtcSatbs().isEmpty()) {
				Iterator itAtb = pontoCritico.getPontoCriticoSisAtributoPtcSatbs().iterator();
				while (itAtb.hasNext()) {
					PontoCriticoSisAtributoPtcSatb pontoCriticoSisAtributoPtcSatb = (PontoCriticoSisAtributoPtcSatb) itAtb.next();
					
                    if(pontoCriticoSisAtributoPtcSatb.getSisAtributoSatb().getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg() == Input.IMAGEM){
                    	
                    	String nomeCampo = request.getParameter("a" + pontoCriticoSisAtributoPtcSatb.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga().toString());
                    	
                    	if (nomeCampo != null && nomeCampo.equals("")){
                    		
	    		    		String fullFile = pontoCriticoSisAtributoPtcSatb.getInformacao();
	    		    		
	    		    		if (fullFile.lastIndexOf("=") != -1)     
	    		    			fullFile = fullFile.substring(fullFile.lastIndexOf("=") + 1);
    		    		
	    		    		File f = new File(fullFile);
	    		    		if( f.exists() ) 
	    		    			FileUpload.apagarArquivo(fullFile);
                    	}
                    }
					
					session.delete(pontoCriticoSisAtributoPtcSatb);
					objetos.add(pontoCriticoSisAtributoPtcSatb);
				}
			}
			else
				pontoCritico.setPontoCriticoSisAtributoPtcSatbs(new HashSet<PontoCriticoSisAtributoPtcSatb>());
			pontoCritico.setPontoCriticoSisAtributoPtcSatbs(null);
			this.setPontoCritico(request, pontoCritico);
			pontoCritico.setUsuarioUsuByCodUsuUltManutPtc(usuario);
			pontoCritico.setDataUltManutencaoPtc(Data.getDataAtual());
			List filhos = new ArrayList();
			if (pontoCritico.getPontoCriticoCorPtccores() != null) {
				filhos.addAll(pontoCritico.getPontoCriticoCorPtccores());
			}
			if (pontoCritico.getPontoCriticoSisAtributoPtcSatbs() != null) {
				for (Object object : pontoCritico.getPontoCriticoSisAtributoPtcSatbs()) {
					PontoCriticoSisAtributoPtcSatb ptcSatb = (PontoCriticoSisAtributoPtcSatb) object;
					ptcSatb.gerarTiposValores();
				}
				filhos.addAll(pontoCritico.getPontoCriticoSisAtributoPtcSatbs());
			}
			session.update(pontoCritico);
			objetos.add(pontoCritico);
			Iterator it = filhos.iterator();
			while (it.hasNext()) {
				PaiFilho object = (PaiFilho) it.next();
				object.atribuirPKPai();
				// salva os filhos
				session.save(object);
				objetos.add(object);
			}
			tx.commit();
			session.merge(pontoCritico);
			pojoHistorico.carregar(pontoCritico);
			tx = session.beginTransaction();
			gerarHistorico(pojoHistorico, Historico.ALTERAR, ((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
			tx.commit();
			if (super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC_ALT_EXC");
				Iterator itObj = objetos.iterator();
				while (itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
		}
		catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				}
				catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		catch (ECARException e) {
			this.logger.error(e);
			if (tx != null)
				try {
					tx.rollback();
				}
				catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			throw e;
		}
	}
	
        /**
         *
         * @param atbLivre
         * @param tiposValores
         * @param funcao
         */
        public void copiarTipoValorSemID(PontoCriticoSisAtributoPtcSatb atbLivre, Set<TipoValor> tiposValores, FuncaoFun funcao) {
		Set listaTiposValores = new HashSet();
		for (TipoValor tipoValor : tiposValores) {
			TipoValor tipoValorInner = new TipoValor();
			tipoValorInner.setConteudo(tipoValor.getConteudo());
			tipoValorInner.setPontoCriticoSisAtributo(atbLivre);
			tipoValorInner.setTipo(tipoValor.getTipo());
			tipoValorInner.setFuncao(funcao);
			listaTiposValores.add(tipoValorInner);
		}
		atbLivre.setTiposValores(listaTiposValores);
	}
	
	/**
	 * Altera um objeto estrutura e salva os registros filhos passados na List
	 * 
	 * @param usuario
	 * @throws ECARException
	 */
	// public void alterar(PontoCriticoPtc pontoCritico, HttpServletRequest
	// request, List campos, UsuarioUsu usuario, HistoricoPtc historico)
	// throws ECARException {
	// Transaction tx = null;
	//
	// try {
	// ArrayList objetos = new ArrayList();
	// super.inicializarLogBean();
	//
	// tx = session.beginTransaction();
	//
	// PontoCriticoPtc pontoCriticoOld = (PontoCriticoPtc) buscar(
	// PontoCriticoPtc.class, Long.valueOf(pontoCritico.getCodPtc()));
	//            
	// /***Historico***/
	// historico.gerarHistorico(pontoCriticoOld);
	// /***Historico***/
	//			
	// /* apaga os filhos para serem gravados novamente */
	// if (pontoCritico.getPontoCriticoCorPtccores() != null) {
	// Iterator itAtb = pontoCritico.getPontoCriticoCorPtccores().iterator();
	// while (itAtb.hasNext()) {
	// PontocriticoCorPtccor ptcCor = (PontocriticoCorPtccor) itAtb
	// .next();
	// session.delete(ptcCor);
	// objetos.add(ptcCor);
	// }
	// }
	// pontoCritico.setPontoCriticoCorPtccores(null);
	//			
	// /* apaga os filhos para serem gravados novamente */
	// if (pontoCritico.getPontoCriticoSisAtributoPtcSatbs() != null) {
	// Iterator itAtb =
	// pontoCritico.getPontoCriticoSisAtributoPtcSatbs().iterator();
	// while (itAtb.hasNext()) {
	// PontoCriticoSisAtributoPtcSatb pontoCriticoSisAtributoPtcSatb =
	// (PontoCriticoSisAtributoPtcSatb) itAtb
	// .next();
	// session.delete(pontoCriticoSisAtributoPtcSatb);
	// objetos.add(pontoCriticoSisAtributoPtcSatb);
	// }
	// }
	// pontoCritico.setPontoCriticoSisAtributoPtcSatbs(null);
	//			
	// this.setPontoCritico(request, campos, pontoCritico);
	//
	// pontoCritico.setUsuarioUsuByCodUsuUltManutPtc(usuario);
	// pontoCritico.setDataUltManutencaoPtc(Data.getDataAtual());
	//			
	// List filhos = new ArrayList();
	// if (pontoCritico.getPontoCriticoCorPtccores() != null)
	// filhos.addAll(pontoCritico.getPontoCriticoCorPtccores());
	//			
	// session.update(pontoCritico);
	// objetos.add(pontoCritico);
	//
	// Iterator it = filhos.iterator();
	//
	// while (it.hasNext()) {
	// PaiFilho object = (PaiFilho) it.next();
	// object.atribuirPKPai();
	// // salva os filhos
	// session.save(object);
	// objetos.add(object);
	// }
	//
	// tx.commit();
	//
	// if (super.logBean != null) {
	// super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
	// super.logBean.setOperacao("INC_ALT_EXC");
	// Iterator itObj = objetos.iterator();
	//
	// while (itObj.hasNext()) {
	// super.logBean.setObj(itObj.next());
	// super.loggerAuditoria.info(logBean.toString());
	// }
	// }
	//
	// } catch (HibernateException e) {
	// if (tx != null)
	// try {
	// tx.rollback();
	// } catch (HibernateException r) {
	// this.logger.error(r);
	// throw new ECARException("erro.hibernateException");
	// }
	// this.logger.error(e);
	// throw new ECARException("erro.hibernateException");
	// } catch (ECARException e) {
	// this.logger.error(e);
	// if (tx != null)
	// try {
	// tx.rollback();
	// } catch (HibernateException r) {
	// this.logger.error(r);
	// throw new ECARException("erro.hibernateException");
	// }
	// throw e;
	// }
	// }
	/**
	 * Método para criar a coleção de PtcCor a partir de parâmetros passados por
	 * request
	 * 
	 * @param request
	 * @param pontoCritico
	 * @throws ECARException
	 */
	public void setPontoCriticoCor(HttpServletRequest request, PontoCriticoPtc pontoCritico) throws ECARException {
		PontocriticoCorPtccor ptcCor = null;
		List setCores = new CorDao(request).listar(Cor.class, new String[] { "ordemCor", "asc" });
		Cor cor = null;
		Iterator itCores = null;
		if (setCores != null)
			itCores = setCores.iterator();
		while (itCores.hasNext()) {
			cor = (Cor) itCores.next();
			ptcCor = new PontocriticoCorPtccor();
			ptcCor.setCor(cor);
			ptcCor.setPontoCriticoPtc(pontoCritico);
			if(request.getParameter("ant_" + cor.getCodCor() )!=null && !"".equals(request.getParameter("ant_" + cor.getCodCor() ))){
				ptcCor.setAntecedenciaPrimEmailPtccor(Integer.valueOf(Pagina.getParamInt(request, "ant_" + cor.getCodCor())));
			}
			if(request.getParameter("freq_" + cor.getCodCor())!=null && !"".equals(request.getParameter("freq_" + cor.getCodCor()))){
				ptcCor.setFrequenciaEnvioEmailPtccor(Integer.valueOf(Pagina.getParamInt(request, "freq_" + cor.getCodCor())));
			}
			
			ptcCor.setIndAtivoEnvioEmailPtccor("S".equals(Pagina.getParamStr(request, "ativo_" + cor.getCodCor()).trim()) ? "S" : "N");
			if (pontoCritico.getPontoCriticoCorPtccores() == null)
				pontoCritico.setPontoCriticoCorPtccores(new HashSet());
			pontoCritico.getPontoCriticoCorPtccores().add(ptcCor);
		}
	}
	
	/**
	 * Método para criar a coleção de PtcCor a partir de parâmetros passados por
	 * request
	 * 
	 * @param request
	 * @param pontoCritico
	 * @throws ECARException
	 */
	// public void setPontoCriticoCor(HttpServletRequest request, List campos,
	// PontoCriticoPtc pontoCritico) throws ECARException {
	// try {
	//			
	//		
	// PontocriticoCorPtccor ptcCor = null;
	// List setCores = new CorDao(request).listar(Cor.class, new
	// String[]{"ordemCor","asc"});
	// Cor cor = null;
	// Iterator itCores = null;
	//	
	// if (setCores != null)
	// itCores = setCores.iterator();
	//			
	// while (itCores.hasNext())
	// {
	// cor = (Cor) itCores.next();
	//				
	// ptcCor = new PontocriticoCorPtccor();
	// ptcCor.setCor(cor);
	// ptcCor.setPontoCriticoPtc(pontoCritico);
	// ptcCor.setAntecedenciaPrimEmailPtccor(Integer.valueOf(FileUpload.verificaValorCampo(campos,
	// "ant_"+ cor.getCodCor())));
	// ptcCor.setFrequenciaEnvioEmailPtccor(Integer.valueOf(FileUpload.verificaValorCampo(campos,
	// "freq_"+ cor.getCodCor())));
	// ptcCor.setIndAtivoEnvioEmailPtccor("S".equals(FileUpload.verificaValorCampo(campos,
	// "ativo["+ cor.getCodCor() +"]").trim())?"S":"N");
	//				
	// if (pontoCritico.getPontoCriticoCorPtccores() == null)
	// pontoCritico.setPontoCriticoCorPtccores(new HashSet());
	// pontoCritico.getPontoCriticoCorPtccores().add(ptcCor);
	// }
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	// }
	/**
	 * Recebe um Array com Códigos de Pontos Criticos. Para cada um dos Pontos
	 * Criticos verifica a existência de apontamentos. Se não existirem exclui o
	 * Ponto Crítico. Se existirem, dispara escobrir quem serão os vilões do
	 * próximo longa. Em entrevista à MTV, Michael Caine, o mordomo Alfred, bota
	 * lenha na fogueira.uma Exception
	 * 
	 * @param codigosParaExcluir
         * @param usuario
         * @throws ECARException
	 */
	public void excluir(String[] codigosParaExcluir, UsuarioUsu usuario) throws ECARException {
		Transaction tx = null;
		HistoricoPontoCriticoPtc pojoHistorico = new HistoricoPontoCriticoPtc();
		try {
			super.inicializarLogBean();
			tx = session.beginTransaction();
			List<PontoCriticoPtc> listaPontoCriticoPtc = new ArrayList<PontoCriticoPtc>();
			for (int i = 0; i < codigosParaExcluir.length; i++) {
				/*if(codigosParaExcluir[i].indexOf(",") != -1){
					throw new ECARException("itemEstrutura.pontoCritico.exclusao.jaExcluidos");
				}*/				
				PontoCriticoPtc pontoCritico = (PontoCriticoPtc) this.buscar(PontoCriticoPtc.class, Long.valueOf(codigosParaExcluir[i]));
				if (pontoCritico.getIndExcluidoPtc() != null && "S".equals(pontoCritico.getIndExcluidoPtc())) {
					throw new ECARException("itemEstrutura.pontoCritico.exclusao.jaExcluidos");
				}
				listaPontoCriticoPtc.add(pontoCritico);
			}
			
			for (PontoCriticoPtc pontoCritico : listaPontoCriticoPtc) {			
			//for (int i = 0; i < codigosParaExcluir.length; i++) {
				//PontoCriticoPtc pontoCritico = (PontoCriticoPtc) this.buscar(PontoCriticoPtc.class, Long.valueOf(codigosParaExcluir[i]));
				if (pontoCritico.getApontamentoApts() != null && pontoCritico.getApontamentoApts().size() > 0) {
					throw new ECARException("itemEstrutura.pontoCritico.exclusao.possuiApontamentos");
				}
				else {
					// PontoCriticoPtc old = (PontoCriticoPtc)
					// pontoCritico.clone();
					PontoCriticoPtc old = (PontoCriticoPtc) buscar(PontoCriticoPtc.class, Long.valueOf(pontoCritico.getCodPtc()));
					pontoCritico.setDataUltManutencaoPtc(Data.getDataAtual());
					pontoCritico.setUsuarioUsuByCodUsuUltManutPtc(usuario);
					pontoCritico.setIndExcluidoPtc(Dominios.SIM);
					/******** Historico *********/
					HistoricoPtc historico = new HistoricoPtc(pontoCritico, HistoricoPtc.exclusao, session, new ConfiguracaoDao(request), request);
					historico.gerarHistorico(old);
					/******** Historico *********/
					pojoHistorico.carregar(pontoCritico);
					if (pontoCritico.getPontoCriticoCorPtccores() == null)
						pojoHistorico.setPontoCriticoCorPtccores(new HashSet<PontocriticoCorPtccor>());
					if (pontoCritico.getPontoCriticoSisAtributoPtcSatbs() == null)
						pojoHistorico.setPontoCriticoSisAtributoPtcSatbs(new HashSet<PontoCriticoSisAtributoPtcSatb>());
					gerarHistorico(pojoHistorico, Historico.EXCLUIR, ((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());//pojoHistorico.getUsuarioUsu());//((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
					session.update(pontoCritico);
				}
			}
			tx.commit();
			if (super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("EXC");
			}
		}
		catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				}
				catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}
	
	/**
	 * Salva um registro de POnto Crítico
	 * 
	 * @param pontoCritico
         * @param usuarioUsu
         * @throws ECARException
	 */
	public void salvar(PontoCriticoPtc pontoCritico, UsuarioUsu usuarioUsu) throws ECARException {
		super.inicializarLogBean();
		HistoricoPontoCriticoPtc pojoHistorico = new HistoricoPontoCriticoPtc();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			pontoCritico.setDataInclusaoPtc(Data.getDataAtual());
			pontoCritico.setUsuarioUsuInclusao(usuarioUsu);
			pontoCritico.setIndExcluidoPtc("N");
			session.save(pontoCritico);
			pojoHistorico.carregar(pontoCritico);
			List filhos = new ArrayList();
			if (pontoCritico.getPontoCriticoCorPtccores() != null) {
				filhos.addAll(pontoCritico.getPontoCriticoCorPtccores());
			}
			if (pontoCritico.getPontoCriticoSisAtributoPtcSatbs() != null) {
				filhos.addAll(pontoCritico.getPontoCriticoSisAtributoPtcSatbs());
			}
			if (pontoCritico.getPontoCriticoSisAtributoPtcSatbs() != null) {
				Set listaPtcSisAtributo = pontoCritico.getPontoCriticoSisAtributoPtcSatbs();
				// Código antigo
				// filhos.addAll(pontoCritico.getPontoCriticoSisAtributoPtcSatbs());
				filhos.addAll(listaPtcSisAtributo);
				FuncaoDao funcaoDao = new FuncaoDao(request);
				FuncaoFun funcao = funcaoDao.getFuncaoPontosCriticos();
				for (Iterator it = listaPtcSisAtributo.iterator(); it.hasNext();) {
					PontoCriticoSisAtributoPtcSatb ptcSisAtributo = (PontoCriticoSisAtributoPtcSatb) it.next();
					SisAtributoSatb sisAtributo = ptcSisAtributo.getSisAtributoSatb();
					// Caso o tipo de validação seja uma das três abaixo, será
					// gerado um código incremental.
					if (sisAtributo != null && (sisAtributo.isAtributoAutoIcremental() || sisAtributo.isAtributoContemMascara())) {
						ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request, session);
						itemEstruturaDao.geraValorIncremental(ptcSisAtributo, sisAtributo, funcao, ptcSisAtributo.getPontoCriticoPtc().getItemEstruturaIett()
						        .getEstruturaEtt(), null);
					}
				}
			}
			Iterator it = filhos.iterator();
			while (it.hasNext()) {
				PaiFilho object = (PaiFilho) it.next();
				object.atribuirPKPai();
				// salva os filhos
				session.save(object);
				// objetosInseridos.add(object);
			}
			gerarHistorico(pojoHistorico, Historico.INCLUIR, ((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
			if (logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("INC");
				Iterator it2 = filhos.iterator();
				while (it2.hasNext()) {
					logBean.setObj(it2.next());
					loggerAuditoria.info(logBean.toString());
				}
			}
			tx.commit();
		}
		catch (ECARException e){
			if (tx != null)
				try {
					tx.rollback();
				}
				catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			throw e; 
		}
		catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				}
				catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}
	
	/**
	 * @param item
	 * @return Collection
	 * @throws ECARException
	 */
	public Collection getPontosCriticosSolucionadosOrdenadoDataLimite(ItemEstruturaIett item, String ordem) throws ECARException {
		try {
			
			String ordenacao;
			
			if (ordem != null){
				
				ordenacao = " order by ptc."+ordem+" asc, ptc.indAtivoPtc desc, dataLimitePtc desc"; 
			}
			else
			{
				ordenacao = " order by ptc.indAtivoPtc desc, dataLimitePtc desc "; 
			}

			
			Query query = this.getSession().createQuery(
			        "select ptc from PontoCriticoPtc ptc" + " where ptc.itemEstruturaIett.codIett = :codIett" + " and ptc.itemEstruturaIett.indAtivoIett = 'S'"
			                + " and (ptc.indExcluidoPtc <> 'S' or  ptc.indExcluidoPtc is null) "
			                + " and ptc.dataSolucaoPtc is not null " + ordenacao);
			query.setLong("codIett", item.getCodIett().longValue());
			return query.list();
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}
	
	public Collection getPontosCriticosSolucionadosOrdenadoDataLimite(ItemEstruturaIett item) throws ECARException {
		return getPontosCriticosSolucionadosOrdenadoDataLimite(item, null);
	}
	
	/**
	 * @param item
	 * @return Collection
	 * @throws ECARException
	 */
	public Collection getPontosCriticosSolucionados(ItemEstruturaIett item) throws ECARException {
		try {
			Query query = this.getSession().createQuery(
			        "select ptc from PontoCriticoPtc ptc" + " where ptc.itemEstruturaIett.codIett = :codIett" + " and ptc.itemEstruturaIett.indAtivoIett = 'S'"
			                + " and (ptc.indExcluidoPtc <> 'S' or  ptc.indExcluidoPtc is null) " + " and ptc.indAtivoPtc = 'S'"
			                + " and ptc.dataSolucaoPtc is not null");
			query.setLong("codIett", item.getCodIett().longValue());
			return query.list();
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}
	
	/**
	 * @param item
	 * @return Collection
	 * @throws ECARException
	 */
	public Collection getPontosCriticosNaoSolucionadosOrdenadoDataLimite(ItemEstruturaIett item, String ordem) throws ECARException {
		try {
			
			String ordenacao;
			
			if (ordem != null){
				
				ordenacao = " order by ptc."+ordem+" asc, ptc.indAtivoPtc desc, dataLimitePtc desc"; 
			}
			else
			{
				ordenacao = " order by ptc.indAtivoPtc desc, dataLimitePtc desc "; 
			}
			
			
			Query query = this.getSession().createQuery(
			        "select ptc from PontoCriticoPtc ptc" + " where ptc.itemEstruturaIett.codIett = :codIett" + " and ptc.itemEstruturaIett.indAtivoIett = 'S'"
			                + " and (ptc.indExcluidoPtc <> 'S' or  ptc.indExcluidoPtc is null) "
			                + " and ptc.dataSolucaoPtc is null " + ordenacao);
			query.setLong("codIett", item.getCodIett().longValue());
			return query.list();
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}
	
	public Collection getPontosCriticosNaoSolucionadosOrdenadoDataLimite(ItemEstruturaIett item) throws ECARException {
		return 	getPontosCriticosNaoSolucionadosOrdenadoDataLimite(item, null);
	}
	
	/**
	 * @param item
	 * @return Collection
	 * @throws ECARException
	 */
	public Collection getPontosCriticosNaoSolucionados(ItemEstruturaIett item) throws ECARException {
		try {
			Query query = this.getSession().createQuery(
			        "select ptc from PontoCriticoPtc ptc" + " where ptc.itemEstruturaIett.codIett = :codIett" + " and ptc.itemEstruturaIett.indAtivoIett = 'S'"
			                + " and (ptc.indExcluidoPtc <> 'S' or  ptc.indExcluidoPtc is null) " + " and ptc.indAtivoPtc = 'S'"
			                + " and ptc.dataSolucaoPtc is null");
			query.setLong("codIett", item.getCodIett().longValue());
			return query.list();
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}
	
	/**
	 * Verifica datas ultrapassadas
	 * 
	 * @param lista
	 * @return boolean
	 */
	public boolean verificaDatasUltrapassadas(Collection lista) {
		// verificar os pontos críticos
		if (lista != null && lista.size() > 0) {
			Iterator it = lista.iterator();
			while (it.hasNext()) {
				PontoCriticoPtc pontoCritico = (PontoCriticoPtc) it.next();
				Calendar dataAtual = Calendar.getInstance();
				dataAtual.clear(Calendar.HOUR);
				dataAtual.clear(Calendar.HOUR_OF_DAY);
				dataAtual.clear(Calendar.MINUTE);
				dataAtual.clear(Calendar.SECOND);
				dataAtual.clear(Calendar.MILLISECOND);
				dataAtual.clear(Calendar.AM_PM);
				if (pontoCritico.getDataLimitePtc()!=null) {
					Calendar dataLimite = Calendar.getInstance();
					dataLimite.setTime(pontoCritico.getDataLimitePtc());
					dataLimite.clear(Calendar.HOUR);
					dataLimite.clear(Calendar.HOUR_OF_DAY);
					dataLimite.clear(Calendar.MINUTE);
					dataLimite.clear(Calendar.SECOND);
					dataLimite.clear(Calendar.MILLISECOND);
					dataLimite.clear(Calendar.AM_PM);
					if (dataAtual.after(dataLimite) && pontoCritico.getDataSolucaoPtc() == null) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * @param pontoCritico
	 * @param data
	 * @return String[]
	 * @throws ECARException
	 */
	public String[] getRelogioNaData(PontoCriticoPtc pontoCritico, Date data) throws ECARException {
		String corRelogio = "Branco";
		String descRelogio = "Data registrada (Nenhuma ação requerida)";
		int diasAntecedencia;
		Date dataCorrente = data;
		String dataCorrStr = Data.parseDate(dataCorrente);
		Date dataLimitePtc = pontoCritico.getDataLimitePtc();
		String dataLimiteStr = Data.parseDate(dataLimitePtc);
		if ("S".equals(pontoCritico.getIndAtivoPtc())) {
			if (pontoCritico.getDataSolucaoPtc() != null) {
				corRelogio = "Checked";
				descRelogio = "Solucionado";
			}
			else
				if (dataLimitePtc != null) {
					if ((dataLimitePtc.before(dataCorrente)) || (dataLimiteStr.equals(dataCorrStr))) {
						corRelogio = "PretoFixo";
						descRelogio = "Data Limite Ultrapassada";
					}
					// Caso contrário, itera-se buscando qual o período de envio
					// visando descobrir qual o período mais 'grave' que se
					// encaixa
					// nos dias de antecedência que restam
					else {
						List listPtcCor = this.ordenaSet(pontoCritico.getPontoCriticoCorPtccores(), "this.antecedenciaPrimEmailPtccor", "asc");
						Iterator itPtcCor = listPtcCor.iterator();
						boolean encontrouLimite = false;
						while ((itPtcCor.hasNext()) && (!encontrouLimite)) {
							PontocriticoCorPtccor ptcCor = (PontocriticoCorPtccor) itPtcCor.next();
							if (ptcCor.getAntecedenciaPrimEmailPtccor() == null)
								diasAntecedencia = 0;
							else
								diasAntecedencia = ptcCor.getAntecedenciaPrimEmailPtccor().intValue();
							Date dataComparacao = Data.addDias(diasAntecedencia, dataCorrente);
							String dataCompStr = Data.parseDate(dataComparacao);
							// Após definir qual é a data que será comparada,
							// verifica-se
							// se esta se enquadra no PtcCor atual.O loop while
							// é interrompido quando isto é feito.
							if ((dataComparacao.after(dataLimitePtc)) || (dataCompStr.equals(dataLimiteStr))) {
								if (diasAntecedencia > 0) {
									corRelogio = ptcCor.getCor().getNomeCor();
									descRelogio = ptcCor.getCor().getSignificadoCor();
									encontrouLimite = true;
								}
							}
						}// fim do while
					} // fim do if - else Preto
				}
		}
		else if ("N".equals(pontoCritico.getIndAtivoPtc())){
			corRelogio = "Proibido";
			descRelogio = "Cancelado";
		}
		String[] retorno = { corRelogio, descRelogio };
		return (retorno);
	}
	
        /**
         *
         * @param pontoCritico
         * @param data
         * @return
         * @throws ECARException
         */
        public String[] getRelogioNaData(HistoricoPontoCriticoPtc pontoCritico, Date data) throws ECARException {
		String corRelogio = "Branco";
		String descRelogio = "Data registrada (Nenhuma ação requerida)";
		int diasAntecedencia;
		Date dataCorrente = data;
		String dataCorrStr = Data.parseDate(dataCorrente);
		Date dataLimitePtc = pontoCritico.getDataLimitePtc();
		String dataLimiteStr = Data.parseDate(dataLimitePtc);
		if ("S".equals(pontoCritico.getIndAtivoPtc())) {
			if (pontoCritico.getDataSolucaoPtc() != null) {
				corRelogio = "Checked";
				descRelogio = "Solucionado";
			}
			else
				if (dataLimitePtc != null) {
					if ((dataLimitePtc.before(dataCorrente)) || (dataLimiteStr.equals(dataCorrStr))) {
						corRelogio = "PretoFixo";
						descRelogio = "Data Limite Ultrapassada";
					}
					// Caso contrário, itera-se buscando qual o período de envio
					// visando descobrir qual o período mais 'grave' que se
					// encaixa
					// nos dias de antecedência que restam
					else {
						
//						List listPtcCor = this.ordenaList(Util.converteParaList(pontoCritico.getPontoCriticoCorPtccores()), "this.antecedenciaPrimEmailPtccor");
						List listPtcCor = this.ordenaList(Util.converteParaList(pontoCritico.getPontoCriticoCorPtccores()), "antecedenciaPrimEmailPtccor");						
						Iterator itPtcCor = listPtcCor.iterator();
						boolean encontrouLimite = false;
						while ((itPtcCor.hasNext()) && (!encontrouLimite)) {
							PontocriticoCorPtccor ptcCor = (PontocriticoCorPtccor) itPtcCor.next();
							if (ptcCor.getAntecedenciaPrimEmailPtccor() == null)
								diasAntecedencia = 0;
							else
								diasAntecedencia = ptcCor.getAntecedenciaPrimEmailPtccor().intValue();
							Date dataComparacao = Data.addDias(diasAntecedencia, dataCorrente);
							String dataCompStr = Data.parseDate(dataComparacao);
							// Após definir qual é a data que será comparada,
							// verifica-se
							// se esta se enquadra no PtcCor atual.O loop while
							// é interrompido quando isto é feito.
							if ((dataComparacao.after(dataLimitePtc)) || (dataCompStr.equals(dataLimiteStr))) {
								if (diasAntecedencia > 0) {
									corRelogio = ptcCor.getCor().getNomeCor();
									descRelogio = ptcCor.getCor().getSignificadoCor();
									encontrouLimite = true;
								}
							}
						}// fim do while
					} // fim do if - else Preto
				}
		}
		else if ("N".equals(pontoCritico.getIndAtivoPtc())){
			corRelogio = "Proibido";
			descRelogio = "Cancelado";
		}
		String[] retorno = { corRelogio, descRelogio };
		return (retorno);
	}
	
	/**
	 * Retorna a lista de Pontos Críticos Ativos de acordo com a data limite
	 * informada. <br>
	 * 
         * @param situacao
         * @param excluido
         * @author rogerio
	 * @since 0.1, 26/02/2007
	 * @version 0.1, 26/02/2007
	 * @return List
	 * @throws ECARException
	 */
	public List listarPontoCriticoAtivoNExcluidoNSolucionado(String situacao, String excluido) throws ECARException {
		List list = null;
		try {
			Query query = session.createQuery("from PontoCriticoPtc " + 
					"where indAtivoPtc = :situacao and ind_excluido_ptc <> :excluido and data_solucao_ptc is null " 
					+ "order by codPtc asc ");
			query.setString("situacao", situacao);
			query.setString("excluido", excluido);
			list = query.list();
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		return list;
	} // fim listarPontoCritico
	
	/**
	 * Verifica se o pontoCritico possui apontamentos
	 * 
	 * @param pontoCritico
	 * @return
	 * @author 05110500460
	 */
	public boolean contemListaApontamentos(PontoCriticoPtc pontoCritico) {
		Set<Object> apontamentos = null;
		if (pontoCritico != null) {
			apontamentos = pontoCritico.getApontamentoApts();
		}
		if (apontamentos != null && apontamentos.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Lista os pontos críticos pertencentes ao itemEstrutura que <b> não </b>
	 * foram excluídos
	 * 
         * @param itemEstrutura
         * @return
	 * @throws ECARException
	 * @author 05110500460
	 */
	public List listar(ItemEstruturaIett itemEstrutura) throws ECARException {
		List list = null;
		try {
			Query query = session.createQuery("select ptc from PontoCriticoPtc ptc" + " where "
			        + " (ptc.indExcluidoPtc <> 'S' or  ptc.indExcluidoPtc is null) " + " and ptc.itemEstruturaIett.codIett = :codIett");
			query.setLong("codIett", itemEstrutura.getCodIett());
			list = query.list();
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		return list;
	}
	
	/**
	 * Lista os pontos críticos pertencentes ao itemEstrutura que <b> não </b>
	 * foram excluídos
	 * 
         * @param itemEstrutura
         * @param dataBase
         * @return
	 * @throws ECARException
	 * @author 05110500460
	 */
	public List listarItensIncluidosAntesDaDataBase(ItemEstruturaIett itemEstrutura, Date dataBase) throws ECARException {
		List list = null;
		try {
			Query query = session.createQuery("select ptc from PontoCriticoPtc ptc" + " where " +
			// " (ptc.indExcluidoPtc <> 'S' or  ptc.indExcluidoPtc is null) and "
			        // + // Não pega os itens excluidos por causa do calcula da
			        // situação
			        // do item no dia da dataBase
			        " ptc.itemEstruturaIett.codIett = :codIett " + " and ptc.dataInclusaoPtc < :dataBase " + " order by ptc.codPtc");
			query.setLong("codIett", itemEstrutura.getCodIett());
			query.setDate("dataBase", dataBase);
			list = query.list();
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		return list;
	}
	
	/**
	 * Lista os pontos criticos pertencentes ao itemEstrutura que foram
	 * excluídos
	 * 
	 * @param codIett
	 * @return
	 * @throws ECARException
	 * @author 05110500460
	 */
	public List listarExcluidos(long codIett) throws ECARException {
		List list = null;
		try {
			Query query = this.getSession().createQuery(
			        "select ptc from PontoCriticoPtc ptc" + " join ptc.itemEstruturaIett itemEstrutura "
			                + " where  ptc.indExcluidoPtc = 'S' and itemEstrutura.codIett = :codIett");
			query.setLong("codIett", codIett);
			list = query.list();
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		return list;
	}
	
	/**
	 * Lista as modificações anteriores a dataBase do histórico do Ponto Crítico
	 * 
         * @param pontoCritico
         * @param dataBase
	 * @return Lista com o histórico ordenado pela data de última modificação
	 * @throws ECARException
	 * @author 05110500460rparâmetro
	 */
	public List<HistoricoPtcH> listarHistorico(PontoCriticoPtc pontoCritico, Date dataBase) throws ECARException {
		List list = null;
		try {
			Query query = session.createQuery("select ptcH from HistoricoPtcH ptcH " + " where " + " ptcH.pontoCriticoPtc.codPtc = :codPtc "
			        + " and (ptcH.dataUltManutencaoPtc is null or ptcH.dataUltManutencaoPtc < :dataBase)" + " order by  ptcH.codPtcH asc ");
			query.setLong("codPtc", pontoCritico.getCodPtc());
			query.setDate("dataBase", dataBase);
			list = query.list();
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		return list;
	}
	
	/**
	 * Retorna lista de itens que tenham um Acompanhamento
	 * (AcompReferenciaItemAri) em algum dos Períodos de Referência
	 * (AcompReferenciaAref) passados como parâmetro
	 * 
	 * @param periodosConsiderados
	 *            Collection de AcompReferenciaAref
	 * @param niveisPlanejamento
	 *            Collection
	 * @param orgaoResponsavel
	 *            OrgaoOrg
	 * @param usuarioUsu
	 *            UsuarioUsu
	 * @param gruposUsuario
	 *            Set
	 * @param codTipoAcompanhamento
	 *            Long (se for nulo ignora o tipo de acompanhamento)
	 * @param codIettPai
	 *            Long (se for diferente de nulo, obtêm os filhos desse item)
	 * @return List de AcompReferenciaItemAri. lista vazia se não foi informado
	 *         nenhum periodo
	 * @throws ECARException
	 */
	/*
	 * public Object[] getItensPontoCritico(UsuarioUsu usuarioUsu, Set
	 * gruposUsuario, Long codIettPai, Long codCor) throws ECARException { try{
	 * ItemEstruturaDao itemDao = new ItemEstruturaDao(null); StringBuilder
	 * query = new
	 * StringBuilder("select ptc.ItemEstruturaIett from PontoCriticoPtc ptc");
	 * //StringBuilder where = newStringBuilder(
	 * " where arel.acompReferenciaItemAri.acompReferenciaAref.codAref in (:listaAcompanhamentos)"
	 * ); StringBuilder where = new
	 * StringBuilder(" where ptc.indAtivoPtc = 'S'"); int retornarAteNivel = -1;
	 * List iettFilhos = new ArrayList(); if(codIettPai != null){
	 * ItemEstruturaIett iettPai = (ItemEstruturaIett)
	 * itemDao.buscar(ItemEstruturaIett.class, codIettPai); if(iettPai != null){
	 * retornarAteNivel = iettPai.getNivelIett().intValue() + 1;
	 * iettFilhos.addAll(itemDao.getDescendentes(iettPai, false)); } } Query
	 * queryItens = this.getSession().createQuery(query.toString() +
	 * where.toString()); List listaCodigosAref = new ArrayList(); Iterator it =
	 * periodosConsiderados.iterator(); while(it.hasNext()){ AcompReferenciaAref
	 * aReferencia = (AcompReferenciaAref)it.next();
	 * listaCodigosAref.add(aReferencia.getCodAref()); }
	 * queryItens.setParameterList("listaAcompanhamentos", listaCodigosAref);
	 * if(orgaoResponsavel != null && orgaoResponsavel.getCodOrg() != null){
	 * queryItens.setLong("orgaoResp",
	 * orgaoResponsavel.getCodOrg().longValue()); } if(niveisPlanejamento !=
	 * null && niveisPlanejamento.size() > 0) { List listaCodigosNiveis = new
	 * ArrayList(); Iterator itNiveis = niveisPlanejamento.iterator();
	 * while(itNiveis.hasNext()){ SisAtributoSatb nivel = (SisAtributoSatb)
	 * itNiveis.next(); listaCodigosNiveis.add(nivel.getCodSatb()); }
	 * queryItens.setParameterList("listaNiveis", listaCodigosNiveis); }
	 * if(codTipoAcompanhamento != null) { // listar ARIs conforme o tipo de
	 * acompanhamento passado como parâmetro queryItens.setLong("codTa",
	 * codTipoAcompanhamento.longValue()); } if(codCor != null){
	 * queryItens.setLong("codCor", codCor.longValue()); } List listaItens = new
	 * ArrayList(); List listaAris = queryItens.list(); Iterator itListaAris =
	 * listaAris.iterator(); if(usuarioUsu == null) { //utilizado para o
	 * grafico.jsp - teste de performance while(itListaAris.hasNext()) {
	 * AcompReferenciaItemAri ari = (AcompReferenciaItemAri)itListaAris.next();
	 * listaItens.add(ari.getItemEstruturaIett()); } } else {
	 * while(itListaAris.hasNext()) { AcompReferenciaItemAri ari =
	 * (AcompReferenciaItemAri)itListaAris.next();
	 * if(validaPermissao.permissaoLeituraAcompanhamento(ari, usuarioUsu,
	 * gruposUsuario)) { listaItens.add(ari.getItemEstruturaIett()); } } } List
	 * itensGeralComArvore = itemDao.getArvoreItens(listaItens, null); List
	 * arvoreItens = new ArrayList(itensGeralComArvore); if(retornarAteNivel !=
	 * -1){ Iterator itArvore = arvoreItens.iterator();
	 * while(itArvore.hasNext()){ ItemEstruturaIett iett = (ItemEstruturaIett)
	 * itArvore.next(); if(iett.getNivelIett().intValue() > retornarAteNivel){
	 * itArvore.remove(); } else if(!iettFilhos.contains(iett)){
	 * itArvore.remove(); } } } TipoAcompanhamentoTa tipoAcomp = null;
	 * if(codTipoAcompanhamento != null) { tipoAcomp = (TipoAcompanhamentoTa)
	 * tipoAcompanhamentoDao.buscar(TipoAcompanhamentoTa.class,
	 * codTipoAcompanhamento); } return new
	 * Object[]{itemDao.getItensOrdenados(arvoreItens, tipoAcomp),
	 * itensGeralComArvore}; } else { // Se não foi informado nenhum periodo,
	 * retorna uma lista vazia return new Object[]{new ArrayList(), new
	 * ArrayList()}; } } catch(HibernateException e){ this.logger.error(e);
	 * throw new ECARException(e); } }
	 */
	/**
	 * Retorna o valor de um atributo em um itemEstrutura
	 * 
         * @param pontoCritico
	 * @param nomeAtributo
	 * @param fkAtributo
	 * @return
	 * @throws ECARException
	 */
	public String getValorAtributoPontoCritico(PontoCriticoPtc pontoCritico, String nomeAtributo, String fkAtributo) throws ECARException {
		try {
			Object retorno = Util.invocaGet(pontoCritico, nomeAtributo);
			if (retorno != null) {
				if (fkAtributo != null && !"".equals(fkAtributo)) {
					retorno = Util.invocaGet(retorno, fkAtributo);
					if (retorno != null)
						return retorno.toString();
					else
						return "";
				}
				else {
					if (retorno.getClass().equals(Timestamp.class) || retorno.getClass().equals(Date.class))
						retorno = Data.parseDate((Date) retorno);
					return retorno.toString();
				}
			}
		}
		catch (Exception e) {}
		return "";
	}
	
	/**
	 * Retorna o valor de um atributo em um itemEstrutura
	 * 
         * @param pontoCritico
	 * @param nomeAtributo
	 * @param fkAtributo
	 * @return
	 * @throws ECARException
	 */
	public String getValorAtributoPontoCritico(HistoricoPontoCriticoPtc pontoCritico, String nomeAtributo, String fkAtributo) throws ECARException {
		try {
			Object retorno = Util.invocaGet(pontoCritico, nomeAtributo);
			if (retorno != null) {
				if (fkAtributo != null && !"".equals(fkAtributo)) {
					retorno = Util.invocaGet(retorno, fkAtributo);
					if (retorno != null)
						return retorno.toString();
					else
						return "";
				}
				else {
					if (retorno.getClass().equals(Timestamp.class) || retorno.getClass().equals(Date.class))
						retorno = Data.parseDate((Date) retorno);
					return retorno.toString();
				}
			}
		}
		catch (Exception e) {}
		return "";
	}
	
	/**
	 * @param request
	 * @param pontoCritico
	 * @throws ECARException
	 */
	private void setAtributosLivresPontoCritico(HttpServletRequest request, PontoCriticoPtc pontoCriticoPtc) throws ECARException {
		Boolean planejamentoBloqueado = false;
		ItemEstruturaIett itemEstruturaIett = pontoCriticoPtc.getItemEstruturaIett();
		EstruturaEtt estruturaEtt = null;
		if (itemEstruturaIett != null) {
			if (itemEstruturaIett.getEstruturaEtt() != null) {
				estruturaEtt = itemEstruturaIett.getEstruturaEtt();
			}
			if (itemEstruturaIett.getIndBloqPlanejamentoIett() != null && itemEstruturaIett.getIndBloqPlanejamentoIett().equals("S")) {
				planejamentoBloqueado = true;
			}
		}
		SegurancaECAR seg = (SegurancaECAR) request.getSession().getAttribute("seguranca");
		List funcoesDoUsuario = (new TipoFuncAcompDao(request)).getFuncoesAcompNaEstruturaDoUsuario(itemEstruturaIett, seg.getUsuario(), seg.getGruposAcesso());
		/*
		 * Obter o código do grupo do atributo livre para pegar pelo campo "a" +
		 * codSga;
		 */
		FuncaoDao funcaoDao = new FuncaoDao(request);
		FuncaoFun funcao = funcaoDao.getFuncaoPontosCriticos();
		List sgas = new ArrayList();
		if (estruturaEtt != null && estruturaEtt.getEstruturaAtributoEttats() != null && !estruturaEtt.getEstruturaAtributoEttats().isEmpty()) {
			for (Iterator it = estruturaEtt.getEstruturaAtributoEttats().iterator(); it.hasNext();) {
				EstruturaAtributoEttat ettat = (EstruturaAtributoEttat) it.next();
				if (ettat.getAtributosAtb() != null && ettat.getAtributosAtb().getSisGrupoAtributoSga() != null) {
					if ((ettat.getAtributosAtb().getFuncaoFun().equals(funcao))
					        && (!planejamentoBloqueado || (planejamentoBloqueado && !ettat.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(ettat,
					                funcoesDoUsuario))) {
						sgas.add(ettat.getAtributosAtb().getSisGrupoAtributoSga());
					}
				}
			}
		}
		// Percorrer grupo de atributos para selecionar os atributos livres...
		for (Iterator it = sgas.iterator(); it.hasNext();) {
			SisGrupoAtributoSga grupoAtributo = (SisGrupoAtributoSga) it.next();
			String nomeCampo = "a" + grupoAtributo.getCodSga().toString();
			String tipoCampo = grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString();
			// Se for CheckBox ou RadioButton ou ListBox, não gravar
			// InformacaoIettSatb
			if (tipoCampo.equals(SisTipoExibicGrupoDao.CHECKBOX) || tipoCampo.equals(SisTipoExibicGrupoDao.LISTBOX)
			        || tipoCampo.equals(SisTipoExibicGrupoDao.RADIO_BUTTON) || tipoCampo.equals(SisTipoExibicGrupoDao.COMBOBOX)) {
				String[] atributos = request.getParameterValues(nomeCampo);
				int numAtributos = 0;
				if (atributos != null) {
					int contador=0;
					for (int i = 0; i < atributos.length; i++) {
						if (!atributos[i].equals(Dominios.STRING_VAZIA)) {
							contador++;
						}
					}
					numAtributos = contador;
				}
				for (int i = 0; i < numAtributos; i++) {
					// Não seto todos os outros campos, pois eles serão setados
					// depois de gravar o item.
					PontoCriticoSisAtributoPtcSatb atributoLivre = new PontoCriticoSisAtributoPtcSatb();
					atributoLivre.setDataUltManutencao(Data.getDataAtual());
					atributoLivre.setUsuarioUsu(((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
					atributoLivre.setPontoCriticoPtc(pontoCriticoPtc);
					atributoLivre.setSisAtributoSatb((SisAtributoSatb) this.buscar(SisAtributoSatb.class, Long.valueOf(atributos[i])));
					atributoLivre.atribuirPKPai();
					if (pontoCriticoPtc.getPontoCriticoSisAtributoPtcSatbs() == null) {
						pontoCriticoPtc.setPontoCriticoSisAtributoPtcSatbs(new HashSet());
					}
					pontoCriticoPtc.getPontoCriticoSisAtributoPtcSatbs().add(atributoLivre);
				}
			}
			// Se for TEXT Field
			else
				if (tipoCampo.equals(SisTipoExibicGrupoDao.TEXT) || tipoCampo.equals(SisTipoExibicGrupoDao.TEXTAREA)
				        || tipoCampo.equals(SisTipoExibicGrupoDao.IMAGEM) || tipoCampo.equals(SisTipoExibicGrupoDao.VALIDACAO)) {
					if (grupoAtributo.getSisAtributoSatbs() != null && !grupoAtributo.getSisAtributoSatbs().isEmpty()) {
						SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) grupoAtributo.getSisAtributoSatbs().iterator().next();
						if (!(Pagina.getParamStr(request, nomeCampo).equals(Dominios.STRING_VAZIA)) || (sisAtributoSatb.isAtributoAutoIcremental())
						        || (sisAtributoSatb.isAtributoContemMascara())) {
							// Não seto todos os outros campos, pois eles serão
							// setados depois de gravar o item.
							PontoCriticoSisAtributoPtcSatb atributoLivre = new PontoCriticoSisAtributoPtcSatb();
							atributoLivre.setDataUltManutencao(Data.getDataAtual());
							atributoLivre.setUsuarioUsu(((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
							atributoLivre.setPontoCriticoPtc(pontoCriticoPtc);
							atributoLivre.setSisAtributoSatb((SisAtributoSatb) grupoAtributo.getSisAtributoSatbs().iterator().next());
							atributoLivre.setInformacao(Pagina.getParamStr(request, nomeCampo));
							atributoLivre.setFuncao(funcao);
							atributoLivre.atribuirPKPai();
							if (pontoCriticoPtc.getPontoCriticoSisAtributoPtcSatbs() == null) {
								pontoCriticoPtc.setPontoCriticoSisAtributoPtcSatbs(new HashSet());
							}
							
							String pathRaiz = request.getContextPath();
							
							// tratamento imagem
							String caminhoAuxiliarImagem = Pagina.getParamStr(request, "hidImagem" + "a"
									+ grupoAtributo.getCodSga().toString());
							if (caminhoAuxiliarImagem!=null && caminhoAuxiliarImagem.length()>0) {
								
	    						String chave = atributoLivre.getInformacao();
	    						chave = chave.substring(chave.indexOf("RemoteFile=")+ "RemoteFile=".length());
	    						UsuarioUsu usuario = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
	                            if(usuario.getMapArquivosAtuaisUsuarios() != null && usuario.getMapArquivosAtuaisUsuarios().containsKey(chave)){
//	                            	atributoLivre.setInformacao(usuario.getMapArquivosAtuaisUsuarios().get(chave));
	                            	
	                            	caminhoAuxiliarImagem = usuario.getMapArquivosAtuaisUsuarios().get(chave);
	                            	caminhoAuxiliarImagem = pathRaiz +"/DownloadFile?RemoteFile=" + caminhoAuxiliarImagem;
	                            }
//	                            else{
								
									// salvar a imagem fisicamente que tem o caminho real em "a" + codigo de grupo
									try {									
										String nomeArquivoNovo = FileUpload.salvarArquivoSessaoFisicamente(request, "a" + grupoAtributo.getCodSga().toString(), caminhoAuxiliarImagem);
										if(nomeArquivoNovo != null && !nomeArquivoNovo.equals(""))
											atributoLivre.setInformacao(nomeArquivoNovo);
									} catch (FileNotFoundException e) {
										throw new ECARException("erro.arquivoUrl",e, new String[]{caminhoAuxiliarImagem});
									} catch (Exception e) {
										throw new ECARException("erro.upload",e, new String[]{caminhoAuxiliarImagem});
									}
//	                            }
							}
							
							pontoCriticoPtc.getPontoCriticoSisAtributoPtcSatbs().add(atributoLivre);
						}
					}
				}
				else
					if (tipoCampo.equals(SisTipoExibicGrupoDao.MULTITEXTO)) {
						Enumeration lAtrib = request.getParameterNames();
						while (lAtrib.hasMoreElements()) {
							String atrib = (String) lAtrib.nextElement();
							if (atrib.lastIndexOf('_') > 0) {
								// System.out.println("nomeatributo" + atrib);
								String nomeAtrib = atrib.substring(0, atrib.lastIndexOf('_'));
								String codSisAtrib = atrib.substring(atrib.lastIndexOf('_') + 1);
								nomeCampo = "a" + grupoAtributo.getCodSga().toString() + "_" + codSisAtrib;
								if (nomeAtrib.equals("a" + grupoAtributo.getCodSga().toString()) && !"".equals(Pagina.getParamStr(request, atrib))) {
									PontoCriticoSisAtributoPtcSatb atributoLivre = new PontoCriticoSisAtributoPtcSatb();
									atributoLivre.setDataUltManutencao(Data.getDataAtual());
									atributoLivre.setUsuarioUsu(((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
									atributoLivre.setPontoCriticoPtc(pontoCriticoPtc);
									atributoLivre.setSisAtributoSatb((SisAtributoSatb) this.buscar(SisAtributoSatb.class, Long.valueOf(codSisAtrib)));
									atributoLivre.setInformacao(Pagina.getParamStr(request, nomeCampo));
									atributoLivre.atribuirPKPai();
									if (pontoCriticoPtc.getPontoCriticoSisAtributoPtcSatbs() == null) {
										pontoCriticoPtc.setPontoCriticoSisAtributoPtcSatbs(new HashSet());
									}
									pontoCriticoPtc.getPontoCriticoSisAtributoPtcSatbs().add(atributoLivre);
								}
							}
						}
					}
		}
	}
	
	/**
	 * @param atributo
	 * @param funcoesAcompanhamenoDoUsuario
	 * @return
	 */
	private boolean podeEditarAtributoBloqueadoNaEstrutura(ObjetoEstrutura atributo, List funcoesAcompanhamenoDoUsuario) {
		Set liberadoParaFuncoesAcompanhamento = atributo.iGetLibTipoFuncAcompTpfas();
		for (Iterator<TipoFuncAcompTpfa> itFuncaosAcompDoUsuario = funcoesAcompanhamenoDoUsuario.iterator(); itFuncaosAcompDoUsuario.hasNext();) {
			TipoFuncAcompTpfa funcaoAcompDoUsuario = (TipoFuncAcompTpfa) itFuncaosAcompDoUsuario.next();
			if (liberadoParaFuncoesAcompanhamento != null && liberadoParaFuncoesAcompanhamento.contains(funcaoAcompDoUsuario)) {
				return true; // pode editar o campo
			}
		}
		return false;
	}
	
	/*
	 * Histórico
	 */
        /**
         *
         * @param pojoHistorico
         * @param tipoHistorico
         * @param usuario
         * @throws ECARException
         */
        public void gerarHistorico(HistoricoPontoCriticoPtc pojoHistorico, Long tipoHistorico, UsuarioUsu usuario) throws ECARException {
		if (pojoHistorico != null) {
			List<EstruturaAtributoEttat> lista = null;
			if (pojoHistorico.getItemEstruturaIett() != null){
				EstruturaEtt ett = pojoHistorico.getItemEstruturaIett().getEstruturaEtt();
				FuncaoFun func = new FuncaoDao(null).getFuncaoPontosCriticos();
				lista = (new EstruturaDao(null)).getAtributosEstrutura(ett, func);
				
				for (EstruturaAtributoEttat etta : lista) {
					Hibernate.initialize(etta.getAtributosAtb());
					if (etta.getAtributosAtb().getSisGrupoAtributoSga() != null) {
						Hibernate.initialize(etta.getAtributosAtb().getSisGrupoAtributoSga());
						if (etta.getAtributosAtb().getSisGrupoAtributoSga().getSisAtributoSatbs() != null)
							Hibernate.initialize(etta.getAtributosAtb().getSisGrupoAtributoSga().getSisAtributoSatbs());
					}
				}
				
			}
			pojoHistorico.setSisGrupoAtributoSga(new ConfiguracaoDao(null).getConfiguracao().getSisGrupoAtributoSgaTipoPontoCritico());
			Hibernate.initialize(pojoHistorico.getSisGrupoAtributoSga().getSisAtributoSatbs());
			Hibernate.initialize(pojoHistorico.getSisGrupoAtributoSga().getGrupoAtributosLivresSgas());
			Hibernate.initialize(pojoHistorico.getPontoCriticoSisAtributoPtcSatbs());
			
			pojoHistorico.setAtributoEstrutura(lista);
			
			this.historico.setHistorico(pojoHistorico, tipoHistorico, usuario,
			        session);
		}
	}
	
        /**
         *
         * @param inicio
         * @param fim
         * @param tipoHistorico
         * @param codigos
         * @return
         */
    @SuppressWarnings("empty-statement")
        public List<HistoricoPontoCriticoPtc> listaObjetoHistorico(Date inicio, Date fim, String[] tipoHistorico, String[] codigos) {
		try {
			return historico.listaObjetoHistorico(null, inicio, fim, tipoHistorico, codigos);
		}
		catch (Exception e) {
			e.printStackTrace();
		};
		return null;
	}
	
    /**
     *
     * @param inicio
     * @param fim
     * @param tipoHistorico
     * @param codigos
     * @return
     */
    @SuppressWarnings("empty-statement")
	public List<HistoricoXml> listaHistorico(Date inicio, Date fim, String[] tipoHistorico, String[] codigos) {
		try {
			return historico.listaHistorico(null, inicio, fim, tipoHistorico, codigos);
		}
		catch (Exception e) {
			e.printStackTrace();
		};
		return null;
	}
	
    /**
     *
     * @param chaveHistorico
     * @return
     */
    @SuppressWarnings("empty-statement")
	public HistoricoPontoCriticoPtc getHistorico(Long chaveHistorico) {
		try {
			return historico.getObjetoSerializado(chaveHistorico);
		}
		catch (Exception e) {
			e.printStackTrace();
		};
		return null;
	}
	
    /**
     *
     * @param chaveObjetoSerializado
     * @return
     */
    @SuppressWarnings("empty-statement")
    public List<HistoricoPontoCriticoPtc> getListaHistorico(String[] chaveObjetoSerializado) {
		try {
			return historico.listaObjetoHistorico(null, null, null, null, chaveObjetoSerializado);
		}
		catch (Exception e) {
			e.printStackTrace();
		};
		return null;
	}
	
	/**
	 * efetua a carga inicial do histórico de pontos críticos
	 * Devido ao problema de java heap space e ao problema de não termos tempo
	 * devido a entrega da versão, esse método só da a carga de 100 registros 
	 * de item a cada vez que é chamado
         * @throws ECARException
	 */

	public void cargaInicialHistorico() throws ECARException{
		HistoricoPontoCriticoPtc pojoHistorico = new HistoricoPontoCriticoPtc();
		HistoricoDao historicoDao = new HistoricoDao(request);
		Transaction tx=null;
		try {
			String[] ordem = new String[] {"codPtc", "asc"};
			List<Long> listaIdObjetoSerializado = historicoDao.listaIdObjetoSerializado(HistoricoPontoCriticoPtc.class);
			List<PontoCriticoPtc> lista = getPontoCriticoPtcSemHistorico(listaIdObjetoSerializado);
			Iterator itLista = lista.iterator();
			int count = 1;
			PontoCriticoPtc ptc = null;
			while (itLista.hasNext() && count <= 100){
				ptc = (PontoCriticoPtc) itLista.next();
				System.out.println("Codigo PTC: " + ptc.getCodPtc());
				System.out.println("Contador PTC: " + count);
				tx = session.beginTransaction();
				pojoHistorico.carregar(ptc);
				if (("N").equals(ptc.getIndExcluidoPtc()))
					gerarHistorico(pojoHistorico, Historico.INCLUIR, ((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
				else
					gerarHistorico(pojoHistorico, Historico.EXCLUIR, ((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
				
				tx.commit();
				count++;
				
				ptc = null;
				if (count%100 == 0){
					System.gc();
				}
			}
			
			pojoHistorico = null;
			ordem = null;
			lista = null;
			listaIdObjetoSerializado = null;

		}
		catch (ECARException e) {
			if (tx != null)
				tx.rollback();
			this.logger.error(e);
			throw new ECARException(e);	
		}
	}
	
	/**
	 * Retorna uma lista de pontos críticos que ainda não tem histórico gerado.
	 * @param codigos
	 * @return
	 */
	public List getPontoCriticoPtcSemHistorico(List<Long> codigos){
		StringBuffer query = new StringBuffer();
		query.append("select ptc from PontoCriticoPtc ptc ");
		if (codigos != null && codigos.size() > 0){
			query.append("where ptc.codPtc not in (:codigos)");
		}
		Query q = this.session.createQuery(query.toString());
		if (codigos != null && codigos.size() > 0){
			q.setParameterList("codigos", codigos);
		}
		
		return q.list();
	}
	
	/**
	 * Retorna a quantidade de registros da tabela de pontos críticos
	 * @return
	 */
	public Integer getQuantidadeLinhasPtc(){
		Query q = this.session.createQuery("select count(ptc) from PontoCriticoPtc ptc ");
		return (Integer) q.uniqueResult();
	}

	
	/*
	 * Fim dos Métodos para histórico
	 */
	/**
	 * Consulta a lista de PontosCriticos ativos que utilizam o atributo Livre
	 * utilizado como parâmetro.
	 * 
	 * @param sisAtributo
	 * @return
	 * @throws ECARException 
	 */
	public List<PontoCriticoSisAtributoPtcSatb> consultarPontosCriticosSisAtributoAtivos(SisAtributoSatb sisAtributo) throws ECARException {
		
		

		List<PontoCriticoSisAtributoPtcSatb> listaPtcGeral = consultarPontosCriticosSisAtributo(sisAtributo); 
		
		List<PontoCriticoSisAtributoPtcSatb> listaPtcAtivos = new ArrayList<PontoCriticoSisAtributoPtcSatb>(); 
		
		
		for (PontoCriticoSisAtributoPtcSatb ptcSisAtributo : listaPtcGeral) {
			
			//O ponto critico e o item ao qual o ptc pertence devem estar ativos
			if (ptcSisAtributo.getPontoCriticoPtc().getIndAtivoPtc().equals("S") && (ptcSisAtributo.getPontoCriticoPtc().getItemEstruturaIett().getIndAtivoIett().equals("S"))){
				listaPtcAtivos.add(ptcSisAtributo);
			}
			
		}
			
		return listaPtcAtivos;
	}
	
	/**
	 * Consulta a lista de PontosCriticos ativos que utilizam o atributo Livre
	 * utilizado como parâmetro.
	 * 
	 * @param sisAtributo
	 * @return
	 * @throws ECARException
	 */
	public List<PontoCriticoSisAtributoPtcSatb> consultarPontosCriticosSisAtributo(SisAtributoSatb sisAtributo) throws ECARException {
		try {
			String hql = MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_PONTOCRITICO_ATRIBUTOLIVRE, request.getSession().getServletContext()),
			        sisAtributo.getCodSatb());
			Query query = this.session.createQuery(hql);
			return query.list();
		}
		catch (IOException ioex) {
			this.logger.error(ioex);
			throw new ECARException(ioex);
		}
		catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}

	
	/**
	 * Retorna o Objeto do tipo Sequencial contido na lista.
         * @param ptcSisAtributo
         * @return
         * @throws ECARException
	 */
	public TipoValor obterTipoSequencial(PontoCriticoSisAtributoPtcSatb ptcSisAtributo) throws ECARException {
		
		TipoValor tipoSeq = null;
		Iterator<TipoValor> it = null;
		
		try {
			if (getSession().contains(ptcSisAtributo)) {
				it = ptcSisAtributo.getTiposValores().iterator();
			} else {
				throw new ECARException(new Throwable("Sessão encerrada."));
			}
		} catch (ECARException ecarex){
			this.logger.error(ecarex.getCausaRaiz()+" Recuperação realizada com sucesso.");
			ptcSisAtributo = this.carregarObjetoPtcCriticoSisAtributo(ptcSisAtributo);
			
			it = ptcSisAtributo.getTiposValores().iterator();
		}
		
		while (it.hasNext()) {
			tipoSeq = (TipoValor)it.next();
			
			if (tipoSeq.getTipo().compareTo(TipoValorEnum.SEQUENCIAL) == 0){
				break;
			}
		}
		
		return tipoSeq;
	}

	/**
	 * Utilizado para carregar o objeto PontoCriticoSisAtributoPtcSatb caso ele não esteja na sessão do hibernate.
	 * TODO Novos atributos lazy poderão ser adicionados ao Hibernate.initialize de acordo com a necessidade 
	 * @param ptcSisAtributo
	 * @return
	 */
	private PontoCriticoSisAtributoPtcSatb carregarObjetoPtcCriticoSisAtributo(PontoCriticoSisAtributoPtcSatb ptcSisAtributo) {
		
	   	if (!getSession().contains(ptcSisAtributo)) {
	    	
	   		ptcSisAtributo = (PontoCriticoSisAtributoPtcSatb) getSession().load(PontoCriticoSisAtributoPtcSatb.class,ptcSisAtributo.getComp_id());
    	}
	   	
    	Hibernate.initialize(ptcSisAtributo.getTiposValores());
    	
    	
    	return ptcSisAtributo;
	}
	
}