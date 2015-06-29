/*
 * Criado em 26/04/2005
 *
 */
package ecar.servlet.grafico;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import comum.util.Pagina;
import comum.util.Util;
import comum.util.grafico.FactoryCalculoProjecao;
import comum.util.grafico.Projection;

import ecar.dao.AcompRealFisicoDao;
import ecar.dao.AcompReferenciaDao;
import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.ExercicioDao;
import ecar.dao.ItemEstrtIndResulDao;
import ecar.exception.ECARException;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrutFisicoIettf;

/**
 * @author felipev
 *
 */
public class GraficoPrevisaoIndicadoresResultado extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8640912786694377079L;
	private Logger logger = null;
	
        /**
         *
         */
        public GraficoPrevisaoIndicadoresResultado() {
        this.logger = Logger.getLogger(this.getClass());
     }
    
        
        
    private Date getDataInicioProjecaoIndicador(String codIndicador, String codAri, boolean comQtde, boolean soPrevisao ) throws NumberFormatException, ECARException, ParseException{
		ItemEstrtIndResulDao itemEstrtIndResulDao = new ItemEstrtIndResulDao(null);
		
		AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(null);
		//Descobrir Meses
		ItemEstrtIndResulIettr indicador = (ItemEstrtIndResulIettr) itemEstrtIndResulDao.buscar(ItemEstrtIndResulIettr.class, Long.valueOf(codIndicador));
		
		int[] meses = acompReferenciaItemDao.getMesAnoInicioFimMatrizProjecao(indicador, comQtde, soPrevisao);
		
		int mes = meses[0];
		int ano = meses[1];
		
		StringBuffer data = new StringBuffer("01/");
		data.append(mes).append("/").append(ano);
		
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		Date dataInicio = formatador.parse(data.toString()); 
		
		return dataInicio;
    }
    
    
    private Date getDataFinalRealizadoIndicador(String codAri) throws NumberFormatException, ECARException, ParseException{
    	
    	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(null);
    	AcompReferenciaItemAri ari = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(codAri));
    	
		int mesFimRealizado = Integer.valueOf(ari.getAcompReferenciaAref().getMesAref()).intValue(); ;
		int anoFimRealizado = Integer.valueOf(ari.getAcompReferenciaAref().getAnoAref()).intValue(); ;
		
		StringBuffer data = new StringBuffer("01/");
		data.append(mesFimRealizado).append("/").append(anoFimRealizado);
		
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		Date dataFim = formatador.parse(data.toString()); 
		
		return dataFim;
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
    @SuppressWarnings({"empty-statement", "empty-statement"})
    @Override
    public final void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    try {
		
		JFreeChart grafico;
		
		List sMeses = new ArrayList();
		List sRealizado = new ArrayList();
		
		List<Double> valuesProjection = new ArrayList<Double>();
		
		int tipoProjecao = FactoryCalculoProjecao.MEDIA;
		
		try{
			tipoProjecao = Integer.parseInt(Pagina.getParamStr(request, "projecao"));
		}catch(Exception e){
			//se o valor é invalido fica com o valor padrao de projecao: 1
			
		}finally{
			//se a projecao tem um valor inválido, coloca-se o default
			if(tipoProjecao != FactoryCalculoProjecao.MEDIA && tipoProjecao != FactoryCalculoProjecao.MEDIA_ACELERADA){
				tipoProjecao = FactoryCalculoProjecao.MEDIA;
			}
		}
				
		ItemEstrtIndResulDao itemEstrtIndResulDao = new ItemEstrtIndResulDao(null);

		ExercicioDao exercicioDao = new ExercicioDao(null);
		AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(null);

		AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(null);
		//Descobrir Meses
		ItemEstrtIndResulIettr indicador = (ItemEstrtIndResulIettr) itemEstrtIndResulDao.buscar(ItemEstrtIndResulIettr.class, Long.valueOf(Pagina.getParamStr(request,"codIndicador")));
		AcompReferenciaItemAri ari = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(Pagina.getParamStr(request,"codAri")));
		
		boolean comQtde = ("S".equals(Pagina.getParamStr(request, "comQtde")) ? true : false);
		boolean soPrevisao = ("S".equals(Pagina.getParamStr(request, "soPrevisao")) ? true : false);
		
		int[] meses = acompReferenciaItemDao.getMesAnoInicioFimMatrizProjecao(indicador, comQtde, soPrevisao);
		
		int mesInicio = meses[0];
		int anoInicio = meses[1];
		
		if(soPrevisao){
			mesInicio = Integer.valueOf(Pagina.getParamStr(request, "mesIni")).intValue();
			anoInicio = Integer.valueOf(Pagina.getParamStr(request, "anoIni")).intValue();
		}
		else {
			mesInicio = meses[0];
			anoInicio = meses[1];
		}
		
		int mesFimRealizado = Integer.valueOf(ari.getAcompReferenciaAref().getMesAref()).intValue(); ;
		int anoFimRealizado = Integer.valueOf(ari.getAcompReferenciaAref().getAnoAref()).intValue(); ;
		
		TimeSeriesCollection data = new TimeSeriesCollection();

		TimeSeries serie1 = new TimeSeries("Realizado", Month.class);
		TimeSeries serie2 = new TimeSeries("Projetado", Month.class);
		TimeSeries serie3 = new TimeSeries("Previsto", Month.class);

		int auxMes = mesInicio;
		int auxAno = anoInicio;
		
		int i = 1;
		double qtdeAnterior = 0;		

		while(auxAno < anoFimRealizado || (auxAno == anoFimRealizado && auxMes <= mesFimRealizado)){
			String strMesInicio = Integer.valueOf(auxMes).toString();
			if(strMesInicio.length() == 1)
				strMesInicio = "0" + strMesInicio;
			String strAnoInicio = Integer.valueOf(auxAno).toString();
			Double qtde = new AcompRealFisicoDao(null).getQtdRealizadaMesAno(indicador, Long.valueOf(strMesInicio), Long.valueOf(strAnoInicio));
			double qtdeSerie = 0;
						
			//utilizar o valor referência para o início da projeção [por Thaise].
			if (indicador.getIndiceMaisRecenteIettr()!= null && qtdeAnterior==0) {
				qtdeAnterior = indicador.getIndiceMaisRecenteIettr().doubleValue();
			}
			
			if(qtde != null){
				//qtdeSerie = qtdeAnterior + qtde.doubleValue();
				//FIXME: Verificar regra do não-acumulável: Está pegando o valor pq não tem uma lista de valores para comparar o último/maior.
				if("S".equals(indicador.getIndAcumulavelIettr())){
					qtdeSerie = qtdeAnterior + qtde.doubleValue();
				}else {
					qtdeSerie = qtde.doubleValue();
				}
				qtdeAnterior = qtdeSerie;
			} else 
				qtdeSerie = qtdeAnterior;
			serie1.add(new Month(auxMes, auxAno), qtdeSerie);
			valuesProjection.add(qtdeSerie);
	
			sMeses.add(Integer.valueOf(i));
			
			sRealizado.add(new Double(qtdeSerie));
			auxMes++;
			if(auxMes == 13){
				auxMes = 1;
				auxAno++;
			}
			i++;
		}
		//Avança 1 mes
		int mesAnt = auxMes;
		int anoAnt = auxAno;
		if(mesAnt - 1 == 0){
			mesAnt = 12;
			anoAnt--;
		} else {
			mesAnt--;
		}

		int mesFimProjecao = mesFimRealizado;
		int anoFimProjecao = anoFimRealizado;
		int xProjetado = 0;
		
			
		int[] fimProjecao = acompReferenciaItemDao.getMesAnoFimProjecao(indicador);
		mesFimProjecao = fimProjecao[0];
		anoFimProjecao = fimProjecao[1];
		
		//Descobrir o xProjetado (índice que mesFimProjecao/anoFimProjecao teria na matriz de realizados)
		if(anoFimProjecao == anoFimRealizado){
			xProjetado = i + (mesFimProjecao - mesFimRealizado);
		} else {
			while(anoFimProjecao > anoFimRealizado){ // Correção Thaise [antes era um !=]
				mesFimRealizado++;
				if(mesFimRealizado == 13){
					anoFimRealizado++;
					mesFimRealizado = 1;
				}
				xProjetado++;
			}
			xProjetado += i + mesFimProjecao;
		}
			
		//Descobrir até onde vai o PREVISTO
		String strMesInicio = Integer.valueOf(mesInicio).toString();
		if(strMesInicio.length() == 1)
			strMesInicio = "0" + strMesInicio;
		String strMesFim = Integer.valueOf(mesFimProjecao).toString();
		if(strMesFim.length() == 1)
			strMesFim = "0" + strMesFim;

		String strAnoInicio = Integer.valueOf(anoInicio).toString();
		String strAnoFim = Integer.valueOf(anoFimProjecao).toString();


		ExercicioExe exercicioInicial = acompReferenciaDao.getExercicio(strAnoInicio, strMesInicio);
		ExercicioExe exercicioFinal = acompReferenciaDao.getExercicio(strAnoFim, strMesFim);
		
		List exerciciosAnteriores = new ArrayList();
		List exerciciosPosteriores = new ArrayList();
		if(exercicioFinal!=null)
			exerciciosAnteriores = exercicioDao.getExerciciosAnteriores(exercicioFinal);
		if(exercicioInicial!=null)
			exerciciosPosteriores = exercicioDao.getExerciciosPosteriores(exercicioInicial);
		
		Set exerciciosDoPeriodo = new HashSet();
		exerciciosDoPeriodo.addAll(Util.intersecao(exerciciosAnteriores, exerciciosPosteriores));
		if(exercicioInicial!=null)
			exerciciosDoPeriodo.add(exercicioInicial);
		if(exercicioFinal!=null)
			exerciciosDoPeriodo.add(exercicioFinal);
		


		double previstoFinal = 0;

		ArrayList<ItemEstrutFisicoIettf> previstos = new ArrayList<ItemEstrutFisicoIettf>(indicador.getItemEstrutFisicoIettfs());
		if(previstos!=null && previstos.size()>0 && (previstos.get(0).getAnoIettf()!=anoInicio || previstos.get(0).getMesIettf()!=mesInicio)){
			serie3.addOrUpdate(new Month(mesInicio,anoInicio),0);
		}
		for(ItemEstrutFisicoIettf previsto: previstos){
			if("S".equals(indicador.getIndAcumulavelIettr())){
				previstoFinal+=previsto.getQtdPrevistaIettf();
			}else{
				previstoFinal=previsto.getQtdPrevistaIettf();
			}
			serie3.addOrUpdate(new Month(previsto.getMesIettf(),previsto.getAnoIettf()),previstoFinal);
		}

		//plotar valores projetados até ultimo mes de projecao, ou então até que projetado > previstoFinal 
		double ultimoProjetado = 0;
		double projetadoAnterior = acompReferenciaItemDao.calcularPrevistoMes(sMeses, sRealizado, i);
		while(i <= xProjetado){				
			i++;
			auxMes++;
			if(auxMes==13){
				auxMes = 1;
				auxAno++;
			}
			ultimoProjetado = acompReferenciaItemDao.calcularPrevistoMes(sMeses, sRealizado, i);
			
			qtdeAnterior += (ultimoProjetado - projetadoAnterior);
			projetadoAnterior = ultimoProjetado;
			serie2.add(new Month(auxMes, auxAno), qtdeAnterior);
			if(qtdeAnterior > previstoFinal) //qtdeAnterior jah é maior que previsto final, break it!
				break;
		}
		
	
			//Continua a segunda série até ultrapassar previstoFinal (caso já não tenha ultrapassado)
			while(qtdeAnterior < previstoFinal && qtdeAnterior>0){
				i++;
				auxMes++;
				if(auxMes > 12){
					auxMes = 1;
					auxAno++;
				}
				ultimoProjetado = acompReferenciaItemDao.calcularPrevistoMes(sMeses, sRealizado, i);
				
				qtdeAnterior += (ultimoProjetado - projetadoAnterior);
				projetadoAnterior = ultimoProjetado;
			}

		if(!soPrevisao)
			data.addSeries(serie1);
		
		if(comQtde){
			Date start = getDataInicioProjecaoIndicador(Pagina.getParam(request, "codIndicador"), Pagina.getParam(request, "codAri"), comQtde, soPrevisao);
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(start);

			Projection myProjection = FactoryCalculoProjecao.getProjection(tipoProjecao, valuesProjection, calendar);
			
			TimeSeries projectionSerie = new TimeSeries("Projetado", Month.class);
			
			calendar.add(GregorianCalendar.MONTH,valuesProjection.size()-1);
			
			Map<GregorianCalendar,Double> projection = myProjection.getMapProjection(calendar, myProjection.getDate(previstoFinal));
			
			
			for(GregorianCalendar date: projection.keySet()){
				projectionSerie.add(new Month(date.get(GregorianCalendar.MONTH) + 1, date.get(GregorianCalendar.YEAR)), projection.get(date));
			}

			data.addSeries(projectionSerie);
		}
		
		data.addSeries(serie3);
		
		
		
		data.setDomainIsPointsInTime(true);
 
		grafico = ChartFactory.createTimeSeriesChart(
				indicador.getNomeIettir(),
				"Meses",
				"Quantidade Realizada", 
				data,
				true,
				true,
				true
				);
		
		// coloca os marcadores nas linhas
		XYPlot plot = grafico.getXYPlot();
		XYItemRenderer renderer = plot.getRenderer();
		if(renderer instanceof StandardXYItemRenderer){
			StandardXYItemRenderer rr = (StandardXYItemRenderer) renderer;
			//rr.setPlotShapes(true);
			rr.setShapesFilled(true);
		} 
		
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setAutoRange(false);
		axis.setVerticalTickLabels(true);
		
		axis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH, 1));
		axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		
	    response.setContentType("image/jpeg");	        
	    ChartUtilities.writeChartAsPNG(response.getOutputStream(), grafico, i * 35, 470);
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
			        
    } catch (Exception e) {     
    	this.logger.error(e);
    }
}


    

}
