/*
 * Created on 20/04/2005
 *
 */
package ecar.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.bean.DetalhamentoTotaisConta;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.ConfigExecFinanCef;
import ecar.pojo.ConfigSisExecFinanCsef;
import ecar.pojo.ConfigSisExecFinanCsefv;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EfItemEstContaEfiec;
import ecar.pojo.EfItemEstRealizadoEfier;
import ecar.pojo.ExercicioExe;

/**
 * @author garten
 *
 */
public class ItemEstruturaRealizadoDao extends Dao {
    
    /**
     *
     * @param request
     */
    public ItemEstruturaRealizadoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * 
	 * @param item
	 * @param request
	 * @throws ECARException
	 */
	public void setEfItemEstRealizadoEfier(EfItemEstRealizadoEfier item, HttpServletRequest request) throws ECARException{
		ConfigSisExecFinanCsefvDao versaoDao = new ConfigSisExecFinanCsefvDao(null);
		ItemEstruturaContaOrcamentoDao itemEstContaDao = new ItemEstruturaContaOrcamentoDao(null);
		
		SegurancaECAR usuarioLogado = (SegurancaECAR) request.getSession().getAttribute("seguranca");

		Long anoReferencia = Long.valueOf(Pagina.getParamStr(request, "anoReferenciaEfier"));
		Long mesReferencia = Long.valueOf(Pagina.getParamStr(request, "mesReferenciaEfier"));
		
		item.setAnoReferenciaEfier(anoReferencia);
		item.setMesReferenciaEfier(mesReferencia);

		ConfigSisExecFinanCsefv versao = (ConfigSisExecFinanCsefv) versaoDao.buscar(ConfigSisExecFinanCsefv.class, Long.valueOf(Pagina.getParamStr(request, "codVersaoEscolhida")));
		if(versao == null)
			throw new ECARException("integracaoFinanceira.manual.inclusao.versaoInexistente");
				
//		TODO MANTIS  0011017
//		INICIO - MANTIS  0011017		
		item.setConfigSisExecFinanCsefv(versao);
//		 FIM - MANTIS  0011017
		
		//Verificar se o sistema permite inclusão manual
		//ConfigSisExecFinanCsef sistema = (ConfigSisExecFinanCsef) new ConfigSisExecFinanDao(request).buscar(ConfigSisExecFinanCsef.class, Long.valueOf(Pagina.getParamStr(request, "configSisExecFinanCsef")));
		ConfigSisExecFinanCsef sistema = versao.getConfigSisExecFinanCsef();
		if(!"S".equals(sistema.getIndPermiteValormanualorcCsef()))
			throw new ECARException("integracaoFinanceira.manual.inclusao.naoPermiteManual");
		
		List estruturasContabil = new ConfigExecFinanDao(request).getConfigExecFinanByVersao(versao);
        String estrutura = "";
        if (estruturasContabil != null) {
            
            Iterator it = estruturasContabil.iterator();
            while(it.hasNext()){
                ConfigExecFinanCef estruturaContabil = (ConfigExecFinanCef) it.next();
                if(estrutura.length() > 0)
                    estrutura += " ";
                //estrutura += Pagina.getParamStr(request, "e" + estruturaContabil.getCodCef().toString() + mes + ano + verSistema);                
                estrutura += Pagina.getParamStr(request, "e" + estruturaContabil.getCodCef().toString() + String.valueOf(versao.getCodCsefv()));                
            }
            /*
             * Por causa destes ifs neste método os valores da Estrutura não são perdidos na alteração. Motivo: na tela de alterar
             * conta todos os campos, com a exceção do Acumulado estão desabilitados e por isso não são enviados por request. 
             * Quando faço essa comparação, ao verificar que estes campos estão vazios, mantenho os valores existentes originalmente
             * no objeto conta ( no caso são os valores que vieram do banco ) e por isso não são perdidos mesmo que venha vazio no
             * request.
             */       
            if(!"".equals(estrutura)){
        		List contasOrcamentarias = itemEstContaDao.listar(EfItemEstContaEfiec.class, new String[] {"contaSistemaOrcEfiec", "asc"});
				estrutura = estrutura.trim();
				boolean existeConta = false;
				Iterator itConta = contasOrcamentarias.iterator();
				while(itConta.hasNext()){
					EfItemEstContaEfiec con = (EfItemEstContaEfiec) itConta.next();
					if(estrutura.equals(con.getContaSistemaOrcEfiec())){
						existeConta = true;
						break;
					}
				}
				
				if(!existeConta){
				//	throw new ECARException("integracaoFinanceira.manual.inclusao.contaInexistente");
				}

                item.setContaSistemaOrcEfier(estrutura.toUpperCase());
            }
        }		
		
		item.setDataHoraInfoEfier(Data.getDataAtual());
		
		if(item.getDataInclusaoEfier() == null)
			item.setDataInclusaoEfier(Data.getDataAtual());
		
		item.setIndContabilidadeEfier("A");
		item.setIndManualEfier(Pagina.getParamStr(request, "indManualEfier"));
		

		item.setUsuarioUsu(usuarioLogado.getUsuario());
		
		String valor1 = Pagina.getParamStr(request, "valor1Efier");
		if(!"".equals(valor1)){
			item.setValor1Efier(new Double(valor1.replaceAll(",",".")));
		}

		String valor2 = Pagina.getParamStr(request, "valor2Efier");
		if(!"".equals(valor2)){
			item.setValor2Efier(new Double(valor2.replaceAll(",",".")));
		}
		
		String valor3 = Pagina.getParamStr(request, "valor3Efier");
		if(!"".equals(valor3)){
			item.setValor3Efier(new Double(valor3.replaceAll(",",".")));
		}
		
		String valor4 = Pagina.getParamStr(request, "valor4Efier");
		if(!"".equals(valor4)){
			item.setValor4Efier(new Double(valor4.replaceAll(",",".")));
		}
		
		String valor5 = Pagina.getParamStr(request, "valor5Efier");
		if(!"".equals(valor5)){
			item.setValor5Efier(new Double(valor5.replaceAll(",",".")));
		}
		
		String valor6 = Pagina.getParamStr(request, "valor6Efier");
		if(!"".equals(valor6)){
			item.setValor6Efier(new Double(valor6.replaceAll(",",".")));
		}
		
	}
	
	
	/**
	 * 
	 * @param item
	 * @param request
	 * @param iteracao numero da iteracao para inserção de multiplos parametros
	 * @throws ECARException
	 */
	public void setEfItemEstRealizadoEfier(EfItemEstRealizadoEfier item, HttpServletRequest request, int iteracao) throws ECARException{
		ConfigSisExecFinanCsefvDao versaoDao = new ConfigSisExecFinanCsefvDao(null);
		ItemEstruturaContaOrcamentoDao itemEstContaDao = new ItemEstruturaContaOrcamentoDao(null);
		
		SegurancaECAR usuarioLogado = (SegurancaECAR) request.getSession().getAttribute("seguranca");

		Long anoReferencia = Long.valueOf(Pagina.getParamStr(request, "anoReferenciaEfier"));
		Long mesReferencia = Long.valueOf(Pagina.getParamStr(request, "mesReferenciaEfier"));
		
		item.setAnoReferenciaEfier(anoReferencia);
		item.setMesReferenciaEfier(mesReferencia);

		ConfigSisExecFinanCsefv versao = (ConfigSisExecFinanCsefv) versaoDao.buscar(ConfigSisExecFinanCsefv.class, Long.valueOf(Pagina.getParamStr(request, "codVersaoEscolhida")));
		if(versao == null)
			throw new ECARException("integracaoFinanceira.manual.inclusao.versaoInexistente");

		
//		TODO MANTIS  0011017
//		INICIO - MANTIS  0011017		
		item.setConfigSisExecFinanCsefv(versao);
//		 FIM - MANTIS  0011017
		
		//Verificar se o sistema permite inclusão manual
		//ConfigSisExecFinanCsef sistema = (ConfigSisExecFinanCsef) new ConfigSisExecFinanDao(request).buscar(ConfigSisExecFinanCsef.class, Long.valueOf(Pagina.getParamStr(request, "configSisExecFinanCsef")));
		ConfigSisExecFinanCsef sistema = versao.getConfigSisExecFinanCsef();
		if(!"S".equals(sistema.getIndPermiteValormanualorcCsef()))
			throw new ECARException("integracaoFinanceira.manual.inclusao.naoPermiteManual");
		
		List estruturasContabil = new ConfigExecFinanDao(request).getConfigExecFinanByVersao(versao);
        String estrutura = "";
        if (estruturasContabil != null) {
            
            Iterator it = estruturasContabil.iterator();
            while(it.hasNext()){
                ConfigExecFinanCef estruturaContabil = (ConfigExecFinanCef) it.next();
                if(estrutura.length() > 0)
                    estrutura += " ";
                //estrutura += Pagina.getParamStr(request, "e" + estruturaContabil.getCodCef().toString() + mes + ano + verSistema);                
                //estrutura += Pagina.getParamStr(request, "e" + estruturaContabil.getCodCef().toString() + String.valueOf(versao.getCodCsefv()));
                estrutura += request.getParameterValues("e" + estruturaContabil.getCodCef().toString() + String.valueOf(versao.getCodCsefv()))[iteracao - 1];
            }
            /*
             * Por causa destes ifs neste método os valores da Estrutura não são perdidos na alteração. Motivo: na tela de alterar
             * conta todos os campos, com a exceção do Acumulado estão desabilitados e por isso não são enviados por request. 
             * Quando faço essa comparação, ao verificar que estes campos estão vazios, mantenho os valores existentes originalmente
             * no objeto conta ( no caso são os valores que vieram do banco ) e por isso não são perdidos mesmo que venha vazio no
             * request.
             */       
            if(!"".equals(estrutura)){
        		List contasOrcamentarias = itemEstContaDao.listar(EfItemEstContaEfiec.class, new String[] {"contaSistemaOrcEfiec", "asc"});
				estrutura = estrutura.trim();
				boolean existeConta = false;
				Iterator itConta = contasOrcamentarias.iterator();
				while(itConta.hasNext()){
					EfItemEstContaEfiec con = (EfItemEstContaEfiec) itConta.next();
					if(estrutura.equals(con.getContaSistemaOrcEfiec())){
						existeConta = true;
						break;
					}
				}
				
				if(!existeConta){
				//	throw new ECARException("integracaoFinanceira.manual.inclusao.contaInexistente");
				}

                item.setContaSistemaOrcEfier(estrutura.toUpperCase());
            }
        }
        

		item.setDataHoraInfoEfier(Data.getDataAtual());
		
		if(item.getDataInclusaoEfier() == null)
			item.setDataInclusaoEfier(Data.getDataAtual());
		
		item.setIndContabilidadeEfier("A");
		item.setIndManualEfier(Pagina.getParamStr(request, "indManualEfier"));
		

		item.setUsuarioUsu(usuarioLogado.getUsuario());
		
		String valor1 = Pagina.getParamStr(request, "valor" + iteracao+ "_1Efier");
		if(!"".equals(valor1)){
			item.setValor1Efier(new Double(valor1.replaceAll(",",".")));
		}

		String valor2 = Pagina.getParamStr(request, "valor" + iteracao+ "_2Efier");
		if(!"".equals(valor2)){
			item.setValor2Efier(new Double(valor2.replaceAll(",",".")));
		}
		
		String valor3 = Pagina.getParamStr(request, "valor" + iteracao+ "_3Efier");
		if(!"".equals(valor3)){
			item.setValor3Efier(new Double(valor3.replaceAll(",",".")));
		}
		
		String valor4 = Pagina.getParamStr(request, "valor" + iteracao+ "_4Efier");
		if(!"".equals(valor4)){
			item.setValor4Efier(new Double(valor4.replaceAll(",",".")));
		}
		
		String valor5 = Pagina.getParamStr(request, "valor" + iteracao+ "_5Efier");
		if(!"".equals(valor5)){
			item.setValor5Efier(new Double(valor5.replaceAll(",",".")));
		}
		
		String valor6 = Pagina.getParamStr(request, "valor" + iteracao+ "_6Efier");
		if(!"".equals(valor6)){
			item.setValor6Efier(new Double(valor6.replaceAll(",",".")));
		}
	}
	
