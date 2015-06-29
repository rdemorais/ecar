package ecar.action;

import java.util.ArrayList;
import java.util.List;

import ecar.pojo.EstruturaEtt;
import ecar.pojo.Hierarchyable;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.NoArvoreEstrutura;

/**
 *
 * @author 70744416353
 */
public class ActionHierarquia {

	private List<NoArvoreEstrutura> listaMontada;
	
        /**
         *
         * @param lista
         * @return
         */
        public List montarListaEstruturaHierarquizada(List<Hierarchyable> lista) {
		
		listaMontada = new ArrayList<NoArvoreEstrutura>();
		List filhos = new ArrayList<EstruturaEtt>();
		boolean controle = true;
		
		
		for (Hierarchyable _objeto : lista) {
		
			if (lista.contains(_objeto.iGetPai())) { // Quando o pai do elemento est� na lista, executa o c�digo abaixo.
				if (_objeto.iGetPai() == null) { //Quando a estrutura n�o possui pai ela � raiz deve ser inserida na lista
					NoArvoreEstrutura noArvore = new NoArvoreEstrutura();
					noArvore.setElemento(_objeto);
					noArvore.setListaFilhos(new ArrayList<NoArvoreEstrutura>());
					listaMontada.add(noArvore);
				} else {
					filhos.add(_objeto);
				}
			} else {//Quando o pai do elemento n�o est� na lista, n�o precisa procurar basta criar o n� e inserir na listaMontada. Quando o pai n�o estiver na lista � porque n�o foi selecionado pelo usu�rio.
				NoArvoreEstrutura noArvore = new NoArvoreEstrutura();
				noArvore.setElemento(_objeto);
				noArvore.setListaFilhos(new ArrayList<NoArvoreEstrutura>());
				listaMontada.add(noArvore);
			}
		}
		
		if (!filhos.isEmpty()){ //lista de Filhos n�o � vazia 
			montarNivelFilho (filhos);
		}
		
		
		return limparListaMontada(lista); 
	}
	

	
	/**
	 * Monta o segundo n�vel da �rvore, o n�vel dos filhos. 
	 * 
	 * @param filhos
	 */
	private void montarNivelFilho(List<Hierarchyable> filhos) {
		
		List netos = new ArrayList<Hierarchyable>();
		boolean encontrouPai;
		
		for (Hierarchyable _objeto : filhos) {
			encontrouPai = false;
			for (NoArvoreEstrutura no : listaMontada) {
				//if (no.getElemento().equals(_objeto.iGetPai())) {//verifica se o n� da lista � igual ao n� pai da estrutura, caso seja, o pai da estrutura foi encontrado.
				if (_objeto.iGetPai().equals(no.getElemento())) {//verifica se o n� da lista � igual ao n� pai da estrutura, caso seja, o pai da estrutura foi encontrado.
					NoArvoreEstrutura noArvore = new NoArvoreEstrutura();
					noArvore.setElemento(_objeto);
					noArvore.setListaFilhos(new ArrayList<NoArvoreEstrutura>());
					no.getListaFilhos().add(noArvore); //Adiciona o novo n� criado a lista de filhos do pai
					listaMontada.add(noArvore); //Adiciona o filho na lista geral, isso facilita muito a busca.
					encontrouPai = true;
					break;
				}
			}
			
			if (!encontrouPai) {
				netos.add(_objeto);
			}
		}
		
		if (!netos.isEmpty()){ //lista de Netos n�o � vazia 
			montarNivelSeguinte (netos);
		}
		
	}

	
	/**
	 * Monta do terceiro n�vel (netos) em diante e completa toda a �rvore 	
	 * @param netos
	 */
	private void montarNivelSeguinte(List<EstruturaEtt> netos) {

		List restantes = new ArrayList<EstruturaEtt>();
		boolean encontrouPai;
		
		for (Hierarchyable _objeto : netos) {
			encontrouPai = false;
			for (NoArvoreEstrutura no : listaMontada) {
				//if (no.getElemento().equals(_objeto.iGetPai())) {//verifica se o n� da lista � igual ao n� pai da estrutura, caso seja, o pai da estrutura foi encontrado.
				if (_objeto.iGetPai().equals(no.getElemento())) {//verifica se o n� da lista � igual ao n� pai da estrutura, caso seja, o pai da estrutura foi encontrado.
					NoArvoreEstrutura noArvore = new NoArvoreEstrutura();
					noArvore.setElemento(_objeto);
					noArvore.setListaFilhos(new ArrayList<NoArvoreEstrutura>());
					no.getListaFilhos().add(noArvore); //Adiciona o novo n� criado a lista de filhos do pai
					listaMontada.add(noArvore); //Adiciona o filho na lista geral, isso facilita muito a busca.
					encontrouPai = true;
					break;
				}
			}
			
			if (!encontrouPai) {
				restantes.add(_objeto);
			}
		}
		
		if (!restantes.isEmpty()){
			montarNivelSeguinte(restantes);
		}
	}

	
	/**
	 * Criar� uma lista apenas com os n�s Raiz
	 * 
	 * Apartir dos n�s Raiz todos os n�s de n�vel mais baixo ser�o acess�veis   
	 * @return
	 */
	private List limparListaMontada(List lista) {
		
		List listaLimpa = new ArrayList<NoArvoreEstrutura>();
		
		for (NoArvoreEstrutura noArvore : listaMontada) {
			
			if (noArvore.getElemento().iGetPai() == null){ //Se a estrutura for raiz ser� inserida na lista 
				listaLimpa.add (noArvore);
			} else if (noArvore.getElemento() instanceof ItemEstruturaIett && !lista.contains(noArvore.getElemento().iGetPai())) { //ou se o pai do item n�o estiver na lista original, portanto ele tambem dever� ser inserido na lista.
				listaLimpa.add (noArvore);
			}
			
		}
		
		return listaLimpa;
	}

}
