package ecar.api.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ecar.dao.LocalItemDao;
import ecar.exception.ECARException;
import ecar.pojo.AcompRealFisicoLocalArfl;
import ecar.pojo.LocalItemLit;

/**
 * Wrapper class para o LocalItemLit (Local) 
 * 
 * @author N/A
 *
 */
public class Local implements EcarWrapperInterface<LocalItemLit>{
	
	/** Os grupos geográficos são obtidos em {@link GrupoLocal}. 
	 Essas constantes foram mantidas, mas não estão sendo usadas por que podem variar
	 de acordo com o que se tem no banco de dados (tb_grupo_local_lgp)*/
	public static final int  REGIAO = 1;
	public static final int  PAIS = 11;
	public static final int  ESTADO = 7;
	public static final int  MUNICIPIO = 8;
	public static final int  EXTERIOR = 10;
	public static final int  NAO_DEFINIDO = 1000;
	
	
	private LocalItemLit local;
	private LocalItemDao dao = new LocalItemDao(null);
	private long abrangencia = NAO_DEFINIDO;
	
	public Local(LocalItemLit local){
		this.local = local;
		this.abrangencia = getAbrangencia();
	}

	public Local(long cod_lit) throws ECARException{
		this.local = (LocalItemLit) dao.buscar(LocalItemLit.class, Long.valueOf(cod_lit));
		if(local == null){
			throw new ECARException();
		}
		this.abrangencia = getAbrangencia();
	}
	
	public long getId(){
		return local.getCodLit();
	}

	public String getSigla(){
		return local.getSiglaLit();
	}

	public String getNome(){
		return local.getIdentificacaoLit();
	}

	public String getCodigoIBGE(){
		return local.getCodIbgeLit();
	}

	
	public long getAbrangencia(){
		return local.getCodLgp();
	}
	
	/**
	 * Retorna um string dizendo a abrangencia do local:
	 * país, estado, município, etc
	 * 
	 * 
	 * @return
	 */
	public String getAbrangenciaAsString(){

		
		return GrupoLocal.getInstance().getNomeGrupo((int)getAbrangencia());
//		long cod = local.getCodLgp();
//		
//		switch((int)cod){
//			case MUNICIPIO:
//				 return "Município"; 	
//
//			case ESTADO:
//				 return "Estado";
//
//			case REGIAO:	 
//				 return "Região"; 	
//				 
//			case PAIS:
//				 return "País";
//			
//			case EXTERIOR:
//				 return "Exterior";
//				 
//		    default:
//		    	return "Não Informado";
//		}
		
	}
	

	/**
	 * True se o local for da abrangencia passado como argumento.
	 * Uso: local.hasAbrangencia(Local.ESTADO), retorna verdadeiro se 
	 * o local é um estado.
	 * 
	 * @param abrangencia
	 * @return
	 */
	public boolean hasAbrangencia(int abrangencia){
		return abrangencia == getAbrangencia();
	}
	
	/*
	 * (non-Javadoc)
	 * @see ecar.api.facade.EcarWrapperInterface#getRealObject()
	 */
	public LocalItemLit getRealObject(){
		return local;
	}
	
	/**
	 * Retorna os valores realizados para esse local.
	 * 
	 * @return
	 */
	public List<RealizadoLocal> getRealizados(){
		List<RealizadoLocal> realizados = null;
		//AcompRealFisicoLocalArfl
		Set set = local.getAcompRealFisicoLocalArfls();
		if(set != null && set.size() > 0){
			realizados = new ArrayList<RealizadoLocal>();
			for(Object arfl: set ){
				if(arfl != null){
					realizados.add(new RealizadoLocal((AcompRealFisicoLocalArfl)arfl));
				}
			}
		}
		
		return realizados;
	}
	

	public boolean hasFilhos(){
		return dao.getFilhosById(getRealObject()).size() > 0;
	}
	
	
	/**
	 * 
	 * Retorna a lista de filhos do local.
	 * 
	 * @return
	 */
	public List<Local> getFilhos(){
		List<Local> filhos = null;
		List l = dao.getLocaisFilhos(getRealObject());
		if(l != null && l.size() > 0){
			filhos = new ArrayList<Local>();
			for(Object o: l){
				if(o != null){
					filhos.add(new Local((LocalItemLit) o ));
				}
			}
		}
		return filhos;
	}
	
	
	
	/**
	 * Percorre recursivamente a hierarquia para pegar os locais de uma
	 * determinada abrangência
	 * 
	 * @param root
	 * @param res
	 * @param abrangencia
	 */
	private void getFilhos(Local root, List<Local> res, int abrangencia){
		
		if(this.abrangencia == abrangencia){
			res.add(root);
			return;
		}
		
		if(root.getAbrangencia() == abrangencia && !res.contains(root)){
			res.add(root);
		}else{
			if(root.getFilhos() != null && root.getFilhos().size() > 0){
				for(Local local: root.getFilhos()){
					getFilhos(local, res, abrangencia);//recursão
				}
			}
		}
	}	
	
	/**
	 * Desce na hierarquia para pegar os locais da abrangência
	 * solicitada.
	 * 
	 * @param abrangencia
	 * @return
	 */
	public List<Local> getFilhos(int abrangencia){
		
		List<Local> buffer = new ArrayList<Local>();

		getFilhos(this, buffer, abrangencia);
		
		return buffer;
	}
	
	
	/**
	 * Retorna os pais de um  local.
	 * 
	 * @return
	 */
	public List<Local> getPais(){
		List<Local> pais = null;
		List l = dao.getLocaisPais(getRealObject());
		if(l != null && l.size() > 0){
			pais = new ArrayList<Local>();
			for(Object o: l){
				if(o != null){
					pais.add(new Local((LocalItemLit) o ));
				}
			}
		}
		return pais;
	}
	
	
	public boolean equals(Object o){
		if(o instanceof Local){
			return this.local.getCodLit() == ((Local)o).getId();
		}
		return false;
		
	}
	
	public int hashCode(){
		return (int) getId();
	}
	
	
	public String toString(){
		return this.getId() + " " + this.getNome()+ "(" + getAbrangenciaAsString() +")";
	}
	
	
}
