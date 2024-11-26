package tech.suji.seven_prods.projects.cats.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.suji.seven_prods.domain.AuditUtil;


@Entity
@Getter
@Setter
@Table(name = "cats")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Cat extends AuditUtil{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	private String name;
}
