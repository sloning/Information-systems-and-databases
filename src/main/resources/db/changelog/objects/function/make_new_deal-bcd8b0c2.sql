CREATE OR REPLACE FUNCTION public.make_new_deal()
    RETURNS trigger
    LANGUAGE plpgsql
AS
$function$
BEGIN
    PERFORM check_players_trading_reputation(new.offer_id, new.player_id);
    RAISE NOTICE 'trading_reputation';
    PERFORM check_player_items_availability(new.offer_id, new.player_id);
    RAISE NOTICE 'items_availability';
    PERFORM exchange(new.offer_id, new.player_id);
    RAISE NOTICE 'exchange';
    PERFORM add_reputation(new.offer_id, new.player_id);
    RAISE NOTICE 'add_reputation';
    RETURN new;
END;
$function$
