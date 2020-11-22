package frontier.learning.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

	@Override
	public void customize(RestTemplate restTemplate) {
		log.info("BlockingRestTemplateCustomizer.customize() :::: Starts ");
		restTemplate.setRequestFactory(this.clientHttpRequestFactory());
		log.info("BlockingRestTemplateCustomizer.customize() :::: Ends ");
	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		log.info("BlockingRestTemplateCustomizer.clientHttpRequestFactory() :::: Starts ");
		/* connectionManager for pooling */
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(100);
		connectionManager.setDefaultMaxPerRoute(20);

		/* Request Config */
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setSocketTimeout(5000)
				.build();

		/* Closing */
		CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(connectionManager)
				.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy()).setDefaultRequestConfig(requestConfig)
				.build();

		log.info("BlockingRestTemplateCustomizer.clientHttpRequestFactory() :::: Ends ");
		return new HttpComponentsClientHttpRequestFactory(closeableHttpClient);
	}

}
