package com.rayfay.bizcloud.microservices.poc.entity;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "T_car_box")
public class T_car_box {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String car;
	private String box;
}
