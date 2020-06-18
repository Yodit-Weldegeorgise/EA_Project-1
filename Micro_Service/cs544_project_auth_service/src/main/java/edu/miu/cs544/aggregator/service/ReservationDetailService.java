package edu.miu.cs544.aggregator.service;

import java.util.List;

import edu.miu.cs544.domain.User;
import edu.miu.cs544.service.aggregator.response.ReservationDetailResponse;

public interface ReservationDetailService {
	List<ReservationDetailResponse> getAll();
	ReservationDetailResponse[] getAllByReservationCode(String reservation_code);
	ReservationDetailResponse[] getAllByReservationCodeAndUserRole(String reservation_code, User user);
	ReservationDetailResponse[] getAllByReservationCodeAndPassengerId(String reservation_code, Integer passenger_id);
	ReservationDetailResponse[] getAllByReservationCodeAndUserEmail(String reservation_code, String user_email);
}
