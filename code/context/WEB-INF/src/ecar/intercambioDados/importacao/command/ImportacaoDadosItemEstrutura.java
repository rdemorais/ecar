package ecar.intercambioDados.importacao.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import comum.util.ConstantesECAR;

import ecar.dao.FuncaoDao;
import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.intercambioDados.IBusinessObject;
import ecar.pojo.FuncaoFun;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.UsuarioUsu;

public class ImportacaoDadosItemEstrutura extends ImportacaoDados {

	@Override
	public String importar(IBusinessObject objetoNegocio, Transaction tx,
			HttpServletRequest request, UsuarioUsu usuarioLogado, Logger logger)
			throws ECARException {
		
		ItemEstruturaIett iett = (ItemEstruturaIett)objetoNegocio;
		ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
		FuncaoDao funcaoDao = new FuncaoDao(request);
		FuncaoFun funcao = funcaoDao.getFuncaoDadosGerais();
		String tipoOperacao = null;
		
//		ItemEstruturaIett itemEstruturaIett = itemEstruturaDao.getItemEstruturaIettByEstruturaSiglaIett(configuracao.getPerfil().getEstruturaEttCriacaoItemImp(), itemEstruturaDTO.getCodigo(), itemEstruturaDTO.getValorAssociacao(), itemEstruturaDTO.getTipoEmpreendimento());
//		if (itemEstruturaDTO.getOperacao().equals(ConstantesECAR.TIPO_OPERACAO_MANUTENCAO)){				
//			//Inclusão se o item não existir na base de dados 
//			if (itemEstruturaIett == null){
//				itemEstruturaDTO.setOperacao(ConstantesECAR.TIPO_OPERACAO_INCLUSAO);
//				listaItensInclusao.add(montadorObjetonegocio.montaItemEstruturaIett(itemEstruturaIett, itemEstruturaDTO, usuarioLogado));
//			} else {
//				//Altera o item caso já exista na base de dados
//				itemEstruturaDTO.setOperacao(ConstantesECAR.TIPO_OPERACAO_ALTERACAO);
//				listaItensAlteracao.add(montadorObjetonegocio.montaItemEstruturaIett(itemEstruturaIett, itemEstruturaDTO, usuarioLogado));
//			}
//		} else if (itemEstruturaDTO.getOperacao().equals(ConstantesECAR.TIPO_OPERACAO_EXCLUSAO)){
//			listaItensExclusao.add(itemEstruturaIett);
//		}
		
		try {	
				// inclusao
				if (iett.getCodIett()==null) {
					itemDao.salvar(request,tx,iett,funcao,false);
					tipoOperacao = ConstantesECAR.TIPO_OPERACAO_INCLUSAO;
					
				// alteração ou exclusao
				} else if(iett.getCodIett()!=null) {
					// alteracao
					if (iett.getIndAtivoIett().equals("S")) {
						itemDao.alterar(tx, request, usuarioLogado, iett);
						tipoOperacao = ConstantesECAR.TIPO_OPERACAO_ALTERACAO;
					// exclusao
					} else {
						itemDao.excluir(tx, new String[]{iett.getCodIett().toString()},usuarioLogado);
						tipoOperacao = ConstantesECAR.TIPO_OPERACAO_EXCLUSAO;
					}
				}
			
		} catch (IOException ioex){
			if (tx != null) {
				try {
					tx.rollback();
				} catch (HibernateException r) {
					logger.error(r);
					throw new ECARException("erro.arquivo"); 
				}
			}
			logger.error(ioex);
			throw new ECARException("erro.arquivo");
		} catch (HibernateException hbmex){
			if (tx != null) {
				try {
					tx.rollback();
				} catch (HibernateException r) {
					logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
			}
	        logger.error(hbmex);
	        throw new ECARException("erro.hibernateException"); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tipoOperacao;

	}

}
