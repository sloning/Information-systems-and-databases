package com.isbd.service;

import com.isbd.dao.RaidDao;
import com.isbd.dao.VillageDao;
import com.isbd.dto.RaidDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.exception.EntityNotSavedException;
import com.isbd.model.AppliedEffect;
import com.isbd.model.Pageable;
import com.isbd.model.Raid;
import com.isbd.model.Village;
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
    private final RaidDao raidDao;
    private final VillageDao villageDao;
    private final AppliedEffectService appliedEffectService;
    private final InventoryService inventoryService;
    private final AuthenticationFacade authenticationFacade;

    public void createRaid() {
        List<Village> villages = villageDao.getAll(Pageable.all());
        if (!canRaidBeCreated(villages)) {
            return;
        }
        int villageWithRaid = getVillageForRaid(villages);

        Raid raid = new Raid();
        raid.setVillageId(villageWithRaid);
        raid.setStartTime(LocalDateTime.now());
        raid.setEndTime(raid.getStartTime().plusMinutes(RAID_DURATION_IN_MINUTES));
        save(raid);
    }

    private boolean canRaidBeCreated(List<Village> villages) {
        if (Math.random() > CHANCE_TO_CREATE_NEW_RAID) {
            return false;
        }
        return villages.size() != 0;
    }

    private int getVillageForRaid(List<Village> villages) {
        int lastVillage = villages.stream().map(Village::getId).max(Comparator.comparing(Integer::valueOf)).get();
        return (int) (Math.random() * (lastVillage - 1) + 1.5);
    }

    public List<Raid> getRaids() {
        createRaid();
        return raidDao.getAll();
    }

    public Optional<Raid> getRaidByVillageId(int villageId) {
        createRaid();
        return raidDao.getByVillageId(villageId);
    }

    public void save(Raid raid) {
        if (raidDao.save(raid) == 0) {
            throw new EntityNotSavedException(String.format("Рейд с идентификатором %d не сохранён", raid.getId()));
        }
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
        raidDao.delete(raid);
        AppliedEffect appliedEffect = applyEffect(playerId);
        return new RaidDto(true, appliedEffect);
    }

    private Raid getValidatedRaid(int raidId) {
        Raid raid = raidDao.get(raidId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Рейд с идентификатором %d не найден", raidId)));
        if (raid.getEndTime().isBefore(LocalDateTime.now())) {
            raidDao.delete(raid);
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
