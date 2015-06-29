package ecar.exception;

import ecar.dao.permissao.link.ErroPermissaoAcessoLinkEnum;


/**
 * @author N/C
 * @since N/C
 * @version N/C
 */
public class PermissaoAcessoLinkException extends ECARException {
	
	private static final long serialVersionUID = 8965552994271665523L;
	
	private ErroPermissaoAcessoLinkEnum permissao;
	
        /**
         *
         * @param permissao
         */
        public PermissaoAcessoLinkException(ErroPermissaoAcessoLinkEnum permissao) {
		this.permissao = permissao;
	}
	
        /**
         *
         * @param causa
         */
        public PermissaoAcessoLinkException(Throwable causa) {
		super(causa);
	}
	
        /**
         *
         * @param messageKeyLoc
         * @param causa
         */
        public PermissaoAcessoLinkException(String messageKeyLoc, Throwable causa) {
		super(messageKeyLoc, causa);
	}

	@Override
	public String getMessage() {
		return permissao.getDescricao();
	}
	
	

}
