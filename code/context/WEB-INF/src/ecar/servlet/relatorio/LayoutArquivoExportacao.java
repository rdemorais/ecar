/*
 * Created on 19/05/2006
 *
 */
package ecar.servlet.relatorio;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import comum.util.Pagina;
import comum.util.XmlBuilder;

import ecar.dao.ConfiguracaoDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.ConfiguracaoCfg;

/**
 * @author aleixo
 *
 */
public class LayoutArquivoExportacao extends AbstractServletReportXmlXsl {

	private static final long serialVersionUID = 2395457474744685932L;
	private static final int TAMANHO_VALORES = 14;
	private ConfiguracaoCfg configuracao;
	
	/**
	 * Pega XML.<br>
	 * 
         * @param request
         * @author N/C
     * @since N/C
     * @version N/C
	 * @return StringBuffer
	 * @throws ECARException
	 */
	public StringBuffer getXml(HttpServletRequest request) throws ECARException{
		configuracao = (new ConfiguracaoDao(null)).getConfiguracao();
		
		setSaida(SAIDA_DOWNLOAD_E_GRAVAR);
		String caminho = configuracao.getRaizUpload() + configuracao.getUploadIntegracao();
		if(!caminho.endsWith("/"))
			caminho = caminho + "/";
		
		String nomeArquivo = Pagina.getParamStr(request, "nomeArquivo");
		SegurancaECAR usuarioLogado = (SegurancaECAR) request.getSession().getAttribute("seguranca");
		String codUsuarioLogado = usuarioLogado.getUsuario().getCodUsu().toString();
		
		String titulo = "Layout para o arquivo \"" + nomeArquivo + "\"";

		String caminhoCompleto = "";

		String formato = "ddMMyyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
            
        nomeArquivo += "_" + formatter.format(new Date()) + "_" + codUsuarioLogado + "_layout.pdf";
		caminhoCompleto = caminho + nomeArquivo;
					
		File diretorio = new File(caminho);
		
		if(!diretorio.isDirectory())
			diretorio.mkdirs();

		setCaminhoArquivoSaidaGravar(caminhoCompleto);
		
		XmlBuilder builder = new XmlBuilder();

		builder.addNode("layout", "titulo=\"" + builder.normalize(titulo) + "\"");
		
		geraXMLTR00(builder);
		geraXMLTR01(builder);
		geraXMLTR02(builder);
		geraXMLTR99(builder);
		
		builder.closeNode("layout");
		
        return builder.toStringBuffer();
    }
    
	/**
	 * Gera XMLTROO.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param builder
	 */
	public void geraXMLTR00(XmlBuilder builder){
		builder.addNode("tr", "numero=\"00 - Header\" mostrarObs=\"N\"");

		escreveCampoTr(builder, "Campo", "Posição inicial", "Tamanho", "Tipo de dado", "Conteúdo");

		escreveCampoTr(builder, "TR", "0", "2", "Numérico", "00");
		escreveCampoTr(builder, "Sistema", "2", "6", "String", "Sigla do Sistema");
		escreveCampoTr(builder, "Mês inicial da solicitação", "8", "2", "Numérico", "MM");
		escreveCampoTr(builder, "Ano inicial da solicitação", "10", "4", "Numérico", "AAAA");
		escreveCampoTr(builder, "Mês final da solicitação", "14", "2", "Numérico", "MM");
		escreveCampoTr(builder, "Ano final da solicitação", "16", "4", "Numérico", "AAAA");
		escreveCampoTr(builder, "Data/Hora da geração", "20", "14", "Numérico", "DDMMAAAAHHMMSS");
		escreveCampoTr(builder, "Data/Hora da Carga", "34", "14", "Numérico", "DDMMAAAAHHMMSS (Data da carga efetuada pelo sistema corporativo)");
		
		builder.closeNode("tr");
	}
	
	/**
	 * Gera XMLTRO1.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param builder
	 */
	public void geraXMLTR01(XmlBuilder builder){
		builder.addNode("tr", "numero=\"01 - Registro de valores\" mostrarObs=\"N\"");

		escreveCampoTr(builder, "Campo", "Posição inicial", "Tamanho", "Tipo de dado", "Conteúdo");

		escreveCampoTr(builder, "TR", "0", "2", "Numérico", "01");
		escreveCampoTr(builder, "Conta", "2", "255", "String", "Conta Contábil");
		escreveCampoTr(builder, "Mês", "257", "2", "Numérico", "MM");
		escreveCampoTr(builder, "Ano", "259", "4", "Numérico", "AAAA");
		
		String[] valores = new String[6];
		valores[0] = configuracao.getFinanceiroDescValor1Cfg();
		valores[1] = configuracao.getFinanceiroDescValor2Cfg();
		valores[2] = configuracao.getFinanceiroDescValor3Cfg();
		valores[3] = configuracao.getFinanceiroDescValor4Cfg();
		valores[4] = configuracao.getFinanceiroDescValor5Cfg();
		valores[5] = configuracao.getFinanceiroDescValor6Cfg();
		
		int pos = 263;
		for(int i = 0; i < 6; i++){
			if("".equals(valores[i].trim()))
				valores[i] = "Não preencher";
			escreveCampoTr(builder, valores[i], String.valueOf(pos), String.valueOf(TAMANHO_VALORES), "Numérico", "99999999999900");
			pos += TAMANHO_VALORES;
		}
		
		escreveCampoTr(builder, "Contabilidade Aberta/Fechada", String.valueOf(pos), "1", "String", "A - Aberto; F - Fechado");		
		
		builder.closeNode("tr");
	}

