package com.example.Hospital.controller;

import com.example.Hospital.model.Doctor;
import com.example.Hospital.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctores")

public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<Doctor> getAllPacientes() {
        return doctorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createDoctor(@RequestBody Doctor doctor) {
        if (doctor.getNombre() == null || doctor.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo 'nombre' es obligatorio");
        }
        if (doctor.getApellido() == null || doctor.getApellido().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo 'apellido' es obligatorio");
        }
        if (doctor.getDni() == null || doctor.getDni().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo 'dni' es obligatorio");
        }
        if (doctor.getEspecialidad() == null || doctor.getEspecialidad().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo 'especialidad' es obligatorio");
        }
        return ResponseEntity.ok(doctorService.save(doctor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        if (doctorService.findById(id).isPresent()) {
            return ResponseEntity.ok(doctorService.update(id, doctor));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        if (doctorService.findById(id).isPresent()) {
            doctorService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
