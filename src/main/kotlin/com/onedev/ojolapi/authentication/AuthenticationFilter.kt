package com.onedev.ojolapi.authentication

import com.fasterxml.jackson.databind.ObjectMapper
import com.onedev.ojolapi.exception.OjolException
import com.onedev.ojolapi.response.BaseResponse
import com.onedev.ojolapi.utils.Empty
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationFilter : OncePerRequestFilter() {

    private val claims = System.getenv("CLAIMS")

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            if (JwtConfig.isPermit(request)) {
                filterChain.doFilter(request, response)
            } else {
                val claims = validate(request)
                if (claims[this.claims] != null) {
                    setupAuthentication(claims) {
                        filterChain.doFilter(request, response)
                    }
                } else {
                    SecurityContextHolder.clearContext()
                    throw OjolException("Token Required")
                }
            }
        } catch (e: Exception) {
            val errorResponse = BaseResponse<Empty>()
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = "application/json"
            when (e) {
                is UnsupportedJwtException -> {
                    errorResponse.message = "Error Unsupported"
                    val responseString = ObjectMapper()
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(errorResponse)
                    response.writer.println(responseString)
                }
                else -> {
                    errorResponse.message = e.message ?: "Token Invalid"
                    val responseString = ObjectMapper()
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(errorResponse)
                    response.writer.println(responseString)
                }
            }
        }
    }

    private fun validate(request: HttpServletRequest): Claims {
        val secret = System.getenv("SECRET")
        val jwtToken = request.getHeader("Authorization")
        return Jwts.parserBuilder()
            .setSigningKey(secret.toByteArray())
            .build()
            .parseClaimsJws(jwtToken)
            .body
    }

    private fun setupAuthentication(claims: Claims, doOnNext: () -> Unit) {
        val authorities = claims[this.claims] as List<String>
        val authStream = authorities.stream().map {
            SimpleGrantedAuthority(it)
        }.collect(Collectors.toList())

        val auth = UsernamePasswordAuthenticationToken(claims.subject, null, authStream)
        SecurityContextHolder.getContext().authentication = auth
        doOnNext.invoke()
    }
}