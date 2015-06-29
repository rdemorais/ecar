/*
 * Criado em 29/12/2004
 *
 */
package ecar.taglib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

import comum.util.ConstantesECAR;
import comum.util.EcarCfg;
import comum.util.Util;

import ecar.action.ActionSisAtributo;
import ecar.dao.ConfiguracaoDao;
import ecar.dao.SisGrupoAtributoDao;
import ecar.exception.ECARException;
import ecar.pojo.EstrutTpFuncAcmpEtttfa;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturaSisAtributoIettSatb;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.TipoValor;

/**
 * Taglib para gerar as rotinas de valida��o dos campos da tela de dados gerais do cadastro de Itens.<br>
 * Gera tamb�m as fun��es de retorno dos par�metros do pop up de pesquisa.<br>
 * Permite valida��es espec�ficas para cada campo que devem ser implementadas nos m�todos geraValidacaoxxxxxxx<br>
 * 
 * @author felipev
 */
public class ValidacaoItemEstruturaTag implements Tag{
    private Collection atributos;

    private Collection sisGrupoAtributoSgaObrigatorio;
    
    private PageContext page = null;
    
    private StringBuffer validacaoCampos;
    
    private StringBuffer retornoPesquisa;
    
    private SisGrupoAtributoDao sisGrupoAtributoDao= null;
    
    private String labelOrgaoOrgByCodOrgaoResponsavel1Iett = null;
    
    private String acao = null;

    private ItemEstruturaIett itemEstrutura;
    
    private HttpServletRequest request;
    
    /**
     *
     * @return
     */
    public ItemEstruturaIett getItemEstrutura() {
		return itemEstrutura;
	}


    /**
     *
     * @param itemEstrutura
     */
    public void setItemEstrutura(ItemEstruturaIett itemEstrutura) {
		this.itemEstrutura = itemEstrutura;
	}


    /**
     *
     * @return
     */
    public String getAcao() {
		return acao;
	}


