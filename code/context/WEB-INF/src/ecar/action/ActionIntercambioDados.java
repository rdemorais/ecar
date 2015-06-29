package ecar.action;

import java.io.UnsupportedEncodingException;

import ecar.bean.DownloadArquivoBean;
import ecar.dao.intercambioDados.LogIntercambioDadosDao;
import ecar.exception.ECARException;
import ecar.pojo.intercambioDados.LogIntercambioDadosLid;
import ecar.util.Dominios;

public class ActionIntercambioDados implements IActionRequest {

	public DownloadArquivoBean consultarArquivoDownload(Long id) {
		DownloadArquivoBean downloadArquivoBean = null;
		try {
			LogIntercambioDadosDao logIntercambioDadosDao = new LogIntercambioDadosDao();
			LogIntercambioDadosLid logIntercambioDadosLid = (LogIntercambioDadosLid) logIntercambioDadosDao.buscar(LogIntercambioDadosLid.class, id);
			downloadArquivoBean = new DownloadArquivoBean(logIntercambioDadosLid.getDadosTecnologia().getNomeArquivoDtlid(), logIntercambioDadosLid.getConteudoLid().getBytes(Dominios.ENCODING_DEFAULT));
		} catch (ECARException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return downloadArquivoBean;
	}
}
