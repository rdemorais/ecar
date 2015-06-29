package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

/**
 * Classe de manipula��o de objetos da classe AtributoAtt.
 *
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Fri Jan 27 07:54:28 BRST 2006
 *
 */
public class TelefoneDao
    extends Dao
{
    /**
     * Construtor. Chama o Session factory do Hibernate
     *
     * @param request
     */
    public TelefoneDao( HttpServletRequest request )
    {
        super(  );
        this.request = request;
    }
}
