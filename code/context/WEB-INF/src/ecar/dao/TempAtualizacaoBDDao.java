package ecar.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.bean.ItemPPALoaBean;
import ecar.bean.MudarPaiItensTempBean;
import ecar.bean.TempBean;
import ecar.exception.ECARException;
import ecar.historico.HistoricoIettus;
import ecar.permissao.ControlePermissao;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompRealFisicoLocalArfl;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EfIettFonteTotEfieft;
import ecar.pojo.EfIettFonteTotEfieftPK;
import ecar.pojo.EfItemEstContaEfiec;
import ecar.pojo.EfItemEstPrevisaoEfiep;
import ecar.pojo.EfItemEstPrevisaoEfiepPK;
import ecar.pojo.EstAtribTipoAcompEata;
import ecar.pojo.EstrutTpFuncAcmpEtttfa;
import ecar.pojo.EstruturaAcessoEtta;
import ecar.pojo.EstruturaAtributoEttat;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.ExercicioExe;
import ecar.pojo.FonteRecursoFonr;
import ecar.pojo.HistoricoIettusH;
import ecar.pojo.HistoricoMaster;
import ecar.pojo.HistoricoMotivo;
import ecar.pojo.IettIndResulRevIettrr;
import ecar.pojo.ItemEstFisicoRevIettfr;
import ecar.pojo.ItemEstLocalRevIettlr;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrutFisicoIettf;
import ecar.pojo.ItemEstrutFisicoIettfPK;
import ecar.pojo.ItemEstrutLocalIettl;
import ecar.pojo.ItemEstrutUsuarioIettus;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturarevisaoIettrev;
import ecar.pojo.LocalItemLit;
import ecar.pojo.RecursoRec;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SituacaoSit;

/**
 * DAO temporária para atualização do banco
 * 
 * @author cristiano
 */
