package ecar.api.facade;

import java.util.Date;

import ecar.pojo.AcompReferenciaAref;

/**
 * Classe wrapper para a classe {@link AcompReferenciaAref} que é o período
 * de referência utilizado no acompanhamento para informar uma valor de um 
 * indicador.
 * 
 * @author 82035644020
 *
 */
public class PeriodoAcompanhamento implements EcarWrapperInterface<AcompReferenciaAref>{

	
	private AcompReferenciaAref periodo = null;
		
	public PeriodoAcompanhamento(long id){
		//TODO: buscar do banco de dados
	}
	
	public PeriodoAcompanhamento(AcompReferenciaAref periodo){
		this.periodo = periodo;
	}
	
	public String getNome(){
		return periodo.getNomeAref();
	}
	
	
	public long getId(){
		return periodo.getCodAref();
	}
	
	public Date getInicioPeriodo(){
		return periodo.getDataInicioAref();
	}
	
	public Date getDataLimite(){
		return periodo.getDataLimiteAcompFisicoAref();
	}
	
	
	public String getAnoReferencia(){
		return periodo.getAnoAref();
	}

	
	public String getMesReferencia(){
		return periodo.getMesAref();
	}


	public Exercicio getExercicio(){
		return new Exercicio(periodo.getExercicioExe());
	}

	/*
	 * (non-Javadoc)
	 * @see ecar.api.facade.EcarWrapperInterface#getRealObject()
	 */
	public AcompReferenciaAref getRealObject() {
		return this.periodo;
	}
	
}
