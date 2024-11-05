package com.nurfad.jpaexercise.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLRestriction;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_details")
@SQLRestriction("deleted_at IS NULL")
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_details_id_gen")
    @SequenceGenerator(name = "user_details_id_gen", sequenceName = "user_details_id_seq", allocationSize = 1)
    @Column(nullable = false)
    private Long id;

    @Column(name = "name", length = 50)
    @Size(max = 50)
    private String name;

    @Size(max = 100)
    @Column(name = "profile_picture_url", length = 100)
    private String profilePictureUrl;

    @Column(name = "quotes")
    private String quotes;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "user_details_user_id_fkey"))
    private User user;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    @PreRemove
    protected void onRemove() {
        deletedAt = OffsetDateTime.now();
    }
}
