package ecar.historico;

import ecar.exception.ECARException;
/**
 * Interface a ser implementada por classes do tipo Histórico.
 * @author carlos
 * @since 19/07/2007
 */
public interface IHistoricoAntigo {

    /**
     *
     * @param acao
     * @throws ECARException
     */
    public void gerarMaster(Integer acao) throws ECARException;
        /**
         *
         * @throws ECARException
         */
        public void gerarHistorico() throws ECARException;

}
