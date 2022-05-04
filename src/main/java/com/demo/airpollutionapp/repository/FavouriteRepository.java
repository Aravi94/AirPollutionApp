package com.demo.airpollutionapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.airpollutionapp.model.Favourite;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
	List<Favourite> findByUserInfoUserId(String userId);
	Optional<Favourite> findByUserInfoUserIdAndCity(String userId, String city);
}
