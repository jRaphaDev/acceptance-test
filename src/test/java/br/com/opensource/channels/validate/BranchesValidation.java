package br.com.opensource.channels.validate;

import com.mashape.unirest.http.JsonNode;
import org.json.JSONObject;

import static org.assertj.core.api.Assertions.assertThat;

public class BranchesValidation {

    public static void verifyFieldsRequired(JsonNode response){
        JSONObject json = response.getObject();

        var companies= UtilsValidation.verifyBrand(json);
        for (var x=0; x < companies.length(); x++) {

            var company = companies.getJSONObject(x);

            UtilsValidation.verifyBaseCompany(company);

            var branches = company.getJSONArray("branches");
            assertThat(branches).isNotNull().isNotEmpty();

            for (var y=0; y < branches.length(); y++) {
                var branch = branches.getJSONObject(y);

                verifyIdentification(branch);
                verifyPostalAddress(branch);
            }

        }
        var links = json.getJSONObject("links");
        UtilsValidation.verifyLinks(links);

        var meta = json.getJSONObject("meta");
        UtilsValidation.verifyMetas(meta);
    }

    private static void verifyIdentification(JSONObject branch) {
        var identification = branch.getJSONObject("identification");

        var type = identification.getEnum(BranchIdentificationType.class, "type");
        assertThat(type).isNotNull();

        var code = identification.getString("code");
        assertThat(code).isNotBlank();

        var checkDigit = identification.getString("checkDigit");
        assertThat(checkDigit).isNotBlank();

        var name = identification.getString("name");
        assertThat(name).isNotBlank();
    }

    private static void verifyPostalAddress(JSONObject branch) {
        var postalAddress = branch.getJSONObject("postalAddress");

        var address = postalAddress.getString("address");
        assertThat(address).isNotBlank();

        var districtName = postalAddress.getString("districtName");
        assertThat(districtName).isNotBlank();

        var townName = postalAddress.getString("townName");
        assertThat(townName).isNotBlank();

        var countrySubDivision = postalAddress.getString("countrySubDivision");
        assertThat(countrySubDivision).isNotBlank();

        var postCode = postalAddress.getString("postCode");
        assertThat(postCode).isNotBlank();

        verifyAvailability(postalAddress);
        verifyServices(postalAddress);
    }

    private static void verifyAvailability(JSONObject postAddress) {
        var availability = postAddress.getJSONObject("availability");

        var allowPublicAccess = availability.getBoolean("allowPublicAccess");
        assertThat(allowPublicAccess).isNotNull();

        verifyStandards(availability);
    }

    private static void verifyStandards(JSONObject availability) {
        var standards = availability.getJSONArray("standards");

        for (var x=0; x < standards.length(); x++) {

            var standard = standards.getJSONObject(x);
            var weekday = standard.getEnum(WeekDay.class, "weekday");
            assertThat(weekday).isNotNull();

            var openingTime = standard.getString("openingTime");
            assertThat(openingTime).isNotBlank();

            var closingTime = standard.getString("closingTime");
            assertThat(closingTime).isNotBlank();
        }
    }

    private static void verifyServices(JSONObject branch) {
        var services = branch.getJSONObject("service");

        var codeType = services.getJSONArray("codes");
        for (var x=0; x < codeType.length(); x++){
            var serviceCode = codeType.getEnum(BranchesServicesCodes.class, x);
            assertThat(serviceCode).isNotNull();
        }
    }

    private enum BranchesServicesCodes {
        ABERTURA_CONTAS,
        RECEBIMENTOS_PAGAMENTOS_TRANSFERENCIAS_ELETRONICAS,
        RECEBIMENTOS_PAGAMENTOS_QUALQUER_NATUREZA,
        OPERACOES_CREDITO,
        CARTAO_CREDITO,
        OPERACOES_CAMBIO,
        INVESTIMENTOS,
        SEGUROS,
        FALAR_ATENDENTE,
        OUTROS
    }

    private enum BranchIdentificationType {
        AGENCIA,
        POSTO_ATENDIMENTO,
        POSTO_ATENDIMENTO_ELETRONICO
    }

    private enum WeekDay {
        DOMINGO,
        SEGUNDA_FEIRA,
        TERCA_FEIRA,
        QUARTA_FEIRA,
        QUINTA_FEIRA,
        SEXTA_FEIRA,
        SABADO
    }
}
