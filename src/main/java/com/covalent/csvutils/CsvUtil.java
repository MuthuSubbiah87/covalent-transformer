package com.covalent.csvutils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class CsvUtil {

	public CSVReader getCSVReader(String file) {
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(file), ',');
//			 reader = new CSVReader(new InputStreamReader(new FileInputStream(
//			 file), "UTF-8" ), ','); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reader;
	}

	public CSVWriter getCSVWriter(String file) {
		CSVWriter csvWriter = null;
		try {
			csvWriter = new CSVWriter(new FileWriter(file),
					CSVWriter.DEFAULT_SEPARATOR);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		return csvWriter;
	}

	public HeaderColumnNameTranslateMappingStrategy<MetaData> getColumnStrategy() {
		HeaderColumnNameTranslateMappingStrategy<MetaData> strategy = new HeaderColumnNameTranslateMappingStrategy<MetaData>();
		strategy.setType(MetaData.class);
		strategy.setColumnMapping(getColumnMap());
		return strategy;
	}

	public static List<String[]> toStringArray(List<MetaData> metaDataList) {
		List<String[]> records = new ArrayList<String[]>();
		// adding header record
		records.add(new String[] { "Name", "Circle Take", "Filename",
				"Slate_Scene", "SlateScene_SlateTake_calc", "ShootDate",
				"Slate_Take", "SlateScene_SlateTake_Camera_calc",
				"Codebook_Pictures::Image", "Tape", "LabRoll", "Cam Roll",
				"Cam Type", "AuxTC 24", "Key Start", "Timecode_Start",
				"Timecode_End", "Timecode_Duration", "FrameNumberDuration",
				"FrameNumberStart", "FrameNumberEnd", "SoundRoll", "SoundTC",
				"AuxTC1", "AudioTRK1", "AudioTRK2", "AudioTRK3", "AudioTRK4",
				"AudioTRK5", "AudioTRK6", "AudioTRK7", "AudioTRK8",
				"Image Calculation", "Selects", "Lens", "Description",
				"Script Super Notes", "Comments_telecine", "VFX prefix",
				"VFX Set ID", "VFX notes", "DateCreated", "VFX Witness Cams",
				"DateModified" });
		Iterator<MetaData> it = metaDataList.iterator();
		while (it.hasNext()) {
			MetaData metaData = it.next();
			records.add(new String[] { metaData.getName(),
					metaData.getCircleTake(), metaData.getFileName(),
					metaData.getSlateScene(), metaData.getSlateSceneCalc(),
					metaData.getShootDate(), metaData.getSlateTake(),
					metaData.getSlateTakeCameraCalc(), metaData.getPictures(),
					metaData.getTape(), metaData.getLabRoll(),
					metaData.getCamRoll(), metaData.getCamRoll(),
					metaData.getAuxTc(), metaData.getKeyStart(),
					metaData.getTimecodeStart(), metaData.getTimecodeEnd(),
					metaData.getTimecodeDuration(),
					metaData.getFrameNumberDuration(),
					metaData.getframeNumberStart(),
					metaData.getFrameNumberEnd(), metaData.getSoundRoll(),
					metaData.getSoundTC(), metaData.getAuxTcOne(),
					metaData.getAudTrkOne(), metaData.getAudTrkTwo(),
					metaData.getAudTrkThree(), metaData.getAudTrkFour(),
					metaData.getAudTrkFive(), metaData.getAudTrkSix(),
					metaData.getAudTrkSeven(), metaData.getAudTrkEight(),
					metaData.getImageCalculation(), metaData.getSelects(),
					metaData.getLens(), metaData.getDescription(),
					metaData.getScriptSuperNotes(),
					metaData.getCommentsTelecine(), metaData.getVfxPrefix(),
					metaData.getVfxSetId(), metaData.getVfxNotes(),
					metaData.getDateCreated(), metaData.getVfxWitnessCams(),
					metaData.getDateModified() });
		}
		return records;
	}

	private Map<String, String> getColumnMap() {
		Map<String, String> columnMapping = new HashMap<String, String>();
		columnMapping.put("Name", "name");
		columnMapping.put("Filename", "fileName");
		columnMapping.put("Slate_Scene", "slateScene");
		columnMapping.put("SlateScene_SlateTake_calc", "slateSceneCalc");
		columnMapping.put("ShootDate", "shootDate");
		columnMapping.put("Slate_Take", "slateTake");
		columnMapping.put("SlateScene_SlateTake_Camera_calc",
				"slateTakeCameraCalc");
		columnMapping.put("Codebook_Pictures::Image", "pictures");
		columnMapping.put("Tape", "tape");
		columnMapping.put("LabRoll", "labRoll");
		columnMapping.put("Cam Roll", "camRoll");
		columnMapping.put("Cam Type", "camType");
		columnMapping.put("AuxTC 24", "auxTc");
		columnMapping.put("Key Start", "keyStart");
		columnMapping.put("Timecode_Start", "timecodeStart");
		columnMapping.put("Timecode_End", "timecodeEnd");
		columnMapping.put("Timecode_Duration", "timecodeDuration");
		columnMapping.put("FrameNumberDuration", "frameNumberDuration");
		columnMapping.put("FrameNumberStart", "frameNumberStart");
		columnMapping.put("FrameNumberEnd", "frameNumberEnd");
		columnMapping.put("SoundRoll", "soundRoll");
		columnMapping.put("SoundTC", "soundTC");
		columnMapping.put("AuxTC1", "auxTcOne");
		columnMapping.put("AudioTRK1", "audTrkOne");
		columnMapping.put("AudioTRK2", "audTrkTwo");
		columnMapping.put("AudioTRK3", "audTrkThree");
		columnMapping.put("AudioTRK4", "audTrkFour");
		columnMapping.put("AudioTRK5", "audTrkFive");
		columnMapping.put("AudioTRK6", "audTrkSix");
		columnMapping.put("AudioTRK7", "audTrkSeven");
		columnMapping.put("AudioTRK8", "audTrkEight");
		columnMapping.put("Image Calculation", "imageCalculation");
		columnMapping.put("Selects", "selects");
		columnMapping.put("Lens", "lens");
		columnMapping.put("Description", "description");
		columnMapping.put("Script Super Notes", "scriptSuperNotes");
		columnMapping.put("Comments_telecine", "commentsTelecine");
		columnMapping.put("VFX prefix", "vfxPrefix");
		columnMapping.put("VFX Set ID", "vfxSetId");
		columnMapping.put("VFX notes", "vfxNotes");
		columnMapping.put("DateCreated", "dateCreated");
		columnMapping.put("VFX Witness Cams", "vfxWitnessCams");
		columnMapping.put("DateModified", "dateModified");
		return columnMapping;
	}
}
