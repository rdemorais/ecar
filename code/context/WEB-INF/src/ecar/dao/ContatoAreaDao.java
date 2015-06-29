/*
 * Created on 11/05/2005
 *
 */
package ecar.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.ContatoAreaCtta;

/**
 * @author felipe
 *
 */
public class ContatoAreaDao
    extends Dao
{
    /**
     *
     * @param request
     */
    public ContatoAreaDao( HttpServletRequest request )
    {
        super(  );
        this.request = request;
    }

    /**
     *
     * @param contato
     * @throws ECARException
     */
    public void salvar( ContatoAreaCtta contato )
                throws ECARException
    {
        contato.setDataInclusaoCtta( Data.getDataAtual(  ) );
        super.salvar( contato );
    }

    /**
    * A partir de dados passados por request popula um objeto Contato Area
    * @param contato
     * @param request
     * @param recuperarParametrosComoString indica se ir� recuperar dados nulos como String vazia
    * @throws ECARException
    */
    public void setContatoArea( ContatoAreaCtta contato, HttpServletRequest request,
                                boolean recuperarParametrosComoString )
                        throws ECARException
    {
        try
        {
            if ( recuperarParametrosComoString )
            {
                contato.setNomeCtta( Pagina.getParamStr( request, "nomeCtta" ) );
                contato.setAssuntoRetornoCtta( Pagina.getParamStr( request, "assuntoRetornoCtta" ) );
                contato.setTextoRetornoCtta( Pagina.getParamStr( request, "textoRetornoCtta" ) );
                contato.setIndEmailRespostaCtta( Pagina.getParamStr( request, "indEmailRespostaCtta" ) );
                contato.setIndLogadoCtta( Pagina.getParamStr( request, "indLogadoCtta" ) );
                contato.setIndAtivoCtta( Pagina.getParamStr( request, "indAtivoCtta" ) );
            } else
            {
                contato.setNomeCtta( Pagina.getParam( request, "nomeCtta" ) );
                contato.setAssuntoRetornoCtta( Pagina.getParam( request, "assuntoRetornoCtta" ) );
                contato.setTextoRetornoCtta( Pagina.getParam( request, "textoRetornoCtta" ) );
                contato.setIndEmailRespostaCtta( Pagina.getParam( request, "indEmailRespostaCtta" ) );
                contato.setIndLogadoCtta( Pagina.getParam( request, "indLogadoCtta" ) );
                contato.setIndAtivoCtta( Pagina.getParamStr( request, "indAtivoCtta" ) );
            }
        } catch ( Exception e )
        {
            this.logger.error( e );
            throw new ECARException( e );
        }
    }

    /**
     * Exclui uma �rea de contato, verificando antes se ela possui rela��o com outras tabelas. Neste caso, n�o permite
     * exclus�o
     * @param contato
     * @throws ECARException
     */
    public void excluir( ContatoAreaCtta contato )
                 throws ECARException
    {
        try
        {
            boolean excluir = true;

            if ( contar( contato.getContatoMailCttms(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "contatoArea.exclusao.erro.contatoMailCttms" );
            }

            if ( excluir )
            {
                super.excluir( contato );
            }
        } catch ( ECARException e )
        {
            this.logger.error( e );
            throw e;
        }
    }

    /**
     * Devolve uma lista com as �reas de contato ativas
     * @author n/c
     * @return list
     * @throws ECARException
     */
    public List getContatoAreaAtivas(  )
                              throws ECARException
    {
        ContatoAreaCtta contato = new ContatoAreaCtta(  );

        contato.setIndAtivoCtta( "S" );

        return this.pesquisar( contato, null );
    }
}
