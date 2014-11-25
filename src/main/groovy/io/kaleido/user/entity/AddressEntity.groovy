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
@Table(name = 'address')
class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'address_id', nullable = false, unique = true)
    Long addressId

    @Column(name = 'address1', nullable = true)
    String address1

    @Column(name = 'address2', nullable = true)
    String address2

    @Column(name = 'address3', nullable = true)
    String address3

    @NotBlank
    @Column(name = 'city', nullable = true)
    String city

    @NotBlank
    @Column(name = 'state', nullable = true)
    String state

    @Column(name = 'postal_code', nullable = true)
    String postalCode

    @NotBlank
    @Column(name = 'country_code', nullable = true)
    String countryCode

    @NotNull
    @Column(name = 'created_date', nullable = false)
    LocalDateTime createdDate

    @NotNull
    @Column(name = 'last_update_date', nullable = false)
    LocalDateTime lastUpdateDate
}
