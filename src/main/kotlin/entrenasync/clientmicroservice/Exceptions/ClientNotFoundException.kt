package entrenasync.clientmicroservice.Exceptions

import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ClientNotFoundException(id: ObjectId) : ClientException("The client with id: $id, was not found")