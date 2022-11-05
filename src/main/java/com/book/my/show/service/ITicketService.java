package com.book.my.show.service;

import com.book.my.show.response.TicketInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITicketService {
    List<TicketInfo> getTicketsByUserId(String userId, Pageable pageable);
    TicketInfo getTicketByUserIdAndTicketId(String userId, String ticketId);
}
