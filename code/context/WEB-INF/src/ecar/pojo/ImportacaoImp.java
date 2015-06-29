package ecar.pojo;


import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author 70744416353
 */
public class ImportacaoImp implements Serializable {

	private static final long serialVersionUID = -6132108582445118670L;
	
	/** COD_IMP - Primary Key ( PK )*/
	private Long codImp;
	
	/** DATA_HORA_IMP - NOT NULL*/
	private Date dataHoraImp;
	
	/** DATA_HORA_GERACAO_SIS_ORIGEM - NOT NULL*/
	private Date dataHoraGeracaoSisOrigem;
	
	/** COD_USU_IMP - Foreign Key - Tabela <TB_Usuario_Usu> - NOT NULL */
	private UsuarioUsu usuarioUsu;
	
	/** NOME_ARQUIVO_IMP - NOT NULL */
	private String nomeArquivoImp;
	
	/** NUM_REGISTROS_VALIDOS_IMP - NOT NULL*/
	private Integer numRegistrosValidosImp;
	
	private Integer numRegistrosInvalidosImp;
	
	private Set historicoEfierHs;
	
	
        /**
         *
         */
        public ImportacaoImp() {
	}

        /**
         *
         * @return
         */
        public Long getCodImp() {
		return codImp;
	}

        /**
         *
         * @param codImp
         */
        public void setCodImp(Long codImp) {
		this.codImp = codImp;
	}
	
        /**
         *
         * @return
         */
        public Date getDataHoraGeracaoSisOrigem() {
		return dataHoraGeracaoSisOrigem;
	}

        /**
         *
         * @param dataHoraGeracaoSisOrigem
         */
        public void setDataHoraGeracaoSisOrigem(Date dataHoraGeracaoSisOrigem) {
		this.dataHoraGeracaoSisOrigem = dataHoraGeracaoSisOrigem;
	}

        /**
         *
         * @return
         */
        public Date getDataHoraImp() {
		return dataHoraImp;
	}

        /**
         *
         * @param dataHoraImp
         */
        public void setDataHoraImp(Date dataHoraImp) {
		this.dataHoraImp = dataHoraImp;
	}

        /**
         *
         * @return
         */
        public String getNomeArquivoImp() {
		return nomeArquivoImp;
	}

        /**
         *
         * @param nomeArquivoImp
         */
        public void setNomeArquivoImp(String nomeArquivoImp) {
		this.nomeArquivoImp = nomeArquivoImp;
	}


        /**
         *
         * @return
         */
        public Integer getNumRegistrosValidosImp() {
		return numRegistrosValidosImp;
	}

        /**
         *
         * @param numRegistrosValidosImp
         */
        public void setNumRegistrosValidosImp(Integer numRegistrosValidosImp) {
		this.numRegistrosValidosImp = numRegistrosValidosImp;
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
        public Integer getNumRegistrosInvalidosImp() {
		return numRegistrosInvalidosImp;
	}

        /**
         *
         * @param numRegistrosInvalidosImp
         */
        public void setNumRegistrosInvalidosImp(Integer numRegistrosInvalidosImp) {
		this.numRegistrosInvalidosImp = numRegistrosInvalidosImp;
	}
	
        /**
         *
         * @return
         */
        public Set getHistoricoEfierHs() {
		return historicoEfierHs;
	}

        /**
         *
         * @param historicoEfierHs
         */
        public void setHistoricoEfierHs(Set historicoEfierHs) {
		this.historicoEfierHs = historicoEfierHs;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((codImp == null) ? 0 : codImp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ImportacaoImp other = (ImportacaoImp) obj;
		if (codImp == null) {
			if (other.codImp != null)
				return false;
		} else if (!codImp.equals(other.codImp))
			return false;
		return true;
	}
}
