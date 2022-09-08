package com.vmodev.tuanva.notes.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.vmodev.tuanva.notes.dto.group.NoteCreateGroup;
import com.vmodev.tuanva.notes.dto.group.NoteUpdateGroup;
import com.vmodev.tuanva.notes.model.ImageNote;
import com.vmodev.tuanva.notes.model.MultipleChoiceNote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequestDTO {
	@NotNull(groups = NoteUpdateGroup.class)
	private Long id;
	@NotNull(groups = { NoteUpdateGroup.class, NoteCreateGroup.class })
	private String title;
	@NotNull(groups = { NoteUpdateGroup.class, NoteCreateGroup.class })
	private String type;
	private String desc;
	private List<ImageNote> imageNotes;
	private List<MultipleChoiceNote> multipleChoiceNotes;
}
