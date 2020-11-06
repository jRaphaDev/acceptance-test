package br.com.opensource.channels;

import br.com.opensource.channels.validate.BranchesValidation;
import br.com.opensource.channels.validate.EletronicChannelsValidation;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[ API Canais de Atendimento ]")
public class CanaisAtendimentoTest {

    private static final String CHANNELS_RESOURCE = "http://localhost:3000";//"https:{{api-seu-banco}}/open-banking/channels/v1";

    @Test
    @DisplayName("[ Branches ]")
    public void branches_shouldListAll() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get(CHANNELS_RESOURCE + "/branches").asJson();
        assertThat(response.getStatus()).isEqualTo(200);

        BranchesValidation.verifyFieldsRequired(response.getBody());
    }

    @Test
    @DisplayName("[ Eletronic Channels ]")
    public void electronicChannels_shouldListAll() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get(CHANNELS_RESOURCE + "/electronic-channels").asJson();
        assertThat(response.getStatus()).isEqualTo(200);

        EletronicChannelsValidation.verifyFieldsRequired(response.getBody());
    }

    //@Test
    //@DisplayName("[ Phone Channels ]")
    public void phoneChannels_shouldListAll() throws UnirestException {
        HttpResponse<JsonNode> branches = Unirest.get(CHANNELS_RESOURCE + "/phone-channels").asJson();
        assertThat(branches.getStatus()).isEqualTo(404);
    }

    //@Test
    //@DisplayName("[ Banking Agents ]")
    public void bankingAgents_shouldListAll() throws UnirestException {
        HttpResponse<JsonNode> branches = Unirest.get(CHANNELS_RESOURCE + "/eletronic-channels").asJson();
        assertThat(branches.getStatus()).isEqualTo(404);
    }

}