        /**
         *
         * @param acao
         */
        public void setAcao(String acao) {
		this.acao = acao;
	}

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
        try{
            JspWriter writer = this.page.getOut();
            StringBuffer s = new StringBuffer();
            validacaoCampos = new StringBuffer();
            retornoPesquisa = new StringBuffer();
            int datas = 0;
            String labelDataIni = "";
            String labelDataFim = "";
            String labelNivelPlanejamento = "";
            boolean nivelPlanejamentoObrigatorio = false;

        //Percorre os atributos para buscar o label do atributo "UnidadeOrcamentariaUO".
        //Se labelOrgaoOrgByCodOrgaoResponsavel1Iett for igual a null, significa que este atributo n�o foi definido,
        //ou n�o est� ativo.
            if(atributos != null){
                Iterator it = atributos.iterator();
                while(it.hasNext()){
                    ObjetoEstrutura atributoEstrutura = (ObjetoEstrutura) it.next();
                    if(atributoEstrutura.iGetNome().equals("orgaoOrgByCodOrgaoResponsavel1Iett")){
                    	labelOrgaoOrgByCodOrgaoResponsavel1Iett = atributoEstrutura.iGetLabel();
                	}
                }                
            }
                        
            if(atributos != null){
                Iterator it = atributos.iterator();
                while(it.hasNext()){
                    ObjetoEstrutura atributoEstrutura = (ObjetoEstrutura) it.next();
                    if(atributoEstrutura.iGetTipo() == EstrutTpFuncAcmpEtttfa.class) {
                        geraPesquisaFuncaoAcompanhamento(atributoEstrutura);     
                        geraValidacaoFuncaoAcompanhamento(atributoEstrutura);
                    }
                    else if(atributoEstrutura.iGetGrupoAtributosLivres() != null 
                    		&& atributoEstrutura.iGetGrupoAtributosLivres().getSisAtributoSatbs() != null 
                    		&& atributoEstrutura.iGetGrupoAtributosLivres().getSisAtributoSatbs().size() > 0){
                    	geraValidacaoAtributoLivre(atributoEstrutura);
                	}
                    else {
                    	try {
	                        this.getClass().
	                        	getMethod("geraValidacao" + Util.primeiraLetraToUpperCase(atributoEstrutura.iGetNome()), new Class[] { ObjetoEstrutura.class }).
	                        		invoke(this, new Object[] { atributoEstrutura });
	                        if("dataInicioIett".equals(atributoEstrutura.iGetNome()) || "dataTerminoIett".equals(atributoEstrutura.iGetNome())){
	                        	datas++;
	                        }
	                        
	                        if("dataInicioIett".equals(atributoEstrutura.iGetNome())){
	                        	labelDataIni = atributoEstrutura.iGetLabel();
	                        }

	                        if("dataTerminoIett".equals(atributoEstrutura.iGetNome())){
	                        	labelDataFim = atributoEstrutura.iGetLabel();
	                        }
	                        
	                        if("nivelPlanejamento".equals(atributoEstrutura.iGetNome())){
	                        	labelNivelPlanejamento = atributoEstrutura.iGetLabel();
	                        	if(atributoEstrutura.iGetObrigatorio()){
	                        		nivelPlanejamentoObrigatorio = true;
	                        	}
	                        }
	                        
	                	}
                    	catch(Exception e) {
                        	Logger logger = Logger.getLogger(this.getClass());
                        	logger.error(e);
                    	}
                    }
                }
                
                if(datas >= 2){
                	geraValidacaoDatasInicioTermino(labelDataIni, labelDataFim);
                }
            }
            s.append("function validar(form) { \n");
            if(validacaoCampos != null)
                s.append(validacaoCampos);
            if(nivelPlanejamentoObrigatorio){
            	if(this.getSisGrupoAtributoSgaObrigatorio() == null || this.getSisGrupoAtributoSgaObrigatorio().size() == 0){
            		List listSisGrupoAtributoSgaObrigatorio = new ArrayList();
	            	ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
	            	listSisGrupoAtributoSgaObrigatorio.add(configuracaoDao.getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan());
	            	this.setSisGrupoAtributoSgaObrigatorio(listSisGrupoAtributoSgaObrigatorio);
            	}
            	
            	s.append(new ecar.dao.SisGrupoAtributoDao(null).getValidacaoNivelPlanejamento((new ArrayList(this.getSisGrupoAtributoSgaObrigatorio())), false, labelNivelPlanejamento));
            }
            s.append( "return true;\n");
            s.append("}\n");
            if(retornoPesquisa != null)
                s.append(retornoPesquisa);
            writer.print(s.toString());
        } catch(Exception e){
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
        return Tag.EVAL_BODY_INCLUDE;
    }
    
    
   
    
    /**
     * Gera valida��o NomeIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoNomeIett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
    }
    /**
     * Gera valida��o SiglaIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoSiglaIett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)        
            geraValidacaoCampoObrigatorio(atributo);
    }
    /**
     * Gera valida��o Descri��oIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDescricaoIett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
    }
    /**
     * Gera valida��oOrigemIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoOrigemIett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)        
            geraValidacaoCampoObrigatorio(atributo);
    }
    /**
     * Gera valida��oObjetivoGeralIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoObjetivoGeralIett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
    }
    /**
     * Gera valida��o BeneficiosIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoBeneficiosIett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
    }
    /**
     * Gera valida��o Objeto especificoIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoObjetivoEspecificoIett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
    }    
    /**
     * Gera valida��o data inicioIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDataInicioIett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
        geraValidacaoData(atributo);
    }
    /**
     * Gera valida��o Data termino Iett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDataTerminoIett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
        geraValidacaoData(atributo);
    }
    /**
     * Gera valida��o IndCriticaIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoIndCriticaIett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorioRadioButton(atributo);
    }
    /**
     * Gera valida��o IndMonitoramentoIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoIndMonitoramentoIett(ObjetoEstrutura atributo) {

    }
    /**
     * Gera valida��o IndMonitoramIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoIndMonitoramIett(ObjetoEstrutura atributo) {

    }
    /**
     * Gera valida��o IndPermBloqPlanejamentoIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoIndBloqPlanejamentoIett(ObjetoEstrutura atributo) {

    }
    /**
     * Gera valida��o IndPermBloqPlanejIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoIndPermBloqPlanejIett(ObjetoEstrutura atributo) {

    }
    
    /**
     * Gera valida��o IndModeloIett.
     * @param atributo
     */
    public void geraValidacaoIndModeloIett(ObjetoEstrutura atributo){
    	if (atributo.iGetObrigatorio().booleanValue() == true){
    		geraValidacaoCampoObrigatorioRadioButton(atributo);
    	}
    }
    /**
     * Gera valida��o ValPrevistoFuturoIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoValPrevistoFuturoIett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
        geraValidacaoNumeroDecimal(atributo);
    }
    /**
     * Gera valida��o DataInicioMonitoramentoIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDataInicioMonitoramentoIett(ObjetoEstrutura atributo) {
    }
    /**
     * Gera valida��o DataInclusaoIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDataInclusaoIett(ObjetoEstrutura atributo) {
    }

    /**
     * Gera valida��o UsuarioUsuByCodUsuIncIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoUsuarioUsuByCodUsuIncIett(ObjetoEstrutura atributo) {
    }
    /**
     * Gera valida��o por data UltManuten��oIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDataUltManutencaoIett(ObjetoEstrutura atributo) {
    }
    /**
     * Gera valida��o UsuarioUsuByCodUsuUltManutIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoUsuarioUsuByCodUsuUltManutIett(ObjetoEstrutura atributo) {
    }

    /**
     * Gera valida��o IndAtivoIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoIndAtivoIett(ObjetoEstrutura atributo) {
    }

    /**
     * Gera valida��o dataR1.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDataR1(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
        geraValidacaoData(atributo);
    }

    /**
     * Gera valida��o dataR2.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDataR2(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
        geraValidacaoData(atributo);
    }

    /**
     * Gera valida��o dataR3.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDataR3(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
        geraValidacaoData(atributo);
    }

    /**
     * Gera valida��o dataR4.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */

