package kz.islam.sanayatov.test.service

import kz.islam.sanayatov.test.data.Phone
import kz.islam.sanayatov.test.data.User
import kz.islam.sanayatov.test.exception.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.collections.HashMap
import kotlin.concurrent.withLock

interface PhoneBookingService {
    fun getPhones(): Map<UUID, Phone>
    fun getBookings(): Map<Phone, Pair<User, LocalDateTime>>
    fun bookPhone(phoneId: UUID)
    fun returnPhone(phoneId: UUID)
}

@Service
class PhoneBookingServiceImpl: PhoneBookingService {

    @Autowired
    private lateinit var userService: UserService

    private val phones: Map<UUID, Phone> = mapOf(
        UUID.fromString("00000000-0000-0000-0000-000000000000") to Phone(name = "Samsung Galaxy S9"),
        UUID.fromString("00000000-0000-0000-0000-000000000001") to Phone(name = "Samsung Galaxy S8"),
        UUID.fromString("00000000-0000-0000-0000-000000000002") to Phone(name = "Samsung Galaxy S7"),
        UUID.fromString("00000000-0000-0000-0000-000000000003") to Phone(name = "Motorola Nexus 6"),
        UUID.fromString("00000000-0000-0000-0000-000000000004") to Phone(name = "LG Nexus 5X"),
        UUID.fromString("00000000-0000-0000-0000-000000000005") to Phone(name = "Huawei Honor 7X"),
        UUID.fromString("00000000-0000-0000-0000-000000000006") to Phone(name = "Apple iPhone X"),
        UUID.fromString("00000000-0000-0000-0000-000000000007") to Phone(name = "Apple iPhone 8"),
        UUID.fromString("00000000-0000-0000-0000-000000000008") to Phone(name = "Apple iPhone 4s"),
        UUID.fromString("00000000-0000-0000-0000-000000000009") to Phone(name = "Nokia 3310")
    )

    private val bookings: HashMap<Phone, Pair<User, LocalDateTime>> = HashMap()

    private val lock = ReentrantLock()

    override fun bookPhone(phoneId: UUID): Unit {
        validatePhoneId(phoneId)

        val phone = phones[phoneId]
        lock.withLock {
            if (bookings.containsKey(phone)) {
                throw BadRequestException("Phone already in use by ".plus(bookings[phone]!!.first.name))
            }

            bookings[phone!!] = Pair(userService.getCurrentUser(), LocalDateTime.now())
        }
    }

    override fun returnPhone(phoneId: UUID): Unit {
        validatePhoneId(phoneId)

        val phone = phones[phoneId]
        lock.withLock {
            if (bookings.containsKey(phone).not()) {
                throw BadRequestException("Not phone found in use")
            }

            val holder = bookings[phone]!!.first
            if (holder != userService.getCurrentUser()) {
                throw BadRequestException("Phone can be returned only by ".plus(holder.name))
            }
            bookings.remove(phone!!)
        }
    }

    override fun getPhones() = phones

    override fun getBookings() = bookings

    private fun validatePhoneId(phoneId: UUID) {
        if (phones.containsKey(phoneId).not()) {
            throw BadRequestException("Undefined phone id: ".plus(phoneId))
        }
    }
}