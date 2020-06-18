package edu.miu.cs544.service;

import edu.miu.cs544.domain.Airline;
import edu.miu.cs544.domain.Airport;
import edu.miu.cs544.domain.Flight;
import edu.miu.cs544.repository.AirlineRepository;
import edu.miu.cs544.repository.AirportRepository;
import edu.miu.cs544.repository.FlightRepository;
import edu.miu.cs544.service.request.FlightRequest;
import edu.miu.cs544.service.response.FlightResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class FlightServiceImpl implements FlightService {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public FlightResponse getById(Integer id) {
        return new FlightResponse(flightRepository.findById(id).get());
    }

    @Override
    public FlightResponse getByNumber(Integer number) {
        return new FlightResponse(flightRepository.findByNumber(number));
    }

    @Override
    public List<FlightResponse> getAll() {
        return flightRepository.findAll()
                .stream()
                .parallel()
                .map(f -> new FlightResponse(f))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightResponse> getAllByDepartureAirportCode(String code) {
        return flightRepository.findByDepartureAirportCode(code)
                .stream()
                .parallel()
                .map(f -> new FlightResponse(f))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightResponse> getAllByNumbers(List<Integer> numbers) {
        return flightRepository.findAllByNumbers(numbers)
                .stream()
                .parallel()
                .map(f -> new FlightResponse(f))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<FlightResponse> saveAll(Collection<FlightRequest> flights) {
        Collection<Flight> res = new ArrayList<>();
        for (FlightRequest f : flights) {
            Airline airline = airlineRepository.findByCode(f.getAirlineCode());
            Airport arrivalAirport = airportRepository.findByCode(f.getArrivalAirportCode());
            Airport departureAirport = airportRepository.findByCode(f.getDepartureAirportCode());
            if (airline == null || arrivalAirport == null || departureAirport == null) {
                throw new IllegalArgumentException("Airline code or Airport code not exist!!!");
            }
            Flight flight = new Flight(f.getNumber(), f.getCapacity(), airline, departureAirport, arrivalAirport, f.getDepartureDate(), f.getArrivalDate());
            res.add(flight);
        }
        flightRepository.saveAll(res);

        return res.stream().map(FlightResponse::new).collect(Collectors.toList());
    }

    @Override
    public FlightResponse put(FlightRequest flightRequest, Integer flightNumber) {
        Airline airline = airlineRepository.findByCode(flightRequest.getAirlineCode());
        Airport arrivalAirport = airportRepository.findByCode(flightRequest.getArrivalAirportCode());
        Airport departureAirport = airportRepository.findByCode(flightRequest.getDepartureAirportCode());
        if (airline == null || arrivalAirport == null || departureAirport == null) {
            throw new IllegalArgumentException("Airline code or Airport code not exist!!!");
        }

        Flight flight = flightRepository.findByNumber(flightNumber);

        if (flight == null) {
            flight = new Flight(flightRequest.getNumber(), flightRequest.getCapacity(), airline, departureAirport, arrivalAirport, flightRequest.getDepartureDate(), flightRequest.getArrivalDate());
            flightRepository.save(flight);
        } else {
            flight.setNumber(flightRequest.getNumber());
            flight.setCapacity(flightRequest.getCapacity());
            flight.setAirline(airline);
            flight.setDepartureAirport(departureAirport);
            flight.setArrivalAirport(arrivalAirport);
            flight.setDepartureDate(flightRequest.getDepartureDate());
            flight.setArrivalDate(flightRequest.getArrivalDate());
        }
        return new FlightResponse(flight);
    }

    @Override
    public FlightResponse deleteFlight(Integer flightNumber) {
        Flight flight = flightRepository.findByNumber(flightNumber);
        if (flight != null) {
            flightRepository.delete(flight);
        } else {
            throw new NoSuchElementException("Flight doesn't exists");
        }
        return new FlightResponse();
    }

    @Override
    public List<FlightResponse> getDepartureAndDestination(Date departure_date, String departure_airport, String arrival_airport) {
        List<Flight> flightList =
                flightRepository.findByDepartureAirportCodeAndArrivalAirportCodeAndDepartureDateAfter(departure_airport,
                        arrival_airport, departure_date);
        return flightList.stream().map(FlightResponse::new).collect(Collectors.toList());
    }
}
