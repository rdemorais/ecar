package ecar.intercambioDados.importacao.comunicacao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ecar.exception.ECARException;
import ecar.intercambioDados.recurso.txt.RegistroTXT;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;

public class ComunicacaoImportacaoTXT extends ComunicacaoLogger {

	private ConfiguracaoImportacaoTXT configuracao;
	private PerfilIntercambioDadosPflid perfil;

	public ComunicacaoImportacaoTXT(PerfilIntercambioDadosPflid perfil, Configuracao configuracao) {
		this.logger = Logger.getLogger(this.getClass());
		this.configuracao = (ConfiguracaoImportacaoTXT)configuracao;
		this.perfil = perfil;
	}
	
	public List<IRegistro> obtemRegistros() throws ECARException {

		// TODO validar nome do arquivo para posterior implementação.

		List<IRegistro> lista = new ArrayList<IRegistro>();

		BufferedReader d;
		try {
			d = new BufferedReader(new InputStreamReader(((ConfiguracaoImportacaoTXT)configuracao).getStream(), perfil.getDadosTecnologiaPerfilDtp().getEncodeDtp()));
			String str;
			while (d.ready()) {

				str = d.readLine();
				RegistroTXT txt = new RegistroTXT(str);
				lista.add(txt);

			}
			if (!lista.isEmpty()) {
				this.validarConteudo(lista);
			}
		} catch (FileNotFoundException e) {
			this.logger.error(e);
			ECARException ecarex = new ECARException("erro.arquivoNaoEncontrado", e);
			throw ecarex;
		} catch (IOException e) {
			this.logger.error(e);
			ECARException ecarex = new ECARException("erro.upload", e);
			throw ecarex;
		}

		return lista;
	}

	
	/**
	 * A validação executada neste método antecede as validações sintáticas e semânticas, pois estas são logadas no BD 
	 * e não poderiam conter caracteres inválidos.
	 * As exceções identificadas neste método não são logadas no BD.    
	 * 
	 * @param lista
	 * @throws ECARException
	 */
	private void validarConteudo(List<IRegistro> lista) throws ECARException {

		for (int linha = 0; linha < lista.size(); linha++) {

			String conteudoLinha = ((RegistroTXT) lista.get(linha)).getLinha();
			char[] array = new char[1];
			for (int coluna = 0; coluna < conteudoLinha.length(); coluna++) {
				char c = conteudoLinha.charAt(coluna);
				
				if (Character.isISOControl(c)) {
					String[] args = new String[3];
					args[0] = perfil.getNomeTipoServicoPflid();
					args[1] = linha+1 + "";
					args[2] = coluna+1 + "";
					throw new ECARException("erro.arquivo.invalido", null, args);
				}
			}
		}
	}

	public ConfiguracaoImportacaoTXT getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(ConfiguracaoImportacaoTXT configuracao) {
		this.configuracao = configuracao;
	}

	public PerfilIntercambioDadosPflid getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilIntercambioDadosPflid perfil) {
		this.perfil = perfil;
	}

	
	
}
