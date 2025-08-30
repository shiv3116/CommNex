package com.ordermanagement.orderservice.repository;

import com.ordermanagement.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findById(long id);

    List<Optional<Order>> findByUserId(long userId);
}
