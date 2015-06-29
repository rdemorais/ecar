package ecar.servlet.file;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ecar.action.IActionRequest;
import ecar.bean.DownloadArquivoBean;

/**
 * 
 * 
 */
public class DownloadSGDB extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1731851347635942309L;

    public void service(HttpServletRequest request, HttpServletResponse response) {

	    String classeInstanciada = request.getParameter("classeInstanciada");
	    Long id = new Long(request.getParameter("idObjeto"));
	    try { 
		    IActionRequest action = (IActionRequest) Class.forName(classeInstanciada).newInstance();
		    DownloadArquivoBean bean = action.consultarArquivoDownload(id);
		    response.setContentType("inline/download");  
		    String arquivo = "attachment;filename=" + bean.getNomeArquivo();  
		    response.setHeader("Content-Disposition", arquivo);  
		    ServletOutputStream servletOutputStream = response.getOutputStream();  
		    servletOutputStream.write(bean.getConteudoArquivo());  
		    servletOutputStream.flush();  
		    servletOutputStream.close();  
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
