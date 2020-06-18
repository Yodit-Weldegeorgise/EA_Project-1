package edu.miu.cs544.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.miu.cs544.domain.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	Optional<Reservation> findByCode(String code);
	Optional<Reservation> findByCodeAndPassengerId(String code, Integer passenger_id);
	Optional<Reservation> findByCodeAndUserEmail(String code, String user_email);
	List<Reservation> findByPassengerId(Integer id);
	List<Reservation> findByUserEmailAndPassengerId(String userEmail, Integer passengerId);
	List<Reservation> findByUserEmail(String userEmail);
}
