package ecar.taglib.util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import ecar.api.facade.ItemEstrutura;
import ecar.api.facade.Local;
import ecar.dao.ItemEstrtIndResulDao;
import ecar.dao.ItemEstrtIndResultLocalIettirlDao;
import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrtIndResulLocalIettirl;
import ecar.pojo.ItemEstrutFisicoIettf;
import ecar.pojo.ItemEstrutLocalIettl;
import ecar.pojo.ItemEstruturaIett;

public class GridLocaisItemTag extends TagSupport {

	private static final long serialVersionUID = -2323257145010394703L;
	
	private String codItemEstrutura;
	private String codItemIndicadorResultado;
	private String ano;
	private String tipo;
	private String nivelAbrangencia;
	
	private String meses[] = {"Jan", "Fev", 
            "Mar", "Abr", "Mai", "Jun", 
            "Jul", "Ago", "Set", "Out",
	      "Nov", "Dez"};	
	
	public String getNivelAbrangencia() {
		return nivelAbrangencia;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setNivelAbrangencia(String nivelAbrangencia) {
		this.nivelAbrangencia = nivelAbrangencia;
	}

	private int cont;
	private int larguraCaixaTexto;

	private HttpServletRequest request;
	
	private Logger logger = Logger.getLogger(this.getClass());
	

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getCodItemIndicadorResultado() {
		return codItemIndicadorResultado;
	}

	public void setCodItemIndicadorResultado(String codItemIndicadorResultado) {
		this.codItemIndicadorResultado = codItemIndicadorResultado;
	}

	public String getCodItemEstrutura() {
		return codItemEstrutura;
	}

	public void setCodItemEstrutura(String codItemEstrutura) {
		this.codItemEstrutura = codItemEstrutura;
	}

	public GridLocaisItemTag(){
    	super();
     }

     public int doStartTag() throws JspException {

    		final int larguraLocal = 250;
    		final int larguraCampo = 150;
    		final int larguraTotal = 200;
    		final int anoInt = Integer.valueOf(ano);    		
    		final long codItemIndicador = Long.valueOf(codItemIndicadorResultado);
    	    final DecimalFormat formatBrasil;
    	    if("Q".equals(this.tipo))
    	    	formatBrasil = new DecimalFormat ("#,##0.##########", new DecimalFormatSymbols (new Locale ("pt", "BR")));
    	    else
    	    	formatBrasil = new DecimalFormat ("#,##0.00", new DecimalFormatSymbols (new Locale ("pt", "BR")));
    	    Double valorPrevisto = 0D;
    	    int i = 0;
    	    
    	    
            ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
    		ItemEstrtIndResultLocalIettirlDao dao = new ItemEstrtIndResultLocalIettirlDao(request);
            try {
            	ItemEstruturaIett item = (ItemEstruturaIett)itemDao.buscar(ItemEstruturaIett.class, Long.valueOf(codItemEstrutura));
            	
            	
            	ItemEstrutura itemWrapper = new ItemEstrutura(item);
            	String str = nivelAbrangencia;
            	
        		long abrangencia = Long.valueOf(str);
        		
            	//se a abrangencia é inválida, pega-se a do item como default
//            	long abrangencia = itemWrapper.getAbrangencia(); 
//            	
//            	try {
//            		abrangencia = Long.valueOf(str);
//                	if(GrupoLocal.getInstance().getNomeGrupo((int)abrangencia).equals(GrupoLocal.NAO_DEFINIDO)){
//                		abrangencia = itemWrapper.getAbrangencia();
//                	}
//            	}catch(Exception e){
//            		//não faz nada, em caso de erro a abrangência adotada é a 
//            		//mesma do item
//            	}
            	
            	
            	List<ItemEstrutLocalIettl> locaisItem = new ArrayList<ItemEstrutLocalIettl>();
            	List<Local> locais = null;
            	
            	if(itemWrapper.getAbrangencia() != abrangencia){
            		locais = itemWrapper.getLocais((int)abrangencia);
            	}else{
            		locais = itemWrapper.getLocais();
            	}
           	

        		if(locais != null && locais.size() > 0){
        			for(Local local: locais){
        				ItemEstrutLocalIettl itemLocal = new ItemEstrutLocalIettl();
        				itemLocal.setLocalItemLit(local.getRealObject());
        				locaisItem.add(itemLocal);
        			}
        		}

            	
				List<GregorianCalendar> listaMeses = itemDao.GetMesesReferenciaFiltraAno(item, Long.valueOf(ano));				
				String strTdSeta = null; 
				String strTdCampo = null;
				String strTdTotalPorLocal = null;
				Locale localePTBR = new Locale ("pt", "BR");				
				

				ItemEstrtIndResulDao itemEstrtIndResulDao = new ItemEstrtIndResulDao(request);
				ItemEstrtIndResulIettr itemEstrtIndResulOriginal = (ItemEstrtIndResulIettr) itemEstrtIndResulDao.buscar(ItemEstrtIndResulIettr.class, codItemIndicador);

				/* Separa, de todos itens físicos do item, somente aqueles pertencente ao ano manipulado*/
				ArrayList<ItemEstrutFisicoIettf> listaAnualItemFisico = new ArrayList<ItemEstrutFisicoIettf>();
				
				
				/*Por mes, terá os locais de cada item
				 * Isso para não ter de ir toda hora ao BD buscar valores, como era feito anteriormente */
				ItemEstrutFisicoIettf itemFisicoMes;				
					
				
				if (itemEstrtIndResulOriginal.getItemEstrutFisicoIettfs() != null)
					{
						 Iterator<ItemEstrutFisicoIettf> it = itemEstrtIndResulOriginal.getItemEstrutFisicoIettfs().iterator();
						 while (it.hasNext()){
							 ItemEstrutFisicoIettf itemF = (ItemEstrutFisicoIettf) it.next();
							 
							 if ((itemF.getAnoIettf().equals(anoInt) )&&(itemF.getItemEstrtIndResulLocalIettirls() != null)){
								 listaAnualItemFisico.add(itemF);
							 }
						 }
					}				
				
				
				
				if ((locaisItem  != null)&&(listaMeses.size() > 0))
				{

					cont = listaMeses.size();	
					if (80 / cont > 15) {
						larguraCaixaTexto = 15;	
					}
					else{
						larguraCaixaTexto = (80 / cont );
					}
					
					request = (HttpServletRequest) (pageContext.getRequest());

					pageContext.getOut().println("<table class='layoutFixo' bgcolor='#EAEEF4' style='table-layout: fixed; text-align: left; margin-left: auto; margin-right: auto;' border='0' cellpadding='0' cellspacing='0' > ");

					pageContext.getOut().println ( "	<tr> ");
					pageContext.getOut().println( "		<td colspan=" + (cont + 2) + " valign='top' height='25px'> "); 
					pageContext.getOut().println( "			<input type='checkbox' name='copiarQtd1' id='copiarQtd1' value='true' onchange='checkboxSincroniza(\"copiarQtd1\",\"copiarQtd2\");' > Copiar Quantidade(s) para o(s) mes(es) posterior(es)? ");
					pageContext.getOut().println( "			<label ");
					pageContext.getOut().println( "				id=\"label|-1|-1\"");
					pageContext.getOut().println( " 				class=\"dica\" ");
					pageContext.getOut().println( "				onmouseover=\"" );
					pageContext.getOut().println( "					montaTooltipMouseOver(this,\'-1\', \'\', \'-1\', \'\');" );
					pageContext.getOut().println( "				\"" );
					pageContext.getOut().println( "				onmouseout=\"" );
					pageContext.getOut().println( "					montaTooltipMouseOut(\'-1\', \'-1\');" );
					pageContext.getOut().println( "				\"" );
					pageContext.getOut().println( "			/>" );
					pageContext.getOut().println( "				<img " );
					pageContext.getOut().println( "					src=\"" + request.getContextPath() + "/images/dica.png\" " );
					pageContext.getOut().println( "					align=\"absmiddle\" " );
					pageContext.getOut().println( "					border=\"0\" " );
					pageContext.getOut().println( "					onclick=\"" );
					pageContext.getOut().println( "						montaTooltipMouseClick(\'-1\', \'\', \'-1\', \'\');" );
					pageContext.getOut().println( "					\" " );
					pageContext.getOut().println( "				/> " );
					pageContext.getOut().println( "			" );
					pageContext.getOut().println( "				<span id=\"span|-1|-1\" ></span>" );
					pageContext.getOut().println( "			" );
					pageContext.getOut().println( "			</label>" );
					pageContext.getOut().println( "		</td> ");
					pageContext.getOut().println( "	</tr> ");
					
					// Inserindo o título Exercício
					pageContext.getOut().println( "	<tr> ");
					pageContext.getOut().println( "		<td style='font-size: 14px;color: #003366;font-weight: bold;'  align='center' >&nbsp; </td>");
					pageContext.getOut().println( "		<td  colspan=\"" + (cont + 1) + "\" style=\"font-size: 14px;color: #003366;font-weight: bold;\"  align=\"center\" >"+ ano.toString() +"</td>");
					pageContext.getOut().println( "	</tr> ");

					pageContext.getOut().println( "	<tr> ");
					pageContext.getOut().println( "		<td style='font-size: 14px;color: #003366;font-weight: bold;'  align='center' >Local</td> ");					
					
					for (Iterator<GregorianCalendar> iterator = listaMeses.iterator(); iterator.hasNext();) {
						GregorianCalendar mes = (GregorianCalendar) iterator.next();
						pageContext.getOut().println( "	<td style=\"font-size: 14px;color: #003366;font-weight: bold;\"  align=\"center\" >");
						pageContext.getOut().println(meses[mes.get(Calendar.MONTH)]);
						pageContext.getOut().println( "</td> ");
					}	
					pageContext.getOut().println( "		<td style='font-size: 14px;color: #003366;font-weight: bold;'  align='center' >Total por Local</td> ");
					pageContext.getOut().println( "	<tr> ");
					
					Collections.sort(locaisItem);
					
    				Iterator<ItemEstrutLocalIettl> it = locaisItem.iterator();
    				
    				Double totalLocal = 0.0;
    				Double totalExercicio[] = new Double [listaMeses.size()];
    				Double totalGeral = 0.0;
    				
    				for(i = 0; i < totalExercicio.length; i++){
    					totalExercicio[i] = 0.0;
    				}
    				
                    while(it.hasNext())
					{
						
						ItemEstrutLocalIettl itemLocal = (ItemEstrutLocalIettl) it.next();

						String nomeLocal = "";
						Local local = new Local(itemLocal.getLocalItemLit());
						if(local.getAbrangencia() == Local.MUNICIPIO){
							//pega o simbolo do estado
							//testa antes, por que pode haver erros no banco de
							//dados, ou seja, um município sem estado...
							if(local.getPais() != null && local.getPais().size() > 0){
								nomeLocal = local.getPais().get(0).getSigla().concat(" ");
							}
							
							nomeLocal = nomeLocal.concat(itemLocal.getLocalItemLit().getIdentificacaoLit());
						}else{
							nomeLocal = itemLocal.getLocalItemLit().getIdentificacaoLit();
						}
						
						// Criar uma célula com a seta e o nome do local
						strTdSeta = new String (
									"<td align='right' height='25px' valign='top'>" +
									"	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
									"	<table width='100%'> " +
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
						pageContext.getOut().println("<tr>");
						pageContext.getOut().println(strTdSeta);
						
						totalLocal = 0.0;
						i = 0;
						String valorInput = new String();						
						for (Iterator<GregorianCalendar> iterator = listaMeses.iterator(); iterator.hasNext();) {
							GregorianCalendar mes = (GregorianCalendar) iterator.next();

							
							itemFisicoMes = null;
							valorPrevisto = null;
							boolean continua = true;
							valorInput = "";
							
							
							/*Nos itens de estrutura fisico filtrado por ano,
							 * verifica se existe algum para o mês em questão.
							 * Se tiver, verifica se existe um local com valores */
							if (listaAnualItemFisico.size() > 0)
							{
								 Iterator<ItemEstrutFisicoIettf> itFisico = listaAnualItemFisico.iterator();
								 while ((itFisico.hasNext())&&(continua)){
									 ItemEstrutFisicoIettf itemF = (ItemEstrutFisicoIettf) itFisico.next();
									 
									 if ((itemF.getMesIettf().equals(mes.get(GregorianCalendar.MONTH)+1) )&&(itemF.getItemEstrtIndResulLocalIettirls() != null)){
										 Iterator<ItemEstrtIndResulLocalIettirl> itLocal = itemF.getItemEstrtIndResulLocalIettirls().iterator();
										 while(itLocal.hasNext()){
											 ItemEstrtIndResulLocalIettirl itItemLocal = (ItemEstrtIndResulLocalIettirl) itLocal.next();

											 if ((itItemLocal.getLocalItemLit().getCodLit().equals(itemLocal.getLocalItemLit().getCodLit()))&&(itItemLocal.getQtdPrevistaIettirl() != null)){
												 valorPrevisto = itItemLocal.getQtdPrevistaIettirl();
												 continua = false;
												 break;
											 }
										 }
									 }
								 }
							}				
							
							if (valorPrevisto != null){
								totalLocal += valorPrevisto;
								totalExercicio [i] += valorPrevisto;
								
								valorInput = "		value=\"" + formatBrasil.format(valorPrevisto) + "\"";
							}
							i++;
							
							// Criar uma célula contendo um campo e uma imagem de informação.
							strTdCampo = new String (
										"<td align=\"center\" >" +
										"	<input " +
										"		class=\"inputPopup previsto" +String.format("%02d", mes.get(GregorianCalendar.MONTH)+1) + "\""+
										"		type=\"text\"" +
										"		size=\"" + (larguraCaixaTexto - 3) + "\"" +
										"		onblur=\"" +
										"			copiarExercicio('" + itemLocal.getLocalItemLit().getCodLit().toString() + "'); " +
										"			calcularTotalLocal('" + itemLocal.getLocalItemLit().getCodLit().toString() + "');" +
										"			calcularTotalExercicio('"+String.format("%02d", mes.get(GregorianCalendar.MONTH)+1) + "');" +
										"		\"" +
										"		id=\"cmp|" + itemLocal.getLocalItemLit().getCodLit().toString() + "|" + String.format("%02d", mes.get(GregorianCalendar.MONTH)+1) + "\"" +
										"		name=\"cmp|" + itemLocal.getLocalItemLit().getCodLit().toString() + "|" + String.format("%02d", mes.get(GregorianCalendar.MONTH)+1) + "\"" +
                                        valorInput +
										"	/>" +
										"	" +
										"	<label " +
										"		id=\"label|" + itemLocal.getLocalItemLit().getCodLit().toString() + "|" + String.format("%02d", mes.get(GregorianCalendar.MONTH)+1) + "\"" +
										" 		class=\"dica\" " +
										"		onmouseover=\"" +
										"			montaTooltipMouseOver(this,\'"+ String.format("%02d", mes.get(GregorianCalendar.MONTH)+1) +  "\',\'" + meses[mes.get(java.util.Calendar.MONTH)] +"\',\'"+ itemLocal.getLocalItemLit().getCodLit().toString() +  "\',\'" + itemLocal.getLocalItemLit().getIdentificacaoLit() +"\');" +
										"		\"" +
										"		onmouseout=\"" +
										"			montaTooltipMouseOut(\'"+ String.format("%02d", mes.get(GregorianCalendar.MONTH)+1) +  "\',\'" + itemLocal.getLocalItemLit().getCodLit().toString() +"\');" +
										"		\"" +
										"	/>" +
										"		<img " +
										"			src=\"" + request.getContextPath() + "/images/dica.png\" " +
										"			align=\"absmiddle\" " +
										"			border=\"0\" " +
										"			onclick=\"" +
										"				montaTooltipMouseClick(\'"+ String.format("%02d", mes.get(GregorianCalendar.MONTH)+1) +  "\',\'" + meses[mes.get(java.util.Calendar.MONTH)] +"\',\'"+ itemLocal.getLocalItemLit().getCodLit().toString() +  "\',\'" + itemLocal.getLocalItemLit().getIdentificacaoLit() +"\');" +
										"			\" " +
										"		/> " +
										"		" +
										"		<span id=\"span|" + itemLocal.getLocalItemLit().getCodLit().toString() + "|" + String.format("%02d", mes.get(GregorianCalendar.MONTH)+1) + "\" ></span>" +
										"		" +
										"	</label>" +
										"</td>"
							);
							pageContext.getOut().println(strTdCampo);

						}						
						
						// Criar uma célula de total por local
						strTdTotalPorLocal = new String(
								"<td align=\"center\" width=\"" + larguraTotal + "\" >" +
								"	<input " +
								"		type=\"text\"" +
								"		size=\"12\"" +
								"		readonly=\"true\"" +
								"		id=\"cmp|" + itemLocal.getLocalItemLit().getCodLit().toString() + "|total\"" +
								"		name=\"cmp|" + itemLocal.getLocalItemLit().getCodLit().toString() + "|total\"" +
								"		value=\"" + formatBrasil.format(totalLocal) + "\"" + 
								"       style=\"background-color:#FFF9DC;\"" +
								"	/>" +
								"</td>"
						);
						pageContext.getOut().println(strTdTotalPorLocal);
						
						pageContext.getOut().println("</tr>");						
					
					}
                    
                    
                    pageContext.getOut().println( "	<tr> ");
                    pageContext.getOut().println( "		<td style='font-size: 14px;color: #003366;font-weight: bold;'  align='center' >Total por Mês</td> ");		

                    i = 0;
    				// Inserindo os totais por exercício e o total geral
					for (Iterator<GregorianCalendar> iterator = listaMeses.iterator(); iterator.hasNext();) {
						GregorianCalendar mes = (GregorianCalendar) iterator.next();    
    					pageContext.getOut().println( "	<td align=\"center\"><input type=\"text\" style=\"background-color:#FFF9DC;\"  size=\"" + larguraCaixaTexto + "\" id=\"qtdPrevistaIettf"+String.format("%02d", mes.get(GregorianCalendar.MONTH)+1) +"\" name=\"qtdPrevistaIettf"+String.format("%02d", mes.get(GregorianCalendar.MONTH)+1)+"\" value=" + formatBrasil.format(totalExercicio[i]) + " readonly=\"true\" /></td>");
    					
    					totalGeral += totalExercicio[i]; 
    					i++;
    				}	
    				pageContext.getOut().println( "		<td align='center'><input type=\"text\" style=\"background-color:#FFF9DC;\"    size='12'  id='totalGeral' style='background-color:#FFF9DC;' name='totalGeral' value='" + formatBrasil.format(totalGeral) + "' readonly='true' /></td> ");
    				pageContext.getOut().println( "	</tr> ");                    
                    
    				// Inserindo a checkbox inferior
    				pageContext.getOut().println( "	<tr> ");
    				pageContext.getOut().println( "		<td colspan=" + (cont + 2) + " valign='top' height='25px'> "); 
    				pageContext.getOut().println( "			<input type='checkbox' name='copiarQtd2' id='copiarQtd2' value='true' onchange='checkboxSincroniza(\"copiarQtd2\",\"copiarQtd1\");' > Copiar Quantidade(s) para o(s) mes(es) posterior(es)? ");
    				pageContext.getOut().println( "			<label ");
    				pageContext.getOut().println( "				id=\"label|-2|-2\"");
    				pageContext.getOut().println( " 				class=\"dica\" ");
    				pageContext.getOut().println( "				onmouseover=\"" );
    				pageContext.getOut().println( "					montaTooltipMouseOver(this,\'-2\', \'\', \'-2\', \'\');" );
    				pageContext.getOut().println( "				\"" );
    				pageContext.getOut().println( "				onmouseout=\"" );
    				pageContext.getOut().println( "					montaTooltipMouseOut(\'-2\', \'-2\');" );
    				pageContext.getOut().println( "				\"" );
    				pageContext.getOut().println( "			/>" );
    				pageContext.getOut().println( "				<img " );
    				pageContext.getOut().println( "					src=\"" + request.getContextPath() + "/images/dica.png\" " );
    				pageContext.getOut().println( "					align=\"absmiddle\" " );
    				pageContext.getOut().println( "					border=\"0\" " );
    				pageContext.getOut().println( "					onclick=\"" );
    				pageContext.getOut().println( "						montaTooltipMouseClick(\'-1\', \'\', \'-1\', \'\');" );
    				pageContext.getOut().println( "					\" " );
    				pageContext.getOut().println( "				/> " );
    				pageContext.getOut().println( "			" );
    				pageContext.getOut().println( "				<span id=\"span|-2|-2\" ></span>" );
    				pageContext.getOut().println( "			" );
    				pageContext.getOut().println( "			</label>" );
    				pageContext.getOut().println( "		</td> ");
    				pageContext.getOut().println( "	</tr> ");	
    				pageContext.getOut().println( "</table> ");                    
				}
				else
				{
					pageContext.getOut().println("Nenhum registro encontrado");					
				}
				 
				
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
				logger.error(e);				
			} catch (ECARException e) {
				e.printStackTrace();
				logger.error(e);
				logger.error(e);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e);				
			}


        return SKIP_BODY;
     }

} 

