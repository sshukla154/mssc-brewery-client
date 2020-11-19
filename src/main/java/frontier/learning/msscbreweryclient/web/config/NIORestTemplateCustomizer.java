package frontier.learning.msscbreweryclient.web.config;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class NIORestTemplateCustomizer implements RestTemplateCustomizer {

	@Override
	public void customize(RestTemplate restTemplate) {
		log.info("NIORestTemplateCustomizer.customize() :::: Starts");
		try {
			restTemplate.setRequestFactory(clientHttpRequestFactory());
			log.info("NIORestTemplateCustomizer.customize() :::: Ends");
		} catch (IOReactorException e) {
			e.printStackTrace();
		}

	}

	private ClientHttpRequestFactory clientHttpRequestFactory() throws IOReactorException {

		log.info("NIORestTemplateCustomizer.clientHttpRequestFactory() :::: Starts");
		final DefaultConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(
				IOReactorConfig.custom().setConnectTimeout(5000).setIoThreadCount(5).setSoTimeout(3000).build());

		final PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(
				ioReactor);
		connectionManager.setDefaultMaxPerRoute(100);
		connectionManager.setMaxTotal(1000);

		CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(connectionManager)
				.build();

		log.info("NIORestTemplateCustomizer.clientHttpRequestFactory() :::: Ends");
		return new HttpComponentsAsyncClientHttpRequestFactory(httpAsyncClient);
	}

}
