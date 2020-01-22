package com.mokhovav.meeting_room_reservation;

import com.mokhovav.meeting_room_reservation.entities.authority.Authority;
import com.mokhovav.meeting_room_reservation.entities.user.User;
import com.mokhovav.meeting_room_reservation.entities.user.UserService;
import com.mokhovav.meeting_room_reservation.entities.authority.AuthorityService;
import com.mokhovav.meeting_room_reservation.error.CustomException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class MeetingRoomReservation {

	private final PasswordEncoder passwordEncoder;
	private final AuthorityService authorityService;
	private final UserService userService;

	public MeetingRoomReservation(PasswordEncoder passwordEncoder, AuthorityService authorityService, UserService userService) {
		this.passwordEncoder = passwordEncoder;
		this.authorityService = authorityService;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(MeetingRoomReservation.class, args);
	}

	@PostConstruct
	private void addDefaultUser() throws CustomException {
		try {
			authorityService.save(new Authority("admin"));
		} catch (Exception e) {
			System.out.println("Warning: " + e.getMessage());
		}
		try {
			userService.save(new User("admin", passwordEncoder.encode("admin")));
		} catch (Exception e) {
			System.out.println("Warning: " + e.getMessage());
		}
		User usr = userService.getByUserName("admin");

		if (!userService.isHaveAuthority(usr, authorityService.getByAuthority("admin"))) {
			usr.getAuthorities().add(authorityService.getByAuthority("admin"));
			userService.update(usr);
		}
	}

}
