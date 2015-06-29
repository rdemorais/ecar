package ecar.enumerator;

public enum Preposicoes {
	POR("POR"),
	PARA("PARA"),
	PERANTE("PERANTE"),
	A("A"),
	ANTE("ANTE"),
	ATE("ATE"),
	APOS("APOS"),
	DA("DA"),
	DE("DE"),
	DO("DO"),
	DESDE("DESDE"),
	EM("EM"),
	ENTRE("ENTRE"),
	COM("COM"),
	CONTRA("CONTRA"),
	SEM("SEM"),
	SOB("SOB"),
	SOBRE("SOBRE"),
	E("E"),
	OU("OU"),
	TRAS("TRAS");
	
	private Preposicoes(String preposicao){
		this.preposicao = preposicao;
	}
	
	private String preposicao;

	public String getPreposicao() {
		return preposicao;
	}
}
