package ecar.dao;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import comum.database.Dao;
import comum.util.ConstantesECAR;
import comum.util.Pagina;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.pojo.AtributoDemandaAtbdem;
import ecar.pojo.ObjetoDemanda;
import ecar.pojo.SisGrupoAtributoSga;

/**
 * Classe de manipula��o de objetos da classe AtributoAtt.
 *
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Fri Jan 27 07:54:28 BRST 2006
 *
 */
public class AtributoDemandaDao
    extends Dao
{
    /**
     * Construtor. Chama o Session factory do Hibernate
     *
    * @param request
    */
    public AtributoDemandaDao( HttpServletRequest request )
    {
        super(  );
        this.request = request;
    }

    /**
    *
    * @param atributoDemanda
    * @throws ECARException
    */
    public void salvar( AtributoDemandaAtbdem atributoDemanda )
                throws ECARException
    {
        if ( pesquisarDuplos( atributoDemanda,
                                  new String[] { "nomeAtbdem", "labelPadraoAtbdem" },
                                  "codAtbdem" ).size(  ) > 0 )
        {
            throw new ECARException( "atributo.validacao.registroDuplicado" );
        }

        if ( ( atributoDemanda.getIndAtivoAtbdem(  ) != null ) && "S".equals( atributoDemanda.getIndAtivoAtbdem(  ) ) )
        {
            if ( pesquisarDuplosAtributoDemanda( atributoDemanda,
                                                     atributoDemanda.getSisGrupoAtributoSga(  ),
                                                     atributoDemanda.getIndAtivoAtbdem(  ) ) )
            {
                throw new ECARException( "atributo.validacao.registroDuplicadoAtivo" );
            }
        }

        super.salvar( atributoDemanda );
    }

    /**
    *
    * @param atributoDemanda
    * @throws ECARException
    */
    public void excluir( AtributoDemandaAtbdem atributoDemanda )
                 throws ECARException
    {
        try
        {
            boolean excluir = true;

            if ( excluir )
            {
            	if (!ehAssociadoAtributoVisao(atributoDemanda)) 
            		super.excluir( atributoDemanda );
            	else
            		throw new ECARException( "atributoDemanda.exclusao.erro.possuiAssociaoAtributoVisao" );
            }
        } catch ( ECARException e )
        {
            this.logger.error( e );
            throw e;
        }
    }
    
    private boolean ehAssociadoAtributoVisao(AtributoDemandaAtbdem atributoDemanda) {
    
    	boolean eh = false;
    	
    	StringBuilder query =
            new StringBuilder( "select visaoAtributoDemanda from VisaoAtributoDemanda as visaoAtributoDemanda where visaoAtributoDemandaPk.atributoDemanda.codAtbdem = " ).
                                                                                                       append( atributoDemanda.getCodAtbdem().toString());

        Query q = this.getSession(  ).createQuery( query.toString(  ) );
        
        List atributosVisoes = q.list();
    	
        if (atributosVisoes!=null && atributosVisoes.size()>0) {
        	eh = true;
        }
        
    	return eh;
    }

    /**
    *
    * @param atributoDemanda
    * @throws ECARException
    */
    public void alterar( AtributoDemandaAtbdem atributoDemanda )
                 throws ECARException
    {
        if ( pesquisarDuplos( atributoDemanda,
                                  new String[] { "nomeAtbdem", "labelPadraoAtbdem" },
                                  "codAtbdem" ).size(  ) > 0 )
        {
            throw new ECARException( "atributo.validacao.registroDuplicado" );
        }

        if ( ( atributoDemanda.getIndAtivoAtbdem(  ) != null ) && "S".equals( atributoDemanda.getIndAtivoAtbdem(  ) ) )
        {
            if ( pesquisarDuplosAtributoDemanda( atributoDemanda,
                                                     atributoDemanda.getSisGrupoAtributoSga(  ),
                                                     atributoDemanda.getIndAtivoAtbdem(  ) ) )
            {
                throw new ECARException( "atributo.validacao.registroDuplicadoAtivo" );
            }
        }

        if ( atributoDemanda.getSisGrupoAtributoSga(  ) == null )
        {
            validarNomeAtributoDemanda( atributoDemanda );
        }

//		if (atributoDemanda.getIndRestritivo().equals("S") && 
//				(atributoDemanda.getSisGrupoAtributoSga() == null || !atributoDemanda.getSisGrupoAtributoSga().getIndCadUsuSga().equals("S"))){
//			throw new ECARException("atributo.validacao.indRestritivoInvalido");
//		}
        super.alterar( atributoDemanda );
    }

    /**
    *
    * @param obj
    * @param sisGrupo
    * @param indAtivoAtbdem
    * @return
    * @throws ECARException
    */
    public boolean pesquisarDuplosAtributoDemanda( AtributoDemandaAtbdem obj, SisGrupoAtributoSga sisGrupo,
                                                   String indAtivoAtbdem )
                                           throws ECARException
    {
        boolean retorno = false;

        try
        {
            Criteria crits = session.createCriteria( AtributoDemandaAtbdem.class );

            crits.add( Restrictions.eq( "sisGrupoAtributoSga.codSga",
                                        ( ( sisGrupo == null ) ? 0L : sisGrupo.getCodSga(  ) ) ) );
            crits.add( Restrictions.eq( "indAtivoAtbdem", indAtivoAtbdem ) );
            crits.add( Restrictions.ne( "codAtbdem", ( ( obj.getCodAtbdem(  ) == null ) ? 0L : obj.getCodAtbdem(  ) ) ) ); // != codAtbdem

            List<AtributoDemandaAtbdem> lista = (List<AtributoDemandaAtbdem>) crits.list(  );

            if ( lista.size(  ) > 0 )
            {
                retorno = true;
            }

            return retorno;
        } catch ( Exception e )
        {
            this.logger.error( e );
            throw new ECARException( "erro.hibernateException" );
        }
    }

    /**
     * Popula um objeto do tipo AtributoDemandaAtbdem com os dados vindos da tela atrav�s da request
     *
     * @param request
     * @param atributoDemanda
     * @param usarGetParamStr
     * @throws ECARException
     */
    public void setAtributoDemanda( HttpServletRequest request, AtributoDemandaAtbdem atributoDemanda,
                                    boolean usarGetParamStr )
                            throws ECARException
    {
        if ( Pagina.getParam( request, "codigo" ) != null )
        {
            atributoDemanda.setCodAtbdem( Long.valueOf( Pagina.getParam( request, "codigo" ) ) );
        } else{
            atributoDemanda.setCodAtbdem( null );
        }

        if ( usarGetParamStr )
        {
            atributoDemanda.setIndAtivoAtbdem( Pagina.getParamOrDefault( request, "indAtivoAtbdem", Pagina.NAO ) );
            atributoDemanda.setNomeAtbdem( Pagina.getParamStr( request, "nomeAtbdem" ).trim(  ) );
            atributoDemanda.setLabelPadraoAtbdem( Pagina.getParamStr( request, "labelPadraoAtbdem" ).trim(  ) );
            atributoDemanda.setCodFkAtbdem( Pagina.getParamStr( request, "codFkAtbdem" ).trim(  ) );
            atributoDemanda.setNomeFkAtbdem( Pagina.getParamStr( request, "nomeFkAtbdem" ).trim(  ) );
            atributoDemanda.setDocumentacaoAtbdem( Pagina.getParamStr( request, "documentacaoAtbdem" ).trim(  ) );
        } else
        {
            atributoDemanda.setIndAtivoAtbdem( Pagina.getParam( request, "indAtivoAtbdem" ) );
            atributoDemanda.setNomeAtbdem( Pagina.getParam( request, "nomeAtbdem" ) );
            atributoDemanda.setLabelPadraoAtbdem( Pagina.getParam( request, "labelPadraoAtbdem" ) );
            atributoDemanda.setCodFkAtbdem( Pagina.getParam( request, "codFkAtbdem" ) );
            atributoDemanda.setNomeFkAtbdem( Pagina.getParam( request, "nomeFkAtbdem" ) );
            atributoDemanda.setDocumentacaoAtbdem( Pagina.getParam( request, "documentacaoAtbdem" ) );
        }

        SisGrupoAtributoDao sgaDao = new SisGrupoAtributoDao( request );

        if ( Pagina.getParam( request, "sisGrupoAtributoSga" ) != null )
        {
            atributoDemanda.setSisGrupoAtributoSga( (SisGrupoAtributoSga) sgaDao.buscar( 
                                                                                         SisGrupoAtributoSga.class,
                                                                                         Long.valueOf( Pagina.getParamStr( request,
                                                                                                                           "sisGrupoAtributoSga" ) ) ) );
        } else
        {
            atributoDemanda.setSisGrupoAtributoSga( null );
        }

        //tamanhoConteudoAtbdem
        if ( Pagina.getParam( request, "tamanhoConteudoAtbdem" ) != null )
        {
            atributoDemanda.setTamanhoConteudoAtbdem( ( Integer.valueOf( Pagina.getParam( request,
                                                                                          "tamanhoConteudoAtbdem" ) ) ) );
        } else{
            atributoDemanda.setTamanhoConteudoAtbdem( null );
        }
    }

    /**
     * Retorna todos atributos ativos de demanda ordenados por sequ�ncia de apresenta��o tela campo.
     * @param codVisao
     * @return
     * @throws ECARException
     */
    public List getAtributosDemandaVisaoAtivosOrdenadosPorSequenciaTelaCampo( Long codVisao )
        throws ECARException
    {
        List retorno = new ArrayList(  );
        ObjetoDemanda atributoDemanda = new AtributoDemandaAtbdem(  );

        try
        {
            String hql =
                MessageFormat.format( Util.getHql( ConstantesECAR.PESQUISA_ATRIBUTOS_DEMANDA_VISAO_ATIVOS_POR_SEQLISTCADASTRO,
                                                   request.getSession(  ).getServletContext(  ) ),
                                      codVisao.toString(  ),
                                      "\'S\'",
                                      "\'S\'" );

            Query q = this.getSession(  ).createQuery( hql );

            retorno = q.list(  );
        } catch ( IOException e )
        {
            this.logger.error( e );
            throw new ECARException( e );
        } catch ( HibernateException e )
        {
            this.logger.error( e );
            throw new ECARException( "erro.hibernateException" );
        }

        return retorno;
    }

    /**
     * Retorna todos atributos ativos de demanda numa visao ordenados por sequ�ncia de apresenta��o na tela de listagem de demandas.
     * @param codVisao
     * @return
     * @throws ECARException
     */
    public List getAtributosDemandaVisaoAtivosOrdenadosPorSequenciaTelaListaDemandas( Long codVisao )
        throws ECARException
    {
        List retorno = new ArrayList(  );
        ObjetoDemanda atributoDemanda = new AtributoDemandaAtbdem(  );

        try
        {
            Long codigoUsuarioLogado =
                ( (ecar.login.SegurancaECAR) request.getSession(  ).getAttribute( "seguranca" ) ).getUsuario(  )
                  .getCodUsu(  );

            String hql =
                MessageFormat.format( Util.getHql( ConstantesECAR.PESQUISA_ATRIBUTOS_DEMANDA_VISAO_ATIVOS_POR_SEQLISTDEM,
                                                   request.getSession(  ).getServletContext(  ) ),
                                      codVisao.toString(  ),
                                      "\'S\'",
                                      "\'S\'" );

            Query q = this.getSession(  ).createQuery( hql );

            retorno = q.list(  );
        } catch ( IOException e )
        {
            this.logger.error( e );
            throw new ECARException( e );
        } catch ( HibernateException e )
        {
            this.logger.error( e );
            throw new ECARException( "erro.hibernateException" );
        }

        return retorno;
    }

    /**
     * Retorna todos atributos ativos de demanda ordenados por sequ�ncia de apresenta��o tela campo.
     * @param codVisao
     * @return
     * @throws ECARException
     */
    public List getAtributosDemandaVisaoAtivosOrdenadosPorSequenciaTelaListaDemandasIncluidasUsuarioLogado( Long codVisao )
        throws ECARException
    {
        List retorno = new ArrayList(  );
        ObjetoDemanda atributoDemanda = new AtributoDemandaAtbdem(  );

        try
        {
            Long codigoUsuarioLogado =
                ( (ecar.login.SegurancaECAR) request.getSession(  ).getAttribute( "seguranca" ) ).getUsuario(  )
                  .getCodUsu(  );

            String hql =
                MessageFormat.format( Util.getHql( ConstantesECAR.PESQUISA_ATRIBUTOS_DEMANDA_VISAO_ATIVOS_POR_SEQLISTDEM_INCLUIDAS_USUARIO,
                                                   request.getSession(  ).getServletContext(  ) ),
                                      codVisao.toString(  ),
                                      "\'S\'",
                                      codigoUsuarioLogado.toString(  ) );

            Query q = this.getSession(  ).createQuery( hql );

            retorno = q.list(  );
        } catch ( IOException e )
        {
            this.logger.error( e );
            throw new ECARException( e );
        } catch ( HibernateException e )
        {
            this.logger.error( e );
            throw new ECARException( "erro.hibernateException" );
        }

        return retorno;
    }

    /**
     * Retorna todos atributos ativos de demanda ordenados por sequ�ncia de apresenta��o tela campo.
     * @param codVisao
     * @return
     * @throws ECARException
     */
    public List getAtributosDemandaVisaoAtivosQueSaoFiltro( Long codVisao )
                                                    throws ECARException
    {
        List retorno = new ArrayList(  );
        ObjetoDemanda atributoDemanda = new AtributoDemandaAtbdem(  );

        try
        {
            String hql =
                MessageFormat.format( Util.getHql( ConstantesECAR.PESQUISA_ATRIBUTOS_DEMANDA_VISAO_ATIVOS_QUE_SAO_FILTROS,
                                                   request.getSession(  ).getServletContext(  ) ),
                                      codVisao.toString(  ),
                                      "\'S\'",
                                      "\'S\'" );

            Query q = this.getSession(  ).createQuery( hql );

            retorno = q.list(  );
        } catch ( IOException e )
        {
            this.logger.error( e );
            throw new ECARException( e );
        } catch ( HibernateException e )
        {
            this.logger.error( e );
            throw new ECARException( "erro.hibernateException" );
        }

        return retorno;
    }

    /**
     * Retorna todos atributos ativos de demanda ordenados por sequ�ncia de apresenta��o tela campo.
     * @param sisGrupoAtributoSga
     * @return
     * @throws ECARException
     */
    public List getAtributosDemandaAtivosPorGrupo( SisGrupoAtributoSga sisGrupoAtributoSga )
                                           throws ECARException
    {
        List retorno = new ArrayList(  );
        ObjetoDemanda atributoDemanda = new AtributoDemandaAtbdem(  );

        try
        {
            StringBuilder query =
                new StringBuilder( "select atributoDemanda from AtributoDemandaAtbdem as atributoDemanda" ).append( " where atributoDemanda.indAtivoAtbdem = 'S'" )
                                                                                                           .append( " and atributoDemanda.sisGrupoAtributoSga = '" )
                                                                                                           .append( sisGrupoAtributoSga.getCodSga(  ) +
                                                                                                                    "'" );

            Query q = this.getSession(  ).createQuery( query.toString(  ) );

            retorno = q.list(  );
        } catch ( HibernateException e )
        {
            this.logger.error( e );
            throw new ECARException( "erro.hibernateException" );
        }

        return retorno;
    }

    /**
     * Valida se o nome informado para o atributo na demanda
     * � igual a algum dos atributos de regDemanda
     *
     * @param atributoDemandaAtbdem
     * @throws ECARException
     */
    public void validarNomeAtributoDemanda( AtributoDemandaAtbdem atributoDemandaAtbdem )
                                    throws ECARException
    {
        if ( ! atributoDemandaAtbdem.iGetNome(  ).equals( "codRegd" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "dataLimiteRegd" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "descricaoRegd" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "observacaoRegd" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "numeroDocOrigemRegd" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "dataSolicitacaoRegd" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "dataInclusaoRegd" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "indAtivoRegd" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "nomeSolicitanteRegd" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "dataSituacaoRegd" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "prioridadePrior" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "sitDemandaSitd" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "usuarioUsuByCodUsuInclusaoRegd" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "entidadeOrgaoDemandaEntorgds" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "localDemandaLdems" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "regApontamentoRegdas" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "itemRegdemandaIregds" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "demAtributoDemas" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "regDemandaRegds" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "regDemandaRegd" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "entidadeDemandaEntds" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "indRestritivo" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "dataAlteracaoRegd" ) &&
                 ! atributoDemandaAtbdem.iGetNome(  ).equals( "usuarioUsuByCodUsuAlteracaoRegd" ) )
        {
            throw new ECARException( "atributoDemanda.validacao.nomeAtributoDemandaInvalido" );
        }
    }

    /**
     * Recupera o label padr�o do atributo na demanda fixo de acordo com o nome do atributo
     * @param nomeAtributo
     * @throws ECARException
     */
    public String getLabelAtributoDemandaFixo( String nomeAtributo )
                                       throws ECARException
    {
        String labelAtributo = "";
        AtributoDemandaAtbdem atributoDemandaAtbdem = new AtributoDemandaAtbdem(  );
        atributoDemandaAtbdem.setNomeAtbdem( nomeAtributo );
        atributoDemandaAtbdem.setSisGrupoAtributoSga( null );

        List atbDem = this.pesquisar( atributoDemandaAtbdem, null );

        if ( atbDem.size(  ) == 1 )
        {
            labelAtributo = ( (AtributoDemandaAtbdem) atbDem.get( 0 ) ).getLabelPadraoAtbdem(  );
        }

        return labelAtributo;
    }
}
