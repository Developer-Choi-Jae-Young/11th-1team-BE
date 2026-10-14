package org.example.knockin.meta.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.knockin.meta.service.impl.SearchServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchHistoryEventListener {
    private final SearchServiceImpl searchService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onSearchKeywordSearched(SearchKeywordSearchedEvent event) {
        try {
            searchService.save(event.memberId(), event.keyword());
        } catch (RuntimeException exception) {
            log.warn("Failed to save search history for memberId={}", event.memberId(), exception);
        }
    }
}
