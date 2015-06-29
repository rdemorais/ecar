package ecar.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import ecar.dao.AcompReferenciaItemDao;
import ecar.pojo.acompanhamentoEstrategico.Estrategia;
import ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico;
import ecar.pojo.acompanhamentoEstrategico.Resultado;

/**
 * Classe respons�vel por gerar os gr�ficos tipo "pizza" dos relat�rios Bijari.
 * @author ivans.silva
 */
public class GraficoUtils {
	/**
	 * M�todo responsavel por gerar uma imgem contendo o gr�fico.
	 * @param largura
	 * @param altura
	 * @param exercicio TODO
	 * @return imagem
	 * @deprecated Use {@link #gerarGraficoResultados(List<Long>,int,int,Integer,boolean,Font,Long,Long)} instead
	 */
	public static BufferedImage gerarGraficoResultados(List<Long> idsResultados, int largura, int altura, Integer codTpfa, boolean isPrioritario, Font fonteLegendas, Long exercicio){
		return gerarGraficoResultados(idsResultados, largura, altura, codTpfa,
				isPrioritario, fonteLegendas, exercicio, null);
	}

	/**
	 * M�todo responsavel por gerar uma imgem contendo o gr�fico.
	 * @param largura
	 * @param altura
	 * @param exercicio TODO
	 * @param acompReferencia TODO
	 * @return imagem
	 */
	public static BufferedImage gerarGraficoResultados(List<Long> idsResultados, int largura, int altura, Integer codTpfa, boolean isPrioritario, Font fonteLegendas, Long exercicio, Long acompReferencia){
		BufferedImage imagemGrafico = null;
		DefaultPieDataset dataset = new DefaultPieDataset();
		JFreeChart grafico = null;
		AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(null);
		List resultado = acompReferenciaItemDao.obterLegendasGraficoRelatorioGerencial(codTpfa, idsResultados, isPrioritario, exercicio, acompReferencia);
		int somaRegistros = somarQtdRegistros(resultado);
		
		for(int i = 0; i < resultado.size(); i++){
			Object [] resultados = (Object[]) resultado.get(i);
			dataset.setValue(montarLegenda(resultados[2], resultados[3], somaRegistros), Integer.parseInt(resultados[2].toString()));
		}
		
		grafico = ChartFactory.createPieChart(null, dataset, false, false, false); 
        
        PiePlot plot = (PiePlot)grafico.getPlot(); 
        plot.setBackgroundPaint(new Color(255,255,255));
        plot.setOutlinePaint(null);
        plot.setLabelFont(fonteLegendas);
        plot.setLabelGap(0.02);
        plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setShadowPaint(Color.WHITE);
        
        Color[] cores = montarCoresFatiasGrafico(resultado);

        CoresGrafico coresGrafico = new CoresGrafico(cores); 
        coresGrafico.setColor(plot, dataset);
        
        imagemGrafico = grafico.createBufferedImage(largura, altura);
		
		return imagemGrafico;
	}
	
	/**
	 * @deprecated Use {@link #gerarGraficoProdutos(List<Long>,int,int,Integer,boolean,Font,Long)} instead
	 */
	public static BufferedImage gerarGraficoProdutos(List<Long> idsResultados, int largura, int altura, Integer codTpfa, boolean isPrioritario, Font fonteLegendas){
		return gerarGraficoProdutos(idsResultados, largura, altura, codTpfa,
				isPrioritario, fonteLegendas, null, null);
	}

	/**
	 * @deprecated Use {@link #gerarGraficoProdutos(List<Long>,int,int,Integer,boolean,Font,Long,Long)} instead
	 */
	public static BufferedImage gerarGraficoProdutos(List<Long> idsResultados, int largura, int altura, Integer codTpfa, boolean isPrioritario, Font fonteLegendas, Long exercicio){
		return gerarGraficoProdutos(idsResultados, largura, altura, codTpfa,
				isPrioritario, fonteLegendas, exercicio, null);
	}

