package com.timecap.apiService.Service;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.sendgrid.*;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class SendGridService {
    @Value("${sendgrid.api}")
    private String sendgridApi;


    public String delay(String toRep, String contentRep, String pathRep, Date schedule) throws IOException{
        HtmlRender html = new HtmlRender();
        String htmlBody = html.getTemplate(contentRep, pathRep);
        Instant instantNow = Instant.now();
        Instant instantSchedule = schedule.toInstant();
        final long interval = Duration.between(instantSchedule, instantNow).toMillis();
        (new Runnable() {
            public void run() {
                try {
                    Thread.sleep(interval);
                    Email from = new Email("xtjnj95@gmail.com");
                    String subject = "Message from time capsule";
                    Email to = new Email(toRep);
                    Content content = new Content("text/html", htmlBody);
                    Mail mail = new Mail(from, subject, to, content);

                    SendGrid sg = new SendGrid(sendgridApi);
                    Request request = new Request();
                    try {
                        request.setMethod(Method.POST);
                        request.setEndpoint("mail/send");
                        request.setBody(mail.build());
                        Response response = sg.api(request);
                        System.out.println(response.getStatusCode());
                        System.out.println(response.getBody());
                        System.out.println(response.getHeaders());
                    } catch (IOException ex) {
                        throw ex;
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }).run();

        return htmlBody;
    }
}
