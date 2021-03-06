package frontier.learning.msscbreweryclient.web.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import frontier.learning.msscbreweryclient.web.model.BeerDTO;
import frontier.learning.msscbreweryclient.web.model.BeerStyleEnum;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class BreweryClientTest {

	@Autowired
	BreweryClient breweryClient;

	@MockBean
	RestTemplate restTemplate;

	@Test
	public void testGetBeerById() {
		log.info("BreweryClientTest.testGetBeerById ::::");
		BeerDTO beerDTO = breweryClient.getBeerById(UUID.randomUUID());
		System.out.println(beerDTO.toString());
		assertNotNull(beerDTO);
	}

	@Test
	public void testCreateBeer() {
		log.info("BreweryClientTest.testCreateBeer ::::");
		BeerDTO beerDTO = BeerDTO.builder().beerName("KF-Strong").beerStyle(BeerStyleEnum.LAGER).upc(123321L).build();
		URI uri = breweryClient.saveBeer(beerDTO);
		assertNotNull(uri);
		System.out.println(uri.toString());
	}

	@Test
	public void testUpdateBeer() {
		log.info("BreweryClientTest.testUpdateBeer ::::");
		BeerDTO beerDTO = BeerDTO.builder().beerName("New Beer").build();
		breweryClient.updateBeer(UUID.randomUUID(), beerDTO);
	}

	@Test
	public void testDeleteBeer() {
		log.info("BreweryClientTest.testDeleteBeer ::::");
		breweryClient.deleteBeer(UUID.randomUUID());
	}

}
