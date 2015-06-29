package ecar.intercambioDados.log;

import java.util.List;

import ecar.exception.ECARException;
import ecar.exception.intercambioDados.SintaticValidationException;
import ecar.intercambioDados.montador.LinhaResultadoValidacao;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.intercambioDados.LogIntercambioDadosLid;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;

public abstract class LogIntercambioDados {

	
	public abstract LogIntercambioDadosLid montaLogSintatico(SintaticValidationException validation,UsuarioUsu usuarioLogado) throws ECARException ;
	
	public abstract LogIntercambioDadosLid montaLogSemantico(UsuarioUsu usuarioUsu, List<LinhaResultadoValidacao> registrosValidos, List<LinhaResultadoValidacao> registrosInvalidos, PerfilIntercambioDadosPflid perfil) throws ECARException;
	
}
