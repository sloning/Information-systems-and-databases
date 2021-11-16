package com.isbd.service.raid;

import com.isbd.Dto.RaidDto;
import com.isbd.model.Raid;

import java.util.List;

public interface RaidService {
    void createRaid();

    List<Raid> getRaids();

    RaidDto fightRaid(long playerId, int raidId);
}
