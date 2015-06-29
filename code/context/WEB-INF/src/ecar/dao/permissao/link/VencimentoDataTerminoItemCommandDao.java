package ecar.dao.permissao.link;

import javax.servlet.ServletContext;

import comum.database.Dao;

import ecar.dao.EstruturaFuncaoDao;
import ecar.evento.URLEvento;
import ecar.exception.ECARException;
import ecar.exception.PermissaoAcessoLinkException;
import ecar.login.SegurancaECAR;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.ItemEstruturaIett;
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
public class VencimentoDataTerminoItemCommandDao
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
            url = URLEvento.montaURLRedirecionamento( Dominios.CFG_MAIL_DATA_TERMINO_ITEM.toString(  ),
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
        String codIett = "";
        String codAba = "";
        String codEttSelecionado = "";
        String[] valores = parametros.split( "," );

        for ( int i = 0; i < valores.length; i++ )
        {
            if ( valores[i].contains( "codIett" ) )
            {
                posicao = valores[i].indexOf( "=" );

                String nomeParametro = valores[i];
                codIett = valores[i].substring( posicao + 1, ( valores[i].length(  ) ) );
            } else if ( valores[i].contains( "codAba" ) )
            {
                posicao = valores[i].indexOf( "=" );

                String nomeParametro = valores[i];
                codAba = valores[i].substring( posicao + 1, ( valores[i].length(  ) ) );
            } else if ( valores[i].contains( "codEttSelecionado" ) )
            {
                posicao = valores[i].indexOf( "=" );

                String nomeParametro = valores[i];
                codEttSelecionado = valores[i].substring( posicao + 1, ( valores[i].length(  ) ) );
            }
        }

        EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao( null );
        EstruturaEtt estruturaEtt = null;
        ItemEstruturaIett item = null;
        EstruturaFuncaoEttf estruturaFuncao = null;

        try
        {
            //verifica se existe o item passado como parametro
            if ( ( codIett != null ) && ! codIett.equals( "" ) )
            {
                try
                {
                    item =
                        (ItemEstruturaIett) this.buscar( ItemEstruturaIett.class,
                                                         Long.valueOf( codIett ) );
                } catch ( Exception e )
                {
                    //ITEM_INEXISTENTE;
                    throw new PermissaoAcessoLinkException( ErroPermissaoAcessoLinkEnum.ITEM_INEXISTENTE );
                }
            }

            //verifica se existe a aba passada como parametro para a estrutura
            if ( ( codAba != null ) && ! codAba.equals( "" ) )
            {
                if ( ( item != null ) && ( item.getEstruturaEtt(  ) != null ) )
                {
                    estruturaEtt = item.getEstruturaEtt(  );
                    estruturaFuncao =
                        estruturaFuncaoDao.getLabelFuncao( estruturaEtt,
                                                           Long.valueOf( codAba ) );
                }

                if ( estruturaFuncao == null )
                {
                    //ABA_INEXISTENTE;
                    throw new PermissaoAcessoLinkException( ErroPermissaoAcessoLinkEnum.ABA_INEXISTENTE );
                }
            }

            //VERIFICA A PERMISSAO DE ACESSO DO ITEM
            if ( item != null )
            {
                if ( ( ( item.getIndAtivoIett(  ) != null ) && ! "".equals( item.getIndAtivoIett(  ).trim(  ) ) ) &&
                         ! "N".equals( item.getIndAtivoIett(  ).toUpperCase(  ) ) )
                {
                    // verifica se o usuario tem permissao de acessar o item em cadastro
                    ValidaPermissao validaPermissao = new ValidaPermissao(  );
                    validaPermissao.permissoesItem( item,
                                                    seguranca.getUsuario(  ),
                                                    seguranca.getGruposAcesso(  ) );

                    boolean permissaoAcessoItem = validaPermissao.permissaoConsultarItem(  );

                    if ( ! permissaoAcessoItem )
                    {
                        // SEM_PERMISSAO;
                        throw new PermissaoAcessoLinkException( ErroPermissaoAcessoLinkEnum.SEM_PERMISSAO_ITEM );
                    }
                } else
                {
                    //	ITEM_INATIVO;
                    throw new PermissaoAcessoLinkException( ErroPermissaoAcessoLinkEnum.ITEM_INATIVO );
                }
            }
        } catch ( ECARException e )
        {
            this.logger.error( e );
            throw new PermissaoAcessoLinkException( e );
        }
    }
}
