package ecar.intercambioDados.exportacao;

import java.util.ArrayList;
import java.util.List;

import comum.util.ConstantesECAR;

import ecar.api.facade.EcarAnexo;
import ecar.api.facade.ItemEstrutura;
import ecar.exception.ECARException;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.intercambioDados.recurso.txt.RegistroTXT;
import ecar.login.SegurancaECAR;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ObjetoEstrutura;

public class ExportaFuncaoAnexo extends ExportaFuncao {

	@Override
	protected List<IRegistro> montarConteudo(
			List<ItemEstruturaIett> listaItensEstruturaExportacao,
			List<AcompReferenciaItemAri> listaArisEstruturaExportacao,
			ConfiguracaoCfg configuracao, List<ObjetoEstrutura> colunas,
			AcompReferenciaAref acompReferenciaAref, SegurancaECAR segurancaECAR)
			throws ECARException {
		
		List<IRegistro> listaRegistros = new ArrayList<IRegistro>();

		for(ItemEstruturaIett itemEst: listaItensEstruturaExportacao){
			ItemEstrutura item = new ItemEstrutura(itemEst);

			StringBuffer headerStr = null;
			IRegistro registro = null;
			
			for(EcarAnexo anexo: item.getAnexos()){
				
				headerStr = new StringBuffer();
				
				//id item estrutura
				headerStr.append(anexo.getItemEstrutura().getId());
				headerStr.append(configuracao.getSeparadorArqTXT());
				
				//id do anexo
				headerStr.append(anexo.getId());
				headerStr.append(configuracao.getSeparadorArqTXT());
				
				//id arel
				headerStr.append(anexo.getRelatorioAcompanhamento() != null ? anexo.getRelatorioAcompanhamento().getCodArel() : "");
				headerStr.append(configuracao.getSeparadorArqTXT());
				
				//nome da categoria
				headerStr.append(anexo.getNomeCategoriaAnexo() != null ? anexo.getNomeCategoriaAnexo() : "");
				headerStr.append(configuracao.getSeparadorArqTXT());
				
				//descrição da categoria
				headerStr.append(anexo.getDescricaoCategoriaAnexo() != null ? anexo.getDescricaoCategoriaAnexo() : "");
				headerStr.append(configuracao.getSeparadorArqTXT());
				
				//tipo da categoria
				headerStr.append((anexo.getTipoCategoria() != null)? anexo.getTipoCategoria() : "");
				headerStr.append(configuracao.getSeparadorArqTXT());
				
				//nome do anexo
				headerStr.append(anexo.getNomeOriginal());
				headerStr.append(configuracao.getSeparadorArqTXT());

				//tipo do anexo	
				headerStr.append((anexo.getTipoAnexo()!=null)? anexo.getTipoAnexo() : "");
				headerStr.append(configuracao.getSeparadorArqTXT());
				
				//nome interno do anexo
				headerStr.append(anexo.getNomeInternoEcar());
				headerStr.append(configuracao.getSeparadorArqTXT());

				//descricao do anexo
				headerStr.append((anexo.getDescricaoAnexo() != null)? anexo.getDescricaoAnexo() : "");
				headerStr.append(configuracao.getSeparadorArqTXT());
				
				//responsável pela inclusão do anexo
				headerStr.append((anexo.getResponsavelInclusao() != null)? anexo.getDescricaoAnexo() : "" );
				headerStr.append(configuracao.getSeparadorArqTXT());

				//tamando do anexo
				headerStr.append((anexo.getTamanhoAnexo() != null) ? anexo.getTamanhoAnexo() : "");
				headerStr.append(configuracao.getSeparadorArqTXT());
				
				//data de inclusão
				headerStr.append((anexo.getDataInclusao() != null) ? anexo.getDataInclusao().getDataFormatadaComBarra() : "");
				headerStr.append(configuracao.getSeparadorArqTXT());
				
				//data da última manutenção
				headerStr.append(anexo.getDataUltimaManutencao() != null ? anexo.getDataUltimaManutencao().getDataFormatadaComBarra() : "");
				headerStr.append(configuracao.getSeparadorArqTXT());

				//usuário da última manutenção
				headerStr.append(anexo.getRealObject().getUsuarioUsuManutencao() != null ? anexo.getRealObject().getUsuarioUsuManutencao().getNomeUsu() : "");

				registro = new RegistroTXT(headerStr.toString());
				listaRegistros.add(registro);
			}
		}
		
		return listaRegistros;
	}

	@Override
	protected IRegistro montarSegundoHeader(EstruturaEtt estruturaEtt,
			List<ItemEstruturaIett> listaItensEstruturaExportacao,
			String labelFuncaoAba, ConfiguracaoCfg configuracao,
			List<ObjetoEstrutura> colunas,
			AcompReferenciaAref acompReferenciaAref) throws ECARException {
	
		StringBuffer headerStr = new StringBuffer(); 
		
		
		headerStr.append(ConstantesECAR.ID_ITEM);
		headerStr.append(configuracao.getSeparadorArqTXT());
		
		headerStr.append(ConstantesECAR.ID_ANEXO);
		headerStr.append(configuracao.getSeparadorArqTXT());

		headerStr.append(ConstantesECAR.ID_AREL);
		headerStr.append(configuracao.getSeparadorArqTXT());
		
		headerStr.append(ConstantesECAR.NOME_CATEGORIA);
		headerStr.append(configuracao.getSeparadorArqTXT());
		
		headerStr.append(ConstantesECAR.DESCRICAO_CATEGORIA);
		headerStr.append(configuracao.getSeparadorArqTXT());
		
		headerStr.append(ConstantesECAR.TIPO_CATEGORIA);
		headerStr.append(configuracao.getSeparadorArqTXT());
				
		headerStr.append(ConstantesECAR.NOME_ORIGINAL_ANEXO);
		headerStr.append(configuracao.getSeparadorArqTXT());

		headerStr.append(ConstantesECAR.TIPO_ANEXO);
		headerStr.append(configuracao.getSeparadorArqTXT());
		
		headerStr.append(ConstantesECAR.NOME_INTERNO_ANEXO);
		headerStr.append(configuracao.getSeparadorArqTXT());
		
		headerStr.append(ConstantesECAR.DESCRICAO_ANEXO);
		headerStr.append(configuracao.getSeparadorArqTXT());
		
		headerStr.append(ConstantesECAR.RESPONSAVEL_INCLUSAO_ANEXO);
		headerStr.append(configuracao.getSeparadorArqTXT());

		headerStr.append(ConstantesECAR.TAMANHO_ANEXO);
		headerStr.append(configuracao.getSeparadorArqTXT());
		
		headerStr.append(ConstantesECAR.DATA_INCLUSAO);
		headerStr.append(configuracao.getSeparadorArqTXT());
		
		headerStr.append(ConstantesECAR.ULTIMA_MANUTENCAO);
		headerStr.append(configuracao.getSeparadorArqTXT());
		
		headerStr.append(ConstantesECAR.USUARIO_ULTIMA_MANUTENCAO);
		
		IRegistro r = new RegistroTXT(headerStr.toString());
		
		return r;
	}

}