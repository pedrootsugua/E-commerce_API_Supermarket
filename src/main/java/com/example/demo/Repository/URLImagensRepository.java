package com.example.demo.Repository;

import com.example.demo.Model.URLImagensModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLImagensRepository extends JpaRepository<URLImagensModel, Long> {
}
