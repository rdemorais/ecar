/*
 * Criado em 08/12/2004
 *
 */
package ecar.taglib.util;

import java.awt.Image;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import comum.util.ConstantesECAR;
import comum.util.FileUpload;
import comum.util.Pagina;
import comum.util.Util;

import ecar.action.ActionSisAtributo;
import ecar.dao.ConfiguracaoDao;
import ecar.exception.ECARException;
import ecar.pojo.AtributoLivre;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/**
 * Taglib para gerar elementos de Formulário HTML.<br>
 * 
 * @author felipev
 */
public class Input implements Tag {
	
    /**
     * Label do Campo
     */
    private String label;

    /**
     * Inteiro que define o tipo de campo<br>
     *     COMBOBOX = 1<br>
     * 	   CHECKBOX = 2<br>
     * 	   LISTBOX = 3<br>
     * 	   RADIO_BUTTON = 4<br>
     * 	   TEXT = 5<br>
     *	   IMAGEM = 6<br>
     *	   MULTITEXTO = 7<br>
     *	   VALIDACAO = 8<br>
     */
    private int tipo;

    /**
     * Define se o campo é obrigatório.<br>
     * "S" Campo Obrigatório<br>
     * "N" Campo Opcional<br>
     */
    private String obrigatorio;
    
    /**
     * Label para ser utilizada nos campos Obrigatorios.<br>
     */
    private String labelObrigatorio;
    
    /**
     * Define se o campo deve se apresentar desabilitado<br>
     * "S" Campo Desabilitado<br>
     * "N" Campo Editável<br>
     */
    private String disabled;

    /**
     * Label do Campo<br>
     */
    private String nome;

    /**
     * Tamanho do campo (para o caso de um campo de Texto)<br>
     */
    private String size;

    /**
     * Classe CSS para utilizar no label
     */
    private String classLabel;
    
    /**
     * Atributo que está sendo mostrado
     */
    
    private SisAtributoSatb sisAtributo;
    
    
    /**
     * Grupo de Atributo que está sendo mostrado (OPCIONAL)
     */
    private SisGrupoAtributoSga sisGrupoAtributoSga;
    
    /**
     * Diretorio raiz da página que solicitou o Input
     */
    
    private String pathRaiz;
    
    private String classInput;

    private Collection selecionados;
    
    private AtributoLivre atribLivre;

    private PageContext page;
    
    private String dica;    
    
    private String contexto;
    
    /**
     * Guarda de que parte do sistema está vindo o atributo: demandas, usuário, local, entidade, cadastro ou pontos críticos
     */
    private String origem;


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
    public static final int MULTIPLO = 9;
    /**
     *
     */
    public static final int TEXTAREA = 10;

    /**
     *
     */
    public static final long MAXLENGTH = 200;
    /**
     *
     */
    public static final long MAXLENGTH_PADRAO = 80;
    
    /**
    *
    */
    public static final long MAXLENGTH_REDUZIDO = 40;
    
    private JspWriter writerParametro = null; 
    
    private boolean transformarComboBoxListaChecks = false;
    
    /**
     * Atributo que define se as opções do nível de planejamento é para ser checkbox (false = DEFAULT)
     * Utilizado no cadastro de usuários
     */
    private Boolean nivelPlanejamentoCheckBox = new Boolean(false);

	
	private Boolean telaFiltro = new Boolean(false);
	
	private HttpServletRequest request;
	
	
	/**
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public Input() {
    }
    
    /**
     * Construtor.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param writer
     */
    public Input(JspWriter writer) {
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
            ConfiguracaoCfg configuracao = new ConfiguracaoDao(null).getConfiguracao();            
        
//        	if (atribLivre == null) {
        		atribLivre = buscaAtributo(sisAtributo);
//        	}
        	
        	if(getDica() != null)
        		s.append("<TR><TD valign=\"top\" title=\"" + getDica()+ "\"");
        	else
        		s.append("<TR><TD valign=\"top\"");
            
        	if (getClassLabel() != null)
                s.append(" class=\"").append(getClassLabel()).append("\"");
            s.append(">");
            
            if ("S".equals(getObrigatorio())){
                s.append(getLabelObrigatorio());
            }          
            
            if(getTipo() == MULTITEXTO)
            {            	            	
            	s.append(getLabel());
            	
            	s.append("</TD>");
            	s.append("<TD valign=\"top\">");
            	
            	//Dica colocada antes do primeiro item multitexto

            	if( dica != null && !"".equals(dica) ){
                	if (contexto != null){
                		s.append(Util.getTagDica(nome, contexto, dica));
                	}
                }
            	
            	s.append("</TD>\n</TR>\n<TR>");
            	setClassLabel("labelNormal");
            }
            else if (getTipo() == IMAGEM){
            	
            	s.append(getLabel());
            	
            	s.append("</TD>");
            	s.append("<TD valign=\"top\">"); 	
            	
            	if( dica != null && !"".equals(dica) ){
                	if (contexto != null){
                		s.append(Util.getTagDica(nome, contexto, dica));
                	}
                }
            }
            else{
            	s.append(getLabel()).append("</TD><TD");  
            	if (getClassInput() != null)
            		s.append(" class=\"").append(getClassInput()).append("\"");
            	
            	s.append(">");
            }
                        

            if(getNivelPlanejamentoCheckBox().booleanValue()
            		&& this.getSisGrupoAtributoSga() != null
            		&& configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan() != null 
            		&& this.getSisGrupoAtributoSga().equals(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan())
            		&& tipo != CHECKBOX) {
            	tipo = CHECKBOX;
            }
            
            switch (tipo) {
            	case COMBOBOX:
            		if (!transformarComboBoxListaChecks)
            			s.append(addInputComboBox());
            		break;                
            	case LISTBOX:
            		s.append(addInputListBox());
            		break;                
            	case TEXT:
            		if(getSize() == null || Integer.parseInt(getSize()) <= ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT){
	            		s.append(addInputText());
	            		break;
            		} else{
            			s.append(addInputTextArea());    	               	
                		break;
            		}
            	case VALIDACAO:
            		s.append(addInputValidacao());
            		break;
            	case IMAGEM:
           			s.append(addInputImagem());
            		break;
            	case MULTIPLO:          	
            		s.append(addInputMultiAtributo());    	               	
            		break;
            	case TEXTAREA:          	
            		s.append(addInputTextArea());    	               	
            		break;
            }
            writer.print(s.toString());
        } catch (Exception e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
        return Tag.EVAL_BODY_INCLUDE;
    }
  
