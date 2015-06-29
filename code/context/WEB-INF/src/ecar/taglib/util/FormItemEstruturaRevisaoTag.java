/*
 * Criado em 28/12/2004
 *
 */
package ecar.taglib.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

import comum.database.Dao;
import comum.util.Pagina;
import comum.util.Util;

import ecar.dao.SituacaoDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.AreaAre;
import ecar.pojo.EstruturaAtributoEttat;
import ecar.pojo.ItemEstruturarevisaoIettrev;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.PeriodicidadePrdc;
import ecar.pojo.SituacaoSit;
import ecar.pojo.SubAreaSare;
import ecar.pojo.UnidadeOrcamentariaUO;

/**
 * As referencias para AtributoEstruturaTela foram subtituidas por ObjetoEstrutura.<br>
 * Modificado em 08/03/05 por garten.
 * @author felipev
 */
public class FormItemEstruturaRevisaoTag implements Tag {

	ValidaPermissao validaPermissao = new ValidaPermissao();
    private ObjetoEstrutura atributo;
    private ItemEstruturarevisaoIettrev itemEstruturaRevisao;
    private Boolean desabilitar;
	private SegurancaECAR seguranca;
	private Boolean exibirBotoes = new Boolean(true);
    

    private PageContext page = null;

    /**
     * Inicializa a montagem da tag para ser adicionada na tela de HTML.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 * @throws JspException
     */
    public int doStartTag() throws JspException {
        try {
            if (atributo.iGetTipo() == EstruturaAtributoEttat.class) {
           		this.getClass().getMethod("geraHTML" + Util.primeiraLetraToUpperCase(atributo.iGetNome()), null).invoke(this, null);
            } else {
                geraHTMLPesquisaFuncaoAcompanhamento();
            }
        } catch (Exception e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
        return Tag.EVAL_BODY_INCLUDE;
    }

    /**
     * Gera Pesquisa função acompanhamento HTML.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLPesquisaFuncaoAcompanhamento() {
        try {
			String codigo = "";
			String valor = "";
			if(getItemEstruturaRevisao().getCodIettrev() != null){
				codigo = atributo.iGetValorCodFk(getItemEstruturaRevisao());
				valor = atributo.iGetValor(getItemEstruturaRevisao());
			}
            criaPesquisa(atributo.iGetNome(), 
                    	atributo.iGetLabel(),
                    	"ecar.popup.PopUpUsuario",
                    	"50",
                    	codigo, 
                    	valor, 
                    	"100");
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera  NomeIett html.<br>
     */
    public void geraHTMLNomeIett() {
        try {
            criaInputText("nomeIett", atributo.iGetLabel(), "50", "200", atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera SiglaIett html.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLSiglaIett() {
        try {
            criaInputText("siglaIett", atributo.iGetLabel(), "12", "10", atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera descriçãoIett html.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDescricaoIett() {
        try {
            criaTextArea("descricaoIett", atributo.iGetLabel(), "4", "60", atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera justificativaIett html.<br>
     *
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLJustificativaIett() {
            criaTextArea("justificativaIett", "Justificativa", "4", "60", getItemEstruturaRevisao().getJustificativaIettrev().toString());
    }

    
    /**
     * Gera origemIett html.<br>
     *
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLOrigemIett() {
        try {
            criaTextArea("origemIett", atributo.iGetLabel(), "4", "60", atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html Objetivo Geral Iett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLObjetivoGeralIett() {
        try {
            criaTextArea("objetivoGeralIett", atributo.iGetLabel(), "4", "60", atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    
    /**
     * Gera html Objetivo Especifico Iett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLObjetivoEspecificoIett() {
        try {
            criaTextArea("objetivoEspecificoIett", atributo.iGetLabel(), "4", "60", atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html Beneficios Iett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLBeneficiosIett() {
        try {
            criaTextArea("beneficiosIett", atributo.iGetLabel(), "4", "60", atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera data inicio Iett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataInicioIett() {
        try {
            criaInputTextData("dataInicioIett", atributo.iGetLabel(), atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html data de termino Iett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataTerminoIett() {
        try {
            criaInputTextData("dataTerminoIett", atributo.iGetLabel(), atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html IndCriticaIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLIndCriticaIett() {
        List opcoes = new ArrayList();
        opcoes.add(new String[] { "S", "Sim" });
        opcoes.add(new String[] { "N", "Não" });
        try {
            criaRadio(atributo.iGetNome(), atributo.iGetLabel(), atributo.iGetValor(getItemEstruturaRevisao()), opcoes);
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html ValPreivstoFuturoIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLValPrevistoFuturoIett() {
        String valor = "";
        try {
            if(!"".equals(atributo.iGetValor(getItemEstruturaRevisao())))				
                valor = Pagina.trocaNullNumeroDecimalSemMilhar(Double.valueOf(atributo.iGetValor(getItemEstruturaRevisao())));
            
            criaInputTextMoeda("valPrevistoFuturoIett", atributo.iGetLabel(), "12", "30", valor);
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera data inicio monitoramento Iett html.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataInicioMonitoramentoIett() {
    }

    /**
     * Gera data inclusão Iett html.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataInclusaoIett() {
    	try{
    		if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true){
    			criaLabelText(atributo.iGetNome(), atributo.iGetLabel(), "15", "15", atributo.iGetValor(getItemEstruturaRevisao()));
    		}
    	}
    	catch (ECARException e) {
    		Logger logger = Logger.getLogger(this.getClass());
    		logger.error(e);
    	}
    }

    /**
     * Gera html UsuarioUsuByCodUsuIncIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLUsuarioUsuByCodUsuIncIett() {
    	try{
    		if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true){
    			criaLabelText(atributo.iGetNome(), atributo.iGetLabel(), "50", "50", atributo.iGetValor(getItemEstruturaRevisao()));
    		}
    	}
    	catch (ECARException e) {
    		Logger logger = Logger.getLogger(this.getClass());
    		logger.error(e);
    	}
    }

    /**
     * Gera data da utlima manutenção Iett HTML.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
   public void geraHTMLDataUltManutencaoIett() {
//    	try{
//    		if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true){
//    			criaLabelText(atributo.iGetNome(), atributo.iGetLabel(), "15", "15", atributo.iGetValor(getItemEstruturaRevisao()));
//    		}
//    	}
//    	catch (ECARException e) {
//    		Logger logger = Logger.getLogger(this.getClass());
//    		logger.error(e);
//    	}
    }
//
    /**
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLUsuarioUsuByCodUsuUltManutIett() {
//    	try{
//    		if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true){
//    			criaLabelText(atributo.iGetNome(), atributo.iGetLabel(), "50", "50", atributo.iGetValor(getItemEstruturaRevisao()));
//    		}
//    	}
//    	catch (ECARException e) {
//    		Logger logger = Logger.getLogger(this.getClass());
//    		logger.error(e);
//    	}
    }

    /**
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLIndAtivoIett() {
    }

    /**
     * Gera html dataR1.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataR1() {
        try {
            criaInputTextData("dataR1", atributo.iGetLabel(), atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    /**
     * Gera html dataR2.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataR2() {
        try {
            criaInputTextData("dataR2", atributo.iGetLabel(), atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html dataR3.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataR3() {
        try {
            criaInputTextData("dataR3", atributo.iGetLabel(), atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html dataR4.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataR4() {
        try {
            criaInputTextData("dataR4", atributo.iGetLabel(), atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html dataR5.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataR5() {
        try {
            criaInputTextData("dataR5", atributo.iGetLabel(), atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html decrisão R1.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDescricaoR1() {
        try {
            criaTextArea("descricaoR1", atributo.iGetLabel(), "4", "60", atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html decrisão R2.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDescricaoR2() {
        try {
            criaTextArea("descricaoR2", atributo.iGetLabel(), "4", "60", atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html decrisão R3.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDescricaoR3() {
        try {
            criaTextArea("descricaoR3", atributo.iGetLabel(), "4", "60", atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html decrisão R4.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDescricaoR4() {
        try {
            criaTextArea("descricaoR4", atributo.iGetLabel(), "4", "60", atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html decrisão R5.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDescricaoR5() {
        try {
            criaTextArea("descricaoR5", atributo.iGetLabel(), "4", "60", atributo.iGetValor(getItemEstruturaRevisao()));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html AreaAre.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLAreaAre() {
        AreaAre area = new AreaAre();
        area.setIndAtivoAre("S");
        try {
            List areas = new Dao().pesquisar(area, new String[] { atributo.iGetNomeFk(), "asc" });
            List options = new ArrayList();
            Iterator it = areas.iterator();
            while (it.hasNext()) {
                area = (AreaAre) it.next();
                options.add(new String[] { area.getCodAre().toString(), Util.invocaGet(area, atributo.iGetNomeFk()).toString() });
            }
            
            //criaSelect("areaAre", atributo.iGetLabel(), atributo.iGetValorCodFk(getItemEstrutura()), options, "onChange=\"updateSub(this.selectedIndex, this.form.subAreaSare);\"");
            criaSelect("areaAre", atributo.iGetLabel(), atributo.iGetValorCodFk(getItemEstruturaRevisao()), options, "");
            
            //criaJScriptArea(areas);
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html subAreaSare.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLSubAreaSare() {
        SubAreaSare sArea = new SubAreaSare();
        sArea.setIndAtivoSare("S");
        try {
            List sAreas = new Dao().pesquisar(sArea, new String[] { atributo.iGetNomeFk(), "asc" });
            List options = new ArrayList();
            Iterator it = sAreas.iterator();
            while (it.hasNext()) {
                sArea = (SubAreaSare) it.next();
                options.add(new String[] { sArea.getCodSare().toString(), Util.invocaGet(sArea, atributo.iGetNomeFk()).toString() });
            }
            criaSelect("subAreaSare", atributo.iGetLabel(), atributo.iGetValorCodFk(getItemEstruturaRevisao()), options, "");
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Gera html unidade orcamentaria UO.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLUnidadeOrcamentariaUO() {
        UnidadeOrcamentariaUO unidade = new UnidadeOrcamentariaUO();
        unidade.setIndAtivoUo("S");
        try {
            List unidades = new Dao().pesquisar(unidade, new String[] { atributo.iGetNomeFk(), "asc" });
            List options = new ArrayList();
            Iterator it = unidades.iterator();
            while (it.hasNext()) {
                unidade = (UnidadeOrcamentariaUO) it.next();
                options.add(new String[] { unidade.getCodUo().toString(), Util.invocaGet(unidade, atributo.iGetNomeFk()).toString() });
            }
            
            criaSelect("unidadeOrcamentariaUo", atributo.iGetLabel(), atributo.iGetValorCodFk(getItemEstruturaRevisao()), options, "");
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Gera html do Orgao Responsavel Iett por cod.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLOrgaoOrgByCodOrgaoResponsavel1Iett() {
        OrgaoOrg orgao = new OrgaoOrg();
        orgao.setIndAtivoOrg("S");
        try {
            List orgaos = new Dao().pesquisar(orgao, new String[] {
                    atributo.iGetNomeFk(), "asc" });
            List options = new ArrayList();
            Iterator it = orgaos.iterator();
            while (it.hasNext()) {
                orgao = (OrgaoOrg) it.next();
                options.add(new String[] { orgao.getCodOrg().toString(), Util.invocaGet(orgao, atributo.iGetNomeFk()).toString() });
            }
            criaSelect("orgaoOrgByCodOrgaoResponsavel1Iett", atributo.iGetLabel(), atributo.iGetValorCodFk(getItemEstruturaRevisao()), options, "");
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    /**
     * Gera html do Orgao Responsavel2 Iett por cod.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLOrgaoOrgByCodOrgaoResponsavel2Iett() {
        OrgaoOrg orgao = new OrgaoOrg();
        orgao.setIndAtivoOrg("S");
        try {
            List orgaos = new Dao().pesquisar(orgao, new String[] {atributo.iGetNomeFk(), "asc" });
            List options = new ArrayList();
            Iterator it = orgaos.iterator();
            while (it.hasNext()) {
                orgao = (OrgaoOrg) it.next();
                options.add(new String[] { orgao.getCodOrg().toString(), Util.invocaGet(orgao, atributo.iGetNomeFk()).toString() });
            }
            criaSelect("orgaoOrgByCodOrgaoResponsavel2Iett", atributo.iGetLabel(), atributo.iGetValorCodFk(getItemEstruturaRevisao()), options, "");
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera Periodicidade Prdc html.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLPeriodicidadePrdc() {
        PeriodicidadePrdc prd = new PeriodicidadePrdc();
        try {
            List periodicidades = new Dao().pesquisar(prd, new String[] {atributo.iGetNomeFk(), "asc" });
            List options = new ArrayList();
            Iterator it = periodicidades.iterator();
            while (it.hasNext()) {
                prd = (PeriodicidadePrdc) it.next();
                options.add(new String[] { prd.getCodPrdc().toString(), Util.invocaGet(prd, atributo.iGetNomeFk()).toString() });
            }
            criaSelect("periodicidadePrdc", atributo.iGetLabel(), atributo.iGetValorCodFk(getItemEstruturaRevisao()), options, "");
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera situação html.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLSituacaoSit() {
    	SituacaoSit situacao = new SituacaoSit();
        try {
        	List situacoes = new SituacaoDao(null).getSituacaoByEstrutura(itemEstruturaRevisao.getItemEstruturaIettrev().getEstruturaEtt(), new String[] {"descricaoSit","asc"});
        	
        	List options = new ArrayList();
        	Iterator it = situacoes.iterator();
        	while(it.hasNext()){
        		situacao = (SituacaoSit) it.next();
        		options.add(new String[] {situacao.getCodSit().toString(), Util.invocaGet(situacao, atributo.iGetNomeFk()).toString() });
        	}
            criaSelect("situacaoSit", atributo.iGetLabel(), atributo.iGetValorCodFk(getItemEstruturaRevisao()), options, "");
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera função de acompanhamento html.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLFuncaoAcompanhamento() {
        try {
            criaPesquisa(atributo.iGetNome(), atributo.iGetLabel(),
                    "ecar.pojo.UsuarioUsu", "50", atributo.iGetValorCodFk(getItemEstruturaRevisao()), atributo.iGetValor(getItemEstruturaRevisao()), "100");
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Este Método cria o botão que Permite Ativar e Retirar Monitoramento.<br>
     * Ele cria o botão, um campo hidden com o flag e um javascript para ser executado no método onclick do botão
     * O javascript verificar o label atual do botão, seta o flag do campo hidden dependendo deste label.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLIndMonitoramentoIett() {
        String labelAtual = "Ativar ";
        try {
			
			boolean temPermissao = validaPermissao.permissaoAtivarMonitoramentoItem(getItemEstruturaRevisao().getItemEstruturaIettrev(), seguranca.getUsuario(), seguranca.getGruposAcesso()); 
					
            if("S".equals(atributo.iGetValor(getItemEstruturaRevisao()))){
				temPermissao = validaPermissao.permissaoDesativarMonitoramentoItem(getItemEstruturaRevisao().getItemEstruturaIettrev(), seguranca.getUsuario(), seguranca.getGruposAcesso());
                labelAtual = "Retirar ";        
            }
            if("N".equals(atributo.iGetValor(getItemEstruturaRevisao()))){
				temPermissao = validaPermissao.permissaoAtivarMonitoramentoItem(getItemEstruturaRevisao().getItemEstruturaIettrev(), seguranca.getUsuario(), seguranca.getGruposAcesso());
                labelAtual = "Ativar ";        
            }
            if("".equals(atributo.iGetValor(getItemEstruturaRevisao()))){
				temPermissao = validaPermissao.permissaoAtivarMonitoramentoItem(getItemEstruturaRevisao().getItemEstruturaIettrev(), seguranca.getUsuario(), seguranca.getGruposAcesso());
				labelAtual = "Ativar ";
				getItemEstruturaRevisao().setIndMonitoramentoIettrev("N");
            }    
            
			
			if(temPermissao){
				setDesabilitar(Boolean.FALSE);
			}
			else{
				setDesabilitar(Boolean.TRUE);
			}
//            StringBuffer auxScriptValueBtn = new StringBuffer("document.form.bt").append(atributo.iGetNome()).append(".value");
            StringBuffer auxScriptValueHidden = new StringBuffer("document.form.").append(atributo.iGetNome()).append(".value");
            StringBuffer auxScriptValueHidden1 = new StringBuffer("'N'");
            StringBuffer auxScriptValueHidden2 = new StringBuffer("'S'");
            StringBuffer script = new StringBuffer(" if(")
            							 .append(auxScriptValueHidden)
            							 .append("==")
            							 .append(auxScriptValueHidden2)
            							 .append(") {")
            							 .append(auxScriptValueHidden)
            							 .append("=")
            							 .append(auxScriptValueHidden1)
            							 .append(";")
            							 .append("aoClicarMonitoramento(document.form);this.disabled=true;")
            							 .append("} ")
            							 .append("else { if(")
            							 .append(auxScriptValueHidden)
            							 .append("==")
            							 .append(auxScriptValueHidden1)
            							 .append(") {")
            							 .append(auxScriptValueHidden)
            							 .append("=")
            							 .append(auxScriptValueHidden2)
            							 .append(";aoClicarMonitoramento(document.form);this.disabled=true;")
            							 .append("} }");
            criaInputButton(labelAtual + atributo.iGetLabel(), atributo.iGetNome() , script.toString());
            criaInputHidden(atributo.iGetNome(), atributo.iGetValor(getItemEstruturaRevisao()));
            
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html IndBloqPlanejamentoIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLIndBloqPlanejamentoIett() {
        String labelAtual = "Bloquear ";
		
		boolean temPermissao = validaPermissao.permissaoBloquearPlanejamentoItem(getItemEstruturaRevisao().getItemEstruturaIettrev(), seguranca.getUsuario(), seguranca.getGruposAcesso()); 
        
        try {
            if("S".equals(atributo.iGetValor(getItemEstruturaRevisao()))){
				temPermissao = validaPermissao.permissaoLiberarPlanejamentoItem(getItemEstruturaRevisao().getItemEstruturaIettrev(), seguranca.getUsuario(), seguranca.getGruposAcesso());
                labelAtual = "Liberar ";        
            }if("N".equals(atributo.iGetValor(getItemEstruturaRevisao())))
                labelAtual = "Bloquear ";        
            if("".equals(atributo.iGetValor(getItemEstruturaRevisao()))){
                labelAtual = "Bloquear ";
                getItemEstruturaRevisao().setIndBloqPlanejamentoIettrev("N");
            }    
			
            
			if(temPermissao){
				setDesabilitar(Boolean.FALSE);
			}
			else{
				setDesabilitar(Boolean.TRUE);
			}

            String auxScriptValueBtn = "document.form.bt" + atributo.iGetNome() + ".value";
            String auxScriptValueHidden = "document.form." + atributo.iGetNome() + ".value";
            String auxScriptValueHidden1 = "'N'";
            String auxScriptValueHidden2 = "'S'";

            String script = " if(" + auxScriptValueHidden + "==" + auxScriptValueHidden2 + ") {" + 	            				 
			auxScriptValueHidden + "=" + auxScriptValueHidden1 + ";aoClicarPlanejamento(document.form);this.disabled=true;} " +
			"else { if(" + auxScriptValueHidden + "==" + auxScriptValueHidden1 + ") {" + 	            				
			auxScriptValueHidden + "=" + auxScriptValueHidden2 + ";aoClicarPlanejamento(document.form);this.disabled=true;} }";
            
            criaInputButton(labelAtual + atributo.iGetLabel(), atributo.iGetNome() , script);
			
            criaInputHidden(atributo.iGetNome(), atributo.iGetValor(getItemEstruturaRevisao()));
            
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    

    /**
     * Cria Pesquisa.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param nome 
     * @param classePesquisa
     * @param label
     * @param size
     * @param valor
     * @param maxlength
     * @param valorText
     */
    public void criaPesquisa(String nome, String label, String classePesquisa,
            String size, String valor, String valorText, String maxlength) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {
            s.append("<TR><TD class=\"label\">");
            s.append(label);
            s.append("</TD>");
            s.append("<TD>");
            s.append("<input type=\"text\" disabled name=\"nome" + nome +"rev"
                    + "\" size=\"" + size + "\" value=\"" + valorText
                    + "\" maxlength=\"" + maxlength + "\"");
            s.append("><input type=\"hidden\" name=\"" + nome +"rev"+ "\" value=\""
                    + valor + "\">");
            
            if(this.getExibirBotoes().booleanValue()){
	            s.append("&nbsp;&nbsp;<input type=\"button\" name=\"pesq\" value=\"Pesquisar\" class=\"botao\" ");
	            if (getDesabilitar() != null && getDesabilitar().booleanValue() == true)
	                s.append(" disabled");
	            
	            s.append(" onclick=\"popup_pesquisa('" + classePesquisa
	                            + "', 'retorno" + nome + "rev" +  "');\">");
	            s.append("&nbsp;&nbsp;<input type=\"button\" name=\"pesq\" value=\"Limpar\" class=\"botao\" " +
	            		"onclick=\"document.form.nome" + nome +".value=''; document.form." + nome + ".value=''\"");
	
	            if (getDesabilitar() != null && getDesabilitar().booleanValue() == true)
	                s.append(" disabled");
	            
	            s.append(">");
            }
            
            s.append("</TD></TR>");
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Cria Select.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param nome
     * @param label
     * @param valor
     * @param scripts 
     * @param opcoes
     */
    public void criaSelect(String nome, String label, String valor, Collection opcoes, String scripts) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {
            s.append("<TR><TD class=\"label\">");
            s.append(label);
            s.append("</TD>");
            s.append("<TD>");
            s.append("<select name=\"" + nome + "rev" + "\" " + scripts);
            if (getDesabilitar() != null && getDesabilitar().booleanValue() == true)
                s.append(" disabled");
            s.append(">");
            s.append("<option value=\"\"></option>");
            if (opcoes != null) {
                Iterator it = opcoes.iterator();
                while (it.hasNext()) {
                    String[] chaveValor = (String[]) it.next();
                    s.append("<option value=\"" + chaveValor[0] + "\"");
                    if (chaveValor[0].equals(valor))
                        s.append(" selected ");
                    s.append(">");
                    s.append(chaveValor[1]);
                    s.append("</option>");
                }
            }
            s.append("</select></TD></TR>");
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Cria Radio.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param nome
     * @param label
     * @param valor
     * @param opcoes
     */
    public void criaRadio(String nome, String label, String valor,
            Collection opcoes) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {
            s.append("<TR><TD class=\"label\">");
            s.append(label);
            s.append("</TD>");
            s.append("<TD>");
            if (opcoes != null) {
                Iterator it = opcoes.iterator();
                while (it.hasNext()) {
                    String[] chaveValor = (String[]) it.next();
                    s.append("<input type=\"radio\" class=\"form_check_radio\" name=\"" + nome + "rev"
                            + "\" value=\"" + chaveValor[0] + "\"");
                    if (getDesabilitar() != null && getDesabilitar().booleanValue() == true)
                        s.append(" disabled");
                    if (chaveValor[0].equals(valor))
                        s.append(" checked ");
                    s.append(">");
                    s.append(chaveValor[1]);
                }
                s.append("&nbsp;<input type=\"button\" name=\"buttonLimpar\" value=\"Limpar\" class=\"botao\" ")
                .append("onclick=\"limparRadioButtons(document.getElementsByName('" + nome + "'));\"");
                if (getDesabilitar() != null && getDesabilitar().booleanValue() == true)
	    	   		s.append(" disabled ");
                s.append(">");
            }
            s.append("</TD></TR>");
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Cria botão input.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param label 
     * @param name
     * @param onclick
     */
    public void criaInputButton(String label, String name, String onclick){
    	if(!this.getExibirBotoes().booleanValue())
    		return;
    	
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {   
            s.append("<TR><TD>&nbsp;</TD><TD>");
            s.append("<input type=\"button\" ");
            if (getDesabilitar() != null && getDesabilitar().booleanValue() == true)
                s.append(" disabled ");
            s.append("value=\"" + label + "\" name=\"bt" + name  + "rev"+ "\" onclick=\"" + onclick + "\"");
            s.append("></TD></TR>");
            writer.print(s.toString());
        } catch (IOException e) { 
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }        
    }

    /**
     * Cria hidden input.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param nome 
     * @param valor
     */
    public void criaInputHidden(String nome,String valor) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {            
            s.append("<input type=\"hidden\" name=\"" + nome + "rev" + "\" value=\"" + valor + "\"");
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Cria texto input.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param nome
     * @param size 
     * @param label
     * @param maxlength
     * @param valor
     */
    public void criaInputText(String nome, String label, String size,
            String maxlength, String valor) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {
            s.append("<TR><TD class=\"label\">");
            s.append(label);
            s.append("</TD>");
            s.append("<TD>");
            s.append("<input type=\"text\" name=\"" + nome + "rev" + "\" size=\""
                    + size + "\" value=\"" + valor + "\" maxlength=\""
                    + maxlength + "\"");
            if (getDesabilitar() != null && getDesabilitar().booleanValue() == true)
                s.append(" disabled");
            s.append("></TD></TR>");
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Cria Label text.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param nome
     * @param label
     * @param maxlength 
     * @param size
     * @param valor
     */
    public void criaLabelText(String nome, String label, String size,
            String maxlength, String valor) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {
            s.append("<TR><TD class=\"label\">");
            s.append(label);
            s.append("</TD>");
            s.append("<TD>");
            s.append(valor + "</TD></TR>");
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Cria Input Text Moeda.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param nome
     * @param size 
     * @param maxlength 
     * @param label
     * @param valor
     */
    public void criaInputTextMoeda(String nome, String label, String size,
            String maxlength, String valor) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {
            s.append("<TR><TD class=\"label\">");
            s.append(label);
            s.append("</TD>");
            s.append("<TD>");
            /*s.append("<input type=\"text\" onkeydown=\"formataMoeda(this,event);\" name=\""*/
            s.append("<input type=\"text\" name=\""
                            + nome + "rev"
                            + "\" size=\""
                            + size
                            + "\" value=\""
                            + valor + "\" maxlength=\"" + maxlength + "\"");
            if (getDesabilitar() != null && getDesabilitar().booleanValue() == true)
                s.append(" disabled");
            s.append("></TD></TR>");
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Cria input Texto data.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param nome
     * @param label 
     * @param valor
     */
    public void criaInputTextData(String nome, String label, String valor) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {
            s.append("<TR><TD class=\"label\">");
            s.append(label);
            s.append("</TD>");
            s.append("<TD>");
            s.append("<input type=\"text\" name=\""
                            + nome + "rev"
                            + "\" size=\"11\" value=\""
                            + valor
                            + "\" maxlength=\"10\" onkeyup=\"mascaraData(event, this);\"");
            if (getDesabilitar() != null && getDesabilitar().booleanValue() == true) {
                s.append(" disabled>");
            } else {
    			s.append(">		<img class=\"posicao\" title=\"Selecione a data\" src=\"../../images/icone_calendar.gif\" onclick=\"open_calendar('data', document.forms[0]."+nome+"rev, '')\" " + this.getDesabilitar() + " " + this.getDesabilitar() + ">");
            }
            s.append("</TD></TR>");
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Cria texto area.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param nome
     * @param label
     * @param cols 
     * @param rows
     * @param valor
     */
    public void criaTextArea(String nome, String label, String rows,
            String cols, String valor) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {
            s.append("<TR><TD class=\"label\">");
            s.append(label);
            s.append("</TD>");
            s.append("<TD>");
            s.append("<textarea name=\"" + nome  + "rev"+ "\" rows=\"" + rows
                    + "\" cols=\"" + cols + "\" onkeyup=\"return validaTamanhoLimite(this, 2000);\"");
            if (getDesabilitar() != null && getDesabilitar().booleanValue() == true)
                s.append(" readonly");
            s.append(">");
            s.append(valor);
            s.append("</textarea>");
            s.append("</TD></TR>");
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Cria javaScript Area.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param areas
     */
    public void criaJScriptArea(List areas) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        AreaAre area;
        SubAreaSare subArea;
        List lSubAreas = new ArrayList(0);
        try {
            s.append("\n<script language=\"javascript\">\n");
            s.append("aSubArea = new Array(" + (areas.size() + 1) + ");\n");

            s.append("for (var i = 0; i < aSubArea.length; ++i) { \n");
            s.append("	aSubArea[i] = new Array();\n");
            s.append("}\n");
            s.append("aSubArea[0][0] = new Option('Selecione uma Área','');\n");

            for (int i = 0; i < areas.size(); i++) {
                area = (AreaAre) areas.get(i);
                s.append("aSubArea["+ (i+1) +"][0] = new Option('');\n");
                lSubAreas.clear();
                //lSubAreas.addAll(area.getSubAreaSares());
                int indiceSubArea = 1;
                for (int j = 0; j < lSubAreas.size(); j++) {
                    subArea = (SubAreaSare) lSubAreas.get(j);
                    if ("S".equals(subArea.getIndAtivoSare()))
                        s.append("aSubArea[" + (i+1) + "][" + (indiceSubArea++) + "] = new Option('" + subArea.getNomeSare() + "','" + subArea.getCodSare().toString() +"');\n");
                }
            }
            s.append("</script>\n");
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Encerra Tag.<bR>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 * @throws JspException
     */
    public int doEndTag() throws JspException {
        return Tag.EVAL_PAGE;
    }

    /**
     * Atribui valor especificado para PageContext page.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param arg0
     */
    public void setPageContext(PageContext arg0) {
        this.page = arg0;
    }

    /**
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param arg0
     */
    public void setParent(Tag arg0) {
    }

    /**
     * Retorna Tag null.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Tag
     */
    public Tag getParent() {
        return null;
    }

    /**
     * Retorna PageContext page.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return PageContext - (Returns the page)
     */
    public PageContext getPage() {
        return page;
    }

    /**
     * Atribui valor especificado para PageContext page.<br>
     * 
     * @param page
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setPage(PageContext page) {
        this.page = page;
    }

    /**
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void release() {

    }

    /**
     * Retorna ObjetoEstrutura atributo.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return ObjetoEstrutura - (Returns the atributo)
     */
    public ObjetoEstrutura getAtributo() {
        return atributo;
    }
    
    /**
     * Atribui valor especificado para ObjetoEstrutura atributo.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setAtributo(ObjetoEstrutura atributo) {
        this.atributo = atributo;
    }
    
    /**
     * Retorna Boolean desabilitar.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return Boolean - (Returns the desabilitar)
     */
    public Boolean getDesabilitar() {
        return desabilitar;
    }
    
    /**
     * Atribui valor especificado para Boolean desabilitar.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param desabilitar
     */
    public void setDesabilitar(Boolean desabilitar) {
        this.desabilitar = desabilitar;
    }
    
    /**
     * Retorna ItemEstruturarevisaoIettrev itemEstruturaRevisao.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return ItemEstruturarevisaoIettrev - (Returns the itemEstrutura)
     */
    public ItemEstruturarevisaoIettrev getItemEstruturaRevisao() {
        return this.itemEstruturaRevisao;
    }
    
    /**
     * Atribui valor especificado para ItemEstruturarevisaoIettrev itemEstruturaRevisao.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param itemEstruturaRevisao
     */
    public void setItemEstruturaRevisao(ItemEstruturarevisaoIettrev itemEstruturaRevisao) {
        this.itemEstruturaRevisao = itemEstruturaRevisao;
    }
    
	/**
	 * Retorna SegurancaECAR seguranca.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return SegurancaECAR - (Returns the seguranca)
	 */
	public SegurancaECAR getSeguranca() {
		return seguranca;
	}
	
	/**
	 * Atribui valor especificado para SegurancaECAR seguranca.<br>
	 * 
         * @param seguranca
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setSeguranca(SegurancaECAR seguranca) {
		this.seguranca = seguranca;
	}

	/**
	 * Retorna Boolean exibirBotoes.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Boolean
	 */
	public Boolean getExibirBotoes() {
		return exibirBotoes;
	}

	/**
	 * Atribui valor especificado para Boolean exibirBotoes.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param exibirBotoes
	 */
	public void setExibirBotoes(Boolean exibirBotoes) {
		this.exibirBotoes = exibirBotoes;
	}
}