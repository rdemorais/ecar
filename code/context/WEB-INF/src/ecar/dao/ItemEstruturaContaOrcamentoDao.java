/*
 * Criado em 05/01/2005
 *
 */
package ecar.dao;

import java.util.ArrayList;
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
import comum.database.HibernateUtil;
import comum.util.Data;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.ConfigExecFinanCef;
import ecar.pojo.EfItemEstContaEfiec;
import ecar.pojo.EfItemEstRealizadoEfier;
import ecar.pojo.ExercicioExe;
import ecar.pojo.FonteRecursoFonr;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.RecursoRec;

/**
 * @author felipev
 *  
 */
public class ItemEstruturaContaOrcamentoDao extends Dao {

    /**
     * Construtor. Chama o Session factory do Hibernate
     * @param request
     */
    public ItemEstruturaContaOrcamentoDao(HttpServletRequest request) {
		super();
		this.request = request;
    }
    
    
    /**
     * 
     * @param itemEstrutura
     * @param codFonr
     * @return List
     * @throws ECARException
     */
    public List getAtivos(ItemEstruturaIett itemEstrutura, long codFonr) throws ECARException{        
        try{
           // return this.getSession().createFilter(itemEstrutura.getEfItemEstContaEfiecs(), "where this.indAtivoEfiec = 'S' order by this.contaSistemaOrcEfiec asc").list();    
            return this.getSession().createFilter(itemEstrutura.getEfItemEstContaEfiecs(), "where this.indAtivoEfiec = 'S' and this.fonteRecursoFonr.codFonr = " + codFonr + " order by this.fonteRecursoFonr.sequenciaFonr asc, this.recursoRec.sequenciaRec asc").list();    
        } catch (HibernateException e){
        	this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
                    
    }    
    
    /**
     * Verifica a possibilidade de inclusão de uma Conta para o item baseado na seguinte regra:
     * Esta informação, mesmo que disponível para mais de um nível dentro da estrutura somente poderá ser incluída
     * em um dos níveis dentro de um hierarquia de itens. Ou seja, se houver um ainformação incluída em um nível os
     * itens "pais, avôs, etc" deste item e os "filhos, netos, etc" deste item não poderão ter informação nesta função
     * @param itemEstrutura
     * @return
     * @throws ECARException
     */
    public boolean verificaPossibilidadeInclusao(ItemEstruturaIett itemEstrutura) throws ECARException{
    	/*
		try{
			EstruturaEtt estrutura = itemEstrutura.getEstruturaEtt();
			Query queryEstrutura = this.getSession().createQuery("select distinct conta.itemEstruturaIett.estruturaEtt " +
					" from EfItemEstContaEfiec conta");
			EstruturaEtt estruturaNiveis = (EstruturaEtt) queryEstrutura.uniqueResult();
			if(estruturaNiveis == null)
				return true;
			else{
				if(estruturaNiveis.equals(estrutura))
					return true;
			}
			return false;	
		}catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}
		*/		

		if(itemEstrutura.getEfItemEstContaEfiecs().size() > 0)
			return true;
			
		ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
		
		List lista = new ArrayList();
		lista.addAll(itemDao.getAscendentes(itemEstrutura));
		lista.addAll(itemDao.getDescendentes(itemEstrutura, true));
		
		Iterator it = lista.iterator();
		
		while(it.hasNext()){
			ItemEstruturaIett itemLista = (ItemEstruturaIett) it.next();
			
			if(itemLista.getEfItemEstContaEfiecs() != null &&
					itemLista.getEfItemEstContaEfiecs().size() > 0)
				return false;
		}
		
		return true;	
    
    } 
    
    /**
     * Método que gera os campos HTML para cadastro da Estrutura da Conta, baseado nos registros
     * da tabela de Estrutura Contábil
     * @param conta Se for informada uma conta, recupera os valores gravados para esta conta
     * @param disabled Indicativo mostrando se os campos devem estar desabilitados ou não
     * @param request Procura no request a informação de valores para cada campo criado
     * @return
     * @throws ECARException
     */
      public static String geraHTMLCadastroEstruturaConta(String conta, boolean disabled, HttpServletRequest request) throws ECARException {
        StringBuilder retorno = new StringBuilder();
        String strConta = conta;
        List estruturasContabil = new ConfigExecFinanDao(request).listar(
                ConfigExecFinanCef.class, new String[] { "seqApresentacaoCef","asc" });
        retorno.append("");
        if (estruturasContabil != null) {
            Iterator it = estruturasContabil.iterator();
            String camposConta[] = new String[estruturasContabil.size()];
            if(strConta != null)
                camposConta = strConta.split(" ");

            int i = 0;       
            while (it.hasNext()) {
                String strValor = "";
                ConfigExecFinanCef estruturaContabil = (ConfigExecFinanCef) it
                        .next();
                /* este try-catch serve para aassegurar que não irá ocorrer uma exceção ao ler valores após
                 * ter sido criada uma nova estrutura contábil
                 */
                try{
                    if(camposConta[i] != null)
                        strValor = camposConta[i];                    
                } catch ( ArrayIndexOutOfBoundsException ex){
                	
                	strValor = "";
                }
                
                StringBuilder buffer = new StringBuilder();
                StringBuilder campoHidden = new StringBuilder();
	            
	            if("validaCategoria".equals(estruturaContabil.getConfigTipoDadoCtd().getIdRotinaCtd())) {
	            	
	            	Query q = null;
	        		q =  HibernateUtil.currentSession().createQuery("from FonteRecursoFonr");
	        		List<FonteRecursoFonr> categorias = (List<FonteRecursoFonr>)q.list();
	        		
	        		for(FonteRecursoFonr categoria : categorias) {
	        			buffer.append(categoria.getSiglaFonr() + ",");
	        		}
	        		
	        		String strBuffer = buffer.toString().substring(0, buffer.toString().lastIndexOf(","));
	        		
	        		campoHidden.append("<input type='hidden' name='categorias' id='categorias' value='" + strBuffer + "'>");
	            	
	            }
	            
	            
	            if("validaFonte".equals(estruturaContabil.getConfigTipoDadoCtd().getIdRotinaCtd())) {
	            	
	            	Query q = null;
	        		q =  HibernateUtil.currentSession().createQuery("from RecursoRec");
	        		List<RecursoRec> fontes = (List<RecursoRec>)q.list();
	        		
	        		for(RecursoRec fonte : fontes) {
	        			buffer.append(fonte.getSiglaRec() + ",");
	        		}
	        		
	        		String strBuffer = buffer.toString().substring(0, buffer.toString().lastIndexOf(","));
	        		campoHidden.append("<input type='hidden' name='fontes' id='fontes' value='" + strBuffer + "'>");
	            	
	            }
                
                if(!"".equals(Pagina.getParamStr(request, "e" + estruturaContabil.getCodCef())))
                    strValor = Pagina.getParamStr(request, "e" + estruturaContabil.getCodCef());
                retorno.append("<div class=\"tabelaaolado\">")
                	   .append(estruturaContabil.getLabelCef() )
                	   .append("&nbsp;&nbsp;</br>")
                	   .append("<input type=\"text\" size=\"")
                	   .append(estruturaContabil.getNumCaracteresCef())
                	   .append("\" maxlength=\"")
                	   .append(estruturaContabil.getNumCaracteresCef())
                	   .append("\" name=\"e")
                	   .append(estruturaContabil.getCodCef())
                	   .append("\" value=\"")
                	   .append(strValor)
                	   .append("\"");
               if(disabled)
                   retorno.append( " disabled ");
               retorno.append("></div>");
               
               if(campoHidden.length() > 0) {
            	   retorno.append(campoHidden.toString());
               }
               
                i++;
            }
        }        
        return retorno.toString();
    }
    
    /**
     * Método que gera o label a ser usado na listagem de Contas do Orçamento, baseado nos registros
     * da tabela de Estrutura Contábil
     * @param request
     * @return String com código HTML
     * @throws ECARException
     */
    public static String geraLabelCadastroEstruturaConta(HttpServletRequest request) throws ECARException {        
        StringBuilder retorno = new StringBuilder();        
        List estruturasContabil = new ConfigExecFinanDao(request).listar(
                ConfigExecFinanCef.class, new String[] { "seqApresentacaoCef","asc" });
        if (estruturasContabil != null) {
            Iterator it = estruturasContabil.iterator();
            while (it.hasNext()) {
                ConfigExecFinanCef estruturaContabil = (ConfigExecFinanCef) it
                        .next();
                if(retorno.length() > 0)
                    retorno.append(" / ");
                retorno.append(estruturaContabil.getLabelCef());
            }
        }
        return retorno.toString();
    }

    /**
     * Método que gera validação para os campos do cadastro da Estrutura da Conta, baseado nos registros
     * da tabela de Estrutura Contábil
     * @param request
     * @return String com código JavaScript
     * @throws ECARException
     */
    public static String geraValidacaoCadastroEstruturaConta(HttpServletRequest request) throws ECARException{
	    StringBuilder retorno = new StringBuilder();
	    List estruturasContabil = new ConfigExecFinanDao(request).listar(
	            					ConfigExecFinanCef.class, 
	            					new String [] {"seqApresentacaoCef","asc"});
	    if(estruturasContabil != null){
	        Iterator it = estruturasContabil.iterator();
	        while ( it.hasNext() ){
	            
	        	ConfigExecFinanCef estruturaContabil = (ConfigExecFinanCef) it.next();
	        	
	            retorno.append("if (!")
	            	   .append(estruturaContabil.getConfigTipoDadoCtd().getIdRotinaCtd())
	            	   .append("(form.e")
	            	   .append(estruturaContabil.getCodCef())
	            	   .append(",'")
	            	   .append(estruturaContabil.getLabelCef())
	            	   .append("',true))\n")
	            	   .append(" return false;\n");
	        }
	    }
	    return retorno.toString();
	}
    
    /**
     * Exclusão de Contas do Orçamento de um Item
     * Funcionamento: Verifica se a conta está cadastrada alguma vez em EF_Item_Est_Realizado_EFIER.
     * Se não estivar reliza exclusão da conta do BD, se encontrar inativa a conta.
     * @param codigosParaExcluir Array com os códigos das contas a serem excluídas
     * @throws ECARException
     */
    public void excluir(String[] codigosParaExcluir) throws ECARException{
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        for (int i = 0; i < codigosParaExcluir.length; i++) {
	            EfItemEstContaEfiec conta = (EfItemEstContaEfiec) this.buscar(EfItemEstContaEfiec.class, Long.valueOf(codigosParaExcluir[i]));
	            EfItemEstRealizadoEfier realizado = new EfItemEstRealizadoEfier();
	            realizado.setContaSistemaOrcEfier(conta.getContaSistemaOrcEfiec());
	            if( this.pesquisar(realizado, null).size() == 0) {
	                session.delete(conta);
	            	objetos.add(conta);
	            } else {
	                conta.setIndAtivoEfiec("N");
	                session.update(conta);	                
					objetos.add(conta);
	            }                
	        }
			
			tx.commit();
	
			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("ALT_EXC");
				Iterator itObj = objetos.iterator();
	
				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
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
     * Grava uma conta fazendo uma verificação anteriormente para não permitir que a mesma conta (estrutura) seja
     * gravada mais de uma vez para o mesmo item, fonte e recurso 
     * @param conta
     * @throws ECARException
     */
    public void salvar(EfItemEstContaEfiec conta) throws ECARException{
        EfItemEstContaEfiec contaAux = new EfItemEstContaEfiec();
        //Esses campos nao podem repetir no registro
        contaAux.setContaSistemaOrcEfiec(conta.getContaSistemaOrcEfiec());
        contaAux.setExercicioExe(conta.getExercicioExe());
        contaAux.setFonteRecursoFonr(conta.getFonteRecursoFonr());
        contaAux.setRecursoRec(conta.getRecursoRec());
        //contaAux.setItemEstruturaIett(conta.getItemEstruturaIett());
        if(this.pesquisar(contaAux, null).size() > 0)
            throw new ECARException("itemEstrutura.contaOrcamento.inclusao.erro.jaExiste");
        else
            super.salvar(conta);
    }
    
    /**
     * Grava uma lista de contas
     * @param contas
     * @author aleixo
     * @since 0.1, 12/03/2007
     * @version 0.2, 13/03/2007 
     * @throws ECARException
     */
    public void salvar(List<EfItemEstContaEfiec> contas) throws ECARException{
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            for(EfItemEstContaEfiec conta : contas){
                EfItemEstContaEfiec contaAux = new EfItemEstContaEfiec();
                //Esses campos nao podem repetir no registro
                contaAux.setContaSistemaOrcEfiec(conta.getContaSistemaOrcEfiec());
                contaAux.setExercicioExe(conta.getExercicioExe());
                contaAux.setFonteRecursoFonr(conta.getFonteRecursoFonr());
                contaAux.setRecursoRec(conta.getRecursoRec());
                contaAux.setItemEstruturaIett(conta.getItemEstruturaIett());
                
                //Se encontrar algum, deixar este item em particular e passar para o próximo, sem gerar exceção.
                //Pq o item já existe.
                if(!(this.pesquisar(contaAux, null).size() > 0))
                	session.save(conta);
            }
            tx.commit();
            
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
     * Cria um objeto EfItemEstContaEfiec a partir de parâmetros passados
     * no objeto request
     * 
     * @param request
     * @param conta
     * @throws ECARException
     */    
    public void setItemEstruturaContaOrcamento(HttpServletRequest request, EfItemEstContaEfiec conta) throws ECARException{
        List estruturasContabil = new ConfigExecFinanDao(request).listar(
                ConfigExecFinanCef.class, new String[] { "seqApresentacaoCef","asc" });
        String estrutura = "";
        if (estruturasContabil != null) {
            Iterator it = estruturasContabil.iterator();
            while(it.hasNext()){
                ConfigExecFinanCef estruturaContabil = (ConfigExecFinanCef) it.next();
                if(estrutura.length() > 0)
                    estrutura += " ";
                estrutura += Pagina.getParamStr(request, "e" + estruturaContabil.getCodCef());                
            }
            /*
             * Por causa destes ifs neste método os valores da Estrutura não são perdidos na alteração. Motivo: na tela de alterar
             * conta todos os campos, com a exceção do Acumulado estão desabilitados e por isso não são enviados por request. 
             * Quando faço essa comparação, ao verificar que estes campos estão vazios, mantenho os valores existentes originalmente
             * no objeto conta ( no caso são os valores que vieram do banco ) e por isso não são perdidos mesmo que venha vazio no
             * request.
             */       
            if(!"".equals(estrutura))
                conta.setContaSistemaOrcEfiec(estrutura);
        }
        if(!"".equals(Pagina.getParamStr(request, "exercicioExe")))
            conta.setExercicioExe( (ExercicioExe) this.buscar(ExercicioExe.class, Long.valueOf(Pagina.getParamStr(request, "exercicioExe"))));
        if(!"".equals(Pagina.getParamStr(request, "fonteRecursoFonr")))
            conta.setFonteRecursoFonr( (FonteRecursoFonr) this.buscar(FonteRecursoFonr.class, Long.valueOf(Pagina.getParamStr(request, "fonteRecursoFonr"))));
        if(!"".equals(Pagina.getParamStr(request, "recursoRec")))
            conta.setRecursoRec( (RecursoRec) this.buscar(RecursoRec.class, Long.valueOf(Pagina.getParamStr(request, "recursoRec"))));
        conta.setIndAcumuladoEfiec(Pagina.getParamStr(request, "indAcumuladoEfiec"));
        conta.setItemEstruturaIett( (ItemEstruturaIett) this.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett"))) );
        conta.setIndAtivoEfiec("S");
    }
    
    /**
     * Devolve um ItemEstruturaConta a partir de um item, exercicio, fonte e recurso.
     * Se não encontrar, devolve um ItemEstruturaConta vazio (somente com os parametros informados).
     * @param item
     * @param exercicio 
     * @param fonte
     * @param recurso
     * @return EfItemEstContaEfiec
     * @throws ECARException
     */
    public EfItemEstContaEfiec getItemEstruturaConta(ItemEstruturaIett item, 
    												 ExercicioExe exercicio,
    												 FonteRecursoFonr fonte,
    												 RecursoRec recurso) throws ECARException {
        
        EfItemEstContaEfiec efiec = new EfItemEstContaEfiec();
        List lista = new ArrayList();
        
        efiec.setItemEstruturaIett(item);
        efiec.setExercicioExe(exercicio);
        efiec.setFonteRecursoFonr(fonte);
        efiec.setRecursoRec(recurso);
        
        lista = pesquisar(efiec, null);
        
        if (lista != null && lista.size() > 0)
            return ((EfItemEstContaEfiec)lista.get(0));
        else
            return efiec;		

        
    }
    
    /**
     * Retorna as contas (registros) para o arquivo de exportação
     * @param mesIni
     * @param anoIni
     * @param mesFim
     * @param anoFim
     * @return Lista de contas
     * @throws ECARException
     * @throws HibernateException
     */
    public List getContasParaExportacao(String mesIni, String anoIni, String mesFim, String anoFim) throws ECARException, HibernateException{
    	try{
    		
    		int diaFinal = Data.getUltimoDiaMes(Data.parseDate("01/" + mesFim + "/" + anoFim));
    		Date dataInicial = Data.parseDate("01/" + mesIni + "/" + anoIni);
    		Date dataFinal = Data.parseDate(String.valueOf(diaFinal) + "/" + mesFim + "/" + anoFim);
    		
    		
	       	StringBuilder baseQuery = new StringBuilder("from EfItemEstContaEfiec conta ")
	       									  .append(" where (:dataIni >= conta.exercicioExe.dataInicialExe")
	       									  .append("   and :dataFim <= conta.exercicioExe.dataFinalExe)");
	       	
	       	Query objQuery = this.getSession().createQuery(baseQuery.toString());
	       	
	       	objQuery.setDate("dataIni", dataInicial);
	       	objQuery.setDate("dataFim", dataFinal);
	       	
	    	return objQuery.list();
        } catch(Exception e){
            this.logger.error(e);
        	throw new ECARException("integracaoFinanceira.exportarArquivo.consultaDados.erro");
        }	 
    }
    
    /**
     * Método que verifica se já existe no banco algum registro que coincida com os novos dados do objeto a ser alterado.
     * Caso exista algum registro que coincida então o método retornará false.
     * @param obj
     * @author carlos
     * @return false
     * @throws ECARException
     * @throws HibernateException
     */
    public boolean consisteEfItemEstContaEfiec(EfItemEstContaEfiec obj) throws ECARException, HibernateException {
    	
    	boolean retorno = true;
    	
    	try
    	{
    	
	    	Criteria crits = session.createCriteria(EfItemEstContaEfiec.class);
	    	
	    	crits.add(Restrictions.ne("codEfiec", (obj.getCodEfiec() == null? 0L: obj.getCodEfiec()))); // != codEfiec 
	    	crits.add(Restrictions.eq("contaSistemaOrcEfiec", obj.getContaSistemaOrcEfiec()));
	    	crits.add(Restrictions.eq("exercicioExe.codExe", obj.getExercicioExe().getCodExe()));
	    	crits.add(Restrictions.eq("recursoRec.codRec", obj.getRecursoRec().getCodRec()));
	    	crits.add(Restrictions.eq("fonteRecursoFonr.codFonr", obj.getFonteRecursoFonr().getCodFonr()));
	    	
	    	List<EfItemEstContaEfiec> lista = (List<EfItemEstContaEfiec>)crits.list();
	    	
	    	if (lista.size() > 0) {
	    		retorno = false;
	    	}
	    	
	    	return retorno;
    	
		}catch (Exception e) {
	        this.logger.error(e);
	    	throw new ECARException("erro.hibernateException");
		}
    	
    }
    
    /**
     * Retorna um objeto EfItemEstContaEfiec caso exista, do contrário devolve null
     * @author carlos
     * @param conta
     * @param exercicio
     * @return EfItemEstContaEfiec
     * @throws ECARException
     * @throws HibernateException
     */
    public EfItemEstContaEfiec getEfItemEstContaEfiec(String conta, ExercicioExe exercicio) throws ECARException, HibernateException {
    	
    	EfItemEstContaEfiec retorno = null;
    	
    	try
    	{
    		Criteria crits = session.createCriteria(EfItemEstContaEfiec.class);
    		crits.add(Restrictions.eq("contaSistemaOrcEfiec", conta));
    		crits.add(Restrictions.eq("exercicioExe.codExe", exercicio.getCodExe()));
    		
    		List<EfItemEstContaEfiec> lista = (List<EfItemEstContaEfiec>)crits.list();
    		
    		if(lista.size() > 0) {
    			retorno = lista.get(0);
    		}
    		
    	}catch (Exception e) {
	        this.logger.error(e);
	    	throw new ECARException("erro.hibernateException");
    	}
    	
    	return retorno;
    }
    
}