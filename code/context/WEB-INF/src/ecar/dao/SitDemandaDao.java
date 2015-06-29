package ecar.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.SitDemandaSitd;

/**
 * Classe de manipulação de objetos da classe AtributoAtt.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Fri Jan 27 07:54:28 BRST 2006
 *
 */
public class SitDemandaDao extends Dao {
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public SitDemandaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Verifica depois exclui
	 * @param sitDemanda
	 * @throws ECARException
	 */
	public void excluir(SitDemandaSitd sitDemanda) throws ECARException {	    
		try{
			boolean excluir = true;
			if(contar(sitDemanda.getRegDemandaRegds()) > 0){
				excluir = false;
				throw new ECARException("sitDemanda.exclusao.erro.regDemandaRegds");
			}  

			if(contar(sitDemanda.getVisaoSituacaoDemandas()) > 0){
				excluir = false;
				throw new ECARException("sitDemanda.exclusao.erro.visoes");
			}  
			//(SitDemandaSitd) VisaoDao.buscar(SitDemandaSitd.class, Long.valueOf(Pagina.getParam(request, "codigo")))
			
			if(excluir)
				super.excluir(sitDemanda);
		}catch(ECARException e){
			this.logger.error(e);
			throw e;
		}    
	}	
	
	/**
	 * 
	 * @return List
	 * @throws ECARException
	 */
	public List buscar() throws ECARException {
		return super.listar((new SitDemandaSitd()).getClass(), new String[] {"descricaoSitd", Dao.ORDEM_ASC});
	}
	
	/**
	 * verifica duplicação de registro depois salva
         * @param situacaoDemanda
         * @throws ECARException
	 */
	public void salvar(SitDemandaSitd situacaoDemanda) throws ECARException {
		if (pesquisarDuplos(situacaoDemanda, new String[] {"descricaoSitd"}, "codSitd").size() > 0)
		    throw new ECARException("situacao.validacao.registroDuplicado");
		
		//antes de salvar verifica se existe alguma situação já setada como primeira situação
		if (jaTemPrimeiraSituacao(situacaoDemanda))
			throw new ECARException("situacaoDemanda.validacao.indPrimeiraSituacaoSitd.duplicada");
		
		super.salvar(situacaoDemanda);
	}
	
	/**
	 * 
	 * verifica duplicação de registro depois Altera
         * @param situacaoDemanda
         * @throws ECARException
	 */
	public void alterar(SitDemandaSitd situacaoDemanda) throws ECARException {
		if (pesquisarDuplos(situacaoDemanda, new String[] {"descricaoSitd"}, "codSitd").size() > 0)
		    throw new ECARException("situacao.validacao.registroDuplicado");
		
		//antes de salvar verifica se existe alguma situação já setada como primeira situação
		if (jaTemPrimeiraSituacao(situacaoDemanda))
			throw new ECARException("situacaoDemanda.validacao.indPrimeiraSituacaoSitd.duplicada");
		
		super.alterar(situacaoDemanda);
	}
	
	private boolean jaTemPrimeiraSituacao(SitDemandaSitd situacaoDemanda) throws ECARException{
		
		boolean jaTemPrimeiraSituacao = false;
		
		if (situacaoDemanda.getIndPrimeiraSituacaoSitd().equals("S")){
			
			List list = listar(SitDemandaSitd.class, new String[] {"descricaoSitd", Dao.ORDEM_ASC});
			SitDemandaSitd situacao;
			
			for (Object situacaoObj : list) {
				
				situacao = (SitDemandaSitd) situacaoObj;
				
				if (!situacao.equals(situacaoDemanda) && situacao.getIndPrimeiraSituacaoSitd().equals("S")){
					
					jaTemPrimeiraSituacao = true;
					break;
				}
			}
		}
		
		return jaTemPrimeiraSituacao;
	}
	
}
