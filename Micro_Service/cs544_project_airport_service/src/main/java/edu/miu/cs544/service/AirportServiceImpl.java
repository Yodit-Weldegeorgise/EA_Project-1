package edu.miu.cs544.service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import edu.miu.cs544.domain.Flight;
import edu.miu.cs544.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.miu.cs544.domain.Address;
import edu.miu.cs544.domain.Airport;
import edu.miu.cs544.repository.AirportRepository;
import edu.miu.cs544.repository.FlightRepository;
import edu.miu.cs544.service.request.AirportRequest;
import edu.miu.cs544.service.response.AirportResponse;

@Service
@Transactional
public class AirportServiceImpl implements AirportService {
	
	@Autowired
	private AirportRepository airportRepository;

	@Autowired
	private FlightRepository flightRepository;
  
	@Autowired
	private AddressRepository addressRepository;
	
	@Override
	public AirportResponse getByCode(String code) {
		return new AirportResponse(airportRepository.findByCode(code));
	}

	@Override
	public AirportResponse getById(Integer id) {
		return new AirportResponse(airportRepository.findById(id).get());
	}
	
	@Override
	public List<AirportResponse> getAll() {
		return airportRepository.findAll()
				.parallelStream()
				.map(a -> new AirportResponse(a))
				.collect(Collectors.toList());
	}

	@Override
	public Collection<AirportResponse> saveAll(Collection<AirportRequest> airports) {
		Collection<Airport> res = airports.stream().map(Airport::new).collect(Collectors.toList());
		airportRepository.saveAll(res);
		return res.stream().map(AirportResponse::new).collect(Collectors.toList());
	}

	@Override
	public AirportResponse put(AirportRequest airportRequest, String code) {
		if (!airportRequest.getCode().equals(code)) {
			throw new IllegalArgumentException("Code has to match with code in the BODY");
		}
		if (code.length() != 3) {
			throw new IllegalArgumentException("Code length has to be 3 chars");
		}
		Airport airport = airportRepository.findByCode(code);
		if (airport == null) {
			airport = new Airport(airportRequest);
			airportRepository.save(airport);

		} else {
			airport.setAddress(new Address(airportRequest.getAddress()));
			airport.setCode(airportRequest.getCode());
			airport.setName(airportRequest.getName());
			airportRepository.save(airport);
		}
		
		return new AirportResponse(airport);
	}

	@Override
	public AirportResponse deleteAirport(String code) {
		Airport airport = airportRepository.findByCode(code);
		if (airport != null) {
			Flight flight = flightRepository.findFirstByArrivalAirportOrDepartureAirport(airport, airport);
			if (flight == null) {
				Address address = addressRepository.findById(airport.getAddress().getId()).get();
				addressRepository.delete(address);
				airportRepository.delete(airport);
			}
			else
				throw new IllegalArgumentException("Airport has a flight");
		} else {
			throw new NoSuchElementException("Airport doesn't exists");
		}
		return new AirportResponse();
	}
}
