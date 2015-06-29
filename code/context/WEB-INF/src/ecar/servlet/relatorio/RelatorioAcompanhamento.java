/*
 * Created on 29/09/2006
 *
 */
package ecar.servlet.relatorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import comum.util.ConstantesECAR;
import comum.util.Data;
import comum.util.Mensagem;
import comum.util.Pagina;
import comum.util.Util;
import comum.util.XmlBuilder;

import ecar.bean.IettArfBean;
import ecar.dao.AcompRealFisicoDao;
import ecar.dao.AcompReferenciaDao;
import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.ConfigRelatorioCfgrelDAO;
import ecar.dao.ConfiguracaoDao;
import ecar.dao.CorDao;
import ecar.dao.EmpresaDao;
import ecar.dao.EstruturaAtributoDao;
import ecar.dao.EstruturaDao;
import ecar.dao.EstruturaFuncaoDao;
import ecar.dao.ExercicioDao;
import ecar.dao.ItemEstrtIndResulDao;
import ecar.dao.ItemEstruturaContaOrcamentoDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.ItemEstruturaPrevisaoDao;
import ecar.dao.ItemEstruturaRealizadoDao;
import ecar.dao.ModeloRelatorioMrelDAO;
import ecar.dao.OrgaoDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.ConfigRelatorioCfgrel;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EfItemEstContaEfiec;
import ecar.pojo.EfItemEstPrevisaoEfiep;
import ecar.pojo.EmpresaEmp;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.ItemEstrutAcaoIetta;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ModeloRelatorioMrel;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.StatusRelatorioSrl;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/**
 * po
 * Servlet do Relatório de Acompanhamento. <br>
 * Relatório antigamente feito em Access, foi convertido para Java (FOP).<br>
 * 
 * @author aleixo
 * @since Outubro/2006.
 * @see Mantis: 6052
 */

public class RelatorioAcompanhamento extends AbstractServletReportXmlXsl {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2395457474744685932L;
	
	private static final String MODELO_ESTRUTURA = "ECAR-001C";
	
	private HttpServletRequest request;
	private ConfiguracaoDao configDao;
	private ConfiguracaoCfg config;
	private AcompReferenciaDao acompReferenciaDao;
	private AcompReferenciaItemDao acompReferenciaItemDao;
	private AcompRealFisicoDao acompRealFisicoDao;
	private CorDao corDao;
	private ConfigRelatorioCfgrel configRel;
	private ConfigRelatorioCfgrelDAO configRelDao;
	private String pathEcar;
	private String modelo;
	private String pathRaiz;
	private EstruturaDao estruturaDao;
	private EstruturaAtributoDao estAtribDao;
	private EstruturaFuncaoDao estFuncDao;
	private ItemEstruturaDao itemEstruturaDao;
	private ItemEstruturaPrevisaoDao itemEstPrevDao;
	private ItemEstruturaRealizadoDao itemEstRealizadoDao;
	private ItemEstruturaContaOrcamentoDao itemEstContaOrcDao;
	private ItemEstrtIndResulDao itemEstrtIndResulDao;
	
	private String tipoAcomp;
	private String exigeLiberarAcompanhamento;
	boolean ehSeparadoPorOrgao;
	
	/**
	 * Gera XML.<br>
	 * 
         * @param request
         * @author aleixo
     * @since N/C
     * @version N/C
	 * @return StringBuffer
	 * @throws ECARException
	 */
	public StringBuffer getXml(HttpServletRequest request) throws ECARException{
		XmlBuilder builder = new XmlBuilder();
		
		acompReferenciaDao = new AcompReferenciaDao(request);
		acompReferenciaItemDao = new AcompReferenciaItemDao(request);
		acompRealFisicoDao = new AcompRealFisicoDao(request);
		configDao = new ConfiguracaoDao(request);
		corDao = new CorDao(request);
		configRelDao = new ConfigRelatorioCfgrelDAO(request);
		estruturaDao = new EstruturaDao(request);
		estAtribDao = new EstruturaAtributoDao(request);
		estFuncDao = new EstruturaFuncaoDao(request);
		itemEstruturaDao = new ItemEstruturaDao(request);
		itemEstPrevDao = new ItemEstruturaPrevisaoDao(request);
		itemEstRealizadoDao = new ItemEstruturaRealizadoDao(request);
		itemEstContaOrcDao = new ItemEstruturaContaOrcamentoDao(request);
		itemEstrtIndResulDao = new ItemEstrtIndResulDao(request);
		
		this.request = request;
		config = configDao.getConfiguracao();
		pathEcar = request.getContextPath();
		configRel = configRelDao.getConfigRelatorioCfgrel();
		pathRaiz = config.getRaizUpload();
		ehSeparadoPorOrgao = false;
		
		AcompReferenciaAref mesReferencia =  null;
		String nomeReferencia = "";
		
		
		EmpresaDao empresaDao = new EmpresaDao(request);
		List confg = empresaDao.listar(EmpresaEmp.class, null);
		EmpresaEmp empresa = new EmpresaEmp();
		if(confg != null && confg.size() > 0){
			empresa = (EmpresaEmp) confg.iterator().next();
		}
		
		
		String opcaoModelo = Pagina.getParamStr(request, "opcaoModelo");
		ModeloRelatorioMrel mrel = new ModeloRelatorioMrelDAO(request).getModeloRelatorioByCodAlfa(opcaoModelo);
		
		String strMesReferencia  = Pagina.getParamStr(request, "mesReferencia");
		if(!strMesReferencia.equals("")) {
			mesReferencia = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(strMesReferencia));
		}
		if(mesReferencia != null) {
			this.tipoAcomp = mesReferencia.getTipoAcompanhamentoTa().getDescricaoTa();
			this.exigeLiberarAcompanhamento = mesReferencia.getTipoAcompanhamentoTa().getIndLiberarAcompTa();
			if(mesReferencia.getTipoAcompanhamentoTa().getIndSepararOrgaoTa() != null && mesReferencia.getTipoAcompanhamentoTa().getIndSepararOrgaoTa().equals("S"))
				ehSeparadoPorOrgao = true;
		}
		
		/*Definindo o título*/
		String titulo = "";
		if(!"".equals(Pagina.getParamStr(request, "tituloCustomizado")))
			titulo = Pagina.getParamStr(request, "tituloCustomizado");
		else {
			if(configRel != null && !"".equals(configRel.getTituloCfgrel()))
				titulo = configRel.getTituloCfgrel();
			else
				titulo = config.getTituloSistema();
				
		}

		/*Definindo o rodapé*/
		String rodape = geraDataRodape();
		if(!"".equals(Pagina.getParamStr(request, "rodapeCustomizado")))
			rodape += " - "  + Pagina.getParamStr(request, "rodapeCustomizado");
		else {
			if(configRel != null && configRel.getNotaRodapeCfgrel() != null && !"".equals(configRel.getNotaRodapeCfgrel()))
				rodape += " - " + configRel.getNotaRodapeCfgrel();
		}
		
		String arisSelecionados = Pagina.getParamStr(request, "arisSelecionados");
		
		/*
		 * Se arisSelecionados == "", é por que o relatório foi pedido da tela de Opçoes,
		 * ou seja, se opcaoModelo == ECAR-001B, são escolhidos todos os itens de um órgão específico;
		 * se opcaoModelo == ECAR-002B, são escolhidos todos os itens que possuem uma situação específica.
		 * 
		 * Obs.: opcaoModelo só vai ter esses valores se arisSelecionados == "". Caso arisSelecionados != "",
		 * é por que os itens foram filtrados e as opções escolhidas virão da tela de Formato.
		 */
		
		List codArisSelecionados = new ArrayList();
		
		if(!"".equals(arisSelecionados)){
			String[] codAris = arisSelecionados.split(";");
			for(int i = 0; i < codAris.length; i++){
				if(!"".equals(codAris[i]) && !";".equals(codAris[i])){
					codArisSelecionados.add(Long.valueOf(codAris[i]));
				}
			}
		}
		
		Util.liberarImagem();
		
		//verifica qual vai ser o nome da referencia. Se for mais de uma, será consolidado.
    	if(ehSeparadoPorOrgao && acompReferenciaDao.getListaMesmaReferenciaDiaMesAno(mesReferencia).size() > 1){
			nomeReferencia = mesReferencia.getDiaAref() + "/" + mesReferencia.getMesAref() + "/" + mesReferencia.getAnoAref() + " - " + ConstantesECAR.LABEL_ORGAO_CONSOLIDADO;
		} else {
			nomeReferencia = mesReferencia.getNomeAref();
		}
    
		
		/* Início do relatório */
		
		builder.addNode("relatorio", 
				" titulo=\"" + builder.normalize(Util.normalizaCaracterMarcador(titulo)) + "\"" +
				" mesReferencia=\"" + builder.normalize(Util.normalizaCaracterMarcador(nomeReferencia)) + "\"" +
				" codModelo=\"" + builder.normalize(Util.normalizaCaracterMarcador(mrel.getCodAlfaMrel() + " - " + mrel.getClassifMrel())) + "\"" +
				" rodape=\"" + builder.normalize(Util.normalizaCaracterMarcador(rodape)) + "\"" +
				" caminhoImagemCab=\"" + builder.normalize(Util.normalizaCaracterMarcador(pathRaiz + empresa.getLogotipoRelatorioEmp())) + "\"" + 
				"");
		
		modelo = mrel.getCodAlfaMrel();
		
		List referenciasAgrupadas = new ArrayList();
		
