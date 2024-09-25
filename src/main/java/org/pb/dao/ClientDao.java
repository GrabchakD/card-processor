package org.pb.dao;

import org.pb.model.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientDao extends JpaRepository<Client, Long> {
    List<Client> findAllByOrderByIdDesc();
}
