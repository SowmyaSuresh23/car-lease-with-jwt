package com.sowmya.carleaseplatform.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sowmya.carleaseplatform.entity.CarDetailsEntity;

/**
 * @author Sowmya Suresh
 *
 */
@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetailsEntity, Long> {

  public Long deleteByCarNumberIgnoreCase(String carNumber);

  public Optional<CarDetailsEntity> findByCarNumberIgnoreCase(String carNumber);
}
