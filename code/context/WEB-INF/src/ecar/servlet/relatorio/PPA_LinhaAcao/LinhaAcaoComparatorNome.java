package ecar.servlet.relatorio.PPA_LinhaAcao;

import java.util.Comparator;

import ecar.pojo.ItemEstruturaIett;

/**
 *
 * @author 70744416353
 */
public class LinhaAcaoComparatorNome implements Comparator<ItemEstruturaIett>{

	public int compare(ItemEstruturaIett beanParam1, ItemEstruturaIett beanParam2) {
		
		return beanParam1.getNomeIett().compareTo( beanParam2.getNomeIett() );
			
	}	
	
}
