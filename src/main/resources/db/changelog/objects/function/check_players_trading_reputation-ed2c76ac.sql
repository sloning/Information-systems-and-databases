CREATE OR REPLACE FUNCTION public.check_players_trading_reputation(_offer_id bigint, _player_id bigint)
    RETURNS void
    LANGUAGE plpgsql
AS
$function$
DECLARE
    _reputation_level int;
BEGIN
    SELECT reputation_level
    INTO _reputation_level
    FROM trading_reputation
    WHERE trading_reputation.player_id = _player_id
      AND villager_id = (SELECT villager_id FROM offer WHERE offer.offer_id = _offer_id);
    IF (_reputation_level IS NULL) THEN
        _reputation_level = 1;
        INSERT INTO trading_reputation(player_id, villager_id, reputation_level)
        VALUES (_player_id, (SELECT villager_id FROM offer WHERE offer_id = _offer_id), _reputation_level);
    END IF;
    IF (SELECT needed_reputation
        FROM offer
                 JOIN reputation_level ON offer.needed_reputation_level = reputation_level.reputation_level_id
        WHERE offer_id = _offer_id) > _reputation_level THEN
        RAISE EXCEPTION 'Player has not enough trading level';
    END IF;
END;
$function$
