package edu.miu.cs544.aggregator.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Override
	public void deleteFlight(Integer flightNumber) {
		restTemplate.delete(lookupUrlFor(airportServiceName) + "/flights/" + flightNumber);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FlightResponse> getAllByNumbers(List<Integer> numbers) {
		String numbersCommaSeparated = numbers.stream()
                .map(num -> num.toString())
                .collect(Collectors.joining(","));
		return restTemplate.getForObject(lookupUrlFor(airportServiceName) + "/flights?numbers="+numbersCommaSeparated, List.class);
	}

}
