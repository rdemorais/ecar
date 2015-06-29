/*
 * Criado em 08/12/2004
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

import ecar.dao.ConfiguracaoDao;
import ecar.pojo.AtributoLivre;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;


/**
 * Taglib que gera uma lista de Opções para um controle HTML.<br>
 * 
 * @author felipev
 */
public class Options implements Tag {

    /**
     * Collection com as opções a serem mostradas no Controle
     */
    private Collection options;

    /**
     * Define o atributo dos objetos da Collection que será acessado para
     * definir os Values das Opções
     */
    private String valor;

    /**
     * Define se uma opção vazia deve ser incluída na lista
     */
    private boolean opcaoVazia;

    /**
     * Define o atributo dos objetos da Collection que será acessado para
     * definir o Label das Opções
     */
    private String label;
    
    /**
     * Atributo que define se as opções do nível de planejamento é para ser checkbox (false = DEFAULT)
     * Utilizado no cadastro de usuários
     */
    private Boolean nivelPlanejamentoCheckBox = new Boolean(false);

    private PageContext page = null;

    private Tag parent;
    
    private String imagem;
    
    private String dica;
    
    private String nome;
    
    private String contexto;
    
    private String sValueHidden;
    private String nameHidden;

    /**
     *
     */
    public static final int COMBOBOX = 1;
    /**
     *
     */
    public static final int CHECKBOX = 2;
    /**
     *
     */
    public static final int LISTBOX = 3;
    /**
     *
     */
    public static final int RADIO_BUTTON = 4;
    /**
     *
     */
    public static final int TEXT = 5;
    /**
     *
     */
    public static final int IMAGEM = 6;
    /**
     *
     */
    public static final int MULTITEXTO = 7;
    /**
     *
     */
    public static final int VALIDACAO = 8;

    /**
     *
     */
    public static final long MAXLENGTH = 200;
    private JspWriter writerParametro = null;
    
    private boolean transformarComboBoxListaChecks = false;
    private boolean transformarRadioListaChecks = false;
    
