CREATE OR REPLACE FUNCTION public.add_reputation(_offer_id bigint, _player_id bigint)
    RETURNS void
    LANGUAGE plpgsql
AS
$function$
DECLARE
    current_reputation_level int;
    DECLARE _villager_id     int;
BEGIN
    SELECT villager_id INTO _villager_id FROM offer WHERE offer_id = _offer_id;
    SELECT reputation_level
    INTO current_reputation_level
    FROM trading_reputation
    WHERE player_id = _player_id
      AND villager_id = _villager_id;

    INSERT INTO trading_reputation(player_id, villager_id, reputation_level)
    VALUES (_player_id, _villager_id, current_reputation_level + 1)
    ON CONFLICT(player_id, villager_id) DO UPDATE SET reputation_level = current_reputation_level + 1;
END;
$function$
