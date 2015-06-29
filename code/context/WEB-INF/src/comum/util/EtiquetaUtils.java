package comum.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import br.com.p2d.phonetizer.FonetizacaoBR;

import comum.database.Dao;

import ecar.enumerator.Preposicoes;
import ecar.pojo.Etiqueta;

public class EtiquetaUtils {
	public static boolean verificarPreposicao(String preposicao){
		boolean isPreposicao = false;
		for(Preposicoes preposicoes : Preposicoes.values()){
			if(StringUtils.equals(preposicoes.getPreposicao(), preposicao.toUpperCase())){
				isPreposicao = true;
				break;
			}
		}
		
		return isPreposicao;
	}
	
	public static String fonetizar(String palavra){
		String palavraFonetizada = null;
		if(StringUtils.isNotBlank(palavra)){
			palavraFonetizada = FonetizacaoBR.fonetizar(palavra);
		}
		
		return palavraFonetizada;
	}
	
	public static Map<String, String> fonetizarNomeEtiqueta(String nomeEtiqueta){
		Map<String, String> nomesFonetizados = new HashMap<String, String>();
		if(StringUtils.isNotBlank(nomeEtiqueta)){
			String [] palavras = nomeEtiqueta.split(" ");
			for(int i = 0; i < palavras.length; i++){
				if(StringUtils.isNotBlank(palavras[i]) && !verificarPreposicao(palavras[i]) && !validarCadastroEtiqueta(palavras[i])){
					nomesFonetizados.put(palavras[i], FonetizacaoBR.fonetizar(palavras[i]));
				}
			}
		}
		
		return nomesFonetizados;
	}
	
	public static boolean validarCadastroEtiqueta(String nomeEtiqueta){
		boolean existeRegistro = false;
		Dao<Etiqueta> etiquetaDao = new Dao<Etiqueta>();
		StringBuilder hql = new StringBuilder();
		hql.append("select count(*) from Etiqueta e ");
		hql.append("where upper(e.nomeFonetico) = upper(?) ");
		
		Long qtdRegistros = etiquetaDao.buscarQuantidadeRegistros(hql.toString(), EtiquetaUtils.fonetizar(nomeEtiqueta));
		
		if(qtdRegistros > 0){
			existeRegistro = true;
		}
		
		return existeRegistro;
	}
	
	public static boolean validarEdicaoEtiqueta(Long codigoEtiqueta, String nomeEtiqueta){
		boolean existeRegistro = false;
		Dao<Etiqueta> etiquetaDao = new Dao<Etiqueta>();
		StringBuilder hql = new StringBuilder();
		hql.append("select count(*) from Etiqueta e ");
		hql.append("where e.nome = ? and codigo not in (?)");
		
		Long qtdRegistros = etiquetaDao.buscarQuantidadeRegistros(hql.toString(), nomeEtiqueta, codigoEtiqueta);
		
		if(qtdRegistros > 0){
			existeRegistro = true;
		}
		
		return existeRegistro;
	}
	
}