    public void geraValidacaoDataR4(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
        geraValidacaoData(atributo);
    }

    /**
     * Gera valida��o dataR5.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDataR5(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
        geraValidacaoData(atributo);
    }

    /**
     * Gera valida��o descris�o1.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDescricaoR1(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);        
    }

    /**
     * Gera valida��o descris�o2.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDescricaoR2(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);        
    }

    /**
     * Gera valida��o descris�o3.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDescricaoR3(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);        
    }

    /**
     * Gera valida��o descris�o4.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDescricaoR4(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);        
    }

    /**
     * Gera valida��o descris�o5.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDescricaoR5(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);        
    }

    /**
     * Gera valida��o AreaAre.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoAreaAre(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);  
    }

    /**
     * Gera valida��o SubAreaAre.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoSubAreaSare(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);  
    }
    /**
     * Gera valida��o unidade or�amentaria UO.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoUnidadeOrcamentariaUO(ObjetoEstrutura atributo) {
        String labelOrgaoResponsavel = "Org�o Respons�vel";
        
        if(labelOrgaoOrgByCodOrgaoResponsavel1Iett != null)
        	labelOrgaoResponsavel = labelOrgaoOrgByCodOrgaoResponsavel1Iett; 
    	
        if(atributo.iGetObrigatorio().booleanValue() == true){
            //geraValidacaoCampoObrigatorio(atributo);
            getValidacaoCampos().append(" if (document.form.").append(atributo.iGetNome()).append(" != null && document.form.").append(atributo.iGetNome()).append(".selectedIndex == 0 && document.form." ).append(atributo.iGetNome()).append(".length > 1){\n");
            getValidacaoCampos().append("   alert(\"Obrigat�rio o Preenchimento do Campo ").append(atributo.iGetLabel()).append("\"); \n");
            getValidacaoCampos().append("   return false; \n");
            getValidacaoCampos().append(" } else if(document.form.").append(atributo.iGetNome()).append(" == null || document.form.").append(atributo.iGetNome()).append(".selectedIndex == 0){\n");           
            getValidacaoCampos().append("    alert(\"Obrigat�rio o Preenchimento do Campo ").append(atributo.iGetLabel())
								.append(             ". Para ativ�-lo, o campo ").append(labelOrgaoResponsavel)
								.append(             " deve estar habilitado e selecionado com um item que possua uma unidade or�ament�ria cadastrada.\");\n" );
            getValidacaoCampos().append("    return false; \n");
            getValidacaoCampos().append("   } \n");                        
        }
    }
    /**
     * Gera valida��o org�o por codigo do orgao responsavel Iett.<Br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoOrgaoOrgByCodOrgaoResponsavel1Iett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);  
    }
    /**
     * Gera valida��o org�o por codigo do orgao responsavel2 Iett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoOrgaoOrgByCodOrgaoResponsavel2Iett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);  
    }
    /**
     * Gera valida��o PeriodicidadePrdc.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoPeriodicidadePrdc(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);  
    }
    /**
     * Gera valida��o situa��o sit.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoSituacaoSit(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);  
    }
    /**
     * Gera valida��o fun��o acompanhamento.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoFuncaoAcompanhamento(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
    }
    
    /**
     * Gera valida��o Nivel planejamento.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoNivelPlanejamento(ObjetoEstrutura atributo){
    }
    
    
    /**
     * Gera valida��o Fun��o acompanhamento.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraPesquisaFuncaoAcompanhamento(ObjetoEstrutura atributo){
        getRetornoPesquisa().append( "function retorno").append(atributo.iGetNome()).append("(codigo, descricao) {\n");
        getRetornoPesquisa().append( "if (document.form.").append(atributo.iGetNome()).append(".value != codigo){\n");
        getRetornoPesquisa().append( "	if (eval(document.form.alterou)){\n");
        getRetornoPesquisa().append( "	document.form.alterou.value = 'S';\n");
        getRetornoPesquisa().append( "	}\n");
        getRetornoPesquisa().append( "}\n");
        getRetornoPesquisa().append( "document.form.nome").append(atributo.iGetNome()).append(".value = descricao;\n");
        getRetornoPesquisa().append( "document.form.").append(atributo.iGetNome()).append(".value = codigo;\n");
        getRetornoPesquisa().append("}\n");
    }

    /**
     * Gera valida��o campo obrigatorio.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoEstrutura atributo
     */
    private void geraValidacaoCampoObrigatorio(ObjetoEstrutura atributo){        
        getValidacaoCampos().append( " if (document.form.").append(atributo.iGetNome()).append(".value == \"\"){\n" );
        getValidacaoCampos().append( "  alert(\"Obrigat�rio o Preenchimento do Campo ").append(atributo.iGetLabel()).append("\");\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");
    }
    
