/*
 * Created on 05/01/2005
 * 
 * Esta interface normaliza os metodos de acesso aos objetos de uma estrutura.
 * Os objetos de uma estrutura s�o estrutura_atributo e estrutura_funcao_acompanhamento.
 * O objetivo � enxergar os dois tipos de objetos como um objetoEstrutura, para facilitar 
 * a construcao de listas, relatorios e o form de dados gerais do item
 *
 */
package ecar.pojo;

import java.util.Set;

import ecar.exception.ECARException;
import ecar.pojo.historico.HistoricoItemEstruturaIett;
import ecar.pojo.historico.HistoricoPontoCriticoPtc;

/**
 * @author garten
 *
 */
public interface ObjetoEstrutura {
    
    /**
     * Define um valor limite para a sequencia de um campo na tela de dados gerais.
     * Quando o usuario esquece de informar a sequencia do campo no cadastro de
     * estrutura_atributo, o campo toma este valor como default, ou seja, ficar�
     * por ultimo na tela.
     */
    public static final int MAX_SEQUENCIA_TELA_CAMPO = 1000;

    /**
     * Define um valor limite para a sequencia de colunas em listagens de itens.
     * Quando o usuario esquece de informar a sequencia da coluna no cadastro de
     * estrutura_atributo, a coluna toma este valor como default, ou seja, ficar�
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
     * Devolve o nome do atributo da estrutura.
     * Este � o nome que est� mapeado como um campo na tabela TB_Item_Estrutura_Iett.
     * O nome est� na sintaxe de atributo de objeto, ex: nomeIett
     * @return nome do campo na tabela ItemEstruturaIett
     */
    public String iGetNome();

    /**
     * Devolve o nome do codigo da fk descrito no atributo_atb. por exemplo codAre
     * @return
     */
    public String iGetCodFk();
    
    /**
     * Devolve o nome do nome da fk descrito no atributo_atb. por exemplo nomeAre
     * @return
     */
    public String iGetNomeFk();
    
    /**
     * Devolve o nome do atributo que ser� utilizado para ordenar uma lista de itemEstruturaIett.
     * Ser� utilizado na clausula order by do HQL que lista os itens na relacao de acesso
     * � estrutura. Precisa ser um campo que est� mapeado na tabela ItemEstruturaIett. 
     * 
     * Se for um objeto complexo, resolve o campo desse objeto. ex: "codOrg.descricaoOrg"
     * Ex. retorna "codOrg.descricaoOrg" ou "nomeIett", etc
     * @return nome do campo que ser� utilizado para ordenar os itens.
     */
    public String iGetNomeOrdenarLista();
    
    /**
     * Devolve o label a ser utilizado em telas de cadastro e relat�rios.
     * Geralmente � o labelEstruturaEttat ou labelPadraoAtb ou labelTpfa
     * @return nome do label que ser� mostrado ao usu�rio
     */
    public String iGetLabel();
    
    /**
     * Obtem o valor cadastrado na tabela de itemEstruturaIett de um determinado campo.
     * Utiliza o item e o nome no atributo para determinar seu valor.
     * Por exemplo, devolve o valor do campo nome_iett realizando um getNomeIett() ou
     * getOrgaoOrg().getDescricaoOrg() conforme estiver cadastrado em tb_atributos_atb no campo nome_fk_atb 
     * 
     * @param item
     * @return string contendo o valor do campo na tabela ItemEstruturaIett
     * @throws ECARException
     */
    public String iGetValor(ItemEstruturaIett item) throws ECARException;
    
    /**
     *
     * @param historicoItemEstruturaIett
     * @return
     * @throws ECARException
     */
    public String iGetValor(HistoricoItemEstruturaIett historicoItemEstruturaIett) throws ECARException;

    /**
     *
     * @param pontoCriticoPtc
     * @return
     * @throws ECARException
     */
    public String iGetValor(PontoCriticoPtc pontoCriticoPtc) throws ECARException;
    
    /**
     *
     * @param historicoPontoCriticoPtc
     * @return
     * @throws ECARException
     */
    public String iGetValor(HistoricoPontoCriticoPtc historicoPontoCriticoPtc) throws ECARException;
    
    /**
     *
     * @param object
     * @return
     * @throws ECARException
     */
    public String iGetValor(Object object) throws ECARException;

    /**
     * Obtem o valor do codigo de um campo caso ele seja uma FK dentro da tabela de ItemEstruturaIett.
     * Por exemplo, para AreaAre, vai voltar o valor do codigo da Area (codAre).
     * Para um objeto que n�o seja FK, n�o se aplica, vai retornar o mesmo valor que iGetValor
     * @param item
     * @return string contendo o codigo do campo na tabela ItemEstruturaIett
     * @throws ECARException
     */
    public String iGetValorCodFk(ItemEstruturaIett item) throws ECARException;
    
