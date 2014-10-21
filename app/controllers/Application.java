package controllers;

import models.Task;
import play.*;
import play.mvc.*;
import views.html.*;
import play.data.*;
import static play.data.Form.form;

public class Application extends Controller {

static Form<Task> taskForm = form(Task.class);
  
  public static Result index() {
    return redirect(routes.Application.tasks());
  }
  
  public static Result tasks() {
    return ok(
      views.html.index.render(Task.all(), taskForm)
    );
  }
  
  public static Result newTask() {
    Form<Task> filledForm = taskForm.bindFromRequest();
    if(filledForm.hasErrors()) {
      return badRequest(
        views.html.index.render(Task.all(), filledForm)
      );
    } else {
      Task.create(filledForm.get());
      return redirect(routes.Application.tasks());  
    }
  }
  
  public static Result deleteTask(Long id) {
    Task.delete(id);
    return redirect(routes.Application.tasks());
  }
	
  public static Result editTaskForm(Long id) {
	Task t  = Task.find.ref(id);
	if (t==null) {
		return notFound();
	}
	Form<Task> filledForm = taskForm.fill(t);
	return ok(
		views.html.editpage.render(Task.all(), filledForm)
	);
  }
  
  public static Result editTask(Long id) {
	Form<Task> filledForm = taskForm.bindFromRequest();
    if(filledForm.hasErrors()) {
      return badRequest(
        views.html.editpage.render(Task.all(), filledForm)
      );
    } else {
      Task.edit(filledForm.get());
      return redirect(routes.Application.tasks());  
    }
  }
  
}
