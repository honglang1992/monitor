package com.vrv.monitor.core.http;

import org.apache.http.HttpException;
import org.junit.Test;

import java.io.IOException;

public class HttpUtilsTest {

    @Test
    public void testOA() throws IOException, HttpException {
        String bodyStr = "{\n" +
                "    \"I_HEAD\": {\n" +
                "        \"LIFNR\": \"\",\n" +
                "        \"BUKRS\": \"1000\",\n" +
                "        \"EKORG\": \"1000\",\n" +
                "        \"KTOKK\": \"ZK02\",\n" +
                "        \"LAND1\": \"CN\",\n" +
                "        \"NAME1\": \"TEST\",\n" +
                "        \"ORT01\": \"\",\n" +
                "        \"PSTLZ\": \"\",\n" +
                "        \"REGIO\": \"\",\n" +
                "        \"SORTL\": \"\",\n" +
                "        \"STRAS\": \"\",\n" +
                "        \"ANRED\": \"\",\n" +
                "        \"BRSCH\": \"\",\n" +
                "        \"ERDAT\": \"\",\n" +
                "        \"ERNAM\": \"\",\n" +
                "        \"KUNNR\": \"\",\n" +
                "        \"SPRAS\": \"\",\n" +
                "        \"STCEG\": \"\",\n" +
                "        \"STCD5\": \"\",\n" +
                "        \"AKONT\": \"2202010101\",\n" +
                "        \"ZTERM_BUKRS\": \"\",\n" +
                "        \"REPRF\": \"X\",\n" +
                "        \"LFABC\": \"\",\n" +
                "        \"WAERS\": \"\",\n" +
                "        \"VERKF\": \"\",\n" +
                "        \"TELF1\": \"\",\n" +
                "        \"ZTERM_EKORG\": \"\",\n" +
                "        \"WEBRE\": \"\",\n" +
                "        \"SPERR\": \"\",\n" +
                "        \"SPERR_BUKRS\": \"\",\n" +
                "        \"SPERM\": \"\",\n" +
                "        \"SPERM_EBELN\": \"\",\n" +
                "        \"SPERQ\": \"\",\n" +
                "        \"NAME3\": \"\",\n" +
                "        \"NAME4\": \"\",\n" +
                "        \"TEL_NUMBER\": \"\",\n" +
                "        \"FAX_NUMBER\": \"\",\n" +
                "        \"SMTP_ADDR\": \"\"\n" +
                "    },\n" +
                "    \"T_BANK\": {\n" +
                "        \"item\": {\n" +
                "            \"BANKS\": \"CN\",\n" +
                "            \"BANKL\": \"102375306111111\",\n" +
                "            \"BANKN\": \"TEST\",\n" +
                "            \"KOINH\": \"TEST\",\n" +
                "            \"ZBANKN\": \"\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"T_PARTNER\": {\n" +
                "        \"item\": {\n" +
                "            \"LIFN2\": \"\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        String str = HttpUtils.post("http://podev01.ajhchem.com:50000/RESTAdapter/ZMM_OATOSAP_0010/",bodyStr);
        System.out.println(str);

    }

}