package ecar.servlet.etiqueta;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import ecar.exception.ECARException;
import ecar.exception.EtiquetaException;
import ecar.pojo.Etiqueta;
import ecar.servico.EtiquetaService;

/**
 * Servlet implementation class AdministracaoEtiquetaSerlvet
 */
public class AdministracaoEtiquetaSerlvet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		EtiquetaService etiquetaService = new EtiquetaService();
		List<Etiqueta> listaEtiquetas = etiquetaService.listarEtiquetas();
		if(StringUtils.isBlank(acao)){
			request.setAttribute("listaEtiquetas", listaEtiquetas);
			
			request.getRequestDispatcher("/tabelas/etiquetas/admEtiquetas.jsp").forward(request, response);
		}
		if(StringUtils.equals(acao, "editar")){
			Long codigoEtiqueta = Long.parseLong(request.getParameter("codigoEtiqueta"));
			try {
				Etiqueta etiqueta = etiquetaService.recuperarEtiquetaPorId(codigoEtiqueta);
				request.setAttribute("etiqueta", etiqueta);
				request.setAttribute("listaEtiquetas", listaEtiquetas);
				
				request.getRequestDispatcher("/tabelas/etiquetas/admEtiquetas.jsp").forward(request, response);
			} catch (ECARException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.equals(acao, "excluir")){
			String codigoEtiqueta = request.getParameter("codigoEtiqueta");
			try {
				Etiqueta etiqueta = etiquetaService.recuperarEtiquetaPorId(new Long(codigoEtiqueta));
				request.setAttribute("etiqueta", etiqueta);
				request.getRequestDispatcher("/tabelas/etiquetas/popUpExclusaoEtiqueta.jsp").forward(request, response);
			} catch (ECARException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(StringUtils.equals(acao, "inativarEtiqueta")){
			Long codigoEtiqueta = Long.parseLong(request.getParameter("codigoEtiqueta"));
			etiquetaService.inativarEtiquetaPorCodigo(codigoEtiqueta);
			String mensagem = "Etiqueta inativada com sucesso.";
			request.setAttribute("mensagem", mensagem);
			
			request.getRequestDispatcher("/tabelas/etiquetas/popUpExclusaoEtiqueta.jsp").forward(request, response);
		}
		if(StringUtils.equals(acao, "substituirEtiqueta")){
			Long codigoEtiquetaSubstituta = Long.parseLong(request.getParameter("codigoEtiquetaSubst"));
			Long codigoEtiquetaSubstituida = Long.parseLong(request.getParameter("codigoEtiqueta"));
			
			etiquetaService.substituirEtiquetas(codigoEtiquetaSubstituta, codigoEtiquetaSubstituida);
			
			String mensagem = "Etiqueta substituida com sucesso.";
			request.setAttribute("mensagem", mensagem);
			
			request.getRequestDispatcher("/tabelas/etiquetas/popUpExclusaoEtiqueta.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		String codigoEtiqueta = request.getParameter("codigoEtiqueta");
		String nomeEtiqueta = request.getParameter("nomeEtiqueta");
		String prioritario  = request.getParameter("prioritario");
		String ativo        = request.getParameter("ativo");
		
		String mensagem = null;
		
		EtiquetaService etiquetaService = new EtiquetaService();
		
		
		try {
			if(StringUtils.isNotBlank(codigoEtiqueta)){
				etiquetaService.editarEtiqueta(new Long(codigoEtiqueta), nomeEtiqueta, prioritario, ativo);
				mensagem = "Etiqueta alterada com sucesso.";
			}else{
				etiquetaService.gravarEtiquetas(nomeEtiqueta, prioritario, ativo);
				mensagem = "Etiqueta gravada com sucesso.";
			}
			request.setAttribute("mensagem", mensagem);
		}catch (EtiquetaException e) {
			mensagem = "Etiqueta já está cadastrada.";
			request.setAttribute("mensagem", mensagem);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Etiqueta> listaEtiquetas = etiquetaService.listarEtiquetas();
		
		request.setAttribute("listaEtiquetas", listaEtiquetas);
		
		request.getRequestDispatcher("/tabelas/etiquetas/admEtiquetas.jsp").forward(request, response);
		
	}
}
