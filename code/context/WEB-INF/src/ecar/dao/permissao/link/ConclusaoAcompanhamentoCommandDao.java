package ecar.dao.permissao.link;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import comum.database.Dao;

import ecar.dao.AcompReferenciaDao;
import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.EstruturaAcessoDao;
import ecar.dao.ItemEstUsutpfuacDao;
import ecar.dao.TipoAcompanhamentoDao;
import ecar.dao.TipoFuncAcompDao;
import ecar.evento.URLEvento;
import ecar.exception.ECARException;
import ecar.exception.PermissaoAcessoLinkException;
import ecar.login.SegurancaECAR;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.UsuarioAtributoUsua;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/*
 * Created on 06/01/2009
 * Por Patricia Pessoa
 *
 */

/**
 *
 * @author 70744416353
 */
public class ConclusaoAcompanhamentoCommandDao
    extends Dao
    implements PermissaoAcessoLinkCommand
{
    /**
     * Verifica a permissao de acesso do usu�rio e monta a URL de redirecionamento
     *
     * @param evento
     * @param parametros
     * @param seguranca
     * @param servletContext
     * @return
     * @throws ECARException
     */
    public String execute( String evento, String parametros, SegurancaECAR seguranca, ServletContext servletContext )
                   throws ECARException
    {
        String url = null;

        try
        {
            permissaoAcesso( parametros, seguranca );
            url = URLEvento.montaURLRedirecionamento( Dominios.CFG_MAIL_CONCLUSAO_ACOMPANHAMENTO.toString(  ),
                                                      parametros,
                                                      servletContext );
        } catch ( ECARException e )
        {
            //guarda a mensagem que o usu�rio deve exibir na tela
            if ( e.getCausaRaiz(  ) != null )
            {
                String mensagemErro = e.getCausaRaiz(  ).getMessage(  );
                throw new ECARException( mensagemErro );
            }
        }

        return url;
    }

    /**
    * Verifica a permissao de acesso do usu�rio da parte de cadastro
    *
     * @param parametros
     * @param seguranca
     * @throws PermissaoAcessoLinkException
    */
    public void permissaoAcesso( String parametros, SegurancaECAR seguranca )
                         throws PermissaoAcessoLinkException
    {
        int posicao = 0;

        //recupera os parametros passados no link
        String codTipoAcompanhamento = "";
        String referencia_hidden = "";
        String periodo = "";
        String codAri = "";
        String[] valores = parametros.split( "," );

        for ( int i = 0; i < valores.length; i++ )
        {
            if ( valores[i].contains( "codTipoAcompanhamento" ) )
            {
                posicao = valores[i].indexOf( "=" );

                String nomeParametro = valores[i];
                codTipoAcompanhamento = valores[i].substring( posicao + 1, ( valores[i].length(  ) ) );
            } else if ( valores[i].contains( "referencia_hidden" ) )
            {
                posicao = valores[i].indexOf( "=" );

                String nomeParametro = valores[i];
                referencia_hidden = valores[i].substring( posicao + 1, ( valores[i].length(  ) ) );
            } else if ( valores[i].contains( "codAri" ) )
            {
                posicao = valores[i].indexOf( "=" );

                String nomeParametro = valores[i];
                codAri = valores[i].substring( posicao + 1, ( valores[i].length(  ) ) );
            }
        }

        //PERMISSAO DE ACESSO DA PARTE DE REGISTRO DE MONITORAMENTO 
        int qtdePeriodosAnteriores = 1;
        Collection periodosConsiderados = new ArrayList(  );
        AcompReferenciaItemAri ari = null;
        ItemEstruturaIett item = null;
        AcompReferenciaAref acompReferencia = null;
        TipoAcompanhamentoTa tipoAcompanhamento = null;
        List tpfaOrdenadosPorEstrutura = null;
        TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao( null );
        EstruturaAcessoDao estruturaAcessoDao = new EstruturaAcessoDao( null );
        AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao( null );
        AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao( null );
        TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao( null );
        ItemEstUsutpfuacDao itemEstUsuDao = new ItemEstUsutpfuacDao( null );
        AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao( null );
        boolean usuarioLogadoEmiteParecer = false;
        boolean permissaoLapis = false;
        boolean permissaoAdministradorAcompanhamento = false;
        Iterator itPeriodosAcao = null;
        Map mapAcao = null;

        UsuarioUsu usuario = seguranca.getUsuario(  );

        try
        {
            try
            {
                if ( ( codTipoAcompanhamento != null ) && ! codTipoAcompanhamento.equals( "" ) )
                {
                    tipoAcompanhamento =
                        (TipoAcompanhamentoTa) taDao.buscar( TipoAcompanhamentoTa.class,
                                                             Long.valueOf( codTipoAcompanhamento ) );
                    permissaoAdministradorAcompanhamento =
                        estruturaAcessoDao.temPermissoesAcessoAcomp( tipoAcompanhamento,
                                                                     seguranca.getGruposAcesso(  ) );
                }
            } catch ( Exception e )
            {
                //NAO EXISTE O TIPO DE ACOMPANHAMENTO;
                throw new PermissaoAcessoLinkException( ErroPermissaoAcessoLinkEnum.TIPO_DE_ACOMPANHAMENTO_INEXISTENTE );
            }

            try
            {
                if ( ( referencia_hidden != null ) && ! referencia_hidden.equals( "" ) )
                {
                    periodosConsiderados =
                        acompReferenciaDao.getPeriodosAnterioresOrdenado( Long.valueOf( referencia_hidden ),
                                                                          qtdePeriodosAnteriores,
                                                                          Long.valueOf( codTipoAcompanhamento ),
                                                                          false );
                }
            } catch ( Exception e )
            {
                //NAO EXISTE A REFERENCIA;
                throw new PermissaoAcessoLinkException( ErroPermissaoAcessoLinkEnum.REFERENCIA_INEXISTENTE );
            }

            try
            {
                // Busca cole��o com o per�odo a ser considereado
                if ( ( codAri != null ) && ! codAri.equals( "" ) )
                {
                    ari = (AcompReferenciaItemAri) ariDao.buscar( AcompReferenciaItemAri.class,
                                                                  Long.valueOf( codAri ) );
                    item = ari.getItemEstruturaIett(  );
                }
            } catch ( Exception e )
            {
                //NAO EXISTE O ACOMPANHAMENTO;
                throw new PermissaoAcessoLinkException( ErroPermissaoAcessoLinkEnum.ACOMPANHAMENTO_INEXISTENTE );
            }

            if ( ( item.getIndAtivoIett(  ) == null ) ||
                     "".equals( item.getIndAtivoIett(  ).trim(  ) ) ||
                     "N".equals( item.getIndAtivoIett(  ).toUpperCase(  ) ) )
            {
                //O ITEM EST� INATIVO;
                throw new PermissaoAcessoLinkException( ErroPermissaoAcessoLinkEnum.ITEM_INATIVO );

                //verifica se o usu�rio pode "Gerar Per�odo de Acompanhamento"
            } else if ( permissaoAdministradorAcompanhamento )
            {
                permissaoLapis = true;
            } else
            {
                itPeriodosAcao = periodosConsiderados.iterator(  );
                mapAcao = acompReferenciaItemDao.criarMapPeriodoAri( periodosConsiderados, item );
                tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas(  );

                if ( ( itPeriodosAcao != null ) && itPeriodosAcao.hasNext(  ) )
                {
                    //Pega s� o per�odo selecionado (Aref), que � o primeiro
                    acompReferencia = (AcompReferenciaAref) itPeriodosAcao.next(  );

                    if ( ! mapAcao.isEmpty(  ) && mapAcao.containsKey( acompReferencia ) )
                    {
                        AcompReferenciaItemAri ariAcao = (AcompReferenciaItemAri) mapAcao.get( acompReferencia );

                        //Pega os Arels do Ari selecionado 
                        List relatorios =
                            acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp( ariAcao,
                                                                                            tpfaOrdenadosPorEstrutura );
                        Iterator itRelatorios = relatorios.iterator(  );

                        if ( itRelatorios != null )
                        {
                            while ( itRelatorios.hasNext(  ) )
                            {
                                AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next(  );

                                ItemEstUsutpfuacIettutfa itemEstUsu =
                                    itemEstUsuDao.buscar( item.getCodIett(  ),
                                                          relatorio.getTipoFuncAcompTpfa(  ).getCodTpfa(  ) );

                                //Verifica se a permiss�o � de grupo ou usu�rio
                                if ( ( itemEstUsu != null ) && ( usuario != null ) )
                                {
                                    if ( itemEstUsu.getUsuarioUsu(  ) != null )
                                    {
                                        usuarioLogadoEmiteParecer = itemEstUsu.getUsuarioUsu(  ).getCodUsu(  )
                                                                              .equals( usuario.getCodUsu(  ) );
                                    } else if ( itemEstUsu.getSisAtributoSatb(  ) != null )
                                    {
                                        if ( itemEstUsu.getSisAtributoSatb(  ).getUsuarioAtributoUsuas(  ) != null )
                                        {
                                            Iterator itUsuarios =
                                                itemEstUsu.getSisAtributoSatb(  ).getUsuarioAtributoUsuas(  ).iterator(  );

                                            if ( itUsuarios != null )
                                            {
                                                while ( itUsuarios.hasNext(  ) )
                                                {
                                                    UsuarioAtributoUsua usuarioAtributoUsua =
                                                        (UsuarioAtributoUsua) itUsuarios.next(  );

                                                    if ( ( usuarioAtributoUsua != null ) &&
                                                             ( usuarioAtributoUsua.getUsuarioUsu(  ) != null ) &&
                                                             usuarioAtributoUsua.getUsuarioUsu(  ).getCodUsu(  )
                                                                                    .equals( usuario.getCodUsu(  ) ) )
                                                    {
                                                        usuarioLogadoEmiteParecer = true;

                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                if ( usuarioLogadoEmiteParecer == true )
                                {
                                    break;
                                }
                            }
                        }
                    }

                    if ( ! ( usuarioLogadoEmiteParecer || permissaoLapis ) )
                    {
                        //N�O TEM PERMISSAO PARA ACESSAR O ACOMPANHAMENTO
                        throw new PermissaoAcessoLinkException( ErroPermissaoAcessoLinkEnum.SEM_PERMISSAO_ACOMPANHAMENTO );
                    }
                }
            }
        } catch ( ECARException e )
        {
            this.logger.error( e );
            throw new PermissaoAcessoLinkException( e );
        }
    }
}
