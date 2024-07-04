package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.art.Artist;
import com.dmitryshundrik.knowledgebase.model.art.Painting;
import com.dmitryshundrik.knowledgebase.model.common.EntityUpdateInfo;
import com.dmitryshundrik.knowledgebase.model.common.Resource;
import com.dmitryshundrik.knowledgebase.model.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.model.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.model.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EntityUpdateInfoService {

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

    public EntityUpdateInfoService(SettingService settingService, MusicianService musicianService, AlbumService albumService,
                                   CompositionService compositionService, RecipeService recipeService,
                                   CocktailService cocktailService, WriterService writerService, ProseService proseService,
                                   QuoteService quoteService, ArtistService artistService, PaintingService paintingService,
                                   ResourcesService resourcesService) {
        this.settingService = settingService;
        this.musicianService = musicianService;
        this.albumService = albumService;
        this.compositionService = compositionService;
        this.recipeService = recipeService;
        this.cocktailService = cocktailService;
        this.writerService = writerService;
        this.proseService = proseService;
        this.quoteService = quoteService;
        this.artistService = artistService;
        this.paintingService = paintingService;
        this.resourcesService = resourcesService;
    }

    public List<EntityUpdateInfo> getLatestUpdates() {
        List<EntityUpdateInfo> allUpdateInfo = new ArrayList<>();
        allUpdateInfo.addAll(getMusicianUpdates());
        allUpdateInfo.addAll(getAlbumUpdates());
        allUpdateInfo.addAll(getCompositionUpdates());
        allUpdateInfo.addAll(getRecipeUpdates());
        allUpdateInfo.addAll(getCocktailUpdates());
        allUpdateInfo.addAll(getWriterUpdates());
        allUpdateInfo.addAll(getProseUpdates());
        allUpdateInfo.addAll(getQuoteUpdates());
        allUpdateInfo.addAll(getArtistUpdates());
        allUpdateInfo.addAll(getPaintingUpdates());
        allUpdateInfo.addAll(getResourcesUpdates());

        Instant instant = Instant.now().minus(settingService.getTimeIntervalForUpdates(), ChronoUnit.DAYS);
        for (EntityUpdateInfo entityUpdateInfo : allUpdateInfo) {
            entityUpdateInfo.setNew(entityUpdateInfo.getCreated().isAfter(instant));
        }

        for (EntityUpdateInfo entityUpdateInfo : allUpdateInfo) {
            entityUpdateInfo.setTimeStamp(InstantFormatter.instantFormatterYMDHMS(entityUpdateInfo.getCreated()));
        }

        return allUpdateInfo.stream()
                .sorted((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()))
                .limit(settingService.getLimitForUpdates())
                .collect(Collectors.toList());
    }

    private List<EntityUpdateInfo> getMusicianUpdates() {
        List<EntityUpdateInfo> musicianUpdateInfo = new ArrayList<>();
        List<Musician> latestUpdate = musicianService.getLatestUpdate();
        for (Musician musician : latestUpdate) {
            musicianUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(musician.getCreated())
                    .archiveSection("музыка:")
                    .description("музыкант " + musician.getNickName())
                    .link("music/musician/" + musician.getSlug())
                    .build());
        }
        return musicianUpdateInfo;
    }

    private List<EntityUpdateInfo> getAlbumUpdates() {
        List<EntityUpdateInfo> albumUpdateInfo = new ArrayList<>();
        List<Album> latestUpdate = albumService.getLatestUpdate();
        for (Album album : latestUpdate) {
            albumUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(album.getCreated())
                    .archiveSection("музыка:")
                    .description("альбом " + album.getTitle() + " добавлен в архив " + album.getMusician().getNickName())
                    .link("music/musician/" + album.getMusician().getSlug())
                    .build());
        }
        return albumUpdateInfo;
    }

    private List<EntityUpdateInfo> getCompositionUpdates() {
        List<EntityUpdateInfo> compositionUpdateInfo = new ArrayList<>();
        List<Composition> latestUpdate = compositionService.getLatestUpdate();
        for (Composition composition : latestUpdate) {
            compositionUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(composition.getCreated())
                    .archiveSection("музыка:")
                    .description("произведение " + composition.getTitle() + " добавлено в архив " + composition.getMusician().getNickName())
                    .link("music/musician/" + composition.getMusician().getSlug())
                    .build());
        }
        return compositionUpdateInfo;
    }

    private List<EntityUpdateInfo> getRecipeUpdates() {
        List<EntityUpdateInfo> recipeUpdateInfo = new ArrayList<>();
        List<Recipe> latestUpdate = recipeService.getLatestUpdate();
        for (Recipe recipe : latestUpdate) {
            recipeUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(recipe.getCreated())
                    .archiveSection("гастрономия:")
                    .description("рецепт " + recipe.getTitle())
                    .link("gastronomy/recipe/" + recipe.getSlug())
                    .build());
        }
        return recipeUpdateInfo;
    }

    private List<EntityUpdateInfo> getCocktailUpdates() {
        List<EntityUpdateInfo> cocktailUpdateInfo = new ArrayList<>();
        List<Cocktail> latestUpdate = cocktailService.getLatestUpdate();
        for (Cocktail cocktail : latestUpdate) {
            cocktailUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(cocktail.getCreated())
                    .archiveSection("гастрономия:")
                    .description("коктейль " + cocktail.getTitle())
                    .link("gastronomy/cocktail/" + cocktail.getSlug())
                    .build());
        }
        return cocktailUpdateInfo;
    }

    private List<EntityUpdateInfo> getWriterUpdates() {
        List<EntityUpdateInfo> writerUpdateInfo = new ArrayList<>();
        List<Writer> latestUpdate = writerService.getLatestUpdate();
        for (Writer writer : latestUpdate) {
            writerUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(writer.getCreated())
                    .archiveSection("литература:")
                    .description("писатель " + writer.getNickName())
                    .link("literature/writer/" + writer.getSlug())
                    .build());
        }
        return writerUpdateInfo;
    }

    private List<EntityUpdateInfo> getProseUpdates() {
        List<EntityUpdateInfo> proseUpdateInfo = new ArrayList<>();
        List<Prose> latestUpdate = proseService.getLatestUpdate();
        for (Prose prose : latestUpdate) {
            proseUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(prose.getCreated())
                    .archiveSection("литература:")
                    .description("произведение «" + prose.getTitle() + "» добавлено в архив " + prose.getWriter().getNickName())
                    .link("literature/writer/" + prose.getWriter().getSlug())
                    .build());
        }
        return proseUpdateInfo;
    }

    private List<EntityUpdateInfo> getQuoteUpdates() {
        List<EntityUpdateInfo> quoteUpdateInfo = new ArrayList<>();
        List<Quote> latestUpdate = quoteService.getLatestUpdate();
        for (Quote quote : latestUpdate) {
            quoteUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(quote.getCreated())
                    .archiveSection("литература:")
                    .description("цитата " + (quote.getProse() == null ? "" : "из «" + quote.getProse().getTitle() + "» ")
                            + "добавлена в архив " + (quote.getWriter() == null ? "" : quote.getWriter().getNickName()))
                    .link("literature/quote/all")
                    .build());
        }
        return quoteUpdateInfo;
    }

    private List<EntityUpdateInfo> getArtistUpdates() {
        List<EntityUpdateInfo> artistUpdateInfo = new ArrayList<>();
        List<Artist> latestUpdate = artistService.getLatestUpdate();
        for (Artist artist : latestUpdate) {
            artistUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(artist.getCreated())
                    .archiveSection("искусство:")
                    .description("художник " + artist.getNickName())
                    .link("art/artist/" + artist.getSlug())
                    .build());
        }
        return artistUpdateInfo;
    }

    private List<EntityUpdateInfo> getPaintingUpdates() {
        List<EntityUpdateInfo> paintingUpdateInfo = new ArrayList<>();
        List<Painting> latestUpdate = paintingService.getLatestUpdate();
        for (Painting painting : latestUpdate) {
            paintingUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(painting.getCreated())
                    .archiveSection("искусство:")
                    .description("картина «" + painting.getTitle() + "» добавлена в архив " +
                            (painting.getArtist().getNickName().equals("Неизвестен") ? "" : painting.getArtist().getNickName()))
                    .link("art/painting/all")
                    .build());
        }
        return paintingUpdateInfo;
    }

    private List<EntityUpdateInfo> getResourcesUpdates() {
        List<EntityUpdateInfo> quoteUpdateInfo = new ArrayList<>();
        List<Resource> latestUpdate = resourcesService.getLatestUpdate();
        for (Resource resource : latestUpdate) {
            quoteUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(resource.getCreated())
                    .archiveSection("ресурсы:")
                    .description("издание «" + resource.getTitle() + "»")
                    .link(resource.getLink())
                    .build());
        }
        return quoteUpdateInfo;
    }

}
