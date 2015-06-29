package ecar.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.RegDemandaAnexoRegdan;

/**
 * Classe de manipulação de objetos da classe RegDemandaAnexoRedan.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Fri Jan 27 07:54:28 BRST 2006
 *
 */
public class RegDemandaAnexoDao extends Dao {
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public RegDemandaAnexoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Busca os anexos de um demanda de codigo codRegd
	 * @param codRegd
	 * @return
	 * @throws ECARException
	 */
	public List getAnexos(Long codRegd) throws ECARException {
		
		List<RegDemandaAnexoRegdan> anexos  = new ArrayList<RegDemandaAnexoRegdan>();
		
		
		Criteria c = session.createCriteria(RegDemandaAnexoRegdan.class);
		
		c.add(Restrictions.eq("regDemanda.codRegd", codRegd));
		
		return  c.list();
	}
	

	/**
	 * Ordenar a lista de anexos
	 * 		aptCampo : descricao ou srcAnexo(caminho do anexo)
	 * 		aptOrden : asc ou desc.
	 * @param aptCampo - Campo pelo qual a lista será ordenada.
	 * @param aptOrdem - Ordem pela qual a lista será ordenada.
	 * @param lista - lista que será ordenada.
	 */
	public void ordenar (String aptCampo, String aptOrdem, List lista) {
		//testar cada string para realizar a ordenação
		if ("descricao".equals(aptCampo)) {
			if ("asc".equals(aptOrdem)) {
				Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        		    return ( (RegDemandaAnexoRegdan) o1 ).getDescricao().compareToIgnoreCase(( (RegDemandaAnexoRegdan) o2 ).getDescricao());
		        		}
		    		} );
    		} else {
    			Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        			return - ( (RegDemandaAnexoRegdan) o1 ).getDescricao().compareToIgnoreCase(( (RegDemandaAnexoRegdan) o2 ).getDescricao());
		        		}
		    		} );
    		}
		}
		if ("srcAnexo".equals(aptCampo)) {
			if ("asc".equals(aptOrdem)) {
				Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        			RegDemandaAnexoRegdan regA1 = (RegDemandaAnexoRegdan) o1;
		        			RegDemandaAnexoRegdan regA2 = (RegDemandaAnexoRegdan) o2;
		        			//Quando a data está nula, foi utilizado um artifício para não ocorrer Exception
		        			if (regA1.getSrcAnexo() != null) {
		        				if (regA2.getSrcAnexo() != null) {
		        					return regA1.getSrcAnexo().compareTo( regA2.getSrcAnexo());
		        				} else {
		        					return "a".compareTo("");
		        				}
		        			} else {
		        				if (regA2.getSrcAnexo() != null) {
		        					return "".compareTo("a");
		        				} else {
		        					return "".compareTo("");
		        				}
		        			}
		        		}
		    		} );
    		} else {
    			Collections.sort(lista,
    				new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        			RegDemandaAnexoRegdan regA1 = (RegDemandaAnexoRegdan) o1;
		        			RegDemandaAnexoRegdan regA2 = (RegDemandaAnexoRegdan) o2;
		        			//Quando a data está nula, foi utilizado um artifício para não ocorrer Exception
		        			if (regA1.getSrcAnexo() != null) {
		        				if (regA2.getSrcAnexo() != null) {
		        					return - (regA1.getSrcAnexo().compareTo( regA2.getSrcAnexo()));
		        				} else {
		        					return - ("a".compareTo(""));
		        				}
		        			} else {
		        				if (regA2.getSrcAnexo() != null) {
		        					return - ("".compareTo("a"));
		        				} else {
		        					return - ("".compareTo(""));
		        				}
		        			}
		        		}
		    		} );
    		}
		}
	}
	
}
