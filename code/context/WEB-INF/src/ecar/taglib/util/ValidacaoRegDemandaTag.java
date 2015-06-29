/*
 * Criado em 11/03/2008
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
import ecar.pojo.ObjetoDemanda;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;

/**
 * Taglib para gerar as rotinas de validação dos campos da tela de Demandas.<br>
 * Permite validações específicas para cada campo que devem ser implementadas nos métodos geraValidacao<br>
 * 
 * @author 
 */
public class ValidacaoRegDemandaTag implements Tag{
   
	private Collection atributos;

    //private Collection sisGrupoAtributoSgaObrigatorio;
    
    private PageContext page = null;
    
    private StringBuffer validacaoCampos;
    
    private StringBuffer retornoPesquisa;
    
    private SisGrupoAtributoDao sisGrupoAtributoDao= null;
    
    private String acao;
    
    private String codigoVisao;
    
    private Boolean telaFiltro = null;
       

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
            
            telaFiltro = "filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao) ? true:false;
            
                   	
            if(atributos != null){
                Iterator it = atributos.iterator();
                while(it.hasNext()){
                    ObjetoDemanda objetoDemanda = (ObjetoDemanda) it.next();
                    if(objetoDemanda.iGetGrupoAtributosLivres() != null){
                    	geraValidacaoAtributoLivre(objetoDemanda);
                	}
                    else {
                    	try {
	                        this.getClass().
	                        	getMethod("geraValidacao" + Util.primeiraLetraToUpperCase(objetoDemanda.iGetNome()), new Class[] { ObjetoDemanda.class }).
	                        		invoke(this, new Object[] { objetoDemanda });	                        	                        
                    	}
                    	catch(Exception e) {
                        	Logger logger = Logger.getLogger(this.getClass());
                        	logger.error(e);
                    	}
                    }
                }
                
                
            }
            s.append("function validar(form) { \n");
            if(validacaoCampos != null)
                s.append(validacaoCampos);
//            if(this.getSisGrupoAtributoSgaObrigatorio() != null && !this.getSisGrupoAtributoSgaObrigatorio().isEmpty()) {
//            	s.append(new ecar.dao.SisGrupoAtributoDao(null).getValidacoesAtributos(new ArrayList(this.getSisGrupoAtributoSgaObrigatorio())));
//            }
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
     * Gera validação DescricaoRegd.<br>
     * 
     * @param objetoDemanda
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDescricaoRegd(ObjetoDemanda objetoDemanda) {
        if(objetoDemanda.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true 
        		&& (acao.equals("alterar") || acao.equals("incluir") || acao.equals("classificar") ) ){
            geraValidacaoCampoObrigatorio(objetoDemanda);
        }
    }
    
    /**
     * Gera validação de DataLimiteRegd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param objetoDemanda
     */
    public void geraValidacaoDataLimiteRegd(ObjetoDemanda objetoDemanda) {
        if(objetoDemanda.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true
        		&& (acao.equals("alterar") || acao.equals("incluir")) ){
            geraValidacaoCampoObrigatorio(objetoDemanda);
            
        }
        
        if (!acao.equals("classificar")){ 
        	geraValidacaoData(objetoDemanda);
        } else {  //Em Classificar Demanda o campo será necessariamente obrigatorio [Thaise 22/04/2008]
        	geraValidacaoCampoObrigatorio(objetoDemanda);
        	geraValidacaoData(objetoDemanda);
        }
    }
    
