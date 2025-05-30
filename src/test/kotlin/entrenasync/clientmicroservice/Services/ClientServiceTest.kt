package entrenasync.clientmicroservice.Services

import entrenasync.clientmicroservice.Dtos.ClientCreateRequest
import entrenasync.clientmicroservice.Dtos.ClientUpdateRequest
import entrenasync.clientmicroservice.Exceptions.ClientNotFoundException
import entrenasync.clientmicroservice.Mappers.toEntity
import entrenasync.clientmicroservice.Mappers.toResponse
import entrenasync.clientmicroservice.Repositories.IClientRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

@ExtendWith(MockKExtension::class)
class ClientServiceTest {

    @MockK
    lateinit var clientRepository: IClientRepository

    @InjectMockKs
    lateinit var service: ClientService

    private val sampleId = ObjectId.get()
    private val sampleCreateRequest = ClientCreateRequest(
        name = "sampleName",
        address = "SampleAddress",
        avatar = "sampleAvatar",
        phone = "1234567890",
        birthDate = "2020-10-20",
        gender = "sampleGender",
        userId = "sampleUserId",
        hiredServicesIds = emptyList(),
        workouts = emptyList()
    )

    private val sampleEntity by lazy {
        sampleCreateRequest.toEntity().apply {
            id = sampleId
        }
    }
    private val sampleResponse by lazy { sampleEntity.toResponse() }

    @Test
    fun getClients() {

        val pageable = PageRequest.of(0, 5)
        val page = PageImpl(listOf(sampleEntity), pageable, 1)
        every { clientRepository.findAll(pageable) } returns page

        val result = service.getClients(pageable)

        assertEquals(1, result.totalElements)
        assertEquals(sampleResponse, result.content.first())
        verify { clientRepository.findAll(pageable) }


    }

    @Test
    fun getClientByUserId() {
        val userId = "sampleUserId"
        every { clientRepository.findClientByUserId(userId) } returns sampleEntity

        val result = service.getClientByUserId(userId)

        assertEquals(sampleResponse, result)
        verify { clientRepository.findClientByUserId(userId) }
    }

    @Test
    fun getClientById() {
        every { clientRepository.findById(sampleId) } returns java.util.Optional.of(sampleEntity)

        val result = service.getClientById(sampleId)

        assertEquals(sampleResponse, result)
        verify { clientRepository.findById(sampleId) }
    }

    @Test
    fun `getClientById throws ClientNotFoundException when not foun`() {
        every { clientRepository.findById(sampleId) }.returns(java.util.Optional.empty())

        assertThrows(ClientNotFoundException::class.java) {
            service.getClientById(sampleId)
        }
        verify { clientRepository.findById(sampleId) }
    }

    @Test
    fun createClient() {
        every { clientRepository.save(any()) } returns sampleEntity

        val result = service.createClient(sampleCreateRequest)

        assertEquals(sampleResponse, result)
        verify { clientRepository.save(any()) }
    }

    @Test
    fun updateClient() {
        val updateRequest = ClientUpdateRequest(
            name = "updatedName",
            address = "UpdatedAddress",
            avatar = "updatedAvatar",
            phone = "0987654321",
            hiredServicesIds = emptyList(),
            workouts = emptyList()
        )
        every { clientRepository.findById(sampleId) } returns java.util.Optional.of(sampleEntity)
        every { clientRepository.save(any()) } returns updateRequest.toEntity(sampleEntity).apply {
            id = sampleId
            updatedAt = sampleEntity.updatedAt
        }
        val result = service.updateClient(sampleId, updateRequest)

        assertEquals(sampleId, result.id)
        assertEquals(updateRequest.name, result.name)
        assertEquals(updateRequest.address, result.address)
        assertEquals(updateRequest.avatar, result.avatar)
        assertEquals(updateRequest.phone, result.phone)
        assertEquals(sampleEntity.birthDate, result.birthDate)
        verify { clientRepository.findById(sampleId) }
        verify { clientRepository.save(any()) }
    }

    @Test
    fun `updateClient throws ClientNotFoundException when not found`() {
        val updateRequest = ClientUpdateRequest(
            name = "updatedName",
            address = "UpdatedAddress",
            avatar = "updatedAvatar",
            phone = "0987654321",
            hiredServicesIds = emptyList(),
            workouts = emptyList()
        )
        every { clientRepository.findById(sampleId) }.returns(java.util.Optional.empty())

        assertThrows(ClientNotFoundException::class.java) {
            service.updateClient(sampleId, updateRequest)
        }
        verify { clientRepository.findById(sampleId) }
    }

    @Test
    fun deleteClient() {
        every { clientRepository.findById(sampleId) } returns java.util.Optional.of(sampleEntity)
        every { clientRepository.delete(sampleEntity) } returns Unit

        service.deleteClient(sampleId)

        verify { clientRepository.findById(sampleId) }
        verify { clientRepository.delete(sampleEntity) }
    }

    @Test
    fun `getClientById throws ClientNotFoundException when not found`() {
        every { clientRepository.findById(sampleId) }.returns(java.util.Optional.empty())
        assertThrows(ClientNotFoundException::class.java) {
            service.getClientById(sampleId)
        }
        verify { clientRepository.findById(sampleId) }
    }
}