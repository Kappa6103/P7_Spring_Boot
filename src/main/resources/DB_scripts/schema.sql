CREATE SCHEMA IF NOT EXISTS `demo`;
USE `demo`;

CREATE TABLE `bid` (
  bid_id INT NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  bidQuantity DOUBLE,
  askQuantity DOUBLE,
  bid DOUBLE ,
  ask DOUBLE,
  benchmark VARCHAR(125),
  bidListDate TIMESTAMP,
  commentary VARCHAR(125),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  book VARCHAR(125),
  creationName VARCHAR(125),
  creationDate TIMESTAMP ,
  revisionName VARCHAR(125),
  revisionDate TIMESTAMP ,
  dealName VARCHAR(125),
  dealType VARCHAR(125),
  sourceListId VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (`bid_id`)
);

CREATE TABLE `trade` (
  trade_id INT NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buyQuantity DOUBLE,
  sellQuantity DOUBLE,
  buyPrice DOUBLE ,
  sellPrice DOUBLE,
  tradeDate TIMESTAMP,
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creationName VARCHAR(125),
  creationDate TIMESTAMP ,
  revisionName VARCHAR(125),
  revisionDate TIMESTAMP ,
  dealName VARCHAR(125),
  dealType VARCHAR(125),
  sourceListId VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (`trade_id`)
);

CREATE TABLE `curve_point` (
  curve_point_id INT NOT NULL AUTO_INCREMENT,
  CurveId tinyint,
  asOfDate TIMESTAMP,
  term DOUBLE ,
  curve_point_value DOUBLE ,
  creationDate TIMESTAMP ,

  PRIMARY KEY (curve_point_id)
);

CREATE TABLE `rating` (
  rating_id INT NOT NULL AUTO_INCREMENT,
  moodysRating VARCHAR(125),
  sandPRating VARCHAR(125),
  fitchRating VARCHAR(125),
  orderNumber tinyint,

  PRIMARY KEY (rating_id)
);

CREATE TABLE `rulename` (
  rulename_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(125),
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sqlStr VARCHAR(125),
  sqlPart VARCHAR(125),

  PRIMARY KEY (rulename_id)
);

CREATE TABLE Users (
  Id tinyint NOT NULL AUTO_INCREMENT,
  username VARCHAR(125),
  password VARCHAR(125),
  fullname VARCHAR(125),
  role ENUM('ADMIN', 'USER') NOT NULL,

  PRIMARY KEY (Id)
);