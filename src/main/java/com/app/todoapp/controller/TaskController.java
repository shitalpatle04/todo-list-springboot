package com.app.todoapp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.app.todoapp.models.Task;
import com.app.todoapp.services.TaskService;

@Controller 
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("/")
    public String home() {
        return "index";  
    }

    @GetMapping("/task")
    public String getTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks"; 
    }

    @PostMapping("/todo")
    public String createTask(@RequestParam String title, @RequestParam String dueDate) {
        taskService.createTask(title, LocalDate.parse(dueDate));
        return "redirect:/task";
    }

    @GetMapping("/task/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/task"; 
    }

    @GetMapping("/task/{id}/toggle")
    public String toggleTask(@PathVariable Long id) {
        taskService.toggleTask(id);
        return "redirect:/task"; 
    }

    @GetMapping("/task/mark-all")
    public String markAllCompleted() {
        taskService.markAllCompleted();
        return "redirect:/task";
    }

    @GetMapping("/task/clear-completed")
    public String clearCompletedTasks() {
        taskService.clearCompletedTasks();
        return "redirect:/task";
    }

    @PostMapping("/task/{id}/edit")
    public String editTask(@PathVariable Long id, @RequestParam String newTitle) {
        taskService.updateTask(id, newTitle);
        return "redirect:/task";
    }

    @GetMapping("/task/pending")
    public String getPendingTasks(Model model) {
        model.addAttribute("tasks", taskService.getPendingTasks());
        return "tasks";
    }

    @GetMapping("/task/completed")
    public String getCompletedTasks(Model model) {
        model.addAttribute("tasks", taskService.getCompletedTasks());
        return "tasks";
    }
}
