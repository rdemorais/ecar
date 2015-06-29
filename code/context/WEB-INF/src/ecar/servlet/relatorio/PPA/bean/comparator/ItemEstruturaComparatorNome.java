package ecar.servlet.relatorio.PPA.bean.comparator;

import java.util.Comparator;

import ecar.pojo.ItemEstruturaIettPPA;

/**
 *
 * @author 70744416353
 */
public class ItemEstruturaComparatorNome implements Comparator<ItemEstruturaIettPPA>{

	public int compare(ItemEstruturaIettPPA beanParam1, ItemEstruturaIettPPA beanParam2) {
		
		return  beanParam1.getNomeIett().compareTo( beanParam2.getNomeIett() );
		
			
	}	
	
}
