package edu.miu.cs544.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import edu.miu.cs544.util.Constant.ReservationStatus;

@Entity
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true)
	private String code;
	private ReservationStatus reservationStatus;
	
	@ManyToOne
	@JoinColumn
	private Passenger passenger;
	
	private String userEmail;
	
	@OneToMany(mappedBy = "reservation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private Collection<ReservationDetail> reservationDetails = new ArrayList<ReservationDetail>();
	
	public Reservation() {
		super();
	}
	
	public Reservation(String code, Passenger passenger, String userEmail, List<ReservationDetail> listReservationDetail) {
		super();
		this.code = code;
		this.passenger = passenger;
		this.userEmail = userEmail;
		this.reservationDetails = listReservationDetail;
		this.reservationStatus = ReservationStatus.PENDING;
	}
	
	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Passenger getPassenger() {
		return passenger;
	}
	
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	
	public Collection<ReservationDetail> getReservationDetails() {
		return Collections.unmodifiableCollection(reservationDetails);
  }
	
	public void setReservationDetails(Collection<ReservationDetail> reservationDetails) {
		this.reservationDetails = reservationDetails;
	}
	
	public boolean addReservationDetail(ReservationDetail reservationDetail) {
		boolean success = false;
		if (reservationDetails.add(reservationDetail)) {
			reservationDetail.setReservation(this);
			success = true;
		}
		return success;
	}
	
	public boolean removeReservationDetail(ReservationDetail reservationDetail) {
		boolean success = false;
		if (reservationDetails.remove(reservationDetail)) {
			reservationDetail.setReservation(null);
			success = true;
		}
		return success;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	
}
