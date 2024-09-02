package es.diplock.examples.entities.audit;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@JsonIgnoreProperties(
		value = { "created_date", "updated_date" },
		allowGetters = true
)
public abstract class DateAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
	
    @PrePersist
    public void prePersist() {
		createdDate = LocalDateTime.now();
		updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
    	updatedDate = LocalDateTime.now();
    }

}