    /**
     * Gera validação OrgaoOrg.<br>
     * 
     * @param objetoDemanda
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoOrgaoOrg(ObjetoDemanda objetoDemanda) {
        if(objetoDemanda.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true 
        		&& (acao.equals("alterar") || acao.equals("incluir") ) ){
            geraValidacaoCampoObrigatorio(objetoDemanda);  
    	} 
        //Em Classificar Demanda o campo será necessariamente obrigatorio [Thaise 22/04/2008]
        else if (acao.equals("classificar")){
        	geraValidacaoCampoObrigatorio(objetoDemanda);
        }
    }
    
    
    /**
     * Gera validação SitDemandaSitd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param objetoDemanda
     */
    public void geraValidacaoSitDemandaSitd(ObjetoDemanda objetoDemanda) {
//        if(objetoDemanda.iGetObrigatorio().booleanValue() == true && acao.equals("classificar")){

    	//Em Classificar Demanda o campo será necessariamente obrigatorio [Thaise 22/04/2008]
    	//foi comentada a linha a cima
    	if (acao.equals("classificar")){
        	geraValidacaoCampoObrigatorio(objetoDemanda);                    
        }
    }
    
    
    /**
     * Gera validação PrioridadePrior.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param objetoDemanda
     */
    public void geraValidacaoPrioridadePrior(ObjetoDemanda objetoDemanda) {
        //if(objetoDemanda.iGetObrigatorio().booleanValue() == true && acao.equals("classificar")){
    	//Em Classificar Demanda o campo será necessariamente obrigatorio [Thaise 22/04/2008]
    	//foi comentada a linha a cima
    	 if(objetoDemanda.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true
         		&& (acao.equals("alterar") || acao.equals("incluir") || acao.equals("classificar")) ){
        	geraValidacaoCampoObrigatorio(objetoDemanda);  
        }
    }
    
    /**
     * Gera validação IndAtivoRegd.<br>
     * 
     * @param objetoDemanda
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoIndAtivoRegd(ObjetoDemanda objetoDemanda) {
        if(objetoDemanda.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true 
        		&& (acao.equals("alterar") || acao.equals("incluir")) ){
            geraValidacaoCampoObrigatorio(objetoDemanda);
        }
    }
    
    /**
     * Gera validação NumeroDocOrigemRegd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param objetoDemanda
     */
    public void geraValidacaoNumeroDocOrigemRegd(ObjetoDemanda objetoDemanda) {
        if(objetoDemanda.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true
        		&& (acao.equals("alterar") || acao.equals("incluir")) ){
            geraValidacaoCampoObrigatorio(objetoDemanda);
            
        }
        if ("filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao)){
        	geraValidacaoNumeroDecimalInicioFim(objetoDemanda);
        } else {
        	if (!acao.equals("classificar")){
            	geraValidacaoNumeroDecimal(objetoDemanda);
            }
        }
        
    }
    
    /**
     * Gera validação NumeroDocOrigemRegd.<br>
     * 
     * @param objetoDemanda
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoRegDemandaRegd(ObjetoDemanda objetoDemanda) {
        if(objetoDemanda.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true
        		&& (acao.equals("alterar") || acao.equals("incluir")) ){
            geraValidacaoCampoObrigatorio(objetoDemanda);
        }
    }
    
    /**
     * Gera validação NomeSolicitanteRegd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param objetoDemanda
     */
    public void geraValidacaoNomeSolicitanteRegd(ObjetoDemanda objetoDemanda) {
        if(objetoDemanda.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true
        		&& (acao.equals("alterar") || acao.equals("incluir")) || acao.equals("classificar") ){
            geraValidacaoCampoObrigatorio(objetoDemanda);
        }
    }
    
    /**
     * Gera validação DataSolicitacaoRegd.<br>
     * 
     * @param objetoDemanda
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDataSolicitacaoRegd(ObjetoDemanda objetoDemanda) {
        if(objetoDemanda.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true
        		&& (acao.equals("alterar") || acao.equals("incluir") || acao.equals("classificar")) ){
        	geraValidacaoCampoObrigatorio(objetoDemanda);
        }            
       
       geraValidacaoData(objetoDemanda);
       
    }
    
    /**
     * Gera validação ObservacaoRegd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param objetoDemanda
     */
    public void geraValidacaoObservacaoRegd(ObjetoDemanda objetoDemanda) {
        if(objetoDemanda.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true
        		&& (acao.equals("alterar") || acao.equals("incluir") || acao.equals("classificar")) ){
            geraValidacaoCampoObrigatorio(objetoDemanda);
        }
    }
    
