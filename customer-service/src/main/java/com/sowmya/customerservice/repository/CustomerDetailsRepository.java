package com.sowmya.customerservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sowmya.customerservice.entity.CustomerEntity;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerEntity, Long> {

  public Optional<CustomerEntity> findByEmail(String email);

  public Long deleteByEmail(String email);
}
