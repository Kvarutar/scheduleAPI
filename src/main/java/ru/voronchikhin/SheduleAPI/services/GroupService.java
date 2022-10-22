package ru.voronchikhin.SheduleAPI.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.voronchikhin.SheduleAPI.dto.GroupDTO;
import ru.voronchikhin.SheduleAPI.models.Group;
import ru.voronchikhin.SheduleAPI.repositories.GroupRepository;
import ru.voronchikhin.SheduleAPI.util.GroupNotFoundException;


import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findAll(){
        return groupRepository.findAll();
    }

    public Optional<Group> findById(int id){
        return groupRepository.findById(id);
    }

    public Optional<Group> findByName(String name){
        return groupRepository.findByName(name);
    }

    @Transactional
    public void save(Group group){
        groupRepository.save(group);
    }

    @Transactional
    public void delete(String name) throws GroupNotFoundException{
        if(groupRepository.findByName(name).isEmpty()){
            throw new GroupNotFoundException("We can't delete group with this name");
        }
        groupRepository.deleteByName(name);
    }

    @Transactional
    public void update(int id, GroupDTO groupDTO) throws GroupNotFoundException{
        Optional<Group> groupToUpdate = groupRepository.findById(id);
        if (groupToUpdate.isEmpty()){
            throw new GroupNotFoundException("We can't find group with this name");
        }else {
            groupToUpdate.get().setName(groupDTO.getName());
        }
    }
}
