package com.covalent.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covalent.SpringBootWebApplication;
import com.covalent.models.FileModel;
import com.covalent.service.FilesProcessService;

@Controller
public class UploadController {
	
	final static Logger logger = Logger.getLogger(UploadController.class);
	
	@Autowired
	private final FilesProcessService filesProcessService;

	UploadController(FilesProcessService service) {
		this.filesProcessService = service;
	}	

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@PostMapping("/upload")
	public @ResponseBody String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws InterruptedException {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/status";
		}
		try {
			logger.debug("Uploading File: " + file.getName());
			byte[] bytes  = uploadFile(file);
			filesProcessService.processTheFile(file, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/uploadStatus";
	}
	
	private byte[] uploadFile(MultipartFile file) throws IOException {
		/** Uploading File **/
		byte[] bytes = file.getBytes();
		return bytes;
	}
	
}