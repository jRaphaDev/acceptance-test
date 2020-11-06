package br.com.opensource.channels.validate;

import org.json.JSONArray;
import org.json.JSONObject;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilsValidation {

    public static JSONArray verifyBrand(JSONObject json) {

        var data = json.getJSONObject("data");
        var brand = data.getJSONObject("brand");

        var brandName = brand.getString("name");
        assertThat(brandName).isNotBlank();

        var companies = brand.getJSONArray("companies");
        assertThat(companies).isNotEmpty();

        return companies;
    }

    public static void verifyLinks(JSONObject json) {

        var self = json.getString("self");
        assertThat(self).isNotBlank();

        var first = json.getString("first");
        assertThat(first).isNotBlank();

        var last = json.getString("last");
        assertThat(last).isNotBlank();
    }

    public static void verifyMetas(JSONObject json) {

        var totalRecords = json.getInt("totalRecords");
        assertThat(totalRecords).isNotNegative().isNotZero();

        var totalPages = json.getInt("totalPages");
        assertThat(totalPages).isNotNegative().isNotZero();
    }

    public static void verifyBaseCompany(JSONObject company) {

        var companyName = company.getString("name");
        assertThat(companyName).isNotBlank();

        var companyCNPJ = company.getString("cnpjNumber");
        assertThat(companyCNPJ).isNotBlank();
    }


}
