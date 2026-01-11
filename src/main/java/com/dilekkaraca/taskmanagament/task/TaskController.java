package com.dilekkaraca.taskmanagament.task;

import com.dilekkaraca.taskmanagament.task.dto.TaskCreateRequest;
import com.dilekkaraca.taskmanagament.task.dto.TaskResponse;
import com.dilekkaraca.taskmanagament.task.dto.TaskStatusUpdateRequest;
import com.dilekkaraca.taskmanagament.task.dto.TaskUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // ✅ CREATE (kendi taskımı oluştururum)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody TaskCreateRequest request,
                       Authentication authentication) {
        taskService.create(request, authentication);
    }

    // ✅ READ (sadece kendi tasklarım)
    @GetMapping
    public List<TaskResponse> myTasks(Authentication authentication) {
        return taskService.getMyTasks(authentication);
    }

    // ✅ UPDATE (sadece kendi taskımı güncellerim)
    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id,
                               @Valid @RequestBody TaskUpdateRequest request,
                               Authentication authentication) {
        return taskService.updateMyTask(id, request, authentication);
    }

    // ✅ DELETE (sadece kendi taskımı silerim)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, Authentication authentication) {
        taskService.deleteMyTask(id, authentication);
    }

    @PatchMapping("/{id}/status")
    public TaskResponse updateStatus(@PathVariable Long id,
                                     @Valid @RequestBody TaskStatusUpdateRequest request,
                                     Authentication authentication) {

        return taskService.updateStatus(id, request, authentication);
    }


}
