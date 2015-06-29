package ecar.servlet.componente;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import comum.util.Pagina;

import ecar.dao.ItemEstrtIndResultLocalIettirlDao;

/**
 *
 * @author 70744416353
 */
public class ExcluirIndicadorPorLocalServlet extends HttpServlet{


	private static final long serialVersionUID = 8006183415879658218L;

	private Logger logger = Logger.getLogger(this.getClass());
	
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("request :: GET:: Classe:: " + this.getClass().getName() );
		StringBuilder conteudo = new StringBuilder();
		conteudo.append("false");
		logger.error("Acesso nao autorizado:: acesso GET ao servico de salvar indicador por local");
		responseToHTML( response, conteudo.toString());
	}
	
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("request :: POST:: Classe:: " + this.getClass().getName() );
		execute(request,response);
				
	}
	
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final ItemEstrtIndResultLocalIettirlDao dao = new ItemEstrtIndResultLocalIettirlDao(request);
		StringBuilder conteudo = new StringBuilder();
		
		try {
			// Excluindo todas quantidades/valores referentes ao item estrutura.
			Long codIettirLong = Pagina.getParamLong(request, "codIettir");
//			dao.deleteBycodIettir( codIettirLong );

			// Informando que a exclusão foi realizada com sucesso.
			conteudo.append("true");
			responseToHTML(response, conteudo.toString() );
			
		} catch(Exception e){
			logger.error(e);
			StringBuilder responseHTML = new StringBuilder();
			
			// Informando que a exclusão das quantidades/valores já cadastrados
			// não puderam ser excluídos.
			responseHTML.append("false");
			responseToHTML(response, responseHTML.toString());
		}
	}
	
	private void responseToHTML ( HttpServletResponse response, String conteudo ) throws IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.append(conteudo);
		writer.flush();
		writer.close();
	}
	
	
}