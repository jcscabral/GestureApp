package com.example.gestureapp.data.database

import kotlinx.coroutines.flow.Flow
import com.example.gestureapp.data.database.UserDao
class OfflineUsersRepository (private val userDao: UserDao) : UsersRepository {

    override val allUsers = userDao.getAllUsers()
    override fun getAllUsersStream(): Flow<List<User>> = userDao.getAllUsers()

    override fun getUserStream(id: Int): Flow<User?> = userDao.getUser(id)

    override suspend fun insertUser(user: User) = userDao.insert(user)

    override suspend fun deleteUser(user: User) = userDao.delete(user)

    override suspend fun updateUser(user: User) = userDao.update(user)
}