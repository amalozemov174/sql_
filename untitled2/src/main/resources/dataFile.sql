 CREATE TABLE IF NOT EXISTS lesson1.user (
   id INT NOT NULL,
   password VARCHAR(45) NOT NULL,
   created_at DATE NULL,
   PRIMARY KEY (id));

   CREATE TABLE IF NOT EXISTS lesson1.post (
   id INT NOT NULL,
   text VARCHAR(45) NOT NULL,
   created_at DATE NULL,
   user_id INT NOT NULL,
   PRIMARY KEY (id),
   CONSTRAINT fk_user
     FOREIGN KEY (user_id)
     REFERENCES lesson1.user (id)
     ON DELETE NO ACTION
     ON UPDATE NO ACTION);

 CREATE TABLE IF NOT EXISTS lesson1.comment (
   id INT NOT NULL,
   text VARCHAR(45) NOT NULL,
   post_id INT NOT NULL,
   user_id INT NOT NULL,
   created_at DATE NULL,
   PRIMARY KEY (id),
   CONSTRAINT fk_post_id
     FOREIGN KEY (post_id)
     REFERENCES lesson1.post(id)
     ON DELETE NO ACTION
     ON UPDATE NO ACTION,
   CONSTRAINT fk_user_id
     FOREIGN KEY (user_id)
     REFERENCES lesson1.user(id)
     ON DELETE NO ACTION
     ON UPDATE NO ACTION);


 CREATE TABLE IF NOT EXISTS lesson1.like (
   id INT NOT NULL,
   user_id INT NOT NULL,
   post_id INT NULL,
   comment_id INT NULL,
   PRIMARY KEY (id),
   CONSTRAINT fk_user_ids
     FOREIGN KEY (user_id)
     REFERENCES lesson1.user(id)
     ON DELETE NO ACTION
     ON UPDATE NO ACTION,
   CONSTRAINT fk_post_ids
     FOREIGN KEY (post_id)
     REFERENCES lesson1.post(id)
     ON DELETE NO ACTION
     ON UPDATE NO ACTION,
   CONSTRAINT fk_comment_ids
     FOREIGN KEY (comment_id)
     REFERENCES lesson1.comment (id)
     ON DELETE NO ACTION
     ON UPDATE NO ACTION);
--
CREATE OR REPLACE FUNCTION auditlogfunc() RETURNS TRIGGER AS $like_stamp$
   BEGIN
	IF new.post_id is NULL and new.comment_id is NULL THEN
		raise exception 'post_id and comment_id are NULL';
	END IF;
   END;
$like_stamp$ LANGUAGE plpgsql;

CREATE IF NOT EXISTS TRIGGER like_BEFORE_INSERT BEFORE INSERT ON lesson1.like
FOR EACH ROW EXECUTE PROCEDURE auditlogfunc();
--
insert into lesson1.user values (1 , 'abc', NOW());
insert into lesson1.post values (1, 'abcd', NOW(), 1);
insert into lesson1.comment values (1, 'comment', 1, 1, NOW());
insert into lesson1.like values (1, 1, null, 1);

--insert into `lesson1`.`like` values (2, 1, 1, null);
--insert into `lesson1`.`like` values (3, 1, null, null);