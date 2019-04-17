package ua.kiev.prog.oauth2.loginviagoogle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.kiev.prog.oauth2.loginviagoogle.model.Task;
import ua.kiev.prog.oauth2.loginviagoogle.services.GeneralService;

import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.List;
import java.time.ZoneId;

@Service
public class MyTimerTaskService {
    @Autowired
    private GeneralService generalService;
    @Autowired
    private JavaMailSender sender;

    @Scheduled(cron = "0 */5 * * * *") //every 5 min
    public void sendMailToUsers() {
        List<Task> tasks = generalService.findAll();
        for(Task task: tasks){
            LocalDate date = task.getDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate now = LocalDate.now();
            if(now.compareTo(date)==0) {
                sendEmail(task);
            }
        }
    }

    public void sendEmail(Task task) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo(task.getAccount().getEmail());
            helper.setText(task.getText());
            helper.setSubject("Reminder");

            sender.send(message);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
