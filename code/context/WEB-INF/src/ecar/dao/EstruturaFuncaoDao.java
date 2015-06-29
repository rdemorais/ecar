/*
 * Criado em 29/11/2004
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.EstruturaFuncaoEttfPK;
import ecar.pojo.FuncaoFun;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.util.Dominios;

/**
 * @author felipev
 *
 */
public class EstruturaFuncaoDao
    extends Dao
{
    /*private EstruturaDao estruturaDao = null;
        private FuncaoDao funcaoDao = null;*/

    /**
     * Construtor. Chama o Session factory do Hibernate
     *
     * @param request
     */
    public EstruturaFuncaoDao( HttpServletRequest request )
    {
        super(  );
        this.request = request;

        /*estruturaDao = new EstruturaDao(this.request);
        funcaoDao = new FuncaoDao(this.request);*/
    }

    /**
     *
     * @param request
     * @param listaEstrutura
     * @param listaFuncao
     * @throws ECARException
     */
    public void controlaListas( HttpServletRequest request, List listaEstrutura, List listaFuncao )
                        throws ECARException
    {
        if ( Pagina.getParam( request, "estruturaEtt" ) != null )
        {
            listaFuncao.addAll( this.getFuncoes( (EstruturaEtt) new EstruturaDao( request ).buscar( 
                                                                                                    EstruturaEtt.class,
                                                                                                    Long.valueOf( Pagina.getParam( request,
                                                                                                                                   "estruturaEtt" ) ) ) ) );
        } else
        {
            /* Lista de fun��es recebe todas as fun��es */
            listaFuncao.addAll( new FuncaoDao( request ).listar( 
                                                                 FuncaoFun.class,
                                                                 new String[] { "nomeFun", "asc" } ) );
        }

        if ( Pagina.getParam( request, "funcaoFun" ) != null )
        {
            listaEstrutura.addAll( this.getEstruturasNaoVirtuais( (FuncaoFun) new FuncaoDao( request ).buscar( 
                                                                                                               FuncaoFun.class,
                                                                                                               Long.valueOf( Pagina.getParam( request,
                                                                                                                                              "funcaoFun" ) ) ) ) );
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
     * @param request
     * @param estruturaFuncao
     * @param usarGetParamStr
     * @throws ECARException
     */
    public void setEstruturaFuncao( HttpServletRequest request, EstruturaFuncaoEttf estruturaFuncao,
                                    boolean usarGetParamStr )
                            throws ECARException
    {
        if ( Pagina.getParam( request, "estruturaEtt" ) != null )
        {
            estruturaFuncao.setEstruturaEtt( (EstruturaEtt) ( 
                                                 new EstruturaDao( request ).buscar( 
                                                                                     EstruturaEtt.class,
                                                                                     Long.valueOf( Pagina.getParam( request,
                                                                                                                    "estruturaEtt" ) ) )
                                              ) );
        }

        if ( Pagina.getParam( request, "funcaoFun" ) != null )
        {
            estruturaFuncao.setFuncaoFun( (FuncaoFun) new FuncaoDao( request ).buscar( 
                                                                                       FuncaoFun.class,
                                                                                       Long.valueOf( Pagina.getParam( request,
                                                                                                                      "funcaoFun" ) ) ) );
        }

        if ( usarGetParamStr )
        {
            estruturaFuncao.setLabelEttf( Pagina.getParamStr( request, "labelEttf" ) );
            estruturaFuncao.setDicaEttf( Pagina.getParamStr( request, "dicaEttf" ) );
            estruturaFuncao.setIndListagemImpressaResEttf( Pagina.getParamOrDefault( request,
                                                                                     "indListagemImpressaResEttf",
                                                                                     Pagina.NAO ) );
            estruturaFuncao.setIndListagemImpressCompEttf( Pagina.getParamOrDefault( request,
                                                                                     "indListagemImpressCompEttf",
                                                                                     Pagina.NAO ) );
            estruturaFuncao.setIndRevisaoEttf( Pagina.getParamOrDefault( request, "indRevisaoEttf", Pagina.NAO ) );
            estruturaFuncao.setIndPodeBloquearEttf( Pagina.getParamOrDefault( request, "indPodeBloquearEttf", Pagina.NAO ) );
            estruturaFuncao.setIndExibirHistoricoEttf( Pagina.getParamOrDefault( request, "indExibirHistoricoEttf",
                                                                                 Pagina.NAO ) );
        } else
        {
            estruturaFuncao.setLabelEttf( Pagina.getParam( request, "labelEttf" ) );
            estruturaFuncao.setDicaEttf( Pagina.getParam( request, "dicaEttf" ) );
            estruturaFuncao.setIndListagemImpressaResEttf( Pagina.getParam( request, "indListagemImpressaResEttf" ) );
            estruturaFuncao.setIndListagemImpressCompEttf( Pagina.getParam( request, "indListagemImpressCompEttf" ) );
            estruturaFuncao.setIndRevisaoEttf( Pagina.getParam( request, "indRevisaoEttf" ) );
            estruturaFuncao.setIndPodeBloquearEttf( Pagina.getParam( request, "indPodeBloquearEttf" ) );
            estruturaFuncao.setIndExibirHistoricoEttf( Pagina.getParam( request, "indExibirHistoricoEttf" ) );
        }

        if ( Pagina.getParam( request, "seqApresentacaoTelaEttf" ) != null )
        {
            estruturaFuncao.setSeqApresentacaoTelaEttf( Integer.valueOf( Pagina.getParam( request,
                                                                                          "seqApresentacaoTelaEttf" ) ) );
        }

        if ( Pagina.getParam( request, "seqApresentacaoRelatorioEttf" ) != null )
        {
            estruturaFuncao.setSeqApresentacaoRelatorioEttf( Integer.valueOf( Pagina.getParam( request,
                                                                                               "seqApresentacaoRelatorioEttf" ) ) );
        }

        if ( Pagina.getParamStr( request, "documentacaoEttf" ) != null )
        {
            estruturaFuncao.setDocumentacaoEttf( Pagina.getParam( request, "documentacaoEttf" ) );
        } else{
            estruturaFuncao.setDocumentacaoEttf( null );
        }
    }

    /**
     * Verifica��o das estruturas de acompanhementos que para quem o bloqueio foi liberado
     * @return  Conjunto com a lista de tipo de acompanhamento
     */
    public Set setLimbTipoFuncAcompTpfa(  )
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
     * Devolve uma lista com todas as fun��es vinculadas a uma estrutura
     * @param estrutura
     * @return List de FuncaoFun
     */
    public List getFuncoes( EstruturaEtt estrutura )
    {
        List retorno = new ArrayList(  );

        if ( estrutura.getEstruturaFuncaoEttfs(  ) != null )
        {
            Iterator it = estrutura.getEstruturaFuncaoEttfs(  ).iterator(  );

            while ( it.hasNext(  ) )
            {
                EstruturaFuncaoEttf estruturaFuncao = (EstruturaFuncaoEttf) it.next(  );
                retorno.add( estruturaFuncao.getFuncaoFun(  ) );
            }
        }

        return retorno;
    }
    
    public List<EstruturaFuncaoEttf> getListaDeFuncoes(AcompReferenciaItemAri acompAri,List<EstruturaFuncaoEttf> listaEstruturaFuncao)throws ECARException{
    	
    	List<EstruturaFuncaoEttf> lista = new ArrayList<EstruturaFuncaoEttf>();
    	for (EstruturaFuncaoEttf estruturaFuncaoEttf : listaEstruturaFuncao) {
    		
    		Long codEtt = acompAri.getItemEstruturaIett().getEstruturaEtt().getCodEtt();
    		Long cod = estruturaFuncaoEttf.getEstruturaEtt().getCodEtt();
    		
    		if(codEtt.equals(cod)){
    			lista.add(estruturaFuncaoEttf);
    		}
		}
    	
		return lista ;
    }
    public List<EstruturaFuncaoEttf> getListaDeFuncoes(List<Long> listaParaFuncoes)throws ECARException{
    	try{
    	    	StringBuilder query = new StringBuilder("select ettf from EstruturaFuncaoEttf ettf")
    	    							.append(" join fetch ettf.estruturaEtt estrutura ")
    	    							.append(" where estrutura.codEtt in (:listCodEtt)");
    	    	

    	    	Query q = this.getSession().createQuery(query.toString());
    			q.setParameterList("listCodEtt", listaParaFuncoes);
    			q.setCacheable(true);
    			q.setCacheMode(CacheMode.NORMAL);
    			
    			List retorno = q.list();

    			if(retorno == null || retorno.isEmpty()){
                    return null;
                } else { 
                	return retorno;
                }
                        
        } catch(HibernateException e){
			this.logger.error(e);
            throw new ECARException(e);
        }
    }

    /**
     * Devolve uma lista com todas as fun��es vinculadas a uma estrutura que podem ser
     * copiadas de um item como mod�lo
     * @param estrutura
     * @return List de FuncaoFun
     */
    public List getFuncoesCopia( EstruturaEtt estrutura )
    {
        List retorno = new ArrayList(  );

        if ( estrutura.getEstruturaFuncaoEttfs(  ) != null )
        {
            Iterator it = estrutura.getEstruturaFuncaoEttfs(  ).iterator(  );

            while ( it.hasNext(  ) )
            {
                EstruturaFuncaoEttf estruturaFuncao = (EstruturaFuncaoEttf) it.next(  );

                if ( ( estruturaFuncao.getFuncaoFun(  ).getIndCopiaFun(  ) != null ) &&
                         estruturaFuncao.getFuncaoFun(  ).getIndCopiaFun(  ).equals( Dominios.SIM ) )
                {
                    retorno.add( estruturaFuncao.getFuncaoFun(  ) );
                }
            }
        }

        return retorno;
    }

    /**
     * Devolve uma lista com todas as estruturas vinculadas a uma fun��o
     * @param funcao
     * @return List de FuncaoFun
     */
    public List getEstruturas( FuncaoFun funcao )
    {
        List retorno = new ArrayList(  );

        if ( funcao.getEstruturaFuncaoEttfs(  ) != null )
        {
            Iterator it = funcao.getEstruturaFuncaoEttfs(  ).iterator(  );

            while ( it.hasNext(  ) )
            {
                EstruturaFuncaoEttf estruturaFuncao = (EstruturaFuncaoEttf) it.next(  );
                retorno.add( estruturaFuncao.getEstruturaEtt(  ) );
            }
        }

        return retorno;
    }

    /**
     * Devolve uma lista com todas as estruturas n�o virtuais vinculadas a uma fun��o
     * @param funcao
     * @return List de FuncaoFun
     */
    public List getEstruturasNaoVirtuais( FuncaoFun funcao )
    {
        List retorno = new ArrayList(  );

        if ( funcao.getEstruturaFuncaoEttfs(  ) != null )
        {
            Iterator it = funcao.getEstruturaFuncaoEttfs(  ).iterator(  );

            while ( it.hasNext(  ) )
            {
                EstruturaFuncaoEttf estruturaFuncao = (EstruturaFuncaoEttf) it.next(  );

                if ( ! estruturaFuncao.getEstruturaEtt(  ).isVirtual(  ) )
                {
                    retorno.add( estruturaFuncao.getEstruturaEtt(  ) );
                }
            }
        }

        return retorno;
    }

    /**
     * Devolve um objeto EstruturaFuncao, utilizado para imprimir o Label da Fun��o
     * @param estrutura
     * @param codFun (codAba)
     * @return Obj EstruturaFuncao
     * @throws ECARException
     */
    public EstruturaFuncaoEttf getLabelFuncao( EstruturaEtt estrutura, Long codFun )
                                       throws ECARException
    {
        EstruturaFuncaoEttfPK chave = new EstruturaFuncaoEttfPK(  );

        chave.setCodEtt( estrutura.getCodEtt(  ) );
        chave.setCodFun( codFun );

        try
        {
            return (EstruturaFuncaoEttf) buscar( EstruturaFuncaoEttf.class, chave );
        } catch ( ECARException e )
        {
            if ( "erro.objectNotFound".equals( e.getMessageKey(  ) ) )
            {
                return null;
            }

            throw new ECARException( e );
        }
    }

    /**
     *
     * @author n/c
     * @param estrutura
     * @return String
     * @throws ECARException
     */
    public String getLabelFuncaoFonteRecurso( EstruturaEtt estrutura )
                                      throws ECARException
    {
        FuncaoFun funcao = new FuncaoFun(  );
        funcao.setNomeFun( "Fontes_Recursos" );

        List pesquisa = new FuncaoDao( request ).pesquisar( funcao,
                                                            new String[] { "nomeFun", "asc" } );
        FuncaoFun f = (FuncaoFun) pesquisa.iterator(  ).next(  );

        try
        {
            return getLabelFuncao( estrutura,
                                   f.getCodFun(  ) ).getLabelEttf(  );
        } catch ( Exception e )
        {
            this.logger.error( e );

            if ( funcao.getLabelPadraoFun(  ) != null )
            {
                return f.getLabelPadraoFun(  );
            } else{
                return f.getNomeFun(  );
            }
        }
    }

    /**
     *
     * @author n/c
     * @param estrutura
     * @return String
     * @throws ECARException
     */
    public String getLabelFuncaoRecurso( EstruturaEtt estrutura )
                                 throws ECARException
    {
        FuncaoFun funcao = new FuncaoFun(  );
        funcao.setNomeFun( "Recursos" );

        // Pesquisa em ordem descendente para que Recursos venha antes de Fonte_recurso
        List pesquisa = new FuncaoDao( request ).pesquisar( funcao,
                                                            new String[] { "nomeFun", "desc" } );
        FuncaoFun f = (FuncaoFun) pesquisa.iterator(  ).next(  );

        try
        {
            return getLabelFuncao( estrutura,
                                   f.getCodFun(  ) ).getLabelEttf(  );
        } catch ( Exception e )
        {
            this.logger.error( e );

            if ( funcao.getLabelPadraoFun(  ) != null )
            {
                return f.getLabelPadraoFun(  );
            } else{
                return f.getNomeFun(  );
            }
        }
    }

    /**
     *
     * @author n/c
     * @param estrutura
     * @return string
     * @throws ECARException
     */
    public String getLabelFuncaoAnexo( EstruturaEtt estrutura )
                               throws ECARException
    {
        FuncaoFun funcao = new FuncaoFun(  );
        funcao.setNomeFun( "Itens_de_Anexo" );

        List pesquisa = new FuncaoDao( request ).pesquisar( funcao,
                                                            new String[] { "nomeFun", "asc" } );
        FuncaoFun f = (FuncaoFun) pesquisa.iterator(  ).next(  );

        try
        {
            return getLabelFuncao( estrutura,
                                   f.getCodFun(  ) ).getLabelEttf(  );
        } catch ( Exception e )
        {
            this.logger.error( e );

            if ( funcao.getLabelPadraoFun(  ) != null )
            {
                return f.getLabelPadraoFun(  );
            } else{
                return f.getNomeFun(  );
            }
        }
    }

    /**
     *
     * @author n/c
     * @param estrutura
     * @return string
     * @throws ECARException
     */
    public String getLabelFuncaoPontosCriticos( EstruturaEtt estrutura )
                                        throws ECARException
    {
        FuncaoFun funcao = new FuncaoFun(  );
        funcao.setNomeFun( "Pontos_Criticos" );

        List pesquisa = new FuncaoDao( request ).pesquisar( funcao,
                                                            new String[] { "nomeFun", "asc" } );
        FuncaoFun f = (FuncaoFun) pesquisa.iterator(  ).next(  );

        try
        {
            return getLabelFuncao( estrutura,
                                   f.getCodFun(  ) ).getLabelEttf(  );
        } catch ( Exception e )
        {
            this.logger.error( e );

            if ( funcao.getLabelPadraoFun(  ) != null )
            {
                return f.getLabelPadraoFun(  );
            } else{
                return f.getNomeFun(  );
            }
        }
    }

    /**
     *
     * @author n/c
     * @param estrutura
     * @return boolean
     * @throws ECARException
     */
    public boolean existeFuncaoPontosCriticos( EstruturaEtt estrutura )
                                       throws ECARException
    {
        FuncaoFun funcao = new FuncaoFun(  );
        funcao.setNomeFun( "Pontos_Criticos" );

        List pesquisa = new FuncaoDao( request ).pesquisar( funcao,
                                                            new String[] { "nomeFun", "asc" } );
        FuncaoFun f = (FuncaoFun) pesquisa.iterator(  ).next(  );
        boolean resultado = false;

        try
        {
            if ( getLabelFuncao( estrutura,
                                     f.getCodFun(  ) ) != null )
            {
                resultado = true;
            }

            return resultado;
        } catch ( ECARException e )
        {
            this.logger.error( e );

            return resultado;
        }
    }

    /**
     *
     * @author n/c
     * @param estrutura
     * @return boolean
     * @throws ECARException
     */
    public boolean existeFuncaoAnexo( EstruturaEtt estrutura )
                              throws ECARException
    {
        FuncaoFun funcao = new FuncaoFun(  );
        funcao.setNomeFun( "Itens_de_Anexo" );

        List pesquisa = new FuncaoDao( request ).pesquisar( funcao,
                                                            new String[] { "nomeFun", "asc" } );
        FuncaoFun f = (FuncaoFun) pesquisa.iterator(  ).next(  );
        boolean resultado = false;

        try
        {
            if ( getLabelFuncao( estrutura,
                                     f.getCodFun(  ) ) != null )
            {
                resultado = true;
            }

            return resultado;
        } catch ( ECARException e )
        {
            this.logger.error( e );

            return resultado;
        }
    }

    /**
     *
     * @author n/c
     * @param estrutura
     * @return String
     * @throws ECARException
     */
    public String getLabelFuncaoCategoriaAnexo( EstruturaEtt estrutura )
                                        throws ECARException
    {
        FuncaoFun funcao = new FuncaoFun(  );
        funcao.setNomeFun( "Categorias" );

        List pesquisa = new FuncaoDao( request ).pesquisar( funcao,
                                                            new String[] { "nomeFun", "asc" } );
        FuncaoFun f = (FuncaoFun) pesquisa.iterator(  ).next(  );

        try
        {
            return getLabelFuncao( estrutura,
                                   f.getCodFun(  ) ).getLabelEttf(  );
        } catch ( Exception e )
        {
            this.logger.error( e );

            if ( funcao.getLabelPadraoFun(  ) != null )
            {
                return f.getLabelPadraoFun(  );
            } else{
                return f.getNomeFun(  );
            }
        }
    }

    /**
     *
     * @author n/c
     * @param estrutura
     * @return String
     * @throws ECARException
     */
    public String getLabelIndicadoresResultado( EstruturaEtt estrutura )
                                        throws ECARException
    {
        FuncaoFun funcao = new FuncaoFun(  );
        funcao.setNomeFun( "Indicadores_Resultado" );

        List pesquisa = new FuncaoDao( request ).pesquisar( funcao,
                                                            new String[] { "nomeFun", "asc" } );
        FuncaoFun f = (FuncaoFun) pesquisa.iterator(  ).next(  );

        try
        {
            return getLabelFuncao( estrutura,
                                   f.getCodFun(  ) ).getLabelEttf(  );
        } catch ( Exception e )
        {
            this.logger.error( e );

            if ( funcao.getLabelPadraoFun(  ) != null )
            {
                return f.getLabelPadraoFun(  );
            } else{
                return f.getNomeFun(  );
            }
        }
    }

    /**
     *
     * @author n/c
     * @param estrutura
     * @return String
     * @throws ECARException
     */
    public String getLabelOcorrencias( EstruturaEtt estrutura )
                               throws ECARException
    {
        FuncaoFun funcao = new FuncaoFun(  );
        funcao.setNomeFun( "Evento" );

        List pesquisa = new FuncaoDao( request ).pesquisar( funcao,
                                                            new String[] { "nomeFun", "asc" } );
        FuncaoFun f = (FuncaoFun) pesquisa.iterator(  ).next(  );

        try
        {
            return getLabelFuncao( estrutura,
                                   f.getCodFun(  ) ).getLabelEttf(  );
        } catch ( Exception e )
        {
            this.logger.error( e );

            if ( funcao.getLabelPadraoFun(  ) != null )
            {
                return f.getLabelPadraoFun(  );
            } else{
                return f.getNomeFun(  );
            }
        }
    }

    /**
     *
     * @author n/c
     * @param estrutura
     * @return String
     * @throws ECARException
     */
    public String getLabelQuantidadesPrevistas( EstruturaEtt estrutura )
                                        throws ECARException
    {
        FuncaoFun funcao = new FuncaoFun(  );
        funcao.setNomeFun( "Quantidades_Previstas" );

        List pesquisa = new FuncaoDao( request ).pesquisar( funcao,
                                                            new String[] { "nomeFun", "asc" } );
        FuncaoFun f = (FuncaoFun) pesquisa.iterator(  ).next(  );

        try
        {
            return getLabelFuncao( estrutura,
                                   f.getCodFun(  ) ).getLabelEttf(  );
        } catch ( Exception e )
        {
            this.logger.error( e );

            if ( f.getLabelPadraoFun(  ) != null )
            {
                return f.getLabelPadraoFun(  );
            } else{
                return f.getNomeFun(  );
            }
        }
    }

    /**
     *
     * @author n/c
     * @param estrutura
     * @return String
     * @throws ECARException
     */
    public String getLabelApontamentos( EstruturaEtt estrutura )
                                throws ECARException
    {
        FuncaoFun funcao = new FuncaoFun(  );
        funcao.setNomeFun( "Apontamentos" );

        List pesquisa = new FuncaoDao( request ).pesquisar( funcao,
                                                            new String[] { "nomeFun", "asc" } );
        FuncaoFun f = (FuncaoFun) pesquisa.iterator(  ).next(  );

        try
        {
            return getLabelFuncao( estrutura,
                                   f.getCodFun(  ) ).getLabelEttf(  );
        } catch ( Exception e )
        {
            this.logger.error( e );

            if ( funcao.getLabelPadraoFun(  ) != null )
            {
                return f.getLabelPadraoFun(  );
            } else
            {
                return f.getNomeFun(  );
            }
        }
    }

    /**
     *
     * @param estrutura
     * @return
     * @throws ECARException
     */
    public EstruturaFuncaoEttf getApontamentos( EstruturaEtt estrutura )
                                        throws ECARException
    {
        FuncaoFun funcao = new FuncaoFun(  );
        funcao.setNomeFun( "Apontamentos" );

        List pesquisa = new FuncaoDao( request ).pesquisar( funcao,
                                                            new String[] { "nomeFun", "asc" } );
        FuncaoFun f = (FuncaoFun) pesquisa.iterator(  ).next(  );

        try
        {
            return getLabelFuncao( estrutura,
                                   f.getCodFun(  ) );
        } catch ( ECARException e )
        {
            this.logger.error( e );

            return null;
        }
    }

    /**
     *
     * @param estrutura
     * @return
     * @throws ECARException
     */
    public EstruturaFuncaoEttf getQuantidadesPrevistas( EstruturaEtt estrutura )
                                                throws ECARException
    {
        FuncaoFun funcao = new FuncaoFun(  );
        funcao.setNomeFun( "Quantidades_Previstas" );

        List pesquisa = new FuncaoDao( request ).pesquisar( funcao,
                                                            new String[] { "nomeFun", "asc" } );
        FuncaoFun f = (FuncaoFun) pesquisa.iterator(  ).next(  );

        try
        {
            return getLabelFuncao( estrutura,
                                   f.getCodFun(  ) );
        } catch ( ECARException e )
        {
            this.logger.error( e );

            return null;
        }
    }

    /**
     *
     * @param estrutura
     * @return
     * @throws ECARException
     */
    public EstruturaFuncaoEttf getItensAnexo( EstruturaEtt estrutura )
                                      throws ECARException
    {
        FuncaoFun funcao = new FuncaoFun(  );
        funcao.setNomeFun( "Itens_de_Anexo" );

        List pesquisa = new FuncaoDao( request ).pesquisar( funcao,
                                                            new String[] { "nomeFun", "asc" } );
        FuncaoFun f = (FuncaoFun) pesquisa.iterator(  ).next(  );

        try
        {
            return getLabelFuncao( estrutura,
                                   f.getCodFun(  ) );
        } catch ( ECARException e )
        {
            this.logger.error( e );

            return null;
        }
    }

    /**
     *
     * @author Milton Pereira, Jos� Andr� e Thaise Dantas [SUNNE-SERPRO]
     * @param estrutura
     * @return String
     * @throws ECARException
     */
    public String getLabelFuncaoContasOrcamento( EstruturaEtt estrutura )
                                         throws ECARException
    {
        FuncaoFun funcao = new FuncaoFun(  );
        funcao.setNomeFun( "Contas_do_Orcamento" );

        List pesquisa = new FuncaoDao( request ).pesquisar( funcao,
                                                            new String[] { "nomeFun", "asc" } );
        FuncaoFun f = (FuncaoFun) pesquisa.iterator(  ).next(  );

        try
        {
            return getLabelFuncao( estrutura,
                                   f.getCodFun(  ) ).getLabelEttf(  );
        } catch ( Exception e )
        {
            this.logger.error( e );

            if ( funcao.getLabelPadraoFun(  ) != null )
            {
                return f.getLabelPadraoFun(  );
            } else{
                return f.getNomeFun(  );
            }
        }
    }

    /**
     * Verifica se tem permiss�o para exibir o hist�rico
     * @param estruturaFuncaoEttf
     * @return
     */
    public boolean permissaoExibirHistorico( EstruturaFuncaoEttf estruturaFuncaoEttf )
    {
        if ( estruturaFuncaoEttf != null )
        {
            if ( ( estruturaFuncaoEttf.getIndExibirHistoricoEttf(  ) != null ) &&
                     estruturaFuncaoEttf.getIndExibirHistoricoEttf(  ).equals( "S" ) )
            {
                return true;
            }
        }

        return false;
    }
    
    
    /**
     * Dada uma fun��o, verifica se a fun��o est� associada a estrutura.
     * @param estrutura
     * @param funcao
     * @return
     */
    public boolean ehFuncaoAssociadaEstrutura (EstruturaEtt estrutura, FuncaoFun funcao){
    	
    	boolean funcaoAssociada = false;
    	
    	List<FuncaoFun> funcoesAssociadas = getFuncoes(estrutura);
    	
    	for (FuncaoFun funcaoEstrutura : funcoesAssociadas) {
			if (funcao.equals(funcaoEstrutura)){
				funcaoAssociada = true;
				break;
			}
    		
		}
    	
    	return funcaoAssociada;
    }
    
    /**
     * Devolve a estruturaFuncao de acordo com a estrutura e fun��o passadas.
     * @param estrutura
     * 
     * @return EstruturaFuncaoEttf
     */
    public EstruturaFuncaoEttf getEstruturaFuncao(EstruturaEtt estruturaEtt, FuncaoFun funcaoFun) {
        EstruturaFuncaoEttf estruturaFuncaoEttf = null;

        if (estruturaEtt.getEstruturaFuncaoEttfs( ) != null ) {
            Iterator it = estruturaEtt.getEstruturaFuncaoEttfs().iterator();
            while (it.hasNext()) {
                EstruturaFuncaoEttf ettf = (EstruturaFuncaoEttf) it.next(  );
                if (ettf.getFuncaoFun().equals(funcaoFun)){
                	estruturaFuncaoEttf = ettf;
                	break;
                }
            }
        }
        return estruturaFuncaoEttf;
    }

    /**
     * Retorna a EstruturaFuncao referente a fun��o pai da sub-fun��o passada como par�metro.  
     * 
     * Ex.: fun��o apontamento enviada no parametro, EstruturaFuncao retornada contem a fun��o pai de apontamento, 
     * neste caso o ponto critico, para a estrutura . 
     * 
     * @param funcaoFun
     * @return
     * @throws ECARException 
     */
	public EstruturaFuncaoEttf buscarEstruturaFuncaoDaFuncaoPai(EstruturaEtt estruturaEtt, FuncaoFun funcaoFun) throws ECARException {
		
		StringBuffer hql = new StringBuffer();
		
		hql.append("select ef from EstruturaFuncaoEttf ef join ef.funcaoFun funcao where funcao.nomeFun = '");
	
		if (funcaoFun.getNomeFun().equals(FuncaoDao.NOME_FUNCAO_QUANTIDADES_PREVISTAS)) {
			hql.append(FuncaoDao.NOME_FUNCAO_METAS_INDICADORES);
			hql.append("'");
		} else if (funcaoFun.getNomeFun().equals(FuncaoDao.NOME_FUNCAO_APONTAMENTOS)) {
			hql.append(FuncaoDao.NOME_FUNCAO_PONTOS_CRITICOS);
			hql.append("'");
		} else {
			throw new ECARException("sub_funcao_invalida");
		}

		hql.append(" and ef.estruturaEtt.codEtt = ");
		hql.append (estruturaEtt.getCodEtt());
		
		List<EstruturaFuncaoEttf> listaEstruturaFuncao = super.consultarPorHQL(hql.toString());
	
		EstruturaFuncaoEttf retorno = null;
		
		if (!listaEstruturaFuncao.isEmpty()) {
			retorno = listaEstruturaFuncao.get(0);
		}
		

		return retorno;
	}
	
	/**
    *
    * @author n/c
    * @param estrutura
    * @return string
    * @throws ECARException
    */
   public String getLabelFuncaoMetasIndicadores( EstruturaEtt estrutura )
                                       throws ECARException
   {
       FuncaoFun funcao = new FuncaoFun(  );
       funcao.setNomeFun( FuncaoDao.NOME_FUNCAO_METAS_INDICADORES );

       List pesquisa = new FuncaoDao( request ).pesquisar( funcao,
                                                           new String[] { "nomeFun", "asc" } );
       FuncaoFun f = (FuncaoFun) pesquisa.iterator(  ).next(  );

       try
       {
           return getLabelFuncao( estrutura,
                                  f.getCodFun(  ) ).getLabelEttf(  );
       } catch ( Exception e )
       {
           this.logger.error( e );

           if ( funcao.getLabelPadraoFun(  ) != null )
           {
               return f.getLabelPadraoFun(  );
           } else{
               return f.getNomeFun(  );
           }
       }
   }

    
}
