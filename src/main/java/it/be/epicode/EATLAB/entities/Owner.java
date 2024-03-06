package it.be.epicode.EATLAB.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String avatar;
    private String business_name;
    private String cellphone;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Owner(String name, String surname, String email, String password, String avatar) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.role = Role.USER;
        this.type = Type.OWNER;
    }

}
