package com.garylee.tmall_springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.garylee.tmall_springboot.dao")
public class TmallSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmallSpringbootApplication.class, args);
	}
}
