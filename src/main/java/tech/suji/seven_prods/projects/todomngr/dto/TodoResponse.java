package tech.suji.seven_prods.projects.todomngr.dto;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoResponse {

	private Long id;

	private String title;

	private String description;

	private long timeLeft;
	
	private long timeReq;
	
	private Integer priority;

	private TodoStatusType status;

}
