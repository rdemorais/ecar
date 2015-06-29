/*
 * Created on 30/11/2004
 * 
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.ConfigExecFinanCef;
import ecar.pojo.ConfigSisExecFinanCsef;
import ecar.pojo.ConfigSisExecFinanCsefv;

/**
 * @author denilson
 *
 */
public class ConfigExecFinanDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public ConfigExecFinanDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * 
	 * @author n/c
	 * @param csef
	 * @throws ECARException
	 */
    public void excluir(ConfigSisExecFinanCsef csef) throws ECARException {
        try {
            boolean excluir = true;
            if (contar(csef.getConfigSisExecFinanCsefvs()) > 0) {
                excluir = false;
                throw new ECARException(
                        "csef.exclusao.erro.configSisExecFinanCsefv");
            }
		    if(excluir)
		        super.excluir(csef);	
	   }catch(ECARException e){
		   this.logger.error(e);
	       throw e;
	   }    
	}
	
    
    /**
     * 
     * @author n/c
     * @param versao
     * @return list
     * @throws ECARException
     */
    public List getConfigExecFinanByVersao(ConfigSisExecFinanCsefv versao) throws ECARException{
    	List retorno = new ArrayList();
    	try{
    		StringBuilder sql = new StringBuilder("select config from ConfigExecFinanCef config")
    							    	.append(" join config.configSisExecFinanCsefv versao")
    							    	.append(" where versao.mesVersaoCsefv = :mesVersao")
    							    	.append("   and versao.anoVersaoCsefv = :anoVersao")
    							    	.append(" order by config.seqApresentacaoCef asc");
    		
    		Query q = this.session.createQuery(sql.toString());
    		
    		q.setLong("mesVersao", versao.getMesVersaoCsefv().longValue());
    		q.setLong("anoVersao", versao.getAnoVersaoCsefv().longValue());
    		
    		retorno = q.list();
    		
    		if(retorno == null)
    			retorno = new ArrayList();
    	}
    	catch (HibernateException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
		}
    	
    	return retorno;
    }
    
    /**
     * Método que gera os campos para inserção da conta, seguindo o layout definido na versão
     * cadastrada na estrutura contábil.
     * @param conta Se for informada uma conta, recupera os valores gravados para esta conta.
     * @param versao Versão que está sendo lida.
     * @param disabled indicativo que define se os campos devem aparecer habilitados ou não.
     * @param request Procura no request a informação de valores para cada campo criado.
     * @return
     * @throws ECARException
     */
    public String geraHTMLLayoutConta(String conta, ConfigSisExecFinanCsefv versao, boolean disabled, HttpServletRequest request) throws ECARException {
        StringBuilder retorno = new StringBuilder();
        String strConta = conta;
        List estruturasContabil = this.getConfigExecFinanByVersao(versao);
        retorno.append("");
        if (estruturasContabil != null) {
            Iterator it = estruturasContabil.iterator();
            String camposConta[] = new String[estruturasContabil.size()];
            if(strConta != null)
                camposConta = strConta.split(" ");

            String hidEstruturasContabeis = "";
            
            int i = 0;       
            while (it.hasNext()) {
                String strValor = "";
                ConfigExecFinanCef estruturaContabil = (ConfigExecFinanCef) it
                        .next();
                /* este try-catch serve para aassegurar que não irá ocorrer uma exceção ao ler valores após
                 * ter sido criada uma nova estrutura contábil
                 */
                try{
                    if(camposConta[i] != null)
                        strValor = camposConta[i];                    
                } catch ( ArrayIndexOutOfBoundsException ex){
                    strValor = "";
                    //this.logger.error(ex); Não é necessário logar essa exceção
                }
                if(!"".equals(Pagina.getParamStr(request, "e" + estruturaContabil.getCodCef())))
                    strValor = Pagina.getParamStr(request, "e" + estruturaContabil.getCodCef());
                retorno.append("<div class=\"tabelaaolado\">")
                	   .append(estruturaContabil.getLabelCef())
                	   .append("&nbsp;&nbsp;</br>")
                	   .append("<input type=\"text\" size=\"")
                	   .append(estruturaContabil.getNumCaracteresCef())
                	   .append("\" maxlength=\"")
                	   .append(estruturaContabil.getNumCaracteresCef())
                	   .append("\" name=\"e")
                	   .append(estruturaContabil.getCodCef())
                	   .append(String.valueOf(versao.getCodCsefv()))
                	   .append("\" value=\"")
                	   .append(strValor)
                	   .append("\"");
                hidEstruturasContabeis += estruturaContabil.getCodCef().toString() + "|";
               if(disabled)
                   retorno.append( " disabled ");
               retorno.append("></div>");               
                i++;
            }
            if(!"".equals(retorno.toString().trim()))
            	retorno.append("<input type=\"hidden\" name=\"layout" + String.valueOf(versao.getCodCsefv()) + "\" value=\"" + hidEstruturasContabeis + "\">");
        }        
        return retorno.toString();
    }
}