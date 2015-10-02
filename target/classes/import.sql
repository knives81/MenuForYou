INSERT INTO `tb_user` (`authority`,`enabled`,`password`,`username`) VALUES ('ADMIN',1, '$2a$10$hcVz9jGM5uT/fasBcqrwReE.Skid7Tjz7FlrYTIpaeIJosUKYJo6W','Admin');

INSERT INTO `tb_menu` (`menu_name`,`user`) VALUES ('ROOT_MENU','Admin');

INSERT INTO `tb_parameter` (`parameter_name`,`parameter_value`,`parameter_menu_id`) VALUES ('PARAMETER1','TRUE',1);
INSERT INTO `tb_parameter` (`parameter_name`,`parameter_value`,`parameter_menu_id`) VALUES ('PARAMETER2','FALSE',1);

INSERT INTO `tb_sequencenumber` (`id`) VALUES (1);
INSERT INTO `tb_ingredient`(`menu_id`,`orderdisplay`) VALUES (1,1);
INSERT INTO `tb_ingredientlang`(`lang_description`,`lang_language`,`lang_identity`) VALUES ('Ingrediente Comune','IT',1);
INSERT INTO `tb_ingredientlang`(`lang_description`,`lang_language`,`lang_identity`) VALUES ('Common Ingredient','EN',1);

INSERT INTO `tb_sequencenumber` (`id`) VALUES (2);
INSERT INTO `tb_ingredient`(`menu_id`,`orderdisplay`) VALUES (1,2);
INSERT INTO `tb_ingredientlang`(`lang_description`,`lang_language`,`lang_identity`) VALUES ('Ingrediente Comune 2','IT',2);
INSERT INTO `tb_ingredientlang`(`lang_description`,`lang_language`,`lang_identity`) VALUES ('Common Ingredient 2','EN',2);

ALTER TABLE `tb_menu` AUTO_INCREMENT = 1000;
ALTER TABLE `tb_ingredient` AUTO_INCREMENT = 2000;
ALTER TABLE `tb_typedish` AUTO_INCREMENT = 3000;
ALTER TABLE `tb_dish` AUTO_INCREMENT = 4000;
ALTER TABLE `tb_sequencenumber` AUTO_INCREMENT = 5000;

--------------------------------------------------------------------------------

INSERT INTO `tb_user` (`authority`,`enabled`,`password`,`username`) VALUES ('ADMIN',1,'$2a$08$BeozDliOAcuUR8nhsiaxWewLlgzBw5yi1mFdC3M1Z0x.B8q9RQh/.','maurizio01');

INSERT INTO `tb_menu` (`menu_name`,`user`) VALUES ('menu01','maurizio01');

INSERT INTO `tb_parameter` (`parameter_name`,`parameter_value`,`parameter_menu_id`) VALUES ('DISPLAY_FEEDBACK','TRUE',1000);
INSERT INTO `tb_parameter` (`parameter_name`,`parameter_value`,`parameter_menu_id`) VALUES ('CURRENCY','DOLLAR',1000);

INSERT INTO `tb_restaurant`(`restaurant_address`,`restaurant_image_url`,`restaurant_name`,`restaurant_user`,`restaurant_menu_id`) VALUES ('viale trastevere','','Restaurant01','maurizio01',1000);  

INSERT INTO `tb_sequencenumber` (`id`) VALUES (5000);
INSERT INTO `tb_typedish`(`menu_id`,`orderdisplay`) VALUES (1000,5000);
INSERT INTO `tb_typedishlang` (`lang_description`,`lang_language`,`lang_identity`) VALUES ('Pasta IT','IT',3000);
INSERT INTO `tb_typedishlang` (`lang_description`,`lang_language`,`lang_identity`) VALUES ('Pasta EN','EN',3000);

INSERT INTO `tb_sequencenumber` (`id`) VALUES (5001);
INSERT INTO `tb_typedish`(`menu_id`,`orderdisplay`) VALUES (1000,5001);
INSERT INTO `tb_typedishlang` (`lang_description`,`lang_language`,`lang_identity`) VALUES ('Dolci IT','IT',3001);
INSERT INTO `tb_typedishlang` (`lang_description`,`lang_language`,`lang_identity`) VALUES ('Dessert EN','EN',3001);

INSERT INTO `tb_sequencenumber` (`id`) VALUES (5002);
INSERT INTO `tb_typedish`(`menu_id`,`orderdisplay`) VALUES (1000,5002);
INSERT INTO `tb_typedishlang` (`lang_description`,`lang_language`,`lang_identity`) VALUES ('Antipasto IT','IT',3002);
INSERT INTO `tb_typedishlang` (`lang_description`,`lang_language`,`lang_identity`) VALUES ('Appetizer EN','EN',3002);

INSERT INTO `tb_sequencenumber` (`id`) VALUES (5003);
INSERT INTO `tb_dish`(`dish_image_url`,`dish_name`,`dish_price`,`menu_id`,`dish_typedish_id`,`orderdisplay`) VALUES ('','Lasagne',15.00,1000,3000,5003);
INSERT INTO `tb_dishlang` (`lang_description`,`lang_language`,`lang_identity`) VALUES ('Lasagna al sugo','IT',4000);
INSERT INTO `tb_dishlang` (`lang_description`,`lang_language`,`lang_identity`) VALUES ('Lasagna with tomato sauce','EN',4000);