    /**
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public Options() {
    	
    }
    
    /**
     * Atribui valor especificado para JspWriter writerParametro.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param writer
     */
    public Options(JspWriter writer) {
    	this.writerParametro = writer;
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
    	JspWriter writer = null;
    	if(writerParametro != null) {
    		writer = writerParametro;
    	} else {
        	writer = this.page.getOut();
    	}
        StringBuffer s = new StringBuffer();
        Input tagInput = (Input) getParent();
        try {
            ConfiguracaoCfg configuracao = new ConfiguracaoDao(null).getConfiguracao();

            if (opcaoVazia) {
                if (tagInput.getTipo() == LISTBOX) {
                    s.append("<option value=\"\"></option>");
                }
            } else {
                /* comboBox sempre inicia com option vazia */
                if (tagInput.getTipo() == COMBOBOX) {
                    s.append("<option value=\"\"></option>");
                }

            }

            Iterator it = options.iterator();
            int countOptions = 0;

            while (it.hasNext()) {
            	
            	countOptions++;
            	
                String sValue = "";
                String sLabel = "";
                /**
                 * invoca o Método getXXXX para recuperar o valor do atributo
                 * value *
                 */
                Object obj = it.next();
                sValue = obj.getClass().getMethod("get"+ Util.primeiraLetraToUpperCase(getValor()),null).invoke(obj, null).toString();
                sLabel = obj.getClass().getMethod("get"+ Util.primeiraLetraToUpperCase(getLabel()),null).invoke(obj, null).toString();

                int tipo = tagInput.getTipo(); 
                
                if(getNivelPlanejamentoCheckBox().booleanValue()
                		&& tagInput.getSisGrupoAtributoSga() != null
                		&& configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan() != null 
                		&& tagInput.getSisGrupoAtributoSga().equals(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan())
                		&& tipo != CHECKBOX) {
                	tipo = CHECKBOX;
                }
                
                switch (tipo) {
                case COMBOBOX:
                	if (!transformarComboBoxListaChecks)
                		addOpcaoComboBox(sValue, sLabel, obj, s, tipo, countOptions);
                	else
                		addOpcaoCheckBox(sValue, sLabel, obj, s, countOptions);
                	break;
                case LISTBOX:
                    addOpcaoComboBox(sValue, sLabel, obj, s, tipo, countOptions);
                    break;
                case CHECKBOX:
                    addOpcaoCheckBox(sValue, sLabel, obj, s, countOptions);
                    break;
                case RADIO_BUTTON:
                	if(!transformarRadioListaChecks)
                		addOpcaoRadioButton(sValue, sLabel, obj, s, countOptions);
                	else
                		addOpcaoCheckBox(sValue, sLabel, obj, s, countOptions);
                	break;
                case MULTITEXTO:
                	               	
                    addOpcaoMultiTexto(sValue, sLabel, obj, s, countOptions);
                    break;
                }

            }
            if (sValueHidden != null & nameHidden != null){
	            s.append("<input type=\"hidden\" name=\"hidden_").append(nameHidden).append("\"");
	            s.append(" value=\"");
	            s.append(sValueHidden);
	            s.append("\"");
	            s.append(">");
            }
            writer.print(s.toString());
        } catch (Exception e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
        return Tag.EVAL_BODY_INCLUDE;
    }

    /**
     * Adiciona Opção comboBox.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param sValue
     * @param obj 
     * @param sLabel
     * @param s
     * @param tipo
     * @param countOptions
     */
    public void addOpcaoComboBox(String sValue, String sLabel, Object obj,
            StringBuffer s, int tipo, int countOptions) {
        Input tagInput = (Input) getParent();
        s.append("<option value=\"").append(sValue).append("\"");
        
        if (tagInput.getSelecionados() != null)
            if (tagInput.atributoSelecionado((SisAtributoSatb)obj))
                s.append(" selected ");
        s.append(">");
        s.append(sLabel);
        s.append("</option>");
        
        if (tagInput.getSelecionados() != null){
          if (tagInput.atributoSelecionado((SisAtributoSatb)obj)){
        	  sValueHidden = sValue;
        	  nameHidden = tagInput.getNome();
          }
        }
    }
    
//    public void addOpcaoComboBoxHidden(String sValue, String sLabel, Object obj,
//            StringBuffer s, int tipo, int countOptions){
//      if (tagInput.getSelecionados() != null){
//      if (tagInput.atributoSelecionado((SisAtributoSatb)obj)){
//      	s.append("<input type=\"hidden\" name=\"hidden_").append(tagInput.getNome()).append("\"");
//      	s.append(" value=\"");
//      	s.append(sValue);
//      	s.append("\"");
//      	s.append(" />");
//      }
//
//    }

    /**
     * Adiciona opção CheckBox.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param sValue
     * @param sLabel
     * @param obj
     * @param s
     * @param countOptions
     */
    public void addOpcaoCheckBox(String sValue, String sLabel, Object obj,
            StringBuffer s, int countOptions) {
        try{
	    	Input tagInput = (Input) getParent();
	        ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(null);
	        SisGrupoAtributoSga sgaNvPl = configuracaoDao.getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan();
	        
	        SisAtributoSatb satb = (SisAtributoSatb) obj;
	        if(sgaNvPl != null && satb.getSisGrupoAtributoSga().equals(sgaNvPl) ){
	        	s.append("<img src=\"").append(getImagem() + satb.getAtribInfCompSatb()).append("\">");	        	
	        }
	        s.append("<input type=\"checkbox\" class=\"form_check_radio\"  name=\"").append(tagInput.getNome())
	                .append("\" value=\"").append(sValue).append("\"");
	        if (tagInput.getSelecionados() != null)
	            if (tagInput.atributoSelecionado(satb))
	                s.append(" checked ");
	        if (tagInput.getDisabled() != null && "S".equals(tagInput.getDisabled()))    
	               s.append(" disabled "); 
	        s.append(">");	        
	        s.append(sLabel);
	        if (countOptions == 1){
	        	if( dica != null && !"".equals(dica) ){
	            	if (contexto != null){
	            		s.append(Util.getTagDica(nome, contexto, dica));
	            	}
	            }
	        }
	        s.append("<br>\n"); 
	        
	        if (tagInput.getSelecionados() != null){
	            if (tagInput.atributoSelecionado(satb) && !"".equals(sValue)){
	            	s.append("<input type=\"hidden\" name=\"hidden_").append(tagInput.getNome()).append("\"");
		        	s.append(" value=\"");
		        	s.append(sValue);
		        	s.append("\"");
		        	s.append(" />");
	            }
	        }    
	               
	        
        }catch(Exception e){
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);	
        }
    }

