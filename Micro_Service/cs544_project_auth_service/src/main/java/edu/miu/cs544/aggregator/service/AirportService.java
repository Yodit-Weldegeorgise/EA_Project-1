package edu.miu.cs544.aggregator.service;


import java.util.Collection;
import java.util.List;

import edu.miu.cs544.service.aggregator.response.AirportResponse;
import edu.miu.cs544.service.request.AirportRequest;

public interface AirportService {
	AirportResponse getByCode(String code);
	List<AirportResponse> getAll();

    Collection<AirportResponse> saveAll(Collection<AirportRequest> airports);

	AirportResponse put(AirportRequest airportRequest, String code);

	AirportResponse deleteAirport(String code);
}
