package kz.islam.sanayatov.test.exception

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView
import java.lang.RuntimeException

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ModelAndView {
        return ModelAndView("error").addObject("errorMessage", ex.message)
    }
}

class BadRequestException(msg: String): RuntimeException(msg)