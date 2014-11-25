package io.kaleido.user.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.CompileStatic
import io.kaleido.user.api.RoleType
import org.hibernate.validator.constraints.NotBlank
import org.joda.time.LocalDateTime

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Transient
import javax.validation.constraints.NotNull


@CompileStatic
@Entity
@Table(name = 'user')
class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'user_id')
    Long userId

    @NotBlank
    @Column(name = 'user_guid', nullable = false, unique = true)
    String userGuid

    @NotBlank
    @Column(name = 'first_name', nullable = false)
    String firstName

    @NotBlank
    @Column(name = 'last_name', nullable = false)
    String lastName

    @NotBlank
    @Column(name = 'email', nullable = false, unique = true)
    String email

    @NotNull
    @Column(name = 'birth_date', nullable = false)
    LocalDateTime birthDate

    @NotBlank
    @Column(name = 'password', nullable = false)
    String password

    @NotNull
    @Column(name = 'created_date', nullable = false)
    LocalDateTime createdDate

    @NotNull
    @Column(name = 'last_update_date', nullable = false)
    LocalDateTime lastUpdateDate

    @JsonIgnore
    @NotNull
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = 'user')
    Set<UserRoleEntity> userRoles

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = 'user_address',
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    List<AddressEntity> addresses

    @Transient
    Set<RoleType> getRoles() {
        if (userRoles) {
            Set<RoleType> roles = []
            for (UserRoleEntity relation in userRoles) {
                roles.add(RoleType.create(relation.role?.roleName))
            }
            return roles
        }
        return [] as Set
    }
}
