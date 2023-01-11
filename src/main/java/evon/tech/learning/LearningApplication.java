package evon.tech.learning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LearningApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(LearningApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder ;


	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(passwordEncoder.encode("abcd"));
		
	}

}
