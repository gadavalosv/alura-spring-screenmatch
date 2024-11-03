package com.gadv.screenmatch;

import com.gadv.screenmatch.main.Main;
import com.gadv.screenmatch.main.StreamsExample;
import com.gadv.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	@Autowired
	private SerieRepository serieRepository;
	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		Main main = new Main(serieRepository);
		main.showMenu(scanner);
		scanner.close();
//		StreamsExample.showExample();
	}
}
