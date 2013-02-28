package com.roscopeco.ormsample.model;

import java.util.List;

import com.roscopeco.ormdroid.Entity;

import static com.roscopeco.ormdroid.Query.eql;

public class Department extends Entity {
  public int id;
  public String name;
  
  public Department() {
    this(null);
  }
  
  public Department(String name) {
    this.name = name;
  }
  
  public List<Person> people() {
    return query(Person.class).where(eql("department", id)).executeMulti();
  }
  
  public String toString() {
    return name;
  }
}
