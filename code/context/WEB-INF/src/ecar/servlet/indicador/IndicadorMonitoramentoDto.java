package ecar.servlet.indicador;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IndicadorMonitoramentoDto {
	private Long id;
	private Long codAref;
	private Long codArf;
	private String tipo;
	private String nomeIndicador;
	private String unidadeMedida;
	private List<IndicadorValorDto> valores = new ArrayList<IndicadorValorDto>();
	public Long getId() {
		return id;
	}
	
	public Long getCodAref() {
		return codAref;
	}

	public void setCodAref(Long codAref) {
		this.codAref = codAref;
	}
	
	public Long getCodArf() {
		return codArf;
	}

	public void setCodArf(Long codArf) {
		this.codArf = codArf;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeIndicador() {
		return nomeIndicador;
	}
	public void setNomeIndicador(String nomeIndicador) {
		this.nomeIndicador = nomeIndicador;
	}
	public String getUnidadeMedida() {
		return unidadeMedida;
	}
	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}
	public List<IndicadorValorDto> getValores() {
		return valores;
	}
	public void setValores(List<IndicadorValorDto> valores) {
		this.valores = valores;
	}
	public void addValor(IndicadorValorDto valorDto) {
		this.valores.add(valorDto);
	}

	@Override
	public String toString() {
		return "IndicadorMonitoramentoDto [id=" + id + ", tipo=" + tipo
				+ ", nomeIndicador=" + nomeIndicador + ", unidadeMedida="
				+ unidadeMedida + ", valores=" + valores + "]";
	}
	
}