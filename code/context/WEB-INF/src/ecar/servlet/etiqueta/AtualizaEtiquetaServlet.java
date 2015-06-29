package ecar.servlet.etiqueta;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ecar.dao.EtiquetaDao;

public class AtualizaEtiquetaServlet extends HttpServlet {

	private static final long serialVersionUID = 6832232277082216265L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		EtiquetaDao dao = new EtiquetaDao();
		dao.atualizaEtiquetaRecursiva(null,null);
	}
}
