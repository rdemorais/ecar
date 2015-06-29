package ecar.util.jasper;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

/**
 * Classe utilitaria para geracao de relatorio
 * @author gabrielsolana
 * 
 */
public class JasperService {

	private static Logger logger = Logger.getLogger(JasperService.class);
    
	/**
     *
     */
    public static String PATH_RELATORIO = "/PPAJasper";
	
	
    /**
     *
     */
    public JasperService(){}
	
	
    public static JasperReport loadReport(Report report) {
    	logger.info( "Carregando arquivo de relatorio <.jrxml>" );
    	JasperDesign jasperDesign;
    	try {
    		jasperDesign = JRXmlLoader.load(JasperService.class.getResourceAsStream("/peJasper/" + report.getReportName()));
    		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
    		return jasperReport;
    	}catch (JRException e) {
    		e.printStackTrace();
    		logger.error( "<ERRO JasperReports>: Nao foi possivel carregar arquivo de relatorio <.jrxml>" , e );
    		return null;
		}
    }
        
	/**
	 * Carrega o arquivo .jasper ( Relatorio compilado )
         * @param pathReport
         * @return objeto que representa o relatorio JasperReport, se erro retorna <null>
	 */
	public static JasperReport loadReport( String pathReport ){
		
		logger.info( "Carregando arquivo de relatorio <.jasper>" );
		try {

			JasperReport report = (JasperReport)JRLoader.loadObject( pathReport );
			logger.info( "Arquivo de relatorio <.jasper> carregado com sucesso" );
			return report;
			
		} catch (JRException e) {
			e.printStackTrace();
			logger.error( "<ERRO JasperReports>: Nao foi possivel carregar arquivo de relatorio <.jasper>" , e );	
			return null;
		}
		catch ( Exception e) {
			e.printStackTrace();
			logger.error( "<ERRO>: Nao foi possivel carregar arquivo de relatorio <.jasper>" , e );	
		return null;
	}			
		
	}
	
	/**
	 * Gera uma pre-impressao Jasper do objeto relatorio.
	 * @param relatorio objeto Jasper
	 * @param parametros do relatorio
	 * @param beanDS data Source do relatorio
	 * @return objeto pre-impressao Jasper
	 */
	public static JasperPrint fillReport(JasperReport relatorio, Map parametros, JRBeanCollectionDataSource beanDS ){
		
		logger.info( "Gerar objeto impressao Jasper" );
		try {
			JasperPrint print = JasperFillManager.fillReport(relatorio, parametros, beanDS );
			return print;
			
		} catch (JRException e) {
			e.printStackTrace();
			logger.error( "<ERRO JasperReports>: Nao foi possivel gerar impressao Jasper" , e );
			return null;
		} catch (Exception ex) {
				ex.printStackTrace();
				logger.error( "<ERRO>: Nao foi possivel gerar impressao Jasper" , ex );
				return null;
		}
	}
	
	/**
	 * Gera uma pre-impressao Jasper do objeto relatorio.
	 * @param relatorio objeto Jasper
	 * @param parametros do relatorio
	 * @return objeto pre-impressao Jasper
	 */
	public static JasperPrint fillReport(JasperReport relatorio, Map parametros ){
		
		logger.info( "Gerar objeto impressao Jasper" );
		try {
			

			JasperPrint print = JasperFillManager.fillReport(relatorio, parametros, new JREmptyDataSource() );
			
			return print;
			
		} catch (JRException e) {
			e.printStackTrace();
			logger.error( "<ERRO JasperReports>: Nao foi possivel gerar impressao Jasper"  , e );
			return null;
		} catch (Exception ex) {
				ex.printStackTrace();
				logger.error( "<ERRO>: Nao foi possivel gerar impressao Jasper" , ex );
				return null;
		}
	}	
	
	
        /**
         *
         * @param relatorio
         * @return
         */
        public static Integer getNumberOfPages( JasperPrint relatorio ){
		return relatorio.getPages().size();		
	}
	