        /**
         *
         * @param efier
         * @param tx
         * @throws ECARException
         */
        public void gravar(EfItemEstRealizadoEfier efier, Transaction tx) throws ECARException{
		Long mes = efier.getMesReferenciaEfier();
		Long ano = efier.getAnoReferenciaEfier();
		String conta = efier.getContaSistemaOrcEfier();

		try{
			StringBuilder sql = new StringBuilder("from EfItemEstRealizadoEfier efier")
							.append(" where efier.mesReferenciaEfier = :mes")
							.append(" and efier.anoReferenciaEfier = :ano")
							.append(" and efier.contaSistemaOrcEfier = :conta");
			
			Query q  = this.session.createQuery(sql.toString());
			q.setLong("mes", mes.longValue());
			q.setLong("ano", ano.longValue());
			q.setString("conta", conta);
			
			List lista = q.list();
			
			if(lista != null && !lista.isEmpty()){
				throw new ECARException("integracaoFinanceira.manual.inclusao.contaCadastrada");
			}
			
			if(tx != null) {
				session.save(efier);
			} else {
				super.salvar(efier);
			}
		}
		catch (HibernateException e) {
            this.logger.error(e);
        	throw new ECARException("integracaoFinanceira.manual.inclusao.erro");
		}
	}
	
