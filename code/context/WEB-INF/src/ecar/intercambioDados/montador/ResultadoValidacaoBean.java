/**
 * 
 */
package ecar.intercambioDados.montador;

import java.util.ArrayList;
import java.util.List;

import ecar.intercambioDados.IBusinessObject;

/**
 * Bean com resultado da validação de intercambio de dados
 */
public class ResultadoValidacaoBean {

	List<IBusinessObject> registrosOriginais = new ArrayList<IBusinessObject>();
	List<IBusinessObject> registrosValidos = new ArrayList<IBusinessObject>();
	List<IBusinessObject> registrosInvalidos = new ArrayList<IBusinessObject>();
	
	List<LinhaResultadoValidacao> linhasRegistrosInvalidos = new ArrayList<LinhaResultadoValidacao>();
	List<LinhaResultadoValidacao> linhasRegistrosValidos = new ArrayList<LinhaResultadoValidacao>();
	List<LinhaResultadoValidacao> linhasRegistrosOriginais = new ArrayList<LinhaResultadoValidacao>();
	
	/**
	 * 
	 */
	public ResultadoValidacaoBean(){
		super();
	}
	
	/**
	 * @param registrosValidos
	 * @param registrosInvalidos
	 */
	public ResultadoValidacaoBean(List<IBusinessObject> registrosOriginais, List<IBusinessObject> registrosValidos,
			List<IBusinessObject> registrosInvalidos, List<LinhaResultadoValidacao> linhasRegistrosOriginais,
			List<LinhaResultadoValidacao> linhasRegistrosInvalidos, List<LinhaResultadoValidacao> linhasRegistrosValidos) {
		super();
		this.registrosOriginais = registrosOriginais;
		this.registrosValidos = registrosValidos;
		this.registrosInvalidos = registrosInvalidos;
		this.linhasRegistrosOriginais = linhasRegistrosOriginais;
		this.linhasRegistrosValidos = linhasRegistrosValidos;
		this.linhasRegistrosInvalidos = linhasRegistrosInvalidos;
	}

	public List<IBusinessObject> getRegistrosOriginais() {
		return registrosOriginais;
	}

	public void setRegistrosOriginais(List<IBusinessObject> registrosOriginais) {
		this.registrosOriginais = registrosOriginais;
	}

	public List<IBusinessObject> getRegistrosValidos() {
		return registrosValidos;
	}

	public void setRegistrosValidos(List<IBusinessObject> registrosValidos) {
		this.registrosValidos = registrosValidos;
	}

	public List<LinhaResultadoValidacao> getLinhasRegistrosInvalidos() {
		return linhasRegistrosInvalidos;
	}

	public void setLinhasRegistrosInvalidos(
			List<LinhaResultadoValidacao> linhasRegistrosInvalidos) {
		this.linhasRegistrosInvalidos = linhasRegistrosInvalidos;
	}

	public List<LinhaResultadoValidacao> getLinhasRegistrosValidos() {
		return linhasRegistrosValidos;
	}

	public void setLinhasRegistrosValidos(
			List<LinhaResultadoValidacao> linhasRegistrosValidos) {
		this.linhasRegistrosValidos = linhasRegistrosValidos;
	}


	public List<LinhaResultadoValidacao> getLinhasRegistrosOriginais() {
		return linhasRegistrosOriginais;
	}

	public void setLinhasRegistrosOriginais(
			List<LinhaResultadoValidacao> linhasRegistrosOriginais) {
		this.linhasRegistrosOriginais = linhasRegistrosOriginais;
	}

	public List<IBusinessObject> getRegistrosInvalidos() {
		return registrosInvalidos;
	}

	public void setRegistrosInvalidos(List<IBusinessObject> registrosInvalidos) {
		this.registrosInvalidos = registrosInvalidos;
	}
	
	
	
	
}
