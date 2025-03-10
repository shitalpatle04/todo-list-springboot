package com.app.todoapp.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.todoapp.models.Task;
import com.app.todoapp.repository.TaskRepository;

@Service
public class TaskService {
	
	private final TaskRepository taskRepository;
	 
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	public List<Task> getAllTasks(){
		return taskRepository.findAll();
		
	}

	public void createTask(String title, LocalDate dueDate) {
	    Task task = new Task();
	    task.setTitle(title);
	    task.setCompleted(false);
	    task.setDueDate(dueDate);
	    taskRepository.save(task);
	}


	public void deleteTask(Long id) {
		taskRepository.deleteById(id);
	}

	public void toggleTask(Long id) {
		Task task = taskRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid task id"));
		
		task.setCompleted(!task.isCompleted());
		taskRepository.save(task);
	}
	
	public void markAllCompleted() {
	    List<Task> tasks = taskRepository.findAll();
	    for (Task task : tasks) {
	        task.setCompleted(true);
	    }
	    taskRepository.saveAll(tasks);
	}

	public void clearCompletedTasks() {
	    List<Task> tasks = taskRepository.findAll();
	    tasks.removeIf(Task::isCompleted);
	    taskRepository.deleteAll(tasks);
	}

	public void updateTask(Long id, String newTitle) {
	    Task task = taskRepository.findById(id)
	        .orElseThrow(() -> new IllegalArgumentException("Invalid task ID"));
	    task.setTitle(newTitle);
	    taskRepository.save(task);
	}


	public List<Task> getPendingTasks() {
	    return taskRepository.findAll().stream()
	        .filter(task -> !task.isCompleted())
	        .collect(Collectors.toList());
	}

	public List<Task> getCompletedTasks() {
	    return taskRepository.findAll().stream()
	        .filter(Task::isCompleted)
	        .collect(Collectors.toList());
	}

}
