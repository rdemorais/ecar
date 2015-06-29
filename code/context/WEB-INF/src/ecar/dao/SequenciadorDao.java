package ecar.dao;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;

import comum.database.Dao;
import comum.util.ConstantesECAR;
import comum.util.Util;

import ecar.pojo.EstruturaEtt;
import ecar.pojo.FuncaoFun;
import ecar.pojo.SequenciadoraSeq;
import ecar.pojo.SisAtributoSatb;

/**
 *
 * @author 70744416353
 */
public class SequenciadorDao extends Dao {

    /**
     *
     * @param session
     */
    public SequenciadorDao(Session session){
		super(session);
	}


    /**
     *
     * @param sequenciador
     */
    public void salvar(SequenciadoraSeq sequenciador){
		session.save(sequenciador);
	}
	
        /**
         *
         * @param sequenciador
         */
        public void atualizar(SequenciadoraSeq sequenciador){
		session.update(sequenciador);
	}
	

        /**
         *
         * @param request
         */
        public void setHttpRequest(HttpServletRequest request){
		this.request = request;
	}
	
    
        /**
         *
         * @param sisAtributo
         * @param funcao
         * @param estrutura
         * @return
         * @throws IOException
         */
        public SequenciadoraSeq consultar(SisAtributoSatb sisAtributo, FuncaoFun funcao,EstruturaEtt estrutura) throws IOException {
    	
    	String hql = null;
    
    	if (sisAtributo.getGeral() && sisAtributo.getPeriodico()) { //O atributo é geral e reinicializa a cada novo ano
    		int ano = Calendar.getInstance().get(Calendar.YEAR);
    		
    		hql = MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_SEQUENCIADORA_GERAL_PERIODICO, request.getSession().getServletContext()), sisAtributo.getCodSatb(),ano+"");
    	} else if (sisAtributo.getGeral() && !sisAtributo.getPeriodico()) {//O atributo é geral e não reinicializa a cada novo ano
    		hql = MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_SEQUENCIADORA_GERAL_NAO_PERIODICO, request.getSession().getServletContext()), sisAtributo.getCodSatb());
    	} else if (!sisAtributo.getGeral() && sisAtributo.getPeriodico()) {//O atributo não é geral, ou seja, é específico para a função usada (Ex.: Dados Gerais ou Pontos criticos), e reinicializa a cada novo ano
    		int ano = Calendar.getInstance().get(Calendar.YEAR);
    		
    		hql = MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_SEQUENCIADORA_NAO_GERAL_PERIODICO, request.getSession().getServletContext()), sisAtributo.getCodSatb(),ano+"",funcao.getCodFun(),estrutura.getCodEtt());
    	} else if (!sisAtributo.getGeral() && !sisAtributo.getPeriodico()) {//O atributo não é geral, ou seja, é específico para a função usada (Ex.: Dados Gerais ou Pontos criticos), e NÃO reinicializa a cada novo ano
    		hql = MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_SEQUENCIADORA_NAO_GERAL_NAO_PERIODICO, request.getSession().getServletContext()), sisAtributo.getCodSatb(),funcao.getCodFun(),estrutura.getCodEtt());
    	}
    	Query q = this.session.createQuery(hql);
    	
		return (SequenciadoraSeq)q.uniqueResult();
    	
    }
}
