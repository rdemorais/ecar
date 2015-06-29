package ecar.util;

/**
 * Classe que contém os domínios utilizados ao longo do projeto e-CAR.<br>
 * 
 * @author rogerio
 * @since 02/03/2007
 * @version N/C
 */
public class Dominios {

	/* -- Genérico -- */
    /**
     *
     */
    public static String SIM = "S";
        /**
         *
         */
        public static String NAO = "N";
	
	/* -- CorDao :: Caminho relativo para imagens personalizadas pelo usuário -- */
        /**
         *
         */
        public static String PATH_REMOTE_IMAGES = "/CorTipoFunc/Acomp/";
    
    /* -- CorDao :: Caminho relativo aos links das mensagens de email -- */
        /**
         *
         */
        public static String PATH_LOGIN = "/login.jsp?";
    /**
     *
     */
    public static String LOGIN_EVENTO = "evento=";
    /**
     *
     */
    public static String LOGIN_PARAM = "param=";
    
    /* -- CorTipoFuncAcompCtfa :: Código para posicionamento das imagens por grupos -- */
    /**
     *
     */
    public static int POSICAO_LISTAGEM  = 1;
    /**
     *
     */
    public static int POSICAO_DETALHE   = 2;
    /**
     *
     */
    public static int POSICAO_RELATORIO = 3;
    
    /* -- Util -- */
    /**
     *
     */
    public static final String REGEXP_METODOS_GET = "(get){1}[A-Z]+.*";
        /**
         *
         */
        public static final String REGEXP_METODOS_SET = "(set){1}[A-Z]+.*";
        /**
         *
         */
        public static final String REGEXP_TODOS	= ".*";

        /**
         *
         */
        public static final String CARACTER_ESTRANHO_MARCADOR = "•";
        /**
         *
         */
        public static final String CARACTER_ESTRANHO_MARCADOR2 = "–";
        /**
         *
         */
        public static final String CARACTER_ESTRANHO_ABREASPAS = "“";
        /**
         *
         */
        public static final String CARACTER_ESTRANHO_FECHAASPAS = "”";
        /**
         *
         */
        public static final String CARACTER_ESTRANHO_ABREASPAS_SIMPLES = "‘";
        /**
         *
         */
        public static final String CARACTER_ESTRANHO_FECHAASPAS_SIMPLES = "’";
	
	/* -- Controle Permissao -- */
        /**
         *
         */
        public static final String PERMISSAO_FUNCAO_ACOMPANHAMENTO = "F";
    /**
     *
     */
    public static final String PERMISSAO_USUARIO = "U";
    /**
     *
     */
    public static final String PERMISSAO_GRUPO = "G";
    
    /**
     *
     */
    public static final String OBRIGATORIO = "O";
    /**
     *
     */
    public static final String OPCIONAL = "P";

    /* -- Realizado Físico -- */
    /**
     *
     */
    public static final String TODOS = "T";
    /**
     *
     */
    public static final String CONCLUIDOS = "C";
    /**
     *
     */
    public static final String NAO_CONCLUIDOS = "N";
    
    /* -- Forma de inclusão de Contas Orçamentárias -- */
    /**
     *
     */
    public static final int CONTA_FORMA_INCLUSAO_VIA_CARGA = 1;
    /**
     *
     */
    public static final int CONTA_FORMA_INCLUSAO_VIA_USUARIO = 2;
    /**
     *
     */
    public static final int CONTA_FORMA_INCLUSAO_VIA_IMPORTACAO = 3;
	
    
    /* -- tipo adm -- */
    /**
     *
     */
    public static final String TIPO_ADM_DIRETA = "D";
    /**
     *
     */
    public static final String TIPO_ADM_INDIRETA = "I";
    
    /* -- Tipo orcamento -- */
    /**
     *
     */
    public static final String TIPO_ORC_PROPRIO = "P";
    /**
     *
     */
    public static final String TIPO_ORC_FISCAL = "F";
    /**
     *
     */
    public static final String TIPO_ORC_INVESTIMENTO = "I";
    
