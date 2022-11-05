package com.book.my.show.service;

import com.book.my.show.request.MovieTicketRequest;
import com.book.my.show.response.MovieTicketResponse;

public interface IMovieTicketService {
    MovieTicketResponse bookMovieTicket(MovieTicketRequest movieTicketRequest) throws Exception;
}
