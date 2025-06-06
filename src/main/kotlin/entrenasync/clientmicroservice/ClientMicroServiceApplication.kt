package entrenasync.clientmicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class ClientMicroServiceApplication

fun main(args: Array<String>) {
    runApplication<ClientMicroServiceApplication>(*args)
}
