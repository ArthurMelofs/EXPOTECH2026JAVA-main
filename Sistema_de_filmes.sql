CREATE DATABASE sistema_filmes;
USE sistema_filmes;

-- USUARIOS
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    perfil ENUM('ADMIN', 'USUARIO') NOT NULL
);

-- FILMES
CREATE TABLE filmes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    diretor VARCHAR(100) NOT NULL,
    ano INT NOT NULL,
    genero VARCHAR(50) NOT NULL,
    CONSTRAINT chk_ano CHECK (ano > 1800 AND ano <= 2100)
);

-- AVALIACOES
CREATE TABLE avaliacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    filme_id INT NOT NULL,
    usuario_id INT NOT NULL,
    nota INT NOT NULL,
    comentario TEXT,

    FOREIGN KEY (filme_id) REFERENCES filmes(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,

    CONSTRAINT chk_nota CHECK (nota BETWEEN 1 AND 5)
);

select * from usuarios;

select * from filmes;

select * from avaliacoes;

#Não aparecer a interogação em caracteres especiais#
ALTER DATABASE sistema_filmes
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

ALTER TABLE usuarios CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE filmes CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE avaliacoes CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

#Continua guardadndo senha mais agora com hash#
ALTER TABLE usuarios MODIFY senha VARCHAR(64);

#Insert do admin padrao#
INSERT INTO usuarios (nome, senha, perfil)
VALUES ('admin', '123', 'ADMIN');

#Inserts de usuarios#
INSERT INTO usuarios (nome, senha, perfil) VALUES
('admin', SHA2('123', 256), 'ADMIN'),
('joao', SHA2('abc123', 256), 'USUARIO'),
('maria', SHA2('senha123', 256), 'USUARIO'),
('pedro', SHA2('teste123', 256), 'USUARIO'),
('ana', SHA2('abc456', 256), 'USUARIO');

#Filmes Inserts#
INSERT INTO filmes (titulo, diretor, ano, genero) VALUES
('Interestelar', 'Christopher Nolan', 2014, 'Ficção Científica'),
('Parasita', 'Bong Joon-ho', 2019, 'Drama'),
('Clube da Luta', 'David Fincher', 1999, 'Drama'),
('Matrix', 'Irmãos Wachowski', 1999, 'Ação / Ficção Científica'),
('Oppenheimer', 'Christopher Nolan', 2023, 'Drama Histórico');
