/*
 * Created on 01/02/2005
 *
 */
package ecar.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.bean.AtributoEstruturaListagemItens;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompRefItemLimitesArli;
import ecar.pojo.AcompRefLimitesArl;
import ecar.pojo.AcompRefLimitesArlPK;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstrUplCategIettuc;
import ecar.pojo.ItemEstrutUsuarioIettus;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.Pesquisa;
import ecar.pojo.PesquisaIett;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.UsuarioAtributoUsua;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;


/**
 * @author garten
 *
 */
public class AcompReferenciaDao extends Dao {

	ValidaPermissao validaPermissao = new ValidaPermissao();

	/**
     * 
         * @param request
         */
    public AcompReferenciaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
    
    /**
     * Devolve uma lista de AcompReferencia ordenado por dia/mes/ano
     * @return
     * @throws ECARException
     */
    public List getListAcompReferencia() throws ECARException {
        return listar(AcompReferenciaAref.class, new String[] {"anoAref","desc","mesAref","desc", "diaAref", "desc", "dataInicioAref", "desc"});
    }
    
    /*
     * M�todo comentado (e modificado logo abaixo) devido ao bug 1893:
     * Ao inv�s de retornar o m�s mais pr�ximo, ele retornava ou o m�s mais antigo ou o m�s atual.
     * 
     * 
    public AcompReferenciaAref getAcompSelecionado(List aref){
    	int mesAtual = Data.getCalendar(Data.getDataAtual()).get(Calendar.MONTH) + 1;
		int anoAtual = Data.getCalendar(Data.getDataAtual()).get(Calendar.YEAR);
		int mesAnoAtual = mesAtual + anoAtual;
		AcompReferenciaAref arefSelecionado = null;
		int menorDiferenca = 0;
		Iterator it = aref.iterator();
		while(it.hasNext()){
			AcompReferenciaAref arefAtual = (AcompReferenciaAref) it.next();
			int mesAno = Integer.valueOf(arefAtual.getMesAref()).intValue() + Integer.valueOf(arefAtual.getAnoAref()).intValue();
			if(mesAno - mesAnoAtual == 0)
				return arefAtual;
			else{
				int diferencaAtual = (mesAno - mesAnoAtual);
				if(diferencaAtual < 0)
					diferencaAtual = diferencaAtual * -1;
				if(diferencaAtual > menorDiferenca || menorDiferenca == 0){
					menorDiferenca = diferencaAtual; 
					arefSelecionado = arefAtual;
				}
			}
		}
		return arefSelecionado;
    }*/
    /**
     * Retorna o M�s mais pr�ximo do atual para AcompReferenciaAref
     * @param aref
     * @return AcompReferenciaAref
     */
    public AcompReferenciaAref getAcompSelecionado(List aref){
    	int diaAtual = Data.getCalendar(Data.getDataAtual()).get(Calendar.DAY_OF_MONTH) + 1;
    	int mesAtual = Data.getCalendar(Data.getDataAtual()).get(Calendar.MONTH) + 1;
		int anoAtual = Data.getCalendar(Data.getDataAtual()).get(Calendar.YEAR);
		AcompReferenciaAref arefSelecionado = null;
		
		String anoMesAtualDia = "";
		
		if(diaAtual < 10 && mesAtual < 10){
			anoMesAtualDia = "" + anoAtual + "0" + mesAtual + "0" + diaAtual;
		} else if(mesAtual < 10 && diaAtual >= 10){
			anoMesAtualDia = "" + anoAtual + "0" + mesAtual + diaAtual;
		} else if(mesAtual >= 10 && diaAtual < 10){
			anoMesAtualDia = "" + anoAtual + mesAtual + "0" + diaAtual;
		}else {
			anoMesAtualDia = "" + anoAtual + mesAtual + diaAtual;
			
		}
		
		Integer atual = Integer.valueOf(anoMesAtualDia);
		int maisProximo = 0;
		
		for (Iterator it = aref.iterator(); it.hasNext();) {
			AcompReferenciaAref itemAref = (AcompReferenciaAref) it.next();
			Integer item = Integer.valueOf("" + itemAref.getAnoAref() + itemAref.getMesAref() + itemAref.getDiaAref());
			if(atual.intValue() == item.intValue()){
				return itemAref;
			}else if(item.intValue() < atual.intValue() && item.intValue() > maisProximo){
				maisProximo = item.intValue();
				arefSelecionado = itemAref;
			}else if(item.longValue() > atual.longValue()){
				maisProximo = atual.intValue();
				int diaItem = Integer.valueOf(itemAref.getDiaAref()).intValue();
				int mesItem = Integer.valueOf(itemAref.getMesAref()).intValue();
				int anoItem = Integer.valueOf(itemAref.getAnoAref()).intValue();
				int difItem;
				int difProximo = atual.intValue() - maisProximo;
								
				if(anoItem == anoAtual){ //Se estiverem no mesmo ano
					difItem = atual.intValue() - item.intValue();
					
					int anoProximo = Integer.valueOf(("" + maisProximo).substring(0,4)).intValue();
					int mesProximo = Integer.valueOf(("" + maisProximo).substring(4,6)).intValue();
					int diaProximo = Integer.valueOf(("" + maisProximo).substring(6,8)).intValue();
					
					if(anoProximo < anoAtual){ //testo com ano do Proximo maior que o ano atual
						int qtd = 0;
						int ano = anoAtual;
						int mes = mesAtual;
						int dia = diaAtual;
						while(ano >= anoProximo){
							while(mes >= 1){
								if(ano == anoProximo && mes == mesProximo && dia == diaProximo){
									break;
								}
								qtd++;
								mes--;
							}
							mes = 12;
							ano--;							
						}
						difProximo = qtd;
						
					}
					else if(anoProximo > anoAtual){ //testo com ano do Proximo menos que o atual
						int qtd = 0;
						int ano = anoAtual;
						int mes = mesAtual;
						while(ano <= anoProximo){
							while(mes <= 12){
								if(ano == anoProximo && mes == mesProximo){
									break;
								}
								qtd++;
								mes++;
							}
							mes = 1;
							ano++;
						}
						difProximo = qtd;
					}
					else { //anoProximo == anoAtual
						int mes = mesAtual;
						if(mes == mesProximo){
							arefSelecionado = itemAref;
						}
						else {
							difProximo = mes - mesProximo;
						}
					}
					
					/*
					 * Estas duas linhas abaixo servem para transformar a diferen�a em n�mero positivo, caso d� negativo
					 */
					difItem = (difItem < 0 ? (difItem * -1) : difItem);
					difProximo = (difProximo < 0 ? (difProximo * -1) : difProximo);
					
					if(difItem < difProximo){
						maisProximo = item.intValue();
						arefSelecionado = itemAref;
					}
				}
				else if (anoItem > anoAtual){ //Se ano do item � maior que o ano atual
					int qtdMesesMais = 0;
					int mes = mesAtual;
					int ano = anoAtual;

					while(ano <= anoItem){
						while(mes <= 12){
							if(ano == anoItem && mes == mesItem){
								break;
							}
							qtdMesesMais++;
							mes++;
						}
						mes = 1;
						ano++;
					}
					
					if(qtdMesesMais < difProximo){
						maisProximo = item.intValue();
						arefSelecionado = itemAref;
					}
					
					if(arefSelecionado == null){
						arefSelecionado = itemAref;
					}
				}
				else{ //Ano item � menor que ano atual
					int qtdMesesMenos = 0;
					int mes = mesAtual;
					int ano = anoAtual;
					while(ano >= anoItem){
						while(mes >=1){
							if(ano == anoItem && mes == mesItem){
								break;
							}
							qtdMesesMenos++;
							mes--;
						}
						mes = 12;
						ano--;
					}
					
					if(qtdMesesMenos < difProximo){
						maisProximo = item.intValue();
						arefSelecionado = itemAref;
					}
				}
			}
		}
		return arefSelecionado;
    }

