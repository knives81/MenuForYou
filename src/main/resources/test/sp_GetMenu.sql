USE `dbMenu`;
DROP procedure IF EXISTS `GetMenuIT`;

DELIMITER $$
USE `dbMenu`$$
CREATE PROCEDURE `GetMenuIT` (IN menuId bigint(20))
BEGIN
select
typedishes.id as id_td,
typedishes.lang_description as desc_td,
typedishes.orderdisplay as order_td,
dishesingredients.id as id_d,
dishesingredients.dishlang as desc_d,
dishesingredients.dishorder as order_d,
dishesingredients.ingreid as id_i,
dishesingredients.ingrelang as desc_i,
dishesingredients.ingreorder as order_i
 from
(
	select t.id, tl.lang_description, t.orderdisplay, t.menu_id
	from tb_typedish t join tb_typedishlang tl
	on t.id = tl.lang_identity
	where tl.lang_language = 'IT'
) typedishes
join
(
	select dishes.id, 
	dishes.lang_description as dishlang, 
	dishes.orderdisplay as dishorder, 
    ingredients.id as ingreid,
	ingredients.lang_description as ingrelang,
    ingredients.orderdisplay as ingreorder,
	dishes.dish_typedish_id from
    (
		select d.id, dl.lang_description, d.orderdisplay, d.dish_typedish_id
		from tb_dish d join tb_dishlang dl
		on d.id = dl.lang_identity
		where dl.lang_language = 'IT'
		and d.menu_id = menuId
	) dishes left join
    tb_dish_ingredient dish_ingredient
    on dishes.id = dish_ingredient.dish_ingredient_id1 left join
	(
		select i.id, il.lang_description, i.orderdisplay
		from tb_ingredient i join tb_ingredientlang il
		on i.id = il.lang_identity
		where il.lang_language = 'IT'
	) ingredients
	on ingredients.id = dish_ingredient.dish_ingredient_id2
) dishesingredients
on typedishes.id = dishesingredients.dish_typedish_id
order by typedishes.orderdisplay,dishesingredients.dishorder,dishesingredients.ingreorder;
END
$$

DELIMITER ;