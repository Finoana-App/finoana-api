package com.anonymous.finoanaapi.models;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.anonymous.finoanaapi.models.enums.UserRole;
import com.anonymous.finoanaapi.models.enums.UserSex;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
public class User {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private String id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(nullable = false, unique = true)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserSex sex;

  @Column(name = "birth_date", nullable = false)
  private LocalDate birthDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole role;
}
