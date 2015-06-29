package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

/** 
 * Classe responsavel para armazenar dados da tabela tb_historico_iettuph
 * - Modificado para comportar historico
 * 
 * @author rogerio, gabriel
 * @since 0.1, 10/05/2007
 * @version 0.2, 28/05/2007
 */
public class HistoricoIettupH implements Serializable {


	private static final long serialVersionUID = 2213422395945407235L;
	
	/** cod_iettuph - Chave Primaria (PK) */
	private Long codIettupH;
		
    /** cod_iettup */
	private Long codIettup;

	/** data_inclusao_iettup */
	private Date dataInclusaoIettup;	
	
	/** ind_ativo_iettup */
	private String indAtivoIettup;
	
	/** nome_original_iettup */
	private String nomeOriginalIettup;
	
	/** tamanho_iettup */
	private Long tamanhoIettup;
	
	/** descricao_iettup */
	private String descricaoIettup;
	
	/** arquivo_iettup */
	private String arquivoIettup;
	
    /** COD_IETT - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
	private ItemEstruturaIett itemEstruturaIett;
	
	/** cod_iettuc - Chave estrangeira (FK) - Tabela <item_estr_upl_categ_iettuc> */
	private ItemEstrUplCategIettuc itemEstrUplCategIettuc;
	
    /** cod_usu - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
	private UsuarioUsu usuarioUsu;
	
    /** cod_uta - Chave estrangeira (FK) - Tabela <tb_upload_tipo_arquivo_uta> */		
	private UploadTipoArquivoUta uploadTipoArquivoUta;	
	
    /** COD_USU_MANUTENCAO - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
    private UsuarioUsu usuManutencao;

    /** nome_iettuc */
    private String nomeIettuc;    
    
    /** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;    
    
    /**
     *
     */
    public HistoricoIettupH() {
	}

    /**
     *
     * @return
     */
    public String getArquivoIettup() {
		return arquivoIettup;
	}

        /**
         *
         * @param arquivoIettup
         */
        public void setArquivoIettup(String arquivoIettup) {
		this.arquivoIettup = arquivoIettup;
	}

        /**
         *
         * @return
         */
        public Long getCodIettupH() {
		return codIettupH;
	}

        /**
         *
         * @param codIettupH
         */
        public void setCodIettupH(Long codIettupH) {
		this.codIettupH = codIettupH;
	}

        /**
         *
         * @return
         */
        public Date getDataInclusaoIettup() {
		return dataInclusaoIettup;
	}

        /**
         *
         * @param dataInclusaoIettup
         */
        public void setDataInclusaoIettup(Date dataInclusaoIettup) {
		this.dataInclusaoIettup = dataInclusaoIettup;
	}

        /**
         *
         * @return
         */
        public String getDescricaoIettup() {
		return descricaoIettup;
	}

        /**
         *
         * @param descricaoIettup
         */
        public void setDescricaoIettup(String descricaoIettup) {
		this.descricaoIettup = descricaoIettup;
	}

        /**
         *
         * @return
         */
        public HistoricoMaster getHistoricoMaster() {
		return historicoMaster;
	}

        /**
         *
         * @param historicoMaster
         */
        public void setHistoricoMaster(HistoricoMaster historicoMaster) {
		this.historicoMaster = historicoMaster;
	}

        /**
         *
         * @return
         */
        public String getIndAtivoIettup() {
		return indAtivoIettup;
	}

        /**
         *
         * @param indAtivoIettup
         */
        public void setIndAtivoIettup(String indAtivoIettup) {
		this.indAtivoIettup = indAtivoIettup;
	}

        /**
         *
         * @return
         */
        public ItemEstrUplCategIettuc getItemEstrUplCategIettuc() {
		return itemEstrUplCategIettuc;
	}

        /**
         *
         * @param itemEstrUplCategIettuc
         */
        public void setItemEstrUplCategIettuc(
			ItemEstrUplCategIettuc itemEstrUplCategIettuc) {
		this.itemEstrUplCategIettuc = itemEstrUplCategIettuc;
	}

        /**
         *
         * @return
         */
        public ItemEstruturaIett getItemEstruturaIett() {
		return itemEstruturaIett;
	}

        /**
         *
         * @param itemEstruturaIett
         */
        public void setItemEstruturaIett(ItemEstruturaIett itemEstruturaIett) {
		this.itemEstruturaIett = itemEstruturaIett;
	}

        /**
         *
         * @return
         */
        public String getNomeIettuc() {
		return nomeIettuc;
	}

        /**
         *
         * @param nomeIettuc
         */
        public void setNomeIettuc(String nomeIettuc) {
		this.nomeIettuc = nomeIettuc;
	}

        /**
         *
         * @return
         */
        public String getNomeOriginalIettup() {
		return nomeOriginalIettup;
	}

        /**
         *
         * @param nomeOriginalIettup
         */
        public void setNomeOriginalIettup(String nomeOriginalIettup) {
		this.nomeOriginalIettup = nomeOriginalIettup;
	}

        /**
         *
         * @return
         */
        public Long getTamanhoIettup() {
		return tamanhoIettup;
	}

        /**
         *
         * @param tamanhoIettup
         */
        public void setTamanhoIettup(Long tamanhoIettup) {
		this.tamanhoIettup = tamanhoIettup;
	}

        /**
         *
         * @return
         */
        public UploadTipoArquivoUta getUploadTipoArquivoUta() {
		return uploadTipoArquivoUta;
	}

        /**
         *
         * @param uploadTipoArquivoUta
         */
        public void setUploadTipoArquivoUta(UploadTipoArquivoUta uploadTipoArquivoUta) {
		this.uploadTipoArquivoUta = uploadTipoArquivoUta;
	}

        /**
         *
         * @return
         */
        public UsuarioUsu getUsuarioUsu() {
		return usuarioUsu;
	}

        /**
         *
         * @param usuarioUsu
         */
        public void setUsuarioUsu(UsuarioUsu usuarioUsu) {
		this.usuarioUsu = usuarioUsu;
	}

        /**
         *
         * @return
         */
        public UsuarioUsu getUsuManutencao() {
		return usuManutencao;
	}

        /**
         *
         * @param usuManutencao
         */
        public void setUsuManutencao(UsuarioUsu usuManutencao) {
		this.usuManutencao = usuManutencao;
	}

        /**
         *
         * @return
         */
        public Long getCodIettup() {
		return codIettup;
	}

        /**
         *
         * @param codIettup
         */
        public void setCodIettup(Long codIettup) {
		this.codIettup = codIettup;
	}
    
    
    
}
