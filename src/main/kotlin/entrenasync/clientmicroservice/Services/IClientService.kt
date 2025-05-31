package entrenasync.clientmicroservice.Services

import entrenasync.clientmicroservice.Dtos.ClientCreateRequest
import entrenasync.clientmicroservice.Dtos.ClientResponse
import entrenasync.clientmicroservice.Dtos.ClientUpdateRequest
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface IClientService {
    fun getClients(pageable: Pageable): Page<ClientResponse>
    fun getAllClients(): List<ClientResponse>
    fun getClientByUserId(userId: String): ClientResponse
    fun getClientById(id: ObjectId) : ClientResponse
    fun createClient(client: ClientCreateRequest) : ClientResponse
    fun updateClient(id: ObjectId, client: ClientUpdateRequest): ClientResponse
    fun deleteClient(id: ObjectId)
}