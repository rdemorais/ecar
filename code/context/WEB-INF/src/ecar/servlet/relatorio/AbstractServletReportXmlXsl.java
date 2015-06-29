package ecar.servlet.relatorio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.XMLFilter;

import ecar.exception.ECARException;
import ecar.util.Dominios;

/**
 * @author fabios
 */
public abstract class AbstractServletReportXmlXsl extends HttpServlet {

    /**
     *
     */
    protected Logger logger = null;
	
        /**
         *
         */
        public static String SAIDA_DOWNLOAD = "SAIDA_DOWNLOAD";
        /**
         *
         */
        public static String SAIDA_GRAVAR = "SAIDA_GRAVAR";
        /**
         *
         */
        public static String SAIDA_DOWNLOAD_E_GRAVAR = "SAIDA_DOWNLOAD_E_GRAVAR";

	private String saida = SAIDA_DOWNLOAD;
	private String caminhoArquivoSaidaGravar = "";
    
	
	/**
	 * Construtor.<br>
	 */
    public AbstractServletReportXmlXsl() {
        this.logger = Logger.getLogger(this.getClass());
    }

    /**
     * Retorna String saida.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getSaida() {
		return saida;
	}

    /**
     * Atribui velor especificado para String saida.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param saida
     */
	public void setSaida(String saida) {
		this.saida = saida;
	}

	/**
	 * Prepara relatorio para ser impresso, mesclando XSL e XML.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param request
         * @param response
	 * @throws IOException
	 */
	
    @Override
	public final void service(HttpServletRequest request, HttpServletResponse response)
    	throws ServletException, IOException {

		String filesPath = this.getServletContext().getRealPath(File.separator + "relatorios" + File.separator + "xsl");
		String imagesPath = this.getServletContext().getRealPath(File.separator + "images" + File.separator + "relAcomp");
		String xslPath = filesPath + File.separator + getXslFileName();

		String protocol = ( request.getRequestURL().indexOf("https") == 0 ? "https://" : "http://" );
		//org.apache.fop.configuration.Configuration.put("baseDir", protocol + request.getLocalName() + ":" + request.getLocalPort() + request.getContextPath() + "/");
		
        try {
        	
        	//FopImageFactory.resetCache();
            StringBuffer relatorio = getXml(request);
            String tipoArquivoRelatorio = request.getParameter("tipoArquivoRelatorio"); 
            
            // Adicionado if que possibilita a geração do relatório em PPT
            if (tipoArquivoRelatorio!=null && tipoArquivoRelatorio.equals("ppt")) {
            	parserPPT(imagesPath, relatorio, request, response);
            } else {
            	parser(new File(xslPath), relatorio, response);
            }
                        
        } catch (ECARException e) {
        	this.logger.error(e);
            response.sendRedirect(getErrorPage(request, e.getMessageKey()));
        }
    }
    
	/**
	 * 
	 * 
         * @param request
         * @author N/C
     * @since N/C
     * @version N/C
	 * @return StringBuffer
	 * @throws ECARException
	 */
    public abstract StringBuffer getXml(HttpServletRequest request) throws ECARException;
    
    /**
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public abstract String getXslFileName();
    
    /**
     * @author N/C
     * @since N/C
     * @version N/C
     * @param request
     * @param mensagem
     * @return String
     */
    public abstract String getErrorPage(HttpServletRequest request, String mensagem);
    
    /**
     * Mescla dados do XML com XSL.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param String stylesheet
     * @param StringBuffer xml
     * @param HttpServletResponse response
     * @throws ECARException
     */
    private void parser(File stylesheet , StringBuffer xml, HttpServletResponse response) throws ECARException{    	
        try{
			InputSource xmlSource = new InputSource(new ByteArrayInputStream(xml.toString().getBytes(Dominios.ENCODING_DEFAULT)));
			//XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			// arquivo onde vai ser gerado o FO
	        ByteArrayOutputStream outFo = new ByteArrayOutputStream();

			//java.io.PrintWriter out = response.getWriter();
			StreamResult result = new StreamResult(outFo);
			XMLFilter style = null;
			SAXTransformerFactory stf = (SAXTransformerFactory)SAXTransformerFactory.newInstance();
			try{
				StreamSource ss = new StreamSource(stylesheet);
				style = stf.newXMLFilter(ss);
			}catch(javax.xml.transform.TransformerConfigurationException ex){
			    logger.error(ex);
			}

			SAXSource transformSource = new SAXSource(style, xmlSource);
			stf.newTransformer().transform(transformSource, result);
           
			outFo.close();

	        InputSource foInput = new InputSource(new ByteArrayInputStream(outFo.toByteArray()));

	        ByteArrayOutputStream outPdf = new ByteArrayOutputStream();

   	        //response.setHeader("Content-Disposition", "attachment; filename=\"MyFile.pdf\""); //$NON-NLS-1$ //$NON-NLS-2$
	        response.setHeader("Content-Disposition","inline");
	        response.setContentType("application/pdf");
	        
			// instanciando o driver FOP para fazer a renderização do documento
            //Driver driver = new Driver(foInput, outPdf);
           	// setando o tipo da renderização a ser aplicada (PDF)
            //driver.setRenderer(Driver.RENDER_PDF);            
          
            //driver.run();

            byte[] content = outPdf.toByteArray();
            
            if(SAIDA_DOWNLOAD.equals(this.getSaida()) || SAIDA_DOWNLOAD_E_GRAVAR.equals(this.getSaida())) {
	            response.setContentLength(content.length);
	            response.getOutputStream().write(content);
	            response.getOutputStream().flush();
				response.getOutputStream().close();
            } 
            
            if(SAIDA_GRAVAR.equals(this.getSaida()) || SAIDA_DOWNLOAD_E_GRAVAR.equals(this.getSaida())) {
            	FileOutputStream arq = new FileOutputStream(this.getCaminhoArquivoSaidaGravar());
            	arq.write(content);
            	arq.flush();
            	arq.close();
            }
		}catch(Exception e){
    		this.logger.error(e);
			throw new ECARException("Erro na renderizacao do arquivo PDF");
		}
    }

    /**
     * Mescla dados do XML com XSL e exporta o relatorio para PPT.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param String stylesheet
     * @param StringBuffer xml
     * @param HttpServletResponse response
     * @throws ECARException
     */
    private void parserPPT(String caminhoImagens, StringBuffer xml, HttpServletRequest request, HttpServletResponse response) throws ECARException{    	
        try{
			        	        	
        	MontaPPT paserXMLPPT = new MontaPPT();
        	paserXMLPPT.parserXMLPPT(xml, caminhoImagens, request, response);     	
        		
		}catch(Exception e){
    		this.logger.error(e);
			throw new ECARException("Erro na renderizacao do arquivo PDF");
		}
    }
        
    /**
     * Retorna String caminhoArquivoSaidaGravar.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String - (caminhoArquivoSaidaGravar)
     */
	public String getCaminhoArquivoSaidaGravar() {
		return caminhoArquivoSaidaGravar;
	}

	/**
	 * Atribui valor especificado para String caminhoArquivoSaidaGravar.<br>
	 * 
         * @param caminhoArquivoSaidaGravar
         * @author N/C
     * @since N/C
     * @version N/C
	 */
	public void setCaminhoArquivoSaidaGravar(String caminhoArquivoSaidaGravar) {
		this.caminhoArquivoSaidaGravar = caminhoArquivoSaidaGravar;
	}
	
	
	
}