INSERT INTO `tb_sequencenumber` (`id`) VALUES (5004);
INSERT INTO `tb_dish`(`dish_image_url`,`dish_name`,`dish_price`,`menu_id`,`dish_typedish_id`,`orderdisplay`) VALUES ('','Panna Cotta',11.80,1000,3001,5004);
INSERT INTO `tb_dishlang` (`lang_description`,`lang_language`,`lang_identity`) VALUES ('Panna Cotta alle fragole','IT',4001);
INSERT INTO `tb_dishlang` (`lang_description`,`lang_language`,`lang_identity`) VALUES ('Panna Cotta strawberry','EN',4001);
INSERT INTO `tb_feedback` (`feedback_message`,`feedback_rating`,`feedback_dish_id`) VALUES ('Too salty',1,4001);
INSERT INTO `tb_feedback` (`feedback_message`,`feedback_rating`,`feedback_dish_id`) VALUES ('Very good',4,4001);

INSERT INTO `tb_sequencenumber` (`id`) VALUES (5005);
INSERT INTO `tb_dish`(`dish_image_url`,`dish_name`,`dish_price`,`menu_id`,`dish_typedish_id`,`orderdisplay`) VALUES ('','Bistecca',25.00,1000,3001,5005);
INSERT INTO `tb_dishlang` (`lang_description`,`lang_language`,`lang_identity`) VALUES ('Bistecca','IT',4002);
INSERT INTO `tb_dishlang` (`lang_description`,`lang_language`,`lang_identity`) VALUES ('Steak','EN',4002);
INSERT INTO `tb_feedback` (`feedback_message`,`feedback_rating`,`feedback_dish_id`) VALUES ('not bad',2,4002);

INSERT INTO `tb_sequencenumber` (`id`) VALUES (5006);
INSERT INTO `tb_ingredient`(`menu_id`,`orderdisplay`) VALUES (1000,5006);
INSERT INTO `tb_ingredientlang`(`lang_description`,`lang_language`,`lang_identity`) VALUES ('Burro','IT',2000);
INSERT INTO `tb_ingredientlang`(`lang_description`,`lang_language`,`lang_identity`) VALUES ('Butter','EN',2000);

INSERT INTO `tb_sequencenumber` (`id`) VALUES (5007);
INSERT INTO `tb_ingredient`(`menu_id`,`orderdisplay`) VALUES (1000,5007);
INSERT INTO `tb_ingredientlang`(`lang_description`,`lang_language`,`lang_identity`) VALUES ('Zucchero','IT',2001);
INSERT INTO `tb_ingredientlang`(`lang_description`,`lang_language`,`lang_identity`) VALUES ('Sugar','EN',2001);

INSERT INTO `tb_sequencenumber` (`id`) VALUES (5008);
INSERT INTO `tb_ingredient`(`menu_id`,`orderdisplay`) VALUES (1000,5008);
INSERT INTO `tb_ingredientlang`(`lang_description`,`lang_language`,`lang_identity`) VALUES ('Uova','IT',2002);
INSERT INTO `tb_ingredientlang`(`lang_description`,`lang_language`,`lang_identity`) VALUES ('Eggs','EN',2002);

INSERT INTO `tb_sequencenumber` (`id`) VALUES (5009);
INSERT INTO `tb_ingredient`(`menu_id`,`orderdisplay`) VALUES (1000,5009);
INSERT INTO `tb_ingredientlang`(`lang_description`,`lang_language`,`lang_identity`) VALUES ('Sale','IT',2003);
INSERT INTO `tb_ingredientlang`(`lang_description`,`lang_language`,`lang_identity`) VALUES ('Salt','EN',2003);

INSERT INTO `tb_sequencenumber` (`id`) VALUES (5010);
INSERT INTO `tb_dish`(`dish_image_url`,`dish_name`,`dish_price`,`menu_id`,`dish_typedish_id`,`orderdisplay`) VALUES ('','',15.00,1000,3001,5010);
INSERT INTO `tb_dishlang` (`lang_description`,`lang_language`,`lang_identity`) VALUES ('Insalata Cesare','IT',4003);
INSERT INTO `tb_dishlang` (`lang_description`,`lang_language`,`lang_identity`) VALUES ('Ceaser Salad','EN',4003);

INSERT INTO `tb_dish_ingredient` (`dish_ingredient_id1`,`dish_ingredient_id2`) VALUES (4000,2000);
INSERT INTO `tb_dish_ingredient` (`dish_ingredient_id1`,`dish_ingredient_id2`) VALUES (4000,2001);
INSERT INTO `tb_dish_ingredient` (`dish_ingredient_id1`,`dish_ingredient_id2`) VALUES (4002,2000);
INSERT INTO `tb_dish_ingredient` (`dish_ingredient_id1`,`dish_ingredient_id2`) VALUES (4001,1);