		if(ehSeparadoPorOrgao) {
			if(codArisSelecionados != null && !codArisSelecionados.isEmpty()) {
				referenciasAgrupadas = montaListaReferenciasAgrupadas(codArisSelecionados);
			} else {
				//opção de filtrar por órgão
				if("ECAR-001B".equalsIgnoreCase(opcaoModelo)) { 
					String nomeCodOrg = Pagina.getParamStr(request, "chaveEscolhida");
					if(!nomeCodOrg.equals("")) {
						OrgaoDao orgaoDao = new OrgaoDao (request);
						OrgaoOrg orgao = (OrgaoOrg) orgaoDao.buscar(OrgaoOrg.class, Long.valueOf(nomeCodOrg));
						AcompReferenciaAref arefOrgao = acompReferenciaDao.getAcompReferenciaByOrgaoDiaMesAnoAref(orgao, mesReferencia);
						if(arefOrgao != null)
							referenciasAgrupadas.add(arefOrgao);
					}
				//opção de filtrar por situacao	
				} else if("ECAR-002B".equalsIgnoreCase(opcaoModelo)){
					referenciasAgrupadas = acompReferenciaDao.getListaMesmaReferenciaDiaMesAno(mesReferencia);
				}
			}
		} else {
			referenciasAgrupadas.add(mesReferencia);
		}
			
