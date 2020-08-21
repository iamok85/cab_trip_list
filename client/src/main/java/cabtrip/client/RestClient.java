package cabtrip.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestClient {

	private static final Logger log = LoggerFactory.getLogger(RestClient.class);

	public static void main(String[] args) {
		SpringApplication.run(RestClient.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			try {

				if (args[0].equals("help")) {
					
					help();

				} else {

					// first argument always is a url
					// check the second argument if is cleancache for setup rest api request for
					// cleaning cache
					if (args.length>1) {

						if (args[1].equals("cleancache")) {
							ResponseEntity<String> response;

							if (args.length == 3 && args[2] != null && !args[2].equals("")) {
								log.info("Cleaning cache for date " + args[2]);
								response = restTemplate.getForEntity(args[0] + "/cleancache/" + args[2], String.class);
							} else {
								log.info("Cleaning whole cache");
								response = restTemplate.getForEntity(args[0] + "/cleancache", String.class);
							}
							log.info(response.getBody());
						} else { // setup rest api request for retrieving cab trip data
							String cache = "true";
							if (args.length == 3)
								cache = args[2];
							log.info("Retrieving trip summary for date " + args[1] + ". Cacheable:" + cache + " from "
									+ args[0] + ".");
							ResponseEntity<List> response = restTemplate.getForEntity(
									args[0] + "/cabtripcountpickup?pickupdate=" + args[1] + "&cache=" + cache,
									List.class);
							log.info(response.getBody().toString());
						}
					}else {
						log.info("There is an error on the commandline, please follow the help manual.");
						help();
					}

				}
			} catch (HttpClientErrorException ex) {
				log.error(ex.getMessage());
			}
		};
	}
	
	private void help() {
		log.info("==================================================================");
		log.info("Usage1: Retrieve Trip Summary");
		log.info("	java -jar {filepath}/client-1.0.0.jar {root_url:port} {pickup_date} {cache}");
		log.info(
				"	root_url should be starting with 'http' and it is the url of rest service, please starting up the rest service before run this command");
		log.info(
				"	Note: {pickup date} is a required field and should be a date value in format yyyy-mm-dd  Ex.2013-12-03");
		log.info(
				"	Note: {cache} is a optional field and should be a boolean value true or false. Default value:true");
		log.info("");

		log.info("Usage2: Clean Cache");
		log.info("	java -jar {filepath}/client-1.0.0.jar {root_url:port} cleancache {pickup_date}");
		log.info(
				"	root_url should be starting with 'http' and it is the url of rest service, please starting up the rest service before run this command");
		log.info(
				"	Note: {pickup date} is a optional field and should be a date value in format yyyy-mm-dd  Ex.2013-12-03");
		log.info("==================================================================");
	}
}
