package com.joelgtsantos.cmsusers.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import com.joelgtsantos.cmsusers.entity.ResponseTransfer;
import com.joelgtsantos.cmsusers.entity.UserExtraInformation;
import com.joelgtsantos.cmsusers.exception.ExceptionInternalError;
import com.joelgtsantos.cmsusers.inte.UserExtraInformationInt;
import com.joelgtsantos.cmsusers.repo.UserExtraInformationRepository;

@Component
public class UserExtraInformationImpl implements UserExtraInformationInt {
	/**
	 *
	 */
	public static org.slf4j.Logger log = LoggerFactory.getLogger(UserExtraInformationImpl.class);
	
	@Autowired
	private UserExtraInformationRepository repo;
	private static String UPLOADED_FOLDER = "C:\\temp\\";
	
	@Override
	public ResponseEntity<UserExtraInformation> getUser(
			Principal principal, 
			HttpServletRequest request, 
			HttpServletResponse response) {
				
		Map details = (Map)((OAuth2Authentication)principal).getUserAuthentication().getDetails();
        String email = (String)details.get("email");        
        
		UserExtraInformation e = repo.findByEmail(email);
		return new ResponseEntity<>(e, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<UserExtraInformation> doCreate(
			UserExtraInformation userextra,
			Principal principal,
			HttpServletRequest request, 
			HttpServletResponse response)
			throws ExceptionInternalError {
		
		Map details = (Map)((OAuth2Authentication)principal).getUserAuthentication().getDetails();
		
        String email = (String)details.get("email");
		
		UserExtraInformation e = repo.findByEmail(email);
		userextra.setId(e.getId());
		e = repo.save(userextra);
		return new ResponseEntity<>(e, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseTransfer> doCV(ResponseTransfer file, HttpServletRequest request, HttpServletResponse response)
		throws ExceptionInternalError {
		FileOutputStream outFile = null;
		
        try {
            // Get the file and save it locally
            //Path path = Paths.get(UPLOADED_FOLDER + file.getFilename());
            outFile = new FileOutputStream(UPLOADED_FOLDER + file.getFileName());
            outFile.write(file.getFile());
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	try {
				outFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
		return new ResponseEntity<>(file, HttpStatus.CREATED);
	}
}