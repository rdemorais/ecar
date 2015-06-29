package ecar.api.facade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import comum.util.Util;
import ecar.dao.AcompRealFisicoDao;
import ecar.dao.ExercicioDao;
import ecar.exception.ECARException;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.ExercicioExe;

public class Realizado implements EcarWrapperInterface<AcompRealFisicoArf>, Serializable{

	
	private static final long serialVersionUID = -6203166479049819117L;
	private AcompRealFisicoArf realizado = null;
	
	
	public Realizado(AcompRealFisicoArf realizado){
		this.realizado = realizado;
	}
	
		
	
	public AcompRealFisicoArf getRealObject(){
		return this.realizado;
	}
	
	
	public long getMes(){
		return this.realizado.getMesArf();
	}
	
	public long getAno(){
		return this.realizado.getAnoArf();
	}
	
	/**
	 * 
	 * Retorna a lista de exerc�cios do acompanhamento f�sico.
	 * 
	 * @return
	 * @throws ECARException
	 */
	public List<Exercicio> getExercicios() throws ECARException{
		List<Exercicio> exercicios = new ArrayList<Exercicio>();
		ExercicioDao exercicioDao = new ExercicioDao(null);
		List<ExercicioExe> listaExeAnt =  exercicioDao.getExerciciosProjecaoResultados(realizado.getItemEstruturaIett().getCodIett());
		
		if(listaExeAnt != null && listaExeAnt.size() > 0){
			for(ExercicioExe ecarExe: listaExeAnt){
				exercicios.add(new Exercicio(ecarExe.getCodExe()));
			}
		}
		
		return exercicios;
	}
	
	
	/**
	 * Retorna a lista de exericios at� o ano referencia.
	 * 
	 * @param anoRef
	 * @return
	 * @throws ECARException
	 */
	public List<Exercicio> getExercicios(String anoRef) throws ECARException{
		List<Exercicio> l = new ArrayList<Exercicio>();
		List<Exercicio> exercicios = getExercicios();
		
		if(exercicios.size() > 0){
			for(Exercicio exe: exercicios){
				if(!(Long.valueOf(exe.getDescricao())>Long.valueOf(anoRef))){
					l.add(exe);
				}
			}
		}	
		
		return l;
	}

	/**
	 * 
	 * Retorna o valor realizado em um exercicio dado o mes e ano de referencia
	 * 
	 * @param exe
	 * @param anoRef
	 * @param mesRef
	 * @return
	 * @throws NumberFormatException
	 * @throws ECARException
	 */

	public double getRealizado(Exercicio exe, String anoRef, String mesRef, boolean acumulado) throws NumberFormatException, ECARException{
		AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(null);
		
		double res = 0;
		
		if(acumulado == true){
			res = acompRealFisicoDao.getQtdIndicadorGrupoRealizadaExercicioNaoAcumulavel(realizado.getItemEstrtIndResulIettr(),
					exe.getRealObject(),					
					Long.valueOf(anoRef), 
					Long.valueOf(mesRef));

		}else{
			res = acompRealFisicoDao.getQtdIndicadorGrupoRealizadaExercicio(realizado.getItemEstrtIndResulIettr(), 
					exe.getRealObject(), 
					Long.valueOf(anoRef), 
					Long.valueOf(mesRef));
		}
		
		return res;
	}
	
	
	public Double getRealizado(){
		if(realizado.getQtdRealizadaArf() == null){
			return new Double(0);
		}
		return realizado.getQtdRealizadaArf();
	}

	/**
	 * Retorna o valor formatado de acordo com o tipo do valor.
	 * @return
	 */
	public String getRealizadoFormatado(){

		String res = "";
		//se for  moeda
		if(realizado.getItemEstrtIndResulIettr().getIndTipoQtde().equals("V")){
			//por no formato de moeda
			res  = Util.formataMoedaPT_BR(getRealizado());
		}else{
			res = Util.formataNumeroDecimal(getRealizado());
		}
		
		return res;
	}
	
	
	public String toString(){
		return getMes() + "/" + getAno() + " Valor realizado: " + getRealizadoFormatado() + "\n";
	}
	
	public EcarData getData(){
		int mes = getRealObject().getMesArf().intValue();
		int ano = getRealObject().getAnoArf().intValue();
		
		return new EcarData(mes, ano);
	}
	
	/**
	 * 
	 * @return TRUE se o realizado est� em uma situa��o que representa conclus�o
	 */
	public boolean isConcluido(){
		
		if(this.realizado.getSituacaoSit() == null){
			return false;
		}else{
			return this.realizado.getSituacaoSit().getIndConcluidoSit().equals("S");
		}
		
	}
	
	/**
	 * Retorna a string representando a situa��o 
	 * @return
	 */
	public String getStringSituacao(){
		return this.realizado.getSituacaoSit().getDescricaoSit();
	}

	/**
	 * Retorna o valor formatado de acordo com o tipo do valor.
	 * @return
	 */
	public String getValorFormatado(){
		Double realizado = this.realizado.getQtdRealizadaArf(); 
		String sRealizado = null;
			if(this.realizado.getItemEstrtIndResulIettr().getIndTipoQtde().equals("Q")){
				sRealizado=Util.formataNumeroDecimal((Double)realizado);
			}else{
				sRealizado=Util.formataMoeda((Double)realizado);
			}
			
		return sRealizado;	
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((realizado == null) ? 0 : realizado.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Realizado)) {
			return false;
		}
		Realizado other = (Realizado) obj;
		if (realizado == null) {
			if (other.realizado != null) {
				return false;
			}
		} else if (!realizado.equals(other.realizado)) {
			return false;
		}
		return true;
	}
	
	
}
