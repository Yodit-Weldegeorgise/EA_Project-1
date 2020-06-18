package edu.miu.cs544.aggregator.service;


import java.util.Collection;
import java.util.List;

import edu.miu.cs544.service.aggregator.response.FlightResponse;
import edu.miu.cs544.service.request.FlightRequest;

public interface FlightService {
	FlightResponse getByNumber(Integer number);
	List<FlightResponse> getAllByNumbers(List<Integer> numbers);
	List<FlightResponse> getAll();

    void deleteFlight(Integer flightNumber);

    FlightResponse put(FlightRequest flightRequest, Integer flightNumber);

	Collection<FlightResponse> saveAll(Collection<FlightRequest> flights);
}
