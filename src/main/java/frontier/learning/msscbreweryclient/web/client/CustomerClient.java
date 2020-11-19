package frontier.learning.msscbreweryclient.web.client;

import java.net.URI;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import frontier.learning.msscbreweryclient.web.model.CustomerDTO;

@Component
@ConfigurationProperties(prefix = "brewery", ignoreUnknownFields = false)
public class CustomerClient {

	private String CUSTOMER_PATH_V1 = "/api/assignment/customer/";

	private String apihost;

	private final RestTemplate restTemplate;

	private String URL = "http://localhost:7070" + CUSTOMER_PATH_V1;

	public CustomerClient(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public void setApihost(String apihost) {
		this.apihost = apihost;
	}

	// GetCustomerById
	public CustomerDTO getCustomerById(UUID customerId) {
		return restTemplate.getForObject(URL + customerId, CustomerDTO.class);
	}

	// CreateCustomer
	public URI createCustomer(UUID customerId, CustomerDTO customerDTO) {
		return restTemplate.postForLocation(URL + customerId, customerDTO);
	}

	// UpdateCustomerbyId
	public void updateCustomerById(UUID customerId, CustomerDTO customerDTO) {
		restTemplate.put(URL + customerId, customerDTO);
	}

	// DeleteCustomer
	public void deleteCustomer(UUID customerId) {
		restTemplate.delete(URL + customerId);
	}

}
