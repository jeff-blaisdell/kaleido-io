package io.kaleido.user.entity

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@CompileStatic
@Entity
@Table(name = 'user_role')
class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'user_role_id')
    Long userRoleId

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = 'user_id')
    UserEntity user

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = 'role_id')
    RoleEntity role

}
