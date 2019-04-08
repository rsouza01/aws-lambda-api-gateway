package com.rsouza01.mvc;

public class PersonRequest {

    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String address;


    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
