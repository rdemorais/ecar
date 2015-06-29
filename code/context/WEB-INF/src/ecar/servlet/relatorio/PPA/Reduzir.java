package ecar.servlet.relatorio.PPA;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author 70744416353
 */
public class Reduzir {

    /**
     *
     * @param colecao
     * @return
     */
    public static Set reduzir(Set colecao){
		
		final int MAX = colecao.size();
		HashSet retorno = new HashSet();
		int cont = 0;
		for (Iterator iter = colecao.iterator(); iter.hasNext();) {
			if ( cont <= MAX){
				retorno.add(iter.next());				
			}else{
				break;
			}
			cont++;			
		}
		
		return colecao;		
		
	}
	
}
