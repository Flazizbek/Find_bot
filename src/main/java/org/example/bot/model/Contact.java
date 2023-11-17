package org.example.bot.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class Contact {
private long id;
private String firstName;
private String lastName;
private String gender;
private String phoneNumber;
private String city;
private String birthDate;
}
