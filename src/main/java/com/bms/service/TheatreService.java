package com.bms.service;

import com.bms.dao.Cache;
import com.bms.dao.TheatreRepository;
import com.bms.model.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheatreService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TheatreRepository theatreRepository;

    public Theatre findById(Long tid){
        return this.theatreRepository.findById(tid).orElse(null);
    }

    public Theatre add(Theatre theatre) {
        Cache.locations.add(theatre.getLocation());
        return this.theatreRepository.save(theatre);
    }

    public List<String> findLocations() {
        List<String> locations = null;
        if(Cache.locations.isEmpty()){
            locations = entityManager
                    .createQuery(
                            "select distinct t.location from Theatre t", String.class)
                    .getResultList();
        }
        else{
            locations = Cache.locations.stream().collect(Collectors.toList());
        }
        return locations;
    }

    public List<Theatre> getTheatresOnLocation(String location) {
        return this.theatreRepository.findAllByLocation(location);
    }
}
