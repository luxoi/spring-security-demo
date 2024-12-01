package com.example.demo_jwt.User;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements UserDetails{
    //anotaciones
    @Id
    @GeneratedValue
    Integer id;

    @Column(nullable = false)
    String username;
    String password;
    String firstname;
    String lastname;
    String country;
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of(new SimpleGrantedAuthority((role.name())));
    }

    public boolean isAccountNonExpired() {
		return true;
	}

    public boolean isAccountNonLocked() {
		return true;
	}

    public boolean isCredentialsNonExpired() {
		return true;
	}

    public boolean isEnabled() {
		return true;
	}


}
