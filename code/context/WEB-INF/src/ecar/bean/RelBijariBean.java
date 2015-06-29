package ecar.bean;

import java.util.List;

import ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico;

/**
 * Classe a ser usanda no relat�rio relBijari.jasper
 * */
public class RelBijariBean {
	
	/* Sub-relat�rio Objetivos Estrat�gicos */
	private List<ObjetivoEstrategico> objetivosEstrategicos;

	
	
	
	/* Getters e Setters */
	public List<ObjetivoEstrategico> getObjetivosEstrategicos() {
		return objetivosEstrategicos;
	}

	public void setObjetivosEstrategicos(
			List<ObjetivoEstrategico> objetivosEstrategicos) {
		this.objetivosEstrategicos = objetivosEstrategicos;
	}
	
	

}
