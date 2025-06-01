package entrenasync.clientmicroservice.Dtos

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.bson.types.ObjectId
import java.time.LocalDateTime

data class ClientUpdateRequest(

    @field:Size(min = 3, max = 40, message = "Client name must be between 3 and 40 characters")
    var name: String?,

    @field:Size(min = 5, max = 150, message = "Client address must be between 5 and 150 characters")
    var address: String?,

    var avatar: String?,

    @field:Size(min = 9, max = 15, message = "Client phone must be between 9 and 15 digits")
    var phone: String?,

    var gender: String,

    var hiredServicesIds: List<String> = emptyList(),
    var workouts: List<String> = emptyList(),
)
