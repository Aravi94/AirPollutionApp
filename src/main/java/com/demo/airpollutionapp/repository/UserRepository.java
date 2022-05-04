package com.demo.airpollutionapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.airpollutionapp.model.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, String> {
	List<UserInfo> findByFirstNameLikeOrLastNameLike(String firstName, String lastName);
	Optional<UserInfo> findByUserIdAndRole(String userId, String role);
	Optional<UserInfo> findByUserIdAndStatus(String userId, String status);
}