    /**
     * Gera validação EntidadeOrgaoDemandaEntorgds.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param objetoDemanda
     */
    public void geraValidacaoEntidadeOrgaoDemandaEntorgds(ObjetoDemanda objetoDemanda) {
        if(objetoDemanda.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true
        		&& (acao.equals("alterar") || acao.equals("incluir") || acao.equals("classificar") ) ){
        	geraValidacaoCampoObrigatorioOrgaoSolucinadorCheck(objetoDemanda);
        }
    }
    
    
    /**
     * Gera validação EntidadeDemandaEntds.<br>
     * 
     * @param objetoDemanda
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoEntidadeDemandaEntds(ObjetoDemanda objetoDemanda) {
        if(objetoDemanda.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true
        		&& (acao.equals("alterar") || acao.equals("incluir") || acao.equals("classificar") ) ){
        	geraValidacaoCampoObrigatorioEntidadesCheck(objetoDemanda);
        }
    }
    
    /**
     * Gera validação LocalDemandaLdems.<br>
     * 
     * @param objetoDemanda
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoLocalDemandaLdems(ObjetoDemanda objetoDemanda) {
        if(objetoDemanda.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true
        		&& (acao.equals("alterar") || acao.equals("incluir") || acao.equals("classificar")) ){
        	geraValidacaoCampoObrigatorioLocaisCheck(objetoDemanda);
        }
    }
  
     /**
     * Gera validação campo obrigatorio.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoDemanda objetoDemanda
     */
    private void geraValidacaoCampoObrigatorio(ObjetoDemanda objetoDemanda){
    	if (!telaFiltro){
    		    		    		
    		getValidacaoCampos().append( " if (document.form.").append(objetoDemanda.iGetNome()).append(".value == \"\"){\n" );
            getValidacaoCampos().append( "  alert(\"Obrigatório o Preenchimento do Campo ").append(objetoDemanda.iGetLabel()).append("\");\n" );
            getValidacaoCampos().append(" return false; \n");
            getValidacaoCampos().append( " } \n");
                		
    	}        
    }
    /**
     * Gera validação para verificar se o campo está definido em Classifica Demanda.<br>
     * Método temporário para obrigar a configuração do atributo "sitDemandaSitd"<br>
     * e "prioridadePrior" no Atributo na Demanda. Com a aplicação de visões em Demanda
     * este método passará a ser dinâmico. 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    private void geraValidacaoExistenciaCamposClassificaDemanda(){
    	if (!telaFiltro){
    		getValidacaoCampos().append( " if (document.form.sitDemandaSitd == null){\n" );
            getValidacaoCampos().append("  \t alert(\"Obrigatória a configuração do Campo sitDemandaSitd");
            getValidacaoCampos().append(" em Atributo na Demanda\");\n" );
            getValidacaoCampos().append(" \t return false; \n");
            getValidacaoCampos().append( " } \n");

            getValidacaoCampos().append( " if (document.form.prioridadePrior == null){\n" );
            getValidacaoCampos().append("  \t alert(\"Obrigatória a configuração do Campo prioridadePrior");
            getValidacaoCampos().append(" em Atributo na Demanda\");\n" );
            getValidacaoCampos().append(" \t return false; \n");
            getValidacaoCampos().append( " } \n");
            
    		getValidacaoCampos().append( " if (document.form.orgaoOrg == null){\n" );
            getValidacaoCampos().append("  \t alert(\"Obrigatória a configuração do Campo orgaoOrg");
            getValidacaoCampos().append(" em Atributo na Demanda\");\n" );
            getValidacaoCampos().append(" \t return false; \n");
            getValidacaoCampos().append( " } \n");

    		getValidacaoCampos().append( " if (document.form.dataLimiteRegd == null){\n" );
            getValidacaoCampos().append("  alert(\"Obrigatória a configuração do Campo dataLimiteRegd");
            getValidacaoCampos().append(" em Atributo na Demanda\");\n" );
            getValidacaoCampos().append(" return false; \n");
            getValidacaoCampos().append( " } \n");
            
            
    	}
        
    }

    /**
     * Gera validação campo obrigatorio.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoDemanda objetoDemanda
     */
    private void geraValidacaoCampoObrigatorioLocaisCheck(ObjetoDemanda objetoDemanda){
    	if (!telaFiltro){
    		
    		// O document.form.contadorLocalReal.value foi removido porque nao estava funcionando no internet explorer
    	    //getValidacaoCampos().append( " if (document.form.").append("contadorLocalReal").append(".value == \"0\"){\n" );
    		getValidacaoCampos().append( " if (").append("contadorLocalReal").append("== \"0\" ||" );
    		getValidacaoCampos().append( " (document.form.").append("contadorLocalReal").append(".value == \"0\")){\n" );
	        getValidacaoCampos().append( "  alert(\"Obrigatório o Preenchimento do Campo ").append(objetoDemanda.iGetLabel()).append("\");\n" );
	        getValidacaoCampos().append(" return false; \n");
	        getValidacaoCampos().append( " } \n");
    	}
    }

