package com.vmodev.tuanva.notes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vmodev.tuanva.notes.service.note.ImageNoteServiceImpl;
import com.vmodev.tuanva.notes.service.note.MultipleChoiceNoteServiceImpl;
import com.vmodev.tuanva.notes.service.note.NoteService;
import com.vmodev.tuanva.notes.service.note.PlainNoteServiceImpl;

@Service
public class NoteFactory implements NoteAbstractFactory {
	@Autowired
	PlainNoteServiceImpl plainNoteServiceImpl;

	@Autowired
	MultipleChoiceNoteServiceImpl multipleChoiceNoteServiceImpl;

	@Autowired
	ImageNoteServiceImpl imageServiceImpl;

	@Override
	public NoteService get(String type) {
		switch (type) {
		case "1":
			return plainNoteServiceImpl;
		case "2":
			return multipleChoiceNoteServiceImpl;
		case "3":
			return imageServiceImpl;
		default:
			throw new IllegalArgumentException("Type not support");
		}
	}
}
