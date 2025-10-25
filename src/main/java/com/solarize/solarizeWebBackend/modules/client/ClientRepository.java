package com.solarize.solarizeWebBackend.modules.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    boolean existsByDocumentNumber(String documentNumber);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByCnpj(String cnpj);
    boolean existsByDocumentNumberAndIdNot(String documentNumber, Integer id);
    boolean existsByEmailAndIdNot(String email, Integer id);
    boolean existsByPhoneAndIdNot(String phone, Integer id);
    boolean existsByCnpjAndIdNot(String cnpj, Integer id);
}
