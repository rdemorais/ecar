package ecar.servico;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import comum.database.Dao;
import comum.util.EtiquetaUtils;

import ecar.dao.EtiquetaDao;
import ecar.exception.ECARException;
import ecar.exception.EtiquetaException;
import ecar.pojo.Etiqueta;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.RelacionamentoIettEtiqueta;

/**
 * Classe controladora dos nogócios relacionados às etiquetas dos itens 
 * @since 22/03/2012
 * */
public class EtiquetaService {

	private static final long serialVersionUID = 1L;
	private EtiquetaDao etiquetaDao = new EtiquetaDao();
	
	 
	
	public void inserirEtiqueta (Etiqueta etiqueta) throws Exception{
		etiquetaDao.incluirEtiqueta(etiqueta);
		
	}
	
	
	public void apagarEtiqueta(Long idetiqueta) throws Exception{
		
		
	}
	
	/**
	 * Cria um novo relacionamento REL_ITEMESTRUTIETT_ETIQUETA, já ativo.
	 * */
	public void criarVinculoEtiquetaItem(Etiqueta etiqueta, ItemEstruturaIett item) throws Exception{
		RelacionamentoIettEtiqueta rel = new RelacionamentoIettEtiqueta();
		rel.setEtiqueta(etiqueta);
		rel.setIndAtivo("S");
		rel.setItemEstruturaIett(item);
		
		etiquetaDao.incluirRelacionamentoEtiquetaItem(rel);
		etiquetaDao.atualizaEtiquetaRecursiva(rel.getItemEstruturaIett().getCodIett(),rel.getEtiqueta().getCodigo());
		
	}

	
	/**
	 * Pesquisa coleção de etiquetas
	 * @param etiqueta Nome ou descrição do meta-dado. 
	 * */
	public Set<Etiqueta> pesquisaColecaoEtiquetas(String etiqueta) throws Exception{
		Set<Etiqueta> rs = new HashSet<Etiqueta>();
		rs = etiquetaDao.pesquisarListaEtiquetas(etiqueta);
		return rs;
	}
	
	public List<Etiqueta> listarEtiquetas(){
		List<Etiqueta> listaEtiqueta = new ArrayList<Etiqueta>();
		Dao<Etiqueta> etiquetaDao = new Dao<Etiqueta>();
		StringBuilder hql = new StringBuilder();
		hql.append("select e from Etiqueta e");
		listaEtiqueta = etiquetaDao.listarPorHQL(hql.toString());
		
		return listaEtiqueta;
	}
	
	public void editarEtiqueta(Long codigoEtiqueta, String nomeEtiqueta, String prioritario, String ativo) throws Exception{
		if(!EtiquetaUtils.validarEdicaoEtiqueta(codigoEtiqueta, nomeEtiqueta)){
			Dao<Etiqueta> etiquetaDao = new Dao<Etiqueta>();
			Etiqueta etiquetaEdicao = preencherEtiquetaEdicao(codigoEtiqueta, nomeEtiqueta, prioritario, ativo);
			etiquetaDao.alterar(etiquetaEdicao);
		}else{
			throw new EtiquetaException();
		}
	}
	
	private Etiqueta preencherEtiquetaEdicao(Long codigoEtiqueta, String nomeEtiqueta, String prioritario, String ativo){
		Etiqueta etiqueta = new Etiqueta();
		etiqueta.setCodigo(codigoEtiqueta);
		etiqueta.setNome(nomeEtiqueta);
		etiqueta.setNomeFonetico(EtiquetaUtils.fonetizar(nomeEtiqueta));
		etiqueta.setIndPrioritario(StringUtils.isNotBlank(prioritario) ? "S" : "N");
		etiqueta.setIndAtivo(StringUtils.isNotBlank(ativo) ? "S" : "N");
		
		return etiqueta;
	}
	
	public void gravarEtiquetas(String nomeEtiqueta, String prioritario, String ativo) throws Exception{
		List<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
		Etiqueta etiquetaPrincipal = preencherEtiquetaPrincipal(nomeEtiqueta, prioritario, ativo);
		etiquetas.add(etiquetaPrincipal);
		if(nomeEtiqueta.split(" ").length > 1){
			Map<String, String> nomesEtiquetas = EtiquetaUtils.fonetizarNomeEtiqueta(nomeEtiqueta);
			for(String chave : nomesEtiquetas.keySet()){
				Etiqueta etiquetaSecundaria = new Etiqueta();
				etiquetaSecundaria.setNome(chave);
				etiquetaSecundaria.setNomeFonetico(nomesEtiquetas.get(chave));
				etiquetaSecundaria.setIndAtivo("S");
				etiquetaSecundaria.setIndPrioritario("N");
				etiquetas.add(etiquetaSecundaria);
			}
		}
		
		inserirEtiquetas(etiquetas);
		
		
	}
	
