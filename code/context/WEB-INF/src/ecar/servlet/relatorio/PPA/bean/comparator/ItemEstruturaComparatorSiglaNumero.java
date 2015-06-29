package ecar.servlet.relatorio.PPA.bean.comparator;

import java.util.Comparator;

import ecar.pojo.ItemEstruturaIettPPA;

/**
 *
 * @author 70744416353
 */
public class ItemEstruturaComparatorSiglaNumero implements Comparator<ItemEstruturaIettPPA>{

	public int compare(ItemEstruturaIettPPA beanParam1, ItemEstruturaIettPPA beanParam2) {
		
		Long bean1Long = Long.valueOf( beanParam1.getSiglaIett() );
		Long bean2Long = Long.valueOf( beanParam2.getSiglaIett() );		
		
		return bean1Long.compareTo(bean2Long);
			
	}	
	
}
