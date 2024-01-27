package com.example.isaprojekat.security.config;

import com.example.isaprojekat.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    /*@Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/registration/**")
                .permitAll()
                .anyRequest()
                .authenticated().and()
                .formLogin()
                .defaultSuccessUrl("http://localhost:4200/homepage", true);
    }*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/users/getUserByUsername/").permitAll()
                .antMatchers("/api/users/all").permitAll()
                .antMatchers("/api/companies/getById/**").permitAll()
                .antMatchers("/api/companies/all").permitAll()
                .antMatchers("/api/registration/").permitAll()
                .antMatchers("/api/equipment/getEquipmentForCompany/**").permitAll()
                .antMatchers("/api/equipment/getById/**").permitAll()
                .antMatchers("/api/equipment/update/**").permitAll()
                .antMatchers("/api/equipment/searchByName").permitAll() //search equipment controller
                .antMatchers("/api/equipment/all").permitAll()          //all equipment
                .antMatchers("/api/equipment/getAllEquipmentWithCompanies").permitAll()  //dodala
                .antMatchers("/api/companies/update/**").permitAll()
                .antMatchers("/api/companies/create").permitAll()
                .antMatchers("/api/users/getUserByUsername/**").permitAll()
                .antMatchers("/api/users/updateUser/**").permitAll()
                .antMatchers("/api/users/updateUsersPassword/**/**").permitAll() //Change password
                .antMatchers("/api/registration/**").permitAll()
                .antMatchers("/api/companies/search").permitAll()
                .antMatchers("/api/companyAdmins/createAdmins/**").permitAll() // copanyAdmin
                .antMatchers("/api/companyAdmins/getUsersNotInCompanyAdmin").permitAll() //companyAdmin
                .antMatchers("/api/appointments/getById/**").permitAll()
                .antMatchers("/api/appointments/all").permitAll()
                .antMatchers("/api/appointments/update").permitAll()
                .antMatchers("/api/appointments/create").permitAll()
                .antMatchers("/api/appointments/delete/**").permitAll()
                .antMatchers("/api/appointments/adminsAppointments/**").permitAll() 
                .antMatchers("/api/companies/getForAdmin/**").permitAll()
                .antMatchers("/api/item/create").permitAll()
                .antMatchers("/api/reservations/create").permitAll()
                .antMatchers("/api/appointments/availableDates").permitAll()
                .antMatchers("/api/reservations/byUser/**").permitAll()
                .antMatchers("/api/reservations/addReservationToItem/**").permitAll()
                .antMatchers("/api/reservations/cancel").permitAll()
                .antMatchers("/api/reservations/**").permitAll()
                .antMatchers("/api/reservations/getAdminsAppointmentReservation/**").permitAll() //DODALA
                .antMatchers("/api/item/byReservation/**").permitAll()
                .antMatchers("/api/companies/removeFrom/**/**").permitAll()
                .antMatchers("/api/companies/addTo/**/**").permitAll()
                .antMatchers("/api/companyAdmins/getCompanyForAdmin/**").permitAll() // copanyAdmin
                .antMatchers("/api/companyAdmins/getAdminsForCompany/**").permitAll()
                .antMatchers("/api/registration/registerSystemAdmin/**").permitAll()
                .antMatchers("/api/qr-code/readQrCodeImage").permitAll()
                .antMatchers("/api/appointments/companyAppointments/**").permitAll()
                .antMatchers("/api/item/update/**").permitAll()
                .antMatchers("/api/reservations/all").permitAll()
                .antMatchers("/api/appointments/addAdminToAppointment/**").permitAll()
                .antMatchers("/api/qr-code/generateQrCodeSendMail").permitAll()
                .antMatchers("/api/reservations/findReservationById/**").permitAll()
                .antMatchers("/api/reservations/expired").permitAll()
                .antMatchers("/api/reservations/takeOver").permitAll()
                .antMatchers("/api/appointments/companyAvailableAppointments/**").permitAll()
                .antMatchers("/api/qr-code/getQRCodeData/**").permitAll()
                .antMatchers("/api/users/resetPenaltyPoints").permitAll()
                .antMatchers("/api/reservations/getAllUsersReservations/**").permitAll()
                .antMatchers("/api/reservations/getAllTakenUsersReservations/**").permitAll()
                .antMatchers("/api/reservations/setPrice").permitAll()
                .antMatchers("/custom-api-docs-path/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .cors()
                .and()
                .csrf().disable();


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider
                = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }

    //

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

