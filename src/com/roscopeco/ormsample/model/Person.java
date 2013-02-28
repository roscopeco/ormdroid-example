package com.roscopeco.ormsample.model;

import com.roscopeco.ormdroid.Entity;

public class Person extends Entity {
  public int id;
  public String name;
  public String telephone;
  public Department department;
  
  public Person() {
    this(null, null, null);
  }
  
  public Person(String name, String telephone) {
    this(name, telephone, null);    
  }
  
  public Person(String name, String telephone, Department department) {
    this.name = name;
    this.telephone = telephone;
    this.department = department;
  }
  
  public String toString() {
    return name + " - " + telephone;
  }
}
