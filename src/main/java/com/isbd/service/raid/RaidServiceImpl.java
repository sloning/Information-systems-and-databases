package com.isbd.service.raid;

import com.isbd.Dao.RaidDao;
import com.isbd.Dao.VillageDao;
import com.isbd.Dto.RaidDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.AppliedEffect;
import com.isbd.model.Raid;
import com.isbd.model.Village;
import com.isbd.security.AuthenticationFacade;
import com.isbd.service.effect.AppliedEffectService;
import com.isbd.service.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RaidServiceImpl implements RaidService {
    private final RaidDao raidDao;
    private final VillageDao villageDao;
    private final AppliedEffectService appliedEffectService;
    private final InventoryService inventoryService;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public void createRaid() {
        if (Math.random() < 0.7) return;

        List<Village> villages = villageDao.getAll(Integer.MAX_VALUE, 0);
        if (villages.size() == 0) return;
        int lastVillage = villages.stream().map(Village::getId).max(Comparator.comparing(Integer::valueOf)).get();
        int villageWithRaid = (int) (Math.random() * lastVillage);
        if (villageWithRaid < 1) villageWithRaid = 1;

        Raid raid = new Raid();
        raid.setVillageId(villageWithRaid);
        raid.setStartTime(LocalDateTime.now());
        raid.setEndTime(raid.getStartTime().plusMinutes(30));
        raidDao.save(raid);
    }

    @Override
    public List<Raid> getRaids() {
        createRaid();
        return raidDao.getAll();
    }

    @Override
    public RaidDto fightRaid(int raidId) {
        long playerId = authenticationFacade.getPlayerId();
        AppliedEffect appliedEffect;
        Raid raid = raidDao.get(raidId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Raid with id: %d was not found", raidId)));
        if (raid.getEndTime().isBefore(LocalDateTime.now())) raidDao.delete(raid);

        if (Math.random() < 0.5) {
            inventoryService.deleteByPlayerId(playerId);
            return new RaidDto(false, null);
        }
        raidDao.delete(raid);
        try {
            appliedEffect = appliedEffectService.applyEffect(1, playerId);
        } catch (EntityNotFoundException e) {
            return new RaidDto(true, null);
        }
        return new RaidDto(true, appliedEffect);
    }
}
