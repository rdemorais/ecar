package ecar.servlet.relatorio.dto;

import java.util.ArrayList;
import java.util.List;

public class MonitoramentoCicloDTO {
	private String objSigla;
	private String estNum;
	private List<MonitoramentoCicloEstrategiaDTO> estrategias = new ArrayList<MonitoramentoCicloEstrategiaDTO>();

	public String getObjSigla() {
		return objSigla;
	}
	public void setObjSigla(String objSigla) {
		this.objSigla = objSigla;
	}
	public String getEstNum() {
		return estNum;
	}
	public void setEstNum(String estNum) {
		this.estNum = estNum;
	}
	public List<MonitoramentoCicloEstrategiaDTO> getEstrategias() {
		return estrategias;
	}
	public void setEstrategias(List<MonitoramentoCicloEstrategiaDTO> estrategias) {
		this.estrategias = estrategias;
	}
	public void addEstrategia(MonitoramentoCicloEstrategiaDTO estrategia) {
		estrategias.add(estrategia);
	}
}