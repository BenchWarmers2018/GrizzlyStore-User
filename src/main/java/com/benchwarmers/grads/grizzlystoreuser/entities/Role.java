package com.benchwarmers.grads.grizzlystoreuser.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName name;

    public Role() {

    }

    public Role(RoleName name) {
        this.name = name;
    }

//    public Long getId() {
//        return id;
//    }

//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    @Type(type="uuid-char")
//    //@Column(name = "id_Account", updatable = false, nullable = false)
//    private UUID id;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }

}