		if(referenciasAgrupadas != null) {
			Iterator itReferenciasAgrupadas = referenciasAgrupadas.iterator();
				
			while(itReferenciasAgrupadas.hasNext()) {
				mesReferencia = (AcompReferenciaAref) itReferenciasAgrupadas.next();
				List arels = acompReferenciaItemDao.getAcompRelatorioAcompanhamentoByAris(codArisSelecionados, mesReferencia, opcaoModelo, Pagina.getParamStr(request, "chaveEscolhida"), Pagina.getParamStr(request, "tipoFuncAcompTpfa"));
				geraXmlPrincipal(builder, arels, mesReferencia.getOrgaoOrg());
				
			}
		}	
		builder.closeNode("relatorio");
        return builder.toStringBuffer();
    }

    /**
     * Gera XML Principal.<br>
     * 
     * @author aleixo
     * @since N/C
     * @version N/C
     * @param XmlBuilder builder
     * @param List arels
     * @throws ECARException
     */
	private void geraXmlPrincipal(XmlBuilder builder, List arels, OrgaoOrg orgao) throws ECARException{
		builder.addNode("principal");
		boolean temItens = false;
		
		StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) this.acompReferenciaItemDao.buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
		
		String nomeSiglaOrgaoImpresso = "";
		
		if(arels != null && !arels.isEmpty()){
			Iterator it = arels.iterator();
			boolean primeiroItem = true;
						
			long codIett = -1;
			int indice = 0;
			Set ascendentes = new HashSet();
			
	    	SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
	    	ValidaPermissao validaPermissao = new ValidaPermissao();
	    	
			while(it.hasNext()){
				AcompRelatorioArel arel = (AcompRelatorioArel) it.next();
				
		    	List listaPermissaoTpfa = validaPermissao.permissaoVisualizarPareceres(arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getTipoAcompanhamentoTa(),seguranca.getGruposAcesso());
				if(listaPermissaoTpfa.contains(arel.getTipoFuncAcompTpfa()) 
						&& validaPermissao.permissaoLeituraAcompanhamento(arel.getAcompReferenciaItemAri(), seguranca.getUsuario(), seguranca.getGruposAcesso())){
							
					// Teste ref. Mantis 10848
//					if("S".equals(this.exigeLiberarAcompanhamento) && !"S".equals(arel.getIndLiberadoArel())){
//						continue;
//					}
					if ("N".equals(this.exigeLiberarAcompanhamento) || arel.getAcompReferenciaItemAri().getStatusRelatorioSrl().equals(statusLiberado)){
						
						if( (Dominios.SIM).equals(arel.getIndLiberadoArel()) ){
						
							String nomeSiglaOrg = getNomeSiglaOrgao(arel, orgao);
												
							String siglaOrgItem = "";
							if(MODELO_ESTRUTURA.equals(modelo)){
							
								if(ehSeparadoPorOrgao && arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getOrgaoOrg() != null){
									siglaOrgItem = arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getOrgaoOrg() .getSiglaOrg();
								} else if (!ehSeparadoPorOrgao && arel.getAcompReferenciaItemAri().getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett() != null) {
									siglaOrgItem = arel.getAcompReferenciaItemAri().getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg();
								}
								else {
									siglaOrgItem = config.getLabelAgrupamentoItensSemOrgao();
								}
							}
							
							if(!nomeSiglaOrgaoImpresso.equals(nomeSiglaOrg)){
								nomeSiglaOrgaoImpresso = nomeSiglaOrg;
								if(!primeiroItem){
									builder.closeNode("itens");
								}
								
								String exibirOrgao = "S";
								String quebrarPaginaItens = "S";
								if(MODELO_ESTRUTURA.equals(modelo)){
									exibirOrgao = "N";
									quebrarPaginaItens = "N";
								}
								temItens = true;
								
								
								builder.addNode("itens", "orgao=\"" + builder.normalize(nomeSiglaOrgaoImpresso) + "\" exibirOrgao=\"" + builder.normalize(exibirOrgao) + "\" quebrarPaginaItens=\"" + builder.normalize(quebrarPaginaItens) + "\"");
							}
							
							
							if(codIett != arel.getAcompReferenciaItemAri().getItemEstruturaIett().getCodIett().longValue()){
								boolean geraHierarquia = primeiroItem || (ascendentes != null && !ascendentes.containsAll(itemEstruturaDao.getAscendentes(arel.getAcompReferenciaItemAri().getItemEstruturaIett())));
								codIett = arel.getAcompReferenciaItemAri().getItemEstruturaIett().getCodIett().longValue();
								//ascendentes.addAll(geraXmlItens(builder, arel, estAtribDao.getDescricaoItemByAtributo(arel.getAcompReferenciaItemAri().getItemEstruturaIett(), arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getTipoAcompanhamentoTa()), false, geraHierarquia, siglaOrgItem, primeiroItem));
								
								String exibirEncaminhamentos = "S";
								if((indice + 1) < arels.size() || (indice == arels.size() - 1)){
									int proximo = indice + 1;
									
									try{
										AcompRelatorioArel proximoArel = (AcompRelatorioArel) arels.get(proximo);
										if(codIett == proximoArel.getAcompReferenciaItemAri().getItemEstruturaIett().getCodIett().longValue())
											exibirEncaminhamentos = "N";
									}
									catch (IndexOutOfBoundsException e) {
										//Não tem mais itens na lista, exibe o complemento.
										exibirEncaminhamentos = "S";
									}
								}
								
								
								ascendentes.addAll(geraXmlItens(builder, arel, estAtribDao.getDescricaoItemByAtributo(arel.getAcompReferenciaItemAri().getItemEstruturaIett(), arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getTipoAcompanhamentoTa()), true, geraHierarquia, nomeSiglaOrgaoImpresso, primeiroItem, exibirEncaminhamentos));
							}
							else {
								boolean exibirComplemento = false;
								String exibirEncaminhamentos = "S";
								if((indice + 1) < arels.size() || (indice == arels.size() - 1)){
									int proximo = indice + 1;
									
									try{
										AcompRelatorioArel proximoArel = (AcompRelatorioArel) arels.get(proximo);
										exibirComplemento = !arel.getAcompReferenciaItemAri().equals(proximoArel.getAcompReferenciaItemAri());
									}
									catch (IndexOutOfBoundsException e) {
										//Não tem mais itens na lista, exibe o complemento.
										exibirComplemento = true;
										exibirEncaminhamentos = "S";
									}
								}
								
								geraXmlItens(builder, arel, "", exibirComplemento, false, nomeSiglaOrgaoImpresso, primeiroItem, exibirEncaminhamentos);
								
							}
							indice++;
							primeiroItem = false;
						}
					}
				}//fim do if que verifica a permissão das carinhas	
			}
			if(temItens){
				builder.closeNode("itens");
			}
		}
		else {
			
			String exibirOrgao = "S";
			nomeSiglaOrgaoImpresso = getNomeSiglaOrgao(null, orgao);
			
			builder.addClosedNode("semItens", "orgao=\"" + builder.normalize(nomeSiglaOrgaoImpresso) + "\" exibirOrgao=\"" + builder.normalize(exibirOrgao)  + "\"");			
		}

		builder.closeNode("principal");
	}
	
	private String getNomeSiglaOrgao(AcompRelatorioArel arel, OrgaoOrg orgao){
		
		String nomeSiglaOrg = "";
		
		if(ehSeparadoPorOrgao && orgao != null){
			
			nomeSiglaOrg = orgao.getDescricaoOrg() +
			" - " + 
			orgao.getSiglaOrg();
		} else if (ehSeparadoPorOrgao == false && arel != null && arel.getAcompReferenciaItemAri().getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett() != null) {
			
			nomeSiglaOrg = arel.getAcompReferenciaItemAri().getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg() +
			" - " + 
			arel.getAcompReferenciaItemAri().getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg();
		}
		else {
			nomeSiglaOrg = config.getLabelAgrupamentoItensSemOrgao();
		}
		
		return nomeSiglaOrg;
	}
	
	/**
	 * Gera itens xml.<br>
	 * 
	 * @author aleixo
     * @since N/C
     * @version N/C
     * @param XmlBuilder builder
     * @param AcompRelatorioArel arel
     * @param String nomeItem
     * @param boolean exibirComplemento
     * @param boolean gerarHierarquia
     * @param String orgao
     * @param boolean primeiroItem
     * @param String exibirEncaminhamentos
	 * @return list
	 * @throws ECARException
	 */
	private List geraXmlItens(XmlBuilder builder, AcompRelatorioArel arel, 
			String nomeItem, boolean exibirComplemento, boolean gerarHierarquia, String orgao, 
			boolean primeiroItem, String exibirEncaminhamentos) throws ECARException{

		
	     String tipoArquivoRelatorio = request.getParameter("tipoArquivoRelatorio"); 
         
         if (!MODELO_ESTRUTURA.equals(modelo) && (tipoArquivoRelatorio==null || !tipoArquivoRelatorio.equals("ppt"))) {
        	 orgao = "";
         } 
		
		
		String labelDR1 = "";
		String valorDR1 = "";
		String labelDR4 = "";
		String valorDR4 = "";
		String labelOrigemIett = "";
		String valorOrigemIett = "";
		String labelRespTecnicoIett = "";
		String valorRespTecnicoIett = "";
		
		String labelEncaminhamentos = "";
		String ehPPA = (tipoAcomp.contains("PPA")) ? "S" : "N";

		//FIXME: Verificar a inclusão de um campo no tipoAcompanhamento para realizar este teste.
		if(!tipoAcomp.contains("PPA")){
			labelEncaminhamentos = "Encaminhamentos";
		}

		if(!"".equals(nomeItem)){
			ItemEstruturaIett item = arel.getAcompReferenciaItemAri().getItemEstruturaIett();
			EstruturaEtt estrutura = item.getEstruturaEtt();
			//labelData = estAtribDao.getLabelAtributoEstrutura("dataInicioIett", estrutura);
			//dataInicio = Data.parseDate(item.getDataInicioIett());
			
			//FIXME: Verificar a inclusão de um campo no tipoAcompanhamento para realizar este teste.
			if(!tipoAcomp.contains("PPA")){
				/*Dados Qualitativos*/
				labelDR1 = estAtribDao.getLabelAtributoEstrutura("descricaoR1", estrutura);
				
				valorDR1 = "";
				if(!pathEcar.contains("prve")) {
					valorDR1 = item.getDescricaoR1();	
				}
								
				/*Meta*/
				//labelBeneficiosIett = estAtribDao.getLabelAtributoEstrutura("beneficiosIett", estrutura);
				//valorBeneficiosIett = item.getBeneficiosIett();
				
				/*Previsão de Conclusão*/
				labelDR4 = estAtribDao.getLabelAtributoEstrutura("descricaoR4", estrutura);
				valorDR4 = item.getDescricaoR4();
	
				/*Investimentos*/
				labelOrigemIett = estAtribDao.getLabelAtributoEstrutura("origemIett", estrutura);
				valorOrigemIett = item.getOrigemIett();
			}
			
			/*Responsável Técnico*/
			Iterator itTpfas = item.getItemEstUsutpfuacIettutfas().iterator();
			while(itTpfas.hasNext()){
				ItemEstUsutpfuacIettutfa utfa = (ItemEstUsutpfuacIettutfa) itTpfas.next();
				if((!tipoAcomp.contains("PPA") && utfa.getTipoFuncAcompTpfa().getCodTpfa().longValue() == 2) //Não é PPA e é o Responsável Técnico
				|| (tipoAcomp.contains("PPA") && utfa.getTipoFuncAcompTpfa().getCodTpfa().longValue() == 3)) //É PPA e é o GPS
				{
					String nomeUsu = "";
					String emailUsu = "";
					String telefone = "";
					if (utfa.getUsuarioUsu() != null){
						UsuarioUsu respTecnico = utfa.getUsuarioUsu();
						nomeUsu = respTecnico.getNomeUsu();
						emailUsu = respTecnico.getEmail1Usu();
						telefone = "";
						if(respTecnico.getComercDddUsu() != null && !"".equals(respTecnico.getComercDddUsu())){
							telefone += "(" + respTecnico.getComercDddUsu() + ") ";
						}
						
						if(respTecnico.getComercTelefoneUsu() != null && !"".equals(respTecnico.getComercTelefoneUsu())){
							telefone += respTecnico.getComercTelefoneUsu();
							telefone = " - " + telefone;
						}
						
					} else if (utfa.getSisAtributoSatb() != null){
						nomeUsu = utfa.getSisAtributoSatb().getDescricaoSatb();
					}
					
					labelRespTecnicoIett = utfa.getTipoFuncAcompTpfa().getLabelTpfa().toUpperCase() + ": ";
					valorRespTecnicoIett = nomeUsu + " (" + emailUsu + telefone.trim() + ")";
					break;
				}
			}
		}

		//FIXME: mantis 10304: Temporariamente não exibir Encaminhamentos...
		labelEncaminhamentos = "";
		//labelRespTecnicoIett = "";
		//valorRespTecnicoIett = "";

		
		builder.addNode("item", 
				"nomeItem=\"" + builder.normalize(Util.normalizaCaracterMarcador(nomeItem)) + "\"" +
				" orgaoItem=\"" + builder.normalize(Util.normalizaCaracterMarcador(orgao)) + "\"" +
				" labelDR1=\"" + builder.normalize(Util.normalizaCaracterMarcador(labelDR1)) + "\"" + 
				" valorDR1=\"" + builder.normalize(Util.normalizaCaracterMarcador(valorDR1)) + "\"" +
				" labelDR4=\"" + builder.normalize(Util.normalizaCaracterMarcador(labelDR4)) + "\"" + 
				" valorDR4=\"" + builder.normalize(Util.normalizaCaracterMarcador(valorDR4)) + "\"" +
				" labelOrigemIett=\"" + builder.normalize(Util.normalizaCaracterMarcador(labelOrigemIett)) + "\"" + 
				" valorOrigemIett=\"" + builder.normalize(Util.normalizaCaracterMarcador(valorOrigemIett)) + "\"" +
				" labelRespTecnicoIett=\"" + builder.normalize(Util.normalizaCaracterMarcador(labelRespTecnicoIett)) + "\"" + 
				" valorRespTecnicoIett=\"" + builder.normalize(Util.normalizaCaracterMarcador(valorRespTecnicoIett)) + "\"" +
				" exibirEncaminhamentos=\"" + builder.normalize(Util.normalizaCaracterMarcador(exibirEncaminhamentos)) + "\"" +
				" labelEncaminhamentos=\"" + builder.normalize(Util.normalizaCaracterMarcador(labelEncaminhamentos)) + "\"" +
				" ehPPA=\"" + builder.normalize(Util.normalizaCaracterMarcador(ehPPA)) + "\"" +  
				"");
		List retorno = new ArrayList();
		
		if(MODELO_ESTRUTURA.equals(modelo) && gerarHierarquia){
			boolean quebrarPagina = true;
			
			if(primeiroItem)
				quebrarPagina = false;
			
			retorno = geraXmlHierarquia(builder, arel.getAcompReferenciaItemAri().getItemEstruturaIett(), quebrarPagina);
		}

		String labelParecer = arel.getTipoFuncAcompTpfa().getLabelPosicaoTpfa();
		String caminhoImagem = pathEcar + "/images/relAcomp/" + corDao.getImagemRelatorio(arel.getCor(), arel.getTipoFuncAcompTpfa());
//		String descricao = Util.normalizaCaracterMarcador(Util.stripHTML(arel.getDescricaoArel()));
		String descricao = Util.normalizaCaracterMarcador(Util.stripHTMLModificado(arel.getDescricaoArel()));
		descricao = Util.stripHTMLCommentsModificado(descricao);
		descricao = descricao.replace("&lt;","<");
		descricao = descricao.replace("&gt;",">");
//		descricao = arel.getDescricaoArel().replaceAll("</?\\w++[^>]*+>", "");
//		descricao = descricao.replaceAll("<!--.*?-->", "");
//		descricao = descricao.replaceAll("<[^>]*>", "");
		String observacoes = Util.normalizaCaracterMarcador(arel.getComplementoArel());
		String dataUltParecer = "";
		String situacaoParecer = "";
		
		if(arel.getDataUltManutArel() != null)
			dataUltParecer = " (" + Data.parseDate(arel.getDataUltManutArel()) + ")";
		else
			dataUltParecer = " (" + Data.parseDate(arel.getDataInclusaoArel()) + ")";
		
		if(!(descricao != null && !"".equals(descricao))){
			descricao = "Sem Itens de Parecer Cadastrados.";
		}
		
		if(arel.getSituacaoSit() != null){
			situacaoParecer = arel.getSituacaoSit().getDescricaoSit();
		}
		
		String ocultarObservacoesParecer = new ConfiguracaoDao(request).getConfiguracao().getIndOcultarObservacoesParecer();
		String labelSituacaoParecer = new ConfiguracaoDao(request).getConfiguracao().getLabelSituacaoParecer();
		
		if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")){
		
			builder.addClosedNode("parecer", 
					" caminhoImagem=\"" + builder.normalize(Util.normalizaCaracterMarcador(caminhoImagem)) + "\"" +
					" labelParecer=\"" + builder.normalize(Util.normalizaCaracterMarcador(labelParecer)) + "\"" +
					" descricao=\"" + builder.normalize(Util.normalizaCaracterMarcador(descricao)) + "\"" +
					" observacoes=\"" + builder.normalize(Util.normalizaCaracterMarcador(observacoes)) + "\"" +
					" dataUltParecer=\"" + builder.normalize(Util.normalizaCaracterMarcador(dataUltParecer)) + "\"" +
					" labelSituacaoParecer=\"" + builder.normalize(Util.normalizaCaracterMarcador(labelSituacaoParecer)) + "\"" +
					" situacaoParecer=\"" + builder.normalize(Util.normalizaCaracterMarcador(situacaoParecer)) + "\"" + 
					"");
		}
		else{
			
			builder.addClosedNode("parecer", 
					" caminhoImagem=\"" + builder.normalize(Util.normalizaCaracterMarcador(caminhoImagem)) + "\"" +
					" labelParecer=\"" + builder.normalize(Util.normalizaCaracterMarcador(labelParecer)) + "\"" +
					" descricao=\"" + builder.normalize(Util.normalizaCaracterMarcador(descricao)) + "\"" +					
					" dataUltParecer=\"" + builder.normalize(Util.normalizaCaracterMarcador(dataUltParecer)) + "\"" +
					" labelSituacaoParecer=\"" + builder.normalize(Util.normalizaCaracterMarcador(labelSituacaoParecer)) + "\"" +
					" situacaoParecer=\"" + builder.normalize(Util.normalizaCaracterMarcador(situacaoParecer)) + "\"" + 
					"");
		}

		if(exibirComplemento){

			String arisSelecionados = Pagina.getParamStr(request, "arisSelecionados");
			List codArisSelecionados = new ArrayList();
			
			if(!"".equals(arisSelecionados)){
				String[] codAris = arisSelecionados.split(";");
				for(int i = 0; i < codAris.length; i++){
					if(!"".equals(codAris[i]) && !";".equals(codAris[i])){
						codArisSelecionados.add(Long.valueOf(codAris[i]));
					}
				}
			}				
			
			if("S".equals(Pagina.getParamStr(request, "indResultado")) && !"".equals(nomeItem)){
				geraXMLIndicadores(builder, arel.getAcompReferenciaItemAri(), false);
			}

			if("S".equals(Pagina.getParamStr(request, "evolucaoFinanceira")))
				geraXmlEvolucaoFinanceira(builder, arel.getAcompReferenciaItemAri());
			
			geraXMLEtapas(builder, arel);
			
			if(!"".equals(nomeItem)){
				geraXMLOcorrencias(builder, arel.getAcompReferenciaItemAri());
			}

			if("S".equals(Pagina.getParamStr(request, "indResultado"))){
				geraXmlItensFilhos(builder, arel, codArisSelecionados);
			}
		
		}
		
		builder.closeNode("item");
		return retorno;
	}
	
	/**
	 * Gera Xml Itens Filhos
	 * 
	 * @author aleixo
	 * @version 0.1 - 02/05/2007
	 * @param XmlBuilder builder
	 * @param AcompRelatorioArel arel
	 * @param List codArisSelecionados
	 * @throws ECARException
	 */
	private void geraXmlItensFilhos(XmlBuilder builder, AcompRelatorioArel arel, List codArisSelecionados) throws ECARException{
		builder.addNode("itensFilhos");
		//List descendentes = new ItemEstruturaDao(request).getDescendentes(arel.getAcompReferenciaItemAri().getItemEstruturaIett(), true);
		ItemEstruturaIett itemPai = arel.getAcompReferenciaItemAri().getItemEstruturaIett();
		TipoAcompanhamentoTa tipoAcompanhamento = arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getTipoAcompanhamentoTa();
		Long mes = Long.valueOf(arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getMesAref());
		Long ano = Long.valueOf(arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getAnoAref());
		List descendentes = acompRealFisicoDao.getArfsByIettAndTipoAcomp(itemPai, tipoAcompanhamento, config, null, mes, ano);
		
		//Agrupar descendentes por estrutura
		
		Collections.sort(descendentes, new Comparator(){
			public int compare(Object arg0, Object arg1) {
				IettArfBean ia1 = (IettArfBean) arg0;
				IettArfBean ia2 = (IettArfBean) arg1;
				
				ItemEstruturaIett i1 = ia1.getItem();
				ItemEstruturaIett i2 = ia2.getItem();
				
				String ord1 = i1.getEstruturaEtt().getNomeEtt() + " " + i1.getNomeIett();
				String ord2 = i2.getEstruturaEtt().getNomeEtt() + " " + i2.getNomeIett();
				
				//return i1.getEstruturaEtt().getNomeEtt().compareTo(i2.getEstruturaEtt().getNomeEtt());
				return ord1.compareTo(ord2);
			}
		});

		Iterator itDes = descendentes.iterator();
		long codEtt = -1;
        while(itDes.hasNext()){
            //ItemEstruturaIett item = (ItemEstruturaIett) itDes.next();
        	IettArfBean iaBean = (IettArfBean) itDes.next();
        	ItemEstruturaIett item = iaBean.getItem();
        	
        	if(item.equals(itemPai)){
        		continue;
        	}
            
            AcompReferenciaItemAri acompanhamentoFilho = acompReferenciaItemDao.getAcompReferenciaItemByItemEstruturaIett(arel.getAcompReferenciaItemAri().getAcompReferenciaAref(), item);
            if(acompanhamentoFilho != null && !codArisSelecionados.contains(acompanhamentoFilho.getCodAri().toString())) {
            	
            	//FIXME: ver com Beier regra de ARF anteriores!!!
	            List listARF = acompRealFisicoDao.buscarPorIett(
	            		acompanhamentoFilho.getItemEstruturaIett().getCodIett(),
	            		Long.valueOf(acompanhamentoFilho.getAcompReferenciaAref().getMesAref()),
	            		Long.valueOf(acompanhamentoFilho.getAcompReferenciaAref().getAnoAref()));
	            if(listARF != null && !listARF.isEmpty()){
	            	String nomeEstrutura = "";
	            	
	            	if(codEtt != item.getEstruturaEtt().getCodEtt().longValue()){
	            		codEtt = item.getEstruturaEtt().getCodEtt().longValue();
	            		nomeEstrutura = item.getEstruturaEtt().getNomeEtt();
	            	}
	            	
	            	geraXMLIndicadoresFilhos(builder, acompanhamentoFilho, nomeEstrutura);		            	
	            }
            }
        }
		builder.closeNode("itensFilhos");
	}
	
	/**
	 * Gera Hierarquia Xml.<br>
	 * 
	 * @author aleixo
     * @since N/C
     * @version N/C
	 * @param XmlBuilder builder
	 * @param ItemEstruturaIett item
	 * @param boolean quebrarPagina
	 * @return List
	 * @throws ECARException
	 */
	private List geraXmlHierarquia(XmlBuilder builder, ItemEstruturaIett item, boolean quebrarPagina) throws ECARException{
		List ascendentes =  itemEstruturaDao.getAscendentes(item);
		
		if(ascendentes != null && !ascendentes.isEmpty()){
			if(quebrarPagina)
				builder.addNode("hierarquia", "quebrarPagina=\"S\"");
			else
				builder.addNode("hierarquia", "quebrarPagina=\"N\"");
			
			Iterator it = ascendentes.iterator();
			while(it.hasNext()){
				ItemEstruturaIett iettAsc = (ItemEstruturaIett) it.next();
				//System.out.println(iettAsc.getEstruturaEtt().getNomeEtt() + " - " + iettAsc.getNomeIett());
				String nomeItem = "";
				if(iettAsc.getSiglaIett() != null)
					nomeItem += "- ";
				nomeItem += iettAsc.getNomeIett();
				
				String siglaItem = iettAsc.getSiglaIett();
				
				builder.addClosedNode("itemHierarquia", "nomeItem=\"" + builder.normalize(Util.normalizaCaracterMarcador(nomeItem)) + "\" nivel=\"" + builder.normalize(Util.normalizaCaracterMarcador(iettAsc.getNivelIett().toString())) + "\" sigla=\"" + builder.normalize(Util.normalizaCaracterMarcador(siglaItem)) + "\"");
			}
			builder.closeNode("hierarquia");
		}
		return ascendentes;
	}


	/**
	 * Gera data rodape.<br>
	 * 
	 * @author aleixo
     * @since N/C
     * @version N/C
	 * @return String
	 */
	private String geraDataRodape(){
		Date dataAtual = Data.getDataAtual();
		String dia = String.valueOf(Data.getDia(dataAtual));
		String mes = Data.getNomeMesExtenso(Data.getMes(dataAtual) + 1).toLowerCase();
		String ano = String.valueOf(Data.getAno(dataAtual));
		String hora = Data.getHorario(dataAtual);
				
		return Data.getDiaSemanaNomeExtenso(dataAtual) + ", " + dia + " de " + mes + " de " + ano + ", às " + hora; 
	}
	
	/**
	 * Pega o nome do aquivo xsl.<br>
	 * 
	 * @author aleixo
     * @since N/C
     * @version N/C
	 * @return String
	 */
    public String getXslFileName() {
        return "relatorioAcompanhamento.xsl";
    }
    
    /**
     * Retorna a mensagem de erro para a página.<br>
     * 
     * @author aleixo
     * @since N/C
     * @version N/C
     * @param request
     * @param mensagem
     * @return String
     */
    public String getErrorPage(HttpServletRequest request, String mensagem){        
        String errorPage = "relAcompanhamento\\listaRelAcomp.jsp?msgOperacao=" + mensagem; 
        return errorPage;
    }
    
    /**
     * Gera Evolução financeira xml.<br>
     * 
     * @author aleixo
     * @since 0.1 - N/C
     * @version 2.0 - 10/04/2007
     * @param XmlBuilder builder
     * @param AcompReferenciaItemAri itemAri
     * @throws ECARException
     */
    private void geraXmlEvolucaoFinanceira(XmlBuilder builder, AcompReferenciaItemAri itemAri) throws ECARException{
    	try{
    		List listaExercicios = itemEstPrevDao.getListaExerciciosItemEstruturaPrevisao(itemAri.getItemEstruturaIett());
    		
    		
    		
	    	//List lista = itemEstPrevDao.getListaItemEstruturaPrevisao(itemAri.getItemEstruturaIett(), itemAri.getAcompReferenciaAref().getExercicioExe());
    		List lista = itemEstPrevDao.getListaItemEstruturaPrevisao(itemAri.getItemEstruturaIett(), null);
	    	Iterator it = lista.iterator();

	    	EfItemEstPrevisaoEfiep itemEstPrev = new EfItemEstPrevisaoEfiep();
	    	
	    	/* FIXME: Revisar esse trecho para, quando todos os valores forem zero, não apresentar 
	    	 * o quadro de evolução financeira.
	    	 * 
	    	 * Neste caso, exibir uma mensagem "Nenhum valor foi informado."
	    	 * 
	    	 *  Daqui até... */
	    	boolean possuiValores = false;
	    	while(it.hasNext()){
	    		itemEstPrev = (EfItemEstPrevisaoEfiep) it.next();
    			
    			/* ler EfItemEstContaEfiec */
    			EfItemEstContaEfiec itemEstConta = 
    					itemEstContaOrcDao.getItemEstruturaConta(
    						itemAri.getItemEstruturaIett(),
    						itemEstPrev.getExercicioExe(),
    						itemEstPrev.getFonteRecursoFonr(),
    						itemEstPrev.getRecursoRec());
    			
    			/* verificar valores em Efier */
    			Double[] valores = itemEstRealizadoDao.getSomaItemEstruturaRealizado(
    					itemEstConta,
    					itemEstPrev.getExercicioExe());

    			if(itemEstPrev.getValorAprovadoEfiep() != null && itemEstPrev.getValorAprovadoEfiep().doubleValue() > 0){
    				possuiValores = true;
    			}
    			if(itemEstPrev.getValorRevisadoEfiep() != null && itemEstPrev.getValorRevisadoEfiep().doubleValue() > 0){
    				possuiValores = true;
    			}
    			
    			for(int i = 0; i < 6; i++){
    				if(valores[i] != null && valores[i].doubleValue() > 0){
    					possuiValores = true;
    				}
    			}
	    	}
	    	/* ... aqui! */
	    	
	    	
	    	//if (it.hasNext()){
	    	if (possuiValores){

	    		int colunas = 0;
	    		int numeroColunasExibidas = 0; 

	    		String descFinanceiro[] = new String[3];
	    		boolean mostrarDescFinanceiro[] = new boolean[3];
	    		descFinanceiro[0] = config.getRecursoDescValor1Cfg();
	    		descFinanceiro[1] = config.getRecursoDescValor2Cfg();
	    		descFinanceiro[2] = config.getRecursoDescValor3Cfg();

	    		boolean mostrarValores[] = new boolean[6];

	    		String descricoes[] = new String[6];
	    		descricoes[0] = config.getFinanceiroDescValor1Cfg();
	    		descricoes[1] = config.getFinanceiroDescValor2Cfg();
	    		descricoes[2] = config.getFinanceiroDescValor3Cfg();
	    		descricoes[3] = config.getFinanceiroDescValor4Cfg();
	    		descricoes[4] = config.getFinanceiroDescValor5Cfg();
	    		descricoes[5] = config.getFinanceiroDescValor6Cfg();
	    		
				for(int i = 0; i < 6; i++){
	    			mostrarValores[i] = itemEstRealizadoDao.getVerificarMostrarValorByPosicaoCfg(i);
	    			if(mostrarValores[i]){
	    				colunas++;
	    			}
	    		}	    
				
	    		builder.addNode("evolucaoFinanceira", "nenhumValor='N' colunasRealizadas=\"" + builder.normalize(Util.normalizaCaracterMarcador(String.valueOf(colunas))) + "\"");
	    		
	    		
	    		builder.addNode("colunas");
	    		
				builder.addClosedNode("coluna", "nome=\"Categoria Econômica\"");
				builder.addClosedNode("coluna", "nome=\"Fonte\"");
				//builder.addClosedNode("coluna", "nome=\"Aprovado\"");
				//builder.addClosedNode("coluna", "nome=\"Revisado\"");
	    		//int numeroColunasExibidas = 2; //Aprovado - Revisado 

	    		for(int i = 0; i < 3; i++){
	    			mostrarDescFinanceiro[i] = itemEstRealizadoDao.getVerificarMostrarRecursoByPosicaoCfg(i);
	    			if(mostrarDescFinanceiro[i]){
	    				builder.addClosedNode("coluna", "nome=\"" + builder.normalize(Util.normalizaCaracterMarcador(descFinanceiro[i])) + "\"");
	    				numeroColunasExibidas++;
	    			}
	    		}
				
				for(int i = 0; i < 6; i++){
	    			mostrarValores[i] = itemEstRealizadoDao.getVerificarMostrarValorByPosicaoCfg(i);
	    			if(mostrarValores[i]){
	    				builder.addClosedNode("coluna", "nome=\"" + builder.normalize(Util.normalizaCaracterMarcador(descricoes[i])) + "\"");
	    				numeroColunasExibidas++;
	    			}
	    		}	    		

				builder.closeNode("colunas");

				/*
				 * 22.50cm / numeroColunasExibidas = tamanho de cada coluna dinâmica
				 * Quanto mais colunas, menos espaço no relatório.
				 */
				double t = 22.50;
				String tam = String.valueOf(t/numeroColunasExibidas) + "cm";
				
				builder.addNode("colunasHeader");
				for(int x = 1; x <= numeroColunasExibidas; x++){
					builder.addClosedNode("colunaHeader", "tamanho=\"" + builder.normalize(Util.normalizaCaracterMarcador(tam)) + "\"");
				}
				builder.closeNode("colunasHeader");
	    		
				double totFonAprovado = 0, totFonRevisado = 0;
				double totGerAprovado = 0, totGerRevisado = 0;
				double[] totFonValor = new double[6];
				double[] totGerValor = new double[6];
				
				/*Inicializar os valores...*/
				for(int i = 0; i < 6; i++){
					totFonValor[i] = 0;
					totGerValor[i] = 0;
				}
				
				
				Iterator itExe = listaExercicios.iterator();
				while(itExe.hasNext()){
					ExercicioExe exercicio = (ExercicioExe) itExe.next();
					String exercicioDesc = exercicio.getDescricaoExe();
					String exercicioDescTotal = exercicioDesc;
					double totExeAprovado = 0, totExeRevisado = 0; 
					double[] totExeValor = new double[6];
					
					/*Inicializar os valores...*/
					for(int i = 0; i < 6; i++){
						totExeValor[i] = 0;
					}
					
					
					lista = itemEstPrevDao.getListaItemEstruturaPrevisao(itemAri.getItemEstruturaIett(), exercicio);
					it = lista.iterator();
					long codFonte = -1;
		    		while(it.hasNext()){
		    			itemEstPrev = (EfItemEstPrevisaoEfiep) it.next();
		    			
		    			/* ler EfItemEstContaEfiec */
		    			EfItemEstContaEfiec itemEstConta = 
		    					itemEstContaOrcDao.getItemEstruturaConta(
		    						itemAri.getItemEstruturaIett(),
		    						exercicio,
		    						itemEstPrev.getFonteRecursoFonr(),
		    						itemEstPrev.getRecursoRec());
		    			
		    			/* verificar valores em Efier */
		    			Double[] valores = itemEstRealizadoDao.getSomaItemEstruturaRealizado(
		    					itemEstConta,
		    					exercicio);
		    			
		    			/* Ao trocar a fonte e ao iniciar imprimir linha de fonte */
		    			if(codFonte != itemEstPrev.getFonteRecursoFonr().getCodFonr().longValue()){
		    				/* se não é a primeira passada da fonte */
		    				if(codFonte != -1){
	
		    					builder.closeNode("fonte");
	
		    					/* zerar os valores do total da fonte */
		    					totFonAprovado = 0;
		    					totFonRevisado = 0;
	
		    					for(int i = 0; i < 6; i++){
		    						totFonValor[i] = 0;
		    					}
		    				}
		    				
		    				codFonte = itemEstPrev.getFonteRecursoFonr().getCodFonr().longValue();
		    				//IMPRIMIR FONTE
		    				builder.addNode("fonte", "nome=\"" + builder.normalize(Util.normalizaCaracterMarcador(itemEstPrev.getFonteRecursoFonr().getNomeFonr()))+ "\" exercicio=\"" + builder.normalize(Util.normalizaCaracterMarcador(exercicioDesc)) + "\"");
		    				exercicioDesc = "";
		    			}
		    			
		    			/* somar nos valores do total da fonte */
		    			if(itemEstPrev.getValorAprovadoEfiep() != null){
		    				totFonAprovado = totFonAprovado + itemEstPrev.getValorAprovadoEfiep().doubleValue();
		    				totExeAprovado = totExeAprovado + itemEstPrev.getValorAprovadoEfiep().doubleValue();
		    			}
		    			if(itemEstPrev.getValorRevisadoEfiep() != null){
		    				totFonRevisado = totFonRevisado + itemEstPrev.getValorRevisadoEfiep().doubleValue();
		    				totExeRevisado = totExeRevisado + itemEstPrev.getValorRevisadoEfiep().doubleValue();
		    			}
	
		    			for(int i = 0; i < 6; i++){
							if(mostrarValores[i]){
								totFonValor[i] = totFonValor[i] + valores[i].doubleValue();
								totExeValor[i] = totExeValor[i] + valores[i].doubleValue();
							}
		    			}
		    			
		    			//IMPRIMIR RECURSO
						builder.addNode("recurso", "nome=\"" + builder.normalize(Util.normalizaCaracterMarcador(itemEstPrev.getRecursoRec().getNomeRec())) + "\"");
						//builder.addClosedNode("rec", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(itemEstPrev.getValorAprovadoEfiep().doubleValue()))+ "\"");
						//builder.addClosedNode("rec", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(itemEstPrev.getValorRevisadoEfiep().doubleValue()))+ "\"");
						
		    			for(int i = 0; i < 3; i++){
							if(mostrarDescFinanceiro[i]){
								if(i == 0)
									builder.addClosedNode("rec", "valor=\"" + builder.normalize(Pagina.trocaNullNumeroSemDecimal(itemEstPrev.getValorAprovadoEfiep())) + "\"");
								if(i == 1)
									builder.addClosedNode("rec", "valor=\"" + builder.normalize(Pagina.trocaNullNumeroSemDecimal(itemEstPrev.getValorRevisadoEfiep())) + "\"");
							}
						}
						
						for(int i = 0; i < 6; i++){
							if(mostrarValores[i])
		    					builder.addClosedNode("rec", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(valores[i].doubleValue()))+ "\"");
						}
						builder.closeNode("recurso");
		    		}
		    		
					//builder.closeNode("fonte");

					/* somar nos valores do total geral */
					totGerAprovado = totGerAprovado + totExeAprovado;
					totGerRevisado = totGerRevisado + totExeRevisado;
					
					for(int i = 0; i < 6; i++){
						if(mostrarValores[i])
							totGerValor[i] = totGerValor[i] + totExeValor[i];
					}
		    		
		    		
		    		//IMPRIMIR TOTAL
					builder.addNode("totalEvolucaoFinanceiraExercicio", "exercicio=\"" + builder.normalize(Util.normalizaCaracterMarcador(exercicioDescTotal)) + "\"");
					//builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totGerAprovado))+ "\"");
					//builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totGerRevisado))+ "\"");
					
	    			for(int i = 0; i < 3; i++){
						if(mostrarDescFinanceiro[i]){
							if(i == 0)
								builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totExeAprovado))+ "\"");
							if(i == 1)
								builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totExeRevisado))+ "\"");
						}
					}
					
					for(int i = 0; i < 6; i++){
						if(mostrarValores[i])
	    					builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totExeValor[i]))+ "\"");
					}
					builder.closeNode("totalEvolucaoFinanceiraExercicio");

					builder.closeNode("fonte");
				}
				
	    		//IMPRIMIR TOTAL
				builder.addNode("totalEvolucaoFinanceira");
				//builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totGerAprovado))+ "\"");
				//builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totGerRevisado))+ "\"");
				
    			for(int i = 0; i < 3; i++){
					if(mostrarDescFinanceiro[i]){
						if(i == 0)
							builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totGerAprovado))+ "\"");
						if(i == 1)
							builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totGerRevisado))+ "\"");
					}
				}
				
				for(int i = 0; i < 6; i++){
					if(mostrarValores[i])
    					builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totGerValor[i]))+ "\"");
				}
				builder.closeNode("totalEvolucaoFinanceira");
				
				builder.closeNode("evolucaoFinanceira");
	    	}
	    	else {
	    		builder.addClosedNode("evolucaoFinanceira", "nenhumValor='S' colunasRealizadas='0'");
	    	}
    	} catch (HibernateException e){
    		this.logger.error(e);
    		throw new ECARException("Erro na criação do relatório: Evolução Financeira - " + e.getMessage());
    	}
    }

    /**
     * Gera indicadores xml.<br>
     * 
     * @author aleixo
     * @since N/C
     * @version N/C
     * @param XmlBuilder builder
     * @param AcompReferenciaItemAri itemAri
     * @throws ECARException
     */
    private void geraXMLIndicadores(XmlBuilder builder, AcompReferenciaItemAri itemAri, boolean filho) throws ECARException{
    	try {
    		if(itemAri != null) {

    			String mostrarProjecao = ("S".equals(Pagina.getParamStr(request, "indProjecao"))) ? "S" : "N";
    			String itemFilho = filho ? "S" : "N";
    			
    			//List<ExercicioExe> exercicios = new ExercicioDao(null).getExerciciosProjecao(itemAri.getItemEstruturaIett().getCodIett());
    			//Ref. mantis 7770: exibir os últimos quatro anos...
    			//Pegando exercicios do mais novo para mais antigo para pegar os ultimos anos primeiro...
    			//List<ExercicioExe> todosExercicios = new ExercicioDao(null).listar(ExercicioExe.class, new String[] {"dataInicialExe","desc"});
    			
    			//Mantis #11071: Ajustes no Relatório do PPA (metas físicas de exercícios)
    			List<ExercicioExe> todosExercicios = new ExercicioDao(null).getExeByPerExe(itemAri.getAcompReferenciaAref().getExercicioExe());
    			List<ExercicioExe> exercicios = new ArrayList<ExercicioExe>();

    			if(todosExercicios == null || todosExercicios.isEmpty()) {
    				throw new Exception("Não existe exercícios cadastrado");
    			}
    			//Obtendo ultimos 4 anos...
    			int qtdeAnos = 4;
    			if(todosExercicios.size() < qtdeAnos) {
    				qtdeAnos = todosExercicios.size(); 
    			}
    			exercicios.addAll(todosExercicios.subList(0, qtdeAnos));
    			
    			//Re-ordenando do mais antigo para o o mais novo... 
    			Collections.reverse(exercicios);
    			
				List indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(itemAri, Dominios.TODOS, false);
				if(indResultados != null && indResultados.size() > 0){

					builder.addNode("indicadores", 
							"labelFuncao=\"" + builder.normalize(Util.normalizaCaracterMarcador(estFuncDao.getLabelIndicadoresResultado(itemAri.getItemEstruturaIett().getEstruturaEtt()))) + 
							"\" mostrarProjecao=\"" + builder.normalize(Util.normalizaCaracterMarcador(mostrarProjecao)) + 
							"\" filho=\"" + builder.normalize(Util.normalizaCaracterMarcador(itemFilho)) + "\"");
					
					int numeroExercicios = 0;
					for(ExercicioExe exercicio : exercicios){
						builder.addClosedNode("columnExercicio", "ano=\"" + builder.normalize(Util.normalizaCaracterMarcador(exercicio.getDescricaoExe())) + "\"");
						builder.addClosedNode("indExercicio", "exercicio=\"" + builder.normalize(Util.normalizaCaracterMarcador(exercicio.getDescricaoExe())) + "\"");
						numeroExercicios++;
					}
					
					Iterator itIndResult = indResultados.iterator();
					String grupoIndicador = "Indicador";
					String exibirGrupoIndicador = "N";
					while(itIndResult.hasNext()){
						AcompRealFisicoArf indicador = (AcompRealFisicoArf) itIndResult.next();
						String tipoQtde = indicador.getItemEstrtIndResulIettr().getIndTipoQtde();
						String situacao = "";
						
						exibirGrupoIndicador = "N";
						if(config.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null){
							if(indicador.getItemEstrtIndResulIettr().getSisAtributoSatb() != null && !grupoIndicador.equals(indicador.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb())){
								grupoIndicador = indicador.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb();
								exibirGrupoIndicador = "S";
							}
							else if(indicador.getItemEstrtIndResulIettr().getSisAtributoSatb() == null && !"".equals(grupoIndicador)) {
								exibirGrupoIndicador = "S";
							}
						}
						
						
						if(indicador.getSituacaoSit()!=null) {
							situacao = indicador.getSituacaoSit().getDescricaoSit();
						}

						double totalRealizado = 0;
						double totalPrevisto = 0;
						
						builder.addNode("indicador", 
//	                         	"nome=\"" + builder.normalize(indicador.getItemEstrtIndResulIettr().getNomeIettir()) + "\"" +
								"nome=\"" + builder.normalize(Util.normalizaCaracterMarcador(indicador.getItemEstrtIndResulIettr().getNomeIettir() + " (" + indicador.getItemEstrtIndResulIettr().getUnidMedidaIettr())) + ")" + "\"" + 
	                         	" situacao=\"" + builder.normalize(Util.normalizaCaracterMarcador(situacao)) + "\"" +
//	                         	" realizadoNoMes=\"" + builder.normalize(Pagina.trocaNullMoeda(indicador.getQtdRealizadaArf()) + " " + indicador.getItemEstrtIndResulIettr().getUnidMedidaIettr()) + "\"" +
	                         	" realizadoNoMes=\"" + builder.normalize(Pagina.trocaNullNumeroSemDecimal(indicador.getQtdRealizadaArf())) + "\"" +
	                         	" numeroExercicios=\"" + builder.normalize(String.valueOf(numeroExercicios)) + "\"" +
	                         	" mostrarProjecao=\"" + builder.normalize(Util.normalizaCaracterMarcador(mostrarProjecao)) + "\"" +
	                         	" grupoIndicador=\"" + builder.normalize(Util.normalizaCaracterMarcador(grupoIndicador)) + "\"" +
	                         	" exibirGrupoIndicador=\"" + builder.normalize(Util.normalizaCaracterMarcador(exibirGrupoIndicador)) + "\"" +
	                         	" numeroExe=\"" + builder.normalize(String.valueOf(numeroExercicios)) + "\""
	                         	);
						
						if(indicador.getItemEstrtIndResulIettr().getSisAtributoSatb() == null && !"".equals(grupoIndicador)) {
							grupoIndicador = "";
						}
						
						List valoresR = new ArrayList();
						List valoresP = new ArrayList();

						for(ExercicioExe exercicio : exercicios){
							double realizadoNoExercicio = 0;
							
							//Comentado por aleixo 18/04/2007
//							if(exercicio.equals(itemAri.getAcompReferenciaAref().getExercicioExe())){
//								// Se o exercício em questão é mesmo exercicio do periodo de referência e o indicador for acumulável
//								// soma todas as quantidades até o mes/ano do periodo
//								if("S".equals(indicador.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
//									AcompReferenciaAref aref = itemAri.getAcompReferenciaAref();
//									int mesRef = Integer.valueOf(aref.getMesAref()).intValue();
//									int anoRef = Integer.valueOf(aref.getAnoAref()).intValue();
//									realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicio(exercicio, indicador.getItemEstrtIndResulIettr(), mesRef, anoRef);																					
//								} else {
//									//se não for acumulável o realizado no exercicio é o realizado no periodo
//									/*
//									if (indicador.getQtdRealizadaArf() != null)
//										realizadoNoExercicio = indicador.getQtdRealizadaArf().doubleValue();
//									*/
//									realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicioNaoAcumulavel(exercicio, indicador.getItemEstrtIndResulIettr(), itemAri.getAcompReferenciaAref());
//								}
//							} else {
//								if("S".equals(indicador.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
//									realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicio(exercicio, indicador.getItemEstrtIndResulIettr(), itemAri.getAcompReferenciaAref());										
//								} else {
//									realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicioNaoAcumulavel(exercicio, indicador.getItemEstrtIndResulIettr(), itemAri.getAcompReferenciaAref());																						
//								}
//							} 

							if("S".equals(indicador.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
								realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicio(exercicio, indicador.getItemEstrtIndResulIettr(), itemAri.getAcompReferenciaAref());										
							} else {
								realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicioNaoAcumulavel(exercicio, indicador.getItemEstrtIndResulIettr(), itemAri.getAcompReferenciaAref());																						
							}

							double previstoNoExercicio = new ItemEstrtIndResulDao(null).getQtdPrevistoExercicio(indicador.getItemEstrtIndResulIettr(), exercicio);
							if("S".equals(indicador.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
								totalRealizado += realizadoNoExercicio;
								totalPrevisto += previstoNoExercicio;
							}else{
								totalRealizado = realizadoNoExercicio;
								totalPrevisto = previstoNoExercicio;											
							}
							
							valoresR.add(realizadoNoExercicio);
							valoresP.add(previstoNoExercicio);

							String strPrevistoExe = "";
							String strRealizadoExe = "";
							if ("Q".equalsIgnoreCase(tipoQtde)){ //Quantidade --> sem casas decimais
								strPrevistoExe = Pagina.trocaNullNumeroSemDecimal(previstoNoExercicio);
								strRealizadoExe = Pagina.trocaNullNumeroSemDecimal(realizadoNoExercicio);
							}
							else {
								strPrevistoExe = Pagina.trocaNullMoeda(previstoNoExercicio);
								strRealizadoExe = Pagina.trocaNullMoeda(realizadoNoExercicio);
							}
							
							builder.addClosedNode("valorExercicio", 
								"exercicio=\"" + builder.normalize(Util.normalizaCaracterMarcador(exercicio.getDescricaoExe())) + "\"" + 
//								" valorPrevisto=\"" + builder.normalize(Pagina.trocaNullNumeroSemDecimal(previstoNoExercicio)) + "\"" +
//								" valorRealizado=\"" + builder.normalize(Pagina.trocaNullNumeroSemDecimal(realizadoNoExercicio)) + "\"");								
								" valorPrevisto=\"" + builder.normalize(strPrevistoExe) + "\"" +
								" valorRealizado=\"" + builder.normalize(strRealizadoExe) + "\"");								
						}
						
						Collections.reverse(valoresR);
						Collections.reverse(valoresP);
						
						totalPrevisto = acompRealFisicoDao.getSomaValoresArfs(indicador.getItemEstrtIndResulIettr(), valoresP).doubleValue();
						totalRealizado = acompRealFisicoDao.getSomaValoresArfs(indicador.getItemEstrtIndResulIettr(), valoresR).doubleValue();
						
						double realizadoPrevisto = 0;
						if(totalPrevisto > 0) {
							realizadoPrevisto = ((totalRealizado/totalPrevisto) * 100);
						}
						
						String strTotalPrevisto = "";
						String strTotalRealizado = "";
						if ("Q".equalsIgnoreCase(tipoQtde)){ //Quantidade --> sem casas decimais
							strTotalPrevisto = Pagina.trocaNullNumeroSemDecimal(totalPrevisto);
							strTotalRealizado = Pagina.trocaNullNumeroSemDecimal(totalRealizado);
						}
						else {
							strTotalPrevisto = Pagina.trocaNullMoeda(totalPrevisto);
							strTotalRealizado = Pagina.trocaNullMoeda(totalRealizado);
						}
						
						builder.addClosedNode("valorTotal", 
	                         	" percentualRealizadoPrevisto=\"" + builder.normalize(Pagina.trocaNullNumeroDecimalSemMilhar(new Double((realizadoPrevisto)))) + "\"" +
//	                         	" totalPrevisto=\"" + builder.normalize(Pagina.trocaNullNumeroSemDecimal(Double.valueOf(totalPrevisto))) + "\"" +
//	                         	" totalRealizado=\"" + builder.normalize(Pagina.trocaNullNumeroSemDecimal(Double.valueOf(totalRealizado))) + "\""
	                         	" totalPrevisto=\"" + builder.normalize(strTotalPrevisto) + "\"" +
	                         	" totalRealizado=\"" + builder.normalize(strTotalRealizado) + "\""
	                         	);
						

						String strProjecao = "";
						String imagemProjecao = "";
						String strPorcentual = "-";

						//deve ter valores previstos, se não tiver gerará um null pointer
						if("S".equals(indicador.getItemEstrtIndResulIettr().getIndProjecaoIettr()) && totalRealizado > 0 && 
								indicador.getItemEstrtIndResulIettr().getItemEstrutFisicoIettfs().size() > 0){
							
							double resultado = acompReferenciaItemDao.calculoProjecao(indicador.getItemEstrtIndResulIettr(), itemAri);
							ExercicioExe exercicioPrevisto = itemEstrtIndResulDao.getMaiorExercicioIndicador(indicador.getItemEstrtIndResulIettr());
							double qtdePrevista = itemEstrtIndResulDao.getQtdPrevistoExercicio(indicador.getItemEstrtIndResulIettr(), exercicioPrevisto);
							
							Mensagem msg = new Mensagem(this.request.getSession().getServletContext());

							if(resultado == qtdePrevista){
								strProjecao = msg.getMensagem("acompRelatorio.indicadorResultado.projecao.seraAtingida");
								imagemProjecao = pathEcar + "/images/relAcomp/previsto_emtempo.gif";
							}
							if(resultado > qtdePrevista){
								strProjecao = msg.getMensagem("acompRelatorio.indicadorResultado.projecao.seraAtingidaAntes");
								imagemProjecao = pathEcar + "/images/relAcomp/previsto_antestempo.gif";
							}
							if(resultado < qtdePrevista){
								strProjecao = msg.getMensagem("acompRelatorio.indicadorResultado.projecao.naoSeraAtingida");																									
								imagemProjecao = pathEcar + "/images/relAcomp/previsto_depoistempo.gif";
							}
							
							Double porcentagem = new Double((resultado/qtdePrevista) * 100);
							
							strPorcentual = Pagina.trocaNullNumeroDecimalSemMilhar(porcentagem);
						} else {
							if(totalRealizado == 0){
								strProjecao = "Não é possível realizar projeção sem informação de quantidades realizadas.";										
								imagemProjecao = pathEcar + "/images/relAcomp/previsto_semquantidades.gif";
							} else {
								strProjecao = "N/A";
								imagemProjecao = pathEcar + "/images/relAcomp/previsto_naopermite.gif";
							}
						}
						
						builder.addClosedNode("valorProjecao",
	                         	" projecao=\"" + builder.normalize(Util.normalizaCaracterMarcador(strProjecao)) + "\"" +
	                         	" imagemProjecao=\"" + builder.normalize(Util.normalizaCaracterMarcador(imagemProjecao)) + "\"" + 
	                         	" mostrarProjecao=\"" + builder.normalize(Util.normalizaCaracterMarcador(mostrarProjecao)) + "\"" +
	                         	" percentual=\"" + builder.normalize(Util.normalizaCaracterMarcador(strPorcentual)) + "\"" +
	                         	" situacao=\"" + builder.normalize(Util.normalizaCaracterMarcador(situacao)) + "\""
								);
						
						
						builder.closeNode("indicador");

					}
					
					geraXMLLegendaIndicadores(builder, new Mensagem(this.request.getSession().getServletContext()));
					
					builder.closeNode("indicadores");
				}
			}

    	} catch(Exception e){
    		this.logger.error(e);
    		throw new ECARException("Erro na criação do relatório: Indicadores - " + e.getMessage());               
    	}
    }

    /**
     *
     * @param builder
     * @param msg
     */
    public void geraXMLLegendaIndicadores(XmlBuilder builder, Mensagem msg){
    	builder.addNode("legendaIndicadores");
    	
    	builder.addClosedNode("legenda", 
    			"imagem=\"" + builder.normalize(pathEcar + "/images/relAcomp/previsto_antestempo.gif") + "\"" + 
    			" descricao=\"" + builder.normalize(msg.getMensagem("acompRelatorio.indicadorResultado.projecao.seraAtingidaAntes")) + "\"" + 
    			"");
    	builder.addClosedNode("legenda", 
    			"imagem=\"" + builder.normalize(pathEcar + "/images/relAcomp/previsto_emtempo.gif") + "\"" + 
    			" descricao=\"" + builder.normalize(msg.getMensagem("acompRelatorio.indicadorResultado.projecao.seraAtingida")) + "\"" + 
    			"");
    	builder.addClosedNode("legenda", 
    			"imagem=\"" + builder.normalize(pathEcar + "/images/relAcomp/previsto_depoistempo.gif") + "\"" + 
    			" descricao=\"" + builder.normalize(msg.getMensagem("acompRelatorio.indicadorResultado.projecao.naoSeraAtingida")) + "\"" + 
    			"");
    	builder.addClosedNode("legenda", 
    			"imagem=\"" + builder.normalize(pathEcar + "/images/relAcomp/previsto_semquantidades.gif") + "\"" + 
    			" descricao=\"" + builder.normalize("Não é possível realizar projeção sem informação de quantidades realizadas.") + "\"" + 
    			"");
    	builder.addClosedNode("legenda", 
    			"imagem=\"" + builder.normalize(pathEcar + "/images/relAcomp/previsto_naopermite.gif") + "\"" + 
    			" descricao=\"" + builder.normalize("Não permite projeção") + "\"" + 
    			"");
    	builder.closeNode("legendaIndicadores");
    }
    
    /**
     * Gera indicadores xml filhos!.<br>
     * 
     * @author aleixo
     * @since N/C
     * @version N/C
     * @param XmlBuilder builder
     * @param AcompReferenciaItemAri itemAri
     * @param String nomeEstrutura
     * @throws ECARException
     */
    private void geraXMLIndicadoresFilhos(XmlBuilder builder, AcompReferenciaItemAri itemAri, String nomeEstrutura) throws ECARException{
    	try {
    		String nomeItem = itemAri.getItemEstruturaIett().getNomeIett();
			String siglaOrg = "";
			
			if(itemAri.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
				siglaOrg = itemAri.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg();
			}
			else {
				siglaOrg = "Órgão não informado";
			}

    		builder.addNode("itemFilho", 
    				"nomeItem=\"" + builder.normalize(Util.normalizaCaracterMarcador(nomeItem)) + "\"" +
    				" orgaoItem=\"" + builder.normalize(Util.normalizaCaracterMarcador(siglaOrg)) + "\"" +
    				" estruturaItem=\"" + builder.normalize(Util.normalizaCaracterMarcador(nomeEstrutura)) + "\"" + 
    				"");
    		geraXMLIndicadores(builder, itemAri, true);
			builder.closeNode("itemFilho");

    	} catch(Exception e){
    		this.logger.error(e);
    		throw new ECARException("Erro na criação do relatório: IndicadoresFilhos - " + e.getMessage());               
    	}
    }
    
    
    /**
     * Gera ocorrencias xml.<br>
     * 
     * @author aleixo
     * @since N/C
     * @version N/C
     * @param XmlBuilder builder
     * @param AcompReferenciaItemAri itemAri
     * @throws ECARException
     */
    
    private void geraXMLOcorrencias(XmlBuilder builder, AcompReferenciaItemAri itemAri) throws ECARException{
    	try {
    		//Ocorrências = getItemEstruturaIett().getItemEstrutAcaoIettas() 
    		List<ItemEstrutAcaoIetta> ocorrencias = new ArrayList(itemAri.getItemEstruturaIett().getItemEstrutAcaoIettas());
    		if(!itemAri.getItemEstruturaIett().getItemEstrutAcaoIettas().isEmpty())
    			ocorrencias = new ArrayList(itemAri.getItemEstruturaIett().getItemEstrutAcaoIettas());
    		
    		if(ocorrencias != null && !ocorrencias.isEmpty()){
    			//Ordenando pela Data
    			Collections.sort(ocorrencias, new Comparator(){

					public int compare(Object arg0, Object arg1) {
						ItemEstrutAcaoIetta i1 = (ItemEstrutAcaoIetta) arg0;
						ItemEstrutAcaoIetta i2 = (ItemEstrutAcaoIetta) arg1;
						
						if(i1.getDataIetta() == null && i2.getDataIetta() == null)
							return 0;
						if(i1.getDataIetta() != null && i2.getDataIetta() == null)
							return -1;
						if(i1.getDataIetta() == null && i2.getDataIetta() != null)
							return 1;
						
						return i1.getDataIetta().compareTo(i2.getDataIetta());
					}
    				
    			});
    			
    			Collections.reverse(ocorrencias);//inverto a lista para ordenar do mais recente para o mais antigo
    			
    			builder.addNode("ocorrencias", "labelFuncao=\"" + builder.normalize(Util.normalizaCaracterMarcador(estFuncDao.getLabelOcorrencias(itemAri.getItemEstruturaIett().getEstruturaEtt()))) + "\"");
    			for(ItemEstrutAcaoIetta ocorrencia : ocorrencias){
    				// ignorar registro inativo
    				if(Dominios.NAO.equals(ocorrencia.getIndAtivoIetta())) {
    					continue;
    				}
    				String data = Data.parseDate(ocorrencia.getDataIetta());
    				String descricao = ocorrencia.getDescricaoIetta();
    				builder.addClosedNode("ocorrencia",
    						"data=\"" + builder.normalize(data) + "\"" + 
    						" descricao=\"" + builder.normalize(Util.normalizaCaracterMarcador(descricao)) + "\""
    					);
    			}
    			builder.closeNode("ocorrencias");
    		}

    	} catch(Exception e){
    		this.logger.error(e);
    		throw new ECARException("Erro na criação do relatório: Ocorrencias - " + e.getMessage());               
    	}
    }
    
    /**
     * Gera etapas xml.<br>
     * 
     * @author aleixo
     * @version 0.2 - 26/03/2007
     * @since 0.1 - 15/03/2007
     * @param XmlBuilder builder
     * @param AcompRelatorioArel arel
     * @throws ECARException
     */
    private void geraXMLEtapas(XmlBuilder builder, AcompRelatorioArel arel) throws ECARException{

    	ItemEstruturaIett item = arel.getAcompReferenciaItemAri().getItemEstruturaIett();
    	
    	List ettEtapas = estruturaDao.getEstruturasEtapas(item.getEstruturaEtt());

    	String labelFuncao = "";
    	if(ettEtapas != null && ettEtapas.size() > 1){
    		labelFuncao = "Etapas:";
    	}
    	
    	builder.addNode("etapas", 
    			"labelFuncao=\"" + builder.normalize(Util.normalizaCaracterMarcador(labelFuncao)) + "\"");
    	if(ettEtapas != null && !ettEtapas.isEmpty()){
    		Iterator itEttEtapas = ettEtapas.iterator();
    		while(itEttEtapas.hasNext()){
    			EstruturaEtt ettEtapa = (EstruturaEtt) itEttEtapas.next();

    			List etapas = new ArrayList();
    			List colunas = estruturaDao.getAtributosAcessoEstrutura(ettEtapa);
    			
				if(colunas != null && colunas.size() > 0) {
					//etapas = itemEstruturaDao.getItensFilho(item, ettEtapa, ((ObjetoEstrutura)colunas.get(0)).iGetNomeOrdenarLista());
					etapas = itemEstruturaDao.getItensFilho(item, ettEtapa, colunas);
				}
				else {
					etapas = itemEstruturaDao.getItensFilho(item, ettEtapa, "");
				}
    			
				if(etapas != null && !etapas.isEmpty()){
				
					/*
					 * Testando se existem valores para etapa
					 */
					boolean apresentarEtapa = false;
					for(Iterator it = etapas.iterator(); it.hasNext();){
    					ItemEstruturaIett etapa = (ItemEstruturaIett) it.next();
    					if(etapa.getEstruturaEtt().equals(ettEtapa)){
    						apresentarEtapa = true;
    					}
					}
					
					if(!apresentarEtapa){
						continue;
					}
					
	    			builder.addNode("etapa", "nomeEtapa=\"" + builder.normalize(Util.normalizaCaracterMarcador(ettEtapa.getNomeEtt())) + "\"");

	    			geraXMLColunasEtapa(builder, colunas);
	    			if(colunas != null && !colunas.isEmpty()){
	    				Iterator itEtapa = etapas.iterator();
	    	    		String corFundo = "#EEE9E9";
	    				while(itEtapa.hasNext()){
	    					ItemEstruturaIett etapa = (ItemEstruturaIett) itEtapa.next();
	    					if(etapa.getEstruturaEtt().equals(ettEtapa)){
	    						geraXMLValoresEtapa(builder, etapa, colunas, corFundo);

	    						if(!"".equals(corFundo)){
		    						corFundo = "";
		    					}
		    					else {
		    						corFundo = "#EEE9E9";
		    					}
	    					}
	    				}
	    			}
	    			builder.closeNode("etapa");
				}
    		}
    	}
    	
        builder.closeNode("etapas");
    }
    
    /**
     * Gera colunas da etapa.<br>
     * 
     * @author aleixo
     * @version 0.2 - 26/03/2007, 0.1 - 23/03/2007
     * @param builder
     * @param colunas
     * @throws ECARException
     */
    public void geraXMLColunasEtapa(XmlBuilder builder, List colunas) throws ECARException{
    	if(colunas != null && !colunas.isEmpty()){
    		Iterator itColunas = colunas.iterator();
			while (itColunas.hasNext()){
				ObjetoEstrutura coluna = (ObjetoEstrutura) itColunas.next();
				//double larguraReal = ((315 * coluna.iGetLargura().intValue()) / (100));
				double larguraReal = ((250 * coluna.iGetLargura().intValue()) / (100));
				String largura = String.valueOf(larguraReal) + "mm";
				String label = coluna.iGetLabel();
				
				builder.addClosedNode("etapasColunaHeader", "largura=\"" + builder.normalize(Util.normalizaCaracterMarcador(largura)) + "\"");
				builder.addClosedNode("etapasColuna", "label=\"" + builder.normalize(Util.normalizaCaracterMarcador(label)) + "\"");
			}
    	}
    }
    
    /**
     * Gera Valores da coluna de etapas.<br>
     * 
     * @author aleixo
     * @version 0,1 23/03/2007
     * @param builder
     * @param etapa
     * @param colunas
     * @param corFundo
     * @throws ECARException
     */
    public void geraXMLValoresEtapa(XmlBuilder builder, ItemEstruturaIett etapa, List colunas, String corFundo) throws ECARException{
    	if(colunas != null && !colunas.isEmpty()){
    		builder.addNode("itemEtapa", "corFundo=\"" + builder.normalize(corFundo) + "\"");
    		Iterator itColunas = colunas.iterator();
			while (itColunas.hasNext()){
				ObjetoEstrutura coluna = (ObjetoEstrutura) itColunas.next();
				builder.addClosedNode("etapasValor", "valor=\"" + builder.normalize(coluna.iGetValor(etapa)) + "\"");
			}
			builder.closeNode("itemEtapa");
    	}
    }
    
    /**
     * Verifica numa lista de itens de etapas (ItemEstruturaIett), se existe algum item de etapa
     * que pertença à etapa (EstruturaEtt) passada pelo parâmetro.<br>
     * 
     * @author aleixo
     * @version 0,1 23/03/2007
     * @param EstruturaEtt ettEtapa - Etapa em questão
     * @param List etapas - Lista com itens de uma etapa
     * @return boolean
     */
    private boolean verificaEtapa(EstruturaEtt ettEtapa, List etapas){
		if(etapas != null && !etapas.isEmpty()){
	    	Iterator itEtapa = etapas.iterator();
			while(itEtapa.hasNext()){
				ItemEstruturaIett etapa = (ItemEstruturaIett) itEtapa.next();
				if(etapa.getEstruturaEtt().equals(ettEtapa)){
					return true;
				}
			}
		}
		return false;
    }
    
    
    /**
     * Monta a lista de refrencias agrupadas a partir dos acompanhamentos selecionados na tela.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param codArisSelecionados
     * @return referenciasAgrupadas
     * @throws ECARException
     */
	public List montaListaReferenciasAgrupadas(List codArisSelecionados) throws ECARException{
	    
	    	
	    	OrgaoOrg orgao = new OrgaoOrg();
	    	OrgaoDao orgaoDao = new OrgaoDao(request);
	    	List referenciasAgrupadas = new ArrayList();
	    	
	       	try {
				if(codArisSelecionados != null && !codArisSelecionados.isEmpty()){
			       Iterator itCodArisSelecionados = codArisSelecionados.iterator();
				  
			       while(itCodArisSelecionados.hasNext()) {
			    	   Long codAri = (Long) itCodArisSelecionados.next();
			    	   AcompReferenciaItemAri ari = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, codAri);
			    	   if(ari != null && !referenciasAgrupadas.contains(ari.getAcompReferenciaAref())) {
			    		   referenciasAgrupadas.add(ari.getAcompReferenciaAref());
			    	   }
			    	   
			       }
				}
				
				return referenciasAgrupadas;
				
	       	} catch(Exception e){
	    		this.logger.error(e);
	    		throw new ECARException("Erro na criação do relatório: Lista de Acompanhamento Selecionado - " + e.getMessage());               
	    	}
	    }	
    
}