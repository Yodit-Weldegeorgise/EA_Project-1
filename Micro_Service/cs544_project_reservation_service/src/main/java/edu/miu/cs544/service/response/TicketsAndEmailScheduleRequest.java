package edu.miu.cs544.service.response;

import java.util.List;

public class TicketsAndEmailScheduleRequest {
	
	private List<TicketResponse> tickets;
	private PassengerResponse passenger;
	
	public TicketsAndEmailScheduleRequest() {
		
	}
	public TicketsAndEmailScheduleRequest(List<TicketResponse> tickets, PassengerResponse passenger) {
		super();
		this.tickets = tickets;
		this.passenger = passenger;
	}
	public List<TicketResponse> getTickets() {
		return tickets;
	}
	public void setTickets(List<TicketResponse> tickets) {
		this.tickets = tickets;
	}
	public PassengerResponse getPassenger() {
		return passenger;
	}
	public void setPassengerResponse(PassengerResponse passenger) {
		this.passenger = passenger;
	}
	
}
