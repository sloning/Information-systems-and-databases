CREATE OR REPLACE FUNCTION public.give_kit_starter()
    RETURNS trigger
    LANGUAGE plpgsql
AS
$function$
BEGIN
    PERFORM give_kit(new.player_id, 1);
    RETURN new;
END;
$function$
