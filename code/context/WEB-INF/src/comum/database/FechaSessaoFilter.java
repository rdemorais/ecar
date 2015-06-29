/*
 * Created on 04/04/2005
 *
 */
package comum.database;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

/**
 * Filtro que fecha a sessao corrente do hibernate cada vez que o Web Container processa
 * uma pagina. O filtro e configurado no web.xml.<br>
 * 
 * @author garten
 * @since N/C
 * @version N/C
 */
public class FechaSessaoFilter implements Filter {

    /**
     * Construtor.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public FechaSessaoFilter() {
        super();
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    /**
     * Metodo de inicializacao.<br>
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
     * Esse filtro faz pos-processamento das requisições, fechando a sessão do Hibernate.<br>
     * 
     * @param request 
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     * @author N/C
     * @since N/C
     * @version N/C
     * @Param ServletRequest request
     * @Param ServletResponse response
     * @Param FilterChain chain
     * @Throws IOException
     * @Throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //String pagina =  ((HttpServletRequest) request).getServletPath();
        // executa a pagina e outros filtros, se existirem
        chain.doFilter(request, response);

        // por ultimo, fecha a sessao aberta.
        try {
            HibernateUtil.closeSession();
        } catch (Exception e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        	e.printStackTrace();
            HibernateUtil.clearSession();
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    /**
     * Metodo destrutor.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public void destroy() {
    }

}
