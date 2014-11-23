package io.kaleido.user.entity

import groovy.transform.CompileStatic
import org.hibernate.validator.constraints.NotBlank
import org.joda.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotNull


@CompileStatic
@Entity
@Table(name = 'user')
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'user_id')
    Long userId

    @NotBlank
    @Column(name = 'user_guid', nullable = false, unique = true)
    String userGuid

    @NotBlank
    @Column(name = 'first_name', nullable = false, unique = true)
    String firstName

    @NotBlank
    @Column(name = 'last_name', nullable = false, unique = true)
    String lastName

    @NotBlank
    @Column(name = 'email', nullable = false, unique = true)
    String email

    @NotNull
    @Column(name = 'birth_date', nullable = false, unique = true)
    LocalDateTime birthDate

}
