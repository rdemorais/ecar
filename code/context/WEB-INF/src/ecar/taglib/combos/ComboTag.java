/*
 * Criado em 26/10/2004
 *
 */
package ecar.taglib.combos;

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
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import comum.database.Dao;
import comum.util.Util;

/**
 * Classe para gera��o de Combos Gen�rica.<br>
 * 
 * Exemplo de Utiliza��o:<br>
 * <p>
 * combo:ComboTag <br>
 * nome="subArea"<br>
 * objeto="ecar.pojo.AreaAre"<br> 
 * label="nomeAre" <br>
 * value="codAre"<br>
 * filters="indAtivoAre=S"<br> 
 * order="nomeAre" <br>
 * selected="5"<br> 
 * 
 * @author felipev
 */
public class ComboTag implements Tag {

    /**
     * Value selecionado por default
     */
    private String selected;

    /**
     * Nome da combo. Obrigat�rio
     */
    private String nome;

    /**
     * Objetos que ser�o listados para a cria��o da combo. Obrigat�rio.<br>
     * Ex:<br>
     * ecar.pojo.AreaAre
     */
    private String objeto;

    /**
     * Atributo do objeto que servir� como label para a combo. Obrigat�rio.
     */
    private String label;

    /**
     * Atributo do objeto que servir� como Value para a combo. Obrigat�rio;
     */
    private String value;

    /**
     * Atributo do objeto pelo qual ser� feita a ordena��o dos registros na
     * combo
     */
    private String order;

    /**
     * Filtros que podem ser usados para restringir a lista de registros da
     * combo. Devem ser informados na forma "atributo=valor;atributo=valor".<br>
     * Ex:<br>
     * filters="indAtivoAre=S;nomeAre=Sa�de"
     */
    private String filters;

    /**
     * Func�es javascrip que podem ser chamadas quando ocorrem eventos com a
     * combo. Ex: scripts="onclick=\"funcao1()\" onchange=\"funcao2()\""
     */
    private String scripts;

    /**
     * Estilo da combo
     */
    private String style;

    /**
     * Cole��o de registros que ser�o aprensentados na lista. Caso esse
     * par�metro seja informado o taglib n�o far� o select no objeto selecionado
     * e retornar� somente os elementos desta Collection.<br>
     */
    private Collection colecao;

    /**
     * Lista com objetos que n�o aparecer�o na Check list.<br>
     */
    private Collection objetosExcluidos;
    
    /**
     * Se nulo mostra o primeiro option da combo em branco,
     * Sen�o, omite este, e mostra o primeiro como valor
     * Utilizado em combo com Multiplos valores (multiple).<br>
     */
    private String option;
    
    /**
     * Se nulo mostra o primeiro option da combo em branco,
     * Sen�o, omite este, e mostra o primeiro como valor
     * Utilizado em combo com Multiplos valores (multiple).<br>
     */
    private String disabled;
    
    /**
     * Texto padr�o para Option Vazia.<br>
     */
    private String textoPadrao;

    private PageContext page = null;

    private Logger logger = null;
    
    private Boolean ignorarTagSelect = new Boolean(false);
    
    private JspWriter writerParametro = null;

    private Boolean valida;
    
    private Integer tamanhoMaximoCombo = null;

	/**
     *
     * @return
     */
    public Boolean getValida() {
		return valida;
	}

    /**
     *
     * @param valida
     */
    public void setValida(Boolean valida) {
		this.valida = valida;
	}

        /**
         *
         */
        public ComboTag() {
        super();
        this.logger = Logger.getLogger(this.getClass());
    }
	
