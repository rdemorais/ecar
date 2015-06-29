package ecar.bean.intercambioDados;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.util.Util;

import ecar.pojo.UsuarioUsu;

public class TelaExportacaoBean {

	
	private Date dataGeracao;
	private List<CaminhoArquivoExportacaoBean> caminhosArquivo;
	
	
	public List<CaminhoArquivoExportacaoBean> getCaminhosArquivo() {
		return caminhosArquivo;
	}
	
	public List<CaminhoArquivoExportacaoBean> getCaminhosArquivoComHash(HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		UsuarioUsu usuarioImagem = null;  
		String hashNomeArquivo = null;
//		StringBuffer arquivoURLEncoder = new StringBuffer();
		
		usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
		if (caminhosArquivo != null){
			for (CaminhoArquivoExportacaoBean caminho : caminhosArquivo) {
				
				if (caminho != null){
					hashNomeArquivo = Util.calcularHashNomeArquivo(null, caminho.getCaminhoFisico(), false);
					Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, null, caminho.getCaminhoFisico());			
				}		
				// substitui por URL Encoding
//	    		arquivoURLEncoder.append(URLDecoder.decode(hashNomeArquivo, Dominios.ENCODING_DEFAULT));
				caminho.setCaminhoFisicoSemEncode(hashNomeArquivo + "&N");			
			}
		}
		return caminhosArquivo;
	}


	public void setCaminhosArquivo(List<CaminhoArquivoExportacaoBean> caminhosArquivo) {
		this.caminhosArquivo = caminhosArquivo;
	}
	
	public Date getDataGeracao() {
		return dataGeracao;
	}

	public void setDataGeracao(Date dataGeracao) {
		this.dataGeracao = dataGeracao;
	}
	
	public void adicionarCaminhoArquivo(CaminhoArquivoExportacaoBean caminhoArquivo) {
		if (caminhosArquivo!=null) {
			this.caminhosArquivo.add(caminhoArquivo);
		} else {
			this.caminhosArquivo = new ArrayList<CaminhoArquivoExportacaoBean>();
			this.caminhosArquivo.add(caminhoArquivo);
		}
	}
	
	
	
	
}
