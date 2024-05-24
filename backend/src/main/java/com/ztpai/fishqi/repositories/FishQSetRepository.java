package com.ztpai.fishqi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ztpai.fishqi.entity.Customer;
import com.ztpai.fishqi.entity.FishQSet;

public interface FishQSetRepository extends JpaRepository<FishQSet, Long>{
    List<FishQSet> findFishQSetByOwner(Customer owner);
}
