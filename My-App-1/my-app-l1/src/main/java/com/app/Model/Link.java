package com.app.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "links")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {

    @Id
    @Column(name = "id")
    private UUID id; // Oracle RAW(16) mapped to UUID

    private String title;

    @Column(name = "parent_id")
    private UUID parentId;

    @Column(name = "is_admin_only")
    private Integer isAdminOnly;
    
    @Column(name = "IS_ACTIVE")
    private Integer isActive;
    
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
    
    @Transient
    private List<Link> children = new ArrayList<>();

}
