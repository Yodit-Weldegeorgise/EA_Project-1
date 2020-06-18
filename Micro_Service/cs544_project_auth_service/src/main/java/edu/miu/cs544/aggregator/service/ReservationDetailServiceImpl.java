package edu.miu.cs544.aggregator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import edu.miu.cs544.domain.ERole;
import edu.miu.cs544.domain.User;
import edu.miu.cs544.service.aggregator.response.ReservationDetailResponse;

@Service
public class ReservationDetailServiceImpl implements ReservationDetailService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
    private EurekaClient eurekaClient;

    @Value("${reservation_service}")
    private String reservationServiceName;
    
    private String lookupUrlFor(String appName) {
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(appName, false);
        return instanceInfo.getHomePageUrl();
    }
    
	@SuppressWarnings("unchecked")
	@Override
	public List<ReservationDetailResponse> getAll() {
		return restTemplate.getForObject(lookupUrlFor(reservationServiceName) + "/reservations/details", List.class);
	}

	@Override
	public ReservationDetailResponse[] getAllByReservationCode(String reservation_code) {
		return restTemplate.getForObject(lookupUrlFor(reservationServiceName) + "/reservations/details?reservation_code="+reservation_code, ReservationDetailResponse[].class);
	}

	@Override
	public ReservationDetailResponse[] getAllByReservationCodeAndPassengerId(String reservation_code,
			Integer passenger_id) {
		return restTemplate.getForObject(lookupUrlFor(reservationServiceName) 
				+ "/reservations/details?reservation_code="+reservation_code
				+ "&passenger_id="+passenger_id
				, ReservationDetailResponse[].class);
	}

	@Override
	public ReservationDetailResponse[] getAllByReservationCodeAndUserEmail(String reservation_code, String user_email) {
		return restTemplate.getForObject(lookupUrlFor(reservationServiceName) 
				+ "/reservations/details?reservation_code="+reservation_code
				+ "?user_email="+user_email
				, ReservationDetailResponse[].class);
	}

	@Override
	public ReservationDetailResponse[] getAllByReservationCodeAndUserRole(String reservation_code, User user) {
		if(user.getRole().getName() == ERole.ROLE_PASSENGER)
		{
			return getAllByReservationCodeAndPassengerId(reservation_code, user.getPassengerId());
		}
		if(user.getRole().getName() == ERole.ROLE_AGENT)
		{
			return getAllByReservationCodeAndUserEmail(reservation_code, user.getUsername());
		}
		return getAllByReservationCode(reservation_code);
	}

}
