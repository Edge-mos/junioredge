package ru.job4j.siteparser.quartz;

import org.quartz.*;
import ru.job4j.siteparser.controller.ParserController;

import java.time.LocalDateTime;

public class QuartzJob implements Job {

    public QuartzJob() {

    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Current date: ".concat(LocalDateTime.now().toString()));

        JobKey key = context.getJobDetail().getKey();
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String property = dataMap.getString("property");
        ParserController controller = new ParserController(property);
        controller.parse();
        controller.insert();

    }
}
