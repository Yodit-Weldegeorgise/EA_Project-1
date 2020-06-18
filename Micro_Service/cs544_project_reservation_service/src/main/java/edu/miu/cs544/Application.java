package edu.miu.cs544;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import edu.miu.cs544.domain.Address;
import edu.miu.cs544.domain.Passenger;
import edu.miu.cs544.domain.Reservation;
import edu.miu.cs544.domain.ReservationDetail;
import edu.miu.cs544.repository.PassengerRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class Application implements CommandLineRunner {
	
	@Autowired
    private PassengerRepository passengerRepository;
	
	private DateFormat dateFormat1 = new SimpleDateFormat("MM dd, yyyy");

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/* Address */
		Address residenceAddress1 = new Address("1000N 4th str","Fairfield","IA","52557");
		Address residenceAddress2 = new Address("1000N 4th str","Chicago","IL","52556");
		Address residenceAddress3 = new Address("1000N 4th str","Chicago","IL","52555");
		Address residenceAddress4 = new Address("1000N 4th str","Chicago","IL","52554");
        
        /*Passenger*/
        Passenger passenger1 = new Passenger("Otgonbayar", "Otgonbayar", dateFormat1.parse("08 07, 1987"), "pass1@miu.edu", residenceAddress1);
        Passenger passenger2 = new Passenger("Nahom", "Berta", dateFormat1.parse("08 07, 1987"), "pass2@miu.edu", residenceAddress2);
        Passenger passenger3 = new Passenger("Yodit", "Wondaferew ", dateFormat1.parse("08 07, 1987"), "pass3@miu.edu", residenceAddress3);
        Passenger passenger4 = new Passenger("Thi Le", "Nguyen", dateFormat1.parse("08 07, 1987"), "pass4@miu.edu", residenceAddress4);
        
        /*ReservationDetails*/ 
        ReservationDetail red1 = new ReservationDetail(1248);
        ReservationDetail red2 = new ReservationDetail(1249);
        ReservationDetail red3 = new ReservationDetail(1250);
        ReservationDetail red4 = new ReservationDetail(1251);
        ReservationDetail red5 = new ReservationDetail(1252);
        ReservationDetail red6 = new ReservationDetail(1253);
        ReservationDetail red7 = new ReservationDetail(1248);
        ReservationDetail red8 = new ReservationDetail(1249);
        ReservationDetail red9 = new ReservationDetail(1250);
        ReservationDetail red10 = new ReservationDetail(1251);
        ReservationDetail red11 = new ReservationDetail(1252);
        ReservationDetail red12 = new ReservationDetail(1253);
        ReservationDetail red13 = new ReservationDetail(1252);
        ReservationDetail red14 = new ReservationDetail(1253);
        ReservationDetail red15 = new ReservationDetail(1248);
        ReservationDetail red16 = new ReservationDetail(1248);
        
        /*Reservation*/
        Reservation reservation1 = new Reservation("123451", passenger1, "omijiddorj@miu.edu", Arrays.asList(red1, red2));
        Reservation reservation2 = new Reservation("123452", passenger1, "nberta@miu.edu", Arrays.asList(red3, red4));
        Reservation reservation3 = new Reservation("123453", passenger2, "agent@miu.edu", Arrays.asList(red5, red6));
        Reservation reservation4 = new Reservation("123454", passenger2, "agent@miu.edu", Arrays.asList(red7, red8));
        Reservation reservation5 = new Reservation("123455", passenger3, "agent@miu.edu", Arrays.asList(red9, red10));
        Reservation reservation6 = new Reservation("123456", passenger3, "admin@miu.edu", Arrays.asList(red11, red12));
        Reservation reservation7 = new Reservation("123457", passenger4, "admin@miu.edu", Arrays.asList(red13, red14));
        Reservation reservation8 = new Reservation("123458", passenger4, "admin@miu.edu", Arrays.asList(red15, red16));
        
        red1.setReservation(reservation1);
        red2.setReservation(reservation1);
        red3.setReservation(reservation2);
        red4.setReservation(reservation2);
        red5.setReservation(reservation3);
        red6.setReservation(reservation3);
        red7.setReservation(reservation4);
        red8.setReservation(reservation4);
        red9.setReservation(reservation5);
        red10.setReservation(reservation5);
        red11.setReservation(reservation6);
        red12.setReservation(reservation6);
        red13.setReservation(reservation7);
        red14.setReservation(reservation7);
        red15.setReservation(reservation8);
        red16.setReservation(reservation8);
        
        passenger1.addReservation(reservation1);
        passenger1.addReservation(reservation2);
        passenger2.addReservation(reservation3);
        passenger2.addReservation(reservation4);
        passenger3.addReservation(reservation5);
        passenger3.addReservation(reservation6);
        passenger4.addReservation(reservation7);
        passenger4.addReservation(reservation8);
        
        passengerRepository.saveAll(Arrays.asList(passenger1, passenger2, passenger3, passenger4));
        
        
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
