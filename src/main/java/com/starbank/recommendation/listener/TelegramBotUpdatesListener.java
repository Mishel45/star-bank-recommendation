package com.starbank.recommendation.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.starbank.recommendation.dto.RecommendationDto;
import com.starbank.recommendation.repository.UserRepository;
import com.starbank.recommendation.service.RecommendationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot telegramBot;
    private final UserRepository userRepository;
    private final RecommendationService recommendationService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot,
                                      UserRepository userRepository,
                                      RecommendationService recommendationService) {
        this.telegramBot = telegramBot;
        this.userRepository = userRepository;
        this.recommendationService = recommendationService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            if (update.message() == null) return;
            String text = update.message().text();
            Long chatId = update.message().chat().id();

            if (text == null) return;

            if (text.startsWith("/recommend ")) {
                String input = text.substring("/recommend ".length()).trim();
                List<String> fullNames;
                UUID userId = null;

                // Пытаемся понять: это UUID или username?
                try {
                    UUID id = UUID.fromString(input);
                    fullNames = userRepository.findFullNameById(id);
                    userId = id;
                } catch (IllegalArgumentException e) {
                    // Это не UUID, ищем по username
                    fullNames = userRepository.findFullNameByUsername(input);
                    if (fullNames.size() == 1) {
                        userId = userRepository.findIdByUsername(input);
                    }
                }

                // Если найдено 0 или >1 пользователей — "Пользователь не найден"
                if (fullNames.size() != 1 || userId == null) {
                    telegramBot.execute(new SendMessage(chatId, "Пользователь не найден"));
                    return;
                }

                String fullName = fullNames.get(0);

                // Получаем рекомендации
                List<RecommendationDto> recs = recommendationService.getAllRecommendationsForUser(userId);

                // Формируем ответ
                StringBuilder response = new StringBuilder();
                response.append("Здравствуйте ").append(fullName).append("\n\n");

                if (recs.isEmpty()) {
                    response.append("Пока новых продуктов для вас нет.");
                } else {
                    response.append("Новые продукты для вас:\n");
                    for (RecommendationDto rec : recs) {
                        response.append("• ").append(rec.getName()).append("\n");
                    }
                }

                telegramBot.execute(new SendMessage(chatId, response.toString()));

            } else if (text.equals("/start") || text.equals("/help")) {
                telegramBot.execute(new SendMessage(chatId,
                        "Привет! Я бот рекомендаций.\n" +
                                "Отправь команду: /recommend username\n" +
                                "Например: /recommend sheron.berge"));
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}