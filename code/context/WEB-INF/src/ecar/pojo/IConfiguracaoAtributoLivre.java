package ecar.pojo;

import java.util.Collection;

public interface IConfiguracaoAtributoLivre {

	
	public Collection<FuncaoSisAtributo> getListaAtributosLivres();

	public ItemEstruturaIett itemProprietario();
	
	public Long getCodigo();
}
