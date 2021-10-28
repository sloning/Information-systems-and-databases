package com.isbd.service.village;

import com.isbd.DAO.DAO;
import com.isbd.exception.VillageException;
import com.isbd.model.Village;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VillageServiceImpl implements VillageService {
    private final DAO<Village> villageDAO;


    @Override
    public List<Village> getVillages() {
        return villageDAO.getAll();
    }

    @Override
    public Village getVillage(int id) {
        return villageDAO.get(id).orElseThrow(() ->
                new VillageException.EntityNotFoundException(String.format("Village with id: %d was not found", id)));
    }
}