        /**
         *
         * @param writer
         */
        public ComboTag(JspWriter writer) {
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
        try {
            /** Cria uma inst�ncia do objeto * */
            Object obj = Class.forName(getObjeto()).newInstance();
            Dao dao = new Dao();
            Session session = dao.getSession();

            List lista = new ArrayList();

            if (getColecao() != null) {

                lista.addAll(getColecao());

            } else {

                /** Cria um criteria para selecionar o objeto desejado * */
                Criteria select = session.createCriteria(Class
                        .forName(getObjeto()));

                /** Adiciona os par�metros na query * */
                if (filters != null && !"".equals(filters)) {
                    String parametros[] = filters.split(";");
                    for (int i = 0; i < parametros.length; i++) {
                        String[] chaveValor = parametros[i].split("=");
                        	
                        if (chaveValor[1].equalsIgnoreCase("true")){
                            select.add(Expression.eq(chaveValor[0], Boolean.TRUE));
                        }
                        else if (chaveValor[1].equalsIgnoreCase("false")){
                            select.add(Expression.eq(chaveValor[0], Boolean.FALSE));
                        }
                        else if(Util.ehValor(chaveValor[1])){
                        	select.add(Expression.eq(chaveValor[0], Long.parseLong(chaveValor[1])));
                        }
                        else{
                            select.add(Expression.like(chaveValor[0], chaveValor[1]));
                        }
                    }
                }

                /** Define a ordem dos resultados * */
                
                if(order.indexOf(",")>-1){
                	String ordens[] = order.split(",");
                	for(int i=0;i<ordens.length;i++){
                		select.addOrder(Order.asc(ordens[i]));
                	}
                }
                else                
                	select.addOrder(Order.asc(order));
                lista = select.list();
            }

            /** Executa a query * */
            Iterator it = lista.iterator();

            if(!getIgnorarTagSelect().booleanValue()) {
	            /** Constroi a combo * */
	            s.append("<select name=\""+nome+"\" id=\""+nome+"\" ");
	            
	            if (getTamanhoMaximoCombo() != null){
	            	s.append("style=\"width: " + getTamanhoMaximoCombo().intValue() + "px\" ");
	            }
	            
	            if(scripts != null && !"".equals(scripts)){
	                s.append( scripts );
	            }

	            if (style != null && !"".equals(style)) {
	                s.append(" style=\"").append(style).append("\"");
	            }
	            
	            //Mantis: 8196
	            if (valida != null && valida.booleanValue()){ 
	            	s.append(" onChange =\"javascript:validaOpcao(this.form)\" ");
	            }
	            
	            if (disabled!=null && disabled.equals("true")) {
	            	s.append(" disabled =\"disabled\" ");
	            }
	            
	            s.append(" >");
            }

            if (getOption() == null){
            	if (getTextoPadrao() != null)
            		s.append("<option value=\"\">").append(getTextoPadrao()).append("</option>");
            	else
            		s.append("<option value=\"\"></option>");
            }
            
            while (it.hasNext()) {
                obj = it.next();
                if ((objetosExcluidos == null)
                        || (objetosExcluidos != null && !objetosExcluidos
                                .contains(obj))) {
                    s.append("<option value=\"");
                    /**
                     * invoca o M�todo getXXXX para recuperar o valor do
                     * atributo value *
                     */
                    Object value = Class.forName(getObjeto()).getMethod(
                            "get" + Util.primeiraLetraToUpperCase(getValue()),
                            null).invoke(obj, null);
                    s.append(value);
                    s.append("\"");
                    if ((value.toString()).equals(selected))
                        s.append(" selected ");
                    s.append(">");
                    /**
                     * invoca o M�todo getXXXX para recuperar o valor do
                     * atributo label *
                     */
                    if (!this.isMultiLabel())
                    {
                    	String conteudo = (String)Class.forName(getObjeto()).getMethod("get" + Util.primeiraLetraToUpperCase(getLabel()),null).invoke(obj, null);
                    	
                    	if (conteudo != null) {
                    		s.append(conteudo);
                    	} else {
                    		s.append("");
                    	}
                    }
                    else
                    {
                    	Iterator itLabels = this.getLabels().iterator();
                    	while (itLabels.hasNext())
                    	{
                        	s.append(Class.forName(getObjeto()).getMethod(
                                    "get" + Util.primeiraLetraToUpperCase((String)itLabels.next()),
                                    null).invoke(obj, null));
                        	s.append(" - ");
                    	}
                    	s.deleteCharAt(s.length()-2);                    	
                    }
                    s.append("</option>");
                }
            }
            if(!getIgnorarTagSelect().booleanValue()) {
            	s.append("</select>");
            }
            writer.print(s.toString());
        } catch (Exception e) {
        	this.logger.error(e);
        	try {
                writer.print("Erro na gera��o da Combo: " + e.getMessage());
                this.logger.error(e);
            } catch (IOException ioE) {
                this.logger.error(ioE);
            }

        }
        return Tag.EVAL_BODY_INCLUDE;
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
     * Retorna null.<br>
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
     * Retorna String scripts.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the scripts)
     */
    public String getScripts() {
        return scripts;
    }

    /**
     * Atribui valor especificado para String scripts.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param scripts
     */
    public void setScripts(String scripts) {
        this.scripts = scripts;
    }

