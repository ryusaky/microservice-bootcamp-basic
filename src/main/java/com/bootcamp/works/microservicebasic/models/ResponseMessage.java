package com.bootcamp.works.microservicebasic.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage<T> {

	private T entity;
	private int errorCode;
	private String message;
	private List<String> messages;
}
