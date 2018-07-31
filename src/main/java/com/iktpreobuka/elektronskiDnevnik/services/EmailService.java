package com.iktpreobuka.elektronskiDnevnik.services;

import com.iktpreobuka.elektronskiDnevnik.models.EmailObject;

public interface EmailService { 
		 
	void sendSimpleMessage (EmailObject object); // slanje obicne tekstualne poruke 
	void sendTemplateMessage (EmailObject object) throws Exception; //slanje poruke i html sadrzaja
	void sendMessageWithAttachment (EmailObject object, String pathToAttachment) throws Exception;  // slanje poruke i attachmenta
		}

