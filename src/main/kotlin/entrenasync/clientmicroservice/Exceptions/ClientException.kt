package entrenasync.clientmicroservice.Exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
abstract class ClientException(message: String) : RuntimeException(message)