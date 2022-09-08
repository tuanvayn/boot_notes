package com.vmodev.tuanva.notes.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vmodev.tuanva.notes.model.UserInfo;

@Repository
public interface UserRepo extends JpaRepository<UserInfo, Long> {
	boolean existsByUsername(String username);

	Optional<UserInfo> findByUsername(String username);

}
