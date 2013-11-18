/*
 * Copyright 2011 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.sample.mobilewebapp.server.domain;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A task used in the task list. This is a monolothic implementation of a data
 * object for use with {@code RequestFactory}. Better patterns make use of
 * Locators and ServiceLocators to simplify the boilerplate required to expose a
 * data object.
 * <p>
 * See <a href=
 * 'http://turbomanage.wordpress.com/2011/03/25/using-gwt-requestfactory-with-ob
 * j e c t i f y / ' >this fine blog post</a>, for an example.
 */
@Entity
public class Task {

  private static long idCounter = 0;
  private static final Map<String, Map<Long, Task>> taskListByUser =
      new HashMap<String, Map<Long, Task>>();

  /**
   * Find all tasks for the current user.
   */
  @SuppressWarnings("unchecked")
  public static List<Task> findAllTasks() {

    String currentUserId = getCurrentUserID();

    List<Task> list = new ArrayList<Task>(taskListByUser.get(currentUserId).values());
    if (list.size() == 0) {
      populateDatastore();
      list = new ArrayList<Task>(taskListByUser.get(currentUserId).values());
    }

    return list;
  }

  private static String getCurrentUserID() {
    HttpServletRequest request = RequestFactoryServlet.getThreadLocalRequest();
    HttpSession session = request.getSession();

    String userId = (String) session.getAttribute("userId");
    if (userId != null) {
      return userId;
    }
    return null;
  }

  /**
   * Find a {@link Task} by id for the current user.
   * 
   * @param id the {@link Task} id
   * @return the associated {@link Task}, or null if not found
   */
  public static Task findTask(Long id) {
    String currentUserId = getCurrentUserID();

    if (id == null || !taskListByUser.containsKey(currentUserId)) {
      return null;
    }

    return taskListByUser.get(currentUserId).get(id);
  }

  /**
   * Populate the datastore with some default tasks. We do this to make the app
   * more intuitive on first use.
   * 
   * @param currentUserId
   */
  @SuppressWarnings("deprecation")
  private static void populateDatastore() {
    String currentUserId = getCurrentUserID();

    HashMap<Long, Task> list = new HashMap<Long, Task>();
    {
      // Task 0.
      Task task0 = new Task();
      task0.id = nextId();
      task0.setName("Beat Angry Birds");
      task0.setNotes("This game is impossible!");
      task0.setDueDate(new Date(100, 4, 20));
      task0.userId = currentUserId;
      list.put(task0.id, task0);
    }
    {
      // Task 1.
      Task task1 = new Task();
      task1.id = nextId();
      task1.setName("Make a million dollars");
      task1.setNotes("Then spend it all on Android apps");
      task1.userId = currentUserId;
      list.put(task1.id, task1);
    }
    {
      // Task 2.
      Task task2 = new Task();
      task2.id = nextId();
      task2.setName("Buy a dozen eggs");
      task2.setNotes("of the chicken variety");
      task2.userId = currentUserId;
      list.put(task2.id, task2);
    }
    {
      // Task 3.
      Task task3 = new Task();
      task3.id = nextId();
      task3.setName("Complete all tasks");
      task3.userId = currentUserId;
      list.put(task3.id, task3);
    }
    taskListByUser.put(currentUserId, list);
  }

  private static Long nextId() {
    return ++idCounter;
  }

  @Id
  Long id;

  private Date dueDate;

  @NotNull(message = "You must specify a name")
  @Size(min = 3, message = "Name must be at least 3 characters long")
  private String name;

  private String notes;

  /**
   * The unique ID of the user who owns this task.
   */
  private String userId;

  // TODO: Move this field to a superclass that implements a persistence layer
  private Integer version = 0;

  /**
   * Get the due date of the Task.
   */
  public Date getDueDate() {
    return dueDate;
  }

  /**
   * Get the unique ID of the Task.
   */
  public Long getId() {
    return id;
  }

  /**
   * Get the name of the Task.
   */
  public String getName() {
    return name;
  }

  /**
   * Get the notes associated with the task.
   */
  public String getNotes() {
    return notes;
  }

  /**
   * Get the version of this datastore object.
   */
  public Integer getVersion() {
    // TODO: Move this method to a superclass that implements a persistence
    // layer
    return version;
  }

  /**
   * Persist this object in the data store.
   */
  public void persist() {
    String currentUserId = getCurrentUserID();
    if (!taskListByUser.containsKey(currentUserId)) {
      taskListByUser.put(currentUserId, new HashMap<Long, Task>());
    }
    onPersist();
    taskListByUser.get(currentUserId).put(id, this);
  }

  /**
   * Remove this object from the data store.
   */
  public void remove() {
    String currentUserId = getCurrentUserID();

    if (taskListByUser.containsKey(currentUserId)) {
      taskListByUser.remove(currentUserId);
    }
  }

  /**
   * Set the due date of the task.
   * 
   * @param dueDate the due date, or null if no due date
   */
  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Set the name of the task.
   * 
   * @param name the task name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set the notes associated with the task.
   * 
   * @param notes the notes
   */
  public void setNotes(String notes) {
    this.notes = notes;
  }

  void onPersist() {
    if (id == null) {
      id = nextId();
    }
    ++this.version;
  }
}
