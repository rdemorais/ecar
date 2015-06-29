/*
 * Created on 19/05/2006
 *
 */
package ecar.servlet.relatorio;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.util.Data;
import comum.util.Util;
import comum.util.XmlBuilder;

import ecar.dao.ConfiguracaoDao;
import ecar.exception.ECARException;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EfImportOcorrenciasEfio;

/**
 * @author aleixo
 *
 */
public class RelatorioOcorrenciasImportacao extends AbstractServletReportXmlXsl {

	private static final long serialVersionUID = 2395457474744685932L;
	private static final int TAMANHO_VALORES = 14;
	private ConfiguracaoCfg configuracao;
	
	/**
	 * Gera XML.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param request
	 * @return StringBuffer
	 * @throws ECARException
	 */
	public StringBuffer getXml(HttpServletRequest request) throws ECARException{
		configuracao = (new ConfiguracaoDao(null)).getConfiguracao();

		XmlBuilder builder = new XmlBuilder();
		String titulo = configuracao.getTituloSistema() + " - Relatório de Ocorrências de Importação";
		String dataHora = Data.parseDateHour(Data.getDataAtual());
		
		Util.liberarImagem();
		
		List ocorrencias = (List) request.getSession().getAttribute("listaCriticas");
		//SegurancaECAR usuarioLogado = (SegurancaECAR) request.getSession().getAttribute("seguranca");
		
		builder.addNode("relatorio", "titulo=\"" + builder.normalize(titulo) + "\" dataHora=\"" + builder.normalize(dataHora) + "\"");

		if(ocorrencias != null && !ocorrencias.isEmpty()){
			
			builder.addNode("ocorrencias");
			
			Iterator it = ocorrencias.iterator();
			while(it.hasNext()){
				EfImportOcorrenciasEfio ocorrencia = (EfImportOcorrenciasEfio) it.next();

//				TODO MANTIS  0011017
				// INICIO - MANTIS  0011017
				//String data = Data.parseDate(ocorrencia.getdataHoraImportacaoEfio());
				String data = "";
				// FIM - MANTIS  0011017
				
				String descricao = ocorrencia.getDescricaoEfio();
				builder.addClosedNode("ocorrencia", 
						"data=\"" + builder.normalize(data) + "\"" +
						" descricao=\"" + builder.normalize(descricao) + "\"");
				
			}
			
			builder.closeNode("ocorrencias");
		}
		
		builder.closeNode("relatorio");
		
        return builder.toStringBuffer();
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
        return "relatorioOcorrenciasImportacao.xsl";
    }
    
    /**
     * Pega o erro de pagina.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param request
     * @param mensagem
     * @return String
     */
    public String getErrorPage(HttpServletRequest request, String mensagem){        
        String errorPage = "ctrl.jsp?msgOperacao=" + mensagem; 
        return errorPage;
    }
}
