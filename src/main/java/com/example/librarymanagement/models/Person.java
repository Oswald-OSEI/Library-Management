package com.example.librarymanagement;

public abstract class Person {
    protected int personId;
    protected String name;
    protected String email;
    protected String telNumber;
    protected String password;

    public Person( String name, String email, String telNumber, String password) {
        this.name = name;
        this.email = email;
        this.telNumber = telNumber;
        this.password = password;
    }

    // Getters and Setters for the attributes
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
