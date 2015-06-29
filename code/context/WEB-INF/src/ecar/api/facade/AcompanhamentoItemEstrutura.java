package ecar.api.facade;

import java.util.Date;
import java.util.List;

import ecar.dao.AcompReferenciaItemDao;
import ecar.exception.ECARException;
import ecar.pojo.AcompReferenciaItemAri;


/**
 * 
 * Wrapper class para a classe @{link AcompReferenciaItemAri} do ecar.
 * 
 * 
 * @author 82035644020
 *
 */
public class AcompanhamentoItemEstrutura implements EcarWrapperInterface<AcompReferenciaItemAri> {

	private AcompReferenciaItemAri acompanhamento;
	
	public AcompanhamentoItemEstrutura(long codAri) throws ECARException{

		AcompReferenciaItemDao acompRefItemDao = new AcompReferenciaItemDao(null);
		acompanhamento = (AcompReferenciaItemAri) acompRefItemDao.buscar(
					AcompReferenciaItemAri.class, 
					codAri);

		if(acompanhamento == null){
			throw new ECARException();
		}
		
	}
	
	public AcompReferenciaItemAri getRealObject(){
		return acompanhamento;
	}
	
	public AcompanhamentoItemEstrutura(AcompReferenciaItemAri acompanhamento){
		this.acompanhamento = acompanhamento;
	}
	
	
	public long getId(){
		return acompanhamento.getCodAri();
	}
	
	
	public Date getDataInicial(){
		return acompanhamento.getDataInicioAri();
	}
	
	public Date getDataLimite(){
		return acompanhamento.getDataLimiteAcompFisicoAri();
	}

	public Date getDataUltimaManutencao(){
		return acompanhamento.getDataUltManutAri();
	}
	
	
	public EcarData getDataReferencia(){
		return new EcarData(getMesReferencia(), getAnoReferencia());
	}
	
	public String getMesReferencia(){
		return acompanhamento.getAcompReferenciaAref().getMesAref();
	}
	
	public String getAnoReferencia(){
		return acompanhamento.getAcompReferenciaAref().getAnoAref();
	}
	
	/**
	 * Retorna o ID do item de estrutura que o acompanhamento
	 * está associado
	 * 
	 * @return
	 */
	public long getItemEstruturaId(){
		return acompanhamento.getItemEstruturaIett().getCodIett();
	}
	
	/**
	 * Retorna um perído de acompanhamento. Wrapper para 
	 * o objeto @link{AcompReferenciaAref}. 
	 * Um acompanhamento de item de estrutura possui um período, ou 
	 * ainda, um AcompReferenciaItemAri possui um AcompReferenciaAref.
	 * 
	 * @return
	 */
	public PeriodoAcompanhamento getPeriodoAcompanhamento(){
		return new PeriodoAcompanhamento(acompanhamento.getAcompReferenciaAref());
	}
	
	/**
	 * Retorna os exercícios do item de estrutura sendo acompanhado.
	 * 
	 * @return
	 * @throws ECARException
	 */
	public List<Exercicio> getExercicios() throws ECARException{
		return getItemEstrutura().getExercicios();
	}

	
	/**
	 * Retorna verdadeiro se o exercicio existe no acompanhamento
	 * do item de estrutura.
	 * 
	 * @param exe
	 * @return
	 * @throws ECARException 
	 */
	public boolean possuiExercicio(Exercicio exe) throws ECARException{
		List<Exercicio> l = getExercicios();
		if(l.size() > 0){
			for(Exercicio e: l){
				if(exe.getRealObject().equals(e.getRealObject())){
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	public ItemEstrutura getItemEstrutura()  throws ECARException{
		ItemEstrutura itemEstrutura = new ItemEstrutura(getItemEstruturaId());
		return itemEstrutura;
	}
	
}
