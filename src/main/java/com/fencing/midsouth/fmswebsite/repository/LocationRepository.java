package com.fencing.midsouth.fmswebsite.repository;

import com.fencing.midsouth.fmswebsite.model.entity.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
}
