package com.midasit.challenge.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.midasit.challenge.utils.ImageUtils;
import com.midasit.challenge.utils.UploadUtils;

@Controller
public class ImageController {

	private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

	@Value("${app.uploadPath}")
	private String uploadPath;

	@ResponseBody
	@RequestMapping(value = "/upload/image", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String uploadAjax(MultipartFile file, String str, HttpSession session, HttpServletRequest request,
			Model model) throws Exception {

		logger.info("originalName: " + file.getOriginalFilename());

		ResponseEntity<String> img_path = new ResponseEntity<>(
				UploadUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()), HttpStatus.CREATED);
		String user_imgPath = (String) img_path.getBody();

		logger.info("file name : " + user_imgPath);

		return user_imgPath;
	}

	@ResponseBody
	@RequestMapping("/display/image")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {

		InputStream in = null;
		ResponseEntity<byte[]> entity = null;

		logger.info("FILE NAME: " + fileName);

		try {

			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

			MediaType mType = ImageUtils.getMediaType(formatName);

			HttpHeaders headers = new HttpHeaders();

			in = new FileInputStream(uploadPath + fileName);

			if (mType != null) {
				headers.setContentType(mType);
			} else {

				fileName = fileName.substring(fileName.indexOf("_") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition",
						"attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
			}

			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}

	@ResponseBody
	@RequestMapping(value = "/delete/image", method = RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String fileName) {

		logger.info("delete file: " + fileName);

		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

		MediaType mType = ImageUtils.getMediaType(formatName);

		if (mType != null) {

			String front = fileName.substring(0, 12);
			String end = fileName.substring(14);
			new File(uploadPath + (front + end).replace('/', File.separatorChar)).delete();
		}

		new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();

		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
}
