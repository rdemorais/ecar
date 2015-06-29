package ecar.email;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import comum.util.Data;

import ecar.dao.ConfiguracaoDao;
import ecar.dao.UsuarioDao;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.UsuarioUsu;

/**
 * Filtro que fecha a sessao corrente do hibernate cada vez que o Web Container processa
 * uma pagina. O filtro e configurado no web.xml.<br>
 * 
 * @author steinmacher
 */
public class AtualizadorEmail implements Filter {

	
    /**
     * Construtor.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public AtualizadorEmail() {
        super();
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    /**
     * Inicializador.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param arg0
     * @throws ServletException
     */
    public void init(FilterConfig arg0) throws ServletException {
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    /**
     * Esse filtro atualiza os mails dos Usuarios.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param request
     * @param response 
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
   		try {
   	        chain.doFilter(request, response);
//			TODO: Criar os campos na tabela configuracaoCfg (hbm+pojo+dao) e ativar as linhas abaixo
   	        HttpServletRequest httpRequest = (HttpServletRequest) request;
   			ConfiguracaoDao configDao = new ConfiguracaoDao(httpRequest);
 	    	ConfiguracaoCfg configuracao =  configDao.getConfiguracao();
 	    	if (Data.getDataAtual().after(Data.addDias(configuracao.getIntervaloAtualizacaoEmail().intValue() ,configuracao.getUltimaAtualizacaoEmail())))
 	    	{
		    	UsuarioDao usuDao = new UsuarioDao((HttpServletRequest)request);
		    	UsuarioUsu usuario = null;
		    	Iterator listaIds = usuDao.getListaIdDominio().iterator();
		    	List listaUsu = new ArrayList();
		    	while (listaIds.hasNext()) 
		    	{
		    		String idAtual = (String) listaIds.next();
		    		usuario = usuDao.getUsuarioByIdDominio(idAtual);
		    		if ((!"".equals(usuario.getEmail1UsuSent())) || (!(UsuarioDao.INFORMACAO_NAO_LOCALIZADA_SENTINELA).equals(usuario.getNomeUsu())))
		    		{
		    			usuario.setEmail1Usu(usuario.getEmail1UsuSent());
		    			usuario.setNomeUsu(usuario.getNomeUsuSent());
		    			listaUsu.add(usuario);
		    		}
		    	}
		    	if (listaUsu.size() > 0)
		    		usuDao.salvarOuAlterar(listaUsu);    	

		    	//Após alterar os usuários, guarda a data da atualização em ConfiguracaoCfg
		    	String fullUrl;
		    	fullUrl = httpRequest.getScheme() + "://" + httpRequest.getServerName();
				if (httpRequest.getServerPort() != 80) {
					fullUrl += ":" + httpRequest.getServerPort();
				}
				fullUrl += httpRequest.getContextPath();
				
				
		    	configuracao.setContextPath(fullUrl);
		    	configuracao.setUltimaAtualizacaoEmail(Data.getDataAtual());
   				configDao.salvarOuAlterar(configuracao);
 	    	}
	    } catch (Exception e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	    }
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    /**
     * Destrutor.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public void destroy() {
    }

}
