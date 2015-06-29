/*
 * Criado em 28/10/2004
 *
 */
package ecar.taglib.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.collection.PersistentSet;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import comum.database.Dao;
import comum.util.Util;

import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.TipoFuncAcompTpfaPermiteAlterar;
import ecar.pojo.TipoFuncAcompTpfaPermiteAlterarPK;

/**
 * @author felipev
 * 
 * Taglib para geração de listas para seleção múltipla (Checkbox)
 *
 */
public class CheckListVariosNiveisTag implements Tag{
    
	/**
	 * Nome dos checkbox. Obrigatório
	 */
	private String nome;
	/**
	 * Objetos que serão listados para a criação da lista. Obrigatório. Ex: ecar.pojo.AreaAre
	 */	
	private String objeto;	
	/**
	 * Vetor com os values selecionados por default
	 */
	private Collection selected;	
	/**
	 * Atributo do objeto que servirá como label para os checkbox. Obrigatório.
	 */
	private String label;
	/**
	 * Atributo do objeto que servirá como Value para os checkbox. Obrigatório;
	 */
	private String value;
	/**
	 * Atributo do objeto pelo qual será feita a ordenação dos registros na combo
	 */
	private String order;
	/**
	 * Filtros que podem ser usados para restringir a lista de registros apresentados. Devem ser informados na forma
	 * "atributo=valor;atributo=valor". Ex: filters="indAtivoAre=S;nomeAre=Saúde"
	 */
	private String filters;
	/**
	 * Funcões javascrip que podem ser chamadas quando ocorrem eventos com as opções.
	 * Ex: scripts="onclick=\"funcao1()\" onchange=\"funcao2()\""
	 */
	private String scripts;
	
	/**
	 * Coleção de registros que serão parensentados na lista. Caso esse parâmetro seja informado o taglib não fará o select no 
	 * objeto selecionado e retornará somente os elementos desta Collection
	 */
	private Collection colecao;
	
	/**
	 * Lista com objetos que não aparecerão na Check list
	 */
	private Collection objetosExcluidos;
	
	private Object objetoAlterado;
	
	private PageContext page = null;	
	
	private int nivel = 0;
	
