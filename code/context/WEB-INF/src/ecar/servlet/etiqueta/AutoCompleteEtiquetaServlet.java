package ecar.servlet.etiqueta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import ecar.pojo.Etiqueta;
import ecar.servico.EtiquetaService;

/**
 * Servlet implementation class AutoCompleteEtiquetaServlet
 */
public class AutoCompleteEtiquetaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeEtiqueta = request.getParameter("nomeEtiqueta");
		if(StringUtils.isNotBlank(nomeEtiqueta)){
			EtiquetaService etiquetaService = new EtiquetaService();
			List<Etiqueta> listaEtiquetas = new ArrayList<Etiqueta>();
			listaEtiquetas = etiquetaService.buscarEtiquetasPorNome(nomeEtiqueta);
			StringBuilder resultado = new StringBuilder();
			for(Etiqueta etiqueta : listaEtiquetas){
				resultado.append("<etiqueta>");
				resultado.append("<codigo>");
				resultado.append(etiqueta.getCodigo());
				resultado.append("</codigo>");
				resultado.append("<nome>");
				resultado.append(etiqueta.getNome());
				resultado.append("</nome>");
				resultado.append("</etiqueta>");
			}
			
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write("<?xml version='1.0' encoding='UTF-8' standalone='no'?>");
			response.getWriter().write("<etiquetas>" + resultado + "</etiquetas>");
		}
	}
}
