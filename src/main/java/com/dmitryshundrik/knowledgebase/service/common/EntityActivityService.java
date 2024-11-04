package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.art.Artist;
import com.dmitryshundrik.knowledgebase.model.art.Painting;
import com.dmitryshundrik.knowledgebase.model.common.EntityActivity;
import com.dmitryshundrik.knowledgebase.model.common.Resource;
import com.dmitryshundrik.knowledgebase.model.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.model.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.model.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.literature.dto.WriterEntityUpdateInfoDTO;
import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicianActivityDto;
import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.CocktailService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.RecipeService;
import com.dmitryshundrik.knowledgebase.service.literature.ProseService;
import com.dmitryshundrik.knowledgebase.service.literature.QuoteService;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EntityActivityService {

    private final SettingService settingService;

    private final MusicianService musicianService;

    private final AlbumService albumService;

    private final CompositionService compositionService;

    private final RecipeService recipeService;

    private final CocktailService cocktailService;

    private final WriterService writerService;

    private final ProseService proseService;

    private final QuoteService quoteService;

    private final ArtistService artistService;

    private final PaintingService paintingService;

    private final ResourcesService resourcesService;

    public List<EntityActivity> getLatestActivities() {
        List<EntityActivity> entityActivities = new ArrayList<>();
        entityActivities.addAll(getMusicianActivities());
        entityActivities.addAll(getAlbumActivities());
        entityActivities.addAll(getCompositionActivities());
        entityActivities.addAll(getRecipeActivities());
        entityActivities.addAll(getCocktailActivities());
        entityActivities.addAll(getWriterActivities());
        entityActivities.addAll(getProseActivities());
        entityActivities.addAll(getQuoteActivities());
        entityActivities.addAll(getArtistActivities());
        entityActivities.addAll(getPaintingActivities());
        entityActivities.addAll(getResourcesActivities());

        Instant instant = Instant.now().minus(settingService.getTimeIntervalForUpdates(), ChronoUnit.DAYS);

        for (EntityActivity entityActivity : entityActivities) {
            entityActivity.setTimeStamp(InstantFormatter.instantFormatterYMDHMS(entityActivity.getCreated()));
            entityActivity.setNew(entityActivity.getCreated().isAfter(instant));
        }

        return entityActivities.stream()
                .sorted((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()))
                .limit(settingService.getLimitForUpdates())
                .collect(Collectors.toList());
    }

    private List<EntityActivity> getMusicianActivities() {
        List<EntityActivity> musicianActivities = new ArrayList<>();
        List<MusicianActivityDto> latestUpdate = musicianService.getLatestUpdate();
        for (MusicianActivityDto activityDto : latestUpdate) {
            musicianActivities.add(EntityActivity.builder()
                    .created(activityDto.getCreated())
                    .archiveSection("музыка:")
                    .description("музыкант " + activityDto.getNickName())
                    .link("music/musician/" + activityDto.getSlug())
                    .build());
        }
        return musicianActivities;
    }

    private List<EntityActivity> getAlbumActivities() {
        List<EntityActivity> albumActivities = new ArrayList<>();
        List<Album> latestUpdate = albumService.getLatestUpdate();
        for (Album album : latestUpdate) {
            albumActivities.add(EntityActivity.builder()
                    .created(album.getCreated())
                    .archiveSection("музыка:")
                    .description("альбом " + album.getTitle() + " добавлен в архив " + album.getMusician().getNickName())
                    .link("music/musician/" + album.getMusician().getSlug())
                    .build());
        }
        return albumActivities;
    }

    private List<EntityActivity> getCompositionActivities() {
        List<EntityActivity> compositionActivities = new ArrayList<>();
        List<Composition> latestUpdate = compositionService.getLatestUpdate();
        for (Composition composition : latestUpdate) {
            compositionActivities.add(EntityActivity.builder()
                    .created(composition.getCreated())
                    .archiveSection("музыка:")
                    .description("произведение " + composition.getTitle() + " добавлено в архив " + composition.getMusician().getNickName())
                    .link("music/musician/" + composition.getMusician().getSlug())
                    .build());
        }
        return compositionActivities;
    }

    private List<EntityActivity> getRecipeActivities() {
        List<EntityActivity> recipeActivities = new ArrayList<>();
        List<Recipe> latestUpdate = recipeService.getLatestUpdate();
        for (Recipe recipe : latestUpdate) {
            recipeActivities.add(EntityActivity.builder()
                    .created(recipe.getCreated())
                    .archiveSection("гастрономия:")
                    .description("рецепт " + recipe.getTitle())
                    .link("gastronomy/recipe/" + recipe.getSlug())
                    .build());
        }
        return recipeActivities;
    }

    private List<EntityActivity> getCocktailActivities() {
        List<EntityActivity> cocktailActivities = new ArrayList<>();
        List<Cocktail> latestUpdate = cocktailService.getLatestUpdate();
        for (Cocktail cocktail : latestUpdate) {
            cocktailActivities.add(EntityActivity.builder()
                    .created(cocktail.getCreated())
                    .archiveSection("гастрономия:")
                    .description("коктейль " + cocktail.getTitle())
                    .link("gastronomy/cocktail/" + cocktail.getSlug())
                    .build());
        }
        return cocktailActivities;
    }

    private List<EntityActivity> getWriterActivities() {
        List<EntityActivity> writerActivities = new ArrayList<>();
        List<WriterEntityUpdateInfoDTO> latestUpdate = writerService.getLatestUpdate();
        for (WriterEntityUpdateInfoDTO writer : latestUpdate) {
            writerActivities.add(EntityActivity.builder()
                    .created(writer.getCreated())
                    .archiveSection("литература:")
                    .description("писатель " + writer.getNickName())
                    .link("literature/writer/" + writer.getSlug())
                    .build());
        }
        return writerActivities;
    }

    private List<EntityActivity> getProseActivities() {
        List<EntityActivity> proseActivities = new ArrayList<>();
        List<Prose> latestUpdate = proseService.getLatestUpdate();
        for (Prose prose : latestUpdate) {
            proseActivities.add(EntityActivity.builder()
                    .created(prose.getCreated())
                    .archiveSection("литература:")
                    .description("произведение «" + prose.getTitle() + "» добавлено в архив " + prose.getWriter().getNickName())
                    .link("literature/writer/" + prose.getWriter().getSlug())
                    .build());
        }
        return proseActivities;
    }

    private List<EntityActivity> getQuoteActivities() {
        List<EntityActivity> quoteActivities = new ArrayList<>();
        List<Quote> latestUpdate = quoteService.getLatestUpdate();
        for (Quote quote : latestUpdate) {
            quoteActivities.add(EntityActivity.builder()
                    .created(quote.getCreated())
                    .archiveSection("литература:")
                    .description("цитата " + (quote.getProse() == null ? "" : "из «" + quote.getProse().getTitle() + "» ")
                            + "добавлена в архив " + (quote.getWriter() == null ? "" : quote.getWriter().getNickName()))
                    .link("literature/quote/all")
                    .build());
        }
        return quoteActivities;
    }

    private List<EntityActivity> getArtistActivities() {
        List<EntityActivity> artistActivities = new ArrayList<>();
        List<Artist> latestUpdate = artistService.getLatestUpdate();
        for (Artist artist : latestUpdate) {
            artistActivities.add(EntityActivity.builder()
                    .created(artist.getCreated())
                    .archiveSection("искусство:")
                    .description("художник " + artist.getNickName())
                    .link("art/artist/" + artist.getSlug())
                    .build());
        }
        return artistActivities;
    }

    private List<EntityActivity> getPaintingActivities() {
        List<EntityActivity> paintingUpdateInfo = new ArrayList<>();
        List<Painting> latestUpdate = paintingService.getLatestUpdate();
        for (Painting painting : latestUpdate) {
            paintingUpdateInfo.add(EntityActivity.builder()
                    .created(painting.getCreated())
                    .archiveSection("искусство:")
                    .description("картина «" + painting.getTitle() + "» добавлена в архив " +
                            (painting.getArtist().getNickName().equals("Неизвестен") ? "" : painting.getArtist().getNickName()))
                    .link("art/painting/all")
                    .build());
        }
        return paintingUpdateInfo;
    }

    private List<EntityActivity> getResourcesActivities() {
        List<EntityActivity> resourcesActivities = new ArrayList<>();
        List<Resource> latestUpdate = resourcesService.getLatestUpdate();
        for (Resource resource : latestUpdate) {
            resourcesActivities.add(EntityActivity.builder()
                    .created(resource.getCreated())
                    .archiveSection("ресурсы:")
                    .description("издание «" + resource.getTitle() + "»")
                    .link(resource.getLink())
                    .build());
        }
        return resourcesActivities;
    }
}
