package com.javagda17.myproject.carservice.service;

import com.javagda17.myproject.carservice.mapper.ChecklistMapper;
import com.javagda17.myproject.carservice.model.Checklist;
import com.javagda17.myproject.carservice.model.Item;
import com.javagda17.myproject.carservice.model.dto.ChecklistDto;
import com.javagda17.myproject.carservice.model.dto.ItemDto;
import com.javagda17.myproject.carservice.repository.ChecklistRepository;
import com.javagda17.myproject.carservice.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChecklistService {
    @Autowired
    private ChecklistRepository checklistRepository;
    @Autowired
    private ChecklistMapper checklistMapper;
    @Autowired
    private ItemRepository itemRepository;

    public List<Checklist> getAllWpisy(){
        return checklistRepository.findAll();
    }

    public Optional<Checklist> addWall(ChecklistDto checklistDto){
        Checklist checklist= new Checklist();
        checklist.setDataWpisu(checklistDto.getWpisDataWpisu());
        checklist.setWpis(checklistDto.getWpisWpis());

        return Optional.of(checklistRepository.save(checklist));
    }
    public List<ChecklistDto> getAll(){
        return checklistRepository
                .findAll()
                .stream()
                .map(checklist -> checklistMapper.wallToWallDto(checklist))
                .collect(Collectors.toList());
    }
    public void remove(Long wpisId) {
        checklistRepository.deleteById(wpisId);
    }

    public Optional<ChecklistDto> getById(Long id) {
        Optional<Checklist> wallOptional = checklistRepository.findById(id);
        return wallOptional.map(checklist -> checklistMapper.wallToWallDto(checklist));
    }

    public void update(ChecklistDto dto) {
        Checklist checklist = checklistMapper.wallDtoToWall(dto);

        checklistRepository.save(checklist);
    }

    public void addTagToTweet(ItemDto dto) {
        Optional<Checklist> optionalTweet = checklistRepository.findById(dto.getWpisId());
        if (optionalTweet.isPresent()) {
            Checklist checklist = optionalTweet.get();
            Item tag = getTag(dto.getTagName(),dto.getTagMilage(),dto.getTagMakeyear());

            checklist.getTagSet().add(tag);
            checklistRepository.save(checklist);

            tag.getTweetSet().add(checklist);
            itemRepository.save(tag);
        }
    }

    public Item getTag(String name,Long milage,Long makeyear) {
        Optional<Item> tagOptional = itemRepository.findByName(name);
        if (tagOptional.isPresent()) {
            return tagOptional.get();
        }
        Item tag = new Item();
        tag.setName(name);
        tag.setMilage(milage);
        tag.setMakeyear(makeyear);

        tag = itemRepository.save(tag);
        tag = itemRepository.findById(tag.getId()).get();
        return tag;
    }

    public List<ChecklistDto> findTweetsWithTag(String tagName,Long tagMilage,Long tagMakeyear) {
        Item tag = getTag(tagName,tagMilage,tagMakeyear);
        return tag.getTweetSet()
                .stream()
                .map(checklist -> checklistMapper.wallToWallDto(checklist))
                .collect(Collectors.toList());
    }
}
