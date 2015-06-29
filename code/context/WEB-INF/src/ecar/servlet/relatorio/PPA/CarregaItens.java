package ecar.servlet.relatorio.PPA;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.pojo.ItemEstruturaIettPPA;


/**
 *
 * @author 70744416353
 */
public abstract class CarregaItens {

	/**
	 * Log da classe
	 */
	private Logger logger = Logger.getLogger(this.getClass());	
	
        /**
         *
         */
        protected ArrayList<ItemEstruturaIettPPA> itensLinhaAcao;
        /**
         *
         */
        protected ArrayList<ItemEstruturaIettPPA> itensPrograma;
        /**
         *
         */
        protected ArrayList<ItemEstruturaIettPPA> itensProduto;
	
        /**
         *
         */
        protected HttpServletRequest request;
        /**
         *
         */
        protected ItemEstruturaDao itemDao;
	
        /**
         *
         */
        protected CarregaItens(){}
	
        /**
         *
         * @param paramRequest
         */
        public CarregaItens( HttpServletRequest paramRequest ){
		this.request = paramRequest;
		itemDao = new ItemEstruturaDao(paramRequest);	
	}
	
        /**
         *
         * @throws ECARException
         */
        protected void getItensLinhaAcao() throws ECARException{
		
		try {
			ArrayList<ItemEstruturaIettPPA> retorno = new ArrayList<ItemEstruturaIettPPA>();
			
			List itens = itemDao.getItensByEstruturaPPA( RelatorioParametro.COD_ESTRUTURA_LINHA_ACAO );
			for (Iterator iter = itens.iterator(); iter.hasNext();) {
				ItemEstruturaIettPPA element = (ItemEstruturaIettPPA) iter.next();
				if (  "S".equals( element.getIndAtivoIett() )  ){
					retorno.add(element);
				}
				
			}
			
			itensLinhaAcao = retorno==null?new ArrayList<ItemEstruturaIettPPA>():retorno;
			
		} catch (Exception e) {
			e.printStackTrace(System.out);
			logger.error(e);
			throw new ECARException("Nao foi possivel carregar itens de nivel Linha de acao");
		}		
	}

        /**
         *
         * @param itemLinhaAcao
         * @throws ECARException
         */
        protected void getItensPrograma( Long itemLinhaAcao ) throws ECARException{
		
		try {
			ArrayList<ItemEstruturaIettPPA> itens = new ArrayList<ItemEstruturaIettPPA>();
			ItemEstruturaIettPPA itemLinha =  (ItemEstruturaIettPPA)itemDao.buscar( ItemEstruturaIettPPA.class , itemLinhaAcao );

			Set programas = itemLinha.getItemEstruturaIetts();
			for (Iterator iter = programas.iterator(); iter.hasNext();) {
				ItemEstruturaIettPPA element = (ItemEstruturaIettPPA) iter.next();
				if (  "S".equals( element.getIndAtivoIett() )  ){
					itens.add(element);
				}
				
			}
			itensPrograma = itens==null?new ArrayList<ItemEstruturaIettPPA>():itens;
			
		} catch (Exception e) {
			e.printStackTrace(System.out);
			logger.error(e);
			throw new ECARException("Nao foi possivel carregar itens de nivel Programa");
		}		
	}
	
        /**
         *
         * @param codIettPrograma
         * @throws ECARException
         */
        protected void getItensProduto( Long codIettPrograma ) throws ECARException{
		
		try {
			ArrayList<ItemEstruturaIettPPA> itens = new ArrayList<ItemEstruturaIettPPA>();
			ItemEstruturaIettPPA itemPrograma =  (ItemEstruturaIettPPA)itemDao.buscar( ItemEstruturaIettPPA.class , codIettPrograma );

			Set produtos = itemPrograma.getItemEstruturaIetts();
			for (Iterator iter = produtos.iterator(); iter.hasNext();) {
				ItemEstruturaIettPPA element = (ItemEstruturaIettPPA) iter.next();
				if (  "S".equals( element.getIndAtivoIett() )  ){
					itens.add(element);
				}
				
			}
			itensProduto = itens==null?new ArrayList<ItemEstruturaIettPPA>():itens;
			
			
		} catch (Exception e) {
			e.printStackTrace(System.out);
			logger.error(e);
			throw new ECARException("Nao foi possivel carregar itens de nivel Produto");
		}		
	}	
	
	
}
