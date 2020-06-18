package edu.miu.cs544.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.miu.cs544.repository.ReservationDetailRepository;
import edu.miu.cs544.service.response.ReservationDetailResponse;

@Service
@Transactional
public class ReservationDetailServiceImpl implements ReservationDetailService {

	@Autowired
	private ReservationDetailRepository reservationDetailRepository;
	
	@Override
	public List<ReservationDetailResponse> getAllByReservationCode(String reservation_code) {
		return reservationDetailRepository.findByReservationCode(reservation_code)
				.parallelStream()
				.map(detail -> new ReservationDetailResponse(detail))
				.collect(Collectors.toList());
	}

	@Override
	public List<ReservationDetailResponse> getAll() {
		return reservationDetailRepository.findAll()
				.parallelStream()
				.map(detail -> new ReservationDetailResponse(detail))
				.collect(Collectors.toList());
	}

	@Override
	public List<ReservationDetailResponse> getAllByReservationCodeAndPassengerId(String reservation_code,
			Integer passenger_id) {
		return reservationDetailRepository.findByReservationCodeAndReservationPassengerId(reservation_code, passenger_id)
				.parallelStream()
				.map(detail -> new ReservationDetailResponse(detail))
				.collect(Collectors.toList());
	}

	@Override
	public List<ReservationDetailResponse> getAllByReservationCodeAndUserEmail(String reservation_code,
			String user_email) {
		return reservationDetailRepository.findByReservationCodeAndReservationUserEmail(reservation_code, user_email)
				.parallelStream()
				.map(detail -> new ReservationDetailResponse(detail))
				.collect(Collectors.toList());
	}
}
