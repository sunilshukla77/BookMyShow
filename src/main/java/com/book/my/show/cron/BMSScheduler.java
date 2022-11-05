package com.book.my.show.cron;

import com.book.my.show.service.ICronService;
import com.book.my.show.type.SeatStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BMSScheduler {
    @Value("${max.seat.unblock.threshold.delay.min}")
    private Long maxThresholdDuration;

    private final ICronService cronService;

    public BMSScheduler(ICronService cronService) {
        this.cronService = cronService;
    }

    @Scheduled(initialDelayString = "${bms.scheduler.initial.delay.ms}", fixedDelayString = "${bms.scheduler.fixed.delay.ms}")
    public void makeBlockedSeatBackToOpenState() {
        cronService.unblockSeatsIfThresholdExceed(SeatStatus.BLOCKED, maxThresholdDuration);
    }
}