    /**
     * Adiciona opção multiTexto.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param sValue
     * @param sLabel 
     * @param obj
     * @param s
     * @param countOptions
     */
    public void addOpcaoMultiTexto(String sValue, String sLabel, Object obj,
            StringBuffer s, int countOptions) {
        Input tagInput = (Input) getParent();
                        
        s.append("\n<td class=\"").append(tagInput.getClassLabel()).append("\">");
        s.append(sLabel); 
                    
        s.append("</td>\n<td><input type=\"text\"  name=\"").append(tagInput.getNome())
        		.append("_").append(sValue).append("\"");  
        s.append(" size = \"").append(tagInput.getSize()).append("\" maxlength = \"").append(MAXLENGTH).append("\" ");
        
        if (tagInput.getSelecionados() != null && !tagInput.getSelecionados().isEmpty())
        {
        	Iterator iMulti = tagInput.getSelecionados().iterator();
        	while (iMulti.hasNext())
        	{
        		AtributoLivre atribLivre = (AtributoLivre) iMulti.next();
        		if(atribLivre.getSisAtributoSatb().equals(obj))
        			s.append(" value=\"").append(atribLivre.getInformacao()).append("\"");
        	}
        }
        if (tagInput.getDisabled() != null && "S".equals(tagInput.getDisabled()))    
               s.append(" disabled "); 
        s.append("/>");
        
        s.append("</td></tr>"); 
        
        s.append("<input type=\"hidden\" name=\"hidden_").append(tagInput.getNome()).append("_").append(sValue).append("\"");
        if (tagInput.getSelecionados() != null && !tagInput.getSelecionados().isEmpty())
        {
        	Iterator iMulti = tagInput.getSelecionados().iterator();
        	while (iMulti.hasNext())
        	{
        		AtributoLivre atribLivre = (AtributoLivre) iMulti.next();
        		if(atribLivre.getSisAtributoSatb().equals(obj))
        			s.append(" value=\"").append(atribLivre.getInformacao()).append("\"");
        	}
        } else {
        	s.append(" value=\"\"");
        }
        s.append(">");

        s.append("\n<tr>");		
    }
    
    /**
     * Adiciona opção RadioButton.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param sValue
     * @param sLabel
     * @param obj
     * @param s
     * @param countOptions
     */
    public void addOpcaoRadioButton(String sValue, String sLabel, Object obj,
            StringBuffer s, int countOptions) {
        
        try{
        	Input tagInput = (Input) getParent();
        	ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(null);
	        SisGrupoAtributoSga sgaNvPl = configuracaoDao.getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan();
	        
	        SisAtributoSatb satb = (SisAtributoSatb) obj;
	        if(sgaNvPl != null && satb.getSisGrupoAtributoSga().equals(sgaNvPl) ){
	        	s.append("<img src=\"").append(getImagem() + satb.getAtribInfCompSatb()).append("\">");
	        }
	        s.append("<input type=\"radio\" class=\"form_check_radio\" name=\"").append(tagInput.getNome())
	                .append("\" value=\"").append(sValue).append("\"");
	        if (tagInput.getSelecionados() != null)
	            if (tagInput.atributoSelecionado((SisAtributoSatb)obj))
	                s.append(" checked");
	        if (tagInput.getDisabled() != null &&  "S".equals(tagInput.getDisabled()))    
	            s.append(" disabled ");    
	        s.append(">");
	        s.append(sLabel);
	        if (countOptions == 1){
	        	s.append("&nbsp;<input type=\"button\" name=\"buttonLimpar\" value=\"Limpar\" class=\"botao\" ")
                .append("onclick=\"limparRadioButtons(document.getElementsByName('" + nome + "'));\"");
	        	if (tagInput.getDisabled() != null &&  "S".equals(tagInput.getDisabled()))    
		            s.append(" disabled "); 
	        	s.append(">");
	        	if( dica != null && !"".equals(dica) ){
	            	if (contexto != null){
	            		s.append(Util.getTagDica(nome, contexto, dica));
	            	}
	            }
	        }
	        s.append("<br>\n");
	        
	        
	        if (tagInput.getSelecionados() != null){
	            if (tagInput.atributoSelecionado((SisAtributoSatb)obj) && !"".equals(sValue)){
	            	s.append("<input type=\"hidden\" name=\"hidden_").append(tagInput.getNome()).append("\"");
		        	s.append(" value=\"");
		        	s.append(sValue);
		        	s.append("\"");
		        	s.append(">");
	            }
	        }    
	        
	        
        }catch(Exception e){
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);	
        }
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
     * Atribui valor especificado para Tag parent.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param arg0
     */
    public void setParent(Tag arg0) {
        parent = arg0;
    }

