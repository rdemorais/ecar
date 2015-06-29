package ecar.dao.permissao.link;

/**
 *
 * @author 70744416353
 */
public enum ErroPermissaoAcessoLinkEnum {

    /**
     *
     */
    PERMISSAO_ITEM(1, "Usu�rio possui permiss�o para acessar o item."),
        /**
         *
         */
        SEM_PERMISSAO_ITEM(2, "Usu�rio n�o possui mais permiss�o para acessar o item."),
        /**
         *
         */
        ITEM_INATIVO(3, "O item n�o est� mais ativo."),
        /**
         *
         */
        ITEM_INEXISTENTE(4, "O item n�o existe mais."),
        /**
         *
         */
        ACOMPANHAMENTO_INEXISTENTE(5, "O acompanhamento n�o existe mais no sistema."),
        /**
         *
         */
        SEM_PERMISSAO_ACOMPANHAMENTO(6, "Usu�rio n�o possui mais permiss�o para acessar a parte de registro deste acompanhamento."),
        /**
         *
         */
        REFERENCIA_INEXISTENTE(7, "A refer�ncia do acompanhamento n�o existe mais no sistema."),
        /**
         *
         */
        TIPO_DE_ACOMPANHAMENTO_INEXISTENTE(8, "O tipo de acompanhamento n�o existe mais no sistema."),
        /**
         *
         */
        ESTRUTURA_INEXISTENTE(9, "A estrutura n�o existe mais no sistema. "),
        /**
         *
         */
        ABA_INEXISTENTE(10, "A funcionalidade n�o est� mais configurada no sistema para esta estrutura."),
        /**
         *
         */
        ABA_SITUACAO_NAO_CONFIGURADA(11, "A funcionalidade n�o est� mais configurada no sistema.");

	
	
	 private int codigo;
	 private String descricao;
	 
	 ErroPermissaoAcessoLinkEnum(int codigo, String descricao) {
		 this.codigo = codigo;
		 this.descricao = descricao;
	}
	 
	 
         /**
          *
          * @param _codigo
          * @return
          * @throws IllegalArgumentException
          */
         public static ErroPermissaoAcessoLinkEnum valueOf(int _codigo) throws IllegalArgumentException {
		 ErroPermissaoAcessoLinkEnum value = null;
		 for (ErroPermissaoAcessoLinkEnum element : ErroPermissaoAcessoLinkEnum.values()) {
			 if (element.getCodigo() == _codigo) {
				 value = element;
				 break;
			 }
		 }
		 
		 if (value == null) {
			 throw new IllegalArgumentException(String.valueOf(_codigo));
		 }
		 
		 return value;
	}


         /**
          *
          * @return
          */
         public int getCodigo() {
		return codigo;
	}


        /**
         *
         * @return
         */
        public String getDescricao() {
		return descricao;
	}
	 
	 
	
	 
	 
}
