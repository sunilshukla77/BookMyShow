package com.book.my.show.service;

import com.book.my.show.type.SeatStatus;

import java.time.LocalDateTime;

public interface ICronService {
    void unblockSeatsIfThresholdExceed(SeatStatus seatStatus, long maxThresholdDuration);
}
