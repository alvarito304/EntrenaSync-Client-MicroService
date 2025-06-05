package entrenasync.clientmicroservice.Models


import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime


@Document("Clients")
@TypeAlias("Clients")
data class Client(
    @Id
    var id: ObjectId = ObjectId(),

    @field:Min(value = 3, message = "Minimum value for client name is 3")
    @field:Max(value = 40, message = "Max value for client name is 40")
    @field:NotBlank(message = "Client name must not be empty")
    var name: String?,

    @field:Min(value = 5, message = "Minimum value for client address is 5")
    @field:Max(value = 150, message = "Max value for client addres is 150")
    var address: String?,

    @field:NotBlank(message = "Client avatar must not be empty")
    var avatar: String? = "METER URL DEFAULT",

    @field:Min(value = 9, message = "Minimum value for client phone is 9")
    @field:Max(value = 15, message = "Max value for client phone is 15")
    @field:NotBlank(message = "Client phone must not be empty")
    var phone: String?,

    @field:NotBlank(message = "Client birth date must not be empty")
    var birthDate: LocalDate,

    @field:NotBlank(message = "Client birth date must not be empty")
    var gender: String?,


    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    // Relations
    var userId: String,
    var hiredServicesIds: List<String> = emptyList(),
    var workouts: List<String> = emptyList(),
){
    @JsonProperty("id")
    fun get_id(): String {
        return id.toHexString()
    }
}
