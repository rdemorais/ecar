package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsIndicadorAbrangencia")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsIndicadorAbrangencia {
	
	@XmlElement(required = true, nillable = false)
	private WsDominio abrangenciaDominio;

	@XmlElement(required = true, nillable = false)
	private String[] localizacoesIndicador;

	public WsIndicadorAbrangencia() {
	}

	public WsIndicadorAbrangencia(WsDominio abrangenciaDominio,
			String[] localizacoesIndicador) {
		this.abrangenciaDominio = abrangenciaDominio;
		this.localizacoesIndicador = localizacoesIndicador;
	}

	public WsDominio getAbrangenciaDominio() {
		return abrangenciaDominio;
	}

	public void setAbrangenciaDominio(WsDominio abrangenciaDominio) {
		this.abrangenciaDominio = abrangenciaDominio;
	}

	public String[] getLocalizacoesIndicador() {
		return localizacoesIndicador;
	}

	public void setLocalizacoesIndicador(String[] localizacoesIndicador) {
		this.localizacoesIndicador = localizacoesIndicador;
	}

}