        /**
         *
         * @param efier
         * @throws ECARException
         */
        public void alterar(EfItemEstRealizadoEfier efier) throws ECARException{
		super.alterar(efier);
	}

        /**
         *
         * @param codigosParaExcluir
         * @throws ECARException
         * @throws HibernateException
         */
        public void excluir(String[] codigosParaExcluir) throws ECARException, HibernateException{
		Transaction tx = null;
		try {
			tx = this.session.beginTransaction();
			for(int i = 0; i < codigosParaExcluir.length; i++){
				EfItemEstRealizadoEfier efier = (EfItemEstRealizadoEfier) buscar(EfItemEstRealizadoEfier.class, Long.valueOf(codigosParaExcluir[i]));
				session.delete(efier);
			}
			tx.commit();
		} catch (Exception e) {
			if(tx != null)
				tx.rollback();
            this.logger.error(e);
        	throw new ECARException("integracaoFinanceira.manual.exclusao.erro");
		}
	}
	
    /**
     * Devolve um array de 6 posicoes com a soma dos valores de um ItemEstruturaRealizadoDao
     * 
     * Se por algum motivo esse método mudar sua forma de consulta, 
     * então analisar a possibilidade também de alterar o método "getSomaDetalhadaItemEstruturaRealizado()",
     * que é praticamente um clone desse método, mudando apenas a forma de retorno das informações.
     * 
     * clausula where da query montada, período trabalhado somente em cima de Mês e Ano
     * pois no HQL do hibernate não encontramos alternativa para data 
     * 
     * 	AnoX = Ano Referência
     * 	AnoI = Ano de Início do Exercício
     * 	AnoF = Ano Fim do Exercício
     * 	MexX = Mês Referência
     * 	MesI = Mês de Início do Exercício
     * 	MesF = Mês Fim do Exercício
     *
     *		((AnoX = AnoI E MesX >= MesI) OU (AnoX > AnoI) 
     * 		E
     * 		((AnoX = AnoF E MesX <= MesF) OU (AnoX < AnoF)
     * 
     * explicação:
     * 		Quando Ano Referência igual ao Inicial, o Mês deve ser maior ou igual
     * 		Quando Ano Referência maior que o Inicial, o Mês não importa
     * 		Quando Ano Referência igual ao Final, o Mês deve ser menor ou igual
     * 		Quando Ano Referência menor que o Final, o Mês não importa 
     * 
     * @param efItem
     * @param exercicio
     * @return Double[]
     * @throws ECARException
     * @throws HibernateException
     */
    public Double[] getSomaItemEstruturaRealizado(EfItemEstContaEfiec efItem, ExercicioExe exercicio) throws ECARException, HibernateException {
    	
        try{
        	
        	StringBuilder baseQuery = new StringBuilder();
        	StringBuilder where = new StringBuilder();
        	Double[] efier = new Double[]{new Double(0), new Double(0), new Double(0), new Double(0), new Double(0), new Double(0)};
        	
        	if(efItem.getIndAcumuladoEfiec() == null || "".equals(efItem.getIndAcumuladoEfiec().trim())) {
        		return efier;
        	}
        	
        	Calendar dataIniExe = Data.getGregorianCalendar(exercicio.getDataInicialExe());
        	Calendar dataFimExe = Data.getGregorianCalendar(exercicio.getDataFinalExe());
        	
        	/* O Calendar retorna os meses de 0 a 11 */
        	/* e para comparar no HQL soma-se 1 */
        	int mesIniExe = dataIniExe.get(Calendar.MONTH) + 1;
        	int mesFimExe = dataFimExe.get(Calendar.MONTH) + 1;
        	
        	Query query = null;
        	
        	if("S".equals(efItem.getIndAcumuladoEfiec())) {

	        	baseQuery.append("select itemEstRealizado.valor1Efier, ") 
				 .append("itemEstRealizado.valor2Efier,")
				 .append("itemEstRealizado.valor3Efier,")
				 .append("itemEstRealizado.valor4Efier,")
				 .append("itemEstRealizado.valor5Efier,")
				 .append("itemEstRealizado.valor6Efier ")
				 .append("from EfItemEstRealizadoEfier itemEstRealizado ");
		
				where.append(" where itemEstRealizado.contaSistemaOrcEfier = :conta ")
					 .append(" and (((itemEstRealizado.anoReferenciaEfier = :anoIni and itemEstRealizado.mesReferenciaEfier >= :mesIni)")
					 .append(" or itemEstRealizado.anoReferenciaEfier > :anoIni )")
					 .append(" and ((itemEstRealizado.anoReferenciaEfier = :anoFim and itemEstRealizado.mesReferenciaEfier <= :mesFim)")
					 .append(" or itemEstRealizado.anoReferenciaEfier < :anoFim))")
					 .append(" order by itemEstRealizado.anoReferenciaEfier desc, itemEstRealizado.mesReferenciaEfier desc");
	        	
        	} else if("N".equals(efItem.getIndAcumuladoEfiec())) {
        		
	            baseQuery.append("select sum(itemEstRealizado.valor1Efier), ")
	   			 .append("sum(itemEstRealizado.valor2Efier),")
	   			 .append("sum(itemEstRealizado.valor3Efier),")
	   			 .append("sum(itemEstRealizado.valor4Efier),")
	   			 .append("sum(itemEstRealizado.valor5Efier),")
	   			 .append("sum(itemEstRealizado.valor6Efier)")
	   			 .append("from EfItemEstRealizadoEfier itemEstRealizado ");
			
	            where.append(" where itemEstRealizado.contaSistemaOrcEfier = :conta ")
				 .append(" and (((itemEstRealizado.anoReferenciaEfier = :anoIni and itemEstRealizado.mesReferenciaEfier >= :mesIni)")
				 .append(" or itemEstRealizado.anoReferenciaEfier > :anoIni )")
				 .append(" and ((itemEstRealizado.anoReferenciaEfier = :anoFim and itemEstRealizado.mesReferenciaEfier <= :mesFim)")
				 .append(" or itemEstRealizado.anoReferenciaEfier < :anoFim))");
        		
        	}

        	query = this.getSession().createQuery(baseQuery.toString() + where);
        	query.setString("conta", efItem.getContaSistemaOrcEfiec());
        	query.setLong("anoIni", Long.parseLong(String.valueOf(dataIniExe.get(Calendar.YEAR))));
        	query.setLong("mesIni", Long.parseLong(String.valueOf(mesIniExe)));
        	query.setLong("anoFim", Long.parseLong(String.valueOf(dataFimExe.get(Calendar.YEAR))));
        	query.setLong("mesFim", Long.parseLong(String.valueOf(mesFimExe)));
        	
        	List lista = query.list();
        	
        	Iterator it = lista.iterator();
        	
        	/* Única forma que funcionou, receber o objeto e depois CAST p/ Double */
        	if (it.hasNext()){
        		Object aux[] = (Object[]) it.next();

        		if (aux[0] != null)
        			efier[0] = (Double) aux[0];
        		if (aux[1] != null)
        			efier[1] = (Double) aux[1];
        		if (aux[2] != null)
        			efier[2] = (Double) aux[2];
        		if (aux[3] != null)
        			efier[3] = (Double) aux[3];
        		if (aux[4] != null)
        			efier[4] = (Double) aux[4];
        		if (aux[5] != null)
        			efier[5] = (Double) aux[5];
        	}
        	
            return (efier);
        	
        } catch(Exception e){
            this.logger.error(e);
        	throw new ECARException("relAcompanhamento.financeiro.consulta.erro");
        }
    }
    
