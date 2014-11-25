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
@Table(name = 'user_address')
class UserAddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'user_address_id', nullable = false, unique = true)
    Long userAddressId

    @NotBlank
    @Column(name = 'user_id', nullable = false)
    String userId

    @NotBlank
    @Column(name = 'address_id', nullable = false)
    String addressId

    @NotNull
    @Column(name = 'created_date', nullable = false)
    LocalDateTime createdDate

    @NotNull
    @Column(name = 'last_update_date', nullable = false)
    LocalDateTime lastUpdateDate
}
