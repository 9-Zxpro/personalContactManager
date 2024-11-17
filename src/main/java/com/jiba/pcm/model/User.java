package com.jiba.pcm.model;

import com.jiba.pcm.enums.Provider;
import com.jiba.pcm.enums.Roles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    private String userId;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String phoneNumber;
    private String about;

    @Builder.Default
    private boolean enabled = false;
    @Builder.Default
    private boolean emailVerified = false;
    private String emailToken;
    
    @Builder.Default
    private boolean termsAndPrivacy = false;

    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private Provider provider = Provider.SELF;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<Roles> role = new HashSet<>();

    private String providerId;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Image profileImg;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts;

}
