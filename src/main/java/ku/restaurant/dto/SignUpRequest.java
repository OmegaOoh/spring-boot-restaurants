package ku.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotBlank(message = "Username is mandatory")
    @Size(min=4, message="Username must be at least 4 characters long")
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Size(min=8, message="Password must be at least 8 characters long")
    private String password;
    @NotBlank(message = "Name is mandatory")
    private String name;
}