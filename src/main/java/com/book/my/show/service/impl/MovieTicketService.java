package com.book.my.show.service.impl;

import com.book.my.show.entity.BookShowSeat;
import com.book.my.show.entity.Ticket;
import com.book.my.show.entity.User;
import com.book.my.show.exception.BookingNotPossibleException;
import com.book.my.show.exception.ErrorMapping;
import com.book.my.show.factory.PaymentFactory;
import com.book.my.show.repository.BookShowSeatRepository;
import com.book.my.show.repository.TicketRepository;
import com.book.my.show.repository.UserRepository;
import com.book.my.show.repository.specification.BookShowSeatSpecification;
import com.book.my.show.request.MovieTicket;
import com.book.my.show.request.MovieTicketRequest;
import com.book.my.show.request.TheatreInfo;
import com.book.my.show.request.UserInfo;
import com.book.my.show.response.MovieTicketResponse;
import com.book.my.show.service.IMovieTicketService;
import com.book.my.show.type.BookStatus;
import com.book.my.show.type.SeatStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class MovieTicketService implements IMovieTicketService {
    private final BookShowSeatRepository bookShowSeatRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public MovieTicketService(BookShowSeatRepository bookShowSeatRepository,
                              UserRepository userRepository, TicketRepository ticketRepository) {
        this.bookShowSeatRepository = bookShowSeatRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    @Override
    public MovieTicketResponse bookMovieTicket(MovieTicketRequest movieTicketRequest) throws Exception {
        MovieTicket movieTicket = movieTicketRequest.getMovieTicket();
        TheatreInfo theatreInfo = movieTicket.getTheatreInfo();
        UserInfo userInfo = movieTicket.getUserInfo();

        List<BookShowSeat> bookShowSeats =
                bookShowSeatRepository.findAll(createBookShowSeatSpecification(theatreInfo));

        if(!CollectionUtils.isEmpty(bookShowSeats)) {
            if(isUserRequestedSeatsAvailable(theatreInfo, bookShowSeats)) {
                blockMovieSeats(bookShowSeats);

                final double totalPayableAmount = calculateTotalPayableAmount(bookShowSeats);

                if(processPaymentViaUserProvidedPaymentOption(movieTicket, totalPayableAmount)) {
                    User user = createUserRepresentation(userInfo);
                    Ticket ticket = createTicket(user, bookShowSeats, totalPayableAmount);
                    linkBookShowSeatToTicket(bookShowSeats, ticket);
                    saveuser(user, ticket);
                    return new MovieTicketResponse()
                            .setBookStatus(BookStatus.BOOKED)
                            .setTicketId(ticket.getTicketId());
                }
                log.info("Failed to process the payment due to some issues at payment service side/ due to insufficient balance for theatre : {}", theatreInfo.getTheatreName());
                throw new BookingNotPossibleException(HttpStatus.OK, ErrorMapping.BMS016);
            }
            log.info("Already filled some of the seats that user has requested for theatre : {}", theatreInfo.getTheatreName());
            throw new BookingNotPossibleException(HttpStatus.OK, ErrorMapping.BMS017);
        }
        log.info("No seat left as per the user's seat combination request for theatre : {}", theatreInfo.getTheatreName());
        throw new BookingNotPossibleException(HttpStatus.OK, ErrorMapping.BMS018);
    }

    private BookShowSeatSpecification createBookShowSeatSpecification(TheatreInfo theatreInfo) {
        return BookShowSeatSpecification.builder()
                .showDay(theatreInfo.getShowDay())
                .showTime(theatreInfo.getShowTime())
                .cityName(theatreInfo.getCityName())
                .movieName(theatreInfo.getMovieName())
                .theatreName(theatreInfo.getTheatreName())
                .seatInfoList(theatreInfo.getRequestedSeatList())
                .build();
    }

    private boolean isUserRequestedSeatsAvailable(TheatreInfo theatreInfo, List<BookShowSeat> bookShowSeats) {
        int openStatusSeatCount = bookShowSeats.stream()
                .filter((bookShowSeat) -> bookShowSeat.getStatus().equals(SeatStatus.OPEN))
                .mapToInt((bookShowSeat) -> 1)
                .sum();
        return openStatusSeatCount == theatreInfo.getRequestedSeatList().size();
    }

    private void blockMovieSeats(List<BookShowSeat> bookShowSeats) {
        bookShowSeats.forEach((bookShowSeat) -> {
            bookShowSeat.setStatus(SeatStatus.BLOCKED);
        });
        bookShowSeatRepository.saveAll(bookShowSeats);
    }

    private double calculateTotalPayableAmount(List<BookShowSeat> bookShowSeats) {
        return bookShowSeats.stream()
                .mapToDouble((bookShowSeat) -> bookShowSeat.getSeat().getSeatType().getPrice()).sum();
    }

    private boolean processPaymentViaUserProvidedPaymentOption(MovieTicket movieTicket, double totalPayableAmount) throws Exception {
        return PaymentFactory.PAYMENT_TYPE_CLASS_MAP.get(movieTicket.getPaymentType())
                .newInstance().processPayment(totalPayableAmount);
    }

    private Ticket createTicket(User user, List<BookShowSeat> bookShowSeats, double totalPayableAmount) {
        Ticket ticket = new Ticket()
                .setUser(user)
                .setBookShowSeats(changeSeatStatusFromBlockToBook(bookShowSeats))
                .setTicketAmount(totalPayableAmount)
                .setStatus(BookStatus.BOOKED)
                .setTicketId(UUID.randomUUID().toString().substring(0, 18));
        ticket.setCreatedBy(user.getName());
        ticket.setUpdatedBy(user.getName());
        return ticketRepository.save(ticket);
    }

    private User createUserRepresentation(UserInfo userInfo) {
        return new User()
                .setName(userInfo.getName())
                .setEmail(userInfo.getEmail())
                .setMobileNumber(userInfo.getMobileNumber())
                .setUserId(UUID.randomUUID().toString().substring(0, 6));
    }

    private List<BookShowSeat> changeSeatStatusFromBlockToBook(List<BookShowSeat> bookShowSeats) {
        bookShowSeats.forEach((bookShowSeat) -> {
            bookShowSeat.setStatus(SeatStatus.BOOKED);
            bookShowSeat.setBookTime(LocalDateTime.now());
        });
        return bookShowSeats;
    }

    private void linkBookShowSeatToTicket(List<BookShowSeat> bookShowSeats, Ticket ticket) {
        bookShowSeats.forEach((bookShowSeat) -> {
            bookShowSeat.setTicket(ticket);
        });
        bookShowSeatRepository.saveAll(bookShowSeats);
    }

    private void saveuser(User user, Ticket ticket) {
        user.setCreatedBy(user.getName());
        user.setUpdatedBy(user.getName());
        user.getTickets().add(ticket);
        userRepository.save(user);
    }
}
