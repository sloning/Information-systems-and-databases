package com.isbd.service;

import com.isbd.dto.RaidDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.AppliedEffect;
import com.isbd.model.Pageable;
import com.isbd.model.Raid;
import com.isbd.model.Village;
import com.isbd.repository.RaidRepository;
import com.isbd.repository.VillageRepository;
import com.isbd.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.isbd.service.ServicesConstants.*;

@Service
@RequiredArgsConstructor
public class RaidService {
    private final RaidRepository raidRepository;
    private final VillageRepository villageRepository;
    private final AppliedEffectService appliedEffectService;
    private final InventoryService inventoryService;
    private final AuthenticationFacade authenticationFacade;

    public void createRaid() {
        List<Village> villages = villageRepository.getAll(Pageable.all());
        if (!canRaidBeCreated(villages)) {
            return;
        }
        int villageWithRaid = getVillageForRaid(villages);

        Raid raid = new Raid();
        raid.setVillageId(villageWithRaid);
        raid.setStartTime(LocalDateTime.now());
        raid.setEndTime(raid.getStartTime().plusMinutes(RAID_DURATION_IN_MINUTES));
        raidRepository.save(raid);
    }

    private boolean canRaidBeCreated(List<Village> villages) {
        if (Math.random() > CHANCE_TO_CREATE_NEW_RAID) {
            return false;
        }
        return villages.size() != 0;
    }

    private int getVillageForRaid(List<Village> villages) {
        int lastVillage = villages.stream().map(Village::getId).max(Comparator.comparing(Integer::valueOf)).get();
        int villageWithRaid = (int) (Math.random() * lastVillage);
        if (villageWithRaid < 1) {
            villageWithRaid = 1;
        }
        return villageWithRaid;
    }

    public List<Raid> getRaids() {
        createRaid();
        return raidRepository.getAll();
    }

    public Optional<Raid> getRaidByVillageId(int villageId) {
        createRaid();
        return raidRepository.getByVillageId(villageId);
    }

    public RaidDto fightRaid(int raidId) {
        long playerId = authenticationFacade.getPlayerId();
        Raid raid = getValidatedRaid(raidId);

        if (Math.random() > CHANCE_TO_WIN_RAID) {
            return looseFight(playerId);
        }
        return winFight(playerId, raid);
    }

    private RaidDto looseFight(long playerId) {
        inventoryService.deleteByPlayerId(playerId);
        return new RaidDto(false, null);
    }

    private RaidDto winFight(long playerId, Raid raid) {
        raidRepository.delete(raid);
        AppliedEffect appliedEffect = applyEffect(playerId);
        return new RaidDto(true, appliedEffect);
    }

    private Raid getValidatedRaid(int raidId) {
        Raid raid = raidRepository.get(raidId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Рейд с идентификатором %d не найден", raidId)));
        if (raid.getEndTime().isBefore(LocalDateTime.now())) {
            raidRepository.delete(raid);
        }
        return raid;
    }

    private AppliedEffect applyEffect(long playerId) {
        try {
            return appliedEffectService.applyEffect(EFFECT_OF_RAID_WINNING, playerId);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }
}
