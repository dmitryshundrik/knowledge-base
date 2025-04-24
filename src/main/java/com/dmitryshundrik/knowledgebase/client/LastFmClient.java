package com.dmitryshundrik.knowledgebase.client;

import com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topalbums.TopAlbumsResponse;
import com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topartists.TopArtistsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "lastfm", url = "${lastfm.api.url}")
public interface LastFmClient {

    @GetMapping("/?method=user.gettopartists&format=json")
    TopArtistsResponse getTopArtists(@RequestParam("user") String user,
                                     @RequestParam("period") String period,
                                     @RequestParam("limit") String limit,
                                     @RequestParam("page") String page,
                                     @RequestParam("api_key") String apiKey);

    @GetMapping("/?method=user.gettopalbums&format=json")
    TopAlbumsResponse getTopAlbums(@RequestParam("user") String user,
                                   @RequestParam("period") String period,
                                   @RequestParam("limit") String limit,
                                   @RequestParam("page") String page,
                                   @RequestParam("api_key") String apiKey);

}
