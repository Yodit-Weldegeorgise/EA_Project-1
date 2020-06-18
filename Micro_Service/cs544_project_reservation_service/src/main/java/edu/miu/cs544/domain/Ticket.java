package edu.miu.cs544.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true, length = 20)
	private String number;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn
	private ReservationDetail ReservationDetail;
	
	public Ticket() {
		super();
	}
	
	public Ticket(String number, ReservationDetail reservationDetail) {
		super();
		this.number = number;
		ReservationDetail = reservationDetail;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public ReservationDetail getReservationDetail() {
		return ReservationDetail;
	}
	public void setReservationDetail(ReservationDetail reservationDetail) {
		ReservationDetail = reservationDetail;
	}
	
	
}