    /**
     * Retorna os totais da contas detalhadas em inserções manuais e via importação de arquivo txt.
     * @param efItem
     * @param exercicio
     * @return DetalhamentoTotaisConta
     * @throws ECARException
     * @throws HibernateException
     */
    public DetalhamentoTotaisConta getSomaDetalhadaItemEstruturaRealizado(EfItemEstContaEfiec efItem, ExercicioExe exercicio) throws ECARException, HibernateException {
    	
        try{
        	
        	StringBuilder baseQuery = null;
        	StringBuilder where = null;
        	
        	DetalhamentoTotaisConta detalheConta = new DetalhamentoTotaisConta();
        	
        	if(efItem.getIndAcumuladoEfiec() == null || "".equals(efItem.getIndAcumuladoEfiec().trim())) {
        		return detalheConta;
        	}
        	        	        	
        	Calendar dataIniExe = Data.getGregorianCalendar(exercicio.getDataInicialExe());
        	Calendar dataFimExe = Data.getGregorianCalendar(exercicio.getDataFinalExe());
        	
        	/* O Calendar retorna os meses de 0 a 11 */
        	/* e para comparar no HQL soma-se 1 */
        	int mesIniExe = dataIniExe.get(Calendar.MONTH) + 1;
        	int mesFimExe = dataFimExe.get(Calendar.MONTH) + 1;
        	
        	Query query = null;
        	String indManual = "N";
        	List lista = new ArrayList();
        	
        	if("S".equals(efItem.getIndAcumuladoEfiec())) {        		
        		
        		for(int i=0; i<=1; i++) {
        		
        			baseQuery = new StringBuilder();
        			where = new StringBuilder();
        			
		        	baseQuery.append("select itemEstRealizado.valor1Efier, ") 
					 .append("itemEstRealizado.valor2Efier,")
					 .append("itemEstRealizado.valor3Efier,")
					 .append("itemEstRealizado.valor4Efier,")
					 .append("itemEstRealizado.valor5Efier,")
					 .append("itemEstRealizado.valor6Efier,")
					 .append("itemEstRealizado.indManualEfier ")
					 .append("from EfItemEstRealizadoEfier itemEstRealizado ");
			
					where.append(" where itemEstRealizado.contaSistemaOrcEfier like :conta ")
						 .append(" and (((itemEstRealizado.anoReferenciaEfier = :anoIni and itemEstRealizado.mesReferenciaEfier >= :mesIni)")
						 .append(" or itemEstRealizado.anoReferenciaEfier > :anoIni )")
						 .append(" and ((itemEstRealizado.anoReferenciaEfier = :anoFim and itemEstRealizado.mesReferenciaEfier <= :mesFim)")
						 .append(" or itemEstRealizado.anoReferenciaEfier < :anoFim))")
						 .append(" and itemEstRealizado.indManualEfier = :indManualEfier))")
						 .append(" order by itemEstRealizado.anoReferenciaEfier desc, itemEstRealizado.mesReferenciaEfier desc");
					
			    	query = this.getSession().createQuery(baseQuery.toString() + where);
			    	//Não apagar essas linhas de forma nenhuma.
			    	query.setString("conta", efItem.getContaSistemaOrcEfiec());
//			    	String[] parametros = efItem.getContaSistemaOrcEfiec().split(" ");
//			    	query.setString("conta", parametros[0] + "%" + parametros[2] + " " + parametros[3]);
//			    	query.setString("conta", parametros[0] + "%" + parametros[1] + " " + parametros[2]);
		        	query.setLong("anoIni", Long.parseLong(String.valueOf(dataIniExe.get(Calendar.YEAR))));
		        	query.setLong("mesIni", Long.parseLong(String.valueOf(mesIniExe)));
		        	query.setLong("anoFim", Long.parseLong(String.valueOf(dataFimExe.get(Calendar.YEAR))));
		        	query.setLong("mesFim", Long.parseLong(String.valueOf(mesFimExe)));
		        	query.setString("indManualEfier", indManual);
		        	
		        	query.setMaxResults(1);
		        	lista.addAll(query.list());
		        	
		        	if("S".equals(indManual)) {
		        		indManual = "S";
		        	}
        		}
				
	        	
        	} else if("N".equals(efItem.getIndAcumuladoEfiec())) {
        		
	            baseQuery.append("select sum(itemEstRealizado.valor1Efier), ")
	   			 .append("sum(itemEstRealizado.valor2Efier),")
	   			 .append("sum(itemEstRealizado.valor3Efier),")
	   			 .append("sum(itemEstRealizado.valor4Efier),")
	   			 .append("sum(itemEstRealizado.valor5Efier),")
	   			 .append("sum(itemEstRealizado.valor6Efier),")
	   			 .append("itemEstRealizado.indManualEfier ")
	   			 .append("from EfItemEstRealizadoEfier itemEstRealizado ");
			
	            where.append(" where itemEstRealizado.contaSistemaOrcEfier like :conta ")
				 	.append(" and (((itemEstRealizado.anoReferenciaEfier = :anoIni and itemEstRealizado.mesReferenciaEfier >= :mesIni)")
				 	.append(" or itemEstRealizado.anoReferenciaEfier > :anoIni )")
				 	.append(" and ((itemEstRealizado.anoReferenciaEfier = :anoFim and itemEstRealizado.mesReferenciaEfier <= :mesFim)")
				 	.append(" or itemEstRealizado.anoReferenciaEfier < :anoFim))")
				 	.append(" group by itemEstRealizado.indManualEfier")
	            	.append(" order by itemEstRealizado.indManualEfier asc");
	            
	        	query = this.getSession().createQuery(baseQuery.toString() + where);	        	
		    	//Não apagar essas linhas de forma nenhuma.
		    	query.setString("conta", efItem.getContaSistemaOrcEfiec());
//		    	String[] parametros = efItem.getContaSistemaOrcEfiec().split(" ");
//		    	query.setString("conta", parametros[0] + "%" + parametros[2] + " " + parametros[3]);
//		    	query.setString("conta", parametros[0] + "%" + parametros[1] + " " + parametros[2]);
	        	query.setLong("anoIni", Long.parseLong(String.valueOf(dataIniExe.get(Calendar.YEAR))));
	        	query.setLong("mesIni", Long.parseLong(String.valueOf(mesIniExe)));
	        	query.setLong("anoFim", Long.parseLong(String.valueOf(dataFimExe.get(Calendar.YEAR))));
	        	query.setLong("mesFim", Long.parseLong(String.valueOf(mesFimExe)));

	        	lista.addAll(query.list());
        	}
        	       	
        	Iterator it = lista.iterator();

        	Double[][] arrayDetalhesContas = detalheConta.getDetalhesContas();
        	
        	int linha = 0;
        	while (it.hasNext()){
        		
        		Object aux[] = (Object[]) it.next();

        		if ((aux[6] != null) && ("S".equals((String)aux[6])) && (linha == 0))
        			linha++;
        		
        		if (aux[0] != null)
        			arrayDetalhesContas[linha][0] = (Double) aux[0];
        		if (aux[1] != null)
        			arrayDetalhesContas[linha][1] = (Double) aux[1];
        		if (aux[2] != null)
        			arrayDetalhesContas[linha][2] = (Double) aux[2];
        		if (aux[3] != null)
        			arrayDetalhesContas[linha][3] = (Double) aux[3];
        		if (aux[4] != null)
        			arrayDetalhesContas[linha][4] = (Double) aux[4];
        		if (aux[5] != null)
        			arrayDetalhesContas[linha][5] = (Double) aux[5];
        		
        		linha++;
        	}
        	
            return (detalheConta);
        	
        } catch(Exception e){
            this.logger.error(e);
        	throw new ECARException("relAcompanhamento.financeiro.consulta.erro");
        }
    }

    
    /**
     * Método que verifica se é para apresentar o valor na devida posição, conforme a configuração.
     * O Critério para apresentar o valor ou não, depende se existe conteúdo no campo financeiroDescValor<posicao>Cfg.
     * Ex.: Se existir conteúdo em ConfiguracaoCfg.getFinanceiroDescValor1Cfg(), então deve mostrar o valor1Efier.
     * 
     * Obs.: O parâmetro "posicao" é verificado em um array, portanto, para o valor1Efier, deve-se passar posicao=0,
     * para valor2Efier deve-se passar posicao=1, e assim por diante.
     * 
     * @param posicao
     * @return
     * @throws ECARException
     */
    public boolean getVerificarMostrarValorByPosicaoCfg(int posicao) throws ECARException{
    	ConfiguracaoCfg cfg = (new ConfiguracaoDao(null)).getConfiguracao();
    	posicao++; //Incremento a posição porque ela é passada em array[0,1,2,3,4,5]
    	boolean retorno = false;
    	
    	switch (posicao) {
		case 1:{
			if(cfg.getFinanceiroDescValor1Cfg() != null && !"".equals(cfg.getFinanceiroDescValor1Cfg().trim()))
				retorno = true;
			break;
		}
		case 2:{
			if(cfg.getFinanceiroDescValor2Cfg() != null && !"".equals(cfg.getFinanceiroDescValor2Cfg().trim()))
				retorno = true;
			break;
		}
		case 3:{
			if(cfg.getFinanceiroDescValor3Cfg() != null && !"".equals(cfg.getFinanceiroDescValor3Cfg().trim()))
				retorno = true;
			break;
		}
		case 4:{
			if(cfg.getFinanceiroDescValor4Cfg() != null && !"".equals(cfg.getFinanceiroDescValor4Cfg().trim()))
				retorno = true;
			break;
		}
		case 5:{
			if(cfg.getFinanceiroDescValor5Cfg() != null && !"".equals(cfg.getFinanceiroDescValor5Cfg().trim()))
				retorno = true;
			break;
		}
		case 6:{
			if(cfg.getFinanceiroDescValor6Cfg() != null && !"".equals(cfg.getFinanceiroDescValor6Cfg().trim()))
				retorno = true;
			break;
		}

		default:
			break;
		}
    	return retorno;
    }
    
