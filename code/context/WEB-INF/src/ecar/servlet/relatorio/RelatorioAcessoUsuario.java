/*
 * Created on 19/05/2006
 *
 */
package ecar.servlet.relatorio;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.util.Data;
import comum.util.Mensagem;
import comum.util.Pagina;
import comum.util.Util;
import comum.util.XmlBuilder;

import ecar.dao.RegControleAcessoRcaDao;
import ecar.exception.ECARException;
import ecar.pojo.RegControleAcessoRca;

/**
 *
 * @author 70744416353
 */
public class RelatorioAcessoUsuario extends AbstractServletReportXmlXsl {

	private static final long serialVersionUID = 3387112017468241181L;
	private Date dataInicio = null;
	private Date dataFim = null;
	private String codUsu;
	private String nomeUsu;
	private List usuarios;
	private String ordenacao;
	
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
		XmlBuilder builder = new XmlBuilder();
		
		Util.liberarImagem();
		
		if (!"".equals(Pagina.getParamStr(request, "dataInicio")))
			dataInicio = Data.parseDate(Pagina.getParamStr(request, "dataInicio"));
		if (!"".equals(Pagina.getParamStr(request, "dataFim")))
			dataFim = Data.parseDate(Pagina.getParamStr(request, "dataFim"));
		
		codUsu = Pagina.getParamStr(request, "codUsu");
		nomeUsu = Pagina.getParamStr(request, "nomeUsu");
		ordenacao = Pagina.getParamStr(request, "ordenacao");

		builder.addNode(
				"relatorio", 
				" titulo=\"" + builder.normalize(Pagina.getParamStr(request, "titulo_sistema")) + "\"" +
				" dataHora=\"" + builder.normalize(Data.parseDateHourMinuteSecond(Data.getDataAtual())) + "\""
				);
		
		
		RegControleAcessoRcaDao regDao = new RegControleAcessoRcaDao(request);
		if (!"".equals(codUsu)){
			usuarios = regDao.listar(Long.valueOf(codUsu), dataInicio, dataFim, ordenacao);
		} else {
			usuarios = regDao.listar(null, dataInicio, dataFim, ordenacao);
		}
		
		geraXMLItens(builder, request);
		
		builder.closeNode("relatorio");
		
        return builder.toStringBuffer();
    }
    
	/**
	 * Pega o nome do arquivo xml.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
    public String getXslFileName() {
        return "relatorioAcessoUsuario.xsl";
    }
    
    /**
     * Pega o erro da pagina.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param request
     * @param mensagem
     * @return String
     */
    public String getErrorPage(HttpServletRequest request, String mensagem){        
        String errorPage = "relatorios/rel_acessoUsuario.jsp?msgOperacao=" + mensagem; 
        return errorPage;
    }
    
    /**
     * Gera itens XML.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param request
     * @throws ECARException
     */
    public void geraXMLItens (XmlBuilder builder, HttpServletRequest request) throws ECARException {
    	String coluna1 = "Nome do Usuário";
    	String coluna2 = "Data/Hora do Login";
    	
    	if(!"usuario".equals(ordenacao)) {
        	coluna1 = "Data/Hora do Login";
        	coluna2 = "Nome do Usuário";
    	}
    		
    	builder.addNode("filtro");
    	
    	builder.addClosedNode("campo", 
				" label=\"" + builder.normalize("Ordenado por: ") + "\"" +
				" valor=\"" + builder.normalize(coluna1) + "\"");
    	
    	if (!"".equals(codUsu)){
    		builder.addClosedNode("campo", 
    				" label=\"" + builder.normalize("Acessos do Usuário: ") + "\"" +
    				" valor=\"" + builder.normalize(nomeUsu) + "\"");
    	} else {
    		builder.addClosedNode("campo", 
    				" label=\"" + builder.normalize("Acessos de Todos os Usuários") + "\"" +
    				" valor=\"" + builder.normalize("") + "\"");
    	}
    	
		builder.addClosedNode("campo", 
				" label=\"" + builder.normalize("Período de Acesso: ") + "\"" +
				" valor=\"" + builder.normalize(Data.parseDate(dataInicio) + " até " + Data.parseDate(dataFim)) + "\"");
    	
    	builder.closeNode("filtro");

    	builder.addNode("controleAcesso", "coluna1=\"" + coluna1 + "\" " + "coluna2=\"" + coluna2 + "\"");
    	
    	if(usuarios == null || usuarios.isEmpty()) {
    		builder.addClosedNode("itemAcesso", 
    				" campo1=\"" + new Mensagem(request.getSession().getServletContext()).getMensagem("relatorioAcessoUsuario.semRegistro.periodoInformado") + "\"");
    	} else {
	    	Iterator itUsu = usuarios.iterator();
	    	while (itUsu.hasNext()){
	    		
	    		RegControleAcessoRca reg = (RegControleAcessoRca)itUsu.next();
	    		
	    		String nomeUsuario = builder.normalize(reg.getUsuarioUsu().getNomeUsuSent());
	    		String dataAcesso = builder.normalize("" + Data.parseDateHourMinuteSecond(reg.getDataAcessoRca()));
	
	    		if("usuario".equals(ordenacao)) {
		    		builder.addClosedNode("itemAcesso", 
		    				" campo1=\"" + nomeUsuario + "\"" +
		    				" campo2=\"" + dataAcesso + "\"");
	    		} else {
		    		builder.addClosedNode("itemAcesso", 
		    				" campo1=\"" + dataAcesso + "\"" +
		    				" campo2=\"" + nomeUsuario + "\"");
	    		}
	    	}
    	}
    	builder.closeNode("controleAcesso");
    }
}