	public List<Etiqueta> gravarEtiquetasComRetorno(String nomeEtiqueta, String prioritario, String ativo) throws Exception{
		List<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
		Etiqueta etiquetaPrincipal = preencherEtiquetaPrincipal(nomeEtiqueta, prioritario, ativo);
		etiquetas.add(etiquetaPrincipal);
		Map<String, String> nomesEtiquetas = EtiquetaUtils.fonetizarNomeEtiqueta(nomeEtiqueta);
		for(String chave : nomesEtiquetas.keySet()){
			Etiqueta etiquetaSecundaria = new Etiqueta();
			etiquetaSecundaria.setNome(chave);
			etiquetaSecundaria.setNomeFonetico(nomesEtiquetas.get(chave));
			etiquetaSecundaria.setIndAtivo(StringUtils.isNotBlank(ativo) ? "S" : "N");
			etiquetaSecundaria.setIndPrioritario(StringUtils.isNotBlank(prioritario) ? "S" : "N");
			etiquetas.add(etiquetaSecundaria);
		}
		
		inserirEtiquetas(etiquetas);
		
		return etiquetas;
	}
	
	
	public RelacionamentoIettEtiqueta verificaRelacionamentoEtiquetaIettExistentes(String nomeEtiqueta, ItemEstruturaIett iett){
		RelacionamentoIettEtiqueta retorno = null;
		try {
			retorno = etiquetaDao.pesquisarRelacionamentoIettEtiqueta(nomeEtiqueta, iett.getCodigo());
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return retorno;
		
	}
	
	public Etiqueta preencherEtiquetaPrincipal(String nomeEtiqueta, String prioritario, String ativo) throws EtiquetaException{
		Etiqueta etiqueta = null;
		if(!EtiquetaUtils.validarCadastroEtiqueta(nomeEtiqueta)){
			etiqueta = new Etiqueta();
			etiqueta.setNome(nomeEtiqueta);
			etiqueta.setNomeFonetico(EtiquetaUtils.fonetizar(nomeEtiqueta));
			etiqueta.setIndAtivo("S");
			etiqueta.setIndPrioritario(StringUtils.isNotBlank(prioritario) ? "S" : "N");
		}else{
			throw new EtiquetaException("A etiqueta " + nomeEtiqueta + " j� est� cadastrada.");
		}
		
		return etiqueta;
	}
	
	private void inserirEtiquetas(List<Etiqueta> etiquetas) throws Exception{
		for(Etiqueta etiqueta : etiquetas){
			inserirEtiqueta(etiqueta);
		}
	}
	
	public Etiqueta recuperarEtiquetaPorId(Long id) throws ECARException{
		Dao<Etiqueta> etiquetaDao = new Dao<Etiqueta>();
		Etiqueta etiqueta = new Etiqueta();
		etiqueta = (Etiqueta) etiquetaDao.buscar(Etiqueta.class, id);
		
		return etiqueta;
	}
	
	public List<RelacionamentoIettEtiqueta> listarRelacionamentosIettEtiquetaById(Long id){
		
		EtiquetaDao etiquetaDao = new EtiquetaDao();
		List<RelacionamentoIettEtiqueta> result = new ArrayList<RelacionamentoIettEtiqueta>();
		try {
			result = (List<RelacionamentoIettEtiqueta>) etiquetaDao.pesquisarListaRelacionamentosIettEtiqueta(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Pesquisa no banco relacionamentos em que suas etiquetas tem nome parecido com o par�metro <code>nomeEtiqueta<code/> 
	 * @param id C�digo do Item de Estrutura.
	 * @param nomeEtiqueta Campo 'nome' da etiqueta associada ao relacionamento.
	 * @return Lista de relacionamentos da tabela REL_ITEMESTRUTIETT_ETIQUETA de acordo com o nome da etiqueta e o <br/>
	 * c�digo do item de estrutura associados.
	 * */
	public List<RelacionamentoIettEtiqueta> listarRelacionamentosIettEtiqueta(Long id, String nomeEtiqueta){
		
		EtiquetaDao etiquetaDao = new EtiquetaDao();
		List<RelacionamentoIettEtiqueta> result = new ArrayList<RelacionamentoIettEtiqueta>();
		try {
			result = (List<RelacionamentoIettEtiqueta>) etiquetaDao.pesquisarListaRelacionamentosIettEtiqueta(id, nomeEtiqueta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RelacionamentoIettEtiqueta recuperarRelacionamentoEtiquetaIettPorId(Long id) throws Exception{
		
		EtiquetaDao etiquetaDao = new EtiquetaDao();
		RelacionamentoIettEtiqueta result = new RelacionamentoIettEtiqueta();
		try {
			result = (RelacionamentoIettEtiqueta) etiquetaDao.pesquisarRelacionamentoIettEtiqueta(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
	
	public RelacionamentoIettEtiqueta recuperarRelacionamentoEtiqueta(Long idIett, Long idEtiqueta){
		
		EtiquetaDao etiquetaDao = new EtiquetaDao();
		RelacionamentoIettEtiqueta result = null;
		try {
			result = (RelacionamentoIettEtiqueta) etiquetaDao.pesquisarRelacionamentoIettEtiqueta(idEtiqueta, idIett);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return result;
	}
	
	public RelacionamentoIettEtiqueta atualizarRelacionamentoEtiquetaIettPorId(RelacionamentoIettEtiqueta rel){
		
		EtiquetaDao etiquetaDao = new EtiquetaDao();
		RelacionamentoIettEtiqueta result = new RelacionamentoIettEtiqueta();
		try {
			etiquetaDao.atualizarRelacionamentoIettEtiqueta(rel);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	

	public List<Etiqueta> buscarEtiquetasPorNome(String nomeEtiqueta){
		List<Etiqueta> listaEtiquetas = new ArrayList<Etiqueta>();
		Dao<Etiqueta> etiquetaDao = new Dao<Etiqueta>();
		StringBuilder hql = new StringBuilder();
		hql.append("select e from Etiqueta e where ");
		hql.append("e.nome like upper(?) and e.indAtivo = ? ");
		
		listaEtiquetas = etiquetaDao.listarPorHQL(hql.toString(), nomeEtiqueta+"%", "S");
		
		return listaEtiquetas;
	}
	
//	public File criarArquivoEtiquetas(String nomeEtiqueta){
//		File file = new File("etiquetas", ".xml");
//		
//		return file;
//	}
	
	public void inativarEtiquetaPorCodigo(Long codigoEtiqueta){
		try {
			EtiquetaDao etiquetaDao = new EtiquetaDao();
			Etiqueta etiqueta = (Etiqueta) etiquetaDao.buscar(Etiqueta.class, codigoEtiqueta);
			etiqueta.setIndAtivo("N");
			etiquetaDao.alterar(etiqueta);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Etiqueta buscarEtiquetaPorNome(String nomeEtiqueta){
		Etiqueta etiqueta = new Etiqueta();
		Dao<Etiqueta> etiquetaDao = new Dao<Etiqueta>();
		StringBuilder hql = new StringBuilder();
		hql.append("select e from Etiqueta e where ");
		hql.append("e.nome like upper(?)");
		
		etiqueta = etiquetaDao.buscarPorHQL(hql.toString(), nomeEtiqueta.toUpperCase());
		
		return etiqueta;
	}
	
	public void excluirEtiquetaPorNome(String nomeEtiqueta) throws ECARException{
		StringBuilder hql = new StringBuilder();
		hql.append("select e from Etiqueta e where ");
		hql.append("e.nome = ? ");
		Dao<Etiqueta> etiquetaDao = new Dao<Etiqueta>();
		Etiqueta etiqueta = etiquetaDao.buscarPorHQL(hql.toString(), nomeEtiqueta.toUpperCase());
		if(etiqueta != null){
			etiqueta.setIndAtivo("N");
			etiquetaDao.alterar(etiqueta);
		}
	}
	
	public void substituirEtiquetas(Long codigoEtiquetaSubstituta, Long codigoEtiquetaSubstituida){
		Dao<RelacionamentoIettEtiqueta> relacionamentoDao = new Dao<RelacionamentoIettEtiqueta>();
		StringBuilder hql = new StringBuilder();
		hql.append("select r from RelacionamentoIettEtiqueta r where ");
		hql.append("r.etiqueta.codigo = ? ");
		List<RelacionamentoIettEtiqueta> relacionamentosIett =  relacionamentoDao.listarPorHQL(hql.toString(), codigoEtiquetaSubstituida);
		gravarEtiquetaSubstituta(relacionamentosIett, codigoEtiquetaSubstituta);
	}
	
	public void gravarEtiquetaSubstituta(List<RelacionamentoIettEtiqueta> relacionamentosIett, Long codigoEtiquetaSubstituta){
		try {
			Dao<RelacionamentoIettEtiqueta> relacionamentoDao = new Dao<RelacionamentoIettEtiqueta>();
			for(RelacionamentoIettEtiqueta relacionamento : relacionamentosIett){
				relacionamento.setEtiqueta(recuperarEtiquetaPorId(codigoEtiquetaSubstituta));
				relacionamentoDao.salvar(relacionamento);
			}
		} catch (ECARException e) {
			e.printStackTrace();
		}
	}
	
	public void apagarRelacionamento(RelacionamentoIettEtiqueta rel) {
		try {
			etiquetaDao.excluir(rel);
		} catch (ECARException e) {
			e.printStackTrace();
		}
	}
	
	public void apagarRelacionamento(List<RelacionamentoIettEtiqueta> rel) {
		try {
			etiquetaDao.excluir(rel);
		} catch (ECARException e) {
			e.printStackTrace();
		}
	}	

}
