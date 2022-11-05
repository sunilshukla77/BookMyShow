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
public class AvailableSeatResponse implements Serializable {
    private static final long serialVersionUID = 2430515445933391010L;

    private List<SeatInfo> seats;
}
