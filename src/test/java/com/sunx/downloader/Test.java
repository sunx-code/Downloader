package com.sunx.downloader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1 测试代码
 *   主要测试下载http数据,下载https网页源码的能力
 */
public class Test {
    private String url = "http://www.clubmed.com.cn/cm/callProposal.do?PAYS=66&LANG=ZH&village=SAYC&dateDeDebut=2017%2F07%2F08&dureeSejour=4&nbParticipants=2&nbEnfants=0&dateDeNaissance=";
    private Downloader downloader = new HttpClientDownloader();

    @org.junit.Test
    public void testHttp(){
        Downloader downloader = new HttpClientDownloader();
        Site site = new Site();
        site.addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        site.addHeader("Accept","application/json, text/javascript, */*; q=0.01");
        site.addHeader("Accept-Encoding","gzip, deflate, sdch");
        site.addHeader("Accept-Language","zh-CN,zh;q=0.8");
        site.addHeader("Content-Type","application/json");
//        site.addHeader("cookie","JSESSIONID=BE2B76105C094355FCCE1B8D1378A484.node18; ak_bmsc=8B814F1E6760665C1AD95A77EBF072827AE00A3D993C000056425259D9651676~pl29cl7iuVySWLc/bN9a88RB/Z1mZgsP9NLDVFyeSgubrdpL8ti47vSujl+tq8Qio7tQVojzlyujCu8ZQJe5XLxYkuLufUQA/uVFNrVRBiq19xf6zA8dzPabku7jVO7G4XqK4Vy1MchQF3hLppmRCxMwLH+8R8gmRWqIzGVVt+rXY7cXskOVYrHPYvpjZVaQJ7HbsZHP/mt09fjXBO0Lt0cQ==");
        site.addHeader("country","066");
        site.addHeader("lang","ZH");
        site.addHeader("village","SAYC");
        site.addHeader("proposal","58556757");

        String postData = "{\"body\":{\"id\":58556757,\"dateCreation\":[2017,6,27,0,0,0,0],\"codeCanalDistribution\":\"NET\",\"numeroProposition\":1,\"numeroDossier\":null,\"robinetTransport\":false,\"statut\":\"TEMPORAIRE\",\"dateReservation\":[2017,6,27],\"codePeriodeCommerciale\":\"17E\",\"codePaysGeographique\":\"066\",\"acheminementParDefaut\":null,\"codeLangue\":\"ZH\",\"typeForfait\":\"HOTELIER\",\"montantBookNow\":null,\"montantDynamicPricing\":null,\"gestionnaire\":{\"codeGestionnaire\":\"CBD0238\"},\"recommandation\":{\"codeMiseFileAttente\":null,\"dateMiseFileAttente\":null,\"dossierRer\":false,\"numeroCorporate\":null,\"propositionETicket\":false,\"typeTarif\":null,\"typeTarifReponse\":null,\"tarificationGds\":[]},\"espoir\":null,\"sejourOrServiceOrCircuit\":[{\"@class\":\"com.clubmed.resa.context.v1_0.Accessoire\",\"id\":386131649,\"codeRessource\":null,\"typeRessource\":\"AI\",\"sousTypeRessource\":\"DE\",\"dateDebut\":[2017,7,7],\"dateFin\":[2017,7,11],\"duree\":4,\"codePackage\":null,\"numeroOrdrePackage\":0,\"cumulable\":false,\"ressourceInformative\":null,\"ressourceLieeAvant\":null,\"ressourceLieeApres\":null,\"typeAccessoire\":null,\"sumPrices\":170,\"minPrice\":85,\"logementCommercial\":[],\"prix\":[{\"id\":1933456745,\"referencePersonne\":\"A\",\"montant\":85},{\"id\":1933456746,\"referencePersonne\":\"B\",\"montant\":85}],\"tronconDate\":[]},{\"@class\":\"com.clubmed.resa.context.v1_0.Accessoire\",\"id\":386131648,\"codeRessource\":null,\"typeRessource\":\"AI\",\"sousTypeRessource\":\"CA\",\"dateDebut\":[2017,7,7],\"dateFin\":[2017,7,11],\"duree\":4,\"codePackage\":null,\"numeroOrdrePackage\":0,\"cumulable\":false,\"ressourceInformative\":null,\"ressourceLieeAvant\":null,\"ressourceLieeApres\":null,\"typeAccessoire\":null,\"sumPrices\":360,\"minPrice\":180,\"logementCommercial\":[],\"prix\":[{\"id\":1933456743,\"referencePersonne\":\"A\",\"montant\":180},{\"id\":1933456744,\"referencePersonne\":\"B\",\"montant\":180}],\"tronconDate\":[]},{\"@class\":\"com.clubmed.resa.context.v1_0.Sejour\",\"id\":386131646,\"codeRessource\":\"SAYC\",\"typeRessource\":\"SE\",\"sousTypeRessource\":\"CE\",\"dateDebut\":[2017,7,7],\"dateFin\":[2017,7,11],\"duree\":4,\"codePackage\":null,\"numeroOrdrePackage\":0,\"cumulable\":true,\"ressourceInformative\":null,\"ressourceLieeAvant\":null,\"ressourceLieeApres\":null,\"codeFormule\":\"OAS\",\"codeForfait\":\"AI\",\"robinetSejour\":false,\"typeFormule\":\"LIBRE\",\"sumPrices\":16872,\"minPrice\":8436,\"logementCommercial\":[{\"id\":70091772,\"codeLogementCommercial\":\"A2+\",\"codeOccupation\":2,\"typeVenteOccupation\":\"UNITE\",\"codeInfoDisponibiliteOccupation\":28,\"codeInfoDisponibiliteOccupationInitiale\":132,\"referencePersonne\":[\"A\",\"B\"],\"codeLogementCommercial1\":null,\"codeOccupation1\":null,\"codeLogementCommercial2\":null,\"codeOccupation2\":null,\"nombreLogement\":1,\"topGestionLitBB\":false}],\"prix\":[{\"id\":1933456739,\"referencePersonne\":\"A\",\"montant\":8436},{\"id\":1933456740,\"referencePersonne\":\"B\",\"montant\":8436}],\"tronconDate\":[]},{\"@class\":\"com.clubmed.resa.context.v1_0.Service\",\"id\":386131647,\"codeRessource\":\"SAYSAI\",\"typeRessource\":\"SV\",\"sousTypeRessource\":\"CB\",\"dateDebut\":[2017,7,7],\"dateFin\":[2017,7,11],\"duree\":4,\"codePackage\":null,\"numeroOrdrePackage\":0,\"cumulable\":false,\"ressourceInformative\":null,\"ressourceLieeAvant\":null,\"ressourceLieeApres\":{\"codeRessource\":\"SAYC\",\"typeRessource\":\"SE\",\"dateDebut\":[2017,7,7],\"codePackage\":null,\"numeroOrdrePackage\":0},\"inclusForfait\":true,\"natureService\":\"G\",\"nombreService\":null,\"codeInformationDisponibilite\":0,\"codeCategorieService\":\"CB1\",\"informationTransfertVv\":null,\"trancheHoraire\":\"  \",\"codeInformationDisponibiliteInitiale\":0,\"sumPrices\":0,\"minPrice\":null,\"logementCommercial\":[],\"prix\":[{\"id\":1933456741,\"referencePersonne\":\"A\",\"montant\":0},{\"id\":1933456742,\"referencePersonne\":\"B\",\"montant\":0}],\"tronconDate\":[]}],\"personne\":[{\"id\":161218523,\"dateNaissance\":null,\"reference\":\"A\",\"age\":null,\"numeroClient\":0,\"nom\":null,\"prenom\":null,\"civilite\":null,\"lettreCodeFidelite\":null,\"numeroFiliation\":null,\"partitionClient\":null,\"topContact\":null,\"dateDebutSejour\":[2017,7,7],\"isChildren\":false,\"isBebe\":false},{\"id\":161218524,\"dateNaissance\":null,\"reference\":\"B\",\"age\":null,\"numeroClient\":0,\"nom\":null,\"prenom\":null,\"civilite\":null,\"lettreCodeFidelite\":null,\"numeroFiliation\":null,\"partitionClient\":null,\"topContact\":null,\"dateDebutSejour\":[2017,7,7],\"isChildren\":false,\"isBebe\":false}],\"promotion\":[{\"id\":49029070,\"codePromotion\":\"4S7S35\",\"codeReduction\":\"RED-35\",\"numeroBon\":null,\"numeroTarif\":null,\"codeAjustement\":\"Y35X\",\"typePromotion\":\"TFO\",\"promotionCible\":false,\"codeMessage\":0,\"sumPrices\":-5906,\"minPrice\":-2953,\"logementCommercial\":[],\"prix\":[{\"id\":1933456737,\"referencePersonne\":\"A\",\"montant\":-2953,\"codeGroupeAjustement\":null,\"referencePersonneContributive\":[]},{\"id\":1933456738,\"referencePersonne\":\"B\",\"montant\":-2953,\"codeGroupeAjustement\":null,\"referencePersonneContributive\":[]}],\"tronconDate\":[]}],\"attributaire\":{\"codePaysCommercial\":\"370\",\"codeReseauDistribution\":\"WB\",\"codeAgent\":\"WEB37\",\"codeDevise\":\"CNY\"}}}";


        String url = "https://secure.clubmed.com.cn/cm/be/api/14146/accommodations/true";

        Request request = new Request(url).setMethod(Method.JSON).setSrt(postData);

        String data = downloader.downloader(request,
                site.setTimeOut(10000).setIsSave(true));

        System.out.println(data);
    }

