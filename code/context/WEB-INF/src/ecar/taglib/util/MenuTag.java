/*
 * Created on 26/04/2005
 *
 */
package ecar.taglib.util;

import ecar.dao.ConfiguracaoDao;
import ecar.exception.ECARException;
import ecar.pojo.ConfiguracaoCfg;
import gov.pr.celepar.sentinela.comunicacao.SentinelaParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import comum.database.SentinelaUtil;
import comum.util.ItemMenu;

/**
 * @author garten
 *
 */
public class MenuTag extends TagSupport {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -8562932869631678975L;
	private JspWriter writer;
    private javax.servlet.http.HttpServletRequest request;
    
    /**
     * Inicializa a montagem da tag para ser adicionada na tela de HTML.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
     */
    @Override
	public int doStartTag() {
        writer = this.pageContext.getOut();
        
        try {
        	// foi necessário adicionar a linha abaixo para não perder informações do sentinela
	        SentinelaUtil sentinelaUtil = new SentinelaUtil(this.request);
	
		    //List menuVertical = (List)getRequest().getSession().getAttribute("menuVertical");
		    
		    //if(menuVertical == null) {
            @SuppressWarnings("static-access")
				SentinelaParam[] me = SentinelaUtil.getSentinelaInterface().getMenu();
				
				List menuVertical = ItemMenu.carregaMenu(me);
				
				getRequest().getSession().setAttribute("menuVertical", menuVertical);
		    //}
	
		    writer.println("\t var itemtext = new Array();");
	
		    // método que passa somente uma vez pelos itens principais do 
		    // menu e devolve um Array de Strings utilizadas na sequencia
		    String jsPartes[] = opcoesPrincipais(menuVertical);
		    getRequest().getSession().setAttribute("qtdeMenusPrincipais", String.valueOf(menuVertical.size()));
		    // montar início do js - itemtext[]
		    writer.println(jsPartes[0]);
		    writer.println("\n\n");
		    
		    // completar menu - submenu.add()
		    imprimeSubMenus(menuVertical);
		    writer.println("\n\n");
		    
		    // montar os divs
		    writer.println(jsPartes[1]);
		    writer.println("\n\n");
		    	    
		    // escrever as funções abre e fecha
		    writer.println(jsPartes[2]);
		    writer.println("\n\n");
		    
		    // fechamenu
		    writer.println(jsPartes[3]);	    
        }
        catch(Exception e) {
        	
        }
        
		/* nao processa o conteudo do corpo da tag, porque nao existe */
		return Tag.SKIP_BODY;
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
    @Override
	public int doEndTag() throws JspException {
	    /* processa o restante da página jsp */
		return Tag.EVAL_PAGE;
	}

	/**
	 * Exemplo de impressao da lista de menus.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param List lista
	 * @param int nivel
	 * @throws IOException
	 */
	/*
	 * Método não utilizado.
	 * private void imprimeLista(List lista, int nivel) throws IOException {
	    if (lista == null || lista.size() == 0)
	        return;
	    
	    Iterator it = lista.iterator();
	    while (it.hasNext()) {
	        ItemMenu m = (ItemMenu) it.next();
	        StringBuffer sb = new StringBuffer();
	        sb.append("<tr>");
	        sb.append("<td>" + nivel + "</td>");
	        sb.append("<td>" + m.getCodigo().toString() + "</td>");
	        sb.append("<td>" + m.getNome() + "</td>");
	        sb.append("<td>" + m.getUrl() + "</td>");
	        if (m.getItemMenuPai() != null)
	            sb.append("<td>" + m.getItemMenuPai().getCodigo().toString() + "</td>");
	        sb.append("</tr>");
            writer.println(sb.toString());
            imprimeLista(m.getItemMenuFilhos(), nivel+1);
	    }
	}*/
	
	/**
	 * Método que passa somente uma vez pelos itens principais do 
     * menu e devolve um Array de Strings utilizadas na sequencia
     * para a formação do js.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param List lista
	 * @return String[]
	 * @throws IOException
	 */
	private String[] opcoesPrincipais(List lista) throws IOException {
	    if (lista == null || lista.size() == 0)
	        return (new String[] {new String(""), new String(""), new String(""), new String("")});
	    
	    StringBuffer itemText = new StringBuffer();
	    StringBuffer divs = new StringBuffer();
	    StringBuffer fechaMenu = new StringBuffer();
	    StringBuffer funcoes = new StringBuffer();
	    try {
		    ConfiguracaoCfg configuracaoMenu = new ConfiguracaoDao(null).getConfiguracao();
		    
		    int nivel = 0, auxNivel = 0;
		    
		    Iterator it = lista.iterator();
		    while (it.hasNext()) {
		        ItemMenu m = (ItemMenu) it.next();
		        
		        auxNivel = nivel + 1;
		        
	            // montar início do js - itemtext[]
		        itemText.append("\t itemtext[").append(nivel).append("] = \"").append(m.getNome()).append("\"; \n");
	            
	            // montar os divs
		        divs.append("\t document.write(\"");
		        divs.append("<div id='item").append(auxNivel).append("'>");
		        divs.append("<a href='javascript:mudaFuncao(").append(auxNivel).append(");abremenu(").append(auxNivel).append(");'>");
		        divs.append("\"+itemtext[").append(nivel).append("]+\"");
		        divs.append("</a></div>\"); \n");
		        divs.append("\t document.write(\"");
		        divs.append("<div id='submenu").append(auxNivel).append("'>");
		        divs.append("\"+submenu").append(nivel).append("+\"</div>\"); \n");
		        
		        //fechamenu
		        fechaMenu.append("\t fechamenu(").append(auxNivel).append("); \n");
		        
	            nivel = nivel + 1;
		    }
		        
		    funcoes.append("\t function fechamenu(x) { \n");
		    funcoes.append("\t\t itemmenu = \"item\"+x; \n");
		    funcoes.append("\t\t itemmenutext = itemtext[x-1]; \n");
		    funcoes.append("\t\t divsubmenu = \"submenu\"+x; \n");
		    funcoes.append("\t\t document.getElementById(itemmenu).innerHTML = \"<a href='javascript:mudaFuncao(\" + x + \");abremenu(\"+x+\");'>\"+itemmenutext+\"</a>\"; \n");
		    funcoes.append("\t\t document.getElementById(itemmenu).style.backgroundImage = \"url(\"+_pathEcar+\"/images/estilo/icone_menu_mais_gd_"+configuracaoMenu.getEstilo().getNome()+".gif)\"; \n");
		    funcoes.append("\t\t divsubmenu = \"submenu\"+x; \n");
		    funcoes.append("\t\t document.getElementById(divsubmenu).style.visibility = \"hidden\"; \n");
		    funcoes.append("\t\t document.getElementById(divsubmenu).style.position = \"absolute\"; \n");
		    funcoes.append("\t } \n");
		    
		    // escrever as funções abre e fecha
		    funcoes.append("\t function abremenu(x) { \n");
		    funcoes.append("\t\t itemmenu = \"item\"+x; \n");
		    funcoes.append("\t\t itemmenutext = itemtext[x-1]; \n");
		    funcoes.append("\t\t divsubmenu = \"submenu\"+x; \n");
		    funcoes.append("\t\t document.getElementById(itemmenu).innerHTML = \"<a href='javascript:fechamenu(\"+x+\");'>\"+itemmenutext+\"</a>\"; \n");
		    funcoes.append("\t\t document.getElementById(itemmenu).style.backgroundImage = \"url(\"+_pathEcar+\"/images/estilo/icone_menu_menos_gd_"+configuracaoMenu.getEstilo().getNome()+".gif)\"; \n");
		    funcoes.append("\t\t document.getElementById(divsubmenu).style.visibility = \"visible\"; \n");
		    funcoes.append("\t\t document.getElementById(divsubmenu).style.position = \"static\"; \n");
		    funcoes.append("\t\t y=1; \n");
		    funcoes.append("\t\t while (y <= ").append(auxNivel).append(") { \n");
		    funcoes.append("\t\t\t if (y != x) { \n");
		    funcoes.append("\t\t\t\t fechamenu(y); \n");
		    funcoes.append("\t\t\t } \n");
		    funcoes.append("\t\t\t y = y + 1; \n");
		    funcoes.append("\t\t } \n");
		    funcoes.append("\t } \n");
	    } catch (ECARException e) {
	    	
	    }

	    return (new String[] {itemText.toString(), divs.toString(), funcoes.toString(), fechaMenu.toString()});
	}
	
	
	/**
	 * Imprime as opções principais do menu, passando e verificando a existência 
	 * dos filhos pelo método imprimeSubMenusFilhos.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param List lista
	 * @throws IOException
	 */
	private void imprimeSubMenus(List lista) throws IOException {
	    if (lista == null || lista.size() == 0)
	        return;
	    
	    int numMenu = 0;
	    
	    Iterator it = lista.iterator();
	    while (it.hasNext()) {
	        ItemMenu m = (ItemMenu) it.next();
	        StringBuffer sb = new StringBuffer();
	        
        	sb.append("\n\t submenu").append(numMenu).append(" = new dTree('submenu").append(numMenu).append("'); \n");
        	sb.append("\t submenu").append(numMenu).append(".add(").append(m.getCodigo().toString()).append(",-1,'');");
        	
        	writer.println(sb.toString());
            imprimeSubMenusFilhos(m.getItemMenuFilhos(), numMenu);
            
            numMenu = numMenu + 1;
	    }
	}
	
	/**
	 * Método acionado a partir imprimeSubMenus, imprimindo os filhos dos itens 
	 * principais e os demais (filhos dos filhos, netos).<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param List lista
	 * @param int nivel
	 * @throws IOException
	 */
	private void imprimeSubMenusFilhos(List lista, int numMenu) throws IOException {
	
		if (lista == null || lista.size() == 0)
	        return;
	    
	    Iterator it = lista.iterator();
	    while (it.hasNext()) {
	        ItemMenu m = (ItemMenu) it.next();
	        StringBuffer sb = new StringBuffer();
	        
	        //guarda na lista os nomes do caminho na ordem inversa a mostrada na tela
	        List<String> nomesDoCaminho = new ArrayList<String>();
	        nomesDoCaminho.add(m.getNome());
	       
	        ItemMenu menuAtual = m.getItemMenuPai(); //pega o pai do primeiro
	        
	        while(menuAtual != null) { //pega o pai do filho até não existir mais pai
	        	nomesDoCaminho.add(menuAtual.getNome());//vai adicionando os nomes a lista
	        	ItemMenu teste = new ItemMenu (null,0," "," ");
	        	teste = menuAtual;
	        	menuAtual = teste.getItemMenuPai();
	        }
	        
	       //percorre a lista para montar o caminho na ordem inversa a armazenada na lista
	        String caminho = "";//vai guardar o caminho do menu
	        int i = (nomesDoCaminho.size() - 1);
	        caminho =  nomesDoCaminho.get(i);
	        i = (nomesDoCaminho.size() - 2);
	        while(i>=0) {
	        	if(nomesDoCaminho.get(i)!= null)
	        		caminho =  caminho + " > "+ nomesDoCaminho.get(i);
	        	i=i-1;
	        }
	        
	        sb.append("\t submenu").append(numMenu).append(".add(").append(m.getCodigo().toString()).append(",");
        	sb.append(m.getItemMenuPai().getCodigo().toString()).append(",'").append(m.getNome()).append("',");
        	if(m.getUrl() != null && !"".equals(m.getUrl())){
        		sb.append("'").append(request.getContextPath()).append("/abrirUrl.jsp?url=").append(m.getUrl()).append("&titulo=").append(caminho).append("');");
        	}
        	else {
        		sb.append("'');");
        	}
	        
            writer.println(sb.toString());
            imprimeSubMenusFilhos(m.getItemMenuFilhos(), numMenu);
	    }
	    
	    
	}
	
	/**
	 * Fazer uma função que "gospe" o texto do JavaScript que está em menu.jsp.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param List lista
	 * @throws IOException
	 */
	private void montaMenuJS(List lista) throws IOException {
	    
	}

	/**
	 * Retorna javax.servlet.http.HttpServletRequest request.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return javax.servlet.http.HttpServletRequest
	 */
	public javax.servlet.http.HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * Atribui valor especificado para javax.servlet.http.HttpServletRequest request.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param request
	 */
	public void setRequest(javax.servlet.http.HttpServletRequest request) {
		this.request = request;
	}
}