package ecar.jobs.service;

import java.text.ParseException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import org.quartz.impl.StdSchedulerFactory;
import ecar.jobs.service.JobSimpr;

public class AgendaSimpr implements ServletContextListener{
	
	 private Scheduler scheduler;

	 
	 public void init(){
		 

		
	}

	
	public void contextDestroyed(ServletContextEvent arg0) {
		restart();
		
	}

	
	public void contextInitialized(ServletContextEvent arg0) {
		testeInicio();
	}
	
	
	public void restart(){
		try {
			this.scheduler.shutdown(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testeInicio(){
		 System.out.println("entrou no listener");
		 System.out.println("entrou na agenda");
			
		CronTrigger cronTrigger=null;
		try {
			cronTrigger = new CronTrigger("cronTrigger", "grupo", "0 0 6 ? * 5");
			//cronTrigger = new CronTrigger("cronTrigger", "grupo", "0 1 * * * ?");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JobDetail jobDetail = new JobDetail( "JobSimpr", Scheduler.DEFAULT_GROUP, JobSimpr.class );


       try {
           scheduler = new StdSchedulerFactory().getScheduler();
           scheduler.scheduleJob( jobDetail, cronTrigger );
           scheduler.start();
       } catch (Exception ex) {
           ex.printStackTrace();
       }		
	}
	
}

