-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Mar 05 Février 2019 à 10:40
-- Version du serveur :  5.7.14
-- Version de PHP :  5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `banque`
--

-- --------------------------------------------------------

--
-- Structure de la table `agentbanque`
--

CREATE TABLE `agentbanque` (
  `idAgent` int(11) NOT NULL,
  `login` varchar(20) NOT NULL,
  `mdp` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `agentbanque`
--

INSERT INTO `agentbanque` (`idAgent`, `login`, `mdp`) VALUES
(1, 'ismael', 'ismael');

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `idClient` int(11) NOT NULL,
  `nomClient` varchar(40) NOT NULL,
  `prenomClient` varchar(40) NOT NULL,
  `adresseClient` varchar(40) NOT NULL,
  `telephoneClient` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE `compte` (
  `idCompte` int(11) NOT NULL,
  `numCompte` varchar(20) NOT NULL,
  `idClient` int(11) NOT NULL,
  `typeCompte` varchar(20) NOT NULL,
  `soldeCompte` double NOT NULL,
  `dateCreationCompte` datetime NOT NULL,
  `mdpCompte` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `operation`
--

CREATE TABLE `operation` (
  `idOperation` int(11) NOT NULL,
  `numCompte` varchar(20) NOT NULL,
  `typeOperation` varchar(20) NOT NULL,
  `montantOperation` double NOT NULL,
  `dateOperation` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Index pour les tables exportées
--

--
-- Index pour la table `agentbanque`
--
ALTER TABLE `agentbanque`
  ADD PRIMARY KEY (`idAgent`),
  ADD UNIQUE KEY `login` (`login`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`idClient`);

--
-- Index pour la table `compte`
--
ALTER TABLE `compte`
  ADD PRIMARY KEY (`idCompte`),
  ADD KEY `fk_idClient_idClient` (`idClient`);

--
-- Index pour la table `operation`
--
ALTER TABLE `operation`
  ADD PRIMARY KEY (`idOperation`),
  ADD KEY `fk_idCompte_numCompte` (`numCompte`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `agentbanque`
--
ALTER TABLE `agentbanque`
  MODIFY `idAgent` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `idClient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT pour la table `compte`
--
ALTER TABLE `compte`
  MODIFY `idCompte` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `operation`
--
ALTER TABLE `operation`
  MODIFY `idOperation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `compte`
--
ALTER TABLE `compte`
  ADD CONSTRAINT `fk_idClient_idClient` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
