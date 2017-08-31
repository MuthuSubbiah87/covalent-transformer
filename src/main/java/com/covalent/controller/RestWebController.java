package com.covalent.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.covalent.models.FileModel;
import com.covalent.service.CovalentService;


//ready for use by spring mvc to handle web request(@RestController)
@RestController
public class RestWebController {
	
	@Autowired
	private final CovalentService service;

	@Autowired
	RestWebController(CovalentService service) {
		this.service = service;
	} 
	@RequestMapping(value = "/file/list", method = RequestMethod.GET)
	public List<FileModel> getResource(){
		List<FileModel> filesList = service.findAll();
		return filesList;
	}	
	
}
