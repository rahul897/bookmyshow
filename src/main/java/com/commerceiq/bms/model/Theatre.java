package com.commerceiq.bms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "theatre")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Theatre {
    @Id
    @GeneratedValue
    Long id;
    String location;
    String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "theatre_id")
    List<Movie> movies = new ArrayList<>();

    public void add(Movie movie) {
        if(CollectionUtils.isEmpty(movies)){
            movies = new ArrayList<>();
        }
        movies.add(movie);
    }
}
