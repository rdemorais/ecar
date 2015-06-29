/*
 * Created on 13/09/2004
 *
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.TipoParticipacaoTpp;

/**
 * @author garten
 *
 */
public class TipoParticipacaoDao
    extends Dao
{
    /**
     * Construtor. Chama o Session factory do Hibernate
     *
     * @param request
     */
    public TipoParticipacaoDao( HttpServletRequest request )
    {
        super(  );
        this.request = request;
    }

    /**
     * Verifica depois exclui
     * @param tpp
     * @throws ECARException
     */
    public void excluir( TipoParticipacaoTpp tpp )
                 throws ECARException
    {
        try
        {
            if ( this.contar( tpp.getItemEstrutEntidadeIettes(  ) ) > 0 )
            {
                throw new ECARException( "tipoParticipacao.exclusao.erro.itemEstrutEntidade" );
            } else{
                super.excluir( tpp );
            }
        } catch ( ECARException e )
        {
            this.logger.error( e );
            throw e;
        }
    }
}
