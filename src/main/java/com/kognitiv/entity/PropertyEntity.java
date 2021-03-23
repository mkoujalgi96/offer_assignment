package com.kognitiv.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "property")
@Getter
@Setter
public class PropertyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "property_code")
	private String propertyCode;

	@Column(name = "images")
	private String images;

	@Column(name = "languages")
	private String languages;

	@Column(name = "description", length = 65530)
	private String description;

	@Column(name = "city")
	private String city;

	@Column(name = "country")
	private String country;

	@Column(name = "lattitude")
	private double lattitude;

	@Column(name = "longitude")
	private double longitude;

	// Trust you
	@Column(name = "trust_you_name")
	private String trustYouName;

	@Column(name = "trust_you_score")
	private String trustYouScore;

	@Column(name = "trust_you_score_description")
	private String trustYouScoreDescription;

	@Column(name = "trust_you_sources_count")
	private long trustYouSourceCount;

	@Column(name = "trust_you_reviewsCount")
	private long trustYouReviewsCount;

	@OneToMany
	private List<PropertyEntity> alternateProperties;

}
