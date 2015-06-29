package ecar.email;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import comum.util.Data;

import ecar.dao.ConfiguracaoDao;
import ecar.pojo.ConfiguracaoCfg;

/**
 * @author N/C
 * @since N/C
 * @version N/C
 */
public class AgendadorPerformance extends TimerTask {

    private static AgendadorPerformance instance = null;
    private final static long INTERVALO = 1000 * 60 * 60 * 24;// a cada 24 horas
    private final static long TEMPO = 1000 * 60 * 60;// a cada 1 horas
    private final static long CICLO = 24;
    // ...

    private Timer timer = null;
    private Date dataAtual;
    private String hora = "00:00";

    /**
         *
         */
    public AgendadorPerformance() {
    }

    /**
     * Singleton!
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return AgendadorEmail
     */
    // Singleton!
    public static AgendadorPerformance getInstance() {
	if (instance == null) {
	    instance = new AgendadorPerformance();
	}
	return instance;
    }

    /**
     * Método chamado pelo Agendador Listener para que tudo comece.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public void disparaTimer() {
	try {

	    ConfiguracaoCfg configCfg = new ConfiguracaoDao(null).getConfiguracao();

	    this.dataAtual = Data.getDataAtual();
	    String data = Data.parseDateHour(this.dataAtual).substring(0, 10)+ " " + this.hora + ":00:000";
		
	    Date dataExecucao;
		
	    if (Data.isPassado(Data.parseDateHour(data)))
		dataExecucao = Data.addDias(1, this.dataAtual);
		else
		dataExecucao = this.dataAtual;

		data = Data.parseDateHour(dataExecucao);
		data = data.substring(0, 10) + " " + this.hora + ":00:000";
		dataExecucao = Data.parseDateHour(data);

		if (timer == null)
		    timer = new Timer();

		timer.scheduleAtFixedRate(this, dataExecucao, AgendadorPerformance.INTERVALO);
		
	    
	} catch (Exception e) {
	    org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	}

    }

    /**
     * Implementação do Método run() da classe abstrata TimerTask.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public void run() {
    	/*
	org.apache.log4j.Logger.getLogger(this.getClass()).info("Iniciando verificação de performance " + Data.getDataAtual() + " " + Data.getHoraAtual(true));

	try {

	    Date data = Data.parseDateHour(Data.parseDateHour(Data.getDataAtual()).substring(0, 10) + " 00:00:00:000");
	    
	    Performance perf = new Performance();
	    perf.avaliacaoHibernate(HibernateUtil.currentSessionFactory().getStatistics(), false, CICLO, TEMPO, "e-Car " +Data.parseDateHourMinuteSecond(Data.getDataAtual()).toString());

	    org.apache.log4j.Logger.getLogger(this.getClass()).info("Verificando performance" + Data.getDataAtual() + " " + Data.getHoraAtual(true));
	    
	} catch (Exception e) {
	    org.apache.log4j.Logger.getLogger(this.getClass()).error(e);

	}
	*/
    }

} // fim class