    /**
     * Verifica na configuração se é para mostrar o campo recursoDescValor1, recursoDescValor2 ou recursoDescValor3.
     * @author aleixo
     * @version 0.1 - 26/03/2007
     * @param posicao
     * @return boolean
     * @throws ECARException
     */
    public boolean getVerificarMostrarRecursoByPosicaoCfg(int posicao) throws ECARException{
    	ConfiguracaoCfg cfg = (new ConfiguracaoDao(null)).getConfiguracao();
    	posicao++; //Incremento a posição porque ela é passada em array[0,1,2,3,4,5]
    	boolean retorno = false;
    	
    	switch (posicao) {
		case 1:{
			if(cfg.getRecursoDescValor1Cfg() != null && !"".equals(cfg.getRecursoDescValor1Cfg().trim()))
				retorno = true;
			break;
		}
		case 2:{
			if(cfg.getRecursoDescValor2Cfg() != null && !"".equals(cfg.getRecursoDescValor2Cfg().trim()))
				retorno = true;
			break;
		}
		case 3:{
			if(cfg.getRecursoDescValor3Cfg() != null && !"".equals(cfg.getRecursoDescValor3Cfg().trim()))
				retorno = true;
			break;
		}

		default:
			break;
		}
    	return retorno;
    }
    
