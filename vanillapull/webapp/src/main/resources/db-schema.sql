create table PARAM (
	KEY VARCHAR(20) NOT NULL PRIMARY KEY,
	VALUE VARCHAR(255) NOT NULL
);

insert into PARAM(key, value) values('INTEREST_RATE', '0.015');

create table INSTRUMENT (
  SYMBOL VARCHAR(5) NOT NULL PRIMARY KEY,
  LABEL VARCHAR(30) NOT NULL,
  SPOT NUMERIC(10,2) NOT NULL,
  VOLATILITY  NUMERIC(10,2) NOT NULL,
  VARIATION  NUMERIC(10,2) NOT NULL,
);

insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('AC','Accor', 25.41, 1, 0.15);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('AI','Air Liquide', 97.92, 1, 0.10);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('ALO','Alstom', 28.72, 1, 0.73);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('MT','ArcelorMittal', 9.79, 1, -0.17);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('CS','AXA', 14.78, 1, 0.19);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('BNP','BNP Paribas', 45.04, 1, 0.89);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('EN','Bouygues', 21.30, 1, -0.31);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('CAP','Capgemini', 39.15, 1, 0.03);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('CA','Carrefour', 23.33, 1, 0.14);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('ACA','Credit Agricole', 6.92, 1, 0.13);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('EAD','EADS', 42.44, 1, 1.24);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('EDF','EDF', 18.16, 1, 0.04);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('EI','Essilor', 88.73, 1, 0.57);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('FTE','France Telecom', 8.32, 1, -0.01);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('GTO','Gemalto', 60, 1, 1.25);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('GSZ','GDF Suez', 16.58, 1, -0.16);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('BN','Groupe Danone', 58.95, 1, 0.13);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('OR','L''Oreal', 136, 1, 1.85);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('LG','Lafarge', 53.33, 1, 0.74);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('LR','Legrand', 37.68, 1, 0.17);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('MC','LVMH', 135.75, 1, 2.6);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('ML','Michelin', 67.46, 1, 0.36);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('RI','Pernod Ricard', 94.46, 1, 0.18);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('PP','PPR', 173.35, 1, -0.35);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('PUB','Publicis', 55.22, 1, -0.52);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('RNO','Renault', 56, 1, 2.21);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('SAF','Safran', 39.5, 1, 0.29);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('SGO','Saint-Gobain', 32.6, 1, 0.41);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('SAN','Sanofi', 84.75, 1, 0.05);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('SU','Schneider Electric', 58.9, 1, 0.22);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('GLE','Societe Generale', 30, 1, 0.15);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('SOLB','Solvay', 115, 1, -2.15);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('STM','STMicroelectronics', 7.05, 1, -0.01);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('TEC','Technip', 85.46, 1, 0.76);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('FP','Total', 38.97, 1, 0.17);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('UL','Unibail-Rodamco', 201.8, 1, 4.15);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('VK','Vallourec', 41.24, 1, -0.22);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('VIE','Veolia Environnement', 10.4, 1, -0.06);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('DG','Vinci', 37.71, 1, 0.16);
insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('VIV','Vivendi', 15.98, 1, 0.23);
