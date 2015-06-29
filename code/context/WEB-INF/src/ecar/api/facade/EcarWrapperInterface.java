package ecar.api.facade;

/**
 * Interface das classes wrapper que servirão para tentar melhorar o acesso aos dados do banco de dados
 * utilizando os conceitos de orientação a objetos 
 * 
 * Muito do código dos jsp estão replicados em vários outros  * causando transtornos quando se vai 
 * dar manutenção em um e não se sabe que o mesmo trecho está repetido em outros lugares. O objetivo
 * de se fazer classes wrapper é contornar essas dificuldade para facilitar  * as manutenções e tirar 
 * um pouco do código java repetido em jsp.
 *  
 *  
 * @author 82035644020
 *
 * @param <T>
 */
public interface EcarWrapperInterface <T> {

	
	/**
	 * Retorna o objeto real do ecar que a classe está
	 * encapsulando
	 * 
	 * @return
	 */
	public T getRealObject();
	
	
}
