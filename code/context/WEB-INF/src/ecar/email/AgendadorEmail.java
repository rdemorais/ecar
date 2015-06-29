
package ecar.email;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.hibernate.Session;

import comum.database.HibernateUtil;
import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;

import ecar.dao.AcompRealFisicoDao;
import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.AcompRelatorioDao;
import ecar.dao.ConfigMailCfgmDAO;
import ecar.dao.ConfiguracaoDao;
import ecar.dao.EmpresaDao;
import ecar.dao.EstruturaDao;
import ecar.dao.EstruturaFuncaoDao;
import ecar.dao.FuncaoDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.PontoCriticoDao;
import ecar.dao.ServicoDao;
import ecar.dao.TfuncacompConfigmailTfacfgmDAO;
import ecar.dao.UsuarioDao;
import ecar.evento.Evento;
import ecar.evento.EventoVencimentoDataInicioItem;
import ecar.evento.EventoVencimentoDataReserva1;
import ecar.evento.EventoVencimentoDataReserva2;
import ecar.evento.EventoVencimentoDataReserva3;
import ecar.evento.EventoVencimentoDataReserva4;
import ecar.evento.EventoVencimentoDataReserva5;
import ecar.evento.EventoVencimentoDataTerminoItem;
import ecar.evento.EventoVencimentoLimiteFisico;
import ecar.evento.EventoVencimentoLimiteParecer;
import ecar.evento.EventoVencimentoLimitePontoCritico;
import ecar.evento.URLEvento;
import ecar.exception.ECARException;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompRefItemLimitesArli;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.ConfigMailCfgm;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EmpresaEmp;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.PontoCriticoPtc;
import ecar.pojo.PontocriticoCorPtccor;
import ecar.pojo.ServicoSer;
import ecar.pojo.TfuncacompConfigmailTfacfgm;
import ecar.pojo.TfuncacompConfigmailTfacfgmPK;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;
import ecar.pojo.intercambioDados.funcionalidade.PerfilIntercambioDadosCadastroPidc;
import ecar.util.Dominios;

/**
 * @author N/C
 * @since N/C
 * @version N/C
 */
public class AgendadorEmail extends TimerTask {
	
	private static AgendadorEmail instance = null;
	private final static long INTERVALO = 1000*60*60*24;//a cada 24 horas ...
	
	private Timer timer = null;	
	private Date dataAtual;
	private String hora = "03:00";
	
	private String nomeSistema = "";
	private String contextPath = "";
	private String cabecalho = "";
	//private String emailContatoEmpresa = "";
	private String emailErroEmpresa = "";
	private String link = "";
	
        /**
         *
         */
        public final static String LABEL_WHO_CHANGE_ALTERACAO = "Alteração efetuada por:";
        /**
         *
         */
        public final static String LABEL_WHO_CHANGE_LIBERACAO = "Liberação de acompanhamento efetuada por:";
        /**
         *
         */
        public final static String LABEL_WHO_CHANGE_RECUPERACAO = "Recuperação de acompanhamento efetuada por:";
	
        /**
         *
         */
        public AgendadorEmail()	{}
	

	/**
	 * Singleton!
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return AgendadorEmail
	 */
	// Singleton!
	public static AgendadorEmail getInstance() {
		if(instance == null) {
		   instance = new AgendadorEmail();
		}
		return instance;		
	}
	
	/**
	 * Atualiza cabecalho de email.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @throws ECARException
	 */
	private void atualizaCabecalhos() throws ECARException {
		
		ConfiguracaoCfg configCfg = new ConfiguracaoDao(null).getConfiguracao();
		
		// Configuracoes gerais dos e-mails (cabecalho, figuras, nome do sistema...)
		this.nomeSistema = "Sistema " + configCfg.getTituloSistema();
		this.contextPath = configCfg.getContextPath();
		//this.emailContatoEmpresa = "";
		
		String logotipo = "";
		List listEmpresa = new ArrayList();
		listEmpresa = new EmpresaDao(null).listar(EmpresaEmp.class, null);
		if(listEmpresa != null && !listEmpresa.isEmpty()){
			EmpresaEmp emp = (EmpresaEmp) listEmpresa.iterator().next();
			logotipo = emp.getLogotipoEmailEmp();
			//this.emailContatoEmpresa = emp.getEmailContatoEmp(); 
			this.emailErroEmpresa = emp.getEmailErrosEmp(); 
		}
				
		this.cabecalho = contextPath + "/DownloadFile?tipo=open&downloadEmail=S&RemoteFile=" +  configCfg.getRaizUpload() + logotipo;
	}

	/**
	 * Método chamado pelo Agendador Listener para que tudo comece.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void disparaTimer(){
		try {
			ConfiguracaoCfg configCfg = new ConfiguracaoDao(null).getConfiguracao();
			
			this.atualizaCabecalhos();
		
    		if(configCfg.getEmailServer() != null && !"".equals(configCfg.getEmailServer().trim())) {
				if (configCfg.getHoraEnvioMailCfg() == null)
					this.hora = "03:00";
				else
					this.hora = configCfg.getHoraEnvioMailCfg().trim();

				this.dataAtual = Data.getDataAtual();
				String data = Data.parseDateHour(this.dataAtual).substring(0,10) + " " + this.hora + ":00:000";
				Date dataExecucao;
				if (Data.isPassado(Data.parseDateHour(data)))
					dataExecucao = Data.addDias(1, this.dataAtual);
				else
					dataExecucao = this.dataAtual;
					
				data = Data.parseDateHour(dataExecucao);
				data = data.substring(0,10) + " " + this.hora + ":00:000";
				
				dataExecucao = Data.parseDateHour(data);
				
				/* ----Igor!----
				 * Deixa o timer como uma espécie de "singleton". Isto permite que usemos
				 * um só timer, que pode ter sua hora alterada na próxima iteração do run()
				 */
				if (timer == null)
					timer = new Timer();
		
