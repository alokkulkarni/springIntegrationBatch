package com.example.alok;


import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.support.GenericMessage;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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


	public Optional<List<String>> userDetailsList() {
		jdbcTemplate.setDataSource(dataSource);
		Optional<List<String>> userDetailsList = Optional.ofNullable(jdbcTemplate.queryForList("Select a.firstName from USERDETAILS a",String.class));
		return userDetailsList;
	}


	@Bean
	IntegrationFlow userFlow() {
		return IntegrationFlows.from(() -> new GenericMessage<>(userDetailsList()),e->e.poller(p->p.fixedRate(1000).maxMessagesPerPoll(1)))
				.handle(message -> {
					Optional<List<String>> stringList = (Optional<List<String>>) message.getPayload();
					if (stringList.isPresent()) {
						stringList.get().forEach(System.out::println);
//						List<String> strings = stringList.get();
//						strings.forEach(System.out::println);
					} else {
						System.out.println("no Data present");
					}
				})
				.get();
	}

	@Bean
	CommandLineRunner runner(UserRepository userRepository) {
		return args -> {

			userRepository.deleteAll();

			Stream.of("Alok,Kulkanri","Avinash,Kulkarni","Sarthak,Kulkarni","Sheetal,Kulkarni")
					.map(s -> s.split(","))
					.forEach(tuple -> userRepository.save(new UserDetails(tuple[0],tuple[1])));

			userRepository.findAll().forEach(System.out::println);
		};
	}



	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationBatchApplication.class, args);
	}
}


