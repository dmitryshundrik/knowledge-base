package com.dmitryshundrik.knowledgebase.service.core.impl;

import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.entity.art.Painting;
import com.dmitryshundrik.knowledgebase.model.entity.core.EntityCurrentActivity;
import com.dmitryshundrik.knowledgebase.model.entity.core.Resource;
import com.dmitryshundrik.knowledgebase.model.entity.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.model.entity.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterEntityUpdateInfoDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianActivityDto;
import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import com.dmitryshundrik.knowledgebase.service.core.EntityActivityService;
import com.dmitryshundrik.knowledgebase.service.core.ResourcesService;
import com.dmitryshundrik.knowledgebase.service.core.SettingService;
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
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.ENTITY_ACTIVITY_CACHE;

@Service
@Transactional
@RequiredArgsConstructor
public class EntityActivityServiceImpl implements EntityActivityService {

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

    @Override
    @Cacheable(value = ENTITY_ACTIVITY_CACHE, key = "#root.methodName")
    public List<EntityCurrentActivity> getEntityActivities() {
        return processEntityActivities();
    }

    @Override
    @CachePut(value = ENTITY_ACTIVITY_CACHE, key = "'getEntityActivities'")
    public List<EntityCurrentActivity> processEntityActivities() {
        List<EntityCurrentActivity> entityActivities = new ArrayList<>();
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

        for (EntityCurrentActivity entityCurrentActivity : entityActivities) {
            entityCurrentActivity.setTimeStamp(InstantFormatter.instantFormatterYMDHMS(entityCurrentActivity.getCreated()));
            entityCurrentActivity.setNew(entityCurrentActivity.getCreated().isAfter(instant));
        }

        return entityActivities.stream()
                .sorted((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()))
                .limit(settingService.getLimitForUpdates())
                .collect(Collectors.toList());
    }

    private List<EntityCurrentActivity> getMusicianActivities() {
        List<EntityCurrentActivity> musicianActivities = new ArrayList<>();
        List<MusicianActivityDto> latestUpdate = musicianService.getLatestUpdate();
        for (MusicianActivityDto activityDto : latestUpdate) {
            musicianActivities.add(EntityCurrentActivity.builder()
                    .created(activityDto.getCreated())
                    .archiveSection("музыка:")
                    .description("музыкант " + activityDto.getNickName())
                    .link("music/musician/" + activityDto.getSlug())
                    .build());
        }
        return musicianActivities;
    }

    private List<EntityCurrentActivity> getAlbumActivities() {
        List<EntityCurrentActivity> albumActivities = new ArrayList<>();
        List<Album> latestUpdate = albumService.getLatestUpdates();
        for (Album album : latestUpdate) {
            albumActivities.add(EntityCurrentActivity.builder()
                    .created(album.getCreated())
                    .archiveSection("музыка:")
                    .description("альбом " + album.getTitle() + " добавлен в архив " + album.getMusician().getNickName())
                    .link("music/musician/" + album.getMusician().getSlug())
                    .build());
        }
        return albumActivities;
    }

    private List<EntityCurrentActivity> getCompositionActivities() {
        List<EntityCurrentActivity> compositionActivities = new ArrayList<>();
        List<Composition> latestUpdate = compositionService.getLatestUpdate();
        for (Composition composition : latestUpdate) {
            compositionActivities.add(EntityCurrentActivity.builder()
                    .created(composition.getCreated())
                    .archiveSection("музыка:")
                    .description("произведение " + composition.getTitle() + " добавлено в архив " + composition.getMusician().getNickName())
                    .link("music/musician/" + composition.getMusician().getSlug())
                    .build());
        }
        return compositionActivities;
    }

    private List<EntityCurrentActivity> getRecipeActivities() {
        List<EntityCurrentActivity> recipeActivities = new ArrayList<>();
        List<Recipe> latestUpdate = recipeService.getLatestUpdate();
        for (Recipe recipe : latestUpdate) {
            recipeActivities.add(EntityCurrentActivity.builder()
                    .created(recipe.getCreated())
                    .archiveSection("гастрономия:")
                    .description("рецепт " + recipe.getTitle())
                    .link("gastronomy/recipe/" + recipe.getSlug())
                    .build());
        }
        return recipeActivities;
    }

