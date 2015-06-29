/*
 * Criado em 19/05/2006
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.EstAtribTipoAcompEata;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SituacaoSit;
import ecar.pojo.TipoAcompFuncAcompPK;
import ecar.pojo.TipoAcompFuncAcompTafc;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/**
 * @author evandro
 *
 */
public class TipoAcompanhamentoDao
        extends Dao {

    /**
     *
     */
    public TipoAcompanhamentoDao() {
        super();
    }

    /**
     * Construtor. Chama o Session factory do Hibernate
     * @param request
     */
    public TipoAcompanhamentoDao(HttpServletRequest request) {
        super();
        this.request = request;
    }

    /**
     * Obtem os tipos de acompanhamentos ativos ordenados pela sequencia de apresenta��o
     *
     * @return List de TipoAcompanhamentoTa
     * @throws ECARException
     */
    public List getTipoAcompanhamentoAtivosBySeqApresentacao()
            throws ECARException {
        List retorno = new ArrayList();

        TipoAcompanhamentoTa ta = new TipoAcompanhamentoTa();

        ta.setIndAtivoTa("S");

        retorno =
                this.pesquisar(ta,
                new String[]{"seqApresentacaoTa", "asc"});

        return retorno;
    }

    /**
     * M�todo que realiza o Set no TipoAcompanhamentoTa por request de acordo
     * com os dados preenchidos
     * @param tipoAcomp
     * @param request
     * @param usarGetParamStr
     * @throws NumberFormatException
     * @throws ECARException
     */
    public void setTipoAcompanhamento(HttpServletRequest request, TipoAcompanhamentoTa tipoAcomp,
            boolean usarGetParamStr)
            throws NumberFormatException, ECARException {
        if (usarGetParamStr) {
            tipoAcomp.setDescricaoTa(Pagina.getParamStr(request, "descricaoTa").trim());

            //Caso seja vazio, setar IndMonitoramentoTa como 'N'. Isso e preciso, porque o check box
            //da interface grafica nao retorna valor para 'S'.  
            if (!Pagina.getParamStr(request, "indMonitoramentoTa").trim().equals("")) {
                tipoAcomp.setIndMonitoramentoTa(Pagina.getParamStr(request, "indMonitoramentoTa").trim());
            } else {
                tipoAcomp.setIndMonitoramentoTa(Pagina.NAO);
            }

            if (!Pagina.getParamStr(request, "indNaoMonitoramentoTa").trim().equals("")) {
                tipoAcomp.setIndNaoMonitoramentoTa(Pagina.getParamStr(request, "indNaoMonitoramentoTa").trim());
            } else {
                tipoAcomp.setIndNaoMonitoramentoTa(Pagina.NAO);
            }

            if (Pagina.getParam(request, "indSepararOrgaoTa") != null && !Pagina.getParamStr(request, "indSepararOrgaoTa").trim().equals("")) {
                tipoAcomp.setIndSepararOrgaoTa(Pagina.getParamStr(request, "indSepararOrgaoTa").trim());
            } else {
            	tipoAcomp.setIndSepararOrgaoTa(Pagina.NAO);
            }
            
            if (!Pagina.getParamStr(request, "indGerarArquivoTa").trim().equals("")) {
                tipoAcomp.setIndGerarArquivoTa(Pagina.getParamStr(request, "indGerarArquivoTa").trim());
            } else {
                tipoAcomp.setIndGerarArquivoTa(Pagina.NAO);
            }

            if (!Pagina.getParamStr(request, "indAtivoTa").trim().equals("")) {
                tipoAcomp.setIndAtivoTa(Pagina.getParamStr(request, "indAtivoTa").trim());
            } else {
                tipoAcomp.setIndAtivoTa(Pagina.NAO);
            }

            if (!Pagina.getParamStr(request, "indLiberarAcompTa").trim().equals("")) {
                tipoAcomp.setIndLiberarAcompTa(Pagina.getParamStr(request, "indLiberarAcompTa").trim());
            } else {
                tipoAcomp.setIndLiberarAcompTa(Pagina.NAO);
            }

            if (!Pagina.getParamStr(request, "indLiberarParecerTa").trim().equals("")) {
                tipoAcomp.setIndLiberarParecerTa(Pagina.getParamStr(request, "indLiberarParecerTa").trim());
            } else {
                tipoAcomp.setIndLiberarParecerTa(Pagina.NAO);
            }
        } else {
            tipoAcomp.setDescricaoTa(Pagina.getParam(request, "descricaoTa"));
            tipoAcomp.setIndMonitoramentoTa(Pagina.getParam(request, "indMonitoramentoTa"));
            tipoAcomp.setIndNaoMonitoramentoTa(Pagina.getParam(request, "indNaoMonitoramentoTa"));           
           	tipoAcomp.setIndSepararOrgaoTa(Pagina.getParam(request, "indSepararOrgaoTa"));
            	            
            tipoAcomp.setIndGerarArquivoTa(Pagina.getParamStr(request, "indGerarArquivoTa").trim());
            tipoAcomp.setIndAtivoTa(Pagina.getParam(request, "indAtivoTa"));
            tipoAcomp.setIndLiberarAcompTa(Pagina.getParam(request, "indLiberarAcompTa"));
            tipoAcomp.setIndLiberarParecerTa(Pagina.getParam(request, "indLiberarParecerTa"));
        }

        if (Pagina.getParam(request, "seqApresentacaoTa") != null) {
            tipoAcomp.setSeqApresentacaoTa(Long.valueOf(Pagina.getParamLong(request, "seqApresentacaoTa")));
        } else {
            tipoAcomp.setSeqApresentacaoTa(null);
        }

        if (Pagina.getParam(request, "estruturaEtt") != null) {
            EstruturaEtt menorEtt =
                    (EstruturaEtt) new EstruturaDao(request).buscar(EstruturaEtt.class,
                    Long.valueOf(Pagina.getParamStr(request,
                    "estruturaEtt")));
            tipoAcomp.setEstruturaEtt(menorEtt);
        } else {
            tipoAcomp.setEstruturaEtt(null);
        }

        if (Pagina.getParam(request, "estruturaEttAcomp") != null) {
            EstruturaEtt estruturaEttAcomp =
                    (EstruturaEtt) new EstruturaDao(request).buscar(EstruturaEtt.class,
                    Long.valueOf(Pagina.getParamStr(request,
                    "estruturaEttAcomp")));
            tipoAcomp.setEstruturaNivelGeracaoTa(estruturaEttAcomp);
        } else {
            tipoAcomp.setEstruturaNivelGeracaoTa(null);
        }

        setSituacaoSits(tipoAcomp, request);

        setNiveisPlanejamento(tipoAcomp, request);
    }

    /**
     * M�todo que realiza o Set de SisAtributoSatbs no TipoAcompanhamentoTa por
     * request de acordo com os dados preenchidos
     * @param tipoAcomp
     * @param request
     * @throws NumberFormatException
     * @throws ECARException
     */
    public void setNiveisPlanejamento(TipoAcompanhamentoTa tipoAcomp, HttpServletRequest request)
            throws NumberFormatException, ECARException {
        String[] niveis = request.getParameterValues("nivel");
        tipoAcomp.setSisAtributoSatbs(new HashSet());

        List lista = new ArrayList();

        if (niveis != null) {
            for (int i = 0; i < niveis.length; i++) {
                SisAtributoSatb nivel =
                        (SisAtributoSatb) this.buscar(SisAtributoSatb.class,
                        Long.valueOf(niveis[i]));
                lista.add(nivel);
            }
        }

        tipoAcomp.getSisAtributoSatbs().addAll(lista);
    }

    /**
     * M�todo que realiza o Set de TipoAcompanhamentoSituacaoTasit
     * request de acordo com os dados preenchidos
     * @param tipoAcomp
     * @param request
     * @throws NumberFormatException
     * @throws ECARException
     */
    public void setSituacaoSits(TipoAcompanhamentoTa tipoAcomp, HttpServletRequest request)
            throws NumberFormatException, ECARException {
        String[] situacoes = request.getParameterValues("situacao");
        tipoAcomp.setSituacaoSits(new HashSet());

        List lista = new ArrayList();

        if (situacoes != null) {
            for (int i = 0; i < situacoes.length; i++) {
                SituacaoSit situacaoSit = (SituacaoSit) this.buscar(SituacaoSit.class,
                        Long.valueOf(situacoes[i]));
                lista.add(situacaoSit);
            }
        }

        tipoAcomp.getSituacaoSits().addAll(lista);
    }

    /**
     * M�todo que realiza o Set de TipoAcompFuncAcompTafcs no TipoAcompanhamentoTa
     * por request de acordo com os dados preenchidos, utilizado somente na Pesquisa
     * @param tipoAcomp
     * @param request
     * @throws NumberFormatException
     * @throws ECARException
     */
    public void setTpFuncAcompanhamento(TipoAcompanhamentoTa tipoAcomp, HttpServletRequest request)
            throws NumberFormatException, ECARException {
        TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
        List lFuncAcomp = tipoFuncAcompDao.getTipoFuncAcompEmitePosicao();
        List lista = new ArrayList();
        tipoAcomp.setTipoAcompFuncAcompTafcs(new HashSet());

        for (Iterator itFuncAcomp = lFuncAcomp.iterator(); itFuncAcomp.hasNext();) {
            TipoFuncAcompTpfa funcao = (TipoFuncAcompTpfa) itFuncAcomp.next();

            /*
            if ("S".equals(Pagina.getParamStr(request, "indObrigatorio" + funcao.getCodTpfa())) ||
            "S".equals(Pagina.getParamStr(request, "indOpcional" + funcao.getCodTpfa()))) {
             */
            if ("OBR".equals(Pagina.getParamStr(request, "indObrigatorioOpcional" + funcao.getCodTpfa())) ||
                    "OPC".equals(Pagina.getParamStr(request, "indObrigatorioOpcional" + funcao.getCodTpfa()))) {
                TipoAcompFuncAcompPK chave = new TipoAcompFuncAcompPK();
                chave.setCodTpfa(funcao.getCodTpfa());

                TipoAcompFuncAcompTafc tafc = new TipoAcompFuncAcompTafc();
                tafc.setComp_id(chave);

                /*
                if ("S".equals(Pagina.getParamStr(request, "indObrigatorio" + funcao.getCodTpfa()))) {
                tafc.setIndObrigatorio("S");
                } else {
                tafc.setIndObrigatorio("N");
                }
                if ("S".equals(Pagina.getParamStr(request, "indOpcional" + funcao.getCodTpfa()))) {
                tafc.setIndOpcional("S");
                } else {
                tafc.setIndOpcional("N");
                }
                 */
                if ("OBR".equals(Pagina.getParamStr(request, "indObrigatorioOpcional" + funcao.getCodTpfa()))) {
                    tafc.setIndObrigatorio("S");
                } else {
                    tafc.setIndObrigatorio("N");
                }

                if ("OPC".equals(Pagina.getParamStr(request, "indObrigatorioOpcional" + funcao.getCodTpfa()))) {
                    tafc.setIndOpcional("S");
                } else {
                    tafc.setIndOpcional("N");
                }

                if (!"".equals(Pagina.getParamStr(request, "indRegistroPosicao" + funcao.getCodTpfa()))) {
                    tafc.setIndRegistroPosicaoTafc(Pagina.getParamStr(request,
                            "indRegistroPosicao" + funcao.getCodTpfa()));
                } else {
                    tafc.setIndRegistroPosicaoTafc(null);
                }

                lista.add(tafc);
            }
        }

        tipoAcomp.getTipoAcompFuncAcompTafcs().addAll(lista);
    }

    /**
     * Retorna a lista de TipoAcompFuncAcompTafcs de acordo com as op��es
     * selecionadas
     * @param tipoAcomp
     * @param request
     * @return
     * @throws NumberFormatException
     * @throws ECARException
     */
    public List getTpFuncAcompanhamento(TipoAcompanhamentoTa tipoAcomp, HttpServletRequest request)
            throws NumberFormatException, ECARException {
        TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
        List lFuncAcomp = tipoFuncAcompDao.getTipoFuncAcompEmitePosicao();
        List lista = new ArrayList();

        for (Iterator itFuncAcomp = lFuncAcomp.iterator(); itFuncAcomp.hasNext();) {
            TipoFuncAcompTpfa funcao = (TipoFuncAcompTpfa) itFuncAcomp.next();

            /*
            if ("S".equals(Pagina.getParamStr(request, "indObrigatorio" + funcao.getCodTpfa())) ||
            "S".equals(Pagina.getParamStr(request, "indOpcional" + funcao.getCodTpfa()))) {
             */
            TipoAcompFuncAcompPK chave = new TipoAcompFuncAcompPK();
            chave.setCodTpfa(funcao.getCodTpfa());

            TipoAcompFuncAcompTafc tafc = new TipoAcompFuncAcompTafc();
            tafc.setComp_id(chave);

            if ("OBR".equals(Pagina.getParamStr(request, "indObrigatorioOpcional" + funcao.getCodTpfa()))) {
                tafc.setIndObrigatorio("S");
            } else {
                tafc.setIndObrigatorio("N");
            }

            if ("OPC".equals(Pagina.getParamStr(request, "indObrigatorioOpcional" + funcao.getCodTpfa()))) {
                tafc.setIndOpcional("S");
            } else {
                tafc.setIndOpcional("N");
            }
           
            if ("OBR".equals(Pagina.getParamStr(request, "indObrigatorioOpcional" + funcao.getCodTpfa())) ||
                    "OPC".equals(Pagina.getParamStr(request, "indObrigatorioOpcional" + funcao.getCodTpfa()))) {
                if (!"".equals(Pagina.getParamStr(request, "indRegistroPosicao" + funcao.getCodTpfa()))) {
                    tafc.setIndRegistroPosicaoTafc(Pagina.getParamStr(request,
                            "indRegistroPosicao" + funcao.getCodTpfa()));
                } else {
                    tafc.setIndRegistroPosicaoTafc(null);
                }
            }

            lista.add(tafc);
        }

        return lista;
    }

    /**
     * Lista os niveis de planejamento
     * @param request
     * @return List
     * @throws NumberFormatException
     * @throws ECARException
     */
    public List getNiveisPlanejamento(HttpServletRequest request)
            throws NumberFormatException, ECARException {
        String[] niveis = request.getParameterValues("nivel");
        List lista = new ArrayList();

        for (int i = 0; i < niveis.length; i++) {
            SisAtributoSatb nivel = (SisAtributoSatb) this.buscar(SisAtributoSatb.class,
                    Long.valueOf(niveis[i]));
            lista.add(nivel);
        }

        return lista;
    }

    /**
     * Verifica depois exclui
     * @param tipoAcomp
     * @throws ECARException
     */
    public void excluir(TipoAcompanhamentoTa tipoAcomp)
            throws ECARException {
        Transaction tx = null;

        try {
            boolean excluir = true;

            if (contar(tipoAcomp.getAcompReferenciaArefs()) > 0) {
                excluir = false;

                AcompReferenciaAref ocorrencia =
                        (AcompReferenciaAref) new ArrayList(tipoAcomp.getAcompReferenciaArefs()).get(0);
                throw new ECARException("tipoAcompanhamento.exclusao.erro.existeAref", null,
                        new String[]{ocorrencia.getNomeAref()});
            }

            if (contar(tipoAcomp.getEstAtribTipoAcompEatas()) > 0) {
                excluir = false;

                EstAtribTipoAcompEata estAtribTipoAcompEata =
                        (EstAtribTipoAcompEata) new ArrayList(tipoAcomp.getEstAtribTipoAcompEatas()).get(0);
                throw new ECARException("tipoAcompanhamento.exclusao.erro.existeEata", null, new String[]{});
            }

            if (excluir) {
                ArrayList objetos = new ArrayList();
                super.inicializarLogBean();

                tx = session.beginTransaction();

                for (Iterator itEXC = tipoAcomp.getTipoAcompFuncAcompTafcs().iterator(); itEXC.hasNext();) {
                    TipoAcompFuncAcompTafc tafc = (TipoAcompFuncAcompTafc) itEXC.next();
                    session.delete(tafc);
                    objetos.add(tafc);
                }

                // Deleta as ocorrencias de Estrutura Atributo Tipo Acompanhamento relacionadas ao TipoAcompanhamento.
//    			for (Iterator itEXC = tipoAcomp.getEstAtribTipoAcompEatas().iterator(); itEXC.hasNext();) {
//    	        	EstAtribTipoAcompEata eata = (EstAtribTipoAcompEata) itEXC.next();
//    	        	session.delete(eata);
//    	            objetos.add(eata);
//    	        }
                session.delete(tipoAcomp);
                objetos.add(tipoAcomp);

                tx.commit();

                if (super.logBean != null) {
                    super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
                    super.logBean.setOperacao("EXC");

                    for (Iterator itObj = objetos.iterator(); itObj.hasNext();) {
                        super.logBean.setObj(itObj.next());
                        super.loggerAuditoria.info(logBean.toString());
                    }
                }
            }
        } catch (HibernateException he) {
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (HibernateException r) {
                    this.logger.error(r);
                    throw new ECARException("erro.hibernateException");
                }
            }

            this.logger.error(he);
            throw new ECARException("erro.hibernateException");
        }
    }

    /**
     * Obtem os tipos de fun��o de acompanhamento de um tipo de acompanhamento
     *
     * @param ta TipoAcompanhamentoTa ta
     * @return List de TipoFuncAcompTpfa
     * @throws ECARException
     */
    public List getTipoFuncaoAcompanhamento(TipoAcompanhamentoTa ta)
            throws ECARException {
        List retorno = new ArrayList();

        if (ta.getTipoAcompFuncAcompTafcs() != null) {
            for (Iterator it = ta.getTipoAcompFuncAcompTafcs().iterator(); it.hasNext();) {
                TipoAcompFuncAcompTafc tafc = (TipoAcompFuncAcompTafc) it.next();
                if (Pagina.SIM.equals(tafc.getIndObrigatorio()) || Pagina.SIM.equals(tafc.getIndOpcional())){
                	retorno.add(tafc.getTipoFuncAcompTpfa());
                }
            }
        }

        return retorno;
    }

    /**
     * Verifica depois salva
     * @param tipoAcomp
     * @param listaTafc
     * @throws ECARException
     */
    public void salvar(TipoAcompanhamentoTa tipoAcomp, List listaTafc)
            throws ECARException {
        Transaction tx = null;

        try {
            ArrayList objetos = new ArrayList();
            super.inicializarLogBean();

            tx = session.beginTransaction();

            session.save(tipoAcomp);
            objetos.add(tipoAcomp);

            for (Iterator it = listaTafc.iterator(); it.hasNext();) {
                TipoAcompFuncAcompTafc tafc = (TipoAcompFuncAcompTafc) it.next();
                tafc.getComp_id().setCodTa(tipoAcomp.getCodTa());
                session.save(tafc);
                objetos.add(tafc);
            }

            tx.commit();

            if (super.logBean != null) {
                super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
                super.logBean.setOperacao("INC");

                for (Iterator itObj = objetos.iterator(); itObj.hasNext();) {
                    super.logBean.setObj(itObj.next());
                    super.loggerAuditoria.info(logBean.toString());
                }
            }
        } catch (HibernateException he) {
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (HibernateException r) {
                    this.logger.error(r);
                    throw new ECARException("erro.hibernateException");
                }
            }

            this.logger.error(he);
            throw new ECARException("erro.hibernateException");
        }
    }

    /**
     * Verifica depois altera
     * @param tipoAcomp
     * @param listaTafc
     * @throws ECARException
     */
    public void alterar(TipoAcompanhamentoTa tipoAcomp, List listaTafc)
            throws ECARException {
        Transaction tx = null;

        try {
            //Verifica se a op��o "Exige liberar Parecer" foi desmarcada e altera o status de todos os pareceres gravados para "Liberados". 
            if (tipoAcomp.getIndLiberarParecerTa().equals("N")) {
//                String hqlConsulta =
//                        "select distinct codArel from AcompRelatorioArel arel " +
//                        "where arel.acompReferenciaItemAri.acompReferenciaAref.tipoAcompanhamentoTa.codTa = :codAcomp";
//                
//                String hqlConsulta = 
//                		"select distinct arel.cod_arel "+
//                		"from tb_acomp_relatorio_arel arel "+
//                		"inner join tb_acomp_referencia_item_ari ari on ari.cod_ari = arel.cod_ari "+
//                		"inner join tb_acomp_referencia_aref aref on aref.cod_aref = ari.cod_aref "+
//                		"where aref.cod_ta = :codAcomp";
//                
//                
//                SQLQuery q = session.createSQLQuery(hqlConsulta.toString());
//                q.setLong("codAcomp",
//                        tipoAcomp.getCodTa());
//
//                List itensArel = q.list();
//
//                if ((itensArel != null) && (itensArel.size() > 0)) {
//                    String hql =
//                            "update AcompRelatorioArel arel set arel.indLiberadoArel ='S' " +
//                            "where arel.indLiberadoArel ='N' " +
//                            "and arel.acompReferenciaItemAri.acompReferenciaAref.tipoAcompanhamentoTa.codTa = :codAcomp";
                	
                	String hql = 
                			"update tb_acomp_relatorio_arel "+
                			"set ind_liberado_arel = 'S' "+
                			"where ind_liberado_arel = 'N' " +
                			"and cod_arel in(" +
                			"select distinct arel.cod_arel "+
                    		"from tb_acomp_relatorio_arel arel "+
                    		"inner join tb_acomp_referencia_item_ari ari on ari.cod_ari = arel.cod_ari "+
                    		"inner join tb_acomp_referencia_aref aref on aref.cod_aref = ari.cod_aref "+
                    		"where aref.cod_ta = :codAcomp)";
                    
                    SQLQuery query = session.createSQLQuery(hql);
                    query.setLong("codAcomp",
                            tipoAcomp.getCodTa());
                    query.executeUpdate();
                //}
            }

            ArrayList objetos = new ArrayList();
            super.inicializarLogBean();

            tx = session.beginTransaction();

            session.update(tipoAcomp);
            objetos.add(tipoAcomp);

            for (Iterator itEXC = tipoAcomp.getTipoAcompFuncAcompTafcs().iterator(); itEXC.hasNext();) {
                TipoAcompFuncAcompTafc tafc = (TipoAcompFuncAcompTafc) itEXC.next();
                session.delete(tafc);
                objetos.add(tafc);
            }

            for (Iterator itTafc = listaTafc.iterator(); itTafc.hasNext();) {
                TipoAcompFuncAcompTafc tafc = (TipoAcompFuncAcompTafc) itTafc.next();
                tafc.getComp_id().setCodTa(tipoAcomp.getCodTa());
                session.save(tafc);
                objetos.add(tafc);
            }

            tx.commit();

            if (super.logBean != null) {
                super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
                super.logBean.setOperacao("INC_ALT_EXC");

                for (Iterator itObj = objetos.iterator(); itObj.hasNext();) {
                    super.logBean.setObj(itObj.next());
                    super.loggerAuditoria.info(logBean.toString());
                }
            }
        } catch (HibernateException he) {
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (HibernateException r) {
                    this.logger.error(r);
                    throw new ECARException("erro.hibernateException");
                }
            }

            this.logger.error(he);
            throw new ECARException("erro.hibernateException");
        }
    }

    /**
     * Pesquisa ...
     * @param tipoAcomp
     * @return lista
     * @throws ECARException
     */
    public List pesquisar(TipoAcompanhamentoTa tipoAcomp)
            throws ECARException {
        Set setResultadoParcial = new HashSet();
        Set setResultadoFinal = new HashSet();
        Set setResultadoSituacao = new HashSet();

        /* REALIZA A PESQUISA PELO OBJETO TipoAcompanhamentoTa */
        List pesquisa = super.pesquisar(tipoAcomp, null);

        /*
         * -- por Thaise
         * Corre��o na tela de altera��o de Configura��o Sistema > Config. Estrutura > Tipo Acompanhamento
         * Est� trazendo o tipoAcmp como null
         **/

        /* VERIFICA SE O TipoAcompanhamentoTa POSSUI UM DOS niveis SELECIONADOS */
        if ((tipoAcomp != null) &&
                (tipoAcomp.getSisAtributoSatbs() != null) &&
                (tipoAcomp.getSisAtributoSatbs().size() > 0)) {
            for (Iterator itPes = pesquisa.iterator(); itPes.hasNext();) {
                TipoAcompanhamentoTa tipoAcompPes = (TipoAcompanhamentoTa) itPes.next();

                for (Iterator itSisAt = tipoAcomp.getSisAtributoSatbs().iterator(); itSisAt.hasNext();) {
                    SisAtributoSatb sisAt = (SisAtributoSatb) itSisAt.next();

                    if (tipoAcompPes.getSisAtributoSatbs().contains(sisAt)) {
                        setResultadoParcial.add(tipoAcompPes);
                    }
                }
            }
        } else {
            /* N�o selecionou nenhum nivel para pesquisa adicionar todo o resultado */
            setResultadoParcial.addAll(pesquisa);
        }

        /* VERIFICA SE O TipoAcompanhamentoTa POSSUI ALGUMA SITUA��O SELECIONADA */
        if ((tipoAcomp.getSituacaoSits() != null) && (tipoAcomp.getSituacaoSits().size() > 0)) {
            for (Iterator itPes = setResultadoParcial.iterator(); itPes.hasNext();) {
                TipoAcompanhamentoTa tipoAcompPes = (TipoAcompanhamentoTa) itPes.next();

                for (Iterator itSit = tipoAcomp.getSituacaoSits().iterator(); itSit.hasNext();) {
                    SituacaoSit situacaoSit = (SituacaoSit) itSit.next();

                    if (tipoAcompPes.getSituacaoSits().contains(situacaoSit)) {
                        setResultadoSituacao.add(tipoAcompPes);
                    }
                }
            }

            setResultadoParcial = setResultadoSituacao;
        }

        /* VERIFICA SE O TipoAcompanhamentoTa POSSUI A fun��o SELECIONADA */
        if ((tipoAcomp.getTipoAcompFuncAcompTafcs() != null) &&
                (tipoAcomp.getTipoAcompFuncAcompTafcs().size() > 0)) {
            for (Iterator itRes = setResultadoParcial.iterator(); itRes.hasNext();) {
                TipoAcompanhamentoTa tipoAcompRes = (TipoAcompanhamentoTa) itRes.next();

                //verificar se tem fun��o
                if (tipoAcompRes.getTipoAcompFuncAcompTafcs().size() > 0) {
                    for (Iterator itTaFuncRes = tipoAcompRes.getTipoAcompFuncAcompTafcs().iterator();
                            itTaFuncRes.hasNext();) {
                        TipoAcompFuncAcompTafc taFuncaoRes = (TipoAcompFuncAcompTafc) itTaFuncRes.next();

                        for (Iterator itTaFuncPesq = tipoAcomp.getTipoAcompFuncAcompTafcs().iterator();
                                itTaFuncPesq.hasNext();) {
                            TipoAcompFuncAcompTafc taFuncaoPes = (TipoAcompFuncAcompTafc) itTaFuncPesq.next();

                            //batendo pesquisa com resultado, adiciona em resultado final
                            if (taFuncaoRes.getTipoFuncAcompTpfa().getCodTpfa().equals(taFuncaoPes.getComp_id().getCodTpfa()) &&
                                    taFuncaoRes.getIndObrigatorio().equals(taFuncaoPes.getIndObrigatorio()) &&
                                    taFuncaoRes.getIndOpcional().equals(taFuncaoPes.getIndOpcional())) {
                                setResultadoFinal.add(tipoAcompRes);
                            }
                        }
                    }
                }
            }
        } else {
            setResultadoFinal.addAll(setResultadoParcial);
        }

        List resultado = new ArrayList();
        resultado.addAll(setResultadoFinal);

        /* Ordenar por codTa */
        Collections.sort(resultado,
                new Comparator() {

                    public int compare(Object o1, Object o2) {
                        return ((TipoAcompanhamentoTa) o1).getCodTa().compareTo(((TipoAcompanhamentoTa) o2).getCodTa());
                    }
                });

        return resultado;
    }

    /**
     * Obtem os tipos de acompanhamentos ativos ordenados pela sequencia de apresenta��o que
     * possuem acompanhamento (Aref)
     *
     * @return List de TipoAcompanhamentoTa
     * @throws ECARException
     */
    public List getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao()
            throws ECARException {
        try {
            return this.getSession().createQuery("select distinct ta from TipoAcompanhamentoTa ta " +
                    "join ta.acompReferenciaArefs " + "where ta.indAtivoTa = :ativo " +
                    "order by ta.seqApresentacaoTa").setString("ativo", Dominios.ATIVO).list();
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException(e);
        }
    }

    /**
     * @author Robson
     * @return List
     * @throws ECARException
     * @since  22/11/2007
     */
    public List getTipoAcompanhamentoAtivosByDescricao()
            throws ECARException {
        TipoAcompanhamentoTa ta = new TipoAcompanhamentoTa();

        ta.setIndAtivoTa(Dominios.ATIVO);

        return this.pesquisar(ta,
                new String[]{"descricaoTa", "asc"});
    }

    /**
     * @param seguranca
     * @author Robson
     * @return List<TipoAcompanhamentoTa>
     * @throws ECARException
     * @since  27/11/2007
     */
    public List getTipoAcompanhamentoComAcesso(SegurancaECAR seguranca)
            throws ECARException {
        try {
            return this.getSession().createQuery("select distinct ta " + "from TipoAcompanhamentoTa ta " +
                    "join ta.tipoAcompGrpAcessos taa " + "where taa.acessoInclusao = :inclusao " +
                    "and ta.indAtivoTa = :ativo " + "and taa.sisAtributoSatb in (:gruposUsuario) " +
                    "order by ta.descricaoTa").setString("inclusao", Dominios.COM_ACESSO_INCLUSAO).setString("ativo", Dominios.ATIVO).setParameterList("gruposUsuario",
                    seguranca.getGruposAcesso()).list();
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException(e);
        }
    }

    /**
     *
     * @param seguranca
     * @return
     * @throws ECARException
     */
    public List getOrgaosAcessoUsuario(SegurancaECAR seguranca)
            throws ECARException {
        ValidaPermissao validaPermissao = new ValidaPermissao();

        List secretarias = new ArrayList();

        if (validaPermissao.permissaoAcessoSecretariasUsuario(
                seguranca.getGruposAcesso(),
                Dominios.SEPARAR_ORGAO_TODOS)) {
            secretarias.addAll(new OrgaoDao(null).listar(
                    OrgaoOrg.class,
                    new String[]{"siglaOrg", "asc"}));
        } else if (validaPermissao.permissaoAcessoSecretariasUsuario(
                seguranca.getGruposAcesso(),
                Dominios.SEPARAR_ORGAO_USUARIO)) {
            secretarias.addAll(seguranca.getUsuario().getOrgaoOrgs());
        } else if (validaPermissao.permissaoAcessoSecretariasUsuario(
                seguranca.getGruposAcesso(),
                Dominios.SEPARAR_ORGAO_RESPONSABILIDADE)) {
            List listaCods = new ArrayList();

            for (Iterator it = seguranca.getGruposAcesso().iterator(); it.hasNext();) {
                SisAtributoSatb satb = (SisAtributoSatb) it.next();
                listaCods.add(satb.getCodSatb());
            }

            return this.getSession().createQuery("select orgao " + "from OrgaoOrg orgao " +
                    "join orgao.itemEstruturaIettsByCodOrgaoResponsavel1Iett iett " +
                    "join iett.itemEstrutUsuarioIettusesByCodIett iettus " +
                    "where iettus.usuarioUsu.codUsu in (:lista) " +
                    "and orgao.indAtivoOrg = :ativoOrg " + "and iett.indAtivoIett = :ativoIett " +
                    "and iettus.indLeituraParecerIettus = :leituraIettus " +
                    "order by orgao.siglaOrg ").setParameterList("lista", listaCods).setString("ativoOrg", Dominios.ATIVO).setString("ativoIett", Dominios.ATIVO).setString("indLeituraParecerIettus", Dominios.COM_ACESSO_LEITURA).list();
        }

        return secretarias;
    }
    
    /**
     * Verifica se existe alguma aba configurada para o tipo de acompanhamento passado como parametro .<br>
     *
     * @param ta
     * @param gruposUsuario
     * @author N/C
     * @since N/C
     * @version N/C
     * @return boolean existe
     * @throws ECARException
     */
   public List getOrgaosAcessoUsuarioPorTipoAcompanhamento(SegurancaECAR seguranca, TipoAcompanhamentoTa ta, boolean apenasOrgaosAtivos)
           throws ECARException {
       ValidaPermissao validaPermissao = new ValidaPermissao();

       List secretarias = new ArrayList();
       OrgaoDao orgDao = new OrgaoDao(request);
   		// se � para gerar apenas para os seus �rg�os
		if(validaPermissao.permissaoAcessoReferenciaSeusOrgaos(ta, seguranca.getGruposAcesso())) {
			 secretarias.addAll(orgDao.getListaOrgaosUsuario(seguranca.getUsuario(), apenasOrgaosAtivos));
		} else {
			// se for para gerar para todos os �rg�os
			secretarias.addAll(orgDao.getListaOrgaos(apenasOrgaosAtivos));
		}
		

       return secretarias;
   }
   
   
   
   
   /**
    * Verifica se existe alguma aba configurada para o tipo de acompanhamento passado como parametro .<br>
    *
    * @param ta
    * @param gruposUsuario
    * @author N/C
    * @since N/C
    * @version N/C
    * @return boolean existe
    * @throws ECARException
    */
  public List getOrgaosAcessoUsuarioPorTipoAcompanhamentoPeloUsuario(Set grupoUsuarios, UsuarioUsu usuario, TipoAcompanhamentoTa ta, boolean apenasOrgaosAtivos)
          throws ECARException {
      ValidaPermissao validaPermissao = new ValidaPermissao();

      List secretarias = new ArrayList();
      OrgaoDao orgDao = new OrgaoDao(request);
  		// se � para gerar apenas para os seus �rg�os
		if(validaPermissao.permissaoAcessoReferenciaSeusOrgaos(ta, grupoUsuarios)) {
			 secretarias.addAll(orgDao.getListaOrgaosUsuario(usuario, apenasOrgaosAtivos));
		} else {
			// se for para gerar para todos os �rg�os
			secretarias.addAll(orgDao.getListaOrgaos(apenasOrgaosAtivos));
		}
		

      return secretarias;
  }


    /**
     * Verifica se existe alguma aba configurada para o tipo de acompanhamento passado como parametro .<br>
     *
     * @param ta
     * @param gruposUsuario
     * @author N/C
     * @since N/C
     * @version N/C
     * @return boolean existe
     * @throws ECARException
     */
    public boolean existeAbaConfiguradaTipoAcompanhamento(TipoAcompanhamentoTa ta, Set gruposUsuario)
            throws ECARException {
        boolean existe = true;
        List listAbasComAcesso = null;

        AbaDao abaDao = new AbaDao(this.request);

        //vai buscar as abas com acesso
        if ((ta != null) && (gruposUsuario != null)) {
            listAbasComAcesso = abaDao.getListaAbasComAcesso(ta, gruposUsuario);
        }

        // resumo hoje � fixo, mas quando estiver configuravel deve ser alterado o valor
        if ((listAbasComAcesso != null) && (listAbasComAcesso.size() < 1)) {
            existe = false;
        }

        return existe;
    }
}