    /**
     * Gera validação campo obrigatorio.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoDemanda objetoDemanda
     */
    private void geraValidacaoCampoObrigatorioEntidadesCheck(ObjetoDemanda objetoDemanda){
    	if (!telaFiltro){
    		// O document.form.contadorEntidadeReal.value foi removido porque nao estava funcionando no internet explorer
	        //getValidacaoCampos().append( " if (document.form.").append("contadorEntidadeReal").append(".value == \"0\"){\n" );
    		getValidacaoCampos().append( " if (").append("contadorEntidadeReal").append(" == \"0\"){\n" );
	        getValidacaoCampos().append( "  alert(\"Obrigatório o Preenchimento do Campo ").append(objetoDemanda.iGetLabel()).append("\");\n" );
	        getValidacaoCampos().append(" return false; \n");
	        getValidacaoCampos().append( " } \n");
	    }
    }
    
    
    /**
     * Gera validação campo obrigatorio.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoDemanda objetoDemanda
     */
    private void geraValidacaoCampoObrigatorioOrgaoSolucinadorCheck(ObjetoDemanda objetoDemanda){
    	if (!telaFiltro){
    		// O document.form.contadorEntidadeReal.value foi removido porque nao estava funcionando no internet explorer
    		//  getValidacaoCampos().append( " if (document.form.").append("contadorEntidadeOrgaoReal").append(".value == \"0\"){\n" );
            getValidacaoCampos().append( " if (").append("contadorEntidadeOrgaoReal").append(" == \"0\"){\n" );
	        getValidacaoCampos().append( "  alert(\"Obrigatório o Preenchimento do Campo ").append(objetoDemanda.iGetLabel()).append("\");\n" );
	        getValidacaoCampos().append(" return false; \n");
	        getValidacaoCampos().append( " } \n");
	    }
    }
    
    /**
     * Gera validação data.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoDemanda objetoDemanda
     */
    private void geraValidacaoData(ObjetoDemanda objetoDemanda){
    	if (telaFiltro){
	        getValidacaoCampos().append("if(form.").append(objetoDemanda.iGetNome() + "_Inicio").append(".value != \"\" && !validaData(form.").append(objetoDemanda.iGetNome() + "_Inicio").append(",false,true,true)){\n");
	        getValidacaoCampos().append( "  alert(\"Valor inválido para o campo ").append(objetoDemanda.iGetLabel()).append("\");\n" );
	        getValidacaoCampos().append(" return false; \n");
	        getValidacaoCampos().append( " } \n");
	        getValidacaoCampos().append("if(form.").append(objetoDemanda.iGetNome() + "_Fim").append(".value != \"\" && !validaData(form.").append(objetoDemanda.iGetNome() + "_Fim").append(",false,true,true)){\n");
	        getValidacaoCampos().append( "  alert(\"Valor inválido para o campo ").append(objetoDemanda.iGetLabel()).append("\");\n" );
	        getValidacaoCampos().append(" return false; \n");
	        getValidacaoCampos().append( " } \n");
	        getValidacaoCampos().append("if(!DataMenor(form.").append(objetoDemanda.iGetNome() + "_Inicio").append(".value, form.").append(objetoDemanda.iGetNome() + "_Fim").append(".value)){\n");
	        getValidacaoCampos().append( "  alert(\"").append(objetoDemanda.iGetLabel()).append(" inicial maior que final").append("\");\n" );
	        getValidacaoCampos().append(" return false; \n");
	        getValidacaoCampos().append( " } \n");
	        
    	} else {
    		getValidacaoCampos().append("if(form.").append(objetoDemanda.iGetNome()).append(".value != \"\" && !validaData(form.").append(objetoDemanda.iGetNome()).append(",false,true,true)){\n");
	        getValidacaoCampos().append( "  alert(\"Valor inválido para o campo ").append(objetoDemanda.iGetLabel()).append("\");\n" );
	        getValidacaoCampos().append(" return false; \n");
	        getValidacaoCampos().append( " } \n");
    	}

    }
    
