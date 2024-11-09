package com.jiba.pcm.config;

import com.jiba.pcm.model.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsImp implements UserDetails {
    private UUID id;
    private String username;
    private String password;

    private Collection<GrantedAuthority> authorities;

    public static UserDetailsImp buildUserDetails(User user) {
        List<GrantedAuthority> authorityCollection = user.getRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
        return new UserDetailsImp(
                UUID.fromString(user.getUserId()),
                user.getUsername(),
                user.getPassword(),
                authorityCollection
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
