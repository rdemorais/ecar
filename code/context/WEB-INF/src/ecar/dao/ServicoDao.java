package ecar.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;
import comum.util.Data;

import ecar.exception.ECARException;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.HistoricoPtcH;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ParametroPar;
import ecar.pojo.PontoCriticoPtc;
import ecar.pojo.ServicoParametroSerPar;
import ecar.pojo.ServicoSer;

/**
 *
 * @author 70744416353
 */
public class ServicoDao
    extends Dao
{
    /**
     *
     * @param request
     */
    public ServicoDao( HttpServletRequest request )
    {
        super(  );
        super.request = request;
    }

    /**
     * (1)Apura��o de Conclus�o de Datas Cr�ticas (Pontos Cr�ticos)
     *
     * Calcular o percentual de Datas Cr�ticas que foram solucionadas
     * (conclu�das) - basear o c�lculo nos dados que estiverem no
     * cadastro/hist�rico de datas cr�ticas do item. O c�lculo dever� ser
     * realizado em fun��o do n�mero de datas cr�ticas conclu�das at� a data
     * base passada como par�metro.
     *
     * Basear os c�lculos no estado do item no dia da data base
     *
     * @param itemEstrutura
     *            item para o qual se deseja fazer as apura��es
     * @param dataBase
     *            dia de referencia para realiza��o das apura��es
     * @return
     * @throws ECARException
     * @author 05110500460
     */
    public double apuracaoConclusaoPontosCriticos( ItemEstruturaIett itemEstrutura, Date dataBase )
                                           throws ECARException
    {
        double numDataCadastrada = 0;
        double numDataConcluida = 0;
        PontoCriticoDao pontoCriticoDao = new PontoCriticoDao( null );

        if ( ( itemEstrutura == null ) || ( dataBase == null ) )
        {
            throw new ECARException(  );
        }

        List<PontoCriticoPtc> pontosCriticos =
            pontoCriticoDao.listarItensIncluidosAntesDaDataBase( itemEstrutura,
                                                                 Data.addDias( 1, dataBase ) );

        if ( ( pontosCriticos != null ) && ( pontosCriticos.size(  ) > 0 ) )
        {
            numDataCadastrada = getTotalPontosCriticos( pontosCriticos, dataBase );
        }

        if ( numDataCadastrada == 0 )
        {
            return numDataCadastrada;
        }

        for ( Iterator itPontosCriticos = pontosCriticos.iterator(  ); itPontosCriticos.hasNext(  ); )
        {
            PontoCriticoPtc pontoCritico = (PontoCriticoPtc) itPontosCriticos.next(  );

            // se a �ltima atualiza��o no dia ou antes da dataBase
            if ( ( 
                         ( pontoCritico.getDataUltManutencaoPtc(  ) != null ) &&
                         ( Data.compareAnoMesDia( pontoCritico.getDataUltManutencaoPtc(  ),
                                                      dataBase ) <= 0 )
                      ) ||
                     ( pontoCritico.getDataUltManutencaoPtc(  ) == null ) )
            {
                // Verificar se o pontoCritico est� conclu�do na data Base
                if ( ( pontoCritico.getDataSolucaoPtc(  ) != null ) &&
                         ( Data.compareAnoMesDia( pontoCritico.getDataSolucaoPtc(  ),
                                                      dataBase ) <= 0 ) &&
                         ( ! estaExcluido( pontoCritico ) ) )
                {
                    numDataConcluida++;
                }
            } else
            {
                HistoricoPtcH historico = recuperaEstadoDasDatasNaDataBase( pontoCritico, dataBase );

                // Verificar se no historico o Ponto Critico est� concluido na
                // data Base
                if ( ( historico != null ) &&
                         ( historico.getDataSolucaoPtc(  ) != null ) &&
                         ( Data.compareAnoMesDia( historico.getDataSolucaoPtc(  ),
                                                      dataBase ) <= 0 ) &&
                         ( ! estaExcluido( historico ) ) )
                {
                    numDataConcluida++;
                }
            }
        } // fim for

        return ( 100 * numDataConcluida ) / numDataCadastrada;
    }

    /**
     * (1)Apura��o de Conclus�o de Datas Cr�ticas (Pontos Cr�ticos)
     *
     * Calcular o percentual de Datas Cr�ticas que foram solucionadas
     * (conclu�das) - basear o c�lculo nos dados que estiverem no
     * cadastro/hist�rico de datas cr�ticas do item. O c�lculo dever� ser
     * realizado em fun��o do n�mero de datas cr�ticas conclu�das at� a data
     * base passada como par�metro.
     *
     * Basear os c�lculos no estado do item no dia da data base
     *
     * @param codIett
     * @param dataBase
     *            dia de refer�ncia para realiza��o das apura��es no formato
     *            dd/mm/aaaa
     *
     * @return percentual de Pontos Cr�ticos conclu�dos no dia da dataBase
     *
     * @throws ECARException
     * @author 05110500460
     */
    public double apuracaoConclusaoPontosCriticos( String codIett, String dataBase )
                                           throws ECARException
    {
        ItemEstruturaIett itemEstrutura =
            (ItemEstruturaIett) this.buscar( ItemEstruturaIett.class,
                                             Long.valueOf( codIett ) );
        Date data = Data.parseDate( dataBase );

        if ( ( itemEstrutura == null ) || ( data == null ) )
        {
            throw new ECARException(  );
        }

        return this.apuracaoConclusaoPontosCriticos( itemEstrutura, data );
    }

    /**
     * (2)Apura��o de Atraso de Conclus�o de Datas Cr�ticas (Pontos Cr�ticos)
     *
     * Calcular o percentual de Datas Cr�ticas que foram conclu�das
     * (solucionadas) depois da data fim - basear o c�lculo nos dados que
     * estiverem no cadastro do item. O c�lculo dever� ser realizado em fun��o
     * do n�mero de datas cr�ticas conclu�das depois da data fim, ou datas
     * cr�ticas n�o conclu�das e cuja data de conclus�o prevista j� ultrapassa a
     * data base passada como par�metro.
     *
     * Basear os c�lculos no estado do item no dia da data base.
     *
     * @param itemEstrutura
     *            item para o qual se deseja fazer as apura��es
     * @param dataBase
     *            dia de refer�ncia para realiza��o das apura��es
     *
     * @return percentual de Pontos Cr�ticos com atraso de conclus�o no dia da
     *         dataBase
     *
     * @throws ECARException
     * @author 05110500460
     */
    public double apuracaoAtrasoConclusaoPontosCriticos( ItemEstruturaIett itemEstrutura, Date dataBase )
                                                 throws ECARException
    {
        PontoCriticoDao pontoCriticoDao = new PontoCriticoDao( null );
        double numDataCadastrada = 0;
        double numDataAtrasoConclusao = 0;

        if ( itemEstrutura == null )
        {
            throw new ECARException(  );
        }

        List<PontoCriticoPtc> pontosCriticos =
            pontoCriticoDao.listarItensIncluidosAntesDaDataBase( itemEstrutura,
                                                                 Data.addDias( 1, dataBase ) );

        if ( ( pontosCriticos != null ) && ( pontosCriticos.size(  ) > 0 ) )
        {
            numDataCadastrada = getTotalPontosCriticos( pontosCriticos, dataBase );
        }

        if ( numDataCadastrada == 0 )
        {
            return numDataCadastrada;
        }

        for ( Iterator itPontosCriticos = pontosCriticos.iterator(  ); itPontosCriticos.hasNext(  ); )
        {
            PontoCriticoPtc pontoCritico = (PontoCriticoPtc) itPontosCriticos.next(  );

            // Ultima modifica��o antes da database
            if ( ( 
                         ( pontoCritico.getDataUltManutencaoPtc(  ) != null ) &&
                         ( Data.compareAnoMesDia( pontoCritico.getDataUltManutencaoPtc(  ),
                                                      dataBase ) <= 0 )
                      ) ||
                     ( pontoCritico.getDataUltManutencaoPtc(  ) == null ) )
            { // N�o houve modifica��o

                if ( ( 
                             ( pontoCritico.getDataLimitePtc(  ) != null ) && ( ! estaExcluido( pontoCritico ) )// Verifica se a Data Limite � menor do que a data da solu��o
                              &&
                             ( 
                                     ( pontoCritico.getDataSolucaoPtc(  ) != null ) &&
                                     ( 
                                             Data.compareAnoMesDia( 
                                                                        pontoCritico.getDataLimitePtc(  ),
                                                                            pontoCritico.getDataSolucaoPtc(  ) ) < 0
                                          )
                                  )
                          )// Verifica se j� passou da data base e n�o foi  solucionado
                          ||
                         ( 
                                 ( pontoCritico.getDataSolucaoPtc(  ) == null ) &&
                                 ( Data.compareAnoMesDia( 
                                                              pontoCritico.getDataLimitePtc(  ),
                                                                  dataBase ) < 0 )
                              ) )
                {
                    numDataAtrasoConclusao++;
                }
            } else
            {
                // buscar no historico
                HistoricoPtcH historico = this.recuperaEstadoDasDatasNaDataBase( pontoCritico, dataBase );

                if ( ( historico != null ) &&
                         ( historico.getDataLimitePtc(  ) != null ) &&
                         ( ! estaExcluido( historico ) ) &&
                         (  // Verifica se no dia da Data Base havia solu��o

                                 ( 
                                         ( historico.getDataSolucaoPtc(  ) != null )// Verifica no hist�rico se a Data Limite � menor do que a data da solu��o
                                          &&
                                         ( 
                                                 Data.compareAnoMesDia( 
                                                                            historico.getDataLimitePtc(  ),
                                                                                historico.getDataSolucaoPtc(  ) ) < 0
                                              )
                                      )// Verifica se j� passou da data base e n�o foi solucionado
                                  ||
                                 ( 
                                         ( historico.getDataSolucaoPtc(  ) == null ) &&
                                         ( Data.compareAnoMesDia( 
                                                                      historico.getDataLimitePtc(  ),
                                                                          dataBase ) < 0 )
                                      )
                              ) )
                {
                    numDataAtrasoConclusao++;
                }
            } // fim else - historico
        } // fim for

        return ( 100 * numDataAtrasoConclusao ) / numDataCadastrada;
    }

    /**
     * (2)Apura��o de Atraso de Conclus�o de Datas Cr�ticas (Pontos Cr�ticos)
     *
     * Calcular o percentual de Datas Cr�ticas que foram conclu�das
     * (solucionadas) depois da data fim - basear o c�lculo nos dados que
     * estiverem no cadastro do item. O c�lculo dever� ser realizado em fun��o
     * do n�mero de datas cr�ticas conclu�das depois da data fim, ou datas
     * cr�ticas n�o conclu�das e cuja data de conclus�o prevista j� ultrapassa a
     * data base passada como par�metro.
     *
     * Basear os c�lculos no estado do item no dia da data base.
     *
     * @param codIett
     * @param dataBase
     *            dia de refer�ncia para realiza��o das apura��es no formato
     *            dd/mm/aaaa
     *
     * @return percentual de Pontos Cr�ticos com atraso de conclus�o no dia da
     *         dataBase
     *
     * @throws ECARException
     * @author 05110500460
     */
    public double apuracaoAtrasoConclusaoPontosCriticos( String codIett, String dataBase )
                                                 throws ECARException
    {
        ItemEstruturaIett itemEstrutura =
            (ItemEstruturaIett) this.buscar( ItemEstruturaIett.class,
                                             Long.valueOf( codIett ) );
        Date data = Data.parseDate( dataBase );

        if ( ( itemEstrutura == null ) || ( data == null ) )
        {
            throw new ECARException(  );
        }

        return apuracaoAtrasoConclusaoPontosCriticos( itemEstrutura, data );
    }

    /**
     * (3)Apura��o de Total de Datas Cr�ticas (Pontos Cr�ticos)
     *
     * Calcular a quantidade total de Datas Cr�ticas cadastradas para um item -
     * basear o c�lculo nos dados que estiverem no cadastro do item.
     *
     * Basear os c�lculos no estado do item no dia da data base
     *
     *
     * @param itemEstrutura
     *            item para o qual se deseja fazer as apura��es
     * @param dataBase
     *            dia de refer�ncia para realiza��o das apura��es
     *
     * @return quantidade de Pontos Cr�ticos no cadastro no dia da dataBase
     *
     * @throws ECARException
     * @author 05110500460
     */
    public double apuracaoTotalPontosCriticos( ItemEstruturaIett itemEstrutura, Date dataBase )
                                       throws ECARException
    {
        if ( ( itemEstrutura == null ) || ( dataBase == null ) )
        {
            throw new ECARException(  );
        }

        double contTotal = 0;
        List<PontoCriticoPtc> list =
            ( new PontoCriticoDao( null ) ).listarItensIncluidosAntesDaDataBase( itemEstrutura,
                                                                                 Data.addDias( 1, dataBase ) );

        contTotal = getTotalPontosCriticos( list, dataBase );

        return contTotal;
    }

    /**
     * (3)Apura��o de Total de Datas Cr�ticas (Pontos Cr�ticos)
     *
     * Calcular a quantidade total de Datas Cr�ticas cadastradas para um item -
     * basear o c�lculo nos dados que estiverem no cadastro do item.
     *
     * Basear os c�lculos no estado do item no dia da data base
     *
     *
     * @param codIett
     * @param dataBase
     *            dia de refer�ncia para realiza��o das apura��es no formato
     *            dd/mm/aaaa
     *
     * @return quantidade de Pontos Cr�ticos no cadastro no dia da dataBase
     *
     * @throws ECARException
     * @author 05110500460
     */
    public double apuracaoTotalPontosCriticos( String codIett, String dataBase )
                                       throws ECARException
    {
        ItemEstruturaIett itemEstrutura =
            (ItemEstruturaIett) this.buscar( ItemEstruturaIett.class,
                                             Long.valueOf( codIett ) );
        Date data = Data.parseDate( dataBase );

        if ( ( itemEstrutura == null ) || ( data == null ) )
        {
            throw new ECARException(  );
        }

        return this.apuracaoTotalPontosCriticos( itemEstrutura, data );
    }

    /**
     *
     * (4)Apura��o de Reprograma��o de Datas Cr�ticas (Pontos Cr�ticos) -
     * C�lculo de Extens�o de Prazo
     *
     * Calcular a taxa de replanejamento das datas cr�ticas - basear o c�lculo
     * nos dados que estiverem no cadastro do item e no hist�rico de datas
     * cr�ticas. O c�lculo dever� ser realizado em fun��o do n�mero de extens�es
     * realizadas no prazo das datas cr�ticas do item e o n�mero total de datas
     * cr�ticas.
     *
     * Basear os c�lculos no estado do item no dia da data base
     *
     * @param itemEstrutura
     *            item para o qual se deseja fazer as apura��es
     * @param dataBase
     *            dia de refer�ncia para realiza��o das apura��es
     *
     * @return percentual de Pontos Cr�ticos que reprogramaram exten��o de prazo
     *         at� o dia da Data Base
     *
     * @throws ECARException
     * @author 05110500460
     */
    public double apuracaoReprogramacaoExtensaoPontosCriticos( ItemEstruturaIett itemEstrutura, Date dataBase )
        throws ECARException
    {
        if ( itemEstrutura == null )
        {
            throw new ECARException(  );
        }

        double numDataCadastrada = 0;
        int numDatasExtendidas = 0;
        PontoCriticoDao pontoCriticoDao = new PontoCriticoDao( null );
        List<HistoricoPtcH> listHistPtc = null;
        HistoricoPtcH historicoAnterior = null;
        List<PontoCriticoPtc> pontosCriticos =
            pontoCriticoDao.listarItensIncluidosAntesDaDataBase( itemEstrutura,
                                                                 Data.addDias( 1, dataBase ) );

        if ( ( pontosCriticos != null ) && ( pontosCriticos.size(  ) > 0 ) )
        {
            numDataCadastrada = getTotalPontosCriticos( pontosCriticos, dataBase );
        }

        if ( numDataCadastrada == 0 )
        {
            return numDataCadastrada;
        }

        for ( PontoCriticoPtc pontoCritico : pontosCriticos )
        {
            listHistPtc =
                pontoCriticoDao.listarHistorico( pontoCritico,
                                                 Data.addDias( 1, dataBase ) );
            historicoAnterior = null;

            Set<Date> datasLimites = null;

            // N�o apresentou modifica��o no hist�rico
            if ( listHistPtc.size(  ) == 0 )
            {
                continue;
            }

            datasLimites = new HashSet<Date>( listHistPtc.size(  ) );

            for ( HistoricoPtcH historico : listHistPtc )
            {
                // Se a �ltima manuten��o for antes da dataBase � para considerar a situa��o atual do item
                if ( ( pontoCritico.getDataUltManutencaoPtc(  ) != null ) &&
                         ( Data.compareAnoMesDia( pontoCritico.getDataUltManutencaoPtc(  ),
                                                      dataBase ) <= 0 ) )
                {
                    if ( estaExcluido( pontoCritico ) )
                    {
                        break;
                    }
                    // Verifica se os valores s�o Nulos
                    else if ( ( 
                                      ( pontoCritico.getDataLimitePtc(  ) != null ) && ( historico != null ) &&
                                      ( historico.getDataLimitePtc(  ) != null )
                                   )// Verifica se a data Limite atual � maior do que a apresentada no historico
                                   &&
                                  ( 
                                          Data.compareAnoMesDia( 
                                                                     pontoCritico.getDataLimitePtc(  ),
                                                                         historico.getDataLimitePtc(  ) ) > 0
                                       ) )
                    {
                        numDatasExtendidas++;

                        break; // p�ra o loop de hist�rico
                    } else if ( estaExcluido( pontoCritico ) )
                    {
                        break;
                    }
                }

                if ( ( historico != null ) && ( historicoAnterior != null ) )
                {
                    if ( ( historico.getDataLimitePtc(  ) != null ) &&
                             ( historicoAnterior.getDataLimitePtc(  ) != null ) &&
                             ( ! estaExcluido( historico ) ) &&
                             ( 
                                     Data.compareAnoMesDia( 
                                                                historico.getDataLimitePtc(  ),
                                                                    historicoAnterior.getDataLimitePtc(  ) ) > 0
                                  ) )
                    {
                        numDatasExtendidas++;

                        break;
                    }
                }

                if ( ( historico != null ) && ( historico.getDataLimitePtc(  ) != null ) &&
                         ( ! estaExcluido( historico ) ) )
                {
                    boolean temNoHistorico = false;

                    if ( ( historico.getDataUltManutencaoPtc(  ) == null ) && ( 
                                                                                      historico.getDataLimitePtc(  ) != null
                                                                                   ) )
                    {
                        datasLimites.add( historico.getDataLimitePtc(  ) );
                    } else
                    {
                        for ( Date date : datasLimites )
                        {
                            // Se alguma data anterior for menor do que a data do
                            // historico
                            if ( date.compareTo( historico.getDataLimitePtc(  ) ) < 0 )
                            {
                                numDatasExtendidas++;
                                temNoHistorico = true;

                                break; // loop datasLimites
                            }
                        }

                        if ( temNoHistorico )
                        {
                            break; // para loop de historico
                        }

                        datasLimites.add( historico.getDataLimitePtc(  ) );
                    }
                }

                if ( historico.getDataUltManutencaoPtc(  ) != null )
                {
                    historicoAnterior = historico; // Para comparar com o valor anterior
                }
            } // fim for do loop de historico
        }

        return ( 100 * numDatasExtendidas ) / numDataCadastrada;
    }

    /**
     *
     * (4)Apura��o de Reprograma��o de Datas Cr�ticas (Pontos Cr�ticos) -
     * C�lculo de Extens�o de Prazo
     *
     * Calcular a taxa de replanejamento das datas cr�ticas - basear o c�lculo
     * nos dados que estiverem no cadastro do item e no hist�rico de datas
     * cr�ticas. O c�lculo dever� ser realizado em fun��o do n�mero de extens�es
     * realizadas no prazo das datas cr�ticas do item e o n�mero total de datas
     * cr�ticas.
     *
     * Basear os c�lculos no estado do item no dia da data base
     *
     * @param codIett
     * @param dataBase
     *            dia de refer�ncia para realiza��o das apura��es no formato
     *            dd/mm/aaaa
     *
     * @return percentual de Pontos Cr�ticos que reprogramaram exten��o de prazo
     *         at� o dia da Data Base
     *
     * @throws ECARException
     * @author 05110500460
     */
    public double apuracaoReprogramacaoExtensaoPontosCriticos( String codIett, String dataBase )
        throws ECARException
    {
        ItemEstruturaIett itemEstrutura =
            (ItemEstruturaIett) this.buscar( ItemEstruturaIett.class,
                                             Long.valueOf( codIett ) );
        Date data = Data.parseDate( dataBase );

        if ( ( itemEstrutura == null ) || ( data == null ) )
        {
            throw new ECARException(  );
        }

        return this.apuracaoReprogramacaoExtensaoPontosCriticos( itemEstrutura, data );
    }

    /**
     * (5)Apura��o de Reprograma��o de Datas Cr�ticas (Pontos Cr�ticos) -
     * C�lculo Redu��o de Prazo Calcular a taxa de replanejamento das datas
     * cr�ticas - basear o c�lculo nos dados que estiverem no cadastro do item e
     * no hist�rico de datas cr�ticas. O c�lculo dever� ser realizado em fun��o
     * do n�mero de redu��es realizadas no prazo das datas cr�ticas do item e o
     * n�mero total de datas cr�ticas.
     *
     * Basear os c�lculos no estado do item no dia da data base
     *
     * @param itemEstrutura
     *            item para o qual se deseja fazer as apura��es
     * @param dataBase
     *            dia de refer�ncia para realiza��o das apura��es
     *
     * @return percentual de Pontos Cr�ticos que reprogramaram redu��o de prazo
     *         at� o dia da Data Base
     *
     * @throws ECARException
     * @author 05110500460
     */
    public double apuracaoReprogramacaoReducaoPontosCriticos( ItemEstruturaIett itemEstrutura, Date dataBase )
                                                      throws ECARException
    {
        if ( itemEstrutura == null )
        {
            throw new ECARException(  );
        }

        double numDataCadastrada = 0;
        int numDatasReduzidas = 0;
        PontoCriticoDao pontoCriticoDao = new PontoCriticoDao( null );
        List<HistoricoPtcH> listHistPtc = null;
        List<HistoricoPtcH> setHistPtc = null;
        HistoricoPtcH historicoAnterior = null;
        HistoricoPtcH primeiroRegistro = null; // O primeiro registro no hist�rico � a proje��o inicial

        List<PontoCriticoPtc> pontosCriticos =
            pontoCriticoDao.listarItensIncluidosAntesDaDataBase( itemEstrutura,
                                                                 Data.addDias( 1, dataBase ) ); // listar(itemEstrutura);

        if ( ( pontosCriticos != null ) && ( pontosCriticos.size(  ) > 0 ) )
        {
            numDataCadastrada = getTotalPontosCriticos( pontosCriticos, dataBase );
        }

        if ( numDataCadastrada == 0 )
        {
            return numDataCadastrada;
        }

        for ( PontoCriticoPtc pontoCritico : pontosCriticos )
        {
            listHistPtc =
                pontoCriticoDao.listarHistorico( pontoCritico,
                                                 Data.addDias( 1, dataBase ) );
            historicoAnterior = null;

            Set<Date> datasLimites = null;

            // N�o apresentou modifica��o no hist�rico
            if ( ( listHistPtc == null ) || ( listHistPtc.size(  ) == 0 ) )
            {
                continue;
            }

            datasLimites = new HashSet<Date>( listHistPtc.size(  ) );

            for ( HistoricoPtcH historico : listHistPtc )
            {
                // Se a �ltima manuten��o for antes da dataBase � para considerar a situa��o atual do item
                if ( ( pontoCritico.getDataUltManutencaoPtc(  ) != null ) &&
                         ( Data.compareAnoMesDia( pontoCritico.getDataUltManutencaoPtc(  ),
                                                      dataBase ) <= 0 ) )
                {
                    if ( estaExcluido( pontoCritico ) )
                    {
                        break;
                    }
                    // Verifica se os valores s�o Nulos
                    else if ( ( 
                                      ( pontoCritico.getDataLimitePtc(  ) != null ) && ( historico != null ) &&
                                      ( historico.getDataLimitePtc(  ) != null )
                                   )// Verifica se a Data Limite atual � menor do que a apresentada no historico
                                   &&
                                  ( 
                                          Data.compareAnoMesDia( 
                                                                     pontoCritico.getDataLimitePtc(  ),
                                                                         historico.getDataLimitePtc(  ) ) < 0
                                       ) )
                    {
                        numDatasReduzidas++;

                        break; // p�ra o loop de hist�rico
                    }
                }

                //Compara com valores no hist�rico
                if ( ( historico != null ) && ( historicoAnterior != null ) )
                {
                    if ( ( historico.getDataLimitePtc(  ) != null ) &&
                             ( historicoAnterior.getDataLimitePtc(  ) != null ) &&
                             ( ! estaExcluido( historico ) )//Verifica Data Limite do historico atual � menor do que do registro anterior  
                              &&
                             ( 
                                     Data.compareAnoMesDia( 
                                                                historico.getDataLimitePtc(  ),
                                                                    historicoAnterior.getDataLimitePtc(  ) ) < 0
                                  ) )
                    {
                        numDatasReduzidas++;

                        break; // p�ra o loop de hist�rico
                    }
                }

                if ( ( historico != null ) && ( historico.getDataLimitePtc(  ) != null ) &&
                         ( ! estaExcluido( historico ) ) )
                {
                    boolean temNoHistorico = false;

                    if ( ( historico.getDataUltManutencaoPtc(  ) == null ) && ( 
                                                                                      historico.getDataLimitePtc(  ) != null
                                                                                   ) )
                    {
                        datasLimites.add( historico.getDataLimitePtc(  ) );
                    } else
                    {
                        for ( Date date : datasLimites )
                        {
                            // Se alguma data anterior for maior do que a data do historico atual
                            if ( date.compareTo( historico.getDataLimitePtc(  ) ) > 0 )
                            {
                                numDatasReduzidas++;
                                temNoHistorico = true;

                                break; // loop datasLimites
                            }
                        }

                        if ( temNoHistorico )
                        {
                            break; // para loop de historico
                        }

                        datasLimites.add( historico.getDataLimitePtc(  ) );
                    }
                }

                if ( historico.getDataUltManutencaoPtc(  ) != null )
                {
                    historicoAnterior = historico; // Para comparar com o valor anterior
                }
            } // fim for do loop de historico
        } // fim for PontoCritico

        return ( 100 * numDatasReduzidas ) / numDataCadastrada;
    }

    /**
     *
     * (4)Apura��o de Reprograma��o de Datas Cr�ticas (Pontos Cr�ticos) -
     * C�lculo de Extens�o de Prazo
     *
     * Calcular a taxa de replanejamento das datas cr�ticas - basear o c�lculo
     * nos dados que estiverem no cadastro do item e no hist�rico de datas
     * cr�ticas. O c�lculo dever� ser realizado em fun��o do n�mero de extens�es
     * realizadas no prazo das datas cr�ticas do item e o n�mero total de datas
     * cr�ticas.
     *
     * Basear os c�lculos no estado do item no dia da data base
     *
     * @param codIett
     * @param dataBase
     *            dia de refer�ncia para realiza��o das apura��es
     *
     * @return percentual de Pontos Cr�ticos que reprogramaram redu��o de prazo
     *         at� o dia da Data Base
     *
     * @throws ECARException
     * @author 05110500460
     */
    public double apuracaoReprogramacaoReducaoPontosCriticos( String codIett, String dataBase )
                                                      throws ECARException
    {
        ItemEstruturaIett itemEstrutura =
            (ItemEstruturaIett) this.buscar( ItemEstruturaIett.class,
                                             Long.valueOf( codIett ) );

        Date data = Data.parseDate( dataBase );

        if ( ( itemEstrutura == null ) || ( data == null ) )
        {
            throw new ECARException(  );
        }

        return this.apuracaoReprogramacaoReducaoPontosCriticos( itemEstrutura, data );
    }

    /**
     * Recupera os valores dos atributos: - Data Limite - Data Solu��o No dia
     * passado como parametro
     *
     * @param pontoCritico
     * @param dataBase
     * @return PontoCritico com os valores dos campos DataLimite e DataSolu��o
     *         referentes ao dia da DataBase
     *
     * @author 05110500460
     */
    private HistoricoPtcH recuperaEstadoDasDatasNaDataBase( PontoCriticoPtc pontoCritico, Date dataBase )
                                                    throws ECARException
    {
        PontoCriticoDao ptcDao = new PontoCriticoDao( null );

        // se a ultima manuten��o foi posterior a data base
        if ( ( pontoCritico.getDataUltManutencaoPtc(  ) != null ) &&
                 ( Data.compareAnoMesDia( pontoCritico.getDataUltManutencaoPtc(  ),
                                              dataBase ) > 0 ) )
        {
            // Set<HistoricoPtcH> setHistorico =
            // pontoCritico.getHistoricoPtcHs();
            List<HistoricoPtcH> listHistorico =
                new PontoCriticoDao( null ).listarHistorico( pontoCritico,
                                                             Data.addDias( 1, dataBase ) );

            if ( listHistorico != null )
            {
                for ( HistoricoPtcH historico : listHistorico )
                {
                    // Parametros a recuperar o estado
                    if ( ( historico != null ) &&
                             ( historico.getDataUltManutencaoPtc(  ) != null ) &&
                             ( Data.compareAnoMesDia( historico.getDataUltManutencaoPtc(  ),
                                                          dataBase ) < 0 ) )
                    {
                        return historico;

                        // Verificar no historico a data da inclus�o
                    } else if ( ( historico != null ) &&
                                    ( historico.getDataUltManutencaoPtc(  ) == null ) &&
                                    ( historico.getDataInclusaoPtc(  ) != null ) )
                    {
                        if ( Data.compareAnoMesDia( historico.getDataInclusaoPtc(  ),
                                                        dataBase ) < 0 )
                        {
                            return historico;
                        }
                    }
                } // fim for
            } // fim if
        } // fim else

        return null;
    } // fim recuperaEstadoDasDatasNaDataBase

    /**
     * Retorna um array de objetos com os par�metros setados para o servi�o
     *
     * @param servicoSer
     * @param ari
     * @return Object[]
     */
    public Object[] getParametrosServico( ServicoSer servicoSer, AcompReferenciaItemAri ari )
    {
        Object[] parametros = null;

        if ( servicoSer != null )
        {
            Set setParametros = servicoSer.getServicoParametros(  );

            List listParametros = new ArrayList( setParametros );

            Collections.sort( listParametros,
                              new Comparator(  )
                {
                    public int compare( Object o1, Object o2 )
                    {
                        ServicoParametroSerPar a1 = (ServicoParametroSerPar) o1;
                        ServicoParametroSerPar a2 = (ServicoParametroSerPar) o2;

                        return a1.getSequencia(  ).compareTo( a2.getSequencia(  ) );
                    }
                } );

            Iterator itParametros = listParametros.iterator(  );

            int countParametros = 0;

            parametros = new Object[listParametros.size(  )];

            while ( itParametros.hasNext(  ) )
            {
                ServicoParametroSerPar servicoParametroSerPar = (ServicoParametroSerPar) itParametros.next(  );

                if ( servicoParametroSerPar.getParametroPar(  ).getCodParametroPar(  )
                                               .equals( ParametroPar.IDENTIFICADOR_ITEM_ESTRUTURA ) )
                {
                    parametros[countParametros] = ari.getItemEstruturaIett(  ).getCodIett(  ).toString(  );
                    countParametros++;
                } else if ( servicoParametroSerPar.getParametroPar(  ).getCodParametroPar(  )
                                                      .equals( ParametroPar.DATA_ATUAL ) )
                {
                    Date dataAtual = Data.getDataAtual(  );
                    String dataAtualStr = Data.parseDate( dataAtual );
                    parametros[countParametros] = dataAtualStr;
                    countParametros++;
                } else if ( servicoParametroSerPar.getParametroPar(  ).getCodParametroPar(  )
                                                      .equals( ParametroPar.DATA_LIMITE_REALIZADO_FISICO ) )
                {
                    String dataLimiteRealizadoFisicoStr = Data.parseDate( ari.getDataLimiteAcompFisicoAri(  ) );
                    parametros[countParametros] = dataLimiteRealizadoFisicoStr;
                    countParametros++;
                } else if ( servicoParametroSerPar.getParametroPar(  ).getCodParametroPar(  )
                                                      .equals( ParametroPar.DATA_INICIO_ACOMPANHAMENTO ) )
                {
                    String dataInicioAcompanhamentoStr = Data.parseDate( ari.getDataInicioAri(  ) );
                    parametros[countParametros] = dataInicioAcompanhamentoStr;
                    countParametros++;
                }
            }
        }

        return parametros;
    }

    private boolean estaExcluido( PontoCriticoPtc pontoCritico )
    {
        if ( ( pontoCritico.getIndExcluidoPtc(  ) != null ) && pontoCritico.getIndExcluidoPtc(  ).equals( "S" ) )
        {
            return true;
        }

        return false;
    }

    private boolean estaExcluido( HistoricoPtcH historico )
    {
        if ( ( historico.getIndExcluidoPtc(  ) != null ) && historico.getIndExcluidoPtc(  ).equals( "S" ) )
        {
            return true;
        }

        return false;
    }

    private double getTotalPontosCriticos( List<PontoCriticoPtc> list, Date dataBase )
                                   throws ECARException
    {
        double contTotal = 0;

        for ( PontoCriticoPtc pontoCriticoPtc : list )
        {
            if ( ( pontoCriticoPtc.getDataUltManutencaoPtc(  ) == null ) && ( ! estaExcluido( pontoCriticoPtc ) ) )
            {
                contTotal++;
            } else if ( ( pontoCriticoPtc.getDataUltManutencaoPtc(  ) != null ) &&
                            ( Data.compareAnoMesDia( pontoCriticoPtc.getDataUltManutencaoPtc(  ),
                                                         dataBase ) <= 0 ) &&
                            ( ! estaExcluido( pontoCriticoPtc ) ) )
            {
                contTotal++;
            } else
            {
                HistoricoPtcH historico = recuperaEstadoDasDatasNaDataBase( pontoCriticoPtc, dataBase );

                if ( ( historico != null ) &&
                         ( historico.getDataUltManutencaoPtc(  ) == null ) &&
                         ( ! estaExcluido( historico ) ) )
                {
                    contTotal++;
                } else if ( ( historico != null ) &&
                                ( historico.getDataUltManutencaoPtc(  ) != null ) &&
                                ( Data.compareAnoMesDia( 
                                                             historico.getDataUltManutencaoPtc(  ),
                                                                 dataBase ) <= 0 ) &&
                                ( ! estaExcluido( historico ) ) )
                {
                    contTotal++;
                }
            }
        } // fim for

        return contTotal;
    }
}
