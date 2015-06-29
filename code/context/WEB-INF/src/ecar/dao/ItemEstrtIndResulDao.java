/*
 * Criado em 21/12/2004
 *
 */
package ecar.dao;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.historico.Historico;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.Cor;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.ExercicioExe;
import ecar.pojo.HistoricoIettfH;
import ecar.pojo.HistoricoIettrH;
import ecar.pojo.HistoricoMaster;
import ecar.pojo.HistoricoMotivo;
import ecar.pojo.ItemEstrtIndResulCorIettrcor;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrutFisicoIettf;
import ecar.pojo.ItemEstrutLocalIettl;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.LocalGrupoLgp;
import ecar.pojo.LocalItemLit;
import ecar.pojo.PaiFilho;
import ecar.pojo.PeriodicidadePrdc;
import ecar.pojo.ServicoSer;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.historico.HistoricoItemEstrtIndResulIettr;
import ecar.servlet.relatorio.dto.marcas.RedeCicloDTO;
import ecar.sinalizacao.Sinalizacao;
import ecar.taglib.util.Input;
import ecar.util.Dominios;

/**
 * @author evandro
 * 
 */
public class ItemEstrtIndResulDao extends Dao {
	/**
	 * Construtor. Chama o Session factory do Hibernate
	 */
	private Historico<HistoricoItemEstrtIndResulIettr, ItemEstrtIndResulIettr> historico = null;
	private HistoricoItemEstrtIndResulIettr pojoHistorico;

        /**
         *
         * @param request
         */
        public ItemEstrtIndResulDao(HttpServletRequest request) {
		super();
		this.request = request;
		// this.usuario = ((ecar.login.SegurancaECAR)
		// request.getSession().getAttribute("seguranca")).getUsuario();
	}
        
        public List<RedeCicloDTO> listaIdicadoresDTO() {
        	StringBuffer hql = new StringBuffer();
        	hql.append("SELECT new RedeCicloDTO() " +
        			"FROM ItemEstrtIndResulIettr");
        	
        	Query q = this.session.createQuery(hql.toString());
        	
        	return q.list();
        }

