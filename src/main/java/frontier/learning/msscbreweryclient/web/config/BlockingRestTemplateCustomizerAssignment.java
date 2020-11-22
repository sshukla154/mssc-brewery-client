package frontier.learning.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@PropertySource("file:src/main/resources/application.properties")
public class BlockingRestTemplateCustomizerAssignment implements RestTemplateCustomizer {

	private Integer maxTotalConnections;

	private Integer defaultMaxTotalConnections;

	private Integer connectionRequestTimeout;

	private Integer socketTimeout;

	@Value("${frontier.assignment.maxtotalconnections}")
	public void setMaxTotalConnections(Integer maxTotalConnections) {
		this.maxTotalConnections = maxTotalConnections;
	}

	@Value("${frontier.assignment.defaultmaxtotalconnections}")
	public void setDefaultMaxTotalConnections(Integer defaultMaxTotalConnections) {
		this.defaultMaxTotalConnections = defaultMaxTotalConnections;
	}

	@Value("${frontier.assignment.connectionrequesttimeout}")
	public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}

	@Value("${frontier.assignment.sockettimeout}")
	public void setSocketTimeout(Integer socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	@Override
	public void customize(RestTemplate restTemplate) {
		log.info("BlockingRestTemplateCustomizerAssignment.customize() :::: Starts ");
		restTemplate.setRequestFactory(this.clientHttpRequestFactory());
		log.info("BlockingRestTemplateCustomizerAssignment.customize() :::: Ends ");
	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		log.info("BlockingRestTemplateCustomizerAssignment.clientHttpRequestFactory() :::: Starts ");
		/* connectionManager for pooling */
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(maxTotalConnections);
		connectionManager.setDefaultMaxPerRoute(defaultMaxTotalConnections);

		/* Request Config */
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
				.setSocketTimeout(socketTimeout).build();

		/* Closing */
		CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(connectionManager)
				.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy()).setDefaultRequestConfig(requestConfig)
				.build();

		log.info("BlockingRestTemplateCustomizerAssignment.clientHttpRequestFactory() :::: Ends ");
		return new HttpComponentsClientHttpRequestFactory(closeableHttpClient);
	}

}
