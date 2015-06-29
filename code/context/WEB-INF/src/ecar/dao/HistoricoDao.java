package ecar.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.historico.Historico;
import ecar.pojo.HistoricoConfig;
import ecar.pojo.HistoricoIettH;
import ecar.pojo.ItemEstrutUploadIettup;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.historico.HistoricoItemEstruturaIett;

/**
 * @author
 * @since
 */
public class HistoricoDao
    extends Dao
{
    /**
     *
     * @param request
     */
    public HistoricoDao( HttpServletRequest request )
    {
        super(  );
        this.request = request;
    }

    /**
     *
     * @param request
     * @param objetos
     * @throws ECARException
     */
    @SuppressWarnings( "unchecked" )
    public void alterar( HttpServletRequest request, ArrayList objetos )
                 throws ECARException
    {
        Transaction tx = null;

        try
        {
            //Altera��o para colocar as altera��es da tela de abas em uma �nica transa��o
            //ArrayList<Historico> objetos = new ArrayList<Historico>();
            //super.inicializarLogBean();
            //tx = session.beginTransaction();

            /* Passar por todas as abas e atualizar */
            List<HistoricoConfig> lista =
                (List<HistoricoConfig>) this.listar( HistoricoConfig.class,
                                                     new String[] { "codHistorico", "asc" } );

            for ( HistoricoConfig historico : lista )
            {
                //O request deve passar os parametros no formato [nome]+[codHistorico]
                historico.setIconeHistorico( Pagina.getParamStr( request,
                                                                 "iconeLinkHistorico" +
                                                                 historico.getCodHistorico(  ).toString(  ) ) );

                historico.setCorHistorico( Pagina.getParamStr( request,
                                                               "corHistorico" +
                                                               historico.getCodHistorico(  ).toString(  ) ) );

                session.update( historico );
                objetos.add( historico );
            }

            //Altera��o para colocar as altera��es da tela de abas em uma �nica transa��o
            //tx.commit();

            /* log */
//			if (super.logBean != null) {
//				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
//				super.logBean.setOperacao("ALT");
//
//				for (Iterator itObj = objetos.iterator(); itObj.hasNext();) {
//					super.logBean.setObj(itObj.next());
//					super.loggerAuditoria.info(logBean.toString());
//				}
//			}
        } catch ( HibernateException e )
        {
            if ( tx != null )
            {
                try
                {
//					tx.rollback();
                } catch ( HibernateException r )
                {
                    this.logger.error( r );
                    throw new ECARException( "erro.hibernateException" );
                }
            }

            this.logger.error( e );
            throw new ECARException( "erro.hibernateException" );
        } catch ( ECARException e )
        {
            if ( tx != null )
            {
                try
                {
                    //				tx.rollback();
                } catch ( HibernateException r )
                {
                    this.logger.error( r );
                    throw new ECARException( "erro.hibernateException" );
                }
            }

            this.logger.error( e );
            throw e;
        }
    }

    /**
    * Retorna o caminho da imagem personalizada pelo usu�rio do indicador/resultado. <br>
    * Na falta da imagem personalizada, retorna NULL. <br>
    * @author
    * @since
    * @version
     * @param historico
    * @return String
    * @throws ECARException
    */
    public String getImagemPersonalizadaHistorico( HistoricoConfig historico )
                                           throws ECARException
    {
        String path = null;
        ConfiguracaoDao cdao = new ConfiguracaoDao( null );

        if ( ( historico != null ) &&
                 ( historico.getIconeHistorico(  ) != null ) &&
                 ! historico.getIconeHistorico(  ).equals( "" ) )
        {
            path = cdao.getConfiguracao(  ).getRaizUpload(  ) + historico.getIconeHistorico(  );
            //path = "/home/02759475484/pessoal" +  historico.getIconeHistorico(  );

            File file = new File( path );

            if ( ! file.exists(  ) )
            {
                path = null;
            }
        }

        return path;
    } // fim getImagemPersonalizadaIndResul()

    /**
     * Efetua a carga inicial do hist�rico
     * @throws ECARException
     */
    public void cargaInicialHistorico(  )
                               throws ECARException
    {
        Historico historico = new Historico<HistoricoItemEstruturaIett, ItemEstruturaIett>(  )
            {
            };

        ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao( request );
        PontoCriticoDao pontoCriticoDao = new PontoCriticoDao( request );

        try
        {
            itemEstruturaDao.cargaInicialHistorico(  );
            pontoCriticoDao.cargaInicialHistorico(  );
        } catch ( ECARException e )
        {
            this.logger.error( e );
            throw new ECARException( e );
        }
    }

    /**
     *
     * @param classe
     * @return
     */
    public List<Long> listaIdObjetoSerializado( Class classe )
    {
        Query q =
            this.session.createQuery( "select distinct historicoXml.idObjetoSerializado from HistoricoXml historicoXml where historico = :historico" );
        q.setString( "historico",
                     classe.getName(  ) );

        return q.list(  );
    }

    /**
     *
     * @param classe
     * @return
     */
    public Integer getQuantidadeLinhasHistorico( Class classe )
    {
        Query q =
            this.session.createQuery( "select count(historicoXml) from HistoricoXml historicoXml where historico = :historico" );
        q.setString( "historico",
                     classe.getName(  ) );

        return (Integer) q.uniqueResult(  );
    }
    
 
}
