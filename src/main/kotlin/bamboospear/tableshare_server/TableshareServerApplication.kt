package bamboospear.tableshare_server

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@OpenAPIDefinition(info = Info(title = "tableShare", version = "1.0", description = "안녕하세요"))
class TableshareServerApplication

fun main(args: Array<String>) {
    runApplication<TableshareServerApplication>(*args)
}