    /**
     * Gera valida��o campo obrigatorio do tipo RadioButton.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoEstrutura atributo
     */
    private void geraValidacaoCampoObrigatorioRadioButton(ObjetoEstrutura atributo){
    	getValidacaoCampos().append("if(form.").append(atributo.iGetNome()).append("[0] != null && \n");
    	getValidacaoCampos().append("   form.").append(atributo.iGetNome()).append("[0].checked == false && \n");
    	getValidacaoCampos().append("	form.").append(atributo.iGetNome()).append("[1] != null && \n");
    	getValidacaoCampos().append("   form.").append(atributo.iGetNome()).append("[1].checked == false) { \n");
    	getValidacaoCampos().append( "  	alert(\"Obrigat�ria a sele��o do Campo ").append(atributo.iGetLabel()).append("\");\n" );
    	getValidacaoCampos().append("		return false;\n");
    	getValidacaoCampos().append( " } \n");        
    }
    
    /**
     * Gera valida��o data.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoEstrutura atributo
     */
    private void geraValidacaoData(ObjetoEstrutura atributo){
        getValidacaoCampos().append("if(form.").append(atributo.iGetNome()).append(".value != \"\" && !validaData(form.").append(atributo.iGetNome()).append(",false,true,true)){\n");
        getValidacaoCampos().append( "  alert(\"Valor inv�lido para o campo ").append(atributo.iGetLabel()).append("\");\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");

    }
    
    /**
     * Gera valida��o moeda.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoEstrutura atributo
     */
    private void geraValidacaoMoeda(ObjetoEstrutura atributo){
        getValidacaoCampos().append("if(document.form.").append(atributo.iGetNome()).append(".value != \"\" && !isValidMoeda(form.").append(atributo.iGetNome()).append(".value)){\n");
        getValidacaoCampos().append( "  alert(\"Valor inv�lido para o campo ").append(atributo.iGetLabel()).append("\");\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");
        
    }
    
