package comum.database;

import gov.pr.celepar.sentinela.comunicacao.SentinelaAdmInterface;
import gov.pr.celepar.sentinela.comunicacao.SentinelaAdministracao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;
import gov.pr.celepar.sentinela.comunicacao.SentinelaParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author N/C
 * @since N/C
 * @version N/C
 */
public class SentinelaUtil {
	private static final ThreadLocal thread = new ThreadLocal();
	private SentinelaInterface sentinelaInterface = null;
	//private SentinelaAdmInterface sentinelaAdmInterface= null;
	
	/**
	 * Construtor.<br>
	 * 
         * @param request
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public SentinelaUtil(HttpServletRequest request) {
		sentinelaInterface = SentinelaComunicacao.getInstance(request);
		thread.set(sentinelaInterface);
	}

/**
 * Retorna SentinelaInterface.<br>
 * 
 * @author N/C
 * @version N/C
 * @return SentinelaInterface
 */
	public static SentinelaInterface getSentinelaInterface() {
		return (SentinelaInterface)thread.get();
	}
	

	/**
	 * Retorna SentinelaAdmInterface.<br>
	 * 
         * @param request
         * @author N/C
	 * @version N/C
	 * @return SentinelaAdmInterface
	 */
		public static SentinelaAdmInterface getSentinelaAdmInterface(HttpServletRequest  request) {
			return  SentinelaAdministracao.getInstance (request); 
		}

	/**
	 * 
	 * @param grupos  Todos os grupos do sistema 
	 * @param vinculados  Grupos pertencentes ao usuário 
	 * @return retorna lista dos não vinculados atraves dos parametros passados 
	 */
	public static SentinelaParam[] getGruposNaoVinculados(SentinelaParam[] grupos, SentinelaParam[] vinculados ){
		
		/*Grupos não pertencentes ao usuário */   
		SentinelaParam[] naoVinculados = new SentinelaParam[grupos.length - vinculados.length ] ;
		
		int contNaoVinculado = 0;
		for (int i =0; i<grupos.length;i++){
			boolean ehVinculado = false;
			
			for (int j = 0; j<vinculados.length;j++ ){
				if ( grupos[i].getCodigo()== vinculados[j].getCodigo() ){
					ehVinculado = true;
					break;
				}
			}
			//se não eh vinculado
			if (! ehVinculado){
				naoVinculados[contNaoVinculado++]=grupos[i] ;		
			}
					
		}
		
		return naoVinculados;
		
	}
    
}
