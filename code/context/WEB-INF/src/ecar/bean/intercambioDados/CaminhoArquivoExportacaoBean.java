package ecar.bean.intercambioDados;

import java.io.File;
import java.io.UnsupportedEncodingException;

public class CaminhoArquivoExportacaoBean {
	
	private String caminhoFisico;
	private String nomeArquivo;
	private String nomeEstrutura;
	private String nomeFuncao;
	private String caminhoFisicoSemEncode;
	
	
	public String getCaminhoFisicoSemEncode() {
		return caminhoFisicoSemEncode;
	}
	public void setCaminhoFisicoSemEncode(String caminhoFisicoSemEncode) {
		this.caminhoFisicoSemEncode = caminhoFisicoSemEncode;
	}
	public String getNomeEstrutura() {
		return nomeEstrutura;
	}
	public void setNomeEstrutura(String nomeEstrutura) {
		this.nomeEstrutura = nomeEstrutura;
	}
	public String getNomeFuncao() {
		return nomeFuncao;
	}
	public void setNomeFuncao(String nomeFuncao) {
		this.nomeFuncao = nomeFuncao;
	}
	public String getCaminhoFisico() throws UnsupportedEncodingException {
		//return URLEncoder.encode(caminhoFisico,ConstantesECAR.ENCODE_PADRAO);
		return caminhoFisico;
	}
	public void setCaminhoFisico(String caminhoFisico) {
		this.caminhoFisico = caminhoFisico;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	
	public String getCaminhoCompleto() {
		return this.caminhoFisico + File.separator + this.nomeArquivo;
	}
			
}
