/*
 * Criado em 13/04/2005
 *
 */
package ecar.servlet.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import comum.util.FileUpload;
import comum.util.Util;

import ecar.dao.ConfiguracaoDao;
import ecar.dao.EmpresaDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EmpresaEmp;
import ecar.pojo.UsuarioUsu;
import ecar.util.ConfiguracaoResponseBean;
import ecar.util.Dominios;

/**
 * @author felipev
 *
 */ 
public class DownloadFile extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5548532554690785199L;
	private Logger logger = Logger.getLogger(this.getClass());

	private static ConfiguracaoCfg configuracao = null;
	private static EmpresaDao empDao = null;
	private static EmpresaEmp emp = null;
	
	/**
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public DownloadFile(){
				
	}
	
	/**
	 * Controla download de arquivos de acordo com permissao de acesso publico.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param request
         * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
    @SuppressWarnings("unchecked")
	@Override
	public final void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String strDownloadDeslogado = request.getParameter("acessoPublico");
		if (strDownloadDeslogado != null) {
			request.getSession().setAttribute("downloadDeslogado",
					new Boolean(true));
		}
		
		SegurancaECAR seguranca = null;
		PrintWriter out = null;
		String file = request.getParameter("RemoteFile");
		List<EmpresaEmp> empresas = null;
		String stringHtml1 = null;
		String stringHtml2 = null;
		UsuarioUsu usuario = null;
		
		stringHtml1 =  "<HTML><HEAD><TITLE>ecar</TITLE></HEAD>" +
        "<BODY>\n" +
        "<H1>Download n&atilde;o permitido: Usu&aacute;rio n&atilde;o autenticado</H1>\n" +
        "</BODY></HTML>";
		
		stringHtml2 =   "<HTML><HEAD><TITLE>ecar</TITLE></HEAD>" +
        "<BODY>\n" +
        "<H1>Download n&atilde;o permitido</H1>\n" +
        "</BODY></HTML>";
        
		
		// #############
		// #############
		// #############

		//#################################################
		//# Verifica�ao 1-  Verifica se usu�rio est� logado ou Verifica se a imagem acessado n�o � o logo (caso especial de acessar p�gina inicial)
		//# Verifica�ao 2 - Remove os ../ recursivamente
		//# Verifica�ao 3 - Verifica o caminho se inicia com o caminho inicial de upload
		//#################################################
		// se a variavel configura��o nao foi inicializada ainda
		// se a variavel empresa nao foi inicializada ainda
		try {
			
			if (configuracao==null) {
					configuracao = new ConfiguracaoDao(request).getConfiguracao();
			}
			
			empDao = new EmpresaDao(request);
			if(empDao.getSession().isOpen()) {
				empresas = empDao.listar(ecar.pojo.EmpresaEmp.class, null);
				if (empresas!=null && empresas.size()>0)
					emp = empresas.get(0);
			}
			
		} catch (ECARException e) {
			e.printStackTrace();
			this.logger.error(e);
			logger.equals(e);
		} catch (Exception e) {
			
		}
		
		if(request.getSession()!=null)
				seguranca = (SegurancaECAR)request.getSession().getAttribute("seguranca");
		
		Map<String, String> listaArquivos = null;
		if(request.getSession()!=null) {
				seguranca = (SegurancaECAR)request.getSession().getAttribute("seguranca");
				if (seguranca!=null) {
					usuario = seguranca.getUsuario();
					if (usuario!=null) {
						listaArquivos = usuario.getMapArquivosAtuaisUsuarios();
						//Verifica se � para utilizar encode
						if (file.contains("&N")){
							if (listaArquivos!=null && listaArquivos.containsKey(file.substring(0, file.indexOf("&")))) {
								file = listaArquivos.get(file.substring(0, file.indexOf("&")));
							}
						}
						else{
							if (listaArquivos!=null && listaArquivos.containsKey(URLEncoder.encode(file, Dominios.ENCODING_DEFAULT))) {
								file = listaArquivos.get(URLEncoder.encode(file, Dominios.ENCODING_DEFAULT));
							}	
						}
					}
				}
		}

		//#################################################
		//# Verifica�ao 1
		//#################################################
		if ((seguranca==null || !seguranca.isAutenticado()) && (emp!=null && file!=null && file.indexOf(emp.getLogotipoEmp())==-1)) {
//			throw new ServletException(
//					"Download nao permitido: Usuario nao autenticado");

			out = response.getWriter();
		    out.println(stringHtml1);
		    return;
		}

		//#################################################
		//# Verifica�ao 2
		//#################################################		
		// remover ../ ou recursivamente
		while(file!=null && file.indexOf(".." + File.separator)>0) {
			file = file.substring(0,file.lastIndexOf(".." + File.separator)) + file.substring(file.lastIndexOf(".." + File.separator) + (".." + File.separator).length(), file.length()); 
		}
		
		//#################################################
		//# Verifica�ao 3
		//#################################################
		// verifica se caminho se inicia com o direito padrao de uploads
		if (file.indexOf(configuracao.getRaizUpload())==-1) {
//			throw new ServletException(
//			"Download nao permitido: Usuario nao autenticado");
		
			out = response.getWriter();
			  out.println(stringHtml2);
		    return;
		
		}

		// se log
		String tipo = request.getParameter("tipo");
		boolean download = true;
		boolean multimidia = false;
		if (tipo != null && "open".equals(tipo))
			download = false;
		if (tipo != null && "multimidia".equals(tipo)) {
			multimidia = true;
		}		
		
		String fileBytesHex = request.getParameter("RemoteFileSessao");
		String fileName = "getimage.jpeg";
		File f = null;
		File diretorioCorrente = new File(".");
		//Base64Decoder base64Decoder = null;
		
		
		// arquivo existe armazenado em algum local f�sico
		if (file!=null && file.length()>0) {
			file = Util.trocar(file, "/", File.separator);
			fileName = file.substring(file.lastIndexOf(File.separator) + 1);
			f = new File(file);
		
		} 

		
		if (multimidia) {
			response.setContentType("video/mpeg");
			response.setHeader("Cache-Control", "cache; filename=\"" + fileName
					+ "\"");
		} else if (download) {
			/**
			 * @author 05228805419
			 */
			// recupera o objeto de configuracao do response do contexto da aplica��o
			ConfiguracaoResponseBean configuracaoResponseBean = (ConfiguracaoResponseBean) request
					.getSession().getServletContext().getAttribute(
							ConfiguracaoResponseBean.class.getName());

			// verifica se o objeto de configuracao do response deve ser utilizado
			if (configuracaoResponseBean != null
					&& configuracaoResponseBean
							.getInUtilizarConfiguracaoResponse()) {

				// verifica se deve setar o content length
				if (configuracaoResponseBean.getInSetarContentLength()) {
					response.setContentLength((int) f.length());
				}
				
				if (configuracaoResponseBean.getContentType() != null) {
					response.setContentType(configuracaoResponseBean
							.getContentType());
				}

				HashMap<String, String> hmHeaders = configuracaoResponseBean
						.getHeaders();
				Iterator iterator = hmHeaders.keySet().iterator();

				while (iterator.hasNext()) {
					String chave = (String) iterator.next();
					String valor = (String) hmHeaders.get(chave);

					response.setHeader(chave, valor);
				}
			} else {
				// Garantir que o IE far� o download sob https
				if (f!=null) {
					if (!f.exists()) {
						File caminhoCompleto = new File(diretorioCorrente.getCanonicalPath() + f.getPath());
						response.setContentLength((int) caminhoCompleto.length());
					} else {
						response.setContentLength((int) f.length());
					}
				} else {
					try {
						response.setContentLength((int) FileUpload.recuperarArquivoTemporarioSessao(request, fileBytesHex).length);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				response.setContentType("application/octetstream");
				response.setHeader("Pragma", "Public");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			}
		} else {
			response.setContentType("image/jpeg");
			response.setHeader("Cache-Control", "no-cache; filename=\""
					+ fileName + "\"");
		}
		java.io.InputStream is = null;
		OutputStream out2 = null;
		try {
			out2 = response.getOutputStream();
			
			// arquivo existe fisicamente
			if (f!=null) {
				if (!f.exists())
					is = new FileInputStream(diretorioCorrente.getCanonicalPath() + file);
				else
					is = new FileInputStream(file);
			
			// arquivo n�o existe fisicamente e est� na sessao
			} else {
				out2.write(FileUpload.recuperarArquivoTemporarioSessao(request, fileBytesHex));
			}
			
			int i;
			while (is!=null && (i = is.read()) != -1) {
				out2.write(i);
			}
		} catch (Exception e) {
			this.logger.error(e);
			logger.equals(e);
		} finally {
			if (out2 != null) {
				out2.flush();
				out2.close();
			}
			if (is != null) {
				is.close();
			}
		}
	}
}
