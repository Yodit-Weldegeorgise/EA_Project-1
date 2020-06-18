package edu.miu.cs544.aggregator.service;

import java.util.Collection;
import java.util.List;

import edu.miu.cs544.service.aggregator.response.AirlineResponse;
import edu.miu.cs544.service.request.AirlineRequest;
import org.springframework.http.ResponseEntity;

public interface AirlineService {
	AirlineResponse getByCode(String code);
	List<AirlineResponse> getAll();
	List<AirlineResponse> getAllByDepartureAirportCode(String departure_airport_code);

	ResponseEntity<?> saveAll(Collection<AirlineRequest> airlines);

	ResponseEntity<?> put(AirlineRequest airlineRequest, String code);

	ResponseEntity<?> deleteAirline(String code);
}
