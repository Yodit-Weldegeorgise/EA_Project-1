package edu.miu.cs544.service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import edu.miu.cs544.domain.Address;
import edu.miu.cs544.domain.Flight;
import edu.miu.cs544.repository.AddressRepository;
import edu.miu.cs544.repository.FlightRepository;
import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.miu.cs544.domain.Airline;
import edu.miu.cs544.repository.AirlineRepository;
import edu.miu.cs544.service.request.AirlineRequest;
import edu.miu.cs544.service.response.AirlineResponse;

@Service
@Transactional
public class AirlineServiceImpl implements AirlineService {

	@Autowired
	private AirlineRepository airlineRepository;

	@Autowired
	private FlightService flightService;

	@Autowired
	private FlightRepository flightRepository;

	@Override
	public AirlineResponse getById(Integer id) {
		return new AirlineResponse(airlineRepository.findById(id).get());
	}

	@Override
	public AirlineResponse getByCode(String code) {
		return new AirlineResponse(airlineRepository.findByCode(code));
	}

	@Override
	public List<AirlineResponse> getAll() {
		return airlineRepository.findAll().parallelStream().map(airline -> new AirlineResponse(airline))
				.collect(Collectors.toList());
	}

	@Override
	public List<AirlineResponse> getAllByDepartureAirportCode(String code) {
		return flightService.getAllByDepartureAirportCode(code).parallelStream().map(flight -> flight.getAirline())
				.collect(Collectors.toList());
	}

	@Override
	public Collection<AirlineResponse> saveAll(Collection<AirlineRequest> airlines) {
		Collection<Airline> res = airlines.stream().map(Airline::new).collect(Collectors.toList());
		airlineRepository.saveAll(res);
		return res.stream().map(AirlineResponse::new).collect(Collectors.toList());
	}

	@Override
	public AirlineResponse put(AirlineRequest airlineRequest, String code) {
		if (!airlineRequest.getCode().equals(code))
			throw new IllegalArgumentException("Code has to be match with code in the body");
		Airline airline = airlineRepository.findByCode(code);

		if (airline == null) {
			airline = new Airline(airlineRequest);
		} else {
			airline.setCode(airlineRequest.getCode());
			airline.setName(airlineRequest.getName());
			airline.setHistory(airlineRequest.getHistory());
		}

		airlineRepository.save(airline);
		return new AirlineResponse(airline);
	}

	@Override
	public AirlineResponse deleteAirline(String code) {
		Airline airline = airlineRepository.findByCode(code);
		if (airline != null) {
			Flight flight = flightRepository.findFirstByAirline(airline);
			if (flight == null)
				airlineRepository.delete(airline);
			else
				throw new IllegalArgumentException("Airline has a flight");
		} else {
			throw new NoSuchElementException("Airline doesn't exists");
		}
		
		return new AirlineResponse();
	}

}
