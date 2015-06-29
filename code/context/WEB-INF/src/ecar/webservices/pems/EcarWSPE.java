/**
 * 
 */
package ecar.webservices.pems;

import java.util.List;

import javax.ws.rs.core.Response;

import ecar.pojo.acompanhamentoEstrategico.Comentario;
import ecar.pojo.acompanhamentoEstrategico.Etiqueta;
import ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico;
import ecar.pojo.acompanhamentoEstrategico.Produto;
import ecar.pojo.acompanhamentoEstrategico.Responsavel;
import ecar.pojo.acompanhamentoEstrategico.Resultado;
import ecar.pojo.acompanhamentoEstrategico.ResultadoStatusContar;
import ecar.webservices.Seguranca;

/**
 * Interface responsável pelo WebService do Ecar para o
 * site Planejamento Estratégico.
 * @author gekson.silva
 *
 */
public interface EcarWSPE {
	
	Seguranca loginWS(String usuario, String senha);
	
	Seguranca logOffWS(String uuid);

	List<ResultadoStatusContar> buscaResultadoStatus(String uuid, Long exercicio);	
	
	List<ObjetivoEstrategico> buscaObjetivoEstrategicoLista(String uuid);
	
	List<Etiqueta> buscaEtiquetaPrioritaria(String uuid);
	
	List<Etiqueta> buscaEtiqueta(String uuid, String nome);
	
	List<ObjetivoEstrategico> buscaObjetivoEstrategicoFiltro(String uuid, List<Integer> codigoObjetivoEstrategico,
			List<String> etiqueta,
			List<String> etiquetaPrioritaria,
			List<Integer> resultadoStatus,
			Long exercicio,
			int painelIndicador);
	
	Resultado buscaResultado(String uuid, Integer codigoResultado, String mes, String ano, Long exercicio);
	
	Produto buscaProduto(String uuid, Integer codigoProduto, Long exercicio);
	
	List<Comentario> buscaComentario(String uuid, Integer codigoResultado);
	
	Comentario gravarComentario(String uuid, Integer codResultado, String texto, Boolean atencaoMinistro, String prazo, Integer codResponsavel);
	
	Response marcarAtencaoMinistro(String uuid, Integer codResultado);
	
	List<Responsavel> buscaResponsavel(String uuid, String nome);
	
	Response excluirComentario(String uuid, Integer codComentario);
	
	List<ObjetivoEstrategico> buscaPainelIndicadores(String uuid, List<Integer> codigoObjetivoEstrategico,
			List<String> etiqueta,
			List<String> etiquetaPrioritaria,
			List<Integer> resultadoStatus,
			Long exercicio,
			int painelIndicador);
}