    /**
     * Retorna Tag parent.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Tag
     */
    public Tag getParent() {
        return parent;
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
     * 
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void release() {
        //this.selected = null;
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
     * Retorna String label.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the label)
     */
    public String getLabel() {
        return label;
    }

    /**
     * Atribui valor especificado para String label.<br>
     * 
     * @param label
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Retorna Collection options
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return Collection - (Returns the options)
     */
    public Collection getOptions() {
        return options;
    }

    /**
     * Atribui valor especificado para Collection options.<br>
     * 
     * @param options
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setOptions(Collection options) {
        this.options = options;
    }

    /**
     * Retorna String valor.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the valor)
     */
    public String getValor() {
        return valor;
    }

    /**
     * Atribui valor especificado para String valor.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param valor
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * Retorna boolean opcaoVazia.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return boolean - (Returns the opcaoVazia)
     */
    public boolean isOpcaoVazia() {
        return opcaoVazia;
    }

    /**
     * Atribui valor especificado para boolean opcaoVazia.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param opcaoVazia
     */
    public void setOpcaoVazia(boolean opcaoVazia) {
        this.opcaoVazia = opcaoVazia;
    }

    /**
     * Retorna Boolean nivelPlanejamentoCheckBox.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return Boolean
     */
	public Boolean getNivelPlanejamentoCheckBox() {
		return nivelPlanejamentoCheckBox;
	}

	/**
	 * Atribui valor especificado para Boolean nivelPlanejamentoCheckBox.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param nivelPlanejamentoCheckBox
	 */
	public void setNivelPlanejamentoCheckBox(Boolean nivelPlanejamentoCheckBox) {
		this.nivelPlanejamentoCheckBox = nivelPlanejamentoCheckBox;
	}

        /**
         *
         * @return
         */
        public String getImagem() {
		return imagem;
	}

        /**
         *
         * @param imagem
         */
        public void setImagem(String imagem) {
		this.imagem = imagem;
	}

        /**
         *
         * @return
         */
        public String getDica() {
		return dica;
	}

        /**
         *
         * @param dica
         */
        public void setDica(String dica) {
		this.dica = dica;
	}

        /**
         *
         * @return
         */
        public String getNome() {
		return nome;
	}

        /**
         *
         * @param nome
         */
        public void setNome(String nome) {
		this.nome = nome;
	}

        /**
         *
         * @return
         */
        public String getContexto() {
		return contexto;
	}

        /**
         *
         * @param contexto
         */
        public void setContexto(String contexto) {
		this.contexto = contexto;
	}

        /**
         *
         * @return
         */
        public boolean isTransformarComboBoxListaChecks() {
		return transformarComboBoxListaChecks;
	}

        /**
         *
         * @param transformarComboBoxListaChecks
         */
        public void setTransformarComboBoxListaChecks(boolean transformarComboBoxListaChecks) {
		this.transformarComboBoxListaChecks = transformarComboBoxListaChecks;
	}

        /**
         *
         * @return
         */
        public boolean isTransformarRadioListaChecks() {
		return transformarRadioListaChecks;
	}

        /**
         *
         * @param transformarRadioListaChecks
         */
        public void setTransformarRadioListaChecks(boolean transformarRadioListaChecks) {
		this.transformarRadioListaChecks = transformarRadioListaChecks;
	}
	
	
}