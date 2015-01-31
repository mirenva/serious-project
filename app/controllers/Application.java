package controllers;

import models.Task;
import play.*;
import play.mvc.*;
import views.html.*;
import play.data.*;
import java.io.FileInputStream;
import static play.data.Form.form;
import java.util.*;
import play.db.ebean.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressBase;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.*;


public class Application extends Controller {

  static Form<Task> taskForm = form(Task.class);
  
  public static Result index() {
    return redirect(routes.Application.tasks());
  }
  
  public static Result tasks() {
    return ok(
      views.html.index.render(Task.all(),taskForm)
    );
  }

  public static Result findTasks() {
    DynamicForm requestData = Form.form().bindFromRequest();
    List<Task> taskList = Task.find.where()
        .ilike("groupNumber", "%" + requestData.get("groupNumber") + "%")
        .ilike("day", "%" + requestData.get("day") + "%")
        .ilike("hours", "%" + requestData.get("hours") + "%")
        .ilike("lection", "%" + requestData.get("lection") + "%")
        .ilike("teacher", "%" + requestData.get("teacher") + "%")
        .ilike("room", "%" + requestData.get("room") + "%")
    .findList();
    return ok(
            views.html.index.render(taskList.all(),requestData)
    );
  }

  // разбираем файл Excel в БД
  public static void main(String[] args) throws Exception {

    org.apache.poi.poifs.filesystem.POIFSFileSystem fs =
            new org.apache.poi.poifs.filesystem.POIFSFileSystem(new FileInputStream("schedule.xls"));
    org.apache.poi.hssf.usermodel.HSSFWorkbook workbook =
            new org.apache.poi.hssf.usermodel.HSSFWorkbook(fs);
    org.apache.poi.hssf.usermodel.HSSFSheet sheet1 = workbook.getSheetAt(0);
    org.apache.poi.hssf.usermodel.HSSFRow row;
    org.apache.poi.hssf.usermodel.HSSFCell cell;

    String[][] data = new String[240][60];
    // разбираем файл Excel в массив
    for (int i = 0; i < 240; i++) {
      row = sheet1.getRow(i);
      if (row != null) {
        for (int j = 0; j < 60; j++) {
          cell = row.getCell(j);
          if (cell != null) {
            switch (cell.getCellType()) {
              case HSSFCell.CELL_TYPE_STRING:
                data[i][j] = cell.getStringCellValue();
                break;
            }
          }
        }
      }
    }
    // массив уже парсим в БД

    // ищем, где начинается расписание
    int z = 1;
    int x = 1;
    for (int i=0; i<100; i++) {
      if (data[x][1] = "Дни") {
        z = x;
        break;
      }
    } // z - строка, где начинается "чистое" расписание

    int y = 3;

    while (true) {
      String gN = data[x][y]; //

      while (true) {
        if (x > 200) break;

        x = x + 1;
        if (data[x][1] != null) {
          String d = data[x][1];
          while (true) {
            if (data[x][2] != null) {
              Task task = new Task();
              task.groupNumber = gN;
              task.day = d;
              task.hours = data[x][2];
              task.lection = data[x][y];
              task.teacher = data[x][y + 1];
              task.room = data[x][y + 2];
              task.save();
            }
            else {
              if (data[x][y] != null) {
                Task task = new Task();
                task.groupNumber = gN;
                task.day = d;
                task.hours = data[x][2];
                task.lection = data[x][y];
                task.teacher = data[x][y + 1];
                task.room = data[x][y + 2];
                task.save();
              }
            }
            if (data[x + 1][1] != null) {
              break;
            } else {
              x = x + 1;
              if (x > 200) {
                break;
              }
            }
          }


        }
      }
      y = y + 3;
      x = z;
      if (data[x][y] = "Часы") {
        break;
      }
    }
  }


  }
