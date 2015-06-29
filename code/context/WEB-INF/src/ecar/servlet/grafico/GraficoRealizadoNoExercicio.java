/*
 * Criado em 25/05/2005
 *
 */
package ecar.servlet.grafico;

import java.awt.Font;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;

import ecar.api.facade.AcompanhamentoItemEstrutura;
import ecar.api.facade.EcarData;
import ecar.api.facade.Exercicio;
import ecar.api.facade.IndicadorResultado;
import ecar.api.facade.Previsto;
import ecar.api.facade.Realizado;
import ecar.dao.AcompRealFisicoDao;
import ecar.dao.ExercicioDao;
import ecar.dao.ItemEstrtIndResulDao;
import ecar.exception.ECARException;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstrtIndResulIettr;

/**
 * @author evandro
 *
 */
public class GraficoRealizadoNoExercicio extends HttpServlet{

	
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -7922981954605986999L;
	private Logger logger = null;
	
	
	/**
	 * Construtor
	 *
	 * @author N/C
     * @since N/C
     * @version N/C
	 */
    public GraficoRealizadoNoExercicio() {
        this.logger = Logger.getLogger(this.getClass());
        
     }
    
    
    
    
    /**
     * Gera Grafico dos valores realizados no exercicio.<br>
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
	        JFreeChart grafico;
	        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	        
	        String categoria = "Realizado";
	        String previsto = "Previsto";
	        String serie = "";
	        String indicador = "";
	        String titulo = "Valores Mensais Realizado no Exercício";
	        int numMeses = 0;
	        boolean existeLabelGrupo = false;
	        
	        executaRequest(request);
	        
	        long codInd = Long.valueOf(Pagina.getParamStr(request, "codIettir"));
	        IndicadorResultado indicadorResultado = new IndicadorResultado(codInd);
	        titulo = indicadorResultado.getNome();
	        
	        boolean isMoeda = indicadorResultado.isMoeda();
	        
	        Exercicio exe = null;
	        
	        try{
	        	exe = new Exercicio(Long.valueOf(Pagina.getParamStr(request, "codExe")));
	        }catch(Exception e){
	        	exe = null;
	        }
	        
	        
	        if(!"".equals(Pagina.getParamStr(request, "codExe"))){
	        	dataset = executaRequest(request);
	        }else{

	        	if(exe != null){
	        		serie = exe.getDescricao();
	        		dataset.addValue(0, categoria, serie);
	        	}else{
	        		dataset.addValue(0, categoria, serie);
	        	}
	        }
	       
	        
	        grafico = ChartFactory.createBarChart3D(titulo,
	                                               "",
	                                               titulo,
	                                               dataset,
	                                               PlotOrientation.VERTICAL,
	                                               true,
	                                               true,
	                                               false);
	                
	        CategoryPlot plot = grafico.getCategoryPlot();
	        
	        //Cria o renderer que será utilizado para apresentar o valor de cada barra no gráfico.
	        CategoryItemRenderer renderer = plot.getRenderer();
	        ItemLabelPosition posicao = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BOTTOM_CENTER,TextAnchor.CENTER,0);
	        renderer.setPositiveItemLabelPosition(posicao);
	        renderer.setItemLabelsVisible(true);
	        Font labelFont = new Font("Times new Roman", 0, 15);
	        renderer.setItemLabelFont(labelFont);
	        //renderer.setLabelGenerator(new LabelGenerator(isMoeda));
	        
	        
	        TextTitle title = new TextTitle();
	        title.setFont(new Font("Arial", Font.PLAIN, 15));
	        title.setText(titulo);
	        
	        grafico.setTitle(title);        
	        
	        CategoryAxis categoryaxis = plot.getDomainAxis();
	        categoryaxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 8)); 
	
	        ValueAxis valueaxis = plot.getRangeAxis();
	        valueaxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 8)); 
	                
	        
	        //para o tamanho do gráfico é bom ser maior q 2
	        numMeses = dataset.getColumnCount();
	        if(numMeses < 6){
	        	numMeses = 6;
	        }
	        
	        response.setContentType("image/jpeg");
	        int aumentaComp = 0;
	        if(existeLabelGrupo){
	        	aumentaComp = dataset.getRowCount() * 80;
	        }
	        int comp = (numMeses * 100 + aumentaComp);
	        int altura = 400;
	        ChartUtilities.writeChartAsPNG(response.getOutputStream(), grafico, comp, altura);
	        response.getOutputStream().flush();
	        response.getOutputStream().close();
	        
	    }catch (IOException e) {        
	    	
	    	 StringWriter sw = new StringWriter();
	         PrintWriter pw = new PrintWriter(sw, true);
	         e.printStackTrace(pw);
	         pw.flush();
	         sw.flush();
	        this.logger.error(sw.toString()); 	
            this.logger.error(e);
	    }catch (ECARException e) {
	    	
	    	 StringWriter sw = new StringWriter();
	         PrintWriter pw = new PrintWriter(sw, true);
	         e.printStackTrace(pw);
	         pw.flush();
	         sw.flush();
	    	
            this.logger.error(e);
	    }
    }
    

    /**
     * Retorna true se todos os códigos podem ser convertidos para long
     * @param ids
     * @return
     */
    private boolean isValid(String... ids){
    	
    	boolean valid = false;
    	
    	if(ids.length > 0){
    		try{	
    			for(String cod: ids){
    				long res = 	Long.valueOf(cod);
    			}
    		}catch(Exception e){
    			return false;	
    		}

    		return true;
    	}
    	
    	return valid;
    }
    
    
    
