package ecar.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.hibernate.Hibernate;

import comum.util.Data;

/**
 * Esta classe tem o objetivo de trabalhar com entidades copiando os valores das
 * propriedades.
 */
public class Entidade {

	static {
		/**
		 * Corrige o bug do BeanUtils pra converter valores de data que estão
		 * null
		 */
		ConvertUtils.register(new Converter() {
			public Object convert(Class type, Object value) {
				SqlTimestampConverter sqlTimestampConverter = new SqlTimestampConverter();

				if ((value == null) || (value.toString().length() < 1)) {
					return null;
				}

				return sqlTimestampConverter.convert(type, value);
			}
		}, Timestamp.class);

		// Resolve o problemas dos nulos para o Double
		ConvertUtils.register(new Converter() {
			public Object convert(Class type, Object value) {
				DoubleConverter converte = new DoubleConverter();
				if (value == null || value.equals("")) {
					return null;
				} else {
					return converte.convert(type, value);
				}
			}
		}, Double.class);

		// Resolve o problemas dos nulos para o Integer
		ConvertUtils.register(new Converter() {
			public Object convert(Class type, Object value) {
				IntegerConverter converte = new IntegerConverter();
				if (value == null || value.equals("")) {
					return null;
				} else {
					return converte.convert(type, value);
				}
			}
		}, Integer.class);

		// Resolve o problemas dos nulos para o Long
		ConvertUtils.register(new Converter() {
			public Object convert(Class type, Object value) {
				LongConverter converte = new LongConverter();
				if (value == null || value.equals("")) {
					return null;
				} else {
					return converte.convert(type, value);
				}
			}
		}, Long.class);

		// Resolve o problemas dos nulos para o BigDecimal
		ConvertUtils.register(new Converter() {
			public Object convert(Class type, Object value) {
				BigDecimalConverter converte = new BigDecimalConverter();
				if (value == null || value.equals("")) {
					return null;
				} else {
					return converte.convert(type, value);
				}
			}
		}, BigDecimal.class);

	}

	/**
	 * Copia as propriedades de um objeto para o outro, fazendo as conversões de
	 * tipo necessárias. Todas as propriedades do objeto de origem devem ser do
	 * tipo String. As propriedades a serem copiadas do objeto de origem devem
	 * ter exatamente o mesmo nome no obeto de destino.
	 * 
	 * @param origem
	 *            - o bean de origem, todos os seus campos devem ser do tipo
	 *            String
	 * @param destino
	 *            - o bean de destino, as propriedades do bean de origem serão
	 *            copiadas para esse bean, com as devidas conversões
	 * @throws Exception
	 */
	public static void copiarVOStringTipo(Object origem, Object destino) throws Exception {
		Object ovalor;

		Map mapOrigem = BeanUtils.describe(origem);
		Map mapDestino = BeanUtils.describe(destino);

		for (Iterator iter = mapOrigem.keySet().iterator(); iter.hasNext();) {
			String propriedade = (String) iter.next();

			if (mapDestino.keySet().contains(propriedade)) {
				Class propriedadeTipo = PropertyUtils.getPropertyType(destino, propriedade);
				ovalor = PropertyUtils.getProperty(origem, propriedade);
				setarPropriedadeStringTipo(destino, propriedade, ovalor, propriedadeTipo);
			}
		}
	}

