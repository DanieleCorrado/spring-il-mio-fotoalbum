INSERT INTO photos(title,created_at, description, photo, visibility) VALUES('portrait','2023-11-23 11:35:00', 'test', null, 1);
INSERT INTO photos(title,created_at, description, photo, visibility) VALUES('nature','2023-11-23 11:35:00', 'test2', null, 1);

INSERT INTO categories(description, name) VALUES(null, 'portrait');
INSERT INTO categories(description, name) VALUES(null, 'nature');
INSERT INTO categories(description, name) VALUES(null, 'black & white');
INSERT INTO categories(description, name) VALUES(null, 'urban');

INSERT INTO photos_categories(photo_id, categories_id) VALUES(1, 1);
INSERT INTO photos_categories(photo_id, categories_id) VALUES(1, 4);
INSERT INTO photos_categories(photo_id, categories_id) VALUES(2, 2);
INSERT INTO photos_categories(photo_id, categories_id) VALUES(2, 3);

INSERT INTO roles (id, name) VALUES(1, 'ADMIN');
INSERT INTO roles (id, name) VALUES(2, 'USER');

INSERT INTO users (email, first_name, last_name, registered_at, password) VALUES('admin@email.com', 'John', 'Doe', '2023-11-20 10:35', '{noop}admin');
INSERT INTO users (email, first_name, last_name, registered_at, password) VALUES('user@email.com', 'Jane', 'Smith', '2023-11-20 10:35','{noop}user');

INSERT INTO users_roles (user_id, roles_id) VALUES(1, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES(2, 2);
