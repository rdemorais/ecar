package ecar.webservices.restful.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/testeRest")
public class TesteResource {

//	@GET 
//	@Produces("text/plain") 
//	public String showHelloWorld() { 
//		return "Olá mundo!"; 
//	}
	
//	@GET	
//	@Produces("text/xml")
//	public List<Cor> mostraCor() {				
//		CorDao corDao = new CorDao(null);	
//		try {
//			return corDao.getOrdemCores();
//		} catch (ECARException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	@GET
	@Produces({"application/xml", "application/json"})	
	@Path("etiqueta")
	public Etiqueta getEtiqueta(@MatrixParam("id") List<Integer> t,
			@MatrixParam("et") List<String> e) {
		Etiqueta etiqueta = new Etiqueta();
		etiqueta.setAtivo("S");
		etiqueta.setId("1");		
		StringBuffer nome = new StringBuffer();
		nome.append("Teste GET 1 - " );
		
		if(t.size() > 0) {
			nome.append("ID: ");
			for (int i = 0; i < t.size(); i++) {				
				nome.append(t.get(i) + " ; ");
			}
		}
		
		if(e.size() > 0) {
			nome.append("ET: ");
			for (int i = 0; i < e.size(); i++) {				
				nome.append(e.get(i) + " ; ");
			}
		}
		
		etiqueta.setNome(nome.toString());
		etiqueta.setPrioridade("0");
		return etiqueta;
	}
	
	@GET
	@Produces("application/json")	
	@Path("teste")
	public Etiqueta getEtiqueta2(@MatrixParam("id") List<Integer> t,
			@MatrixParam("et") List<String> e) {
		Etiqueta etiqueta = new Etiqueta();
		etiqueta.setAtivo("S");
		etiqueta.setId("1");		
		StringBuffer nome = new StringBuffer();
		nome.append("Teste GET 2 - " );
		
		if(t.size() > 0) {
			nome.append("ID: ");
			for (int i = 0; i < t.size(); i++) {				
				nome.append(t.get(i) + " ; ");
			}
		}
		
		if(e.size() > 0) {
			nome.append("ET: ");
			for (int i = 0; i < e.size(); i++) {				
				nome.append(e.get(i) + " ; ");
			}
		}
		
		etiqueta.setNome(nome.toString());
		etiqueta.setPrioridade("0");
		return etiqueta;
	}
	
//	@PUT
//	@Produces("application/json")
//	@Path("/rest/testeRest/")
//	public Etiqueta getEtiquetaPut(String t) {
//		Etiqueta etiqueta = new Etiqueta();
//		etiqueta.setAtivo("S");
//		etiqueta.setId("1");
//		etiqueta.setNome("Teste PUT");
//		if(t.equals("S")) {
//			etiqueta.setNome("Teste PUT  - " + t);
//		}
//		
//		etiqueta.setPrioridade("0");
//		return etiqueta;
//	}
	
		
}
