package ecar.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GanttUtil {
	public static List<Integer> formataGantt(Date dtInicial, Date dtFinal) {
		Calendar dtInicio = Calendar.getInstance();
		Calendar dtFim = Calendar.getInstance();
		
		dtInicio.setTime(dtInicial);
		dtFim.setTime(dtFinal);
		
		List<Integer> anos = itensDoPeriodo(dtInicio, dtFim, Calendar.YEAR);
		List<Integer> meses = new ArrayList<Integer>();
		for (int i = 0; i < anos.size(); i++) {
			if(anos.size() >= 2 && (i < (anos.size() -1))) {
				dtFim.set(anos.get(i), Calendar.DECEMBER, 31);
			}
			if(i > 0) {
				dtInicio.set(anos.get(i), Calendar.JANUARY, 1);
			}
			if(i == (anos.size() -1)) {
				dtFim.setTime(dtFinal);
			}
			meses = itensDoPeriodo(dtInicio, dtFim, Calendar.MONTH);
//			for (Integer mes : meses) {
//				int anoIndice = anos.get(i) - 2011;
//				listaMeses.add(mes + (anoIndice));
//			}
		}
		
		return meses;
	}
	
	private static List<Integer> itensDoPeriodo(Calendar ini, Calendar fim, int tipo) {
		List<Integer> itens = new ArrayList<Integer>();
		int iniNumero = ini.get(tipo);
		int fimNumero = fim.get(tipo);
		for(;iniNumero < fimNumero; iniNumero++) {
			itens.add(iniNumero);
		}
		itens.add(fimNumero);
		return itens;
	}
}
