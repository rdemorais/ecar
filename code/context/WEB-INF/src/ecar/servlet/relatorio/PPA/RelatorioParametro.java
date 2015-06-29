package ecar.servlet.relatorio.PPA;

import java.util.ArrayList;

import ecar.exception.ECARException;
import ecar.pojo.ItemEstruturaIettPPA;

/**
 * Interface que identifica quais as possibilidades de filtro para o Relatorio
 * @author gabrielsolana
 *
 */
public interface RelatorioParametro {

	
    /**
     *
     */
    public static final Long COD_ESTRUTURA_LINHA_ACAO = Long.valueOf(27);

	/**
	 * Lista itens com os parametros informados
         * @param programa
         * @param orgao
         * @param listaCriteriosCom
         * @param listaCriteriosSem
         * @return Listagem de itens
         * @throws ECARException
	 */
	public ArrayList<ItemEstruturaIettPPA> execute( ArrayList<String> programa, Long orgao, ArrayList<String> listaCriteriosCom, ArrayList<String> listaCriteriosSem )  throws ECARException;
	
        /**
         *
         * @return
         */
        public TipoPesquisaRelatorio getTipo();
	
}
