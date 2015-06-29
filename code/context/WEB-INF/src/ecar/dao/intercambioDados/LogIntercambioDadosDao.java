/*
 * Criado em 02/12/2004
 *
 */
package ecar.dao.intercambioDados;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;

import ecar.dao.UsuarioDao;
import ecar.exception.ECARException;
import ecar.pojo.intercambioDados.LogIntercambioDadosLid;
import ecar.pojo.intercambioDados.funcionalidade.TipoFuncionalidadeEnum;
import ecar.pojo.intercambioDados.tecnologia.TipoTecnologiaEnum;

/**
 * @author felipev
 *
 */
public class LogIntercambioDadosDao extends Dao {
	
	/**
	 * 
	 */
	public LogIntercambioDadosDao() {
		super();
	}
	
	
    /**
     * Construtor. Chama o Session factory do Hibernate
     * @param request
     */
    public LogIntercambioDadosDao(HttpServletRequest request)
    {
        super();
        this.request = request;
    }
    
    /**
     * @return
     * @throws ECARException
     */
    @SuppressWarnings("unchecked")
	public List<LogIntercambioDadosLid> listarLog() throws ECARException{
	
    	List<LogIntercambioDadosLid> lista = listar(LogIntercambioDadosLid.class, new String[] {"dataHoraProcessamentoLid",Dao.ORDEM_DESC});
    	
    	return lista;
    }
    
    
    public void salvar(LogIntercambioDadosLid logIntercambioDados){
    	
    	getSession().save(logIntercambioDados);
    	
    }

    public void salvarOrUpdate(LogIntercambioDadosLid logIntercambioDados){
    	
    	getSession().saveOrUpdate(logIntercambioDados);
    	
    }
    
    
    
    // selecionar a data mais recente do processsamento de log
    // parametros: tipo de tecnologia e tipo de funcionalidade
    public Date buscar(TipoTecnologiaEnum  tecno, TipoFuncionalidadeEnum func) throws ECARException {
    	
    	Date retorno = null;
    	
        try {                   	
        	StringBuilder query = new StringBuilder("select log from LogIntercambioDadosLid as log")
				.append(" where log.perfilLog.tipoTecnologia = '").append(tecno.getDescricao())
				.append("' and log.perfilLog.tipoFuncionalidade = '").append(func.getDescricao())
				.append("' order by codLid desc");
			Query q = this.getSession().createQuery(query.toString());
									
			retorno = ((LogIntercambioDadosLid)q.list().get(0)).getDataHoraProcessamentoLid();
			
        } catch (HibernateException e) {
            this.logger.error(e);
            e.printStackTrace();
            throw new ECARException("erro.hibernateException");
        }
    	
        return retorno;
        
    }
    
    
    
    public static void main(String[] args) throws Exception {
    	
    	
    			
    	LogIntercambioDadosDao dao = new LogIntercambioDadosDao();
    	UsuarioDao usuarioDao = new UsuarioDao();
    	
    	org.hibernate.Transaction tx = dao.getSession().beginTransaction();
    	
//    	PerfilIntercambioDadosLogPflogid perfilLog = new PerfilIntercambioDadosLogPflogid(null, "CADASTRO", "dadoscadastro", "TXT", "linhatxt");
//    	DadosTecnlogiaLogIntercambioDadosLogdtid dadosTecnologia = new DadosTecnlogiaLogIntercambioDadosLogdtid(null, Calendar.getInstance().getTime(), "nome",
//    			Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), null);
//    	
//    	UsuarioUsu usuarioUsu = usuarioDao.getUsuarioUsuByNome("Administrador do eCAR");
//    	
//    	MotivoRejeicaoMtr motivo = new MotivoRejeicaoMtr();
//    	motivo.setId(Long.valueOf(101));
//    	motivo.setMensagem("Foi encontrado um registro desconhecido no arquivo.");
//    	EntidadeLogIntercambioDadosEtlogid entidade = new EntidadeLogIntercambioDadosEtlogid(null, "ITEM", motivo, "A", "B", Long.valueOf(1), "A", "I", null);
//    	Set<EntidadeLogIntercambioDadosEtlogid> entidades = new HashSet<EntidadeLogIntercambioDadosEtlogid>();
//    	entidades.add(entidade);
//    	
//    	LogIntercambioDadosLid log = new LogIntercambioDadosLid(null,
//    			Calendar.getInstance().getTime(), 
//    			"M", 
//    			usuarioUsu, 
//    			Long.valueOf(1), 
//    			"conteudo", null,
//    			perfilLog,
//    			"A", 
//    			entidades, 
//    			dadosTecnologia);
//    	
//    	entidade.setLogIntercambioDados(log);
    	
//    	dao.salvarOrUpdate(log);
    	System.out.println(dao.buscar(TipoTecnologiaEnum.ARQUIVOTEXTO_PACINTER, TipoFuncionalidadeEnum.CADASTRO));
    	
    	tx.commit();
    	
	}
    
}
