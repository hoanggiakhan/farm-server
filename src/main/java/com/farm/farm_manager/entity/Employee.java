package com.farm.farm_manager.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int employeeId;

    String fullName;
    String username;
    String password;
    String address;
    String phoneNumber;
    int age;
    LocalDate joinDate;
    String email;
    double salary;
    String role;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    Farm farm;

    @OneToMany(mappedBy = "employee")
    List<Task> tasks;

    @OneToMany(mappedBy = "employee")
    List<Notifications> notifications;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    List<Attendance> attendances;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }
}
