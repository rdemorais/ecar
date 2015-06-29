package ecar.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import comum.util.Data;
import comum.util.Util;

import ecar.bean.AtributoEstruturaListagemItens;
import ecar.bean.OrdenacaoIett;
import ecar.dao.EstruturaDao;
import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturaSisAtributoIettSatb;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.SisAtributoSatb;
import ecar.taglib.util.Input;

/**
 *
 * @author 70744416353
 */
public class ActionEstrutura {
	
			
	/**
	 * Este metodo é baseado na estrutura virtual passada e monta um map cuja chave é a estrutura real dos itens 
	 * associados e o valor é a lista de itens associados correspondente a sua chave(estrutura real).
	 * Ex.: map<estrutura real,lista de itens associados a estrutura virtual>    
	 * @param estrutura
	 * @return
	 */
	public Map montarMapItensEstruturaVirtual(EstruturaEtt estrutura){
		
		Map<EstruturaEtt,List<ItemEstruturaIett>> map = new HashMap<EstruturaEtt, List<ItemEstruturaIett>>();
		
		Set<ItemEstruturaIett> itensEstruturaVirtual = estrutura.getItensEstruturaVirtual();
						
		if (itensEstruturaVirtual != null && !itensEstruturaVirtual.isEmpty()) {
		
			for (ItemEstruturaIett itemEstrutura : itensEstruturaVirtual) {
				
				List item = map.get(itemEstrutura.getEstruturaEtt());
				
				if (item == null){
					List<ItemEstruturaIett> listaInner = new ArrayList<ItemEstruturaIett>();
					listaInner.add(itemEstrutura);
					map.put(itemEstrutura.getEstruturaEtt(), listaInner);
				} else {
					item.add(itemEstrutura);
				}
				
				
			}
		}
		
		return map;
		
	}
	
	
	/**
	 * Este metodo é baseado na estrutura virtual passada e monta um map cuja chave é a estrutura real dos itens 
	 * associados e o valor é a lista de itens associados correspondente a sua chave(estrutura real).
	 * Ex.: map<estrutura real,lista de itens associados a estrutura virtual> apenas os que tem permissão de acesso na tela de cadastro.   
	 * @param estrutura
     * @param seguranca
     * @return
     * @throws ECARException
	 */
	public Map montarMapItensEstruturaVirtualComPermissao(EstruturaEtt estrutura, SegurancaECAR seguranca) throws ECARException {
		
		Map<EstruturaEtt,List<ItemEstruturaIett>> map = new HashMap<EstruturaEtt, List<ItemEstruturaIett>>();
		
		Set<ItemEstruturaIett> itensEstruturaVirtual = estrutura.getItensEstruturaVirtual();
	
		ValidaPermissao validaPermissao = new ValidaPermissao();

		boolean permissaoAcessoItem = false;
		
		try {
			if (itensEstruturaVirtual != null && !itensEstruturaVirtual.isEmpty()) {
			
				for (ItemEstruturaIett itemEstrutura : itensEstruturaVirtual) {
					
					List item = map.get(itemEstrutura.getEstruturaEtt());
					
					//verifica a permissão de consultar o item e se ele está ativo ou não
					if ((itemEstrutura.getIndAtivoIett() != null || !"".equals(itemEstrutura.getIndAtivoIett().trim())) 
							&& !"N".equals(itemEstrutura.getIndAtivoIett().toUpperCase())) {
					
						validaPermissao.permissoesItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso());				
						permissaoAcessoItem = validaPermissao.permissaoConsultarItem();
				
					} else {
						permissaoAcessoItem = false;
					}	
					
					if (item == null){
						List<ItemEstruturaIett> listaInner = new ArrayList<ItemEstruturaIett>();
					
						if(permissaoAcessoItem){
							listaInner.add(itemEstrutura);
							map.put(itemEstrutura.getEstruturaEtt(), listaInner);
						}
			
					} else {
			
						if(permissaoAcessoItem ){
							item.add(itemEstrutura);
						}	
					}
				}
				
				Set<EstruturaEtt> estruturas = map.keySet();
				Iterator<EstruturaEtt> estruturasIt = estruturas.iterator();
				while(estruturasIt.hasNext()) {
					EstruturaEtt estruturaEtt = (EstruturaEtt)estruturasIt.next(); 
					List<ItemEstruturaIett> itens = map.get(estruturaEtt);
					List lColunas = new EstruturaDao(null).getAtributosAcessoEstruturaArvore(estruturaEtt);
					if(lColunas != null && lColunas.size() > 0) {
						itens = this.getItensOrdenados(itens, estruturaEtt, lColunas);
					} else {
						itens = this.getItensOrdenados(itens, estruturaEtt, null);
					}
					map.put(estruturaEtt, itens);
				}
					
			}	
				
		} catch (Exception e) {
			return map;
		}
		
