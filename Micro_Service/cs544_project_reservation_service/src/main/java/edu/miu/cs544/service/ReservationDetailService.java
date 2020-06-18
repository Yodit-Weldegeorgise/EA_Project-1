package edu.miu.cs544.service;

import java.util.List;

import edu.miu.cs544.service.response.ReservationDetailResponse;

public interface ReservationDetailService {
	List<ReservationDetailResponse> getAll();
	List<ReservationDetailResponse> getAllByReservationCode(String reservation_code);
	List<ReservationDetailResponse> getAllByReservationCodeAndPassengerId(String reservation_code, Integer passenger_id);
	List<ReservationDetailResponse> getAllByReservationCodeAndUserEmail(String reservation_code, String user_email);
}
