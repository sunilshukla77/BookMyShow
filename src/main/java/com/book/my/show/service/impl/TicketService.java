package com.book.my.show.service.impl;

import com.book.my.show.entity.Auditorium;
import com.book.my.show.entity.BookShowSeat;
import com.book.my.show.entity.Ticket;
import com.book.my.show.exception.ContentNotFoundException;
import com.book.my.show.exception.ErrorMapping;
import com.book.my.show.repository.TicketRepository;
import com.book.my.show.response.SeatInfo;
import com.book.my.show.response.TicketInfo;
import com.book.my.show.service.ITicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TicketService implements ITicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Cacheable("Tickets")
    @Override
    public List<TicketInfo> getTicketsByUserId(String userId, Pageable pageable) {
        Page<Ticket> ticketPages = ticketRepository.findAllByUserId(userId, pageable);

        if(ticketPages.getContent().size() > 0) {
            return ticketPages.get()
                    .map(this::processRawTicketInfo).collect(Collectors.toList());
        }

        log.info("Ticket history not present against userId : {}", userId);
        throw new ContentNotFoundException(HttpStatus.OK, ErrorMapping.BMS007);
    }

    @Cacheable("Ticket")
    @Override
    public TicketInfo getTicketByUserIdAndTicketId(String userId, String ticketId) {
        Optional<Ticket> mayBeTicket = ticketRepository.findByUserIdAndTicketId(userId, ticketId);

        if(mayBeTicket.isPresent()) {
            return processRawTicketInfo(mayBeTicket.get());
        }

        log.info("Ticket not present against userId : {} and ticketId : {}", userId, ticketId);
        throw new ContentNotFoundException(HttpStatus.OK, ErrorMapping.BMS008);
    }

    private TicketInfo processRawTicketInfo(Ticket ticket) {
        List<BookShowSeat> bookShowSeats = ticket.getBookShowSeats();
        String showDay = null, showTime = null, auditoriumName = null, theatreName = null, movieName = null;
        List<SeatInfo> seatInfoList = null;
        if(!CollectionUtils.isEmpty(bookShowSeats)) {
            showDay = bookShowSeats.get(0).getShowDay();
            showTime = bookShowSeats.get(0).getShow().getShowTime();

            Set<Auditorium> auditoriumSet = bookShowSeats.get(0).getShow().getAuditoriums();
            for (Auditorium auditorium : auditoriumSet) {
                auditoriumName = auditorium.getName();
                theatreName = auditorium.getTheatre().getName();
                movieName = auditorium.getMovie().getName();
                break;
            }

            seatInfoList = bookShowSeats.stream()
                    .map((bookShowSeat) -> new SeatInfo().setSeatName(bookShowSeat.getSeat().getName())
                            .setSeatType(bookShowSeat.getSeat().getSeatType())).collect(Collectors.toList());
        }

        return getTicketInfo(ticket, showDay, showTime, auditoriumName, theatreName, movieName, seatInfoList);
    }

    private TicketInfo getTicketInfo(Ticket ticket, String showDay, String showTime, String auditoriumName, String theatreName, String movieName, List<SeatInfo> seatInfoList) {
        return new TicketInfo()
                .setTheatreName(theatreName)
                .setAuditoriumName(auditoriumName)
                .setMovieName(movieName)
                .setTicketId(ticket.getTicketId())
                .setTicketAmount(ticket.getTicketAmount())
                .setShowTime(showTime)
                .setShowDay(showDay).setSeatInfoList(seatInfoList);
    }
}
