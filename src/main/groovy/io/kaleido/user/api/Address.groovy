package io.kaleido.user.api

import org.joda.time.LocalDateTime

class Address {
    Long addressId
    String address1
    String address2
    String address3
    String city
    String state
    String postalCode
    String countryCode
    LocalDateTime createdDate
    LocalDateTime lastUpdateDate
}
