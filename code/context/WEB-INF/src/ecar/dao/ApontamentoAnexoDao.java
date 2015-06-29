package ecar.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.ApontamentoAnexo;
import ecar.pojo.RegApontamentoRegda;

/**
 *
 * @author 70744416353
 */
public class ApontamentoAnexoDao
    extends Dao
{
    /**
     * Construtor. Chama o Session factory do Hibernate
     *
     * @param request
     */
    public ApontamentoAnexoDao( HttpServletRequest request )
    {
        super(  );
        this.request = request;
    }

    /**
    * Retorna uma lista de ApontamentoAnexo.
    *
    * @param regApontamentoRegda
     * @return List<ApontamentoAnexo>
     * @throws HibernateException
     * @throws ECARException
    */
    public List<ApontamentoAnexo> getAnexosApontamentoRegda( RegApontamentoRegda regApontamentoRegda )
                                                     throws HibernateException, ECARException
    {
        List<ApontamentoAnexo> retorno = null;

        try
        {
            StringBuilder query =
                new StringBuilder( " select apontamentoAnexo from ApontamentoAnexo as apontamentoAnexo " ).append( " join apontamentoAnexo.regApontamentoRegda as regApontamentoRegda " )
                                                                                                          .append( " where " )
                                                                                                          .append( " apontamentoAnexo.regApontamentoRegda.codRegda = regApontamentoRegda.codRegda " )
                                                                                                          .append( " and apontamentoAnexo.regApontamentoRegda.codRegda = :codRegda " );

            Query q = this.getSession(  ).createQuery( query.toString(  ) );

            q.setLong( "codRegda",
                       regApontamentoRegda.getCodRegda(  ).longValue(  ) );

            retorno = q.list(  );
        } catch ( HibernateException e )
        {
            this.logger.error( e );
            throw new ECARException( e );
        }

        return retorno;
    }
}
