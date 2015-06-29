package ecar.servlet.relatorio.PPA_funcao.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import ecar.pojo.SubAreaSare;

/**
 *
 * @author 70744416353
 */
public class FuncaoSubAreaSareBean implements Serializable{

	private static final long serialVersionUID = -1271772962860232161L;
	
	private SubAreaSare subAreaSare;

    private String nomeSare;
    
    private Long codigoIdentSare;
    
	private BigDecimal valor1;
	private BigDecimal valor2;
	private BigDecimal valor3;
	private BigDecimal valor4;
	private BigDecimal total;	
	private Integer indice;
	
	
	
	
        /**
         *
         * @return
         */
        public Integer getIndice() {
		return indice;
	}

        /**
         *
         * @param indice
         */
        public void setIndice(Integer indice) {
		this.indice = indice;
	}

        /**
         *
         * @return
         */
        public SubAreaSare getSubAreaSare() {
		return subAreaSare;
	}

        /**
         *
         * @param subAreaSare
         */
        public void setSubAreaSare(SubAreaSare subAreaSare) {
		this.subAreaSare = subAreaSare;
	}

        /**
         *
         * @return
         */
        public BigDecimal getTotal() {
		return total;
	}

        /**
         *
         * @param total
         */
        public void setTotal(BigDecimal total) {
		this.total = total;
	}

        /**
         *
         * @return
         */
        public BigDecimal getValor1() {
		return valor1;
	}

        /**
         *
         * @param valor1
         */
        public void setValor1(BigDecimal valor1) {
		this.valor1 = valor1;
	}

        /**
         *
         * @return
         */
        public BigDecimal getValor2() {
		return valor2;
	}

        /**
         *
         * @param valor2
         */
        public void setValor2(BigDecimal valor2) {
		this.valor2 = valor2;
	}

        /**
         *
         * @return
         */
        public BigDecimal getValor3() {
		return valor3;
	}

        /**
         *
         * @param valor3
         */
        public void setValor3(BigDecimal valor3) {
		this.valor3 = valor3;
	}

        /**
         *
         * @return
         */
        public BigDecimal getValor4() {
		return valor4;
	}

        /**
         *
         * @param valor4
         */
        public void setValor4(BigDecimal valor4) {
		this.valor4 = valor4;
	}


    @Override
	public String toString() {
		return subAreaSare.getCodigoIdentSare().toString() + " " + subAreaSare.getNomeSare() + " " + valor1.toString() + " " + valor2.toString() + " " + valor3.toString() + " " + valor4.toString() + " " + total.toString();
	}

        /**
         *
         * @return
         */
        public Long getCodigoIdentSare() {
		return codigoIdentSare;
	}

        /**
         *
         * @param codigoIdentSare
         */
        public void setCodigoIdentSare(Long codigoIdentSare) {
		this.codigoIdentSare = codigoIdentSare;
	}

        /**
         *
         * @return
         */
        public String getNomeSare() {
		return nomeSare;
	}

        /**
         *
         * @param nomeSare
         */
        public void setNomeSare(String nomeSare) {
		this.nomeSare = nomeSare;
	}
	
	
}

