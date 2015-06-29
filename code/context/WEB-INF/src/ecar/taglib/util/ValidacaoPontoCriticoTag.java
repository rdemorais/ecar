package ecar.taglib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

import comum.util.ConstantesECAR;
import comum.util.EcarCfg;
import comum.util.Util;

import ecar.action.ActionSisAtributo;
import ecar.dao.CorDao;
import ecar.dao.SisGrupoAtributoDao;
import ecar.exception.ECARException;
import ecar.pojo.Cor;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.PontoCriticoPtc;
import ecar.pojo.PontoCriticoSisAtributoPtcSatb;
import ecar.pojo.PontocriticoCorPtccorPK;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.TipoValor;
import ecar.util.Dominios;

/**
 * Taglib para gerar as rotinas de validação dos campos da tela de pontos críticos.<br>
 * Gera também as funções de retorno dos parãmetros do pop up de pesquisa.<br>
 * Permite validações específicas para cada campo que devem ser implementadas nos métodos geraValidacaoxxxxxxx<br>
 * 
 */
public class ValidacaoPontoCriticoTag implements Tag{
    private Collection atributos;

    private Collection sisGrupoAtributoSgaObrigatorio;
    
    private PageContext page = null;
    
    private StringBuffer validacaoCampos;
    
    private StringBuffer retornoPesquisa;
    
    private StringBuffer retornoPopUpUpload;
    
    private SisGrupoAtributoDao sisGrupoAtributoDao= null;

    private String acao = null;
    
    private PontoCriticoPtc pontoCritico;
    
    private String monitoramento = Dominios.NAO;
    
    /**
     *
     * @return
     */
    public PontoCriticoPtc getPontoCritico() {
		return pontoCritico;
	}

