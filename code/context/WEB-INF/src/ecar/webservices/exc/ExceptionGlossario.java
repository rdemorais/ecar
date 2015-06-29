package ecar.webservices.exc;

public enum ExceptionGlossario {
	
	ERRO_CONSTRUCAO_CONSULTA(1001L,"Problemas na construção da consulta"),
	ERRO_CHAVE_MAX_MENOR_CHAVE_MIN(2001L,"O parâmetro chvExternoMinimo deve ser menor que chvExternoMaximo");
	
	private Long cod;
	private String msg;
	
	private ExceptionGlossario(Long cod, String msg) {
		this.cod = cod;
		this.msg = msg;
	}

	public Long getCod() {
		return cod;
	}

	public void setCod(Long cod) {
		this.cod = cod;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}