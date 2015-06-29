/*
 * Created on 02/12/2004
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.AtributoDemandaAtbdem;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.SisTipoExibicGrupoSteg;

/**
 * @author evandro
 * @author felipe
 *
 */
public class SisGrupoAtributoDao
    extends Dao
{
    /**
     * Construtor. Chama o Session factory do Hibernate
     * @param request
     */
    public SisGrupoAtributoDao( HttpServletRequest request )
    {
        super(  );
        this.request = request;
    }

    /**
     * Construtor padr�o.
     * @author garten
     *
     */
    public SisGrupoAtributoDao(  )
    {
        super(  );
    }

    /**
     * Devolve uma lista com os Grupos de Atributo que devem aparecer no
     * cadastro do usu�rio, ou seja, os ativos e os que n�o s�o atributos do
     * sistema, ordenados pela sequ�ncia de apresenta��o.
     *
     * @param indTabelaUso
     * @return
     * @throws ECARException
     */
    public List getGruposAtributosCadastro( String indTabelaUso )
                                    throws ECARException
    {
        SisGrupoAtributoSga sisGrupoAtributo = new SisGrupoAtributoSga(  );
        sisGrupoAtributo.setIndAtivoSga( "S" );
        sisGrupoAtributo.setIndSistemaSga( "N" );

        if ( "U".equals( indTabelaUso ) )
        {
            sisGrupoAtributo.setIndCadUsuSga( "S" );
        } else
        {
            sisGrupoAtributo.setIndTabelaUsoSga( indTabelaUso );
        }

        return this.pesquisar( sisGrupoAtributo,
                               new String[] { "seqApresentacaoSga", "asc" } );
    }

    /**
    * Devolve uma lista com os Grupos de Atributo que devem aparecer no
     * cadastro do usu�rio, ou seja, os ativos e os que n�o s�o atributos do
     * sistema, ordenados pela sequ�ncia de apresenta��o.
     *
     * @return
     * @throws ECARException
     */
    public List getGruposAtributosCadastroUsuarioSite(  )
                                               throws ECARException
    {
        SisGrupoAtributoSga sisGrupoAtributo = new SisGrupoAtributoSga(  );
        sisGrupoAtributo.setIndAtivoSga( "S" );
        sisGrupoAtributo.setIndSistemaSga( "N" );
        sisGrupoAtributo.setIndCadSiteSga( "S" );

        return this.pesquisar( sisGrupoAtributo,
                               new String[] { "seqApresentacaoSga", "asc" } );
    }

    /**
     * Retorna um List com os Atributos de um Grupo de Atributos ordenados de
     * acordo com o par�metro para definir ordena��o dos atributos do grupo
     *
     * @param sisGrupoAtributo
     * @return
     * @throws ECARException
     */
    public List getAtributosOrdenados( SisGrupoAtributoSga sisGrupoAtributo )
                               throws ECARException
    {
        List retorno = new ArrayList(  );

        if ( sisGrupoAtributo.getSisTipoOrdenacaoSto(  ) != null )
        {
            if ( sisGrupoAtributo.getSisTipoOrdenacaoSto(  ).getCodSto(  ).toString(  )
                                     .equals( SisTipoOrdenacaoDao.CODIGO_ASC ) )
            {
                retorno =
                    this.ordenaSet( sisGrupoAtributo.getSisAtributoSatbs(  ),
                                    "this.codSatb",
                                    "asc" );
            }

            if ( sisGrupoAtributo.getSisTipoOrdenacaoSto(  ).getCodSto(  ).toString(  )
                                     .equals( SisTipoOrdenacaoDao.CODIGO_DESC ) )
            {
                retorno =
                    this.ordenaSet( sisGrupoAtributo.getSisAtributoSatbs(  ),
                                    "this.codSatb",
                                    "desc" );
            }

            if ( sisGrupoAtributo.getSisTipoOrdenacaoSto(  ).getCodSto(  ).toString(  )
                                     .equals( SisTipoOrdenacaoDao.DESCRICAO_ASC ) )
            {
                retorno =
                    this.ordenaSet( sisGrupoAtributo.getSisAtributoSatbs(  ),
                                    "this.descricaoSatb",
                                    "asc" );
            }

            if ( sisGrupoAtributo.getSisTipoOrdenacaoSto(  ).getCodSto(  ).toString(  )
                                     .equals( SisTipoOrdenacaoDao.DESCRICAO_DESC ) )
            {
                retorno =
                    this.ordenaSet( sisGrupoAtributo.getSisAtributoSatbs(  ),
                                    "this.descricaoSatb",
                                    "desc" );
            }

            if ( sisGrupoAtributo.getSisTipoOrdenacaoSto(  ).getCodSto(  ).toString(  )
                                     .equals( SisTipoOrdenacaoDao.INF_COMPLEMENTAR_ASC ) )
            {
                retorno = new ArrayList(sisGrupoAtributo.getSisAtributoSatbs());                
//                    this.ordenaSet( sisGrupoAtributo.getSisAtributoSatbs(  ),
//                                    "this.atribInfCompSatb",
//                                    "asc" );
            }

            if ( sisGrupoAtributo.getSisTipoOrdenacaoSto(  ).getCodSto(  ).toString(  )
                                     .equals( SisTipoOrdenacaoDao.INF_COMPLEMENTAR_DESC ) )
            {
                retorno =
                    this.ordenaSet( sisGrupoAtributo.getSisAtributoSatbs(  ),
                                    "this.atribInfCompSatb",
                                    "desc" );
            }

            retorno = getSisAtributoSatbsAtivos( retorno );
        } else
        {
            retorno.addAll( getSisAtributoSatbsAtivos( new ArrayList( sisGrupoAtributo.getSisAtributoSatbs(  ) ) ) );

//            retorno.addAll(sisGrupoAtributo.getSisAtributoSatbs());
        }

        return retorno;
    }

    /**
    *
    * @param sisGrupoAtributo
    * @throws ECARException
    */
   public void alterar(SisGrupoAtributoSga sisGrupoAtributo, Long sisTipoExibicGrupoSteg)
                throws ECARException {
       try {
           boolean alterar = true;
           
           EstruturaDao estruturaDao = new EstruturaDao(this.request);
           if( estruturaDao.verificaSisGrupoAtributoRestringirVisualizacao(sisGrupoAtributo) && 
       		   !sisGrupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString().equals(sisTipoExibicGrupoSteg.toString()) ) {
               alterar = false;
               throw new ECARException("sisGrupoAtributo.validacao.sisTipoExibicGrupoSteg");
           } else {
        	   sisGrupoAtributo.setSisTipoExibicGrupoSteg((SisTipoExibicGrupoSteg)this.buscar(SisTipoExibicGrupoSteg.class, sisTipoExibicGrupoSteg));
           }
           
           if(alterar) {
               super.alterar(sisGrupoAtributo);
           }
       } catch(ECARException e) {
           this.logger.error(e);
           throw e;
       }
   }
           
    /**
     *
     * @param sisGrupoAtributo
     * @throws ECARException
     */
    public void excluir( SisGrupoAtributoSga sisGrupoAtributo )
                 throws ECARException
    {
        try
        {
            boolean excluir = true;
            
            EstruturaDao estruturaDao = new EstruturaDao(this.request);
            if(estruturaDao.verificaSisGrupoAtributoRestringirVisualizacao(sisGrupoAtributo)) {
                excluir = false;
                throw new ECARException("sisGrupoAtributo.exclusao.erro.sisGrpAtbRestringeVisualizacao");
            }
            
            if ( contar( sisGrupoAtributo.getSegmentoSgts(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "sisGrupoAtributo.exclusao.erro.segmentoSgts" );
            }

            if ( contar( sisGrupoAtributo.getSisAtributoSatbs(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "sisGrupoAtributo.exclusao.erro.sisAtributoSatbs" );
            }

            if ( contar( sisGrupoAtributo.getConfiguracaoCfgsByCodSgaGrAtrPgIni(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "sisGrupoAtributo.exclusao.erro.configuracaoCfgsByCodSgaGrAtrPgIni" );
            }

            if ( contar( sisGrupoAtributo.getConfiguracaoCfgsByCodSgaGrAtrNvPlan(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "sisGrupoAtributo.exclusao.erro.configuracaoCfgsByCodSgaGrAtrNvPlan" );
            }

            if ( contar( sisGrupoAtributo.getConfiguracaoCfgsByCodSgaGrAtrClAcesso(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "sisGrupoAtributo.exclusao.erro.configuracaoCfgsByCodSgaGrAtrClAcesso" );
            }

            if ( contar( sisGrupoAtributo.getConfiguracaoCfgsByCodSgaGrAtrLeiCapa(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "sisGrupoAtributo.exclusao.erro.configuracaoCfgsByCodSgaGrAtrLeiCapa" );
            }

            if ( contar( sisGrupoAtributo.getConfiguracaoCfgsByCodSgaGrAtrTpAcesso(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "sisGrupoAtributo.exclusao.erro.configuracaoCfgsByCodSgaGrAtrTpAcesso" );
            }

            // pesquisa nos grupos de atributos na demanda
            if ( sisGrupoAtributo.getIndTabelaUsoSga(  ).equals( "D" ) )
            {
                //valida a exclusao para o caso de demandas
                excluir = validaExclusaoDemanda( sisGrupoAtributo );
            }

            if ( excluir )
            {
                super.excluir( sisGrupoAtributo );
            }
        } catch ( ECARException e )
        {
            this.logger.error( e );
            throw e;
        }
    }

    /**
     * Retornar os Atributos definidos como Classes de Acesso em Configura��es.
     *
     * @return
     * @throws ECARException
     */
    public Set getClassesDeAcesso(  )
                           throws ECARException
    {
        SisGrupoAtributoSga grupoAtributosClasseAcesso =
            new ConfiguracaoDao( request ).getConfiguracao(  ).getSisGrupoAtributoSgaByCodSgaGrAtrClAcesso(  );
        Set lAtributosClasseAcesso = grupoAtributosClasseAcesso.getSisAtributoSatbs(  );

        return lAtributosClasseAcesso;
    }

    /**
    * Devolve uma String com c�digo Javascript para validar Grupos de Atributos
    * da Lista
    *
    * @param atributosObrigatorios
    * @return String com codigo Javascript
    */
    public String getValidacoesAtributos( List atributosObrigatorios )
    {
        return getValidacoesAtributos( atributosObrigatorios, false );
    }

    /**
     * Devolve uma String com c�digo Javascript para validar Grupos de Atributos
     * da Lista
     *
     * @param atributosObrigatorios
     * @param cadastroUsuario
     * @return String com codigo Javascript
     */
    public String getValidacoesAtributos( List atributosObrigatorios, boolean cadastroUsuario )
    {
        StringBuilder script = new StringBuilder( "" );
        Iterator it = atributosObrigatorios.iterator(  );

        while ( it.hasNext(  ) )
        {
            SisGrupoAtributoSga grupoAtributo = (SisGrupoAtributoSga) it.next(  );
            script.append( this.getValidacaoAtributo( grupoAtributo, cadastroUsuario ) );
        }

        return script.toString(  );
    }

    /**
     * Devolve uma String com c�digo Javascript para validar Grupos de Atributos do Nivel Planejamento
     *
     * @param atributosObrigatorios
     * @param cadastroUsuario
     * @param labelNivelPlanejamentoNaEstrutura
     * @return String com codigo Javascript
     */
    public String getValidacaoNivelPlanejamento( List atributosObrigatorios, boolean cadastroUsuario,
                                                 String labelNivelPlanejamentoNaEstrutura )
    {
        StringBuilder script = new StringBuilder( "" );
        Iterator it = atributosObrigatorios.iterator(  );

        while ( it.hasNext(  ) )
        {
            SisGrupoAtributoSga grupoAtributo = (SisGrupoAtributoSga) it.next(  );
            script.append( this.getValidacaoAtributo( grupoAtributo, cadastroUsuario, labelNivelPlanejamentoNaEstrutura ) );
        }

        return script.toString(  );
    }

    /**
     *
     * @param grupoAtributo
     * @return
     */
    public String getValidacaoAtributo( SisGrupoAtributoSga grupoAtributo )
    {
        return getValidacaoAtributo( grupoAtributo, false );
    }

    /**
     *
     * @param grupoAtributo
     * @param cadastroUsuario
     * @param labelAtributoNaEstrutura
     * @return
     */
    public String getValidacaoAtributo( SisGrupoAtributoSga grupoAtributo, boolean cadastroUsuario,
                                        String labelAtributoNaEstrutura )
    {
        StringBuilder script = new StringBuilder( "" );

        String labelAtributo = "";

        if ( ( labelAtributoNaEstrutura != null ) && ! labelAtributoNaEstrutura.equals( "" ) )
        {
            labelAtributo = labelAtributoNaEstrutura;
        } else
        {
            labelAtributo = grupoAtributo.getDescricaoSga(  );
        }

        boolean possuiAtributoAtivo = false;

        for ( int i = 0; i < grupoAtributo.getSisAtributoSatbs(  ).size(  ); i++ )
        {
            if ( ( (SisAtributoSatb) grupoAtributo.getSisAtributoSatbs(  ).toArray(  )[i] ).getIndAtivoSatb(  )
                       .equals( "S" ) )
            {
                possuiAtributoAtivo = true;
            }
        }

        String tipoExibicao = "";

        if ( cadastroUsuario && ( grupoAtributo.getSisTipoExibicGrupoCadUsuSteg(  ) != null ) )
        {
            tipoExibicao = grupoAtributo.getSisTipoExibicGrupoCadUsuSteg(  ).getCodSteg(  ).toString(  );
        } else
        {
            tipoExibicao = grupoAtributo.getSisTipoExibicGrupoSteg(  ).getCodSteg(  ).toString(  );
        }

        if ( possuiAtributoAtivo )
        {
            if ( SisTipoExibicGrupoDao.CHECKBOX.equals( tipoExibicao ) )
            {
                script.append( " numChecks = verificaChecks(form, \"" ).append( "a" ).append( grupoAtributo.getCodSga(  ) )
                      .append( "\");\n" ).append( "encontrouChecked = false;\n" ).append( "if(numChecks > 1){\n" )
                      .append( "for(i = 0; i < form." ).append( "a" ).append( grupoAtributo.getCodSga(  ) )
                      .append( ".length; i++){\n" ).append( "if(form." ).append( "a" )
                      .append( grupoAtributo.getCodSga(  ) ).append( "[i].checked == true){\n" )
                      .append( "encontrouChecked = true;" ).append( "}\n" ).append( "}\n" ).append( "}else{" )
                      .append( "if(form." ).append( "a" ).append( grupoAtributo.getCodSga(  ) )
                      .append( ".checked == true){\n" ).append( "encontrouChecked = true;}\n" ).append( "}\n" )
                      .append( "if(!encontrouChecked){\n" ).append( "alert(\"Obrigat�ria a sele��o do Campo " )
                      .append( labelAtributo ).append( "\");\n" ).append( "return false;\n" ).append( "}\n" );
            }

            if ( SisTipoExibicGrupoDao.COMBOBOX.equals( tipoExibicao ) ||
                     SisTipoExibicGrupoDao.LISTBOX.equals( tipoExibicao ) ||
                     SisTipoExibicGrupoDao.TEXTAREA.equals( tipoExibicao ) ||
                     SisTipoExibicGrupoDao.TEXT.equals( tipoExibicao ) )
            {
                script.append( "if(" );
                script.append( " form.a" ).append( grupoAtributo.getCodSga(  ) ).append( "!= null &&" );
                script.append( " !validaString(form." ).append( "a" ).append( grupoAtributo.getCodSga(  ) )
                      .append( ", \"" ).append( labelAtributo ).append( "\", true)){ \n" );
                script.append( "return false;\n" );
                script.append( "}\n" );
            }

            if ( SisTipoExibicGrupoDao.RADIO_BUTTON.equals( tipoExibicao ) )
            {
                // quando tiver apenas 1 
                if ( grupoAtributo.getSisAtributoSatbs(  ).size(  ) == 1 )
                {
                    script.append( "if(" );
                    script.append( " form.a" ).append( grupoAtributo.getCodSga(  ) ).append( "!= null &&" );
                    script.append( " form.a" ).append( grupoAtributo.getCodSga(  ) ).append( ".checked == false " );
                    script.append( "){\n" ).append( "alert(\"Obrigat�ria a sele��o do Campo " ).append( labelAtributo )
                          .append( "\");\n" ).append( "return false;\n" ).append( "}" );
                } else
                {
                    script.append( "if(" );

                    for ( int i = 0; i < grupoAtributo.getSisAtributoSatbs(  ).size(  ); i++ )
                    {
                        if ( i > 0 )
                        {
                            script.append( "&&" );
                        }

                        script.append( " form.a" ).append( grupoAtributo.getCodSga(  ) ).append( "[" ).append( i )
                              .append( "] != null &&" );
                        script.append( " form.a" ).append( grupoAtributo.getCodSga(  ) ).append( "[" ).append( i )
                              .append( "].checked == false " );
                    }

                    script.append( "){\n" ).append( "alert(\"Obrigat�ria a sele��o do Campo " ).append( labelAtributo )
                          .append( "\");\n" ).append( "return false;\n" ).append( "}" );
                }
            }

            if ( SisTipoExibicGrupoDao.MULTITEXTO.equals( tipoExibicao ) )
            {
                script.append( "if(" );

                for ( int i = 0; i < grupoAtributo.getSisAtributoSatbs(  ).size(  ); i++ )
                {
                    SisAtributoSatb sisAtributoSatb =
                        (SisAtributoSatb) grupoAtributo.getSisAtributoSatbs(  ).toArray(  )[i];

                    if ( i > 0 )
                    {
                        script.append( " || " );
                    }

                    script.append( " Trim(form.a" ).append( grupoAtributo.getCodSga(  ) ).append( "_" )
                          .append( sisAtributoSatb.getCodSatb(  ).toString(  ) ).append( ".value)==\"\"" );
                }

                script.append( "){\n" ).append( "alert(\"Obrigat�rio o preenchimento de todas as linhas do campo " )
                      .append( labelAtributo ).append( "\");\n" ).append( "return false;\n" ).append( "}" );
            }

            if ( SisTipoExibicGrupoDao.IMAGEM.equals( tipoExibicao ) )
            {
                String path = "";
                String imageDefault = "";

                if ( request != null )
                {
                    path = request.getContextPath(  );
                    imageDefault = "/images/ImagemIndisponivel.gif";
                }

                script.append( " if(!validaImage(form." ).append( "a" ).append( grupoAtributo.getCodSga(  ) )
                      .append( ", \"" ).append( labelAtributo ).append( "\", true" ).append( ", \"" )
                      .append( path + imageDefault + "\")){ \n" );
                script.append( "return false;\n" );
                script.append( "}\n" );
            }

            if ( SisTipoExibicGrupoDao.VALIDACAO.equals( tipoExibicao ) )
            {
                script.append( " if(!validaString(form." ).append( "a" ).append( grupoAtributo.getCodSga(  ) )
                      .append( ", \"" ).append( labelAtributo ).append( "\", true)){ \n" );
                script.append( "return false;\n" );
                script.append( "}\n" );
            }
        }

        return script.toString(  );
    }

    /**
     *
     * @param grupoAtributo
     * @param cadastroUsuario
     * @return
     */
    public String getValidacaoAtributo( SisGrupoAtributoSga grupoAtributo, boolean cadastroUsuario )
    {
        return getValidacaoAtributo( grupoAtributo, cadastroUsuario, "" );
    }

    /**
     *
     * @param grupoAtributo
     * @param novoLabelGrupo
     * @return
     */
    public String getValidacaoAtributo( SisGrupoAtributoSga grupoAtributo, String novoLabelGrupo )
    {
        return getValidacaoAtributo( grupoAtributo, novoLabelGrupo, false );
    }

    //M�todo igual ao getValidacaoAtributo(SisGrupoAtributoSga grupoAtributo), s� que leva em considera��o
    //o label passado
    /**
     *
     * @param grupoAtributo
     * @param novoLabelGrupo
     * @param cadastroUsuario
     * @return
     */
    public String getValidacaoAtributo( SisGrupoAtributoSga grupoAtributo, String novoLabelGrupo,
                                        boolean cadastroUsuario )
    {
        StringBuilder script = new StringBuilder( "" );

        boolean possuiAtributoAtivo = false;

        for ( int i = 0; i < grupoAtributo.getSisAtributoSatbs(  ).size(  ); i++ )
        {
            if ( ( (SisAtributoSatb) grupoAtributo.getSisAtributoSatbs(  ).toArray(  )[i] ).getIndAtivoSatb(  )
                       .equals( "S" ) )
            {
                possuiAtributoAtivo = true;
            }
        }

        String tipoExibicao = "";

        if ( cadastroUsuario && ( grupoAtributo.getSisTipoExibicGrupoCadUsuSteg(  ) != null ) )
        {
            tipoExibicao = grupoAtributo.getSisTipoExibicGrupoCadUsuSteg(  ).getCodSteg(  ).toString(  );
        } else
        {
            tipoExibicao = grupoAtributo.getSisTipoExibicGrupoSteg(  ).getCodSteg(  ).toString(  );
        }

        if ( possuiAtributoAtivo )
        {
            if ( SisTipoExibicGrupoDao.CHECKBOX.equals( tipoExibicao ) )
            {
                script.append( " numChecks = verificaChecks(form, \"" ).append( "a" ).append( grupoAtributo.getCodSga(  ) )
                      .append( "\");\n" ).append( "encontrouChecked = false;\n" ).append( "if(numChecks > 1){\n" )
                      .append( "for(i = 0; i < form." ).append( "a" ).append( grupoAtributo.getCodSga(  ) )
                      .append( ".length; i++){\n" ).append( "if(form." ).append( "a" )
                      .append( grupoAtributo.getCodSga(  ) ).append( "[i].checked == true){\n" )
                      .append( "encontrouChecked = true;" ).append( "}\n" ).append( "}\n" ).append( "}else{" )
                      .append( "if(form." ).append( "a" ).append( grupoAtributo.getCodSga(  ) )
                      .append( ".checked == true){\n" ).append( "encontrouChecked = true;}\n" ).append( "}\n" )
                      .append( "if(!encontrouChecked){\n" ).append( "alert(\"Obrigat�ria a sele��o do Campo " )
                      .append( novoLabelGrupo ).append( "\");\n" ).append( "return false;\n" ).append( "}\n" );
            }

            if ( SisTipoExibicGrupoDao.COMBOBOX.equals( tipoExibicao ) ||
                     SisTipoExibicGrupoDao.LISTBOX.equals( tipoExibicao ) ||
                     SisTipoExibicGrupoDao.TEXTAREA.equals( tipoExibicao ) ||
                     SisTipoExibicGrupoDao.TEXT.equals( tipoExibicao ) )
            {
                //valida atributo apenas se seu grupo estiver ativo
                if ( grupoAtributo.getIndAtivoSga(  ).equals( "S" ) )
                {
                    script.append( " if(!validaString(form." ).append( "a" ).append( grupoAtributo.getCodSga(  ) )
                          .append( ", \"" ).append( novoLabelGrupo ).append( "\", true)){ \n" );
                    script.append( "return false;\n" );
                    script.append( "}\n" );
                }
            }

            if ( SisTipoExibicGrupoDao.RADIO_BUTTON.equals( tipoExibicao ) )
            {
                script.append( "if(" );

                if ( grupoAtributo.getSisAtributoSatbs(  ).size(  ) == 1 )
                {
                    script.append( " form.a" ).append( grupoAtributo.getCodSga(  ) ).append( ".checked == false " )
                          .append( "){\n" ).append( "alert(\"Obrigat�ria a sele��o do Campo " ).append( novoLabelGrupo )
                          .append( "\");\n" ).append( "return false;\n" ).append( "}" );
                } else
                {
                    for ( int i = 0; i < grupoAtributo.getSisAtributoSatbs(  ).size(  ); i++ )
                    {
                        if ( i > 0 )
                        {
                            script.append( "&&" );
                        }

                        script.append( " form.a" ).append( grupoAtributo.getCodSga(  ) ).append( "[" ).append( i )
                              .append( "].checked == false " );
                    }

                    script.append( "){\n" ).append( "alert(\"Obrigat�ria a sele��o do Campo " ).append( novoLabelGrupo )
                          .append( "\");\n" ).append( "return false;\n" ).append( "}" );
                }
            }

            if ( SisTipoExibicGrupoDao.MULTITEXTO.equals( tipoExibicao ) )
            {
                script.append( "if(" );

                for ( int i = 0; i < grupoAtributo.getSisAtributoSatbs(  ).size(  ); i++ )
                {
                    SisAtributoSatb sisAtributoSatb =
                        (SisAtributoSatb) grupoAtributo.getSisAtributoSatbs(  ).toArray(  )[i];

                    if ( i > 0 )
                    {
                        script.append( " || " );
                    }

                    script.append( " Trim(form.a" ).append( grupoAtributo.getCodSga(  ) ).append( "_" )
                          .append( sisAtributoSatb.getCodSatb(  ).toString(  ) ).append( ".value)==\"\"" );
                }

                script.append( "){\n" ).append( "alert(\"Obrigat�rio o preenchimento de todas as linhas do campo " )
                      .append( novoLabelGrupo ).append( "\");\n" ).append( "return false;\n" ).append( "}" );
            }

            if ( SisTipoExibicGrupoDao.IMAGEM.equals( tipoExibicao ) )
            {
                String path = "";
                String imageDefault = "";

                if ( request != null )
                {
                    path = request.getContextPath(  );
                    imageDefault = "/images/ImagemIndisponivel.gif";
                }

                script.append( " if(!validaImage(form." ).append( "a" ).append( grupoAtributo.getCodSga(  ) )
                      .append( ", \"" ).append( novoLabelGrupo ).append( "\", true" ).append( ", \"" )
                      .append( path + imageDefault + "\")){ \n" );
                script.append( "return false;\n" );
                script.append( "}\n" );
            }

            if ( SisTipoExibicGrupoDao.VALIDACAO.equals( tipoExibicao ) )
            {
                script.append( " if(!validaString(form." ).append( "a" ).append( grupoAtributo.getCodSga(  ) )
                      .append( ", \"" ).append( novoLabelGrupo ).append( "\", true)){ \n" );
                script.append( "return false;\n" );
                script.append( "}\n" );
            }
        }

        return script.toString(  );
    }

    /**
     * Valida��es de exclus�o se o grupo de atributos for de demanda
     *
     * @param sisGrupoAtributo
     * @return existe Se for true, pode excluir. Se for false, nao pode excluir
     * @throws ECARException
     */
    public boolean validaExclusaoDemanda( SisGrupoAtributoSga sisGrupoAtributo )
                                  throws ECARException
    {
        boolean excluir = false;

        AtributoDemandaAtbdem atributoDemandaAtbdem = new AtributoDemandaAtbdem(  );
        atributoDemandaAtbdem.setSisGrupoAtributoSga( sisGrupoAtributo );

        //pesquisa se existe algum atributo do grupo de atributos que esteja sendo usado em demanda
        if ( this.pesquisar( atributoDemandaAtbdem, null ).size(  ) > 0 )
        {
            throw new ECARException( "sisGrupoAtributo.exclusao.erro.atributoAssociadoDemanda" );
        } else
        {
            excluir = true;
        }

        return excluir;
    }

    /**
     * Retorna os atribuos livres que s�o ativo
     *
     * @param sisAtributoSatbs
     * @return List
     */
    public static List getSisAtributoSatbsAtivos( List<SisAtributoSatb> sisAtributoSatbs )
    {
        List sisAtributoSatbsAtivos = new ArrayList<SisAtributoSatb>(  );
        Iterator<SisAtributoSatb> it = sisAtributoSatbs.iterator(  );
        int i = 0;

        while ( it.hasNext(  ) )
        {
            SisAtributoSatb type = (SisAtributoSatb) it.next(  );

            if ( ( type.getIndAtivoSatb(  ) != null ) && type.getIndAtivoSatb(  ).equals( "S" ) )
            {
                sisAtributoSatbsAtivos.add( type );
            }
        }

        return sisAtributoSatbsAtivos;
    }
    
    /**
     * Retorna um List com os Atributos de um Grupo de Atributos ordenados de
     * acordo com o par�metro para definir ordena��o dos atributos do grupo
     *
     * @param sisGrupoAtributo
     * @return
     * @throws ECARException
     */
    public List ordenadarSisAtributos( SisGrupoAtributoSga sga, List satbs ) throws ECARException
    {
        List retorno = new ArrayList();

        if ( sga.getSisTipoOrdenacaoSto(  ) != null ) {
            if ( sga.getSisTipoOrdenacaoSto(  ).getCodSto(  ).toString(  )
                                     .equals( SisTipoOrdenacaoDao.CODIGO_ASC ) )
            {
                retorno =
                    this.ordenaListInvert( satbs,
                                    "codSatb");
            }

            if ( sga.getSisTipoOrdenacaoSto(  ).getCodSto(  ).toString(  )
                                     .equals( SisTipoOrdenacaoDao.CODIGO_DESC ) )
            {
                retorno =
                    this.ordenaList(satbs,
                                    "codSatb");
            }

            if ( sga.getSisTipoOrdenacaoSto(  ).getCodSto(  ).toString(  )
                                     .equals( SisTipoOrdenacaoDao.DESCRICAO_ASC ) )
            {
                retorno =
                    this.ordenaListInvert( satbs,
                                    "descricaoSatb");
            }

            if ( sga.getSisTipoOrdenacaoSto(  ).getCodSto(  ).toString(  )
                                     .equals( SisTipoOrdenacaoDao.DESCRICAO_DESC ) )
            {
                retorno =
                    this.ordenaList(satbs,
                                    "descricaoSatb");
            }

            if ( sga.getSisTipoOrdenacaoSto(  ).getCodSto(  ).toString(  )
                                     .equals( SisTipoOrdenacaoDao.INF_COMPLEMENTAR_ASC ) )
            {
                retorno =
                    this.ordenaListInvert( satbs,
                                    "atribInfCompSatb");
            }

            if ( sga.getSisTipoOrdenacaoSto(  ).getCodSto(  ).toString(  )
                                     .equals( SisTipoOrdenacaoDao.INF_COMPLEMENTAR_DESC ) )
            {
                retorno =
                    this.ordenaList(satbs,
                                    "atribInfCompSatb");
            }
        } else {
        	retorno = satbs;
        }

        return retorno;
    }
}
