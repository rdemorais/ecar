package ecar.servlet.relatorio.PPA_funcao.bean.comparator;

import java.util.Comparator;

import ecar.servlet.relatorio.PPA_funcao.bean.FuncaoAreaAreBean;

/**
 *
 * @author 70744416353
 */
public class FuncaoComparatorAreaAre implements Comparator<FuncaoAreaAreBean>{

	public int compare(FuncaoAreaAreBean beanParam1, FuncaoAreaAreBean beanParam2) {
		return beanParam1.getAreaAre().getCodigoIdentAre().compareTo( beanParam2.getAreaAre().getCodigoIdentAre() );
			
	}	
	
}
