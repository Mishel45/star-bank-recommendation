package com.starbank.recommendation.service.rule;

import com.starbank.recommendation.dto.RecommendationDto;
import com.starbank.recommendation.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TopSavingRule implements RecommendationRuleSet {

    private final TransactionRepository repository;

    private static final UUID PRODUCT_ID = UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");
    private static final String PRODUCT_NAME = "Top Saving";
    private static final String PRODUCT_TEXT = "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!\n\nПреимущества «Копилки»:\n\nНакопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.\n\nПрозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости.\n\nБезопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.\n\nНачните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!";

    public TopSavingRule(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<RecommendationDto> check(UUID userId) {
        boolean hasDebit = repository.hasProductType(userId, "DEBIT");

        double debitDeposits = repository.getSumByProductAndTransactionType(userId, "DEBIT", "DEPOSIT");
        double savingDeposits = repository.getSumByProductAndTransactionType(userId, "SAVING", "DEPOSIT");
        // Логическое ИЛИ между пополнениями
        boolean passDepositCondition = (debitDeposits >= 50000.0) || (savingDeposits >= 50000.0);

        double debitWithdrawals = repository.getSumByProductAndTransactionType(userId, "DEBIT", "WITHDRAW");
        boolean isSavingMoreThanSpending = debitDeposits > debitWithdrawals;

        // Полное условие
        if (hasDebit && passDepositCondition && isSavingMoreThanSpending) {
            return Optional.of(new RecommendationDto(PRODUCT_ID, PRODUCT_NAME, PRODUCT_TEXT));
        }
        return Optional.empty();
    }
}
