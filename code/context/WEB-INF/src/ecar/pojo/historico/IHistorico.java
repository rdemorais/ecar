package ecar.pojo.historico;

import java.util.Date;

import ecar.pojo.UsuarioUsu;


/**
 *
 * @author 70744416353
 */
public interface IHistorico extends Cloneable{
	
    /**
     *
     * @return
     */
    public UsuarioUsu getUsuarioUsuOperacao();
        /**
         *
         * @return
         */
        public Date getDataHistorico();
        /**
         *
         * @return
         */
        public Long getTipoHistorico();
        /**
         *
         * @return
         */
        public Long getIdObjetoSerializado();
        /**
         *
         * @return
         */
        public Long getIdHistorico();
        /**
         *
         * @return
         */
        public String getHistorico();
	
        /**
         *
         * @param usuarioUsuOperacao
         */
        public void setUsuarioUsuOperacao(UsuarioUsu usuarioUsuOperacao);
        /**
         *
         * @param data
         */
        public void setDataHistorico(Date data);
        /**
         *
         * @param tipoHistorico
         */
        public void setTipoHistorico(Long tipoHistorico);
        /**
         *
         * @param idObjetoSerializado
         */
        public void setIdObjetoSerializado(Long idObjetoSerializado);
        /**
         *
         * @param idHistorico
         */
        public void setIdHistorico(Long idHistorico);
        /**
         *
         * @param historico
         */
        public void setHistorico(String historico);
	
        /**
         *
         * @return
         */
        public Object descarregar();
        /**
         *
         * @param objPai
         */
        public void carregar(Object objPai);
	
//	public List listaObjetoSerializadoNoDia(Date data);

}