    @org.junit.Test
    public void testURLConnection() throws Exception{
        URL u = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();

        connection.connect();

        Map<String, List<String>> map = connection.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey() +
                    " ,Value : " + entry.getValue());
        }

    }

    private String page(String url){
        return downloader.downloader(url);
    }

    @org.junit.Test
    public void test(){
        String url = "http://www.lagou.com/resume/downloadResume?key=aed0842a9b63a8c541da0f7a85da530a&type=3";

        Map<String,String> header = new HashMap<String,String>();
        header.put("Cookie","JSESSIONID=ABAAABAACDBAAIA17FA1E0CEFE6319AD6DFC0CECD9064A9;"); //+
//                "PRE_UTM=; PRE_HOST=www.baidu.com; PRE_SITE=https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DH5MfMIFakyWPPLUbfz8mSVtafoLDn5YFe01ZDfDyYFe%26wd%3D%26eqid%3D9b68e65d0000051d00000004591fe553; PRE_LAND=https%3A%2F%2Fwww.lagou.com%2F; _putrc=3D2FF6B5F29D6F81; login=true; unick=%E5%AD%99%E6%98%9F; showExpriedIndex=1; showExpriedCompanyHome=1; showExpriedMyPublish=1; hasDeliver=58; index_location_city=%E6%9D%AD%E5%B7%9E; _gid=GA1.2.800925464.1495262577; _ga=GA1.2.264126753.1495189521; LGSID=20170520144232-801b4d28-3d27-11e7-86ac-5254005c3644; LGRID=20170520144258-8fbdccf8-3d27-11e7-86ac-5254005c3644");

        HttpClientDownloader downloader = new HttpClientDownloader();
        String str = downloader.downloader(new Request().setUrl(url),new Site().setHeader(header));
        System.out.println(str);
    }
    @org.junit.Test
    public void testDown(){
        String url = "https://xueqiu.com/statuses/search.json?page=1&q=%E5%92%8C%E7%9D%A6%E5%AE%B6";

        Site site = new Site();
        site.addHeader("Cookie","xq_a_token=445b4b15f59fa37c8bd8133949f910e7297a52ef;");
//        site.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//        site.addHeader("Accept-Encoding","gzip, deflate, sdch");
//        site.addHeader("Accept-Language","zh-CN,zh;q=0.8");
//        site.addHeader("Referer","http://dianzibao.cb.com.cn/html/2017-05/22/node_1.htm");
//        site.addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");


        //https://xueqiu.com/

        String src = downloader.downloader(new Request(url).setMethod(Method.GET),site);
        System.out.println(src);
    }
}