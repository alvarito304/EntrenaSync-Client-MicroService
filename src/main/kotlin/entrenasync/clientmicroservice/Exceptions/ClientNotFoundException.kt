package entrenasync.clientmicroservice.Exceptions

import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ClientNotFoundException : ClientException {
    constructor(id: ObjectId) : super("The client with id: $id, was not found")
    constructor(userId: String) : super("The client with userId: $userId, was not found")
}