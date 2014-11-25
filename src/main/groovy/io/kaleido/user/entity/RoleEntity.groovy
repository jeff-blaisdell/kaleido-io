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
@Table(name = 'role')
class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'role_id', nullable = false, unique = true)
    Long roleId

    @NotBlank
    @Column(name = 'role_name', nullable = false, unique = true)
    String roleName

    @NotBlank
    @Column(name = 'role_description', nullable = true)
    String roleDescription

    @NotNull
    @Column(name = 'created_date', nullable = false)
    LocalDateTime createdDate

    @NotNull
    @Column(name = 'last_update_date', nullable = false)
    LocalDateTime lastUpdateDate

}
