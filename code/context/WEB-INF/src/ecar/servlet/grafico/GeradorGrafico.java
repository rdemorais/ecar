package ecar.servlet.grafico;

import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import comum.util.Pagina;
import comum.util.Util;

import ecar.api.facade.AcompanhamentoItemEstrutura;
import ecar.api.facade.Exercicio;
import ecar.api.facade.IndicadorResultado;
import ecar.exception.ECARException;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ItemEstrtIndResulIettr;

/**
 * Classe utilizada para gerar os gráficos de barras que apresentam
 * a evolução dos indicadores usados no acompanhamento. Além de gerar
 * o gráfico, essa classe é responsável por gerar o map do gráfico
 * possibilitando a navegação entre gráficos. 
 * 
 * 
 * @author N/A
 *
 */
public class GeradorGrafico {

	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private String map = null;
	private int width = 0;
	private int height = 0;
	
	Map<String, String> urlArguments = new HashMap<String, String>();
	
	
	
	public GeradorGrafico(){
		
	}


	/**
	 * Classe privada para guardar o gráfico e 
	 * o mapa de navegação do gráfico
	 * @author N/A
	 *
	 */
	private class EcarChart {
		JFreeChart chart;
		String map;
		int width;
		int height;
		
		public EcarChart(JFreeChart chart, String map, int height, int width){
			this.chart = chart;
			this.map = map;
			this.width = width;
			this.height = height;
		}
		
	}
	
	
	
	private EcarChart geraGrafico(String codAri, String codIettir, String codExe, String baseURL) throws NumberFormatException, ECARException{
	
		JFreeChart grafico  = null;
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		String categoria = "Total Realizado";
		String strIndicador = ""; 
		
		Map<String, String> urlArguments  = new HashMap<String, String>();
		
		AcompanhamentoItemEstrutura acompanhamento = new AcompanhamentoItemEstrutura(Long.valueOf(codAri));
		IndicadorResultado indicador = new IndicadorResultado(Long.valueOf(codIettir));
		
		strIndicador = indicador.getNome();
		
		//se o indicador pertence a um grupo, obtém os valores
		//dos indicadores do grupo
		if(indicador.hasLabelGrupo()){

			for(Exercicio exercicio: acompanhamento.getExercicios()){
				for(IndicadorResultado ind: indicador.getIndicadoresDoGrupo()){

					//previsto por exercicio
					dataset.addValue(ind.getPrevistoNoExercicio(exercicio), ind.getNome().concat(" (Previsto)"), exercicio.getDescricao());

					//coloca o valor previsto - mesmo link do realizado
					StringBuffer prevStrKey = new StringBuffer("codAri=");

					prevStrKey.append(codAri.toString()).append("&codExe=").
					append(exercicio.getId()).
					append("&codIettir=").append(ind.getId());

					urlArguments.put(ind.getNome().concat(" (Previsto)").concat(exercicio.getDescricao()), prevStrKey.toString());

					//value[valor, rowKey, columnKey] = [valor, nome do indicador, nome]
					dataset.addValue(ind.getRealizadoNoExercio(exercicio), ind.getNome().concat(" (Realizado)"), exercicio.getDescricao());

					StringBuffer realStrKey = new StringBuffer("codAri=");

					realStrKey.append(codAri.toString()).append("&codExe=").
					append(exercicio.getId()).
					append("&codIettir=").append(ind.getId());

					urlArguments.put(ind.getNome().concat(" (Realizado)").concat(exercicio.getDescricao()), realStrKey.toString());
				}
			}
		}else{//indicador não pertence a um grupo
			//series
			String PREVISTO = "Previsto";
			String REALIZADO = "Realizado";

			for(Exercicio exercicio: acompanhamento.getExercicios()){
				
				double realizado = indicador.getRealizadoNoExercicio(exercicio, acompanhamento);
				double previsto  = indicador.getPrevistoNoExercicio(exercicio);
				
				dataset.addValue(previsto, PREVISTO, exercicio.getDescricao());
				dataset.addValue(realizado, REALIZADO, exercicio.getDescricao());

				StringBuffer p = new StringBuffer("codAri=");
				p.append(codAri.toString()).append("&codExe=").append(exercicio.getRealObject().getCodExe().toString()).append("&codIettir=").append(indicador.getId());
				urlArguments.put(exercicio.getDescricao(), p.toString());
			}

		}

		
		grafico = ChartFactory.createBarChart3D("Valores Realizado por Exercício",
				"",
				strIndicador,
				dataset,
				PlotOrientation.VERTICAL,
				true,
				true,
				true);

		CategoryPlot plot = grafico.getCategoryPlot();

		BarRenderer renderer = (BarRenderer) plot.getRenderer();

		ItemLabelPosition posicao = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BOTTOM_CENTER,TextAnchor.CENTER,0);
		renderer.setPositiveItemLabelPosition(posicao);
		renderer.setItemLabelsVisible(true);
		
