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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import comum.util.FileUpload;
import comum.util.Util;

import ecar.dao.ConfiguracaoDao;
import ecar.dao.EmpresaDao;
import ecar.dao.ItemEstruturaUploadDao;
import ecar.exception.ECARException;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EmpresaEmp;
import ecar.pojo.ItemEstrutUploadIettup;
import ecar.util.ConfiguracaoResponseBean;
import ecar.util.CriptografiaUtil;
import ecar.webservices.Seguranca;
import ecar.webservices.SegurancaWS;
import ecar.webservices.exc.SegurancaWSException;

/**
 * @author felipev
 *
 */ 
public class DownloadFileIett extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5548532554690785199L;
	private Logger logger = Logger.getLogger(this.getClass());

	private static ConfiguracaoCfg configuracao = null;
	private static EmpresaDao empDao = null;
	private static EmpresaEmp emp = null;
	private static String encode = null;
	
	/**
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public DownloadFileIett(){
				
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
    @Override
	public final void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String strDownloadDeslogado = request.getParameter("acessoPublico");
		if (strDownloadDeslogado != null) {
			request.getSession().setAttribute("downloadDeslogado",
					new Boolean(true));
		}
		
		//SegurancaECAR seguranca = null;
		PrintWriter out = null;
		String arquivo = URLDecoder.decode(request.getParameter("RemoteFile"));

		String stringHtml1 = null;
		stringHtml1 =  "<HTML><HEAD><TITLE>ecar</TITLE></HEAD>" +
        "<BODY>\n" +
        "<H1>Download n&atilde;o permitido: Usu&aacute;rio n&atilde;o autenticado</H1>\n" +
        "</BODY></HTML>";
		
		
		String codigoArquivo = CriptografiaUtil.decriptar(CriptografiaUtil.CHAVE, arquivo.replace(" ", "+"));
		
		ItemEstruturaUploadDao itemEstruturaUploadDao = new ItemEstruturaUploadDao(null);
		
		ItemEstrutUploadIettup t = null;
		try {
			t = (ItemEstrutUploadIettup) itemEstruturaUploadDao.buscar(ItemEstrutUploadIettup.class, Long.parseLong(codigoArquivo));
		} catch (ECARException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String file = t.getArquivoIettup();
		
		System.out.println(arquivo);
		System.out.println(file);
			
		if (configuracao == null) {
			try {
				configuracao = new ConfiguracaoDao(request).getConfiguracao();
			} catch (ECARException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		file = configuracao.getRaizUpload()+file;
		
//		if(!file.equals("")){
//			response.getWriter().println(file);
//			return;
//		}
			
		SegurancaWS segurancaWS = SegurancaWS.getInstance();
		
		Seguranca seguranca = new Seguranca();
		seguranca.setUuid(request.getParameter("uiid"));
		

		try {
			if(!segurancaWS.verificaSessao(seguranca.getUuid())) {
					out = response.getWriter();
				    out.println(stringHtml1);
				    return;				
			}
		} catch (SegurancaWSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		//#################################################
		//# Verifica�ao 2
		//#################################################		
		// remover ../ ou recursivamente
		while(file!=null && file.indexOf(".." + File.separator)>0) {
			file = file.substring(0,file.lastIndexOf(".." + File.separator)) + file.substring(file.lastIndexOf(".." + File.separator) + (".." + File.separator).length(), file.length()); 
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
	
	private void configurarResponse(HttpServletResponse response) {
		
	}
	
	public String decode(String s, String enc) 
	throws UnsupportedEncodingException{
	
	boolean needToChange = false;
	int numChars = s.length();
	StringBuffer sb = new StringBuffer(numChars > 500 ? numChars / 2 : numChars);
	int i = 0;

	if (enc.length() == 0) {
	    throw new UnsupportedEncodingException ("URLDecoder: empty string enc parameter");
	}

	char c;
	byte[] bytes = null;
	while (i < numChars) {
            c = s.charAt(i);
            switch (c) {
	    case '+':
		sb.append(' ');
		i++;
		needToChange = true;
		break;
	    case '%':
		/*
		 * Starting with this instance of %, process all
		 * consecutive substrings of the form %xy. Each
		 * substring %xy will yield a byte. Convert all
		 * consecutive  bytes obtained this way to whatever
		 * character(s) they represent in the provided
		 * encoding.
		 */

		try {

		    // (numChars-i)/3 is an upper bound for the number
		    // of remaining bytes
		    if (bytes == null)
			bytes = new byte[(numChars-i)/3];
		    int pos = 0;
		    
		    while ( ((i+2) < numChars) && 
			    (c=='%')) {
			bytes[pos++] = 
			    (byte)Integer.parseInt(s.substring(i+1,i+3),16);
			i+= 3;
			if (i < numChars)
			    c = s.charAt(i);
		    }

		    // A trailing, incomplete byte encoding such as
		    // "%x" will cause an exception to be thrown

		    if ((i < numChars) && (c=='%'))
			throw new IllegalArgumentException(
		         "URLDecoder: Incomplete trailing escape (%) pattern");
		    
		    sb.append(new String(bytes, 0, pos, enc));
		} catch (NumberFormatException e) {
		    throw new IllegalArgumentException(
                    "URLDecoder: Illegal hex characters in escape (%) pattern - " 
		    + e.getMessage());
		}
		needToChange = true;
		break;
	    default: 
		sb.append(c); 
		i++;
		break; 
            }
        }

        return (needToChange? sb.toString() : s);
    }
	
}
