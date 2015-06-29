package ecar.bean;

import java.util.List;

import ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico;

/**
 * Classe a ser usanda no relatório relBijari.jasper
 * */
public class RelBijariBean {
	
	/* Sub-relatório Objetivos Estratégicos */
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