public class TempAtualizacaoBDDao extends Dao {
    /**
     * 
     * @param request
     */
    public TempAtualizacaoBDDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
    
    
    /**
	 * Atualizar os registros de AcompRealFisico devido a mudanças na tabela
	 * 
     * @param request
     * @throws ECARException
	 */
	public void atualizar (HttpServletRequest request) throws ECARException {
		Transaction tx = null;

		try {
			/*
			// PARTE 1 - não é mais necessária - deve ser executado por script
			System.out.println("TempAtualizacaoARFDao.atualizar() - Iniciando transacao... PARTE 1");
			tx = session.beginTransaction();

			// obter os ARF
			String query = "select ARF from AcompRealFisicoArf as ARF";
			
			List listaARF = this.getSession().find(query);
			
			System.out.println("TempAtualizacaoARFDao.atualizar() - Total de registros para atualização: " + listaARF.size());

			Iterator it = listaARF.iterator();

			while (it.hasNext()){
				AcompRealFisicoArf arf = (AcompRealFisicoArf)it.next();
				
				// setar o mês/ano do AREF no ARF
				arf.setMesArf(Long.valueOf(arf.getAcompReferenciaItemAri().getAcompReferenciaAref().getMesAref()));
				arf.setAnoArf(Long.valueOf(arf.getAcompReferenciaItemAri().getAcompReferenciaAref().getAnoAref()));
				
				session.update(arf);
			}

			System.out.println("TempAtualizacaoARFDao.atualizar() - Commit da transacao... PARTE 1");
			tx.commit();
			*/

			// PARTE 2
			System.out.println("TempAtualizacaoARFDao.atualizar() - Iniciando transacao...");
			tx = session.beginTransaction();

			// obter os ARF
			String query = "select ARF from AcompRealFisicoArf as ARF order by ARF.itemEstruturaIett.codIett, ARF.itemEstrtIndResulIettr.codIettir, ARF.mesArf, ARF.anoArf";
			
			List listaARF = new ArrayList();//this.getSession().find(query);
			
			System.out.println("TempAtualizacaoARFDao.atualizar() - Total de registros para verificação: " + listaARF.size());

			List listRepetidos = new ArrayList();
			int cont = 0;

			while (cont < listaARF.size()){
				AcompRealFisicoArf arf = (AcompRealFisicoArf)listaARF.get(cont);
				
				String iettMesAnoAtual = String.valueOf(arf.getItemEstruturaIett().getCodIett())
										+ String.valueOf(arf.getItemEstrtIndResulIettr().getCodIettir())
										+ String.valueOf(arf.getMesArf()) 
										+ String.valueOf(arf.getAnoArf());
				cont++;
				if(cont < listaARF.size()) {
					AcompRealFisicoArf arfAux = (AcompRealFisicoArf)listaARF.get(cont);
					
					if(arfAux != null) {
						String iettMesAnoAux = String.valueOf(arfAux.getItemEstruturaIett().getCodIett())
													+ String.valueOf(arfAux.getItemEstrtIndResulIettr().getCodIettir())
													+ String.valueOf(arfAux.getMesArf()) 
													+ String.valueOf(arfAux.getAnoArf());
						int ultimoContRepetido = -1;
						if (iettMesAnoAux.equals(iettMesAnoAtual)){
							listRepetidos.add(arfAux);
							ultimoContRepetido = cont;
						}
						int contAux = cont;
						while (iettMesAnoAux.equals(iettMesAnoAtual) && contAux < listaARF.size()){
							contAux++;
							if(contAux < listaARF.size()) {
								arfAux = (AcompRealFisicoArf)listaARF.get(contAux);
								
								iettMesAnoAux = String.valueOf(arfAux.getItemEstruturaIett().getCodIett())
														+ String.valueOf(arfAux.getItemEstrtIndResulIettr().getCodIettir())
														+ String.valueOf(arfAux.getMesArf()) 
														+ String.valueOf(arfAux.getAnoArf());

								if (iettMesAnoAux.equals(iettMesAnoAtual)){
									listRepetidos.add(arfAux);
									ultimoContRepetido = contAux;
								}
							}
						}
						
						if(ultimoContRepetido > -1) {
							cont = ultimoContRepetido + 1;
						}
					}
				}
			}

			Iterator it = listRepetidos.iterator();

			while (it.hasNext()){
				AcompRealFisicoArf arf = (AcompRealFisicoArf)it.next();
				
				System.out.println("arf para ser deletado: " + arf.getItemEstruturaIett().getCodIett() + " - " + arf.getItemEstrtIndResulIettr().getCodIettir() + " - " + arf.getMesArf() + " - " + arf.getAnoArf() + " - " + arf.getCodArf());
				session.delete(arf);
			}

			System.out.println("TempAtualizacaoARFDao.atualizar() - Commit da transacao...");
			tx.commit();

		} catch (Exception e) {
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
	 * Atualizar os registros de AcompRealFisico devido ao mantis 6533
	 * 
     * @param request
     * @throws ECARException
	 */
	public void atualizar2 (HttpServletRequest request) throws ECARException {
		Transaction tx = null;

		try {
			System.out.println("TempAtualizacaoARFDao.atualizar2() - Iniciando transacao...");
			tx = session.beginTransaction();

			// situação "Em andamento": cod_sit=12
			String query = "select situacao from SituacaoSit as situacao where situacao.codSit=12";
			List listaSituacao = new ArrayList();//this.getSession().find(query);
			SituacaoSit situacao = (SituacaoSit)listaSituacao.get(0);
			System.out.println("TempAtualizacaoARFDao.atualizar2() - situacao: " + situacao.getDescricaoSit());

			System.out.println("TempAtualizacaoARFDao.atualizar2() - obter dados para 2005");

			// obter as quantidades previstas de revisão de 2004 (cod_exe = 1), de 2005 (cod_exe = 2)
			query = "select IETTFR from ItemEstFisicoRevIettfr as IETTFR where IETTFR.exercicioExe.codExe=2";
			
			int arfAtualizados = 0;
			
			List listaIETTFR = new ArrayList();//this.getSession().find(query);
			
			if(listaIETTFR != null && !listaIETTFR.isEmpty()) {
				// obter os ARF de Dezembro/2004
				query = "select ARF from AcompRealFisicoArf as ARF where ARF.mesArf=12 and ARF.anoArf=2005 order by ARF.itemEstruturaIett.codIett";
				
				List listaARF = new ArrayList();//this.getSession().find(query);
				
				if(listaARF != null && !listaARF.isEmpty()) {
					Iterator itARF = listaARF.iterator();
		
					while (itARF.hasNext()){
						AcompRealFisicoArf arf = (AcompRealFisicoArf)itARF.next();
						
						Iterator itIETTFR = listaIETTFR.iterator();
						while (itIETTFR.hasNext()){
							ItemEstFisicoRevIettfr iettfr = (ItemEstFisicoRevIettfr)itIETTFR.next();
							
							if(iettfr.getIettIndResulRevIettrr().getItemEstrtIndResulIettr().equals(arf.getItemEstrtIndResulIettr())) {
								System.out.println("TempAtualizacaoARFDao.atualizar2() - item: " + arf.getItemEstruturaIett().getCodIett() + " - qtde: " + iettfr.getQtdPrevistaIettfr());

								arf.setQtdRealizadaArf(iettfr.getQtdPrevistaIettfr());
	
								// situação "Em andamento"
								arf.setSituacaoSit(situacao);
								
								session.update(arf);
								
								arfAtualizados++;
								break;
							}
						}

					}
				}
			}

			System.out.println("TempAtualizacaoARFDao.atualizar2() - arfAtualizados: " + arfAtualizados);
			System.out.println("TempAtualizacaoARFDao.atualizar2() - Commit da transacao...");
			
			tx.commit();
		} catch (Exception e) {
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
	 * Remover registros de ARI desnecessários (Mantis 7135)
	 * 
         * @param request
         * @throws ECARException
	 */
	public void atualizar3 (HttpServletRequest request) throws ECARException {
		Transaction tx = null;

		try {
			List listaAriParaRemover = new ArrayList();
			System.out.println("TempAtualizacaoARFDao.atualizar3() - Iniciando transacao...");
			tx = session.beginTransaction();

			// selecionar os ARIs do acompanhamento Outubro/2006 - PPA (cod_aref = 46)
			String query = "select ari from AcompReferenciaItemAri as ari where ari.acompReferenciaAref.codAref=46";
			List listaAri = new ArrayList();//this.getSession().find(query);

			if(listaAri != null && !listaAri.isEmpty()) {
				System.out.println("TempAtualizacaoARFDao.atualizar3() - total de aris: " + listaAri.size());

				Iterator itARI = listaAri.iterator();
		
				while (itARI.hasNext()){
					AcompReferenciaItemAri ari = (AcompReferenciaItemAri)itARI.next();
					List listaNivel = new ArrayList(ari.getItemEstruturaIett().getItemEstruturaNivelIettns());
					
					if(listaNivel != null && !listaNivel.isEmpty()) {
						Iterator itNivel = listaNivel.iterator();
						boolean manter = false;
						while (itNivel.hasNext()){
							SisAtributoSatb satb = (SisAtributoSatb)itNivel.next();
							
							// nivel de planejamento diferente de 35 e 39 (PPA e PPA Apoio)
							if(satb.getCodSatb().longValue() == 35 || satb.getCodSatb().longValue() == 39) {
								manter = true;
								break;
							}
						}
						
						if(!manter) {
							listaAriParaRemover.add(ari);
						}
					}
				}
			}
			
			Iterator itARI = listaAriParaRemover.iterator();
			
			while (itARI.hasNext()){
				AcompReferenciaItemAri ari = (AcompReferenciaItemAri)itARI.next();
				System.out.println("TempAtualizacaoARFDao.atualizar3() - item: " + ari.getItemEstruturaIett().getNomeIett());

				session.delete(ari);
			}

			System.out.println("TempAtualizacaoARFDao.atualizar3() - listaAriParaRemover: " + listaAriParaRemover.size());
			System.out.println("TempAtualizacaoARFDao.atualizar3() - Commit da transacao...");
			
			tx.commit();
		} catch (Exception e) {
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
	 * Remover registros de ARF desnecessários (Mantis 7135)
	 * 
         * @param request
         * @throws ECARException
	 */
	public void atualizar4 (HttpServletRequest request) throws ECARException {
		Transaction tx = null;

		try {
			List listaArfParaRemover = new ArrayList();
			//System.out.println("TempAtualizacaoARFDao.atualizar4() - Iniciando transacao...");
			//tx = session.beginTransaction();

			// selecionar os ARFs de 10/2006
			String query = "select arf from AcompRealFisicoArf as arf where arf.mesArf=10 and arf.anoArf=2006";
			List listaArf = new ArrayList();//this.getSession().find(query);

			if(listaArf != null && !listaArf.isEmpty()) {
				// selecionar os ARIs do acompanhamento Outubro/2006 - PPA (cod_aref = 46) e Outubro/2006 - Monitorado (cod_aref = 39)
				query = "select ari from AcompReferenciaItemAri as ari where " +
						"ari.acompReferenciaAref.codAref=46 or ari.acompReferenciaAref.codAref=39";
				List listaAri = new ArrayList();//this.getSession().find(query);
	
				if(listaAri != null && !listaAri.isEmpty()) {
					System.out.println("TempAtualizacaoARFDao.atualizar4() - total de aris: " + listaAri.size());
	
					System.out.println("TempAtualizacaoARFDao.atualizar4() - Identificando ARF sem ARI correspondente");
					Iterator itARF = listaArf.iterator();
			
					while (itARF.hasNext()){
						AcompRealFisicoArf arf = (AcompRealFisicoArf)itARF.next();
						
						Iterator itARI = listaAri.iterator();
						
						boolean manter = false;
						
						System.out.println("TempAtualizacaoARFDao.atualizar4() - Item: " + arf.getItemEstruturaIett().getNomeIett());
						
						while (itARI.hasNext()){
							AcompReferenciaItemAri ari = (AcompReferenciaItemAri)itARI.next();
							
							if(arf.getItemEstruturaIett().equals(ari.getItemEstruturaIett())) {
								System.out.println("TempAtualizacaoARFDao.atualizar4() - Mantido");
								manter = true;
								break;
							}
						}
						
							
						if(!manter) {
							System.out.println("TempAtualizacaoARFDao.atualizar4() - Remover");
							listaArfParaRemover.add(arf);
						}
					}
				}
				
				System.out.println("TempAtualizacaoARFDao.atualizar4() - listaArfParaRemover: " + listaArfParaRemover.size());
				Iterator itARF = listaArfParaRemover.iterator();
				
				while (itARF.hasNext()){
					AcompRealFisicoArf arf = (AcompRealFisicoArf)itARF.next();
					System.out.println("TempAtualizacaoARFDao.atualizar4() - removendo item: " + arf.getItemEstruturaIett().getNomeIett());
	
					//session.delete(arf);
				}
	
				//System.out.println("TempAtualizacaoARFDao.atualizar4() - Commit da transacao...");
				
				//tx.commit();
			}
		} catch (Exception e) {
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
	 * Atualização de registros de metas físicas (Mantis 7283)
	 * 
         * @param request
         * @throws ECARException
	 */
	public void atualizar5 (HttpServletRequest request) throws ECARException {
		Transaction tx = null;

		try {
			System.out.println("TempAtualizacaoARFDao.atualizar5() - Iniciando transacao...");
			tx = session.beginTransaction();

			String query = "select iettfr from ItemEstFisicoRevIettfr as iettfr";
			List listaIettfr = this.getSession().createQuery(query).list();

			Iterator itIettfr = listaIettfr.iterator();

			System.out.println("TempAtualizacaoARFDao.atualizar5() - Buscando relação entre iettf e iettfr.");
			int i = 0;
			int j = 0;
			while (itIettfr.hasNext()){
				ItemEstFisicoRevIettfr iettfr = (ItemEstFisicoRevIettfr) itIettfr.next();
				
				query = "select iettf from ItemEstrutFisicoIettf iettf" +
						" where iettf.exercicioExe.codExe = " + iettfr.getExercicioExe().getCodExe().toString() +
						"   and iettf.itemEstrtIndResulIettr.codIettir = " + iettfr.getIettIndResulRevIettrr().getItemEstrtIndResulIettr().getCodIettir().toString();
				
				List listaIettf2 = this.getSession().createQuery(query).list();
				
				if(listaIettf2.size() > 1){
					throw new Exception("TempAtualizacaoARFDao.atualizar5() - listaIettf2.size() > 1");
				}
				/*
				 FIXME : Histórico : Valida a existência no histórico antes de excluir.
				else if (listaIettf2.size() == 1){
					i++;
					ItemEstrutFisicoIettf iettf = (ItemEstrutFisicoIettf) listaIettf2.get(0);

					ItemEstrtFisHistIettfh historico = new ItemEstrtFisHistIettfh();
					
					historico.setDataInclusaoIettfh(iettf.getDataInclusaoIettf());
					historico.setIndAtivoIettfh(iettf.getIndAtivoIettf());
					historico.setQtdPrevistaIettfh(iettf.getQtdPrevistaIettf());
					//historico.setItemEstrutFisicoIettf(iettf);
					historico.setExercicioExe((ExercicioExe) this.buscar(ExercicioExe.class, iettfr.getExercicioExe().getCodExe()));
					historico.setItemEstrtIndResulIettr((ItemEstrtIndResulIettr) this.buscar(ItemEstrtIndResulIettr.class, iettfr.getIettIndResulRevIettrr().getItemEstrtIndResulIettr().getCodIettir()));
					
					System.out.println("TempAtualizacaoARFDao.atualizar5() - Inserindo histórico...");
					session.save(historico);
					
					iettf.setDataInclusaoIettf(iettfr.getDataInclusaoIettfr());
					iettf.setIndAtivoIettf(iettfr.getIndAtivoIettfr());
					iettf.setQtdPrevistaIettf(iettfr.getQtdPrevistaIettfr());
					
					System.out.println("TempAtualizacaoARFDao.atualizar5() - Atualizando iettf com dados de revisão iettfr...");
					session.update(iettf);					
				}
				*/
				else {
					j++;
					ItemEstrutFisicoIettf iettf = new ItemEstrutFisicoIettf();

					iettf.setDataInclusaoIettf(iettfr.getDataInclusaoIettfr());
					iettf.setIndAtivoIettf(iettfr.getIndAtivoIettfr());
					iettf.setQtdPrevistaIettf(iettfr.getQtdPrevistaIettfr());
					iettf.setItemEstrtIndResulIettr(iettfr.getIettIndResulRevIettrr().getItemEstrtIndResulIettr());
					//Mantis 0010128 - Qtd prevista não é mais informado por exercício
					//iettf.setExercicioExe(iettfr.getExercicioExe());

					ItemEstrutFisicoIettfPK chave = new ItemEstrutFisicoIettfPK();
					
					chave.setCodExe(iettfr.getExercicioExe().getCodExe());
					chave.setCodIettir(iettfr.getIettIndResulRevIettrr().getItemEstrtIndResulIettr().getCodIettir());
					
					//FIXME: Ajustar Carga de Itens
					/* Mantis 0010128 - Qtd prevista não é mais informado por exercício
					 * Mudou a pk. não usa mais chave composta
					 * */
					//iettf.setComp_id(chave);
					
					System.out.println("TempAtualizacaoARFDao.atualizar5() - Inserindo iettf com dados de revisão iettfr...");
					session.save(iettf);
				}
				
			}

			System.out.println("TempAtualizacaoARFDao.atualizar5() - Iettf atualizados: " + i);
			System.out.println("TempAtualizacaoARFDao.atualizar5() - Iettf inseridos: " + j);
			System.out.println("TempAtualizacaoARFDao.atualizar5() - Iettf total: " + (i + j));

			
			System.out.println("TempAtualizacaoARFDao.atualizar5() - Commit da transacao...");
			tx.commit();
		} catch (Exception e) {
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
	 * Lista intens PPA
	 * @param request
	 * @throws ECARException
	 */
	public void listarItensPPA(HttpServletRequest request) throws ECARException {
		try {
			// 52 = PPA Novembro/2006
			String query = "select ari.itemEstruturaIett from AcompReferenciaItemAri ari where" +
					" ari.acompReferenciaAref.codAref=52";
			List listaPPA52 = this.getSession().createQuery(query).list();

			// 55 = PPA TESTE
			query = "select ari.itemEstruturaIett from AcompReferenciaItemAri ari where" +
			" ari.acompReferenciaAref.codAref=55";
			List listaPPA55 = this.getSession().createQuery(query).list();

			List itensRetirados = new ArrayList();
			Iterator it = listaPPA52.iterator();

			while (it.hasNext()){
				ItemEstruturaIett iett = (ItemEstruturaIett) it.next();
				
				if(!listaPPA55.contains(iett)) {
					itensRetirados.add(iett);
				}
			}

			it = itensRetirados.iterator();

			while (it.hasNext()){
				ItemEstruturaIett iett = (ItemEstruturaIett) it.next();
				String siglaItemPai = "";
				if(iett.getItemEstruturaIett() != null) {
					siglaItemPai = "Sigla Pai:" + iett.getItemEstruturaIett().getSiglaIett();
				}
				String str = siglaItemPai + "/Sigla: " + iett.getSiglaIett() + " - " + iett.getNomeIett() + " (CodIett: " + iett.getCodIett() + ")";
				System.out.println(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
	}
	
	/**
	 * Remove itens PPA
	 * @param request
	 * @throws ECARException
	 */
	public void removerItensPPA(HttpServletRequest request) throws ECARException {
		Transaction tx = null;

		try {
			System.out.println("TempAtualizacaoARFDao.removerItensPPA() - Iniciando transacao...");
			tx = session.beginTransaction();

			// 52 = PPA Novembro/2006
			String query = "select ari from AcompReferenciaItemAri ari where" +
					" ari.acompReferenciaAref.codAref=52";
			List listaAriPPA52 = this.getSession().createQuery(query).list();

			// 55 = PPA TESTE
			query = "select ari from AcompReferenciaItemAri ari where" +
					" ari.acompReferenciaAref.codAref=55";
			List listaAriPPA55 = this.getSession().createQuery(query).list();

			List itensAriPPA55 = new ArrayList();
			Iterator it = listaAriPPA55.iterator();

			while (it.hasNext()){
				AcompReferenciaItemAri ari = (AcompReferenciaItemAri) it.next();
				
				itensAriPPA55.add(ari.getItemEstruturaIett());
			}

			List itensAriIndevidos = new ArrayList();
			List itensIndevidos = new ArrayList();
			it = listaAriPPA52.iterator();

			while (it.hasNext()){
				AcompReferenciaItemAri ari = (AcompReferenciaItemAri) it.next();
				
				if(!itensAriPPA55.contains(ari.getItemEstruturaIett())) {
					itensIndevidos.add(ari.getItemEstruturaIett());
					itensAriIndevidos.add(ari);
				}
			}

			it = itensIndevidos.iterator();

			while (it.hasNext()){
				ItemEstruturaIett iett = (ItemEstruturaIett) it.next();
				String siglaItemPai = "";
				if(iett.getItemEstruturaIett() != null) {
					siglaItemPai = "Sigla Pai:" + iett.getItemEstruturaIett().getSiglaIett();
				}
				String str = siglaItemPai + "/Sigla: " + iett.getSiglaIett() + " - " + iett.getNomeIett() + " (CodIett: " + iett.getCodIett() + ")";
				System.out.println(str);
			}

			it = itensAriIndevidos.iterator();

			while (it.hasNext()){
				AcompReferenciaItemAri ari = (AcompReferenciaItemAri) it.next();
				session.delete(ari);
				System.out.println("Removendo ARI: " + ari.getCodAri());
			}
			System.out.println("TempAtualizacaoARFDao.removerItensPPA() - Commit da transacao...");
			tx.commit();
		} catch (Exception e) {
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
	 * Lista itens PPA(*parte 2)
	 * @param request
	 * @throws ECARException
	 */
	public void listarItensPPAParte2(HttpServletRequest request) throws ECARException {
		try {
			// 52 = PPA Novembro/2006
			String query = "select ari.itemEstruturaIett from AcompReferenciaItemAri ari where" +
					" ari.acompReferenciaAref.codAref=52";
			List listaPPA52 = this.getSession().createQuery(query).list();

			List itensParaRetirar = new ArrayList();
			Iterator it = listaPPA52.iterator();

			while (it.hasNext()){
				ItemEstruturaIett iett = (ItemEstruturaIett) it.next();
				
				System.out.println(iett.getSiglaIett() + iett.getNomeIett());
				
				if(iett.getItemEstruturaIett() != null) {
					ItemEstruturaIett iettAux = iett;
					while(iettAux.getItemEstruturaIett() != null) {
						if(iettAux.getItemEstruturaIett().getSituacaoSit() != null && "S".equals(iettAux.getItemEstruturaIett().getSituacaoSit().getIndConcluidoSit())) {
							if(!itensParaRetirar.contains(iett)) {
								itensParaRetirar.add(iett);
							}
						}
						iettAux = iettAux.getItemEstruturaIett();
					}
				}
				if(iett.getSituacaoSit() != null && (iett.getSituacaoSit() != null && "S".equals(iett.getSituacaoSit().getIndConcluidoSit()))) {
					if(!itensParaRetirar.contains(iett)) {
						itensParaRetirar.add(iett);
					}
				}
			}

			System.out.println("\n\n itensParaRetirar");

			it = itensParaRetirar.iterator();

			while (it.hasNext()){
				ItemEstruturaIett iett = (ItemEstruturaIett) it.next();
				System.out.println(iett.getCodIett() + " - " + iett.getNomeIett());
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
	}
	
	/*
	- Problema com os ARELs (GPS = obrigatório):
	Nos itens do acompanhamento "PPA - Novembro/2006", para cada ARI que NÃO tenha AREL para GPS:
	Se tiver ARF (através do item + mês + ano), remove o AREL do Administrador, senão remove o ARI + ARELs.
	 */
	
	/**
	 * Remove itens PPA(*parte2)
         * @param request
         * @throws ECARException
	 */
	public void removerItensPPAParte2(HttpServletRequest request) throws ECARException {
		Transaction tx = null;

		try {
			System.out.println("TempAtualizacaoBDDao.removerItensPPAParte2() - Iniciando transacao...");
			tx = session.beginTransaction();
			AcompRealFisicoDao arfDao = new AcompRealFisicoDao(null); 

			// 52 = PPA Novembro/2006
			String query = "select ari from AcompReferenciaItemAri ari where ari.acompReferenciaAref.codAref=52";
			List listaAriPPA52 = this.getSession().createQuery(query).list();

			Iterator it = listaAriPPA52.iterator();

			while (it.hasNext()){
				AcompReferenciaItemAri ari = (AcompReferenciaItemAri) it.next();
				
				boolean possuiArelParaGPS = false;
				Iterator itArel = ari.getAcompRelatorioArels().iterator();
				while(itArel.hasNext() && !possuiArelParaGPS) {
					AcompRelatorioArel arel = (AcompRelatorioArel)itArel.next();
					if(arel.getTipoFuncAcompTpfa().getCodTpfa().intValue() == 3) { //3 = GPS
						possuiArelParaGPS = true;
					}
				}
				
				if(!possuiArelParaGPS) {
					List listArf = arfDao.buscarPorIett(ari.getItemEstruturaIett().getCodIett(), Long.valueOf(11), Long.valueOf(2006));
					
					if(listArf != null && !listArf.isEmpty()) {
						Iterator itArelAdm = ari.getAcompRelatorioArels().iterator();
						while(itArelAdm.hasNext()) {
							AcompRelatorioArel arel = (AcompRelatorioArel)itArelAdm.next();
							if(arel.getTipoFuncAcompTpfa().getCodTpfa().intValue() == 1) { //1 = Administrador
								System.out.println("AREL de Administrador para remover (ARI/AREL/SIGLA e NOME ITEM): " + ari.getCodAri() + " / " + arel.getCodArel() + " / " + ari.getItemEstruturaIett().getSiglaIett() + " - " + ari.getItemEstruturaIett().getNomeIett());
								session.delete(arel);
								break;
							}
						}
					} else {
						System.out.println("ARI para remover (ARI/SIGLA e NOME ITEM): " + ari.getCodAri() + " / " + ari.getItemEstruturaIett().getSiglaIett() + " - " + ari.getItemEstruturaIett().getNomeIett());
						session.delete(ari);
					}
				}
			}

			System.out.println("TempAtualizacaoBDDao.removerItensPPAParte2() - Commit da transacao...");
			tx.commit();
		} catch (Exception e) {
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
	 * Mantis 7540 - Carga contas orçamentárias
	 * @param request
	 * @throws ECARException
	 */
	public void cargaContasOrcamentarias(HttpServletRequest request) throws ECARException {
		
		Transaction tx = null;

		final int ACAO = 3;
		
		List<EfItemEstContaEfiec> todasContas = new ArrayList<EfItemEstContaEfiec>();
		List<EfIettFonteTotEfieft> fontesTotEfieft = new ArrayList<EfIettFonteTotEfieft>();
		
		try {
			
			tx = session.beginTransaction();
			
			Query q = null;
			
			//Limpa a tabela cujo a conta se refira a ACAO
			q = this.session.createQuery("from EfItemEstContaEfiec conta where conta.itemEstruturaIett.estruturaEtt.codEtt = :codEtt");
			q.setLong("codEtt", ACAO);
			
			List<EfItemEstContaEfiec> contasEfiec = (List<EfItemEstContaEfiec>)q.list();
			
			for(EfItemEstContaEfiec obj : contasEfiec) {
				
				session.delete(obj);
			}
			
			q = this.session.createQuery("from FonteRecursoFonr");
			List<FonteRecursoFonr> fontesRecurso = (List<FonteRecursoFonr>)q.list();
			
			q = this.session.createQuery("from RecursoRec");
			List<RecursoRec> recursos = (List<RecursoRec>)q.list();
			
			q = this.session.createQuery("from ExercicioExe exe order by exe.dataInicialExe asc");
			List<ExercicioExe> exercicios = (List<ExercicioExe>)q.list();
						
			q = this.session.createQuery("from ItemEstruturaIett iett where iett.estruturaEtt.codEtt = :codEtt");
			q.setLong("codEtt", ACAO);
			
			int contador = 0;
					
			List ietts = q.list();
			Iterator it = ietts.iterator();
			
			while(it.hasNext()){
							
				ItemEstruturaIett iett = (ItemEstruturaIett) it.next();
				
				/************************************************/
				//Verifica se já existe objetos EfIettFonteTotEfieft para esse iett, caso contrário inclui o mesmo no banco.
				ItemEstruturaFonteRecursoDao dao = new ItemEstruturaFonteRecursoDao(request);
				//O ExercicioExe é apenas exigido no método, mas não considerado na consulta, a linha referente
				//ao ExercicioExe está comentado no método.
				List fontesRecursos = dao.getFontesRecursosByExercicio(iett, new ExercicioExe());
				
				if((fontesRecursos.size() == 0) && (!iett.getSiglaIett().substring(0,1).equals("0"))) {
				
					//Tá invertido, o Recurso responde pela Fonte do recurso e vice-versa
					for(Iterator<FonteRecursoFonr> itFontes = fontesRecurso.iterator(); itFontes.hasNext();) {
						
						FonteRecursoFonr fonte = itFontes.next();
						
						EfIettFonteTotEfieft efTotEfieft = new EfIettFonteTotEfieft();
						
						EfIettFonteTotEfieftPK efTotEfieftPK = new EfIettFonteTotEfieftPK();
						efTotEfieftPK.setCodFonr(fonte.getCodFonr());
						efTotEfieftPK.setCodIett(iett.getCodIett());
						
						efTotEfieft.setComp_id(efTotEfieftPK);
						efTotEfieft.setDataInclusaoEfieft(new Date());
						efTotEfieft.setIndAtivoEfieft("S");
						efTotEfieft.setItemEstruturaIett(iett);
						efTotEfieft.setFonteRecursoFonr(fonte);
						
						//Acrescenta o objeto na lista que será salva posteriormente no banco.
						fontesTotEfieft.add(efTotEfieft);
					}
					
				}
				
				/***********************************************/
				
				//Descartar ações em que a sigla inicie com zero
				if (!iett.getSiglaIett().substring(0,1).equals("0")) {
					
					int anoInicio = Integer.parseInt(new SimpleDateFormat("yyyy").format(iett.getDataInicioIett())); 
					int anoFim = anoInicio;
					
					if(iett.getDataTerminoIett() != null) {
						anoFim = Integer.parseInt(new SimpleDateFormat("yyyy").format((iett.getDataTerminoIett())));						
					}
					
					
					//loop dos anos a serem gerados ocorrências (contas)
					for(int inicio=anoInicio; inicio<=anoFim; inicio++) {
						
						List<EfItemEstContaEfiec> contas = new ArrayList<EfItemEstContaEfiec>();
											
						//Gerar 4 ocorrências (contas) por ano
						for(int i=0; i<4; i++) {
							
							EfItemEstContaEfiec conta = new EfItemEstContaEfiec();
							
							for(ExercicioExe exercicio : exercicios) {	
								
								int ano = Integer.parseInt(new SimpleDateFormat("yyyy").format(exercicio.getDataInicialExe()));
								
								if(inicio == ano) {
									conta.setExercicioExe(exercicio);
									break;
								}
							}
													
							conta.setIndAtivoEfiec("S");
							conta.setIndAcumuladoEfiec("N");
							conta.setContaSistemaOrcEfiec(iett.getSiglaIett() + " 0000");
							conta.setItemEstruturaIett(iett);
													
							contas.add(conta);
						}
																	
						for(Iterator<EfItemEstContaEfiec> itContas = contas.iterator(); itContas.hasNext();) {
							
							EfItemEstContaEfiec conta = itContas.next(); 
							
							for(Iterator<FonteRecursoFonr> itFontes = fontesRecurso.iterator(); itFontes.hasNext();) {
								
								FonteRecursoFonr fonte = itFontes.next();
								conta.setContaSistemaOrcEfiec(conta.getContaSistemaOrcEfiec() + " " + fonte.getSiglaFonr().trim());
								conta.setFonteRecursoFonr(fonte);
								//Tá invertido, o Recurso responde pela Fonte do recurso e vice-versa
								for(Iterator<RecursoRec> itRecursos = recursos.iterator(); itRecursos.hasNext();) {
									
									RecursoRec recurso = itRecursos.next();
									conta.setContaSistemaOrcEfiec(conta.getContaSistemaOrcEfiec() + " " + recurso.getSiglaRec().trim());
									conta.setRecursoRec(recurso);
									
									if(itContas.hasNext() && itRecursos.hasNext()) {
										conta = itContas.next();
										conta.setFonteRecursoFonr(fonte);
										conta.setContaSistemaOrcEfiec(conta.getContaSistemaOrcEfiec() + " " + fonte.getSiglaFonr().trim());
									} else if(itContas.hasNext() && itFontes.hasNext()) {
										conta = itContas.next();
									}
								}
							}
							
						}
						
						todasContas.addAll(contas);
					}
				} else {
					contador++;
				}
			}
			
			
			System.out.println("Total de Ações que iniciam em zero: " + contador);
		
			ItemEstruturaPrevisaoDao iePrevisaoDao = new ItemEstruturaPrevisaoDao(request);
			
			//Salva os objetos no banco
			for(EfItemEstContaEfiec obj : todasContas) {
				
				EfItemEstPrevisaoEfiep objEfiep = null;
				try
				{
					objEfiep = iePrevisaoDao.buscar(obj.getItemEstruturaIett().getCodIett(),
			            obj.getFonteRecursoFonr().getCodFonr(), obj.getRecursoRec().getCodRec(), obj.getExercicioExe().getCodExe());

				}catch (Exception e) {
					//Significa que não achou registro e poderá ser inserido um novo objeto no banco.
					objEfiep = new EfItemEstPrevisaoEfiep();  
					
					EfItemEstPrevisaoEfiepPK pk = new EfItemEstPrevisaoEfiepPK();
					pk.setCodExe(obj.getExercicioExe().getCodExe());
					pk.setCodFonr(obj.getFonteRecursoFonr().getCodFonr());
					pk.setCodIett(obj.getItemEstruturaIett().getCodIett());
					pk.setCodRec(obj.getRecursoRec().getCodRec());
					
					objEfiep.setComp_id(pk);
					objEfiep.setDataInclusaoEfiep(new Date());
					objEfiep.setExercicioExe(obj.getExercicioExe());
					objEfiep.setFonteRecursoFonr(obj.getFonteRecursoFonr());
					objEfiep.setIndAtivoEfiep("S");
					objEfiep.setItemEstruturaIett(obj.getItemEstruturaIett());
					objEfiep.setRecursoRec(obj.getRecursoRec());
					objEfiep.setValorAprovadoEfiep(new BigDecimal(0));
					objEfiep.setValorRevisadoEfiep(new BigDecimal(0));
					
					
			    	/*
			         * FIXME: Verificar esta regra
			         * Está fixo, pois falta fazer na tela para informar a espécie e a fonte
			         * rec 3 = fonte 49
			         * rec 4 = fonte 50
			         * rec 5 = fonte 51
			         */ 
//			    	if(objEfiep.getEspecieEsp() == null){
//			    		objEfiep.setEspecieEsp((EspecieEsp) buscar(EspecieEsp.class, Long.valueOf(0)));
//			    	}
//			    	
//			    	if(objEfiep.getFonteFon() == null){
//			    		if(objEfiep.getRecursoRec().getCodRec().longValue() == 3){
//			    			objEfiep.setFonteFon((FonteFon) buscar(FonteFon.class, Long.valueOf(49)));
//			    		}
//			    		if(objEfiep.getRecursoRec().getCodRec().longValue() == 4){
//			    			objEfiep.setFonteFon((FonteFon) buscar(FonteFon.class, Long.valueOf(50)));
//			    		}
//			    		if(objEfiep.getRecursoRec().getCodRec().longValue() == 5){
//			    			objEfiep.setFonteFon((FonteFon) buscar(FonteFon.class, Long.valueOf(51)));
//			    		}
//			    	}
					
					
					session.save(objEfiep);
				}
				
				session.save(obj);
			}
			
			
			//Salva os objetos no banco
			for(EfIettFonteTotEfieft obj : fontesTotEfieft) {
				session.save(obj);
			}			
			
			System.out.println("TempAtualizacaoBDDao.cargaContasOrcamentarias() - Commit da transacao...");
			
			
			tx.commit();
			//tx.rollback();
			
			
			
		} catch (Exception e) {
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
	 * Atualizar os itens PPA Apoio Administrativo (Mantis 7680):
	 * 1) Selecionar todos os itens do eCAR-SEPL com Nível de Planejamento = "PPA - Apoio Administrativo" e setar a Situação do Item = "Sem acompanhamento";
	 * 2) Selecionar todos os itens do eCAR-SEPL com Nível de Planejamento = "PPA - Apoio Administrativo" e setar o Nivel de Planejamento = "PPA";
	 * 
     * @param request
     * @throws ECARException
	 */
	public void atualizarItensPPAApoioAdministrativo(HttpServletRequest request) throws ECARException {
		Transaction tx = null;

		try {
			System.out.println("atualizarItensPPAApoioAdministrativo() - Iniciando transacao...");
			tx = session.beginTransaction();

			// situação "Sem acompanhamento": cod_sit=17
			StringBuilder query = new StringBuilder("select situacao from SituacaoSit as situacao where situacao.codSit=17");
			List listaSituacao = this.getSession().createQuery(query.toString()).list();
			
			SituacaoSit situacaoSit = (SituacaoSit)listaSituacao.get(0);
			
			// Nível de Planejamento PPA: codSatb = 35 
			query = new StringBuilder("select satb from SisAtributoSatb as satb");	        		
			query.append(" where ");
			query.append("satb.codSatb=35");
        	
			List listaSatbs = this.getSession().createQuery(query.toString()).list();

			SisAtributoSatb satbPPA = (SisAtributoSatb)listaSatbs.get(0);

			query = new StringBuilder("select item from ItemEstruturaIett as item");	        		
			query.append(" where ");
			query.append("item.itemEstruturaNivelIettns.codSatb=39"); // 39 = PPA - Apoio Administrativo
        	
			List itens = this.getSession().createQuery(query.toString()).list();

			Iterator itItens = itens.iterator();
			while(itItens.hasNext()) {
				ItemEstruturaIett iett = (ItemEstruturaIett) itItens.next();
				
				iett.setSituacaoSit(situacaoSit);
				
				Set novosNiveis = new HashSet();
				Iterator itNiveis = iett.getItemEstruturaNivelIettns().iterator();
				while(itNiveis.hasNext()) {
					SisAtributoSatb satb = (SisAtributoSatb)itNiveis.next();
					if(satb.getCodSatb() != 39 && satb.getCodSatb() != 35) { // diferente de "PPA - Apoio Administrativo" e de "PPA"
						novosNiveis.add(satb);
					}
					novosNiveis.add(satbPPA); // adiciona o nível PPA
				}
		        iett.setItemEstruturaNivelIettns(novosNiveis);

		        System.out.println("item: " + iett.getNomeIett());
		        session.update(iett);
			}
			
			System.out.println("atualizarItensPPAApoioAdministrativo() - Commit da transacao...");
			tx.commit();
			
		} catch (Exception e) {
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
	 * Solicitação da Rosangela (Sepl)
	 * 
         * @param request
	 * @throws ECARException
	 */
	public void listarItensPPALoa(HttpServletRequest request) throws ECARException {
		try {
			StringBuilder query = new StringBuilder("select item from ItemEstruturaIett as item");	        		
			query.append(" where ");
			query.append("(item.nivelIett=3 OR item.nivelIett=4)"); // Ação ou Produto
			query.append("AND item.itemEstruturaNivelIettns.codSatb=35"); // 35 = PPA
        	
			List itens = this.getSession().createQuery(query.toString()).list();

			Iterator itItens = itens.iterator();
			while(itItens.hasNext()) {
				ItemEstruturaIett iett = (ItemEstruturaIett) itItens.next();
				
				if(iett.getSituacaoSit() != null && (iett.getSituacaoSit().getCodSit().longValue() == 3 //concluído
						|| iett.getSituacaoSit().getCodSit().longValue() == 13 //excluído do PPA
						|| iett.getSituacaoSit().getCodSit().longValue() == 15 //Transferido
						|| iett.getSituacaoSit().getCodSit().longValue() == 5 //cancelado
						)) {
					itItens.remove();
				}
			}
			itItens = itens.iterator();
			while(itItens.hasNext()) {
				ItemEstruturaIett iett = (ItemEstruturaIett) itItens.next();
				
				StringBuilder s = new StringBuilder();
				
				if(iett.getNivelIett().intValue() == 3) { //Ação
					s.append(iett.getItemEstruturaIett().getSiglaIett()); //Sigla do Programa
					s.append(";" + iett.getSiglaIett() + ";" + iett.getNomeIett());
					s.append("; ;"); //Sigla/Nome do Produto
				} else if(iett.getNivelIett().intValue() == 4) { //Produto
					s.append(iett.getItemEstruturaIett().getItemEstruturaIett().getSiglaIett()); //Sigla do Programa
					s.append(";" + iett.getItemEstruturaIett().getSiglaIett() + ";" + iett.getItemEstruturaIett().getNomeIett()); //Sigla/Nome da Ação
					s.append(";" + iett.getSiglaIett() + ";" + iett.getNomeIett()); //Sigla/Nome do Produto
				}
				s.append(";");
				String orgao = " ";
				if(iett.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null) {
					orgao = iett.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg() + "(" + iett.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg() + ")";  
				}
				s.append(orgao + ";");
	            if(iett.getItemEstrutLocalIettls() != null && !iett.getItemEstrutLocalIettls().isEmpty()){
	                Iterator it = iett.getItemEstrutLocalIettls().iterator();
	                boolean primeiro = true;
	                while(it.hasNext()){
	                    ItemEstrutLocalIettl local = (ItemEstrutLocalIettl) it.next();                   
	                    
	                    if(primeiro) {
	                    	s.append(local.getLocalItemLit().getLocalGrupoLgp().getIdentificacaoLgp() + ":");
	        				s.append(";");
	                    	primeiro = false;
	                    }
						s.append(local.getLocalItemLit().getIdentificacaoLit() + ",");
	                }
	                
	                if(s.toString().endsWith(",")) {
	                	s.deleteCharAt(s.length() - 1);
	                }
	            } else {
					s.append(";");
	            }
				s.append(";");

	            if(iett.getItemEstrtIndResulIettrs() != null && !iett.getItemEstrtIndResulIettrs().isEmpty()){
	                Iterator it = iett.getItemEstrtIndResulIettrs().iterator();
	                while(it.hasNext()){
	                	ItemEstrtIndResulIettr iettr = (ItemEstrtIndResulIettr) it.next();
						s.append(iettr.getNomeIettir() + ";" + iettr.getUnidMedidaIettr());
						
	                    Iterator it2 = iettr.getItemEstrutFisicoIettfs().iterator();
	                    while(it2.hasNext()){
	                        ItemEstrutFisicoIettf iettf = (ItemEstrutFisicoIettf) it2.next();
	                        //Mantis 0010128 - Qtd prevista não é mais informado por exercício
	                        /*
	                        if("2007".equals(iettf.getExercicioExe().getDescricaoExe().trim())) {
	                        	s.append(";" + Pagina.trocaNullNumeroSemDecimal(iettf.getQtdPrevistaIettf().toString()));
	                        	break;
	                        }
	                        */
	                    }
	                }
	            } else {
					s.append(";");
	            }
				s.append(";");
				//s = new StringBuilder(s.toString().replaceAll(",", " "));
				System.out.println(s.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
	}

	/**
	 * 
	 * Solicitação da Rosangela (Sepl) - DADOS DE REVISÃO
	 * 
         * @param request
         * @throws ECARException
	 */
	public void listarItensPPALoaREVISAO(HttpServletRequest request) throws ECARException {
		try {
			StringBuilder query = new StringBuilder("select item from ItemEstruturaIett as item");	        		
			query.append(" where ");
			query.append("(item.nivelIett=3 OR item.nivelIett=4)"); // Ação ou Produto
			query.append("AND item.itemEstruturaNivelIettns.codSatb=35"); // 35 = PPA
        	
			List itens = this.getSession().createQuery(query.toString()).list();

			Iterator itItens = itens.iterator();
			while(itItens.hasNext()) {
				ItemEstruturaIett iett = (ItemEstruturaIett) itItens.next();
				
				if(iett.getSituacaoSit() != null && (iett.getSituacaoSit().getCodSit().longValue() == 3 //concluído
						|| iett.getSituacaoSit().getCodSit().longValue() == 13 //excluído do PPA
						|| iett.getSituacaoSit().getCodSit().longValue() == 15 //Transferido
						|| iett.getSituacaoSit().getCodSit().longValue() == 5 //cancelado
						)) {
					itItens.remove();
				}
			}
			itItens = itens.iterator();
			List itensBean = new ArrayList();
			while(itItens.hasNext()) {
				ItemEstruturaIett iett = (ItemEstruturaIett) itItens.next();
				ItemEstruturarevisaoIettrev ultRev = this.getUltimaRevisaoIett(iett.getItemEstruturarevisaoIettrevs());
				
				ItemPPALoaBean iplb = new ItemPPALoaBean();
				
				if(iett.getNivelIett().intValue() == 3) { //Ação
					iplb.setSiglaPrograma(iett.getItemEstruturaIett().getSiglaIett());
					
					if(ultRev != null && !"".equals(ultRev.getSiglaIettrev()))
						iplb.setSiglaAcao(ultRev.getSiglaIettrev());
					else
						iplb.setSiglaAcao(iett.getSiglaIett());
					
					if(ultRev != null && !"".equals(ultRev.getNomeIettrev()))
						iplb.setNomeAcao(ultRev.getNomeIettrev());
					else
						iplb.setNomeAcao(iett.getNomeIett());
					
				} else if(iett.getNivelIett().intValue() == 4) { //Produto
					iplb.setSiglaPrograma(iett.getItemEstruturaIett().getItemEstruturaIett().getSiglaIett());
					iplb.setSiglaAcao(iett.getItemEstruturaIett().getSiglaIett());
					iplb.setNomeAcao(iett.getItemEstruturaIett().getNomeIett());

					if(ultRev != null && !"".equals(ultRev.getSiglaIettrev()))
						iplb.setSiglaProduto(ultRev.getSiglaIettrev());
					else
						iplb.setSiglaProduto(iett.getSiglaIett());
					
					if(ultRev != null && !"".equals(ultRev.getNomeIettrev()))
						iplb.setNomeProduto(ultRev.getNomeIettrev());
					else
						iplb.setNomeProduto(iett.getNomeIett());
				}
				
				if(ultRev != null && ultRev.getOrgaoOrgByCodOrgaoResponsavel2Iettrev() != null){
					iplb.setSiglaOrgao(ultRev.getOrgaoOrgByCodOrgaoResponsavel2Iettrev().getSiglaOrg());
					iplb.setNomeOrgao(ultRev.getOrgaoOrgByCodOrgaoResponsavel2Iettrev().getDescricaoOrg());
				}
				else if(iett.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null) {
					iplb.setSiglaOrgao(iett.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg());
					iplb.setNomeOrgao(iett.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg());
				}
				
				if(ultRev != null && ultRev.getItemEstLocalRevIettlrs() != null && !ultRev.getItemEstLocalRevIettlrs().isEmpty()){
					String abrangencia = "";
					String localizacao = "";
					boolean primeiro = true;
					int i = 0;
					int ultPos = ultRev.getItemEstLocalRevIettlrs().size() - 1;
					for(Iterator it = ultRev.getItemEstLocalRevIettlrs().iterator(); it.hasNext();){
						ItemEstLocalRevIettlr local = (ItemEstLocalRevIettlr) it.next();
						if(primeiro){
							abrangencia = local.getLocalItemLit().getLocalGrupoLgp().getIdentificacaoLgp() + ":";
							primeiro = false;
						}
						localizacao += local.getLocalItemLit().getIdentificacaoLit();
						if(i < ultPos){
							localizacao += ",";
						}
						i++;
					}
					
					iplb.setAbrangencia(abrangencia);
					iplb.setLocal(localizacao);
					
				}
				else if(iett.getItemEstrutLocalIettls() != null && !iett.getItemEstrutLocalIettls().isEmpty()){
					String abrangencia = "";
					String localizacao = "";
					boolean primeiro = true;
					int i = 0;
					int ultPos = iett.getItemEstrutLocalIettls().size() - 1;
					for(Iterator it = iett.getItemEstrutLocalIettls().iterator(); it.hasNext();){
						ItemEstrutLocalIettl local = (ItemEstrutLocalIettl) it.next();
						if(primeiro){
							abrangencia = local.getLocalItemLit().getLocalGrupoLgp().getIdentificacaoLgp() + ":";
							primeiro = false;
						}
						localizacao += local.getLocalItemLit().getIdentificacaoLit();
						if(i < ultPos){
							localizacao += ",";
						}
						i++;
					}
					
					iplb.setAbrangencia(abrangencia);
					iplb.setLocal(localizacao);
				}

				String nomeMetaF = "";
				String unidMetaF = "";
				String valor2007 = "";
				List indicadores = new ArrayList(iett.getItemEstrtIndResulIettrs());
				List indJaEstaoRevisao = new ArrayList();
				
				if(ultRev != null && ultRev.getIettIndResulRevIettrrs() != null && !ultRev.getIettIndResulRevIettrrs().isEmpty()){
					for(Iterator it = ultRev.getIettIndResulRevIettrrs().iterator(); it.hasNext();){
						IettIndResulRevIettrr iettrr = (IettIndResulRevIettrr) it.next();
						
						if(indicadores.contains(iettrr.getItemEstrtIndResulIettr())){
							
							if(iettrr.getItemEstFisicoRevIettfrs() != null){
								for(Iterator it2 = iettrr.getItemEstFisicoRevIettfrs().iterator(); it2.hasNext();){
									ItemEstFisicoRevIettfr iettfr = (ItemEstFisicoRevIettfr) it2.next();
									if("2007".equals(iettfr.getExercicioExe().getDescricaoExe().trim())){
										indJaEstaoRevisao.add(iettrr.getItemEstrtIndResulIettr());
										
										if(iettrr.getItemEstrtIndResulIettr() != null){
											nomeMetaF += iettrr.getItemEstrtIndResulIettr().getNomeIettir() + ",";
											unidMetaF += iettrr.getItemEstrtIndResulIettr().getUnidMedidaIettr() + ",";
										}
										valor2007 += Pagina.trocaNullNumeroSemDecimal(iettfr.getQtdPrevistaIettfr()) + ",";
										break;
									}
								}
							}
						}
					}
				}
				
				if(iett.getItemEstrtIndResulIettrs() != null && !iett.getItemEstrtIndResulIettrs().isEmpty()){
					for(Iterator it = iett.getItemEstrtIndResulIettrs().iterator(); it.hasNext();){
						ItemEstrtIndResulIettr iettr = (ItemEstrtIndResulIettr) it.next();
						if(!indJaEstaoRevisao.contains(iettr)){
							nomeMetaF += iettr.getNomeIettir() + ",";
							unidMetaF += iettr.getUnidMedidaIettr() + ",";
							
							if(iettr.getItemEstrutFisicoIettfs() != null && !iettr.getItemEstrutFisicoIettfs().isEmpty()){
								for(Iterator it2 = iettr.getItemEstrutFisicoIettfs().iterator(); it2.hasNext();){
									ItemEstrutFisicoIettf iettf = (ItemEstrutFisicoIettf) it2.next();
									//Mantis 0010128 - Qtd prevista não é mais informado por exercício
									/*
									if("2007".equals(iettf.getExercicioExe().getDescricaoExe().trim())){
										valor2007 += Pagina.trocaNullNumeroSemDecimal(iettf.getQtdPrevistaIettf()) + ",";
										break;
									}
									*/
								}
							}
						}
					}
				}
				
				if(nomeMetaF.endsWith(",")){
					int ultPos = nomeMetaF.length() - 1;
					nomeMetaF = nomeMetaF.substring(0, ultPos);
				}
				if(unidMetaF.endsWith(",")){
					int ultPos = unidMetaF.length() - 1;
					unidMetaF = unidMetaF.substring(0, ultPos);
				}
				if(valor2007.endsWith(",")){
					int ultPos = valor2007.length() - 1;
					valor2007 = valor2007.substring(0, ultPos);
				}
				iplb.setTipoMetaFisica(nomeMetaF);
				iplb.setUnidadeMetaFisica(unidMetaF);
				iplb.setQtde2007(valor2007);
				
				itensBean.add(iplb);
			}
			
			Collections.sort(itensBean, new Comparator(){

				public int compare(Object arg0, Object arg1) {
					ItemPPALoaBean i1 = (ItemPPALoaBean) arg0;
					ItemPPALoaBean i2 = (ItemPPALoaBean) arg1;

					return i1.getSiglaOrgao().compareTo(i2.getSiglaOrgao());
				}
				
			});
			
			for(Iterator it = itensBean.iterator(); it.hasNext();){
				ItemPPALoaBean i = (ItemPPALoaBean) it.next();
				System.out.println(i.gerarSaidaCSV());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
	}
	
	/**
	 * Reorna a última revisão (não "Exclusão") de uma lista de revisões de um item...
	 * @param listaRevisoes
	 * @return
	 */
    private ItemEstruturarevisaoIettrev getUltimaRevisaoIett(Set listaRevisoes){
    	ItemEstruturarevisaoIettrev retorno = null;
    	if(listaRevisoes != null && listaRevisoes.size() > 0){
    		List revisoes = new ArrayList(listaRevisoes);
    		
    		for(Iterator it = revisoes.iterator(); it.hasNext();){
    			ItemEstruturarevisaoIettrev rev = (ItemEstruturarevisaoIettrev) it.next();
    			
    			if("E".equals(rev.getSituacaoIettrev())){
    				it.remove();
    			}
    		}
    		
    		if(revisoes != null && !revisoes.isEmpty()){
    		
	    		Collections.sort(revisoes,
	    			new Comparator(){
						public int compare(Object o1, Object o2) {
							ItemEstruturarevisaoIettrev iett1 = (ItemEstruturarevisaoIettrev) o1;
							ItemEstruturarevisaoIettrev iett2 = (ItemEstruturarevisaoIettrev) o2;
							
							return iett1.getDataInclusaoIettrev().compareTo(iett2.getDataInclusaoIettrev());
						}	
	    			}
	    		);
	    		
	    		int ultimaRev = (revisoes.size() > 1) ? revisoes.size() - 1 : 0;
	    		
	    		ItemEstruturarevisaoIettrev ultimoIettRev = (ItemEstruturarevisaoIettrev) revisoes.get(ultimaRev);
	    		retorno = ultimoIettRev;
    		}
    	}
    	
    	return retorno;
    }

	
	/**
	 * 
	 * Solicitação da SEPL - carga de realizado físico por local (Leite das Criancas), através de arquivo (2006 e 2007)
	 * 
         * @param request
	 * @throws ECARException
	 */
	public void efetuarCargaArquivoRealizadoFisicoPorLocalLeiteDasCriancas2006e2007(HttpServletRequest request) throws ECARException {
		Transaction tx = null;
		try {
			BufferedReader in = new BufferedReader (new FileReader("/home/precoma/CriancasAtendidas.csv"));
			String linha = "";
			int registrosLidos = 0;
			List listTempBean = new ArrayList();
			
			while ((linha=in.readLine()) != null) {
				// ordem dos campos: cod_lit;Janeiro de 2006 até março de 2007;
				String[] campos = linha.split(";");
				if(campos != null) {
					// Janeiro/2006
					TempBean tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("1"));
					tempBean.setAno(Integer.valueOf("2006"));
					tempBean.setQtde(Double.valueOf(campos[1]));
					listTempBean.add(tempBean);
					// Fevereiro/2006
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("2"));
					tempBean.setAno(Integer.valueOf("2006"));
					tempBean.setQtde(Double.valueOf(campos[2]));
					listTempBean.add(tempBean);
					// Março/2006
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("3"));
					tempBean.setAno(Integer.valueOf("2006"));
					tempBean.setQtde(Double.valueOf(campos[3]));
					listTempBean.add(tempBean);
					// Abril/2006
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("4"));
					tempBean.setAno(Integer.valueOf("2006"));
					tempBean.setQtde(Double.valueOf(campos[4]));
					listTempBean.add(tempBean);
					// Maio/2006
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("5"));
					tempBean.setAno(Integer.valueOf("2006"));
					tempBean.setQtde(Double.valueOf(campos[5]));
					listTempBean.add(tempBean);
					// Junho/2006
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("6"));
					tempBean.setAno(Integer.valueOf("2006"));
					tempBean.setQtde(Double.valueOf(campos[6]));
					listTempBean.add(tempBean);
					// Julho/2006
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("7"));
					tempBean.setAno(Integer.valueOf("2006"));
					tempBean.setQtde(Double.valueOf(campos[7]));
					listTempBean.add(tempBean);
					// Agosto/2006
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("8"));
					tempBean.setAno(Integer.valueOf("2006"));
					tempBean.setQtde(Double.valueOf(campos[8]));
					listTempBean.add(tempBean);
					// Setembro/2006
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("9"));
					tempBean.setAno(Integer.valueOf("2006"));
					tempBean.setQtde(Double.valueOf(campos[9]));
					listTempBean.add(tempBean);
					// Outubro/2006
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("10"));
					tempBean.setAno(Integer.valueOf("2006"));
					tempBean.setQtde(Double.valueOf(campos[10]));
					listTempBean.add(tempBean);
					// Novembro/2006
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("11"));
					tempBean.setAno(Integer.valueOf("2006"));
					tempBean.setQtde(Double.valueOf(campos[11]));
					listTempBean.add(tempBean);
					// Dezembro/2006
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("12"));
					tempBean.setAno(Integer.valueOf("2006"));
					tempBean.setQtde(Double.valueOf(campos[12]));
					listTempBean.add(tempBean);
					// Janeiro/2007
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("1"));
					tempBean.setAno(Integer.valueOf("2007"));
					tempBean.setQtde(Double.valueOf(campos[13]));
					listTempBean.add(tempBean);
					// Fevereiro/2007
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("2"));
					tempBean.setAno(Integer.valueOf("2007"));
					tempBean.setQtde(Double.valueOf(campos[14]));
					listTempBean.add(tempBean);
					// Março/2007
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("3"));
					tempBean.setAno(Integer.valueOf("2007"));
					tempBean.setQtde(Double.valueOf(campos[15]));
					listTempBean.add(tempBean);
				}
				registrosLidos++;
			}
			
			System.out.println("registrosLidos: " + registrosLidos);

			System.out.println("Iniciando transacao...");
			tx = session.beginTransaction();
			Dao dao = new Dao();
			AcompRealFisicoDao arfDao = new AcompRealFisicoDao(null);
			Iterator it = listTempBean.iterator();
			while(it.hasNext()) {
				TempBean bean = (TempBean)it.next();
				LocalItemLit lit = (LocalItemLit)dao.buscar(LocalItemLit.class, bean.getCodLit());
				AcompRealFisicoArf arf = arfDao.buscarPorIettir(Long.valueOf(bean.getMes()), Long.valueOf(bean.getAno()), Long.valueOf("1186"));
				
				if(lit == null || arf == null) {
					throw new Exception("lit == null || arf == null");
				}
				
				AcompRealFisicoLocalArfl arfl = new AcompRealFisicoLocalArfl();
				arfl.setLocalItemLit(lit);
				arfl.setAcompRealFisicoArf(arf);
				arfl.setQuantidadeArfl(bean.getQtde());
				session.save(arfl);
			}
			
			System.out.println("Commit...");
			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException(r); 
				}
			this.logger.error(e);
			throw new ECARException(e); 
		}
	}

	/**
	 * 
	 * Solicitação da SEPL - carga de realizado físico por local (Tarifa Social Homero Oguido), através de arquivo
	 * 
         * @param request
         * @throws ECARException
	 */
	public void efetuarCargaArquivoRealizadoFisicoPorLocalTarifaSocial(HttpServletRequest request) throws ECARException {
		Transaction tx = null;
		try {
			BufferedReader in = new BufferedReader (new FileReader("/home/precoma/Dados_Tarifa_Social.csv"));
			String linha = "";
			int registrosLidos = 0;
			List listTempBean = new ArrayList();
			String codCidadeManfrinopolis = "201";
			
			while ((linha=in.readLine()) != null) {
				// ordem dos campos: cod_lit;Janeiro de 2006 até Fevereiro de 2007;
				String[] campos = linha.split(";");
				if(campos != null) {
					TempBean tempBean = new TempBean();
					// Janeiro/2006
					if(!codCidadeManfrinopolis.equals(campos[0])) {
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("1"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[1]));
						listTempBean.add(tempBean);
					}
					// Fevereiro/2006
					if(!codCidadeManfrinopolis.equals(campos[0])) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("2"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[2]));
						listTempBean.add(tempBean);
					}
					// Março/2006
					if(!codCidadeManfrinopolis.equals(campos[0])) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("3"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[3]));
						listTempBean.add(tempBean);
					}
					// Abril/2006
					if(!codCidadeManfrinopolis.equals(campos[0])) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("4"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[4]));
						listTempBean.add(tempBean);
					}
					// Maio/2006
					if(!codCidadeManfrinopolis.equals(campos[0])) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("5"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[5]));
						listTempBean.add(tempBean);
					}
					// Junho/2006
					if(!codCidadeManfrinopolis.equals(campos[0])) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("6"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[6]));
						listTempBean.add(tempBean);
					}
					// Julho/2006
					if(!codCidadeManfrinopolis.equals(campos[0])) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("7"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[7]));
						listTempBean.add(tempBean);
					}
					// Agosto/2006
					if(!codCidadeManfrinopolis.equals(campos[0])) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("8"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[8]));
						listTempBean.add(tempBean);
					}
					// Setembro/2006
					if(!codCidadeManfrinopolis.equals(campos[0])) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("9"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[9]));
						listTempBean.add(tempBean);
					}
					// Outubro/2006
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("10"));
					tempBean.setAno(Integer.valueOf("2006"));
					tempBean.setQtde(Double.valueOf(campos[10]));
					listTempBean.add(tempBean);
					// Novembro/2006
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("11"));
					tempBean.setAno(Integer.valueOf("2006"));
					tempBean.setQtde(Double.valueOf(campos[11]));
					listTempBean.add(tempBean);
					// Dezembro/2006
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("12"));
					tempBean.setAno(Integer.valueOf("2006"));
					tempBean.setQtde(Double.valueOf(campos[12]));
					listTempBean.add(tempBean);
					// Janeiro/2007
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("1"));
					tempBean.setAno(Integer.valueOf("2007"));
					tempBean.setQtde(Double.valueOf(campos[13]));
					listTempBean.add(tempBean);
					// Fevereiro/2007
					tempBean = new TempBean();
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("2"));
					tempBean.setAno(Integer.valueOf("2007"));
					tempBean.setQtde(Double.valueOf(campos[14]));
					listTempBean.add(tempBean);
				}
				registrosLidos++;
			}
			
			System.out.println("registrosLidos: " + registrosLidos);

			System.out.println("Iniciando transacao...");
			tx = session.beginTransaction();
			Dao dao = new Dao();
			AcompRealFisicoDao arfDao = new AcompRealFisicoDao(null);
			Iterator it = listTempBean.iterator();
			while(it.hasNext()) {
				TempBean bean = (TempBean)it.next();
				LocalItemLit lit = (LocalItemLit)dao.buscar(LocalItemLit.class, bean.getCodLit());
				AcompRealFisicoArf arf = arfDao.buscarPorIettir(Long.valueOf(bean.getMes()), Long.valueOf(bean.getAno()), Long.valueOf("1187"));
				
				if(lit == null || arf == null) {
					throw new Exception("lit == null || arf == null");
				}
				
				AcompRealFisicoLocalArfl arfl = new AcompRealFisicoLocalArfl();
				arfl.setLocalItemLit(lit);
				arfl.setAcompRealFisicoArf(arf);
				arfl.setQuantidadeArfl(bean.getQtde());
				session.save(arfl);
			}
			
			//int i = Integer.parseInt("asdasd.as;;");
			
			System.out.println("Commit...");
			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException(r); 
				}
			this.logger.error(e);
			throw new ECARException(e); 
		}
	}

	/**
	 * 
	 * Solicitação da SEPL - carga de realizado físico por local (Leite das Criancas), através de arquivo (2004)
	 * 
         * @param request
         * @throws ECARException
	 */
	public void efetuarCargaArquivoRealizadoFisicoPorLocalLeiteDasCriancas2004(HttpServletRequest request) throws ECARException {
		Transaction tx = null;
		try {
			BufferedReader in = new BufferedReader (new FileReader("/home/precoma/LeiteDasCriancas2004_Dados.csv"));
			String linha = "";
			int registrosLidos = 0;
			List listTempBean = new ArrayList();
			
			while ((linha=in.readLine()) != null) {
				// ordem dos campos: cod_lit;Janeiro de 2004 até Dezembro de 2004;
				String[] campos = linha.split(";");
				if(campos != null && campos.length > 1) {
					TempBean tempBean;
					// Janeiro/2004
					if(campos[1] != null && !"".equals(campos[1].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("1"));
						tempBean.setAno(Integer.valueOf("2004"));
						tempBean.setQtde(Double.valueOf(campos[1]));
						listTempBean.add(tempBean);
					}
					// Fevereiro/2004
					if(campos[2] != null && !"".equals(campos[2].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("2"));
						tempBean.setAno(Integer.valueOf("2004"));
						tempBean.setQtde(Double.valueOf(campos[2]));
						listTempBean.add(tempBean);
					}
					// Março/2004
					if(campos[3] != null && !"".equals(campos[3].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("3"));
						tempBean.setAno(Integer.valueOf("2004"));
						tempBean.setQtde(Double.valueOf(campos[3]));
						listTempBean.add(tempBean);
					}
					// Abril/2004
					if(campos[4] != null && !"".equals(campos[4].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("4"));
						tempBean.setAno(Integer.valueOf("2004"));
						tempBean.setQtde(Double.valueOf(campos[4]));
						listTempBean.add(tempBean);
					}
					// Maio/2004
					if(campos[5] != null && !"".equals(campos[5].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("5"));
						tempBean.setAno(Integer.valueOf("2004"));
						tempBean.setQtde(Double.valueOf(campos[5]));
						listTempBean.add(tempBean);
					}
					// Junho/2004
					if(campos[6] != null && !"".equals(campos[6].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("6"));
						tempBean.setAno(Integer.valueOf("2004"));
						tempBean.setQtde(Double.valueOf(campos[6]));
						listTempBean.add(tempBean);
					}
					// Julho/2004
					if(campos[7] != null && !"".equals(campos[7].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("7"));
						tempBean.setAno(Integer.valueOf("2004"));
						tempBean.setQtde(Double.valueOf(campos[7]));
						listTempBean.add(tempBean);
					}
					// Agosto/2004
					if(campos[8] != null && !"".equals(campos[8].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("8"));
						tempBean.setAno(Integer.valueOf("2004"));
						tempBean.setQtde(Double.valueOf(campos[8]));
						listTempBean.add(tempBean);
					}
					// Setembro/2004
					if(campos[9] != null && !"".equals(campos[9].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("9"));
						tempBean.setAno(Integer.valueOf("2004"));
						tempBean.setQtde(Double.valueOf(campos[9]));
						listTempBean.add(tempBean);
					}
					// Outubro/2004
					if(campos[10] != null && !"".equals(campos[10].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("10"));
						tempBean.setAno(Integer.valueOf("2004"));
						tempBean.setQtde(Double.valueOf(campos[10]));
						listTempBean.add(tempBean);
					}
					// Novembro/2004
					if(campos[11] != null && !"".equals(campos[11].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("11"));
						tempBean.setAno(Integer.valueOf("2004"));
						tempBean.setQtde(Double.valueOf(campos[11]));
						listTempBean.add(tempBean);
					}
					// Dezembro/2004
					if(campos[12] != null && !"".equals(campos[12].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("12"));
						tempBean.setAno(Integer.valueOf("2004"));
						tempBean.setQtde(Double.valueOf(campos[12]));
						listTempBean.add(tempBean);
					}
				}
				registrosLidos++;
			}
			
			System.out.println("registrosLidos: " + registrosLidos);

			System.out.println("Iniciando transacao...");
			tx = session.beginTransaction();
			Dao dao = new Dao();
			AcompRealFisicoDao arfDao = new AcompRealFisicoDao(null);
			Iterator it = listTempBean.iterator();
			while(it.hasNext()) {
				TempBean bean = (TempBean)it.next();
				LocalItemLit lit = (LocalItemLit)dao.buscar(LocalItemLit.class, bean.getCodLit());
				AcompRealFisicoArf arf = arfDao.buscarPorIettir(Long.valueOf(bean.getMes()), Long.valueOf(bean.getAno()), Long.valueOf("1186"));
				
				if(lit == null || arf == null) {
					throw new Exception("lit == null || arf == null");
				}
				
				AcompRealFisicoLocalArfl arfl = new AcompRealFisicoLocalArfl();
				arfl.setLocalItemLit(lit);
				arfl.setAcompRealFisicoArf(arf);
				arfl.setQuantidadeArfl(bean.getQtde());
				session.save(arfl);
			}
			
			//int i = Integer.parseInt("asdasd,adsd.as");
			
			System.out.println("Commit...");
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException(r); 
				}
			this.logger.error(e);
			throw new ECARException(e); 
		}
	}

	
	/**List listaIettrs = iettrs.
	 * 
	 * Solicitação da SEPL - carga de realizado físico por local (Leite das Criancas), através de arquivo (2005)
	 * 
         * @param request
	 * @throws ECARException
	 */
	public void efetuarCargaArquivoRealizadoFisicoPorLocalLeiteDasCriancas2005(HttpServletRequest request) throws ECARException {
		Transaction tx = null;
		try {
			BufferedReader in = new BufferedReader (new FileReader("/home/precoma/LeiteDasCriancas2005_Dados.csv"));
			String linha = "";
			int registrosLidos = 0;
			List listTempBean = new ArrayList();
			
			while ((linha=in.readLine()) != null) {
				// ordem dos campos: cod_lit;Janeiro de 2005 até Dezembro de 2005;
				String[] campos = linha.split(";");
				if(campos != null && campos.length > 1) {
					TempBean tempBean;
					// Janeiro/2005
					if(campos[1] != null && !"".equals(campos[1].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("1"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[1]));
						listTempBean.add(tempBean);
					}
					// Fevereiro/2005
					if(campos[2] != null && !"".equals(campos[2].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("2"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[2]));
						listTempBean.add(tempBean);
					}
					// Março/2005
					if(campos[3] != null && !"".equals(campos[3].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("3"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[3]));
						listTempBean.add(tempBean);
					}
					// Abril/2005
					if(campos[4] != null && !"".equals(campos[4].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("4"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[4]));
						listTempBean.add(tempBean);
					}
					// Maio/2005
					if(campos[5] != null && !"".equals(campos[5].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("5"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[5]));
						listTempBean.add(tempBean);
					}
					// Junho/2005
					if(campos[6] != null && !"".equals(campos[6].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("6"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[6]));
						listTempBean.add(tempBean);
					}
					// Julho/2005
					if(campos[7] != null && !"".equals(campos[7].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("7"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[7]));
						listTempBean.add(tempBean);
					}
					// Agosto/2005
					if(campos[8] != null && !"".equals(campos[8].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("8"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[8]));
						listTempBean.add(tempBean);
					}
					// Setembro/2005
					if(campos[9] != null && !"".equals(campos[9].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("9"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[9]));
						listTempBean.add(tempBean);
					}
					// Outubro/2005
					if(campos[10] != null && !"".equals(campos[10].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("10"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[10]));
						listTempBean.add(tempBean);
					}
					// Novembro/2005
					if(campos[11] != null && !"".equals(campos[11].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("11"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[11]));
						listTempBean.add(tempBean);
					}
					// Dezembro/2005
					if(campos[12] != null && !"".equals(campos[12].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("12"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[12]));
						listTempBean.add(tempBean);
					}
				}
				registrosLidos++;
			}
			
			System.out.println("registrosLidos: " + registrosLidos);

			System.out.println("Iniciando transacao...");
			tx = session.beginTransaction();
			Dao dao = new Dao();
			AcompRealFisicoDao arfDao = new AcompRealFisicoDao(null);
			Iterator it = listTempBean.iterator();
			while(it.hasNext()) {
				TempBean bean = (TempBean)it.next();
				LocalItemLit lit = (LocalItemLit)dao.buscar(LocalItemLit.class, bean.getCodLit());
				AcompRealFisicoArf arf = arfDao.buscarPorIettir(Long.valueOf(bean.getMes()), Long.valueOf(bean.getAno()), Long.valueOf("1186"));
				
				if(lit == null || arf == null) {
					throw new Exception("lit == null || arf == null");
				}
				
				AcompRealFisicoLocalArfl arfl = new AcompRealFisicoLocalArfl();
				arfl.setLocalItemLit(lit);
				arfl.setAcompRealFisicoArf(arf);
				arfl.setQuantidadeArfl(bean.getQtde());
				session.save(arfl);
			}
			
			//int i = Integer.parseInt("asdasd,adsd.as");
			
			System.out.println("Commit...");
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException(r); 
				}
			this.logger.error(e);
			throw new ECARException(e); 
		}
	}
	
	/**
	 * 
	 * Solicitação da SEPL - cópia dos pareceres para o acompanhamento Piloto (Mantis: 9227)
	 * 
         * @param request
         * @throws ECARException
	 */
	public void copiarPareceresParaAcompPiloto(HttpServletRequest request) throws ECARException {
		Transaction tx = null;
		try {
			System.out.println("Iniciando transacao...");
			tx = session.beginTransaction();
			AcompReferenciaDao arefDao = new AcompReferenciaDao(null);
			List listAref = arefDao.getListAcompReferencia();
			List listArefEmMonitoramento = new ArrayList();
			List listArefPiloto = new ArrayList();
			int anoMesInicial = 200512;
			int anoMesFinal = 200702;
			StringBuilder arelsAlterados = new StringBuilder("");
			
			if(listAref != null && !listAref.isEmpty()) {
				Iterator it = listAref.iterator();
				while(it.hasNext()) {
					AcompReferenciaAref aref = (AcompReferenciaAref)it.next();
					if(Integer.parseInt(aref.getAnoAref() + aref.getMesAref()) >=  anoMesInicial
							&& Integer.parseInt(aref.getAnoAref() + aref.getMesAref()) <=  anoMesFinal) {
						if(aref.getTipoAcompanhamentoTa().getCodTa().longValue() == 1) { //Tipo "Em monitoramento"
							listArefEmMonitoramento.add(aref);
						} else  if(aref.getTipoAcompanhamentoTa().getCodTa().longValue() == 4) { //Tipo "Piloto"
							listArefPiloto.add(aref);
						}
					}
				}
			}

			Dao dao = new Dao();

			List listArel = new ArrayList();
			if(listArefEmMonitoramento != null && !listArefEmMonitoramento.isEmpty()) {
				Iterator it = listArefEmMonitoramento.iterator();
				while(it.hasNext()) {
					AcompReferenciaAref aref = (AcompReferenciaAref)it.next();
					
					aref = (AcompReferenciaAref)dao.buscar(AcompReferenciaAref.class, aref.getCodAref());
					
					// obter os aris/arel dos 5 itens
					Iterator it2 = aref.getAcompReferenciaItemAris().iterator();
					while(it2.hasNext()) {
						AcompReferenciaItemAri ari = (AcompReferenciaItemAri)it2.next();
						
						if(ari.getItemEstruturaIett().getCodIett().longValue() == 1754 // PR-323 - Maringá - Paiçandu (duplicação)
								|| ari.getItemEstruturaIett().getCodIett().longValue() == 141 // Leite das Crianças
								|| ari.getItemEstruturaIett().getCodIett().longValue() == 1741 // Hospital Regional do Litoral (antiga Santa Casa de Paranaguá)
								|| ari.getItemEstruturaIett().getCodIett().longValue() == 185 // Tarifa Social Homero Oguido
								|| ari.getItemEstruturaIett().getCodIett().longValue() == 1232 // Centro de Detenção e Ressocialização de Foz do Iguaçu
								) {
							
							//obter os arels
							Iterator it3 = ari.getAcompRelatorioArels().iterator();
							while(it3.hasNext()) {
								AcompRelatorioArel arel = (AcompRelatorioArel)it3.next();
								// somente copiar arel do SEPL ou Responsável
								if(arel.getTipoFuncAcompTpfa().getCodTpfa().longValue() == 1
										|| arel.getTipoFuncAcompTpfa().getCodTpfa().longValue() == 2) {
									listArel.add(arel);
								}
							}
						}
					}
				}
			}
			
			if(!listArel.isEmpty() && !listArefPiloto.isEmpty()) {
				Iterator it = listArefPiloto.iterator();
				while(it.hasNext()) {
					AcompReferenciaAref aref = (AcompReferenciaAref)it.next();
					
					aref = (AcompReferenciaAref)dao.buscar(AcompReferenciaAref.class, aref.getCodAref());
					
					// obter os aris/arel dos 5 itens
					Iterator it2 = aref.getAcompReferenciaItemAris().iterator();
					while(it2.hasNext()) {
						AcompReferenciaItemAri ari = (AcompReferenciaItemAri)it2.next();
						
						if(ari.getItemEstruturaIett().getCodIett().longValue() == 1754 // PR-323 - Maringá - Paiçandu (duplicação)
								|| ari.getItemEstruturaIett().getCodIett().longValue() == 141 // Leite das Crianças
								|| ari.getItemEstruturaIett().getCodIett().longValue() == 1741 // Hospital Regional do Litoral (antiga Santa Casa de Paranaguá)
								|| ari.getItemEstruturaIett().getCodIett().longValue() == 185 // Tarifa Social Homero Oguido
								|| ari.getItemEstruturaIett().getCodIett().longValue() == 1232 // Centro de Detenção e Ressocialização de Foz do Iguaçu
								) {
							
							//obter os arels
							Iterator it3 = ari.getAcompRelatorioArels().iterator();
							while(it3.hasNext()) {
								AcompRelatorioArel arel = (AcompRelatorioArel)it3.next();
								System.out.println("Arel analisado: " + arel.getCodArel());
								// somente copiar arel do SEPL ou Responsável
								if(arel.getTipoFuncAcompTpfa().getCodTpfa().longValue() == 1
										|| arel.getTipoFuncAcompTpfa().getCodTpfa().longValue() == 2) {
									// obter o parecer do acompanhamento monitorado
									Iterator it4 = listArel.iterator();
									while(it4.hasNext()) {
										AcompRelatorioArel arelAux = (AcompRelatorioArel)it4.next();
										if((arelAux.getAcompReferenciaItemAri().getItemEstruturaIett().getCodIett().longValue()
												== arel.getAcompReferenciaItemAri().getItemEstruturaIett().getCodIett().longValue())
											&& (Long.parseLong(arelAux.getAcompReferenciaItemAri().getAcompReferenciaAref().getMesAref())
												== Long.parseLong(arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getMesAref()))
											&& (Long.parseLong(arelAux.getAcompReferenciaItemAri().getAcompReferenciaAref().getAnoAref())
													== Long.parseLong(arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getAnoAref()))
											&& (arelAux.getTipoFuncAcompTpfa().getCodTpfa().longValue() == arel.getTipoFuncAcompTpfa().getCodTpfa().longValue())
											) {
											
											if(arel.getDescricaoArel() == null) {
												arel.setDescricaoArel(arelAux.getDescricaoArel());
											}
											if(arel.getSituacaoSit() == null) {
												arel.setSituacaoSit(arelAux.getSituacaoSit());
											}
											if(arel.getCor() == null) {
												arel.setCor(arelAux.getCor());
											}
											if(arel.getComplementoArel() == null) {
												arel.setComplementoArel(arelAux.getComplementoArel());
											}
											if(arel.getIndLiberadoArel() == null) {
												arel.setIndLiberadoArel(arelAux.getIndLiberadoArel());
											}
											if(arel.getUsuarioUsuUltimaManutencao() == null) {
												arel.setUsuarioUsuUltimaManutencao(arelAux.getUsuarioUsuUltimaManutencao());
											}
											if(arel.getDataUltManutArel() == null) {
												arel.setDataUltManutArel(arelAux.getDataUltManutArel());
											}
											arelsAlterados.append(arel.getCodArel() + ",");
											System.out.println("Alterando AREL: " + arel.getCodArel());
											session.update(arel);
										}
									}
								}
							}
						}
					}
				}
			}
			
			System.out.println("ARELs alterados: " + arelsAlterados.toString());
			
			//int i = Integer.parseInt("asdasd,adsd.as");
			
			System.out.println("Commit...");
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException(r); 
				}
			this.logger.error(e);
			throw new ECARException(e); 
		}
	}
	
	/**
	 * 
	 * Solicitação da SEPL - carga de realizado físico por local (Leite das Criancas), através de arquivo (2006)
	 * 
         * @param request
         * @throws ECARException
	 */
	public void efetuarCargaArquivoRealizadoFisicoPorLocalLeiteDasCriancas2006(HttpServletRequest request) throws ECARException {
		Transaction tx = null;
		try {
			BufferedReader in = new BufferedReader (new FileReader("/home/precoma/LeiteDasCriancas2006_Dados.csv"));
			String linha = "";
			int registrosLidos = 0;
			List listTempBean = new ArrayList();
			
			while ((linha=in.readLine()) != null) {
				// ordem dos campos: cod_lit;Janeiro de 2006 até Dezembro de 2006;
				String[] campos = linha.split(";");
				if(campos != null && campos.length > 1) {
					TempBean tempBean;
					// Janeiro/2006
					if(campos[1] != null && !"".equals(campos[1].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("1"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[1]));
						listTempBean.add(tempBean);
					}
					// Fevereiro/2006
					if(campos[2] != null && !"".equals(campos[2].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("2"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[2]));
						listTempBean.add(tempBean);
					}
					// Março/2006
					if(campos[3] != null && !"".equals(campos[3].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("3"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[3]));
						listTempBean.add(tempBean);
					}
					// Abril/2006
					if(campos[4] != null && !"".equals(campos[4].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("4"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[4]));
						listTempBean.add(tempBean);
					}
					// Maio/2006
					if(campos[5] != null && !"".equals(campos[5].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("5"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[5]));
						listTempBean.add(tempBean);
					}
					// Junho/2006
					if(campos[6] != null && !"".equals(campos[6].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("6"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[6]));
						listTempBean.add(tempBean);
					}
					// Julho/2006
					if(campos[7] != null && !"".equals(campos[7].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("7"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[7]));
						listTempBean.add(tempBean);
					}
					// Agosto/2006
					if(campos[8] != null && !"".equals(campos[8].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("8"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[8]));
						listTempBean.add(tempBean);
					}
					// Setembro/2006
					if(campos[9] != null && !"".equals(campos[9].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("9"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[9]));
						listTempBean.add(tempBean);
					}
					// Outubro/2006
					if(campos[10] != null && !"".equals(campos[10].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("10"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[10]));
						listTempBean.add(tempBean);
					}
					// Novembro/2006
					if(campos[11] != null && !"".equals(campos[11].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("11"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[11]));
						listTempBean.add(tempBean);
					}
					// Dezembro/2006
					if(campos[12] != null && !"".equals(campos[12].trim())) {
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("12"));
						tempBean.setAno(Integer.valueOf("2006"));
						tempBean.setQtde(Double.valueOf(campos[12]));
						listTempBean.add(tempBean);
					}
				}
				registrosLidos++;
			}
			
			System.out.println("registrosLidos: " + registrosLidos);

			System.out.println("Iniciando transacao...");
			tx = session.beginTransaction();
			Dao dao = new Dao();
			AcompRealFisicoDao arfDao = new AcompRealFisicoDao(null);
			Iterator it = listTempBean.iterator();
			while(it.hasNext()) {
				TempBean bean = (TempBean)it.next();
				LocalItemLit lit = (LocalItemLit)dao.buscar(LocalItemLit.class, bean.getCodLit());
				AcompRealFisicoArf arf = arfDao.buscarPorIettir(Long.valueOf(bean.getMes()), Long.valueOf(bean.getAno()), Long.valueOf("1189"));
				
				if(lit == null || arf == null) {
					throw new Exception("lit == null || arf == null");
				}
				
				AcompRealFisicoLocalArfl arfl = new AcompRealFisicoLocalArfl();
				arfl.setLocalItemLit(lit);
				arfl.setAcompRealFisicoArf(arf);
				arfl.setQuantidadeArfl(bean.getQtde());
				session.save(arfl);
			}
			
			//int i = Integer.parseInt("asdasd,adsd.as");
			
			System.out.println("Commit...");
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException(r); 
				}
			this.logger.error(e);
			throw new ECARException(e); 
		}
	}
	
	
	/**
	 * 
	 * Solicitação da DIGOV - mudar o item pai de alguns itens solicitados pela área do projeto "DIGOV - Sites DIGOV" (Mantis: 9471)
	 * 
         * @param request
         * @throws ECARException
	 */
	public void mudarPaiItens(HttpServletRequest request) throws ECARException {
		Transaction tx = null;
		try {
			List listItens = new ArrayList();
			
			MudarPaiItensTempBean bean = new MudarPaiItensTempBean(Long.valueOf("1533"), Long.valueOf("521"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1523"), Long.valueOf("1593"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1186"), Long.valueOf("521"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1520"), Long.valueOf("521"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1532"), Long.valueOf("521"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1322"), Long.valueOf("1593"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1429"), Long.valueOf("1593"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1519"), Long.valueOf("521"));
			
			listItens.add(bean);
			
			/* 
			// SITES
			bean = new MudarPaiItensTempBean(Long.valueOf("1522"), Long.valueOf("457"));
			
			listItens.add(bean);			
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1710"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1583"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1329"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1457"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1581"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1535"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1212"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1196"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("453"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("452"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("437"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("484"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1187"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1660"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("481"), Long.valueOf("457"));
			
			listItens.add(bean);		
			
			bean = new MudarPaiItensTempBean(Long.valueOf("483"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("484"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("479"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1184"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("465"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("466"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1204"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1334"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1524"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("482"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("448"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("449"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1213"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1193"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("477"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1730"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("464"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("469"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1454"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("468"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1184"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("467"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1444"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("451"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1211"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1208"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1447"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1245"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1330"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1446"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1490"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1621"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1700"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1701"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1734"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1215"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1666"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1349"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1416"), Long.valueOf("457"));
			
			listItens.add(bean);
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1362"), Long.valueOf("457"));
			
			listItens.add(bean);			
			
			bean = new MudarPaiItensTempBean(Long.valueOf("1184"), Long.valueOf("457"));
			
			listItens.add(bean);			
			 */ 
			
			
            tx = session.beginTransaction();
    		ControlePermissao controlePermissao = new ControlePermissao();
			
            Iterator it = listItens.iterator();
            
            while (it.hasNext()) {
            	
            	MudarPaiItensTempBean itemBean = (MudarPaiItensTempBean) it.next();
				ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) this.buscar(ItemEstruturaIett.class, itemBean.getCodItem());
				Set Iettus = itemEstrutura.getItemEstrutUsuarioIettusesByCodIett(); 
				
				Iterator itIettus = Iettus.iterator();
				
				while (itIettus.hasNext()) {
					
					ItemEstrutUsuarioIettus itemEstrutUsuario = (ItemEstrutUsuarioIettus) itIettus.next();
					
		            /******** Historico *********/
		            
		            HistoricoIettus historico = new HistoricoIettus(itemEstrutUsuario,
		            												HistoricoIettus.excluirPermissoes,
		            												session,
		            												new ConfiguracaoDao(request),
		            												request);
		            historico.gerarHistorico();
		            
		        	/******** Historico *********/
			
			        //
			        // controlar as permissoes
			        //
		        	//if(!ControlePermissao.PERMISSAO_FUNCAO_ACOMPANHAMENTO.equals(itemEstrutUsuario.getCodTpPermIettus())){
		        		session.delete(itemEstrutUsuario);
		        	//}
				}	
	        	//mudar o pai
	        	itemEstrutura.setItemEstruturaIett((ItemEstruturaIett) this.buscar(ItemEstruturaIett.class, itemBean.getCodPai()));           
						        
				//
    	        // controlar as permissoes
    	        //
    			               	
    			controlePermissao.atualizarPermissoesItemEstrutura(itemEstrutura, null, session, true, request);
				
            }           
			
			System.out.println("Commit...");
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException(r); 
				}
			this.logger.error(e);
			throw new ECARException(e); 
		}
	}
	
	/**
	 * Mantis 9917
	 * 
	 * Solicitação da SEPL - carga de realizado físico por local (Tarifa Social Homero Oguido), através de arquivo
	 * 
         * @param request
	 * @throws ECARException
	 */
	public void efetuarCargaArquivoRealizadoFisicoPorLocalTarifaSocialDezembro2004(HttpServletRequest request) throws ECARException {
		Transaction tx = null;
		try {
			BufferedReader in = new BufferedReader (new FileReader("/home/precoma/TarifaSocialDezembro2004_dados_cvs.csv"));
			String linha = "";
			int registrosLidos = 0;
			List listTempBean = new ArrayList();
			
			while ((linha=in.readLine()) != null) {
				// ordem dos campos: cod_lit;Dezembro/2004;
				String[] campos = linha.split(";");
				if(campos != null) {
					TempBean tempBean = new TempBean();
					
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("12"));
					tempBean.setAno(Integer.valueOf("2004"));
					tempBean.setQtde(Double.valueOf(campos[1]));
					listTempBean.add(tempBean);
				}
				registrosLidos++;
			}
			
			System.out.println("registrosLidos: " + registrosLidos);

			System.out.println("Iniciando transacao...");
			tx = session.beginTransaction();
			Dao dao = new Dao();
			AcompRealFisicoDao arfDao = new AcompRealFisicoDao(null);
			Iterator it = listTempBean.iterator();
			while(it.hasNext()) {
				TempBean bean = (TempBean)it.next();
				LocalItemLit lit = (LocalItemLit)dao.buscar(LocalItemLit.class, bean.getCodLit());
				AcompRealFisicoArf arf = arfDao.buscarPorIettir(Long.valueOf(bean.getMes()), Long.valueOf(bean.getAno()), Long.valueOf("1187"));
				
				if(lit == null || arf == null) {
					throw new Exception("lit == null || arf == null");
				}
				
				AcompRealFisicoLocalArfl arfl = new AcompRealFisicoLocalArfl();
				arfl.setLocalItemLit(lit);
				arfl.setAcompRealFisicoArf(arf);
				arfl.setQuantidadeArfl(bean.getQtde());
				session.save(arfl);
			}
			
			//int i = Integer.parseInt("asdasd.as;;");
			
			System.out.println("Commit...");
			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException(r); 
				}
			this.logger.error(e);
			throw new ECARException(e); 
		}
	}
	/**
	 * Mantis 9917
	 * 
	 * Solicitação da SEPL - carga de realizado físico por local (Tarifa Social Homero Oguido), através de arquivo
	 * 
         * @param request
         * @throws ECARException
	 */
	public void efetuarCargaArquivoRealizadoFisicoPorLocalTarifaSocialDezembro2005(HttpServletRequest request) throws ECARException {
		Transaction tx = null;
		try {
			BufferedReader in = new BufferedReader (new FileReader("/home/precoma/TarifaSocialDezembro2005_dados_cvs.csv"));
			String linha = "";
			int registrosLidos = 0;
			List listTempBean = new ArrayList();
			
			while ((linha=in.readLine()) != null) {
				// ordem dos campos: cod_lit;Dezembro/2005;
				String[] campos = linha.split(";");
				if(campos != null) {
					TempBean tempBean = new TempBean();
					
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("12"));
					tempBean.setAno(Integer.valueOf("2005"));
					tempBean.setQtde(Double.valueOf(campos[1]));
					listTempBean.add(tempBean);
				}
				registrosLidos++;
			}
			
			System.out.println("registrosLidos: " + registrosLidos);

			System.out.println("Iniciando transacao...");
			tx = session.beginTransaction();
			Dao dao = new Dao();
			AcompRealFisicoDao arfDao = new AcompRealFisicoDao(null);
			Iterator it = listTempBean.iterator();
			while(it.hasNext()) {
				TempBean bean = (TempBean)it.next();
				LocalItemLit lit = (LocalItemLit)dao.buscar(LocalItemLit.class, bean.getCodLit());
				AcompRealFisicoArf arf = arfDao.buscarPorIettir(Long.valueOf(bean.getMes()), Long.valueOf(bean.getAno()), Long.valueOf("1187"));
				
				if(lit == null || arf == null) {
					throw new Exception("lit == null || arf == null");
				}
				
				AcompRealFisicoLocalArfl arfl = new AcompRealFisicoLocalArfl();
				arfl.setLocalItemLit(lit);
				arfl.setAcompRealFisicoArf(arf);
				arfl.setQuantidadeArfl(bean.getQtde());
				session.save(arfl);
			}
			
			//int i = Integer.parseInt("asdasd.as;;");
			
			System.out.println("Commit...");
			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException(r); 
				}
			this.logger.error(e);
			throw new ECARException(e); 
		}
	}
	
	/**
	 * Carga de tipo de indicador (grupo de metas físicas) para os itens (SEPL)
	 * 
         * @param request
	 * @throws ECARException
	 */
	public void alteracaoTipoIndicador (HttpServletRequest request) throws ECARException {
		Transaction tx = null;

		try {
			List listaAriParaRemover = new ArrayList();
			System.out.println("TempAtualizacaoARFDao.alteracaoTipoIndicador() - Iniciando transacao...");
			tx = session.beginTransaction();

			// selecionar os IETTRs do tipo Meta Física PPA (cod_satb = 41)
			String select = "select iettr from ItemEstrtIndResulIettr as iettr where iettr.sisAtributoSatb.codSatb=41";
			Query q = this.session.createQuery(select);
			List listaIettrs = q.list();
			

			if(listaIettrs != null && !listaIettrs.isEmpty()) {
				System.out.println("TempAtualizacaoARFDao.alteracaoTipoIndicador() - total de iettrs: " + listaIettrs.size());

				Iterator itIettr = listaIettrs.iterator();
		
				while (itIettr.hasNext()){
					ItemEstrtIndResulIettr iettr = (ItemEstrtIndResulIettr)itIettr.next();
					boolean temIndPPA = false;
					// nivel de produto
					if(iettr.getItemEstruturaIett().getEstruturaEtt().getCodEtt().longValue() == 4) {
						//
						List listaNiveis = new ArrayList(iettr.getItemEstruturaIett().getItemEstruturaNivelIettns());
						if(listaNiveis != null && !listaNiveis.isEmpty()) {
							Iterator itNiveis = listaNiveis.iterator();
							
							while (itNiveis.hasNext()){
								SisAtributoSatb satb = (SisAtributoSatb)itNiveis.next();
								
								//nivel de planejamento PPA (cod_satb=35)
								if(satb.getCodSatb().longValue() == 35) {
									temIndPPA = true;
								}
							}							
							
						}	
						if(!temIndPPA){
							//indicador de resultado Meta Física (cod_satb=40)
							iettr.setSisAtributoSatb((SisAtributoSatb) new SisAtributoDao(null).buscar(SisAtributoSatb.class, Long.valueOf("40")));
							//temIndPPA = false;
						}	
					} else{
						//indicador de resultado Meta Física (cod_satb=40)
						iettr.setSisAtributoSatb((SisAtributoSatb) new SisAtributoDao(null).buscar(SisAtributoSatb.class, Long.valueOf("40")));
					}
					
				}	
						
			}
					
			System.out.println("TempAtualizacaoARFDao.alteracaoTipoIndicador() - Commit da transacao...");
			
			tx.commit();
		} catch (Exception e) {
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
	 * Lista itens da estrutura que possuem mais de uma ocorrência na função Indicadores/Metas. (Mantis 10103):
         * @param request
         * @return
         * @throws ECARException
	 */
	public List listaIettIndResul(HttpServletRequest request) throws ECARException {
		Transaction tx = null;
		List ietts = new ArrayList(); 
		
		try {
			
			System.out.println("listaIettIndResul() - Iniciando consulta...");
			tx = session.beginTransaction();

			StringBuilder select = new StringBuilder("select item from ItemEstruturaIett as item where item.indAtivoIett = 'S'");
			
			Query q = this.getSession().createQuery(select.toString());
			
			List listaIett = q.list();			
		
			if(listaIett != null && !listaIett.isEmpty()) {
				Iterator itIett = listaIett.iterator();
				List listaIetts = new ArrayList(); 
				String temp = "";
					
				while (itIett.hasNext()){
					ItemEstruturaIett iett = (ItemEstruturaIett)itIett.next();
					listaIetts = new ArrayList(iett.getItemEstrtIndResulIettrs());
					
					if(listaIetts.size() > 1)
						ietts.add(iett);					
				}	
						
			}
					
			System.out.println("listaIettIndResul() - Commit da transacao...");
			
			tx.commit();
			
			return ietts;
			
		} catch (Exception e) {
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
	 * Mantis : 10014: Investigação do uso dado aos campos do tipo data no eCAR-SEPL
	 * 
	 * @author aleixo
	 * @param codEstrutura
	 * @throws ECARException 
	 */
	public void listarDadosItemEstrutura(long codEstrutura) throws ECARException{
		try {
			System.out.println("TempAtualizacaoARFDao.listarDadosItemEstrutura() - Início do método...");

			// selecionar os IETTRs do tipo Meta Física PPA (cod_satb = 41)
			String select = "from ItemEstruturaIett iett where iett.estruturaEtt.codEtt = :codEtt order by iett.siglaIett, iett.nomeIett";
			Query q = this.session.createQuery(select);
			q.setLong("codEtt", codEstrutura);

			System.out.println("TempAtualizacaoARFDao.listarDadosItemEstrutura() - Buscando dados...");
			List listaIetts = q.list();
					
			if(listaIetts != null && !listaIetts.isEmpty()){
				System.out.println("TempAtualizacaoARFDao.listarDadosItemEstrutura() - Escrevendo no console...");
				Iterator itIett = listaIetts.iterator();
				System.out.println("Numero;Nome;Situacao;DataInicio;DataTermino;PrevisaoConclusao");
				while(itIett.hasNext()){
					ItemEstruturaIett item = (ItemEstruturaIett) itIett.next();
					
					String numero = item.getSiglaIett();
					String nome = item.getNomeIett();
					String situacao = (item.getSituacaoSit() != null) ? item.getSituacaoSit().getDescricaoSit() : "";
					String dataInicio = Data.parseDate(item.getDataInicioIett());
					String dataTermino = Data.parseDate(item.getDataTerminoIett());
					String previsaoConclusao = (item.getDescricaoR4() != null && !"null".equalsIgnoreCase(item.getDescricaoR4())) ? item.getDescricaoR4() : "";
					
					System.out.println(numero+";"+nome+";"+situacao+";"+dataInicio+";"+dataTermino+";"+previsaoConclusao);
				}
			}
			else {
				System.out.println("TempAtualizacaoARFDao.listarDadosItemEstrutura() - Nenhum item foi encontrado...");
			}

			System.out.println("TempAtualizacaoARFDao.listarDadosItemEstrutura() - Fim do método.");
			
		} catch (Exception e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
	}
	
	/**
	 * Mantis 10149: Duplicar configurações da estrutura para a SEPL (Requisito para o novo PPA)
	 * 
	 * @author aleixo
	 * @throws ECARException
	 */
	public void duplicarEstruturas() throws ECARException{
		Transaction tx = null;

		try {
			List listaAriParaRemover = new ArrayList();
			System.out.println("TempAtualizacaoARFDao.duplicarEstruturas() - Iniciando transacao...");
			tx = session.beginTransaction();
			/*
			 * Buscar todas as Estruturas.
			 * Para cada estrutura:
			 * 		Copiar dados
			 * 		Copiar atributos na estrutura
			 * 		Copiar funcoes na estrutura
			 * 		Copiar funcoes de acompanhamento na estrutura
			 * FimPara
			 * 
			 */

			EstruturaDao estruturaDao = new EstruturaDao(this.request);
			
			System.out.println("TempAtualizacaoARFDao.duplicarEstruturas() - Buscando estruturas...");
			List listaEstruturas = estruturaDao.listar(EstruturaEtt.class, new String[]{"seqApresentacaoEtt","asc"});
			if(listaEstruturas != null && !listaEstruturas.isEmpty()){
				Iterator it = listaEstruturas.iterator();
				while(it.hasNext()){
					EstruturaEtt ett = (EstruturaEtt) it.next();
					
					//Clonando a estrutura
					EstruturaEtt novaEtt = (EstruturaEtt) BeanUtils.cloneBean(ett);
					//EstruturaEtt novaEtt = new EstruturaEtt();
					
					//Setando os dados para incluir...
					novaEtt.setCodEtt(null);
					String nome = ett.getNomeEtt() + " 2";
					if(nome.length() > 30)
						nome = nome.substring(0, 28) + "2";
					
					novaEtt.setNomeEtt(nome);
					
					String sigla = ett.getSiglaEtt() + " 2";
					if(sigla.length() > 10)
						sigla = sigla.substring(0, 8) + "2";
						
					novaEtt.setSiglaEtt(sigla);
					
					novaEtt.setEstruturaEtt(null);
					novaEtt.setEstruturaEtts(null);
					novaEtt.setItemEstruturaIetts(null);
					novaEtt.setItemEstruturarevisaoIettrevs(null);
					novaEtt.setEstruturaSituacaoEtts(null);
					novaEtt.setTipoAcompanhamentoTas(null);

					/*
					List situacoes = new ArrayList(ett.getEstruturaSituacaoEtts());
					novaEtt.setEstruturaSituacaoEtts(null);
					*/
					List estrutTpFuncAcmpEtttfa = new ArrayList(ett.getEstrutTpFuncAcmpEtttfas());
					novaEtt.setEstrutTpFuncAcmpEtttfas(null);

					List estruturaAcessoEttas = new ArrayList(ett.getEstruturaAcessoEttas());
					novaEtt.setEstruturaAcessoEttas(null);

					List estruturaAtributoEttats = new ArrayList(ett.getEstruturaAtributoEttats());
					novaEtt.setEstruturaAtributoEttats(null);

					List estruturaFuncaoEttfs = new ArrayList(ett.getEstruturaFuncaoEttfs());
					novaEtt.setEstruturaFuncaoEttfs(null);
					
					
					//Salvando a estrutura para obter o novo codEtt
					session.save(novaEtt);

//					Percorrendo objetos sets da estrutura para modificar para a nova estrutura
					/*if(situacoes != null && !situacoes.isEmpty()){
						Iterator itSituacoes = situacoes.iterator();
						while(itSituacoes.hasNext()){
							SituacaoSit sit = (SituacaoSit) itSituacoes.next();
							SituacaoSit novaSit = (SituacaoSit) BeanUtils.cloneBean(sit);
							//novaEtttfa.getComp_id().setCodEtt(novaEtt.getCodEtt());
							//novaEtttfa.setEstruturaEtt(novaEtt);
							novaSit.getEstruturaSituacaoEtts().add(novaEtt);
							session.save(novaSit);
						}
					}*/
					
					if(estrutTpFuncAcmpEtttfa != null && !estrutTpFuncAcmpEtttfa.isEmpty()){
						Iterator itEstrutTpFuncAcmpEtttfa = estrutTpFuncAcmpEtttfa.iterator();
						while(itEstrutTpFuncAcmpEtttfa.hasNext()){
							EstrutTpFuncAcmpEtttfa etttfa = (EstrutTpFuncAcmpEtttfa) itEstrutTpFuncAcmpEtttfa.next();
							EstrutTpFuncAcmpEtttfa novaEtttfa = (EstrutTpFuncAcmpEtttfa) BeanUtils.cloneBean(etttfa);
							novaEtttfa.getComp_id().setCodEtt(novaEtt.getCodEtt());
							novaEtttfa.setEstruturaEtt(novaEtt);
							session.save(novaEtttfa);
						}
					}

					if(estruturaAtributoEttats != null && !estruturaAtributoEttats.isEmpty()){
						Iterator itEstruturaAtributosEttats = estruturaAtributoEttats.iterator();
						while(itEstruturaAtributosEttats.hasNext()){
							EstruturaAtributoEttat ettat = (EstruturaAtributoEttat) itEstruturaAtributosEttats.next();
							EstruturaAtributoEttat novaEttat = (EstruturaAtributoEttat) BeanUtils.cloneBean(ettat);
							novaEttat.getComp_id().setCodEtt(novaEtt.getCodEtt());
							novaEttat.setEstruturaEtt(novaEtt);
							novaEttat.setEstAtribTipoAcompEatas(null);
							session.save(novaEttat);
							if(ettat != null && ettat.getEstAtribTipoAcompEatas() != null && !ettat.getEstAtribTipoAcompEatas().isEmpty()) {
								List eatas = new ArrayList(ettat.getEstAtribTipoAcompEatas());
								if(eatas != null && !eatas.isEmpty()){
									Iterator itEatas = eatas.iterator();
									while(itEatas.hasNext()){
										EstAtribTipoAcompEata eata = (EstAtribTipoAcompEata) itEatas.next();
										EstAtribTipoAcompEata novaEata = (EstAtribTipoAcompEata) BeanUtils.cloneBean(eata);
										
										novaEata.setEstruturaAtributoEttat(novaEttat);
										
										session.save(novaEata);
									}
								}
							}
						}
					}

					
					if(estruturaFuncaoEttfs != null && !estruturaFuncaoEttfs.isEmpty()){
						Iterator itEstruturaFuncaoEttfs = estruturaFuncaoEttfs.iterator();
						while(itEstruturaFuncaoEttfs.hasNext()){
							EstruturaFuncaoEttf ettf = (EstruturaFuncaoEttf) itEstruturaFuncaoEttfs.next();
							EstruturaFuncaoEttf novaEttf = (EstruturaFuncaoEttf) BeanUtils.cloneBean(ettf);
							novaEttf.getComp_id().setCodEtt(novaEtt.getCodEtt());
							novaEttf.setEstruturaEtt(novaEtt);
							session.save(novaEttf);
						}
					}

					if(estruturaAcessoEttas != null && !estruturaAcessoEttas.isEmpty()){
						Iterator itEstruturaAcessoEttas = estruturaAcessoEttas.iterator();
						while(itEstruturaAcessoEttas.hasNext()){
							EstruturaAcessoEtta etta = (EstruturaAcessoEtta) itEstruturaAcessoEttas.next();
							EstruturaAcessoEtta novoEtta = (EstruturaAcessoEtta) BeanUtils.cloneBean(etta);
							
							novoEtta.getComp_id().setCodEtt(novaEtt.getCodEtt());
							novoEtta.setEstruturaEtt(novaEtt);
							
							session.save(novoEtta);
						}
					}
				}
			}
			
			System.out.println("TempAtualizacaoARFDao.duplicarEstruturas() - Commit da transacao...");
			tx.commit();
			System.out.println("TempAtualizacaoARFDao.duplicarEstruturas() - Commit realizado com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null)
				try {
					tx.rollback();
					System.out.println("TempAtualizacaoARFDao.duplicarEstruturas() - Erro no commit. Rollback efetuado.");
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
	}

	/**
	 * Mantis 10116
	 * 
	 * Solicitação da SEPL - carga de realizado físico por local (Tarifa Social Homero Oguido), através de arquivo
	 * 
         * @param request
         * @throws ECARException
	 */
	public void efetuarCargaArquivoRealizadoFisicoPorLocalTarifaSocialMarco2007(HttpServletRequest request) throws ECARException {
		Transaction tx = null;
		try {
			BufferedReader in = new BufferedReader (new FileReader("/home/precoma/TarifaSocialMarco2007_dados_csv.csv"));
			String linha = "";
			int registrosLidos = 0;
			List listTempBean = new ArrayList();
			
			while ((linha=in.readLine()) != null) {
				// ordem dos campos: cod_lit;Marco/2007;
				String[] campos = linha.split(";");
				if(campos != null) {
					TempBean tempBean = new TempBean();
					
					tempBean.setCodLit(Long.valueOf(campos[0]));
					tempBean.setMes(Integer.valueOf("03"));
					tempBean.setAno(Integer.valueOf("2007"));
					tempBean.setQtde(Double.valueOf(campos[1]));
					listTempBean.add(tempBean);
				}
				registrosLidos++;
			}
			
			System.out.println("registrosLidos: " + registrosLidos);

			System.out.println("Iniciando transacao...");
			tx = session.beginTransaction();
			Dao dao = new Dao();
			AcompRealFisicoDao arfDao = new AcompRealFisicoDao(null);
			Iterator it = listTempBean.iterator();
			while(it.hasNext()) {
				TempBean bean = (TempBean)it.next();
				LocalItemLit lit = (LocalItemLit)dao.buscar(LocalItemLit.class, bean.getCodLit());
				AcompRealFisicoArf arf = arfDao.buscarPorIettir(Long.valueOf(bean.getMes()), Long.valueOf(bean.getAno()), Long.valueOf("1187"));
				
				if(lit == null || arf == null) {
					throw new Exception("lit == null || arf == null");
				}
				
				AcompRealFisicoLocalArfl arfl = new AcompRealFisicoLocalArfl();
				arfl.setLocalItemLit(lit);
				arfl.setAcompRealFisicoArf(arf);
				arfl.setQuantidadeArfl(bean.getQtde());
				session.save(arfl);
			}
			
			//int i = Integer.parseInt("asdasd.as;;");
			
			System.out.println("Commit...");
			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException(r); 
				}
			this.logger.error(e);
			throw new ECARException(e); 
		}
	}

	/**
	 * Mantis: 10462
	 * Solicitação da SEPL - carga de realizado físico por local (Leite das Criancas - Crianças Atendidas), através de arquivo (2004 e 2005) 
	 * 
         * @param request
	 * @throws ECARException
	 */
	public void efetuarCargaArquivoRealizadoFisicoPorLocalLeiteDasCriancas2004e2005(HttpServletRequest request) throws ECARException {
		Transaction tx = null;
		try {
			BufferedReader in = new BufferedReader (new FileReader("/home/precoma/CriancasAtendidas2004e2005_Dados.csv"));
			String linha = "";
			int registrosLidos = 0;
			List listTempBean = new ArrayList();
			
			while ((linha=in.readLine()) != null) {
				// ordem dos campos: cod_lit;Dezembro de 2004 até Dezembro de 2005;
				String[] campos = linha.split(";");
				if(campos != null) {
					TempBean tempBean = null;
					if(campos[1] != null && !"".equals(campos[1].trim())) {
						// Dezembro/2004
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("12"));
						tempBean.setAno(Integer.valueOf("2004"));
						tempBean.setQtde(Double.valueOf(campos[1]));
						listTempBean.add(tempBean);
					}
					if(campos[2] != null && !"".equals(campos[2].trim())) {
						// Janeiro/2005
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("1"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[2]));
						listTempBean.add(tempBean);
					}
					if(campos[3] != null && !"".equals(campos[3].trim())) {
						// Fevereiro/2005
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("2"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[3]));
						listTempBean.add(tempBean);
					}
					if(campos[4] != null && !"".equals(campos[4].trim())) {
						// Março/2005
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("3"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[4]));
						listTempBean.add(tempBean);
					}
					if(campos[5] != null && !"".equals(campos[5].trim())) {
						// Abril/2005
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("4"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[5]));
						listTempBean.add(tempBean);
					}
					if(campos[6] != null && !"".equals(campos[6].trim())) {
						// Maio/2005
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("5"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[6]));
						listTempBean.add(tempBean);
					}
					if(campos[7] != null && !"".equals(campos[7].trim())) {
						// Junho/2005
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("6"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[7]));
						listTempBean.add(tempBean);
					}
					if(campos[8] != null && !"".equals(campos[8].trim())) {
						// Julho/2005
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("7"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[8]));
						listTempBean.add(tempBean);
					}
					if(campos[9] != null && !"".equals(campos[9].trim())) {
						// Agosto/2005
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("8"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[9]));
						listTempBean.add(tempBean);
					}
					if(campos[10] != null && !"".equals(campos[10].trim())) {
						// Setembro/2005
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("9"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[10]));
						listTempBean.add(tempBean);
					}
					if(campos[11] != null && !"".equals(campos[11].trim())) {
						// Outubro/2005
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("10"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[11]));
						listTempBean.add(tempBean);
					}
					if(campos[12] != null && !"".equals(campos[12].trim())) {
						// Novembro/2005
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("11"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[12]));
						listTempBean.add(tempBean);
					}
					if(campos[13] != null && !"".equals(campos[13].trim())) {
						// Dezembro/2005
						tempBean = new TempBean();
						tempBean.setCodLit(Long.valueOf(campos[0]));
						tempBean.setMes(Integer.valueOf("12"));
						tempBean.setAno(Integer.valueOf("2005"));
						tempBean.setQtde(Double.valueOf(campos[13]));
						listTempBean.add(tempBean);
					}
				}
				registrosLidos++;
			}
			
			System.out.println("registrosLidos: " + registrosLidos);

			System.out.println("Iniciando transacao...");
			tx = session.beginTransaction();
			Dao dao = new Dao();
			AcompRealFisicoDao arfDao = new AcompRealFisicoDao(null);
			Iterator it = listTempBean.iterator();
			while(it.hasNext()) {
				TempBean bean = (TempBean)it.next();
				LocalItemLit lit = (LocalItemLit)dao.buscar(LocalItemLit.class, bean.getCodLit());
				AcompRealFisicoArf arf = arfDao.buscarPorIettir(Long.valueOf(bean.getMes()), Long.valueOf(bean.getAno()), Long.valueOf("1186"));
				
				if(lit == null || arf == null) {
					throw new Exception("lit == null || arf == null");
				}
				
				AcompRealFisicoLocalArfl arfl = new AcompRealFisicoLocalArfl();
				arfl.setLocalItemLit(lit);
				arfl.setAcompRealFisicoArf(arf);
				arfl.setQuantidadeArfl(bean.getQtde());
				session.save(arfl);
			}
			
			System.out.println("Commit...");
			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException(r); 
				}
			this.logger.error(e);
			throw new ECARException(e); 
		}
	}
	
	/**
	 * 
	 * Solicitação de mudança de item pai - ECARCEL - (Mantis: 10911)
	 * 
         * @param request
         * @throws ECARException
	 */
	public void mudarPaiItemEcarcel(HttpServletRequest request) throws ECARException {
		Transaction tx = null;
		try {
            tx = session.beginTransaction();

            List listItens = new ArrayList();
			
			MudarPaiItensTempBean bean = new MudarPaiItensTempBean(Long.valueOf("1795"), Long.valueOf("1980"));
			
			listItens.add(bean);
			
    		ControlePermissao controlePermissao = new ControlePermissao();
			
            Iterator it = listItens.iterator();
            
            while (it.hasNext()) {
            	MudarPaiItensTempBean itemBean = (MudarPaiItensTempBean) it.next();
				ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) this.buscar(ItemEstruturaIett.class, itemBean.getCodItem());
				Set iettus = itemEstrutura.getItemEstrutUsuarioIettusesByCodIett(); 
				
				Iterator itIettus = iettus.iterator();
				
				while (itIettus.hasNext()) {
					ItemEstrutUsuarioIettus itemEstrutUsuario = (ItemEstrutUsuarioIettus) itIettus.next();
					
		            /******** Historico *********/
		        	ConfiguracaoDao dao = new ConfiguracaoDao(request);
		        	ConfiguracaoCfg config = dao.getConfiguracao();
					
		        	HistoricoMaster historicoMaster = new HistoricoMaster();
		        	
		        	if("S".equals(config.getIndGerarHistoricoCfg())) {
		        	
						historicoMaster.setDataHoraHistorico(new Date());
						historicoMaster.setUsuManutencao(itemEstrutUsuario.getUsuManutencao());
						historicoMaster.setCodReferenciaGeral(itemEstrutUsuario.getItemEstruturaIett().getCodIett());
						historicoMaster.setHistoricoMotivo((HistoricoMotivo)super.buscar(HistoricoMotivo.class, Long.valueOf(28)));
						session.save(historicoMaster);
						
						HistoricoIettusH iettush = new HistoricoIettusH();
										
						iettush.setCod_atb((itemEstrutUsuario.getSisAtributoSatb() != null?itemEstrutUsuario.getSisAtributoSatb().getCodSatb():null));
						iettush.setCodTpPermIettus(itemEstrutUsuario.getCodTpPermIettus());
						iettush.setDataInclusaoIettus(itemEstrutUsuario.getDataInclusaoIettus());
						iettush.setIndAtivMonitIettus(itemEstrutUsuario.getIndAtivMonitIettus());
						iettush.setIndBloqPlanIettus(itemEstrutUsuario.getIndBloqPlanIettus());
						iettush.setIndDesatMonitIettus(itemEstrutUsuario.getIndDesatMonitIettus());
						iettush.setIndDesblPlanIettus(itemEstrutUsuario.getIndDesblPlanIettus());
						iettush.setIndEdicaoIettus(itemEstrutUsuario.getIndEdicaoIettus());
						iettush.setIndEmitePosIettus(itemEstrutUsuario.getIndEmitePosIettus());
						iettush.setIndExcluirIettus(itemEstrutUsuario.getIndExcluirIettus());
						iettush.setIndInfAndamentoIettus(itemEstrutUsuario.getIndInfAndamentoIettus());
						iettush.setIndLeituraIettus(itemEstrutUsuario.getIndLeituraIettus());
						iettush.setIndProxNivelIettus(itemEstrutUsuario.getIndProxNivelIettus());
						iettush.setItemEstruturaIett(itemEstrutUsuario.getItemEstruturaIett());
						iettush.setItemEstruturaIettOrigem(itemEstrutUsuario.getItemEstruturaIettOrigem());
						iettush.setTipoFuncAcompTpfa(itemEstrutUsuario.getTipoFuncAcompTpfa());
						iettush.setUsuarioUsu(itemEstrutUsuario.getUsuarioUsu());
						iettush.setUsuManutencao(itemEstrutUsuario.getUsuManutencao());	
						iettush.setHistoricoMaster(historicoMaster);
						
						session.save(iettush);
		        	}
		        	/******** Historico *********/
		        						
					
	        		session.delete(itemEstrutUsuario);
				}	

				//mudar o pai
	        	itemEstrutura.setItemEstruturaIett((ItemEstruturaIett) this.buscar(ItemEstruturaIett.class, itemBean.getCodPai()));           
						        
    			controlePermissao.atualizarPermissoesItemEstrutura(itemEstrutura, null, session, true, request);
            }           
			
			System.out.println("Commit...");
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException(r); 
				}
			this.logger.error(e);
			throw new ECARException(e); 
		}
	}
}