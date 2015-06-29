package ecar.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;

import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.permissao.ControlePermissao;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstrutLocalIettl;
import ecar.pojo.ItemEstrutUsuarioIettus;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.LocalItemLit;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.PaiFilho;
import ecar.pojo.PeriodicidadePrdc;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SituacaoSit;
import ecar.pojo.SubAreaSare;
import ecar.pojo.UsuarioUsu;

/**
 * DAO temporária para carga/atualizacao em ItemEstruturaIetts - E-CAR DF
 * 
 * @author cristiano
 */
public class TempCargaEcarDFIettsBDDao extends Dao {
    /**
     *
     * @param request
     */
    public TempCargaEcarDFIettsBDDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
    
    /**
     *
     * @throws ECARException
     */
    public void efetuarCargaItens() throws ECARException{
        Transaction tx = null;

        try{
		    super.inicializarLogBean();

            tx = session.beginTransaction();

            //Buscando a estrutura Empreendimentos
//        	EstruturaEtt estrutura = (EstruturaEtt) new EstruturaDao(request).buscar(EstruturaEtt.class, Long.valueOf(3));
            //Buscando a estrutura SUB-Empreendimentos
        	EstruturaEtt estrutura = (EstruturaEtt) new EstruturaDao(request).buscar(EstruturaEtt.class, Long.valueOf(6));
            
	    	BufferedReader in = new BufferedReader (new FileReader("/workspace/importacao/impsubemp.txt"));
			String linha="";
			while ((linha=in.readLine()) != null) {
				
				String[] campos = linha.split(";");
				System.out.println(linha);
		    	 /*
		    	0	T2015_DSC_EMPREENDIMENTO
				1	T2015_COD_EMPREENDIMENTO_SPI
				2	T2015_DAT_DATA_CONCLUSAO_EMPREENDIMENTO
				3	T2016_IND_PRIORITARIA (ind Monitorada )
				4	T2016_VAL_EMPREENDIMENTO_TOTAL (valor total no item)
				5	COD_IMPORTACAO
				6	T2016_NUM_OCORRENCIA_UF
				7	T2016_IDN_EMPREENDIMENTO (Sigla) 
				8	T2016_TXT_EMPREENDIEMNTO
				9	cod_local1 (códigos para tabela Item Local com código do local)
				10	cod_local2
				11	cod_local3
				12	cod_local4
				13	cod_local5
				14	cod_orgao (código tabela Orgão)
				15	cod_situa (código tabela de situação)
				16	cod_bol_(Ind é critico )
				17	cod_sis_atr (Nível Planejamento)
				18	Cod_iett (Item Estrutura PAI)
				19	Código Sub_Tipo (Código tabela Sub_Area) (NOVO)

		    	 */
				
				String nomeItem = campos[0].trim();
				String descricaoR3 = campos[1].trim();
		    	String dataTermino = campos[2].trim();
		    	String indMonitoramento = campos[3].trim();
		    	String valorPrevisto = campos[4].trim();
		    	String descricaoR2 = campos[5].trim();
		    	String descricaoR1 = campos[6].trim();
		    	String siglaItem = campos[7].trim();
		    	String descricaoR4 = campos[8].trim();
		    	String local1 = campos[9].trim();
		    	String local2 = campos[10].trim();
		    	String local3 = campos[11].trim();
		    	String local4 = campos[12].trim();
		    	String local5 = campos[13].trim();
		    	String orgao = campos[14].trim();
		    	String situacao = campos[15].trim();
		    	String indCritica = campos[16].trim();
		    	String nivelPlanejamento = campos[17].trim();
		    	String codItemPai = campos[18].trim();
		    	String codSubArea = campos[19].trim();
//		    	String codSubArea = "";
		    	
		    	String sqlBuscaPai = "select pai from ItemEstruturaIett pai where pai.codIett = :codIettPai";

		    	Query q = this.session.createQuery(sqlBuscaPai);
	    		q.setString("codIettPai", codItemPai);

	    		q.setMaxResults(1);
    			ItemEstruturaIett iettPai = (ItemEstruturaIett) q.uniqueResult();
    			if(iettPai == null)
    				throw new ECARException("IettPai Nulo");
    			
    			ItemEstruturaIett iett = new ItemEstruturaIett();
    			
    			iett.setIndAtivoIett("S");
    			iett.setIndBloqPlanejamentoIett("N");
    			
    			if("1".equals(indCritica)){
    				indCritica = "S";
    			} else {
    				indCritica = "N";
    			}
    			iett.setIndCriticaIett(indCritica);
    			
    			if("1".equals(indMonitoramento)){
    				indMonitoramento = "S";
    			} else {
    				indMonitoramento = "N";
    			}
    			iett.setIndMonitoramentoIett(indMonitoramento);

    			iett.setDataInclusaoIett(Data.getDataAtual());
    			iett.setDataInicioIett(Data.parseDate("01/01/2007"));
    			if(!"".equals(dataTermino.trim())) {
    				dataTermino = dataTermino.substring(0, 10);
    				iett.setDataTerminoIett(Data.parseDate(dataTermino));
    			}

    			iett.setNivelIett(Integer.valueOf(3));

    			SubAreaSare subArea = null;
    			if (!codSubArea.equals("")) {

	    			String sqlBuscaSubArea = "select subarea from SubAreaSare subarea where subarea.codSare = :codSubArea";
			    	q = this.session.createQuery(sqlBuscaSubArea);
		    		q.setString("codSubArea", codSubArea);
	
		    		q.setMaxResults(1);
	    			subArea = (SubAreaSare) q.uniqueResult();
    			}
    			if (subArea != null) {
    				System.out.println("CodSubArea: " + subArea.getCodSare());
    			} else {
    				System.out.println("CodSubArea: NULO");
    			}
    			iett.setSubAreaSare(subArea);

    			iett.setItemEstruturaIett(iettPai);
    			iett.setSiglaIett(siglaItem);
    			iett.setNomeIett(nomeItem);
    			iett.setDescricaoR3(descricaoR3);
    			if(!"".equals(valorPrevisto)) {
    				valorPrevisto = valorPrevisto.replace(',', '.');
    				iett.setValPrevistoFuturoIett(new BigDecimal(valorPrevisto));
    			}
    			iett.setDescricaoR2(descricaoR2);
    			iett.setDescricaoR1(descricaoR1);
    			iett.setDescricaoR4(descricaoR4);
				OrgaoOrg orgaoOrg = null;
				String sql;
    			if (!orgao.equals("")) {
	    			sql = "select orgao from OrgaoOrg orgao where orgao.codOrg = :codOrg";
	    			q = this.session.createQuery(sql);
	    			System.out.println("orgao: " + orgao);
	    			q.setLong("codOrg", Long.valueOf(orgao).longValue());
	    			q.setMaxResults(1);
	    			
					orgaoOrg = (OrgaoOrg) q.uniqueResult();
	    			if(orgao == null)
	    				throw new ECARException("Orgao Nulo");
					iett.setOrgaoOrgByCodOrgaoResponsavel1Iett(orgaoOrg);
    			}

   				sql = "select sit from SituacaoSit sit where sit.codSit = :codSit";
    			q = this.session.createQuery(sql);
    			q.setLong("codSit", Long.valueOf(situacao).longValue());
    			q.setMaxResults(1);
    			
				SituacaoSit situacaoSit = (SituacaoSit) q.uniqueResult();
    			if(situacaoSit == null)
    				throw new ECARException("Situacao Nulo");
				iett.setSituacaoSit(situacaoSit);

				iett.setEstruturaEtt(estrutura);
                iett.setPeriodicidadePrdc((PeriodicidadePrdc)super.buscar(PeriodicidadePrdc.class, Long.valueOf("1")));

		        iett.setItemEstruturaNivelIettns(new HashSet());
                iett.getItemEstruturaNivelIettns().add(super.buscar(SisAtributoSatb.class, Long.valueOf(nivelPlanejamento)));

				iett.setUsuarioUsuByCodUsuIncIett(((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
    			
	            iett.setDataInclusaoIett(Data.getDataAtual());
		        List filhos = new ArrayList();
		        if (iett.getItemEstUsutpfuacIettutfas() != null)
		            filhos.addAll(iett.getItemEstUsutpfuacIettutfas());
		        
                // locais do item
    	        iett.setItemEstrutLocalIettls(new HashSet());
    			if(!"".equals(local1.trim())) {
    				LocalItemLit lit = (LocalItemLit)super.buscar(LocalItemLit.class, Long.valueOf(local1));
    				ItemEstrutLocalIettl iettl = new ItemEstrutLocalIettl();
    				iettl.setDataInclusaoIettl(Data.getDataAtual());
    				iettl.setIndExclusaoPosHistorico(Boolean.FALSE);
    				iettl.setItemEstruturaIett(iett);
    				iettl.setLocalItemLit(lit);
    				iettl.setUsuarioUsuManutencao(((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
	                iett.getItemEstrutLocalIettls().add(iettl);
    			}
    			if(!"".equals(local2.trim())) {
    				LocalItemLit lit = (LocalItemLit)super.buscar(LocalItemLit.class, Long.valueOf(local2));
    				ItemEstrutLocalIettl iettl = new ItemEstrutLocalIettl();
    				iettl.setDataInclusaoIettl(Data.getDataAtual());
    				iettl.setIndExclusaoPosHistorico(Boolean.FALSE);
    				iettl.setItemEstruturaIett(iett);
    				iettl.setLocalItemLit(lit);
    				iettl.setUsuarioUsuManutencao(((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
	                iett.getItemEstrutLocalIettls().add(iettl);
    			}
    			if(!"".equals(local3.trim())) {
    				LocalItemLit lit = (LocalItemLit)super.buscar(LocalItemLit.class, Long.valueOf(local3));
    				ItemEstrutLocalIettl iettl = new ItemEstrutLocalIettl();
    				iettl.setDataInclusaoIettl(Data.getDataAtual());
    				iettl.setIndExclusaoPosHistorico(Boolean.FALSE);
    				iettl.setItemEstruturaIett(iett);
    				iettl.setLocalItemLit(lit);
    				iettl.setUsuarioUsuManutencao(((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
	                iett.getItemEstrutLocalIettls().add(iettl);
    			}
    			if(!"".equals(local4.trim())) {
    				LocalItemLit lit = (LocalItemLit)super.buscar(LocalItemLit.class, Long.valueOf(local4));
    				ItemEstrutLocalIettl iettl = new ItemEstrutLocalIettl();
    				iettl.setDataInclusaoIettl(Data.getDataAtual());
    				iettl.setIndExclusaoPosHistorico(Boolean.FALSE);
    				iettl.setItemEstruturaIett(iett);
    				iettl.setLocalItemLit(lit);
    				iettl.setUsuarioUsuManutencao(((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
	                iett.getItemEstrutLocalIettls().add(iettl);
    			}
    			if(!"".equals(local5.trim())) {
    				LocalItemLit lit = (LocalItemLit)super.buscar(LocalItemLit.class, Long.valueOf(local5));
    				ItemEstrutLocalIettl iettl = new ItemEstrutLocalIettl();
    				iettl.setDataInclusaoIettl(Data.getDataAtual());
    				iettl.setIndExclusaoPosHistorico(Boolean.FALSE);
    				iettl.setItemEstruturaIett(iett);
    				iettl.setLocalItemLit(lit);
    				iettl.setUsuarioUsuManutencao(((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
	                iett.getItemEstrutLocalIettls().add(iettl);
    			}
    			
		        if (iett.getItemEstrutLocalIettls() != null)
		            filhos.addAll(iett.getItemEstrutLocalIettls());

		        session.save(iett);


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
				}

				session.save(itemEstrutUsuario);
			}
			//Integer i = Integer.parseInt("asdças-asd");
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
    
}