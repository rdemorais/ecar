package comum.util;

import java.lang.reflect.Method;
import java.util.List;

public class Ordena<T> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> ordenarLista(List<T> list, String coluna) throws Exception {

		for (int i = 0; i < list.size(); i++) {

			T t = list.get(i);

			Method meto1 = t.getClass().getMethod(coluna, new Class[] {});

			Comparable comp1 = (Comparable) meto1.invoke(t, new Object[] {});

			for (int j = i + 1; j < list.size(); j++) {

				T t2 = list.get(j);

				Method meto2 = t2.getClass().getMethod(coluna, new Class[] {});

				Comparable comp2 = (Comparable) meto2.invoke(t2,
						new Object[] {});

				if (!meto1.getReturnType().toString().contains("String")) {

					if (comp1.compareTo(comp2) >= 1) {

						list.remove(j);

						list.add(i, t2);

						i--;

						j = list.size();

					}

				} else {

					String v = comp1.toString().toUpperCase();

					Comparable cp = v;

					String d = comp2.toString().toUpperCase();

					Comparable cp2 = d;

					if (cp.compareTo(cp2) >= 1) {

						list.remove(j);

						list.add(i, t2);

						i--;

						j = list.size();

					}

				}

			}

		}

		return list;

	}
}