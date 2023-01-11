package evon.tech.learning.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails{
    @Id
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name="user_email", unique =true)
    private String email;

    @Column(name= "user_password", length=500)
    private String password;

    @Column
    private String gender;

    @Column(length=1000)
    private String about;

    @Column(name="user_image_name")
    private String imageName;

    @OneToMany(mappedBy ="user",fetch =FetchType.LAZY,cascade =CascadeType.REMOVE)
    private List<Order> order = new ArrayList<>();

    //must have to implement
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       
        return null;
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }

    @Override
    public String getUsername() {
        
        return this.email;
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