package ecar.dao;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Util;

import ecar.bean.CargaFinalidadesPPA;
import ecar.bean.CargaProdutosPPA;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.permissao.ControlePermissao;
import ecar.pojo.AreaAre;
import ecar.pojo.EfIettFonteTotEfieft;
import ecar.pojo.EfIettFonteTotEfieftPK;
import ecar.pojo.EfItemEstPrevisaoEfiep;
import ecar.pojo.EfItemEstPrevisaoEfiepPK;
import ecar.pojo.EspecieEsp;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ExercicioExe;
import ecar.pojo.FonteFon;
import ecar.pojo.FonteRecursoFonr;
import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrtIndResulLocalIettirl;
import ecar.pojo.ItemEstrutFisicoIettf;
import ecar.pojo.ItemEstrutFisicoIettfPK;
import ecar.pojo.ItemEstrutLocalIettl;
import ecar.pojo.ItemEstrutLocalIettlPK;
import ecar.pojo.ItemEstrutUsuarioIettus;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturaSisAtributoIettSatb;
import ecar.pojo.LocalItemLit;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.OrgaoPeriodoExercicioOrgPerExe;
import ecar.pojo.OrgaoPeriodoExercicioOrgPerExePK;
import ecar.pojo.PaiFilho;
import ecar.pojo.PeriodoExercicioPerExe;
import ecar.pojo.PoderPeriodoExercicioPodPerExe;
import ecar.pojo.PoderPeriodoExercicioPodPerExePK;
import ecar.pojo.PoderPod;
import ecar.pojo.RecursoRec;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SubAreaSare;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.UnidadeOrcamentariaPeriodoExercicioUoPerExe;
import ecar.pojo.UnidadeOrcamentariaPeriodoExercicioUoPerExePK;
import ecar.pojo.UnidadeOrcamentariaUO;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/**
 * DAO temporária para carga/atualizacao em ItemEstruturaIetts
 * 
 * @author aleixo
 */
public class TempCargaIettsBDDao extends Dao {
    /**
     * 
     * @param request
     */
    public TempCargaIettsBDDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
    
    private static final int STRING = 1;
    private static int LONG = 2;
    
    
    /**
     * Metodo para lancar Exceção!
     * @param mensagem
     * @throws ECARException
     */
    private void lancarErro(String mensagem) throws ECARException{
    	System.out.println("[ERRO NA ATUALIZACAO] - " + mensagem);
    	throw new ECARException(mensagem);
    }

    /**
     * Exibe uma mensagem no console...
     * @param mensagem
     */
    private void msg(String mensagem){
    	System.out.println(mensagem);
    }
    
    /**
     * Faz um select simples da classe "objeto", onde "campoDeCondicao" = "valorCampo".
     * 
     * @author aleixo
     * @since 27/07/2007
     * @param objeto
     * @param campoDeCondicao
     * @param tipoCampo
     * @param valorCampo
     * @return
     * @throws ECARException
     */
    private Object selectObjeto(String objeto, String campoDeCondicao, int tipoCampo, Object valorCampo, Session s) throws ECARException{
		Query q;
		StringBuilder select;
		Object o;
		
		select = new StringBuilder();
		select.append("select o from " + objeto + " o where o." + campoDeCondicao + " = :valor");
		if(s != null)
			q = s.createQuery(select.toString());
		else
			q = this.session.createQuery(select.toString());
		
		if(tipoCampo == STRING){ //String
			q.setString("valor", (String) valorCampo);
		}
		if(tipoCampo == LONG){ //Long
			q.setLong("valor", ((Long) valorCampo).longValue());
		}
		q.setMaxResults(1);
		o = q.uniqueResult();
		if(o == null){
			System.out.println("++++++++ ERRO: " + objeto + " é Nulo para valor " + valorCampo.toString());
			throw new ECARException(objeto + " is null");
		}
		
		return o;
    }

