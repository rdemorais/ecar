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
		
			if (lista.contains(_objeto.iGetPai())) { // Quando o pai do elemento está na lista, executa o código abaixo.
				if (_objeto.iGetPai() == null) { //Quando a estrutura não possui pai ela é raiz deve ser inserida na lista
					NoArvoreEstrutura noArvore = new NoArvoreEstrutura();
					noArvore.setElemento(_objeto);
					noArvore.setListaFilhos(new ArrayList<NoArvoreEstrutura>());
					listaMontada.add(noArvore);
				} else {
					filhos.add(_objeto);
				}
			} else {//Quando o pai do elemento não está na lista, não precisa procurar basta criar o nó e inserir na listaMontada. Quando o pai não estiver na lista é porque não foi selecionado pelo usuário.
				NoArvoreEstrutura noArvore = new NoArvoreEstrutura();
				noArvore.setElemento(_objeto);
				noArvore.setListaFilhos(new ArrayList<NoArvoreEstrutura>());
				listaMontada.add(noArvore);
			}
		}
		
		if (!filhos.isEmpty()){ //lista de Filhos não é vazia 
			montarNivelFilho (filhos);
		}
		
		
		return limparListaMontada(lista); 
	}
	

	
	/**
	 * Monta o segundo nível da árvore, o nível dos filhos. 
	 * 
	 * @param filhos
	 */
	private void montarNivelFilho(List<Hierarchyable> filhos) {
		
		List netos = new ArrayList<Hierarchyable>();
		boolean encontrouPai;
		
		for (Hierarchyable _objeto : filhos) {
			encontrouPai = false;
			for (NoArvoreEstrutura no : listaMontada) {
				//if (no.getElemento().equals(_objeto.iGetPai())) {//verifica se o nó da lista é igual ao nó pai da estrutura, caso seja, o pai da estrutura foi encontrado.
				if (_objeto.iGetPai().equals(no.getElemento())) {//verifica se o nó da lista é igual ao nó pai da estrutura, caso seja, o pai da estrutura foi encontrado.
					NoArvoreEstrutura noArvore = new NoArvoreEstrutura();
					noArvore.setElemento(_objeto);
					noArvore.setListaFilhos(new ArrayList<NoArvoreEstrutura>());
					no.getListaFilhos().add(noArvore); //Adiciona o novo nó criado a lista de filhos do pai
					listaMontada.add(noArvore); //Adiciona o filho na lista geral, isso facilita muito a busca.
					encontrouPai = true;
					break;
				}
			}
			
			if (!encontrouPai) {
				netos.add(_objeto);
			}
		}
		
		if (!netos.isEmpty()){ //lista de Netos não é vazia 
			montarNivelSeguinte (netos);
		}
		
	}

	
	/**
	 * Monta do terceiro nível (netos) em diante e completa toda a árvore 	
	 * @param netos
	 */
	private void montarNivelSeguinte(List<EstruturaEtt> netos) {

		List restantes = new ArrayList<EstruturaEtt>();
		boolean encontrouPai;
		
		for (Hierarchyable _objeto : netos) {
			encontrouPai = false;
			for (NoArvoreEstrutura no : listaMontada) {
				//if (no.getElemento().equals(_objeto.iGetPai())) {//verifica se o nó da lista é igual ao nó pai da estrutura, caso seja, o pai da estrutura foi encontrado.
				if (_objeto.iGetPai().equals(no.getElemento())) {//verifica se o nó da lista é igual ao nó pai da estrutura, caso seja, o pai da estrutura foi encontrado.
					NoArvoreEstrutura noArvore = new NoArvoreEstrutura();
					noArvore.setElemento(_objeto);
					noArvore.setListaFilhos(new ArrayList<NoArvoreEstrutura>());
					no.getListaFilhos().add(noArvore); //Adiciona o novo nó criado a lista de filhos do pai
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
	 * Criará uma lista apenas com os nós Raiz
	 * 
	 * Apartir dos nós Raiz todos os nós de nível mais baixo serão acessíveis   
	 * @return
	 */
	private List limparListaMontada(List lista) {
		
		List listaLimpa = new ArrayList<NoArvoreEstrutura>();
		
		for (NoArvoreEstrutura noArvore : listaMontada) {
			
			if (noArvore.getElemento().iGetPai() == null){ //Se a estrutura for raiz será inserida na lista 
				listaLimpa.add (noArvore);
			} else if (noArvore.getElemento() instanceof ItemEstruturaIett && !lista.contains(noArvore.getElemento().iGetPai())) { //ou se o pai do item não estiver na lista original, portanto ele tambem deverá ser inserido na lista.
				listaLimpa.add (noArvore);
			}
			
		}
		
		return listaLimpa;
	}

}
