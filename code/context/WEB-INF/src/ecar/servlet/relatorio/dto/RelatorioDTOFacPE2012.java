package ecar.servlet.relatorio.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.UsuarioUsu;
import ecar.servlet.relatorio.dto.marcas.RedePlanoAcaoDTO;
import ecar.servlet.relatorio.dto.util.Util;

/**
 * 
 * @author Rafael Freitas de Morais
 * 11/04/2012
 */
public class RelatorioDTOFacPE2012 {
	
	private ItemEstruturaDao itemEstruturaDao;
	
	public RelatorioDTOFacPE2012(HttpServletRequest request, HttpServletResponse response) {
		itemEstruturaDao = new ItemEstruturaDao(request);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<RedePlanoAcaoDTO> dtoRedePlanoAcao(Set gruposAcesso, UsuarioUsu usuario) throws ECARException {
		List<RedePlanoAcaoDTO> planos = new ArrayList<RedePlanoAcaoDTO>();
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yy");
		List<ItemEstruturaIett> ietts = itemEstruturaDao.getItensByEstrutura(gruposAcesso, usuario, 5L);
		RedePlanoAcaoDTO dto;
		ItemEstruturaIett acao = null;
		ItemEstruturaIett eixo = null;
		for (ItemEstruturaIett iett : ietts) {
			List<ItemEstruturaIett> ascendentes = itemEstruturaDao.getAscendentes(iett);
			dto = new RedePlanoAcaoDTO();
			eixo = ascendentes.get(1);
			acao = ascendentes.get(2);
			dto.setRede(ascendentes.get(0).getNomeIett());
			dto.setObjetivo(ascendentes.get(0).getObjetivoGeralIett());
			String sigla = "";
			if(eixo.getSiglaIett() != null || eixo.getSiglaIett() != "") {
				sigla = eixo.getSiglaIett() + " - ";
			}
			dto.setEixo(sigla + eixo.getNomeIett());
			
			sigla = "";
			if(acao.getSiglaIett() != null || acao.getSiglaIett() != "") {
				sigla = acao.getSiglaIett() + " - ";
			}
			dto.setAcao(sigla + acao.getNomeIett());
			dto.setRespAcao(Util.responsavel(acao).getNomeUsu());
			if(acao.getDataInicioIett() != null) {
				dto.setInicioAcao(sd.format(acao.getDataInicioIett()));
			}
			if(acao.getDataTerminoIett() != null) {
				dto.setTerminoAcao(sd.format(acao.getDataTerminoIett()));
			}
			sigla = "";
			if(iett.getSiglaIett() != null || iett.getSiglaIett() != "") {
				sigla = iett.getSiglaIett() + " - ";
			}
			dto.setAtividade(sigla + iett.getNomeIett());
			dto.setRespAtividade(Util.responsavel(iett).getNomeUsu());
			if(iett.getDataInicioIett() != null) {
				dto.setInicioAtividade(sd.format(iett.getDataInicioIett()));
			}
			if(iett.getDataTerminoIett() != null) {
				dto.setTerminoAtividade(sd.format(iett.getDataTerminoIett()));
			}
			if(iett.getDataInicioIett() != null && iett.getDataTerminoIett() != null) {
				Util.formataGantt(dto, iett);
			}
			planos.add(dto);
		}
		Util.ordenarPlanoAcao(planos);
		return planos;
	}
	
	
}
