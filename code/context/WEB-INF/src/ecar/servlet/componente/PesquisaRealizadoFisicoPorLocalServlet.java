package ecar.servlet.componente;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import comum.util.Pagina;

import ecar.api.facade.ItemEstrutura;
import ecar.api.facade.Local;
import ecar.dao.AcompRealFisicoDao;
import ecar.dao.AcompRealFisicoLocalDao;
import ecar.dao.ItemEstruturaDao;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompRealFisicoLocalArfl;
import ecar.pojo.ItemEstrutLocalIettl;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.LocalItemLit;

/**
 *
 * @author 70744416353
 */
public class PesquisaRealizadoFisicoPorLocalServlet extends HttpServlet{

	private static final long serialVersionUID = 2244076567158750862L;
	private Logger logger = Logger.getLogger(this.getClass());
	
	//	constante de base de dados - Periodo de 2008 - 2011
	//private final Long CONSTANTE_PERIODO_EXERCICIO = Long.valueOf(2);
	
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//logger.info("request :: GET:: Classe:: " + this.getClass().getName() );
		execute(request,response);
	}
	
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//logger.info("request :: POST:: Classe:: " + this.getClass().getName() );
		execute(request,response);
				
	}
	
		
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final DecimalFormat formatBrasil = new DecimalFormat ("#,##0.00", new DecimalFormatSymbols (new Locale ("pt", "BR")));

		try{
			StringBuilder responseHTML = new StringBuilder();
			String strTdSeta = null;
			String strTdCampo = null;
			// Criando a tabela
			responseHTML.append( "<table width='100%' id='indicadores' class='locais' > ");
	
			AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);
			Long codARFLong = Long.valueOf(Pagina.getParamStr(request, "codARF"));

			String podeGravar = String.valueOf(Pagina.getParamStr(request, "podeGravar"));
			String statusAlteracao = new String("false");
			

			ItemEstruturaDao itemDao = new ItemEstruturaDao(request);

			AcompRealFisicoArf arf = (AcompRealFisicoArf) acompRealFisicoDao.buscar(AcompRealFisicoArf.class, codARFLong);
			ItemEstruturaIett item = arf.getItemEstruturaIett();

			ItemEstrutura itemWrapper = new ItemEstrutura(item);
						
        	List<ItemEstrutLocalIettl> locaisItem = new ArrayList<ItemEstrutLocalIettl>();
        	List<Local> locais = null;
        	
        	if(arf.getItemEstrtIndResulIettr().getNivelAbrangencia() != null){
        		locais = itemWrapper.getLocais(arf.getItemEstrtIndResulIettr().getNivelAbrangencia().intValue());
        		
        		if(locais != null && locais.size() > 0){
        			for(Local local: locais){
        				ItemEstrutLocalIettl itemLocal = new ItemEstrutLocalIettl();
        				itemLocal.setLocalItemLit(local.getRealObject());
        				locaisItem.add(itemLocal);
        			}
        		}			
        		
        	}
       	
    		if(arf.getItemEstrtIndResulIettr().getNivelAbrangencia() == null){
				responseHTML.append( "<tr> ");
				responseHTML.append( "	</td colspan='2'> ");		
				responseHTML.append( "		Não foi selecionado o nível de abrangência para esse indicador de resultado do item.");		
				responseHTML.append( "	</td> ");		
				responseHTML.append( "</tr> ");		
    		}
    		else    		
			if (locaisItem.isEmpty()){
				responseHTML.append( "<tr> ");
				responseHTML.append( "	</td colspan='2'> ");		
				responseHTML.append( "		Nenhum local foi encontrado para esse item.");		
				responseHTML.append( "	</td> ");		
				responseHTML.append( "</tr> ");		
				
			} else {
		
			
				// Inserindo local inicial e período
				responseHTML.append("<tr>");
				
				// Criar uma célula contendo o nome do local
				strTdSeta = new String (
												"<td align='rigth' width='50%' height='45px'  valign='center'>" +
												"	<table width='100%' valign='left' border='0'> " +
												"		<tr>" +
												"			<td width='10%' id='indicadores' class='locais' align='left'>" +
												"				<img src='../../images/icon_seta_ident.gif'/>" +
												"			</td>" +
												"			<td width='90%' id='indicadores' class='locais' >" +
												"				Locais " +
												"				&nbsp;" + 
												"			</td>" +
												"		</tr>" +
												"	</table>" +
												"</td>"
				);
				responseHTML.append(strTdSeta);
				responseHTML.append( "			<td width='50%' height='45px' id='indicadores' class='locais'   align='center' > ");
				responseHTML.append( "			" + arf.getMesArf().toString() + "/" + arf.getAnoArf().toString());
				responseHTML.append( "			</td> ");
				responseHTML.append( "		</tr> ");
				
     			// Inserindo a caixa de seleção			
	    		responseHTML.append( "	<tr> ");
				responseHTML.append( "		<td width='50%' id='indicadores' class='locaisDesc'  align='left' > ");
					
				// Para cada local
				Collections.sort(locaisItem);			
					
				AcompRealFisicoLocalDao arflDAO = new AcompRealFisicoLocalDao(request);
				
				for (Iterator iterator = locaisItem.iterator(); iterator.hasNext();) {
	
					    ItemEstrutLocalIettl itemLocal = (ItemEstrutLocalIettl) iterator.next();
					    LocalItemLit local = itemLocal.getLocalItemLit();
						AcompRealFisicoLocalArfl arfl = arflDAO.getAcompRealFisicoLocalByLocal(arf, local);
//						if (arfl.getQuantidadeArfl() == null){
//							arfl.setQuantidadeArfl(0.0);
//						}
						
					
						responseHTML.append("<tr>");
						
						String nomeLocal = "";
						Local localLabel = new Local(itemLocal.getLocalItemLit());
						if(localLabel.getAbrangencia() == Local.MUNICIPIO){
							//pega o simbolo do estado
							//testa antes, por que pode haver erros no banco de
							//dados, ou seja, um município sem estado...
							if(localLabel.getPais() != null && localLabel.getPais().size() > 0){
								nomeLocal = localLabel.getPais().get(0).getSigla().concat(" ");
							}
							
							nomeLocal = nomeLocal.concat(itemLocal.getLocalItemLit().getIdentificacaoLit());
						}else{
							nomeLocal = itemLocal.getLocalItemLit().getIdentificacaoLit();
						}

						
						// Criar uma célula contendo o nome do local
						strTdSeta = new String (
												"<td align='rigth' width='50%' height='30px' valign='center'>" +
												"	<table width='100%' valign='center'> " +
												"		<tr>" +
												"			<td width='25%' align='right'>" +
												"				<img src='../../images/icon_seta_ident.gif'/>" +
												"			</td>" +
												"			<td width='75%'>" +
												"				" + nomeLocal +
												"				&nbsp;" + 
												"			</td>" +
												"		</tr>" +
												"	</table>" +
												"</td>"
						);
						responseHTML.append(strTdSeta);
				
						// Criar uma célula contendo um campo e uma imagem de informação.
						if (podeGravar.equals("S")){
							statusAlteracao = "true";
						}
						else{
							statusAlteracao = "false";
						}
						
						String valorInput = "";

						if (arfl.getQuantidadeArfl() != null){
							valorInput = "		value=\"" + formatBrasil.format(arfl.getQuantidadeArfl()) + "\"";
						}						
						
						strTdCampo = new String (
												"<td width=\"50%\" align=\"center\" valign=\"center\">" +
												"	<input " +
												"		type=\"text\"" +
												"		size=\"13\"" +
												"		onblur=\"" +
												"			calcularTotal();" +
												"		\"" +
												" 		class=\"realizadoPorLocal\" " +
												"		id=\"cmp|" + local.getCodLit().toString() + "\"" +
												"		name=\"cmp|" + local.getCodLit().toString() + "\"" +
												valorInput  +
//												"		readOnly=\"" + statusAlteracao + "\""+
														 disabledString(podeGravar)  +
												"	/>" +
												"	" +
												"	<label " +
												"		id=\"label|" + local.getCodLit().toString() + "\"" +
												" 		class=\"dica\" " + 
												"		onmouseover=\"" +
												"			montaTooltipMouseOver(this,\'"+ arf.getMesArf().toString() + "/" + arf.getAnoArf().toString() + "\', \'" + local.getCodLit().toString() + "\',\'" + local.getIdentificacaoLit() + "\');" +
												"		\"" +
												"		onmouseout=\"" +
												"			montaTooltipMouseOut(\'"+ local.getCodLit().toString() +  "\');" +
												"		\"" +
												"	/>" +
												"		<img " +
												"			src=\"" + request.getContextPath() + "/images/dica.png\" " +
												"			align=\"absmiddle\" " +
												"			border=\"0\" " +
												"			onclick=\"" +
												"				montaTooltipMouseClick(\'" + arf.getMesArf().toString() + "/" + arf.getAnoArf().toString() + "\',\'"+ local.getCodLit().toString() +  "\',\'" + local.getIdentificacaoLit() + "\');" +
												"			\" " +
												"		/> " +
												"		" +
												"		<span id=\"spanLocal|" + local.getCodLit().toString() + "\" ></span>" +
												"		" +
												"	</label>" +
												"</td>"
						);
						responseHTML.append(strTdCampo);
						responseHTML.append( "</tr> ");	
						
					}
					responseHTML.append( "		<tr> ");	
					responseHTML.append( "			<td width='50%' height='45px' id='indicadores' class='locais' align='center' > ");
					responseHTML.append( "				Total por Período" );
					responseHTML.append( "			</td> ");
					responseHTML.append( "			<td width='50%' height='45px' id='indicadores' class='locais' align='center' > ");
					if(arf.getQtdRealizadaArf() == null) {
						responseHTML.append( "				<input type='text' name='total' id='total' class=\"total\" readOnly='true' size='15' value='" + formatBrasil.format( new Double(0.0)) + "' " + disabledString(podeGravar) + "  />" );
					} else {
						responseHTML.append( "				<input type='text' name='total' id='total' class=\"total\" readOnly='true' size='15' value='" + formatBrasil.format( arf.getQtdRealizadaArf()) + "'" + disabledString(podeGravar) + "  />" );
					}
					responseHTML.append( "			</td> ");
					responseHTML.append( "		</tr> ");
				}
			responseHTML.append( "</table> ");
			responseToHTML(response, responseHTML.toString());
		
		}catch(Exception e){
			throw new ServletException(e);
		}
	}

	/**
	 * Retorna a string disabled="true", caso não seja
	 * possível gravar os dados. Alguns browser entendem
	 * disable="qualquer coisa" como desabilitado.
	 * 
	 *  
	 * @param arg
	 * @return
	 */
	private String disabledString(String arg){
		
		if(arg.equals("N")){
			return "disabled=\"true\"";
		}else{
			return "";
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
