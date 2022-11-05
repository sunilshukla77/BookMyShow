package com.book.my.show.controller;

import com.book.my.show.response.TicketResponse;
import com.book.my.show.service.ITicketService;
import com.book.my.show.validator.ValidationUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@RestController
@RequestMapping("/bookmyshow")
@Slf4j
public class TicketController {
    private final ITicketService ticketService;
    private final HttpServletRequest servletRequest;

    public TicketController(ITicketService ticketService, HttpServletRequest servletRequest) {
        this.ticketService = ticketService;
        this.servletRequest = servletRequest;
    }

    @GetMapping("/ext/v1/users/{userId}/tickets")
    public ResponseEntity<TicketResponse> getTicketsByUserId(@PathVariable("userId") String userId,
                                                             @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
                                                             @RequestParam(required = false, name = "limit", defaultValue = "10") int limit,
                                                             @RequestParam(required = false, name = "sortOrder", defaultValue = "ASC") String sortOrder,
                                                             @RequestParam(required = false, name = "sortElement", defaultValue = "updatedOn") String sortElement) throws InstantiationException, IllegalAccessException {
        //Validation Layer
        ValidationUtility.validate(servletRequest);

        return ResponseEntity
                .ok().body(new TicketResponse()
                        .setTicketInfoList(ticketService.getTicketsByUserId(userId,
                                PageRequest.of(offset, limit, Sort.by(Sort.Direction.valueOf(sortOrder), sortElement)))));
    }

    @GetMapping("/ext/v1/users/{userId}/tickets/{ticketId}")
    public ResponseEntity<TicketResponse> getByTicketId(@PathVariable("userId") String userId,
                                        @PathVariable("ticketId") String ticketId) throws InstantiationException, IllegalAccessException {
        //Validation Layer
        ValidationUtility.validate(servletRequest);

        return ResponseEntity
                .ok().body(new TicketResponse()
                        .setTicketInfoList(Collections
                                .singletonList(ticketService.getTicketByUserIdAndTicketId(userId, ticketId))));
    }
}
