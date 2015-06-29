/*
 * Criado em 14/12/2004
 *
 */
package ecar.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.BeneficiarioBnf;
import ecar.pojo.ItemEstrtBenefIettb;
import ecar.pojo.ItemEstrtBenefIettbPK;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.UsuarioUsu;

/**
 * @author felipev
 *  
 */
public class ItemEstruturaBeneficiarioDao extends Dao {
	/*private BeneficiarioDao beneficiarioDao = null;*/
	
    /**
     *
     * @param request
     */
    public ItemEstruturaBeneficiarioDao(HttpServletRequest request) {
		super();
		this.request = request;
		/*beneficiarioDao = new BeneficiarioDao(request);*/
    }

    /**
     * Retorna um objeto ItemEstrtBenefIettb a partir do código do Item da
     * Estrutura e do código do beneficiário
     * 
     * @param codItemEstrutura
     * @param codBeneficiario
     * @return
     * @throws ECARException
     */
    public ItemEstrtBenefIettb buscar(Long codItemEstrutura,
            Long codBeneficiario) throws ECARException {
        ItemEstrtBenefIettbPK comp_id = new ItemEstrtBenefIettbPK();
        comp_id.setCodBnf(codBeneficiario);
        comp_id.setCodIett(codItemEstrutura);
        return (ItemEstrtBenefIettb) super.buscar(ItemEstrtBenefIettb.class,comp_id);
    }

    /**
     * Cria um objeto itemEstruturaBeneficiario a partir de parâmetros passados
     * no objeto request
     * 
     * @param request
     * @param itemEstruturaBeneficiario
     * @throws ECARException
     */
    public void setItemEstruturaBeneficiario(HttpServletRequest request,ItemEstrtBenefIettb itemEstruturaBeneficiario) throws ECARException {
    	
    	BeneficiarioDao benefDao =  new BeneficiarioDao(request);
    	
        ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) this.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
        BeneficiarioBnf beneficiario = (BeneficiarioBnf) benefDao.buscar(BeneficiarioBnf.class, Long.valueOf(Pagina.getParamStr(request, "codBnf")));
        UsuarioUsu usuario = ((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
        
        
        itemEstruturaBeneficiario.setBeneficiarioBnf(beneficiario);
        itemEstruturaBeneficiario.setItemEstruturaIett(itemEstrutura);
        
        itemEstruturaBeneficiario.setComentarioIettb(Pagina.getParamStr(request, "comentarioIEttB"));
        
        if (Pagina.getParamStr(request, "qtdePrevistaIEttB") != null) {
            itemEstruturaBeneficiario.setQtdPrevistaIettb(new BigDecimal(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, "qtdePrevistaIEttB"))).doubleValue()));
        }
        
        itemEstruturaBeneficiario.setDataUltManutencaoIettb(Data.getDataAtual());
        itemEstruturaBeneficiario.setUsuarioUsuManutencao(usuario);
    }

    /**
     * Método utilizado para setar os valores da PK da classe
     * ItemEStruturaBeneficiario
     * 
     * @param itemEstruturaBeneficiario
     */
    public void setPK(ItemEstrtBenefIettb itemEstruturaBeneficiario) {
        ItemEstrtBenefIettbPK comp_id = new ItemEstrtBenefIettbPK();
        comp_id.setCodBnf(itemEstruturaBeneficiario.getBeneficiarioBnf()
                .getCodBnf());
        comp_id.setCodIett(itemEstruturaBeneficiario.getItemEstruturaIett()
                .getCodIett());
        itemEstruturaBeneficiario.setComp_id(comp_id);
    }

    /**
     * Grava uma relação entre itemEstrutura e Beneficiário
     * 
     * @param itemEstruturaBeneficiario
     * @throws ECARException
     */
    public void salvar(ItemEstrtBenefIettb itemEstruturaBeneficiario)
            throws ECARException {
        	
    	ItemEstrtBenefIettb itemEstruturaBeneficiarioConsultado = null;
    	
    	try {
    		itemEstruturaBeneficiarioConsultado = buscar(itemEstruturaBeneficiario.getItemEstruturaIett().getCodIett(), itemEstruturaBeneficiario.getBeneficiarioBnf().getCodBnf());
    	} catch (ECARException ecarex) {
    		if (!(ecarex.getCausaRaiz() instanceof ObjectNotFoundException)){
    			throw ecarex;
    		}
    	}
    	
    	//O beneficiário nunca foi cadastrado para o item.
    	if (itemEstruturaBeneficiarioConsultado == null) {
    		itemEstruturaBeneficiario.atribuirPKPai();
    		
            super.salvar(itemEstruturaBeneficiario);
    	} else if (itemEstruturaBeneficiarioConsultado.getIndExclusaoPosHistorico() == null || !itemEstruturaBeneficiarioConsultado.getIndExclusaoPosHistorico()){ // O Beneficiario já existe e está ativo deve levantar erro de tentativa de cadastro duplicado 
    		throw new ECARException("itemEstrutura.beneficiario.inclusao.jaExiste");
    	} else if (itemEstruturaBeneficiarioConsultado.getIndExclusaoPosHistorico()){//O Beneficiario já existe e NÃO está ativo, portanto deve atualizá-lo e ativá-lo.
    		
    		itemEstruturaBeneficiarioConsultado.setIndExclusaoPosHistorico(false);
    		itemEstruturaBeneficiarioConsultado.setComentarioIettb(itemEstruturaBeneficiario.getComentarioIettb());
    		itemEstruturaBeneficiarioConsultado.setDataUltManutencaoIettb(itemEstruturaBeneficiario.getDataUltManutencaoIettb());
    		itemEstruturaBeneficiarioConsultado.setQtdPrevistaIettb(itemEstruturaBeneficiario.getQtdPrevistaIettb());
    		itemEstruturaBeneficiarioConsultado.setUsuarioUsuManutencao(itemEstruturaBeneficiario.getUsuarioUsuManutencao());    		
            
    		super.alterar(itemEstruturaBeneficiarioConsultado);
    	}
        	
    }

    /**
     * Altera os atributos de uma relação entre ItemEstrutura e Beneficiário
     * 
     * @param itemEstruturaBeneficiario
     * @throws ECARException
     */
    public void alterar(ItemEstrtBenefIettb itemEstruturaBeneficiario)
            throws ECARException {
        super.alterar(itemEstruturaBeneficiario);
    }

    /**
     * Recebe um código de item estrutura e um array contendo códigos de
     * beneficiários e exclui todos os registros que relacioname este item
     * estrutura com caada um dos códigos de beneficiários
     * 
     * @param codigosParaExcluir
     * @param codItemEstrutura
     * @param usuarioUsu
     * @throws ECARException
     */
    public void excluir(String[] codigosParaExcluir, Long codItemEstrutura, UsuarioUsu usuarioUsu)
            throws ECARException {
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        for (int i = 0; i < codigosParaExcluir.length; i++) {
	            ItemEstrtBenefIettb itemEstruturaBenef = buscar(codItemEstrutura,
	                    Long.valueOf(codigosParaExcluir[i]));

	            itemEstruturaBenef.setUsuarioUsuManutencao(usuarioUsu);
	            itemEstruturaBenef.setDataUltManutencaoIettb(new Date());
	            itemEstruturaBenef.setIndExclusaoPosHistorico(Boolean.TRUE);
	            
            	session.update(itemEstruturaBenef);
				objetos.add(itemEstruturaBenef);
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
}

