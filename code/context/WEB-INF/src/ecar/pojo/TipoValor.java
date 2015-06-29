package ecar.pojo;

/**
 *
 * @author 70744416353
 */
public class TipoValor {

	private Long codigo;
	private String conteudo; 
	private TipoValorEnum tipo;
	private ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributo;
	private FuncaoFun funcao;
	private PontoCriticoSisAtributoPtcSatb pontoCriticoSisAtributo;
	
        /**
         *
         * @return
         */
        public PontoCriticoSisAtributoPtcSatb getPontoCriticoSisAtributo() {
		return pontoCriticoSisAtributo;
	}
        /**
         *
         * @param pontoCriticoSisAtributo
         */
        public void setPontoCriticoSisAtributo(PontoCriticoSisAtributoPtcSatb pontoCriticoSisAtributo) {
		this.pontoCriticoSisAtributo = pontoCriticoSisAtributo;
	}
        /**
         *
         * @return
         */
        public FuncaoFun getFuncao() {
		return funcao;
	}

        /**
         *
         * @param funcao
         */
        public void setFuncao(FuncaoFun funcao) {
		this.funcao = funcao;
	}
        /**
         *
         * @return
         */
        public ItemEstruturaSisAtributoIettSatb getItemEstruturaSisAtributo() {
		return itemEstruturaSisAtributo;
	}
        /**
         *
         * @param itemEstruturaSisAtributo
         */
        public void setItemEstruturaSisAtributo(ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributo) {
		this.itemEstruturaSisAtributo = itemEstruturaSisAtributo;
	}
        /**
         *
         * @return
         */
        public Long getCodigo() {
		return codigo;
	}
        /**
         *
         * @param codigo
         */
        public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
        /**
         *
         * @return
         */
        public String getConteudo() {
		return conteudo;
	}
        /**
         *
         * @param conteudo
         */
        public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
        /**
         *
         * @return
         */
        public TipoValorEnum getTipo() {
		return tipo;
	}
        /**
         *
         * @param tipo
         */
        public void setTipo(TipoValorEnum tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		boolean ret;
		
		if (obj instanceof TipoValor) {
			TipoValor innerTipoValor = (TipoValor) obj; 
			
			if (this.getCodigo() == innerTipoValor.getCodigo()){
				ret = true;
			} else {
				ret = false;
			}
		} else {
			ret = false;
		}
		
		
		return ret; 
	}
	
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
}