		Font labelFont = new Font("Times new Roman", 0, 15);
		renderer.setItemLabelFont(labelFont);
		//renderer.setLabelGenerator(new LabelGenerator(indicador.isMoeda()));
		
		if(urlArguments != null){
			StandardCategoryURLGenerator generator = new StandardCategoryURLGenerator(baseURL, urlArguments, indicador.hasLabelGrupo());
			renderer.setItemURLGenerator(generator);
		}
		
		TextTitle title = new TextTitle();
		title.setFont(new Font("Arial", Font.PLAIN, 15));
		title.setText("Valores Realizado por Exercício");

		grafico.setTitle(title);

		CategoryAxis categoryaxis = plot.getDomainAxis();
		categoryaxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 8)); 

		ValueAxis valueaxis = plot.getRangeAxis();
		valueaxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 8)); 

		int numExe = acompanhamento.getExercicios().size();
		// para o tamanho do gráfico é bom ser maior q 1
		if(numExe < 2){
			numExe = 2;
		}


		int comp = (numExe * 150);
		int altura = 400;

		
		//armazena o mapa da figura para poder navegar no mesmo
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
		
        try {
			ChartUtilities.writeChartAsPNG(bytes, grafico , comp, altura, info);
		} catch (IOException ioe) {
			this.logger.info("Erro ao tentar gerar o gráfico de realizado por exercício");
			this.logger.error(ioe);
			return null;
		}
        
		String map = ChartUtilities.getImageMap("chart", info);
		
		EcarChart ecarChart = new EcarChart(grafico, map, altura, comp);
		
		return ecarChart;
	}
	
	
	
	/**
	 * Cria o gráfico realizado x previsto por exercício
	 *
	 * @see GraficoRealizadoNoExercicio
	 * @param request
	 * @return
	 */
	public JFreeChart gerarGraficoPorExercicio(HttpServletRequest request){	
	
		
		String codAri = Pagina.getParam(request, "codAri"); //acompanhamento do item
		String codExe = Pagina.getParam(request, "codExe");
		String codIettir = Pagina.getParam(request, "codIettir");
		
		try {
			
			JFreeChart grafico;

			AcompanhamentoItemEstrutura a = new AcompanhamentoItemEstrutura(Long.valueOf(codAri));

			String baseURL2 = request.getContextPath().concat("/acompanhamento/resultado/popUpGraficoRealizadoNoExercicio.jsp");
			
			EcarChart myChart = geraGrafico(codAri, codIettir, codExe, baseURL2);
			
			grafico = myChart.chart;
			this.map = myChart.map;
			this.height = myChart.height;
			this.width = myChart.width;

			//armazena o mapa da figura para poder navegar no mesmo
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());

			try {
				ChartUtilities.writeChartAsPNG(bytes, grafico , myChart.width, myChart.height, info);
			} catch (IOException ioe) {
				this.logger.info("Erro ao tentar gerar o gráfico de realizado por exercício");
				this.logger.error(ioe);
				return null;
			}

			return grafico;


		}catch (ECARException e) {            
			this.logger.info("Erro ao tentar gerar o gráfico de realizado por exercício");
			this.logger.error(Util.getStackTrace(e));
		}catch(Exception e){
			this.logger.info("Erro ao tentar gerar o gráfico de realizado por exercício");
			this.logger.error(Util.getStackTrace(e));
		}
		
		return null;
	}

	
	public String getMap(){
		return map;
	}
	
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	


	
	
	   /**
     * Classe responsável pela geração do label utilizado no gráfico.
     */
    static class LabelGenerator implements CategoryItemLabelGenerator {
    	
    	private boolean moeda = false;
    	
    	/**
    	* Gera o label para o item especificado
    	*
    	* @param dataset
    	* @param series
    	* @param category
    	*
    	* @return O label que será utilizado no gráfico
    	*/    	
    	
    	public LabelGenerator(boolean moeda){
    		super();
    		this.moeda = moeda;
    	}
    	
    	public String generateColumnLabel(CategoryDataset arg0, int arg1) {
    		// TODO Auto-generated method stub
    		return null;
    	}
    	
    	public String generateRowLabel(CategoryDataset arg0, int arg1) {
    		// TODO Auto-generated method stub
    		return null;
    	}
    	
		public String generateLabel(CategoryDataset dataSet, int series, int category) {
			
	    	double valor = dataSet.getValue(series, category).doubleValue();
	    	if(valor == 0){
	    		return "";
	    	}else{
	    		if(moeda == true){
	    			return Util.formataMoeda(valor);
	    		}
	    		return Util.formataNumeroDecimal(valor);
	    	}
		}
    }
    


	/**
	 * Gerador de URL do gráfico. Cada barra apresentada estará apontando 
	 * para um link no formato: <application_path>/ecar/GraficoRealizadoNoExercicio?codAri=[x]&codExe=[y]&codIettir=[z] 
	 * 
	 * Os valores x,y,z são retirados do hash map urlArguments inseridos nos métodos:
	 * 
	 * @see GeradorGrafico#getDataSetPrevistoRealizadoPorExercicio(List, ItemEstrtIndResulIettr, AcompReferenciaItemAri, HttpServletRequest)
	 * @see GeradorGrafico#imprimeIndicadoresMesmoGrupo(ItemEstrtIndResulIettr, AcompReferenciaItemAri, List, Long, Long, HttpServletRequest)
	 * 	  
	 * @author N/A
	 *
	 */
	class StandardCategoryURLGenerator implements CategoryURLGenerator, Cloneable, Serializable {

		private String baseURL;
		private Map<String, String> urlArguments;
		private boolean agrupado = false;
		
		public StandardCategoryURLGenerator(String baseURL, Map<String, String> urlArguments, boolean agrupado) {
				this.baseURL = baseURL;
				this.urlArguments = urlArguments;
				this.agrupado = agrupado;
		}
		
		/**
		 * Retorna a url de um barra do gráfico
		 */
		public String generateURL(CategoryDataset dataset, int series, int category) {

			String url = null;

			if(baseURL == null || urlArguments == null){
				url = null;
			}else{
				if(agrupado){
					url = getURLDoGrupo(dataset, series, category);
				}else{
					url = getURLIndicador(dataset, series, category);
				}
			}
			return url;
		}
		
		
		public String getURLDoGrupo(CategoryDataset dataset, int series, int category){
			
			Comparable<Long> codExe = dataset.getColumnKey(category);
			Comparable<String> s1 = dataset.getRowKey(series);
			String parametros = urlArguments.get(s1.toString().concat(codExe.toString()));
			
			StringBuffer urlbuffer = new StringBuffer(baseURL).append("?").append(parametros).append("&voltar");
			return urlbuffer.toString();
		}

		public String getURLIndicador(CategoryDataset dataset, int series, int category){
			Comparable<Long> codExe = dataset.getColumnKey(category);
			String parametros = urlArguments.get(codExe);

			StringBuffer urlbuffer = new StringBuffer(baseURL).append("?").append(parametros).append("&voltar");
			return urlbuffer.toString();
			
		}
		
	}
}
