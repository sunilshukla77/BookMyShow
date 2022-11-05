package com.book.my.show.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@Setter
@Getter
@NoArgsConstructor
public class TicketResponse implements Serializable {
    private static final long serialVersionUID = 3441396655395362561L;

    private List<TicketInfo> ticketInfoList;
}