	/**
	 * Copia as propriedades de um objeto para o outro, fazendo as conversões de
	 * tipo para string. Todas as propriedades do objeto de destino devem ser do
	 * tipo String. As propriedades a serem copiadas do objeto de origem devem
	 * ter exatamente o mesmo nome no obeto de destino.
	 * 
	 * @param origem
	 *            - o bean de origem, todos os seus campos devem ser do tipo
	 *            String
	 * @param destino
	 *            - o bean de destino, as propriedades do bean de origem serão
	 *            copiadas para esse bean, com as devidas conversões
	 * @return void
	 * @throws Exception
	 */
	private static void setarPropriedadeStringTipo(Object beanVo, String propriedade, Object object, Class tipo) throws Exception {
		String valor = null;
		String datatype = tipo.getName();

		if (object instanceof String[]) {
			String[] arrValor = (String[]) object;

			if ((arrValor != null) && (arrValor.length != 0)) {
				BeanUtils.setProperty(beanVo, propriedade, object);
			}

			return;
		} else if (object instanceof String) {
			valor = (String) object;
		} else {
			return;
		}

		if ((valor == null) || valor.trim().equals("")) {
			if (datatype.equals("java.lang.String") || datatype.equals("String") || datatype.equals("java.util.Date")
					|| datatype.equals("java.math.BigDecimal") || datatype.equals("java.lang.Double") || datatype.equals("java.lang.Integer")
					|| datatype.equals("java.lang.Long") || datatype.equals("java.lang.Float") || datatype.equals("java.lang.Short")
					|| datatype.equals("java.lang.Byte")) {
				BeanUtils.setProperty(beanVo, propriedade, null);
			}

			return;
		}

		if (datatype.equals("java.lang.String") || datatype.equals("String")) {
			BeanUtils.setProperty(beanVo, propriedade, valor);

			return;
		} else if (datatype.equals("java.util.Date")) {
			Date ovalor = FormatDate.parse(valor, "dd/MM/yyyy");
			BeanUtils.setProperty(beanVo, propriedade, ovalor);

			return;
		} else if (datatype.equals("java.sql.Date")) {
			java.sql.Date ovalor = FormatDate.parseDate(valor, "dd/MM/yyyy");
			BeanUtils.setProperty(beanVo, propriedade, ovalor);

			return;
		} else if (datatype.equals("double")) {
			Double ovalor = new Double(valor);
			BeanUtils.setProperty(beanVo, propriedade, ovalor);

			return;
		} else if (datatype.equals("int")) {
			Integer ovalor = new Integer(valor);
			BeanUtils.setProperty(beanVo, propriedade, ovalor);

			return;
		} else if (datatype.equals("long")) {
			Long ovalor = new Long(valor);
			BeanUtils.setProperty(beanVo, propriedade, ovalor);

			return;
		} else if (datatype.equals("short")) {
			Short ovalor = new Short(valor);
			BeanUtils.setProperty(beanVo, propriedade, ovalor);

			return;
		} else if (datatype.equals("java.lang.Double")) {
			Double ovalor = new Double(valor);
			BeanUtils.setProperty(beanVo, propriedade, ovalor);

			return;
		} else if (datatype.equals("java.lang.Integer")) {
			Integer ovalor = new Integer(valor);
			BeanUtils.setProperty(beanVo, propriedade, ovalor);

			return;
		} else if (datatype.equals("java.lang.Long")) {
			Long ovalor = new Long(valor);
			BeanUtils.setProperty(beanVo, propriedade, ovalor);

			return;
		} else if (datatype.equals("java.lang.Float")) {
			Float ovalor = new Float(valor);
			BeanUtils.setProperty(beanVo, propriedade, ovalor);

			return;
		} else if (datatype.equals("java.lang.Short")) {
			Short ovalor = new Short(valor);
			BeanUtils.setProperty(beanVo, propriedade, ovalor);

			return;
		} else if (datatype.equals("java.lang.Byte")) {
			Byte ovalor = new Byte(valor);
			BeanUtils.setProperty(beanVo, propriedade, ovalor);

			return;
		} else if (datatype.equals("java.math.BigDecimal")) {
			java.math.BigDecimal ovalor = new java.math.BigDecimal(valor);
			BeanUtils.setProperty(beanVo, propriedade, ovalor);

			return;
		} else if (datatype.equals("boolean")) {
			Boolean ovalor = new Boolean(valor);
			BeanUtils.setProperty(beanVo, propriedade, ovalor);

			return;
		} else if (datatype.equals("java.sql.Timestamp")) {
			/**
			 * Se o valor for igual a 10 usar dd/MM/yyyy caso não, usar
			 * yyyy-MM-dd HH:mm:ss.mmm
			 */
			java.sql.Timestamp ovalor = ((valor.length() == 10) ? new java.sql.Timestamp(FormatDate.parseDate(valor).getTime()) : FormatDate.parseTimestamp(
					valor, "dd/MM/yyyy"));
			BeanUtils.setProperty(beanVo, propriedade, ovalor);

			return;
		}
	}

	/**
	 * Copia as propriedades de um objeto para o outro, fazendo as conversões de
	 * tipo necessárias. Todas as propriedades do objeto de destino devem ser do
	 * tipo String. As propriedades a serem copiadas do objeto de origem devem
	 * ter exatamente o mesmo nome no objeto de destino.
	 * 
         * @param origem
         * @param destino
         * @throws Exception
	 */
	public static void copiarVOTipoString(Object origem, Object destino) throws Exception {
		Object ovalor;

		Map mapOrigem = BeanUtils.describe(origem);
		Map mapDestino = BeanUtils.describe(destino);

		for (Iterator iter = mapOrigem.keySet().iterator(); iter.hasNext();) {
			String propriedade = (String) iter.next();

			if (mapDestino.keySet().contains(propriedade)) {
				Class propriedadeTipo = PropertyUtils.getPropertyType(origem, propriedade);
				ovalor = PropertyUtils.getProperty(origem, propriedade);
				setarPropriedadeTipoString(destino, propriedade, ovalor, propriedadeTipo);
			}
		}
	}

