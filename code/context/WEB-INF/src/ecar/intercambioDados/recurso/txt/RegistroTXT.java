package ecar.intercambioDados.recurso.txt;

import comum.util.Util;

import ecar.intercambioDados.TipoDadosImportacao;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.intercambioDados.importacao.comunicacao.TipoRegistroEnum;
import ecar.intercambioDados.montador.IMontadorTXT;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;
import ecar.pojo.intercambioDados.tecnologia.DadosTecnologiaPerfilTxtDtpt;


public class RegistroTXT implements IRegistro {

	private String linha;
	private int numeroLinha;
	
	
	public String getLinha() {
		return linha;
	}

	public RegistroTXT(String linha) {
		super();
		this.linha = Util.normalizaQuebraDeLinha(Util.normalizaCaracterMarcador(linha)).replaceAll("\n"," ");
	}

	public TipoDadosImportacao getTipo(PerfilIntercambioDadosPflid perfil) {
		
		String[] posicoes = null;
		int tipo = 0;
		TipoDadosImportacao tipoDados = null;
		
		posicoes = Util.split(getLinha(), ((DadosTecnologiaPerfilTxtDtpt)perfil.getDadosTecnologiaPerfilDtp()).getSeparadorCamposDtpt());
		tipo = Integer.parseInt(posicoes[IMontadorTXT.POSICAO_CAMPO_IDENTIFICADOR]);
		
		if (tipo== TipoRegistroEnum.HEADER_ITEM.getCodigo()) {
			tipoDados = TipoDadosImportacao.ITEM;
		} 
		
		return tipoDados;
	}
	
	public String getOperacao(PerfilIntercambioDadosPflid perfil) {
		
		String[] posicoes = null;		
		
		posicoes = Util.split(getLinha(), ((DadosTecnologiaPerfilTxtDtpt)perfil.getDadosTecnologiaPerfilDtp()).getSeparadorCamposDtpt());

				
		return posicoes[IMontadorTXT.POSICAO_CAMPO_OPERACAO];
	}
	

	public int getNumeroLinha() {
		return numeroLinha;
	}

	public void setNumeroLinha(int numeroLinha) {
		this.numeroLinha = numeroLinha;
	}
	
	
	
	public String toString(){
		return linha;
	}
	
}
