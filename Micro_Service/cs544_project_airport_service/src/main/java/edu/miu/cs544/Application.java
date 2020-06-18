package edu.miu.cs544;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import edu.miu.cs544.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import edu.miu.cs544.domain.Address;
import edu.miu.cs544.domain.Airline;
import edu.miu.cs544.domain.Airport;
import edu.miu.cs544.domain.Flight;
import edu.miu.cs544.repository.FlightRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class Application implements CommandLineRunner {
	
	@Autowired
    private FlightRepository flightRepository;

	@Autowired
    private AirlineRepository airlineRepository;
	
	private DateFormat dateFormat = new SimpleDateFormat("MM dd, yyyy hh:mm");
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		/* Airline objects */
        Airline airline1 = new Airline("UA","United Airlines", "AAAAAAAAAAAAAAAAAA");
        Airline airline2 = new Airline("KAL","Korean Air", "AAAAAAAAAAAAAAAAAA");
        Airline airline3 = new Airline("OM","MIAT-MONGOLIAN AIRLINES", "AAAAAAAAAAAAAAAAAA");
        Airline airline4 = new Airline("OM1","MIAT-MONGOLIAN AIRLINES", "AAAAAAAAAAAAAAAAAA");
        Airline airline5 = new Airline("OM2","MIAT-MONGOLIAN AIRLINES", "AAAAAAAAAAAAAAAAAA");
        Airline airline6 = new Airline("OM3","MIAT-MONGOLIAN AIRLINES", "AAAAAAAAAAAAAAAAAA");
        Airline airline7 = new Airline("OM4","MIAT-MONGOLIAN AIRLINES", "AAAAAAAAAAAAAAAAAA");
        
        /* Address */
        Address address1 = new Address("1000N 4th str","Fairfield","IA","52557");
        Address address2 = new Address("1000N 4th str","Chicago","IL","52557");
        Address address3 = new Address("1000N 4th str","Chicago","IL","52556");
        Address address4 = new Address("1000N 4th str","Fairfield","IA","52557");
        Address address5 = new Address("1000N 4th str","Chicago","IL","52557");
        Address address6 = new Address("1000N 4th str","Chicago","IL","52556");
        Address address7 = new Address("1000N 4th str","Fairfield","IA","52557");
        Address address8 = new Address("1000N 4th str","Chicago","IL","52557");
        Address address9 = new Address("1000N 4th str","Chicago","IL","52556");
        Address address10 = new Address("1000N 4th str","Fairfield","IA","52557");
        Address address11 = new Address("1000N 4th str","Chicago","IL","52557");

        /* Airport*/
        Airport cid = new Airport("CID", "Eastern Iowa Airport", address1);
        Airport ams = new Airport("AMS", "Schiphol", address2);
        Airport lhr = new Airport("LHR", "London Heathrow", address3);
        Airport fra = new Airport("FRA", "Frankfurt International Airport", address4);
        Airport dtw = new Airport("DTW", "Detroid City", address5);
        Airport ord = new Airport("ORD", "Chicago O'hare International", address6);
        Airport lax = new Airport("LAX", "Los Angeles International", address7);
        Airport jfk = new Airport("JFK", "John F. Kennedy International", address8);
        Airport nrt = new Airport("NRT", "Narita International Airport", address9);
        Airport syd = new Airport("SYD", "Kingsford Smith", address10);
        Airport sin = new Airport("SIN", "Changi Airport", address11);

        /* Flight */
        Flight flight1 = new Flight(1248, 500, airline1, cid, ord, dateFormat.parse("05 21, 2020 23:00"), dateFormat.parse("05 21, 2020 23:00"));
        Flight flight2 = new Flight(1249, 500, airline2, ams, lhr, dateFormat.parse("05 21, 2020 23:00"), dateFormat.parse("05 21, 2020 23:00"));
        Flight flight3 = new Flight(1250, 500, airline3, fra, dtw, dateFormat.parse("05 21, 2020 23:00"), dateFormat.parse("05 21, 2020 23:00"));
        Flight flight4 = new Flight(1251, 500, airline1, lax, syd, dateFormat.parse("05 21, 2020 23:00"), dateFormat.parse("05 21, 2020 23:00"));
        Flight flight5 = new Flight(1252, 500, airline2, jfk, syd, dateFormat.parse("05 21, 2020 23:00"), dateFormat.parse("05 21, 2020 23:00"));
        Flight flight6 = new Flight(1253, 500, airline3, nrt, sin, dateFormat.parse("05 21, 2020 23:00"), dateFormat.parse("05 21, 2020 23:00"));
 
        flightRepository.saveAll(Arrays.asList(flight1, flight2, flight3, flight4, flight5, flight6));
        airlineRepository.saveAll(Arrays.asList(airline4, airline5, airline6, airline7));
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
