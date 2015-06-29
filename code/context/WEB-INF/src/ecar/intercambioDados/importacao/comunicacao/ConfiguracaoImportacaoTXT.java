package ecar.intercambioDados.importacao.comunicacao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import comum.util.Util;

import ecar.exception.ECARException;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;

public class ConfiguracaoImportacaoTXT extends Configuracao {

	private InputStream stream;
	private String fileName;
	private Date dataGeracaoArquivo;
	
	public String getFileName() {
		return Util.getNomeArquivo(fileName);
	}
	
	public String getAbsoluteFileName() {
		return fileName;
	}

	public Date getDataGeracaoArquivo() {
		return dataGeracaoArquivo;
	}

	public void setDataGeracaoArquivo(Date dataGeracaoArquivo) {
		this.dataGeracaoArquivo = dataGeracaoArquivo;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ConfiguracaoImportacaoTXT(PerfilIntercambioDadosPflid perfil, InputStream stream, String fileName) {
		super(perfil);
		this.stream = stream;
		this.fileName = fileName;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}
	
	public String getSource() throws ECARException {

		BufferedReader d;
		String arquivo = "";
		try {
			stream.reset();
			d = new BufferedReader(new InputStreamReader(stream, perfil.getDadosTecnologiaPerfilDtp().getEncodeDtp()));
			while (d.ready()) {
				arquivo += d.readLine().trim() + "\n";
			}
		} catch (UnsupportedEncodingException unex) {
			ECARException ecarex = new ECARException("erro.upload", unex);
			throw ecarex;
		} catch (IOException ioex) {
			ECARException ecarex = new ECARException("erro.upload", ioex);
			throw ecarex;
		}

		return arquivo;
	}
	
	
	
}