    /**
     * Gera valida��o Numero Decimal.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoEstrutura atributo
     */
    private void geraValidacaoNumeroDecimal(ObjetoEstrutura atributo){
        getValidacaoCampos().append("if(document.form.").append(atributo.iGetNome()).append(".value != \"\" && !validaDecimal(form.").append(atributo.iGetNome()).append(".value)){\n");
        getValidacaoCampos().append( "  alert(\"Valor inv�lido para o campo ").append(atributo.iGetLabel()).append("\");\n" );
        getValidacaoCampos().append( "  document.form.").append(atributo.iGetNome()).append(".focus();\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");
        
    }
    /**
     * Gera valida��o Datas Inicio Termino.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoEstrutura atributo
     */
    private void geraValidacaoDatasInicioTermino(String labelDataIni, String labelDataFim){
    	getValidacaoCampos().append("/**** Valida��o das Datas de Inicio e Fim *****/");
    	getValidacaoCampos().append("if(document.form.dataInicioIett.value != \"\" && document.form.dataInicioIett.value.length < 10){");
    	getValidacaoCampos().append("  alert(\"Valor inv�lido para ").append(labelDataIni).append("\");");
    	getValidacaoCampos().append("  document.form.dataInicioIett.focus();");
    	getValidacaoCampos().append("  return false;");
    	getValidacaoCampos().append("}");
    	getValidacaoCampos().append("if(document.form.dataTerminoIett.value != \"\" && document.form.dataTerminoIett.value.length < 10){");
    	getValidacaoCampos().append("  alert(\"Valor inv�lido para ").append(labelDataFim).append("\");");
    	getValidacaoCampos().append("  document.form.dataTerminoIett.focus();");
    	getValidacaoCampos().append("  return false;");
    	getValidacaoCampos().append("}");
    	getValidacaoCampos().append("var dataTempIni = document.form.dataInicioIett.value.split(\"/\");");
    	getValidacaoCampos().append("var dataIni = parseInt(dataTempIni[2] + dataTempIni[1] + dataTempIni[0]);");
    	getValidacaoCampos().append("var dataTempFim = document.form.dataTerminoIett.value.split(\"/\");");
    	getValidacaoCampos().append("var dataFim = parseInt(dataTempFim[2] + dataTempFim[1] + dataTempFim[0]);");
    	getValidacaoCampos().append("if(dataFim < dataIni){");
    	getValidacaoCampos().append("  alert(\"").append(labelDataFim).append(" � menor que ").append(labelDataIni).append("\");");
    	getValidacaoCampos().append("  document.form.dataInicioIett.focus();");
    	getValidacaoCampos().append("  return false;");
    	getValidacaoCampos().append("}");
    	getValidacaoCampos().append("/**** Fim da Valida��o das Datas de Inicio e Fim *****/");
    }
    
    private void geraValidacaoAtributoLivre(ObjetoEstrutura atributo) throws ECARException {
    	
    	Iterator<SisAtributoSatb> it = atributo.iGetGrupoAtributosLivres().getSisAtributoSatbs().iterator();
    	
    	SisAtributoSatb sisAtributo = (SisAtributoSatb)it.next();
    	
    	if (sisAtributo.getAtribInfCompSatb() != null && sisAtributo.isAtributoMascaraEditavel()) {
    		getValidacaoCampos().append("/**** In�cio Valida��o M�scara Edit�vel " + atributo.iGetNome() + " ****/\n");    		
    		getValidacaoCampos().append(montaScriptValidaMascaraEditavel(atributo,atributo.iGetGrupoAtributosLivres(),sisAtributo));
    		getValidacaoCampos().append("/**** Fim Valida��o M�scara Edit�vel " + atributo.iGetNome() + " ****/\n");
    	} 
    	
    	if(atributo.iGetObrigatorio().booleanValue() == true && atributo.iGetGrupoAtributosLivres() != null) {
    		SisGrupoAtributoSga grupoAtributo = atributo.iGetGrupoAtributosLivres();

    		getValidacaoCampos().append("/**** In�cio Valida��o Atributo Livre " + atributo.iGetNome() + " ****/\n");    		
    		if ( sisGrupoAtributoDao==null)  {
    			getValidacaoCampos().append((new SisGrupoAtributoDao(null)).getValidacaoAtributo(grupoAtributo, false, atributo.iGetLabel()));
    		} else {
    			getValidacaoCampos().append(sisGrupoAtributoDao.getValidacaoAtributo(grupoAtributo, false, atributo.iGetLabel()));
    		}    		    		
    		getValidacaoCampos().append("/**** Fim Valida��o Atributo Livre " + atributo.iGetNome() + " ****/\n");
    	}
    }
        
    
    
