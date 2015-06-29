/*
 * Created on 05/10/2004
 * 
 * Classe para controlar os itens de locais
 * 
 * Itens de locais sao instancias de um determinado grupo de local
 * Por exemplo, para o grupo de local Estado, temos os itens Parana, Sao Paulo
 * Para o grupo Municipio, temos Curitiba, Sao Jose dos Pinhais, etc
 * 
 * Um grupo está associado a varios locais
 * Um local pertence a um grupo
 * A hierarquia de locais depende da hierarquia de grupos. então uma vez montada
 * hierarquia de locais não podemos mais alterar a hierarquia de grupos, por razoes
 * de integridade historica 
 * 
 * Seja a tabela de hierarquia de grupos (de locais)
 * GRUPO ->	GRUPO_PAI
 * ------------------
 * M		E
 * M		R
 * E		R
 * R		P
 * 
 * Seja a tabela de locais (itens ou instancias de um grupo)
 * LOCAL		-> 	GRUPO
 * ----------------------
 * RS, SC, PR		E
 * CTBA, FOZ		M
 * SUL, NORTE		R
 * BRASIL, CHILE	P
 * 
 * 
 * Dado um local, quem são seus locais associados na hierarquia de locais?
 * Ex. Local: PR
 * 
 * Locais Associados:
 * 
 * grupo <- local.getGrupo()     //  grupo <- E  (PR.getGrupo())
 * 
 * listaFilhosGrupo <- grupo.getFilhos() // listafilhosGrupo <- {M} (E.getFilhos())
 * 
 * para cada filho na listaFilhosGrupo 
 * 		listaLocaisAssociados <- listaLocaisAssociados U filho.getItemLocais()    
 * 		
 * Entao a lista de itens de local associada será:
 * {CTBA, FOZ}
 * 
 * 
 */
package ecar.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.FileUpload;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.LocAtributoLoca;
import ecar.pojo.LocalGrupoLgp;
import ecar.pojo.LocalItemLit;
import ecar.pojo.PaiFilho;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.UsuarioUsu;
import ecar.taglib.util.Input;

/**
 * @author evandro
 *
 */
