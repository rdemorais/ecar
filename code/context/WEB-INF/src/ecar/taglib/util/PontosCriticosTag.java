/*
 * Criado em 18/09/2007
 *
 */
package ecar.taglib.util;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import comum.util.Data;
import comum.util.Pagina;

import ecar.dao.ConfigMailCfgmDAO;
import ecar.dao.ConfiguracaoDao;
import ecar.dao.CorDao;
import ecar.dao.PontocriticoCorPtccorDAO;
import ecar.dao.SisGrupoAtributoDao;
import ecar.login.SegurancaECAR;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ConfigMailCfgm;
import ecar.pojo.Cor;
import ecar.pojo.PontoCriticoPtc;
import ecar.pojo.PontocriticoCorPtccor;
import ecar.pojo.PontocriticoCorPtccorPK;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.taglib.combos.ComboTag;
import ecar.util.Dominios;


/**
 * Taglib que gera a tela de pontos críticos para um controle HTML.<br>
 * 
 * @author Milton Pereira
 */
public class PontosCriticosTag implements Tag {
	
	private PontoCriticoPtc pontoCritico;
	private String disabled;
	private String disabledCampo="";
	private String readOnly;
	private String readOnlyCampo;
	private HttpServletRequest request;
		
	private String nomeComboTag;
	private String objetoComboTag;
	private String labelComboTag;
	private String valueComboTag;
	private String orderComboTag;
	private String filtersComboTag;
	private Boolean abaPontoCriticoDeAcompanhamento = new Boolean (false);
	private AcompReferenciaItemAri ari;
	private SegurancaECAR seguranca;

	//Esta variavel eh usada apenas na nova tela de situação acompanhamento
	private String indice;

    private PageContext page = null;

    private Tag parent;
    
    private String imagem;

    /**
     *
     */
    public static final long MAXLENGTH = 200;
    
    private JspWriter writerParametro = null;
    
    /**
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public PontosCriticosTag() {
    	
    }
    
    /**
     * Atribui valor especificado para JspWriter writerParametro.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param writer
     */
    public PontosCriticosTag(JspWriter writer) {
    	this.writerParametro = writer;
    }
    
