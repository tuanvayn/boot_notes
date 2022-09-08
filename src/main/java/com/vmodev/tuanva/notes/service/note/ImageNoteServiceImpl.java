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
import com.vmodev.tuanva.notes.model.ImageNote;
import com.vmodev.tuanva.notes.model.PlainNote;
import com.vmodev.tuanva.notes.repo.ImageNoteRepo;
import com.vmodev.tuanva.notes.repo.PlainNoteRepo;
import com.vmodev.tuanva.notes.service.user.SecurityContextService;

@Service
public class ImageNoteServiceImpl implements NoteService {

	@Autowired
	private PlainNoteRepo plainNoteRepo;

	@Autowired
	private ImageNoteRepo imageNoteRepo;

	@Override
	public boolean create(NoteRequestDTO request) throws Exception {
		List<ImageNote> imageNotes = null;
		PlainNote note = PlainNote.builder().title(request.getTitle()).type(request.getType())
				.createdBy(SecurityContextService.getUser()).createdAt(new Date()).desc(request.getDesc()).build();
		note = plainNoteRepo.save(note);
		final PlainNote innerNote = note;
		if (!CollectionUtils.isEmpty(request.getImageNotes())) {
			imageNotes = request.getImageNotes().stream().map(imageNoteReq -> {
				return ImageNote.builder().note(innerNote).image(imageNoteReq.getImage()).build();
			}).collect(Collectors.toList());
			imageNoteRepo.saveAll(imageNotes);
		}
		return true;
	}

	@Override
	public boolean delete(Long id) {
		PlainNote note = plainNoteRepo.findById(id).orElseThrow(() -> new CustomNotFoundException());
		List<ImageNote> imageNotes = note.getImageNotes();
		if (!CollectionUtils.isEmpty(imageNotes)) {
			imageNotes.stream().forEach(imageNoteReq -> {
				imageNoteReq.setNote(null);
			});
		}
		note.getImageNotes().clear();
		plainNoteRepo.delete(note);
		return true;
	}

	@Override
	public BaseResponseDTO<PlainNote> update(NoteRequestDTO request) {
		PlainNote note = plainNoteRepo.findById(request.getId()).orElseThrow(() -> new CustomNotFoundException());
		note = note.setTitle(request.getTitle()).setType(note.getType()).setCreatedBy(note.getCreatedBy())
				.setCreatedAt(note.getCreatedAt()).setUpdatedAt(new Date()).setDesc(request.getDesc());

		List<ImageNote> imageNotes = note.getImageNotes();
		List<ImageNote> imageNotesReqs = request.getImageNotes();

		List<Long> imageNoteIds = note.getImageNotes().stream().map(imageNote -> imageNote.getId())
				.collect(Collectors.toList());
		List<Long> imageNotesReqIds = request.getImageNotes().stream().map(imageNote -> imageNote.getId())
				.collect(Collectors.toList());
		// this list use to delete
		final List<Long> listIdNotFoundInReq = ListUtils.removeAll(imageNotesReqIds, imageNoteIds);
		final List<Long> listIdInDbUpdate = ListUtils.removeAll(imageNoteIds, listIdNotFoundInReq);
		// remove from schema
		imageNotes.stream().forEach(imageNote -> {
			if (listIdNotFoundInReq.contains(imageNote.getId())) {
				imageNote.setNote(null);
			}
		});

		imageNotesReqs.stream().forEach(imageNoteReq -> {
			if (imageNoteReq.getId() != null && listIdNotFoundInReq.contains(imageNoteReq.getId())) {
				return;
			}
			if (imageNoteReq.getId() != null && listIdInDbUpdate.contains(imageNoteReq.getId())) {
				imageNotes.stream().forEach(imageNote -> {
					if (imageNote.getId().compareTo(imageNoteReq.getId()) == 0) {
						imageNote.setImage(imageNoteReq.getImage());
					}
				});
			} else {
				imageNotes.add(ImageNote.builder().image(imageNoteReq.getImage()).build());
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
