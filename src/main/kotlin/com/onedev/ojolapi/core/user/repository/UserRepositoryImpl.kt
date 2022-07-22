package com.onedev.ojolapi.core.user.repository

import com.mongodb.client.MongoCollection
import com.onedev.ojolapi.core.user.entity.User
import com.onedev.ojolapi.database.DatabaseComponent
import com.onedev.ojolapi.exception.OjolException
import com.onedev.ojolapi.utils.toResult
import org.bson.conversions.Bson
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import org.litote.kmongo.setValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    @Autowired
    private val databaseComponent: DatabaseComponent
): UserRepository {

    private val databaseName = System.getenv("DB_NAME")
    private fun getCollection(): MongoCollection<User> {
        return databaseComponent.database.getDatabase(databaseName).getCollection()
    }
    override fun insertUser(user: User): Result<Boolean> {
        val existingUser = getUserByUsername(user.username)
        return if (existingUser.isSuccess) {
            throw OjolException("User Exist")
        } else {
            getCollection().insertOne(user).wasAcknowledged().toResult()
        }
    }

    override fun getUserById(id: String): Result<User> {
        return getCollection().findOne(User::id eq id).toResult()
    }

    override fun getUserByUsername(username: String): Result<User> {
        return getCollection().findOne(User::username eq username).toResult("username $username not found")
    }

    override fun getUserByRole(role: String): Result<List<User>> {
        return getCollection().find(User::role eq role).toList().toResult("user with role $role not found")
    }

    override fun updateUser(
        id: String,
        name: String,
        username: String,
        password: String,
        role: String,
        location: String
    ): Result<User> {
        val listBson = ArrayList<Bson>()
        listBson.add(setValue(User::id, id))
        listBson.add(setValue(User::name, name))
        listBson.add(setValue(User::username, username))
        listBson.add(setValue(User::password, password))
        listBson.add(setValue(User::role, role))
        listBson.add(setValue(User::location, location))

        val update = getCollection().updateOne(User::id eq id, listBson)
        return if (update.wasAcknowledged()) {
            getUserById(id)
        } else {
            throw IllegalStateException("Update Gagal")
        }
    }

}