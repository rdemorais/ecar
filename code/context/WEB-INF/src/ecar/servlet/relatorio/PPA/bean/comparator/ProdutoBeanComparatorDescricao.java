package ecar.servlet.relatorio.PPA.bean.comparator;

import java.util.Comparator;

import ecar.servlet.relatorio.PPA.bean.ProdutoBean;

/**
 *
 * @author 70744416353
 */
public class ProdutoBeanComparatorDescricao implements Comparator<ProdutoBean>{

	public int compare(ProdutoBean beanParam1, ProdutoBean beanParam2) {
		return beanParam1.getDescricao().compareTo( beanParam2.getDescricao() );
	}	
	
}
