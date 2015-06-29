package ecar.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;

import ecar.exception.ECARException;
import ecar.pojo.RegApontamentoRegda;

/**
 * Classe de manipulação de objetos da classe AtributoAtt.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Fri Jan 27 07:54:28 BRST 2006
 *
 */
public class RegApontamentoDao extends Dao {
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public RegApontamentoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Classifica e ordena a lista conforme os parâmetros passados.
	 * 		aptCampo : infoRegda, dataRegda, usuarioUsu.
	 * 		aptOrden : asc ou desc.
	 * @param aptCampo - Campo pelo qual a lista será ordenada.
	 * @param aptOrdem - Ordem pela qual a lista será ordenada.
	 * @param lista - lista que será ordenada.
	 */
	public void classificarOrdenacao (String aptCampo, String aptOrdem, List lista) {
		//testar cada string para realizar a ordenação
		if ("infoRegda".equals(aptCampo)) {
			if ("asc".equals(aptOrdem)) {
				Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        		    return ( (RegApontamentoRegda) o1 ).getInfoRegda().compareToIgnoreCase(( (RegApontamentoRegda) o2 ).getInfoRegda());
		        		}
		    		} );
    		} else {
    			Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        			return - ( (RegApontamentoRegda) o1 ).getInfoRegda().compareToIgnoreCase(( (RegApontamentoRegda) o2 ).getInfoRegda());
		        		}
		    		} );
    		}
		}
		if ("dataRegda".equals(aptCampo)) {
			if ("asc".equals(aptOrdem)) {
				Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        			RegApontamentoRegda regA1 = (RegApontamentoRegda) o1;
		        			RegApontamentoRegda regA2 = (RegApontamentoRegda) o2;
		        			//Quando a data está nula, foi utilizado um artifício para não ocorrer Exception
		        			if (regA1.getDataRegda() != null) {
		        				if (regA2.getDataRegda() != null) {
		        					return regA1.getDataRegda().compareTo( regA2.getDataRegda() );
		        				} else {
		        					return "a".compareTo("");
		        				}
		        			} else {
		        				if (regA2.getDataRegda() != null) {
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
		        			RegApontamentoRegda regA1 = (RegApontamentoRegda) o1;
		        			RegApontamentoRegda regA2 = (RegApontamentoRegda) o2;
		        			//Quando a data está nula, foi utilizado um artifício para não ocorrer Exception
		        			if (regA1.getDataRegda() != null) {
		        				if (regA2.getDataRegda() != null) {
		        					return - (regA1.getDataRegda().compareTo( regA2.getDataRegda() ));
		        				} else {
		        					return - ("a".compareTo(""));
		        				}
		        			} else {
		        				if (regA2.getDataRegda() != null) {
		        					return - ("".compareTo("a"));
		        				} else {
		        					return - ("".compareTo(""));
		        				}
		        			}
		        		}
		    		} );
    		}
		}
		if ("usuarioUsu".equals(aptCampo)) {
			if ("asc".equals(aptOrdem)) {
				Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        			return ( (RegApontamentoRegda) o1 ).getUsuarioUsu().getNomeUsuSent().compareToIgnoreCase(( (RegApontamentoRegda) o2 ).getUsuarioUsu().getNomeUsuSent());
		        		}
		    		} );
    		} else {
    			Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        			return -( (RegApontamentoRegda) o1 ).getUsuarioUsu().getNomeUsuSent().compareToIgnoreCase(( (RegApontamentoRegda) o2 ).getUsuarioUsu().getNomeUsuSent());
		        		}
		    		} );
    		}
		}
	}
	
	/**
	 * Verifica depois exclui
	 * 
	 * @param codigosParaExcluir
	 * @throws ECARException
	 */
	public void excluir(String[] codigosParaExcluir) throws ECARException {
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        for (int i = 0; i < codigosParaExcluir.length; i++) {
	            RegApontamentoRegda regApontamento = (RegApontamentoRegda) buscar(RegApontamentoRegda.class, Long.valueOf(codigosParaExcluir[i]));
	            session.delete(regApontamento);
				objetos.add(regApontamento);
	        }
			
			tx.commit();
	
			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("EXC");
				Iterator itObj = objetos.iterator();
	
				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
		} catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
	       this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
	}
}
