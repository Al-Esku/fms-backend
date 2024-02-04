package com.fencing.midsouth.fmswebsite.repository;

import com.fencing.midsouth.fmswebsite.model.entity.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.endDate < CURRENT_DATE AND LOWER(e.name) LIKE %:name%")
    List<Event> findPastEvents(String name);

    @Query("SELECT e FROM Event e WHERE ((e.startDate >= :firstDate AND e.startDate < :lastDate) OR (e.endDate >= :firstDate AND e.endDate < :lastDate))")
    List<Event> findEventsBetweenDates(@Param("firstDate") ZonedDateTime firstDate,
                                       @Param("lastDate") ZonedDateTime lastDate);

    @Query("SELECT e FROM Event e WHERE e.endDate >= CURRENT_DATE AND LOWER(e.name) LIKE %:name%")
    List<Event> findUpcomingEvents(@Param("name") String name);
}
