package tech.suji.seven_prods.domain;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class AuditUtil {
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private OffsetDateTime dateCreated;

	@LastModifiedDate
	@Column(nullable = false)
	private OffsetDateTime lastUpdated;
}
