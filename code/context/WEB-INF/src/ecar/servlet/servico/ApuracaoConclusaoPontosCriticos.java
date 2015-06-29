package ecar.servlet.servico;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import comum.util.Data;

import ecar.dao.ServicoDao;
import ecar.exception.ECARException;
import ecar.pojo.ItemEstruturaIett;

/**
 *
 * @author 70744416353
 */
public class ApuracaoConclusaoPontosCriticos extends HttpServlet {
	private static final long serialVersionUID = 3723440142007156805L;
	private Logger logger = Logger.getLogger(this.getClass());
	
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("request :: GET:: Classe:: " + this.getClass().getName() );
		execute(request,response);
	}
	
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("request :: POST:: Classe:: " + this.getClass().getName() );
		execute(request,response);
				
	}
	
		
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String codIett = request.getParameter("codIett"); // código do ItemEstruturaIett correspondente
		String dataBase = request.getParameter("dataBase"); // data base para o cálculo da apuração
		
		ServicoDao servicoDao = new ServicoDao(request);
		
		try{
			
			ItemEstruturaIett itemEstrutura = (ItemEstruturaIett)servicoDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIett) );
			double apuracaoConclusao = servicoDao.apuracaoConclusaoPontosCriticos(itemEstrutura, Data.parseDate(dataBase));
			
			response.setContentType("text/html");
			
			PrintWriter output = response.getWriter();
			output.println(apuracaoConclusao);
			
		} catch (IOException e) {
			this.logger.error(e);
			throw new ServletException(e);
		} catch (ECARException e) {
			this.logger.error(e);
			throw new ServletException(e);
		} catch (Exception e) {
			this.logger.error(e);
			throw new ServletException(e);
		}
	}
		

}
