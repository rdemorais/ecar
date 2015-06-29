package ecar.servlet.indicador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import comum.util.ConstantesECAR;
import comum.util.Util;

import ecar.dao.AcompRealFisicoDao;
import ecar.dao.AcompReferenciaDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.UsuarioUsu;

public class ManterRealizadoFisico extends HttpServlet {

	private static final long serialVersionUID = -67783813712685516L;
	
	private IndicadorMonitoramentoBean indMon;
	private AcompRealFisicoDao acompRealFisicoDao;
	private AcompReferenciaDao acompReferenciaDao;

	public ManterRealizadoFisico() {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getSession().getAttribute("seguranca") != null) {
			configuraIndicadores(req);
			configuraPeriodosAcompanhamento(req);
			req.getRequestDispatcher(ConstantesECAR.REALIZADO_FISICO_JSP).forward(req, resp);
		}
	}
	
	private void configuraIndicadores(HttpServletRequest req) {
		indMon = new IndicadorMonitoramentoBean();
		try {
			acompReferenciaDao = new AcompReferenciaDao(null);
			req.setAttribute("listaIndicadoresJson", indMon.jsonListaIndicadores(acompReferenciaDao.getUltimoPeriodoAcompanhamentoId()));
		} catch (ECARException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void configuraPeriodosAcompanhamento(HttpServletRequest req) {
		acompReferenciaDao = new AcompReferenciaDao(null);
		try {
			List refs = acompReferenciaDao.getListAcompReferencia();
			List<PeriodoAcompanhamentoDto> nomeRefs = new ArrayList<PeriodoAcompanhamentoDto>();
			PeriodoAcompanhamentoDto arefDto;
			for (Object arefO : refs) {
				AcompReferenciaAref aref = (AcompReferenciaAref) arefO;
				arefDto = new PeriodoAcompanhamentoDto();
				arefDto.setId(aref.getCodAref());
				arefDto.setNomeAref(aref.getNomeAref());
				nomeRefs.add(arefDto);
			}
			
			Gson gson = new Gson();
			String pJson = gson.toJsonTree(nomeRefs).toString();
			req.setAttribute("listaPeriodos", pJson);
		} catch (ECARException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		String selectPeriodo = req.getParameter("selectPeriodo");
		SegurancaECAR seg = (SegurancaECAR) req.getSession().getAttribute("seguranca");
		//Mudança de período de referência
		try {
			if(selectPeriodo != null) {
				trocaPeriodoReferencia(req, response);
			}else {
				//gravar valores
				gravarValores(seg.getUsuario(),deserialize(req.getParameter("indicadores")));
			}
		} catch (ECARException e) {
			e.printStackTrace();
		}
		
		response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
 
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");
	}
	
	private List<RealizadoDto> deserialize(String indicadoresJSON) {
		JsonArray realizados = new JsonParser().parse(indicadoresJSON).getAsJsonArray();
		List<RealizadoDto> lista = new ArrayList<RealizadoDto>();
		RealizadoDto dto;
		for (JsonElement reaJson : realizados) {
			JsonObject reaObj = reaJson.getAsJsonObject();
			dto = new RealizadoDto();
			dto.setIndId(reaObj.get("indId").getAsLong());
			dto.setValorRealizado(Double.parseDouble(Util.formataNumero(reaObj.get("valorRealizado").getAsString())));
			dto.setCodAref(reaObj.get("codAref").getAsLong());
			
			lista.add(dto);
		}
		return lista;
	}
	
	private void gravarValores(UsuarioUsu usu, List<RealizadoDto> realizados) throws NumberFormatException, ECARException {
		acompRealFisicoDao = new AcompRealFisicoDao(null);
		acompReferenciaDao = new AcompReferenciaDao(null);
		for (RealizadoDto realizado : realizados) {
			AcompReferenciaAref aref = (AcompReferenciaAref) acompReferenciaDao.buscarAref(realizado.getCodAref());
			Long mes = Long.parseLong(aref.getMesAref());
			Long ano = Long.parseLong(aref.getAnoAref());
			AcompRealFisicoArf arf = (AcompRealFisicoArf) acompRealFisicoDao.buscarPorIettir(mes, ano, realizado.getIndId());
			if(arf != null) {
				arf.setQtdRealizadaArf(realizado.getValorRealizado());
				arf.setDataUltManut(new Date());
				arf.setUsuarioUltManut(usu);
				acompRealFisicoDao.alterar(arf);
			}			
		}
	}
	
	private void trocaPeriodoReferencia(HttpServletRequest req, HttpServletResponse response) throws IOException, ECARException {
		PrintWriter out = response.getWriter();
		indMon = new IndicadorMonitoramentoBean();
		String selectPeriodo = req.getParameter("selectPeriodo");
		Long codAref = Long.parseLong(selectPeriodo);
		out.write(indMon.jsonListaIndicadores(codAref));
		out.close();
	}
}