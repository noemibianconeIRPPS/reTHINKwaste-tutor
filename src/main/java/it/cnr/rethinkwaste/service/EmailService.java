package it.cnr.rethinkwaste.service;

import it.cnr.rethinkwaste.model.Email;
import it.cnr.rethinkwaste.model.User_;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.List;

@Service
public interface EmailService {

    void sendEmail(User_ sender, String destination, String messageObject, String messageBody) throws NoSuchProviderException, IOException, java.security.NoSuchProviderException, javax.mail.NoSuchProviderException;

    void recoverPasswordEmail(String destination, String token) throws NoSuchProviderException, IOException, MessagingException;

    void sendEmailAdministration(String destination, String messageObject, String messageBody) throws NoSuchProviderException, IOException, java.security.NoSuchProviderException, javax.mail.NoSuchProviderException;

    List<Email> findAllByUsersContaining(User_ user);

    void deleteEmail(Long id);

    void sendEmailToAdmins(User_ registeredUser) throws NoSuchProviderException, IOException, NoSuchProviderException, java.security.NoSuchProviderException, javax.mail.NoSuchProviderException;

}
