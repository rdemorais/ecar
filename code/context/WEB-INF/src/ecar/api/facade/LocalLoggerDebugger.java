package ecar.api.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * 
 * Classe utilizada para gerar log em tempo de desenvolvimento
 * ou junit, sem necessidade de rodar o jboss.
 * 
 * @author N/A
 *
 */
public class LocalLoggerDebugger {

	public static final String properties = "log4j-debug.properties"; 
	
	
	/**
	 * 
	 * Obtém um log a partir de um arquivo padrão. 
	 * 
	 * @return
	 */
	public static Log getLocalLogger(Class clasz) {
		Properties props = new Properties();

		Log log = null;
		try{
			log = LogFactory.getLog(clasz);
		}catch(Exception e){
			//ocorrerá uma exeção pois o log4j tentará
			//carregar um arquivo padrão. Ignoramos e 
			//refazemos a configuraçaõ.
		}

		String path = null;
		try {
		    path = new File(".").getCanonicalPath().concat("/").concat(properties);
		    System.out.println("[ECAR-LOGGER] using the properties file: " + path);
		}catch(Exception e){
			System.out.println("Não foi possível criar o logger");
			return null;
		}

		try {
			InputStream configStream = new FileInputStream(path);
			props.load(configStream);
			configStream.close();
		} catch(IOException e) {
			System.out.println("Error: Cannot laod configuration file ");
		}

		LogManager.resetConfiguration();
		PropertyConfigurator.configure(props);     

		//todos os logs imprimem no log de desenvolvimento agora
		Logger.getLogger(LocalLoggerDebugger.class); 
		
		return log;
	}

	/**
	 * 
	 * Obtém um log a partir de um arquivo de configuração.
	 * O argumento filepath deve ser o caminho completo para o arquivo de configuração
	 * 
	 * 
	 * @param filepath
	 * @return
	 */
	public static Log getLocalLogger(Class clasz, String filepath){
		Properties props = new Properties();

		Log log = null;
		try{
			log = LogFactory.getLog(clasz);
		}catch(Exception e){
			//ocorrerá uma exeção pois o log4j tentará
			//carregar um arquivo padrão. Ignoramos e 
			//refazemos a configuraçaõ.
		}

		try {
		    System.out.println("[ECAR-LOGGER] using the properties file: " + filepath);
			InputStream configStream = new FileInputStream(filepath);
			props.load(configStream);
			configStream.close();
		} catch(IOException e) {
			System.out.println("Error: Cannot laod configuration file ");
		}

		LogManager.resetConfiguration();
		PropertyConfigurator.configure(props);     

		//todos os logs imprimem no log de desenvolvimento agora
		Logger.getLogger(LocalLoggerDebugger.class); 

		return log;
		
	}
	
}
