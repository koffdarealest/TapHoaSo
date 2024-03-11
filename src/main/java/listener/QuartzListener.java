package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.annotation.WebServlet;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import scheduler.PostConfirmScheduler;

@WebListener
public class QuartzListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = null;
        SimpleScheduleBuilder repeatTime = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(60*10).repeatForever();
        try {
            scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        JobDetail jobDetail = JobBuilder.newJob(PostConfirmScheduler.class).withIdentity("PostConfirmJob", "group1").build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("PostConfirmTrigger", "group1")
                .startNow().withSchedule(repeatTime).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