	private static void setarPropriedadeTipoString(Object beanVo, String propriedade, Object valor, Class tipo) throws Exception {
		String datatype = tipo.getName();
		String svalor;

		if (datatype.equals("java.lang.String") || datatype.equals("String")) {
			BeanUtils.setProperty(beanVo, propriedade, valor);

			return;
		} else if (datatype.equals("java.util.Date")) {
			svalor = FormatDate.format((Date) valor);
			BeanUtils.setProperty(beanVo, propriedade, svalor);

			return;
		} else if (datatype.equals("java.sql.Date")) {
			svalor = FormatDate.format((java.sql.Date) valor);
			BeanUtils.setProperty(beanVo, propriedade, svalor);

			return;
		} else if (datatype.equals("double")) {
			svalor = Double.toString(((Double) valor).doubleValue());
			BeanUtils.setProperty(beanVo, propriedade, svalor);

			return;
		} else if (datatype.equals("int")) {
			svalor = Double.toString(((Integer) valor).intValue());
			BeanUtils.setProperty(beanVo, propriedade, svalor);

			return;
		} else if (datatype.equals("long")) {
			svalor = Double.toString(((Long) valor).longValue());
			BeanUtils.setProperty(beanVo, propriedade, svalor);

			return;
		} else if (datatype.equals("short")) {
			svalor = Double.toString(((Short) valor).shortValue());
			BeanUtils.setProperty(beanVo, propriedade, svalor);

			return;
		} else if (datatype.equals("java.lang.Double")) {
			svalor = Double.toString(((Double) valor).doubleValue());
			BeanUtils.setProperty(beanVo, propriedade, svalor);

			return;
		} else if (datatype.equals("java.lang.Integer")) {
			svalor = Double.toString(((Integer) valor).intValue());
			BeanUtils.setProperty(beanVo, propriedade, svalor);

			return;
		} else if (datatype.equals("java.lang.Long")) {
			svalor = Double.toString(((Long) valor).longValue());
			BeanUtils.setProperty(beanVo, propriedade, svalor);

			return;
		} else if (datatype.equals("java.lang.Float")) {
			svalor = Double.toString(((Float) valor).floatValue());
			BeanUtils.setProperty(beanVo, propriedade, svalor);

			return;
		} else if (datatype.equals("java.lang.Short")) {
			svalor = Double.toString(((Short) valor).shortValue());
			BeanUtils.setProperty(beanVo, propriedade, svalor);

			return;
		} else if (datatype.equals("java.lang.Byte")) {
			svalor = Double.toString(((Byte) valor).byteValue());
			BeanUtils.setProperty(beanVo, propriedade, svalor);

			return;
		} else if (datatype.equals("java.math.BigDecimal")) {
			svalor = Double.toString(((BigDecimal) valor).doubleValue());
			BeanUtils.setProperty(beanVo, propriedade, svalor);

			return;
		} else if (datatype.equals("boolean")) {
			svalor = Double.toString(((Integer) valor).intValue());
			BeanUtils.setProperty(beanVo, propriedade, svalor);

			return;

			/**
			 * Se o valor for igual a 10 usar dd/MM/yyyy caso não, usar
			 * yyyy-MM-dd HH:mm:ss.mmm
			 */
		} else if (datatype.equals("java.sql.Timestamp")) {
			// TODO:implementar metodo
			return;
		}
	}

	/**
	 * Copia as propriedades de um objeto para o outro, sem conversões de tipo.
	 * As propriedades a serem copiadas do objeto de origem devem ter exatamente
	 * o mesmo nome no objeto de destino.
	 * 
         * @param origem
         * @param destino
	 * @throws Exception
	 */
	public static void clonarPojo(Object origem, Object destino) throws Exception {
		Object ovalor;
		Date valorData;
		Class tipo;
		
		Map mapOrigem = BeanUtils.describe(origem);
		Map mapDestino = BeanUtils.describe(destino);

		for (Iterator iter = mapOrigem.keySet().iterator(); iter.hasNext();) {
			String propriedade = (String) iter.next();
			Hibernate.initialize(propriedade);
			if (mapDestino.keySet().contains(propriedade)) {
				tipo = PropertyUtils.getPropertyType(origem, propriedade);
				if (tipo.getName().equals("java.util.Date")){
					valorData = (Date) PropertyUtils.getProperty(origem, propriedade);
					
					GregorianCalendar calendar = null;
					
					if (valorData != null){
						calendar = Data.getGregorianCalendar(valorData);
					}
										
					if (valorData != null){
						BeanUtils.setProperty(destino, propriedade, calendar.getTime());
						
					}
				}
				else if (tipo.getName().equals("java.util.Set")) {
					Set set = new HashSet( (Set) PropertyUtils.getProperty(origem, propriedade));
					BeanUtils.setProperty(destino, propriedade, set);
					
				}				
				else{
					ovalor = PropertyUtils.getProperty(origem, propriedade);
					
					if (ovalor != null){
						BeanUtils.setProperty(destino, propriedade, ovalor);
					}
				}
							

			}
		}
	}
	

}
