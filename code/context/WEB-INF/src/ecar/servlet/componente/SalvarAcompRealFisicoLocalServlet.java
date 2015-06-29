package ecar.servlet.componente;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import comum.util.Pagina;
import comum.util.Util;

import ecar.api.facade.ItemEstrutura;
import ecar.api.facade.Local;
import ecar.dao.AcompRealFisicoDao;
import ecar.dao.AcompRealFisicoLocalDao;
import ecar.dao.SituacaoDao;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompRealFisicoLocalArfl;
import ecar.pojo.ItemEstrutLocalIettl;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.LocalItemLit;
import ecar.pojo.SituacaoSit;

/**
 *
 * @author 70744416353
 */
public class SalvarAcompRealFisicoLocalServlet extends HttpServlet{


	private static final long serialVersionUID = 8006183415879658218L;

	private Logger logger = Logger.getLogger(this.getClass());
	
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("request :: GET:: Classe:: " + this.getClass().getName() );
		StringBuilder conteudo = new StringBuilder();
		conteudo.append("false");
		logger.error("Acesso nao autorizado:: acesso GET ao servico de salvar indicador por local");
		responseToHTML( response, conteudo.toString());
	}
	
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("request :: POST:: Classe:: " + this.getClass().getName() );
		execute(request,response);
				
	}
	
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StringBuilder conteudo = new StringBuilder();
		
		try {
			// Obtendo os códigos do arf e do grupo usado
			Long codArfLong = Pagina.getParamLong(request, "codARF");
			
			// Obtendo o arf
			AcompRealFisicoDao arfDAO = new AcompRealFisicoDao(request);
			AcompRealFisicoArf arf = arfDAO.buscar(codArfLong);
			
			ItemEstruturaIett item = arf.getItemEstruturaIett();
			ItemEstrutura itemWrapper = new ItemEstrutura(item);			
			
        	List<ItemEstrutLocalIettl> listaLocais = new ArrayList<ItemEstrutLocalIettl>();
        	List<Local> locais = null;
        	
        	if(arf.getItemEstrtIndResulIettr().getNivelAbrangencia() != null){
        		locais = itemWrapper.getLocais(arf.getItemEstrtIndResulIettr().getNivelAbrangencia().intValue());
        		
        		if(locais != null && locais.size() > 0){
        			for(Local local: locais){
        				ItemEstrutLocalIettl itemLocal = new ItemEstrutLocalIettl();
        				itemLocal.setLocalItemLit(local.getRealObject());
        				listaLocais.add(itemLocal);
        			}
        		}			
        		
        	}			
        	
        	String situacao = request.getParameter("situacaoLocal");
			if (!"".equals(situacao)) {
				arf.setSituacaoSit( (SituacaoSit) new SituacaoDao(request).buscar(SituacaoSit.class, Long.valueOf(situacao)));				
			} else {
				arf.setSituacaoSit(null);
			}        	
        	
			
        	arfDAO.alterar(arf);
        	
        	//deleta o arf e seus locais
			AcompRealFisicoLocalDao arflDAO = new AcompRealFisicoLocalDao(request);
			arflDAO.excluirLocaleARF(arf);

			// Gerando uma lista de arfl a partir da lista de locais, do arf e dos 
			// valores digitados pelo usuário.
			List listaArfl = new ArrayList();			

			for (Iterator iterator = listaLocais.iterator(); iterator.hasNext();) {
				
			    ItemEstrutLocalIettl itemLocal = (ItemEstrutLocalIettl) iterator.next();
			    LocalItemLit local = itemLocal.getLocalItemLit();

				// Obtendo a quantidade digitada pelo usuário
				String nomeCampo = "cmp|" + local.getCodLit().toString();
				String vlr = request.getParameter(nomeCampo);

				// Convertendo o valor para Double
				if ( vlr!=null & (!"".equalsIgnoreCase(vlr))){
					StringBuilder strValor = new StringBuilder(vlr);
					char charValor [] = new char [strValor.length()]; 
					int j = 0;
					for (int i = 0; i < strValor.length(); i++) {
						char posicao = strValor.charAt(i);
						 
						if ( posicao == ','){
							//strValor.replace(i, (i+1), ".");
							charValor[j] = '.';
							j++;
						}
						else{
							if (posicao != '.'){
								charValor[j] = posicao;
								j++;
							}
						}
					}

					vlr = Util.formataNumero(vlr);
					Double vlrDouble = Double.parseDouble(vlr);
					
					AcompRealFisicoLocalArfl arfl = new AcompRealFisicoLocalArfl();

					arfl.setLocalItemLit(local);
					
					// Alterando o valor do arfl e incluindo ele na lista
					arfl.setQuantidadeArfl(vlrDouble);
					listaArfl.add(arfl);
				}
			}

			// Salvando a lista de arfl
			arflDAO.salvar(listaArfl, arf);
			
			conteudo.append("true");
			responseToHTML(response, conteudo.toString() );
		} catch(Exception e){
			logger.error(e);
			StringBuilder responseHTML = new StringBuilder();
			responseHTML.append("false");
			responseToHTML(response, responseHTML.toString());
		}
	}
	
	private void responseToHTML ( HttpServletResponse response, String conteudo ) throws IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.append(conteudo);
		writer.flush();
		writer.close();
	}
	
	
}
