package com.bms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Booking {
    @Id
    @GeneratedValue
    Long id;
    @Column(name = "user_id")
    Long userId;
    @Column(name = "movie_id")
    Long movieId;
    @Transient
    Movie movie;
    @Column(columnDefinition = "text")
    String selectedSeatsJson;
    @Transient
    List<String> selectedSeats;
    @Transient
    String message;
    @Transient
    String status="FAILED";//FAILED or DONE

    public void preSave() {
        try {
            status = "DONE";
            selectedSeatsJson = new ObjectMapper().writeValueAsString(selectedSeats);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @PostPersist
    @PostLoad
    public void onLoad() {
        try {
            selectedSeats = new ObjectMapper().readValue(selectedSeatsJson, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
