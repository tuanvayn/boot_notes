package com.vmodev.tuanva.notes.service.util;

public enum NoteStatusEnum {
	COMPLETED("0"), INCOMPLETED("1");

	String status;

	NoteStatusEnum(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
