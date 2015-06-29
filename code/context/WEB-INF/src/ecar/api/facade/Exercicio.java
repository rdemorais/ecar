package ecar.api.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import ecar.dao.ExercicioDao;
import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstruturaIett;

public class Exercicio implements EcarWrapperInterface<ExercicioExe>, Comparable<Exercicio>{

	
	ExercicioDao exercicioDao = new ExercicioDao(null);
	ExercicioExe exercicio = null; 

	public Exercicio(long codExe) throws ECARException{
		exercicio = (ExercicioExe) exercicioDao.buscar(ExercicioExe.class, codExe);
		if(exercicio == null){
			throw new ECARException();
		}
	}
	
	public Exercicio(ExercicioExe exe){
		this.exercicio = exe;
	}
	
	
	public long getId(){
		return exercicio.getCodExe();
	}
	
	public String getDescricao(){
		return exercicio.getDescricaoExe();
	}

//	public String getAnoReferencia(){
//		return exercicio.getDescricaoExe();
//	}
	
	
	public Date getDataInicial(){
		return exercicio.getDataInicialExe();
	}
	

	public Date getDataFinal(){
		return exercicio.getDataFinalExe();
	}


	public boolean equals(Exercicio e){
		if(e.getRealObject().equals(exercicio)){
			return true;
		}
		return false;
	}
	
	

	/**
	 * Retorna a lista de períodos de acompanhamento do exercício
	 * @see AcompReferenciaAref
	 * 
	 * @return
	 */
	public List<PeriodoAcompanhamento> getPeriodosAcompanhamento(){
		
		List<PeriodoAcompanhamento> acomps = null; 
		
		Set res = exercicio.getAcompReferenciaArefs();
		if(res != null && res.size() > 0){
			acomps = new ArrayList<PeriodoAcompanhamento>();
			for(Object aref: res){
				acomps.add(new PeriodoAcompanhamento((AcompReferenciaAref)aref));
			}
		}
		
		return acomps;
	}
	
	
	public ExercicioExe getRealObject(){
		return exercicio;
	}

	public int compareTo(Exercicio exe) {

		final int BEFORE = -1;
	    final int EQUAL = 0;
	    final int AFTER = 1;

	    if ( this.exercicio == exe.getRealObject() || this.equals(exe) ) return EQUAL;
	    
	    if(Long.valueOf(exe.getDescricao()) == Long.valueOf(this.getDescricao())){
	    	return EQUAL;
	    }else if(Long.valueOf(exe.getDescricao()) < Long.valueOf(this.getDescricao())){
	    	return AFTER;
	    }else{
	    	return BEFORE;
	    }
	}
	
	
	/**
	 * Retorna os meses dentro do exercício
	 * 
	 * @return
	 */
	public List<EcarData> getMeses(){
		List meses = new ArrayList<Date>();
		List mesesExe = exercicioDao.getMesesDentroDoExercicio(getRealObject());
		
		if(mesesExe != null && mesesExe.size() > 0){
			for(Object mesAno: mesesExe){
    			String mes = mesAno.toString().split("-")[0];
    			String ano = mesAno.toString().split("-")[1];
    			meses.add(new EcarData(mes, ano));
			}
		}
		
		return meses;
	}
	
	
	/**
	 * 
	 * Retorna uma lista de meses dentro de um exercício até o mes e ano
	 * passados como argumentos.
	 * 
	 * @param mes
	 * @param ano
	 * @return
	 */
	public List<EcarData> getMeses(int mes, int ano){
		List meses = new ArrayList<Date>();
		List mesesExe = exercicioDao.getMesesDentroDoExercicio(getRealObject(), mes, ano);
		
		if(mesesExe != null && mesesExe.size() > 0){
			for(Object mesAno: mesesExe){
    			String _mes = mesAno.toString().split("-")[0];
    			String _ano = mesAno.toString().split("-")[1];
    			meses.add(new EcarData(_mes, _ano));
			}
		}
		
		return meses;
	
	}
	
	
	/**
	 * Retorna TRUE se podemos alterar as datas limites do exercício
	 * 
	 * @param novaDataInicial
	 * @param novaDataFinal
	 * @return
	 * @throws ECARException 
	 */
	public boolean podeAlterarDatasLimites(String novaDataInicial, String novaDataFinal) throws ECARException{
		ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
		List<ItemEstruturaIett> itens = itemDao.getListaDeItens();

		if(itens != null && itens.size() > 0){
			for(ItemEstruturaIett item: itens){
				ItemEstrutura iw = new ItemEstrutura(item);
				if(iw.getExercicios().contains(this)){
					if(iw.podeAlterarData(novaDataInicial, novaDataFinal) == false){
						setErrorKey(iw.getErrorKey());
						return false;
					}
				}
			}
		}
	
		return true;
	}
	
	private String errorKey = null;
	
	private void setErrorKey(String key){
		this.errorKey = key;
	}
	
	public String getErrorKey(){
		return this.errorKey;
	}
	
	
	public boolean equals(Object o){
	    if(o instanceof Exercicio){
	    	Exercicio objeto = (Exercicio)o;
	    	if(objeto.getId() == this.getId()){
	    		return true;
	    	}
	    }
	    return false;
	}
	

	
}
