package kz.islam.sanayatov.test.controller

import kz.islam.sanayatov.test.service.PhoneBookingService
import kz.islam.sanayatov.test.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
class PhoneController {

    @Autowired
    private lateinit var phoneBookingService: PhoneBookingService

    @Autowired
    private lateinit var userService: UserService

    @GetMapping("/phone", "/")
    fun getPhones(model: Model): ModelAndView {
        return buildPhoneModelView()
    }

    @PostMapping("/phone/{phoneId}/book")
    fun bookPhone(@PathVariable phoneId: UUID, model: Model): ModelAndView {
        phoneBookingService.bookPhone(phoneId)
        return buildPhoneModelView()
    }

    @PostMapping("/phone/{phoneId}/return")
    fun revertPhone(@PathVariable phoneId: UUID, model: Model): ModelAndView {
        phoneBookingService.returnPhone(phoneId)
        return buildPhoneModelView()
    }

    private fun buildPhoneModelView() = ModelAndView("phones").apply {
        addObject("phones", phoneBookingService.getPhones())
        addObject("bookings", phoneBookingService.getBookings())
        addObject("user", userService.getCurrentUser())
    }
}