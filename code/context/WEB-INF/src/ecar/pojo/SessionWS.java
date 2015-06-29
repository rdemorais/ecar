package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Esta classe tem a função de armazenar um UUID e controlar o acesso de um usuário que acessa o conteúdo do sistema
 * através de um WebService.
 * 
 * O uso desta classe simula a Sessão de um ambiente HTTP.
 * 
 * @author Rafael Freitas de Morais
 * 30/06/2012
 */
public class SessionWS implements Serializable{

	private static final long serialVersionUID = 495981264442276587L;

	private Long codSessionWS;
	
	private String uuid;
	
	private Date dataHoraInicio;
	
	private UsuarioUsu usuarioUsu;

	public UsuarioUsu getUsuarioUsu() {
		return usuarioUsu;
	}

	public void setUsuarioUsu(UsuarioUsu usuarioUsu) {
		this.usuarioUsu = usuarioUsu;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getCodSessionWS() {
		return codSessionWS;
	}

	public void setCodSessionWS(Long codSessionWS) {
		this.codSessionWS = codSessionWS;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}
}