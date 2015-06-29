package ecar.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrtIndResulLocalIettirl;
import ecar.pojo.ItemEstrutFisicoIettf;
import ecar.pojo.LocalItemLit;

/**
 *
 * @author 70744416353
 */
public class ItemEstrtIndResultLocalIettirlDao
    extends Dao
{
    /**
     *
     * @param request
     */
    public ItemEstrtIndResultLocalIettirlDao( HttpServletRequest request )
    {
        super(  );
        this.request = request;
    }

    /**
     *
     * @param codIettir Codigo do Item Estrutura Indicador de Resultado
     * @param codExe Exercicio
     * @param codLit Local
     * @param qtdePrevista Quantidade Prevista
     * @throws ECARException
     */
    public void salvarByLocal( Long codIettir, Long mes, Long ano, Long codLit, Double qtdePrevista, ItemEstrutFisicoIettf itemFisicoLocal )
                       throws ECARException
    {
        Transaction tx = null;

        try
        {
            tx = session.beginTransaction(  );

            ItemEstrtIndResulIettr itemEstrtIndResulIettr =
                (ItemEstrtIndResulIettr) this.buscar( ItemEstrtIndResulIettr.class, codIettir );

            LocalItemLit localItemLit = (LocalItemLit) this.buscar( LocalItemLit.class, codLit );

            ItemEstrtIndResulLocalIettirl itemEstrtIndResulLocalIettirl = new ItemEstrtIndResulLocalIettirl(  );
            
			ItemEstrutFisicoIettf itemEstrutFisico = itemFisicoLocal;
			
			
			
			if (itemEstrutFisico == null){
				/* Mantis 0010128 - Qtd prevista não é mais informado por exercício
				 * Mudou a pk. não usa mais chave composta
				 * */
				itemEstrutFisico = new ItemEstrutFisicoIettf();				
				itemEstrutFisico.setItemEstrtIndResulIettr(itemEstrtIndResulIettr);
				itemEstrutFisico.setDataUltManutencao(new Date());
				itemEstrutFisico.setDataInclusaoIettf(new Date());
				itemEstrutFisico.setIndAtivoIettf("S");
				itemEstrutFisico.setUsuarioUsuManutencao(((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
//				pojoHistorico.getItemEstrutFisicoIettfs().add(itemEstrutFisico);
				itemEstrutFisico.setQtdPrevistaIettf(qtdePrevista);
				itemEstrutFisico.setMesIettf(mes.intValue());
				itemEstrutFisico.setAnoIettf(ano.intValue());
				session.save(itemEstrutFisico);				
			}
			else
			{
				itemEstrutFisico.setQtdPrevistaIettf(itemEstrutFisico.getQtdPrevistaIettf() + qtdePrevista);
				session.update(itemEstrutFisico);
			}

            itemEstrtIndResulLocalIettirl.setDataInclusaoIettirl( new Date(  ) );
            //Mantis 0010128 - Qtd prevista não é mais informado por exercício
            //itemEstrtIndResulLocalIettirl.setExercicioExe( exercicioExe );
            
            itemEstrtIndResulLocalIettirl.setLocalItemLit( localItemLit );
            itemEstrtIndResulLocalIettirl.setQtdPrevistaIettirl( qtdePrevista );
            itemEstrtIndResulLocalIettirl.setIndAtivoIettirl( "S" );
            itemEstrtIndResulLocalIettirl.setItemEstrutFisicoIettf(itemEstrutFisico);
            
            session.save( itemEstrtIndResulLocalIettirl );
            tx.commit(  );
        } catch ( Exception e )
        {
            if ( tx != null )
            {
                tx.rollback(  );
            }

            this.logger.error( e );
            throw new ECARException( "erro.hibernateException" );
        }
    }

    
    /**
     * Retorna uma lista dos valores de quantidade previstas por local
     * para todos os exercicios.
     *  
     * Retorna null se não existe nenhum previsto por local cadastrado
     * @return
     */
    public List<ItemEstrtIndResulLocalIettirl> getQtdePrevistaPorLocal(Long codIettir){

    	List<ItemEstrtIndResulLocalIettirl> previstos = null;
    	try
        {
            StringBuilder sb = new StringBuilder(  );
            sb.append( "from ItemEstrtIndResulLocalIettirl bean" );
            sb.append( " where " );
            sb.append( " bean.itemEsrtIndResulIettr.codIettir = :codIettir " );
//            sb.append( " and bean.indAtivoIettirl = :status order by cod_exe" );

            Query query = session.createQuery( sb.toString(  ) );

            query.setLong( "codIettir",
                           codIettir.longValue(  ) );
            query.setString( "status", "S" );

            //Object retorno = query.uniqueResult();
            List retorno = query.list();
            
            if ( ( retorno != null ) && retorno instanceof List )
            {
            	
            	previstos = new ArrayList<ItemEstrtIndResulLocalIettirl>();
                for ( Iterator iter = retorno.iterator(); iter.hasNext(); )
                {
                    ItemEstrtIndResulLocalIettirl element = (ItemEstrtIndResulLocalIettirl) iter.next(  );
                    previstos.add(element);
                }

                return previstos;
            }
        } catch ( HibernateException e )
        {
            e.printStackTrace( System.out );
            this.logger.error( e );

        }

        return previstos;
    }
    
    
    
    /**
     * Recupera objeto de previsto indicador de resultado por local
     * @param codIettir Codigo Item Estrutura Indicador de Resultado
     * @param mes 
     * @param ano 
     * @param codLitl codigo Local
     * @return ItemEstrtIndResulLocalIettirl
     */
    public ItemEstrtIndResulLocalIettirl getQtdePrevistaByLocal( Long codIettir, Long codExe, Long codLitl )
    {
//        try
//        {
//            StringBuilder sb = new StringBuilder(  );
//            sb.append( "from ItemEstrtIndResulLocalIettirl bean" );
//            sb.append( " where " );
//            sb.append( " bean.itemEsrtIndResulIettr.codIettir = :codIettir " );
//            sb.append( " and bean.exercicioExe.codExe = :codExe " );
//            sb.append( " and bean.localItemLit.codLit = :codLitl " );
//            sb.append( " and bean.indAtivoIettirl = :status " );
//
//            Query query = session.createQuery( sb.toString(  ) );
//
//            query.setLong( "codIettir",
//                           codIettir.longValue(  ) );
//            query.setLong( "codExe",
//                           codExe.longValue(  ) );
//            query.setLong( "codLitl",
//                           codLitl.longValue(  ) );
//            query.setString( "status", "S" );
//
//            //Object retorno = query.uniqueResult();
//            List retorno = query.list(  );
//
//            if ( ( retorno != null ) && retorno instanceof List )
//            {
//                for ( Iterator iter = retorno.iterator(  ); iter.hasNext(  ); )
//                {
//                    ItemEstrtIndResulLocalIettirl element = (ItemEstrtIndResulLocalIettirl) iter.next(  );
//
//                    return element;
//                }
//
//                //return (ItemEstrtIndResulLocalIettirl)retorno;
//            }
//        } catch ( HibernateException e )
//        {
//            e.printStackTrace( System.out );
//            this.logger.error( e );
//
//            return new ItemEstrtIndResulLocalIettirl(  );
//        }

        return new ItemEstrtIndResulLocalIettirl(  );
    }

    private Double getQtdePrevistaBySomaSubLocal( Long codIettir, Long codLitl, Long exe )
    {
        final LocalItemDao localDao = new LocalItemDao( request );
        ArrayList<Long> listaSubLocalPK = new ArrayList<Long>(  );

        try
        {
//            LocalItemLit localChave = (LocalItemLit) localDao.buscar( LocalItemLit.class, codLitl );
//
//            Set locais = localChave.getLocalItemHierarquiaLithsByCodLitPai(  );
//
//            for ( Iterator iter = locais.iterator(  ); iter.hasNext(  ); )
//            {
//                LocalItemLit loc = (LocalItemLit) iter.next(  );
//                listaSubLocalPK.add( loc.getCodLit(  ) );
//            }
//
//            StringBuilder sb = new StringBuilder(  );
//            sb.append( "select sum(bean.qtdPrevistaIettirl) " );
//            sb.append( "from ItemEstrtIndResulLocalIettirl bean" );
//            sb.append( " where " );
//            sb.append( " bean.itemEsrtIndResulIettr.codIettir = :codIettir " );
//            sb.append( " and bean.exercicioExe.codExe = :exercicio " );
//            sb.append( " and bean.localItemLit.codLit in (:listaLitl) " );
//            sb.append( " and bean.indAtivoIettirl = :status " );
//
//            Query query = session.createQuery( sb.toString(  ) );
//
//            query.setLong( "codIettir",
//                           codIettir.longValue(  ) );
//            query.setLong( "exercicio",
//                           exe.longValue(  ) );
//            query.setParameterList( "listaLitl", listaSubLocalPK );
//            query.setString( "status", "S" );
//
//            Object retorno = query.uniqueResult(  );
//
//            if ( ( retorno != null ) && retorno instanceof Double )
//            {
//                return (Double) retorno;
//            } else
//            {
                return 0D;
//            }
        } catch ( Exception e )
        {
            logger.error( e );

            return 0D;
        }
    }

    /**
     *
     * @param codIettir
     * @param codLitl
     * @return
     */
    public List<Double> getListQtdePrevistaByLocal( Long codIettir, Long codLitl )
    {
        try
        {
            final ExercicioDao exercicioDao = new ExercicioDao( request );
            ArrayList<Double> valores = new ArrayList<Double>(  );

            // carrega todos os exercicios do periodo de 2008 / 2011
//            List listaExercicios = exercicioDao.getExercicioByPeriodicidade( 2L );
//
//            for ( Iterator iter = listaExercicios.iterator(  ); iter.hasNext(  ); )
//            {
//                ExercicioExe exe = (ExercicioExe) iter.next(  );
//                Double soma = getQtdePrevistaBySomaSubLocal( codIettir,
//                                                             codLitl,
//                                                             exe.getCodExe(  ) );
//                valores.add( soma );
//            }

            return valores;
        } catch ( Exception e )
        {
            logger.error( e );

            return null;
        }
    }

    /**
     * Recupera objeto de previsto indicador de resultado por local
     * @param codIettir Codigo Item Estrutura Indicador de Resultado
     * @param exercicio
     * @param codLitl codigo Local
     * @return ItemEstrtIndResulLocalIettirl
     */
    public Double getSomaQtdePrevistaByLocal( Long codIettir, ArrayList<Long> exercicio, Long codLitl )
    {
        try
        {
//            StringBuilder sb = new StringBuilder(  );
//            sb.append( "select sum(bean.qtdPrevistaIettirl) " );
//            sb.append( "from ItemEstrtIndResulLocalIettirl bean" );
//            sb.append( " where " );
//            sb.append( " bean.itemEsrtIndResulIettr.codIettir = :codIettir " );
//            sb.append( " and bean.exercicioExe.codExe in (:listaExercicios) " );
//            sb.append( " and bean.localItemLit.codLit = :codLitl " );
//            sb.append( " and bean.indAtivoIettirl = :status " );
//
//            Query query = session.createQuery( sb.toString(  ) );
//
//            query.setLong( "codIettir",
//                           codIettir.longValue(  ) );
//            query.setParameterList( "listaExercicios", exercicio );
//            query.setLong( "codLitl",
//                           codLitl.longValue(  ) );
//            query.setString( "status", "S" );
//
//            Object retorno = query.uniqueResult(  );
//
//            if ( ( retorno != null ) && retorno instanceof Double )
//            {
//                return (Double) retorno;
        	
//            return (Double) 0D;
//            }
        } catch ( HibernateException e )
        {
            e.printStackTrace( System.out );
            this.logger.error( e );

            return null;
        }

        return new Double( 0D );
    }

    /**
     *
     * @param codIettir
     * @return
     */
    public int deleteBycodIettf( Long codIettf )
    {
        try
        {
            StringBuilder sb = new StringBuilder(  );
            sb.append( " delete from ItemEstrtIndResulLocalIettirl il " );
            sb.append( " where " );
            sb.append( " il.itemEstrutFisicoIettf.codIettf = :codIettf " );

            Query query = session.createQuery( sb.toString(  ) );

            query.setLong( "codIettf",
                           codIettf.longValue(  ) );

            int rowcount = query.executeUpdate(  );

            return rowcount;
        } catch ( HibernateException e )
        {
            e.printStackTrace( System.out );
            this.logger.error( e );

            return 0;
        }
    }
    /**
     * Conta a quantidade de registros de previstos por local existentes 
     * dada uma lista de locais e um código de item de estrutura.
     * 
     * @param codigosDeLocais - Lista com os códigos de locais
     * @param codIett         - Código do Item de estrutura
     * @return
     */
    public Long countByLocaisItemEstrutura(List<Long> codigosDeLocais, Long codIett){
        try
        {	
        	StringBuilder sb = new StringBuilder(  );
        	sb.append( " select count(*) from ItemEstrtIndResulLocalIettirl as IETTIRL " );			
			sb.append( " where IETTIRL.localItemLit.codLit in (:locais) " );			
			sb.append( " and IETTIRL.itemEstrutFisicoIettf.itemEstrtIndResulIettr.itemEstruturaIett.codIett = :codIett " );
			Query query = this.getSession().createQuery(sb.toString());
			query.setParameterList("locais", codigosDeLocais);
			query.setLong("codIett", codIett);
			query.setMaxResults(1);

			Long valor = (Long)query.uniqueResult();
			
			return valor;
        
        } catch ( HibernateException e )
        {
            e.printStackTrace( System.out );
            this.logger.error( e );

            return 0l;
        }
    }
}
