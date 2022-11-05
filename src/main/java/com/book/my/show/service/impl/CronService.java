package com.book.my.show.service.impl;

import com.book.my.show.entity.BookShowSeat;
import com.book.my.show.repository.BookShowSeatRepository;
import com.book.my.show.service.ICronService;
import com.book.my.show.type.BookStatus;
import com.book.my.show.type.SeatStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CronService implements ICronService {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final BookShowSeatRepository bookShowSeatRepository;

    public CronService(BookShowSeatRepository bookShowSeatRepository) {
        this.bookShowSeatRepository = bookShowSeatRepository;
    }

    @Transactional
    @Override
    public void unblockSeatsIfThresholdExceed(SeatStatus seatStatus, long maxThresholdDuration) {
        int offset = 0, limit = 20;

        long count = bookShowSeatRepository
                .findCountByStatusAndThresholdAndCreatedDate(seatStatus);

        log.info("Total number of blocked seats that has exceeds its threshold is {}", count);

        Pageable pageable = PageRequest.of(offset, limit);

        while(pageable.getOffset() < count) {
            List<BookShowSeat> bookShowSeats = bookShowSeatRepository
                    .findAllByStatusAndThresholdAndCreatedDate(seatStatus, pageable);

            log.info("Processing batch number : {}", pageable.getPageNumber());

            LocalDateTime currentDateTime = LocalDateTime.now();

            bookShowSeats = bookShowSeats.stream().filter((bookShowSeat) -> {
                LocalDate showDay = LocalDate.parse(bookShowSeat.getShowDay(), formatter);
                if((showDay.isAfter(currentDateTime.toLocalDate()) || showDay.isEqual(currentDateTime.toLocalDate()))
                        && Duration.between(bookShowSeat.getBookTime(), currentDateTime).getSeconds() >= (maxThresholdDuration * 60)) {
                    bookShowSeat.setStatus(SeatStatus.OPEN);
                    bookShowSeat.setTicket(null);
                    bookShowSeat.getTicket().setStatus(BookStatus.SYSTEM_CANCELLED);
                    return true;
                }
                return false;
            }).collect(Collectors.toList());

            bookShowSeatRepository.saveAll(bookShowSeats);
            pageable = pageable.next();
        }
    }
}