    /**
     * Retorna os valores (registros) das contas para o arquivo de exportação
     * @param mesIni
     * @param anoIni
     * @param mesFim
     * @param anoFim
     * @return Lista de contas
     * @throws ECARException
     * @throws HibernateException
     */
    public List getValoresParaExportacao(String mesIni, String anoIni, String mesFim, String anoFim) throws ECARException, HibernateException{
    	try{
	       	StringBuilder baseQuery = new StringBuilder("from EfItemEstRealizadoEfier itemEstRealizado ")
	       					.append(" where (((itemEstRealizado.anoReferenciaEfier = :anoIni and itemEstRealizado.mesReferenciaEfier >= :mesIni)")
	       					.append(" or itemEstRealizado.anoReferenciaEfier > :anoIni)")
	       					.append(" and ((itemEstRealizado.anoReferenciaEfier = :anoFim and itemEstRealizado.mesReferenciaEfier <= :mesFim)")
	       					.append(" or itemEstRealizado.anoReferenciaEfier < :anoFim))");
	       	
	       	Query objQuery = this.getSession().createQuery(baseQuery.toString());
	       	
	       	objQuery.setLong("mesIni", Long.valueOf(mesIni).longValue());
	       	objQuery.setLong("anoIni", Long.valueOf(anoIni).longValue());
	       	objQuery.setLong("mesFim", Long.valueOf(mesFim).longValue());
	       	objQuery.setLong("anoFim", Long.valueOf(anoFim).longValue());
	       	
	    	return objQuery.list();
        } catch(Exception e){
            this.logger.error(e);
        	throw new ECARException("integracaoFinanceira.exportarArquivo.consultaDados.erro");
        }	 
    }
    
