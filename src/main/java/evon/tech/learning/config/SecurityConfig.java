package evon.tech.learning.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig 
{
    @Autowired
    private UserDetailsService userDetailService; 

    // @Bean
    // public UserDetailsService userDetailsService()
    // {
    //     UserDetails normal =User.builder()
    //         .username("anant")
    //         .password(passwordEncoder().encode("anant"))
    //         .roles("NORMAL")
           
    //         .build();

    //     UserDetails admin =User.builder()    
    //          .username("anugrah")
    //          .password(passwordEncoder().encode("admin"))
    //          .roles("ADMIN")
            
    //          .build();
          // users create

         // InMemoryUserDetailsManager is implementation of userDetailService Interface

    //     return new InMemoryUserDetailsManager(normal,admin);

    // this bean is used as form based login customization
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.  authorizeHttpRequests(null)
              
            .formLogin()
              .loginPage("login.html")
              .loginProcessingUrl("/process-url")
              .defaultSuccessUrl("/dashboard")
              .failureUrl("error")
              .and()
              .logout()
              .logoutUrl("/logout"); 
        return http.build();
    }

    @Bean
     public DaoAuthenticationProvider authenticationProvider()
     {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
     }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();            
        
    }

}
