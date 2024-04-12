package com.fencing.midsouth.fmswebsite.repository;

import com.fencing.midsouth.fmswebsite.model.entity.Event;
import com.fencing.midsouth.fmswebsite.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findTop2ByStartDateAfterAndUser(ZonedDateTime startDate, User user, Sort sort);

    @Query("SELECT e FROM Event e WHERE e.startDate < CURRENT_DATE AND LOWER(e.name) LIKE %:name% AND LOWER(e.type) IN :types AND LOWER(e.user.username) IN :creators")
    Page<Event> findPastEvents(String name, PageRequest pageRequest, String[] types, String[] creators);

    @Query("SELECT e FROM Event e WHERE ((e.startDate >= :firstDate AND e.startDate < :lastDate) OR (e.endDate >= :firstDate AND e.endDate < :lastDate))")
    List<Event> findEventsBetweenDates(@Param("firstDate") ZonedDateTime firstDate,
                                       @Param("lastDate") ZonedDateTime lastDate);

    @Query("SELECT e FROM Event e WHERE e.endDate >= :after AND e.startDate < :before AND LOWER(e.name) LIKE %:name% AND LOWER(e.type) IN (:types) AND LOWER(e.user.username) IN :creators")
    Page<Event> findEventsBetweenDates(@Param("name") String name, PageRequest pageRequest, @Param("types") String[] types,
                                       @Param("creators") String[] creators, @Param("before") ZonedDateTime before,
                                       @Param("after") ZonedDateTime after);

    @Query("SELECT e FROM Event e WHERE e.endDate >= CURRENT_DATE AND LOWER(e.name) LIKE %:name% AND LOWER(e.type) IN (:types) AND LOWER(e.user.username) IN :creators")
    Page<Event> findUpcomingEvents(@Param("name") String name, PageRequest pageRequest, @Param("types") String[] types, @Param("creators") String[] creators);

    Event findEventByUuid(String uuid);

    void deleteEventByUuid(String uuid);

    boolean existsByUuid(String uuid);

    @Query("SELECT e FROM Event e WHERE e.startDate < :date AND LOWER(e.name) LIKE %:name% AND LOWER(e.type) IN (:types) AND LOWER(e.user.username) IN :creators")
    Page<Event> findEventsBeforeDate(@Param("name") String name, PageRequest pageRequest, @Param("types") String[] types, @Param("creators") String[] creators, @Param("date") ZonedDateTime date);

    @Query("SELECT e FROM Event e WHERE e.endDate >= :date AND LOWER(e.name) LIKE %:name% AND LOWER(e.type) IN (:types) AND LOWER(e.user.username) IN :creators")
    Page<Event> findEventsAfterDate(@Param("name") String name, PageRequest pageRequest, @Param("types") String[] types, @Param("creators") String[] creators, @Param("date") ZonedDateTime date);
}
