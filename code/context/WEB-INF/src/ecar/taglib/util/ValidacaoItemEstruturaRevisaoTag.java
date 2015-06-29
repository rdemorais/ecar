/*
 * Criado em 29/12/2004
 *
 */
package ecar.taglib.util;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

import comum.util.Util;

import ecar.pojo.EstrutTpFuncAcmpEtttfa;
import ecar.pojo.ObjetoEstrutura;

/**
 * Taglib para gerar as rotinas de validação dos campos da tela de dados gerais do cadastro de Itens.<br>
 * Gera também as funções de retorno dos parãmetros do pop up de pesquisa.<br>
 * Permite validações específicas para cada campo que devem ser implementadas nos métodos geraValidacaoxxxxxxx<br>
 * 
 * @author felipev
 */
public class ValidacaoItemEstruturaRevisaoTag implements Tag{
    
    private Collection atributos;
    
    private PageContext page = null;
    
    private StringBuffer validacaoCampos;
    
    private StringBuffer retornoPesquisa;
    
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
            if(atributos != null){
                Iterator it = atributos.iterator();
                while(it.hasNext()){
                    ObjetoEstrutura atributoEstrutura = (ObjetoEstrutura) it.next();
                    if(atributoEstrutura.iGetTipo() == EstrutTpFuncAcmpEtttfa.class) {
                        geraPesquisaFuncaoAcompanhamento(atributoEstrutura);     
                        geraValidacaoFuncaoAcompanhamento(atributoEstrutura);
                    } else {
                        this.getClass().
                        	getMethod("geraValidacao" + Util.primeiraLetraToUpperCase(atributoEstrutura.iGetNome()), new Class[] { ObjetoEstrutura.class }).
                        		invoke(this, new Object[] { atributoEstrutura });
                    }
                }
            }
//            s.append("function validar(form) { \n");
            //if(validacaoCampos != null)
                //s.append(validacaoCampos);
            //s.append( "return true;\n");
            //s.append("}\n");
            if(retornoPesquisa != null)
                s.append(retornoPesquisa);
            writer.print(s.toString());
        } catch(Exception e){
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
        return Tag.EVAL_BODY_INCLUDE;
    }
    
    
    /* versao antiga com validacao para funcao de acompanhamento "todos ou nenhum"
    public int doStartTag() throws JspException {
        try{
            JspWriter writer = this.page.getOut();
            StringBuffer s = new StringBuffer();
            validacaoCampos = new StringBuffer();
            retornoPesquisa = new StringBuffer();
            List atributosFuncaoAcompanhamento = new ArrayList();
            if(atributos != null){
                Iterator it = atributos.iterator();
                while(it.hasNext()){
                    ObjetoEstrutura atributoEstrutura = (ObjetoEstrutura) it.next();
                    if(atributoEstrutura.iGetTipo() == EstrutTpFuncAcmpEtttfa.class) {
                        geraPesquisaFuncaoAcompanhamento(atributoEstrutura);
                        atributosFuncaoAcompanhamento.add(atributoEstrutura);                        
                    } else {
                        this.getClass().
                        	getMethod("geraValidacao" + Util.primeiraLetraToUpperCase(atributoEstrutura.iGetNome()), new Class[] { ObjetoEstrutura.class }).
                        		invoke(this, new Object[] { atributoEstrutura });
                    }
                }
            }
            Iterator itFunAcomp = atributosFuncaoAcompanhamento.iterator();
            Object[] atributosEstrutura = atributosFuncaoAcompanhamento.toArray(); 
            while(itFunAcomp.hasNext()){
                ObjetoEstrutura atributoEstrutura = (ObjetoEstrutura) itFunAcomp.next();
                validacaoCampos.append("if(document.form." + atributoEstrutura.iGetNome() + ".value != \'\'){");
                for(int i = 0; i < atributosEstrutura.length; i++){
                    ObjetoEstrutura obj = (ObjetoEstrutura) atributosEstrutura[i];                    
                    geraValidacaoCampoObrigatorio(obj);
                }
                validacaoCampos.append("}");
            }
            s.append("function validar(form) { \n");
            if(validacaoCampos != null)
                s.append(validacaoCampos);
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
     */
    
   /**
    * Gera validação nomeIett.<br>
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
     * Gera validação SiglaIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoSiglaIett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)        
            geraValidacaoCampoObrigatorio(atributo);
    }

    /**
     * Gera validação DescriçãoIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDescricaoIett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
    }


    /**
     * Gera validação OrigemIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoOrigemIett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)        
            geraValidacaoCampoObrigatorio(atributo);
    }


    /**
     * Gera validação ObjetivoGeralIett.<br>
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
     * Gera validação BeneficiariosIett.<br>
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
     * Gera validação ObjetivoEspecificoIett.<br>
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
     * Gera validação Data inicio Iett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDataInicioIett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
        geraValidacaoData(atributo);
    }

    /**
     * Gera validação Data terminoIett.<br>
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
     * Gera validação IndCriticaIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoIndCriticaIett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
    }

    /**
     * Gera validação IndMonitoramentoIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoIndMonitoramentoIett(ObjetoEstrutura atributo) {

    }
    /**
     * Gera validação IndMonitoramIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoIndMonitoramIett(ObjetoEstrutura atributo) {

    }
    /**
     * Gera validação IndBloqPlanejamentoIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoIndBloqPlanejamentoIett(ObjetoEstrutura atributo) {

    }
    /**
     * Gera validação IndPermBloqPlanejIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoIndPermBloqPlanejIett(ObjetoEstrutura atributo) {

    }
    /**
     * Gera validação ValPrevistoFuturoIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoValPrevistoFuturoIett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
        geraValidacaoNumeroDecimal(atributo);
    }

    /**
     * Gera validação data inicio MonitoramentoIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDataInicioMonitoramentoIett(ObjetoEstrutura atributo) {
    }

    /**
     * Gera validação dataInclusaoIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDataInclusaoIett(ObjetoEstrutura atributo) {
    }
    /**
     * Gera validação UsuarioUsuByCodUsuIncIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoUsuarioUsuByCodUsuIncIett(ObjetoEstrutura atributo) {
    }
    
    /**
     * Gera validação data ultima manutenção Iett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDataUltManutencaoIett(ObjetoEstrutura atributo) {
    }

    /**
     * Gera validação UsuarioUsuByCodUsuUltManutIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoUsuarioUsuByCodUsuUltManutIett(ObjetoEstrutura atributo) {
    }

    /**
     * Gera validação IndAtivoIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoIndAtivoIett(ObjetoEstrutura atributo) {
    }

    /**
     * Gera validação DataR1.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDataR1(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
        geraValidacaoData(atributo);
    }
    /**
     * Gera validação DataR2.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDataR2(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
        geraValidacaoData(atributo);
    }
    /**
     * Gera validação DataR3.<br>
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
     * Gera validação DataR4.<br>
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
     * Gera validação DataR5.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDataR5(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
        geraValidacaoData(atributo);
    }

    /**
     * Gera validação DescriçãoR1.<br>
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
     * Gera validação DescriçãoR2.<br>
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
     * Gera validação DescriçãoR3.<br>
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
     * Gera validação DescriçãoR4.<br>
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
     * Gera validação DescriçãoR5.<br>
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
     * Gera validação AreaAre.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoAreaAre(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);  
    }
    /**
     * Gera validação SubAreaSare.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoSubAreaSare(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);  
    }
    /**
     * Gera validação UnidadeOrçamentariaUO.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoUnidadeOrcamentariaUO(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);  
    }
    /**
     * Gera validação orgão por codigo responsavel IETT.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoOrgaoOrgByCodOrgaoResponsavel1Iett(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);  
    }
    /**
     * Gera validação orgão por codigo responsavel2 IETT.<br>
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
     * Gera validação PeriodicidadePrdc.<br>
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
     * Gera validação SituaçãoSit.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoSituacaoSit(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);  
    }
    

    /**
     * Gera validação função Acompanhamento.<br>
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
     * Gera pesquisa Função Acompanhamento.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraPesquisaFuncaoAcompanhamento(ObjetoEstrutura atributo){
        getRetornoPesquisa().append( "function retorno").append(atributo.iGetNome()).append("rev").append("(codigo, descricao) {\n");
        getRetornoPesquisa().append( "document.form.nome").append(atributo.iGetNome()).append("rev").append(".value = descricao;\n");
        getRetornoPesquisa().append( "document.form.").append(atributo.iGetNome()).append("rev").append(".value = codigo;\n");
        getRetornoPesquisa().append("}\n");
    }


    /**
     * Gera validação Campo Obrigatorio.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoEstrutura atributo
     */
    private void geraValidacaoCampoObrigatorio(ObjetoEstrutura atributo){        
        getValidacaoCampos().append( " if (document.form.").append(atributo.iGetNome()).append("rev").append(".value == \"\"){\n" );
        getValidacaoCampos().append( "  alert(\"Obrigatório o Preenchimento do Campo ").append(atributo.iGetLabel()).append("\");\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");
    }
    

    /**
     * Gera validação data.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoEstrutura atributo
     */
    private void geraValidacaoData(ObjetoEstrutura atributo){
        getValidacaoCampos().append("if(form.").append(atributo.iGetNome()).append("rev").append(".value != \"\" && !validaData(form.").append(atributo.iGetNome()).append("rev").append(",false,true,true)){\n");
        getValidacaoCampos().append( "  alert(\"Valor inválido para o campo ").append(atributo.iGetLabel()).append("\");\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");

    }
    

    /**
     * Gera validação moeda.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoEstrutura atributo
     */
    private void geraValidacaoMoeda(ObjetoEstrutura atributo){
        getValidacaoCampos().append("if(document.form.").append(atributo.iGetNome()).append("rev").append(".value != \"\" && !isValidMoeda(form.").append(atributo.iGetNome()).append("rev").append(".value)){\n");
        getValidacaoCampos().append( "  alert(\"Valor inválido para o campo ").append(atributo.iGetLabel()).append("\");\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");
        
    }
    

    /**
     * Gera validação Numero Decimal.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoEstrutura atributo
     */
    private void geraValidacaoNumeroDecimal(ObjetoEstrutura atributo){
        getValidacaoCampos().append("if(document.form.").append(atributo.iGetNome()).append("rev").append(".value != \"\" && !validaDecimal(form.").append(atributo.iGetNome()).append("rev").append(".value)){\n");
        getValidacaoCampos().append( "  alert(\"Valor inválido para o campo ").append(atributo.iGetLabel()).append("\");\n" );
        getValidacaoCampos().append( "  document.form.").append(atributo.iGetNome()).append("rev").append(".focus();\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");
        
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
     * @return StringBuffer - (Returns the validacaoCampos)
     */
    public StringBuffer getValidacaoCampos() {
        return validacaoCampos;
    }
    
    /**
     * Atribui valor especificado para StringBuffer validacaoCampos.<br>
     * 
     * @param validacaoCampos
     * @author N/C
	 * @since N/C
	 * @version N/C
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
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param arg0
     */
    public void setPageContext(PageContext arg0) {
        this.page = arg0;
    }

    /**
     * 
     * 
     * @param arg0
     * @author N/C
	 * @since N/C
	 * @version N/C
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
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param page
     */
    public void setPage(PageContext page) {
        this.page = page;
    }

    /**
     * 
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void release() {

    }
}
