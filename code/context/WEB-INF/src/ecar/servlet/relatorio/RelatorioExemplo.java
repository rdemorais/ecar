/*
 * Created on 28/12/2004
 *
 */
package ecar.servlet.relatorio;

import javax.servlet.http.HttpServletRequest;

import comum.util.Util;

import ecar.exception.ECARException;
import ecar.util.Dominios;

/**
 * @author garten
 *
 */
public class RelatorioExemplo extends AbstractServletReportXmlXsl {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8279094089574665068L;

	/**
	 * Gera XML.<br>
	 * 
         * @param request
         * @author N/C
     * @since N/C
     * @version N/C
	 * @return StringBuffer
	 */
    public StringBuffer getXml(HttpServletRequest request) throws ECARException {
    	Util.liberarImagem();
        StringBuffer xml = new StringBuffer();
        xml.append("<?xml version=\"1.0\" encoding=\"" + Dominios.ENCODING_DEFAULT + "\"?>\n")
           .append("<relatorio>\n")
           .append("	<campo titulo=\"Descricao da Acao\" texto=\" A descricao deve deixar claro\n")
           .append("		o objeto da A��o, para quem nao a conhece. Deve conter a situa��o\n")
           .append("		inicial, ou seja, a situa��o que justifica as a��es propostas.\"/>\n")
           .append("	<campo titulo=\"Objetivo Geral\" texto=\"Objetivo maior da a��o...\"/>\n")
           .append("	<campo titulo=\"Objetivos Espec�ficos\" texto=\"Orientam a atua��o e definem...\"/>\n")
           .append("	<lista item=\"Encerrar forte conteudo programatico\"/>\n")
           .append("	<lista item=\"Constar como prioridade\"/>\n")
           .append("	<lista item=\"Possuir ampla abrangencia social\"/>\n")
           .append("	<lista item=\"Comprometer o governo em caso de insucesso\"/>\n")
           .append("	<lista item=\"Ter adquirido centralidade pol�tica\"/>\n")
           .append("	<lista2>\n")
           .append("		<lt>Crit�rios</lt>\n")
           .append("		<li>item 1</li>\n")
           .append("		<li>item 2</li>\n")
           .append("		<li>item 3</li>\n")
           .append("		<li>item 4</li>\n")
           .append("		<li>item 5</li>\n")
           .append("	</lista2>\n")
           .append("</relatorio>");

        return xml;
    }

    /**
     * Pega o nome do arquivo xsl.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getXslFileName() {
        return "relatorioExemplo.xsl";
    }

    /**
     * Pega o erro de pagina.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param request
     * @param msg
     * @return String
     */
    /* (non-Javadoc)
     * @see ecar.relatorio.servlet.AbstractServletReportXmlXsl#getErrorPage(javax.servlet.http.HttpServletRequest)
     */
    public String getErrorPage(HttpServletRequest request, String msg) {
        return null;
    }

}
