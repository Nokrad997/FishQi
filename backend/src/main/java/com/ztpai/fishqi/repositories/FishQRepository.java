package com.ztpai.fishqi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ztpai.fishqi.entity.FishQSet;
import com.ztpai.fishqi.entity.FishQa;

public interface FishQRepository extends JpaRepository<FishQa, Long> {
    List<FishQa> findFishQaBySetIn(List<FishQSet> set);
}