	private long primeiroNivel = 0;
	
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
		JspWriter writer = this.page.getOut();
		StringBuffer s = new StringBuffer();
		try {
			
			List itensLista = new ArrayList();
			
			/** Cria uma instância do objeto **/	
		    Object obj = Class.forName(getObjeto()).newInstance();
		    Dao dao = new Dao();
		    Session session = dao.getSession();
			
			if (getColecao() == null){
			    /** Cria um criteria para selecionar o objeto desejado **/
			    Criteria select = session.createCriteria( Class.forName(getObjeto()) );
			    
			    /** Adiciona os parâmetros na query **/
			    if(filters != null && !"".equals(filters)){
			        String parametros[] = filters.split(";");
			        for(int i = 0; i < parametros.length; i++){
			            String[] chaveValor = parametros[i].split("=");
			            select.add(Expression.like(chaveValor[0], chaveValor[1]));		            
			        }
				 }
			    
			    /** Define a ordem dos resultados **/
			    select.addOrder(Order.asc(order));
			    itensLista = select.list();
			}else{
				itensLista.addAll(getColecao());
			}
		    
		    /** Executa a query **/
		    Iterator it = itensLista.iterator();
		   // Object obj2 = null;		    
		    while(it.hasNext()){
		        obj = it.next();
		        //obj = ((TipoFuncAcompTpfaPermiteAlterar) obj2).getComp_id().getCod_inferior_tpfapa();
		        if( (objetosExcluidos == null) || ( objetosExcluidos != null && !objetosExcluidos.contains(obj) ) ){		            
				        s.append("<input type=\"checkbox\" class=\"form_check_radio\" name=\"").append(getNome()).append("\" value=\"");
				        /** invoca o Método getXXXX para recuperar o valor do atributo value **/
				        Object value = Class.forName(getObjeto()).getMethod("get"+ Util.primeiraLetraToUpperCase(getValue()), null).invoke(obj, null);
				        s.append(value);		        
				        s.append("\"");
				        
				        if(objetoAlterado != null){
					        if(permiteAlterar(((TipoFuncAcompTpfa)objetoAlterado), (Long)value)){
					        	s.append(" checked ");
					        }
				        }
				        /*if("S".equals(((TipoFuncAcompTpfaPermiteAlterar) obj2).getPermiteAlterarParecer())){
				        	s.append(" checked ");
				        }*/
				        /*if(getSelected()!= null){
				            Iterator itValue = getSelected().iterator();
				            while(itValue.hasNext()){
				                if( (itValue.next().toString()).equals(value.toString())){
				                    s.append(" checked ");        
				                }
				            }
				        }*/
				        if(scripts!= null && !"".equals(scripts))
				            s.append(" ").append(scripts);
				        s.append(">");
				        if (!this.isMultiLabel())
	                    {
				        	/** invoca o Método getXXXX para recuperar o valor do atributo label **/
				        	s.append(Class.forName(getObjeto()).getMethod("get"+ Util.primeiraLetraToUpperCase(getLabel()), null).invoke(obj, null));
	                    }
				        else
	                    {
	                    	Iterator itLabels = this.getLabels().iterator();
	                    	while (itLabels.hasNext())
	                    	{
					        	s.append(Class.forName(getObjeto()).getMethod("get"+ Util.primeiraLetraToUpperCase((String)itLabels.next()), null).invoke(obj, null));
	                        	s.append(" - ");
	                    	}
	                    	s.deleteCharAt(s.length()-2);                    	
	                    }
				        s.append("<br>");

				       /* Iterator itFilhos = colecao.iterator();
				        while(itFilhos.hasNext()){*/
					        List lista = new ArrayList();
					        lista.addAll((PersistentSet) Class.forName(getObjeto()).getMethod("getTipoFuncAcompTpfas").invoke(obj, null));
					        nivel = 0;
					        primeiroNivel = (Long) Class.forName(getObjeto()).getMethod("getCodTpfa").invoke(obj, null);
					        if(lista.size() > 0){
					        	nivel++;
				        		imprimirFilhos(lista, writer, s);
					        }
				        //}
				       
				        		           
		        }
		    }		    
		    writer.print(s.toString());
		} catch (Exception e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
	    	try{
	    	    writer.print("Erro na geração da CheckList: " + e.getMessage());
	        	logger.error(e);
	    	}catch(IOException ioE){
	        	logger.error(ioE);
	    	}   	
		}
		return Tag.EVAL_BODY_INCLUDE;
	}
	
        /**
         *
         * @return
         */
        public Object getObjetoAlterado() {
		return objetoAlterado;
	}

        /**
         *
         * @param objetoAlterado
         */
        public void setObjetoAlterado(Object objetoAlterado) {
		this.objetoAlterado = objetoAlterado;
	}

        /**
         *
         * @param objetoAlt
         * @param value
         * @return
         */
        public boolean permiteAlterar(TipoFuncAcompTpfa objetoAlt, Long value){
		boolean retorno = false;
		boolean encontrouTPFA = false;
		
		if(objetoAlt.getTipoFuncAcompTpfasPermiteAlterarInferior() != null && objetoAlt.getTipoFuncAcompTpfasPermiteAlterarInferior().size() > 0){
			for (Object obj : objetoAlt.getTipoFuncAcompTpfasPermiteAlterarInferior()) {
				TipoFuncAcompTpfaPermiteAlterar tipoFAPA = (TipoFuncAcompTpfaPermiteAlterar) obj;
				if(tipoFAPA.getComp_id().getCod_inferior_tpfapa().getCodTpfa().longValue() == value.longValue()){
					encontrouTPFA = true;
					if("S".equalsIgnoreCase(tipoFAPA.getPermiteAlterarParecer())){
						retorno = true;
					}				
				}
			}
		}
		
		if(!encontrouTPFA){
			TipoFuncAcompTpfa tpFASuperior = new TipoFuncAcompTpfa();
			tpFASuperior.setCodTpfa(objetoAlt.getCodTpfa());
			TipoFuncAcompTpfa tpFAInferior = new TipoFuncAcompTpfa();
			tpFAInferior.setCodTpfa(value);
			TipoFuncAcompTpfaPermiteAlterarPK tpFAPK = new TipoFuncAcompTpfaPermiteAlterarPK();
			tpFAPK.setCod_inferior_tpfapa(tpFAInferior);
			tpFAPK.setCod_superior_tpfapa(tpFASuperior);
			TipoFuncAcompTpfaPermiteAlterar tpFAPA = new TipoFuncAcompTpfaPermiteAlterar();
			tpFAPA.setComp_id(tpFAPK);
			tpFAPA.setPermiteAlterarParecer("N");
			if(objetoAlt.getTipoFuncAcompTpfasPermiteAlterarInferior() == null){
				objetoAlt.setTipoFuncAcompTpfasPermiteAlterarInferior(new HashSet());				
			}
			objetoAlt.getTipoFuncAcompTpfasPermiteAlterarInferior().add(tpFAPA);
		}
		
		return retorno;		
	}
	
        /**
         *
         * @param col
         * @param writer
         * @param s
         * @throws IllegalArgumentException
         * @throws SecurityException
         * @throws IllegalAccessException
         * @throws InvocationTargetException
         * @throws NoSuchMethodException
         * @throws ClassNotFoundException
         * @throws IOException
         */
        public void imprimirFilhos(Collection col, JspWriter writer, StringBuffer s) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, IOException{

		Object obj = null;
		Object obj2 = null;
	    Iterator it = col.iterator();
	     
	    while(it.hasNext()){
	        obj = it.next();
	        //obj = ((TipoFuncAcompTpfaPermiteAlterar) obj2).getComp_id().getCod_inferior_tpfapa();
	        if( (objetosExcluidos == null) || ( objetosExcluidos != null && !objetosExcluidos.contains(obj) ) ){		            
			        for(int i = 0; i <  nivel; i++){
			        	s.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			        }
	        		s.append("<input type=\"checkbox\" class=\"form_check_radio\" name=\"").append(getNome()).append("\" value=\"");
			        /** invoca o Método getXXXX para recuperar o valor do atributo value **/
			        Object value = Class.forName(getObjeto()).getMethod("get"+ Util.primeiraLetraToUpperCase(getValue()), null).invoke(obj, null);
			        s.append(value);
			        
			        TipoFuncAcompTpfa pai = ((TipoFuncAcompTpfa) Class.forName(getObjeto()).getMethod("getTipoFuncAcompTpfa").invoke(obj, null));			        
			        while(pai != null){
			        	s.append(",");
			        	Long codPai = pai.getCodTpfa();
			        	s.append(codPai);
			        	if(codPai.floatValue() == primeiroNivel){
			        		break;
			        	} else{
			        		pai = pai.getTipoFuncAcompTpfa();
			        	}
			        }
			        s.append("\"");
			        if(objetoAlterado != null){
				        if(permiteAlterar(((TipoFuncAcompTpfa)objetoAlterado), (Long)value)){
				        	s.append(" checked ");
				        }
			        }
			       /* if("S".equals(((TipoFuncAcompTpfaPermiteAlterar) obj2).getPermiteAlterarParecer())){
			        	s.append(" checked ");
			        }*/

			        /*if(getSelected()!= null){
			            Iterator itValue = getSelected().iterator();
			            while(itValue.hasNext()){
			                if( (itValue.next().toString()).equals(value.toString())){
			                    s.append(" checked ");        
			                }
			            }
			        }*/
			        if(scripts!= null && !"".equals(scripts))
			            s.append(" ").append(scripts);
			        s.append(">");
			        if (!this.isMultiLabel())
                    {

			        	/** invoca o Método getXXXX para recuperar o valor do atributo label **/
			        	s.append(Class.forName(getObjeto()).getMethod("get"+ Util.primeiraLetraToUpperCase(getLabel()), null).invoke(obj, null));
                    }
			        else
                    {
                    	Iterator itLabels = this.getLabels().iterator();
                    	while (itLabels.hasNext())
                    	{
				        	s.append(Class.forName(getObjeto()).getMethod("get"+ Util.primeiraLetraToUpperCase((String)itLabels.next()), null).invoke(obj, null));
                        	s.append(" - ");
                    	}
                    	s.deleteCharAt(s.length()-2);                    	
                    }
			        s.append("<br>");

			       /* Iterator itFilhos = colecao.iterator();
			        while(itFilhos.hasNext()){*/
				        List lista = new ArrayList();
				        lista.addAll((PersistentSet) Class.forName(getObjeto()).getMethod("getTipoFuncAcompTpfas").invoke(obj, null));
				        if(lista.size() > 0){
				        	nivel++;
				        	imprimirFilhos(lista, writer, s);
				        	nivel--;
				        }
			        //}
			       
			        		           
	        }
	    }		    
	  //  writer.print(s.toString());
	}

	/**
	 * Retorna List labels.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return List
	 */
    public List getLabels()
    {
        String labelAux = label;
        List labels = new ArrayList();
    	while(labelAux.lastIndexOf(",") != -1){
    		labels.add(labelAux.substring(0, labelAux.indexOf(",")));
    		labelAux = labelAux.substring(labelAux.indexOf(",") + 1);
        }      
    	labels.add(labelAux);
    	
    	return labels;
    }
    
    /**
     * Verifica se e MultiLabel.<br>
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
     * @return PageContext - (Returns the page).
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
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param filters
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
     * Atribui valor especificado para String order.<br>
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
     * Atribui valor especificado para String value.<br>
     * 
     * @param value
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    /**
     * Retorna Collection selected.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return Collection- (Returns the selected)
     */
    public Collection getSelected() {
        return selected;
    }
    
    /**
     * Atribui valor especificado para Collection selected.<br>
     * 
     * @param selected
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setSelected(Collection selected) {
        this.selected = selected;
    }
    
    /**
     * Retorna String scripts.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the scripts)s
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
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param colecao
	 */
	public void setColecao(Collection colecao) {
		this.colecao = colecao;
	}
}
