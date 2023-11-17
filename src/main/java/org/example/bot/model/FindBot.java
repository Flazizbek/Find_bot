package org.example.bot.model;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

public class FindBot extends TelegramLongPollingBot {

static    String data;

    static {
        try {
            data = Files.readString(Path.of("src/main/resources/MOCK_DATA (4).json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static   Type type = new TypeToken<List<Contact>>() {}.getType();
    static List<Contact> employees = new GsonBuilder().create().fromJson(data,type);


    public FindBot() throws IOException {super("6959068206:AAH8YpANdYLwpm32yU3HBY9si_Zu4hie5Ns");}
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
            if(update.hasMessage()){
               final  Message message = update.getMessage();
                User user = message.getFrom();
                System.out.println(message.getText());
                if(message.hasText()){
                    String text = message.getText();
                    if (text.equals("/start")){
                        this.execute(new SendMessage(message.getChatId().toString(),
                                "Hello welcome.This bot can help you to find contacts."));
                        UserRepo.USER_STEP.put(message.getChatId(),Step.START);
                    } else if (UserRepo.USER_STEP.get(message.getChatId())==Step.START) {
                        this.execute(new SendMessage(message.getChatId().toString(),"Enter /help to get some information. "));
                        UserRepo.USER_STEP.put(message.getChatId(),Step.HELP);
                    } else if (UserRepo.USER_STEP.get(message.getChatId())==Step.HELP) {
                        if(text.equals("/help")){
                            this.execute(new SendMessage(message.getChatId().toString(),"\n" +
                                    "/help\n" +
                                    " - Quick introduction message  about your bot functionality and instructions  how to use it . This message include all commands and their definitions.\n" +
                                    "2 - \n" +
                                    "/start\n" +
                                    " - Starting the bot\n" +
                                    "3 - \n" +
                                    "/contact\n" +
                                    " - To contact with administrator\n" +
                                    "4 - \n" +
                                    "/comment \n" +
                                    "- Leaving comment (If user's message starts with '/comment' (ex. \n" +
                                    "/comment how a nice bot | \uD83C\uDF1F.\n) "+
                                    "-find\n"+
                                    "Find contact with your information "));

                            UserRepo.USER_STEP.put(message.getChatId(),Step.ACTIONS);
                        }
                            UserRepo.USER_STEP.put(message.getChatId(),Step.START);
                    } else if (UserRepo.USER_STEP.get(message.getChatId())==Step.ACTIONS) {
                        if(text.equals("/find")){
                            this.execute(new SendMessage(message.getChatId().toString(),"Enter information (ex: )"));
                            UserRepo.ACTION_STEPS.put(message.getChatId(),ActionSteps.FIND);
                        }
                        else if (text.equals("/contact")){
                            this.execute(new SendMessage(message.getChatId().toString(),
                                    "Email address : lazizbek.fayzullayev.17@gmail.com.\n Telegram user name : t.me/Fayzullayev_Lazizbek"));
                        }
                        else if(Pattern.matches("^(/comment).+",text)){
                            this.execute(new SendMessage(message.getChatId().toString()," Thanks for your comment."));
                            UserRepo.COMMENTS.put("userName"+user.getUserName()+", chat id:"+message.getChatId().toString(),text);
                            System.out.println(UserRepo.COMMENTS);
                        }
                    }
                }
            }
    }

    @Override
    public String getBotUsername() {return "t.me/contact_find_bot";};
}
