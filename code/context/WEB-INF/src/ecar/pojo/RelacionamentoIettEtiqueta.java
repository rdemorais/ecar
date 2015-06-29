package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class RelacionamentoIettEtiqueta implements Serializable {

	private static final long serialVersionUID = 5537438278288177332L;
	private Long codigo;
	private String indAtivo;
	
    /** nullable persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    /** nullable persistent field */
    private ecar.pojo.Etiqueta etiqueta;


    
    

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public ecar.pojo.ItemEstruturaIett getItemEstruturaIett() {
		return itemEstruturaIett;
	}

	public void setItemEstruturaIett(ecar.pojo.ItemEstruturaIett itemEstruturaIett) {
		this.itemEstruturaIett = itemEstruturaIett;
	}

	public ecar.pojo.Etiqueta getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(ecar.pojo.Etiqueta etiqueta) {
		this.etiqueta = etiqueta;
	}

	
	
	public String getIndAtivo() {
		return indAtivo;
	}

	public void setIndAtivo(String indAtivo) {
		this.indAtivo = indAtivo;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("codigo", getCodigo())
				.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCodigo()).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof RelacionamentoIettEtiqueta))
			return false;
		RelacionamentoIettEtiqueta castOther = (RelacionamentoIettEtiqueta) other;
		return new EqualsBuilder().append(this.getCodigo(),
				castOther.getCodigo()).isEquals();
	}

}
