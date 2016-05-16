INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('+', 'Slobodno za razmenu', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('-', 'Deziderat', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('1', 'Naručeno', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('2', 'U obradi', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('3', 'U povezu', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('4', 'U reviziji', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('5', 'Preusmereno', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('6', 'Oštećeno', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('7', 'Zagubljeno', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('8', 'Izgubljeno', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('9', 'Otpisano', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('A', 'Aktivno', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('p', 'Pasiva', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('č', 'Čitaonica / dečje', 0);

INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('D', 'Dečje');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('N', 'Naučno');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('O', 'Odrasli');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('Z', 'Zavičajno');

INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('A', 'Autor');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('Š', 'Šabac, mesto izdanja');

INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('00', 'Sve');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('01', 'Odrasli');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('02', 'Dečje');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('03', 'Naučno');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('04', 'Zavičajno');

INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('a', 'Kupovina');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('b', 'Razmena');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('c', 'Poklon');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('d', 'Obavezni primerak');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('e', 'Zatečeni (stari) fond');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('f', 'Sopstvena izdanja');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('k', 'Kupovina');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('o', 'Otkup');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('p', 'Poklon');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('s', 'Sopstvena izdanja');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('r', 'Sopstvena izdanja');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('*', 'Nepoznato');

INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('a', 'Bibliofilski povez');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('b', 'Broširano (tvrde korice, meke korice)');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('c', 'Fascikl povez');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('d', 'Italijanski povez');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('f', 'Platno');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('g', 'Poluplatno');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('h', 'Polukoža');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('i', 'Spiralni povez (npr. kalendar)');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('k', 'Veštačka koža');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('l', 'Ostali povezi (nove vrste, nenaved. pov, moderni)');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('m', 'Meki povez');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('t', 'Tvrdi povez');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('x', 'Fotokopija');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('*', 'Nepoznato');

INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('1', 'Vremenski ograničena dostupnost - do 7 dana');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('2', 'Vremenski ograničena dostupnost - 7 dana');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('3', 'Vremenski ograničena dostupnost - 14 dana');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('4', 'Delimična dostupnost - čitaonica');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('5', 'Delimična dostupnost - sa dozvolom autora');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('6', 'Sadržaj dokumenta nedostupan (poseban tretman)');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('7', 'Potpuna nedostupnost (arhivski primerak ...)');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('*', 'Nepoznato');

INSERT INTO SigFormat (SigFormat_ID, Format_opis) VALUES ('FOL', 'Format folio');
INSERT INTO SigFormat (SigFormat_ID, Format_opis) VALUES ('I', 'Format 1');
INSERT INTO SigFormat (SigFormat_ID, Format_opis) VALUES ('II', 'Format 2');
INSERT INTO SigFormat (SigFormat_ID, Format_opis) VALUES ('III', 'Format 3');

INSERT INTO Counters (counter_name, counter_value) VALUES ('recordid', 0);
INSERT INTO Counters (counter_name, counter_value) VALUES ('primerakid', 0); 
INSERT INTO Counters (counter_name, counter_value) VALUES ('godinaid', 0);  
INSERT INTO Counters (counter_name, counter_value) VALUES ('sveskaid', 0);

INSERT INTO Counters (counter_name, counter_value) VALUES ('RN', 1);
INSERT INTO Counters (counter_name, counter_value) VALUES ('0000',1);

INSERT INTO Tipovi_obrade (tipobr_id, tipobr_spec) VALUES (1,'<?xml version="1.0"?><process-type name="Monografske - kompletna obrada" pubType="1"><initial-subfield name="0017"/><initial-subfield name="001c"/><initial-subfield name="001d"/><initial-subfield name="010a"/><initial-subfield name="100b"/><initial-subfield name="100c"/><initial-subfield name="101a"/><initial-subfield name="102a"/><initial-subfield name="200a"/><initial-subfield name="200f"/><initial-subfield name="200g"/><initial-subfield name="200h"/><initial-subfield name="200i"/><initial-subfield name="205a"/><initial-subfield name="210a"/><initial-subfield name="300a"/><initial-subfield name="7004"/><initial-subfield name="700a"/><initial-subfield name="700b"/><mandatory-subfield name="100c"/><mandatory-subfield name="200a"/></process-type>');
INSERT INTO Tipovi_obrade (tipobr_id, tipobr_spec) VALUES (2,'<?xml version="1.0"?><process-type name="Serijske - kompletna obrada" pubType="2"><initial-subfield name="0017"/><initial-subfield name="001c"/><initial-subfield name="001d"/><initial-subfield name="011a"/><initial-subfield name="100b"/><initial-subfield name="100c"/><initial-subfield name="101a"/><initial-subfield name="102a"/><initial-subfield name="110a"/><initial-subfield name="110b"/><initial-subfield name="110c"/><initial-subfield name="200a"/><initial-subfield name="200f"/><initial-subfield name="200g"/><initial-subfield name="200h"/><initial-subfield name="200i"/><initial-subfield name="205a"/><initial-subfield name="210a"/><initial-subfield name="300a"/><mandatory-subfield name="100c"/><mandatory-subfield name="200a"/></process-type>');

INSERT INTO Bibliotekari (username, password, ime, prezime, email, napomena, obrada, cirkulacija, administracija, context) VALUES ('admin', 'admin','Bojana', 'Dimi�','bdimic@uns.ns.ac.yu','napomena', 1, 1, 1, '<?xml version="1.0"?><librarian-context><process-type name="Monografske - kompletna obrada"/><process-type name="Serijske - kompletna obrada"/><default-process-type name="Monografske - kompletna obrada"/><prefixes pref1="AU" pref2="TI" pref3="PU" pref4="PY" pref5="KW"/></librarian-context>');
INSERT INTO Bibliotekari (username, password, ime, prezime, email, napomena, obrada, cirkulacija, administracija, context) VALUES ('circ', 'circ','Danijela','Tesendic','tesendic@uns.ns.ac.yu','', 0, 1, 0, '<?xml version="1.0"?><librarian-context><process-type name="Monografske - kompletna obrada"/><default-process-type name="Monografske - kompletna obrada"/><prefixes pref1="AU" pref2="TI" pref3="PU" pref4="PY" pref5="KW"/></librarian-context>');
