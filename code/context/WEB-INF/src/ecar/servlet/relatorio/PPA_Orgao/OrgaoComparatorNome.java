package ecar.servlet.relatorio.PPA_Orgao;

import java.util.Comparator;

import ecar.pojo.OrgaoOrg;

/**
 *
 * @author 70744416353
 */
public class OrgaoComparatorNome implements Comparator<OrgaoOrg>{

	public int compare(OrgaoOrg beanParam1, OrgaoOrg beanParam2) {
		
		return beanParam1.getDescricaoOrg().compareTo( beanParam2.getDescricaoOrg() );
			
	}	
	
}
