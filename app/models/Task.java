package models;

import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;


@Entity
public class Task extends Model {

  @Id
  public Long id;
  
  @Required
  public String name;
  
  @Required
  public String h_number;
  
  @Required
  public String m_number;
  
  public static Finder<Long,Task> find = new Finder(
    Long.class, Task.class
  );
  
  public static List<Task> all() {
    return find.all();
  }
  
  public static void create(Task task) {
    task.save();
  }
  
  public static void delete(Long id) {
    find.ref(id).delete();
  }
  
  public static void edit(Task task) {
    task.update();
  }  
}