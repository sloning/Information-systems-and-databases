CREATE OR REPLACE FUNCTION public.give_kit(_player_id bigint, _kit_id integer)
    RETURNS void
    LANGUAGE plpgsql
AS
$function$
DECLARE
    item record;
BEGIN
    FOR item IN SELECT item_id, amount FROM kit_composition WHERE kit_id = _kit_id
        LOOP
            INSERT INTO inventory(player_id, item_id, amount)
            VALUES (_player_id, item.item_id, item.amount)
            ON CONFLICT(player_id, item_id) DO UPDATE SET amount = item.amount +
                                                                   (SELECT amount
                                                                    FROM inventory
                                                                    WHERE player_id = _player_id
                                                                      AND item_id = item.item_id);
        END LOOP;
    INSERT INTO kit_obtainment
    VALUES (_player_id, _kit_id, CURRENT_TIMESTAMP)
    ON CONFLICT(player_id, kit_id) DO UPDATE SET last_obtainment = CURRENT_TIMESTAMP;
END;
$function$
