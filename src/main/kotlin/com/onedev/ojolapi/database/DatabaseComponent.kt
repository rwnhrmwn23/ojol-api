package com.onedev.ojolapi.database

import com.mongodb.client.MongoClient
import org.litote.kmongo.KMongo
import org.springframework.stereotype.Component

@Component
class DatabaseComponent {
    // DB_NAME=ojol;DB_URL=mongodb+srv://one:XMuYhtJ6wIEHaelS@cluster0.vo2mzss.mongodb.net/?retryWrites=true&w=majority;SECRET=5mxPWaWGo8pEKvXZzOiFsK+8xVQv05s/uiQCRgT/rJk=;CLAIMS=auth
    private val databaseURL = System.getenv("DB_URL")
    val database: MongoClient = KMongo.createClient(databaseURL)
}