    /**
     * Retorna uma lista de EfItemEstRealizadoEfier referente à conta/mes/ano.
     * @param conta
     * @param mes
     * @param ano
     * @return
     * @throws ECARException
     */
    public List getItemEstRealizadoEfierToImportacao(String conta, Long mes, Long ano) throws ECARException{
    	try{
	       	StringBuilder baseQuery = new StringBuilder("from EfItemEstRealizadoEfier itemEstRealizado ")
	       						.append(" where itemEstRealizado.anoReferenciaEfier = :ano")
	       						.append("   and itemEstRealizado.mesReferenciaEfier = :mes")
	       						.append("   and itemEstRealizado.contaSistemaOrcEfier = :conta");
	       	
	       	Query objQuery = this.getSession().createQuery(baseQuery.toString());
	       	
	       	objQuery.setString("conta", conta);
	       	objQuery.setLong("ano", ano.longValue());
	       	objQuery.setLong("mes", mes.longValue());
	       	
	    	return objQuery.list();
    	}
    	catch (HibernateException e) {
            this.logger.error(e);
        	throw new ECARException("integracaoFinanceira.importarArquivo.importacao.erro");
		}
    }
    
    /**
     * Retorna uma lista de EfItemEstRealizadoEfier referente à Ano Inicio e Mês Inicio/Ano Fim Mês Fim.
     * Onde itemEstRealizado não seja entrada manual.
     * @author carlos
     * @since 16/04/2007
     * @param inicio
     * @param fim
     * @param sistema
     * @return List
     * @throws ECARException
     */
    public List getItemEstRealizadoEfierToImportacao(Date inicio, Date fim, ConfigSisExecFinanCsef sistema) throws ECARException{
    	
   	
    	Long anoIni = Long.valueOf(new SimpleDateFormat("yyyy").format(inicio)); 
    	Long mesIni = Long.valueOf(new SimpleDateFormat("MM").format(inicio));
    	Long anoFim = Long.valueOf(new SimpleDateFormat("yyyy").format(fim));
    	Long mesFim = Long.valueOf(new SimpleDateFormat("MM").format(fim));
    	
    	try{
	       	StringBuilder baseQuery = new StringBuilder("from EfItemEstRealizadoEfier itemEstRealizado ")
	       						.append(" where itemEstRealizado.indManualEfier = 'N'")
	       						.append(" and (itemEstRealizado.anoReferenciaEfier >= :anoIni and itemEstRealizado.anoReferenciaEfier <= :anoFim)")
	       						.append(" and (itemEstRealizado.mesReferenciaEfier >= :mesIni and itemEstRealizado.mesReferenciaEfier <= :mesFim)")
	       						.append(" and itemEstRealizado.configSisExecFinanCsefv.configSisExecFinanCsef.codCsef = :codSistema");
	       	
	       	Query objQuery = this.getSession().createQuery(baseQuery.toString());
	       	objQuery.setLong("anoIni", anoIni.longValue());
	       	objQuery.setLong("mesIni", mesIni.longValue());
	       	objQuery.setLong("anoFim", anoFim.longValue());
	       	objQuery.setLong("mesFim", mesFim.longValue());
	       	objQuery.setLong("codSistema", sistema.getCodCsef());
	       	
	    	return objQuery.list();
    	}
    	catch (HibernateException e) {
            this.logger.error(e);
        	throw new ECARException("integracaoFinanceira.importarArquivo.importacao.erro");
		}
    }
    