public class LocalItemDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public LocalItemDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
	 */
	public LocalItemDao() {
		super();
	}
	
	/**
	 * Retorna um list com identificações de todos os locais filhos de um local
	 * @param localItem
	 * @return List de Long
	 */
	public List getFilhosById(LocalItemLit localItem){
	    List filhos = new ArrayList();
	    if(localItem.getLocalItemHierarquiaLithsByCodLitPai() != null){
		    Iterator it = localItem.getLocalItemHierarquiaLithsByCodLitPai().iterator();
		    while(it.hasNext()){
		        LocalItemLit localItemFilho = (LocalItemLit) it.next();
		        filhos.add(localItemFilho.getCodLit());
		    }	    	
	    }
	    return filhos;
	}
	
	/**
	 * Retorna os locais acima do local passado como parâmetro (pais, avôs, etc)
	 * @param localItem
	 * @return Set Coleção de Locais
	 */
	public List getAscendentes(LocalItemLit localItem){
	    List grupos = new ArrayList();
	    if(localItem.getLocalItemHierarquiaLithsByCodLit() != null){
	        // Coleção dos Pais
	        Iterator it = localItem.getLocalItemHierarquiaLithsByCodLit().iterator();
	        while(it.hasNext()){
	            LocalItemLit localItemLit = (LocalItemLit) it.next();
	            if(!grupos.contains(localItemLit)){
	                grupos.add(localItemLit);
	                grupos.addAll(getAscendentes(localItemLit));
	            }    
	        }
	    }
	    return grupos;
	}
	
	/**
	 * verifica depois exclui
	 * @param localItem
	 * @throws ECARException
	 */
	public void excluir(LocalItemLit localItem) throws ECARException {	    
	   try{
	       	boolean excluir = true;
		    if(contar(localItem.getLocalItemHierarquiaLithsByCodLit()) > 0){ 
		        excluir = false;
			    throw new ECARException("localItem.exclusao.erro.localItemHierarquiaLithsByCodLit");
		    }
		    if(contar(localItem.getLocalItemHierarquiaLithsByCodLitPai()) > 0){ 
		        excluir = false;
			    throw new ECARException("localItem.exclusao.erro.localItemHierarquiaLithsByCodLitPai");
		    }
		    if(contar(localItem.getItemEstrutLocalIettls()) > 0){ 
		        excluir = false;
			    throw new ECARException("localItem.exclusao.erro.itemEstrutLocalIettls");
		    }
		    
		    if (excluir) {
		    	List objs = new ArrayList();                
                if (localItem.getLocAtributoLocas() != null) {
                    Iterator itAtb = localItem.getLocAtributoLocas().iterator();
                    while (itAtb.hasNext()) {
                    	LocAtributoLoca localAtrib = (LocAtributoLoca) itAtb.next();
                        objs.add(localAtrib);
                    }
                }
                localItem.setLocAtributoLocas(null);
                objs.add(localItem);                   
                super.excluir(objs);
		    }
	   }catch(ECARException e){
		   this.logger.error(e);
	       throw e;
	   }    
	}
	

	/**
	 * Recebe um localItemPai e retorna a lista de todos os localItem que podem ser seus filhos, respeitando hierarquia
	 * já estabelecida
	 * @param localItemPai
         * @return
         * @throws ECARException
	 */
	public List getLocalItemByLocalItem(LocalItemLit localItemPai) throws ECARException{
		List listaFilhos = new ArrayList();


		try{
		    /* objetos que serão usados dentro do loop */
			Collection itens;
			LocalGrupoLgp localGrupoFilho;
		    
			/* descobre o grupo do item */
			LocalGrupoLgp localGrupoPai = localItemPai.getLocalGrupoLgp();
			
			/* descobre os filhos do grupo (ex, municipio é filho de estado) */
			//Collection filhos = this.getSession().filter(localGrupoPai.getLocalGrupoHierarquiaLgphsByCodLgpPai(), "order by this.codLgp");
			Collection filhos = this.getDescendentes(localGrupoPai);
			
			// Comentado por Cristiano - Mantis 10417
			//filhos.add(localGrupoPai);
			
			String where = "where indAtivoLit='S' ";
			
			if(!"".equals(localItemPai.getIdentificacaoLit().trim())) {
				where += "and upper(identificacaoLit) like '%" + localItemPai.getIdentificacaoLit().trim().toUpperCase() + "%' ";
			}
			
			if(!"".equals(localItemPai.getCodIbgeLit().trim())) {
				where += "and upper(codIbgeLit) = '" + localItemPai.getCodIbgeLit().trim().toUpperCase() + "' ";
			}
			
			if(!"".equals(localItemPai.getCodPlanejamentoLit().trim())) {
				where += "and upper(codPlanejamentoLit) = '" + localItemPai.getCodPlanejamentoLit().trim().toUpperCase() + "' ";
			}
			
			Iterator it = filhos.iterator();

			/* obter os locais do grupo */
			while (it.hasNext()){
				localGrupoFilho = (LocalGrupoLgp) it.next();
				
		    	Query query = this.getSession().createQuery(
		    			"from LocalItemLit " + where + " and localGrupoLgp.codLgp = :codLgp");
		    	query.setLong("codLgp", localGrupoFilho.getCodLgp().longValue());

		    	itens = query.list();
				
				if(itens != null) {
					Iterator itAux = itens.iterator();

					while (itAux.hasNext()){
						LocalItemLit localItemLit = (LocalItemLit) itAux.next();
						
						if(!listaFilhos.contains(localItemLit)) {
							listaFilhos.add(localItemLit);
						}
					}
				}
			}

			Collections.sort(listaFilhos,
	            new Comparator() {
	        		public int compare(Object o1, Object o2) {		        		    	        		 
	        			LocalItemLit l1 = (LocalItemLit) o1;
	        			LocalItemLit l2 = (LocalItemLit) o2;
	        			
	        			String ord1 = l1.getLocalGrupoLgp().getCodLgp().toString() + l1.getIdentificacaoLit();
	        			String ord2 = l2.getLocalGrupoLgp().getCodLgp().toString() + l2.getIdentificacaoLit();

	        			return ord1.compareToIgnoreCase(ord2);	
					}
	   			}
			);
	        
		    
		} catch (HibernateException e){
			this.logger.error(e);
		    throw new ECARException(e);
		}
		
		
		return listaFilhos;
	    
	}
    
    /**
     * Retorna uma lista com a lista completa de filhos de um LocalGrupoLgp
     * 
     * @param localGrupoLgp
     * @return List de LocalGrupoLgp
     * @throws ECARException
     */   
    public List getDescendentes(LocalGrupoLgp localGrupoLgp) throws ECARException {
        List retorno = new ArrayList();
 
        if (localGrupoLgp.getLocalGrupoHierarquiaLgphsByCodLgpPai() != null) {
            
            Iterator it = localGrupoLgp.getLocalGrupoHierarquiaLgphsByCodLgpPai().iterator();
            while (it.hasNext()) {
            	LocalGrupoLgp localGrupoLgpFilho = (LocalGrupoLgp) it.next();
                
                if (!retorno.contains(localGrupoLgpFilho))
                    retorno.add(localGrupoLgpFilho);
                retorno.addAll(this.getDescendentes(localGrupoLgpFilho));
            }
        }
        return retorno;
    }
    
	/**
	 * verifica duplicação depois salva
	 * @param local
	 * @throws ECARException
	 */
	public void salvar(LocalItemLit local) throws ECARException {
		if (pesquisarDuplos(local, new String[] {"identificacaoLit"}, "codLit").size() > 0)
		    throw new ECARException("localItem.validacao.registroDuplicado");
		
		List filhos = new ArrayList();
		if (local.getLocAtributoLocas() != null)
			filhos.addAll(local.getLocAtributoLocas());
		
        super.salvar(local, filhos);
	}
	

	/**
	 * 
         * @param pLocal
         * @return List
         * @throws ECARException
	 */
	public ArrayList<LocalItemLit> pesquisarPorCodIBGE(LocalItemLit pLocal) throws ECARException {   	   	
		Query query = this.getSession().
			createQuery("select i from LocalItemLit i where i.codIbgeLit = '" + pLocal.getCodIbgeLit() + "'");
		return (ArrayList)query.list();
	}
	
	/**
         * @param mapLocais
         * @throws ECARException
	 */
	public void salvar(Map<Long, List<LocalItemLit>> mapLocais) throws ECARException {
		inicializarLogBean();
		Long chave = null;
		Transaction tx = null;
		try {
			LocalItemLit local = null;
			LocalItemLit localTemp = null;
			LocalItemLit localPai = null;
			
		    Set<Long> chaves = mapLocais.keySet();
			Iterator<Long> chavesIt = chaves.iterator();
			List<LocalItemLit> locais = new ArrayList<LocalItemLit>();
			List<LocalItemLit> locaisTemp = new ArrayList<LocalItemLit>();
			Iterator<LocalItemLit> locaisIt = null;
						
			List<LocalItemLit> pais = new ArrayList<LocalItemLit>();
			
			tx = session.beginTransaction();

			while(chavesIt.hasNext()) {
				// Pega os locais por UF
				chave = chavesIt.next();

				// Salva local pai
				localPai = new LocalItemLit();
				localPai.setCodIbgeLit(chave.toString());
				pais = pesquisarPorCodIBGE(localPai);
								
				if(pais != null && !pais.isEmpty()) {
					localPai = pais.get(0);
					// Pega locais filhos
					locais = mapLocais.get(chave);
					
					locaisIt = locais.iterator();
					while(locaisIt.hasNext()) {
						local = locaisIt.next();
						locaisTemp = pesquisarPorCodIBGE(local);
						
						if(locaisTemp != null && !locaisTemp.isEmpty()) {
												
							if(locaisTemp.size() > 1) {
								int indice = locaisTemp.size();
								for(int i = 0; i < indice; i++) {
									localTemp = locaisTemp.get(i);
									if(!(localTemp.getIdentificacaoLit()).equalsIgnoreCase(local.getIdentificacaoLit())) {
										localTemp = null;
									} else {
										//System.out.println("---------"+localTemp.getCodLit()+"------------> " + localTemp.getIdentificacaoLit());
										break;
									}
								}								
							} else {
								localTemp = locaisTemp.get(0);
							}
							
							this.setAtributos(request, localTemp);
							//System.out.println("-------------1------> "+ localTemp.getCodLit());
							
							// Salva local e hierarquia local
							if(localTemp.getLocalItemHierarquiaLithsByCodLit() != null) {
								if(!localTemp.getLocalItemHierarquiaLithsByCodLit().contains(localPai))
									localTemp.getLocalItemHierarquiaLithsByCodLit().add(localPai);
							} else {
								localTemp.setLocalItemHierarquiaLithsByCodLit(new HashSet());
								localTemp.getLocalItemHierarquiaLithsByCodLit().add(localPai);
							}
							session.update(localTemp);
						} else {
							this.setAtributos(request, local);
							
							// Salva local
							session.saveOrUpdate(local);
							//System.out.println("---------------2----> "+ local.getCodLit());
							
							// Salva hierarquia local
							if(local.getLocalItemHierarquiaLithsByCodLit() != null) {
								local.getLocalItemHierarquiaLithsByCodLit().add(localPai);
							} else {
								local.setLocalItemHierarquiaLithsByCodLit(new HashSet());
								local.getLocalItemHierarquiaLithsByCodLit().add(localPai);
							}
							session.saveOrUpdate(local);
						}
					}
				} else {
					// Pega locais filhos
					locais = mapLocais.get(chave);
					
					locaisIt = locais.iterator();
					while(locaisIt.hasNext()) {
						local = locaisIt.next();
						locaisTemp = pesquisarPorCodIBGE(local);
						
						if(locaisTemp != null && !locaisTemp.isEmpty()) {
												
							if(locaisTemp.size() > 1) {
								int indice = locaisTemp.size();
								for(int i = 0; i < indice; i++) {
									localTemp = locaisTemp.get(i);
									if(!(localTemp.getIdentificacaoLit()).equalsIgnoreCase(local.getIdentificacaoLit())) {
										localTemp = null;
									} else {
										break;
									}
								}								
							} else {
								localTemp = locaisTemp.get(0);
							}
							
							this.setAtributos(request, localTemp);
							// Salva local
							session.update(localTemp);
						} else {
							this.setAtributos(request, local);
							
							// Salva local
							session.saveOrUpdate(local);
						}
					}
				}
			}
			
			tx.commit();
			
			if(logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("INC");
			}

		} catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
	}
	
        /**
         *
         * @param local
         * @param request
         * @throws Exception 
         */
        public void alterar(LocalItemLit local, HttpServletRequest request)
				throws Exception {
		if (pesquisarDuplos(local, new String[] {"identificacaoLit"}, "codLit").size() > 0)
		    throw new ECARException("localItem.validacao.registroDuplicado");
		
		Transaction tx = null;
		
		try {
			List objetos = new ArrayList();
			super.inicializarLogBean();
			tx = session.beginTransaction();
			
			if (local.getLocAtributoLocas() != null) {
				Iterator itAtt = local.getLocAtributoLocas().iterator();
				while (itAtt.hasNext()) {
					LocAtributoLoca localAtributo = (LocAtributoLoca) itAtt.next();
					
                    if(localAtributo.getSisAtributoSatb().getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg() == Input.IMAGEM){
                    	
                    	String nomeCampo = request.getParameter("a" + localAtributo.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga().toString());
                    	
                    	if (nomeCampo != null && nomeCampo.equals("")){
                    		
	    		    		String fullFile = localAtributo.getInformacao();
	    		    		
	    		    		if (fullFile.lastIndexOf("=") != -1)     
	    		    			fullFile = fullFile.substring(fullFile.lastIndexOf("=") + 1);
    		    		
    		    		File f = new File(fullFile);
    		    		if( f.exists() ) 
    		    			FileUpload.apagarArquivo(fullFile);
                    	}
                    }
					
					session.delete(localAtributo);
					objetos.add(localAtributo);
				}
			}
			local.setLocAtributoLocas(null);
			
			this.setAtributos(request, local);
			
			List filhos = new ArrayList();
			if (local.getLocAtributoLocas() != null) {
				filhos.addAll(local.getLocAtributoLocas());
			}
			
			session.update(local);
			objetos.add(local);
			
			Iterator it = filhos.iterator();
            while(it.hasNext()){                
				PaiFilho object = (PaiFilho) it.next();
				object.atribuirPKPai();
				//salva os filhos
				session.save(object);                  
				objetos.add(object);
            }
			
			tx.commit();
			
			if (super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC_ALT_EXC");
				Iterator itObj = objetos.iterator();

				while (itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
			
		} catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		} catch (ECARException e) {
			this.logger.error(e);
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			throw e;
		}
	}
	
	/**
	 * Recebe um LocalGrupoLgp e retorna a lista de todos os localItem que são
	 * 		do grupo.
         * @param localGrupo
	 * @return List
         * @throws ECARException
         * @throws HibernateException
	 */
	public List getLocalItemPorLocalGrupo(LocalGrupoLgp localGrupo)
				throws ECARException, HibernateException{
    	Query query = this.getSession().createQuery(
    			"select lit from LocalItemLit lit" +
    			" where lit.localGrupoLgp.codLgp = :codLgp" +
    			" and lit.indAtivoLit = 'S'" +
    			" order by lit.identificacaoLit");
    	query.setLong("codLgp", localGrupo.getCodLgp().longValue());
		
		return query.list();
	}

	
        /**
         *
         * @param localGrupo
         * @return
         * @throws ECARException
         * @throws HibernateException
         */
	public List getLocalItemPorLocalGrupo(Long localGrupo)throws ECARException, HibernateException{

		LocalGrupoLgp grupo = new LocalGrupoLgp();
		grupo.setCodLgp(localGrupo);
		return getLocalItemPorLocalGrupo( grupo );
	}
	
	/**
	 * Devolve uma lista de LocalItemLit ativos que são pais do local passado como
	 * parâmetro
	 * @param localItem
	 * @return List
	 */
	public List getLocaisPais(LocalItemLit localItem) {
		List locaisPais = new ArrayList();
		
		if(localItem.getLocalItemHierarquiaLithsByCodLit() != null){
			locaisPais.addAll(localItem.getLocalItemHierarquiaLithsByCodLit());
		}
		
		return locaisPais;
	}
	
	/**
	 * Devolve uma lista de LocalItemLit ativos que são filhos do local passado como
	 * parâmetro
	 * @param localItem
	 * @return List
	 */
	public List getLocaisFilhos(LocalItemLit localItem) {
		List locaisFilhos = new ArrayList();
		
		if(localItem.getLocalItemHierarquiaLithsByCodLitPai() != null){
			locaisFilhos.addAll(localItem.getLocalItemHierarquiaLithsByCodLitPai());
		}
		
		return locaisFilhos;
	}
	
        /**
         *
         * @param localItem
         * @return
         * @throws ECARException
         */
        public List getLocaisFilhos(Long localItem) throws ECARException{
		LocalItemLit item = (LocalItemLit)this.buscar(LocalItemLit.class, localItem);		
		return getLocaisFilhos(item);
	}
	
	/**
	 * 
	 * @param local
	 * @param grupoAtributo
	 * @return List atributos
	 */
	public List getAtributosLocalByGrupo(LocalItemLit local, SisGrupoAtributoSga grupoAtributo) {
		List retorno = new ArrayList();
		
		Set result = local.getLocAtributoLocas();
        if (result != null) {
            if (result.size() > 0) {
                Iterator it = result.iterator();
                while (it.hasNext()) {
                	LocAtributoLoca localAtributo = (LocAtributoLoca) it.next();
                	if (localAtributo.getSisAtributoSatb().getSisGrupoAtributoSga().equals(grupoAtributo))
                		retorno.add(localAtributo);
                }
            }
        }
		
		return retorno;
	}
	
	/**
	 * Obtém os atributos dinâmicos da página e os transforma para uma lista de
	 * LocAtributoLoca utilizado para salvar e alterar LocalItemLit.
	 * @param request
         * @param localItem
         * @throws ECARException
	 */
	public void setAtributos(HttpServletRequest request,
            LocalItemLit localItem) throws ECARException {
		localItem.setLocAtributoLocas(null);
		List lAtributos;
		lAtributos =  new SisGrupoAtributoDao(request).getGruposAtributosCadastro("L");
        Iterator it = lAtributos.iterator();
        while (it.hasNext()) {
            SisGrupoAtributoSga grupoAtributo = (SisGrupoAtributoSga) it.next();           
            if (!"".equals(Pagina.getParamStr(request, "a" + grupoAtributo.getCodSga().toString()))) {
                LocAtributoLoca localAtributo = new LocAtributoLoca();
                localAtributo.setLocalItemLit(localItem);
                /*
                 * Caso o tipo de campo seja texto considera-se que o Grupo de
                 * Atributos possuirá apenas 1 atributo que o representa.
                 */
                if (SisTipoExibicGrupoDao.TEXT.equals(grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString())
                   	|| SisTipoExibicGrupoDao.VALIDACAO.equals(grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString())
                   	|| SisTipoExibicGrupoDao.TEXTAREA.equals(grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString())
                	|| SisTipoExibicGrupoDao.IMAGEM.equals(grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString())) {
                    if (grupoAtributo.getSisAtributoSatbs() != null && grupoAtributo.getSisAtributoSatbs().size() > 0) {
                        localAtributo.setInformacao(Pagina.getParamStr(request,"a" + grupoAtributo.getCodSga().toString()));
                        localAtributo.setSisAtributoSatb((SisAtributoSatb) grupoAtributo.getSisAtributoSatbs().iterator().next());
                        localAtributo.setDataInclusao(Data.getDataAtual());
                        if (localItem.getLocAtributoLocas() == null)
                            localItem.setLocAtributoLocas(new HashSet());
                        localItem.getLocAtributoLocas().add(localAtributo);
                        
                        String pathRaiz = request.getContextPath();
                        
                        // tratamento imagem
    					String caminhoAuxiliarImagem = Pagina.getParamStr(request, "hidImagem" + "a"
    							+ grupoAtributo.getCodSga().toString());
    					if (caminhoAuxiliarImagem!=null && caminhoAuxiliarImagem.length()>0) {
    						
    						String chave = localAtributo.getInformacao();
    						chave = chave.substring(chave.indexOf("RemoteFile=")+ "RemoteFile=".length());
    						UsuarioUsu usuario = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
                            if(usuario.getMapArquivosAtuaisUsuarios() != null && usuario.getMapArquivosAtuaisUsuarios().containsKey(chave)){
//                            	localAtributo.setInformacao(usuario.getMapArquivosAtuaisUsuarios().get(chave));
                            	
                            	caminhoAuxiliarImagem = usuario.getMapArquivosAtuaisUsuarios().get(chave);
                            	caminhoAuxiliarImagem = pathRaiz +"/DownloadFile?RemoteFile=" + caminhoAuxiliarImagem;
                            }
//                            else{
    						
	    						// salvar a imagem fisicamente que tem o caminho real no campo "a" + codigo de grupo de atributo
	    						try {									
	    							String nomeArquivoNovo = FileUpload.salvarArquivoSessaoFisicamente(request, "a" + grupoAtributo.getCodSga().toString(), caminhoAuxiliarImagem);
	    							if(nomeArquivoNovo != null && !nomeArquivoNovo.equals(""))
	    								localAtributo.setInformacao(nomeArquivoNovo);
	    						} catch (FileNotFoundException e) {
	    							throw new ECARException("erro.arquivoUrl",e, new String[]{caminhoAuxiliarImagem});
	    						} catch (Exception e) {
	    							throw new ECARException("erro.upload",e, new String[]{caminhoAuxiliarImagem});
	    						}
//                            }
    					}
                    }
                } else {

                    String[] atributos = request.getParameterValues("a"
                            + grupoAtributo.getCodSga().toString());
                    for (int i = 0; i < atributos.length; i++) {
                        /*
                         * Tenho que criar novamente o usuário atributo senão
                         * ele não é adicionado no set no final deste laço
                         */
                        localAtributo = new LocAtributoLoca();
                        localAtributo.setLocalItemLit(localItem);
                        localAtributo.setSisAtributoSatb((SisAtributoSatb) super.buscar(SisAtributoSatb.class, Long.valueOf(atributos[i])));
                        localAtributo.setDataInclusao(Data.getDataAtual());
                        if (localItem.getLocAtributoLocas() == null)
                            localItem.setLocAtributoLocas(new HashSet());
                        localItem.getLocAtributoLocas().add(localAtributo);
                    }
                }
            }
            /* Foi necessário alterar o nome dos campos dos elementos multitexto, adicionando "-codSatb"
             * Assim, ficamos com o nome do campo no seguinte padrão: "a + codSteg + - + codSatb" (ex.: a12-38)
             * Isto foi feito visto a diferença existente entre um grupo com suporte a 1 campo texto
             * e este, que suporta vários campos texto.
             */
            else{ 
                if (SisTipoExibicGrupoDao.MULTITEXTO.equals(grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString()))
                {
                	Enumeration lAtrib = request.getParameterNames();
                	while(lAtrib.hasMoreElements())
                	{
                		String atrib = (String)lAtrib.nextElement();
                		if (atrib.lastIndexOf('-') > 0)
                		{
                			String nomeAtrib = atrib.substring(0,atrib.lastIndexOf('-'));
                			String nomeCampo = atrib.substring(atrib.lastIndexOf('-')+1);
                			if (nomeAtrib.equals("a"+grupoAtributo.getCodSga().toString())
                		         && !"".equals(Pagina.getParamStr(request, atrib)))
                			{
                                LocAtributoLoca localAtributo = new LocAtributoLoca();
                                localAtributo.setLocalItemLit(localItem);
                                localAtributo.setInformacao(Pagina.getParamStr(request, atrib));
                                localAtributo.setSisAtributoSatb((SisAtributoSatb) super.buscar(SisAtributoSatb.class, Long.valueOf(nomeCampo)));
                                localAtributo.setDataInclusao(Data.getDataAtual());
                                if (localItem.getLocAtributoLocas() == null)
                                    localItem.setLocAtributoLocas(new HashSet());
                                localItem.getLocAtributoLocas().add(localAtributo);
                			}
                		}
                	}
                }            
            }
        }
    }
	
	/**
	 * 
	 * @param local
	 * @return List
	 * @throws ECARException
	 */
	public List pesquisar(LocalItemLit local) throws ECARException{   	   	
    	// classes marcadas na tela como filtro
    	List listFiltro = new ArrayList();
    	if(local.getLocAtributoLocas() != null && local.getLocAtributoLocas().size() > 0) {
    		Iterator itAtr = local.getLocAtributoLocas().iterator();
    		LocAtributoLoca localAtributo;
    		while(itAtr.hasNext()) {
    			localAtributo = (LocAtributoLoca) itAtr.next();
    			listFiltro.add(localAtributo);
    		}
    	}

       List pesquisa = super.pesquisar(local, new String[] {"identificacaoLit","asc"});
       
       Iterator it = pesquisa.iterator();
       while (it.hasNext()) {
    	   LocalItemLit localAux = (LocalItemLit) it.next();

    	   //verificações se foi marcado classes de acesso como filtro da pesquisa
   		   boolean ignorar = false;
   		   List listVerificacoes = new ArrayList();
    	   
   		   if (listFiltro.size() > 0) {
    		   if (localAux.getLocAtributoLocas() != null && localAux.getLocAtributoLocas().size() > 0) {
	       		
    			   Iterator itAtr = localAux.getLocAtributoLocas().iterator();
    			   LocAtributoLoca locAtrAux;
    			   while (itAtr.hasNext()) {
    				   locAtrAux = (LocAtributoLoca) itAtr.next();
    				   listVerificacoes.add(locAtrAux);
    			   }
    			   
       			   Iterator itClassesTela = listFiltro.iterator();
       	   		   List listIgnorar = new ArrayList();
       	   		   String ignorarItem = "";
       	   		   LocAtributoLoca locaisTela;
       	   		   
       	   		   while (itClassesTela.hasNext()) {
       	   			   locaisTela = (LocAtributoLoca) itClassesTela.next();
	       			   Iterator itVerificacoes = listVerificacoes.iterator();       			   
       				   ignorarItem = "SIM";
       				   LocAtributoLoca atributos;
	       			   while (itVerificacoes.hasNext()) {
	       				   atributos = (LocAtributoLoca) itVerificacoes.next(); 
	       				   SisGrupoAtributoSga tipo = (SisGrupoAtributoSga) locaisTela.getSisAtributoSatb().getSisGrupoAtributoSga();
		       			   if (SisTipoExibicGrupoDao.TEXT.equals(tipo.getSisTipoExibicGrupoSteg().getCodSteg().toString())) {
		       				   if (atributos.getInformacao() != null && atributos.getInformacao().length() > 0) {
		       					   if (atributos.getInformacao().indexOf(atributos.getInformacao()) > -1) {
		       						   ignorarItem = "NAO";
		       						   break;
		       					   }
		       				   }
		       			   }
		       			   else {
		       				   if (atributos.getSisAtributoSatb().getCodSatb().longValue() == locaisTela.getSisAtributoSatb().getCodSatb().longValue()) {
		       					   ignorarItem = "NAO";
		       					   break;
		       				   }
		       			   }
		       		   }
	       			   
	       			   listIgnorar.add(ignorarItem);
       	   		   }
	       		   
       	   		   if (listIgnorar.contains("SIM")) {
       	   			   ignorar = true;
       	   		   } else {
       	   			   ignorar = false;
       	   		   }
       		   } else {
       			   ignorar = true;
       		   }
    	   } if (ignorar) {
       		  it.remove(); 
       	   }
       }
       
       return pesquisa;
    }
	
	/**
	 * 
	 * @param codLocais
	 * @return List
	 * @throws Exception
	 */
	public List pesquisarCodLocais(String codLocais) throws Exception {   	   	
		Query query = this.getSession().createQuery("select i from LocalItemLit i where i.codLit IN (" + codLocais + ")");
		return query.list();
	}
}