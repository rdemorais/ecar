package ecar.dao.permissao.link;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.util.Dominios;

/**
 *
 * @author 70744416353
 */
public class GerenciadorPermissaoAcessoLink
{
    Map<Integer, PermissaoAcessoLinkCommand> permissaoAcessoLinkCommands =
        new HashMap<Integer, PermissaoAcessoLinkCommand>(  );
    private static GerenciadorPermissaoAcessoLink gerenciador;

    /**
     * Inicializa o mapeamento do codigo de Dominio  com o camando
     */
    private GerenciadorPermissaoAcessoLink(  )
    {
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_MANUTENCAO_FUNCOES_ACOMPANHAMENTO,
                                         new ManutencaoFuncoesAcompanhamentoCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_RECUPERACAO_PARECER,
                                         new RecuperacaoAvaliacaoParecerCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_ALTERAR_DATA_LIMITE_REGISTRO_FISICO,
                                         new AlterarDatasLimitesRegistroFisicoCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_ALTERAR_DATA_LIMITE_PARECER,
                                         new AlterarDatasLimitesParecerCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_CRIACAO_REGISTRO_ACOMPANHAMENTO,
                                         new CriacaoRegistroAcompanhamentoCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_VENCIMENTO_LIMITE_FISICO,
                                         new VencimentoLimiteFisicoCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_VENCIMENTO_LIMITE_PARECER,
                                         new VencimentoLimiteParecerCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_VENCIMENTO_LIMITE_PONTO_CRITICO,
                                         new VencimentoLimitePontoCriticoCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_DATA_TERMINO_ITEM,
                                         new VencimentoDataTerminoItemCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_DATA_INICIO_ITEM,
                                         new VencimentoDataInicioItemCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_DATA_RESERVA_1,
                                         new VencimentoDataReserva1CommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_DATA_RESERVA_2,
                                         new VencimentoDataReserva2CommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_DATA_RESERVA_3,
                                         new VencimentoDataReserva3CommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_DATA_RESERVA_4,
                                         new VencimentoDataReserva4CommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_DATA_RESERVA_5,
                                         new VencimentoDataReserva5CommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_MANUTENCAO_EVENTO,
                                         new ManutencaoEventoCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_MANUTENCAO_PONTO_CRITICO,
                                         new ManutencaoPontoCriticoCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_MANUTENCAO_ANEXO,
                                         new ManutencaoAnexoCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_MANUTENCAO_APONTAMENTO,
                                         new ManutencaoApontamentoPontoCriticoCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_LIBERACAO_PARECER,
                                         new LiberarParecerCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_CONCLUSAO_ACOMPANHAMENTO,
                                         new ConclusaoAcompanhamentoCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_RECUPERACAO_ACOMPANHAMENTO,
                                         new RecuperarAcompanhamentoCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_ATIVAR_MONITORAMENTO,
                                         new AtivarMonitoramentoCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_BLOQUEAR_PLANEJAMENTO,
                                         new BloquearPlanejamentoCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_DESBLOQUEAR_PLANEJAMENTO,
                                         new DesbloquearPlanejamentoCommandDao(  ) );
        permissaoAcessoLinkCommands.put( Dominios.CFG_MAIL_DESATIVAR_MONITORAMENTO,
                                         new DesativarMonitoramentoCommandDao(  ) );

        //permissaoAcessoLinkCommands.put(Dominios.CFG_MAIL_STATUS_INDICADOR_RESULTADO, new StatusIndicadorResultadoCommandDao()); 	
    }

    /**
     *
     * @return
     */
    public static GerenciadorPermissaoAcessoLink getInstancia(  )
    {
        if ( gerenciador == null )
        {
            return new GerenciadorPermissaoAcessoLink(  );
        } else
        {
            return gerenciador;
        }
    }

    /**
     * M�todo que cria o link para o redirecionamento da pagina dependendo do evento
     * @param evento
     * @param parametros
     * @param seguranca
     * @param servletContext
     * @return pagRedirecionada
     * @throws ECARException
     */
    public String criarLinkRedirecionamento( String evento, String parametros, SegurancaECAR seguranca,
                                             ServletContext servletContext )
                                     throws ECARException
    {
        String pagRedirecionada = "";
        PermissaoAcessoLinkCommand permissao = null;

        if ( ( evento != null ) && ! evento.equals( "" ) )
        {
            permissao = (PermissaoAcessoLinkCommand) permissaoAcessoLinkCommands.get( Integer.valueOf( evento ) );
        }

        if ( permissao != null )
        {
            pagRedirecionada = permissao.execute( evento, parametros, seguranca, servletContext );
        }

        return pagRedirecionada;
    }
}
