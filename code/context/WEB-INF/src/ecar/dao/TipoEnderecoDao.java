package ecar.dao;


import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.TipoEnderecoTend;

/**
 * Classe de manipulação de objetos da classe AtributoAtt.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Fri Jan 27 07:54:28 BRST 2006
 *
 */
public class TipoEnderecoDao extends Dao {
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public TipoEnderecoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * 
	 * @return String
	 * @throws ECARException
	 */
    public String getTipoEnderecoJS() throws ECARException {
    	List listaTpEnd = super.listar(TipoEnderecoTend.class, new String[] {"descricaoTpend","asc"});
    	Iterator itTpEnd = listaTpEnd.iterator();
    	StringBuilder javaScript = new StringBuilder("function getTipoEndereco(n)\n{\n");
    	javaScript.append("\tconteudo = \"\";\n");
    	while (itTpEnd.hasNext())
    	{
    		TipoEnderecoTend tpEnd = (TipoEnderecoTend)itTpEnd.next();
    		javaScript.append("\tconteudo += \"\\t\\t\\t\\t<option value = \\\"")
    				  .append(tpEnd.getCodTpend().toString() )
    				  .append("\\\">")
    				  .append(tpEnd.getDescricaoTpend())
    				  .append("\";\n")
    				  .append("\tconteudo += \"</option>\\n\"\n");
    	}
    	javaScript.append("\treturn(conteudo);\n}");
    	return javaScript.toString();
    }
}
