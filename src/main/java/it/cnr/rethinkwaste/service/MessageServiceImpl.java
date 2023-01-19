package it.cnr.rethinkwaste.service;

import it.cnr.rethinkwaste.model.Conversation;
import it.cnr.rethinkwaste.model.Message;
import it.cnr.rethinkwaste.model.MessageDetail;
import it.cnr.rethinkwaste.model.User_;
import it.cnr.rethinkwaste.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageTypeRepository messageTypeRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageDetailRepository messageDetailRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    public List<User_> convertArrayToList(String[] users) {
        List<User_> usersList = new ArrayList<>();
        for(String s : users) {
            Optional<User_> user = userRepository.findById(Long.parseLong(s));
            if(user.isPresent()) {
                usersList.add(user.get());
            }
        }
        return usersList;
    }

    @Override
    public void deleteConversationByParticipantOrByUsers(User_ user) {
        conversationRepository.deleteByUsersContainingOrParticipantsContaining(user, user);
    }

    @Override
    public void sendMessages(User_ sender, String messageBody, String[] usersArray) {
        List<User_> users = this.convertArrayToList(usersArray);
        users.add(sender);
        List<Conversation> conversations = conversationRepository.findAllByUsersContainingOrderByUpdateDateDesc(sender);
        Conversation conversation = null;
        Message message = null;
        for(Conversation c : conversations) {
            if(c.getUsers().containsAll(users) && users.containsAll(c.getUsers())) {
                conversation = c;
            }
        }
        if(conversation != null) {
            message = new Message();
            message.setBody(messageBody);
            message.setCreationDate(new Date());
            message.setRead(true);
            message.setSender(sender);
            message.setType(messageTypeRepository.findByName("SIMPLE"));
            message = messageRepository.save(message);
            conversation.setUpdateDate(new Date());
            conversation.getMessages().add(message);
            conversationRepository.save(conversation);
        }
        else {
            conversation = new Conversation();
            conversation.setMessages(new ArrayList<>());
            conversation.setCreationDate(new Date());
            conversation.setUpdateDate(new Date());
            conversation.setUsers(users);
            conversation.setParticipants(users);
            message = new Message();
            message.setBody(messageBody);
            message.setCreationDate(new Date());
            message.setRead(true);
            message.setSender(sender);
            message.setType(messageTypeRepository.findByName("SIMPLE"));
            message = messageRepository.save(message);
            conversation.getMessages().add(message);
            conversationRepository.save(conversation);
        }
        //users.remove(sender);
        for(User_ u : users) {
            if(!u.equals(sender)) {
                MessageDetail detail = new MessageDetail();
                detail.setConversation(conversation);
                detail.setMessage(message);
                detail.setNotReadingUser(u);
                messageDetailRepository.save(detail);
            }
        }
    }

    @Override
    public List<Conversation> findAllByUsersContaining(User_ user) {
        return conversationRepository.findAllByUsersContainingOrderByUpdateDateDesc(user);
    }

    @Override
    public Optional<Conversation> findById(Long id) {
        return conversationRepository.findById(id);
    }

    @Override
    public Message sendReply(User_ sender, String messageBody, Conversation conversation) {
        Message message = new Message();
        message.setBody(messageBody);
        message.setCreationDate(new Date());
        message.setRead(true);
        message.setSender(sender);
        message.setType(messageTypeRepository.findByName("SIMPLE"));
        messageRepository.save(message);
        conversation.getMessages().add(message);
        conversationRepository.save(conversation);
        List<User_> users = conversation.getUsers();
        for(User_ u : users) {
            if(!u.equals(sender)) {
                MessageDetail detail = new MessageDetail();
                detail.setConversation(conversation);
                detail.setMessage(message);
                detail.setNotReadingUser(u);
                messageDetailRepository.save(detail);
            }
        }
        return message;
    }

    @Override
    public void deleteConversation(Conversation conversation, User_ user) {
        if(conversation.getParticipants().size() > 1) {
            conversation.getParticipants().remove(user);
            conversationRepository.save(conversation);
        }
        else {
            List<Message> messages = conversation.getMessages();
            for(Message m : messages) {
                messageRepository.delete(m);
            }
            conversationRepository.delete(conversation);
        }

    }

    @Override
    public List<MessageDetail> findAllByConversationAndNotReadingUser(Conversation conversation, User_ notReadingUser) {
        return messageDetailRepository.findAllByConversationAndNotReadingUser(conversation, notReadingUser);
    }

    @Override
    @Transactional
    public void deleteAllByConversationAndNotReadingUser(Conversation conversation, User_ notReadingUser) {
        messageDetailRepository.deleteAllByConversationAndNotReadingUser(conversation, notReadingUser);
    }

    @Override
    public List<MessageDetail> findAllByNotReadingUser(User_ notReadingUser) {
        return messageDetailRepository.findAllByNotReadingUser(notReadingUser);
    }

    @Override
    public Long countByConversationAndNotReadingUser(Conversation conversation, User_ notReadingUser) {
        return messageDetailRepository.countByConversationAndNotReadingUser(conversation, notReadingUser);
    }

}