    /**
     * Gera validação moeda.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoDemanda objetoDemanda
     */
    private void geraValidacaoMoeda(ObjetoDemanda objetoDemanda){
	   	getValidacaoCampos().append("if(document.form.").append(objetoDemanda.iGetNome()).append(".value != \"\" && !isValidMoeda(form.").append(objetoDemanda.iGetNome()).append(".value)){\n");
        getValidacaoCampos().append( "  alert(\"Valor inválido para o campo ").append(objetoDemanda.iGetLabel()).append("\");\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");       
    }
    
    /**
     * Gera validação Numero Decimal.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoDemanda objetoDemanda
     */
    private void geraValidacaoNumeroDecimal(ObjetoDemanda objetoDemanda){

    	//Verifica o tamanho do inteiro
    	getValidacaoCampos().append("if(document.form.").append(objetoDemanda.iGetNome()).append(".value != \"\" && (form.").append(objetoDemanda.iGetNome()).append(".value).length > 9){\n");
	    getValidacaoCampos().append( "  alert(\"Valor de no máximo 9 digitos para o campo ").append(objetoDemanda.iGetLabel()).append("\");\n" );
	    getValidacaoCampos().append( "  document.form.").append(objetoDemanda.iGetNome()).append(".focus();\n" );
	    getValidacaoCampos().append(" return false; \n");
	    getValidacaoCampos().append( " } \n");
	    
    	//Verifica se é um numero decimal
    	getValidacaoCampos().append("if(document.form.").append(objetoDemanda.iGetNome()).append(".value != \"\" && !validaDecimal(form.").append(objetoDemanda.iGetNome()).append(".value)){\n");
	    getValidacaoCampos().append( "  alert(\"Valor inválido para o campo ").append(objetoDemanda.iGetLabel()).append("\");\n" );
	    getValidacaoCampos().append( "  document.form.").append(objetoDemanda.iGetNome()).append(".focus();\n" );
	    getValidacaoCampos().append(" return false; \n");
	    getValidacaoCampos().append( " } \n");
    }
    
