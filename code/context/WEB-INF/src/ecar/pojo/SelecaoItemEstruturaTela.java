/*
 * Criado em 03/02/2005
 * 
 */
package ecar.pojo;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @author felipev
 *
 * Classe usada para constru��o da tela 7.b.3 Gera��o do Per�odo de Refer�ncia -
 * Sele��o de Itens da Estrutura 
 *
 */
public class SelecaoItemEstruturaTela {
    
    private ItemEstruturaIett itemEstrutura;
    private boolean exibirEmTela;
    private int nivelItem;
   
    /**
     * @return Returns the exibirEmTela.
     */
    public boolean isExibirEmTela() {
        return exibirEmTela;
    }
    /**
     * @param exibirEmTela The exibirEmTela to set.
     */
    public void setExibirEmTela(boolean exibirEmTela) {
        this.exibirEmTela = exibirEmTela;
    }
    /**
     * @return Returns the itemEstrutura.
     */
    public ItemEstruturaIett getItemEstrutura() {
        return itemEstrutura;
    }
    /**
     * @param itemEstrutura The itemEstrutura to set.
     */
    public void setItemEstrutura(ItemEstruturaIett itemEstrutura) {
        this.itemEstrutura = itemEstrutura;
    }
    /**
     * @return Returns the nivelItem.
     */
    public int getNivelItem() {
        return nivelItem;
    }
    /**
     * @param nivelItem The nivelItem to set.
     */
    public void setNivelItem(int nivelItem) {
        this.nivelItem = nivelItem;
    }
    
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SelecaoItemEstruturaTela) ) return false;
        SelecaoItemEstruturaTela castOther = (SelecaoItemEstruturaTela) other;
        return new EqualsBuilder()
            .append(this.getItemEstrutura(), castOther.getItemEstrutura())
            .isEquals();
    }
    
    
}
