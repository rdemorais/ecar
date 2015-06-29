package ecar.intercambioDados.montador;

import ecar.exception.ECARException;
import ecar.intercambioDados.IBusinessObject;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;

public interface IMontadorTXT extends IMontador {
	
	//Header do Item
	public static final int POSICAO_CAMPO_IDENTIFICADOR = 0;
	public static final int POSICAO_CAMPO_OPERACAO = 1;
	public static final int POSICAO_CAMPO_CODIGO = 2;
	public static final int POSICAO_CAMPO_NOME = 3;
	public static final int POSICAO_CAMPO_DESCRICAO = 4;
	public static final int POSICAO_CAMPO_DATA_INICIO = 5;
	public static final int POSICAO_CAMPO_DATA_CONCLUSAO = 6;
	public static final int POSICAO_CAMPO_CUSTO = 7;
	public static final int POSICAO_CAMPO_SITUACAO = 8;
	public static final int POSICAO_CAMPO_TIPO = 9;
	public static final int POSICAO_CAMPO_SUBTIPO = 10;
	public static final int POSICAO_CAMPO_VALOR_ASSOCIACAO = 11;
	
	//-- Novos campos do Header do Item
	public static final int POSICAO_CAMPO_EXECUTOR = 12;
	public static final int POSICAO_CAMPO_META_PAC = 13;
	public static final int POSICAO_CAMPO_INVEST_PREV_2007_2010 = 14;
	public static final int POSICAO_CAMPO_INVEST_PREV_APOS_2010 = 15;
	public static final int POSICAO_CAMPO_ESTAGIO = 16;
	public static final int POSICAO_CAMPO_TIPO_EMPREENDIMENTO = 17;
	
	public static final int POSICAO_CAMPO_SEQUENCIAL = 18;
	
	IBusinessObject montar(IRegistro registro, PerfilIntercambioDadosPflid perfil, UsuarioUsu usuarioLogado) throws ECARException;
	
	IBusinessObject montar(IBusinessObject objetoNegocio, PerfilIntercambioDadosPflid perfil, UsuarioUsu usuarioLogado) throws ECARException;
	

}
