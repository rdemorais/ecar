package ecar.servlet.componente;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import comum.util.Pagina;

import ecar.dao.ExercicioDao;
import ecar.dao.ItemEstrtIndResultLocalIettirlDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.LocalItemDao;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstrtIndResulLocalIettirl;
import ecar.pojo.ItemEstrutLocalIettl;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.LocalGrupoLgp;
import ecar.pojo.LocalItemLit;

/**
 *
 * @author 70744416353
 */
public class PesquisaLocalServlet extends HttpServlet{

	private static final long serialVersionUID = 2244076567158750862L;
	private Logger logger = Logger.getLogger(this.getClass());
	
	//	constante de base de dados - Periodo de 2008 - 2011
	//private final Long CONSTANTE_PERIODO_EXERCICIO = Long.valueOf(2);
	
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
		final DecimalFormat formatBrasil = new DecimalFormat ("#,##0.##########", new DecimalFormatSymbols (new Locale ("pt", "BR")));
		try{
			StringBuilder responseHTML = new StringBuilder();
			Long paramGrupo = Pagina.getParamLong(request, "grupo");
			Long codIettir = Pagina.getParamLong(request, "codIettir");
			Long codIett = Pagina.getParamLong(request, "codIett");
			Long itemSelecionado = Pagina.getParamLong(request, "itemSelecionado");
			
			final LocalItemDao daoItem = new LocalItemDao(request);
			final ExercicioDao exercicioDao = new ExercicioDao(request);
			
			List localList = daoItem.getLocalItemPorLocalGrupo(paramGrupo);
			List listaExercicios = exercicioDao.getExerciciosValidos(Long.valueOf( codIett ));

			ItemEstruturaDao itemDao = new ItemEstruturaDao(request);

			ItemEstruturaIett item = (ItemEstruturaIett)itemDao.buscar(ItemEstruturaIett.class, Long.valueOf( codIett ));
			List locaisItem = new ArrayList(item.getItemEstrutLocalIettls());
			LocalGrupoLgp localGrupoItem = ((ItemEstrutLocalIettl)locaisItem.get(0)).getLocalItemLit().getLocalGrupoLgp();
			
			// Se a lista de locais não estiver vazia
			if (!localList.isEmpty() && !localList.isEmpty()){
				final int larguraLocal = 250;
				final int larguraCampo = 150;
				final int larguraTotal = 200;
				final int cont=listaExercicios.size();
				final int larguraCaixaTexto;
				if (80 / cont > 15) {
					larguraCaixaTexto = 15;	
				}
				else{
					larguraCaixaTexto = (80 / cont );
				}
				
				
				// Criando a tabela
				responseHTML.append( "<table width=" + (larguraLocal + cont*larguraCampo + larguraTotal) + "class='layoutFixo' bgcolor='#EAEEF4' style='table-layout: fixed; text-align: left; margin-left: auto; margin-right: auto;' border='0' cellpadding='0' cellspacing='0' > ");

				// Inserindo a checkbox superior
				responseHTML.append( "	<tr> ");
				responseHTML.append( "		<td colspan=" + (cont + 2) + "valign='top' height='25px'> "); 
				responseHTML.append( "			<input type='checkbox' name='copiarQtde1' id='copiarQtde1' value='true' onchange='checkboxSincroniza(\"copiarQtde1\",\"copiarQtde2\");' > Copiar Quantidade(s) para o(s) exercício(s) posterior(es)? ");
				responseHTML.append( "			<label ");
				responseHTML.append( "				id=\"label|-1|-1\"");
				responseHTML.append( " 				class=\"dica\" ");
				responseHTML.append( "				onmouseover=\"" );
				responseHTML.append( "					montaTooltipMouseOver(this,\'-1\', \'\', \'-1\', \'\');" );
				responseHTML.append( "				\"" );
				responseHTML.append( "				onmouseout=\"" );
				responseHTML.append( "					montaTooltipMouseOut(\'-1\', \'-1\');" );
				responseHTML.append( "				\"" );
				responseHTML.append( "			/>" );
				responseHTML.append( "				<img " );
				responseHTML.append( "					src=\"" + request.getContextPath() + "/images/dica.png\" " );
				responseHTML.append( "					align=\"absmiddle\" " );
				responseHTML.append( "					border=\"0\" " );
				responseHTML.append( "					onclick=\"" );
				responseHTML.append( "						montaTooltipMouseClick(\'-1\', \'\', \'-1\', \'\');" );
				responseHTML.append( "					\" " );
				responseHTML.append( "				/> " );
				responseHTML.append( "			" );
				responseHTML.append( "				<span id=\"span|-1|-1\" ></span>" );
				responseHTML.append( "			" );
				responseHTML.append( "			</label>" );
				responseHTML.append( "		</td> ");
				responseHTML.append( "	</tr> ");

				// Inserindo o título Exercício
				responseHTML.append( "	<tr> ");
				responseHTML.append( "		<td width=" + larguraLocal + " style='font-size: 14px;color: #003366;font-weight: bold;'  align='center' >&nbsp; </td>");
				responseHTML.append( "		<td  colspan=\"" + (cont + 1) + "\" style=\"font-size: 14px;color: #003366;font-weight: bold;\"  align=\"center\" >Exercicio</td>"); 
				responseHTML.append( "	</tr> ");

				// Inserindo os títulos Local, exercícios e Total por Local	
				responseHTML.append( "	<tr> ");
				responseHTML.append( "		<td width=" + larguraLocal + " style='font-size: 14px;color: #003366;font-weight: bold;'  align='center' >Local</td> ");					
				for (Iterator iterator = listaExercicios.iterator(); iterator.hasNext();) {
					ExercicioExe exe = (ExercicioExe) iterator.next();
					responseHTML.append( "	<td width=" + larguraCampo + " style=\"font-size: 14px;color: #003366;font-weight: bold;\"  align=\"center\" > ");
					responseHTML.append(exe.getDescricaoExe().toUpperCase() );
					responseHTML.append( "	</td> ");
				}	
				responseHTML.append( "		<td width=" + larguraTotal + " style='font-size: 14px;color: #003366;font-weight: bold;'  align='center' >Total por Local</td> ");
				responseHTML.append( "	<tr> ");

				// Inserindo a caixa de seleção
				Set subgrupo = localGrupoItem.getLocalGrupoHierarquiaLgphsByCodLgpPai();
				if ( subgrupo!=null && !subgrupo.isEmpty()){
					responseHTML.append( "	<tr> ");
					responseHTML.append( "		<td width=" + larguraLocal + " style='font-size: 12px;color: #003366;'  align='left' > ");
					responseHTML.append( "		<table width='90%'> ");
					responseHTML.append( "			<tr>" );
					responseHTML.append( "				<td width='25%' align='right'>" );
					responseHTML.append( "					<img src='../../images/icon_seta_ident.gif'/>" );
					responseHTML.append( "				</td>" );
					responseHTML.append( "				<td width='75%'>" );
					responseHTML.append( "					<select name='subgrupo' id='subgrupo' onchange='carregarlocal();'  > ");
					
					int indice = 1;
					responseHTML.append( "						<option value=" + localGrupoItem.getCodLgp() + " >Selecione</option> ");
					for (Iterator itGrupo = subgrupo.iterator(); itGrupo.hasNext();) {
						LocalGrupoLgp grupo = (LocalGrupoLgp) itGrupo.next();
						if(indice == itemSelecionado){
							responseHTML.append( "				<option value='" + grupo.getCodLgp() + "' selected='true'>" + grupo.getIdentificacaoLgp() + "</option>" );
						}
						else {
							responseHTML.append( "				<option value='" + grupo.getCodLgp() + "' >" + grupo.getIdentificacaoLgp() + "</option>" );
						}
						indice++;
					}
					responseHTML.append( "					</select> ");
					responseHTML.append( "					</td>" );
					responseHTML.append( "				</tr>" );
					responseHTML.append( "			</table>" );
					
					responseHTML.append( "		</td> ");					
					responseHTML.append( "   	<td colspan=" + (cont +1) + "valign='top' height='25px'> &nbsp; </td> ");
					responseHTML.append( "	</tr> ");
				}
		
				
				// Inserindo o conteúdo da tabela
				String strTdSeta = null; 
				String strTdCampo = null;
				String strTdTotalPorLocal = null;
				
				Double totalLocal = 0.0;
				Double totalExercicio[] = new Double [listaExercicios.size()];
				Double totalGeral = 0.0;
				int i = 0;
				for(i = 0; i < totalExercicio.length; i++){
					totalExercicio[i] = 0.0;
				}
				
				
				// Para cada local
				for (Iterator iter = localList.iterator(); iter.hasNext();) {
					LocalItemLit local = (LocalItemLit) iter.next();
					
					// Criar uma célula com a seta e o nome do local
					strTdSeta = new String (
								"<td align='rigth' width='" + larguraLocal + "' height='25px' valign='top'>" +
								"	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
								"	<table width='100%'> " +
								"		<tr>" +
								"			<td width='25%' align='right'>" +
								"				<img src='../../images/icon_seta_ident.gif'/>" +
								"			</td>" +
								"			<td width='75%'>" +
								"				" + local.getIdentificacaoLit() +
								"				&nbsp;" + 
								"			</td>" +
								"		</tr>" +
								"	</table>" +
								"</td>"
					);
					responseHTML.append("<tr>");
					responseHTML.append(strTdSeta);
					
					totalLocal = 0.0;
					i = 0;
					// Para cada exercício
					for (Iterator iterator = listaExercicios.iterator(); iterator.hasNext();) {
						ExercicioExe exe = (ExercicioExe) iterator.next();
						ItemEstrtIndResulLocalIettirl itemEstrtIndResulLocalIettirl = dao.getQtdePrevistaByLocal( codIettir, exe.getCodExe(), local.getCodLit() );
		
						totalLocal += itemEstrtIndResulLocalIettirl.getQtdPrevistaIettirl().doubleValue();
						totalExercicio [i] += itemEstrtIndResulLocalIettirl.getQtdPrevistaIettirl().doubleValue();
						i++;
						
						//if (local.getCodLit().intValue() == 418){
							
							
						// Criar uma célula contendo um campo e uma imagem de informação.
						strTdCampo = new String (
									"<td width=\"" + larguraCampo + "\" align=\"center\" >" +
									"	<input " +
									"		type=\"text\"" +
									"		size=\"" + (larguraCaixaTexto - 3) + "\"" +
									"		onblur=\"" +
									"			copiarExercicio('" + local.getCodLit().toString() + "'); " +
									"			calcularTotalLocal('" + local.getCodLit().toString() + "');" +
									"			calcularTotalExercicio('"+exe.getCodExe().toString() + "');" +
									"		\"" +
									"		id=\"cmp|" + local.getCodLit().toString() + "|" + exe.getCodExe().toString() + "\"" +
									"		name=\"cmp|" + local.getCodLit().toString() + "|" + exe.getCodExe().toString() + "\"" +
									"		value=\"" + formatBrasil.format(itemEstrtIndResulLocalIettirl.getQtdPrevistaIettirl().doubleValue()) + "\"" +
									"	/>" +
									"	" +
									"	<label " +
									"		id=\"label|" + local.getCodLit().toString() + "|" + exe.getCodExe().toString() + "\"" +
									" 		class=\"dica\" " +
									"		onmouseover=\"" +
									"			montaTooltipMouseOver(this,\'"+ exe.getCodExe().toString() +  "\',\'" + exe.getDescricaoExe() +"\',\'"+ local.getCodLit().toString() +  "\',\'" + local.getIdentificacaoLit() +"\');" +
									"		\"" +
									"		onmouseout=\"" +
									"			montaTooltipMouseOut(\'"+ exe.getCodExe().toString() +  "\',\'" + local.getCodLit().toString() +"\');" +
									"		\"" +
									"	/>" +
									"		<img " +
									"			src=\"" + request.getContextPath() + "/images/dica.png\" " +
									"			align=\"absmiddle\" " +
									"			border=\"0\" " +
									"			onclick=\"" +
									"				montaTooltipMouseClick(\'"+ exe.getCodExe().toString() +  "\',\'" + exe.getDescricaoExe() +"\',\'"+ local.getCodLit().toString() +  "\',\'" + local.getIdentificacaoLit() +"\');" +
									"			\" " +
									"		/> " +
									"		" +
									"		<span id=\"span|" + local.getCodLit().toString() + "|" + exe.getCodExe().toString() + "\" ></span>" +
									"		" +
									"	</label>" +
									"</td>"
						);
						responseHTML.append(strTdCampo);
					}
					
					// Criar uma célula de total por local
					strTdTotalPorLocal = new String(
							"<td align=\"center\" width=\"" + larguraTotal + "\" >" +
							"	<input " +
							"		type=\"text\"" +
							"		size=\"12\"" +
							"		readonly=\"true\"" +
							"		id=\"cmp|" + local.getCodLit().toString() + "|total\"" +
							"		name=\"cmp|" + local.getCodLit().toString() + "|total\"" +
							"		value=\"" + formatBrasil.format(totalLocal) + "\"" + 
							"       style=\"background-color:#FFF9DC;\"" +
							"	/>" +
							"</td>"
					);
					responseHTML.append(strTdTotalPorLocal);
					responseHTML.append("</tr>");
					
					//}// if 418

				}

				responseHTML.append( "	<tr> ");
				responseHTML.append( "		<td width=" + larguraLocal + " style='font-size: 14px;color: #003366;font-weight: bold;'  align='center' >Total por Exercício</td> ");		

				// Inserindo os totais por exercício e o total geral
				i = 0;
				for (Iterator iterator = listaExercicios.iterator(); iterator.hasNext();) {
					ExercicioExe exe = (ExercicioExe) iterator.next();
					//responseHTML.append( "	<td width=" + larguraCampo + " align=\"center\"><input type=\"text\" style=\"background-color:#FFF9DC;\"  size=\"" + larguraCaixaTexto + "\" id=\"total"+exe.getCodExe().toString() +"\" name=\"total"+exe.getCodExe()+"\" value=" + formatBrasil.format(totalExercicio[i]) + " readonly=\"true\" /></td>");
					responseHTML.append( "	<td width=" + larguraCampo + " align=\"center\"><input type=\"text\" style=\"background-color:#FFF9DC;\"  size=\"" + larguraCaixaTexto + "\" id=\"qtdPrevistaIettf"+exe.getCodExe().toString() +"\" name=\"qtdPrevistaIettf"+exe.getCodExe()+"\" value=" + formatBrasil.format(totalExercicio[i]) + " readonly=\"true\" /></td>");
					
					totalGeral += totalExercicio[i]; 
					i++;
				}	
				responseHTML.append( "		<td widht=" + larguraTotal + " align='center'><input type=\"text\" style=\"background-color:#FFF9DC;\"    size='12'  id='totalGeral' style='background-color:#FFF9DC;' name='totalGeral' value='" + formatBrasil.format(totalGeral) + "' readonly='true' /></td> ");
				responseHTML.append( "	</tr> ");

				// Inserindo a checkbox inferior
				responseHTML.append( "	<tr> ");
				responseHTML.append( "		<td colspan=" + (cont + 2) + "valign='top' height='25px'> "); 
				responseHTML.append( "			<input type='checkbox' name='copiarQtde2' id='copiarQtde2' value='true' onchange='checkboxSincroniza(\"copiarQtde2\",\"copiarQtde1\");' > Copiar Quantidade(s) para o(s) exercício(s) posterior(es)? ");
				responseHTML.append( "			<label ");
				responseHTML.append( "				id=\"label|-2|-2\"");
				responseHTML.append( " 				class=\"dica\" ");
				responseHTML.append( "				onmouseover=\"" );
				responseHTML.append( "					montaTooltipMouseOver(this,\'-2\', \'\', \'-2\', \'\');" );
				responseHTML.append( "				\"" );
				responseHTML.append( "				onmouseout=\"" );
				responseHTML.append( "					montaTooltipMouseOut(\'-2\', \'-2\');" );
				responseHTML.append( "				\"" );
				responseHTML.append( "			/>" );
				responseHTML.append( "				<img " );
				responseHTML.append( "					src=\"" + request.getContextPath() + "/images/dica.png\" " );
				responseHTML.append( "					align=\"absmiddle\" " );
				responseHTML.append( "					border=\"0\" " );
				responseHTML.append( "					onclick=\"" );
				responseHTML.append( "						montaTooltipMouseClick(\'-1\', \'\', \'-1\', \'\');" );
				responseHTML.append( "					\" " );
				responseHTML.append( "				/> " );
				responseHTML.append( "			" );
				responseHTML.append( "				<span id=\"span|-2|-2\" ></span>" );
				responseHTML.append( "			" );
				responseHTML.append( "			</label>" );
				responseHTML.append( "		</td> ");
				responseHTML.append( "	</tr> ");	
				responseHTML.append( "</table> ");
			}else{
				responseHTML.append("Nenhum registro encontrado");
			}
			responseToHTML(response, responseHTML.toString());
		}catch(Exception e){
			e.printStackTrace(System.out);
			logger.error(e);
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