	/**
	 * Cria um objeto itemEstrtIndResul a partir de par�metros passados no
	 * objeto request
	 * 
	 * @param request
	 * @param itemEstrtIndResul
	 * @throws ECARException
	 */
	public void setItemEstrtIndResul(HttpServletRequest request, ItemEstrtIndResulIettr itemEstrtIndResul) throws ECARException {

		SisGrupoAtributoSga grupoMetas = new ConfiguracaoDao(request).getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas();

		itemEstrtIndResul.setItemEstruturaIett((ItemEstruturaIett) this.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett"))));
		itemEstrtIndResul.setNomeIettir(Pagina.getParamStr(request, "nomeIettir"));
		//INI -- Ministerio da Saude
		itemEstrtIndResul.setConceituacao(Pagina.getParamStr(request, "conceitIettir"));

		//SINALIZAÇÃO
		String strSinalizacao = Pagina.getParamStr(request, "idSinalizacao");
		
		if(strSinalizacao != null && !strSinalizacao.equals("")){
			Long idSinalizacao = Long.valueOf(strSinalizacao);
			Sinalizacao sinalizacao = (Sinalizacao) this.buscar(Sinalizacao.class, idSinalizacao);
			if(sinalizacao.getItemEstrtIndResulIettrList() == null){
				sinalizacao.setItemEstrtIndResulIettrList(new HashSet<ItemEstrtIndResulIettr>());
			}
			sinalizacao.getItemEstrtIndResulIettrList().add(itemEstrtIndResul);
			itemEstrtIndResul.setSinalizacao(sinalizacao);
		}
		
		itemEstrtIndResul.setInterpretacao(Pagina.getParamStr(request, "interpIettir"));
		itemEstrtIndResul.setFonteIettr(Pagina.getParamStr(request, "fonteIettr"));
		itemEstrtIndResul.setMetodoCalculo(Pagina.getParamStr(request, "mCalcIettir"));
		
		//FIM -- Ministerio da Saude
		itemEstrtIndResul.setDescricaoIettir(Pagina.getParamStr(request, "descricaoIettir"));
		itemEstrtIndResul.setIndProjecaoIettr(Pagina.getParamStr(request, "indProjecaoIettr"));
		itemEstrtIndResul.setIndAcumulavelIettr(Pagina.getParamStr(request, "indAcumulavelIettr"));
		itemEstrtIndResul.setIndPublicoIettr(Pagina.getParamStr(request, "indPublicoIettr"));
		try {
			itemEstrtIndResul.setOrdem(Integer.parseInt(Pagina.getParamStr(request, "ordem")));
		} catch (Exception e) {
			itemEstrtIndResul.setOrdem(-1);
		}
		itemEstrtIndResul.setFormulaIettr(Pagina.getParamStr(request, "formulaIettr"));

		// Mantis 12390
		itemEstrtIndResul.setIndAtivoIettr(Dominios.SIM);

		if (!"".equals(Pagina.getParamStr(request, "dataApuracaoIettr"))) {
			itemEstrtIndResul.setDataApuracaoIettr(Data.parseDate(Pagina.getParamStr(request, "dataApuracaoIettr")));
		} else {
			itemEstrtIndResul.setDataApuracaoIettr(null);
		}

		if (!"".equals(Pagina.getParamStr(request, "periodicidadePrdc"))) {
			itemEstrtIndResul.setPeriodicidadePrdc((PeriodicidadePrdc) this.buscar(PeriodicidadePrdc.class, Long.valueOf(Pagina.getParamStr(request,
					"periodicidadePrdc"))));
		} else {
			itemEstrtIndResul.setPeriodicidadePrdc(null);
		}

		if (!"".equals(Pagina.getParamStr(request, "indiceMaisRecenteIettr"))) {
			DecimalFormat dff = (DecimalFormat) DecimalFormat.getInstance();
			Double indiceMaisRecente = new Double("0");
			String indiceMaisRecenteString = Pagina.getParamStr(request, "indiceMaisRecenteIettr");

			try {
				indiceMaisRecente = dff.parse(indiceMaisRecenteString).doubleValue();
			} catch (Exception e) {
				// N�o precisa lan�ar exce��o aqui.
			}

			itemEstrtIndResul.setIndiceMaisRecenteIettr(indiceMaisRecente);
		} else {
			itemEstrtIndResul.setIndiceMaisRecenteIettr(null);
		}

		String indPrevPorLocal = Pagina.getParamStr(request, "indPrevPorLocal");
		String indRealPorLocal = Pagina.getParamStr(request, "indRealPorLocal");		
		
		if ((itemEstrtIndResul.getItemEstruturaIett().getItemEstrutLocalIettls() == null)||(itemEstrtIndResul.getItemEstruturaIett().getItemEstrutLocalIettls().size() == 0)){
			indPrevPorLocal = "N";
			indRealPorLocal = "N";
		}else{
		
		// alteracao nova tela MANTIS #0011576

		if ("".equals(indPrevPorLocal.trim())){
			if (!"".equals(Pagina.getParamStr(request, "previstoPorLocal"))){
				if ("S".equals(Pagina.getParamStr(request, "previstoPorLocal")))
				indPrevPorLocal = "S";				
			else
				indPrevPorLocal = "N";
		    } else
		    	indPrevPorLocal = "N";
		}

		// alteracao nova tela MANTIS #0011576
		if ("".equals(indRealPorLocal.trim()))
			indRealPorLocal = "N";
		}
		
		itemEstrtIndResul.setIndRealPorLocal(indRealPorLocal);		
		itemEstrtIndResul.setIndPrevPorLocal(indPrevPorLocal);

		itemEstrtIndResul.setIndTipoQtde(Pagina.getParamStr(request, "indTipoQtde"));

		if (!"".equals(Pagina.getParamStr(request, "indValorFinalIettr"))) {
			itemEstrtIndResul.setIndValorFinalIettr(Pagina.getParamStr(request, "indValorFinalIettr"));
		} else {
			itemEstrtIndResul.setIndValorFinalIettr(null);
		}

		if (grupoMetas != null) {
			String nomeCampo = "a" + grupoMetas.getCodSga().toString();
			if (!"".equals(Pagina.getParamStr(request, nomeCampo))) {
				itemEstrtIndResul.setSisAtributoSatb((SisAtributoSatb) new SisAtributoDao(null).buscar(SisAtributoSatb.class, Long.valueOf(Pagina.getParamStr(
						request, nomeCampo))));
			}
			else
			{
				itemEstrtIndResul.setSisAtributoSatb(null);
			}
		}
		/*
		 * Se a configura��o do sistema est� setada para trabalhar com um grupo
		 * de unidades, o valor selecionado na tela deve ser gravada na vari�vel
		 * CodUnidMedidaIettr. Sen�o, o valor digitado ser� guardado na variavel
		 * unidMedidaIettr.
		 */
		SisGrupoAtributoSga grupoUnidades = new ConfiguracaoDao(request).getConfiguracao().getSisGrupoAtributoSgaByUnidMedida();

		if (grupoUnidades != null) {
			String nomeCampo = "a" + grupoUnidades.getCodSga().toString();
			if (!"".equals(Pagina.getParamStr(request, nomeCampo))) {
				if (grupoUnidades.getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT))
						|| grupoUnidades.getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA))
						|| grupoUnidades.getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO))
						|| grupoUnidades.getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO))) {

					itemEstrtIndResul.setCodUnidMedidaIettr((SisAtributoSatb) new SisAtributoDao(null).buscar(SisAtributoSatb.class, Long.valueOf(Pagina
							.getParamStr(request, "codigoSisAtbUnidadeMedida"))));
				} else {
					// Caso de Combo, Check, Radio, etc..
					itemEstrtIndResul.setCodUnidMedidaIettr((SisAtributoSatb) new SisAtributoDao(null).buscar(SisAtributoSatb.class, Long.valueOf(Pagina
							.getParamStr(request, nomeCampo))));
				}
			}
			itemEstrtIndResul.setUnidMedidaIettr("");
		} else {
			itemEstrtIndResul.setUnidMedidaIettr(Pagina.getParamStr(request, "unidMedidaIettr"));
			itemEstrtIndResul.setCodUnidMedidaIettr(null);
		}

		// Gr�fico de Grupo
		if (!"".equals(Pagina.getParamStr(request, "labelGraficoGrupoIettir"))) {
			itemEstrtIndResul.setLabelGraficoGrupoIettir(Pagina.getParamStr(request, "labelGraficoGrupoIettir").toUpperCase());
		} else {
			itemEstrtIndResul.setLabelGraficoGrupoIettir(null);
		}

		// Utiliza Sinaliza��o
		if (!"".equals(Pagina.getParamStr(request, "ind_sinalizacao"))) {
			itemEstrtIndResul.setIndSinalizacaoIettr(Pagina.getParamStr(request, "ind_sinalizacao"));
		} else {
			itemEstrtIndResul.setIndSinalizacaoIettr(null);
		}

		// No caso de inser��o de uma nova meta/indicador
		// Recupera do request os ItemEstrtIndResulCorIettrcores e seta o
		// "itemEstrtIndResul"com esses valores.
		if (itemEstrtIndResul.getItemEstrtIndResulCorIettrcores() == null) {
			this.setItemEstrtIndResulCorIettrcor(request, itemEstrtIndResul);
		}

		// Tipo de atualiza��o autom�tica ou manual
		itemEstrtIndResul.setIndTipoAtualizacaoPrevisto(Pagina.getParamStr(request, "indTipoAtualizacaoPrevisto"));
		itemEstrtIndResul.setIndTipoAtualizacaoRealizado(Pagina.getParamStr(request, "indTipoAtualizacaoRealizado"));

		// Valor Previsto
		if (!"".equals(Pagina.getParamStr(request, "previstoServicoSer"))) {
			itemEstrtIndResul.setPrevistoServicoSer((ServicoSer) ((ServicoSer) this.buscar(ServicoSer.class, Long.valueOf(Pagina.getParamStr(request,
					"previstoServicoSer")))));
		} else {
			itemEstrtIndResul.setPrevistoServicoSer(null);
		}

		// Valor Realizado
		if (!"".equals(Pagina.getParamStr(request, "realizadoServicoSer"))) {
			itemEstrtIndResul.setRealizadoServicoSer((ServicoSer) ((ServicoSer) this.buscar(ServicoSer.class, Long.valueOf(Pagina.getParamStr(request,
					"realizadoServicoSer")))));
		} else {
			itemEstrtIndResul.setRealizadoServicoSer(null);
		}
		
		if (("S".equals(indPrevPorLocal))||("S".equals(indRealPorLocal))){
			//Se n�o tiver valor, significa que a combo est� desabilitada
			//E se um dos indices for S e a combo desabilitado, deve manter o valor
			if(!"".equals(Pagina.getParamStr(request, "abrangenciaLocal")))
			    itemEstrtIndResul.setNivelAbrangencia(Long.valueOf(Pagina.getParamStr(request, "abrangenciaLocal")));
			else{
				if(!"".equals(Pagina.getParamStr(request, "nivelAbrangencia")))
					itemEstrtIndResul.setNivelAbrangencia(Long.valueOf(Pagina.getParamStr(request, "nivelAbrangencia")));
			}
			    
		}
		else
		{
			itemEstrtIndResul.setNivelAbrangencia(null);
		}
				

	}

        /**
         *
         * @param request
         * @param itemEstrtIndResul
         * @throws ECARException
         */
        public void setItemEstrtIndResulCorIettrcor(HttpServletRequest request, ItemEstrtIndResulIettr itemEstrtIndResul) throws ECARException {

		ItemEstrtIndResulCorIettrcor iettrCor = null;
		int quantidadeCores = 0;
		if (Pagina.getParamStr(request, "quantidadeCores") != null && !Pagina.getParamStr(request, "quantidadeCores").equals("")) {
			quantidadeCores = Integer.valueOf(Pagina.getParamStr(request, "quantidadeCores"));
		}

		for (int count = 1; count <= quantidadeCores; count++) {

			if (Pagina.getParamStr(request, "cor_" + count) != null && !Pagina.getParamStr(request, "cor_" + count).equals("")) {

				iettrCor = new ItemEstrtIndResulCorIettrcor();

				iettrCor.setCor((Cor) new CorDao(request).buscar(Cor.class, Long.valueOf(Pagina.getParamStr(request, "cor_" + count))));

				iettrCor.setItemEstrtIndResulIettr(itemEstrtIndResul);

				if (!Pagina.getParamStr(request, "valor_" + count).equals("") && !Pagina.getParamStr(request, "valor_" + count).equals("Maior Valor")) {
					iettrCor.setValorPrimEmailIettrcor(new BigDecimal(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, "valor_" + count)))
							.doubleValue()));
				} else if (Pagina.getParamStr(request, "valor_" + count).equals("Maior Valor")) {
					iettrCor.setValorPrimEmailIettrcor(null);
					iettrCor.setIndMaiorValorIettrcor("S");
				} else {
					iettrCor.setValorPrimEmailIettrcor(null);
					iettrCor.setIndMaiorValorIettrcor("N");
				}

				if (count == 1) {
					iettrCor.setIndMenorValorIettrcor("S");
				} else {
					iettrCor.setIndMenorValorIettrcor("N");
				}

				iettrCor.setFrequenciaEnvioEmailIettrcor(Integer.valueOf(Pagina.getParamInt(request, "freq_" + count)));
				iettrCor.setIndAtivoEnvioEmailIettrcor("S".equals(Pagina.getParamStr(request, "ativo[" + count + "]").trim()) ? "S" : "N");

				iettrCor.setSequenciaIettrcor(Integer.valueOf(count));

				if (itemEstrtIndResul.getItemEstrtIndResulCorIettrcores() == null)
					itemEstrtIndResul.setItemEstrtIndResulCorIettrcores(new HashSet());
				itemEstrtIndResul.getItemEstrtIndResulCorIettrcores().add(iettrCor);

			}
		}

		// System.out.println();

	}

	/**
	 * Recebe um array contendo c�digos de IndResultado e exclui todos os
	 * registros
	 * 
	 * @param codigosParaExcluir
         * @param usuario
         * @throws ECARException
	 */
	public void excluir(String[] codigosParaExcluir, UsuarioUsu usuario) throws ECARException {
		pojoHistorico = new HistoricoItemEstrtIndResulIettr();
		Transaction tx = null;
		List<ItemEstrtIndResulIettr> indicadoresParaExcluir = new ArrayList<ItemEstrtIndResulIettr>();

		try {

			tx = session.beginTransaction();

			for (int i = 0; i < codigosParaExcluir.length; i++) {

				ItemEstrtIndResulIettr itemEstrtIndResul = (ItemEstrtIndResulIettr) buscar(ItemEstrtIndResulIettr.class, Long.valueOf(codigosParaExcluir[i]));

				if (verificarExistenciaEmAcompRealFisicoArf(itemEstrtIndResul))
					throw new ECARException("itemEstrutura.indResultado.exclusao.exiteEmArf", null, new String[] { itemEstrtIndResul.getNomeIettir() });

				ConfiguracaoDao dao = new ConfiguracaoDao(request);
				ConfiguracaoCfg config = dao.getConfiguracao();

				if (itemEstrtIndResul.getItemEstrtIndResulCorIettrcores() == null)
					itemEstrtIndResul.setItemEstrtIndResulCorIettrcores(new HashSet<ItemEstrtIndResulCorIettrcor>());

				pojoHistorico.carregar(itemEstrtIndResul);

				HistoricoMaster historicoMaster = new HistoricoMaster();

				if ("S".equals(config.getIndGerarHistoricoCfg())) {

					historicoMaster.setDataHoraHistorico(new Date());
					historicoMaster.setUsuManutencao(usuario);
					historicoMaster.setCodReferenciaGeral(itemEstrtIndResul.getItemEstruturaIett().getCodIett());
					historicoMaster.setHistoricoMotivo((HistoricoMotivo) super.buscar(HistoricoMotivo.class, Long.valueOf(24)));
					session.save(historicoMaster);

					HistoricoIettrH iettrh = new HistoricoIettrH();
					ItemEstrtIndResulIettr iettrhAntesAlteracao = (ItemEstrtIndResulIettr) super.buscar(ItemEstrtIndResulIettr.class, itemEstrtIndResul
							.getCodIettir());
					iettrh.setItemEstruturaIett(iettrhAntesAlteracao.getItemEstruturaIett());
					iettrh.setUnidMedidaIettr(iettrhAntesAlteracao.getUnidMedidaIettr());
					iettrh.setDescricaoIettir(iettrhAntesAlteracao.getDescricaoIettir());
					iettrh.setNomeIettir(iettrhAntesAlteracao.getNomeIettir());
					iettrh.setIndProjecaoIettr(iettrhAntesAlteracao.getIndProjecaoIettr());
					iettrh.setIndAcumulavelIettr(iettrhAntesAlteracao.getIndAcumulavelIettr());
					iettrh.setIndTipoQtde(iettrhAntesAlteracao.getIndTipoQtde());
					iettrh.setIndValorFinalIettr(iettrhAntesAlteracao.getIndValorFinalIettr());
					iettrh.setIndRealPorLocal(iettrhAntesAlteracao.getIndRealPorLocal());
					iettrh.setIndPublicoIettr(iettrhAntesAlteracao.getIndPublicoIettr());

					// alteracao nova tela
					iettrh.setIndPrevPorLocal(iettrhAntesAlteracao.getIndPrevPorLocal());
					// alteracao nova tela

					iettrh.setSisAtributoSatb(iettrhAntesAlteracao.getSisAtributoSatb());
					iettrh.setFonteIettr(iettrhAntesAlteracao.getFonteIettr());
					iettrh.setIndiceMaisRecenteIettr(iettrhAntesAlteracao.getIndiceMaisRecenteIettr());
					iettrh.setDataApuracaoIettr(iettrhAntesAlteracao.getDataApuracaoIettr());
					iettrh.setFormulaIettr(iettrhAntesAlteracao.getFormulaIettr());
					iettrh.setPeriodicidadePrdc(iettrhAntesAlteracao.getPeriodicidadePrdc());
					iettrh.setUsuarioUsuManutencao(usuario);
					iettrh.setDataUltManutencao(itemEstrtIndResul.getDataUltManutencao());
					iettrh.setHistoricoMaster(historicoMaster);

					session.save(iettrh);
				}

				indicadoresParaExcluir.add(itemEstrtIndResul);

				// excluir todas as quantidades previstas
				List listaAux = new ArrayList();
				listaAux.addAll(itemEstrtIndResul.getItemEstrutFisicoIettfs());

				Iterator itAux = listaAux.iterator();
				while (itAux.hasNext()) {
					ItemEstrutFisicoIettf itemEstrutFisico = (ItemEstrutFisicoIettf) itAux.next();
					itemEstrutFisico.setDataUltManutencao(Data.getDataAtual());
					itemEstrutFisico.setUsuarioUsuManutencao(usuario);

					if ("S".equals(config.getIndGerarHistoricoCfg())) {

						HistoricoIettfH iettfh = new HistoricoIettfH();
						iettfh.setDataInclusaoIettf(itemEstrutFisico.getDataInclusaoIettf());
						iettfh.setDataUltManutencao(itemEstrutFisico.getDataUltManutencao());
						iettfh.setItemEstrtIndResulIettr(itemEstrutFisico.getItemEstrtIndResulIettr());
						
						//FIXME
						/* Mantis 0010128 - Qtd prevista n�o � mais informado por exerc�cio*/
						//iettfh.setExercicioExe(itemEstrutFisico.getExercicioExe());
						iettfh.setQtdPrevistaIettf(itemEstrutFisico.getQtdPrevistaIettf());
						iettfh.setUsuManutencao(usuario);
						iettfh.setIndAtivoIettf(Dominios.NAO);
						iettfh.setHistoricoMaster(historicoMaster);
						session.save(iettfh);
					}

					session.delete(itemEstrutFisico);
				}

			}

			for (ItemEstrtIndResulIettr obj : indicadoresParaExcluir) {
				obj.setUsuarioUsuManutencao(usuario);
				obj.setDataUltManutencao(new Date());
				obj.setIndAtivoIettr(Dominios.NAO);
				session.update(obj);
			}
			gerarHistorico(Historico.EXCLUIR);
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			this.logger.error(e);
			throw new ECARException(e.getMessage());
		}

	}

	/**
	 * Soma o valor de todos as quantidades de um Indicador de Resultado onde
	 * IndAtivo = 'S'
	 * 
	 * @param itemEstrtIndResul
	 * @return
	 * @throws ECARException
	 */
	/*
	 * Comentado: Utilizar m�todo abaixo: getSomaQuantidadePrevista public
	 * double getSomaQuantidades(ItemEstrtIndResulIettr itemEstrtIndResul)
	 * throws ECARException { double total = 0;
	 * 
	 * if (itemEstrtIndResul.getItemEstrutFisicoIettfs() != null) { Iterator it
	 * = itemEstrtIndResul.getItemEstrutFisicoIettfs().iterator(); while
	 * (it.hasNext()) { ItemEstrutFisicoIettf itemEstrutFisico =
	 * (ItemEstrutFisicoIettf) it.next();
	 * if("S".equalsIgnoreCase(itemEstrutFisico.getIndAtivoIettf())) total +=
	 * itemEstrutFisico.getQtdPrevistaIettf().doubleValue(); } } return total; }
	 */
	/**
	 * Se Indicador de Resultado � Acumul�vel soma o valor de todos as
	 * quantidades onde IndAtivo = 'S', o retorno � em string; Sen�o retorna
	 * "N�o se aplica"
	 * 
	 * @param itemEstrtIndResul
	 * @return
	 * @throws ECARException
	 */
	public String getSomaQuantidadePrevista(ItemEstrtIndResulIettr itemEstrtIndResul) throws ECARException {
		try {
			String retorno = "0";
			double total = 0;

			String tipoQtde = "V";
			if("Q".equals(itemEstrtIndResul.getIndTipoQtde())){
				tipoQtde = "Q";
			}
			
			if ("S".equals(itemEstrtIndResul.getIndAcumulavelIettr())) {
				if (itemEstrtIndResul.getItemEstrutFisicoIettfs() != null) {
					Iterator it = itemEstrtIndResul.getItemEstrutFisicoIettfs().iterator();
					while (it.hasNext()) {
						ItemEstrutFisicoIettf itemEstrutFisico = (ItemEstrutFisicoIettf) it.next();
						if ("S".equalsIgnoreCase(itemEstrutFisico.getIndAtivoIettf()))
							total += itemEstrutFisico.getQtdPrevistaIettf().doubleValue();
					}
					if(tipoQtde=="V")
						retorno = Util.formataMoeda(total);
					else
						retorno = Util.formataNumeroDecimal(total);
				}
			} else {

				/*
				 * Anota��o ref. Mantis 5016: - Maior: obter o maior valor de
				 * ItemEstrutFisicoIettf - �ltimo: obter valor do �ltimo
				 * exerc�cio informado de ItemEstrutFisicoIettf - N�o se aplica:
				 * soma total ItemEstrutFisicoIettf
				 */

				if ("M".equals(itemEstrtIndResul.getIndValorFinalIettr())) { // Maior
					if (itemEstrtIndResul.getItemEstrutFisicoIettfs() != null) {
						Iterator it = itemEstrtIndResul.getItemEstrutFisicoIettfs().iterator();
						double maior = 0;
						while (it.hasNext()) {
							ItemEstrutFisicoIettf itemEstrutFisico = (ItemEstrutFisicoIettf) it.next();
							if ("S".equalsIgnoreCase(itemEstrutFisico.getIndAtivoIettf())) {
								if (itemEstrutFisico.getQtdPrevistaIettf().doubleValue() > maior) {
									maior = itemEstrutFisico.getQtdPrevistaIettf().doubleValue();
								}
								total = maior;
							}
						}
						retorno = Util.formataMoeda(total);
					}
				} else if ("U".equals(itemEstrtIndResul.getIndValorFinalIettr())) { // Ultimo
					double ultimo = 0;
					List<ItemEstrutFisicoIettf> previstos = null;
					
					previstos = new ArrayList<ItemEstrutFisicoIettf>(itemEstrtIndResul.getItemEstrutFisicoIettfs());
					
					if (previstos.size() > 0) {
						Collections.reverse(previstos);//percorre na ordem inversa at� encontrar o primeiro ativo.
						Iterator<ItemEstrutFisicoIettf> it = previstos.iterator();
												
						while (it.hasNext()) {
							ItemEstrutFisicoIettf itemEstrutFisico = (ItemEstrutFisicoIettf) it.next();
							if ("S".equalsIgnoreCase(itemEstrutFisico.getIndAtivoIettf())) {
								ultimo = itemEstrutFisico.getQtdPrevistaIettf().doubleValue();
								break; //encerra o la�o ap�s encontar o primeiro(ultimo) registro de previsto ativo 
							}
						}	
					}
					
					retorno = Util.formataMoeda(ultimo);
					
					// Mantis 0010128 - Qtd prevista n�o � mais informado por exerc�cio
					/*
					ExercicioExe ultimoExe = getMaiorExercicioIndicador(itemEstrtIndResul);
					ExercicioDao exercicioDAO = new ExercicioDao(request);

					if (itemEstrtIndResul.getItemEstrutFisicoIettfs() != null) {
						Iterator it = itemEstrtIndResul.getItemEstrutFisicoIettfs().iterator();
						while (it.hasNext()) {
							ItemEstrutFisicoIettf itemEstrutFisico = (ItemEstrutFisicoIettf) it.next();
							if ("S".equalsIgnoreCase(itemEstrutFisico.getIndAtivoIettf())) {
								
								List meses = exercicioDAO.getMesesDentroDoExercicio(ultimoExe);
								Iterator itMeses = meses.iterator();
								while(itMeses.hasNext()){
									String chave = itMeses.next().toString();
									String mes = chave.split("-")[0];
									if(mes.length() == 1)
										mes = "0" + mes;									
									
									String ano = chave.split("-")[1];
									if (Integer.valueOf(mes) == itemEstrutFisico.getMesIettf() && (Integer.valueOf(ano) == itemEstrutFisico.getAnoIettf() )){
										ultimo = itemEstrutFisico.getQtdPrevistaIettf().doubleValue();	
									}
								}
								
								
								if (itemEstrutFisico.getExercicioExe().getCodExe().equals(ultimoExe.getCodExe())) {
									ultimo = itemEstrutFisico.getQtdPrevistaIettf().doubleValue();
									break;
								}								
							}
						}
					}
					*/					
				} else if ("N".equals(itemEstrtIndResul.getIndValorFinalIettr())) { // N�o
					// se
					// aplica
					/*
					 * if (itemEstrtIndResul.getItemEstrutFisicoIettfs() !=
					 * null) { Iterator it =
					 * itemEstrtIndResul.getItemEstrutFisicoIettfs().iterator();
					 * while (it.hasNext()) { ItemEstrutFisicoIettf
					 * itemEstrutFisico = (ItemEstrutFisicoIettf) it.next();
					 * if("S"
					 * .equalsIgnoreCase(itemEstrutFisico.getIndAtivoIettf()))
					 * total +=
					 * itemEstrutFisico.getQtdPrevistaIettf().doubleValue(); }
					 * retorno = Util.formataNumeroSemDecimal(total); }
					 */
					retorno = "";
				}
			}

			return retorno;
		} catch (Exception e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}

	/**
	 * M�todo que retorna a Quantidade Prevista de um Indicador de Resultado em
	 * um Exerc�cio.
	 * 
	 * @param indResul
	 * @param exercicio
	 * @return
	 * @throws NumberFormatException
	 */
	public double getQtdPrevistoExercicio(ItemEstrtIndResulIettr indResul, ExercicioExe exercicio) {
		double quant = 0;

		try {
			// Mantis 0010128 - Qtd prevista n�o � mais informado por exerc�cio
			ItemEstrutFisicoDao itemEstrutFisicoDao = new ItemEstrutFisicoDao(request);
			quant = itemEstrutFisicoDao.getQtdPrevistaExercicio(exercicio, indResul, null);
			
			//ItemEstrutFisicoIettf qtdPrevista = new ItemEstrutFisicoDao(request).buscar(indResul.getCodIettir(), exercicio.getCodExe());
			/*
			if (qtdPrevista != null) {
				if ("S".equalsIgnoreCase(qtdPrevista.getIndAtivoIettf())) {
					quant = qtdPrevista.getQtdPrevistaIettf().doubleValue();
				}
			}
			*/
		} catch (Exception e) {
			/* n�o realiza nada e devolve quant = 0 */
		}

		return quant;
	}
		
	/*
	 * M�todo que retorna a Quantidade Prevista de um Exerc�cio.
	 */
	
	public double getQtdPrevistoExercicio(AcompReferenciaItemAri ari, String tipoSelecao, ItemEstruturaIett item, ExercicioExe exercicio, String grupoIndicador) {
		double quant = 0;

		try {
			List indResultados = new AcompRealFisicoDao(request).getIndResulByAcompRefItemBySituacao(ari, tipoSelecao, false);
			if (indResultados != null && indResultados.size() > 0) {
				Iterator itIndResult = indResultados.iterator();
				while(itIndResult.hasNext()) {
					AcompRealFisicoArf arf = (AcompRealFisicoArf) itIndResult.next();
					if(grupoIndicador.equals("") || arf.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb().equals(grupoIndicador)) {
						ItemEstrutFisicoIettf qtdPrevista = null;
						try {
							// Mantis 0010128 - Qtd prevista n�o � mais informado por exerc�cio
							//qtdPrevista = new ItemEstrutFisicoDao(request).buscar(arf.getItemEstrtIndResulIettr().getCodIettir(), exercicio.getCodExe());
							quant = new ItemEstrutFisicoDao(request).getQtdPrevistaExercicio(exercicio, arf.getItemEstrtIndResulIettr(), null);
						} catch(Exception e) {
							// n�o realiza nada e procura proxima qtde prevista 
						}
				
						if (qtdPrevista != null) {
							if ("S".equalsIgnoreCase(qtdPrevista.getIndAtivoIettf())) {
								quant += qtdPrevista.getQtdPrevistaIettf().doubleValue();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// n�o realiza nada e devolve quant = 0 
		}

		return quant;
	}
	
	
	
	/**
	 * Devolve o maior exerc�cio em que foi cadastrada uma quantidade prevista
	 * para um indicador de resultado
	 * 
	 * @param indResul
	 * @return
	 * @throws ECARException
	 */
	public ExercicioExe getMaiorExercicioIndicador(ItemEstrtIndResulIettr indResul) throws ECARException {
		try {
			Query q = this.getSession().createQuery(
					"select ieFisico from ItemEstrutFisicoIettf ieFisico" + " where ieFisico.itemEstrtIndResulIettr.codIettir = ? "
							+ " order by ieFisico.anoIettf desc,ieFisico.mesIettf desc");
			q.setLong(0, indResul.getCodIettir().longValue());
			q.setMaxResults(1);
			
			ItemEstrutFisicoIettf mesAno = (ItemEstrutFisicoIettf) q.uniqueResult();
			
			ExercicioDao exeDao = new ExercicioDao(null);
			ExercicioExe exe = exeDao.getExercicio(mesAno.getMesIettf().toString(), mesAno.getAnoIettf().toString());
			return exe;
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
		
	}

	//Mantis 0010128 - Qtd prevista n�o � mais informado por exerc�cio
	/* M�todo n�o est� sendo usado em nenhum lugar
	 * 
	 * @author n/c
	 * @param indicativoResultado
	 * @return List
	 * @throws ECARException
	 */
/*	
	public List getExerciosCadastroPermitidos(ItemEstrtIndResulIettr indicativoResultado) throws ECARException {
		List exercicios = new ExercicioDao(request).listar(ExercicioExe.class, new String[] { "descricaoExe", Dao.ORDEM_ASC });
		Set quantidades = indicativoResultado.getItemEstrutFisicoIettfs();
		Iterator it = quantidades.iterator();
		while (it.hasNext()) {
			ItemEstrutFisicoIettf ieFisico = (ItemEstrutFisicoIettf) it.next();
			ExercicioExe exercicioCadastrado = ieFisico.getExercicioExe();
			exercicios.remove(exercicioCadastrado);
		}
		return exercicios;
	}
*/
	/**
	 * retorna lista vari�vel de quantidade prevista preenchidas de acordo com
	 * o ano e mes.
	 * 
	 * @param request
	 * @return List
	 * @throws ECARException
	 */
	public List<ItemEstrutFisicoIettf> getListaQuantidadePrevista(HttpServletRequest request, ItemEstrtIndResulIettr indicador) throws ECARException {
		//Mantis 0010128 - Qtd prevista n�o � mais informado por exerc�cio
				
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);		
		
		List<GregorianCalendar> listaMesesReferencia = itemEstruturaDao.GetMesesReferencia(indicador.getItemEstruturaIett());		

		Iterator<GregorianCalendar> itMesesReferencia = listaMesesReferencia.iterator();		
		
		List<ItemEstrutFisicoIettf> listaQtd = new ArrayList<ItemEstrutFisicoIettf>();
		int ano = 0;
		int mes = 0;
		String anoMes = null;
		while (itMesesReferencia.hasNext()) {
			GregorianCalendar mesReferencia = (GregorianCalendar) itMesesReferencia.next();			
			mes = mesReferencia.get(Calendar.MONTH) + 1;						
			ano = mesReferencia.get(Calendar.YEAR);
			anoMes = String.format("%04d", ano) + String.format("%02d", mes);

			if (!"".equals(Pagina.getParamStr(request, "qtdPrevistaIettf" + anoMes))) {
				ItemEstrutFisicoIettf itemEstrutFisico = new ItemEstrutFisicoIettf();
				//itemEstrutFisicoDao.setItemEstrutFisico(request, itemEstrutFisico);
				//itemEstrutFisico.setItemEstrtIndResulIettr(indicador);
				itemEstrutFisico.setAnoIettf(ano);
				itemEstrutFisico.setMesIettf(mes);
				String qtdePrevista = Pagina.getParamStr(request, "qtdPrevistaIettf" + anoMes);
				if (qtdePrevista != null) {
					try{
						itemEstrutFisico.setQtdPrevistaIettf(Util.parseDecimalPT_BR(qtdePrevista));
					}catch(NumberFormatException e){
						String hideqtde = Pagina.getParamStr(request, "hideqtdPrevistaIettf" + anoMes);
						try{
						itemEstrutFisico.setQtdPrevistaIettf(Util.parseDecimalPT_BR(hideqtde));
						}catch(NumberFormatException excep){
							//Se o valor � inv�lido remove da lista
							continue;
						}
					}

				} else {
					String hideqtde = Pagina.getParamStr(request, "hideqtdPrevistaIettf" + anoMes);
					itemEstrutFisico.setQtdPrevistaIettf(Util.parseDecimalPT_BR(hideqtde));
				}

				//itemEstrutFisico.setExercicioExe(exercicio);
				itemEstrutFisico.setIndAtivoIettf("S");
				itemEstrutFisico.setDataInclusaoIettf(Data.getDataAtual());

				listaQtd.add(itemEstrutFisico);
			}
		}

		return listaQtd;
	}

	/**
	 * 
	 * @author n/c
	 * @param itemEstrtIndResul
	 * @param listaQtd
         * @param usuario
         * @throws ECARException
	 */
    @SuppressWarnings("static-access")
	public void salvar(ItemEstrtIndResulIettr itemEstrtIndResul, List listaQtd, UsuarioUsu usuario) throws ECARException {
		pojoHistorico = new HistoricoItemEstrtIndResulIettr();
		inicializarLogBean();
		Transaction tx = null;
		try {
			ArrayList objetosInseridos = new ArrayList();
			tx = session.beginTransaction();

			// salva o pai

			session.save(itemEstrtIndResul);
			pojoHistorico.carregar(itemEstrtIndResul);

			pojoHistorico.setItemEstrutFisicoIettfs(new HashSet<ItemEstrutFisicoIettf>());
			objetosInseridos.add(itemEstrtIndResul);
			Iterator itQtd = listaQtd.iterator();
			while (itQtd.hasNext()) {
				ItemEstrutFisicoIettf itemEstrutFisico = (ItemEstrutFisicoIettf) itQtd.next();				
				/* Mantis 0010128 - Qtd prevista n�o � mais informado por exerc�cio
				 * Mudou a pk. n�o usa mais chave composta
				 * */
				//itemEstrutFisico.getComp_id().setCodIettir(itemEstrtIndResul.getCodIettir());		
				
				itemEstrutFisico.setItemEstrtIndResulIettr(itemEstrtIndResul);
				itemEstrutFisico.setDataUltManutencao(new Date());
				itemEstrutFisico.setUsuarioUsuManutencao(usuario);
				pojoHistorico.getItemEstrutFisicoIettfs().add(itemEstrutFisico);
				session.save(itemEstrutFisico);

				objetosInseridos.add(itemEstrutFisico);
			}

			List listaIettrCor = new ArrayList();
			if (itemEstrtIndResul.getItemEstrtIndResulCorIettrcores() != null)
				listaIettrCor.addAll(itemEstrtIndResul.getItemEstrtIndResulCorIettrcores());

			pojoHistorico.setItemEstrtIndResulCorIettrcores(new HashSet<ItemEstrtIndResulCorIettrcor>());
			Iterator itListaIettrCor = listaIettrCor.iterator();
			while (itListaIettrCor.hasNext()) {
				PaiFilho object = (PaiFilho) itListaIettrCor.next();
				object.atribuirPKPai();
				pojoHistorico.getItemEstrtIndResulCorIettrcores().add(object);
				// salva os filhos
				session.save(object);
				objetosInseridos.add(object);
			}

			try {
				gerarHistorico(Historico.INCLUIR);
			} catch (Exception e) {
				
			}

			tx.commit();

			if (logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("INC");
				Iterator it2 = objetosInseridos.iterator();

				while (it2.hasNext()) {
					logBean.setObj(it2.next());
					loggerAuditoria.info(logBean.toString());
				}
			}
		} catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param itemEstrtIndResul
	 * @param listaQtd
	 * @throws ECARException
	 */
    @SuppressWarnings("static-access")
	public void alterar(ItemEstrtIndResulIettr itemEstrtIndResul, List<ItemEstrutFisicoIettf> listaQtd) throws ECARException {
		pojoHistorico = new HistoricoItemEstrtIndResulIettr();
		Boolean qtdPrevistaBloqueado = false;
		ItemEstruturaIett itemEstrutura = itemEstrtIndResul.getItemEstruturaIett();
		if (itemEstrutura.getIndBloqPlanejamentoIett() != null && itemEstrutura.getIndBloqPlanejamentoIett().equals("S")) {
			EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
			EstruturaFuncaoEttf estruturaFuncaoQtdePrevista = new EstruturaFuncaoEttf();
			estruturaFuncaoQtdePrevista = (EstruturaFuncaoEttf) estruturaFuncaoDao.getQuantidadesPrevistas(itemEstrutura.getEstruturaEtt());
			if (estruturaFuncaoQtdePrevista.getIndPodeBloquearEttf() != null && estruturaFuncaoQtdePrevista.getIndPodeBloquearEttf().equals("S")) {
				qtdPrevistaBloqueado = true;
			}
		}

		inicializarLogBean();
		Transaction tx = null;

		try {

			ArrayList objetosInseridos = new ArrayList();
			ArrayList objetosExcluidos = new ArrayList();
			tx = session.beginTransaction();

			// Atualizando a lista de ItemEstrtIndResulCorIettrcor

			/* apaga os filhos para serem gravados novamente */
			if (itemEstrtIndResul.getItemEstrtIndResulCorIettrcores() != null) {
				Iterator itAtb = itemEstrtIndResul.getItemEstrtIndResulCorIettrcores().iterator();
				while (itAtb.hasNext()) {
					ItemEstrtIndResulCorIettrcor iettrCor = (ItemEstrtIndResulCorIettrcor) itAtb.next();
					session.delete(iettrCor);
					// objetos.add(iettrCor);
				}
			} else
				itemEstrtIndResul.setItemEstrtIndResulCorIettrcores(new HashSet<ItemEstrtIndResulCorIettrcor>());
			itemEstrtIndResul.setItemEstrtIndResulCorIettrcores(null);
			this.setItemEstrtIndResulCorIettrcor(request, itemEstrtIndResul);

			List filhos = new ArrayList();
			if (itemEstrtIndResul.getItemEstrtIndResulCorIettrcores() != null)
				filhos.addAll(itemEstrtIndResul.getItemEstrtIndResulCorIettrcores());

			Iterator iterator = filhos.iterator();

			// salva os filhos
			while (iterator.hasNext()) {
				PaiFilho object = (PaiFilho) iterator.next();
				object.atribuirPKPai();
				session.save(object);
			}

			// *************

			// ConfiguracaoDao dao = new ConfiguracaoDao(request);
			// ConfiguracaoCfg config = dao.getConfiguracao();
			//
			// HistoricoMaster historicoMaster = new HistoricoMaster();
			//
			// if ("S".equals(config.getIndGerarHistoricoCfg())) {
			//
			// historicoMaster.setDataHoraHistorico(new Date());
			// historicoMaster.setUsuManutencao(itemEstrtIndResul.getUsuarioUsuManutencao());
			// historicoMaster.setCodReferenciaGeral(itemEstrtIndResul.getItemEstruturaIett().getCodIett());
			// historicoMaster.setHistoricoMotivo((HistoricoMotivo)
			// super.buscar(HistoricoMotivo.class, Long.valueOf(23)));
			// session.save(historicoMaster);
			//
			// HistoricoIettrH iettrh = new HistoricoIettrH();
			// ItemEstrtIndResulIettr iettrhAntesAlteracao =
			// (ItemEstrtIndResulIettr)
			// super.buscar(ItemEstrtIndResulIettr.class, itemEstrtIndResul
			// .getCodIettir());
			// iettrh.setItemEstruturaIett(iettrhAntesAlteracao.getItemEstruturaIett());
			// iettrh.setUnidMedidaIettr(iettrhAntesAlteracao.getUnidMedidaIettr());
			// iettrh.setCodUnidMedidaIettr(iettrhAntesAlteracao.getCodUnidMedidaIettr());
			// iettrh.setDescricaoIettir(iettrhAntesAlteracao.getDescricaoIettir());
			// iettrh.setNomeIettir(iettrhAntesAlteracao.getNomeIettir());
			// iettrh.setIndProjecaoIettr(iettrhAntesAlteracao.getIndProjecaoIettr());
			// iettrh.setIndAcumulavelIettr(iettrhAntesAlteracao.getIndAcumulavelIettr());
			// iettrh.setIndPublicoIettr(iettrhAntesAlteracao.getIndPublicoIettr());
			// iettrh.setIndTipoQtde(iettrhAntesAlteracao.getIndTipoQtde());
			// iettrh.setIndValorFinalIettr(iettrhAntesAlteracao.getIndValorFinalIettr());
			// iettrh.setIndRealPorLocal(iettrhAntesAlteracao.getIndRealPorLocal());
			//
			// // alteracao nova tela
			// iettrh.setIndPrevPorLocal(iettrhAntesAlteracao.getIndPrevPorLocal());
			// // alteracao nova tela
			//
			// iettrh.setSisAtributoSatb(iettrhAntesAlteracao.getSisAtributoSatb());
			// iettrh.setFonteIettr(iettrhAntesAlteracao.getFonteIettr());
			// iettrh.setIndiceMaisRecenteIettr(iettrhAntesAlteracao.getIndiceMaisRecenteIettr());
			// iettrh.setDataApuracaoIettr(iettrhAntesAlteracao.getDataApuracaoIettr());
			// iettrh.setFormulaIettr(iettrhAntesAlteracao.getFormulaIettr());
			// iettrh.setPeriodicidadePrdc(iettrhAntesAlteracao.getPeriodicidadePrdc());
			// iettrh.setUsuarioUsuManutencao(itemEstrtIndResul.getUsuarioUsuManutencao());
			// iettrh.setDataUltManutencao(itemEstrtIndResul.getDataUltManutencao());
			// iettrh.setHistoricoMaster(historicoMaster);
			//
			// session.save(iettrh);
			//
			// }

			if (!qtdPrevistaBloqueado && itemEstrtIndResul.getIndPrevPorLocal().equals("N")) { //atualiza os valores previstos somente quando n�o for por local. 
				// excluir todas as quantidades previstas dentro do per�odo do item de estrutura.
				ItemEstrutFisicoDao itemEstrutFisicoDao = new ItemEstrutFisicoDao(request);
				List listaAux = itemEstrutFisicoDao.getListaPrevistosIettr(itemEstrtIndResul);
				//listaAux.addAll(itemEstrtIndResul.getItemEstrutFisicoIettfs());

				Iterator itAux = listaAux.iterator();

				while (itAux.hasNext()) {
					ItemEstrutFisicoIettf itemEstrutFisico = (ItemEstrutFisicoIettf) itAux.next();
					itemEstrutFisico.setUsuarioUsuManutencao(itemEstrtIndResul.getUsuarioUsuManutencao());
					itemEstrutFisico.setDataUltManutencao(new Date());

					// if ("S".equals(config.getIndGerarHistoricoCfg())) {
					//
					// HistoricoIettfH iettfh = new HistoricoIettfH();
					//
					// iettfh.setDataInclusaoIettf(itemEstrutFisico.getDataInclusaoIettf());
					// iettfh.setDataUltManutencao(itemEstrutFisico.getDataUltManutencao());
					// iettfh.setItemEstrtIndResulIettr(itemEstrutFisico.getItemEstrtIndResulIettr());
					// iettfh.setExercicioExe(itemEstrutFisico.getExercicioExe());
					// iettfh.setQtdPrevistaIettf(itemEstrutFisico.getQtdPrevistaIettf());
					// iettfh.setUsuManutencao(itemEstrutFisico.getUsuarioUsuManutencao());
					// iettfh.setIndAtivoIettf(Dominios.NAO);
					// iettfh.setHistoricoMaster(historicoMaster);
					// session.save(iettfh);
					// }

					objetosExcluidos.add(itemEstrutFisico);

					session.delete(itemEstrutFisico);
				}
			} else {
				itemEstrtIndResul.setItemEstrutFisicoIettfs(new HashSet<ItemEstrutFisicoIettf>());
			}

			if (logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("EXC");
				Iterator it = objetosExcluidos.iterator();

				while (it.hasNext()) {
					logBean.setObj(it.next());
					loggerAuditoria.info(logBean.toString());
				}
			}

			// salva o pai

			if (logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setObj(itemEstrtIndResul);
				logBean.setOperacao("ALT");
				loggerAuditoria.info(logBean.toString());
			}

			if (!qtdPrevistaBloqueado && itemEstrtIndResul.getIndPrevPorLocal().equals("N")) {
				Iterator itQtd = listaQtd.iterator();
				while (itQtd.hasNext()) {
					ItemEstrutFisicoIettf itemEstrutFisico = (ItemEstrutFisicoIettf) itQtd.next();
					//Mantis 0010128 - Qtd prevista n�o � mais informado por exerc�cio					
					//itemEstrutFisico.getComp_id().setCodIettir(itemEstrtIndResul.getCodIettir());
					itemEstrutFisico.setItemEstrtIndResulIettr(itemEstrtIndResul);
					itemEstrutFisico.setUsuarioUsuManutencao(itemEstrtIndResul.getUsuarioUsuManutencao());
					itemEstrutFisico.setDataUltManutencao(new Date());
					// itemEstrutFisico.setIndExclusaoPosHistorico(Boolean.FALSE);
					session.save(itemEstrutFisico);

					// HistoricoIettfH iettfh = new HistoricoIettfH();
					// iettfh.setDataInclusaoIettf(itemEstrutFisico.getDataInclusaoIettf());
					// iettfh.setItemEstrtIndResulIettr((ItemEstrtIndResulIettr)
					// this.buscar(ItemEstrtIndResulIettr.class,
					// itemEstrutFisico.getComp_id()
					// .getCodIettir()));
					// iettfh.setExercicioExe((ExercicioExe)
					// this.buscar(ExercicioExe.class,
					// itemEstrutFisico.getComp_id().getCodExe()));
					// iettfh.setQtdPrevistaIettf(itemEstrutFisico.getQtdPrevistaIettf());
					// iettfh.setUsuManutencao(itemEstrutFisico.getUsuarioUsuManutencao());
					// iettfh.setIndAtivoIettf(itemEstrutFisico.getIndAtivoIettf());
					// iettfh.setHistoricoMaster(historicoMaster);
					// iettfh.setDataUltManutencao(itemEstrutFisico.getDataUltManutencao());
					// session.save(iettfh);

					objetosInseridos.add(itemEstrutFisico);
				}
			}

			pojoHistorico.carregar(itemEstrtIndResul);
			//gerarHistorico(Historico.ALTERAR);
			tx.commit();

			if (logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("INC");
				Iterator it2 = objetosInseridos.iterator();

				while (it2.hasNext()) {
					logBean.setObj(it2.next());
					loggerAuditoria.info(logBean.toString());
				}
			}
		} catch (HibernateException e) {
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
	 * Sobrecarga do m�todo "alterar"
	 * 
	 * @param itemEstrtIndResul
	 * @param listaQtd
	 * @param iettrCor
	 * @throws ECARException
	 */
	public void alterar(ItemEstrtIndResulIettr itemEstrtIndResul, List listaQtd, List iettrCor) throws ECARException {
		Boolean qtdPrevistaBloqueado = false;
		ItemEstruturaIett itemEstrutura = itemEstrtIndResul.getItemEstruturaIett();
		if (itemEstrutura.getIndBloqPlanejamentoIett() != null && itemEstrutura.getIndBloqPlanejamentoIett().equals("S")) {
			EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
			EstruturaFuncaoEttf estruturaFuncaoQtdePrevista = new EstruturaFuncaoEttf();
			estruturaFuncaoQtdePrevista = (EstruturaFuncaoEttf) estruturaFuncaoDao.getQuantidadesPrevistas(itemEstrutura.getEstruturaEtt());
			if (estruturaFuncaoQtdePrevista.getIndPodeBloquearEttf().equals("S")) {
				qtdPrevistaBloqueado = true;
			}
		}

		inicializarLogBean();
		Transaction tx = null;

		try {

			ArrayList objetosInseridos = new ArrayList();
			ArrayList objetosExcluidos = new ArrayList();
			tx = session.beginTransaction();

			// Atualizando a lista de ItemEstrtIndResulCorIettrcor
			List listIettrcor = new ArrayList();
			if (itemEstrtIndResul.getItemEstrtIndResulCorIettrcores() != null)
				listIettrcor.addAll(itemEstrtIndResul.getItemEstrtIndResulCorIettrcores());

			Iterator itIettrcor = listIettrcor.iterator();
			while (itIettrcor.hasNext()) {
				ItemEstrtIndResulCorIettrcor iettrcor = (ItemEstrtIndResulCorIettrcor) itIettrcor.next();
				session.update(iettrcor);
			}

			// ConfiguracaoDao dao = new ConfiguracaoDao(request);
			// ConfiguracaoCfg config = dao.getConfiguracao();
			//
			// HistoricoMaster historicoMaster = new HistoricoMaster();
			//
			// if ("S".equals(config.getIndGerarHistoricoCfg())) {
			//
			// historicoMaster.setDataHoraHistorico(new Date());
			// historicoMaster.setUsuManutencao(itemEstrtIndResul.getUsuarioUsuManutencao());
			// historicoMaster.setCodReferenciaGeral(itemEstrtIndResul.getItemEstruturaIett().getCodIett());
			// historicoMaster.setHistoricoMotivo((HistoricoMotivo)
			// super.buscar(HistoricoMotivo.class, Long.valueOf(23)));
			// session.save(historicoMaster);
			//
			// HistoricoIettrH iettrh = new HistoricoIettrH();
			// ItemEstrtIndResulIettr iettrhAntesAlteracao =
			// (ItemEstrtIndResulIettr)
			// super.buscar(ItemEstrtIndResulIettr.class, itemEstrtIndResul
			// .getCodIettir());
			// iettrh.setItemEstruturaIett(iettrhAntesAlteracao.getItemEstruturaIett());
			// iettrh.setUnidMedidaIettr(iettrhAntesAlteracao.getUnidMedidaIettr());
			// iettrh.setCodUnidMedidaIettr(iettrhAntesAlteracao.getCodUnidMedidaIettr());
			// iettrh.setDescricaoIettir(iettrhAntesAlteracao.getDescricaoIettir());
			// iettrh.setNomeIettir(iettrhAntesAlteracao.getNomeIettir());
			// iettrh.setIndProjecaoIettr(iettrhAntesAlteracao.getIndProjecaoIettr());
			// iettrh.setIndAcumulavelIettr(iettrhAntesAlteracao.getIndAcumulavelIettr());
			// iettrh.setIndPublicoIettr(iettrhAntesAlteracao.getIndPublicoIettr());
			// iettrh.setIndTipoQtde(iettrhAntesAlteracao.getIndTipoQtde());
			// iettrh.setIndValorFinalIettr(iettrhAntesAlteracao.getIndValorFinalIettr());
			// iettrh.setIndRealPorLocal(iettrhAntesAlteracao.getIndRealPorLocal());
			//
			// // alteracao nova tela
			// iettrh.setIndPrevPorLocal(iettrhAntesAlteracao.getIndPrevPorLocal());
			// // alteracao nova tela
			//
			// iettrh.setSisAtributoSatb(iettrhAntesAlteracao.getSisAtributoSatb());
			// iettrh.setFonteIettr(iettrhAntesAlteracao.getFonteIettr());
			// iettrh.setIndiceMaisRecenteIettr(iettrhAntesAlteracao.getIndiceMaisRecenteIettr());
			// iettrh.setDataApuracaoIettr(iettrhAntesAlteracao.getDataApuracaoIettr());
			// iettrh.setFormulaIettr(iettrhAntesAlteracao.getFormulaIettr());
			// iettrh.setPeriodicidadePrdc(iettrhAntesAlteracao.getPeriodicidadePrdc());
			// iettrh.setUsuarioUsuManutencao(itemEstrtIndResul.getUsuarioUsuManutencao());
			// iettrh.setDataUltManutencao(itemEstrtIndResul.getDataUltManutencao());
			// iettrh.setHistoricoMaster(historicoMaster);
			//
			// session.save(iettrh);
			// }

			if ((!qtdPrevistaBloqueado) && itemEstrtIndResul.getIndPrevPorLocal().equals("N")) {
				// excluir todas as quantidades previstas considerando o per�odo do item.
				ItemEstrutFisicoDao itemEstrutFisicoDao = new ItemEstrutFisicoDao(request);
				List listaAux = itemEstrutFisicoDao.getListaPrevistosIettr(itemEstrtIndResul);				
				//List listaAux = new ArrayList();
				//listaAux.addAll(itemEstrtIndResul.getItemEstrutFisicoIettfs());

				Iterator itAux = listaAux.iterator();

				while (itAux.hasNext()) {
					ItemEstrutFisicoIettf itemEstrutFisico = (ItemEstrutFisicoIettf) itAux.next();
					itemEstrutFisico.setUsuarioUsuManutencao(itemEstrtIndResul.getUsuarioUsuManutencao());
					itemEstrutFisico.setDataUltManutencao(new Date());

					// if ("S".equals(config.getIndGerarHistoricoCfg())) {
					//
					// HistoricoIettfH iettfh = new HistoricoIettfH();
					//
					// iettfh.setDataInclusaoIettf(itemEstrutFisico.getDataInclusaoIettf());
					// iettfh.setDataUltManutencao(itemEstrutFisico.getDataUltManutencao());
					// iettfh.setItemEstrtIndResulIettr(itemEstrutFisico.getItemEstrtIndResulIettr());
					// iettfh.setExercicioExe(itemEstrutFisico.getExercicioExe());
					// iettfh.setQtdPrevistaIettf(itemEstrutFisico.getQtdPrevistaIettf());
					// iettfh.setUsuManutencao(itemEstrutFisico.getUsuarioUsuManutencao());
					// iettfh.setIndAtivoIettf(Dominios.NAO);
					// iettfh.setHistoricoMaster(historicoMaster);
					// session.save(iettfh);
					// }

					objetosExcluidos.add(itemEstrutFisico);

					session.delete(itemEstrutFisico);
				}
			}

			if (logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("EXC");
				Iterator it = objetosExcluidos.iterator();

				while (it.hasNext()) {
					logBean.setObj(it.next());
					loggerAuditoria.info(logBean.toString());
				}
			}

			// salva o pai
			session.update(itemEstrtIndResul);

			if (logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setObj(itemEstrtIndResul);
				logBean.setOperacao("ALT");
				loggerAuditoria.info(logBean.toString());
			}

			if ((!qtdPrevistaBloqueado) && itemEstrtIndResul.getIndPrevPorLocal().equals("N")) {
				Iterator itQtd = listaQtd.iterator();
				while (itQtd.hasNext()) {
					ItemEstrutFisicoIettf itemEstrutFisico = (ItemEstrutFisicoIettf) itQtd.next();
					//Mantis 0010128 - Qtd prevista n�o � mais informado por exerc�cio
					//itemEstrutFisico.getComp_id().setCodIettir(itemEstrtIndResul.getCodIettir());
					itemEstrutFisico.setItemEstrtIndResulIettr(itemEstrtIndResul);
					itemEstrutFisico.setUsuarioUsuManutencao(itemEstrtIndResul.getUsuarioUsuManutencao());
					itemEstrutFisico.setDataUltManutencao(new Date());
					// itemEstrutFisico.setIndExclusaoPosHistorico(Boolean.FALSE);
					session.save(itemEstrutFisico);

					// HistoricoIettfH iettfh = new HistoricoIettfH();
					// iettfh.setDataInclusaoIettf(itemEstrutFisico.getDataInclusaoIettf());
					// iettfh.setItemEstrtIndResulIettr((ItemEstrtIndResulIettr)
					// this.buscar(ItemEstrtIndResulIettr.class,
					// itemEstrutFisico.getComp_id()
					// .getCodIettir()));
					// iettfh.setExercicioExe((ExercicioExe)
					// this.buscar(ExercicioExe.class,
					// itemEstrutFisico.getComp_id().getCodExe()));
					// iettfh.setQtdPrevistaIettf(itemEstrutFisico.getQtdPrevistaIettf());
					// iettfh.setUsuManutencao(itemEstrutFisico.getUsuarioUsuManutencao());
					// iettfh.setIndAtivoIettf(itemEstrutFisico.getIndAtivoIettf());
					// iettfh.setHistoricoMaster(historicoMaster);
					// iettfh.setDataUltManutencao(itemEstrutFisico.getDataUltManutencao());
					// session.save(iettfh);

					objetosInseridos.add(itemEstrutFisico);
				}
			}

			pojoHistorico.carregar(itemEstrtIndResul);
			//gerarHistorico(Historico.ALTERAR);
			tx.commit();

			if (logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("INC");
				Iterator it2 = objetosInseridos.iterator();

				while (it2.hasNext()) {
					logBean.setObj(it2.next());
					loggerAuditoria.info(logBean.toString());
				}
			}
		} catch (HibernateException e) {
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
	 * Verifica se o indicador existe em algum objeto AcompRealFisicoArf
	 * 
         * @param indicador
	 * @return boolean (true - existe/false - n�o existe)
	 */
	public boolean verificarExistenciaEmAcompRealFisicoArf(ItemEstrtIndResulIettr indicador) {
		boolean existe = false;

		if (indicador.getAcompRealFisicoArfs() != null && !indicador.getAcompRealFisicoArfs().isEmpty()) {
			Iterator it = indicador.getAcompRealFisicoArfs().iterator();

			while (it.hasNext()) {
				AcompRealFisicoArf arf = (AcompRealFisicoArf) it.next();

				if (arf.getQtdRealizadaArf() != null && arf.getQtdRealizadaArf().doubleValue() > 0) {
					return true;
				}
			}
		}

		return existe;
	}

	public boolean verificarExistenciaEmPrevistos(ItemEstrtIndResulIettr indicador) {
		boolean existe = false;

		if (indicador.getItemEstrutFisicoIettfs() != null && !indicador.getItemEstrutFisicoIettfs().isEmpty()) {
			Iterator it = indicador.getItemEstrutFisicoIettfs().iterator();

			while (it.hasNext()) {
				ItemEstrutFisicoIettf itemFisico = (ItemEstrutFisicoIettf) it.next();
				//Est� sendo gravado 0 para previsto, nos casos em que for preenchido com 0
				if (itemFisico.getQtdPrevistaIettf() != null) {
					return true;
				}
			}
		}

		return existe;
	}
	
	
        /**
         *
         * @param itemEstrtIndResul
         * @return
         */
        public String getUnidadeUsada(ItemEstrtIndResulIettr itemEstrtIndResul) {
		if (itemEstrtIndResul != null) {
			if (itemEstrtIndResul.getCodUnidMedidaIettr() != null) {
				return itemEstrtIndResul.getCodUnidMedidaIettr().getDescricaoSatb();
			} else {
				return itemEstrtIndResul.getUnidMedidaIettr();
			}
		}
		return "";
	}

	/**
	 * Retorna os nomes dos Gr�ficos de Grupo de um determinado ItemEstrutura e
	 * os p�blicos definidos pelo sistema.
	 * 
         * @param iett
         * @return
         * @throws ECARException
	 */
	public List retornaNomesGraficoGrupo(ItemEstruturaIett iett) throws ECARException {
		List retorno = new ArrayList();

		if (iett != null) {
			try {
				Query q = this.getSession().createQuery(
						"select distinct iettIndRes.labelGraficoGrupoIettir from ItemEstrtIndResulIettr iettIndRes "
								+ "where (iettIndRes.itemEstruturaIett.codIett = :codIett or iettIndRes.indPublicoIettr like :indPublico) "
								+ "and iettIndRes.labelGraficoGrupoIettir is not null " + "and iettIndRes.indAtivoIettr like :indAtivo");
				q.setLong("codIett", iett.getCodIett().longValue());
				q.setString("indPublico", "S");
				q.setString("indAtivo", "S");

				retorno = q.list();
			} catch (HibernateException e) {
				this.logger.error(e);
				throw new ECARException(e);
			}
		}

		return retorno;
	}

	/**
	 * Retorna todos os indicadores do item que pertencem ao mesmo
	 * "Grupo de Gr�fico do Indicador" e todos os indicadores que, apesar de n�o
	 * serem do mesmo item foram definidos como p�blicos e pertencem ao mesmo
	 * "Grupo de Gr�fico do Indicador".
	 * 
	 * 
	 * @param itemEstrtIndResulIettr
	 * @return
	 * @throws ECARException
	 */
	public List retornaIndicadoresGraficoGrupo(ItemEstrtIndResulIettr itemEstrtIndResulIettr) throws ECARException {
		List retorno = new ArrayList();

		if (itemEstrtIndResulIettr != null && itemEstrtIndResulIettr.getItemEstruturaIett() != null) {
			try {
				Query q = this.getSession().createQuery(
						"select distinct iettIndRes from ItemEstrtIndResulIettr iettIndRes "
								+ "where (iettIndRes.itemEstruturaIett.codIett = :codIett or iettIndRes.indPublicoIettr like :indPublico) "
								+ "and iettIndRes.labelGraficoGrupoIettir like :labelGrupo " + "and iettIndRes.indAtivoIettr like :indAtivo");
				q.setLong("codIett", itemEstrtIndResulIettr.getItemEstruturaIett().getCodIett().longValue());
				q.setString("indPublico", "S");
				q.setString("labelGrupo", itemEstrtIndResulIettr.getLabelGraficoGrupoIettir());
				q.setString("indAtivo", "S");

				retorno = q.list();
			} catch (HibernateException e) {
				this.logger.error(e);
				throw new ECARException(e);
			}
		}
		return retorno;
	}

	/**
	 * Verifica no banco a exist�ncia de algum indicador com o nome e grupo
	 * passado como par�metro e retorna o c�digo iett.
	 * 
	 * @param nomeIettir
	 * @param labelGraficoGrupoIettir
	 * @return Retorna o c�digo iett do item encontrado.
	 * @throws ECARException
	 */
	public long verificarExistenciaNomeIndicadorGraficoGrupo(String nomeIettir, String labelGraficoGrupoIettir) throws ECARException {
		List indicadoresGrafico = new ArrayList();

		try {
			Query q = this.getSession().createQuery(
					"select distinct iettIndRes from ItemEstrtIndResulIettr iettIndRes " + "where (upper(iettIndRes.nomeIettir) = upper(:nomeIettir) and "
							+ "		upper(iettIndRes.labelGraficoGrupoIettir) = upper(:labelGraficoGrupoIettir) and" + "		iettIndRes.indAtivoIettr = :indAtivo)");
			q.setString("nomeIettir", nomeIettir);
			q.setString("labelGraficoGrupoIettir", labelGraficoGrupoIettir);
			q.setString("indAtivo", "S");

			indicadoresGrafico = q.list();

			if (indicadoresGrafico.size() > 0) {
				ItemEstrtIndResulIettr indResul = (ItemEstrtIndResulIettr) indicadoresGrafico.get(0);
				return indResul.getCodIettir();
			}

			// Retorna -1 caso nenhum indicador seja encontrado.
			return -1;

		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}

	/*
	 * Novos M�todos para hist�rico
	 */

        /**
         *
         * @param itemEstrtIndResul
         * @return
         */
        public String getUnidadeUsada(HistoricoItemEstrtIndResulIettr itemEstrtIndResul) {
		if (itemEstrtIndResul != null) {
			if (itemEstrtIndResul.getCodUnidMedidaIettr() != null) {
				return itemEstrtIndResul.getCodUnidMedidaIettr().getDescricaoSatb();
			} else {
				return itemEstrtIndResul.getUnidMedidaIettr();
			}
		}
		return "";
	}

        /**
         *
         * @param itemEstrtIndResul
         * @return
         * @throws ECARException
         */
        public String getSomaQuantidadePrevista(HistoricoItemEstrtIndResulIettr itemEstrtIndResul) throws ECARException {
		try {
			String retorno = "0";
			double total = 0;

			if ("S".equals(itemEstrtIndResul.getIndAcumulavelIettr())) {
				if (itemEstrtIndResul.getItemEstrutFisicoIettfs() != null) {
					Iterator it = itemEstrtIndResul.getItemEstrutFisicoIettfs().iterator();
					while (it.hasNext()) {
						ItemEstrutFisicoIettf itemEstrutFisico = (ItemEstrutFisicoIettf) it.next();
						if ("S".equalsIgnoreCase(itemEstrutFisico.getIndAtivoIettf()))
							total += itemEstrutFisico.getQtdPrevistaIettf().doubleValue();
					}
					retorno = Util.formataMoeda(total);
				}
			} else {

				if ("M".equals(itemEstrtIndResul.getIndValorFinalIettr())) { // Maior
					if (itemEstrtIndResul.getItemEstrutFisicoIettfs() != null) {
						Iterator it = itemEstrtIndResul.getItemEstrutFisicoIettfs().iterator();
						double maior = 0;
						while (it.hasNext()) {
							ItemEstrutFisicoIettf itemEstrutFisico = (ItemEstrutFisicoIettf) it.next();
							if ("S".equalsIgnoreCase(itemEstrutFisico.getIndAtivoIettf())) {
								if (itemEstrutFisico.getQtdPrevistaIettf().doubleValue() > maior) {
									maior = itemEstrutFisico.getQtdPrevistaIettf().doubleValue();
								}
								total = maior;
							}
						}
						retorno = Util.formataMoeda(total);
					}
				} else if ("U".equals(itemEstrtIndResul.getIndValorFinalIettr())) { // Ultimo
					double ultimo = 0;
					if (itemEstrtIndResul.getItemEstrutFisicoIettfs() != null) {
						Iterator it = itemEstrtIndResul.getItemEstrutFisicoIettfs().iterator();
						while (it.hasNext()) {
							ItemEstrutFisicoIettf itemEstrutFisico = (ItemEstrutFisicoIettf) it.next();
							if ("S".equalsIgnoreCase(itemEstrutFisico.getIndAtivoIettf())) {
								ultimo = itemEstrutFisico.getQtdPrevistaIettf().doubleValue();
								break;
							}
						}
					}
					retorno = Util.formataMoeda(ultimo);
				} else if ("N".equals(itemEstrtIndResul.getIndValorFinalIettr())) {
					retorno = "";
				}
			}
			return retorno;
		} catch (Exception e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}

        /**
         *
         * @param inicio
         * @param fim
         * @return
         */
    @SuppressWarnings("empty-statement")
        public List<HistoricoItemEstrtIndResulIettr> listaHistorico(Date inicio, Date fim) {
		historico = new Historico<HistoricoItemEstrtIndResulIettr, ItemEstrtIndResulIettr>() {
		};
		try {
			return historico.listaObjetoHistorico(null, inicio, fim, null, null);
			
 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		;
		return null;
	}

    /**
     *
     * @param chaveHistorico
     * @return
     */
    @SuppressWarnings("empty-statement")
	public HistoricoItemEstrtIndResulIettr getHistorico(Long chaveHistorico) {
		historico = new Historico<HistoricoItemEstrtIndResulIettr, ItemEstrtIndResulIettr>() {
		};
		try {
			return historico.getObjetoSerializado(chaveHistorico);
		} catch (Exception e) {
			e.printStackTrace();
		}
		;
		return null;
	}

	private void gerarHistorico(Long tipoHistorico) throws ECARException {

		if (this.pojoHistorico != null) {
			historico = new Historico<HistoricoItemEstrtIndResulIettr, ItemEstrtIndResulIettr>() {
			};

			ItemEstruturaDao iettDao = new ItemEstruturaDao(null);
			ItemEstruturaIett itemEstruturaIett = (ItemEstruturaIett) iettDao
					.buscar(ItemEstruturaIett.class, pojoHistorico.getItemEstruturaIett().getCodIett());
			EstruturaDao estrDao = new EstruturaDao(null);
			EstruturaEtt ett = (EstruturaEtt) estrDao.buscar(EstruturaEtt.class, pojoHistorico.getItemEstruturaIett().getEstruturaEtt().getCodEtt());

			Hibernate.initialize(ett);
			Hibernate.initialize(itemEstruturaIett);
			itemEstruturaIett.setEstruturaEtt(ett);

			pojoHistorico.setItemEstruturaIett(itemEstruturaIett);

			this.historico.setHistorico(pojoHistorico, tipoHistorico, ((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario(),
					session);
		}
	}
	/*
	 * Fim dos M�todos para hist�rico
	 */

	public Set<LocalGrupoLgp> getGrupoLocaisItem(ItemEstruturaIett itemEstrt){
		Set<LocalGrupoLgp> grupos = new HashSet<LocalGrupoLgp>();
		
		Iterator it = itemEstrt.getItemEstrutLocalIettls().iterator();
		while (it.hasNext()){
			ItemEstrutLocalIettl local = (ItemEstrutLocalIettl) it.next();
            grupos.add(local.getLocalItemLit().getLocalGrupoLgp());
            getInferior(local.getLocalItemLit(), grupos);            
		}
		
		
		return grupos;
	}

	private void getInferior(LocalItemLit local,
			Set<LocalGrupoLgp> grupos) {
		
		if (local.getLocalItemHierarquiaLithsByCodLitPai() != null){
			Iterator it = local.getLocalItemHierarquiaLithsByCodLitPai().iterator();
			while (it.hasNext()){
				LocalItemLit loc = (LocalItemLit) it.next();
				grupos.add(loc.getLocalGrupoLgp());
	            getInferior(loc, grupos);            
				
			}
			
		}
		
	}
	
	public boolean podeAlterarNivelAbrangencia(ItemEstrtIndResulIettr itemEstrtIndResul){

		boolean resultado = true;
		boolean comRegistrosEmPrevistos = verificarExistenciaEmPrevistos(itemEstrtIndResul);
		boolean comRegistrosEmRealFisico = verificarExistenciaEmAcompRealFisicoArf(itemEstrtIndResul);
		boolean indicadorPrevPorLocal = ("S".equals(itemEstrtIndResul.getIndPrevPorLocal())); 
		boolean indicadorRealPorLocal = ("S".equals(itemEstrtIndResul.getIndRealPorLocal()));
		
		if ((indicadorPrevPorLocal)&&(indicadorRealPorLocal)){//Se os dois estiverem como por local, s� pode alterar a abrangencia se ambos estiverem vazios
			resultado = (!comRegistrosEmPrevistos)&&(!comRegistrosEmRealFisico);
		}
		else
		{
			if ((indicadorPrevPorLocal)||(indicadorRealPorLocal)){ //se somente 1 for por local, se esse estiver com registro n�o pode alterar
				if (((indicadorPrevPorLocal)&&(comRegistrosEmPrevistos))||((indicadorRealPorLocal)&&(comRegistrosEmRealFisico)))
				{
				  resultado = false;	
				}
				
			}
			else //nenhum dois dois � por local, ent�o, n�o pode alterar abrang�ncia
				resultado = false;
		}
		
   	  return resultado;
	}
	
	/**
	 * Método que retorna a Quantidade Prevista parcial de um Indicador de Resultado em
	 * um Exercício.
	 * 
	 * @param indResul
	 * @param exercicio
	 * @param mesRef
	 * @return
	 * @throws NumberFormatException
	 */
	public double getQtdPrevistoParcialExercicio(ItemEstrtIndResulIettr indResul, ExercicioExe exercicio, long mesRef) {
		double quant = 0;

		try {
			// Mantis 0010128 - Qtd prevista não é mais informado por exercício
			ItemEstrutFisicoDao itemEstrutFisicoDao = new ItemEstrutFisicoDao(request);
			quant = itemEstrutFisicoDao.getQtdPrevistaParcialExercicio(exercicio, mesRef, indResul, null);
			
			//ItemEstrutFisicoIettf qtdPrevista = new ItemEstrutFisicoDao(request).buscar(indResul.getCodIettir(), exercicio.getCodExe());
			/*
			if (qtdPrevista != null) {
				if ("S".equalsIgnoreCase(qtdPrevista.getIndAtivoIettf())) {
					quant = qtdPrevista.getQtdPrevistaIettf().doubleValue();
				}
			}
			*/
		} catch (Exception e) {
			/* não realiza nada e devolve quant = 0 */
		}

		return quant;
	}
	
	
}// fim classe
