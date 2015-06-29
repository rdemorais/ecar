package ecar.pojo.acompanhamentoEstrategico;

import java.util.Comparator;

public class ResultadoComparator implements Comparator<Resultado>{

	public int compare(Resultado resultado, Resultado outroResultado) {
		if(resultado.getPeriodoAcompanhamento().get(0).getStatusPeriodoAcompanhamento().get(0).getCodigo() == 3){
			return -1;
		}
		if(outroResultado.getPeriodoAcompanhamento().get(0).getStatusPeriodoAcompanhamento().get(0).getCodigo() == 3){
			return 1;
		}
		if(resultado.getPeriodoAcompanhamento().get(0).getStatusPeriodoAcompanhamento().get(0).getCodigo() == 2){
			return -1;
		}
		if(outroResultado.getPeriodoAcompanhamento().get(0).getStatusPeriodoAcompanhamento().get(0).getCodigo() == 2){
			return 1;
		}
		if(resultado.getPeriodoAcompanhamento().get(0).getStatusPeriodoAcompanhamento().get(0).getCodigo() == 1){
			return -1;
		}
		if(outroResultado.getPeriodoAcompanhamento().get(0).getStatusPeriodoAcompanhamento().get(0).getCodigo() == 1){
			return 1;
		}
		
		return 0;
	}
	
}
