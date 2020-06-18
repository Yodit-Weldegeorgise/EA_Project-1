package edu.miu.cs544.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.miu.cs544.domain.Reservation;
import edu.miu.cs544.domain.Ticket;
import edu.miu.cs544.repository.ReservationRepository;
import edu.miu.cs544.repository.TicketRepository;
import edu.miu.cs544.service.response.TicketResponse;
import edu.miu.cs544.util.Constant.ReservationStatus;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {
	
	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private ReservationRepository reservationRepository;
		
	public List<Ticket> saveAll(List<Ticket> tickets) {
		tickets = ticketRepository.saveAll(tickets);
		return tickets;
	}
	
	@Override
	public List<TicketResponse> purchaseTickets(String code, Integer passengerId) {
		Reservation reservation = reservationRepository.findByCode(code).get();
		
		ReservationStatus status = Optional.ofNullable(reservation.getReservationStatus())
				.orElseThrow(() -> new IllegalArgumentException("Invalid status found"));
		if(status == ReservationStatus.CANCELLED) throw new RuntimeException();
		if(status == ReservationStatus.CONFIRMED) throw new RuntimeException();
				
		List<Ticket> tickets = reservation.getReservationDetails().stream()
								.map(detail -> new Ticket(makeTicketNumber(), detail))
								.collect(Collectors.toList());
	
		reservation.setReservationStatus(ReservationStatus.CONFIRMED);

		ticketRepository.saveAll(tickets);
		reservationRepository.save(reservation);
		
		return tickets.parallelStream()
				.map(ticket -> new TicketResponse(ticket))
				.collect(Collectors.toList());
	}
	
	private static final String[] digits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
	private String getRandomDigit() {
		return digits[(int) Math.floor(Math.random() * 10)];
	}
	
	private String makeTicketNumber() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 20; i++) sb.append(getRandomDigit());
		String ticketNumber = sb.toString();
		Optional<Ticket> ticket = ticketRepository.findByNumber(ticketNumber);
		if (ticket.isPresent()) {
			return makeTicketNumber();
		}
		System.out.println(ticketNumber);
		return ticketNumber;	
	}
	
}
