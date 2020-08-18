package com.commerceiq.bms.dao;

import com.commerceiq.bms.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre,Long> {

    List<Theatre> findAllByLocation(String location);
}