    private DefaultCategoryDataset executaRequest(HttpServletRequest request) throws ECARException{

    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String REALIZADO = "Realizado";
        String PREVISTO = "Previsto";
        String serie = "";

    	if(isValid(Pagina.getParamStr(request, "codAri"), Pagina.getParamStr(request, "codIettir"), Pagina.getParamStr(request, "codExe"))){
        	long codAri = Long.valueOf(Pagina.getParamStr(request, "codAri"));
        	long codIettir = Long.valueOf(Pagina.getParamStr(request, "codIettir"));
        	long codExe = Long.valueOf(Pagina.getParamStr(request, "codExe"));

        	AcompanhamentoItemEstrutura acompanhamento =  new AcompanhamentoItemEstrutura(codAri);
        	IndicadorResultado indicador = new IndicadorResultado(codIettir);
        	Exercicio exercicio = new Exercicio(codExe);
                	
        	List<EcarData> meses = null;
        	
        	List<EcarData> mesesExercicio = exercicio.getMeses();
        	
        	EcarData dataInicioItem = new EcarData(indicador.getItemEstrutura().getDataInicial());
        	EcarData dataFimItem = new EcarData(indicador.getItemEstrutura().getDataFinal());
        	
        	meses = new ArrayList<EcarData>();
        	//pega os meses do item somente
        	for(EcarData data: mesesExercicio){
        		if(EcarData.pertenceAoIntervalo(data, dataInicioItem, dataFimItem)){
        			meses.add(new EcarData(data.getMes(), data.getAno()));
        		}
        	}
        	
        	if(indicador.hasLabelGrupo()){
        		//pega o indicadores do grupo
        		List<IndicadorResultado> indicadoresDoGrupo = indicador.getIndicadoresDoGrupo();
        		
        		for(IndicadorResultado indGrupo: indicadoresDoGrupo){
                	for(EcarData mes: meses){

                		double value = 0;
                		Previsto p = indGrupo.getPrevistoMensal(mes);
                		if( p != null ) value = p.getValorPrevisto();
                		dataset.addValue(value, indGrupo.getNome().concat(" (Previsto)"), mes.getDataFormatada());
                		
                		Realizado r = indGrupo.getRealizadoMensal(mes);
                		double valor = (r == null)? 0 : r.getRealizado();
                		dataset.addValue(valor, indGrupo.getNome().concat(" (Realizado)"), mes.getDataFormatada());
                	}
        		}
        	}else{
            	for(EcarData mes: meses){

            		double value = 0;
            		Previsto p = indicador.getPrevistoMensal(mes);
            		if( p != null ) value = p.getValorPrevisto();
            		dataset.addValue(value, PREVISTO, mes.getDataFormatada());
            		
            		Realizado r = indicador.getRealizadoMensal(mes);
            		double valor = (r == null)? 0 : r.getRealizado();
            		dataset.addValue(valor, REALIZADO, mes.getDataFormatada());
            	}
        	}
        }else{
        	Exercicio exe = null;
        	try{
        		exe = new Exercicio(Long.valueOf(Pagina.getParamStr(request, "codExe")));
        	}catch(Exception e){
        		exe = null;
        	}
        	
        	if(exe != null) serie = exe.getDescricao();
        	
        	dataset.addValue(0, "Realizado", serie);
        }
    	
    	return dataset;
    	
    }
    
    
    
    
    
