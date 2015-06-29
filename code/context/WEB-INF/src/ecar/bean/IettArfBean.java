/*
 * Created on 12/09/2006
 */
package ecar.bean;

import java.util.List;

import ecar.pojo.ItemEstruturaIett;



/**
 * Bean contendo lista de ARFs para um Item.<br> 
 * 
 * @author aleixo
 */

public class IettArfBean {
	
	private ItemEstruturaIett item;
	private List arfs;

	/**
	 * Get para atributo arfs
	 * @return
	 */
	public List getArfs() {
		return arfs;
	}
	
	/**
	 * Set para atributo arfs
	 * @param arfs
	 */
	public void setArfs(List arfs) {
		this.arfs = arfs;
	}
	
	/**
	 * Get para atributo item
	 * @return
	 */
	public ItemEstruturaIett getItem() {
		return item;
	}
	
	/**
	 * Set para atributo item
	 * @param item
	 */
	public void setItem(ItemEstruturaIett item) {
		this.item = item;
	}
}