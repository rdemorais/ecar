package ecar.servlet.relatorio;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.lang.StringUtils;

import ecar.dao.ItemEstruturaDao;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico;
import ecar.util.jasper.JasperService;
import ecar.util.jasper.servlet.RelatorioServlet;

public class RelatorioBijariServlet extends RelatorioServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doRelatorioGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			String tipoRelatorio = request.getParameter("tipoRelatorio");
			String codOEs = request.getParameter("codOEs");
			JasperPrint relatorio = null;
			RelatorioPE2012 relatorioPE = new RelatorioPE2012();
			
			if(StringUtils.equals(tipoRelatorio, "Executivo")){
				relatorio = relatorioPE.gerarRelatorioExecutivo();
			}
			else if(StringUtils.equals(tipoRelatorio, "OperacionalProdutos")){
				
				//ItemEstruturaIett iettPai = new ItemEstruturaDao(request).getAscendenteMaximo(item);
				relatorio = relatorioPE.gerarRelatorioOperacionalProdutos(codOEs, "relatorioProdutos");
			}
			else if(StringUtils.equals(tipoRelatorio, "OperacionalProdutosAcoes")){
				relatorio = relatorioPE.gerarRelatorioOperacionalProdutos(codOEs, "relatorioProdutosAcoes");
			}
			else if(StringUtils.equals(tipoRelatorio, "OperacionalProdutosAcoesAtividades")){
				relatorio = relatorioPE.gerarRelatorioOperacionalProdutos(codOEs, "relatorioProdutosAcoesAtividades");
			}
			else if(StringUtils.equals(tipoRelatorio, "Gerencial")){
				relatorio = relatorioPE.gerarRelatorioGerencial(codOEs);
			}
			else if (StringUtils.equals(tipoRelatorio, "Indicadores")) {
				relatorio = relatorioPE.gerarRelatorioIndicadores();
			}
			
			if(relatorio != null ){
				byte[] arquivo = JasperService.geraRelatorioPDF(relatorio);
				this.responseToPDF(response, arquivo);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void doRelatorioPost(HttpServletRequest request,
			HttpServletResponse response) {

		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);

		List<ObjetivoEstrategico> listaOE = itemEstruturaDao
				.listarObjetivoEstrategico(new UsuarioUsu(), null, null);

		for (ObjetivoEstrategico objetivoEstrategico : listaOE) {
			System.out.println(objetivoEstrategico.getCodCor());
			System.out.println(objetivoEstrategico.getNomeCor());
			objetivoEstrategico.getEstrategias();
		}

	}
}