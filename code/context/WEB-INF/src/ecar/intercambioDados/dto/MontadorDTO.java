package ecar.intercambioDados.dto;

import java.util.List;

import ecar.exception.ECARException;
import ecar.intercambioDados.importacao.comunicacao.Configuracao;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.UsuarioUsu;

public abstract class MontadorDTO {

	protected Configuracao config; 
	
	public abstract List<IBusinessObjectDTO> montaDTO(List<IRegistro> registro);

	public abstract ItemEstruturaIett montaItemEstruturaIett(ItemEstruturaIett itemEstruturaIett, ItemEstruturaTXTDTO itemEstruturaDTO, UsuarioUsu usuarioLogado) throws ECARException;

}