package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.client.dto.CreateClientDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    boolean existsByDocumentNumber(String documentNumber);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByCnpj(String cnpj);

}
