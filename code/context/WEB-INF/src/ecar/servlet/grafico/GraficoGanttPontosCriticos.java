package ecar.servlet.grafico;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.ui.TextAnchor;

import comum.util.Data;
import comum.util.Pagina;

import ecar.pojo.PontoCriticoPtc;

/**
 *
 * @author 70744416353
 */
public class GraficoGanttPontosCriticos extends HttpServlet {

	private Logger logger = null;
	
	/**
	 * Constructor of the object.
	 */
    public GraficoGanttPontosCriticos() {
        this.logger = Logger.getLogger(this.getClass());
     }
	
    /**
     * Gera Grafico de Previsao dos Indicadores de Resultado.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public final void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
	    try {
	    	
	    	HttpSession session = request.getSession();
	        session.setAttribute("lang","pt");
	        
	    	List pontosCriticosSolucionados =  (ArrayList) request.getSession().getAttribute("listPontosCriticos");
	    	
	    	String labelPontosCriticos = Pagina.getParamStr(request, "labelPontosCriticos");
	    
	    	Date dataBase = new Date(Pagina.getParamStr(request, "dataBase"));
	    	
	    	IntervalCategoryDataset dataSet = criarDataSetSolucionadoAteDataBase(pontosCriticosSolucionados, dataBase);
	    	
	    	if (dataSet != null){
		    	
	    		JFreeChart grafico = gerarGrafico(dataSet, labelPontosCriticos, pontosCriticosSolucionados.size());
		    		    	
		    	response.setContentType("image/jpeg");        

		    	ChartUtilities.writeChartAsJPEG(response.getOutputStream(), grafico, 1200, 120 + (pontosCriticosSolucionados.size() *35));
			    response.getOutputStream().flush();
			    response.getOutputStream().close();
	    	}	    	
	    	
	    } catch (IOException e) {
			this.logger.error(e);
			throw new ServletException(e);
		} catch (Exception e) {
			this.logger.error(e);
			throw new ServletException(e);
		}
    }
    
    private static IntervalCategoryDataset criarDataSet(List pontosCriticosSolucionados, Date dataBase){
    	    	
    	TaskSeries sDatasPrevistas = new TaskSeries("Data Limite");
    	TaskSeries sDatasFim = new TaskSeries("Data Fim");
    	    	    	
    	Iterator itPontosCriticosSolucionados = pontosCriticosSolucionados.iterator();
    	boolean possuiAlgumaDataSolucao = false;	
    	
    	while (itPontosCriticosSolucionados.hasNext()){
    		
    		PontoCriticoPtc pontoCriticoSolucionado = (PontoCriticoPtc) itPontosCriticosSolucionados.next();
    			    		
    		//Verificar data base
    		if (pontoCriticoSolucionado.getDataSolucaoPtc() != null && pontoCriticoSolucionado.getDataSolucaoPtc().compareTo(dataBase) <= 0){
    			
    			Date dataLimite = pontoCriticoSolucionado.getDataLimitePtc();
    			possuiAlgumaDataSolucao = true;
    			
    			if (pontosCriticosSolucionados.size() == 1){
    				if (dataLimite != null){
    					sDatasPrevistas.add(new Task(pontoCriticoSolucionado.getDescricaoPtc(),
    							date(Data.getAno(dataLimite), Data.getMes(dataLimite), Data.getDia(dataLimite)-1),
    							date(Data.getAno(dataLimite), Data.getMes(dataLimite), Data.getDia(dataLimite) + 1)));
    				}
    				
			
					Date dataFim = pontoCriticoSolucionado.getDataSolucaoPtc();
					
					if (dataFim != null){
						sDatasFim.add(new Task(pontoCriticoSolucionado.getDescricaoPtc(),
		    					date(Data.getAno(dataFim), Data.getMes(dataFim), Data.getDia(dataFim)-1),
		    					date(Data.getAno(dataFim), Data.getMes(dataFim), Data.getDia(dataFim) + 1)));
					}
    			}
    			else{
    				if (dataLimite != null){
    					sDatasPrevistas.add(new Task(pontoCriticoSolucionado.getDescricaoPtc(),
    							date(Data.getAno(dataLimite), Data.getMes(dataLimite), Data.getDia(dataLimite)-1),
    							date(Data.getAno(dataLimite), Data.getMes(dataLimite), Data.getDia(dataLimite) + pontosCriticosSolucionados.size()/2)));
    				}
	    				    			
	    			Date dataFim = pontoCriticoSolucionado.getDataSolucaoPtc();
	    			if (dataFim != null){
	    				sDatasFim.add(new Task(pontoCriticoSolucionado.getDescricaoPtc(),
		    					date(Data.getAno(dataFim), Data.getMes(dataFim), Data.getDia(dataFim)-1),
		    					date(Data.getAno(dataFim), Data.getMes(dataFim), Data.getDia(dataFim) + pontosCriticosSolucionados.size()/2)));
	    			}
	    			
    			}
    			
    		}
    	}
    	
    	TaskSeriesCollection colecao = new TaskSeriesCollection();
    	
    	
    	if (possuiAlgumaDataSolucao){    	
	    	
	    	colecao.add(sDatasPrevistas);
	    	colecao.add(sDatasFim);
    	}
    	
        return colecao;
    }
    
    private static IntervalCategoryDataset criarDataSetSolucionadoAteDataBase(List pontosCriticosSolucionados, Date dataBase){
    	    	
    	TaskSeries sDatasPrevistas = new TaskSeries("Data Limite");
    	TaskSeries sDatasFim = new TaskSeries("Data Fim");
    	    	    	
    	Iterator itPontosCriticosSolucionados = pontosCriticosSolucionados.iterator();
    	boolean possuiAlgumaDataSolucao = false;	
    	
    	while (itPontosCriticosSolucionados.hasNext()){
    		
    		PontoCriticoPtc pontoCriticoSolucionado = (PontoCriticoPtc) itPontosCriticosSolucionados.next();
    			    		
//    		//Verificar data base
//    		if (pontoCriticoSolucionado.getDataSolucaoPtc() != null && pontoCriticoSolucionado.getDataSolucaoPtc().compareTo(dataBase) <= 0){
    			
			Date dataLimite = pontoCriticoSolucionado.getDataLimitePtc();
			Date dataFim = pontoCriticoSolucionado.getDataSolucaoPtc();
			possuiAlgumaDataSolucao = true;
			
			if (pontosCriticosSolucionados.size() == 1){
				if (dataLimite != null){
					sDatasPrevistas.add(new Task(pontoCriticoSolucionado.getDescricaoPtc(),
							date(Data.getAno(dataLimite), Data.getMes(dataLimite), Data.getDia(dataLimite)-1),
							date(Data.getAno(dataLimite), Data.getMes(dataLimite), Data.getDia(dataLimite) + 1)));
				} 
		
				if (dataFim != null && dataFim.compareTo(dataBase) <= 0){
					sDatasFim.add(new Task(pontoCriticoSolucionado.getDescricaoPtc(),
    					date(Data.getAno(dataFim), Data.getMes(dataFim), Data.getDia(dataFim)-1),
    					date(Data.getAno(dataFim), Data.getMes(dataFim), Data.getDia(dataFim) + 1)));
				}
			}
			else{
				if (dataLimite != null){
					sDatasPrevistas.add(new Task(pontoCriticoSolucionado.getDescricaoPtc(),
							date(Data.getAno(dataLimite), Data.getMes(dataLimite), Data.getDia(dataLimite)-1),
							date(Data.getAno(dataLimite), Data.getMes(dataLimite), Data.getDia(dataLimite) + pontosCriticosSolucionados.size()/2)));
				}
    			
    			if (dataFim != null && dataFim.compareTo(dataBase) <= 0){
	    			sDatasFim.add(new Task(pontoCriticoSolucionado.getDescricaoPtc(),
		    					date(Data.getAno(dataFim), Data.getMes(dataFim), Data.getDia(dataFim)-1),
		    					date(Data.getAno(dataFim), Data.getMes(dataFim), Data.getDia(dataFim) + pontosCriticosSolucionados.size()/2)));
    			}
			}
    	} //fim while
    	
    	TaskSeriesCollection colecao = new TaskSeriesCollection();
    	
    	
    	if (possuiAlgumaDataSolucao){    	
	    	
	    	colecao.add(sDatasPrevistas);
	    	colecao.add(sDatasFim);
    	}
    	
        return colecao;
    }
    
    private static Date date(int year, int month, int day) {

        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        final Date result = calendar.getTime();
        return result;

    }
    
    private JFreeChart gerarGrafico(IntervalCategoryDataset dataSet, String labelPontosCriticos, int numeroPontosCriticos) {
        
    	JFreeChart chart = ChartFactory.createGanttChart(
    		labelPontosCriticos,  // chart title
            "Descrição",         // domain axis label
            "Data",              // range axis label
            dataSet,             // data
            true,                // include legend
            true,                // tooltips
            false                // urls
        );
    	    	    	    	
        //chart.getCategoryPlot().getDomainAxis().setMaxCategoryLabelWidthRatio(10.0f);
    	    	
        chart.setBackgroundPaint(Color.white);
        
        CategoryPlot plot = chart.getCategoryPlot();
        
        //barras verticais e horizontais do gráfico
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);   
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
        
        //eixo das datas
        ValueAxis rangeAxis = (ValueAxis) plot.getRangeAxis();
        rangeAxis.setUpperMargin(5.0 / (double) numeroPontosCriticos);
                
        //eixo das descrições
        CategoryAxis domainAxis = (CategoryAxis) plot.getDomainAxis();
        domainAxis.setLowerMargin(0.10);
        domainAxis.setCategoryMargin(0.2);
                                
        CategoryItemRenderer renderer = plot.getRenderer();
        
        //Fonte das datas
        Font labelFont = new Font("Times new Roman", 0, 10);
        renderer.setItemLabelFont(labelFont);
        
        //Posições das datas
        renderer.setPositiveItemLabelPosition(new ItemLabelPosition(   
        		ItemLabelAnchor.OUTSIDE2, TextAnchor.CENTER_LEFT), true);
        
        //renderer.setLabelGenerator(new LabelGenerator());
        
        renderer.setItemLabelsVisible(true);
             	
    	return chart;
    }
    
    
    static class LabelGenerator implements CategoryItemLabelGenerator {
    	    	
    	/**
    	* Generates a label for the specified item. The label is typically a
    	* formatted version of the data value, but any text can be used.
    	*
    	* @param dataset the dataset (<code>null</code> not permitted).
    	* @param series the series index (zero-based).
    	* @param category the category index (zero-based).
    	*
    	* @return The label (possibly <code>null</code>).
    	*/    	
		public String generateLabel(CategoryDataset dataSet, int series, int category) {
			
			String result = null;
	    	Number value = dataSet.getValue(series, category);
	    	
	    	if (value != null) {
	    		
		    	long v = value.longValue()+1;
		    	Date date = new Date(v);
		    	date = Data.addDias(1, date);
//		    	date = Data.addMeses(1, date);
		    	int dia = Data.getDia(date);
		    	if (dia == 32 || dia == 0)
		    		dia = 1;
		    	int mes = Data.getMes(date) + 1;		    	
		    	if (mes == 0 || mes == 13)
		    		mes = 1;
		    	result = dia + "/" + 
		    			mes + "/" + Data.getAno(date);
		    	
	    	}
	    	return result;
		}

		public String generateColumnLabel(CategoryDataset arg0, int arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		public String generateRowLabel(CategoryDataset arg0, int arg1) {
			// TODO Auto-generated method stub
			return null;
		}
    }
}
