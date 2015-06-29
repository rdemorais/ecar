/*
 * Criado em 12/12/2008
 *
 */
package comum.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

/**
 */
public class EcarCfg {

    private static Properties p = null;
    
	private Logger logger = null;

    /**
     * Cria uma nova instância da classe EcarCfg.<br>
     * 
     * * @param ServletContext application
     * @param application
     */
	 public EcarCfg(ServletContext application){
    	logger = Logger.getLogger(EcarCfg.class);

        if (p == null) {
        	load(application);
        }
    }
	
    
    private void load(ServletContext application) {
		
        try {
			if(p == null) {
		        p = new Properties();  // Instancia a Properties
	            p.load(new FileInputStream(application.getRealPath("/WEB-INF/classes/properties/ecar_cfg.properties"))); // Abre o arquivo de properties
			}
        } catch (FileNotFoundException e) {
        	logger.error(e);
        } catch (IOException e) {
        	logger.error(e);
        }            
		
	}


	/**
     * Retorna uma mensagem definida no arquivo de configuração.<br>
     * 
         * @param key
         * @author N/C
     * @since N/C
     * @version N/C
     * @return String 			- A mensagem correspondente ou mensagem padrão se não encontra a chave informada
     */
    public static String getConfiguracao(String key){
        
    	String valor;
    	
	    if(p != null && p.containsKey(key)) {
	        valor = p.get(key).toString();
	    } else {
	        valor = "Mensagem não econtrada";
	    }
        
    	return valor;
    }
    
}
