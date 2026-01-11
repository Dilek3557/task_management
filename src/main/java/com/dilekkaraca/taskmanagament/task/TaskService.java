package com.dilekkaraca.taskmanagament.task;

import com.dilekkaraca.taskmanagament.task.dto.TaskCreateRequest;
import com.dilekkaraca.taskmanagament.task.dto.TaskResponse;
import com.dilekkaraca.taskmanagament.task.dto.TaskStatusUpdateRequest;
import com.dilekkaraca.taskmanagament.task.dto.TaskUpdateRequest;
import com.dilekkaraca.taskmanagament.user.User;
import com.dilekkaraca.taskmanagament.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // ✅ Ortak: token’dan login olan user’ı bul
    private User currentUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User bulunamadı: " + email));
    }

    // ✅ POST /tasks (Task oluşturma)
    public void create(TaskCreateRequest request, Authentication authentication) {
        User user = currentUser(authentication);

        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .owner(user)
                .status(TaskStatus.TODO)
                .build();

        taskRepository.save(task);
    }

    // ✅ GET /tasks (Sadece kendi tasklarım)
    public List<TaskResponse> getMyTasks(Authentication authentication) {
        User me = currentUser(authentication);

        return taskRepository.findByOwner(me).stream()
                .map(t -> new TaskResponse(t.getId(), t.getTitle(), t.getDescription(),t.getStatus()))
                .toList();
    }

    // ✅ PUT /tasks/{id} (Sadece kendi taskımı güncellerim)
    public TaskResponse updateMyTask(Long id, TaskUpdateRequest request, Authentication authentication) {
        User me = currentUser(authentication);

        Task task = taskRepository.findByIdAndOwner(id, me)
                .orElseThrow(() -> new RuntimeException("Task bulunamadı veya sana ait değil"));

        task.setTitle(request.title());
        task.setDescription(request.description());

        Task saved = taskRepository.save(task);
        return new TaskResponse(saved.getId(), saved.getTitle(), saved.getDescription(), saved.getStatus());
    }

    // ✅ DELETE /tasks/{id} (Sadece kendi taskımı silerim)
    public void deleteMyTask(Long id, Authentication authentication) {
        User me = currentUser(authentication);

        Task task = taskRepository.findByIdAndOwner(id, me)
                .orElseThrow(() -> new RuntimeException("Task bulunamadı veya sana ait değil"));

        taskRepository.delete(task);
    }
    public TaskResponse updateStatus(Long id,
                                     TaskStatusUpdateRequest request,
                                     Authentication authentication) {

        User me = currentUser(authentication);

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Task task;

        if (isAdmin) {
            task = taskRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Task bulunamadı"));
        } else {
            task = taskRepository.findByIdAndOwner(id, me)
                    .orElseThrow(() -> new RuntimeException("Task bulunamadı veya sana ait değil"));
        }

        task.setStatus(request.status());

        Task saved = taskRepository.save(task);

        return new TaskResponse(saved.getId(), saved.getTitle(), saved.getDescription(), saved.getStatus());
    }



}
