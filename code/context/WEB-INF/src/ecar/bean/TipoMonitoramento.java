package ecar.bean;


public enum TipoMonitoramento {

	OBJETIVO_ESTRATEGICO(1), ESTRATEGIA(2), RESULTADO(3), PRODUTO(4), ACAO(5);
	private final int tipo;

	public int getTipo() {
		return tipo;
	}

	private TipoMonitoramento(int tipo) {
		this.tipo = tipo;
	}
	
}
