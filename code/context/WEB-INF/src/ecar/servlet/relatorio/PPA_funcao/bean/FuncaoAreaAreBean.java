package ecar.servlet.relatorio.PPA_funcao.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import ecar.pojo.AreaAre;

/**
 *
 * @author 70744416353
 */
public class FuncaoAreaAreBean implements Serializable {

	private static final long serialVersionUID = -3879525783241417242L;

	private AreaAre areaAre;

    private Long codigoIdentAre;
    
    private String nomeAre;
	
	private TreeSet<FuncaoSubAreaSareBean> subAreaList;

	private BigDecimal valor1;

	private BigDecimal valor2;

	private BigDecimal valor3;

	private BigDecimal valor4;

	private BigDecimal total;

	private ArrayList<Long> codIettList;
	
	
        /**
         *
         * @return
         */
        public ArrayList<Long> getCodIettList() {
		return codIettList;
	}

        /**
         *
         * @param codIettList
         */
        public void setCodIettList(ArrayList<Long> codIettList) {
		this.codIettList = codIettList;
	}

        /**
         *
         * @return
         */
        public AreaAre getAreaAre() {
		return areaAre;
	}

        /**
         *
         * @param areaAre
         */
        public void setAreaAre(AreaAre areaAre) {
		this.areaAre = areaAre;
	}

        /**
         *
         * @return
         */
        public TreeSet<FuncaoSubAreaSareBean> getSubAreaList() {
		return subAreaList;
	}

        /**
         *
         * @param subAreaList
         */
        public void setSubAreaList(TreeSet<FuncaoSubAreaSareBean> subAreaList) {
		this.subAreaList = subAreaList;
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
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((areaAre == null) ? 0 : areaAre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final FuncaoAreaAreBean other = (FuncaoAreaAreBean) obj;
		if (areaAre == null) {
			if (other.areaAre != null)
				return false;
		} else if (!areaAre.equals(other.areaAre))
			return false;
		return true;
	}

    @Override
	public String toString() {

		StringBuilder iettStr = new StringBuilder();
		for (Iterator iter = codIettList.iterator(); iter.hasNext();) {
			Long element = (Long) iter.next();
			iettStr.append(  element.toString() + " " );
		}

		return areaAre.getCodigoIdentAre().toString() + " " + areaAre.getNomeAre() + " " +
		valor1.toString() + " " + valor2.toString() + " " + valor3.toString() + " " + 
		valor4.toString() + " " + total.toString() + " Itens:: " + iettStr.toString();
		
	}

    /**
     *
     * @return
     */
    public Long getCodigoIdentAre() {
		return codigoIdentAre;
	}

        /**
         *
         * @param codigoIdentAre
         */
        public void setCodigoIdentAre(Long codigoIdentAre) {
		this.codigoIdentAre = codigoIdentAre;
	}

        /**
         *
         * @return
         */
        public String getNomeAre() {
		return nomeAre;
	}

        /**
         *
         * @param nomeAre
         */
        public void setNomeAre(String nomeAre) {
		this.nomeAre = nomeAre;
	}

}