    /**
     * Método para criar botões. Criado na refatoração da criação de campos do tipo imagem
     * em que havia repetição de código.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param nome
     * @param valor
     * @param params
     * @return StringBuffer
     */
    public StringBuffer criaBotao(String nome, String valor, String params)
    {
    	StringBuffer s = new StringBuffer();
   		s.append("&nbsp;<input type=button class=\"botao\" name = \"").append(nome).append("\" value=\"").append(valor).append("\" ")
   		 .append("onClick=\"javascript:abrirPopUpUpload(").append(params).append(")\"");
       		if (getDisabled() != null && "S".equals(getDisabled()))
    	   		s.append(" disabled ");                    			
       		s.append(">\n");   
       		return s;

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
     * Encerra Tag.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 * @throws JspException
     */
    public int doEndTag() throws JspException {
        JspWriter writer = null;
        if(writerParametro != null) {
    		writer = writerParametro;
    	} else {
        	writer = this.page.getOut();
    	}
        StringBuffer s = new StringBuffer();
        try {
            switch (tipo) {
            case COMBOBOX:
                s.append("</select>");
                break;
            case LISTBOX:
                s.append("</select>");                
		        s.append("&nbsp;<input type=\"button\" name=\"buttonLimpar\" value=\"Limpar\" class=\"botao\" ")
		        .append("onclick=\"limparListBox(document.getElementById('" + nome + "'));\"");
		        if (getDisabled() != null && "S".equals(getDisabled()))
	    	   		s.append(" disabled ");
		        s.append(">");
                break;            
            case RADIO_BUTTON:            	
            	break;
            }
            if( dica != null && !"".equals(dica) ){
            	if (contexto != null && tipo!= CHECKBOX && tipo != RADIO_BUTTON && tipo != MULTITEXTO && tipo != IMAGEM){
            		s.append(Util.getTagDica(nome, contexto, dica));
            	}
            }
            s.append("</TD>");
            s.append("</TR>");
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
        return Tag.EVAL_PAGE;
    }
    
  
    /**
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void release() {
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
     * Retorna String disabled.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the disabled)
     */
    public String getDisabled() {
        return disabled;
    }

    /**
     * Atribui valor especificado para String disabled.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param disabled
     */
    public void setDisabled(String disabled) {
        this.disabled = disabled;
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
     * Retorna String obrigatorio.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the obrigatorio)
     */
    public String getObrigatorio() {
        return obrigatorio;
    }

    /**
     * Atribui valor especificado para String obrigatorio.<br>
     * 
     * @param obrigatorio
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setObrigatorio(String obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    /**
     * Retorna Collection selecionados.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return Collection - (Returns the selecionados)
     */
    public Collection getSelecionados() {
        return selecionados;
    }

    /**
     * Atribui valor especificado para Collection selecionados.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param selecionados
     */
    public void setSelecionados(Collection selecionados) {
        this.selecionados = selecionados;
    }

    /**
     * Retorna int tipo.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return int - (Returinputns the tipo)
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Atribui valor especificado para int tipo.<br>
     * 
     * @param tipo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * Retorna String classInput.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the classInput)
     */
    public String getClassInput() {
        return classInput;
    }

    /**
     * Atribui valor especificado para String classInput.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param classInput
     */
    public void setClassInput(String classInput) {
        this.classInput = classInput;
    }

    /**
     * Retorna String classLabel.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the classLabel)
     */
    public String getClassLabel() {
        return classLabel;
    }

    /**
     * Atribui valor especificado para String classLabel.<br>
     * 
     * @param classLabel
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setClassLabel(String classLabel) {
        this.classLabel = classLabel;
    }

    /**
     * Retorna String size.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the size)
     */
    public String getSize() {
        return size;
    }

    /**
     * Atribui valor especificado para String size.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param size
     */
    public void setSize(String size) {
        this.size = size;
    }
    
    /**
     * Retorna String labelObrigatorio.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the labelObrigatorio)
     */
    public String getLabelObrigatorio() {
        return labelObrigatorio;
    }
    
    /**
     * Atribui valor especificado para String labelObrigatorio.<br>
     * 
     * @param labelObrigatorio
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setLabelObrigatorio(String labelObrigatorio) {
        this.labelObrigatorio = labelObrigatorio;
    }
    
    /**
     * Retorna SisAtributoSatb sisAtributo.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return SisAtributoSatb
     */
    public SisAtributoSatb getSisAtributo() {
        return sisAtributo;
    }

    /**
     * Atribui valor especificado para SisAtributoSatb sisAtributo.<br>
     * 
     * @param sisAtributo
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setSisAtributo(SisAtributoSatb sisAtributo) {
        this.sisAtributo = sisAtributo;
    }

    /**
     * Retorna AtributoLivre atribLivre.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return AtributoLivre
     */
    public AtributoLivre getAtribLivre() {
        return atribLivre;
    }

    /**
     * Atribui valor especificado para AtributoLivre atribLivre.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atribLivre
     */
    public void setAtribLivre(AtributoLivre atribLivre) {
        this.atribLivre = atribLivre;
    }

    /**
     * Retorna String pathRaiz.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String
     */
    public String getPathRaiz() {
        return pathRaiz;
    }

    /**
     * Atribui valor especificado para String pathRaiz.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param pathRaiz
     */
    public void setPathRaiz(String pathRaiz) {
        this.pathRaiz = pathRaiz;
    }   
    
    /**
     * Retorna String dica.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String
     */
    public String getDica() {
		return dica;
	}

    /**
     * Atribui valor especificado para String dica.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param dica
     */
	public void setDica(String dica) {
		this.dica = dica;
	}

	/**
	 * Atributo Selecionado.<br>
	 * 
         * @param sisAtributo
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return boolean
	 */
	public boolean atributoSelecionado(SisAtributoSatb sisAtributo)
    {
    	Iterator itSisAtb = getSelecionados().iterator();
    	while(itSisAtb.hasNext())
    	{
    		if(sisAtributo.equals(((AtributoLivre)itSisAtb.next()).getSisAtributoSatb()))
    			return true;
    	}
    	return false;
    		
    }

	/**
	 * Busca atributo.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param sisAtb
	 * @return Atributo livre
	 */
    public AtributoLivre buscaAtributo (SisAtributoSatb sisAtb)
    {
    	AtributoLivre atribLivre = new AtributoLivre();
    	Iterator itAtb = getSelecionados().iterator();
    	while (itAtb.hasNext())
    	{
    		atribLivre = (AtributoLivre)itAtb.next();
    		if(sisAtb.equals(atribLivre.getSisAtributoSatb()))
    			return atribLivre;
    	}
    	return null;
    }
    
    
    /**
     * Adiciona Input Imagem.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return StringBuffer
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     * @throws ECARException 
     */
    public StringBuffer addInputImagem() throws UnsupportedEncodingException, NoSuchAlgorithmException, ECARException
    {            	
        StringBuffer s = new StringBuffer();
        boolean ehImagem = false;
                
        
        if (atribLivre != null && !atribLivre.equals(null))
        {
        	String arquivo = atribLivre.getInformacao();
           	if (arquivo!=null && !("".equals(arquivo.trim())))
           	{
           		String nomeArquivo = "";
           		String width = "";
           		String heigth = "";
           	    if(arquivo.lastIndexOf("\\") != -1) {
           			nomeArquivo = arquivo.substring(arquivo.lastIndexOf("\\") + 1); 
           		} else 
           			if (arquivo.lastIndexOf("/") != -1)     
           	        	nomeArquivo = arquivo.substring(arquivo.lastIndexOf("/") + 1);
           	    
           	    //Modificado por José André Fernandes
           	    if (arquivo.lastIndexOf("=") != -1)     
           	    	arquivo = arquivo.substring(arquivo.lastIndexOf("=") + 1);
           	    
           	    
           	    //Modificado por Patricia Pessoa
           	    //Colocar um delimitador na imagem para aparecer no internet explorer
	 			//Seta width e heigth da imagem
	           	ImageIcon imagem = new ImageIcon(arquivo);
	           	Image image = new ImageIcon(arquivo).getImage();
	           	 
	           	if (((imagem.getIconHeight() < 0) && (imagem.getIconWidth() < 0))){
	           		ehImagem = true;
	           	}
	           	
	           	String raiz = new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getRaizUpload();
	           	//arquivo = arquivo.substring(raiz.length());
	           	
	        	UsuarioUsu usuario = null;
	        	String hashNomeArquivo = null;
	        	String hashNomeURLEncoder = null;
	        	
	        	usuario = ((ecar.login.SegurancaECAR)page.getSession().getAttribute("seguranca")).getUsuario();    
	        	
            	hashNomeArquivo = Util.calcularHashNomeArquivo(raiz, arquivo);
        		Util.adicionarMapArquivosAtuaisUsuarios(usuario, hashNomeArquivo, raiz, arquivo);
        		
        		hashNomeURLEncoder = Util.calcularHashNomeArquivo(raiz, URLEncoder.encode(arquivo,ConstantesECAR.ENCODE_PADRAO));
        		Util.adicionarMapArquivosAtuaisUsuarios(usuario, hashNomeURLEncoder, raiz, arquivo);
	           	
	           	String urlPath = pathRaiz +"/DownloadFile?RemoteFile=" +hashNomeArquivo;
	           	if (arquivo!=null && arquivo.length()>0) {
	           		arquivo = pathRaiz +"/DownloadFile?RemoteFile=" + hashNomeURLEncoder;
	           	}
		         
           	    
           	    double intImgWidth;			
	 			double intImgHeight;
	 			double intPadrWidth = 330;
	 			double intPadrHeight = 250;
	 			double intPropImg = imagem.getIconWidth()/imagem.getIconHeight();
	 			double intPropPadr = intPadrWidth/intPadrHeight;
	 			
	 			if (image.getWidth(null) > intPadrWidth && intPropImg >= intPropPadr)
	 			{
	 				intImgWidth = intPadrWidth;
	 				intImgHeight = imagem.getIconHeight() * intPadrWidth/imagem.getIconWidth();
	 			}
	 			else if(image.getHeight(null) > intPadrHeight && intPropImg < intPropPadr){
	 				intImgWidth = imagem.getIconWidth() * intPadrHeight/imagem.getIconHeight();
	 				intImgHeight = intPadrHeight;
	 			}
	 			else
	 			{
	 				intImgWidth = imagem.getIconWidth();
	 				intImgHeight = imagem.getIconHeight();
	 			}
	 			
	 			width = String.valueOf(intImgWidth);
	 			heigth = String.valueOf(intImgHeight);
	 			 
	 			// colocar um campo hidden e testar
//	 			if (arquivo==null || arquivo.trim().length()==0)
//	 				arquivo = pathRaiz + "/images/ImagemIndisponivel.gif";
	 			
	 			s.append("<input type=\"hidden\" name=\"hidImagem").append(getNome()).append("\" value=\"").append(arquivo).append("\"><br>");
	 			
        		s.append("<input type=\"text\" name=\"").append(getNome()).append("\" value=\"")
        		 .append(arquivo).append("\" style=\"display:none\" >");
           		
           		//s.append("<br><div id=\"nomeArquivo").append(getNome()).append("\"> "+ nomeArquivo+ "</div>");
           		s.append("<br><img name=\"imagem").append(getNome()).append("\" src=\"").append(urlPath).append("\" width=\"")
           		 .append(width).append("\" heigth=\"").append(heigth).append("\" ");
            	if (getDisabled() != null && "S".equals(getDisabled()))
                    s.append(" disabled ");
        		s.append("><br>\n");
        		s.append("<div id=\"link").append(getNome()).append("\">");
        		s.append("<a href=\"").append(urlPath).append("\">").append(FileUpload.getNomeArquivoOriginal(nomeArquivo)).append("</a></div>");
        		s.append("<br>\n");
        		s.append(criaBotao("b1"+getNome(), "Alterar " + getLabel(), "'" + getNome() + "', '"+ getLabel()+ "'"));
           		s.append(criaBotao("b2"+getNome(), "Excluir " + getLabel(), "'" + getNome() + "', '"+ getLabel()+ "', '" + arquivo + "'"));
           		s.append("<br>\n");
           		s.append("<br>\n");
           		
	       		s.append("<div style=\"color:#FF0000\" id=\"msg" + getNome() + "\">" + "</div>");
//	       		s.append("<br>\n");
	       		
           		
        		return s;
        	}
        	
        	
        }
   		String arquivo = pathRaiz + "/images/ImagemIndisponivel.gif";
        s.append("<input type=\"text\" name=\"").append(getNome()).append("\" value=\"\"style=\"display:none\">");
        s.append("<input type=\"hidden\" name=\"hidImagem").append(getNome()).append("\" value=\"\"><br>");
                        	
       	s.append("<br><img name=\"imagem").append(getNome()).append("\" src=\"").append(arquivo).append("\" width=\"\" heigth=\"\"").append(" style=\"display:none\"");
        if (getDisabled() != null && "S".equals(getDisabled()))
   				s.append(" disabled ");
       	s.append(">");
		s.append("<div id=\"link").append(getNome()).append("\"> </div>");
		s.append("<br>\n");
		s.append(criaBotao("b1"+getNome(), "Inserir " + getLabel(), "'" + getNome() + "', '"+ getLabel()+ "'"));
		s.append("<br>\n");
   		s.append("<br>\n");
   		
   		s.append("<div style=\"color:#FF0000\" id=\"msg" + getNome() + "\">" + "</div>");
 //  		s.append("<br>\n");
   		
		return s;
		
    }
    
    /**
     *
     * @return
     * @throws ECARException
     */
    public StringBuffer addInputValidacao() throws ECARException {
    	if (atribLivre == null || atribLivre.equals(null)){ 
        	atribLivre = new AtributoLivre();
        	atribLivre.setSisAtributoSatb(sisAtributo);
        }
    	if (telaFiltro.booleanValue() && 
    		atribLivre != null && 
    		!atribLivre.equals(null) && 
    		atribLivre.getSisAtributoSatb() != null && 
    		atribLivre.getSisAtributoSatb().getAtribInfCompSatb() != null){
    		
    		if (atribLivre.getSisAtributoSatb().getAtribInfCompSatb().equals("dataScript") || 
    			atribLivre.getSisAtributoSatb().getAtribInfCompSatb().equals("numeroInteiroScript") ||
    			atribLivre.getSisAtributoSatb().getAtribInfCompSatb().equals("numeroRealScript") ||
    			atribLivre.getSisAtributoSatb().getAtribInfCompSatb().equals("valorMonetarioScript") ){
    			
    			return addInputValidacaoCampo("_Inicio").append(" a ").append(addInputValidacaoCampo("_Fim"));
    			
    		} else {
    			return addInputValidacaoCampo("");
    		}
    	} else {
    		return addInputValidacaoCampo("");
    	}
    }
    
    /**
     * Adiciona Input validação.<br>
     * 
     * @param nomeCampo
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return StringBuffer
     * @throws ECARException 
     * 
     */
    public StringBuffer addInputValidacaoCampo(String nomeCampo) throws ECARException {   
    	StringBuffer s = new StringBuffer();
    	ActionSisAtributo action = new ActionSisAtributo(); 
    	
    	String hidden = "";
    	
    	if (atribLivre.getSisAtributoSatb().isAtivo()) {
	    	s.append("<input type=\"text\" name=\"").append(getNome() + nomeCampo).append("\"");
	        s.append(" id=\"").append(getNome() + nomeCampo).append("\" ");
	        
	        hidden = "<input type=\"hidden\" name=\"hidden_" + getNome() + nomeCampo + "\"";
	    	
	        if (getSize() != null) {
	            s.append(" size=\"").append(getSize()).append("\" ");
	        }
	        
	        String opcaoScript = atribLivre.getSisAtributoSatb().getAtribInfCompSatb();
	        
	        if (atribLivre.getSisAtributoSatb().isAtributoAutoIcremental()) {
	        	s.append(" readOnly=\"true\" value=\""+Pagina.trocaNull(atribLivre.getInformacao())+"\" />");
	        	hidden += " value=\""+Pagina.trocaNull(atribLivre.getInformacao())+"\" />";
	        } else if (atribLivre.getSisAtributoSatb().isAtributoMascara()) {
	        	s.append(" readOnly=\"true\" value=\"");
	        	if (atribLivre.getInformacao() != null){
	        		s.append(Pagina.trocaNull(atribLivre.getInformacao())+"\" />");
	        		hidden += " value=\""+Pagina.trocaNull(atribLivre.getInformacao())+"\" />";
	        	} else {
	        		s.append(action.geraConteudo(atribLivre.getSisAtributoSatb().getMascara())+"\" />");
	        		hidden += " value=\""+action.geraConteudo(atribLivre.getSisAtributoSatb().getMascara())+"\" />";
	        	}
	        } else {
		        if (getDisabled() != null && "S".equals(getDisabled()))
		            s.append(" disabled ");
		                
		        if (atribLivre == null || atribLivre.equals(null)){ 
		        	atribLivre = new AtributoLivre();
		        	atribLivre.setSisAtributoSatb(sisAtributo);
		        }
		        
		        String scriptValidacao="";
		        if (atribLivre != null){
		        	if (atribLivre.getSisAtributoSatb().isAtributoMascaraEditavel()) {
		        		
		            	if (atribLivre.getInformacao() != null){
		            		s.append(" value=\""+Pagina.trocaNull(atribLivre.getInformacao())+"\" readOnly=\"true\" ");
		            		hidden += " value=\""+Pagina.trocaNull(atribLivre.getInformacao())+"\" />";
		            	} else {
		            		s.append(" value=\""+action.geraConteudo(atribLivre.getSisAtributoSatb().getMascara())+"\" ");
		            		hidden += " value=\""+action.geraConteudo(atribLivre.getSisAtributoSatb().getMascara())+"\" />";
		            	}
		        		
		        		s.append(" maxlength=\"").append(MAXLENGTH_REDUZIDO).append("\" ");
		        		
		        	} else {
		        	
		        		s.append(" value=\"").append(Pagina.trocaNull(atribLivre.getInformacao())).append("\"");
		        		hidden += " value=\""+Pagina.trocaNull(atribLivre.getInformacao())+"\" />";
		       		
			        	if (opcaoScript != null){       			
			       			if (opcaoScript.equals("dataScript")) {
			           			scriptValidacao = "validaData(" +
									"document.getElementById('" +
									//"a" + atribLivre.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga() + nomeCampo +
									getNome() + nomeCampo +
									"')," +						
									"true,true,false)";
			           			s.append(" maxlength=\"").append("10").append("\" ");
			           			s.append(" onkeyup=\"").append("mascaraData(event, this);").append("\"");
			           			
							} else if (opcaoScript.equals("numeroInteiroScript")) {
				           		scriptValidacao = "isIntegerValid(" +
									"document.getElementById('" +
									//"a" + atribLivre.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga() + nomeCampo +
									getNome() + nomeCampo +
									"')," +
									"'"+atribLivre.getSisAtributoSatb().getSisGrupoAtributoSga().getDescricaoSga()+"'"+
									")";
				           		s.append(" maxlength=\"");
				           		if(getSize() != null && Integer.parseInt(getSize()) > 0){
				           			s.append(getSize());
				           		} else {
				           			s.append(MAXLENGTH_PADRAO);
				           		}
				           		s.append("\" ");
							} else if (opcaoScript.equals("numeroRealScript")) {
				           		scriptValidacao = "isNumericValid(" +
									"document.getElementById('" +
									//"a" + atribLivre.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga() + nomeCampo +
									getNome() + nomeCampo +
									"')," +
									"'"+atribLivre.getSisAtributoSatb().getSisGrupoAtributoSga().getDescricaoSga()+"'"+
									")";
				           		s.append(" maxlength=\"");
				           		if(getSize() != null && Integer.parseInt(getSize()) > 0){
				           			s.append(getSize());
				           		} else {
				           			s.append(MAXLENGTH_PADRAO);
				           		}
				           		s.append("\" ");
							} else if (opcaoScript.equals("valorMonetarioScript")) {
				           		scriptValidacao = "isMoedaValid(" +
				           			"document.getElementById('" +
				           			//"a" + atribLivre.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga() + nomeCampo +
				           			getNome() + nomeCampo +
				           			"')," +
				           			"'"+atribLivre.getSisAtributoSatb().getSisGrupoAtributoSga().getDescricaoSga()+"'"+
				           			")";															
				           		if(getSize() != null && Integer.parseInt(getSize()) > 0){
				           			s.append(" onKeyPress=\"").append("return mascaraMoeda(this,'.',',',event," +
				           					(Integer.parseInt(getSize()) + 1) +");").append("\"");
				           		} else {
				           			s.append(" onKeyPress=\"").append("return mascaraMoeda(this,'.',',',event," +
				           					(MAXLENGTH_PADRAO + 1) +");").append("\"");
				           		}
				           	
				           		s.append(" maxlength=\"");
				           		if(getSize() != null && Integer.parseInt(getSize()) > 0){
				           			s.append(getSize());
				           		} else {
				           			s.append(MAXLENGTH_PADRAO);
				           		}
				           		s.append("\" ");					
							} else if (opcaoScript.equals("cpfScript")) {
					           	scriptValidacao = "isCpf(" +
				           			"document.getElementById('" +
				           			//"a" + atribLivre.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga() + nomeCampo +
				           			getNome() + nomeCampo +
				           			"')," +
				           			"'"+atribLivre.getSisAtributoSatb().getSisGrupoAtributoSga().getDescricaoSga()+"'"+
				           			")";															
				           		s.append(" onKeyPress=\"").append("MascaraCPF(" +
				           				"document.getElementById('" +
				           				//"a" + atribLivre.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga() + nomeCampo +
				           				getNome() + nomeCampo +
				           				"'),event);").append("\"");	           		
				           		s.append(" maxlength=\"14\"");	           			           							
							} else if (opcaoScript.equals("cnpjScript")) {
					           	scriptValidacao = "isCnpj(" +
				           			"document.getElementById('" +
				           			//"a" + atribLivre.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga() + nomeCampo +
				           			getNome() + nomeCampo +
				           			"')," +
				           			"'"+atribLivre.getSisAtributoSatb().getSisGrupoAtributoSga().getDescricaoSga()+"'"+
				           			")";															
					           	s.append(" onKeyPress=\"").append("MascaraCNPJ(" +
			           				"document.getElementById('" +
			           				//"a" + atribLivre.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga() + nomeCampo +
			           				getNome() + nomeCampo +
			           				"'),event);").append("\"");	           		
					           	s.append(" maxlength=\"18\"");	           			           							
							} else if (opcaoScript.equals("emailScript")) {
					           	scriptValidacao = "isEmail(" +
				           			"document.getElementById('" +
				           			//"a" + atribLivre.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga() + nomeCampo +
				           			getNome() + nomeCampo +
				           			"')," +
				           			"'"+atribLivre.getSisAtributoSatb().getSisGrupoAtributoSga().getDescricaoSga()+"'"+
				           			")";															
					           	s.append(" maxlength=\"");
				           		if(getSize() != null && Integer.parseInt(getSize()) > 0){
				           			s.append(getSize());
				           		} else {
				           			s.append(MAXLENGTH_PADRAO);
				           		}
				           		s.append("\" ");	           			           							
							}     			
			        	
				       		s.append(" onBlur=\"").append(scriptValidacao).append("\"");      			    			
			       		} else {
			         		s.append("\" onBlur=\"").append(atribLivre.toString()).append("\"");                		
			        	}
		        	}
		        }
		        
		        s.append(" />");
		        
		        //Cria botão de alteração para atributos ID editáveis.
	        	if (atribLivre.getSisAtributoSatb().isAtributoMascaraEditavel() &&	atribLivre.getInformacao() != null) {
	        		if (disabled == null) {
	        			s.append ("&nbsp;<input type=\"button\" name=\"atualizar_id_editavel\" value=\"Atualizar Valor\" class=\"botao\" onClick=\"javascript:habilitaCampoID('"+getNome() + nomeCampo+"')\"/> ");
	        		} else {
	        			s.append ("&nbsp;<input type=\"button\" name=\"atualizar_id_editavel\" value=\"Atualizar Valor\" class=\"botao\" disabled /> ");
	        		}
	        	}
		        
	       	} 
	//*********************
	        
	        if (!Dominios.SIM.equals(getDisabled()) && atribLivre != null && atribLivre.getSisAtributoSatb() != null) {
	        	opcaoScript = atribLivre.getSisAtributoSatb().getAtribInfCompSatb();
	        	if (opcaoScript.equals("dataScript")) {
	        		s.append("		<img style=\"cursor:pointer;top:4px;\" title=\"Selecione a data\" src=\""+pathRaiz+"/images/icone_calendar.gif\" onclick=\"open_calendar('data', document.getElementsByName('"+this.getNome()+nomeCampo+"')[0], '','"+pathRaiz+"/calendario/calendario.jsp');\" " + true + " " + true + ">");
	        	}
	        }
    	}
           
    	s.append(hidden);
        return s;
    }
    
    /**
     * Adiciona input ComboBox.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return StringBuffer
     */
    public StringBuffer addInputComboBox()
    {
        StringBuffer s = new StringBuffer();
        s.append("<select name=\"").append(getNome()).append("\"");
        if (getDisabled() != null && "S".equals(getDisabled()))
            s.append(" disabled ");
        s.append(">");
        return s;
    }
    
    /**
     * Adiciona uma input listBox.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return StringBuffer
     */
    public StringBuffer addInputListBox()
    {
        StringBuffer s = new StringBuffer();    	
        s.append("<select multiple size=5 id=\"").append(getNome()).append("\" name=\"").append(getNome()).append("\"");
        if (getDisabled() != null && "S".equals(getDisabled()))
            s.append(" disabled ");
        if (getSize() != null)
            s.append(" size=\"").append(getSize()).append("\" ");
        s.append(">");
        return s;
    }
    
    /**
     * Adiciona Input Text.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return StringBuffer
     */
    public StringBuffer addInputText() {

    	StringBuffer s = new StringBuffer();    	
        try {
	        s.append("<input type=\"text\" name=\"").append(getNome()).append("\"");
	
	        
	        if (getSize() == null) {
	        	setSize("80");
	        }
	        s.append(" size=\"");
	        s.append(getSize());
	        s.append("\" ");
	        s.append(" maxlength=\"");
	        s.append(getSize());
	        s.append("\" ");
	        if (getDisabled() != null && "S".equals(getDisabled())) {
	            s.append(" disabled ");
	        }
	        if (atribLivre != null) {
	        	s.append("value=\"");
	        	s.append(Pagina.trocaNull(atribLivre.getInformacao()));
	        	s.append("\"");
	        }
	        s.append(" />");
	        
	        s.append("<input type=\"hidden\" name=\"hidden_").append(getNome()).append("\"");
	        if (atribLivre != null) {
	        	s.append(" value=\"");
	        	s.append(Pagina.trocaNull(atribLivre.getInformacao()));
	        	s.append("\"");
	        }
	        s.append(">"); //  <br>");
	        
        } catch (Exception e) {
			e.printStackTrace();
		}
        return s;
    }
    
    /**
     * Adiciona input MultiAtributo.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return StringBuffer
     */
    public StringBuffer addInputMultiAtributo()
    {
        StringBuffer sb = new StringBuffer();    
    	sb.append(addScriptFuncoesMulti());
        sb.append("<input type=\"hidden\" id=\"cont");
    	sb.append(getNome());
    	sb.append("\" name=\"cont");
    	sb.append(getNome());
    	sb.append("\"  value=\"0\">");
        sb.append("\n</td>");
        sb.append("\n</tr>");
//      sb.append("\n<%;");
//      sb.append("\n}");
//      sb.append("\nif((!navega) || (");
//    	sb.append(getNome());
//    	sb.append(".size() > 0)) {");
//      sb.append("\n%>");
        sb.append("\n<tr>");
        sb.append("\n<td class=\"label\" colspan=\"2\">&nbsp;</td>");		
        sb.append("\n</tr> ");
        sb.append("\n<tr>");
        sb.append("\n<td class=\"label\"><span class=\"fontLabelEntidade\">");
    	sb.append(getLabel());
    	sb.append("</span></td>");
        sb.append("\n<td>");
    	if (!"S".equals(getDisabled()))
    	{
    		sb.append("\n<a href=\"#\" onClick=\"adiciona");
    		sb.append(getNome());
    		sb.append("('','')\"> Adicionar Novo");
    		sb.append(getLabel());
    		sb.append("</a>");
    	}
        sb.append("\n</td>");
        sb.append("\n</tr>");
        sb.append("\n<tr>");
        sb.append("\n<td id=\"");
    	sb.append(getNome());
    	sb.append("\" colspan=\"2\">"); 
        
        /*
        s.append("<input type=\"text\" name=\"" + getNome() + "\"");
        if (getSize() != null) {
            s.append(" size=\"" + getSize() + "\" ");
            s.append(" maxlength=\"" + MAXLENGTH + "\" ");
        }
        if (getDisabled() != null && "S".equals(getDisabled()))
            s.append(" disabled ");
        if (atribLivre != null && !atribLivre.equals(null))
            s.append(" value=\""
                    + atribLivre.getInformacao() + "\" ");*/
        return sb;
    } 

    /**
     * Adiciona input TextArea.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return StringBuffer
     */
    public StringBuffer addInputTextArea() {		    	
    	StringBuffer s = new StringBuffer();    	
    	try {
    		s.append("<textarea name=\"").append(getNome()).append("\"");
    		s.append(" rows=\"4\" cols=\"60\" ");
    		
			String tam = "2000";
	        if(getSize() != null){
	        	tam = String.valueOf(getSize());
	        }
    		s.append("\" onkeyup=\"return validaTamanhoLimite(this, " + tam + ");\" ");
    		
    		if (getDisabled() != null && "S".equals(getDisabled())) {
    			s.append(" disabled ");        	
    		}
    		s.append(">");
    		if (atribLivre != null) {
    			s.append(Pagina.trocaNull(atribLivre.getInformacao()));
    		}
    		s.append("</textarea>");
    		
	        s.append("<input type=\"hidden\" name=\"hidden_").append(getNome()).append("\"");
	        if (atribLivre != null) {
	        	s.append(" value=\"");
	        	s.append(Pagina.trocaNull(atribLivre.getInformacao()));
	        	s.append("\">");
	        } else {
	        	s.append(" value=\"\">");
	        }
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return s;
    }
    
    /**
     * Adiciona Script MultiFunções.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return StringBuffer
     */
    public StringBuffer addScriptFuncoesMulti()
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append("<script>\n");
    	sb.append("var contador");
    	sb.append(getNome());
    	sb.append(" = 0;\n");
    	sb.append("var campo");
    	sb.append(getNome());
    	sb.append("  = new Array();\n");
    	sb.append("var id");
    	sb.append(getNome());
    	sb.append("  = new Array();\n");
    	sb.append("var add");
    	sb.append(getNome());
    	sb.append("  = new Array();\n");
    	sb.append("function setOutros");
    	sb.append(getNome());
    	sb.append("(n)\n");
    	sb.append("{\n");
    	sb.append("\tfor(i = 1; i <= n; i++)\n");
    	sb.append("\t\t{\n");
    	sb.append("\t\tif(document.getElementById(\"adiciona");
    	sb.append(getNome());
    	sb.append("\"+i).value == \"S\")\n");
    	sb.append("\t\t{\n");
    	sb.append("\t\t\tcampo");
    	sb.append(getNome());
    	sb.append("[i] = document.getElementById(\"campo");
    	sb.append(getNome());
    	sb.append(" \"+i).value;\n");
    	sb.append("\t\t\tid");
    	sb.append(getNome());
    	sb.append("[i] = document.getElementById(\"id");
    	sb.append(getNome());
    	sb.append("\"+i).value;\n");
    	sb.append("\t\t}\n");
    	sb.append("\tadd");
    	sb.append(getNome());
    	sb.append("[i] = document.getElementById(\"adiciona");
    	sb.append(getNome());
    	sb.append("\"+i).value;\n");
    	sb.append("\t}\n");
    	sb.append("\t}\n");
    	sb.append("\n\tfunction getOutros");
    	sb.append(getNome());
    	sb.append("(n)\n");
    	sb.append("\t{\n");
    	sb.append("\t\tfor(i = 1; i < n; i++)\n");
    	sb.append("\t\t{\n");
    	sb.append("\t\t\tif (add");
    	sb.append(getNome());
    	sb.append("[i] == \"S\")\n");
    	sb.append("\t\t\t{\n");
    	sb.append("\t\t\t\tdocument.getElementById(\"campo");
    	sb.append(getNome());
    	sb.append("\"+i).value = campo");
    	sb.append(getNome());
    	sb.append("[i];\n");
    	sb.append("\t\t\t\tdocument.getElementById(\"id");
    	sb.append(getNome());
    	sb.append("\"+i).value = id");
    	sb.append(getNome());
    	sb.append("[i];\n");
    	sb.append("\t\t\t}\n");
    	sb.append("\t\tdocument.getElementById(\"adiciona");
    	sb.append(getNome());
    	sb.append("\"+i).value = add");
    	sb.append(getNome());
    	sb.append("[i];\n");
    	sb.append("\t}\n");
    	sb.append("\t}\n");

    	sb.append("\n\tfunction adiciona");
    	sb.append(getNome());
    	sb.append("(campo, id) {\n");
    	sb.append("\t\tif ((id == null) || (id == \"\"))\n");
    	sb.append("\t\t\tid");
    	sb.append(getNome());
    	sb.append(" = prompt(\"Digite um identificador para o ");
    	sb.append(getLabel());
    	sb.append("\", \"\");\n");	
    	sb.append("\t\telse\n");
    	sb.append("\t\t\tid");
    	sb.append(getNome());
    	sb.append(" = id;\n");
    	sb.append("\t\tif ((id");
    	sb.append(getNome());
    	sb.append(" != null) && (id");
    	sb.append(getNome());
    	sb.append(" != \"\"))\n");
    	sb.append("\t\t{\n");	
    	sb.append("\t\t\tsetOutros");
    	sb.append(getNome());
    	sb.append("(contador");
    	sb.append(getNome());
    	sb.append(");\n");
    	sb.append("\t\t\tcontador");
    	sb.append(getNome());
    	sb.append(" += 1;\n");
    	sb.append("\t\t\tdivconteudo = \"<table id='");
    	sb.append(getNome());
    	sb.append("\"+contador");
    	sb.append(getNome());
    	sb.append("+\"'>\";\n");
    	sb.append("\t\t\tdivconteudo += \"<input type=\\\"hidden\\\" id=\\\"id");
    	sb.append(getNome());
    	sb.append("\"+contador");
    	sb.append(getNome());
    	sb.append("+\"\\\" name=\\\"id");
    	sb.append(getNome());
    	sb.append("\"+contador");
    	sb.append(getNome());
    	sb.append("+\"\\\" value=\"+ id");
    	sb.append(getNome());
    	sb.append(" +\" ");
    	if (getDisabled() != null && "S".equals(getDisabled()))
    	   		sb.append(" disabled ");
    	sb.append(" >\";\n");
    	sb.append("\t\t\tdivconteudo += \"<input type=\\\"hidden\\\" id=\\\"adiciona");
    	sb.append(getNome());
    	sb.append("\"+ contador");
    	sb.append(getNome());
    	sb.append(" +\"\\\" name=\\\"adiciona");
    	sb.append(getNome());
    	sb.append("\"+ contador");
    	sb.append(getNome());
    	sb.append(" +\"\\\" value=\\\"S\\\"");
    	if (getDisabled() != null && "S".equals(getDisabled()))
    	   		sb.append(" disabled ");
    	sb.append(" >\"	;\n");
    	sb.append("\t\t\tdivconteudo += \"\t<tr>\";\n");
    	sb.append("\t\t\tdivconteudo += \"\t\t<td class=\\\"labelNormal\\\">\"+id");
    	sb.append(getNome());
    	sb.append("+\"</td>\";\n");
    	sb.append("\t\t\tdivconteudo += \"\t\t<td><input type=\\\"text\\\" size=\\\"");
    	sb.append(getSize());
    	sb.append("\\\" maxlength=\\\"");
    	sb.append(getSize());
    	sb.append("\\\" id=\\\"campo");
    	sb.append(getNome());
    	sb.append("\" + contador");
    	sb.append(getNome());
    	sb.append(" + \"\\\" name=\\\"campo");
    	sb.append(getNome());
    	sb.append("\" + contador");
    	sb.append(getNome());
    	sb.append(" + \"\\\" value=\\\"\"+ campo +\"\\\" ");
    	if (getDisabled() != null && "S".equals(getDisabled()))
    	   		sb.append(" disabled ");
    	sb.append(" /> \";\n");
    	sb.append("\t\t\tdivconteudo += \"<a href=\\\"#\\\" onClick=\\\"remove");
    	sb.append(getNome());
    	sb.append("(\"+ contador");
    	sb.append(getNome());
    	sb.append(" +\")\\\">\";\n");
    	sb.append("\t\t\t\tif (\"");
    	if (getDisabled() != null && "S".equals(getDisabled()))
    	   		sb.append(" disabled ");
    	sb.append("\" == \"\")\n");
    	sb.append("\t\t\t\t\tdivconteudo +=\"Excluir\";\n");
    	sb.append("\t\t\t\t\tdivconteudo +=\"</a>\";\n");
    	sb.append("\t\t\t\t\tdivconteudo += \"\t</tr>\";\n");
    	sb.append("\t\t\t\t\tdivconteudo += \"<//table>\";\n");
    	sb.append("\t\t\t\t\tdocument.getElementById(\"");
    	sb.append(getNome());
    	sb.append("\").innerHTML += divconteudo;\n");
    	sb.append("\t\t\t\t\tdocument.form.cont");
    	sb.append(getNome());
    	sb.append(".value = contador");
    	sb.append(getNome());
    	sb.append(";\n");
    	sb.append("\t\t\t\t\tgetOutros");
    	sb.append(getNome());
    	sb.append("(contador");
    	sb.append(getNome());
    	sb.append(");\n");
    	sb.append("\t\t\t\t}\n");
    	sb.append("\t\t\t}\n");

    	sb.append("\tfunction remove");
    	sb.append(getNome());
    	sb.append("(n) {\n");
    	sb.append("\t\tif (confirm(\"O ");
    	sb.append(getLabel());
    	sb.append(" \"+n+\" será excluído. Confirma?\"))\n");
    	sb.append("\t\t\tdocument.getElementById(\"");
    	sb.append(getNome());
    	sb.append("\"+n).innerHTML = \"<input type=\\\"hidden\\\" id=\\\"adiciona");
    	sb.append(getNome());
    	sb.append("\" + n +\"\\\" name=\\\"adiciona");
    	sb.append(getNome());
    	sb.append("\" + n +\"\\\" value=\\\"N\\\">\";\n");
    	sb.append("\t\t}\n");
    	sb.append("</script>\n");
    	return (sb);
    }
    
    /**
     * Adiciona Scripts ExecMulti.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return StringBuffer
     */
    public StringBuffer addScriptsExecMulti()
    {
    	StringBuffer sb = new StringBuffer();
    	return(sb);
    }

    /**
     * Retorna SisGrupoAtributoSga sisGrupoAtributoSga.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return SisGrupoAtributoSga
     */
	public SisGrupoAtributoSga getSisGrupoAtributoSga() {
		return sisGrupoAtributoSga;
	}

	/**
	 * Atribui valor especificado para SisGrupoAtributoSga sisGrupoAtributoSga.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param sisGrupoAtributoSga
	 */
	public void setSisGrupoAtributoSga(SisGrupoAtributoSga sisGrupoAtributoSga) {
		this.sisGrupoAtributoSga = sisGrupoAtributoSga;
	}
    
        /**
         *
         * @return
         */
        public Boolean getNivelPlanejamentoCheckBox() {
		return nivelPlanejamentoCheckBox;
	}

    /**
     *
     * @param nivelPlanejamentoCheckBox
     */
    public void setNivelPlanejamentoCheckBox(Boolean nivelPlanejamentoCheckBox) {
		this.nivelPlanejamentoCheckBox = nivelPlanejamentoCheckBox;
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
        public String getOrigem() {
    		return origem;
    	}

        /**
         * 
         * @param origem
         */
    	public void setOrigem(String origem) {
    		this.origem = origem;
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
