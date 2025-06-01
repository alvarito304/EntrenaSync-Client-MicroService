package entrenasync.clientmicroservice.Controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import entrenasync.clientmicroservice.Dtos.ClientCreateRequest
import entrenasync.clientmicroservice.Dtos.ClientUpdateRequest
import entrenasync.clientmicroservice.Mappers.toEntity
import entrenasync.clientmicroservice.Mappers.toResponse
import entrenasync.clientmicroservice.Services.IClientService
import io.mockk.every
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(ClientController::class)
class ClientControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var clientService: IClientService

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
    fun getAllClients() {
        val pageable = PageRequest.of(0, 10)
        val page = PageImpl(listOf(sampleResponse), pageable, 1)
        every { clientService.getClients(pageable) } returns page

        mockMvc.perform(get("/Clients"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content[0].id").value(sampleResponse.id.toString()))
            .andExpect(jsonPath("$.content[0].name").value(sampleResponse.name))
            .andExpect(jsonPath("$.totalElements").value(1))
    }

    @Test
    fun getClientByUserId() {
        val userId = "sampleUserId"
        every { clientService.getClientByUserId(userId) } returns sampleResponse

        mockMvc.perform(get("/Clients/user/$userId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(sampleResponse.id.toString()))
            .andExpect(jsonPath("$.name").value(sampleResponse.name))
    }

    @Test
    fun getClientById() {
        every { clientService.getClientById(sampleId) } returns sampleResponse

        mockMvc.perform(get("/Clients/${sampleId.toHexString()}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(sampleResponse.id.toString()))
            .andExpect(jsonPath("$.name").value(sampleResponse.name))
    }

    @Test
    fun createClient() {
        every { clientService.createClient(sampleCreateRequest) } returns sampleResponse

        mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/Clients")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(sampleCreateRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(sampleResponse.id.toString()))
            .andExpect(jsonPath("$.name").value(sampleResponse.name))

    }

    @Test
    fun updateClient() {
        val updateRequest = ClientUpdateRequest(
            name = "updatedName",
            address = "UpdatedAddress",
            avatar = "updatedAvatar",
            phone = "0987654321",
            hiredServicesIds = emptyList(),
            workouts = emptyList(),
            gender = "Masculino"
        )
        every { clientService.updateClient(sampleId, updateRequest) } returns updateRequest.toEntity(sampleEntity).toResponse()

        mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/Clients/${sampleId.toHexString()}")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updateRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(sampleResponse.id.toString()))
            .andExpect(jsonPath("$.name").value("updatedName"))
    }

    @Test
    fun deleteClient() {
        every { clientService.deleteClient(sampleId) } returns Unit

        mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/Clients/${sampleId.toHexString()}")
        )
            .andExpect(status().isNoContent)
    }
}