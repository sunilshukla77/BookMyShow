package com.book.my.show.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Getter
@NoArgsConstructor
public class MovieTicketRequest implements Serializable {
    private static final long serialVersionUID = 1524680640404367184L;

    private MovieTicket movieTicket;
}
