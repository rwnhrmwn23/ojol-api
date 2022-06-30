package com.onedev.ojolapi.authentication

import com.onedev.ojolapi.core.entity.Register
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest

@EnableWebSecurity
@Configuration
class JwtConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var authenticationFilter: AuthenticationFilter

    override fun configure(http: HttpSecurity) {
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf()
            .disable()
            .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, *postPermit.toTypedArray()).permitAll()
            .antMatchers(HttpMethod.GET, *getPermit.toTypedArray()).permitAll()
            .anyRequest()
            .authenticated()
    }

    companion object {
        val postPermit = listOf(
            "/api/user/login",
            "/api/user/register"
        )

        val getPermit = listOf(
            "/api/ping"
        )

        fun generateToken(user: Register.User) : String {
            val claims = System.getenv("CLAIMS")
            val secret = System.getenv("SECRET")
            val subject = user.id
            val expired = Date(System.currentTimeMillis() + 60_000 * 60 * 24)
            val granted = AuthorityUtils.commaSeparatedStringToAuthorityList(user.username)
            val grantedStream = granted.stream().map {
                it.authority
            }.collect(Collectors.toList())

            return Jwts.builder()
                .setSubject(subject)
                .claim(claims, grantedStream)
                .setExpiration(expired)
                .signWith(Keys.hmacShaKeyFor(secret.toByteArray()), SignatureAlgorithm.HS256)
                .compact()
        }

        fun isPermit(request: HttpServletRequest): Boolean {
            val path = request.servletPath
            return postPermit.contains(path) or getPermit.contains(path)
        }
    }
}