package com.isbd.service.kit;

import com.isbd.exception.EntityNotFoundException;
import com.isbd.exception.KitHaveBeenAlreadyGivenException;
import com.isbd.model.InventoryItem;
import com.isbd.model.Kit;
import com.isbd.model.ObtainedKit;
import com.isbd.repository.KitObtainmentRepository;
import com.isbd.repository.KitRepository;
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
    private final KitObtainmentRepository kitObtainmentRepository;
    private final KitRepository kitRepository;
    private final InventoryService inventoryService;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<ObtainedKit> getAll() {
        long playerId = authenticationFacade.getPlayerId();
        return kitObtainmentRepository.getByPlayer(playerId);
    }

    @Override
    public List<InventoryItem> obtainKit(int kitId) {
        long playerId = authenticationFacade.getPlayerId();
        List<Kit> kits = kitRepository.getAll();
        if (kits.stream().map(Kit::getId).noneMatch(i -> i == kitId))
            throw new EntityNotFoundException(String.format("Kit with id: %d was not found", kitId));

        Optional<ObtainedKit> obtainedKitOptional = kitObtainmentRepository.getByPlayerAndKit(playerId, kitId);
        if (obtainedKitOptional.isPresent()) {
            long hours = ChronoUnit.HOURS.between(obtainedKitOptional.get().getLastObtained(), LocalDateTime.now());
            if (hours < 24) {
                throw new KitHaveBeenAlreadyGivenException(String.format("You must wait another %d hours to get this kit",
                        24 - hours));
            }
        }

        kitRepository.obtainKit(playerId, kitId);
        return inventoryService.getByPlayerId(playerId);
    }
}
