package com.example.demo.repository;

import com.example.demo.model.Bus;
import com.example.demo.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BusRepo extends JpaRepository<Bus,Integer> {

//    @Query("select b from Bus b where b.route.origin =:origin and b.route.destination=:destination and b.route.startDateTime = :start")
//    List<Bus> findBuses(String origin, String destination, LocalDateTime start);
@Query("""
select b from Bus b
where lower(b.route.origin) = lower(:origin)
and lower(b.route.destination) = lower(:destination)
and (
        b.route.daily = true

        OR

        FUNCTION('DATE', b.route.startDateTime) = :date
)
and FUNCTION('TIME', b.route.startDateTime) >= :time
""")
List<Bus> findBuses(String origin,
                    String destination,
                    LocalDate date,
                    LocalTime time);

    List<Bus> findByBusOperator_Email(String email);

    List<Bus> findByRoute(Route route);
}
