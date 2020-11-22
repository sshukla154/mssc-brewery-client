package frontier.learning.msscbreweryclient.web.client;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import frontier.learning.msscbreweryclient.web.model.BeerDTO;

/*
 * 
 * TODO: @ConfigurationProperties is not working as of now
 * 
 * */

@Component
@PropertySource("file:src/main/resources/application.properties")
//@ConfigurationProperties(prefix = "brewery", ignoreUnknownFields = false)
public class BreweryClient {

	private String BEER_PATH_V1 = "/api/v1/beer/";

	private String apihost;

	private final RestTemplate restTemplate;

	private String URL = "http://localhost:7070" + BEER_PATH_V1;

	public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Value("${brewery.apihost}")
	public void setApihost(String apihost) {
		this.apihost = apihost;
	}

	public BeerDTO getBeerById(UUID beerId) {
		return restTemplate.getForObject(apihost + BEER_PATH_V1 + beerId.toString(), BeerDTO.class);
//		return restTemplate.getForObject(URL + beerId.toString(), BeerDTO.class);
	}

	public URI saveBeer(BeerDTO beerDTO) {
		return restTemplate.postForLocation(apihost + BEER_PATH_V1, beerDTO);
//		return restTemplate.postForLocation(URL, beerDTO);
	}

	public void updateBeer(UUID beerId, BeerDTO beerDTO) {
		restTemplate.put(apihost + BEER_PATH_V1 + beerId.toString(), beerDTO);
//		restTemplate.put(URL + beerId.toString(), beerDTO);
	}

	public void deleteBeer(UUID beerId) {
		restTemplate.delete(apihost + BEER_PATH_V1 + beerId.toString());
//		restTemplate.delete(URL + beerId.toString());
	}

}
