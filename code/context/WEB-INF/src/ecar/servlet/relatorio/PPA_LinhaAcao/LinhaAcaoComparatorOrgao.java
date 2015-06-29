package ecar.servlet.relatorio.PPA_LinhaAcao;

import java.util.Comparator;


/**
 *
 * @author 70744416353
 */
public class LinhaAcaoComparatorOrgao implements Comparator<PPA_LinhaAcaoBean>{

	public int compare(PPA_LinhaAcaoBean beanParam1, PPA_LinhaAcaoBean beanParam2) {
		return beanParam1.getSiglaOrgao().compareTo( beanParam2.getSiglaOrgao() );
			
	}		
	
	
}
