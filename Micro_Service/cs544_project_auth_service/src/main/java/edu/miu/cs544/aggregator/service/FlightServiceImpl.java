package edu.miu.cs544.aggregator.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import edu.miu.cs544.service.aggregator.response.AirportResponse;
import edu.miu.cs544.service.request.FlightRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import edu.miu.cs544.service.aggregator.response.FlightResponse;

@Service
public class FlightServiceImpl implements FlightService {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
    private EurekaClient eurekaClient;

    @Value("${airport_service}")
    private String airportServiceName;
    
    private String lookupUrlFor(String appName) {
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(appName, false);
        return instanceInfo.getHomePageUrl();
    }
    
	@Override
	public FlightResponse getByNumber(Integer number) {
		return restTemplate.getForObject(lookupUrlFor(airportServiceName) + "/flights?number="+number, FlightResponse.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FlightResponse> getAll() {
		return restTemplate.getForObject(lookupUrlFor(airportServiceName) + "/flights", List.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FlightResponse> getAllByNumbers(List<Integer> numbers) {
		String numbersCommaSeparated = numbers.stream()
                .map(num -> num.toString())
                .collect(Collectors.joining(","));
		return restTemplate.getForObject(lookupUrlFor(airportServiceName) + "/flights?numbers="+numbersCommaSeparated, List.class);
	}

	@Override
	public Collection<FlightResponse> saveAll(Collection<FlightRequest> flights) {
		HttpEntity<String> request = prepareHttpRequest(flights);
		return restTemplate.postForObject(lookupUrlFor(airportServiceName) + "/flights", request, Collection.class);
	}

	@Override
	public FlightResponse put(FlightRequest flightRequest, Integer flightNumber) {
		HttpEntity<String> request = prepareHttpRequest(flightRequest);
		return restTemplate.exchange(lookupUrlFor(airportServiceName) + "/flights/" + flightNumber, HttpMethod.PUT,request, FlightResponse.class).getBody();
	}

	@Override
	public void deleteFlight(Integer flightNumber) {
		restTemplate.delete(lookupUrlFor(airportServiceName) + "/flights/" + flightNumber);
	}

	private HttpEntity<String> prepareHttpRequest(Object token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject json = new JSONObject(token);
		HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
		return request;
	}

	//http://localhost:8081/flights/available?departure_date=05/21/2020&departure_airport=CID&arrival_airport=ORD
		@Override
		public List<FlightResponse> getDepartureAndDestination(Date departure_date, String departure_airport, String arrival_airport) {
			return restTemplate.getForObject(lookupUrlFor(airportServiceName)+"/flights/available?departure_date="+departure_date+"&"+
							"departure_airport="+departure_airport+"&"+"arrival_airport="+arrival_airport,List.class);
		}

}
