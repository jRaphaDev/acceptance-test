package br.com.opensource.channels.validate;

import br.com.opensource.channels.validate.UtilsValidation;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONObject;

import static org.assertj.core.api.Assertions.assertThat;

public class EletronicChannelsValidation {

    public static void verifyFieldsRequired(JsonNode response) {
        JSONObject json = response.getObject();

        var companies= UtilsValidation.verifyBrand(json);

        for (var x=0; x < companies.length(); x++) {

            var company = companies.getJSONObject(x);

            UtilsValidation.verifyBaseCompany(company);

            var channels = company.getJSONArray("channels");
            assertThat(channels).isNotNull().isNotEmpty();

            for (var y=0; y < channels.length(); y++) {
                var channel = channels.getJSONObject(y);

                verifyIdentification(channel);
                verifyServices(channel);

            }
        }

        var links = json.getJSONObject("links");
        UtilsValidation.verifyLinks(links);

        var meta = json.getJSONObject("meta");
        UtilsValidation.verifyMetas(meta);
    }

    private static void verifyIdentification(JSONObject channel) {

        var identification = channel.getJSONObject("identification");

        var type = identification.getEnum(ElectronicChannelsType.class, "type");
        assertThat(type).isNotNull();

        var additionalInfo = identification.getString("additionalInfo");
        assertThat(additionalInfo).isNotBlank();

        var url = identification.getString("url");
        assertThat(url).isNotBlank();

    }

    private static void verifyServices(JSONObject branch) {
        var service = branch.getJSONObject("service");

        var additionalInfo = service.getString("additionalInfo");
        assertThat(additionalInfo).isNotBlank();

        var codeType = service.getJSONArray("codes");
        for (var x=0; x < codeType.length(); x++){
            var serviceCode = codeType.getEnum(ElectronicChannelsServicesCodes.class, x);
            assertThat(serviceCode).isNotNull();
        }
    }

    private enum ElectronicChannelsType {
        INTERNET_BANKING,
        MOBILE_BANKING,
        CHAT,
        OUTROS
    }

    private enum ElectronicChannelsServicesCodes {
        ABERTURA_CONTA,
        RECEBIMENTOS_PAGAMENTOS_TRANSFERENCIAS_ELETRONICAS,
        OPERACOES_CREDITO,
        CARTAO_CREDITO,
        OPERACOES_CAMBIO,
        INVESTIMENTOS,
        SEGUROS,
        OUTROS
    }
}