    /**
     * Efetua carga de ações para PPA 2008-2011, importando os dados do COP.
     * 
     * @author aleixo
     * @since 11/07/2007
     * @throws ECARException
     */
    public void efetuarCargaItens() throws ECARException{
    	//ItemEstruturaDao itemDao = new ItemEstruturaDao(request);

    	//Buscando a estrutura de nível de ação do PPA 2008-2011
    	EstruturaEtt estrutura = (EstruturaEtt) new EstruturaDao(request).buscar(EstruturaEtt.class, Long.valueOf(20));
    	
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();
            
	    	BufferedReader in = new BufferedReader (new FileReader("/home/aleixo/cop/dados.csv"));
			String linha="";
			while ((linha=in.readLine()) != null) {
				
				String[] campos = linha.split(";");
				System.out.println(linha);
		    	//Número do Programa;Número do Projeto/Atividade(Ação);Nome;Órgão;Unidade;Função;Sub Função
		    	String siglaPrograma = campos[0].trim();
		    	String siglaAcao = campos[1].trim();
		    	//String nomeAcao = Util.soPrimeiraLetraToUpperCase(campos[2].trim().replaceAll("\"", ""));
		    	String nomeAcao = Util.todasPrimeirasLetrasToUpperCase(campos[2]);
		    	String codIdentOrg = campos[3].trim();
		    	String codIdentUo = campos[4].trim();
		    	String codIdentFunc = campos[5].trim();
		    	String codIdentSubFunc = campos[6].trim();
		    	
		    	String sqlBuscaPai = "select pai from ItemEstruturaIett pai where siglaIett = :sigla and pai.estruturaEtt.codEtt = :codEttPai";

		    	Query q = this.session.createQuery(sqlBuscaPai);
	    		q.setString("sigla", siglaPrograma);
	    		q.setLong("codEttPai", 22); //22 é Programa do PPA 2008-2011
	    		q.setMaxResults(1);
    			ItemEstruturaIett iettPai = (ItemEstruturaIett) q.uniqueResult();
    			if(iettPai == null)
    				throw new ECARException("IettPai Nulo");
    			
    			System.out.println(iettPai);
		    	System.out.println("Item do Programa: " + siglaPrograma + " - " + iettPai.getNomeIett() + " - " + iettPai.getEstruturaEtt().getNomeEtt() + " - " + iettPai.getItemEstruturaIett().getNomeIett() + " - " + iettPai.getItemEstruturaIett().getEstruturaEtt().getNomeEtt());
		    	System.out.println("Nome: " + siglaAcao + " - " + nomeAcao);
		    	System.out.println("Org/Uo/Func/SubFunc: " + codIdentOrg + "/" + codIdentUo + "/" + codIdentFunc + "/" + codIdentSubFunc);
    			
    			ItemEstruturaIett iett = new ItemEstruturaIett();
    			
    			iett.setIndAtivoIett("S");
    			iett.setIndBloqPlanejamentoIett("N");
    			iett.setIndCriticaIett("N");
    			iett.setIndMonitoramentoIett("N");
    			iett.setDataInclusaoIett(Data.getDataAtual());
    			iett.setDataUltManutencaoIett(Data.getDataAtual());
    			iett.setDataInicioIett(Data.parseDate("01/01/2008"));
    			iett.setDataTerminoIett(Data.parseDate("31/12/2011"));
    			iett.setNivelIett(Integer.valueOf(3));
    			
    			
    			iett.setItemEstruturaIett(iettPai);
    			iett.setSiglaIett(siglaAcao);
    			iett.setNomeIett(nomeAcao);

   				iett.setEstruturaEtt(estrutura);

    			/* Buscando unidade orçamentárias pelo codigo do COP */
   				String sql = "select unidade from UnidadeOrcamentariaUO unidade where " +
   						" unidade.codigoIdentUo = :codIdentUo" +
   						" and unidade.orgaoOrg.codigoIdentOrg = :codIdentOrg";
    			q = this.session.createQuery(sql);
    			q.setLong("codIdentUo", Long.valueOf(codIdentUo).longValue());
    			q.setLong("codIdentOrg", Long.valueOf(codIdentOrg).longValue());
    			q.setMaxResults(1);
    			
				UnidadeOrcamentariaUO unidade = (UnidadeOrcamentariaUO) q.uniqueResult();
    			if(unidade == null)
    				throw new ECARException("Unidade Nulo");
				iett.setUnidadeOrcamentariaUO(unidade);

    			/* Buscando orgao pelo codigo do COP */
   				sql = "select orgao from OrgaoOrg orgao where orgao.codigoIdentOrg = :codIdentOrg";
    			q = this.session.createQuery(sql);
    			q.setLong("codIdentOrg", Long.valueOf(codIdentOrg).longValue());
    			q.setMaxResults(1);
    			
				OrgaoOrg orgao = (OrgaoOrg) q.uniqueResult();
    			if(orgao == null)
    				throw new ECARException("Orgao Nulo");
				iett.setOrgaoOrgByCodOrgaoResponsavel1Iett(orgao);
				
    			/* Setando Usuario para funcao GPS*/
    			TipoFuncAcompTpfa funcaoGPS = (TipoFuncAcompTpfa) new TipoFuncAcompDao(request).buscar(TipoFuncAcompTpfa.class, Long.valueOf(3)); //3 - GPS, 1 - Administrador
    			
    			UsuarioUsu user = this.getUsuarioGPSByOrgao(orgao);
    			
    			if(user != null){
	                ItemEstUsutpfuacIettutfa funcaoItemEstrutura = new ItemEstUsutpfuacIettutfa();
	                funcaoItemEstrutura.setItemEstruturaIett(iett);
	                funcaoItemEstrutura.setTipoFuncAcompTpfa(funcaoGPS);
	                funcaoItemEstrutura.setUsuarioUsu(user);
	    	        iett.setItemEstUsutpfuacIettutfas(new HashSet());
	                iett.getItemEstUsutpfuacIettutfas().add(funcaoItemEstrutura);

	                System.out.println(orgao);
	                System.out.println(funcaoItemEstrutura.getUsuarioUsu());
    			}
                
    			/* Buscando funcao pelo codigo do COP */
    			sql = "select funcao from AreaAre funcao where funcao.codigoIdentAre = :codIdentFunc";
    			q = this.session.createQuery(sql);
    			q.setLong("codIdentFunc", Long.valueOf(codIdentFunc).longValue());
    			q.setMaxResults(1);
    			
				AreaAre funcao = (AreaAre) q.uniqueResult();
    			if(funcao == null)
    				throw new ECARException("Funcao Nulo");
				iett.setAreaAre(funcao);

    			/* Buscando subfuncao pelo codigo do COP */
    			sql = "select subfuncao from SubAreaSare subfuncao where subfuncao.codigoIdentSare = :codIdentSubFunc";
    			q = this.session.createQuery(sql);
    			q.setLong("codIdentSubFunc", Long.valueOf(codIdentSubFunc).longValue());
    			q.setMaxResults(1);
    			
				SubAreaSare subFuncao = (SubAreaSare) q.uniqueResult();
    			if(subFuncao == null)
    				throw new ECARException("subFuncao Nulo");
				iett.setSubAreaSare(subFuncao);
                
                /* Salvando item */
    			//itemDao.salvar(request, iett);
    			//Como preciso salvar todos os itens em transacao, foi "copiado" o método salvar e colocado todos os itens em transacao.
    			
	            iett.setDataInclusaoIett(Data.getDataAtual());
		        List filhos = new ArrayList();
		        if (iett.getItemEstUsutpfuacIettutfas() != null)
		            filhos.addAll(iett.getItemEstUsutpfuacIettutfas());
		        
	            session.save(iett);
				objetos.add(iett);
				

				//
		        // controlar as permissoes passando o item e a lista das funcoes de acompanhamento velhas (vai ser uma lista vazia)
		        //
				new ControlePermissao().atualizarPermissoesItemEstrutura(iett, null, session, true, request);
				
				
				// gravar permissão para o usuário que criou o item 
				ItemEstrutUsuarioIettus itemEstrutUsuario = new ItemEstrutUsuarioIettus();
		
				itemEstrutUsuario.setItemEstruturaIett(iett);
				itemEstrutUsuario.setItemEstruturaIettOrigem(iett);
				itemEstrutUsuario.setCodTpPermIettus(ControlePermissao.PERMISSAO_USUARIO);
				itemEstrutUsuario.setUsuarioUsu(((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
		
				itemEstrutUsuario.setIndLeituraIettus("S");
				itemEstrutUsuario.setIndEdicaoIettus("S");
				itemEstrutUsuario.setIndExcluirIettus("S");
				
				itemEstrutUsuario.setIndAtivMonitIettus("N");
				itemEstrutUsuario.setIndDesatMonitIettus("N");
				itemEstrutUsuario.setIndBloqPlanIettus("N");
				itemEstrutUsuario.setIndDesblPlanIettus("N");
				itemEstrutUsuario.setIndInfAndamentoIettus("N");
				itemEstrutUsuario.setIndEmitePosIettus("N");
				itemEstrutUsuario.setIndProxNivelIettus("N");
				
				itemEstrutUsuario.setDataInclusaoIettus(Data.getDataAtual());

				Iterator it = filhos.iterator();
				while(it.hasNext()) {
				    PaiFilho object = (PaiFilho) it.next();
				    object.atribuirPKPai();
				    //salva os filhos
				    session.save(object);
					objetos.add(object);
				}

				session.save(itemEstrutUsuario);
				objetos.add(itemEstrutUsuario);
			}
			
			tx.commit();

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC");
				Iterator itObj = objetos.iterator();

				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}	    			
    }
    
    /**
     * Método somente usado em efetuarCargaItens() para retornar o usuário de um determinado órgão, seguindo planilha anexada no mantis 11861.
     * 
     * @author aleixo
     * @since 11/07/2007
     * @param orgao
     * @return
     * @throws ECARException
     */
    private UsuarioUsu getUsuarioGPSByOrgao(OrgaoOrg orgao) throws ECARException{
    	
    	final long COD_ORG_SEPL = 1; /* Mirian Pappi Gomes - 325 */
    	final long COD_ORG_PGE = 5; /* Maristela Pioli - 155 */
    	final long COD_ORG_SEAP = 6; /* Luciane do Rocio Walesko Fantin - 136 */
    	final long COD_ORG_SEAB = 7; /* Teodoro Kostin Neto - 133 */
    	final long COD_ORG_SETI = 8; /* Sérgio Luiz Covalski - 149 */
    	final long COD_ORG_SECS = 9; /* Luis Carlos Fracaro - 157 */
    	final long COD_ORG_SEEC = 10; /* Matias Marino da Silva - 340 */
    	final long COD_ORG_SEDU = 11; /* Reynaldo Aquino de Paula - 158 */
    	final long COD_ORG_SEED = 12; /* Carlos Roberto Sottomaior Valiente - 139 */
    	final long COD_ORG_SEFA = 13; /* Roseli Naufal Schnekemberg - 141 */
    	final long COD_ORG_SEIM = 14; /* Eliziany Sutil de Oliveira Guimarães - 334 */
    	final long COD_ORG_SEJU = 15; /* Sílvio Carlos Nass - 143 */
    	final long COD_ORG_SEMA = 16; /* Walter Osternack Junior - 160 */
    	final long COD_ORG_SEOP = 17; /* Emir Carlos Grassani - 144 */
    	final long COD_ORG_SESA = 19; /* Sueli de Sá Riechi - 145 */
    	final long COD_ORG_SESP = 20; /* Maria Helena Paes - 147 */
    	final long COD_ORG_SETP = 21; /* Helena Oliveira Borges Saldanha - 151 */
    	final long COD_ORG_SETR = 22; /* Rejane Karam - 153 */
    	final long COD_ORG_SETU = 23; /* Elaine Ligiero Ferreira - 162 */
    	final long COD_ORG_CPE = 79;  /* Maria Cristina da Silva Magalhães - 131 */
    	
    	Long chave = null;
    	
    	if(orgao != null){
    		if(orgao.getCodOrg().longValue() == COD_ORG_SEPL) chave = Long.valueOf(325);
    		if(orgao.getCodOrg().longValue() == COD_ORG_PGE) chave = Long.valueOf(155);
    		if(orgao.getCodOrg().longValue() == COD_ORG_SEAP) chave = Long.valueOf(136);
    		if(orgao.getCodOrg().longValue() == COD_ORG_SEAB) chave = Long.valueOf(133);
    		if(orgao.getCodOrg().longValue() == COD_ORG_SETI) chave = Long.valueOf(149);
    		if(orgao.getCodOrg().longValue() == COD_ORG_SECS) chave = Long.valueOf(157);
    		if(orgao.getCodOrg().longValue() == COD_ORG_SEEC) chave = Long.valueOf(340);
    		if(orgao.getCodOrg().longValue() == COD_ORG_SEDU) chave = Long.valueOf(158);
    		if(orgao.getCodOrg().longValue() == COD_ORG_SEED) chave = Long.valueOf(139);
    		if(orgao.getCodOrg().longValue() == COD_ORG_SEFA) chave = Long.valueOf(141);
    		if(orgao.getCodOrg().longValue() == COD_ORG_SEIM) chave = Long.valueOf(334);
    		if(orgao.getCodOrg().longValue() == COD_ORG_SEJU) chave = Long.valueOf(143);
    		if(orgao.getCodOrg().longValue() == COD_ORG_SEMA) chave = Long.valueOf(160);
    		if(orgao.getCodOrg().longValue() == COD_ORG_SEOP) chave = Long.valueOf(144);
    		if(orgao.getCodOrg().longValue() == COD_ORG_SESA) chave = Long.valueOf(145);
    		if(orgao.getCodOrg().longValue() == COD_ORG_SESP) chave = Long.valueOf(147);
    		if(orgao.getCodOrg().longValue() == COD_ORG_SETP) chave = Long.valueOf(151);
    		if(orgao.getCodOrg().longValue() == COD_ORG_SETR) chave = Long.valueOf(153);
    		if(orgao.getCodOrg().longValue() == COD_ORG_SETU) chave = Long.valueOf(162);
    		if(orgao.getCodOrg().longValue() == COD_ORG_CPE) chave = Long.valueOf(131);
    		
    		if(chave != null){
    			return (UsuarioUsu) new UsuarioDao(request).buscar(UsuarioUsu.class, chave);
    		}
    	}
    	
    	return null;
    }
    
    /**
     * Efetua carga nas tabelas de PoderPeriodoExercicioPodPerExe, 
     * OrgaoPeriodoExercicioOrgPerExe e UnidadeOrcamentariaPeriodoExercicioUoPerExe, relacionando PoderPod, OrgaoOrg
     * e UnidadeOrcamentariaUo com PeriodoExercicioPerExe 2008-2011.
     * 
     * @author aleixo
     * @since 12/07/2007
     * @throws ECARException
     */
    public void efetuarCargaPeriodoExercicios() throws ECARException{
    	Dao dao = new Dao();
    	PeriodoExercicioDao perExeDao = new PeriodoExercicioDao(request);
    	PeriodoExercicioPerExe perExe = (PeriodoExercicioPerExe) perExeDao.buscar(PeriodoExercicioPerExe.class, Long.valueOf(2)); //2 é o 2008-2011
    	
    	PoderDao poderDao = new PoderDao(request);
    	List<PoderPod> poderes = poderDao.listar(PoderPod.class, null);
    	for(PoderPod poder : poderes){
			PoderPeriodoExercicioPodPerExePK compId = new PoderPeriodoExercicioPodPerExePK();
			compId.setCodPerExe(perExe.getCodPerExe());
			compId.setCodPod(poder.getCodPod());
			
			PoderPeriodoExercicioPodPerExe podPerExe = new PoderPeriodoExercicioPodPerExe();
			podPerExe.setCompId(compId);
			podPerExe.setIndAtivoPodPerExe("S");
			podPerExe.setPoderPod(poder);
			podPerExe.setPeriodoExercicioPerExe(perExe);
			
			dao.salvar(podPerExe);
		}
    	
    	OrgaoDao orgaoDao = new OrgaoDao(request);
    	List<OrgaoOrg> orgaos = orgaoDao.listar(OrgaoOrg.class, null);
    	for(OrgaoOrg orgao : orgaos){
			OrgaoPeriodoExercicioOrgPerExePK compId = new OrgaoPeriodoExercicioOrgPerExePK();
			compId.setCodPerExe(perExe.getCodPerExe());
			compId.setCodOrg(orgao.getCodOrg());
			
			OrgaoPeriodoExercicioOrgPerExe orgPerExe = new OrgaoPeriodoExercicioOrgPerExe();
			orgPerExe.setCompId(compId);
			orgPerExe.setIndAtivoOrgPerExe("S");
			orgPerExe.setOrgaoOrg(orgao);
			orgPerExe.setPeriodoExercicioPerExe(perExe);
			
			dao.salvar(orgPerExe);
		}
    	
    	UnidadeOrcamentariaDao uoDao = new UnidadeOrcamentariaDao(request);
    	List<UnidadeOrcamentariaUO> uos = uoDao.listar(UnidadeOrcamentariaUO.class, null);
    	for(UnidadeOrcamentariaUO unidade : uos){
    		UnidadeOrcamentariaPeriodoExercicioUoPerExePK compId = new UnidadeOrcamentariaPeriodoExercicioUoPerExePK();
    		compId.setCodPerExe(perExe.getCodPerExe());
    		compId.setCodUo(unidade.getCodUo());
    		
    		UnidadeOrcamentariaPeriodoExercicioUoPerExe uoPerExe = new UnidadeOrcamentariaPeriodoExercicioUoPerExe();
    		uoPerExe.setCompId(compId);
    		uoPerExe.setIndAtivoUoPerExe("S");
    		uoPerExe.setPeriodoExercicioPerExe(perExe);
    		uoPerExe.setUnidadeOrcamentariaUO(unidade);
    		
    		dao.salvar(uoPerExe);
    	}
    }
    
    /**
     * Seta os Tipos de Ações do PPA 2008-2011 para a carga efetuada conforme a regra:<br>
     * Para ações com o número iniciado por 1 - Projeto (ex.: 1144)<br>
     * Para ações com o número iniciado por 2 - Atividade (ex.: 2079)<br>
     * Para ações com o número iniciado por 9 - Operações Especiais (ex.: 9084)<br>
     * 
     * @author aleixo
     * @since 19/07/2007
     * @throws ECARException
     */
    public void setTipoAcoesPPA20082011() throws ECARException{
    	
    	UsuarioUsu usuarioLogado = ((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
    	
    	SisAtributoDao sisDao = new SisAtributoDao(request);
    	
    	SisAtributoSatb projeto = (SisAtributoSatb) sisDao.buscar(SisAtributoSatb.class, Long.valueOf(46));
    	SisAtributoSatb atividade = (SisAtributoSatb) sisDao.buscar(SisAtributoSatb.class, Long.valueOf(47));
    	SisAtributoSatb operacaoEspecial = (SisAtributoSatb) sisDao.buscar(SisAtributoSatb.class, Long.valueOf(48));
    	
        Transaction tx = null;

        try{
            tx = session.beginTransaction();

            StringBuilder s = new StringBuilder();
	    	s.append("select iett from ItemEstruturaIett iett where iett.estruturaEtt.codEtt = 20");
	    	s.append(" and iett.siglaIett like '1%' ");
	    	
	    	Query q = this.session.createQuery(s.toString());
	    	
	    	List<ItemEstruturaIett> acoesProjeto = q.list();
	    	for(ItemEstruturaIett iett : acoesProjeto){
	    			
	    		ItemEstruturaSisAtributoIettSatb atbLivre = new ItemEstruturaSisAtributoIettSatb();
	    		atbLivre.setItemEstruturaIett(iett);
	    		atbLivre.setSisAtributoSatb(projeto);
	    		atbLivre.setDataUltManutencao(Data.getDataAtual());
	    		atbLivre.setUsuarioUsu(usuarioLogado);
	    		atbLivre.atribuirPKPai();
	    		
	    		Set atbsLivres = iett.getItemEstruturaSisAtributoIettSatbs();
	    		
	    		boolean podeInserir = true;
	    		if(atbsLivres != null && !atbsLivres.isEmpty()){
	    			for(Iterator it = atbsLivres.iterator(); it.hasNext();){
	    				ItemEstruturaSisAtributoIettSatb aux = (ItemEstruturaSisAtributoIettSatb) it.next();
	    				
	    				if(aux.getItemEstruturaIett().equals(iett) && aux.getSisAtributoSatb().equals(projeto)){
	    					podeInserir = false;
	    					break;
	    				}
	    			}
	    		}
	    		
	    		if(podeInserir){
	    			session.save(atbLivre);
	    		}
	    	}
	    	
            s = new StringBuilder();
	    	s.append("select iett from ItemEstruturaIett iett where iett.estruturaEtt.codEtt = 20");
	    	s.append(" and iett.siglaIett like '2%' ");
	    	
	    	q = this.session.createQuery(s.toString());
	    	
	    	List<ItemEstruturaIett> acoesAtividade = q.list();
	    	for(ItemEstruturaIett iett : acoesAtividade){
	    			
	    		ItemEstruturaSisAtributoIettSatb atbLivre = new ItemEstruturaSisAtributoIettSatb();
	    		atbLivre.setItemEstruturaIett(iett);
	    		atbLivre.setSisAtributoSatb(atividade);
	    		atbLivre.setDataUltManutencao(Data.getDataAtual());
	    		atbLivre.setUsuarioUsu(usuarioLogado);
	    		atbLivre.atribuirPKPai();
	    		
	    		Set atbsLivres = iett.getItemEstruturaSisAtributoIettSatbs();
	    		
	    		boolean podeInserir = true;
	    		if(atbsLivres != null && !atbsLivres.isEmpty()){
	    			for(Iterator it = atbsLivres.iterator(); it.hasNext();){
	    				ItemEstruturaSisAtributoIettSatb aux = (ItemEstruturaSisAtributoIettSatb) it.next();
	    				
	    				if(aux.getItemEstruturaIett().equals(iett) && aux.getSisAtributoSatb().equals(atividade)){
	    					podeInserir = false;
	    					break;
	    				}
	    			}
	    		}
	    		
	    		if(podeInserir){
	    			session.save(atbLivre);
	    		}
	    	}

            s = new StringBuilder();
	    	s.append("select iett from ItemEstruturaIett iett where iett.estruturaEtt.codEtt = 20");
	    	s.append(" and iett.siglaIett like '9%' ");
	    	
	    	q = this.session.createQuery(s.toString());
	    	
	    	List<ItemEstruturaIett> acoesOperEsp = q.list();
	    	for(ItemEstruturaIett iett : acoesOperEsp){
	    			
	    		ItemEstruturaSisAtributoIettSatb atbLivre = new ItemEstruturaSisAtributoIettSatb();
	    		atbLivre.setItemEstruturaIett(iett);
	    		atbLivre.setSisAtributoSatb(operacaoEspecial);
	    		atbLivre.setDataUltManutencao(Data.getDataAtual());
	    		atbLivre.setUsuarioUsu(usuarioLogado);
	    		atbLivre.atribuirPKPai();
	    		
	    		Set atbsLivres = iett.getItemEstruturaSisAtributoIettSatbs();
	    		
	    		boolean podeInserir = true;
	    		if(atbsLivres != null && !atbsLivres.isEmpty()){
	    			for(Iterator it = atbsLivres.iterator(); it.hasNext();){
	    				ItemEstruturaSisAtributoIettSatb aux = (ItemEstruturaSisAtributoIettSatb) it.next();
	    				
	    				if(aux.getItemEstruturaIett().equals(iett) && aux.getSisAtributoSatb().equals(operacaoEspecial)){
	    					podeInserir = false;
	    					break;
	    				}
	    			}
	    		}
	    		
	    		if(podeInserir){
	    			session.save(atbLivre);
	    		}
	    	}

	    	tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
    }
    
    /**
     * Metodo para efetuar a carga de valores em EfItemEstPrevisaoEfiep
     * conforme dados enviados pelo COP.
     * 
     * @author aleixo
     * @since 27/07/2007
     * @throws ECARException
     */
    public void efetuarCargaValoresCOP() throws ECARException{
    	
    	final String CORRENTE = "C";
    	final String CAPITAL = "K";    	
    	final String TESOURO = "T";
    	final String OUTRAS_FONTES = "O";
    	
    	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
    	
        Transaction tx = null;

        try{
        	//Buscando a estrutura de nível de ação do PPA 2008-2011
        	EstruturaEtt estrutura = (EstruturaEtt) new EstruturaDao(request).buscar(EstruturaEtt.class, Long.valueOf(20));
        	List<EfIettFonteTotEfieft> fontesTotEfieft = new ArrayList<EfIettFonteTotEfieft>();
        	
			Query q = this.session.createQuery("from FonteRecursoFonr");
			List<FonteRecursoFonr> fontesRecurso = (List<FonteRecursoFonr>)q.list();
        	
        	
        	tx = session.beginTransaction();
            
	    	//BufferedReader in = new BufferedReader (new FileReader("/home/aleixo/cop/valores.csv"));
        	//BufferedReader in = new BufferedReader (new FileReader("/home/aleixo/cop/valores-quente-ok.csv"));
        	//BufferedReader in = new BufferedReader (new FileReader("/home/aleixo/cop/valores-novo-quente.csv"));
        	BufferedReader in = new BufferedReader (new FileReader("/home/aleixo/cop/valoresOK.csv"));
        	//BufferedReader in = new BufferedReader (new FileReader("/home/aleixo/cop/teste.csv"));
			String linha="";
			System.out.println("+++++++++++++++ [Início do Processo]++++++++++++++++");
			while ((linha=in.readLine()) != null) {
				String[] campos = linha.split(";");

				// CODPAT ANOEXERCICIO C CODESPECIE T   CODFONTE SUM(VLRPRODDESINICIAL)
				String codProjAtiv = campos[0].trim();
				String anoExe = campos[1].trim();
				String indCapCor = campos[2].trim();
				String codEsp = campos[3].trim();
				String tipoFonte = campos[4].trim();
				String codFonte = campos[5].trim();
				String valor = campos[6].trim();

				Long codFonr = null;
				if(CORRENTE.equals(indCapCor))
					codFonr = new Long(1); //1 Corrente
				else if(CAPITAL.equals(indCapCor))
					codFonr = new Long(2); //2 Capital
				else{
					System.out.println("++++++++ ERRO: Indicador inválido para FonteRecursoFonr: " + indCapCor);
					throw new ECARException("IndCapCor inválido");
				}
				
				Long codTipoFon = null;
				if(TESOURO.equals(tipoFonte))
					codTipoFon = new Long(3);
				else if (OUTRAS_FONTES.equals(tipoFonte))
					codTipoFon = new Long(4);
				else{
					System.out.println("++++++++ ERRO: TipoFonte inválido para RecursoRec: " + tipoFonte);
					throw new ECARException("tipoFonte inválido");
				}

				ItemEstruturaIett projAtiv = itemDao.getIettBySigla(codProjAtiv, estrutura);
				if(projAtiv == null){
					System.out.println("++++++++ ERRO: ItemEstruturaIett é Nulo para valor " + codProjAtiv);
					throw new ECARException("ItemEstruturaIett is null");
				}
				
				ExercicioExe exercicio = (ExercicioExe) this.selectObjeto("ExercicioExe", "descricaoExe", STRING, anoExe, null);
				FonteRecursoFonr fonr = (FonteRecursoFonr) this.selectObjeto("FonteRecursoFonr", "codFonr", LONG, codFonr, null);
				EspecieEsp esp = (EspecieEsp) this.selectObjeto("EspecieEsp", "codEsp", LONG, Long.valueOf(codEsp), null);
				RecursoRec rec = (RecursoRec) this.selectObjeto("RecursoRec", "codRec", LONG, codTipoFon, null);
				FonteFon fon = (FonteFon) this.selectObjeto("FonteFon", "codigoIdentFon", LONG, Long.valueOf(codFonte), null);
				BigDecimal valorAprovado = new BigDecimal(valor).setScale(0);
				
				
				//Verifica se já existe objetos EfIettFonteTotEfieft para esse iett, caso contrário inclui o mesmo no banco.
				ItemEstruturaFonteRecursoDao dao = new ItemEstruturaFonteRecursoDao(request);
				//O ExercicioExe é apenas exigido no método, mas não considerado na consulta, a linha referente
				//ao ExercicioExe está comentado no método.
				List fontesRecursos = dao.getFontesRecursosByExercicio(projAtiv, new ExercicioExe());
				
				if((fontesRecursos.size() == 0) && (!projAtiv.getSiglaIett().substring(0,1).equals("0"))) {
				
					//Tá invertido, o Recurso responde pela Fonte do recurso e vice-versa
					for(Iterator<FonteRecursoFonr> itFontes = fontesRecurso.iterator(); itFontes.hasNext();) {
						
						FonteRecursoFonr fonte = itFontes.next();
						
						EfIettFonteTotEfieft efTotEfieft = new EfIettFonteTotEfieft();
						
						EfIettFonteTotEfieftPK efTotEfieftPK = new EfIettFonteTotEfieftPK();
						efTotEfieftPK.setCodFonr(fonte.getCodFonr());
						efTotEfieftPK.setCodIett(projAtiv.getCodIett());
						
						efTotEfieft.setComp_id(efTotEfieftPK);
						efTotEfieft.setDataInclusaoEfieft(new Date());
						efTotEfieft.setIndAtivoEfieft("S");
						efTotEfieft.setItemEstruturaIett(projAtiv);
						efTotEfieft.setFonteRecursoFonr(fonte);
						
						//Acrescenta o objeto na lista que será salva posteriormente no banco.
						if(!fontesTotEfieft.contains(efTotEfieft))
							fontesTotEfieft.add(efTotEfieft);
					}
					
				}
				
				
				EfItemEstPrevisaoEfiepPK compId = new EfItemEstPrevisaoEfiepPK();
				compId.setCodExe(exercicio.getCodExe());
				compId.setCodFonr(fonr.getCodFonr());
				compId.setCodIett(projAtiv.getCodIett());
				compId.setCodRec(rec.getCodRec());
//				compId.setCodEsp(esp.getCodEsp());
//				compId.setCodFon(fon.getCodFon());
				
				EfItemEstPrevisaoEfiep efiep = new EfItemEstPrevisaoEfiep();
				efiep.setComp_id(compId);
				efiep.setDataInclusaoEfiep(Data.getDataAtual());
				efiep.setEspecieEsp(esp);
				efiep.setExercicioExe(exercicio);
				efiep.setFonteRecursoFonr(fonr);
				efiep.setIndAtivoEfiep("S");
				efiep.setItemEstruturaIett(projAtiv);
				efiep.setRecursoRec(rec);
				efiep.setFonteFon(fon);
				efiep.setValorAprovadoEfiep(valorAprovado);
				
				System.out.println("--> " + projAtiv.getNomeIett());
				session.save(efiep);
			}
			
			//Salva os objetos no banco
			for(EfIettFonteTotEfieft obj : fontesTotEfieft) {
				session.save(obj);
			}				
			
			tx.commit(); //Não esquecer de descomentar o beginTransaction
			System.out.println("+++++++++++++++ [Fim do Processo]++++++++++++++++");
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}	    			
    }
    
    
    /**
     * Efetua a carga das finalidades das ações do PPA 2008-2011 - dados importados do COP.
     * @throws ECARException
     */
    public void efetuarCargaFinalidadesCOP() throws ECARException{
    	
    	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
    	
        Transaction tx = null;

        try{
        	//Buscando a estrutura de nível de ação do PPA 2008-2011
        	EstruturaEtt estrutura = (EstruturaEtt) new EstruturaDao(request).buscar(EstruturaEtt.class, Long.valueOf(20));

			System.out.println("+++++++++++++++ [Início do Processo]++++++++++++++++");
        	tx = session.beginTransaction();
            
			System.out.println("+++++++++++++++ [Lendo arquivo...]++++++++++++++++");
        	BufferedReader in = new BufferedReader (new FileReader("/home/aleixo/cop/FinalidadePat2008-teste.txt"));
			String linha="";
			
			List<String> linhas = new ArrayList<String>();
			
			String finalidade = "";
			while ((linha=in.readLine()) != null) {
				linha = linha.trim();
				
				if("".equals(linha)){
					continue;
				}
				
				//Ações sempre começam com 1, 2 ou 9 na sigla.
				if(linha.charAt(0) == '1' || linha.charAt(0) == '2' || linha.charAt(0) == '9'){
					//nova finalidade
					linhas.add(finalidade.trim());
					finalidade = "";
				}
				finalidade += linha + " "; 
			}
			
			System.out.println("+++++++++++++++ [Identificando \"Sigla\" e \"Finalidade\"...]++++++++++++++++");
			List<CargaFinalidadesPPA> finalidades = new ArrayList<CargaFinalidadesPPA>(); 			
			for(String l : linhas){
				
				if(!"".equals(l)){
					String sigla = l.substring(0,4);
					String fin = l.substring(5,l.length());
					
					CargaFinalidadesPPA cfPPA = new CargaFinalidadesPPA();
					cfPPA.setSigla(sigla);
					cfPPA.setFinalidade(fin);
					
					finalidades.add(cfPPA);					
				}
			}
			
			System.out.println("+++++++++++++++ [Alterando ações...]++++++++++++++++");
			for(CargaFinalidadesPPA cfPPA : finalidades){
				ItemEstruturaIett acao = itemDao.getIettBySigla(cfPPA.getSigla(), estrutura);

				if(acao == null){
					System.out.println("++++++++ ERRO: ItemEstruturaIett é Nulo para valor " + cfPPA.getSigla());
					throw new ECARException("ItemEstruturaIett is null");
				}

				acao.setObjetivoGeralIett(cfPPA.getFinalidade());
				session.update(acao);
			}
			
			System.out.println("+++++++++++++++ [COMMIT]++++++++++++++++");
			tx.commit(); //Não esquecer de descomentar o beginTransaction
			System.out.println("+++++++++++++++ [Fim do Processo]++++++++++++++++");
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null)
				try {
					System.out.println("+++++++++++++++ [ROLLBACK]++++++++++++++++");
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}	    			
    }
    
    /**
     * Método referente ao mantis 12295
     * Carga de Produtos e respectivas metas físicas por local.
     * 
     * @author aleixo
     * @throws ECARException 
     * @since 10/08/2007
     */
    public void efetuarCargaProdutosCOP() throws ECARException{

    	//Buscando a estrutura de nível de projeto/atividade do PPA 2008-2011
    	EstruturaEtt estruturaProdutos = (EstruturaEtt) new EstruturaDao(request).buscar(EstruturaEtt.class, Long.valueOf(21));
    	
        Transaction tx = null;

        try{
        	
        	UsuarioUsu usuarioLogado = ((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
        	
		    ArrayList objetos = new ArrayList();
		    
		    super.inicializarLogBean();

		    msg("++++++++++++++++ [ Processo Iniciado ] ++++++++++++++++++++");
            tx = session.beginTransaction();
            
            List<CargaProdutosPPA> itensArquivo = new ArrayList<CargaProdutosPPA>();
        	List<String> itens = new ArrayList<String>();

        	msg("++++++++++++++++ [ Lendo Arquivo ] ++++++++++++++++++++");
	    	BufferedReader in = new BufferedReader (new FileReader("/home/aleixo/cop/dadosProdutosOK.csv"));
	    	//BufferedReader in = new BufferedReader (new FileReader("/home/aleixo/cop/dadosProdutos2604OK.csv"));
			String linha="";
			
        	msg("++++++++++++++++ [ Adicionando itens do arquivo na lista itensArquivos] ++++++++++++++++++++");
			while ((linha=in.readLine()) != null) {
				
				//ANOEXERCICIO; CODPAT; NOMEPRODUTOORC; DESCRPRODUTOPAT; SIGLAUNIDMEDIDA; QTDEPRODPATPREV; CODREGIAO; CODMUNICIPIO
				//ANOEXERCICIO; CODPAT; CODPRODUTOPAT; Obra(S/N); NOMEPRODUTOORC; DESCRPRODUTOPAT; SIGLAUNIDMEDIDA; QTDEPRODPATPREV; CODREGIAO; CODMUNICIPIO
				String[] campos = linha.split(";");
				
				String anoExe = campos[0].trim();
				String siglaAcao = campos[1].trim();
				String siglaProduto = campos[2].trim();
				String indObra = campos[3].trim();				
				String nomeIndicador = campos[4].trim();
				String nomeProduto = campos[5].trim();
				String unidadeIndicador = campos[6].trim();
				String valorPrevisto = campos[7].trim();
				String codRegiao = campos[8].trim();
				
				String codMunicipio = "";
				if(campos.length == 10)
					codMunicipio = campos[9].trim();
				
				
				CargaProdutosPPA itemArquivo = new CargaProdutosPPA();
				itemArquivo.setAnoExe(anoExe);
				itemArquivo.setCodMunicipio(codMunicipio);
				itemArquivo.setCodRegiao(codRegiao);
				itemArquivo.setIndObra(indObra);
				itemArquivo.setNomeIndicador(Util.removeEspacosDuplicados(nomeIndicador));
				itemArquivo.setNomeProduto(Util.removeEspacosDuplicados(nomeProduto));
				itemArquivo.setSiglaAcao(siglaAcao);
				itemArquivo.setSiglaProduto(siglaProduto);
				itemArquivo.setUnidadeIndicador(unidadeIndicador);
				itemArquivo.setValorPrevisto(valorPrevisto);
				
				itensArquivo.add(itemArquivo);
			}
			
        	msg("++++++++++++++++ [ Ordenando itensArquivo pela siglaAcao e siglaProduto ] ++++++++++++++++++++");
        	
        	Collections.sort(itensArquivo, new Comparator(){

				public int compare(Object o1, Object o2) {
					CargaProdutosPPA c1 = (CargaProdutosPPA) o1;
					CargaProdutosPPA c2 = (CargaProdutosPPA) o2;
					
					String s1 = c1.getSiglaAcao();
					String s2 = c2.getSiglaAcao();
					
					return s1.compareTo(s2);
				}
        		
        	});

        	
		    List<CargaProdutosPPA> itensSalvos = new ArrayList<CargaProdutosPPA>();
		    List<CargaProdutosPPA> itensIgnorados = new ArrayList<CargaProdutosPPA>();

		    msg("++++++++++++++++ [ Percorrendo itensArquivo para inserção de itens ] ++++++++++++++++++++");
		    
		    String siglaAcaoAux = "";
			int siglaProdAux = 1;

		    
			for(CargaProdutosPPA itemArquivo : itensArquivo){
				
				/* Obtendo local para cadastrar a abrangencia (pegando pela Região, que já engloba o municipio)*/
				StringBuilder select = new StringBuilder();
				select.append("select local from LocalItemLit local");
				select.append(" where local.codPlanejamentoLit = :codLocal");
				select.append("   and local.localGrupoLgp.codLgp = 11");
				
				Query q = this.session.createQuery(select.toString());
				q.setString("codLocal", itemArquivo.getCodRegiao()); 
				
				q.setMaxResults(1);
				
				Object o = q.uniqueResult();
				if(o == null){
					lancarErro("Erro ao pesquisar abrangencia: Local invalido para Regiao: " + itemArquivo.getCodRegiao());
				}
				LocalItemLit localAbrangencia = (LocalItemLit) o;
				
				
				
				if("".equals(itemArquivo.getValorPrevisto())){
					lancarErro("Valor inválido: [" + itemArquivo.getValorPrevisto() + "]");
				}
				
				ItemEstruturaIett iett = null;
				
				//String chave = siglaAcao + "_" + siglaProduto + "_" + indObra;
				String chave = itemArquivo.getSiglaAcao() + "_" + itemArquivo.getIndObra() + "_" + itemArquivo.getNomeProduto();
				
				
				if(!itens.contains(chave)){
					itensSalvos.add(itemArquivo);
					itens.add(chave);
					
					if(!siglaAcaoAux.equals(itemArquivo.getSiglaAcao())){
						siglaAcaoAux = itemArquivo.getSiglaAcao();
						siglaProdAux = 1;
					}
					
					// Gravar item e indicador
					
			    	//Obtendo item Pai (do nível de ação do ppa 2008-2011
			    	String sqlBuscaPai = "select pai from ItemEstruturaIett pai where siglaIett = :sigla and pai.estruturaEtt.codEtt = :codEttPai";
			    	q = this.session.createQuery(sqlBuscaPai);
		    		q.setString("sigla", itemArquivo.getSiglaAcao());
		    		q.setLong("codEttPai", 20); //20 é Ação do PPA 2008-2011
		    		q.setMaxResults(1);
	    			ItemEstruturaIett iettPai = (ItemEstruturaIett) q.uniqueResult();
	    			if(iettPai == null)
	    				lancarErro("Item Pai é Nulo para sigla " + itemArquivo.getSiglaAcao());
	    			
			    	// Setando item referente ao projeto/atividade
			    	iett = new ItemEstruturaIett();
	    			
	    			iett.setIndAtivoIett("S");
	    			iett.setIndBloqPlanejamentoIett("N");
	    			iett.setIndCriticaIett("N");
	    			iett.setIndMonitoramentoIett("N");
	    			iett.setUsuarioUsuByCodUsuIncIett(usuarioLogado);
	    			iett.setDataInclusaoIett(Data.getDataAtual());
	    			iett.setDataUltManutencaoIett(Data.getDataAtual());
	    			iett.setDataInicioIett(Data.parseDate("01/01/2008"));
	    			iett.setDataTerminoIett(Data.parseDate("31/12/2011"));
	    			iett.setNivelIett(Integer.valueOf(4));
	    			
	    			
	    			iett.setItemEstruturaIett(iettPai);
	    			//iett.setSiglaIett(itemArquivo.getSiglaProduto());
	    			iett.setSiglaIett(String.valueOf(siglaProdAux));
	    			iett.setNomeIett(itemArquivo.getNomeProduto());

	   				iett.setEstruturaEtt(estruturaProdutos);

	    			/* Buscando unidade orçamentárias pela unidade orçamentária da ação */
					UnidadeOrcamentariaUO unidade = iettPai.getUnidadeOrcamentariaUO();
	    			if(unidade == null)
	    				lancarErro("Unidade Orçamentária do Item Pai é Nulo");
					iett.setUnidadeOrcamentariaUO(unidade);

	    			/* Buscando orgao pelo orgao da acao */
					OrgaoOrg orgao = iettPai.getOrgaoOrgByCodOrgaoResponsavel1Iett();
	    			if(orgao == null)
	    				lancarErro("Orgao do Item Pai é Nulo");
	    			
					iett.setOrgaoOrgByCodOrgaoResponsavel1Iett(orgao);
	                
	                /* Salvando item */
	    			//itemDao.salvar(request, iett);
	    			//Como preciso salvar todos os itens em transacao, foi "copiado" o método salvar e colocado todos os itens em transacao.
	    			
		            session.save(iett);
					objetos.add(iett);
					msg("Salvando Item --> " + itemArquivo.getSiglaAcao() + " - " + itemArquivo.getSiglaProduto() + " - " + itemArquivo.getNomeProduto());
					
					siglaProdAux++;
					
					//Salvar Indicador de Obra.
					Long codIndObra = (Dominios.SIM.equals(itemArquivo.getIndObra()) ? Long.valueOf(51) : Long.valueOf(52));
					SisAtributoSatb obra = (SisAtributoSatb) buscar(SisAtributoSatb.class, codIndObra);
					
					ItemEstruturaSisAtributoIettSatb atbLivreObra = new ItemEstruturaSisAtributoIettSatb();
					atbLivreObra.setItemEstruturaIett(iett);
					atbLivreObra.setSisAtributoSatb(obra);
					atbLivreObra.setDataUltManutencao(Data.getDataAtual());
					atbLivreObra.setUsuarioUsu(usuarioLogado);
					atbLivreObra.atribuirPKPai();
					
					session.save(atbLivreObra);
					objetos.add(atbLivreObra);
					
					// Salvando a Abrangência
					ItemEstrutLocalIettl abrangencia = new ItemEstrutLocalIettl();
					ItemEstrutLocalIettlPK abrgCompId = new ItemEstrutLocalIettlPK();
					
					abrgCompId.setCodIett(iett.getCodIett());
					abrgCompId.setCodLit(localAbrangencia.getCodLit());
					
					abrangencia.setComp_id(abrgCompId);
					abrangencia.setDataInclusaoIettl(Data.getDataAtual());
					abrangencia.setItemEstruturaIett(iett);
					abrangencia.setLocalItemLit(localAbrangencia);
					abrangencia.setUsuarioUsuManutencao(usuarioLogado);
					session.save(abrangencia);
					objetos.add(abrangencia);

					//
			        // controlar as permissoes passando o item e a lista das funcoes de acompanhamento velhas (vai ser uma lista vazia)
			        //
					new ControlePermissao().atualizarPermissoesItemEstrutura(iett, null, session, true, request);
					
					// gravar permissão para o usuário que criou o item 
					ItemEstrutUsuarioIettus itemEstrutUsuario = new ItemEstrutUsuarioIettus();
			
					itemEstrutUsuario.setItemEstruturaIett(iett);
					itemEstrutUsuario.setItemEstruturaIettOrigem(iett);
					itemEstrutUsuario.setCodTpPermIettus(ControlePermissao.PERMISSAO_USUARIO);
					itemEstrutUsuario.setUsuarioUsu(usuarioLogado);
			
					itemEstrutUsuario.setIndLeituraIettus("S");
					itemEstrutUsuario.setIndEdicaoIettus("S");
					itemEstrutUsuario.setIndExcluirIettus("S");
					
					itemEstrutUsuario.setIndAtivMonitIettus("N");
					itemEstrutUsuario.setIndDesatMonitIettus("N");
					itemEstrutUsuario.setIndBloqPlanIettus("N");
					itemEstrutUsuario.setIndDesblPlanIettus("N");
					itemEstrutUsuario.setIndInfAndamentoIettus("N");
					itemEstrutUsuario.setIndEmitePosIettus("N");
					itemEstrutUsuario.setIndProxNivelIettus("N");
					
					itemEstrutUsuario.setDataInclusaoIettus(Data.getDataAtual());

					session.save(itemEstrutUsuario);
					objetos.add(itemEstrutUsuario);
				}
				else {
					itensIgnorados.add(itemArquivo);
				}
			}
			
		    msg("++++++++++++++++ [ Realizando Commit ] ++++++++++++++++++++");
			tx.commit(); // Ao descomentar essa linha, não esquecer de descomentar o beginTransaction

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC");
				Iterator itObj = objetos.iterator();

				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
		    msg("++++++++++++++++ [ Processo Finalizado Com Sucesso ] ++++++++++++++++++++");
			
		} catch (Exception e) {
			e.printStackTrace();
		    msg("++++++++++++++++ [ Erro no Processo. Executando RollBack ] ++++++++++++++++++++");
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}	    			
    }
    
    /**
     * Método complementar para o método efetuarCargaProdutosCOP().
     * Este método carrega no sistema os valores do previsto por local para os produtos do COP.
     * 
     * @throws ECARException
     */
    public void efetuarCargaIndicadoresProdutosCOP() throws ECARException{

    	//ItemEstruturaDao itemDao = new ItemEstruturaDao(request);

    	//Buscando a estrutura de nível de projeto/atividade do PPA 2008-2011
    	EstruturaEtt estruturaProdutos = (EstruturaEtt) new EstruturaDao(request).buscar(EstruturaEtt.class, Long.valueOf(21));
    	
        Transaction tx = null;

        try{
        	
        	UsuarioUsu usuarioLogado = ((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
        	
		    ArrayList objetos = new ArrayList();
		    
		    super.inicializarLogBean();

		    msg("++++++++++++++++ [ Processo Iniciado ] ++++++++++++++++++++");
            tx = session.beginTransaction();
            
		    msg("++++++++++++++++ [ Lendo Arquivo ] ++++++++++++++++++++");
	    	BufferedReader in = new BufferedReader (new FileReader("/home/aleixo/cop/dadosProdutosOK.csv"));
	    	//BufferedReader in = new BufferedReader (new FileReader("/home/aleixo/cop/dadosProdutos2604OK.csv"));
	    	
	    	List<String> indicadoresIncluidos = new ArrayList<String>();  
			String linha="";
			while ((linha=in.readLine()) != null) {
				
				//ANOEXERCICIO; CODPAT; CODPRODUTOPAT; Obra(S/N); NOMEPRODUTOORC; DESCRPRODUTOPAT; SIGLAUNIDMEDIDA; QTDEPRODPATPREV; CODREGIAO; CODMUNICIPIO
				String[] campos = linha.split(";");
				
				String anoExe = campos[0].trim();
				String siglaAcao = campos[1].trim();
				String siglaProduto = campos[2].trim();
				String indObra = campos[3].trim();				
				String nomeIndicador = Util.removeEspacosDuplicados(campos[4].trim());
				String nomeProduto = Util.removeEspacosDuplicados(campos[5].trim());
				String unidadeIndicador = campos[6].trim();
				String valorPrevisto = campos[7].trim();
				String codRegiao = campos[8].trim();
				
				String codMunicipio = "";
				if(campos.length == 10)
					codMunicipio = campos[9].trim();

				/* Buscando local do item*/
				StringBuilder select = new StringBuilder();
				select.append("select local from LocalItemLit local");
				select.append(" where local.codPlanejamentoLit = :codLocal");
				select.append("   and (local.localGrupoLgp.codLgp = 8 or local.localGrupoLgp.codLgp = 11)");
				
				Query q = this.session.createQuery(select.toString());
				q.setString("codLocal", (!"".equals(codMunicipio) ? codMunicipio : codRegiao)); //Se existir o municipio, o local vai ser o municipio, senão vai ser a região
				
				q.setMaxResults(1);
				
				Object o = q.uniqueResult();
				if(o == null){
					if(!"".equals(codMunicipio))
						lancarErro("Local invalido para Municipio: " + codMunicipio);
					else
						lancarErro("Local invalido para Regiao: " + codRegiao);
				}
				LocalItemLit local = (LocalItemLit) o;
				
				if("".equals(valorPrevisto)){
					lancarErro("Valor inválido: [" + valorPrevisto + "]");
				}
				
				ItemEstruturaIett iett = null;
				
				// Gravar somente o indicador
				//buscar o item para gravar o indicador
				//iett = (ItemEstruturaIett) this.selectObjeto("ItemEstruturaIett", "siglaIett", STRING, siglaProduto, session);
				select = new StringBuilder();
				select.append("select item from ItemEstruturaIett item where item.nomeIett = :nome and item.estruturaEtt.codEtt = :codEtt and item.itemEstruturaIett.siglaIett = :siglaPai");
				
				q = session.createQuery(select.toString());
				q.setString("nome", nomeProduto);
				q.setString("siglaPai", siglaAcao);
				q.setLong("codEtt", estruturaProdutos.getCodEtt().longValue());
				
				List itensTemp = q.list();
				if(itensTemp != null && !itensTemp.isEmpty()){
					iett = (ItemEstruturaIett) itensTemp.get(0);
				}
				else {
					lancarErro("Item Nulo para: " + nomeProduto + " para siglaPai: " + siglaAcao);
					//continue;
				}
				
				//Incluir o indicador!
				
				//Verificar se o indicador existe
				String chaveIndicador = iett.getItemEstruturaIett().getSiglaIett() + "_" + "_" + iett.getNomeIett() + "_" + nomeIndicador;
				
				if(!indicadoresIncluidos.contains(chaveIndicador)){
					
					indicadoresIncluidos.add(chaveIndicador);
					
					//Cadastrar indicador
					ItemEstrtIndResulIettr indicador = new ItemEstrtIndResulIettr();
					indicador.setItemEstruturaIett(iett);
			    	indicador.setNomeIettir(nomeIndicador);
			    	indicador.setUnidMedidaIettr(unidadeIndicador);
			    	indicador.setIndProjecaoIettr(Dominios.NAO);
			    	indicador.setIndAtivoIettr(Dominios.SIM);
			    	indicador.setIndPrevPorLocal(Dominios.SIM);
			    	indicador.setIndRealPorLocal(Dominios.NAO);
			    	indicador.setIndTipoQtde(Dominios.IETTR_QUANTIDADE);
			    	indicador.setUsuarioUsuManutencao(usuarioLogado);
			    	indicador.setDataUltManutencao(Data.getDataAtual());
			    	
			    	//41 é Meta Física PPA
			    	SisAtributoSatb metaFisicaPPA = (SisAtributoSatb) this.selectObjeto("SisAtributoSatb", "codSatb", LONG, Long.valueOf(41), session);
			    	indicador.setSisAtributoSatb(metaFisicaPPA);

			    	//itemEstrtIndResul.setIndValorFinalIettr(null); Não preencher
			    	//itemEstrtIndResul.setIndAcumulavelIettr(Pagina.getParamStr(request, "indAcumulavelIettr")); não preencher
			    	
			    	session.save(indicador);
			    	objetos.add(indicador);
				}
			}
			
		    msg("++++++++++++++++ [ Realizando Commit ] ++++++++++++++++++++");
			tx.commit(); // Ao descomentar essa linha, não esquecer de descomentar o beginTransaction

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC");
				Iterator itObj = objetos.iterator();

				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
		    msg("++++++++++++++++ [ Processo Finalizado Com Sucesso ] ++++++++++++++++++++");
			
		} catch (Exception e) {
			e.printStackTrace();
		    msg("++++++++++++++++ [ Erro no Processo. Executando RollBack ] ++++++++++++++++++++");
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}	    			
    }
    
    /**
     *
     * @throws ECARException
     */
    public void efetuarCargaValoresIndicadoresProdutosCOP() throws ECARException{
    	//ItemEstruturaDao itemDao = new ItemEstruturaDao(request);

    	//Buscando a estrutura de nível de projeto/atividade do PPA 2008-2011
    	EstruturaEtt estruturaProdutos = (EstruturaEtt) new EstruturaDao(request).buscar(EstruturaEtt.class, Long.valueOf(21));
    	
        Transaction tx = null;

        try{
        	
        	//UsuarioUsu usuarioLogado = ((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
        	
		    ArrayList objetos = new ArrayList();
		    
		    super.inicializarLogBean();

		    msg("++++++++++++++++ [ Processo Iniciado ] ++++++++++++++++++++");
            tx = session.beginTransaction();
            
		    msg("++++++++++++++++ [ Lendo Arquivo ] ++++++++++++++++++++");
	    	BufferedReader in = new BufferedReader (new FileReader("/home/aleixo/cop/dadosProdutosOK.csv"));
	    	
			String linha="";
			while ((linha=in.readLine()) != null) {
				
				//ANOEXERCICIO; CODPAT; CODPRODUTOPAT; Obra(S/N); NOMEPRODUTOORC; DESCRPRODUTOPAT; SIGLAUNIDMEDIDA; QTDEPRODPATPREV; CODREGIAO; CODMUNICIPIO
				String[] campos = linha.split(";");
				
				String anoExe = campos[0].trim();
				String siglaAcao = campos[1].trim();
				String siglaProduto = campos[2].trim();
				String indObra = campos[3].trim();				
				String nomeIndicador = Util.removeEspacosDuplicados(campos[4].trim());
				String nomeProduto = Util.removeEspacosDuplicados(campos[5].trim());
				String unidadeIndicador = campos[6].trim();
				String valorPrevisto = campos[7].trim();
				String codRegiao = campos[8].trim();
				
				String codMunicipio = "";
				if(campos.length == 10)
					codMunicipio = campos[9].trim();

				ExercicioExe exercicio = (ExercicioExe) this.selectObjeto("ExercicioExe", "descricaoExe", STRING, anoExe, session);
				
				/* Buscando local do item*/
				StringBuilder select = new StringBuilder();
				select.append("select local from LocalItemLit local");
				select.append(" where local.codPlanejamentoLit = :codLocal");
				select.append("   and (local.localGrupoLgp.codLgp = 8 or local.localGrupoLgp.codLgp = 11)");
				
				Query q = this.session.createQuery(select.toString());
				q.setString("codLocal", (!"".equals(codMunicipio) ? codMunicipio : codRegiao)); //Se existir o municipio, o local vai ser o municipio, senão vai ser a região
				
				q.setMaxResults(1);
				
				Object o = q.uniqueResult();
				if(o == null){
					if(!"".equals(codMunicipio))
						lancarErro("Local invalido para Municipio: " + codMunicipio);
					else
						lancarErro("Local invalido para Regiao: " + codRegiao);
				}
				LocalItemLit local = (LocalItemLit) o;
				
				if("".equals(valorPrevisto)){
					lancarErro("Valor inválido: [" + valorPrevisto + "]");
				}
				
				ItemEstruturaIett iett = null;
				
				select = new StringBuilder();
				select.append("select item from ItemEstruturaIett item where item.nomeIett = :nome and item.estruturaEtt.codEtt = :codEtt and item.itemEstruturaIett.siglaIett = :siglaPai");
				
				q = session.createQuery(select.toString());
				q.setString("nome", nomeProduto);
				q.setString("siglaPai", siglaAcao);
				q.setLong("codEtt", estruturaProdutos.getCodEtt().longValue());
				
				List itensTemp = q.list();
				if(itensTemp != null && !itensTemp.isEmpty()){
					iett = (ItemEstruturaIett) itensTemp.get(0);
				}
				else {
					lancarErro("Item Nulo para: " + nomeProduto + " para siglaPai: " + siglaAcao);
					//continue;
				}
				
				//Verificar se o indicador existe
				select = new StringBuilder();
				select.append("select indicador from ItemEstrtIndResulIettr indicador");
				select.append(" where indicador.itemEstruturaIett.codIett = :item");
				select.append("   and lower(indicador.nomeIettir) = :nomeInd");
				
				q = this.session.createQuery(select.toString());
				q.setLong("item", iett.getCodIett().longValue());
				q.setString("nomeInd", nomeIndicador.toLowerCase());
				
				List indicadores = q.list();
				
				ItemEstrtIndResulIettr indicador = null;
				if(indicadores != null && !indicadores.isEmpty()){
					//Indicador existe.
					indicador = (ItemEstrtIndResulIettr) indicadores.get(0);
				}
				else {
					lancarErro("Indicador = null para: " + nomeIndicador);
				}
				
				ItemEstrtIndResulLocalIettirl indResulLocal = new ItemEstrtIndResulLocalIettirl();
				indResulLocal.setDataInclusaoIettirl(Data.getDataAtual());
				//Mantis 0010128 - Qtd prevista não é mais informado por exercício
				//indResulLocal.setExercicioExe(exercicio);
				indResulLocal.setIndAtivoIettirl(Dominios.SIM);
				//Mantis 0010128 - Qtd prevista não é mais informado por exercício
				//Previsto por local não e mais associado diretamente ao indicador 
				//indResulLocal.setItemEsrtIndResulIettr(indicador);
				indResulLocal.setLocalItemLit(local);
				indResulLocal.setQtdPrevistaIettirl(Double.valueOf(valorPrevisto));
				
				session.save(indResulLocal);
				objetos.add(indResulLocal);
				
			}
			
		    msg("++++++++++++++++ [ Realizando Commit ] ++++++++++++++++++++");
			tx.commit(); // Ao descomentar essa linha, não esquecer de descomentar o beginTransaction

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC");
				Iterator itObj = objetos.iterator();

				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
		    msg("++++++++++++++++ [ Processo Finalizado Com Sucesso ] ++++++++++++++++++++");
			
		} catch (Exception e) {
			e.printStackTrace();
		    msg("++++++++++++++++ [ Erro no Processo. Executando RollBack ] ++++++++++++++++++++");
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}	    			
    }
    
    /**
     *
     * @throws ECARException
     */
    public void efetuarTotalizacaoIndicadoresProdutosCOP() throws ECARException{

    	//ItemEstruturaDao itemDao = new ItemEstruturaDao(request);

    	//Buscando a estrutura de nível de projeto/atividade do PPA 2008-2011
    	EstruturaEtt estruturaProdutos = (EstruturaEtt) new EstruturaDao(request).buscar(EstruturaEtt.class, Long.valueOf(21));
    	ExercicioExe exe2008 = (ExercicioExe) buscar(ExercicioExe.class, Long.valueOf(6));
    	ExercicioExe exe2009 = (ExercicioExe) buscar(ExercicioExe.class, Long.valueOf(7));
    	ExercicioExe exe2010 = (ExercicioExe) buscar(ExercicioExe.class, Long.valueOf(8));
    	ExercicioExe exe2011 = (ExercicioExe) buscar(ExercicioExe.class, Long.valueOf(9));
    	
        Transaction tx = null;

        try{
        	
        	//UsuarioUsu usuarioLogado = ((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
        	
		    List<CargaProdutosPPA> itensArquivo = new ArrayList<CargaProdutosPPA>();
		    
		    msg("++++++++++++++++ [ Processo Iniciado ] ++++++++++++++++++++");
            tx = session.beginTransaction();
            
		    msg("++++++++++++++++ [ Lendo Arquivo ] ++++++++++++++++++++");
	    	BufferedReader in = new BufferedReader (new FileReader("/home/aleixo/cop/dadosProdutosOK.csv"));
			String linha="";
			while ((linha=in.readLine()) != null) {
				
				//ANOEXERCICIO; CODPAT; CODPRODUTOPAT; Obra(S/N); NOMEPRODUTOORC; DESCRPRODUTOPAT; SIGLAUNIDMEDIDA; QTDEPRODPATPREV; CODREGIAO; CODMUNICIPIO
				String[] campos = linha.split(";");
				
				String anoExe = campos[0].trim();
				String siglaAcao = campos[1].trim();
				String siglaProduto = campos[2].trim();
				String indObra = campos[3].trim();				
				String nomeIndicador = campos[4].trim();
				String nomeProduto = campos[5].trim();
				String unidadeIndicador = campos[6].trim();
				String valorPrevisto = campos[7].trim();
				String codRegiao = campos[8].trim();
				
				String codMunicipio = "";
				if(campos.length == 10)
					codMunicipio = campos[9].trim();
				
				
				CargaProdutosPPA itemArquivo = new CargaProdutosPPA();
				itemArquivo.setAnoExe(anoExe);
				itemArquivo.setCodMunicipio(codMunicipio);
				itemArquivo.setCodRegiao(codRegiao);
				itemArquivo.setIndObra(indObra);
				itemArquivo.setNomeIndicador(Util.removeEspacosDuplicados(nomeIndicador));
				itemArquivo.setNomeProduto(Util.removeEspacosDuplicados(nomeProduto));
				itemArquivo.setSiglaAcao(siglaAcao);
				itemArquivo.setSiglaProduto(siglaProduto);
				itemArquivo.setUnidadeIndicador(unidadeIndicador);
				itemArquivo.setValorPrevisto(valorPrevisto);
				
				itensArquivo.add(itemArquivo);
			}
			
			Set itens = estruturaProdutos.getItemEstruturaIetts();
			if(itens != null && !itens.isEmpty()){
				for(Iterator it = itens.iterator(); it.hasNext();){
					ItemEstruturaIett item = (ItemEstruturaIett) it.next();

					String chaveItem = item.getItemEstruturaIett().getSiglaIett() + "_" + "_" + item.getNomeIett();
					
					List<ItemEstrtIndResulIettr> indicadores = new ArrayList<ItemEstrtIndResulIettr>(item.getItemEstrtIndResulIettrs());
					
					msg("--> Percorrendo indicadores do item " + item.getNomeIett());

					for(ItemEstrtIndResulIettr indicador : indicadores){

						double previsto2008 = 0;
						double previsto2009 = 0;
						double previsto2010 = 0;
						double previsto2011 = 0;
						
						for(CargaProdutosPPA itemArq : itensArquivo){
							String chaveItemArq = itemArq.getSiglaAcao() + "_" + "_" + itemArq.getNomeProduto();
							
							if(chaveItem.equals(chaveItemArq)){					
								if(indicador.getNomeIettir().equals(itemArq.getNomeIndicador())){
									if("2008".equals(itemArq.getAnoExe())){
										previsto2008 += Double.valueOf(itemArq.getValorPrevisto()).doubleValue();
									}
									if("2009".equals(itemArq.getAnoExe())){
										previsto2009 += Double.valueOf(itemArq.getValorPrevisto()).doubleValue();
									}
									if("2010".equals(itemArq.getAnoExe())){
										previsto2010 += Double.valueOf(itemArq.getValorPrevisto()).doubleValue();
									}
									if("2011".equals(itemArq.getAnoExe())){
										previsto2011 += Double.valueOf(itemArq.getValorPrevisto()).doubleValue();
									}
								}
							}
						}
						
						//Gravar valores previstos para cada ano do indicador
						
						//Gravando para: 2008
						if(previsto2008 > 0){
							msg("-------> Gravando total para 2008: " + previsto2008);
							
							ItemEstrutFisicoIettfPK compId2008 = new ItemEstrutFisicoIettfPK();
							compId2008.setCodExe(exe2008.getCodExe());
							compId2008.setCodIettir(indicador.getCodIettir());
	
							ItemEstrutFisicoIettf iettf2008 = new ItemEstrutFisicoIettf();
							
							//FIXME: Ajustar Carga de Itens
							/* Mantis 0010128 - Qtd prevista não é mais informado por exercício
							 * Mudou a pk. não usa mais chave composta
							 * */
							//iettf2008.setComp_id(compId2008);
							iettf2008.setDataInclusaoIettf(Data.getDataAtual());
							//iettf2008.setExercicioExe(exe2008);
							iettf2008.setIndAtivoIettf(Dominios.SIM);
							iettf2008.setItemEstrtIndResulIettr(indicador);
							iettf2008.setQtdPrevistaIettf(Double.valueOf(previsto2008));
							
							session.save(iettf2008);
						}
						
						//Gravando para: 2009
						if(previsto2009 > 0){
							msg("-------> Gravando total para 2009: " + previsto2009);

							ItemEstrutFisicoIettfPK compId2009 = new ItemEstrutFisicoIettfPK();
							compId2009.setCodExe(exe2009.getCodExe());
							compId2009.setCodIettir(indicador.getCodIettir());
	
							ItemEstrutFisicoIettf iettf2009 = new ItemEstrutFisicoIettf();
							//FIXME: Ajustar Carga de Itens
							/* Mantis 0010128 - Qtd prevista não é mais informado por exercício
							 * Mudou a pk. não usa mais chave composta
							 * */
							//iettf2009.setComp_id(compId2009);
							iettf2009.setDataInclusaoIettf(Data.getDataAtual());
							//iettf2009.setExercicioExe(exe2009);
							iettf2009.setIndAtivoIettf(Dominios.SIM);
							iettf2009.setItemEstrtIndResulIettr(indicador);
							iettf2009.setQtdPrevistaIettf(Double.valueOf(previsto2009));
							
							session.save(iettf2009);
						}

						//Gravando para: 2010
						if(previsto2010 > 0){
							msg("-------> Gravando total para 2010: " + previsto2010);

							ItemEstrutFisicoIettfPK compId2010 = new ItemEstrutFisicoIettfPK();
							compId2010.setCodExe(exe2010.getCodExe());
							compId2010.setCodIettir(indicador.getCodIettir());
	
							ItemEstrutFisicoIettf iettf2010 = new ItemEstrutFisicoIettf();
							//FIXME: Ajustar Carga de Itens
							/* Mantis 0010128 - Qtd prevista não é mais informado por exercício
							 * Mudou a pk. não usa mais chave composta
							 * */
							//iettf2010.setComp_id(compId2010);
							iettf2010.setDataInclusaoIettf(Data.getDataAtual());
							//iettf2010.setExercicioExe(exe2010);
							iettf2010.setIndAtivoIettf(Dominios.SIM);
							iettf2010.setItemEstrtIndResulIettr(indicador);
							iettf2010.setQtdPrevistaIettf(Double.valueOf(previsto2010));
							
							session.save(iettf2010);
						}

						//Gravando para: 2011
						if(previsto2011 > 0){
							msg("-------> Gravando total para 2011: " + previsto2011);
							
							ItemEstrutFisicoIettfPK compId2011 = new ItemEstrutFisicoIettfPK();
							compId2011.setCodExe(exe2011.getCodExe());
							compId2011.setCodIettir(indicador.getCodIettir());
	
							ItemEstrutFisicoIettf iettf2011 = new ItemEstrutFisicoIettf();
							//FIXME: Ajustar Carga de Itens
							/* Mantis 0010128 - Qtd prevista não é mais informado por exercício
							 * Mudou a pk. não usa mais chave composta
							 * */
							//iettf2011.setComp_id(compId2011);
							iettf2011.setDataInclusaoIettf(Data.getDataAtual());
							//iettf2011.setExercicioExe(exe2011);
							iettf2011.setIndAtivoIettf(Dominios.SIM);
							iettf2011.setItemEstrtIndResulIettr(indicador);
							iettf2011.setQtdPrevistaIettf(Double.valueOf(previsto2011));
							
							session.save(iettf2011);
						}
					}
				}
			}

			
		    msg("++++++++++++++++ [ Realizando Commit ] ++++++++++++++++++++");
			tx.commit(); // Ao descomentar essa linha, não esquecer de descomentar o beginTransaction

			msg("++++++++++++++++ [ Processo Finalizado Com Sucesso ] ++++++++++++++++++++");
			
		} catch (Exception e) {
			e.printStackTrace();
		    msg("++++++++++++++++ [ Erro no Processo. Executando RollBack ] ++++++++++++++++++++");
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}	    			
    }

    /**
     * Método para listar os produtos do COP
     * @return
     * @throws ECARException
     */
    public List listarCargaProdutosCOP() throws ECARException{

        try{
		    msg("++++++++++++++++ [ Processo Iniciado ] ++++++++++++++++++++");
            
        	List<CargaFinalidadesPPA> itens = new ArrayList<CargaFinalidadesPPA>();
        	List<String> listVerif = new ArrayList<String>();
		    msg("++++++++++++++++ [ Lendo Arquivo ] ++++++++++++++++++++");
	    	BufferedReader in = new BufferedReader (new FileReader("/home/aleixo/cop/dadosProdutosOK.csv"));
			String linha="";
			while ((linha=in.readLine()) != null) {
				
				//ANOEXERCICIO; CODPAT; NOMEPRODUTOORC; DESCRPRODUTOPAT; SIGLAUNIDMEDIDA; QTDEPRODPATPREV; CODREGIAO; CODMUNICIPIO
				//ANOEXERCICIO; CODPAT; CODPRODUTOPAT; Obra(S/N); NOMEPRODUTOORC; DESCRPRODUTOPAT; SIGLAUNIDMEDIDA; QTDEPRODPATPREV; CODREGIAO; CODMUNICIPIO
				String[] campos = linha.split(";");
				
				String anoExe = campos[0].trim();
				String siglaAcao = campos[1].trim();
				String siglaProduto = campos[2].trim();
				String indObra = campos[3].trim();				
				String nomeIndicador = campos[4].trim();
				String nomeProduto = campos[5].trim();
				String unidadeIndicador = campos[6].trim();
				String valorPrevisto = campos[7].trim();
				String codRegiao = campos[8].trim();
				
				String codMunicipio = "";
				if(campos.length == 10)
					codMunicipio = campos[9].trim();
				
				CargaFinalidadesPPA item = new CargaFinalidadesPPA();
				
				item.setFinalidade(nomeProduto + " - " + siglaProduto + " - " + anoExe);
				item.setSigla(siglaAcao);
				
				String chave = nomeProduto;
				if(!listVerif.contains(chave)){
					listVerif.add(chave);
					itens.add(item);
				}

			}
			
			Collections.sort(itens, new Comparator(){
				public int compare (Object o1, Object o2){
					CargaFinalidadesPPA c1 = (CargaFinalidadesPPA)o1;	
					CargaFinalidadesPPA c2 = (CargaFinalidadesPPA)o2;	
					
					return c1.getFinalidade().compareTo(c2.getFinalidade()); 
				}
			});
			
			Collections.sort(itens, new Comparator(){
				public int compare (Object o1, Object o2){
					CargaFinalidadesPPA c1 = (CargaFinalidadesPPA)o1;	
					CargaFinalidadesPPA c2 = (CargaFinalidadesPPA)o2;	
					
					return c1.getSigla().compareTo(c2.getSigla()); 
				}
			});

		    msg("++++++++++++++++ [ Início do Arquivo ] ++++++++++++++++++++");
		    
			FileOutputStream arquivo = new FileOutputStream("/home/aleixo/Desktop/itensRepProd_NOVO.txt");

			for(CargaFinalidadesPPA item : itens){
				String total = item.getSigla() + " - " + item.getFinalidade() + "\n";
				arquivo.write(total.getBytes(Dominios.ENCODING_DEFAULT));
				arquivo.flush();
			}

			arquivo.close();
		    
		    msg("++++++++++++++++ [ Fim do Arquivo ] ++++++++++++++++++++");

		    msg("++++++++++++++++ [ Processo Finalizado Com Sucesso ] ++++++++++++++++++++");
			
		    return itens;
		} catch (Exception e) {
			e.printStackTrace();
		    msg("++++++++++++++++ [ Erro no Processo. Executando RollBack ] ++++++++++++++++++++");
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}	    			
    }
    
    /**
     * Efetuar carga do valor default ("Não") para o atributo livre "Integralização de Capital" dos
     * itens de nível de ação do PPA 2008-2011.
     * 
     * @author aleixo
     * @since 20/08/2007
     * @throws ECARException
     */
    public void efetuarCargaIntegralizacaoDeCapitalDefault() throws ECARException{
    	
    	final int COD_INT_NAO = 54;
    	
    	//Buscando a estrutura de nível de ação do PPA 2008-2011
    	EstruturaEtt estrutura = (EstruturaEtt) new EstruturaDao(request).buscar(EstruturaEtt.class, Long.valueOf(20));
    	SisAtributoSatb atbIntNao = (SisAtributoSatb) buscar(SisAtributoSatb.class, Long.valueOf(COD_INT_NAO));
    	UsuarioUsu usuarioLogado = ((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
    	
        Transaction tx = null;

        try{
        	msg("+++++++++++++++++ [ INICIO TRANSACAO ] ++++++++++++++++++++");
            tx = session.beginTransaction();
            
            if(estrutura.getItemEstruturaIetts() != null && !estrutura.getItemEstruturaIetts().isEmpty()){

            	for(Iterator it = estrutura.getItemEstruturaIetts().iterator(); it.hasNext();){
            		ItemEstruturaIett item = (ItemEstruturaIett) it.next();
            		
            		if(Dominios.NAO.equals(item.getEstruturaEtt().getIndEtapaNivelSuperiorEtt())){
            			ItemEstruturaSisAtributoIettSatb atbLivre = new ItemEstruturaSisAtributoIettSatb();
            			atbLivre.setDataUltManutencao(Data.getDataAtual());
            			atbLivre.setItemEstruturaIett(item);
            			atbLivre.setSisAtributoSatb(atbIntNao);
            			atbLivre.setUsuarioUsu(usuarioLogado);
            			atbLivre.atribuirPKPai();
            			
            			
        	    		Set atbsLivres = item.getItemEstruturaSisAtributoIettSatbs();

        	    		boolean podeInserir = true;
        	    		if(atbsLivres != null && !atbsLivres.isEmpty()){
        	    			for(Iterator it2 = atbsLivres.iterator(); it2.hasNext();){
        	    				ItemEstruturaSisAtributoIettSatb aux = (ItemEstruturaSisAtributoIettSatb) it2.next();
        	    				
        	    				if(aux.getItemEstruturaIett().equals(item) && aux.getSisAtributoSatb().equals(atbIntNao)){
        	    					podeInserir = false;
        	    					break;
        	    				}
        	    			}
        	    		}
        	    		
        	    		if(podeInserir){
        	    			session.save(atbLivre);
        	    			msg("--> Gravando \"Não\" para item: " + item.getSiglaIett() + " - " + item.getNomeIett());
        	    		}
            		}
            	}
            }
            
        	msg("+++++++++++++++++ [ COMMIT TRANSACAO ] ++++++++++++++++++++");
            tx.commit();
        	msg("+++++++++++++++++ [ FIM TRANSACAO ] ++++++++++++++++++++");
            
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null)
				try {
		        	msg("+++++++++++++++++ [ ROLLBACK TRANSACAO ] ++++++++++++++++++++");
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}	    			
    }
}