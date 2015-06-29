package ecar.servlet.relatorio.PPA_funcao.bean.comparator;

import java.util.Comparator;

import ecar.servlet.relatorio.PPA_funcao.bean.FuncaoSubAreaSareBean;

/**
 *
 * @author 70744416353
 */
public class FuncaoComparatorSubAreaSare implements Comparator<FuncaoSubAreaSareBean>{

	public int compare(FuncaoSubAreaSareBean beanParam1, FuncaoSubAreaSareBean beanParam2) {
		return beanParam1.getSubAreaSare().getCodigoIdentSare().compareTo( beanParam2.getSubAreaSare().getCodigoIdentSare() );
			
	}	
	
}
