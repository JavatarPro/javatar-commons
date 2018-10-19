/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.commons.reader;

import java.time.LocalDateTime;

/**
 * @author Andrii Murashkin / Javatar LLC
 * @version 06-09-2018
 */
public class User {
    private String name;
    private String surname;
    private int age;
    private LocalDateTime registrationDate;

    public User() {
    }

    public User(String name, String surname, int age, LocalDateTime registrationDate) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.registrationDate = registrationDate;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        if (age != user.age)
            return false;
        if (name != null ? !name.equals(user.name) : user.name != null)
            return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null)
            return false;
        return registrationDate != null ?
                registrationDate.equals(user.registrationDate) :
                user.registrationDate == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        return result;
    }
}
