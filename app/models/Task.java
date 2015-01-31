package models;

import java.util.*;
import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;


@Entity
public class Task extends Model {

  public String groupNumber;
  public String day;
  public String hours;
  public String lection;
  public String teacher;
  public String room;
  
  public static Finder<String,Task> find = new Finder(
    String.class, Task.class
  );
  
  public static List<Task> all() {
    return find.all();
  }


//пусть сеттеры-геттеры останутся на всякий случай
//
//  public void setId(Long id) {
//	this.id = id;
//  }
//
//  public Long getId() {
//    return id;
//  }
//
//  public void setName(String name) {
//	  this.name = name;
//  }
//  public String getName() {
//    return name;
//  }

//  public void setHNumber(String number) {
//	  this.hNumber = number;
//  }
//  public String getHNumber() {
//    return hNumber;
//  }
//
//  public void setMNumber(String number) {
//	  this.mNumber = number;
//  }
//  public String getMNumber() {
//    return mNumber;
//  }
}