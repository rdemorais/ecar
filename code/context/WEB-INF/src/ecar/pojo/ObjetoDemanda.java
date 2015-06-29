package ecar.pojo;

import ecar.exception.ECARException;

/**
 *
 * @author 70744416353
 */
public interface ObjetoDemanda {

	/**
     * Define um valor limite para a sequencia de um campo na tela.
     * Quando o usuario esquece de informar a sequencia do campo no cadastro de
     * atributo_demanda, o campo toma este valor como default, ou seja, ficar�
     * por ultimo na tela.
     */
    public static final int MAX_SEQUENCIA_TELA_CAMPO = 1000;

    /**
     * Define um valor limite para a sequencia de colunas em listagens de atributos
     * demanda.
     * Quando o usuario esquece de informar a sequencia da coluna no cadastro de
     * atributo_demanda, a coluna toma este valor como default, ou seja, ficar�
     * por ultimo na lista.
     */
    public static final int MAX_SEQUENCIA_COLUNA_LISTAGEM = 1000;
    
    
    /**
     * Define a largura em porcentagem de uma coluna em telas de listagem.
     * Quando usuario esquece de definir a largura de uma coluna em telas de listagem
     * assume esse valor como default.
     */
    public static final int DEFAULT_LARGURA_TELA_CAMPO = 100;
    
    /**
     * Define o tamanho default de um campo input, quando o usu�rio n�o define o tamanho.
     * Este tamanho tamb�m � levado em conta na hora do sistema exibir uma text ou textArea.
     * Quando o usu�rio define um tamanho maior que 80, ser� exibido um textArea. Caso o valor
     * seja menor que 80, ser� exibido um text.
     */
    public static final int DEFAULT_TAMANHO_CAMPO_TEXT = 80;
    
    /**
     * Devolve o nome do atributo da demanda.
     * Este � o nome que est� mapeado como um campo na tabela tb_reg_demanda_regd.
     * O nome est� na sintaxe de atributo de objeto, ex: descricaoRegd
     * @return nome do campo na tabela RegDemandaRegd
     */
    public String iGetNome();

    /**
     * Devolve o nome do codigo da fk descrito no atributo_demanda. por exemplo codOrg
     * @return
     */
    public String iGetCodFk();
    
    /**
     * Devolve o nome do nome da fk descrito no atributo_demanda. por exemplo descricaoOrg
     * @return
     */
    public String iGetNomeFk();
    
    
    /**
     * Devolve o label a ser utilizado em telas de cadastro e relat�rios.     * 
     * @return nome do label que ser� mostrado ao usu�rio
     */
    public String iGetLabel();
    
    /**
     * Obtem o valor cadastrado na tabela de regDemandaRegd de um determinado campo.
     * Utiliza o regDemanda e o nome no atributo demanda para determinar seu valor.
     * Por exemplo, devolve o valor do campo nome_iett realizando um getDescricaoRegd() ou
     * getOrgaoOrg().getDescricaoOrg() conforme estiver cadastrado em tb_atributo_demanda_atbdem no campo nome_fk_atbdem 
     * 
     * @param regDemanda
     * @return string contendo o valor do campo na tabela ItemEstruturaIett
     * @throws ECARException
     */
    public String iGetValor(RegDemandaRegd regDemanda) throws ECARException;
    
    /**
     * Obtem o valor do codigo de um campo caso ele seja uma FK dentro da tabela de RegDemandaRegd.
     * Por exemplo, para OrgaoOrg, vai voltar o valor do codigo do Org�o (codOrg).
     * Para um objeto que n�o seja FK, n�o se aplica, vai retornar o mesmo valor que iGetValor
     * @param regDemanda
     * @return string contendo o codigo do campo na tabela RegDemandaRegd
     * @throws ECARException
     */
    public String iGetValorCodFk(RegDemandaRegd regDemanda) throws ECARException;
    
    /**
     * Obtem o conjunto de valores do codigo de um campo caso ele seja uma FK dentro 
     * de uma tabela de relacionamentos que se relaciona com RegDemandaRegd.
     * Por exemplo, para OrgaoOrg, vai voltar o valor do codigo do Org�o (codOrg).
     * Para um objeto que n�o seja FK, n�o se aplica, vai retornar o mesmo valor que iGetValor
     * @param regDemanda
     * @return string contendo o codigo do campo na tabela RegDemandaRegd
     * @throws ECARException
     */
    public String iGetValoresCodFk(RegDemandaRegd regDemanda) throws ECARException;
      
    /**
     * Devolve a largura de uma coluna em porcentagem nas telas de listagens de item em colunas.
     * Se nao estiver especificado um valor, deve retornar DEFAULT_LARGURA_TELA_CAMPO
     * @param codigo da visao
     * @return valor correspondendo a largura de uma coluna
     */
    public Integer iGetLargura();
    
    /**
     * Devolve a largura de uma coluna em porcentagem nas telas de listagens de item em colunas.
     * Se nao estiver especificado um valor, deve retornar DEFAULT_LARGURA_TELA_CAMPO
     * @param codVisao
     * @return valor correspondendo a largura de uma coluna
     */
    public Integer iGetLargura(Long codVisao);
       
    /**
     * Retorna o tipo da classe que est� encapsulada no ObjetoDemanda.
     * Geralmente deve retornar ecar.pojo.RegDemandaRegd
     * @return o tipo da classe
     */
    public Class iGetTipo();
     
    /**
     * Retorna verdadeiro ou falso dependendo da obrigatoriedade do objeto
     * @param codVisao
     * @return Boolean(true) ou Boolean(false)
     */
    public Boolean iGetObrigatorio(Long codVisao);
    
    /**
     * Devolve a dica do campo a ser utilizado em telas de cadastro e relat�rios.
     * � o campo dica.
     * @param Long codigo da visao
     * @return dica do campo que ser� mostrado ao usu�rio
     */
    public String iGetDica(Long codVisao);
    
    /**
     * Devolve valor de campo que determinar se um campo � vis�vel ou n�o
     * @param Long codigo da visao
     * @return � exibel ou nao
     */
    public Boolean iGetExibivel(Long codVisao);
    
    
    /**
     * Devolve valor de campo que determinar se um campo � vis�vel ou n�o
     * @param Long codigo da visao
     * @return � exibel ou nao
     */
    public Boolean iGetExibivelConsulta(Long codVisao);
    
    /**
     * Devolve o grupo de atributos livres de um atributo demanda
     * 
     * @return SisGrupoAtributoSga
     */
    public SisGrupoAtributoSga iGetGrupoAtributosLivres();
    
    /**
     * Devolve o tamanho do conte�do de um atributo demanda.
     * 
     * @return Integer
     */
    public Integer iGetTamanhoConteudoAtbdem();
	  
    /**
     * Devolve a hierarquia dos locais de uma demanda (cidade - sigla - estado)
     * 
     * @param regDemanda
     * @return
     * @throws ECARException
     */
    public String iGetHierarquiaLocaisDemanda(RegDemandaRegd regDemanda) throws ECARException;
    
    /**
     * Devolve o c�digo (chave prim�ria) de um atributo demanda.
     * 
     * @return Long
     */        
    public Long iGetCodAtbdem();
    
    /**
     * Devolve a indica��o do atributo ser edit�vel ou n�o na vis�o passada por par�metro.
     * 
     * @param codVisao
     * @return
     */
    public String iGetIndEditavel(Long codVisao);
}
