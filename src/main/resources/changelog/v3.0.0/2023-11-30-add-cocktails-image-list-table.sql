create table if not exists cocktails_image_list
(
    cocktail_id uuid not null constraint cocktails_image_list_cocktail_id_constraint references cocktails,
    image_list_id uuid not null constraint cocktails_image_list_image_list_id_constraint references images
);

alter table words owner to postgres;