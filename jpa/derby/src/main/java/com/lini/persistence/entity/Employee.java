package com.lini.persistence.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** JavaDB
 CREATE TABLE employee (
	id 		      INT NOT NULL GENERATED ALWAYS AS IDENTITY,
	first_name 	VARCHAR(32) NOT NULL, 
	last_name 	VARCHAR(32) NOT NULL, 
	birthday 	  DATE NOT NULL
 );
 */

@Entity
@Table(name = "employee")
public class Employee {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;
  @Column(length = 32)
  private String first_name;
  @Column(length = 32)
  private String last_name;  
  @Column
  private Date birthday;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

}