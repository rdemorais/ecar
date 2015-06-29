package ecar.pojo;

/**
 *
 * @author 70744416353
 */
public class Pessoa {

	private String nome;

	private String endereco;

	private String numeroPreferido;

	// getters and setters

	
	
	@Override
	public String toString() {
		return "[Pessoa " + nome + "," + endereco + "," + numeroPreferido + "]";
	}

        /**
         *
         * @return
         */
        public String getEndereco() {
		return endereco;
	}

        /**
         *
         * @param endereco
         */
        public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

        /**
         *
         * @return
         */
        public String getNome() {
		return nome;
	}

        /**
         *
         * @param nome
         */
        public void setNome(String nome) {
		this.nome = nome;
	}

        /**
         *
         * @return
         */
        public String getNumeroPreferido() {
		return numeroPreferido;
	}

        /**
         *
         * @param numeroPreferido
         */
        public void setNumeroPreferido(String numeroPreferido) {
		this.numeroPreferido = numeroPreferido;
	}

}
