package com.ats.webapi.repo.cloudkitchen;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.webapi.model.cloudkitchen.OrderTrail;

public interface OrderTrailRepo extends JpaRepository<OrderTrail, Integer> {


}
