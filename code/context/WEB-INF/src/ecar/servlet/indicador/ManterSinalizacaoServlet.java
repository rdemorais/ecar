package ecar.servlet.indicador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ecar.dao.CorDao;
import ecar.exception.ECARException;
import ecar.pojo.Cor;
import ecar.sinalizacao.Faixa;
import ecar.sinalizacao.Sinalizacao;
import ecar.sinalizacao.dao.SinalizacaoDao;

public class ManterSinalizacaoServlet extends HttpServlet{

	private static final long serialVersionUID 		= -3384579562148134088L;
	private static final String CAD_SINALIZACAO_JSP	= "configuracoes/indicador/configSinalizacao.jsp";
	
	private CorDao corDao;
	private SinalizacaoDao sinalizacaoDao;
	
	public ManterSinalizacaoServlet() {
		corDao = new CorDao(null);
		sinalizacaoDao = new SinalizacaoDao();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getSession().getAttribute("seguranca") != null) {
			configurarAtributos(req);
			req.getRequestDispatcher(CAD_SINALIZACAO_JSP).forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String sinalizacaoJSON = req.getParameter("sinalizacaoJSON");
		
		try {
			Sinalizacao sinalizacao = deserialize(sinalizacaoJSON);
			sinalizacaoDao.salvar(sinalizacao);
		} catch (ECARException e) {
			e.printStackTrace();
		}
		
		configurarAtributos(req);
		req.getRequestDispatcher(CAD_SINALIZACAO_JSP).forward(req, resp);
	}
	
	private Sinalizacao deserialize(String sinalizacaoJSON) throws ECARException {
		Sinalizacao sinalizacao = new Sinalizacao();
		JsonObject jsonSin = new JsonParser().parse(sinalizacaoJSON).getAsJsonObject();
		sinalizacao.setIdentificacao(jsonSin.get("nome").getAsString());
		
		JsonArray faixasJson = jsonSin.getAsJsonArray("faixas");
		Faixa faixa;
		Cor cor;
		for (JsonElement faixaJson : faixasJson) {
			faixa = new Faixa();
			JsonObject fJson = faixaJson.getAsJsonObject();
			faixa.setMin(fJson.get("intervaloDe").getAsDouble());
			faixa.setMax(fJson.get("intervaloAte").getAsDouble());
			cor = (Cor)corDao.buscar(Cor.class, fJson.get("cor").getAsLong());
			faixa.setCor(cor);
			faixa.setSinalizacao(sinalizacao);
			
			sinalizacao.addFaixa(faixa);
		}
		
		return sinalizacao;
	}
	
	@SuppressWarnings("unchecked")	
	private void configurarAtributos(HttpServletRequest req) {
		List<Sinalizacao> listaSinalizacoes = new ArrayList<Sinalizacao>();
		List<Cor> listaCores = corDao.listarPorHQL("FROM Cor c WHERE c.indIndicadoresFisicosCor = 'S'");
		
		req.setAttribute("listaCores", listaCores);
		req.setAttribute("listaSinalizacoes", listaSinalizacoes);
	}
}