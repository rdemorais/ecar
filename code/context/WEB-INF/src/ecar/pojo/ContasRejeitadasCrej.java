package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author 70744416353
 */
public class ContasRejeitadasCrej implements Serializable {

	private static final long serialVersionUID = -442317145307961009L;

	/** COD_CREJ ( PK ) */
	private Long codCrej;
	
	/** CONTA **/
	private String contaCrej;
	
	/** COD_USU_MANUTENCAO - Foreign Key <TB_USUARIO_USU> */
	private UsuarioUsu usuarioUsu;
	
	/** DATA_ULT_MANUTENCAO */
	private Date dataUltManutencao;
	
        /**
         *
         */
        public ContasRejeitadasCrej() {
	
	}

        /**
         *
         * @param codCrej
         * @param contaCrej
         * @param usuarioUsu
         * @param dataUltManutencao
         */
        public ContasRejeitadasCrej(Long codCrej, String contaCrej, UsuarioUsu usuarioUsu, Date dataUltManutencao) {
		super();
		this.codCrej = codCrej;
		this.contaCrej = contaCrej;
		this.usuarioUsu = usuarioUsu;
		this.dataUltManutencao = dataUltManutencao;
	}

        /**
         *
         * @return
         */
        public Long getCodCrej() {
		return codCrej;
	}

        /**
         *
         * @param codCrej
         */
        public void setCodCrej(Long codCrej) {
		this.codCrej = codCrej;
	}

        /**
         *
         * @return
         */
        public Date getDataUltManutencao() {
		return dataUltManutencao;
	}

        /**
         *
         * @param dataUltManutencao
         */
        public void setDataUltManutencao(Date dataUltManutencao) {
		this.dataUltManutencao = dataUltManutencao;
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
        public String getContaCrej() {
		return contaCrej;
	}

        /**
         *
         * @param contaCrej
         */
        public void setContaCrej(String contaCrej) {
		this.contaCrej = contaCrej;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((codCrej == null) ? 0 : codCrej.hashCode());
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
		final ContasRejeitadasCrej other = (ContasRejeitadasCrej) obj;
		if (codCrej == null) {
			if (other.codCrej != null)
				return false;
		} else if (!codCrej.equals(other.codCrej))
			return false;
		return true;
	}
}
