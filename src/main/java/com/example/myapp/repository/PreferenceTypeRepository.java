package com.example.myapp.repository;

import com.example.myapp.model.PreferenceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface PreferenceTypeRepository extends JpaRepository<PreferenceType, Integer> {


    List<PreferenceType> findByNameIn(Set<String> names);
}
