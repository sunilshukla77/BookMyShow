package com.book.my.show.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@Setter
@Getter
@NoArgsConstructor
public class RunningShowResponse implements Serializable {
    private static final long serialVersionUID = 7814246106956720948L;

    private List<RunningShow> runningShows;
}
