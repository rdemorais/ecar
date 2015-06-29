package comum.util;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class MesComparator implements Comparator<String>{
	private SimpleDateFormat sdf = new SimpleDateFormat("MMM", new Locale("pt","BR"));
	
	public int compare(String mes1, String mes2) {
		int retorno = 0;
		try {
			Date d1 = sdf.parse(mes1);
			Date d2 = sdf.parse(mes2);
			retorno = d1.compareTo(d2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}
}
