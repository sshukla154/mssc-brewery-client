package frontier.learning.msscbreweryclient.web.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import frontier.learning.msscbreweryclient.web.model.CustomerDTO;

@SpringBootTest
public class CustomerClientTest {

	@Autowired
	CustomerClient customerClient;

	@MockBean
	RestTemplate restTemplate;

	@Test
	public void testGetBeerById() {
		CustomerDTO customerDTO = customerClient.getCustomerById(UUID.randomUUID());
		System.out.println(customerDTO.toString());
		assertNotNull(customerDTO);
	}

	@Test
	public void testCreateCustomer() {
		CustomerDTO customerDTO = CustomerDTO.builder().name("Hello World").build();
		URI uri = customerClient.createCustomer(UUID.randomUUID(), customerDTO);
		assertNotNull(uri);
		System.out.println(uri.toString());
	}

	@Test
	public void testUpdateBeer() {
		CustomerDTO customerDTO = CustomerDTO.builder().name("New Customer").build();
		customerClient.updateCustomerById(UUID.randomUUID(), customerDTO);
	}

	@Test
	public void testDeleteCustomer() {
		customerClient.deleteCustomer(UUID.randomUUID());
	}

}
