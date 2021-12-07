package com.isbd.service;

import com.isbd.dto.ObtainedKitDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.exception.KitHaveBeenAlreadyGivenException;
import com.isbd.model.InventoryItem;
import com.isbd.model.Kit;
import com.isbd.model.ObtainedKit;
import com.isbd.repository.KitObtainmentRepository;
import com.isbd.repository.KitRepository;
import com.isbd.security.AuthenticationFacade;
import com.isbd.service.mapper.ObtainedKitMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.isbd.service.ServicesConstants.HOURS_TO_RELOAD_KIT;

@Service
@RequiredArgsConstructor
public class KitService {
    private final KitObtainmentRepository kitObtainmentRepository;
    private final KitRepository kitRepository;
    private final InventoryService inventoryService;
    private final AuthenticationFacade authenticationFacade;
    private final ObtainedKitMapper obtainedKitMapper;

    public Kit getKit(int kitId) {
        return kitRepository.get(kitId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Набор с идентификатором %d не найден", kitId)));
    }

    public List<ObtainedKitDto> getAll() {
        long playerId = authenticationFacade.getPlayerId();
        return kitObtainmentRepository.getByPlayer(playerId).stream().map(obtainedKitMapper::createFrom)
                .collect(Collectors.toList());
    }

    public List<InventoryItem> obtainKit(int obtainingKitId) {
        long playerId = authenticationFacade.getPlayerId();
        validateKitId(obtainingKitId);
        checkKitAvailabilityForPlayer(obtainingKitId, playerId);

        kitRepository.obtainKit(playerId, obtainingKitId);
        return inventoryService.getByPlayerId(playerId);
    }

    private void validateKitId(int kitId) {
        getKit(kitId);
    }

    private void checkKitAvailabilityForPlayer(int kitId, long playerId) {
        Optional<ObtainedKit> optionalObtainedKit = kitObtainmentRepository.getByPlayerAndKit(playerId, kitId);
        optionalObtainedKit.ifPresent(this::checkReloadOfKit);
    }

    private void checkReloadOfKit(ObtainedKit obtainedKit) {
        long hoursPassedSinceLastObtainment =
                ChronoUnit.HOURS.between(obtainedKit.getLastObtained(), LocalDateTime.now());
        if (HOURS_TO_RELOAD_KIT > hoursPassedSinceLastObtainment) {
            throw new KitHaveBeenAlreadyGivenException(
                    String.format("Для получения данного набора вы должны подождать ещё %d часов",
                            HOURS_TO_RELOAD_KIT - hoursPassedSinceLastObtainment));
        }
    }
}
