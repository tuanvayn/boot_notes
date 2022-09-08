package com.vmodev.tuanva.notes.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.vmodev.tuanva.notes.dto.group.Create;
import com.vmodev.tuanva.notes.dto.group.Update;
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
	@NotNull(groups = Update.class)
	private Long id;
	@NotNull(groups = { Update.class, Create.class })
	private String title;
	@NotNull(groups = { Update.class, Create.class })
	private String type;
	private String desc;
	private List<ImageNote> imageNotes;
	private List<MultipleChoiceNote> multipleChoiceNotes;
}
