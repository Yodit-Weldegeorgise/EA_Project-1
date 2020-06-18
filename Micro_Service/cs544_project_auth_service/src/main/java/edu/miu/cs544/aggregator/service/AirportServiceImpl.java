package edu.miu.cs544.aggregator.service;

import java.util.Collection;
import java.util.List;

import edu.miu.cs544.service.aggregator.response.AirlineResponse;
import edu.miu.cs544.service.request.AirportRequest;
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

import edu.miu.cs544.service.aggregator.response.AirportResponse;

@Service
public class AirportServiceImpl implements AirportService {
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
	public AirportResponse getByCode(String code) {
		return restTemplate.getForObject(lookupUrlFor(airportServiceName) + "/airports?code="+code, AirportResponse.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AirportResponse> getAll() {
		return restTemplate.getForObject(lookupUrlFor(airportServiceName) + "/airports", List.class);
	}

	@Override
	public Collection<AirportResponse> saveAll(Collection<AirportRequest> airports) {
		HttpEntity<String> request = prepareHttpRequest(airports);
		return restTemplate.postForObject(lookupUrlFor(airportServiceName) + "/airports", request, Collection.class);
	}

	@Override
	public AirportResponse put(AirportRequest airportRequest, String code) {
		HttpEntity<String> request = prepareHttpRequest(airportRequest);
		return restTemplate.exchange(lookupUrlFor(airportServiceName) + "/airports/" + code, HttpMethod.PUT,request, AirportResponse.class).getBody();
	}

	@Override
	public AirportResponse deleteAirport(String code) {
		return restTemplate.exchange(lookupUrlFor(airportServiceName) + "/airports/" + code, HttpMethod.DELETE, new HttpEntity<>(""), AirportResponse.class).getBody();
	}

	private HttpEntity<String> prepareHttpRequest(Object token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject json = new JSONObject(token);
		HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
		return request;
	}

}
