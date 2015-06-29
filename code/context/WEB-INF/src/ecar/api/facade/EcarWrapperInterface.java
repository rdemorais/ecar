package ecar.api.facade;

/**
 * Interface das classes wrapper que servir�o para tentar melhorar o acesso aos dados do banco de dados
 * utilizando os conceitos de orienta��o a objetos 
 * 
 * Muito do c�digo dos jsp est�o replicados em v�rios outros  * causando transtornos quando se vai 
 * dar manuten��o em um e n�o se sabe que o mesmo trecho est� repetido em outros lugares. O objetivo
 * de se fazer classes wrapper � contornar essas dificuldade para facilitar  * as manuten��es e tirar 
 * um pouco do c�digo java repetido em jsp.
 *  
 *  
 * @author 82035644020
 *
 * @param <T>
 */
public interface EcarWrapperInterface <T> {

	
	/**
	 * Retorna o objeto real do ecar que a classe est�
	 * encapsulando
	 * 
	 * @return
	 */
	public T getRealObject();
	
	
}