    /**
     *
     * @param pontoCritico
     */
    public void setPontoCritico(PontoCriticoPtc pontoCritico) {
		this.pontoCritico = pontoCritico;
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
            retornoPopUpUpload = new StringBuffer();
            int datas = 0;
            String labelDataIni = "";
            String labelDataFim = "";
                     
            if(atributos != null){
                Iterator it = atributos.iterator();
                while(it.hasNext()){
                    ObjetoEstrutura atributoEstrutura = (ObjetoEstrutura) it.next();
                    if(atributoEstrutura.iGetGrupoAtributosLivres() != null){
                    	geraValidacaoAtributoLivre(atributoEstrutura);
                	} else {
                    	try {
	                        this.getClass().
	                        	getMethod("geraValidacao" + Util.primeiraLetraToUpperCase(atributoEstrutura.iGetNome()), new Class[] { ObjetoEstrutura.class }).
	                        		invoke(this, new Object[] { atributoEstrutura });
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
            if(this.getSisGrupoAtributoSgaObrigatorio() != null && !this.getSisGrupoAtributoSgaObrigatorio().isEmpty()) {
            	s.append(new ecar.dao.SisGrupoAtributoDao(null).getValidacoesAtributos(new ArrayList(this.getSisGrupoAtributoSgaObrigatorio())));
            }
            s.append( "return true;\n");
            s.append("}\n");
            if(retornoPesquisa != null)
                s.append(retornoPesquisa);
            if(retornoPopUpUpload != null)
            	s.append(retornoPopUpUpload);
            writer.print(s.toString());
        } catch(Exception e){
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
        return Tag.EVAL_BODY_INCLUDE;
    }

    /**
     * Gera validação IndAtivoPtc.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoIndAtivoPtc(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorioRadioButton(atributo);
    }
    
    /**
     * Gera validação DataInclusaoPtc.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDataInclusaoPtc(ObjetoEstrutura atributo) {
        //if(atributo.iGetObrigatorio().booleanValue() == true)
        //    geraValidacaoCampoObrigatorio(atributo);
        //geraValidacaoData(atributo);
    }

    /**
     * Gera validação DataSolucaoPtc.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDataSolucaoPtc(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
        geraValidacaoData(atributo);
    }
    
    /**
     * Gera validação DataUltManutencaoPtc.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDataUltManutencaoPtc(ObjetoEstrutura atributo) {
        //if(atributo.iGetObrigatorio().booleanValue() == true)
        //    geraValidacaoCampoObrigatorio(atributo);
        //geraValidacaoData(atributo);
    }
    
    /**
     * Gera validação DescricaoSolucaoPtc.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoDescricaoSolucaoPtc(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
    }
    
    /**
     * Gera validação IndAmbitoInternoGovPtc.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoIndAmbitoInternoGovPtc(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorioRadioButton(atributo);
    }
    
    /**
     * Gera validação DataLimitePtc.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDataLimitePtc(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
        geraValidacaoData(atributo);
    }
    
    
    /**
     * Gera validação DataIdentificacaoPtc.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDataIdentificacaoPtc(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
        geraValidacaoData(atributo);
    }
    
    /**
     * Gera validação DescricaoPtc.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoDescricaoPtc(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
    }
    
    /**
     * Gera validação UsuarioUsu.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoUsuarioUsu(ObjetoEstrutura atributo) {
        if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
        geraPesquisaUsuario(atributo);
    }
    
    /**
     * Gera validação UsuarioUsuInclusao.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     */
    public void geraValidacaoUsuarioUsuInclusao(ObjetoEstrutura atributo) {
        //if(atributo.iGetObrigatorio().booleanValue() == true)
        //    geraValidacaoCampoObrigatorio(atributo);
    }
    
    /**
     * Gera validação UsuarioUsuByCodUsuUltManutPtc.<br>
     * 
     * @param atributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraValidacaoUsuarioUsuByCodUsuUltManutPtc(ObjetoEstrutura atributo) {
        //if(atributo.iGetObrigatorio().booleanValue() == true)
        //    geraValidacaoCampoObrigatorio(atributo);
    }
    
    /**
     * Gera validação PontoCriticoCorPtccores.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
     * @throws ECARException
     */
    public void geraValidacaoPontoCriticoCorPtccores(ObjetoEstrutura atributo) throws ECARException{
    	List setCores = new CorDao(null).listar(Cor.class, new String[]{"ordemCor","asc"});
		Cor cor = null;
		PontocriticoCorPtccorPK id = null;
		Iterator itCores = null;
		if (setCores != null)
			itCores = setCores.iterator();
		
		String imagePath = "";
		CorDao cDao = new CorDao(null);
		
		// Indica se algum marcado.
		getValidacaoCampos().append("algumAtivo = false; \n");
		
        // ----------------------------------------------------------------
		//
		// Definição de variáveis para uso na validação dos campos de antecedência e frequência de envio de email.
        //  
        // -- Valores devem ser maiores ou iguais a zero
        // -- A coluna de antecedência não deve permitir valores iguais. 
        //
        // ----------------------------------------------------------------
		
		// Usadas para indicar se valores válidos.
		getValidacaoCampos().append("ehNumero = true; \n");
		getValidacaoCampos().append("ehValido = true; \n");
		getValidacaoCampos().append("ehInteiro = true; \n");
		getValidacaoCampos().append("var er = /^[0-9]+$/; \n");;
		// -- 

		// Usadas para conter os valores dos campos.
		getValidacaoCampos().append("valorAntecedencia = 0; \n");
		getValidacaoCampos().append("valorFrequencia = 0; \n");
		// -- 

		// -- Usadas para tratamento de valores iguais. 
		getValidacaoCampos().append("i = 0; \n");
		getValidacaoCampos().append("var listaAntecedencia = new Array(); \n");
        // ---------------------------------------------------------------- 
		
		String campoAntecedencia = null, campoFrequenciaEnvio = null;
		String campoAtivo = null;
		
		while (itCores.hasNext())
		{
			cor = (Cor) itCores.next();
			if(cor.getIndPontoCriticoCor().equals("S")){

		        if(atributo.iGetObrigatorio().booleanValue() == true){
					// Valida se há algum marcado.
					getValidacaoCampos().append( " if (document.getElementById('ativo_").append(cor.getCodCor()).append("') != null) { \n");
					getValidacaoCampos().append( "   if (document.getElementById('ativo_").append(cor.getCodCor()).append("').checked) { \n");
			        getValidacaoCampos().append( " 	   algumAtivo = true; \n");
			        getValidacaoCampos().append( " 	 } \n");
			        getValidacaoCampos().append( " } \n");
		        }
		        

		        // ----------------------------------------------------------------
		 	    //
		        // Validação dos campos de antecedência e frequência de envio de email.
		        //  
		        // -- Valores devem ser maiores ou iguais a zero
		        // -- A coluna de antecedência não deve permitir valores iguais. 
		        //
		        // ----------------------------------------------------------------
		       
				// Pegando nomes dos campos que receberão tratamento.
		        campoAntecedencia    = "ant_"  + cor.getCodCor();
		        campoFrequenciaEnvio = "freq_" + cor.getCodCor();
		        campoAtivo = "ativo_" + cor.getCodCor(); 
		        
		        getValidacaoCampos().append( "  if (document.getElementById('" + campoAtivo + "').checked != false && ( document.getElementById('" + campoAntecedencia + "').value == ''  || document.getElementById('" + campoFrequenciaEnvio + "').value == '')) { \n");
				getValidacaoCampos().append("    alert(\"Valores na tabela '").append(atributo.iGetLabel()).append("' devem ser preenchidos quando ativo.\"); \n");
				getValidacaoCampos().append("    return false; \n");
				getValidacaoCampos().append("  } \n");
	        
				// Validação dos valores existentes nos campos Antecedencia e Frequencia.
				getValidacaoCampos().append( "  if (document.getElementById('" + campoAntecedencia + "') != null && document.getElementById('" + campoAntecedencia + "').value != '' ) { \n");
				getValidacaoCampos().append( "    valorAntecedencia = document.getElementById('" + campoAntecedencia + "').value; \n");
											    // Valida se é numérico e maior ou igual a zero.
				getValidacaoCampos().append( "    if ( isNaN(valorAntecedencia) && valorAntecedencia.indexOf(',')==-1) { \n");
		        getValidacaoCampos().append( " 	    ehNumero = false; \n");
		        getValidacaoCampos().append( "    } \n");
		        getValidacaoCampos().append( "    else { \n");
				getValidacaoCampos().append( "      if ( valorAntecedencia < 0 ) { \n");
		        getValidacaoCampos().append( " 	      ehValido = false; \n");
		        getValidacaoCampos().append( " 	    } \n");
				getValidacaoCampos().append( "      else if (!er.test(valorAntecedencia) ) { \n");
		        getValidacaoCampos().append( " 	      ehInteiro = false; \n");
		        getValidacaoCampos().append( " 	    } \n");
		        getValidacaoCampos().append( " 	    else { \n");
				getValidacaoCampos().append( "        listaAntecedencia[i] = valorAntecedencia; \n");
		        getValidacaoCampos().append( "        i++; \n");
		        getValidacaoCampos().append( "      } \n");
		        getValidacaoCampos().append( "    } \n");
		        getValidacaoCampos().append( "  } \n");
		        
				getValidacaoCampos().append( "  if (document.getElementById('" + campoFrequenciaEnvio + "') != null && document.getElementById('" + campoFrequenciaEnvio + "').value != '' ) { \n");
				getValidacaoCampos().append( "    valorFrequencia = parseInt(document.getElementById('" + campoFrequenciaEnvio + "').value); \n");
												// Valida se é numérico e maior ou igual a zero.
				getValidacaoCampos().append( "    if ( isNaN(valorFrequencia) ) { \n");
		        getValidacaoCampos().append( " 	    ehNumero = false; \n");
		        getValidacaoCampos().append( "    } \n");
		        getValidacaoCampos().append( "    else { \n");
				getValidacaoCampos().append( "      if ( valorFrequencia < 0) { \n");
		        getValidacaoCampos().append( " 	      ehValido = false; \n");
		        getValidacaoCampos().append( "      } \n");
		        getValidacaoCampos().append( "    } \n");
		        getValidacaoCampos().append( "  } \n");
		        // ----------------------------------------------------------------		        
		        
			}			
		}
        if(atributo.iGetObrigatorio().booleanValue() == true){
			// Tratando se não há marcação.
			getValidacaoCampos().append("if (!algumAtivo) { \n");
			getValidacaoCampos().append("  alert(\"Pelo menos um item associado à tabela '").append(atributo.iGetLabel()).append("' deve ficar ativo.\"); \n");
			getValidacaoCampos().append("  return false;");
			getValidacaoCampos().append("} \n");
        }

	    // Valida se existe repetição de valores.
		getValidacaoCampos().append( " j = 0; \n");
		getValidacaoCampos().append( " existeIgual = false; \n");
        
        getValidacaoCampos().append( " if( ehNumero && ehValido ) { \n");
        getValidacaoCampos().append( "   for(i = 0; i < listaAntecedencia.length && !existeIgual; i++) { \n");
        getValidacaoCampos().append( "     v = listaAntecedencia[i]; \n");
        getValidacaoCampos().append( "     for(j = i; j < listaAntecedencia.length; j++) { \n");
        getValidacaoCampos().append( "       if(i == j) continue; \n");
        getValidacaoCampos().append( "       if( v == listaAntecedencia[j] ) { \n");
        getValidacaoCampos().append( "         existeIgual = true; \n");
        getValidacaoCampos().append( "         break; \n");
        getValidacaoCampos().append( "       } \n");
        getValidacaoCampos().append( "     } \n");
        getValidacaoCampos().append( "   } \n");        
        getValidacaoCampos().append( " } \n");
        
        // ----------------------------------------------------------------
		// Tratando número inválido.
		getValidacaoCampos().append("  if ( !ehNumero ) { \n");
		getValidacaoCampos().append("    alert(\"Valor na tabela '").append(atributo.iGetLabel()).append("' deve ser numérico.\"); \n");
		getValidacaoCampos().append("    return false; \n");
		getValidacaoCampos().append("  } \n");
		getValidacaoCampos().append("  if ( !ehValido ) { \n");
		getValidacaoCampos().append("    alert(\"Valor na tabela '").append(atributo.iGetLabel()).append("' deve ser maior ou igual a zero.\"); \n");
		getValidacaoCampos().append("    return false; \n");
		getValidacaoCampos().append("  } \n");
		getValidacaoCampos().append("  if ( !ehInteiro ) { \n");
		getValidacaoCampos().append("    alert(\"Valor na tabela '").append(atributo.iGetLabel()).append("' deve ser inteiro.\"); \n");
		getValidacaoCampos().append("    return false; \n");
		getValidacaoCampos().append("  } \n");

		// Tratando repeticão de número.
		getValidacaoCampos().append("  if (existeIgual) { \n");
		getValidacaoCampos().append("    alert(\"Valores para antecedência na tabela '").append(atributo.iGetLabel()).append("' devem ser distintos.\"); \n");
		getValidacaoCampos().append("    return false; \n");
		getValidacaoCampos().append("  } \n");
		
		getValidacaoCampos().append("  if (existeIgual) { \n");
		getValidacaoCampos().append("    alert(\"Valores para antecedência na tabela '").append(atributo.iGetLabel()).append("' devem ser distintos.\"); \n");
		getValidacaoCampos().append("    return false; \n");
		getValidacaoCampos().append("  } \n");

		
		// ----------------------------------------------------------------
    }
    
    /**
     * Gera validação de acompRelatorioArel
     * 
     * @param atributo
     */
    public void geraValidacaoAcompRelatorioArel(ObjetoEstrutura atributo){
    	if(monitoramento.equals(Dominios.SIM) && atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorio(atributo);
    }

    /**
     * Gera validação de sisAtributoTipo
     * 
     * @param atributo
     */
    public void geraValidacaoSisAtributoTipo(ObjetoEstrutura atributo){
    	if(atributo.iGetObrigatorio().booleanValue() == true)
            geraValidacaoCampoObrigatorioSisAtributoTipo(atributo);
    }


    /**
     * Gera o método de retorno da pesquisa de usuário
     * 
     * @param atributo
     */
    public void geraPesquisaUsuario(ObjetoEstrutura atributo){
        getRetornoPesquisa().append( "function retorno").append(atributo.iGetNome()).append("(codigo, descricao) {\n");
        getRetornoPesquisa().append( "document.form.nome").append(atributo.iGetNome()).append(".value = descricao;\n");
        getRetornoPesquisa().append( "document.form.").append(atributo.iGetNome()).append(".value = codigo;\n");
        getRetornoPesquisa().append("}\n");
    }

    /**
     * Gera a função de abrir o popup de upload de arquivos
     * 
     */
    public void geraPopUpUpload(){
    	getRetornoPopUpUpload().append(" function abrirPopUpUpload(nome, labelCampo) {\n ");
    	getRetornoPopUpUpload().append(" 	abreJanela(\"../../usuarios/usuarios/popUpUpload.jsp?nomeCampo=\"+nome+\"&labelCampo=\"+labelCampo+\"&excluir=\",	500, 100, nome); \n");
    	getRetornoPopUpUpload().append(" } \n");
    	getRetornoPopUpUpload().append(" function abrirPopUpUpload(nome, labelCampo, excluir) { \n");
    	getRetornoPopUpUpload().append("	abreJanela(\"../../usuarios/usuarios/popUpUpload.jsp?nomeCampo=\"+nome+\"&labelCampo=\"+labelCampo+\"&excluir=\"+excluir,	500, 100, nome); \n");
    	getRetornoPopUpUpload().append(" } \n");
    	
    }

    /**
     * Gera validação campo obrigatorio.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoEstrutura atributo
     */
    private void geraValidacaoCampoObrigatorio(ObjetoEstrutura atributo){        
        getValidacaoCampos().append( " if (document.form.").append(atributo.iGetNome()).append(".value == \"\"){\n" );
        getValidacaoCampos().append( "  alert(\"Obrigatório o Preenchimento do Campo ").append(atributo.iGetLabel()).append("\");\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");
    }
    
    /**
     * Gera validação campo obrigatorio para o tipo de restrição.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param ObjetoEstrutura atributo
     */
    private void geraValidacaoCampoObrigatorioSisAtributoTipo(ObjetoEstrutura atributo){        
        getValidacaoCampos().append( " if (document.form.").append("codSgaPontoCritico").append(".value == \"\"){\n" );
        getValidacaoCampos().append( "  alert(\"Obrigatório o Preenchimento do Campo ").append(atributo.iGetLabel()).append("\");\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");
    }
    
    /**
     * Gera validação campo obrigatorio do tipo RadioButton.<br>
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
    	getValidacaoCampos().append( "  	alert(\"Obrigatória a seleção do Campo ").append(atributo.iGetLabel()).append("\");\n" );
    	getValidacaoCampos().append("		return false;\n");
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
        getValidacaoCampos().append("if(form.").append(atributo.iGetNome()).append(".value != \"\" && !validaData(form.").append(atributo.iGetNome()).append(",false,true,true)){\n");
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
        getValidacaoCampos().append("if(document.form.").append(atributo.iGetNome()).append(".value != \"\" && !isValidMoeda(form.").append(atributo.iGetNome()).append(".value)){\n");
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
        getValidacaoCampos().append("if(document.form.").append(atributo.iGetNome()).append(".value != \"\" && !validaDecimal(form.").append(atributo.iGetNome()).append(".value)){\n");
        getValidacaoCampos().append( "  alert(\"Valor inválido para o campo ").append(atributo.iGetLabel()).append("\");\n" );
        getValidacaoCampos().append( "  document.form.").append(atributo.iGetNome()).append(".focus();\n" );
        getValidacaoCampos().append(" return false; \n");
        getValidacaoCampos().append( " } \n");
        
    }
    
    private void geraValidacaoAtributoLivre(ObjetoEstrutura atributo) throws ECARException{

    	if (atributo.iGetGrupoAtributosLivres() != null && !atributo.iGetGrupoAtributosLivres().getSisAtributoSatbs().isEmpty()) {
	    	Iterator<SisAtributoSatb> it = atributo.iGetGrupoAtributosLivres().getSisAtributoSatbs().iterator();
	    	
	    	SisAtributoSatb sisAtributo = (SisAtributoSatb)it.next();
	    	
	    	if (sisAtributo.getAtribInfCompSatb() != null && sisAtributo.isAtributoMascaraEditavel()) {
	    		getValidacaoCampos().append("/**** Início Validação Máscara Editável " + atributo.iGetNome() + " ****/\n");    		
	    		getValidacaoCampos().append(montaScriptValidaMascaraEditavel(atributo,atributo.iGetGrupoAtributosLivres(),sisAtributo));
	    		getValidacaoCampos().append("/**** Fim Validação Máscara Editável " + atributo.iGetNome() + " ****/\n");
	    	} else {
	    	
		    	if(atributo.iGetObrigatorio().booleanValue() == true && atributo.iGetGrupoAtributosLivres() != null){
		    		SisGrupoAtributoSga grupoAtributo = atributo.iGetGrupoAtributosLivres();
		
		    		getValidacaoCampos().append("/**** Início Validação Atributo Livre " + atributo.iGetNome() + " ****/\n");    		
		    		if ( sisGrupoAtributoDao==null)  {
		    			getValidacaoCampos().append((new SisGrupoAtributoDao(null)).getValidacaoAtributo(grupoAtributo, false, atributo.iGetLabel()));
		    		} else {
		    			getValidacaoCampos().append(sisGrupoAtributoDao.getValidacaoAtributo(grupoAtributo, false, atributo.iGetLabel()));
		    		}    		    		
		    		getValidacaoCampos().append("/**** Fim Validação Atributo Livre " + atributo.iGetNome() + " ****/\n");
		    	}
		    	if (atributo.iGetGrupoAtributosLivres() != null && atributo.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().intValue() == Input.IMAGEM){
		    		if (getRetornoPopUpUpload() == null || getRetornoPopUpUpload().toString().equals("")){
		    			geraPopUpUpload();
		    		}
		    	}
	    	}
    	}
    }
        
    private String montaScriptValidaMascaraEditavel(ObjetoEstrutura atributo,SisGrupoAtributoSga grupo,SisAtributoSatb sisAtributo) throws ECARException {
		
    	StringBuffer ret = new StringBuffer(); 
    	
    	String nomeAtributoLivre = "a" + grupo.getCodSga().toString();
    	
    	if (acao != null && acao.equals(ConstantesECAR.ACAO_ALTERAR)){

    		Set atributoLivres = pontoCritico.getPontoCriticoSisAtributoPtcSatbs();	
    		PontoCriticoSisAtributoPtcSatb atributoLivre= pontoCritico.buscarItemEstruturaSisAtributoLista(sisAtributo);
    		
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
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributos
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
         * @param sisGrupoAtributoSgaObrigatorio
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setSisGrupoAtributoSgaObrigatorio(Collection sisGrupoAtributoSgaObrigatorio) {
		this.sisGrupoAtributoSgaObrigatorio = sisGrupoAtributoSgaObrigatorio;
	}

	/**
	 * 
	 * @return
	 */
	public StringBuffer getRetornoPopUpUpload() {
		return retornoPopUpUpload;
	}

	/**
	 * 
	 * @param retornoPopUpUpload
	 */
	public void setRetornoPopUpUpload(StringBuffer retornoPopUpUpload) {
		this.retornoPopUpUpload = retornoPopUpUpload;
	}

        /**
         *
         * @return
         */
        public String getMonitoramento() {
		return monitoramento;
	}

        /**
         *
         * @param monitoramento
         */
        public void setMonitoramento(String monitoramento) {
		this.monitoramento = monitoramento;
	}
}