    /**
     * Retorna uma lista de EfItemEstRealizadoEfier
     * @param conta
     * @param mes
     * @param indManualEfier
     * @param ano
     * @param codSistema
     * @return
     * @throws ECARException
     */
    public List getItemEstRealizadoEfier(String conta, String mes, String ano, String codSistema, String indManualEfier) throws ECARException{
    	
    	try
    	{    		

    		Criteria crits = session.createCriteria(EfItemEstRealizadoEfier.class);
    		
    		if(!"".equals(conta.trim()))
    			crits.add(Restrictions.eq("contaSistemaOrcEfier", conta));
    		
    		if(!"".equals(mes.trim()))
    			crits.add(Restrictions.eq("mesReferenciaEfier", Long.valueOf(mes).longValue()));
    		
    		if(!"".equals(ano.trim()))
    			crits.add(Restrictions.eq("anoReferenciaEfier", Long.valueOf(ano).longValue()));
    		
    		if(!"".equals(indManualEfier.trim()))
    			crits.add(Restrictions.eq("indManualEfier", indManualEfier));
    		
    		if(!"".equals(codSistema.trim())){   			
    			Criteria crits2 = crits.createCriteria("configSisExecFinanCsefv");
    			crits2.add(Restrictions.eq("configSisExecFinanCsef.codCsef", Long.valueOf(codSistema).longValue()));
    		}
    		
    		return crits.list();

    	}catch (Exception e) {
            this.logger.error(e);
        	throw new ECARException("integracaoFinanceira.manual.buscar.erro");
		}
    }
}
