package entrenasync.clientmicroservice.Mappers

import entrenasync.clientmicroservice.Dtos.ClientCreateRequest
import entrenasync.clientmicroservice.Dtos.ClientResponse
import entrenasync.clientmicroservice.Dtos.ClientUpdateRequest
import entrenasync.clientmicroservice.Models.Client
import org.bson.types.ObjectId
import java.time.LocalDate
import java.time.LocalDateTime

fun ClientCreateRequest.toEntity(): Client{
    return Client(
        id = ObjectId(),
        name = this.name,
        address = this.address,
        avatar = this.avatar,
        phone = this.phone,
        birthDate = LocalDate.parse(birthDate),
        gender = this.gender,
        userId = this.userId,
        hiredServicesIds = this.hiredServicesIds,
        workouts = this.workouts,
    )
}

fun ClientUpdateRequest.toEntity(oldClient: Client): Client{
    return Client(
        id = oldClient.id,
        name = if (this.name != null) this.name else oldClient.name,
        address = if (this.address != null) this.address else oldClient.address,
        avatar = if (this.avatar != null) this.avatar else oldClient.avatar,
        phone = if (this.phone != null) this.phone else oldClient.phone,
        birthDate = oldClient.birthDate,
        gender = if (this.gender != null) this.gender else oldClient.gender,
        userId = oldClient.userId,
        hiredServicesIds = if (this.hiredServicesIds.isEmpty()) {
            oldClient.hiredServicesIds
        } else {
            oldClient.hiredServicesIds + this.hiredServicesIds
        },
        workouts = this.workouts.ifEmpty { oldClient.workouts },
        createdAt = oldClient.createdAt,
        updatedAt = LocalDateTime.now()
    )
}

fun Client.toResponse(): ClientResponse{
    return ClientResponse(
        id = this.id,
        name = this.name,
        address = this.address,
        avatar = this.avatar,
        phone = this.phone,
        birthDate = this.birthDate,
        gender = this.gender,
        userId = this.userId,
        hiredServicesIds = this.hiredServicesIds,
        workouts = this.workouts,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}