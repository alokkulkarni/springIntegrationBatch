package com.example.alok;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by alokkulkarni on 13/02/17.
 */
@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {

}
