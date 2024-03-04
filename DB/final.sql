-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : dim. 03 mars 2024 à 22:16
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `final`
--

-- --------------------------------------------------------

--
-- Structure de la table `arbitre`
--

CREATE TABLE `arbitre` (
  `id_arbitre` int(11) NOT NULL,
  `Nom` varchar(255) DEFAULT NULL,
  `prenom` text DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE `categorie` (
  `IDCateg` int(11) NOT NULL,
  `Nom` varchar(255) DEFAULT NULL,
  `Description` text DEFAULT NULL,
  `Image` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`IDCateg`, `Nom`, `Description`, `Image`) VALUES
(1, 'Volleyball', 'your ultimate sport gathering', '/img/istockphoto-1371823675-612x612.jpg'),
(2, 'Tennis', 'it only takes two persons to live the fun', '/img/807f757c-tennis-gf8a2b0441_1920-768x513.jpg'),
(3, 'Football', 'Gather your team and be our guest or we will create a one for you', '/img/clubs-foot-europeens-plus-suivis-reseaux-sociaux-min.jpeg'),
(4, 'Rugby', 'your ultimate sport matching', '/img/sport-le-nouvel-equipement-a-400-euros-qui-arrive-sur-les-pelouses-de-rugby-1451176.jpg'),
(5, 'Basketball', 'your ultimate sport gathering', '/img/180358.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `classementequipe`
--

CREATE TABLE `classementequipe` (
  `id` int(11) NOT NULL,
  `equipe_id` int(11) NOT NULL,
  `points` int(11) NOT NULL,
  `rank` int(11) NOT NULL,
  `nbre_de_match` int(11) NOT NULL,
  `win` int(11) NOT NULL,
  `draw` int(11) NOT NULL,
  `loss` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `classementuser`
--

CREATE TABLE `classementuser` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `points` int(11) NOT NULL,
  `rank` int(11) NOT NULL,
  `nbre_de_match` int(11) NOT NULL,
  `win` int(11) NOT NULL,
  `draw` int(11) NOT NULL,
  `loss` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `commentaire`
--

CREATE TABLE `commentaire` (
  `ID_Commentaire` int(11) NOT NULL,
  `ID_User` int(11) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `Heure` time DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `competition`
--

CREATE TABLE `competition` (
  `ID_Competition` int(11) NOT NULL,
  `Nom` varchar(100) DEFAULT NULL,
  `Type` varchar(50) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `Heure` time DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `equipe1_id` int(11) DEFAULT NULL,
  `equipe2_id` int(11) DEFAULT NULL,
  `terrain_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `custom`
--

CREATE TABLE `custom` (
  `ID_Custom` int(11) NOT NULL,
  `ID_User` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `equipe`
--

CREATE TABLE `equipe` (
  `IDEquipe` int(11) NOT NULL,
  `Nom` varchar(255) DEFAULT NULL,
  `Niveau` varchar(50) DEFAULT NULL,
  `IDCateg` int(11) DEFAULT NULL,
  `isRandom` tinyint(1) NOT NULL,
  `id_createur` int(11) DEFAULT NULL,
  `rank` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `equipe`
--

INSERT INTO `equipe` (`IDEquipe`, `Nom`, `Niveau`, `IDCateg`, `isRandom`, `id_createur`, `rank`) VALUES
(3, 'ESTS', 'niveau1', 1, 0, 2, 1),
(4, 'CA', 'niveau1', 2, 1, 3, 2),
(5, 'EST', 'niveau1', 1, 0, 2, 1),
(6, 'CA', 'niveau1', 2, 1, 3, 2),
(7, 'EST', 'niveau1', 1, 0, 2, 1),
(8, 'CA', 'niveau1', 2, 1, 3, 2),
(9, 'EST', 'niveau1', 1, 0, 2, 1),
(10, 'CA', 'niveau1', 2, 1, 3, 2),
(11, 'EST', 'niveau1', 1, 0, 2, 1),
(12, 'CA', 'niveau1', 2, 1, 3, 2),
(14, 'Sample Equipe', 'Sample Niveau', 1, 1, NULL, 1),
(16, 'Sample Equipe', 'Sample Niveau', 1, 1, 16, 1),
(17, 'Sample Equipe', 'Sample Niveau', 1, 1, 17, 1),
(18, 'Updated Nom', 'Updated Niveau', 1, 0, 18, 2),
(19, 'Sample Equipe', 'Sample Niveau', 1, 1, 19, 1),
(20, 'Sample Equipe', 'Sample Niveau', 1, 1, 20, 1),
(21, 'Sample Equipe', 'Sample Niveau', 1, 1, 21, 1),
(22, 'Sample Equipe', 'Sample Niveau', 1, 1, 22, 1),
(23, 'Sample Equipe', 'Sample Niveau', 1, 1, 23, 1),
(24, 'Sample Equipe', 'Sample Niveau', 1, 1, 24, 1),
(25, 'Sample Equipe', 'Sample Niveau', 1, 1, 25, 1),
(26, 'Sample Equipe', 'Sample Niveau', 1, 1, 26, 1),
(27, 'Sample Equipe', 'Sample Niveau', 1, 1, 27, 1),
(28, 'Sample Equipe', 'Sample Niveau', 1, 1, 28, 1),
(29, 'Sample Equipe', 'Sample Niveau', 1, 1, 29, 1),
(30, 'Sample Equipe', 'Sample Niveau', 1, 1, 30, 1),
(31, 'Sample Equipe', 'Sample Niveau', 1, 1, 31, 1),
(32, 'Sample Equipe', 'Sample Niveau', 1, 1, 32, 1),
(33, 'Sample Equipe', 'Sample Niveau', 1, 1, 33, 1),
(34, 'Sample Equipe', 'Sample Niveau', 1, 1, 34, 1),
(35, 'Sample Equipe', 'Sample Niveau', 1, 1, 35, 1),
(36, 'Sample Equipe', 'Sample Niveau', 1, 1, 36, 1),
(37, 'Achouri\'s Team', 'silver', 2, 1, NULL, 2),
(38, 'achouri\'s team', 'niveau2', 2, 1, NULL, 3),
(39, 'EST', 'niveau1', 1, 0, NULL, 1),
(40, 'EST', 'niveau1', 1, 0, NULL, 1),
(41, 'Sample Equipe', '1', 1, 1, NULL, 1),
(42, '1', 'niveau1', 1, 0, NULL, 1);

-- --------------------------------------------------------

--
-- Structure de la table `equipe_members`
--

CREATE TABLE `equipe_members` (
  `equipe_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `matc`
--

CREATE TABLE `matc` (
  `ID_Matc` int(11) NOT NULL,
  `Nom` varchar(100) DEFAULT NULL,
  `Type` varchar(50) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `Heure` time DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Equipe1` int(11) DEFAULT NULL,
  `Equipe2` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `post`
--

CREATE TABLE `post` (
  `ID_post` int(11) NOT NULL,
  `ID_User` int(11) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `Heure` time DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE `reservation` (
  `ID_Reservation` int(11) NOT NULL,
  `id` int(11) DEFAULT NULL,
  `id_Terrain` int(11) DEFAULT NULL,
  `Date_Heure` datetime DEFAULT NULL,
  `Duree` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `score`
--

CREATE TABLE `score` (
  `idScore` int(11) NOT NULL,
  `competitionId` int(11) DEFAULT NULL,
  `winnerId` int(11) DEFAULT NULL,
  `loserId` int(11) DEFAULT NULL,
  `equipe1score` int(11) DEFAULT NULL,
  `equipe2Score` int(11) DEFAULT NULL,
  `resultat` text DEFAULT NULL,
  `reclamation` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `terrain`
--

CREATE TABLE `terrain` (
  `id_Terrain` int(11) NOT NULL,
  `Nom` varchar(255) DEFAULT NULL,
  `Type_surface` varchar(255) DEFAULT NULL,
  `Localisation` varchar(255) DEFAULT NULL,
  `Prix` double(10,2) DEFAULT NULL,
  `ID_Proprietaire` int(11) DEFAULT NULL,
  `image_ter` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `terrain`
--

INSERT INTO `terrain` (`id_Terrain`, `Nom`, `Type_surface`, `Localisation`, `Prix`, `ID_Proprietaire`, `image_ter`) VALUES
(44, 'Terrain L\'aouina', 'CLair', 'L\'aouina', 150.50, NULL, 'C:\\Users\\wael\\Downloads\\prix-terrain-de-tennis.jpg'),
(46, 'Happy-teniss', 'Estendu', 'Ariana', 100.00, NULL, 'C:\\Users\\wael\\Downloads\\téléchargé (1).jpg'),
(47, 'Golden_Tennis', 'Clair', 'Rades', 75.00, NULL, 'C:\\Users\\wael\\Downloads\\téléchargé.jpg'),
(49, 'TenissForYou', 'Couvert', 'Sousse', 87.00, 0, 'C:\\Users\\wael\\Downloads\\images (1).jpg'),
(76, 'tERRAIN', 'tERRAIN', 'tERRAIN', 100.00, 0, 'C:\\Users\\wael\\Downloads\\téléchargé.png');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `mot_de_passe` varchar(255) DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `date_de_naissance` date DEFAULT NULL,
  `role` enum('USER','ADMIN','PROPRIETAIRE') DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `niveau_competence` enum('NONE','Gold') DEFAULT NULL,
  `verified` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `nom`, `prenom`, `image`, `mot_de_passe`, `adresse`, `date_de_naissance`, `role`, `email`, `niveau_competence`, `verified`) VALUES
(1, 'Marwanii', 'Wael', 'IMG_0558.JPG', '$2a$10$LhaxevCVhMwzxvwtAi1cVedMoYEZUWVWmUwVw4zpbmtOagtRZ1/lq', 'Ariana', '2024-03-13', 'USER', 'marwaniwael88@gmail.com', 'NONE', 1);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateurs`
--

CREATE TABLE `utilisateurs` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `mot_de_passe` varchar(100) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `niveau_competence` varchar(50) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  `date_de_naissance` date DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `verified` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utilisateurs`
--

INSERT INTO `utilisateurs` (`id`, `nom`, `prenom`, `email`, `mot_de_passe`, `image`, `niveau_competence`, `role`, `date_de_naissance`, `adresse`, `verified`) VALUES
(5, 'Achouri', 'Mariem', 'Achouri.Mariem@gmail.com', 'mariem12%', '/img/jimmy-fallon.png', 'niveau 1', 'client', '2014-02-06', 'Sidi bouzid', 0),
(6, 'Amdouni', 'Wajdi', 'Amdouni.Wajdi@gmail.com', 'wajdi12%', '/img/jimmy-fallon.png', 'niveau2', 'client', '2014-02-07', 'Tunis', 0),
(7, 'Achouri', 'Mariem', 'Achouri.Mariem@gmail.com', 'mariem12%', '/img/jimmy-fallon.png', 'niveau 1', 'client', '2014-02-06', 'Sidi bouzid', 0),
(8, 'Amdouni', 'Wajdi', 'Amdouni.Wajdi@gmail.com', 'wajdi12%', '/img/jimmy-fallon.png', 'niveau2', 'client', '2014-02-07', 'Tunis', 0),
(9, 'Mariem', 'Achouri', 'achoury.mayem@gmail.com', '$2a$10$eAeE/5hy9jnDstEmhCvlHOZZr43rQWyTdv4nEr592VOXG4JdS.UsS', 'rechercher.png', 'NONE', 'USER', '2013-02-28', 'Tunis', 0);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `arbitre`
--
ALTER TABLE `arbitre`
  ADD KEY `id_arbitre_index` (`id_arbitre`);

--
-- Index pour la table `classementequipe`
--
ALTER TABLE `classementequipe`
  ADD PRIMARY KEY (`id`),
  ADD KEY `equipe_id` (`equipe_id`);

--
-- Index pour la table `classementuser`
--
ALTER TABLE `classementuser`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Index pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD PRIMARY KEY (`ID_Commentaire`),
  ADD KEY `ID_User` (`ID_User`);

--
-- Index pour la table `custom`
--
ALTER TABLE `custom`
  ADD PRIMARY KEY (`ID_Custom`),
  ADD KEY `ID_User` (`ID_User`);

--
-- Index pour la table `equipe_members`
--
ALTER TABLE `equipe_members`
  ADD PRIMARY KEY (`equipe_id`,`user_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Index pour la table `matc`
--
ALTER TABLE `matc`
  ADD PRIMARY KEY (`ID_Matc`),
  ADD KEY `Equipe1` (`Equipe1`),
  ADD KEY `Equipe2` (`Equipe2`);

--
-- Index pour la table `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`ID_post`),
  ADD KEY `ID_User` (`ID_User`);

--
-- Index pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`ID_Reservation`),
  ADD KEY `ID_utilisateur` (`id`),
  ADD KEY `ID_terrain` (`id_Terrain`);

--
-- Index pour la table `score`
--
ALTER TABLE `score`
  ADD PRIMARY KEY (`idScore`),
  ADD KEY `competitionId` (`competitionId`),
  ADD KEY `winnerId` (`winnerId`),
  ADD KEY `loserId` (`loserId`);

--
-- Index pour la table `terrain`
--
ALTER TABLE `terrain`
  ADD PRIMARY KEY (`id_Terrain`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `ID_Reservation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=137;

--
-- AUTO_INCREMENT pour la table `terrain`
--
ALTER TABLE `terrain`
  MODIFY `id_Terrain` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`id`) REFERENCES `utilisateur` (`id`),
  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`id_Terrain`) REFERENCES `terrain` (`id_Terrain`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
