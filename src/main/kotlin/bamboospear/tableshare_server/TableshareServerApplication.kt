package bamboospear.tableshare_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class TableshareServerApplication

fun main(args: Array<String>) {
    runApplication<TableshareServerApplication>(*args)
}
