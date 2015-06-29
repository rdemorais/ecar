package ecar.dao.permissao.link;

import javax.servlet.ServletContext;

import ecar.exception.ECARException;
import ecar.exception.PermissaoAcessoLinkException;
import ecar.login.SegurancaECAR;

/*
 * Created on 15/12/2008
 *
 */

/**
 *
 * @author 70744416353
 */
public interface PermissaoAcessoLinkCommand
{
    /**
     *
     * @param evento
     * @param parametros
     * @param seguranca
     * @param servletContext
     * @return
     * @throws ECARException
     */
    public abstract String execute( String evento, String parametros, SegurancaECAR seguranca,
                                    ServletContext servletContext )
                            throws ECARException;

    /**
     *
     * @param parametros
     * @param seguranca
     * @throws PermissaoAcessoLinkException
     */
    public abstract void permissaoAcesso( String parametros, SegurancaECAR seguranca )
                                  throws PermissaoAcessoLinkException;
}
