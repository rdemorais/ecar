package ecar.api.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ecar.dao.EstruturaDao;
import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.pojo.EstrutTpFuncAcmpEtttfa;
import ecar.pojo.EstruturaAtributoEttat;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ObjetoEstrutura;

/**
 * Classe wrapper para {@link EstruturaEtt}.
 * 
 *   @author 82035644020
 *
 */
public class Estrutura implements EcarWrapperInterface<EstruturaEtt>{


	private EstruturaDao estruturaDao = null;
	private ItemEstruturaDao itemDao = null;
	private EstruturaEtt estrutura = null;
	private List<ItemEstruturaIett> itensDaEstrutura = null;
	private Map<String, String> labelAtributos = null;
	
	public Estrutura(long id)  throws ECARException{
		estruturaDao = new EstruturaDao(null);
		estrutura = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, id);

		if(estrutura == null){
			System.out.println("Estrutura não encontrada");
			throw new ECARException();
		}
		
		itemDao = new ItemEstruturaDao(null);
		itensDaEstrutura = itemDao.getItensByEstrutura(estrutura.getCodEtt());
	}
	
	
	public Estrutura(EstruturaEtt estrutura)  throws ECARException{
		this.estrutura = estrutura;
		estruturaDao = new EstruturaDao(null);
	}
	
	
	public Estrutura(String nomeEstrutura) throws ECARException{
		estruturaDao = new EstruturaDao(null);
		estrutura = estruturaDao.getEstruturaEttByNome(nomeEstrutura);

		if(estrutura == null){
			System.out.println("Estrutura não encontrada");
			throw new ECARException();
		}
		
		itemDao = new ItemEstruturaDao(null);
		itensDaEstrutura = itemDao.getItensByEstrutura(estrutura.getCodEtt());
	}
	

	/**
	 * Returna a estrutura correspondende ao nome
	 * 
	 * @return
	 */
	public EstruturaEtt getEstrutura(){
		return estrutura;
	}

	
	public Long getId(){
		return this.getRealObject().getCodEtt();
	}
	
	
	public String getNome(){
		return estrutura.getNomeEtt();
	}
	
	//@TODO: ainda falta colocar as permissões de acordo com o usuário
	public List<ItemEstruturaIett> getItensDaEstrutura() throws ECARException{
		return itensDaEstrutura;
	}

	/**
	 * Retorna a quantidade de itens relacionados a estrutura
	 * @return
	 */
	public Long numeroItensEstruturaIett() {
		return estruturaDao.numeroItensEstruturaIett(getEstrutura());
	}
	
	public ItemEstruturaIett getItemDaEstruturaPorNome(String nome) {
		if(itensDaEstrutura != null){
			for(ItemEstruturaIett item: itensDaEstrutura){
				if(item.getNomeIett().equals(nome)){
					return item;
				}
			}
		}
		return null;
	}
	

	public ItemEstruturaIett getItemDaEstruturaPorNome(long id) {
		if(itensDaEstrutura != null){
			for(ItemEstruturaIett item: itensDaEstrutura){
				if(item.getCodIett() == id){
					return item;
				}
			}
		}
		return null;
	}

	
	public List<EstruturaAtributoEttat> getAtributos() throws ECARException{
	
		List<EstruturaAtributoEttat> atributos = new ArrayList<EstruturaAtributoEttat>();
		List<EstruturaAtributoEttat> attrs = estruturaDao.getAtributosEstruturaDadosGerais(estrutura);
		
		if(attrs != null && attrs.size() > 0){
			for(Object attr: attrs){
				if(attr instanceof EstruturaAtributoEttat){
					atributos.add((EstruturaAtributoEttat) attr);
				}
			}
		}
		return atributos;
		
	}
	
	public List<EstrutTpFuncAcmpEtttfa> getFuncoesAcompanhamento() throws ECARException{
		
		List<EstrutTpFuncAcmpEtttfa> funcoes = new ArrayList<EstrutTpFuncAcmpEtttfa>();
		List<EstrutTpFuncAcmpEtttfa> attrs = estruturaDao.getAtributosEstruturaDadosGerais(estrutura);
		
		if(attrs != null && attrs.size() > 0){
			for(Object attr: attrs){
				if(attr instanceof EstrutTpFuncAcmpEtttfa){
					funcoes.add((EstrutTpFuncAcmpEtttfa) attr);
				}
			}
		}
		return funcoes;
	}


	public EstruturaEtt getRealObject() {
		return this.estrutura;
	}
	
    /**
     * Devolve uma lista de Atributos e Funcoes de Acompanhamento da Estrutura.
     * Essa lista contém objetos estruturaAtributo e estrutTipoFuncAcmp ordenados pela ordem do campo na tela.
     * O acesso aos objetos dessa lista se dá pela interface ObjetoEstrutura que normaliza os métodos de acesso
     * @return lista de objetos estruturaAtributo e estrutTipoFuncAcmp ordenados pela ordem do campo na tela.
     * @throws ECARException
     */
	public List<ObjetoEstrutura> getAtributosEstruturaDadosGerais() throws ECARException{
		EstruturaDao estDao = new EstruturaDao(null);
		return estDao.getAtributosEstruturaDadosGerais(this.getRealObject());
	}

	/**
	 * Retorna o label usado na tela pra um determinado atributo:
	 * ex: dataTerminoIett, pode ser Data Término, Data Final, Final Date, etc..
	 * 
	 * Inclui os dados gerais e as funções
	 * 
	 * depende de como foi configurado na estrutura.
	 * 
	 * @return
	 * @throws ECARException 
	 */
	public String getLabelAtributo(String atributo) throws ECARException{
	
		if(labelAtributos == null){//cria o hash se necessário
			List<ObjetoEstrutura> attrs = this.getAtributosEstruturaDadosGerais();
			
			labelAtributos = new HashMap<String, String>();
			
			for(ObjetoEstrutura obj: attrs){
				labelAtributos.put(obj.iGetNome(), obj.iGetLabel());
			}
		}
		
		return labelAtributos.get(atributo);
	}
	
}
