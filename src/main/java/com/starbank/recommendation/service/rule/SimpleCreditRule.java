package com.starbank.recommendation.service.rule;

import com.starbank.recommendation.dto.RecommendationDto;
import com.starbank.recommendation.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SimpleCreditRule implements RecommendationRuleSet {

    private final TransactionRepository repository;

    private static final UUID PRODUCT_ID = UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");
    private static final String PRODUCT_NAME = "Простой кредит";
    private static final String PRODUCT_TEXT = "Откройте мир выгодных кредитов с нами!\n\nИщете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.\n\nПочему выбирают нас:\n\nБыстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.\n\nУдобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.\n\nШирокий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.\n\nНе упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!";

    public SimpleCreditRule(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<RecommendationDto> check(UUID userId) {
        boolean hasNoCredit = !repository.hasProductType(userId, "CREDIT");

        double debitDeposits = repository.getSumByProductAndTransactionType(userId, "DEBIT", "DEPOSIT");
        double debitWithdrawals = repository.getSumByProductAndTransactionType(userId, "DEBIT", "WITHDRAW");

        boolean isSavingMoreThanSpending = debitDeposits > debitWithdrawals;
        boolean spendMoreThan100k = debitWithdrawals > 100000.0;

        // Полное условие: !hasCredit && (sumDeposit > sumWithdrawal) && (sumWithdrawal > 100000)
        if (hasNoCredit && isSavingMoreThanSpending && spendMoreThan100k) {
            return Optional.of(new RecommendationDto(PRODUCT_ID, PRODUCT_NAME, PRODUCT_TEXT));
        }
        return Optional.empty();
    }
}