	private String montaScriptValidaMascaraEditavel(ObjetoEstrutura atributo,SisGrupoAtributoSga grupo,SisAtributoSatb sisAtributo) throws ECARException {
		
    	StringBuffer ret = new StringBuffer(); 
    	
    	String nomeAtributoLivre = "a" + grupo.getCodSga().toString();
    	
    	if (acao != null && acao.equals(ConstantesECAR.ACAO_ALTERAR)){

    		ItemEstruturaSisAtributoIettSatb atributoLivre= itemEstrutura.buscarItemEstruturaSisAtributoLista(sisAtributo);
    		
    		ActionSisAtributo action = new ActionSisAtributo();
    		TipoValor mascaraArmazenada = atributoLivre.obterTipoMascara();
    		
    		String sequencialGerado = action.obterSequencial(atributoLivre.getInformacao(), mascaraArmazenada.getConteudo());
    		
    		ret.append("if (!validaMascaraEditavel(document.getElementById('"+nomeAtributoLivre+"'),'"+sisAtributo.getMascara()+"','"+atributo.iGetLabel()+"','"+EcarCfg.getConfiguracao("caracter.mascara.mes")+"','"+EcarCfg.getConfiguracao("caracter.mascara.ano")+"','"+EcarCfg.getConfiguracao("caracter.mascara.sequencial")+"','"+sequencialGerado+"','"+mascaraArmazenada.getConteudo()+"')) {\n");
    		
    		ret.append("\t return false;\n");
        	ret.append("} \n");    	
    	} else {
    		ret.append("if (!validaMascaraEditavel(document.getElementById('"+nomeAtributoLivre+"'),'"+sisAtributo.getMascara()+"','"+atributo.iGetLabel()+"','"+EcarCfg.getConfiguracao("caracter.mascara.mes")+"','"+EcarCfg.getConfiguracao("caracter.mascara.ano")+"')) {\n");
    		ret.append("\t return false;\n");
        	ret.append("} \n");    	
    	}
    	
    	
		return ret.toString();
	}


        /**
         *
         * @return
         */
        public SisGrupoAtributoDao getSisGrupoAtributoDao() {
		return sisGrupoAtributoDao;
	}


        /**
         *
         * @param sisGrupoAtributoDao
         */
        public void setSisGrupoAtributoDao(SisGrupoAtributoDao sisGrupoAtributoDao) {
		this.sisGrupoAtributoDao = sisGrupoAtributoDao;
	}


	/**
     * Retorna Collection atributos.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return Collection - (Returns the atributos)
     */
    public Collection getAtributos() {
        return atributos;
    }
    
    /**
     * Atribui valor especificado para Collection atributos.<br>
     * 
     * @param atributos
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setAtributos(Collection atributos) {
        this.atributos = atributos;
    }
    
    /**
     * Retorna StringBuffer retornoPesquisa.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return StringBuffer - (Returns the retornoPesquisa)
     */
    public StringBuffer getRetornoPesquisa() {
        return retornoPesquisa;
    }
    
    /**
     * Atribui valor especificado para StringBuffer retornoPesquisa.<br>
     * 
     * @param retornoPesquisa
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setRetornoPesquisa(StringBuffer retornoPesquisa) {
        this.retornoPesquisa = retornoPesquisa;
    }
    
    /**
     * Retorna StringBuffer validacaoCampos.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return StringBuffer Returns - (the validacaoCampos)
     */
    public StringBuffer getValidacaoCampos() {
        return validacaoCampos;
    }
    
    /**
     * Atribui valor especificado para StringBuffer validacaoCampos.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param validacaoCampos
     */
    public void setValidacaoCampos(StringBuffer validacaoCampos) {
        this.validacaoCampos = validacaoCampos;
    }
    
    /**
     * Encerra Tag.<br>
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
     * @param arg0
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setPageContext(PageContext arg0) {
        this.page = arg0;
    }

    /**
     * 
     * 
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
	 * @rreturn Tag
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
     * Retorna Collection sisGrupoAtributoSgaObrigatorio.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return Collection
     */
	public Collection getSisGrupoAtributoSgaObrigatorio() {
		return sisGrupoAtributoSgaObrigatorio;
	}

	/**
	 * Atribui valor especificado para Collection sisGrupoAtributoSgaObrigatorio.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param sisGrupoAtributoSgaObrigatorio
	 */
	public void setSisGrupoAtributoSgaObrigatorio(Collection sisGrupoAtributoSgaObrigatorio) {
		this.sisGrupoAtributoSgaObrigatorio = sisGrupoAtributoSgaObrigatorio;
	}


        /**
         *
         * @return
         */
        public HttpServletRequest getRequest() {
		return request;
	}


        /**
         *
         * @param request
         */
        public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
