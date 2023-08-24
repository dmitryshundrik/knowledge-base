package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.common.EntityUpdateInfo;
import com.dmitryshundrik.knowledgebase.model.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.model.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.model.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.service.gastronomy.CocktailService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.RecipeService;
import com.dmitryshundrik.knowledgebase.service.literature.ProseService;
import com.dmitryshundrik.knowledgebase.service.literature.QuoteService;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Integer DAYS_UPDATES = 1;

    @Autowired
    private MusicianService musicianService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private CompositionService compositionService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CocktailService cocktailService;

    @Autowired
    private WriterService writerService;

    @Autowired
    private ProseService proseService;

    @Autowired
    private QuoteService quoteService;

    public List<EntityUpdateInfo> getAll() {
        List<EntityUpdateInfo> allUpdateInfo = new ArrayList<>();

        allUpdateInfo.addAll(getMusicianUpdates());
        allUpdateInfo.addAll(getAlbumUpdates());
        allUpdateInfo.addAll(getCompositionUpdates());
        allUpdateInfo.addAll(getRecipeUpdates());
        allUpdateInfo.addAll(getCocktailUpdates());
        allUpdateInfo.addAll(getWriterUpdates());
        allUpdateInfo.addAll(getProseUpdates());
        allUpdateInfo.addAll(getQuoteUpdates());

        Instant instant = Instant.now().minus(DAYS_UPDATES, ChronoUnit.DAYS);
        for (EntityUpdateInfo entityUpdateInfo : allUpdateInfo) {
            entityUpdateInfo.setNew(entityUpdateInfo.getCreated().isAfter(instant));
        }

        return allUpdateInfo.stream()
                .sorted((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()))
                .limit(20)
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
                    .description("композиция " + composition.getTitle() + " добавлена в архив " + composition.getMusician().getNickName())
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
                    .description("цитата " + (quote.getProse() == null ? "" : "из «" + quote.getProse().getTitle() + "» ") + "добавлена в архив " + quote.getWriter().getNickName())
                    .link("literature/writer/" + quote.getWriter().getSlug())
                    .build());
        }
        return quoteUpdateInfo;
    }

}
