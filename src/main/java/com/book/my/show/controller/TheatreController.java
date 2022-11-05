package com.book.my.show.controller;

import com.book.my.show.response.AvailableSeatResponse;
import com.book.my.show.response.RunningShowResponse;
import com.book.my.show.response.TheatreResponse;
import com.book.my.show.service.impl.TheatreService;
import com.book.my.show.validator.ValidationUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/bookmyshow")
public class TheatreController {
    private final TheatreService theatreService;
    private final HttpServletRequest servletRequest;

    public TheatreController(TheatreService theatreService, HttpServletRequest servletRequest) {
        this.theatreService = theatreService;
        this.servletRequest = servletRequest;
    }

    @GetMapping("/ext/v1/cities/{cityName}/theatres")
    public ResponseEntity<TheatreResponse> getTheatresByCity(@PathVariable("cityName") String cityName) throws InstantiationException, IllegalAccessException {
        //Validation Layer
        ValidationUtility.validate(servletRequest);

        return ResponseEntity
                .ok().body(theatreService.getTheatresByCity(cityName.toUpperCase()));
    }

    @GetMapping("/ext/v1/cities/{cityName}/movies/{movieName}/theatres")
    public ResponseEntity<TheatreResponse> getTheatresByCityAndMovie(@PathVariable("cityName") String cityName,
                                                                     @PathVariable("movieName") String movieName) throws InstantiationException, IllegalAccessException {
        //Validation Layer
        ValidationUtility.validate(servletRequest);

        return ResponseEntity
                .ok().body(theatreService.getTheatresByCityAndMovie(cityName.toUpperCase(), movieName.toUpperCase()));
    }

    @GetMapping("/ext/v1/cities/{cityName}/theatres/{theatreName}/auditoriums/shows")
    public ResponseEntity<RunningShowResponse> getAuditoriumsAndShowsByTheatreAndCity(@PathVariable("cityName") String cityName,
                                                                      @PathVariable("theatreName") String theatreName) throws InstantiationException, IllegalAccessException {
        //Validation Layer
        ValidationUtility.validate(servletRequest);

        return ResponseEntity
                .ok().body(theatreService.getAuditoriumsAndShowsByTheatreAndCity(theatreName.toUpperCase(), cityName.toUpperCase()));
    }

    @GetMapping("/ext/v1/cities/{cityName}/theatres/{theatreName}/movies/{movieName}/shows/{showTime}/showDays/{showDay}/seats")
    public ResponseEntity<AvailableSeatResponse> getAvailableSeats(@PathVariable("cityName") String cityName,
                                                                   @PathVariable("theatreName") String theatreName, @PathVariable("movieName") String movieName,
                                                                   @PathVariable("showTime") String showTime, @PathVariable("showDay") String showDay) throws InstantiationException, IllegalAccessException {

    //Validation Layer
    ValidationUtility.validate(servletRequest);

    return ResponseEntity
            .ok().body(theatreService.getAvailableSeats(cityName.toUpperCase(), theatreName.toUpperCase(),
                    movieName.toUpperCase(), showTime.toUpperCase(), showDay));
    }
}