				/* Chama o agendador, iniciando em dataAtual e repetindo a cada INTERVALO de tempo
				   Em nosso caso, a data de inicio seria no dia seguinte, no horario cadastrado
				   nas configuracoes, repetindo a cada 24 hs...
				 */	
				timer.scheduleAtFixedRate(this, dataExecucao, AgendadorEmail.INTERVALO);
				// new Date(System.currentTimeMillis())
//				timer.scheduleAtFixedRate(this, new Date(System.currentTimeMillis()), AgendadorEmail.INTERVALO);
			} else {
    			throw new ECARException("erro.servidor.email.invalido");
			}
		} catch (Exception e){
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			this.enviarEmailExcecao("AgendadorEmail.disparaTimer()", e);
		} finally {
			try {
				// fechar a sessão
				HibernateUtil.closeSession();
			} catch (Exception e) {
				org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
				this.enviarEmailExcecao("AgendadorEmail.disparaTimer() - HibernateUtil.closeSession()", e);
			}
		}
		
	}

	/**
	 * Implementação do Método run() da classe abstrata TimerTask.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void run() {
		org.apache.log4j.Logger.getLogger(this.getClass()).info("Enviando e-mails em " + Data.getDataAtual() + " " + Data.getHoraAtual(true));
		
		
		try {
			
			gravarValorRealizadoMetasIndicadores();
			
			
 			ConfiguracaoDao confDao = new ConfiguracaoDao(null);
			ConfiguracaoCfg configuracao =  confDao.getConfiguracao();			
			ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
			List listConfigMailCfgm = configMailCfgmDAO.getMailsAtivos();
			
			this.atualizaCabecalhos();
			
			/*			
 			// Acertar a hora de envio de acordo com as configuracoes gerais
			// se este foi alterado após a última execução.
			if (!this.hora.equals(configuracao.getHoraEnvioMailCfg()))
			{
				this.dataAtual = Data.getDataAtual();
				String data = Data.parseDateHour(this.dataAtual).substring(0,10) + " " + this.hora + ":00:000";
				Date dataExecucao;
				if (Data.isPassado(Data.parseDateHour(data)))
					dataExecucao = Data.addDias(1, this.dataAtual);
				else
					dataExecucao = this.dataAtual;
					
				data = Data.parseDateHour(dataExecucao);
				data = data.substring(0,10) + " " + this.hora + ":00:000";
				
				dataExecucao = Data.parseDateHour(data);

				timer = null;
				timer = new Timer();
				timer.scheduleAtFixedRate(this, dataExecucao, AgendadorEmail.INTERVALO);
			}
			*/	
			
			Long diasAnt;
			Iterator it = listConfigMailCfgm.iterator();
		
			String dataLimStr = null;
						
			while( it.hasNext() ) {
				ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) it.next();
				Date dataCorr = Data.addDias(0, configuracao.getUltimoEnvioEmailCfg());
				
				if ((configuracao.getDiasAntecedenciaMailCfg().intValue() <= 0) 
						|| ("".equals(configuracao.getDiasAntecedenciaMailCfg().toString()))
						|| (configuracao.getDiasAntecedenciaMailCfg() == null))
				{
					diasAnt = Long.valueOf("1");
				}
				else
				{
					diasAnt = configMailCfgm.getConfiguracaoCfg().getDiasAntecedenciaMailCfg();
				}
						
				Date data = Data.parseDateHour(Data.parseDateHour(Data.getDataAtual()).substring(0,10) + " 00:00:00:000");
				
				while (dataCorr.compareTo(data) < 0)
				{
					dataCorr = Data.addDias(1, dataCorr);
					Date dataLim = Data.addDias(diasAnt.intValue(), dataCorr);
					dataLimStr = Data.parseDate(dataLim);
					
					switch( configMailCfgm.getCodCfgm().intValue() ) {
						case 4: // vcto limite fisico					
							eMailVenctoLimiteFisico(dataLimStr, configMailCfgm, dataLim);
							break;
						case 5: // vcto limite parecer										
							eMailVenctoParecer(dataLimStr, configMailCfgm, dataLim);
							break;
						case 6: // vcto limite ponto critico			
							eMailVenctoPontoCritico(dataCorr, configMailCfgm, dataLimStr);
							break;
						//case 27: // status indicador resultado	-> Falta definições 		
							//eMailStatusIndicadorResultado(dataCorr, configMailCfgm);
						//	break;	
							
					} // fim switch
					
					/* -- Trata dos tipo de 7 a 13 -- */
					if( configMailCfgm.getCodCfgm().intValue() > 6 && configMailCfgm.getCodCfgm().intValue() < 14 ) {
						eMailDataItem(dataLimStr, configMailCfgm);
					}

				} // fim do while (dias)
			}
			configuracao.setUltimoEnvioEmailCfg(Data.getDataAtual());		
			confDao.alterar(configuracao);
			org.apache.log4j.Logger.getLogger(this.getClass()).info("E-mails enviados em " + Data.getDataAtual() + " " + Data.getHoraAtual(true));
		} catch(Exception e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			this.enviarEmailExcecao("AgendadorEmail.run()", e);
		} finally {	
			try {
				// fechar a sessão 
				HibernateUtil.closeSession();
			} catch(Exception e) {
				org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
				this.enviarEmailExcecao("AgendadorEmail.run() - HibernateUtil.closeSession()", e);
			}
		}
	}	
	
	
	/**
	 * Monta E-mail.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param textoEmail
         * @param nomeResp
         * @param item
         * @param dataLimite
         * @param tipoAcompanhamento
         * @param link
         * @param eventDesc
	 * @return StringBuffer
	 * @throws ECARException
	 */
	public StringBuffer montaEmailComLinkDescricao(String textoEmail, String nomeResp, Long item, String dataLimite, 
			String tipoAcompanhamento, String link, String eventDesc) throws ECARException {
		ItemEstruturaDao iettDao = new ItemEstruturaDao(null);
		ItemEstruturaIett iett = (ItemEstruturaIett) iettDao.buscar(ItemEstruturaIett.class, item);
		return this.makeMailComLink(nomeResp, textoEmail, null, dataLimite, null, eventDesc, iett, null, tipoAcompanhamento, link);
	}

	
	/**
	 * Monta E-mail.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param textoEmail
         * @param nomeResp
         * @param item
         * @param dataLimite
         * @param tipoAcompanhamento
         * @param link
         * @return StringBuffer
	 * @throws ECARException
	 */
	public StringBuffer montaEmailComLink(String textoEmail, String nomeResp, Long item, String dataLimite, 
			String tipoAcompanhamento, String link) throws ECARException {
		ItemEstruturaDao iettDao = new ItemEstruturaDao(null);
		ItemEstruturaIett iett = (ItemEstruturaIett) iettDao.buscar(ItemEstruturaIett.class, item);
		return this.makeMailComLink(nomeResp, textoEmail, null, dataLimite, null, null, iett, null, tipoAcompanhamento, link);

	}
	
	/**
	 * 
	 * Monta E-mail.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param textoEmail
         * @param nomeResp
         * @param quemAlterou
         * @param item
         * @param descEvent
         * @param anterior 
         * @param atual
         * @param labelQuemAlterou
         * @param tipoAcompanhamento
         * @param link
         * @return StringBuffer
	 * @throws ECARException
	 */
	public StringBuffer montaEmailComLink(String textoEmail, String nomeResp, String quemAlterou, Long item, 
			String descEvent, String anterior, String atual, String labelQuemAlterou, String tipoAcompanhamento, String link) throws ECARException {
		/* --
		 * Utilizado por: ctrlDatasLimites.jsp
		 * -- */
		ItemEstruturaDao iettDao = new ItemEstruturaDao(null);
		ItemEstruturaIett iett = (ItemEstruturaIett) iettDao.buscar(ItemEstruturaIett.class, item);
		return this.makeMailComLink(nomeResp, textoEmail, quemAlterou, anterior, atual, descEvent, iett, labelQuemAlterou, tipoAcompanhamento, link);
	}
	
	
	
	
	/**
	 * Monta E-mail.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param textoEmail
         * @param nomeResp
         * @param item
         * @param dataLimite
         * @param tipoAcompanhamento
	 * @return StringBuffer
	 * @throws ECARException
	 */
	public StringBuffer montaEmail(String textoEmail, String nomeResp, Long item, String dataLimite, 
			String tipoAcompanhamento) throws ECARException {
		/* --
		 * Utilizado por: AgendadorEmail.java (envio automatico)
		 * -- */
		ItemEstruturaDao iettDao = new ItemEstruturaDao(null);
		ItemEstruturaIett iett = (ItemEstruturaIett) iettDao.buscar(ItemEstruturaIett.class, item);
		return this.makeMailComLink(nomeResp, textoEmail, null, dataLimite, null, null, iett, null, tipoAcompanhamento, link);
	}

	/**
	 * 
	 * Monta E-mail.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param textoEmail
         * @param nomeResp
         * @param quemAlterou
         * @param item
         * @param descEvent
         * @param anterior
         * @param atual
         * @param labelQuemAlterou
         * @param tipoAcompanhamento
         * @return StringBuffer
	 * @throws ECARException
	 */
	public StringBuffer montaEmail(String textoEmail, String nomeResp, String quemAlterou, Long item, 
			String descEvent, String anterior, String atual, String labelQuemAlterou, String tipoAcompanhamento) throws ECARException {
		/* --
		 * Utilizado por: ctrlDatasLimites.jsp
		 * -- */
		ItemEstruturaDao iettDao = new ItemEstruturaDao(null);
		ItemEstruturaIett iett = (ItemEstruturaIett) iettDao.buscar(ItemEstruturaIett.class, item);
		return this.makeMail(nomeResp, textoEmail, quemAlterou, anterior, atual, descEvent, iett, labelQuemAlterou, tipoAcompanhamento);
	}
	
	/**
	 * 
	 * @param textoEmail
	 * @param nomeResp
	 * @return
	 * @throws ECARException
	 */
	public StringBuffer montaEmail(String textoEmail, String nomeResp) throws ECARException{
		return this.makeMail(nomeResp, textoEmail, null, null, null, null, null, null, null);
	}
	
	/**
	 * Monta o cabeçalho padrão do e-mail.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param String nameTo - nome do responsável que receberá o e-mail
	 * @return String HTML
	 */
	private String makeHeaderMail(String nameTo) throws ECARException{
		String header = new String();
		header = "<html><head><title>E-Mail Automatico</title></head><body style=\"{font-family: verdana, arial; font-size: 10px; color: #000;}\">";
		header = header + "<img src=\""+ this.cabecalho + "\">";
		header = header + "<p>Caro Sr(a). " + nameTo + ",</p><br>";
		
		return header;
	}
	
	/**
	 * Monta o rodapé padrão do e-mail.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param String eventDesc
	 * @return String - (HTML)
	 * @throws ECARException
	 */
	private String makeFooterMail(String eventDesc) throws ECARException{
		String footer = new String();
	
		footer = "<p><i>Esta mensagem foi gerada automaticamente pelo " + this.nomeSistema + ", <b>favor não responder</b>.";
		if( eventDesc != null ) 
			footer = footer + " (" + eventDesc + ")";
		footer = footer + "</i></p>";
		footer = footer + "</body></html>";
		
		return footer;
	}
	
	/**
	 * Monta o e-mail completo, conforme os dados que são passados.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param String nameTo 			- Nome de quem receberá o e-mail
	 * @param String bodyText 			- Texto padrão (TextoSite)
	 * @param String whoChange 			- Nome de quem alterou o item 
	 * @param String original			- Data de referência
	 * @param String novo				- Nova data de referência (caso seja uma alteração de data)
	 * @param String eventDesc			- Descrição do evento
	 * @param ItemEstruturaIett iett	- Item que foi alterado
	 * @param String labelWhoChange		- Label a ser adicionado antes do nome de quem alterou
	 * @param String tipoAcompanhamento
	 * @return StringBuffer - (String HTML 	- para ser enviado por e-mail)
	 * @throws ECARException
	 */
	private StringBuffer makeMail(String nameTo, String bodyText, String whoChange, 
								  String original, String novo, String eventDesc, 
								  ItemEstruturaIett iett, String labelWhoChange, String tipoAcompanhamento) throws ECARException {
		this.atualizaCabecalhos();
		StringBuffer html = new StringBuffer();
		
		html.append(this.makeHeaderMail(nameTo));
		html.append("<p>").append(bodyText).append("</p>");
		html.append("<p>");
		
		if( original != null && novo != null )
			html.append("Anterior: ").append(original).append(" / Novo: ").append(novo).append("<br><br>");
		else if( original != null ) 
			html.append("Referência: ").append(original).append("<br><br>");
		if( tipoAcompanhamento != null && !"".equals(tipoAcompanhamento.trim())) 
			html.append("Tipo do acompanhamento: ").append(tipoAcompanhamento).append("<br><br>");
		if( labelWhoChange != null && whoChange != null ) 
			html.append(labelWhoChange).append(" ").append(whoChange).append("<br><br>");
		if( iett != null ) {
			ItemEstruturaDao itemEstruturaDAO = new ItemEstruturaDao(null);
			
			List listAscendentes = itemEstruturaDAO.getAscendentes(iett);
			listAscendentes.add(iett);
			Iterator it = listAscendentes.iterator();
			
			while( it.hasNext() ) {
				ItemEstruturaIett itemIett = (ItemEstruturaIett) it.next();
				
				EstruturaDao estruturaDAO = new EstruturaDao(null);
				EstruturaEtt estruturaItens = (EstruturaEtt)estruturaDAO.buscar(EstruturaEtt.class, itemIett.getEstruturaEtt().getCodEtt());
				String descricaoItem = itemEstruturaDAO.criaColunaConteudoColunaArvoreAjax(itemIett, estruturaItens);
				
				/* Identação pelo nível do item */
				for(int i = 1; i < itemIett.getNivelIett().intValue(); i++){
					html.append("<img src=\"").append(this.contextPath).append("/images/pixel.gif\" width=\"22\" height=\"9\">");
				}
				
				html.append("<img src=\"").append(this.contextPath).append("/images/icon_seta_ident.gif\">");
				html.append(itemIett.getEstruturaEtt().getNomeEtt()).append(" - ").append(descricaoItem);
				html.append("<br>");
			}
		}			
		html.append("</p>");
		html.append(this.makeFooterMail(eventDesc));
		
		return html;
	}
	
	
	/**
	 * Monta o e-mail completo, conforme os dados que são passados.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param String nameTo 			- Nome de quem receberá o e-mail
	 * @param String bodyText 			- Texto padrão (TextoSite)
	 * @param String whoChange 			- Nome de quem alterou o item 
	 * @param String original			- Data de referência
	 * @param String novo				- Nova data de referência (caso seja uma alteração de data)
	 * @param String eventDesc			- Descrição do evento
	 * @param ItemEstruturaIett iett	- Item que foi alterado
	 * @param String labelWhoChange		- Label a ser adicionado antes do nome de quem alterou
	 * @param String tipoAcompanhamento
	 * @param String link 			    - Link para o item
	 * @return StringBuffer - (String HTML 	- para ser enviado por e-mail)
	 * @throws ECARException
	 */
	private StringBuffer makeMailComLink(String nameTo, String bodyText, String whoChange, 
								  String original, String novo, String eventDesc, 
								  ItemEstruturaIett iett, String labelWhoChange, String tipoAcompanhamento, String link) throws ECARException {
		this.atualizaCabecalhos();
		StringBuffer html = new StringBuffer();
		
		html.append(this.makeHeaderMail(nameTo));
		html.append("<p>").append(bodyText).append("</p>");
		html.append("<p>");
		
		if( original != null && novo != null )
			html.append("Anterior: ").append(original).append(" / Novo: ").append(novo).append("<br><br>");
		else if( original != null ) 
			html.append("Referência: ").append(original).append("<br><br>");
		if( tipoAcompanhamento != null && !"".equals(tipoAcompanhamento.trim())) 
			html.append("Tipo do acompanhamento: ").append(tipoAcompanhamento).append("<br><br>");
		if( labelWhoChange != null && whoChange != null ) 
			html.append(labelWhoChange).append(" ").append(whoChange).append("<br><br>");
		if( iett != null ) {
			ItemEstruturaDao itemEstruturaDAO = new ItemEstruturaDao(null);
			
			List listAscendentes = itemEstruturaDAO.getAscendentes(iett);
			listAscendentes.add(iett);
			Iterator it = listAscendentes.iterator();
			
			while( it.hasNext() ) {
				ItemEstruturaIett itemIett = (ItemEstruturaIett) it.next();
				
				EstruturaDao estruturaDAO = new EstruturaDao(null);
				EstruturaEtt estruturaItens = (EstruturaEtt)estruturaDAO.buscar(EstruturaEtt.class, itemIett.getEstruturaEtt().getCodEtt());
				String descricaoItem = itemEstruturaDAO.criaColunaConteudoColunaArvoreAjax(itemIett, estruturaItens);
				
				/* Identação pelo nível do item */
				for(int i = 1; i < itemIett.getNivelIett().intValue(); i++){
					html.append("<img src=\"").append(this.contextPath).append("/images/pixel.gif\" width=\"22\" height=\"9\">");
				}
				
				html.append("<img src=\"").append(this.contextPath).append("/images/icon_seta_ident.gif\">");
				html.append(itemIett.getEstruturaEtt().getNomeEtt()).append(" - ").append(descricaoItem);
				html.append("<br>");
			}
		}			
		html.append("</p>");
		
		
		html.append("</p>");
		
		//somente manda acessar o item diretamente se tiver o link
		if(link != null && !link.equals("")){
			html.append("Para acessar o item diretamente e verificar este evento, clique no link abaixo: <br>");
			html.append("<a href=\"").append(link).append("\" >").append(link).append("</a><br><br>");
		}
		
		html.append("<p>");
		html.append(this.makeFooterMail(eventDesc));
				
		return html;
	} 

	
	/**
	* Enviar um e-mail.<br>
	* 
	* @author Cristiano
	* @since 13/12/2006
	* @version N/C
         * @param assunto
         * @param remetente
         * @param texto
         * @param destinatarioCc
         * @param destinatarioBcc
         * @param destinatarioPara
         * @param usuario
         * @throws ECARException
	*/
	public void enviarEmail(String assunto, String remetente, String texto, String destinatarioPara, String destinatarioCc, String destinatarioBcc, UsuarioUsu usuario) throws ECARException{
		try {
			Util.enviarEmail(assunto, this.nomeSistema, remetente, texto, destinatarioPara, destinatarioCc, destinatarioBcc, usuario);
			
    		org.apache.log4j.Logger.getLogger(this.getClass()).info(
    				"E-mail enviado (" + 
    				Data.getDataAtual() + " " + Data.getHoraAtual(true) + "): " +
    				" assunto: " + assunto + 
    				" - remetente: " + remetente +
    				" - destinatarioPara: " + destinatarioPara + 
    				" - destinatarioCc: " + destinatarioCc +
    				" - destinatarioBcc: " + destinatarioBcc
    				);
		} catch (AddressException ae) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(ae);
			this.enviarEmailExcecao("AgendadorEmail.enviarEmail()", ae);
			throw new ECARException("erro.email.invalido", ae);
		} catch (MessagingException me) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(me);
			this.enviarEmailExcecao("AgendadorEmail.enviarEmail()", me);
			throw new ECARException("erro.envio.email", me);
		} catch (ECARException ec) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(ec);
			this.enviarEmailExcecao("AgendadorEmail.enviarEmail()", ec);
			throw new ECARException(ec.getMessageKey(), ec);
		} catch (Exception e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			this.enviarEmailExcecao("AgendadorEmail.enviarEmail()", e);
			throw new ECARException("erro.envio.email", e);
		}
	}

	/**
	 * Envia e-mail avisando que o parecer esta vencendo.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param String dataLimStr
	 * @param ConfigMailCfgm configMailCfgm
	 * @throws ECARException
	 * @throws Exception
	 */
	private void eMailVenctoParecer (String dataLimStr, ConfigMailCfgm configMailCfgm, Date dataLimite) throws ECARException, Exception {
		try {
			AcompReferenciaItemDao ariDAO = new AcompReferenciaItemDao(null);
			UsuarioDao usuDAO = new UsuarioDao();
			TfuncacompConfigmailTfacfgmDAO tfuncacompConfigmailTfacfgmDAO = new TfuncacompConfigmailTfacfgmDAO();
			AcompRelatorioDao acompRelatorioDao = new AcompRelatorioDao(null);
			
			List listItens     = null;
			String dataCompStr = null;
			Iterator itItem    = null;		
			
			/* --
			 * Por Rogério (26/02/2007)
			 * Otimização de query.
			 * listItens = ariDAO.listar(AcompRefItemLimitesArli.class, new String[]{"dataLimiteArli","ASC"});
			 * itItem = listItens.iterator();
			 * -- */
			
			listItens = ariDAO.listarAcompReferenciaItemLimitesPorVenctoParecer(dataLimite);
			if( listItens != null ) itItem = listItens.iterator();
			
			/* -- OFinal da otimização -- */
			
			while( itItem.hasNext() ){
				AcompRefItemLimitesArli arli = (AcompRefItemLimitesArli) itItem.next();
				dataCompStr = Data.parseDate(arli.getDataLimiteArli());

				if( dataLimStr.equals(dataCompStr) ) {
					AcompReferenciaItemAri ari = arli.getAcompReferenciaItemAri();
					
					List listAri = new ArrayList();
					if( ari.getItemEstruturaIett() != null && ari.getItemEstruturaIett().getItemEstUsutpfuacIettutfas() != null )
						listAri = new ArrayList(ari.getItemEstruturaIett().getItemEstUsutpfuacIettutfas());
					
					List usuariosEmailEnviado = new ArrayList();
					
					String descricaoEvento = "Vencimento da Data limite para " + arli.getTipoFuncAcompTpfa().getLabelPosicaoTpfa() + ".";
					
					Iterator itList = listAri.iterator();
					while( itList.hasNext() ) {
						ItemEstUsutpfuacIettutfa itemEstUsutpfacIetutfa = (ItemEstUsutpfuacIettutfa) itList.next();
						TipoFuncAcompTpfa tipoFuncao = itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa(); 
						TfuncacompConfigmailTfacfgmPK tfcfgmPK = new TfuncacompConfigmailTfacfgmPK();
						tfcfgmPK.setCodCfgm(configMailCfgm.getCodCfgm());
						tfcfgmPK.setCodTpfa(tipoFuncao.getCodTpfa());
						TfuncacompConfigmailTfacfgm tfcfm = (TfuncacompConfigmailTfacfgm) tfuncacompConfigmailTfacfgmDAO.buscar(TfuncacompConfigmailTfacfgm.class, tfcfgmPK);
	
						//Verificando se já existe posição liberada
						//Caso já exista (indLiberadoArel) , não precisa mandar e-mail
						AcompRelatorioArel acompRelatorio = acompRelatorioDao.getAcompRelatorio(tipoFuncao, ari); 
						if ( acompRelatorio != null && !"S".equals(acompRelatorio.getIndLiberadoArel()) ) {
							
							if ( itemEstUsutpfacIetutfa != null && itemEstUsutpfacIetutfa.getComp_id() != null && "S".equals(tfcfm.getEnviaMailTfacfgm()) ) {
								
	    						
								//UsuarioUsu usu = (UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getComp_id().getCodUsu());
								List usuarios = new ArrayList();
								if (itemEstUsutpfacIetutfa.getUsuarioUsu() != null) {
									usuarios.add((UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getUsuarioUsu().getCodUsu()));
								} else if (itemEstUsutpfacIetutfa.getSisAtributoSatb() != null){
									usuarios.addAll(usuDAO.getUsuariosBySisAtributoSatb(itemEstUsutpfacIetutfa.getSisAtributoSatb()));
								}
								
								Iterator itUsu = usuarios.iterator();
								
								while (itUsu.hasNext()){
								
									UsuarioUsu usu = (UsuarioUsu) itUsu.next();	
								
									if(!usuariosEmailEnviado.contains(usu)) {
										usuariosEmailEnviado.add(usu);
										
										Long codIett = Long.valueOf(0);
										String textoMail = "";
										String assunto   = "";
										String remetente = "";
									
										if( configMailCfgm.getTextosSiteMail() != null ) {
											textoMail = configMailCfgm.getTextosSiteMail().getTextoTxts();
											assunto   = configMailCfgm.getTextosSiteMail().getDescricaoUsoTxts();
											remetente = configMailCfgm.getTextosSiteMail().getEmailResponsavelTxts();
										}
									
										if( ari.getItemEstruturaIett() != null )
											codIett = ari.getItemEstruturaIett().getCodIett();
													
										try{
											
											Evento evento = null;
											evento = new EventoVencimentoLimiteParecer();
											ConfiguracaoCfg configCfg = new ConfiguracaoDao(null).getConfiguracao();
										    String contextPath = configCfg.getContextPath();
										    String[] valores = new String[3];
										    
										    //"codTipoAcompanhamento",
										    valores[0] = ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getCodTa().toString();
										    // "referencia_hidden",
										    valores[1] = ari.getAcompReferenciaAref().getCodAref().toString();
										    // "codAri"
										    valores[2] = ari.getCodAri().toString();

										    link = URLEvento.montaURLEventoSemRequest(evento, contextPath, valores);
											
										    
											String html = "";
											if(descricaoEvento != null && !"".equals(descricaoEvento)){
												html = this.montaEmailComLinkDescricao(textoMail, usu.getNomeUsu(), codIett, dataLimStr, null, link, descricaoEvento).toString();
											} else{
												html = this.montaEmailComLink(textoMail, usu.getNomeUsu(), codIett, dataLimStr, null, link).toString();
											}
											//String html = this.montaEmailComLink(textoMail, usu.getNomeUsu(), codIett, dataLimStr, acompRelatorio.getAcompReferenciaItemAri().getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa(), link).toString();
											
											if ((usu.getEmail1Usu() != null) && (!"".equals(usu.getEmail1Usu()))) {
												Util.enviarEmail(assunto, this.nomeSistema, remetente, html, usu.getEmail1Usu(),"", "", usu);
																				
									    		org.apache.log4j.Logger.getLogger(this.getClass()).info(
									    				"E-mail enviado (" + 
									    				Data.getDataAtual() + " " + Data.getHoraAtual(true) + "): " +
									    				" assunto: " + assunto + 
									    				" - remetente: " + remetente +
									    				" - destinatarioPara: " + usu.getEmail1Usu()
									    				);
											} else {
												String erro = "Erro na tentativa de enviar e-mail para o usuario " + usu.getNomeUsu() + "(Cod: " + usu.getCodUsu() + ")<br> Verifique o e-mail e o nome do mesmo no cadastro de usuarios";
												Util.enviarEmail("[" + this.nomeSistema + "] Erro de Envio de Email", this.nomeSistema, remetente, erro, remetente, "", "", usu);
									    		org.apache.log4j.Logger.getLogger(this.getClass()).info(
									    				"E-mail enviado (" + 
									    				Data.getDataAtual() + " " + Data.getHoraAtual(true) + "): " +
									    				" assunto: " + assunto + 
									    				" - remetente: " + remetente +
									    				" - destinatarioPara: " + remetente
									    				);
											}					
										} catch (Exception e) {
											org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
											String erro = "Erro na tentativa de enviar e-mail para o usuario " + usu.getNomeUsu() + "(Cod: " + usu.getCodUsu() + ")<br> Verifique o e-mail e o nome do mesmo no cadastro de usuarios";
											erro = erro + "<br>" + e.toString();
											Util.enviarEmail("[" + this.nomeSistema + "] Erro de Envio de Email", this.nomeSistema, remetente, erro, remetente, "", "",usu);
								    		org.apache.log4j.Logger.getLogger(this.getClass()).info(
								    				"E-mail enviado (" + 
								    				Data.getDataAtual() + " " + Data.getHoraAtual(true) + "): " +
								    				" assunto: " + assunto + 
								    				" - remetente: " + remetente +
								    				" - destinatarioPara: " + remetente
								    				);
											this.enviarEmailExcecao("AgendadorEmail.eMailVenctoParecer() (1)", e);
										}
									}
								}
							}
							
							if ("S".equals(tfcfm.getEnviaSms())) {
								// envia sms
							}
						}
					}
					
				}
			}	
		} catch(Exception e) {
			this.enviarEmailExcecao("AgendadorEmail.eMailVenctoParecer() (2)", e);
			throw(e);
		}
	}
	
	/**
	 * Envia o e-mail para aviso do vencimento limite do parecer do realizado físico. <br>
	 * 
	 * @author n/c, rogerio
	 * @since 0.1, n/c
	 * @version 0.2, 23/02/2007
	 * @param String dataLimStr
	 * @param ConfigMailCfgm configMailCfgm
	 * @throws ECARException
	 * @throws Exception
	 */
	private void eMailVenctoLimiteFisico(String dataLimStr, ConfigMailCfgm configMailCfgm, Date dataLimite) throws ECARException, Exception {
		try {
			AcompReferenciaItemDao ariDAO = new AcompReferenciaItemDao(null);
			TfuncacompConfigmailTfacfgmDAO tfuncacompConfigmailTfacfgmDAO = new TfuncacompConfigmailTfacfgmDAO();
			AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(null);
			UsuarioDao usuDAO = new UsuarioDao();
			
			List listItens     = null;
			String dataCompStr = null;
			Iterator itItem    = null;				
	
			listItens = ariDAO.listarAcompReferenciaItemPorDataLimiteFisico(dataLimite);
			if( listItens != null ) itItem = listItens.iterator();
			
			/* -- Fim da otimização -- */
			
			while( itItem.hasNext() ){
				AcompReferenciaItemAri ari = (AcompReferenciaItemAri) itItem.next();
	
				dataCompStr = Data.parseDate(ari.getDataLimiteAcompFisicoAri());
				if( dataLimStr.equals(dataCompStr) &&    // compara as datas e envia somente para os itens ativos  
						(ari != null && ari.getItemEstruturaIett() != null && ari.getItemEstruturaIett().getIndAtivoIett().equals("S"))) { 
					
					
					List list = new ArrayList(ari.getItemEstruturaIett().getItemEstUsutpfuacIettutfas());
					
					String descricaoEvento = "Vencimento da Data limite Físico.";
					
					List usuariosEmailEnviado = new ArrayList();
					Iterator itList = list.iterator();
					
					while( itList.hasNext() ) {
						ItemEstUsutpfuacIettutfa itemEstUsutpfacIetutfa = (ItemEstUsutpfuacIettutfa) itList.next();
						TfuncacompConfigmailTfacfgmPK tfcfgmPK = new TfuncacompConfigmailTfacfgmPK();
						tfcfgmPK.setCodCfgm(configMailCfgm.getCodCfgm());
						tfcfgmPK.setCodTpfa(itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa().getCodTpfa());
						
						TfuncacompConfigmailTfacfgm tfcfm = (TfuncacompConfigmailTfacfgm) tfuncacompConfigmailTfacfgmDAO.buscar(TfuncacompConfigmailTfacfgm.class, tfcfgmPK);
	
						List acompRealFisicoList = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari, Dominios.TODOS, false);
						Iterator itArf = acompRealFisicoList.iterator();
						boolean faltaArf = false;
						while (itArf.hasNext())
						{
							AcompRealFisicoArf arf = (AcompRealFisicoArf) itArf.next();
							if (arf.getQtdRealizadaArf() == null)
								faltaArf = true;
						}
						
						List novosIndicadores = ariDAO.getNovosIndicadores(ari);
						Iterator itIndicadores = novosIndicadores.iterator();
						if(itIndicadores.hasNext()){
							faltaArf = true;
						}
						
						
						//depois apagar o item
						if (("S".equals(tfcfm.getEnviaMailTfacfgm())) && (faltaArf)) {

							//UsuarioUsu usu = (UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getComp_id().getCodUsu());
														
							List usuarios = new ArrayList();
							if (itemEstUsutpfacIetutfa.getUsuarioUsu() != null) {
								usuarios.add((UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getUsuarioUsu().getCodUsu()));
							} else if (itemEstUsutpfacIetutfa.getSisAtributoSatb() != null){
								usuarios.addAll(usuDAO.getUsuariosBySisAtributoSatb(itemEstUsutpfacIetutfa.getSisAtributoSatb()));
							}
							
							Iterator itUsu = usuarios.iterator();
							
							while (itUsu.hasNext()){
							
								UsuarioUsu usu = (UsuarioUsu) itUsu.next();
							
						
								if(!usuariosEmailEnviado.contains(usu)) {
									usuariosEmailEnviado.add(usu);
	
									Long   codIett	 = Long.valueOf(0);
									String textoMail = "";
									String assunto   = "";
									String remetente = "";
								
									if( configMailCfgm.getTextosSiteMail() != null ) {
										textoMail = configMailCfgm.getTextosSiteMail().getTextoTxts();
										assunto   = configMailCfgm.getTextosSiteMail().getDescricaoUsoTxts();
										remetente = configMailCfgm.getTextosSiteMail().getEmailResponsavelTxts();
									}
								
									if( ari.getItemEstruturaIett() != null )
										codIett = ari.getItemEstruturaIett().getCodIett();
												
									try {
										
										Evento evento = null;
										evento = new EventoVencimentoLimiteFisico();
										ConfiguracaoCfg configCfg = new ConfiguracaoDao(null).getConfiguracao();
									    String contextPath = configCfg.getContextPath();
									    String[] valores = new String[3];//codAri, codTipoAcompanhamento
									   // "codTipoAcompanhamento",
									    valores[0] = ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getCodTa().toString();
									    //"referencia_hidden",
									    valores[1] = ari.getAcompReferenciaAref().getCodAref().toString();
                       				 	//"codAri"
									    valores[2] = ari.getCodAri().toString();
									    
									    
									    
									
									    link = URLEvento.montaURLEventoSemRequest(evento, contextPath, valores);
										
										String html = "";
										if(descricaoEvento != null && !"".equals(descricaoEvento)){
											html = this.montaEmailComLinkDescricao(textoMail, usu.getNomeUsu(), codIett, dataLimStr, null, link, descricaoEvento).toString();
										} else{
											html = this.montaEmailComLink(textoMail, usu.getNomeUsu(), codIett, dataLimStr, null, link).toString();
										}
										//String html = this.montaEmailComLink(textoMail, usu.getNomeUsu(), codIett, dataLimStr, ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa(), link).toString();
										if ((usu.getEmail1Usu() != null) && (!"".equals(usu.getEmail1Usu()))) {
											Util.enviarEmail(assunto, this.nomeSistema, remetente, html, usu.getEmail1Usu(),"", "", usu);
																		
								    		org.apache.log4j.Logger.getLogger(this.getClass()).info(
								    				"E-mail enviado (" + 
								    				Data.getDataAtual() + " " + Data.getHoraAtual(true) + "): " +
								    				" assunto: " + assunto + 
								    				" - remetente: " + remetente +
								    				" - destinatarioPara: " + usu.getEmail1Usu()
								    				);
										} else {
											String erro = "Erro na tentativa de enviar e-mail para o usuario " + usu.getNomeUsu() + "(Cod: " + usu.getCodUsu() + ")<br> Verifique o e-mail e o nome do mesmo no cadastro de usuarios";
											Util.enviarEmail("["+ this.nomeSistema + "] Erro de Envio de Email", this.nomeSistema, remetente, erro, remetente, "", "", usu);
								    		org.apache.log4j.Logger.getLogger(this.getClass()).info(
								    				"E-mail enviado (" + 
								    				Data.getDataAtual() + " " + Data.getHoraAtual(true) + "): " +
								    				" assunto: " + assunto + 
								    				" - remetente: " + remetente +
								    				" - destinatarioPara: " + remetente
								    				);
										}
									}catch (Exception e) {
										org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
										String erro = "Erro na tentativa de enviar e-mail para o usuario " + usu.getNomeUsu() + "(Cod: " + usu.getCodUsu() + ")<br> Verifique o e-mail e o nome do mesmo no cadastro de usuarios";
										erro = erro + "<br>" + e.toString();
										Util.enviarEmail("["+ this.nomeSistema + "] Erro de Envio de Email", this.nomeSistema, remetente, erro, remetente, "", "", usu);
							    		org.apache.log4j.Logger.getLogger(this.getClass()).info(
							    				"E-mail enviado (" + 
							    				Data.getDataAtual() + " " + Data.getHoraAtual(true) + "): " +
							    				" assunto: " + assunto + 
							    				" - remetente: " + remetente +
							    				" - destinatarioPara: " + remetente
							    				);
										this.enviarEmailExcecao("AgendadorEmail.eMailVenctoLimiteFisico() (1)", e);
									}
								}
							}
						}
						
						if ("S".equals(tfcfm.getEnviaSms())) {
							// envia sms
						}
					}
				}
			}
		} catch(Exception e) {
			this.enviarEmailExcecao("AgendadorEmail.eMailVenctoLimiteFisico() (2)", e);
			throw(e);
		}
	}
		
	/**
	 * Envia o e-mail para aviso dos indicadores do projeto aos responsáveis
	 * @param dataCorrente
	 * @param configMailCfgm
	 * @throws ECARException
	 * @throws Exception
	 */
	
	//FALTA DEFINIÇÃO DE COMO DEVERÁ SER IMPLEMENTADO (ACORDADO COM BEIER)
	/*private void eMailStatusIndicadorResultado (Date dataCorrente, ConfigMailCfgm configMailCfgm) throws ECARException, Exception {
		try {
			ItemEstrtIndResulDao iettrDao = new ItemEstrtIndResulDao(null);
			ItemEstrtIndResulCorIettrcorDAO  iettrcorDao = new ItemEstrtIndResulCorIettrcorDAO(null);
			AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(null);
			String link = null;
			Evento evento = null;
			ConfiguracaoCfg configCfg = new ConfiguracaoDao(null).getConfiguracao();
		    String contextPath = configCfg.getContextPath();
		    String[] valores = new String[3];//"codTipoAcompanhamento","referencia_hidden","codAri"
			
			TfuncacompConfigmailTfacfgmDAO tfuncacompConfigmailTfacfgmDAO = new TfuncacompConfigmailTfacfgmDAO();
			
			UsuarioDao usuDAO = new UsuarioDao();
			
			double valorIndicadorResultado = 0;
			
			List listItemEstrtIndResul = null;
			//String dataCompStr = null;
			Iterator itItemEstrtIndResul = null;
			String corRelogio = null;
			Set datasLimite = null;
				
			ItemEstrtIndResulIettr itemEstrtIndResulIettrFiltro = new ItemEstrtIndResulIettr();
			
			//apenas os indicadores q estao ativos e sinalizados
			itemEstrtIndResulIettrFiltro.setIndAtivoIettr("S");
			itemEstrtIndResulIettrFiltro.setIndSinalizacaoIettr("S");
			
			
			//pesquisa
			listItemEstrtIndResul = iettrDao.pesquisar(itemEstrtIndResulIettrFiltro, null);
			
			//percorre a lista de resultado
			if( listItemEstrtIndResul != null ){ 
				itItemEstrtIndResul = listItemEstrtIndResul.iterator();
				
			
				
				while(itItemEstrtIndResul.hasNext() ){
					ItemEstrtIndResulIettr iettr = (ItemEstrtIndResulIettr) itItemEstrtIndResul.next();
					
					//Retorna a lista de Iettrcor Ativos de acordo com o ItemEstrtIndResulIettr informado
					List iettrCors = iettrcorDao.listarIettrcorAtivosPorIettrOrderByValorPrimEmailIettrcor(iettr);
					 
					if (iettrCors != null){
						
						Iterator itIettrCors = iettrCors.iterator();
						boolean ehPrimeiro = true;
						
						while (itIettrCors.hasNext()){
							ItemEstrtIndResulCorIettrcor iettrcor = (ItemEstrtIndResulCorIettrcor) itIettrCors.next();
							
							
 							if (valorIndicadorResultado <= iettrcor.getValorPrimEmailIettrcor().doubleValue() && ehPrimeiro){
								
								//*******************************************************************
								//*************ROTINA DE ENVIO DE EMAIL******************************
								//*******************************************************************
								
								//pega a o item
								ItemEstruturaIett iett = (ItemEstruturaIett) iettr.getItemEstruturaIett() ;
								
								evento = new EventoStatusIndicadorResultado();
								
								link = URLEvento.montaURLEventoSemRequest(evento, contextPath, valores);
								
								ehPrimeiro = false;
								
								if( (iett != null) ) {						
														
									List usuariosEmailEnviado = new ArrayList();
					
									List listIett = new ArrayList(iett.getItemEstUsutpfuacIettutfas());
									
									Iterator itList = listIett.iterator();
									
									while( itList.hasNext() ) {
										ItemEstUsutpfuacIettutfa itemEstUsutpfacIetutfa = (ItemEstUsutpfuacIettutfa) itList.next();
										
										TfuncacompConfigmailTfacfgmPK tfcfgmPK = new TfuncacompConfigmailTfacfgmPK();
										tfcfgmPK.setCodCfgm(configMailCfgm.getCodCfgm());
										tfcfgmPK.setCodTpfa(itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa().getCodTpfa());
										
										TfuncacompConfigmailTfacfgm tfcfm = (TfuncacompConfigmailTfacfgm) tfuncacompConfigmailTfacfgmDAO.buscar(TfuncacompConfigmailTfacfgm.class, tfcfgmPK);
										
										if ("S".equals(tfcfm.getEnviaMailTfacfgm())) {
											
											//UsuarioUsu usu = (UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getComp_id().getCodUsu());
											
											List usuarios = new ArrayList();
											if (itemEstUsutpfacIetutfa.getUsuarioUsu() != null) {
												usuarios.add((UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getUsuarioUsu().getCodUsu()));
											} else if (itemEstUsutpfacIetutfa.getSisAtributoSatb() != null){
												usuarios.addAll(usuDAO.getUsuariosBySisAtributoSatb(itemEstUsutpfacIetutfa.getSisAtributoSatb()));
											}
											
											Iterator itUsu = usuarios.iterator();
											
											while (itUsu.hasNext()){
											
												UsuarioUsu usu = (UsuarioUsu) itUsu.next();
											
										
												if(!usuariosEmailEnviado.contains(usu)) {
													usuariosEmailEnviado.add(usu);
					
													Long   codIett = Long.valueOf(0);
													String textoMail = "";
													String assunto   = "";
													String remetente = "";
												
													if( configMailCfgm.getTextosSiteMail() != null ) {
														textoMail = configMailCfgm.getTextosSiteMail().getTextoTxts();
														assunto   = configMailCfgm.getTextosSiteMail().getDescricaoUsoTxts();
														remetente = configMailCfgm.getTextosSiteMail().getEmailResponsavelTxts();
													}
												
													codIett = iett.getCodIett();
																		
													try {
														String image = "";
														if(iettrcor.getCor().getCaminhoImagemPontoCriticoCor()!=null){
															image =" <img src=\"" + this.contextPath + "/DownloadFile?tipo=open&RemoteFile=" 
																	+ iettrcor.getCor().getCaminhoImagemPontoCriticoCor() + "\">"; 
														}
														else {
															image = "<img src=\"/images/pc"+iettrcor.getCor().getNomeCor()+"1.png\" title=\""+iettrcor.getCor().getSignificadoCor()+"\">";
														}
															
														textoMail +=  "<br>" ;
														textoMail +=  "Indicador: " + iettr.getNomeIettir() + "<br>";
														textoMail +=  "Valor: " + valorIndicadorResultado + "<br>";
														textoMail +=  "Status: " + iettrcor.getCor().getSignificadoCor() + image + "<br>";
														
														String html = this.montaEmailComLink( textoMail, usu.getNomeUsu(), null, codIett,
																"Alerta status indicadores.",
																null, null, null, null, link).toString();
														if ((usu.getEmail1Usu() != null) && (!"".equals(usu.getEmail1Usu())))
														{
															if ((assunto == null) || (assunto.length() <= 0)) {
																assunto = "Alerta de status de Indicadores";
															}
																						
															Util.enviarEmail(assunto, this.nomeSistema, remetente, html, usu.getEmail1Usu(),"", "", usu);
												    		org.apache.log4j.Logger.getLogger(this.getClass()).info(
												    				"E-mail enviado (" + 
 												    				Data.getDataAtual() + " " + Data.getHoraAtual(true) + "): " +
												    				" assunto: " + assunto + 
												    				" - remetente: " + remetente +
												    				" - destinatarioPara: " + usu.getEmail1Usu()
												    				);
														}	
														else
														{
															String erro = "Erro na tentativa de enviar e-mail para o usuario " + usu.getNomeUsu() + "(Cod: " + usu.getCodUsu() + ")<br> Verifique o e-mail e o nome do mesmo no cadastro de usuarios";
															Util.enviarEmail("["+ this.nomeSistema + "] Erro de Envio de Email", this.nomeSistema, remetente, erro, remetente, "", "", usu);
												    		org.apache.log4j.Logger.getLogger(this.getClass()).info(
												    				"E-mail enviado (" + 
												    				Data.getDataAtual() + " " + Data.getHoraAtual(true) + "): " +
												    				" assunto: " + assunto + 
												    				" - remetente: " + remetente +
												    				" - destinatarioPara: " + remetente
												    				);
														}
													}catch (Exception e) {
														org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
														String erro = "Erro na tentativa de enviar e-mail para o usuario " + usu.getNomeUsu() + "(Cod: " + usu.getCodUsu() + ")<br> Verifique o e-mail e o nome do mesmo no cadastro de usuarios";
														erro = erro + "<br>" + e.toString();
														Util.enviarEmail("["+ this.nomeSistema + "] Erro de Envio de Email", this.nomeSistema, remetente, erro, remetente, "", "", usu);
											    		org.apache.log4j.Logger.getLogger(this.getClass()).info(
											    				"E-mail enviado (" + 
											    				Data.getDataAtual() + " " + Data.getHoraAtual(true) + "): " +
											    				" assunto: " + assunto + 
											    				" - remetente: " + remetente +
											    				" - destinatarioPara: " + remetente
											    				);
														this.enviarEmailExcecao("AgendadorEmail.eMailStatusIndicadorResultado() (1)", e);
													}							
												}
											}
										}
										
										if ("S".equals(tfcfm.getEnviaSms())) {
										// envia sms
										}
								}
									
								}
								
								//*************FIM DA ROTINA DE ENVIO DE EMAIL******************************
							}
							
						}
					}
				}
			}
		} catch(Exception e) {
			this.enviarEmailExcecao("AgendadorEmail.eMailStatusIndicadorResultado() (2)", e);
			throw(e);
		}
	}*/
					
