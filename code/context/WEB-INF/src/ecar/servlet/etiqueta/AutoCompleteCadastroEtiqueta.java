package ecar.servlet.etiqueta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import ecar.pojo.RelacionamentoIettEtiqueta;
import ecar.servico.EtiquetaService;

public class AutoCompleteCadastroEtiqueta  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<RelacionamentoIettEtiqueta> listaRelEtiquetas = new ArrayList<RelacionamentoIettEtiqueta>();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeEtiqueta = request.getParameter("nomeEtiq");
		Long codIett = new Long(request.getParameter("codIett"));
		listaRelEtiquetas.clear();
		if(StringUtils.isNotBlank(nomeEtiqueta)){
			EtiquetaService etiquetaService = new EtiquetaService();
			listaRelEtiquetas.addAll(etiquetaService.listarRelacionamentosIettEtiqueta(codIett, nomeEtiqueta));
			StringBuilder resultado = new StringBuilder();
			
			for(RelacionamentoIettEtiqueta rel : listaRelEtiquetas){
				resultado.append("<etiqueta>");
				resultado.append("<codigo>");
				resultado.append(rel.getEtiqueta().getCodigo());
				resultado.append("</codigo>");
				resultado.append("<nome>");
				resultado.append(rel.getEtiqueta().getNome());
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
