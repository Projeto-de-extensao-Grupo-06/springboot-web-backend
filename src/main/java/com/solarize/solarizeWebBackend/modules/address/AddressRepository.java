package com.solarize.solarizeWebBackend.modules.address;

import com.solarize.solarizeWebBackend.modules.address.enumerated.BrazilianState;
import com.solarize.solarizeWebBackend.modules.address.projection.StateCityProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("""
        SELECT DISTINCT a.state AS state, a.city AS city
        FROM Address a
        WHERE a.state IS NOT NULL 
              AND a.city IS NOT NULL
        ORDER BY a.state ASC, a.city ASC
    """)
    List<StateCityProjection> findAllStateCity();
}
