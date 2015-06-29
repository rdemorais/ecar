/*
 * Criado em 25/10/2004
 *
 */
package comum.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

/**
 * @author felipev
 */
public class Mensagem {

    private static Properties p = null;
    private ServletContext context;
    
	private Logger logger = null;

    /**
     * Cria uma nova instância da classe Mensagem.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param application
     */
    public Mensagem(ServletContext application){
    	this.logger = Logger.getLogger(this.getClass());

        context = application;
        try {
			if(p == null) {
		        p = new Properties();  // Instancia a Properties
	            p.load(new FileInputStream(application.getRealPath("/WEB-INF/classes/properties/ecar.properties"))); // Abre o arquivo de properties
			}
        } catch (FileNotFoundException e) {
        	logger.error(e);
        } catch (IOException e) {
        	logger.error(e);
        }            
    }
    
    /**
     * Retorna uma mensagem definida no arquivo de mensagens.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param key
     * @return String 			- A mensagem correspondente ou mensagem padrão se não encontra a chave informada
     */
    public String getMensagem(String key){
        
        if(p != null && p.containsKey(key))
            return p.get(key).toString();
        else
            return "Mensagem não econtrada";
        
    }
    
    
    /**
     *
     * @param key
     * @param param
     * @return
     */
    public String getMensagem(String key,String[] param){
        
    	String ret = getMensagem(key);
    	
        	if (param != null) {
	        	for (int i=0;i<param.length;i++){
	        		ret = ret.replace("{"+i+"}", param[i]);
	        	}
        	}            
     
        
        return ret;
    }    

    
    /**
     * Retorna código Javascript para exibir um alert.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param page
     * @param msg
     * @throws Exception
     */
    public static void alert(PageContext page, String msg) throws Exception {
        try {
	    	page.getOut().println("<script language=\"javascript\">");

	    	if(msg == null){
    			page.getOut().println("alert(\"" + "Ocorreu um erro inesperado, por favor contate o administrador." + "\");");
	    	}else{
	    		if (!msg.contains(ConstantesECAR.QUEBRA_LINHA_MENSAGEM)) {
	    			page.getOut().println("alert(\"" + msg + "\");");
	    		} else {
	    			page.getOut().println("var msg = '"+msg+"';");
	    			page.getOut().println("alert(replaceAll(msg,'"+ConstantesECAR.QUEBRA_LINHA_MENSAGEM+"','\\n'));");

	    		}
	    	}
	    	page.getOut().println("</script>");                              

        } catch(IOException e) {
            throw new Exception(e);
        }
    }
    
    /**
     * Imprime na página o codigo javascript para atribuir um valor a uma input.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param page 
     * @param input
     * @param value
     * @throws Exception
     */
    public static void setInput(PageContext page, String input, String value) throws Exception {
        try {
            page.getOut().println("<script language=\"javascript\">");
            page.getOut().println(input + " = \"" + value + "\"");
            page.getOut().println("</script>");                              
        } catch(IOException e) {
            throw new Exception(e);
        }
    }
    
    /**
     * Metodo especifico para obter a chave path.upload.raiz no arquivo de propriedades.<br>
     * Retorna o valor declarado nessa propriedade ou um valor default que é o
     * caminho fisico do contexto atual.<br>
     * 
     * @author garten
     * @since N/C
     * @version N/C
     * @param key
     * @return String 			- Conteudo da chave no arquivo de properties
     */
    public String getPathUploadRaiz(String key) {

        if(p.containsKey(key))
            return p.get(key).toString();
        else
            return context.getRealPath(""); 
    }
    
    /**
     * Retorna a quantidade especificada de itens por pagina de pesquisa.<br>
     * Se nao especificada retorna 10.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param key
     * @return int
     */
    public int getQtdeItensPaginaPesquisa(String key) {
        if(p.containsKey(key))
            return Integer.parseInt(p.get(key).toString());
        else
            return 10; 
    }

}
