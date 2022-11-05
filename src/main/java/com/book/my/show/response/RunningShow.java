package com.book.my.show.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@Setter
@Getter
@NoArgsConstructor
public class RunningShow {
    private String auditoriumName;
    private String movieName;
    private List<String> showTimes;
}