		return map;
		
	}
	
	/**
	 * Este metodo retorna a lista de itens correspondente a sua estrutura real de forma ordenada (de acordo com configuração).
	 * 
	 * @param itens
     * @param estrutura
     * @param colunas
     * @return listaItensOrdenados
     * @throws ECARException
	 */
	public List<ItemEstruturaIett> getItensOrdenados(List<ItemEstruturaIett> itens, EstruturaEtt estrutura, List colunas) throws ECARException {
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
		List listaItensOrdenados = new ArrayList();
		List<OrdenacaoIett> listaOrdem = new ArrayList<OrdenacaoIett>();
		
		if (itens != null && !itens.isEmpty()) {
	    	int tamanho = this.getTamanhoMaximoCampo(colunas, itens);
	    	
	    	for(Iterator<ItemEstruturaIett> it = itens.iterator(); it.hasNext();){
	    		ItemEstruturaIett iett = (ItemEstruturaIett) it.next();
	    		
	    		if(!iett.getEstruturaEtt().equals(estrutura)){
	    			continue;
	    		}
	    		
	    		OrdenacaoIett ordem = new OrdenacaoIett();
	    		ordem.setIett(iett);
	    		
    			String campo = "";
    			//percorre as colunas
	    		if(colunas != null && !colunas.isEmpty()){
	    			
	    			for(Iterator it2 = colunas.iterator(); it2.hasNext();){
	    				ObjetoEstrutura atb = (ObjetoEstrutura) it2.next();
	    				String valor = "";
	    				
	    				if("nivelPlanejamento".equals(atb.iGetNome())){
	    					String niveis = "";
	    			    	if(iett.getItemEstruturaNivelIettns() != null && !iett.getItemEstruturaNivelIettns().isEmpty()){
	    				    	Iterator itNiveis = iett.getItemEstruturaNivelIettns().iterator();
	    				    	while(itNiveis.hasNext()){
	    				    		SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
	    				    		niveis += nivel.getDescricaoSatb() + "; ";
	    				    	}
	    				    	niveis = niveis.substring(0, niveis.lastIndexOf(";"));
	    			    	}
	    					valor = niveis;
	    					
	    				} else if (atb.iGetGrupoAtributosLivres() != null)  {
	    					Iterator itIettSatbs =  iett.getItemEstruturaSisAtributoIettSatbs().iterator();
	    					String informacaoIettSatb = "";
	    					while (itIettSatbs.hasNext()) {
	    						ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributoIettSatb = (ItemEstruturaSisAtributoIettSatb) itIettSatbs.next();
	    						
	    						if (itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getSisGrupoAtributoSga().equals(atb.iGetGrupoAtributosLivres())){
	    							if (atb.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT)) ||
	    							 	atb.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA)) ||
	    							 	atb.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO)) ||
	    							 	atb.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO)) ) {
	    							 
	    								informacaoIettSatb = informacaoIettSatb + itemEstruturaSisAtributoIettSatb.getInformacao() +  "; ";
	    							
	    							} else if (!atb.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {
	    								//se for do tipo imagem não faz nada, deixa em branco.
	    								informacaoIettSatb = informacaoIettSatb + itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getDescricaoSatb() +  "; ";
	    							} 
	    						} 
	    					}
	    					if (informacaoIettSatb.length() > 0){
	    						informacaoIettSatb = informacaoIettSatb.substring(0, informacaoIettSatb.length() - 2); 
	    					}
	    					valor = informacaoIettSatb;
	    				} else {
	    					valor = itemEstruturaDao.getValorAtributoItemEstrutura(iett, atb.iGetNome(), atb.iGetNomeFk());
	    				}

	    				
	    				Date data = Data.parseDate(valor, "dd/MM/yyyy");
						if(data != null){ //É campo data!
							//Conseguiu converter para objeto Date!!!
							int d = Data.getDia(data);
							int m = Data.getMes(data) + 1;
							int a = Data.getAno(data);
							String dia = (d < 10) ? "0" + String.valueOf(d) : String.valueOf(d);
							String mes = (m < 10) ? "0" + String.valueOf(m) : String.valueOf(m);
							String ano = String.valueOf(a);

							valor = ano + mes + dia;
						}

						campo += this.completarParaOrdenacao(valor, tamanho);
					}
	    		}
	    		else {
	    			campo = this.completarParaOrdenacao(iett.getNomeIett(), tamanho);
	    		}

    			ordem.setCampoOrdenar(campo.toUpperCase());
    			
	    		listaOrdem.add(ordem);	    		
	    	}
	    	
	    }
	    //Ordenando pelo campo de ordenação
	    Collections.sort(listaOrdem, new Comparator(){

			public int compare(Object arg0, Object arg1) {
				OrdenacaoIett o1 = (OrdenacaoIett) arg0;
				OrdenacaoIett o2 = (OrdenacaoIett) arg1;
				
				return o1.getCampoOrdenar().compareTo(o2.getCampoOrdenar());				
			}
	    	
	    });
	    
	    for(OrdenacaoIett o : listaOrdem){
	    	listaItensOrdenados.add(o.getIett());
	    }
	    
	    return listaItensOrdenados;
		
	}
	
	/**
	 * Retorna o tamanho máximo da string dos valores de uma lista de campos numa lista de itens.
	 *  
	 * @param colunas
	 * @param itens
	 * @return 
	 * @throws ECARException
	 */
	public int getTamanhoMaximoCampo(List colunas, List itens) throws ECARException {
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
		int tam = 0;
		for(Iterator it = itens.iterator(); it.hasNext();){
			ItemEstruturaIett item = (ItemEstruturaIett) it.next();
			
			if(colunas != null && !colunas.isEmpty()){
				for(Iterator it2 = colunas.iterator(); it2.hasNext();){
					ObjetoEstrutura atb = (ObjetoEstrutura) it2.next();
					String valor = itemEstruturaDao.getValorAtributoItemEstrutura(item, atb.iGetNome(), atb.iGetNomeFk());
					
					if(valor != null){
						if(valor.length() > tam){
							tam = valor.length();
						}
					}
				}
			}
			else {
				String valor = item.getNomeIett();
				if(valor != null){
					if(valor.length() > tam){
						tam = valor.length();
					}
				}
			}
			
		}
		return tam;
	}
	
	/**
	 * Completa para ordenação
	 * @param valor
	 * @param tamanho
	 * @return String
	 */
	private String completarParaOrdenacao(String valor, int tamanho) {
    	String completar = "E";
    	try{
    		Long dummy = Long.valueOf(valor); //se conseguiu transformar, é número - completar zeros à esquerda
    	}
    	catch (NumberFormatException e) {
    		// ATENÇÃO: Não é necesário lancar exceção aqui
    		
    		// não conseguiu transformar, não é número - completar zeros à direita
    		completar = "D";
		}
    	return Util.completarCaracteres(valor, "0", tamanho, completar);
	}
	
	/**
	 * Retorna todos os itens de uma estrutura real que estão associados a uma estrutura virtual e que pertencem a uma determinada estrutura real. 
	 * Os itens retornados estão ativos e que possuem permissão de consulta
	 * 
	 * 
	 * @param estruturaReal     estrutura real cujos itens estão sendo pesquisados
	 * @param estruturaVirtual  estrutura virtual cujos itens estao associados
	 * @param seguranca         
	 * 
         * @return List
         * @throws ECARException
	 */
	public Set getItensComPermissaoPorEstruturaReal (EstruturaEtt estruturaReal, EstruturaEtt estruturaVirtual, SegurancaECAR seguranca) throws ECARException {
		
		Map<EstruturaEtt,List<ItemEstruturaIett>> map = new HashMap<EstruturaEtt, List<ItemEstruturaIett>>();
		Set<ItemEstruturaIett> itensEstruturaVirtual = estruturaVirtual.getItensEstruturaVirtual();
		ValidaPermissao validaPermissao = new ValidaPermissao();
		boolean permissaoAcessoItem = false;
		//List<ItemEstruturaIett> item = new ArrayList();
		Set<ItemEstruturaIett> item = new java.util.HashSet();
		
		try {
			if (itensEstruturaVirtual != null && !itensEstruturaVirtual.isEmpty()) {
			
				for (ItemEstruturaIett itemEstrutura : itensEstruturaVirtual) {
					
					//se a estrutura do item for a estrutura procurada
					if(itemEstrutura.getEstruturaEtt().equals(estruturaReal)) {
						
						//verifica a permissão de consultar o item e se ele está ativo ou não
						if ((itemEstrutura.getIndAtivoIett() != null || !"".equals(itemEstrutura.getIndAtivoIett().trim())) 
								&& !"N".equals(itemEstrutura.getIndAtivoIett().toUpperCase())) {
							validaPermissao.permissoesItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso());				
							permissaoAcessoItem = validaPermissao.permissaoConsultarItem();
					
						} else {
							permissaoAcessoItem = false;
						}	
						
						if(permissaoAcessoItem){
							item.add(itemEstrutura);
							
						}	
						
					}
				}
			}	
				
		} catch (Exception e) {
			return item;
		}
		
		return item;
		
	}
	
	
        /**
         *
         * @param itensOrdenadosPorPai
         * @return
         */
        public Map montarMapItensEstruturaVirtualOrdenadosPorPai(List<ItemEstruturaIett> itensOrdenadosPorPai){
		
		Map<EstruturaEtt,List<ItemEstruturaIett>> map = new HashMap<EstruturaEtt, List<ItemEstruturaIett>>();
						
		if (itensOrdenadosPorPai != null && !itensOrdenadosPorPai.isEmpty()) {
		
			for (ItemEstruturaIett itemEstrutura : itensOrdenadosPorPai) {
				
				List item = map.get(itemEstrutura.getEstruturaEtt());
				
				if (item == null){
					List<ItemEstruturaIett> listaInner = new ArrayList<ItemEstruturaIett>();
					listaInner.add(itemEstrutura);
					map.put(itemEstrutura.getEstruturaEtt(), listaInner);
				} else {
					item.add(itemEstrutura);
				}
				
				
			}
		}
		
		return map;
		
	}

	/**
	 * Cria um Map com a estrutura virtual como chave e a lista dos itens vinculados como valor, para ser exibido na 
	 * tela de geração de período.
	 * @param itensEstrutura
	 * @return
	 */
	public Map montarMapItensPorEstruturaVirtual(List<AtributoEstruturaListagemItens> itensEstrutura) {
		/*
		Map<EstruturaEtt, List<ItemEstruturaIett>> mapItensEstruturaVirtual = new HashMap<EstruturaEtt, List<ItemEstruturaIett>>(); 
		

		for (AtributoEstruturaListagemItens atributo : itensEstrutura) {
			
			//Verifica se o item possui a lista de Estruturas virtuais, isso indica que o item está associado a alguma Estrutura virtual 
			if (!atributo.getItem().getEstruturasVirtual().isEmpty()) {
				
				// Obtem a lista de estruturas em que o item está associado.
				for (EstruturaEtt estruturaVirtual : atributo.getItem().getEstruturasVirtual()) {

					List<ItemEstruturaIett> listaItens = (List<ItemEstruturaIett>) mapItensEstruturaVirtual.get(estruturaVirtual);
					
					if (listaItens == null){//Quando a estrutura ainda não faz parte das chaves do map. Adiciona-se a estrutura virtual como chave e o valor é criado como uma lista contendo o primeiro item da lista.
						List<ItemEstruturaIett> inner = new ArrayList<ItemEstruturaIett>();
						inner.add(atributo.getItem());
						mapItensEstruturaVirtual.put(estruturaVirtual, inner);
					} else {//Quando a estrutura já faz parte das chaves do map. Obtemos a lista de itens e adicionamos mais um item na lista.
						listaItens.add(atributo.getItem());
					}

				}
				
			}
			
		}
		
		return mapItensEstruturaVirtual;
		*/
		return new HashMap<EstruturaEtt, List<ItemEstruturaIett>>();
	}
	
	
}
