package tech.suji.seven_prods.projects.empmgnt.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Branch {
	
	
	private Integer code;
	private String name;
	private String city;
	private String state;
	
	/* @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "employee_id", nullable = false)
	   Employee emp;*/
	
	public Branch(Integer code, String name, String city, String state) {
		super();
		this.code = code;
		this.name = name;
		this.city = city;
		this.state = state;
	}
}

