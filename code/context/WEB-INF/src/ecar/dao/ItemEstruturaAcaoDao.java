/*
 * Criado em 17/12/2004
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.ItemEstrutAcaoIetta;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.acompanhamentoEstrategico.Comentario;
import ecar.util.Dominios;

/**
 * @author felipev
 *  
 */
public class ItemEstruturaAcaoDao extends Dao {

    /**
     *
     * @param request
     */
    public ItemEstruturaAcaoDao(HttpServletRequest request) {
		super();
		this.request = request;
    }

    /**
     * Cria um objeto itemEstruturaBeneficiario a partir de par�metros passados
     * no objeto request
     * 
     * @param request
     * @param itemEstruturaAcao
     * @throws ECARException
     */
    public void setItemEstruturaAcao(HttpServletRequest request,
            ItemEstrutAcaoIetta itemEstruturaAcao) throws ECARException {
        ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) this.buscar(
                ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(
                        request, "codIett")));
        itemEstruturaAcao.setItemEstruturaIett(itemEstrutura);
        itemEstruturaAcao.setDataIetta(Pagina.getParamDataBanco(request,
                "dataIetta"));
        itemEstruturaAcao.setDescricaoIetta(Pagina.getParamStr(request,
                "descricaoIetta"));
        itemEstruturaAcao.setIndAtivoIetta(Pagina.getParamStr(request,
                "indAtivoIetta"));
        String codUsu = Pagina.getParamStr(request,"Usuario");
        UsuarioUsu usuario = (UsuarioUsu) new UsuarioDao(request).buscar(UsuarioUsu.class, Long.valueOf(codUsu));
        itemEstruturaAcao.setUsuarioUsu(usuario);
    }

    /**
     * Grava uma a��o
     * 
     * @param itemEstruturaAcao 
     * @param usuario
     * @throws ECARException
     */
    public void salvar(ItemEstrutAcaoIetta itemEstruturaAcao, UsuarioUsu usuario) throws ECARException {
        itemEstruturaAcao.setDataInclusaoIetta(Data.getDataAtual());
        itemEstruturaAcao.setUsuarioUsu(usuario);
        super.salvar(itemEstruturaAcao);
    }

    /**
     * Recebe um array de c�digos de ItemEstruturaAcao e exclui os registro
     * referenciados por estes c�digos
     * 
     * @param codigosParaExcluir
     * @param usuario
     * @throws ECARException
     */
    public void excluir(String[] codigosParaExcluir, UsuarioUsu usuario) throws ECARException {
        Transaction tx = null;

        try{
        	
        	
        	
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        for (int i = 0; i < codigosParaExcluir.length; i++) {
	            ItemEstrutAcaoIetta itemEstruturaAcao = (ItemEstrutAcaoIetta) this
	                    .buscar(ItemEstrutAcaoIetta.class, Long
	                            .valueOf(codigosParaExcluir[i]));
	            
	            itemEstruturaAcao.setUsuarioUsuManutencao(usuario);
	            itemEstruturaAcao.setIndAtivoIetta(Dominios.NAO);
	            itemEstruturaAcao.setIndExclusaoPosHistorico(Boolean.TRUE);
	            
	            session.update(itemEstruturaAcao);
				objetos.add(itemEstruturaAcao);
	        }
			
			tx.commit();
	
			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("EXC");
				Iterator itObj = objetos.iterator();
	
				while(itObj.hasNext()) {
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
		}
	}

    
    /**
     *
     * @param obj
     * @param usuario
     * @throws ECARException
     */
    public void alterar(ItemEstrutAcaoIetta obj, UsuarioUsu usuario) throws ECARException {
    	obj.setUsuarioUsuManutencao(usuario);
    	super.alterar(obj);
    }
    
    public void salvarComentarioWS(Comentario comentario) throws ECARException {
    	ItemEstrutAcaoIetta coment = new ItemEstrutAcaoIetta();
    	ItemEstruturaIett iett = (ItemEstruturaIett) this.localizar(ItemEstruturaIett.class, comentario.getCodResultado());
    	UsuarioUsu usuario = (UsuarioUsu) this.localizar(UsuarioUsu.class, comentario.getCodResponsavel()!=0?comentario.getCodResponsavel():responsavel(iett).getCodUsu());
    	coment.setDataInclusaoIetta(new Date());
    	coment.setDataIetta(comentario.getPrazo());
    	coment.setItemEstruturaIett(iett);
    	coment.setUsuarioUsu(usuario);
    	coment.setIndAtivoIetta("S");
    	coment.setUsuarioUsuManutencao(usuario);
    	coment.setDescricaoIetta(comentario.getTexto());
    	
    	this.salvar(coment);
    	
    }
    
    @SuppressWarnings("unchecked")
	public List<Comentario> loadComentarioWS(Long codIett, Date dataInicial, Date dataFinal) {
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT new ecar.pojo.acompanhamentoEstrategico.Comentario(");
		hql.append("ac.codIetta,");
		hql.append("ac.dataInclusaoIetta,");
		hql.append("ac.descricaoIetta,");
		hql.append("ac.dataIetta,");
		hql.append("ac.usuarioUsu.codUsu,");
		hql.append("ac.usuarioUsu.nomeUsu");
		hql.append(") ");
		hql.append("FROM ItemEstrutAcaoIetta ac ");
		hql.append("WHERE ac.itemEstruturaIett.codIett = :codIett ");
		
		if(dataInicial != null && dataFinal != null) {
			hql.append("AND ac.dataInclusaoIetta > :dataInicial ");
			hql.append("AND ac.dataInclusaoIetta < :dataFinal ");	
		}
		
		hql.append("AND ac.indAtivoIetta <> 'N' ");	
		
		hql.append("ORDER BY ac.dataInclusaoIetta DESC");
		Query q = this.getSession().createQuery(hql.toString());
		q.setParameter("codIett", codIett);		
		if(dataInicial != null && dataFinal != null) {
			q.setParameter("dataInicial", dataInicial);
			q.setParameter("dataFinal", dataFinal);
		}
		return q.list();
	}
    
    @SuppressWarnings({ "rawtypes" })
	private UsuarioUsu responsavel(ItemEstruturaIett iett) {
		Iterator it = iett.getItemEstUsutpfuacIettutfas().iterator();
		while(it.hasNext()) {
			return ((ItemEstUsutpfuacIettutfa) iett.getItemEstUsutpfuacIettutfas().iterator().next()).getUsuarioUsu();
		}
		return new UsuarioUsu();
	}
    
    public void excluirComentarioWS(Comentario comentario) throws ECARException {
    	ItemEstrutAcaoIetta coment = new ItemEstrutAcaoIetta();    	
    	coment.setCodIetta(comentario.getCodigo());
    	
    	this.excluir(coment);
    	
    }
}