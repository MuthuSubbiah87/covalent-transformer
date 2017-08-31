package com.covalent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
public class WSPusher {

	@Autowired
	private SimpMessagingTemplate template;

	public String websocketNotify(String msg) {
		template.convertAndSend("/ws-process/progress", msg);
		return msg;
	}
}
