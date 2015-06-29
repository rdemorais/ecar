/*
 * Created on 12/05/2005
 *
 */
package ecar.pojo;

import java.util.Date;

/**
 * @author felipe
 * Bean para encapsular Agendas e Segmentos. Utilizado na tela de manutenção de 
 * destaques onde estes dois objetos são tratados como se representassem 'Segmentos 
 * do Sistema'
 */
public class SegmentoAgendaBean {

	private Long codigo;
	private boolean agenda;
	private boolean segmento;
	private String descricao;
	private Date data;
	
	/**
	 * @return Returns the agenda.
	 */
	public boolean isAgenda() {
		return agenda;
	}
	/**
	 * @param agenda The agenda to set.
	 */
	public void setAgenda(boolean agenda) {
		this.agenda = agenda;
	}
	/**
	 * @return Returns the codigo.
	 */
	public Long getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo The codigo to set.
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return Returns the descricao.
	 */
	public String getDescricao() {
		return descricao;
	}
	/**
	 * @param descricao The descricao to set.
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	/**
	 * @return Returns the segmento.
	 */
	public boolean isSegmento() {
		return segmento;
	}
	/**
	 * @param segmento The segmento to set.
	 */
	public void setSegmento(boolean segmento) {
		this.segmento = segmento;
	}
	/**
	 * @return Returns the data.
	 */
	public Date getData() {
		return data;
	}
	/**
	 * @param data The data to set.
	 */
	public void setData(Date data) {
		this.data = data;
	}
}