	public static BufferedImage gerarGraficoProdutos(List<Long> idsResultados, int largura, int altura, Integer codTpfa, boolean isPrioritario, Font fonteLegendas, Long exercicio, Long acompReferencia){
		BufferedImage imagemGrafico = null;
		DefaultPieDataset dataset = new DefaultPieDataset();
		JFreeChart grafico = null;
		AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(null);
		List resultado = acompReferenciaItemDao.obterLegendasGraficoRelatorioGerencial(codTpfa, idsResultados,isPrioritario, exercicio, acompReferencia);
		int somaRegistros = somarQtdRegistros(resultado);
		
		for(int i = 0; i < resultado.size(); i++){
			Object [] resultados = (Object[]) resultado.get(i);
			dataset.setValue(montarLegenda(resultados[2], resultados[3], somaRegistros), Integer.parseInt(resultados[2].toString()));
		}
		
		grafico = ChartFactory.createPieChart(null, dataset, false, false, false); 
        
        PiePlot plot = (PiePlot)grafico.getPlot(); 
        plot.setBackgroundPaint(new Color(255,255,255));
        plot.setOutlinePaint(null);
        plot.setLabelFont(fonteLegendas);
        plot.setLabelGap(0.02);
        plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setShadowPaint(Color.WHITE);
        
        Color[] cores = montarCoresFatiasGrafico(resultado);

        CoresGrafico coresGrafico = new CoresGrafico(cores); 
        coresGrafico.setColor(plot, dataset);
        
        imagemGrafico = grafico.createBufferedImage(largura, altura);
		
		return imagemGrafico;
	}
	
	private static Color[] montarCoresFatiasGrafico(List resultado){
		Color [] cores = new Color[resultado.size()];
		for(int i = 0; i < resultado.size(); i++){
			Object [] resultados = (Object[]) resultado.get(i);
			Color cor = Color.decode(resultados[1]!=null?resultados[1].toString():"#FFFFFF");
			cores[i] = cor;
		}
		
		return cores;
	}
	
	private static int somarQtdRegistros(List resultado){
		int soma = 0;
		for(int i = 0; i < resultado.size(); i ++){
			Object [] resultados = (Object[]) resultado.get(i);
			int qtd = Integer.parseInt(resultados[2].toString());
			soma = soma + qtd;	
		}
		
		return soma;
	}
	
	private static String montarLegenda(Object qtdRegistros, Object status, int somaRegistros){
		StringBuilder legenda = new StringBuilder();
		legenda.append(montarLegendaPercentual(Integer.parseInt(qtdRegistros.toString()), somaRegistros));
		legenda.append(status.toString().toUpperCase());
		
		return legenda.toString();
	}
	
	private static String montarLegendaPercentual(int qtdRegistros, int somaRegistros){
		StringBuilder legendaPercentual = new StringBuilder();
		double percentual = (qtdRegistros * 100) / somaRegistros;
		legendaPercentual.append(percentual);
		legendaPercentual.append("% = ");
		legendaPercentual.append(qtdRegistros);
		legendaPercentual.append("\n");
		
		return legendaPercentual.toString();
	}
	/**
	 * Classe respons�vel por colorir o gr�fico de acordo com as cores informadas.
	 * @author ivans.silva
	 *
	 */
	public static class CoresGrafico 
    { 
        private Color[] color; 
        
        public CoresGrafico(Color[] color) 
        { 
            this.color = color; 
        }        
        /**
         * M�todo respons�vel por colorir o gr�fico.
         * @param plot
         * @param dataset
         */
        @SuppressWarnings("unchecked")
		public void setColor(PiePlot plot, DefaultPieDataset dataset) 
        { 
            List <Comparable> keys = dataset.getKeys(); 
            int aInt; 
            
            for (int i = 0; i < keys.size(); i++) 
            { 
                aInt = i % this.color.length; 
                plot.setSectionPaint(keys.get(i), this.color[aInt]);
            } 
        } 
    }
	
	public static List<Long> extrairIdsResultados(List<ObjetivoEstrategico> listaOE) {
		List<Long> ids = new ArrayList<Long>();
		for (ObjetivoEstrategico oe : listaOE) {
			for (Estrategia est : oe.getEstrategias()) {
				for (Resultado r : est.getResultados()) {
					ids.add(r.getCodigo());
				}
			}
		}
		return ids;
	}
}