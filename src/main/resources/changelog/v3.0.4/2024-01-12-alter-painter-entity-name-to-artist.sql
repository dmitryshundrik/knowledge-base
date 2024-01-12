
alter table painters rename to artists;

alter table artists rename constraint painters_slug_constraint to artists_slug_constraint;

alter table painters_events rename to artists_events;

alter table artists_events rename column painters_id to artists_id;

alter table artists_events rename constraint painters_events_painter_id_constraint to artists_events_artist_id_constraint;

alter table artists_events rename constraint painters_events_event_id_constraint to artists_events_event_id_constraint;

alter table paintings rename column painter_id to artist_id;

alter table paintings rename column painter_top_rank to artist_top_rank;

alter table paintings rename constraint paintings_painter_id_constraint to paintings_artist_id_constraint;


