package com.example.alok;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@EnableIntegration
@EnableBatchProcessing
@SpringBootApplication
public class SpringIntegrationBatchApplication {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JobRepository jobRepository;


	@Bean
	MessageSource<Object> jdbcMessageSource() {
		return new JdbcPollingChannelAdapter(this.dataSource, "Select * from User WHERE firstName = 'Avinash' and Status = '0'");
	}

	@Bean
	IntegrationFlow pollingFlow() {
		return IntegrationFlows.from(jdbcMessageSource(),
				c -> c.poller(Pollers.fixedRate(1000).maxMessagesPerPoll(1)))
				.handle(System.out::println)
				.get();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationBatchApplication.class, args);
	}
}


