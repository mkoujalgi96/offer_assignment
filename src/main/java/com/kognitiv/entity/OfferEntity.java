package com.kognitiv.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "offer")
@Data
public class OfferEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "validFrom")
	private Date validFrom;

	@Column(name = "validTill")
	private Date validTill;

	@Column(name = "location")
	private String location;

	@Column(name = "images")
	private String images;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "properyId", referencedColumnName = "id")
	private PropertyEntity property;

}
