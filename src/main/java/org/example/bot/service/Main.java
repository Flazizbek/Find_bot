package org.example.bot.service;

import org.example.bot.model.FindBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws TelegramApiException, IOException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        FindBot bot = new FindBot();
        api.registerBot(bot);
    }

}
