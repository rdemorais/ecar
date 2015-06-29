package ecar.webservices.pems.impl;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

import ecar.pojo.acompanhamentoEstrategico.Estrategia;
import ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico;
import ecar.pojo.acompanhamentoEstrategico.Produto;
import ecar.pojo.acompanhamentoEstrategico.Resultado;

@Provider
public class JAXBContextResolver implements ContextResolver<JAXBContext > {

	private JAXBContext context;
    @SuppressWarnings("rawtypes")
	private Class[] types = {ObjetivoEstrategico.class, Resultado.class, Estrategia.class, Produto.class};

    public JAXBContextResolver() throws Exception {        
        this.context = new JSONJAXBContext(JSONConfiguration.mapped().arrays(
        		"objetivoEstrategico","estrategias","resultados","coResponsavel","produtos",
        		"acoes", "indicador", "comentarios", "articulacao", "periodoAcompanhamento").build(),
                types);
    }
    
	@SuppressWarnings("rawtypes")
	public JAXBContext  getContext(Class<?> arg0) {
        for (Class type : types) {
            if (type == arg0) {
                return context;
            }
        }
		return null;
	}

}
