package ecar.servlet.relatorio;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ecar.dao.AcompReferenciaDao;
import ecar.exception.ECARException;
import ecar.pojo.AcompReferenciaAref;
/**
 * Servlet implementation class cicloReferenciaServlet
 */
public class CicloReferenciaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CicloReferenciaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
//		StringBuffer saida = new StringBuffer();
//		
//		try {
//			List<AcompReferenciaAref> listaCiclos = acompReferenciaDao.getListAcompReferencia();
//			for(AcompReferenciaAref ciclo: listaCiclos){
//				saida.append("<option value='"+ciclo.getCodAref().toString()+"'>"+ciclo.getDiaAref()+"/"+ciclo.getMesAref()+"/"+ciclo.getAnoAref()+" - "+ciclo.getNomeAref()+"<option>");
//			}
//		} catch (ECARException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		response.setContentType("text/html");
//		response.setCharacterEncoding("UTF-8");
//		response.getWriter().write(saida.toString());		
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
		StringBuffer saida = new StringBuffer("<option value='0'>Todos os Ciclos</option>");
		Long exercicio = Long.parseLong(request.getParameter("exercicio").toString());
		
		try {
			List<AcompReferenciaAref> listaCiclos = (List<AcompReferenciaAref>) acompReferenciaDao.getListAcompReferencia();
			for(AcompReferenciaAref ciclo: listaCiclos){
				if((ciclo.getExercicioExe().getCodExe().equals(exercicio)||exercicio.equals(0L))&&ciclo!=null){
					saida.append("<option value='"+ciclo.getCodAref().toString()+"'>"+ciclo.getDiaAref()+"/"+ciclo.getMesAref()+"/"+ciclo.getAnoAref()+" - "+ciclo.getNomeAref()+"</option>");
				}	
			}
		} catch (ECARException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(saida.toString());
	}	
}
