/*
 * Created on 28/02/2005
 *
 */
package ecar.grafico;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * @author garten
 */
public class GraficoPizza {
	
	private Logger logger = null;

    /**
     * Mostra Grafico Pizza.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public GraficoPizza() {
    	this.logger = Logger.getLogger(this.getClass());
        JFreeChart grafico;
        
        /* declara o dataSet */
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Java", Integer.valueOf(80));
        pieDataset.setValue("C++", Integer.valueOf(15));
        pieDataset.setValue("Php", Integer.valueOf(5));

        
        grafico = ChartFactory.createPieChart("Primeiro Grafico",
        									   pieDataset,
        									   true,
        									   true,
        									   true);
        
        try {
            ChartUtilities.saveChartAsJPEG(new File("/home/felipev/grafico.jpg"), grafico, 500, 300);
        } catch (IOException e) {
            this.logger.error(e);
        }
    }

}
