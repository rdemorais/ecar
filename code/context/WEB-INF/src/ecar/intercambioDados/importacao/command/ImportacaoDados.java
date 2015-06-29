package ecar.intercambioDados.importacao.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import ecar.exception.ECARException;
import ecar.intercambioDados.IBusinessObject;
import ecar.pojo.UsuarioUsu;

public abstract class ImportacaoDados {

	public abstract String importar(IBusinessObject objetoNegocio, Transaction tx, HttpServletRequest request, UsuarioUsu usuarioLogado, Logger logger) throws ECARException;
}
