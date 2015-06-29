package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import ecar.action.ActionSisAtributo;
import ecar.exception.ECARException;

/**
 *
 * @author 70744416353
 */
public class ItemEstruturaSisAtributoIettSatb extends FuncaoSisAtributo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3298092790581090133L;
    private ecar.pojo.ItemEstruturaSisAtributoIettSatbPK comp_id;
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;
    private Boolean indExclusaoPosHistorico;
    private ecar.pojo.ItemEstruturaIettPPA itemEstruturaIettPPA;

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstruturaIettPPA getItemEstruturaIettPPA() {
        return itemEstruturaIettPPA;
    }

    /**
     *
     * @param itemEstruturaIettPPA
     */
    public void setItemEstruturaIettPPA(
            ecar.pojo.ItemEstruturaIettPPA itemEstruturaIettPPA) {
        this.itemEstruturaIettPPA = itemEstruturaIettPPA;
    }

    /**
     *
     * @return
     */
    public Boolean getIndExclusaoPosHistorico() {
        return indExclusaoPosHistorico;
    }

    /**
     *
     * @param indExclusaoPosHistorico
     */
    public void setIndExclusaoPosHistorico(Boolean indExclusaoPosHistorico) {
        this.indExclusaoPosHistorico = indExclusaoPosHistorico;
    }

    /**
     * Default Constructor
     */
    public ItemEstruturaSisAtributoIettSatb() {
    }

    /**
     * Min Constructor
     * @param comp_id
     */
    public ItemEstruturaSisAtributoIettSatb(ItemEstruturaSisAtributoIettSatbPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     * Full Constructor
     * @param comp_id
     * @param itemEstruturaIett
     * @param sisAtributoSatb
     * @param informacaoIettSatb
     * @param dataUltManutencaoIettSatb
     */
    public ItemEstruturaSisAtributoIettSatb(ItemEstruturaSisAtributoIettSatbPK comp_id, ItemEstruturaIett itemEstruturaIett, SisAtributoSatb sisAtributoSatb, String informacaoIettSatb, Date dataUltManutencaoIettSatb) {
        super(informacaoIettSatb, dataUltManutencaoIettSatb, sisAtributoSatb);
        this.comp_id = comp_id;
        this.itemEstruturaIett = itemEstruturaIett;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstruturaSisAtributoIettSatbPK getComp_id() {
        return comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.ItemEstruturaSisAtributoIettSatbPK comp_id) {
        this.comp_id = comp_id;
    }

     /**
     *
     * @return
     */
    public ecar.pojo.ItemEstruturaIett getItemEstruturaIett() {
        return itemEstruturaIett;
    }

    /**
     *
     * @param itemEstruturaIett
     */
    public void setItemEstruturaIett(ecar.pojo.ItemEstruturaIett itemEstruturaIett) {
        this.itemEstruturaIett = itemEstruturaIett;
    }

    /**
     *
     */
    public void atribuirPKPai() {
        comp_id = new ItemEstruturaSisAtributoIettSatbPK();
        comp_id.setCodIett(this.getItemEstruturaIett().getCodIett());
        comp_id.setCodSatb(this.getSisAtributoSatb().getCodSatb());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("codIett", getComp_id().getCodIett()).append("codSatb", getComp_id().getCodSatb()).toString();
    }

    @Override
    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if (!(other instanceof ItemEstruturaSisAtributoIettSatb)) {
            return false;
        }
        ItemEstruturaSisAtributoIettSatb castOther = (ItemEstruturaSisAtributoIettSatb) other;
        return new EqualsBuilder().append(this.getItemEstruturaIett().getCodIett(), castOther.getItemEstruturaIett().getCodIett()).append(this.getSisAtributoSatb().getCodSatb(), castOther.getSisAtributoSatb().getCodSatb()).isEquals();
    }

    /**
     * Cria os tipos valores necessários para serem persistidos e retorna o conjunto.
     * @throws ECARException
     * @Override
     */
    public void atualizaListaTiposValores(SisAtributoSatb sisAtributo, SequenciadoraSeq sequenciador, ActionSisAtributo action, FuncaoFun funcao)
            throws ECARException {

        Set tiposValores = new HashSet();

        //Tipo Valor Mês
        TipoValor tipoMes = new TipoValor();
        String mes = action.obterMes(this.getInformacao(), sisAtributo.getMascara());
        tipoMes.setConteudo(mes);
        tipoMes.setItemEstruturaSisAtributo(this);
        tipoMes.setTipo(TipoValorEnum.MES);
        tipoMes.setFuncao(funcao);
        tiposValores.add(tipoMes);

        //Tipo Valor Ano
        TipoValor tipoAno = new TipoValor();
        String ano = action.obterAno(this.getInformacao(), sisAtributo.getMascara());
        tipoAno.setConteudo(ano);
        tipoAno.setItemEstruturaSisAtributo(this);
        tipoAno.setTipo(TipoValorEnum.ANO);
        tipoAno.setFuncao(funcao);
        tiposValores.add(tipoAno);

        //Tipo Valor Sequencial
        TipoValor tipoSequencial = new TipoValor();
        if (sequenciador != null) {//Se o sequenciador existe então utilizo o sequenciador gerado (caso de inclusão de item)
            tipoSequencial.setConteudo(sequenciador.getSequenciaSeq() + "");
        } else {//Se o sequenciador não existe então obtem o sequencial do proprio objeto atributoLivreBean e utiliza na lista (caso de alteração de item)
			/*IMPORTANTE:
             * O objeto atributoLivreBean do tipo ItemEstruturaSisAtributoIettSatb é um objeto criado com os dados da tela portanto a lista de
             * tipos Valores está nula. Porém o itemEstrutura contido no atributoLivreBean é proveniente do banco e possui a lista completa
             * dos AtributosLivres armazenados.
             */

            ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributoNegocio = this.getItemEstruturaIett().buscarItemEstruturaSisAtributoLista(sisAtributo);

            TipoValor seq = null;
            Iterator<TipoValor> it = null;

            it = itemEstruturaSisAtributoNegocio.getTiposValores().iterator();

            while (it.hasNext()) {
                seq = (TipoValor) it.next();

                if (seq.getTipo().compareTo(TipoValorEnum.SEQUENCIAL) == 0) {
                    break;
                }
            }

            tipoSequencial.setConteudo(seq.getConteudo());
        }
        tipoSequencial.setItemEstruturaSisAtributo(this);
        tipoSequencial.setTipo(TipoValorEnum.SEQUENCIAL);
        tipoSequencial.setFuncao(funcao);
        tiposValores.add(tipoSequencial);

        //Tipo Valor Mascara
        if (!sisAtributo.isAtributoAutoIcremental()) {
            TipoValor tipoMascara = new TipoValor();
            tipoMascara.setConteudo(sisAtributo.getMascara());
            tipoMascara.setItemEstruturaSisAtributo(this);
            tipoMascara.setTipo(TipoValorEnum.MASCARA);
            tipoMascara.setFuncao(funcao);
            tiposValores.add(tipoMascara);
        }

        this.setTiposValores(tiposValores);


    }

    /**
     * Copia a lista de tipos Valores sem os ID's
     * @param tiposValores
     * @param funcao
     * @Override
     */
    public void copiarTipoValorSemID(Set<TipoValor> tiposValores, FuncaoFun funcao) {

        Set listaTiposValores = new HashSet();
        for (TipoValor tipoValor : tiposValores) {

            TipoValor tipoValorInner = new TipoValor();

            tipoValorInner.setConteudo(tipoValor.getConteudo());
            tipoValorInner.setItemEstruturaSisAtributo(this);
            tipoValorInner.setTipo(tipoValor.getTipo());
            tipoValorInner.setFuncao(funcao);

            listaTiposValores.add(tipoValorInner);

        }
        this.setTiposValores(listaTiposValores);

    }

    /**
     * @author N/C
     * @since N/C
     * @return int
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