	/**
	 * Adiciona o relatorioOrigem ao relatorioDestino
	 * @param relatorioOrigem impressao Jasper que deve ser copiado
         * @param relatorioDestino impressao Jasper Destino, � aqui que vai ser adicionado o outro relatorio
         * @param out
	 */
	public static void addRelatorio( JasperPrint relatorioOrigem, JasperPrint relatorioDestino, ByteArrayOutputStream out ){
		try {

			byte[] bytesOrg  = geraRelatorioPDF(relatorioOrigem);
			byte[] bytesDest  = geraRelatorioPDF(relatorioDestino);

			PdfReader readerOrg = new PdfReader(bytesOrg);
			int paginasOrg = readerOrg.getNumberOfPages();
			PdfReader readerDest = new PdfReader(bytesDest);
			int paginasDest = readerDest.getNumberOfPages();
			
			Document document = new Document(readerOrg.getPageSizeWithRotation(1));
			PdfCopy writer = new PdfCopy( document, out );
			
			document.open();

			 PdfImportedPage page;
             for (int i = 0; i < paginasOrg; ) {
                 ++i;
                 page = writer.getImportedPage(readerOrg, i);
                 writer.addPage(page);
             }

             for ( int j =0;j<paginasDest;){
            	 ++j;
            	 page = writer.getImportedPage(readerDest, j);
                 writer.addPage(page);            	 
             }

             document.close();
			
             out.flush();
             out.close();			
			
			

		} catch (Exception e) {
			e.printStackTrace(System.out);
			logger.error(e);
		}
	}
	
        /**
         *
         * @param impressaoJasper
         * @return
         */
        public static byte[] geraRelatorioPDF( JasperPrint impressaoJasper ){

		try{		
	
			byte[] relFile = JasperExportManager.exportReportToPdf(impressaoJasper); 
			if(relFile.length <= 0){
				logger.error( "<ERRO: Relatorio vazio>: Nao foi possivel gerar relatorio no formato PDF" );				
				return null;
			}
			return relFile;
			
		} catch (JRException e) {
			e.printStackTrace();
			logger.error( "<ERRO JasperReports>: Nao foi possivel gerar relatorio no formato PDF" , e );
			return null;
		} catch (Exception ex) {
				ex.printStackTrace();
				logger.error( "<ERRO>: Nao foi possivel gerar relatorio no formato PDF" , ex );
				return null;
		}
}
	
        /**
         *
         * @param relatorio
         * @param parametros
         * @param beanDS
         * @return
         */
        public static byte[] geraRelatorioPDF( JasperReport relatorio, Map parametros, JRBeanCollectionDataSource beanDS ){

		logger.info( "Gerando relatorio em formato PDF" );
		try {

			JasperPrint print = fillReport(relatorio, parametros, beanDS );
			byte[] relFile = geraRelatorioPDF(print);

			if(relFile.length <= 0){
				logger.error( "<ERRO: Relatorio vazio>: Nao foi possivel gerar relatorio no formato PDF" );				
				return null;
			}
			return relFile;
			
		}  catch (Exception ex) {
				ex.printStackTrace();
				logger.error( "<ERRO>: Nao foi possivel gerar relatorio no formato PDF" , ex );
				return null;
		}
	}
	
/**
*
* @param impressaoJasper
* @return
*/
   public static byte[] geraRelatorioExcel( JasperPrint impressaoJasper ){

		try{		
			JRXlsExporter exporter = new JRXlsExporter();     
		    ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();     
		    exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressaoJasper);    
		    exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, xlsReport);     
		    exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);     
		    exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);     
		    exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);     
		    //exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, "c:/relatorio.xls");  
		  
		      
		    exporter.exportReport();     
		    byte[] relFile = xlsReport.toByteArray(); 
		    			
			if(relFile.length <= 0){
				logger.error( "<ERRO: Relatorio vazio>: Nao foi possivel gerar relatorio no formato Excel" );				
				return null;
			}
			return relFile;
			
		}catch (Exception ex) {
				ex.printStackTrace();
				logger.error( "<ERRO>: Nao foi possivel gerar relatorio no formato Excel" , ex );
				return null;
		}
    }
}
