package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;

import comum.database.Dao;

import ecar.pojo.HistoricoIettupH;
import ecar.pojo.ItemEstrUplCategIettuc;

/**
 *
 * @author 70744416353
 */
public class HistoricoItemEstrutUploadDao extends Dao {

    /**
     *
     * @param request
     */
    public HistoricoItemEstrutUploadDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	List<HistoricoIettupH> getHistoricoPorCategoriaUpload(List<ItemEstrUplCategIettuc> categoriasUpload){
		
		List historicosUpload = null;
		
		StringBuilder query = new StringBuilder("select historicoUpload from HistoricoIettupH as historicoUpload")
		.append(" where historicoUpload.itemEstrUplCategIettuc.codIettuc in (:listaCodIettuc)");
		

		Query queryItens = this.getSession().createQuery(query.toString());
		
		List listaCodigosCategoriaUpload = new ArrayList();
		
		Iterator<ItemEstrUplCategIettuc> itCategoriasUpload = categoriasUpload.iterator();
		
		while (itCategoriasUpload.hasNext()){
			listaCodigosCategoriaUpload.add(itCategoriasUpload.next().getCodIettuc());
		}
		
		queryItens.setParameterList("listaCodIettuc", listaCodigosCategoriaUpload);
		
		historicosUpload = queryItens.list();
		
		return historicosUpload;
	}
}
