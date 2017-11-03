package com.blogspot.direinem.application.user;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.blogspot.direinem.application.AbstractController;
import com.blogspot.direinem.domain.model.User;
import com.blogspot.direinem.infrastructure.encryption.ShaEncryptor;
import com.blogspot.direinem.infrastructure.repository.UserRepository;

@Named
@RequestScoped
public class ProfileController extends AbstractController {

	@Inject
	private UserRepository userRepository;
	@Inject
	private ShaEncryptor shaEncryptor;

	private String firstname;
	private String lastname;
	private String email;
	private String zip;
	private String street;
	private String phone;
	private String password;
	private String confirmPassword;
	private String profileState;
	private String passwordState;

	@PostConstruct
	public void init() {
		User user = userRepository.getUserByEmail(getCurrentUsername());

		this.firstname = user.getFirstname();
		this.lastname = user.getLastname();
		this.email = user.getEmail();
		this.zip = user.getZip();
		this.street = user.getStreet();
		this.phone = user.getPhone();
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getProfileState() {
		return profileState;
	}

	public String getPasswordState() {
		return passwordState;
	}

	public void saveProfile() {
		try {
			User user = userRepository.getUserByEmail(getCurrentUsername());

			user.setFirstname(firstname);
			user.setLastname(lastname);
			user.setStreet(street);
			user.setPhone(phone);
			user.setZip(zip);

			userRepository.updateUser(user);

			this.profileState = "Profile update successful!";
		}
		catch (Exception e) {
			this.profileState = "Profile update failed!";
		}
	}

	public void savePassword() {
		try {
			User user = userRepository.getUserByEmail(getCurrentUsername());
			user.setPassword(shaEncryptor.encrypt(password));
			userRepository.updateUser(user);

			this.passwordState = "Password update successful!";
		}
		catch (Exception e) {
			this.passwordState = "Password update failed!";
		}
	}
}
