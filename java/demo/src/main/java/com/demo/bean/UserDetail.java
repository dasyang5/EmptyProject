package com.demo.bean;

import com.demo.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Alex
 * @date 2021/9/18 9:39
 */
@Getter
@Setter
public class UserDetail implements UserDetails {

    private User user;

    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null || roles.isEmpty()) {
            return new ArrayList<>();
        }

        List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
