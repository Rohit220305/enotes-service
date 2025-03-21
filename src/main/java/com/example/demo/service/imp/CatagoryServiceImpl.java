package com.example.demo.service.imp;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.example.demo.dto.CatagoryDto;
import com.example.demo.dto.CatagoryRespo;
import com.example.demo.entity.Catagory;
import com.example.demo.repository.CatagoryRepository;	
import com.example.demo.service.CatagoryService;

@Service
public class CatagoryServiceImpl implements CatagoryService {

    @Autowired
    private CatagoryRepository catagoryRepo;
    
    @Autowired
    private ModelMapper mapper;

    @Override
    public boolean saveCatagory(CatagoryDto catagoryDto) {
        Catagory catagory = mapper.map(catagoryDto, Catagory.class);
        
        if(ObjectUtils.isEmpty(catagory.getId()))
        {
        	 catagory.setDeleted(false);
             catagory.setCreatedBy(1);
             catagory.setCreatedOn(new Date());
        }else {
			updateCatagory(catagory);
		}
        
       
        Catagory saveCatagory = catagoryRepo.save(catagory);
        return !ObjectUtils.isEmpty(saveCatagory);
    }

    private void updateCatagory(Catagory catagory) {

    	Optional<Catagory> findById = catagoryRepo.findById(catagory.getId());
		if (findById.isPresent()) {
			Catagory existCatagory = findById.get();
			catagory.setCreatedBy(existCatagory.getCreatedBy());
			catagory.setCreatedOn(existCatagory.getCreatedOn());
			catagory.setDeleted(existCatagory.isDeleted());
			
			catagory.setUpdatedBy(1);
			catagory.setUpdatedOn(new Date());
		}    	
    	
	}

	@Override
    public List<CatagoryDto> getAllCatagory() {
        List<Catagory> catagories = catagoryRepo.findByIsDeletedFalse();
        return catagories.stream().map(cat -> mapper.map(cat, CatagoryDto.class)).toList();
    }

    @Override
    public List<CatagoryRespo> getActiveCatagory() {
        List<Catagory> catagories = catagoryRepo.findByIsActiveTrueAndIsDeletedFalse();
        return catagories.stream().map(cat->mapper.map(cat, CatagoryRespo.class)).toList();
    }

    @Override
    public CatagoryDto getCatagoryById(Integer id) {
        Optional<Catagory> findByCatagoryId = catagoryRepo.findById(id);
        return findByCatagoryId.map(catagory -> mapper.map(catagory, CatagoryDto.class)).orElse(null);
    }

    @Override
    public Boolean deleteCatagoryById(Integer id) {
        Optional<Catagory> findByCatagoryId = catagoryRepo.findById(id);
        if (findByCatagoryId.isPresent()) {
            Catagory catagory = findByCatagoryId.get();
            catagory.setDeleted(true);
            catagoryRepo.save(catagory);
            return true;
        }
        return false;
    }
}
