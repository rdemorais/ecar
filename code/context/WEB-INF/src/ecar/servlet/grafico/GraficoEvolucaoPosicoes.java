/*
 * Criado em 26/04/2005
 *
 */
package ecar.servlet.grafico;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import comum.util.Pagina;
import comum.util.Util;

import ecar.dao.CorDao;
import ecar.dao.OrgaoDao;
import ecar.exception.ECARException;
import ecar.pojo.Cor;
import ecar.pojo.OrgaoOrg;
import ecar.servlet.grafico.bean.PosicaoBean;

/**
 * @author felipev
 * 
 */
public class GraficoEvolucaoPosicoes extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1924552455684086649L;
	private Logger logger = null;

	/**
	 * Construtor.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public GraficoEvolucaoPosicoes() {
		this.logger = Logger.getLogger(this.getClass());

	}

	/**
	 * Gera Grafico de Evolucao das Posicoes.<br>
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
	public final void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			
			 HttpSession session = request.getSession();
		     session.setAttribute("lang","pt");
			
			List listAvaliacoes = (List) request.getSession().getAttribute(	"listAvaliacoes");
			CorDao corDao = new CorDao(null);
			OrgaoDao orgaoDao = new OrgaoDao(null);
			OrgaoOrg orgao = null;
			String msgGrafico = "";


			String strOrgao = "Em Monitoramento";
			if (!"".equals(Pagina.getParamStr(request, "codOrgao"))) {
				orgao = (OrgaoOrg) orgaoDao.buscar(OrgaoOrg.class, Long
						.valueOf(Pagina.getParamStr(request, "codOrgao")));
				if (orgao.getSiglaOrg() != null
						&& !"".equals(orgao.getSiglaOrg()))
					strOrgao = orgao.getSiglaOrg();
				else
					strOrgao = orgao.getDescricaoOrg();
			}

			JFreeChart grafico;
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			Map<String, Integer> mapCoresConfiguradas = null; // Map para as cores configuradas, chave:codigo da cor; valor:quantidade de vez em que apareceu
			List <String>listOrdemCores = null;		
			
			Iterator itAvaliacoes = listAvaliacoes.iterator();

			while (itAvaliacoes.hasNext()) {
				PosicaoBean posicaoBean = (PosicaoBean) itAvaliacoes.next();

				String serie = posicaoBean.getARef().getNomeAref();
				mapCoresConfiguradas = corDao.contadorDePosicoesPorCores(posicaoBean);
				
				//So precisa ordenar na primeira vez
				if (listOrdemCores==null)
					listOrdemCores = corDao.ordenarCores(mapCoresConfiguradas.keySet());

				// Adiciona os tï¿½tulos das cores no Grï¿½fico 
				for (String codCorConfig : listOrdemCores) {
					if (codCorConfig.equals(Cor.BRANCO)) {
						dataset.addValue(mapCoresConfiguradas.get(codCorConfig),Cor.LABEL_BRANCO, serie);
					} else if (codCorConfig.equals(Cor.NAO_LIBERADO)) {
						dataset.addValue(mapCoresConfiguradas.get(codCorConfig),Cor.LABEL_NAO_LIBERADO, serie);
//					} else if (codCorConfig.equals(Cor.NAO_ACOMPANHADO)) {
//						dataset.addValue(mapCoresConfiguradas.get(codCorConfig),Cor.LABEL_NAO_ACOMPANHADO, serie);
					} else if (Util.ehValor(codCorConfig)){
						Cor corConfig = (Cor) corDao.buscar(Cor.class, Long.parseLong(codCorConfig));

						//Para cores de grï¿½fico nï¿½o configurada exibe um * no final do nome 
						if (corConfig.getCodCorGrafico()==null && !corConfig.getSignificadoCor().endsWith(" *")) {
							corConfig.setSignificadoCor(corConfig.getSignificadoCor() + " *");
						} 

						dataset.addValue(mapCoresConfiguradas.get(codCorConfig),corConfig.getSignificadoCor(), serie);
					}// fim if ehValor
				}// fim for
			}

			grafico = ChartFactory.createBarChart3D("Evolução das Posições - " 
					+ strOrgao, "Período(s) de Referência", "Quantidades",
					dataset, PlotOrientation.VERTICAL, true, true, true);

			CategoryPlot plot = grafico.getCategoryPlot();

			TextTitle title = new TextTitle();
			title.setFont(new Font("Arial", Font.PLAIN, 15));
			title.setText("Evolução das Posições - " + strOrgao);

			grafico.setTitle(title);

			CategoryAxis categoryaxis = plot.getDomainAxis();
			categoryaxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 8));

			ValueAxis valueaxis = plot.getRangeAxis();
			valueaxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 8));

			// Renderer modifica propriedades
			CategoryItemRenderer renderer = plot.getRenderer();

			int numeroCor = 0;
			
			//define as cores das barras do grï¿½fico
			for (String codCorConfig : listOrdemCores) {
				
				if (Util.ehValor(codCorConfig) ) {
					Cor corConfig = (Cor) corDao.buscar(Cor.class, Long.parseLong(codCorConfig));

					if (corConfig.getCodCorGrafico()!=null){
						Color colorConfig = new Color(Integer.parseInt(corConfig.getCodCorGrafico().replace("#", ""), 16));
						renderer.setSeriesPaint(numeroCor++, colorConfig);
					 } else {
						 renderer.setSeriesPaint(numeroCor++, Color.DARK_GRAY);
					 }
				} // fim ehValor
				else if (codCorConfig.equals(Cor.BRANCO)) {
					// BRANCO - Nï¿½o informado 
					renderer.setSeriesPaint(numeroCor++, Color.WHITE);
				} //fim BRANCO 
				else if (codCorConfig.equals(Cor.NAO_LIBERADO)) {
					//N/L 
					renderer.setSeriesPaint(numeroCor++, Color.LIGHT_GRAY);
				} // fim N/L
//				else if (codCorConfig.equals(Cor.NAO_ACOMPANHADO)) {
//				// N/A
//				 renderer.setSeriesPaint(numeroCor++, Color.BLUE);
//				}
				
			}// fim for
			
			
			response.setContentType("image/jpeg");
			int comp = (listAvaliacoes.size() * 150);

			if (comp <= 150) {
				comp = comp * 2;
			}
			ChartUtilities.writeChartAsJPEG(response.getOutputStream(), grafico, comp, 450);

			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (IOException e) {
			this.logger.error(e);
			throw new ServletException(e);
		} catch (ECARException e) {
			this.logger.error(e);
			throw new ServletException(e);
		} catch (Exception e) {
			this.logger.error(e);
			throw new ServletException(e);
		}
	}


}
