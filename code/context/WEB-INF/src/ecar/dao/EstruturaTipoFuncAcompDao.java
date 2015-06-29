/*
 * Criado em 01/12/2004
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.permissao.ControlePermissao;
import ecar.pojo.EstrutTpFuncAcmpEtttfa;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.TipoFuncAcompTpfa;

/**
 * @author felipev
 *
 */
public class EstruturaTipoFuncAcompDao
    extends Dao
{
    /*private EstruturaDao estruturaDao = null;
    private TipoFuncAcompDao tipoFuncAcompDao = null;
    */

    /**
     * Construtor. Chama o Session factory do Hibernate
     *
     * @param request
     */
    public EstruturaTipoFuncAcompDao( HttpServletRequest request )
    {
        super(  );
        this.request = request;

        /*estruturaDao = new EstruturaDao(this.request);
        tipoFuncAcompDao = new TipoFuncAcompDao(this.request);*/
    }

    /**
     * Controla listas
     *
     * @author n/c
     * @param request
     * @param listaEstrutura
     * @param listaTipoFuncAcomp
     * @throws ECARException
     */
    public void controlaListas( HttpServletRequest request, List listaEstrutura, List listaTipoFuncAcomp )
                        throws ECARException
    {
        if ( Pagina.getParam( request, "estruturaEtt" ) != null )
        {
            listaTipoFuncAcomp.addAll( this.getTipoFuncAcomp( (EstruturaEtt) new EstruturaDao( request ).buscar( 
                                                                                                                 EstruturaEtt.class,
                                                                                                                 Long.valueOf( Pagina.getParam( request,
                                                                                                                                                "estruturaEtt" ) ) ) ) );
        } else
        {
            listaTipoFuncAcomp.addAll( new TipoFuncAcompDao( request ).listar( 
                                                                               TipoFuncAcompTpfa.class,
                                                                               new String[] { "labelTpfa", "asc" } ) );
        }

        if ( Pagina.getParam( request, "tipoFuncAcompTpfa" ) != null )
        {
            listaEstrutura.addAll( this.getEstruturasNaoVirtuais( (TipoFuncAcompTpfa) new TipoFuncAcompDao( request ).buscar( 
                                                                                                                              TipoFuncAcompTpfa.class,
                                                                                                                              Long.valueOf( Pagina.getParam( request,
                                                                                                                                                             "tipoFuncAcompTpfa" ) ) ) ) );
        } else
        {
//		    listaEstrutura.addAll(new EstruturaDao(request).listar(EstruturaEtt.class, new String[]{"nomeEtt", "asc"}));
            EstruturaEtt estruturaPesquisa = new EstruturaEtt(  );
            estruturaPesquisa.setVirtual( false );
            listaEstrutura.addAll( new EstruturaDao( request ).pesquisar( 
                                                                          estruturaPesquisa,
                                                                          new String[] { "nomeEtt", "asc" } ) );
        }
    }

    /**
     *
     * @author n/c
     * @param request
     * @param estruturaTipoFuncAcomp
     * @param usarGetParamStr
     * @throws ECARException
     */
    public void setEstruturaTipoFuncAcomp( HttpServletRequest request, EstrutTpFuncAcmpEtttfa estruturaTipoFuncAcomp,
                                           boolean usarGetParamStr )
                                   throws ECARException
    {
        if ( Pagina.getParam( request, "estruturaEtt" ) != null )
        {
            estruturaTipoFuncAcomp.setEstruturaEtt( (EstruturaEtt) ( 
                                                        new EstruturaDao( request ).buscar( 
                                                                                            EstruturaEtt.class,
                                                                                            Long.valueOf( Pagina.getParam( request,
                                                                                                                           "estruturaEtt" ) ) )
                                                     ) );
        }

        if ( Pagina.getParam( request, "tipoFuncAcompTpfa" ) != null )
        {
            estruturaTipoFuncAcomp.setTipoFuncAcompTpfa( (TipoFuncAcompTpfa) new TipoFuncAcompDao( request ).buscar( 
                                                                                                                     TipoFuncAcompTpfa.class,
                                                                                                                     Long.valueOf( Pagina.getParam( request,
                                                                                                                                                    "tipoFuncAcompTpfa" ) ) ) );
        }

        if ( ! "".equals( Pagina.getParamStr( request, "seqApresentTelaCampoEtttfa" ) ) )
        {
            estruturaTipoFuncAcomp.setSeqApresentTelaCampoEtttfa( Integer.valueOf( Pagina.getParamStr( request,
                                                                                                       "seqApresentTelaCampoEtttfa" ) ) );
        }

        if ( ! "".equals( Pagina.getParamStr( request, "seqApresListagemTelaEtttfa" ) ) )
        {
            estruturaTipoFuncAcomp.setSeqApresListagemTelaEtttfa( Integer.valueOf( Pagina.getParamStr( request,
                                                                                                       "seqApresListagemTelaEtttfa" ) ) );
        } else{
            estruturaTipoFuncAcomp.setSeqApresListagemTelaEtttfa( null );
        }

        if ( ! "".equals( Pagina.getParamStr( request, "larguraListagemTelaEtttfa" ) ) )
        {
            estruturaTipoFuncAcomp.setLarguraListagemTelaEtttfa( Integer.valueOf( Pagina.getParamStr( request,
                                                                                                      "larguraListagemTelaEtttfa" ) ) );
        } else{
            estruturaTipoFuncAcomp.setLarguraListagemTelaEtttfa( null );
        }

        if ( usarGetParamStr )
        {
            //Acrescentado por Milton
            if ( ! Pagina.getParamStr( request, "indListagemImprCompEtttfa" ).equals( "" ) )
            {
                estruturaTipoFuncAcomp.setIndListagemImprCompEtttfa( Pagina.getParamStr( request,
                                                                                         "indListagemImprCompEtttfa" ) );
            } else
            {
                estruturaTipoFuncAcomp.setIndListagemImprCompEtttfa( null );
            }

            if ( ! Pagina.getParamStr( request, "indListagemImprResEtttfa" ).equals( "" ) )
            {
                estruturaTipoFuncAcomp.setIndListagemImprResEtttfa( Pagina.getParamStr( request,
                                                                                        "indListagemImprResEtttfa" ) );
            } else
            {
                estruturaTipoFuncAcomp.setIndListagemImprResEtttfa( null );
            }

            if ( ! Pagina.getParamStr( request, "indListagemTelaEtttfa" ).equals( "" ) )
            {
                estruturaTipoFuncAcomp.setIndListagemTelaEtttfa( Pagina.getParamStr( request, "indListagemTelaEtttfa" ) );
            } else
            {
                estruturaTipoFuncAcomp.setIndListagemTelaEtttfa( null );
            }

            if ( ! Pagina.getParamStr( request, "indRevisao" ).equals( "" ) )
            {
                estruturaTipoFuncAcomp.setIndRevisao( Pagina.getParamStr( request, "indRevisao" ) );
            } else
            {
                estruturaTipoFuncAcomp.setIndRevisao( null );
            }

            if ( ! Pagina.getParamStr( request, "idFiltroEtttfa" ).equals( "" ) )
            {
                estruturaTipoFuncAcomp.setIdFiltroEtttfa( Pagina.getParamStr( request, "idFiltroEtttfa" ) );
            } else
            {
                estruturaTipoFuncAcomp.setIdFiltroEtttfa( null );
            }

            if ( ! Pagina.getParamStr( request, "idPodeBloquearEtttfa" ).equals( "" ) )
            {
                estruturaTipoFuncAcomp.setIdPodeBloquearEtttfa( Pagina.getParamStr( request, "idPodeBloquearEtttfa" ) );
            } else
            {
                estruturaTipoFuncAcomp.setIdPodeBloquearEtttfa( null );
            }

            if ( ! Pagina.getParamStr( request, "indManterProximoNivelEtttfa" ).equals( "" ) )
            {
                estruturaTipoFuncAcomp.setIndManterProximoNivelEtttfa( Pagina.getParamStr( request,
                                                                                           "indManterProximoNivelEtttfa" ) );
            } else
            {
                estruturaTipoFuncAcomp.setIndManterProximoNivelEtttfa( null );
            }
        } else
        {
            estruturaTipoFuncAcomp.setIndListagemImprCompEtttfa( Pagina.getParamOrDefault( request,
                                                                                           "indListagemImprCompEtttfa",
                                                                                           Pagina.NAO ) );
            estruturaTipoFuncAcomp.setIndListagemImprResEtttfa( Pagina.getParamOrDefault( request,
                                                                                          "indListagemImprResEtttfa",
                                                                                          Pagina.NAO ) );
            estruturaTipoFuncAcomp.setIndListagemTelaEtttfa( Pagina.getParamOrDefault( request,
                                                                                       "indListagemTelaEtttfa",
                                                                                       Pagina.NAO ) );
            estruturaTipoFuncAcomp.setIndRevisao( Pagina.getParamOrDefault( request, "indRevisao", Pagina.NAO ) );
            estruturaTipoFuncAcomp.setIdFiltroEtttfa( Pagina.getParamOrDefault( request, "idFiltroEtttfa", Pagina.NAO ) );
            estruturaTipoFuncAcomp.setIdPodeBloquearEtttfa( Pagina.getParamOrDefault( request, "idPodeBloquearEtttfa",
                                                                                      Pagina.NAO ) );
            estruturaTipoFuncAcomp.setIndManterProximoNivelEtttfa( Pagina.getParamOrDefault( request,
                                                                                             "indManterProximoNivelEtttfa",
                                                                                             Pagina.NAO ) );
        }
    }

    /**
     * Devolve uma lista com todas os Tipos de Fun��o de Acompanhamento vinculados a uma estrutura
     *
     *
     * @author n/c
     * @param estrutura
     * @return List de TipoFuncAcompTpfa
     */
    public List getTipoFuncAcomp( EstruturaEtt estrutura )
    {
        List retorno = new ArrayList(  );

        if ( estrutura.getEstrutTpFuncAcmpEtttfas(  ) != null )
        {
            Iterator it = estrutura.getEstrutTpFuncAcmpEtttfas(  ).iterator(  );

            while ( it.hasNext(  ) )
            {
                EstrutTpFuncAcmpEtttfa estruturaTipoFuncAcomp = (EstrutTpFuncAcmpEtttfa) it.next(  );
                retorno.add( estruturaTipoFuncAcomp.getTipoFuncAcompTpfa(  ) );
            }
        }

        return retorno;
    }

    /**
     * Devolve uma lista com todas os Tipos de Fun��o de Acompanhamento vinculados a uma estrutura que emitem posicao.
     *
     *
     * @author n/c
     * @param estrutura
     * @return List de TipoFuncAcompTpfa
     */
    public List getTipoFuncAcompEmitePosicao( EstruturaEtt estrutura )
    {
        List retorno = new ArrayList(  );

        if ( estrutura.getEstrutTpFuncAcmpEtttfas(  ) != null )
        {
            Iterator it = estrutura.getEstrutTpFuncAcmpEtttfas(  ).iterator(  );

            while ( it.hasNext(  ) )
            {
                EstrutTpFuncAcmpEtttfa estruturaTipoFuncAcomp = (EstrutTpFuncAcmpEtttfa) it.next(  );

                if ( "S".equals( estruturaTipoFuncAcomp.getTipoFuncAcompTpfa(  ).getIndEmitePosicaoTpfa(  ) ) )
                {
                    retorno.add( estruturaTipoFuncAcomp.getTipoFuncAcompTpfa(  ) );
                }
            }
        }

        return retorno;
    }

    /**
     * Devolve uma lista com todas as estruturas vinculadas a um Tipo de Fun��o de Acompanhamento
     *
     *
     * @author n/c
     * @param tipoFuncaoAcomp
     * @return List de EstruturaEtt
     */
    public List getEstruturas( TipoFuncAcompTpfa tipoFuncaoAcomp )
    {
        List retorno = new ArrayList(  );

        if ( tipoFuncaoAcomp.getEstrutTpFuncAcmpEtttfas(  ) != null )
        {
            Iterator it = tipoFuncaoAcomp.getEstrutTpFuncAcmpEtttfas(  ).iterator(  );

            while ( it.hasNext(  ) )
            {
                EstrutTpFuncAcmpEtttfa estruturaTipoFuncAcomp = (EstrutTpFuncAcmpEtttfa) it.next(  );
                retorno.add( estruturaTipoFuncAcomp.getEstruturaEtt(  ) );
            }
        }

        return retorno;
    }

    /**
     * Devolve uma lista com todas as estruturas n�o virtuais vinculadas a uma fun��o
     * @param tipoFuncaoAcomp
     * @return List de FuncaoFun
     */
    public List getEstruturasNaoVirtuais( TipoFuncAcompTpfa tipoFuncaoAcomp )
    {
        List retorno = new ArrayList(  );

        if ( tipoFuncaoAcomp.getEstrutTpFuncAcmpEtttfas(  ) != null )
        {
            Iterator it = tipoFuncaoAcomp.getEstrutTpFuncAcmpEtttfas(  ).iterator(  );

            while ( it.hasNext(  ) )
            {
                EstrutTpFuncAcmpEtttfa estruturaTipoFuncAcomp = (EstrutTpFuncAcmpEtttfa) it.next(  );

                if ( ! estruturaTipoFuncAcomp.getEstruturaEtt(  ).isVirtual(  ) )
                {
                    retorno.add( estruturaTipoFuncAcomp.getEstruturaEtt(  ) );
                }
            }
        }

        return retorno;
    }

    /**
     * Configura as funcoes de acompanhamento com permissao  permiss�o de editar o item mesmo quando o item estiver com o plenejamento bloqueado
     * Adiquire as informacoes do request
     *
     * @return  Conjunto com a lista de tipo de acompanhamento setada na interface
     */
    public Set<TipoFuncAcompTpfa> setLibTipoFuncAcompTpfa(  )
    {
        Set<TipoFuncAcompTpfa> set = new HashSet<TipoFuncAcompTpfa>(  );

        if ( request.getParameterValues( "limbTipoFuncAcompTpfa" ) != null )
        {
            String[] strLibTipoFuncAcomp = request.getParameterValues( "limbTipoFuncAcompTpfa" );
            Long codTpfa = null; //[] = new Long[strEntidades.length];

            try
            {
                for ( int i = 0; i < strLibTipoFuncAcomp.length; i++ )
                {
                    codTpfa = Long.parseLong( strLibTipoFuncAcomp[i] );

                    TipoFuncAcompTpfa tipoFuncAcomp =
                        (TipoFuncAcompTpfa) this.buscar( TipoFuncAcompTpfa.class, codTpfa );

                    set.add( tipoFuncAcomp );
                } //fim for
            } catch ( ECARException e )
            {
//				e.printStackTrace();
            }
        } //fim if

        return set;
    }

    /**
     * Altera um registro de EstruturaTipoFuncAcomp e propaga/remove a permiss�o para os pr�ximos n�veis
     *
     * @param estrutTpFuncAcmpEtttfa
     * @throws ECARException
     */
    public void alterar( EstrutTpFuncAcmpEtttfa estrutTpFuncAcmpEtttfa )
                 throws ECARException
    {
        Transaction tx = null;

        try
        {
            ArrayList objetos = new ArrayList(  );

            super.inicializarLogBean(  );

            tx = session.beginTransaction(  );

            ControlePermissao controlePermissao = new ControlePermissao(  );

            //
            // controlar as permissoes
            //
            String indManterProximoNivelAntigo = request.getParameter( "indManterProximoNivelEtttfaAntigo" );
            String indManterProximoNivelAtual = estrutTpFuncAcmpEtttfa.getIndManterProximoNivelEtttfa(  );

            if ( ! indManterProximoNivelAntigo.equals( indManterProximoNivelAtual ) )
            {
                controlePermissao.atualizarPermissaoManterProximoNivel( estrutTpFuncAcmpEtttfa, session, request );
            }

            session.update( estrutTpFuncAcmpEtttfa );
            objetos.add( estrutTpFuncAcmpEtttfa );

            tx.commit(  );

            if ( super.logBean != null )
            {
                super.logBean.setCodigoTransacao( Data.getHoraAtual( false ) );
                super.logBean.setOperacao( "ALT" );

                Iterator itObj = objetos.iterator(  );

                while ( itObj.hasNext(  ) )
                {
                    super.logBean.setObj( itObj.next(  ) );
                    super.loggerAuditoria.info( logBean.toString(  ) );
                }
            }
        } catch ( HibernateException e )
        {
            if ( tx != null )
            {
                try
                {
                    tx.rollback(  );
                } catch ( HibernateException r )
                {
                    this.logger.error( r );
                    throw new ECARException( "erro.hibernateException" );
                }
            }

            this.logger.error( e );
            throw new ECARException( "erro.hibernateException" );
        }
    }
}
