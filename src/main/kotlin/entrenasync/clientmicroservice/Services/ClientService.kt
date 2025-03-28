package entrenasync.clientmicroservice.Services

import entrenasync.clientmicroservice.Dtos.ClientCreateRequest
import entrenasync.clientmicroservice.Dtos.ClientResponse
import entrenasync.clientmicroservice.Dtos.ClientUpdateRequest
import entrenasync.clientmicroservice.Exceptions.ClientNotFoundException
import entrenasync.clientmicroservice.Mappers.toEntity
import entrenasync.clientmicroservice.Mappers.toResponse
import entrenasync.clientmicroservice.Models.Client
import entrenasync.clientmicroservice.Repositories.IClientRepository
import org.bson.types.ObjectId

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ClientService(
    private val clientRepository: IClientRepository
) : IClientService {

    // private val logger = logging()
    override fun getClients(pageable: Pageable): Page<ClientResponse> {
       // logger.info {"Getting all clients"}
        var clients = clientRepository.findAll(pageable)
        return clients.map { client -> client.toResponse() }
    }

    override fun getClientById(id: ObjectId): ClientResponse {
        // logger.info {"Getting client with id $id"}
        return clientRepository.findById(id).orElseThrow{ClientNotFoundException(id)}.toResponse()
    }

    override fun createClient(client: ClientCreateRequest): ClientResponse {
        // logger.info { "Creating client" }
        val newClient = clientRepository.save(client.toEntity())
        return newClient.toResponse()
    }

    override fun updateClient(id: ObjectId, client: ClientUpdateRequest): ClientResponse {
        // logger.info { "Updating client with id $id" }
        var oldClient = clientRepository.findById(id).orElseThrow{ClientNotFoundException(id)}
        val updatedClient = client.toEntity(oldClient)
        return clientRepository.save(updatedClient).toResponse()
    }

    override fun deleteClient(id: ObjectId) {
        var clientToDelete = clientRepository.findById(id).orElseThrow{ClientNotFoundException(id)}
        return clientRepository.delete(clientToDelete)
    }


}