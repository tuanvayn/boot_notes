package com.vmodev.tuanva.notes.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Accessors(chain = true)
public class MultipleChoiceNote extends BaseNote {
	@Lob
	private String option;
	private Boolean done;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "note_id")
	@JsonBackReference
	private PlainNote note;
}
