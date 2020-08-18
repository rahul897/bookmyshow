package com.commerceiq.bms.bo;

import com.commerceiq.bms.model.Movie;
import com.commerceiq.bms.model.Theatre;
import com.commerceiq.bms.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TheatreBO {

    @Autowired
    private TheatreService theatreService;

    public Theatre add(Theatre theatre) {
        return this.theatreService.add(theatre);
    }

    public Theatre findById(Long theatreId) {
        return this.theatreService.findById(theatreId);
    }

    public List<String> findLocations() {
        return this.theatreService.findLocations();
    }

    public List<Theatre> getTheatresOnLocation(String location) {
        return this.theatreService.getTheatresOnLocation(location);
    }


}
