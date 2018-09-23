INSERT INTO `user` (`id`, `username`, `password`, `email`, `enable`, `first_name`, `last_name`) VALUES
	(8, "walder", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","walder.frey@crossing.com",TRUE, "Walder", "Frey")
;

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
    (8, 1)
;