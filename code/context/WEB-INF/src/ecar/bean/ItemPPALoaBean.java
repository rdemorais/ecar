package ecar.bean;


/**
 * Bean contendo as informações para listagem do método listarItensPPALoaREVISAO de tempAtualizacaoBDDao.<br>
 * 
 * @author aleixo
 */

public class ItemPPALoaBean {
	
	private static final String ESPACO = " ";
	
	private String siglaPrograma;
	private String siglaAcao;
	private String nomeAcao;
	private String siglaProduto;
	private String nomeProduto;
	private String siglaOrgao;
	private String nomeOrgao;
	private String abrangencia;
	private String local;
	private String tipoMetaFisica;
	private String unidadeMetaFisica;
	private String qtde2007;
	
        /**
         *
         */
        public ItemPPALoaBean(){
		this.siglaPrograma = ESPACO;
		this.siglaAcao = ESPACO;
		this.nomeAcao = ESPACO;
		this.siglaProduto = ESPACO;
		this.nomeProduto = ESPACO;
		this.siglaOrgao = ESPACO;
		this.nomeOrgao = ESPACO;
		this.abrangencia = ESPACO;
		this.local = ESPACO;
		this.tipoMetaFisica = ESPACO;
		this.unidadeMetaFisica = ESPACO;
		this.qtde2007 = ESPACO;
	}
	
        /**
         *
         * @return
         */
        public String gerarSaidaCSV(){
		return getSiglaPrograma() + ";" + 
				getSiglaAcao() + ";" + 
				getNomeAcao() + ";" + 
				getSiglaProduto() + ";" + 
				getNomeProduto() + ";" + 
				getSiglaOrgao() + "(" + getNomeOrgao() + ");" + 
				getAbrangencia() + ";" + 
				getLocal() + ";" + 
				getTipoMetaFisica() + ";" + 
				getUnidadeMetaFisica() + ";" + 
				getQtde2007() + ";";
	}
	
        /**
         *
         * @return
         */
        public String getAbrangencia() {
		return abrangencia;
	}
        /**
         *
         * @param abrangencia
         */
        public void setAbrangencia(String abrangencia) {
		this.abrangencia = abrangencia;
	}
        /**
         *
         * @return
         */
        public String getLocal() {
		return local;
	}
        /**
         *
         * @param local
         */
        public void setLocal(String local) {
		this.local = local;
	}
        /**
         *
         * @return
         */
        public String getNomeAcao() {
		return nomeAcao;
	}
        /**
         *
         * @param nomeAcao
         */
        public void setNomeAcao(String nomeAcao) {
		this.nomeAcao = nomeAcao;
	}
        /**
         *
         * @return
         */
        public String getNomeOrgao() {
		return nomeOrgao;
	}
        /**
         *
         * @param nomeOrgao
         */
        public void setNomeOrgao(String nomeOrgao) {
		this.nomeOrgao = nomeOrgao;
	}
        /**
         *
         * @return
         */
        public String getNomeProduto() {
		return nomeProduto;
	}
        /**
         *
         * @param nomeProduto
         */
        public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
        /**
         *
         * @return
         */
        public String getSiglaPrograma() {
		return siglaPrograma;
	}
        /**
         *
         * @param siglaPrograma
         */
        public void setSiglaPrograma(String siglaPrograma) {
		this.siglaPrograma = siglaPrograma;
	}
        /**
         *
         * @return
         */
        public String getQtde2007() {
		return qtde2007;
	}
        /**
         *
         * @param qtde2007
         */
        public void setQtde2007(String qtde2007) {
		this.qtde2007 = qtde2007;
	}
        /**
         *
         * @return
         */
        public String getSiglaAcao() {
		return siglaAcao;
	}
        /**
         *
         * @param siglaAcao
         */
        public void setSiglaAcao(String siglaAcao) {
		this.siglaAcao = siglaAcao;
	}
        /**
         *
         * @return
         */
        public String getSiglaOrgao() {
		return siglaOrgao;
	}
        /**
         *
         * @param siglaOrgao
         */
        public void setSiglaOrgao(String siglaOrgao) {
		this.siglaOrgao = siglaOrgao;
	}
        /**
         *
         * @return
         */
        public String getSiglaProduto() {
		return siglaProduto;
	}
        /**
         *
         * @param siglaProduto
         */
        public void setSiglaProduto(String siglaProduto) {
		this.siglaProduto = siglaProduto;
	}
        /**
         *
         * @return
         */
        public String getTipoMetaFisica() {
		return tipoMetaFisica;
	}
        /**
         *
         * @param tipoMetaFisica
         */
        public void setTipoMetaFisica(String tipoMetaFisica) {
		this.tipoMetaFisica = tipoMetaFisica;
	}
        /**
         *
         * @return
         */
        public String getUnidadeMetaFisica() {
		return unidadeMetaFisica;
	}
        /**
         *
         * @param unidadeMetaFisica
         */
        public void setUnidadeMetaFisica(String unidadeMetaFisica) {
		this.unidadeMetaFisica = unidadeMetaFisica;
	}
	
}
