create table if not exists recipes_image_list
(
    recipe_id uuid not null constraint recipes_image_list_recipe_id_constraint references recipes,
    image_list_id uuid not null constraint recipes_image_list_image_list_id_constraint references images
);

alter table words owner to postgres;