    /**
     * Gera validação Numero Decimal.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoDemanda objetoDemanda
     */
    private void geraValidacaoNumeroDecimalInicioFim(ObjetoDemanda objetoDemanda){

    	//Verifica o tamanho do inteiro
    	getValidacaoCampos().append("if(document.form.").append(objetoDemanda.iGetNome()).append("_Inicio.value != \"\" && (form.").append(objetoDemanda.iGetNome()).append("_Inicio.value).length > 9){\n");
	    getValidacaoCampos().append( "  alert(\"Valor de no máximo 9 digitos para o campo ").append(objetoDemanda.iGetLabel()).append("\");\n" );
	    getValidacaoCampos().append( "  document.form.").append(objetoDemanda.iGetNome()).append("_Inicio.focus();\n" );
	    getValidacaoCampos().append(" return false; \n");
	    getValidacaoCampos().append( " } \n");
	    
    	//Verifica se é um numero decimal
    	getValidacaoCampos().append("if(document.form.").append(objetoDemanda.iGetNome()).append("_Inicio.value != \"\" && !validaDecimal(form.").append(objetoDemanda.iGetNome()).append("_Inicio.value)){\n");
	    getValidacaoCampos().append( "  alert(\"Valor inválido para o campo ").append(objetoDemanda.iGetLabel()).append("\");\n" );
	    getValidacaoCampos().append( "  document.form.").append(objetoDemanda.iGetNome()).append("_Inicio.focus();\n" );
	    getValidacaoCampos().append(" return false; \n");
	    getValidacaoCampos().append( " } \n");
	    
    	//Verifica o tamanho do inteiro
    	getValidacaoCampos().append("if(document.form.").append(objetoDemanda.iGetNome()).append("_Fim.value != \"\" && (form.").append(objetoDemanda.iGetNome()).append("_Fim.value).length > 9){\n");
	    getValidacaoCampos().append( "  alert(\"Valor de no máximo 9 digitos para o campo ").append(objetoDemanda.iGetLabel()).append("\");\n" );
	    getValidacaoCampos().append( "  document.form.").append(objetoDemanda.iGetNome()).append("_Fim.focus();\n" );
	    getValidacaoCampos().append(" return false; \n");
	    getValidacaoCampos().append( " } \n");
	    
    	//Verifica se é um numero decimal
    	getValidacaoCampos().append("if(document.form.").append(objetoDemanda.iGetNome()).append("_Fim.value != \"\" && !validaDecimal(form.").append(objetoDemanda.iGetNome()).append("_Fim.value)){\n");
	    getValidacaoCampos().append( "  alert(\"Valor inválido para o campo ").append(objetoDemanda.iGetLabel()).append("\");\n" );
	    getValidacaoCampos().append( "  document.form.").append(objetoDemanda.iGetNome()).append("_Fim.focus();\n" );
	    getValidacaoCampos().append(" return false; \n");
	    getValidacaoCampos().append( " } \n");
	    
	    //Verifica se o número final é maior que o inicial
    	getValidacaoCampos().append("if(parseFloat(document.form.").append(objetoDemanda.iGetNome()).append("_Inicio.value) > parseFloat(document.form.").append(objetoDemanda.iGetNome()).append("_Fim.value)){\n");
    	getValidacaoCampos().append( "  alert(\"").append(objetoDemanda.iGetLabel()).append(" inicial maior que final").append("\");\n" );
	    getValidacaoCampos().append( "  document.form.").append(objetoDemanda.iGetNome()).append("_Inicio.focus();\n" );
	    getValidacaoCampos().append(" return false; \n");
	    getValidacaoCampos().append( " } \n");

    }

    
    /**
     * Gera validação de atributo livre.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoDemanda objetoDemanda
     */
    private void geraValidacaoAtributoLivre(ObjetoDemanda objetoDemanda){
    	if (!telaFiltro && (acao.equals("alterar") || acao.equals("incluir") 
    			|| acao.equals("classificar") )){ //Foi adicionada a eiçao de atributos livre em Classifica Demanda[Thaise 22/04/2008]
	    	if(objetoDemanda.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true && 
	    			objetoDemanda.iGetGrupoAtributosLivres() != null && 
	    			objetoDemanda.iGetGrupoAtributosLivres().getSisAtributoSatbs().size() > 0){
	    		
	    		SisGrupoAtributoSga grupoAtributo = objetoDemanda.iGetGrupoAtributosLivres();
	
	    		
	    		if (grupoAtributo != null && grupoAtributo.getIndAtivoSga().equals("S") && 
	    				grupoAtributo.getSisAtributoSatbs() != null && grupoAtributo.getSisAtributoSatbs().size() > 0){
	    			
	    			getValidacaoCampos().append("/**** Iní­cio Validação Atributo Livre " + objetoDemanda.iGetNome() + " ****/");
	    			
		    		if ( sisGrupoAtributoDao==null)  {
		    			getValidacaoCampos().append((new SisGrupoAtributoDao(null)).getValidacaoAtributo(grupoAtributo, objetoDemanda.iGetLabel()));
		    		} else {
		    			getValidacaoCampos().append(sisGrupoAtributoDao.getValidacaoAtributo(grupoAtributo, objetoDemanda.iGetLabel()));
		    		}    
		    		
		    		getValidacaoCampos().append("/**** Fim Validação Atributo Livre " + objetoDemanda.iGetNome() + " ****/");
	    		}
	    	}
    	} else if (telaFiltro){
    		SisGrupoAtributoSga grupoAtributo = objetoDemanda.iGetGrupoAtributosLivres();
    		if (grupoAtributo != null && grupoAtributo.getIndAtivoSga().equals("S") && 
    				grupoAtributo.getSisAtributoSatbs() != null && grupoAtributo.getSisAtributoSatbs().size() > 0){
	    		if (grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().intValue() == Input.VALIDACAO){
	    			if (grupoAtributo.getSisAtributoSatbs() != null && grupoAtributo.getSisAtributoSatbs().iterator().hasNext()){
		    			SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) grupoAtributo.getSisAtributoSatbs().iterator().next();
		    			if (sisAtributoSatb.getAtribInfCompSatb().equals("dataScript")) {
		    				getValidacaoCampos().append(validacaoIntervaloDataAtributoLivre(objetoDemanda)); 
		    			} else if (sisAtributoSatb.getAtribInfCompSatb().equals("numeroInteiroScript") ||
		    					   sisAtributoSatb.getAtribInfCompSatb().equals("numeroRealScript") ||
		    					   sisAtributoSatb.getAtribInfCompSatb().equals("valorMonetarioScript")){
		    				getValidacaoCampos().append(validacaoIntervaloNumeroAtributoLivre(objetoDemanda));
		    			}
	    			}
	    		}
    		}
    	}
    }
       
    /**
     * Gera validação de atributo livre para intervalo de datas.<br>
     * @param objetoDemanda
     * @return String
     */
    public String validacaoIntervaloDataAtributoLivre(ObjetoDemanda objetoDemanda){
    	StringBuffer validacao = new StringBuffer();
    	validacao.append("if(document.form.a").append(objetoDemanda.iGetGrupoAtributosLivres().getCodSga() + "_Fim").append(".value != '' && document.form.a").append(objetoDemanda.iGetGrupoAtributosLivres().getCodSga() + "_Inicio").append(".value != '') {\n" );
    	validacao.append("	if(DataMenor(form.a").append(objetoDemanda.iGetGrupoAtributosLivres().getCodSga() + "_Fim").append(".value, form.a").append(objetoDemanda.iGetGrupoAtributosLivres().getCodSga() + "_Inicio").append(".value)){\n");
    	validacao.append("  	alert(\"").append(objetoDemanda.iGetLabel()).append(" inicial maior que final").append("\");\n" );
    	validacao.append(" 		return false; \n");
    	validacao.append(" 	} \n");
    	validacao.append("} \n");
    	return validacao.toString();
    }
    
    /**
     * Gera validação de atributo livre para intervalo de números.<br>
     * @param objetoDemanda
     * @return String
     */
    public String validacaoIntervaloNumeroAtributoLivre(ObjetoDemanda objetoDemanda){
    	StringBuffer validacao = new StringBuffer();
    	validacao.append("var valorInicio = ").append("transformaValorMascaraMoedaParaDouble(form.a").append(objetoDemanda.iGetGrupoAtributosLivres().getCodSga() + "_Inicio").append(".value); \n");
    	validacao.append("var valorFim = ").append("transformaValorMascaraMoedaParaDouble(form.a").append(objetoDemanda.iGetGrupoAtributosLivres().getCodSga() + "_Fim").append(".value); \n");
    	validacao.append("if (valorInicio.length > 0 && valorFim.length > 0) { \n");
    	validacao.append("if(parseFloat(valorFim) < parseFloat(valorInicio)){\n");
    	validacao.append( "  alert(\"").append(objetoDemanda.iGetLabel()).append(" inicial maior que final").append("\");\n" );
    	validacao.append(" return false; \n");
    	validacao.append( " } \n }");
    	return validacao.toString();
    }
    
    
    /**
     * Gera validação de atributo livre para intervalo de números.<br>
     * @return String
     */
