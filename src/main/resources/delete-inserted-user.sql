DELETE FROM `user_role` WHERE `user_id` = (
	SELECT `id` 
	FROM `user`
	WHERE `username` = "wojtek")
;

DELETE FROM `user` WHERE `username` = "wojtek";