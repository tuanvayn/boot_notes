package com.vmodev.tuanva.notes.service.note;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.vmodev.tuanva.notes.dto.BaseResponseDTO;
import com.vmodev.tuanva.notes.dto.NoteRequestDTO;
import com.vmodev.tuanva.notes.exception.CustomNotFoundException;
import com.vmodev.tuanva.notes.model.MultipleChoiceNote;
import com.vmodev.tuanva.notes.model.PlainNote;
import com.vmodev.tuanva.notes.repo.MultipleChoiceNoteRepo;
import com.vmodev.tuanva.notes.repo.PlainNoteRepo;
import com.vmodev.tuanva.notes.service.user.SecurityContextService;

@Service
public class MultipleChoiceNoteServiceImpl implements NoteService {

	@Autowired
	private PlainNoteRepo plainNoteRepo;

	@Autowired
	private MultipleChoiceNoteRepo multipleChoiceNoteRepo;

	@Override
	public boolean create(NoteRequestDTO request) throws Exception {
		List<MultipleChoiceNote> multipleChoiceNotes = null;
		PlainNote note = PlainNote.builder().title(request.getTitle()).type(request.getType())
				.createdBy(SecurityContextService.getUser()).createdAt(new Date()).desc(request.getDesc()).build();
		note = plainNoteRepo.save(note);
		final PlainNote innerNote = note;
		if (!CollectionUtils.isEmpty(request.getMultipleChoiceNotes())) {
			multipleChoiceNotes = request.getMultipleChoiceNotes().stream().map(mcNoteReq -> {
				return MultipleChoiceNote.builder().note(innerNote).option(mcNoteReq.getOption())
						.done(mcNoteReq.getDone()).build();
			}).collect(Collectors.toList());
			multipleChoiceNoteRepo.saveAll(multipleChoiceNotes);
		}
		return true;
	}

	@Override
	public boolean delete(Long id) {
		PlainNote note = plainNoteRepo.findById(id).orElseThrow(() -> new CustomNotFoundException());
		List<MultipleChoiceNote> multipleChoiceNotes = note.getMultipleChoiceNotes();
		if (!CollectionUtils.isEmpty(multipleChoiceNotes)) {
			multipleChoiceNotes.stream().forEach(multipleChoiceNoteReq -> {
				multipleChoiceNoteReq.setNote(null);
			});
		}
		note.getMultipleChoiceNotes().clear();
		plainNoteRepo.delete(note);
//		multipleChoiceNoteRepo.deleteAll(imageNotes);
		return true;
	}

	@Override
	public BaseResponseDTO<PlainNote> update(NoteRequestDTO request) {
		PlainNote note = plainNoteRepo.findById(request.getId()).orElseThrow(() -> new CustomNotFoundException());
		note = note.setTitle(request.getTitle()).setType(note.getType()).setCreatedBy(note.getCreatedBy())
				.setCreatedAt(note.getCreatedAt()).setUpdatedAt(new Date()).setDesc(request.getDesc());

		List<MultipleChoiceNote> multipleChoiceNotes = note.getMultipleChoiceNotes();
		List<MultipleChoiceNote> multipleChoiceNotesReqs = request.getMultipleChoiceNotes();

		List<Long> multipleChoiceNoteIds = note.getMultipleChoiceNotes().stream()
				.map(multipleChoiceNote -> multipleChoiceNote.getId()).collect(Collectors.toList());
		List<Long> multipleChoiceNotesReqIds = request.getMultipleChoiceNotes().stream()
				.map(multipleChoiceNote -> multipleChoiceNote.getId()).collect(Collectors.toList());
		// this list use to delete
		final List<Long> listIdNotFoundInReq = ListUtils.removeAll(multipleChoiceNotesReqIds, multipleChoiceNoteIds);
		final List<Long> listIdInDbUpdate = ListUtils.removeAll(multipleChoiceNoteIds, listIdNotFoundInReq);
		// remove from schema
		multipleChoiceNotes.stream().forEach(multipleChoiceNote -> {
			if (listIdNotFoundInReq.contains(multipleChoiceNote.getId())) {
				multipleChoiceNote.setNote(null);
			}
		});

		multipleChoiceNotesReqs.stream().forEach(multipleChoiceNoteReq -> {
			if (multipleChoiceNoteReq.getId() != null && listIdNotFoundInReq.contains(multipleChoiceNoteReq.getId())) {
				return;
			}
			if (multipleChoiceNoteReq.getId() != null && listIdInDbUpdate.contains(multipleChoiceNoteReq.getId())) {
				multipleChoiceNotes.stream().forEach(multipleChoiceNote -> {
					if (multipleChoiceNote.getId().compareTo(multipleChoiceNoteReq.getId()) == 0) {
						multipleChoiceNote.setOption(multipleChoiceNoteReq.getOption())
								.setDone(multipleChoiceNoteReq.getDone());
					}
				});
			} else {
				multipleChoiceNotes.add(MultipleChoiceNote.builder().option(multipleChoiceNoteReq.getOption())
						.done(multipleChoiceNoteReq.getDone()).build());
			}
		});

		return new BaseResponseDTO<PlainNote>(plainNoteRepo.save(note));
	}

	@Override
	public BaseResponseDTO<PlainNote> get(Long id) {
		return new BaseResponseDTO<PlainNote>(
				plainNoteRepo.findById(id).orElseThrow(() -> new CustomNotFoundException()));
	}
}
