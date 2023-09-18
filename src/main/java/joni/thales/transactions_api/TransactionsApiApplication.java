package joni.thales.transactions_api;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info=@Info(
            title="Transaction API",
            description = "Documentation for all the available REST endpoints in this demo project.\n\n" +
                    "Tip: You can interact with the API directly in your browser with the \"Try it out\" button.\n\n" +
                    "© Joni Pohjaniemi",
            version = "1.0.0"
        ),
        externalDocs = @ExternalDocumentation(
                description = "Source code",
                url = "https://github.com/pohjaniemi/transaction_api"
        )
)
@SpringBootApplication
public class TransactionsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionsApiApplication.class, args);
    }

}
