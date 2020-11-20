package frontier.learning.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Data
@ConfigurationProperties
public class BlockingRestTemplateCustomizerAssignment implements RestTemplateCustomizer {

	@Value("${frontier.assignment.maxtotalconnections}")
	private Integer maxTotalConnections;
	
	@Value("${frontier.assignment.defaultmaxtotalconnections}")
	private Integer defaultMaxTotalConnections;
	
	@Value("${frontier.assignment.connectionrequesttimeout}")
	private Integer connectionRequestTimeout;
	
	@Value("${frontier.assignment.sockettimeout}")
	private Integer socketTimeout;

//	public BlockingRestTemplateCustomizerAssignment(
//			@Value("${frontier.assignment.maxtotalconnections}") Integer maxTotalConnections,
//			@Value("${frontier.assignment.defaultmaxtotalconnections}") Integer defaultMaxTotalConnections,
//			@Value("${frontier.assignment.connectionrequesttimeout}") Integer connectionRequestTimeout,
//			@Value("${frontier.assignment.sockettimeout}") Integer socketTimeout) {
//		this.maxTotalConnections = maxTotalConnections;
//		this.defaultMaxTotalConnections = defaultMaxTotalConnections;
//		this.connectionRequestTimeout = connectionRequestTimeout;
//		this.socketTimeout = socketTimeout;
//	}

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
