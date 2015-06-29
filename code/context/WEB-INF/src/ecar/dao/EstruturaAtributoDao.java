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
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.CacheManagerImpl;
import comum.database.Dao;
import comum.util.Pagina;
import comum.util.Util;

import ecar.bean.AtributoEstruturaBean;
import ecar.exception.ECARException;
import ecar.pojo.AtributosAtb;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EstAtribTipoAcompEata;
import ecar.pojo.EstruturaAtributoEttat;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.FuncaoFun;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturaSisAtributoIettSatb;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.taglib.util.Input;
import ecar.util.Dominios;

/**
 * @author felipev, aleixo
 *
 */
public class EstruturaAtributoDao
    extends Dao
{
    /*private AtributoDao atributoDao = null;
    private EstruturaDao estruturaDao = null;*/

    // Constantes
    private ConfiguracaoCfg config;

    /**
     * Construtor. Chama o Session factory do Hibernate
     * @param request
     * @throws ECARException
     */
    public EstruturaAtributoDao( HttpServletRequest request )
                         throws ECARException
    {
        super(  );
        this.request = request;
        config = new ConfiguracaoDao( null ).getConfiguracao(  );

        /*atributoDao = new AtributoDao(this.request);
        estruturaDao = new EstruturaDao(this.request);*/
    }

    /**
     * Controla listas
     * @author n/c
     * @param request
     * @param listaEstrutura
     * @param listaAtributo
     * @param listaFuncao
     * @throws ECARException
     */
    public void controlaListas( HttpServletRequest request, List listaEstrutura, List listaAtributo, List listaFuncao )
                        throws ECARException
    {
        EstruturaEtt estruturaEtt = null;
        AtributosAtb atributosAtb = null;
        FuncaoFun funcaoFun = null;

        if ( Pagina.getParam( request, "estruturaEtt" ) != null )
        {
            estruturaEtt =
                (EstruturaEtt) new EstruturaDao( request ).buscar( EstruturaEtt.class,
                                                                   Long.valueOf( Pagina.getParam( request,
                                                                                                  "estruturaEtt" ) ) );
        }

        if ( Pagina.getParam( request, "funcaoFun" ) != null )
        {
            funcaoFun =
                (FuncaoFun) new FuncaoDao( request ).buscar( FuncaoFun.class,
                                                             Long.valueOf( Pagina.getParam( request, "funcaoFun" ) ) );
        }

        if ( Pagina.getParam( request, "atributosAtb" ) != null )
        {
            atributosAtb =
                (AtributosAtb) new AtributoDao( request ).buscar( AtributosAtb.class,
                                                                  Long.valueOf( Pagina.getParam( request, "atributosAtb" ) ) );
        }

        if ( ( estruturaEtt != null ) || ( atributosAtb != null ) )
        {
            listaFuncao.addAll( this.getFuncoes( estruturaEtt, atributosAtb ) );
        } else
        {
            FuncaoFun funcaoFunFiltro = new FuncaoFun(  );
            funcaoFunFiltro.setIndPossuiAtributos( "S" );
            listaFuncao.addAll( new FuncaoDao( request ).pesquisar( 
                                                                    funcaoFunFiltro,
                                                                    new String[] { "labelPadraoFun", "asc" } ) ); //listar(FuncaoFun.class, new String[]{"labelPadraoFun", "asc"}));
        }

        if ( ( estruturaEtt != null ) || ( funcaoFun != null ) )
        {
            listaAtributo.addAll( this.getAtributos( estruturaEtt, funcaoFun ) );
        } else
        {
            listaAtributo.addAll( new AtributoDao( request ).listar( 
                                                                     AtributosAtb.class,
                                                                     new String[] { "funcaoFun", "asc", "nomeAtb", "asc" } ) );
        }

        //Pesquisa apenas estruturas n�o-virtuais
        if ( ( funcaoFun != null ) || ( atributosAtb != null ) )
        {
            listaEstrutura.addAll( this.getEstruturasNaoVirtuais( funcaoFun, atributosAtb ) );
        } else
        {
            EstruturaEtt estruturaPesquisa = new EstruturaEtt(  );
            estruturaPesquisa.setVirtual( false );
            listaEstrutura.addAll( new EstruturaDao( request ).pesquisar( 
                                                                          estruturaPesquisa,
                                                                          new String[] { "nomeEtt", "asc" } ) ); //listar(EstruturaEtt.class, new String[]{"nomeEtt", "asc"}));
        }
    }

    /**
     *
     * @author n/c
     * @param request
     * @param estruturaAtributo
     * @throws ECARException
     * @throws HibernateException
     */
    public void alterar( HttpServletRequest request, EstruturaAtributoEttat estruturaAtributo )
                 throws ECARException, HibernateException
    {
        EstAtribTipoAcompEataDao eataDao = new EstAtribTipoAcompEataDao( null );
        TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao( null );
        Transaction tx = null;

        try
        {
            tx = this.session.beginTransaction(  );
            /*
             * Gravar a EstruturaAtributoEttat
             */
            this.setEstruturaAtributo( request, estruturaAtributo, true );

            session.update( estruturaAtributo );
            session.merge( estruturaAtributo );

//			Entidade.gerarHistorico(estruturaAtributo, Entidade.ALTERAR, Entidade.IETT_ITEM_ESTRUTURA, 
//					config, request, session);

            /* Gravar os objetos TipoAcompanhamentoTa
             * 1 - Pegar pelo request quantos TipoAcompanhamentoTa foram informados na tela
             */
            String codTas = Pagina.getParamStr( request, "codTas" );
            ArrayList<Long> tas = new ArrayList<Long>();

            String codTemp = "";

            for ( int i = 0; i < codTas.length(  ); i++ ){
                if ( ! codTas.substring( i, i + 1 ).equals( "|" ) ){
                    codTemp = codTemp + codTas.substring( i, i + 1 );
                } else {
                    if ( ! codTemp.trim(  ).equals( "" ) ){
                        tas.add( Long.valueOf( codTemp ) );
                        codTemp = "";
                    }
                }
            }

            Iterator it = tas.iterator(  );

            while ( it.hasNext(  ) )
            {
                Long codigoTas = (Long) it.next(  );

                //System.out.println("tas: " + codigoTas);
                /*
                 * 2 - Para cada TipoAcompanhamento, buscar o tipo.
                 */
                TipoAcompanhamentoTa ta =
                    (TipoAcompanhamentoTa) taDao.buscar( TipoAcompanhamentoTa.class,
                                                         Long.valueOf( codigoTas ) );

                if ( ta != null )
                {
                    /* 3 - Estanciar objeto EstAtribTipoAcompEata setando os valores vindos do request */
                    EstAtribTipoAcompEata eata = eataDao.getEstAtribTipoAcompEata( estruturaAtributo, ta );

                    if ( eata == null )
                    {
                        eata = new EstAtribTipoAcompEata(  );
                        eata.setEstruturaAtributoEttat( estruturaAtributo );
                        eata.setTipoAcompanhamentoTa( ta );
                    }

                    String exibirEata = Pagina.getParamStr( request, "exibirEata" + ta.getCodTa(  ).toString(  ));
                    
                    if (Dominios.STRING_VAZIA.equals(exibirEata)){
                    	exibirEata = Dominios.NAO;
                    }
                    
                    eata.setExibirEata(exibirEata);

                    if ( ! "".equals( Pagina.getParamStr( request, "sequenciaEata" + ta.getCodTa(  ).toString(  ) ) ) ){
                        eata.setSequenciaEata( Long.valueOf( Pagina.getParamStr( request,
                                                                                 "sequenciaEata" +
                                                                                 ta.getCodTa(  ).toString(  ) ) ) );
                    } else {
                        eata.setSequenciaEata( null );
                    }

                    //SERPRO
                    String filtroStr = Pagina.getParamStr( request, "filtroEata" + ta.getCodTa(  ).toString(  )); 
                    
                    if (Dominios.STRING_VAZIA.equals(filtroStr)){
                    	filtroStr = Dominios.NAO;
                    } 
                    
                    eata.setFiltroEata(filtroStr);

                    if ( eata.getCodEata(  ) == null )
                    {
                        session.save( eata );
                    } else
                    {
                        session.update( eata );
                    }
                }
            }

            tx.commit(  );
        } catch ( Exception e )
        {
            if ( tx != null )
            {
                tx.rollback(  );
            }

            this.logger.error( e );
            throw new ECARException( "erro.hibernateException" );
        }
    }

    /**
     *
     * @author n/c
     * @param request
     * @param estruturaAtributo
     * @param usarGetParamStr
     * @throws ECARException
     */
    public void setEstruturaAtributo( HttpServletRequest request, EstruturaAtributoEttat estruturaAtributo,boolean usarGetParamStr )throws ECARException{
    	
        if ( Pagina.getParam( request, "estruturaEtt" ) != null ){
            estruturaAtributo.setEstruturaEtt( (EstruturaEtt) ( new EstruturaDao( request ).buscar(EstruturaEtt.class,Long.valueOf( Pagina.getParam( request,"estruturaEtt" ) ) )) );
        }

        if ( Pagina.getParam( request, "atributosAtb" ) != null ){
            estruturaAtributo.setAtributosAtb( (AtributosAtb) new AtributoDao( request ).buscar(AtributosAtb.class,Long.valueOf( Pagina.getParam( request,"atributosAtb" ) ) ) );
        }

        /*LUANA*/
        if ( Pagina.getParam( request, "labelTamanhoConteudo" ) != null ){
            estruturaAtributo.setTamanhoConteudoAtribEttat( Integer.valueOf( Pagina.getParamStr( request,"labelTamanhoConteudo" ) ) );
        } else {
            estruturaAtributo.setTamanhoConteudoAtribEttat( null );
        }

        if ( usarGetParamStr ){
            estruturaAtributo.setLabelEstruturaEttat( Pagina.getParamStr( request, "labelEstruturaEttat" ).trim(  ) );
            estruturaAtributo.setIndObrigatorioEttat( Pagina.getParamOrDefault( request, "indObrigatorioEttat",Pagina.NAO ) );
            estruturaAtributo.setIndPodeBloquearEttat( Pagina.getParamOrDefault( request, "indPodeBloquearEttat",Pagina.NAO ) );
            estruturaAtributo.setIndFiltroEttat( Pagina.getParamOrDefault( request, "indFiltroEttat", Pagina.NAO ) );
            estruturaAtributo.setIndListagemTelaEttat( Pagina.getParamOrDefault( request, "indListagemTelaEttat",Pagina.NAO ) );
            estruturaAtributo.setIndRevisaoEttat( Pagina.getParamOrDefault( request, "indRevisaoEttat", Pagina.NAO ) );
            estruturaAtributo.setIndListagemImpressCompEtta( Pagina.getParamOrDefault( request,"indListagemImpressCompEtta",Pagina.NAO ) );
            estruturaAtributo.setIndListagemImpressaResEtta( Pagina.getParamOrDefault( request,"indListagemImpressaResEtta",Pagina.NAO ) );
            estruturaAtributo.setIndRelacaoImpressaEttat( Pagina.getParamOrDefault( request, "indRelacaoImpressaEttat",Pagina.NAO ) );
            estruturaAtributo.setIndListagemArvoreEttat( Pagina.getParamOrDefault( request, "indListagemArvoreEttat",Pagina.NAO ) );
        } else {
            estruturaAtributo.setLabelEstruturaEttat( Pagina.getParam( request, "labelEstruturaEttat" ) );
            estruturaAtributo.setIndObrigatorioEttat( Pagina.getParam( request, "indObrigatorioEttat" ) );
            estruturaAtributo.setIndPodeBloquearEttat( Pagina.getParam( request, "indPodeBloquearEttat" ) );
            estruturaAtributo.setIndFiltroEttat( Pagina.getParam( request, "indFiltroEttat" ) );
            estruturaAtributo.setIndListagemTelaEttat( Pagina.getParam( request, "indListagemTelaEttat" ) );
            estruturaAtributo.setIndRevisaoEttat( Pagina.getParam( request, "indRevisaoEttat" ) );
            estruturaAtributo.setIndListagemImpressCompEtta( Pagina.getParam( request, "indListagemImpressCompEtta" ) );
            estruturaAtributo.setIndListagemImpressaResEtta( Pagina.getParam( request, "indListagemImpressaResEtta" ) );
            estruturaAtributo.setIndRelacaoImpressaEttat( Pagina.getParam( request, "indRelacaoImpressaEttat" ) );
            estruturaAtributo.setIndListagemArvoreEttat( Pagina.getParam( request, "indListagemArvoreEttat" ) );
        }

        if ( Pagina.getParam( request, "seqApresentTelaCampoEttat" ) != null ) {
            estruturaAtributo.setSeqApresentTelaCampoEttat( Integer.valueOf( Pagina.getParam( request,"seqApresentTelaCampoEttat" ) ) );
        }

        /* campos abaixo n�o s�o obrigat�rios */
        if ( Pagina.getParam( request, "larguraListagemTelaEttat" ) != null ){
            estruturaAtributo.setLarguraListagemTelaEttat( Integer.valueOf( Pagina.getParam( request,"larguraListagemTelaEttat" ) ) );
        } else{
            estruturaAtributo.setLarguraListagemTelaEttat( null );
        }

        if ( Pagina.getParam( request, "seqApresListagemTelaEttat" ) != null ){
            estruturaAtributo.setSeqApresListagemTelaEttat( Integer.valueOf( Pagina.getParam( request,"seqApresListagemTelaEttat" ) ) );
        } else{
            estruturaAtributo.setSeqApresListagemTelaEttat( null );
        }

        if ( Pagina.getParam( request, "dicaEttat" ) != null ){
            estruturaAtributo.setDicaEttat( Pagina.getParam( request, "dicaEttat" ) );
        } else{
            estruturaAtributo.setDicaEttat( null );
        }

        if ( Pagina.getParam( request, "documentacaoEttat" ) != null ){
            estruturaAtributo.setDocumentacaoEttat( Pagina.getParam( request, "documentacaoEttat" ) );
        } else{
            estruturaAtributo.setDocumentacaoEttat( null );
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
            Long codTpfa = null;

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
     * Retorna a Label de um atributo em uma estrutura
     * @param nomeAtributo
     * @param estrutura
     * @return
     * @throws ECARException
     */
    public String getLabelAtributoEstrutura( String nomeAtributo, EstruturaEtt estrutura )
                                     throws ECARException
    {
        AtributosAtb atributo = new AtributosAtb(  );
        atributo.setNomeAtb( nomeAtributo );

        List atb = super.pesquisar( atributo, null );

        if ( atb.size(  ) > 0 )
        {
            atributo = (AtributosAtb) atb.get( 0 );

            EstruturaAtributoEttat estruturaAtributo = new EstruturaAtributoEttat(  );
            estruturaAtributo.setAtributosAtb( atributo );
            estruturaAtributo.setEstruturaEtt( estrutura );

            List estAtb = super.pesquisar( estruturaAtributo, null );

            if ( estAtb.size(  ) > 0 )
            {
                return ( (EstruturaAtributoEttat) estAtb.get( 0 ) ).getLabelEstruturaEttat(  );
            }
            else if (atributo.getLabelPadraoAtb() != null && !atributo.getLabelPadraoAtb().equals("")){
            	return atributo.getLabelPadraoAtb();
            	
            }else{
                return "";
            }
        }

        return "";
    }

    /**
     * Devolve uma lista com todas os atributos vinculados a uma estrutura
     * @param estruturaEtt
     * @return List de AtributosAtb
     * @throws ECARException
     */
    public List getAtributos( EstruturaEtt estruturaEtt )
                      throws ECARException
    {
        return this.getAtributos( estruturaEtt, null );
    }

    /**
     * Devolve uma lista com todos os atributos vinculados a uma estrutura e uma fun��o
     * @param estruturaEtt
     * @param funcaoFun
     * @return List de AtributosAtb
     * @throws ECARException
     */
    public List getAtributos( EstruturaEtt estruturaEtt, FuncaoFun funcaoFun )
                      throws ECARException
    {
        List retorno = new ArrayList(  );

        if ( ( estruturaEtt != null ) && ( estruturaEtt.getEstruturaAtributoEttats(  ) != null ) )
        {
            Iterator itEstruturaAtributoEttats = estruturaEtt.getEstruturaAtributoEttats(  ).iterator(  );

            while ( itEstruturaAtributoEttats.hasNext(  ) )
            {
                EstruturaAtributoEttat estruturaAtributoEttat =
                    (EstruturaAtributoEttat) itEstruturaAtributoEttats.next(  );

                if ( funcaoFun != null )
                {
                    if ( ! estruturaAtributoEttat.getAtributosAtb(  ).getFuncaoFun(  ).equals( funcaoFun ) )
                    {
                        continue;
                    }
                }

                retorno.add( estruturaAtributoEttat.getAtributosAtb(  ) );
            }
        } else if ( funcaoFun != null )
        {
            if ( funcaoFun.getAtributosAtbs(  ) != null )
            {
                retorno.addAll( funcaoFun.getAtributosAtbs(  ) );
            }
        }

        return retorno;
    }
    
    /**
     * Retorna um list com identifica��es de todos os Atributos de um grupo de atributos
     * 
     * @param estrutura
     * @param funcaoFun
     * @return List de Long
     */
    public List getAtributosById(Set<SisAtributoSatb> atributos) {
        List lAtributos = new ArrayList();
        if (atributos != null) {
            Iterator it = atributos.iterator();
            while (it.hasNext()) {
            	SisAtributoSatb atributo = (SisAtributoSatb) it.next();
               	lAtributos.add(atributo.getCodSatb());
            }
        }
        return lAtributos;
    }

    /**
     * Devolve uma lista com todos os atributos de uma estrutura e uma fun��o que podem ser visualizados de acordo com estrutura superior
     * @param estruturaEtt
     * @param funcaoFun
     * @return List de AtributosAtb
     * @throws ECARException
     */
    public List<AtributosAtb> getAtributosVisualizarEttSuperior(EstruturaEtt estruturaEtt, FuncaoFun funcaoFun) throws ECARException {
        List<AtributosAtb> retorno = new ArrayList<AtributosAtb>();

        if((estruturaEtt != null) && (estruturaEtt.getEstruturaAtributoEttats() != null)) {
        	EstruturaEtt estrutura = (EstruturaEtt)this.buscar(EstruturaEtt.class, estruturaEtt.getCodEtt());
            Iterator<EstruturaAtributoEttat> itEstruturaAtributoEttats = estrutura.getEstruturaAtributoEttats().iterator();

            while(itEstruturaAtributoEttats.hasNext()) {
                EstruturaAtributoEttat estruturaAtributoEttat = (EstruturaAtributoEttat) itEstruturaAtributoEttats.next();
                if(funcaoFun != null) {
                    if(!estruturaAtributoEttat.getAtributosAtb().getFuncaoFun().equals(funcaoFun)) {
                        continue;
                    }
                }
                AtributosAtb atributoAtb = estruturaAtributoEttat.getAtributosAtb(); 
                if( atributoAtb.getSisGrupoAtributoSga() != null && atributoAtb.getIndAtivoAtb().equals(Dominios.ATIVO) && 
                	temAtributosAtivos(atributoAtb.getSisGrupoAtributoSga().getSisAtributoSatbs()) &&
                	(atributoAtb.getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.CHECKBOX)) || 
                	atributoAtb.getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.COMBOBOX)) || 
                	atributoAtb.getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.LISTBOX)) || 
                	atributoAtb.getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.RADIO_BUTTON))) ) {
                		retorno.add(atributoAtb);
                }
            }
        } else if(funcaoFun != null) {
        	if(funcaoFun.getAtributosAtbs() != null) {
        		Iterator<AtributosAtb> itAtributosAtb = funcaoFun.getAtributosAtbs().iterator();
        		while(itAtributosAtb.hasNext()) {
        			AtributosAtb atributoAtb = (AtributosAtb) itAtributosAtb.next();
                    if(funcaoFun != null) {
                        if(!atributoAtb.getFuncaoFun().equals(funcaoFun)) {
                            continue;
                        }
                    } 
                    if( atributoAtb.getSisGrupoAtributoSga() != null && atributoAtb.getIndAtivoAtb().equals(Dominios.ATIVO) &&
                    	temAtributosAtivos(atributoAtb.getSisGrupoAtributoSga().getSisAtributoSatbs()) &&
                        (atributoAtb.getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.CHECKBOX)) || 
                        atributoAtb.getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.COMBOBOX)) || 
                        atributoAtb.getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.LISTBOX)) || 
                        atributoAtb.getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.RADIO_BUTTON))) ) {
                        	retorno.add(atributoAtb);
                    }
                }
            }
        }

        return retorno;
    }
    
    private boolean temAtributosAtivos(Set<SisAtributoSatb> setSisAtributoSatb) {
    	boolean retorno = false;
    	if(setSisAtributoSatb != null && !setSisAtributoSatb.isEmpty()) {
	    	Iterator<SisAtributoSatb> itSisAtributoSatb = setSisAtributoSatb.iterator();
	    	while(itSisAtributoSatb.hasNext()) {
	    		SisAtributoSatb sisAtributoAtb = (SisAtributoSatb)itSisAtributoSatb.next();  
	    		if(sisAtributoAtb.isAtivo()) {
	    			retorno = true;
	    			break;
	    		}
	    	}
    	}
    	return retorno;
    }
    
    /**
     * Devolve uma lista com todas as estruturas vinculadas a um atributo e a uma fun��o
     * @param funcaoFun
     * @param atributosAtb
     * @return List de EstruturaEtt
     * @throws ECARException
     */
    public List getEstruturas( FuncaoFun funcaoFun, AtributosAtb atributosAtb )
                       throws ECARException
    {
        List retorno = new ArrayList(  );

        if ( ( atributosAtb != null ) && ( atributosAtb.getEstruturaAtributoEttats(  ) != null ) )
        {
            Iterator itEstruturaAtributoEttats = atributosAtb.getEstruturaAtributoEttats(  ).iterator(  );

            while ( itEstruturaAtributoEttats.hasNext(  ) )
            {
                EstruturaAtributoEttat estruturaAtributoEttat =
                    (EstruturaAtributoEttat) itEstruturaAtributoEttats.next(  );

                if ( funcaoFun != null )
                {
                    if ( ! estruturaAtributoEttat.getAtributosAtb(  ).getFuncaoFun(  ).equals( funcaoFun ) )
                    {
                        continue;
                    }
                }

                retorno.add( estruturaAtributoEttat.getEstruturaEtt(  ) );
            }
        } else if ( ( funcaoFun != null ) && ( funcaoFun.getEstruturaFuncaoEttfs(  ) != null ) )
        {
            Iterator itEstruturaFuncaoEttfs = funcaoFun.getEstruturaFuncaoEttfs(  ).iterator(  );

            while ( itEstruturaFuncaoEttfs.hasNext(  ) )
            {
                retorno.add( ( (EstruturaFuncaoEttf) itEstruturaFuncaoEttfs.next(  ) ).getEstruturaEtt(  ) );
            }
        }

        return retorno;
    }

    /**
     * Devolve uma lista com todas as estruturas vinculadas a um atributo e a uma fun��o
     * @param funcaoFun
     * @param atributosAtb
     * @return List de EstruturaEtt
     * @throws ECARException
     */
    public List getEstruturasNaoVirtuais( FuncaoFun funcaoFun, AtributosAtb atributosAtb )
                                  throws ECARException
    {
        List retorno = new ArrayList(  );

        if ( ( atributosAtb != null ) && ( atributosAtb.getEstruturaAtributoEttats(  ) != null ) )
        {
            Iterator itEstruturaAtributoEttats = atributosAtb.getEstruturaAtributoEttats(  ).iterator(  );

            while ( itEstruturaAtributoEttats.hasNext(  ) )
            {
                EstruturaAtributoEttat estruturaAtributoEttat =
                    (EstruturaAtributoEttat) itEstruturaAtributoEttats.next(  );

                if ( funcaoFun != null )
                {
                    if ( ! estruturaAtributoEttat.getAtributosAtb(  ).getFuncaoFun(  ).equals( funcaoFun ) )
                    {
                        continue;
                    }
                }

                if ( ! estruturaAtributoEttat.getEstruturaEtt(  ).isVirtual(  ) )
                {
                    retorno.add( estruturaAtributoEttat.getEstruturaEtt(  ) );
                }
            }
        } else if ( ( funcaoFun != null ) && ( funcaoFun.getEstruturaFuncaoEttfs(  ) != null ) )
        {
            Iterator itEstruturaFuncaoEttfs = funcaoFun.getEstruturaFuncaoEttfs(  ).iterator(  );

            while ( itEstruturaFuncaoEttfs.hasNext(  ) )
            {
                EstruturaEtt estrutura = ( (EstruturaFuncaoEttf) itEstruturaFuncaoEttfs.next(  ) ).getEstruturaEtt(  );

                if ( ! estrutura.isVirtual(  ) )
                {
                    retorno.add( estrutura );
                }
            }
        }

        return retorno;
    }

    /**
     * Devolve uma lista com todas os atributos vinculados a uma estrutura e uma fun��o
     * @param estruturaEtt
     * @param atributosAtb
     * @return List de AtributosAtb
     * @throws ECARException
     */
    public List getFuncoes( EstruturaEtt estruturaEtt, AtributosAtb atributosAtb )
                    throws ECARException
    {
        List retorno = new ArrayList(  );

        if ( ( estruturaEtt != null ) && ( estruturaEtt.getEstruturaFuncaoEttfs(  ) != null ) )
        {
            Iterator itEstruturaFuncaoEttfs = estruturaEtt.getEstruturaFuncaoEttfs(  ).iterator(  );

            while ( itEstruturaFuncaoEttfs.hasNext(  ) )
            {
                EstruturaFuncaoEttf estruturaFuncaoEttf = (EstruturaFuncaoEttf) itEstruturaFuncaoEttfs.next(  );

                if ( atributosAtb != null )
                {
                    if ( ! estruturaFuncaoEttf.getFuncaoFun(  ).getAtributosAtbs(  ).contains( atributosAtb ) )
                    {
                        continue;
                    }
                }

                if ( ( estruturaFuncaoEttf.getFuncaoFun(  ).getIndPossuiAtributos(  ) == null ) ||
                         ! estruturaFuncaoEttf.getFuncaoFun(  ).getIndPossuiAtributos(  ).equals( "S" ) )
                {
                    continue;
                }

                retorno.add( estruturaFuncaoEttf.getFuncaoFun(  ) );
            }
        } else if ( ( atributosAtb != null ) && ( atributosAtb.getFuncaoFun(  ) != null ) )
        {
            retorno.add( atributosAtb.getFuncaoFun(  ) );
        }

        return retorno;
    }

    /**
     * Retorna uma lista de objetos AtributoEstruturaBean, contendo os atributos v�lido de cada estrutura
     * e sua respectiva estrutura.
     * @param tipoAcomp
     * @return List<AtributoEstruturaBean>
     * @throws ECARException
     */
    public List getAtributosValidoEstruturas( TipoAcompanhamentoTa tipoAcomp )
                                      throws ECARException
    {
        List retorno = new ArrayList(  );

        List atributos = null;

        if ( tipoAcomp == null )
        {
            atributos = this.getEstruturaAtributoEttatByEstruturaEtt( null );
        } else
        {
            atributos = this.getEstruturaAtributoEttatByTipoAcompanhamentoTa( tipoAcomp );
        }

        if ( ( atributos != null ) && ! atributos.isEmpty(  ) )
        {
            Iterator itAtributos = atributos.iterator(  );

            while ( itAtributos.hasNext(  ) )
            {
                EstruturaAtributoEttat atributo = (EstruturaAtributoEttat) itAtributos.next(  );
                AtributoEstruturaBean aeBean = new AtributoEstruturaBean(  );
                aeBean.setAtributo( atributo );
                aeBean.setEstrutura( atributo.getEstruturaEtt(  ) );
                retorno.add( aeBean );
            }
        }

        return retorno;
    }

    /**
     * Retorna uma string com os atributos definidos
     * com "S" no campo IndListagemTelaEttat de EstruturaAtributoEttat,
     * ordenados pela sequ�ncia de apresenta��o.
     * @param item
     * @param tipoAcomp
     * @return String
     * @throws ECARException
     */
    public String getDescricaoItemByAtributo( ItemEstruturaIett item, TipoAcompanhamentoTa tipoAcomp )
                                      throws ECARException
    {
        ItemEstruturaDao itemDao = new ItemEstruturaDao( null );
        FuncaoDao funcaoDao = new FuncaoDao( null );
        String retorno = "";

        List ettats = null;

        if ( tipoAcomp == null )
        {
            ettats = this.getEstruturaAtributoEttatByEstruturaEtt( item.getEstruturaEtt(  ) );
        } else
        {
        	   // Implementação de cache de segundo nivel
        	   List list;
        	   String cacheId = tipoAcomp.getCodTa().toString();
        	   list = (List) CacheManagerImpl.get(cacheId);
        	   if (list == null) {
        	    list = this.getEstruturaAtributoEttatByTipoAcompanhamentoTa( tipoAcomp );
        	    CacheManagerImpl.add(cacheId, list);
        	   }
        	   ettats = list;
        }

        FuncaoFun funcaoFun = funcaoDao.getFuncaoDadosGerais(  );

        if ( ( ettats != null ) && ! ettats.isEmpty(  ) )
        {
            Iterator it = ettats.iterator(  );

            while ( it.hasNext(  ) )
            {
                EstruturaAtributoEttat atributo = (EstruturaAtributoEttat) it.next(  );

                if ( ( funcaoFun != null ) && ! atributo.getAtributosAtb(  ).getFuncaoFun(  ).equals( funcaoFun ) )
                {
                    continue;
                }

                if ( ( tipoAcomp != null ) && ! item.getEstruturaEtt(  ).equals( atributo.getEstruturaEtt(  ) ) )
                {
                    continue;
                }

                if ( "nivelPlanejamento".equals( atributo.iGetNome(  ) ) )
                {
                    String niveis = "";

                    if ( ( item.getItemEstruturaNivelIettns(  ) != null ) &&
                             ! item.getItemEstruturaNivelIettns(  ).isEmpty(  ) )
                    {
                        Iterator itNiveis = item.getItemEstruturaNivelIettns(  ).iterator(  );

                        while ( itNiveis.hasNext(  ) )
                        {
                            SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next(  );
                            niveis += ( nivel.getDescricaoSatb(  ) + config.getSeparadorCampoMultivalor() );
                        }

                        niveis =
                            niveis.substring( 0,
                                              niveis.lastIndexOf( config.getSeparadorCampoMultivalor() ) );
                    }

                    retorno += ( niveis + " - " );
                } else if ( atributo.iGetGrupoAtributosLivres(  ) != null )
                {
                    Iterator itIettSatbs = item.getItemEstruturaSisAtributoIettSatbs(  ).iterator(  );
                    String informacaoIettSatb = "";

                    while ( itIettSatbs.hasNext(  ) )
                    {
                        ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributoIettSatb =
                            (ItemEstruturaSisAtributoIettSatb) itIettSatbs.next(  );

                        if ( itemEstruturaSisAtributoIettSatb.getSisAtributoSatb(  ).getSisGrupoAtributoSga(  )
                                                                 .equals( atributo.iGetGrupoAtributosLivres(  ) ) )
                        {
                            if ( atributo.iGetGrupoAtributosLivres(  ).getSisTipoExibicGrupoSteg(  ).getCodSteg(  )
                                             .equals( new Long( Input.TEXT ) ) ||
                                     atributo.iGetGrupoAtributosLivres(  ).getSisTipoExibicGrupoSteg(  ).getCodSteg(  )
                                                 .equals( new Long( Input.TEXTAREA ) ) ||
                                     atributo.iGetGrupoAtributosLivres(  ).getSisTipoExibicGrupoSteg(  ).getCodSteg(  )
                                                 .equals( new Long( Input.MULTITEXTO ) ) ||
                                     atributo.iGetGrupoAtributosLivres(  ).getSisTipoExibicGrupoSteg(  ).getCodSteg(  )
                                                 .equals( new Long( Input.VALIDACAO ) ) )
                            {
                                informacaoIettSatb = informacaoIettSatb +
                                                     itemEstruturaSisAtributoIettSatb.getInformacao(  ) + config.getSeparadorCampoMultivalor();
                            } else if ( ! atributo.iGetGrupoAtributosLivres(  ).getSisTipoExibicGrupoSteg(  )
                                                      .getCodSteg(  ).equals( new Long( Input.IMAGEM ) ) )
                            {
                                //se for do tipo imagem n�o faz nada, deixa em branco.
                                informacaoIettSatb = informacaoIettSatb +
                                                     itemEstruturaSisAtributoIettSatb.getSisAtributoSatb(  )
                                                                                     .getDescricaoSatb(  ) + config.getSeparadorCampoMultivalor()/*"; "*/;
                            }
                        }
                    }

                    if ( informacaoIettSatb.length(  ) > 0 )
                    {
                        informacaoIettSatb = informacaoIettSatb.substring( 0, informacaoIettSatb.length(  ) - config.getSeparadorCampoMultivalor().length() );
                    }

                    retorno += ( informacaoIettSatb + " - " );
                } else
                {
                    retorno += ( 
                                   itemDao.getValorAtributoItemEstrutura( item,
                                                                          atributo.getAtributosAtb(  ).getNomeAtb(  ),
                                                                          atributo.getAtributosAtb(  ).getNomeFkAtb(  ) ) +
                                   " - "
                                );
                }
            }
        }

        if ( retorno.length(  ) > 0 )
        {
            retorno =
                retorno.substring( 0,
                                   retorno.lastIndexOf( " - " ) );
        }

        return retorno;
    }

    /**
     * Retorna uma Lista de EstruturaAtributoEttat, para uma EstruturaEtt.
     * @param estrutura
     * @return
     * @throws ECARException
     */
    public List getEstruturaAtributoEttatByEstruturaEtt( EstruturaEtt estrutura )
                                                 throws ECARException
    {
        try
        {
            String select =
                "select ettat from EstruturaAtributoEttat ettat " + "where ettat.indListagemTelaEttat = 'S'";

            if ( estrutura != null )
            {
                select += "  and ettat.estruturaEtt.codEtt = :codEtt";
            }

            select += " order by ettat.seqApresListagemTelaEttat asc";

            Query q = this.session.createQuery( select );

            if ( estrutura != null )
            {
                q.setLong( "codEtt",
                           estrutura.getCodEtt(  ).longValue(  ) );
            }

            return q.list(  );
        } catch ( HibernateException e )
        {
            this.logger.error( e );
            throw new ECARException( "erro.hibernateException" );
        }
    }

    /**
     * Retorna uma Lista de EstruturaAtributoEttat, para um TipoAcompanhamentoTa.
     * @param tipoAcomp
     * @return List
     * @throws ECARException
     */
    public List getEstruturaAtributoEttatByTipoAcompanhamentoTa( TipoAcompanhamentoTa tipoAcomp )
        throws ECARException
    {
        List retorno = new ArrayList(  );

        try
        {
            StringBuilder select =
                new StringBuilder( "select eata from EstAtribTipoAcompEata eata " ).append( " 	where eata.tipoAcompanhamentoTa.codTa = :codTa " )
                                                                                   .append( "     and eata.exibirEata = 'S' " )
                                                                                   .append( " order by eata.sequenciaEata asc" );

            Query q = this.session.createQuery( select.toString(  ) );

            q.setLong( "codTa",
                       tipoAcomp.getCodTa(  ).longValue(  ) );

            List eatas = q.list(  );

            if ( ( eatas != null ) && ! eatas.isEmpty(  ) )
            {
                Iterator it = eatas.iterator(  );

                while ( it.hasNext(  ) )
                {
                    EstAtribTipoAcompEata eata = (EstAtribTipoAcompEata) it.next(  );
                    retorno.add( eata.getEstruturaAtributoEttat(  ) );
                }
            }
        } catch ( HibernateException e )
        {
            this.logger.error( e );
            throw new ECARException( "erro.hibernateException" );
        }

        return retorno;
    }

    /**
     * Retorna a lista de EstruturaAtributoEttat buscando pelo TipoAcompanhamentoTa, exibirEata, sequenciaEata e filtroEata.
     * @author egger - SERPRO
     * @param tipoAcomp
     * @param exibirEata
     * @param sequenciaEata
     * @param filtroEata
     * @return List
     * @throws ECARException
     */
    public List getEstruturaAtributoEttatByEata( TipoAcompanhamentoTa tipoAcomp, String exibirEata, Long sequenciaEata,
                                                 String filtroEata )
                                         throws ECARException
    {
        List retorno = new ArrayList(  );

        try
        {
            String select = "select eata from EstAtribTipoAcompEata eata ";
            String where = "";

            if ( tipoAcomp != null )
            {
                where += "where eata.tipoAcompanhamentoTa.codTa = :tipoAcomp";
            }

            if ( ! "".equals( exibirEata ) )
            {
                if ( "".equals( where ) )
                {
                    where += " where ";
                } else
                {
                    where += " and ";
                }

                where += " eata.exibirEata = :exibirEata";
            }

            if ( sequenciaEata != null )
            {
                if ( "".equals( where ) )
                {
                    where += " where ";
                } else
                {
                    where += " and ";
                }

                where += " eata.sequenciaEata = :sequenciaEata";
            }

            //SERPRO
            if ( ! "".equals( filtroEata ) )
            {
                if ( "".equals( where ) )
                {
                    where += " where ";
                } else
                {
                    where += " and ";
                }

                where += " eata.filtroEata = :filtroEata";
            }

            Query q = this.session.createQuery( select + where );

            if ( tipoAcomp != null )
            {
                q.setLong( "tipoAcomp",
                           tipoAcomp.getCodTa(  ).longValue(  ) );
            }

            if ( ! "".equals( exibirEata ) )
            {
                q.setString( "exibirEata", exibirEata );
            }

            if ( sequenciaEata != null )
            {
                q.setLong( "sequenciaEata",
                           sequenciaEata.longValue(  ) );
            }

            //SERPRO
            if ( ! "".equals( filtroEata ) )
            {
                q.setString( "filtroEata", filtroEata );
            }

            List eatas = q.list(  );

            if ( ( eatas != null ) && ! eatas.isEmpty(  ) )
            {
                Iterator it = eatas.iterator(  );

                while ( it.hasNext(  ) )
                {
                    EstAtribTipoAcompEata eata = (EstAtribTipoAcompEata) it.next(  );
                    retorno.add( eata.getEstruturaAtributoEttat(  ) );
                }
            }
        } catch ( HibernateException e )
        {
            this.logger.error( e );
            throw new ECARException( "erro.hibernateException" );
        }

        return retorno;
    }
    
    /**
     * Filtra EstruturaAtributoEttat considerando os Tipos de Acompanhamentos definidos na tela (EstAtribTipoAcompEata).
     * @param estruturaAtributos
     * @param estAtribTipoAcomp
     * @param funcaoFun
     * @return List
     * @throws ECARException
     */
    public List filtrarEstruturaAtributoByEata( List estruturaAtributos, List estAtribTipoAcomp, FuncaoFun funcaoFun )
                                        throws ECARException
    {
        boolean possuiTiposAcomps = ( ( estAtribTipoAcomp != null ) && ( estAtribTipoAcomp.size(  ) > 0 ) ) ? true : false;
        boolean possuiEttats = ( ( estruturaAtributos != null ) && ( estruturaAtributos.size(  ) > 0 ) ) ? true : false;

        if ( funcaoFun != null ){
            Iterator itEstruturaAtributos = estruturaAtributos.iterator(  );

            while ( itEstruturaAtributos.hasNext(  ) ){
                EstruturaAtributoEttat estruturaAtributoEttat = (EstruturaAtributoEttat) itEstruturaAtributos.next(  );

                if ( ! estruturaAtributoEttat.getAtributosAtb(  ).getFuncaoFun(  ).equals( funcaoFun ) ){
                    itEstruturaAtributos.remove(  );
                }
            }
        }

        if ( possuiTiposAcomps && possuiEttats ){
            List estruturaAtributosFiltrados = new ArrayList(  );
            Iterator it = estAtribTipoAcomp.iterator(  );

            while ( it.hasNext(  ) ){
                EstAtribTipoAcompEata eata = (EstAtribTipoAcompEata) it.next(  );

                // SERPRO add filtroEata
                List ettats = this.getEstruturaAtributoEttatByEata( eata.getTipoAcompanhamentoTa(  ),
                                                          eata.getExibirEata(  ),
                                                          eata.getSequenciaEata(  ),
                                                          eata.getFiltroEata(  ) );
                Iterator itEt = ettats.iterator(  );

                while ( itEt.hasNext(  ) ){
                    EstruturaAtributoEttat ettat = (EstruturaAtributoEttat) itEt.next(  );

                    if ( ! estruturaAtributosFiltrados.contains( ettat ) ){
                        estruturaAtributosFiltrados.add( ettat );
                    }
                }
            }

            return new ArrayList( Util.intersecao( estruturaAtributos, estruturaAtributosFiltrados ) );
        } else {
            return estruturaAtributos;
        }
    }
    
    /**
     * Devolve uma lista com todos os atributos na estrutura da estrutura passada e da fun��o passada
     * @param estruturaEtt
     * @param funcaoFun
     * @return List de EstruturaAtributoEtt
     * @throws ECARException
     */
    public List<EstruturaAtributoEttat> getEstruturaAtributosFuncao( EstruturaEtt estruturaEtt, FuncaoFun funcaoFun )
                      throws ECARException
    {
        List retorno = new ArrayList(  );

        if (( estruturaEtt != null ) && ( estruturaEtt.getEstruturaAtributoEttats() != null ))
        {
            Iterator itEstruturaAtributoEttats = estruturaEtt.getEstruturaAtributoEttats().iterator();

            while (itEstruturaAtributoEttats.hasNext())
            {
                EstruturaAtributoEttat estruturaAtributoEttat = (EstruturaAtributoEttat) itEstruturaAtributoEttats.next();

                if ( funcaoFun != null )
                {
                    if (estruturaAtributoEttat.getAtributosAtb().getFuncaoFun().equals(funcaoFun))
                    {
                    	retorno.add(estruturaAtributoEttat);
                    }
                }                
            }
        }

        return retorno;
    }
}
