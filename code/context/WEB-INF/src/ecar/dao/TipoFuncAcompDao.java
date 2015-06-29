/*
 * Criado em 28/10/2004
 *
 */
package ecar.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.collection.PersistentSet;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import comum.database.Dao;
import comum.util.Data;

import ecar.bean.OrdenacaoDataTpfa;
import ecar.bean.OrdenacaoTpfaEstrutura;
import ecar.exception.ECARException;
import ecar.permissao.ControlePermissao;
import ecar.pojo.AcompRefItemLimitesArli;
import ecar.pojo.AcompRefLimitesArl;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.EstrutTpFuncAcmpEtttfa;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.TfuncacompConfigmailTfacfgm;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.TipoFuncAcompTpfaPermiteAlterar;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/**
 * @author felipev
 *
 */
public class TipoFuncAcompDao
    extends Dao
{
    /**
     *
     */
    public TipoFuncAcompDao(  )
    {
        super(  );
    }

    /**
    * Construtor. Chama o Session factory do Hibernate
     * @param request
     */
    public TipoFuncAcompDao( HttpServletRequest request )
    {
        super(  );
        this.request = request;
    }

    /**
     * Salva ...
     * @param tipoFuncAcomp
     * @param lstTFuncAcompCfgm
     * @throws ECARException
     */
    public void salvar( TipoFuncAcompTpfa tipoFuncAcomp, List lstTFuncAcompCfgm )
                throws ECARException
    {
        Transaction tx = null;
        ArrayList objetos = new ArrayList(  );

        super.inicializarLogBean(  );

        try
        {
            tx = session.beginTransaction(  );

            session.save( tipoFuncAcomp );
            objetos.add( tipoFuncAcomp );

            // inserindo os TfuncacompConfigmailTfacfgm
            for ( Iterator it = lstTFuncAcompCfgm.iterator(  ); it.hasNext(  ); )
            {
                TfuncacompConfigmailTfacfgm tFuncAcompCfgm = (TfuncacompConfigmailTfacfgm) it.next(  );

                tFuncAcompCfgm.getComp_id(  ).setCodTpfa( tipoFuncAcomp.getCodTpfa(  ) );

                session.save( tFuncAcompCfgm );
                objetos.add( tFuncAcompCfgm );
            }

            tx.commit(  );

            if ( super.logBean != null )
            {
                super.logBean.setCodigoTransacao( Data.getHoraAtual( false ) );
                super.logBean.setOperacao( "INC" );

                for ( Iterator itObj = objetos.iterator(  ); itObj.hasNext(  ); )
                {
                    super.logBean.setObj( itObj.next(  ) );
                    super.loggerAuditoria.info( logBean.toString(  ) );
                }
            }
        } catch ( HibernateException e )
        {
            if ( tx != null )
            {
                try
                {
                    tx.rollback(  );
                } catch ( HibernateException r )
                {
                    this.logger.error( r );
                    throw new ECARException( "erro.hibernateException" );
                }
            }

            this.logger.error( e );
            throw new ECARException( "erro.hibernateException" );
        }
    } // fim salvar

    /**
     * Altera
     * @param tipoFuncAcomp
     * @param lstTFuncAcompCfgm
     * @throws ECARException
     */
    public void alterar( TipoFuncAcompTpfa tipoFuncAcomp, List lstTFuncAcompCfgm )
                 throws ECARException
    {
        /* --
         * Alterado para salvar tambï¿½m o obj tFuncAcompConfigMailTfa.
         * @author rogeriom
         * @since 26/04/2006
         * -- */
        Transaction tx = null;
        ArrayList objetos = new ArrayList(  );

        super.inicializarLogBean(  );

        try
        {
            tx = session.beginTransaction(  );

            session.update( tipoFuncAcomp );
            objetos.add( tipoFuncAcomp );

            // deletando os TfuncacompConfigmailTfacfgm gravados
            for ( Iterator it = tipoFuncAcomp.getTfuncacompConfigmailTfacfgm(  ).iterator(  ); it.hasNext(  ); )
            {
                TfuncacompConfigmailTfacfgm tFuncAcompCfgm = (TfuncacompConfigmailTfacfgm) it.next(  );
                session.delete( tFuncAcompCfgm );
            }

            // inserindo os TfuncacompConfigmailTfacfgm
            for ( Iterator it = lstTFuncAcompCfgm.iterator(  ); it.hasNext(  ); )
            {
                TfuncacompConfigmailTfacfgm tFuncAcompCfgm = (TfuncacompConfigmailTfacfgm) it.next(  );
                session.save( tFuncAcompCfgm );
                objetos.add( tFuncAcompCfgm );
            }

            new ControlePermissao(  ).atualizarPermissoesPorFuncaoDeAcompanhamento( tipoFuncAcomp );

            tx.commit(  );

            if ( super.logBean != null )
            {
                super.logBean.setCodigoTransacao( Data.getHoraAtual( false ) );
                super.logBean.setOperacao( "INC_ALT_EXC" );

                for ( Iterator itObj = objetos.iterator(  ); itObj.hasNext(  ); )
                {
                    super.logBean.setObj( itObj.next(  ) );
                    super.loggerAuditoria.info( logBean.toString(  ) );
                }
            }
        } catch ( HibernateException e )
        {
            if ( tx != null )
            {
                try
                {
                    tx.rollback(  );
                } catch ( HibernateException r )
                {
                    this.logger.error( r );
                    throw new ECARException( "erro.hibernateException" );
                }
            }

            this.logger.error( e );
            throw new ECARException( "erro.hibernateException" );
        }
    }

    /**
     * Exclui...
     * @param tipoFuncAcomp
     * @param lstTFuncAcompCfgm
     * @throws ECARException
     */
    public void excluir( TipoFuncAcompTpfa tipoFuncAcomp, List lstTFuncAcompCfgm )
                 throws ECARException
    {
        /* --
         * Alterado para suportar transacao.
         * @author rogeriom
         * @since 02/05/2006
         * -- */
        Transaction tx = null;
        ArrayList objetos = new ArrayList(  );

        super.inicializarLogBean(  );

        try
        {
            boolean excluir = true;

            if ( ! tipoFuncAcomp.getTipoFuncAcompTpfasPermiteAlterarInferior(  ).isEmpty(  ) ||
                     ! tipoFuncAcomp.getTipoFuncAcompTpfasPermiteAlterarSuperior(  ).isEmpty(  ) )
            {
                excluir = false;
                throw new ECARException( "tipoFuncAcomp.exclusao.erro.permissoes" ); // possui permissï¿½es associadas portanto nï¿½o pode ser excluï¿½do
                                                                                     //TODO a exclusï¿½o de permissï¿½es deverï¿½ ser re-avaliada no Futuro. Mantis Corporativo:22281 e 21041
            }

            if ( contar( tipoFuncAcomp.getEstrutTpFuncAcmpEtttfas(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "tipoFuncAcomp.exclusao.erro.estrutTpFuncAcmpEtttfas" ); // possui estruturas ligadas a essa funï¿½ï¿½o de acompanhamento
            }

            if ( contar( tipoFuncAcomp.getItemEstUsutpfuacIettutfas(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "tipoFuncAcomp.exclusao.erro.itemEstUsutpfuacIettutfas" ); //possui itens de estrutura ligados a essa funcao de acompanhamento
            }

            if ( contar( tipoFuncAcomp.getSituacaoTpFuncAcmpSitfas(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "tipoFuncAcomp.exclusao.erro.situacaoTpFuncAcmpSitfas" ); //possui pareceres ligados a essa funcao de acompanhamento
            }

            if ( contar( tipoFuncAcomp.getTipoFuncAcompTpfas(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "tipoFuncAcomp.exclusao.erro.tipoFuncAcompTpfas" ); //possui funï¿½ï¿½es de acompanhamento superiores ou inferiores ligadas a essa funcao de 
                                                                                             // acompanhamento
            }

            if ( contar( tipoFuncAcomp.getAcompRefItemLimitesArlis(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "tipoFuncAcomp.exclusao.erro.acompRefItemLimitesArlis" ); //Possui referencias ligadas a essa funcao de acompanhamento 
            }

            if ( contar( tipoFuncAcomp.getAcompRefLimitesArls(  ) ) > 0 )
            {
                excluir = false;
                throw new ECARException( "tipoFuncAcomp.exclusao.erro.acompRefLimitesArls" ); //Possui acompanhamentos ligados a essa funcao de acompanhamento codAri)
            }

            if ( excluir )
            {
                tx = session.beginTransaction(  );

                // deletando os TfuncacompConfigmailTfacfgm gravados
                for ( Iterator it = tipoFuncAcomp.getTfuncacompConfigmailTfacfgm(  ).iterator(  ); it.hasNext(  ); )
                {
                    TfuncacompConfigmailTfacfgm tFuncAcompCfgm = (TfuncacompConfigmailTfacfgm) it.next(  );
                    session.delete( tFuncAcompCfgm );
                }

                session.delete( tipoFuncAcomp );

                tx.commit(  );

                if ( super.logBean != null )
                {
                    super.logBean.setCodigoTransacao( Data.getHoraAtual( false ) );
                    super.logBean.setOperacao( "EXC" );

                    for ( Iterator itObj = objetos.iterator(  ); itObj.hasNext(  ); )
                    {
                        super.logBean.setObj( itObj.next(  ) );
                        super.loggerAuditoria.info( logBean.toString(  ) );
                    }
                }
            }
        } catch ( HibernateException e )
        {
            if ( tx != null )
            {
                try
                {
                    tx.rollback(  );
                } catch ( HibernateException r )
                {
                    this.logger.error( r );
                    throw new ECARException( "erro.hibernateException" );
                }
            }

            this.logger.error( e );
            throw new ECARException( "erro.hibernateException" );
        }
    }

    /**
     * Recebe um array com os Cï¿½digos dos Tipos de Funï¿½ï¿½o e retorna um Set com
     * objetos TipoFuncAcompTpfa correspondentes a estes cï¿½digos
     *
     * @param funcoes
     *            array com os Cï¿½digos dos Tipos de Funï¿½ï¿½o
     * @return Set objetos TipoFuncAcompTpfa
     * @throws ECARException
     */
    public Set getSetFuncoesAcompanhamento( String[] funcoes )
                                    throws ECARException
    {
        Set retorno = new HashSet(  );

        if ( funcoes != null )
        {
            for ( int i = 0; i < funcoes.length; i++ )
            {
                try
                {
                    TipoFuncAcompTpfa funcao =
                        (TipoFuncAcompTpfa) this.buscar( TipoFuncAcompTpfa.class,
                                                         Long.valueOf( funcoes[i] ) );
                    retorno.add( funcao );
                } catch ( ECARException e )
                {
                    this.logger.error( e );
                    throw e;
                }
            }
        }

        return retorno;
    }

    /**
     *
     * @param lista
     * @param codTPFA
     * @return
     * @throws ECARException
     */
    public TipoFuncAcompTpfa pesquisarNaLista( List lista, Long codTPFA )
                                       throws ECARException
    {
        TipoFuncAcompTpfa tpFA = null;

        for ( Object object : lista )
        {
            tpFA = (TipoFuncAcompTpfa) object;

            if ( tpFA.getCodTpfa(  ).longValue(  ) == codTPFA.longValue(  ) )
            {
                return tpFA;
            }
        }

        tpFA = new TipoFuncAcompTpfa(  );
        tpFA.setCodTpfa( codTPFA );

        return tpFA;
    }

    /*public void salvarTPFA(Long codInferior, Long codSuperior){
            String insert = "insert into TipoFuncAcompTpfaPermiteAlterar as tipo " +
                " values tipo.comp_id.cod_superior_tpfapa.codTpfa= :cod";
                Query q = this.session.createQuery(select);

                q.setLong("cod", funcAcomp.getCodTpfa());

                Set setTPFAPA = new HashSet();
                setTPFAPA.addAll(q.list());
    }*/

    /**
     *
     * @param cl
     * @param chave
     * @return
     * @throws ECARException
     */
    public Object buscarComDescendentes( Class cl, Serializable chave )
                                 throws ECARException
    {
        List funcoes = new ArrayList(  );
        TipoFuncAcompTpfa funcAcomp;
        //try {
        funcAcomp = (TipoFuncAcompTpfa) buscar( cl, chave );

        List listaFuncAcomp = new ArrayList(  );
        listaFuncAcomp.add( funcAcomp );
        funcoes = getDescendentesHierarquizados( listaFuncAcomp );

        /*String select = "from TipoFuncAcompTpfaPermiteAlterar as tipo " +
            " where tipo.comp_id.cod_superior_tpfapa.codTpfa= :cod";
            Query q = this.session.createQuery(select);

            q.setLong("cod", funcAcomp.getCodTpfa());

            Set setTPFAPA = new HashSet();
            setTPFAPA.addAll(q.list());

            Set setTPFAPAReal = new HashSet();
            for (Object object : funcoes) {
                    TipoFuncAcompTpfa tpFA = (TipoFuncAcompTpfa) object;

                    TipoFuncAcompTpfaPermiteAlterar tpFAPA = new TipoFuncAcompTpfaPermiteAlterar();
                        TipoFuncAcompTpfaPermiteAlterarPK tpFAPK = new TipoFuncAcompTpfaPermiteAlterarPK();
                        tpFAPK.setCod_inferior_tpfapa(tpFA);
                        tpFAPK.setCod_superior_tpfapa(funcAcomp);
                        tpFAPA.setComp_id(tpFAPK);

                    for (Object object2 : setTPFAPA) {
                                TipoFuncAcompTpfaPermiteAlterar tpFAPA2 = (TipoFuncAcompTpfaPermiteAlterar) object2;
                                if(tpFA.getCodTpfa().longValue() == tpFAPA2.getComp_id().getCod_inferior_tpfapa().getCodTpfa().longValue()){
                                        tpFAPA.setPermiteAlterarParecer(tpFAPA2.getPermiteAlterarParecer());
                                }
                        }
                    setTPFAPAReal.add(tpFAPA);
                }
            funcAcomp.setTipoFuncAcompTpfasPermiteAlterarInferior(setTPFAPAReal);

        } catch (ECARException e) {
                this.logger.error(e);
        throw new ECARException("erro.exception");
        }
        */
        return funcAcomp;
    }

    /**
     *
     * @param tipoFuncAcomp
     * @param argumentosOrder
     * @return
     * @throws ECARException
     */
    public List pesquisarComDescendentes( TipoFuncAcompTpfa tipoFuncAcomp, String[] argumentosOrder )
                                  throws ECARException
    {
        List funcoes = new ArrayList(  );

        List lista = pesquisar( tipoFuncAcomp, argumentosOrder );

/*               StringBuilder query = new StringBuilder("select distinct item from ItemEstruturaIett as item")
                   .append(" join item.itemEstUsutpfuacIettutfas as itemFuncao");

   query.append(" left join item.itemEstrtIndResulIettrs as indResultados");
   query.append(" where ");
   query.append(" item.indAtivoIett = 'S' AND ");
   query.append(" (itemFuncao.tipoFuncAcompTpfa.indInformaAndamentoTpfa='S' ");*/

        /*TipoFuncAcompTpfaPermiteAlterarPK pk = new TipoFuncAcompTpfaPermiteAlterarPK();
        pk.setCod_superior_tpfapa(tipoFuncAcomp);
        try{
                TipoFuncAcompTpfaPermiteAlterar tipoFuncao = (TipoFuncAcompTpfaPermiteAlterar) getSession().load(TipoFuncAcompTpfaPermiteAlterar.class, pk.getCod_superior_tpfapa().getCodTpfa());/*= new TipoFuncAcompTpfaPermiteAlterar();
        } catch (Exception e){
                e.printStackTrace();
        }
        */

        //tipoFuncao.setComp_id(pk);

        //List lista2 = pesquisar(tipoFuncao, new String[] {"comp_id","asc"});
        for ( Object obj : lista )
        {
            TipoFuncAcompTpfa funcAcomp = (TipoFuncAcompTpfa) obj;
            List listaFuncAcomp = new ArrayList(  );

            listaFuncAcomp.add( funcAcomp );
            funcoes = getDescendentesHierarquizados( listaFuncAcomp );

            /*        Set setFA = new HashSet();
                    setFA.addAll(funcoes);

            List m = new ArrayList();
            try{
                    String select = "from TipoFuncAcompTpfaPermiteAlterar as tipo " +
                        " where tipo.comp_id.cod_superior_tpfapa.codTpfa= :cod";
                        Query q = this.session.createQuery(select);

                        q.setLong("cod", funcAcomp.getCodTpfa());

                        Set setTPFAPA = new HashSet();
                        setTPFAPA.addAll(q.list());

                        Set setTPFAPAReal = new HashSet();
                        for (Object object : funcoes) {
                                TipoFuncAcompTpfa tpFA = (TipoFuncAcompTpfa) object;

                                TipoFuncAcompTpfaPermiteAlterar tpFAPA = new TipoFuncAcompTpfaPermiteAlterar();
                                    TipoFuncAcompTpfaPermiteAlterarPK tpFAPK = new TipoFuncAcompTpfaPermiteAlterarPK();
                                    tpFAPK.setCod_inferior_tpfapa(tpFA);
                                    tpFAPK.setCod_superior_tpfapa(funcAcomp);
                                    tpFAPA.setComp_id(tpFAPK);

                                for (Object object2 : setTPFAPA) {
                                            TipoFuncAcompTpfaPermiteAlterar tpFAPA2 = (TipoFuncAcompTpfaPermiteAlterar) object2;
                                            if(tpFA.getCodTpfa().longValue() == tpFAPA2.getComp_id().getCod_inferior_tpfapa().getCodTpfa().longValue()){
                                                    tpFAPA.setPermiteAlterarParecer(tpFAPA2.getPermiteAlterarParecer());
                                            }
                                    }
                                setTPFAPAReal.add(tpFAPA);
                            }
                        funcAcomp.setTipoFuncAcompTpfasPermiteAlterarInferior(setTPFAPAReal);
                        //excluir(setTPFAPA);

                        /*if(setTPFAPA == null || setTPFAPA.size() == 0){
                                for (Object object : funcoes) {
                                            Long codInferior = (Long) object;
                                            TipoFuncAcompTpfa tpFASuperior = new TipoFuncAcompTpfa();
                                            tpFASuperior = funcAcomp;
                                            TipoFuncAcompTpfa tpFAInferior = new TipoFuncAcompTpfa();
                                            tpFAInferior = pesquisarNaLista(lista,codInferior);
                                            //tpFAInferior.setCodTpfa(codInferior);
                                            TipoFuncAcompTpfaPermiteAlterarPK tpFAPK = new TipoFuncAcompTpfaPermiteAlterarPK();
                                            tpFAPK.setCod_inferior_tpfapa(tpFAInferior);
                                            tpFAPK.setCod_superior_tpfapa(tpFASuperior);
                                            TipoFuncAcompTpfaPermiteAlterar tpFAPA = new TipoFuncAcompTpfaPermiteAlterar();
                                            tpFAPA.setComp_id(tpFAPK);
                                            tpFAPA.setPermiteAlterarParecer("N");
                                            setTPFAPA.add(tpFAPA);

                                            salvar(tpFAPA);
                                            //inserirTipoFuncAcompTpfasPermiteAlterar(setTPFAPA);
                                    }
                                //salvarOuAlterar(setTPFAPA);
                        } else {

                                for (Object object : setTPFAPA) {
                                        boolean encontrou = false;
                                        TipoFuncAcompTpfaPermiteAlterar tipoFuncAPA = (TipoFuncAcompTpfaPermiteAlterar) object;
                                        for (Object object2 : funcoes) {
                                                Long codInferior = (Long) object2;
                                                    if(codInferior.longValue() == tipoFuncAPA.getComp_id().getCod_inferior_tpfapa().getCodTpfa().longValue()){
                                                            encontrou = true;
                                                    }
                                        }
                                        if(!encontrou){
                                                excluir(object);
                                                setTPFAPA.remove(object);
                                        }
                                }

                                for (Object object : funcoes) {
                                        boolean encontrou = false;
                                            Long codInferior = (Long) object;
                                            for (Object object2 : setTPFAPA) {
                                                    TipoFuncAcompTpfaPermiteAlterar tipoFuncAPA = (TipoFuncAcompTpfaPermiteAlterar) object2;
                                                    if(codInferior.longValue() == tipoFuncAPA.getComp_id().getCod_inferior_tpfapa().getCodTpfa().longValue()){
                                                            encontrou = true;
                                                    }
                                            }
                                            if(!encontrou){
                                                    TipoFuncAcompTpfa tpFASuperior = new TipoFuncAcompTpfa();
                                                    tpFASuperior.setCodTpfa(funcAcomp.getCodTpfa());
                                                    TipoFuncAcompTpfa tpFAInferior = new TipoFuncAcompTpfa();
                                                    tpFAInferior.setCodTpfa(codInferior);
                                                    TipoFuncAcompTpfaPermiteAlterarPK tpFAPK = new TipoFuncAcompTpfaPermiteAlterarPK();
                                                    tpFAPK.setCod_inferior_tpfapa(tpFAInferior);
                                                    tpFAPK.setCod_superior_tpfapa(tpFASuperior);
                                                    TipoFuncAcompTpfaPermiteAlterar tpFAPA = new TipoFuncAcompTpfaPermiteAlterar();
                                                    tpFAPA.setComp_id(tpFAPK);
                                                    tpFAPA.setPermiteAlterarParecer("N");
                                                    setTPFAPA.add(tpFAPA);
                                                    salvarOuAlterar(tpFAPA);
                                            }
                                            //inserirTipoFuncAcompTpfasPermiteAlterar(setTPFAPA);
                                    }
                                //TESTE!!!salvarOuAlterar(setTPFAPA);
                        }*/

            //Se tirar o /**/ tira isso tb
            //funcAcomp.setTipoFuncAcompTpfasPermiteAlterarInferior(setTPFAPA);
            /* } catch(Exception e){
                     e.printStackTrace();
            }

                 //funcAcomp.setTipoFuncAcompTpfas(setFA);*/
        }

        //funcoes = getDescendentes(lista);

        //getDescendentesHierarquizadosPA(lista);

        //((TipoFuncAcompTpfa)lista.get(0)).getTipoFuncAcompTpfasPermiteAlterarInferior();
        //((TipoFuncAcompTpfa)lista.get(0)).getTipoFuncAcompTpfasPermiteAlterarSuperior();
        return lista;
    }

    /**
     *
     * @param tipoFuncAcomp
     * @param valores
     */
    public void montarFilhos( TipoFuncAcompTpfa tipoFuncAcomp, String[] valores )
    {
        if ( valores != null )
        {
            for ( int i = 0; i < valores.length; i++ )
            {
                int indexVirgula = valores[i].indexOf( "," );

                if ( indexVirgula != -1 )
                {
                    valores[i] = valores[i].substring( 0, indexVirgula );
                }
            }
        }

        Set listaInferiores = new HashSet(  );
        listaInferiores.addAll( tipoFuncAcomp.getTipoFuncAcompTpfasPermiteAlterarInferior(  ) );

        boolean pegouSessao = false;

        if ( ( listaInferiores == null ) || ( listaInferiores.size(  ) == 0 ) )
        {
            //inserirTipoFuncAcompTpfasPermiteAlterar((PersistentSet)request.getSession().getAttribute("listaInferiores"));
            if ( (PersistentSet) request.getSession(  ).getAttribute( "listaInferiores" ) != null )
            {
                listaInferiores.addAll( (PersistentSet) request.getSession(  ).getAttribute( "listaInferiores" ) );
                pegouSessao = true;
            }
        } else
        {
            Set listaInferioresSessao = ( (PersistentSet) request.getSession(  ).getAttribute( "listaInferiores" ) );

            if ( ( listaInferioresSessao != null ) && ( listaInferiores.size(  ) < listaInferioresSessao.size(  ) ) )
            {
                for ( Object objectSessao : listaInferioresSessao )
                {
                    boolean encontrou = false;
                    TipoFuncAcompTpfaPermiteAlterar tpFASessao = (TipoFuncAcompTpfaPermiteAlterar) objectSessao;

                    for ( Object object : listaInferiores )
                    {
                        TipoFuncAcompTpfaPermiteAlterar tpFA = (TipoFuncAcompTpfaPermiteAlterar) object;

                        if ( tpFA.getComp_id(  ).getCod_inferior_tpfapa(  ).getCodTpfa(  ).longValue(  ) == tpFASessao.getComp_id(  )
                                                                                                                          .getCod_inferior_tpfapa(  )
                                                                                                                          .getCodTpfa(  )
                                                                                                                          .longValue(  ) )
                        {
                            encontrou = true;

                            break;
                        }
                    }

                    if ( ! encontrou )
                    {
                        listaInferiores.add( tpFASessao );
                        tipoFuncAcomp.getTipoFuncAcompTpfasPermiteAlterarInferior(  ).add( tpFASessao );
                    }
                }
            }
        }

        //hierarquizados(listaInferiores, valores);
        for ( Object object : listaInferiores )
        {
            TipoFuncAcompTpfaPermiteAlterar tipo = (TipoFuncAcompTpfaPermiteAlterar) object;
            boolean permite = false;

            if ( valores != null )
            {
                for ( int i = 0; i < valores.length; i++ )
                {
                    if ( tipo.getComp_id(  ).getCod_inferior_tpfapa(  ).getCodTpfa(  ).longValue(  ) == Long.parseLong( valores[i] ) )
                    {
                        tipo.setPermiteAlterarParecer( "S" );
                        permite = true;
                    }
                }
            }

            if ( ! permite )
            {
                tipo.setPermiteAlterarParecer( "N" );
            }
        }

        if ( pegouSessao )
        {
            tipoFuncAcomp.setTipoFuncAcompTpfasPermiteAlterarInferior( listaInferiores );
        }
    }

    /**
     *
     * @param listaInferiores
     * @param valores
     * @return
     */
    public boolean hierarquizados( Set listaInferiores, String[] valores )
    {
        boolean permite = false;

        for ( Object object : listaInferiores )
        {
            TipoFuncAcompTpfaPermiteAlterar tipo = (TipoFuncAcompTpfaPermiteAlterar) object;
            permite = false;

            if ( valores != null )
            {
                for ( int i = 0; i < valores.length; i++ )
                {
                    if ( tipo.getComp_id(  ).getCod_inferior_tpfapa(  ).getCodTpfa(  ).longValue(  ) == Long.parseLong( valores[i] ) )
                    {
                        tipo.setPermiteAlterarParecer( "S" );
                        permite = true;
                    }
                }
            }

            if ( ! permite )
            {
                if ( ( 
                             tipo.getComp_id(  ).getCod_inferior_tpfapa(  ).getTipoFuncAcompTpfasPermiteAlterarInferior(  ) != null
                          ) &&
                         ( 
                                 tipo.getComp_id(  ).getCod_inferior_tpfapa(  )
                                         .getTipoFuncAcompTpfasPermiteAlterarInferior(  ).size(  ) > 0
                              ) )
                {
                    if ( ! hierarquizados( tipo.getComp_id(  ).getCod_inferior_tpfapa(  )
                                                   .getTipoFuncAcompTpfasPermiteAlterarInferior(  ),
                                               valores ) )
                    {
                        tipo.setPermiteAlterarParecer( "N" );
                    } else
                    {
                        tipo.setPermiteAlterarParecer( "S" );
                        permite = true;
                    }
                } else
                {
                    tipo.setPermiteAlterarParecer( "N" );
                }
            }

/*                        TipoFuncAcompTpfaPermiteAlterar teste = new TipoFuncAcompTpfaPermiteAlterar();
                TipoFuncAcompTpfaPermiteAlterarPK pk = new TipoFuncAcompTpfaPermiteAlterarPK();
                pk.setCod_superior_tpfapa(tipoFuncAcomp);
                pk.setCod_inferior_tpfapa(tipo.getComp_id().getCod_inferior_tpfapa());
                teste.setComp_id(pk);
                        teste.setPermiteAlterarParecer(tipo.getPermiteAlterarParecer());
                        testeTPFAPA.add(teste);*/
        }

        return permite;
    }

    /**
     *
     * @param lista
     * @return
     */
    public List listarFilhosPA( List lista )
    {
        List funcoes = new ArrayList(  );

        for ( Object obj : lista )
        {
            TipoFuncAcompTpfa funcAcomp = (TipoFuncAcompTpfa) obj;

            List listaFilhos = new ArrayList(  );
            PersistentSet listaFilhosPA = (PersistentSet) funcAcomp.getTipoFuncAcompTpfasPermiteAlterarInferior(  );

            for ( Object object : listaFilhosPA )
            {
                listaFilhos.add( ( (TipoFuncAcompTpfaPermiteAlterar) object ).getComp_id(  ).getCod_inferior_tpfapa(  ) );
                funcoes.add( (TipoFuncAcompTpfaPermiteAlterar) object );
            }

            //listaFilhos.add(((TipoFuncAcompTpfaPermiteAlterar)funcAcomp.getTipoFuncAcompTpfasPermiteAlterarInferior()).getTipoFuncAcompTpfaInferior());
            if ( listaFilhos.size(  ) > 0 )
            {
                funcoes.addAll( listarFilhosPA( listaFilhos ) );
            }
        }

        return funcoes;
    }

    /**
     * Retorna as Funï¿½ï¿½es abaixo da Funï¿½ï¿½o passada como parï¿½metro (filhos,
     * netos, etc.)
     *
     * @param lista
     */
    public void getDescendentesHierarquizadosPA( List lista )
    {
        for ( Object obj : lista )
        {
            TipoFuncAcompTpfa funcAcomp = (TipoFuncAcompTpfa) obj;

            List listaFilhos = new ArrayList(  );
            PersistentSet listaFilhosPA = (PersistentSet) funcAcomp.getTipoFuncAcompTpfasPermiteAlterarInferior(  );

            for ( Object object : listaFilhosPA )
            {
                listaFilhos.add( ( (TipoFuncAcompTpfaPermiteAlterar) object ).getComp_id(  ).getCod_inferior_tpfapa(  ) );
            }

            //listaFilhos.add(((TipoFuncAcompTpfaPermiteAlterar)funcAcomp.getTipoFuncAcompTpfasPermiteAlterarInferior()).getTipoFuncAcompTpfaInferior());
            if ( listaFilhos.size(  ) > 0 )
            {
                getDescendentesHierarquizadosPA( listaFilhos );
            }
        }
    }

    /**
     * Retorna as Funï¿½ï¿½es abaixo da Funï¿½ï¿½o passada como parï¿½metro (filhos,
     * netos, etc.)
     *
     * @param listTipoFuncAcomp
     * @return Set Coleï¿½ï¿½o de Funï¿½ï¿½es Pseudocodigo:
     *
     * Descendentes(g) { resultado = {conjunto vazio}
     *
     * se (Filhos(g) != {conjunto vazio}) para cada f em Filhos(g) faca
     * resultado <- {f} U Descendentes(f) return (resultado) }
     */
    public List getDescendentesHierarquizados( List listTipoFuncAcomp )
    {
        List funcoes = new ArrayList(  );

        Iterator itPrincipal = listTipoFuncAcomp.iterator(  );

        while ( itPrincipal.hasNext(  ) )
        {
            TipoFuncAcompTpfa tipoFuncAcomp = (TipoFuncAcompTpfa) itPrincipal.next(  );

            if ( tipoFuncAcomp.getTipoFuncAcompTpfas(  ) != null )
            {
                // Coleï¿½ï¿½o dos Filhos
                Iterator it = tipoFuncAcomp.getTipoFuncAcompTpfas(  ).iterator(  );

                while ( it.hasNext(  ) )
                {
                    TipoFuncAcompTpfa tipoFuncAcompFilho = (TipoFuncAcompTpfa) it.next(  );
                    //if (!funcoes.contains(tipoFuncAcompFilho)) {
                    tipoFuncAcomp.getTipoFuncAcompTpfas(  ).add( tipoFuncAcompFilho );
                    funcoes.add( tipoFuncAcompFilho );

                    List listFilho = new ArrayList(  );
                    listFilho.add( tipoFuncAcompFilho );
                    //getDescendentesHierarquizados(listFilho);
                    funcoes.addAll( getDescendentesHierarquizados( listFilho ) );

                    //   }
                }
            }
        }

        return funcoes;
    }

    /**
     * Retorna as Funï¿½ï¿½es abaixo da Funï¿½ï¿½o passada como parï¿½metro (filhos,
     * netos, etc.)
     *
     * @param listTipoFuncAcomp
     * @return Set Coleï¿½ï¿½o de Funï¿½ï¿½es Pseudocodigo:
     *
     * Descendentes(g) { resultado = {conjunto vazio}
     *
     * se (Filhos(g) != {conjunto vazio}) para cada f em Filhos(g) faca
     * resultado <- {f} U Descendentes(f) return (resultado) }
     */
    public List getDescendentes( List listTipoFuncAcomp )
    {
        List funcoes = new ArrayList(  );

        Iterator itPrincipal = listTipoFuncAcomp.iterator(  );

        while ( itPrincipal.hasNext(  ) )
        {
            TipoFuncAcompTpfa tipoFuncAcomp = (TipoFuncAcompTpfa) itPrincipal.next(  );

            if ( tipoFuncAcomp.getTipoFuncAcompTpfas(  ) != null )
            {
                // Coleï¿½ï¿½o dos Filhos
                Iterator it = tipoFuncAcomp.getTipoFuncAcompTpfas(  ).iterator(  );

                while ( it.hasNext(  ) )
                {
                    TipoFuncAcompTpfa tipoFuncAcompFilho = (TipoFuncAcompTpfa) it.next(  );

                    if ( ! funcoes.contains( tipoFuncAcompFilho ) )
                    {
                        funcoes.add( tipoFuncAcompFilho );

                        List listFilho = new ArrayList(  );
                        listFilho.add( tipoFuncAcompFilho );
                        funcoes.addAll( getDescendentes( listFilho ) );
                    }
                }
            }
        }

        return funcoes;
    }

    /**
     * @param objetosExcluidos
     * @return Set Coleï¿½ï¿½o de Funï¿½ï¿½es
     * @deprecated
     * @throws HibernateException
     */
    public List getTipoFuncAcompPermitidos( List objetosExcluidos )
                                    throws HibernateException
    {
        /*
             * Nï¿½o estï¿½ sendo utilizado. Nao sei para que serve... (garten)
             */
        List funcoes = new ArrayList(  );

        Dao dao = new Dao(  );
        Session session = dao.getSession(  );
        Criteria select = session.createCriteria( TipoFuncAcompTpfa.class );
        select.addOrder( Order.asc( "descricaoTpfa" ) );
        funcoes = select.list(  );

        funcoes.removeAll( objetosExcluidos );

        return funcoes;
    }

    /**
     * Retorna a funcao de acompanhamento de maior nivel, isto ï¿½, que nï¿½o tem pai
     * @author garten
     * @return Objeto TipoFuncAcompTpfa
     * @throws ECARException
     */
    public List getPrincipal(  )
                      throws ECARException
    {
        try
        {
            return session.createQuery( "from TipoFuncAcompTpfa as f where f.tipoFuncAcompTpfa is null" ).list(  );
        } catch ( HibernateException e )
        {
            this.logger.error( e );
            throw new ECARException( "erro.hibernateException" );
        }
    }

    /**
     * Devolve todas as funcoes de acompanhamento que emitem posicao ordenadas do filho para o pai
     * @author garten
     * @return List TipoFuncAcompTpfa
     * @throws ECARException
     */
    public List getTipoFuncAcompEmitePosicao(  )
                                      throws ECARException
    {
        List lResultado = new ArrayList(  );
        List descendentes = new ArrayList(  );
        List principais = this.getPrincipal(  );

        descendentes.addAll( principais );
        descendentes.addAll( getDescendentes( principais ) );

        for ( Iterator itDescendentes = descendentes.iterator(  ); itDescendentes.hasNext(  ); )
        {
            TipoFuncAcompTpfa funcao = (TipoFuncAcompTpfa) itDescendentes.next(  );

            if ( "S".equals( funcao.getIndEmitePosicaoTpfa(  ) ) )
            {
                lResultado.add( funcao );
            }
        }

        Collections.reverse( lResultado );

        return lResultado;
    }

//    public List getTipoFuncAcompEmitePosicao (TipoFuncAcompTpfa ta){
//    	List lista = new ArrayList();
//    	Criteria select = this.getSession().createCriteria(TipoFuncAcompTpfa.class)
//    						.add(Restrictions.eq("indEmitePosicaoTpfa", Dominios.SIM));
//    	
//    	if(ta == null || ta.getCodTpfa() == null)
//    		select.add(Restrictions.isNotNull("tipoFuncAcompTpfas"));
//    	else
//    		select.add(Restrictions.eq("tipoFuncAcompTpfas", ta));
//    	
//    	List raiz = select.list();
//    	
//    	lista.addAll(raiz);
//    	
//    	Iterator it = raiz.iterator();
//    	
//    	while(it.hasNext()){
//    		lista.addAll(this.getTipoFuncAcompEmitePosicao((TipoFuncAcompTpfa) it.next()));
//    	}
//    	return lista;	
//    }

//    public List getTipoFuncAcompEmitePosicao (){
//    	return this.getTipoFuncAcompEmitePosicao(null);
//    }

    /**
     * Devolve todas as funcoes de acompanhamento ordenadas do filho para o pai
     * @author garten
     * @return List TipoFuncAcompTpfa
     * @throws ECARException
     */
    public List getTipoFuncAcompOrdemFilhoAoPai(  )
                                         throws ECARException
    {
        List descendentes = new ArrayList(  );
        List principais = getPrincipal(  );
        descendentes.addAll( principais );
        descendentes.addAll( getDescendentes( principais ) );
        Collections.reverse( descendentes );

        return descendentes;
    }

    /**
     * Obter as funï¿½ï¿½es de acompanhamento utilizadas pelos acompanhamentos(AREF -> ARL) e de determinado tipo de acompanhamento,
     * ordenado por datas.
     * Exemplo:
     * Funï¿½ï¿½es de acompanhamento do ARL: A, B, D e E.
     * Funï¿½ï¿½es de acompanhamento do TipoAcompanhamento: A, B, C e D.
     * Este mï¿½todo retornarï¿½: A, B, C, D e E.
     *
     * @param codTa
     * @param listaAcompanhamentos
     * @return List de OrdenacaoDataTpfa
     *
     * @throws ECARException
     */
    public List getTpfaOfArlAndTipoAcompanhamentoOrderByDatas( Long codTa, List listaAcompanhamentos )
        throws ECARException
    {
        try
        {
            AcompReferenciaDao acompDao = new AcompReferenciaDao( null );
            List listOrdenacaoDataTpfa = new ArrayList(  );
            List listTpfaCompleta = new ArrayList(  );

            Calendar calendar = Calendar.getInstance(  );
            calendar.set( 1990, 0, 1 );

            // obter os TPFA do tipo de acompanhamento
            List listTpfaDoTipoAcomp =
                new TipoAcompanhamentoDao( null ).getTipoFuncaoAcompanhamento( (TipoAcompanhamentoTa) super.buscar( TipoAcompanhamentoTa.class,
                                                                                                                    codTa ) );

            // obter os TPFA dos ARLs
            Query query =
                session.createQuery( "select distinct tpfa from TipoFuncAcompTpfa as tpfa " +
                                     "join tpfa.acompRefLimitesArls as arls " +
                                     "where arls.acompReferenciaAref.tipoAcompanhamentoTa.codTa = :codTa " );
            query.setLong( "codTa",
                           codTa.longValue(  ) );

            List listTpfaDoArl = query.list(  );

            listTpfaCompleta.addAll( listTpfaDoArl );

            for ( Iterator it = listTpfaDoTipoAcomp.iterator(  ); it.hasNext(  ); )
            {
                TipoFuncAcompTpfa tpfa = (TipoFuncAcompTpfa) it.next(  );

                if ( ! listTpfaCompleta.contains( tpfa ) )
                {
                    listTpfaCompleta.add( tpfa );
                }
            }

            // ordenar datas do primeiro acompanhamento da lista
            if ( ( listaAcompanhamentos != null ) && ( listaAcompanhamentos.size(  ) > 0 ) )
            {
                AcompReferenciaAref aref = (AcompReferenciaAref) listaAcompanhamentos.get( 0 );

                // datas fixas
                OrdenacaoDataTpfa ordenacaoDataTpfa = new OrdenacaoDataTpfa(  );
                ordenacaoDataTpfa.setData( aref.getDataInicioAref(  ) );
                ordenacaoDataTpfa.setTpfaFixo( OrdenacaoDataTpfa.FUNCAO_INICIO );
                ordenacaoDataTpfa.setLabel( "Início" );
                listOrdenacaoDataTpfa.add( ordenacaoDataTpfa );

                ordenacaoDataTpfa = new OrdenacaoDataTpfa(  );
                ordenacaoDataTpfa.setData( aref.getDataLimiteAcompFisicoAref(  ) );
                ordenacaoDataTpfa.setTpfaFixo( OrdenacaoDataTpfa.FUNCAO_LIMITE );
                ordenacaoDataTpfa.setLabel( "Ac./Fis./Fin." );
                listOrdenacaoDataTpfa.add( ordenacaoDataTpfa );

                // adiciona datas de TPFA variï¿½veis
                Iterator itTpfaCompleta = listTpfaCompleta.iterator(  );

                while ( itTpfaCompleta.hasNext(  ) )
                {
                    TipoFuncAcompTpfa tpfa = (TipoFuncAcompTpfa) itTpfaCompleta.next(  );
                    ordenacaoDataTpfa = new OrdenacaoDataTpfa(  );

                    ordenacaoDataTpfa.setTpfa( tpfa );
                    ordenacaoDataTpfa.setLabel( tpfa.getLabelPosicaoTpfa(  ) );
                    ordenacaoDataTpfa.setData( calendar.getTime(  ) );

                    listOrdenacaoDataTpfa.add( ordenacaoDataTpfa );
                }

                List arls = new ArrayList( acompDao.getAcompRefLimitesOrderByFuncaoAcomp( aref ) );

                Iterator itOrdenacaoDataTpfa = listOrdenacaoDataTpfa.iterator(  );

                while ( itOrdenacaoDataTpfa.hasNext(  ) )
                {
                    OrdenacaoDataTpfa ord = (OrdenacaoDataTpfa) itOrdenacaoDataTpfa.next(  );

                    Iterator itArls = arls.iterator(  );

                    while ( itArls.hasNext(  ) )
                    {
                        AcompRefLimitesArl arl = (AcompRefLimitesArl) itArls.next(  );

                        if ( ( ord.getTpfa(  ) != null ) && ord.getTpfa(  ).equals( arl.getTipoFuncAcompTpfa(  ) ) )
                        {
                            ord.setData( arl.getDataLimiteArl(  ) );

                            break;
                        }
                    }
                }

                // ordenar
                Collections.sort( listOrdenacaoDataTpfa,
                                  new Comparator(  )
                    {
                        public int compare( Object o1, Object o2 )
                        {
                            OrdenacaoDataTpfa ord1 = (OrdenacaoDataTpfa) o1;
                            OrdenacaoDataTpfa ord2 = (OrdenacaoDataTpfa) o2;

                            String data1 = Data.parseDate( ord1.getData(  ) );
                            String data2 = Data.parseDate( ord2.getData(  ) );

                            //dd/mm/yyyy -> yyyymmaa
                            data1 = data1.substring( 6 ) + data1.substring( 3, 5 ) + data1.substring( 0, 2 );
                            data2 = data2.substring( 6 ) + data2.substring( 3, 5 ) + data2.substring( 0, 2 );

                            return data1.compareTo( data2 );
                        }
                    } );
            } else
            {
                // se nï¿½o tiver acompanhamentos

                // datas fixas
                OrdenacaoDataTpfa ordenacaoDataTpfa = new OrdenacaoDataTpfa(  );
                ordenacaoDataTpfa.setTpfaFixo( OrdenacaoDataTpfa.FUNCAO_INICIO );
                ordenacaoDataTpfa.setLabel( "Início" );
                listOrdenacaoDataTpfa.add( ordenacaoDataTpfa );

                ordenacaoDataTpfa = new OrdenacaoDataTpfa(  );
                ordenacaoDataTpfa.setTpfaFixo( OrdenacaoDataTpfa.FUNCAO_LIMITE );
                ordenacaoDataTpfa.setLabel( "Ac./Fis./Fin." );
                listOrdenacaoDataTpfa.add( ordenacaoDataTpfa );

                // adiciona datas de TPFA variï¿½veis
                Iterator itTpfaCompleta = listTpfaCompleta.iterator(  );

                while ( itTpfaCompleta.hasNext(  ) )
                {
                    TipoFuncAcompTpfa tpfa = (TipoFuncAcompTpfa) itTpfaCompleta.next(  );
                    ordenacaoDataTpfa = new OrdenacaoDataTpfa(  );

                    ordenacaoDataTpfa.setTpfa( tpfa );
                    ordenacaoDataTpfa.setLabel( tpfa.getLabelPosicaoTpfa(  ) );

                    listOrdenacaoDataTpfa.add( ordenacaoDataTpfa );
                }
            }

            return listOrdenacaoDataTpfa;
        } catch ( Exception e )
        {
            this.logger.error( e );
            throw new ECARException( e );
        }
    }

    /**
     * Ordena as Funções de Acompanhamento pela sequencia definida na estrutura.
     * @param funcoes
     * @param estrutura
     * @return List
     * @throws ECARException
     */
    public List<TipoFuncAcompTpfa> ordenarTpfaBySequencia(EstruturaEtt estrutura )
                                throws ECARException
    {
        EstruturaTipoFuncAcompDao estruturaTipoFuncAcompDao = new EstruturaTipoFuncAcompDao( null );
        List<TipoFuncAcompTpfa> retorno = new ArrayList();
        
         StringBuilder sb = new StringBuilder("select tpfa from TipoFuncAcompTpfa as tpfa ");
                    sb.append(" where tpfa.codTpfa in (select tf.comp_id.codTpfa from EstrutTpFuncAcmpEtttfa tf where tf.estruturaEtt.codEtt = :codEtt");
                    sb.append(" order by tf.seqApresentTelaCampoEtttfa)");
                                    		
         String codett = estrutura.getCodEtt().toString();       
         List tpfas = this.getSession().createQuery(sb.toString()).setBigInteger("codEtt", new BigInteger(codett)).list();

         return (List<TipoFuncAcompTpfa>) tpfas;
    }
    
    
    
    
    
    
    /**
     * Retorna um bean contendo EstruturaEtt e um List<TipoFuncAcompTpfa> ordenados por EstruturaEtt.
     * <br>
     * Bean: OrdenacaoTpfaEstrutura
     *
     * @return List
     * @throws ECARException
     */
    public List getFuncaoAcompOrderByEstruturas(  )
                                         throws ECARException
    {
        /*
         * Chamar esse mï¿½todo nas telas que utilizam o mï¿½todo AcompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp()
         * para que, ao inves de chamar o ordenarTpfaBySequencia() do mï¿½todo acima para cada item,
         * sï¿½ passar como parametro o bean contendo a estrutura e a lista de funï¿½ï¿½es ordenadas na tela.
         *
         * A idï¿½ia ï¿½ para nï¿½o chamar esse mï¿½todo para cada item a ser exibido.
         *
         * Lembrar de verificar o tempo de execuï¿½ï¿½o da tela para verificar o desempenho.
         */
        List retorno = new ArrayList(  );

        List funAcomp = this.listar( TipoFuncAcompTpfa.class,
                                     new String[] { "codTpfa", "asc" } );

//    	List estruturas = new EstruturaDao(null).getEstruturas();
        List estruturas = new EstruturaDao( null ).getListaEstruturas(  );

        for ( Iterator it = estruturas.iterator(  ); it.hasNext(  ); )
        {
            EstruturaEtt estrutura = (EstruturaEtt) it.next(  );

            OrdenacaoTpfaEstrutura o = new OrdenacaoTpfaEstrutura(  );
            o.setEstrutura( estrutura );
            o.setTipoFuncAcomp( this.ordenarTpfaBySequencia(estrutura ) );

            retorno.add( o );
        }

        return retorno;
    }

    /**
     * Retorna um bean contendo EstruturaEtt e um List<TipoFuncAcompTpfa> ordenados por EstruturaEtt.
     * <br>
     * Bean: OrdenacaoTpfaEstrutura
     *
     * @return List
     * @throws ECARException
     */
    public List getFuncaoAcompOrderByEstruturasAtivasInativas(  )
                                         throws ECARException
    {
        /*
         * Chamar esse mï¿½todo nas telas que utilizam o mï¿½todo AcompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp()
         * para que, ao inves de chamar o ordenarTpfaBySequencia() do mï¿½todo acima para cada item,
         * sï¿½ passar como parametro o bean contendo a estrutura e a lista de funï¿½ï¿½es ordenadas na tela.
         *
         * A idï¿½ia ï¿½ para nï¿½o chamar esse mï¿½todo para cada item a ser exibido.
         *
         * Lembrar de verificar o tempo de execuï¿½ï¿½o da tela para verificar o desempenho.
         */
    	
    	
        List retorno = new ArrayList(  );

        List funAcomp = this.listar( TipoFuncAcompTpfa.class,
                                     new String[] { "codTpfa", "asc" } );

//    	List estruturas = new EstruturaDao(null).getEstruturas();
        List estruturas = new EstruturaDao( null ).getListaEstruturasAtivasInativas( null  );

        for ( Iterator it = estruturas.iterator(  ); it.hasNext(  ); )
        {
            EstruturaEtt estrutura = (EstruturaEtt) it.next(  );

            OrdenacaoTpfaEstrutura o = new OrdenacaoTpfaEstrutura(  );
            o.setEstrutura( estrutura );
           	o.setTipoFuncAcomp( this.ordenarTpfaBySequencia(estrutura));
            
            retorno.add( o );
        }
        return retorno;
    }
    
    /**
     * Retorna um bean contendo EstruturaEtt e um List<TipoFuncAcompTpfa> ordenados por EstruturaEtt
     * na ordem hierï¿½rquica do parecer, ou seja, a ordem inversa de apresentaï¿½ï¿½o na tela.
     * <br>
     * Bean: OrdenacaoTpfaEstrutura
     *
     * @return List
     * @throws ECARException
     */
    public List getFuncaoAcompOrderByEstruturasHierarquicamente(  )
        throws ECARException
    {
        List retorno = new ArrayList(  );

        List funAcomp = this.listar( TipoFuncAcompTpfa.class,
                                     new String[] { "codTpfa", "asc" } );

//    	List estruturas = new EstruturaDao(null).getEstruturas();
        List estruturas = new EstruturaDao( null ).getListaEstruturasAtivasInativas( null );

        for ( Iterator it = estruturas.iterator(  ); it.hasNext(  ); )
        {
            EstruturaEtt estrutura = (EstruturaEtt) it.next(  );

            List tpfaHierarquica = this.ordenarTpfaBySequencia(estrutura );
            Collections.reverse( tpfaHierarquica );

            OrdenacaoTpfaEstrutura o = new OrdenacaoTpfaEstrutura(  );
            o.setEstrutura( estrutura );
            o.setTipoFuncAcomp( tpfaHierarquica );

            retorno.add( o );
        }

        return retorno;
    }

    /**
     * @author Robson
     * @return List<TipoFuncAcompTpfa>
     * @since  26/11/2007
     */
    public List getFuncAcompByLabel(  )
    {
        return this.getSession(  ).createCriteria( TipoFuncAcompTpfa.class )
                   .add( Restrictions.eq( "indEmitePosicaoTpfa", Dominios.SIM ) ).addOrder( Order.asc( "labelTpfa" ) )
                   .list(  );
    }

    /**
     * @author Robson
     * @param ta
     * @return List<TipoFuncAcompTpfa>
     * @since 13/12/2007
     */
    public List getFuncAcompComAcesso( TipoAcompanhamentoTa ta )
    {
        return this.getSession(  )
                   .createQuery( "select tpfa " + "from TipoFuncAcompTpfa tpfa " +
                                 "join tpfa.tipoAcompFuncAcompTafcs tafc " + "join tafc.tipoAcompanhamentoTa ta " +
                                 "where (tafc.indObrigatorio = :obrigatorio " + "or tafc.indOpcional = :opcional) " +
                                 "and ta.codTa = :codigo " + "order by tpfa.labelTpfa " ).setString( "obrigatorio", "S" )
                   .setString( "opcional", "S" ).setLong( "codigo",
                                                          ta.getCodTa(  ) ).list(  );
    }

    /**
     * Utilizado na alteraï¿½ï¿½o de referï¿½ncia da geraï¿½ï¿½o de perï¿½odo, caso a referencia jï¿½ exista, o metodo verifica se foram adicionados novas funï¿½ï¿½es de acompanhamento para o tipo de acompanhamento.
     * @param aref
     * @param ta
     * @return List<TipoFuncAcompTpfa>
     * @since 27/05/2009
     */
    public List<TipoFuncAcompTpfa> getFuncAcompArlComAcesso( AcompReferenciaAref aref, TipoAcompanhamentoTa ta )
    {
        //Lista completa da funï¿½ï¿½es de acompanhamentos associadas ao tipo de acompanhamento 
        List<TipoFuncAcompTpfa> listaCompleta = this.getFuncAcompComAcesso( ta );

        //Lista das funï¿½ï¿½es de acompanhamentos jï¿½ inseridos na referencia.
        List<TipoFuncAcompTpfa> listaFuncoesIncluidas = this.getFuncAcompArlComAcesso( aref );

        //Resultado sï¿½o todas as funï¿½ï¿½es jï¿½ incluï¿½das mais o que tem na lista completa e que nï¿½o tem na lista de funï¿½ï¿½es incluidas. 
        List<TipoFuncAcompTpfa> listaResultado = new ArrayList<TipoFuncAcompTpfa>( listaFuncoesIncluidas );

        for ( TipoFuncAcompTpfa tipoFuncAcompTpfa : listaCompleta )
        {
            if ( ! listaFuncoesIncluidas.contains( tipoFuncAcompTpfa ) )
            {
                listaResultado.add( tipoFuncAcompTpfa );
            }
        }

        return listaResultado;
    }

    /**
     * @param aref
     * @author Robson
     * @return List<TipoFuncAcompTpfa>
     * @since 13/12/2007
     */
    public List getFuncAcompArlComAcesso( AcompReferenciaAref aref )
    {
        return this.getSession(  )
                   .createQuery( "select tpfa " + "from TipoFuncAcompTpfa tpfa " +
                                 "join tpfa.acompRefLimitesArls arl " + "join arl.acompReferenciaAref aref " +
                                 "with aref.codAref = :codigo " + "order by tpfa.labelTpfa " ).setLong( "codigo",
                                                                                                        aref.getCodAref(  ) )
                   .list(  );
    }

    /**
     * Procura a data limite equivalente a funï¿½ï¿½o de acompanhamento do relatï¿½rio,
     *
     * @param relatorio
     * @return
     */
    public Date getDataLimiteDoTipoFuncaoDeAcomp( AcompRelatorioArel relatorio )
    {
        Iterator<AcompRefItemLimitesArli> itDataLimites =
            relatorio.getAcompReferenciaItemAri(  ).getAcompRefItemLimitesArlis(  ).iterator(  );

        /*caso o parecer jï¿½ tenha sido informado */
//    	if (relatorio.getDataUltManutArel()!= null ){
//    		return relatorio.getDataUltManutArel();
//    	}

        /*Caso o parecer nï¿½o tenha sido informado recupera a data limite*/
        while ( itDataLimites.hasNext(  ) )
        {
            AcompRefItemLimitesArli dataLimite = (AcompRefItemLimitesArli) itDataLimites.next(  );

            if ( dataLimite.getTipoFuncAcompTpfa(  ).equals( relatorio.getTipoFuncAcompTpfa(  ) ) )
            {
                return dataLimite.getDataLimiteArli(  );
            }
        }

        return null;
    }

    /**
     * Nesta lista serï¿½o adicionadas as funï¿½ï¿½es de acompanhamento pertencentes ao usuï¿½rio (grupos)
     * @param item
     * @param usuario
     * @param gruposUsuario
     * @return
     * @throws ECARException
     */
    public List getFuncoesAcompNaEstruturaDoUsuario( ItemEstruturaIett item, UsuarioUsu usuario, Set gruposUsuario )
                                             throws ECARException
    {
        // EstruturaFuncaoEttf estruturaFuncaoEttf
        // Nesta lista serï¿½o adicionadas as funï¿½ï¿½es de acompanhamento pertencentes ao usuï¿½rio (grupos) logado	
        List listTipoFuncaoUsuario = new ArrayList<TipoFuncAcompTpfa>(  );
        ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao( null );

        // Informaï¿½ï¿½es da estrutura e das funï¿½ï¿½es de acompanhamento que compï¿½em a estrutura

        //estruturaFuncaoEttf.getEstruturaEtt().getEstrutTpFuncAcmpEtttfas()
        Iterator itFuncoesAcompNaEstrutura = item.getEstruturaEtt(  ).getEstrutTpFuncAcmpEtttfas(  ).iterator(  );

        while ( itFuncoesAcompNaEstrutura.hasNext(  ) )
        {
            EstrutTpFuncAcmpEtttfa estrutTpFuncAcmpEtttfa = (EstrutTpFuncAcmpEtttfa) itFuncoesAcompNaEstrutura.next(  );
            TipoFuncAcompTpfa tpfaNaEstrutura = estrutTpFuncAcmpEtttfa.getTipoFuncAcompTpfa(  );

            UsuarioUsu usuarioFuncao = null;
            SisAtributoSatb sisAtributoSatb = null;

            usuarioFuncao = (UsuarioUsu) itemEstruturaDao.getValorFunAcompItemEstrutura( item, tpfaNaEstrutura );

            if ( usuarioFuncao == null )
            {
                sisAtributoSatb = (SisAtributoSatb) itemEstruturaDao.getValorSatbFunAcompItemEstrutura( item,
                                                                                                        tpfaNaEstrutura );
            }

            if ( ( ( usuarioFuncao != null ) && usuarioFuncao.equals( usuario ) ) ||
                     ( ( sisAtributoSatb != null ) && gruposUsuario.contains( sisAtributoSatb ) ) )
            {
                listTipoFuncaoUsuario.add( tpfaNaEstrutura );
            }
        }

        return listTipoFuncaoUsuario;
    }

    /**
    * Retorna true ou false quando uma funï¿½ï¿½o de acompanhamento (objetoAlt) possuir permissao de dar parecer para outra
     * @param objetoAlt
     * @param value
     * @return
    */
    public boolean existePermissaoAlterarParecer( TipoFuncAcompTpfa objetoAlt, Long value )
    {
        boolean retorno = false;
        boolean encontrouTPFA = false;

        if ( ( objetoAlt.getTipoFuncAcompTpfasPermiteAlterarInferior(  ) != null ) &&
                 ( objetoAlt.getTipoFuncAcompTpfasPermiteAlterarInferior(  ).size(  ) > 0 ) )
        {
            for ( Object obj : objetoAlt.getTipoFuncAcompTpfasPermiteAlterarInferior(  ) )
            {
                TipoFuncAcompTpfaPermiteAlterar tipoFAPA = (TipoFuncAcompTpfaPermiteAlterar) obj;

                if ( tipoFAPA.getComp_id(  ).getCod_inferior_tpfapa(  ).getCodTpfa(  ).longValue(  ) == value.longValue(  ) )
                {
                    encontrouTPFA = true;

                    if ( "S".equalsIgnoreCase( tipoFAPA.getPermiteAlterarParecer(  ) ) )
                    {
                        retorno = true;
                    }
                }
            }
        }

        return retorno;
    }
    
    /**
     * Retorna a lista de funções superiores ordenada pela hierarquia
     * @param tipoFuncAcompTpfa
     * @return
     */
    public List<TipoFuncAcompTpfa> getListaTpfaSuperioresOrderByHierarquia(TipoFuncAcompTpfa tipoFuncAcompTpfa) {
    	List<TipoFuncAcompTpfa> tpfas = new ArrayList<TipoFuncAcompTpfa>();
    	if (tipoFuncAcompTpfa != null){
    		TipoFuncAcompTpfa tpfaLocal = tipoFuncAcompTpfa;
    		while (tpfaLocal.getTipoFuncAcompTpfa() != null){
    			tpfaLocal = tpfaLocal.getTipoFuncAcompTpfa();
    			if (existePermissaoAlterarParecer(tpfaLocal, tipoFuncAcompTpfa.getCodTpfa())){
    				tpfas.add(0, tpfaLocal);
    			}
    		}
    	}
    	return tpfas;
    }
    
    /**
     * Retorna uma lista com os acompanhamentos obrigatórios de uma estrutura
     * @param estrutura
     * @return List
     * @throws ECARException
     */
    public List<TipoFuncAcompTpfa> getTipoAcompObrigatorioDaEstrutura(EstruturaEtt estrutura )
                                throws ECARException
    {
        List<TipoFuncAcompTpfa> retorno = new ArrayList();
        
         StringBuilder sb = new StringBuilder("select tpfa from TipoFuncAcompTpfa as tpfa ");
         sb.append("join tpfa.tipoAcompFuncAcompTafcs tafc ");
         sb.append(" where tpfa.codTpfa in (select tf.comp_id.codTpfa from EstrutTpFuncAcmpEtttfa tf where tf.estruturaEtt.codEtt = :codEtt");
         sb.append(" order by tf.seqApresentTelaCampoEtttfa)");
         sb.append(" and tafc.indObrigatorio = :obrigatorio");
                                    		
         String codett = estrutura.getCodEtt().toString();       
         List tpfas = this.getSession().createQuery(sb.toString()).setBigInteger("codEtt", new BigInteger(codett)).setString( "obrigatorio", "S" ).list();

         return (List<TipoFuncAcompTpfa>) tpfas;
    }    
}