    /**
     * Inicializa a montagem da tag para ser adicionada na tela de HTML.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 * @throws JspException
     */
    public int doStartTag() throws JspException {
    	JspWriter writer = null;
    	if(writerParametro != null) {
    		writer = writerParametro;
    	} else {
        	writer = this.page.getOut();
    	}
    	String idInput = "";
    	if(getIndice() != null && !getIndice().equals(""))
			idInput = getIndice();
        StringBuffer s = new StringBuffer();
        try {
        	s.append("<table name=\"form\" class=\"form\">");
        	s.append("<tr>");
        	s.append("	<td colspan=2>");
        	s.append("		<div class=\"camposobrigatorios\">* Campos de preenchimento obrigat&oacute;rio</div>");
        	s.append("	</td>");
        	s.append("</tr>");
        	s.append("<tr>");
        	s.append("	<td class=\"label\">* Data de Registro</td>");
        	
			String dataReg = Pagina.trocaNullData(pontoCritico.getDataIdentificacaoPtc());
			if (dataReg.length() <= 0)
				dataReg = Data.parseDate(Data.getDataAtual());
			
			s.append("		<td>");
			s.append("			<input type=\"text\" name=\"dataIdentificacaoPtc"+idInput+"\" id=\"dataIdentificacaoPtc"+idInput+"\" size=\"13\" maxlength=\"10\" value=\""+dataReg+"\" onkeyup=\"mascaraData(event, this);\""+disabled +" "+ disabledCampo+">");
			if ( !disabled.equals("disabled") && !disabledCampo.equals("disabled")) {  
				s.append("			<img class=\"posicao\" title=\"Selecione a data\" src=\"../../images/icone_calendar.gif\" onclick=\"open_calendar('data', document.forms[0].dataIdentificacaoPtc, '')\""+ disabled +" "+disabledCampo+">");
			}
			
			
        	s.append("		</td>");
        	s.append("	</tr>");
        	
        	if (pontoCritico.getUsuarioUsuInclusao() != null) {
        		s.append("	<tr>");
        		s.append("		<td class=\"label\">Responsável pela Inclusão</td>");
        		s.append("		<td>");
        		s.append(pontoCritico.getUsuarioUsuInclusao().getNomeUsu()+"</td>");
        		s.append("	</tr>");	
        	}
        	
        	/*  
        	 *  Verificar o grupo de acesso que esse usuario faz parte e quais as funções
        	 *  de acompanhamento que ele pode representar
        	 *  
        	 *  Como a funcao de acompanhamento é ligada ao relatorio. O código passado é o 
        	 *  codArel (codigo d o relatorio em que o usuario está dando parecer) 
        	 *  mas o texto exibido é o label do Tipo funcao de acompanhamento.
        	 */ 
        	
//************ 30/01/2010: Comentado para retirar ligação de AcompRelatorioAreal com Pontos críticos, que resulta em um erro na geração de períodos ***********        	
//        	if (this.abaPontoCriticoDeAcompanhamento.booleanValue() ) {
//	        	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
//	        	
//
//	        	//List listTpfa = ariDao.getTipoFuncoesAcompanhamentoQueEmitemParecerNoAri(ari,seguranca.getUsuario());
//	        	List listTpfa = ariDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari);
//	        	AcompRelatorioDao arelDao = new AcompRelatorioDao(request);
//	        	int numOpcoes =0;
//	        	StringBuffer strTemp = new StringBuffer(""); 
//	        	
//	        	AcompRelatorioArel selectedArel = new AcompRelatorioArel();
//	        	if (pontoCritico!=null && pontoCritico.getAcompRelatorioArel()!=null  )
//	        		selectedArel = pontoCritico.getAcompRelatorioArel();
//	        	
//	        	if (listTpfa!=null ){
//	        		//Iterator<TipoFuncAcompTpfa> it = listTpfa.iterator();
//	        		Iterator<AcompRelatorioArel> it = listTpfa.iterator();
//	        	
//	        		s.append("	<tr>");
//	        		s.append("		<td class=\"label\">Função de Acompanhamento</td>\n");
//	        		s.append("		<td>");
//	        		s.append("			<select name=\"codArel\" "+ getDisabled() + "> \n" );
//	    			
//	        		while (it.hasNext()) {
//	        			AcompRelatorioArel arel = (AcompRelatorioArel) it.next();
//	        			if (arelDao.podeGravarRelatorio(seguranca.getUsuario(), arel.getTipoFuncAcompTpfa(), ari , arel ) == AcompRelatorioDao.OPERACAO_PERMITIDA){
//			        		String selected = "";
//			        		
//			        		if (selectedArel.equals(arel) ){
//			        			selected = "selected"; 
//			        		}
//			        		
//			        		
//			        		strTemp.append("				<option value=\"" + arel.getCodArel() + "\" "+ selected +"   > " + arel.getTipoFuncAcompTpfa().getLabelTpfa()  +" </option>\n");
//		    				numOpcoes++;
//			        	}
//	    			}
//	        		
//	        		/* Exibe uma opção vazia caso o usuario seja mais de um tipo de funcao de acompanhamento */
//	        		if (numOpcoes >1) {
//		        		s.append("				<option value=\"\">   </option>\n");
//	        		} else if (numOpcoes==0 && selectedArel!= null){
//	        			s.append("				<option value=\""+ selectedArel.getCodArel()+"\"> "+  selectedArel.getTipoFuncAcompTpfa().getLabelTpfa() +" </option>\n");
//	        			
//	        		}
//
//	        		s.append(strTemp.toString());	        		
//	        		s.append("			</select>");
//	        		s.append("		</td>");
//	        		s.append("	</tr>");	
//	        	} else {
//	        		//Redirecionar para a página de acesso indevido
//	        		
//	        	}
//        	}
        	
        	
        	SisGrupoAtributoSga sisGrupoPtc = new ConfiguracaoDao(request).getConfiguracao().getSisGrupoAtributoSgaTipoPontoCritico();
    		if (sisGrupoPtc != null){
    			s.append("	<tr>");
    			s.append("		<td class=\"label\"> " +sisGrupoPtc.getDescricaoSga()+ "</td>");
    			s.append("		<td>");
    			
    			writer.print(s);
    			
    			List tiposPontosCriticos = new SisGrupoAtributoDao().getAtributosOrdenados(sisGrupoPtc);
    			SisAtributoSatb selectedTipoPtC = pontoCritico.getSisAtributoTipo();

    			String script = disabled;
    			
    			
    			ComboTag comboTag = new ComboTag(writer);
            	comboTag.setNome(nomeComboTag);
            	comboTag.setObjeto(objetoComboTag);
            	comboTag.setLabel(labelComboTag);
            	comboTag.setValue(valueComboTag);
            	comboTag.setScripts(script);
            	comboTag.setColecao(tiposPontosCriticos);
            			
            	if (selectedTipoPtC != null){
            		comboTag.setSelected(selectedTipoPtC.getCodSatb().toString());
            	} 
            	
        	    comboTag.doStartTag();
    			
    			
    			
    			//if (selectedTipoPtC != null)
    			//{
    			/*	<combo:ComboTag 
    						nome="codSgaPontoCritico"
    						objeto="ecar.pojo.SisAtributoSatb" 
    						label="descricaoSatb" 
    						value="codSatb" 
    						order="descricaoSatb"
    						colecao="<%=tiposPontosCriticos%>"
    						selected="<%=selectedTipoPtC.getCodSatb().toString()%>"
    						scripts="<%=script%>"
    						filters="indAtivoSga=S"
    				/>*/
    			
    			//} else {
    			/*
    				<combo:ComboTag 
    					nome="codSgaPontoCritico"
    					objeto="ecar.pojo.SisAtributoSatb" 
    					label="descricaoSatb" 
    					value="codSatb" 
    					order="descricaoSatb"
    					colecao="<%=tiposPontosCriticos%>"
    					scripts="<%=script%>"
    					filters="indAtivoSga=S"
    				/>
    			*/
    			//}
        	    s = new StringBuffer();
    			s.append("		</td>");
    			s.append("	</tr>");
    					
    		}
    		s.append("	<tr>");
    		s.append("		<td class=\"label\">* Descri&ccedil;&atilde;o</td>");
    		s.append("		<td><textarea name=\"descricaoPtc"+idInput+"\" id=\"descricaoPtc"+idInput+"\" rows=\"5\" cols=\"60\""+disabled+" "+ readOnly+" "+ readOnlyCampo); 
    		s.append("			onkeyup=\"return validaTamanhoLimite(this, 2000);\"");
    		s.append("			onkeydown=\"return validaTamanhoLimite(this, 2000);\"");
    		s.append("			onblur=\"return validaTamanhoLimite(this, 2000);\">"+Pagina.trocaNull(pontoCritico.getDescricaoPtc())+"</textarea>");
    		s.append("		</td>");
    		s.append("	</tr>");
    		s.append("	<tr>");
    		s.append("		<td class=\"label\">* &Acirc;mbito</td>");
    		s.append("		<td>");
    		s.append("		<input type=\"radio\" class=\"form_check_radio\" name=\"indAmbitoInternoGovPtc"+idInput+"\" id=\"indAmbitoInternoGovPtc"+idInput+"\" value=\"I\""+((pontoCritico.getIndAmbitoInternoGovPtc()!=null)?Pagina.isChecked(pontoCritico.getIndAmbitoInternoGovPtc(), "I"):" checked") +" "+disabled +" " + disabledCampo+ "> Interno");
    		s.append("		<input type=\"radio\" class=\"form_check_radio\" name=\"indAmbitoInternoGovPtc"+idInput+"\" id=\"indAmbitoInternoGovPtc"+idInput+"\" value=\"E\""+Pagina.isChecked(pontoCritico.getIndAmbitoInternoGovPtc(), "E")+" " + disabled + " " + disabledCampo + "> Externo");
    		if(!"".equals(disabledCampo)){
    		
    			s.append("			<input type=\"hidden\" name=\"indAmbitoInternoGovPtc"+idInput+"\" id=\"indAmbitoInternoGovPtc"+idInput+"\" value=\""+pontoCritico.getIndAmbitoInternoGovPtc()+"\">");
    		
    		}
    		s.append("		</td>");
    		s.append("	</tr>");

    		s.append("	<tr>");
    		s.append("		<td class=\"label\">* Data Limite</td>");
    		s.append("		<td valign=\"middle\">");
    		s.append("			<input type=\"text\" name=\"dataLimitePtc"+idInput+"\" id=\"dataLimitePtc"+idInput+"\" size=\"13\" maxlength=\"10\" value=\""+Pagina.trocaNullData(pontoCritico.getDataLimitePtc())+"\" onkeyup=\"mascaraData(event, this);\" "+ disabled + " " +disabledCampo + ">");
    		if ( !disabled.equals("disabled") && !disabledCampo.equals("disabled")) {
    			if(idInput != null && !idInput.equals("")) {
    				s.append("		<img class=\"posicao\" title=\"Selecione a data\" src=\"../../images/icone_calendar.gif\" onclick=\"open_calendar('data', document.forms[1].dataLimitePtc"+idInput+", '')\" " + disabled + " " + disabledCampo + ">");
    			} else {
    				s.append("		<img class=\"posicao\" title=\"Selecione a data\" src=\"../../images/icone_calendar.gif\" onclick=\"open_calendar('data', document.forms[0].dataLimitePtc, '')\" " + disabled + " " + disabledCampo + ">");
    			}
    		}
    		s.append("		</td>");
			s.append("</tr>");
			s.append("<tr>");
			s.append("	<td class=\"label\">Estados e Envio de e-mails</td>");
			s.append("	<td>&nbsp;</td>");
			s.append("</tr>");
			s.append("	<tr>");
			s.append("		<td>&nbsp;</td>");
			s.append("		<td>");
			s.append("			<table border=\"1\">");
			s.append("				<tr>");
			s.append("					<td class=\"titulo\" align=\"center\" width=\"20\">Estado</td>");
			s.append("					<td class=\"titulo\" align=\"center\" width=\"20\">Antecedência (em dias)</td>");
			s.append("					<td class=\"titulo\" align=\"center\" width=\"20\">Freqüência de envio (em dias)</td>");
			s.append("					<td class=\"titulo\" align=\"center\" width=\"10\">Ativo</td>");
			s.append("				</tr><!--");			
			s.append("				<tr>");
			s.append("					<td class=\"form_label\" align=\"center\">");
			s.append("						<img src=\"../../images/pcBranco1.png\">");
			s.append("					</td>");
			s.append("					<td valign=\"middle\" colspan=\"2\" class=\"form_label\" align=\"center\">Nenhuma Ação</td>");
			s.append("					<td valign=\"middle\" class=\"form_label\" align=\"center\">&nbsp;</td>");
			s.append("				</tr>");
			s.append("				-->");
			
			PontocriticoCorPtccor ptcCor = null;
			ConfigMailCfgm configMailVencto = (ConfigMailCfgm) new ConfigMailCfgmDAO(request).buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_VENCIMENTO_LIMITE_PONTO_CRITICO);
			List setCores = new CorDao(request).listar(Cor.class, new String[]{"ordemCor","asc"});
			Cor cor = null;
			PontocriticoCorPtccorPK id = null;
			Iterator itCores = null;
			if (setCores != null)
				itCores = setCores.iterator();
			
			String imagePath = "";
			CorDao cDao = new CorDao(request);
			
			while (itCores.hasNext())
			{
				cor = (Cor) itCores.next(); 			
				id = new PontocriticoCorPtccorPK(pontoCritico.getCodPtc(), cor.getCodCor());
				ptcCor = (PontocriticoCorPtccor)new PontocriticoCorPtccorDAO(request).buscar(cor, pontoCritico);
				
				if(cor.getIndPontoCriticoCor().equals("S")){
					s.append("<tr>");
					s.append("<td valign=\"middle\"  class=\"form_label\" align=\"center\">");
					// Por Rogério (06/03/2007)
					// Modificada a forma de obtenção da imagem.
					// Referente ao Mantis #7442
					imagePath = cDao.getImagemPersonalizada(cor, null, null);
					if( imagePath != null ) {
						/* -- As tags do CSS "max-width" e "max-height" não funcionam no IE6 ou menor -- */
						s.append("<!--<img border=\"0\" src=\""+request.getContextPath()+"/DownloadFile?tipo=open&RemoteFile="+imagePath+"\" style=\"width: 23px; height: 23px;\" title=\""+cor.getSignificadoCor()+"\"> -->");
				    	s.append("<img border=\"0\" src=\""+request.getContextPath()+"/DownloadFile?tipo=open&RemoteFile="+imagePath+"\" title=\""+cor.getSignificadoCor()+"\">");
					} else {
						if( cor.getCodCor() != null ) {
							s.append("<img src=\"../../images/pc"+cor.getNomeCor()+"1.png\" title=\""+cor.getSignificadoCor()+"\">"); 
						}
					} 
					s.append("</td>");
					s.append("<td valign=\"middle\" class=\"form_label\" align=\"center\">");
					String idInputCont = "";
					if(idInput != null && !idInput.equals(""))
						idInputCont = idInput + "i";
					s.append("	<input type=\"text\" name=\"ant_"+idInputCont+cor.getCodCor()+"\" id=\"ant_"+idInputCont+cor.getCodCor()+"\" value=\""+Pagina.trocaNull(ptcCor.getAntecedenciaPrimEmailPtccor())+"\" size=\"4\" "+disabled + " >");
					if ( !disabled.equals("disabled") && !disabledCampo.equals("disabled")) {  
						if(getIndice() != null && !getIndice().equals("")) {
							s.append("<img class=\"posicao\" title=\"Selecione a data\" src=\"../../images/icone_calendar.gif\" onclick=\"open_calendar('difDias', document.forms[1].ant_"+idInputCont+cor.getCodCor()+", document.forms[1].dataLimitePtc"+getIndice()+".value)\">");
						} else {
							s.append("<img class=\"posicao\" title=\"Selecione a data\" src=\"../../images/icone_calendar.gif\" onclick=\"open_calendar('difDias', document.forms[0].ant_"+cor.getCodCor()+", document.forms[0].dataLimitePtc.value)\">");							
						}
					}
					s.append("</td>");
					s.append("<td valign=\"middle\" class=\"form_label\" align=\"center\">");
					s.append("<input type=\"text\" name=\"freq_"+idInputCont+cor.getCodCor()+"\" id=\"freq_"+idInputCont+cor.getCodCor()+"\" value=\""+Pagina.trocaNull(ptcCor.getFrequenciaEnvioEmailPtccor())+"\" size=\"4\" " + disabled + ">");
					s.append("</td>");
					s.append("<td valign=\"middle\" class=\"form_label\" align=\"center\">");
					s.append("<input type=\"checkBox\" class=\"form_check_radio\" value=\"S\" name=\"ativo"+idInput+"["+cor.getCodCor()+"]\" id=\"ativo"+idInput+"["+cor.getCodCor()+"]\""); 
//					if (!"N".equals(ptcCor.getIndAtivoEnvioEmailPtccor())) {
//						s.append(" checked"); 
//					}
					s.append(" " + disabled+">");
					s.append("</td>");
					s.append("</tr>");
				}			
			}
			
			s.append("<!--<tr>");
			s.append("	<td valign=\"middle\" class=\"form_label\" align=\"center\">");
			s.append("		<img src=\"../../images/pcChecked1.png\">");
			s.append("	</td>");
			s.append("	<td valign=\"middle\" class=\"form_label\" align=\"center\" colspan=\"2\">Concluído</td>");
			s.append("	<td valign=\"middle\" class=\"form_label\" align=\"center\">&nbsp;</td>");
			s.append("</tr>");
			s.append("<tr>");
			s.append("	<td valign=\"middle\" class=\"form_label\" align=\"center\">");
			s.append("		<img src=\"../../images/pcPretoFixo1.png\">");
			s.append("	</td>");
			s.append("	<td valign=\"middle\" class=\"form_label\" align=\"center\" colspan=\"2\">Limite Ultrapassado</td>");
			s.append("	<td valign=\"middle\" class=\"form_label\" align=\"center\">");
			s.append("		<input type=\"checkBox\" class=\"form_check_radio\" name=\"ativoPreto"+idInput+"\" id=\"ativoPreto"+idInput+"\" ");
			if ("S".equals(configMailVencto.getAtivoCfgm())) {
				s.append(" checked"); 
			}
			s.append("disabled >");					
			s.append("	</td>");
			s.append("</tr>");	
			s.append("--></table>");
			s.append("</td>");
			s.append("</tr>");	
		
			s.append("<tr>");
			s.append("<td class=\"label\">Sugest&atilde;o de Solu&ccedil;&atilde;o</td>");
			s.append("<td><textarea name=\"descricaoSolucaoPtc"+idInput+"\" id=\"descricaoSolucaoPtc"+idInput+"\" rows=\"5\" cols=\"50\" "+ disabled + " "); 
			s.append(" onkeyup=\"return validaTamanhoLimite(this, 2000);\"");
			s.append(" onkeydown=\"return validaTamanhoLimite(this, 2000);\"");
			s.append(" onblur=\"return validaTamanhoLimite(this, 2000);\"");
			s.append(">"+Pagina.trocaNull(pontoCritico.getDescricaoSolucaoPtc())+"</textarea>");
			s.append("</td>");
			s.append("</tr>");	
			s.append("<tr>");
			s.append("	<td class=\"label\">Data da Solu&ccedil;&atilde;o</td>");
			s.append("	<td><input type=\"text\" name=\"dataSolucaoPtc"+idInput+"\" id=\"dataSolucaoPtc"+idInput+"\" size=\"13\" maxlength=\"10\" value=\""+Pagina.trocaNullData(pontoCritico.getDataSolucaoPtc())+"\" onkeyup=\"mascaraData(event, this);\""+disabled+">");
			if ( !disabled.equals("disabled") && !disabledCampo.equals("disabled")) {
    			if(idInput != null && !idInput.equals("")) {
    				s.append("		<img class=\"posicao\" title=\"Selecione a data\" src=\"../../images/icone_calendar.gif\" onclick=\"open_calendar('data', document.forms[1].dataSolucaoPtc"+idInput+", '')\" " + disabled + " " + disabledCampo + ">");
    			} else {
    				s.append("		<img class=\"posicao\" title=\"Selecione a data\" src=\"../../images/icone_calendar.gif\" onclick=\"open_calendar('data', document.forms[0].dataSolucaoPtc, '')\" " + disabled + " " + disabledCampo + ">");
    			}
    		}
    		s.append("		</td>");
			s.append("</tr>");
			s.append("<tr>"); 
			s.append("<td class=\"label\">Respons&aacute;vel pela Solu&ccedil;&atilde;o</td>");
			s.append("<td>");		
			
			// Jogar valores para uma variável para evitar nullPointerException
			String nomeUsuario = "";
			String codUsuario = "";
			if(pontoCritico.getUsuarioUsu()!=null){
				nomeUsuario = pontoCritico.getUsuarioUsu().getNomeUsuSent().toString();
				codUsuario = pontoCritico.getUsuarioUsu().getCodUsu().toString();
			}
	
		
			s.append("<input type=\"text\" name=\"nomeUsu"+idInput+"\" id=\"nomeUsu"+idInput+"\"  size=\"55\" value=\""+Pagina.trocaNull(nomeUsuario)+"\" disabled>");
			s.append("<input type=\"hidden\" name=\"codUsu"+idInput+"\" id=\"codUsu"+idInput+"\" value=\""+codUsuario+"\">");
			if(getIndice() != null && !getIndice().equals("")) {
				s.append("<input type=\"button\" value=\"Pesquisar\" class=\"botao\" "+ disabled + " onclick=\"popup_pesquisa('ecar.popup.PopUpUsuario', 'retornoUsu"+getIndice()+"');\">");
			} else {
				s.append("<input type=\"button\" value=\"Pesquisar\" class=\"botao\" "+ disabled + " onclick=\"popup_pesquisa('ecar.popup.PopUpUsuario' );\">");
			}
			s.append("&nbsp;&nbsp;");
			s.append("<input type=\"button\" value=\"Limpar\" class=\"botao\" ");
			s.append("onclick=\"document.form.nomeUsu" + idInput + ".value = ''; document.form.codUsu" + idInput + ".value = ''; \">");
			s.append("</td>");
			s.append("</tr>");	
			s.append("<tr>");
			s.append("<td class=\"label\">* Estado </td>");
			s.append("<td>");
			s.append("<input type=\"radio\" class=\"form_check_radio\" name=\"indAtivoPtc"+idInput+"\" id=\"indAtivoPtc"+idInput+"\" value=\"S\" "+ ((pontoCritico.getIndAtivoPtc()!=null)? Pagina.isChecked(pontoCritico.getIndAtivoPtc(), "S"):" checked") + " " + disabled + " " + disabledCampo + "> Ativo");
			s.append("<input type=\"radio\" class=\"form_check_radio\" name=\"indAtivoPtc"+idInput+"\" id=\"indAtivoPtc"+idInput+"\" value=\"N\" "+ Pagina.isChecked(pontoCritico.getIndAtivoPtc(), "N") + " " + disabled + " " + disabledCampo + "> Cancelado");
			if(!"".equals(disabledCampo)){
				s.append("<input type=\"hidden\" name=\"indAtivoPtc"+idInput+"\" id=\"indAtivoPtc"+idInput+"\" value=\"" + pontoCritico.getIndAtivoPtc() + "\">");
			}
			s.append("</td>");
			s.append("</tr>");
			s.append("</table>");
			
			writer.print(s);
			
			//Apagar a partir daqui    	
        	
//        	s.append("<table class=\"form\">");
//            s.append("<tr> ");
//            s.append("	<td colspan=2> ");
//    		s.append("		<div class=\"camposobrigatorios\">* Campos de preenchimento obrigat&oacute;rio</div>");
//    		s.append("	</td>");
//    		s.append("</tr>");
//    		s.append("<tr>");
//    		s.append("	<td class=\"label\">");
//    		if(anexo != null && anexo.getArquivoIettup()!=null){
//    			s.append("Novo Arquivo:");
//    		} else {	
//    			s.append("* Arquivo:");	
//    		}
//    		s.append("	</td>");
//    		s.append("	<td>");
//    		s.append("		<input type=\"file\" name=\"arquivoIettup\"" + disabled + ">");
//    		s.append("	</td>");
//    		s.append("</tr>");
//    		s.append("<tr>");
//    		s.append("	<td class=\"label\">* Descri&ccedil;&atilde;o</td>");		
//    		s.append("	<td><textarea name=\"descricaoIettup\" rows=\"4\" cols=\"60\" "+ readOnly);
//    		s.append("		onkeyup=\"return validaTamanhoLimite(this, 2000);\"");
//    		s.append("  	onkeydown=\"return validaTamanhoLimite(this, 2000);\"");
//    		s.append("  	onblur=\"return validaTamanhoLimite(this, 2000);\"> " + Pagina.trocaNull(anexo.getDescricaoIettup()) + "</textarea>");
//    		s.append("	</td>");
//    		s.append("</tr>");
//    		
//    		if(anexo!= null && anexo.getArquivoIettup()!=null){
//    			s.append("<tr>");
//    			s.append("	<td class=\"label\">Arquivo Atual:</td>");
//    			s.append("	<td> "+ anexo.getNomeOriginalIettup() + "</td>");
//    			s.append("</tr>");
//    			s.append("<tr>");
//    			s.append("	<td class=\"label\">Tamanho Atual:</td>");
//    			s.append("	<td>  " + Util.formataByte(anexo.getTamanhoIettup()) + "</td>");
//    			s.append("</tr>");
//    			s.append("<tr>");
//    			s.append("	<td class=\"label\">Data:</td>");
//    			s.append("	<td> " + Pagina.trocaNullData(anexo.getDataInclusaoIettup()) + "</td>");
//    			s.append("</tr>");
//    		}
//    		s.append("<tr>");
//    		s.append("	<td class=\"label\">* Tipo</td>");
//    		s.append("	<td>");
//    		
//    		writer.print(s.toString());
//
//    		String selectedTipo = "";
//    		if(anexo.getUploadTipoArquivoUta() != null)
//    			selectedTipo = anexo.getUploadTipoArquivoUta().getCodUta().toString();
//        	
//    		ComboTag comboTag = new ComboTag(writer);
//        	comboTag.setNome(nomeComboTag);
//        	comboTag.setObjeto(objetoComboTag);
//        	comboTag.setLabel(labelComboTag);
//        	comboTag.setValue(valueComboTag);
//        	comboTag.setSelected(selectedTipo);
//        	comboTag.setScripts(scriptsComboTag);
//        	comboTag.setColecao(colecao);
//        			
//    	    comboTag.doStartTag();
//    	    
//    		sFim.append("</td>");
//    		sFim.append("</tr>");
//    		sFim.append("</table>");
//    		
//    		writer.print(sFim);
  	 
            
        } catch (Exception e) {
        	
        }
        return Tag.EVAL_BODY_INCLUDE;
    } 

    /**
     *
     * @return
     */
    public String getIndice() {
		return indice;
	}

    /**
     *
     * @param indice
     */
    public void setIndice(String indice) {
		this.indice = indice;
	}

	/**
     * Atribui valor especificado para PageContext page.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
         * @param arg0
     */
    public void setPageContext(PageContext arg0) {
        this.page = arg0;
    }

    /**
     * Atribui valor especificado para Tag parent.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param arg0
     */
    public void setParent(Tag arg0) {
        parent = arg0;
    }

    /**
     * Retorna Tag parent.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Tag
     */
    public Tag getParent() {
        return parent;
    }

    /**
     * Encerra Tag.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 * @throws JspException
     */
    public int doEndTag() throws JspException {
        return Tag.EVAL_PAGE;
    }

    /**
     * 
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void release() {
        //this.selected = null;
    }

    /**
     * Retorna PageContext page.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return PageContext - (Returns the page)
     */
    public PageContext getPage() {
        return page;
    }

    /**
     * Atribui valor especificado para PageContext page.<br>
     * 
     * @param page
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setPage(PageContext page) {
        this.page = page;
    }


    /**
     *
     * @return
     */
    public String getImagem() {
		return imagem;
	}

    /**
     *
     * @param imagem
     */
    public void setImagem(String imagem) {
		this.imagem = imagem;
	}

    /**
     *
     * @return
     */
    public String getDisabled() {
		return disabled;
	}

        /**
         *
         * @param disabled
         */
        public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

        /**
         *
         * @return
         */
        public String getReadOnly() {
		return readOnly;
	}

        /**
         *
         * @param readOnly
         */
        public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

        /**
         *
         * @return
         */
        public String getNomeComboTag() {
		return nomeComboTag;
	}

        /**
         *
         * @param nomeComboTag
         */
        public void setNomeComboTag(String nomeComboTag) {
		this.nomeComboTag = nomeComboTag;
	}

        /**
         *
         * @return
         */
        public String getObjetoComboTag() {
		return objetoComboTag;
	}

        /**
         *
         * @param objetoComboTag
         */
        public void setObjetoComboTag(String objetoComboTag) {
		this.objetoComboTag = objetoComboTag;
	}

        /**
         *
         * @return
         */
        public String getLabelComboTag() {
		return labelComboTag;
	}

        /**
         *
         * @param labelComboTag
         */
        public void setLabelComboTag(String labelComboTag) {
		this.labelComboTag = labelComboTag;
	}

        /**
         *
         * @return
         */
        public String getValueComboTag() {
		return valueComboTag;
	}

        /**
         *
         * @param valueComboTag
         */
        public void setValueComboTag(String valueComboTag) {
		this.valueComboTag = valueComboTag;
	}

        /**
         *
         * @return
         */
        public String getOrderComboTag() {
		return orderComboTag;
	}

        /**
         *
         * @param orderComboTag
         */
        public void setOrderComboTag(String orderComboTag) {
		this.orderComboTag = orderComboTag;
	}

        /**
         *
         * @return
         */
        public PontoCriticoPtc getPontoCritico() {
		return pontoCritico;
	}

        /**
         *
         * @param pontoCritico
         */
        public void setPontoCritico(PontoCriticoPtc pontoCritico) {
		this.pontoCritico = pontoCritico;
	}

        /**
         *
         * @return
         */
        public String getDisabledCampo() {
		return disabledCampo;
	}

        /**
         *
         * @param disabledCampo
         */
        public void setDisabledCampo(String disabledCampo) {
		this.disabledCampo = disabledCampo;
	}

        /**
         *
         * @return
         */
        public String getReadOnlyCampo() {
		return readOnlyCampo;
	}

        /**
         *
         * @param readOnlyCampo
         */
        public void setReadOnlyCampo(String readOnlyCampo) {
		this.readOnlyCampo = readOnlyCampo;
	}

        /**
         *
         * @return
         */
        public HttpServletRequest getRequest() {
		return request;
	}

        /**
         *
         * @param request
         */
        public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

        /**
         *
         * @return
         */
        public String getFiltersComboTag() {
		return filtersComboTag;
	}

        /**
         *
         * @param filtersComboTag
         */
        public void setFiltersComboTag(String filtersComboTag) {
		this.filtersComboTag = filtersComboTag;
	}

        /**
         *
         * @return
         */
        public JspWriter getWriterParametro() {
		return writerParametro;
	}

        /**
         *
         * @param writerParametro
         */
        public void setWriterParametro(JspWriter writerParametro) {
		this.writerParametro = writerParametro;
	}

        /**
         *
         * @return
         */
        public Boolean getAbaPontoCriticoDeAcompanhamento() {
		return abaPontoCriticoDeAcompanhamento;
	}

        /**
         *
         * @param abaPontoCriticoDeAcompanhamento
         */
        public void setAbaPontoCriticoDeAcompanhamento(
			Boolean abaPontoCriticoDeAcompanhamento) {
		this.abaPontoCriticoDeAcompanhamento = abaPontoCriticoDeAcompanhamento;
	}

        /**
         *
         * @return
         */
        public AcompReferenciaItemAri getAri() {
		return ari;
	}

        /**
         *
         * @param ari
         */
        public void setAri(AcompReferenciaItemAri ari) {
		this.ari = ari;
	}

        /**
         *
         * @return
         */
        public SegurancaECAR getSeguranca() {
		return seguranca;
	}

        /**
         *
         * @param seguranca
         */
        public void setSeguranca(SegurancaECAR seguranca) {
		this.seguranca = seguranca;
	}


	
}