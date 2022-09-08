package com.vmodev.tuanva.notes.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Accessors(chain = true)
public class PlainNote extends BaseNote {
	private String title;
	private String status;
	private String type;
	@CreatedBy
	private String createdBy;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;
	private String desc;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference
	private List<ImageNote> imageNotes = new ArrayList<>();

	public void setImageNotes(List<ImageNote> imageNotes) {
		if (!CollectionUtils.isEmpty(imageNotes)) {
			this.imageNotes.clear();
			for (ImageNote child : imageNotes) {
				child.setNote(this);
			}
			this.imageNotes.addAll(imageNotes);
		}
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference
	private List<MultipleChoiceNote> multipleChoiceNotes = new ArrayList<>();

	public void setMultipleChoiceNotes(List<ImageNote> multipleChoiceNotes) {
		if (!CollectionUtils.isEmpty(multipleChoiceNotes)) {
			this.multipleChoiceNotes.clear();
			for (ImageNote child : multipleChoiceNotes) {
				child.setNote(this);
			}
			this.imageNotes.addAll(multipleChoiceNotes);
		}
	}

}