    /**
     *  Retorna um DefaultCategoryDataet com todas as metas/indicadores do item que participam do mesmo "Gráfico de Grupo do Indicador" e/ou que foram cadastradas como públicas
     *  e que participam do mesmo Gráfico de Grupo do Indicador.
     * 
     * @param indResul
     * @param exercicio 
     * @param mesRef
     * @param anoRef
     * @param request
     * @return
     * @throws ECARException
     */
    private DefaultCategoryDataset imprimeIndicadoresMesmoGrupo(ItemEstrtIndResulIettr indResul,ExercicioExe exercicio, int mesRef, int anoRef, HttpServletRequest request) throws ECARException{
    	
    	ItemEstrtIndResulDao indResulDao = new ItemEstrtIndResulDao(null);
    	AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(null);
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	List listaIndicadoresGrupo = indResulDao.retornaIndicadoresGraficoGrupo(indResul);
		Iterator itListaIndicadoresGrupo = listaIndicadoresGrupo.iterator();
		String serie = "";
		
		
		//Percorre a lista de indicadores que pertencem ao mesmo Grupo indicador.
		while(itListaIndicadoresGrupo.hasNext()){
			ItemEstrtIndResulIettr itemIndicadorIettr = (ItemEstrtIndResulIettr) itListaIndicadoresGrupo.next();
			
			Set listaIndicadorAcompRealFisico = itemIndicadorIettr.getAcompRealFisicoArfs();
			Iterator itListaIndicadorAcompRealFisico = listaIndicadorAcompRealFisico.iterator();
			
			//Percorre os acompanhamentos físicos realizados de cada indicador.
			while(itListaIndicadorAcompRealFisico.hasNext()){
				AcompRealFisicoArf indicadorAcompRealFisico = (AcompRealFisicoArf) itListaIndicadorAcompRealFisico.next();
				
				List exerciciosAnterioresIndicador = new ExercicioDao(request).getExerciciosProjecaoByExercicio(indicadorAcompRealFisico.getItemEstruturaIett().getCodIett(),exercicio.getCodExe());
				Iterator itExAnterioresIndicador = exerciciosAnterioresIndicador.iterator();
					
				//Percorre todos os exercícios do acompanhamento físico em questão.
				while(itExAnterioresIndicador.hasNext()){
				
					ExercicioExe exercicioIndicador = (ExercicioExe) itExAnterioresIndicador.next();
					
					List meses = new ArrayList();
					
					//Verifica se o exercício do indicador é o mesmo exercício do item a partir do qual o gráfico será gerado.
					if(exercicio.getCodExe().equals(exercicioIndicador.getCodExe())){
		    			meses = new ExercicioDao(null).getMesesDentroDoExercicio(exercicio,mesRef,anoRef);
		    			
		    			Map mapMesesIndicadorGrupo = acompRealFisicoDao.getQtdRealizadaExercicioPorMes(exercicioIndicador, indicadorAcompRealFisico.getItemEstrtIndResulIettr());			
			    		
			    		Iterator it = meses.iterator();
			    		
			    		//Verifica nos acompanhamentos físicos os valores informados até o mês atual e os adiciona ao dataset. 
			    		while(it.hasNext()){
			    			String mes = it.next().toString();
			    			String abrvMes = mes.split("-")[0];
			    			String ano = mes.split("-")[1];
			    			
			    			if(Integer.parseInt(abrvMes) <= mesRef){
				    			
				    			serie = Data.getAbreviaturaMes(Integer.valueOf(abrvMes).intValue()); //Abreviatura do mês
				    			double valor = 0;
				    			if(mapMesesIndicadorGrupo.get(mes) != null) {
				    				valor = Double.parseDouble(mapMesesIndicadorGrupo.get(mes).toString());
				    			}				
				    			dataset.addValue(valor, itemIndicadorIettr.getNomeIettir(), serie);
				    			IndicadorResultado indicador = new IndicadorResultado(itemIndicadorIettr);
				    			
				           		double value = 0;
			            		Previsto p = indicador.getPrevistoMensal(Integer.valueOf(abrvMes), Integer.valueOf(ano));
			            		if( p != null ) value = p.getValorPrevisto();
				    			dataset.addValue(value, "Previsto", serie);
			    			}
			    		}
		    		}
				}
			}
		}
		return dataset;
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