    /**
     * Retorna String value.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the value)
     */
    public String getValue() {
        return value;
    }

    /**
     * Atribui valor especficado para String value.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
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
     * Atribui null para selected.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void release() {
        this.selected = null;
    }

    /**
     * Retorna String selected.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String
     */
    public String getSelected() {
        return selected;
    }

    /**
     * Atribui valor especifcado para String selected.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param string
     */
    public void setSelected(String string) {
        selected = string;
    }

    /**
     * Retorna String objeto.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the objeto)
     */
    public String getObjeto() {
        return objeto;
    }

    /**
     * Atribui valor especificado para String objeto.<br>
     * 
     * @param objeto
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setObjeto(String objeto) {
        this.objeto = objeto;
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
     * Retorna String filters.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the filters)
     */
    public String getFilters() {
        return filters;
    }

    /**
     * Atribui valor especificado para String filters.<br>
     * 
     * @param filters
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setFilters(String filters) {
        this.filters = filters;
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
     * Retorna List de labels selecionados.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return List
     */
    public List getLabels()
    {
        String labelAux = this.label;
        List labels = new ArrayList();
    	while(labelAux.lastIndexOf(",") != -1){
    		labels.add(labelAux.substring(0, labelAux.indexOf(",")));
    		labelAux = labelAux.substring(labelAux.indexOf(",") + 1);
        }      
    	labels.add(labelAux);
    	
    	return labels;
    }
    
    /**
     * Verifica se pode-se selecionar mais de um label.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return boolean
     */
    public boolean isMultiLabel()
    {
    	if (this.getLabel().lastIndexOf(",") == -1)
    			return false;
    	return true;
    }

    /**
     * Retorna String order.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the order)
     */
    public String getOrder() {
        return order;
    }

    /**
     * Atrinui valor especificado para String order.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param order
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * Retorna String nome.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the nome)
     */
    public String getNome() {
        return nome;
    }

    /**
     * Atribui valor especificado para String nome.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna String Style
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the style)
     */
    public String getStyle() {
        return style;
    }

    /**
     * Atribui valor especificado para String style.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param style
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Retorna Collection colecao.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return Collection - (Returns the colecao)
     */
    public Collection getColecao() {
        return colecao;
    }

    /**
     * Atribui valor especificado para Collection colecao.<br>
     * 
     * @param colecao
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setColecao(Collection colecao) {
        this.colecao = colecao;
    }

    /**
     * Retorna Collection objetosExcluidos.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return Collection - (Returns the objetosExcluidos)
     */
    public Collection getObjetosExcluidos() {
        return objetosExcluidos;
    }

    /**
     * Atribui valor especificado para Collection objetosExcluidos.<br>
     * 
     * @param objetosExcluidos
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setObjetosExcluidos(Collection objetosExcluidos) {
        this.objetosExcluidos = objetosExcluidos;
    }
	
    /**
     * Retorna String option.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String - (Returns the option)
	 */
	public String getOption() {
		return option;
	}
	/**
	 * Atribui valor especificado para String option.<br>
	 * 
         * @param option
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setOption(String option) {
		this.option = option;
	}
	/**
	 * Retorna String textoPadrao.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String - (Returns the textoPadrao)
	 */
	public String getTextoPadrao() {
		return textoPadrao;
	}
	/**
	 * Atribui valor especificado para String textoPadrao.<br>
	 * 
         * @param textoPadrao
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setTextoPadrao(String textoPadrao) {
		this.textoPadrao = textoPadrao;
	}

	/**
	 * Retorna Boolean ignorarTagSelect.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Boolean
	 */
	public Boolean getIgnorarTagSelect() {
		return ignorarTagSelect;
	}

	/**
	 * Atribui valor especificado para Boolean ignorarTagSelect.<br>
	 * 
         * @param ignorarTagSelect
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setIgnorarTagSelect(Boolean ignorarTagSelect) {
		this.ignorarTagSelect = ignorarTagSelect;
	}

	/**
	 * 
	 * @return
	 */
	public String getDisabled() {
		return disabled;
	}

	/**
	 * 
	 * @param disabled
	 */
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public Integer getTamanhoMaximoCombo() {
		return tamanhoMaximoCombo;
	}

	public void setTamanhoMaximoCombo(Integer tamanhoMaximoCombo) {
		this.tamanhoMaximoCombo = tamanhoMaximoCombo;
	}
	
}