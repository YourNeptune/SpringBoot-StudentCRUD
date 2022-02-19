package com.example.demo.student;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StudentService {
	
	private final StudentRepository studentRepository;
	
	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> getStudents() {
		return studentRepository.findAll();
	}
	
	
	public void addNewStudent(Student student) {
		boolean isEmailTaken = isEmailTaken(student.getEmail());
		
		if(isEmailTaken) {
			throw new IllegalStateException("Email taken");
		}
		
		studentRepository.save(student);
	}

	public void deleteStudent(Long id) {
		boolean exists = studentRepository.existsById(id);
		if(!exists) {
			throw new IllegalStateException("student with id " + id + " does not exists");
		}else {
			studentRepository.deleteById(id);
		}
		
	}

	@Transactional
	public void updateStudent(Long id, String name, String email) {
		Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalStateException(
				"student with id " + id + " does not exists."
				));
		
		if(student != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
			student.setName(name);
		}
		
		if(student != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
			boolean isEmailTaken = isEmailTaken(email);
			if(isEmailTaken) {
				throw new IllegalStateException("Email taken");
			}
			student.setEmail(email);
		}
		
	}
	
	private boolean isEmailTaken(String email) {
		Optional<Student> studentByEmail = studentRepository.findStudentByEmail(email);
		
		if(studentByEmail.isPresent()) {
			return true;
		}
		return false;
	}
}
