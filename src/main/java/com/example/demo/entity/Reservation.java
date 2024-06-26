package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "reservation")
@Data
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reservation_id")
	private Integer reservationId;

	@Column(name = "lend_item_id")
	private Integer LendItemId;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "reservation_date")
	@Transient private Date reservationDate;

	public Reservation() {
	}

	public Reservation(Integer lendItemId, Integer userId) {
		super();
		LendItemId = lendItemId;
		this.userId = userId;
	}

}
