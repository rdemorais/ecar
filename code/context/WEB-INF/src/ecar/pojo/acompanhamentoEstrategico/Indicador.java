/***********************************************************************
 * Module:  Indicador.java
 * Author:  leonardo.marques
 * Purpose: Defines the Class Indicador
 ***********************************************************************/

package ecar.pojo.acompanhamentoEstrategico;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Indicador {
	private Integer codigo = -1;
	private String nome;
	private double meta;
	private int ano;
	private java.util.Map<String, Double> valorRealizado;
	private double consolidado;
	private String metodoCalculo;
	private String unidadeMedida;
	private String fonte;
	private String formula;
	/*
	 * Campos usados no relat�rio
	 * */
	private String meta2012 = "-";
	private String meta2013 = "-";
	private String meta2014 = "-";
	private String meta2015 = "-";
	private String valorRealizado2012 = "-";
	private String valorRealizado2013 = "-";
	private String valorRealizado2014 = "-";
	private String valorRealizado2015 = "-";
	private String cosolidadoAnual = "-";
	
	//Campos usados no relatório de Detalhamento Indicadores
	public List<IndicadorDetalhamento> detalhe = new ArrayList<IndicadorDetalhamento>(); 
	
	
	public Indicador() {
		
	}
	
	public Indicador(Integer codigo, String unidadeMedida, String nome, String metodoCalculo, String fonte, String formula){
		this.codigo = codigo;
		this.unidadeMedida = unidadeMedida;
		this.nome = nome;
		this.metodoCalculo = metodoCalculo;
		this.fonte = fonte;
		this.formula = formula;
	}
	
	public Indicador(Long codigo, String nome, String metodoCalculo) {
		this.codigo = codigo.intValue();
		this.nome = nome;
		this.metodoCalculo = metodoCalculo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getMeta() {
		return meta;
	}

	public void setMeta(double meta) {
		this.meta = meta;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public java.util.Map<String, Double> getValorRealizado() {
		return valorRealizado;
	}

	public void setValorRealizado(java.util.Map<String, Double> valorRealizado) {
		this.valorRealizado = valorRealizado;
	}

	public double getConsolidado() {
		return consolidado;
	}

	public void setConsolidado(double consolidado) {
		this.consolidado = consolidado;
	}

	public String getMetodoCalculo() {
		return metodoCalculo;
	}

	public void setMetodoCalculo(String metodoCalculo) {
		this.metodoCalculo = metodoCalculo;
	}

	public String getMeta2012() {
		return meta2012;
	}

	public void setMeta2012(String meta2012) {
		this.meta2012 = meta2012;
	}

	public String getMeta2013() {
		return meta2013;
	}

	public void setMeta2013(String meta2013) {
		this.meta2013 = meta2013;
	}

	public String getMeta2014() {
		return meta2014;
	}

	public void setMeta2014(String meta2014) {
		this.meta2014 = meta2014;
	}

	public String getMeta2015() {
		return meta2015;
	}

	public void setMeta2015(String meta2015) {
		this.meta2015 = meta2015;
	}

	public String getValorRealizado2012() {
		return valorRealizado2012;
	}

	public void setValorRealizado2012(String valorRealizado2012) {
		this.valorRealizado2012 = valorRealizado2012;
	}

	public String getValorRealizado2013() {
		return valorRealizado2013;
	}

	public void setValorRealizado2013(String valorRealizado2013) {
		this.valorRealizado2013 = valorRealizado2013;
	}

	public String getValorRealizado2014() {
		return valorRealizado2014;
	}

	public void setValorRealizado2014(String valorRealizado2014) {
		this.valorRealizado2014 = valorRealizado2014;
	}

	public String getValorRealizado2015() {
		return valorRealizado2015;
	}

	public void setValorRealizado2015(String valorRealizado2015) {
		this.valorRealizado2015 = valorRealizado2015;
	}

	public String getCosolidadoAnual() {
		return cosolidadoAnual;
	}

	public void setCosolidadoAnual(String cosolidadoAnual) {
		this.cosolidadoAnual = cosolidadoAnual;
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public String getFonte() {
		return fonte;
	}

	public void setFonte(String fonte) {
		this.fonte = fonte;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public List<IndicadorDetalhamento> getDetalhe() {
		if (detalhe == null)
			detalhe = new ArrayList<IndicadorDetalhamento>();
		return detalhe;
	}

	public void setDetalhe(List<IndicadorDetalhamento> detalhe) {
		this.detalhe = detalhe;
	}

	@Override
	public String toString() {
		return "Indicador [codigo=" + codigo + ", nome=" + nome + ", meta=" + meta + ", ano=" + ano + ", valorRealizado=" + valorRealizado + ", consolidado=" + consolidado + ", metodoCalculo=" + metodoCalculo + ", unidadeMedida=" + unidadeMedida + ", fonte=" + fonte + ", formula=" + formula
				+ ", meta2012=" + meta2012 + ", meta2013=" + meta2013 + ", meta2014=" + meta2014 + ", meta2015=" + meta2015 + ", valorRealizado2012=" + valorRealizado2012 + ", valorRealizado2013=" + valorRealizado2013 + ", valorRealizado2014=" + valorRealizado2014 + ", valorRealizado2015="
				+ valorRealizado2015 + ", cosolidadoAnual=" + cosolidadoAnual + ", detalhe=" + detalhe + "]";
	}
	
	
}