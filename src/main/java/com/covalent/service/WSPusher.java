package com.covalent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covalent.models.FileModel;

@Service
public class WSPusher {

	//@Autowired
	//private SimpRMessagingTemplate template;

	public Iterable<FileModel> websocketNotify(Iterable<FileModel> fileModels) {
		//template.convertAndSend("/ws-topic/user", fileModels);
		return fileModels;
	}
}
