package ru.job4j.siteparser.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzMain {
    public static void main(String[] args) throws SchedulerException {

        JobDetail job = JobBuilder.newJob(QuartzJob.class)
                .withIdentity("myJob", "group1")
                .usingJobData("property", args[0])
                .build();
        Trigger t1 = TriggerBuilder.newTrigger().withIdentity("CronTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0 0 12 1/1 * ? *")).build();
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, t1);




    }


}
