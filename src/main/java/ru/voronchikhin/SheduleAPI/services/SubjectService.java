package ru.voronchikhin.SheduleAPI.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.voronchikhin.SheduleAPI.dto.SubjectDTO;
import ru.voronchikhin.SheduleAPI.models.Subject;
import ru.voronchikhin.SheduleAPI.repositories.SubjectRepository;
import ru.voronchikhin.SheduleAPI.util.SubjectNotFoundException;


import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> findAll(){
        return subjectRepository.findAll();
    }

    public Optional<Subject> findById(int id){
        return subjectRepository.findById(id);
    }

    public Optional<Subject> findByName(String name){
        return subjectRepository.findByName(name);
    }

    @Transactional
    public void save(Subject subject){
        subjectRepository.save(subject);
    }

    @Transactional
    public void delete(int id){
        subjectRepository.deleteById(id);
    }

    @Transactional
    public void update(String name, SubjectDTO subjectDTO){
        Optional<Subject> subjectToUpdate = subjectRepository.findByName(name);
        if (subjectToUpdate.isEmpty()){
            throw new SubjectNotFoundException("We can't update group with this name(");
        }else{
            subjectToUpdate.get().setName(subjectDTO.getName());
        }
    }
}