//	A gravação do valor preenchido neste campo de Realizado ocorrerá quando a 
//	rotina (diária) de datas críticas que envia email for realizada.
//	Para isto, deverá ser acrescentada a esta rotina a seguinte 
//	verificação: a partir dos períodos de acompanhamento gerados, 
//	verificar quais os que: 
//		(*) têm a data limite para realizado físico igual a data de hoje, e 
//		(**) quais os indicadores que estão cadastrados para o valor realizado ser automático. 
//	Uma vez encontrados os indicadores que se encaixam nessa verificação, gravar o valor apurado pelo serviço.
	
	/**
	 * Grava o valor realizado das metas/indicadores. <br>
	 * 
	 * @author 
	 * @throws ECARException
	 */
	private void gravarValorRealizadoMetasIndicadores() throws ECARException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		AcompReferenciaItemAri ariFiltro = new AcompReferenciaItemAri();
		ServicoDao servicoDao = new ServicoDao(null);
		AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(null);
		
		Date dataAtual = Data.getDataAtual();
		Calendar calendarDataAtual = Data.getCalendar(dataAtual);
		Calendar calendarDataAtualSemHMS = new GregorianCalendar(calendarDataAtual.get(Calendar.YEAR), calendarDataAtual.get(Calendar.MONTH), calendarDataAtual.get(Calendar.DAY_OF_MONTH));
		
		Date dataOntem = Data.addDias(-1, calendarDataAtualSemHMS.getTime());	

		
		ariFiltro.setDataLimiteAcompFisicoAri(dataOntem);
		
		List listAri = new AcompReferenciaItemDao(null).pesquisarPorData(ariFiltro, null);

		if (listAri != null){
			Iterator itAri = listAri.iterator();
			while (itAri.hasNext()){
				AcompReferenciaItemAri ari = (AcompReferenciaItemAri) itAri.next();
				// o sistema só grava os valores realizados se existir item e este for ativo, e além disso, se a data de início for informada. 
				if (ari.getItemEstruturaIett() != null && ari.getItemEstruturaIett().getIndAtivoIett().equals(Pagina.SIM) && ari.getItemEstruturaIett().getDataInicioIett() != null){
					Set setIettrs = ari.getItemEstruturaIett().getItemEstrtIndResulIettrs();
					if (setIettrs != null){
						Iterator itIettrs = setIettrs.iterator();
						while (itIettrs.hasNext()){
							ItemEstrtIndResulIettr iettr = (ItemEstrtIndResulIettr) itIettrs.next();
							if (iettr.getRealizadoServicoSer() != null 
									&& iettr.getAcompRealFisicoArfs() != null
									&& iettr.getIndRealPorLocal().equals(Pagina.NAO)
									&& iettr.getIndTipoAtualizacaoRealizado() != null
									&& iettr.getIndTipoAtualizacaoRealizado().equals(ServicoSer.TIPO_ATUALIZACAO_REALIZADO_AUTOMATICO)
									&& Pagina.SIM.equals(iettr.getIndAtivoIettr())){
								
								Set setAcompRealFisicoArf = iettr.getAcompRealFisicoArfs();
								if (setAcompRealFisicoArf != null){
									Iterator itAcompRealFisicoArf = setAcompRealFisicoArf.iterator();
									while (itAcompRealFisicoArf.hasNext()){
										AcompRealFisicoArf acompRealFisicoArf = (AcompRealFisicoArf) itAcompRealFisicoArf.next();
										if (acompRealFisicoArf.getAnoArf().equals(Long.valueOf(ari.getAcompReferenciaAref().getAnoAref())) && 
												acompRealFisicoArf.getMesArf().equals(Long.valueOf(ari.getAcompReferenciaAref().getMesAref()))
												&&acompRealFisicoArf.getQtdRealizadaArf()==null){
											String url = iettr.getRealizadoServicoSer().getUrlSer();
											url = url.substring(0,1).toLowerCase() + url.substring(1);
											Object[] parametros = new ServicoDao(null).getParametrosServico(iettr.getRealizadoServicoSer(), ari);
											Double qtdRealizadoArf = (Double)(ServicoDao.class.getMethod(url, new Class[]{String.class, String.class}).invoke(servicoDao, parametros)); 					
											acompRealFisicoArf.setQtdRealizadaArf(qtdRealizadoArf);
											acompRealFisicoDao.alterar(acompRealFisicoArf); 
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Envia o e-mail para aviso dos pontos críticos do projeto aos responsáveis. <br>
	 * 
	 * @author ..., rogerio
	 * @since N/C
	 * @version 0.1, 26/02/2007
	 * @param Date dataCorrente
	 * @param ConfigMailCfgm configMailCfgm
	 * @param String dataLimiteSeNula
	 * @throws ECARException
	 * @throws Exception
	 */
	private void eMailVenctoPontoCritico (Date dataCorrente, ConfigMailCfgm configMailCfgm, String dataLimiteSeNula) throws ECARException, Exception {
		
		try {
			
			PontoCriticoDao ptcDAO = new PontoCriticoDao(null);
			TfuncacompConfigmailTfacfgmDAO tfuncacompConfigmailTfacfgmDAO = new TfuncacompConfigmailTfacfgmDAO();
			UsuarioDao usuDAO = new UsuarioDao();
			ItemEstruturaIett iett = null;
			
			List listItens = null;
			String dataCompStr = null;
			Iterator itPontos = null;
			String corRelogio = null;
			Set datasLimite = null;
			
			/* --
			 * Por Rogério (26/02/2007)
			 * Otimização da query, visando buscar apenas os pontos criticos ativos.
			listItens = itemDAO.listar(PontoCriticoPtc.class, new String[]{"codPtc","ASC"});
			itPontos = listItens.iterator();
			-- */
			
			listItens = ptcDAO.listarPontoCriticoAtivoNExcluidoNSolucionado("S", "S");
			
			if( listItens != null ) itPontos = listItens.iterator();
			
			/* -- Final da otimização -- */
			
			while( itPontos != null && itPontos.hasNext() ){
				PontoCriticoPtc ptc = (PontoCriticoPtc) itPontos.next();
				iett= null;
	
				//Data Limite do ponto Critico a ser usada para comparacao com
				//as outras obtidas através da frequencia e antecedencia
				Date dataComparacao = ptc.getDataLimitePtc(); 
				
				if(dataComparacao != null)
					dataCompStr = Data.parseDate(dataComparacao);
				
				int diasAntecedencia;
				int frequencia;
				
		
				// Se a data for igual à data limite, é enviado e-mail contendo
				// o relogio quebrado (PretoFixo)
				if (dataCompStr!= null && dataCorrente != null && dataCompStr.equals(Data.parseDate(dataCorrente)))
				{
					corRelogio = "PretoFixo";
					datasLimite = new HashSet();
					datasLimite.add(dataCompStr);
		
				}
				
				
				
				// Caso contrário, itera-se buscando qual o período de envio
				// visando descobrir qual o período mais 'grave' que se encaixa
				// nos dias de antecedência que restam
				else
				{
					// Ordena-se o set de PtcCor de maneira a obter uma lista de dias de antecedencia
					// crescente. Isto é, primeiro testa-se a menor antecedencia, buscando trabalhar com
					// o mais urgente que se enquadra na data atual
					List listPtcCor = null;
					
					if(ptc.getPontoCriticoCorPtccores() != null)
						listPtcCor = ptcDAO.ordenaSet(ptc.getPontoCriticoCorPtccores(), "this.antecedenciaPrimEmailPtccor", "asc");
					
					Iterator itPtcCor = null;
					if(listPtcCor!= null)
						itPtcCor = listPtcCor.iterator();
					boolean encontrouLimite = false;
					while(( itPtcCor != null && itPtcCor.hasNext()) && (!encontrouLimite))
					{	
						
						PontocriticoCorPtccor ptcCor = (PontocriticoCorPtccor) itPtcCor.next();				
						
						// Caso a antecedencia seja nula, define-se como 0 (zero)
						if (ptcCor.getAntecedenciaPrimEmailPtccor() == null)
							diasAntecedencia = 0;
						else
							diasAntecedencia = ptcCor.getAntecedenciaPrimEmailPtccor().intValue();
	
						// Caso a frequencia seja nula, define-se como 0 (zero)
						if (ptcCor.getFrequenciaEnvioEmailPtccor() == null)
							frequencia = 0;
						else
							frequencia = ptcCor.getFrequenciaEnvioEmailPtccor().intValue();
						
						Date dataLimite = Data.addDias(diasAntecedencia, dataCorrente);
						String dataLimStr = Data.parseDate(dataLimite);
						
						// Após definir qual é a data que será comparada, verifica-se
						// se esta se enquadra no PtcCor atual. Caso positivo, define-se a data
						// e as subsequentes (de acordo com a frequencia)
						// O loop while é interrompido quando isto é feito.
						if ( (dataLimite != null && dataComparacao != null && dataLimStr != null && dataCompStr != null) && 
								(dataLimite.after(dataComparacao)) || (dataLimStr.equals(dataCompStr)))
						{
							datasLimite = new HashSet();
							if (diasAntecedencia > 0)
							{
								if ("S".equals(ptcCor.getIndAtivoEnvioEmailPtccor()))
								{
									
									datasLimite.add(dataLimStr);
									if (frequencia > 0)
									{
										while (diasAntecedencia > 0)
										{
											diasAntecedencia = diasAntecedencia - frequencia;
											dataLimStr =  new String(Data.parseDate(Data.addDias(diasAntecedencia, dataCorrente)));
											datasLimite.add(dataLimStr);
										}
									}
									corRelogio = ptcCor.getCor().getNomeCor();
								}
								encontrouLimite = true;
							}
						}
					}
				}			
						
				
				iett = (ItemEstruturaIett) ptc.getItemEstruturaIett();
				
				
				// Só manda email para os itens ativos
				if( (datasLimite != null && datasLimite.contains(dataCompStr)) && (iett != null && "S".equals(iett.getIndAtivoIett()))) {						
					List usuariosEmailEnviado = new ArrayList();
					Iterator itList = null;	
					List listIett = null;
					
					if (iett.getItemEstUsutpfuacIettutfas() != null)
						listIett = new ArrayList(iett.getItemEstUsutpfuacIettutfas());
					
					if(listIett!=null)  {
						itList = listIett.iterator();
					
						while( itList != null && itList.hasNext() ) {
							ItemEstUsutpfuacIettutfa itemEstUsutpfacIetutfa = (ItemEstUsutpfuacIettutfa) itList.next();
							
							TfuncacompConfigmailTfacfgmPK tfcfgmPK = new TfuncacompConfigmailTfacfgmPK();
							tfcfgmPK.setCodCfgm(configMailCfgm.getCodCfgm());
							
							if(itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa() != null)
								tfcfgmPK.setCodTpfa(itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa().getCodTpfa());
							
							TfuncacompConfigmailTfacfgm tfcfm = null;
							
							try {
								tfcfm = (TfuncacompConfigmailTfacfgm) tfuncacompConfigmailTfacfgmDAO.buscar(TfuncacompConfigmailTfacfgm.class, tfcfgmPK);
							}  catch(Exception e) {
								tfcfm = null;
							}
								
							if (tfcfm != null && "S".equals(tfcfm.getEnviaMailTfacfgm())) {
								
								//UsuarioUsu usu = (UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getComp_id().getCodUsu());
								List usuarios = new ArrayList();
								if (itemEstUsutpfacIetutfa.getUsuarioUsu() != null) {
									usuarios.add((UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getUsuarioUsu().getCodUsu()));
								} else if (itemEstUsutpfacIetutfa.getSisAtributoSatb() != null){
									usuarios.addAll(usuDAO.getUsuariosBySisAtributoSatb(itemEstUsutpfacIetutfa.getSisAtributoSatb()));
								}
								
								Iterator itUsu = usuarios.iterator();
								
								while (itUsu.hasNext()){
								
									UsuarioUsu usu = (UsuarioUsu) itUsu.next();
								
									if(!usuariosEmailEnviado.contains(usu)) {
										usuariosEmailEnviado.add(usu);
		
										Long   codIett = Long.valueOf(0);
										String textoMail = "";
										String assunto   = "";
										String remetente = "";
									
										if( configMailCfgm.getTextosSiteMail() != null ) {
											textoMail = configMailCfgm.getTextosSiteMail().getTextoTxts();
											assunto   = configMailCfgm.getTextosSiteMail().getDescricaoUsoTxts();
											remetente = configMailCfgm.getTextosSiteMail().getEmailResponsavelTxts();
										}
									
										codIett = iett.getCodIett();
															
										try {
											
											Evento evento = null;
											evento = new EventoVencimentoLimitePontoCritico();
											ConfiguracaoCfg configCfg = new ConfiguracaoDao(null).getConfiguracao();
										    String contextPath = configCfg.getContextPath();
										    String[] valores = new String[3];//codIett, codAba
										    
										    valores[0] = iett.getCodIett().toString();
										    
										 	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(null);
											boolean existeAbaPontosCriticos = estruturaFuncaoDao.existeFuncaoPontosCriticos(iett.getEstruturaEtt());
											
				
											 //Só envia email se existir Aba Pontos Criticos para a Estrutura 
											if(existeAbaPontosCriticos) {
												
												FuncaoDao funcaoDao = new FuncaoDao(null);
												long codPontosCriticos = funcaoDao.getCodFuncaoPontosCriticos();
												valores[1] = String.valueOf(codPontosCriticos);
											
											    if(iett.getEstruturaEtt() != null) {
													Long codEttSelecionado = iett.getEstruturaEtt().getCodEtt();
													if(codEttSelecionado != null) {
														valores[2] = codEttSelecionado.toString();
													}
												}
											    
												// monta a url
											    link = URLEvento.montaURLEventoSemRequest(evento, contextPath, valores);
												String html = this.montaEmailComLink(textoMail, usu.getNomeUsu(), null, codIett,
																"Alerta de criticidade de data: <img src=\""+this.contextPath+"/images/pc"+corRelogio+"1.png\">",
																dataCompStr, null, null, null, link).toString();
												if ((usu.getEmail1Usu() != null) && (!"".equals(usu.getEmail1Usu())))
												{
													if ((assunto == null) || (assunto.length() <= 0)) {
														assunto = "Alerta de criticidade de data";
													}
																				
													Util.enviarEmail(assunto, this.nomeSistema, remetente, html, usu.getEmail1Usu(),"", "", usu);
													org.apache.log4j.Logger.getLogger(this.getClass()).info(
										    				"E-mail enviado (" + 
										    				Data.getDataAtual() + " " + Data.getHoraAtual(true) + "): " +
										    				" assunto: " + assunto + 
										    				" - remetente: " + remetente +
										    				" - destinatarioPara: " + usu.getEmail1Usu()
										    				);
												}	
												else
												{
													String erro = "Erro na tentativa de enviar e-mail para o usuario " + usu.getNomeUsu() + "(Cod: " + usu.getCodUsu() + ")<br> Verifique o e-mail e o nome do mesmo no cadastro de usuarios";
													Util.enviarEmail("["+ this.nomeSistema + "] Erro de Envio de Email", this.nomeSistema, remetente, erro, remetente, "", "", usu);
										    		org.apache.log4j.Logger.getLogger(this.getClass()).info(
										    				"E-mail enviado (" + 
										    				Data.getDataAtual() + " " + Data.getHoraAtual(true) + "): " +
										    				" assunto: " + assunto + 
										    				" - remetente: " + remetente +
										    				" - destinatarioPara: " + remetente
										    				);
												}
												
											}
										
										    
										    
										}catch (Exception e) {
											org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
											String erro = "Erro na tentativa de enviar e-mail para o usuario " + usu.getNomeUsu() + "(Cod: " + usu.getCodUsu() + ")<br> Verifique o e-mail e o nome do mesmo no cadastro de usuarios";
											erro = erro + "<br>" + e.toString();
											Util.enviarEmail("["+ this.nomeSistema + "] Erro de Envio de Email", this.nomeSistema, remetente, erro, remetente, "", "", usu);
								    		org.apache.log4j.Logger.getLogger(this.getClass()).info(
								    				"E-mail enviado (" + 
								    				Data.getDataAtual() + " " + Data.getHoraAtual(true) + "): " +
								    				" assunto: " + assunto + 
								    				" - remetente: " + remetente +
								    				" - destinatarioPara: " + remetente
								    				);
											this.enviarEmailExcecao("AgendadorEmail.eMailVenctoPontoCritico() (1)", e);
										}							
									}
								}
							}
							
							if ("S".equals(tfcfm.getEnviaSms())) {
								// envia sms
							}
						}
					}
				}
			}
		} catch(Exception e) {
			this.enviarEmailExcecao("AgendadorEmail.eMailVenctoPontoCritico() (2)", e);
			throw(e);
		}
		
	} // fim eMailVenctoPontoCritico
	
	/**
	 * Trata dos casos previstos entre os códigos de 7 até 13 da configuração de e-mail para envio. <br>
	 * 
	 * @author rogerio
	 * @since  0.1, 09/02/2007
	 * @version 0.1, 09/02/2007
	 * @param String dataLimStr
	 * @param ConfigMailCfgm configMailCfgm
	 * @throws ECARException
	 * @throws Exception
	 */
	private void eMailDataItem (String dataLimStr, ConfigMailCfgm configMailCfgm) throws ECARException, Exception	{
		try {
			Iterator itItem = null;		
			
			/* -- 
			 * Rogério (23/02/2007)
			 * Comentado devido a otimização de código na query. 
			 * -- */
			//itItem = new AcompReferenciaItemDao(null).listar(ItemEstruturaIett.class, new String[]{"codIett","ASC"}).iterator();
			
			// esta pesquisa já retorna somente os itens ativos
			List list = new ItemEstruturaDao(null).listarItemEstruturaPorDataLimite(dataLimStr);
			
			if( list != null ) itItem = list.iterator();
					
			// Pra cada item, trata o código pra buscar a data 
			while( itItem.hasNext() ){
				ItemEstruturaIett iett = (ItemEstruturaIett) itItem.next();
				String dataItemStr = null;
				
				
				switch(configMailCfgm.getCodCfgm().intValue()) {
						case 7: // data termino item		
							dataItemStr = Data.parseDate(iett.getDataTerminoIett());
							break;
						case 8: // data inicio item					
							dataItemStr = Data.parseDate(iett.getDataInicioIett());				
							break;
						case 9: // data reserva 1					
							dataItemStr = Data.parseDate(iett.getDataR1());
							break;
						case 10: // data reserva 2					
							dataItemStr = Data.parseDate(iett.getDataR2());
							break;
						case 11: // data reserva 3					
							dataItemStr = Data.parseDate(iett.getDataR3());
							break;
						case 12: // data reserva 4					
							dataItemStr = Data.parseDate(iett.getDataR4());
							break;
						case 13: // data reserva 5
							dataItemStr = Data.parseDate(iett.getDataR5());
							break;
				}
					
				
				eMailGenerico(dataLimStr, dataItemStr, configMailCfgm, iett);
				
				
			}
		} catch(Exception e) {
			this.enviarEmailExcecao("AgendadorEmail.eMailDataItem()", e);
			throw(e);
		}

	} // fim eMailDataItem
	
	
	
	/**
	 * Obtem e trata os dados para envio de e-mails de acordo com os prazos limites. <br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param dataLimStr
         * @param dataCompStr
         * @param configMailCfgm
         * @param iett
         * @throws ECARException
	 * @throws Exception
	 */
	public void eMailGenerico(String dataLimStr, String dataCompStr, ConfigMailCfgm configMailCfgm, ItemEstruturaIett iett) throws ECARException, Exception {
		try {
			TfuncacompConfigmailTfacfgmDAO tfuncacompConfigmailTfacfgmDAO = new TfuncacompConfigmailTfacfgmDAO();
			UsuarioDao usuDAO = new UsuarioDao();
			
			String eventDesc = null;
			String objeto = null;
			String valor = null;
			
			if( dataLimStr.equals(dataCompStr)) { 
				List listIettutfas = new ArrayList(iett.getItemEstUsutpfuacIettutfas());
				List usuariosEmailEnviado = new ArrayList();
				Iterator itList = listIettutfas.iterator();
				
				String link = null;
				
				
				String[] valores = new String[3];
				// codigo do item
				if(iett != null && iett.getCodIett() != null) {
					valores[0] = iett.getCodIett().toString();
				}
					
				//codigo da aba
				FuncaoDao funcaoDao = new FuncaoDao(null);
				valores[1] = String.valueOf(funcaoDao.getCodFuncaoDadosGerais());
				
				//codigo da estrutura selecionada
				String 	paramCodEttSelecionado = "";
				if(iett.getEstruturaEtt() != null) {
					Long codEttSelecionado = iett.getEstruturaEtt().getCodEtt();
					if(codEttSelecionado != null) {
						valores[2] = codEttSelecionado.toString();
					}
				}
				
				ConfiguracaoCfg configCfg = new ConfiguracaoDao(null).getConfiguracao();
				contextPath = configCfg.getContextPath();
				Evento evento = null;
				
				switch(configMailCfgm.getCodCfgm().intValue()) {
					case 7: // data termino item		
						evento = new EventoVencimentoDataTerminoItem();
						eventDesc = "Alteração da Data de Término para ";
						break;
					case 8: // data inicio item					
						evento = new EventoVencimentoDataInicioItem();
						eventDesc = "Alteração da Data Início para ";
						break;
					case 9: // data reserva 1					
						evento = new EventoVencimentoDataReserva1();
						eventDesc = "Alteração da Data Reserva 1 para ";
						//Data.parseDate(itemEstUsutpfacIetutfa.getItemEstruturaIett().getDataR1());;
						break;
					case 10: // data reserva 2					
						evento = new EventoVencimentoDataReserva2();
						eventDesc = "Alteração da Data Reserva 2 para ";
						break;
					case 11: // data reserva 3					
						evento = new EventoVencimentoDataReserva3();
						eventDesc = "Alteração da Data Reserva 3 para ";
						break;
					case 12: // data reserva 4					
						evento = new EventoVencimentoDataReserva4();
						eventDesc = "Alteração da Data Reserva 4 para ";
						break;
					case 13: // data reserva 5
						evento = new EventoVencimentoDataReserva5();
						eventDesc = "Alteração da Data Reserva 5 para ";
						break;
				}
				
				
				if(evento != null)
					link = URLEvento.montaURLEventoSemRequest(evento, contextPath, valores);
				
				/**Manda o email para todos os usuários das Funções de Acompanhamento relacionadas ao item (cadastradas no item) e configuradas para receber**/
				while( itList.hasNext() ) {
					ItemEstUsutpfuacIettutfa itemEstUsutpfacIetutfa = (ItemEstUsutpfuacIettutfa) itList.next();
					
					TfuncacompConfigmailTfacfgmPK tfcfgmPK = new TfuncacompConfigmailTfacfgmPK();
					tfcfgmPK.setCodCfgm(configMailCfgm.getCodCfgm());
					tfcfgmPK.setCodTpfa(itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa().getCodTpfa());
					
					TfuncacompConfigmailTfacfgm tfcfm = (TfuncacompConfigmailTfacfgm) tfuncacompConfigmailTfacfgmDAO.
								buscar(TfuncacompConfigmailTfacfgm.class, tfcfgmPK);
					
					// se a funcao de acompanhamento estiver configurada para receber email (configuração estrutura/funcao acompanhamento)
					if ("S".equals(tfcfm.getEnviaMailTfacfgm())) {
						
						//UsuarioUsu usu = (UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getComp_id().getCodUsu());
						List usuarios = new ArrayList();
						if (itemEstUsutpfacIetutfa.getUsuarioUsu() != null) {
							usuarios.add((UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getUsuarioUsu().getCodUsu()));
						} else if (itemEstUsutpfacIetutfa.getSisAtributoSatb() != null){
							usuarios.addAll(usuDAO.getUsuariosBySisAtributoSatb(itemEstUsutpfacIetutfa.getSisAtributoSatb()));
						}
						
						Iterator itUsu = usuarios.iterator();
						
						while (itUsu.hasNext()){
						
							UsuarioUsu usu = (UsuarioUsu) itUsu.next();
					
							if(!usuariosEmailEnviado.contains(usu)) {
								usuariosEmailEnviado.add(usu);
								Long codIett = Long.valueOf(0);
								String textoMail = "";
								String assunto   = "";
								String remetente = "";
							
								if( configMailCfgm.getTextosSiteMail() != null ) {
									textoMail = configMailCfgm.getTextosSiteMail().getTextoTxts();
									assunto   = configMailCfgm.getTextosSiteMail().getDescricaoUsoTxts();
									remetente = configMailCfgm.getTextosSiteMail().getEmailResponsavelTxts();
								}
							
								codIett = iett.getCodIett();
										
								try {
									
									String html = "";
									if(eventDesc != null && !"".equals(eventDesc)){
										switch(configMailCfgm.getCodCfgm().intValue()) {
											case 7: // data termino item		
												eventDesc += Data.parseDate(itemEstUsutpfacIetutfa.getItemEstruturaIett().getDataTerminoIett());
												break;
											case 8: // data inicio item					
												eventDesc += Data.parseDate(itemEstUsutpfacIetutfa.getItemEstruturaIett().getDataInicioIett());
												break;
											case 9: // data reserva 1					
												eventDesc += Data.parseDate(itemEstUsutpfacIetutfa.getItemEstruturaIett().getDataR1());
												//Data.parseDate(itemEstUsutpfacIetutfa.getItemEstruturaIett().getDataR1());;
												break;
											case 10: // data reserva 2					
												eventDesc += Data.parseDate(itemEstUsutpfacIetutfa.getItemEstruturaIett().getDataR2());
												break;
											case 11: // data reserva 3					
												eventDesc += Data.parseDate(itemEstUsutpfacIetutfa.getItemEstruturaIett().getDataR3());
												break;
											case 12: // data reserva 4					
												eventDesc += Data.parseDate(itemEstUsutpfacIetutfa.getItemEstruturaIett().getDataR4());
												break;
											case 13: // data reserva 5
												eventDesc += Data.parseDate(itemEstUsutpfacIetutfa.getItemEstruturaIett().getDataR5());
												break;
										}
										
										html = this.montaEmailComLinkDescricao(textoMail, usu.getNomeUsu(), codIett, dataLimStr, null, link, eventDesc).toString();
									} else{
										html = this.montaEmailComLink(textoMail, usu.getNomeUsu(), codIett, dataLimStr, null, link).toString();
									}
										
									if ((usu.getEmail1Usu() != null) && (!"".equals(usu.getEmail1Usu()))) {
										Util.enviarEmail(assunto, this.nomeSistema, remetente, html, usu.getEmail1Usu(),"", "", usu);
							    		
										org.apache.log4j.Logger.getLogger(this.getClass()).info(
							    				"E-mail enviado (" + 
							    				Data.getDataAtual() + " " + Data.getHoraAtual(true) + "): " +
							    				" assunto: " + assunto + 
							    				" - remetente: " + remetente +
							    				" - destinatarioPara: " + usu.getEmail1Usu()
							    				);
									} else {
										String erro = "Erro na tentativa de enviar e-mail para o usuario " + usu.getNomeUsu() + "(Cod: " + usu.getCodUsu() + ")<br> Verifique o e-mail e o nome do mesmo no cadastro de usuarios";
										Util.enviarEmail("["+ this.nomeSistema + "] Erro de Envio de Email", this.nomeSistema, remetente, erro, remetente, "", "", usu);
							    		org.apache.log4j.Logger.getLogger(this.getClass()).info(
							    				"E-mail enviado (" + 
							    				Data.getDataAtual() + " " + Data.getHoraAtual(true) + "): " +
							    				" assunto: " + assunto + 
							    				" - remetente: " + remetente +
							    				" - destinatarioPara: " + remetente
							    				);
									}			
								}catch (Exception e) {
									org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
									String erro = "Erro na tentativa de enviar e-mail para o usuario " + usu.getNomeUsu() + "(Cod: " + usu.getCodUsu() + ")<br> Verifique o e-mail e o nome do mesmo no cadastro de usuarios";
									erro = erro + "<br>" + e.toString();
									Util.enviarEmail("["+ this.nomeSistema + "] Erro de Envio de Email", this.nomeSistema, remetente, erro, remetente, "", "", usu);
						    		org.apache.log4j.Logger.getLogger(this.getClass()).info(
						    				"E-mail enviado (" + 
						    				Data.getDataAtual() + " " + Data.getHoraAtual(true) + "): " +
						    				" assunto: " + assunto + 
						    				" - remetente: " + remetente +
						    				" - destinatarioPara: " + remetente
						    				);
									this.enviarEmailExcecao("AgendadorEmail.eMailGenerico() (1)", e);
								}
							}
						}
					}
					
					if ("S".equals(tfcfm.getEnviaSms())) {
						// envia sms
					}
				}
			}
		} catch(Exception e) {
			this.enviarEmailExcecao("AgendadorEmail.eMailGenerico() (2)", e);
			throw(e);
		}
	}	
	
	
	
	
	
	/**
	 * Envia e-mail de exceções para o e-mail de erros cadastrado na empresa. <br>
	 * 
	 * @author cristiano
	 * @since 0.1, 06/03/2007
	 * @version 0.2, 19/03/2007
	 * @param String mensagem
	 * @param Exception e
	 * @throws ECARException
	 * @throws Exception
	 */
	private void enviarEmailExcecao(String mensagem, Exception e) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(mensagem);
			
			if(e != null) {
				sb.append("\n");
				sb.append(e.getMessage());
			}
			
			if(this.emailErroEmpresa != null && !"".equals(this.emailErroEmpresa)) {
				Util.enviarEmail("[" + this.nomeSistema + "] ERRO - Agendador de Email", 
						this.nomeSistema, 
						this.emailErroEmpresa, 
						sb.toString(), 
						this.emailErroEmpresa, 
						"", 
						"", null);
			}
		} catch(Exception ex) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(ex);
		}
	}
	
	public static void main(String[] args) {
		Session sessao = HibernateUtil.currentSession();
		
		PerfilIntercambioDadosPflid perf = (PerfilIntercambioDadosPflid)sessao.load(PerfilIntercambioDadosPflid.class, 5L);
		
		System.out.println(((PerfilIntercambioDadosCadastroPidc)perf).getEstruturaItemNivelSuperiorPidc().getNomeEtt());
	}
	
} // fim class