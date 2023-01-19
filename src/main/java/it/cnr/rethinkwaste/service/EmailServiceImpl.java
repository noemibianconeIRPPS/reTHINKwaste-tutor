package it.cnr.rethinkwaste.service;

import it.cnr.rethinkwaste.model.Email;
import it.cnr.rethinkwaste.model.Role;
import it.cnr.rethinkwaste.model.User_;
import it.cnr.rethinkwaste.repository.EmailRepository;
import it.cnr.rethinkwaste.repository.RoleRepository;
import it.cnr.rethinkwaste.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;


@Service
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;


    @Autowired
    public EmailServiceImpl(EmailRepository emailRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.emailRepository = emailRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void recoverPasswordEmail(String destination, String token) throws MessagingException {

        final String username = "irpps_rethinkwaste_tutor";

        final String password = "rethinkwaste"; // "project"
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", "smtp.amministrazione.cnr.it"); // amministrazione.cnr.it
        props.put("mail.smtp.port", "465"); //465
        props.put("mail.smtp.connectiontimeout", "t1");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.timeout", "t2");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        String dest[] = destination.split(";");

        for (String emailAddress:dest) {
            try {
                MimeMessage message = new MimeMessage(session);

                /* SECONDO PARAMETRO = ALIAS - L'EMAIL ARRIVA A NOME reTHINKwaste tutor */
                message.setFrom(new InternetAddress("tutor.rethinkwaste@irpps.cnr.it", "reTHINKwaste tutor")); // biovoices_platform@cnr.irpps.it
                message.setRecipients(MimeMessage.RecipientType.TO,
                        InternetAddress.parse(emailAddress));
                message.setSubject("Password reset request via reTHINKwaste tutor");
                message.setContent("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                                "    <head>\n" +
                                "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                                "        <title>A Simple Responsive HTML Email</title>\n" +
                                "        <style type=\"text/css\">\n" +
                                "        body {margin: 0; padding: 0; min-width: 100%!important;}\n" +
                                "        .content {width: 100%; max-width: 600px;}  " +
                                "       @media only screen and (min-device-width: 601px) {\n" +
                                "       .content {width: 600px !important;}\n" +
                                "       .header {padding: 40px 30px 20px 30px;}"+
                                "       }\n" +
                                "        </style>\n" +
                                "    </head>\n" +
                                "    <body yahoo style=\"background: url(https://www.tutor-rethinkwaste.eu/img/background.png) #FFFFFF; background-size:cover; background-position:center;\">\n" +
                                "        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                                "            <tr>\n" +
                                "                <td>\n" +
                                "                    <table class=\"content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                                "                        <tr style=\"text-align: center;\">\n" +
                                "                            <td class=\"header\" align=\"center\">\n" +
                                "                               <img src=\"https://www.tutor-rethinkwaste.eu/img/logo_colors_500.png\""+
                                "                                \n" +
                                "                            </td>\n" +
                                "                        </tr>\n" +
                                "                       <tr>" +
                                "                           <td align=\"center\">" +
                                "                               <div style=\" background-color: #FFF; border: 1px solid #C7D114; border-radius: 35px; padding: 15%; margin-top: 5%;\">" +
                                "                                   <h2 style=\"color: #C7D114;\">To reset your password, please click " +
                                "                                   <a style=\"color: #C7D114;\" href='https://www.tutor-rethinkwaste.eu/forgotPassword/resetPassword?token="+ token +"'>here</a></h2>" +
                                "                               <div>" +
                                "                           </td>"+
                                "                       </tr>" +
                                "                       <tr>" +
                                "                           <td align=\"center\">" +
                                "                               <img style=\"max-height: 60px; margin-top: 5%;\" src=\"https://www.tutor-rethinkwaste.eu/img/logo-footer.png\">"+
                                "                           </td>"+
                                "                       </tr>" +
                                "                    </table>\n" +
                                "                </td>\n" +
                                "            </tr>\n" +
                                "        </table>\n" +
                                "    </body>\n" +
                                "</html>",
                        "text/html; charset=utf-8");


                Transport.send(message);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendEmail(User_ sender, String destination, String messageObject, String messageBody) throws NoSuchProviderException, IOException {

        String dest[] = destination.split(";");

        for (String emailAddress:dest) {
            String messageSubject = "New message from " + sender.getEmail() + " via reTHINKwaste tutor. Subject: " + messageObject;
            String webMailTitle = "New message via reTHINKwaste tutor";
            this.sendAndSaveMessage(sender, webMailTitle, emailAddress, messageBody, messageSubject, messageObject);
            }
    }

    @Override
    public void sendEmailAdministration(String destination, String messageObject, String messageBody) throws NoSuchProviderException, IOException {
        String webMailTitle = "Registration success!";
        String messageSubject = "Registration success!";

        String dest[] = destination.split(";");

        for (String emailAddress : dest) {
            try {
                this.sendMessage(webMailTitle, emailAddress, messageBody, messageSubject);
            } catch (javax.mail.NoSuchProviderException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendEmailToAdmins(User_ registeredUser) throws NoSuchProviderException, IOException {
        String webMailTitle = "New user registration to reTHINKwaste tutor";
        String messageSubject = "New user registration to reTHINKwaste tutor";

        Optional<Role> adminRole = roleRepository.findById(1L);
        if(adminRole.isPresent()) {
            List<User_> admins = userRepository.findAllByRolesContaining(adminRole.get());

            for (User_ admin : admins) {
                String messageBody = "A new account (" + registeredUser.getEmail() + ") has been created in reTHINKwaste tutor." +
                        "Until you activate the account, he will not be able to use it. " +
                        "Please login to the platform and choose whether to activate user's account. " +
                        registeredUser.getEmail();
                try {
                    this.sendMessage(webMailTitle, admin.getEmail(), messageBody, messageSubject);
                } catch (javax.mail.NoSuchProviderException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void sendMessage(String webMailTitle, String receiverEmail, String messageBody, String messageSubject) throws NoSuchProviderException, IOException, javax.mail.NoSuchProviderException {

        final String username = "irpps_rethinkwaste_tutor";

        final String password = "rethinkwaste"; // "project"
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", "smtp.amministrazione.cnr.it"); // amministrazione.cnr.it
        props.put("mail.smtp.port", "465"); //465
        props.put("mail.smtp.connectiontimeout", "t1");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.timeout", "t2");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        boolean isConnected = session.getTransport("smtp").isConnected();
        try {
            MimeMessage message = new MimeMessage(session);

            /* SECONDO PARAMETRO = ALIAS - L'EMAIL ARRIVA A NOME reTHINKwaste tutor */
            message.setFrom(new InternetAddress("tutor.rethinkwaste@irpps.cnr.it", "reTHINKwaste tutor")); // biovoices_platform@cnr.irpps.it
            message.setRecipients(MimeMessage.RecipientType.TO,
                    InternetAddress.parse(receiverEmail));
            message.setSubject(messageSubject);

            message.setContent("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                            "    <head>\n" +
                            "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                            "        <title>" + webMailTitle + "</title>\n" +
                            "        <style type=\"text/css\">\n" +
                            "        body {margin: 0; padding: 0; min-width: 100%!important;}\n" +
                            "        .content {width: 100%; max-width: 600px;}  " +
                            "       @media only screen and (min-device-width: 601px) {\n" +
                            "       .content {width: 600px !important;}\n" +
                            "       .header {padding: 40px 30px 20px 30px;}"+
                            "       }\n" +
                            "        </style>\n" +
                            "    </head>\n" +
                            "    <body yahoo bgcolor=\"#ffffff\">\n" +
                            "        <table width=\"100%\" bgcolor=\"#ffffff\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                            "            <tr>\n" +
                            "                <td>\n" +
                            "                    <table class=\"content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                            "                        <tr style=\"text-align: center;\">\n" +
                            "                            <td class=\"header\">\n" +
                            "                               <img src=\"https://www.tutor-rethinkwaste.eu/img/logo_for_emails.jpg\""+
                            "                                Hello!\n" +
                            "                            </td>\n" +
                            "                        </tr>\n" +
                            "                       <tr style=\"background-color: #FFFFFF;\">" +
                            "                           <td style=\"padding: 5%;\">" +
                            "                           <h4 style=\"font-weight: normal;\">"+messageBody+"</h4></td>"+
                            "                       </tr>" +
                            "                       <tr style=\"background-color: #FFFFFF;\">" +
                            "                           <td style=\"padding: 5%;\">" +
                            "                           <h4>Sent from reTHINKwaste tutor https://www.tutor-rethinkwaste.eu</h4></td>"+
                            "                       </tr>" +
                            "                       <tr style=\"text-align: center;\">" +
                            "                           <td>" +
                            "                           <img style=\"max-height: 60px;\" src=\"https://www.tutor-rethinkwaste.eu/img/logo-footer.png\"></td>"+
                            "                       </tr>" +
                            "                    </table>\n" +
                            "                </td>\n" +
                            "            </tr>\n" +
                            "        </table>\n" +
                            "    </body>\n" +
                            "</html>",
                    "text/html; charset=utf-8");


            Transport.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void sendAndSaveMessage(User_ sender, String webMailTitle, String receiverEmail, String messageBody, String messageSubject, String messageObject) throws NoSuchProviderException, IOException {
        final String username = "irpps_rethinkwaste_tutor";

        final String password = "rethinkwaste"; // "project"
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", "smtp.amministrazione.cnr.it"); // amministrazione.cnr.it
        props.put("mail.smtp.port", "465"); //465
        props.put("mail.smtp.connectiontimeout", "t1");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.timeout", "t2");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        boolean done = false;
        try {
            boolean isConnected = session.getTransport("smtp").isConnected();
        } catch (javax.mail.NoSuchProviderException e) {
            e.printStackTrace();
        }
        try {
            MimeMessage message = new MimeMessage(session);

            /* SECONDO PARAMETRO = ALIAS - L'EMAIL ARRIVA A NOME reTHINKwaste tutor */
            message.setFrom(new InternetAddress("tutor.rethinkwaste@irpps.cnr.it", "reTHINKwaste tutor")); // biovoices_platform@cnr.irpps.it
            message.setRecipients(MimeMessage.RecipientType.TO,
                    InternetAddress.parse(receiverEmail));
            message.setSubject(messageSubject);

            message.setContent("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                            "    <head>\n" +
                            "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                            "        <title>" + webMailTitle + "</title>\n" +
                            "        <style type=\"text/css\">\n" +
                            "        body {margin: 0; padding: 0; min-width: 100%!important;}\n" +
                            "        .content {width: 100%; max-width: 600px;}  " +
                            "       @media only screen and (min-device-width: 601px) {\n" +
                            "       .content {width: 600px !important;}\n" +
                            "       .header {padding: 40px 30px 20px 30px;}"+
                            "       }\n" +
                            "        </style>\n" +
                            "    </head>\n" +
                            "    <body yahoo bgcolor=\"#ffffff\">\n" +
                            "        <table width=\"100%\" bgcolor=\"#ffffff\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                            "            <tr>\n" +
                            "                <td>\n" +
                            "                    <table class=\"content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                            "                        <tr style=\"text-align: center;\">\n" +
                            "                            <td class=\"header\">\n" +
                            "                               <img src=\"https://www.tutor-rethinkwaste.eu/img/logo_for_emails.jpg\""+
                            "                                Hello!\n" +
                            "                            </td>\n" +
                            "                        </tr>\n" +
                            "                       <tr style=\"background-color: #FFFFFF;\">" +
                            "                           <td style=\"padding: 5%;\">" +
                            "                           <h4 style=\"font-weight: normal;\">"+messageBody+"</h4></td>"+
                            "                       </tr>" +
                            "                       <tr style=\"background-color: #FFFFFF;\">" +
                            "                           <td style=\"padding: 5%;\">" +
                            "                           <h4>Sent from reTHINKwaste tutor https://www.tutor-rethinkwaste.eu</h4></td>"+
                            "                       </tr>" +
                            "                       <tr style=\"text-align: center;\">" +
                            "                           <td>" +
                            "                           <img style=\"max-height: 60px;\" src=\"https://www.tutor-rethinkwaste.eu/img/logo-footer.png\"></td>"+
                            "                       </tr>" +
                            "                    </table>\n" +
                            "                </td>\n" +
                            "            </tr>\n" +
                            "        </table>\n" +
                            "    </body>\n" +
                            "</html>",
                    "text/html; charset=utf-8");


            Transport.send(message);
            done = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(done){
            Email emailDB = new Email();
            emailDB.setSender(sender);
            emailDB.setDestination(receiverEmail);
            emailDB.setObject(messageObject);
            emailDB.setBody(messageBody);
            emailDB.setCreationDate(new Date());
            emailRepository.save(emailDB);
        }

    }

    @Override
    public List<Email> findAllByUsersContaining(User_ user) {

        return emailRepository.findBySenderOrderByCreationDateDesc(user);
    }

    @Override
    public void deleteEmail(Long id) {
        Optional<Email> e= emailRepository.findById(id);
        if(e.isPresent()) {
            emailRepository.delete(e.get());
        }
    }
}
