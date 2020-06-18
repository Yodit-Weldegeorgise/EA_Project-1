package edu.miu.cs544.aggregator.service;


import java.util.List;

import edu.miu.cs544.service.aggregator.response.FlightResponse;

public interface FlightService {
	FlightResponse getByNumber(Integer number);
	List<FlightResponse> getAllByNumbers(List<Integer> numbers);
	List<FlightResponse> getAll();

    void deleteFlight(Integer flightNumber);
}