    /**
     *
     * @param historicoItemEstruturaIett
     * @return
     * @throws ECARException
     */
    public String iGetValorCodFk(HistoricoItemEstruturaIett historicoItemEstruturaIett) throws ECARException;
    
    /**
     *
     * @param pontoCriticoPtc
     * @return
     * @throws ECARException
     */
    public String iGetValorCodFk(PontoCriticoPtc pontoCriticoPtc) throws ECARException;
    
    /**
     *
     * @param historicoPontoCriticoPtc
     * @return
     * @throws ECARException
     */
    public String iGetValorCodFk(HistoricoPontoCriticoPtc historicoPontoCriticoPtc) throws ECARException;
    
    /**
     *
     * @param object
     * @return
     * @throws ECARException
     */
    public String iGetValorCodFk(Object object) throws ECARException;
    /**
     * Devolve a largura de uma coluna em porcentagem nas telas de listagens de item em colunas.
     * Se nao estiver especificado um valor, deve retornar DEFAULT_LARGURA_TELA_CAMPO
     * @return valor correspondendo a largura de uma coluna
     */
    public Integer iGetLargura();
    
    /**
     * Devolve o numero que corresponde � sequencia das colunas em telas de listagem.
     * @return o numero de sequencia entre 0..1000
     */
    public Integer iGetSequenciaColunaEmListagem();

    /**
     * Devolve o numero que corresponde � sequencia dos campos em uma tela de cadastro. 
     * @return numero da sequencia entre 0..1000
     */
    public Integer iGetSequenciaCampoEmTela();
    
    /**
     * Retorna o tipo da classe que est� encapsulada no ObjetoEstrutura.
     * Geralmente deve retornar ecar.pojo.EstruturaAtributoEttAt ou ecar.pojo.EstrutTpFuncAcmpEtttfa
     * @return o tipo da classe
     */
    public Class iGetTipo();
    
    /**
     * Retorna verdadeiro ou falso dependendo da obrigatoriedade do objeto
     * @return Boolean(true) ou Boolean(false)
     */
    public Boolean iGetObrigatorio();
    
    /**
     * Retorna verdadeiro ou falso dependendo da configura��o do atributo poder ser alterado,
     * mesmo o cadastro estando bloqueado.
     * @return Boolean(true) ou Boolean(false)
     */
    public Boolean iGetBloqueado();
    
    /**
     * Obtem o valor cadastrado na tabela de itemEstruturaIett de um determinado campo.
     * Utiliza o item e o nome no atributo para determinar seu valor.
     * Por exemplo, devolve o valor do campo nome_iett realizando um getNomeIett() ou
     * getOrgaoOrg().getDescricaoOrg() conforme estiver cadastrado em tb_atributos_atb no campo nome_fk_atb 
     * 
     * @param itemRev
     * @return string contendo o valor do campo na tabela ItemEstruturaIett
     * @throws ECARException
     */
    public String iGetValor(ItemEstruturarevisaoIettrev itemRev) throws ECARException;

    /**
     * Obtem o valor do codigo de um campo caso ele seja uma FK dentro da tabela de ItemEstruturaIett.
     * Por exemplo, para AreaAre, vai voltar o valor do codigo da Area (codAre).
     * Para um objeto que n�o seja FK, n�o se aplica, vai retornar o mesmo valor que iGetValor
     * @param itemRev
     * @return string contendo o codigo do campo na tabela ItemEstruturaIett
     * @throws ECARException
     */
    public String iGetValorCodFk(ItemEstruturarevisaoIettrev itemRev) throws ECARException;

    /**
     *
     * @return
     */
    public Boolean iGetIndOpcional();
    
    /**
     * Devolve a dica do campo a ser utilizado em telas de cadastro e relat�rios.
     * � o campo dicaEttat.
     * @return dica do campo que ser� mostrado ao usu�rio
     */
    public String iGetDica();
    
    /**
     * Devolve o grupo de atributos livres de um atributo
     * 
     * @return SisGrupoAtributoSga
     */
    public SisGrupoAtributoSga iGetGrupoAtributosLivres();
    
    /**
     * Devolve o tamanho do conte�do de um atributo.
     * 
     * @return Integer
     */
    public Integer iGetTamanhoConteudoAtrib();
    
    /**
     *
     * @return
     */
    public Set iGetLibTipoFuncAcompTpfas();
}
