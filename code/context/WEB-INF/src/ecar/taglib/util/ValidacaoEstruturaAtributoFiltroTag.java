/*
 * Criado em 17/04/2008
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

import ecar.dao.SisGrupoAtributoDao;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;

/**
 * Taglib para gerar as rotinas de validação dos campos da tela de filtros dinâmicos.<br>
 * Permite validações específicas para cada campo que devem ser implementadas nos métodos geraValidacaoxxxxxxx<br>
 * 
 * @author Milton Pereira
 */
public class ValidacaoEstruturaAtributoFiltroTag implements Tag{
    private Collection atributos;

    private Collection sisGrupoAtributoSgaObrigatorio;
    
    private PageContext page = null;
    
    private StringBuffer validacaoCampos;
    
    private StringBuffer retornoPesquisa;
    
    private SisGrupoAtributoDao sisGrupoAtributoDao= null;
    
    private Long codEstrutura = null;
       
    
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
            if(atributos != null){
                Iterator it = atributos.iterator();
                while(it.hasNext()){
                    ObjetoEstrutura atributoEstrutura = (ObjetoEstrutura) it.next();
                     
                    if(atributoEstrutura.iGetGrupoAtributosLivres() == null){
                    	try {
	                        this.getClass().
	                        	getMethod("geraValidacao" + Util.primeiraLetraToUpperCase(atributoEstrutura.iGetNome()), new Class[] { ObjetoEstrutura.class }).
	                        		invoke(this, new Object[] { atributoEstrutura });
	                                           	}
                    	catch(Exception e) {
                        	Logger logger = Logger.getLogger(this.getClass());
                        	logger.error(e);
                    	}
                    } else {
                    	geraValidacaoAtributoLivre(atributoEstrutura);
                    }
                }
                
            }
            s.append("function validar_"+ getCodEstrutura() +"(form) { \n");
            if(validacaoCampos != null)
                s.append(validacaoCampos);
            if(this.getSisGrupoAtributoSgaObrigatorio() != null && !this.getSisGrupoAtributoSgaObrigatorio().isEmpty()) {
            	//s.append(new ecar.dao.SisGrupoAtributoDao(null).getValidacoesAtributos(new ArrayList(this.getSisGrupoAtributoSgaObrigatorio())));
            	s.append("");
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
     * Gera validação NomeIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoNomeIett(ObjetoEstrutura atributo) {
        
    }
    /**
     * Gera validação SiglaIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoSiglaIett(ObjetoEstrutura atributo) {
        
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
        
    }
    /**
     * Gera validaçãoOrigemIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoOrigemIett(ObjetoEstrutura atributo) {
        
    }
    /**
     * Gera validaçãoObjetivoGeralIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoObjetivoGeralIett(ObjetoEstrutura atributo) {
        
    }
    /**
     * Gera validação BeneficiosIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoBeneficiosIett(ObjetoEstrutura atributo) {
        
    }
    /**
     * Gera validação Objeto especificoIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoObjetivoEspecificoIett(ObjetoEstrutura atributo) {
        
    }    
    /**
     * Gera validação data inicioIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDataInicioIett(ObjetoEstrutura atributo) {
        geraValidacaoData(atributo);
    }
    /**
     * Gera validação Data termino Iett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDataTerminoIett(ObjetoEstrutura atributo) {
        geraValidacaoData(atributo);
    }
    /**
     * Gera validação IndCriticaIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoIndCriticaIett(ObjetoEstrutura atributo) {

    }
    /**
     * Gera validação IndMonitoramentoIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoIndMonitoramentoIett(ObjetoEstrutura atributo) {

    }
    /**
     * Gera validação IndMonitoramIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoIndMonitoramIett(ObjetoEstrutura atributo) {

    }
    /**
     * Gera validação IndPermBloqPlanejamentoIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoIndBloqPlanejamentoIett(ObjetoEstrutura atributo) {

    }
    /**
     * Gera validação IndPermBloqPlanejIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
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
        geraValidacaoNumeroDecimal(atributo);
    }
    /**
     * Gera validação DataInicioMonitoramentoIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDataInicioMonitoramentoIett(ObjetoEstrutura atributo) {
    	geraValidacaoData(atributo);
    }
    /**
     * Gera validação DataInclusaoIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDataInclusaoIett(ObjetoEstrutura atributo) {
    	geraValidacaoData(atributo);
    }

    /**
     * Gera validação UsuarioUsuByCodUsuIncIett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoUsuarioUsuByCodUsuIncIett(ObjetoEstrutura atributo) {
    	
    }
    /**
     * Gera validação por data UltManutençãoIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDataUltManutencaoIett(ObjetoEstrutura atributo) {
    	geraValidacaoData(atributo);
    }
    /**
     * Gera validação UsuarioUsuByCodUsuUltManutIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
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
     * Gera validação dataR1.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDataR1(ObjetoEstrutura atributo) {
        geraValidacaoData(atributo);
    }

    /**
     * Gera validação dataR2.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDataR2(ObjetoEstrutura atributo) {
        geraValidacaoData(atributo);
    }

    /**
     * Gera validação dataR3.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDataR3(ObjetoEstrutura atributo) {
        geraValidacaoData(atributo);
    }

    /**
     * Gera validação dataR4.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */

    public void geraValidacaoDataR4(ObjetoEstrutura atributo) {
        geraValidacaoData(atributo);
    }

    /**
     * Gera validação dataR5.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDataR5(ObjetoEstrutura atributo) {
        geraValidacaoData(atributo);
    }

    /**
     * Gera validação descrisão1.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDescricaoR1(ObjetoEstrutura atributo) {
                
    }

    /**
     * Gera validação descrisão2.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDescricaoR2(ObjetoEstrutura atributo) {
                
    }

    /**
     * Gera validação descrisão3.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDescricaoR3(ObjetoEstrutura atributo) {
                
    }

    /**
     * Gera validação descrisão4.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDescricaoR4(ObjetoEstrutura atributo) {
                
    }

    /**
     * Gera validação descrisão5.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDescricaoR5(ObjetoEstrutura atributo) {
                
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
          
    }

    /**
     * Gera validação SubAreaAre.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoSubAreaSare(ObjetoEstrutura atributo) {
          
    }
    /**
     * Gera validação unidade orçamentaria UO.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoUnidadeOrcamentariaUO(ObjetoEstrutura atributo) {
          
    }
    /**
     * Gera validação orgão por codigo do orgao responsavel Iett.<Br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoOrgaoOrgByCodOrgaoResponsavel1Iett(ObjetoEstrutura atributo) {
          
    }
    /**
     * Gera validação orgão por codigo do orgao responsavel2 Iett.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoOrgaoOrgByCodOrgaoResponsavel2Iett(ObjetoEstrutura atributo) {
          
    }
    /**
     * Gera validação PeriodicidadePrdc.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoPeriodicidadePrdc(ObjetoEstrutura atributo) {
          
    }
    /**
     * Gera validação situação sit.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoSituacaoSit(ObjetoEstrutura atributo) {
          
    }
    /**
     * Gera validação função acompanhamento.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoFuncaoAcompanhamento(ObjetoEstrutura atributo) {
        
    }
    
    /**
     * Gera validação Nivel planejamento.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoNivelPlanejamento(ObjetoEstrutura atributo){
    	
    }
    /**
     * Gera validação Função acompanhamento.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
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
     * Gera validação data.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoEstrutura atributo
     */
    private void geraValidacaoData(ObjetoEstrutura atributo){
    	
    	getValidacaoCampos().append("if((document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome() + "_Inicio").append("')[0]).value != \"\" && !validaData((document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome() + "_Inicio").append("')[0])").append(",false,true,true)){\n");
        getValidacaoCampos().append( "  alert(\"Valor inválido para o campo ").append(atributo.iGetLabel()).append("\");\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");
        getValidacaoCampos().append("if((document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome() + "_Fim").append("')[0]).value != \"\" && !validaData((document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome() + "_Fim").append("')[0])").append(",false,true,true)){\n");
        getValidacaoCampos().append( "  alert(\"Valor inválido para o campo ").append(atributo.iGetLabel()).append("\");\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");
        getValidacaoCampos().append("if(!DataMenor((document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome() + "_Inicio").append("')[0]).value, (document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome() + "_Fim").append("')[0]).value)){\n");
        getValidacaoCampos().append( "  alert(\"").append(atributo.iGetLabel()).append(" inicial maior que final").append("\");\n" );
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
        getValidacaoCampos().append("if((document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome()).append("_Inicio')[0]).value  != \"\" && !isValidMoeda((document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome()).append("_Inicio')[0]).value)){\n");
        getValidacaoCampos().append( "  alert(\"Valor inválido para o campo ").append(atributo.iGetLabel()).append("\");\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");
        getValidacaoCampos().append("if((document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome()).append("_Fim')[0]).value  != \"\" && !isValidMoeda((document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome()).append("_Fim')[0]).value)){\n");
        getValidacaoCampos().append( "  alert(\"Valor inválido para o campo ").append(atributo.iGetLabel()).append("\");\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");
        getValidacaoCampos().append("var valorInicio = ").append("transformaValorMascaraMoedaParaDouble(document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome() + "_Inicio')[0]").append(".value); \n");
    	getValidacaoCampos().append("var valorFim = ").append("transformaValorMascaraMoedaParaDouble(document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome() + "_Fim')[0]").append(".value); \n");
    	getValidacaoCampos().append("if (valorInicio.length > 0 && valorFim.length > 0) { \n");
    	getValidacaoCampos().append("if(parseFloat(valorFim) < parseFloat(valorInicio)){\n");
    	getValidacaoCampos().append( "  alert(\"").append(atributo.iGetLabel()).append(" inicial maior que final").append("\");\n" );
    	getValidacaoCampos().append(" return false; \n");
    	getValidacaoCampos().append( " } \n }");       
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
        getValidacaoCampos().append("if((document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome()).append("_Inicio')[0]).value != \"\" && !validaDecimal((document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome()).append("_Inicio')[0]).value)){\n");
        getValidacaoCampos().append( "  alert(\"Valor inválido para o campo ").append(atributo.iGetLabel()).append("\");\n" );
        //getValidacaoCampos().append( "  document.form.").append(getCodEstrutura()).append("_").append(atributo.iGetNome()).append(".focus();\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");
        getValidacaoCampos().append("if((document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome()).append("_Fim')[0]).value != \"\" && !validaDecimal((document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome()).append("_Fim')[0]).value)){\n");
        getValidacaoCampos().append( "  alert(\"Valor inválido para o campo ").append(atributo.iGetLabel()).append("\");\n" );
        //getValidacaoCampos().append( "  document.form.").append(getCodEstrutura()).append("_").append(atributo.iGetNome()).append(".focus();\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");
        getValidacaoCampos().append("var valorInicio = ").append("transformaValorMascaraMoedaParaDouble(document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome() + "_Inicio')[0]").append(".value); \n");
    	getValidacaoCampos().append("var valorFim = ").append("transformaValorMascaraMoedaParaDouble(document.getElementsByName('").append(getCodEstrutura()).append("_").append(atributo.iGetNome() + "_Fim')[0]").append(".value); \n");
    	getValidacaoCampos().append("if (valorInicio.length > 0 && valorFim.length > 0) { \n");
    	getValidacaoCampos().append("if(parseFloat(valorFim) < parseFloat(valorInicio)){\n");
    	getValidacaoCampos().append( "  alert(\"").append(atributo.iGetLabel()).append(" inicial maior que final").append("\");\n" );
    	getValidacaoCampos().append(" return false; \n");
    	getValidacaoCampos().append( " } \n }");


    }
    
    
    /**
     * Gera validação de atributo livre.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoEstrutura objetoEstrutura
     */
    private void geraValidacaoAtributoLivre(ObjetoEstrutura atributo){
		SisGrupoAtributoSga grupoAtributo = atributo.iGetGrupoAtributosLivres();
		if (grupoAtributo != null && grupoAtributo.getIndAtivoSga().equals("S") && 
				grupoAtributo.getSisAtributoSatbs() != null && grupoAtributo.getSisAtributoSatbs().size() > 0){
			if (grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().intValue() == Input.VALIDACAO){
				if (grupoAtributo.getSisAtributoSatbs() != null && grupoAtributo.getSisAtributoSatbs().iterator().hasNext()){
	    			SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) grupoAtributo.getSisAtributoSatbs().iterator().next();
	    			if (sisAtributoSatb.getAtribInfCompSatb().equals("dataScript")) {
	    				getValidacaoCampos().append(validacaoIntervaloDataAtributoLivre(atributo)); 
	    			} else if (sisAtributoSatb.getAtribInfCompSatb().equals("numeroInteiroScript") ||
	    					   sisAtributoSatb.getAtribInfCompSatb().equals("numeroRealScript") ||
	    					   sisAtributoSatb.getAtribInfCompSatb().equals("valorMonetarioScript")){
	    				getValidacaoCampos().append(validacaoIntervaloNumeroAtributoLivre(atributo));
	    			}
				}
			}
		}
    }        
    
    /**
     * Gera validação de atributo livre para intervalo de datas.<br>
     * @param atributo
     * @return String
     */
    public String validacaoIntervaloDataAtributoLivre(ObjetoEstrutura atributo){
    	StringBuffer validacao = new StringBuffer();
    	validacao.append("if(document.getElementsByName('").append(getCodEstrutura()).append("_a").append(atributo.iGetGrupoAtributosLivres().getCodSga() + "_Fim')[0]").append(".value != '' && document.getElementsByName('").append(getCodEstrutura()).append("_a").append(atributo.iGetGrupoAtributosLivres().getCodSga() + "_Inicio')[0]").append(".value != '') {\n" );
    	validacao.append("	if(!DataMenor(document.getElementsByName('").append(getCodEstrutura()).append("_a").append(atributo.iGetGrupoAtributosLivres().getCodSga() + "_Inicio')[0]").append(".value, document.getElementsByName('").append(getCodEstrutura()).append("_a").append(atributo.iGetGrupoAtributosLivres().getCodSga() + "_Fim')[0]").append(".value)){\n");
    	validacao.append("  	alert(\"").append(atributo.iGetLabel()).append(" inicial maior que final").append("\");\n" );
    	validacao.append(" 		return false; \n");
    	validacao.append(" 	} \n");
    	validacao.append("} \n");
    	return validacao.toString();
    }
    
    /**
     * Gera validação de atributo livre para intervalo de números.<br>
     * @param atributo
     * @return String
     */
    public String validacaoIntervaloNumeroAtributoLivre(ObjetoEstrutura atributo){
    	StringBuffer validacao = new StringBuffer();
    	validacao.append("var valorInicio = ").append("transformaValorMascaraMoedaParaDouble(document.getElementsByName('").append(getCodEstrutura()).append("_a").append(atributo.iGetGrupoAtributosLivres().getCodSga() + "_Inicio')[0]").append(".value); \n");
    	validacao.append("var valorFim = ").append("transformaValorMascaraMoedaParaDouble(document.getElementsByName('").append(getCodEstrutura()).append("_a").append(atributo.iGetGrupoAtributosLivres().getCodSga() + "_Fim')[0]").append(".value); \n");
    	validacao.append("if (valorInicio.length > 0 && valorFim.length > 0) { \n");
    	validacao.append("if(parseFloat(valorFim) < parseFloat(valorInicio)){\n");
    	validacao.append( "  alert(\"").append(atributo.iGetLabel()).append(" inicial maior que final").append("\");\n" );
    	validacao.append(" return false; \n");
    	validacao.append( " } \n }");
    	return validacao.toString();
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
        public Long getCodEstrutura() {
		return codEstrutura;
	}


        /**
         *
         * @param codEstrutura
         */
        public void setCodEstrutura(Long codEstrutura) {
		this.codEstrutura = codEstrutura;
	}
}
