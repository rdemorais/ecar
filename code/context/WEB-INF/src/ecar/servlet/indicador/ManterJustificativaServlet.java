package ecar.servlet.indicador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ecar.dao.AcompRealFisicoDao;
import ecar.exception.ECARException;
import ecar.pojo.AcompRealFisicoArf;

public class ManterJustificativaServlet extends HttpServlet{

	private static final long serialVersionUID = -6526468580075026363L;
	
	private AcompRealFisicoDao arfDao;
	
	public ManterJustificativaServlet() {
		arfDao = new AcompRealFisicoDao(null);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String justificativa = req.getParameter("justificativa");
		String codArf = req.getParameter("codArf");
		String codUsu = req.getParameter("codUsu");
		System.out.println(justificativa);
		super.doPost(req, resp);
		/*
		try {
			AcompRealFisicoArf arf = (AcompRealFisicoArf) arfDao.localizar(AcompRealFisicoArf.class, Long.parseLong(codArf));
			arf.setJustificativaArf(justificativa);
			
			arfDao.alterar(arf);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ECARException e) {
			e.printStackTrace();
		}
		*/
	}
}
