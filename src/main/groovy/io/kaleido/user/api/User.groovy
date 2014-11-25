package io.kaleido.user.api

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.joda.time.LocalDateTime

class User {

    Long userId
    String userGuid
    String firstName
    String lastName
    String email
    LocalDateTime birthDate

    @JsonIgnore
    String password

    LocalDateTime createdDate
    LocalDateTime lastUpdateDate
    List<RoleType> roles
    List<Address> addresses

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

}
