/*
 * Created on 17/06/2005
 *
 */
package comum.util;

import java.util.Calendar;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Mappings;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PrimaryKey;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Table;
import org.hibernate.type.Type;

import comum.database.HibernateUtil;

import ecar.pojo.UsuarioUsu;

/**
 * @author cristiano
 * @since 17/06/2005
 * @version N/C
 */
public class LogBean {
    /**
     *
     */
    public static final String SEPARADOR_CAMPOS_LOG = "#";
    /**
     *
     */
    public static final String SEPARADOR_CAMPOS_DADOS = ",";
	
	private Logger logger = null;

	private Object obj = null; // objeto que foi inserido, atualizado ou deletado
	private UsuarioUsu usuario = null; // usuario logado
	private String iPUsuario = null; // IP do usuário logado
	private String operacao = null; // Operação: Incluir, Excluir ou Alterar
	private String codigoTransacao = null;
	private String codigoSessao = null;
	
	/**
	 * Retorna String IPUsuario.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String		- IPUsuario
	 */
	public String getIPUsuario() {
		return iPUsuario;
	}

	/**
	 * Atribui valor especificado para String IPUsuario.<br>
	 * 
         * @param usuario
         * @author N/C
     * @since N/C
     * @version N/C
	 */
	public void setIPUsuario(String usuario) {
		iPUsuario = usuario;
	}

	/**
	 * Retorna Object obj.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return Object		- obj
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * Atribui valor especificado para Object obj.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param obj
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	/**
	 * Retorna UsuarioUsu usuario.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return UsuarioUsu
	 */
	public UsuarioUsu getUsuario() {
		return usuario;
	}
	
	/**
	 * Atribui valor especificado para UsuarioUsu usuario.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param usuario
	 */
	public void setUsuario(UsuarioUsu usuario) {
		this.usuario = usuario;
	}

	/**
	 * Construtor.<br>
	 * 
	 * @author N/C
     * @since N/C
	 * @version N/C
	 */
	public LogBean() {
		logger = Logger.getLogger(this.getClass());
	}

	/**
	 * Retorna String operacao.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public String getOperacao() {
		return operacao;
	}

	/**
	 * Atribui valor especificado para String opreracao.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param operacao
	 */
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
	
	/**
	 * Retorna String codigoSessao.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public String getCodigoSessao() {
		return codigoSessao;
	}

	/**
	 * Atribui valor especificado para String codigoSessao.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param codigoSessao
	 */
	public void setCodigoSessao(String codigoSessao) {
		this.codigoSessao = codigoSessao;
	}

	/**
	 * Retorna String codigoTransacao.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public String getCodigoTransacao() {
		return codigoTransacao;
	}

	/**
	 * Atribui valor especificado para String codigoTransacao
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param codigoTransacao
	 */
	public void setCodigoTransacao(String codigoTransacao) {
		this.codigoTransacao = codigoTransacao;
	}

	/**
	 * Retorna String no formato:<br>
	 * CodUsu#IPUsuario#CodigoTransacao#CodigoSessao#Operacao#TableName#Dados#DataAtual.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
     * @return String
	 */
    @Override
	public String toString() {
		
		StringBuffer retorno = new StringBuffer(); 
		retorno.append(getUsuario().getCodUsu());
		retorno.append(SEPARADOR_CAMPOS_LOG);
		retorno.append(getIPUsuario());
		retorno.append(SEPARADOR_CAMPOS_LOG);
		retorno.append(getCodigoTransacao());
		retorno.append(SEPARADOR_CAMPOS_LOG);
		retorno.append(getCodigoSessao());
		retorno.append(SEPARADOR_CAMPOS_LOG);
		retorno.append(getOperacao());
		retorno.append(SEPARADOR_CAMPOS_LOG);
		if (getTableName(getObj())!=null)
		retorno.append(getTableName(getObj()));
		retorno.append(SEPARADOR_CAMPOS_LOG);
		retorno.append(getDados());
		retorno.append(SEPARADOR_CAMPOS_LOG);
		retorno.append(Data.parseDate(Data.getDataAtual()));
				
		return retorno.toString();
	}
	
