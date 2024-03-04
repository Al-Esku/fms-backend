package com.fencing.midsouth.fmswebsite.repository;

import com.fencing.midsouth.fmswebsite.model.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.endDate < CURRENT_DATE AND LOWER(e.name) LIKE %:name%")
    Page<Event> findPastEvents(String name, PageRequest pageRequest);

    @Query("SELECT e FROM Event e WHERE ((e.startDate >= :firstDate AND e.startDate < :lastDate) OR (e.endDate >= :firstDate AND e.endDate < :lastDate))")
    List<Event> findEventsBetweenDates(@Param("firstDate") ZonedDateTime firstDate,
                                       @Param("lastDate") ZonedDateTime lastDate);

    @Query("SELECT e FROM Event e WHERE e.endDate >= CURRENT_DATE AND LOWER(e.name) LIKE %:name%")
    Page<Event> findUpcomingEvents(@Param("name") String name, PageRequest pageRequest);
}
