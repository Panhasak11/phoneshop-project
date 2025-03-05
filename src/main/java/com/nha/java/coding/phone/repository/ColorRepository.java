package com.nha.java.coding.phone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nha.java.coding.phone.entity.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long>{

}
