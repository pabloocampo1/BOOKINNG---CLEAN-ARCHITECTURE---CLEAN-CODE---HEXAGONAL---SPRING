package com.booking_platform.infrastructure.rest.dto;

public record ChangePasswordDto (
	Integer code,
	String newPassword

) {
	
}
