package com.hitzseb.currencyTrader;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "currencyTrader Api"))
public class CurrencyTraderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyTraderApplication.class, args);
	}

}
