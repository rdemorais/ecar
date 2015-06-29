package ecar.action;

import ecar.bean.DownloadArquivoBean;

public interface IActionRequest {

	public DownloadArquivoBean consultarArquivoDownload(Long id);
	
}
