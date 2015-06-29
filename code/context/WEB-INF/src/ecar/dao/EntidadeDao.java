/*
 * Created on 18/09/2004
 *
 */
package ecar.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.FileUpload;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.EnderecoEnd;
import ecar.pojo.EntidadeAtributoEnta;
import ecar.pojo.EntidadeEnt;
import ecar.pojo.LocalItemLit;
import ecar.pojo.PaiFilho;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.TelefoneTel;
import ecar.pojo.TipoEnderecoTend;
import ecar.pojo.Uf;
import ecar.pojo.UsuarioUsu;
import ecar.taglib.util.Input;

/**
 * @author evandro
 *
 */
public class EntidadeDao
    extends Dao
{
    /**
     * Construtor. Chama o Session factory do Hibernate
     *
     * @param request
     */
    public EntidadeDao( HttpServletRequest request )
    {
        super(  );
        this.request = request;
    }

    /**
     *
     * @param request
     * @param entidade
     * @param paramStr
     * @throws ECARException
     */
    public void setEntidade( HttpServletRequest request, EntidadeEnt entidade, boolean paramStr )
                     throws ECARException
    {
        if ( paramStr )
        {
            entidade.setCpfCnpjEnt( Pagina.getParamStr( request, "cpfCnpjEnt" ) );
            entidade.setEmailEnt( Pagina.getParamStr( request, "emailEnt" ) );
            entidade.setIndAtivoEnt( Pagina.getParamStr( request, "indAtivoEnt" ) );
            entidade.setIndOrgaoEnt( Pagina.getParamStr( request, "indOrgaoEnt" ) );
            entidade.setNomeEnt( Pagina.getParamStr( request, "nomeEnt" ) );
            entidade.setSiglaEnt( Pagina.getParamStr( request, "siglaEnt" ) );
        } else
        {
            entidade.setCpfCnpjEnt( Pagina.getParam( request, "cpfCnpjEnt" ) );
            entidade.setEmailEnt( Pagina.getParam( request, "emailEnt" ) );
            entidade.setIndAtivoEnt( Pagina.getParam( request, "indAtivoEnt" ) );
            entidade.setIndOrgaoEnt( Pagina.getParam( request, "indOrgaoEnt" ) );
            entidade.setNomeEnt( Pagina.getParam( request, "nomeEnt" ) );
            entidade.setSiglaEnt( Pagina.getParam( request, "siglaEnt" ) );
        }

        setTelefonesEntidade( request, entidade );
        setEnderecosEntidade( request, entidade );
        setLocaisEntidade( request, entidade );
        setAtributosEntidade( request, entidade );
    }

    /**
     *
     * @author n/c
     * @param request
     * @param entidade
     * @throws ECARException
     */
    public void setAtributosEntidade( HttpServletRequest request, EntidadeEnt entidade )
                              throws ECARException
    {
        entidade.setEntidadeAtributoEntas( null );

        List lAtributos;
        lAtributos = new SisGrupoAtributoDao( request ).getGruposAtributosCadastro( "E" );

        Iterator it = lAtributos.iterator(  );

        while ( it.hasNext(  ) )
        {
            SisGrupoAtributoSga grupoAtributo = (SisGrupoAtributoSga) it.next(  );

            if ( ! "".equals( Pagina.getParamStr( request, "a" + grupoAtributo.getCodSga(  ).toString(  ) ) ) )
            {
                EntidadeAtributoEnta entidadeAtributo = new EntidadeAtributoEnta(  );
                entidadeAtributo.setEntidadeEnt( entidade );

                /*
                 * Caso o tipo de campo seja texto considera-se que o Grupo de
                 * Atributos possuir� apenas 1 atributo que o representa.
                 */
                if ( SisTipoExibicGrupoDao.TEXT.equals( grupoAtributo.getSisTipoExibicGrupoSteg(  ).getCodSteg(  )
                                                                         .toString(  ) ) ||
                         SisTipoExibicGrupoDao.VALIDACAO.equals( grupoAtributo.getSisTipoExibicGrupoSteg(  ).getCodSteg(  )
                                                                                  .toString(  ) ) ||
                         SisTipoExibicGrupoDao.TEXTAREA.equals( grupoAtributo.getSisTipoExibicGrupoSteg(  ).getCodSteg(  )
                                                                                 .toString(  ) ) ||
                         SisTipoExibicGrupoDao.IMAGEM.equals( grupoAtributo.getSisTipoExibicGrupoSteg(  ).getCodSteg(  )
                                                                               .toString(  ) ) )
                {
                    if ( ( grupoAtributo.getSisAtributoSatbs(  ) != null ) &&
                             ( grupoAtributo.getSisAtributoSatbs(  ).size(  ) > 0 ) )
                    {
                        entidadeAtributo.setInformacao( Pagina.getParamStr( request,
                                                                            "a" +
                                                                            grupoAtributo.getCodSga(  ).toString(  ) ) );
                        entidadeAtributo.setSisAtributoSatb( (SisAtributoSatb) grupoAtributo.getSisAtributoSatbs(  )
                                                                                            .iterator(  ).next(  ) );
                        entidadeAtributo.setDataInclusao( Data.getDataAtual(  ) );

                        if ( entidade.getEntidadeAtributoEntas(  ) == null )
                        {
                            entidade.setEntidadeAtributoEntas( new HashSet(  ) );
                        }
                        
                        String pathRaiz = request.getContextPath();
                        
                        // tratamento imagem
    					String caminhoAuxiliarImagem = Pagina.getParamStr(request, "hidImagem" + "a"
    							+ grupoAtributo.getCodSga().toString());
    					if (caminhoAuxiliarImagem!=null && caminhoAuxiliarImagem.length()>0) {
    						
    						String chave = entidadeAtributo.getInformacao();
    						chave = chave.substring(chave.indexOf("RemoteFile=")+ "RemoteFile=".length());
    						UsuarioUsu usuario = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
                            if(usuario.getMapArquivosAtuaisUsuarios() != null && usuario.getMapArquivosAtuaisUsuarios().containsKey(chave)){
//                            	entidadeAtributo.setInformacao(usuario.getMapArquivosAtuaisUsuarios().get(chave));
                            	
                            	caminhoAuxiliarImagem = usuario.getMapArquivosAtuaisUsuarios().get(chave);
                            	caminhoAuxiliarImagem = pathRaiz +"/DownloadFile?RemoteFile=" + caminhoAuxiliarImagem;
                            } 
//                            else{
    						
	    						// salvar a imagem fisicamente que tem o caminho real no campo "a" + codigo de grupo de atributo
	    						try {									
	    							String nomeArquivoNovo = FileUpload.salvarArquivoSessaoFisicamente(request, "a" + grupoAtributo.getCodSga().toString(), caminhoAuxiliarImagem);
	    							if(nomeArquivoNovo != null && !nomeArquivoNovo.equals(""))
										entidadeAtributo.setInformacao(nomeArquivoNovo);
	    						} catch (FileNotFoundException e) {
	    							throw new ECARException("erro.arquivoUrl",e, new String[]{caminhoAuxiliarImagem});
	    						} catch (Exception e) {
	    							throw new ECARException("erro.upload",e, new String[]{caminhoAuxiliarImagem});
	    						}
//                            }
    					}

                        entidade.getEntidadeAtributoEntas(  ).add( entidadeAtributo );
                    }
                } else
                {
                    String[] atributos = request.getParameterValues( "a" + grupoAtributo.getCodSga(  ).toString(  ) );

                    for ( int i = 0; i < atributos.length; i++ )
                    {
                        /*
                         * Tenho que criar novamente o usu�rio atributo sen�o
                         * ele n�o � adicionado no set no final deste la�o
                         */
                        entidadeAtributo = new EntidadeAtributoEnta(  );
                        entidadeAtributo.setEntidadeEnt( entidade );
                        entidadeAtributo.setSisAtributoSatb( (SisAtributoSatb) super.buscar( 
                                                                                             SisAtributoSatb.class,
                                                                                             Long.valueOf( atributos[i] ) ) );
                        entidadeAtributo.setDataInclusao( Data.getDataAtual(  ) );

                        if ( entidade.getEntidadeAtributoEntas(  ) == null )
                        {
                            entidade.setEntidadeAtributoEntas( new HashSet(  ) );
                        }

                        entidade.getEntidadeAtributoEntas(  ).add( entidadeAtributo );
                    }
                }
            }
            /* Foi necess�rio alterar o nome dos campos dos elementos multitexto, adicionando "-codSatb"
             * Assim, ficamos com o nome do campo no seguinte padr�o: "a + codSteg + - + codSatb" (ex.: a12-38)
             * Isto foi feito visto a diferen�a existente entre um grupo com suporte a 1 campo texto
             * e este, que suporta v�rios campos texto.
             */
            else
            {
                if ( SisTipoExibicGrupoDao.MULTITEXTO.equals( grupoAtributo.getSisTipoExibicGrupoSteg(  ).getCodSteg(  )
                                                                               .toString(  ) ) )
                {
                    Enumeration lAtrib = request.getParameterNames(  );

                    while ( lAtrib.hasMoreElements(  ) )
                    {
                        String atrib = (String) lAtrib.nextElement(  );

                        if ( atrib.lastIndexOf( '-' ) > 0 )
                        {
                            String nomeAtrib = atrib.substring( 0,
                                                                atrib.lastIndexOf( '-' ) );
                            String nomeCampo = atrib.substring( atrib.lastIndexOf( '-' ) + 1 );

                            if ( nomeAtrib.equals( "a" + grupoAtributo.getCodSga(  ).toString(  ) ) &&
                                     ! "".equals( Pagina.getParamStr( request, atrib ) ) )
                            {
                                EntidadeAtributoEnta entidadeAtributo = new EntidadeAtributoEnta(  );
                                entidadeAtributo.setEntidadeEnt( entidade );
                                entidadeAtributo.setInformacao( Pagina.getParamStr( request, atrib ) );
                                entidadeAtributo.setSisAtributoSatb( (SisAtributoSatb) super.buscar( 
                                                                                                     SisAtributoSatb.class,
                                                                                                     Long.valueOf( nomeCampo ) ) );
                                entidadeAtributo.setDataInclusao( Data.getDataAtual(  ) );

                                if ( entidade.getEntidadeAtributoEntas(  ) == null )
                                {
                                    entidade.setEntidadeAtributoEntas( new HashSet(  ) );
                                }

                                entidade.getEntidadeAtributoEntas(  ).add( entidadeAtributo );
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @author n/c
     * @param request
     * @param entidade
     * @throws ECARException
     */
    public void setLocaisEntidade( HttpServletRequest request, EntidadeEnt entidade )
                           throws ECARException
    {
        int numLocais = Integer.parseInt( request.getParameter( "contLocal" ) );

        for ( int i = 1; i <= numLocais; i++ )
        {
            if ( "S".equals( Pagina.getParamStr( request, "adicionaLocal" + i ) ) )
            {
                LocalItemLit local = new LocalItemLit(  );
                local =
                    (LocalItemLit) super.buscar( LocalItemLit.class,
                                                 Long.valueOf( Pagina.getParamStr( request, "codLit" + i ) ) );

                if ( entidade.getLocalEntidadeLents(  ) == null )
                {
                    entidade.setLocalEntidadeLents( new HashSet(  ) );
                }

                ( entidade.getLocalEntidadeLents(  ) ).add( local );
            }
        }
    }

    /**
     *
     * @author n/c
     * @param request
     * @param entidade
     * @throws ECARException
     */
    public void setTelefonesEntidade( HttpServletRequest request, EntidadeEnt entidade )
                              throws ECARException
    {
        int numTelefones = Integer.parseInt( request.getParameter( "contTelefones" ) );

        for ( int i = 1; i <= numTelefones; i++ )
        {
            if ( "S".equals( Pagina.getParamStr( request, "adicionaTelefone" + i ) ) )
            {
                TelefoneTel telefone = new TelefoneTel(  );
                telefone.setEntidadeEnt( entidade );
                telefone.setDddTel( Pagina.getParamStr( request, "dddTel" + i ) );
                telefone.setTelefoneTel( Pagina.getParamStr( request, "telefoneTel" + i ) );
                telefone.setIdTel( Pagina.getParamStr( request, "idTel" + i ) );

                if ( entidade.getTelefoneTels(  ) == null )
                {
                    entidade.setTelefoneTels( new HashSet(  ) );
                }

                ( entidade.getTelefoneTels(  ) ).add( telefone );
            }
        }
    }

    /**
     * @author n/c
     * @param request
     * @param entidade
     * @throws ECARException
     */
    public void setEnderecosEntidade( HttpServletRequest request, EntidadeEnt entidade )
                              throws ECARException
    {
        int numEnderecos = Integer.parseInt( request.getParameter( "contEnderecos" ) );
        EnderecoEnd endereco;

        for ( int i = 1; i <= numEnderecos; i++ )
        {
            if ( "S".equals( Pagina.getParamStr( request, "adicionaEndereco" + i ) ) )
            {
                endereco = new EnderecoEnd(  );
                endereco.setEntidadeEnt( entidade );
                endereco.setBairroEnd( Pagina.getParamStr( request, "bairroEnd" + i ) );
                endereco.setCepEnd( Integer.valueOf( Pagina.getParamInt( request, "cepEnd" + i ) ) );
                endereco.setCidadeEnd( Pagina.getParamStr( request, "cidadeEnd" + i ) );
                endereco.setComplementoEnd( Pagina.getParamStr( request, "complementoEnd" + i ) );
                endereco.setEnderecoEnd( Pagina.getParamStr( request, "enderecoEnd" + i ) );
                endereco.setIdEnd( Pagina.getParamStr( request, "idEnd" + i ) );

                if ( ! "".equals( Pagina.getParamStr( request, "tipoEnderecoTpend" + i ) ) )
                {
                    endereco.setTipoEnderecoTend( (TipoEnderecoTend) super.buscar( 
                                                                                   TipoEnderecoTend.class,
                                                                                   Long.valueOf( Pagina.getParamStr( request,
                                                                                                                     "tipoEnderecoTpend" +
                                                                                                                     i ) ) ) );
                }

                if ( ! "".equals( Pagina.getParamStr( request, "uf" + i ) ) )
                {
                    endereco.setUf( (Uf) super.buscar( 
                                                       Uf.class,
                                                       Pagina.getParamStr( request, "uf" + i ) ) );
                }

                if ( entidade.getEnderecoEnds(  ) == null )
                {
                    entidade.setEnderecoEnds( new HashSet(  ) );
                }

                entidade.getEnderecoEnds(  ).add( endereco );
            }
        }
    }

    /**
     * @author n/c
     * @param entidade
     * @throws ECARException
     */
    public void excluir( EntidadeEnt entidade )
                 throws ECARException
    {
        try
        {
            boolean excluir = true;

            if ( contar( entidade.getHierarquiaEntidadeHentsByCodEnt(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "entidade.exclusao.erro.hierarquiaEntidadeHentsByCodEnt" );
            }

            if ( contar( entidade.getHierarquiaEntidadeHentsByCodEntPai(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "entidade.exclusao.erro.hierarquiaEntidadeHentsByCodEntPai" );
            }

            if ( contar( entidade.getRegDemandaRegds(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "entidade.exclusao.erro.regDemandaRegds" );
            }
            
            if ( contar( entidade.getItemEstrutEntidadeIettes() ) > 0 )
            {
                excluir = false;
                throw new ECARException( "entidade.exclusao.erro.itemEstrutEntidade" );
            }

            if ( excluir )
            {
                List objs = new ArrayList(  );

                if ( entidade.getEntidadeAtributoEntas(  ) != null )
                {
                    Iterator itAtb = entidade.getEntidadeAtributoEntas(  ).iterator(  );

                    while ( itAtb.hasNext(  ) )
                    {
                        EntidadeAtributoEnta entAtrib = (EntidadeAtributoEnta) itAtb.next(  );
                        objs.add( entAtrib );
                    }
                }

                entidade.setEntidadeAtributoEntas( null );
                objs.add( entidade );
                super.excluir( objs );
            }
        } catch ( ECARException e )
        {
            this.logger.error( e );
            throw e;
        }
    }

    /**
     * Retorna um list com identifica��es de todos as entidades filhos de uma
     * entidade
     *
     * @param entidade
     * @return List de Long
     */
    public List getIdFilhos( EntidadeEnt entidade )
    {
        List filhos = new ArrayList(  );

        if ( entidade.getHierarquiaEntidadeHentsByCodEntPai(  ) != null )
        {
            Iterator it = entidade.getHierarquiaEntidadeHentsByCodEntPai(  ).iterator(  );

            while ( it.hasNext(  ) )
            {
                EntidadeEnt filhoEnt = (EntidadeEnt) it.next(  );
                filhos.add( filhoEnt.getCodEnt(  ) );
            }
        }

        return filhos;
    }

    /**
     * Retorna um list com identifica��es de todos as entidades pais de uma
     * entidade
     *
     * @param entidade
     * @return List de Long
     */
    public List getIdPais( EntidadeEnt entidade )
    {
        List pais = new ArrayList(  );

        if ( entidade.getHierarquiaEntidadeHentsByCodEnt(  ) != null )
        {
            Iterator it = entidade.getHierarquiaEntidadeHentsByCodEnt(  ).iterator(  );

            while ( it.hasNext(  ) )
            {
                EntidadeEnt paiEnt = (EntidadeEnt) it.next(  );
                pais.add( paiEnt.getCodEnt(  ) );
            }
        }

        return pais;
    }

    /**
     * @author n/c
     * @param entidade
     * @param request
     * @throws Exception 
     */
    public void alterar( EntidadeEnt entidade, HttpServletRequest request )
                 throws Exception
    {
        Transaction tx = null;

        try
        {
            List objetos = new ArrayList(  );
            super.inicializarLogBean(  );
            tx = session.beginTransaction(  );

            if ( entidade.getTelefoneTels(  ) != null )
            {
                Iterator itTel = entidade.getTelefoneTels(  ).iterator(  );

                while ( itTel.hasNext(  ) )
                {
                    TelefoneTel telefone = (TelefoneTel) itTel.next(  );
                    session.delete( telefone );
                    objetos.add( telefone );
                }
            }

            entidade.setTelefoneTels( null );

            if ( entidade.getEnderecoEnds(  ) != null )
            {
                Iterator itEnd = entidade.getEnderecoEnds(  ).iterator(  );

                while ( itEnd.hasNext(  ) )
                {
                    EnderecoEnd endereco = (EnderecoEnd) itEnd.next(  );
                    session.delete( endereco );
                    objetos.add( endereco );
                }
            }

            entidade.setEnderecoEnds( null );

            if ( entidade.getEntidadeAtributoEntas(  ) != null )
            {
                Iterator itAtt = entidade.getEntidadeAtributoEntas(  ).iterator(  );

                while ( itAtt.hasNext(  ) )
                {
                    EntidadeAtributoEnta entidadeAtributo = (EntidadeAtributoEnta) itAtt.next(  );
                    session.delete( entidadeAtributo );
                    if(entidadeAtributo.getSisAtributoSatb().getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg() == Input.IMAGEM){
                    	
                    	String nomeCampo = request.getParameter("a" + entidadeAtributo.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga().toString());
                    	
                    	if (nomeCampo != null && nomeCampo.equals("")){
                    		
	    		    		String fullFile = entidadeAtributo.getInformacao();
	    		    		
	    		    		if (fullFile.lastIndexOf("=") != -1)     
	    		    			fullFile = fullFile.substring(fullFile.lastIndexOf("=") + 1);
	    		    		
	    		    		File f = new File(fullFile);
	    		    		if( f.exists() ) 
	    		    			FileUpload.apagarArquivo(fullFile);
                    	}
                    }
                    objetos.add( entidadeAtributo );
                }
            }

            entidade.setEntidadeAtributoEntas( null );

            entidade.setLocalEntidadeLents( null );

            this.setEntidade( request, entidade, true );

            List filhos = new ArrayList(  );

            if ( entidade.getEntidadeAtributoEntas(  ) != null )
            {
                filhos.addAll( entidade.getEntidadeAtributoEntas(  ) );
            }

            session.update( entidade );
            objetos.add( entidade );

            Iterator it = filhos.iterator(  );

            while ( it.hasNext(  ) )
            {
                PaiFilho object = (PaiFilho) it.next(  );
                object.atribuirPKPai(  );
                //salva os filhos
                session.save( object );
                objetos.add( object );
            }

            tx.commit(  );

            if ( super.logBean != null )
            {
                super.logBean.setCodigoTransacao( Data.getHoraAtual( false ) );
                super.logBean.setOperacao( "INC_ALT_EXC" );

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
        } catch ( ECARException e )
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
            throw e;
        }
    }

    /**
     *
     * @author n/c
     * @param entidade
     * @param grupo
     * @return List
     * @throws ECARException
     */
    public List getEntidadeAtributoByGrupo( EntidadeEnt entidade, SisGrupoAtributoSga grupo )
                                    throws ECARException
    {
        List retorno = new ArrayList(  );
        Set result = entidade.getEntidadeAtributoEntas(  );

        if ( result != null )
        {
            if ( result.size(  ) > 0 )
            {
                Iterator it = result.iterator(  );

                while ( it.hasNext(  ) )
                {
                    EntidadeAtributoEnta entidadeAtributo = (EntidadeAtributoEnta) it.next(  );

                    if ( entidadeAtributo.getSisAtributoSatb(  ).getSisGrupoAtributoSga(  ).equals( grupo ) )
                    {
                        retorno.add( entidadeAtributo );
                    }
                }
            }
        }

        return retorno;
    }

    /**
     *
         * @author n/c
     * @param entidade
     * @throws ECARException
     */
    public void salvar( EntidadeEnt entidade )
                throws ECARException
    {
        entidade.setDataInclusaoEnt( Data.getDataAtual(  ) );

        List filhos = new ArrayList(  );

        if ( entidade.getEntidadeAtributoEntas(  ) != null )
        {
            filhos.addAll( entidade.getEntidadeAtributoEntas(  ) );
        }

        super.salvar( entidade, filhos );
    }

    /**
     * Coloca em uma Lista todos os Atributos da Entidade
         * @author n/c
     * @param entidade
     * @return
     * @throws ECARException
     */
    public List pesquisar( EntidadeEnt entidade )
                   throws ECARException
    {
        //Coloca em uma Lista todos os Atributos da Entidade
        List listFiltro = new ArrayList(  );

        if ( ( entidade.getEntidadeAtributoEntas(  ) != null ) && ( 
                                                                          entidade.getEntidadeAtributoEntas(  ).size(  ) > 0
                                                                       ) )
        {
            Iterator itAtr = entidade.getEntidadeAtributoEntas(  ).iterator(  );
            EntidadeAtributoEnta entAtr;

            while ( itAtr.hasNext(  ) )
            {
                entAtr = (EntidadeAtributoEnta) itAtr.next(  );
                listFiltro.add( entAtr );
            }
        }

        List pesquisa = super.pesquisar( entidade,
                                         new String[] { "nomeEnt", "asc" } );
        Iterator it = pesquisa.iterator(  );

        while ( it.hasNext(  ) )
        {
            EntidadeEnt ent = (EntidadeEnt) it.next(  );
            boolean ignorar = false;
            List listVerificacoes = new ArrayList(  );

            if ( listFiltro.size(  ) > 0 )
            {
                if ( ( ent.getEntidadeAtributoEntas(  ) != null ) && ( 
                                                                             ent.getEntidadeAtributoEntas(  ).size(  ) > 0
                                                                          ) )
                {
                    Iterator itAtr = ent.getEntidadeAtributoEntas(  ).iterator(  );
                    EntidadeAtributoEnta entAtr;

                    while ( itAtr.hasNext(  ) )
                    {
                        entAtr = (EntidadeAtributoEnta) itAtr.next(  );
                        listVerificacoes.add( entAtr );
                    }

                    Iterator itClassesTela = listFiltro.iterator(  );
                    List listIgnorar = new ArrayList(  );
                    String ignorarItem = "";
                    EntidadeAtributoEnta entidadesTela;

                    while ( itClassesTela.hasNext(  ) )
                    {
                        entidadesTela = (EntidadeAtributoEnta) itClassesTela.next(  );

                        Iterator itVerificacoes = listVerificacoes.iterator(  );
                        ignorarItem = "SIM";

                        EntidadeAtributoEnta atributosEntidade;

                        while ( itVerificacoes.hasNext(  ) )
                        {
                            atributosEntidade = (EntidadeAtributoEnta) itVerificacoes.next(  );

                            SisGrupoAtributoSga tipo =
                                (SisGrupoAtributoSga) entidadesTela.getSisAtributoSatb(  ).getSisGrupoAtributoSga(  );

                            if ( SisTipoExibicGrupoDao.TEXT.equals( tipo.getSisTipoExibicGrupoSteg(  ).getCodSteg(  )
                                                                            .toString(  ) ) )
                            {
                                if ( ( atributosEntidade.getInformacao(  ) != null ) &&
                                         ( atributosEntidade.getInformacao(  ).length(  ) > 0 ) )
                                {
                                    if ( atributosEntidade.getInformacao(  ).indexOf( atributosEntidade.getInformacao(  ) ) > -1 )
                                    {
                                        ignorarItem = "NAO";

                                        break;
                                    }
                                }
                            } else
                            {
                                if ( atributosEntidade.getSisAtributoSatb(  ).getCodSatb(  ).longValue(  ) == entidadesTela.getSisAtributoSatb(  )
                                                                                                                               .getCodSatb(  )
                                                                                                                               .longValue(  ) )
                                {
                                    ignorarItem = "NAO";

                                    break;
                                }
                            }
                        }

                        listIgnorar.add( ignorarItem );
                    }

                    if ( listIgnorar.contains( "SIM" ) )
                    {
                        ignorar = true;
                    } else
                    {
                        ignorar = false;
                    }
                } else
                {
                    ignorar = true;
                }
            }

            if ( ignorar )
            {
                it.remove(  );
            }
        }

/*       Collections.sort(pesquisa,
            new Comparator() {
                        public int compare(Object o1, Object o2) {
                                       return ( (EntidadeEnt)o1 ).getDes().compareToIgnoreCase( ( (UsuarioUsu)o2 ).getNomeUsuSent() );
                        }
                    } );
     */
        return pesquisa;
    }
}
