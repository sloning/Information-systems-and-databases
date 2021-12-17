package com.isbd.service;

import com.isbd.dao.KitDao;
import com.isbd.dao.KitObtainmentDao;
import com.isbd.dto.ObtainedKitDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.exception.KitHaveBeenAlreadyGivenException;
import com.isbd.model.InventoryItem;
import com.isbd.model.Kit;
import com.isbd.model.ObtainedKit;
import com.isbd.security.AuthenticationFacade;
import com.isbd.service.mapper.ObtainedKitMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KitService {
    private final KitObtainmentDao kitObtainmentDao;
    private final KitDao kitDao;
    private final InventoryService inventoryService;
    private final AuthenticationFacade authenticationFacade;
    private final ObtainedKitMapper obtainedKitMapper;

    public Kit getKit(int kitId) {
        return kitDao.get(kitId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Набор с идентификатором %d не найден", kitId)));
    }

    public List<ObtainedKitDto> getAll() {
        long playerId = authenticationFacade.getPlayerId();
        List<ObtainedKitDto> kits = kitObtainmentDao.getByPlayer(playerId).stream().map(obtainedKitMapper::createFrom)
                .collect(Collectors.toList());
        if (kits.size() < 2) {
            List<Kit> actualKits = kitDao.getAll();
            for (int i = 0; i < actualKits.size(); ++i) {
                try {
                    if (actualKits.get(i).getId() != kits.get(i).getId()) {
                        kits.add(obtainedKitMapper.createFrom(convertToObtainedKit(actualKits.get(i))));
                    }
                } catch (Exception e) {
                    kits.add(obtainedKitMapper.createFrom(convertToObtainedKit(actualKits.get(i))));
                }
            }
        }
        return kits;
    }

    private ObtainedKit convertToObtainedKit(Kit kit) {
        ObtainedKit obtainedKit = new ObtainedKit();

        obtainedKit.setId(kit.getId());
        obtainedKit.setName(kit.getName());
        obtainedKit.setItems(kit.getItems());
        obtainedKit.setReload(kit.getReload());
        obtainedKit.setLastObtained(LocalDateTime.now().minusSeconds(kit.getReload() + 1));

        return obtainedKit;
    }

    public List<InventoryItem> obtainKit(int obtainingKitId) {
        long playerId = authenticationFacade.getPlayerId();
        validateKitId(obtainingKitId);
        checkKitAvailabilityForPlayer(obtainingKitId, playerId);

        kitDao.obtainKit(playerId, obtainingKitId);
        return inventoryService.getByPlayerId(playerId);
    }

    private void validateKitId(int kitId) {
        getKit(kitId);
    }

    private void checkKitAvailabilityForPlayer(int kitId, long playerId) {
        Optional<ObtainedKit> optionalObtainedKit = kitObtainmentDao.getByPlayerAndKit(playerId, kitId);
        optionalObtainedKit.ifPresent(this::checkReloadOfKit);
    }

    private void checkReloadOfKit(ObtainedKit obtainedKit) {
        long secondsToReload = obtainedKit.getReload();
        long secondsPassedSinceLastObtainment =
                ChronoUnit.SECONDS.between(obtainedKit.getLastObtained(), LocalDateTime.now());
        if (secondsToReload > secondsPassedSinceLastObtainment) {
            long hoursToWait = (secondsToReload - secondsPassedSinceLastObtainment) / 3600;
            long minutesToWait = ((secondsToReload - secondsPassedSinceLastObtainment) % 3600) / 60;
            long secondsToWait = (secondsToReload - secondsPassedSinceLastObtainment) % 216000;
            throw new KitHaveBeenAlreadyGivenException(
                    String.format("Для получения данного набора вы должны подождать ещё %d часов %d минут %d секунд",
                            hoursToWait, minutesToWait, secondsToWait));
        }
    }
}
