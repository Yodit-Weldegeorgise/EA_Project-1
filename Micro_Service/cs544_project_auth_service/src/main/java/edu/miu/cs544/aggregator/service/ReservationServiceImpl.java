package edu.miu.cs544.aggregator.service;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import edu.miu.cs544.service.aggregator.response.FlightResponse;
import edu.miu.cs544.service.aggregator.response.PassengerResponse;
import edu.miu.cs544.service.aggregator.response.ReservationDetailResponse;
import edu.miu.cs544.service.aggregator.response.ReservationResponse;
import edu.miu.cs544.service.aggregator.response.ScheduleEmailRequest;
import edu.miu.cs544.service.aggregator.response.ScheduleEmailResponse;
import edu.miu.cs544.service.aggregator.response.TicketResponseAndEmailScheduleRequest;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
    private EurekaClient eurekaClient;
	
	@Autowired
	private FlightService flightService;

    @Value("${reservation_service}")
    private String reservationServiceName;
    
    @Value("${quartz_email_service}")
    private String quartzEmailService;
    
    private String lookupUrlFor(String appName) {
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(appName, false);
        return instanceInfo.getHomePageUrl();
    }
	
	@Override
	public ReservationResponse getByCode(String code) {
		return restTemplate.getForObject(lookupUrlFor(reservationServiceName) + "/reservations?code="+code, ReservationResponse.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReservationResponse> getAll() {
		return restTemplate.getForObject(lookupUrlFor(reservationServiceName) + "/reservations", List.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReservationResponse> getAllByPassengerId(Integer passenger_id) {
		return restTemplate.getForObject(lookupUrlFor(reservationServiceName) + "/reservations?passenger_id="+passenger_id, List.class);
	}

	@Override
	public ScheduleEmailResponse finalizeReservation(String code) {
		TicketResponseAndEmailScheduleRequest ticketsAndEmailRequest = restTemplate
				.getForObject(lookupUrlFor(reservationServiceName) + "/reservations/" + code + "/tickets", TicketResponseAndEmailScheduleRequest.class);
		ScheduleEmailRequest request = buildEmailRequest(ticketsAndEmailRequest);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject json = new JSONObject(request);
		HttpEntity<String> postRequest = new HttpEntity<>(json.toString(), headers);
		ScheduleEmailResponse response = restTemplate.postForObject(lookupUrlFor(quartzEmailService) + "/schedule-email", postRequest, ScheduleEmailResponse.class);
		return response;
	}
	
	private ScheduleEmailRequest buildEmailRequest(TicketResponseAndEmailScheduleRequest ticketsAndEmailScheduleRequest) {
		List<ReservationDetailResponse> details = ticketsAndEmailScheduleRequest
					.getTickets().stream()
					.map(t -> t.getReservationDetail())
					.collect(Collectors.toList());
		PassengerResponse passenger = ticketsAndEmailScheduleRequest.getPassenger();
		ReservationDetailResponse detail = details.get(0);
		FlightResponse flight = flightService.getByNumber(detail.getFlightNumber());
		return new ScheduleEmailRequest(passenger.getEmail(), passenger.getFirstName(), passenger.getLastName(), flight.getNumber(), flight.getAirline().getName(),
				flight.getDepartureAirport().getName(), flight.getArrivalAirport().getName(), flight.getDepartureDate(), flight.getArrivalDate());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReservationResponse> getAllByUserEmail(String user_email) {
		return restTemplate.getForObject(lookupUrlFor(reservationServiceName) + "/reservations?user_email="+user_email, List.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReservationResponse> getAllByUserEmailAndPassengerId(String user_email, Integer passenger_id) {
		return restTemplate.getForObject(
				lookupUrlFor(reservationServiceName) + "/reservations?user_email="+user_email+"&passenger_id="+passenger_id, List.class);
	}

	@Override
	public ReservationResponse getByCodeAndPassengerId(String code, Integer passenger_id) {
		return restTemplate.getForObject(lookupUrlFor(reservationServiceName) 
				+ "/reservations?code="+code+"&passenger_id="+passenger_id, ReservationResponse.class);
	}

	@Override
	public ReservationResponse getByCodeAndUserEmail(String code, String user_email) {
		return restTemplate.getForObject(lookupUrlFor(reservationServiceName) 
				+ "/reservations?code="+code+"&user_email="+user_email, ReservationResponse.class);
	}
}