//    public String validacaoIntervaloNumeroAtributoLivre(ObjetoDemanda objetoDemanda){
//    	StringBuffer validacao = new StringBuffer();
//    	validacao.append("var maior = Math.Max(").append(objetoDemanda.iGetGrupoAtributosLivres().getCodSga() + "_Fim").append(".value,").append(objetoDemanda.iGetGrupoAtributosLivres().getCodSga() + "_Fim").append(".value)");
//    	validacao.append("if(maior == form.a").append(objetoDemanda.iGetGrupoAtributosLivres().getCodSga() + "_Fim").append(".value ){\n");
//    	validacao.append( "  alert(\"").append(objetoDemanda.iGetLabel()).append(" inicial maior que final").append("\");\n" );
//    	validacao.append(" return false; \n");
//    	validacao.append( " } \n");
//    	return validacao.toString();
//    }
    
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
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param page
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
     *
     * @return
     */
    public Boolean getTelaFiltro() {
		return telaFiltro;
	}


        /**
         *
         * @param telaFiltro
         */
        public void setTelaFiltro(Boolean telaFiltro) {
		this.telaFiltro = telaFiltro;
	}


        /**
         *
         * @return
         */
        public String getCodigoVisao() {
		return codigoVisao;
	}


        /**
         *
         * @param codigoVisao
         */
        public void setCodigoVisao(String codigoVisao) {
		this.codigoVisao = codigoVisao;
	}

	
}
