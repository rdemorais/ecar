package ecar.servlet.componente;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import comum.util.Pagina;

import ecar.dao.ExercicioDao;
import ecar.dao.ItemEstrtIndResultLocalIettirlDao;
import ecar.dao.LocalItemDao;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstrtIndResulLocalIettirl;
import ecar.pojo.LocalItemLit;

/**
 *
 * @author 70744416353
 */
public class PesquisaLocalHierarquiaServlet extends HttpServlet{

	private static final long serialVersionUID = -693849991527194195L;
	private Logger logger = Logger.getLogger(this.getClass());
	//	constante de base de dados - Periodo de 2008 - 2011
	private final Long CONSTANTE_PERIODO_EXERCICIO = Long.valueOf(2);
	
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
	
		final ItemEstrtIndResultLocalIettirlDao dao = new ItemEstrtIndResultLocalIettirlDao(request);
		final DecimalFormat formatBrasil = new DecimalFormat ("#,##0.00", new DecimalFormatSymbols (new Locale ("pt", "BR")));
		
		try{
			StringBuilder responseHTML = new StringBuilder();
			Long paramLocal = Pagina.getParamLong(request, "local");
			Long codIettir = Pagina.getParamLong(request, "codIettir");
			
			LocalItemDao daoItem = new LocalItemDao(request);
			final ExercicioDao exercicioDao = new ExercicioDao(request);
			List listaExercicios = exercicioDao.getExercicioByPeriodicidade( CONSTANTE_PERIODO_EXERCICIO );
			
			List localList = daoItem.getLocaisFilhos(paramLocal);
			
			if ( !localList.isEmpty() ){
				responseHTML.append( "<table align=\"left\" width='100%' border='0' >");
				for (Iterator iter = localList.iterator(); iter.hasNext();) {
					responseHTML.append( "<tr>");
					LocalItemLit local = (LocalItemLit) iter.next();
					responseHTML.append("<td width='30%' align='left' height='25px' valign='top' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					responseHTML.append("<img src='../../images/icon_seta_ident.gif'/>&nbsp;");	
					responseHTML.append( local.getIdentificacaoLit() +"</td>");

					for (Iterator iterator = listaExercicios.iterator(); iterator.hasNext();) {
						ExercicioExe exe = (ExercicioExe) iterator.next();

						ItemEstrtIndResulLocalIettirl item = dao.getQtdePrevistaByLocal( codIettir, exe.getCodExe(), local.getCodLit() );
						
						responseHTML.append( "<td width='14%' align=\"center\" >");
						responseHTML.append( "<input type=\"text\"  onblur=\"calcularTotalLocal('" );
						responseHTML.append( local.getCodLit().toString() );
						responseHTML.append(  "');calcularTotalExercicio('"+exe.getCodExe().toString()+"');\"    onmouseover=\"calcularTotalExercicio('"+exe.getCodExe().toString()+"');calcularTotalLocal('" +local.getCodLit().toString() + "');\" " );								
						responseHTML.append( " id=\"" );
						responseHTML.append( "cmp|" + local.getCodLit().toString() + "|" + exe.getCodExe().toString() );
						responseHTML.append( "\" name=\"" );
						responseHTML.append( "cmp|" + local.getCodLit().toString() + "|" + exe.getCodExe().toString() );
						responseHTML.append( "\" value=\"" + formatBrasil.format(item.getQtdPrevistaIettirl().doubleValue()) + "\"" );						
						responseHTML.append( "\" />" );

						responseHTML.append( "<label class=\"dica\" onmouseover=\"montaTooltipMouseOver(this,\'"+ exe.getCodExe().toString() +  "\',\'" + exe.getDescricaoExe() +"\',\'"+ local.getCodLit().toString() +  "\',\'" + local.getIdentificacaoLit() +"\');\"   onmouseout=\"montaTooltipMouseOut(\'"+ exe.getCodExe().toString() +  "\',\'" + local.getCodLit().toString() +"\');\"  >" ); 
						responseHTML.append( "<img src=\"" + request.getContextPath() + "/images/dica.png\" align=\"absmiddle\" border=\"0\" onclick=\"montaTooltipMouseClick(\'"+ exe.getCodExe().toString() +  "\',\'" + exe.getDescricaoExe() +"\',\'"+ local.getCodLit().toString() +  "\',\'" + local.getIdentificacaoLit() +"\');\" > " );
						responseHTML.append( "<span id=\"" + local.getCodLit().toString() + "|" + exe.getCodExe().toString() + "\" ></span></label>" );
						responseHTML.append( "</td>");					
				
					}
			
					responseHTML.append( "<td width='14%' align=\"center\" >");
					responseHTML.append( "<input type=\"text\" id=\"" );
					responseHTML.append( "cmp|" + local.getCodLit().toString() + "|total");
					responseHTML.append( "\" name=\"" );
					responseHTML.append( "cmp|" + local.getCodLit().toString() + "|total");
					responseHTML.append( "\" value=\"0,00\" />" );
					responseHTML.append( "</td>");
							
					responseHTML.append( "</tr>");
					
				}
				responseHTML.append("</table>");
			}else{
				responseHTML.append("Nenhum registro encontrado");
			}
			
			responseToHTML(response, responseHTML.toString());
			
		}catch(Exception e){
			StringBuilder responseHTML = new StringBuilder();
			responseHTML.append("Nenhum registro encontrado");
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