    /**
     * Devolve uma lista de AcompReferencia ordenado por mes/ano
     * @return
     * @throws ECARException
     */
    public List getListAcompReferenciaOrderByNome() throws ECARException {
        return listar(AcompReferenciaAref.class, new String[] {"nomeAref","asc"});
    }
    
    
    /**
     * Devolve uma lista de AcompReferencia com tipoAcompanhamento.indMonitoramentoTa = 'S'
     * @return
     * @throws ECARException
     */
    public List getListAcompReferenciaEmMonitoramento() throws ECARException {
        List lista = null;
        
        try {
            lista = this.session.createQuery("from AcompReferenciaAref a where a.tipoAcompanhamentoTa.indMonitoramentoTa = 'S' order by a.anoAref desc, a.mesAref desc, a.diaAref desc").list();
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        
        return lista;
    }
    
    /**
     * Devolve uma lista de AcompReferencia com cod_ORG preenchido
     * ordenado por cod_ORG
     * @return
     * @throws ECARException
     * @deprecated desde 21/05/2005
     * @see getListAcompReferenciaPorOrgao(OrgaoOrg orgao)  
     */
    public List getListAcompReferenciaPorOrgao() throws ECARException {
        List lista = null;

        try {
            lista = this.session.createQuery("from AcompReferenciaAref a where a.tipoAcompanhamentoTa.indMonitoramentoTa = 'N' order by a.orgaoOrg.codOrg asc, a.anoAref desc, a.mesAref desc, a.diaAref desc").list();
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        
        return lista;
    }
    

    /**
     * Devolve uma lista de acompanhamento de referencia do orgao passado como param.
     * @param orgao
     * @return List AcompReferencia
     * @throws ECARException
     */
    public List getListAcompReferenciaProprioOrgao(OrgaoOrg orgao) throws ECARException {
        try {
        	
        	StringBuilder select = new StringBuilder("from AcompReferenciaAref a ")
        					.append("where a.orgaoOrg.codOrg = :codOrg ")
        					.append("order by a.orgaoOrg.codOrg asc, a.anoAref desc, a.mesAref desc, a.diaAref desc");
        	
        	Query q = this.session.createQuery(select.toString());
        	
        	q.setLong("codOrg", orgao.getCodOrg().longValue());
        	
        	return q.list();
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
    }
    
    /**
     * Devolve uma lista de acompanhamento de referencia cujos orgao sejam diferentes do passado como parametro.
     * @param orgao
     * @return List AcompReferencia
     * @throws ECARException
     */
    public List getListAcompReferenciaOutrosOrgaos(OrgaoOrg orgao) throws ECARException {
        try {
        	StringBuilder select = new StringBuilder("from AcompReferenciaAref a ")
        					.append("where (a.orgaoOrg.codOrg <> :codOrg or a.orgaoOrg is null) ")
        					.append("order by a.orgaoOrg.codOrg asc, a.anoAref desc, a.mesAref desc, a.diaAref desc");
        	
        	Query q = this.session.createQuery(select.toString());
        	
        	q.setLong("codOrg", orgao.getCodOrg().longValue());
        	
            return q.list();
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
    }
    
    
    /**
     * Devolve a lista de datas limite ordenadas de acordo com a hierarquia
     * de funcoes de acompanhamento de filho para pai, ou seja, do mais fraco para
     * o mais forte na hierarquia
     * @param acomp 
     * @return
     * @throws ECARException
     */
    public List getAcompRefLimitesOrderByFuncaoAcomp(AcompReferenciaAref acomp) 
    	throws ECARException {
        
        List lResultado = new ArrayList();
        List lFuncAcomp = new TipoFuncAcompDao(request).getTipoFuncAcompEmitePosicao();
        
        TipoFuncAcompTpfa funcao; 
        AcompRefLimitesArl acompRefLimite;
        
        if (acomp != null && acomp.getAcompRefLimitesArls() != null) {
            /* loop nas funcoes em ordem de filho para pai */
        	
        	for (Iterator itFunc = lFuncAcomp.iterator(); itFunc.hasNext();) {
                funcao = (TipoFuncAcompTpfa) itFunc.next();
                
                for (Iterator itAcomp = acomp.getAcompRefLimitesArls().iterator(); itAcomp.hasNext();) {
                    acompRefLimite = (AcompRefLimitesArl) itAcomp.next();
                    if (acompRefLimite.getTipoFuncAcompTpfa().getCodTpfa().longValue() == funcao.getCodTpfa().longValue()) {
                        lResultado.add(acompRefLimite);
                        break;
                    }
                }
            }
        }
        return lResultado;
    }
    
    /**
     * Retorna um objeto AcompRefLimites a partir de uma Fun��o de Acompanhamento e um Acompanhamento
     * @param acomp
     * @param funcaoAcomp
     * @return
     * @throws ECARException
     */
    public AcompRefLimitesArl getAcompRefLimitesByFuncaoAcomp(AcompReferenciaAref acomp, TipoFuncAcompTpfa funcaoAcomp) 
	throws ECARException {
        try{
        	return (AcompRefLimitesArl) this.getSession()
        				.get(AcompRefLimitesArl.class, 
        						new AcompRefLimitesArlPK(acomp.getCodAref(),funcaoAcomp.getCodTpfa()));
        } catch(HibernateException e){
        	this.logger.error(e);
            throw new ECARException(e);
        }       
}
    
       
    /**
     * Cria um objeto acompReferencia a partir de par�metros passados
     * no objeto request
     * 
     * @param request
     * @param acompReferencia 
     * @param orgaoOrg
     * @param tipoAcompanhamento
     * @throws ECARException
     */
    public void setAcompReferencia(HttpServletRequest request, AcompReferenciaAref acompReferencia, Long orgaoOrg, TipoAcompanhamentoTa tipoAcompanhamento) throws ECARException {

    	
    	String possuiOrgaoInformado = "";
    	
    	acompReferencia.setTipoAcompanhamentoTa(tipoAcompanhamento);
        
        if (orgaoOrg != null) {
            acompReferencia.setOrgaoOrg( (OrgaoOrg) this.buscar(OrgaoOrg.class, orgaoOrg));
        }
        else {
            acompReferencia.setOrgaoOrg(null);
        }
        
        
       //se for inclusao 
        if(acompReferencia.getCodAref() == null) {
        	acompReferencia.setAnoAref(Pagina.getParamStr(request, "anoAref").trim());
        	acompReferencia.setMesAref(Pagina.getParamStr(request, "mesAref"));
        	String diaAref = Pagina.getParamStr(request, "diaAref").trim();
        	if(diaAref.length() < 2)
        		diaAref = "0" + diaAref; 
        	acompReferencia.setDiaAref(diaAref);
        	acompReferencia.setDataInicioAref(Data.parseDate(Pagina.getParamStr(request, "dataInicioAref")));
        	possuiOrgaoInformado = Pagina.getParamStr(request, "checkItensOrgaoInformado");
        	if("".equals(possuiOrgaoInformado))
        		possuiOrgaoInformado = "S";
        	acompReferencia.setIndInformacaoOrgaoAref(possuiOrgaoInformado);
        	this.validaInclusaoExercicioPeriodoReferencia(acompReferencia); 
            
        }
        
        acompReferencia.setNomeAref(Pagina.getParamStr(request, "nomeAref").trim());
        acompReferencia.setDataLimiteAcompFisicoAref(Data.parseDate(Pagina.getParamStr(request, "dataLimiteAcompFisicoAref")));
        acompReferencia.setDataUltManutAref(Data.getDataAtual());
        acompReferencia.setExercicioExe(getExercicio(acompReferencia.getAnoAref(), acompReferencia.getMesAref(), acompReferencia.getDiaAref()));
                
        setAcompLimites(request, acompReferencia);
      
    }
    
    /**
     * M�todo para criar a cole��o de acompLimites a partir de
     * par�metros passados por request
     * 
     * @param request
     * @param acompReferencia
     * @throws ECARException
     */
    public void setAcompLimites(HttpServletRequest request, AcompReferenciaAref acompReferencia) throws ECARException {
    	    	
    	Set limites = new HashSet();
	
    	for (Iterator it = new TipoFuncAcompDao(request).getTipoFuncAcompEmitePosicao().iterator(); it.hasNext();) {
    		TipoFuncAcompTpfa funcao = (TipoFuncAcompTpfa) it.next();
    		
    		if (!"".equals(Pagina.getParamStr(request, "prazoFinalPara" + funcao.getCodTpfa().toString()))) {
    			AcompRefLimitesArl acompLimite;

    			// busca um ARL para alterar a data
    			acompLimite = this.getAcompRefLimitesByFuncaoAcomp(acompReferencia, funcao);
    			if(acompLimite != null){
        			acompLimite.setDataLimiteArl(Data.parseDate(Pagina.getParamStr(request, "prazoFinalPara" + funcao.getCodTpfa().toString())));
        			
    			} else {
    			    // senao insere um novo ARL
    			    acompLimite = new AcompRefLimitesArl();
        			acompLimite.setAcompReferenciaAref(acompReferencia);
        			acompLimite.setTipoFuncAcompTpfa(funcao);
        			acompLimite.setDataLimiteArl(Data.parseDate(Pagina.getParamStr(request, "prazoFinalPara" + funcao.getCodTpfa().toString())));
    			}
    			limites.add(acompLimite);	
    		}
    		
    	}
   	
    	// 
    	//acompReferencia.setAcompRefLimitesArls(null);
    	acompReferencia.setAcompRefLimitesArls(limites);
    }
    
    /**
     * Exclui acompReferencia, e seus filhos acompRefLimites relacionados;
     * 
     * @param codigosParaExcluir
     * @throws ECARException
     * @throws HibernateException
     */
    public void excluir(String[] codigosParaExcluir) throws ECARException, HibernateException {
    	AcompRealFisicoDao arfDao = new AcompRealFisicoDao(null);
    	PesquisaDao pesquisaDao = new PesquisaDao(null);
    	List lista = new ArrayList();
    	for (int i = 0; i < codigosParaExcluir.length; i++) {
        	AcompReferenciaAref acompReferencia = (AcompReferenciaAref) buscar(AcompReferenciaAref.class, Long.valueOf(codigosParaExcluir[i]));
        	/*
        	 * Valida��o referente ao bug 898
        	 */
        	if (acompReferencia.getAcompReferenciaItemAris() != null){
        		acompReferencia.getAcompReferenciaItemAris().size();
        		List itensAri = new ArrayList(acompReferencia.getAcompReferenciaItemAris());
        		
        		for (Iterator itItensAri = itensAri.iterator(); itItensAri.hasNext();) {
        			AcompReferenciaItemAri acompRefItAri = (AcompReferenciaItemAri) itItensAri.next();
        			
        			if(acompRefItAri.getStatusRelatorioSrl() != null
        					&& acompRefItAri.getStatusRelatorioSrl().getCodSrl() != null
        					&& acompRefItAri.getStatusRelatorioSrl().getCodSrl().intValue() == AcompReferenciaItemDao.STATUS_LIBERADO) {

        				throw new ECARException("periodoReferencia.exclusao.posicaoJaLiberada", null, new String[] {acompRefItAri.getItemEstruturaIett().getNomeIett()});

        			}
        			/*
        			C�digo anterior
        			---------------
        			if (acompRefItAri.getAcompRelatorioArels() != null){
        				acompRefItAri.getAcompRelatorioArels().size();
        				List itensArel = new ArrayList(acompRefItAri.getAcompRelatorioArels());
        				Iterator itItensArel = itensArel.iterator();
        				while(itItensArel.hasNext()){
        					AcompRelatorioArel aRel = (AcompRelatorioArel) itItensArel.next();
        					if ("S".equals(aRel.getIndLiberadoArel())){
        						//throw new ECARException("periodoReferencia.exclusao.posicaoJaLiberada");
        						throw new ECARException("periodoReferencia.exclusao.posicaoJaLiberada", null, new String[] {acompRefItAri.getItemEstruturaIett().getNomeIett()});
        					}
        				}
        			}
        			*/
        			
        			//Adiciona o hist�rico de upload aos objetos a serem exclu�dos caso exista
        			if (acompRefItAri.getItemEstrUplCategIettuc() != null && acompRefItAri.getItemEstrUplCategIettuc().size() > 0){
	        			List<ItemEstrUplCategIettuc> categoriasUpload = new ArrayList<ItemEstrUplCategIettuc>(acompRefItAri.getItemEstrUplCategIettuc());                	                
	  	                List historicosCategoriaExcluir = new HistoricoItemEstrutUploadDao(request).getHistoricoPorCategoriaUpload(categoriasUpload);
	  	                
	  	                lista.addAll(historicosCategoriaExcluir);
        			}
        			
        			// Nova verifica��o devido ao Mantis 5518
	                // verificar o ARF correspondente ao ARI que podem ser exclu�do
	    			StringBuilder query = new StringBuilder("select ARI from AcompReferenciaItemAri as ARI")
	    							.append(" where ARI.itemEstruturaIett.codIett = :codIett")
	    							.append(" and ARI.itemEstruturaIett.indAtivoIett = 'S'")
	    							.append(" and ARI.acompReferenciaAref.diaAref = :diaAref")
	    							.append(" and ARI.acompReferenciaAref.mesAref = :mesAref")
	    							.append(" and ARI.acompReferenciaAref.anoAref = :anoAref")
	    							.append(" and ARI.codAri<> :codAri");
	    			
	    			Query q = this.getSession().createQuery(query.toString());
	    			
	    			q.setLong("codIett", acompRefItAri.getItemEstruturaIett().getCodIett().longValue());
	    			q.setString("diaAref", acompRefItAri.getAcompReferenciaAref().getDiaAref());
	    			q.setString("mesAref", acompRefItAri.getAcompReferenciaAref().getMesAref());
	    			q.setString("anoAref", acompRefItAri.getAcompReferenciaAref().getAnoAref());
	    			q.setLong("codAri", acompRefItAri.getCodAri().longValue());
					
					List listaARI = q.list();
					
					if(listaARI == null || listaARI.isEmpty()) {
						// o ARF correspondente pode ser removido pois n�o est� em uso
						List listArf = arfDao.buscarPorIett(
								acompRefItAri.getItemEstruturaIett().getCodIett(),
								Long.valueOf(acompRefItAri.getAcompReferenciaAref().getMesAref()),
								Long.valueOf(acompRefItAri.getAcompReferenciaAref().getAnoAref()));
						
						if(listArf != null && !listArf.isEmpty()) {
			                lista.addAll(listArf);
						}
					}
        		}
        	}
        	lista.addAll(acompReferencia.getAcompRefLimitesArls());
        	lista.addAll(acompReferencia.getAcompReferenciaItemAris());
        	lista.add(acompReferencia);
        }
    	//Percorre a lista de Pesquisas verificando
    	//quais pesquisas foram salvas com os aref's selecionados para exclus�o.
    	//Exclui o objeto da classe Pesquisa se todos os objetos PesquisaIett forem exclu�dos. 
    	List<Pesquisa> pesquisas = pesquisaDao.listarPesquisasByAref(codigosParaExcluir);
    	List listaExclusao = new ArrayList();
    	for (Pesquisa pesquisa : pesquisas) {
    		boolean excluirPesquisa = true;
			List<PesquisaIett> pesquisasItens = new ArrayList(pesquisa.getPesquisaIetts());
			for (PesquisaIett pesquisaIett : pesquisasItens) {
				boolean excluirPesquisaIett = false;
				for (int i = 0; i < codigosParaExcluir.length; i++) {
					if (codigosParaExcluir[i].equals(pesquisaIett.getAcompReferenciaAref().getCodAref().toString())){
						excluirPesquisaIett = true;
						break;
					} 
				}
				if (excluirPesquisaIett){
					listaExclusao.add(pesquisaIett);
				} else {
					excluirPesquisa = false;
				}
			}
			if (excluirPesquisa){
				listaExclusao.add(pesquisa);
			}
		}
    	lista.addAll(0, listaExclusao);

    	super.excluir(lista);
    }
        
    /**
     * Lista todos os itensEstrutura de AcompReferenciaItem, para um AcompReferenciaAref 
     * lista[0] = retorna a arvore de itens
     * lista[1] = retorna os itens selecionaveis
     * 
     * @param listAris
     * @param tipoAcompanhamento
     * @param usuario UsuarioUsu
     * @param gruposUsuario Set
     * @param tipoAcesso String
     * @param somenteAcompanhamento String
     * 
     * @param nuPagina 
     * @param nuItensPaginacao
     * @return lista de itensEstrutura
     * @throws ECARException
     */
    public List[] getItemEstruturaAcompanhamento (List listAris, 
    		TipoAcompanhamentoTa tipoAcompanhamento, UsuarioUsu usuario, 
    		Set gruposUsuario, String tipoAcesso, String somenteAcompanhamento, 
    		int nuPagina, int nuItensPaginacao) throws ECARException{
    	
    	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
    	
    	List[] retorno = new List[4];	
    	List selecionaveis = new ArrayList();
    	List itensSemParecer = new ArrayList();
    	List selecionaveisOrdenadosPaginados = new ArrayList();
    	    	
    	// indices inicial e final para a consulta dos itensEstrutura
    	int indiceInicial = (nuPagina-1)*nuItensPaginacao + 1;
    	int indiceFinal = (nuPagina-1)*nuItensPaginacao + nuItensPaginacao;
    	int contador = 1; // variavel que guarda qual o indice no iterador
    	   	
    	for (Iterator it = listAris.iterator(); it.hasNext();) {
    		AcompReferenciaItemAri acompRefItem = (AcompReferenciaItemAri) it.next();
    		if( validaPermissao.permissaoAcessoItemElaboracaoAcomp(acompRefItem.getItemEstruturaIett(), usuario, gruposUsuario, tipoAcesso, tipoAcompanhamento)) {
	        	if(acompRefItem.getAcompRelatorioArels() != null && acompRefItem.getAcompRelatorioArels().size() > 0) {
    				selecionaveis.add(acompRefItem.getItemEstruturaIett());
    			} else if("N".equals(somenteAcompanhamento)) {
    				selecionaveis.add(acompRefItem.getItemEstruturaIett());
    				itensSemParecer.add(acompRefItem.getItemEstruturaIett());
    			}
	        }
    	}
    	
    	//Selecionar os itens da pagina que esta sendo visualizada
    	List selecionaveisOrdenados = itemEstruturaDao.getItensOrdenados(selecionaveis, tipoAcompanhamento);
    	Iterator selecionaveisOrdenadosIt = selecionaveisOrdenados.iterator();
    	while(selecionaveisOrdenadosIt.hasNext()) {
    		AtributoEstruturaListagemItens atEstListagem = (AtributoEstruturaListagemItens)selecionaveisOrdenadosIt.next();
    		if(indiceInicial <= contador && contador <= indiceFinal) {
    			selecionaveisOrdenadosPaginados.add(atEstListagem);
    		} else if(contador > indiceFinal) {
    			break;
    		}
    		contador++;
    	}
    	//selecionaveis ordenados e j� paginados
    	retorno[0] = selecionaveisOrdenadosPaginados;
    	retorno[1] = selecionaveis; 
    	retorno[2] = itensSemParecer;
    	retorno[3] = selecionaveisOrdenados;
    	
		return retorno;
    }
    
    /**
     * Retorna o Exerc�cio de um AcompReferencia, ou ent�o de um ano e mes 
     * passados como parametro.
     * 
     * @param ano
     * @param mes
     * @return
     * @throws ECARException
     */
    public ExercicioExe getExercicio(String ano, String mes) throws ECARException{
    	ExercicioExe exercicio = new ExercicioExe();
    	
    	/* Padr�o iniciar o Exerc�cio � o dia 01 */
    	/* Padr�o de data no HQL, � como no Banco de Dados */
    	StringBuilder select = new StringBuilder("select distinct exercicio from ExercicioExe as exercicio")
    							.append(" where exercicio.dataInicialExe <= :data")
    							.append(" and exercicio.dataFinalExe >= :data");
    	
		Date data = Data.parseDate("01/" + mes + "/" + ano);
		
		try {
			
			Query q = this.getSession().createQuery(select.toString());
			
			q.setDate("data", data);
			
			List lista = q.list();
			
			Iterator it = lista.iterator();
			
			if (it.hasNext()){
				exercicio = (ExercicioExe) it.next();
				return exercicio;
			} 
			else {
				return null;	
			}
		} catch (HibernateException e){
			this.logger.error(e);
			throw new ECARException (e);
		}
    }
    
    /**
     * Retorna o Exerc�cio de um AcompReferencia, ou ent�o de um ano, mes e dia 
     * passados como parametro.
     * 
     * @param ano
     * @param mes
     * @param dia
     * @return
     * @throws ECARException
     */
    public ExercicioExe getExercicio(String ano, String mes, String dia) throws ECARException{
    	ExercicioExe exercicio = new ExercicioExe();
    	
    	/* Padr�o iniciar o Exerc�cio � o dia 01 */
    	/* Padr�o de data no HQL, � como no Banco de Dados */
    	StringBuilder select = new StringBuilder("select distinct exercicio from ExercicioExe as exercicio")
    							.append(" where exercicio.dataInicialExe <= :data")
    							.append(" and exercicio.dataFinalExe >= :data");
    	
		Date data = Data.parseDate(dia + "/" + mes + "/" + ano);
		
		try {
			
			Query q = this.getSession().createQuery(select.toString());
			
			q.setDate("data", data);
			
			List lista = q.list();
			
			Iterator it = lista.iterator();
			
			if (it.hasNext()){
				exercicio = (ExercicioExe) it.next();
				return exercicio;
			} 
			else {
				return null;	
			}
		} catch (HibernateException e){
			this.logger.error(e);
			throw new ECARException (e);
		}
    }
    
    
   
    /**
     * Retorna lista de itens descendentes (filhos) de um itemPai, que satisfa�am as 
     * regras abaixo:
     *    - os itens devem possuir indicador de resultado, e quantidade prevista.
     *    - os itens devem possuir o mesmo AcompReferencia do Pai.
     * 
     * lista[0] = retorna a arvore de itens at� o filho selecionavel
     * lista[1] = retorna os filhos selecionaveis
     * 
     * @param acompReferencia
     * @param itemPai
     * @param usuario
     * @return
     * @throws HibernateException
     * @throws ECARException
     */
    public List[] getItensFilhosByAcompReferencia(AcompReferenciaAref acompReferencia,
    					ItemEstruturaIett itemPai,UsuarioUsu usuario) throws HibernateException, ECARException{
    	List[] retorno = new List[2];
    	List listaAux = new ArrayList();

    	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
    	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);

		// Itens gravados para o acompanhamento
		List listItensGravados = new ArrayList();
		if(acompReferencia.getCodAref() != null) {
			listItensGravados = acompReferenciaItemDao.getListaItensAcompanhamento(acompReferencia);
		}
		
		//List listNiveis = new ArrayList(acompReferencia.getTipoAcompanhamentoTa().getSisAtributoSatbs());

		List listaItem = itemEstruturaDao.getDescendentes(itemPai, true);
    	
		for (Iterator it = listaItem.iterator(); it.hasNext();) {
    		ItemEstruturaIett item = (ItemEstruturaIett) it.next();
    		
    		StringBuilder select = new StringBuilder("select item from ItemEstruturaIett as item")
    							.append(" left join item.itemEstrtIndResulIettrs as indResultados")
    							.append(" left join indResultados.itemEstrutFisicoIettfs as qtdPrevista")
    							.append(" where ((qtdPrevista.indAtivoIettf = 'S') OR (qtdPrevista.indAtivoIettf is null))")
    							.append(" and item.codIett = :codIett")
    							.append(" and item.indAtivoIett = 'S'");
    		
    		Query q = this.getSession().createQuery(select.toString());
    		
    		q.setLong("codIett", item.getCodIett().longValue());
    		
   			List<ItemEstruturaIett> listaQtdPrev = q.list();
    		
   			boolean permissaoParecerLeituraMetaIndicador = false;
   			//obtem a lista de itens
   			for (ItemEstruturaIett item_inner : listaQtdPrev) {
   				
   				Collection<ItemEstrutUsuarioIettus> listaIettus = item_inner.getItemEstrutUsuarioIettusesByCodIett();
   				//obtem a lista de permiss�es
   				for (ItemEstrutUsuarioIettus iettus : listaIettus) {
					
   					//verifica se h� alguma permiss�o de leitura de parecer e a permiss�o � para o usu�rio passado ou se o grupo de acesso contido no iettus est� na lista de grupos de acesso do usu�rio.
   					if ((iettus.getIndLeituraParecerIettus() != null && iettus.getIndLeituraParecerIettus().equals(Dominios.SIM)) && (usuario.equals(iettus.getUsuarioUsu()) || consultarPermissaoUsuario(usuario,iettus))) {
   						permissaoParecerLeituraMetaIndicador = true;
   						break;//interrompe o loop mais interno
   					}
				}
   				if (permissaoParecerLeituraMetaIndicador){//J� encontrou portanto interrompe o loop mais externo
   					break;
   				}
   				
			}
   			
   			if (!permissaoParecerLeituraMetaIndicador || listaQtdPrev.isEmpty() || !listItensGravados.contains(item)) { 
    			listaAux.add(item);
    		}
   			
   			/*if (!permissaoParecerLeituraMetaIndicador) { 
	   			if(listaQtdPrev.isEmpty()){
	    			listaAux.add(item);
	    		}else{
	    			if(!listItensGravados.contains(item)){
	    				listaAux.add(item);
	    			}
	    		}
   			}*/
    	}
    	
    	listaItem.removeAll(listaAux);
    	
    	retorno[0] = itemEstruturaDao.getArvoreItens(listaItem, itemPai);
    	retorno[0].remove(itemPai);
    	retorno[1] = listaItem;

    	return retorno;
    }

    /**
     * Verifica na lista de grupos do usu�rio se ele possui acesso a permiss�o(iettus) enviada como par�metro. 
     * @param usuario
     * @param iettus
     * @return
     */
    private boolean consultarPermissaoUsuario(UsuarioUsu usuario, ItemEstrutUsuarioIettus iettus) {
		
    	boolean ret = false;
    	
    	
    	Set<UsuarioAtributoUsua> listaUsuarioAtributo = usuario.getUsuarioAtributoUsuas();
    	
    	for (UsuarioAtributoUsua usuarioAtributo : listaUsuarioAtributo) {
			
    		if (usuarioAtributo.getSisAtributoSatb().equals(iettus.getSisAtributoSatb())){
    			ret = true;
    			break;
    		}
		}
    	
		return ret;
	}

	/**
     * Valida a possibilidade de inclus�o de um per�odo de refer�ncia.
     * Dispara exce��es correspondetes a motivos que impe�am o cadastro de um acompanhemto:
     * periodoReferencia.validacao.mesAnoTipoAcompanhamento.jaExistente = J� existe um acompanhamento para este m�s/ano deste tipo de acompanhamento
     * periodoReferencia.validacao.exercicioNaoEncontrado = N�o existe um exerc�cio cadastrado que compreenda o dia/m�s/ano informado
     * @param acompanhamentoReferencia
     * @throws ECARException
     */
    public void validaInclusaoExercicioPeriodoReferencia(AcompReferenciaAref acompanhamentoReferencia) throws ECARException{        
    	if(getExercicio(acompanhamentoReferencia.getAnoAref(), acompanhamentoReferencia.getMesAref(), acompanhamentoReferencia.getDiaAref()) == null) {
    		throw new ECARException("periodoReferencia.validacao.exercicioNaoEncontrado");
    	}
    }
    
	/**
     * Valida a possibilidade de inclus�o de um per�odo de refer�ncia com o mesmo dia, mes e ano
     * @param acompanhamentoReferencia
     * @throws ECARException
     */
    public void validaInclusaoDiaMesAnoPeriodoReferencia(AcompReferenciaAref acompanhamentoReferencia) throws ECARException{        
    	// se uma referencia com orgao especifico se existir a mesma referencia dia, mes e ano 
    	
    	if(existeMesmaReferenciaDiaMesAno(acompanhamentoReferencia)) {
    		throw new ECARException("periodoReferencia.validacao.mesAnoTipoAcompanhamento.jaExistente");
    		
    	} 
    }
    
    
    

    /**
     * Retorna uma cole��o com os n periodos anteriores a um periodo.
     * @param codArefReferencia O C�digo do periodo de referencia
     * @param numPeriodosAnteriores Quantos periodos devem ser retornados
     * @param tipoAcompanhamento Tipo de Acompanhamento
     * 
     * @return Cole�ao de AcompReferenciaAref
     * @throws ECARException
     */
    public Collection getPeriodosAnteriores(Long codArefReferencia, int numPeriodosAnteriores, Long tipoAcompanhamento) throws ECARException{
        return getPeriodosAnterioresOrdenado(codArefReferencia, numPeriodosAnteriores, tipoAcompanhamento, true);
    }
    
    /**
     * Retorna uma cole��o com os n periodos anteriores a um periodo.
     * @param codArefReferencia O C�digo do periodo de referencia
     * @param numPeriodosAnteriores Quantos periodos devem ser retornados
     * @param tipoAcompanhamento Tipo de Acompanhamento
     * 
     * @param ordena
     * @return Cole�ao de AcompReferenciaAref
     * @throws ECARException
     */
    
    public Collection getPeriodosAnterioresOrdenado(Long codArefReferencia, int numPeriodosAnteriores, Long tipoAcompanhamento, boolean ordena) throws ECARException{
        
        try{
            
        	
        	AcompReferenciaAref aref = (AcompReferenciaAref) this.buscar(AcompReferenciaAref.class, codArefReferencia);
            
            // Descobre todos os Aref que ser�o considerados, dependendo da Data de Inicio do Aref e o n�mero de meses anteriores
        	String diaReferencia = aref.getDiaAref();
			String mesReferencia = aref.getMesAref();
			String anoReferencia = aref.getAnoAref();
			
			//pesquisa o periodo de referencia selecionado
			StringBuilder query = new StringBuilder("select aref from AcompReferenciaAref aref")
				.append(" where aref.codAref = :codArefReferencia")
				.append(" and aref.tipoAcompanhamentoTa.codTa = :codTa");
		
			Query queryAcompanhamentos = this.getSession().createQuery(query.toString());
		    queryAcompanhamentos.setLong("codArefReferencia", codArefReferencia);
		    queryAcompanhamentos.setLong("codTa", tipoAcompanhamento.longValue());
          
			//adiciona a lista
		    List retorno = queryAcompanhamentos.list();
//		    AcompReferenciaAref arefResultado = (AcompReferenciaAref) retorno.get(0);
		    
			//diminui a quantidade de resultados maximos obtidos porque o primeiro j� � a pr�pria refere�ncia
		    numPeriodosAnteriores = numPeriodosAnteriores - 1;
			
			if(numPeriodosAnteriores >0) {
                                    
				query = new StringBuilder("select aref from AcompReferenciaAref aref ")
            					.append(" where (aref.anoAref < :anoReferencia  or (aref.anoAref = :anoReferencia and aref.mesAref <= :mesReferencia) or (aref.anoAref = :anoReferencia and aref.mesAref = :mesReferencia and aref.diaAref <= :diaReferencia))")
            					.append("and aref.codAref <> :codArefReferencia")
            					.append(" and aref.tipoAcompanhamentoTa.codTa = :codTa")
            					.append(" order by aref.anoAref desc, aref.mesAref desc, aref.diaAref desc, aref.dataInicioAref desc");
            
				queryAcompanhamentos = this.getSession().createQuery(query.toString());
	            queryAcompanhamentos.setString("anoReferencia", anoReferencia);
	            queryAcompanhamentos.setString("mesReferencia", mesReferencia);
	            queryAcompanhamentos.setString("diaReferencia", diaReferencia);
	            queryAcompanhamentos.setLong("codArefReferencia", codArefReferencia);
	            queryAcompanhamentos.setLong("codTa", tipoAcompanhamento.longValue());
	            
	            /* quantidade de resultados ser� o n�mero de periodos anteriores */
	            queryAcompanhamentos.setMaxResults(numPeriodosAnteriores);
	            
	            
	            if(queryAcompanhamentos!= null) {
	            	//adiciona a lista os periodos anteriores
	            	Iterator it = queryAcompanhamentos.iterate();
	            	while(it.hasNext())	{
	            		AcompReferenciaAref arefTeste = (AcompReferenciaAref) it.next();
	            		retorno.add(arefTeste);
	            	}
	            } 
			}

			if(ordena) {
            	Collections.reverse(retorno);
            }
                        
            return retorno;
        
            
        }catch(HibernateException e){
        	this.logger.error(e);
            throw new ECARException(e);
        }
    }
    
    
    /**
     * Retorna uma lista com todos os �rg�os que possuem algum acompanhamento de refer�ncia criado
     * @param codTipoAcompanhamento
     * @return
     * @throws ECARException
     */
    public Collection getOrgaosComAcompanhamentosCriados(String codTipoAcompanhamento) throws ECARException{
        try{
        	StringBuilder query = new StringBuilder("select distinct aref.orgaoOrg from AcompReferenciaAref aref");
        	if(!"".equals(codTipoAcompanhamento)) {
        		query.append(" where aref.tipoAcompanhamentoTa.codTa=").append(codTipoAcompanhamento);
        	}
            Query queryItens = this.getSession().createQuery(query.toString());
            
            return queryItens.list();
        } catch(HibernateException e){
        	this.logger.error(e);
        	throw new ECARException(e);
        }
        
    }

    /**
     * Verifica se existe quantidades de realizado f�sico.
     * @param codigos
     * @return
     * @throws ECARException
     */
    public boolean existeQuantidades(String[] codigos) throws ECARException {
    	AcompRealFisicoDao arfDao = new AcompRealFisicoDao(null); 
    	for (int i = 0; i < codigos.length; i++) {

    		AcompReferenciaAref acompReferencia = (AcompReferenciaAref) buscar(AcompReferenciaAref.class, Long.valueOf(codigos[i]));

        	if (acompReferencia.getAcompReferenciaItemAris() != null){
        		acompReferencia.getAcompReferenciaItemAris().size();

        		
        		for (Iterator itItensAri = acompReferencia.getAcompReferenciaItemAris().iterator(); itItensAri.hasNext();) {
        			AcompReferenciaItemAri acompRefItAri = (AcompReferenciaItemAri) itItensAri.next();
        			
        			List listArf = arfDao.buscarPorIett(
        					acompRefItAri.getItemEstruturaIett().getCodIett(),
        					Long.valueOf(acompRefItAri.getAcompReferenciaAref().getMesAref()),
        					Long.valueOf(acompRefItAri.getAcompReferenciaAref().getAnoAref()));

        			if(listArf != null){
        				
        				for (Iterator itItensArfs = listArf.iterator(); itItensArfs.hasNext();) {
        					AcompRealFisicoArf acompRealFisicoArf = (AcompRealFisicoArf) itItensArfs.next();
        					if(acompRealFisicoArf.getQtdRealizadaArf() != null && acompRealFisicoArf.getQtdRealizadaArf().doubleValue() > 0){
        						return true;
        					}
        				}
        			}
        		}
        	}
        }
    	return false;
    }
    
    /**
     * Obter os �ltimos acompanhamentos (AcompReferenciaAref) de um item.
     * @param item
     * @param qtdeUltimosAcompanhamentos
     * @return
     * @throws HibernateException
     * @throws ECARException
     */
    public List getUltimoAcompanhamentoItem (ItemEstruturaIett item, Integer qtdeUltimosAcompanhamentos) 
    			throws HibernateException, ECARException {
    	try{
	        StringBuilder str = new StringBuilder(); 
	        str.append("select aref from AcompReferenciaAref aref");
	        str.append(" join aref.acompReferenciaItemAris as aris");
	        str.append(" where aris.itemEstruturaIett.codIett = :codItem");
	        str.append(" and aris.itemEstruturaIett.indAtivoIett = 'S'");
	        str.append(" order by aref.anoAref desc, aref.mesAref desc, aref.diaAref desc");
	        
	        Query query = this.getSession().createQuery(str.toString());
	        query.setLong("codItem", item.getCodIett().longValue());
	        if(qtdeUltimosAcompanhamentos != null){
	        	query.setMaxResults(qtdeUltimosAcompanhamentos.intValue());
	        }
	    	
	    	return query.list();

    	} catch(HibernateException e){
    		this.logger.error(e);
            throw new ECARException(e);
        }
    }
    
    /**
     * Obter os acompanhamentos (AcompReferenciaAref) que sejam de um tipo de acompanhamento
     * @param codTipoAcompanhamento Long
     * @return
     * @throws HibernateException
     * @throws ECARException
     */
    public List<AcompReferenciaAref> getListAcompReferenciaByTipoAcompanhamento(Long codTipoAcompanhamento) throws HibernateException, ECARException {
    	try{
	        StringBuilder str = new StringBuilder(); 
	        str.append("select aref from AcompReferenciaAref aref");
	        str.append(" where aref.tipoAcompanhamentoTa.codTa = :codTa");
	        str.append(" order by aref.anoAref desc, aref.mesAref desc, aref.diaAref desc, aref.dataInicioAref desc");
	        
	        Query query = this.getSession().createQuery(str.toString());
	        query.setLong("codTa", codTipoAcompanhamento.longValue());
	    	return query.list();

    	} catch(HibernateException e){
    		this.logger.error(e);
            throw new ECARException(e);
        }
    }

    /**
     * Obtem a lista de acompanhamento que sejam de um tipo de acompanhamento que est�o abertos.
     * Abertos s�o os acompanhamento que tem pelo menos uma data para emitir parecer no futuro.
     *  
     * @param codTipoAcompanhamento Long
     * @return
     * @throws HibernateException
     * @throws ECARException
     */
    @SuppressWarnings("unchecked")
	public List<AcompReferenciaAref> getListAcompReferenciaByTipoAcompanhamentoAbertos(Long codTipoAcompanhamento) throws HibernateException, ECARException {
    	try{
    		List<AcompReferenciaAref> listaAref = this.getListAcompReferenciaByTipoAcompanhamento(codTipoAcompanhamento);
    		List<AcompReferenciaAref> listaArefRetorno = new ArrayList<AcompReferenciaAref>();
    		for (AcompReferenciaAref aref : listaAref) {
				Boolean aberto = false;
	    		Set<AcompRefLimitesArl> limites = aref.getAcompRefLimitesArls();
	    		
	    		aberto = (aref.getDataLimiteAcompFisicoAref().before(new Date()));
	    		
	    		if (!aberto)
				for (AcompRefLimitesArl arl : limites) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Integer hj = new Integer(sdf.format(new Date()));
					
					if (hj <= new Integer(sdf.format(arl.getDataLimiteArl()))) {
						aberto = true;
						break;
					}
				}
				if (aberto) {
					listaArefRetorno.add(aref);
				}
			}
    		return listaArefRetorno;
    	} catch(HibernateException e){
    		this.logger.error(e);
            throw new ECARException(e);
        }
    }

    /**
     * Obtem a lista de acompanhamento que sejam de um tipo de acompanhamento que j� foram concluidos.
     * Conclu�dos s�o os acompanhamento que n�o tem nenhuma data limite para emitir parecer no futuro.
     *  
     * @param codTipoAcompanhamento Long
     * @return
     * @throws HibernateException
     * @throws ECARException
     */
    public List<AcompReferenciaAref> getListAcompReferenciaByTipoAcompanhamentoConcluidos(Long codTipoAcompanhamento) throws HibernateException, ECARException {
    	try{
    		List<AcompReferenciaAref> listaAref = this.getListAcompReferenciaByTipoAcompanhamento(codTipoAcompanhamento);
    		List<AcompReferenciaAref> listaArefRetorno = new ArrayList<AcompReferenciaAref>();
    		for (AcompReferenciaAref aref : listaAref) {
				Boolean concluido = true;
	    		Set<AcompRefLimitesArl> limites = aref.getAcompRefLimitesArls();
	    		
	    		 if (  aref.getDataLimiteAcompFisicoAref().compareTo(new Date()) >=0){
		    			concluido = false; 
		    	 }
	    		
	    		if (concluido) 
				for (AcompRefLimitesArl arl : limites) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Integer hj = new Integer(sdf.format(new Date()));
					
					if (hj <= new Integer(sdf.format(arl.getDataLimiteArl()))) {
						concluido = false;
						break;
					}
				}
				if (concluido) {
					listaArefRetorno.add(aref);
				}
			}
    		return listaArefRetorno;
    	} catch(HibernateException e){
    		this.logger.error(e);
            throw new ECARException(e);
        }
    }
    
    /**
     * Obter os ARIs de um AcompReferenciaAref
     * @param codAref Long
     * @return List de AcompReferenciaItemAri
     * @throws HibernateException
     * @throws ECARException
     */
    public List getAcompReferenciaItemAriByAref(Long codAref) throws HibernateException, ECARException {
    	try{
	        StringBuilder str = new StringBuilder(); 
	        str.append("select ari from AcompReferenciaItemAri ari");
	        str.append(" where ari.acompReferenciaAref.codAref = :codAref");
	        
	        Query query = this.getSession().createQuery(str.toString());
	        query.setLong("codAref", codAref.longValue());
	    	
	    	return query.list();

    	} catch(HibernateException e){
    		this.logger.error(e);
            throw new ECARException(e);
        }
    }

    /**
     *
     * @param codAri
     * @param codTpfa
     * @return
     * @throws ECARException
     */
    public AcompRefItemLimitesArli getAcompRefItemLimitesArliByAriAndTpfa(Long codAri, Long codTpfa) throws ECARException {
    	try{
	        StringBuilder str = new StringBuilder(); 
	        str.append("select arli from AcompRefItemLimitesArli arli");
	        str.append(" where arli.acompReferenciaItemAri.codAri = :codAri");
	        str.append(" and arli.tipoFuncAcompTpfa.codTpfa = :codTpfa");
	        
	        Query query = this.getSession().createQuery(str.toString());
	        query.setLong("codAri", codAri.longValue());
	        query.setLong("codTpfa", codTpfa.longValue());
            query.setMaxResults(1);
            
            return (AcompRefItemLimitesArli)query.uniqueResult();
            

    	} catch(HibernateException e){
    		this.logger.error(e);
            throw new ECARException(e);
        }
    }
    
    /**
     * Devolve uma lista de AcompReferencia que o usu�rio fa�a parte como fun��o de acompanhamento'
     * @param seguranca 
     * @return
     * @throws ECARException
     */
    public List getListAcompReferenciaSuaResponsabilidade(SegurancaECAR seguranca) throws ECARException {
        List lista = null;
//        AcompReferenciaAref acompReferenciaAref = null;
 //       acompReferenciaAref.getAcompReferenciaItemAris();
  //      AcompReferenciaItemAri ari = null;
   //     ari.getItemEstruturaIett().getItemEstUsutpfuacIettutfas()

        try {
        	
            Query query = this.session.createQuery("select aref from AcompReferenciaAref aref " +
            								 "join aref.acompReferenciaItemAris as aris " +
            								 "join aref.acompReferenciaItemAris as aris " +
            								 "join iett.itemEstUsutpfuacIettutfas as iettutfas " +
            								 "where aris.itemEstruturaIett.indAtivoIett = 'S' " +
            								 "and " +
            								 "		(iettutfas.usuarioUsu = :usuario or iettutfas.sisAtributoSatb in (:gruposAcesso)) " +
            								 "order by aref.anoAref desc, aref.mesAref desc, aref.diaAref desc");        
            
		
            query.setParameterList("gruposAcesso", seguranca.getGruposAcesso()); 
			query.setParameter("usuario", seguranca.getUsuario()); 
            
            lista = query.list();

        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        
        return lista;
    }
    
    
    
    
    /**
     * Verifica a exist�ncia de um AcompReferencia para o mesmo Dia, M�s e Ano, para um mesmo tipo de acompanhametno
     *   tanto para altera��o, quanto para inclus�o.
     * 
     * @param acompReferencia
     * @return boolean
     * @throws HibernateException
     * @throws ECARException
     */
    public boolean existeMesmaReferenciaDiaMesAno(AcompReferenciaAref acompReferencia) throws HibernateException, ECARException{
    	StringBuilder select = new StringBuilder("select acompRef from AcompReferenciaAref as acompRef")
    							.append(" where acompRef.anoAref = :ano")
    							.append(" and acompRef.mesAref = :mes")
    							.append(" and acompRef.diaAref = :dia")
    							.append(" and acompRef.tipoAcompanhamentoTa.codTa= :codTipoAcompanhamento");
    							
    							//se for separado por orgao, pesquisa se existe um acompanhamento para o mesmo orgao
    							if(acompReferencia.getOrgaoOrg() != null) {
    								select.append(" and acompRef.orgaoOrg.codOrg= :codOrgao");
    							} else {
    							//se nao for separado por orgao, pesquisa se existe um acompanhamento para a mesma referencia	
    								select.append(" and acompRef.orgaoOrg.codOrg is null");
    							}
    	
    	Query q = this.getSession().createQuery(select.toString());
    	
    	q.setString("ano", acompReferencia.getAnoAref());
    	q.setString("mes", acompReferencia.getMesAref());
    	q.setString("dia", acompReferencia.getDiaAref());
    	q.setLong("codTipoAcompanhamento", acompReferencia.getTipoAcompanhamentoTa().getCodTa());
    	if(acompReferencia.getOrgaoOrg() != null) {
    		q.setString("codOrgao", acompReferencia.getOrgaoOrg().getCodOrg().toString());
    	} 
		List listaAcompRef = q.list();
		
		if(listaAcompRef.size() <= 0){
			// N�o existe nenhum AcompReferencia para o m�s e ano
			return false;
		}else{
			return true;
		
		}
    }
    
    
    
    /**
     * Retorna uma lista de referencias que tenham o mesmo dia, mes, ano e orgao da referencia passada como parametro
     * @param acompReferencia
     * @return boolean
     * @throws HibernateException
     * @throws ECARException
     */
    public List getListaMesmaReferenciaDiaMesAno(AcompReferenciaAref acompReferencia) throws HibernateException, ECARException{
    	StringBuilder select = new StringBuilder("select acompRef from AcompReferenciaAref as acompRef")
    							.append(" where acompRef.anoAref = :ano")
    							.append(" and acompRef.mesAref = :mes")
    							.append(" and acompRef.diaAref = :dia")
    							.append(" and acompRef.tipoAcompanhamentoTa.codTa= :codTipoAcompanhamento");
    	
    	Query q = this.getSession().createQuery(select.toString());
    	
    	q.setString("ano", acompReferencia.getAnoAref());
    	q.setString("mes", acompReferencia.getMesAref());
    	q.setString("dia", acompReferencia.getDiaAref());
    	q.setLong("codTipoAcompanhamento", acompReferencia.getTipoAcompanhamentoTa().getCodTa());
    	List listaAcompRef = q.list();
		
		return listaAcompRef;
    }
    
    public int getListaMesmaReferenciaDiaMesAnoCount(AcompReferenciaAref acompReferencia) throws HibernateException, ECARException{
    	StringBuilder select = new StringBuilder("select count(acompRef.codAref) from AcompReferenciaAref as acompRef")
    							.append(" where acompRef.anoAref = :ano")
    							.append(" and acompRef.mesAref = :mes")
    							.append(" and acompRef.diaAref = :dia")
    							.append(" and acompRef.tipoAcompanhamentoTa.codTa= :codTipoAcompanhamento");
    	
    	Query q = this.getSession().createQuery(select.toString());
    	
    	q.setString("ano", acompReferencia.getAnoAref());
    	q.setString("mes", acompReferencia.getMesAref());
    	q.setString("dia", acompReferencia.getDiaAref());
    	q.setLong("codTipoAcompanhamento", acompReferencia.getTipoAcompanhamentoTa().getCodTa());
    	
    	try {
    		Long listaAcompRef = (Long) q.uniqueResult();
    		return listaAcompRef.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
    }
    
    
    
   /** 
    * Retorna uma lista de referencias do acompanhamento que tenham dia, mes e ano diferentes. 
    * @param acompReferencia
    * @return List
    * @throws HibernateException
    * @throws ECARException
    */
   public List getComboReferencia(TipoAcompanhamentoTa tipoAcompanhamento) throws HibernateException, ECARException {
	   
	   
	   AcompReferenciaAref acompReferencia = null;
	   AcompReferenciaAref acompReferenciaUnificada = null;  
	   List listaReferencias = new ArrayList();
	   List acompanhamentos = getListAcompReferenciaByTipoAcompanhamento(tipoAcompanhamento.getCodTa());
	   Iterator itAcomp2 = acompanhamentos.iterator();
	   boolean existeJaNaLista = false;
		
		//percorre a lista de referencias do acompanhamentos
	    while(itAcomp2.hasNext()){
			acompReferencia = (AcompReferenciaAref) itAcomp2.next();
			existeJaNaLista = false;
			List listaMesmaReferenciaDiaMesAno = getListaMesmaReferenciaDiaMesAno(acompReferencia);
			
			if(listaMesmaReferenciaDiaMesAno.isEmpty() || listaReferencias.isEmpty()) {
				listaReferencias.add(acompReferencia);
			} else {
				Iterator itListaReferencias = listaReferencias.iterator();
				while(itListaReferencias.hasNext()) {
					acompReferenciaUnificada = (AcompReferenciaAref) itListaReferencias.next();
					if(acompReferenciaUnificada.getDiaAref().equals(acompReferencia.getDiaAref()) &&
							acompReferenciaUnificada.getMesAref().equals(acompReferencia.getMesAref()) && 
							acompReferenciaUnificada.getAnoAref().equals(acompReferencia.getAnoAref())) {
						existeJaNaLista = true; 
						break;
					}
				}
				
				if(!existeJaNaLista)
					listaReferencias.add(acompReferencia);
			}
	   
	    }
	return listaReferencias;
   } 
   
   /**
    * Retorna uma lista dos distintos �rg�os dos per�odos de refer�ncia passados por par�metro
    * 
    * @param periodosReferencia
    * @return
    */
   public List<OrgaoOrg> getOrgaosReferencias(Collection<AcompReferenciaAref> periodosReferencia){
	   
	   List<OrgaoOrg> orgaosReferencias = new ArrayList<OrgaoOrg>();
	   
	   Iterator itPeriodosConsiderados = periodosReferencia.iterator();
		
		//adiciona o �rg�o de cada refer�ncia na lista de �rg�os
		while (itPeriodosConsiderados.hasNext()){
			
			AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itPeriodosConsiderados.next();
			
			if (acompReferencia.getOrgaoOrg() != null){
				if (!orgaosReferencias.contains(acompReferencia.getOrgaoOrg())){
					orgaosReferencias.add(acompReferencia.getOrgaoOrg());
				}
			}
			
		}
		
		return orgaosReferencias;
   }
   
   
   /**
    * Retorna uma lista dos distintos �rg�os dos per�odos de refer�ncia passados por par�metro
    * 
    * @param periodosReferencia
    * @return
    */
   public List<OrgaoOrg> getOrgaosReferenciasSomenteSeusOrgaos(Collection<AcompReferenciaAref> periodosReferencia, List<OrgaoOrg> orgaosUsuario){
	   
	   List<OrgaoOrg> orgaosReferencias = new ArrayList<OrgaoOrg>();
	   
	   Iterator itPeriodosConsiderados = periodosReferencia.iterator();
		
		//adiciona o �rg�o de cada refer�ncia na lista de �rg�os
		while (itPeriodosConsiderados.hasNext() && orgaosUsuario != null){
			
			AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itPeriodosConsiderados.next();
			
			if (acompReferencia.getOrgaoOrg() != null && orgaosUsuario.contains(acompReferencia.getOrgaoOrg())){
				if (!orgaosReferencias.contains(acompReferencia.getOrgaoOrg())){
					orgaosReferencias.add(acompReferencia.getOrgaoOrg());
				}
			}
			
		}
		
		return orgaosReferencias;
   }
   
   
   /**
    * Retorna uma lista de referencias que tenham o mesmo dia, mes, ano e tipo de acompanhamento das referencia passadas como parametro
    * @param acompReferencias
    * @return List
    * @throws HibernateException
    * @throws ECARException
    */
   public List<AcompReferenciaAref> getListaMesmaReferenciaDiaMesAno(Collection<AcompReferenciaAref> acompReferencias) throws HibernateException, ECARException{
   	
	   List<AcompReferenciaAref> listaAcompRef = new ArrayList<AcompReferenciaAref>();
	   
	   StringBuilder select = new StringBuilder("select acompRef from AcompReferenciaAref as acompRef")
		.append(" where acompRef.anoAref = :ano")
		.append(" and acompRef.mesAref = :mes")
		.append(" and acompRef.diaAref = :dia")
		.append(" and acompRef.tipoAcompanhamentoTa.codTa= :codTipoAcompanhamento");
	   
	   Query q = this.getSession().createQuery(select.toString());
	   
	   for (AcompReferenciaAref acompReferenciaAref : acompReferencias) {
				
			q.setString("ano", acompReferenciaAref.getAnoAref());
			q.setString("mes", acompReferenciaAref.getMesAref());
			q.setString("dia", acompReferenciaAref.getDiaAref());
			q.setString("codTipoAcompanhamento", acompReferenciaAref.getTipoAcompanhamentoTa().getCodTa().toString());
			
			listaAcompRef.addAll(q.list()); 
	   }	  
			
	   return listaAcompRef;
   }
   
   /**
    * Retorna uma lista de per�odos considerados agrupados a partir dos per�odos exibidos no combo e do n�mero de per�odos escolhidos para visualiza��o
    * @param acompReferenciasAgrupadas
    * @param acompReferenciaAtual
    * @param numPeriodosConsiderados
    * @return List<AcompReferenciaAref>
    */
   public List<AcompReferenciaAref> getPeriodosAgrupadosConsiderados (List acompReferenciasAgrupadas, AcompReferenciaAref acompReferenciaAtual, int numPeriodosConsiderados){
	   
	   List<AcompReferenciaAref> periodosAgrupadosConsiderados = new ArrayList<AcompReferenciaAref>();
	   
	   //recupera a posi��o da refer�ncia atual na lista de refer�ncias que � exibida no combo
	   int posicaoReferenciaAtual = acompReferenciasAgrupadas.indexOf(acompReferenciaAtual);
	   
	   //adiciona a refer�ncia atual � lista de retorno
	   periodosAgrupadosConsiderados.add(acompReferenciaAtual);
	   
	   //adiciona os n periodos agrupados considerados ap�s a refer�ncia atual
	   for (int i=1; i<numPeriodosConsiderados; i++){
		   if ((posicaoReferenciaAtual + i) < acompReferenciasAgrupadas.size()){
			   periodosAgrupadosConsiderados.add((AcompReferenciaAref)acompReferenciasAgrupadas.get(posicaoReferenciaAtual + i));
		   }
	   }
	   
	   return periodosAgrupadosConsiderados;
   }
   
   /**
    * Recupera a referencia pelo orgao, dia, mes e ano da refer�ncia passada como par�metro
    * 
    * @param orgao
    * @param aref
    * @return
    * @throws ECARException
    * 
    */
   public AcompReferenciaAref getAcompReferenciaByOrgaoDiaMesAnoAref(OrgaoOrg orgao, AcompReferenciaAref aref) throws ECARException{
	   try{   		
		   StringBuilder query = new StringBuilder("select aref from AcompReferenciaAref aref")
    							.append(" where aref.diaAref = :diaAref")
    							.append(" and aref.mesAref = :mesAref")
    							.append(" and aref.anoAref = :anoAref")
		   						.append(" and aref.tipoAcompanhamentoTa.codTa= :codTipoAcompanhamento");
    	
		   if(orgao != null)
			   query.append(" and aref.orgaoOrg.codOrg = :codOrg");
		   else 
			   query.append(" and aref.orgaoOrg.codOrg is null");
	
		   Query q = this.getSession().createQuery(query.toString());
		   if(orgao != null)
			   q.setLong("codOrg", orgao.getCodOrg().longValue());
		   q.setString("diaAref", aref.getDiaAref());
		   q.setString("mesAref", aref.getMesAref());
		   q.setString("anoAref", aref.getAnoAref());
		   q.setString("codTipoAcompanhamento", aref.getTipoAcompanhamentoTa().getCodTa().toString());
	
		   return (AcompReferenciaAref) q.uniqueResult();

	   } catch(HibernateException e){
		   this.logger.error(e);
		   throw new ECARException(e);
       }
   }
   
   /**
    * Retorna uma lista de per�odos considerados agrupados a partir dos per�odos exibidos no combo e do n�mero de per�odos escolhidos para visualiza��o
    * preenchendo a lista com as refer�ncias que tenham o mesmo �rg�o da refer�ncia passada por par�metro.
    * @param acompReferenciasAgrupadas
    * @param acompReferenciaAtual
    * @param numPeriodosConsiderados
    * @return List<AcompReferenciaAref>
    */
   public List<AcompReferenciaAref> getPeriodosAgrupadosConsideradosMesmoOrgao (List acompReferenciasAgrupadas, AcompReferenciaAref acompReferenciaAtualCombo, AcompReferenciaAref acompReferenciaSelecionadaAri, int numPeriodosConsiderados) throws ECARException{
	   
	   OrgaoOrg orgaoSelecionado = acompReferenciaSelecionadaAri.getOrgaoOrg();
	   
	   List<AcompReferenciaAref> periodosAgrupadosConsideradosMesmoOrgao = new ArrayList<AcompReferenciaAref>();
	   
	   //recupera a posi��o da refer�ncia atual na lista de refer�ncias que � exibida no combo
	   int posicaoReferenciaAtual = acompReferenciasAgrupadas.indexOf(acompReferenciaAtualCombo);
	   
	   //adiciona a refer�ncia atual do ari � lista de retorno
	   periodosAgrupadosConsideradosMesmoOrgao.add(acompReferenciaSelecionadaAri);
	   
	   //adiciona os n periodos agrupados considerados ap�s a refer�ncia atual
	   for (int i=1; i<numPeriodosConsiderados && (posicaoReferenciaAtual + i<acompReferenciasAgrupadas.size()-1); i++){
		   
		   AcompReferenciaAref acompReferencia = (AcompReferenciaAref)acompReferenciasAgrupadas.get(posicaoReferenciaAtual + i);
		   		   		   
		   AcompReferenciaAref referenciaMesmoOrgao = getAcompReferenciaByOrgaoDiaMesAnoAref(orgaoSelecionado, acompReferencia);
		   
		   if (referenciaMesmoOrgao == null)
			   referenciaMesmoOrgao = acompReferencia;
		   
		   periodosAgrupadosConsideradosMesmoOrgao.add(referenciaMesmoOrgao);
	   }
	   
	   return periodosAgrupadosConsideradosMesmoOrgao;
   }
   
   /**
    * Retorna a referencia da listagem de refer�ncias agrupadas do combo que tenha o mesmo dia/mes/ano da refer�ncia passada como par�metro
    * @param acompReferencias
    * @param acompReferenciaSelecionadaAri
    * @return
    */
   public AcompReferenciaAref getReferenciaMesmoDiaMesAno (List acompReferencias, AcompReferenciaAref acompReferenciaSelecionada){
	   
	   AcompReferenciaAref acompReferenciaSelecionadaListagemAgrupada = null;
	   
	   Iterator itReferencias = acompReferencias.iterator();
	   
	   while (itReferencias.hasNext()){
		   
		   acompReferenciaSelecionadaListagemAgrupada = (AcompReferenciaAref) itReferencias.next();
		   if (acompReferenciaSelecionadaListagemAgrupada.getDiaAref().equals(acompReferenciaSelecionada.getDiaAref()) &&
				   acompReferenciaSelecionadaListagemAgrupada.getMesAref().equals(acompReferenciaSelecionada.getMesAref()) &&
				   acompReferenciaSelecionadaListagemAgrupada.getAnoAref().equals(acompReferenciaSelecionada.getAnoAref())){
			   
			   return acompReferenciaSelecionadaListagemAgrupada;
		   }
	   }
	   
	   return acompReferenciaSelecionadaListagemAgrupada;
   }
   
   public Object[] getMesAnoByNomeAref(String nomeAref) {
	   StringBuilder query = new StringBuilder("select aref.mesAref, aref.anoAref from AcompReferenciaAref aref")
		.append(" where aref.nomeAref = :nomeAref");
	   
	   Query q = this.getSession().createQuery(query.toString());
	   q.setParameter("nomeAref", nomeAref);
	   return (Object[]) q.list().get(0);
   }
   
   public AcompReferenciaAref buscarAref(Long codAref) {
   	String hql = "FROM AcompReferenciaAref aref WHERE aref.codAref = :cod";
   	Query q = getSession().createQuery(hql);
   	q.setLong("cod", codAref);
   	return (AcompReferenciaAref) q.uniqueResult();
   }
   
   public Long getUltimoPeriodoAcompanhamentoId() {
   	String sql = "select max(codAref) from AcompReferenciaAref";
   	Query q = getSession().createQuery(sql);
   	return (Long) q.uniqueResult();
   }
       
}