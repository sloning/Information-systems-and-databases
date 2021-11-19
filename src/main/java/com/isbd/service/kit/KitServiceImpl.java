package com.isbd.service.kit;

import com.isbd.Dao.KitDao;
import com.isbd.Dao.KitObtainmentDao;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.exception.KitHaveBeenAlreadyGivenException;
import com.isbd.model.InventoryItem;
import com.isbd.model.Kit;
import com.isbd.model.ObtainedKit;
import com.isbd.security.AuthenticationFacade;
import com.isbd.service.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KitServiceImpl implements KitService {
    private final KitObtainmentDao kitObtainmentDao;
    private final KitDao kitDao;
    private final InventoryService inventoryService;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<ObtainedKit> getAll() {
        long playerId = authenticationFacade.getPlayerId();
        return kitObtainmentDao.getByPlayer(playerId);
    }

    @Override
    public List<InventoryItem> obtainKit(int kitId) {
        long playerId = authenticationFacade.getPlayerId();
        List<Kit> kits = kitDao.getAll();
        if (kits.stream().map(Kit::getId).noneMatch(i -> i == kitId))
            throw new EntityNotFoundException(String.format("Kit with id: %d was not found", kitId));

        Optional<ObtainedKit> obtainedKitOptional = kitObtainmentDao.getByPlayerAndKit(playerId, kitId);
        if (obtainedKitOptional.isPresent()) {
            long hours = ChronoUnit.HOURS.between(obtainedKitOptional.get().getLastObtained(), LocalDateTime.now());
            if (hours < 24) {
                throw new KitHaveBeenAlreadyGivenException(String.format("You must wait another %d hours to get this kit",
                        24 - hours));
            }
        }

        kitDao.obtainKit(playerId, kitId);
        return inventoryService.getByPlayerId(playerId);
    }
}
