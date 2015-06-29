package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.PesquisaGrpAcesso;
import ecar.pojo.SisAtributoSatb;
import ecar.util.Dominios;

/**
 *
 * @author 70744416353
 */
public class PesquisaGrpAcessoDao
    extends Dao
{
    /**
     *
     */
    public PesquisaGrpAcessoDao(  )
    {
        super(  );
    }

    /**
     *
     * @param satb
     * @return
     * @throws ECARException
     */
    public PesquisaGrpAcesso getPesquisaGrpAcesso( SisAtributoSatb satb )
                                           throws ECARException
    {
        try
        {
            StringBuilder query =
                new StringBuilder( "select pesquisas from PesquisaGrpAcesso as pesquisas " ).append( " where pesquisas.codSatb = " +
                                                                                                     satb.getCodSatb(  ) );
            Query q = this.getSession(  ).createQuery( query.toString(  ) );

            q.setMaxResults( 1 );

            Object pesquisa = q.uniqueResult(  );

            if ( pesquisa != null )
            {
                return (PesquisaGrpAcesso) pesquisa;
            } else
            {
                return null;
            }
        } catch ( Exception e )
        {
            this.logger.error( e );
            throw new ECARException( e );
        }
    }

    /**
     *
     * @param gruposAcesso
     * @return
     * @throws ECARException
     */
    public List getListPesquisaGrupos( Set gruposAcesso )
                               throws ECARException
    {
        try
        {
            StringBuilder query =
                new StringBuilder( "select pesquisas from PesquisaGrpAcesso as pesquisas " ).append( " where pesquisas.codSatb in (:gruposAcesso)" );

            Query q = this.getSession(  ).createQuery( query.toString(  ) );

            List lCodSatb = new ArrayList(  );
            Iterator itGrupos = gruposAcesso.iterator(  );

            while ( itGrupos.hasNext(  ) )
            {
                SisAtributoSatb grupoAcesso = (SisAtributoSatb) itGrupos.next(  );
                lCodSatb.add( grupoAcesso.getCodSatb(  ) );
            }

            if ( ! lCodSatb.isEmpty(  ) )
            {
                q.setParameterList( "gruposAcesso", lCodSatb );
            }

            List lPesquisaGrupos = q.list(  );

            if ( lPesquisaGrupos != null )
            {
                return lPesquisaGrupos;
            } else
            {
                return null;
            }
        } catch ( Exception e )
        {
            this.logger.error( e );
            throw new ECARException( e );
        }
    }

    private void inicializaValoresNegativos( PesquisaGrpAcesso configPesquisaGrpAcesso )
    {
        configPesquisaGrpAcesso.setIndPodeVerGeral( Dominios.NAO );
        configPesquisaGrpAcesso.setIndPodeVerMinhaVisao( Dominios.NAO );
        configPesquisaGrpAcesso.setIndPodeVerPendencias( Dominios.NAO );
        configPesquisaGrpAcesso.setIndPodeVerPersonalizado( Dominios.NAO );
        configPesquisaGrpAcesso.setIndPodeCriarPesquisaSistema( Dominios.NAO );
        configPesquisaGrpAcesso.setIndPodeCriarPesquisaUsuario( Dominios.NAO );
    }

    /**
     * Devolve um objeto contendo as informa��o de quais tipo de Filtro o usu�rio
     * tem permiss�o de visualizar.
     *
     * Inicializa negando a visualiza��o dos filtro e a medida que os grupos de Acesso
     * tem a permiss�o vai liberando a sua visualiza��o.
     *
     * @param gruposAcesso
     * @return
     * @throws ECARException
     */
    public PesquisaGrpAcesso getConfiguracaoPesquisaGrupoAcesso( Set gruposAcesso )
        throws ECARException
    {
        PesquisaGrpAcesso configPesquisaGrpAcesso = new PesquisaGrpAcesso(  );
        List lPesquisaGrupos = this.getListPesquisaGrupos( gruposAcesso );
        Iterator itPesquisaGrupos = lPesquisaGrupos.iterator(  );

        this.inicializaValoresNegativos( configPesquisaGrpAcesso );

        while ( itPesquisaGrupos.hasNext(  ) )
        {
            PesquisaGrpAcesso configPesquisaAux = (PesquisaGrpAcesso) itPesquisaGrupos.next(  );

            if ( configPesquisaAux.getIndPodeVerGeral(  ).equals( "S" ) )
            {
                configPesquisaGrpAcesso.setIndPodeVerGeral( "S" );
            }

            if ( configPesquisaAux.getIndPodeVerMinhaVisao(  ).equals( "S" ) )
            {
                configPesquisaGrpAcesso.setIndPodeVerMinhaVisao( "S" );
            }

            if ( configPesquisaAux.getIndPodeVerPendencias(  ).equals( "S" ) )
            {
                configPesquisaGrpAcesso.setIndPodeVerPendencias( "S" );
            }

            if ( configPesquisaAux.getIndPodeVerPersonalizado(  ).equals( "S" ) )
            {
                configPesquisaGrpAcesso.setIndPodeVerPersonalizado( "S" );
            }

            if ( configPesquisaAux.getIndPodeCriarPesquisaSistema(  ).equals( "S" ) )
            {
                configPesquisaGrpAcesso.setIndPodeCriarPesquisaSistema( "S" );
            }

            if ( configPesquisaAux.getIndPodeCriarPesquisaUsuario(  ).equals( "S" ) )
            {
                configPesquisaGrpAcesso.setIndPodeCriarPesquisaUsuario( "S" );
            }
        }

        return configPesquisaGrpAcesso;
    }
}