	/**
	 * Gera XMLTRO2.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param builder
	 */
	public void geraXMLTR02(XmlBuilder builder){
		builder.addNode("tr", "numero=\"02 - Registro de valores não previstos no e-Car\" mostrarObs=\"N\"");

		escreveCampoTr(builder, "Campo", "Posição inicial", "Tamanho", "Tipo de dado", "Conteúdo");

		escreveCampoTr(builder, "TR", "0", "2", "Numérico", "02");
		escreveCampoTr(builder, "Mês", "2", "2", "Numérico", "MM");
		escreveCampoTr(builder, "Ano", "4", "4", "Numérico", "AAAA");
		
		String[] valores = new String[6];
		valores[0] = configuracao.getFinanceiroDescValor1Cfg();
		valores[1] = configuracao.getFinanceiroDescValor2Cfg();
		valores[2] = configuracao.getFinanceiroDescValor3Cfg();
		valores[3] = configuracao.getFinanceiroDescValor4Cfg();
		valores[4] = configuracao.getFinanceiroDescValor5Cfg();
		valores[5] = configuracao.getFinanceiroDescValor6Cfg();
		
		int pos = 8;
		for(int i = 0; i < 6; i++){
			if("".equals(valores[i].trim()))
				valores[i] = "Não preencher";
			escreveCampoTr(builder, valores[i], String.valueOf(pos), String.valueOf(TAMANHO_VALORES), "Numérico", "99999999999900");
			pos += TAMANHO_VALORES;
		}
		
		builder.closeNode("tr");
	}

	/**
	 * Gera XMLTR99.<br>
	 * 
         * @param builder
         * @author N/C
     * @since N/C
     * @version N/C
	 */
	public void geraXMLTR99(XmlBuilder builder){
		builder.addNode("tr", "numero=\"99 - Trailer\" mostrarObs=\"S\"");

		escreveCampoTr(builder, "Campo", "Posição inicial", "Tamanho", "Tipo de dado", "Conteúdo");

		escreveCampoTr(builder, "TR", "0", "2", "Numérico", "99");
		escreveCampoTr(builder, "Quantidade de registros", "2", "6", "Numérico", "Quantidade de registros incluindo o Header e o Trailer");
		
		String[] valores = new String[6];
		valores[0] = configuracao.getFinanceiroDescValor1Cfg();
		valores[1] = configuracao.getFinanceiroDescValor2Cfg();
		valores[2] = configuracao.getFinanceiroDescValor3Cfg();
		valores[3] = configuracao.getFinanceiroDescValor4Cfg();
		valores[4] = configuracao.getFinanceiroDescValor5Cfg();
		valores[5] = configuracao.getFinanceiroDescValor6Cfg();
		
		int pos = 8;
		for(int i = 0; i < 6; i++){
			if("".equals(valores[i].trim()))
				valores[i] = "Não preencher";
			else
				valores[i] = "Total do " + valores[i];
			
			escreveCampoTr(builder, valores[i], String.valueOf(pos), String.valueOf(TAMANHO_VALORES), "Numérico", "99999999999900");
			pos += TAMANHO_VALORES;
		}
		
		
		builder.closeNode("tr");
	}

	/**
	 * Escreve campo Tr.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param builder
         * @param nome
         * @param tamanho
         * @param posIni 
         * @param tipo
         * @param conteudo
	 */
	public void escreveCampoTr(XmlBuilder builder, String nome, String posIni, String tamanho, String tipo, String conteudo){
		builder.addClosedNode("campo",
				"nome=\"" + builder.normalize(nome) + "\"" +
				" posIni=\"" + builder.normalize(posIni) + "\"" +
				" tamanho=\"" + builder.normalize(tamanho) + "\"" +
				" tipo=\"" + builder.normalize(tipo) + "\"" +
				" conteudo=\"" + builder.normalize(conteudo) + "\""
				);
	}
	
	/**
	 * Pega o nome do arquivo XLS.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
    public String getXslFileName() {
        return "layoutArquivoExportacao.xsl";
    }
    
    /**
     * Retorna pagina de erro.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param request
     * @param mensagem
     * @return String - Erro da pagina
     */
    public String getErrorPage(HttpServletRequest request, String mensagem){        
        String errorPage = "exportarConta.jsp?msgOperacao=" + mensagem; 
        return errorPage;
    }
}
