package ecar.servlet.relatorio.PPA_funcao;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;

import ecar.servlet.relatorio.PPA_funcao.bean.FuncaoAreaAreBean;
import ecar.util.jasper.JasperService;
import ecar.util.jasper.servlet.RelatorioServlet;

/**
 * Servlet responsavel por interagir com os eventos de geracao de relatorio PPA
 * @author gabrielsolana
 *
 */
public class RelatorioPPAFuncaoServlet extends RelatorioServlet {
	

	private static final long serialVersionUID = -2928712623369347910L;
	
	private Logger logger = Logger.getLogger(this.getClass());

        /**
         *
         */
        public RelatorioPPAFuncaoServlet(){
		super();
	}
	
        /**
         *
         * @param request
         * @param response
         */
        public void doRelatorioGet(HttpServletRequest request, HttpServletResponse response) {
		doRelatorioPost(request,response);
	}

        /**
         *
         * @param request
         * @param response
         */
        public void doRelatorioPost(HttpServletRequest request, HttpServletResponse response) {

		logger.info("Gerando relatorio PPA Por Funcao...");
		
		final ServletContext context = getServletContext();

		byte[] bytes = null;
		Map parametros = new HashMap();
		try {

			String titulo = request.getParameter("titulo");
			String pagina = request.getParameter("pagina");
			String data = request.getParameter("data");
			
			Integer paginaInt = null;
			
			try {
				if( pagina ==null || pagina.equals("") ){
					paginaInt = Integer.valueOf(1);
				}else{
					paginaInt = Integer.valueOf( pagina );
				}
			} catch (Exception e) {
				paginaInt = Integer.valueOf( 1 );
			}
			
			try {
				if( data ==null || data.equals("") ){
					data = "";
				}
			} catch (Exception e) {
				data = "";
			}			
			
			try {
				if( titulo ==null || "".equals( titulo ) ){
					titulo = "ESTADO DO PARANÁ - PLANO PLURIANUAL 2008/2011 - Demonstrativo por Função / Subfunção";	
				}
			} catch (Exception e) {
				titulo = "ESTADO DO PARANÁ - PLANO PLURIANUAL 2008/2011 - Demonstrativo por Função / Subfunção";
			}			
			
			
			final RelatorioPPAFuncaoService service = RelatorioPPAFuncaoService.getInstance(request);
						
			// path absoluto do relatorio PPA
			final String pathRelPPA = context.getRealPath( JasperService.PATH_RELATORIO + "/relatorioPPAFuncao.jasper" );
			final String pathRelPPASub = context.getRealPath( JasperService.PATH_RELATORIO + "/relatorioPPAFuncaoSub.jasper" );
			final String pathRelPPACapa = context.getRealPath( JasperService.PATH_RELATORIO + "/relatorioPPA_Demonstrativos_capa.jasper" );					

			JasperReport relatorioJasper = JasperService.loadReport( pathRelPPA );
			JasperReport relatorioCapaJasper = JasperService.loadReport( pathRelPPACapa );	
			this.redirectDefaultErrorIfNull(request, response, relatorioJasper);
			
			TreeSet<FuncaoAreaAreBean> relatorioDS = service.generatePPA();
			this.redirectDefaultErrorIfNull(request, response, relatorioDS);
			
			
			
			parametros.put("pagina", paginaInt );
			parametros.put( "data" , data );
			parametros.put( "titulo" , titulo );	
			parametros.put( "relatorioPPA_demonstrativo_funcao_PATH" , pathRelPPASub );
			parametros.put( "total1" , service.getTotalGeral1() );
			parametros.put( "total2" , service.getTotalGeral2() );
			parametros.put( "total3" , service.getTotalGeral3() );
			parametros.put( "total4" , service.getTotalGeral4() );
			parametros.put( "totalGeral" , service.getTotalGeral() );			
			
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(relatorioDS);
			
			JasperPrint capa = JasperService.fillReport(relatorioCapaJasper,parametros );
			JasperPrint rel = JasperService.fillReport(relatorioJasper,parametros, ds);
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JasperService.addRelatorio( capa, rel, out );			
			this.responseToPDF(response, out.toByteArray());
			
		} catch (Exception e) {
			logger.error( "Nao foi possivel gerar relatorio PPA por Função",e);
		}
		
	}
	


	
}
