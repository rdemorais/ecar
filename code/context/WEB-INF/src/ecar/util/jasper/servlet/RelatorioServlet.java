package ecar.util.jasper.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 *
 * @author 70744416353
 */
public abstract class RelatorioServlet extends HttpServlet {
	
	private Logger logger = Logger.getLogger(this.getClass());

        /**
         *
         */
        public RelatorioServlet(){
		super();
	}

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("request :: GET:: Classe:: " + this.getClass().getName() );
		if (request.getSession().getAttribute("seguranca") != null) {
			doRelatorioGet(request,response);
		}
	}
	
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("request :: POST:: Classe:: " + this.getClass().getName() );		
		doRelatorioPost(request,response);
	}

        /**
         *
         * @param request
         * @param response
         */
        public abstract void doRelatorioGet(HttpServletRequest request, HttpServletResponse response) ;
        /**
         *
         * @param request
         * @param response
         */
        public abstract void doRelatorioPost(HttpServletRequest request, HttpServletResponse response) ;
	
	
        /**
         *
         * @param response
         * @param arquivo
         * @throws IOException
         */
        protected void responseToPDF( HttpServletResponse response, byte[] arquivo ) throws IOException{
		response.setContentType("application/pdf");
		response.setContentLength(arquivo.length);	
		response.setHeader("Content-disposition", "attachment; filename=Relatorio.pdf");
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(arquivo,0,arquivo.length);
		outputStream.flush();
		outputStream.close();
	}
        
        /**
        * Manda ao usuário um arquivo no formato xls (Excel) como resposta do sistema. <br>
        * O arquivo de resposta tem, por padrão, o nome "Relatorio.xls" e "mime type" padrão de arquivos do Microsoft Excel.
        *
        * @since 15/03/2012
        * @author fabio.bicalho (fabiosbicalho.contato@gmail.com, fabio.bicalho@cast.com.br) 
        * @param response Objeto responsável por mandar o arquivo para o usuário.
        * @param arquivo Array de bytes a ser mandado ao usuário.
        * @throws IOException
        */
     protected void responseToXLS( HttpServletResponse response, byte[] arquivo ) throws IOException{
		response.setContentType("application/vnd.ms-excel");
		response.setContentLength(arquivo.length);	
		response.setHeader("Content-disposition", "attachment; filename=Relatorio.xls");
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(arquivo,0,arquivo.length);
		outputStream.flush();
		outputStream.close();
	}
	
	/*  TODO implementar formato para HTML  
	 * */
        /**
         *
         * @param response
         * @param conteudo
         * @throws IOException
         */
        protected void responseToHTML( HttpServletResponse response, String conteudo ) throws IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.append(conteudo);
		writer.flush();
		writer.close();
	}
	
	/**
	 * Redireciona para pagina de erro padrao se o parametro "objeto" for nulo
	 * @param request requisicao
	 * @param response responsta
	 * @param objeto objeto a ser testado
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void redirectDefaultErrorIfNull( HttpServletRequest request, HttpServletResponse response, Object objeto  ) throws ServletException, IOException{
		if ( objeto == null){
			RequestDispatcher dispatcher = request.getRequestDispatcher( "../erro.jsp" );
			dispatcher.forward( request, response); 
		}
	}
	
}
