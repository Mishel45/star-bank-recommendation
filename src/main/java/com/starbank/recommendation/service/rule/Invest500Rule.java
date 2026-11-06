package com.starbank.recommendation.service.rule;

import com.starbank.recommendation.dto.RecommendationDto;
import com.starbank.recommendation.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class Invest500Rule implements RecommendationRuleSet {

    private final TransactionRepository repository;

    private static final UUID PRODUCT_ID = UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a");
    private static final String PRODUCT_NAME = "Invest 500";
    private static final String PRODUCT_TEXT = "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!";

    public Invest500Rule(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<RecommendationDto> check(UUID userId) {
        boolean hasDebit = repository.hasProductType(userId, "DEBIT");
        boolean hasNoInvest = !repository.hasProductType(userId, "INVEST");
        double savingDeposits = repository.getSumByProductAndTransactionType(userId, "SAVING", "DEPOSIT");

        // Условие: hasDebit && !hasInvest && sumSaving > 1000
        if (hasDebit && hasNoInvest && savingDeposits > 1000.0) {
            return Optional.of(new RecommendationDto(PRODUCT_ID, PRODUCT_NAME, PRODUCT_TEXT));
        }
        return Optional.empty();
    }
}
