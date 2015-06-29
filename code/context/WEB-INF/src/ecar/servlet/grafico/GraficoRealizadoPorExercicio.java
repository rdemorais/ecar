/*
 * Criado em 26/04/2005
 *
 */
package ecar.servlet.grafico;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

/**
 * @author evandro
 *
 */
public class GraficoRealizadoPorExercicio extends HttpServlet{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6114387130036357031L;
	private Logger logger = null;
    
        /**
         *
         */
        public GraficoRealizadoPorExercicio() {
        this.logger = Logger.getLogger(this.getClass());
        
     }
    
    /**
     * Gera gráfico com valores do realizado por exercicio.<br>
     * Esse servlet é chamado em popUpGraficoRealizadoPorExercicio.jsp
     * passando como parâmetros (codAri, codExe, codIettir)
     * 
     * 
     * @param request
     * @param response
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public final void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	
    	GeradorGrafico grafico = new GeradorGrafico();
    	JFreeChart chart = grafico.gerarGraficoPorExercicio(request);
    	
    	if(chart != null){
        	response.setContentType("image/jpeg");
        	ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, grafico.getWidth(), grafico.getHeight());
	        response.getOutputStream().flush();
	        response.getOutputStream().close();
    	}else{
    		response.getOutputStream().println("Não foi possível gerar o gráfico");
	        response.getOutputStream().flush();
	        response.getOutputStream().close();
    	}
    		
    } 	
   
 }