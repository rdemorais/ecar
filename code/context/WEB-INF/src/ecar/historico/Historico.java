
package ecar.historico;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.thoughtworks.xstream.XStream;
import comum.util.Compactador;
import comum.util.Data;

import ecar.dao.ConfiguracaoDao;
import ecar.dao.HistoricoDao;
import ecar.exception.ECARException;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.historico.HistoricoXml;
import ecar.pojo.historico.IHistorico;

/**
 *
 * @author 70744416353
 * @param <HISTORICO>
 * @param <POJO>
 */
public class Historico<HISTORICO extends IHistorico, POJO extends Serializable> {
	
	/** Tipo Hitórico - Incluir */
	public static final Long INCLUIR = 1L;
	/** Tipo Hitórico - Alterar */
	public static final Long ALTERAR = 2L;
	/** Tipo Hitórico - Excluir */
	public static final Long EXCLUIR = 3L;
	private HISTORICO objHistorico = null;
	private POJO objPai = null;
	private Class<HISTORICO> historicozz;
	private Class<POJO> pojozz;
	
        /**
         *
         */
        public Historico() {
		historicozz = (Class<HISTORICO>) ((ParameterizedType) (getClass().getGenericSuperclass())).getActualTypeArguments()[0];
		pojozz = (Class<POJO>) ((ParameterizedType) (getClass().getGenericSuperclass())).getActualTypeArguments()[1];
		try {
			objHistorico = historicozz.newInstance();
			objPai = pojozz.newInstance();
		}
		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Cria o historico apartir de um objeto que será serializado em formato
	 * xml, depois compactado e posteriormente salvo na tabela historico xml.
	 * 
         * @param objHistorico
         * @param tipoHistorico
	 *            - Representa qual a operação de histórico a ser realizada
	 *            +Long
	 * @param usuario
	 *            - Usuário que está logado no momento. +UsuarioUsu
	 * @param session
	 *            - Instância de Hibernate Session para Salvar o histórico
         *            dentro da mesma transação que o objeto a ser salvo. Session
         * @throws ECARException
	 */
	public void setHistorico(HISTORICO objHistorico, Long tipoHistorico, UsuarioUsu usuario, Session session) throws ECARException {
		ConfiguracaoCfg config = new ConfiguracaoDao(null).getConfiguracao();
		Compactador comp = new Compactador();
		XStream xstream = new XStream();
		HistoricoXml xml = new HistoricoXml();
		if ("S".equals(config.getIndGerarHistoricoCfg())) {
			if (objHistorico != null) {
				objHistorico.setUsuarioUsuOperacao(usuario);
				objHistorico.setDataHistorico(Data.getDataAtual());
				objHistorico.setTipoHistorico(tipoHistorico);
				objHistorico.setIdHistorico(xml.getCodigo());
				xml.setObjeto((comp.compactar(xstream.toXML(objHistorico))));
				xml.setHistorico(objHistorico.getClass().getName());
				xml.setUsuarioUsu(usuario);
				xml.setDataHistorico(Data.getDataAtual());
				xml.setTipoHistorico(tipoHistorico);
				xml.setIdObjetoSerializado(objHistorico.getIdObjetoSerializado());
				session.save(xml);
			}
		}
	}
	
        /**
         *
         * @param chaveHistorico
         * @return
         * @throws ECARException
         */
        public HistoricoXml getHistorico(Serializable chaveHistorico) throws ECARException {
		HistoricoXml xml = new HistoricoXml();
		HistoricoDao dao = new HistoricoDao(null);
		xml = (HistoricoXml) dao.buscar(xml.getClass(), Long.valueOf(chaveHistorico.toString()));
		return xml;
	}
	
        /**
         *
         * @param usuario
         * @param tipoHistorico
         * @return
         * @throws ECARException
         */
        public List<HistoricoXml> listaHistorico(UsuarioUsu usuario, Long tipoHistorico) throws ECARException {
		List<HistoricoXml> lstDados = null;
		HistoricoXml xml = new HistoricoXml();
		HistoricoDao dao = new HistoricoDao(null);
		String[] ordem = new String[] { "historico", "asc", "idObjetoSerializado", "asc", "dataHistorico", "desc" };
		Map<String, Object> argumentos = new HashMap<String, Object>();
		argumentos.put("historico", objHistorico.getClass().getName());
		if (usuario != null)
			argumentos.put("usuarioUsu", usuario);
		if (tipoHistorico != null && tipoHistorico > 0)
			argumentos.put("tipoHistorico", tipoHistorico);
		lstDados = dao.buscar(xml.getClass(), null, null, null, argumentos, ordem);
		return lstDados;
	}
	
        /**
         *
         * @param listaObjetoHistorico
         * @return
         * @throws ECARException
         */
        public List<POJO> listaObjetoSerializado(List<HISTORICO> listaObjetoHistorico) throws ECARException {
		List<POJO> lstHistorico = new ArrayList<POJO>();
		for (IHistorico objeto : listaObjetoHistorico) {
			lstHistorico.add((POJO) objeto.descarregar());
		}
		return lstHistorico;
	}
	
        /**
         *
         * @param listaHistorico
         * @return
         * @throws ECARException
         */
        public List<HISTORICO> listaObjetoHistorico(List<HistoricoXml> listaHistorico) throws ECARException {
		List<HISTORICO> lstHistorico = new ArrayList<HISTORICO>();
		Compactador comp = new Compactador();
		XStream xstream = new XStream();
		
		for (HistoricoXml objeto : listaHistorico) {
			objHistorico = (HISTORICO) xstream.fromXML(comp.descompactar(objeto.getObjeto()));
			objHistorico.setIdHistorico(objeto.getCodigo());
			lstHistorico.add(objHistorico);
		}
		
		return lstHistorico;
	}
	
        /**
         *
         * @param usuario
         * @param inicio
         * @param fim
         * @param tipoHistorico
         * @param codigos
         * @return
         * @throws ECARException
         */
        public List<HISTORICO> listaObjetoHistorico(UsuarioUsu usuario, Date inicio, Date fim, String[] tipoHistorico, String[] codigos) throws ECARException {
		List<HistoricoXml> lstDados = new ArrayList<HistoricoXml>();
		List<HISTORICO> lstHistorico = new ArrayList<HISTORICO>();
		Compactador comp = new Compactador();
		XStream xstream = new XStream();
		HistoricoXml xml = new HistoricoXml();
		HistoricoDao dao = new HistoricoDao(null);
		String[] ordem = new String[] { "historico", "asc",  "idObjetoSerializado", "asc", "dataHistorico", "desc" };
		Map<String, Object> argumentos = new HashMap<String, Object>();
		List<Long> argumentosIN = new ArrayList<Long>();
		String campoIN = "idObjetoSerializado";
		if (tipoHistorico != null)
			for (String tipo : tipoHistorico) {
				argumentos.put("tipoHistorico", Long.valueOf(tipo));
			}
		if (codigos != null)
			for (String cod : codigos) {
//				argumentos.put("idObjetoSerializado", Long.valueOf(cod));
				argumentosIN.add(Long.valueOf(cod));
			}
		if (usuario != null)
			argumentos.put("usuarioUsu", usuario);
		if (objHistorico != null) {
			argumentos.put("historico", objHistorico.getClass().getName());
			if (inicio != null & fim != null)
				lstDados = dao.buscar(xml.getClass(), "dataHistorico", inicio, fim, argumentos, ordem);
			else
				lstDados = dao.buscar(xml.getClass(), null, null, null, campoIN, argumentosIN, argumentos, ordem);
			for (HistoricoXml object : lstDados) {
				objHistorico = (HISTORICO) xstream.fromXML(comp.descompactar(object.getObjeto()));
				objHistorico.setIdHistorico(object.getCodigo());
				lstHistorico.add(objHistorico);
			}
		}
		return lstHistorico;
	}
	
        /**
         *
         * @param usuario
         * @param inicio
         * @param fim
         * @param tipoHistorico
         * @param codigos
         * @return
         * @throws ECARException
         */
        public List<HistoricoXml> listaHistorico(UsuarioUsu usuario, Date inicio, Date fim, String[] tipoHistorico, String[] codigos) throws ECARException {
		List<HistoricoXml> lstDados = new ArrayList<HistoricoXml>();
		List<HISTORICO> lstHistorico = new ArrayList<HISTORICO>();
		Compactador comp = new Compactador();
		XStream xstream = new XStream();
		HistoricoXml xml = new HistoricoXml();
		HistoricoDao dao = new HistoricoDao(null);
		String campoIn = null;
		List<Long> argumentosIn = new ArrayList<Long>(); 
		String[] ordem = new String[] { "historico", "asc",  "idObjetoSerializado", "asc", "dataHistorico", "desc" };
		Map<String, Object> argumentos = new HashMap<String, Object>();
		
		if (tipoHistorico != null){
			campoIn = "tipoHistorico";
			for (String cod : tipoHistorico)
				argumentosIn.add(Long.valueOf(cod));
		}
			
		
		if (codigos != null)
			for (String cod : codigos) {
				argumentos.put("idObjetoSerializado", Long.valueOf(cod));
			}
		if (usuario != null)
			argumentos.put("usuarioUsu", usuario);
		if (objHistorico != null) {
			argumentos.put("historico", objHistorico.getClass().getName());

		if (campoIn != null & argumentosIn != null)
			if (inicio != null & fim != null)
					lstDados = dao.buscar(xml.getClass(), "dataHistorico", inicio, fim, campoIn, argumentosIn, argumentos, ordem);
				else
					lstDados = dao.buscar(xml.getClass(), null, null, null, campoIn, argumentosIn, argumentos, ordem);

		}
		return lstDados;
	}
	
        /**
         *
         * @param chaveHistorico
         * @return
         * @throws ECARException
         */
        public HISTORICO getObjetoSerializado(Long chaveHistorico) throws ECARException {
		Compactador comp = new Compactador();
		XStream xstream = new XStream();
		HistoricoXml xml = getHistorico(chaveHistorico);
		HISTORICO pojo = ((HISTORICO) xstream.fromXML(comp.descompactar(xml.getObjeto())));
		return pojo;
	}
	
	private HISTORICO getObjetoSerializado(HistoricoXml historico) throws ECARException {
		Compactador comp = new Compactador();
		XStream xstream = new XStream();
		HISTORICO pojo = ((HISTORICO) xstream.fromXML(comp.descompactar(historico.getObjeto())));
		return pojo;
	}
	
        /**
         *
         * @return
         */
        public boolean limpar(){
		HistoricoDao dao = new HistoricoDao(null);
		List<HistoricoXml> lista = null;
		try {
			lista = listaHistorico(null, null);
			if (lista != null)
				dao.excluir(lista);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
