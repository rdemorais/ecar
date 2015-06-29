package ecar.servlet.componente;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import comum.util.Pagina;
import comum.util.Util;

import ecar.dao.ItemEstrtIndResulDao;
import ecar.dao.ItemEstrtIndResultLocalIettirlDao;
import ecar.dao.ItemEstrutFisicoDao;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrutFisicoIettf;

/**
 *
 * @author 70744416353
 */
public class SalvarIndicadorPorLocalServlet extends HttpServlet{


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
			Long codIettirLong = Pagina.getParamLong(request, "codIettir");
			Enumeration a = request.getParameterNames();
			
			// Criando os DAOs
			ItemEstrtIndResulDao itemEstrtIndResulDao = new ItemEstrtIndResulDao(request);
			ItemEstrtIndResulIettr itemEstrtIndResulOriginal = (ItemEstrtIndResulIettr) itemEstrtIndResulDao.buscar(ItemEstrtIndResulIettr.class, Long.valueOf(Pagina.getParamStr(request, "codIettir")));
/*			List listaQtd = itemEstrtIndResulDao.getListaQuantidadePrevista(request, itemEstrtIndResulOriginal);
			itemEstrtIndResulOriginal.setDataUltManutencao(new Date());
			itemEstrtIndResulOriginal.setUsuarioUsuManutencao(((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
			itemEstrtIndResulDao.alterar(itemEstrtIndResulOriginal, listaQtd);
			*/
			final ItemEstrtIndResultLocalIettirlDao dao = new ItemEstrtIndResultLocalIettirlDao(request);
			final ItemEstrutFisicoDao daoFisico = new ItemEstrutFisicoDao(request);


			a = request.getParameterNames();
			Long ano = Long.valueOf(request.getParameter("ano"));

				if (itemEstrtIndResulOriginal.getItemEstrutFisicoIettfs() != null)
				{
					 Iterator<ItemEstrutFisicoIettf> it = itemEstrtIndResulOriginal.getItemEstrutFisicoIettfs().iterator();
					 while (it.hasNext()){
						 ItemEstrutFisicoIettf itemF = (ItemEstrutFisicoIettf) it.next();
						 
						 
						 if ((itemF.getAnoIettf().equals(ano.intValue()) )&&(itemF.getItemEstrtIndResulLocalIettirls() != null)){
								dao.deleteBycodIettf(itemF.getCodIettf());
						 }
					 }
				}				
			
/*			ItemEstrutFisicoIettf itemFisico = daoFisico.getEstruturaFisicaPorMesAno(25l, 6l, 2010l);
			
			Iterator it = itemFisico.getItemEstrtIndResulLocalIettirls().iterator();
			
			ItemEstrtIndResulLocalIettirl itemLocal = (ItemEstrtIndResulLocalIettirl) it.next();
*/
			daoFisico.deleteItemEstrutFisicoDeItem(codIettirLong, ano);
			
			
			
			while(a.hasMoreElements()){
				String elemento = (String)a.nextElement();
				

				if ( elemento.startsWith("cmp")){
					
					StringTokenizer token = new StringTokenizer( elemento, "|", false );
					
					int cont =0;
					String[] parts = new String[3];
					while(token.hasMoreElements()){
						parts[cont] = (String)token.nextElement();
						cont++;
					}
					
					if ( parts.length==3 && (!parts[2].equalsIgnoreCase("total")) ){
						String local = parts[1];
						String mes = parts[2];
						String vlr = request.getParameter(elemento);
						
						if ( vlr!=null & (!"".equalsIgnoreCase(vlr))){
							Long localLong = Long.valueOf(local);
							Long mesLong = Long.valueOf(mes);
							
							
							vlr = Util.formataNumero(vlr);

							ItemEstrutFisicoIettf itemFisico = daoFisico.getEstruturaFisicaPorMesAno(codIettirLong, mesLong, ano);
							
						//	System.out.println("Valor: " + vlr);
							Double vlrDouble = Double.parseDouble(vlr);

							dao.salvarByLocal( codIettirLong, mesLong, ano, localLong, vlrDouble, itemFisico );							
						}
					}
				}
			}
				
			conteudo.append("true");
			responseToHTML(response, conteudo.toString() );
			
		} catch(Exception e){
			logger.error(e);
			e.printStackTrace();	
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
