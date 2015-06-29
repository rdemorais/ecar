package ecar.intercambioDados.importacao.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import ecar.exception.ECARException;
import ecar.exception.intercambioDados.SemanticValidationException;
import ecar.exception.intercambioDados.SintaticValidationException;
import ecar.intercambioDados.IBusinessObject;
import ecar.intercambioDados.TipoDadosImportacao;
import ecar.intercambioDados.importacao.analisesemantica.AnaliseSemanticaTXTFactory;
import ecar.intercambioDados.importacao.analisesintatica.AnaliseSintaticaTXTFactory;
import ecar.intercambioDados.importacao.comunicacao.ComunicacaoImportacaoFactory;
import ecar.intercambioDados.importacao.comunicacao.Configuracao;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.intercambioDados.montador.MontadorTXTFactory;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;

public class ImportacaoDadosGeraisImportacaoPacInter implements ImportacaoDadosGeraisImportacaoPacInterCommand, ImportacaoDadosGeraisAnalisePacInterCommand {

	
	private static ImportacaoDadosGeraisImportacaoPacInter importacaoDadosImportacao = null;
	private static ImportacaoDadosGeraisAnalisePacInter importacaoDadosAnalise = null;
	
	
	public static ImportacaoDadosGeraisImportacaoPacInter getInstancia() {
		
		if (importacaoDadosImportacao==null) {
			importacaoDadosImportacao = new ImportacaoDadosGeraisImportacaoPacInter();
			importacaoDadosAnalise = importacaoDadosAnalise.getInstancia();
		} 
		return importacaoDadosImportacao;
	}
	
	
	public boolean analisarSemantica(IBusinessObject objetoNegocio, PerfilIntercambioDadosPflid perfil) throws SemanticValidationException, ECARException{
		return new AnaliseSemanticaTXTFactory().criar(objetoNegocio).valida(objetoNegocio, perfil);
	}

	public String importar(IBusinessObject objetoNegocio, Transaction tx, HttpServletRequest request, UsuarioUsu usuarioLogado, Logger logger) throws ECARException {		
		return ImportacaoDadosFactory.criar(objetoNegocio).importar(objetoNegocio, tx, request, usuarioLogado, logger);
	}
	
	public IBusinessObject montar(IRegistro registro, PerfilIntercambioDadosPflid perfil, UsuarioUsu usuarioLogado) throws ECARException {
		// if (perfil tem tecnologia TXT)
		return new MontadorTXTFactory().criar(registro.getTipo(perfil), perfil).montar(registro, perfil, usuarioLogado);
		// if (perfil tem tecnologia WS)
//		return new MontadorWSFactory().criar(registro.getTipo(perfil)).montar(registro, perfil, usuarioLogado);
	}
	
	public IBusinessObject montar(IBusinessObject objetoNegocio, PerfilIntercambioDadosPflid perfil, UsuarioUsu usuarioLogado) throws ECARException {
		// if (perfil tem tecnologia TXT e objeto de negocio é um item
		return new MontadorTXTFactory().criar(TipoDadosImportacao.ITEM, perfil).montar(objetoNegocio, perfil, usuarioLogado);
		// if (perfil tem tecnologia WS)
//		return new MontadorWSFactory().criar(registro.getTipo(perfil)).montar(registro, perfil, usuarioLogado);
	}
		
	public boolean analisarSintaxe(TipoDadosImportacao tipo, List<IRegistro> listaRegistros, PerfilIntercambioDadosPflid perfil, Configuracao configuracaoImportacao, Logger logger) throws SintaticValidationException, ECARException {
		// if (perfil tem tecnologia TXT)
		return new AnaliseSintaticaTXTFactory().criar(tipo, configuracaoImportacao, logger).valida(listaRegistros, perfil);
		// if (perfil tem tecnologia WS)
		
	}
	
	public List<IRegistro> comunicar(PerfilIntercambioDadosPflid perfil, Configuracao configuracao) throws ECARException {
		return ComunicacaoImportacaoFactory.criar(perfil, configuracao).obtemRegistros();
	}

	
	
	

}
