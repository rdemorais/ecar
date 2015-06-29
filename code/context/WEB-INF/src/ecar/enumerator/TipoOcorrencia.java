package ecar.enumerator;

import java.io.Serializable;

public class TipoOcorrencia implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4358792454768094490L;

	private int tipoOcorrencia;
	
	public static int CONTA_INEXISTENTE = 1;
	public static int SISTEMA_INVALIDO = 2;
	public static int TR_INVALIDO = 3;
	public static int DATA_HORA_CARGA_INEXISTENTE = 4;
	public static int FLAG_CONTABILIDADE_INVALIDO = 5;
	public static int QTDE_REGISTROS_INVALIDO = 6;
	public static int VALOR_INVALIDO = 7;
	public static int FORMATO_DATA_HORA_INVALIDO = 8;
	public static int INCONSISTENCIA_QTDE_REGISTROS_INFORMADOS_x_LIDOS = 9;
	public static int INCONSISTENCIA_TOTALIZACAO = 10;
	public static int VERSAO_INEXISTENTE_MES_ANO = 11;
	public static int LAYOUT_INVALIDO = 12;
	public static int ACAO_INEXISTENTE = 13;
	public static int RECURSO_INEXISTENTE = 14;
	public static int FONTE_INEXISTENTE = 15;
	
	public TipoOcorrencia() {}

	public TipoOcorrencia(int tipoOcorrencia) {
		this.tipoOcorrencia = tipoOcorrencia;
	}
	
	public int getTipoOcorrencia() {
		return tipoOcorrencia;
	}
	
	public void setTipoOcorrencia(int tipoOcorrencia) {
		this.tipoOcorrencia = tipoOcorrencia;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + tipoOcorrencia;
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
		final TipoOcorrencia other = (TipoOcorrencia) obj;
		if (tipoOcorrencia != other.tipoOcorrencia)
			return false;
		return true;
	}

}
