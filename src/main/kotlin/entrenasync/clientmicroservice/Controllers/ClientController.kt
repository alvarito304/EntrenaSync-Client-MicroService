package entrenasync.clientmicroservice.Controllers

import entrenasync.clientmicroservice.Dtos.ClientCreateRequest
import entrenasync.clientmicroservice.Dtos.ClientResponse
import entrenasync.clientmicroservice.Dtos.ClientUpdateRequest
import entrenasync.clientmicroservice.Services.IClientService
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.util.function.Consumer


@RestController
@RequestMapping("/Clients")
class ClientController(
    private val clientService: IClientService
) {

    @GetMapping
    fun getAllClients(): ResponseEntity<Page<ClientResponse>>{
        return ResponseEntity.ok()
            .body(clientService.getClients(PageRequest.of(0, 10)))
    }

    @GetMapping("/{id}")
    fun getClientById(@PathVariable id: ObjectId): ResponseEntity<ClientResponse>{
        return ResponseEntity.ok()
            .body(clientService.getClientById(id))
    }

    @PostMapping
    fun createClient(@RequestBody client: ClientCreateRequest): ResponseEntity<ClientResponse>{
        return ResponseEntity.ok()
            .body(clientService.createClient(client))
    }

    @PutMapping("/{id}")
    fun updateClient(@PathVariable id: ObjectId, @RequestBody client: ClientUpdateRequest): ResponseEntity<ClientResponse>{
        return ResponseEntity.ok()
            .body(clientService.updateClient(id, client))
    }

    @DeleteMapping("/{id}")
    fun deleteClient(@PathVariable id: ObjectId): ResponseEntity<Void>{
        clientService.deleteClient(id)
        return ResponseEntity.noContent().build()
    }

}