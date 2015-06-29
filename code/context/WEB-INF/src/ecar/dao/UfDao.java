/*
 * Created on 13/09/2004
 * 
 */
package ecar.dao;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.Uf;

/**
 * @author garten
 *
 */
public class UfDao extends Dao {
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public UfDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	
	/**
	 * Salvar sobrescrito. Testa se já existe uma UF com a mesma sigla.
	 * @param uf
	 * @throws ECARException
	 */
	public void salvar (Uf uf) throws ECARException {
		try {
			if (pesquisarDuplos(uf, new String[] {"descricaoUf"}, "codUf").size() > 0)
			    throw new ECARException("uf.validacao.registroDuplicado");
		    
		    /* tentar buscar */
			if (buscar(Uf.class, uf.getCodUf()) != null)
				throw new ECARException("uf.inclusao.jaExiste");
			
		} catch (ECARException e) {
			this.logger.error(e);
			if (e.getMessageKey().equalsIgnoreCase("erro.objectNotFound")) {
				super.salvar(uf);
			} else
			    /* joga para frente a uf.inclusao.jaExiste */
				throw e;
		}
	}
	

	/**
	 * Verifica as regras de negocio de UF antes de excluir
	 * @param uf
	 * @throws ECARException
	 */
	public void excluir (Uf uf) throws ECARException {
		try {
		    /* testa dependecia com tbela usuario */
			if (this.contar(uf.getUsuarioUsusByComercUfUsu()) > 0 ||
				this.contar(uf.getUsuarioUsusByResidUfUsu()) > 0) {
				throw new ECARException("uf.exclusao.erro.usuarioUsu");
			/* testa dependencia com tabela empresa */
			} else if (this.contar(uf.getEmpresaEmps()) > 0) {
			    throw new ECARException("uf.exclusao.erro.empresaEmp");
			} else if (this.contar(uf.getEnderecoEnds()) > 0) {
			    throw new ECARException("uf.exclusao.erro.enderecoEnd");
			}else
				super.excluir(uf);
		} catch (ECARException e) {
			this.logger.error(e);
			throw e;
		}
	}
	
	/**
	 * Verifica duplicação depois altera
	 * @param uf
	 * @throws ECARException
	 */
	public void alterar(Uf uf) throws ECARException {
		if (pesquisarDuplos(uf, new String[] {"descricaoUf"}, "codUf").size() > 0)
		    throw new ECARException("uf.validacao.registroDuplicado");
		super.alterar(uf);
	}
	
	/**
	 * 
	 * @return String
	 * @throws ECARException
	 */
    public String getUfJS() throws ECARException {
    	List listaUf = super.listar(Uf.class, new String[] {"descricaoUf","asc"});
    	Iterator itUf = listaUf.iterator();
    	StringBuilder javaScript = new StringBuilder("function getUf()\n{\n");
    							javaScript.append("\tconteudo = \"\";\n");
    	while (itUf.hasNext())
    	{
    		Uf uf = (Uf)itUf.next();
    		javaScript.append("\tconteudo += \"\\t\\t\\t\\t<option value = \\\"")
    				  .append(uf.getCodUf().toString()).append("\\\">")
    				  .append(uf.getDescricaoUf()).append("\";\n")
    				  .append("\tconteudo += \"</option>\\n\"\n");
    	}
    	javaScript.append("\treturn(conteudo);\n}");
    	return javaScript.toString();
    }	
	
}