    /* -- Configuração de Envio de E-mail (ConfigMailCfgm) -- */
    /**
     *
     */
    public static final Integer CFG_MAIL_ALTERAR_DATA_LIMITE_REGISTRO_FISICO = new Integer(1);
    /**
     *
     */
    public static final Integer CFG_MAIL_ALTERAR_DATA_LIMITE_PARECER         = new Integer(2);
    /**
     *
     */
    public static final Integer CFG_MAIL_CRIACAO_REGISTRO_ACOMPANHAMENTO     = new Integer(3);
    /**
     *
     */
    public static final Integer CFG_MAIL_VENCIMENTO_LIMITE_FISICO            = new Integer(4);
    /**
     *
     */
    public static final Integer CFG_MAIL_VENCIMENTO_LIMITE_PARECER           = new Integer(5);
    /**
     *
     */
    public static final Integer CFG_MAIL_VENCIMENTO_LIMITE_PONTO_CRITICO     = new Integer(6);
    /**
     *
     */
    public static final Integer CFG_MAIL_DATA_TERMINO_ITEM                   = new Integer(7);
    /**
     *
     */
    public static final Integer CFG_MAIL_DATA_INICIO_ITEM                    = new Integer(8);
    /**
     *
     */
    public static final Integer CFG_MAIL_DATA_RESERVA_1                      = new Integer(9);
    /**
     *
     */
    public static final Integer CFG_MAIL_DATA_RESERVA_2                      = new Integer(10);
    /**
     *
     */
    public static final Integer CFG_MAIL_DATA_RESERVA_3                      = new Integer(11);
    /**
     *
     */
    public static final Integer CFG_MAIL_DATA_RESERVA_4                      = new Integer(12);
    /**
     *
     */
    public static final Integer CFG_MAIL_DATA_RESERVA_5                      = new Integer(13);
    /**
     *
     */
    public static final Integer CFG_MAIL_MANUTENCAO_EVENTO                   = new Integer(14);
    /**
     *
     */
    public static final Integer CFG_MAIL_MANUTENCAO_PONTO_CRITICO            = new Integer(15);
    /**
     *
     */
    public static final Integer CFG_MAIL_MANUTENCAO_ANEXO                    = new Integer(16);
    /**
     *
     */
    public static final Integer CFG_MAIL_MANUTENCAO_APONTAMENTO              = new Integer(17);
    /**
     *
     */
    public static final Integer CFG_MAIL_LIBERACAO_PARECER                   = new Integer(18);
    /**
     *
     */
    public static final Integer CFG_MAIL_RECUPERACAO_PARECER                 = new Integer(19);
    /**
     *
     */
    public static final Integer CFG_MAIL_CONCLUSAO_ACOMPANHAMENTO            = new Integer(20);
    /**
     *
     */
    public static final Integer CFG_MAIL_RECUPERACAO_ACOMPANHAMENTO          = new Integer(21);
    /**
     *
     */
    public static final Integer CFG_MAIL_ATIVAR_MONITORAMENTO                = new Integer(22);
    /**
     *
     */
    public static final Integer CFG_MAIL_BLOQUEAR_PLANEJAMENTO               = new Integer(23);
    /**
     *
     */
    public static final Integer CFG_MAIL_DESBLOQUEAR_PLANEJAMENTO            = new Integer(24);
    /**
     *
     */
    public static final Integer CFG_MAIL_DESATIVAR_MONITORAMENTO             = new Integer(25);
    /**
     *
     */
    public static final Integer CFG_MAIL_MANUTENCAO_FUNCOES_ACOMPANHAMENTO   = new Integer(26);
    
    /**
     *
     */
    public static final Integer CFG_MAIL_STATUS_INDICADOR_RESULTADO 		 = new Integer(27);
    
    /**
     * 
     */
    public static final Integer CFG_MAIL_INTERCAMBIO_DADOS	= new Integer(28);
    
    /* -- EfItemEstPrevisaoEfiep --*/
    /**
     *
     */
    public static final String EFIEP_VALOR_APROVADO = "A";
    /**
     *
     */
    public static final String EFIEP_VALOR_REVISADO = "R";
    
    /* -- ItemEstrtIndResulIettr (indicadores) -- */
    /**
     *
     */
    public static final String IETTR_QUANTIDADE = "Q";
    /**
     *
     */
    public static final String IETTR_VALOR = "V";
    
    /**
     *
     */
    public static final String COM_ACESSO_LEITURA = "S";
        /**
         *
         */
        public static final String SEM_ACESSO_LEITURA = "N";
	
        /**
         *
         */
        public static final String COM_ACESSO_INCLUSAO = "S";
        /**
         *
         */
        public static final String SEM_ACESSO_INCLUSAO = "N";
	
        /**
         *
         */
        public static final String ATIVO = "S";
        /**
         *
         */
        public static final String INATIVO = "N";
	
        /**
         *
         */
        public static final int SEPARAR_ORGAO_TODOS            = 1;
        /**
         *
         */
        public static final int SEPARAR_ORGAO_USUARIO          = 2;
        /**
         *
         */
        public static final int SEPARAR_ORGAO_RESPONSABILIDADE = 3;
	
        /**
         *
         */
        public static final String ESTILO_DEFAULT = "ecar";
    
        /**
         *
         */
        public static final String PREFIX_DATA = "data";
	
        /**
         *
         */
        public static final String STRING_VAZIA = "";
	
        public static final String ENCODING_DEFAULT = "ISO-8859-1";
        
        public static final int PERFIL_IDENTIFICADOR_SERVICO = 1;
        
        public static final String PERFIL_NOME_SERVICO = "Importação";
        
        public static final String PERFIL_MODO_PROCESSAMENTO_MANUAL = "M";
        
        public static final String PERFIL_MODO_PROCESSAMENTO_AUTOMATICO = "A";
        
        public static final int PERFIL_IDENTIFICADOR_SISTEMA_DESTINO = 1;
        
        public static final String PERFIL_NOME_SISTEMA_DESTINO = "e-CAR";
        
        public static final int PERFIL_IDENTIFICADOR_SISTEMA_ORIGEM = 2;
        
        public static final String PERFIL_NOME_SISTEMA_ORIGEM = "PACInter";
        
        public static final String PERFIL_PROTOCOLO_HTTP = "http";
        
        public static final String PERFIL_PROTOCOLO_HTTPS = "https";
        
        public static final String PERFIL_TIPO_HOST_DOMINIO = "D";
        
        public static final String PERFIL_TIPO_HOST_IP = "I";
        
        public static final int TAMANHO_MAXIMO_NOME_ARQUIVO_INTERCAMBIO_DADOS = 50;
        
} // fim class