    private List<EntityCurrentActivity> getCocktailActivities() {
        List<EntityCurrentActivity> cocktailActivities = new ArrayList<>();
        List<Cocktail> latestUpdate = cocktailService.getLatestUpdate();
        for (Cocktail cocktail : latestUpdate) {
            cocktailActivities.add(EntityCurrentActivity.builder()
                    .created(cocktail.getCreated())
                    .archiveSection("гастрономия:")
                    .description("коктейль " + cocktail.getTitle())
                    .link("gastronomy/cocktail/" + cocktail.getSlug())
                    .build());
        }
        return cocktailActivities;
    }

    private List<EntityCurrentActivity> getWriterActivities() {
        List<EntityCurrentActivity> writerActivities = new ArrayList<>();
        List<WriterEntityUpdateInfoDto> latestUpdate = writerService.getLatestUpdate();
        for (WriterEntityUpdateInfoDto writer : latestUpdate) {
            writerActivities.add(EntityCurrentActivity.builder()
                    .created(writer.getCreated())
                    .archiveSection("литература:")
                    .description("писатель " + writer.getNickName())
                    .link("literature/writer/" + writer.getSlug())
                    .build());
        }
        return writerActivities;
    }

    private List<EntityCurrentActivity> getProseActivities() {
        List<EntityCurrentActivity> proseActivities = new ArrayList<>();
        List<Prose> latestUpdate = proseService.getLatestUpdate();
        for (Prose prose : latestUpdate) {
            proseActivities.add(EntityCurrentActivity.builder()
                    .created(prose.getCreated())
                    .archiveSection("литература:")
                    .description("произведение «" + prose.getTitle() + "» добавлено в архив " + prose.getWriter().getNickName())
                    .link("literature/writer/" + prose.getWriter().getSlug())
                    .build());
        }
        return proseActivities;
    }

    private List<EntityCurrentActivity> getQuoteActivities() {
        List<EntityCurrentActivity> quoteActivities = new ArrayList<>();
        List<Quote> latestUpdate = quoteService.getLatestUpdate();
        for (Quote quote : latestUpdate) {
            quoteActivities.add(EntityCurrentActivity.builder()
                    .created(quote.getCreated())
                    .archiveSection("литература:")
                    .description("цитата " + (quote.getProse() == null ? "" : "из «" + quote.getProse().getTitle() + "» ")
                            + "добавлена в архив " + (quote.getWriter() == null ? "" : quote.getWriter().getNickName()))
                    .link("literature/quote/all")
                    .build());
        }
        return quoteActivities;
    }

    private List<EntityCurrentActivity> getArtistActivities() {
        List<EntityCurrentActivity> artistActivities = new ArrayList<>();
        List<Artist> latestUpdate = artistService.getLatestUpdate();
        for (Artist artist : latestUpdate) {
            artistActivities.add(EntityCurrentActivity.builder()
                    .created(artist.getCreated())
                    .archiveSection("искусство:")
                    .description("художник " + artist.getNickName())
                    .link("art/artist/" + artist.getSlug())
                    .build());
        }
        return artistActivities;
    }

    private List<EntityCurrentActivity> getPaintingActivities() {
        List<EntityCurrentActivity> paintingUpdateInfo = new ArrayList<>();
        List<Painting> latestUpdate = paintingService.getLatestUpdate();
        for (Painting painting : latestUpdate) {
            paintingUpdateInfo.add(EntityCurrentActivity.builder()
                    .created(painting.getCreated())
                    .archiveSection("искусство:")
                    .description("картина «" + painting.getTitle() + "» добавлена в архив " +
                            (painting.getArtist().getNickName().equals("Неизвестен") ? "" : painting.getArtist().getNickName()))
                    .link("art/painting/all")
                    .build());
        }
        return paintingUpdateInfo;
    }

    private List<EntityCurrentActivity> getResourcesActivities() {
        List<EntityCurrentActivity> resourcesActivities = new ArrayList<>();
        List<Resource> latestUpdate = resourcesService.getLatestUpdate();
        for (Resource resource : latestUpdate) {
            resourcesActivities.add(EntityCurrentActivity.builder()
                    .created(resource.getCreated())
                    .archiveSection("ресурсы:")
                    .description("издание «" + resource.getTitle() + "»")
                    .link(resource.getLink())
                    .build());
        }
        return resourcesActivities;
    }
}
