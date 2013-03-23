package com.jrk.aderoid;

import java.util.Hashtable;

public class Finals {

	public static void populateClasses(Hashtable<String, String> nf) {

		nf.put("infosalles", "Informatique (Salles)");
		nf.put("geniebio", "Génie Biologique");
		nf.put("gea", "GEA");
		nf.put("genieciv", "Génie civil");
		nf.put("geniechi", "Génie chimique");
		nf.put("lpmairyc", "LP MAIRYC");
		nf.put("chimie", "Chimie");
		nf.put("info", "Informatique");

	}

	public static void populateGroups(String key,
			Hashtable<String, String> groupsTab) {
		// TODO Auto-generated method stub

		groupsTab.clear();

		if (key.equals("info")) {
			groupsTab.put("1306", "ASPE1");
			groupsTab.put("1307", "ASPE2");
			groupsTab.put("1308", "ASPE5");
			groupsTab.put("1309", "ASP3E");
			groupsTab.put("1311", "ASP4E");
			groupsTab.put("1306", "ASPE");
			groupsTab.put("1312", "DUT Modulaire 1A");
			groupsTab.put("1313", "DUT Modulaire 2A");
			groupsTab.put("1314", "DUT Modulaire 3A");
			groupsTab.put("1354", "G6S1");
			groupsTab.put("1356", "G6S2");
			groupsTab.put("1371", "G6S3");
			groupsTab.put("1358", "G6S4");
			groupsTab.put("1308", "LP DASI A");
			groupsTab.put("1309", "LP DASI B");
			groupsTab.put("1310", "LP SID");
			groupsTab.put("1440", "Réservation 1");
			groupsTab.put("1441", "Réservation 2");
			groupsTab.put("1455", "Réservation 3");
			groupsTab.put("410", "G1S1");
			groupsTab.put("403", "G2S1");
			groupsTab.put("400", "G3S1");
			groupsTab.put("272", "G4S1");
			groupsTab.put("139", "G5S1");
			groupsTab.put("1364", "G1S2");
			groupsTab.put("1363", "G2S2");
			groupsTab.put("1362", "G3S2");
			groupsTab.put("1361", "G4S2");
			groupsTab.put("114", "G1S3");
			groupsTab.put("101", "G2S3");
			groupsTab.put("95", "G3S3");
			groupsTab.put("94", "G4S3");
			groupsTab.put("1369", "G1S4");
			groupsTab.put("1368", "G2S4");
			groupsTab.put("1367", "G3S4");
			groupsTab.put("1366", "G4S4");
		} else if (key.equals("geniebio")) {
			groupsTab.put("773", "A1");
			groupsTab.put("774", "A2");
			groupsTab.put("775", "B1");
			groupsTab.put("776", "B2");
			groupsTab.put("777", "C1");
			groupsTab.put("778", "C2");
			groupsTab.put("779", "D1");
			groupsTab.put("780", "D2");
			groupsTab.put("916", "A 2a");
			groupsTab.put("918", "B 2a");
			groupsTab.put("901", "A sp");
			groupsTab.put("910", "B sp");
			groupsTab.put("750", "Agro 1");
			groupsTab.put("753", "Agro 4a");
			groupsTab.put("751", "Agro 2");
			groupsTab.put("754", "Agro 4b");
			groupsTab.put("752", "Agro 3");
			groupsTab.put("755", "Agro 4c");
			groupsTab.put("1344", "diet1");
			groupsTab.put("1343", "diet2");
			groupsTab.put("1527", "groupe chimie");
			groupsTab.put("1143", "LP Alimentation & Santé");
			groupsTab.put("1459", "LP Biotech-Génomique");
			groupsTab.put("1409", "2A ABB");
			groupsTab.put("1425", "Culture cellulaire");
			groupsTab.put("1170", "Département de Chimie");
			groupsTab.put("1243", "Département de GEA");
			groupsTab.put("1340", "FEA niveau I");
			groupsTab.put("1139", "Fête de la Science");
			groupsTab.put("1174", "Fête de la Science INRIA");
			groupsTab.put("1241", "Québec");
			groupsTab.put("1126", "Sanofi oct 2011");
			groupsTab.put("1138", "E-ABB");
			groupsTab.put("1137", "E-Agro");

		} else if (key.equals("gea")) {
			groupsTab.put("1132", "Cadre de santé");
			groupsTab.put("982", "DU DCG");
			groupsTab.put("1007", "DU MMIC");
			groupsTab.put("55", "Etudiants Bourg");
			groupsTab.put("233", "A1");
			groupsTab.put("234", "A2");
			groupsTab.put("235", "A3");
			groupsTab.put("253", "B1");
			groupsTab.put("254", "B2");
			groupsTab.put("255", "B3");
			groupsTab.put("237", "C1");
			groupsTab.put("238", "C2");
			groupsTab.put("239", "C3");
			groupsTab.put("241", "D1");
			groupsTab.put("242", "D2");
			groupsTab.put("243", "D3");
			groupsTab.put("249", "E1");
			groupsTab.put("250", "E2");
			groupsTab.put("251", "E3");
			groupsTab.put("245", "F1");
			groupsTab.put("246", "F2");
			groupsTab.put("247", "F3");
			groupsTab.put("184", "FC1a");
			groupsTab.put("185", "FC1b");
			groupsTab.put("186", "FC1c");
			groupsTab.put("227", "FC2a");
			groupsTab.put("228", "FC2b");
			groupsTab.put("229", "FC2c");
			groupsTab.put("211", "PMO1a");
			groupsTab.put("212", "PMO1b");
			groupsTab.put("213", "PMO1c");
			groupsTab.put("215", "PMO2a");
			groupsTab.put("216", "PMO2b");
			groupsTab.put("217", "PMO2c");
			groupsTab.put("384", "RHa");
			groupsTab.put("223", "RHb");
			groupsTab.put("763", "AS FC");
			groupsTab.put("219", "AS PMO");
			groupsTab.put("781", "AS RH");
			groupsTab.put("1133", "GESS");
			groupsTab.put("1134", "L3 Audit");
			groupsTab.put("894", "LPro EBusiness");
			groupsTab.put("897", "MDI Alternants");
			groupsTab.put("898", "MDI Départ étranger");
			groupsTab.put("937", "LPro PGI");
			groupsTab.put("893", "LPro RH");
			groupsTab.put("1104", "Master2 PME & ETI");

		} else if (key.equals("chimie")) {
			groupsTab.put("160", "11A");
			groupsTab.put("276", "11B");
			groupsTab.put("281", "12A");
			groupsTab.put("283", "12B");
			groupsTab.put("296", "20A");
			groupsTab.put("297", "20B");
			groupsTab.put("305", "20C");
			groupsTab.put("306", "20D");
			groupsTab.put("827", "PEPS Alt");
			groupsTab.put("823", "PEPS FI");

		} else if (key.equals("infosalles")) {
			groupsTab.put("694", "Salle de cours 10");
			groupsTab.put("695", "Salle de cours 11");
			groupsTab.put("696", "Salle de cours 12");
			groupsTab.put("697", "Salle de cours 15");
			groupsTab.put("699", "Salle de cours 23");
			groupsTab.put("1302", "Salle de cours 26");
			groupsTab.put("1291", "Salle d'informatique 01");
			groupsTab.put("1292", "Salle d'informatique 03");
			groupsTab.put("1294", "Salle d'informatique 13");
			groupsTab.put("1293", "Salle d'informatique 14");
			groupsTab.put("1295", "Salle d'informatique 16");
			groupsTab.put("1296", "Salle d'informatique 17");
			groupsTab.put("1298", "Salle d'informatique 22");
			groupsTab.put("1297", "Salle d'informatique 24");
			groupsTab.put("1304", "Salle de réseaux 18");
		}
		else if (key.equals("geniechi")) {
			groupsTab.put("160","11A");
			groupsTab.put("276","11B");
			groupsTab.put("281","12A");
			groupsTab.put("283","12B");
			groupsTab.put("296","20A");
			groupsTab.put("297","20B");
			groupsTab.put("305","20C");
			groupsTab.put("306","20D");
			groupsTab.put("827","PEPS Alt");
			groupsTab.put("823","PEPS FI");
		}
		else if (key.equals("genieciv")) {
			groupsTab.put("496", "1A");
			groupsTab.put("497", "1B");
			groupsTab.put("1091", "1C");
			groupsTab.put("498", "2A");
			groupsTab.put("499", "2B");
			groupsTab.put("1092", "2C");
			groupsTab.put("1088", "2a");
			groupsTab.put("1087", "2b");
			groupsTab.put("1085", "1a");
			groupsTab.put("1073", "1b");
			groupsTab.put("500", "1A");
			groupsTab.put("501", "1B");
			groupsTab.put("502", "2A");
			groupsTab.put("503", "2B");
			groupsTab.put("359", "1A");
			groupsTab.put("360", "1B");
			groupsTab.put("361", "2A");
			groupsTab.put("362", "2B");
			groupsTab.put("1273", "Gr TP");
			groupsTab.put("363", "Gr 1");
			groupsTab.put("365", "1A");
			groupsTab.put("366", "1B");
			groupsTab.put("367", "1A");
			groupsTab.put("369", "1B");
			groupsTab.put("1094", "Jury 1");
			groupsTab.put("1095", "Jury 2");
			groupsTab.put("424", "6A");
			groupsTab.put("425", "6B");
			groupsTab.put("482", "1A");
			groupsTab.put("483", "1B");
			groupsTab.put("484", "2A");
			groupsTab.put("485", "2B");
			groupsTab.put("486", "3A");
			groupsTab.put("487", "3B");
			groupsTab.put("488", "4A");
			groupsTab.put("489", "4B");
			groupsTab.put("490", "5A");
			groupsTab.put("491", "5B");
			groupsTab.put("492", "6A");
			groupsTab.put("493", "6B");
			groupsTab.put("388", "1A");
			groupsTab.put("389", "1B");
			groupsTab.put("390", "1A");
			groupsTab.put("391", "1B");
			groupsTab.put("738", "1C");
			groupsTab.put("428", "1A");
			groupsTab.put("429", "1B");
			groupsTab.put("431", "2A");
			groupsTab.put("432", "2B");
			groupsTab.put("434", "3A");
			groupsTab.put("435", "3B");
			groupsTab.put("437", "4A");
			groupsTab.put("438", "4B");
			groupsTab.put("440", "5A");
			groupsTab.put("441", "5B");
			groupsTab.put("444", "6A");
			groupsTab.put("445", "6B");
			groupsTab.put("450", "1A");
			groupsTab.put("451", "1B");
			groupsTab.put("1090", "2A");
			groupsTab.put("447", "1A");
			groupsTab.put("448", "1B");
			groupsTab.put("452", "2A");
			groupsTab.put("453", "2B");
			groupsTab.put("456", "3A");
			groupsTab.put("457", "3B");
			groupsTab.put("454", "4A");
			groupsTab.put("455", "4B");
			groupsTab.put("461", "5A");
			groupsTab.put("462", "5B");
			groupsTab.put("426", "C Long");
			groupsTab.put("414", "1A");
			groupsTab.put("416", "1B");
			groupsTab.put("883", "2.1A");
			groupsTab.put("884", "2.1B");
			groupsTab.put("1252", "2.2A");
			groupsTab.put("1253", "2.2B");
			groupsTab.put("879", "3A");
			groupsTab.put("880", "3B");
			groupsTab.put("418", "4A");
			groupsTab.put("419", "4B");
			groupsTab.put("886", "5A");
			groupsTab.put("887", "5B");
			groupsTab.put("872", "FC 2e Année");
			groupsTab.put("495", "FC 3e Année");
			groupsTab.put("350", "1A");
			groupsTab.put("351", "1B");
			groupsTab.put("352", "1A");
			groupsTab.put("353", "1B");
			groupsTab.put("370", "1");
			groupsTab.put("375", "2");
			groupsTab.put("376", "3");
			groupsTab.put("354", "RH.1A");
			groupsTab.put("355", "RH.1B");
			groupsTab.put("356", "1A");
			groupsTab.put("357", "1B");
			groupsTab.put("358", "2");
			groupsTab.put("1005", "EC1");
			groupsTab.put("1011", "EC2");
			groupsTab.put("1024", "GCQE1");
			groupsTab.put("1025", "GCQE2");
			groupsTab.put("1421", "GCQE3");
			groupsTab.put("1116", "Jury LPro");
			groupsTab.put("1012", "RH1");
			groupsTab.put("1019", "RH2");
			groupsTab.put("128", "RH3");
		}
		else if (key.equals("lpmairyc")) {
			groupsTab.put("1199", "LP MAIRYC");
		}

	}

}