	/**
	 * Retorna String Colunas.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	private String getDados() {
		return getColunas(getObj());
	}
	
	/**
	 * Se comSeparador verdadeiro retorna a Hora atual com separador ':', do contrario retorna sem.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param comSeparador
	 * @return String
	 */
	public static String getHoraAtual(boolean comSeparador)	{
			Calendar cal = Calendar.getInstance();

			StringBuffer  hh = new StringBuffer(); 
			StringBuffer  mm = new StringBuffer(); 
			StringBuffer  ss = new StringBuffer(); 
			hh.append(cal.get(Calendar.HOUR_OF_DAY));
			mm.append(cal.get(Calendar.MINUTE));
			ss.append(cal.get(Calendar.SECOND));

			if(mm.length() < 2)
				mm.insert(0, "0");
			if(hh.length() < 2)
				hh.insert(0, "0");
			if(ss.length() < 2)
				ss.insert(0, "0");
								
			if(comSeparador) {
				return (hh.append(":").append(mm).append(":").append(ss)).toString();
			}
			else {
				return (hh.append(mm).append(ss)).toString();
			}
	}
		
	

	
	/**
	 * Retorna o nome/valor dos campos da tabela que não sejam PK.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param Object o		- Objeto a ser recuperados os valores
	 * @return String		- Nome/Valor dos campos
	 */
	private String getColunas(Object o) {
		StringBuffer r = new StringBuffer();
		
		r.append(getPkName(o)).append(SEPARADOR_CAMPOS_DADOS);
		
		
		Configuration conf = HibernateUtil.getConfiguration();
		Mappings map = conf.createMappings();
		//TODO: verificar se funcionará apos mudança...
		Iterator itr = null;
		
		if (o!=null && o.getClass().getName()!=null && map.getClass(o.getClass().getName())!=null)
			itr = map.getClass(o.getClass().getName()).getPropertyIterator();
		
		while (itr!=null && itr.hasNext()) {
			Property prop = (Property) itr.next();
			
			String colname = prop.getName();
			//Value value = prop.getValue();
			String colvalue = null;
			Type t = prop.getType();
			
			if("java.util.Set".equals(t.getReturnedClass().getName())) {
				continue;
			}

			try{
				Object v = Util.invocaGet(o, colname);
				boolean mostrarIgual = true; 

				if(v != null) {
					// se for data, formata-la
					if(v instanceof java.util.Date) {
						colvalue = Data.parseDate((java.util.Date)v);
					}
					// se for do pacote ecar.pojo, recuperar o valor da chave
					else if(t.getReturnedClass().getName().indexOf("ecar.pojo.") > -1) {
						colvalue = getPkName(v);
						mostrarIgual = false; 
					}
					else {
						colvalue = v.toString();
					}
				}
				
				if(mostrarIgual) {
					r.append(colname.toUpperCase()).append("=").append(colvalue);
				}
				else {
					r.append(colvalue);
				}
				r.append(SEPARADOR_CAMPOS_DADOS);

			}catch(Exception e){
				logger.error(e);
			}
		}
		r.delete(r.length() - SEPARADOR_CAMPOS_DADOS.length(), r.length());

		return r.toString();
	}
	
	/**
	 * Retorna o nome e o valor da(s) chave(s) primária(s).<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param Object o		- Objeto a ser descoberto a(s) chave(s)
	 * @return String		- Nome/valor da(s) chave(s)
	 */
	private String getPkName(Object o) {
		StringBuffer r = new StringBuffer();
		Configuration conf = HibernateUtil.getConfiguration();
		Mappings map = conf.createMappings();
		if (o!=null && o.getClass().getName()!=null && map.getClass(o.getClass().getName())!=null) {
			Table tab = map.getClass(o.getClass().getName()).getTable();
			PrimaryKey pk = tab.getPrimaryKey();
			Iterator itr = pk.getColumnIterator();
			while (itr.hasNext()) {
				Column col = (Column) itr.next();
				String colname = col.getName();
				String value = "";
				try {
					value = conf.getClassMapping(o.getClass().getName())
							.getIdentifierProperty().getGetter(o.getClass()).get(o)
							.toString();
				} catch (HibernateException e) {
					logger.error(e);
				}
				r.append(colname.toUpperCase()).append("=").append(value);
				r.append(SEPARADOR_CAMPOS_DADOS);
			}
			r.delete(r.length() - SEPARADOR_CAMPOS_DADOS.length(), r.length());
		
		}
		
		return r.toString();
		
	}

	/**
	 * Recupera o nome da tabela de um determinado objeto.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param Object o		- Objeto a ser descoberta a tabela
	 * @return String		- Nome da tabela
	 */
	private String getTableName(Object o) {
		Configuration conf = HibernateUtil.getConfiguration();
		Mappings map = conf.createMappings();
		Table tab = null;
		String tabela2 = null;
		if (o!=null && o.getClass().getName()!=null && map.getClass(o.getClass().getName())!=null) {
			tab = map.getClass(o.getClass().getName()).getTable();
			tabela2 = tab.getName();
		}


		return tabela2;
	}
}
