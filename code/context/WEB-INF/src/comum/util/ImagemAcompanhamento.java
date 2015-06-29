package comum.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import ecar.dao.ConfiguracaoDao;
import ecar.dao.CorDao;
import ecar.exception.ECARException;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.Cor;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.UsuarioUsu;

/**
 *
 * @author 70744416353
 */
public class ImagemAcompanhamento {
	/**
	 * = (TipoFuncAcompTpfa) tipoFuncAcompDao.buscar(TipoFuncAcompTpfa.class, codTpfa);
	 * 
	 * @param cor
	 * @param request
         * @param funcao
         * @return
	 * @throws ECARException 
	 */
	public static String getURLImagem(Cor cor, HttpServletRequest request , TipoFuncAcompTpfa funcao ) throws ECARException, NoSuchAlgorithmException, UnsupportedEncodingException{
		String url=null;
		CorDao corDao = new CorDao(request);
		
		// Configuração	
		ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
		String pathRaiz = configuracao.getRaizUpload();
		
		UsuarioUsu usuarioImagem = null;  
		String hashNomeArquivo = null;
		
		if(cor.getIndPosicoesGeraisCor().equals("S")){ 
			cor.getCodCor();
			
		url = corDao.getImagemPersonalizada(cor, funcao, "D");
		if( url != null ) {
			
			usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
			hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, url);
			Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, url);
			
			url=request.getContextPath()+"/DownloadFile?tipo=open&RemoteFile="+ hashNomeArquivo ;
		} else {
			if( cor.getCodCor() != null ) { 
				url =request.getContextPath() + "/images/" + corDao.getImagemSinal(cor,funcao)+ "" ;
			}
		} 
	}
		
		return url;
	}
	

}
