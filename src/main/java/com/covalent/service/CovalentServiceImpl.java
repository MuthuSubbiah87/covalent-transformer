package com.covalent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.covalent.models.FileModel;
import com.covalent.repository.CovalentRepository;


@Service
public final class CovalentServiceImpl implements CovalentService{
	
	@Autowired
	private CovalentRepository covalentRepository;
	
	@Override
	public FileModel create(FileModel file) {
		FileModel fileModel = covalentRepository.save(file);
		return fileModel;
	}

	@Override
	public FileModel delete(String id) {
		covalentRepository.delete(id);
		return null;
	}
	
	@Override
	public void deleteAll() {
		covalentRepository.deleteAll();
	}

	@Override
	public List<FileModel> findAll() {
		List<FileModel> fileList = covalentRepository.findAll();
		return fileList;
	}

	@Override
	public FileModel findById(String id) {
		covalentRepository.findOne(id);
		return null;
	}

	@Override
	public FileModel update(FileModel fileModelIn) {
		FileModel fileModel = covalentRepository.save(fileModelIn);
		return fileModel;
	}

}
