package ku.restaurant.entity;

import java.util.UUID;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import ku.restaurant.config.AttributeEncryptor;
import lombok.Data;

@Data
@Entity
@Table(name="user_info")
public class User {
    @Id
    @GeneratedValue
    private UUID id;
    
    @Column(unique = true)
    private String username;
    
    private String password;
    
    @Convert(converter = AttributeEncryptor.class)
    private String name;
    private String role;
    private Instant createdAt;
}