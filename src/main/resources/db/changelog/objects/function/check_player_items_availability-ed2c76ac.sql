CREATE OR REPLACE FUNCTION public.check_player_items_availability(_offer_id bigint, _player_id bigint)
    RETURNS void
    LANGUAGE plpgsql
AS
$function$
DECLARE
    _item_id           int;
    DECLARE player_has int;
BEGIN
    SELECT buying_item_id INTO _item_id FROM offer WHERE offer_id = _offer_id;
    SELECT amount INTO player_has FROM inventory WHERE player_id = _player_id AND item_id = _item_id;
    IF (player_has IS NULL)
        OR player_has <
           (SELECT amount_of_buying_items FROM offer WHERE offer.offer_id = _offer_id) THEN
        RAISE EXCEPTION 'Player has not enough items';
    END IF;
END;
$function$
