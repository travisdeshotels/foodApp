package tk.codedojo.food.beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tk.codedojo.food.beans.security.Role;

import java.util.Collection;
import java.util.List;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer implements UserDetails {
    @Id
    private String id;
    private String lastName;
    private String firstName;
    @NonNull
    private String userName;
    @NonNull
    private String password;
    @NonNull
    private String email;
    private Role role;
    private String restaurantId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    public @NonNull String getUserName(){
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
