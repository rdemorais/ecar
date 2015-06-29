package ecar.api.facade;

import java.util.Set;

import ecar.dao.EstruturaFuncaoDao;
import ecar.exception.ECARException;
import ecar.pojo.AtributosAtb;
import ecar.pojo.EstruturaAtributoEttat;
import ecar.pojo.EstruturaFuncaoEttf;

/**
 * Wrapper class para facilitar o uso de Funções. Funções são compostas por
 * atributos (@see {@link AtributosAtb}) e aparecem como abas na tela de 
 * cadastro, como por exemplo "Dados Gerais". As funções estão ligadas
 * a Estruturas.
 * 
 * 
 * @author 82035644020
 *
 */
public class Funcao implements EcarWrapperInterface<EstruturaFuncaoEttf> {

	private EstruturaFuncaoEttf funcao;
	private EstruturaFuncaoDao dao = new EstruturaFuncaoDao(null);
	
	
	public Funcao(EstruturaFuncaoEttf funcao){
		this.funcao = funcao;
	}
	
	public String getNome(){
		return funcao.getFuncaoFun().getNomeFun();
	}
	
	public long getId(){
		return funcao.getComp_id().getCodFun();
	}
	
	public String getLabel(){
		return funcao.getLabelEttf();
	}

	public String getLabelPadrao(){
		return funcao.getFuncaoFun().getLabelPadraoFun();
	}
	
	
	public Estrutura getEstrutura() throws ECARException{
		return new Estrutura(funcao.getComp_id().getCodEtt());
	}
	
	public boolean podeSerBloqueada(){
		return funcao.getIndPodeBloquearEttf().equals("S");
	}
	
	public boolean equals(Funcao funcao){
		return this.funcao.equals(funcao.getRealObject());
	}
	
	public EstruturaFuncaoEttf getRealObject() {
		return funcao;
	}

	/**
	 * @return true se possui algum atributo bloqueado
	 */
	public boolean hasAtributosBloqueados(){
		
		Set<AtributosAtb> atributos = funcao.getFuncaoFun().getAtributosAtbs();
		if(atributos != null){
			for(AtributosAtb atributo: atributos){
				if(atributo.getEstruturaAtributoEttats() != null && atributo.getEstruturaAtributoEttats().size() > 0){
					for(Object estAtrib: atributo.getEstruturaAtributoEttats()){
						EstruturaAtributoEttat a = (EstruturaAtributoEttat) estAtrib;
						if(a.getComp_id().getCodEtt().equals(funcao.getComp_id().getCodEtt())){
							if(a.iGetBloqueado() == true){
								return true;
							}
						}
					}
				}
			}
		}
		
		
		return false;
	}
	
	
	
}