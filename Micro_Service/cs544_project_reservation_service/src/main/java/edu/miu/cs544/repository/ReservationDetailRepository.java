package edu.miu.cs544.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.miu.cs544.domain.ReservationDetail;

@Repository
public interface ReservationDetailRepository extends JpaRepository<ReservationDetail, Integer> {
	List<ReservationDetail> findByReservationCode(String code);
	List<ReservationDetail> findByReservationCodeAndReservationPassengerId(String code, Integer passenger_id);
	List<ReservationDetail> findByReservationCodeAndReservationUserEmail(String code, String user_email);
}
