package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author 70744416353
 */
public class PesquisaGrpAcesso implements Serializable {
	
	private static final long serialVersionUID = -5549149190114022412L;
	
	//Código do grupo de Acesso
	private Long codSatb;
    private String indPodeCriarPesquisaUsuario;
    private String indPodeCriarPesquisaSistema;
    private String indPodeVerGeral; 
    private String indPodeVerMinhaVisao; 
    private String indPodeVerPendencias;
    private String indPodeVerPersonalizado; 

    
    //Pesquisas que o Grupo pode visualizar
    private Set<Pesquisa> pesquisas; 
 

    /**
     *
     * @return
     */
    public String getIndPodeVerGeral() {
		return indPodeVerGeral;
	}

    /**
     *
     * @param indPodeVerGeral
     */
    public void setIndPodeVerGeral(String indPodeVerGeral) {
		this.indPodeVerGeral = indPodeVerGeral;
	}

    /**
     *
     * @return
     */
    public String getIndPodeVerMinhaVisao() {
		return indPodeVerMinhaVisao;
	}

        /**
         *
         * @param indPodeVerMinhaVisao
         */
        public void setIndPodeVerMinhaVisao(String indPodeVerMinhaVisao) {
		this.indPodeVerMinhaVisao = indPodeVerMinhaVisao;
	}

        /**
         *
         * @return
         */
        public String getIndPodeVerPendencias() {
		return indPodeVerPendencias;
	}

        /**
         *
         * @param indPodeVerPendencias
         */
        public void setIndPodeVerPendencias(String indPodeVerPendencias) {
		this.indPodeVerPendencias = indPodeVerPendencias;
	}

        /**
         *
         * @return
         */
        public String getIndPodeVerPersonalizado() {
		return indPodeVerPersonalizado;
	}

        /**
         *
         * @param indPodeVerPersonalizado
         */
        public void setIndPodeVerPersonalizado(String indPodeVerPersonalizado) {
		this.indPodeVerPersonalizado = indPodeVerPersonalizado;
	}

        /**
         *
         */
        public PesquisaGrpAcesso() {
    }

        /**
         *
         * @return
         */
        public Long getCodSatb() {
		return codSatb;
	}

        /**
         *
         * @param codSatb
         */
        public void setCodSatb(Long codSatb) {
		this.codSatb = codSatb;
	}

        /**
         *
         * @return
         */
        public String getIndPodeCriarPesquisaUsuario() {
		return indPodeCriarPesquisaUsuario;
	}

        /**
         *
         * @param indPodeCriarPesquisaUsuario
         */
        public void setIndPodeCriarPesquisaUsuario(String indPodeCriarPesquisaUsuario) {
		this.indPodeCriarPesquisaUsuario = indPodeCriarPesquisaUsuario;
	}

        /**
         *
         * @return
         */
        public String getIndPodeCriarPesquisaSistema() {
		return indPodeCriarPesquisaSistema;
	}

        /**
         *
         * @param indPodeCriarPesquisaSistema
         */
        public void setIndPodeCriarPesquisaSistema(String indPodeCriarPesquisaSistema) {
		this.indPodeCriarPesquisaSistema = indPodeCriarPesquisaSistema;
	}

        /**
         *
         * @return
         */
        public Set<Pesquisa> getPesquisas() {
		return pesquisas;
	}

        /**
         *
         * @param pesquisas
         */
        public void setPesquisas(Set<Pesquisa> pesquisas) {
		this.pesquisas = pesquisas;
	}

    
}
