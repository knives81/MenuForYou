select 
*
 from
(
	select t.id, tl.lang_description, t.orderdisplay
	from tb_typedish t join tb_typedishlang tl
	on t.id = tl.lang_identity
	where tl.lang_language = 'IT'
	and menu_id = 1000
) typedishes
join
(
	select dishes.id, 
	dishes.lang_description as dishlang, 
	dishes.orderdisplay, 
    ingredients.id as ingreid,
	ingredients.lang_description as ingrelang,
	dishes.dish_typedish_id from
	(
		select i.id, il.lang_description
		from tb_ingredient i join tb_ingredientlang il
		on i.id = il.lang_identity
		where il.lang_language = 'IT'
	) ingredients,
	(
		select d.id, dl.lang_description, d.orderdisplay, d.dish_typedish_id
		from tb_dish d join tb_dishlang dl
		on d.id = dl.lang_identity
		where dl.lang_language = 'IT'
	) dishes,
	tb_dish_ingredient dish_ingredient
	where ingredients.id = dish_ingredient.dish_ingredient_id2
	and dishes.id = dish_ingredient.dish_ingredient_id1
) dishesingredients
on typedishes.id = dishesingredients.dish_typedish_id
order by typedishes.orderdisplay,dishesingredients.orderdisplay;
