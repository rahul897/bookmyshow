package com.commerceiq.bms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    @Id
    @GeneratedValue
    Long id;
    @Column(name = "theatre_id")
    Long theatreId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "movie_id")
    List<Booking> bookings;
    String name;
    //assuming one show not including timings
    @Column(columnDefinition = "text")
    String seatingLayoutJson;
    @Transient
    List<String> seatingLayout = new ArrayList<>();
    @Column(columnDefinition = "text")
    String availableSeatsJson;
    @Transient
    Set<String> availableSeats = new HashSet<>();

    public void preSave() {
        try {
            seatingLayoutJson = new ObjectMapper().writeValueAsString(seatingLayout);
            availableSeatsJson = new ObjectMapper().writeValueAsString(availableSeats);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @PostPersist
    @PostLoad
    public void onLoad() {
        try {
            seatingLayout = new ObjectMapper().readValue(seatingLayoutJson, new TypeReference<List<String>>() {});
            availableSeats = new ObjectMapper().readValue(availableSeatsJson, new TypeReference<Set<String>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void fillSeats(List<String> selectedSeats) {
        availableSeats.removeAll(selectedSeats);
    }

    public boolean findOverlap(List<String> selectedSeats) {
        return selectedSeats.stream().anyMatch(s->!availableSeats.contains(s));
    }
}
