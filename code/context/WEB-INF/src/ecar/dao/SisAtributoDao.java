/*
 * Created on 02/12/2004
 * 
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;
import comum.util.ConstantesECAR;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstrutUsuarioIettus;
import ecar.pojo.PontoCriticoSisAtributoPtcSatb;
import ecar.pojo.SegmentoSgt;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.SisTipoExibicGrupoSteg;

/**
 * @author evandro
 *
 */
public class SisAtributoDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public SisAtributoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
        /**
         *
         */
        public SisAtributoDao()
	{
		super();
	}
		
	/**
	 * 
     * Verifica depois exclui
	 * @param sisAtributo
	 * @throws ECARException
	 */
	public void excluir(SisAtributoSatb sisAtributo) throws ECARException {	    
	   try{
	       	boolean excluir = true;
/*		    if(contar(sisAtributo.getUsuarioAtributoUsuas()) > 0){ 
		        excluir = false;
			    throw new ECARException("sisAtributo.exclusao.erro.usuarioAtributoUsuas");
		    }
		    if(contar(sisAtributo.getEntidadeAtributoEntas()) > 0){ 
		        excluir = false;
			    throw new ECARException("sisAtributo.exclusao.erro.entidadeAtributoEntas");
		    }
		    if(contar(sisAtributo.getDemAtributoDemas()) > 0){ 
		        excluir = false;
			    throw new ECARException("sisAtributo.exclusao.erro.demAtributoDemas");
		    }
		    if(contar(sisAtributo.getLocAtributoLocas()) > 0){ 
		        excluir = false;
			    throw new ECARException("sisAtributo.exclusao.erro.locAtributoLocas");
		    }
*/      	 	
	       	EstruturaDao estruturaDao = new EstruturaDao(this.request);
	       	if(estruturaDao.verificaSisAtributoRestringirVisualizacao(sisAtributo)){ 
		        excluir = false;
			    throw new ECARException("sisAtributo.exclusao.erro.sisAtbRestringeVisualizacao");
		    }
	       	
	       	if(contar(sisAtributo.getSegmentoTpAcessoSgttas()) > 0){ 
		        excluir = false;
			    throw new ECARException("sisAtributo.exclusao.erro.segmentoTpAcessoSgttas");
		    }
		    if(contar(sisAtributo.getItemEstruturaNivelIettns()) > 0){ 
		        excluir = false;
			    throw new ECARException("sisAtributo.exclusao.erro.itemEstruturaNivelIettns");
		    }
		    if(contar(sisAtributo.getSegmentoItemTpacesSgtitas()) > 0){ 
		        excluir = false;
			    throw new ECARException("sisAtributo.exclusao.erro.segmentoItemTpacesSgtitas");
		    }
		    if(contar(sisAtributo.getSegmentoCategTpAcessSgts()) > 0){ 
		        excluir = false;
			    throw new ECARException("sisAtributo.exclusao.erro.segmentoCategTpAcessSgts");
		    }
		    /*if(contar(sisAtributo.getEstruturaAcessoEttas()) > 0){ 
		        excluir = false;
			    throw new ECARException("sisAtributo.exclusao.erro.estruturaAcessoEttas");
		    }*/
		    if(contar(sisAtributo.getConfiguracaoCfgsByCodSacapa()) > 0){ 
		        excluir = false;
			    throw new ECARException("sisAtributo.exclusao.erro.configuracaoCfgsByCodSacapa");
		    }
		    if(contar(sisAtributo.getConfiguracaoCfgsByCodSapadrao()) > 0){ 
		        excluir = false;
			    throw new ECARException("sisAtributo.exclusao.erro.configuracaoCfgsByCodSapadrao");
		    }
		    
		    //Verifica se tem algum item contendo o atributo
		    if(!sisAtributo.getItemEstruturaSisAtributoIettSatbs().isEmpty()){ 
		        excluir = false;
			    throw new ECARException("sisAtributo.exclusao.erro.configuracaoItemEstruturaSisAtributo");
		    }
		    
		    //Verifica se tem algum ponto critico que utiliza o atributo
		    PontoCriticoDao ptcDao = new PontoCriticoDao(request);
		    
		    if (!ptcDao.consultarPontosCriticosSisAtributo(sisAtributo).isEmpty()) {
		        excluir = false;
			    throw new ECARException("sisAtributo.exclusao.erro.configuracaoPtcSisAtributo");
		    }
		    
		    
		    if(excluir) {
		    	if (sisAtributo.getItemEstrutUsuarioIettuses() != null) {
		    		Iterator itExc = sisAtributo.getItemEstrutUsuarioIettuses().iterator();
		    		while(itExc.hasNext()){
		    			ItemEstrutUsuarioIettus iettus = (ItemEstrutUsuarioIettus) itExc.next();
		    			super.excluir(iettus);
		    		}
		    	}
		        super.excluir(sisAtributo);
            }
	   }catch(ECARException e){
		   this.logger.error(e);
	       throw e;
	   }    
	}
	
	/**
	 * 
     * Salva
	 * @param sisAtributo
	 * @throws ECARException
	 */
	public void salvar(SisAtributoSatb sisAtributo) throws ECARException {
		
		//Objeto utilizado apenas para testar se é igual.
		SisTipoExibicGrupoSteg tipoGrupo = new SisTipoExibicGrupoSteg();
		
		tipoGrupo.setCodSteg(Long.parseLong(SisTipoExibicGrupoDao.VALIDACAO));
		
		
    	if (sisAtributo.getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg() != null && sisAtributo.getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().equals(tipoGrupo)) {  
	    	if (sisAtributo.getSisGrupoAtributoSga().getSisAtributoSatbs().size() > Integer.parseInt(ConstantesECAR.ZERO)){
	    		throw new ECARException("sisAtributo.salvar.erro.grupo.maximo.um");
	    	}
    	}

		
		super.salvar(sisAtributo);
	}

	
	/**
	 * Altera
         * @param sisAtributoBean
         * @throws ECARException
	 */
	public void alterar(SisAtributoSatb sisAtributoBean) throws ECARException {
		
		//Objeto utilizado apenas para testar se é igual.
		SisTipoExibicGrupoSteg tipoGrupo = new SisTipoExibicGrupoSteg();
		
		tipoGrupo.setCodSteg(Long.parseLong(SisTipoExibicGrupoDao.VALIDACAO));
		
		
    	if (sisAtributoBean.getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg() != null && sisAtributoBean.getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().equals(tipoGrupo)) {  
	    	if (sisAtributoBean.getSisGrupoAtributoSga().getSisAtributoSatbs().size() > Integer.parseInt(ConstantesECAR.UM)){
	    		throw new ECARException("sisAtributo.salvar.erro.grupo.maximo.um");
	    	}
    	}

    	
		
		session.evict(sisAtributoBean);
		
		SisAtributoSatb sisAtributo = (SisAtributoSatb) buscar(SisAtributoSatb.class, sisAtributoBean.getCodSatb());

		if (!sisAtributoBean.isAtivo() && sisAtributo.isAtivo()){
			
			EstruturaDao estruturaDao = new EstruturaDao(request);
			List<EstruturaEtt> estruturas = estruturaDao.getEstruturasSisAtributo(sisAtributo);
			
			if (estruturas.size() > 0){
												
				throw new ECARException("sisAtributo.alteracao.erro.indAtivoSatb");
			}
		}
		
		//Verifica se é do tipo ID
		if (sisAtributo.isAtributoAutoIcremental() || sisAtributo.isAtributoContemMascara()) {
			
			if (houveAlteracao(sisAtributoBean,sisAtributo)){
			
				ItemEstruturaDao itemEstDao = new ItemEstruturaDao(request);
				
				List<PontoCriticoSisAtributoPtcSatb> listaItem = itemEstDao.consultarItensEstruturaSisAtributoAtivos(sisAtributoBean);
				
				//Verifica se há itens Estrutura que utilizam o atributo livre 
				if (!listaItem.isEmpty()){
					throw new ECARException("sisAtributo.alteracao.erro");
				}
				
				PontoCriticoDao ptcDao = new PontoCriticoDao(request);
				
				List<PontoCriticoSisAtributoPtcSatb> listaPtc = ptcDao.consultarPontosCriticosSisAtributoAtivos(sisAtributoBean);
				
				//Verifica se há Pontos Criticos que utilizam o atributo livre 
				if (!listaPtc.isEmpty()) {
					throw new ECARException("sisAtributo.alteracao.erro");
				}
			}
		}
		
		session.evict(sisAtributo);
		super.alterar(sisAtributoBean);
	}
    
	private boolean houveAlteracao(SisAtributoSatb sisAtributoBean, SisAtributoSatb sisAtributo) {
		
		boolean ret = false;
		
		try {
			//Verifica se o tipo de validação do atributo livre foi modificado.
			if (!sisAtributoBean.getAtribInfCompSatb().equals(sisAtributo.getAtribInfCompSatb())){
				throw new IllegalArgumentException();
			}
			
			//Verifica se o atributo geral ou se o atributo periodico forma alterados.
			if (sisAtributoBean.getPeriodico() != sisAtributo.getPeriodico() ||
					sisAtributoBean.getGeral() != sisAtributo.getGeral()){
				throw new IllegalArgumentException();
			}
			
			//Verifica se o atributo geral ou se o atributo periodico forma alterados.
			if (!sisAtributoBean.getMascara().equals(sisAtributo.getMascara())) {
				throw new IllegalArgumentException();
			}
			
			
		} catch (IllegalArgumentException e){
			ret = true;
		}
		
		return ret;
	}

	/**
	 * 
	 * @return List
	 * @throws ECARException
	 */
    public List getAtributosTipoAcesso() throws ECARException {
        ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
        SisAtributoSatb atributo = new SisAtributoSatb();
        atributo.setSisGrupoAtributoSga(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrTpAcesso());
        return super.pesquisar(atributo, null);
    }
    
    /**
     * Seta Editorias
     * @param atributo
     * @param segmento
     * @param request
     * @param recuperarParametrosComoString
     */
    public void setEditorias(SisAtributoSatb atributo, SegmentoSgt segmento, HttpServletRequest request, boolean recuperarParametrosComoString){
        if(recuperarParametrosComoString){
            atributo.setDescricaoSatb(Pagina.getParamStr(request, "descricaoSatb"));
            atributo.setIndAtivoSatb(Pagina.getParamStr(request, "indAtivoSatb"));            
        } else {
            atributo.setDescricaoSatb(Pagina.getParam(request, "descricaoSatb"));
            atributo.setIndAtivoSatb(Pagina.getParam(request, "indAtivoSatb"));                        
        }
        atributo.setSisGrupoAtributoSga(segmento.getSisGrupoAtributoSga());
    }
    
    /**
     * 
     * @param atributo
     * @param application
     * @return
     * @throws ECARException
     */
    public List pequisarEditoriasItensLivre(SisAtributoSatb atributo, ServletContext application) throws ECARException{
        List retorno = new ArrayList();
        List segmentosLivres = new SegmentoDao(request).getSegmentosLivres(application);
        Iterator it = segmentosLivres.iterator();
        while(it.hasNext()){
            SegmentoSgt segmento = (SegmentoSgt) it.next();
            retorno.add(this.pesquisarEditoriasBySegmento(atributo, segmento));
        }
        return retorno;
    }
    
    /**
     * Pesquisa editoriais por segmento
     * @param atributo
     * @param segmento
     * @return List
     * @throws ECARException
     */
    public List pesquisarEditoriasBySegmento(SisAtributoSatb atributo, SegmentoSgt segmento) throws ECARException{
        
        List pesquisa = super.pesquisar(atributo, new String[] {"descricaoSatb","asc"});
        List retorno = new ArrayList();
        if(pesquisa.size() > 0){
            Iterator it = pesquisa.iterator();
            while(it.hasNext()){
                 SisAtributoSatb atb = (SisAtributoSatb) it.next();
                 if(segmento.getSisGrupoAtributoSga() != null && atb.getSisGrupoAtributoSga().equals(segmento.getSisGrupoAtributoSga()))
                     retorno.add(atb);                 
            }           
        }
        return pesquisa;
     }
        
    /**
     * Pesquisa
     * 
     * @param atributo
     * @param indTabelaUso
     * @return List
     * @throws ECARException
     */
    public List pesquisar (SisAtributoSatb atributo, String indTabelaUso) throws ECARException
    {
    	List pesquisa = super.pesquisar(atributo, new String[] {"descricaoSatb","asc"});
    	List retorno = new ArrayList();
        if(pesquisa.size() > 0){
        	Iterator it = pesquisa.iterator();
        	while(it.hasNext()){
        		SisAtributoSatb atb = (SisAtributoSatb) it.next();
                if(atb.getSisGrupoAtributoSga().getIndTabelaUsoSga().equals(indTabelaUso))
                	retorno.add(atb);                 
            }           
        }
        return retorno;
    }
    
    /**
     * @author Robson
     * @return List
     * @since 21/11/2007
     * retorna uma lista dos SisAtributos de grupo de acesso   
     */
    public List getListaAcesso(){
    	String hql = new String(
	    	"select atributo " +
	    	"from SisAtributoSatb as atributo " +
	    	"join atributo.sisGrupoAtributoSga as grupo " +
	    	"join grupo.configuracaoCfgsByCodSgaGrAtrClAcesso as config " +
	    	"where atributo.sisGrupoAtributoSga = config.sisGrupoAtributoSgaByCodSgaGrAtrClAcesso " +
	    	"and atributo.indAtivoSatb = \'" + SisAtributoSatb.ATIVO + "\' " +
	    	"order by atributo.descricaoSatb");
    	
    	return this.getSession().createQuery(hql).list();
    }
    
    /**
     * 
     * @param sisGrupoAtributoSga
     * @param descricaoSatb
     * @return
     */
    public SisAtributoSatb getSisAtributoSatb(SisGrupoAtributoSga sisGrupoAtributoSga, String descricaoSatb) throws ECARException{
    	SisAtributoSatb sisAtributoSatb = null;
    	try {
	    	StringBuffer hql = new StringBuffer();
			hql.append("select sisAtributoSatb from SisAtributoSatb sisAtributoSatb ");
			hql.append("where sisAtributoSatb.sisGrupoAtributoSga.codSga = :codSga and ");
			hql.append("sisAtributoSatb.descricaoSatb = :descricaoSatb");
			Query q = this.session.createQuery(hql.toString());
			q.setLong("codSga", sisGrupoAtributoSga.getCodSga());
			q.setString("descricaoSatb", descricaoSatb);
			q.setMaxResults(1);
			sisAtributoSatb = (SisAtributoSatb) q.uniqueResult();
	    } catch(HibernateException e) {
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
    	return sisAtributoSatb;
    }
}
