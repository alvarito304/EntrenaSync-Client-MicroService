package entrenasync.clientmicroservice.Repositories

import entrenasync.clientmicroservice.Models.Client
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface IClientRepository : MongoRepository<Client, ObjectId> {
}