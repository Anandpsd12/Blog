package com.blog.entities;

import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String username;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",                                        // 3Rd table created automatically
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName="id"), //  between parent table & 3rd table
            inverseJoinColumns = @JoinColumn(name = "role_id",  // child table & 3RD table
                    referencedColumnName = "id"))
    private Set<Role> roles;
}