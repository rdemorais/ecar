package ecar.servlet.relatorio.dto.marcas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comum.util.Ordena;

public class RedeCicloDTO {
	private String rede = "";
	private String eixo = "";
	private String acao = "";
	private String depResp = "";
	private String responsavel = "";
	private String parecer = "";
	private String situacao = "";
	private List<RedeCicloIndicadorDTO> listIndicadores = new ArrayList<RedeCicloIndicadorDTO>();

	public RedeCicloDTO() {
		
	}
	
	public RedeCicloDTO(String rede, String tipoIndicador, String nome) {
		this.rede = rede;
		RedeCicloIndicadorDTO ind = new RedeCicloIndicadorDTO();
		ind.setTipoIndicador(tipoIndicador);
		ind.setNomeIndicador(nome);
	}
	
	public String getRede() {
		return rede;
	}
	public void setRede(String rede) {
		this.rede = rede;
	}
	public String getEixo() {
		return eixo;
	}
	public void setEixo(String eixo) {
		this.eixo = eixo;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public String getDepResp() {
		return depResp;
	}
	public void setDepResp(String depResp) {
		this.depResp = depResp;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public String getParecer() {
		return parecer;
	}
	public void setParecer(String parecer) {
		this.parecer = parecer;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public List<RedeCicloIndicadorDTO> getListIndicadores() {
		return listIndicadores;
	}
	public void setListIndicadores(List<RedeCicloIndicadorDTO> listIndicadores) {
		this.listIndicadores = listIndicadores;
	}
	public void addIndicador(RedeCicloIndicadorDTO ind) {
		this.listIndicadores.add(ind);
	}
	
	public void ordenaIndicadores() {
		try {
			//Separa as listas dos tipos de indicador
			Map<String, List<RedeCicloIndicadorDTO>> mapTipoIndicador = new  HashMap<String, List<RedeCicloIndicadorDTO>>();
			for (RedeCicloIndicadorDTO indicador : listIndicadores) {
				if(!mapTipoIndicador.containsKey(indicador.getTipoIndicador())) {
					mapTipoIndicador.put(indicador.getTipoIndicador(), new ArrayList<RedeCicloIndicadorDTO>());	
				}
				mapTipoIndicador.get(indicador.getTipoIndicador()).add(indicador);
			}
			Ordena<RedeCicloIndicadorDTO> ordenacao = new Ordena<RedeCicloIndicadorDTO>();
			
			listIndicadores.clear();
			
			for (String key : mapTipoIndicador.keySet()) {
				List<RedeCicloIndicadorDTO> lIndicadores = mapTipoIndicador.get(key);
				//Ordena cada tipo pela ordem
				ordenacao.ordenarLista(lIndicadores, "getOrdem");
				//Adiciona na lista geral. Agora agrupado e ordenado
				listIndicadores.addAll(lIndicadores);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ordenaIndicadoresAcao() {
		try {
			//Separa as listas dos tipos de indicador
			Map<String, List<RedeCicloIndicadorDTO>> mapTipoIndicador = new  HashMap<String, List<RedeCicloIndicadorDTO>>();
			for (RedeCicloIndicadorDTO indicador : listIndicadores) {
				if(!mapTipoIndicador.containsKey(indicador.getTipoIndicador())) {
					mapTipoIndicador.put(indicador.getTipoIndicador(), new ArrayList<RedeCicloIndicadorDTO>());	
				}
				mapTipoIndicador.get(indicador.getTipoIndicador()).add(indicador);
			}
			Ordena<RedeCicloIndicadorDTO> ordenacao = new Ordena<RedeCicloIndicadorDTO>();
			
			listIndicadores.clear();
			
			for (String key : mapTipoIndicador.keySet()) {
				List<RedeCicloIndicadorDTO> lIndicadores = mapTipoIndicador.get(key);
				//Ordena cada tipo pela ordem
				ordenacao.ordenarLista(lIndicadores, "getAcao");
				//Adiciona na lista geral. Agora agrupado e ordenado
				listIndicadores.addAll(lIndicadores);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		RedeCicloDTO other = (RedeCicloDTO) obj;
		if(this.rede.equals(other.getRede())){
			return true;
		}
		return false;
	}
	
}