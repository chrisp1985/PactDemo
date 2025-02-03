package com.chrisp1985.pact.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class LadbrokesClient {

    private final ObjectMapper objectMapper;
    private final RestClient ladbrokesRestClient;

    @Autowired
    public LadbrokesClient(ObjectMapper objectMapper, RestClient ladbrokesRestClient) {
        this.objectMapper = objectMapper;
        this.ladbrokesRestClient = ladbrokesRestClient;
    }

    public ResponseModel queryOddsEndpoint(String path) {

        var responseLadbrokes = ladbrokesRestClient.get()
                .uri(path)
                .retrieve()
                .toEntity(ResponseModel.class)
                .getBody();

        try {
            log.debug("Response Ladbrokes : {}", objectMapper.writeValueAsString(responseLadbrokes));
        } catch (JsonProcessingException ignored) {
            log.debug("Response Ladbrokes (JsonProcessingException) : {}", responseLadbrokes);
        }
        return responseLadbrokes;
    }

    public ResponseModel getOddsData() {
        return queryOddsEndpoint(getPathForOddsWithStartXHoursAhead(1));
    }

    public String getPathForOddsWithStartXHoursAhead(int hoursAheadToStart) {
        int hoursToQuery = 6;

//        String classList = "139,136,130,149,144,143,142,140,119,118,115,114,113,112,111,110,127,124,122,120,179,177," +
//                "176,175,170,740,183,181,159,158,157,154,153,152,151,150,169,166,165,164,162,161,87,89,88,93,95,90,91,98,97,67,71,74,73,79,76,78,77,760,82,85,81,80,106,105,102,101";

        String classList = "1,2,3,4,5,6,7,8,9,10,11,12,1387,87,88,67,89,88,93,95,90,91,98,97,67,71,74,73,79,76,78,77,82,85,81,80";

        return String.format("/openbet-ssviewer/Drilldown/2.31/EventToOutcomeForClass/%s?responseFormat=json&prune=event&simpleFilter=event" +
                ".startTime:greaterThanOrEqual:"+ TimeUtils.getDatePlusHours(hoursAheadToStart)+"&simpleFilter=event.startTime:lessThan:"+ TimeUtils.getDatePlusHours(hoursAheadToStart + hoursToQuery)+
                "&simpleFilter=event.isStarted:isFalse&simpleFilter=event.suspendAtTime:greaterThan:" + TimeUtils.getDatePlusHours(hoursAheadToStart + hoursToQuery), classList);
    }
}
