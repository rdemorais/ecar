/*
 * Created on 10/05/2005
 *
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.DestaqueAreaDtqa;

/**
 * @author felipe
 *
 */
public class DestaqueAreaDao
    extends Dao
{
    /**
     *
     * @param request
     */
    public DestaqueAreaDao( HttpServletRequest request )
    {
        super(  );
        this.request = request;
    }

    /**
     * A partir de dados passados por request popula um objeto Destaque Area
     * @param destaque
     * @param request
     * @param recuperarParametrosComoString indica se ir� recuperar dados nulos como String vazia
     * @throws ECARException
     */
    public void setDestaqueArea( DestaqueAreaDtqa destaque, HttpServletRequest request,
                                 boolean recuperarParametrosComoString )
                         throws ECARException
    {
        try
        {
            if ( ! "".equals( Pagina.getParamStr( request, "qtdColunasDtqa" ) ) )
            {
                destaque.setQtdColunasDtqa( Integer.valueOf( Pagina.getParamStr( request, "qtdColunasDtqa" ) ) );
            }

            if ( recuperarParametrosComoString )
            {
                destaque.setIdentificacaoDtqa( Pagina.getParamStr( request, "identificacaoDtqa" ) );
                destaque.setNomeDtqa( Pagina.getParamStr( request, "nomeDtqa" ) );
                destaque.setDescricaoDtqa( Pagina.getParamStr( request, "descricaoDtqa" ) );
            } else
            {
                destaque.setIdentificacaoDtqa( Pagina.getParam( request, "identificacaoDtqa" ) );
                destaque.setNomeDtqa( Pagina.getParam( request, "nomeDtqa" ) );
                destaque.setDescricaoDtqa( Pagina.getParam( request, "descricaoDtqa" ) );
            }
        } catch ( Exception e )
        {
            this.logger.error( e );
            throw new ECARException( e );
        }
    }

    /**
     * Exclui uma �rea de destaque, verificando antes se ela possui rela��o com outras tabelas. Neste caso, n�o permite
     * exclus�o
     * @param destaque
     * @throws ECARException
     */
    public void excluir( DestaqueAreaDtqa destaque )
                 throws ECARException
    {
        try
        {
            boolean excluir = true;

            if ( contar( destaque.getDestaqueSubAreaDtqsas(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "destaqueArea.exclusao.erro.subAreaDestaque" );
            }

            if ( excluir )
            {
                super.excluir( destaque );
            }
        } catch ( ECARException e )
        {
            this.logger.error( e );
            throw e;
        }
    }
}
