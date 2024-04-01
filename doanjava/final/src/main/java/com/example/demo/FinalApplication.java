package com.example.demo;

import com.example.demo.controller.MainController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;


@SpringBootApplication
public class FinalApplication {
	public static void main(String[] args) {
		new File(MainController.uploadDirectory).mkdir();
		SpringApplication.run(FinalApplication.class, args);
	}
}
